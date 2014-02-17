//====================================================================
//
// File:      $RCSfile: oal_lex.g,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2013/01/10 23:43:47 $
//
// (c) Copyright 2003-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
header {
   package com.mentor.nucleus.bp.als.oal;
}
class OalLexer extends Lexer;
options {
	importVocab=Oal;
    caseSensitiveLiterals=false;
    caseSensitive=false;
    testLiterals=false;
    k=3;
    charVocabulary = '\u0000'..'\ufffe';
}
{
	// override method so that tabs are one character (default is 8)
    public void tab() {
      setColumn( getColumn() + 1 );
    }
}
WS
  : (WS1 | WS2)+
	{ $setType(Token.SKIP); }
  ;
protected
WS1
  : ( ' ' | '\t' )
  ;
protected
WS2
  :
  ( ("\r\n")=>"\r\n"
  | '\n'
  | '\r'
  ) {newline();}
  ;
//---------------------------------------------------------------------
// operators
//---------------------------------------------------------------------
TOK_ARROW       : "->";
TOK_COMMA       : ',';
TOK_COLON       : ':';
TOK_DIV         : '/';
TOK_DOUBLECOLON : "::";
TOK_DOUBLEEQUAL : "==";
TOK_EQUAL       : '=';
TOK_GE          : ">=";
TOK_GT          : '>';
TOK_LE          : "<=";
TOK_LESSTHAN    : '<';
TOK_LPAREN      : '(';
TOK_LSQBR       : '[';
TOK_MINUS       : '-';
TOK_MOD         : '%';
TOK_NOTEQUAL    : "!=";
TOK_PLUS        : '+';
TOK_RPAREN      : ')';
TOK_RSQBR       : ']';
Semicolon       : ';';
TOK_TIMES       : '*';
TOK_QMARK       : '?';


protected
DIGIT : '0'..'9' ;
protected
ID_START_LETTER
    :    'a'..'z'
    |    '_'
    |    '@'
    |    '\u0080'..'\ufffe'
    ;
protected
ID_LETTER
    :    ID_START_LETTER
    |    DIGIT
    ;
    
protected
NON_ID_LETTER : 
  // It would be nice if this could just be ~ID_LETTER,
  // but that causes a syntax error, as does 
  // ~(ID_START_LETTER | DIGIT )
   ~('a'..'z' | '@'  |'\u0080'..'\ufffe' | '_' | '0'..'9'  ) ;

protected
GENERAL_NAME_MORE_START_LETTERS
    :    '#'
    ;
protected
GENERAL_NAME_MORE_LETTERS
    :    '#'
    ;

protected
EXPONENT : 'e' ('+'|'-')? (DIGIT)+ ;
protected
FLOAT_SUFFIX : 'f'|'l' ;
protected
FLOAT :
      ((DIGIT)+ '.' (DIGIT)*)
      | ( '.' (DIGIT)+ )
      ;

TOK_UNDEFINED
    options {testLiterals=true;}
    :
      ( FLOAT )=>
      ( FLOAT (EXPONENT)? (FLOAT_SUFFIX)? )
        { $setType(TOK_FRACTION); }
    | '.'
        { $setType(TOK_DOT); }
    |
      ( 'r' (DIGIT)+ NON_ID_LETTER )=>('r' (DIGIT)+)
        {$setType(TOK_RELID);}
    | 
      ( (DIGIT)+ (ID_START_LETTER | GENERAL_NAME_MORE_START_LETTERS) )=>
      (DIGIT)+
          {$setType(TOK_NUMBER);}
    | {$setType(TOK_ID);}
      ( ID_START_LETTER 
      | GENERAL_NAME_MORE_START_LETTERS
          { $setType(TOK_GENERAL_NAME);}
      )
      ( ID_LETTER
      | GENERAL_NAME_MORE_LETTERS
          { $setType(TOK_GENERAL_NAME);}
      )*
    | ( (DIGIT)+ )
        { $setType(TOK_NUMBER); }
    ;
TOK_STRING
    :
      '"'
    ( ~('\r'|'\n'|'"')
    )*
      '"'
    ;
TICKED_PHRASE
    :
      '\''
    ( ~('\r'|'\n'|'\'')
    )*
      '\''
    ;
ML_COMMENT
  	:	"/*"
  		(	/*	'\r' '\n' can be matched in one alternative or by matching
    				'\r' in one iteration and '\n' in another.  I am trying to
    				handle any flavor of newline that comes in, but the language
    				that allows both "\r\n" and "\r" and "\n" to all be valid
    				newline is ambiguous.  Consequently, the resulting grammar
				    must be ambiguous.  I'm shutting this warning off.
   			 */
  			  options {
      				generateAmbigWarnings=false;
    			}
    		:
    			{ LA(2)!='/' }? '*'
      		|	'\r' '\n'		{newline();}
      		|	'\r'			{newline();}
      		|	'\n'			{newline();}
      		|	~('*'|'\n'|'\r')
  		)*
		"*/"
		{$setType(Token.SKIP);}
	;


SL_STRING
  :
    "//"
  ( ~('\r'|'\n')
  )*
    { $setType(Token.SKIP); }
  ;
