package org.ihtsdo.otf.sqs.parser.secl;// Generated from ExpressionConstraint.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionConstraintParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TAB=1, LF=2, CR=3, SPACE=4, EXCLAMATION=5, QUOTE=6, POUND=7, DOLLAR=8, 
		PERCENT=9, AMPERSAND=10, APOSTROPHE=11, LEFT_PAREN=12, RIGHT_PAREN=13, 
		ASTERISK=14, PLUS=15, COMMA=16, DASH=17, PERIOD=18, SLASH=19, ZERO=20, 
		ONE=21, TWO=22, THREE=23, FOUR=24, FIVE=25, SIX=26, SEVEN=27, EIGHT=28, 
		NINE=29, COLON=30, SEMICOLON=31, LESS_THAN=32, EQUALS=33, GREATER_THAN=34, 
		QUESTION=35, AT=36, CAP_A=37, CAP_B=38, CAP_C=39, CAP_D=40, CAP_E=41, 
		CAP_F=42, CAP_G=43, CAP_H=44, CAP_I=45, CAP_J=46, CAP_K=47, CAP_L=48, 
		CAP_M=49, CAP_N=50, CAP_O=51, CAP_P=52, CAP_Q=53, CAP_R=54, CAP_S=55, 
		CAP_T=56, CAP_U=57, CAP_V=58, CAP_W=59, CAP_X=60, CAP_Y=61, CAP_Z=62, 
		LEFT_BRACE=63, BACKSLASH=64, RIGHT_BRACE=65, CARAT=66, UNDERSCORE=67, 
		ACCENT=68, A=69, B=70, C=71, D=72, E=73, F=74, G=75, H=76, I=77, J=78, 
		K=79, L=80, M=81, N=82, O=83, P=84, Q=85, R=86, S=87, T=88, U=89, V=90, 
		W=91, X=92, Y=93, Z=94, LEFT_CURLY_BRACE=95, PIPE=96, RIGHT_CURLY_BRACE=97, 
		TILDE=98, U_0080=99, U_0081=100, U_0082=101, U_0083=102, U_0084=103, U_0085=104, 
		U_0086=105, U_0087=106, U_0088=107, U_0089=108, U_008A=109, U_008B=110, 
		U_008C=111, U_008D=112, U_008E=113, U_008F=114, U_0090=115, U_0091=116, 
		U_0092=117, U_0093=118, U_0094=119, U_0095=120, U_0096=121, U_0097=122, 
		U_0098=123, U_0099=124, U_009A=125, U_009B=126, U_009C=127, U_009D=128, 
		U_009E=129, U_009F=130, U_00A0=131, U_00A1=132, U_00A2=133, U_00A3=134, 
		U_00A4=135, U_00A5=136, U_00A6=137, U_00A7=138, U_00A8=139, U_00A9=140, 
		U_00AA=141, U_00AB=142, U_00AC=143, U_00AD=144, U_00AE=145, U_00AF=146, 
		U_00B0=147, U_00B1=148, U_00B2=149, U_00B3=150, U_00B4=151, U_00B5=152, 
		U_00B6=153, U_00B7=154, U_00B8=155, U_00B9=156, U_00BA=157, U_00BB=158, 
		U_00BC=159, U_00BD=160, U_00BE=161, U_00BF=162, U_00C2=163, U_00C3=164, 
		U_00C4=165, U_00C5=166, U_00C6=167, U_00C7=168, U_00C8=169, U_00C9=170, 
		U_00CA=171, U_00CB=172, U_00CC=173, U_00CD=174, U_00CE=175, U_00CF=176, 
		U_00D0=177, U_00D1=178, U_00D2=179, U_00D3=180, U_00D4=181, U_00D5=182, 
		U_00D6=183, U_00D7=184, U_00D8=185, U_00D9=186, U_00DA=187, U_00DB=188, 
		U_00DC=189, U_00DD=190, U_00DE=191, U_00DF=192, U_00E0=193, U_00E1=194, 
		U_00E2=195, U_00E3=196, U_00E4=197, U_00E5=198, U_00E6=199, U_00E7=200, 
		U_00E8=201, U_00E9=202, U_00EA=203, U_00EB=204, U_00EC=205, U_00ED=206, 
		U_00EE=207, U_00EF=208, U_00F0=209, U_00F1=210, U_00F2=211, U_00F3=212, 
		U_00F4=213;
	public static final int
		RULE_expressionconstraint = 0, RULE_simpleexpressionconstraint = 1, RULE_refinedexpressionconstraint = 2, 
		RULE_compoundexpressionconstraint = 3, RULE_conjunctionexpressionconstraint = 4, 
		RULE_disjunctionexpressionconstraint = 5, RULE_exclusionexpressionconstraint = 6, 
		RULE_subexpressionconstraint = 7, RULE_focusconcept = 8, RULE_memberof = 9, 
		RULE_conceptreference = 10, RULE_conceptid = 11, RULE_term = 12, RULE_wildcard = 13, 
		RULE_constraintoperator = 14, RULE_descendantof = 15, RULE_descendantorselfof = 16, 
		RULE_ancestorof = 17, RULE_ancestororselfof = 18, RULE_conjunction = 19, 
		RULE_disjunction = 20, RULE_exclusion = 21, RULE_refinement = 22, RULE_conjunctionrefinementset = 23, 
		RULE_disjunctionrefinementset = 24, RULE_subrefinement = 25, RULE_attributeset = 26, 
		RULE_conjunctionattributeset = 27, RULE_disjunctionattributeset = 28, 
		RULE_subattributeset = 29, RULE_attributegroup = 30, RULE_attribute = 31, 
		RULE_cardinality = 32, RULE_to = 33, RULE_many = 34, RULE_reverseflag = 35, 
		RULE_attributeoperator = 36, RULE_attributename = 37, RULE_expressionconstraintvalue = 38, 
		RULE_expressioncomparisonoperator = 39, RULE_numericcomparisonoperator = 40, 
		RULE_stringcomparisonoperator = 41, RULE_numericvalue = 42, RULE_stringvalue = 43, 
		RULE_integervalue = 44, RULE_decimalvalue = 45, RULE_nonnegativeintegervalue = 46, 
		RULE_sctid = 47, RULE_ws = 48, RULE_mws = 49, RULE_sp = 50, RULE_htab = 51, 
		RULE_cr = 52, RULE_lf = 53, RULE_qm = 54, RULE_bs = 55, RULE_digit = 56, 
		RULE_zero = 57, RULE_digitnonzero = 58, RULE_nonwsnonpipe = 59, RULE_anynonescapedchar = 60, 
		RULE_escapedchar = 61, RULE_utf8_2 = 62, RULE_utf8_3 = 63, RULE_utf8_4 = 64, 
		RULE_utf8_tail = 65;
	public static final String[] ruleNames = {
		"expressionconstraint", "simpleexpressionconstraint", "refinedexpressionconstraint", 
		"compoundexpressionconstraint", "conjunctionexpressionconstraint", "disjunctionexpressionconstraint", 
		"exclusionexpressionconstraint", "subexpressionconstraint", "focusconcept", 
		"memberof", "conceptreference", "conceptid", "term", "wildcard", "constraintoperator", 
		"descendantof", "descendantorselfof", "ancestorof", "ancestororselfof", 
		"conjunction", "disjunction", "exclusion", "refinement", "conjunctionrefinementset", 
		"disjunctionrefinementset", "subrefinement", "attributeset", "conjunctionattributeset", 
		"disjunctionattributeset", "subattributeset", "attributegroup", "attribute", 
		"cardinality", "to", "many", "reverseflag", "attributeoperator", "attributename", 
		"expressionconstraintvalue", "expressioncomparisonoperator", "numericcomparisonoperator", 
		"stringcomparisonoperator", "numericvalue", "stringvalue", "integervalue", 
		"decimalvalue", "nonnegativeintegervalue", "sctid", "ws", "mws", "sp", 
		"htab", "cr", "lf", "qm", "bs", "digit", "zero", "digitnonzero", "nonwsnonpipe", 
		"anynonescapedchar", "escapedchar", "utf8_2", "utf8_3", "utf8_4", "utf8_tail"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\\u0009'", "'\\u000A'", "'\\u000D'", "' '", "'!'", "'\"'", "'#'", 
		"'$'", "'%'", "'&'", "'''", "'('", "')'", "'*'", "'+'", "','", "'-'", 
		"'.'", "'/'", "'0'", "'1'", "'2'", "'3'", "'4'", "'5'", "'6'", "'7'", 
		"'8'", "'9'", "':'", "';'", "'<'", "'='", "'>'", "'?'", "'@'", "'A'", 
		"'B'", "'C'", "'D'", "'E'", "'F'", "'G'", "'H'", "'I'", "'J'", "'K'", 
		"'L'", "'M'", "'N'", "'O'", "'P'", "'Q'", "'R'", "'S'", "'T'", "'U'", 
		"'V'", "'W'", "'X'", "'Y'", "'Z'", "'['", "'\\'", "']'", "'^'", "'_'", 
		"'`'", "'a'", "'b'", "'c'", "'d'", "'e'", "'f'", "'g'", "'h'", "'i'", 
		"'j'", "'k'", "'l'", "'m'", "'n'", "'o'", "'p'", "'q'", "'r'", "'s'", 
		"'t'", "'u'", "'v'", "'w'", "'x'", "'y'", "'z'", "'{'", "'|'", "'}'", 
		"'~'", "'\\u0080'", "'\\u0081'", "'\\u0082'", "'\\u0083'", "'\\u0084'", 
		"'\\u0085'", "'\\u0086'", "'\\u0087'", "'\\u0088'", "'\\u0089'", "'\\u008A'", 
		"'\\u008B'", "'\\u008C'", "'\\u008D'", "'\\u008E'", "'\\u008F'", "'\\u0090'", 
		"'\\u0091'", "'\\u0092'", "'\\u0093'", "'\\u0094'", "'\\u0095'", "'\\u0096'", 
		"'\\u0097'", "'\\u0098'", "'\\u0099'", "'\\u009A'", "'\\u009B'", "'\\u009C'", 
		"'\\u009D'", "'\\u009E'", "'\\u009F'", "'\\u00A0'", "'\\u00A1'", "'\\u00A2'", 
		"'\\u00A3'", "'\\u00A4'", "'\\u00A5'", "'\\u00A6'", "'\\u00A7'", "'\\u00A8'", 
		"'\\u00A9'", "'\\u00AA'", "'\\u00AB'", "'\\u00AC'", "'\\u00AD'", "'\\u00AE'", 
		"'\\u00AF'", "'\\u00B0'", "'\\u00B1'", "'\\u00B2'", "'\\u00B3'", "'\\u00B4'", 
		"'\\u00B5'", "'\\u00B6'", "'\\u00B7'", "'\\u00B8'", "'\\u00B9'", "'\\u00BA'", 
		"'\\u00BB'", "'\\u00BC'", "'\\u00BD'", "'\\u00BE'", "'\\u00BF'", "'\\u00C2'", 
		"'\\u00C3'", "'\\u00C4'", "'\\u00C5'", "'\\u00C6'", "'\\u00C7'", "'\\u00C8'", 
		"'\\u00C9'", "'\\u00CA'", "'\\u00CB'", "'\\u00CC'", "'\\u00CD'", "'\\u00CE'", 
		"'\\u00CF'", "'\\u00D0'", "'\\u00D1'", "'\\u00D2'", "'\\u00D3'", "'\\u00D4'", 
		"'\\u00D5'", "'\\u00D6'", "'\\u00D7'", "'\\u00D8'", "'\\u00D9'", "'\\u00DA'", 
		"'\\u00DB'", "'\\u00DC'", "'\\u00DD'", "'\\u00DE'", "'\\u00DF'", "'\\u00E0'", 
		"'\\u00E1'", "'\\u00E2'", "'\\u00E3'", "'\\u00E4'", "'\\u00E5'", "'\\u00E6'", 
		"'\\u00E7'", "'\\u00E8'", "'\\u00E9'", "'\\u00EA'", "'\\u00EB'", "'\\u00EC'", 
		"'\\u00ED'", "'\\u00EE'", "'\\u00EF'", "'\\u00F0'", "'\\u00F1'", "'\\u00F2'", 
		"'\\u00F3'", "'\\u00F4'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "TAB", "LF", "CR", "SPACE", "EXCLAMATION", "QUOTE", "POUND", "DOLLAR", 
		"PERCENT", "AMPERSAND", "APOSTROPHE", "LEFT_PAREN", "RIGHT_PAREN", "ASTERISK", 
		"PLUS", "COMMA", "DASH", "PERIOD", "SLASH", "ZERO", "ONE", "TWO", "THREE", 
		"FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "COLON", "SEMICOLON", 
		"LESS_THAN", "EQUALS", "GREATER_THAN", "QUESTION", "AT", "CAP_A", "CAP_B", 
		"CAP_C", "CAP_D", "CAP_E", "CAP_F", "CAP_G", "CAP_H", "CAP_I", "CAP_J", 
		"CAP_K", "CAP_L", "CAP_M", "CAP_N", "CAP_O", "CAP_P", "CAP_Q", "CAP_R", 
		"CAP_S", "CAP_T", "CAP_U", "CAP_V", "CAP_W", "CAP_X", "CAP_Y", "CAP_Z", 
		"LEFT_BRACE", "BACKSLASH", "RIGHT_BRACE", "CARAT", "UNDERSCORE", "ACCENT", 
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", 
		"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "LEFT_CURLY_BRACE", 
		"PIPE", "RIGHT_CURLY_BRACE", "TILDE", "U_0080", "U_0081", "U_0082", "U_0083", 
		"U_0084", "U_0085", "U_0086", "U_0087", "U_0088", "U_0089", "U_008A", 
		"U_008B", "U_008C", "U_008D", "U_008E", "U_008F", "U_0090", "U_0091", 
		"U_0092", "U_0093", "U_0094", "U_0095", "U_0096", "U_0097", "U_0098", 
		"U_0099", "U_009A", "U_009B", "U_009C", "U_009D", "U_009E", "U_009F", 
		"U_00A0", "U_00A1", "U_00A2", "U_00A3", "U_00A4", "U_00A5", "U_00A6", 
		"U_00A7", "U_00A8", "U_00A9", "U_00AA", "U_00AB", "U_00AC", "U_00AD", 
		"U_00AE", "U_00AF", "U_00B0", "U_00B1", "U_00B2", "U_00B3", "U_00B4", 
		"U_00B5", "U_00B6", "U_00B7", "U_00B8", "U_00B9", "U_00BA", "U_00BB", 
		"U_00BC", "U_00BD", "U_00BE", "U_00BF", "U_00C2", "U_00C3", "U_00C4", 
		"U_00C5", "U_00C6", "U_00C7", "U_00C8", "U_00C9", "U_00CA", "U_00CB", 
		"U_00CC", "U_00CD", "U_00CE", "U_00CF", "U_00D0", "U_00D1", "U_00D2", 
		"U_00D3", "U_00D4", "U_00D5", "U_00D6", "U_00D7", "U_00D8", "U_00D9", 
		"U_00DA", "U_00DB", "U_00DC", "U_00DD", "U_00DE", "U_00DF", "U_00E0", 
		"U_00E1", "U_00E2", "U_00E3", "U_00E4", "U_00E5", "U_00E6", "U_00E7", 
		"U_00E8", "U_00E9", "U_00EA", "U_00EB", "U_00EC", "U_00ED", "U_00EE", 
		"U_00EF", "U_00F0", "U_00F1", "U_00F2", "U_00F3", "U_00F4"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ExpressionConstraint.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionConstraintParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExpressionconstraintContext extends ParserRuleContext {
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public RefinedexpressionconstraintContext refinedexpressionconstraint() {
			return getRuleContext(RefinedexpressionconstraintContext.class,0);
		}
		public CompoundexpressionconstraintContext compoundexpressionconstraint() {
			return getRuleContext(CompoundexpressionconstraintContext.class,0);
		}
		public SimpleexpressionconstraintContext simpleexpressionconstraint() {
			return getRuleContext(SimpleexpressionconstraintContext.class,0);
		}
		public ExpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterExpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitExpressionconstraint(this);
		}
	}

	public final ExpressionconstraintContext expressionconstraint() throws RecognitionException {
		ExpressionconstraintContext _localctx = new ExpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expressionconstraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			ws();
			setState(136);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(133);
				refinedexpressionconstraint();
				}
				break;
			case 2:
				{
				setState(134);
				compoundexpressionconstraint();
				}
				break;
			case 3:
				{
				setState(135);
				simpleexpressionconstraint();
				}
				break;
			}
			setState(138);
			ws();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleexpressionconstraintContext extends ParserRuleContext {
		public FocusconceptContext focusconcept() {
			return getRuleContext(FocusconceptContext.class,0);
		}
		public ConstraintoperatorContext constraintoperator() {
			return getRuleContext(ConstraintoperatorContext.class,0);
		}
		public WsContext ws() {
			return getRuleContext(WsContext.class,0);
		}
		public SimpleexpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleexpressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterSimpleexpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitSimpleexpressionconstraint(this);
		}
	}

	public final SimpleexpressionconstraintContext simpleexpressionconstraint() throws RecognitionException {
		SimpleexpressionconstraintContext _localctx = new SimpleexpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_simpleexpressionconstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			_la = _input.LA(1);
			if (_la==LESS_THAN || _la==GREATER_THAN) {
				{
				setState(140);
				constraintoperator();
				setState(141);
				ws();
				}
			}

			setState(145);
			focusconcept();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RefinedexpressionconstraintContext extends ParserRuleContext {
		public SimpleexpressionconstraintContext simpleexpressionconstraint() {
			return getRuleContext(SimpleexpressionconstraintContext.class,0);
		}
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public TerminalNode COLON() { return getToken(ExpressionConstraintParser.COLON, 0); }
		public RefinementContext refinement() {
			return getRuleContext(RefinementContext.class,0);
		}
		public RefinedexpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_refinedexpressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterRefinedexpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitRefinedexpressionconstraint(this);
		}
	}

	public final RefinedexpressionconstraintContext refinedexpressionconstraint() throws RecognitionException {
		RefinedexpressionconstraintContext _localctx = new RefinedexpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_refinedexpressionconstraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			simpleexpressionconstraint();
			setState(148);
			ws();
			setState(149);
			match(COLON);
			setState(150);
			ws();
			setState(151);
			refinement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundexpressionconstraintContext extends ParserRuleContext {
		public ConjunctionexpressionconstraintContext conjunctionexpressionconstraint() {
			return getRuleContext(ConjunctionexpressionconstraintContext.class,0);
		}
		public DisjunctionexpressionconstraintContext disjunctionexpressionconstraint() {
			return getRuleContext(DisjunctionexpressionconstraintContext.class,0);
		}
		public ExclusionexpressionconstraintContext exclusionexpressionconstraint() {
			return getRuleContext(ExclusionexpressionconstraintContext.class,0);
		}
		public CompoundexpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundexpressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterCompoundexpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitCompoundexpressionconstraint(this);
		}
	}

	public final CompoundexpressionconstraintContext compoundexpressionconstraint() throws RecognitionException {
		CompoundexpressionconstraintContext _localctx = new CompoundexpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_compoundexpressionconstraint);
		try {
			setState(156);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				conjunctionexpressionconstraint();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				disjunctionexpressionconstraint();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(155);
				exclusionexpressionconstraint();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctionexpressionconstraintContext extends ParserRuleContext {
		public List<SubexpressionconstraintContext> subexpressionconstraint() {
			return getRuleContexts(SubexpressionconstraintContext.class);
		}
		public SubexpressionconstraintContext subexpressionconstraint(int i) {
			return getRuleContext(SubexpressionconstraintContext.class,i);
		}
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public List<ConjunctionContext> conjunction() {
			return getRuleContexts(ConjunctionContext.class);
		}
		public ConjunctionContext conjunction(int i) {
			return getRuleContext(ConjunctionContext.class,i);
		}
		public ConjunctionexpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunctionexpressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterConjunctionexpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitConjunctionexpressionconstraint(this);
		}
	}

	public final ConjunctionexpressionconstraintContext conjunctionexpressionconstraint() throws RecognitionException {
		ConjunctionexpressionconstraintContext _localctx = new ConjunctionexpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_conjunctionexpressionconstraint);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			subexpressionconstraint();
			setState(164); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(159);
					ws();
					setState(160);
					conjunction();
					setState(161);
					ws();
					setState(162);
					subexpressionconstraint();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(166); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjunctionexpressionconstraintContext extends ParserRuleContext {
		public List<SubexpressionconstraintContext> subexpressionconstraint() {
			return getRuleContexts(SubexpressionconstraintContext.class);
		}
		public SubexpressionconstraintContext subexpressionconstraint(int i) {
			return getRuleContext(SubexpressionconstraintContext.class,i);
		}
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public List<DisjunctionContext> disjunction() {
			return getRuleContexts(DisjunctionContext.class);
		}
		public DisjunctionContext disjunction(int i) {
			return getRuleContext(DisjunctionContext.class,i);
		}
		public DisjunctionexpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjunctionexpressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDisjunctionexpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDisjunctionexpressionconstraint(this);
		}
	}

	public final DisjunctionexpressionconstraintContext disjunctionexpressionconstraint() throws RecognitionException {
		DisjunctionexpressionconstraintContext _localctx = new DisjunctionexpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_disjunctionexpressionconstraint);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			subexpressionconstraint();
			setState(174); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(169);
					ws();
					setState(170);
					disjunction();
					setState(171);
					ws();
					setState(172);
					subexpressionconstraint();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(176); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExclusionexpressionconstraintContext extends ParserRuleContext {
		public List<SubexpressionconstraintContext> subexpressionconstraint() {
			return getRuleContexts(SubexpressionconstraintContext.class);
		}
		public SubexpressionconstraintContext subexpressionconstraint(int i) {
			return getRuleContext(SubexpressionconstraintContext.class,i);
		}
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public ExclusionContext exclusion() {
			return getRuleContext(ExclusionContext.class,0);
		}
		public ExclusionexpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusionexpressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterExclusionexpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitExclusionexpressionconstraint(this);
		}
	}

	public final ExclusionexpressionconstraintContext exclusionexpressionconstraint() throws RecognitionException {
		ExclusionexpressionconstraintContext _localctx = new ExclusionexpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_exclusionexpressionconstraint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			subexpressionconstraint();
			setState(179);
			ws();
			setState(180);
			exclusion();
			setState(181);
			ws();
			setState(182);
			subexpressionconstraint();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubexpressionconstraintContext extends ParserRuleContext {
		public SimpleexpressionconstraintContext simpleexpressionconstraint() {
			return getRuleContext(SimpleexpressionconstraintContext.class,0);
		}
		public TerminalNode LEFT_PAREN() { return getToken(ExpressionConstraintParser.LEFT_PAREN, 0); }
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ExpressionConstraintParser.RIGHT_PAREN, 0); }
		public CompoundexpressionconstraintContext compoundexpressionconstraint() {
			return getRuleContext(CompoundexpressionconstraintContext.class,0);
		}
		public RefinedexpressionconstraintContext refinedexpressionconstraint() {
			return getRuleContext(RefinedexpressionconstraintContext.class,0);
		}
		public SubexpressionconstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subexpressionconstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterSubexpressionconstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitSubexpressionconstraint(this);
		}
	}

	public final SubexpressionconstraintContext subexpressionconstraint() throws RecognitionException {
		SubexpressionconstraintContext _localctx = new SubexpressionconstraintContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_subexpressionconstraint);
		try {
			setState(194);
			switch (_input.LA(1)) {
			case ASTERISK:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
			case LESS_THAN:
			case GREATER_THAN:
			case CARAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(184);
				simpleexpressionconstraint();
				}
				break;
			case LEFT_PAREN:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(185);
				match(LEFT_PAREN);
				setState(186);
				ws();
				setState(189);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(187);
					compoundexpressionconstraint();
					}
					break;
				case 2:
					{
					setState(188);
					refinedexpressionconstraint();
					}
					break;
				}
				setState(191);
				ws();
				setState(192);
				match(RIGHT_PAREN);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FocusconceptContext extends ParserRuleContext {
		public ConceptreferenceContext conceptreference() {
			return getRuleContext(ConceptreferenceContext.class,0);
		}
		public WildcardContext wildcard() {
			return getRuleContext(WildcardContext.class,0);
		}
		public MemberofContext memberof() {
			return getRuleContext(MemberofContext.class,0);
		}
		public WsContext ws() {
			return getRuleContext(WsContext.class,0);
		}
		public FocusconceptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_focusconcept; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterFocusconcept(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitFocusconcept(this);
		}
	}

	public final FocusconceptContext focusconcept() throws RecognitionException {
		FocusconceptContext _localctx = new FocusconceptContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_focusconcept);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			_la = _input.LA(1);
			if (_la==CARAT) {
				{
				setState(196);
				memberof();
				setState(197);
				ws();
				}
			}

			setState(203);
			switch (_input.LA(1)) {
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
				{
				setState(201);
				conceptreference();
				}
				break;
			case ASTERISK:
				{
				setState(202);
				wildcard();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberofContext extends ParserRuleContext {
		public TerminalNode CARAT() { return getToken(ExpressionConstraintParser.CARAT, 0); }
		public MemberofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterMemberof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitMemberof(this);
		}
	}

	public final MemberofContext memberof() throws RecognitionException {
		MemberofContext _localctx = new MemberofContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_memberof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(CARAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConceptreferenceContext extends ParserRuleContext {
		public ConceptidContext conceptid() {
			return getRuleContext(ConceptidContext.class,0);
		}
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public List<TerminalNode> PIPE() { return getTokens(ExpressionConstraintParser.PIPE); }
		public TerminalNode PIPE(int i) {
			return getToken(ExpressionConstraintParser.PIPE, i);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public ConceptreferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conceptreference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterConceptreference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitConceptreference(this);
		}
	}

	public final ConceptreferenceContext conceptreference() throws RecognitionException {
		ConceptreferenceContext _localctx = new ConceptreferenceContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_conceptreference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			conceptid();
			setState(215);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(208);
				ws();
				setState(209);
				match(PIPE);
				setState(210);
				ws();
				setState(211);
				term();
				setState(212);
				ws();
				setState(213);
				match(PIPE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConceptidContext extends ParserRuleContext {
		public SctidContext sctid() {
			return getRuleContext(SctidContext.class,0);
		}
		public ConceptidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conceptid; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterConceptid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitConceptid(this);
		}
	}

	public final ConceptidContext conceptid() throws RecognitionException {
		ConceptidContext _localctx = new ConceptidContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_conceptid);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			sctid();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public List<NonwsnonpipeContext> nonwsnonpipe() {
			return getRuleContexts(NonwsnonpipeContext.class);
		}
		public NonwsnonpipeContext nonwsnonpipe(int i) {
			return getRuleContext(NonwsnonpipeContext.class,i);
		}
		public List<SpContext> sp() {
			return getRuleContexts(SpContext.class);
		}
		public SpContext sp(int i) {
			return getRuleContext(SpContext.class,i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_term);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(220); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(219);
				nonwsnonpipe();
				}
				}
				setState(222); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EXCLAMATION) | (1L << QUOTE) | (1L << POUND) | (1L << DOLLAR) | (1L << PERCENT) | (1L << AMPERSAND) | (1L << APOSTROPHE) | (1L << LEFT_PAREN) | (1L << RIGHT_PAREN) | (1L << ASTERISK) | (1L << PLUS) | (1L << COMMA) | (1L << DASH) | (1L << PERIOD) | (1L << SLASH) | (1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE) | (1L << COLON) | (1L << SEMICOLON) | (1L << LESS_THAN) | (1L << EQUALS) | (1L << GREATER_THAN) | (1L << QUESTION) | (1L << AT) | (1L << CAP_A) | (1L << CAP_B) | (1L << CAP_C) | (1L << CAP_D) | (1L << CAP_E) | (1L << CAP_F) | (1L << CAP_G) | (1L << CAP_H) | (1L << CAP_I) | (1L << CAP_J) | (1L << CAP_K) | (1L << CAP_L) | (1L << CAP_M) | (1L << CAP_N) | (1L << CAP_O) | (1L << CAP_P) | (1L << CAP_Q) | (1L << CAP_R) | (1L << CAP_S) | (1L << CAP_T) | (1L << CAP_U) | (1L << CAP_V) | (1L << CAP_W) | (1L << CAP_X) | (1L << CAP_Y) | (1L << CAP_Z) | (1L << LEFT_BRACE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BACKSLASH - 64)) | (1L << (RIGHT_BRACE - 64)) | (1L << (CARAT - 64)) | (1L << (UNDERSCORE - 64)) | (1L << (ACCENT - 64)) | (1L << (A - 64)) | (1L << (B - 64)) | (1L << (C - 64)) | (1L << (D - 64)) | (1L << (E - 64)) | (1L << (F - 64)) | (1L << (G - 64)) | (1L << (H - 64)) | (1L << (I - 64)) | (1L << (J - 64)) | (1L << (K - 64)) | (1L << (L - 64)) | (1L << (M - 64)) | (1L << (N - 64)) | (1L << (O - 64)) | (1L << (P - 64)) | (1L << (Q - 64)) | (1L << (R - 64)) | (1L << (S - 64)) | (1L << (T - 64)) | (1L << (U - 64)) | (1L << (V - 64)) | (1L << (W - 64)) | (1L << (X - 64)) | (1L << (Y - 64)) | (1L << (Z - 64)) | (1L << (LEFT_CURLY_BRACE - 64)) | (1L << (RIGHT_CURLY_BRACE - 64)) | (1L << (TILDE - 64)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (U_00C2 - 163)) | (1L << (U_00C3 - 163)) | (1L << (U_00C4 - 163)) | (1L << (U_00C5 - 163)) | (1L << (U_00C6 - 163)) | (1L << (U_00C7 - 163)) | (1L << (U_00C8 - 163)) | (1L << (U_00C9 - 163)) | (1L << (U_00CA - 163)) | (1L << (U_00CB - 163)) | (1L << (U_00CC - 163)) | (1L << (U_00CD - 163)) | (1L << (U_00CE - 163)) | (1L << (U_00CF - 163)) | (1L << (U_00D0 - 163)) | (1L << (U_00D1 - 163)) | (1L << (U_00D2 - 163)) | (1L << (U_00D3 - 163)) | (1L << (U_00D4 - 163)) | (1L << (U_00D5 - 163)) | (1L << (U_00D6 - 163)) | (1L << (U_00D7 - 163)) | (1L << (U_00D8 - 163)) | (1L << (U_00D9 - 163)) | (1L << (U_00DA - 163)) | (1L << (U_00DB - 163)) | (1L << (U_00DC - 163)) | (1L << (U_00DD - 163)) | (1L << (U_00DE - 163)) | (1L << (U_00DF - 163)) | (1L << (U_00E0 - 163)) | (1L << (U_00E1 - 163)) | (1L << (U_00E2 - 163)) | (1L << (U_00E3 - 163)) | (1L << (U_00E4 - 163)) | (1L << (U_00E5 - 163)) | (1L << (U_00E6 - 163)) | (1L << (U_00E7 - 163)) | (1L << (U_00E8 - 163)) | (1L << (U_00E9 - 163)) | (1L << (U_00EA - 163)) | (1L << (U_00EB - 163)) | (1L << (U_00EC - 163)) | (1L << (U_00ED - 163)) | (1L << (U_00EE - 163)) | (1L << (U_00EF - 163)) | (1L << (U_00F0 - 163)) | (1L << (U_00F1 - 163)) | (1L << (U_00F2 - 163)) | (1L << (U_00F3 - 163)) | (1L << (U_00F4 - 163)))) != 0) );
			setState(236);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(225); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(224);
						sp();
						}
						}
						setState(227); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==SPACE );
					setState(230); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(229);
						nonwsnonpipe();
						}
						}
						setState(232); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EXCLAMATION) | (1L << QUOTE) | (1L << POUND) | (1L << DOLLAR) | (1L << PERCENT) | (1L << AMPERSAND) | (1L << APOSTROPHE) | (1L << LEFT_PAREN) | (1L << RIGHT_PAREN) | (1L << ASTERISK) | (1L << PLUS) | (1L << COMMA) | (1L << DASH) | (1L << PERIOD) | (1L << SLASH) | (1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE) | (1L << COLON) | (1L << SEMICOLON) | (1L << LESS_THAN) | (1L << EQUALS) | (1L << GREATER_THAN) | (1L << QUESTION) | (1L << AT) | (1L << CAP_A) | (1L << CAP_B) | (1L << CAP_C) | (1L << CAP_D) | (1L << CAP_E) | (1L << CAP_F) | (1L << CAP_G) | (1L << CAP_H) | (1L << CAP_I) | (1L << CAP_J) | (1L << CAP_K) | (1L << CAP_L) | (1L << CAP_M) | (1L << CAP_N) | (1L << CAP_O) | (1L << CAP_P) | (1L << CAP_Q) | (1L << CAP_R) | (1L << CAP_S) | (1L << CAP_T) | (1L << CAP_U) | (1L << CAP_V) | (1L << CAP_W) | (1L << CAP_X) | (1L << CAP_Y) | (1L << CAP_Z) | (1L << LEFT_BRACE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BACKSLASH - 64)) | (1L << (RIGHT_BRACE - 64)) | (1L << (CARAT - 64)) | (1L << (UNDERSCORE - 64)) | (1L << (ACCENT - 64)) | (1L << (A - 64)) | (1L << (B - 64)) | (1L << (C - 64)) | (1L << (D - 64)) | (1L << (E - 64)) | (1L << (F - 64)) | (1L << (G - 64)) | (1L << (H - 64)) | (1L << (I - 64)) | (1L << (J - 64)) | (1L << (K - 64)) | (1L << (L - 64)) | (1L << (M - 64)) | (1L << (N - 64)) | (1L << (O - 64)) | (1L << (P - 64)) | (1L << (Q - 64)) | (1L << (R - 64)) | (1L << (S - 64)) | (1L << (T - 64)) | (1L << (U - 64)) | (1L << (V - 64)) | (1L << (W - 64)) | (1L << (X - 64)) | (1L << (Y - 64)) | (1L << (Z - 64)) | (1L << (LEFT_CURLY_BRACE - 64)) | (1L << (RIGHT_CURLY_BRACE - 64)) | (1L << (TILDE - 64)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (U_00C2 - 163)) | (1L << (U_00C3 - 163)) | (1L << (U_00C4 - 163)) | (1L << (U_00C5 - 163)) | (1L << (U_00C6 - 163)) | (1L << (U_00C7 - 163)) | (1L << (U_00C8 - 163)) | (1L << (U_00C9 - 163)) | (1L << (U_00CA - 163)) | (1L << (U_00CB - 163)) | (1L << (U_00CC - 163)) | (1L << (U_00CD - 163)) | (1L << (U_00CE - 163)) | (1L << (U_00CF - 163)) | (1L << (U_00D0 - 163)) | (1L << (U_00D1 - 163)) | (1L << (U_00D2 - 163)) | (1L << (U_00D3 - 163)) | (1L << (U_00D4 - 163)) | (1L << (U_00D5 - 163)) | (1L << (U_00D6 - 163)) | (1L << (U_00D7 - 163)) | (1L << (U_00D8 - 163)) | (1L << (U_00D9 - 163)) | (1L << (U_00DA - 163)) | (1L << (U_00DB - 163)) | (1L << (U_00DC - 163)) | (1L << (U_00DD - 163)) | (1L << (U_00DE - 163)) | (1L << (U_00DF - 163)) | (1L << (U_00E0 - 163)) | (1L << (U_00E1 - 163)) | (1L << (U_00E2 - 163)) | (1L << (U_00E3 - 163)) | (1L << (U_00E4 - 163)) | (1L << (U_00E5 - 163)) | (1L << (U_00E6 - 163)) | (1L << (U_00E7 - 163)) | (1L << (U_00E8 - 163)) | (1L << (U_00E9 - 163)) | (1L << (U_00EA - 163)) | (1L << (U_00EB - 163)) | (1L << (U_00EC - 163)) | (1L << (U_00ED - 163)) | (1L << (U_00EE - 163)) | (1L << (U_00EF - 163)) | (1L << (U_00F0 - 163)) | (1L << (U_00F1 - 163)) | (1L << (U_00F2 - 163)) | (1L << (U_00F3 - 163)) | (1L << (U_00F4 - 163)))) != 0) );
					}
					} 
				}
				setState(238);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WildcardContext extends ParserRuleContext {
		public TerminalNode ASTERISK() { return getToken(ExpressionConstraintParser.ASTERISK, 0); }
		public WildcardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wildcard; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterWildcard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitWildcard(this);
		}
	}

	public final WildcardContext wildcard() throws RecognitionException {
		WildcardContext _localctx = new WildcardContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_wildcard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(ASTERISK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintoperatorContext extends ParserRuleContext {
		public DescendantorselfofContext descendantorselfof() {
			return getRuleContext(DescendantorselfofContext.class,0);
		}
		public DescendantofContext descendantof() {
			return getRuleContext(DescendantofContext.class,0);
		}
		public AncestororselfofContext ancestororselfof() {
			return getRuleContext(AncestororselfofContext.class,0);
		}
		public AncestorofContext ancestorof() {
			return getRuleContext(AncestorofContext.class,0);
		}
		public ConstraintoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterConstraintoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitConstraintoperator(this);
		}
	}

	public final ConstraintoperatorContext constraintoperator() throws RecognitionException {
		ConstraintoperatorContext _localctx = new ConstraintoperatorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_constraintoperator);
		try {
			setState(245);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(241);
				descendantorselfof();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(242);
				descendantof();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(243);
				ancestororselfof();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(244);
				ancestorof();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DescendantofContext extends ParserRuleContext {
		public TerminalNode LESS_THAN() { return getToken(ExpressionConstraintParser.LESS_THAN, 0); }
		public DescendantofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descendantof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDescendantof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDescendantof(this);
		}
	}

	public final DescendantofContext descendantof() throws RecognitionException {
		DescendantofContext _localctx = new DescendantofContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_descendantof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(LESS_THAN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DescendantorselfofContext extends ParserRuleContext {
		public List<TerminalNode> LESS_THAN() { return getTokens(ExpressionConstraintParser.LESS_THAN); }
		public TerminalNode LESS_THAN(int i) {
			return getToken(ExpressionConstraintParser.LESS_THAN, i);
		}
		public DescendantorselfofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descendantorselfof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDescendantorselfof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDescendantorselfof(this);
		}
	}

	public final DescendantorselfofContext descendantorselfof() throws RecognitionException {
		DescendantorselfofContext _localctx = new DescendantorselfofContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_descendantorselfof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(249);
			match(LESS_THAN);
			setState(250);
			match(LESS_THAN);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AncestorofContext extends ParserRuleContext {
		public TerminalNode GREATER_THAN() { return getToken(ExpressionConstraintParser.GREATER_THAN, 0); }
		public AncestorofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ancestorof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAncestorof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAncestorof(this);
		}
	}

	public final AncestorofContext ancestorof() throws RecognitionException {
		AncestorofContext _localctx = new AncestorofContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ancestorof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(GREATER_THAN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AncestororselfofContext extends ParserRuleContext {
		public List<TerminalNode> GREATER_THAN() { return getTokens(ExpressionConstraintParser.GREATER_THAN); }
		public TerminalNode GREATER_THAN(int i) {
			return getToken(ExpressionConstraintParser.GREATER_THAN, i);
		}
		public AncestororselfofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ancestororselfof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAncestororselfof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAncestororselfof(this);
		}
	}

	public final AncestororselfofContext ancestororselfof() throws RecognitionException {
		AncestororselfofContext _localctx = new AncestororselfofContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ancestororselfof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(254);
			match(GREATER_THAN);
			setState(255);
			match(GREATER_THAN);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctionContext extends ParserRuleContext {
		public MwsContext mws() {
			return getRuleContext(MwsContext.class,0);
		}
		public TerminalNode A() { return getToken(ExpressionConstraintParser.A, 0); }
		public TerminalNode CAP_A() { return getToken(ExpressionConstraintParser.CAP_A, 0); }
		public TerminalNode N() { return getToken(ExpressionConstraintParser.N, 0); }
		public TerminalNode CAP_N() { return getToken(ExpressionConstraintParser.CAP_N, 0); }
		public TerminalNode D() { return getToken(ExpressionConstraintParser.D, 0); }
		public TerminalNode CAP_D() { return getToken(ExpressionConstraintParser.CAP_D, 0); }
		public TerminalNode COMMA() { return getToken(ExpressionConstraintParser.COMMA, 0); }
		public ConjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitConjunction(this);
		}
	}

	public final ConjunctionContext conjunction() throws RecognitionException {
		ConjunctionContext _localctx = new ConjunctionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_conjunction);
		int _la;
		try {
			setState(262);
			switch (_input.LA(1)) {
			case CAP_A:
			case A:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(257);
				_la = _input.LA(1);
				if ( !(_la==CAP_A || _la==A) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(258);
				_la = _input.LA(1);
				if ( !(_la==CAP_N || _la==N) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(259);
				_la = _input.LA(1);
				if ( !(_la==CAP_D || _la==D) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(260);
				mws();
				}
				}
				break;
			case COMMA:
				enterOuterAlt(_localctx, 2);
				{
				setState(261);
				match(COMMA);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjunctionContext extends ParserRuleContext {
		public MwsContext mws() {
			return getRuleContext(MwsContext.class,0);
		}
		public TerminalNode O() { return getToken(ExpressionConstraintParser.O, 0); }
		public TerminalNode CAP_O() { return getToken(ExpressionConstraintParser.CAP_O, 0); }
		public TerminalNode R() { return getToken(ExpressionConstraintParser.R, 0); }
		public TerminalNode CAP_R() { return getToken(ExpressionConstraintParser.CAP_R, 0); }
		public DisjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDisjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDisjunction(this);
		}
	}

	public final DisjunctionContext disjunction() throws RecognitionException {
		DisjunctionContext _localctx = new DisjunctionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_disjunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			_la = _input.LA(1);
			if ( !(_la==CAP_O || _la==O) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(265);
			_la = _input.LA(1);
			if ( !(_la==CAP_R || _la==R) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(266);
			mws();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExclusionContext extends ParserRuleContext {
		public MwsContext mws() {
			return getRuleContext(MwsContext.class,0);
		}
		public TerminalNode M() { return getToken(ExpressionConstraintParser.M, 0); }
		public TerminalNode CAP_M() { return getToken(ExpressionConstraintParser.CAP_M, 0); }
		public TerminalNode I() { return getToken(ExpressionConstraintParser.I, 0); }
		public TerminalNode CAP_I() { return getToken(ExpressionConstraintParser.CAP_I, 0); }
		public TerminalNode N() { return getToken(ExpressionConstraintParser.N, 0); }
		public TerminalNode CAP_N() { return getToken(ExpressionConstraintParser.CAP_N, 0); }
		public TerminalNode U() { return getToken(ExpressionConstraintParser.U, 0); }
		public TerminalNode CAP_U() { return getToken(ExpressionConstraintParser.CAP_U, 0); }
		public TerminalNode S() { return getToken(ExpressionConstraintParser.S, 0); }
		public TerminalNode CAP_S() { return getToken(ExpressionConstraintParser.CAP_S, 0); }
		public ExclusionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterExclusion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitExclusion(this);
		}
	}

	public final ExclusionContext exclusion() throws RecognitionException {
		ExclusionContext _localctx = new ExclusionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_exclusion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			_la = _input.LA(1);
			if ( !(_la==CAP_M || _la==M) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(269);
			_la = _input.LA(1);
			if ( !(_la==CAP_I || _la==I) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(270);
			_la = _input.LA(1);
			if ( !(_la==CAP_N || _la==N) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(271);
			_la = _input.LA(1);
			if ( !(_la==CAP_U || _la==U) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(272);
			_la = _input.LA(1);
			if ( !(_la==CAP_S || _la==S) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(273);
			mws();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RefinementContext extends ParserRuleContext {
		public SubrefinementContext subrefinement() {
			return getRuleContext(SubrefinementContext.class,0);
		}
		public WsContext ws() {
			return getRuleContext(WsContext.class,0);
		}
		public ConjunctionrefinementsetContext conjunctionrefinementset() {
			return getRuleContext(ConjunctionrefinementsetContext.class,0);
		}
		public DisjunctionrefinementsetContext disjunctionrefinementset() {
			return getRuleContext(DisjunctionrefinementsetContext.class,0);
		}
		public RefinementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_refinement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterRefinement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitRefinement(this);
		}
	}

	public final RefinementContext refinement() throws RecognitionException {
		RefinementContext _localctx = new RefinementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_refinement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			subrefinement();
			setState(276);
			ws();
			setState(279);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(277);
				conjunctionrefinementset();
				}
				break;
			case 2:
				{
				setState(278);
				disjunctionrefinementset();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctionrefinementsetContext extends ParserRuleContext {
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public List<ConjunctionContext> conjunction() {
			return getRuleContexts(ConjunctionContext.class);
		}
		public ConjunctionContext conjunction(int i) {
			return getRuleContext(ConjunctionContext.class,i);
		}
		public List<SubrefinementContext> subrefinement() {
			return getRuleContexts(SubrefinementContext.class);
		}
		public SubrefinementContext subrefinement(int i) {
			return getRuleContext(SubrefinementContext.class,i);
		}
		public ConjunctionrefinementsetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunctionrefinementset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterConjunctionrefinementset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitConjunctionrefinementset(this);
		}
	}

	public final ConjunctionrefinementsetContext conjunctionrefinementset() throws RecognitionException {
		ConjunctionrefinementsetContext _localctx = new ConjunctionrefinementsetContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_conjunctionrefinementset);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(286); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(281);
					ws();
					setState(282);
					conjunction();
					setState(283);
					ws();
					setState(284);
					subrefinement();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(288); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjunctionrefinementsetContext extends ParserRuleContext {
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public List<DisjunctionContext> disjunction() {
			return getRuleContexts(DisjunctionContext.class);
		}
		public DisjunctionContext disjunction(int i) {
			return getRuleContext(DisjunctionContext.class,i);
		}
		public List<SubrefinementContext> subrefinement() {
			return getRuleContexts(SubrefinementContext.class);
		}
		public SubrefinementContext subrefinement(int i) {
			return getRuleContext(SubrefinementContext.class,i);
		}
		public DisjunctionrefinementsetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjunctionrefinementset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDisjunctionrefinementset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDisjunctionrefinementset(this);
		}
	}

	public final DisjunctionrefinementsetContext disjunctionrefinementset() throws RecognitionException {
		DisjunctionrefinementsetContext _localctx = new DisjunctionrefinementsetContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_disjunctionrefinementset);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(295); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(290);
					ws();
					setState(291);
					disjunction();
					setState(292);
					ws();
					setState(293);
					subrefinement();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(297); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubrefinementContext extends ParserRuleContext {
		public AttributesetContext attributeset() {
			return getRuleContext(AttributesetContext.class,0);
		}
		public AttributegroupContext attributegroup() {
			return getRuleContext(AttributegroupContext.class,0);
		}
		public TerminalNode LEFT_PAREN() { return getToken(ExpressionConstraintParser.LEFT_PAREN, 0); }
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public RefinementContext refinement() {
			return getRuleContext(RefinementContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ExpressionConstraintParser.RIGHT_PAREN, 0); }
		public SubrefinementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subrefinement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterSubrefinement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitSubrefinement(this);
		}
	}

	public final SubrefinementContext subrefinement() throws RecognitionException {
		SubrefinementContext _localctx = new SubrefinementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_subrefinement);
		try {
			setState(307);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(299);
				attributeset();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				attributegroup();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(301);
				match(LEFT_PAREN);
				setState(302);
				ws();
				setState(303);
				refinement();
				setState(304);
				ws();
				setState(305);
				match(RIGHT_PAREN);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributesetContext extends ParserRuleContext {
		public SubattributesetContext subattributeset() {
			return getRuleContext(SubattributesetContext.class,0);
		}
		public WsContext ws() {
			return getRuleContext(WsContext.class,0);
		}
		public ConjunctionattributesetContext conjunctionattributeset() {
			return getRuleContext(ConjunctionattributesetContext.class,0);
		}
		public DisjunctionattributesetContext disjunctionattributeset() {
			return getRuleContext(DisjunctionattributesetContext.class,0);
		}
		public AttributesetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAttributeset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAttributeset(this);
		}
	}

	public final AttributesetContext attributeset() throws RecognitionException {
		AttributesetContext _localctx = new AttributesetContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_attributeset);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			subattributeset();
			setState(310);
			ws();
			setState(313);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(311);
				conjunctionattributeset();
				}
				break;
			case 2:
				{
				setState(312);
				disjunctionattributeset();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctionattributesetContext extends ParserRuleContext {
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public List<ConjunctionContext> conjunction() {
			return getRuleContexts(ConjunctionContext.class);
		}
		public ConjunctionContext conjunction(int i) {
			return getRuleContext(ConjunctionContext.class,i);
		}
		public List<SubattributesetContext> subattributeset() {
			return getRuleContexts(SubattributesetContext.class);
		}
		public SubattributesetContext subattributeset(int i) {
			return getRuleContext(SubattributesetContext.class,i);
		}
		public ConjunctionattributesetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunctionattributeset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterConjunctionattributeset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitConjunctionattributeset(this);
		}
	}

	public final ConjunctionattributesetContext conjunctionattributeset() throws RecognitionException {
		ConjunctionattributesetContext _localctx = new ConjunctionattributesetContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_conjunctionattributeset);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(320); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(315);
					ws();
					setState(316);
					conjunction();
					setState(317);
					ws();
					setState(318);
					subattributeset();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(322); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjunctionattributesetContext extends ParserRuleContext {
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public List<DisjunctionContext> disjunction() {
			return getRuleContexts(DisjunctionContext.class);
		}
		public DisjunctionContext disjunction(int i) {
			return getRuleContext(DisjunctionContext.class,i);
		}
		public List<SubattributesetContext> subattributeset() {
			return getRuleContexts(SubattributesetContext.class);
		}
		public SubattributesetContext subattributeset(int i) {
			return getRuleContext(SubattributesetContext.class,i);
		}
		public DisjunctionattributesetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjunctionattributeset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDisjunctionattributeset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDisjunctionattributeset(this);
		}
	}

	public final DisjunctionattributesetContext disjunctionattributeset() throws RecognitionException {
		DisjunctionattributesetContext _localctx = new DisjunctionattributesetContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_disjunctionattributeset);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(329); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(324);
					ws();
					setState(325);
					disjunction();
					setState(326);
					ws();
					setState(327);
					subattributeset();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(331); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubattributesetContext extends ParserRuleContext {
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public TerminalNode LEFT_PAREN() { return getToken(ExpressionConstraintParser.LEFT_PAREN, 0); }
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public AttributesetContext attributeset() {
			return getRuleContext(AttributesetContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ExpressionConstraintParser.RIGHT_PAREN, 0); }
		public SubattributesetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subattributeset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterSubattributeset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitSubattributeset(this);
		}
	}

	public final SubattributesetContext subattributeset() throws RecognitionException {
		SubattributesetContext _localctx = new SubattributesetContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_subattributeset);
		try {
			setState(340);
			switch (_input.LA(1)) {
			case ASTERISK:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
			case LESS_THAN:
			case CAP_R:
			case LEFT_BRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(333);
				attribute();
				}
				break;
			case LEFT_PAREN:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(334);
				match(LEFT_PAREN);
				setState(335);
				ws();
				setState(336);
				attributeset();
				setState(337);
				ws();
				setState(338);
				match(RIGHT_PAREN);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributegroupContext extends ParserRuleContext {
		public TerminalNode LEFT_CURLY_BRACE() { return getToken(ExpressionConstraintParser.LEFT_CURLY_BRACE, 0); }
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public AttributesetContext attributeset() {
			return getRuleContext(AttributesetContext.class,0);
		}
		public TerminalNode RIGHT_CURLY_BRACE() { return getToken(ExpressionConstraintParser.RIGHT_CURLY_BRACE, 0); }
		public CardinalityContext cardinality() {
			return getRuleContext(CardinalityContext.class,0);
		}
		public AttributegroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributegroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAttributegroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAttributegroup(this);
		}
	}

	public final AttributegroupContext attributegroup() throws RecognitionException {
		AttributegroupContext _localctx = new AttributegroupContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_attributegroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
			_la = _input.LA(1);
			if (_la==LEFT_BRACE) {
				{
				setState(342);
				cardinality();
				setState(343);
				ws();
				}
			}

			setState(347);
			match(LEFT_CURLY_BRACE);
			setState(348);
			ws();
			setState(349);
			attributeset();
			setState(350);
			ws();
			setState(351);
			match(RIGHT_CURLY_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public AttributenameContext attributename() {
			return getRuleContext(AttributenameContext.class,0);
		}
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public CardinalityContext cardinality() {
			return getRuleContext(CardinalityContext.class,0);
		}
		public ReverseflagContext reverseflag() {
			return getRuleContext(ReverseflagContext.class,0);
		}
		public AttributeoperatorContext attributeoperator() {
			return getRuleContext(AttributeoperatorContext.class,0);
		}
		public ExpressioncomparisonoperatorContext expressioncomparisonoperator() {
			return getRuleContext(ExpressioncomparisonoperatorContext.class,0);
		}
		public ExpressionconstraintvalueContext expressionconstraintvalue() {
			return getRuleContext(ExpressionconstraintvalueContext.class,0);
		}
		public NumericcomparisonoperatorContext numericcomparisonoperator() {
			return getRuleContext(NumericcomparisonoperatorContext.class,0);
		}
		public NumericvalueContext numericvalue() {
			return getRuleContext(NumericvalueContext.class,0);
		}
		public StringcomparisonoperatorContext stringcomparisonoperator() {
			return getRuleContext(StringcomparisonoperatorContext.class,0);
		}
		public StringvalueContext stringvalue() {
			return getRuleContext(StringvalueContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAttribute(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_attribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			_la = _input.LA(1);
			if (_la==LEFT_BRACE) {
				{
				setState(353);
				cardinality();
				setState(354);
				ws();
				}
			}

			setState(361);
			_la = _input.LA(1);
			if (_la==CAP_R) {
				{
				setState(358);
				reverseflag();
				setState(359);
				ws();
				}
			}

			setState(366);
			_la = _input.LA(1);
			if (_la==LESS_THAN) {
				{
				setState(363);
				attributeoperator();
				setState(364);
				ws();
				}
			}

			setState(368);
			attributename();
			setState(369);
			ws();
			setState(382);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				{
				setState(370);
				expressioncomparisonoperator();
				setState(371);
				ws();
				setState(372);
				expressionconstraintvalue();
				}
				}
				break;
			case 2:
				{
				{
				setState(374);
				numericcomparisonoperator();
				setState(375);
				ws();
				setState(376);
				numericvalue();
				}
				}
				break;
			case 3:
				{
				{
				setState(378);
				stringcomparisonoperator();
				setState(379);
				ws();
				setState(380);
				stringvalue();
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CardinalityContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(ExpressionConstraintParser.LEFT_BRACE, 0); }
		public List<NonnegativeintegervalueContext> nonnegativeintegervalue() {
			return getRuleContexts(NonnegativeintegervalueContext.class);
		}
		public NonnegativeintegervalueContext nonnegativeintegervalue(int i) {
			return getRuleContext(NonnegativeintegervalueContext.class,i);
		}
		public ToContext to() {
			return getRuleContext(ToContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(ExpressionConstraintParser.RIGHT_BRACE, 0); }
		public ManyContext many() {
			return getRuleContext(ManyContext.class,0);
		}
		public CardinalityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cardinality; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterCardinality(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitCardinality(this);
		}
	}

	public final CardinalityContext cardinality() throws RecognitionException {
		CardinalityContext _localctx = new CardinalityContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_cardinality);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			match(LEFT_BRACE);
			setState(385);
			nonnegativeintegervalue();
			setState(386);
			to();
			setState(389);
			switch (_input.LA(1)) {
			case ZERO:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
				{
				setState(387);
				nonnegativeintegervalue();
				}
				break;
			case ASTERISK:
				{
				setState(388);
				many();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(391);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ToContext extends ParserRuleContext {
		public List<TerminalNode> PERIOD() { return getTokens(ExpressionConstraintParser.PERIOD); }
		public TerminalNode PERIOD(int i) {
			return getToken(ExpressionConstraintParser.PERIOD, i);
		}
		public ToContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_to; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterTo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitTo(this);
		}
	}

	public final ToContext to() throws RecognitionException {
		ToContext _localctx = new ToContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_to);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(393);
			match(PERIOD);
			setState(394);
			match(PERIOD);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ManyContext extends ParserRuleContext {
		public TerminalNode ASTERISK() { return getToken(ExpressionConstraintParser.ASTERISK, 0); }
		public ManyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_many; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterMany(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitMany(this);
		}
	}

	public final ManyContext many() throws RecognitionException {
		ManyContext _localctx = new ManyContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_many);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			match(ASTERISK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReverseflagContext extends ParserRuleContext {
		public TerminalNode CAP_R() { return getToken(ExpressionConstraintParser.CAP_R, 0); }
		public ReverseflagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reverseflag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterReverseflag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitReverseflag(this);
		}
	}

	public final ReverseflagContext reverseflag() throws RecognitionException {
		ReverseflagContext _localctx = new ReverseflagContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_reverseflag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			match(CAP_R);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeoperatorContext extends ParserRuleContext {
		public DescendantorselfofContext descendantorselfof() {
			return getRuleContext(DescendantorselfofContext.class,0);
		}
		public DescendantofContext descendantof() {
			return getRuleContext(DescendantofContext.class,0);
		}
		public AttributeoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAttributeoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAttributeoperator(this);
		}
	}

	public final AttributeoperatorContext attributeoperator() throws RecognitionException {
		AttributeoperatorContext _localctx = new AttributeoperatorContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_attributeoperator);
		try {
			setState(402);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(400);
				descendantorselfof();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(401);
				descendantof();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributenameContext extends ParserRuleContext {
		public ConceptreferenceContext conceptreference() {
			return getRuleContext(ConceptreferenceContext.class,0);
		}
		public WildcardContext wildcard() {
			return getRuleContext(WildcardContext.class,0);
		}
		public AttributenameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributename; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAttributename(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAttributename(this);
		}
	}

	public final AttributenameContext attributename() throws RecognitionException {
		AttributenameContext _localctx = new AttributenameContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_attributename);
		try {
			setState(406);
			switch (_input.LA(1)) {
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
				enterOuterAlt(_localctx, 1);
				{
				setState(404);
				conceptreference();
				}
				break;
			case ASTERISK:
				enterOuterAlt(_localctx, 2);
				{
				setState(405);
				wildcard();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionconstraintvalueContext extends ParserRuleContext {
		public SimpleexpressionconstraintContext simpleexpressionconstraint() {
			return getRuleContext(SimpleexpressionconstraintContext.class,0);
		}
		public TerminalNode LEFT_PAREN() { return getToken(ExpressionConstraintParser.LEFT_PAREN, 0); }
		public List<WsContext> ws() {
			return getRuleContexts(WsContext.class);
		}
		public WsContext ws(int i) {
			return getRuleContext(WsContext.class,i);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(ExpressionConstraintParser.RIGHT_PAREN, 0); }
		public RefinedexpressionconstraintContext refinedexpressionconstraint() {
			return getRuleContext(RefinedexpressionconstraintContext.class,0);
		}
		public CompoundexpressionconstraintContext compoundexpressionconstraint() {
			return getRuleContext(CompoundexpressionconstraintContext.class,0);
		}
		public ExpressionconstraintvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionconstraintvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterExpressionconstraintvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitExpressionconstraintvalue(this);
		}
	}

	public final ExpressionconstraintvalueContext expressionconstraintvalue() throws RecognitionException {
		ExpressionconstraintvalueContext _localctx = new ExpressionconstraintvalueContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_expressionconstraintvalue);
		try {
			setState(418);
			switch (_input.LA(1)) {
			case ASTERISK:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
			case LESS_THAN:
			case GREATER_THAN:
			case CARAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(408);
				simpleexpressionconstraint();
				}
				break;
			case LEFT_PAREN:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(409);
				match(LEFT_PAREN);
				setState(410);
				ws();
				setState(413);
				switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
				case 1:
					{
					setState(411);
					refinedexpressionconstraint();
					}
					break;
				case 2:
					{
					setState(412);
					compoundexpressionconstraint();
					}
					break;
				}
				setState(415);
				ws();
				setState(416);
				match(RIGHT_PAREN);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressioncomparisonoperatorContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(ExpressionConstraintParser.EQUALS, 0); }
		public TerminalNode EXCLAMATION() { return getToken(ExpressionConstraintParser.EXCLAMATION, 0); }
		public ExpressioncomparisonoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressioncomparisonoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterExpressioncomparisonoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitExpressioncomparisonoperator(this);
		}
	}

	public final ExpressioncomparisonoperatorContext expressioncomparisonoperator() throws RecognitionException {
		ExpressioncomparisonoperatorContext _localctx = new ExpressioncomparisonoperatorContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_expressioncomparisonoperator);
		try {
			setState(423);
			switch (_input.LA(1)) {
			case EQUALS:
				enterOuterAlt(_localctx, 1);
				{
				setState(420);
				match(EQUALS);
				}
				break;
			case EXCLAMATION:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(421);
				match(EXCLAMATION);
				setState(422);
				match(EQUALS);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericcomparisonoperatorContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(ExpressionConstraintParser.EQUALS, 0); }
		public TerminalNode EXCLAMATION() { return getToken(ExpressionConstraintParser.EXCLAMATION, 0); }
		public TerminalNode LESS_THAN() { return getToken(ExpressionConstraintParser.LESS_THAN, 0); }
		public TerminalNode GREATER_THAN() { return getToken(ExpressionConstraintParser.GREATER_THAN, 0); }
		public NumericcomparisonoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericcomparisonoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterNumericcomparisonoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitNumericcomparisonoperator(this);
		}
	}

	public final NumericcomparisonoperatorContext numericcomparisonoperator() throws RecognitionException {
		NumericcomparisonoperatorContext _localctx = new NumericcomparisonoperatorContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_numericcomparisonoperator);
		try {
			setState(434);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(425);
				match(EQUALS);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(426);
				match(EXCLAMATION);
				setState(427);
				match(EQUALS);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(428);
				match(LESS_THAN);
				setState(429);
				match(EQUALS);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(430);
				match(LESS_THAN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				{
				setState(431);
				match(GREATER_THAN);
				setState(432);
				match(EQUALS);
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(433);
				match(GREATER_THAN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringcomparisonoperatorContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(ExpressionConstraintParser.EQUALS, 0); }
		public TerminalNode EXCLAMATION() { return getToken(ExpressionConstraintParser.EXCLAMATION, 0); }
		public StringcomparisonoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringcomparisonoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterStringcomparisonoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitStringcomparisonoperator(this);
		}
	}

	public final StringcomparisonoperatorContext stringcomparisonoperator() throws RecognitionException {
		StringcomparisonoperatorContext _localctx = new StringcomparisonoperatorContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_stringcomparisonoperator);
		try {
			setState(439);
			switch (_input.LA(1)) {
			case EQUALS:
				enterOuterAlt(_localctx, 1);
				{
				setState(436);
				match(EQUALS);
				}
				break;
			case EXCLAMATION:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(437);
				match(EXCLAMATION);
				setState(438);
				match(EQUALS);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumericvalueContext extends ParserRuleContext {
		public TerminalNode POUND() { return getToken(ExpressionConstraintParser.POUND, 0); }
		public DecimalvalueContext decimalvalue() {
			return getRuleContext(DecimalvalueContext.class,0);
		}
		public IntegervalueContext integervalue() {
			return getRuleContext(IntegervalueContext.class,0);
		}
		public NumericvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterNumericvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitNumericvalue(this);
		}
	}

	public final NumericvalueContext numericvalue() throws RecognitionException {
		NumericvalueContext _localctx = new NumericvalueContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_numericvalue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(441);
			match(POUND);
			setState(444);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(442);
				decimalvalue();
				}
				break;
			case 2:
				{
				setState(443);
				integervalue();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringvalueContext extends ParserRuleContext {
		public List<QmContext> qm() {
			return getRuleContexts(QmContext.class);
		}
		public QmContext qm(int i) {
			return getRuleContext(QmContext.class,i);
		}
		public List<AnynonescapedcharContext> anynonescapedchar() {
			return getRuleContexts(AnynonescapedcharContext.class);
		}
		public AnynonescapedcharContext anynonescapedchar(int i) {
			return getRuleContext(AnynonescapedcharContext.class,i);
		}
		public List<EscapedcharContext> escapedchar() {
			return getRuleContexts(EscapedcharContext.class);
		}
		public EscapedcharContext escapedchar(int i) {
			return getRuleContext(EscapedcharContext.class,i);
		}
		public StringvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterStringvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitStringvalue(this);
		}
	}

	public final StringvalueContext stringvalue() throws RecognitionException {
		StringvalueContext _localctx = new StringvalueContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_stringvalue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(446);
			qm();
			setState(449); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(449);
				switch (_input.LA(1)) {
				case TAB:
				case LF:
				case CR:
				case SPACE:
				case EXCLAMATION:
				case POUND:
				case DOLLAR:
				case PERCENT:
				case AMPERSAND:
				case APOSTROPHE:
				case LEFT_PAREN:
				case RIGHT_PAREN:
				case ASTERISK:
				case PLUS:
				case COMMA:
				case DASH:
				case PERIOD:
				case SLASH:
				case ZERO:
				case ONE:
				case TWO:
				case THREE:
				case FOUR:
				case FIVE:
				case SIX:
				case SEVEN:
				case EIGHT:
				case NINE:
				case COLON:
				case SEMICOLON:
				case LESS_THAN:
				case EQUALS:
				case GREATER_THAN:
				case QUESTION:
				case AT:
				case CAP_A:
				case CAP_B:
				case CAP_C:
				case CAP_D:
				case CAP_E:
				case CAP_F:
				case CAP_G:
				case CAP_H:
				case CAP_I:
				case CAP_J:
				case CAP_K:
				case CAP_L:
				case CAP_M:
				case CAP_N:
				case CAP_O:
				case CAP_P:
				case CAP_Q:
				case CAP_R:
				case CAP_S:
				case CAP_T:
				case CAP_U:
				case CAP_V:
				case CAP_W:
				case CAP_X:
				case CAP_Y:
				case CAP_Z:
				case LEFT_BRACE:
				case RIGHT_BRACE:
				case CARAT:
				case UNDERSCORE:
				case ACCENT:
				case A:
				case B:
				case C:
				case D:
				case E:
				case F:
				case G:
				case H:
				case I:
				case J:
				case K:
				case L:
				case M:
				case N:
				case O:
				case P:
				case Q:
				case R:
				case S:
				case T:
				case U:
				case V:
				case W:
				case X:
				case Y:
				case Z:
				case LEFT_CURLY_BRACE:
				case PIPE:
				case RIGHT_CURLY_BRACE:
				case TILDE:
				case U_00C2:
				case U_00C3:
				case U_00C4:
				case U_00C5:
				case U_00C6:
				case U_00C7:
				case U_00C8:
				case U_00C9:
				case U_00CA:
				case U_00CB:
				case U_00CC:
				case U_00CD:
				case U_00CE:
				case U_00CF:
				case U_00D0:
				case U_00D1:
				case U_00D2:
				case U_00D3:
				case U_00D4:
				case U_00D5:
				case U_00D6:
				case U_00D7:
				case U_00D8:
				case U_00D9:
				case U_00DA:
				case U_00DB:
				case U_00DC:
				case U_00DD:
				case U_00DE:
				case U_00DF:
				case U_00E0:
				case U_00E1:
				case U_00E2:
				case U_00E3:
				case U_00E4:
				case U_00E5:
				case U_00E6:
				case U_00E7:
				case U_00E8:
				case U_00E9:
				case U_00EA:
				case U_00EB:
				case U_00EC:
				case U_00ED:
				case U_00EE:
				case U_00EF:
				case U_00F0:
				case U_00F1:
				case U_00F2:
				case U_00F3:
				case U_00F4:
					{
					setState(447);
					anynonescapedchar();
					}
					break;
				case BACKSLASH:
					{
					setState(448);
					escapedchar();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(451); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TAB) | (1L << LF) | (1L << CR) | (1L << SPACE) | (1L << EXCLAMATION) | (1L << POUND) | (1L << DOLLAR) | (1L << PERCENT) | (1L << AMPERSAND) | (1L << APOSTROPHE) | (1L << LEFT_PAREN) | (1L << RIGHT_PAREN) | (1L << ASTERISK) | (1L << PLUS) | (1L << COMMA) | (1L << DASH) | (1L << PERIOD) | (1L << SLASH) | (1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE) | (1L << COLON) | (1L << SEMICOLON) | (1L << LESS_THAN) | (1L << EQUALS) | (1L << GREATER_THAN) | (1L << QUESTION) | (1L << AT) | (1L << CAP_A) | (1L << CAP_B) | (1L << CAP_C) | (1L << CAP_D) | (1L << CAP_E) | (1L << CAP_F) | (1L << CAP_G) | (1L << CAP_H) | (1L << CAP_I) | (1L << CAP_J) | (1L << CAP_K) | (1L << CAP_L) | (1L << CAP_M) | (1L << CAP_N) | (1L << CAP_O) | (1L << CAP_P) | (1L << CAP_Q) | (1L << CAP_R) | (1L << CAP_S) | (1L << CAP_T) | (1L << CAP_U) | (1L << CAP_V) | (1L << CAP_W) | (1L << CAP_X) | (1L << CAP_Y) | (1L << CAP_Z) | (1L << LEFT_BRACE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BACKSLASH - 64)) | (1L << (RIGHT_BRACE - 64)) | (1L << (CARAT - 64)) | (1L << (UNDERSCORE - 64)) | (1L << (ACCENT - 64)) | (1L << (A - 64)) | (1L << (B - 64)) | (1L << (C - 64)) | (1L << (D - 64)) | (1L << (E - 64)) | (1L << (F - 64)) | (1L << (G - 64)) | (1L << (H - 64)) | (1L << (I - 64)) | (1L << (J - 64)) | (1L << (K - 64)) | (1L << (L - 64)) | (1L << (M - 64)) | (1L << (N - 64)) | (1L << (O - 64)) | (1L << (P - 64)) | (1L << (Q - 64)) | (1L << (R - 64)) | (1L << (S - 64)) | (1L << (T - 64)) | (1L << (U - 64)) | (1L << (V - 64)) | (1L << (W - 64)) | (1L << (X - 64)) | (1L << (Y - 64)) | (1L << (Z - 64)) | (1L << (LEFT_CURLY_BRACE - 64)) | (1L << (PIPE - 64)) | (1L << (RIGHT_CURLY_BRACE - 64)) | (1L << (TILDE - 64)))) != 0) || ((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (U_00C2 - 163)) | (1L << (U_00C3 - 163)) | (1L << (U_00C4 - 163)) | (1L << (U_00C5 - 163)) | (1L << (U_00C6 - 163)) | (1L << (U_00C7 - 163)) | (1L << (U_00C8 - 163)) | (1L << (U_00C9 - 163)) | (1L << (U_00CA - 163)) | (1L << (U_00CB - 163)) | (1L << (U_00CC - 163)) | (1L << (U_00CD - 163)) | (1L << (U_00CE - 163)) | (1L << (U_00CF - 163)) | (1L << (U_00D0 - 163)) | (1L << (U_00D1 - 163)) | (1L << (U_00D2 - 163)) | (1L << (U_00D3 - 163)) | (1L << (U_00D4 - 163)) | (1L << (U_00D5 - 163)) | (1L << (U_00D6 - 163)) | (1L << (U_00D7 - 163)) | (1L << (U_00D8 - 163)) | (1L << (U_00D9 - 163)) | (1L << (U_00DA - 163)) | (1L << (U_00DB - 163)) | (1L << (U_00DC - 163)) | (1L << (U_00DD - 163)) | (1L << (U_00DE - 163)) | (1L << (U_00DF - 163)) | (1L << (U_00E0 - 163)) | (1L << (U_00E1 - 163)) | (1L << (U_00E2 - 163)) | (1L << (U_00E3 - 163)) | (1L << (U_00E4 - 163)) | (1L << (U_00E5 - 163)) | (1L << (U_00E6 - 163)) | (1L << (U_00E7 - 163)) | (1L << (U_00E8 - 163)) | (1L << (U_00E9 - 163)) | (1L << (U_00EA - 163)) | (1L << (U_00EB - 163)) | (1L << (U_00EC - 163)) | (1L << (U_00ED - 163)) | (1L << (U_00EE - 163)) | (1L << (U_00EF - 163)) | (1L << (U_00F0 - 163)) | (1L << (U_00F1 - 163)) | (1L << (U_00F2 - 163)) | (1L << (U_00F3 - 163)) | (1L << (U_00F4 - 163)))) != 0) );
			setState(453);
			qm();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegervalueContext extends ParserRuleContext {
		public DigitnonzeroContext digitnonzero() {
			return getRuleContext(DigitnonzeroContext.class,0);
		}
		public List<DigitContext> digit() {
			return getRuleContexts(DigitContext.class);
		}
		public DigitContext digit(int i) {
			return getRuleContext(DigitContext.class,i);
		}
		public TerminalNode DASH() { return getToken(ExpressionConstraintParser.DASH, 0); }
		public TerminalNode PLUS() { return getToken(ExpressionConstraintParser.PLUS, 0); }
		public ZeroContext zero() {
			return getRuleContext(ZeroContext.class,0);
		}
		public IntegervalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integervalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterIntegervalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitIntegervalue(this);
		}
	}

	public final IntegervalueContext integervalue() throws RecognitionException {
		IntegervalueContext _localctx = new IntegervalueContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_integervalue);
		int _la;
		try {
			setState(466);
			switch (_input.LA(1)) {
			case PLUS:
			case DASH:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(456);
				_la = _input.LA(1);
				if (_la==PLUS || _la==DASH) {
					{
					setState(455);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==DASH) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
				}

				setState(458);
				digitnonzero();
				setState(462);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE))) != 0)) {
					{
					{
					setState(459);
					digit();
					}
					}
					setState(464);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				break;
			case ZERO:
				enterOuterAlt(_localctx, 2);
				{
				setState(465);
				zero();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecimalvalueContext extends ParserRuleContext {
		public IntegervalueContext integervalue() {
			return getRuleContext(IntegervalueContext.class,0);
		}
		public TerminalNode PERIOD() { return getToken(ExpressionConstraintParser.PERIOD, 0); }
		public List<DigitContext> digit() {
			return getRuleContexts(DigitContext.class);
		}
		public DigitContext digit(int i) {
			return getRuleContext(DigitContext.class,i);
		}
		public DecimalvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decimalvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDecimalvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDecimalvalue(this);
		}
	}

	public final DecimalvalueContext decimalvalue() throws RecognitionException {
		DecimalvalueContext _localctx = new DecimalvalueContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_decimalvalue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(468);
			integervalue();
			setState(469);
			match(PERIOD);
			setState(471); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(470);
				digit();
				}
				}
				setState(473); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonnegativeintegervalueContext extends ParserRuleContext {
		public DigitnonzeroContext digitnonzero() {
			return getRuleContext(DigitnonzeroContext.class,0);
		}
		public List<DigitContext> digit() {
			return getRuleContexts(DigitContext.class);
		}
		public DigitContext digit(int i) {
			return getRuleContext(DigitContext.class,i);
		}
		public ZeroContext zero() {
			return getRuleContext(ZeroContext.class,0);
		}
		public NonnegativeintegervalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonnegativeintegervalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterNonnegativeintegervalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitNonnegativeintegervalue(this);
		}
	}

	public final NonnegativeintegervalueContext nonnegativeintegervalue() throws RecognitionException {
		NonnegativeintegervalueContext _localctx = new NonnegativeintegervalueContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_nonnegativeintegervalue);
		int _la;
		try {
			setState(483);
			switch (_input.LA(1)) {
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(475);
				digitnonzero();
				setState(479);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE))) != 0)) {
					{
					{
					setState(476);
					digit();
					}
					}
					setState(481);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				break;
			case ZERO:
				enterOuterAlt(_localctx, 2);
				{
				setState(482);
				zero();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SctidContext extends ParserRuleContext {
		public DigitnonzeroContext digitnonzero() {
			return getRuleContext(DigitnonzeroContext.class,0);
		}
		public List<DigitContext> digit() {
			return getRuleContexts(DigitContext.class);
		}
		public DigitContext digit(int i) {
			return getRuleContext(DigitContext.class,i);
		}
		public SctidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sctid; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterSctid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitSctid(this);
		}
	}

	public final SctidContext sctid() throws RecognitionException {
		SctidContext _localctx = new SctidContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_sctid);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			digitnonzero();
			{
			setState(486);
			digit();
			}
			{
			setState(487);
			digit();
			}
			{
			setState(488);
			digit();
			}
			{
			setState(489);
			digit();
			}
			{
			setState(490);
			digit();
			}
			setState(582);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				{
				setState(492);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE))) != 0)) {
					{
					setState(491);
					digit();
					}
				}

				}
				break;
			case 2:
				{
				{
				{
				setState(494);
				digit();
				}
				{
				setState(495);
				digit();
				}
				}
				}
				break;
			case 3:
				{
				{
				{
				setState(497);
				digit();
				}
				{
				setState(498);
				digit();
				}
				{
				setState(499);
				digit();
				}
				}
				}
				break;
			case 4:
				{
				{
				{
				setState(501);
				digit();
				}
				{
				setState(502);
				digit();
				}
				{
				setState(503);
				digit();
				}
				{
				setState(504);
				digit();
				}
				}
				}
				break;
			case 5:
				{
				{
				{
				setState(506);
				digit();
				}
				{
				setState(507);
				digit();
				}
				{
				setState(508);
				digit();
				}
				{
				setState(509);
				digit();
				}
				{
				setState(510);
				digit();
				}
				}
				}
				break;
			case 6:
				{
				{
				{
				setState(512);
				digit();
				}
				{
				setState(513);
				digit();
				}
				{
				setState(514);
				digit();
				}
				{
				setState(515);
				digit();
				}
				{
				setState(516);
				digit();
				}
				{
				setState(517);
				digit();
				}
				}
				}
				break;
			case 7:
				{
				{
				{
				setState(519);
				digit();
				}
				{
				setState(520);
				digit();
				}
				{
				setState(521);
				digit();
				}
				{
				setState(522);
				digit();
				}
				{
				setState(523);
				digit();
				}
				{
				setState(524);
				digit();
				}
				{
				setState(525);
				digit();
				}
				}
				}
				break;
			case 8:
				{
				{
				{
				setState(527);
				digit();
				}
				{
				setState(528);
				digit();
				}
				{
				setState(529);
				digit();
				}
				{
				setState(530);
				digit();
				}
				{
				setState(531);
				digit();
				}
				{
				setState(532);
				digit();
				}
				{
				setState(533);
				digit();
				}
				{
				setState(534);
				digit();
				}
				}
				}
				break;
			case 9:
				{
				{
				{
				setState(536);
				digit();
				}
				{
				setState(537);
				digit();
				}
				{
				setState(538);
				digit();
				}
				{
				setState(539);
				digit();
				}
				{
				setState(540);
				digit();
				}
				{
				setState(541);
				digit();
				}
				{
				setState(542);
				digit();
				}
				{
				setState(543);
				digit();
				}
				{
				setState(544);
				digit();
				}
				}
				}
				break;
			case 10:
				{
				{
				{
				setState(546);
				digit();
				}
				{
				setState(547);
				digit();
				}
				{
				setState(548);
				digit();
				}
				{
				setState(549);
				digit();
				}
				{
				setState(550);
				digit();
				}
				{
				setState(551);
				digit();
				}
				{
				setState(552);
				digit();
				}
				{
				setState(553);
				digit();
				}
				{
				setState(554);
				digit();
				}
				{
				setState(555);
				digit();
				}
				}
				}
				break;
			case 11:
				{
				{
				{
				setState(557);
				digit();
				}
				{
				setState(558);
				digit();
				}
				{
				setState(559);
				digit();
				}
				{
				setState(560);
				digit();
				}
				{
				setState(561);
				digit();
				}
				{
				setState(562);
				digit();
				}
				{
				setState(563);
				digit();
				}
				{
				setState(564);
				digit();
				}
				{
				setState(565);
				digit();
				}
				{
				setState(566);
				digit();
				}
				{
				setState(567);
				digit();
				}
				}
				}
				break;
			case 12:
				{
				{
				{
				setState(569);
				digit();
				}
				{
				setState(570);
				digit();
				}
				{
				setState(571);
				digit();
				}
				{
				setState(572);
				digit();
				}
				{
				setState(573);
				digit();
				}
				{
				setState(574);
				digit();
				}
				{
				setState(575);
				digit();
				}
				{
				setState(576);
				digit();
				}
				{
				setState(577);
				digit();
				}
				{
				setState(578);
				digit();
				}
				{
				setState(579);
				digit();
				}
				{
				setState(580);
				digit();
				}
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WsContext extends ParserRuleContext {
		public List<SpContext> sp() {
			return getRuleContexts(SpContext.class);
		}
		public SpContext sp(int i) {
			return getRuleContext(SpContext.class,i);
		}
		public List<HtabContext> htab() {
			return getRuleContexts(HtabContext.class);
		}
		public HtabContext htab(int i) {
			return getRuleContext(HtabContext.class,i);
		}
		public List<CrContext> cr() {
			return getRuleContexts(CrContext.class);
		}
		public CrContext cr(int i) {
			return getRuleContext(CrContext.class,i);
		}
		public List<LfContext> lf() {
			return getRuleContexts(LfContext.class);
		}
		public LfContext lf(int i) {
			return getRuleContext(LfContext.class,i);
		}
		public WsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ws; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterWs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitWs(this);
		}
	}

	public final WsContext ws() throws RecognitionException {
		WsContext _localctx = new WsContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_ws);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(590);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(588);
					switch (_input.LA(1)) {
					case SPACE:
						{
						setState(584);
						sp();
						}
						break;
					case TAB:
						{
						setState(585);
						htab();
						}
						break;
					case CR:
						{
						setState(586);
						cr();
						}
						break;
					case LF:
						{
						setState(587);
						lf();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(592);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MwsContext extends ParserRuleContext {
		public List<SpContext> sp() {
			return getRuleContexts(SpContext.class);
		}
		public SpContext sp(int i) {
			return getRuleContext(SpContext.class,i);
		}
		public List<HtabContext> htab() {
			return getRuleContexts(HtabContext.class);
		}
		public HtabContext htab(int i) {
			return getRuleContext(HtabContext.class,i);
		}
		public List<CrContext> cr() {
			return getRuleContexts(CrContext.class);
		}
		public CrContext cr(int i) {
			return getRuleContext(CrContext.class,i);
		}
		public List<LfContext> lf() {
			return getRuleContexts(LfContext.class);
		}
		public LfContext lf(int i) {
			return getRuleContext(LfContext.class,i);
		}
		public MwsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mws; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterMws(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitMws(this);
		}
	}

	public final MwsContext mws() throws RecognitionException {
		MwsContext _localctx = new MwsContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_mws);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(597); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(597);
					switch (_input.LA(1)) {
					case SPACE:
						{
						setState(593);
						sp();
						}
						break;
					case TAB:
						{
						setState(594);
						htab();
						}
						break;
					case CR:
						{
						setState(595);
						cr();
						}
						break;
					case LF:
						{
						setState(596);
						lf();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(599); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SpContext extends ParserRuleContext {
		public TerminalNode SPACE() { return getToken(ExpressionConstraintParser.SPACE, 0); }
		public SpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterSp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitSp(this);
		}
	}

	public final SpContext sp() throws RecognitionException {
		SpContext _localctx = new SpContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_sp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(601);
			match(SPACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HtabContext extends ParserRuleContext {
		public TerminalNode TAB() { return getToken(ExpressionConstraintParser.TAB, 0); }
		public HtabContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htab; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterHtab(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitHtab(this);
		}
	}

	public final HtabContext htab() throws RecognitionException {
		HtabContext _localctx = new HtabContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_htab);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(603);
			match(TAB);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CrContext extends ParserRuleContext {
		public TerminalNode CR() { return getToken(ExpressionConstraintParser.CR, 0); }
		public CrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterCr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitCr(this);
		}
	}

	public final CrContext cr() throws RecognitionException {
		CrContext _localctx = new CrContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_cr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(605);
			match(CR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LfContext extends ParserRuleContext {
		public TerminalNode LF() { return getToken(ExpressionConstraintParser.LF, 0); }
		public LfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterLf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitLf(this);
		}
	}

	public final LfContext lf() throws RecognitionException {
		LfContext _localctx = new LfContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_lf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(607);
			match(LF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QmContext extends ParserRuleContext {
		public TerminalNode QUOTE() { return getToken(ExpressionConstraintParser.QUOTE, 0); }
		public QmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterQm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitQm(this);
		}
	}

	public final QmContext qm() throws RecognitionException {
		QmContext _localctx = new QmContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_qm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(609);
			match(QUOTE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BsContext extends ParserRuleContext {
		public TerminalNode BACKSLASH() { return getToken(ExpressionConstraintParser.BACKSLASH, 0); }
		public BsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterBs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitBs(this);
		}
	}

	public final BsContext bs() throws RecognitionException {
		BsContext _localctx = new BsContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_bs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(611);
			match(BACKSLASH);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DigitContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(ExpressionConstraintParser.ZERO, 0); }
		public TerminalNode ONE() { return getToken(ExpressionConstraintParser.ONE, 0); }
		public TerminalNode TWO() { return getToken(ExpressionConstraintParser.TWO, 0); }
		public TerminalNode THREE() { return getToken(ExpressionConstraintParser.THREE, 0); }
		public TerminalNode FOUR() { return getToken(ExpressionConstraintParser.FOUR, 0); }
		public TerminalNode FIVE() { return getToken(ExpressionConstraintParser.FIVE, 0); }
		public TerminalNode SIX() { return getToken(ExpressionConstraintParser.SIX, 0); }
		public TerminalNode SEVEN() { return getToken(ExpressionConstraintParser.SEVEN, 0); }
		public TerminalNode EIGHT() { return getToken(ExpressionConstraintParser.EIGHT, 0); }
		public TerminalNode NINE() { return getToken(ExpressionConstraintParser.NINE, 0); }
		public DigitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDigit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDigit(this);
		}
	}

	public final DigitContext digit() throws RecognitionException {
		DigitContext _localctx = new DigitContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(613);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ZeroContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(ExpressionConstraintParser.ZERO, 0); }
		public ZeroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_zero; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterZero(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitZero(this);
		}
	}

	public final ZeroContext zero() throws RecognitionException {
		ZeroContext _localctx = new ZeroContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_zero);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(615);
			match(ZERO);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DigitnonzeroContext extends ParserRuleContext {
		public TerminalNode ONE() { return getToken(ExpressionConstraintParser.ONE, 0); }
		public TerminalNode TWO() { return getToken(ExpressionConstraintParser.TWO, 0); }
		public TerminalNode THREE() { return getToken(ExpressionConstraintParser.THREE, 0); }
		public TerminalNode FOUR() { return getToken(ExpressionConstraintParser.FOUR, 0); }
		public TerminalNode FIVE() { return getToken(ExpressionConstraintParser.FIVE, 0); }
		public TerminalNode SIX() { return getToken(ExpressionConstraintParser.SIX, 0); }
		public TerminalNode SEVEN() { return getToken(ExpressionConstraintParser.SEVEN, 0); }
		public TerminalNode EIGHT() { return getToken(ExpressionConstraintParser.EIGHT, 0); }
		public TerminalNode NINE() { return getToken(ExpressionConstraintParser.NINE, 0); }
		public DigitnonzeroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digitnonzero; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterDigitnonzero(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitDigitnonzero(this);
		}
	}

	public final DigitnonzeroContext digitnonzero() throws RecognitionException {
		DigitnonzeroContext _localctx = new DigitnonzeroContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_digitnonzero);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(617);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonwsnonpipeContext extends ParserRuleContext {
		public TerminalNode EXCLAMATION() { return getToken(ExpressionConstraintParser.EXCLAMATION, 0); }
		public TerminalNode QUOTE() { return getToken(ExpressionConstraintParser.QUOTE, 0); }
		public TerminalNode POUND() { return getToken(ExpressionConstraintParser.POUND, 0); }
		public TerminalNode DOLLAR() { return getToken(ExpressionConstraintParser.DOLLAR, 0); }
		public TerminalNode PERCENT() { return getToken(ExpressionConstraintParser.PERCENT, 0); }
		public TerminalNode AMPERSAND() { return getToken(ExpressionConstraintParser.AMPERSAND, 0); }
		public TerminalNode APOSTROPHE() { return getToken(ExpressionConstraintParser.APOSTROPHE, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ExpressionConstraintParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ExpressionConstraintParser.RIGHT_PAREN, 0); }
		public TerminalNode ASTERISK() { return getToken(ExpressionConstraintParser.ASTERISK, 0); }
		public TerminalNode PLUS() { return getToken(ExpressionConstraintParser.PLUS, 0); }
		public TerminalNode COMMA() { return getToken(ExpressionConstraintParser.COMMA, 0); }
		public TerminalNode DASH() { return getToken(ExpressionConstraintParser.DASH, 0); }
		public TerminalNode PERIOD() { return getToken(ExpressionConstraintParser.PERIOD, 0); }
		public TerminalNode SLASH() { return getToken(ExpressionConstraintParser.SLASH, 0); }
		public TerminalNode ZERO() { return getToken(ExpressionConstraintParser.ZERO, 0); }
		public TerminalNode ONE() { return getToken(ExpressionConstraintParser.ONE, 0); }
		public TerminalNode TWO() { return getToken(ExpressionConstraintParser.TWO, 0); }
		public TerminalNode THREE() { return getToken(ExpressionConstraintParser.THREE, 0); }
		public TerminalNode FOUR() { return getToken(ExpressionConstraintParser.FOUR, 0); }
		public TerminalNode FIVE() { return getToken(ExpressionConstraintParser.FIVE, 0); }
		public TerminalNode SIX() { return getToken(ExpressionConstraintParser.SIX, 0); }
		public TerminalNode SEVEN() { return getToken(ExpressionConstraintParser.SEVEN, 0); }
		public TerminalNode EIGHT() { return getToken(ExpressionConstraintParser.EIGHT, 0); }
		public TerminalNode NINE() { return getToken(ExpressionConstraintParser.NINE, 0); }
		public TerminalNode COLON() { return getToken(ExpressionConstraintParser.COLON, 0); }
		public TerminalNode SEMICOLON() { return getToken(ExpressionConstraintParser.SEMICOLON, 0); }
		public TerminalNode LESS_THAN() { return getToken(ExpressionConstraintParser.LESS_THAN, 0); }
		public TerminalNode EQUALS() { return getToken(ExpressionConstraintParser.EQUALS, 0); }
		public TerminalNode GREATER_THAN() { return getToken(ExpressionConstraintParser.GREATER_THAN, 0); }
		public TerminalNode QUESTION() { return getToken(ExpressionConstraintParser.QUESTION, 0); }
		public TerminalNode AT() { return getToken(ExpressionConstraintParser.AT, 0); }
		public TerminalNode CAP_A() { return getToken(ExpressionConstraintParser.CAP_A, 0); }
		public TerminalNode CAP_B() { return getToken(ExpressionConstraintParser.CAP_B, 0); }
		public TerminalNode CAP_C() { return getToken(ExpressionConstraintParser.CAP_C, 0); }
		public TerminalNode CAP_D() { return getToken(ExpressionConstraintParser.CAP_D, 0); }
		public TerminalNode CAP_E() { return getToken(ExpressionConstraintParser.CAP_E, 0); }
		public TerminalNode CAP_F() { return getToken(ExpressionConstraintParser.CAP_F, 0); }
		public TerminalNode CAP_G() { return getToken(ExpressionConstraintParser.CAP_G, 0); }
		public TerminalNode CAP_H() { return getToken(ExpressionConstraintParser.CAP_H, 0); }
		public TerminalNode CAP_I() { return getToken(ExpressionConstraintParser.CAP_I, 0); }
		public TerminalNode CAP_J() { return getToken(ExpressionConstraintParser.CAP_J, 0); }
		public TerminalNode CAP_K() { return getToken(ExpressionConstraintParser.CAP_K, 0); }
		public TerminalNode CAP_L() { return getToken(ExpressionConstraintParser.CAP_L, 0); }
		public TerminalNode CAP_M() { return getToken(ExpressionConstraintParser.CAP_M, 0); }
		public TerminalNode CAP_N() { return getToken(ExpressionConstraintParser.CAP_N, 0); }
		public TerminalNode CAP_O() { return getToken(ExpressionConstraintParser.CAP_O, 0); }
		public TerminalNode CAP_P() { return getToken(ExpressionConstraintParser.CAP_P, 0); }
		public TerminalNode CAP_Q() { return getToken(ExpressionConstraintParser.CAP_Q, 0); }
		public TerminalNode CAP_R() { return getToken(ExpressionConstraintParser.CAP_R, 0); }
		public TerminalNode CAP_S() { return getToken(ExpressionConstraintParser.CAP_S, 0); }
		public TerminalNode CAP_T() { return getToken(ExpressionConstraintParser.CAP_T, 0); }
		public TerminalNode CAP_U() { return getToken(ExpressionConstraintParser.CAP_U, 0); }
		public TerminalNode CAP_V() { return getToken(ExpressionConstraintParser.CAP_V, 0); }
		public TerminalNode CAP_W() { return getToken(ExpressionConstraintParser.CAP_W, 0); }
		public TerminalNode CAP_X() { return getToken(ExpressionConstraintParser.CAP_X, 0); }
		public TerminalNode CAP_Y() { return getToken(ExpressionConstraintParser.CAP_Y, 0); }
		public TerminalNode CAP_Z() { return getToken(ExpressionConstraintParser.CAP_Z, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(ExpressionConstraintParser.LEFT_BRACE, 0); }
		public TerminalNode BACKSLASH() { return getToken(ExpressionConstraintParser.BACKSLASH, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(ExpressionConstraintParser.RIGHT_BRACE, 0); }
		public TerminalNode CARAT() { return getToken(ExpressionConstraintParser.CARAT, 0); }
		public TerminalNode UNDERSCORE() { return getToken(ExpressionConstraintParser.UNDERSCORE, 0); }
		public TerminalNode ACCENT() { return getToken(ExpressionConstraintParser.ACCENT, 0); }
		public TerminalNode A() { return getToken(ExpressionConstraintParser.A, 0); }
		public TerminalNode B() { return getToken(ExpressionConstraintParser.B, 0); }
		public TerminalNode C() { return getToken(ExpressionConstraintParser.C, 0); }
		public TerminalNode D() { return getToken(ExpressionConstraintParser.D, 0); }
		public TerminalNode E() { return getToken(ExpressionConstraintParser.E, 0); }
		public TerminalNode F() { return getToken(ExpressionConstraintParser.F, 0); }
		public TerminalNode G() { return getToken(ExpressionConstraintParser.G, 0); }
		public TerminalNode H() { return getToken(ExpressionConstraintParser.H, 0); }
		public TerminalNode I() { return getToken(ExpressionConstraintParser.I, 0); }
		public TerminalNode J() { return getToken(ExpressionConstraintParser.J, 0); }
		public TerminalNode K() { return getToken(ExpressionConstraintParser.K, 0); }
		public TerminalNode L() { return getToken(ExpressionConstraintParser.L, 0); }
		public TerminalNode M() { return getToken(ExpressionConstraintParser.M, 0); }
		public TerminalNode N() { return getToken(ExpressionConstraintParser.N, 0); }
		public TerminalNode O() { return getToken(ExpressionConstraintParser.O, 0); }
		public TerminalNode P() { return getToken(ExpressionConstraintParser.P, 0); }
		public TerminalNode Q() { return getToken(ExpressionConstraintParser.Q, 0); }
		public TerminalNode R() { return getToken(ExpressionConstraintParser.R, 0); }
		public TerminalNode S() { return getToken(ExpressionConstraintParser.S, 0); }
		public TerminalNode T() { return getToken(ExpressionConstraintParser.T, 0); }
		public TerminalNode U() { return getToken(ExpressionConstraintParser.U, 0); }
		public TerminalNode V() { return getToken(ExpressionConstraintParser.V, 0); }
		public TerminalNode W() { return getToken(ExpressionConstraintParser.W, 0); }
		public TerminalNode X() { return getToken(ExpressionConstraintParser.X, 0); }
		public TerminalNode Y() { return getToken(ExpressionConstraintParser.Y, 0); }
		public TerminalNode Z() { return getToken(ExpressionConstraintParser.Z, 0); }
		public TerminalNode LEFT_CURLY_BRACE() { return getToken(ExpressionConstraintParser.LEFT_CURLY_BRACE, 0); }
		public TerminalNode RIGHT_CURLY_BRACE() { return getToken(ExpressionConstraintParser.RIGHT_CURLY_BRACE, 0); }
		public TerminalNode TILDE() { return getToken(ExpressionConstraintParser.TILDE, 0); }
		public Utf8_2Context utf8_2() {
			return getRuleContext(Utf8_2Context.class,0);
		}
		public Utf8_3Context utf8_3() {
			return getRuleContext(Utf8_3Context.class,0);
		}
		public Utf8_4Context utf8_4() {
			return getRuleContext(Utf8_4Context.class,0);
		}
		public NonwsnonpipeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonwsnonpipe; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterNonwsnonpipe(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitNonwsnonpipe(this);
		}
	}

	public final NonwsnonpipeContext nonwsnonpipe() throws RecognitionException {
		NonwsnonpipeContext _localctx = new NonwsnonpipeContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_nonwsnonpipe);
		int _la;
		try {
			setState(624);
			switch (_input.LA(1)) {
			case EXCLAMATION:
			case QUOTE:
			case POUND:
			case DOLLAR:
			case PERCENT:
			case AMPERSAND:
			case APOSTROPHE:
			case LEFT_PAREN:
			case RIGHT_PAREN:
			case ASTERISK:
			case PLUS:
			case COMMA:
			case DASH:
			case PERIOD:
			case SLASH:
			case ZERO:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
			case COLON:
			case SEMICOLON:
			case LESS_THAN:
			case EQUALS:
			case GREATER_THAN:
			case QUESTION:
			case AT:
			case CAP_A:
			case CAP_B:
			case CAP_C:
			case CAP_D:
			case CAP_E:
			case CAP_F:
			case CAP_G:
			case CAP_H:
			case CAP_I:
			case CAP_J:
			case CAP_K:
			case CAP_L:
			case CAP_M:
			case CAP_N:
			case CAP_O:
			case CAP_P:
			case CAP_Q:
			case CAP_R:
			case CAP_S:
			case CAP_T:
			case CAP_U:
			case CAP_V:
			case CAP_W:
			case CAP_X:
			case CAP_Y:
			case CAP_Z:
			case LEFT_BRACE:
			case BACKSLASH:
			case RIGHT_BRACE:
			case CARAT:
			case UNDERSCORE:
			case ACCENT:
			case A:
			case B:
			case C:
			case D:
			case E:
			case F:
			case G:
			case H:
			case I:
			case J:
			case K:
			case L:
			case M:
			case N:
			case O:
			case P:
			case Q:
			case R:
			case S:
			case T:
			case U:
			case V:
			case W:
			case X:
			case Y:
			case Z:
			case LEFT_CURLY_BRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(619);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EXCLAMATION) | (1L << QUOTE) | (1L << POUND) | (1L << DOLLAR) | (1L << PERCENT) | (1L << AMPERSAND) | (1L << APOSTROPHE) | (1L << LEFT_PAREN) | (1L << RIGHT_PAREN) | (1L << ASTERISK) | (1L << PLUS) | (1L << COMMA) | (1L << DASH) | (1L << PERIOD) | (1L << SLASH) | (1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE) | (1L << COLON) | (1L << SEMICOLON) | (1L << LESS_THAN) | (1L << EQUALS) | (1L << GREATER_THAN) | (1L << QUESTION) | (1L << AT) | (1L << CAP_A) | (1L << CAP_B) | (1L << CAP_C) | (1L << CAP_D) | (1L << CAP_E) | (1L << CAP_F) | (1L << CAP_G) | (1L << CAP_H) | (1L << CAP_I) | (1L << CAP_J) | (1L << CAP_K) | (1L << CAP_L) | (1L << CAP_M) | (1L << CAP_N) | (1L << CAP_O) | (1L << CAP_P) | (1L << CAP_Q) | (1L << CAP_R) | (1L << CAP_S) | (1L << CAP_T) | (1L << CAP_U) | (1L << CAP_V) | (1L << CAP_W) | (1L << CAP_X) | (1L << CAP_Y) | (1L << CAP_Z) | (1L << LEFT_BRACE))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (BACKSLASH - 64)) | (1L << (RIGHT_BRACE - 64)) | (1L << (CARAT - 64)) | (1L << (UNDERSCORE - 64)) | (1L << (ACCENT - 64)) | (1L << (A - 64)) | (1L << (B - 64)) | (1L << (C - 64)) | (1L << (D - 64)) | (1L << (E - 64)) | (1L << (F - 64)) | (1L << (G - 64)) | (1L << (H - 64)) | (1L << (I - 64)) | (1L << (J - 64)) | (1L << (K - 64)) | (1L << (L - 64)) | (1L << (M - 64)) | (1L << (N - 64)) | (1L << (O - 64)) | (1L << (P - 64)) | (1L << (Q - 64)) | (1L << (R - 64)) | (1L << (S - 64)) | (1L << (T - 64)) | (1L << (U - 64)) | (1L << (V - 64)) | (1L << (W - 64)) | (1L << (X - 64)) | (1L << (Y - 64)) | (1L << (Z - 64)) | (1L << (LEFT_CURLY_BRACE - 64)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case RIGHT_CURLY_BRACE:
			case TILDE:
				enterOuterAlt(_localctx, 2);
				{
				setState(620);
				_la = _input.LA(1);
				if ( !(_la==RIGHT_CURLY_BRACE || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case U_00C2:
			case U_00C3:
			case U_00C4:
			case U_00C5:
			case U_00C6:
			case U_00C7:
			case U_00C8:
			case U_00C9:
			case U_00CA:
			case U_00CB:
			case U_00CC:
			case U_00CD:
			case U_00CE:
			case U_00CF:
			case U_00D0:
			case U_00D1:
			case U_00D2:
			case U_00D3:
			case U_00D4:
			case U_00D5:
			case U_00D6:
			case U_00D7:
			case U_00D8:
			case U_00D9:
			case U_00DA:
			case U_00DB:
			case U_00DC:
			case U_00DD:
			case U_00DE:
			case U_00DF:
				enterOuterAlt(_localctx, 3);
				{
				setState(621);
				utf8_2();
				}
				break;
			case U_00E0:
			case U_00E1:
			case U_00E2:
			case U_00E3:
			case U_00E4:
			case U_00E5:
			case U_00E6:
			case U_00E7:
			case U_00E8:
			case U_00E9:
			case U_00EA:
			case U_00EB:
			case U_00EC:
			case U_00ED:
			case U_00EE:
			case U_00EF:
				enterOuterAlt(_localctx, 4);
				{
				setState(622);
				utf8_3();
				}
				break;
			case U_00F0:
			case U_00F1:
			case U_00F2:
			case U_00F3:
			case U_00F4:
				enterOuterAlt(_localctx, 5);
				{
				setState(623);
				utf8_4();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnynonescapedcharContext extends ParserRuleContext {
		public HtabContext htab() {
			return getRuleContext(HtabContext.class,0);
		}
		public CrContext cr() {
			return getRuleContext(CrContext.class,0);
		}
		public LfContext lf() {
			return getRuleContext(LfContext.class,0);
		}
		public TerminalNode SPACE() { return getToken(ExpressionConstraintParser.SPACE, 0); }
		public TerminalNode EXCLAMATION() { return getToken(ExpressionConstraintParser.EXCLAMATION, 0); }
		public TerminalNode POUND() { return getToken(ExpressionConstraintParser.POUND, 0); }
		public TerminalNode DOLLAR() { return getToken(ExpressionConstraintParser.DOLLAR, 0); }
		public TerminalNode PERCENT() { return getToken(ExpressionConstraintParser.PERCENT, 0); }
		public TerminalNode AMPERSAND() { return getToken(ExpressionConstraintParser.AMPERSAND, 0); }
		public TerminalNode APOSTROPHE() { return getToken(ExpressionConstraintParser.APOSTROPHE, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(ExpressionConstraintParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(ExpressionConstraintParser.RIGHT_PAREN, 0); }
		public TerminalNode ASTERISK() { return getToken(ExpressionConstraintParser.ASTERISK, 0); }
		public TerminalNode PLUS() { return getToken(ExpressionConstraintParser.PLUS, 0); }
		public TerminalNode COMMA() { return getToken(ExpressionConstraintParser.COMMA, 0); }
		public TerminalNode DASH() { return getToken(ExpressionConstraintParser.DASH, 0); }
		public TerminalNode PERIOD() { return getToken(ExpressionConstraintParser.PERIOD, 0); }
		public TerminalNode SLASH() { return getToken(ExpressionConstraintParser.SLASH, 0); }
		public TerminalNode ZERO() { return getToken(ExpressionConstraintParser.ZERO, 0); }
		public TerminalNode ONE() { return getToken(ExpressionConstraintParser.ONE, 0); }
		public TerminalNode TWO() { return getToken(ExpressionConstraintParser.TWO, 0); }
		public TerminalNode THREE() { return getToken(ExpressionConstraintParser.THREE, 0); }
		public TerminalNode FOUR() { return getToken(ExpressionConstraintParser.FOUR, 0); }
		public TerminalNode FIVE() { return getToken(ExpressionConstraintParser.FIVE, 0); }
		public TerminalNode SIX() { return getToken(ExpressionConstraintParser.SIX, 0); }
		public TerminalNode SEVEN() { return getToken(ExpressionConstraintParser.SEVEN, 0); }
		public TerminalNode EIGHT() { return getToken(ExpressionConstraintParser.EIGHT, 0); }
		public TerminalNode NINE() { return getToken(ExpressionConstraintParser.NINE, 0); }
		public TerminalNode COLON() { return getToken(ExpressionConstraintParser.COLON, 0); }
		public TerminalNode SEMICOLON() { return getToken(ExpressionConstraintParser.SEMICOLON, 0); }
		public TerminalNode LESS_THAN() { return getToken(ExpressionConstraintParser.LESS_THAN, 0); }
		public TerminalNode EQUALS() { return getToken(ExpressionConstraintParser.EQUALS, 0); }
		public TerminalNode GREATER_THAN() { return getToken(ExpressionConstraintParser.GREATER_THAN, 0); }
		public TerminalNode QUESTION() { return getToken(ExpressionConstraintParser.QUESTION, 0); }
		public TerminalNode AT() { return getToken(ExpressionConstraintParser.AT, 0); }
		public TerminalNode CAP_A() { return getToken(ExpressionConstraintParser.CAP_A, 0); }
		public TerminalNode CAP_B() { return getToken(ExpressionConstraintParser.CAP_B, 0); }
		public TerminalNode CAP_C() { return getToken(ExpressionConstraintParser.CAP_C, 0); }
		public TerminalNode CAP_D() { return getToken(ExpressionConstraintParser.CAP_D, 0); }
		public TerminalNode CAP_E() { return getToken(ExpressionConstraintParser.CAP_E, 0); }
		public TerminalNode CAP_F() { return getToken(ExpressionConstraintParser.CAP_F, 0); }
		public TerminalNode CAP_G() { return getToken(ExpressionConstraintParser.CAP_G, 0); }
		public TerminalNode CAP_H() { return getToken(ExpressionConstraintParser.CAP_H, 0); }
		public TerminalNode CAP_I() { return getToken(ExpressionConstraintParser.CAP_I, 0); }
		public TerminalNode CAP_J() { return getToken(ExpressionConstraintParser.CAP_J, 0); }
		public TerminalNode CAP_K() { return getToken(ExpressionConstraintParser.CAP_K, 0); }
		public TerminalNode CAP_L() { return getToken(ExpressionConstraintParser.CAP_L, 0); }
		public TerminalNode CAP_M() { return getToken(ExpressionConstraintParser.CAP_M, 0); }
		public TerminalNode CAP_N() { return getToken(ExpressionConstraintParser.CAP_N, 0); }
		public TerminalNode CAP_O() { return getToken(ExpressionConstraintParser.CAP_O, 0); }
		public TerminalNode CAP_P() { return getToken(ExpressionConstraintParser.CAP_P, 0); }
		public TerminalNode CAP_Q() { return getToken(ExpressionConstraintParser.CAP_Q, 0); }
		public TerminalNode CAP_R() { return getToken(ExpressionConstraintParser.CAP_R, 0); }
		public TerminalNode CAP_S() { return getToken(ExpressionConstraintParser.CAP_S, 0); }
		public TerminalNode CAP_T() { return getToken(ExpressionConstraintParser.CAP_T, 0); }
		public TerminalNode CAP_U() { return getToken(ExpressionConstraintParser.CAP_U, 0); }
		public TerminalNode CAP_V() { return getToken(ExpressionConstraintParser.CAP_V, 0); }
		public TerminalNode CAP_W() { return getToken(ExpressionConstraintParser.CAP_W, 0); }
		public TerminalNode CAP_X() { return getToken(ExpressionConstraintParser.CAP_X, 0); }
		public TerminalNode CAP_Y() { return getToken(ExpressionConstraintParser.CAP_Y, 0); }
		public TerminalNode CAP_Z() { return getToken(ExpressionConstraintParser.CAP_Z, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(ExpressionConstraintParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(ExpressionConstraintParser.RIGHT_BRACE, 0); }
		public TerminalNode CARAT() { return getToken(ExpressionConstraintParser.CARAT, 0); }
		public TerminalNode UNDERSCORE() { return getToken(ExpressionConstraintParser.UNDERSCORE, 0); }
		public TerminalNode ACCENT() { return getToken(ExpressionConstraintParser.ACCENT, 0); }
		public TerminalNode A() { return getToken(ExpressionConstraintParser.A, 0); }
		public TerminalNode B() { return getToken(ExpressionConstraintParser.B, 0); }
		public TerminalNode C() { return getToken(ExpressionConstraintParser.C, 0); }
		public TerminalNode D() { return getToken(ExpressionConstraintParser.D, 0); }
		public TerminalNode E() { return getToken(ExpressionConstraintParser.E, 0); }
		public TerminalNode F() { return getToken(ExpressionConstraintParser.F, 0); }
		public TerminalNode G() { return getToken(ExpressionConstraintParser.G, 0); }
		public TerminalNode H() { return getToken(ExpressionConstraintParser.H, 0); }
		public TerminalNode I() { return getToken(ExpressionConstraintParser.I, 0); }
		public TerminalNode J() { return getToken(ExpressionConstraintParser.J, 0); }
		public TerminalNode K() { return getToken(ExpressionConstraintParser.K, 0); }
		public TerminalNode L() { return getToken(ExpressionConstraintParser.L, 0); }
		public TerminalNode M() { return getToken(ExpressionConstraintParser.M, 0); }
		public TerminalNode N() { return getToken(ExpressionConstraintParser.N, 0); }
		public TerminalNode O() { return getToken(ExpressionConstraintParser.O, 0); }
		public TerminalNode P() { return getToken(ExpressionConstraintParser.P, 0); }
		public TerminalNode Q() { return getToken(ExpressionConstraintParser.Q, 0); }
		public TerminalNode R() { return getToken(ExpressionConstraintParser.R, 0); }
		public TerminalNode S() { return getToken(ExpressionConstraintParser.S, 0); }
		public TerminalNode T() { return getToken(ExpressionConstraintParser.T, 0); }
		public TerminalNode U() { return getToken(ExpressionConstraintParser.U, 0); }
		public TerminalNode V() { return getToken(ExpressionConstraintParser.V, 0); }
		public TerminalNode W() { return getToken(ExpressionConstraintParser.W, 0); }
		public TerminalNode X() { return getToken(ExpressionConstraintParser.X, 0); }
		public TerminalNode Y() { return getToken(ExpressionConstraintParser.Y, 0); }
		public TerminalNode Z() { return getToken(ExpressionConstraintParser.Z, 0); }
		public TerminalNode LEFT_CURLY_BRACE() { return getToken(ExpressionConstraintParser.LEFT_CURLY_BRACE, 0); }
		public TerminalNode PIPE() { return getToken(ExpressionConstraintParser.PIPE, 0); }
		public TerminalNode RIGHT_CURLY_BRACE() { return getToken(ExpressionConstraintParser.RIGHT_CURLY_BRACE, 0); }
		public TerminalNode TILDE() { return getToken(ExpressionConstraintParser.TILDE, 0); }
		public Utf8_2Context utf8_2() {
			return getRuleContext(Utf8_2Context.class,0);
		}
		public Utf8_3Context utf8_3() {
			return getRuleContext(Utf8_3Context.class,0);
		}
		public Utf8_4Context utf8_4() {
			return getRuleContext(Utf8_4Context.class,0);
		}
		public AnynonescapedcharContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anynonescapedchar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterAnynonescapedchar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitAnynonescapedchar(this);
		}
	}

	public final AnynonescapedcharContext anynonescapedchar() throws RecognitionException {
		AnynonescapedcharContext _localctx = new AnynonescapedcharContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_anynonescapedchar);
		int _la;
		try {
			setState(635);
			switch (_input.LA(1)) {
			case TAB:
				enterOuterAlt(_localctx, 1);
				{
				setState(626);
				htab();
				}
				break;
			case CR:
				enterOuterAlt(_localctx, 2);
				{
				setState(627);
				cr();
				}
				break;
			case LF:
				enterOuterAlt(_localctx, 3);
				{
				setState(628);
				lf();
				}
				break;
			case SPACE:
			case EXCLAMATION:
				enterOuterAlt(_localctx, 4);
				{
				setState(629);
				_la = _input.LA(1);
				if ( !(_la==SPACE || _la==EXCLAMATION) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case POUND:
			case DOLLAR:
			case PERCENT:
			case AMPERSAND:
			case APOSTROPHE:
			case LEFT_PAREN:
			case RIGHT_PAREN:
			case ASTERISK:
			case PLUS:
			case COMMA:
			case DASH:
			case PERIOD:
			case SLASH:
			case ZERO:
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case NINE:
			case COLON:
			case SEMICOLON:
			case LESS_THAN:
			case EQUALS:
			case GREATER_THAN:
			case QUESTION:
			case AT:
			case CAP_A:
			case CAP_B:
			case CAP_C:
			case CAP_D:
			case CAP_E:
			case CAP_F:
			case CAP_G:
			case CAP_H:
			case CAP_I:
			case CAP_J:
			case CAP_K:
			case CAP_L:
			case CAP_M:
			case CAP_N:
			case CAP_O:
			case CAP_P:
			case CAP_Q:
			case CAP_R:
			case CAP_S:
			case CAP_T:
			case CAP_U:
			case CAP_V:
			case CAP_W:
			case CAP_X:
			case CAP_Y:
			case CAP_Z:
			case LEFT_BRACE:
				enterOuterAlt(_localctx, 5);
				{
				setState(630);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << POUND) | (1L << DOLLAR) | (1L << PERCENT) | (1L << AMPERSAND) | (1L << APOSTROPHE) | (1L << LEFT_PAREN) | (1L << RIGHT_PAREN) | (1L << ASTERISK) | (1L << PLUS) | (1L << COMMA) | (1L << DASH) | (1L << PERIOD) | (1L << SLASH) | (1L << ZERO) | (1L << ONE) | (1L << TWO) | (1L << THREE) | (1L << FOUR) | (1L << FIVE) | (1L << SIX) | (1L << SEVEN) | (1L << EIGHT) | (1L << NINE) | (1L << COLON) | (1L << SEMICOLON) | (1L << LESS_THAN) | (1L << EQUALS) | (1L << GREATER_THAN) | (1L << QUESTION) | (1L << AT) | (1L << CAP_A) | (1L << CAP_B) | (1L << CAP_C) | (1L << CAP_D) | (1L << CAP_E) | (1L << CAP_F) | (1L << CAP_G) | (1L << CAP_H) | (1L << CAP_I) | (1L << CAP_J) | (1L << CAP_K) | (1L << CAP_L) | (1L << CAP_M) | (1L << CAP_N) | (1L << CAP_O) | (1L << CAP_P) | (1L << CAP_Q) | (1L << CAP_R) | (1L << CAP_S) | (1L << CAP_T) | (1L << CAP_U) | (1L << CAP_V) | (1L << CAP_W) | (1L << CAP_X) | (1L << CAP_Y) | (1L << CAP_Z) | (1L << LEFT_BRACE))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case RIGHT_BRACE:
			case CARAT:
			case UNDERSCORE:
			case ACCENT:
			case A:
			case B:
			case C:
			case D:
			case E:
			case F:
			case G:
			case H:
			case I:
			case J:
			case K:
			case L:
			case M:
			case N:
			case O:
			case P:
			case Q:
			case R:
			case S:
			case T:
			case U:
			case V:
			case W:
			case X:
			case Y:
			case Z:
			case LEFT_CURLY_BRACE:
			case PIPE:
			case RIGHT_CURLY_BRACE:
			case TILDE:
				enterOuterAlt(_localctx, 6);
				{
				setState(631);
				_la = _input.LA(1);
				if ( !(((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (RIGHT_BRACE - 65)) | (1L << (CARAT - 65)) | (1L << (UNDERSCORE - 65)) | (1L << (ACCENT - 65)) | (1L << (A - 65)) | (1L << (B - 65)) | (1L << (C - 65)) | (1L << (D - 65)) | (1L << (E - 65)) | (1L << (F - 65)) | (1L << (G - 65)) | (1L << (H - 65)) | (1L << (I - 65)) | (1L << (J - 65)) | (1L << (K - 65)) | (1L << (L - 65)) | (1L << (M - 65)) | (1L << (N - 65)) | (1L << (O - 65)) | (1L << (P - 65)) | (1L << (Q - 65)) | (1L << (R - 65)) | (1L << (S - 65)) | (1L << (T - 65)) | (1L << (U - 65)) | (1L << (V - 65)) | (1L << (W - 65)) | (1L << (X - 65)) | (1L << (Y - 65)) | (1L << (Z - 65)) | (1L << (LEFT_CURLY_BRACE - 65)) | (1L << (PIPE - 65)) | (1L << (RIGHT_CURLY_BRACE - 65)) | (1L << (TILDE - 65)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case U_00C2:
			case U_00C3:
			case U_00C4:
			case U_00C5:
			case U_00C6:
			case U_00C7:
			case U_00C8:
			case U_00C9:
			case U_00CA:
			case U_00CB:
			case U_00CC:
			case U_00CD:
			case U_00CE:
			case U_00CF:
			case U_00D0:
			case U_00D1:
			case U_00D2:
			case U_00D3:
			case U_00D4:
			case U_00D5:
			case U_00D6:
			case U_00D7:
			case U_00D8:
			case U_00D9:
			case U_00DA:
			case U_00DB:
			case U_00DC:
			case U_00DD:
			case U_00DE:
			case U_00DF:
				enterOuterAlt(_localctx, 7);
				{
				setState(632);
				utf8_2();
				}
				break;
			case U_00E0:
			case U_00E1:
			case U_00E2:
			case U_00E3:
			case U_00E4:
			case U_00E5:
			case U_00E6:
			case U_00E7:
			case U_00E8:
			case U_00E9:
			case U_00EA:
			case U_00EB:
			case U_00EC:
			case U_00ED:
			case U_00EE:
			case U_00EF:
				enterOuterAlt(_localctx, 8);
				{
				setState(633);
				utf8_3();
				}
				break;
			case U_00F0:
			case U_00F1:
			case U_00F2:
			case U_00F3:
			case U_00F4:
				enterOuterAlt(_localctx, 9);
				{
				setState(634);
				utf8_4();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EscapedcharContext extends ParserRuleContext {
		public List<BsContext> bs() {
			return getRuleContexts(BsContext.class);
		}
		public BsContext bs(int i) {
			return getRuleContext(BsContext.class,i);
		}
		public QmContext qm() {
			return getRuleContext(QmContext.class,0);
		}
		public EscapedcharContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_escapedchar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterEscapedchar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitEscapedchar(this);
		}
	}

	public final EscapedcharContext escapedchar() throws RecognitionException {
		EscapedcharContext _localctx = new EscapedcharContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_escapedchar);
		try {
			setState(643);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(637);
				bs();
				setState(638);
				qm();
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(640);
				bs();
				setState(641);
				bs();
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Utf8_2Context extends ParserRuleContext {
		public Utf8_tailContext utf8_tail() {
			return getRuleContext(Utf8_tailContext.class,0);
		}
		public TerminalNode U_00C2() { return getToken(ExpressionConstraintParser.U_00C2, 0); }
		public TerminalNode U_00C3() { return getToken(ExpressionConstraintParser.U_00C3, 0); }
		public TerminalNode U_00C4() { return getToken(ExpressionConstraintParser.U_00C4, 0); }
		public TerminalNode U_00C5() { return getToken(ExpressionConstraintParser.U_00C5, 0); }
		public TerminalNode U_00C6() { return getToken(ExpressionConstraintParser.U_00C6, 0); }
		public TerminalNode U_00C7() { return getToken(ExpressionConstraintParser.U_00C7, 0); }
		public TerminalNode U_00C8() { return getToken(ExpressionConstraintParser.U_00C8, 0); }
		public TerminalNode U_00C9() { return getToken(ExpressionConstraintParser.U_00C9, 0); }
		public TerminalNode U_00CA() { return getToken(ExpressionConstraintParser.U_00CA, 0); }
		public TerminalNode U_00CB() { return getToken(ExpressionConstraintParser.U_00CB, 0); }
		public TerminalNode U_00CC() { return getToken(ExpressionConstraintParser.U_00CC, 0); }
		public TerminalNode U_00CD() { return getToken(ExpressionConstraintParser.U_00CD, 0); }
		public TerminalNode U_00CE() { return getToken(ExpressionConstraintParser.U_00CE, 0); }
		public TerminalNode U_00CF() { return getToken(ExpressionConstraintParser.U_00CF, 0); }
		public TerminalNode U_00D0() { return getToken(ExpressionConstraintParser.U_00D0, 0); }
		public TerminalNode U_00D1() { return getToken(ExpressionConstraintParser.U_00D1, 0); }
		public TerminalNode U_00D2() { return getToken(ExpressionConstraintParser.U_00D2, 0); }
		public TerminalNode U_00D3() { return getToken(ExpressionConstraintParser.U_00D3, 0); }
		public TerminalNode U_00D4() { return getToken(ExpressionConstraintParser.U_00D4, 0); }
		public TerminalNode U_00D5() { return getToken(ExpressionConstraintParser.U_00D5, 0); }
		public TerminalNode U_00D6() { return getToken(ExpressionConstraintParser.U_00D6, 0); }
		public TerminalNode U_00D7() { return getToken(ExpressionConstraintParser.U_00D7, 0); }
		public TerminalNode U_00D8() { return getToken(ExpressionConstraintParser.U_00D8, 0); }
		public TerminalNode U_00D9() { return getToken(ExpressionConstraintParser.U_00D9, 0); }
		public TerminalNode U_00DA() { return getToken(ExpressionConstraintParser.U_00DA, 0); }
		public TerminalNode U_00DB() { return getToken(ExpressionConstraintParser.U_00DB, 0); }
		public TerminalNode U_00DC() { return getToken(ExpressionConstraintParser.U_00DC, 0); }
		public TerminalNode U_00DD() { return getToken(ExpressionConstraintParser.U_00DD, 0); }
		public TerminalNode U_00DE() { return getToken(ExpressionConstraintParser.U_00DE, 0); }
		public TerminalNode U_00DF() { return getToken(ExpressionConstraintParser.U_00DF, 0); }
		public Utf8_2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_utf8_2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterUtf8_2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitUtf8_2(this);
		}
	}

	public final Utf8_2Context utf8_2() throws RecognitionException {
		Utf8_2Context _localctx = new Utf8_2Context(_ctx, getState());
		enterRule(_localctx, 124, RULE_utf8_2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(645);
			_la = _input.LA(1);
			if ( !(((((_la - 163)) & ~0x3f) == 0 && ((1L << (_la - 163)) & ((1L << (U_00C2 - 163)) | (1L << (U_00C3 - 163)) | (1L << (U_00C4 - 163)) | (1L << (U_00C5 - 163)) | (1L << (U_00C6 - 163)) | (1L << (U_00C7 - 163)) | (1L << (U_00C8 - 163)) | (1L << (U_00C9 - 163)) | (1L << (U_00CA - 163)) | (1L << (U_00CB - 163)) | (1L << (U_00CC - 163)) | (1L << (U_00CD - 163)) | (1L << (U_00CE - 163)) | (1L << (U_00CF - 163)) | (1L << (U_00D0 - 163)) | (1L << (U_00D1 - 163)) | (1L << (U_00D2 - 163)) | (1L << (U_00D3 - 163)) | (1L << (U_00D4 - 163)) | (1L << (U_00D5 - 163)) | (1L << (U_00D6 - 163)) | (1L << (U_00D7 - 163)) | (1L << (U_00D8 - 163)) | (1L << (U_00D9 - 163)) | (1L << (U_00DA - 163)) | (1L << (U_00DB - 163)) | (1L << (U_00DC - 163)) | (1L << (U_00DD - 163)) | (1L << (U_00DE - 163)) | (1L << (U_00DF - 163)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(646);
			utf8_tail();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Utf8_3Context extends ParserRuleContext {
		public TerminalNode U_00E0() { return getToken(ExpressionConstraintParser.U_00E0, 0); }
		public List<Utf8_tailContext> utf8_tail() {
			return getRuleContexts(Utf8_tailContext.class);
		}
		public Utf8_tailContext utf8_tail(int i) {
			return getRuleContext(Utf8_tailContext.class,i);
		}
		public TerminalNode U_00A0() { return getToken(ExpressionConstraintParser.U_00A0, 0); }
		public TerminalNode U_00A1() { return getToken(ExpressionConstraintParser.U_00A1, 0); }
		public TerminalNode U_00A2() { return getToken(ExpressionConstraintParser.U_00A2, 0); }
		public TerminalNode U_00A3() { return getToken(ExpressionConstraintParser.U_00A3, 0); }
		public TerminalNode U_00A4() { return getToken(ExpressionConstraintParser.U_00A4, 0); }
		public TerminalNode U_00A5() { return getToken(ExpressionConstraintParser.U_00A5, 0); }
		public TerminalNode U_00A6() { return getToken(ExpressionConstraintParser.U_00A6, 0); }
		public TerminalNode U_00A7() { return getToken(ExpressionConstraintParser.U_00A7, 0); }
		public TerminalNode U_00A8() { return getToken(ExpressionConstraintParser.U_00A8, 0); }
		public TerminalNode U_00A9() { return getToken(ExpressionConstraintParser.U_00A9, 0); }
		public TerminalNode U_00AA() { return getToken(ExpressionConstraintParser.U_00AA, 0); }
		public TerminalNode U_00AB() { return getToken(ExpressionConstraintParser.U_00AB, 0); }
		public TerminalNode U_00AC() { return getToken(ExpressionConstraintParser.U_00AC, 0); }
		public TerminalNode U_00AD() { return getToken(ExpressionConstraintParser.U_00AD, 0); }
		public TerminalNode U_00AE() { return getToken(ExpressionConstraintParser.U_00AE, 0); }
		public TerminalNode U_00AF() { return getToken(ExpressionConstraintParser.U_00AF, 0); }
		public TerminalNode U_00B0() { return getToken(ExpressionConstraintParser.U_00B0, 0); }
		public TerminalNode U_00B1() { return getToken(ExpressionConstraintParser.U_00B1, 0); }
		public TerminalNode U_00B2() { return getToken(ExpressionConstraintParser.U_00B2, 0); }
		public TerminalNode U_00B3() { return getToken(ExpressionConstraintParser.U_00B3, 0); }
		public TerminalNode U_00B4() { return getToken(ExpressionConstraintParser.U_00B4, 0); }
		public TerminalNode U_00B5() { return getToken(ExpressionConstraintParser.U_00B5, 0); }
		public TerminalNode U_00B6() { return getToken(ExpressionConstraintParser.U_00B6, 0); }
		public TerminalNode U_00B7() { return getToken(ExpressionConstraintParser.U_00B7, 0); }
		public TerminalNode U_00B8() { return getToken(ExpressionConstraintParser.U_00B8, 0); }
		public TerminalNode U_00B9() { return getToken(ExpressionConstraintParser.U_00B9, 0); }
		public TerminalNode U_00BA() { return getToken(ExpressionConstraintParser.U_00BA, 0); }
		public TerminalNode U_00BB() { return getToken(ExpressionConstraintParser.U_00BB, 0); }
		public TerminalNode U_00BC() { return getToken(ExpressionConstraintParser.U_00BC, 0); }
		public TerminalNode U_00BD() { return getToken(ExpressionConstraintParser.U_00BD, 0); }
		public TerminalNode U_00BE() { return getToken(ExpressionConstraintParser.U_00BE, 0); }
		public TerminalNode U_00BF() { return getToken(ExpressionConstraintParser.U_00BF, 0); }
		public TerminalNode U_00E1() { return getToken(ExpressionConstraintParser.U_00E1, 0); }
		public TerminalNode U_00E2() { return getToken(ExpressionConstraintParser.U_00E2, 0); }
		public TerminalNode U_00E3() { return getToken(ExpressionConstraintParser.U_00E3, 0); }
		public TerminalNode U_00E4() { return getToken(ExpressionConstraintParser.U_00E4, 0); }
		public TerminalNode U_00E5() { return getToken(ExpressionConstraintParser.U_00E5, 0); }
		public TerminalNode U_00E6() { return getToken(ExpressionConstraintParser.U_00E6, 0); }
		public TerminalNode U_00E7() { return getToken(ExpressionConstraintParser.U_00E7, 0); }
		public TerminalNode U_00E8() { return getToken(ExpressionConstraintParser.U_00E8, 0); }
		public TerminalNode U_00E9() { return getToken(ExpressionConstraintParser.U_00E9, 0); }
		public TerminalNode U_00EA() { return getToken(ExpressionConstraintParser.U_00EA, 0); }
		public TerminalNode U_00EB() { return getToken(ExpressionConstraintParser.U_00EB, 0); }
		public TerminalNode U_00EC() { return getToken(ExpressionConstraintParser.U_00EC, 0); }
		public TerminalNode U_00ED() { return getToken(ExpressionConstraintParser.U_00ED, 0); }
		public TerminalNode U_0080() { return getToken(ExpressionConstraintParser.U_0080, 0); }
		public TerminalNode U_0081() { return getToken(ExpressionConstraintParser.U_0081, 0); }
		public TerminalNode U_0082() { return getToken(ExpressionConstraintParser.U_0082, 0); }
		public TerminalNode U_0083() { return getToken(ExpressionConstraintParser.U_0083, 0); }
		public TerminalNode U_0084() { return getToken(ExpressionConstraintParser.U_0084, 0); }
		public TerminalNode U_0085() { return getToken(ExpressionConstraintParser.U_0085, 0); }
		public TerminalNode U_0086() { return getToken(ExpressionConstraintParser.U_0086, 0); }
		public TerminalNode U_0087() { return getToken(ExpressionConstraintParser.U_0087, 0); }
		public TerminalNode U_0088() { return getToken(ExpressionConstraintParser.U_0088, 0); }
		public TerminalNode U_0089() { return getToken(ExpressionConstraintParser.U_0089, 0); }
		public TerminalNode U_008A() { return getToken(ExpressionConstraintParser.U_008A, 0); }
		public TerminalNode U_008B() { return getToken(ExpressionConstraintParser.U_008B, 0); }
		public TerminalNode U_008C() { return getToken(ExpressionConstraintParser.U_008C, 0); }
		public TerminalNode U_008D() { return getToken(ExpressionConstraintParser.U_008D, 0); }
		public TerminalNode U_008E() { return getToken(ExpressionConstraintParser.U_008E, 0); }
		public TerminalNode U_008F() { return getToken(ExpressionConstraintParser.U_008F, 0); }
		public TerminalNode U_0090() { return getToken(ExpressionConstraintParser.U_0090, 0); }
		public TerminalNode U_0091() { return getToken(ExpressionConstraintParser.U_0091, 0); }
		public TerminalNode U_0092() { return getToken(ExpressionConstraintParser.U_0092, 0); }
		public TerminalNode U_0093() { return getToken(ExpressionConstraintParser.U_0093, 0); }
		public TerminalNode U_0094() { return getToken(ExpressionConstraintParser.U_0094, 0); }
		public TerminalNode U_0095() { return getToken(ExpressionConstraintParser.U_0095, 0); }
		public TerminalNode U_0096() { return getToken(ExpressionConstraintParser.U_0096, 0); }
		public TerminalNode U_0097() { return getToken(ExpressionConstraintParser.U_0097, 0); }
		public TerminalNode U_0098() { return getToken(ExpressionConstraintParser.U_0098, 0); }
		public TerminalNode U_0099() { return getToken(ExpressionConstraintParser.U_0099, 0); }
		public TerminalNode U_009A() { return getToken(ExpressionConstraintParser.U_009A, 0); }
		public TerminalNode U_009B() { return getToken(ExpressionConstraintParser.U_009B, 0); }
		public TerminalNode U_009C() { return getToken(ExpressionConstraintParser.U_009C, 0); }
		public TerminalNode U_009D() { return getToken(ExpressionConstraintParser.U_009D, 0); }
		public TerminalNode U_009E() { return getToken(ExpressionConstraintParser.U_009E, 0); }
		public TerminalNode U_009F() { return getToken(ExpressionConstraintParser.U_009F, 0); }
		public TerminalNode U_00EE() { return getToken(ExpressionConstraintParser.U_00EE, 0); }
		public TerminalNode U_00EF() { return getToken(ExpressionConstraintParser.U_00EF, 0); }
		public Utf8_3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_utf8_3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterUtf8_3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitUtf8_3(this);
		}
	}

	public final Utf8_3Context utf8_3() throws RecognitionException {
		Utf8_3Context _localctx = new Utf8_3Context(_ctx, getState());
		enterRule(_localctx, 126, RULE_utf8_3);
		int _la;
		try {
			setState(662);
			switch (_input.LA(1)) {
			case U_00E0:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(648);
				match(U_00E0);
				setState(649);
				_la = _input.LA(1);
				if ( !(((((_la - 131)) & ~0x3f) == 0 && ((1L << (_la - 131)) & ((1L << (U_00A0 - 131)) | (1L << (U_00A1 - 131)) | (1L << (U_00A2 - 131)) | (1L << (U_00A3 - 131)) | (1L << (U_00A4 - 131)) | (1L << (U_00A5 - 131)) | (1L << (U_00A6 - 131)) | (1L << (U_00A7 - 131)) | (1L << (U_00A8 - 131)) | (1L << (U_00A9 - 131)) | (1L << (U_00AA - 131)) | (1L << (U_00AB - 131)) | (1L << (U_00AC - 131)) | (1L << (U_00AD - 131)) | (1L << (U_00AE - 131)) | (1L << (U_00AF - 131)) | (1L << (U_00B0 - 131)) | (1L << (U_00B1 - 131)) | (1L << (U_00B2 - 131)) | (1L << (U_00B3 - 131)) | (1L << (U_00B4 - 131)) | (1L << (U_00B5 - 131)) | (1L << (U_00B6 - 131)) | (1L << (U_00B7 - 131)) | (1L << (U_00B8 - 131)) | (1L << (U_00B9 - 131)) | (1L << (U_00BA - 131)) | (1L << (U_00BB - 131)) | (1L << (U_00BC - 131)) | (1L << (U_00BD - 131)) | (1L << (U_00BE - 131)) | (1L << (U_00BF - 131)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(650);
				utf8_tail();
				}
				}
				break;
			case U_00E1:
			case U_00E2:
			case U_00E3:
			case U_00E4:
			case U_00E5:
			case U_00E6:
			case U_00E7:
			case U_00E8:
			case U_00E9:
			case U_00EA:
			case U_00EB:
			case U_00EC:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(651);
				_la = _input.LA(1);
				if ( !(((((_la - 194)) & ~0x3f) == 0 && ((1L << (_la - 194)) & ((1L << (U_00E1 - 194)) | (1L << (U_00E2 - 194)) | (1L << (U_00E3 - 194)) | (1L << (U_00E4 - 194)) | (1L << (U_00E5 - 194)) | (1L << (U_00E6 - 194)) | (1L << (U_00E7 - 194)) | (1L << (U_00E8 - 194)) | (1L << (U_00E9 - 194)) | (1L << (U_00EA - 194)) | (1L << (U_00EB - 194)) | (1L << (U_00EC - 194)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				{
				setState(652);
				utf8_tail();
				}
				{
				setState(653);
				utf8_tail();
				}
				}
				}
				break;
			case U_00ED:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(655);
				match(U_00ED);
				setState(656);
				_la = _input.LA(1);
				if ( !(((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (U_0080 - 99)) | (1L << (U_0081 - 99)) | (1L << (U_0082 - 99)) | (1L << (U_0083 - 99)) | (1L << (U_0084 - 99)) | (1L << (U_0085 - 99)) | (1L << (U_0086 - 99)) | (1L << (U_0087 - 99)) | (1L << (U_0088 - 99)) | (1L << (U_0089 - 99)) | (1L << (U_008A - 99)) | (1L << (U_008B - 99)) | (1L << (U_008C - 99)) | (1L << (U_008D - 99)) | (1L << (U_008E - 99)) | (1L << (U_008F - 99)) | (1L << (U_0090 - 99)) | (1L << (U_0091 - 99)) | (1L << (U_0092 - 99)) | (1L << (U_0093 - 99)) | (1L << (U_0094 - 99)) | (1L << (U_0095 - 99)) | (1L << (U_0096 - 99)) | (1L << (U_0097 - 99)) | (1L << (U_0098 - 99)) | (1L << (U_0099 - 99)) | (1L << (U_009A - 99)) | (1L << (U_009B - 99)) | (1L << (U_009C - 99)) | (1L << (U_009D - 99)) | (1L << (U_009E - 99)) | (1L << (U_009F - 99)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(657);
				utf8_tail();
				}
				}
				break;
			case U_00EE:
			case U_00EF:
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(658);
				_la = _input.LA(1);
				if ( !(_la==U_00EE || _la==U_00EF) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				{
				setState(659);
				utf8_tail();
				}
				{
				setState(660);
				utf8_tail();
				}
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Utf8_4Context extends ParserRuleContext {
		public TerminalNode U_00F0() { return getToken(ExpressionConstraintParser.U_00F0, 0); }
		public TerminalNode U_0090() { return getToken(ExpressionConstraintParser.U_0090, 0); }
		public TerminalNode U_0091() { return getToken(ExpressionConstraintParser.U_0091, 0); }
		public TerminalNode U_0092() { return getToken(ExpressionConstraintParser.U_0092, 0); }
		public TerminalNode U_0093() { return getToken(ExpressionConstraintParser.U_0093, 0); }
		public TerminalNode U_0094() { return getToken(ExpressionConstraintParser.U_0094, 0); }
		public TerminalNode U_0095() { return getToken(ExpressionConstraintParser.U_0095, 0); }
		public TerminalNode U_0096() { return getToken(ExpressionConstraintParser.U_0096, 0); }
		public TerminalNode U_0097() { return getToken(ExpressionConstraintParser.U_0097, 0); }
		public TerminalNode U_0098() { return getToken(ExpressionConstraintParser.U_0098, 0); }
		public TerminalNode U_0099() { return getToken(ExpressionConstraintParser.U_0099, 0); }
		public TerminalNode U_009A() { return getToken(ExpressionConstraintParser.U_009A, 0); }
		public TerminalNode U_009B() { return getToken(ExpressionConstraintParser.U_009B, 0); }
		public TerminalNode U_009C() { return getToken(ExpressionConstraintParser.U_009C, 0); }
		public TerminalNode U_009D() { return getToken(ExpressionConstraintParser.U_009D, 0); }
		public TerminalNode U_009E() { return getToken(ExpressionConstraintParser.U_009E, 0); }
		public TerminalNode U_009F() { return getToken(ExpressionConstraintParser.U_009F, 0); }
		public TerminalNode U_00A0() { return getToken(ExpressionConstraintParser.U_00A0, 0); }
		public TerminalNode U_00A1() { return getToken(ExpressionConstraintParser.U_00A1, 0); }
		public TerminalNode U_00A2() { return getToken(ExpressionConstraintParser.U_00A2, 0); }
		public TerminalNode U_00A3() { return getToken(ExpressionConstraintParser.U_00A3, 0); }
		public TerminalNode U_00A4() { return getToken(ExpressionConstraintParser.U_00A4, 0); }
		public TerminalNode U_00A5() { return getToken(ExpressionConstraintParser.U_00A5, 0); }
		public TerminalNode U_00A6() { return getToken(ExpressionConstraintParser.U_00A6, 0); }
		public TerminalNode U_00A7() { return getToken(ExpressionConstraintParser.U_00A7, 0); }
		public TerminalNode U_00A8() { return getToken(ExpressionConstraintParser.U_00A8, 0); }
		public TerminalNode U_00A9() { return getToken(ExpressionConstraintParser.U_00A9, 0); }
		public TerminalNode U_00AA() { return getToken(ExpressionConstraintParser.U_00AA, 0); }
		public TerminalNode U_00AB() { return getToken(ExpressionConstraintParser.U_00AB, 0); }
		public TerminalNode U_00AC() { return getToken(ExpressionConstraintParser.U_00AC, 0); }
		public TerminalNode U_00AD() { return getToken(ExpressionConstraintParser.U_00AD, 0); }
		public TerminalNode U_00AE() { return getToken(ExpressionConstraintParser.U_00AE, 0); }
		public TerminalNode U_00AF() { return getToken(ExpressionConstraintParser.U_00AF, 0); }
		public TerminalNode U_00B0() { return getToken(ExpressionConstraintParser.U_00B0, 0); }
		public TerminalNode U_00B1() { return getToken(ExpressionConstraintParser.U_00B1, 0); }
		public TerminalNode U_00B2() { return getToken(ExpressionConstraintParser.U_00B2, 0); }
		public TerminalNode U_00B3() { return getToken(ExpressionConstraintParser.U_00B3, 0); }
		public TerminalNode U_00B4() { return getToken(ExpressionConstraintParser.U_00B4, 0); }
		public TerminalNode U_00B5() { return getToken(ExpressionConstraintParser.U_00B5, 0); }
		public TerminalNode U_00B6() { return getToken(ExpressionConstraintParser.U_00B6, 0); }
		public TerminalNode U_00B7() { return getToken(ExpressionConstraintParser.U_00B7, 0); }
		public TerminalNode U_00B8() { return getToken(ExpressionConstraintParser.U_00B8, 0); }
		public TerminalNode U_00B9() { return getToken(ExpressionConstraintParser.U_00B9, 0); }
		public TerminalNode U_00BA() { return getToken(ExpressionConstraintParser.U_00BA, 0); }
		public TerminalNode U_00BB() { return getToken(ExpressionConstraintParser.U_00BB, 0); }
		public TerminalNode U_00BC() { return getToken(ExpressionConstraintParser.U_00BC, 0); }
		public TerminalNode U_00BD() { return getToken(ExpressionConstraintParser.U_00BD, 0); }
		public TerminalNode U_00BE() { return getToken(ExpressionConstraintParser.U_00BE, 0); }
		public TerminalNode U_00BF() { return getToken(ExpressionConstraintParser.U_00BF, 0); }
		public List<Utf8_tailContext> utf8_tail() {
			return getRuleContexts(Utf8_tailContext.class);
		}
		public Utf8_tailContext utf8_tail(int i) {
			return getRuleContext(Utf8_tailContext.class,i);
		}
		public TerminalNode U_00F1() { return getToken(ExpressionConstraintParser.U_00F1, 0); }
		public TerminalNode U_00F2() { return getToken(ExpressionConstraintParser.U_00F2, 0); }
		public TerminalNode U_00F3() { return getToken(ExpressionConstraintParser.U_00F3, 0); }
		public TerminalNode U_00F4() { return getToken(ExpressionConstraintParser.U_00F4, 0); }
		public TerminalNode U_0080() { return getToken(ExpressionConstraintParser.U_0080, 0); }
		public TerminalNode U_0081() { return getToken(ExpressionConstraintParser.U_0081, 0); }
		public TerminalNode U_0082() { return getToken(ExpressionConstraintParser.U_0082, 0); }
		public TerminalNode U_0083() { return getToken(ExpressionConstraintParser.U_0083, 0); }
		public TerminalNode U_0084() { return getToken(ExpressionConstraintParser.U_0084, 0); }
		public TerminalNode U_0085() { return getToken(ExpressionConstraintParser.U_0085, 0); }
		public TerminalNode U_0086() { return getToken(ExpressionConstraintParser.U_0086, 0); }
		public TerminalNode U_0087() { return getToken(ExpressionConstraintParser.U_0087, 0); }
		public TerminalNode U_0088() { return getToken(ExpressionConstraintParser.U_0088, 0); }
		public TerminalNode U_0089() { return getToken(ExpressionConstraintParser.U_0089, 0); }
		public TerminalNode U_008A() { return getToken(ExpressionConstraintParser.U_008A, 0); }
		public TerminalNode U_008B() { return getToken(ExpressionConstraintParser.U_008B, 0); }
		public TerminalNode U_008C() { return getToken(ExpressionConstraintParser.U_008C, 0); }
		public TerminalNode U_008D() { return getToken(ExpressionConstraintParser.U_008D, 0); }
		public TerminalNode U_008E() { return getToken(ExpressionConstraintParser.U_008E, 0); }
		public TerminalNode U_008F() { return getToken(ExpressionConstraintParser.U_008F, 0); }
		public Utf8_4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_utf8_4; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterUtf8_4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitUtf8_4(this);
		}
	}

	public final Utf8_4Context utf8_4() throws RecognitionException {
		Utf8_4Context _localctx = new Utf8_4Context(_ctx, getState());
		enterRule(_localctx, 128, RULE_utf8_4);
		int _la;
		try {
			setState(679);
			switch (_input.LA(1)) {
			case U_00F0:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(664);
				match(U_00F0);
				setState(665);
				_la = _input.LA(1);
				if ( !(((((_la - 115)) & ~0x3f) == 0 && ((1L << (_la - 115)) & ((1L << (U_0090 - 115)) | (1L << (U_0091 - 115)) | (1L << (U_0092 - 115)) | (1L << (U_0093 - 115)) | (1L << (U_0094 - 115)) | (1L << (U_0095 - 115)) | (1L << (U_0096 - 115)) | (1L << (U_0097 - 115)) | (1L << (U_0098 - 115)) | (1L << (U_0099 - 115)) | (1L << (U_009A - 115)) | (1L << (U_009B - 115)) | (1L << (U_009C - 115)) | (1L << (U_009D - 115)) | (1L << (U_009E - 115)) | (1L << (U_009F - 115)) | (1L << (U_00A0 - 115)) | (1L << (U_00A1 - 115)) | (1L << (U_00A2 - 115)) | (1L << (U_00A3 - 115)) | (1L << (U_00A4 - 115)) | (1L << (U_00A5 - 115)) | (1L << (U_00A6 - 115)) | (1L << (U_00A7 - 115)) | (1L << (U_00A8 - 115)) | (1L << (U_00A9 - 115)) | (1L << (U_00AA - 115)) | (1L << (U_00AB - 115)) | (1L << (U_00AC - 115)) | (1L << (U_00AD - 115)) | (1L << (U_00AE - 115)) | (1L << (U_00AF - 115)) | (1L << (U_00B0 - 115)) | (1L << (U_00B1 - 115)) | (1L << (U_00B2 - 115)) | (1L << (U_00B3 - 115)) | (1L << (U_00B4 - 115)) | (1L << (U_00B5 - 115)) | (1L << (U_00B6 - 115)) | (1L << (U_00B7 - 115)) | (1L << (U_00B8 - 115)) | (1L << (U_00B9 - 115)) | (1L << (U_00BA - 115)) | (1L << (U_00BB - 115)) | (1L << (U_00BC - 115)) | (1L << (U_00BD - 115)) | (1L << (U_00BE - 115)) | (1L << (U_00BF - 115)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				{
				setState(666);
				utf8_tail();
				}
				{
				setState(667);
				utf8_tail();
				}
				}
				}
				break;
			case U_00F1:
			case U_00F2:
			case U_00F3:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(669);
				_la = _input.LA(1);
				if ( !(((((_la - 210)) & ~0x3f) == 0 && ((1L << (_la - 210)) & ((1L << (U_00F1 - 210)) | (1L << (U_00F2 - 210)) | (1L << (U_00F3 - 210)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				{
				setState(670);
				utf8_tail();
				}
				{
				setState(671);
				utf8_tail();
				}
				{
				setState(672);
				utf8_tail();
				}
				}
				}
				break;
			case U_00F4:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(674);
				match(U_00F4);
				setState(675);
				_la = _input.LA(1);
				if ( !(((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (U_0080 - 99)) | (1L << (U_0081 - 99)) | (1L << (U_0082 - 99)) | (1L << (U_0083 - 99)) | (1L << (U_0084 - 99)) | (1L << (U_0085 - 99)) | (1L << (U_0086 - 99)) | (1L << (U_0087 - 99)) | (1L << (U_0088 - 99)) | (1L << (U_0089 - 99)) | (1L << (U_008A - 99)) | (1L << (U_008B - 99)) | (1L << (U_008C - 99)) | (1L << (U_008D - 99)) | (1L << (U_008E - 99)) | (1L << (U_008F - 99)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				{
				setState(676);
				utf8_tail();
				}
				{
				setState(677);
				utf8_tail();
				}
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Utf8_tailContext extends ParserRuleContext {
		public TerminalNode U_0080() { return getToken(ExpressionConstraintParser.U_0080, 0); }
		public TerminalNode U_0081() { return getToken(ExpressionConstraintParser.U_0081, 0); }
		public TerminalNode U_0082() { return getToken(ExpressionConstraintParser.U_0082, 0); }
		public TerminalNode U_0083() { return getToken(ExpressionConstraintParser.U_0083, 0); }
		public TerminalNode U_0084() { return getToken(ExpressionConstraintParser.U_0084, 0); }
		public TerminalNode U_0085() { return getToken(ExpressionConstraintParser.U_0085, 0); }
		public TerminalNode U_0086() { return getToken(ExpressionConstraintParser.U_0086, 0); }
		public TerminalNode U_0087() { return getToken(ExpressionConstraintParser.U_0087, 0); }
		public TerminalNode U_0088() { return getToken(ExpressionConstraintParser.U_0088, 0); }
		public TerminalNode U_0089() { return getToken(ExpressionConstraintParser.U_0089, 0); }
		public TerminalNode U_008A() { return getToken(ExpressionConstraintParser.U_008A, 0); }
		public TerminalNode U_008B() { return getToken(ExpressionConstraintParser.U_008B, 0); }
		public TerminalNode U_008C() { return getToken(ExpressionConstraintParser.U_008C, 0); }
		public TerminalNode U_008D() { return getToken(ExpressionConstraintParser.U_008D, 0); }
		public TerminalNode U_008E() { return getToken(ExpressionConstraintParser.U_008E, 0); }
		public TerminalNode U_008F() { return getToken(ExpressionConstraintParser.U_008F, 0); }
		public TerminalNode U_0090() { return getToken(ExpressionConstraintParser.U_0090, 0); }
		public TerminalNode U_0091() { return getToken(ExpressionConstraintParser.U_0091, 0); }
		public TerminalNode U_0092() { return getToken(ExpressionConstraintParser.U_0092, 0); }
		public TerminalNode U_0093() { return getToken(ExpressionConstraintParser.U_0093, 0); }
		public TerminalNode U_0094() { return getToken(ExpressionConstraintParser.U_0094, 0); }
		public TerminalNode U_0095() { return getToken(ExpressionConstraintParser.U_0095, 0); }
		public TerminalNode U_0096() { return getToken(ExpressionConstraintParser.U_0096, 0); }
		public TerminalNode U_0097() { return getToken(ExpressionConstraintParser.U_0097, 0); }
		public TerminalNode U_0098() { return getToken(ExpressionConstraintParser.U_0098, 0); }
		public TerminalNode U_0099() { return getToken(ExpressionConstraintParser.U_0099, 0); }
		public TerminalNode U_009A() { return getToken(ExpressionConstraintParser.U_009A, 0); }
		public TerminalNode U_009B() { return getToken(ExpressionConstraintParser.U_009B, 0); }
		public TerminalNode U_009C() { return getToken(ExpressionConstraintParser.U_009C, 0); }
		public TerminalNode U_009D() { return getToken(ExpressionConstraintParser.U_009D, 0); }
		public TerminalNode U_009E() { return getToken(ExpressionConstraintParser.U_009E, 0); }
		public TerminalNode U_009F() { return getToken(ExpressionConstraintParser.U_009F, 0); }
		public TerminalNode U_00A0() { return getToken(ExpressionConstraintParser.U_00A0, 0); }
		public TerminalNode U_00A1() { return getToken(ExpressionConstraintParser.U_00A1, 0); }
		public TerminalNode U_00A2() { return getToken(ExpressionConstraintParser.U_00A2, 0); }
		public TerminalNode U_00A3() { return getToken(ExpressionConstraintParser.U_00A3, 0); }
		public TerminalNode U_00A4() { return getToken(ExpressionConstraintParser.U_00A4, 0); }
		public TerminalNode U_00A5() { return getToken(ExpressionConstraintParser.U_00A5, 0); }
		public TerminalNode U_00A6() { return getToken(ExpressionConstraintParser.U_00A6, 0); }
		public TerminalNode U_00A7() { return getToken(ExpressionConstraintParser.U_00A7, 0); }
		public TerminalNode U_00A8() { return getToken(ExpressionConstraintParser.U_00A8, 0); }
		public TerminalNode U_00A9() { return getToken(ExpressionConstraintParser.U_00A9, 0); }
		public TerminalNode U_00AA() { return getToken(ExpressionConstraintParser.U_00AA, 0); }
		public TerminalNode U_00AB() { return getToken(ExpressionConstraintParser.U_00AB, 0); }
		public TerminalNode U_00AC() { return getToken(ExpressionConstraintParser.U_00AC, 0); }
		public TerminalNode U_00AD() { return getToken(ExpressionConstraintParser.U_00AD, 0); }
		public TerminalNode U_00AE() { return getToken(ExpressionConstraintParser.U_00AE, 0); }
		public TerminalNode U_00AF() { return getToken(ExpressionConstraintParser.U_00AF, 0); }
		public TerminalNode U_00B0() { return getToken(ExpressionConstraintParser.U_00B0, 0); }
		public TerminalNode U_00B1() { return getToken(ExpressionConstraintParser.U_00B1, 0); }
		public TerminalNode U_00B2() { return getToken(ExpressionConstraintParser.U_00B2, 0); }
		public TerminalNode U_00B3() { return getToken(ExpressionConstraintParser.U_00B3, 0); }
		public TerminalNode U_00B4() { return getToken(ExpressionConstraintParser.U_00B4, 0); }
		public TerminalNode U_00B5() { return getToken(ExpressionConstraintParser.U_00B5, 0); }
		public TerminalNode U_00B6() { return getToken(ExpressionConstraintParser.U_00B6, 0); }
		public TerminalNode U_00B7() { return getToken(ExpressionConstraintParser.U_00B7, 0); }
		public TerminalNode U_00B8() { return getToken(ExpressionConstraintParser.U_00B8, 0); }
		public TerminalNode U_00B9() { return getToken(ExpressionConstraintParser.U_00B9, 0); }
		public TerminalNode U_00BA() { return getToken(ExpressionConstraintParser.U_00BA, 0); }
		public TerminalNode U_00BB() { return getToken(ExpressionConstraintParser.U_00BB, 0); }
		public TerminalNode U_00BC() { return getToken(ExpressionConstraintParser.U_00BC, 0); }
		public TerminalNode U_00BD() { return getToken(ExpressionConstraintParser.U_00BD, 0); }
		public TerminalNode U_00BE() { return getToken(ExpressionConstraintParser.U_00BE, 0); }
		public TerminalNode U_00BF() { return getToken(ExpressionConstraintParser.U_00BF, 0); }
		public Utf8_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_utf8_tail; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).enterUtf8_tail(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionConstraintListener ) ((ExpressionConstraintListener)listener).exitUtf8_tail(this);
		}
	}

	public final Utf8_tailContext utf8_tail() throws RecognitionException {
		Utf8_tailContext _localctx = new Utf8_tailContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_utf8_tail);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(681);
			_la = _input.LA(1);
			if ( !(((((_la - 99)) & ~0x3f) == 0 && ((1L << (_la - 99)) & ((1L << (U_0080 - 99)) | (1L << (U_0081 - 99)) | (1L << (U_0082 - 99)) | (1L << (U_0083 - 99)) | (1L << (U_0084 - 99)) | (1L << (U_0085 - 99)) | (1L << (U_0086 - 99)) | (1L << (U_0087 - 99)) | (1L << (U_0088 - 99)) | (1L << (U_0089 - 99)) | (1L << (U_008A - 99)) | (1L << (U_008B - 99)) | (1L << (U_008C - 99)) | (1L << (U_008D - 99)) | (1L << (U_008E - 99)) | (1L << (U_008F - 99)) | (1L << (U_0090 - 99)) | (1L << (U_0091 - 99)) | (1L << (U_0092 - 99)) | (1L << (U_0093 - 99)) | (1L << (U_0094 - 99)) | (1L << (U_0095 - 99)) | (1L << (U_0096 - 99)) | (1L << (U_0097 - 99)) | (1L << (U_0098 - 99)) | (1L << (U_0099 - 99)) | (1L << (U_009A - 99)) | (1L << (U_009B - 99)) | (1L << (U_009C - 99)) | (1L << (U_009D - 99)) | (1L << (U_009E - 99)) | (1L << (U_009F - 99)) | (1L << (U_00A0 - 99)) | (1L << (U_00A1 - 99)) | (1L << (U_00A2 - 99)) | (1L << (U_00A3 - 99)) | (1L << (U_00A4 - 99)) | (1L << (U_00A5 - 99)) | (1L << (U_00A6 - 99)) | (1L << (U_00A7 - 99)) | (1L << (U_00A8 - 99)) | (1L << (U_00A9 - 99)) | (1L << (U_00AA - 99)) | (1L << (U_00AB - 99)) | (1L << (U_00AC - 99)) | (1L << (U_00AD - 99)) | (1L << (U_00AE - 99)) | (1L << (U_00AF - 99)) | (1L << (U_00B0 - 99)) | (1L << (U_00B1 - 99)) | (1L << (U_00B2 - 99)) | (1L << (U_00B3 - 99)) | (1L << (U_00B4 - 99)) | (1L << (U_00B5 - 99)) | (1L << (U_00B6 - 99)) | (1L << (U_00B7 - 99)) | (1L << (U_00B8 - 99)) | (1L << (U_00B9 - 99)) | (1L << (U_00BA - 99)) | (1L << (U_00BB - 99)) | (1L << (U_00BC - 99)) | (1L << (U_00BD - 99)) | (1L << (U_00BE - 99)) | (1L << (U_00BF - 99)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\u00d7\u02ae\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\3\2\3\2\5\2\u008b\n\2\3\2"+
		"\3\2\3\3\3\3\3\3\5\3\u0092\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5"+
		"\3\5\5\5\u009f\n\5\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u00a7\n\6\r\6\16\6\u00a8"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\6\7\u00b1\n\7\r\7\16\7\u00b2\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\t\u00c0\n\t\3\t\3\t\3\t\5\t\u00c5\n\t\3"+
		"\n\3\n\3\n\5\n\u00ca\n\n\3\n\3\n\5\n\u00ce\n\n\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\5\f\u00da\n\f\3\r\3\r\3\16\6\16\u00df\n\16\r\16\16"+
		"\16\u00e0\3\16\6\16\u00e4\n\16\r\16\16\16\u00e5\3\16\6\16\u00e9\n\16\r"+
		"\16\16\16\u00ea\7\16\u00ed\n\16\f\16\16\16\u00f0\13\16\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\5\20\u00f8\n\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\5\25\u0109\n\25\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\5\30\u011a\n\30"+
		"\3\31\3\31\3\31\3\31\3\31\6\31\u0121\n\31\r\31\16\31\u0122\3\32\3\32\3"+
		"\32\3\32\3\32\6\32\u012a\n\32\r\32\16\32\u012b\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\5\33\u0136\n\33\3\34\3\34\3\34\3\34\5\34\u013c\n\34\3"+
		"\35\3\35\3\35\3\35\3\35\6\35\u0143\n\35\r\35\16\35\u0144\3\36\3\36\3\36"+
		"\3\36\3\36\6\36\u014c\n\36\r\36\16\36\u014d\3\37\3\37\3\37\3\37\3\37\3"+
		"\37\3\37\5\37\u0157\n\37\3 \3 \3 \5 \u015c\n \3 \3 \3 \3 \3 \3 \3!\3!"+
		"\3!\5!\u0167\n!\3!\3!\3!\5!\u016c\n!\3!\3!\3!\5!\u0171\n!\3!\3!\3!\3!"+
		"\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\5!\u0181\n!\3\"\3\"\3\"\3\"\3\"\5\"\u0188"+
		"\n\"\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3&\3&\5&\u0195\n&\3\'\3\'\5\'\u0199"+
		"\n\'\3(\3(\3(\3(\3(\5(\u01a0\n(\3(\3(\3(\5(\u01a5\n(\3)\3)\3)\5)\u01aa"+
		"\n)\3*\3*\3*\3*\3*\3*\3*\3*\3*\5*\u01b5\n*\3+\3+\3+\5+\u01ba\n+\3,\3,"+
		"\3,\5,\u01bf\n,\3-\3-\3-\6-\u01c4\n-\r-\16-\u01c5\3-\3-\3.\5.\u01cb\n"+
		".\3.\3.\7.\u01cf\n.\f.\16.\u01d2\13.\3.\5.\u01d5\n.\3/\3/\3/\6/\u01da"+
		"\n/\r/\16/\u01db\3\60\3\60\7\60\u01e0\n\60\f\60\16\60\u01e3\13\60\3\60"+
		"\5\60\u01e6\n\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\5\61\u01ef\n\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3"+
		"\61\3\61\3\61\3\61\5\61\u0249\n\61\3\62\3\62\3\62\3\62\7\62\u024f\n\62"+
		"\f\62\16\62\u0252\13\62\3\63\3\63\3\63\3\63\6\63\u0258\n\63\r\63\16\63"+
		"\u0259\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3:\3:\3;\3"+
		";\3<\3<\3=\3=\3=\3=\3=\5=\u0273\n=\3>\3>\3>\3>\3>\3>\3>\3>\3>\5>\u027e"+
		"\n>\3?\3?\3?\3?\3?\3?\5?\u0286\n?\3@\3@\3@\3A\3A\3A\3A\3A\3A\3A\3A\3A"+
		"\3A\3A\3A\3A\3A\5A\u0299\nA\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B"+
		"\3B\5B\u02aa\nB\3C\3C\3C\2\2D\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 "+
		"\"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082"+
		"\u0084\2\34\4\2\'\'GG\4\2\64\64TT\4\2**JJ\4\2\65\65UU\4\288XX\4\2\63\63"+
		"SS\4\2//OO\4\2;;[[\4\299YY\4\2\21\21\23\23\3\2\26\37\3\2\27\37\3\2\7a"+
		"\3\2cd\3\2\6\7\3\2\tA\3\2Cd\3\2\u00a5\u00c2\3\2\u0085\u00a4\3\2\u00c4"+
		"\u00cf\3\2e\u0084\3\2\u00d1\u00d2\3\2u\u00a4\3\2\u00d4\u00d6\3\2et\3\2"+
		"e\u00a4\u02cb\2\u0086\3\2\2\2\4\u0091\3\2\2\2\6\u0095\3\2\2\2\b\u009e"+
		"\3\2\2\2\n\u00a0\3\2\2\2\f\u00aa\3\2\2\2\16\u00b4\3\2\2\2\20\u00c4\3\2"+
		"\2\2\22\u00c9\3\2\2\2\24\u00cf\3\2\2\2\26\u00d1\3\2\2\2\30\u00db\3\2\2"+
		"\2\32\u00de\3\2\2\2\34\u00f1\3\2\2\2\36\u00f7\3\2\2\2 \u00f9\3\2\2\2\""+
		"\u00fb\3\2\2\2$\u00fe\3\2\2\2&\u0100\3\2\2\2(\u0108\3\2\2\2*\u010a\3\2"+
		"\2\2,\u010e\3\2\2\2.\u0115\3\2\2\2\60\u0120\3\2\2\2\62\u0129\3\2\2\2\64"+
		"\u0135\3\2\2\2\66\u0137\3\2\2\28\u0142\3\2\2\2:\u014b\3\2\2\2<\u0156\3"+
		"\2\2\2>\u015b\3\2\2\2@\u0166\3\2\2\2B\u0182\3\2\2\2D\u018b\3\2\2\2F\u018e"+
		"\3\2\2\2H\u0190\3\2\2\2J\u0194\3\2\2\2L\u0198\3\2\2\2N\u01a4\3\2\2\2P"+
		"\u01a9\3\2\2\2R\u01b4\3\2\2\2T\u01b9\3\2\2\2V\u01bb\3\2\2\2X\u01c0\3\2"+
		"\2\2Z\u01d4\3\2\2\2\\\u01d6\3\2\2\2^\u01e5\3\2\2\2`\u01e7\3\2\2\2b\u0250"+
		"\3\2\2\2d\u0257\3\2\2\2f\u025b\3\2\2\2h\u025d\3\2\2\2j\u025f\3\2\2\2l"+
		"\u0261\3\2\2\2n\u0263\3\2\2\2p\u0265\3\2\2\2r\u0267\3\2\2\2t\u0269\3\2"+
		"\2\2v\u026b\3\2\2\2x\u0272\3\2\2\2z\u027d\3\2\2\2|\u0285\3\2\2\2~\u0287"+
		"\3\2\2\2\u0080\u0298\3\2\2\2\u0082\u02a9\3\2\2\2\u0084\u02ab\3\2\2\2\u0086"+
		"\u008a\5b\62\2\u0087\u008b\5\6\4\2\u0088\u008b\5\b\5\2\u0089\u008b\5\4"+
		"\3\2\u008a\u0087\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u0089\3\2\2\2\u008b"+
		"\u008c\3\2\2\2\u008c\u008d\5b\62\2\u008d\3\3\2\2\2\u008e\u008f\5\36\20"+
		"\2\u008f\u0090\5b\62\2\u0090\u0092\3\2\2\2\u0091\u008e\3\2\2\2\u0091\u0092"+
		"\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\5\22\n\2\u0094\5\3\2\2\2\u0095"+
		"\u0096\5\4\3\2\u0096\u0097\5b\62\2\u0097\u0098\7 \2\2\u0098\u0099\5b\62"+
		"\2\u0099\u009a\5.\30\2\u009a\7\3\2\2\2\u009b\u009f\5\n\6\2\u009c\u009f"+
		"\5\f\7\2\u009d\u009f\5\16\b\2\u009e\u009b\3\2\2\2\u009e\u009c\3\2\2\2"+
		"\u009e\u009d\3\2\2\2\u009f\t\3\2\2\2\u00a0\u00a6\5\20\t\2\u00a1\u00a2"+
		"\5b\62\2\u00a2\u00a3\5(\25\2\u00a3\u00a4\5b\62\2\u00a4\u00a5\5\20\t\2"+
		"\u00a5\u00a7\3\2\2\2\u00a6\u00a1\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a6"+
		"\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\13\3\2\2\2\u00aa\u00b0\5\20\t\2\u00ab"+
		"\u00ac\5b\62\2\u00ac\u00ad\5*\26\2\u00ad\u00ae\5b\62\2\u00ae\u00af\5\20"+
		"\t\2\u00af\u00b1\3\2\2\2\u00b0\u00ab\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\r\3\2\2\2\u00b4\u00b5\5\20\t"+
		"\2\u00b5\u00b6\5b\62\2\u00b6\u00b7\5,\27\2\u00b7\u00b8\5b\62\2\u00b8\u00b9"+
		"\5\20\t\2\u00b9\17\3\2\2\2\u00ba\u00c5\5\4\3\2\u00bb\u00bc\7\16\2\2\u00bc"+
		"\u00bf\5b\62\2\u00bd\u00c0\5\b\5\2\u00be\u00c0\5\6\4\2\u00bf\u00bd\3\2"+
		"\2\2\u00bf\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\5b\62\2\u00c2"+
		"\u00c3\7\17\2\2\u00c3\u00c5\3\2\2\2\u00c4\u00ba\3\2\2\2\u00c4\u00bb\3"+
		"\2\2\2\u00c5\21\3\2\2\2\u00c6\u00c7\5\24\13\2\u00c7\u00c8\5b\62\2\u00c8"+
		"\u00ca\3\2\2\2\u00c9\u00c6\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cd\3\2"+
		"\2\2\u00cb\u00ce\5\26\f\2\u00cc\u00ce\5\34\17\2\u00cd\u00cb\3\2\2\2\u00cd"+
		"\u00cc\3\2\2\2\u00ce\23\3\2\2\2\u00cf\u00d0\7D\2\2\u00d0\25\3\2\2\2\u00d1"+
		"\u00d9\5\30\r\2\u00d2\u00d3\5b\62\2\u00d3\u00d4\7b\2\2\u00d4\u00d5\5b"+
		"\62\2\u00d5\u00d6\5\32\16\2\u00d6\u00d7\5b\62\2\u00d7\u00d8\7b\2\2\u00d8"+
		"\u00da\3\2\2\2\u00d9\u00d2\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\27\3\2\2"+
		"\2\u00db\u00dc\5`\61\2\u00dc\31\3\2\2\2\u00dd\u00df\5x=\2\u00de\u00dd"+
		"\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1"+
		"\u00ee\3\2\2\2\u00e2\u00e4\5f\64\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7"+
		"\u00e9\5x=\2\u00e8\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00e8\3\2\2"+
		"\2\u00ea\u00eb\3\2\2\2\u00eb\u00ed\3\2\2\2\u00ec\u00e3\3\2\2\2\u00ed\u00f0"+
		"\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\33\3\2\2\2\u00f0"+
		"\u00ee\3\2\2\2\u00f1\u00f2\7\20\2\2\u00f2\35\3\2\2\2\u00f3\u00f8\5\"\22"+
		"\2\u00f4\u00f8\5 \21\2\u00f5\u00f8\5&\24\2\u00f6\u00f8\5$\23\2\u00f7\u00f3"+
		"\3\2\2\2\u00f7\u00f4\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f6\3\2\2\2\u00f8"+
		"\37\3\2\2\2\u00f9\u00fa\7\"\2\2\u00fa!\3\2\2\2\u00fb\u00fc\7\"\2\2\u00fc"+
		"\u00fd\7\"\2\2\u00fd#\3\2\2\2\u00fe\u00ff\7$\2\2\u00ff%\3\2\2\2\u0100"+
		"\u0101\7$\2\2\u0101\u0102\7$\2\2\u0102\'\3\2\2\2\u0103\u0104\t\2\2\2\u0104"+
		"\u0105\t\3\2\2\u0105\u0106\t\4\2\2\u0106\u0109\5d\63\2\u0107\u0109\7\22"+
		"\2\2\u0108\u0103\3\2\2\2\u0108\u0107\3\2\2\2\u0109)\3\2\2\2\u010a\u010b"+
		"\t\5\2\2\u010b\u010c\t\6\2\2\u010c\u010d\5d\63\2\u010d+\3\2\2\2\u010e"+
		"\u010f\t\7\2\2\u010f\u0110\t\b\2\2\u0110\u0111\t\3\2\2\u0111\u0112\t\t"+
		"\2\2\u0112\u0113\t\n\2\2\u0113\u0114\5d\63\2\u0114-\3\2\2\2\u0115\u0116"+
		"\5\64\33\2\u0116\u0119\5b\62\2\u0117\u011a\5\60\31\2\u0118\u011a\5\62"+
		"\32\2\u0119\u0117\3\2\2\2\u0119\u0118\3\2\2\2\u0119\u011a\3\2\2\2\u011a"+
		"/\3\2\2\2\u011b\u011c\5b\62\2\u011c\u011d\5(\25\2\u011d\u011e\5b\62\2"+
		"\u011e\u011f\5\64\33\2\u011f\u0121\3\2\2\2\u0120\u011b\3\2\2\2\u0121\u0122"+
		"\3\2\2\2\u0122\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123\61\3\2\2\2\u0124"+
		"\u0125\5b\62\2\u0125\u0126\5*\26\2\u0126\u0127\5b\62\2\u0127\u0128\5\64"+
		"\33\2\u0128\u012a\3\2\2\2\u0129\u0124\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c\63\3\2\2\2\u012d\u0136\5\66\34"+
		"\2\u012e\u0136\5> \2\u012f\u0130\7\16\2\2\u0130\u0131\5b\62\2\u0131\u0132"+
		"\5.\30\2\u0132\u0133\5b\62\2\u0133\u0134\7\17\2\2\u0134\u0136\3\2\2\2"+
		"\u0135\u012d\3\2\2\2\u0135\u012e\3\2\2\2\u0135\u012f\3\2\2\2\u0136\65"+
		"\3\2\2\2\u0137\u0138\5<\37\2\u0138\u013b\5b\62\2\u0139\u013c\58\35\2\u013a"+
		"\u013c\5:\36\2\u013b\u0139\3\2\2\2\u013b\u013a\3\2\2\2\u013b\u013c\3\2"+
		"\2\2\u013c\67\3\2\2\2\u013d\u013e\5b\62\2\u013e\u013f\5(\25\2\u013f\u0140"+
		"\5b\62\2\u0140\u0141\5<\37\2\u0141\u0143\3\2\2\2\u0142\u013d\3\2\2\2\u0143"+
		"\u0144\3\2\2\2\u0144\u0142\3\2\2\2\u0144\u0145\3\2\2\2\u01459\3\2\2\2"+
		"\u0146\u0147\5b\62\2\u0147\u0148\5*\26\2\u0148\u0149\5b\62\2\u0149\u014a"+
		"\5<\37\2\u014a\u014c\3\2\2\2\u014b\u0146\3\2\2\2\u014c\u014d\3\2\2\2\u014d"+
		"\u014b\3\2\2\2\u014d\u014e\3\2\2\2\u014e;\3\2\2\2\u014f\u0157\5@!\2\u0150"+
		"\u0151\7\16\2\2\u0151\u0152\5b\62\2\u0152\u0153\5\66\34\2\u0153\u0154"+
		"\5b\62\2\u0154\u0155\7\17\2\2\u0155\u0157\3\2\2\2\u0156\u014f\3\2\2\2"+
		"\u0156\u0150\3\2\2\2\u0157=\3\2\2\2\u0158\u0159\5B\"\2\u0159\u015a\5b"+
		"\62\2\u015a\u015c\3\2\2\2\u015b\u0158\3\2\2\2\u015b\u015c\3\2\2\2\u015c"+
		"\u015d\3\2\2\2\u015d\u015e\7a\2\2\u015e\u015f\5b\62\2\u015f\u0160\5\66"+
		"\34\2\u0160\u0161\5b\62\2\u0161\u0162\7c\2\2\u0162?\3\2\2\2\u0163\u0164"+
		"\5B\"\2\u0164\u0165\5b\62\2\u0165\u0167\3\2\2\2\u0166\u0163\3\2\2\2\u0166"+
		"\u0167\3\2\2\2\u0167\u016b\3\2\2\2\u0168\u0169\5H%\2\u0169\u016a\5b\62"+
		"\2\u016a\u016c\3\2\2\2\u016b\u0168\3\2\2\2\u016b\u016c\3\2\2\2\u016c\u0170"+
		"\3\2\2\2\u016d\u016e\5J&\2\u016e\u016f\5b\62\2\u016f\u0171\3\2\2\2\u0170"+
		"\u016d\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0172\3\2\2\2\u0172\u0173\5L"+
		"\'\2\u0173\u0180\5b\62\2\u0174\u0175\5P)\2\u0175\u0176\5b\62\2\u0176\u0177"+
		"\5N(\2\u0177\u0181\3\2\2\2\u0178\u0179\5R*\2\u0179\u017a\5b\62\2\u017a"+
		"\u017b\5V,\2\u017b\u0181\3\2\2\2\u017c\u017d\5T+\2\u017d\u017e\5b\62\2"+
		"\u017e\u017f\5X-\2\u017f\u0181\3\2\2\2\u0180\u0174\3\2\2\2\u0180\u0178"+
		"\3\2\2\2\u0180\u017c\3\2\2\2\u0181A\3\2\2\2\u0182\u0183\7A\2\2\u0183\u0184"+
		"\5^\60\2\u0184\u0187\5D#\2\u0185\u0188\5^\60\2\u0186\u0188\5F$\2\u0187"+
		"\u0185\3\2\2\2\u0187\u0186\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u018a\7C"+
		"\2\2\u018aC\3\2\2\2\u018b\u018c\7\24\2\2\u018c\u018d\7\24\2\2\u018dE\3"+
		"\2\2\2\u018e\u018f\7\20\2\2\u018fG\3\2\2\2\u0190\u0191\78\2\2\u0191I\3"+
		"\2\2\2\u0192\u0195\5\"\22\2\u0193\u0195\5 \21\2\u0194\u0192\3\2\2\2\u0194"+
		"\u0193\3\2\2\2\u0195K\3\2\2\2\u0196\u0199\5\26\f\2\u0197\u0199\5\34\17"+
		"\2\u0198\u0196\3\2\2\2\u0198\u0197\3\2\2\2\u0199M\3\2\2\2\u019a\u01a5"+
		"\5\4\3\2\u019b\u019c\7\16\2\2\u019c\u019f\5b\62\2\u019d\u01a0\5\6\4\2"+
		"\u019e\u01a0\5\b\5\2\u019f\u019d\3\2\2\2\u019f\u019e\3\2\2\2\u01a0\u01a1"+
		"\3\2\2\2\u01a1\u01a2\5b\62\2\u01a2\u01a3\7\17\2\2\u01a3\u01a5\3\2\2\2"+
		"\u01a4\u019a\3\2\2\2\u01a4\u019b\3\2\2\2\u01a5O\3\2\2\2\u01a6\u01aa\7"+
		"#\2\2\u01a7\u01a8\7\7\2\2\u01a8\u01aa\7#\2\2\u01a9\u01a6\3\2\2\2\u01a9"+
		"\u01a7\3\2\2\2\u01aaQ\3\2\2\2\u01ab\u01b5\7#\2\2\u01ac\u01ad\7\7\2\2\u01ad"+
		"\u01b5\7#\2\2\u01ae\u01af\7\"\2\2\u01af\u01b5\7#\2\2\u01b0\u01b5\7\"\2"+
		"\2\u01b1\u01b2\7$\2\2\u01b2\u01b5\7#\2\2\u01b3\u01b5\7$\2\2\u01b4\u01ab"+
		"\3\2\2\2\u01b4\u01ac\3\2\2\2\u01b4\u01ae\3\2\2\2\u01b4\u01b0\3\2\2\2\u01b4"+
		"\u01b1\3\2\2\2\u01b4\u01b3\3\2\2\2\u01b5S\3\2\2\2\u01b6\u01ba\7#\2\2\u01b7"+
		"\u01b8\7\7\2\2\u01b8\u01ba\7#\2\2\u01b9\u01b6\3\2\2\2\u01b9\u01b7\3\2"+
		"\2\2\u01baU\3\2\2\2\u01bb\u01be\7\t\2\2\u01bc\u01bf\5\\/\2\u01bd\u01bf"+
		"\5Z.\2\u01be\u01bc\3\2\2\2\u01be\u01bd\3\2\2\2\u01bfW\3\2\2\2\u01c0\u01c3"+
		"\5n8\2\u01c1\u01c4\5z>\2\u01c2\u01c4\5|?\2\u01c3\u01c1\3\2\2\2\u01c3\u01c2"+
		"\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6"+
		"\u01c7\3\2\2\2\u01c7\u01c8\5n8\2\u01c8Y\3\2\2\2\u01c9\u01cb\t\13\2\2\u01ca"+
		"\u01c9\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01d0\5v"+
		"<\2\u01cd\u01cf\5r:\2\u01ce\u01cd\3\2\2\2\u01cf\u01d2\3\2\2\2\u01d0\u01ce"+
		"\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01d5\3\2\2\2\u01d2\u01d0\3\2\2\2\u01d3"+
		"\u01d5\5t;\2\u01d4\u01ca\3\2\2\2\u01d4\u01d3\3\2\2\2\u01d5[\3\2\2\2\u01d6"+
		"\u01d7\5Z.\2\u01d7\u01d9\7\24\2\2\u01d8\u01da\5r:\2\u01d9\u01d8\3\2\2"+
		"\2\u01da\u01db\3\2\2\2\u01db\u01d9\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc]"+
		"\3\2\2\2\u01dd\u01e1\5v<\2\u01de\u01e0\5r:\2\u01df\u01de\3\2\2\2\u01e0"+
		"\u01e3\3\2\2\2\u01e1\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2\u01e6\3\2"+
		"\2\2\u01e3\u01e1\3\2\2\2\u01e4\u01e6\5t;\2\u01e5\u01dd\3\2\2\2\u01e5\u01e4"+
		"\3\2\2\2\u01e6_\3\2\2\2\u01e7\u01e8\5v<\2\u01e8\u01e9\5r:\2\u01e9\u01ea"+
		"\5r:\2\u01ea\u01eb\5r:\2\u01eb\u01ec\5r:\2\u01ec\u0248\5r:\2\u01ed\u01ef"+
		"\5r:\2\u01ee\u01ed\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef\u0249\3\2\2\2\u01f0"+
		"\u01f1\5r:\2\u01f1\u01f2\5r:\2\u01f2\u0249\3\2\2\2\u01f3\u01f4\5r:\2\u01f4"+
		"\u01f5\5r:\2\u01f5\u01f6\5r:\2\u01f6\u0249\3\2\2\2\u01f7\u01f8\5r:\2\u01f8"+
		"\u01f9\5r:\2\u01f9\u01fa\5r:\2\u01fa\u01fb\5r:\2\u01fb\u0249\3\2\2\2\u01fc"+
		"\u01fd\5r:\2\u01fd\u01fe\5r:\2\u01fe\u01ff\5r:\2\u01ff\u0200\5r:\2\u0200"+
		"\u0201\5r:\2\u0201\u0249\3\2\2\2\u0202\u0203\5r:\2\u0203\u0204\5r:\2\u0204"+
		"\u0205\5r:\2\u0205\u0206\5r:\2\u0206\u0207\5r:\2\u0207\u0208\5r:\2\u0208"+
		"\u0249\3\2\2\2\u0209\u020a\5r:\2\u020a\u020b\5r:\2\u020b\u020c\5r:\2\u020c"+
		"\u020d\5r:\2\u020d\u020e\5r:\2\u020e\u020f\5r:\2\u020f\u0210\5r:\2\u0210"+
		"\u0249\3\2\2\2\u0211\u0212\5r:\2\u0212\u0213\5r:\2\u0213\u0214\5r:\2\u0214"+
		"\u0215\5r:\2\u0215\u0216\5r:\2\u0216\u0217\5r:\2\u0217\u0218\5r:\2\u0218"+
		"\u0219\5r:\2\u0219\u0249\3\2\2\2\u021a\u021b\5r:\2\u021b\u021c\5r:\2\u021c"+
		"\u021d\5r:\2\u021d\u021e\5r:\2\u021e\u021f\5r:\2\u021f\u0220\5r:\2\u0220"+
		"\u0221\5r:\2\u0221\u0222\5r:\2\u0222\u0223\5r:\2\u0223\u0249\3\2\2\2\u0224"+
		"\u0225\5r:\2\u0225\u0226\5r:\2\u0226\u0227\5r:\2\u0227\u0228\5r:\2\u0228"+
		"\u0229\5r:\2\u0229\u022a\5r:\2\u022a\u022b\5r:\2\u022b\u022c\5r:\2\u022c"+
		"\u022d\5r:\2\u022d\u022e\5r:\2\u022e\u0249\3\2\2\2\u022f\u0230\5r:\2\u0230"+
		"\u0231\5r:\2\u0231\u0232\5r:\2\u0232\u0233\5r:\2\u0233\u0234\5r:\2\u0234"+
		"\u0235\5r:\2\u0235\u0236\5r:\2\u0236\u0237\5r:\2\u0237\u0238\5r:\2\u0238"+
		"\u0239\5r:\2\u0239\u023a\5r:\2\u023a\u0249\3\2\2\2\u023b\u023c\5r:\2\u023c"+
		"\u023d\5r:\2\u023d\u023e\5r:\2\u023e\u023f\5r:\2\u023f\u0240\5r:\2\u0240"+
		"\u0241\5r:\2\u0241\u0242\5r:\2\u0242\u0243\5r:\2\u0243\u0244\5r:\2\u0244"+
		"\u0245\5r:\2\u0245\u0246\5r:\2\u0246\u0247\5r:\2\u0247\u0249\3\2\2\2\u0248"+
		"\u01ee\3\2\2\2\u0248\u01f0\3\2\2\2\u0248\u01f3\3\2\2\2\u0248\u01f7\3\2"+
		"\2\2\u0248\u01fc\3\2\2\2\u0248\u0202\3\2\2\2\u0248\u0209\3\2\2\2\u0248"+
		"\u0211\3\2\2\2\u0248\u021a\3\2\2\2\u0248\u0224\3\2\2\2\u0248\u022f\3\2"+
		"\2\2\u0248\u023b\3\2\2\2\u0249a\3\2\2\2\u024a\u024f\5f\64\2\u024b\u024f"+
		"\5h\65\2\u024c\u024f\5j\66\2\u024d\u024f\5l\67\2\u024e\u024a\3\2\2\2\u024e"+
		"\u024b\3\2\2\2\u024e\u024c\3\2\2\2\u024e\u024d\3\2\2\2\u024f\u0252\3\2"+
		"\2\2\u0250\u024e\3\2\2\2\u0250\u0251\3\2\2\2\u0251c\3\2\2\2\u0252\u0250"+
		"\3\2\2\2\u0253\u0258\5f\64\2\u0254\u0258\5h\65\2\u0255\u0258\5j\66\2\u0256"+
		"\u0258\5l\67\2\u0257\u0253\3\2\2\2\u0257\u0254\3\2\2\2\u0257\u0255\3\2"+
		"\2\2\u0257\u0256\3\2\2\2\u0258\u0259\3\2\2\2\u0259\u0257\3\2\2\2\u0259"+
		"\u025a\3\2\2\2\u025ae\3\2\2\2\u025b\u025c\7\6\2\2\u025cg\3\2\2\2\u025d"+
		"\u025e\7\3\2\2\u025ei\3\2\2\2\u025f\u0260\7\5\2\2\u0260k\3\2\2\2\u0261"+
		"\u0262\7\4\2\2\u0262m\3\2\2\2\u0263\u0264\7\b\2\2\u0264o\3\2\2\2\u0265"+
		"\u0266\7B\2\2\u0266q\3\2\2\2\u0267\u0268\t\f\2\2\u0268s\3\2\2\2\u0269"+
		"\u026a\7\26\2\2\u026au\3\2\2\2\u026b\u026c\t\r\2\2\u026cw\3\2\2\2\u026d"+
		"\u0273\t\16\2\2\u026e\u0273\t\17\2\2\u026f\u0273\5~@\2\u0270\u0273\5\u0080"+
		"A\2\u0271\u0273\5\u0082B\2\u0272\u026d\3\2\2\2\u0272\u026e\3\2\2\2\u0272"+
		"\u026f\3\2\2\2\u0272\u0270\3\2\2\2\u0272\u0271\3\2\2\2\u0273y\3\2\2\2"+
		"\u0274\u027e\5h\65\2\u0275\u027e\5j\66\2\u0276\u027e\5l\67\2\u0277\u027e"+
		"\t\20\2\2\u0278\u027e\t\21\2\2\u0279\u027e\t\22\2\2\u027a\u027e\5~@\2"+
		"\u027b\u027e\5\u0080A\2\u027c\u027e\5\u0082B\2\u027d\u0274\3\2\2\2\u027d"+
		"\u0275\3\2\2\2\u027d\u0276\3\2\2\2\u027d\u0277\3\2\2\2\u027d\u0278\3\2"+
		"\2\2\u027d\u0279\3\2\2\2\u027d\u027a\3\2\2\2\u027d\u027b\3\2\2\2\u027d"+
		"\u027c\3\2\2\2\u027e{\3\2\2\2\u027f\u0280\5p9\2\u0280\u0281\5n8\2\u0281"+
		"\u0286\3\2\2\2\u0282\u0283\5p9\2\u0283\u0284\5p9\2\u0284\u0286\3\2\2\2"+
		"\u0285\u027f\3\2\2\2\u0285\u0282\3\2\2\2\u0286}\3\2\2\2\u0287\u0288\t"+
		"\23\2\2\u0288\u0289\5\u0084C\2\u0289\177\3\2\2\2\u028a\u028b\7\u00c3\2"+
		"\2\u028b\u028c\t\24\2\2\u028c\u0299\5\u0084C\2\u028d\u028e\t\25\2\2\u028e"+
		"\u028f\5\u0084C\2\u028f\u0290\5\u0084C\2\u0290\u0299\3\2\2\2\u0291\u0292"+
		"\7\u00d0\2\2\u0292\u0293\t\26\2\2\u0293\u0299\5\u0084C\2\u0294\u0295\t"+
		"\27\2\2\u0295\u0296\5\u0084C\2\u0296\u0297\5\u0084C\2\u0297\u0299\3\2"+
		"\2\2\u0298\u028a\3\2\2\2\u0298\u028d\3\2\2\2\u0298\u0291\3\2\2\2\u0298"+
		"\u0294\3\2\2\2\u0299\u0081\3\2\2\2\u029a\u029b\7\u00d3\2\2\u029b\u029c"+
		"\t\30\2\2\u029c\u029d\5\u0084C\2\u029d\u029e\5\u0084C\2\u029e\u02aa\3"+
		"\2\2\2\u029f\u02a0\t\31\2\2\u02a0\u02a1\5\u0084C\2\u02a1\u02a2\5\u0084"+
		"C\2\u02a2\u02a3\5\u0084C\2\u02a3\u02aa\3\2\2\2\u02a4\u02a5\7\u00d7\2\2"+
		"\u02a5\u02a6\t\32\2\2\u02a6\u02a7\5\u0084C\2\u02a7\u02a8\5\u0084C\2\u02a8"+
		"\u02aa\3\2\2\2\u02a9\u029a\3\2\2\2\u02a9\u029f\3\2\2\2\u02a9\u02a4\3\2"+
		"\2\2\u02aa\u0083\3\2\2\2\u02ab\u02ac\t\33\2\2\u02ac\u0085\3\2\2\2;\u008a"+
		"\u0091\u009e\u00a8\u00b2\u00bf\u00c4\u00c9\u00cd\u00d9\u00e0\u00e5\u00ea"+
		"\u00ee\u00f7\u0108\u0119\u0122\u012b\u0135\u013b\u0144\u014d\u0156\u015b"+
		"\u0166\u016b\u0170\u0180\u0187\u0194\u0198\u019f\u01a4\u01a9\u01b4\u01b9"+
		"\u01be\u01c3\u01c5\u01ca\u01d0\u01d4\u01db\u01e1\u01e5\u01ee\u0248\u024e"+
		"\u0250\u0257\u0259\u0272\u027d\u0285\u0298\u02a9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}