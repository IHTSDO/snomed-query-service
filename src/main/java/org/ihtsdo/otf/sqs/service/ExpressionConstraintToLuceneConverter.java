package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.parser.secl.ExpressionConstraintBaseListener;
import org.ihtsdo.otf.sqs.parser.secl.ExpressionConstraintLexer;
import org.ihtsdo.otf.sqs.parser.secl.ExpressionConstraintParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ihtsdo.otf.sqs.service.ExpressionConstraintToLuceneConverter.ExpressionConstraintListener.ComparisonOperator.*;

public class ExpressionConstraintToLuceneConverter {

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

	public String parse(String ecQuery) throws RecognitionException {
		final ExpressionConstraintLexer lexer = new ExpressionConstraintLexer(new ANTLRInputStream(ecQuery));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		final ExpressionConstraintParser parser = new ExpressionConstraintParser(tokens);
		final List<RecognitionException> exceptions = new ArrayList<>();
		parser.setErrorHandler(new ANTLRErrorStrategy() {
			@Override
			public void reportError(Parser parser, RecognitionException e) {
				exceptions.add(e);
			}

			@Override
			public void reset(Parser parser) {
			}

			@Override
			public Token recoverInline(Parser parser) throws RecognitionException {
				return null;
			}

			@Override
			public void recover(Parser parser, RecognitionException e) throws RecognitionException {
			}

			@Override
			public void sync(Parser parser) throws RecognitionException {
			}

			@Override
			public boolean inErrorRecoveryMode(Parser parser) {
				return false;
			}

			@Override
			public void reportMatch(Parser parser) {
			}
		});
		ParserRuleContext tree = parser.expressionconstraint();

		final ParseTreeWalker walker = new ParseTreeWalker();
		final ExpressionConstraintListener listener = new ExpressionConstraintListener();
		walker.walk(listener, tree);
		if (exceptions.isEmpty()) {
			return listener.getLuceneQuery();
		} else {
			final RecognitionException recognitionException = exceptions.get(0);
			// TODO Decent exception which logs this stuff
//			System.err.println(recognitionException);
//			System.err.println(recognitionException.getOffendingToken().getLine());
//			System.err.println(recognitionException.getExpectedTokens());
//			System.err.println(ExpressionConstraintLexer.ruleNames[4]);
			throw recognitionException;
		}
	}

	protected static final class ExpressionConstraintListener extends ExpressionConstraintBaseListener {
		enum ComparisonOperator {
			EQUAL_TO("="),
			GREAT_THAN(">"),
			LESS_THAN("<"),
			GREAT_THAN_OR_EQUAL_TO(">="),
			LESS_THAN_OR_EQUAL_TO("<="),
			NOT_EQUAL_TO ("!="),
			NOT_EQUAL_TO_WITH_WORD("not ="),
			NOT_EQUAL_TO_WITH_GREAT_THAN_AND_LESS_THAN("<>");

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
		private boolean isAttributeGroupFound = false;
		private boolean isDefaultGroupConstraint = false;
		private String attributeId = null;
		private boolean isTotalGroupApplied = false;
		private ComparisonOperator comparisonOperator = null;


		@Override
		public void visitErrorNode(ErrorNode node) {
			super.visitErrorNode(node);
		}

