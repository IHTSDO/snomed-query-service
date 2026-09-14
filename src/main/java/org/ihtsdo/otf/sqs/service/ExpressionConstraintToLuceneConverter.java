package org.ihtsdo.otf.sqs.service;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.snomed.langauges.ecl.ECLException;
import org.snomed.langauges.ecl.generated.ImpotentECLListener;
import org.snomed.langauges.ecl.generated.parser.ECLLexer;
import org.snomed.langauges.ecl.generated.parser.ECLParser;

import static org.ihtsdo.otf.sqs.service.ExpressionConstraintToLuceneConverter.ExpressionConstraintListener.ComparisonOperator.*;

public class ExpressionConstraintToLuceneConverter {

	public static final String DEFAULT_CARDINALITY = "1 TO *";
	public static final String AND = " AND ";

	enum InternalFunction {
		ATTRIBUTE_DESCENDANT_OF(true, false, false),
		ATTRIBUTE_DESCENDANT_OR_SELF_OF(true, false, true),
		ATTRIBUTE_ANCESTOR_OF(true, true, false),
		ATTRIBUTE_ANCESTOR_OR_SELF_OF(true, true, true),
		ANCESTOR_OR_SELF_OF(false, true, true),
		ANCESTOR_OF(false, true, false),

		// Constraint operators applied to a member-of expression. The members
		// are not known until the index is read, so these resolve like the
		// functions above rather than becoming field queries here.
		//
		// Deliberately NOT named *_ANCESTOR_OF or *_DESCENDANT_OF: the resolver
		// looks for a function by String.contains, so a name containing an
		// existing one would be rewritten by that one first.
		REFSET_MEMBER_DESCENDANTS(false, false, false, true),
		REFSET_MEMBER_DESCENDANTS_OR_SELF(false, false, true, true),
		REFSET_MEMBER_ANCESTORS(false, true, false, true),
		REFSET_MEMBER_ANCESTORS_OR_SELF(false, true, true, true);

		private final boolean attributeType;
		private final boolean ancestorType;
		private final boolean includeSelf;
		private final boolean refsetMemberType;

		InternalFunction(boolean attributeType, boolean ancestorType, boolean includeSelf) {
			this(attributeType, ancestorType, includeSelf, false);
		}

		InternalFunction(boolean attributeType, boolean ancestorType, boolean includeSelf, boolean refsetMemberType) {
			this.attributeType = attributeType;
			this.ancestorType = ancestorType;
			this.includeSelf = includeSelf;
			this.refsetMemberType = refsetMemberType;
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

		/** Resolve against the MEMBERS of the referenced refset, not the refset concept. */
		public boolean isRefsetMemberType() {
			return refsetMemberType;
		}

	}

