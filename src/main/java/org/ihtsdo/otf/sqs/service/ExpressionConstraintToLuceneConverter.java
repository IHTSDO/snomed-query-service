package org.ihtsdo.otf.sqs.service;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.snomed.langauges.ecl.ECLException;
import org.snomed.langauges.ecl.ECLObjectFactory;
import org.snomed.langauges.ecl.ECLQueryBuilder;
import org.snomed.langauges.ecl.generated.ImpotentECLListener;
import org.snomed.langauges.ecl.generated.parser.ECLLexer;
import org.snomed.langauges.ecl.generated.parser.ECLParser;

import java.util.*;

import static org.ihtsdo.otf.sqs.service.ExpressionConstraintToLuceneConverter.ExpressionConstraintListener.ComparisonOperator.*;

public class ExpressionConstraintToLuceneConverter {

	public static final String DEFAULT_CARDINALITY = "1 TO *";

	private ECLQueryBuilder eclQueryBuilder = new ECLQueryBuilder(new ECLObjectFactory());

	enum InternalFunction {
		ATTRIBUTE_DESCENDANT_OF(true, false, false),
		ATTRIBUTE_DESCENDANT_OR_SELF_OF(true, false, true),
		ATTRIBUTE_ANCESTOR_OF(true, true, false),
		ATTRIBUTE_ANCESTOR_OR_SELF_OF(true, true, true),

		ANCESTOR_OR_SELF_OF(false, true, true),
		ANCESTOR_OF(false, true, false);

		private boolean attributeType;
		private boolean ancestorType;
		private boolean includeSelf;

		InternalFunction(boolean attributeType, boolean ancestorType, boolean includeSelf) {
			this.attributeType = attributeType;
			this.ancestorType = ancestorType;
			this.includeSelf = includeSelf;
		}

		public boolean isAttributeType() {
			return attributeType;
		}

		public boolean isAncestorType() {
			return ancestorType;
		}

		public boolean isIncludeSelf() {
			return includeSelf;
		}

	}

	public String parse(String ecl) throws RecognitionException {
		ANTLRInputStream inputStream = new ANTLRInputStream(ecl);
		final ECLLexer lexer = new ECLLexer(inputStream);
		final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		final ECLParser parser = new ECLParser(tokenStream);
		parser.setErrorHandler(new BailErrorStrategy());
		ParserRuleContext tree;
		try {
			tree = parser.expressionconstraint();
		} catch (NullPointerException | RecognitionException | ParseCancellationException e) {
			throw new ECLException("Failed to parse ECL '" + ecl + "'", e);
		}
		final ParseTreeWalker walker = new ParseTreeWalker();

		final ExpressionConstraintListener listener = new ExpressionConstraintListener();
		walker.walk(listener, tree);
		return listener.getLuceneQuery().trim();
	}

	protected static final class ExpressionConstraintListener extends ImpotentECLListener {
		enum ComparisonOperator {
			EQUAL_TO("="),
			GREAT_THAN(">"),
			LESS_THAN("<"),
			GREAT_THAN_OR_EQUAL_TO(">="),
			LESS_THAN_OR_EQUAL_TO("<="),
			NOT_EQUAL_TO ("!=");

			private String text;

			ComparisonOperator(String text) {
				this.text = text;
			}

			public String getText() {
				return this.text;
			}

			public static Optional<ComparisonOperator> fromText(String text) {
				return Arrays.stream(values())
						.filter(comparisonOperator -> comparisonOperator.text.equalsIgnoreCase(text))
						.findFirst();
			}
		}

		private String luceneQuery = "";
		private boolean inAttribute;
		private Set<ParserRuleContext> bracesToClose = new HashSet<>();
		private boolean inAttributeGroup;
		private ComparisonOperator comparisonOperator = null;
		private ECLParser.ConstraintoperatorContext constraintOperatorContext = null;
		private boolean isMemberOf;
		String cardinality = null;
		String attributeInGroupCardinality = null;
		private boolean isTotalGroupApplied;


		@Override
		public void visitErrorNode(ErrorNode node) {
			super.visitErrorNode(node);
		}


		@Override
		public void enterMemberof(ECLParser.MemberofContext ctx) {
			isMemberOf = true;
		}

		@Override
		public void enterConstraintoperator(ECLParser.ConstraintoperatorContext ctx) {
			constraintOperatorContext = ctx;
		}

