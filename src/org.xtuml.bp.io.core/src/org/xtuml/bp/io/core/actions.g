// activity parser
header 
{
    package org.xtuml.bp.io.core;

    import antlr.TokenStreamSelector;
    import org.xtuml.bp.core.common.IdAssigner;
    import org.xtuml.bp.core.Url_c;
    import java.util.UUID;
}
//---------------------------------------------------------------------
// Parser class is defined here.
//---------------------------------------------------------------------

class ActionParser extends Parser;
options {
    k=2;
}
{
    private String smasl = "";
    private UUID uuid = IdAssigner.NULL_UUID;
    private UUID uuid2 = IdAssigner.NULL_UUID;
    private int m_dialect = -1;
    private TokenStreamSelector selector;
    private CoreImport m_ci;

    private int startLine = 0;

    public ActionParser(TokenStreamSelector s, CoreImport ci, int dialect) {
        this((TokenStream)s);
        selector = s;
        m_ci = ci;
        m_dialect = dialect;

    }

    private void populate( String element, String[] values ) {
        // check arguments
        if ( null == element || 8 != values.length ) return;
        for ( int i = 0; i < values.length; i++ ) {
            if ( null == values[i] ) return;
        }

        smasl += element;
        for ( int i = 0; i < values.length; i++ ) {
            smasl += "," + Url_c.Encode( values[i] );
        }
        smasl += "\n";

        clearValues( values );
    }

    private void clearValues( String[] values ) {
        if ( null == values ) return;
        for ( int i = 0; i < values.length; i++ ) values[i] = "";
    }

    private void sendSmasl() {
        m_ci.processAction( smasl, m_dialect, startLine, uuid, uuid2 );
        smasl = "";
    }

    private void setID( String uuidText ) {
        uuid = IdAssigner.createUUIDFromString( uuidText.replaceAll( "'", "" ) );
    }

    private void setID2( String uuidText ) {
        uuid2 = IdAssigner.createUUIDFromString( uuidText.replaceAll( "'", "" ) );
    }

    public void reportError(RecognitionException arg0) {
        m_errors = true;
	m_output += "ActionParser: " + (( m_ci != null && m_ci.m_actionFile != null) ? m_ci.m_actionFile.getName() + " " : "") + arg0.toString() + "\n";
    }
    public String m_output = "";
    public boolean m_errors = false;
}

activityDefinitions:
            (activityDefinition)+
            EOF
            ;

activityDefinition:     
            ACTIVITYBEGIN1 id:UUID { setID( id.getText() ); } ( id2:UUID { setID2( id2.getText() ); } )? ACTIVITYBEGIN2
            ( serviceDefinition 
            | stateDefinition
            | attributeDefinition
            | transitionDefinition )
            ;

serviceDefinition:
            {
                String element = "routine";
                String[] values = new String[8];
                clearValues(values);
                String val = "";
            }
            val=serviceVisibility { values[2]=val; }
            ("instance" {values[4]="instance";} )?
            kw:"service" { startLine = kw.getLine(); }
            (val=domainName {values[0]=val; } )? "::" 
            ( val=terminatorName "~>" { values[1]=val; }
            | val=objectName "." { values[1]=val; element="operation"; } )?
            val=serviceName { values[3]=val; }
            { populate( element, values ); }
            parameterList
            ( "return" val=returnType { values[0]=val; populate( "typeref", values ); })? "is"
            {
                selector.push("bodylexer");
                BodyParser bodyparser = new BodyParser(selector, m_ci);
                values[0] = bodyparser.codeBlock();
                if ( bodyparser.m_errors ) {
                    m_errors = true;
                    m_output += bodyparser.m_output;
                }
                populate( "codeblock", values );
                selector.pop();
                populate( element, values );
                sendSmasl();
            }
            ;

stateDefinition:
            {
                String[] values = new String[8];
                clearValues(values);
                String val = "";
            }
            val=stateType {values[3]=val;}
            kw:"state" { startLine = kw.getLine(); }
            (val=domainName {values[0]=val;} )? "::"
            val=objectName "." {values[1]=val;}
            val=stateName {values[2]=val;}
            { populate( "state", values ); }
            parameterList "is"
            {
                selector.push("bodylexer");
                BodyParser bodyparser = new BodyParser(selector, m_ci);
                values[0] = bodyparser.codeBlock();
                if ( bodyparser.m_errors ) {
                    m_errors = true;
                    m_output += bodyparser.m_output;
                }
                populate( "codeblock", values );
                selector.pop();
                populate( "state", values );
                sendSmasl();
            }
            ;

attributeDefinition:
            {
                String[] values = new String[8];
                clearValues(values);
                String val = "";
            }
            kw:"attribute" { startLine = kw.getLine(); }
            (val=domainName {values[3]=val;})? "::"
            val=objectName "." {values[4]=val;}
            val=attributeName {values[0]=val;}
            { populate( "attribute", values ); }
            "return" val=returnType "is"
            { values[0]=val; populate( "typeref", values ); }
            {
                selector.push("bodylexer");
                BodyParser bodyparser = new BodyParser(selector, m_ci);
                values[0] = bodyparser.codeBlock();
                if ( bodyparser.m_errors ) {
                    m_errors = true;
                    m_output += bodyparser.m_output;
                }
                populate( "codeblock", values );
                selector.pop();
                populate( "attribute", values );
                sendSmasl();
            }
            ;

