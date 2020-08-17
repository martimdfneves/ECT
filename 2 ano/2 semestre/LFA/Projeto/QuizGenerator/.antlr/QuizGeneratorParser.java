// Generated from /home/tabuas/Desktop/uni/lfa/Projeto2020/lfa1920-g01/QuizGenerator/QuizGenerator.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QuizGeneratorParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, NUM=56, ID=57, WORD=58, WS=59, COMMENT=60;
	public static final int
		RULE_program = 0, RULE_stat = 1, RULE_forBlock = 2, RULE_endf = 3, RULE_ifBlock = 4, 
		RULE_endif = 5, RULE_other = 6, RULE_condition = 7, RULE_instructions = 8, 
		RULE_createQuestion = 9, RULE_assignment = 10, RULE_declaration = 11, 
		RULE_attribution = 12, RULE_expr = 13, RULE_type = 14, RULE_bdAttribution = 15, 
		RULE_questionType = 16, RULE_questionDeclaration = 17, RULE_questionAttribution = 18, 
		RULE_command = 19, RULE_mathExpr = 20, RULE_randMethod = 21, RULE_testType = 22, 
		RULE_difficulty = 23;
	public static final String[] ruleNames = {
		"program", "stat", "forBlock", "endf", "ifBlock", "endif", "other", "condition", 
		"instructions", "createQuestion", "assignment", "declaration", "attribution", 
		"expr", "type", "bdAttribution", "questionType", "questionDeclaration", 
		"questionAttribution", "command", "mathExpr", "randMethod", "testType", 
		"difficulty"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'Begin'", "'create'", "'endcreate'", "';'", "'for'", "'in'", "':'", 
		"'endfor'", "'if'", "'('", "')'", "'endif'", "'else'", "'=='", "'.correctAnswer()'", 
		"'and'", "'or'", "'not'", "'>='", "'<='", "'>'", "'<'", "'Question'", 
		"'.text'", "'='", "'.theme'", "'.difficulty'", "'.answer'", "','", "'.name'", 
		"'[]'", "'['", "']'", "'String'", "'int'", "'double'", "'DB'", "'load'", 
		"'.get'", "'answersMode'", "'.add'", "'rand'", "'.numAnswers'", "'print'", 
		"'userAnswer'", "'*'", "'/'", "'+'", "'-'", "'.answers()'", "'multipleChoice'", 
		"'trueOrFalse'", "'easy'", "'medium'", "'hard'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "NUM", "ID", "WORD", "WS", 
		"COMMENT"
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
	public String getGrammarFileName() { return "QuizGenerator.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QuizGeneratorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode EOF() { return getToken(QuizGeneratorParser.EOF, 0); }
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(T__0);
			setState(49);
			match(T__1);
			setState(50);
			match(ID);
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__8) | (1L << T__22) | (1L << T__30) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__39) | (1L << T__41) | (1L << T__43) | (1L << ID))) != 0)) {
				{
				{
				setState(51);
				stat();
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			match(T__2);
			setState(58);
			match(EOF);
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

	public static class StatContext extends ParserRuleContext {
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IfStatContext extends StatContext {
		public IfBlockContext ifBlock() {
			return getRuleContext(IfBlockContext.class,0);
		}
		public IfStatContext(StatContext ctx) { copyFrom(ctx); }
	}
	public static class InstStatContext extends StatContext {
		public InstructionsContext instructions() {
			return getRuleContext(InstructionsContext.class,0);
		}
		public InstStatContext(StatContext ctx) { copyFrom(ctx); }
	}
	public static class ForStatContext extends StatContext {
		public ForBlockContext forBlock() {
			return getRuleContext(ForBlockContext.class,0);
		}
		public ForStatContext(StatContext ctx) { copyFrom(ctx); }
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stat);
		try {
			setState(65);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__22:
			case T__30:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__39:
			case T__41:
			case T__43:
			case ID:
				_localctx = new InstStatContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				instructions();
				setState(61);
				match(T__3);
				}
				break;
			case T__4:
				_localctx = new ForStatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(63);
				forBlock();
				}
				break;
			case T__8:
				_localctx = new IfStatContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(64);
				ifBlock();
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

	public static class ForBlockContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(QuizGeneratorParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(QuizGeneratorParser.ID, i);
		}
		public EndfContext endf() {
			return getRuleContext(EndfContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ForBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forBlock; }
	}

	public final ForBlockContext forBlock() throws RecognitionException {
		ForBlockContext _localctx = new ForBlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_forBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(T__4);
			setState(68);
			match(ID);
			setState(69);
			match(T__5);
			setState(70);
			match(ID);
			setState(71);
			match(T__6);
			setState(73); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(72);
				stat();
				}
				}
				setState(75); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__8) | (1L << T__22) | (1L << T__30) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__39) | (1L << T__41) | (1L << T__43) | (1L << ID))) != 0) );
			setState(77);
			endf();
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

	public static class EndfContext extends ParserRuleContext {
		public EndfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endf; }
	}

	public final EndfContext endf() throws RecognitionException {
		EndfContext _localctx = new EndfContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_endf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(T__7);
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

	public static class IfBlockContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public EndifContext endif() {
			return getRuleContext(EndifContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public OtherContext other() {
			return getRuleContext(OtherContext.class,0);
		}
		public IfBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifBlock; }
	}

	public final IfBlockContext ifBlock() throws RecognitionException {
		IfBlockContext _localctx = new IfBlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ifBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__8);
			setState(82);
			match(T__9);
			setState(83);
			condition();
			setState(84);
			match(T__10);
			setState(85);
			match(T__6);
			setState(87); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(86);
				stat();
				}
				}
				setState(89); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__8) | (1L << T__22) | (1L << T__30) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__39) | (1L << T__41) | (1L << T__43) | (1L << ID))) != 0) );
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(91);
				other();
				}
			}

			setState(94);
			endif();
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

	public static class EndifContext extends ParserRuleContext {
		public EndifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endif; }
	}

	public final EndifContext endif() throws RecognitionException {
		EndifContext _localctx = new EndifContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_endif);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(T__11);
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

	public static class OtherContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public OtherContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_other; }
	}

	public final OtherContext other() throws RecognitionException {
		OtherContext _localctx = new OtherContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_other);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__12);
			setState(99);
			match(T__6);
			setState(101); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(100);
				stat();
				}
				}
				setState(103); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__8) | (1L << T__22) | (1L << T__30) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__39) | (1L << T__41) | (1L << T__43) | (1L << ID))) != 0) );
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

	public static class ConditionContext extends ParserRuleContext {
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	 
		public ConditionContext() { }
		public void copyFrom(ConditionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CondLowEqContext extends ConditionContext {
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public CondLowEqContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondEqualsContext extends ConditionContext {
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public CondEqualsContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondBigContext extends ConditionContext {
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public CondBigContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondBigEqContext extends ConditionContext {
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public CondBigEqContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondCorrectAnswerContext extends ConditionContext {
		public MathExprContext mathExpr() {
			return getRuleContext(MathExprContext.class,0);
		}
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public CondCorrectAnswerContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondAndContext extends ConditionContext {
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public CondAndContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondOrContext extends ConditionContext {
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public CondOrContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondLowContext extends ConditionContext {
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public CondLowContext(ConditionContext ctx) { copyFrom(ctx); }
	}
	public static class CondNotContext extends ConditionContext {
		public MathExprContext mathExpr() {
			return getRuleContext(MathExprContext.class,0);
		}
		public CondNotContext(ConditionContext ctx) { copyFrom(ctx); }
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_condition);
		try {
			setState(140);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new CondCorrectAnswerContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				mathExpr(0);
				setState(106);
				match(T__13);
				setState(107);
				match(ID);
				setState(108);
				match(T__14);
				}
				break;
			case 2:
				_localctx = new CondAndContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(110);
				mathExpr(0);
				setState(111);
				match(T__15);
				setState(112);
				mathExpr(0);
				}
				break;
			case 3:
				_localctx = new CondOrContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(114);
				mathExpr(0);
				setState(115);
				match(T__16);
				setState(116);
				mathExpr(0);
				}
				break;
			case 4:
				_localctx = new CondNotContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(118);
				match(T__17);
				setState(119);
				mathExpr(0);
				}
				break;
			case 5:
				_localctx = new CondEqualsContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(120);
				mathExpr(0);
				setState(121);
				match(T__13);
				setState(122);
				mathExpr(0);
				}
				break;
			case 6:
				_localctx = new CondBigEqContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(124);
				mathExpr(0);
				setState(125);
				match(T__18);
				setState(126);
				mathExpr(0);
				}
				break;
			case 7:
				_localctx = new CondLowEqContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(128);
				mathExpr(0);
				setState(129);
				match(T__19);
				setState(130);
				mathExpr(0);
				}
				break;
			case 8:
				_localctx = new CondBigContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(132);
				mathExpr(0);
				setState(133);
				match(T__20);
				setState(134);
				mathExpr(0);
				}
				break;
			case 9:
				_localctx = new CondLowContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(136);
				mathExpr(0);
				setState(137);
				match(T__21);
				setState(138);
				mathExpr(0);
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

	public static class InstructionsContext extends ParserRuleContext {
		public InstructionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instructions; }
	 
		public InstructionsContext() { }
		public void copyFrom(InstructionsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CommandInstContext extends InstructionsContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public CommandInstContext(InstructionsContext ctx) { copyFrom(ctx); }
	}
	public static class AssignInstContext extends InstructionsContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public AssignInstContext(InstructionsContext ctx) { copyFrom(ctx); }
	}
	public static class QuestInstContext extends InstructionsContext {
		public CreateQuestionContext createQuestion() {
			return getRuleContext(CreateQuestionContext.class,0);
		}
		public QuestInstContext(InstructionsContext ctx) { copyFrom(ctx); }
	}

	public final InstructionsContext instructions() throws RecognitionException {
		InstructionsContext _localctx = new InstructionsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_instructions);
		try {
			setState(145);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new AssignInstContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				assignment();
				}
				break;
			case 2:
				_localctx = new CommandInstContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(143);
				command();
				}
				break;
			case 3:
				_localctx = new QuestInstContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(144);
				createQuestion();
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

	public static class CreateQuestionContext extends ParserRuleContext {
		public CreateQuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createQuestion; }
	 
		public CreateQuestionContext() { }
		public void copyFrom(CreateQuestionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CreateQuestionThemeContext extends CreateQuestionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public CreateQuestionThemeContext(CreateQuestionContext ctx) { copyFrom(ctx); }
	}
	public static class CreateQuestionAnswerContext extends CreateQuestionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public TerminalNode NUM() { return getToken(QuizGeneratorParser.NUM, 0); }
		public CreateQuestionAnswerContext(CreateQuestionContext ctx) { copyFrom(ctx); }
	}
	public static class CreateQuestionDifficultyContext extends CreateQuestionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public DifficultyContext difficulty() {
			return getRuleContext(DifficultyContext.class,0);
		}
		public CreateQuestionDifficultyContext(CreateQuestionContext ctx) { copyFrom(ctx); }
	}
	public static class CreateQuestionphraseContext extends CreateQuestionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public CreateQuestionphraseContext(CreateQuestionContext ctx) { copyFrom(ctx); }
	}
	public static class CreateQuestionNameContext extends CreateQuestionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public CreateQuestionNameContext(CreateQuestionContext ctx) { copyFrom(ctx); }
	}

	public final CreateQuestionContext createQuestion() throws RecognitionException {
		CreateQuestionContext _localctx = new CreateQuestionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_createQuestion);
		try {
			setState(171);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new CreateQuestionphraseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(147);
				match(T__22);
				setState(148);
				match(ID);
				setState(149);
				match(T__23);
				setState(150);
				match(T__24);
				setState(151);
				match(WORD);
				}
				break;
			case 2:
				_localctx = new CreateQuestionThemeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(152);
				match(ID);
				setState(153);
				match(T__25);
				setState(154);
				match(T__24);
				setState(155);
				match(WORD);
				}
				break;
			case 3:
				_localctx = new CreateQuestionDifficultyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(156);
				match(ID);
				setState(157);
				match(T__26);
				setState(158);
				match(T__24);
				setState(159);
				difficulty();
				}
				break;
			case 4:
				_localctx = new CreateQuestionAnswerContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(160);
				match(ID);
				setState(161);
				match(T__27);
				setState(162);
				match(T__9);
				setState(163);
				match(WORD);
				setState(164);
				match(T__28);
				setState(165);
				match(NUM);
				setState(166);
				match(T__10);
				}
				break;
			case 5:
				_localctx = new CreateQuestionNameContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(167);
				match(ID);
				setState(168);
				match(T__29);
				setState(169);
				match(T__24);
				setState(170);
				match(WORD);
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

	public static class AssignmentContext extends ParserRuleContext {
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	 
		public AssignmentContext() { }
		public void copyFrom(AssignmentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DeclarAssignContext extends AssignmentContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public DeclarAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
	}
	public static class QuestDeclarAssignContext extends AssignmentContext {
		public QuestionDeclarationContext questionDeclaration() {
			return getRuleContext(QuestionDeclarationContext.class,0);
		}
		public QuestDeclarAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
	}
	public static class QuestAttribAssignContext extends AssignmentContext {
		public QuestionAttributionContext questionAttribution() {
			return getRuleContext(QuestionAttributionContext.class,0);
		}
		public QuestAttribAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
	}
	public static class AttribAssignContext extends AssignmentContext {
		public AttributionContext attribution() {
			return getRuleContext(AttributionContext.class,0);
		}
		public AttribAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
	}
	public static class BdAttribAssignContext extends AssignmentContext {
		public BdAttributionContext bdAttribution() {
			return getRuleContext(BdAttributionContext.class,0);
		}
		public BdAttribAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assignment);
		try {
			setState(178);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new DeclarAssignContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(173);
				declaration();
				}
				break;
			case 2:
				_localctx = new AttribAssignContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				attribution();
				}
				break;
			case 3:
				_localctx = new QuestDeclarAssignContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(175);
				questionDeclaration();
				}
				break;
			case 4:
				_localctx = new QuestAttribAssignContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(176);
				questionAttribution();
				}
				break;
			case 5:
				_localctx = new BdAttribAssignContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(177);
				bdAttribution();
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

	public static class DeclarationContext extends ParserRuleContext {
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	 
		public DeclarationContext() { }
		public void copyFrom(DeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DeclarArrayContext extends DeclarationContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public DeclarArrayContext(DeclarationContext ctx) { copyFrom(ctx); }
	}
	public static class DeclarVarContext extends DeclarationContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public DeclarVarContext(DeclarationContext ctx) { copyFrom(ctx); }
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_declaration);
		try {
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new DeclarVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(180);
				type();
				setState(181);
				match(ID);
				}
				break;
			case 2:
				_localctx = new DeclarArrayContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(183);
				type();
				setState(184);
				match(T__30);
				setState(185);
				match(ID);
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

	public static class AttributionContext extends ParserRuleContext {
		public AttributionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribution; }
	 
		public AttributionContext() { }
		public void copyFrom(AttributionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AttribArrayContext extends AttributionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AttribArrayContext(AttributionContext ctx) { copyFrom(ctx); }
	}
	public static class AttribVarContext extends AttributionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AttribVarContext(AttributionContext ctx) { copyFrom(ctx); }
	}

	public final AttributionContext attribution() throws RecognitionException {
		AttributionContext _localctx = new AttributionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_attribution);
		int _la;
		try {
			int _alt;
			setState(213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new AttribVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35))) != 0)) {
					{
					setState(189);
					type();
					}
				}

				setState(192);
				match(ID);
				setState(193);
				match(T__24);
				setState(194);
				expr();
				}
				break;
			case 2:
				_localctx = new AttribArrayContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35))) != 0)) {
					{
					setState(195);
					type();
					}
				}

				setState(198);
				match(T__30);
				setState(199);
				match(ID);
				setState(200);
				match(T__24);
				setState(201);
				match(T__31);
				setState(207);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(202);
						expr();
						setState(203);
						match(T__28);
						}
						} 
					}
					setState(209);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				setState(210);
				expr();
				setState(211);
				match(T__32);
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

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WordExprContext extends ExprContext {
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public WordExprContext(ExprContext ctx) { copyFrom(ctx); }
	}
	public static class MathContext extends ExprContext {
		public MathExprContext mathExpr() {
			return getRuleContext(MathExprContext.class,0);
		}
		public MathContext(ExprContext ctx) { copyFrom(ctx); }
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expr);
		try {
			setState(217);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WORD:
				_localctx = new WordExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(215);
				match(WORD);
				}
				break;
			case T__9:
			case NUM:
			case ID:
				_localctx = new MathContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(216);
				mathExpr(0);
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TypeIntContext extends TypeContext {
		public TypeIntContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class TypeStringContext extends TypeContext {
		public TypeStringContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class TypeDoubleContext extends TypeContext {
		public TypeDoubleContext(TypeContext ctx) { copyFrom(ctx); }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_type);
		try {
			setState(222);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__33:
				_localctx = new TypeStringContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(219);
				match(T__33);
				}
				break;
			case T__34:
				_localctx = new TypeIntContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(220);
				match(T__34);
				}
				break;
			case T__35:
				_localctx = new TypeDoubleContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(221);
				match(T__35);
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

	public static class BdAttributionContext extends ParserRuleContext {
		public BdAttributionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bdAttribution; }
	 
		public BdAttributionContext() { }
		public void copyFrom(BdAttributionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BdAttribContext extends BdAttributionContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public BdAttribContext(BdAttributionContext ctx) { copyFrom(ctx); }
	}

	public final BdAttributionContext bdAttribution() throws RecognitionException {
		BdAttributionContext _localctx = new BdAttributionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_bdAttribution);
		try {
			_localctx = new BdAttribContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			match(T__36);
			setState(225);
			match(ID);
			setState(226);
			match(T__24);
			setState(227);
			match(T__37);
			setState(228);
			match(T__9);
			setState(229);
			match(WORD);
			setState(230);
			match(T__10);
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

	public static class QuestionTypeContext extends ParserRuleContext {
		public QuestionTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionType; }
	}

	public final QuestionTypeContext questionType() throws RecognitionException {
		QuestionTypeContext _localctx = new QuestionTypeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_questionType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(T__22);
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

	public static class QuestionDeclarationContext extends ParserRuleContext {
		public QuestionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionDeclaration; }
	 
		public QuestionDeclarationContext() { }
		public void copyFrom(QuestionDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class QuestDeclarVarContext extends QuestionDeclarationContext {
		public QuestionTypeContext questionType() {
			return getRuleContext(QuestionTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public QuestDeclarVarContext(QuestionDeclarationContext ctx) { copyFrom(ctx); }
	}
	public static class QuestDeclarArrayContext extends QuestionDeclarationContext {
		public QuestionTypeContext questionType() {
			return getRuleContext(QuestionTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public QuestDeclarArrayContext(QuestionDeclarationContext ctx) { copyFrom(ctx); }
	}

	public final QuestionDeclarationContext questionDeclaration() throws RecognitionException {
		QuestionDeclarationContext _localctx = new QuestionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_questionDeclaration);
		try {
			setState(241);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new QuestDeclarVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(234);
				questionType();
				setState(235);
				match(ID);
				}
				break;
			case 2:
				_localctx = new QuestDeclarArrayContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				questionType();
				setState(238);
				match(T__30);
				setState(239);
				match(ID);
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

	public static class QuestionAttributionContext extends ParserRuleContext {
		public QuestionAttributionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionAttribution; }
	 
		public QuestionAttributionContext() { }
		public void copyFrom(QuestionAttributionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class QuestAttribVarContext extends QuestionAttributionContext {
		public List<TerminalNode> ID() { return getTokens(QuizGeneratorParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(QuizGeneratorParser.ID, i);
		}
		public DifficultyContext difficulty() {
			return getRuleContext(DifficultyContext.class,0);
		}
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public QuestionTypeContext questionType() {
			return getRuleContext(QuestionTypeContext.class,0);
		}
		public QuestAttribVarContext(QuestionAttributionContext ctx) { copyFrom(ctx); }
	}
	public static class QuestAttribArrayContext extends QuestionAttributionContext {
		public List<TerminalNode> ID() { return getTokens(QuizGeneratorParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(QuizGeneratorParser.ID, i);
		}
		public TerminalNode NUM() { return getToken(QuizGeneratorParser.NUM, 0); }
		public DifficultyContext difficulty() {
			return getRuleContext(DifficultyContext.class,0);
		}
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public QuestionTypeContext questionType() {
			return getRuleContext(QuestionTypeContext.class,0);
		}
		public QuestAttribArrayContext(QuestionAttributionContext ctx) { copyFrom(ctx); }
	}

	public final QuestionAttributionContext questionAttribution() throws RecognitionException {
		QuestionAttributionContext _localctx = new QuestionAttributionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_questionAttribution);
		int _la;
		try {
			setState(272);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				_localctx = new QuestAttribVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(243);
					questionType();
					}
				}

				setState(246);
				match(ID);
				setState(247);
				match(T__24);
				setState(248);
				match(ID);
				setState(249);
				match(T__38);
				setState(250);
				match(T__9);
				setState(251);
				difficulty();
				setState(252);
				match(T__28);
				setState(253);
				_la = _input.LA(1);
				if ( !(_la==ID || _la==WORD) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(254);
				match(T__10);
				}
				break;
			case 2:
				_localctx = new QuestAttribArrayContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(257);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(256);
					questionType();
					}
				}

				setState(259);
				match(T__30);
				setState(260);
				match(ID);
				setState(261);
				match(T__24);
				setState(262);
				match(ID);
				setState(263);
				match(T__38);
				setState(264);
				match(T__9);
				setState(265);
				match(NUM);
				setState(266);
				match(T__28);
				setState(267);
				difficulty();
				setState(268);
				match(T__28);
				setState(269);
				_la = _input.LA(1);
				if ( !(_la==ID || _la==WORD) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(270);
				match(T__10);
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

	public static class CommandContext extends ParserRuleContext {
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	 
		public CommandContext() { }
		public void copyFrom(CommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RandCommandContext extends CommandContext {
		public RandMethodContext randMethod() {
			return getRuleContext(RandMethodContext.class,0);
		}
		public RandCommandContext(CommandContext ctx) { copyFrom(ctx); }
	}
	public static class PrintCommandContext extends CommandContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode WORD() { return getToken(QuizGeneratorParser.WORD, 0); }
		public PrintCommandContext(CommandContext ctx) { copyFrom(ctx); }
	}
	public static class UserAnswerContext extends CommandContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public UserAnswerContext(CommandContext ctx) { copyFrom(ctx); }
	}
	public static class AnswerModeCommandContext extends CommandContext {
		public TestTypeContext testType() {
			return getRuleContext(TestTypeContext.class,0);
		}
		public AnswerModeCommandContext(CommandContext ctx) { copyFrom(ctx); }
	}
	public static class AddCommandContext extends CommandContext {
		public List<TerminalNode> ID() { return getTokens(QuizGeneratorParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(QuizGeneratorParser.ID, i);
		}
		public AddCommandContext(CommandContext ctx) { copyFrom(ctx); }
	}
	public static class NumAnswersCommandContext extends CommandContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public TerminalNode NUM() { return getToken(QuizGeneratorParser.NUM, 0); }
		public NumAnswersCommandContext(CommandContext ctx) { copyFrom(ctx); }
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_command);
		int _la;
		try {
			setState(294);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new AnswerModeCommandContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				match(T__39);
				setState(275);
				match(T__24);
				setState(276);
				testType();
				}
				break;
			case 2:
				_localctx = new AddCommandContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(277);
				match(ID);
				setState(278);
				match(T__40);
				setState(279);
				match(T__9);
				setState(280);
				match(ID);
				setState(281);
				match(T__10);
				}
				break;
			case 3:
				_localctx = new RandCommandContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(282);
				match(T__41);
				setState(283);
				randMethod();
				}
				break;
			case 4:
				_localctx = new NumAnswersCommandContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(284);
				match(ID);
				setState(285);
				match(T__42);
				setState(286);
				match(T__9);
				setState(287);
				match(NUM);
				setState(288);
				match(T__10);
				}
				break;
			case 5:
				_localctx = new PrintCommandContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(289);
				match(T__43);
				setState(290);
				_la = _input.LA(1);
				if ( !(_la==ID || _la==WORD) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 6:
				_localctx = new UserAnswerContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(291);
				match(ID);
				setState(292);
				match(T__24);
				setState(293);
				match(T__44);
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

	public static class MathExprContext extends ParserRuleContext {
		public MathExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mathExpr; }
	 
		public MathExprContext() { }
		public void copyFrom(MathExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AddSubExprContext extends MathExprContext {
		public Token op;
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public AddSubExprContext(MathExprContext ctx) { copyFrom(ctx); }
	}
	public static class MultDivExprContext extends MathExprContext {
		public Token op;
		public List<MathExprContext> mathExpr() {
			return getRuleContexts(MathExprContext.class);
		}
		public MathExprContext mathExpr(int i) {
			return getRuleContext(MathExprContext.class,i);
		}
		public MultDivExprContext(MathExprContext ctx) { copyFrom(ctx); }
	}
	public static class NumMathExprContext extends MathExprContext {
		public TerminalNode NUM() { return getToken(QuizGeneratorParser.NUM, 0); }
		public NumMathExprContext(MathExprContext ctx) { copyFrom(ctx); }
	}
	public static class ParenthExprContext extends MathExprContext {
		public MathExprContext mathExpr() {
			return getRuleContext(MathExprContext.class,0);
		}
		public ParenthExprContext(MathExprContext ctx) { copyFrom(ctx); }
	}
	public static class IdExprContext extends MathExprContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public IdExprContext(MathExprContext ctx) { copyFrom(ctx); }
	}

	public final MathExprContext mathExpr() throws RecognitionException {
		return mathExpr(0);
	}

	private MathExprContext mathExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MathExprContext _localctx = new MathExprContext(_ctx, _parentState);
		MathExprContext _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_mathExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUM:
				{
				_localctx = new NumMathExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(297);
				match(NUM);
				}
				break;
			case T__9:
				{
				_localctx = new ParenthExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(298);
				match(T__9);
				setState(299);
				mathExpr(0);
				setState(300);
				match(T__10);
				}
				break;
			case ID:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(302);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(313);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(311);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						_localctx = new MultDivExprContext(new MathExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathExpr);
						setState(305);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(306);
						((MultDivExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__45 || _la==T__46) ) {
							((MultDivExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(307);
						mathExpr(6);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExprContext(new MathExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_mathExpr);
						setState(308);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(309);
						((AddSubExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__47 || _la==T__48) ) {
							((AddSubExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(310);
						mathExpr(5);
						}
						break;
					}
					} 
				}
				setState(315);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RandMethodContext extends ParserRuleContext {
		public RandMethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_randMethod; }
	 
		public RandMethodContext() { }
		public void copyFrom(RandMethodContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IdRandMethodContext extends RandMethodContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public IdRandMethodContext(RandMethodContext ctx) { copyFrom(ctx); }
	}
	public static class AnswersRandMethodContext extends RandMethodContext {
		public TerminalNode ID() { return getToken(QuizGeneratorParser.ID, 0); }
		public AnswersRandMethodContext(RandMethodContext ctx) { copyFrom(ctx); }
	}

	public final RandMethodContext randMethod() throws RecognitionException {
		RandMethodContext _localctx = new RandMethodContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_randMethod);
		try {
			setState(319);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				_localctx = new IdRandMethodContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(316);
				match(ID);
				}
				break;
			case 2:
				_localctx = new AnswersRandMethodContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(317);
				match(ID);
				setState(318);
				match(T__49);
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

	public static class TestTypeContext extends ParserRuleContext {
		public TestTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_testType; }
	 
		public TestTypeContext() { }
		public void copyFrom(TestTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TrueOrFalseTypeContext extends TestTypeContext {
		public TrueOrFalseTypeContext(TestTypeContext ctx) { copyFrom(ctx); }
	}
	public static class MultipleChoiceTypeContext extends TestTypeContext {
		public MultipleChoiceTypeContext(TestTypeContext ctx) { copyFrom(ctx); }
	}

	public final TestTypeContext testType() throws RecognitionException {
		TestTypeContext _localctx = new TestTypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_testType);
		try {
			setState(323);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__50:
				_localctx = new MultipleChoiceTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(321);
				match(T__50);
				}
				break;
			case T__51:
				_localctx = new TrueOrFalseTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(322);
				match(T__51);
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

	public static class DifficultyContext extends ParserRuleContext {
		public DifficultyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_difficulty; }
	 
		public DifficultyContext() { }
		public void copyFrom(DifficultyContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EasyDifficultyContext extends DifficultyContext {
		public EasyDifficultyContext(DifficultyContext ctx) { copyFrom(ctx); }
	}
	public static class HardDifficultyContext extends DifficultyContext {
		public HardDifficultyContext(DifficultyContext ctx) { copyFrom(ctx); }
	}
	public static class MediumDifficultyContext extends DifficultyContext {
		public MediumDifficultyContext(DifficultyContext ctx) { copyFrom(ctx); }
	}

	public final DifficultyContext difficulty() throws RecognitionException {
		DifficultyContext _localctx = new DifficultyContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_difficulty);
		try {
			setState(328);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__52:
				_localctx = new EasyDifficultyContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(325);
				match(T__52);
				}
				break;
			case T__53:
				_localctx = new MediumDifficultyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(326);
				match(T__53);
				}
				break;
			case T__54:
				_localctx = new HardDifficultyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(327);
				match(T__54);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 20:
			return mathExpr_sempred((MathExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean mathExpr_sempred(MathExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3>\u014d\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\2\3\2\7\2\67\n\2\f\2\16\2:\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\5\3D\n\3\3\4\3\4\3\4\3\4\3\4\3\4\6\4L\n\4\r\4\16\4M\3\4\3\4\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\6\6Z\n\6\r\6\16\6[\3\6\5\6_\n\6\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\b\6\bh\n\b\r\b\16\bi\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u008f\n\t\3\n\3\n\3\n\5\n\u0094"+
		"\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00ae\n\13"+
		"\3\f\3\f\3\f\3\f\3\f\5\f\u00b5\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00be"+
		"\n\r\3\16\5\16\u00c1\n\16\3\16\3\16\3\16\3\16\5\16\u00c7\n\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\7\16\u00d0\n\16\f\16\16\16\u00d3\13\16\3\16"+
		"\3\16\3\16\5\16\u00d8\n\16\3\17\3\17\5\17\u00dc\n\17\3\20\3\20\3\20\5"+
		"\20\u00e1\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00f4\n\23\3\24\5\24\u00f7\n\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0104\n\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24"+
		"\u0113\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0129\n\25\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\5\26\u0132\n\26\3\26\3\26\3\26\3\26\3\26\3\26\7\26"+
		"\u013a\n\26\f\26\16\26\u013d\13\26\3\27\3\27\3\27\5\27\u0142\n\27\3\30"+
		"\3\30\5\30\u0146\n\30\3\31\3\31\3\31\5\31\u014b\n\31\3\31\2\3*\32\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\2\5\3\2;<\3\2\60\61\3"+
		"\2\62\63\2\u0166\2\62\3\2\2\2\4C\3\2\2\2\6E\3\2\2\2\bQ\3\2\2\2\nS\3\2"+
		"\2\2\fb\3\2\2\2\16d\3\2\2\2\20\u008e\3\2\2\2\22\u0093\3\2\2\2\24\u00ad"+
		"\3\2\2\2\26\u00b4\3\2\2\2\30\u00bd\3\2\2\2\32\u00d7\3\2\2\2\34\u00db\3"+
		"\2\2\2\36\u00e0\3\2\2\2 \u00e2\3\2\2\2\"\u00ea\3\2\2\2$\u00f3\3\2\2\2"+
		"&\u0112\3\2\2\2(\u0128\3\2\2\2*\u0131\3\2\2\2,\u0141\3\2\2\2.\u0145\3"+
		"\2\2\2\60\u014a\3\2\2\2\62\63\7\3\2\2\63\64\7\4\2\2\648\7;\2\2\65\67\5"+
		"\4\3\2\66\65\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2"+
		"\2\2;<\7\5\2\2<=\7\2\2\3=\3\3\2\2\2>?\5\22\n\2?@\7\6\2\2@D\3\2\2\2AD\5"+
		"\6\4\2BD\5\n\6\2C>\3\2\2\2CA\3\2\2\2CB\3\2\2\2D\5\3\2\2\2EF\7\7\2\2FG"+
		"\7;\2\2GH\7\b\2\2HI\7;\2\2IK\7\t\2\2JL\5\4\3\2KJ\3\2\2\2LM\3\2\2\2MK\3"+
		"\2\2\2MN\3\2\2\2NO\3\2\2\2OP\5\b\5\2P\7\3\2\2\2QR\7\n\2\2R\t\3\2\2\2S"+
		"T\7\13\2\2TU\7\f\2\2UV\5\20\t\2VW\7\r\2\2WY\7\t\2\2XZ\5\4\3\2YX\3\2\2"+
		"\2Z[\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\^\3\2\2\2]_\5\16\b\2^]\3\2\2\2^_\3"+
		"\2\2\2_`\3\2\2\2`a\5\f\7\2a\13\3\2\2\2bc\7\16\2\2c\r\3\2\2\2de\7\17\2"+
		"\2eg\7\t\2\2fh\5\4\3\2gf\3\2\2\2hi\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\17\3\2"+
		"\2\2kl\5*\26\2lm\7\20\2\2mn\7;\2\2no\7\21\2\2o\u008f\3\2\2\2pq\5*\26\2"+
		"qr\7\22\2\2rs\5*\26\2s\u008f\3\2\2\2tu\5*\26\2uv\7\23\2\2vw\5*\26\2w\u008f"+
		"\3\2\2\2xy\7\24\2\2y\u008f\5*\26\2z{\5*\26\2{|\7\20\2\2|}\5*\26\2}\u008f"+
		"\3\2\2\2~\177\5*\26\2\177\u0080\7\25\2\2\u0080\u0081\5*\26\2\u0081\u008f"+
		"\3\2\2\2\u0082\u0083\5*\26\2\u0083\u0084\7\26\2\2\u0084\u0085\5*\26\2"+
		"\u0085\u008f\3\2\2\2\u0086\u0087\5*\26\2\u0087\u0088\7\27\2\2\u0088\u0089"+
		"\5*\26\2\u0089\u008f\3\2\2\2\u008a\u008b\5*\26\2\u008b\u008c\7\30\2\2"+
		"\u008c\u008d\5*\26\2\u008d\u008f\3\2\2\2\u008ek\3\2\2\2\u008ep\3\2\2\2"+
		"\u008et\3\2\2\2\u008ex\3\2\2\2\u008ez\3\2\2\2\u008e~\3\2\2\2\u008e\u0082"+
		"\3\2\2\2\u008e\u0086\3\2\2\2\u008e\u008a\3\2\2\2\u008f\21\3\2\2\2\u0090"+
		"\u0094\5\26\f\2\u0091\u0094\5(\25\2\u0092\u0094\5\24\13\2\u0093\u0090"+
		"\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0092\3\2\2\2\u0094\23\3\2\2\2\u0095"+
		"\u0096\7\31\2\2\u0096\u0097\7;\2\2\u0097\u0098\7\32\2\2\u0098\u0099\7"+
		"\33\2\2\u0099\u00ae\7<\2\2\u009a\u009b\7;\2\2\u009b\u009c\7\34\2\2\u009c"+
		"\u009d\7\33\2\2\u009d\u00ae\7<\2\2\u009e\u009f\7;\2\2\u009f\u00a0\7\35"+
		"\2\2\u00a0\u00a1\7\33\2\2\u00a1\u00ae\5\60\31\2\u00a2\u00a3\7;\2\2\u00a3"+
		"\u00a4\7\36\2\2\u00a4\u00a5\7\f\2\2\u00a5\u00a6\7<\2\2\u00a6\u00a7\7\37"+
		"\2\2\u00a7\u00a8\7:\2\2\u00a8\u00ae\7\r\2\2\u00a9\u00aa\7;\2\2\u00aa\u00ab"+
		"\7 \2\2\u00ab\u00ac\7\33\2\2\u00ac\u00ae\7<\2\2\u00ad\u0095\3\2\2\2\u00ad"+
		"\u009a\3\2\2\2\u00ad\u009e\3\2\2\2\u00ad\u00a2\3\2\2\2\u00ad\u00a9\3\2"+
		"\2\2\u00ae\25\3\2\2\2\u00af\u00b5\5\30\r\2\u00b0\u00b5\5\32\16\2\u00b1"+
		"\u00b5\5$\23\2\u00b2\u00b5\5&\24\2\u00b3\u00b5\5 \21\2\u00b4\u00af\3\2"+
		"\2\2\u00b4\u00b0\3\2\2\2\u00b4\u00b1\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4"+
		"\u00b3\3\2\2\2\u00b5\27\3\2\2\2\u00b6\u00b7\5\36\20\2\u00b7\u00b8\7;\2"+
		"\2\u00b8\u00be\3\2\2\2\u00b9\u00ba\5\36\20\2\u00ba\u00bb\7!\2\2\u00bb"+
		"\u00bc\7;\2\2\u00bc\u00be\3\2\2\2\u00bd\u00b6\3\2\2\2\u00bd\u00b9\3\2"+
		"\2\2\u00be\31\3\2\2\2\u00bf\u00c1\5\36\20\2\u00c0\u00bf\3\2\2\2\u00c0"+
		"\u00c1\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\7;\2\2\u00c3\u00c4\7\33"+
		"\2\2\u00c4\u00d8\5\34\17\2\u00c5\u00c7\5\36\20\2\u00c6\u00c5\3\2\2\2\u00c6"+
		"\u00c7\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c9\7!\2\2\u00c9\u00ca\7;\2"+
		"\2\u00ca\u00cb\7\33\2\2\u00cb\u00d1\7\"\2\2\u00cc\u00cd\5\34\17\2\u00cd"+
		"\u00ce\7\37\2\2\u00ce\u00d0\3\2\2\2\u00cf\u00cc\3\2\2\2\u00d0\u00d3\3"+
		"\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d4\3\2\2\2\u00d3"+
		"\u00d1\3\2\2\2\u00d4\u00d5\5\34\17\2\u00d5\u00d6\7#\2\2\u00d6\u00d8\3"+
		"\2\2\2\u00d7\u00c0\3\2\2\2\u00d7\u00c6\3\2\2\2\u00d8\33\3\2\2\2\u00d9"+
		"\u00dc\7<\2\2\u00da\u00dc\5*\26\2\u00db\u00d9\3\2\2\2\u00db\u00da\3\2"+
		"\2\2\u00dc\35\3\2\2\2\u00dd\u00e1\7$\2\2\u00de\u00e1\7%\2\2\u00df\u00e1"+
		"\7&\2\2\u00e0\u00dd\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00df\3\2\2\2\u00e1"+
		"\37\3\2\2\2\u00e2\u00e3\7\'\2\2\u00e3\u00e4\7;\2\2\u00e4\u00e5\7\33\2"+
		"\2\u00e5\u00e6\7(\2\2\u00e6\u00e7\7\f\2\2\u00e7\u00e8\7<\2\2\u00e8\u00e9"+
		"\7\r\2\2\u00e9!\3\2\2\2\u00ea\u00eb\7\31\2\2\u00eb#\3\2\2\2\u00ec\u00ed"+
		"\5\"\22\2\u00ed\u00ee\7;\2\2\u00ee\u00f4\3\2\2\2\u00ef\u00f0\5\"\22\2"+
		"\u00f0\u00f1\7!\2\2\u00f1\u00f2\7;\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00ec"+
		"\3\2\2\2\u00f3\u00ef\3\2\2\2\u00f4%\3\2\2\2\u00f5\u00f7\5\"\22\2\u00f6"+
		"\u00f5\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00f9\7;"+
		"\2\2\u00f9\u00fa\7\33\2\2\u00fa\u00fb\7;\2\2\u00fb\u00fc\7)\2\2\u00fc"+
		"\u00fd\7\f\2\2\u00fd\u00fe\5\60\31\2\u00fe\u00ff\7\37\2\2\u00ff\u0100"+
		"\t\2\2\2\u0100\u0101\7\r\2\2\u0101\u0113\3\2\2\2\u0102\u0104\5\"\22\2"+
		"\u0103\u0102\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0106"+
		"\7!\2\2\u0106\u0107\7;\2\2\u0107\u0108\7\33\2\2\u0108\u0109\7;\2\2\u0109"+
		"\u010a\7)\2\2\u010a\u010b\7\f\2\2\u010b\u010c\7:\2\2\u010c\u010d\7\37"+
		"\2\2\u010d\u010e\5\60\31\2\u010e\u010f\7\37\2\2\u010f\u0110\t\2\2\2\u0110"+
		"\u0111\7\r\2\2\u0111\u0113\3\2\2\2\u0112\u00f6\3\2\2\2\u0112\u0103\3\2"+
		"\2\2\u0113\'\3\2\2\2\u0114\u0115\7*\2\2\u0115\u0116\7\33\2\2\u0116\u0129"+
		"\5.\30\2\u0117\u0118\7;\2\2\u0118\u0119\7+\2\2\u0119\u011a\7\f\2\2\u011a"+
		"\u011b\7;\2\2\u011b\u0129\7\r\2\2\u011c\u011d\7,\2\2\u011d\u0129\5,\27"+
		"\2\u011e\u011f\7;\2\2\u011f\u0120\7-\2\2\u0120\u0121\7\f\2\2\u0121\u0122"+
		"\7:\2\2\u0122\u0129\7\r\2\2\u0123\u0124\7.\2\2\u0124\u0129\t\2\2\2\u0125"+
		"\u0126\7;\2\2\u0126\u0127\7\33\2\2\u0127\u0129\7/\2\2\u0128\u0114\3\2"+
		"\2\2\u0128\u0117\3\2\2\2\u0128\u011c\3\2\2\2\u0128\u011e\3\2\2\2\u0128"+
		"\u0123\3\2\2\2\u0128\u0125\3\2\2\2\u0129)\3\2\2\2\u012a\u012b\b\26\1\2"+
		"\u012b\u0132\7:\2\2\u012c\u012d\7\f\2\2\u012d\u012e\5*\26\2\u012e\u012f"+
		"\7\r\2\2\u012f\u0132\3\2\2\2\u0130\u0132\7;\2\2\u0131\u012a\3\2\2\2\u0131"+
		"\u012c\3\2\2\2\u0131\u0130\3\2\2\2\u0132\u013b\3\2\2\2\u0133\u0134\f\7"+
		"\2\2\u0134\u0135\t\3\2\2\u0135\u013a\5*\26\b\u0136\u0137\f\6\2\2\u0137"+
		"\u0138\t\4\2\2\u0138\u013a\5*\26\7\u0139\u0133\3\2\2\2\u0139\u0136\3\2"+
		"\2\2\u013a\u013d\3\2\2\2\u013b\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013c"+
		"+\3\2\2\2\u013d\u013b\3\2\2\2\u013e\u0142\7;\2\2\u013f\u0140\7;\2\2\u0140"+
		"\u0142\7\64\2\2\u0141\u013e\3\2\2\2\u0141\u013f\3\2\2\2\u0142-\3\2\2\2"+
		"\u0143\u0146\7\65\2\2\u0144\u0146\7\66\2\2\u0145\u0143\3\2\2\2\u0145\u0144"+
		"\3\2\2\2\u0146/\3\2\2\2\u0147\u014b\7\67\2\2\u0148\u014b\78\2\2\u0149"+
		"\u014b\79\2\2\u014a\u0147\3\2\2\2\u014a\u0148\3\2\2\2\u014a\u0149\3\2"+
		"\2\2\u014b\61\3\2\2\2\368CM[^i\u008e\u0093\u00ad\u00b4\u00bd\u00c0\u00c6"+
		"\u00d1\u00d7\u00db\u00e0\u00f3\u00f6\u0103\u0112\u0128\u0131\u0139\u013b"+
		"\u0141\u0145\u014a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}