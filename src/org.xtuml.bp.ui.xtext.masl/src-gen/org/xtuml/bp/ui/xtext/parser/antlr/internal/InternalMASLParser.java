package org.xtuml.bp.ui.xtext.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.xtuml.bp.ui.xtext.services.MASLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalMASLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INSTANCE", "RULE_EVENT", "RULE_SERVICE", "RULE_SCOPE", "RULE_ANONYMOUS", "RULE_OF", "RULE_LPAREN", "RULE_RPAREN", "RULE_SEQUENCE", "RULE_ARRAY", "RULE_SET", "RULE_BAG", "RULE_DICTIONARY", "RULE_RELATIONSHIPNAME", "RULE_DOT", "RULE_DEFERRED", "RULE_ASSIGNER", "RULE_CREATION", "RULE_TERMINAL", "RULE_START", "RULE_COMMA", "RULE_COLON", "RULE_PRIVATE", "RULE_PUBLIC", "RULE_IN", "RULE_OUT", "RULE_SEMI", "RULE_PRAGMA", "RULE_IS", "RULE_FUNCTION", "RULE_RETURN", "RULE_TERMINATOR_SCOPE", "RULE_STATE", "RULE_NULL", "RULE_ASSIGN", "RULE_STREAM_IN", "RULE_STREAM_OUT", "RULE_STREAM_LINE_IN", "RULE_STREAM_LINE_OUT", "RULE_LBRACKET", "RULE_RBRACKET", "RULE_PRIME", "RULE_EXIT", "RULE_WHEN", "RULE_DELAY", "RULE_RAISE", "RULE_DELETE", "RULE_ERASE", "RULE_USING", "RULE_LINK", "RULE_UNLINK", "RULE_SCHEDULE", "RULE_DELTA", "RULE_CANCEL", "RULE_AT", "RULE_GENERATE", "RULE_TO", "RULE_IF", "RULE_THEN", "RULE_END", "RULE_ELSIF", "RULE_ELSE", "RULE_WHILE", "RULE_LOOP", "RULE_CASE", "RULE_GOES_TO", "RULE_CASE_OR", "RULE_OTHERS", "RULE_FOR", "RULE_REVERSE", "RULE_DECLARE", "RULE_BEGIN", "RULE_EXCEPTION", "RULE_READONLY", "RULE_OR", "RULE_XOR", "RULE_AND", "RULE_NOT", "RULE_EQUAL", "RULE_NOT_EQUAL", "RULE_LT", "RULE_GT", "RULE_LTE", "RULE_GTE", "RULE_RANGE_DOTS", "RULE_PLUS", "RULE_MINUS", "RULE_CONCATENATE", "RULE_UNION", "RULE_NOT_IN", "RULE_TIMES", "RULE_DIVIDE", "RULE_MOD", "RULE_POWER", "RULE_REM", "RULE_INTERSECTION", "RULE_DISUNION", "RULE_ABS", "RULE_NAVIGATE", "RULE_WITH", "RULE_ORDERED_BY", "RULE_REVERSE_ORDERED_BY", "RULE_CREATE", "RULE_UNIQUE", "RULE_CURRENT_STATE", "RULE_FIND", "RULE_FIND_ONE", "RULE_FIND_ONLY", "RULE_INTEGERLITERAL", "RULE_REALLITERAL", "RULE_STRINGLITERAL", "RULE_TIMESTAMPLITERAL", "RULE_DURATIONLITERAL", "RULE_TRUE", "RULE_FALSE", "RULE_FLUSH", "RULE_ENDL", "RULE_THIS", "RULE_CONSOLE", "RULE_LINE_NO", "RULE_FILE_NAME", "RULE_IDENT", "RULE_RANGE", "RULE_LTGT", "RULE_CANNOT_HAPPEN", "RULE_CONDITIONALLY", "RULE_DIGITS", "RULE_DOMAIN", "RULE_ENUM", "RULE_IDENTIF", "RULE_IGNORE", "RULE_IS_A", "RULE_MANY", "RULE_NON_EXISTENT", "RULE_OBJECT", "RULE_ONE", "RULE_PREFERRED", "RULE_PROJECT", "RULE_REFERENTIAL", "RULE_RELATIONSHIP", "RULE_STRUCTURE", "RULE_TERMINATOR", "RULE_TRANSITION", "RULE_TYPE", "RULE_UNCONDITIONALLY", "RULE_DIGIT", "RULE_BASEDDIGIT", "RULE_UNBASEDEXPONENT", "RULE_BASEDEXPONENT", "RULE_LETTER", "RULE_ESCAPESEQUENCE", "RULE_UNICODEESCAPE", "RULE_OCTALESCAPE", "RULE_HEXDIGIT", "RULE_COMMENT", "RULE_WHITESPACE"
    };
    public static final int RULE_WITH=103;
    public static final int RULE_CONDITIONALLY=129;
    public static final int RULE_GT=85;
    public static final int RULE_GTE=87;
    public static final int RULE_DISUNION=100;
    public static final int RULE_TERMINAL=22;
    public static final int RULE_NOT_EQUAL=83;
    public static final int RULE_ASSIGNER=20;
    public static final int RULE_OR=78;
    public static final int RULE_BAG=15;
    public static final int RULE_WHILE=66;
    public static final int RULE_LOOP=67;
    public static final int RULE_READONLY=77;
    public static final int RULE_RETURN=34;
    public static final int RULE_EQUAL=82;
    public static final int RULE_MOD=96;
    public static final int RULE_UNLINK=54;
    public static final int RULE_UNICODEESCAPE=155;
    public static final int RULE_TIMES=94;
    public static final int RULE_THIS=121;
    public static final int RULE_DIGIT=149;
    public static final int RULE_REFERENTIAL=142;
    public static final int RULE_COLON=25;
    public static final int RULE_ASSIGN=38;
    public static final int RULE_RAISE=49;
    public static final int RULE_POWER=97;
    public static final int RULE_RELATIONSHIPNAME=17;
    public static final int RULE_STRUCTURE=144;
    public static final int RULE_EXCEPTION=76;
    public static final int RULE_DURATIONLITERAL=116;
    public static final int RULE_FALSE=118;
    public static final int RULE_CANCEL=57;
    public static final int RULE_FIND=109;
    public static final int RULE_EVENT=5;
    public static final int RULE_LBRACKET=43;
    public static final int RULE_DELAY=48;
    public static final int RULE_DELETE=50;
    public static final int RULE_XOR=79;
    public static final int RULE_SET=14;
    public static final int RULE_FUNCTION=33;
    public static final int RULE_FIND_ONLY=111;
    public static final int RULE_ENUM=132;
    public static final int RULE_RANGE_DOTS=88;
    public static final int RULE_STREAM_OUT=40;
    public static final int RULE_OBJECT=138;
    public static final int RULE_AT=58;
    public static final int RULE_OUT=29;
    public static final int RULE_DIVIDE=95;
    public static final int RULE_IN=28;
    public static final int RULE_CURRENT_STATE=108;
    public static final int RULE_NAVIGATE=102;
    public static final int RULE_LINE_NO=123;
    public static final int RULE_IS=32;
    public static final int RULE_DECLARE=74;
    public static final int RULE_IF=61;
    public static final int RULE_DOT=18;
    public static final int RULE_WHITESPACE=159;
    public static final int RULE_CONCATENATE=91;
    public static final int RULE_NOT_IN=93;
    public static final int RULE_PUBLIC=27;
    public static final int RULE_SCHEDULE=55;
    public static final int RULE_LETTER=153;
    public static final int RULE_INTERSECTION=99;
    public static final int RULE_ORDERED_BY=104;
    public static final int RULE_OCTALESCAPE=156;
    public static final int RULE_USING=52;
    public static final int RULE_ENDL=120;
    public static final int RULE_LINK=53;
    public static final int RULE_STREAM_LINE_OUT=42;
    public static final int RULE_FILE_NAME=124;
    public static final int RULE_RELATIONSHIP=143;
    public static final int RULE_REVERSE=73;
    public static final int RULE_BEGIN=75;
    public static final int RULE_SEQUENCE=12;
    public static final int RULE_TO=60;
    public static final int RULE_CANNOT_HAPPEN=128;
    public static final int RULE_BASEDEXPONENT=152;
    public static final int RULE_STRINGLITERAL=114;
    public static final int RULE_CASE_OR=70;
    public static final int RULE_PRIME=45;
    public static final int RULE_REM=98;
    public static final int RULE_LPAREN=10;
    public static final int RULE_DEFERRED=19;
    public static final int RULE_REVERSE_ORDERED_BY=105;
    public static final int RULE_ANONYMOUS=8;
    public static final int RULE_CREATE=106;
    public static final int RULE_PRIVATE=26;
    public static final int RULE_ELSIF=64;
    public static final int RULE_UNCONDITIONALLY=148;
    public static final int RULE_STATE=36;
    public static final int RULE_DICTIONARY=16;
    public static final int RULE_ESCAPESEQUENCE=154;
    public static final int RULE_OTHERS=71;
    public static final int RULE_FLUSH=119;
    public static final int RULE_HEXDIGIT=157;
    public static final int RULE_EXIT=46;
    public static final int RULE_COMMA=24;
    public static final int RULE_TERMINATOR_SCOPE=35;
    public static final int RULE_BASEDDIGIT=150;
    public static final int RULE_THEN=62;
    public static final int RULE_IDENT=125;
    public static final int RULE_LT=84;
    public static final int RULE_CASE=68;
    public static final int RULE_NON_EXISTENT=137;
    public static final int RULE_DELTA=56;
    public static final int RULE_TYPE=147;
    public static final int RULE_ABS=101;
    public static final int RULE_ERASE=51;
    public static final int RULE_STREAM_IN=39;
    public static final int RULE_WHEN=47;
    public static final int RULE_INTEGERLITERAL=112;
    public static final int RULE_PROJECT=141;
    public static final int RULE_ONE=139;
    public static final int RULE_UNION=92;
    public static final int RULE_TIMESTAMPLITERAL=115;
    public static final int RULE_INSTANCE=4;
    public static final int RULE_ELSE=65;
    public static final int RULE_LTE=86;
    public static final int RULE_IGNORE=134;
    public static final int RULE_END=63;
    public static final int RULE_GOES_TO=69;
    public static final int RULE_START=23;
    public static final int RULE_SCOPE=7;
    public static final int RULE_ARRAY=13;
    public static final int RULE_NOT=81;
    public static final int RULE_NULL=37;
    public static final int RULE_DIGITS=130;
    public static final int RULE_AND=80;
    public static final int RULE_GENERATE=59;
    public static final int RULE_TRANSITION=146;
    public static final int RULE_CONSOLE=122;
    public static final int RULE_TRUE=117;
    public static final int RULE_PLUS=89;
    public static final int RULE_FOR=72;
    public static final int EOF=-1;
    public static final int RULE_OF=9;
    public static final int RULE_PRAGMA=31;
    public static final int RULE_FIND_ONE=110;
    public static final int RULE_MANY=136;
    public static final int RULE_SERVICE=6;
    public static final int RULE_COMMENT=158;
    public static final int RULE_IS_A=135;
    public static final int RULE_IDENTIF=133;
    public static final int RULE_UNBASEDEXPONENT=151;
    public static final int RULE_MINUS=90;
    public static final int RULE_RPAREN=11;
    public static final int RULE_TERMINATOR=145;
    public static final int RULE_CREATION=21;
    public static final int RULE_REALLITERAL=113;
    public static final int RULE_STREAM_LINE_IN=41;
    public static final int RULE_SEMI=30;
    public static final int RULE_UNIQUE=107;
    public static final int RULE_DOMAIN=131;
    public static final int RULE_PREFERRED=140;
    public static final int RULE_LTGT=127;
    public static final int RULE_RBRACKET=44;
    public static final int RULE_RANGE=126;

    // delegates
    // delegators


        public InternalMASLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMASLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMASLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalMASL.g"; }



     	private MASLGrammarAccess grammarAccess;

        public InternalMASLParser(TokenStream input, MASLGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "definition";
       	}

       	@Override
       	protected MASLGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuledefinition"
    // InternalMASL.g:64:1: entryRuledefinition returns [EObject current=null] : iv_ruledefinition= ruledefinition EOF ;
    public final EObject entryRuledefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledefinition = null;


        try {
            // InternalMASL.g:64:51: (iv_ruledefinition= ruledefinition EOF )
            // InternalMASL.g:65:2: iv_ruledefinition= ruledefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledefinition=ruledefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledefinition"


    // $ANTLR start "ruledefinition"
    // InternalMASL.g:71:1: ruledefinition returns [EObject current=null] : (this_objectServiceDefinition_0= ruleobjectServiceDefinition | this_objectFunctionDefinition_1= ruleobjectFunctionDefinition | this_stateDefinition_2= rulestateDefinition | this_domainServiceDefinition_3= ruledomainServiceDefinition | this_domainFunctionDefinition_4= ruledomainFunctionDefinition | this_terminatorServiceDefinition_5= ruleterminatorServiceDefinition | this_terminatorFunctionDefinition_6= ruleterminatorFunctionDefinition ) ;
    public final EObject ruledefinition() throws RecognitionException {
        EObject current = null;

        EObject this_objectServiceDefinition_0 = null;

        EObject this_objectFunctionDefinition_1 = null;

        EObject this_stateDefinition_2 = null;

        EObject this_domainServiceDefinition_3 = null;

        EObject this_domainFunctionDefinition_4 = null;

        EObject this_terminatorServiceDefinition_5 = null;

        EObject this_terminatorFunctionDefinition_6 = null;



        	enterRule();

        try {
            // InternalMASL.g:77:2: ( (this_objectServiceDefinition_0= ruleobjectServiceDefinition | this_objectFunctionDefinition_1= ruleobjectFunctionDefinition | this_stateDefinition_2= rulestateDefinition | this_domainServiceDefinition_3= ruledomainServiceDefinition | this_domainFunctionDefinition_4= ruledomainFunctionDefinition | this_terminatorServiceDefinition_5= ruleterminatorServiceDefinition | this_terminatorFunctionDefinition_6= ruleterminatorFunctionDefinition ) )
            // InternalMASL.g:78:2: (this_objectServiceDefinition_0= ruleobjectServiceDefinition | this_objectFunctionDefinition_1= ruleobjectFunctionDefinition | this_stateDefinition_2= rulestateDefinition | this_domainServiceDefinition_3= ruledomainServiceDefinition | this_domainFunctionDefinition_4= ruledomainFunctionDefinition | this_terminatorServiceDefinition_5= ruleterminatorServiceDefinition | this_terminatorFunctionDefinition_6= ruleterminatorFunctionDefinition )
            {
            // InternalMASL.g:78:2: (this_objectServiceDefinition_0= ruleobjectServiceDefinition | this_objectFunctionDefinition_1= ruleobjectFunctionDefinition | this_stateDefinition_2= rulestateDefinition | this_domainServiceDefinition_3= ruledomainServiceDefinition | this_domainFunctionDefinition_4= ruledomainFunctionDefinition | this_terminatorServiceDefinition_5= ruleterminatorServiceDefinition | this_terminatorFunctionDefinition_6= ruleterminatorFunctionDefinition )
            int alt1=7;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // InternalMASL.g:79:3: this_objectServiceDefinition_0= ruleobjectServiceDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getDefinitionAccess().getObjectServiceDefinitionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_objectServiceDefinition_0=ruleobjectServiceDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_objectServiceDefinition_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:88:3: this_objectFunctionDefinition_1= ruleobjectFunctionDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getDefinitionAccess().getObjectFunctionDefinitionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_objectFunctionDefinition_1=ruleobjectFunctionDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_objectFunctionDefinition_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:97:3: this_stateDefinition_2= rulestateDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getDefinitionAccess().getStateDefinitionParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_stateDefinition_2=rulestateDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_stateDefinition_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalMASL.g:106:3: this_domainServiceDefinition_3= ruledomainServiceDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getDefinitionAccess().getDomainServiceDefinitionParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_domainServiceDefinition_3=ruledomainServiceDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_domainServiceDefinition_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalMASL.g:115:3: this_domainFunctionDefinition_4= ruledomainFunctionDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getDefinitionAccess().getDomainFunctionDefinitionParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_domainFunctionDefinition_4=ruledomainFunctionDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_domainFunctionDefinition_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalMASL.g:124:3: this_terminatorServiceDefinition_5= ruleterminatorServiceDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getDefinitionAccess().getTerminatorServiceDefinitionParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_terminatorServiceDefinition_5=ruleterminatorServiceDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_terminatorServiceDefinition_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalMASL.g:133:3: this_terminatorFunctionDefinition_6= ruleterminatorFunctionDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getDefinitionAccess().getTerminatorFunctionDefinitionParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_terminatorFunctionDefinition_6=ruleterminatorFunctionDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_terminatorFunctionDefinition_6;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledefinition"


    // $ANTLR start "entryRuledomainName"
    // InternalMASL.g:145:1: entryRuledomainName returns [String current=null] : iv_ruledomainName= ruledomainName EOF ;
    public final String entryRuledomainName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruledomainName = null;


        try {
            // InternalMASL.g:145:50: (iv_ruledomainName= ruledomainName EOF )
            // InternalMASL.g:146:2: iv_ruledomainName= ruledomainName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDomainNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledomainName=ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledomainName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledomainName"


    // $ANTLR start "ruledomainName"
    // InternalMASL.g:152:1: ruledomainName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruledomainName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:158:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:159:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getDomainNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledomainName"


    // $ANTLR start "entryRuleexceptionName"
    // InternalMASL.g:172:1: entryRuleexceptionName returns [String current=null] : iv_ruleexceptionName= ruleexceptionName EOF ;
    public final String entryRuleexceptionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleexceptionName = null;


        try {
            // InternalMASL.g:172:53: (iv_ruleexceptionName= ruleexceptionName EOF )
            // InternalMASL.g:173:2: iv_ruleexceptionName= ruleexceptionName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExceptionNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleexceptionName=ruleexceptionName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleexceptionName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleexceptionName"


    // $ANTLR start "ruleexceptionName"
    // InternalMASL.g:179:1: ruleexceptionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruleexceptionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:185:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:186:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getExceptionNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleexceptionName"


    // $ANTLR start "entryRuletypeReference"
    // InternalMASL.g:199:1: entryRuletypeReference returns [EObject current=null] : iv_ruletypeReference= ruletypeReference EOF ;
    public final EObject entryRuletypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruletypeReference = null;


        try {
            // InternalMASL.g:199:54: (iv_ruletypeReference= ruletypeReference EOF )
            // InternalMASL.g:200:2: iv_ruletypeReference= ruletypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeReferenceRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruletypeReference=ruletypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruletypeReference; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuletypeReference"


    // $ANTLR start "ruletypeReference"
    // InternalMASL.g:206:1: ruletypeReference returns [EObject current=null] : ( ( () rulenamedTypeRef ) | ( () ruleinstanceTypeRef ) | ( (lv_c_4_0= rulecollectionTypeRef ) ) | ( () ruledeprecatedType ) ) ;
    public final EObject ruletypeReference() throws RecognitionException {
        EObject current = null;

        EObject lv_c_4_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:212:2: ( ( ( () rulenamedTypeRef ) | ( () ruleinstanceTypeRef ) | ( (lv_c_4_0= rulecollectionTypeRef ) ) | ( () ruledeprecatedType ) ) )
            // InternalMASL.g:213:2: ( ( () rulenamedTypeRef ) | ( () ruleinstanceTypeRef ) | ( (lv_c_4_0= rulecollectionTypeRef ) ) | ( () ruledeprecatedType ) )
            {
            // InternalMASL.g:213:2: ( ( () rulenamedTypeRef ) | ( () ruleinstanceTypeRef ) | ( (lv_c_4_0= rulecollectionTypeRef ) ) | ( () ruledeprecatedType ) )
            int alt2=4;
            switch ( input.LA(1) ) {
            case RULE_ANONYMOUS:
                {
                switch ( input.LA(2) ) {
                case RULE_SEQUENCE:
                case RULE_ARRAY:
                case RULE_SET:
                case RULE_BAG:
                case RULE_DICTIONARY:
                    {
                    alt2=3;
                    }
                    break;
                case RULE_IDENT:
                    {
                    alt2=1;
                    }
                    break;
                case RULE_INSTANCE:
                    {
                    alt2=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }

                }
                break;
            case RULE_IDENT:
                {
                alt2=1;
                }
                break;
            case RULE_INSTANCE:
                {
                int LA2_3 = input.LA(2);

                if ( (LA2_3==RULE_OF) ) {
                    alt2=2;
                }
                else if ( (LA2_3==EOF||(LA2_3>=RULE_LPAREN && LA2_3<=RULE_RPAREN)||(LA2_3>=RULE_RELATIONSHIPNAME && LA2_3<=RULE_DOT)||LA2_3==RULE_COMMA||LA2_3==RULE_SEMI||LA2_3==RULE_IS||LA2_3==RULE_TERMINATOR_SCOPE||(LA2_3>=RULE_ASSIGN && LA2_3<=RULE_PRIME)||LA2_3==RULE_DELAY||LA2_3==RULE_USING||LA2_3==RULE_DELTA||(LA2_3>=RULE_AT && LA2_3<=RULE_GENERATE)||LA2_3==RULE_THEN||LA2_3==RULE_LOOP||(LA2_3>=RULE_GOES_TO && LA2_3<=RULE_CASE_OR)||(LA2_3>=RULE_OR && LA2_3<=RULE_AND)||(LA2_3>=RULE_EQUAL && LA2_3<=RULE_DISUNION)||(LA2_3>=RULE_NAVIGATE && LA2_3<=RULE_REVERSE_ORDERED_BY)) ) {
                    alt2=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_SEQUENCE:
            case RULE_ARRAY:
            case RULE_SET:
            case RULE_BAG:
            case RULE_DICTIONARY:
                {
                alt2=3;
                }
                break;
            case RULE_EVENT:
            case RULE_SERVICE:
                {
                alt2=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalMASL.g:214:3: ( () rulenamedTypeRef )
                    {
                    // InternalMASL.g:214:3: ( () rulenamedTypeRef )
                    // InternalMASL.g:215:4: () rulenamedTypeRef
                    {
                    // InternalMASL.g:215:4: ()
                    // InternalMASL.g:216:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeReferenceAccess().getTypeReferenceAction_0_0(),
                      						current);
                      				
                    }

                    }

                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypeReferenceAccess().getNamedTypeRefParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    rulenamedTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:231:3: ( () ruleinstanceTypeRef )
                    {
                    // InternalMASL.g:231:3: ( () ruleinstanceTypeRef )
                    // InternalMASL.g:232:4: () ruleinstanceTypeRef
                    {
                    // InternalMASL.g:232:4: ()
                    // InternalMASL.g:233:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeReferenceAccess().getTypeReferenceAction_1_0(),
                      						current);
                      				
                    }

                    }

                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypeReferenceAccess().getInstanceTypeRefParserRuleCall_1_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    ruleinstanceTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 3 :
                    // InternalMASL.g:248:3: ( (lv_c_4_0= rulecollectionTypeRef ) )
                    {
                    // InternalMASL.g:248:3: ( (lv_c_4_0= rulecollectionTypeRef ) )
                    // InternalMASL.g:249:4: (lv_c_4_0= rulecollectionTypeRef )
                    {
                    // InternalMASL.g:249:4: (lv_c_4_0= rulecollectionTypeRef )
                    // InternalMASL.g:250:5: lv_c_4_0= rulecollectionTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getTypeReferenceAccess().getCCollectionTypeRefParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_c_4_0=rulecollectionTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getTypeReferenceRule());
                      					}
                      					set(
                      						current,
                      						"c",
                      						lv_c_4_0,
                      						"org.xtuml.bp.ui.xtext.MASL.collectionTypeRef");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalMASL.g:268:3: ( () ruledeprecatedType )
                    {
                    // InternalMASL.g:268:3: ( () ruledeprecatedType )
                    // InternalMASL.g:269:4: () ruledeprecatedType
                    {
                    // InternalMASL.g:269:4: ()
                    // InternalMASL.g:270:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeReferenceAccess().getTypeReferenceAction_3_0(),
                      						current);
                      				
                    }

                    }

                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypeReferenceAccess().getDeprecatedTypeParserRuleCall_3_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    ruledeprecatedType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruletypeReference"


    // $ANTLR start "entryRuletypeReferenceWithCA"
    // InternalMASL.g:288:1: entryRuletypeReferenceWithCA returns [EObject current=null] : iv_ruletypeReferenceWithCA= ruletypeReferenceWithCA EOF ;
    public final EObject entryRuletypeReferenceWithCA() throws RecognitionException {
        EObject current = null;

        EObject iv_ruletypeReferenceWithCA = null;


        try {
            // InternalMASL.g:288:60: (iv_ruletypeReferenceWithCA= ruletypeReferenceWithCA EOF )
            // InternalMASL.g:289:2: iv_ruletypeReferenceWithCA= ruletypeReferenceWithCA EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeReferenceWithCARule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruletypeReferenceWithCA=ruletypeReferenceWithCA();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruletypeReferenceWithCA; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuletypeReferenceWithCA"


    // $ANTLR start "ruletypeReferenceWithCA"
    // InternalMASL.g:295:1: ruletypeReferenceWithCA returns [EObject current=null] : (this_typeReference_0= ruletypeReference | this_constrainedArrayTypeRef_1= ruleconstrainedArrayTypeRef ) ;
    public final EObject ruletypeReferenceWithCA() throws RecognitionException {
        EObject current = null;

        EObject this_typeReference_0 = null;

        EObject this_constrainedArrayTypeRef_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:301:2: ( (this_typeReference_0= ruletypeReference | this_constrainedArrayTypeRef_1= ruleconstrainedArrayTypeRef ) )
            // InternalMASL.g:302:2: (this_typeReference_0= ruletypeReference | this_constrainedArrayTypeRef_1= ruleconstrainedArrayTypeRef )
            {
            // InternalMASL.g:302:2: (this_typeReference_0= ruletypeReference | this_constrainedArrayTypeRef_1= ruleconstrainedArrayTypeRef )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=RULE_INSTANCE && LA3_0<=RULE_SERVICE)||LA3_0==RULE_ANONYMOUS||(LA3_0>=RULE_SEQUENCE && LA3_0<=RULE_DICTIONARY)) ) {
                alt3=1;
            }
            else if ( (LA3_0==RULE_IDENT) ) {
                switch ( input.LA(2) ) {
                case RULE_SCOPE:
                    {
                    int LA3_3 = input.LA(3);

                    if ( (LA3_3==RULE_IDENT) ) {
                        int LA3_5 = input.LA(4);

                        if ( (LA3_5==RULE_LPAREN) ) {
                            alt3=2;
                        }
                        else if ( (LA3_5==EOF||LA3_5==RULE_SEMI||LA3_5==RULE_ASSIGN) ) {
                            alt3=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 5, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_LPAREN:
                    {
                    alt3=2;
                    }
                    break;
                case EOF:
                case RULE_SEMI:
                case RULE_ASSIGN:
                    {
                    alt3=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalMASL.g:303:3: this_typeReference_0= ruletypeReference
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeReferenceWithCAAccess().getTypeReferenceParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_typeReference_0=ruletypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_typeReference_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:312:3: this_constrainedArrayTypeRef_1= ruleconstrainedArrayTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeReferenceWithCAAccess().getConstrainedArrayTypeRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_constrainedArrayTypeRef_1=ruleconstrainedArrayTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_constrainedArrayTypeRef_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruletypeReferenceWithCA"


    // $ANTLR start "entryRuledeprecatedType"
    // InternalMASL.g:324:1: entryRuledeprecatedType returns [String current=null] : iv_ruledeprecatedType= ruledeprecatedType EOF ;
    public final String entryRuledeprecatedType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruledeprecatedType = null;


        try {
            // InternalMASL.g:324:54: (iv_ruledeprecatedType= ruledeprecatedType EOF )
            // InternalMASL.g:325:2: iv_ruledeprecatedType= ruledeprecatedType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDeprecatedTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledeprecatedType=ruledeprecatedType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledeprecatedType.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledeprecatedType"


    // $ANTLR start "ruledeprecatedType"
    // InternalMASL.g:331:1: ruledeprecatedType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INSTANCE_0= RULE_INSTANCE | this_EVENT_1= RULE_EVENT | this_SERVICE_2= RULE_SERVICE ) ;
    public final AntlrDatatypeRuleToken ruledeprecatedType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INSTANCE_0=null;
        Token this_EVENT_1=null;
        Token this_SERVICE_2=null;


        	enterRule();

        try {
            // InternalMASL.g:337:2: ( (this_INSTANCE_0= RULE_INSTANCE | this_EVENT_1= RULE_EVENT | this_SERVICE_2= RULE_SERVICE ) )
            // InternalMASL.g:338:2: (this_INSTANCE_0= RULE_INSTANCE | this_EVENT_1= RULE_EVENT | this_SERVICE_2= RULE_SERVICE )
            {
            // InternalMASL.g:338:2: (this_INSTANCE_0= RULE_INSTANCE | this_EVENT_1= RULE_EVENT | this_SERVICE_2= RULE_SERVICE )
            int alt4=3;
            switch ( input.LA(1) ) {
            case RULE_INSTANCE:
                {
                alt4=1;
                }
                break;
            case RULE_EVENT:
                {
                alt4=2;
                }
                break;
            case RULE_SERVICE:
                {
                alt4=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalMASL.g:339:3: this_INSTANCE_0= RULE_INSTANCE
                    {
                    this_INSTANCE_0=(Token)match(input,RULE_INSTANCE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_INSTANCE_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_INSTANCE_0, grammarAccess.getDeprecatedTypeAccess().getINSTANCETerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:347:3: this_EVENT_1= RULE_EVENT
                    {
                    this_EVENT_1=(Token)match(input,RULE_EVENT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_EVENT_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_EVENT_1, grammarAccess.getDeprecatedTypeAccess().getEVENTTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:355:3: this_SERVICE_2= RULE_SERVICE
                    {
                    this_SERVICE_2=(Token)match(input,RULE_SERVICE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_SERVICE_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_SERVICE_2, grammarAccess.getDeprecatedTypeAccess().getSERVICETerminalRuleCall_2());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledeprecatedType"


    // $ANTLR start "entryRulequalifiedObjectName"
    // InternalMASL.g:366:1: entryRulequalifiedObjectName returns [String current=null] : iv_rulequalifiedObjectName= rulequalifiedObjectName EOF ;
    public final String entryRulequalifiedObjectName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulequalifiedObjectName = null;


        try {
            // InternalMASL.g:366:59: (iv_rulequalifiedObjectName= rulequalifiedObjectName EOF )
            // InternalMASL.g:367:2: iv_rulequalifiedObjectName= rulequalifiedObjectName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedObjectNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulequalifiedObjectName=rulequalifiedObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulequalifiedObjectName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulequalifiedObjectName"


    // $ANTLR start "rulequalifiedObjectName"
    // InternalMASL.g:373:1: rulequalifiedObjectName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_objectName_2= ruleobjectName ) ;
    public final AntlrDatatypeRuleToken rulequalifiedObjectName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_SCOPE_1=null;
        AntlrDatatypeRuleToken this_domainName_0 = null;

        AntlrDatatypeRuleToken this_objectName_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:379:2: ( ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_objectName_2= ruleobjectName ) )
            // InternalMASL.g:380:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_objectName_2= ruleobjectName )
            {
            // InternalMASL.g:380:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_objectName_2= ruleobjectName )
            // InternalMASL.g:381:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_objectName_2= ruleobjectName
            {
            // InternalMASL.g:381:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_IDENT) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==RULE_SCOPE) ) {
                    alt5=1;
                }
            }
            switch (alt5) {
                case 1 :
                    // InternalMASL.g:382:4: this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getQualifiedObjectNameAccess().getDomainNameParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_3);
                    this_domainName_0=ruledomainName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_domainName_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    this_SCOPE_1=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SCOPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SCOPE_1, grammarAccess.getQualifiedObjectNameAccess().getSCOPETerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedObjectNameAccess().getObjectNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_objectName_2=ruleobjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_objectName_2);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulequalifiedObjectName"


    // $ANTLR start "entryRuleinstanceTypeRef"
    // InternalMASL.g:414:1: entryRuleinstanceTypeRef returns [String current=null] : iv_ruleinstanceTypeRef= ruleinstanceTypeRef EOF ;
    public final String entryRuleinstanceTypeRef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleinstanceTypeRef = null;


        try {
            // InternalMASL.g:414:55: (iv_ruleinstanceTypeRef= ruleinstanceTypeRef EOF )
            // InternalMASL.g:415:2: iv_ruleinstanceTypeRef= ruleinstanceTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getInstanceTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleinstanceTypeRef=ruleinstanceTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleinstanceTypeRef.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleinstanceTypeRef"


    // $ANTLR start "ruleinstanceTypeRef"
    // InternalMASL.g:421:1: ruleinstanceTypeRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_INSTANCE_1= RULE_INSTANCE this_OF_2= RULE_OF this_qualifiedObjectName_3= rulequalifiedObjectName ) ;
    public final AntlrDatatypeRuleToken ruleinstanceTypeRef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ANONYMOUS_0=null;
        Token this_INSTANCE_1=null;
        Token this_OF_2=null;
        AntlrDatatypeRuleToken this_qualifiedObjectName_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:427:2: ( ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_INSTANCE_1= RULE_INSTANCE this_OF_2= RULE_OF this_qualifiedObjectName_3= rulequalifiedObjectName ) )
            // InternalMASL.g:428:2: ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_INSTANCE_1= RULE_INSTANCE this_OF_2= RULE_OF this_qualifiedObjectName_3= rulequalifiedObjectName )
            {
            // InternalMASL.g:428:2: ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_INSTANCE_1= RULE_INSTANCE this_OF_2= RULE_OF this_qualifiedObjectName_3= rulequalifiedObjectName )
            // InternalMASL.g:429:3: (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_INSTANCE_1= RULE_INSTANCE this_OF_2= RULE_OF this_qualifiedObjectName_3= rulequalifiedObjectName
            {
            // InternalMASL.g:429:3: (this_ANONYMOUS_0= RULE_ANONYMOUS )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_ANONYMOUS) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalMASL.g:430:4: this_ANONYMOUS_0= RULE_ANONYMOUS
                    {
                    this_ANONYMOUS_0=(Token)match(input,RULE_ANONYMOUS,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_ANONYMOUS_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_ANONYMOUS_0, grammarAccess.getInstanceTypeRefAccess().getANONYMOUSTerminalRuleCall_0());
                      			
                    }

                    }
                    break;

            }

            this_INSTANCE_1=(Token)match(input,RULE_INSTANCE,FOLLOW_6); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_INSTANCE_1);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_INSTANCE_1, grammarAccess.getInstanceTypeRefAccess().getINSTANCETerminalRuleCall_1());
              		
            }
            this_OF_2=(Token)match(input,RULE_OF,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_OF_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_OF_2, grammarAccess.getInstanceTypeRefAccess().getOFTerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getInstanceTypeRefAccess().getQualifiedObjectNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_qualifiedObjectName_3=rulequalifiedObjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_qualifiedObjectName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleinstanceTypeRef"


    // $ANTLR start "entryRulenamedTypeRef"
    // InternalMASL.g:466:1: entryRulenamedTypeRef returns [String current=null] : iv_rulenamedTypeRef= rulenamedTypeRef EOF ;
    public final String entryRulenamedTypeRef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulenamedTypeRef = null;


        try {
            // InternalMASL.g:466:52: (iv_rulenamedTypeRef= rulenamedTypeRef EOF )
            // InternalMASL.g:467:2: iv_rulenamedTypeRef= rulenamedTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNamedTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulenamedTypeRef=rulenamedTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulenamedTypeRef.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulenamedTypeRef"


    // $ANTLR start "rulenamedTypeRef"
    // InternalMASL.g:473:1: rulenamedTypeRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? (this_domainName_1= ruledomainName this_SCOPE_2= RULE_SCOPE )? this_typeName_3= ruletypeName ) ;
    public final AntlrDatatypeRuleToken rulenamedTypeRef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ANONYMOUS_0=null;
        Token this_SCOPE_2=null;
        AntlrDatatypeRuleToken this_domainName_1 = null;

        AntlrDatatypeRuleToken this_typeName_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:479:2: ( ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? (this_domainName_1= ruledomainName this_SCOPE_2= RULE_SCOPE )? this_typeName_3= ruletypeName ) )
            // InternalMASL.g:480:2: ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? (this_domainName_1= ruledomainName this_SCOPE_2= RULE_SCOPE )? this_typeName_3= ruletypeName )
            {
            // InternalMASL.g:480:2: ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? (this_domainName_1= ruledomainName this_SCOPE_2= RULE_SCOPE )? this_typeName_3= ruletypeName )
            // InternalMASL.g:481:3: (this_ANONYMOUS_0= RULE_ANONYMOUS )? (this_domainName_1= ruledomainName this_SCOPE_2= RULE_SCOPE )? this_typeName_3= ruletypeName
            {
            // InternalMASL.g:481:3: (this_ANONYMOUS_0= RULE_ANONYMOUS )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_ANONYMOUS) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalMASL.g:482:4: this_ANONYMOUS_0= RULE_ANONYMOUS
                    {
                    this_ANONYMOUS_0=(Token)match(input,RULE_ANONYMOUS,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_ANONYMOUS_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_ANONYMOUS_0, grammarAccess.getNamedTypeRefAccess().getANONYMOUSTerminalRuleCall_0());
                      			
                    }

                    }
                    break;

            }

            // InternalMASL.g:490:3: (this_domainName_1= ruledomainName this_SCOPE_2= RULE_SCOPE )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_IDENT) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==RULE_SCOPE) ) {
                    alt8=1;
                }
            }
            switch (alt8) {
                case 1 :
                    // InternalMASL.g:491:4: this_domainName_1= ruledomainName this_SCOPE_2= RULE_SCOPE
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getNamedTypeRefAccess().getDomainNameParserRuleCall_1_0());
                      			
                    }
                    pushFollow(FOLLOW_3);
                    this_domainName_1=ruledomainName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_domainName_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    this_SCOPE_2=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SCOPE_2);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SCOPE_2, grammarAccess.getNamedTypeRefAccess().getSCOPETerminalRuleCall_1_1());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getNamedTypeRefAccess().getTypeNameParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_2);
            this_typeName_3=ruletypeName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_typeName_3);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulenamedTypeRef"


    // $ANTLR start "entryRuleuserDefinedTypeRef"
    // InternalMASL.g:523:1: entryRuleuserDefinedTypeRef returns [String current=null] : iv_ruleuserDefinedTypeRef= ruleuserDefinedTypeRef EOF ;
    public final String entryRuleuserDefinedTypeRef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleuserDefinedTypeRef = null;


        try {
            // InternalMASL.g:523:58: (iv_ruleuserDefinedTypeRef= ruleuserDefinedTypeRef EOF )
            // InternalMASL.g:524:2: iv_ruleuserDefinedTypeRef= ruleuserDefinedTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUserDefinedTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleuserDefinedTypeRef=ruleuserDefinedTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleuserDefinedTypeRef.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleuserDefinedTypeRef"


    // $ANTLR start "ruleuserDefinedTypeRef"
    // InternalMASL.g:530:1: ruleuserDefinedTypeRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_typeName_2= ruletypeName ) ;
    public final AntlrDatatypeRuleToken ruleuserDefinedTypeRef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_SCOPE_1=null;
        AntlrDatatypeRuleToken this_domainName_0 = null;

        AntlrDatatypeRuleToken this_typeName_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:536:2: ( ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_typeName_2= ruletypeName ) )
            // InternalMASL.g:537:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_typeName_2= ruletypeName )
            {
            // InternalMASL.g:537:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_typeName_2= ruletypeName )
            // InternalMASL.g:538:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_typeName_2= ruletypeName
            {
            // InternalMASL.g:538:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_IDENT) ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==RULE_SCOPE) ) {
                    alt9=1;
                }
            }
            switch (alt9) {
                case 1 :
                    // InternalMASL.g:539:4: this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUserDefinedTypeRefAccess().getDomainNameParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_3);
                    this_domainName_0=ruledomainName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_domainName_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    this_SCOPE_1=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SCOPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SCOPE_1, grammarAccess.getUserDefinedTypeRefAccess().getSCOPETerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUserDefinedTypeRefAccess().getTypeNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_typeName_2=ruletypeName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_typeName_2);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleuserDefinedTypeRef"


    // $ANTLR start "entryRuleconstrainedArrayTypeRef"
    // InternalMASL.g:571:1: entryRuleconstrainedArrayTypeRef returns [EObject current=null] : iv_ruleconstrainedArrayTypeRef= ruleconstrainedArrayTypeRef EOF ;
    public final EObject entryRuleconstrainedArrayTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleconstrainedArrayTypeRef = null;


        try {
            // InternalMASL.g:571:64: (iv_ruleconstrainedArrayTypeRef= ruleconstrainedArrayTypeRef EOF )
            // InternalMASL.g:572:2: iv_ruleconstrainedArrayTypeRef= ruleconstrainedArrayTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConstrainedArrayTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleconstrainedArrayTypeRef=ruleconstrainedArrayTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleconstrainedArrayTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleconstrainedArrayTypeRef"


    // $ANTLR start "ruleconstrainedArrayTypeRef"
    // InternalMASL.g:578:1: ruleconstrainedArrayTypeRef returns [EObject current=null] : ( ruleuserDefinedTypeRef this_arrayBounds_1= rulearrayBounds ) ;
    public final EObject ruleconstrainedArrayTypeRef() throws RecognitionException {
        EObject current = null;

        EObject this_arrayBounds_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:584:2: ( ( ruleuserDefinedTypeRef this_arrayBounds_1= rulearrayBounds ) )
            // InternalMASL.g:585:2: ( ruleuserDefinedTypeRef this_arrayBounds_1= rulearrayBounds )
            {
            // InternalMASL.g:585:2: ( ruleuserDefinedTypeRef this_arrayBounds_1= rulearrayBounds )
            // InternalMASL.g:586:3: ruleuserDefinedTypeRef this_arrayBounds_1= rulearrayBounds
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getConstrainedArrayTypeRefAccess().getUserDefinedTypeRefParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_7);
            ruleuserDefinedTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getConstrainedArrayTypeRefAccess().getArrayBoundsParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_arrayBounds_1=rulearrayBounds();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_arrayBounds_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleconstrainedArrayTypeRef"


    // $ANTLR start "entryRuletypeName"
    // InternalMASL.g:605:1: entryRuletypeName returns [String current=null] : iv_ruletypeName= ruletypeName EOF ;
    public final String entryRuletypeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruletypeName = null;


        try {
            // InternalMASL.g:605:48: (iv_ruletypeName= ruletypeName EOF )
            // InternalMASL.g:606:2: iv_ruletypeName= ruletypeName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruletypeName=ruletypeName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruletypeName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuletypeName"


    // $ANTLR start "ruletypeName"
    // InternalMASL.g:612:1: ruletypeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruletypeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:618:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:619:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getTypeNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruletypeName"


    // $ANTLR start "entryRulearrayBounds"
    // InternalMASL.g:632:1: entryRulearrayBounds returns [EObject current=null] : iv_rulearrayBounds= rulearrayBounds EOF ;
    public final EObject entryRulearrayBounds() throws RecognitionException {
        EObject current = null;

        EObject iv_rulearrayBounds = null;


        try {
            // InternalMASL.g:632:52: (iv_rulearrayBounds= rulearrayBounds EOF )
            // InternalMASL.g:633:2: iv_rulearrayBounds= rulearrayBounds EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayBoundsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulearrayBounds=rulearrayBounds();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulearrayBounds; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulearrayBounds"


    // $ANTLR start "rulearrayBounds"
    // InternalMASL.g:639:1: rulearrayBounds returns [EObject current=null] : (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) this_RPAREN_2= RULE_RPAREN ) ;
    public final EObject rulearrayBounds() throws RecognitionException {
        EObject current = null;

        Token this_LPAREN_0=null;
        Token this_RPAREN_2=null;
        EObject lv_e_1_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:645:2: ( (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) this_RPAREN_2= RULE_RPAREN ) )
            // InternalMASL.g:646:2: (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) this_RPAREN_2= RULE_RPAREN )
            {
            // InternalMASL.g:646:2: (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) this_RPAREN_2= RULE_RPAREN )
            // InternalMASL.g:647:3: this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) this_RPAREN_2= RULE_RPAREN
            {
            this_LPAREN_0=(Token)match(input,RULE_LPAREN,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_0, grammarAccess.getArrayBoundsAccess().getLPARENTerminalRuleCall_0());
              		
            }
            // InternalMASL.g:651:3: ( (lv_e_1_0= ruleexpression ) )
            // InternalMASL.g:652:4: (lv_e_1_0= ruleexpression )
            {
            // InternalMASL.g:652:4: (lv_e_1_0= ruleexpression )
            // InternalMASL.g:653:5: lv_e_1_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArrayBoundsAccess().getEExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_9);
            lv_e_1_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArrayBoundsRule());
              					}
              					set(
              						current,
              						"e",
              						lv_e_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_RPAREN_2=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_2, grammarAccess.getArrayBoundsAccess().getRPARENTerminalRuleCall_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulearrayBounds"


    // $ANTLR start "entryRulecollectionTypeRef"
    // InternalMASL.g:678:1: entryRulecollectionTypeRef returns [EObject current=null] : iv_rulecollectionTypeRef= rulecollectionTypeRef EOF ;
    public final EObject entryRulecollectionTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecollectionTypeRef = null;


        try {
            // InternalMASL.g:678:58: (iv_rulecollectionTypeRef= rulecollectionTypeRef EOF )
            // InternalMASL.g:679:2: iv_rulecollectionTypeRef= rulecollectionTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCollectionTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecollectionTypeRef=rulecollectionTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecollectionTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecollectionTypeRef"


    // $ANTLR start "rulecollectionTypeRef"
    // InternalMASL.g:685:1: rulecollectionTypeRef returns [EObject current=null] : ( ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_SEQUENCE_1= RULE_SEQUENCE (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )? this_OF_5= RULE_OF ( (lv_t_6_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_7= RULE_ANONYMOUS )? this_ARRAY_8= RULE_ARRAY ( (lv_a_9_0= rulearrayBounds ) ) this_OF_10= RULE_OF ( (lv_t_11_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_12= RULE_ANONYMOUS )? this_SET_13= RULE_SET this_OF_14= RULE_OF ( (lv_t_15_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_16= RULE_ANONYMOUS )? this_BAG_17= RULE_BAG this_OF_18= RULE_OF ( (lv_t_19_0= ruletypeReference ) ) ) | ( () (this_ANONYMOUS_21= RULE_ANONYMOUS )? this_DICTIONARY_22= RULE_DICTIONARY ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )? ) ) ;
    public final EObject rulecollectionTypeRef() throws RecognitionException {
        EObject current = null;

        Token this_ANONYMOUS_0=null;
        Token this_SEQUENCE_1=null;
        Token this_LPAREN_2=null;
        Token this_RPAREN_4=null;
        Token this_OF_5=null;
        Token this_ANONYMOUS_7=null;
        Token this_ARRAY_8=null;
        Token this_OF_10=null;
        Token this_ANONYMOUS_12=null;
        Token this_SET_13=null;
        Token this_OF_14=null;
        Token this_ANONYMOUS_16=null;
        Token this_BAG_17=null;
        Token this_OF_18=null;
        Token this_ANONYMOUS_21=null;
        Token this_DICTIONARY_22=null;
        Token this_OF_24=null;
        EObject lv_e_3_0 = null;

        EObject lv_t_6_0 = null;

        EObject lv_a_9_0 = null;

        EObject lv_t_11_0 = null;

        EObject lv_t_15_0 = null;

        EObject lv_t_19_0 = null;

        EObject this_dictKeyType_23 = null;

        EObject lv_d_25_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:691:2: ( ( ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_SEQUENCE_1= RULE_SEQUENCE (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )? this_OF_5= RULE_OF ( (lv_t_6_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_7= RULE_ANONYMOUS )? this_ARRAY_8= RULE_ARRAY ( (lv_a_9_0= rulearrayBounds ) ) this_OF_10= RULE_OF ( (lv_t_11_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_12= RULE_ANONYMOUS )? this_SET_13= RULE_SET this_OF_14= RULE_OF ( (lv_t_15_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_16= RULE_ANONYMOUS )? this_BAG_17= RULE_BAG this_OF_18= RULE_OF ( (lv_t_19_0= ruletypeReference ) ) ) | ( () (this_ANONYMOUS_21= RULE_ANONYMOUS )? this_DICTIONARY_22= RULE_DICTIONARY ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )? ) ) )
            // InternalMASL.g:692:2: ( ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_SEQUENCE_1= RULE_SEQUENCE (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )? this_OF_5= RULE_OF ( (lv_t_6_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_7= RULE_ANONYMOUS )? this_ARRAY_8= RULE_ARRAY ( (lv_a_9_0= rulearrayBounds ) ) this_OF_10= RULE_OF ( (lv_t_11_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_12= RULE_ANONYMOUS )? this_SET_13= RULE_SET this_OF_14= RULE_OF ( (lv_t_15_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_16= RULE_ANONYMOUS )? this_BAG_17= RULE_BAG this_OF_18= RULE_OF ( (lv_t_19_0= ruletypeReference ) ) ) | ( () (this_ANONYMOUS_21= RULE_ANONYMOUS )? this_DICTIONARY_22= RULE_DICTIONARY ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )? ) )
            {
            // InternalMASL.g:692:2: ( ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_SEQUENCE_1= RULE_SEQUENCE (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )? this_OF_5= RULE_OF ( (lv_t_6_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_7= RULE_ANONYMOUS )? this_ARRAY_8= RULE_ARRAY ( (lv_a_9_0= rulearrayBounds ) ) this_OF_10= RULE_OF ( (lv_t_11_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_12= RULE_ANONYMOUS )? this_SET_13= RULE_SET this_OF_14= RULE_OF ( (lv_t_15_0= ruletypeReference ) ) ) | ( (this_ANONYMOUS_16= RULE_ANONYMOUS )? this_BAG_17= RULE_BAG this_OF_18= RULE_OF ( (lv_t_19_0= ruletypeReference ) ) ) | ( () (this_ANONYMOUS_21= RULE_ANONYMOUS )? this_DICTIONARY_22= RULE_DICTIONARY ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )? ) )
            int alt18=5;
            switch ( input.LA(1) ) {
            case RULE_ANONYMOUS:
                {
                switch ( input.LA(2) ) {
                case RULE_ARRAY:
                    {
                    alt18=2;
                    }
                    break;
                case RULE_SEQUENCE:
                    {
                    alt18=1;
                    }
                    break;
                case RULE_SET:
                    {
                    alt18=3;
                    }
                    break;
                case RULE_DICTIONARY:
                    {
                    alt18=5;
                    }
                    break;
                case RULE_BAG:
                    {
                    alt18=4;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }

                }
                break;
            case RULE_SEQUENCE:
                {
                alt18=1;
                }
                break;
            case RULE_ARRAY:
                {
                alt18=2;
                }
                break;
            case RULE_SET:
                {
                alt18=3;
                }
                break;
            case RULE_BAG:
                {
                alt18=4;
                }
                break;
            case RULE_DICTIONARY:
                {
                alt18=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalMASL.g:693:3: ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_SEQUENCE_1= RULE_SEQUENCE (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )? this_OF_5= RULE_OF ( (lv_t_6_0= ruletypeReference ) ) )
                    {
                    // InternalMASL.g:693:3: ( (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_SEQUENCE_1= RULE_SEQUENCE (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )? this_OF_5= RULE_OF ( (lv_t_6_0= ruletypeReference ) ) )
                    // InternalMASL.g:694:4: (this_ANONYMOUS_0= RULE_ANONYMOUS )? this_SEQUENCE_1= RULE_SEQUENCE (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )? this_OF_5= RULE_OF ( (lv_t_6_0= ruletypeReference ) )
                    {
                    // InternalMASL.g:694:4: (this_ANONYMOUS_0= RULE_ANONYMOUS )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==RULE_ANONYMOUS) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // InternalMASL.g:695:5: this_ANONYMOUS_0= RULE_ANONYMOUS
                            {
                            this_ANONYMOUS_0=(Token)match(input,RULE_ANONYMOUS,FOLLOW_10); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_ANONYMOUS_0, grammarAccess.getCollectionTypeRefAccess().getANONYMOUSTerminalRuleCall_0_0());
                              				
                            }

                            }
                            break;

                    }

                    this_SEQUENCE_1=(Token)match(input,RULE_SEQUENCE,FOLLOW_11); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SEQUENCE_1, grammarAccess.getCollectionTypeRefAccess().getSEQUENCETerminalRuleCall_0_1());
                      			
                    }
                    // InternalMASL.g:704:4: (this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==RULE_LPAREN) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalMASL.g:705:5: this_LPAREN_2= RULE_LPAREN ( (lv_e_3_0= ruleexpression ) ) this_RPAREN_4= RULE_RPAREN
                            {
                            this_LPAREN_2=(Token)match(input,RULE_LPAREN,FOLLOW_8); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_LPAREN_2, grammarAccess.getCollectionTypeRefAccess().getLPARENTerminalRuleCall_0_2_0());
                              				
                            }
                            // InternalMASL.g:709:5: ( (lv_e_3_0= ruleexpression ) )
                            // InternalMASL.g:710:6: (lv_e_3_0= ruleexpression )
                            {
                            // InternalMASL.g:710:6: (lv_e_3_0= ruleexpression )
                            // InternalMASL.g:711:7: lv_e_3_0= ruleexpression
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getEExpressionParserRuleCall_0_2_1_0());
                              						
                            }
                            pushFollow(FOLLOW_9);
                            lv_e_3_0=ruleexpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getCollectionTypeRefRule());
                              							}
                              							set(
                              								current,
                              								"e",
                              								lv_e_3_0,
                              								"org.xtuml.bp.ui.xtext.MASL.expression");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }

                            this_RPAREN_4=(Token)match(input,RULE_RPAREN,FOLLOW_6); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_RPAREN_4, grammarAccess.getCollectionTypeRefAccess().getRPARENTerminalRuleCall_0_2_2());
                              				
                            }

                            }
                            break;

                    }

                    this_OF_5=(Token)match(input,RULE_OF,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_OF_5, grammarAccess.getCollectionTypeRefAccess().getOFTerminalRuleCall_0_3());
                      			
                    }
                    // InternalMASL.g:737:4: ( (lv_t_6_0= ruletypeReference ) )
                    // InternalMASL.g:738:5: (lv_t_6_0= ruletypeReference )
                    {
                    // InternalMASL.g:738:5: (lv_t_6_0= ruletypeReference )
                    // InternalMASL.g:739:6: lv_t_6_0= ruletypeReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getTTypeReferenceParserRuleCall_0_4_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_t_6_0=ruletypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCollectionTypeRefRule());
                      						}
                      						set(
                      							current,
                      							"t",
                      							lv_t_6_0,
                      							"org.xtuml.bp.ui.xtext.MASL.typeReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:758:3: ( (this_ANONYMOUS_7= RULE_ANONYMOUS )? this_ARRAY_8= RULE_ARRAY ( (lv_a_9_0= rulearrayBounds ) ) this_OF_10= RULE_OF ( (lv_t_11_0= ruletypeReference ) ) )
                    {
                    // InternalMASL.g:758:3: ( (this_ANONYMOUS_7= RULE_ANONYMOUS )? this_ARRAY_8= RULE_ARRAY ( (lv_a_9_0= rulearrayBounds ) ) this_OF_10= RULE_OF ( (lv_t_11_0= ruletypeReference ) ) )
                    // InternalMASL.g:759:4: (this_ANONYMOUS_7= RULE_ANONYMOUS )? this_ARRAY_8= RULE_ARRAY ( (lv_a_9_0= rulearrayBounds ) ) this_OF_10= RULE_OF ( (lv_t_11_0= ruletypeReference ) )
                    {
                    // InternalMASL.g:759:4: (this_ANONYMOUS_7= RULE_ANONYMOUS )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==RULE_ANONYMOUS) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalMASL.g:760:5: this_ANONYMOUS_7= RULE_ANONYMOUS
                            {
                            this_ANONYMOUS_7=(Token)match(input,RULE_ANONYMOUS,FOLLOW_13); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_ANONYMOUS_7, grammarAccess.getCollectionTypeRefAccess().getANONYMOUSTerminalRuleCall_1_0());
                              				
                            }

                            }
                            break;

                    }

                    this_ARRAY_8=(Token)match(input,RULE_ARRAY,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_ARRAY_8, grammarAccess.getCollectionTypeRefAccess().getARRAYTerminalRuleCall_1_1());
                      			
                    }
                    // InternalMASL.g:769:4: ( (lv_a_9_0= rulearrayBounds ) )
                    // InternalMASL.g:770:5: (lv_a_9_0= rulearrayBounds )
                    {
                    // InternalMASL.g:770:5: (lv_a_9_0= rulearrayBounds )
                    // InternalMASL.g:771:6: lv_a_9_0= rulearrayBounds
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getAArrayBoundsParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_6);
                    lv_a_9_0=rulearrayBounds();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCollectionTypeRefRule());
                      						}
                      						set(
                      							current,
                      							"a",
                      							lv_a_9_0,
                      							"org.xtuml.bp.ui.xtext.MASL.arrayBounds");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    this_OF_10=(Token)match(input,RULE_OF,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_OF_10, grammarAccess.getCollectionTypeRefAccess().getOFTerminalRuleCall_1_3());
                      			
                    }
                    // InternalMASL.g:792:4: ( (lv_t_11_0= ruletypeReference ) )
                    // InternalMASL.g:793:5: (lv_t_11_0= ruletypeReference )
                    {
                    // InternalMASL.g:793:5: (lv_t_11_0= ruletypeReference )
                    // InternalMASL.g:794:6: lv_t_11_0= ruletypeReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getTTypeReferenceParserRuleCall_1_4_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_t_11_0=ruletypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCollectionTypeRefRule());
                      						}
                      						set(
                      							current,
                      							"t",
                      							lv_t_11_0,
                      							"org.xtuml.bp.ui.xtext.MASL.typeReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalMASL.g:813:3: ( (this_ANONYMOUS_12= RULE_ANONYMOUS )? this_SET_13= RULE_SET this_OF_14= RULE_OF ( (lv_t_15_0= ruletypeReference ) ) )
                    {
                    // InternalMASL.g:813:3: ( (this_ANONYMOUS_12= RULE_ANONYMOUS )? this_SET_13= RULE_SET this_OF_14= RULE_OF ( (lv_t_15_0= ruletypeReference ) ) )
                    // InternalMASL.g:814:4: (this_ANONYMOUS_12= RULE_ANONYMOUS )? this_SET_13= RULE_SET this_OF_14= RULE_OF ( (lv_t_15_0= ruletypeReference ) )
                    {
                    // InternalMASL.g:814:4: (this_ANONYMOUS_12= RULE_ANONYMOUS )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==RULE_ANONYMOUS) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // InternalMASL.g:815:5: this_ANONYMOUS_12= RULE_ANONYMOUS
                            {
                            this_ANONYMOUS_12=(Token)match(input,RULE_ANONYMOUS,FOLLOW_14); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_ANONYMOUS_12, grammarAccess.getCollectionTypeRefAccess().getANONYMOUSTerminalRuleCall_2_0());
                              				
                            }

                            }
                            break;

                    }

                    this_SET_13=(Token)match(input,RULE_SET,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SET_13, grammarAccess.getCollectionTypeRefAccess().getSETTerminalRuleCall_2_1());
                      			
                    }
                    this_OF_14=(Token)match(input,RULE_OF,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_OF_14, grammarAccess.getCollectionTypeRefAccess().getOFTerminalRuleCall_2_2());
                      			
                    }
                    // InternalMASL.g:828:4: ( (lv_t_15_0= ruletypeReference ) )
                    // InternalMASL.g:829:5: (lv_t_15_0= ruletypeReference )
                    {
                    // InternalMASL.g:829:5: (lv_t_15_0= ruletypeReference )
                    // InternalMASL.g:830:6: lv_t_15_0= ruletypeReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getTTypeReferenceParserRuleCall_2_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_t_15_0=ruletypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCollectionTypeRefRule());
                      						}
                      						set(
                      							current,
                      							"t",
                      							lv_t_15_0,
                      							"org.xtuml.bp.ui.xtext.MASL.typeReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalMASL.g:849:3: ( (this_ANONYMOUS_16= RULE_ANONYMOUS )? this_BAG_17= RULE_BAG this_OF_18= RULE_OF ( (lv_t_19_0= ruletypeReference ) ) )
                    {
                    // InternalMASL.g:849:3: ( (this_ANONYMOUS_16= RULE_ANONYMOUS )? this_BAG_17= RULE_BAG this_OF_18= RULE_OF ( (lv_t_19_0= ruletypeReference ) ) )
                    // InternalMASL.g:850:4: (this_ANONYMOUS_16= RULE_ANONYMOUS )? this_BAG_17= RULE_BAG this_OF_18= RULE_OF ( (lv_t_19_0= ruletypeReference ) )
                    {
                    // InternalMASL.g:850:4: (this_ANONYMOUS_16= RULE_ANONYMOUS )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==RULE_ANONYMOUS) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // InternalMASL.g:851:5: this_ANONYMOUS_16= RULE_ANONYMOUS
                            {
                            this_ANONYMOUS_16=(Token)match(input,RULE_ANONYMOUS,FOLLOW_15); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_ANONYMOUS_16, grammarAccess.getCollectionTypeRefAccess().getANONYMOUSTerminalRuleCall_3_0());
                              				
                            }

                            }
                            break;

                    }

                    this_BAG_17=(Token)match(input,RULE_BAG,FOLLOW_6); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_BAG_17, grammarAccess.getCollectionTypeRefAccess().getBAGTerminalRuleCall_3_1());
                      			
                    }
                    this_OF_18=(Token)match(input,RULE_OF,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_OF_18, grammarAccess.getCollectionTypeRefAccess().getOFTerminalRuleCall_3_2());
                      			
                    }
                    // InternalMASL.g:864:4: ( (lv_t_19_0= ruletypeReference ) )
                    // InternalMASL.g:865:5: (lv_t_19_0= ruletypeReference )
                    {
                    // InternalMASL.g:865:5: (lv_t_19_0= ruletypeReference )
                    // InternalMASL.g:866:6: lv_t_19_0= ruletypeReference
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getTTypeReferenceParserRuleCall_3_3_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_t_19_0=ruletypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCollectionTypeRefRule());
                      						}
                      						set(
                      							current,
                      							"t",
                      							lv_t_19_0,
                      							"org.xtuml.bp.ui.xtext.MASL.typeReference");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalMASL.g:885:3: ( () (this_ANONYMOUS_21= RULE_ANONYMOUS )? this_DICTIONARY_22= RULE_DICTIONARY ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )? )
                    {
                    // InternalMASL.g:885:3: ( () (this_ANONYMOUS_21= RULE_ANONYMOUS )? this_DICTIONARY_22= RULE_DICTIONARY ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )? )
                    // InternalMASL.g:886:4: () (this_ANONYMOUS_21= RULE_ANONYMOUS )? this_DICTIONARY_22= RULE_DICTIONARY ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )?
                    {
                    // InternalMASL.g:886:4: ()
                    // InternalMASL.g:887:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getCollectionTypeRefAccess().getCollectionTypeRefAction_4_0(),
                      						current);
                      				
                    }

                    }

                    // InternalMASL.g:893:4: (this_ANONYMOUS_21= RULE_ANONYMOUS )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==RULE_ANONYMOUS) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalMASL.g:894:5: this_ANONYMOUS_21= RULE_ANONYMOUS
                            {
                            this_ANONYMOUS_21=(Token)match(input,RULE_ANONYMOUS,FOLLOW_16); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_ANONYMOUS_21, grammarAccess.getCollectionTypeRefAccess().getANONYMOUSTerminalRuleCall_4_1());
                              				
                            }

                            }
                            break;

                    }

                    this_DICTIONARY_22=(Token)match(input,RULE_DICTIONARY,FOLLOW_17); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DICTIONARY_22, grammarAccess.getCollectionTypeRefAccess().getDICTIONARYTerminalRuleCall_4_2());
                      			
                    }
                    // InternalMASL.g:903:4: ( (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==RULE_INSTANCE||(LA17_0>=RULE_ANONYMOUS && LA17_0<=RULE_OF)||LA17_0==RULE_IDENT) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // InternalMASL.g:904:5: (this_dictKeyType_23= ruledictKeyType )? this_OF_24= RULE_OF ( (lv_d_25_0= ruledictValueType ) )
                            {
                            // InternalMASL.g:904:5: (this_dictKeyType_23= ruledictKeyType )?
                            int alt16=2;
                            int LA16_0 = input.LA(1);

                            if ( (LA16_0==RULE_INSTANCE||LA16_0==RULE_ANONYMOUS||LA16_0==RULE_IDENT) ) {
                                alt16=1;
                            }
                            switch (alt16) {
                                case 1 :
                                    // InternalMASL.g:905:6: this_dictKeyType_23= ruledictKeyType
                                    {
                                    if ( state.backtracking==0 ) {

                                      						newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getDictKeyTypeParserRuleCall_4_3_0());
                                      					
                                    }
                                    pushFollow(FOLLOW_6);
                                    this_dictKeyType_23=ruledictKeyType();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						current = this_dictKeyType_23;
                                      						afterParserOrEnumRuleCall();
                                      					
                                    }

                                    }
                                    break;

                            }

                            this_OF_24=(Token)match(input,RULE_OF,FOLLOW_12); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_OF_24, grammarAccess.getCollectionTypeRefAccess().getOFTerminalRuleCall_4_3_1());
                              				
                            }
                            // InternalMASL.g:918:5: ( (lv_d_25_0= ruledictValueType ) )
                            // InternalMASL.g:919:6: (lv_d_25_0= ruledictValueType )
                            {
                            // InternalMASL.g:919:6: (lv_d_25_0= ruledictValueType )
                            // InternalMASL.g:920:7: lv_d_25_0= ruledictValueType
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getCollectionTypeRefAccess().getDDictValueTypeParserRuleCall_4_3_2_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_d_25_0=ruledictValueType();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getCollectionTypeRefRule());
                              							}
                              							set(
                              								current,
                              								"d",
                              								lv_d_25_0,
                              								"org.xtuml.bp.ui.xtext.MASL.dictValueType");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecollectionTypeRef"


    // $ANTLR start "entryRuledictKeyType"
    // InternalMASL.g:943:1: entryRuledictKeyType returns [EObject current=null] : iv_ruledictKeyType= ruledictKeyType EOF ;
    public final EObject entryRuledictKeyType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledictKeyType = null;


        try {
            // InternalMASL.g:943:52: (iv_ruledictKeyType= ruledictKeyType EOF )
            // InternalMASL.g:944:2: iv_ruledictKeyType= ruledictKeyType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDictKeyTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledictKeyType=ruledictKeyType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledictKeyType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledictKeyType"


    // $ANTLR start "ruledictKeyType"
    // InternalMASL.g:950:1: ruledictKeyType returns [EObject current=null] : ( ( (lv_n_0_0= rulenamedTypeRef ) ) | ( (lv_i_1_0= ruleinstanceTypeRef ) ) ) ;
    public final EObject ruledictKeyType() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_n_0_0 = null;

        AntlrDatatypeRuleToken lv_i_1_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:956:2: ( ( ( (lv_n_0_0= rulenamedTypeRef ) ) | ( (lv_i_1_0= ruleinstanceTypeRef ) ) ) )
            // InternalMASL.g:957:2: ( ( (lv_n_0_0= rulenamedTypeRef ) ) | ( (lv_i_1_0= ruleinstanceTypeRef ) ) )
            {
            // InternalMASL.g:957:2: ( ( (lv_n_0_0= rulenamedTypeRef ) ) | ( (lv_i_1_0= ruleinstanceTypeRef ) ) )
            int alt19=2;
            switch ( input.LA(1) ) {
            case RULE_ANONYMOUS:
                {
                int LA19_1 = input.LA(2);

                if ( (LA19_1==RULE_IDENT) ) {
                    alt19=1;
                }
                else if ( (LA19_1==RULE_INSTANCE) ) {
                    alt19=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_IDENT:
                {
                alt19=1;
                }
                break;
            case RULE_INSTANCE:
                {
                alt19=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalMASL.g:958:3: ( (lv_n_0_0= rulenamedTypeRef ) )
                    {
                    // InternalMASL.g:958:3: ( (lv_n_0_0= rulenamedTypeRef ) )
                    // InternalMASL.g:959:4: (lv_n_0_0= rulenamedTypeRef )
                    {
                    // InternalMASL.g:959:4: (lv_n_0_0= rulenamedTypeRef )
                    // InternalMASL.g:960:5: lv_n_0_0= rulenamedTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDictKeyTypeAccess().getNNamedTypeRefParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_n_0_0=rulenamedTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getDictKeyTypeRule());
                      					}
                      					set(
                      						current,
                      						"n",
                      						lv_n_0_0,
                      						"org.xtuml.bp.ui.xtext.MASL.namedTypeRef");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:978:3: ( (lv_i_1_0= ruleinstanceTypeRef ) )
                    {
                    // InternalMASL.g:978:3: ( (lv_i_1_0= ruleinstanceTypeRef ) )
                    // InternalMASL.g:979:4: (lv_i_1_0= ruleinstanceTypeRef )
                    {
                    // InternalMASL.g:979:4: (lv_i_1_0= ruleinstanceTypeRef )
                    // InternalMASL.g:980:5: lv_i_1_0= ruleinstanceTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getDictKeyTypeAccess().getIInstanceTypeRefParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_i_1_0=ruleinstanceTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getDictKeyTypeRule());
                      					}
                      					set(
                      						current,
                      						"i",
                      						lv_i_1_0,
                      						"org.xtuml.bp.ui.xtext.MASL.instanceTypeRef");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledictKeyType"


    // $ANTLR start "entryRuledictValueType"
    // InternalMASL.g:1001:1: entryRuledictValueType returns [EObject current=null] : iv_ruledictValueType= ruledictValueType EOF ;
    public final EObject entryRuledictValueType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledictValueType = null;


        try {
            // InternalMASL.g:1001:54: (iv_ruledictValueType= ruledictValueType EOF )
            // InternalMASL.g:1002:2: iv_ruledictValueType= ruledictValueType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDictValueTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledictValueType=ruledictValueType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledictValueType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledictValueType"


    // $ANTLR start "ruledictValueType"
    // InternalMASL.g:1008:1: ruledictValueType returns [EObject current=null] : ( (lv_t_0_0= ruletypeReference ) ) ;
    public final EObject ruledictValueType() throws RecognitionException {
        EObject current = null;

        EObject lv_t_0_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1014:2: ( ( (lv_t_0_0= ruletypeReference ) ) )
            // InternalMASL.g:1015:2: ( (lv_t_0_0= ruletypeReference ) )
            {
            // InternalMASL.g:1015:2: ( (lv_t_0_0= ruletypeReference ) )
            // InternalMASL.g:1016:3: (lv_t_0_0= ruletypeReference )
            {
            // InternalMASL.g:1016:3: (lv_t_0_0= ruletypeReference )
            // InternalMASL.g:1017:4: lv_t_0_0= ruletypeReference
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getDictValueTypeAccess().getTTypeReferenceParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_t_0_0=ruletypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getDictValueTypeRule());
              				}
              				set(
              					current,
              					"t",
              					lv_t_0_0,
              					"org.xtuml.bp.ui.xtext.MASL.typeReference");
              				afterParserOrEnumRuleCall();
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledictValueType"


    // $ANTLR start "entryRuleterminatorName"
    // InternalMASL.g:1037:1: entryRuleterminatorName returns [String current=null] : iv_ruleterminatorName= ruleterminatorName EOF ;
    public final String entryRuleterminatorName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleterminatorName = null;


        try {
            // InternalMASL.g:1037:54: (iv_ruleterminatorName= ruleterminatorName EOF )
            // InternalMASL.g:1038:2: iv_ruleterminatorName= ruleterminatorName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTerminatorNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleterminatorName=ruleterminatorName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleterminatorName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleterminatorName"


    // $ANTLR start "ruleterminatorName"
    // InternalMASL.g:1044:1: ruleterminatorName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruleterminatorName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1050:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1051:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getTerminatorNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleterminatorName"


    // $ANTLR start "entryRuleobjectName"
    // InternalMASL.g:1064:1: entryRuleobjectName returns [String current=null] : iv_ruleobjectName= ruleobjectName EOF ;
    public final String entryRuleobjectName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleobjectName = null;


        try {
            // InternalMASL.g:1064:50: (iv_ruleobjectName= ruleobjectName EOF )
            // InternalMASL.g:1065:2: iv_ruleobjectName= ruleobjectName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getObjectNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleobjectName=ruleobjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleobjectName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleobjectName"


    // $ANTLR start "ruleobjectName"
    // InternalMASL.g:1071:1: ruleobjectName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruleobjectName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1077:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1078:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getObjectNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleobjectName"


    // $ANTLR start "entryRuleattributeName"
    // InternalMASL.g:1091:1: entryRuleattributeName returns [String current=null] : iv_ruleattributeName= ruleattributeName EOF ;
    public final String entryRuleattributeName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleattributeName = null;


        try {
            // InternalMASL.g:1091:53: (iv_ruleattributeName= ruleattributeName EOF )
            // InternalMASL.g:1092:2: iv_ruleattributeName= ruleattributeName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAttributeNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleattributeName=ruleattributeName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleattributeName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleattributeName"


    // $ANTLR start "ruleattributeName"
    // InternalMASL.g:1098:1: ruleattributeName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruleattributeName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1104:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1105:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getAttributeNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleattributeName"


    // $ANTLR start "entryRulerelationshipSpec"
    // InternalMASL.g:1118:1: entryRulerelationshipSpec returns [EObject current=null] : iv_rulerelationshipSpec= rulerelationshipSpec EOF ;
    public final EObject entryRulerelationshipSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_rulerelationshipSpec = null;


        try {
            // InternalMASL.g:1118:57: (iv_rulerelationshipSpec= rulerelationshipSpec EOF )
            // InternalMASL.g:1119:2: iv_rulerelationshipSpec= rulerelationshipSpec EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRelationshipSpecRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulerelationshipSpec=rulerelationshipSpec();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulerelationshipSpec; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulerelationshipSpec"


    // $ANTLR start "rulerelationshipSpec"
    // InternalMASL.g:1125:1: rulerelationshipSpec returns [EObject current=null] : ( ( (lv_r_0_0= RULE_RELATIONSHIPNAME ) ) (this_DOT_1= RULE_DOT ( (lv_r_2_0= ruleidentifier ) ) (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )? )? ) ;
    public final EObject rulerelationshipSpec() throws RecognitionException {
        EObject current = null;

        Token lv_r_0_0=null;
        Token this_DOT_1=null;
        Token this_DOT_3=null;
        AntlrDatatypeRuleToken lv_r_2_0 = null;

        AntlrDatatypeRuleToken lv_r_4_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1131:2: ( ( ( (lv_r_0_0= RULE_RELATIONSHIPNAME ) ) (this_DOT_1= RULE_DOT ( (lv_r_2_0= ruleidentifier ) ) (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )? )? ) )
            // InternalMASL.g:1132:2: ( ( (lv_r_0_0= RULE_RELATIONSHIPNAME ) ) (this_DOT_1= RULE_DOT ( (lv_r_2_0= ruleidentifier ) ) (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )? )? )
            {
            // InternalMASL.g:1132:2: ( ( (lv_r_0_0= RULE_RELATIONSHIPNAME ) ) (this_DOT_1= RULE_DOT ( (lv_r_2_0= ruleidentifier ) ) (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )? )? )
            // InternalMASL.g:1133:3: ( (lv_r_0_0= RULE_RELATIONSHIPNAME ) ) (this_DOT_1= RULE_DOT ( (lv_r_2_0= ruleidentifier ) ) (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )? )?
            {
            // InternalMASL.g:1133:3: ( (lv_r_0_0= RULE_RELATIONSHIPNAME ) )
            // InternalMASL.g:1134:4: (lv_r_0_0= RULE_RELATIONSHIPNAME )
            {
            // InternalMASL.g:1134:4: (lv_r_0_0= RULE_RELATIONSHIPNAME )
            // InternalMASL.g:1135:5: lv_r_0_0= RULE_RELATIONSHIPNAME
            {
            lv_r_0_0=(Token)match(input,RULE_RELATIONSHIPNAME,FOLLOW_18); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_r_0_0, grammarAccess.getRelationshipSpecAccess().getRRELATIONSHIPNAMETerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getRelationshipSpecRule());
              					}
              					addWithLastConsumed(
              						current,
              						"r",
              						lv_r_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.RELATIONSHIPNAME");
              				
            }

            }


            }

            // InternalMASL.g:1151:3: (this_DOT_1= RULE_DOT ( (lv_r_2_0= ruleidentifier ) ) (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )? )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_DOT) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMASL.g:1152:4: this_DOT_1= RULE_DOT ( (lv_r_2_0= ruleidentifier ) ) (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )?
                    {
                    this_DOT_1=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DOT_1, grammarAccess.getRelationshipSpecAccess().getDOTTerminalRuleCall_1_0());
                      			
                    }
                    // InternalMASL.g:1156:4: ( (lv_r_2_0= ruleidentifier ) )
                    // InternalMASL.g:1157:5: (lv_r_2_0= ruleidentifier )
                    {
                    // InternalMASL.g:1157:5: (lv_r_2_0= ruleidentifier )
                    // InternalMASL.g:1158:6: lv_r_2_0= ruleidentifier
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getRelationshipSpecAccess().getRIdentifierParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_18);
                    lv_r_2_0=ruleidentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getRelationshipSpecRule());
                      						}
                      						add(
                      							current,
                      							"r",
                      							lv_r_2_0,
                      							"org.xtuml.bp.ui.xtext.MASL.identifier");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalMASL.g:1175:4: (this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) ) )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==RULE_DOT) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // InternalMASL.g:1176:5: this_DOT_3= RULE_DOT ( (lv_r_4_0= ruleobjectName ) )
                            {
                            this_DOT_3=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_DOT_3, grammarAccess.getRelationshipSpecAccess().getDOTTerminalRuleCall_1_2_0());
                              				
                            }
                            // InternalMASL.g:1180:5: ( (lv_r_4_0= ruleobjectName ) )
                            // InternalMASL.g:1181:6: (lv_r_4_0= ruleobjectName )
                            {
                            // InternalMASL.g:1181:6: (lv_r_4_0= ruleobjectName )
                            // InternalMASL.g:1182:7: lv_r_4_0= ruleobjectName
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getRelationshipSpecAccess().getRObjectNameParserRuleCall_1_2_1_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_r_4_0=ruleobjectName();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getRelationshipSpecRule());
                              							}
                              							add(
                              								current,
                              								"r",
                              								lv_r_4_0,
                              								"org.xtuml.bp.ui.xtext.MASL.objectName");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulerelationshipSpec"


    // $ANTLR start "entryRuleserviceType"
    // InternalMASL.g:1205:1: entryRuleserviceType returns [String current=null] : iv_ruleserviceType= ruleserviceType EOF ;
    public final String entryRuleserviceType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleserviceType = null;


        try {
            // InternalMASL.g:1205:51: (iv_ruleserviceType= ruleserviceType EOF )
            // InternalMASL.g:1206:2: iv_ruleserviceType= ruleserviceType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getServiceTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleserviceType=ruleserviceType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleserviceType.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleserviceType"


    // $ANTLR start "ruleserviceType"
    // InternalMASL.g:1212:1: ruleserviceType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INSTANCE_0= RULE_INSTANCE (this_DEFERRED_1= RULE_DEFERRED this_LPAREN_2= RULE_LPAREN this_RELATIONSHIPNAME_3= RULE_RELATIONSHIPNAME this_RPAREN_4= RULE_RPAREN )? )? ;
    public final AntlrDatatypeRuleToken ruleserviceType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INSTANCE_0=null;
        Token this_DEFERRED_1=null;
        Token this_LPAREN_2=null;
        Token this_RELATIONSHIPNAME_3=null;
        Token this_RPAREN_4=null;


        	enterRule();

        try {
            // InternalMASL.g:1218:2: ( (this_INSTANCE_0= RULE_INSTANCE (this_DEFERRED_1= RULE_DEFERRED this_LPAREN_2= RULE_LPAREN this_RELATIONSHIPNAME_3= RULE_RELATIONSHIPNAME this_RPAREN_4= RULE_RPAREN )? )? )
            // InternalMASL.g:1219:2: (this_INSTANCE_0= RULE_INSTANCE (this_DEFERRED_1= RULE_DEFERRED this_LPAREN_2= RULE_LPAREN this_RELATIONSHIPNAME_3= RULE_RELATIONSHIPNAME this_RPAREN_4= RULE_RPAREN )? )?
            {
            // InternalMASL.g:1219:2: (this_INSTANCE_0= RULE_INSTANCE (this_DEFERRED_1= RULE_DEFERRED this_LPAREN_2= RULE_LPAREN this_RELATIONSHIPNAME_3= RULE_RELATIONSHIPNAME this_RPAREN_4= RULE_RPAREN )? )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_INSTANCE) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMASL.g:1220:3: this_INSTANCE_0= RULE_INSTANCE (this_DEFERRED_1= RULE_DEFERRED this_LPAREN_2= RULE_LPAREN this_RELATIONSHIPNAME_3= RULE_RELATIONSHIPNAME this_RPAREN_4= RULE_RPAREN )?
                    {
                    this_INSTANCE_0=(Token)match(input,RULE_INSTANCE,FOLLOW_19); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_INSTANCE_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_INSTANCE_0, grammarAccess.getServiceTypeAccess().getINSTANCETerminalRuleCall_0());
                      		
                    }
                    // InternalMASL.g:1227:3: (this_DEFERRED_1= RULE_DEFERRED this_LPAREN_2= RULE_LPAREN this_RELATIONSHIPNAME_3= RULE_RELATIONSHIPNAME this_RPAREN_4= RULE_RPAREN )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==RULE_DEFERRED) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // InternalMASL.g:1228:4: this_DEFERRED_1= RULE_DEFERRED this_LPAREN_2= RULE_LPAREN this_RELATIONSHIPNAME_3= RULE_RELATIONSHIPNAME this_RPAREN_4= RULE_RPAREN
                            {
                            this_DEFERRED_1=(Token)match(input,RULE_DEFERRED,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current.merge(this_DEFERRED_1);
                              			
                            }
                            if ( state.backtracking==0 ) {

                              				newLeafNode(this_DEFERRED_1, grammarAccess.getServiceTypeAccess().getDEFERREDTerminalRuleCall_1_0());
                              			
                            }
                            this_LPAREN_2=(Token)match(input,RULE_LPAREN,FOLLOW_20); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current.merge(this_LPAREN_2);
                              			
                            }
                            if ( state.backtracking==0 ) {

                              				newLeafNode(this_LPAREN_2, grammarAccess.getServiceTypeAccess().getLPARENTerminalRuleCall_1_1());
                              			
                            }
                            this_RELATIONSHIPNAME_3=(Token)match(input,RULE_RELATIONSHIPNAME,FOLLOW_9); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current.merge(this_RELATIONSHIPNAME_3);
                              			
                            }
                            if ( state.backtracking==0 ) {

                              				newLeafNode(this_RELATIONSHIPNAME_3, grammarAccess.getServiceTypeAccess().getRELATIONSHIPNAMETerminalRuleCall_1_2());
                              			
                            }
                            this_RPAREN_4=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current.merge(this_RPAREN_4);
                              			
                            }
                            if ( state.backtracking==0 ) {

                              				newLeafNode(this_RPAREN_4, grammarAccess.getServiceTypeAccess().getRPARENTerminalRuleCall_1_3());
                              			
                            }

                            }
                            break;

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleserviceType"


    // $ANTLR start "entryRuleeventName"
    // InternalMASL.g:1261:1: entryRuleeventName returns [String current=null] : iv_ruleeventName= ruleeventName EOF ;
    public final String entryRuleeventName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleeventName = null;


        try {
            // InternalMASL.g:1261:49: (iv_ruleeventName= ruleeventName EOF )
            // InternalMASL.g:1262:2: iv_ruleeventName= ruleeventName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEventNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleeventName=ruleeventName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleeventName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleeventName"


    // $ANTLR start "ruleeventName"
    // InternalMASL.g:1268:1: ruleeventName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruleeventName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1274:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1275:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getEventNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleeventName"


    // $ANTLR start "entryRulestateName"
    // InternalMASL.g:1288:1: entryRulestateName returns [String current=null] : iv_rulestateName= rulestateName EOF ;
    public final String entryRulestateName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulestateName = null;


        try {
            // InternalMASL.g:1288:49: (iv_rulestateName= rulestateName EOF )
            // InternalMASL.g:1289:2: iv_rulestateName= rulestateName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStateNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestateName=rulestateName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestateName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestateName"


    // $ANTLR start "rulestateName"
    // InternalMASL.g:1295:1: rulestateName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken rulestateName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1301:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1302:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getStateNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestateName"


    // $ANTLR start "entryRulestateType"
    // InternalMASL.g:1315:1: entryRulestateType returns [EObject current=null] : iv_rulestateType= rulestateType EOF ;
    public final EObject entryRulestateType() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestateType = null;


        try {
            // InternalMASL.g:1315:50: (iv_rulestateType= rulestateType EOF )
            // InternalMASL.g:1316:2: iv_rulestateType= rulestateType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStateTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestateType=rulestateType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestateType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestateType"


    // $ANTLR start "rulestateType"
    // InternalMASL.g:1322:1: rulestateType returns [EObject current=null] : ( () ( ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) ) )? ) ;
    public final EObject rulestateType() throws RecognitionException {
        EObject current = null;

        Token lv_s_1_1=null;
        Token lv_s_1_3=null;
        Token lv_s_1_4=null;
        AntlrDatatypeRuleToken lv_s_1_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:1328:2: ( ( () ( ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) ) )? ) )
            // InternalMASL.g:1329:2: ( () ( ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) ) )? )
            {
            // InternalMASL.g:1329:2: ( () ( ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) ) )? )
            // InternalMASL.g:1330:3: () ( ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) ) )?
            {
            // InternalMASL.g:1330:3: ()
            // InternalMASL.g:1331:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getStateTypeAccess().getStateTypeAction_0(),
              					current);
              			
            }

            }

            // InternalMASL.g:1337:3: ( ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=RULE_ASSIGNER && LA25_0<=RULE_TERMINAL)) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMASL.g:1338:4: ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) )
                    {
                    // InternalMASL.g:1338:4: ( (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL ) )
                    // InternalMASL.g:1339:5: (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL )
                    {
                    // InternalMASL.g:1339:5: (lv_s_1_1= RULE_ASSIGNER | lv_s_1_2= ruleassignerStart | lv_s_1_3= RULE_CREATION | lv_s_1_4= RULE_TERMINAL )
                    int alt24=4;
                    switch ( input.LA(1) ) {
                    case RULE_ASSIGNER:
                        {
                        int LA24_1 = input.LA(2);

                        if ( (LA24_1==EOF||LA24_1==RULE_STATE) ) {
                            alt24=1;
                        }
                        else if ( (LA24_1==RULE_START) ) {
                            alt24=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 24, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case RULE_CREATION:
                        {
                        alt24=3;
                        }
                        break;
                    case RULE_TERMINAL:
                        {
                        alt24=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 24, 0, input);

                        throw nvae;
                    }

                    switch (alt24) {
                        case 1 :
                            // InternalMASL.g:1340:6: lv_s_1_1= RULE_ASSIGNER
                            {
                            lv_s_1_1=(Token)match(input,RULE_ASSIGNER,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_s_1_1, grammarAccess.getStateTypeAccess().getSASSIGNERTerminalRuleCall_1_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getStateTypeRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"s",
                              							lv_s_1_1,
                              							"org.xtuml.bp.ui.xtext.MASL.ASSIGNER");
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalMASL.g:1355:6: lv_s_1_2= ruleassignerStart
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getStateTypeAccess().getSAssignerStartParserRuleCall_1_0_1());
                              					
                            }
                            pushFollow(FOLLOW_2);
                            lv_s_1_2=ruleassignerStart();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getStateTypeRule());
                              						}
                              						set(
                              							current,
                              							"s",
                              							lv_s_1_2,
                              							"org.xtuml.bp.ui.xtext.MASL.assignerStart");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }
                            break;
                        case 3 :
                            // InternalMASL.g:1371:6: lv_s_1_3= RULE_CREATION
                            {
                            lv_s_1_3=(Token)match(input,RULE_CREATION,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_s_1_3, grammarAccess.getStateTypeAccess().getSCREATIONTerminalRuleCall_1_0_2());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getStateTypeRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"s",
                              							lv_s_1_3,
                              							"org.xtuml.bp.ui.xtext.MASL.CREATION");
                              					
                            }

                            }
                            break;
                        case 4 :
                            // InternalMASL.g:1386:6: lv_s_1_4= RULE_TERMINAL
                            {
                            lv_s_1_4=(Token)match(input,RULE_TERMINAL,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_s_1_4, grammarAccess.getStateTypeAccess().getSTERMINALTerminalRuleCall_1_0_3());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getStateTypeRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"s",
                              							lv_s_1_4,
                              							"org.xtuml.bp.ui.xtext.MASL.TERMINAL");
                              					
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestateType"


    // $ANTLR start "entryRuleassignerStart"
    // InternalMASL.g:1407:1: entryRuleassignerStart returns [String current=null] : iv_ruleassignerStart= ruleassignerStart EOF ;
    public final String entryRuleassignerStart() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleassignerStart = null;


        try {
            // InternalMASL.g:1407:53: (iv_ruleassignerStart= ruleassignerStart EOF )
            // InternalMASL.g:1408:2: iv_ruleassignerStart= ruleassignerStart EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAssignerStartRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleassignerStart=ruleassignerStart();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleassignerStart.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleassignerStart"


    // $ANTLR start "ruleassignerStart"
    // InternalMASL.g:1414:1: ruleassignerStart returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ASSIGNER_0= RULE_ASSIGNER this_START_1= RULE_START ) ;
    public final AntlrDatatypeRuleToken ruleassignerStart() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ASSIGNER_0=null;
        Token this_START_1=null;


        	enterRule();

        try {
            // InternalMASL.g:1420:2: ( (this_ASSIGNER_0= RULE_ASSIGNER this_START_1= RULE_START ) )
            // InternalMASL.g:1421:2: (this_ASSIGNER_0= RULE_ASSIGNER this_START_1= RULE_START )
            {
            // InternalMASL.g:1421:2: (this_ASSIGNER_0= RULE_ASSIGNER this_START_1= RULE_START )
            // InternalMASL.g:1422:3: this_ASSIGNER_0= RULE_ASSIGNER this_START_1= RULE_START
            {
            this_ASSIGNER_0=(Token)match(input,RULE_ASSIGNER,FOLLOW_21); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ASSIGNER_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ASSIGNER_0, grammarAccess.getAssignerStartAccess().getASSIGNERTerminalRuleCall_0());
              		
            }
            this_START_1=(Token)match(input,RULE_START,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_START_1);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_START_1, grammarAccess.getAssignerStartAccess().getSTARTTerminalRuleCall_1());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleassignerStart"


    // $ANTLR start "entryRuleparameterList"
    // InternalMASL.g:1440:1: entryRuleparameterList returns [EObject current=null] : iv_ruleparameterList= ruleparameterList EOF ;
    public final EObject entryRuleparameterList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleparameterList = null;


        try {
            // InternalMASL.g:1440:54: (iv_ruleparameterList= ruleparameterList EOF )
            // InternalMASL.g:1441:2: iv_ruleparameterList= ruleparameterList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleparameterList=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleparameterList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleparameterList"


    // $ANTLR start "ruleparameterList"
    // InternalMASL.g:1447:1: ruleparameterList returns [EObject current=null] : ( () this_LPAREN_1= RULE_LPAREN ( (lv_p_2_0= ruleparameterDefinition ) )? (this_COMMA_3= RULE_COMMA ( (lv_p_4_0= ruleparameterDefinition ) ) )* this_RPAREN_5= RULE_RPAREN ) ;
    public final EObject ruleparameterList() throws RecognitionException {
        EObject current = null;

        Token this_LPAREN_1=null;
        Token this_COMMA_3=null;
        Token this_RPAREN_5=null;
        EObject lv_p_2_0 = null;

        EObject lv_p_4_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1453:2: ( ( () this_LPAREN_1= RULE_LPAREN ( (lv_p_2_0= ruleparameterDefinition ) )? (this_COMMA_3= RULE_COMMA ( (lv_p_4_0= ruleparameterDefinition ) ) )* this_RPAREN_5= RULE_RPAREN ) )
            // InternalMASL.g:1454:2: ( () this_LPAREN_1= RULE_LPAREN ( (lv_p_2_0= ruleparameterDefinition ) )? (this_COMMA_3= RULE_COMMA ( (lv_p_4_0= ruleparameterDefinition ) ) )* this_RPAREN_5= RULE_RPAREN )
            {
            // InternalMASL.g:1454:2: ( () this_LPAREN_1= RULE_LPAREN ( (lv_p_2_0= ruleparameterDefinition ) )? (this_COMMA_3= RULE_COMMA ( (lv_p_4_0= ruleparameterDefinition ) ) )* this_RPAREN_5= RULE_RPAREN )
            // InternalMASL.g:1455:3: () this_LPAREN_1= RULE_LPAREN ( (lv_p_2_0= ruleparameterDefinition ) )? (this_COMMA_3= RULE_COMMA ( (lv_p_4_0= ruleparameterDefinition ) ) )* this_RPAREN_5= RULE_RPAREN
            {
            // InternalMASL.g:1455:3: ()
            // InternalMASL.g:1456:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getParameterListAccess().getParameterListAction_0(),
              					current);
              			
            }

            }

            this_LPAREN_1=(Token)match(input,RULE_LPAREN,FOLLOW_22); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_1, grammarAccess.getParameterListAccess().getLPARENTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:1466:3: ( (lv_p_2_0= ruleparameterDefinition ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_IDENT) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMASL.g:1467:4: (lv_p_2_0= ruleparameterDefinition )
                    {
                    // InternalMASL.g:1467:4: (lv_p_2_0= ruleparameterDefinition )
                    // InternalMASL.g:1468:5: lv_p_2_0= ruleparameterDefinition
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getParameterListAccess().getPParameterDefinitionParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_23);
                    lv_p_2_0=ruleparameterDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getParameterListRule());
                      					}
                      					add(
                      						current,
                      						"p",
                      						lv_p_2_0,
                      						"org.xtuml.bp.ui.xtext.MASL.parameterDefinition");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalMASL.g:1485:3: (this_COMMA_3= RULE_COMMA ( (lv_p_4_0= ruleparameterDefinition ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==RULE_COMMA) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // InternalMASL.g:1486:4: this_COMMA_3= RULE_COMMA ( (lv_p_4_0= ruleparameterDefinition ) )
            	    {
            	    this_COMMA_3=(Token)match(input,RULE_COMMA,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_COMMA_3, grammarAccess.getParameterListAccess().getCOMMATerminalRuleCall_3_0());
            	      			
            	    }
            	    // InternalMASL.g:1490:4: ( (lv_p_4_0= ruleparameterDefinition ) )
            	    // InternalMASL.g:1491:5: (lv_p_4_0= ruleparameterDefinition )
            	    {
            	    // InternalMASL.g:1491:5: (lv_p_4_0= ruleparameterDefinition )
            	    // InternalMASL.g:1492:6: lv_p_4_0= ruleparameterDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParameterListAccess().getPParameterDefinitionParserRuleCall_3_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_23);
            	    lv_p_4_0=ruleparameterDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getParameterListRule());
            	      						}
            	      						add(
            	      							current,
            	      							"p",
            	      							lv_p_4_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.parameterDefinition");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            this_RPAREN_5=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_5, grammarAccess.getParameterListAccess().getRPARENTerminalRuleCall_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleparameterList"


    // $ANTLR start "entryRuleparameterDefinition"
    // InternalMASL.g:1518:1: entryRuleparameterDefinition returns [EObject current=null] : iv_ruleparameterDefinition= ruleparameterDefinition EOF ;
    public final EObject entryRuleparameterDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleparameterDefinition = null;


        try {
            // InternalMASL.g:1518:60: (iv_ruleparameterDefinition= ruleparameterDefinition EOF )
            // InternalMASL.g:1519:2: iv_ruleparameterDefinition= ruleparameterDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleparameterDefinition=ruleparameterDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleparameterDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleparameterDefinition"


    // $ANTLR start "ruleparameterDefinition"
    // InternalMASL.g:1525:1: ruleparameterDefinition returns [EObject current=null] : ( ruleparameterName this_COLON_1= RULE_COLON ruleparameterMode this_parameterType_3= ruleparameterType ) ;
    public final EObject ruleparameterDefinition() throws RecognitionException {
        EObject current = null;

        Token this_COLON_1=null;
        EObject this_parameterType_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:1531:2: ( ( ruleparameterName this_COLON_1= RULE_COLON ruleparameterMode this_parameterType_3= ruleparameterType ) )
            // InternalMASL.g:1532:2: ( ruleparameterName this_COLON_1= RULE_COLON ruleparameterMode this_parameterType_3= ruleparameterType )
            {
            // InternalMASL.g:1532:2: ( ruleparameterName this_COLON_1= RULE_COLON ruleparameterMode this_parameterType_3= ruleparameterType )
            // InternalMASL.g:1533:3: ruleparameterName this_COLON_1= RULE_COLON ruleparameterMode this_parameterType_3= ruleparameterType
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getParameterDefinitionAccess().getParameterNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_24);
            ruleparameterName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_COLON_1=(Token)match(input,RULE_COLON,FOLLOW_25); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_COLON_1, grammarAccess.getParameterDefinitionAccess().getCOLONTerminalRuleCall_1());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getParameterDefinitionAccess().getParameterModeParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_12);
            ruleparameterMode();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getParameterDefinitionAccess().getParameterTypeParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_parameterType_3=ruleparameterType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_parameterType_3;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleparameterDefinition"


    // $ANTLR start "entryRuleserviceVisibility"
    // InternalMASL.g:1563:1: entryRuleserviceVisibility returns [EObject current=null] : iv_ruleserviceVisibility= ruleserviceVisibility EOF ;
    public final EObject entryRuleserviceVisibility() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleserviceVisibility = null;


        try {
            // InternalMASL.g:1563:58: (iv_ruleserviceVisibility= ruleserviceVisibility EOF )
            // InternalMASL.g:1564:2: iv_ruleserviceVisibility= ruleserviceVisibility EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getServiceVisibilityRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleserviceVisibility=ruleserviceVisibility();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleserviceVisibility; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleserviceVisibility"


    // $ANTLR start "ruleserviceVisibility"
    // InternalMASL.g:1570:1: ruleserviceVisibility returns [EObject current=null] : ( () ( ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) ) )? ) ;
    public final EObject ruleserviceVisibility() throws RecognitionException {
        EObject current = null;

        Token lv_v_1_1=null;
        Token lv_v_1_2=null;


        	enterRule();

        try {
            // InternalMASL.g:1576:2: ( ( () ( ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) ) )? ) )
            // InternalMASL.g:1577:2: ( () ( ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) ) )? )
            {
            // InternalMASL.g:1577:2: ( () ( ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) ) )? )
            // InternalMASL.g:1578:3: () ( ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) ) )?
            {
            // InternalMASL.g:1578:3: ()
            // InternalMASL.g:1579:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getServiceVisibilityAccess().getServiceVisibilityAction_0(),
              					current);
              			
            }

            }

            // InternalMASL.g:1585:3: ( ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( ((LA29_0>=RULE_PRIVATE && LA29_0<=RULE_PUBLIC)) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMASL.g:1586:4: ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) )
                    {
                    // InternalMASL.g:1586:4: ( (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC ) )
                    // InternalMASL.g:1587:5: (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC )
                    {
                    // InternalMASL.g:1587:5: (lv_v_1_1= RULE_PRIVATE | lv_v_1_2= RULE_PUBLIC )
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==RULE_PRIVATE) ) {
                        alt28=1;
                    }
                    else if ( (LA28_0==RULE_PUBLIC) ) {
                        alt28=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 0, input);

                        throw nvae;
                    }
                    switch (alt28) {
                        case 1 :
                            // InternalMASL.g:1588:6: lv_v_1_1= RULE_PRIVATE
                            {
                            lv_v_1_1=(Token)match(input,RULE_PRIVATE,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_v_1_1, grammarAccess.getServiceVisibilityAccess().getVPRIVATETerminalRuleCall_1_0_0());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getServiceVisibilityRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"v",
                              							lv_v_1_1,
                              							"org.xtuml.bp.ui.xtext.MASL.PRIVATE");
                              					
                            }

                            }
                            break;
                        case 2 :
                            // InternalMASL.g:1603:6: lv_v_1_2= RULE_PUBLIC
                            {
                            lv_v_1_2=(Token)match(input,RULE_PUBLIC,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(lv_v_1_2, grammarAccess.getServiceVisibilityAccess().getVPUBLICTerminalRuleCall_1_0_1());
                              					
                            }
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElement(grammarAccess.getServiceVisibilityRule());
                              						}
                              						setWithLastConsumed(
                              							current,
                              							"v",
                              							lv_v_1_2,
                              							"org.xtuml.bp.ui.xtext.MASL.PUBLIC");
                              					
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleserviceVisibility"


    // $ANTLR start "entryRuleparameterMode"
    // InternalMASL.g:1624:1: entryRuleparameterMode returns [String current=null] : iv_ruleparameterMode= ruleparameterMode EOF ;
    public final String entryRuleparameterMode() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleparameterMode = null;


        try {
            // InternalMASL.g:1624:53: (iv_ruleparameterMode= ruleparameterMode EOF )
            // InternalMASL.g:1625:2: iv_ruleparameterMode= ruleparameterMode EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterModeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleparameterMode=ruleparameterMode();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleparameterMode.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleparameterMode"


    // $ANTLR start "ruleparameterMode"
    // InternalMASL.g:1631:1: ruleparameterMode returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IN_0= RULE_IN | this_OUT_1= RULE_OUT ) ;
    public final AntlrDatatypeRuleToken ruleparameterMode() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_IN_0=null;
        Token this_OUT_1=null;


        	enterRule();

        try {
            // InternalMASL.g:1637:2: ( (this_IN_0= RULE_IN | this_OUT_1= RULE_OUT ) )
            // InternalMASL.g:1638:2: (this_IN_0= RULE_IN | this_OUT_1= RULE_OUT )
            {
            // InternalMASL.g:1638:2: (this_IN_0= RULE_IN | this_OUT_1= RULE_OUT )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_IN) ) {
                alt30=1;
            }
            else if ( (LA30_0==RULE_OUT) ) {
                alt30=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // InternalMASL.g:1639:3: this_IN_0= RULE_IN
                    {
                    this_IN_0=(Token)match(input,RULE_IN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_IN_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_IN_0, grammarAccess.getParameterModeAccess().getINTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:1647:3: this_OUT_1= RULE_OUT
                    {
                    this_OUT_1=(Token)match(input,RULE_OUT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_OUT_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_OUT_1, grammarAccess.getParameterModeAccess().getOUTTerminalRuleCall_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleparameterMode"


    // $ANTLR start "entryRuleserviceName"
    // InternalMASL.g:1658:1: entryRuleserviceName returns [String current=null] : iv_ruleserviceName= ruleserviceName EOF ;
    public final String entryRuleserviceName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleserviceName = null;


        try {
            // InternalMASL.g:1658:51: (iv_ruleserviceName= ruleserviceName EOF )
            // InternalMASL.g:1659:2: iv_ruleserviceName= ruleserviceName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getServiceNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleserviceName=ruleserviceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleserviceName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleserviceName"


    // $ANTLR start "ruleserviceName"
    // InternalMASL.g:1665:1: ruleserviceName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruleserviceName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1671:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1672:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getServiceNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleserviceName"


    // $ANTLR start "entryRuleparameterName"
    // InternalMASL.g:1685:1: entryRuleparameterName returns [String current=null] : iv_ruleparameterName= ruleparameterName EOF ;
    public final String entryRuleparameterName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleparameterName = null;


        try {
            // InternalMASL.g:1685:53: (iv_ruleparameterName= ruleparameterName EOF )
            // InternalMASL.g:1686:2: iv_ruleparameterName= ruleparameterName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleparameterName=ruleparameterName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleparameterName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleparameterName"


    // $ANTLR start "ruleparameterName"
    // InternalMASL.g:1692:1: ruleparameterName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken ruleparameterName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1698:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1699:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getParameterNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleparameterName"


    // $ANTLR start "entryRuleparameterType"
    // InternalMASL.g:1712:1: entryRuleparameterType returns [EObject current=null] : iv_ruleparameterType= ruleparameterType EOF ;
    public final EObject entryRuleparameterType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleparameterType = null;


        try {
            // InternalMASL.g:1712:54: (iv_ruleparameterType= ruleparameterType EOF )
            // InternalMASL.g:1713:2: iv_ruleparameterType= ruleparameterType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleparameterType=ruleparameterType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleparameterType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleparameterType"


    // $ANTLR start "ruleparameterType"
    // InternalMASL.g:1719:1: ruleparameterType returns [EObject current=null] : this_typeReference_0= ruletypeReference ;
    public final EObject ruleparameterType() throws RecognitionException {
        EObject current = null;

        EObject this_typeReference_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1725:2: (this_typeReference_0= ruletypeReference )
            // InternalMASL.g:1726:2: this_typeReference_0= ruletypeReference
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getParameterTypeAccess().getTypeReferenceParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_typeReference_0=ruletypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_typeReference_0;
              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleparameterType"


    // $ANTLR start "entryRulereturnType"
    // InternalMASL.g:1737:1: entryRulereturnType returns [EObject current=null] : iv_rulereturnType= rulereturnType EOF ;
    public final EObject entryRulereturnType() throws RecognitionException {
        EObject current = null;

        EObject iv_rulereturnType = null;


        try {
            // InternalMASL.g:1737:51: (iv_rulereturnType= rulereturnType EOF )
            // InternalMASL.g:1738:2: iv_rulereturnType= rulereturnType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getReturnTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulereturnType=rulereturnType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulereturnType; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulereturnType"


    // $ANTLR start "rulereturnType"
    // InternalMASL.g:1744:1: rulereturnType returns [EObject current=null] : this_typeReference_0= ruletypeReference ;
    public final EObject rulereturnType() throws RecognitionException {
        EObject current = null;

        EObject this_typeReference_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1750:2: (this_typeReference_0= ruletypeReference )
            // InternalMASL.g:1751:2: this_typeReference_0= ruletypeReference
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getReturnTypeAccess().getTypeReferenceParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_typeReference_0=ruletypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_typeReference_0;
              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulereturnType"


    // $ANTLR start "entryRulepragmaList"
    // InternalMASL.g:1762:1: entryRulepragmaList returns [String current=null] : iv_rulepragmaList= rulepragmaList EOF ;
    public final String entryRulepragmaList() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulepragmaList = null;


        try {
            // InternalMASL.g:1762:50: (iv_rulepragmaList= rulepragmaList EOF )
            // InternalMASL.g:1763:2: iv_rulepragmaList= rulepragmaList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPragmaListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulepragmaList=rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulepragmaList.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulepragmaList"


    // $ANTLR start "rulepragmaList"
    // InternalMASL.g:1769:1: rulepragmaList returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_pragmaDef_0= rulepragmaDef this_SEMI_1= RULE_SEMI )* ;
    public final AntlrDatatypeRuleToken rulepragmaList() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_SEMI_1=null;
        AntlrDatatypeRuleToken this_pragmaDef_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1775:2: ( (this_pragmaDef_0= rulepragmaDef this_SEMI_1= RULE_SEMI )* )
            // InternalMASL.g:1776:2: (this_pragmaDef_0= rulepragmaDef this_SEMI_1= RULE_SEMI )*
            {
            // InternalMASL.g:1776:2: (this_pragmaDef_0= rulepragmaDef this_SEMI_1= RULE_SEMI )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==RULE_PRAGMA) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalMASL.g:1777:3: this_pragmaDef_0= rulepragmaDef this_SEMI_1= RULE_SEMI
            	    {
            	    if ( state.backtracking==0 ) {

            	      			newCompositeNode(grammarAccess.getPragmaListAccess().getPragmaDefParserRuleCall_0());
            	      		
            	    }
            	    pushFollow(FOLLOW_26);
            	    this_pragmaDef_0=rulepragmaDef();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_pragmaDef_0);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			afterParserOrEnumRuleCall();
            	      		
            	    }
            	    this_SEMI_1=(Token)match(input,RULE_SEMI,FOLLOW_27); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      			current.merge(this_SEMI_1);
            	      		
            	    }
            	    if ( state.backtracking==0 ) {

            	      			newLeafNode(this_SEMI_1, grammarAccess.getPragmaListAccess().getSEMITerminalRuleCall_1());
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulepragmaList"


    // $ANTLR start "entryRulepragmaDef"
    // InternalMASL.g:1798:1: entryRulepragmaDef returns [String current=null] : iv_rulepragmaDef= rulepragmaDef EOF ;
    public final String entryRulepragmaDef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulepragmaDef = null;


        try {
            // InternalMASL.g:1798:49: (iv_rulepragmaDef= rulepragmaDef EOF )
            // InternalMASL.g:1799:2: iv_rulepragmaDef= rulepragmaDef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPragmaDefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulepragmaDef=rulepragmaDef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulepragmaDef.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulepragmaDef"


    // $ANTLR start "rulepragmaDef"
    // InternalMASL.g:1805:1: rulepragmaDef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_PRAGMA_0= RULE_PRAGMA this_pragmaName_1= rulepragmaName this_LPAREN_2= RULE_LPAREN (this_pragmaValue_3= rulepragmaValue (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )* )? this_RPAREN_6= RULE_RPAREN ) ;
    public final AntlrDatatypeRuleToken rulepragmaDef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_PRAGMA_0=null;
        Token this_LPAREN_2=null;
        Token this_COMMA_4=null;
        Token this_RPAREN_6=null;
        AntlrDatatypeRuleToken this_pragmaName_1 = null;

        AntlrDatatypeRuleToken this_pragmaValue_3 = null;

        AntlrDatatypeRuleToken this_pragmaValue_5 = null;



        	enterRule();

        try {
            // InternalMASL.g:1811:2: ( (this_PRAGMA_0= RULE_PRAGMA this_pragmaName_1= rulepragmaName this_LPAREN_2= RULE_LPAREN (this_pragmaValue_3= rulepragmaValue (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )* )? this_RPAREN_6= RULE_RPAREN ) )
            // InternalMASL.g:1812:2: (this_PRAGMA_0= RULE_PRAGMA this_pragmaName_1= rulepragmaName this_LPAREN_2= RULE_LPAREN (this_pragmaValue_3= rulepragmaValue (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )* )? this_RPAREN_6= RULE_RPAREN )
            {
            // InternalMASL.g:1812:2: (this_PRAGMA_0= RULE_PRAGMA this_pragmaName_1= rulepragmaName this_LPAREN_2= RULE_LPAREN (this_pragmaValue_3= rulepragmaValue (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )* )? this_RPAREN_6= RULE_RPAREN )
            // InternalMASL.g:1813:3: this_PRAGMA_0= RULE_PRAGMA this_pragmaName_1= rulepragmaName this_LPAREN_2= RULE_LPAREN (this_pragmaValue_3= rulepragmaValue (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )* )? this_RPAREN_6= RULE_RPAREN
            {
            this_PRAGMA_0=(Token)match(input,RULE_PRAGMA,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_PRAGMA_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_PRAGMA_0, grammarAccess.getPragmaDefAccess().getPRAGMATerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPragmaDefAccess().getPragmaNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_7);
            this_pragmaName_1=rulepragmaName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_pragmaName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_LPAREN_2=(Token)match(input,RULE_LPAREN,FOLLOW_28); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LPAREN_2);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_2, grammarAccess.getPragmaDefAccess().getLPARENTerminalRuleCall_2());
              		
            }
            // InternalMASL.g:1837:3: (this_pragmaValue_3= rulepragmaValue (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )* )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RULE_NULL||(LA33_0>=RULE_INTEGERLITERAL && LA33_0<=RULE_IDENT)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMASL.g:1838:4: this_pragmaValue_3= rulepragmaValue (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )*
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getPragmaDefAccess().getPragmaValueParserRuleCall_3_0());
                      			
                    }
                    pushFollow(FOLLOW_23);
                    this_pragmaValue_3=rulepragmaValue();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_pragmaValue_3);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalMASL.g:1848:4: (this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( (LA32_0==RULE_COMMA) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // InternalMASL.g:1849:5: this_COMMA_4= RULE_COMMA this_pragmaValue_5= rulepragmaValue
                    	    {
                    	    this_COMMA_4=(Token)match(input,RULE_COMMA,FOLLOW_29); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_COMMA_4);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_COMMA_4, grammarAccess.getPragmaDefAccess().getCOMMATerminalRuleCall_3_1_0());
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newCompositeNode(grammarAccess.getPragmaDefAccess().getPragmaValueParserRuleCall_3_1_1());
                    	      				
                    	    }
                    	    pushFollow(FOLLOW_23);
                    	    this_pragmaValue_5=rulepragmaValue();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_pragmaValue_5);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					afterParserOrEnumRuleCall();
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop32;
                        }
                    } while (true);


                    }
                    break;

            }

            this_RPAREN_6=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_RPAREN_6);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_6, grammarAccess.getPragmaDefAccess().getRPARENTerminalRuleCall_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulepragmaDef"


    // $ANTLR start "entryRulepragmaValue"
    // InternalMASL.g:1879:1: entryRulepragmaValue returns [String current=null] : iv_rulepragmaValue= rulepragmaValue EOF ;
    public final String entryRulepragmaValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulepragmaValue = null;


        try {
            // InternalMASL.g:1879:51: (iv_rulepragmaValue= rulepragmaValue EOF )
            // InternalMASL.g:1880:2: iv_rulepragmaValue= rulepragmaValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPragmaValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulepragmaValue=rulepragmaValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulepragmaValue.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulepragmaValue"


    // $ANTLR start "rulepragmaValue"
    // InternalMASL.g:1886:1: rulepragmaValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_identifier_0= ruleidentifier | this_literal_1= ruleliteral ) ;
    public final AntlrDatatypeRuleToken rulepragmaValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;

        AntlrDatatypeRuleToken this_literal_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:1892:2: ( (this_identifier_0= ruleidentifier | this_literal_1= ruleliteral ) )
            // InternalMASL.g:1893:2: (this_identifier_0= ruleidentifier | this_literal_1= ruleliteral )
            {
            // InternalMASL.g:1893:2: (this_identifier_0= ruleidentifier | this_literal_1= ruleliteral )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RULE_IDENT) ) {
                alt34=1;
            }
            else if ( (LA34_0==RULE_NULL||(LA34_0>=RULE_INTEGERLITERAL && LA34_0<=RULE_FILE_NAME)) ) {
                alt34=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // InternalMASL.g:1894:3: this_identifier_0= ruleidentifier
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPragmaValueAccess().getIdentifierParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_identifier_0=ruleidentifier();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_identifier_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:1905:3: this_literal_1= ruleliteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPragmaValueAccess().getLiteralParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_literal_1=ruleliteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_literal_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulepragmaValue"


    // $ANTLR start "entryRulepragmaName"
    // InternalMASL.g:1919:1: entryRulepragmaName returns [String current=null] : iv_rulepragmaName= rulepragmaName EOF ;
    public final String entryRulepragmaName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulepragmaName = null;


        try {
            // InternalMASL.g:1919:50: (iv_rulepragmaName= rulepragmaName EOF )
            // InternalMASL.g:1920:2: iv_rulepragmaName= rulepragmaName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPragmaNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulepragmaName=rulepragmaName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulepragmaName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulepragmaName"


    // $ANTLR start "rulepragmaName"
    // InternalMASL.g:1926:1: rulepragmaName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken rulepragmaName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1932:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:1933:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getPragmaNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulepragmaName"


    // $ANTLR start "entryRuledomainServiceDefinition"
    // InternalMASL.g:1946:1: entryRuledomainServiceDefinition returns [EObject current=null] : iv_ruledomainServiceDefinition= ruledomainServiceDefinition EOF ;
    public final EObject entryRuledomainServiceDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledomainServiceDefinition = null;


        try {
            // InternalMASL.g:1946:64: (iv_ruledomainServiceDefinition= ruledomainServiceDefinition EOF )
            // InternalMASL.g:1947:2: iv_ruledomainServiceDefinition= ruledomainServiceDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDomainServiceDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledomainServiceDefinition=ruledomainServiceDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledomainServiceDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledomainServiceDefinition"


    // $ANTLR start "ruledomainServiceDefinition"
    // InternalMASL.g:1953:1: ruledomainServiceDefinition returns [EObject current=null] : ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_IS_6= RULE_IS ( (lv_c_7_0= rulecodeBlock ) ) (this_SERVICE_8= RULE_SERVICE )? this_SEMI_9= RULE_SEMI rulepragmaList ) ;
    public final EObject ruledomainServiceDefinition() throws RecognitionException {
        EObject current = null;

        Token this_SERVICE_1=null;
        Token this_SCOPE_3=null;
        Token this_IS_6=null;
        Token this_SERVICE_8=null;
        Token this_SEMI_9=null;
        EObject lv_s_0_0 = null;

        EObject lv_p_5_0 = null;

        EObject lv_c_7_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:1959:2: ( ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_IS_6= RULE_IS ( (lv_c_7_0= rulecodeBlock ) ) (this_SERVICE_8= RULE_SERVICE )? this_SEMI_9= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:1960:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_IS_6= RULE_IS ( (lv_c_7_0= rulecodeBlock ) ) (this_SERVICE_8= RULE_SERVICE )? this_SEMI_9= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:1960:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_IS_6= RULE_IS ( (lv_c_7_0= rulecodeBlock ) ) (this_SERVICE_8= RULE_SERVICE )? this_SEMI_9= RULE_SEMI rulepragmaList )
            // InternalMASL.g:1961:3: ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_IS_6= RULE_IS ( (lv_c_7_0= rulecodeBlock ) ) (this_SERVICE_8= RULE_SERVICE )? this_SEMI_9= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:1961:3: ( (lv_s_0_0= ruleserviceVisibility ) )
            // InternalMASL.g:1962:4: (lv_s_0_0= ruleserviceVisibility )
            {
            // InternalMASL.g:1962:4: (lv_s_0_0= ruleserviceVisibility )
            // InternalMASL.g:1963:5: lv_s_0_0= ruleserviceVisibility
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDomainServiceDefinitionAccess().getSServiceVisibilityParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_30);
            lv_s_0_0=ruleserviceVisibility();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDomainServiceDefinitionRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.serviceVisibility");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_SERVICE_1=(Token)match(input,RULE_SERVICE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SERVICE_1, grammarAccess.getDomainServiceDefinitionAccess().getSERVICETerminalRuleCall_1());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDomainServiceDefinitionAccess().getDomainNameParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_3);
            ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_SCOPE_3=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCOPE_3, grammarAccess.getDomainServiceDefinitionAccess().getSCOPETerminalRuleCall_3());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDomainServiceDefinitionAccess().getServiceNameParserRuleCall_4());
              		
            }
            pushFollow(FOLLOW_7);
            ruleserviceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:2002:3: ( (lv_p_5_0= ruleparameterList ) )
            // InternalMASL.g:2003:4: (lv_p_5_0= ruleparameterList )
            {
            // InternalMASL.g:2003:4: (lv_p_5_0= ruleparameterList )
            // InternalMASL.g:2004:5: lv_p_5_0= ruleparameterList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDomainServiceDefinitionAccess().getPParameterListParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_p_5_0=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDomainServiceDefinitionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_5_0,
              						"org.xtuml.bp.ui.xtext.MASL.parameterList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_IS_6=(Token)match(input,RULE_IS,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_6, grammarAccess.getDomainServiceDefinitionAccess().getISTerminalRuleCall_6());
              		
            }
            // InternalMASL.g:2025:3: ( (lv_c_7_0= rulecodeBlock ) )
            // InternalMASL.g:2026:4: (lv_c_7_0= rulecodeBlock )
            {
            // InternalMASL.g:2026:4: (lv_c_7_0= rulecodeBlock )
            // InternalMASL.g:2027:5: lv_c_7_0= rulecodeBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDomainServiceDefinitionAccess().getCCodeBlockParserRuleCall_7_0());
              				
            }
            pushFollow(FOLLOW_33);
            lv_c_7_0=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDomainServiceDefinitionRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_7_0,
              						"org.xtuml.bp.ui.xtext.MASL.codeBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2044:3: (this_SERVICE_8= RULE_SERVICE )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_SERVICE) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMASL.g:2045:4: this_SERVICE_8= RULE_SERVICE
                    {
                    this_SERVICE_8=(Token)match(input,RULE_SERVICE,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SERVICE_8, grammarAccess.getDomainServiceDefinitionAccess().getSERVICETerminalRuleCall_8());
                      			
                    }

                    }
                    break;

            }

            this_SEMI_9=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_9, grammarAccess.getDomainServiceDefinitionAccess().getSEMITerminalRuleCall_9());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDomainServiceDefinitionAccess().getPragmaListParserRuleCall_10());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledomainServiceDefinition"


    // $ANTLR start "entryRuledomainFunctionDefinition"
    // InternalMASL.g:2065:1: entryRuledomainFunctionDefinition returns [EObject current=null] : iv_ruledomainFunctionDefinition= ruledomainFunctionDefinition EOF ;
    public final EObject entryRuledomainFunctionDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledomainFunctionDefinition = null;


        try {
            // InternalMASL.g:2065:65: (iv_ruledomainFunctionDefinition= ruledomainFunctionDefinition EOF )
            // InternalMASL.g:2066:2: iv_ruledomainFunctionDefinition= ruledomainFunctionDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDomainFunctionDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledomainFunctionDefinition=ruledomainFunctionDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledomainFunctionDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledomainFunctionDefinition"


    // $ANTLR start "ruledomainFunctionDefinition"
    // InternalMASL.g:2072:1: ruledomainFunctionDefinition returns [EObject current=null] : ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_RETURN_6= RULE_RETURN ( (lv_r_7_0= rulereturnType ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_FUNCTION_10= RULE_FUNCTION )? this_SEMI_11= RULE_SEMI rulepragmaList ) ;
    public final EObject ruledomainFunctionDefinition() throws RecognitionException {
        EObject current = null;

        Token this_FUNCTION_1=null;
        Token this_SCOPE_3=null;
        Token this_RETURN_6=null;
        Token this_IS_8=null;
        Token this_FUNCTION_10=null;
        Token this_SEMI_11=null;
        EObject lv_s_0_0 = null;

        EObject lv_p_5_0 = null;

        EObject lv_r_7_0 = null;

        EObject lv_c_9_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2078:2: ( ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_RETURN_6= RULE_RETURN ( (lv_r_7_0= rulereturnType ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_FUNCTION_10= RULE_FUNCTION )? this_SEMI_11= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:2079:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_RETURN_6= RULE_RETURN ( (lv_r_7_0= rulereturnType ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_FUNCTION_10= RULE_FUNCTION )? this_SEMI_11= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:2079:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_RETURN_6= RULE_RETURN ( (lv_r_7_0= rulereturnType ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_FUNCTION_10= RULE_FUNCTION )? this_SEMI_11= RULE_SEMI rulepragmaList )
            // InternalMASL.g:2080:3: ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleserviceName ( (lv_p_5_0= ruleparameterList ) ) this_RETURN_6= RULE_RETURN ( (lv_r_7_0= rulereturnType ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_FUNCTION_10= RULE_FUNCTION )? this_SEMI_11= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:2080:3: ( (lv_s_0_0= ruleserviceVisibility ) )
            // InternalMASL.g:2081:4: (lv_s_0_0= ruleserviceVisibility )
            {
            // InternalMASL.g:2081:4: (lv_s_0_0= ruleserviceVisibility )
            // InternalMASL.g:2082:5: lv_s_0_0= ruleserviceVisibility
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDomainFunctionDefinitionAccess().getSServiceVisibilityParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_35);
            lv_s_0_0=ruleserviceVisibility();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDomainFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.serviceVisibility");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_FUNCTION_1=(Token)match(input,RULE_FUNCTION,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_FUNCTION_1, grammarAccess.getDomainFunctionDefinitionAccess().getFUNCTIONTerminalRuleCall_1());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDomainFunctionDefinitionAccess().getDomainNameParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_3);
            ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_SCOPE_3=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCOPE_3, grammarAccess.getDomainFunctionDefinitionAccess().getSCOPETerminalRuleCall_3());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDomainFunctionDefinitionAccess().getServiceNameParserRuleCall_4());
              		
            }
            pushFollow(FOLLOW_7);
            ruleserviceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:2121:3: ( (lv_p_5_0= ruleparameterList ) )
            // InternalMASL.g:2122:4: (lv_p_5_0= ruleparameterList )
            {
            // InternalMASL.g:2122:4: (lv_p_5_0= ruleparameterList )
            // InternalMASL.g:2123:5: lv_p_5_0= ruleparameterList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDomainFunctionDefinitionAccess().getPParameterListParserRuleCall_5_0());
              				
            }
            pushFollow(FOLLOW_36);
            lv_p_5_0=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDomainFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_5_0,
              						"org.xtuml.bp.ui.xtext.MASL.parameterList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_RETURN_6=(Token)match(input,RULE_RETURN,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RETURN_6, grammarAccess.getDomainFunctionDefinitionAccess().getRETURNTerminalRuleCall_6());
              		
            }
            // InternalMASL.g:2144:3: ( (lv_r_7_0= rulereturnType ) )
            // InternalMASL.g:2145:4: (lv_r_7_0= rulereturnType )
            {
            // InternalMASL.g:2145:4: (lv_r_7_0= rulereturnType )
            // InternalMASL.g:2146:5: lv_r_7_0= rulereturnType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDomainFunctionDefinitionAccess().getRReturnTypeParserRuleCall_7_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_r_7_0=rulereturnType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDomainFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"r",
              						lv_r_7_0,
              						"org.xtuml.bp.ui.xtext.MASL.returnType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_IS_8=(Token)match(input,RULE_IS,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_8, grammarAccess.getDomainFunctionDefinitionAccess().getISTerminalRuleCall_8());
              		
            }
            // InternalMASL.g:2167:3: ( (lv_c_9_0= rulecodeBlock ) )
            // InternalMASL.g:2168:4: (lv_c_9_0= rulecodeBlock )
            {
            // InternalMASL.g:2168:4: (lv_c_9_0= rulecodeBlock )
            // InternalMASL.g:2169:5: lv_c_9_0= rulecodeBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDomainFunctionDefinitionAccess().getCCodeBlockParserRuleCall_9_0());
              				
            }
            pushFollow(FOLLOW_37);
            lv_c_9_0=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDomainFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_9_0,
              						"org.xtuml.bp.ui.xtext.MASL.codeBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2186:3: (this_FUNCTION_10= RULE_FUNCTION )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_FUNCTION) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMASL.g:2187:4: this_FUNCTION_10= RULE_FUNCTION
                    {
                    this_FUNCTION_10=(Token)match(input,RULE_FUNCTION,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_FUNCTION_10, grammarAccess.getDomainFunctionDefinitionAccess().getFUNCTIONTerminalRuleCall_10());
                      			
                    }

                    }
                    break;

            }

            this_SEMI_11=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_11, grammarAccess.getDomainFunctionDefinitionAccess().getSEMITerminalRuleCall_11());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDomainFunctionDefinitionAccess().getPragmaListParserRuleCall_12());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledomainFunctionDefinition"


    // $ANTLR start "entryRuleobjectServiceDefinition"
    // InternalMASL.g:2207:1: entryRuleobjectServiceDefinition returns [EObject current=null] : iv_ruleobjectServiceDefinition= ruleobjectServiceDefinition EOF ;
    public final EObject entryRuleobjectServiceDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleobjectServiceDefinition = null;


        try {
            // InternalMASL.g:2207:64: (iv_ruleobjectServiceDefinition= ruleobjectServiceDefinition EOF )
            // InternalMASL.g:2208:2: iv_ruleobjectServiceDefinition= ruleobjectServiceDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getObjectServiceDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleobjectServiceDefinition=ruleobjectServiceDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleobjectServiceDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleobjectServiceDefinition"


    // $ANTLR start "ruleobjectServiceDefinition"
    // InternalMASL.g:2214:1: ruleobjectServiceDefinition returns [EObject current=null] : ( ( (lv_s_0_0= ruleserviceVisibility ) ) (this_INSTANCE_1= RULE_INSTANCE )? this_SERVICE_2= RULE_SERVICE ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_IS_9= RULE_IS ( (lv_c_10_0= rulecodeBlock ) ) (this_SERVICE_11= RULE_SERVICE )? this_SEMI_12= RULE_SEMI rulepragmaList ) ;
    public final EObject ruleobjectServiceDefinition() throws RecognitionException {
        EObject current = null;

        Token this_INSTANCE_1=null;
        Token this_SERVICE_2=null;
        Token this_SCOPE_4=null;
        Token this_DOT_6=null;
        Token this_IS_9=null;
        Token this_SERVICE_11=null;
        Token this_SEMI_12=null;
        EObject lv_s_0_0 = null;

        EObject lv_p_8_0 = null;

        EObject lv_c_10_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2220:2: ( ( ( (lv_s_0_0= ruleserviceVisibility ) ) (this_INSTANCE_1= RULE_INSTANCE )? this_SERVICE_2= RULE_SERVICE ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_IS_9= RULE_IS ( (lv_c_10_0= rulecodeBlock ) ) (this_SERVICE_11= RULE_SERVICE )? this_SEMI_12= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:2221:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) (this_INSTANCE_1= RULE_INSTANCE )? this_SERVICE_2= RULE_SERVICE ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_IS_9= RULE_IS ( (lv_c_10_0= rulecodeBlock ) ) (this_SERVICE_11= RULE_SERVICE )? this_SEMI_12= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:2221:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) (this_INSTANCE_1= RULE_INSTANCE )? this_SERVICE_2= RULE_SERVICE ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_IS_9= RULE_IS ( (lv_c_10_0= rulecodeBlock ) ) (this_SERVICE_11= RULE_SERVICE )? this_SEMI_12= RULE_SEMI rulepragmaList )
            // InternalMASL.g:2222:3: ( (lv_s_0_0= ruleserviceVisibility ) ) (this_INSTANCE_1= RULE_INSTANCE )? this_SERVICE_2= RULE_SERVICE ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_IS_9= RULE_IS ( (lv_c_10_0= rulecodeBlock ) ) (this_SERVICE_11= RULE_SERVICE )? this_SEMI_12= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:2222:3: ( (lv_s_0_0= ruleserviceVisibility ) )
            // InternalMASL.g:2223:4: (lv_s_0_0= ruleserviceVisibility )
            {
            // InternalMASL.g:2223:4: (lv_s_0_0= ruleserviceVisibility )
            // InternalMASL.g:2224:5: lv_s_0_0= ruleserviceVisibility
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectServiceDefinitionAccess().getSServiceVisibilityParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_38);
            lv_s_0_0=ruleserviceVisibility();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectServiceDefinitionRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.serviceVisibility");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2241:3: (this_INSTANCE_1= RULE_INSTANCE )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_INSTANCE) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMASL.g:2242:4: this_INSTANCE_1= RULE_INSTANCE
                    {
                    this_INSTANCE_1=(Token)match(input,RULE_INSTANCE,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_INSTANCE_1, grammarAccess.getObjectServiceDefinitionAccess().getINSTANCETerminalRuleCall_1());
                      			
                    }

                    }
                    break;

            }

            this_SERVICE_2=(Token)match(input,RULE_SERVICE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SERVICE_2, grammarAccess.getObjectServiceDefinitionAccess().getSERVICETerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectServiceDefinitionAccess().getDomainNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_3);
            ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_SCOPE_4=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCOPE_4, grammarAccess.getObjectServiceDefinitionAccess().getSCOPETerminalRuleCall_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectServiceDefinitionAccess().getObjectNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_39);
            ruleobjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_DOT_6=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_DOT_6, grammarAccess.getObjectServiceDefinitionAccess().getDOTTerminalRuleCall_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectServiceDefinitionAccess().getServiceNameParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_7);
            ruleserviceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:2280:3: ( (lv_p_8_0= ruleparameterList ) )
            // InternalMASL.g:2281:4: (lv_p_8_0= ruleparameterList )
            {
            // InternalMASL.g:2281:4: (lv_p_8_0= ruleparameterList )
            // InternalMASL.g:2282:5: lv_p_8_0= ruleparameterList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectServiceDefinitionAccess().getPParameterListParserRuleCall_8_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_p_8_0=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectServiceDefinitionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_8_0,
              						"org.xtuml.bp.ui.xtext.MASL.parameterList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_IS_9=(Token)match(input,RULE_IS,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_9, grammarAccess.getObjectServiceDefinitionAccess().getISTerminalRuleCall_9());
              		
            }
            // InternalMASL.g:2303:3: ( (lv_c_10_0= rulecodeBlock ) )
            // InternalMASL.g:2304:4: (lv_c_10_0= rulecodeBlock )
            {
            // InternalMASL.g:2304:4: (lv_c_10_0= rulecodeBlock )
            // InternalMASL.g:2305:5: lv_c_10_0= rulecodeBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectServiceDefinitionAccess().getCCodeBlockParserRuleCall_10_0());
              				
            }
            pushFollow(FOLLOW_33);
            lv_c_10_0=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectServiceDefinitionRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_10_0,
              						"org.xtuml.bp.ui.xtext.MASL.codeBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2322:3: (this_SERVICE_11= RULE_SERVICE )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==RULE_SERVICE) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMASL.g:2323:4: this_SERVICE_11= RULE_SERVICE
                    {
                    this_SERVICE_11=(Token)match(input,RULE_SERVICE,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SERVICE_11, grammarAccess.getObjectServiceDefinitionAccess().getSERVICETerminalRuleCall_11());
                      			
                    }

                    }
                    break;

            }

            this_SEMI_12=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_12, grammarAccess.getObjectServiceDefinitionAccess().getSEMITerminalRuleCall_12());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectServiceDefinitionAccess().getPragmaListParserRuleCall_13());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleobjectServiceDefinition"


    // $ANTLR start "entryRuleterminatorServiceDefinition"
    // InternalMASL.g:2343:1: entryRuleterminatorServiceDefinition returns [EObject current=null] : iv_ruleterminatorServiceDefinition= ruleterminatorServiceDefinition EOF ;
    public final EObject entryRuleterminatorServiceDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleterminatorServiceDefinition = null;


        try {
            // InternalMASL.g:2343:68: (iv_ruleterminatorServiceDefinition= ruleterminatorServiceDefinition EOF )
            // InternalMASL.g:2344:2: iv_ruleterminatorServiceDefinition= ruleterminatorServiceDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTerminatorServiceDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleterminatorServiceDefinition=ruleterminatorServiceDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleterminatorServiceDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleterminatorServiceDefinition"


    // $ANTLR start "ruleterminatorServiceDefinition"
    // InternalMASL.g:2350:1: ruleterminatorServiceDefinition returns [EObject current=null] : ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_SERVICE_10= RULE_SERVICE )? this_SEMI_11= RULE_SEMI rulepragmaList ) ;
    public final EObject ruleterminatorServiceDefinition() throws RecognitionException {
        EObject current = null;

        Token this_SERVICE_1=null;
        Token this_SCOPE_3=null;
        Token this_TERMINATOR_SCOPE_5=null;
        Token this_IS_8=null;
        Token this_SERVICE_10=null;
        Token this_SEMI_11=null;
        EObject lv_s_0_0 = null;

        EObject lv_p_7_0 = null;

        EObject lv_c_9_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2356:2: ( ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_SERVICE_10= RULE_SERVICE )? this_SEMI_11= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:2357:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_SERVICE_10= RULE_SERVICE )? this_SEMI_11= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:2357:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_SERVICE_10= RULE_SERVICE )? this_SEMI_11= RULE_SEMI rulepragmaList )
            // InternalMASL.g:2358:3: ( (lv_s_0_0= ruleserviceVisibility ) ) this_SERVICE_1= RULE_SERVICE ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_SERVICE_10= RULE_SERVICE )? this_SEMI_11= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:2358:3: ( (lv_s_0_0= ruleserviceVisibility ) )
            // InternalMASL.g:2359:4: (lv_s_0_0= ruleserviceVisibility )
            {
            // InternalMASL.g:2359:4: (lv_s_0_0= ruleserviceVisibility )
            // InternalMASL.g:2360:5: lv_s_0_0= ruleserviceVisibility
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTerminatorServiceDefinitionAccess().getSServiceVisibilityParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_30);
            lv_s_0_0=ruleserviceVisibility();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTerminatorServiceDefinitionRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.serviceVisibility");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_SERVICE_1=(Token)match(input,RULE_SERVICE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SERVICE_1, grammarAccess.getTerminatorServiceDefinitionAccess().getSERVICETerminalRuleCall_1());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorServiceDefinitionAccess().getDomainNameParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_3);
            ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_SCOPE_3=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCOPE_3, grammarAccess.getTerminatorServiceDefinitionAccess().getSCOPETerminalRuleCall_3());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorServiceDefinitionAccess().getTerminatorNameParserRuleCall_4());
              		
            }
            pushFollow(FOLLOW_40);
            ruleterminatorName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_TERMINATOR_SCOPE_5=(Token)match(input,RULE_TERMINATOR_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_TERMINATOR_SCOPE_5, grammarAccess.getTerminatorServiceDefinitionAccess().getTERMINATOR_SCOPETerminalRuleCall_5());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorServiceDefinitionAccess().getServiceNameParserRuleCall_6());
              		
            }
            pushFollow(FOLLOW_7);
            ruleserviceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:2410:3: ( (lv_p_7_0= ruleparameterList ) )
            // InternalMASL.g:2411:4: (lv_p_7_0= ruleparameterList )
            {
            // InternalMASL.g:2411:4: (lv_p_7_0= ruleparameterList )
            // InternalMASL.g:2412:5: lv_p_7_0= ruleparameterList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTerminatorServiceDefinitionAccess().getPParameterListParserRuleCall_7_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_p_7_0=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTerminatorServiceDefinitionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_7_0,
              						"org.xtuml.bp.ui.xtext.MASL.parameterList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_IS_8=(Token)match(input,RULE_IS,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_8, grammarAccess.getTerminatorServiceDefinitionAccess().getISTerminalRuleCall_8());
              		
            }
            // InternalMASL.g:2433:3: ( (lv_c_9_0= rulecodeBlock ) )
            // InternalMASL.g:2434:4: (lv_c_9_0= rulecodeBlock )
            {
            // InternalMASL.g:2434:4: (lv_c_9_0= rulecodeBlock )
            // InternalMASL.g:2435:5: lv_c_9_0= rulecodeBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTerminatorServiceDefinitionAccess().getCCodeBlockParserRuleCall_9_0());
              				
            }
            pushFollow(FOLLOW_33);
            lv_c_9_0=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTerminatorServiceDefinitionRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_9_0,
              						"org.xtuml.bp.ui.xtext.MASL.codeBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2452:3: (this_SERVICE_10= RULE_SERVICE )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==RULE_SERVICE) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMASL.g:2453:4: this_SERVICE_10= RULE_SERVICE
                    {
                    this_SERVICE_10=(Token)match(input,RULE_SERVICE,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SERVICE_10, grammarAccess.getTerminatorServiceDefinitionAccess().getSERVICETerminalRuleCall_10());
                      			
                    }

                    }
                    break;

            }

            this_SEMI_11=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_11, grammarAccess.getTerminatorServiceDefinitionAccess().getSEMITerminalRuleCall_11());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorServiceDefinitionAccess().getPragmaListParserRuleCall_12());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleterminatorServiceDefinition"


    // $ANTLR start "entryRuleterminatorFunctionDefinition"
    // InternalMASL.g:2473:1: entryRuleterminatorFunctionDefinition returns [EObject current=null] : iv_ruleterminatorFunctionDefinition= ruleterminatorFunctionDefinition EOF ;
    public final EObject entryRuleterminatorFunctionDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleterminatorFunctionDefinition = null;


        try {
            // InternalMASL.g:2473:69: (iv_ruleterminatorFunctionDefinition= ruleterminatorFunctionDefinition EOF )
            // InternalMASL.g:2474:2: iv_ruleterminatorFunctionDefinition= ruleterminatorFunctionDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleterminatorFunctionDefinition=ruleterminatorFunctionDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleterminatorFunctionDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleterminatorFunctionDefinition"


    // $ANTLR start "ruleterminatorFunctionDefinition"
    // InternalMASL.g:2480:1: ruleterminatorFunctionDefinition returns [EObject current=null] : ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_RETURN_8= RULE_RETURN ( (lv_r_9_0= rulereturnType ) ) this_IS_10= RULE_IS ( (lv_c_11_0= rulecodeBlock ) ) (this_FUNCTION_12= RULE_FUNCTION )? this_SEMI_13= RULE_SEMI rulepragmaList ) ;
    public final EObject ruleterminatorFunctionDefinition() throws RecognitionException {
        EObject current = null;

        Token this_FUNCTION_1=null;
        Token this_SCOPE_3=null;
        Token this_TERMINATOR_SCOPE_5=null;
        Token this_RETURN_8=null;
        Token this_IS_10=null;
        Token this_FUNCTION_12=null;
        Token this_SEMI_13=null;
        EObject lv_s_0_0 = null;

        EObject lv_p_7_0 = null;

        EObject lv_r_9_0 = null;

        EObject lv_c_11_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2486:2: ( ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_RETURN_8= RULE_RETURN ( (lv_r_9_0= rulereturnType ) ) this_IS_10= RULE_IS ( (lv_c_11_0= rulecodeBlock ) ) (this_FUNCTION_12= RULE_FUNCTION )? this_SEMI_13= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:2487:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_RETURN_8= RULE_RETURN ( (lv_r_9_0= rulereturnType ) ) this_IS_10= RULE_IS ( (lv_c_11_0= rulecodeBlock ) ) (this_FUNCTION_12= RULE_FUNCTION )? this_SEMI_13= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:2487:2: ( ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_RETURN_8= RULE_RETURN ( (lv_r_9_0= rulereturnType ) ) this_IS_10= RULE_IS ( (lv_c_11_0= rulecodeBlock ) ) (this_FUNCTION_12= RULE_FUNCTION )? this_SEMI_13= RULE_SEMI rulepragmaList )
            // InternalMASL.g:2488:3: ( (lv_s_0_0= ruleserviceVisibility ) ) this_FUNCTION_1= RULE_FUNCTION ruledomainName this_SCOPE_3= RULE_SCOPE ruleterminatorName this_TERMINATOR_SCOPE_5= RULE_TERMINATOR_SCOPE ruleserviceName ( (lv_p_7_0= ruleparameterList ) ) this_RETURN_8= RULE_RETURN ( (lv_r_9_0= rulereturnType ) ) this_IS_10= RULE_IS ( (lv_c_11_0= rulecodeBlock ) ) (this_FUNCTION_12= RULE_FUNCTION )? this_SEMI_13= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:2488:3: ( (lv_s_0_0= ruleserviceVisibility ) )
            // InternalMASL.g:2489:4: (lv_s_0_0= ruleserviceVisibility )
            {
            // InternalMASL.g:2489:4: (lv_s_0_0= ruleserviceVisibility )
            // InternalMASL.g:2490:5: lv_s_0_0= ruleserviceVisibility
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getSServiceVisibilityParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_35);
            lv_s_0_0=ruleserviceVisibility();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTerminatorFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.serviceVisibility");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_FUNCTION_1=(Token)match(input,RULE_FUNCTION,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_FUNCTION_1, grammarAccess.getTerminatorFunctionDefinitionAccess().getFUNCTIONTerminalRuleCall_1());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getDomainNameParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_3);
            ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_SCOPE_3=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCOPE_3, grammarAccess.getTerminatorFunctionDefinitionAccess().getSCOPETerminalRuleCall_3());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getTerminatorNameParserRuleCall_4());
              		
            }
            pushFollow(FOLLOW_40);
            ruleterminatorName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_TERMINATOR_SCOPE_5=(Token)match(input,RULE_TERMINATOR_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_TERMINATOR_SCOPE_5, grammarAccess.getTerminatorFunctionDefinitionAccess().getTERMINATOR_SCOPETerminalRuleCall_5());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getServiceNameParserRuleCall_6());
              		
            }
            pushFollow(FOLLOW_7);
            ruleserviceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:2540:3: ( (lv_p_7_0= ruleparameterList ) )
            // InternalMASL.g:2541:4: (lv_p_7_0= ruleparameterList )
            {
            // InternalMASL.g:2541:4: (lv_p_7_0= ruleparameterList )
            // InternalMASL.g:2542:5: lv_p_7_0= ruleparameterList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getPParameterListParserRuleCall_7_0());
              				
            }
            pushFollow(FOLLOW_36);
            lv_p_7_0=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTerminatorFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_7_0,
              						"org.xtuml.bp.ui.xtext.MASL.parameterList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_RETURN_8=(Token)match(input,RULE_RETURN,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RETURN_8, grammarAccess.getTerminatorFunctionDefinitionAccess().getRETURNTerminalRuleCall_8());
              		
            }
            // InternalMASL.g:2563:3: ( (lv_r_9_0= rulereturnType ) )
            // InternalMASL.g:2564:4: (lv_r_9_0= rulereturnType )
            {
            // InternalMASL.g:2564:4: (lv_r_9_0= rulereturnType )
            // InternalMASL.g:2565:5: lv_r_9_0= rulereturnType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getRReturnTypeParserRuleCall_9_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_r_9_0=rulereturnType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTerminatorFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"r",
              						lv_r_9_0,
              						"org.xtuml.bp.ui.xtext.MASL.returnType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_IS_10=(Token)match(input,RULE_IS,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_10, grammarAccess.getTerminatorFunctionDefinitionAccess().getISTerminalRuleCall_10());
              		
            }
            // InternalMASL.g:2586:3: ( (lv_c_11_0= rulecodeBlock ) )
            // InternalMASL.g:2587:4: (lv_c_11_0= rulecodeBlock )
            {
            // InternalMASL.g:2587:4: (lv_c_11_0= rulecodeBlock )
            // InternalMASL.g:2588:5: lv_c_11_0= rulecodeBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getCCodeBlockParserRuleCall_11_0());
              				
            }
            pushFollow(FOLLOW_37);
            lv_c_11_0=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTerminatorFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_11_0,
              						"org.xtuml.bp.ui.xtext.MASL.codeBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2605:3: (this_FUNCTION_12= RULE_FUNCTION )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==RULE_FUNCTION) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMASL.g:2606:4: this_FUNCTION_12= RULE_FUNCTION
                    {
                    this_FUNCTION_12=(Token)match(input,RULE_FUNCTION,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_FUNCTION_12, grammarAccess.getTerminatorFunctionDefinitionAccess().getFUNCTIONTerminalRuleCall_12());
                      			
                    }

                    }
                    break;

            }

            this_SEMI_13=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_13, grammarAccess.getTerminatorFunctionDefinitionAccess().getSEMITerminalRuleCall_13());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getTerminatorFunctionDefinitionAccess().getPragmaListParserRuleCall_14());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleterminatorFunctionDefinition"


    // $ANTLR start "entryRuleobjectFunctionDefinition"
    // InternalMASL.g:2626:1: entryRuleobjectFunctionDefinition returns [EObject current=null] : iv_ruleobjectFunctionDefinition= ruleobjectFunctionDefinition EOF ;
    public final EObject entryRuleobjectFunctionDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleobjectFunctionDefinition = null;


        try {
            // InternalMASL.g:2626:65: (iv_ruleobjectFunctionDefinition= ruleobjectFunctionDefinition EOF )
            // InternalMASL.g:2627:2: iv_ruleobjectFunctionDefinition= ruleobjectFunctionDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getObjectFunctionDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleobjectFunctionDefinition=ruleobjectFunctionDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleobjectFunctionDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleobjectFunctionDefinition"


    // $ANTLR start "ruleobjectFunctionDefinition"
    // InternalMASL.g:2633:1: ruleobjectFunctionDefinition returns [EObject current=null] : ( ( (lv_v_0_0= ruleserviceVisibility ) ) ( (lv_s_1_0= ruleserviceType ) ) this_FUNCTION_2= RULE_FUNCTION ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_RETURN_9= RULE_RETURN ( (lv_r_10_0= rulereturnType ) ) this_IS_11= RULE_IS ( (lv_c_12_0= rulecodeBlock ) ) (this_FUNCTION_13= RULE_FUNCTION )? this_SEMI_14= RULE_SEMI rulepragmaList ) ;
    public final EObject ruleobjectFunctionDefinition() throws RecognitionException {
        EObject current = null;

        Token this_FUNCTION_2=null;
        Token this_SCOPE_4=null;
        Token this_DOT_6=null;
        Token this_RETURN_9=null;
        Token this_IS_11=null;
        Token this_FUNCTION_13=null;
        Token this_SEMI_14=null;
        EObject lv_v_0_0 = null;

        AntlrDatatypeRuleToken lv_s_1_0 = null;

        EObject lv_p_8_0 = null;

        EObject lv_r_10_0 = null;

        EObject lv_c_12_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2639:2: ( ( ( (lv_v_0_0= ruleserviceVisibility ) ) ( (lv_s_1_0= ruleserviceType ) ) this_FUNCTION_2= RULE_FUNCTION ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_RETURN_9= RULE_RETURN ( (lv_r_10_0= rulereturnType ) ) this_IS_11= RULE_IS ( (lv_c_12_0= rulecodeBlock ) ) (this_FUNCTION_13= RULE_FUNCTION )? this_SEMI_14= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:2640:2: ( ( (lv_v_0_0= ruleserviceVisibility ) ) ( (lv_s_1_0= ruleserviceType ) ) this_FUNCTION_2= RULE_FUNCTION ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_RETURN_9= RULE_RETURN ( (lv_r_10_0= rulereturnType ) ) this_IS_11= RULE_IS ( (lv_c_12_0= rulecodeBlock ) ) (this_FUNCTION_13= RULE_FUNCTION )? this_SEMI_14= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:2640:2: ( ( (lv_v_0_0= ruleserviceVisibility ) ) ( (lv_s_1_0= ruleserviceType ) ) this_FUNCTION_2= RULE_FUNCTION ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_RETURN_9= RULE_RETURN ( (lv_r_10_0= rulereturnType ) ) this_IS_11= RULE_IS ( (lv_c_12_0= rulecodeBlock ) ) (this_FUNCTION_13= RULE_FUNCTION )? this_SEMI_14= RULE_SEMI rulepragmaList )
            // InternalMASL.g:2641:3: ( (lv_v_0_0= ruleserviceVisibility ) ) ( (lv_s_1_0= ruleserviceType ) ) this_FUNCTION_2= RULE_FUNCTION ruledomainName this_SCOPE_4= RULE_SCOPE ruleobjectName this_DOT_6= RULE_DOT ruleserviceName ( (lv_p_8_0= ruleparameterList ) ) this_RETURN_9= RULE_RETURN ( (lv_r_10_0= rulereturnType ) ) this_IS_11= RULE_IS ( (lv_c_12_0= rulecodeBlock ) ) (this_FUNCTION_13= RULE_FUNCTION )? this_SEMI_14= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:2641:3: ( (lv_v_0_0= ruleserviceVisibility ) )
            // InternalMASL.g:2642:4: (lv_v_0_0= ruleserviceVisibility )
            {
            // InternalMASL.g:2642:4: (lv_v_0_0= ruleserviceVisibility )
            // InternalMASL.g:2643:5: lv_v_0_0= ruleserviceVisibility
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getVServiceVisibilityParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_41);
            lv_v_0_0=ruleserviceVisibility();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"v",
              						lv_v_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.serviceVisibility");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2660:3: ( (lv_s_1_0= ruleserviceType ) )
            // InternalMASL.g:2661:4: (lv_s_1_0= ruleserviceType )
            {
            // InternalMASL.g:2661:4: (lv_s_1_0= ruleserviceType )
            // InternalMASL.g:2662:5: lv_s_1_0= ruleserviceType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getSServiceTypeParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_35);
            lv_s_1_0=ruleserviceType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.serviceType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_FUNCTION_2=(Token)match(input,RULE_FUNCTION,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_FUNCTION_2, grammarAccess.getObjectFunctionDefinitionAccess().getFUNCTIONTerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getDomainNameParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_3);
            ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_SCOPE_4=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCOPE_4, grammarAccess.getObjectFunctionDefinitionAccess().getSCOPETerminalRuleCall_4());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getObjectNameParserRuleCall_5());
              		
            }
            pushFollow(FOLLOW_39);
            ruleobjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_DOT_6=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_DOT_6, grammarAccess.getObjectFunctionDefinitionAccess().getDOTTerminalRuleCall_6());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getServiceNameParserRuleCall_7());
              		
            }
            pushFollow(FOLLOW_7);
            ruleserviceName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:2712:3: ( (lv_p_8_0= ruleparameterList ) )
            // InternalMASL.g:2713:4: (lv_p_8_0= ruleparameterList )
            {
            // InternalMASL.g:2713:4: (lv_p_8_0= ruleparameterList )
            // InternalMASL.g:2714:5: lv_p_8_0= ruleparameterList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getPParameterListParserRuleCall_8_0());
              				
            }
            pushFollow(FOLLOW_36);
            lv_p_8_0=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_8_0,
              						"org.xtuml.bp.ui.xtext.MASL.parameterList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_RETURN_9=(Token)match(input,RULE_RETURN,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RETURN_9, grammarAccess.getObjectFunctionDefinitionAccess().getRETURNTerminalRuleCall_9());
              		
            }
            // InternalMASL.g:2735:3: ( (lv_r_10_0= rulereturnType ) )
            // InternalMASL.g:2736:4: (lv_r_10_0= rulereturnType )
            {
            // InternalMASL.g:2736:4: (lv_r_10_0= rulereturnType )
            // InternalMASL.g:2737:5: lv_r_10_0= rulereturnType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getRReturnTypeParserRuleCall_10_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_r_10_0=rulereturnType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"r",
              						lv_r_10_0,
              						"org.xtuml.bp.ui.xtext.MASL.returnType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_IS_11=(Token)match(input,RULE_IS,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_11, grammarAccess.getObjectFunctionDefinitionAccess().getISTerminalRuleCall_11());
              		
            }
            // InternalMASL.g:2758:3: ( (lv_c_12_0= rulecodeBlock ) )
            // InternalMASL.g:2759:4: (lv_c_12_0= rulecodeBlock )
            {
            // InternalMASL.g:2759:4: (lv_c_12_0= rulecodeBlock )
            // InternalMASL.g:2760:5: lv_c_12_0= rulecodeBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getCCodeBlockParserRuleCall_12_0());
              				
            }
            pushFollow(FOLLOW_37);
            lv_c_12_0=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getObjectFunctionDefinitionRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_12_0,
              						"org.xtuml.bp.ui.xtext.MASL.codeBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2777:3: (this_FUNCTION_13= RULE_FUNCTION )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RULE_FUNCTION) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMASL.g:2778:4: this_FUNCTION_13= RULE_FUNCTION
                    {
                    this_FUNCTION_13=(Token)match(input,RULE_FUNCTION,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_FUNCTION_13, grammarAccess.getObjectFunctionDefinitionAccess().getFUNCTIONTerminalRuleCall_13());
                      			
                    }

                    }
                    break;

            }

            this_SEMI_14=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_14, grammarAccess.getObjectFunctionDefinitionAccess().getSEMITerminalRuleCall_14());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getObjectFunctionDefinitionAccess().getPragmaListParserRuleCall_15());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleobjectFunctionDefinition"


    // $ANTLR start "entryRulestateDefinition"
    // InternalMASL.g:2798:1: entryRulestateDefinition returns [EObject current=null] : iv_rulestateDefinition= rulestateDefinition EOF ;
    public final EObject entryRulestateDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestateDefinition = null;


        try {
            // InternalMASL.g:2798:56: (iv_rulestateDefinition= rulestateDefinition EOF )
            // InternalMASL.g:2799:2: iv_rulestateDefinition= rulestateDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStateDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestateDefinition=rulestateDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestateDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestateDefinition"


    // $ANTLR start "rulestateDefinition"
    // InternalMASL.g:2805:1: rulestateDefinition returns [EObject current=null] : ( ( (lv_s_0_0= rulestateType ) ) this_STATE_1= RULE_STATE ruledomainName this_SCOPE_3= RULE_SCOPE ruleobjectName this_DOT_5= RULE_DOT rulestateName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_STATE_10= RULE_STATE )? this_SEMI_11= RULE_SEMI rulepragmaList ) ;
    public final EObject rulestateDefinition() throws RecognitionException {
        EObject current = null;

        Token this_STATE_1=null;
        Token this_SCOPE_3=null;
        Token this_DOT_5=null;
        Token this_IS_8=null;
        Token this_STATE_10=null;
        Token this_SEMI_11=null;
        EObject lv_s_0_0 = null;

        EObject lv_p_7_0 = null;

        EObject lv_c_9_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2811:2: ( ( ( (lv_s_0_0= rulestateType ) ) this_STATE_1= RULE_STATE ruledomainName this_SCOPE_3= RULE_SCOPE ruleobjectName this_DOT_5= RULE_DOT rulestateName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_STATE_10= RULE_STATE )? this_SEMI_11= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:2812:2: ( ( (lv_s_0_0= rulestateType ) ) this_STATE_1= RULE_STATE ruledomainName this_SCOPE_3= RULE_SCOPE ruleobjectName this_DOT_5= RULE_DOT rulestateName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_STATE_10= RULE_STATE )? this_SEMI_11= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:2812:2: ( ( (lv_s_0_0= rulestateType ) ) this_STATE_1= RULE_STATE ruledomainName this_SCOPE_3= RULE_SCOPE ruleobjectName this_DOT_5= RULE_DOT rulestateName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_STATE_10= RULE_STATE )? this_SEMI_11= RULE_SEMI rulepragmaList )
            // InternalMASL.g:2813:3: ( (lv_s_0_0= rulestateType ) ) this_STATE_1= RULE_STATE ruledomainName this_SCOPE_3= RULE_SCOPE ruleobjectName this_DOT_5= RULE_DOT rulestateName ( (lv_p_7_0= ruleparameterList ) ) this_IS_8= RULE_IS ( (lv_c_9_0= rulecodeBlock ) ) (this_STATE_10= RULE_STATE )? this_SEMI_11= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:2813:3: ( (lv_s_0_0= rulestateType ) )
            // InternalMASL.g:2814:4: (lv_s_0_0= rulestateType )
            {
            // InternalMASL.g:2814:4: (lv_s_0_0= rulestateType )
            // InternalMASL.g:2815:5: lv_s_0_0= rulestateType
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStateDefinitionAccess().getSStateTypeParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_42);
            lv_s_0_0=rulestateType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStateDefinitionRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.stateType");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_STATE_1=(Token)match(input,RULE_STATE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_STATE_1, grammarAccess.getStateDefinitionAccess().getSTATETerminalRuleCall_1());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getStateDefinitionAccess().getDomainNameParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_3);
            ruledomainName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_SCOPE_3=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCOPE_3, grammarAccess.getStateDefinitionAccess().getSCOPETerminalRuleCall_3());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getStateDefinitionAccess().getObjectNameParserRuleCall_4());
              		
            }
            pushFollow(FOLLOW_39);
            ruleobjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_DOT_5=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_DOT_5, grammarAccess.getStateDefinitionAccess().getDOTTerminalRuleCall_5());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getStateDefinitionAccess().getStateNameParserRuleCall_6());
              		
            }
            pushFollow(FOLLOW_7);
            rulestateName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:2865:3: ( (lv_p_7_0= ruleparameterList ) )
            // InternalMASL.g:2866:4: (lv_p_7_0= ruleparameterList )
            {
            // InternalMASL.g:2866:4: (lv_p_7_0= ruleparameterList )
            // InternalMASL.g:2867:5: lv_p_7_0= ruleparameterList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStateDefinitionAccess().getPParameterListParserRuleCall_7_0());
              				
            }
            pushFollow(FOLLOW_31);
            lv_p_7_0=ruleparameterList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStateDefinitionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_7_0,
              						"org.xtuml.bp.ui.xtext.MASL.parameterList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_IS_8=(Token)match(input,RULE_IS,FOLLOW_32); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_8, grammarAccess.getStateDefinitionAccess().getISTerminalRuleCall_8());
              		
            }
            // InternalMASL.g:2888:3: ( (lv_c_9_0= rulecodeBlock ) )
            // InternalMASL.g:2889:4: (lv_c_9_0= rulecodeBlock )
            {
            // InternalMASL.g:2889:4: (lv_c_9_0= rulecodeBlock )
            // InternalMASL.g:2890:5: lv_c_9_0= rulecodeBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStateDefinitionAccess().getCCodeBlockParserRuleCall_9_0());
              				
            }
            pushFollow(FOLLOW_43);
            lv_c_9_0=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStateDefinitionRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_9_0,
              						"org.xtuml.bp.ui.xtext.MASL.codeBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:2907:3: (this_STATE_10= RULE_STATE )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==RULE_STATE) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMASL.g:2908:4: this_STATE_10= RULE_STATE
                    {
                    this_STATE_10=(Token)match(input,RULE_STATE,FOLLOW_26); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_STATE_10, grammarAccess.getStateDefinitionAccess().getSTATETerminalRuleCall_10());
                      			
                    }

                    }
                    break;

            }

            this_SEMI_11=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_11, grammarAccess.getStateDefinitionAccess().getSEMITerminalRuleCall_11());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getStateDefinitionAccess().getPragmaListParserRuleCall_12());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestateDefinition"


    // $ANTLR start "entryRulestatementList"
    // InternalMASL.g:2928:1: entryRulestatementList returns [EObject current=null] : iv_rulestatementList= rulestatementList EOF ;
    public final EObject entryRulestatementList() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestatementList = null;


        try {
            // InternalMASL.g:2928:54: (iv_rulestatementList= rulestatementList EOF )
            // InternalMASL.g:2929:2: iv_rulestatementList= rulestatementList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestatementList=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestatementList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestatementList"


    // $ANTLR start "rulestatementList"
    // InternalMASL.g:2935:1: rulestatementList returns [EObject current=null] : ( () ( (lv_s_1_0= rulestatement ) )* ) ;
    public final EObject rulestatementList() throws RecognitionException {
        EObject current = null;

        EObject lv_s_1_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2941:2: ( ( () ( (lv_s_1_0= rulestatement ) )* ) )
            // InternalMASL.g:2942:2: ( () ( (lv_s_1_0= rulestatement ) )* )
            {
            // InternalMASL.g:2942:2: ( () ( (lv_s_1_0= rulestatement ) )* )
            // InternalMASL.g:2943:3: () ( (lv_s_1_0= rulestatement ) )*
            {
            // InternalMASL.g:2943:3: ()
            // InternalMASL.g:2944:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getStatementListAccess().getStatementListAction_0(),
              					current);
              			
            }

            }

            // InternalMASL.g:2950:3: ( (lv_s_1_0= rulestatement ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==RULE_INSTANCE||LA43_0==RULE_ANONYMOUS||LA43_0==RULE_LPAREN||(LA43_0>=RULE_SEQUENCE && LA43_0<=RULE_DICTIONARY)||LA43_0==RULE_SEMI||LA43_0==RULE_RETURN||LA43_0==RULE_NULL||LA43_0==RULE_EXIT||(LA43_0>=RULE_DELAY && LA43_0<=RULE_ERASE)||(LA43_0>=RULE_LINK && LA43_0<=RULE_SCHEDULE)||LA43_0==RULE_CANCEL||LA43_0==RULE_GENERATE||LA43_0==RULE_IF||LA43_0==RULE_WHILE||LA43_0==RULE_CASE||LA43_0==RULE_FOR||(LA43_0>=RULE_DECLARE && LA43_0<=RULE_BEGIN)||LA43_0==RULE_NOT||(LA43_0>=RULE_PLUS && LA43_0<=RULE_MINUS)||LA43_0==RULE_ABS||LA43_0==RULE_CREATE||(LA43_0>=RULE_FIND && LA43_0<=RULE_IDENT)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalMASL.g:2951:4: (lv_s_1_0= rulestatement )
            	    {
            	    // InternalMASL.g:2951:4: (lv_s_1_0= rulestatement )
            	    // InternalMASL.g:2952:5: lv_s_1_0= rulestatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getStatementListAccess().getSStatementParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_44);
            	    lv_s_1_0=rulestatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getStatementListRule());
            	      					}
            	      					add(
            	      						current,
            	      						"s",
            	      						lv_s_1_0,
            	      						"org.xtuml.bp.ui.xtext.MASL.statement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestatementList"


    // $ANTLR start "entryRulestatement"
    // InternalMASL.g:2973:1: entryRulestatement returns [EObject current=null] : iv_rulestatement= rulestatement EOF ;
    public final EObject entryRulestatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestatement = null;


        try {
            // InternalMASL.g:2973:50: (iv_rulestatement= rulestatement EOF )
            // InternalMASL.g:2974:2: iv_rulestatement= rulestatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestatement=rulestatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestatement"


    // $ANTLR start "rulestatement"
    // InternalMASL.g:2980:1: rulestatement returns [EObject current=null] : ( () ( ( (lv_c_1_0= rulecodeBlockStatement ) ) | ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) ) | ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) ) | ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) ) | ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) ) | ( (lv_e_6_0= ruleexitStatement ) ) | ( (lv_r_7_0= rulereturnStatement ) ) | ( (lv_d_8_0= ruledelayStatement ) ) | ruleraiseStatement | ( (lv_d_10_0= ruledeleteStatement ) ) | ( (lv_e_11_0= ruleeraseStatement ) ) | ( (lv_l_12_0= rulelinkStatement ) ) | ( (lv_s_13_0= rulescheduleStatement ) ) | ( (lv_c_14_0= rulecancelTimerStatement ) ) | ( (lv_g_15_0= rulegenerateStatement ) ) | ( (lv_i_16_0= ruleifStatement ) ) | ( (lv_c_17_0= rulecaseStatement ) ) | ( (lv_f_18_0= ruleforStatement ) ) | ( (lv_w_19_0= rulewhileStatement ) ) )? this_SEMI_20= RULE_SEMI rulepragmaList ) ;
    public final EObject rulestatement() throws RecognitionException {
        EObject current = null;

        Token this_SEMI_20=null;
        EObject lv_c_1_0 = null;

        EObject lv_e_2_0 = null;

        EObject lv_s_3_0 = null;

        AntlrDatatypeRuleToken lv_n_4_0 = null;

        EObject lv_c_5_0 = null;

        EObject lv_e_6_0 = null;

        EObject lv_r_7_0 = null;

        EObject lv_d_8_0 = null;

        EObject lv_d_10_0 = null;

        EObject lv_e_11_0 = null;

        EObject lv_l_12_0 = null;

        EObject lv_s_13_0 = null;

        EObject lv_c_14_0 = null;

        EObject lv_g_15_0 = null;

        EObject lv_i_16_0 = null;

        EObject lv_c_17_0 = null;

        EObject lv_f_18_0 = null;

        EObject lv_w_19_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:2986:2: ( ( () ( ( (lv_c_1_0= rulecodeBlockStatement ) ) | ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) ) | ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) ) | ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) ) | ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) ) | ( (lv_e_6_0= ruleexitStatement ) ) | ( (lv_r_7_0= rulereturnStatement ) ) | ( (lv_d_8_0= ruledelayStatement ) ) | ruleraiseStatement | ( (lv_d_10_0= ruledeleteStatement ) ) | ( (lv_e_11_0= ruleeraseStatement ) ) | ( (lv_l_12_0= rulelinkStatement ) ) | ( (lv_s_13_0= rulescheduleStatement ) ) | ( (lv_c_14_0= rulecancelTimerStatement ) ) | ( (lv_g_15_0= rulegenerateStatement ) ) | ( (lv_i_16_0= ruleifStatement ) ) | ( (lv_c_17_0= rulecaseStatement ) ) | ( (lv_f_18_0= ruleforStatement ) ) | ( (lv_w_19_0= rulewhileStatement ) ) )? this_SEMI_20= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:2987:2: ( () ( ( (lv_c_1_0= rulecodeBlockStatement ) ) | ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) ) | ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) ) | ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) ) | ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) ) | ( (lv_e_6_0= ruleexitStatement ) ) | ( (lv_r_7_0= rulereturnStatement ) ) | ( (lv_d_8_0= ruledelayStatement ) ) | ruleraiseStatement | ( (lv_d_10_0= ruledeleteStatement ) ) | ( (lv_e_11_0= ruleeraseStatement ) ) | ( (lv_l_12_0= rulelinkStatement ) ) | ( (lv_s_13_0= rulescheduleStatement ) ) | ( (lv_c_14_0= rulecancelTimerStatement ) ) | ( (lv_g_15_0= rulegenerateStatement ) ) | ( (lv_i_16_0= ruleifStatement ) ) | ( (lv_c_17_0= rulecaseStatement ) ) | ( (lv_f_18_0= ruleforStatement ) ) | ( (lv_w_19_0= rulewhileStatement ) ) )? this_SEMI_20= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:2987:2: ( () ( ( (lv_c_1_0= rulecodeBlockStatement ) ) | ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) ) | ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) ) | ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) ) | ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) ) | ( (lv_e_6_0= ruleexitStatement ) ) | ( (lv_r_7_0= rulereturnStatement ) ) | ( (lv_d_8_0= ruledelayStatement ) ) | ruleraiseStatement | ( (lv_d_10_0= ruledeleteStatement ) ) | ( (lv_e_11_0= ruleeraseStatement ) ) | ( (lv_l_12_0= rulelinkStatement ) ) | ( (lv_s_13_0= rulescheduleStatement ) ) | ( (lv_c_14_0= rulecancelTimerStatement ) ) | ( (lv_g_15_0= rulegenerateStatement ) ) | ( (lv_i_16_0= ruleifStatement ) ) | ( (lv_c_17_0= rulecaseStatement ) ) | ( (lv_f_18_0= ruleforStatement ) ) | ( (lv_w_19_0= rulewhileStatement ) ) )? this_SEMI_20= RULE_SEMI rulepragmaList )
            // InternalMASL.g:2988:3: () ( ( (lv_c_1_0= rulecodeBlockStatement ) ) | ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) ) | ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) ) | ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) ) | ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) ) | ( (lv_e_6_0= ruleexitStatement ) ) | ( (lv_r_7_0= rulereturnStatement ) ) | ( (lv_d_8_0= ruledelayStatement ) ) | ruleraiseStatement | ( (lv_d_10_0= ruledeleteStatement ) ) | ( (lv_e_11_0= ruleeraseStatement ) ) | ( (lv_l_12_0= rulelinkStatement ) ) | ( (lv_s_13_0= rulescheduleStatement ) ) | ( (lv_c_14_0= rulecancelTimerStatement ) ) | ( (lv_g_15_0= rulegenerateStatement ) ) | ( (lv_i_16_0= ruleifStatement ) ) | ( (lv_c_17_0= rulecaseStatement ) ) | ( (lv_f_18_0= ruleforStatement ) ) | ( (lv_w_19_0= rulewhileStatement ) ) )? this_SEMI_20= RULE_SEMI rulepragmaList
            {
            // InternalMASL.g:2988:3: ()
            // InternalMASL.g:2989:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getStatementAccess().getStatementAction_0(),
              					current);
              			
            }

            }

            // InternalMASL.g:2995:3: ( ( (lv_c_1_0= rulecodeBlockStatement ) ) | ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) ) | ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) ) | ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) ) | ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) ) | ( (lv_e_6_0= ruleexitStatement ) ) | ( (lv_r_7_0= rulereturnStatement ) ) | ( (lv_d_8_0= ruledelayStatement ) ) | ruleraiseStatement | ( (lv_d_10_0= ruledeleteStatement ) ) | ( (lv_e_11_0= ruleeraseStatement ) ) | ( (lv_l_12_0= rulelinkStatement ) ) | ( (lv_s_13_0= rulescheduleStatement ) ) | ( (lv_c_14_0= rulecancelTimerStatement ) ) | ( (lv_g_15_0= rulegenerateStatement ) ) | ( (lv_i_16_0= ruleifStatement ) ) | ( (lv_c_17_0= rulecaseStatement ) ) | ( (lv_f_18_0= ruleforStatement ) ) | ( (lv_w_19_0= rulewhileStatement ) ) )?
            int alt44=20;
            alt44 = dfa44.predict(input);
            switch (alt44) {
                case 1 :
                    // InternalMASL.g:2996:4: ( (lv_c_1_0= rulecodeBlockStatement ) )
                    {
                    // InternalMASL.g:2996:4: ( (lv_c_1_0= rulecodeBlockStatement ) )
                    // InternalMASL.g:2997:5: (lv_c_1_0= rulecodeBlockStatement )
                    {
                    // InternalMASL.g:2997:5: (lv_c_1_0= rulecodeBlockStatement )
                    // InternalMASL.g:2998:6: lv_c_1_0= rulecodeBlockStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getCCodeBlockStatementParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_c_1_0=rulecodeBlockStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"c",
                      							lv_c_1_0,
                      							"org.xtuml.bp.ui.xtext.MASL.codeBlockStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:3016:4: ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) )
                    {
                    // InternalMASL.g:3016:4: ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) )
                    // InternalMASL.g:3017:5: ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) )
                    {
                    // InternalMASL.g:3023:5: ( (lv_e_2_0= ruleassignStatement ) )
                    // InternalMASL.g:3024:6: (lv_e_2_0= ruleassignStatement )
                    {
                    // InternalMASL.g:3024:6: (lv_e_2_0= ruleassignStatement )
                    // InternalMASL.g:3025:7: lv_e_2_0= ruleassignStatement
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getStatementAccess().getEAssignStatementParserRuleCall_1_1_0_0());
                      						
                    }
                    pushFollow(FOLLOW_26);
                    lv_e_2_0=ruleassignStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getStatementRule());
                      							}
                      							set(
                      								current,
                      								"e",
                      								lv_e_2_0,
                      								"org.xtuml.bp.ui.xtext.MASL.assignStatement");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalMASL.g:3044:4: ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) )
                    {
                    // InternalMASL.g:3044:4: ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) )
                    // InternalMASL.g:3045:5: ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) )
                    {
                    // InternalMASL.g:3051:5: ( (lv_s_3_0= rulestreamStatement ) )
                    // InternalMASL.g:3052:6: (lv_s_3_0= rulestreamStatement )
                    {
                    // InternalMASL.g:3052:6: (lv_s_3_0= rulestreamStatement )
                    // InternalMASL.g:3053:7: lv_s_3_0= rulestreamStatement
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getStatementAccess().getSStreamStatementParserRuleCall_1_2_0_0());
                      						
                    }
                    pushFollow(FOLLOW_26);
                    lv_s_3_0=rulestreamStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getStatementRule());
                      							}
                      							set(
                      								current,
                      								"s",
                      								lv_s_3_0,
                      								"org.xtuml.bp.ui.xtext.MASL.streamStatement");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalMASL.g:3072:4: ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) )
                    {
                    // InternalMASL.g:3072:4: ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) )
                    // InternalMASL.g:3073:5: ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) )
                    {
                    // InternalMASL.g:3079:5: ( (lv_n_4_0= rulenullStatement ) )
                    // InternalMASL.g:3080:6: (lv_n_4_0= rulenullStatement )
                    {
                    // InternalMASL.g:3080:6: (lv_n_4_0= rulenullStatement )
                    // InternalMASL.g:3081:7: lv_n_4_0= rulenullStatement
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getStatementAccess().getNNullStatementParserRuleCall_1_3_0_0());
                      						
                    }
                    pushFollow(FOLLOW_26);
                    lv_n_4_0=rulenullStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getStatementRule());
                      							}
                      							set(
                      								current,
                      								"n",
                      								lv_n_4_0,
                      								"org.xtuml.bp.ui.xtext.MASL.nullStatement");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalMASL.g:3100:4: ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) )
                    {
                    // InternalMASL.g:3100:4: ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) )
                    // InternalMASL.g:3101:5: ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) )
                    {
                    // InternalMASL.g:3107:5: ( (lv_c_5_0= rulecallStatement ) )
                    // InternalMASL.g:3108:6: (lv_c_5_0= rulecallStatement )
                    {
                    // InternalMASL.g:3108:6: (lv_c_5_0= rulecallStatement )
                    // InternalMASL.g:3109:7: lv_c_5_0= rulecallStatement
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getStatementAccess().getCCallStatementParserRuleCall_1_4_0_0());
                      						
                    }
                    pushFollow(FOLLOW_26);
                    lv_c_5_0=rulecallStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getStatementRule());
                      							}
                      							set(
                      								current,
                      								"c",
                      								lv_c_5_0,
                      								"org.xtuml.bp.ui.xtext.MASL.callStatement");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalMASL.g:3128:4: ( (lv_e_6_0= ruleexitStatement ) )
                    {
                    // InternalMASL.g:3128:4: ( (lv_e_6_0= ruleexitStatement ) )
                    // InternalMASL.g:3129:5: (lv_e_6_0= ruleexitStatement )
                    {
                    // InternalMASL.g:3129:5: (lv_e_6_0= ruleexitStatement )
                    // InternalMASL.g:3130:6: lv_e_6_0= ruleexitStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getEExitStatementParserRuleCall_1_5_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_e_6_0=ruleexitStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"e",
                      							lv_e_6_0,
                      							"org.xtuml.bp.ui.xtext.MASL.exitStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalMASL.g:3148:4: ( (lv_r_7_0= rulereturnStatement ) )
                    {
                    // InternalMASL.g:3148:4: ( (lv_r_7_0= rulereturnStatement ) )
                    // InternalMASL.g:3149:5: (lv_r_7_0= rulereturnStatement )
                    {
                    // InternalMASL.g:3149:5: (lv_r_7_0= rulereturnStatement )
                    // InternalMASL.g:3150:6: lv_r_7_0= rulereturnStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getRReturnStatementParserRuleCall_1_6_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_r_7_0=rulereturnStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"r",
                      							lv_r_7_0,
                      							"org.xtuml.bp.ui.xtext.MASL.returnStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 8 :
                    // InternalMASL.g:3168:4: ( (lv_d_8_0= ruledelayStatement ) )
                    {
                    // InternalMASL.g:3168:4: ( (lv_d_8_0= ruledelayStatement ) )
                    // InternalMASL.g:3169:5: (lv_d_8_0= ruledelayStatement )
                    {
                    // InternalMASL.g:3169:5: (lv_d_8_0= ruledelayStatement )
                    // InternalMASL.g:3170:6: lv_d_8_0= ruledelayStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getDDelayStatementParserRuleCall_1_7_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_d_8_0=ruledelayStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"d",
                      							lv_d_8_0,
                      							"org.xtuml.bp.ui.xtext.MASL.delayStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 9 :
                    // InternalMASL.g:3188:4: ruleraiseStatement
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getStatementAccess().getRaiseStatementParserRuleCall_1_8());
                      			
                    }
                    pushFollow(FOLLOW_26);
                    ruleraiseStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;
                case 10 :
                    // InternalMASL.g:3196:4: ( (lv_d_10_0= ruledeleteStatement ) )
                    {
                    // InternalMASL.g:3196:4: ( (lv_d_10_0= ruledeleteStatement ) )
                    // InternalMASL.g:3197:5: (lv_d_10_0= ruledeleteStatement )
                    {
                    // InternalMASL.g:3197:5: (lv_d_10_0= ruledeleteStatement )
                    // InternalMASL.g:3198:6: lv_d_10_0= ruledeleteStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getDDeleteStatementParserRuleCall_1_9_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_d_10_0=ruledeleteStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"d",
                      							lv_d_10_0,
                      							"org.xtuml.bp.ui.xtext.MASL.deleteStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 11 :
                    // InternalMASL.g:3216:4: ( (lv_e_11_0= ruleeraseStatement ) )
                    {
                    // InternalMASL.g:3216:4: ( (lv_e_11_0= ruleeraseStatement ) )
                    // InternalMASL.g:3217:5: (lv_e_11_0= ruleeraseStatement )
                    {
                    // InternalMASL.g:3217:5: (lv_e_11_0= ruleeraseStatement )
                    // InternalMASL.g:3218:6: lv_e_11_0= ruleeraseStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getEEraseStatementParserRuleCall_1_10_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_e_11_0=ruleeraseStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"e",
                      							lv_e_11_0,
                      							"org.xtuml.bp.ui.xtext.MASL.eraseStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 12 :
                    // InternalMASL.g:3236:4: ( (lv_l_12_0= rulelinkStatement ) )
                    {
                    // InternalMASL.g:3236:4: ( (lv_l_12_0= rulelinkStatement ) )
                    // InternalMASL.g:3237:5: (lv_l_12_0= rulelinkStatement )
                    {
                    // InternalMASL.g:3237:5: (lv_l_12_0= rulelinkStatement )
                    // InternalMASL.g:3238:6: lv_l_12_0= rulelinkStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getLLinkStatementParserRuleCall_1_11_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_l_12_0=rulelinkStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"l",
                      							lv_l_12_0,
                      							"org.xtuml.bp.ui.xtext.MASL.linkStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 13 :
                    // InternalMASL.g:3256:4: ( (lv_s_13_0= rulescheduleStatement ) )
                    {
                    // InternalMASL.g:3256:4: ( (lv_s_13_0= rulescheduleStatement ) )
                    // InternalMASL.g:3257:5: (lv_s_13_0= rulescheduleStatement )
                    {
                    // InternalMASL.g:3257:5: (lv_s_13_0= rulescheduleStatement )
                    // InternalMASL.g:3258:6: lv_s_13_0= rulescheduleStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getSScheduleStatementParserRuleCall_1_12_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_s_13_0=rulescheduleStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"s",
                      							lv_s_13_0,
                      							"org.xtuml.bp.ui.xtext.MASL.scheduleStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 14 :
                    // InternalMASL.g:3276:4: ( (lv_c_14_0= rulecancelTimerStatement ) )
                    {
                    // InternalMASL.g:3276:4: ( (lv_c_14_0= rulecancelTimerStatement ) )
                    // InternalMASL.g:3277:5: (lv_c_14_0= rulecancelTimerStatement )
                    {
                    // InternalMASL.g:3277:5: (lv_c_14_0= rulecancelTimerStatement )
                    // InternalMASL.g:3278:6: lv_c_14_0= rulecancelTimerStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getCCancelTimerStatementParserRuleCall_1_13_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_c_14_0=rulecancelTimerStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"c",
                      							lv_c_14_0,
                      							"org.xtuml.bp.ui.xtext.MASL.cancelTimerStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 15 :
                    // InternalMASL.g:3296:4: ( (lv_g_15_0= rulegenerateStatement ) )
                    {
                    // InternalMASL.g:3296:4: ( (lv_g_15_0= rulegenerateStatement ) )
                    // InternalMASL.g:3297:5: (lv_g_15_0= rulegenerateStatement )
                    {
                    // InternalMASL.g:3297:5: (lv_g_15_0= rulegenerateStatement )
                    // InternalMASL.g:3298:6: lv_g_15_0= rulegenerateStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getGGenerateStatementParserRuleCall_1_14_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_g_15_0=rulegenerateStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"g",
                      							lv_g_15_0,
                      							"org.xtuml.bp.ui.xtext.MASL.generateStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 16 :
                    // InternalMASL.g:3316:4: ( (lv_i_16_0= ruleifStatement ) )
                    {
                    // InternalMASL.g:3316:4: ( (lv_i_16_0= ruleifStatement ) )
                    // InternalMASL.g:3317:5: (lv_i_16_0= ruleifStatement )
                    {
                    // InternalMASL.g:3317:5: (lv_i_16_0= ruleifStatement )
                    // InternalMASL.g:3318:6: lv_i_16_0= ruleifStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getIIfStatementParserRuleCall_1_15_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_i_16_0=ruleifStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"i",
                      							lv_i_16_0,
                      							"org.xtuml.bp.ui.xtext.MASL.ifStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 17 :
                    // InternalMASL.g:3336:4: ( (lv_c_17_0= rulecaseStatement ) )
                    {
                    // InternalMASL.g:3336:4: ( (lv_c_17_0= rulecaseStatement ) )
                    // InternalMASL.g:3337:5: (lv_c_17_0= rulecaseStatement )
                    {
                    // InternalMASL.g:3337:5: (lv_c_17_0= rulecaseStatement )
                    // InternalMASL.g:3338:6: lv_c_17_0= rulecaseStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getCCaseStatementParserRuleCall_1_16_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_c_17_0=rulecaseStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"c",
                      							lv_c_17_0,
                      							"org.xtuml.bp.ui.xtext.MASL.caseStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 18 :
                    // InternalMASL.g:3356:4: ( (lv_f_18_0= ruleforStatement ) )
                    {
                    // InternalMASL.g:3356:4: ( (lv_f_18_0= ruleforStatement ) )
                    // InternalMASL.g:3357:5: (lv_f_18_0= ruleforStatement )
                    {
                    // InternalMASL.g:3357:5: (lv_f_18_0= ruleforStatement )
                    // InternalMASL.g:3358:6: lv_f_18_0= ruleforStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getFForStatementParserRuleCall_1_17_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_f_18_0=ruleforStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"f",
                      							lv_f_18_0,
                      							"org.xtuml.bp.ui.xtext.MASL.forStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 19 :
                    // InternalMASL.g:3376:4: ( (lv_w_19_0= rulewhileStatement ) )
                    {
                    // InternalMASL.g:3376:4: ( (lv_w_19_0= rulewhileStatement ) )
                    // InternalMASL.g:3377:5: (lv_w_19_0= rulewhileStatement )
                    {
                    // InternalMASL.g:3377:5: (lv_w_19_0= rulewhileStatement )
                    // InternalMASL.g:3378:6: lv_w_19_0= rulewhileStatement
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getStatementAccess().getWWhileStatementParserRuleCall_1_18_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_w_19_0=rulewhileStatement();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getStatementRule());
                      						}
                      						set(
                      							current,
                      							"w",
                      							lv_w_19_0,
                      							"org.xtuml.bp.ui.xtext.MASL.whileStatement");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            this_SEMI_20=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_20, grammarAccess.getStatementAccess().getSEMITerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getStatementAccess().getPragmaListParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestatement"


    // $ANTLR start "entryRulenullStatement"
    // InternalMASL.g:3411:1: entryRulenullStatement returns [String current=null] : iv_rulenullStatement= rulenullStatement EOF ;
    public final String entryRulenullStatement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulenullStatement = null;


        try {
            // InternalMASL.g:3411:53: (iv_rulenullStatement= rulenullStatement EOF )
            // InternalMASL.g:3412:2: iv_rulenullStatement= rulenullStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNullStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulenullStatement=rulenullStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulenullStatement.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulenullStatement"


    // $ANTLR start "rulenullStatement"
    // InternalMASL.g:3418:1: rulenullStatement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_NULL_0= RULE_NULL ;
    public final AntlrDatatypeRuleToken rulenullStatement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_NULL_0=null;


        	enterRule();

        try {
            // InternalMASL.g:3424:2: (this_NULL_0= RULE_NULL )
            // InternalMASL.g:3425:2: this_NULL_0= RULE_NULL
            {
            this_NULL_0=(Token)match(input,RULE_NULL,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_NULL_0);
              	
            }
            if ( state.backtracking==0 ) {

              		newLeafNode(this_NULL_0, grammarAccess.getNullStatementAccess().getNULLTerminalRuleCall());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulenullStatement"


    // $ANTLR start "entryRuleassignStatement"
    // InternalMASL.g:3435:1: entryRuleassignStatement returns [EObject current=null] : iv_ruleassignStatement= ruleassignStatement EOF ;
    public final EObject entryRuleassignStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleassignStatement = null;


        try {
            // InternalMASL.g:3435:56: (iv_ruleassignStatement= ruleassignStatement EOF )
            // InternalMASL.g:3436:2: iv_ruleassignStatement= ruleassignStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAssignStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleassignStatement=ruleassignStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleassignStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleassignStatement"


    // $ANTLR start "ruleassignStatement"
    // InternalMASL.g:3442:1: ruleassignStatement returns [EObject current=null] : ( ( (lv_lhs_0_0= ruleexpression ) ) this_ASSIGN_1= RULE_ASSIGN ( (lv_rhs_2_0= ruleexpression ) ) ) ;
    public final EObject ruleassignStatement() throws RecognitionException {
        EObject current = null;

        Token this_ASSIGN_1=null;
        EObject lv_lhs_0_0 = null;

        EObject lv_rhs_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:3448:2: ( ( ( (lv_lhs_0_0= ruleexpression ) ) this_ASSIGN_1= RULE_ASSIGN ( (lv_rhs_2_0= ruleexpression ) ) ) )
            // InternalMASL.g:3449:2: ( ( (lv_lhs_0_0= ruleexpression ) ) this_ASSIGN_1= RULE_ASSIGN ( (lv_rhs_2_0= ruleexpression ) ) )
            {
            // InternalMASL.g:3449:2: ( ( (lv_lhs_0_0= ruleexpression ) ) this_ASSIGN_1= RULE_ASSIGN ( (lv_rhs_2_0= ruleexpression ) ) )
            // InternalMASL.g:3450:3: ( (lv_lhs_0_0= ruleexpression ) ) this_ASSIGN_1= RULE_ASSIGN ( (lv_rhs_2_0= ruleexpression ) )
            {
            // InternalMASL.g:3450:3: ( (lv_lhs_0_0= ruleexpression ) )
            // InternalMASL.g:3451:4: (lv_lhs_0_0= ruleexpression )
            {
            // InternalMASL.g:3451:4: (lv_lhs_0_0= ruleexpression )
            // InternalMASL.g:3452:5: lv_lhs_0_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getAssignStatementAccess().getLhsExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_45);
            lv_lhs_0_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getAssignStatementRule());
              					}
              					set(
              						current,
              						"lhs",
              						lv_lhs_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_ASSIGN_1=(Token)match(input,RULE_ASSIGN,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ASSIGN_1, grammarAccess.getAssignStatementAccess().getASSIGNTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:3473:3: ( (lv_rhs_2_0= ruleexpression ) )
            // InternalMASL.g:3474:4: (lv_rhs_2_0= ruleexpression )
            {
            // InternalMASL.g:3474:4: (lv_rhs_2_0= ruleexpression )
            // InternalMASL.g:3475:5: lv_rhs_2_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getAssignStatementAccess().getRhsExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_rhs_2_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getAssignStatementRule());
              					}
              					set(
              						current,
              						"rhs",
              						lv_rhs_2_0,
              						"org.xtuml.bp.ui.xtext.MASL.expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleassignStatement"


    // $ANTLR start "entryRulestreamOperator"
    // InternalMASL.g:3496:1: entryRulestreamOperator returns [String current=null] : iv_rulestreamOperator= rulestreamOperator EOF ;
    public final String entryRulestreamOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulestreamOperator = null;


        try {
            // InternalMASL.g:3496:54: (iv_rulestreamOperator= rulestreamOperator EOF )
            // InternalMASL.g:3497:2: iv_rulestreamOperator= rulestreamOperator EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStreamOperatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestreamOperator=rulestreamOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestreamOperator.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestreamOperator"


    // $ANTLR start "rulestreamOperator"
    // InternalMASL.g:3503:1: rulestreamOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STREAM_IN_0= RULE_STREAM_IN | this_STREAM_OUT_1= RULE_STREAM_OUT | this_STREAM_LINE_IN_2= RULE_STREAM_LINE_IN | this_STREAM_LINE_OUT_3= RULE_STREAM_LINE_OUT ) ;
    public final AntlrDatatypeRuleToken rulestreamOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STREAM_IN_0=null;
        Token this_STREAM_OUT_1=null;
        Token this_STREAM_LINE_IN_2=null;
        Token this_STREAM_LINE_OUT_3=null;


        	enterRule();

        try {
            // InternalMASL.g:3509:2: ( (this_STREAM_IN_0= RULE_STREAM_IN | this_STREAM_OUT_1= RULE_STREAM_OUT | this_STREAM_LINE_IN_2= RULE_STREAM_LINE_IN | this_STREAM_LINE_OUT_3= RULE_STREAM_LINE_OUT ) )
            // InternalMASL.g:3510:2: (this_STREAM_IN_0= RULE_STREAM_IN | this_STREAM_OUT_1= RULE_STREAM_OUT | this_STREAM_LINE_IN_2= RULE_STREAM_LINE_IN | this_STREAM_LINE_OUT_3= RULE_STREAM_LINE_OUT )
            {
            // InternalMASL.g:3510:2: (this_STREAM_IN_0= RULE_STREAM_IN | this_STREAM_OUT_1= RULE_STREAM_OUT | this_STREAM_LINE_IN_2= RULE_STREAM_LINE_IN | this_STREAM_LINE_OUT_3= RULE_STREAM_LINE_OUT )
            int alt45=4;
            switch ( input.LA(1) ) {
            case RULE_STREAM_IN:
                {
                alt45=1;
                }
                break;
            case RULE_STREAM_OUT:
                {
                alt45=2;
                }
                break;
            case RULE_STREAM_LINE_IN:
                {
                alt45=3;
                }
                break;
            case RULE_STREAM_LINE_OUT:
                {
                alt45=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // InternalMASL.g:3511:3: this_STREAM_IN_0= RULE_STREAM_IN
                    {
                    this_STREAM_IN_0=(Token)match(input,RULE_STREAM_IN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STREAM_IN_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_STREAM_IN_0, grammarAccess.getStreamOperatorAccess().getSTREAM_INTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:3519:3: this_STREAM_OUT_1= RULE_STREAM_OUT
                    {
                    this_STREAM_OUT_1=(Token)match(input,RULE_STREAM_OUT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STREAM_OUT_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_STREAM_OUT_1, grammarAccess.getStreamOperatorAccess().getSTREAM_OUTTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:3527:3: this_STREAM_LINE_IN_2= RULE_STREAM_LINE_IN
                    {
                    this_STREAM_LINE_IN_2=(Token)match(input,RULE_STREAM_LINE_IN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STREAM_LINE_IN_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_STREAM_LINE_IN_2, grammarAccess.getStreamOperatorAccess().getSTREAM_LINE_INTerminalRuleCall_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalMASL.g:3535:3: this_STREAM_LINE_OUT_3= RULE_STREAM_LINE_OUT
                    {
                    this_STREAM_LINE_OUT_3=(Token)match(input,RULE_STREAM_LINE_OUT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STREAM_LINE_OUT_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_STREAM_LINE_OUT_3, grammarAccess.getStreamOperatorAccess().getSTREAM_LINE_OUTTerminalRuleCall_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestreamOperator"


    // $ANTLR start "entryRulestreamValue"
    // InternalMASL.g:3546:1: entryRulestreamValue returns [EObject current=null] : iv_rulestreamValue= rulestreamValue EOF ;
    public final EObject entryRulestreamValue() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestreamValue = null;


        try {
            // InternalMASL.g:3546:52: (iv_rulestreamValue= rulestreamValue EOF )
            // InternalMASL.g:3547:2: iv_rulestreamValue= rulestreamValue EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStreamValueRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestreamValue=rulestreamValue();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestreamValue; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestreamValue"


    // $ANTLR start "rulestreamValue"
    // InternalMASL.g:3553:1: rulestreamValue returns [EObject current=null] : ( rulestreamOperator this_expression_1= ruleexpression ) ;
    public final EObject rulestreamValue() throws RecognitionException {
        EObject current = null;

        EObject this_expression_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:3559:2: ( ( rulestreamOperator this_expression_1= ruleexpression ) )
            // InternalMASL.g:3560:2: ( rulestreamOperator this_expression_1= ruleexpression )
            {
            // InternalMASL.g:3560:2: ( rulestreamOperator this_expression_1= ruleexpression )
            // InternalMASL.g:3561:3: rulestreamOperator this_expression_1= ruleexpression
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getStreamValueAccess().getStreamOperatorParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_8);
            rulestreamOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getStreamValueAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestreamValue"


    // $ANTLR start "entryRulestreamStatement"
    // InternalMASL.g:3580:1: entryRulestreamStatement returns [EObject current=null] : iv_rulestreamStatement= rulestreamStatement EOF ;
    public final EObject entryRulestreamStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestreamStatement = null;


        try {
            // InternalMASL.g:3580:56: (iv_rulestreamStatement= rulestreamStatement EOF )
            // InternalMASL.g:3581:2: iv_rulestreamStatement= rulestreamStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStreamStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulestreamStatement=rulestreamStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulestreamStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulestreamStatement"


    // $ANTLR start "rulestreamStatement"
    // InternalMASL.g:3587:1: rulestreamStatement returns [EObject current=null] : ( ( (lv_e_0_0= ruleexpression ) ) ( (lv_s_1_0= rulestreamValue ) )+ ) ;
    public final EObject rulestreamStatement() throws RecognitionException {
        EObject current = null;

        EObject lv_e_0_0 = null;

        EObject lv_s_1_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:3593:2: ( ( ( (lv_e_0_0= ruleexpression ) ) ( (lv_s_1_0= rulestreamValue ) )+ ) )
            // InternalMASL.g:3594:2: ( ( (lv_e_0_0= ruleexpression ) ) ( (lv_s_1_0= rulestreamValue ) )+ )
            {
            // InternalMASL.g:3594:2: ( ( (lv_e_0_0= ruleexpression ) ) ( (lv_s_1_0= rulestreamValue ) )+ )
            // InternalMASL.g:3595:3: ( (lv_e_0_0= ruleexpression ) ) ( (lv_s_1_0= rulestreamValue ) )+
            {
            // InternalMASL.g:3595:3: ( (lv_e_0_0= ruleexpression ) )
            // InternalMASL.g:3596:4: (lv_e_0_0= ruleexpression )
            {
            // InternalMASL.g:3596:4: (lv_e_0_0= ruleexpression )
            // InternalMASL.g:3597:5: lv_e_0_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStreamStatementAccess().getEExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_46);
            lv_e_0_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStreamStatementRule());
              					}
              					set(
              						current,
              						"e",
              						lv_e_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:3614:3: ( (lv_s_1_0= rulestreamValue ) )+
            int cnt46=0;
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=RULE_STREAM_IN && LA46_0<=RULE_STREAM_LINE_OUT)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalMASL.g:3615:4: (lv_s_1_0= rulestreamValue )
            	    {
            	    // InternalMASL.g:3615:4: (lv_s_1_0= rulestreamValue )
            	    // InternalMASL.g:3616:5: lv_s_1_0= rulestreamValue
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getStreamStatementAccess().getSStreamValueParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_47);
            	    lv_s_1_0=rulestreamValue();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getStreamStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"s",
            	      						lv_s_1_0,
            	      						"org.xtuml.bp.ui.xtext.MASL.streamValue");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt46 >= 1 ) break loop46;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(46, input);
                        throw eee;
                }
                cnt46++;
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulestreamStatement"


    // $ANTLR start "entryRulecallStatement"
    // InternalMASL.g:3637:1: entryRulecallStatement returns [EObject current=null] : iv_rulecallStatement= rulecallStatement EOF ;
    public final EObject entryRulecallStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecallStatement = null;


        try {
            // InternalMASL.g:3637:54: (iv_rulecallStatement= rulecallStatement EOF )
            // InternalMASL.g:3638:2: iv_rulecallStatement= rulecallStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCallStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecallStatement=rulecallStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecallStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecallStatement"


    // $ANTLR start "rulecallStatement"
    // InternalMASL.g:3644:1: rulecallStatement returns [EObject current=null] : ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )* this_LPAREN_10= RULE_LPAREN ( (lv_a_11_0= ruleargumentList ) ) this_RPAREN_12= RULE_RPAREN )+ ) ;
    public final EObject rulecallStatement() throws RecognitionException {
        EObject current = null;

        Token this_DOT_1=null;
        Token this_TERMINATOR_SCOPE_3=null;
        Token this_LBRACKET_5=null;
        Token this_RBRACKET_7=null;
        Token this_PRIME_8=null;
        Token this_LPAREN_10=null;
        Token this_RPAREN_12=null;
        EObject lv_p_0_0 = null;

        EObject lv_e_6_0 = null;

        AntlrDatatypeRuleToken lv_c_9_0 = null;

        EObject lv_a_11_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:3650:2: ( ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )* this_LPAREN_10= RULE_LPAREN ( (lv_a_11_0= ruleargumentList ) ) this_RPAREN_12= RULE_RPAREN )+ ) )
            // InternalMASL.g:3651:2: ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )* this_LPAREN_10= RULE_LPAREN ( (lv_a_11_0= ruleargumentList ) ) this_RPAREN_12= RULE_RPAREN )+ )
            {
            // InternalMASL.g:3651:2: ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )* this_LPAREN_10= RULE_LPAREN ( (lv_a_11_0= ruleargumentList ) ) this_RPAREN_12= RULE_RPAREN )+ )
            // InternalMASL.g:3652:3: ( (lv_p_0_0= ruleprimaryExpression ) ) ( ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )* this_LPAREN_10= RULE_LPAREN ( (lv_a_11_0= ruleargumentList ) ) this_RPAREN_12= RULE_RPAREN )+
            {
            // InternalMASL.g:3652:3: ( (lv_p_0_0= ruleprimaryExpression ) )
            // InternalMASL.g:3653:4: (lv_p_0_0= ruleprimaryExpression )
            {
            // InternalMASL.g:3653:4: (lv_p_0_0= ruleprimaryExpression )
            // InternalMASL.g:3654:5: lv_p_0_0= ruleprimaryExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getCallStatementAccess().getPPrimaryExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_48);
            lv_p_0_0=ruleprimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getCallStatementRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.primaryExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:3671:3: ( ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )* this_LPAREN_10= RULE_LPAREN ( (lv_a_11_0= ruleargumentList ) ) this_RPAREN_12= RULE_RPAREN )+
            int cnt48=0;
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==RULE_LPAREN||LA48_0==RULE_DOT||LA48_0==RULE_TERMINATOR_SCOPE||LA48_0==RULE_LBRACKET||LA48_0==RULE_PRIME) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalMASL.g:3672:4: ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )* this_LPAREN_10= RULE_LPAREN ( (lv_a_11_0= ruleargumentList ) ) this_RPAREN_12= RULE_RPAREN
            	    {
            	    // InternalMASL.g:3672:4: ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) ) )*
            	    loop47:
            	    do {
            	        int alt47=5;
            	        switch ( input.LA(1) ) {
            	        case RULE_DOT:
            	            {
            	            alt47=1;
            	            }
            	            break;
            	        case RULE_TERMINATOR_SCOPE:
            	            {
            	            alt47=2;
            	            }
            	            break;
            	        case RULE_LBRACKET:
            	            {
            	            alt47=3;
            	            }
            	            break;
            	        case RULE_PRIME:
            	            {
            	            alt47=4;
            	            }
            	            break;

            	        }

            	        switch (alt47) {
            	    	case 1 :
            	    	    // InternalMASL.g:3673:5: (this_DOT_1= RULE_DOT ruleidentifier )
            	    	    {
            	    	    // InternalMASL.g:3673:5: (this_DOT_1= RULE_DOT ruleidentifier )
            	    	    // InternalMASL.g:3674:6: this_DOT_1= RULE_DOT ruleidentifier
            	    	    {
            	    	    this_DOT_1=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(this_DOT_1, grammarAccess.getCallStatementAccess().getDOTTerminalRuleCall_1_0_0_0());
            	    	      					
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      						newCompositeNode(grammarAccess.getCallStatementAccess().getIdentifierParserRuleCall_1_0_0_1());
            	    	      					
            	    	    }
            	    	    pushFollow(FOLLOW_48);
            	    	    ruleidentifier();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						afterParserOrEnumRuleCall();
            	    	      					
            	    	    }

            	    	    }


            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // InternalMASL.g:3687:5: (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier )
            	    	    {
            	    	    // InternalMASL.g:3687:5: (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier )
            	    	    // InternalMASL.g:3688:6: this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier
            	    	    {
            	    	    this_TERMINATOR_SCOPE_3=(Token)match(input,RULE_TERMINATOR_SCOPE,FOLLOW_4); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(this_TERMINATOR_SCOPE_3, grammarAccess.getCallStatementAccess().getTERMINATOR_SCOPETerminalRuleCall_1_0_1_0());
            	    	      					
            	    	    }
            	    	    if ( state.backtracking==0 ) {

            	    	      						newCompositeNode(grammarAccess.getCallStatementAccess().getIdentifierParserRuleCall_1_0_1_1());
            	    	      					
            	    	    }
            	    	    pushFollow(FOLLOW_48);
            	    	    ruleidentifier();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						afterParserOrEnumRuleCall();
            	    	      					
            	    	    }

            	    	    }


            	    	    }
            	    	    break;
            	    	case 3 :
            	    	    // InternalMASL.g:3701:5: (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET )
            	    	    {
            	    	    // InternalMASL.g:3701:5: (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET )
            	    	    // InternalMASL.g:3702:6: this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET
            	    	    {
            	    	    this_LBRACKET_5=(Token)match(input,RULE_LBRACKET,FOLLOW_8); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(this_LBRACKET_5, grammarAccess.getCallStatementAccess().getLBRACKETTerminalRuleCall_1_0_2_0());
            	    	      					
            	    	    }
            	    	    // InternalMASL.g:3706:6: ( (lv_e_6_0= ruleexpression ) )
            	    	    // InternalMASL.g:3707:7: (lv_e_6_0= ruleexpression )
            	    	    {
            	    	    // InternalMASL.g:3707:7: (lv_e_6_0= ruleexpression )
            	    	    // InternalMASL.g:3708:8: lv_e_6_0= ruleexpression
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getCallStatementAccess().getEExpressionParserRuleCall_1_0_2_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_49);
            	    	    lv_e_6_0=ruleexpression();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      								if (current==null) {
            	    	      									current = createModelElementForParent(grammarAccess.getCallStatementRule());
            	    	      								}
            	    	      								add(
            	    	      									current,
            	    	      									"e",
            	    	      									lv_e_6_0,
            	    	      									"org.xtuml.bp.ui.xtext.MASL.expression");
            	    	      								afterParserOrEnumRuleCall();
            	    	      							
            	    	    }

            	    	    }


            	    	    }

            	    	    this_RBRACKET_7=(Token)match(input,RULE_RBRACKET,FOLLOW_48); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(this_RBRACKET_7, grammarAccess.getCallStatementAccess().getRBRACKETTerminalRuleCall_1_0_2_2());
            	    	      					
            	    	    }

            	    	    }


            	    	    }
            	    	    break;
            	    	case 4 :
            	    	    // InternalMASL.g:3731:5: (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) )
            	    	    {
            	    	    // InternalMASL.g:3731:5: (this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) ) )
            	    	    // InternalMASL.g:3732:6: this_PRIME_8= RULE_PRIME ( (lv_c_9_0= rulecharacteristic ) )
            	    	    {
            	    	    this_PRIME_8=(Token)match(input,RULE_PRIME,FOLLOW_50); if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      						newLeafNode(this_PRIME_8, grammarAccess.getCallStatementAccess().getPRIMETerminalRuleCall_1_0_3_0());
            	    	      					
            	    	    }
            	    	    // InternalMASL.g:3736:6: ( (lv_c_9_0= rulecharacteristic ) )
            	    	    // InternalMASL.g:3737:7: (lv_c_9_0= rulecharacteristic )
            	    	    {
            	    	    // InternalMASL.g:3737:7: (lv_c_9_0= rulecharacteristic )
            	    	    // InternalMASL.g:3738:8: lv_c_9_0= rulecharacteristic
            	    	    {
            	    	    if ( state.backtracking==0 ) {

            	    	      								newCompositeNode(grammarAccess.getCallStatementAccess().getCCharacteristicParserRuleCall_1_0_3_1_0());
            	    	      							
            	    	    }
            	    	    pushFollow(FOLLOW_48);
            	    	    lv_c_9_0=rulecharacteristic();

            	    	    state._fsp--;
            	    	    if (state.failed) return current;
            	    	    if ( state.backtracking==0 ) {

            	    	      								if (current==null) {
            	    	      									current = createModelElementForParent(grammarAccess.getCallStatementRule());
            	    	      								}
            	    	      								add(
            	    	      									current,
            	    	      									"c",
            	    	      									lv_c_9_0,
            	    	      									"org.xtuml.bp.ui.xtext.MASL.characteristic");
            	    	      								afterParserOrEnumRuleCall();
            	    	      							
            	    	    }

            	    	    }


            	    	    }


            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop47;
            	        }
            	    } while (true);

            	    this_LPAREN_10=(Token)match(input,RULE_LPAREN,FOLLOW_51); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_LPAREN_10, grammarAccess.getCallStatementAccess().getLPARENTerminalRuleCall_1_1());
            	      			
            	    }
            	    // InternalMASL.g:3761:4: ( (lv_a_11_0= ruleargumentList ) )
            	    // InternalMASL.g:3762:5: (lv_a_11_0= ruleargumentList )
            	    {
            	    // InternalMASL.g:3762:5: (lv_a_11_0= ruleargumentList )
            	    // InternalMASL.g:3763:6: lv_a_11_0= ruleargumentList
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getCallStatementAccess().getAArgumentListParserRuleCall_1_2_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_a_11_0=ruleargumentList();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getCallStatementRule());
            	      						}
            	      						add(
            	      							current,
            	      							"a",
            	      							lv_a_11_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.argumentList");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }

            	    this_RPAREN_12=(Token)match(input,RULE_RPAREN,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_RPAREN_12, grammarAccess.getCallStatementAccess().getRPARENTerminalRuleCall_1_3());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt48 >= 1 ) break loop48;
            	    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(48, input);
                        throw eee;
                }
                cnt48++;
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecallStatement"


    // $ANTLR start "entryRuleexitStatement"
    // InternalMASL.g:3789:1: entryRuleexitStatement returns [EObject current=null] : iv_ruleexitStatement= ruleexitStatement EOF ;
    public final EObject entryRuleexitStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleexitStatement = null;


        try {
            // InternalMASL.g:3789:54: (iv_ruleexitStatement= ruleexitStatement EOF )
            // InternalMASL.g:3790:2: iv_ruleexitStatement= ruleexitStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExitStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleexitStatement=ruleexitStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleexitStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleexitStatement"


    // $ANTLR start "ruleexitStatement"
    // InternalMASL.g:3796:1: ruleexitStatement returns [EObject current=null] : ( () this_EXIT_1= RULE_EXIT (this_WHEN_2= RULE_WHEN ( (lv_c_3_0= rulecondition ) ) )? ) ;
    public final EObject ruleexitStatement() throws RecognitionException {
        EObject current = null;

        Token this_EXIT_1=null;
        Token this_WHEN_2=null;
        EObject lv_c_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:3802:2: ( ( () this_EXIT_1= RULE_EXIT (this_WHEN_2= RULE_WHEN ( (lv_c_3_0= rulecondition ) ) )? ) )
            // InternalMASL.g:3803:2: ( () this_EXIT_1= RULE_EXIT (this_WHEN_2= RULE_WHEN ( (lv_c_3_0= rulecondition ) ) )? )
            {
            // InternalMASL.g:3803:2: ( () this_EXIT_1= RULE_EXIT (this_WHEN_2= RULE_WHEN ( (lv_c_3_0= rulecondition ) ) )? )
            // InternalMASL.g:3804:3: () this_EXIT_1= RULE_EXIT (this_WHEN_2= RULE_WHEN ( (lv_c_3_0= rulecondition ) ) )?
            {
            // InternalMASL.g:3804:3: ()
            // InternalMASL.g:3805:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getExitStatementAccess().getExitStatementAction_0(),
              					current);
              			
            }

            }

            this_EXIT_1=(Token)match(input,RULE_EXIT,FOLLOW_53); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_EXIT_1, grammarAccess.getExitStatementAccess().getEXITTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:3815:3: (this_WHEN_2= RULE_WHEN ( (lv_c_3_0= rulecondition ) ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==RULE_WHEN) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMASL.g:3816:4: this_WHEN_2= RULE_WHEN ( (lv_c_3_0= rulecondition ) )
                    {
                    this_WHEN_2=(Token)match(input,RULE_WHEN,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_WHEN_2, grammarAccess.getExitStatementAccess().getWHENTerminalRuleCall_2_0());
                      			
                    }
                    // InternalMASL.g:3820:4: ( (lv_c_3_0= rulecondition ) )
                    // InternalMASL.g:3821:5: (lv_c_3_0= rulecondition )
                    {
                    // InternalMASL.g:3821:5: (lv_c_3_0= rulecondition )
                    // InternalMASL.g:3822:6: lv_c_3_0= rulecondition
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getExitStatementAccess().getCConditionParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_c_3_0=rulecondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getExitStatementRule());
                      						}
                      						set(
                      							current,
                      							"c",
                      							lv_c_3_0,
                      							"org.xtuml.bp.ui.xtext.MASL.condition");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleexitStatement"


    // $ANTLR start "entryRulereturnStatement"
    // InternalMASL.g:3844:1: entryRulereturnStatement returns [EObject current=null] : iv_rulereturnStatement= rulereturnStatement EOF ;
    public final EObject entryRulereturnStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulereturnStatement = null;


        try {
            // InternalMASL.g:3844:56: (iv_rulereturnStatement= rulereturnStatement EOF )
            // InternalMASL.g:3845:2: iv_rulereturnStatement= rulereturnStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getReturnStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulereturnStatement=rulereturnStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulereturnStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulereturnStatement"


    // $ANTLR start "rulereturnStatement"
    // InternalMASL.g:3851:1: rulereturnStatement returns [EObject current=null] : (this_RETURN_0= RULE_RETURN this_expression_1= ruleexpression ) ;
    public final EObject rulereturnStatement() throws RecognitionException {
        EObject current = null;

        Token this_RETURN_0=null;
        EObject this_expression_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:3857:2: ( (this_RETURN_0= RULE_RETURN this_expression_1= ruleexpression ) )
            // InternalMASL.g:3858:2: (this_RETURN_0= RULE_RETURN this_expression_1= ruleexpression )
            {
            // InternalMASL.g:3858:2: (this_RETURN_0= RULE_RETURN this_expression_1= ruleexpression )
            // InternalMASL.g:3859:3: this_RETURN_0= RULE_RETURN this_expression_1= ruleexpression
            {
            this_RETURN_0=(Token)match(input,RULE_RETURN,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RETURN_0, grammarAccess.getReturnStatementAccess().getRETURNTerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getReturnStatementAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulereturnStatement"


    // $ANTLR start "entryRuledelayStatement"
    // InternalMASL.g:3875:1: entryRuledelayStatement returns [EObject current=null] : iv_ruledelayStatement= ruledelayStatement EOF ;
    public final EObject entryRuledelayStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledelayStatement = null;


        try {
            // InternalMASL.g:3875:55: (iv_ruledelayStatement= ruledelayStatement EOF )
            // InternalMASL.g:3876:2: iv_ruledelayStatement= ruledelayStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDelayStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledelayStatement=ruledelayStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledelayStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledelayStatement"


    // $ANTLR start "ruledelayStatement"
    // InternalMASL.g:3882:1: ruledelayStatement returns [EObject current=null] : (this_DELAY_0= RULE_DELAY this_expression_1= ruleexpression ) ;
    public final EObject ruledelayStatement() throws RecognitionException {
        EObject current = null;

        Token this_DELAY_0=null;
        EObject this_expression_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:3888:2: ( (this_DELAY_0= RULE_DELAY this_expression_1= ruleexpression ) )
            // InternalMASL.g:3889:2: (this_DELAY_0= RULE_DELAY this_expression_1= ruleexpression )
            {
            // InternalMASL.g:3889:2: (this_DELAY_0= RULE_DELAY this_expression_1= ruleexpression )
            // InternalMASL.g:3890:3: this_DELAY_0= RULE_DELAY this_expression_1= ruleexpression
            {
            this_DELAY_0=(Token)match(input,RULE_DELAY,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_DELAY_0, grammarAccess.getDelayStatementAccess().getDELAYTerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDelayStatementAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledelayStatement"


    // $ANTLR start "entryRuleraiseStatement"
    // InternalMASL.g:3906:1: entryRuleraiseStatement returns [String current=null] : iv_ruleraiseStatement= ruleraiseStatement EOF ;
    public final String entryRuleraiseStatement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleraiseStatement = null;


        try {
            // InternalMASL.g:3906:54: (iv_ruleraiseStatement= ruleraiseStatement EOF )
            // InternalMASL.g:3907:2: iv_ruleraiseStatement= ruleraiseStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRaiseStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleraiseStatement=ruleraiseStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleraiseStatement.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleraiseStatement"


    // $ANTLR start "ruleraiseStatement"
    // InternalMASL.g:3913:1: ruleraiseStatement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_RAISE_0= RULE_RAISE this_qualifiedExceptionName_1= rulequalifiedExceptionName ) ;
    public final AntlrDatatypeRuleToken ruleraiseStatement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_RAISE_0=null;
        AntlrDatatypeRuleToken this_qualifiedExceptionName_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:3919:2: ( (this_RAISE_0= RULE_RAISE this_qualifiedExceptionName_1= rulequalifiedExceptionName ) )
            // InternalMASL.g:3920:2: (this_RAISE_0= RULE_RAISE this_qualifiedExceptionName_1= rulequalifiedExceptionName )
            {
            // InternalMASL.g:3920:2: (this_RAISE_0= RULE_RAISE this_qualifiedExceptionName_1= rulequalifiedExceptionName )
            // InternalMASL.g:3921:3: this_RAISE_0= RULE_RAISE this_qualifiedExceptionName_1= rulequalifiedExceptionName
            {
            this_RAISE_0=(Token)match(input,RULE_RAISE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_RAISE_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RAISE_0, grammarAccess.getRaiseStatementAccess().getRAISETerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getRaiseStatementAccess().getQualifiedExceptionNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_qualifiedExceptionName_1=rulequalifiedExceptionName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_qualifiedExceptionName_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleraiseStatement"


    // $ANTLR start "entryRuledeleteStatement"
    // InternalMASL.g:3942:1: entryRuledeleteStatement returns [EObject current=null] : iv_ruledeleteStatement= ruledeleteStatement EOF ;
    public final EObject entryRuledeleteStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledeleteStatement = null;


        try {
            // InternalMASL.g:3942:56: (iv_ruledeleteStatement= ruledeleteStatement EOF )
            // InternalMASL.g:3943:2: iv_ruledeleteStatement= ruledeleteStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDeleteStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruledeleteStatement=ruledeleteStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruledeleteStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledeleteStatement"


    // $ANTLR start "ruledeleteStatement"
    // InternalMASL.g:3949:1: ruledeleteStatement returns [EObject current=null] : (this_DELETE_0= RULE_DELETE this_expression_1= ruleexpression ) ;
    public final EObject ruledeleteStatement() throws RecognitionException {
        EObject current = null;

        Token this_DELETE_0=null;
        EObject this_expression_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:3955:2: ( (this_DELETE_0= RULE_DELETE this_expression_1= ruleexpression ) )
            // InternalMASL.g:3956:2: (this_DELETE_0= RULE_DELETE this_expression_1= ruleexpression )
            {
            // InternalMASL.g:3956:2: (this_DELETE_0= RULE_DELETE this_expression_1= ruleexpression )
            // InternalMASL.g:3957:3: this_DELETE_0= RULE_DELETE this_expression_1= ruleexpression
            {
            this_DELETE_0=(Token)match(input,RULE_DELETE,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_DELETE_0, grammarAccess.getDeleteStatementAccess().getDELETETerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getDeleteStatementAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledeleteStatement"


    // $ANTLR start "entryRuleeraseStatement"
    // InternalMASL.g:3973:1: entryRuleeraseStatement returns [EObject current=null] : iv_ruleeraseStatement= ruleeraseStatement EOF ;
    public final EObject entryRuleeraseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleeraseStatement = null;


        try {
            // InternalMASL.g:3973:55: (iv_ruleeraseStatement= ruleeraseStatement EOF )
            // InternalMASL.g:3974:2: iv_ruleeraseStatement= ruleeraseStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEraseStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleeraseStatement=ruleeraseStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleeraseStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleeraseStatement"


    // $ANTLR start "ruleeraseStatement"
    // InternalMASL.g:3980:1: ruleeraseStatement returns [EObject current=null] : (this_ERASE_0= RULE_ERASE this_expression_1= ruleexpression ) ;
    public final EObject ruleeraseStatement() throws RecognitionException {
        EObject current = null;

        Token this_ERASE_0=null;
        EObject this_expression_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:3986:2: ( (this_ERASE_0= RULE_ERASE this_expression_1= ruleexpression ) )
            // InternalMASL.g:3987:2: (this_ERASE_0= RULE_ERASE this_expression_1= ruleexpression )
            {
            // InternalMASL.g:3987:2: (this_ERASE_0= RULE_ERASE this_expression_1= ruleexpression )
            // InternalMASL.g:3988:3: this_ERASE_0= RULE_ERASE this_expression_1= ruleexpression
            {
            this_ERASE_0=(Token)match(input,RULE_ERASE,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ERASE_0, grammarAccess.getEraseStatementAccess().getERASETerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getEraseStatementAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleeraseStatement"


    // $ANTLR start "entryRulelinkStatement"
    // InternalMASL.g:4004:1: entryRulelinkStatement returns [EObject current=null] : iv_rulelinkStatement= rulelinkStatement EOF ;
    public final EObject entryRulelinkStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulelinkStatement = null;


        try {
            // InternalMASL.g:4004:54: (iv_rulelinkStatement= rulelinkStatement EOF )
            // InternalMASL.g:4005:2: iv_rulelinkStatement= rulelinkStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLinkStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulelinkStatement=rulelinkStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulelinkStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelinkStatement"


    // $ANTLR start "rulelinkStatement"
    // InternalMASL.g:4011:1: rulelinkStatement returns [EObject current=null] : ( rulelinkType ( (lv_n1_1_0= rulenavigateExpression ) ) ( (lv_r_2_0= rulerelationshipSpec ) ) ( ( (lv_n2_3_0= rulenavigateExpression ) ) (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )? )? ) ;
    public final EObject rulelinkStatement() throws RecognitionException {
        EObject current = null;

        Token this_USING_4=null;
        EObject lv_n1_1_0 = null;

        EObject lv_r_2_0 = null;

        EObject lv_n2_3_0 = null;

        EObject lv_n3_5_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4017:2: ( ( rulelinkType ( (lv_n1_1_0= rulenavigateExpression ) ) ( (lv_r_2_0= rulerelationshipSpec ) ) ( ( (lv_n2_3_0= rulenavigateExpression ) ) (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )? )? ) )
            // InternalMASL.g:4018:2: ( rulelinkType ( (lv_n1_1_0= rulenavigateExpression ) ) ( (lv_r_2_0= rulerelationshipSpec ) ) ( ( (lv_n2_3_0= rulenavigateExpression ) ) (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )? )? )
            {
            // InternalMASL.g:4018:2: ( rulelinkType ( (lv_n1_1_0= rulenavigateExpression ) ) ( (lv_r_2_0= rulerelationshipSpec ) ) ( ( (lv_n2_3_0= rulenavigateExpression ) ) (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )? )? )
            // InternalMASL.g:4019:3: rulelinkType ( (lv_n1_1_0= rulenavigateExpression ) ) ( (lv_r_2_0= rulerelationshipSpec ) ) ( ( (lv_n2_3_0= rulenavigateExpression ) ) (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )? )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getLinkStatementAccess().getLinkTypeParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_54);
            rulelinkType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:4026:3: ( (lv_n1_1_0= rulenavigateExpression ) )
            // InternalMASL.g:4027:4: (lv_n1_1_0= rulenavigateExpression )
            {
            // InternalMASL.g:4027:4: (lv_n1_1_0= rulenavigateExpression )
            // InternalMASL.g:4028:5: lv_n1_1_0= rulenavigateExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getLinkStatementAccess().getN1NavigateExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_20);
            lv_n1_1_0=rulenavigateExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getLinkStatementRule());
              					}
              					set(
              						current,
              						"n1",
              						lv_n1_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.navigateExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:4045:3: ( (lv_r_2_0= rulerelationshipSpec ) )
            // InternalMASL.g:4046:4: (lv_r_2_0= rulerelationshipSpec )
            {
            // InternalMASL.g:4046:4: (lv_r_2_0= rulerelationshipSpec )
            // InternalMASL.g:4047:5: lv_r_2_0= rulerelationshipSpec
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getLinkStatementAccess().getRRelationshipSpecParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_55);
            lv_r_2_0=rulerelationshipSpec();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getLinkStatementRule());
              					}
              					set(
              						current,
              						"r",
              						lv_r_2_0,
              						"org.xtuml.bp.ui.xtext.MASL.relationshipSpec");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:4064:3: ( ( (lv_n2_3_0= rulenavigateExpression ) ) (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )? )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RULE_INSTANCE||LA51_0==RULE_ANONYMOUS||LA51_0==RULE_LPAREN||(LA51_0>=RULE_SEQUENCE && LA51_0<=RULE_DICTIONARY)||LA51_0==RULE_NULL||LA51_0==RULE_CREATE||(LA51_0>=RULE_FIND && LA51_0<=RULE_IDENT)) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalMASL.g:4065:4: ( (lv_n2_3_0= rulenavigateExpression ) ) (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )?
                    {
                    // InternalMASL.g:4065:4: ( (lv_n2_3_0= rulenavigateExpression ) )
                    // InternalMASL.g:4066:5: (lv_n2_3_0= rulenavigateExpression )
                    {
                    // InternalMASL.g:4066:5: (lv_n2_3_0= rulenavigateExpression )
                    // InternalMASL.g:4067:6: lv_n2_3_0= rulenavigateExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLinkStatementAccess().getN2NavigateExpressionParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_56);
                    lv_n2_3_0=rulenavigateExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLinkStatementRule());
                      						}
                      						set(
                      							current,
                      							"n2",
                      							lv_n2_3_0,
                      							"org.xtuml.bp.ui.xtext.MASL.navigateExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalMASL.g:4084:4: (this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) ) )?
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==RULE_USING) ) {
                        alt50=1;
                    }
                    switch (alt50) {
                        case 1 :
                            // InternalMASL.g:4085:5: this_USING_4= RULE_USING ( (lv_n3_5_0= rulenavigateExpression ) )
                            {
                            this_USING_4=(Token)match(input,RULE_USING,FOLLOW_54); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(this_USING_4, grammarAccess.getLinkStatementAccess().getUSINGTerminalRuleCall_3_1_0());
                              				
                            }
                            // InternalMASL.g:4089:5: ( (lv_n3_5_0= rulenavigateExpression ) )
                            // InternalMASL.g:4090:6: (lv_n3_5_0= rulenavigateExpression )
                            {
                            // InternalMASL.g:4090:6: (lv_n3_5_0= rulenavigateExpression )
                            // InternalMASL.g:4091:7: lv_n3_5_0= rulenavigateExpression
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getLinkStatementAccess().getN3NavigateExpressionParserRuleCall_3_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_n3_5_0=rulenavigateExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getLinkStatementRule());
                              							}
                              							set(
                              								current,
                              								"n3",
                              								lv_n3_5_0,
                              								"org.xtuml.bp.ui.xtext.MASL.navigateExpression");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulelinkStatement"


    // $ANTLR start "entryRulelinkType"
    // InternalMASL.g:4114:1: entryRulelinkType returns [String current=null] : iv_rulelinkType= rulelinkType EOF ;
    public final String entryRulelinkType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulelinkType = null;


        try {
            // InternalMASL.g:4114:48: (iv_rulelinkType= rulelinkType EOF )
            // InternalMASL.g:4115:2: iv_rulelinkType= rulelinkType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLinkTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulelinkType=rulelinkType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulelinkType.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelinkType"


    // $ANTLR start "rulelinkType"
    // InternalMASL.g:4121:1: rulelinkType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LINK_0= RULE_LINK | this_UNLINK_1= RULE_UNLINK ) ;
    public final AntlrDatatypeRuleToken rulelinkType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LINK_0=null;
        Token this_UNLINK_1=null;


        	enterRule();

        try {
            // InternalMASL.g:4127:2: ( (this_LINK_0= RULE_LINK | this_UNLINK_1= RULE_UNLINK ) )
            // InternalMASL.g:4128:2: (this_LINK_0= RULE_LINK | this_UNLINK_1= RULE_UNLINK )
            {
            // InternalMASL.g:4128:2: (this_LINK_0= RULE_LINK | this_UNLINK_1= RULE_UNLINK )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_LINK) ) {
                alt52=1;
            }
            else if ( (LA52_0==RULE_UNLINK) ) {
                alt52=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // InternalMASL.g:4129:3: this_LINK_0= RULE_LINK
                    {
                    this_LINK_0=(Token)match(input,RULE_LINK,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LINK_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LINK_0, grammarAccess.getLinkTypeAccess().getLINKTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:4137:3: this_UNLINK_1= RULE_UNLINK
                    {
                    this_UNLINK_1=(Token)match(input,RULE_UNLINK,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_UNLINK_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_UNLINK_1, grammarAccess.getLinkTypeAccess().getUNLINKTerminalRuleCall_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulelinkType"


    // $ANTLR start "entryRulescheduleStatement"
    // InternalMASL.g:4148:1: entryRulescheduleStatement returns [EObject current=null] : iv_rulescheduleStatement= rulescheduleStatement EOF ;
    public final EObject entryRulescheduleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulescheduleStatement = null;


        try {
            // InternalMASL.g:4148:58: (iv_rulescheduleStatement= rulescheduleStatement EOF )
            // InternalMASL.g:4149:2: iv_rulescheduleStatement= rulescheduleStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getScheduleStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulescheduleStatement=rulescheduleStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulescheduleStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulescheduleStatement"


    // $ANTLR start "rulescheduleStatement"
    // InternalMASL.g:4155:1: rulescheduleStatement returns [EObject current=null] : (this_SCHEDULE_0= RULE_SCHEDULE this_expression_1= ruleexpression ( (lv_g_2_0= rulegenerateStatement ) ) rulescheduleType ( (lv_e_4_0= ruleexpression ) ) (this_DELTA_5= RULE_DELTA ( (lv_e_6_0= ruleexpression ) ) )? ) ;
    public final EObject rulescheduleStatement() throws RecognitionException {
        EObject current = null;

        Token this_SCHEDULE_0=null;
        Token this_DELTA_5=null;
        EObject this_expression_1 = null;

        EObject lv_g_2_0 = null;

        EObject lv_e_4_0 = null;

        EObject lv_e_6_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4161:2: ( (this_SCHEDULE_0= RULE_SCHEDULE this_expression_1= ruleexpression ( (lv_g_2_0= rulegenerateStatement ) ) rulescheduleType ( (lv_e_4_0= ruleexpression ) ) (this_DELTA_5= RULE_DELTA ( (lv_e_6_0= ruleexpression ) ) )? ) )
            // InternalMASL.g:4162:2: (this_SCHEDULE_0= RULE_SCHEDULE this_expression_1= ruleexpression ( (lv_g_2_0= rulegenerateStatement ) ) rulescheduleType ( (lv_e_4_0= ruleexpression ) ) (this_DELTA_5= RULE_DELTA ( (lv_e_6_0= ruleexpression ) ) )? )
            {
            // InternalMASL.g:4162:2: (this_SCHEDULE_0= RULE_SCHEDULE this_expression_1= ruleexpression ( (lv_g_2_0= rulegenerateStatement ) ) rulescheduleType ( (lv_e_4_0= ruleexpression ) ) (this_DELTA_5= RULE_DELTA ( (lv_e_6_0= ruleexpression ) ) )? )
            // InternalMASL.g:4163:3: this_SCHEDULE_0= RULE_SCHEDULE this_expression_1= ruleexpression ( (lv_g_2_0= rulegenerateStatement ) ) rulescheduleType ( (lv_e_4_0= ruleexpression ) ) (this_DELTA_5= RULE_DELTA ( (lv_e_6_0= ruleexpression ) ) )?
            {
            this_SCHEDULE_0=(Token)match(input,RULE_SCHEDULE,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SCHEDULE_0, grammarAccess.getScheduleStatementAccess().getSCHEDULETerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getScheduleStatementAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_57);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:4175:3: ( (lv_g_2_0= rulegenerateStatement ) )
            // InternalMASL.g:4176:4: (lv_g_2_0= rulegenerateStatement )
            {
            // InternalMASL.g:4176:4: (lv_g_2_0= rulegenerateStatement )
            // InternalMASL.g:4177:5: lv_g_2_0= rulegenerateStatement
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getScheduleStatementAccess().getGGenerateStatementParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_58);
            lv_g_2_0=rulegenerateStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getScheduleStatementRule());
              					}
              					set(
              						current,
              						"g",
              						lv_g_2_0,
              						"org.xtuml.bp.ui.xtext.MASL.generateStatement");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getScheduleStatementAccess().getScheduleTypeParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_8);
            rulescheduleType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:4201:3: ( (lv_e_4_0= ruleexpression ) )
            // InternalMASL.g:4202:4: (lv_e_4_0= ruleexpression )
            {
            // InternalMASL.g:4202:4: (lv_e_4_0= ruleexpression )
            // InternalMASL.g:4203:5: lv_e_4_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getScheduleStatementAccess().getEExpressionParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_59);
            lv_e_4_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getScheduleStatementRule());
              					}
              					add(
              						current,
              						"e",
              						lv_e_4_0,
              						"org.xtuml.bp.ui.xtext.MASL.expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:4220:3: (this_DELTA_5= RULE_DELTA ( (lv_e_6_0= ruleexpression ) ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==RULE_DELTA) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalMASL.g:4221:4: this_DELTA_5= RULE_DELTA ( (lv_e_6_0= ruleexpression ) )
                    {
                    this_DELTA_5=(Token)match(input,RULE_DELTA,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DELTA_5, grammarAccess.getScheduleStatementAccess().getDELTATerminalRuleCall_5_0());
                      			
                    }
                    // InternalMASL.g:4225:4: ( (lv_e_6_0= ruleexpression ) )
                    // InternalMASL.g:4226:5: (lv_e_6_0= ruleexpression )
                    {
                    // InternalMASL.g:4226:5: (lv_e_6_0= ruleexpression )
                    // InternalMASL.g:4227:6: lv_e_6_0= ruleexpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getScheduleStatementAccess().getEExpressionParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_e_6_0=ruleexpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getScheduleStatementRule());
                      						}
                      						add(
                      							current,
                      							"e",
                      							lv_e_6_0,
                      							"org.xtuml.bp.ui.xtext.MASL.expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulescheduleStatement"


    // $ANTLR start "entryRulecancelTimerStatement"
    // InternalMASL.g:4249:1: entryRulecancelTimerStatement returns [EObject current=null] : iv_rulecancelTimerStatement= rulecancelTimerStatement EOF ;
    public final EObject entryRulecancelTimerStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecancelTimerStatement = null;


        try {
            // InternalMASL.g:4249:61: (iv_rulecancelTimerStatement= rulecancelTimerStatement EOF )
            // InternalMASL.g:4250:2: iv_rulecancelTimerStatement= rulecancelTimerStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCancelTimerStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecancelTimerStatement=rulecancelTimerStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecancelTimerStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecancelTimerStatement"


    // $ANTLR start "rulecancelTimerStatement"
    // InternalMASL.g:4256:1: rulecancelTimerStatement returns [EObject current=null] : (this_CANCEL_0= RULE_CANCEL this_expression_1= ruleexpression ) ;
    public final EObject rulecancelTimerStatement() throws RecognitionException {
        EObject current = null;

        Token this_CANCEL_0=null;
        EObject this_expression_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:4262:2: ( (this_CANCEL_0= RULE_CANCEL this_expression_1= ruleexpression ) )
            // InternalMASL.g:4263:2: (this_CANCEL_0= RULE_CANCEL this_expression_1= ruleexpression )
            {
            // InternalMASL.g:4263:2: (this_CANCEL_0= RULE_CANCEL this_expression_1= ruleexpression )
            // InternalMASL.g:4264:3: this_CANCEL_0= RULE_CANCEL this_expression_1= ruleexpression
            {
            this_CANCEL_0=(Token)match(input,RULE_CANCEL,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_CANCEL_0, grammarAccess.getCancelTimerStatementAccess().getCANCELTerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCancelTimerStatementAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecancelTimerStatement"


    // $ANTLR start "entryRulescheduleType"
    // InternalMASL.g:4280:1: entryRulescheduleType returns [String current=null] : iv_rulescheduleType= rulescheduleType EOF ;
    public final String entryRulescheduleType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulescheduleType = null;


        try {
            // InternalMASL.g:4280:52: (iv_rulescheduleType= rulescheduleType EOF )
            // InternalMASL.g:4281:2: iv_rulescheduleType= rulescheduleType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getScheduleTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulescheduleType=rulescheduleType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulescheduleType.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulescheduleType"


    // $ANTLR start "rulescheduleType"
    // InternalMASL.g:4287:1: rulescheduleType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_AT_0= RULE_AT | this_DELAY_1= RULE_DELAY ) ;
    public final AntlrDatatypeRuleToken rulescheduleType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_AT_0=null;
        Token this_DELAY_1=null;


        	enterRule();

        try {
            // InternalMASL.g:4293:2: ( (this_AT_0= RULE_AT | this_DELAY_1= RULE_DELAY ) )
            // InternalMASL.g:4294:2: (this_AT_0= RULE_AT | this_DELAY_1= RULE_DELAY )
            {
            // InternalMASL.g:4294:2: (this_AT_0= RULE_AT | this_DELAY_1= RULE_DELAY )
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_AT) ) {
                alt54=1;
            }
            else if ( (LA54_0==RULE_DELAY) ) {
                alt54=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // InternalMASL.g:4295:3: this_AT_0= RULE_AT
                    {
                    this_AT_0=(Token)match(input,RULE_AT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_AT_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_AT_0, grammarAccess.getScheduleTypeAccess().getATTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:4303:3: this_DELAY_1= RULE_DELAY
                    {
                    this_DELAY_1=(Token)match(input,RULE_DELAY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_DELAY_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_DELAY_1, grammarAccess.getScheduleTypeAccess().getDELAYTerminalRuleCall_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulescheduleType"


    // $ANTLR start "entryRulegenerateStatement"
    // InternalMASL.g:4314:1: entryRulegenerateStatement returns [EObject current=null] : iv_rulegenerateStatement= rulegenerateStatement EOF ;
    public final EObject entryRulegenerateStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulegenerateStatement = null;


        try {
            // InternalMASL.g:4314:58: (iv_rulegenerateStatement= rulegenerateStatement EOF )
            // InternalMASL.g:4315:2: iv_rulegenerateStatement= rulegenerateStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getGenerateStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulegenerateStatement=rulegenerateStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulegenerateStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulegenerateStatement"


    // $ANTLR start "rulegenerateStatement"
    // InternalMASL.g:4321:1: rulegenerateStatement returns [EObject current=null] : (this_GENERATE_0= RULE_GENERATE rulequalifiedEventName this_LPAREN_2= RULE_LPAREN this_argumentList_3= ruleargumentList this_RPAREN_4= RULE_RPAREN (this_TO_5= RULE_TO ( (lv_e_6_0= ruleexpression ) ) )? ) ;
    public final EObject rulegenerateStatement() throws RecognitionException {
        EObject current = null;

        Token this_GENERATE_0=null;
        Token this_LPAREN_2=null;
        Token this_RPAREN_4=null;
        Token this_TO_5=null;
        EObject this_argumentList_3 = null;

        EObject lv_e_6_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4327:2: ( (this_GENERATE_0= RULE_GENERATE rulequalifiedEventName this_LPAREN_2= RULE_LPAREN this_argumentList_3= ruleargumentList this_RPAREN_4= RULE_RPAREN (this_TO_5= RULE_TO ( (lv_e_6_0= ruleexpression ) ) )? ) )
            // InternalMASL.g:4328:2: (this_GENERATE_0= RULE_GENERATE rulequalifiedEventName this_LPAREN_2= RULE_LPAREN this_argumentList_3= ruleargumentList this_RPAREN_4= RULE_RPAREN (this_TO_5= RULE_TO ( (lv_e_6_0= ruleexpression ) ) )? )
            {
            // InternalMASL.g:4328:2: (this_GENERATE_0= RULE_GENERATE rulequalifiedEventName this_LPAREN_2= RULE_LPAREN this_argumentList_3= ruleargumentList this_RPAREN_4= RULE_RPAREN (this_TO_5= RULE_TO ( (lv_e_6_0= ruleexpression ) ) )? )
            // InternalMASL.g:4329:3: this_GENERATE_0= RULE_GENERATE rulequalifiedEventName this_LPAREN_2= RULE_LPAREN this_argumentList_3= ruleargumentList this_RPAREN_4= RULE_RPAREN (this_TO_5= RULE_TO ( (lv_e_6_0= ruleexpression ) ) )?
            {
            this_GENERATE_0=(Token)match(input,RULE_GENERATE,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_GENERATE_0, grammarAccess.getGenerateStatementAccess().getGENERATETerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getGenerateStatementAccess().getQualifiedEventNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_7);
            rulequalifiedEventName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_LPAREN_2=(Token)match(input,RULE_LPAREN,FOLLOW_51); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_2, grammarAccess.getGenerateStatementAccess().getLPARENTerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getGenerateStatementAccess().getArgumentListParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_9);
            this_argumentList_3=ruleargumentList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_argumentList_3;
              			afterParserOrEnumRuleCall();
              		
            }
            this_RPAREN_4=(Token)match(input,RULE_RPAREN,FOLLOW_60); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_4, grammarAccess.getGenerateStatementAccess().getRPARENTerminalRuleCall_4());
              		
            }
            // InternalMASL.g:4356:3: (this_TO_5= RULE_TO ( (lv_e_6_0= ruleexpression ) ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==RULE_TO) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalMASL.g:4357:4: this_TO_5= RULE_TO ( (lv_e_6_0= ruleexpression ) )
                    {
                    this_TO_5=(Token)match(input,RULE_TO,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_TO_5, grammarAccess.getGenerateStatementAccess().getTOTerminalRuleCall_5_0());
                      			
                    }
                    // InternalMASL.g:4361:4: ( (lv_e_6_0= ruleexpression ) )
                    // InternalMASL.g:4362:5: (lv_e_6_0= ruleexpression )
                    {
                    // InternalMASL.g:4362:5: (lv_e_6_0= ruleexpression )
                    // InternalMASL.g:4363:6: lv_e_6_0= ruleexpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getGenerateStatementAccess().getEExpressionParserRuleCall_5_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_e_6_0=ruleexpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getGenerateStatementRule());
                      						}
                      						set(
                      							current,
                      							"e",
                      							lv_e_6_0,
                      							"org.xtuml.bp.ui.xtext.MASL.expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulegenerateStatement"


    // $ANTLR start "entryRulequalifiedEventName"
    // InternalMASL.g:4385:1: entryRulequalifiedEventName returns [String current=null] : iv_rulequalifiedEventName= rulequalifiedEventName EOF ;
    public final String entryRulequalifiedEventName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulequalifiedEventName = null;


        try {
            // InternalMASL.g:4385:58: (iv_rulequalifiedEventName= rulequalifiedEventName EOF )
            // InternalMASL.g:4386:2: iv_rulequalifiedEventName= rulequalifiedEventName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedEventNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulequalifiedEventName=rulequalifiedEventName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulequalifiedEventName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulequalifiedEventName"


    // $ANTLR start "rulequalifiedEventName"
    // InternalMASL.g:4392:1: rulequalifiedEventName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_qualifiedObjectName_0= rulequalifiedObjectName this_DOT_1= RULE_DOT )? this_eventName_2= ruleeventName ) ;
    public final AntlrDatatypeRuleToken rulequalifiedEventName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_DOT_1=null;
        AntlrDatatypeRuleToken this_qualifiedObjectName_0 = null;

        AntlrDatatypeRuleToken this_eventName_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:4398:2: ( ( (this_qualifiedObjectName_0= rulequalifiedObjectName this_DOT_1= RULE_DOT )? this_eventName_2= ruleeventName ) )
            // InternalMASL.g:4399:2: ( (this_qualifiedObjectName_0= rulequalifiedObjectName this_DOT_1= RULE_DOT )? this_eventName_2= ruleeventName )
            {
            // InternalMASL.g:4399:2: ( (this_qualifiedObjectName_0= rulequalifiedObjectName this_DOT_1= RULE_DOT )? this_eventName_2= ruleeventName )
            // InternalMASL.g:4400:3: (this_qualifiedObjectName_0= rulequalifiedObjectName this_DOT_1= RULE_DOT )? this_eventName_2= ruleeventName
            {
            // InternalMASL.g:4400:3: (this_qualifiedObjectName_0= rulequalifiedObjectName this_DOT_1= RULE_DOT )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==RULE_IDENT) ) {
                int LA56_1 = input.LA(2);

                if ( (LA56_1==RULE_SCOPE||LA56_1==RULE_DOT) ) {
                    alt56=1;
                }
            }
            switch (alt56) {
                case 1 :
                    // InternalMASL.g:4401:4: this_qualifiedObjectName_0= rulequalifiedObjectName this_DOT_1= RULE_DOT
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getQualifiedEventNameAccess().getQualifiedObjectNameParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_39);
                    this_qualifiedObjectName_0=rulequalifiedObjectName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_qualifiedObjectName_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    this_DOT_1=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_DOT_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DOT_1, grammarAccess.getQualifiedEventNameAccess().getDOTTerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedEventNameAccess().getEventNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_eventName_2=ruleeventName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_eventName_2);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulequalifiedEventName"


    // $ANTLR start "entryRuleifStatement"
    // InternalMASL.g:4433:1: entryRuleifStatement returns [EObject current=null] : iv_ruleifStatement= ruleifStatement EOF ;
    public final EObject entryRuleifStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleifStatement = null;


        try {
            // InternalMASL.g:4433:52: (iv_ruleifStatement= ruleifStatement EOF )
            // InternalMASL.g:4434:2: iv_ruleifStatement= ruleifStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIfStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleifStatement=ruleifStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleifStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleifStatement"


    // $ANTLR start "ruleifStatement"
    // InternalMASL.g:4440:1: ruleifStatement returns [EObject current=null] : (this_IF_0= RULE_IF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) ( (lv_e1_4_0= ruleelsifBlock ) )* ( (lv_e2_5_0= ruleelseBlock ) )? this_END_6= RULE_END (this_IF_7= RULE_IF )? ) ;
    public final EObject ruleifStatement() throws RecognitionException {
        EObject current = null;

        Token this_IF_0=null;
        Token this_THEN_2=null;
        Token this_END_6=null;
        Token this_IF_7=null;
        EObject lv_c_1_0 = null;

        EObject lv_s_3_0 = null;

        EObject lv_e1_4_0 = null;

        EObject lv_e2_5_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4446:2: ( (this_IF_0= RULE_IF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) ( (lv_e1_4_0= ruleelsifBlock ) )* ( (lv_e2_5_0= ruleelseBlock ) )? this_END_6= RULE_END (this_IF_7= RULE_IF )? ) )
            // InternalMASL.g:4447:2: (this_IF_0= RULE_IF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) ( (lv_e1_4_0= ruleelsifBlock ) )* ( (lv_e2_5_0= ruleelseBlock ) )? this_END_6= RULE_END (this_IF_7= RULE_IF )? )
            {
            // InternalMASL.g:4447:2: (this_IF_0= RULE_IF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) ( (lv_e1_4_0= ruleelsifBlock ) )* ( (lv_e2_5_0= ruleelseBlock ) )? this_END_6= RULE_END (this_IF_7= RULE_IF )? )
            // InternalMASL.g:4448:3: this_IF_0= RULE_IF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) ( (lv_e1_4_0= ruleelsifBlock ) )* ( (lv_e2_5_0= ruleelseBlock ) )? this_END_6= RULE_END (this_IF_7= RULE_IF )?
            {
            this_IF_0=(Token)match(input,RULE_IF,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IF_0, grammarAccess.getIfStatementAccess().getIFTerminalRuleCall_0());
              		
            }
            // InternalMASL.g:4452:3: ( (lv_c_1_0= rulecondition ) )
            // InternalMASL.g:4453:4: (lv_c_1_0= rulecondition )
            {
            // InternalMASL.g:4453:4: (lv_c_1_0= rulecondition )
            // InternalMASL.g:4454:5: lv_c_1_0= rulecondition
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getCConditionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_61);
            lv_c_1_0=rulecondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getIfStatementRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.condition");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_THEN_2=(Token)match(input,RULE_THEN,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_THEN_2, grammarAccess.getIfStatementAccess().getTHENTerminalRuleCall_2());
              		
            }
            // InternalMASL.g:4475:3: ( (lv_s_3_0= rulestatementList ) )
            // InternalMASL.g:4476:4: (lv_s_3_0= rulestatementList )
            {
            // InternalMASL.g:4476:4: (lv_s_3_0= rulestatementList )
            // InternalMASL.g:4477:5: lv_s_3_0= rulestatementList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getIfStatementAccess().getSStatementListParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_63);
            lv_s_3_0=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getIfStatementRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_3_0,
              						"org.xtuml.bp.ui.xtext.MASL.statementList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:4494:3: ( (lv_e1_4_0= ruleelsifBlock ) )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==RULE_ELSIF) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // InternalMASL.g:4495:4: (lv_e1_4_0= ruleelsifBlock )
            	    {
            	    // InternalMASL.g:4495:4: (lv_e1_4_0= ruleelsifBlock )
            	    // InternalMASL.g:4496:5: lv_e1_4_0= ruleelsifBlock
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getIfStatementAccess().getE1ElsifBlockParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_63);
            	    lv_e1_4_0=ruleelsifBlock();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getIfStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"e1",
            	      						lv_e1_4_0,
            	      						"org.xtuml.bp.ui.xtext.MASL.elsifBlock");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);

            // InternalMASL.g:4513:3: ( (lv_e2_5_0= ruleelseBlock ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==RULE_ELSE) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalMASL.g:4514:4: (lv_e2_5_0= ruleelseBlock )
                    {
                    // InternalMASL.g:4514:4: (lv_e2_5_0= ruleelseBlock )
                    // InternalMASL.g:4515:5: lv_e2_5_0= ruleelseBlock
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getIfStatementAccess().getE2ElseBlockParserRuleCall_5_0());
                      				
                    }
                    pushFollow(FOLLOW_64);
                    lv_e2_5_0=ruleelseBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getIfStatementRule());
                      					}
                      					set(
                      						current,
                      						"e2",
                      						lv_e2_5_0,
                      						"org.xtuml.bp.ui.xtext.MASL.elseBlock");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            this_END_6=(Token)match(input,RULE_END,FOLLOW_65); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_END_6, grammarAccess.getIfStatementAccess().getENDTerminalRuleCall_6());
              		
            }
            // InternalMASL.g:4536:3: (this_IF_7= RULE_IF )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==RULE_IF) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalMASL.g:4537:4: this_IF_7= RULE_IF
                    {
                    this_IF_7=(Token)match(input,RULE_IF,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_IF_7, grammarAccess.getIfStatementAccess().getIFTerminalRuleCall_7());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleifStatement"


    // $ANTLR start "entryRuleelsifBlock"
    // InternalMASL.g:4546:1: entryRuleelsifBlock returns [EObject current=null] : iv_ruleelsifBlock= ruleelsifBlock EOF ;
    public final EObject entryRuleelsifBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleelsifBlock = null;


        try {
            // InternalMASL.g:4546:51: (iv_ruleelsifBlock= ruleelsifBlock EOF )
            // InternalMASL.g:4547:2: iv_ruleelsifBlock= ruleelsifBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getElsifBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleelsifBlock=ruleelsifBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleelsifBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleelsifBlock"


    // $ANTLR start "ruleelsifBlock"
    // InternalMASL.g:4553:1: ruleelsifBlock returns [EObject current=null] : (this_ELSIF_0= RULE_ELSIF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) ) ;
    public final EObject ruleelsifBlock() throws RecognitionException {
        EObject current = null;

        Token this_ELSIF_0=null;
        Token this_THEN_2=null;
        EObject lv_c_1_0 = null;

        EObject lv_s_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4559:2: ( (this_ELSIF_0= RULE_ELSIF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) ) )
            // InternalMASL.g:4560:2: (this_ELSIF_0= RULE_ELSIF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) )
            {
            // InternalMASL.g:4560:2: (this_ELSIF_0= RULE_ELSIF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) ) )
            // InternalMASL.g:4561:3: this_ELSIF_0= RULE_ELSIF ( (lv_c_1_0= rulecondition ) ) this_THEN_2= RULE_THEN ( (lv_s_3_0= rulestatementList ) )
            {
            this_ELSIF_0=(Token)match(input,RULE_ELSIF,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ELSIF_0, grammarAccess.getElsifBlockAccess().getELSIFTerminalRuleCall_0());
              		
            }
            // InternalMASL.g:4565:3: ( (lv_c_1_0= rulecondition ) )
            // InternalMASL.g:4566:4: (lv_c_1_0= rulecondition )
            {
            // InternalMASL.g:4566:4: (lv_c_1_0= rulecondition )
            // InternalMASL.g:4567:5: lv_c_1_0= rulecondition
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getElsifBlockAccess().getCConditionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_61);
            lv_c_1_0=rulecondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getElsifBlockRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.condition");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_THEN_2=(Token)match(input,RULE_THEN,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_THEN_2, grammarAccess.getElsifBlockAccess().getTHENTerminalRuleCall_2());
              		
            }
            // InternalMASL.g:4588:3: ( (lv_s_3_0= rulestatementList ) )
            // InternalMASL.g:4589:4: (lv_s_3_0= rulestatementList )
            {
            // InternalMASL.g:4589:4: (lv_s_3_0= rulestatementList )
            // InternalMASL.g:4590:5: lv_s_3_0= rulestatementList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getElsifBlockAccess().getSStatementListParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_s_3_0=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getElsifBlockRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_3_0,
              						"org.xtuml.bp.ui.xtext.MASL.statementList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleelsifBlock"


    // $ANTLR start "entryRuleelseBlock"
    // InternalMASL.g:4611:1: entryRuleelseBlock returns [EObject current=null] : iv_ruleelseBlock= ruleelseBlock EOF ;
    public final EObject entryRuleelseBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleelseBlock = null;


        try {
            // InternalMASL.g:4611:50: (iv_ruleelseBlock= ruleelseBlock EOF )
            // InternalMASL.g:4612:2: iv_ruleelseBlock= ruleelseBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getElseBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleelseBlock=ruleelseBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleelseBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleelseBlock"


    // $ANTLR start "ruleelseBlock"
    // InternalMASL.g:4618:1: ruleelseBlock returns [EObject current=null] : (this_ELSE_0= RULE_ELSE this_statementList_1= rulestatementList ) ;
    public final EObject ruleelseBlock() throws RecognitionException {
        EObject current = null;

        Token this_ELSE_0=null;
        EObject this_statementList_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:4624:2: ( (this_ELSE_0= RULE_ELSE this_statementList_1= rulestatementList ) )
            // InternalMASL.g:4625:2: (this_ELSE_0= RULE_ELSE this_statementList_1= rulestatementList )
            {
            // InternalMASL.g:4625:2: (this_ELSE_0= RULE_ELSE this_statementList_1= rulestatementList )
            // InternalMASL.g:4626:3: this_ELSE_0= RULE_ELSE this_statementList_1= rulestatementList
            {
            this_ELSE_0=(Token)match(input,RULE_ELSE,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ELSE_0, grammarAccess.getElseBlockAccess().getELSETerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getElseBlockAccess().getStatementListParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_statementList_1=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_statementList_1;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleelseBlock"


    // $ANTLR start "entryRulewhileStatement"
    // InternalMASL.g:4642:1: entryRulewhileStatement returns [EObject current=null] : iv_rulewhileStatement= rulewhileStatement EOF ;
    public final EObject entryRulewhileStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulewhileStatement = null;


        try {
            // InternalMASL.g:4642:55: (iv_rulewhileStatement= rulewhileStatement EOF )
            // InternalMASL.g:4643:2: iv_rulewhileStatement= rulewhileStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWhileStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulewhileStatement=rulewhileStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulewhileStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulewhileStatement"


    // $ANTLR start "rulewhileStatement"
    // InternalMASL.g:4649:1: rulewhileStatement returns [EObject current=null] : (this_WHILE_0= RULE_WHILE ( (lv_c_1_0= rulecondition ) ) this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? ) ;
    public final EObject rulewhileStatement() throws RecognitionException {
        EObject current = null;

        Token this_WHILE_0=null;
        Token this_LOOP_2=null;
        Token this_END_4=null;
        Token this_LOOP_5=null;
        EObject lv_c_1_0 = null;

        EObject lv_s_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4655:2: ( (this_WHILE_0= RULE_WHILE ( (lv_c_1_0= rulecondition ) ) this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? ) )
            // InternalMASL.g:4656:2: (this_WHILE_0= RULE_WHILE ( (lv_c_1_0= rulecondition ) ) this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? )
            {
            // InternalMASL.g:4656:2: (this_WHILE_0= RULE_WHILE ( (lv_c_1_0= rulecondition ) ) this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? )
            // InternalMASL.g:4657:3: this_WHILE_0= RULE_WHILE ( (lv_c_1_0= rulecondition ) ) this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )?
            {
            this_WHILE_0=(Token)match(input,RULE_WHILE,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WHILE_0, grammarAccess.getWhileStatementAccess().getWHILETerminalRuleCall_0());
              		
            }
            // InternalMASL.g:4661:3: ( (lv_c_1_0= rulecondition ) )
            // InternalMASL.g:4662:4: (lv_c_1_0= rulecondition )
            {
            // InternalMASL.g:4662:4: (lv_c_1_0= rulecondition )
            // InternalMASL.g:4663:5: lv_c_1_0= rulecondition
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getWhileStatementAccess().getCConditionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_66);
            lv_c_1_0=rulecondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getWhileStatementRule());
              					}
              					set(
              						current,
              						"c",
              						lv_c_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.condition");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_LOOP_2=(Token)match(input,RULE_LOOP,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOOP_2, grammarAccess.getWhileStatementAccess().getLOOPTerminalRuleCall_2());
              		
            }
            // InternalMASL.g:4684:3: ( (lv_s_3_0= rulestatementList ) )
            // InternalMASL.g:4685:4: (lv_s_3_0= rulestatementList )
            {
            // InternalMASL.g:4685:4: (lv_s_3_0= rulestatementList )
            // InternalMASL.g:4686:5: lv_s_3_0= rulestatementList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getWhileStatementAccess().getSStatementListParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_64);
            lv_s_3_0=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getWhileStatementRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_3_0,
              						"org.xtuml.bp.ui.xtext.MASL.statementList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_END_4=(Token)match(input,RULE_END,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_END_4, grammarAccess.getWhileStatementAccess().getENDTerminalRuleCall_4());
              		
            }
            // InternalMASL.g:4707:3: (this_LOOP_5= RULE_LOOP )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==RULE_LOOP) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalMASL.g:4708:4: this_LOOP_5= RULE_LOOP
                    {
                    this_LOOP_5=(Token)match(input,RULE_LOOP,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOOP_5, grammarAccess.getWhileStatementAccess().getLOOPTerminalRuleCall_5());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulewhileStatement"


    // $ANTLR start "entryRulecondition"
    // InternalMASL.g:4717:1: entryRulecondition returns [EObject current=null] : iv_rulecondition= rulecondition EOF ;
    public final EObject entryRulecondition() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecondition = null;


        try {
            // InternalMASL.g:4717:50: (iv_rulecondition= rulecondition EOF )
            // InternalMASL.g:4718:2: iv_rulecondition= rulecondition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getConditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecondition=rulecondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecondition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecondition"


    // $ANTLR start "rulecondition"
    // InternalMASL.g:4724:1: rulecondition returns [EObject current=null] : this_expression_0= ruleexpression ;
    public final EObject rulecondition() throws RecognitionException {
        EObject current = null;

        EObject this_expression_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4730:2: (this_expression_0= ruleexpression )
            // InternalMASL.g:4731:2: this_expression_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getConditionAccess().getExpressionParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_expression_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current = this_expression_0;
              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecondition"


    // $ANTLR start "entryRulecaseStatement"
    // InternalMASL.g:4742:1: entryRulecaseStatement returns [EObject current=null] : iv_rulecaseStatement= rulecaseStatement EOF ;
    public final EObject entryRulecaseStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecaseStatement = null;


        try {
            // InternalMASL.g:4742:54: (iv_rulecaseStatement= rulecaseStatement EOF )
            // InternalMASL.g:4743:2: iv_rulecaseStatement= rulecaseStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCaseStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecaseStatement=rulecaseStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecaseStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecaseStatement"


    // $ANTLR start "rulecaseStatement"
    // InternalMASL.g:4749:1: rulecaseStatement returns [EObject current=null] : (this_CASE_0= RULE_CASE this_expression_1= ruleexpression this_IS_2= RULE_IS ( (lv_c1_3_0= rulecaseAlternative ) )* ( (lv_c2_4_0= rulecaseOthers ) )? this_END_5= RULE_END (this_CASE_6= RULE_CASE )? ) ;
    public final EObject rulecaseStatement() throws RecognitionException {
        EObject current = null;

        Token this_CASE_0=null;
        Token this_IS_2=null;
        Token this_END_5=null;
        Token this_CASE_6=null;
        EObject this_expression_1 = null;

        EObject lv_c1_3_0 = null;

        EObject lv_c2_4_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4755:2: ( (this_CASE_0= RULE_CASE this_expression_1= ruleexpression this_IS_2= RULE_IS ( (lv_c1_3_0= rulecaseAlternative ) )* ( (lv_c2_4_0= rulecaseOthers ) )? this_END_5= RULE_END (this_CASE_6= RULE_CASE )? ) )
            // InternalMASL.g:4756:2: (this_CASE_0= RULE_CASE this_expression_1= ruleexpression this_IS_2= RULE_IS ( (lv_c1_3_0= rulecaseAlternative ) )* ( (lv_c2_4_0= rulecaseOthers ) )? this_END_5= RULE_END (this_CASE_6= RULE_CASE )? )
            {
            // InternalMASL.g:4756:2: (this_CASE_0= RULE_CASE this_expression_1= ruleexpression this_IS_2= RULE_IS ( (lv_c1_3_0= rulecaseAlternative ) )* ( (lv_c2_4_0= rulecaseOthers ) )? this_END_5= RULE_END (this_CASE_6= RULE_CASE )? )
            // InternalMASL.g:4757:3: this_CASE_0= RULE_CASE this_expression_1= ruleexpression this_IS_2= RULE_IS ( (lv_c1_3_0= rulecaseAlternative ) )* ( (lv_c2_4_0= rulecaseOthers ) )? this_END_5= RULE_END (this_CASE_6= RULE_CASE )?
            {
            this_CASE_0=(Token)match(input,RULE_CASE,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_CASE_0, grammarAccess.getCaseStatementAccess().getCASETerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCaseStatementAccess().getExpressionParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_31);
            this_expression_1=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_1;
              			afterParserOrEnumRuleCall();
              		
            }
            this_IS_2=(Token)match(input,RULE_IS,FOLLOW_68); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IS_2, grammarAccess.getCaseStatementAccess().getISTerminalRuleCall_2());
              		
            }
            // InternalMASL.g:4773:3: ( (lv_c1_3_0= rulecaseAlternative ) )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==RULE_WHEN) ) {
                    int LA61_1 = input.LA(2);

                    if ( (LA61_1==RULE_INSTANCE||LA61_1==RULE_ANONYMOUS||LA61_1==RULE_LPAREN||(LA61_1>=RULE_SEQUENCE && LA61_1<=RULE_DICTIONARY)||LA61_1==RULE_NULL||(LA61_1>=RULE_LINK && LA61_1<=RULE_UNLINK)||LA61_1==RULE_NOT||(LA61_1>=RULE_PLUS && LA61_1<=RULE_MINUS)||LA61_1==RULE_ABS||LA61_1==RULE_CREATE||(LA61_1>=RULE_FIND && LA61_1<=RULE_IDENT)) ) {
                        alt61=1;
                    }


                }


                switch (alt61) {
            	case 1 :
            	    // InternalMASL.g:4774:4: (lv_c1_3_0= rulecaseAlternative )
            	    {
            	    // InternalMASL.g:4774:4: (lv_c1_3_0= rulecaseAlternative )
            	    // InternalMASL.g:4775:5: lv_c1_3_0= rulecaseAlternative
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getCaseStatementAccess().getC1CaseAlternativeParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_68);
            	    lv_c1_3_0=rulecaseAlternative();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getCaseStatementRule());
            	      					}
            	      					add(
            	      						current,
            	      						"c1",
            	      						lv_c1_3_0,
            	      						"org.xtuml.bp.ui.xtext.MASL.caseAlternative");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);

            // InternalMASL.g:4792:3: ( (lv_c2_4_0= rulecaseOthers ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_WHEN) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalMASL.g:4793:4: (lv_c2_4_0= rulecaseOthers )
                    {
                    // InternalMASL.g:4793:4: (lv_c2_4_0= rulecaseOthers )
                    // InternalMASL.g:4794:5: lv_c2_4_0= rulecaseOthers
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getCaseStatementAccess().getC2CaseOthersParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_64);
                    lv_c2_4_0=rulecaseOthers();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getCaseStatementRule());
                      					}
                      					set(
                      						current,
                      						"c2",
                      						lv_c2_4_0,
                      						"org.xtuml.bp.ui.xtext.MASL.caseOthers");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            this_END_5=(Token)match(input,RULE_END,FOLLOW_69); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_END_5, grammarAccess.getCaseStatementAccess().getENDTerminalRuleCall_5());
              		
            }
            // InternalMASL.g:4815:3: (this_CASE_6= RULE_CASE )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==RULE_CASE) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalMASL.g:4816:4: this_CASE_6= RULE_CASE
                    {
                    this_CASE_6=(Token)match(input,RULE_CASE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CASE_6, grammarAccess.getCaseStatementAccess().getCASETerminalRuleCall_6());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecaseStatement"


    // $ANTLR start "entryRulecaseAlternative"
    // InternalMASL.g:4825:1: entryRulecaseAlternative returns [EObject current=null] : iv_rulecaseAlternative= rulecaseAlternative EOF ;
    public final EObject entryRulecaseAlternative() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecaseAlternative = null;


        try {
            // InternalMASL.g:4825:56: (iv_rulecaseAlternative= rulecaseAlternative EOF )
            // InternalMASL.g:4826:2: iv_rulecaseAlternative= rulecaseAlternative EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCaseAlternativeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecaseAlternative=rulecaseAlternative();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecaseAlternative; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecaseAlternative"


    // $ANTLR start "rulecaseAlternative"
    // InternalMASL.g:4832:1: rulecaseAlternative returns [EObject current=null] : (this_WHEN_0= RULE_WHEN this_choiceList_1= rulechoiceList this_GOES_TO_2= RULE_GOES_TO ( (lv_s_3_0= rulestatementList ) ) ) ;
    public final EObject rulecaseAlternative() throws RecognitionException {
        EObject current = null;

        Token this_WHEN_0=null;
        Token this_GOES_TO_2=null;
        EObject this_choiceList_1 = null;

        EObject lv_s_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4838:2: ( (this_WHEN_0= RULE_WHEN this_choiceList_1= rulechoiceList this_GOES_TO_2= RULE_GOES_TO ( (lv_s_3_0= rulestatementList ) ) ) )
            // InternalMASL.g:4839:2: (this_WHEN_0= RULE_WHEN this_choiceList_1= rulechoiceList this_GOES_TO_2= RULE_GOES_TO ( (lv_s_3_0= rulestatementList ) ) )
            {
            // InternalMASL.g:4839:2: (this_WHEN_0= RULE_WHEN this_choiceList_1= rulechoiceList this_GOES_TO_2= RULE_GOES_TO ( (lv_s_3_0= rulestatementList ) ) )
            // InternalMASL.g:4840:3: this_WHEN_0= RULE_WHEN this_choiceList_1= rulechoiceList this_GOES_TO_2= RULE_GOES_TO ( (lv_s_3_0= rulestatementList ) )
            {
            this_WHEN_0=(Token)match(input,RULE_WHEN,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WHEN_0, grammarAccess.getCaseAlternativeAccess().getWHENTerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCaseAlternativeAccess().getChoiceListParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_70);
            this_choiceList_1=rulechoiceList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_choiceList_1;
              			afterParserOrEnumRuleCall();
              		
            }
            this_GOES_TO_2=(Token)match(input,RULE_GOES_TO,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_GOES_TO_2, grammarAccess.getCaseAlternativeAccess().getGOES_TOTerminalRuleCall_2());
              		
            }
            // InternalMASL.g:4856:3: ( (lv_s_3_0= rulestatementList ) )
            // InternalMASL.g:4857:4: (lv_s_3_0= rulestatementList )
            {
            // InternalMASL.g:4857:4: (lv_s_3_0= rulestatementList )
            // InternalMASL.g:4858:5: lv_s_3_0= rulestatementList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getCaseAlternativeAccess().getSStatementListParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_s_3_0=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getCaseAlternativeRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_3_0,
              						"org.xtuml.bp.ui.xtext.MASL.statementList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecaseAlternative"


    // $ANTLR start "entryRulechoiceList"
    // InternalMASL.g:4879:1: entryRulechoiceList returns [EObject current=null] : iv_rulechoiceList= rulechoiceList EOF ;
    public final EObject entryRulechoiceList() throws RecognitionException {
        EObject current = null;

        EObject iv_rulechoiceList = null;


        try {
            // InternalMASL.g:4879:51: (iv_rulechoiceList= rulechoiceList EOF )
            // InternalMASL.g:4880:2: iv_rulechoiceList= rulechoiceList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getChoiceListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulechoiceList=rulechoiceList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulechoiceList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulechoiceList"


    // $ANTLR start "rulechoiceList"
    // InternalMASL.g:4886:1: rulechoiceList returns [EObject current=null] : ( ( (lv_e_0_0= ruleexpression ) ) (this_CASE_OR_1= RULE_CASE_OR ( (lv_e_2_0= ruleexpression ) ) )* ) ;
    public final EObject rulechoiceList() throws RecognitionException {
        EObject current = null;

        Token this_CASE_OR_1=null;
        EObject lv_e_0_0 = null;

        EObject lv_e_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4892:2: ( ( ( (lv_e_0_0= ruleexpression ) ) (this_CASE_OR_1= RULE_CASE_OR ( (lv_e_2_0= ruleexpression ) ) )* ) )
            // InternalMASL.g:4893:2: ( ( (lv_e_0_0= ruleexpression ) ) (this_CASE_OR_1= RULE_CASE_OR ( (lv_e_2_0= ruleexpression ) ) )* )
            {
            // InternalMASL.g:4893:2: ( ( (lv_e_0_0= ruleexpression ) ) (this_CASE_OR_1= RULE_CASE_OR ( (lv_e_2_0= ruleexpression ) ) )* )
            // InternalMASL.g:4894:3: ( (lv_e_0_0= ruleexpression ) ) (this_CASE_OR_1= RULE_CASE_OR ( (lv_e_2_0= ruleexpression ) ) )*
            {
            // InternalMASL.g:4894:3: ( (lv_e_0_0= ruleexpression ) )
            // InternalMASL.g:4895:4: (lv_e_0_0= ruleexpression )
            {
            // InternalMASL.g:4895:4: (lv_e_0_0= ruleexpression )
            // InternalMASL.g:4896:5: lv_e_0_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getChoiceListAccess().getEExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_71);
            lv_e_0_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getChoiceListRule());
              					}
              					add(
              						current,
              						"e",
              						lv_e_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:4913:3: (this_CASE_OR_1= RULE_CASE_OR ( (lv_e_2_0= ruleexpression ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==RULE_CASE_OR) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // InternalMASL.g:4914:4: this_CASE_OR_1= RULE_CASE_OR ( (lv_e_2_0= ruleexpression ) )
            	    {
            	    this_CASE_OR_1=(Token)match(input,RULE_CASE_OR,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_CASE_OR_1, grammarAccess.getChoiceListAccess().getCASE_ORTerminalRuleCall_1_0());
            	      			
            	    }
            	    // InternalMASL.g:4918:4: ( (lv_e_2_0= ruleexpression ) )
            	    // InternalMASL.g:4919:5: (lv_e_2_0= ruleexpression )
            	    {
            	    // InternalMASL.g:4919:5: (lv_e_2_0= ruleexpression )
            	    // InternalMASL.g:4920:6: lv_e_2_0= ruleexpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getChoiceListAccess().getEExpressionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_71);
            	    lv_e_2_0=ruleexpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getChoiceListRule());
            	      						}
            	      						add(
            	      							current,
            	      							"e",
            	      							lv_e_2_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.expression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulechoiceList"


    // $ANTLR start "entryRulecaseOthers"
    // InternalMASL.g:4942:1: entryRulecaseOthers returns [EObject current=null] : iv_rulecaseOthers= rulecaseOthers EOF ;
    public final EObject entryRulecaseOthers() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecaseOthers = null;


        try {
            // InternalMASL.g:4942:51: (iv_rulecaseOthers= rulecaseOthers EOF )
            // InternalMASL.g:4943:2: iv_rulecaseOthers= rulecaseOthers EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCaseOthersRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecaseOthers=rulecaseOthers();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecaseOthers; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecaseOthers"


    // $ANTLR start "rulecaseOthers"
    // InternalMASL.g:4949:1: rulecaseOthers returns [EObject current=null] : (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList ) ;
    public final EObject rulecaseOthers() throws RecognitionException {
        EObject current = null;

        Token this_WHEN_0=null;
        Token this_OTHERS_1=null;
        Token this_GOES_TO_2=null;
        EObject this_statementList_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:4955:2: ( (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList ) )
            // InternalMASL.g:4956:2: (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList )
            {
            // InternalMASL.g:4956:2: (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList )
            // InternalMASL.g:4957:3: this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList
            {
            this_WHEN_0=(Token)match(input,RULE_WHEN,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WHEN_0, grammarAccess.getCaseOthersAccess().getWHENTerminalRuleCall_0());
              		
            }
            this_OTHERS_1=(Token)match(input,RULE_OTHERS,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_OTHERS_1, grammarAccess.getCaseOthersAccess().getOTHERSTerminalRuleCall_1());
              		
            }
            this_GOES_TO_2=(Token)match(input,RULE_GOES_TO,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_GOES_TO_2, grammarAccess.getCaseOthersAccess().getGOES_TOTerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCaseOthersAccess().getStatementListParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_statementList_3=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_statementList_3;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecaseOthers"


    // $ANTLR start "entryRuleforStatement"
    // InternalMASL.g:4981:1: entryRuleforStatement returns [EObject current=null] : iv_ruleforStatement= ruleforStatement EOF ;
    public final EObject entryRuleforStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleforStatement = null;


        try {
            // InternalMASL.g:4981:53: (iv_ruleforStatement= ruleforStatement EOF )
            // InternalMASL.g:4982:2: iv_ruleforStatement= ruleforStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getForStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleforStatement=ruleforStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleforStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleforStatement"


    // $ANTLR start "ruleforStatement"
    // InternalMASL.g:4988:1: ruleforStatement returns [EObject current=null] : (this_FOR_0= RULE_FOR this_loopVariableSpec_1= ruleloopVariableSpec this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? ) ;
    public final EObject ruleforStatement() throws RecognitionException {
        EObject current = null;

        Token this_FOR_0=null;
        Token this_LOOP_2=null;
        Token this_END_4=null;
        Token this_LOOP_5=null;
        EObject this_loopVariableSpec_1 = null;

        EObject lv_s_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:4994:2: ( (this_FOR_0= RULE_FOR this_loopVariableSpec_1= ruleloopVariableSpec this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? ) )
            // InternalMASL.g:4995:2: (this_FOR_0= RULE_FOR this_loopVariableSpec_1= ruleloopVariableSpec this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? )
            {
            // InternalMASL.g:4995:2: (this_FOR_0= RULE_FOR this_loopVariableSpec_1= ruleloopVariableSpec this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )? )
            // InternalMASL.g:4996:3: this_FOR_0= RULE_FOR this_loopVariableSpec_1= ruleloopVariableSpec this_LOOP_2= RULE_LOOP ( (lv_s_3_0= rulestatementList ) ) this_END_4= RULE_END (this_LOOP_5= RULE_LOOP )?
            {
            this_FOR_0=(Token)match(input,RULE_FOR,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_FOR_0, grammarAccess.getForStatementAccess().getFORTerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getForStatementAccess().getLoopVariableSpecParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_66);
            this_loopVariableSpec_1=ruleloopVariableSpec();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_loopVariableSpec_1;
              			afterParserOrEnumRuleCall();
              		
            }
            this_LOOP_2=(Token)match(input,RULE_LOOP,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LOOP_2, grammarAccess.getForStatementAccess().getLOOPTerminalRuleCall_2());
              		
            }
            // InternalMASL.g:5012:3: ( (lv_s_3_0= rulestatementList ) )
            // InternalMASL.g:5013:4: (lv_s_3_0= rulestatementList )
            {
            // InternalMASL.g:5013:4: (lv_s_3_0= rulestatementList )
            // InternalMASL.g:5014:5: lv_s_3_0= rulestatementList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getForStatementAccess().getSStatementListParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_64);
            lv_s_3_0=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getForStatementRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_3_0,
              						"org.xtuml.bp.ui.xtext.MASL.statementList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            this_END_4=(Token)match(input,RULE_END,FOLLOW_67); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_END_4, grammarAccess.getForStatementAccess().getENDTerminalRuleCall_4());
              		
            }
            // InternalMASL.g:5035:3: (this_LOOP_5= RULE_LOOP )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==RULE_LOOP) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalMASL.g:5036:4: this_LOOP_5= RULE_LOOP
                    {
                    this_LOOP_5=(Token)match(input,RULE_LOOP,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LOOP_5, grammarAccess.getForStatementAccess().getLOOPTerminalRuleCall_5());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleforStatement"


    // $ANTLR start "entryRuleloopVariableSpec"
    // InternalMASL.g:5045:1: entryRuleloopVariableSpec returns [EObject current=null] : iv_ruleloopVariableSpec= ruleloopVariableSpec EOF ;
    public final EObject entryRuleloopVariableSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleloopVariableSpec = null;


        try {
            // InternalMASL.g:5045:57: (iv_ruleloopVariableSpec= ruleloopVariableSpec EOF )
            // InternalMASL.g:5046:2: iv_ruleloopVariableSpec= ruleloopVariableSpec EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLoopVariableSpecRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleloopVariableSpec=ruleloopVariableSpec();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleloopVariableSpec; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleloopVariableSpec"


    // $ANTLR start "ruleloopVariableSpec"
    // InternalMASL.g:5052:1: ruleloopVariableSpec returns [EObject current=null] : ( ruleidentifier this_IN_1= RULE_IN (this_REVERSE_2= RULE_REVERSE )? this_expression_3= ruleexpression ) ;
    public final EObject ruleloopVariableSpec() throws RecognitionException {
        EObject current = null;

        Token this_IN_1=null;
        Token this_REVERSE_2=null;
        EObject this_expression_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:5058:2: ( ( ruleidentifier this_IN_1= RULE_IN (this_REVERSE_2= RULE_REVERSE )? this_expression_3= ruleexpression ) )
            // InternalMASL.g:5059:2: ( ruleidentifier this_IN_1= RULE_IN (this_REVERSE_2= RULE_REVERSE )? this_expression_3= ruleexpression )
            {
            // InternalMASL.g:5059:2: ( ruleidentifier this_IN_1= RULE_IN (this_REVERSE_2= RULE_REVERSE )? this_expression_3= ruleexpression )
            // InternalMASL.g:5060:3: ruleidentifier this_IN_1= RULE_IN (this_REVERSE_2= RULE_REVERSE )? this_expression_3= ruleexpression
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getLoopVariableSpecAccess().getIdentifierParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_73);
            ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_IN_1=(Token)match(input,RULE_IN,FOLLOW_74); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_IN_1, grammarAccess.getLoopVariableSpecAccess().getINTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:5071:3: (this_REVERSE_2= RULE_REVERSE )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==RULE_REVERSE) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalMASL.g:5072:4: this_REVERSE_2= RULE_REVERSE
                    {
                    this_REVERSE_2=(Token)match(input,RULE_REVERSE,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_REVERSE_2, grammarAccess.getLoopVariableSpecAccess().getREVERSETerminalRuleCall_2());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getLoopVariableSpecAccess().getExpressionParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_expression_3=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_expression_3;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleloopVariableSpec"


    // $ANTLR start "entryRulecodeBlockStatement"
    // InternalMASL.g:5089:1: entryRulecodeBlockStatement returns [EObject current=null] : iv_rulecodeBlockStatement= rulecodeBlockStatement EOF ;
    public final EObject entryRulecodeBlockStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecodeBlockStatement = null;


        try {
            // InternalMASL.g:5089:59: (iv_rulecodeBlockStatement= rulecodeBlockStatement EOF )
            // InternalMASL.g:5090:2: iv_rulecodeBlockStatement= rulecodeBlockStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCodeBlockStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecodeBlockStatement=rulecodeBlockStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecodeBlockStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecodeBlockStatement"


    // $ANTLR start "rulecodeBlockStatement"
    // InternalMASL.g:5096:1: rulecodeBlockStatement returns [EObject current=null] : ( (this_DECLARE_0= RULE_DECLARE ( (lv_v_1_0= rulevariableDeclaration ) )* )? this_BEGIN_2= RULE_BEGIN ( (lv_s_3_0= rulestatementList ) ) (this_EXCEPTION_4= RULE_EXCEPTION ( (lv_e_5_0= ruleexceptionHandler ) )* ( (lv_p_6_0= ruleotherHandler ) )? )? this_END_7= RULE_END ) ;
    public final EObject rulecodeBlockStatement() throws RecognitionException {
        EObject current = null;

        Token this_DECLARE_0=null;
        Token this_BEGIN_2=null;
        Token this_EXCEPTION_4=null;
        Token this_END_7=null;
        EObject lv_v_1_0 = null;

        EObject lv_s_3_0 = null;

        EObject lv_e_5_0 = null;

        EObject lv_p_6_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5102:2: ( ( (this_DECLARE_0= RULE_DECLARE ( (lv_v_1_0= rulevariableDeclaration ) )* )? this_BEGIN_2= RULE_BEGIN ( (lv_s_3_0= rulestatementList ) ) (this_EXCEPTION_4= RULE_EXCEPTION ( (lv_e_5_0= ruleexceptionHandler ) )* ( (lv_p_6_0= ruleotherHandler ) )? )? this_END_7= RULE_END ) )
            // InternalMASL.g:5103:2: ( (this_DECLARE_0= RULE_DECLARE ( (lv_v_1_0= rulevariableDeclaration ) )* )? this_BEGIN_2= RULE_BEGIN ( (lv_s_3_0= rulestatementList ) ) (this_EXCEPTION_4= RULE_EXCEPTION ( (lv_e_5_0= ruleexceptionHandler ) )* ( (lv_p_6_0= ruleotherHandler ) )? )? this_END_7= RULE_END )
            {
            // InternalMASL.g:5103:2: ( (this_DECLARE_0= RULE_DECLARE ( (lv_v_1_0= rulevariableDeclaration ) )* )? this_BEGIN_2= RULE_BEGIN ( (lv_s_3_0= rulestatementList ) ) (this_EXCEPTION_4= RULE_EXCEPTION ( (lv_e_5_0= ruleexceptionHandler ) )* ( (lv_p_6_0= ruleotherHandler ) )? )? this_END_7= RULE_END )
            // InternalMASL.g:5104:3: (this_DECLARE_0= RULE_DECLARE ( (lv_v_1_0= rulevariableDeclaration ) )* )? this_BEGIN_2= RULE_BEGIN ( (lv_s_3_0= rulestatementList ) ) (this_EXCEPTION_4= RULE_EXCEPTION ( (lv_e_5_0= ruleexceptionHandler ) )* ( (lv_p_6_0= ruleotherHandler ) )? )? this_END_7= RULE_END
            {
            // InternalMASL.g:5104:3: (this_DECLARE_0= RULE_DECLARE ( (lv_v_1_0= rulevariableDeclaration ) )* )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_DECLARE) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalMASL.g:5105:4: this_DECLARE_0= RULE_DECLARE ( (lv_v_1_0= rulevariableDeclaration ) )*
                    {
                    this_DECLARE_0=(Token)match(input,RULE_DECLARE,FOLLOW_32); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_DECLARE_0, grammarAccess.getCodeBlockStatementAccess().getDECLARETerminalRuleCall_0_0());
                      			
                    }
                    // InternalMASL.g:5109:4: ( (lv_v_1_0= rulevariableDeclaration ) )*
                    loop67:
                    do {
                        int alt67=2;
                        int LA67_0 = input.LA(1);

                        if ( (LA67_0==RULE_IDENT) ) {
                            alt67=1;
                        }


                        switch (alt67) {
                    	case 1 :
                    	    // InternalMASL.g:5110:5: (lv_v_1_0= rulevariableDeclaration )
                    	    {
                    	    // InternalMASL.g:5110:5: (lv_v_1_0= rulevariableDeclaration )
                    	    // InternalMASL.g:5111:6: lv_v_1_0= rulevariableDeclaration
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getCodeBlockStatementAccess().getVVariableDeclarationParserRuleCall_0_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_32);
                    	    lv_v_1_0=rulevariableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getCodeBlockStatementRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"v",
                    	      							lv_v_1_0,
                    	      							"org.xtuml.bp.ui.xtext.MASL.variableDeclaration");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop67;
                        }
                    } while (true);


                    }
                    break;

            }

            this_BEGIN_2=(Token)match(input,RULE_BEGIN,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_BEGIN_2, grammarAccess.getCodeBlockStatementAccess().getBEGINTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:5133:3: ( (lv_s_3_0= rulestatementList ) )
            // InternalMASL.g:5134:4: (lv_s_3_0= rulestatementList )
            {
            // InternalMASL.g:5134:4: (lv_s_3_0= rulestatementList )
            // InternalMASL.g:5135:5: lv_s_3_0= rulestatementList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getCodeBlockStatementAccess().getSStatementListParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_75);
            lv_s_3_0=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getCodeBlockStatementRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_3_0,
              						"org.xtuml.bp.ui.xtext.MASL.statementList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5152:3: (this_EXCEPTION_4= RULE_EXCEPTION ( (lv_e_5_0= ruleexceptionHandler ) )* ( (lv_p_6_0= ruleotherHandler ) )? )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_EXCEPTION) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalMASL.g:5153:4: this_EXCEPTION_4= RULE_EXCEPTION ( (lv_e_5_0= ruleexceptionHandler ) )* ( (lv_p_6_0= ruleotherHandler ) )?
                    {
                    this_EXCEPTION_4=(Token)match(input,RULE_EXCEPTION,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_EXCEPTION_4, grammarAccess.getCodeBlockStatementAccess().getEXCEPTIONTerminalRuleCall_3_0());
                      			
                    }
                    // InternalMASL.g:5157:4: ( (lv_e_5_0= ruleexceptionHandler ) )*
                    loop69:
                    do {
                        int alt69=2;
                        int LA69_0 = input.LA(1);

                        if ( (LA69_0==RULE_WHEN) ) {
                            int LA69_1 = input.LA(2);

                            if ( (LA69_1==RULE_IDENT) ) {
                                alt69=1;
                            }


                        }


                        switch (alt69) {
                    	case 1 :
                    	    // InternalMASL.g:5158:5: (lv_e_5_0= ruleexceptionHandler )
                    	    {
                    	    // InternalMASL.g:5158:5: (lv_e_5_0= ruleexceptionHandler )
                    	    // InternalMASL.g:5159:6: lv_e_5_0= ruleexceptionHandler
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getCodeBlockStatementAccess().getEExceptionHandlerParserRuleCall_3_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_68);
                    	    lv_e_5_0=ruleexceptionHandler();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getCodeBlockStatementRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"e",
                    	      							lv_e_5_0,
                    	      							"org.xtuml.bp.ui.xtext.MASL.exceptionHandler");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop69;
                        }
                    } while (true);

                    // InternalMASL.g:5176:4: ( (lv_p_6_0= ruleotherHandler ) )?
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==RULE_WHEN) ) {
                        alt70=1;
                    }
                    switch (alt70) {
                        case 1 :
                            // InternalMASL.g:5177:5: (lv_p_6_0= ruleotherHandler )
                            {
                            // InternalMASL.g:5177:5: (lv_p_6_0= ruleotherHandler )
                            // InternalMASL.g:5178:6: lv_p_6_0= ruleotherHandler
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getCodeBlockStatementAccess().getPOtherHandlerParserRuleCall_3_2_0());
                              					
                            }
                            pushFollow(FOLLOW_64);
                            lv_p_6_0=ruleotherHandler();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getCodeBlockStatementRule());
                              						}
                              						set(
                              							current,
                              							"p",
                              							lv_p_6_0,
                              							"org.xtuml.bp.ui.xtext.MASL.otherHandler");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }

            this_END_7=(Token)match(input,RULE_END,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_END_7, grammarAccess.getCodeBlockStatementAccess().getENDTerminalRuleCall_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecodeBlockStatement"


    // $ANTLR start "entryRulecodeBlock"
    // InternalMASL.g:5204:1: entryRulecodeBlock returns [EObject current=null] : iv_rulecodeBlock= rulecodeBlock EOF ;
    public final EObject entryRulecodeBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecodeBlock = null;


        try {
            // InternalMASL.g:5204:50: (iv_rulecodeBlock= rulecodeBlock EOF )
            // InternalMASL.g:5205:2: iv_rulecodeBlock= rulecodeBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCodeBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecodeBlock=rulecodeBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecodeBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecodeBlock"


    // $ANTLR start "rulecodeBlock"
    // InternalMASL.g:5211:1: rulecodeBlock returns [EObject current=null] : ( ( (lv_v_0_0= rulevariableDeclaration ) )* this_BEGIN_1= RULE_BEGIN ( (lv_s_2_0= rulestatementList ) ) (this_EXCEPTION_3= RULE_EXCEPTION ( (lv_e_4_0= ruleexceptionHandler ) )* ( (lv_o_5_0= ruleotherHandler ) )? )? this_END_6= RULE_END ) ;
    public final EObject rulecodeBlock() throws RecognitionException {
        EObject current = null;

        Token this_BEGIN_1=null;
        Token this_EXCEPTION_3=null;
        Token this_END_6=null;
        EObject lv_v_0_0 = null;

        EObject lv_s_2_0 = null;

        EObject lv_e_4_0 = null;

        EObject lv_o_5_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5217:2: ( ( ( (lv_v_0_0= rulevariableDeclaration ) )* this_BEGIN_1= RULE_BEGIN ( (lv_s_2_0= rulestatementList ) ) (this_EXCEPTION_3= RULE_EXCEPTION ( (lv_e_4_0= ruleexceptionHandler ) )* ( (lv_o_5_0= ruleotherHandler ) )? )? this_END_6= RULE_END ) )
            // InternalMASL.g:5218:2: ( ( (lv_v_0_0= rulevariableDeclaration ) )* this_BEGIN_1= RULE_BEGIN ( (lv_s_2_0= rulestatementList ) ) (this_EXCEPTION_3= RULE_EXCEPTION ( (lv_e_4_0= ruleexceptionHandler ) )* ( (lv_o_5_0= ruleotherHandler ) )? )? this_END_6= RULE_END )
            {
            // InternalMASL.g:5218:2: ( ( (lv_v_0_0= rulevariableDeclaration ) )* this_BEGIN_1= RULE_BEGIN ( (lv_s_2_0= rulestatementList ) ) (this_EXCEPTION_3= RULE_EXCEPTION ( (lv_e_4_0= ruleexceptionHandler ) )* ( (lv_o_5_0= ruleotherHandler ) )? )? this_END_6= RULE_END )
            // InternalMASL.g:5219:3: ( (lv_v_0_0= rulevariableDeclaration ) )* this_BEGIN_1= RULE_BEGIN ( (lv_s_2_0= rulestatementList ) ) (this_EXCEPTION_3= RULE_EXCEPTION ( (lv_e_4_0= ruleexceptionHandler ) )* ( (lv_o_5_0= ruleotherHandler ) )? )? this_END_6= RULE_END
            {
            // InternalMASL.g:5219:3: ( (lv_v_0_0= rulevariableDeclaration ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==RULE_IDENT) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // InternalMASL.g:5220:4: (lv_v_0_0= rulevariableDeclaration )
            	    {
            	    // InternalMASL.g:5220:4: (lv_v_0_0= rulevariableDeclaration )
            	    // InternalMASL.g:5221:5: lv_v_0_0= rulevariableDeclaration
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getCodeBlockAccess().getVVariableDeclarationParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_32);
            	    lv_v_0_0=rulevariableDeclaration();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getCodeBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"v",
            	      						lv_v_0_0,
            	      						"org.xtuml.bp.ui.xtext.MASL.variableDeclaration");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);

            this_BEGIN_1=(Token)match(input,RULE_BEGIN,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_BEGIN_1, grammarAccess.getCodeBlockAccess().getBEGINTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:5242:3: ( (lv_s_2_0= rulestatementList ) )
            // InternalMASL.g:5243:4: (lv_s_2_0= rulestatementList )
            {
            // InternalMASL.g:5243:4: (lv_s_2_0= rulestatementList )
            // InternalMASL.g:5244:5: lv_s_2_0= rulestatementList
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getCodeBlockAccess().getSStatementListParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_75);
            lv_s_2_0=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getCodeBlockRule());
              					}
              					set(
              						current,
              						"s",
              						lv_s_2_0,
              						"org.xtuml.bp.ui.xtext.MASL.statementList");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5261:3: (this_EXCEPTION_3= RULE_EXCEPTION ( (lv_e_4_0= ruleexceptionHandler ) )* ( (lv_o_5_0= ruleotherHandler ) )? )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==RULE_EXCEPTION) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalMASL.g:5262:4: this_EXCEPTION_3= RULE_EXCEPTION ( (lv_e_4_0= ruleexceptionHandler ) )* ( (lv_o_5_0= ruleotherHandler ) )?
                    {
                    this_EXCEPTION_3=(Token)match(input,RULE_EXCEPTION,FOLLOW_68); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_EXCEPTION_3, grammarAccess.getCodeBlockAccess().getEXCEPTIONTerminalRuleCall_3_0());
                      			
                    }
                    // InternalMASL.g:5266:4: ( (lv_e_4_0= ruleexceptionHandler ) )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==RULE_WHEN) ) {
                            int LA73_1 = input.LA(2);

                            if ( (LA73_1==RULE_IDENT) ) {
                                alt73=1;
                            }


                        }


                        switch (alt73) {
                    	case 1 :
                    	    // InternalMASL.g:5267:5: (lv_e_4_0= ruleexceptionHandler )
                    	    {
                    	    // InternalMASL.g:5267:5: (lv_e_4_0= ruleexceptionHandler )
                    	    // InternalMASL.g:5268:6: lv_e_4_0= ruleexceptionHandler
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      						newCompositeNode(grammarAccess.getCodeBlockAccess().getEExceptionHandlerParserRuleCall_3_1_0());
                    	      					
                    	    }
                    	    pushFollow(FOLLOW_68);
                    	    lv_e_4_0=ruleexceptionHandler();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						if (current==null) {
                    	      							current = createModelElementForParent(grammarAccess.getCodeBlockRule());
                    	      						}
                    	      						add(
                    	      							current,
                    	      							"e",
                    	      							lv_e_4_0,
                    	      							"org.xtuml.bp.ui.xtext.MASL.exceptionHandler");
                    	      						afterParserOrEnumRuleCall();
                    	      					
                    	    }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop73;
                        }
                    } while (true);

                    // InternalMASL.g:5285:4: ( (lv_o_5_0= ruleotherHandler ) )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==RULE_WHEN) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // InternalMASL.g:5286:5: (lv_o_5_0= ruleotherHandler )
                            {
                            // InternalMASL.g:5286:5: (lv_o_5_0= ruleotherHandler )
                            // InternalMASL.g:5287:6: lv_o_5_0= ruleotherHandler
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getCodeBlockAccess().getOOtherHandlerParserRuleCall_3_2_0());
                              					
                            }
                            pushFollow(FOLLOW_64);
                            lv_o_5_0=ruleotherHandler();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getCodeBlockRule());
                              						}
                              						set(
                              							current,
                              							"o",
                              							lv_o_5_0,
                              							"org.xtuml.bp.ui.xtext.MASL.otherHandler");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }

            this_END_6=(Token)match(input,RULE_END,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_END_6, grammarAccess.getCodeBlockAccess().getENDTerminalRuleCall_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecodeBlock"


    // $ANTLR start "entryRulevariableDeclaration"
    // InternalMASL.g:5313:1: entryRulevariableDeclaration returns [EObject current=null] : iv_rulevariableDeclaration= rulevariableDeclaration EOF ;
    public final EObject entryRulevariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_rulevariableDeclaration = null;


        try {
            // InternalMASL.g:5313:60: (iv_rulevariableDeclaration= rulevariableDeclaration EOF )
            // InternalMASL.g:5314:2: iv_rulevariableDeclaration= rulevariableDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVariableDeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulevariableDeclaration=rulevariableDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulevariableDeclaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulevariableDeclaration"


    // $ANTLR start "rulevariableDeclaration"
    // InternalMASL.g:5320:1: rulevariableDeclaration returns [EObject current=null] : ( rulevariableName this_COLON_1= RULE_COLON (this_READONLY_2= RULE_READONLY )? ( (lv_t_3_0= ruletypeReferenceWithCA ) ) (this_ASSIGN_4= RULE_ASSIGN ( (lv_e_5_0= ruleexpression ) ) )? this_SEMI_6= RULE_SEMI rulepragmaList ) ;
    public final EObject rulevariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token this_COLON_1=null;
        Token this_READONLY_2=null;
        Token this_ASSIGN_4=null;
        Token this_SEMI_6=null;
        EObject lv_t_3_0 = null;

        EObject lv_e_5_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5326:2: ( ( rulevariableName this_COLON_1= RULE_COLON (this_READONLY_2= RULE_READONLY )? ( (lv_t_3_0= ruletypeReferenceWithCA ) ) (this_ASSIGN_4= RULE_ASSIGN ( (lv_e_5_0= ruleexpression ) ) )? this_SEMI_6= RULE_SEMI rulepragmaList ) )
            // InternalMASL.g:5327:2: ( rulevariableName this_COLON_1= RULE_COLON (this_READONLY_2= RULE_READONLY )? ( (lv_t_3_0= ruletypeReferenceWithCA ) ) (this_ASSIGN_4= RULE_ASSIGN ( (lv_e_5_0= ruleexpression ) ) )? this_SEMI_6= RULE_SEMI rulepragmaList )
            {
            // InternalMASL.g:5327:2: ( rulevariableName this_COLON_1= RULE_COLON (this_READONLY_2= RULE_READONLY )? ( (lv_t_3_0= ruletypeReferenceWithCA ) ) (this_ASSIGN_4= RULE_ASSIGN ( (lv_e_5_0= ruleexpression ) ) )? this_SEMI_6= RULE_SEMI rulepragmaList )
            // InternalMASL.g:5328:3: rulevariableName this_COLON_1= RULE_COLON (this_READONLY_2= RULE_READONLY )? ( (lv_t_3_0= ruletypeReferenceWithCA ) ) (this_ASSIGN_4= RULE_ASSIGN ( (lv_e_5_0= ruleexpression ) ) )? this_SEMI_6= RULE_SEMI rulepragmaList
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getVariableNameParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_24);
            rulevariableName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_COLON_1=(Token)match(input,RULE_COLON,FOLLOW_76); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_COLON_1, grammarAccess.getVariableDeclarationAccess().getCOLONTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:5339:3: (this_READONLY_2= RULE_READONLY )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==RULE_READONLY) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalMASL.g:5340:4: this_READONLY_2= RULE_READONLY
                    {
                    this_READONLY_2=(Token)match(input,RULE_READONLY,FOLLOW_76); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_READONLY_2, grammarAccess.getVariableDeclarationAccess().getREADONLYTerminalRuleCall_2());
                      			
                    }

                    }
                    break;

            }

            // InternalMASL.g:5345:3: ( (lv_t_3_0= ruletypeReferenceWithCA ) )
            // InternalMASL.g:5346:4: (lv_t_3_0= ruletypeReferenceWithCA )
            {
            // InternalMASL.g:5346:4: (lv_t_3_0= ruletypeReferenceWithCA )
            // InternalMASL.g:5347:5: lv_t_3_0= ruletypeReferenceWithCA
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVariableDeclarationAccess().getTTypeReferenceWithCAParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_77);
            lv_t_3_0=ruletypeReferenceWithCA();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
              					}
              					set(
              						current,
              						"t",
              						lv_t_3_0,
              						"org.xtuml.bp.ui.xtext.MASL.typeReferenceWithCA");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5364:3: (this_ASSIGN_4= RULE_ASSIGN ( (lv_e_5_0= ruleexpression ) ) )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==RULE_ASSIGN) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalMASL.g:5365:4: this_ASSIGN_4= RULE_ASSIGN ( (lv_e_5_0= ruleexpression ) )
                    {
                    this_ASSIGN_4=(Token)match(input,RULE_ASSIGN,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_ASSIGN_4, grammarAccess.getVariableDeclarationAccess().getASSIGNTerminalRuleCall_4_0());
                      			
                    }
                    // InternalMASL.g:5369:4: ( (lv_e_5_0= ruleexpression ) )
                    // InternalMASL.g:5370:5: (lv_e_5_0= ruleexpression )
                    {
                    // InternalMASL.g:5370:5: (lv_e_5_0= ruleexpression )
                    // InternalMASL.g:5371:6: lv_e_5_0= ruleexpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getVariableDeclarationAccess().getEExpressionParserRuleCall_4_1_0());
                      					
                    }
                    pushFollow(FOLLOW_26);
                    lv_e_5_0=ruleexpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getVariableDeclarationRule());
                      						}
                      						set(
                      							current,
                      							"e",
                      							lv_e_5_0,
                      							"org.xtuml.bp.ui.xtext.MASL.expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            this_SEMI_6=(Token)match(input,RULE_SEMI,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_SEMI_6, grammarAccess.getVariableDeclarationAccess().getSEMITerminalRuleCall_5());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getVariableDeclarationAccess().getPragmaListParserRuleCall_6());
              		
            }
            pushFollow(FOLLOW_2);
            rulepragmaList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulevariableDeclaration"


    // $ANTLR start "entryRuleexceptionHandler"
    // InternalMASL.g:5404:1: entryRuleexceptionHandler returns [EObject current=null] : iv_ruleexceptionHandler= ruleexceptionHandler EOF ;
    public final EObject entryRuleexceptionHandler() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleexceptionHandler = null;


        try {
            // InternalMASL.g:5404:57: (iv_ruleexceptionHandler= ruleexceptionHandler EOF )
            // InternalMASL.g:5405:2: iv_ruleexceptionHandler= ruleexceptionHandler EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExceptionHandlerRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleexceptionHandler=ruleexceptionHandler();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleexceptionHandler; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleexceptionHandler"


    // $ANTLR start "ruleexceptionHandler"
    // InternalMASL.g:5411:1: ruleexceptionHandler returns [EObject current=null] : (this_WHEN_0= RULE_WHEN rulequalifiedExceptionName this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList ) ;
    public final EObject ruleexceptionHandler() throws RecognitionException {
        EObject current = null;

        Token this_WHEN_0=null;
        Token this_GOES_TO_2=null;
        EObject this_statementList_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:5417:2: ( (this_WHEN_0= RULE_WHEN rulequalifiedExceptionName this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList ) )
            // InternalMASL.g:5418:2: (this_WHEN_0= RULE_WHEN rulequalifiedExceptionName this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList )
            {
            // InternalMASL.g:5418:2: (this_WHEN_0= RULE_WHEN rulequalifiedExceptionName this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList )
            // InternalMASL.g:5419:3: this_WHEN_0= RULE_WHEN rulequalifiedExceptionName this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList
            {
            this_WHEN_0=(Token)match(input,RULE_WHEN,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WHEN_0, grammarAccess.getExceptionHandlerAccess().getWHENTerminalRuleCall_0());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getExceptionHandlerAccess().getQualifiedExceptionNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_70);
            rulequalifiedExceptionName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            this_GOES_TO_2=(Token)match(input,RULE_GOES_TO,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_GOES_TO_2, grammarAccess.getExceptionHandlerAccess().getGOES_TOTerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getExceptionHandlerAccess().getStatementListParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_statementList_3=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_statementList_3;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleexceptionHandler"


    // $ANTLR start "entryRuleotherHandler"
    // InternalMASL.g:5446:1: entryRuleotherHandler returns [EObject current=null] : iv_ruleotherHandler= ruleotherHandler EOF ;
    public final EObject entryRuleotherHandler() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleotherHandler = null;


        try {
            // InternalMASL.g:5446:53: (iv_ruleotherHandler= ruleotherHandler EOF )
            // InternalMASL.g:5447:2: iv_ruleotherHandler= ruleotherHandler EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOtherHandlerRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleotherHandler=ruleotherHandler();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleotherHandler; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleotherHandler"


    // $ANTLR start "ruleotherHandler"
    // InternalMASL.g:5453:1: ruleotherHandler returns [EObject current=null] : (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList ) ;
    public final EObject ruleotherHandler() throws RecognitionException {
        EObject current = null;

        Token this_WHEN_0=null;
        Token this_OTHERS_1=null;
        Token this_GOES_TO_2=null;
        EObject this_statementList_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:5459:2: ( (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList ) )
            // InternalMASL.g:5460:2: (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList )
            {
            // InternalMASL.g:5460:2: (this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList )
            // InternalMASL.g:5461:3: this_WHEN_0= RULE_WHEN this_OTHERS_1= RULE_OTHERS this_GOES_TO_2= RULE_GOES_TO this_statementList_3= rulestatementList
            {
            this_WHEN_0=(Token)match(input,RULE_WHEN,FOLLOW_72); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_WHEN_0, grammarAccess.getOtherHandlerAccess().getWHENTerminalRuleCall_0());
              		
            }
            this_OTHERS_1=(Token)match(input,RULE_OTHERS,FOLLOW_70); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_OTHERS_1, grammarAccess.getOtherHandlerAccess().getOTHERSTerminalRuleCall_1());
              		
            }
            this_GOES_TO_2=(Token)match(input,RULE_GOES_TO,FOLLOW_62); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_GOES_TO_2, grammarAccess.getOtherHandlerAccess().getGOES_TOTerminalRuleCall_2());
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getOtherHandlerAccess().getStatementListParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_statementList_3=rulestatementList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_statementList_3;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleotherHandler"


    // $ANTLR start "entryRulequalifiedExceptionName"
    // InternalMASL.g:5485:1: entryRulequalifiedExceptionName returns [String current=null] : iv_rulequalifiedExceptionName= rulequalifiedExceptionName EOF ;
    public final String entryRulequalifiedExceptionName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulequalifiedExceptionName = null;


        try {
            // InternalMASL.g:5485:62: (iv_rulequalifiedExceptionName= rulequalifiedExceptionName EOF )
            // InternalMASL.g:5486:2: iv_rulequalifiedExceptionName= rulequalifiedExceptionName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedExceptionNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulequalifiedExceptionName=rulequalifiedExceptionName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulequalifiedExceptionName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulequalifiedExceptionName"


    // $ANTLR start "rulequalifiedExceptionName"
    // InternalMASL.g:5492:1: rulequalifiedExceptionName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_exceptionName_2= ruleexceptionName ) ;
    public final AntlrDatatypeRuleToken rulequalifiedExceptionName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_SCOPE_1=null;
        AntlrDatatypeRuleToken this_domainName_0 = null;

        AntlrDatatypeRuleToken this_exceptionName_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:5498:2: ( ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_exceptionName_2= ruleexceptionName ) )
            // InternalMASL.g:5499:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_exceptionName_2= ruleexceptionName )
            {
            // InternalMASL.g:5499:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_exceptionName_2= ruleexceptionName )
            // InternalMASL.g:5500:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_exceptionName_2= ruleexceptionName
            {
            // InternalMASL.g:5500:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==RULE_IDENT) ) {
                int LA78_1 = input.LA(2);

                if ( (LA78_1==RULE_SCOPE) ) {
                    alt78=1;
                }
            }
            switch (alt78) {
                case 1 :
                    // InternalMASL.g:5501:4: this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getQualifiedExceptionNameAccess().getDomainNameParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_3);
                    this_domainName_0=ruledomainName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_domainName_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    this_SCOPE_1=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SCOPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SCOPE_1, grammarAccess.getQualifiedExceptionNameAccess().getSCOPETerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getQualifiedExceptionNameAccess().getExceptionNameParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_exceptionName_2=ruleexceptionName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_exceptionName_2);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulequalifiedExceptionName"


    // $ANTLR start "entryRulevariableName"
    // InternalMASL.g:5533:1: entryRulevariableName returns [String current=null] : iv_rulevariableName= rulevariableName EOF ;
    public final String entryRulevariableName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulevariableName = null;


        try {
            // InternalMASL.g:5533:52: (iv_rulevariableName= rulevariableName EOF )
            // InternalMASL.g:5534:2: iv_rulevariableName= rulevariableName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVariableNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulevariableName=rulevariableName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulevariableName.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulevariableName"


    // $ANTLR start "rulevariableName"
    // InternalMASL.g:5540:1: rulevariableName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_identifier_0= ruleidentifier ;
    public final AntlrDatatypeRuleToken rulevariableName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_identifier_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5546:2: (this_identifier_0= ruleidentifier )
            // InternalMASL.g:5547:2: this_identifier_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              		newCompositeNode(grammarAccess.getVariableNameAccess().getIdentifierParserRuleCall());
              	
            }
            pushFollow(FOLLOW_2);
            this_identifier_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_identifier_0);
              	
            }
            if ( state.backtracking==0 ) {

              		afterParserOrEnumRuleCall();
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulevariableName"


    // $ANTLR start "entryRulefindCondition"
    // InternalMASL.g:5560:1: entryRulefindCondition returns [EObject current=null] : iv_rulefindCondition= rulefindCondition EOF ;
    public final EObject entryRulefindCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindCondition = null;


        try {
            // InternalMASL.g:5560:54: (iv_rulefindCondition= rulefindCondition EOF )
            // InternalMASL.g:5561:2: iv_rulefindCondition= rulefindCondition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindConditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindCondition=rulefindCondition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindCondition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindCondition"


    // $ANTLR start "rulefindCondition"
    // InternalMASL.g:5567:1: rulefindCondition returns [EObject current=null] : ( (lv_f_0_0= rulefindLogicalOr ) ) ;
    public final EObject rulefindCondition() throws RecognitionException {
        EObject current = null;

        EObject lv_f_0_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5573:2: ( ( (lv_f_0_0= rulefindLogicalOr ) ) )
            // InternalMASL.g:5574:2: ( (lv_f_0_0= rulefindLogicalOr ) )
            {
            // InternalMASL.g:5574:2: ( (lv_f_0_0= rulefindLogicalOr ) )
            // InternalMASL.g:5575:3: (lv_f_0_0= rulefindLogicalOr )
            {
            // InternalMASL.g:5575:3: (lv_f_0_0= rulefindLogicalOr )
            // InternalMASL.g:5576:4: lv_f_0_0= rulefindLogicalOr
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getFindConditionAccess().getFFindLogicalOrParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_f_0_0=rulefindLogicalOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getFindConditionRule());
              				}
              				set(
              					current,
              					"f",
              					lv_f_0_0,
              					"org.xtuml.bp.ui.xtext.MASL.findLogicalOr");
              				afterParserOrEnumRuleCall();
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindCondition"


    // $ANTLR start "entryRulefindLogicalOr"
    // InternalMASL.g:5596:1: entryRulefindLogicalOr returns [EObject current=null] : iv_rulefindLogicalOr= rulefindLogicalOr EOF ;
    public final EObject entryRulefindLogicalOr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindLogicalOr = null;


        try {
            // InternalMASL.g:5596:54: (iv_rulefindLogicalOr= rulefindLogicalOr EOF )
            // InternalMASL.g:5597:2: iv_rulefindLogicalOr= rulefindLogicalOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindLogicalOrRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindLogicalOr=rulefindLogicalOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindLogicalOr; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindLogicalOr"


    // $ANTLR start "rulefindLogicalOr"
    // InternalMASL.g:5603:1: rulefindLogicalOr returns [EObject current=null] : ( ( (lv_f_0_0= rulefindLogicalXor ) ) (this_OR_1= RULE_OR ( (lv_f_2_0= rulefindLogicalXor ) ) )* ) ;
    public final EObject rulefindLogicalOr() throws RecognitionException {
        EObject current = null;

        Token this_OR_1=null;
        EObject lv_f_0_0 = null;

        EObject lv_f_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5609:2: ( ( ( (lv_f_0_0= rulefindLogicalXor ) ) (this_OR_1= RULE_OR ( (lv_f_2_0= rulefindLogicalXor ) ) )* ) )
            // InternalMASL.g:5610:2: ( ( (lv_f_0_0= rulefindLogicalXor ) ) (this_OR_1= RULE_OR ( (lv_f_2_0= rulefindLogicalXor ) ) )* )
            {
            // InternalMASL.g:5610:2: ( ( (lv_f_0_0= rulefindLogicalXor ) ) (this_OR_1= RULE_OR ( (lv_f_2_0= rulefindLogicalXor ) ) )* )
            // InternalMASL.g:5611:3: ( (lv_f_0_0= rulefindLogicalXor ) ) (this_OR_1= RULE_OR ( (lv_f_2_0= rulefindLogicalXor ) ) )*
            {
            // InternalMASL.g:5611:3: ( (lv_f_0_0= rulefindLogicalXor ) )
            // InternalMASL.g:5612:4: (lv_f_0_0= rulefindLogicalXor )
            {
            // InternalMASL.g:5612:4: (lv_f_0_0= rulefindLogicalXor )
            // InternalMASL.g:5613:5: lv_f_0_0= rulefindLogicalXor
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindLogicalOrAccess().getFFindLogicalXorParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_78);
            lv_f_0_0=rulefindLogicalXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindLogicalOrRule());
              					}
              					add(
              						current,
              						"f",
              						lv_f_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.findLogicalXor");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5630:3: (this_OR_1= RULE_OR ( (lv_f_2_0= rulefindLogicalXor ) ) )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==RULE_OR) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalMASL.g:5631:4: this_OR_1= RULE_OR ( (lv_f_2_0= rulefindLogicalXor ) )
            	    {
            	    this_OR_1=(Token)match(input,RULE_OR,FOLLOW_79); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_OR_1, grammarAccess.getFindLogicalOrAccess().getORTerminalRuleCall_1_0());
            	      			
            	    }
            	    // InternalMASL.g:5635:4: ( (lv_f_2_0= rulefindLogicalXor ) )
            	    // InternalMASL.g:5636:5: (lv_f_2_0= rulefindLogicalXor )
            	    {
            	    // InternalMASL.g:5636:5: (lv_f_2_0= rulefindLogicalXor )
            	    // InternalMASL.g:5637:6: lv_f_2_0= rulefindLogicalXor
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getFindLogicalOrAccess().getFFindLogicalXorParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_78);
            	    lv_f_2_0=rulefindLogicalXor();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getFindLogicalOrRule());
            	      						}
            	      						add(
            	      							current,
            	      							"f",
            	      							lv_f_2_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.findLogicalXor");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop79;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindLogicalOr"


    // $ANTLR start "entryRulefindLogicalXor"
    // InternalMASL.g:5659:1: entryRulefindLogicalXor returns [EObject current=null] : iv_rulefindLogicalXor= rulefindLogicalXor EOF ;
    public final EObject entryRulefindLogicalXor() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindLogicalXor = null;


        try {
            // InternalMASL.g:5659:55: (iv_rulefindLogicalXor= rulefindLogicalXor EOF )
            // InternalMASL.g:5660:2: iv_rulefindLogicalXor= rulefindLogicalXor EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindLogicalXorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindLogicalXor=rulefindLogicalXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindLogicalXor; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindLogicalXor"


    // $ANTLR start "rulefindLogicalXor"
    // InternalMASL.g:5666:1: rulefindLogicalXor returns [EObject current=null] : ( ( (lv_f_0_0= rulefindLogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_f_2_0= rulefindLogicalAnd ) ) )* ) ;
    public final EObject rulefindLogicalXor() throws RecognitionException {
        EObject current = null;

        Token this_XOR_1=null;
        EObject lv_f_0_0 = null;

        EObject lv_f_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5672:2: ( ( ( (lv_f_0_0= rulefindLogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_f_2_0= rulefindLogicalAnd ) ) )* ) )
            // InternalMASL.g:5673:2: ( ( (lv_f_0_0= rulefindLogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_f_2_0= rulefindLogicalAnd ) ) )* )
            {
            // InternalMASL.g:5673:2: ( ( (lv_f_0_0= rulefindLogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_f_2_0= rulefindLogicalAnd ) ) )* )
            // InternalMASL.g:5674:3: ( (lv_f_0_0= rulefindLogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_f_2_0= rulefindLogicalAnd ) ) )*
            {
            // InternalMASL.g:5674:3: ( (lv_f_0_0= rulefindLogicalAnd ) )
            // InternalMASL.g:5675:4: (lv_f_0_0= rulefindLogicalAnd )
            {
            // InternalMASL.g:5675:4: (lv_f_0_0= rulefindLogicalAnd )
            // InternalMASL.g:5676:5: lv_f_0_0= rulefindLogicalAnd
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindLogicalXorAccess().getFFindLogicalAndParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_80);
            lv_f_0_0=rulefindLogicalAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindLogicalXorRule());
              					}
              					add(
              						current,
              						"f",
              						lv_f_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.findLogicalAnd");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5693:3: (this_XOR_1= RULE_XOR ( (lv_f_2_0= rulefindLogicalAnd ) ) )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==RULE_XOR) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // InternalMASL.g:5694:4: this_XOR_1= RULE_XOR ( (lv_f_2_0= rulefindLogicalAnd ) )
            	    {
            	    this_XOR_1=(Token)match(input,RULE_XOR,FOLLOW_79); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_XOR_1, grammarAccess.getFindLogicalXorAccess().getXORTerminalRuleCall_1_0());
            	      			
            	    }
            	    // InternalMASL.g:5698:4: ( (lv_f_2_0= rulefindLogicalAnd ) )
            	    // InternalMASL.g:5699:5: (lv_f_2_0= rulefindLogicalAnd )
            	    {
            	    // InternalMASL.g:5699:5: (lv_f_2_0= rulefindLogicalAnd )
            	    // InternalMASL.g:5700:6: lv_f_2_0= rulefindLogicalAnd
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getFindLogicalXorAccess().getFFindLogicalAndParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_80);
            	    lv_f_2_0=rulefindLogicalAnd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getFindLogicalXorRule());
            	      						}
            	      						add(
            	      							current,
            	      							"f",
            	      							lv_f_2_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.findLogicalAnd");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindLogicalXor"


    // $ANTLR start "entryRulefindLogicalAnd"
    // InternalMASL.g:5722:1: entryRulefindLogicalAnd returns [EObject current=null] : iv_rulefindLogicalAnd= rulefindLogicalAnd EOF ;
    public final EObject entryRulefindLogicalAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindLogicalAnd = null;


        try {
            // InternalMASL.g:5722:55: (iv_rulefindLogicalAnd= rulefindLogicalAnd EOF )
            // InternalMASL.g:5723:2: iv_rulefindLogicalAnd= rulefindLogicalAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindLogicalAndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindLogicalAnd=rulefindLogicalAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindLogicalAnd; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindLogicalAnd"


    // $ANTLR start "rulefindLogicalAnd"
    // InternalMASL.g:5729:1: rulefindLogicalAnd returns [EObject current=null] : ( ( (lv_f_0_0= rulefindPrimary ) ) (this_AND_1= RULE_AND ( (lv_f_2_0= rulefindPrimary ) ) )* ) ;
    public final EObject rulefindLogicalAnd() throws RecognitionException {
        EObject current = null;

        Token this_AND_1=null;
        EObject lv_f_0_0 = null;

        EObject lv_f_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5735:2: ( ( ( (lv_f_0_0= rulefindPrimary ) ) (this_AND_1= RULE_AND ( (lv_f_2_0= rulefindPrimary ) ) )* ) )
            // InternalMASL.g:5736:2: ( ( (lv_f_0_0= rulefindPrimary ) ) (this_AND_1= RULE_AND ( (lv_f_2_0= rulefindPrimary ) ) )* )
            {
            // InternalMASL.g:5736:2: ( ( (lv_f_0_0= rulefindPrimary ) ) (this_AND_1= RULE_AND ( (lv_f_2_0= rulefindPrimary ) ) )* )
            // InternalMASL.g:5737:3: ( (lv_f_0_0= rulefindPrimary ) ) (this_AND_1= RULE_AND ( (lv_f_2_0= rulefindPrimary ) ) )*
            {
            // InternalMASL.g:5737:3: ( (lv_f_0_0= rulefindPrimary ) )
            // InternalMASL.g:5738:4: (lv_f_0_0= rulefindPrimary )
            {
            // InternalMASL.g:5738:4: (lv_f_0_0= rulefindPrimary )
            // InternalMASL.g:5739:5: lv_f_0_0= rulefindPrimary
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindLogicalAndAccess().getFFindPrimaryParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_81);
            lv_f_0_0=rulefindPrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindLogicalAndRule());
              					}
              					add(
              						current,
              						"f",
              						lv_f_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.findPrimary");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5756:3: (this_AND_1= RULE_AND ( (lv_f_2_0= rulefindPrimary ) ) )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( (LA81_0==RULE_AND) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // InternalMASL.g:5757:4: this_AND_1= RULE_AND ( (lv_f_2_0= rulefindPrimary ) )
            	    {
            	    this_AND_1=(Token)match(input,RULE_AND,FOLLOW_79); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_AND_1, grammarAccess.getFindLogicalAndAccess().getANDTerminalRuleCall_1_0());
            	      			
            	    }
            	    // InternalMASL.g:5761:4: ( (lv_f_2_0= rulefindPrimary ) )
            	    // InternalMASL.g:5762:5: (lv_f_2_0= rulefindPrimary )
            	    {
            	    // InternalMASL.g:5762:5: (lv_f_2_0= rulefindPrimary )
            	    // InternalMASL.g:5763:6: lv_f_2_0= rulefindPrimary
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getFindLogicalAndAccess().getFFindPrimaryParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_81);
            	    lv_f_2_0=rulefindPrimary();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getFindLogicalAndRule());
            	      						}
            	      						add(
            	      							current,
            	      							"f",
            	      							lv_f_2_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.findPrimary");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop81;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindLogicalAnd"


    // $ANTLR start "entryRulefindPrimary"
    // InternalMASL.g:5785:1: entryRulefindPrimary returns [EObject current=null] : iv_rulefindPrimary= rulefindPrimary EOF ;
    public final EObject entryRulefindPrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindPrimary = null;


        try {
            // InternalMASL.g:5785:52: (iv_rulefindPrimary= rulefindPrimary EOF )
            // InternalMASL.g:5786:2: iv_rulefindPrimary= rulefindPrimary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindPrimaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindPrimary=rulefindPrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindPrimary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindPrimary"


    // $ANTLR start "rulefindPrimary"
    // InternalMASL.g:5792:1: rulefindPrimary returns [EObject current=null] : (this_findComparison_0= rulefindComparison | this_findUnary_1= rulefindUnary ) ;
    public final EObject rulefindPrimary() throws RecognitionException {
        EObject current = null;

        EObject this_findComparison_0 = null;

        EObject this_findUnary_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:5798:2: ( (this_findComparison_0= rulefindComparison | this_findUnary_1= rulefindUnary ) )
            // InternalMASL.g:5799:2: (this_findComparison_0= rulefindComparison | this_findUnary_1= rulefindUnary )
            {
            // InternalMASL.g:5799:2: (this_findComparison_0= rulefindComparison | this_findUnary_1= rulefindUnary )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==RULE_IDENT) ) {
                alt82=1;
            }
            else if ( (LA82_0==RULE_LPAREN||LA82_0==RULE_NOT) ) {
                alt82=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // InternalMASL.g:5800:3: this_findComparison_0= rulefindComparison
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFindPrimaryAccess().getFindComparisonParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_findComparison_0=rulefindComparison();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_findComparison_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:5809:3: this_findUnary_1= rulefindUnary
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFindPrimaryAccess().getFindUnaryParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_findUnary_1=rulefindUnary();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_findUnary_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindPrimary"


    // $ANTLR start "entryRulefindUnary"
    // InternalMASL.g:5821:1: entryRulefindUnary returns [EObject current=null] : iv_rulefindUnary= rulefindUnary EOF ;
    public final EObject entryRulefindUnary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindUnary = null;


        try {
            // InternalMASL.g:5821:50: (iv_rulefindUnary= rulefindUnary EOF )
            // InternalMASL.g:5822:2: iv_rulefindUnary= rulefindUnary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindUnaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindUnary=rulefindUnary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindUnary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindUnary"


    // $ANTLR start "rulefindUnary"
    // InternalMASL.g:5828:1: rulefindUnary returns [EObject current=null] : ( (this_NOT_0= RULE_NOT this_findUnary_1= rulefindUnary ) | (this_LPAREN_2= RULE_LPAREN this_findCondition_3= rulefindCondition this_RPAREN_4= RULE_RPAREN ) ) ;
    public final EObject rulefindUnary() throws RecognitionException {
        EObject current = null;

        Token this_NOT_0=null;
        Token this_LPAREN_2=null;
        Token this_RPAREN_4=null;
        EObject this_findUnary_1 = null;

        EObject this_findCondition_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:5834:2: ( ( (this_NOT_0= RULE_NOT this_findUnary_1= rulefindUnary ) | (this_LPAREN_2= RULE_LPAREN this_findCondition_3= rulefindCondition this_RPAREN_4= RULE_RPAREN ) ) )
            // InternalMASL.g:5835:2: ( (this_NOT_0= RULE_NOT this_findUnary_1= rulefindUnary ) | (this_LPAREN_2= RULE_LPAREN this_findCondition_3= rulefindCondition this_RPAREN_4= RULE_RPAREN ) )
            {
            // InternalMASL.g:5835:2: ( (this_NOT_0= RULE_NOT this_findUnary_1= rulefindUnary ) | (this_LPAREN_2= RULE_LPAREN this_findCondition_3= rulefindCondition this_RPAREN_4= RULE_RPAREN ) )
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==RULE_NOT) ) {
                alt83=1;
            }
            else if ( (LA83_0==RULE_LPAREN) ) {
                alt83=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }
            switch (alt83) {
                case 1 :
                    // InternalMASL.g:5836:3: (this_NOT_0= RULE_NOT this_findUnary_1= rulefindUnary )
                    {
                    // InternalMASL.g:5836:3: (this_NOT_0= RULE_NOT this_findUnary_1= rulefindUnary )
                    // InternalMASL.g:5837:4: this_NOT_0= RULE_NOT this_findUnary_1= rulefindUnary
                    {
                    this_NOT_0=(Token)match(input,RULE_NOT,FOLLOW_79); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_NOT_0, grammarAccess.getFindUnaryAccess().getNOTTerminalRuleCall_0_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getFindUnaryAccess().getFindUnaryParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_findUnary_1=rulefindUnary();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_findUnary_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:5851:3: (this_LPAREN_2= RULE_LPAREN this_findCondition_3= rulefindCondition this_RPAREN_4= RULE_RPAREN )
                    {
                    // InternalMASL.g:5851:3: (this_LPAREN_2= RULE_LPAREN this_findCondition_3= rulefindCondition this_RPAREN_4= RULE_RPAREN )
                    // InternalMASL.g:5852:4: this_LPAREN_2= RULE_LPAREN this_findCondition_3= rulefindCondition this_RPAREN_4= RULE_RPAREN
                    {
                    this_LPAREN_2=(Token)match(input,RULE_LPAREN,FOLLOW_79); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LPAREN_2, grammarAccess.getFindUnaryAccess().getLPARENTerminalRuleCall_1_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getFindUnaryAccess().getFindConditionParserRuleCall_1_1());
                      			
                    }
                    pushFollow(FOLLOW_9);
                    this_findCondition_3=rulefindCondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_findCondition_3;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    this_RPAREN_4=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_RPAREN_4, grammarAccess.getFindUnaryAccess().getRPARENTerminalRuleCall_1_2());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindUnary"


    // $ANTLR start "entryRulefindComparison"
    // InternalMASL.g:5873:1: entryRulefindComparison returns [EObject current=null] : iv_rulefindComparison= rulefindComparison EOF ;
    public final EObject entryRulefindComparison() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindComparison = null;


        try {
            // InternalMASL.g:5873:55: (iv_rulefindComparison= rulefindComparison EOF )
            // InternalMASL.g:5874:2: iv_rulefindComparison= rulefindComparison EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindComparisonRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindComparison=rulefindComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindComparison; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindComparison"


    // $ANTLR start "rulefindComparison"
    // InternalMASL.g:5880:1: rulefindComparison returns [EObject current=null] : ( ( (lv_f_0_0= rulefindName ) ) (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL | this_LT_3= RULE_LT | this_GT_4= RULE_GT | this_LTE_5= RULE_LTE | this_GTE_6= RULE_GTE ) ( (lv_a_7_0= ruleadditiveExp ) ) ) ;
    public final EObject rulefindComparison() throws RecognitionException {
        EObject current = null;

        Token this_EQUAL_1=null;
        Token this_NOT_EQUAL_2=null;
        Token this_LT_3=null;
        Token this_GT_4=null;
        Token this_LTE_5=null;
        Token this_GTE_6=null;
        EObject lv_f_0_0 = null;

        EObject lv_a_7_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5886:2: ( ( ( (lv_f_0_0= rulefindName ) ) (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL | this_LT_3= RULE_LT | this_GT_4= RULE_GT | this_LTE_5= RULE_LTE | this_GTE_6= RULE_GTE ) ( (lv_a_7_0= ruleadditiveExp ) ) ) )
            // InternalMASL.g:5887:2: ( ( (lv_f_0_0= rulefindName ) ) (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL | this_LT_3= RULE_LT | this_GT_4= RULE_GT | this_LTE_5= RULE_LTE | this_GTE_6= RULE_GTE ) ( (lv_a_7_0= ruleadditiveExp ) ) )
            {
            // InternalMASL.g:5887:2: ( ( (lv_f_0_0= rulefindName ) ) (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL | this_LT_3= RULE_LT | this_GT_4= RULE_GT | this_LTE_5= RULE_LTE | this_GTE_6= RULE_GTE ) ( (lv_a_7_0= ruleadditiveExp ) ) )
            // InternalMASL.g:5888:3: ( (lv_f_0_0= rulefindName ) ) (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL | this_LT_3= RULE_LT | this_GT_4= RULE_GT | this_LTE_5= RULE_LTE | this_GTE_6= RULE_GTE ) ( (lv_a_7_0= ruleadditiveExp ) )
            {
            // InternalMASL.g:5888:3: ( (lv_f_0_0= rulefindName ) )
            // InternalMASL.g:5889:4: (lv_f_0_0= rulefindName )
            {
            // InternalMASL.g:5889:4: (lv_f_0_0= rulefindName )
            // InternalMASL.g:5890:5: lv_f_0_0= rulefindName
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindComparisonAccess().getFFindNameParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_82);
            lv_f_0_0=rulefindName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindComparisonRule());
              					}
              					set(
              						current,
              						"f",
              						lv_f_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.findName");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5907:3: (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL | this_LT_3= RULE_LT | this_GT_4= RULE_GT | this_LTE_5= RULE_LTE | this_GTE_6= RULE_GTE )
            int alt84=6;
            switch ( input.LA(1) ) {
            case RULE_EQUAL:
                {
                alt84=1;
                }
                break;
            case RULE_NOT_EQUAL:
                {
                alt84=2;
                }
                break;
            case RULE_LT:
                {
                alt84=3;
                }
                break;
            case RULE_GT:
                {
                alt84=4;
                }
                break;
            case RULE_LTE:
                {
                alt84=5;
                }
                break;
            case RULE_GTE:
                {
                alt84=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // InternalMASL.g:5908:4: this_EQUAL_1= RULE_EQUAL
                    {
                    this_EQUAL_1=(Token)match(input,RULE_EQUAL,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_EQUAL_1, grammarAccess.getFindComparisonAccess().getEQUALTerminalRuleCall_1_0());
                      			
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:5913:4: this_NOT_EQUAL_2= RULE_NOT_EQUAL
                    {
                    this_NOT_EQUAL_2=(Token)match(input,RULE_NOT_EQUAL,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_NOT_EQUAL_2, grammarAccess.getFindComparisonAccess().getNOT_EQUALTerminalRuleCall_1_1());
                      			
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:5918:4: this_LT_3= RULE_LT
                    {
                    this_LT_3=(Token)match(input,RULE_LT,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LT_3, grammarAccess.getFindComparisonAccess().getLTTerminalRuleCall_1_2());
                      			
                    }

                    }
                    break;
                case 4 :
                    // InternalMASL.g:5923:4: this_GT_4= RULE_GT
                    {
                    this_GT_4=(Token)match(input,RULE_GT,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_GT_4, grammarAccess.getFindComparisonAccess().getGTTerminalRuleCall_1_3());
                      			
                    }

                    }
                    break;
                case 5 :
                    // InternalMASL.g:5928:4: this_LTE_5= RULE_LTE
                    {
                    this_LTE_5=(Token)match(input,RULE_LTE,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_LTE_5, grammarAccess.getFindComparisonAccess().getLTETerminalRuleCall_1_4());
                      			
                    }

                    }
                    break;
                case 6 :
                    // InternalMASL.g:5933:4: this_GTE_6= RULE_GTE
                    {
                    this_GTE_6=(Token)match(input,RULE_GTE,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_GTE_6, grammarAccess.getFindComparisonAccess().getGTETerminalRuleCall_1_5());
                      			
                    }

                    }
                    break;

            }

            // InternalMASL.g:5938:3: ( (lv_a_7_0= ruleadditiveExp ) )
            // InternalMASL.g:5939:4: (lv_a_7_0= ruleadditiveExp )
            {
            // InternalMASL.g:5939:4: (lv_a_7_0= ruleadditiveExp )
            // InternalMASL.g:5940:5: lv_a_7_0= ruleadditiveExp
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindComparisonAccess().getAAdditiveExpParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_a_7_0=ruleadditiveExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindComparisonRule());
              					}
              					set(
              						current,
              						"a",
              						lv_a_7_0,
              						"org.xtuml.bp.ui.xtext.MASL.additiveExp");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindComparison"


    // $ANTLR start "entryRulefindName"
    // InternalMASL.g:5961:1: entryRulefindName returns [EObject current=null] : iv_rulefindName= rulefindName EOF ;
    public final EObject entryRulefindName() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindName = null;


        try {
            // InternalMASL.g:5961:49: (iv_rulefindName= rulefindName EOF )
            // InternalMASL.g:5962:2: iv_rulefindName= rulefindName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindNameRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindName=rulefindName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindName; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindName"


    // $ANTLR start "rulefindName"
    // InternalMASL.g:5968:1: rulefindName returns [EObject current=null] : ( ( (lv_i1_0_0= ruleidentifier ) ) ( (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) ) | (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET ) )* ) ;
    public final EObject rulefindName() throws RecognitionException {
        EObject current = null;

        Token this_DOT_1=null;
        Token this_LBRACKET_3=null;
        Token this_RBRACKET_5=null;
        AntlrDatatypeRuleToken lv_i1_0_0 = null;

        AntlrDatatypeRuleToken lv_i2_2_0 = null;

        EObject lv_e_4_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:5974:2: ( ( ( (lv_i1_0_0= ruleidentifier ) ) ( (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) ) | (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET ) )* ) )
            // InternalMASL.g:5975:2: ( ( (lv_i1_0_0= ruleidentifier ) ) ( (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) ) | (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET ) )* )
            {
            // InternalMASL.g:5975:2: ( ( (lv_i1_0_0= ruleidentifier ) ) ( (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) ) | (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET ) )* )
            // InternalMASL.g:5976:3: ( (lv_i1_0_0= ruleidentifier ) ) ( (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) ) | (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET ) )*
            {
            // InternalMASL.g:5976:3: ( (lv_i1_0_0= ruleidentifier ) )
            // InternalMASL.g:5977:4: (lv_i1_0_0= ruleidentifier )
            {
            // InternalMASL.g:5977:4: (lv_i1_0_0= ruleidentifier )
            // InternalMASL.g:5978:5: lv_i1_0_0= ruleidentifier
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindNameAccess().getI1IdentifierParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_83);
            lv_i1_0_0=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindNameRule());
              					}
              					set(
              						current,
              						"i1",
              						lv_i1_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.identifier");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:5995:3: ( (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) ) | (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET ) )*
            loop85:
            do {
                int alt85=3;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==RULE_DOT) ) {
                    alt85=1;
                }
                else if ( (LA85_0==RULE_LBRACKET) ) {
                    alt85=2;
                }


                switch (alt85) {
            	case 1 :
            	    // InternalMASL.g:5996:4: (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) )
            	    {
            	    // InternalMASL.g:5996:4: (this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) ) )
            	    // InternalMASL.g:5997:5: this_DOT_1= RULE_DOT ( (lv_i2_2_0= ruleidentifier ) )
            	    {
            	    this_DOT_1=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_DOT_1, grammarAccess.getFindNameAccess().getDOTTerminalRuleCall_1_0_0());
            	      				
            	    }
            	    // InternalMASL.g:6001:5: ( (lv_i2_2_0= ruleidentifier ) )
            	    // InternalMASL.g:6002:6: (lv_i2_2_0= ruleidentifier )
            	    {
            	    // InternalMASL.g:6002:6: (lv_i2_2_0= ruleidentifier )
            	    // InternalMASL.g:6003:7: lv_i2_2_0= ruleidentifier
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getFindNameAccess().getI2IdentifierParserRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_83);
            	    lv_i2_2_0=ruleidentifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getFindNameRule());
            	      							}
            	      							add(
            	      								current,
            	      								"i2",
            	      								lv_i2_2_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.identifier");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalMASL.g:6022:4: (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET )
            	    {
            	    // InternalMASL.g:6022:4: (this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET )
            	    // InternalMASL.g:6023:5: this_LBRACKET_3= RULE_LBRACKET ( (lv_e_4_0= ruleexpression ) ) this_RBRACKET_5= RULE_RBRACKET
            	    {
            	    this_LBRACKET_3=(Token)match(input,RULE_LBRACKET,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_LBRACKET_3, grammarAccess.getFindNameAccess().getLBRACKETTerminalRuleCall_1_1_0());
            	      				
            	    }
            	    // InternalMASL.g:6027:5: ( (lv_e_4_0= ruleexpression ) )
            	    // InternalMASL.g:6028:6: (lv_e_4_0= ruleexpression )
            	    {
            	    // InternalMASL.g:6028:6: (lv_e_4_0= ruleexpression )
            	    // InternalMASL.g:6029:7: lv_e_4_0= ruleexpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getFindNameAccess().getEExpressionParserRuleCall_1_1_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_49);
            	    lv_e_4_0=ruleexpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getFindNameRule());
            	      							}
            	      							add(
            	      								current,
            	      								"e",
            	      								lv_e_4_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.expression");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    this_RBRACKET_5=(Token)match(input,RULE_RBRACKET,FOLLOW_83); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_RBRACKET_5, grammarAccess.getFindNameAccess().getRBRACKETTerminalRuleCall_1_1_2());
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop85;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindName"


    // $ANTLR start "entryRuleexpression"
    // InternalMASL.g:6056:1: entryRuleexpression returns [EObject current=null] : iv_ruleexpression= ruleexpression EOF ;
    public final EObject entryRuleexpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleexpression = null;


        try {
            // InternalMASL.g:6056:51: (iv_ruleexpression= ruleexpression EOF )
            // InternalMASL.g:6057:2: iv_ruleexpression= ruleexpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleexpression=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleexpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleexpression"


    // $ANTLR start "ruleexpression"
    // InternalMASL.g:6063:1: ruleexpression returns [EObject current=null] : ( (lv_r_0_0= rulerangeExpression ) ) ;
    public final EObject ruleexpression() throws RecognitionException {
        EObject current = null;

        EObject lv_r_0_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6069:2: ( ( (lv_r_0_0= rulerangeExpression ) ) )
            // InternalMASL.g:6070:2: ( (lv_r_0_0= rulerangeExpression ) )
            {
            // InternalMASL.g:6070:2: ( (lv_r_0_0= rulerangeExpression ) )
            // InternalMASL.g:6071:3: (lv_r_0_0= rulerangeExpression )
            {
            // InternalMASL.g:6071:3: (lv_r_0_0= rulerangeExpression )
            // InternalMASL.g:6072:4: lv_r_0_0= rulerangeExpression
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getExpressionAccess().getRRangeExpressionParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_r_0_0=rulerangeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getExpressionRule());
              				}
              				set(
              					current,
              					"r",
              					lv_r_0_0,
              					"org.xtuml.bp.ui.xtext.MASL.rangeExpression");
              				afterParserOrEnumRuleCall();
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleexpression"


    // $ANTLR start "entryRulerangeExpression"
    // InternalMASL.g:6092:1: entryRulerangeExpression returns [EObject current=null] : iv_rulerangeExpression= rulerangeExpression EOF ;
    public final EObject entryRulerangeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulerangeExpression = null;


        try {
            // InternalMASL.g:6092:56: (iv_rulerangeExpression= rulerangeExpression EOF )
            // InternalMASL.g:6093:2: iv_rulerangeExpression= rulerangeExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRangeExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulerangeExpression=rulerangeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulerangeExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulerangeExpression"


    // $ANTLR start "rulerangeExpression"
    // InternalMASL.g:6099:1: rulerangeExpression returns [EObject current=null] : ( ( (lv_l_0_0= rulelogicalOr ) ) (this_RANGE_DOTS_1= RULE_RANGE_DOTS ( (lv_l_2_0= rulelogicalOr ) ) )? ) ;
    public final EObject rulerangeExpression() throws RecognitionException {
        EObject current = null;

        Token this_RANGE_DOTS_1=null;
        EObject lv_l_0_0 = null;

        EObject lv_l_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6105:2: ( ( ( (lv_l_0_0= rulelogicalOr ) ) (this_RANGE_DOTS_1= RULE_RANGE_DOTS ( (lv_l_2_0= rulelogicalOr ) ) )? ) )
            // InternalMASL.g:6106:2: ( ( (lv_l_0_0= rulelogicalOr ) ) (this_RANGE_DOTS_1= RULE_RANGE_DOTS ( (lv_l_2_0= rulelogicalOr ) ) )? )
            {
            // InternalMASL.g:6106:2: ( ( (lv_l_0_0= rulelogicalOr ) ) (this_RANGE_DOTS_1= RULE_RANGE_DOTS ( (lv_l_2_0= rulelogicalOr ) ) )? )
            // InternalMASL.g:6107:3: ( (lv_l_0_0= rulelogicalOr ) ) (this_RANGE_DOTS_1= RULE_RANGE_DOTS ( (lv_l_2_0= rulelogicalOr ) ) )?
            {
            // InternalMASL.g:6107:3: ( (lv_l_0_0= rulelogicalOr ) )
            // InternalMASL.g:6108:4: (lv_l_0_0= rulelogicalOr )
            {
            // InternalMASL.g:6108:4: (lv_l_0_0= rulelogicalOr )
            // InternalMASL.g:6109:5: lv_l_0_0= rulelogicalOr
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getRangeExpressionAccess().getLLogicalOrParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_84);
            lv_l_0_0=rulelogicalOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getRangeExpressionRule());
              					}
              					add(
              						current,
              						"l",
              						lv_l_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.logicalOr");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6126:3: (this_RANGE_DOTS_1= RULE_RANGE_DOTS ( (lv_l_2_0= rulelogicalOr ) ) )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==RULE_RANGE_DOTS) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalMASL.g:6127:4: this_RANGE_DOTS_1= RULE_RANGE_DOTS ( (lv_l_2_0= rulelogicalOr ) )
                    {
                    this_RANGE_DOTS_1=(Token)match(input,RULE_RANGE_DOTS,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_RANGE_DOTS_1, grammarAccess.getRangeExpressionAccess().getRANGE_DOTSTerminalRuleCall_1_0());
                      			
                    }
                    // InternalMASL.g:6131:4: ( (lv_l_2_0= rulelogicalOr ) )
                    // InternalMASL.g:6132:5: (lv_l_2_0= rulelogicalOr )
                    {
                    // InternalMASL.g:6132:5: (lv_l_2_0= rulelogicalOr )
                    // InternalMASL.g:6133:6: lv_l_2_0= rulelogicalOr
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getRangeExpressionAccess().getLLogicalOrParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_l_2_0=rulelogicalOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getRangeExpressionRule());
                      						}
                      						add(
                      							current,
                      							"l",
                      							lv_l_2_0,
                      							"org.xtuml.bp.ui.xtext.MASL.logicalOr");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulerangeExpression"


    // $ANTLR start "entryRulelogicalOr"
    // InternalMASL.g:6155:1: entryRulelogicalOr returns [EObject current=null] : iv_rulelogicalOr= rulelogicalOr EOF ;
    public final EObject entryRulelogicalOr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulelogicalOr = null;


        try {
            // InternalMASL.g:6155:50: (iv_rulelogicalOr= rulelogicalOr EOF )
            // InternalMASL.g:6156:2: iv_rulelogicalOr= rulelogicalOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLogicalOrRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulelogicalOr=rulelogicalOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulelogicalOr; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelogicalOr"


    // $ANTLR start "rulelogicalOr"
    // InternalMASL.g:6162:1: rulelogicalOr returns [EObject current=null] : ( ( (lv_l_0_0= rulelogicalXor ) ) (this_OR_1= RULE_OR ( (lv_l_2_0= rulelogicalXor ) ) )* ) ;
    public final EObject rulelogicalOr() throws RecognitionException {
        EObject current = null;

        Token this_OR_1=null;
        EObject lv_l_0_0 = null;

        EObject lv_l_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6168:2: ( ( ( (lv_l_0_0= rulelogicalXor ) ) (this_OR_1= RULE_OR ( (lv_l_2_0= rulelogicalXor ) ) )* ) )
            // InternalMASL.g:6169:2: ( ( (lv_l_0_0= rulelogicalXor ) ) (this_OR_1= RULE_OR ( (lv_l_2_0= rulelogicalXor ) ) )* )
            {
            // InternalMASL.g:6169:2: ( ( (lv_l_0_0= rulelogicalXor ) ) (this_OR_1= RULE_OR ( (lv_l_2_0= rulelogicalXor ) ) )* )
            // InternalMASL.g:6170:3: ( (lv_l_0_0= rulelogicalXor ) ) (this_OR_1= RULE_OR ( (lv_l_2_0= rulelogicalXor ) ) )*
            {
            // InternalMASL.g:6170:3: ( (lv_l_0_0= rulelogicalXor ) )
            // InternalMASL.g:6171:4: (lv_l_0_0= rulelogicalXor )
            {
            // InternalMASL.g:6171:4: (lv_l_0_0= rulelogicalXor )
            // InternalMASL.g:6172:5: lv_l_0_0= rulelogicalXor
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getLogicalOrAccess().getLLogicalXorParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_78);
            lv_l_0_0=rulelogicalXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getLogicalOrRule());
              					}
              					add(
              						current,
              						"l",
              						lv_l_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.logicalXor");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6189:3: (this_OR_1= RULE_OR ( (lv_l_2_0= rulelogicalXor ) ) )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==RULE_OR) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // InternalMASL.g:6190:4: this_OR_1= RULE_OR ( (lv_l_2_0= rulelogicalXor ) )
            	    {
            	    this_OR_1=(Token)match(input,RULE_OR,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_OR_1, grammarAccess.getLogicalOrAccess().getORTerminalRuleCall_1_0());
            	      			
            	    }
            	    // InternalMASL.g:6194:4: ( (lv_l_2_0= rulelogicalXor ) )
            	    // InternalMASL.g:6195:5: (lv_l_2_0= rulelogicalXor )
            	    {
            	    // InternalMASL.g:6195:5: (lv_l_2_0= rulelogicalXor )
            	    // InternalMASL.g:6196:6: lv_l_2_0= rulelogicalXor
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getLogicalOrAccess().getLLogicalXorParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_78);
            	    lv_l_2_0=rulelogicalXor();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getLogicalOrRule());
            	      						}
            	      						add(
            	      							current,
            	      							"l",
            	      							lv_l_2_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.logicalXor");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop87;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulelogicalOr"


    // $ANTLR start "entryRulelogicalXor"
    // InternalMASL.g:6218:1: entryRulelogicalXor returns [EObject current=null] : iv_rulelogicalXor= rulelogicalXor EOF ;
    public final EObject entryRulelogicalXor() throws RecognitionException {
        EObject current = null;

        EObject iv_rulelogicalXor = null;


        try {
            // InternalMASL.g:6218:51: (iv_rulelogicalXor= rulelogicalXor EOF )
            // InternalMASL.g:6219:2: iv_rulelogicalXor= rulelogicalXor EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLogicalXorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulelogicalXor=rulelogicalXor();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulelogicalXor; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelogicalXor"


    // $ANTLR start "rulelogicalXor"
    // InternalMASL.g:6225:1: rulelogicalXor returns [EObject current=null] : ( ( (lv_l_0_0= rulelogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_l_2_0= rulelogicalAnd ) ) )* ) ;
    public final EObject rulelogicalXor() throws RecognitionException {
        EObject current = null;

        Token this_XOR_1=null;
        EObject lv_l_0_0 = null;

        EObject lv_l_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6231:2: ( ( ( (lv_l_0_0= rulelogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_l_2_0= rulelogicalAnd ) ) )* ) )
            // InternalMASL.g:6232:2: ( ( (lv_l_0_0= rulelogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_l_2_0= rulelogicalAnd ) ) )* )
            {
            // InternalMASL.g:6232:2: ( ( (lv_l_0_0= rulelogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_l_2_0= rulelogicalAnd ) ) )* )
            // InternalMASL.g:6233:3: ( (lv_l_0_0= rulelogicalAnd ) ) (this_XOR_1= RULE_XOR ( (lv_l_2_0= rulelogicalAnd ) ) )*
            {
            // InternalMASL.g:6233:3: ( (lv_l_0_0= rulelogicalAnd ) )
            // InternalMASL.g:6234:4: (lv_l_0_0= rulelogicalAnd )
            {
            // InternalMASL.g:6234:4: (lv_l_0_0= rulelogicalAnd )
            // InternalMASL.g:6235:5: lv_l_0_0= rulelogicalAnd
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getLogicalXorAccess().getLLogicalAndParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_80);
            lv_l_0_0=rulelogicalAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getLogicalXorRule());
              					}
              					add(
              						current,
              						"l",
              						lv_l_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.logicalAnd");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6252:3: (this_XOR_1= RULE_XOR ( (lv_l_2_0= rulelogicalAnd ) ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( (LA88_0==RULE_XOR) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalMASL.g:6253:4: this_XOR_1= RULE_XOR ( (lv_l_2_0= rulelogicalAnd ) )
            	    {
            	    this_XOR_1=(Token)match(input,RULE_XOR,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_XOR_1, grammarAccess.getLogicalXorAccess().getXORTerminalRuleCall_1_0());
            	      			
            	    }
            	    // InternalMASL.g:6257:4: ( (lv_l_2_0= rulelogicalAnd ) )
            	    // InternalMASL.g:6258:5: (lv_l_2_0= rulelogicalAnd )
            	    {
            	    // InternalMASL.g:6258:5: (lv_l_2_0= rulelogicalAnd )
            	    // InternalMASL.g:6259:6: lv_l_2_0= rulelogicalAnd
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getLogicalXorAccess().getLLogicalAndParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_80);
            	    lv_l_2_0=rulelogicalAnd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getLogicalXorRule());
            	      						}
            	      						add(
            	      							current,
            	      							"l",
            	      							lv_l_2_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.logicalAnd");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulelogicalXor"


    // $ANTLR start "entryRulelogicalAnd"
    // InternalMASL.g:6281:1: entryRulelogicalAnd returns [EObject current=null] : iv_rulelogicalAnd= rulelogicalAnd EOF ;
    public final EObject entryRulelogicalAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_rulelogicalAnd = null;


        try {
            // InternalMASL.g:6281:51: (iv_rulelogicalAnd= rulelogicalAnd EOF )
            // InternalMASL.g:6282:2: iv_rulelogicalAnd= rulelogicalAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLogicalAndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulelogicalAnd=rulelogicalAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulelogicalAnd; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelogicalAnd"


    // $ANTLR start "rulelogicalAnd"
    // InternalMASL.g:6288:1: rulelogicalAnd returns [EObject current=null] : ( ( (lv_e_0_0= ruleequality ) ) (this_AND_1= RULE_AND ( (lv_e_2_0= ruleequality ) ) )* ) ;
    public final EObject rulelogicalAnd() throws RecognitionException {
        EObject current = null;

        Token this_AND_1=null;
        EObject lv_e_0_0 = null;

        EObject lv_e_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6294:2: ( ( ( (lv_e_0_0= ruleequality ) ) (this_AND_1= RULE_AND ( (lv_e_2_0= ruleequality ) ) )* ) )
            // InternalMASL.g:6295:2: ( ( (lv_e_0_0= ruleequality ) ) (this_AND_1= RULE_AND ( (lv_e_2_0= ruleequality ) ) )* )
            {
            // InternalMASL.g:6295:2: ( ( (lv_e_0_0= ruleequality ) ) (this_AND_1= RULE_AND ( (lv_e_2_0= ruleequality ) ) )* )
            // InternalMASL.g:6296:3: ( (lv_e_0_0= ruleequality ) ) (this_AND_1= RULE_AND ( (lv_e_2_0= ruleequality ) ) )*
            {
            // InternalMASL.g:6296:3: ( (lv_e_0_0= ruleequality ) )
            // InternalMASL.g:6297:4: (lv_e_0_0= ruleequality )
            {
            // InternalMASL.g:6297:4: (lv_e_0_0= ruleequality )
            // InternalMASL.g:6298:5: lv_e_0_0= ruleequality
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getLogicalAndAccess().getEEqualityParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_81);
            lv_e_0_0=ruleequality();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getLogicalAndRule());
              					}
              					add(
              						current,
              						"e",
              						lv_e_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.equality");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6315:3: (this_AND_1= RULE_AND ( (lv_e_2_0= ruleequality ) ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==RULE_AND) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalMASL.g:6316:4: this_AND_1= RULE_AND ( (lv_e_2_0= ruleequality ) )
            	    {
            	    this_AND_1=(Token)match(input,RULE_AND,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_AND_1, grammarAccess.getLogicalAndAccess().getANDTerminalRuleCall_1_0());
            	      			
            	    }
            	    // InternalMASL.g:6320:4: ( (lv_e_2_0= ruleequality ) )
            	    // InternalMASL.g:6321:5: (lv_e_2_0= ruleequality )
            	    {
            	    // InternalMASL.g:6321:5: (lv_e_2_0= ruleequality )
            	    // InternalMASL.g:6322:6: lv_e_2_0= ruleequality
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getLogicalAndAccess().getEEqualityParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_81);
            	    lv_e_2_0=ruleequality();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getLogicalAndRule());
            	      						}
            	      						add(
            	      							current,
            	      							"e",
            	      							lv_e_2_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.equality");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulelogicalAnd"


    // $ANTLR start "entryRuleequality"
    // InternalMASL.g:6344:1: entryRuleequality returns [EObject current=null] : iv_ruleequality= ruleequality EOF ;
    public final EObject entryRuleequality() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleequality = null;


        try {
            // InternalMASL.g:6344:49: (iv_ruleequality= ruleequality EOF )
            // InternalMASL.g:6345:2: iv_ruleequality= ruleequality EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEqualityRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleequality=ruleequality();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleequality; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleequality"


    // $ANTLR start "ruleequality"
    // InternalMASL.g:6351:1: ruleequality returns [EObject current=null] : ( ( (lv_r_0_0= rulerelationalExp ) ) ( (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL ) ( (lv_r_3_0= rulerelationalExp ) ) )* ) ;
    public final EObject ruleequality() throws RecognitionException {
        EObject current = null;

        Token this_EQUAL_1=null;
        Token this_NOT_EQUAL_2=null;
        EObject lv_r_0_0 = null;

        EObject lv_r_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6357:2: ( ( ( (lv_r_0_0= rulerelationalExp ) ) ( (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL ) ( (lv_r_3_0= rulerelationalExp ) ) )* ) )
            // InternalMASL.g:6358:2: ( ( (lv_r_0_0= rulerelationalExp ) ) ( (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL ) ( (lv_r_3_0= rulerelationalExp ) ) )* )
            {
            // InternalMASL.g:6358:2: ( ( (lv_r_0_0= rulerelationalExp ) ) ( (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL ) ( (lv_r_3_0= rulerelationalExp ) ) )* )
            // InternalMASL.g:6359:3: ( (lv_r_0_0= rulerelationalExp ) ) ( (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL ) ( (lv_r_3_0= rulerelationalExp ) ) )*
            {
            // InternalMASL.g:6359:3: ( (lv_r_0_0= rulerelationalExp ) )
            // InternalMASL.g:6360:4: (lv_r_0_0= rulerelationalExp )
            {
            // InternalMASL.g:6360:4: (lv_r_0_0= rulerelationalExp )
            // InternalMASL.g:6361:5: lv_r_0_0= rulerelationalExp
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getEqualityAccess().getRRelationalExpParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_85);
            lv_r_0_0=rulerelationalExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getEqualityRule());
              					}
              					add(
              						current,
              						"r",
              						lv_r_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.relationalExp");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6378:3: ( (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL ) ( (lv_r_3_0= rulerelationalExp ) ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( ((LA91_0>=RULE_EQUAL && LA91_0<=RULE_NOT_EQUAL)) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalMASL.g:6379:4: (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL ) ( (lv_r_3_0= rulerelationalExp ) )
            	    {
            	    // InternalMASL.g:6379:4: (this_EQUAL_1= RULE_EQUAL | this_NOT_EQUAL_2= RULE_NOT_EQUAL )
            	    int alt90=2;
            	    int LA90_0 = input.LA(1);

            	    if ( (LA90_0==RULE_EQUAL) ) {
            	        alt90=1;
            	    }
            	    else if ( (LA90_0==RULE_NOT_EQUAL) ) {
            	        alt90=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 90, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt90) {
            	        case 1 :
            	            // InternalMASL.g:6380:5: this_EQUAL_1= RULE_EQUAL
            	            {
            	            this_EQUAL_1=(Token)match(input,RULE_EQUAL,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_EQUAL_1, grammarAccess.getEqualityAccess().getEQUALTerminalRuleCall_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalMASL.g:6385:5: this_NOT_EQUAL_2= RULE_NOT_EQUAL
            	            {
            	            this_NOT_EQUAL_2=(Token)match(input,RULE_NOT_EQUAL,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_NOT_EQUAL_2, grammarAccess.getEqualityAccess().getNOT_EQUALTerminalRuleCall_1_0_1());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalMASL.g:6390:4: ( (lv_r_3_0= rulerelationalExp ) )
            	    // InternalMASL.g:6391:5: (lv_r_3_0= rulerelationalExp )
            	    {
            	    // InternalMASL.g:6391:5: (lv_r_3_0= rulerelationalExp )
            	    // InternalMASL.g:6392:6: lv_r_3_0= rulerelationalExp
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getEqualityAccess().getRRelationalExpParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_85);
            	    lv_r_3_0=rulerelationalExp();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getEqualityRule());
            	      						}
            	      						add(
            	      							current,
            	      							"r",
            	      							lv_r_3_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.relationalExp");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleequality"


    // $ANTLR start "entryRulerelationalExp"
    // InternalMASL.g:6414:1: entryRulerelationalExp returns [EObject current=null] : iv_rulerelationalExp= rulerelationalExp EOF ;
    public final EObject entryRulerelationalExp() throws RecognitionException {
        EObject current = null;

        EObject iv_rulerelationalExp = null;


        try {
            // InternalMASL.g:6414:54: (iv_rulerelationalExp= rulerelationalExp EOF )
            // InternalMASL.g:6415:2: iv_rulerelationalExp= rulerelationalExp EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getRelationalExpRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulerelationalExp=rulerelationalExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulerelationalExp; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulerelationalExp"


    // $ANTLR start "rulerelationalExp"
    // InternalMASL.g:6421:1: rulerelationalExp returns [EObject current=null] : ( ( (lv_a_0_0= ruleadditiveExp ) ) ( (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE ) ( (lv_a_5_0= ruleadditiveExp ) ) )* ) ;
    public final EObject rulerelationalExp() throws RecognitionException {
        EObject current = null;

        Token this_LT_1=null;
        Token this_GT_2=null;
        Token this_LTE_3=null;
        Token this_GTE_4=null;
        EObject lv_a_0_0 = null;

        EObject lv_a_5_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6427:2: ( ( ( (lv_a_0_0= ruleadditiveExp ) ) ( (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE ) ( (lv_a_5_0= ruleadditiveExp ) ) )* ) )
            // InternalMASL.g:6428:2: ( ( (lv_a_0_0= ruleadditiveExp ) ) ( (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE ) ( (lv_a_5_0= ruleadditiveExp ) ) )* )
            {
            // InternalMASL.g:6428:2: ( ( (lv_a_0_0= ruleadditiveExp ) ) ( (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE ) ( (lv_a_5_0= ruleadditiveExp ) ) )* )
            // InternalMASL.g:6429:3: ( (lv_a_0_0= ruleadditiveExp ) ) ( (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE ) ( (lv_a_5_0= ruleadditiveExp ) ) )*
            {
            // InternalMASL.g:6429:3: ( (lv_a_0_0= ruleadditiveExp ) )
            // InternalMASL.g:6430:4: (lv_a_0_0= ruleadditiveExp )
            {
            // InternalMASL.g:6430:4: (lv_a_0_0= ruleadditiveExp )
            // InternalMASL.g:6431:5: lv_a_0_0= ruleadditiveExp
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getRelationalExpAccess().getAAdditiveExpParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_86);
            lv_a_0_0=ruleadditiveExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getRelationalExpRule());
              					}
              					add(
              						current,
              						"a",
              						lv_a_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.additiveExp");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6448:3: ( (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE ) ( (lv_a_5_0= ruleadditiveExp ) ) )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( ((LA93_0>=RULE_LT && LA93_0<=RULE_GTE)) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // InternalMASL.g:6449:4: (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE ) ( (lv_a_5_0= ruleadditiveExp ) )
            	    {
            	    // InternalMASL.g:6449:4: (this_LT_1= RULE_LT | this_GT_2= RULE_GT | this_LTE_3= RULE_LTE | this_GTE_4= RULE_GTE )
            	    int alt92=4;
            	    switch ( input.LA(1) ) {
            	    case RULE_LT:
            	        {
            	        alt92=1;
            	        }
            	        break;
            	    case RULE_GT:
            	        {
            	        alt92=2;
            	        }
            	        break;
            	    case RULE_LTE:
            	        {
            	        alt92=3;
            	        }
            	        break;
            	    case RULE_GTE:
            	        {
            	        alt92=4;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 92, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt92) {
            	        case 1 :
            	            // InternalMASL.g:6450:5: this_LT_1= RULE_LT
            	            {
            	            this_LT_1=(Token)match(input,RULE_LT,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LT_1, grammarAccess.getRelationalExpAccess().getLTTerminalRuleCall_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalMASL.g:6455:5: this_GT_2= RULE_GT
            	            {
            	            this_GT_2=(Token)match(input,RULE_GT,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_GT_2, grammarAccess.getRelationalExpAccess().getGTTerminalRuleCall_1_0_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalMASL.g:6460:5: this_LTE_3= RULE_LTE
            	            {
            	            this_LTE_3=(Token)match(input,RULE_LTE,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_LTE_3, grammarAccess.getRelationalExpAccess().getLTETerminalRuleCall_1_0_2());
            	              				
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalMASL.g:6465:5: this_GTE_4= RULE_GTE
            	            {
            	            this_GTE_4=(Token)match(input,RULE_GTE,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_GTE_4, grammarAccess.getRelationalExpAccess().getGTETerminalRuleCall_1_0_3());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalMASL.g:6470:4: ( (lv_a_5_0= ruleadditiveExp ) )
            	    // InternalMASL.g:6471:5: (lv_a_5_0= ruleadditiveExp )
            	    {
            	    // InternalMASL.g:6471:5: (lv_a_5_0= ruleadditiveExp )
            	    // InternalMASL.g:6472:6: lv_a_5_0= ruleadditiveExp
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getRelationalExpAccess().getAAdditiveExpParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_86);
            	    lv_a_5_0=ruleadditiveExp();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getRelationalExpRule());
            	      						}
            	      						add(
            	      							current,
            	      							"a",
            	      							lv_a_5_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.additiveExp");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulerelationalExp"


    // $ANTLR start "entryRuleadditiveExp"
    // InternalMASL.g:6494:1: entryRuleadditiveExp returns [EObject current=null] : iv_ruleadditiveExp= ruleadditiveExp EOF ;
    public final EObject entryRuleadditiveExp() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleadditiveExp = null;


        try {
            // InternalMASL.g:6494:52: (iv_ruleadditiveExp= ruleadditiveExp EOF )
            // InternalMASL.g:6495:2: iv_ruleadditiveExp= ruleadditiveExp EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAdditiveExpRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleadditiveExp=ruleadditiveExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleadditiveExp; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleadditiveExp"


    // $ANTLR start "ruleadditiveExp"
    // InternalMASL.g:6501:1: ruleadditiveExp returns [EObject current=null] : ( ( (lv_m_0_0= rulemultExp ) ) ( (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN ) ( (lv_m_6_0= rulemultExp ) ) )* ) ;
    public final EObject ruleadditiveExp() throws RecognitionException {
        EObject current = null;

        Token this_PLUS_1=null;
        Token this_MINUS_2=null;
        Token this_CONCATENATE_3=null;
        Token this_UNION_4=null;
        Token this_NOT_IN_5=null;
        EObject lv_m_0_0 = null;

        EObject lv_m_6_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6507:2: ( ( ( (lv_m_0_0= rulemultExp ) ) ( (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN ) ( (lv_m_6_0= rulemultExp ) ) )* ) )
            // InternalMASL.g:6508:2: ( ( (lv_m_0_0= rulemultExp ) ) ( (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN ) ( (lv_m_6_0= rulemultExp ) ) )* )
            {
            // InternalMASL.g:6508:2: ( ( (lv_m_0_0= rulemultExp ) ) ( (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN ) ( (lv_m_6_0= rulemultExp ) ) )* )
            // InternalMASL.g:6509:3: ( (lv_m_0_0= rulemultExp ) ) ( (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN ) ( (lv_m_6_0= rulemultExp ) ) )*
            {
            // InternalMASL.g:6509:3: ( (lv_m_0_0= rulemultExp ) )
            // InternalMASL.g:6510:4: (lv_m_0_0= rulemultExp )
            {
            // InternalMASL.g:6510:4: (lv_m_0_0= rulemultExp )
            // InternalMASL.g:6511:5: lv_m_0_0= rulemultExp
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getAdditiveExpAccess().getMMultExpParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_87);
            lv_m_0_0=rulemultExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getAdditiveExpRule());
              					}
              					add(
              						current,
              						"m",
              						lv_m_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.multExp");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6528:3: ( (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN ) ( (lv_m_6_0= rulemultExp ) ) )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( ((LA95_0>=RULE_PLUS && LA95_0<=RULE_NOT_IN)) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // InternalMASL.g:6529:4: (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN ) ( (lv_m_6_0= rulemultExp ) )
            	    {
            	    // InternalMASL.g:6529:4: (this_PLUS_1= RULE_PLUS | this_MINUS_2= RULE_MINUS | this_CONCATENATE_3= RULE_CONCATENATE | this_UNION_4= RULE_UNION | this_NOT_IN_5= RULE_NOT_IN )
            	    int alt94=5;
            	    switch ( input.LA(1) ) {
            	    case RULE_PLUS:
            	        {
            	        alt94=1;
            	        }
            	        break;
            	    case RULE_MINUS:
            	        {
            	        alt94=2;
            	        }
            	        break;
            	    case RULE_CONCATENATE:
            	        {
            	        alt94=3;
            	        }
            	        break;
            	    case RULE_UNION:
            	        {
            	        alt94=4;
            	        }
            	        break;
            	    case RULE_NOT_IN:
            	        {
            	        alt94=5;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 94, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt94) {
            	        case 1 :
            	            // InternalMASL.g:6530:5: this_PLUS_1= RULE_PLUS
            	            {
            	            this_PLUS_1=(Token)match(input,RULE_PLUS,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_PLUS_1, grammarAccess.getAdditiveExpAccess().getPLUSTerminalRuleCall_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalMASL.g:6535:5: this_MINUS_2= RULE_MINUS
            	            {
            	            this_MINUS_2=(Token)match(input,RULE_MINUS,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_MINUS_2, grammarAccess.getAdditiveExpAccess().getMINUSTerminalRuleCall_1_0_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalMASL.g:6540:5: this_CONCATENATE_3= RULE_CONCATENATE
            	            {
            	            this_CONCATENATE_3=(Token)match(input,RULE_CONCATENATE,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_CONCATENATE_3, grammarAccess.getAdditiveExpAccess().getCONCATENATETerminalRuleCall_1_0_2());
            	              				
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalMASL.g:6545:5: this_UNION_4= RULE_UNION
            	            {
            	            this_UNION_4=(Token)match(input,RULE_UNION,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_UNION_4, grammarAccess.getAdditiveExpAccess().getUNIONTerminalRuleCall_1_0_3());
            	              				
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalMASL.g:6550:5: this_NOT_IN_5= RULE_NOT_IN
            	            {
            	            this_NOT_IN_5=(Token)match(input,RULE_NOT_IN,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_NOT_IN_5, grammarAccess.getAdditiveExpAccess().getNOT_INTerminalRuleCall_1_0_4());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalMASL.g:6555:4: ( (lv_m_6_0= rulemultExp ) )
            	    // InternalMASL.g:6556:5: (lv_m_6_0= rulemultExp )
            	    {
            	    // InternalMASL.g:6556:5: (lv_m_6_0= rulemultExp )
            	    // InternalMASL.g:6557:6: lv_m_6_0= rulemultExp
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getAdditiveExpAccess().getMMultExpParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_87);
            	    lv_m_6_0=rulemultExp();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getAdditiveExpRule());
            	      						}
            	      						add(
            	      							current,
            	      							"m",
            	      							lv_m_6_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.multExp");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop95;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleadditiveExp"


    // $ANTLR start "entryRulemultExp"
    // InternalMASL.g:6579:1: entryRulemultExp returns [EObject current=null] : iv_rulemultExp= rulemultExp EOF ;
    public final EObject entryRulemultExp() throws RecognitionException {
        EObject current = null;

        EObject iv_rulemultExp = null;


        try {
            // InternalMASL.g:6579:48: (iv_rulemultExp= rulemultExp EOF )
            // InternalMASL.g:6580:2: iv_rulemultExp= rulemultExp EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMultExpRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulemultExp=rulemultExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulemultExp; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulemultExp"


    // $ANTLR start "rulemultExp"
    // InternalMASL.g:6586:1: rulemultExp returns [EObject current=null] : ( ( (lv_u_0_0= ruleunaryExp ) ) ( (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION ) ( (lv_u_8_0= ruleunaryExp ) ) )* ) ;
    public final EObject rulemultExp() throws RecognitionException {
        EObject current = null;

        Token this_TIMES_1=null;
        Token this_DIVIDE_2=null;
        Token this_MOD_3=null;
        Token this_POWER_4=null;
        Token this_REM_5=null;
        Token this_INTERSECTION_6=null;
        Token this_DISUNION_7=null;
        EObject lv_u_0_0 = null;

        EObject lv_u_8_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6592:2: ( ( ( (lv_u_0_0= ruleunaryExp ) ) ( (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION ) ( (lv_u_8_0= ruleunaryExp ) ) )* ) )
            // InternalMASL.g:6593:2: ( ( (lv_u_0_0= ruleunaryExp ) ) ( (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION ) ( (lv_u_8_0= ruleunaryExp ) ) )* )
            {
            // InternalMASL.g:6593:2: ( ( (lv_u_0_0= ruleunaryExp ) ) ( (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION ) ( (lv_u_8_0= ruleunaryExp ) ) )* )
            // InternalMASL.g:6594:3: ( (lv_u_0_0= ruleunaryExp ) ) ( (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION ) ( (lv_u_8_0= ruleunaryExp ) ) )*
            {
            // InternalMASL.g:6594:3: ( (lv_u_0_0= ruleunaryExp ) )
            // InternalMASL.g:6595:4: (lv_u_0_0= ruleunaryExp )
            {
            // InternalMASL.g:6595:4: (lv_u_0_0= ruleunaryExp )
            // InternalMASL.g:6596:5: lv_u_0_0= ruleunaryExp
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getMultExpAccess().getUUnaryExpParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_88);
            lv_u_0_0=ruleunaryExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getMultExpRule());
              					}
              					add(
              						current,
              						"u",
              						lv_u_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.unaryExp");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6613:3: ( (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION ) ( (lv_u_8_0= ruleunaryExp ) ) )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( ((LA97_0>=RULE_TIMES && LA97_0<=RULE_DISUNION)) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // InternalMASL.g:6614:4: (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION ) ( (lv_u_8_0= ruleunaryExp ) )
            	    {
            	    // InternalMASL.g:6614:4: (this_TIMES_1= RULE_TIMES | this_DIVIDE_2= RULE_DIVIDE | this_MOD_3= RULE_MOD | this_POWER_4= RULE_POWER | this_REM_5= RULE_REM | this_INTERSECTION_6= RULE_INTERSECTION | this_DISUNION_7= RULE_DISUNION )
            	    int alt96=7;
            	    switch ( input.LA(1) ) {
            	    case RULE_TIMES:
            	        {
            	        alt96=1;
            	        }
            	        break;
            	    case RULE_DIVIDE:
            	        {
            	        alt96=2;
            	        }
            	        break;
            	    case RULE_MOD:
            	        {
            	        alt96=3;
            	        }
            	        break;
            	    case RULE_POWER:
            	        {
            	        alt96=4;
            	        }
            	        break;
            	    case RULE_REM:
            	        {
            	        alt96=5;
            	        }
            	        break;
            	    case RULE_INTERSECTION:
            	        {
            	        alt96=6;
            	        }
            	        break;
            	    case RULE_DISUNION:
            	        {
            	        alt96=7;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 96, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt96) {
            	        case 1 :
            	            // InternalMASL.g:6615:5: this_TIMES_1= RULE_TIMES
            	            {
            	            this_TIMES_1=(Token)match(input,RULE_TIMES,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_TIMES_1, grammarAccess.getMultExpAccess().getTIMESTerminalRuleCall_1_0_0());
            	              				
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalMASL.g:6620:5: this_DIVIDE_2= RULE_DIVIDE
            	            {
            	            this_DIVIDE_2=(Token)match(input,RULE_DIVIDE,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_DIVIDE_2, grammarAccess.getMultExpAccess().getDIVIDETerminalRuleCall_1_0_1());
            	              				
            	            }

            	            }
            	            break;
            	        case 3 :
            	            // InternalMASL.g:6625:5: this_MOD_3= RULE_MOD
            	            {
            	            this_MOD_3=(Token)match(input,RULE_MOD,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_MOD_3, grammarAccess.getMultExpAccess().getMODTerminalRuleCall_1_0_2());
            	              				
            	            }

            	            }
            	            break;
            	        case 4 :
            	            // InternalMASL.g:6630:5: this_POWER_4= RULE_POWER
            	            {
            	            this_POWER_4=(Token)match(input,RULE_POWER,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_POWER_4, grammarAccess.getMultExpAccess().getPOWERTerminalRuleCall_1_0_3());
            	              				
            	            }

            	            }
            	            break;
            	        case 5 :
            	            // InternalMASL.g:6635:5: this_REM_5= RULE_REM
            	            {
            	            this_REM_5=(Token)match(input,RULE_REM,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_REM_5, grammarAccess.getMultExpAccess().getREMTerminalRuleCall_1_0_4());
            	              				
            	            }

            	            }
            	            break;
            	        case 6 :
            	            // InternalMASL.g:6640:5: this_INTERSECTION_6= RULE_INTERSECTION
            	            {
            	            this_INTERSECTION_6=(Token)match(input,RULE_INTERSECTION,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_INTERSECTION_6, grammarAccess.getMultExpAccess().getINTERSECTIONTerminalRuleCall_1_0_5());
            	              				
            	            }

            	            }
            	            break;
            	        case 7 :
            	            // InternalMASL.g:6645:5: this_DISUNION_7= RULE_DISUNION
            	            {
            	            this_DISUNION_7=(Token)match(input,RULE_DISUNION,FOLLOW_8); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              					newLeafNode(this_DISUNION_7, grammarAccess.getMultExpAccess().getDISUNIONTerminalRuleCall_1_0_6());
            	              				
            	            }

            	            }
            	            break;

            	    }

            	    // InternalMASL.g:6650:4: ( (lv_u_8_0= ruleunaryExp ) )
            	    // InternalMASL.g:6651:5: (lv_u_8_0= ruleunaryExp )
            	    {
            	    // InternalMASL.g:6651:5: (lv_u_8_0= ruleunaryExp )
            	    // InternalMASL.g:6652:6: lv_u_8_0= ruleunaryExp
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getMultExpAccess().getUUnaryExpParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_88);
            	    lv_u_8_0=ruleunaryExp();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getMultExpRule());
            	      						}
            	      						add(
            	      							current,
            	      							"u",
            	      							lv_u_8_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.unaryExp");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop97;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulemultExp"


    // $ANTLR start "entryRuleunaryExp"
    // InternalMASL.g:6674:1: entryRuleunaryExp returns [EObject current=null] : iv_ruleunaryExp= ruleunaryExp EOF ;
    public final EObject entryRuleunaryExp() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleunaryExp = null;


        try {
            // InternalMASL.g:6674:49: (iv_ruleunaryExp= ruleunaryExp EOF )
            // InternalMASL.g:6675:2: iv_ruleunaryExp= ruleunaryExp EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnaryExpRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleunaryExp=ruleunaryExp();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleunaryExp; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleunaryExp"


    // $ANTLR start "ruleunaryExp"
    // InternalMASL.g:6681:1: ruleunaryExp returns [EObject current=null] : ( ( ruleunaryOperator this_unaryExp_1= ruleunaryExp ) | this_linkExpression_2= rulelinkExpression ) ;
    public final EObject ruleunaryExp() throws RecognitionException {
        EObject current = null;

        EObject this_unaryExp_1 = null;

        EObject this_linkExpression_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:6687:2: ( ( ( ruleunaryOperator this_unaryExp_1= ruleunaryExp ) | this_linkExpression_2= rulelinkExpression ) )
            // InternalMASL.g:6688:2: ( ( ruleunaryOperator this_unaryExp_1= ruleunaryExp ) | this_linkExpression_2= rulelinkExpression )
            {
            // InternalMASL.g:6688:2: ( ( ruleunaryOperator this_unaryExp_1= ruleunaryExp ) | this_linkExpression_2= rulelinkExpression )
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==RULE_NOT||(LA98_0>=RULE_PLUS && LA98_0<=RULE_MINUS)||LA98_0==RULE_ABS) ) {
                alt98=1;
            }
            else if ( (LA98_0==RULE_INSTANCE||LA98_0==RULE_ANONYMOUS||LA98_0==RULE_LPAREN||(LA98_0>=RULE_SEQUENCE && LA98_0<=RULE_DICTIONARY)||LA98_0==RULE_NULL||(LA98_0>=RULE_LINK && LA98_0<=RULE_UNLINK)||LA98_0==RULE_CREATE||(LA98_0>=RULE_FIND && LA98_0<=RULE_IDENT)) ) {
                alt98=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }
            switch (alt98) {
                case 1 :
                    // InternalMASL.g:6689:3: ( ruleunaryOperator this_unaryExp_1= ruleunaryExp )
                    {
                    // InternalMASL.g:6689:3: ( ruleunaryOperator this_unaryExp_1= ruleunaryExp )
                    // InternalMASL.g:6690:4: ruleunaryOperator this_unaryExp_1= ruleunaryExp
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUnaryExpAccess().getUnaryOperatorParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_8);
                    ruleunaryOperator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getUnaryExpAccess().getUnaryExpParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_unaryExp_1=ruleunaryExp();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_unaryExp_1;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:6707:3: this_linkExpression_2= rulelinkExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getUnaryExpAccess().getLinkExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_linkExpression_2=rulelinkExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_linkExpression_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleunaryExp"


    // $ANTLR start "entryRuleunaryOperator"
    // InternalMASL.g:6719:1: entryRuleunaryOperator returns [String current=null] : iv_ruleunaryOperator= ruleunaryOperator EOF ;
    public final String entryRuleunaryOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleunaryOperator = null;


        try {
            // InternalMASL.g:6719:53: (iv_ruleunaryOperator= ruleunaryOperator EOF )
            // InternalMASL.g:6720:2: iv_ruleunaryOperator= ruleunaryOperator EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnaryOperatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleunaryOperator=ruleunaryOperator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleunaryOperator.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleunaryOperator"


    // $ANTLR start "ruleunaryOperator"
    // InternalMASL.g:6726:1: ruleunaryOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_MINUS_0= RULE_MINUS | this_PLUS_1= RULE_PLUS | this_NOT_2= RULE_NOT | this_ABS_3= RULE_ABS ) ;
    public final AntlrDatatypeRuleToken ruleunaryOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_MINUS_0=null;
        Token this_PLUS_1=null;
        Token this_NOT_2=null;
        Token this_ABS_3=null;


        	enterRule();

        try {
            // InternalMASL.g:6732:2: ( (this_MINUS_0= RULE_MINUS | this_PLUS_1= RULE_PLUS | this_NOT_2= RULE_NOT | this_ABS_3= RULE_ABS ) )
            // InternalMASL.g:6733:2: (this_MINUS_0= RULE_MINUS | this_PLUS_1= RULE_PLUS | this_NOT_2= RULE_NOT | this_ABS_3= RULE_ABS )
            {
            // InternalMASL.g:6733:2: (this_MINUS_0= RULE_MINUS | this_PLUS_1= RULE_PLUS | this_NOT_2= RULE_NOT | this_ABS_3= RULE_ABS )
            int alt99=4;
            switch ( input.LA(1) ) {
            case RULE_MINUS:
                {
                alt99=1;
                }
                break;
            case RULE_PLUS:
                {
                alt99=2;
                }
                break;
            case RULE_NOT:
                {
                alt99=3;
                }
                break;
            case RULE_ABS:
                {
                alt99=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }

            switch (alt99) {
                case 1 :
                    // InternalMASL.g:6734:3: this_MINUS_0= RULE_MINUS
                    {
                    this_MINUS_0=(Token)match(input,RULE_MINUS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_MINUS_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_MINUS_0, grammarAccess.getUnaryOperatorAccess().getMINUSTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:6742:3: this_PLUS_1= RULE_PLUS
                    {
                    this_PLUS_1=(Token)match(input,RULE_PLUS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_PLUS_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_PLUS_1, grammarAccess.getUnaryOperatorAccess().getPLUSTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:6750:3: this_NOT_2= RULE_NOT
                    {
                    this_NOT_2=(Token)match(input,RULE_NOT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_NOT_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_NOT_2, grammarAccess.getUnaryOperatorAccess().getNOTTerminalRuleCall_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalMASL.g:6758:3: this_ABS_3= RULE_ABS
                    {
                    this_ABS_3=(Token)match(input,RULE_ABS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ABS_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ABS_3, grammarAccess.getUnaryOperatorAccess().getABSTerminalRuleCall_3());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleunaryOperator"


    // $ANTLR start "entryRulelinkExpression"
    // InternalMASL.g:6769:1: entryRulelinkExpression returns [EObject current=null] : iv_rulelinkExpression= rulelinkExpression EOF ;
    public final EObject entryRulelinkExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulelinkExpression = null;


        try {
            // InternalMASL.g:6769:55: (iv_rulelinkExpression= rulelinkExpression EOF )
            // InternalMASL.g:6770:2: iv_rulelinkExpression= rulelinkExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLinkExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulelinkExpression=rulelinkExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulelinkExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelinkExpression"


    // $ANTLR start "rulelinkExpression"
    // InternalMASL.g:6776:1: rulelinkExpression returns [EObject current=null] : ( ( (lv_n1_0_0= rulenavigateExpression ) ) | ( rulelinkType ( (lv_n2_2_0= rulenavigateExpression ) ) ( (lv_r_3_0= rulerelationshipSpec ) ) ( (lv_n3_4_0= rulenavigateExpression ) )? ) ) ;
    public final EObject rulelinkExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_n1_0_0 = null;

        EObject lv_n2_2_0 = null;

        EObject lv_r_3_0 = null;

        EObject lv_n3_4_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6782:2: ( ( ( (lv_n1_0_0= rulenavigateExpression ) ) | ( rulelinkType ( (lv_n2_2_0= rulenavigateExpression ) ) ( (lv_r_3_0= rulerelationshipSpec ) ) ( (lv_n3_4_0= rulenavigateExpression ) )? ) ) )
            // InternalMASL.g:6783:2: ( ( (lv_n1_0_0= rulenavigateExpression ) ) | ( rulelinkType ( (lv_n2_2_0= rulenavigateExpression ) ) ( (lv_r_3_0= rulerelationshipSpec ) ) ( (lv_n3_4_0= rulenavigateExpression ) )? ) )
            {
            // InternalMASL.g:6783:2: ( ( (lv_n1_0_0= rulenavigateExpression ) ) | ( rulelinkType ( (lv_n2_2_0= rulenavigateExpression ) ) ( (lv_r_3_0= rulerelationshipSpec ) ) ( (lv_n3_4_0= rulenavigateExpression ) )? ) )
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==RULE_INSTANCE||LA101_0==RULE_ANONYMOUS||LA101_0==RULE_LPAREN||(LA101_0>=RULE_SEQUENCE && LA101_0<=RULE_DICTIONARY)||LA101_0==RULE_NULL||LA101_0==RULE_CREATE||(LA101_0>=RULE_FIND && LA101_0<=RULE_IDENT)) ) {
                alt101=1;
            }
            else if ( ((LA101_0>=RULE_LINK && LA101_0<=RULE_UNLINK)) ) {
                alt101=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }
            switch (alt101) {
                case 1 :
                    // InternalMASL.g:6784:3: ( (lv_n1_0_0= rulenavigateExpression ) )
                    {
                    // InternalMASL.g:6784:3: ( (lv_n1_0_0= rulenavigateExpression ) )
                    // InternalMASL.g:6785:4: (lv_n1_0_0= rulenavigateExpression )
                    {
                    // InternalMASL.g:6785:4: (lv_n1_0_0= rulenavigateExpression )
                    // InternalMASL.g:6786:5: lv_n1_0_0= rulenavigateExpression
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getLinkExpressionAccess().getN1NavigateExpressionParserRuleCall_0_0());
                      				
                    }
                    pushFollow(FOLLOW_2);
                    lv_n1_0_0=rulenavigateExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getLinkExpressionRule());
                      					}
                      					set(
                      						current,
                      						"n1",
                      						lv_n1_0_0,
                      						"org.xtuml.bp.ui.xtext.MASL.navigateExpression");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:6804:3: ( rulelinkType ( (lv_n2_2_0= rulenavigateExpression ) ) ( (lv_r_3_0= rulerelationshipSpec ) ) ( (lv_n3_4_0= rulenavigateExpression ) )? )
                    {
                    // InternalMASL.g:6804:3: ( rulelinkType ( (lv_n2_2_0= rulenavigateExpression ) ) ( (lv_r_3_0= rulerelationshipSpec ) ) ( (lv_n3_4_0= rulenavigateExpression ) )? )
                    // InternalMASL.g:6805:4: rulelinkType ( (lv_n2_2_0= rulenavigateExpression ) ) ( (lv_r_3_0= rulerelationshipSpec ) ) ( (lv_n3_4_0= rulenavigateExpression ) )?
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getLinkExpressionAccess().getLinkTypeParserRuleCall_1_0());
                      			
                    }
                    pushFollow(FOLLOW_54);
                    rulelinkType();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalMASL.g:6812:4: ( (lv_n2_2_0= rulenavigateExpression ) )
                    // InternalMASL.g:6813:5: (lv_n2_2_0= rulenavigateExpression )
                    {
                    // InternalMASL.g:6813:5: (lv_n2_2_0= rulenavigateExpression )
                    // InternalMASL.g:6814:6: lv_n2_2_0= rulenavigateExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLinkExpressionAccess().getN2NavigateExpressionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_20);
                    lv_n2_2_0=rulenavigateExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLinkExpressionRule());
                      						}
                      						set(
                      							current,
                      							"n2",
                      							lv_n2_2_0,
                      							"org.xtuml.bp.ui.xtext.MASL.navigateExpression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalMASL.g:6831:4: ( (lv_r_3_0= rulerelationshipSpec ) )
                    // InternalMASL.g:6832:5: (lv_r_3_0= rulerelationshipSpec )
                    {
                    // InternalMASL.g:6832:5: (lv_r_3_0= rulerelationshipSpec )
                    // InternalMASL.g:6833:6: lv_r_3_0= rulerelationshipSpec
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getLinkExpressionAccess().getRRelationshipSpecParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_55);
                    lv_r_3_0=rulerelationshipSpec();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getLinkExpressionRule());
                      						}
                      						set(
                      							current,
                      							"r",
                      							lv_r_3_0,
                      							"org.xtuml.bp.ui.xtext.MASL.relationshipSpec");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalMASL.g:6850:4: ( (lv_n3_4_0= rulenavigateExpression ) )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==RULE_INSTANCE||LA100_0==RULE_ANONYMOUS||LA100_0==RULE_LPAREN||(LA100_0>=RULE_SEQUENCE && LA100_0<=RULE_DICTIONARY)||LA100_0==RULE_NULL||LA100_0==RULE_CREATE||(LA100_0>=RULE_FIND && LA100_0<=RULE_IDENT)) ) {
                        alt100=1;
                    }
                    switch (alt100) {
                        case 1 :
                            // InternalMASL.g:6851:5: (lv_n3_4_0= rulenavigateExpression )
                            {
                            // InternalMASL.g:6851:5: (lv_n3_4_0= rulenavigateExpression )
                            // InternalMASL.g:6852:6: lv_n3_4_0= rulenavigateExpression
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getLinkExpressionAccess().getN3NavigateExpressionParserRuleCall_1_3_0());
                              					
                            }
                            pushFollow(FOLLOW_2);
                            lv_n3_4_0=rulenavigateExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getLinkExpressionRule());
                              						}
                              						set(
                              							current,
                              							"n3",
                              							lv_n3_4_0,
                              							"org.xtuml.bp.ui.xtext.MASL.navigateExpression");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulelinkExpression"


    // $ANTLR start "entryRulenavigateExpression"
    // InternalMASL.g:6874:1: entryRulenavigateExpression returns [EObject current=null] : iv_rulenavigateExpression= rulenavigateExpression EOF ;
    public final EObject entryRulenavigateExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulenavigateExpression = null;


        try {
            // InternalMASL.g:6874:59: (iv_rulenavigateExpression= rulenavigateExpression EOF )
            // InternalMASL.g:6875:2: iv_rulenavigateExpression= rulenavigateExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNavigateExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulenavigateExpression=rulenavigateExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulenavigateExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulenavigateExpression"


    // $ANTLR start "rulenavigateExpression"
    // InternalMASL.g:6881:1: rulenavigateExpression returns [EObject current=null] : ( ( (lv_e_0_0= ruleextendedExpression ) ) ( (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? ) | (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) ) | (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder ) | (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder ) )* ) ;
    public final EObject rulenavigateExpression() throws RecognitionException {
        EObject current = null;

        Token this_NAVIGATE_1=null;
        Token this_WITH_4=null;
        Token this_NAVIGATE_6=null;
        Token this_ORDERED_BY_8=null;
        Token this_REVERSE_ORDERED_BY_10=null;
        EObject lv_e_0_0 = null;

        EObject lv_r_2_0 = null;

        EObject lv_w_3_0 = null;

        EObject lv_e_5_0 = null;

        EObject lv_r_7_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:6887:2: ( ( ( (lv_e_0_0= ruleextendedExpression ) ) ( (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? ) | (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) ) | (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder ) | (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder ) )* ) )
            // InternalMASL.g:6888:2: ( ( (lv_e_0_0= ruleextendedExpression ) ) ( (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? ) | (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) ) | (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder ) | (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder ) )* )
            {
            // InternalMASL.g:6888:2: ( ( (lv_e_0_0= ruleextendedExpression ) ) ( (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? ) | (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) ) | (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder ) | (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder ) )* )
            // InternalMASL.g:6889:3: ( (lv_e_0_0= ruleextendedExpression ) ) ( (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? ) | (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) ) | (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder ) | (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder ) )*
            {
            // InternalMASL.g:6889:3: ( (lv_e_0_0= ruleextendedExpression ) )
            // InternalMASL.g:6890:4: (lv_e_0_0= ruleextendedExpression )
            {
            // InternalMASL.g:6890:4: (lv_e_0_0= ruleextendedExpression )
            // InternalMASL.g:6891:5: lv_e_0_0= ruleextendedExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getNavigateExpressionAccess().getEExtendedExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_89);
            lv_e_0_0=ruleextendedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getNavigateExpressionRule());
              					}
              					add(
              						current,
              						"e",
              						lv_e_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.extendedExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:6908:3: ( (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? ) | (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) ) | (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder ) | (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder ) )*
            loop103:
            do {
                int alt103=5;
                switch ( input.LA(1) ) {
                case RULE_NAVIGATE:
                    {
                    alt103=1;
                    }
                    break;
                case RULE_WITH:
                    {
                    alt103=2;
                    }
                    break;
                case RULE_ORDERED_BY:
                    {
                    alt103=3;
                    }
                    break;
                case RULE_REVERSE_ORDERED_BY:
                    {
                    alt103=4;
                    }
                    break;

                }

                switch (alt103) {
            	case 1 :
            	    // InternalMASL.g:6909:4: (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? )
            	    {
            	    // InternalMASL.g:6909:4: (this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )? )
            	    // InternalMASL.g:6910:5: this_NAVIGATE_1= RULE_NAVIGATE ( (lv_r_2_0= rulerelationshipSpec ) ) ( (lv_w_3_0= rulewhereClause ) )?
            	    {
            	    this_NAVIGATE_1=(Token)match(input,RULE_NAVIGATE,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_NAVIGATE_1, grammarAccess.getNavigateExpressionAccess().getNAVIGATETerminalRuleCall_1_0_0());
            	      				
            	    }
            	    // InternalMASL.g:6914:5: ( (lv_r_2_0= rulerelationshipSpec ) )
            	    // InternalMASL.g:6915:6: (lv_r_2_0= rulerelationshipSpec )
            	    {
            	    // InternalMASL.g:6915:6: (lv_r_2_0= rulerelationshipSpec )
            	    // InternalMASL.g:6916:7: lv_r_2_0= rulerelationshipSpec
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getNavigateExpressionAccess().getRRelationshipSpecParserRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_90);
            	    lv_r_2_0=rulerelationshipSpec();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getNavigateExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"r",
            	      								lv_r_2_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.relationshipSpec");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    // InternalMASL.g:6933:5: ( (lv_w_3_0= rulewhereClause ) )?
            	    int alt102=2;
            	    int LA102_0 = input.LA(1);

            	    if ( (LA102_0==RULE_LPAREN) ) {
            	        alt102=1;
            	    }
            	    switch (alt102) {
            	        case 1 :
            	            // InternalMASL.g:6934:6: (lv_w_3_0= rulewhereClause )
            	            {
            	            // InternalMASL.g:6934:6: (lv_w_3_0= rulewhereClause )
            	            // InternalMASL.g:6935:7: lv_w_3_0= rulewhereClause
            	            {
            	            if ( state.backtracking==0 ) {

            	              							newCompositeNode(grammarAccess.getNavigateExpressionAccess().getWWhereClauseParserRuleCall_1_0_2_0());
            	              						
            	            }
            	            pushFollow(FOLLOW_89);
            	            lv_w_3_0=rulewhereClause();

            	            state._fsp--;
            	            if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              							if (current==null) {
            	              								current = createModelElementForParent(grammarAccess.getNavigateExpressionRule());
            	              							}
            	              							add(
            	              								current,
            	              								"w",
            	              								lv_w_3_0,
            	              								"org.xtuml.bp.ui.xtext.MASL.whereClause");
            	              							afterParserOrEnumRuleCall();
            	              						
            	            }

            	            }


            	            }
            	            break;

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalMASL.g:6954:4: (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) )
            	    {
            	    // InternalMASL.g:6954:4: (this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) ) )
            	    // InternalMASL.g:6955:5: this_WITH_4= RULE_WITH ( (lv_e_5_0= ruleextendedExpression ) ) this_NAVIGATE_6= RULE_NAVIGATE ( (lv_r_7_0= rulerelationshipSpec ) )
            	    {
            	    this_WITH_4=(Token)match(input,RULE_WITH,FOLLOW_54); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_WITH_4, grammarAccess.getNavigateExpressionAccess().getWITHTerminalRuleCall_1_1_0());
            	      				
            	    }
            	    // InternalMASL.g:6959:5: ( (lv_e_5_0= ruleextendedExpression ) )
            	    // InternalMASL.g:6960:6: (lv_e_5_0= ruleextendedExpression )
            	    {
            	    // InternalMASL.g:6960:6: (lv_e_5_0= ruleextendedExpression )
            	    // InternalMASL.g:6961:7: lv_e_5_0= ruleextendedExpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getNavigateExpressionAccess().getEExtendedExpressionParserRuleCall_1_1_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_91);
            	    lv_e_5_0=ruleextendedExpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getNavigateExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"e",
            	      								lv_e_5_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.extendedExpression");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    this_NAVIGATE_6=(Token)match(input,RULE_NAVIGATE,FOLLOW_20); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_NAVIGATE_6, grammarAccess.getNavigateExpressionAccess().getNAVIGATETerminalRuleCall_1_1_2());
            	      				
            	    }
            	    // InternalMASL.g:6982:5: ( (lv_r_7_0= rulerelationshipSpec ) )
            	    // InternalMASL.g:6983:6: (lv_r_7_0= rulerelationshipSpec )
            	    {
            	    // InternalMASL.g:6983:6: (lv_r_7_0= rulerelationshipSpec )
            	    // InternalMASL.g:6984:7: lv_r_7_0= rulerelationshipSpec
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getNavigateExpressionAccess().getRRelationshipSpecParserRuleCall_1_1_3_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_89);
            	    lv_r_7_0=rulerelationshipSpec();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getNavigateExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"r",
            	      								lv_r_7_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.relationshipSpec");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalMASL.g:7003:4: (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder )
            	    {
            	    // InternalMASL.g:7003:4: (this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder )
            	    // InternalMASL.g:7004:5: this_ORDERED_BY_8= RULE_ORDERED_BY rulesortOrder
            	    {
            	    this_ORDERED_BY_8=(Token)match(input,RULE_ORDERED_BY,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_ORDERED_BY_8, grammarAccess.getNavigateExpressionAccess().getORDERED_BYTerminalRuleCall_1_2_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getNavigateExpressionAccess().getSortOrderParserRuleCall_1_2_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_89);
            	    rulesortOrder();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalMASL.g:7017:4: (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder )
            	    {
            	    // InternalMASL.g:7017:4: (this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder )
            	    // InternalMASL.g:7018:5: this_REVERSE_ORDERED_BY_10= RULE_REVERSE_ORDERED_BY rulesortOrder
            	    {
            	    this_REVERSE_ORDERED_BY_10=(Token)match(input,RULE_REVERSE_ORDERED_BY,FOLLOW_7); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_REVERSE_ORDERED_BY_10, grammarAccess.getNavigateExpressionAccess().getREVERSE_ORDERED_BYTerminalRuleCall_1_3_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getNavigateExpressionAccess().getSortOrderParserRuleCall_1_3_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_89);
            	    rulesortOrder();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop103;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulenavigateExpression"


    // $ANTLR start "entryRuleextendedExpression"
    // InternalMASL.g:7035:1: entryRuleextendedExpression returns [EObject current=null] : iv_ruleextendedExpression= ruleextendedExpression EOF ;
    public final EObject entryRuleextendedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleextendedExpression = null;


        try {
            // InternalMASL.g:7035:59: (iv_ruleextendedExpression= ruleextendedExpression EOF )
            // InternalMASL.g:7036:2: iv_ruleextendedExpression= ruleextendedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExtendedExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleextendedExpression=ruleextendedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleextendedExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleextendedExpression"


    // $ANTLR start "ruleextendedExpression"
    // InternalMASL.g:7042:1: ruleextendedExpression returns [EObject current=null] : (this_postfixExpression_0= rulepostfixExpression | this_createExpression_1= rulecreateExpression | this_findExpression_2= rulefindExpression ) ;
    public final EObject ruleextendedExpression() throws RecognitionException {
        EObject current = null;

        EObject this_postfixExpression_0 = null;

        EObject this_createExpression_1 = null;

        EObject this_findExpression_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:7048:2: ( (this_postfixExpression_0= rulepostfixExpression | this_createExpression_1= rulecreateExpression | this_findExpression_2= rulefindExpression ) )
            // InternalMASL.g:7049:2: (this_postfixExpression_0= rulepostfixExpression | this_createExpression_1= rulecreateExpression | this_findExpression_2= rulefindExpression )
            {
            // InternalMASL.g:7049:2: (this_postfixExpression_0= rulepostfixExpression | this_createExpression_1= rulecreateExpression | this_findExpression_2= rulefindExpression )
            int alt104=3;
            switch ( input.LA(1) ) {
            case RULE_INSTANCE:
            case RULE_ANONYMOUS:
            case RULE_LPAREN:
            case RULE_SEQUENCE:
            case RULE_ARRAY:
            case RULE_SET:
            case RULE_BAG:
            case RULE_DICTIONARY:
            case RULE_NULL:
            case RULE_INTEGERLITERAL:
            case RULE_REALLITERAL:
            case RULE_STRINGLITERAL:
            case RULE_TIMESTAMPLITERAL:
            case RULE_DURATIONLITERAL:
            case RULE_TRUE:
            case RULE_FALSE:
            case RULE_FLUSH:
            case RULE_ENDL:
            case RULE_THIS:
            case RULE_CONSOLE:
            case RULE_LINE_NO:
            case RULE_FILE_NAME:
            case RULE_IDENT:
                {
                alt104=1;
                }
                break;
            case RULE_CREATE:
                {
                alt104=2;
                }
                break;
            case RULE_FIND:
            case RULE_FIND_ONE:
            case RULE_FIND_ONLY:
                {
                alt104=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // InternalMASL.g:7050:3: this_postfixExpression_0= rulepostfixExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getExtendedExpressionAccess().getPostfixExpressionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_postfixExpression_0=rulepostfixExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_postfixExpression_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:7059:3: this_createExpression_1= rulecreateExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getExtendedExpressionAccess().getCreateExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_createExpression_1=rulecreateExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_createExpression_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:7068:3: this_findExpression_2= rulefindExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getExtendedExpressionAccess().getFindExpressionParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_findExpression_2=rulefindExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_findExpression_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleextendedExpression"


    // $ANTLR start "entryRulesortOrder"
    // InternalMASL.g:7080:1: entryRulesortOrder returns [String current=null] : iv_rulesortOrder= rulesortOrder EOF ;
    public final String entryRulesortOrder() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulesortOrder = null;


        try {
            // InternalMASL.g:7080:49: (iv_rulesortOrder= rulesortOrder EOF )
            // InternalMASL.g:7081:2: iv_rulesortOrder= rulesortOrder EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSortOrderRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulesortOrder=rulesortOrder();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulesortOrder.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesortOrder"


    // $ANTLR start "rulesortOrder"
    // InternalMASL.g:7087:1: rulesortOrder returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_LPAREN_0= RULE_LPAREN (this_sortOrderComponent_1= rulesortOrderComponent (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )* )? this_RPAREN_4= RULE_RPAREN ) ;
    public final AntlrDatatypeRuleToken rulesortOrder() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_LPAREN_0=null;
        Token this_COMMA_2=null;
        Token this_RPAREN_4=null;
        AntlrDatatypeRuleToken this_sortOrderComponent_1 = null;

        AntlrDatatypeRuleToken this_sortOrderComponent_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:7093:2: ( (this_LPAREN_0= RULE_LPAREN (this_sortOrderComponent_1= rulesortOrderComponent (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )* )? this_RPAREN_4= RULE_RPAREN ) )
            // InternalMASL.g:7094:2: (this_LPAREN_0= RULE_LPAREN (this_sortOrderComponent_1= rulesortOrderComponent (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )* )? this_RPAREN_4= RULE_RPAREN )
            {
            // InternalMASL.g:7094:2: (this_LPAREN_0= RULE_LPAREN (this_sortOrderComponent_1= rulesortOrderComponent (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )* )? this_RPAREN_4= RULE_RPAREN )
            // InternalMASL.g:7095:3: this_LPAREN_0= RULE_LPAREN (this_sortOrderComponent_1= rulesortOrderComponent (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )* )? this_RPAREN_4= RULE_RPAREN
            {
            this_LPAREN_0=(Token)match(input,RULE_LPAREN,FOLLOW_92); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_LPAREN_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_0, grammarAccess.getSortOrderAccess().getLPARENTerminalRuleCall_0());
              		
            }
            // InternalMASL.g:7102:3: (this_sortOrderComponent_1= rulesortOrderComponent (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )* )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==RULE_REVERSE||LA106_0==RULE_IDENT) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // InternalMASL.g:7103:4: this_sortOrderComponent_1= rulesortOrderComponent (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )*
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getSortOrderAccess().getSortOrderComponentParserRuleCall_1_0());
                      			
                    }
                    pushFollow(FOLLOW_23);
                    this_sortOrderComponent_1=rulesortOrderComponent();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_sortOrderComponent_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    // InternalMASL.g:7113:4: (this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent )*
                    loop105:
                    do {
                        int alt105=2;
                        int LA105_0 = input.LA(1);

                        if ( (LA105_0==RULE_COMMA) ) {
                            alt105=1;
                        }


                        switch (alt105) {
                    	case 1 :
                    	    // InternalMASL.g:7114:5: this_COMMA_2= RULE_COMMA this_sortOrderComponent_3= rulesortOrderComponent
                    	    {
                    	    this_COMMA_2=(Token)match(input,RULE_COMMA,FOLLOW_93); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_COMMA_2);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_COMMA_2, grammarAccess.getSortOrderAccess().getCOMMATerminalRuleCall_1_1_0());
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					newCompositeNode(grammarAccess.getSortOrderAccess().getSortOrderComponentParserRuleCall_1_1_1());
                    	      				
                    	    }
                    	    pushFollow(FOLLOW_23);
                    	    this_sortOrderComponent_3=rulesortOrderComponent();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					current.merge(this_sortOrderComponent_3);
                    	      				
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					afterParserOrEnumRuleCall();
                    	      				
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop105;
                        }
                    } while (true);


                    }
                    break;

            }

            this_RPAREN_4=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_RPAREN_4);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_4, grammarAccess.getSortOrderAccess().getRPARENTerminalRuleCall_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulesortOrder"


    // $ANTLR start "entryRulesortOrderComponent"
    // InternalMASL.g:7144:1: entryRulesortOrderComponent returns [String current=null] : iv_rulesortOrderComponent= rulesortOrderComponent EOF ;
    public final String entryRulesortOrderComponent() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulesortOrderComponent = null;


        try {
            // InternalMASL.g:7144:58: (iv_rulesortOrderComponent= rulesortOrderComponent EOF )
            // InternalMASL.g:7145:2: iv_rulesortOrderComponent= rulesortOrderComponent EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSortOrderComponentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulesortOrderComponent=rulesortOrderComponent();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulesortOrderComponent.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesortOrderComponent"


    // $ANTLR start "rulesortOrderComponent"
    // InternalMASL.g:7151:1: rulesortOrderComponent returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_REVERSE_0= RULE_REVERSE )? this_identifier_1= ruleidentifier ) ;
    public final AntlrDatatypeRuleToken rulesortOrderComponent() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_REVERSE_0=null;
        AntlrDatatypeRuleToken this_identifier_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:7157:2: ( ( (this_REVERSE_0= RULE_REVERSE )? this_identifier_1= ruleidentifier ) )
            // InternalMASL.g:7158:2: ( (this_REVERSE_0= RULE_REVERSE )? this_identifier_1= ruleidentifier )
            {
            // InternalMASL.g:7158:2: ( (this_REVERSE_0= RULE_REVERSE )? this_identifier_1= ruleidentifier )
            // InternalMASL.g:7159:3: (this_REVERSE_0= RULE_REVERSE )? this_identifier_1= ruleidentifier
            {
            // InternalMASL.g:7159:3: (this_REVERSE_0= RULE_REVERSE )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==RULE_REVERSE) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // InternalMASL.g:7160:4: this_REVERSE_0= RULE_REVERSE
                    {
                    this_REVERSE_0=(Token)match(input,RULE_REVERSE,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_REVERSE_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_REVERSE_0, grammarAccess.getSortOrderComponentAccess().getREVERSETerminalRuleCall_0());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getSortOrderComponentAccess().getIdentifierParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_identifier_1=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_identifier_1);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulesortOrderComponent"


    // $ANTLR start "entryRulecreateExpression"
    // InternalMASL.g:7182:1: entryRulecreateExpression returns [EObject current=null] : iv_rulecreateExpression= rulecreateExpression EOF ;
    public final EObject entryRulecreateExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecreateExpression = null;


        try {
            // InternalMASL.g:7182:57: (iv_rulecreateExpression= rulecreateExpression EOF )
            // InternalMASL.g:7183:2: iv_rulecreateExpression= rulecreateExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCreateExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecreateExpression=rulecreateExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecreateExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecreateExpression"


    // $ANTLR start "rulecreateExpression"
    // InternalMASL.g:7189:1: rulecreateExpression returns [EObject current=null] : (this_CREATE_0= RULE_CREATE (this_UNIQUE_1= RULE_UNIQUE )? ruleobjectName this_createArgumentList_3= rulecreateArgumentList ) ;
    public final EObject rulecreateExpression() throws RecognitionException {
        EObject current = null;

        Token this_CREATE_0=null;
        Token this_UNIQUE_1=null;
        EObject this_createArgumentList_3 = null;



        	enterRule();

        try {
            // InternalMASL.g:7195:2: ( (this_CREATE_0= RULE_CREATE (this_UNIQUE_1= RULE_UNIQUE )? ruleobjectName this_createArgumentList_3= rulecreateArgumentList ) )
            // InternalMASL.g:7196:2: (this_CREATE_0= RULE_CREATE (this_UNIQUE_1= RULE_UNIQUE )? ruleobjectName this_createArgumentList_3= rulecreateArgumentList )
            {
            // InternalMASL.g:7196:2: (this_CREATE_0= RULE_CREATE (this_UNIQUE_1= RULE_UNIQUE )? ruleobjectName this_createArgumentList_3= rulecreateArgumentList )
            // InternalMASL.g:7197:3: this_CREATE_0= RULE_CREATE (this_UNIQUE_1= RULE_UNIQUE )? ruleobjectName this_createArgumentList_3= rulecreateArgumentList
            {
            this_CREATE_0=(Token)match(input,RULE_CREATE,FOLLOW_94); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_CREATE_0, grammarAccess.getCreateExpressionAccess().getCREATETerminalRuleCall_0());
              		
            }
            // InternalMASL.g:7201:3: (this_UNIQUE_1= RULE_UNIQUE )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==RULE_UNIQUE) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalMASL.g:7202:4: this_UNIQUE_1= RULE_UNIQUE
                    {
                    this_UNIQUE_1=(Token)match(input,RULE_UNIQUE,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_UNIQUE_1, grammarAccess.getCreateExpressionAccess().getUNIQUETerminalRuleCall_1());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCreateExpressionAccess().getObjectNameParserRuleCall_2());
              		
            }
            pushFollow(FOLLOW_7);
            ruleobjectName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCreateExpressionAccess().getCreateArgumentListParserRuleCall_3());
              		
            }
            pushFollow(FOLLOW_2);
            this_createArgumentList_3=rulecreateArgumentList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_createArgumentList_3;
              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecreateExpression"


    // $ANTLR start "entryRulecreateArgumentList"
    // InternalMASL.g:7226:1: entryRulecreateArgumentList returns [EObject current=null] : iv_rulecreateArgumentList= rulecreateArgumentList EOF ;
    public final EObject entryRulecreateArgumentList() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecreateArgumentList = null;


        try {
            // InternalMASL.g:7226:59: (iv_rulecreateArgumentList= rulecreateArgumentList EOF )
            // InternalMASL.g:7227:2: iv_rulecreateArgumentList= rulecreateArgumentList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCreateArgumentListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecreateArgumentList=rulecreateArgumentList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecreateArgumentList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecreateArgumentList"


    // $ANTLR start "rulecreateArgumentList"
    // InternalMASL.g:7233:1: rulecreateArgumentList returns [EObject current=null] : ( () this_LPAREN_1= RULE_LPAREN ( ( (lv_c_2_0= rulecreateArgument ) ) (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )* )? this_RPAREN_5= RULE_RPAREN ) ;
    public final EObject rulecreateArgumentList() throws RecognitionException {
        EObject current = null;

        Token this_LPAREN_1=null;
        Token this_COMMA_3=null;
        Token this_RPAREN_5=null;
        EObject lv_c_2_0 = null;

        EObject lv_c_4_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:7239:2: ( ( () this_LPAREN_1= RULE_LPAREN ( ( (lv_c_2_0= rulecreateArgument ) ) (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )* )? this_RPAREN_5= RULE_RPAREN ) )
            // InternalMASL.g:7240:2: ( () this_LPAREN_1= RULE_LPAREN ( ( (lv_c_2_0= rulecreateArgument ) ) (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )* )? this_RPAREN_5= RULE_RPAREN )
            {
            // InternalMASL.g:7240:2: ( () this_LPAREN_1= RULE_LPAREN ( ( (lv_c_2_0= rulecreateArgument ) ) (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )* )? this_RPAREN_5= RULE_RPAREN )
            // InternalMASL.g:7241:3: () this_LPAREN_1= RULE_LPAREN ( ( (lv_c_2_0= rulecreateArgument ) ) (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )* )? this_RPAREN_5= RULE_RPAREN
            {
            // InternalMASL.g:7241:3: ()
            // InternalMASL.g:7242:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getCreateArgumentListAccess().getCreateArgumentListAction_0(),
              					current);
              			
            }

            }

            this_LPAREN_1=(Token)match(input,RULE_LPAREN,FOLLOW_95); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_1, grammarAccess.getCreateArgumentListAccess().getLPARENTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:7252:3: ( ( (lv_c_2_0= rulecreateArgument ) ) (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )* )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==RULE_CURRENT_STATE||LA110_0==RULE_IDENT) ) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // InternalMASL.g:7253:4: ( (lv_c_2_0= rulecreateArgument ) ) (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )*
                    {
                    // InternalMASL.g:7253:4: ( (lv_c_2_0= rulecreateArgument ) )
                    // InternalMASL.g:7254:5: (lv_c_2_0= rulecreateArgument )
                    {
                    // InternalMASL.g:7254:5: (lv_c_2_0= rulecreateArgument )
                    // InternalMASL.g:7255:6: lv_c_2_0= rulecreateArgument
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCreateArgumentListAccess().getCCreateArgumentParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_23);
                    lv_c_2_0=rulecreateArgument();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCreateArgumentListRule());
                      						}
                      						add(
                      							current,
                      							"c",
                      							lv_c_2_0,
                      							"org.xtuml.bp.ui.xtext.MASL.createArgument");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalMASL.g:7272:4: (this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) ) )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==RULE_COMMA) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // InternalMASL.g:7273:5: this_COMMA_3= RULE_COMMA ( (lv_c_4_0= rulecreateArgument ) )
                    	    {
                    	    this_COMMA_3=(Token)match(input,RULE_COMMA,FOLLOW_96); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_COMMA_3, grammarAccess.getCreateArgumentListAccess().getCOMMATerminalRuleCall_2_1_0());
                    	      				
                    	    }
                    	    // InternalMASL.g:7277:5: ( (lv_c_4_0= rulecreateArgument ) )
                    	    // InternalMASL.g:7278:6: (lv_c_4_0= rulecreateArgument )
                    	    {
                    	    // InternalMASL.g:7278:6: (lv_c_4_0= rulecreateArgument )
                    	    // InternalMASL.g:7279:7: lv_c_4_0= rulecreateArgument
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getCreateArgumentListAccess().getCCreateArgumentParserRuleCall_2_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_23);
                    	    lv_c_4_0=rulecreateArgument();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getCreateArgumentListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"c",
                    	      								lv_c_4_0,
                    	      								"org.xtuml.bp.ui.xtext.MASL.createArgument");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop109;
                        }
                    } while (true);


                    }
                    break;

            }

            this_RPAREN_5=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_5, grammarAccess.getCreateArgumentListAccess().getRPARENTerminalRuleCall_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecreateArgumentList"


    // $ANTLR start "entryRulecreateArgument"
    // InternalMASL.g:7306:1: entryRulecreateArgument returns [EObject current=null] : iv_rulecreateArgument= rulecreateArgument EOF ;
    public final EObject entryRulecreateArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecreateArgument = null;


        try {
            // InternalMASL.g:7306:55: (iv_rulecreateArgument= rulecreateArgument EOF )
            // InternalMASL.g:7307:2: iv_rulecreateArgument= rulecreateArgument EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCreateArgumentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecreateArgument=rulecreateArgument();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecreateArgument; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecreateArgument"


    // $ANTLR start "rulecreateArgument"
    // InternalMASL.g:7313:1: rulecreateArgument returns [EObject current=null] : ( ( ( (lv_a_0_0= ruleattributeName ) ) this_GOES_TO_1= RULE_GOES_TO ( (lv_e_2_0= ruleexpression ) ) ) | (this_CURRENT_STATE_3= RULE_CURRENT_STATE this_GOES_TO_4= RULE_GOES_TO ( (lv_s_5_0= rulestateName ) ) ) ) ;
    public final EObject rulecreateArgument() throws RecognitionException {
        EObject current = null;

        Token this_GOES_TO_1=null;
        Token this_CURRENT_STATE_3=null;
        Token this_GOES_TO_4=null;
        AntlrDatatypeRuleToken lv_a_0_0 = null;

        EObject lv_e_2_0 = null;

        AntlrDatatypeRuleToken lv_s_5_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:7319:2: ( ( ( ( (lv_a_0_0= ruleattributeName ) ) this_GOES_TO_1= RULE_GOES_TO ( (lv_e_2_0= ruleexpression ) ) ) | (this_CURRENT_STATE_3= RULE_CURRENT_STATE this_GOES_TO_4= RULE_GOES_TO ( (lv_s_5_0= rulestateName ) ) ) ) )
            // InternalMASL.g:7320:2: ( ( ( (lv_a_0_0= ruleattributeName ) ) this_GOES_TO_1= RULE_GOES_TO ( (lv_e_2_0= ruleexpression ) ) ) | (this_CURRENT_STATE_3= RULE_CURRENT_STATE this_GOES_TO_4= RULE_GOES_TO ( (lv_s_5_0= rulestateName ) ) ) )
            {
            // InternalMASL.g:7320:2: ( ( ( (lv_a_0_0= ruleattributeName ) ) this_GOES_TO_1= RULE_GOES_TO ( (lv_e_2_0= ruleexpression ) ) ) | (this_CURRENT_STATE_3= RULE_CURRENT_STATE this_GOES_TO_4= RULE_GOES_TO ( (lv_s_5_0= rulestateName ) ) ) )
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==RULE_IDENT) ) {
                alt111=1;
            }
            else if ( (LA111_0==RULE_CURRENT_STATE) ) {
                alt111=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }
            switch (alt111) {
                case 1 :
                    // InternalMASL.g:7321:3: ( ( (lv_a_0_0= ruleattributeName ) ) this_GOES_TO_1= RULE_GOES_TO ( (lv_e_2_0= ruleexpression ) ) )
                    {
                    // InternalMASL.g:7321:3: ( ( (lv_a_0_0= ruleattributeName ) ) this_GOES_TO_1= RULE_GOES_TO ( (lv_e_2_0= ruleexpression ) ) )
                    // InternalMASL.g:7322:4: ( (lv_a_0_0= ruleattributeName ) ) this_GOES_TO_1= RULE_GOES_TO ( (lv_e_2_0= ruleexpression ) )
                    {
                    // InternalMASL.g:7322:4: ( (lv_a_0_0= ruleattributeName ) )
                    // InternalMASL.g:7323:5: (lv_a_0_0= ruleattributeName )
                    {
                    // InternalMASL.g:7323:5: (lv_a_0_0= ruleattributeName )
                    // InternalMASL.g:7324:6: lv_a_0_0= ruleattributeName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCreateArgumentAccess().getAAttributeNameParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_70);
                    lv_a_0_0=ruleattributeName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCreateArgumentRule());
                      						}
                      						set(
                      							current,
                      							"a",
                      							lv_a_0_0,
                      							"org.xtuml.bp.ui.xtext.MASL.attributeName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    this_GOES_TO_1=(Token)match(input,RULE_GOES_TO,FOLLOW_8); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_GOES_TO_1, grammarAccess.getCreateArgumentAccess().getGOES_TOTerminalRuleCall_0_1());
                      			
                    }
                    // InternalMASL.g:7345:4: ( (lv_e_2_0= ruleexpression ) )
                    // InternalMASL.g:7346:5: (lv_e_2_0= ruleexpression )
                    {
                    // InternalMASL.g:7346:5: (lv_e_2_0= ruleexpression )
                    // InternalMASL.g:7347:6: lv_e_2_0= ruleexpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCreateArgumentAccess().getEExpressionParserRuleCall_0_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_e_2_0=ruleexpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCreateArgumentRule());
                      						}
                      						set(
                      							current,
                      							"e",
                      							lv_e_2_0,
                      							"org.xtuml.bp.ui.xtext.MASL.expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:7366:3: (this_CURRENT_STATE_3= RULE_CURRENT_STATE this_GOES_TO_4= RULE_GOES_TO ( (lv_s_5_0= rulestateName ) ) )
                    {
                    // InternalMASL.g:7366:3: (this_CURRENT_STATE_3= RULE_CURRENT_STATE this_GOES_TO_4= RULE_GOES_TO ( (lv_s_5_0= rulestateName ) ) )
                    // InternalMASL.g:7367:4: this_CURRENT_STATE_3= RULE_CURRENT_STATE this_GOES_TO_4= RULE_GOES_TO ( (lv_s_5_0= rulestateName ) )
                    {
                    this_CURRENT_STATE_3=(Token)match(input,RULE_CURRENT_STATE,FOLLOW_70); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_CURRENT_STATE_3, grammarAccess.getCreateArgumentAccess().getCURRENT_STATETerminalRuleCall_1_0());
                      			
                    }
                    this_GOES_TO_4=(Token)match(input,RULE_GOES_TO,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_GOES_TO_4, grammarAccess.getCreateArgumentAccess().getGOES_TOTerminalRuleCall_1_1());
                      			
                    }
                    // InternalMASL.g:7375:4: ( (lv_s_5_0= rulestateName ) )
                    // InternalMASL.g:7376:5: (lv_s_5_0= rulestateName )
                    {
                    // InternalMASL.g:7376:5: (lv_s_5_0= rulestateName )
                    // InternalMASL.g:7377:6: lv_s_5_0= rulestateName
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getCreateArgumentAccess().getSStateNameParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_s_5_0=rulestateName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getCreateArgumentRule());
                      						}
                      						set(
                      							current,
                      							"s",
                      							lv_s_5_0,
                      							"org.xtuml.bp.ui.xtext.MASL.stateName");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecreateArgument"


    // $ANTLR start "entryRulefindExpression"
    // InternalMASL.g:7399:1: entryRulefindExpression returns [EObject current=null] : iv_rulefindExpression= rulefindExpression EOF ;
    public final EObject entryRulefindExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefindExpression = null;


        try {
            // InternalMASL.g:7399:55: (iv_rulefindExpression= rulefindExpression EOF )
            // InternalMASL.g:7400:2: iv_rulefindExpression= rulefindExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindExpression=rulefindExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindExpression"


    // $ANTLR start "rulefindExpression"
    // InternalMASL.g:7406:1: rulefindExpression returns [EObject current=null] : ( rulefindType ( (lv_p_1_0= rulepostfixNoCallExpression ) ) ( (lv_w_2_0= rulewhereClause ) ) ) ;
    public final EObject rulefindExpression() throws RecognitionException {
        EObject current = null;

        EObject lv_p_1_0 = null;

        EObject lv_w_2_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:7412:2: ( ( rulefindType ( (lv_p_1_0= rulepostfixNoCallExpression ) ) ( (lv_w_2_0= rulewhereClause ) ) ) )
            // InternalMASL.g:7413:2: ( rulefindType ( (lv_p_1_0= rulepostfixNoCallExpression ) ) ( (lv_w_2_0= rulewhereClause ) ) )
            {
            // InternalMASL.g:7413:2: ( rulefindType ( (lv_p_1_0= rulepostfixNoCallExpression ) ) ( (lv_w_2_0= rulewhereClause ) ) )
            // InternalMASL.g:7414:3: rulefindType ( (lv_p_1_0= rulepostfixNoCallExpression ) ) ( (lv_w_2_0= rulewhereClause ) )
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getFindExpressionAccess().getFindTypeParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_97);
            rulefindType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }
            // InternalMASL.g:7421:3: ( (lv_p_1_0= rulepostfixNoCallExpression ) )
            // InternalMASL.g:7422:4: (lv_p_1_0= rulepostfixNoCallExpression )
            {
            // InternalMASL.g:7422:4: (lv_p_1_0= rulepostfixNoCallExpression )
            // InternalMASL.g:7423:5: lv_p_1_0= rulepostfixNoCallExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindExpressionAccess().getPPostfixNoCallExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_7);
            lv_p_1_0=rulepostfixNoCallExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindExpressionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.postfixNoCallExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:7440:3: ( (lv_w_2_0= rulewhereClause ) )
            // InternalMASL.g:7441:4: (lv_w_2_0= rulewhereClause )
            {
            // InternalMASL.g:7441:4: (lv_w_2_0= rulewhereClause )
            // InternalMASL.g:7442:5: lv_w_2_0= rulewhereClause
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFindExpressionAccess().getWWhereClauseParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_w_2_0=rulewhereClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFindExpressionRule());
              					}
              					set(
              						current,
              						"w",
              						lv_w_2_0,
              						"org.xtuml.bp.ui.xtext.MASL.whereClause");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindExpression"


    // $ANTLR start "entryRulewhereClause"
    // InternalMASL.g:7463:1: entryRulewhereClause returns [EObject current=null] : iv_rulewhereClause= rulewhereClause EOF ;
    public final EObject entryRulewhereClause() throws RecognitionException {
        EObject current = null;

        EObject iv_rulewhereClause = null;


        try {
            // InternalMASL.g:7463:52: (iv_rulewhereClause= rulewhereClause EOF )
            // InternalMASL.g:7464:2: iv_rulewhereClause= rulewhereClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getWhereClauseRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulewhereClause=rulewhereClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulewhereClause; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulewhereClause"


    // $ANTLR start "rulewhereClause"
    // InternalMASL.g:7470:1: rulewhereClause returns [EObject current=null] : ( () this_LPAREN_1= RULE_LPAREN (this_findCondition_2= rulefindCondition )? this_RPAREN_3= RULE_RPAREN ) ;
    public final EObject rulewhereClause() throws RecognitionException {
        EObject current = null;

        Token this_LPAREN_1=null;
        Token this_RPAREN_3=null;
        EObject this_findCondition_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:7476:2: ( ( () this_LPAREN_1= RULE_LPAREN (this_findCondition_2= rulefindCondition )? this_RPAREN_3= RULE_RPAREN ) )
            // InternalMASL.g:7477:2: ( () this_LPAREN_1= RULE_LPAREN (this_findCondition_2= rulefindCondition )? this_RPAREN_3= RULE_RPAREN )
            {
            // InternalMASL.g:7477:2: ( () this_LPAREN_1= RULE_LPAREN (this_findCondition_2= rulefindCondition )? this_RPAREN_3= RULE_RPAREN )
            // InternalMASL.g:7478:3: () this_LPAREN_1= RULE_LPAREN (this_findCondition_2= rulefindCondition )? this_RPAREN_3= RULE_RPAREN
            {
            // InternalMASL.g:7478:3: ()
            // InternalMASL.g:7479:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getWhereClauseAccess().getWhereClauseAction_0(),
              					current);
              			
            }

            }

            this_LPAREN_1=(Token)match(input,RULE_LPAREN,FOLLOW_98); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_1, grammarAccess.getWhereClauseAccess().getLPARENTerminalRuleCall_1());
              		
            }
            // InternalMASL.g:7489:3: (this_findCondition_2= rulefindCondition )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==RULE_LPAREN||LA112_0==RULE_NOT||LA112_0==RULE_IDENT) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // InternalMASL.g:7490:4: this_findCondition_2= rulefindCondition
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getWhereClauseAccess().getFindConditionParserRuleCall_2());
                      			
                    }
                    pushFollow(FOLLOW_9);
                    this_findCondition_2=rulefindCondition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_findCondition_2;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }
                    break;

            }

            this_RPAREN_3=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_3, grammarAccess.getWhereClauseAccess().getRPARENTerminalRuleCall_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulewhereClause"


    // $ANTLR start "entryRulefindType"
    // InternalMASL.g:7507:1: entryRulefindType returns [String current=null] : iv_rulefindType= rulefindType EOF ;
    public final String entryRulefindType() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulefindType = null;


        try {
            // InternalMASL.g:7507:48: (iv_rulefindType= rulefindType EOF )
            // InternalMASL.g:7508:2: iv_rulefindType= rulefindType EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFindTypeRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulefindType=rulefindType();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulefindType.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefindType"


    // $ANTLR start "rulefindType"
    // InternalMASL.g:7514:1: rulefindType returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_FIND_0= RULE_FIND | this_FIND_ONE_1= RULE_FIND_ONE | this_FIND_ONLY_2= RULE_FIND_ONLY ) ;
    public final AntlrDatatypeRuleToken rulefindType() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_FIND_0=null;
        Token this_FIND_ONE_1=null;
        Token this_FIND_ONLY_2=null;


        	enterRule();

        try {
            // InternalMASL.g:7520:2: ( (this_FIND_0= RULE_FIND | this_FIND_ONE_1= RULE_FIND_ONE | this_FIND_ONLY_2= RULE_FIND_ONLY ) )
            // InternalMASL.g:7521:2: (this_FIND_0= RULE_FIND | this_FIND_ONE_1= RULE_FIND_ONE | this_FIND_ONLY_2= RULE_FIND_ONLY )
            {
            // InternalMASL.g:7521:2: (this_FIND_0= RULE_FIND | this_FIND_ONE_1= RULE_FIND_ONE | this_FIND_ONLY_2= RULE_FIND_ONLY )
            int alt113=3;
            switch ( input.LA(1) ) {
            case RULE_FIND:
                {
                alt113=1;
                }
                break;
            case RULE_FIND_ONE:
                {
                alt113=2;
                }
                break;
            case RULE_FIND_ONLY:
                {
                alt113=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // InternalMASL.g:7522:3: this_FIND_0= RULE_FIND
                    {
                    this_FIND_0=(Token)match(input,RULE_FIND,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_FIND_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_FIND_0, grammarAccess.getFindTypeAccess().getFINDTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:7530:3: this_FIND_ONE_1= RULE_FIND_ONE
                    {
                    this_FIND_ONE_1=(Token)match(input,RULE_FIND_ONE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_FIND_ONE_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_FIND_ONE_1, grammarAccess.getFindTypeAccess().getFIND_ONETerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:7538:3: this_FIND_ONLY_2= RULE_FIND_ONLY
                    {
                    this_FIND_ONLY_2=(Token)match(input,RULE_FIND_ONLY,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_FIND_ONLY_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_FIND_ONLY_2, grammarAccess.getFindTypeAccess().getFIND_ONLYTerminalRuleCall_2());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulefindType"


    // $ANTLR start "entryRulepostfixExpression"
    // InternalMASL.g:7549:1: entryRulepostfixExpression returns [EObject current=null] : iv_rulepostfixExpression= rulepostfixExpression EOF ;
    public final EObject entryRulepostfixExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulepostfixExpression = null;


        try {
            // InternalMASL.g:7549:58: (iv_rulepostfixExpression= rulepostfixExpression EOF )
            // InternalMASL.g:7550:2: iv_rulepostfixExpression= rulepostfixExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPostfixExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulepostfixExpression=rulepostfixExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulepostfixExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulepostfixExpression"


    // $ANTLR start "rulepostfixExpression"
    // InternalMASL.g:7556:1: rulepostfixExpression returns [EObject current=null] : ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN ) | (this_DOT_4= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET ) | ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN ) | (this_PRIME_19= RULE_PRIME rulecharacteristic ) )* ) ;
    public final EObject rulepostfixExpression() throws RecognitionException {
        EObject current = null;

        Token this_LPAREN_1=null;
        Token this_RPAREN_3=null;
        Token this_DOT_4=null;
        Token this_TERMINATOR_SCOPE_6=null;
        Token this_LBRACKET_8=null;
        Token this_RBRACKET_10=null;
        Token this_PRIME_11=null;
        Token this_LPAREN_13=null;
        Token this_PRIME_14=null;
        Token this_LPAREN_16=null;
        Token this_RPAREN_18=null;
        Token this_PRIME_19=null;
        EObject lv_p_0_0 = null;

        EObject lv_a_2_0 = null;

        EObject lv_e_9_0 = null;

        EObject lv_a_17_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:7562:2: ( ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN ) | (this_DOT_4= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET ) | ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN ) | (this_PRIME_19= RULE_PRIME rulecharacteristic ) )* ) )
            // InternalMASL.g:7563:2: ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN ) | (this_DOT_4= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET ) | ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN ) | (this_PRIME_19= RULE_PRIME rulecharacteristic ) )* )
            {
            // InternalMASL.g:7563:2: ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN ) | (this_DOT_4= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET ) | ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN ) | (this_PRIME_19= RULE_PRIME rulecharacteristic ) )* )
            // InternalMASL.g:7564:3: ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN ) | (this_DOT_4= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET ) | ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN ) | (this_PRIME_19= RULE_PRIME rulecharacteristic ) )*
            {
            // InternalMASL.g:7564:3: ( (lv_p_0_0= ruleprimaryExpression ) )
            // InternalMASL.g:7565:4: (lv_p_0_0= ruleprimaryExpression )
            {
            // InternalMASL.g:7565:4: (lv_p_0_0= ruleprimaryExpression )
            // InternalMASL.g:7566:5: lv_p_0_0= ruleprimaryExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getPostfixExpressionAccess().getPPrimaryExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_52);
            lv_p_0_0=ruleprimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getPostfixExpressionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.primaryExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:7583:3: ( (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN ) | (this_DOT_4= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET ) | ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN ) | (this_PRIME_19= RULE_PRIME rulecharacteristic ) )*
            loop114:
            do {
                int alt114=7;
                alt114 = dfa114.predict(input);
                switch (alt114) {
            	case 1 :
            	    // InternalMASL.g:7584:4: (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN )
            	    {
            	    // InternalMASL.g:7584:4: (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN )
            	    // InternalMASL.g:7585:5: this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN
            	    {
            	    this_LPAREN_1=(Token)match(input,RULE_LPAREN,FOLLOW_51); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_LPAREN_1, grammarAccess.getPostfixExpressionAccess().getLPARENTerminalRuleCall_1_0_0());
            	      				
            	    }
            	    // InternalMASL.g:7589:5: ( (lv_a_2_0= ruleargumentList ) )
            	    // InternalMASL.g:7590:6: (lv_a_2_0= ruleargumentList )
            	    {
            	    // InternalMASL.g:7590:6: (lv_a_2_0= ruleargumentList )
            	    // InternalMASL.g:7591:7: lv_a_2_0= ruleargumentList
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getPostfixExpressionAccess().getAArgumentListParserRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_a_2_0=ruleargumentList();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getPostfixExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"a",
            	      								lv_a_2_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.argumentList");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    this_RPAREN_3=(Token)match(input,RULE_RPAREN,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_RPAREN_3, grammarAccess.getPostfixExpressionAccess().getRPARENTerminalRuleCall_1_0_2());
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalMASL.g:7614:4: (this_DOT_4= RULE_DOT ruleidentifier )
            	    {
            	    // InternalMASL.g:7614:4: (this_DOT_4= RULE_DOT ruleidentifier )
            	    // InternalMASL.g:7615:5: this_DOT_4= RULE_DOT ruleidentifier
            	    {
            	    this_DOT_4=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_DOT_4, grammarAccess.getPostfixExpressionAccess().getDOTTerminalRuleCall_1_1_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getPostfixExpressionAccess().getIdentifierParserRuleCall_1_1_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_52);
            	    ruleidentifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalMASL.g:7628:4: (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier )
            	    {
            	    // InternalMASL.g:7628:4: (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier )
            	    // InternalMASL.g:7629:5: this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier
            	    {
            	    this_TERMINATOR_SCOPE_6=(Token)match(input,RULE_TERMINATOR_SCOPE,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_TERMINATOR_SCOPE_6, grammarAccess.getPostfixExpressionAccess().getTERMINATOR_SCOPETerminalRuleCall_1_2_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getPostfixExpressionAccess().getIdentifierParserRuleCall_1_2_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_52);
            	    ruleidentifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalMASL.g:7642:4: (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET )
            	    {
            	    // InternalMASL.g:7642:4: (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET )
            	    // InternalMASL.g:7643:5: this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET
            	    {
            	    this_LBRACKET_8=(Token)match(input,RULE_LBRACKET,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_LBRACKET_8, grammarAccess.getPostfixExpressionAccess().getLBRACKETTerminalRuleCall_1_3_0());
            	      				
            	    }
            	    // InternalMASL.g:7647:5: ( (lv_e_9_0= ruleexpression ) )
            	    // InternalMASL.g:7648:6: (lv_e_9_0= ruleexpression )
            	    {
            	    // InternalMASL.g:7648:6: (lv_e_9_0= ruleexpression )
            	    // InternalMASL.g:7649:7: lv_e_9_0= ruleexpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getPostfixExpressionAccess().getEExpressionParserRuleCall_1_3_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_49);
            	    lv_e_9_0=ruleexpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getPostfixExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"e",
            	      								lv_e_9_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.expression");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    this_RBRACKET_10=(Token)match(input,RULE_RBRACKET,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_RBRACKET_10, grammarAccess.getPostfixExpressionAccess().getRBRACKETTerminalRuleCall_1_3_2());
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 5 :
            	    // InternalMASL.g:7672:4: ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN )
            	    {
            	    // InternalMASL.g:7672:4: ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN )
            	    // InternalMASL.g:7673:5: (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN
            	    {
            	    // InternalMASL.g:7673:5: (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN )
            	    // InternalMASL.g:7674:6: this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN
            	    {
            	    this_PRIME_11=(Token)match(input,RULE_PRIME,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(this_PRIME_11, grammarAccess.getPostfixExpressionAccess().getPRIMETerminalRuleCall_1_4_0_0());
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getPostfixExpressionAccess().getCharacteristicParserRuleCall_1_4_0_1());
            	      					
            	    }
            	    pushFollow(FOLLOW_7);
            	    rulecharacteristic();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						afterParserOrEnumRuleCall();
            	      					
            	    }
            	    this_LPAREN_13=(Token)match(input,RULE_LPAREN,FOLLOW_99); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(this_LPAREN_13, grammarAccess.getPostfixExpressionAccess().getLPARENTerminalRuleCall_1_4_0_2());
            	      					
            	    }

            	    }

            	    // InternalMASL.g:7690:5: ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME )
            	    // InternalMASL.g:7691:6: ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME
            	    {
            	    this_PRIME_14=(Token)match(input,RULE_PRIME,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(this_PRIME_14, grammarAccess.getPostfixExpressionAccess().getPRIMETerminalRuleCall_1_4_1());
            	      					
            	    }

            	    }

            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getPostfixExpressionAccess().getCharacteristicParserRuleCall_1_4_2());
            	      				
            	    }
            	    pushFollow(FOLLOW_7);
            	    rulecharacteristic();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }
            	    this_LPAREN_16=(Token)match(input,RULE_LPAREN,FOLLOW_51); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_LPAREN_16, grammarAccess.getPostfixExpressionAccess().getLPARENTerminalRuleCall_1_4_3());
            	      				
            	    }
            	    // InternalMASL.g:7708:5: ( (lv_a_17_0= ruleargumentList ) )
            	    // InternalMASL.g:7709:6: (lv_a_17_0= ruleargumentList )
            	    {
            	    // InternalMASL.g:7709:6: (lv_a_17_0= ruleargumentList )
            	    // InternalMASL.g:7710:7: lv_a_17_0= ruleargumentList
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getPostfixExpressionAccess().getAArgumentListParserRuleCall_1_4_4_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_a_17_0=ruleargumentList();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getPostfixExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"a",
            	      								lv_a_17_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.argumentList");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    this_RPAREN_18=(Token)match(input,RULE_RPAREN,FOLLOW_52); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_RPAREN_18, grammarAccess.getPostfixExpressionAccess().getRPARENTerminalRuleCall_1_4_5());
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 6 :
            	    // InternalMASL.g:7733:4: (this_PRIME_19= RULE_PRIME rulecharacteristic )
            	    {
            	    // InternalMASL.g:7733:4: (this_PRIME_19= RULE_PRIME rulecharacteristic )
            	    // InternalMASL.g:7734:5: this_PRIME_19= RULE_PRIME rulecharacteristic
            	    {
            	    this_PRIME_19=(Token)match(input,RULE_PRIME,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_PRIME_19, grammarAccess.getPostfixExpressionAccess().getPRIMETerminalRuleCall_1_5_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getPostfixExpressionAccess().getCharacteristicParserRuleCall_1_5_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_52);
            	    rulecharacteristic();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop114;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulepostfixExpression"


    // $ANTLR start "entryRulepostfixNoCallExpression"
    // InternalMASL.g:7751:1: entryRulepostfixNoCallExpression returns [EObject current=null] : iv_rulepostfixNoCallExpression= rulepostfixNoCallExpression EOF ;
    public final EObject entryRulepostfixNoCallExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulepostfixNoCallExpression = null;


        try {
            // InternalMASL.g:7751:64: (iv_rulepostfixNoCallExpression= rulepostfixNoCallExpression EOF )
            // InternalMASL.g:7752:2: iv_rulepostfixNoCallExpression= rulepostfixNoCallExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPostfixNoCallExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulepostfixNoCallExpression=rulepostfixNoCallExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulepostfixNoCallExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulepostfixNoCallExpression"


    // $ANTLR start "rulepostfixNoCallExpression"
    // InternalMASL.g:7758:1: rulepostfixNoCallExpression returns [EObject current=null] : ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME rulecharacteristic ) )* ) ;
    public final EObject rulepostfixNoCallExpression() throws RecognitionException {
        EObject current = null;

        Token this_DOT_1=null;
        Token this_TERMINATOR_SCOPE_3=null;
        Token this_LBRACKET_5=null;
        Token this_RBRACKET_7=null;
        Token this_PRIME_8=null;
        EObject lv_p_0_0 = null;

        EObject lv_e_6_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:7764:2: ( ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME rulecharacteristic ) )* ) )
            // InternalMASL.g:7765:2: ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME rulecharacteristic ) )* )
            {
            // InternalMASL.g:7765:2: ( ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME rulecharacteristic ) )* )
            // InternalMASL.g:7766:3: ( (lv_p_0_0= ruleprimaryExpression ) ) ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME rulecharacteristic ) )*
            {
            // InternalMASL.g:7766:3: ( (lv_p_0_0= ruleprimaryExpression ) )
            // InternalMASL.g:7767:4: (lv_p_0_0= ruleprimaryExpression )
            {
            // InternalMASL.g:7767:4: (lv_p_0_0= ruleprimaryExpression )
            // InternalMASL.g:7768:5: lv_p_0_0= ruleprimaryExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getPostfixNoCallExpressionAccess().getPPrimaryExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_100);
            lv_p_0_0=ruleprimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getPostfixNoCallExpressionRule());
              					}
              					set(
              						current,
              						"p",
              						lv_p_0_0,
              						"org.xtuml.bp.ui.xtext.MASL.primaryExpression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:7785:3: ( (this_DOT_1= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET ) | (this_PRIME_8= RULE_PRIME rulecharacteristic ) )*
            loop115:
            do {
                int alt115=5;
                switch ( input.LA(1) ) {
                case RULE_DOT:
                    {
                    alt115=1;
                    }
                    break;
                case RULE_TERMINATOR_SCOPE:
                    {
                    alt115=2;
                    }
                    break;
                case RULE_LBRACKET:
                    {
                    alt115=3;
                    }
                    break;
                case RULE_PRIME:
                    {
                    alt115=4;
                    }
                    break;

                }

                switch (alt115) {
            	case 1 :
            	    // InternalMASL.g:7786:4: (this_DOT_1= RULE_DOT ruleidentifier )
            	    {
            	    // InternalMASL.g:7786:4: (this_DOT_1= RULE_DOT ruleidentifier )
            	    // InternalMASL.g:7787:5: this_DOT_1= RULE_DOT ruleidentifier
            	    {
            	    this_DOT_1=(Token)match(input,RULE_DOT,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_DOT_1, grammarAccess.getPostfixNoCallExpressionAccess().getDOTTerminalRuleCall_1_0_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getPostfixNoCallExpressionAccess().getIdentifierParserRuleCall_1_0_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_100);
            	    ruleidentifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalMASL.g:7800:4: (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier )
            	    {
            	    // InternalMASL.g:7800:4: (this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier )
            	    // InternalMASL.g:7801:5: this_TERMINATOR_SCOPE_3= RULE_TERMINATOR_SCOPE ruleidentifier
            	    {
            	    this_TERMINATOR_SCOPE_3=(Token)match(input,RULE_TERMINATOR_SCOPE,FOLLOW_4); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_TERMINATOR_SCOPE_3, grammarAccess.getPostfixNoCallExpressionAccess().getTERMINATOR_SCOPETerminalRuleCall_1_1_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getPostfixNoCallExpressionAccess().getIdentifierParserRuleCall_1_1_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_100);
            	    ruleidentifier();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 3 :
            	    // InternalMASL.g:7814:4: (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET )
            	    {
            	    // InternalMASL.g:7814:4: (this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET )
            	    // InternalMASL.g:7815:5: this_LBRACKET_5= RULE_LBRACKET ( (lv_e_6_0= ruleexpression ) ) this_RBRACKET_7= RULE_RBRACKET
            	    {
            	    this_LBRACKET_5=(Token)match(input,RULE_LBRACKET,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_LBRACKET_5, grammarAccess.getPostfixNoCallExpressionAccess().getLBRACKETTerminalRuleCall_1_2_0());
            	      				
            	    }
            	    // InternalMASL.g:7819:5: ( (lv_e_6_0= ruleexpression ) )
            	    // InternalMASL.g:7820:6: (lv_e_6_0= ruleexpression )
            	    {
            	    // InternalMASL.g:7820:6: (lv_e_6_0= ruleexpression )
            	    // InternalMASL.g:7821:7: lv_e_6_0= ruleexpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getPostfixNoCallExpressionAccess().getEExpressionParserRuleCall_1_2_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_49);
            	    lv_e_6_0=ruleexpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getPostfixNoCallExpressionRule());
            	      							}
            	      							add(
            	      								current,
            	      								"e",
            	      								lv_e_6_0,
            	      								"org.xtuml.bp.ui.xtext.MASL.expression");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }

            	    this_RBRACKET_7=(Token)match(input,RULE_RBRACKET,FOLLOW_100); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_RBRACKET_7, grammarAccess.getPostfixNoCallExpressionAccess().getRBRACKETTerminalRuleCall_1_2_2());
            	      				
            	    }

            	    }


            	    }
            	    break;
            	case 4 :
            	    // InternalMASL.g:7844:4: (this_PRIME_8= RULE_PRIME rulecharacteristic )
            	    {
            	    // InternalMASL.g:7844:4: (this_PRIME_8= RULE_PRIME rulecharacteristic )
            	    // InternalMASL.g:7845:5: this_PRIME_8= RULE_PRIME rulecharacteristic
            	    {
            	    this_PRIME_8=(Token)match(input,RULE_PRIME,FOLLOW_50); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					newLeafNode(this_PRIME_8, grammarAccess.getPostfixNoCallExpressionAccess().getPRIMETerminalRuleCall_1_3_0());
            	      				
            	    }
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getPostfixNoCallExpressionAccess().getCharacteristicParserRuleCall_1_3_1());
            	      				
            	    }
            	    pushFollow(FOLLOW_100);
            	    rulecharacteristic();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop115;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulepostfixNoCallExpression"


    // $ANTLR start "entryRuleprimaryExpression"
    // InternalMASL.g:7862:1: entryRuleprimaryExpression returns [EObject current=null] : iv_ruleprimaryExpression= ruleprimaryExpression EOF ;
    public final EObject entryRuleprimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleprimaryExpression = null;


        try {
            // InternalMASL.g:7862:58: (iv_ruleprimaryExpression= ruleprimaryExpression EOF )
            // InternalMASL.g:7863:2: iv_ruleprimaryExpression= ruleprimaryExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleprimaryExpression=ruleprimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleprimaryExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleprimaryExpression"


    // $ANTLR start "ruleprimaryExpression"
    // InternalMASL.g:7869:1: ruleprimaryExpression returns [EObject current=null] : ( ( () ruleliteral ) | this_parenthesisedExpression_2= ruleparenthesisedExpression | ( () rulenameExpression ) | this_typeNameExpression_5= ruletypeNameExpression ) ;
    public final EObject ruleprimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_parenthesisedExpression_2 = null;

        EObject this_typeNameExpression_5 = null;



        	enterRule();

        try {
            // InternalMASL.g:7875:2: ( ( ( () ruleliteral ) | this_parenthesisedExpression_2= ruleparenthesisedExpression | ( () rulenameExpression ) | this_typeNameExpression_5= ruletypeNameExpression ) )
            // InternalMASL.g:7876:2: ( ( () ruleliteral ) | this_parenthesisedExpression_2= ruleparenthesisedExpression | ( () rulenameExpression ) | this_typeNameExpression_5= ruletypeNameExpression )
            {
            // InternalMASL.g:7876:2: ( ( () ruleliteral ) | this_parenthesisedExpression_2= ruleparenthesisedExpression | ( () rulenameExpression ) | this_typeNameExpression_5= ruletypeNameExpression )
            int alt116=4;
            switch ( input.LA(1) ) {
            case RULE_NULL:
            case RULE_INTEGERLITERAL:
            case RULE_REALLITERAL:
            case RULE_STRINGLITERAL:
            case RULE_TIMESTAMPLITERAL:
            case RULE_DURATIONLITERAL:
            case RULE_TRUE:
            case RULE_FALSE:
            case RULE_FLUSH:
            case RULE_ENDL:
            case RULE_THIS:
            case RULE_CONSOLE:
            case RULE_LINE_NO:
            case RULE_FILE_NAME:
                {
                alt116=1;
                }
                break;
            case RULE_LPAREN:
                {
                alt116=2;
                }
                break;
            case RULE_IDENT:
                {
                alt116=3;
                }
                break;
            case RULE_INSTANCE:
            case RULE_ANONYMOUS:
            case RULE_SEQUENCE:
            case RULE_ARRAY:
            case RULE_SET:
            case RULE_BAG:
            case RULE_DICTIONARY:
                {
                alt116=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // InternalMASL.g:7877:3: ( () ruleliteral )
                    {
                    // InternalMASL.g:7877:3: ( () ruleliteral )
                    // InternalMASL.g:7878:4: () ruleliteral
                    {
                    // InternalMASL.g:7878:4: ()
                    // InternalMASL.g:7879:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getPrimaryExpressionAccess().getPrimaryExpressionAction_0_0(),
                      						current);
                      				
                    }

                    }

                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getLiteralParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    ruleliteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:7894:3: this_parenthesisedExpression_2= ruleparenthesisedExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getParenthesisedExpressionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_parenthesisedExpression_2=ruleparenthesisedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_parenthesisedExpression_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:7903:3: ( () rulenameExpression )
                    {
                    // InternalMASL.g:7903:3: ( () rulenameExpression )
                    // InternalMASL.g:7904:4: () rulenameExpression
                    {
                    // InternalMASL.g:7904:4: ()
                    // InternalMASL.g:7905:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getPrimaryExpressionAccess().getPrimaryExpressionAction_2_0(),
                      						current);
                      				
                    }

                    }

                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getNameExpressionParserRuleCall_2_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    rulenameExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalMASL.g:7920:3: this_typeNameExpression_5= ruletypeNameExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryExpressionAccess().getTypeNameExpressionParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_typeNameExpression_5=ruletypeNameExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_typeNameExpression_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleprimaryExpression"


    // $ANTLR start "entryRuletypeNameExpression"
    // InternalMASL.g:7932:1: entryRuletypeNameExpression returns [EObject current=null] : iv_ruletypeNameExpression= ruletypeNameExpression EOF ;
    public final EObject entryRuletypeNameExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruletypeNameExpression = null;


        try {
            // InternalMASL.g:7932:59: (iv_ruletypeNameExpression= ruletypeNameExpression EOF )
            // InternalMASL.g:7933:2: iv_ruletypeNameExpression= ruletypeNameExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeNameExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruletypeNameExpression=ruletypeNameExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruletypeNameExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuletypeNameExpression"


    // $ANTLR start "ruletypeNameExpression"
    // InternalMASL.g:7939:1: ruletypeNameExpression returns [EObject current=null] : ( ( () ruleinstanceTypeRef ) | this_collectionTypeRef_2= rulecollectionTypeRef ) ;
    public final EObject ruletypeNameExpression() throws RecognitionException {
        EObject current = null;

        EObject this_collectionTypeRef_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:7945:2: ( ( ( () ruleinstanceTypeRef ) | this_collectionTypeRef_2= rulecollectionTypeRef ) )
            // InternalMASL.g:7946:2: ( ( () ruleinstanceTypeRef ) | this_collectionTypeRef_2= rulecollectionTypeRef )
            {
            // InternalMASL.g:7946:2: ( ( () ruleinstanceTypeRef ) | this_collectionTypeRef_2= rulecollectionTypeRef )
            int alt117=2;
            switch ( input.LA(1) ) {
            case RULE_ANONYMOUS:
                {
                int LA117_1 = input.LA(2);

                if ( ((LA117_1>=RULE_SEQUENCE && LA117_1<=RULE_DICTIONARY)) ) {
                    alt117=2;
                }
                else if ( (LA117_1==RULE_INSTANCE) ) {
                    alt117=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 117, 1, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INSTANCE:
                {
                alt117=1;
                }
                break;
            case RULE_SEQUENCE:
            case RULE_ARRAY:
            case RULE_SET:
            case RULE_BAG:
            case RULE_DICTIONARY:
                {
                alt117=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }

            switch (alt117) {
                case 1 :
                    // InternalMASL.g:7947:3: ( () ruleinstanceTypeRef )
                    {
                    // InternalMASL.g:7947:3: ( () ruleinstanceTypeRef )
                    // InternalMASL.g:7948:4: () ruleinstanceTypeRef
                    {
                    // InternalMASL.g:7948:4: ()
                    // InternalMASL.g:7949:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeNameExpressionAccess().getTypeNameExpressionAction_0_0(),
                      						current);
                      				
                    }

                    }

                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getTypeNameExpressionAccess().getInstanceTypeRefParserRuleCall_0_1());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    ruleinstanceTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalMASL.g:7964:3: this_collectionTypeRef_2= rulecollectionTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeNameExpressionAccess().getCollectionTypeRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_collectionTypeRef_2=rulecollectionTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_collectionTypeRef_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruletypeNameExpression"


    // $ANTLR start "entryRulenameExpression"
    // InternalMASL.g:7976:1: entryRulenameExpression returns [String current=null] : iv_rulenameExpression= rulenameExpression EOF ;
    public final String entryRulenameExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulenameExpression = null;


        try {
            // InternalMASL.g:7976:54: (iv_rulenameExpression= rulenameExpression EOF )
            // InternalMASL.g:7977:2: iv_rulenameExpression= rulenameExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNameExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulenameExpression=rulenameExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulenameExpression.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulenameExpression"


    // $ANTLR start "rulenameExpression"
    // InternalMASL.g:7983:1: rulenameExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_identifier_2= ruleidentifier ) ;
    public final AntlrDatatypeRuleToken rulenameExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_SCOPE_1=null;
        AntlrDatatypeRuleToken this_domainName_0 = null;

        AntlrDatatypeRuleToken this_identifier_2 = null;



        	enterRule();

        try {
            // InternalMASL.g:7989:2: ( ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_identifier_2= ruleidentifier ) )
            // InternalMASL.g:7990:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_identifier_2= ruleidentifier )
            {
            // InternalMASL.g:7990:2: ( (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_identifier_2= ruleidentifier )
            // InternalMASL.g:7991:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )? this_identifier_2= ruleidentifier
            {
            // InternalMASL.g:7991:3: (this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==RULE_IDENT) ) {
                int LA118_1 = input.LA(2);

                if ( (LA118_1==RULE_SCOPE) ) {
                    alt118=1;
                }
            }
            switch (alt118) {
                case 1 :
                    // InternalMASL.g:7992:4: this_domainName_0= ruledomainName this_SCOPE_1= RULE_SCOPE
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getNameExpressionAccess().getDomainNameParserRuleCall_0_0());
                      			
                    }
                    pushFollow(FOLLOW_3);
                    this_domainName_0=ruledomainName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_domainName_0);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				afterParserOrEnumRuleCall();
                      			
                    }
                    this_SCOPE_1=(Token)match(input,RULE_SCOPE,FOLLOW_4); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(this_SCOPE_1);
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newLeafNode(this_SCOPE_1, grammarAccess.getNameExpressionAccess().getSCOPETerminalRuleCall_0_1());
                      			
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getNameExpressionAccess().getIdentifierParserRuleCall_1());
              		
            }
            pushFollow(FOLLOW_2);
            this_identifier_2=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_identifier_2);
              		
            }
            if ( state.backtracking==0 ) {

              			afterParserOrEnumRuleCall();
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulenameExpression"


    // $ANTLR start "entryRuleparenthesisedExpression"
    // InternalMASL.g:8024:1: entryRuleparenthesisedExpression returns [EObject current=null] : iv_ruleparenthesisedExpression= ruleparenthesisedExpression EOF ;
    public final EObject entryRuleparenthesisedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleparenthesisedExpression = null;


        try {
            // InternalMASL.g:8024:64: (iv_ruleparenthesisedExpression= ruleparenthesisedExpression EOF )
            // InternalMASL.g:8025:2: iv_ruleparenthesisedExpression= ruleparenthesisedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParenthesisedExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleparenthesisedExpression=ruleparenthesisedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleparenthesisedExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleparenthesisedExpression"


    // $ANTLR start "ruleparenthesisedExpression"
    // InternalMASL.g:8031:1: ruleparenthesisedExpression returns [EObject current=null] : (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e_3_0= ruleexpression ) ) )* this_RPAREN_4= RULE_RPAREN ) ;
    public final EObject ruleparenthesisedExpression() throws RecognitionException {
        EObject current = null;

        Token this_LPAREN_0=null;
        Token this_COMMA_2=null;
        Token this_RPAREN_4=null;
        EObject lv_e_1_0 = null;

        EObject lv_e_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:8037:2: ( (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e_3_0= ruleexpression ) ) )* this_RPAREN_4= RULE_RPAREN ) )
            // InternalMASL.g:8038:2: (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e_3_0= ruleexpression ) ) )* this_RPAREN_4= RULE_RPAREN )
            {
            // InternalMASL.g:8038:2: (this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e_3_0= ruleexpression ) ) )* this_RPAREN_4= RULE_RPAREN )
            // InternalMASL.g:8039:3: this_LPAREN_0= RULE_LPAREN ( (lv_e_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e_3_0= ruleexpression ) ) )* this_RPAREN_4= RULE_RPAREN
            {
            this_LPAREN_0=(Token)match(input,RULE_LPAREN,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_LPAREN_0, grammarAccess.getParenthesisedExpressionAccess().getLPARENTerminalRuleCall_0());
              		
            }
            // InternalMASL.g:8043:3: ( (lv_e_1_0= ruleexpression ) )
            // InternalMASL.g:8044:4: (lv_e_1_0= ruleexpression )
            {
            // InternalMASL.g:8044:4: (lv_e_1_0= ruleexpression )
            // InternalMASL.g:8045:5: lv_e_1_0= ruleexpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParenthesisedExpressionAccess().getEExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_23);
            lv_e_1_0=ruleexpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getParenthesisedExpressionRule());
              					}
              					add(
              						current,
              						"e",
              						lv_e_1_0,
              						"org.xtuml.bp.ui.xtext.MASL.expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalMASL.g:8062:3: (this_COMMA_2= RULE_COMMA ( (lv_e_3_0= ruleexpression ) ) )*
            loop119:
            do {
                int alt119=2;
                int LA119_0 = input.LA(1);

                if ( (LA119_0==RULE_COMMA) ) {
                    alt119=1;
                }


                switch (alt119) {
            	case 1 :
            	    // InternalMASL.g:8063:4: this_COMMA_2= RULE_COMMA ( (lv_e_3_0= ruleexpression ) )
            	    {
            	    this_COMMA_2=(Token)match(input,RULE_COMMA,FOLLOW_8); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(this_COMMA_2, grammarAccess.getParenthesisedExpressionAccess().getCOMMATerminalRuleCall_2_0());
            	      			
            	    }
            	    // InternalMASL.g:8067:4: ( (lv_e_3_0= ruleexpression ) )
            	    // InternalMASL.g:8068:5: (lv_e_3_0= ruleexpression )
            	    {
            	    // InternalMASL.g:8068:5: (lv_e_3_0= ruleexpression )
            	    // InternalMASL.g:8069:6: lv_e_3_0= ruleexpression
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getParenthesisedExpressionAccess().getEExpressionParserRuleCall_2_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_23);
            	    lv_e_3_0=ruleexpression();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getParenthesisedExpressionRule());
            	      						}
            	      						add(
            	      							current,
            	      							"e",
            	      							lv_e_3_0,
            	      							"org.xtuml.bp.ui.xtext.MASL.expression");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop119;
                }
            } while (true);

            this_RPAREN_4=(Token)match(input,RULE_RPAREN,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(this_RPAREN_4, grammarAccess.getParenthesisedExpressionAccess().getRPARENTerminalRuleCall_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleparenthesisedExpression"


    // $ANTLR start "entryRuleargumentList"
    // InternalMASL.g:8095:1: entryRuleargumentList returns [EObject current=null] : iv_ruleargumentList= ruleargumentList EOF ;
    public final EObject entryRuleargumentList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleargumentList = null;


        try {
            // InternalMASL.g:8095:53: (iv_ruleargumentList= ruleargumentList EOF )
            // InternalMASL.g:8096:2: iv_ruleargumentList= ruleargumentList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArgumentListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleargumentList=ruleargumentList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleargumentList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleargumentList"


    // $ANTLR start "ruleargumentList"
    // InternalMASL.g:8102:1: ruleargumentList returns [EObject current=null] : ( () ( ( (lv_e1_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )* )? ) ;
    public final EObject ruleargumentList() throws RecognitionException {
        EObject current = null;

        Token this_COMMA_2=null;
        EObject lv_e1_1_0 = null;

        EObject lv_e1_3_0 = null;



        	enterRule();

        try {
            // InternalMASL.g:8108:2: ( ( () ( ( (lv_e1_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )* )? ) )
            // InternalMASL.g:8109:2: ( () ( ( (lv_e1_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )* )? )
            {
            // InternalMASL.g:8109:2: ( () ( ( (lv_e1_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )* )? )
            // InternalMASL.g:8110:3: () ( ( (lv_e1_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )* )?
            {
            // InternalMASL.g:8110:3: ()
            // InternalMASL.g:8111:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getArgumentListAccess().getArgumentListAction_0(),
              					current);
              			
            }

            }

            // InternalMASL.g:8117:3: ( ( (lv_e1_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )* )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==RULE_INSTANCE||LA121_0==RULE_ANONYMOUS||LA121_0==RULE_LPAREN||(LA121_0>=RULE_SEQUENCE && LA121_0<=RULE_DICTIONARY)||LA121_0==RULE_NULL||(LA121_0>=RULE_LINK && LA121_0<=RULE_UNLINK)||LA121_0==RULE_NOT||(LA121_0>=RULE_PLUS && LA121_0<=RULE_MINUS)||LA121_0==RULE_ABS||LA121_0==RULE_CREATE||(LA121_0>=RULE_FIND && LA121_0<=RULE_IDENT)) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // InternalMASL.g:8118:4: ( (lv_e1_1_0= ruleexpression ) ) (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )*
                    {
                    // InternalMASL.g:8118:4: ( (lv_e1_1_0= ruleexpression ) )
                    // InternalMASL.g:8119:5: (lv_e1_1_0= ruleexpression )
                    {
                    // InternalMASL.g:8119:5: (lv_e1_1_0= ruleexpression )
                    // InternalMASL.g:8120:6: lv_e1_1_0= ruleexpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getArgumentListAccess().getE1ExpressionParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_101);
                    lv_e1_1_0=ruleexpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getArgumentListRule());
                      						}
                      						add(
                      							current,
                      							"e1",
                      							lv_e1_1_0,
                      							"org.xtuml.bp.ui.xtext.MASL.expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalMASL.g:8137:4: (this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) ) )*
                    loop120:
                    do {
                        int alt120=2;
                        int LA120_0 = input.LA(1);

                        if ( (LA120_0==RULE_COMMA) ) {
                            alt120=1;
                        }


                        switch (alt120) {
                    	case 1 :
                    	    // InternalMASL.g:8138:5: this_COMMA_2= RULE_COMMA ( (lv_e1_3_0= ruleexpression ) )
                    	    {
                    	    this_COMMA_2=(Token)match(input,RULE_COMMA,FOLLOW_8); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(this_COMMA_2, grammarAccess.getArgumentListAccess().getCOMMATerminalRuleCall_1_1_0());
                    	      				
                    	    }
                    	    // InternalMASL.g:8142:5: ( (lv_e1_3_0= ruleexpression ) )
                    	    // InternalMASL.g:8143:6: (lv_e1_3_0= ruleexpression )
                    	    {
                    	    // InternalMASL.g:8143:6: (lv_e1_3_0= ruleexpression )
                    	    // InternalMASL.g:8144:7: lv_e1_3_0= ruleexpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getArgumentListAccess().getE1ExpressionParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_101);
                    	    lv_e1_3_0=ruleexpression();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getArgumentListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"e1",
                    	      								lv_e1_3_0,
                    	      								"org.xtuml.bp.ui.xtext.MASL.expression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop120;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleargumentList"


    // $ANTLR start "entryRuleliteral"
    // InternalMASL.g:8167:1: entryRuleliteral returns [String current=null] : iv_ruleliteral= ruleliteral EOF ;
    public final String entryRuleliteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleliteral = null;


        try {
            // InternalMASL.g:8167:47: (iv_ruleliteral= ruleliteral EOF )
            // InternalMASL.g:8168:2: iv_ruleliteral= ruleliteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleliteral=ruleliteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleliteral.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleliteral"


    // $ANTLR start "ruleliteral"
    // InternalMASL.g:8174:1: ruleliteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INTEGERLITERAL_0= RULE_INTEGERLITERAL | this_REALLITERAL_1= RULE_REALLITERAL | this_STRINGLITERAL_2= RULE_STRINGLITERAL | this_TIMESTAMPLITERAL_3= RULE_TIMESTAMPLITERAL | this_DURATIONLITERAL_4= RULE_DURATIONLITERAL | this_TRUE_5= RULE_TRUE | this_FALSE_6= RULE_FALSE | this_NULL_7= RULE_NULL | this_FLUSH_8= RULE_FLUSH | this_ENDL_9= RULE_ENDL | this_THIS_10= RULE_THIS | this_CONSOLE_11= RULE_CONSOLE | this_LINE_NO_12= RULE_LINE_NO | this_FILE_NAME_13= RULE_FILE_NAME ) ;
    public final AntlrDatatypeRuleToken ruleliteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INTEGERLITERAL_0=null;
        Token this_REALLITERAL_1=null;
        Token this_STRINGLITERAL_2=null;
        Token this_TIMESTAMPLITERAL_3=null;
        Token this_DURATIONLITERAL_4=null;
        Token this_TRUE_5=null;
        Token this_FALSE_6=null;
        Token this_NULL_7=null;
        Token this_FLUSH_8=null;
        Token this_ENDL_9=null;
        Token this_THIS_10=null;
        Token this_CONSOLE_11=null;
        Token this_LINE_NO_12=null;
        Token this_FILE_NAME_13=null;


        	enterRule();

        try {
            // InternalMASL.g:8180:2: ( (this_INTEGERLITERAL_0= RULE_INTEGERLITERAL | this_REALLITERAL_1= RULE_REALLITERAL | this_STRINGLITERAL_2= RULE_STRINGLITERAL | this_TIMESTAMPLITERAL_3= RULE_TIMESTAMPLITERAL | this_DURATIONLITERAL_4= RULE_DURATIONLITERAL | this_TRUE_5= RULE_TRUE | this_FALSE_6= RULE_FALSE | this_NULL_7= RULE_NULL | this_FLUSH_8= RULE_FLUSH | this_ENDL_9= RULE_ENDL | this_THIS_10= RULE_THIS | this_CONSOLE_11= RULE_CONSOLE | this_LINE_NO_12= RULE_LINE_NO | this_FILE_NAME_13= RULE_FILE_NAME ) )
            // InternalMASL.g:8181:2: (this_INTEGERLITERAL_0= RULE_INTEGERLITERAL | this_REALLITERAL_1= RULE_REALLITERAL | this_STRINGLITERAL_2= RULE_STRINGLITERAL | this_TIMESTAMPLITERAL_3= RULE_TIMESTAMPLITERAL | this_DURATIONLITERAL_4= RULE_DURATIONLITERAL | this_TRUE_5= RULE_TRUE | this_FALSE_6= RULE_FALSE | this_NULL_7= RULE_NULL | this_FLUSH_8= RULE_FLUSH | this_ENDL_9= RULE_ENDL | this_THIS_10= RULE_THIS | this_CONSOLE_11= RULE_CONSOLE | this_LINE_NO_12= RULE_LINE_NO | this_FILE_NAME_13= RULE_FILE_NAME )
            {
            // InternalMASL.g:8181:2: (this_INTEGERLITERAL_0= RULE_INTEGERLITERAL | this_REALLITERAL_1= RULE_REALLITERAL | this_STRINGLITERAL_2= RULE_STRINGLITERAL | this_TIMESTAMPLITERAL_3= RULE_TIMESTAMPLITERAL | this_DURATIONLITERAL_4= RULE_DURATIONLITERAL | this_TRUE_5= RULE_TRUE | this_FALSE_6= RULE_FALSE | this_NULL_7= RULE_NULL | this_FLUSH_8= RULE_FLUSH | this_ENDL_9= RULE_ENDL | this_THIS_10= RULE_THIS | this_CONSOLE_11= RULE_CONSOLE | this_LINE_NO_12= RULE_LINE_NO | this_FILE_NAME_13= RULE_FILE_NAME )
            int alt122=14;
            switch ( input.LA(1) ) {
            case RULE_INTEGERLITERAL:
                {
                alt122=1;
                }
                break;
            case RULE_REALLITERAL:
                {
                alt122=2;
                }
                break;
            case RULE_STRINGLITERAL:
                {
                alt122=3;
                }
                break;
            case RULE_TIMESTAMPLITERAL:
                {
                alt122=4;
                }
                break;
            case RULE_DURATIONLITERAL:
                {
                alt122=5;
                }
                break;
            case RULE_TRUE:
                {
                alt122=6;
                }
                break;
            case RULE_FALSE:
                {
                alt122=7;
                }
                break;
            case RULE_NULL:
                {
                alt122=8;
                }
                break;
            case RULE_FLUSH:
                {
                alt122=9;
                }
                break;
            case RULE_ENDL:
                {
                alt122=10;
                }
                break;
            case RULE_THIS:
                {
                alt122=11;
                }
                break;
            case RULE_CONSOLE:
                {
                alt122=12;
                }
                break;
            case RULE_LINE_NO:
                {
                alt122=13;
                }
                break;
            case RULE_FILE_NAME:
                {
                alt122=14;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 122, 0, input);

                throw nvae;
            }

            switch (alt122) {
                case 1 :
                    // InternalMASL.g:8182:3: this_INTEGERLITERAL_0= RULE_INTEGERLITERAL
                    {
                    this_INTEGERLITERAL_0=(Token)match(input,RULE_INTEGERLITERAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_INTEGERLITERAL_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_INTEGERLITERAL_0, grammarAccess.getLiteralAccess().getINTEGERLITERALTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:8190:3: this_REALLITERAL_1= RULE_REALLITERAL
                    {
                    this_REALLITERAL_1=(Token)match(input,RULE_REALLITERAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_REALLITERAL_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_REALLITERAL_1, grammarAccess.getLiteralAccess().getREALLITERALTerminalRuleCall_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalMASL.g:8198:3: this_STRINGLITERAL_2= RULE_STRINGLITERAL
                    {
                    this_STRINGLITERAL_2=(Token)match(input,RULE_STRINGLITERAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_STRINGLITERAL_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_STRINGLITERAL_2, grammarAccess.getLiteralAccess().getSTRINGLITERALTerminalRuleCall_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalMASL.g:8206:3: this_TIMESTAMPLITERAL_3= RULE_TIMESTAMPLITERAL
                    {
                    this_TIMESTAMPLITERAL_3=(Token)match(input,RULE_TIMESTAMPLITERAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_TIMESTAMPLITERAL_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_TIMESTAMPLITERAL_3, grammarAccess.getLiteralAccess().getTIMESTAMPLITERALTerminalRuleCall_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalMASL.g:8214:3: this_DURATIONLITERAL_4= RULE_DURATIONLITERAL
                    {
                    this_DURATIONLITERAL_4=(Token)match(input,RULE_DURATIONLITERAL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_DURATIONLITERAL_4);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_DURATIONLITERAL_4, grammarAccess.getLiteralAccess().getDURATIONLITERALTerminalRuleCall_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalMASL.g:8222:3: this_TRUE_5= RULE_TRUE
                    {
                    this_TRUE_5=(Token)match(input,RULE_TRUE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_TRUE_5);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_TRUE_5, grammarAccess.getLiteralAccess().getTRUETerminalRuleCall_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalMASL.g:8230:3: this_FALSE_6= RULE_FALSE
                    {
                    this_FALSE_6=(Token)match(input,RULE_FALSE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_FALSE_6);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_FALSE_6, grammarAccess.getLiteralAccess().getFALSETerminalRuleCall_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalMASL.g:8238:3: this_NULL_7= RULE_NULL
                    {
                    this_NULL_7=(Token)match(input,RULE_NULL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_NULL_7);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_NULL_7, grammarAccess.getLiteralAccess().getNULLTerminalRuleCall_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalMASL.g:8246:3: this_FLUSH_8= RULE_FLUSH
                    {
                    this_FLUSH_8=(Token)match(input,RULE_FLUSH,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_FLUSH_8);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_FLUSH_8, grammarAccess.getLiteralAccess().getFLUSHTerminalRuleCall_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalMASL.g:8254:3: this_ENDL_9= RULE_ENDL
                    {
                    this_ENDL_9=(Token)match(input,RULE_ENDL,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ENDL_9);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ENDL_9, grammarAccess.getLiteralAccess().getENDLTerminalRuleCall_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalMASL.g:8262:3: this_THIS_10= RULE_THIS
                    {
                    this_THIS_10=(Token)match(input,RULE_THIS,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_THIS_10);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_THIS_10, grammarAccess.getLiteralAccess().getTHISTerminalRuleCall_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalMASL.g:8270:3: this_CONSOLE_11= RULE_CONSOLE
                    {
                    this_CONSOLE_11=(Token)match(input,RULE_CONSOLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_CONSOLE_11);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_CONSOLE_11, grammarAccess.getLiteralAccess().getCONSOLETerminalRuleCall_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalMASL.g:8278:3: this_LINE_NO_12= RULE_LINE_NO
                    {
                    this_LINE_NO_12=(Token)match(input,RULE_LINE_NO,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_LINE_NO_12);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_LINE_NO_12, grammarAccess.getLiteralAccess().getLINE_NOTerminalRuleCall_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalMASL.g:8286:3: this_FILE_NAME_13= RULE_FILE_NAME
                    {
                    this_FILE_NAME_13=(Token)match(input,RULE_FILE_NAME,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_FILE_NAME_13);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_FILE_NAME_13, grammarAccess.getLiteralAccess().getFILE_NAMETerminalRuleCall_13());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleliteral"


    // $ANTLR start "entryRuleidentifier"
    // InternalMASL.g:8297:1: entryRuleidentifier returns [String current=null] : iv_ruleidentifier= ruleidentifier EOF ;
    public final String entryRuleidentifier() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleidentifier = null;


        try {
            // InternalMASL.g:8297:50: (iv_ruleidentifier= ruleidentifier EOF )
            // InternalMASL.g:8298:2: iv_ruleidentifier= ruleidentifier EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIdentifierRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleidentifier=ruleidentifier();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleidentifier.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleidentifier"


    // $ANTLR start "ruleidentifier"
    // InternalMASL.g:8304:1: ruleidentifier returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_IDENT_0= RULE_IDENT ;
    public final AntlrDatatypeRuleToken ruleidentifier() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_IDENT_0=null;


        	enterRule();

        try {
            // InternalMASL.g:8310:2: (this_IDENT_0= RULE_IDENT )
            // InternalMASL.g:8311:2: this_IDENT_0= RULE_IDENT
            {
            this_IDENT_0=(Token)match(input,RULE_IDENT,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(this_IDENT_0);
              	
            }
            if ( state.backtracking==0 ) {

              		newLeafNode(this_IDENT_0, grammarAccess.getIdentifierAccess().getIDENTTerminalRuleCall());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleidentifier"


    // $ANTLR start "entryRulecharacteristic"
    // InternalMASL.g:8321:1: entryRulecharacteristic returns [String current=null] : iv_rulecharacteristic= rulecharacteristic EOF ;
    public final String entryRulecharacteristic() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulecharacteristic = null;


        try {
            // InternalMASL.g:8321:54: (iv_rulecharacteristic= rulecharacteristic EOF )
            // InternalMASL.g:8322:2: iv_rulecharacteristic= rulecharacteristic EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCharacteristicRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecharacteristic=rulecharacteristic();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecharacteristic.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecharacteristic"


    // $ANTLR start "rulecharacteristic"
    // InternalMASL.g:8328:1: rulecharacteristic returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IDENT_0= RULE_IDENT | this_characteristicRW_1= rulecharacteristicRW ) ;
    public final AntlrDatatypeRuleToken rulecharacteristic() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_IDENT_0=null;
        AntlrDatatypeRuleToken this_characteristicRW_1 = null;



        	enterRule();

        try {
            // InternalMASL.g:8334:2: ( (this_IDENT_0= RULE_IDENT | this_characteristicRW_1= rulecharacteristicRW ) )
            // InternalMASL.g:8335:2: (this_IDENT_0= RULE_IDENT | this_characteristicRW_1= rulecharacteristicRW )
            {
            // InternalMASL.g:8335:2: (this_IDENT_0= RULE_IDENT | this_characteristicRW_1= rulecharacteristicRW )
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( (LA123_0==RULE_IDENT) ) {
                alt123=1;
            }
            else if ( (LA123_0==RULE_DELTA||LA123_0==RULE_RANGE) ) {
                alt123=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 123, 0, input);

                throw nvae;
            }
            switch (alt123) {
                case 1 :
                    // InternalMASL.g:8336:3: this_IDENT_0= RULE_IDENT
                    {
                    this_IDENT_0=(Token)match(input,RULE_IDENT,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_IDENT_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_IDENT_0, grammarAccess.getCharacteristicAccess().getIDENTTerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:8344:3: this_characteristicRW_1= rulecharacteristicRW
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getCharacteristicAccess().getCharacteristicRWParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_characteristicRW_1=rulecharacteristicRW();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_characteristicRW_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecharacteristic"


    // $ANTLR start "entryRulecharacteristicRW"
    // InternalMASL.g:8358:1: entryRulecharacteristicRW returns [String current=null] : iv_rulecharacteristicRW= rulecharacteristicRW EOF ;
    public final String entryRulecharacteristicRW() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulecharacteristicRW = null;


        try {
            // InternalMASL.g:8358:56: (iv_rulecharacteristicRW= rulecharacteristicRW EOF )
            // InternalMASL.g:8359:2: iv_rulecharacteristicRW= rulecharacteristicRW EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCharacteristicRWRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulecharacteristicRW=rulecharacteristicRW();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulecharacteristicRW.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecharacteristicRW"


    // $ANTLR start "rulecharacteristicRW"
    // InternalMASL.g:8365:1: rulecharacteristicRW returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_RANGE_0= RULE_RANGE | this_DELTA_1= RULE_DELTA ) ;
    public final AntlrDatatypeRuleToken rulecharacteristicRW() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_RANGE_0=null;
        Token this_DELTA_1=null;


        	enterRule();

        try {
            // InternalMASL.g:8371:2: ( (this_RANGE_0= RULE_RANGE | this_DELTA_1= RULE_DELTA ) )
            // InternalMASL.g:8372:2: (this_RANGE_0= RULE_RANGE | this_DELTA_1= RULE_DELTA )
            {
            // InternalMASL.g:8372:2: (this_RANGE_0= RULE_RANGE | this_DELTA_1= RULE_DELTA )
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==RULE_RANGE) ) {
                alt124=1;
            }
            else if ( (LA124_0==RULE_DELTA) ) {
                alt124=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }
            switch (alt124) {
                case 1 :
                    // InternalMASL.g:8373:3: this_RANGE_0= RULE_RANGE
                    {
                    this_RANGE_0=(Token)match(input,RULE_RANGE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_RANGE_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_RANGE_0, grammarAccess.getCharacteristicRWAccess().getRANGETerminalRuleCall_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalMASL.g:8381:3: this_DELTA_1= RULE_DELTA
                    {
                    this_DELTA_1=(Token)match(input,RULE_DELTA,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_DELTA_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_DELTA_1, grammarAccess.getCharacteristicRWAccess().getDELTATerminalRuleCall_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecharacteristicRW"

    // $ANTLR start synpred1_InternalMASL
    public final void synpred1_InternalMASL_fragment() throws RecognitionException {   
        // InternalMASL.g:3017:5: ( ( ( ruleassignStatement ) ) )
        // InternalMASL.g:3017:6: ( ( ruleassignStatement ) )
        {
        // InternalMASL.g:3017:6: ( ( ruleassignStatement ) )
        // InternalMASL.g:3018:6: ( ruleassignStatement )
        {
        // InternalMASL.g:3018:6: ( ruleassignStatement )
        // InternalMASL.g:3019:7: ruleassignStatement
        {
        pushFollow(FOLLOW_2);
        ruleassignStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred1_InternalMASL

    // $ANTLR start synpred2_InternalMASL
    public final void synpred2_InternalMASL_fragment() throws RecognitionException {   
        // InternalMASL.g:3045:5: ( ( ( rulestreamStatement ) ) )
        // InternalMASL.g:3045:6: ( ( rulestreamStatement ) )
        {
        // InternalMASL.g:3045:6: ( ( rulestreamStatement ) )
        // InternalMASL.g:3046:6: ( rulestreamStatement )
        {
        // InternalMASL.g:3046:6: ( rulestreamStatement )
        // InternalMASL.g:3047:7: rulestreamStatement
        {
        pushFollow(FOLLOW_2);
        rulestreamStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred2_InternalMASL

    // $ANTLR start synpred3_InternalMASL
    public final void synpred3_InternalMASL_fragment() throws RecognitionException {   
        // InternalMASL.g:3073:5: ( ( ( rulenullStatement ) ) )
        // InternalMASL.g:3073:6: ( ( rulenullStatement ) )
        {
        // InternalMASL.g:3073:6: ( ( rulenullStatement ) )
        // InternalMASL.g:3074:6: ( rulenullStatement )
        {
        // InternalMASL.g:3074:6: ( rulenullStatement )
        // InternalMASL.g:3075:7: rulenullStatement
        {
        pushFollow(FOLLOW_2);
        rulenullStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred3_InternalMASL

    // $ANTLR start synpred4_InternalMASL
    public final void synpred4_InternalMASL_fragment() throws RecognitionException {   
        // InternalMASL.g:3101:5: ( ( ( rulecallStatement ) ) )
        // InternalMASL.g:3101:6: ( ( rulecallStatement ) )
        {
        // InternalMASL.g:3101:6: ( ( rulecallStatement ) )
        // InternalMASL.g:3102:6: ( rulecallStatement )
        {
        // InternalMASL.g:3102:6: ( rulecallStatement )
        // InternalMASL.g:3103:7: rulecallStatement
        {
        pushFollow(FOLLOW_2);
        rulecallStatement();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }
    }
    // $ANTLR end synpred4_InternalMASL

    // Delegated rules

    public final boolean synpred2_InternalMASL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalMASL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalMASL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalMASL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalMASL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalMASL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalMASL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalMASL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA44 dfa44 = new DFA44(this);
    protected DFA114 dfa114 = new DFA114(this);
    static final String dfa_1s = "\23\uffff";
    static final String dfa_2s = "\3\4\1\6\2\175\3\uffff\2\7\2\175\2\12\4\uffff";
    static final String dfa_3s = "\1\44\3\41\2\175\3\uffff\2\7\2\175\2\43\4\uffff";
    static final String dfa_4s = "\6\uffff\1\3\1\1\1\2\6\uffff\1\6\1\4\1\7\1\5";
    static final String dfa_5s = "\23\uffff}>";
    static final String[] dfa_6s = {
            "\1\3\1\uffff\1\4\15\uffff\3\6\3\uffff\1\1\1\2\5\uffff\1\5\2\uffff\1\6",
            "\1\3\1\uffff\1\4\32\uffff\1\5",
            "\1\3\1\uffff\1\4\32\uffff\1\5",
            "\1\7\14\uffff\1\10\15\uffff\1\10",
            "\1\11",
            "\1\12",
            "",
            "",
            "",
            "\1\13",
            "\1\14",
            "\1\15",
            "\1\16",
            "\1\20\7\uffff\1\7\20\uffff\1\17",
            "\1\22\7\uffff\1\10\20\uffff\1\21",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "78:2: (this_objectServiceDefinition_0= ruleobjectServiceDefinition | this_objectFunctionDefinition_1= ruleobjectFunctionDefinition | this_stateDefinition_2= rulestateDefinition | this_domainServiceDefinition_3= ruledomainServiceDefinition | this_domainFunctionDefinition_4= ruledomainFunctionDefinition | this_terminatorServiceDefinition_5= ruleterminatorServiceDefinition | this_terminatorFunctionDefinition_6= ruleterminatorFunctionDefinition )";
        }
    }
    static final String dfa_7s = "\67\uffff";
    static final String dfa_8s = "\1\4\2\uffff\41\0\23\uffff";
    static final String dfa_9s = "\1\175\2\uffff\41\0\23\uffff";
    static final String dfa_10s = "\1\uffff\1\1\42\uffff\1\6\1\7\1\10\1\11\1\12\1\13\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\2\1\3\1\5\1\4\1\14";
    static final String dfa_11s = "\3\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\23\uffff}>";
    static final String[] dfa_12s = {
            "\1\30\3\uffff\1\27\1\uffff\1\25\1\uffff\1\31\1\32\1\33\1\34\1\35\15\uffff\1\61\3\uffff\1\45\2\uffff\1\16\10\uffff\1\44\1\uffff\1\46\1\47\1\50\1\51\1\uffff\1\42\1\43\1\52\1\uffff\1\53\1\uffff\1\54\1\uffff\1\55\4\uffff\1\60\1\uffff\1\56\3\uffff\1\57\1\uffff\2\1\5\uffff\1\5\7\uffff\1\4\1\3\12\uffff\1\6\4\uffff\1\36\2\uffff\1\37\1\40\1\41\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\17\1\20\1\21\1\22\1\23\1\24\1\26",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[][] dfa_12 = unpackEncodedStringArray(dfa_12s);

    class DFA44 extends DFA {

        public DFA44(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 44;
            this.eot = dfa_7;
            this.eof = dfa_7;
            this.min = dfa_8;
            this.max = dfa_9;
            this.accept = dfa_10;
            this.special = dfa_11;
            this.transition = dfa_12;
        }
        public String getDescription() {
            return "2995:3: ( ( (lv_c_1_0= rulecodeBlockStatement ) ) | ( ( ( ( ruleassignStatement ) ) )=> ( (lv_e_2_0= ruleassignStatement ) ) ) | ( ( ( ( rulestreamStatement ) ) )=> ( (lv_s_3_0= rulestreamStatement ) ) ) | ( ( ( ( rulenullStatement ) ) )=> ( (lv_n_4_0= rulenullStatement ) ) ) | ( ( ( ( rulecallStatement ) ) )=> ( (lv_c_5_0= rulecallStatement ) ) ) | ( (lv_e_6_0= ruleexitStatement ) ) | ( (lv_r_7_0= rulereturnStatement ) ) | ( (lv_d_8_0= ruledelayStatement ) ) | ruleraiseStatement | ( (lv_d_10_0= ruledeleteStatement ) ) | ( (lv_e_11_0= ruleeraseStatement ) ) | ( (lv_l_12_0= rulelinkStatement ) ) | ( (lv_s_13_0= rulescheduleStatement ) ) | ( (lv_c_14_0= rulecancelTimerStatement ) ) | ( (lv_g_15_0= rulegenerateStatement ) ) | ( (lv_i_16_0= ruleifStatement ) ) | ( (lv_c_17_0= rulecaseStatement ) ) | ( (lv_f_18_0= ruleforStatement ) ) | ( (lv_w_19_0= rulewhileStatement ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA44_3 = input.LA(1);

                         
                        int index44_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA44_4 = input.LA(1);

                         
                        int index44_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA44_5 = input.LA(1);

                         
                        int index44_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA44_6 = input.LA(1);

                         
                        int index44_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA44_7 = input.LA(1);

                         
                        int index44_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA44_8 = input.LA(1);

                         
                        int index44_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA44_9 = input.LA(1);

                         
                        int index44_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA44_10 = input.LA(1);

                         
                        int index44_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA44_11 = input.LA(1);

                         
                        int index44_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA44_12 = input.LA(1);

                         
                        int index44_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_12);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA44_13 = input.LA(1);

                         
                        int index44_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_13);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA44_14 = input.LA(1);

                         
                        int index44_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred3_InternalMASL()) ) {s = 53;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_14);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA44_15 = input.LA(1);

                         
                        int index44_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_15);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA44_16 = input.LA(1);

                         
                        int index44_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_16);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA44_17 = input.LA(1);

                         
                        int index44_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_17);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA44_18 = input.LA(1);

                         
                        int index44_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_18);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA44_19 = input.LA(1);

                         
                        int index44_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_19);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA44_20 = input.LA(1);

                         
                        int index44_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_20);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA44_21 = input.LA(1);

                         
                        int index44_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_21);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA44_22 = input.LA(1);

                         
                        int index44_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_22);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA44_23 = input.LA(1);

                         
                        int index44_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_23);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA44_24 = input.LA(1);

                         
                        int index44_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_24);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA44_25 = input.LA(1);

                         
                        int index44_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_25);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA44_26 = input.LA(1);

                         
                        int index44_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_26);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA44_27 = input.LA(1);

                         
                        int index44_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_27);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA44_28 = input.LA(1);

                         
                        int index44_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_28);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA44_29 = input.LA(1);

                         
                        int index44_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (synpred4_InternalMASL()) ) {s = 52;}

                         
                        input.seek(index44_29);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA44_30 = input.LA(1);

                         
                        int index44_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_30);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA44_31 = input.LA(1);

                         
                        int index44_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_31);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA44_32 = input.LA(1);

                         
                        int index44_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_32);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA44_33 = input.LA(1);

                         
                        int index44_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                         
                        input.seek(index44_33);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA44_34 = input.LA(1);

                         
                        int index44_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index44_34);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA44_35 = input.LA(1);

                         
                        int index44_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_InternalMASL()) ) {s = 50;}

                        else if ( (synpred2_InternalMASL()) ) {s = 51;}

                        else if ( (true) ) {s = 54;}

                         
                        input.seek(index44_35);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 44, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_13s = "\15\uffff";
    static final String dfa_14s = "\1\1\6\uffff\3\13\3\uffff";
    static final String dfa_15s = "\1\12\5\uffff\1\70\3\12\1\4\2\uffff";
    static final String dfa_16s = "\1\151\5\uffff\1\176\3\151\1\175\2\uffff";
    static final String dfa_17s = "\1\uffff\1\7\1\1\1\2\1\3\1\4\5\uffff\1\6\1\5";
    static final String dfa_18s = "\15\uffff}>";
    static final String[] dfa_19s = {
            "\1\2\1\1\5\uffff\1\1\1\3\5\uffff\1\1\5\uffff\1\1\1\uffff\1\1\2\uffff\1\4\2\uffff\5\1\1\5\1\1\1\6\2\uffff\1\1\3\uffff\1\1\3\uffff\1\1\1\uffff\2\1\2\uffff\1\1\4\uffff\1\1\1\uffff\2\1\7\uffff\3\1\1\uffff\23\1\1\uffff\4\1",
            "",
            "",
            "",
            "",
            "",
            "\1\11\104\uffff\1\7\1\10",
            "\1\12\1\13\5\uffff\2\13\5\uffff\1\13\5\uffff\1\13\1\uffff\1\13\2\uffff\1\13\2\uffff\10\13\2\uffff\1\13\3\uffff\1\13\3\uffff\1\13\1\uffff\2\13\2\uffff\1\13\4\uffff\1\13\1\uffff\2\13\7\uffff\3\13\1\uffff\23\13\1\uffff\4\13",
            "\1\12\1\13\5\uffff\2\13\5\uffff\1\13\5\uffff\1\13\1\uffff\1\13\2\uffff\1\13\2\uffff\10\13\2\uffff\1\13\3\uffff\1\13\3\uffff\1\13\1\uffff\2\13\2\uffff\1\13\4\uffff\1\13\1\uffff\2\13\7\uffff\3\13\1\uffff\23\13\1\uffff\4\13",
            "\1\12\1\13\5\uffff\2\13\5\uffff\1\13\5\uffff\1\13\1\uffff\1\13\2\uffff\1\13\2\uffff\10\13\2\uffff\1\13\3\uffff\1\13\3\uffff\1\13\1\uffff\2\13\2\uffff\1\13\4\uffff\1\13\1\uffff\2\13\7\uffff\3\13\1\uffff\23\13\1\uffff\4\13",
            "\1\13\3\uffff\1\13\1\uffff\7\13\24\uffff\1\13\7\uffff\1\14\7\uffff\2\13\32\uffff\1\13\7\uffff\2\13\12\uffff\1\13\4\uffff\1\13\2\uffff\21\13",
            "",
            ""
    };

    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA114 extends DFA {

        public DFA114(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 114;
            this.eot = dfa_13;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "()* loopback of 7583:3: ( (this_LPAREN_1= RULE_LPAREN ( (lv_a_2_0= ruleargumentList ) ) this_RPAREN_3= RULE_RPAREN ) | (this_DOT_4= RULE_DOT ruleidentifier ) | (this_TERMINATOR_SCOPE_6= RULE_TERMINATOR_SCOPE ruleidentifier ) | (this_LBRACKET_8= RULE_LBRACKET ( (lv_e_9_0= ruleexpression ) ) this_RBRACKET_10= RULE_RBRACKET ) | ( (this_PRIME_11= RULE_PRIME rulecharacteristic this_LPAREN_13= RULE_LPAREN ) ( ( RULE_PRIME )=>this_PRIME_14= RULE_PRIME ) rulecharacteristic this_LPAREN_16= RULE_LPAREN ( (lv_a_17_0= ruleargumentList ) ) this_RPAREN_18= RULE_RPAREN ) | (this_PRIME_19= RULE_PRIME rulecharacteristic ) )*";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x006000200001F510L,0x3FFFE42006020000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x000000200001F570L,0x3FFF000000000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000312L,0x2000000000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000001000800L,0x2000000000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000001000800L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000030000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000002000000800L,0x3FFF000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000002000000000L,0x3FFF000000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000800L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000040000040L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000240000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000200000010L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000001040000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x2AEF40244001F512L,0x3FFFE42006020D14L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000078000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000078000000002L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000280800040400L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0100000000000000L,0x6000000000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x006000200001FD10L,0x3FFFE42006020000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000280800040402L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x000000200001F510L,0x3FFFE40000000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x000000200001F512L,0x3FFFE40000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0401000000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x2AEF40244001F510L,0x3FFFE42006020D14L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x8000800000000000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x006000200001F510L,0x3FFFE42006020200L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x8000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x000000200001F570L,0x3FFF000000002000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000004040000000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000400L,0x2000000000020000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000000L,0x0000000000FC0000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000080000040002L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000002L,0x00000000000C0000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000000002L,0x0000000000F00000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000002L,0x000000003E000000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000000002L,0x0000001FC0000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000000002L,0x000003C000000000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000000402L,0x000003C000000000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000800L,0x2000000000000200L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000200L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000000000L,0x2000080000000000L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0000000000000800L,0x2000100000000000L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000000000L,0x2000100000000000L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x000000200001F510L,0x3FFF000000000000L});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x0000000000000C00L,0x2000000000020000L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_100 = new BitSet(new long[]{0x0000280800040002L});
    public static final BitSet FOLLOW_101 = new BitSet(new long[]{0x0000000001000002L});

}