		@Override
		public void enterSimpleexpressionconstraint(ExpressionConstraintParser.SimpleexpressionconstraintContext ctx) {
			final ExpressionConstraintParser.FocusconceptContext focusconcept = ctx.focusconcept();
			if (focusconcept.wildcard() != null) {
				if (!inAttribute) {
					luceneQuery += ConceptFieldNames.ID + ":*";
				} else {
					luceneQuery += "*";
				}
			} else if (focusconcept.memberof() != null) {
				luceneQuery += ConceptFieldNames.MEMBER_OF + ":" + focusconcept.conceptreference().conceptid().getText();
			} else {
				final String conceptId = focusconcept.conceptreference().conceptid().getText();
				final ExpressionConstraintParser.ConstraintoperatorContext constraintoperator = ctx.constraintoperator();
				if (constraintoperator == null) {
					if (!inAttribute) {
						luceneQuery += ConceptFieldNames.ID + ":";
					}
					luceneQuery += conceptId;
				} else {
					if (constraintoperator.descendantof() != null) {
						if (!inAttribute) {
							luceneQuery += ConceptFieldNames.ANCESTOR + ":" + conceptId;
						} else {
							luceneQuery += InternalFunction.ATTRIBUTE_DESCENDANT_OF + "(" + conceptId + ")";
						}
					} else if (constraintoperator.descendantorselfof() != null) {
						if (!inAttribute) {
							luceneQuery += "(" + ConceptFieldNames.ID + ":" + conceptId + " OR " + ConceptFieldNames.ANCESTOR + ":" + conceptId + ")";
						} else {
							luceneQuery += InternalFunction.ATTRIBUTE_DESCENDANT_OR_SELF_OF + "(" + conceptId + ")";
						}
					} else if (constraintoperator.ancestororselfof() != null) {
						if (!inAttribute) {
							luceneQuery += InternalFunction.ANCESTOR_OR_SELF_OF + "(" + conceptId + ")";
						} else {
							luceneQuery += InternalFunction.ATTRIBUTE_ANCESTOR_OR_SELF_OF + "(" + conceptId + ")";
						}
					} else if (constraintoperator.ancestorof() != null) {
						if (!inAttribute) {
							luceneQuery += InternalFunction.ANCESTOR_OF + "(" + conceptId + ")";
						} else {
							luceneQuery += InternalFunction.ATTRIBUTE_ANCESTOR_OF + "(" + conceptId + ")";
						}
					}
				}
			}
		}

		@Override
		public void enterRefinement(ExpressionConstraintParser.RefinementContext ctx) {
			luceneQuery += " AND ";
		}

		@Override
		public void enterAttribute(ExpressionConstraintParser.AttributeContext ctx) {
			final ExpressionConstraintParser.ConceptreferenceContext conceptreference = ctx.attributename().conceptreference();
			if (conceptreference != null) {
				attributeId = conceptreference.conceptid().getText();
			}
			inAttribute = true;
		}

		@Override
		public void exitAttribute(ExpressionConstraintParser.AttributeContext ctx) {
			inAttribute = false;
		}

		@Override
		public void enterAttributename(ExpressionConstraintParser.AttributenameContext ctx) {
			final ExpressionConstraintParser.ConceptreferenceContext conceptreference = ctx.conceptreference();
			if (conceptreference != null) {
				luceneQuery += conceptreference.conceptid().getText();
			} else {
				throwUnsupported("wildcard attributeName");
			}
		}

		@Override
		public void enterExpressioncomparisonoperator(ExpressionConstraintParser.ExpressioncomparisonoperatorContext ctx) {
			if (ctx.getText().equals("=")) {
				luceneQuery += ":";
			} else {
				throwUnsupported("not-equal expressionComparisonOperator");
			}
		}

		@Override
		public void enterConjunction(ExpressionConstraintParser.ConjunctionContext ctx) {
			luceneQuery += " AND ";
		}

		@Override
		public void enterDisjunction(ExpressionConstraintParser.DisjunctionContext ctx) {
			luceneQuery += " OR ";
		}

		@Override
		public void enterExclusion(ExpressionConstraintParser.ExclusionContext ctx) {
			luceneQuery += " NOT ";
		}

		@Override
		public void enterSubexpressionconstraint(ExpressionConstraintParser.SubexpressionconstraintContext ctx) {
			addLeftParenthesisIfNotNull(ctx.LEFT_PAREN());
		}

		@Override
		public void exitSubexpressionconstraint(ExpressionConstraintParser.SubexpressionconstraintContext ctx) {
			addRightParenthesisIfNotNull(ctx.RIGHT_PAREN());
		}

		@Override
		public void enterSubrefinement(ExpressionConstraintParser.SubrefinementContext ctx) {
			addLeftParenthesisIfNotNull(ctx.LEFT_PAREN());
		}

		@Override
		public void exitSubrefinement(ExpressionConstraintParser.SubrefinementContext ctx) {
			addRightParenthesisIfNotNull(ctx.RIGHT_PAREN());
		}

		@Override
		public void enterSubattributeset(ExpressionConstraintParser.SubattributesetContext ctx) {
			addLeftParenthesisIfNotNull(ctx.LEFT_PAREN());
		}

		@Override
		public void enterExpressionconstraintvalue(ExpressionConstraintParser.ExpressionconstraintvalueContext ctx) {
			addLeftParenthesisIfNotNull(ctx.LEFT_PAREN());
		}

		@Override
		public void exitExpressionconstraintvalue(ExpressionConstraintParser.ExpressionconstraintvalueContext ctx) {
			addRightParenthesisIfNotNull(ctx.RIGHT_PAREN());
		}

