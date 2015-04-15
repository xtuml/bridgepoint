// $ANTLR 2.7.2: "antlr.g" -> "ANTLRTreeWalker.java"$

package antlr;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class ANTLRTreeWalker extends antlr.TreeParser       implements ANTLRTokenTypes
 {
public ANTLRTreeWalker() {
	tokenNames = _tokenNames;
}

	public final void rule(AST _t) throws RecognitionException {
		
		AST rule_AST_in = (AST)_t;
		AST r = null;
		
		try {      // for error handling
			AST __t226 = _t;
			r = _t==ASTNULL ? null :(AST)_t;
			match(_t,RULE_REF);
			_t = _t.getFirstChild();
			
			CommonAST t = (CommonAST)rule_AST_in;
			System.out.println("found RULE_REF "+r.getText());
			System.out.println("toSTring: "+r.toString());
			
			AST tmp1_AST_in = (AST)_t;
			match(_t,COLON);
			_t = _t.getNextSibling();
			_t = __t226;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"tokens\"",
		"\"Grammar\"",
		"\"Rule\"",
		"\"Id\"",
		"\"Block\"",
		"\"Alternative\"",
		"\"Element\"",
		"\"Range\"",
		"\"Terminal\"",
		"\"Ebnf\"",
		"\"header\"",
		"STRING_LITERAL",
		"ACTION",
		"DOC_COMMENT",
		"\"lexclass\"",
		"\"class\"",
		"\"extends\"",
		"\"Lexer\"",
		"\"TreeParser\"",
		"OPTIONS",
		"ASSIGN",
		"SEMI",
		"RCURLY",
		"\"charVocabulary\"",
		"CHAR_LITERAL",
		"INT",
		"OR",
		"RANGE",
		"TOKENS",
		"TOKEN_REF",
		"OPEN_ELEMENT_OPTION",
		"CLOSE_ELEMENT_OPTION",
		"LPAREN",
		"RULE_REF",
		"RPAREN",
		"\"Parser\"",
		"\"protected\"",
		"\"public\"",
		"\"private\"",
		"BANG",
		"ARG_ACTION",
		"\"returns\"",
		"COLON",
		"\"throws\"",
		"COMMA",
		"\"exception\"",
		"\"catch\"",
		"NOT_OP",
		"SEMPRED",
		"TREE_BEGIN",
		"QUESTION",
		"STAR",
		"PLUS",
		"IMPLIES",
		"CARET",
		"WILDCARD",
		"\"options\"",
		"WS",
		"COMMENT",
		"SL_COMMENT",
		"ML_COMMENT",
		"ESC",
		"DIGIT",
		"XDIGIT",
		"NESTED_ARG_ACTION",
		"NESTED_ACTION",
		"WS_LOOP",
		"INTERNAL_RULE_REF",
		"WS_OPT"
	};
	
	}
	