		@Override
		public void enterEclfocusconcept(ECLParser.EclfocusconceptContext ctx) {
			if (ctx.wildcard() != null) {
				if (!inAttribute) {
					luceneQuery += ConceptFieldNames.ID + ":*";
				} else {
					luceneQuery += "*";
				}
			} else {
				String conceptId = ctx.eclconceptreference().conceptid().getText();
				if (isMemberOf) {
					luceneQuery += ConceptFieldNames.MEMBER_OF + ":" + conceptId;
				} else {
					constructLuceneQuery(constraintOperatorContext, conceptId);
				}
			}
		}

		@Override
		public void exitEclfocusconcept(ECLParser.EclfocusconceptContext ctx) {
			constraintOperatorContext = null;
			isMemberOf = false;
		}

		private void constructLuceneQuery(ECLParser.ConstraintoperatorContext ctx, String focusConceptId) {
			if (ctx == null) {
				if (!inAttribute) {
					luceneQuery += ConceptFieldNames.ID + ":" + focusConceptId;
				} else {
					addCardinality(focusConceptId);
					luceneQuery += focusConceptId;
				}
			} else {
				if (ctx.descendantof() != null) {
					if (!inAttribute) {
						luceneQuery += ConceptFieldNames.ANCESTOR + ":" + focusConceptId;
					} else {
						luceneQuery += InternalFunction.ATTRIBUTE_DESCENDANT_OF + "(" + focusConceptId + ")";
					}
				} else if (ctx.descendantorselfof() != null) {
					if (!inAttribute) {
						luceneQuery += "(" + ConceptFieldNames.ID + ":" + focusConceptId + " OR " + ConceptFieldNames.ANCESTOR + ":" + focusConceptId + ")";
					} else {
						luceneQuery += InternalFunction.ATTRIBUTE_DESCENDANT_OR_SELF_OF + "(" + focusConceptId + ")";
					}
				} else if (ctx.ancestororselfof() != null) {
					if (!inAttribute) {
						luceneQuery += InternalFunction.ANCESTOR_OR_SELF_OF + "(" + focusConceptId + ")";
					} else {
						luceneQuery += InternalFunction.ATTRIBUTE_ANCESTOR_OR_SELF_OF + "(" + focusConceptId + ")";
					}
				} else if (ctx.ancestorof() != null) {
					if (!inAttribute) {
						luceneQuery += InternalFunction.ANCESTOR_OF + "(" + focusConceptId + ")";
					} else {
						luceneQuery += InternalFunction.ATTRIBUTE_ANCESTOR_OF + "(" + focusConceptId + ")";
					}
				}
			}
		}

		private void addCardinality(String focusConceptId) {
			if (cardinality != null || attributeInGroupCardinality != null) {
				if (inAttributeGroup && !isTotalGroupApplied) {
					cardinality = cardinality == null ? DEFAULT_CARDINALITY : cardinality;
					luceneQuery += focusConceptId + ConceptFieldNames.TOTAL_GROUPS + ":[" + cardinality + "]" + " AND ";
					isTotalGroupApplied = true;
					cardinality = null;
				}
				if (attributeInGroupCardinality != null) {
					luceneQuery += focusConceptId + ConceptFieldNames.GROUP_CARDINALITY + ":[" + attributeInGroupCardinality + "]" + " AND ";
				} else if (cardinality != null) {
					luceneQuery += focusConceptId + ConceptFieldNames.CARDINALITY + ":[" + cardinality + "]" + " AND ";
				}
			}
		}

		@Override
		public void enterEclattributegroup(ECLParser.EclattributegroupContext ctx) {
			inAttributeGroup = true;
		}

		@Override
		public void enterEclattribute(ECLParser.EclattributeContext ctx) {
			inAttribute = true;
		}

		@Override
		public void exitEclattribute(ECLParser.EclattributeContext ctx) {
			inAttribute = false;
		}

		@Override
		public void enterEclattributename(ECLParser.EclattributenameContext ctx) {
			if ("*".equals(ctx.getText())) {
				throwUnsupported("wildcard attribute name");
			}
			if (ctx.getText().contains("<") || ctx.getText().contains(">")) {
				throwUnsupported( "attribute name with expression constraint " + ctx.getText());
			}
		}

		@Override
		public void enterExpressioncomparisonoperator(ECLParser.ExpressioncomparisonoperatorContext ctx) {
			if (ctx.getText().equals("=")) {
				luceneQuery += ":";
			} else {
				throwUnsupported("not-equal expressionComparisonOperator");
			}
		}

		@Override
		public void enterEclrefinement(ECLParser.EclrefinementContext ctx) {
			luceneQuery += " AND ";
		}

