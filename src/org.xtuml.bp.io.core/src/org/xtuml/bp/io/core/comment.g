// comment parser
header 
{
    package org.xtuml.bp.io.core;

    import antlr.TokenStreamSelector;
}
class CommentParser extends Parser;
options {
    k=2;
}
{
    public void reportError(RecognitionException arg0) {
	    m_output += arg0.toString() + "\n";
            System.out.println(m_output);
    }
    public String m_output = "";
}

singleLineComment returns [String s = ""]:
            { String com = ""; }
            com=singleLineCommentBody
            "\n"
            { s = com + "\n"; }
            ;

singleLineCommentBody returns [String s = ""]:
            { String com = ""; }
            ( ch:CHAR com=singleLineCommentBody     { s += ch.getText() + com; }
            | "*" com=singleLineCommentBody         { s += "*" + com; }
            | "/" com=singleLineCommentBody         { s += "/" + com; }
            | /* empty */                           { s = ""; }
            )
            ;

blockComment returns [String s = ""]:
            { String com = ""; }
            com=blockCommentBody
            "*" "/"
            { s = com + "*/"; }
            ;

blockCommentBody returns [String s = ""]:
            { String com = ""; }
            ( ch:CHAR com=blockCommentBody          { s += ch.getText() + com; }
            | "*" com=blockCommentBodyStar          { s += "*" + com; }
            | "/" com=blockCommentBody              { s += "/" + com; }
            | "\n" com=blockCommentBody             { s += "\n" + com; }
            | /* empty */                           { s = ""; }
            )
            ;

blockCommentBodyStar returns [String s = ""]:
            { String com = ""; }
            ( ch:CHAR com=blockCommentBody          { s += ch.getText() + com; }
            | "*" com=blockCommentBodyStar          { s += "*" + com; }
            | "\n" com=blockCommentBody             { s += "\n" + com; }
            | /* empty */                           { s = ""; }
            )
            ;

class CommentLexer extends Lexer;
options {
    k=1;
}
{
    public void reportError(RecognitionException arg0) {
	    m_output += arg0.toString() + "\n";
            System.out.println(m_output);
    }
    public String m_output = "";
}

CHAR        : ~( '\n' );
NL          : '\n' { newline(); };

