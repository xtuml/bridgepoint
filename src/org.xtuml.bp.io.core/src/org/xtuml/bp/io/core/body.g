// body parser
header 
{
    package org.xtuml.bp.io.core;

    import antlr.TokenStreamSelector;
    import antlr.ParserSharedInputState;
}
class BodyParser extends Parser;
options {
    k=1;
}
{
    public BodyParser( TokenStream ts, CoreImport ci ) {
        this(ts);
        m_ci = ci;
    }

    public void reportError(RecognitionException arg0) {
        m_errors = true;
	m_output += "BodyParser: " + (( m_ci != null && m_ci.m_actionFile != null) ? m_ci.m_actionFile.getName() + " " : "") + arg0.toString() + "\n";
    }

    public String m_output = "";
    public boolean m_errors = false;
    public CoreImport m_ci = null;
}

codeBlock returns [String s = ""]:
            "\n"
            ( ln1:LINE1 { s += ln1.getText(); }
            | ln2:LINE2 { s += ln2.getText(); }
            | ln3:LINE3 { s += ln3.getText(); }
            | ln4:LINE4 { s += ln4.getText(); }
            | ln5:LINE5 { s += ln5.getText(); }
            | ln6:LINE6 { s += ln6.getText(); }
            | "\n"      { s += "\n"; }
            )*
            ACTIVITYEND
            ;

class BodyLexer extends Lexer;
options {
    k=3;
}
{
    public BodyLexer( LexerSharedInputState is, CoreImport ci ) {
        this(is);
        m_ci = ci;
    }

    public void reportError(RecognitionException arg0) {
        m_errors = true;
	m_output += "BodyLexer: " + (( m_ci != null && m_ci.m_actionFile != null) ? m_ci.m_actionFile.getName() + " " : "") + arg0.toString() + "\n";
    }

    public String m_output = "";
    public boolean m_errors = false;

    private CoreImport m_ci = null;
}

ACTIVITYEND : "//! ACTIVITY END. DO NOT EDIT THIS LINE." ('\r')? '\n' { newline(); };

LINE1       : '\n'{newline();};
LINE2       : ~('/'|'\n') (~('\n'))* ('\n'{newline();})?;
LINE3       : '/' ('\n'{newline();})?;
LINE4       : '/' ~('/'|'\n') (~('\n'))* ('\n'{newline();})?;
LINE5       : "//" ('\n'{newline();})?;
LINE6       : "//" ~('!'|'\n') (~('\n'))* ('\n'{newline();})?;

