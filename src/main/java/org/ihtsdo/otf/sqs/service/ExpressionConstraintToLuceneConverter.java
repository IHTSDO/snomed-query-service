package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.parser.secl.ExpressionConstraintBaseListener;
import org.ihtsdo.otf.sqs.parser.secl.ExpressionConstraintLexer;
import org.ihtsdo.otf.sqs.parser.secl.ExpressionConstraintParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

		private String luceneQuery = "";
		private boolean inAttribute;
		private Set<ParserRuleContext> bracesToClose = new HashSet<>();

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
			if (ctx.refinedexpressionconstraint() != null || (ctx.compoundexpressionconstraint() != null && ctx.compoundexpressionconstraint().getText().contains(":"))) {
				throw new UnsupportedOperationException("Within an expressionConstraintValue refinedExpressionConstraint is not currently supported.");
			}
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

		// Unsupported enter methods below this line

		@Override
		public void enterCardinality(ExpressionConstraintParser.CardinalityContext ctx) {
			throwUnsupported("cardinality");
		}

		@Override
		public void enterAttributegroup(ExpressionConstraintParser.AttributegroupContext ctx) {
			throwUnsupported("attributeGroup");
		}

		@Override
		public void enterStringcomparisonoperator(ExpressionConstraintParser.StringcomparisonoperatorContext ctx) {
			throwUnsupported("stringComparisonOperator");
		}

		@Override
		public void enterNumericcomparisonoperator(ExpressionConstraintParser.NumericcomparisonoperatorContext ctx) {
			throwUnsupported("numericComparisonOperator");
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
