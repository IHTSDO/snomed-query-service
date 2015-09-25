package com.kaicube.snomed.srqs.parser.secl;// Generated from ExpressionConstraint.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionConstraintParser}.
 */
public interface ExpressionConstraintListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#expressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterExpressionconstraint(ExpressionConstraintParser.ExpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#expressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitExpressionconstraint(ExpressionConstraintParser.ExpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#simpleexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterSimpleexpressionconstraint(ExpressionConstraintParser.SimpleexpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#simpleexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitSimpleexpressionconstraint(ExpressionConstraintParser.SimpleexpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#refinedexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterRefinedexpressionconstraint(ExpressionConstraintParser.RefinedexpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#refinedexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitRefinedexpressionconstraint(ExpressionConstraintParser.RefinedexpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#compoundexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterCompoundexpressionconstraint(ExpressionConstraintParser.CompoundexpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#compoundexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitCompoundexpressionconstraint(ExpressionConstraintParser.CompoundexpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#conjunctionexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterConjunctionexpressionconstraint(ExpressionConstraintParser.ConjunctionexpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#conjunctionexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitConjunctionexpressionconstraint(ExpressionConstraintParser.ConjunctionexpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#disjunctionexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterDisjunctionexpressionconstraint(ExpressionConstraintParser.DisjunctionexpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#disjunctionexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitDisjunctionexpressionconstraint(ExpressionConstraintParser.DisjunctionexpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#exclusionexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterExclusionexpressionconstraint(ExpressionConstraintParser.ExclusionexpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#exclusionexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitExclusionexpressionconstraint(ExpressionConstraintParser.ExclusionexpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#subexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void enterSubexpressionconstraint(ExpressionConstraintParser.SubexpressionconstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#subexpressionconstraint}.
	 * @param ctx the parse tree
	 */
	void exitSubexpressionconstraint(ExpressionConstraintParser.SubexpressionconstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#focusconcept}.
	 * @param ctx the parse tree
	 */
	void enterFocusconcept(ExpressionConstraintParser.FocusconceptContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#focusconcept}.
	 * @param ctx the parse tree
	 */
	void exitFocusconcept(ExpressionConstraintParser.FocusconceptContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#memberof}.
	 * @param ctx the parse tree
	 */
	void enterMemberof(ExpressionConstraintParser.MemberofContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#memberof}.
	 * @param ctx the parse tree
	 */
	void exitMemberof(ExpressionConstraintParser.MemberofContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#conceptreference}.
	 * @param ctx the parse tree
	 */
	void enterConceptreference(ExpressionConstraintParser.ConceptreferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#conceptreference}.
	 * @param ctx the parse tree
	 */
	void exitConceptreference(ExpressionConstraintParser.ConceptreferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#conceptid}.
	 * @param ctx the parse tree
	 */
	void enterConceptid(ExpressionConstraintParser.ConceptidContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#conceptid}.
	 * @param ctx the parse tree
	 */
	void exitConceptid(ExpressionConstraintParser.ConceptidContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ExpressionConstraintParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ExpressionConstraintParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#wildcard}.
	 * @param ctx the parse tree
	 */
	void enterWildcard(ExpressionConstraintParser.WildcardContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#wildcard}.
	 * @param ctx the parse tree
	 */
	void exitWildcard(ExpressionConstraintParser.WildcardContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#constraintoperator}.
	 * @param ctx the parse tree
	 */
	void enterConstraintoperator(ExpressionConstraintParser.ConstraintoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#constraintoperator}.
	 * @param ctx the parse tree
	 */
	void exitConstraintoperator(ExpressionConstraintParser.ConstraintoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#descendantof}.
	 * @param ctx the parse tree
	 */
	void enterDescendantof(ExpressionConstraintParser.DescendantofContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#descendantof}.
	 * @param ctx the parse tree
	 */
	void exitDescendantof(ExpressionConstraintParser.DescendantofContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#descendantorselfof}.
	 * @param ctx the parse tree
	 */
	void enterDescendantorselfof(ExpressionConstraintParser.DescendantorselfofContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#descendantorselfof}.
	 * @param ctx the parse tree
	 */
	void exitDescendantorselfof(ExpressionConstraintParser.DescendantorselfofContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#ancestorof}.
	 * @param ctx the parse tree
	 */
	void enterAncestorof(ExpressionConstraintParser.AncestorofContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#ancestorof}.
	 * @param ctx the parse tree
	 */
	void exitAncestorof(ExpressionConstraintParser.AncestorofContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#ancestororselfof}.
	 * @param ctx the parse tree
	 */
	void enterAncestororselfof(ExpressionConstraintParser.AncestororselfofContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#ancestororselfof}.
	 * @param ctx the parse tree
	 */
	void exitAncestororselfof(ExpressionConstraintParser.AncestororselfofContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(ExpressionConstraintParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(ExpressionConstraintParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(ExpressionConstraintParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(ExpressionConstraintParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#exclusion}.
	 * @param ctx the parse tree
	 */
	void enterExclusion(ExpressionConstraintParser.ExclusionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#exclusion}.
	 * @param ctx the parse tree
	 */
	void exitExclusion(ExpressionConstraintParser.ExclusionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#refinement}.
	 * @param ctx the parse tree
	 */
	void enterRefinement(ExpressionConstraintParser.RefinementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#refinement}.
	 * @param ctx the parse tree
	 */
	void exitRefinement(ExpressionConstraintParser.RefinementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#conjunctionrefinementset}.
	 * @param ctx the parse tree
	 */
	void enterConjunctionrefinementset(ExpressionConstraintParser.ConjunctionrefinementsetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#conjunctionrefinementset}.
	 * @param ctx the parse tree
	 */
	void exitConjunctionrefinementset(ExpressionConstraintParser.ConjunctionrefinementsetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#disjunctionrefinementset}.
	 * @param ctx the parse tree
	 */
	void enterDisjunctionrefinementset(ExpressionConstraintParser.DisjunctionrefinementsetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#disjunctionrefinementset}.
	 * @param ctx the parse tree
	 */
	void exitDisjunctionrefinementset(ExpressionConstraintParser.DisjunctionrefinementsetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#subrefinement}.
	 * @param ctx the parse tree
	 */
	void enterSubrefinement(ExpressionConstraintParser.SubrefinementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#subrefinement}.
	 * @param ctx the parse tree
	 */
	void exitSubrefinement(ExpressionConstraintParser.SubrefinementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#attributeset}.
	 * @param ctx the parse tree
	 */
	void enterAttributeset(ExpressionConstraintParser.AttributesetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#attributeset}.
	 * @param ctx the parse tree
	 */
	void exitAttributeset(ExpressionConstraintParser.AttributesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#conjunctionattributeset}.
	 * @param ctx the parse tree
	 */
	void enterConjunctionattributeset(ExpressionConstraintParser.ConjunctionattributesetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#conjunctionattributeset}.
	 * @param ctx the parse tree
	 */
	void exitConjunctionattributeset(ExpressionConstraintParser.ConjunctionattributesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#disjunctionattributeset}.
	 * @param ctx the parse tree
	 */
	void enterDisjunctionattributeset(ExpressionConstraintParser.DisjunctionattributesetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#disjunctionattributeset}.
	 * @param ctx the parse tree
	 */
	void exitDisjunctionattributeset(ExpressionConstraintParser.DisjunctionattributesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#subattributeset}.
	 * @param ctx the parse tree
	 */
	void enterSubattributeset(ExpressionConstraintParser.SubattributesetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#subattributeset}.
	 * @param ctx the parse tree
	 */
	void exitSubattributeset(ExpressionConstraintParser.SubattributesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#attributegroup}.
	 * @param ctx the parse tree
	 */
	void enterAttributegroup(ExpressionConstraintParser.AttributegroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#attributegroup}.
	 * @param ctx the parse tree
	 */
	void exitAttributegroup(ExpressionConstraintParser.AttributegroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(ExpressionConstraintParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(ExpressionConstraintParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#cardinality}.
	 * @param ctx the parse tree
	 */
	void enterCardinality(ExpressionConstraintParser.CardinalityContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#cardinality}.
	 * @param ctx the parse tree
	 */
	void exitCardinality(ExpressionConstraintParser.CardinalityContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#to}.
	 * @param ctx the parse tree
	 */
	void enterTo(ExpressionConstraintParser.ToContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#to}.
	 * @param ctx the parse tree
	 */
	void exitTo(ExpressionConstraintParser.ToContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#many}.
	 * @param ctx the parse tree
	 */
	void enterMany(ExpressionConstraintParser.ManyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#many}.
	 * @param ctx the parse tree
	 */
	void exitMany(ExpressionConstraintParser.ManyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#reverseflag}.
	 * @param ctx the parse tree
	 */
	void enterReverseflag(ExpressionConstraintParser.ReverseflagContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#reverseflag}.
	 * @param ctx the parse tree
	 */
	void exitReverseflag(ExpressionConstraintParser.ReverseflagContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#attributeoperator}.
	 * @param ctx the parse tree
	 */
	void enterAttributeoperator(ExpressionConstraintParser.AttributeoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#attributeoperator}.
	 * @param ctx the parse tree
	 */
	void exitAttributeoperator(ExpressionConstraintParser.AttributeoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#attributename}.
	 * @param ctx the parse tree
	 */
	void enterAttributename(ExpressionConstraintParser.AttributenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#attributename}.
	 * @param ctx the parse tree
	 */
	void exitAttributename(ExpressionConstraintParser.AttributenameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#expressionconstraintvalue}.
	 * @param ctx the parse tree
	 */
	void enterExpressionconstraintvalue(ExpressionConstraintParser.ExpressionconstraintvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#expressionconstraintvalue}.
	 * @param ctx the parse tree
	 */
	void exitExpressionconstraintvalue(ExpressionConstraintParser.ExpressionconstraintvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#expressioncomparisonoperator}.
	 * @param ctx the parse tree
	 */
	void enterExpressioncomparisonoperator(ExpressionConstraintParser.ExpressioncomparisonoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#expressioncomparisonoperator}.
	 * @param ctx the parse tree
	 */
	void exitExpressioncomparisonoperator(ExpressionConstraintParser.ExpressioncomparisonoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#numericcomparisonoperator}.
	 * @param ctx the parse tree
	 */
	void enterNumericcomparisonoperator(ExpressionConstraintParser.NumericcomparisonoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#numericcomparisonoperator}.
	 * @param ctx the parse tree
	 */
	void exitNumericcomparisonoperator(ExpressionConstraintParser.NumericcomparisonoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#stringcomparisonoperator}.
	 * @param ctx the parse tree
	 */
	void enterStringcomparisonoperator(ExpressionConstraintParser.StringcomparisonoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#stringcomparisonoperator}.
	 * @param ctx the parse tree
	 */
	void exitStringcomparisonoperator(ExpressionConstraintParser.StringcomparisonoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#numericvalue}.
	 * @param ctx the parse tree
	 */
	void enterNumericvalue(ExpressionConstraintParser.NumericvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#numericvalue}.
	 * @param ctx the parse tree
	 */
	void exitNumericvalue(ExpressionConstraintParser.NumericvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#stringvalue}.
	 * @param ctx the parse tree
	 */
	void enterStringvalue(ExpressionConstraintParser.StringvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#stringvalue}.
	 * @param ctx the parse tree
	 */
	void exitStringvalue(ExpressionConstraintParser.StringvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#integervalue}.
	 * @param ctx the parse tree
	 */
	void enterIntegervalue(ExpressionConstraintParser.IntegervalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#integervalue}.
	 * @param ctx the parse tree
	 */
	void exitIntegervalue(ExpressionConstraintParser.IntegervalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#decimalvalue}.
	 * @param ctx the parse tree
	 */
	void enterDecimalvalue(ExpressionConstraintParser.DecimalvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#decimalvalue}.
	 * @param ctx the parse tree
	 */
	void exitDecimalvalue(ExpressionConstraintParser.DecimalvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#nonnegativeintegervalue}.
	 * @param ctx the parse tree
	 */
	void enterNonnegativeintegervalue(ExpressionConstraintParser.NonnegativeintegervalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#nonnegativeintegervalue}.
	 * @param ctx the parse tree
	 */
	void exitNonnegativeintegervalue(ExpressionConstraintParser.NonnegativeintegervalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#sctid}.
	 * @param ctx the parse tree
	 */
	void enterSctid(ExpressionConstraintParser.SctidContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#sctid}.
	 * @param ctx the parse tree
	 */
	void exitSctid(ExpressionConstraintParser.SctidContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#ws}.
	 * @param ctx the parse tree
	 */
	void enterWs(ExpressionConstraintParser.WsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#ws}.
	 * @param ctx the parse tree
	 */
	void exitWs(ExpressionConstraintParser.WsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#mws}.
	 * @param ctx the parse tree
	 */
	void enterMws(ExpressionConstraintParser.MwsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#mws}.
	 * @param ctx the parse tree
	 */
	void exitMws(ExpressionConstraintParser.MwsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#sp}.
	 * @param ctx the parse tree
	 */
	void enterSp(ExpressionConstraintParser.SpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#sp}.
	 * @param ctx the parse tree
	 */
	void exitSp(ExpressionConstraintParser.SpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#htab}.
	 * @param ctx the parse tree
	 */
	void enterHtab(ExpressionConstraintParser.HtabContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#htab}.
	 * @param ctx the parse tree
	 */
	void exitHtab(ExpressionConstraintParser.HtabContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#cr}.
	 * @param ctx the parse tree
	 */
	void enterCr(ExpressionConstraintParser.CrContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#cr}.
	 * @param ctx the parse tree
	 */
	void exitCr(ExpressionConstraintParser.CrContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#lf}.
	 * @param ctx the parse tree
	 */
	void enterLf(ExpressionConstraintParser.LfContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#lf}.
	 * @param ctx the parse tree
	 */
	void exitLf(ExpressionConstraintParser.LfContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#qm}.
	 * @param ctx the parse tree
	 */
	void enterQm(ExpressionConstraintParser.QmContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#qm}.
	 * @param ctx the parse tree
	 */
	void exitQm(ExpressionConstraintParser.QmContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#bs}.
	 * @param ctx the parse tree
	 */
	void enterBs(ExpressionConstraintParser.BsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#bs}.
	 * @param ctx the parse tree
	 */
	void exitBs(ExpressionConstraintParser.BsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#digit}.
	 * @param ctx the parse tree
	 */
	void enterDigit(ExpressionConstraintParser.DigitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#digit}.
	 * @param ctx the parse tree
	 */
	void exitDigit(ExpressionConstraintParser.DigitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#zero}.
	 * @param ctx the parse tree
	 */
	void enterZero(ExpressionConstraintParser.ZeroContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#zero}.
	 * @param ctx the parse tree
	 */
	void exitZero(ExpressionConstraintParser.ZeroContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#digitnonzero}.
	 * @param ctx the parse tree
	 */
	void enterDigitnonzero(ExpressionConstraintParser.DigitnonzeroContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#digitnonzero}.
	 * @param ctx the parse tree
	 */
	void exitDigitnonzero(ExpressionConstraintParser.DigitnonzeroContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#nonwsnonpipe}.
	 * @param ctx the parse tree
	 */
	void enterNonwsnonpipe(ExpressionConstraintParser.NonwsnonpipeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#nonwsnonpipe}.
	 * @param ctx the parse tree
	 */
	void exitNonwsnonpipe(ExpressionConstraintParser.NonwsnonpipeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#anynonescapedchar}.
	 * @param ctx the parse tree
	 */
	void enterAnynonescapedchar(ExpressionConstraintParser.AnynonescapedcharContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#anynonescapedchar}.
	 * @param ctx the parse tree
	 */
	void exitAnynonescapedchar(ExpressionConstraintParser.AnynonescapedcharContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#escapedchar}.
	 * @param ctx the parse tree
	 */
	void enterEscapedchar(ExpressionConstraintParser.EscapedcharContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#escapedchar}.
	 * @param ctx the parse tree
	 */
	void exitEscapedchar(ExpressionConstraintParser.EscapedcharContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#utf8_2}.
	 * @param ctx the parse tree
	 */
	void enterUtf8_2(ExpressionConstraintParser.Utf8_2Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#utf8_2}.
	 * @param ctx the parse tree
	 */
	void exitUtf8_2(ExpressionConstraintParser.Utf8_2Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#utf8_3}.
	 * @param ctx the parse tree
	 */
	void enterUtf8_3(ExpressionConstraintParser.Utf8_3Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#utf8_3}.
	 * @param ctx the parse tree
	 */
	void exitUtf8_3(ExpressionConstraintParser.Utf8_3Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#utf8_4}.
	 * @param ctx the parse tree
	 */
	void enterUtf8_4(ExpressionConstraintParser.Utf8_4Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#utf8_4}.
	 * @param ctx the parse tree
	 */
	void exitUtf8_4(ExpressionConstraintParser.Utf8_4Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionConstraintParser#utf8_tail}.
	 * @param ctx the parse tree
	 */
	void enterUtf8_tail(ExpressionConstraintParser.Utf8_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionConstraintParser#utf8_tail}.
	 * @param ctx the parse tree
	 */
	void exitUtf8_tail(ExpressionConstraintParser.Utf8_tailContext ctx);
}