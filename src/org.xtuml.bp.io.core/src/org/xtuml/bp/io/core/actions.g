//====================================================================
//
// File:      $RCSfile: actions.g,v $
//
//====================================================================
header 
{
	package org.xtuml.bp.io.core;
}
//---------------------------------------------------------------------
// Parser class is defined here.
//---------------------------------------------------------------------
{
	import org.xtuml.bp.core.Ooaofooa;
	import org.eclipse.core.runtime.IProgressMonitor;
}
class ActionParser extends Parser;
options {
	exportVocab=Action;
    k=2;
}
{
	public ActionParser(ActionLexer lexer, CoreImport ci){
		this(lexer, 2);
		m_ci = ci;		
	}
	
	private CoreImport m_ci = null;

	public void reportError(RecognitionException arg0) {
		m_output += arg0.toString() + "\n";
	}
	public String m_output = "";
}
actions [ IProgressMonitor pm ]
    :
    (
      action[pm]
    )+
    EOF
    ;
action[ IProgressMonitor pm ]
    :
    { String name = "";
      String key_lett = "";
      String id1 = "";
      String id2 = "";
      String body = ""; }
    TOK_LBRACK
    name = text
    TOK_COLCOL
    key_lett = text
    TOK_COLCOL
    id1 = text
    ( TOK_DASH
    id2 = text )?
    TOK_RBRACK
    body = text
      {
      	m_ci.processAction( name.trim(), key_lett.trim(), id1.trim(), id2.trim(), body.trim(), pm );
      }
    ;
text returns [String s]
    { s = ""; }
    : ( str:TOK_CHAR { s = s + str.getText(); } )*
    ;
class ActionLexer extends Lexer;
options {
	exportVocab=Action;
    caseSensitiveLiterals=false;
    caseSensitive=false;
    testLiterals=true;
    k=2;
    charVocabulary = '\u0000'..'\ufffe';
}
TOK_LBRACK : "{{";
TOK_RBRACK : "}}";
TOK_COLCOL : "%%";
TOK_DASH   : "--";
TOK_CHAR   : .;
