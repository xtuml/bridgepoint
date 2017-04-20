package org.xtuml.bp.xtext.masl.tests.parser

import com.google.inject.Inject
import org.antlr.runtime.ANTLRStringStream
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.parser.antlr.AntlrTokenDefProvider
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.antlr.runtime.Token
import org.xtuml.bp.xtext.masl.parser.antlr.lexer.jflex.JFlexBasedInternalMASLLexer
import org.xtuml.bp.xtext.masl.tests.MASLInjectorProvider
import com.google.inject.Provider

@RunWith(XtextRunner)
@InjectWith(MASLInjectorProvider)
class LexerTest {
	
	@Inject Provider<JFlexBasedInternalMASLLexer> lexerProvider
	@Inject AntlrTokenDefProvider tokenDefProvider
	
	@Test
	def void testID() {
		"f100".assertTokenTypes('RULE_ID')
		"f_oo".assertTokenTypes('RULE_ID')
		"_foo".assertTokenTypes('RULE_ID')
	}
	
	@Test
	def void testREAL() {
		"0.1".assertTokenTypes('RULE_REAL')
		".0".assertTokenTypes('RULE_REAL')
		"0e1".assertTokenTypes('RULE_REAL')
		"0e-1".assertTokenTypes('RULE_REAL')
		"0e+1".assertTokenTypes('RULE_REAL')
		".0e0".assertTokenTypes('RULE_REAL')
		"0.0e0".assertTokenTypes('RULE_REAL')
		
		"36#z0.Z0".assertTokenTypes('RULE_REAL')
		"36#ab.0".assertTokenTypes('RULE_REAL')
		"36#.x".assertTokenTypes('RULE_REAL')
		"36#y#1".assertTokenTypes('RULE_REAL')
		"36#fsd#-1".assertTokenTypes('RULE_REAL')
		"36#0#+1".assertTokenTypes('RULE_REAL')
		"36#.0#0".assertTokenTypes('RULE_REAL')
		"36#0.0#0".assertTokenTypes('RULE_REAL')
		
	}
	
	@Test
	def void testINT() {
		"0".assertTokenTypes('RULE_INTEGER')
		"01".assertTokenTypes('RULE_INTEGER')
		"2#11111".assertTokenTypes('RULE_INTEGER')
		"16#abcde".assertTokenTypes('RULE_INTEGER')
		"36#Zz00aA".assertTokenTypes('RULE_INTEGER')
	}
	
	@Test
	def void testTIMESTAMP() {
		"@sadlkdlaskdl@".assertTokenTypes('RULE_TIMESTAMP')
		"@sadlkdlaskdl".assertTokenTypes('RULE_TIMESTAMP')
	}
	
	@Test
	def void testDURATION() {
		"@Pasgdjhas89@".assertTokenTypes('RULE_DURATION')
		"@Pasgdjhas89".assertTokenTypes('RULE_DURATION')
	}

	@Test
	def void testSTRING() {
		'""'.assertTokenTypes('RULE_STRING')
		'"foo"'.assertTokenTypes('RULE_STRING')
		'"0"'.assertTokenTypes('RULE_STRING')
		'"@asgdjhas89@"'.assertTokenTypes('RULE_STRING')
		'"@Pasgdjhas89@"'.assertTokenTypes('RULE_STRING')
		'"ksdjdslkdjslk'.assertTokenTypes('RULE_STRING')
		'"\\n\\b\\f"'.assertTokenTypes('RULE_STRING')
		'"\\u34289\\777"'.assertTokenTypes('RULE_STRING')
	}

	@Test
	def void testCHAR() {
		"'s'".assertTokenTypes('RULE_CHAR')
	}
	@Test
	def void testCHAR1() {
		"'\\701'".assertTokenTypes('RULE_CHAR')
	}
	@Test
	def void testCHAR2() {
		"'\\u786'".assertTokenTypes('RULE_CHAR')
	}
	@Test
	def void testCHAR3() {
		"'\\n'".assertTokenTypes('RULE_CHAR')
	}

	@Test
	def void testIntRange() {
		'1..2'.assertTokenTypes('RULE_INTEGER', "'..'", 'RULE_INTEGER')
	}

	protected def assertTokenTypes(CharSequence model, String... tokenTypes) {
		val lexer = lexerProvider.get
		lexer.charStream = new ANTLRStringStream(model.toString)		
		for(tokenType: tokenTypes) {
			val token = lexer.nextToken
			assertNotEquals(Token.EOF_TOKEN, token)
			assertEquals(model.tokens.map[tokenDefProvider.tokenDefMap.get(type)].join(', '), tokenType, tokenDefProvider.tokenDefMap.get(token.type))
		}
		val token = lexer.nextToken
		assertEquals(tokenDefProvider.tokenDefMap.get(token.type), Token.EOF_TOKEN, token)
	} 
	
	protected def getTokens(CharSequence model) {
		val lexer = lexerProvider.get
		lexer.charStream = new ANTLRStringStream(model.toString)
		val tokens = newArrayList
		for(;;) {
			val token = lexer.nextToken
			if(token == Token.EOF_TOKEN)
				return tokens
			tokens += token				
		}
	}
}