// Generated from D:/2020Spring/Compiler/Compiler/src/Parser\Mx_language.g4 by ANTLR 4.8
package Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Mx_languageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

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
		T__45=46, T__46=47, T__47=48, T__48=49, StringLiteral=50, Identifier=51, 
		DecimalInteger=52, WS=53, BLOCK_COMMENT=54, LINE_COMMENT=55;
	public static final int
		RULE_prog = 0, RULE_variable_definition_line = 1, RULE_variable_definition = 2, 
		RULE_class_definition = 3, RULE_function_definition = 4, RULE_common_type = 5, 
		RULE_variable_type = 6, RULE_block = 7, RULE_statement = 8, RULE_if_block = 9, 
		RULE_while_block = 10, RULE_for_block = 11, RULE_parameter = 12, RULE_expression = 13, 
		RULE_expression_list = 14, RULE_primary = 15, RULE_literal = 16, RULE_creator = 17;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "variable_definition_line", "variable_definition", "class_definition", 
			"function_definition", "common_type", "variable_type", "block", "statement", 
			"if_block", "while_block", "for_block", "parameter", "expression", "expression_list", 
			"primary", "literal", "creator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "';'", "'='", "'class'", "'{'", "'}'", "'('", "')'", "'bool'", 
			"'int'", "'string'", "'void'", "'['", "']'", "'continue'", "'break'", 
			"'return'", "'if'", "'else'", "'while'", "'for'", "'.'", "'new'", "'++'", 
			"'--'", "'+'", "'-'", "'~'", "'!'", "'*'", "'/'", "'%'", "'<<'", "'>>'", 
			"'<'", "'>'", "'>='", "'<='", "'=='", "'!='", "'&'", "'|'", "'^'", "'&&'", 
			"'||'", "'this'", "'true'", "'false'", "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "StringLiteral", "Identifier", "DecimalInteger", "WS", "BLOCK_COMMENT", 
			"LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "Mx_language.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Mx_languageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(Mx_languageParser.EOF, 0); }
		public List<Variable_definition_lineContext> variable_definition_line() {
			return getRuleContexts(Variable_definition_lineContext.class);
		}
		public Variable_definition_lineContext variable_definition_line(int i) {
			return getRuleContext(Variable_definition_lineContext.class,i);
		}
		public List<Class_definitionContext> class_definition() {
			return getRuleContexts(Class_definitionContext.class);
		}
		public Class_definitionContext class_definition(int i) {
			return getRuleContext(Class_definitionContext.class,i);
		}
		public List<Function_definitionContext> function_definition() {
			return getRuleContexts(Function_definitionContext.class);
		}
		public Function_definitionContext function_definition(int i) {
			return getRuleContext(Function_definitionContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << Identifier))) != 0)) {
				{
				setState(39);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(36);
					variable_definition_line();
					}
					break;
				case 2:
					{
					setState(37);
					class_definition();
					}
					break;
				case 3:
					{
					setState(38);
					function_definition();
					}
					break;
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(44);
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

	public static class Variable_definition_lineContext extends ParserRuleContext {
		public Variable_typeContext variable_type() {
			return getRuleContext(Variable_typeContext.class,0);
		}
		public List<Variable_definitionContext> variable_definition() {
			return getRuleContexts(Variable_definitionContext.class);
		}
		public Variable_definitionContext variable_definition(int i) {
			return getRuleContext(Variable_definitionContext.class,i);
		}
		public Variable_definition_lineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_definition_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterVariable_definition_line(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitVariable_definition_line(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitVariable_definition_line(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_definition_lineContext variable_definition_line() throws RecognitionException {
		Variable_definition_lineContext _localctx = new Variable_definition_lineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_variable_definition_line);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			variable_type();
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(47);
				variable_definition();
				}
			}

			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(50);
				match(T__0);
				setState(51);
				variable_definition();
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			match(T__1);
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

	public static class Variable_definitionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Variable_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterVariable_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitVariable_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitVariable_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_definitionContext variable_definition() throws RecognitionException {
		Variable_definitionContext _localctx = new Variable_definitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variable_definition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(Identifier);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(60);
				match(T__2);
				setState(61);
				expression(0);
				}
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

	public static class Class_definitionContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public List<Variable_definition_lineContext> variable_definition_line() {
			return getRuleContexts(Variable_definition_lineContext.class);
		}
		public Variable_definition_lineContext variable_definition_line(int i) {
			return getRuleContext(Variable_definition_lineContext.class,i);
		}
		public List<Function_definitionContext> function_definition() {
			return getRuleContexts(Function_definitionContext.class);
		}
		public Function_definitionContext function_definition(int i) {
			return getRuleContext(Function_definitionContext.class,i);
		}
		public Class_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterClass_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitClass_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitClass_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Class_definitionContext class_definition() throws RecognitionException {
		Class_definitionContext _localctx = new Class_definitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_class_definition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(T__3);
			setState(65);
			((Class_definitionContext)_localctx).name = match(Identifier);
			setState(66);
			match(T__4);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << Identifier))) != 0)) {
				{
				setState(69);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(67);
					variable_definition_line();
					}
					break;
				case 2:
					{
					setState(68);
					function_definition();
					}
					break;
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(74);
			match(T__5);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(75);
				match(T__1);
				}
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

	public static class Function_definitionContext extends ParserRuleContext {
		public Variable_typeContext return_type;
		public Token name;
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public Variable_typeContext variable_type() {
			return getRuleContext(Variable_typeContext.class,0);
		}
		public Function_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterFunction_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitFunction_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitFunction_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_definitionContext function_definition() throws RecognitionException {
		Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_function_definition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(78);
				((Function_definitionContext)_localctx).return_type = variable_type();
				}
				break;
			}
			setState(81);
			((Function_definitionContext)_localctx).name = match(Identifier);
			setState(82);
			match(T__6);
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << Identifier))) != 0)) {
				{
				setState(83);
				parameter();
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(84);
					match(T__0);
					setState(85);
					parameter();
					}
					}
					setState(90);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(93);
			match(T__7);
			setState(94);
			block();
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

	public static class Common_typeContext extends ParserRuleContext {
		public Token type;
		public Common_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_common_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterCommon_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitCommon_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitCommon_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Common_typeContext common_type() throws RecognitionException {
		Common_typeContext _localctx = new Common_typeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_common_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			((Common_typeContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11))) != 0)) ) {
				((Common_typeContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
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

	public static class Variable_typeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public Common_typeContext common_type() {
			return getRuleContext(Common_typeContext.class,0);
		}
		public Variable_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterVariable_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitVariable_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitVariable_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_typeContext variable_type() throws RecognitionException {
		Variable_typeContext _localctx = new Variable_typeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_variable_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				{
				setState(98);
				match(Identifier);
				}
				break;
			case T__8:
			case T__9:
			case T__10:
			case T__11:
				{
				setState(99);
				common_type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__12) {
				{
				{
				setState(102);
				match(T__12);
				setState(103);
				match(T__13);
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(T__4);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__4) | (1L << T__6) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__19) | (1L << T__20) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << StringLiteral) | (1L << Identifier) | (1L << DecimalInteger))) != 0)) {
				{
				{
				setState(110);
				statement();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			match(T__5);
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

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class While_stmtContext extends StatementContext {
		public While_blockContext while_block() {
			return getRuleContext(While_blockContext.class,0);
		}
		public While_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterWhile_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitWhile_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitWhile_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Blank_stmtContext extends StatementContext {
		public Blank_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterBlank_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitBlank_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitBlank_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Block_stmtContext extends StatementContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public Block_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterBlock_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitBlock_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitBlock_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Continue_stmtContext extends StatementContext {
		public Continue_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterContinue_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitContinue_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitContinue_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Var_def_stmtContext extends StatementContext {
		public Variable_definition_lineContext variable_definition_line() {
			return getRuleContext(Variable_definition_lineContext.class,0);
		}
		public Var_def_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterVar_def_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitVar_def_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitVar_def_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class If_stmtContext extends StatementContext {
		public If_blockContext if_block() {
			return getRuleContext(If_blockContext.class,0);
		}
		public If_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterIf_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitIf_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitIf_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Break_stmtContext extends StatementContext {
		public Break_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterBreak_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitBreak_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitBreak_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class For_stmtContext extends StatementContext {
		public For_blockContext for_block() {
			return getRuleContext(For_blockContext.class,0);
		}
		public For_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterFor_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitFor_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitFor_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_stmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Expr_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterExpr_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitExpr_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitExpr_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Return_stmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Return_stmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterReturn_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitReturn_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitReturn_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_statement);
		int _la;
		try {
			setState(136);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new Block_stmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				block();
				}
				break;
			case 2:
				_localctx = new Var_def_stmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(119);
				variable_definition_line();
				}
				break;
			case 3:
				_localctx = new If_stmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(120);
				if_block();
				}
				break;
			case 4:
				_localctx = new For_stmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(121);
				for_block();
				}
				break;
			case 5:
				_localctx = new While_stmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(122);
				while_block();
				}
				break;
			case 6:
				_localctx = new Expr_stmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(123);
				expression(0);
				setState(124);
				match(T__1);
				}
				break;
			case 7:
				_localctx = new Continue_stmtContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(126);
				match(T__14);
				setState(127);
				match(T__1);
				}
				break;
			case 8:
				_localctx = new Break_stmtContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(128);
				match(T__15);
				setState(129);
				match(T__1);
				}
				break;
			case 9:
				_localctx = new Return_stmtContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(130);
				match(T__16);
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << StringLiteral) | (1L << Identifier) | (1L << DecimalInteger))) != 0)) {
					{
					setState(131);
					expression(0);
					}
				}

				setState(134);
				match(T__1);
				}
				break;
			case 10:
				_localctx = new Blank_stmtContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(135);
				match(T__1);
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

	public static class If_blockContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public If_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterIf_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitIf_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitIf_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_blockContext if_block() throws RecognitionException {
		If_blockContext _localctx = new If_blockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_if_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(T__17);
			setState(139);
			match(T__6);
			setState(140);
			expression(0);
			setState(141);
			match(T__7);
			setState(142);
			statement();
			setState(145);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(143);
				match(T__18);
				setState(144);
				statement();
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

	public static class While_blockContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public While_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterWhile_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitWhile_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitWhile_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_blockContext while_block() throws RecognitionException {
		While_blockContext _localctx = new While_blockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_while_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(T__19);
			setState(148);
			match(T__6);
			setState(149);
			expression(0);
			setState(150);
			match(T__7);
			setState(151);
			statement();
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

	public static class For_blockContext extends ParserRuleContext {
		public ExpressionContext init;
		public ExpressionContext cond;
		public ExpressionContext incr;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public For_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterFor_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitFor_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitFor_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final For_blockContext for_block() throws RecognitionException {
		For_blockContext _localctx = new For_blockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_for_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(T__20);
			setState(154);
			match(T__6);
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << StringLiteral) | (1L << Identifier) | (1L << DecimalInteger))) != 0)) {
				{
				setState(155);
				((For_blockContext)_localctx).init = expression(0);
				}
			}

			setState(158);
			match(T__1);
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << StringLiteral) | (1L << Identifier) | (1L << DecimalInteger))) != 0)) {
				{
				setState(159);
				((For_blockContext)_localctx).cond = expression(0);
				}
			}

			setState(162);
			match(T__1);
			setState(164);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << StringLiteral) | (1L << Identifier) | (1L << DecimalInteger))) != 0)) {
				{
				setState(163);
				((For_blockContext)_localctx).incr = expression(0);
				}
			}

			setState(166);
			match(T__7);
			setState(167);
			statement();
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

	public static class ParameterContext extends ParserRuleContext {
		public Variable_typeContext variable_type() {
			return getRuleContext(Variable_typeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			variable_type();
			setState(170);
			match(Identifier);
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

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Aref_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Aref_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterAref_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitAref_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitAref_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Logical_or_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Logical_or_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterLogical_or_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitLogical_or_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitLogical_or_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Member_exprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public Member_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterMember_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitMember_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitMember_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Binary_exprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Binary_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterBinary_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitBinary_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitBinary_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Logical_and_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Logical_and_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterLogical_and_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitLogical_and_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitLogical_and_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Suffix_exprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Suffix_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterSuffix_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitSuffix_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitSuffix_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class New_exprContext extends ExpressionContext {
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public New_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterNew_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitNew_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitNew_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Assign_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Assign_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterAssign_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitAssign_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitAssign_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Prefix_exprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Prefix_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterPrefix_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitPrefix_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitPrefix_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Funcall_exprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public Funcall_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterFuncall_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitFuncall_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitFuncall_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Primary_exprContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public Primary_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterPrimary_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitPrimary_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitPrimary_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case StringLiteral:
			case Identifier:
			case DecimalInteger:
				{
				_localctx = new Primary_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(173);
				primary();
				}
				break;
			case T__22:
				{
				_localctx = new New_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(174);
				match(T__22);
				setState(175);
				creator();
				}
				break;
			case T__23:
			case T__24:
			case T__25:
			case T__26:
				{
				_localctx = new Prefix_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(176);
				((Prefix_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26))) != 0)) ) {
					((Prefix_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(177);
				expression(13);
				}
				break;
			case T__27:
			case T__28:
				{
				_localctx = new Prefix_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(178);
				((Prefix_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__27 || _la==T__28) ) {
					((Prefix_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(179);
				expression(12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(233);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(231);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(182);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(183);
						((Binary_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) ) {
							((Binary_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(184);
						expression(12);
						}
						break;
					case 2:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(185);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(186);
						((Binary_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__25 || _la==T__26) ) {
							((Binary_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(187);
						expression(11);
						}
						break;
					case 3:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(188);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(189);
						((Binary_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__32 || _la==T__33) ) {
							((Binary_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(190);
						expression(10);
						}
						break;
					case 4:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(191);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(192);
						((Binary_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37))) != 0)) ) {
							((Binary_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(193);
						expression(9);
						}
						break;
					case 5:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(194);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(195);
						((Binary_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__38 || _la==T__39) ) {
							((Binary_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(196);
						expression(8);
						}
						break;
					case 6:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(197);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(198);
						((Binary_exprContext)_localctx).op = match(T__40);
						setState(199);
						expression(7);
						}
						break;
					case 7:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(200);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(201);
						((Binary_exprContext)_localctx).op = match(T__41);
						setState(202);
						expression(6);
						}
						break;
					case 8:
						{
						_localctx = new Binary_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(203);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(204);
						((Binary_exprContext)_localctx).op = match(T__42);
						setState(205);
						expression(5);
						}
						break;
					case 9:
						{
						_localctx = new Logical_and_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(206);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(207);
						match(T__43);
						setState(208);
						expression(4);
						}
						break;
					case 10:
						{
						_localctx = new Logical_or_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(209);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(210);
						match(T__44);
						setState(211);
						expression(3);
						}
						break;
					case 11:
						{
						_localctx = new Assign_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(212);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(213);
						match(T__2);
						setState(214);
						expression(1);
						}
						break;
					case 12:
						{
						_localctx = new Member_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(215);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(216);
						match(T__21);
						setState(217);
						match(Identifier);
						}
						break;
					case 13:
						{
						_localctx = new Aref_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(218);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(219);
						match(T__12);
						setState(220);
						expression(0);
						setState(221);
						match(T__13);
						}
						break;
					case 14:
						{
						_localctx = new Funcall_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(223);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(224);
						match(T__6);
						setState(226);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << StringLiteral) | (1L << Identifier) | (1L << DecimalInteger))) != 0)) {
							{
							setState(225);
							expression_list();
							}
						}

						setState(228);
						match(T__7);
						}
						break;
					case 15:
						{
						_localctx = new Suffix_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(229);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(230);
						((Suffix_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__23 || _la==T__24) ) {
							((Suffix_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(235);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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

	public static class Expression_listContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Expression_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterExpression_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitExpression_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitExpression_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression_listContext expression_list() throws RecognitionException {
		Expression_listContext _localctx = new Expression_listContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expression_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			expression(0);
			setState(241);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(237);
				match(T__0);
				setState(238);
				expression(0);
				}
				}
				setState(243);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Sub_exprContext extends PrimaryContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Sub_exprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterSub_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitSub_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitSub_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class This_exprContext extends PrimaryContext {
		public This_exprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterThis_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitThis_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitThis_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Variable_exprContext extends PrimaryContext {
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public Variable_exprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterVariable_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitVariable_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitVariable_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Literal_exprContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public Literal_exprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterLiteral_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitLiteral_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitLiteral_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_primary);
		try {
			setState(251);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				_localctx = new Sub_exprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				match(T__6);
				setState(245);
				expression(0);
				setState(246);
				match(T__7);
				}
				break;
			case T__45:
				_localctx = new This_exprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(248);
				match(T__45);
				}
				break;
			case Identifier:
				_localctx = new Variable_exprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(249);
				match(Identifier);
				}
				break;
			case T__46:
			case T__47:
			case T__48:
			case StringLiteral:
			case DecimalInteger:
				_localctx = new Literal_exprContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(250);
				literal();
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

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Null_constContext extends LiteralContext {
		public Null_constContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterNull_const(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitNull_const(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitNull_const(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Int_constContext extends LiteralContext {
		public TerminalNode DecimalInteger() { return getToken(Mx_languageParser.DecimalInteger, 0); }
		public Int_constContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterInt_const(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitInt_const(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitInt_const(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Bool_constContext extends LiteralContext {
		public Bool_constContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterBool_const(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitBool_const(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitBool_const(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class String_constContext extends LiteralContext {
		public TerminalNode StringLiteral() { return getToken(Mx_languageParser.StringLiteral, 0); }
		public String_constContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterString_const(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitString_const(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitString_const(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_literal);
		try {
			setState(258);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DecimalInteger:
				_localctx = new Int_constContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(253);
				match(DecimalInteger);
				}
				break;
			case StringLiteral:
				_localctx = new String_constContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(254);
				match(StringLiteral);
				}
				break;
			case T__46:
				_localctx = new Bool_constContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(255);
				match(T__46);
				}
				break;
			case T__47:
				_localctx = new Bool_constContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(256);
				match(T__47);
				}
				break;
			case T__48:
				_localctx = new Null_constContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(257);
				match(T__48);
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

	public static class CreatorContext extends ParserRuleContext {
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
	 
		public CreatorContext() { }
		public void copyFrom(CreatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Error_creatorContext extends CreatorContext {
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public Common_typeContext common_type() {
			return getRuleContext(Common_typeContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Error_creatorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterError_creator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitError_creator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitError_creator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Single_creatorContext extends CreatorContext {
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public Single_creatorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterSingle_creator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitSingle_creator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitSingle_creator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Array_creatorContext extends CreatorContext {
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public Common_typeContext common_type() {
			return getRuleContext(Common_typeContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Array_creatorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterArray_creator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitArray_creator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitArray_creator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstructorContext extends CreatorContext {
		public TerminalNode Identifier() { return getToken(Mx_languageParser.Identifier, 0); }
		public ConstructorContext(CreatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).enterConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mx_languageListener ) ((Mx_languageListener)listener).exitConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Mx_languageVisitor ) return ((Mx_languageVisitor<? extends T>)visitor).visitConstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_creator);
		try {
			int _alt;
			setState(309);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				_localctx = new Error_creatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Identifier:
					{
					setState(260);
					match(Identifier);
					}
					break;
				case T__8:
				case T__9:
				case T__10:
				case T__11:
					{
					setState(261);
					common_type();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(268); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(264);
						match(T__12);
						setState(265);
						expression(0);
						setState(266);
						match(T__13);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(270); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(274); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(272);
						match(T__12);
						setState(273);
						match(T__13);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(276); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(282); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(278);
						match(T__12);
						setState(279);
						expression(0);
						setState(280);
						match(T__13);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(284); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				_localctx = new Array_creatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(288);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Identifier:
					{
					setState(286);
					match(Identifier);
					}
					break;
				case T__8:
				case T__9:
				case T__10:
				case T__11:
					{
					setState(287);
					common_type();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(294); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(290);
						match(T__12);
						setState(291);
						expression(0);
						setState(292);
						match(T__13);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(296); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(302);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(298);
						match(T__12);
						setState(299);
						match(T__13);
						}
						} 
					}
					setState(304);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				}
				break;
			case 3:
				_localctx = new ConstructorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(305);
				match(Identifier);
				setState(306);
				match(T__6);
				setState(307);
				match(T__7);
				}
				break;
			case 4:
				_localctx = new Single_creatorContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(308);
				match(Identifier);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 13:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		case 11:
			return precpred(_ctx, 18);
		case 12:
			return precpred(_ctx, 16);
		case 13:
			return precpred(_ctx, 15);
		case 14:
			return precpred(_ctx, 14);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\39\u013a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\7\2*\n\2\f\2\16\2-\13\2\3\2\3\2\3\3\3\3\5\3\63"+
		"\n\3\3\3\3\3\7\3\67\n\3\f\3\16\3:\13\3\3\3\3\3\3\4\3\4\3\4\5\4A\n\4\3"+
		"\5\3\5\3\5\3\5\3\5\7\5H\n\5\f\5\16\5K\13\5\3\5\3\5\5\5O\n\5\3\6\5\6R\n"+
		"\6\3\6\3\6\3\6\3\6\3\6\7\6Y\n\6\f\6\16\6\\\13\6\5\6^\n\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\b\3\b\5\bg\n\b\3\b\3\b\7\bk\n\b\f\b\16\bn\13\b\3\t\3\t\7\tr\n"+
		"\t\f\t\16\tu\13\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\5\n\u0087\n\n\3\n\3\n\5\n\u008b\n\n\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\5\13\u0094\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\5\r"+
		"\u009f\n\r\3\r\3\r\5\r\u00a3\n\r\3\r\3\r\5\r\u00a7\n\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00b7\n\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\5\17\u00e5\n\17\3\17\3\17\3\17\7\17\u00ea\n\17\f\17\16\17\u00ed"+
		"\13\17\3\20\3\20\3\20\7\20\u00f2\n\20\f\20\16\20\u00f5\13\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u00fe\n\21\3\22\3\22\3\22\3\22\3\22\5\22"+
		"\u0105\n\22\3\23\3\23\5\23\u0109\n\23\3\23\3\23\3\23\3\23\6\23\u010f\n"+
		"\23\r\23\16\23\u0110\3\23\3\23\6\23\u0115\n\23\r\23\16\23\u0116\3\23\3"+
		"\23\3\23\3\23\6\23\u011d\n\23\r\23\16\23\u011e\3\23\3\23\5\23\u0123\n"+
		"\23\3\23\3\23\3\23\3\23\6\23\u0129\n\23\r\23\16\23\u012a\3\23\3\23\7\23"+
		"\u012f\n\23\f\23\16\23\u0132\13\23\3\23\3\23\3\23\3\23\5\23\u0138\n\23"+
		"\3\23\2\3\34\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2\13\3\2\13"+
		"\16\3\2\32\35\3\2\36\37\3\2 \"\3\2\34\35\3\2#$\3\2%(\3\2)*\3\2\32\33\2"+
		"\u0169\2+\3\2\2\2\4\60\3\2\2\2\6=\3\2\2\2\bB\3\2\2\2\nQ\3\2\2\2\fb\3\2"+
		"\2\2\16f\3\2\2\2\20o\3\2\2\2\22\u008a\3\2\2\2\24\u008c\3\2\2\2\26\u0095"+
		"\3\2\2\2\30\u009b\3\2\2\2\32\u00ab\3\2\2\2\34\u00b6\3\2\2\2\36\u00ee\3"+
		"\2\2\2 \u00fd\3\2\2\2\"\u0104\3\2\2\2$\u0137\3\2\2\2&*\5\4\3\2\'*\5\b"+
		"\5\2(*\5\n\6\2)&\3\2\2\2)\'\3\2\2\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3"+
		"\2\2\2,.\3\2\2\2-+\3\2\2\2./\7\2\2\3/\3\3\2\2\2\60\62\5\16\b\2\61\63\5"+
		"\6\4\2\62\61\3\2\2\2\62\63\3\2\2\2\638\3\2\2\2\64\65\7\3\2\2\65\67\5\6"+
		"\4\2\66\64\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2"+
		"\2;<\7\4\2\2<\5\3\2\2\2=@\7\65\2\2>?\7\5\2\2?A\5\34\17\2@>\3\2\2\2@A\3"+
		"\2\2\2A\7\3\2\2\2BC\7\6\2\2CD\7\65\2\2DI\7\7\2\2EH\5\4\3\2FH\5\n\6\2G"+
		"E\3\2\2\2GF\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JL\3\2\2\2KI\3\2\2\2"+
		"LN\7\b\2\2MO\7\4\2\2NM\3\2\2\2NO\3\2\2\2O\t\3\2\2\2PR\5\16\b\2QP\3\2\2"+
		"\2QR\3\2\2\2RS\3\2\2\2ST\7\65\2\2T]\7\t\2\2UZ\5\32\16\2VW\7\3\2\2WY\5"+
		"\32\16\2XV\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2"+
		"]U\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\7\n\2\2`a\5\20\t\2a\13\3\2\2\2bc\t\2"+
		"\2\2c\r\3\2\2\2dg\7\65\2\2eg\5\f\7\2fd\3\2\2\2fe\3\2\2\2gl\3\2\2\2hi\7"+
		"\17\2\2ik\7\20\2\2jh\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2m\17\3\2\2\2"+
		"nl\3\2\2\2os\7\7\2\2pr\5\22\n\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2"+
		"\2tv\3\2\2\2us\3\2\2\2vw\7\b\2\2w\21\3\2\2\2x\u008b\5\20\t\2y\u008b\5"+
		"\4\3\2z\u008b\5\24\13\2{\u008b\5\30\r\2|\u008b\5\26\f\2}~\5\34\17\2~\177"+
		"\7\4\2\2\177\u008b\3\2\2\2\u0080\u0081\7\21\2\2\u0081\u008b\7\4\2\2\u0082"+
		"\u0083\7\22\2\2\u0083\u008b\7\4\2\2\u0084\u0086\7\23\2\2\u0085\u0087\5"+
		"\34\17\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\u008b\7\4\2\2\u0089\u008b\7\4\2\2\u008ax\3\2\2\2\u008ay\3\2\2\2\u008a"+
		"z\3\2\2\2\u008a{\3\2\2\2\u008a|\3\2\2\2\u008a}\3\2\2\2\u008a\u0080\3\2"+
		"\2\2\u008a\u0082\3\2\2\2\u008a\u0084\3\2\2\2\u008a\u0089\3\2\2\2\u008b"+
		"\23\3\2\2\2\u008c\u008d\7\24\2\2\u008d\u008e\7\t\2\2\u008e\u008f\5\34"+
		"\17\2\u008f\u0090\7\n\2\2\u0090\u0093\5\22\n\2\u0091\u0092\7\25\2\2\u0092"+
		"\u0094\5\22\n\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\25\3\2\2"+
		"\2\u0095\u0096\7\26\2\2\u0096\u0097\7\t\2\2\u0097\u0098\5\34\17\2\u0098"+
		"\u0099\7\n\2\2\u0099\u009a\5\22\n\2\u009a\27\3\2\2\2\u009b\u009c\7\27"+
		"\2\2\u009c\u009e\7\t\2\2\u009d\u009f\5\34\17\2\u009e\u009d\3\2\2\2\u009e"+
		"\u009f\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2\7\4\2\2\u00a1\u00a3\5\34"+
		"\17\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4"+
		"\u00a6\7\4\2\2\u00a5\u00a7\5\34\17\2\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3"+
		"\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\7\n\2\2\u00a9\u00aa\5\22\n\2\u00aa"+
		"\31\3\2\2\2\u00ab\u00ac\5\16\b\2\u00ac\u00ad\7\65\2\2\u00ad\33\3\2\2\2"+
		"\u00ae\u00af\b\17\1\2\u00af\u00b7\5 \21\2\u00b0\u00b1\7\31\2\2\u00b1\u00b7"+
		"\5$\23\2\u00b2\u00b3\t\3\2\2\u00b3\u00b7\5\34\17\17\u00b4\u00b5\t\4\2"+
		"\2\u00b5\u00b7\5\34\17\16\u00b6\u00ae\3\2\2\2\u00b6\u00b0\3\2\2\2\u00b6"+
		"\u00b2\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00eb\3\2\2\2\u00b8\u00b9\f\r"+
		"\2\2\u00b9\u00ba\t\5\2\2\u00ba\u00ea\5\34\17\16\u00bb\u00bc\f\f\2\2\u00bc"+
		"\u00bd\t\6\2\2\u00bd\u00ea\5\34\17\r\u00be\u00bf\f\13\2\2\u00bf\u00c0"+
		"\t\7\2\2\u00c0\u00ea\5\34\17\f\u00c1\u00c2\f\n\2\2\u00c2\u00c3\t\b\2\2"+
		"\u00c3\u00ea\5\34\17\13\u00c4\u00c5\f\t\2\2\u00c5\u00c6\t\t\2\2\u00c6"+
		"\u00ea\5\34\17\n\u00c7\u00c8\f\b\2\2\u00c8\u00c9\7+\2\2\u00c9\u00ea\5"+
		"\34\17\t\u00ca\u00cb\f\7\2\2\u00cb\u00cc\7,\2\2\u00cc\u00ea\5\34\17\b"+
		"\u00cd\u00ce\f\6\2\2\u00ce\u00cf\7-\2\2\u00cf\u00ea\5\34\17\7\u00d0\u00d1"+
		"\f\5\2\2\u00d1\u00d2\7.\2\2\u00d2\u00ea\5\34\17\6\u00d3\u00d4\f\4\2\2"+
		"\u00d4\u00d5\7/\2\2\u00d5\u00ea\5\34\17\5\u00d6\u00d7\f\3\2\2\u00d7\u00d8"+
		"\7\5\2\2\u00d8\u00ea\5\34\17\3\u00d9\u00da\f\24\2\2\u00da\u00db\7\30\2"+
		"\2\u00db\u00ea\7\65\2\2\u00dc\u00dd\f\22\2\2\u00dd\u00de\7\17\2\2\u00de"+
		"\u00df\5\34\17\2\u00df\u00e0\7\20\2\2\u00e0\u00ea\3\2\2\2\u00e1\u00e2"+
		"\f\21\2\2\u00e2\u00e4\7\t\2\2\u00e3\u00e5\5\36\20\2\u00e4\u00e3\3\2\2"+
		"\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00ea\7\n\2\2\u00e7\u00e8"+
		"\f\20\2\2\u00e8\u00ea\t\n\2\2\u00e9\u00b8\3\2\2\2\u00e9\u00bb\3\2\2\2"+
		"\u00e9\u00be\3\2\2\2\u00e9\u00c1\3\2\2\2\u00e9\u00c4\3\2\2\2\u00e9\u00c7"+
		"\3\2\2\2\u00e9\u00ca\3\2\2\2\u00e9\u00cd\3\2\2\2\u00e9\u00d0\3\2\2\2\u00e9"+
		"\u00d3\3\2\2\2\u00e9\u00d6\3\2\2\2\u00e9\u00d9\3\2\2\2\u00e9\u00dc\3\2"+
		"\2\2\u00e9\u00e1\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb"+
		"\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\35\3\2\2\2\u00ed\u00eb\3\2\2"+
		"\2\u00ee\u00f3\5\34\17\2\u00ef\u00f0\7\3\2\2\u00f0\u00f2\5\34\17\2\u00f1"+
		"\u00ef\3\2\2\2\u00f2\u00f5\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2"+
		"\2\2\u00f4\37\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6\u00f7\7\t\2\2\u00f7\u00f8"+
		"\5\34\17\2\u00f8\u00f9\7\n\2\2\u00f9\u00fe\3\2\2\2\u00fa\u00fe\7\60\2"+
		"\2\u00fb\u00fe\7\65\2\2\u00fc\u00fe\5\"\22\2\u00fd\u00f6\3\2\2\2\u00fd"+
		"\u00fa\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe!\3\2\2\2"+
		"\u00ff\u0105\7\66\2\2\u0100\u0105\7\64\2\2\u0101\u0105\7\61\2\2\u0102"+
		"\u0105\7\62\2\2\u0103\u0105\7\63\2\2\u0104\u00ff\3\2\2\2\u0104\u0100\3"+
		"\2\2\2\u0104\u0101\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0103\3\2\2\2\u0105"+
		"#\3\2\2\2\u0106\u0109\7\65\2\2\u0107\u0109\5\f\7\2\u0108\u0106\3\2\2\2"+
		"\u0108\u0107\3\2\2\2\u0109\u010e\3\2\2\2\u010a\u010b\7\17\2\2\u010b\u010c"+
		"\5\34\17\2\u010c\u010d\7\20\2\2\u010d\u010f\3\2\2\2\u010e\u010a\3\2\2"+
		"\2\u010f\u0110\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0114"+
		"\3\2\2\2\u0112\u0113\7\17\2\2\u0113\u0115\7\20\2\2\u0114\u0112\3\2\2\2"+
		"\u0115\u0116\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u011c"+
		"\3\2\2\2\u0118\u0119\7\17\2\2\u0119\u011a\5\34\17\2\u011a\u011b\7\20\2"+
		"\2\u011b\u011d\3\2\2\2\u011c\u0118\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011c"+
		"\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0138\3\2\2\2\u0120\u0123\7\65\2\2"+
		"\u0121\u0123\5\f\7\2\u0122\u0120\3\2\2\2\u0122\u0121\3\2\2\2\u0123\u0128"+
		"\3\2\2\2\u0124\u0125\7\17\2\2\u0125\u0126\5\34\17\2\u0126\u0127\7\20\2"+
		"\2\u0127\u0129\3\2\2\2\u0128\u0124\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u0128"+
		"\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u0130\3\2\2\2\u012c\u012d\7\17\2\2"+
		"\u012d\u012f\7\20\2\2\u012e\u012c\3\2\2\2\u012f\u0132\3\2\2\2\u0130\u012e"+
		"\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0138\3\2\2\2\u0132\u0130\3\2\2\2\u0133"+
		"\u0134\7\65\2\2\u0134\u0135\7\t\2\2\u0135\u0138\7\n\2\2\u0136\u0138\7"+
		"\65\2\2\u0137\u0108\3\2\2\2\u0137\u0122\3\2\2\2\u0137\u0133\3\2\2\2\u0137"+
		"\u0136\3\2\2\2\u0138%\3\2\2\2%)+\628@GINQZ]fls\u0086\u008a\u0093\u009e"+
		"\u00a2\u00a6\u00b6\u00e4\u00e9\u00eb\u00f3\u00fd\u0104\u0108\u0110\u0116"+
		"\u011e\u0122\u012a\u0130\u0137";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}