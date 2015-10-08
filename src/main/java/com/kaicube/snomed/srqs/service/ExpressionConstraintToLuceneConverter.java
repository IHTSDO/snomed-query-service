package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.parser.secl.ExpressionConstraintBaseListener;
import com.kaicube.snomed.srqs.parser.secl.ExpressionConstraintLexer;
import com.kaicube.snomed.srqs.parser.secl.ExpressionConstraintParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

public class ExpressionConstraintToLuceneConverter {

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

		@Override
		public void visitErrorNode(ErrorNode node) {
			super.visitErrorNode(node);
		}

		@Override
		public void enterSimpleexpressionconstraint(ExpressionConstraintParser.SimpleexpressionconstraintContext ctx) {
			final ExpressionConstraintParser.FocusconceptContext focusconcept = ctx.focusconcept();
			if (focusconcept.wildcard() != null) {
				if (!inAttribute) {
					luceneQuery += Concept.ID + ":*";
				} else {
					luceneQuery += "*";
				}
			} else if (focusconcept.memberof() != null) {
				luceneQuery += Concept.MEMBER_OF + ":" + focusconcept.conceptreference().conceptid().getText();
			} else {
				final String conceptId = focusconcept.conceptreference().conceptid().getText();
				final ExpressionConstraintParser.ConstraintoperatorContext constraintoperator = ctx.constraintoperator();
				if (constraintoperator == null) {
					if (!inAttribute) {
						luceneQuery += Concept.ID + ":";
					}
					luceneQuery += conceptId;
				} else {
					if (inAttribute) {
						throwUnsupported("constraintOperator of attribute value");
					}
					if (constraintoperator.descendantof() != null) {
						luceneQuery += Concept.ANCESTOR + ":" + conceptId;
					} else if (constraintoperator.descendantorselfof() != null) {
						luceneQuery += "(" + Concept.ID + ":" + conceptId + " OR " + Concept.ANCESTOR + ":" + conceptId + ")";
					} else if (constraintoperator.ancestororselfof() != null) {
						luceneQuery += "ancestorOrSelfOf(" + conceptId + ")";
					} else if (constraintoperator.ancestorof() != null) {
						luceneQuery += "ancestorOf(" + conceptId + ")";
					}
				}
			}
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
				luceneQuery += " AND " + conceptreference.conceptid().getText();
			} else {
				throwUnsupported("wildcard attributeName");
			}
		}

		@Override
		public void enterExpressioncomparisonoperator(ExpressionConstraintParser.ExpressioncomparisonoperatorContext ctx) {
			luceneQuery += ctx.getText().equals("=") ? ":" : ":-";
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
		public void enterExpressionconstraintvalue(ExpressionConstraintParser.ExpressionconstraintvalueContext ctx) {
//			elQuery.setAttributeValue(ctx.getPayload().getText());
		}

		// Unsupported enter methods below this line

//		@Override
//		public void enterCompoundexpressionconstraint(ExpressionConstraintParser.CompoundexpressionconstraintContext ctx) {
//			luceneQuery += " AND ";
//		}

		@Override
		public void enterConjunctionrefinementset(ExpressionConstraintParser.ConjunctionrefinementsetContext ctx) {
			throwUnsupported("conjunctionRefinementSet");
		}

		@Override
		public void enterDisjunctionrefinementset(ExpressionConstraintParser.DisjunctionrefinementsetContext ctx) {
			throwUnsupported("disjunctionRefinementSet");
		}

		@Override
		public void enterConjunctionattributeset(ExpressionConstraintParser.ConjunctionattributesetContext ctx) {
			throwUnsupported("conjunctionAttributeSet");
		}

		@Override
		public void enterDisjunctionattributeset(ExpressionConstraintParser.DisjunctionattributesetContext ctx) {
			throwUnsupported("disjunctionAttributeSet");
		}

		@Override
		public void enterStringcomparisonoperator(ExpressionConstraintParser.StringcomparisonoperatorContext ctx) {
			throwUnsupported("stringComparisonOperator");
		}

		@Override
		public void enterNumericcomparisonoperator(ExpressionConstraintParser.NumericcomparisonoperatorContext ctx) {
			throwUnsupported("numericComparisonOperator");
		}

		private void throwUnsupported() {
			throw new UnsupportedOperationException("This expression is not currently supported, please use a simpleExpressionConstraint.");
		}

		private void throwUnsupported(String feature) {
			throw new UnsupportedOperationException(feature + " is not currently supported.");
		}

		public String getLuceneQuery() {
			return luceneQuery;
		}
	}

}
