// body parser
header 
{
    package org.xtuml.bp.io.core;

    import antlr.TokenStreamSelector;
    import antlr.ParserSharedInputState;
}
class BodyParser extends Parser;
options {
    k=3;
}
{
    private TokenStreamSelector selector;

    public BodyParser(ParserSharedInputState is, TokenStreamSelector s) {
        this(is);
        selector = s;
    }

    public void reportError(RecognitionException arg0) {
	    m_output += arg0.toString() + "\n";
            System.out.println(m_output);
    }
    public String m_output = "";
}

attributeCodeBlock returns [String s = ""]:
            { String cb = ""; }
            cb=codeBlock
            "attribute" ";" { s = cb.trim(); }
            ;

serviceCodeBlock returns [String s = ""]:
            { String cb = ""; }
            cb=codeBlock
            "service" ";" { s = cb.trim(); }
            ;

stateCodeBlock returns [String s = ""]:
            { String cb = ""; }
            cb=codeBlock
            "state" ";" { s = cb.trim(); }
            ;

transitionCodeBlock returns [String s = ""]:
            { String cb = ""; }
            cb=codeBlock
            "transition" ";" { s = cb.trim(); }
            ;

codeBlock returns [String s = ""]:
            { String ns = "";String kw = ""; }
            ( ns=nonSemi                    { s += ns; }
            | ";"                           { s += ";"; }
            | kw=keyword ns=nonSemi         { s += kw + ns; }
            )+
            ( kw=keyword                    { s += kw; }
            )?
            | /* empty */
            ;

keyword returns [String s = ""]:
            "attribute"                     { s = "attribute"; }
            | "service"                     { s = "service"; }
            | "state"                       { s = "state"; }
            | "transition"                  { s = "transition"; }
            ;

nonSemi returns [String s = ""]:
            { String com = ""; }
            (wd:WORD                         { s = wd.getText(); }
            | sym:SYMBOL                    { s = sym.getText(); }
            | ws:WS                         { s = ws.getText(); }
            | com=comment                   { s = com; }
            )
            ;

comment returns [String s = ""]:
            ( "//"
            {
                selector.push("commentlexer");
                CommentParser commentparser = new CommentParser(selector);
                s = "//" + commentparser.singleLineComment();
                selector.pop();
            }
            | "/*"
            {
                selector.push("commentlexer");
                CommentParser commentparser = new CommentParser(selector);
                s = "/*" + commentparser.blockComment();
                selector.pop();
            } )
            ;

class BodyLexer extends Lexer;
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

SYMBOL      : (~( ' ' | '\t' | '\f' | '\n' | '\r' | 'A'..'Z' | 'a'..'z' ))+;
WORD        : ( 'A'..'Z' | 'a'..'z' )+;
WS          : ( ' ' | '\t' | '\f' | '\n'{newline();} | '\r' )+;