		@Override
		public void enterConjunction(ECLParser.ConjunctionContext ctx) {
			luceneQuery += " AND ";
		}

		@Override
		public void enterDisjunction(ECLParser.DisjunctionContext ctx) {
			luceneQuery += " OR ";
		}

		@Override
		public void enterExclusion(ECLParser.ExclusionContext ctx) {
			luceneQuery += " NOT ";
		}

		@Override
		public void enterSubexpressionconstraint(ECLParser.SubexpressionconstraintContext ctx) {
			addLeftParenthesisIfNotNull(ctx.LEFT_PAREN());
		}

		@Override
		public void exitSubexpressionconstraint(ECLParser.SubexpressionconstraintContext ctx) {
			addRightParenthesisIfNotNull(ctx.RIGHT_PAREN());
		}

		@Override
		public void enterSubrefinement(ECLParser.SubrefinementContext ctx) {
			addLeftParenthesisIfNotNull(ctx.LEFT_PAREN());
		}

		@Override
		public void exitSubrefinement(ECLParser.SubrefinementContext ctx) {
			addRightParenthesisIfNotNull(ctx.RIGHT_PAREN());
		}

		@Override
		public void enterSubattributeset(ECLParser.SubattributesetContext ctx) {
			addLeftParenthesisIfNotNull(ctx.LEFT_PAREN());
		}

		@Override
		public void exitSubattributeset(ECLParser.SubattributesetContext ctx) {
			addRightParenthesisIfNotNull(ctx.RIGHT_PAREN());
		}

		private void addLeftParenthesisIfNotNull(TerminalNode terminalNode) {
			if (terminalNode != null) {
				luceneQuery = luceneQuery.trim();
				luceneQuery += " (";
			}
		}

		private void addRightParenthesisIfNotNull(TerminalNode terminalNode) {
			if (terminalNode != null) {
				luceneQuery = luceneQuery.trim();
				luceneQuery += ")";
			}
		}

		@Override
		public void enterCardinality(ECLParser.CardinalityContext ctx) {
			String text = ctx.getText();
			if (text != null) {
				text = text.replace("..", " TO ");
				if (inAttributeGroup && inAttribute) {
					attributeInGroupCardinality = text;
				} else {
					cardinality = text;
				}
			}
		}

		@Override
		public void enterStringcomparisonoperator(ECLParser.StringcomparisonoperatorContext ctx) {
			comparisonOperator = ComparisonOperator.fromText(ctx.getText()).get();
			if (EQUAL_TO == comparisonOperator) {
				luceneQuery += ":";
			} else if (NOT_EQUAL_TO == comparisonOperator) {
				luceneQuery += " NOT ";
			} else {
				throw new IllegalArgumentException(String.format("Invalid comparison operator %s for String", ctx.getText()));
			}
		}

		@Override
		public void enterStringvalue(ECLParser.StringvalueContext ctx) {
			luceneQuery += ctx.getText();
		}

		@Override
		public void enterNumericcomparisonoperator(ECLParser.NumericcomparisonoperatorContext ctx) {
			comparisonOperator = ComparisonOperator.fromText(ctx.getText()).get();
		}

		@Override
		public void enterNumericvalue(ECLParser.NumericvalueContext ctx) {
			String value = ctx.getText().startsWith("#") ? ctx.getText().substring(1) : ctx.getText();
			if (EQUAL_TO == comparisonOperator) {
				luceneQuery += ":" + value;
			} else if (GREAT_THAN_OR_EQUAL_TO == comparisonOperator) {
				luceneQuery += "_value:[" + value + " TO *]";
			} else if (GREAT_THAN == comparisonOperator) {
				luceneQuery += "_value:{" + value + " TO *}";
			} else if (LESS_THAN == comparisonOperator) {
				luceneQuery += "_value:{* TO " + value + "}";
			} else if (LESS_THAN_OR_EQUAL_TO == comparisonOperator) {
				luceneQuery += "_value:[* TO " + value + "]";
			} else if (NOT_EQUAL_TO == comparisonOperator) {
				luceneQuery += " NOT " + value;
			}
		}

		@Override
		public void enterReverseflag(ECLParser.ReverseflagContext ctx) {
			throwUnsupported("reverseFlag");
		}

		private void throwUnsupported(String feature) {
			throw new UnsupportedOperationException(feature + " is not currently supported.");
		}

		public String getLuceneQuery() {
			return luceneQuery;
		}
	}

}