		@Override
		public void exitSubattributeset(ExpressionConstraintParser.SubattributesetContext ctx) {
			addRightParenthesisIfNotNull(ctx.RIGHT_PAREN());
		}

		private void addLeftParenthesisIfNotNull(TerminalNode terminalNode) {
			if (terminalNode != null) {
				luceneQuery += " ( ";
			}
		}

		private void addRightParenthesisIfNotNull(TerminalNode terminalNode) {
			if (terminalNode != null) {
				luceneQuery += " ) ";
			}
		}
		
		@Override
		public void enterCardinality(ExpressionConstraintParser.CardinalityContext ctx) {
			String text = ctx.getText();
			String range = "";
			String regex = "\\[*..*\\]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			if (matcher.matches()) {
				if (isAttributeGroupFound) {
					if (!isTotalGroupApplied && isDefaultGroupConstraint) {
						range = attributeId + ConceptFieldNames.TOTAL_GROUPS + ":" + "[1 TO *]";
						isTotalGroupApplied = true;
						luceneQuery += range + " AND ";
						range = "";
					} 
					if (!isTotalGroupApplied && !isDefaultGroupConstraint) {
						range = attributeId + ConceptFieldNames.TOTAL_GROUPS;
						isTotalGroupApplied = true;
					} else {
						range +=  attributeId + ConceptFieldNames.GROUP_CARDINALITY;
					}
				} else {
					range = attributeId + ConceptFieldNames.CARDINALITY;
				}
				text = text.replace("..", " TO ");
				range += ":" + text;
			} else {
				range = text;
			}
			luceneQuery += range + " AND ";
		}

		@Override
		public void enterAttributegroup(ExpressionConstraintParser.AttributegroupContext ctx) {
			isAttributeGroupFound = true;
			if (!ctx.getText().startsWith("[")) {
				isDefaultGroupConstraint = true;
			} 
		}
		
		@Override
		public void exitAttributegroup(ExpressionConstraintParser.AttributegroupContext ctx) {
			if (luceneQuery.contains("null")) {
				luceneQuery = luceneQuery.replace("null", attributeId);
			}
		}

		@Override
		public void enterStringcomparisonoperator(ExpressionConstraintParser.StringcomparisonoperatorContext ctx) {
			comparisonOperator = ComparisonOperator.fromText(ctx.getText()).get();
			if (EQUAL_TO == comparisonOperator) {
				luceneQuery += ":";
			} else if (NOT_EQUAL_TO == comparisonOperator
					|| NOT_EQUAL_TO_WITH_WORD == comparisonOperator
					|| ComparisonOperator.NOT_EQUAL_TO_WITH_GREAT_THAN_AND_LESS_THAN == comparisonOperator) {
				luceneQuery += " NOT ";
			} else {
				throw new IllegalArgumentException(String.format("Invalid comparison operator %s for String", ctx.getText()));
			}
		}

		@Override
		public void enterStringvalue(ExpressionConstraintParser.StringvalueContext ctx) {
			luceneQuery += ctx.getText();

		}

		@Override
		public void enterNumericcomparisonoperator(ExpressionConstraintParser.NumericcomparisonoperatorContext ctx) {
			comparisonOperator = ComparisonOperator.fromText(ctx.getText()).get();
		}

		@Override
		public void enterNumericvalue(ExpressionConstraintParser.NumericvalueContext ctx) {
			String value = ctx.getText().startsWith("#") ? ctx.getText().substring(1) : ctx.getText();
			if (EQUAL_TO == comparisonOperator) {
				luceneQuery += ":" + value;
			} else if (GREAT_THAN_OR_EQUAL_TO == comparisonOperator) {
				luceneQuery += ":[" + value + " TO *]";
			} else if (GREAT_THAN == comparisonOperator) {
				luceneQuery += ":{" + value + " TO *}";
			} else if (LESS_THAN == comparisonOperator) {
				luceneQuery += ":{* TO " + value + "}";
			} else if (LESS_THAN_OR_EQUAL_TO == comparisonOperator) {
				luceneQuery += ":[* TO " + value + "]";
			} else if (NOT_EQUAL_TO == comparisonOperator || NOT_EQUAL_TO_WITH_WORD == comparisonOperator
					|| NOT_EQUAL_TO_WITH_GREAT_THAN_AND_LESS_THAN == comparisonOperator ) {
				luceneQuery += " NOT " + value;
			}
		}

		@Override
		public void enterReverseflag(ExpressionConstraintParser.ReverseflagContext ctx) {
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
