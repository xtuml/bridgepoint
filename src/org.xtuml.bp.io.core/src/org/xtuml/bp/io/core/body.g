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
    public void reportError(RecognitionException arg0) {
        m_errors = true;
	m_output += "BodyParser: " + arg0.toString() + "\n";
    }
    public String m_output = "";
    public boolean m_errors = false;
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
    public void reportError(RecognitionException arg0) {
        m_errors = true;
	m_output += "BodyLexer: " + arg0.toString() + "\n";
    }
    public String m_output = "";
    public boolean m_errors = false;
}

LINE        : '\n' | ( (~( '\n' ))+ ('\n'{newline();})? );