	public String parse(String ecl) throws ECLException {
		CharStream inputStream = CharStreams.fromString(ecl);
		final ECLLexer lexer = new ECLLexer(inputStream);
		final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		final ECLParser parser = new ECLParser(tokenStream);
		parser.setErrorHandler(new BailErrorStrategy());
		ParserRuleContext tree;
		try {
			tree = parser.expressionconstraint();
		} catch (RecognitionException | ParseCancellationException | NullPointerException e) {
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
			GREATER_THAN(">"),
			LESS_THAN("<"),
			GREATER_THAN_OR_EQUAL_TO(">="),
			LESS_THAN_OR_EQUAL_TO("<="),
			NOT_EQUAL_TO ("!=");

			private final String text;

			ComparisonOperator(String text) {
				this.text = text;
			}

			public String getText() {
				return this.text;
			}

			public static ComparisonOperator fromText(String text) {
				for (ComparisonOperator operator : ComparisonOperator.values()) {
					if (operator.getText().equalsIgnoreCase(text)) {
						return operator;
					}
				}
				return null;
			}
		}

		private String luceneQuery = "";
		private boolean inAttribute;
		private boolean inAttributeGroup;
		private ComparisonOperator comparisonOperator = null;
		private ECLParser.ConstraintoperatorContext constraintOperatorContext = null;
		private boolean isMemberOf;
		String cardinality = null;
		String attributeInGroupCardinality = null;
		private boolean isTotalGroupApplied;
		private String attributeId;
		private boolean addCloseParenthesis;

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
				if (inAttribute) {
					attributeId = conceptId;
				}
				if (isMemberOf) {
					constructMemberOfQuery(constraintOperatorContext, conceptId);
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

		/**
		 * A member-of expression, with the constraint operator applied to the
		 * MEMBERS.
		 *
		 * <p>{@code ^X} is the members of X. {@code << ^X} is those members and
		 * their descendants - the operator distributes over the member set, it
		 * does not describe the refset concept.
		 *
		 * <p>This used to append {@code memberOf:X} whatever the operator was,
		 * silently discarding it, so {@code << ^X} returned the members alone.
		 * A caller using that to decide which concepts are permitted something
		 * saw every descendant of a member as not permitted it.
		 */
		private void constructMemberOfQuery(ECLParser.ConstraintoperatorContext ctx, String refsetId) {
			if (ctx == null) {
				luceneQuery += ConceptFieldNames.MEMBER_OF + ":" + refsetId;
				return;
			}
			if (inAttribute) {
				// The value of an attribute is a concept list, and the member
				// set is not known here. Refusing is not a regression: the
				// operator was being dropped, so the answer was already wrong.
				throwUnsupported("constraint operator applied to a member-of expression in an attribute value");
			}
			if (ctx.descendantof() != null) {
				luceneQuery += InternalFunction.REFSET_MEMBER_DESCENDANTS + "(" + refsetId + ")";
			} else if (ctx.descendantorselfof() != null) {
				luceneQuery += InternalFunction.REFSET_MEMBER_DESCENDANTS_OR_SELF + "(" + refsetId + ")";
			} else if (ctx.ancestorof() != null) {
				luceneQuery += InternalFunction.REFSET_MEMBER_ANCESTORS + "(" + refsetId + ")";
			} else if (ctx.ancestororselfof() != null) {
				luceneQuery += InternalFunction.REFSET_MEMBER_ANCESTORS_OR_SELF + "(" + refsetId + ")";
			} else {
				throwUnsupported("constraint operator " + ctx.getText() + " applied to a member-of expression");
			}
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
					luceneQuery += focusConceptId + ConceptFieldNames.TOTAL_GROUPS + ":[" + cardinality + "]" + AND;
					isTotalGroupApplied = true;
					cardinality = null;
				}
				if (attributeInGroupCardinality != null) {
					luceneQuery += focusConceptId + ConceptFieldNames.GROUP_CARDINALITY + ":[" + attributeInGroupCardinality + "]" + AND;
				} else if (cardinality != null) {
					luceneQuery += focusConceptId + ConceptFieldNames.CARDINALITY + ":[" + cardinality + "]" + AND;
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
			attributeId = null;
			if (addCloseParenthesis) {
				luceneQuery += ")";
				addCloseParenthesis = false;
			}
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
			} else if (ctx.getText().equals("!=")){
				luceneQuery += ": (* NOT ";
				addCloseParenthesis = true;
			} else {
				throwUnsupported(ctx.getText() + " expressionComparisonOperator");
			}
		}

		@Override
		public void enterEclrefinement(ECLParser.EclrefinementContext ctx) {
			luceneQuery += AND;
		}

		@Override
		public void enterConjunction(ECLParser.ConjunctionContext ctx) {
			luceneQuery += AND;
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
				if (addCloseParenthesis) {
					luceneQuery += ")";
					addCloseParenthesis = false;
				}
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
			comparisonOperator = ComparisonOperator.fromText(ctx.getText());
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
			comparisonOperator = ComparisonOperator.fromText(ctx.getText());
		}

		@Override
		public void enterNumericvalue(ECLParser.NumericvalueContext ctx) {
			String value = ctx.getText().startsWith("#") ? ctx.getText().substring(1) : ctx.getText();
			luceneQuery += "_value:";
			if (EQUAL_TO == comparisonOperator) {
				luceneQuery += "[" + value + " TO " + value + "]";
			} else if (GREATER_THAN_OR_EQUAL_TO == comparisonOperator) {
				luceneQuery += "[" + value + " TO *}";
			} else if (GREATER_THAN == comparisonOperator) {
				luceneQuery += "{" + value + " TO *}";
			} else if (LESS_THAN == comparisonOperator) {
				luceneQuery += "{* TO " + value + "}";
			} else if (LESS_THAN_OR_EQUAL_TO == comparisonOperator) {
				luceneQuery += "{* TO " + value + "]";
			} else if (NOT_EQUAL_TO == comparisonOperator) {
				// Use > or < instead for numeric not equal to
				luceneQuery += "{" + value + " TO *}";
				luceneQuery += " OR " + attributeId + "_value:{* TO " + value + "}";
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

		public void enterMatchsearchterm(ECLParser.MatchsearchtermContext ctx) {
			if (ctx == null) {
				return;
			}

			luceneQuery += ctx.getText();
		}
	}

}
