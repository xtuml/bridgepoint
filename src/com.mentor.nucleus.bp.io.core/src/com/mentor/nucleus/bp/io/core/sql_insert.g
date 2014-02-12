//====================================================================
//
// File:      $RCSfile: sql_insert.g,v $
// Version:   $Revision: 1.15.20.1 $
// Modified:  $Date: 2013/07/09 21:33:15 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
header 
{
	package com.mentor.nucleus.bp.io.core;
}
//---------------------------------------------------------------------
// Parser class is defined here.
//---------------------------------------------------------------------
{
	import java.util.Vector;
	import com.mentor.nucleus.bp.core.Ooaofooa;
	import org.eclipse.core.runtime.IProgressMonitor;
}
class SqlParser extends Parser;
options {
	exportVocab=Sql;
    k=2;
}
{
	Ooaofooa modelRoot;
	public SqlParser(SqlLexer lexer, Ooaofooa aModelRoot, CoreImport ci){
		this(lexer, 2);
		modelRoot = aModelRoot;
		m_ci = ci;		
	}
	
	private CoreImport m_ci = null;

	public void reportError(RecognitionException arg0) {
		m_output += arg0.toString() + "\n";
	}
	public String m_output = "";
}
sql_file [ IProgressMonitor pm ]
    :
    (
      insert_statement[pm]
    )+
    EOF
    ;
insert_statement[ IProgressMonitor pm ]
    :
      { String table;
        String val;
        Vector val_list = new Vector(20);
		Vector rawValues = new Vector(20);
        int col_num = 0;
      }
    "insert"
    "into"
    table = table_name
    "values"
    TOK_LPAREN
    val = data_value
        { val_list.insertElementAt(m_ci.processValue( table, col_num, val ), col_num); 
        	rawValues.insertElementAt(val, col_num);
          col_num += 1;
        }
    (
      TOK_COMMA
      val = data_value
        { val_list.insertElementAt(m_ci.processValue( table, col_num, val ), col_num); 
			rawValues.insertElementAt(val, col_num);
		  col_num += 1;
        }
    )*
    TOK_RPAREN
    Semicolon
      {
      	m_ci.processStatement( modelRoot, table, val_list, rawValues, col_num, pm );
      }
    ;
table_name returns [String s]
    { s = ""; }
    :
    (
        id    :TOK_ID   { s = id.getText(); }
    )
    ;
data_value returns [String s]
    { s = ""; }
    :
      string:TOK_STRING
        { s = string.getText(); }
    | value:TOK_NUMBER
        { s = value.getText(); }
    | uuid:TOK_UUID
        { s = uuid.getText(); }
    ;
class SqlLexer extends Lexer;
options {
	exportVocab=Sql;
    caseSensitiveLiterals=false;
    caseSensitive=false;
    testLiterals=true;
    k=2;
    charVocabulary = '\u0000'..'\ufffe';
}
TOK_ID
  :
  ( 'a'..'z'
  | '_'
  )+
  ;
TOK_NUMBER
  :
  ('-')?
  ( '0'..'9'
  | '.'
  )+
  ;
TOK_STRING
  :
  '\''
  (
     "''"
   | '\r'!
   | '\n' { newline(); }
   | ~('\r' | '\'' | '\n')
  )*
  '\''
  ;
TOK_UUID
  :
  '"'!
  ( '0'..'9'
  | 'a'..'f'
  | '-'
  )*
  '"'!
  ;   
SL_COMMENT
  :
  "--"
  (~'\n')*
  ('\n' { newline(); } )
    { _ttype = Token.SKIP; }
  ; 
PATCH_CONFLICT : "<<<<<<<" ( options {greedy=false;} : . )*  ( '\n' { newline(); }) { _ttype = Token.SKIP; };
PATCH_CONFLICT_BACK_HALF : "=======" ( options {greedy=false;} : . )* ">>>>>>>"  ( options {greedy=false;} : . )* ( '\n' { newline(); }) { _ttype = Token.SKIP; };

WS
  : (WS1 | WS2)+
	{ _ttype = Token.SKIP; }
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
TOK_COMMA       : ',';
TOK_LPAREN      : '(';
TOK_RPAREN      : ')';
Semicolon       : ';';