transitionDefinition:
            {
                String[] values = new String[8];
                clearValues(values);
                String val = "";
            }
            kw:"transition" { startLine = kw.getLine(); }
            (val=domainName {values[1]=val;})? "::" val=objectName {values[2]=val;}
            "in" val=stateName {values[0]=val;} "receives" val=eventName {values[3]=val;}
            { populate( "transition", values ); }
            parameterList "is"
            {
                selector.push("bodylexer");
                BodyParser bodyparser = new BodyParser(selector, m_ci);
                values[0] = bodyparser.codeBlock();
                if ( bodyparser.m_errors ) {
                    m_errors = true;
                    m_output += bodyparser.m_output;
                }
                populate( "codeblock", values );
                selector.pop();
                populate( "transition", values );
                sendSmasl();
            }
            ;

serviceVisibility returns [String s = ""]:
            "public"        { s = "public"; }
            | "private"     { s = "private"; }
            | /* empty */   { s = "public"; }
            ;

domainName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

terminatorName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

objectName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

stateName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

eventName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

attributeName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

serviceName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

parameterList:
            "("
            ( parameterDefinitions )?
            ")"
            ;

parameterDefinitions:
            {
                String[] values = new String[8];
                clearValues(values);
            }
            parameterDefinition ( "," parameterDefinitions )?
            { populate( "parameter", values ); }
            ;

parameterDefinition:
            {
                String[] values = new String[8];
                clearValues(values);
                String val = "";
            }
            val=parameterName { values[0]=val; }
            ":" val=parameterMode { values[1]=val; }
            { populate( "parameter", values ); }
            val=parameterType { values[0]=val; populate( "typeref", values ); }
            ;

parameterName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

parameterMode returns [String s = ""]:
            "in"        { s = "in"; }
            | "out"     { s = "in"; }
            ;

stateType returns [String s = ""]:
            "assigner"              { s = "assigner"; }
            | "assigner" "start"    { s = "assigner start"; }
            | "creation"            { s = "creation"; }
            | "terminal"            { s = "terminal"; }
            | /* empty */
            ;

parameterType returns [String s = ""]:
            s=typeReference
            ;

returnType returns [String s = ""]:
            s=typeReference
            ;

typeReference returns [String s = ""]: 
            { String val = ""; }
            ("anonymous" {s="anonymous ";} )?
            ( val=namedTypeRef      { s += val; }
            | val=instanceTypeRef   { s += val; }
            | val=collectionTypeRef { s += val; } )
            ;

qualifiedObjectName returns [String s = ""]:
            { String val = ""; }
            (s=domainName "::" { s += "::"; })? val=objectName { s += val; }
            ;

instanceTypeRef returns [String s = ""]:
            { String val = ""; }
            "instance" "of" val=qualifiedObjectName { s = "instance of " + val; }
            ;

namedTypeRef returns [String s = ""]:
            { String val = ""; }
            (s=domainName "::" { s += "::"; })? val=typeName { s += val; }
            ;

typeName returns [String s = ""]:
            id:ID { s = id.getText(); }
            ;

/*
arrayBounds:
            "(" expression ")"
            ;
*/

collectionTypeRef returns [String s = ""]:
            { String val = ""; }
            ( "sequence" /*("(" expression ")")?*/ "of" val=typeReference { s = "sequence of " + val; }
            //| "array"  arrayBounds "of" typeReference
            | "set" "of" val=typeReference { s = "set of " + val; }
            | "bag" "of" val=typeReference { s = "bag of " + val; }
            | "dictionary" {s="dictionary";}
            ( (val=dictKeyType { s += " " + val; } )? "of" val=dictValueType { s += " of " + val; } )?
            )
            ;

dictKeyType returns [String s = ""]:
            s=namedTypeRef
	    | s=instanceTypeRef
	    ;

dictValueType returns [String s = ""]:
            s=typeReference
            ;
	

class SignatureLexer extends Lexer;
options {
    k=3;
}
{
    public SignatureLexer( Reader in, CoreImport ci ) {
        this(in);
        m_ci = ci;
    }

    public void reportError(RecognitionException arg0) {
        m_errors = true;
	m_output += "SignatureLexer: " + (( m_ci != null && m_ci.m_actionFile != null) ? m_ci.m_actionFile.getName() + " " : "") + arg0.toString() + "\n";
    }

    public String m_output = "";
    public boolean m_errors = false;
    
    private CoreImport m_ci = null;
}

SCOPE               : "::";
SEMI                : ";";
RPAREN              : ")";
LPAREN              : "(";
COLON               : ":";
TERMINATOR_SCOPE    : "~>";
DOT                 : ".";
COMMA               : ",";
ACTIVITYBEGIN1      : "//! ACTIVITY BEGIN.";
ACTIVITYBEGIN2      : "DO NOT EDIT THIS LINE." ('\r')? '\n' { newline(); };

UUID                : "'" ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) '-' ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) '-' ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) '-' ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) '-' ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) ( '0'..'9' | 'a'..'f' ) "'";

ID                  : ( ('A'..'Z' | 'a'..'z') | '_' ) ( ('A'..'Z' | 'a'..'z') | ('0'..'9') | '_' )*;

WS                  : (' ' | '\t' | '\f' | '\n'{newline();} | '\r' )+ { $setType(Token.SKIP); };
COMMENT             : "//" ((~('!') (~('\n'|'\r'))* ('\r')? '\n') | '\n') { newline(); $setType(Token.SKIP); };
