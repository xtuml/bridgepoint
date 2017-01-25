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

attributeCodeBlock returns [String s = ""]:
            s=codeBlock
            ( "end attribute;\n"
            | "end attribute;"
            )
            ;

serviceCodeBlock returns [String s = ""]:
            s=codeBlock
            ( "end service;\n"
            | "end service;"
            )
            ;

stateCodeBlock returns [String s = ""]:
            s=codeBlock
            ( "end state;\n"
            | "end state;"
            )
            ;

transitionCodeBlock returns [String s = ""]:
            s=codeBlock
            ( "end transition;\n"
            | "end transition;"
            )
            ;

codeBlock returns [String s = ""]:
            "\n"
            ( ln:LINE   { s += ln.getText(); }
            | "\n"      { s += "\n"; }
            )*
            ;

class BodyLexer extends Lexer;
options {
    k=1;
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

LINE        : '\n'{newline();} | ( (~( '\n' ))+ ('\n'{newline();})? );

