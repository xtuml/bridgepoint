package org.argouml.xtuml.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.argouml.xtuml.services.OALGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalOALParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_RELATION_NAME", "RULE_STRING", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "';'", "'create'", "'object'", "'of'", "'select'", "'any'", "'many'", "'one'", "'from'", "'instances'", "'where'", "'related'", "'by'", "'->'", "'['", "']'", "'relate'", "'to'", "'across'", "'unrelate'", "'delete'", "'instance'", "'self'", "'.'", "'assign'", "'='", "'if'", "'then'", "'elif'", "'else'", "'end'", "'for'", "'do'", "'while'", "'not'", "'=='", "'<>'", "'<'", "'>'", "'>='", "'<='", "'empty'", "'not_empty'", "'+'", "'-'", "'*'", "'/'", "'%'", "'('", "')'", "'true'", "'false'"
    };
    public static final int RULE_ID=7;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__62=62;
    public static final int T__27=27;
    public static final int T__63=63;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=11;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int EOF=-1;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__19=19;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__16=16;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__15=15;
    public static final int T__53=53;
    public static final int T__18=18;
    public static final int T__54=54;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__59=59;
    public static final int RULE_INT=6;
    public static final int RULE_RELATION_NAME=4;
    public static final int T__50=50;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int RULE_SL_COMMENT=9;
    public static final int RULE_ML_COMMENT=8;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_STRING=5;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int RULE_WS=10;

    // delegates
    // delegators


        public InternalOALParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalOALParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalOALParser.tokenNames; }
    public String getGrammarFileName() { return "../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g"; }



     	private OALGrammarAccess grammarAccess;
     	
        public InternalOALParser(TokenStream input, OALGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Code";	
       	}
       	
       	@Override
       	protected OALGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleCode"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:67:1: entryRuleCode returns [EObject current=null] : iv_ruleCode= ruleCode EOF ;
    public final EObject entryRuleCode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCode = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:68:2: (iv_ruleCode= ruleCode EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:69:2: iv_ruleCode= ruleCode EOF
            {
             newCompositeNode(grammarAccess.getCodeRule()); 
            pushFollow(FOLLOW_ruleCode_in_entryRuleCode75);
            iv_ruleCode=ruleCode();

            state._fsp--;

             current =iv_ruleCode; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleCode85); 

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
    // $ANTLR end "entryRuleCode"


    // $ANTLR start "ruleCode"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:76:1: ruleCode returns [EObject current=null] : ( (lv_statements_0_0= rulestatement ) )* ;
    public final EObject ruleCode() throws RecognitionException {
        EObject current = null;

        EObject lv_statements_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:79:28: ( ( (lv_statements_0_0= rulestatement ) )* )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:80:1: ( (lv_statements_0_0= rulestatement ) )*
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:80:1: ( (lv_statements_0_0= rulestatement ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_ID||LA1_0==13||LA1_0==16||LA1_0==28||(LA1_0>=31 && LA1_0<=32)||LA1_0==34||LA1_0==36||LA1_0==38||LA1_0==43||LA1_0==45) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:81:1: (lv_statements_0_0= rulestatement )
            	    {
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:81:1: (lv_statements_0_0= rulestatement )
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:82:3: lv_statements_0_0= rulestatement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getCodeAccess().getStatementsStatementParserRuleCall_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulestatement_in_ruleCode130);
            	    lv_statements_0_0=rulestatement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getCodeRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"statements",
            	            		lv_statements_0_0, 
            	            		"statement");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCode"


    // $ANTLR start "entryRulestatement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:106:1: entryRulestatement returns [EObject current=null] : iv_rulestatement= rulestatement EOF ;
    public final EObject entryRulestatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestatement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:107:2: (iv_rulestatement= rulestatement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:108:2: iv_rulestatement= rulestatement EOF
            {
             newCompositeNode(grammarAccess.getStatementRule()); 
            pushFollow(FOLLOW_rulestatement_in_entryRulestatement166);
            iv_rulestatement=rulestatement();

            state._fsp--;

             current =iv_rulestatement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulestatement176); 

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
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:115:1: rulestatement returns [EObject current=null] : ( ( ( (lv_st1_0_0= ruleassignment ) ) | ( (lv_st2_1_0= ruleobject_statement ) ) | ( (lv_st3_2_0= ruleflow_control_statement ) ) ) otherlv_3= ';' ) ;
    public final EObject rulestatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        EObject lv_st1_0_0 = null;

        EObject lv_st2_1_0 = null;

        EObject lv_st3_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:118:28: ( ( ( ( (lv_st1_0_0= ruleassignment ) ) | ( (lv_st2_1_0= ruleobject_statement ) ) | ( (lv_st3_2_0= ruleflow_control_statement ) ) ) otherlv_3= ';' ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:119:1: ( ( ( (lv_st1_0_0= ruleassignment ) ) | ( (lv_st2_1_0= ruleobject_statement ) ) | ( (lv_st3_2_0= ruleflow_control_statement ) ) ) otherlv_3= ';' )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:119:1: ( ( ( (lv_st1_0_0= ruleassignment ) ) | ( (lv_st2_1_0= ruleobject_statement ) ) | ( (lv_st3_2_0= ruleflow_control_statement ) ) ) otherlv_3= ';' )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:119:2: ( ( (lv_st1_0_0= ruleassignment ) ) | ( (lv_st2_1_0= ruleobject_statement ) ) | ( (lv_st3_2_0= ruleflow_control_statement ) ) ) otherlv_3= ';'
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:119:2: ( ( (lv_st1_0_0= ruleassignment ) ) | ( (lv_st2_1_0= ruleobject_statement ) ) | ( (lv_st3_2_0= ruleflow_control_statement ) ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
            case 34:
            case 36:
                {
                alt2=1;
                }
                break;
            case 13:
            case 16:
            case 28:
            case 31:
            case 32:
                {
                alt2=2;
                }
                break;
            case 38:
            case 43:
            case 45:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:119:3: ( (lv_st1_0_0= ruleassignment ) )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:119:3: ( (lv_st1_0_0= ruleassignment ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:120:1: (lv_st1_0_0= ruleassignment )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:120:1: (lv_st1_0_0= ruleassignment )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:121:3: lv_st1_0_0= ruleassignment
                    {
                     
                    	        newCompositeNode(grammarAccess.getStatementAccess().getSt1AssignmentParserRuleCall_0_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleassignment_in_rulestatement223);
                    lv_st1_0_0=ruleassignment();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getStatementRule());
                    	        }
                           		set(
                           			current, 
                           			"st1",
                            		lv_st1_0_0, 
                            		"assignment");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:138:6: ( (lv_st2_1_0= ruleobject_statement ) )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:138:6: ( (lv_st2_1_0= ruleobject_statement ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:139:1: (lv_st2_1_0= ruleobject_statement )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:139:1: (lv_st2_1_0= ruleobject_statement )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:140:3: lv_st2_1_0= ruleobject_statement
                    {
                     
                    	        newCompositeNode(grammarAccess.getStatementAccess().getSt2Object_statementParserRuleCall_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleobject_statement_in_rulestatement250);
                    lv_st2_1_0=ruleobject_statement();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getStatementRule());
                    	        }
                           		set(
                           			current, 
                           			"st2",
                            		lv_st2_1_0, 
                            		"object_statement");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:157:6: ( (lv_st3_2_0= ruleflow_control_statement ) )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:157:6: ( (lv_st3_2_0= ruleflow_control_statement ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:158:1: (lv_st3_2_0= ruleflow_control_statement )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:158:1: (lv_st3_2_0= ruleflow_control_statement )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:159:3: lv_st3_2_0= ruleflow_control_statement
                    {
                     
                    	        newCompositeNode(grammarAccess.getStatementAccess().getSt3Flow_control_statementParserRuleCall_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleflow_control_statement_in_rulestatement277);
                    lv_st3_2_0=ruleflow_control_statement();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getStatementRule());
                    	        }
                           		set(
                           			current, 
                           			"st3",
                            		lv_st3_2_0, 
                            		"flow_control_statement");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,12,FOLLOW_12_in_rulestatement290); 

                	newLeafNode(otherlv_3, grammarAccess.getStatementAccess().getSemicolonKeyword_1());
                

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleobject_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:187:1: entryRuleobject_statement returns [EObject current=null] : iv_ruleobject_statement= ruleobject_statement EOF ;
    public final EObject entryRuleobject_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleobject_statement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:188:2: (iv_ruleobject_statement= ruleobject_statement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:189:2: iv_ruleobject_statement= ruleobject_statement EOF
            {
             newCompositeNode(grammarAccess.getObject_statementRule()); 
            pushFollow(FOLLOW_ruleobject_statement_in_entryRuleobject_statement326);
            iv_ruleobject_statement=ruleobject_statement();

            state._fsp--;

             current =iv_ruleobject_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleobject_statement336); 

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
    // $ANTLR end "entryRuleobject_statement"


    // $ANTLR start "ruleobject_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:196:1: ruleobject_statement returns [EObject current=null] : ( ( rulecreate_statement () ) | this_select_statement_2= ruleselect_statement | ( rulerelate_statement () ) | ( ruleunrelate_statement () ) | ( ruledelete_statement () ) ) ;
    public final EObject ruleobject_statement() throws RecognitionException {
        EObject current = null;

        EObject this_select_statement_2 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:199:28: ( ( ( rulecreate_statement () ) | this_select_statement_2= ruleselect_statement | ( rulerelate_statement () ) | ( ruleunrelate_statement () ) | ( ruledelete_statement () ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:200:1: ( ( rulecreate_statement () ) | this_select_statement_2= ruleselect_statement | ( rulerelate_statement () ) | ( ruleunrelate_statement () ) | ( ruledelete_statement () ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:200:1: ( ( rulecreate_statement () ) | this_select_statement_2= ruleselect_statement | ( rulerelate_statement () ) | ( ruleunrelate_statement () ) | ( ruledelete_statement () ) )
            int alt3=5;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt3=1;
                }
                break;
            case 16:
                {
                alt3=2;
                }
                break;
            case 28:
                {
                alt3=3;
                }
                break;
            case 31:
                {
                alt3=4;
                }
                break;
            case 32:
                {
                alt3=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:200:2: ( rulecreate_statement () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:200:2: ( rulecreate_statement () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:201:5: rulecreate_statement ()
                    {
                     
                            newCompositeNode(grammarAccess.getObject_statementAccess().getCreate_statementParserRuleCall_0_0()); 
                        
                    pushFollow(FOLLOW_rulecreate_statement_in_ruleobject_statement378);
                    rulecreate_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:208:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:209:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getObject_statementAccess().getTypeCreateAction_0_1(),
                                current);
                        

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:216:5: this_select_statement_2= ruleselect_statement
                    {
                     
                            newCompositeNode(grammarAccess.getObject_statementAccess().getSelect_statementParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleselect_statement_in_ruleobject_statement415);
                    this_select_statement_2=ruleselect_statement();

                    state._fsp--;

                     
                            current = this_select_statement_2; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:225:6: ( rulerelate_statement () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:225:6: ( rulerelate_statement () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:226:5: rulerelate_statement ()
                    {
                     
                            newCompositeNode(grammarAccess.getObject_statementAccess().getRelate_statementParserRuleCall_2_0()); 
                        
                    pushFollow(FOLLOW_rulerelate_statement_in_ruleobject_statement437);
                    rulerelate_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:233:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:234:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getObject_statementAccess().getTypeRelateAction_2_1(),
                                current);
                        

                    }


                    }


                    }
                    break;
                case 4 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:240:6: ( ruleunrelate_statement () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:240:6: ( ruleunrelate_statement () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:241:5: ruleunrelate_statement ()
                    {
                     
                            newCompositeNode(grammarAccess.getObject_statementAccess().getUnrelate_statementParserRuleCall_3_0()); 
                        
                    pushFollow(FOLLOW_ruleunrelate_statement_in_ruleobject_statement469);
                    ruleunrelate_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:248:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:249:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getObject_statementAccess().getTypeRelateAction_3_1(),
                                current);
                        

                    }


                    }


                    }
                    break;
                case 5 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:255:6: ( ruledelete_statement () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:255:6: ( ruledelete_statement () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:256:5: ruledelete_statement ()
                    {
                     
                            newCompositeNode(grammarAccess.getObject_statementAccess().getDelete_statementParserRuleCall_4_0()); 
                        
                    pushFollow(FOLLOW_ruledelete_statement_in_ruleobject_statement501);
                    ruledelete_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:263:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:264:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getObject_statementAccess().getTypeDeleteAction_4_1(),
                                current);
                        

                    }


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleobject_statement"


    // $ANTLR start "entryRulecreate_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:277:1: entryRulecreate_statement returns [String current=null] : iv_rulecreate_statement= rulecreate_statement EOF ;
    public final String entryRulecreate_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulecreate_statement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:278:2: (iv_rulecreate_statement= rulecreate_statement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:279:2: iv_rulecreate_statement= rulecreate_statement EOF
            {
             newCompositeNode(grammarAccess.getCreate_statementRule()); 
            pushFollow(FOLLOW_rulecreate_statement_in_entryRulecreate_statement547);
            iv_rulecreate_statement=rulecreate_statement();

            state._fsp--;

             current =iv_rulecreate_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulecreate_statement558); 

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
    // $ANTLR end "entryRulecreate_statement"


    // $ANTLR start "rulecreate_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:286:1: rulecreate_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'create' kw= 'object' (this_name_2= rulename )? kw= 'of' this_class_name_4= ruleclass_name ) ;
    public final AntlrDatatypeRuleToken rulecreate_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_name_2 = null;

        AntlrDatatypeRuleToken this_class_name_4 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:289:28: ( (kw= 'create' kw= 'object' (this_name_2= rulename )? kw= 'of' this_class_name_4= ruleclass_name ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:290:1: (kw= 'create' kw= 'object' (this_name_2= rulename )? kw= 'of' this_class_name_4= ruleclass_name )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:290:1: (kw= 'create' kw= 'object' (this_name_2= rulename )? kw= 'of' this_class_name_4= ruleclass_name )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:291:2: kw= 'create' kw= 'object' (this_name_2= rulename )? kw= 'of' this_class_name_4= ruleclass_name
            {
            kw=(Token)match(input,13,FOLLOW_13_in_rulecreate_statement596); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getCreate_statementAccess().getCreateKeyword_0()); 
                
            kw=(Token)match(input,14,FOLLOW_14_in_rulecreate_statement609); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getCreate_statementAccess().getObjectKeyword_1()); 
                
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:302:1: (this_name_2= rulename )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_ID) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:303:5: this_name_2= rulename
                    {
                     
                            newCompositeNode(grammarAccess.getCreate_statementAccess().getNameParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_rulename_in_rulecreate_statement632);
                    this_name_2=rulename();

                    state._fsp--;


                    		current.merge(this_name_2);
                        
                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            kw=(Token)match(input,15,FOLLOW_15_in_rulecreate_statement652); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getCreate_statementAccess().getOfKeyword_3()); 
                
             
                    newCompositeNode(grammarAccess.getCreate_statementAccess().getClass_nameParserRuleCall_4()); 
                
            pushFollow(FOLLOW_ruleclass_name_in_rulecreate_statement674);
            this_class_name_4=ruleclass_name();

            state._fsp--;


            		current.merge(this_class_name_4);
                
             
                    afterParserOrEnumRuleCall();
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulecreate_statement"


    // $ANTLR start "entryRuleselect_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:338:1: entryRuleselect_statement returns [EObject current=null] : iv_ruleselect_statement= ruleselect_statement EOF ;
    public final EObject entryRuleselect_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleselect_statement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:339:2: (iv_ruleselect_statement= ruleselect_statement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:340:2: iv_ruleselect_statement= ruleselect_statement EOF
            {
             newCompositeNode(grammarAccess.getSelect_statementRule()); 
            pushFollow(FOLLOW_ruleselect_statement_in_entryRuleselect_statement719);
            iv_ruleselect_statement=ruleselect_statement();

            state._fsp--;

             current =iv_ruleselect_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleselect_statement729); 

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
    // $ANTLR end "entryRuleselect_statement"


    // $ANTLR start "ruleselect_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:347:1: ruleselect_statement returns [EObject current=null] : (otherlv_0= 'select' (otherlv_1= 'any' | otherlv_2= 'many' | otherlv_3= 'one' ) ( (lv_var_4_0= rulename ) ) ( (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? ) | (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? ) ) ) ;
    public final EObject ruleselect_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        AntlrDatatypeRuleToken lv_var_4_0 = null;

        EObject this_expression_10 = null;

        EObject this_expression_20 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:350:28: ( (otherlv_0= 'select' (otherlv_1= 'any' | otherlv_2= 'many' | otherlv_3= 'one' ) ( (lv_var_4_0= rulename ) ) ( (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? ) | (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? ) ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:351:1: (otherlv_0= 'select' (otherlv_1= 'any' | otherlv_2= 'many' | otherlv_3= 'one' ) ( (lv_var_4_0= rulename ) ) ( (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? ) | (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? ) ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:351:1: (otherlv_0= 'select' (otherlv_1= 'any' | otherlv_2= 'many' | otherlv_3= 'one' ) ( (lv_var_4_0= rulename ) ) ( (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? ) | (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:351:3: otherlv_0= 'select' (otherlv_1= 'any' | otherlv_2= 'many' | otherlv_3= 'one' ) ( (lv_var_4_0= rulename ) ) ( (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? ) | (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? ) )
            {
            otherlv_0=(Token)match(input,16,FOLLOW_16_in_ruleselect_statement766); 

                	newLeafNode(otherlv_0, grammarAccess.getSelect_statementAccess().getSelectKeyword_0());
                
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:355:1: (otherlv_1= 'any' | otherlv_2= 'many' | otherlv_3= 'one' )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt5=1;
                }
                break;
            case 18:
                {
                alt5=2;
                }
                break;
            case 19:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:355:3: otherlv_1= 'any'
                    {
                    otherlv_1=(Token)match(input,17,FOLLOW_17_in_ruleselect_statement779); 

                        	newLeafNode(otherlv_1, grammarAccess.getSelect_statementAccess().getAnyKeyword_1_0());
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:360:7: otherlv_2= 'many'
                    {
                    otherlv_2=(Token)match(input,18,FOLLOW_18_in_ruleselect_statement797); 

                        	newLeafNode(otherlv_2, grammarAccess.getSelect_statementAccess().getManyKeyword_1_1());
                        

                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:365:7: otherlv_3= 'one'
                    {
                    otherlv_3=(Token)match(input,19,FOLLOW_19_in_ruleselect_statement815); 

                        	newLeafNode(otherlv_3, grammarAccess.getSelect_statementAccess().getOneKeyword_1_2());
                        

                    }
                    break;

            }

            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:369:2: ( (lv_var_4_0= rulename ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:370:1: (lv_var_4_0= rulename )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:370:1: (lv_var_4_0= rulename )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:371:3: lv_var_4_0= rulename
            {
             
            	        newCompositeNode(grammarAccess.getSelect_statementAccess().getVarNameParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_rulename_in_ruleselect_statement837);
            lv_var_4_0=rulename();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getSelect_statementRule());
            	        }
                   		set(
                   			current, 
                   			"var",
                    		lv_var_4_0, 
                    		"name");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:387:2: ( (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? ) | (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? ) )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==20) ) {
                alt9=1;
            }
            else if ( (LA9_0==23) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:387:3: (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:387:3: (otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )? )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:387:5: otherlv_5= 'from' otherlv_6= 'instances' otherlv_7= 'of' ruleclass_name (otherlv_9= 'where' this_expression_10= ruleexpression )?
                    {
                    otherlv_5=(Token)match(input,20,FOLLOW_20_in_ruleselect_statement851); 

                        	newLeafNode(otherlv_5, grammarAccess.getSelect_statementAccess().getFromKeyword_3_0_0());
                        
                    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleselect_statement863); 

                        	newLeafNode(otherlv_6, grammarAccess.getSelect_statementAccess().getInstancesKeyword_3_0_1());
                        
                    otherlv_7=(Token)match(input,15,FOLLOW_15_in_ruleselect_statement875); 

                        	newLeafNode(otherlv_7, grammarAccess.getSelect_statementAccess().getOfKeyword_3_0_2());
                        
                     
                            newCompositeNode(grammarAccess.getSelect_statementAccess().getClass_nameParserRuleCall_3_0_3()); 
                        
                    pushFollow(FOLLOW_ruleclass_name_in_ruleselect_statement891);
                    ruleclass_name();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:407:1: (otherlv_9= 'where' this_expression_10= ruleexpression )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==22) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:407:3: otherlv_9= 'where' this_expression_10= ruleexpression
                            {
                            otherlv_9=(Token)match(input,22,FOLLOW_22_in_ruleselect_statement903); 

                                	newLeafNode(otherlv_9, grammarAccess.getSelect_statementAccess().getWhereKeyword_3_0_4_0());
                                
                             
                                    newCompositeNode(grammarAccess.getSelect_statementAccess().getExpressionParserRuleCall_3_0_4_1()); 
                                
                            pushFollow(FOLLOW_ruleexpression_in_ruleselect_statement925);
                            this_expression_10=ruleexpression();

                            state._fsp--;

                             
                                    current = this_expression_10; 
                                    afterParserOrEnumRuleCall();
                                

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:421:6: (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:421:6: (otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )? )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:421:8: otherlv_11= 'related' otherlv_12= 'by' ruleobject_reference (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+ (otherlv_19= 'where' this_expression_20= ruleexpression )?
                    {
                    otherlv_11=(Token)match(input,23,FOLLOW_23_in_ruleselect_statement946); 

                        	newLeafNode(otherlv_11, grammarAccess.getSelect_statementAccess().getRelatedKeyword_3_1_0());
                        
                    otherlv_12=(Token)match(input,24,FOLLOW_24_in_ruleselect_statement958); 

                        	newLeafNode(otherlv_12, grammarAccess.getSelect_statementAccess().getByKeyword_3_1_1());
                        
                     
                            newCompositeNode(grammarAccess.getSelect_statementAccess().getObject_referenceParserRuleCall_3_1_2()); 
                        
                    pushFollow(FOLLOW_ruleobject_reference_in_ruleselect_statement974);
                    ruleobject_reference();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:437:1: (otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']' )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==25) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:437:3: otherlv_14= '->' ruleclass_name otherlv_16= '[' rulerelation otherlv_18= ']'
                    	    {
                    	    otherlv_14=(Token)match(input,25,FOLLOW_25_in_ruleselect_statement986); 

                    	        	newLeafNode(otherlv_14, grammarAccess.getSelect_statementAccess().getHyphenMinusGreaterThanSignKeyword_3_1_3_0());
                    	        
                    	     
                    	            newCompositeNode(grammarAccess.getSelect_statementAccess().getClass_nameParserRuleCall_3_1_3_1()); 
                    	        
                    	    pushFollow(FOLLOW_ruleclass_name_in_ruleselect_statement1002);
                    	    ruleclass_name();

                    	    state._fsp--;

                    	     
                    	            afterParserOrEnumRuleCall();
                    	        
                    	    otherlv_16=(Token)match(input,26,FOLLOW_26_in_ruleselect_statement1013); 

                    	        	newLeafNode(otherlv_16, grammarAccess.getSelect_statementAccess().getLeftSquareBracketKeyword_3_1_3_2());
                    	        
                    	     
                    	            newCompositeNode(grammarAccess.getSelect_statementAccess().getRelationParserRuleCall_3_1_3_3()); 
                    	        
                    	    pushFollow(FOLLOW_rulerelation_in_ruleselect_statement1029);
                    	    rulerelation();

                    	    state._fsp--;

                    	     
                    	            afterParserOrEnumRuleCall();
                    	        
                    	    otherlv_18=(Token)match(input,27,FOLLOW_27_in_ruleselect_statement1040); 

                    	        	newLeafNode(otherlv_18, grammarAccess.getSelect_statementAccess().getRightSquareBracketKeyword_3_1_3_4());
                    	        

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);

                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:465:3: (otherlv_19= 'where' this_expression_20= ruleexpression )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==22) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:465:5: otherlv_19= 'where' this_expression_20= ruleexpression
                            {
                            otherlv_19=(Token)match(input,22,FOLLOW_22_in_ruleselect_statement1055); 

                                	newLeafNode(otherlv_19, grammarAccess.getSelect_statementAccess().getWhereKeyword_3_1_4_0());
                                
                             
                                    newCompositeNode(grammarAccess.getSelect_statementAccess().getExpressionParserRuleCall_3_1_4_1()); 
                                
                            pushFollow(FOLLOW_ruleexpression_in_ruleselect_statement1077);
                            this_expression_20=ruleexpression();

                            state._fsp--;

                             
                                    current = this_expression_20; 
                                    afterParserOrEnumRuleCall();
                                

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleselect_statement"


    // $ANTLR start "entryRulerelate_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:486:1: entryRulerelate_statement returns [String current=null] : iv_rulerelate_statement= rulerelate_statement EOF ;
    public final String entryRulerelate_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulerelate_statement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:487:2: (iv_rulerelate_statement= rulerelate_statement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:488:2: iv_rulerelate_statement= rulerelate_statement EOF
            {
             newCompositeNode(grammarAccess.getRelate_statementRule()); 
            pushFollow(FOLLOW_rulerelate_statement_in_entryRulerelate_statement1117);
            iv_rulerelate_statement=rulerelate_statement();

            state._fsp--;

             current =iv_rulerelate_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulerelate_statement1128); 

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
    // $ANTLR end "entryRulerelate_statement"


    // $ANTLR start "rulerelate_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:495:1: rulerelate_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'relate' this_object_reference_1= ruleobject_reference kw= 'to' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation ) ;
    public final AntlrDatatypeRuleToken rulerelate_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_object_reference_1 = null;

        AntlrDatatypeRuleToken this_object_reference_3 = null;

        AntlrDatatypeRuleToken this_relation_5 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:498:28: ( (kw= 'relate' this_object_reference_1= ruleobject_reference kw= 'to' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:499:1: (kw= 'relate' this_object_reference_1= ruleobject_reference kw= 'to' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:499:1: (kw= 'relate' this_object_reference_1= ruleobject_reference kw= 'to' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:500:2: kw= 'relate' this_object_reference_1= ruleobject_reference kw= 'to' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation
            {
            kw=(Token)match(input,28,FOLLOW_28_in_rulerelate_statement1166); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getRelate_statementAccess().getRelateKeyword_0()); 
                
             
                    newCompositeNode(grammarAccess.getRelate_statementAccess().getObject_referenceParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleobject_reference_in_rulerelate_statement1188);
            this_object_reference_1=ruleobject_reference();

            state._fsp--;


            		current.merge(this_object_reference_1);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,29,FOLLOW_29_in_rulerelate_statement1206); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getRelate_statementAccess().getToKeyword_2()); 
                
             
                    newCompositeNode(grammarAccess.getRelate_statementAccess().getObject_referenceParserRuleCall_3()); 
                
            pushFollow(FOLLOW_ruleobject_reference_in_rulerelate_statement1228);
            this_object_reference_3=ruleobject_reference();

            state._fsp--;


            		current.merge(this_object_reference_3);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,30,FOLLOW_30_in_rulerelate_statement1246); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getRelate_statementAccess().getAcrossKeyword_4()); 
                
             
                    newCompositeNode(grammarAccess.getRelate_statementAccess().getRelationParserRuleCall_5()); 
                
            pushFollow(FOLLOW_rulerelation_in_rulerelate_statement1268);
            this_relation_5=rulerelation();

            state._fsp--;


            		current.merge(this_relation_5);
                
             
                    afterParserOrEnumRuleCall();
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulerelate_statement"


    // $ANTLR start "entryRuleunrelate_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:558:1: entryRuleunrelate_statement returns [String current=null] : iv_ruleunrelate_statement= ruleunrelate_statement EOF ;
    public final String entryRuleunrelate_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleunrelate_statement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:559:2: (iv_ruleunrelate_statement= ruleunrelate_statement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:560:2: iv_ruleunrelate_statement= ruleunrelate_statement EOF
            {
             newCompositeNode(grammarAccess.getUnrelate_statementRule()); 
            pushFollow(FOLLOW_ruleunrelate_statement_in_entryRuleunrelate_statement1314);
            iv_ruleunrelate_statement=ruleunrelate_statement();

            state._fsp--;

             current =iv_ruleunrelate_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleunrelate_statement1325); 

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
    // $ANTLR end "entryRuleunrelate_statement"


    // $ANTLR start "ruleunrelate_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:567:1: ruleunrelate_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'unrelate' this_object_reference_1= ruleobject_reference kw= 'from' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation ) ;
    public final AntlrDatatypeRuleToken ruleunrelate_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_object_reference_1 = null;

        AntlrDatatypeRuleToken this_object_reference_3 = null;

        AntlrDatatypeRuleToken this_relation_5 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:570:28: ( (kw= 'unrelate' this_object_reference_1= ruleobject_reference kw= 'from' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:571:1: (kw= 'unrelate' this_object_reference_1= ruleobject_reference kw= 'from' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:571:1: (kw= 'unrelate' this_object_reference_1= ruleobject_reference kw= 'from' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:572:2: kw= 'unrelate' this_object_reference_1= ruleobject_reference kw= 'from' this_object_reference_3= ruleobject_reference kw= 'across' this_relation_5= rulerelation
            {
            kw=(Token)match(input,31,FOLLOW_31_in_ruleunrelate_statement1363); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getUnrelate_statementAccess().getUnrelateKeyword_0()); 
                
             
                    newCompositeNode(grammarAccess.getUnrelate_statementAccess().getObject_referenceParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleobject_reference_in_ruleunrelate_statement1385);
            this_object_reference_1=ruleobject_reference();

            state._fsp--;


            		current.merge(this_object_reference_1);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,20,FOLLOW_20_in_ruleunrelate_statement1403); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getUnrelate_statementAccess().getFromKeyword_2()); 
                
             
                    newCompositeNode(grammarAccess.getUnrelate_statementAccess().getObject_referenceParserRuleCall_3()); 
                
            pushFollow(FOLLOW_ruleobject_reference_in_ruleunrelate_statement1425);
            this_object_reference_3=ruleobject_reference();

            state._fsp--;


            		current.merge(this_object_reference_3);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,30,FOLLOW_30_in_ruleunrelate_statement1443); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getUnrelate_statementAccess().getAcrossKeyword_4()); 
                
             
                    newCompositeNode(grammarAccess.getUnrelate_statementAccess().getRelationParserRuleCall_5()); 
                
            pushFollow(FOLLOW_rulerelation_in_ruleunrelate_statement1465);
            this_relation_5=rulerelation();

            state._fsp--;


            		current.merge(this_relation_5);
                
             
                    afterParserOrEnumRuleCall();
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleunrelate_statement"


    // $ANTLR start "entryRuledelete_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:630:1: entryRuledelete_statement returns [String current=null] : iv_ruledelete_statement= ruledelete_statement EOF ;
    public final String entryRuledelete_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruledelete_statement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:631:2: (iv_ruledelete_statement= ruledelete_statement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:632:2: iv_ruledelete_statement= ruledelete_statement EOF
            {
             newCompositeNode(grammarAccess.getDelete_statementRule()); 
            pushFollow(FOLLOW_ruledelete_statement_in_entryRuledelete_statement1511);
            iv_ruledelete_statement=ruledelete_statement();

            state._fsp--;

             current =iv_ruledelete_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuledelete_statement1522); 

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
    // $ANTLR end "entryRuledelete_statement"


    // $ANTLR start "ruledelete_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:639:1: ruledelete_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'delete' kw= 'object' kw= 'instance' this_object_reference_3= ruleobject_reference ) ;
    public final AntlrDatatypeRuleToken ruledelete_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_object_reference_3 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:642:28: ( (kw= 'delete' kw= 'object' kw= 'instance' this_object_reference_3= ruleobject_reference ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:643:1: (kw= 'delete' kw= 'object' kw= 'instance' this_object_reference_3= ruleobject_reference )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:643:1: (kw= 'delete' kw= 'object' kw= 'instance' this_object_reference_3= ruleobject_reference )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:644:2: kw= 'delete' kw= 'object' kw= 'instance' this_object_reference_3= ruleobject_reference
            {
            kw=(Token)match(input,32,FOLLOW_32_in_ruledelete_statement1560); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getDelete_statementAccess().getDeleteKeyword_0()); 
                
            kw=(Token)match(input,14,FOLLOW_14_in_ruledelete_statement1573); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getDelete_statementAccess().getObjectKeyword_1()); 
                
            kw=(Token)match(input,33,FOLLOW_33_in_ruledelete_statement1586); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getDelete_statementAccess().getInstanceKeyword_2()); 
                
             
                    newCompositeNode(grammarAccess.getDelete_statementAccess().getObject_referenceParserRuleCall_3()); 
                
            pushFollow(FOLLOW_ruleobject_reference_in_ruledelete_statement1608);
            this_object_reference_3=ruleobject_reference();

            state._fsp--;


            		current.merge(this_object_reference_3);
                
             
                    afterParserOrEnumRuleCall();
                

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruledelete_statement"


    // $ANTLR start "entryRuleobject_reference"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:680:1: entryRuleobject_reference returns [String current=null] : iv_ruleobject_reference= ruleobject_reference EOF ;
    public final String entryRuleobject_reference() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleobject_reference = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:681:2: (iv_ruleobject_reference= ruleobject_reference EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:682:2: iv_ruleobject_reference= ruleobject_reference EOF
            {
             newCompositeNode(grammarAccess.getObject_referenceRule()); 
            pushFollow(FOLLOW_ruleobject_reference_in_entryRuleobject_reference1654);
            iv_ruleobject_reference=ruleobject_reference();

            state._fsp--;

             current =iv_ruleobject_reference.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleobject_reference1665); 

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
    // $ANTLR end "entryRuleobject_reference"


    // $ANTLR start "ruleobject_reference"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:689:1: ruleobject_reference returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'self' | this_name_1= rulename ) ;
    public final AntlrDatatypeRuleToken ruleobject_reference() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_name_1 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:692:28: ( (kw= 'self' | this_name_1= rulename ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:693:1: (kw= 'self' | this_name_1= rulename )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:693:1: (kw= 'self' | this_name_1= rulename )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==34) ) {
                alt10=1;
            }
            else if ( (LA10_0==RULE_ID) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:694:2: kw= 'self'
                    {
                    kw=(Token)match(input,34,FOLLOW_34_in_ruleobject_reference1703); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getObject_referenceAccess().getSelfKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:701:5: this_name_1= rulename
                    {
                     
                            newCompositeNode(grammarAccess.getObject_referenceAccess().getNameParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_rulename_in_ruleobject_reference1731);
                    this_name_1=rulename();

                    state._fsp--;


                    		current.merge(this_name_1);
                        
                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleobject_reference"


    // $ANTLR start "entryRulerelation"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:719:1: entryRulerelation returns [String current=null] : iv_rulerelation= rulerelation EOF ;
    public final String entryRulerelation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulerelation = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:720:2: (iv_rulerelation= rulerelation EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:721:2: iv_rulerelation= rulerelation EOF
            {
             newCompositeNode(grammarAccess.getRelationRule()); 
            pushFollow(FOLLOW_rulerelation_in_entryRulerelation1777);
            iv_rulerelation=rulerelation();

            state._fsp--;

             current =iv_rulerelation.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulerelation1788); 

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
    // $ANTLR end "entryRulerelation"


    // $ANTLR start "rulerelation"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:728:1: rulerelation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_RELATION_NAME_0= RULE_RELATION_NAME (kw= '.' this_STRING_2= RULE_STRING )? ) ;
    public final AntlrDatatypeRuleToken rulerelation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_RELATION_NAME_0=null;
        Token kw=null;
        Token this_STRING_2=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:731:28: ( (this_RELATION_NAME_0= RULE_RELATION_NAME (kw= '.' this_STRING_2= RULE_STRING )? ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:732:1: (this_RELATION_NAME_0= RULE_RELATION_NAME (kw= '.' this_STRING_2= RULE_STRING )? )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:732:1: (this_RELATION_NAME_0= RULE_RELATION_NAME (kw= '.' this_STRING_2= RULE_STRING )? )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:732:6: this_RELATION_NAME_0= RULE_RELATION_NAME (kw= '.' this_STRING_2= RULE_STRING )?
            {
            this_RELATION_NAME_0=(Token)match(input,RULE_RELATION_NAME,FOLLOW_RULE_RELATION_NAME_in_rulerelation1828); 

            		current.merge(this_RELATION_NAME_0);
                
             
                newLeafNode(this_RELATION_NAME_0, grammarAccess.getRelationAccess().getRELATION_NAMETerminalRuleCall_0()); 
                
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:739:1: (kw= '.' this_STRING_2= RULE_STRING )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==35) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:740:2: kw= '.' this_STRING_2= RULE_STRING
                    {
                    kw=(Token)match(input,35,FOLLOW_35_in_rulerelation1847); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getRelationAccess().getFullStopKeyword_1_0()); 
                        
                    this_STRING_2=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rulerelation1862); 

                    		current.merge(this_STRING_2);
                        
                     
                        newLeafNode(this_STRING_2, grammarAccess.getRelationAccess().getSTRINGTerminalRuleCall_1_1()); 
                        

                    }
                    break;

            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulerelation"


    // $ANTLR start "entryRuleassignment"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:760:1: entryRuleassignment returns [EObject current=null] : iv_ruleassignment= ruleassignment EOF ;
    public final EObject entryRuleassignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleassignment = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:761:2: (iv_ruleassignment= ruleassignment EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:762:2: iv_ruleassignment= ruleassignment EOF
            {
             newCompositeNode(grammarAccess.getAssignmentRule()); 
            pushFollow(FOLLOW_ruleassignment_in_entryRuleassignment1909);
            iv_ruleassignment=ruleassignment();

            state._fsp--;

             current =iv_ruleassignment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleassignment1919); 

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
    // $ANTLR end "entryRuleassignment"


    // $ANTLR start "ruleassignment"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:769:1: ruleassignment returns [EObject current=null] : ( (otherlv_0= 'assign' )? rulelvalue otherlv_2= '=' ( (lv_e_3_0= ruleexpression ) ) ) ;
    public final EObject ruleassignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_e_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:772:28: ( ( (otherlv_0= 'assign' )? rulelvalue otherlv_2= '=' ( (lv_e_3_0= ruleexpression ) ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:773:1: ( (otherlv_0= 'assign' )? rulelvalue otherlv_2= '=' ( (lv_e_3_0= ruleexpression ) ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:773:1: ( (otherlv_0= 'assign' )? rulelvalue otherlv_2= '=' ( (lv_e_3_0= ruleexpression ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:773:2: (otherlv_0= 'assign' )? rulelvalue otherlv_2= '=' ( (lv_e_3_0= ruleexpression ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:773:2: (otherlv_0= 'assign' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==36) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:773:4: otherlv_0= 'assign'
                    {
                    otherlv_0=(Token)match(input,36,FOLLOW_36_in_ruleassignment1957); 

                        	newLeafNode(otherlv_0, grammarAccess.getAssignmentAccess().getAssignKeyword_0());
                        

                    }
                    break;

            }

             
                    newCompositeNode(grammarAccess.getAssignmentAccess().getLvalueParserRuleCall_1()); 
                
            pushFollow(FOLLOW_rulelvalue_in_ruleassignment1975);
            rulelvalue();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            otherlv_2=(Token)match(input,37,FOLLOW_37_in_ruleassignment1986); 

                	newLeafNode(otherlv_2, grammarAccess.getAssignmentAccess().getEqualsSignKeyword_2());
                
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:789:1: ( (lv_e_3_0= ruleexpression ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:790:1: (lv_e_3_0= ruleexpression )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:790:1: (lv_e_3_0= ruleexpression )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:791:3: lv_e_3_0= ruleexpression
            {
             
            	        newCompositeNode(grammarAccess.getAssignmentAccess().getEExpressionParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleexpression_in_ruleassignment2007);
            lv_e_3_0=ruleexpression();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAssignmentRule());
            	        }
                   		set(
                   			current, 
                   			"e",
                    		lv_e_3_0, 
                    		"expression");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleassignment"


    // $ANTLR start "entryRulelvalue"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:815:1: entryRulelvalue returns [String current=null] : iv_rulelvalue= rulelvalue EOF ;
    public final String entryRulelvalue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulelvalue = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:816:2: (iv_rulelvalue= rulelvalue EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:817:2: iv_rulelvalue= rulelvalue EOF
            {
             newCompositeNode(grammarAccess.getLvalueRule()); 
            pushFollow(FOLLOW_rulelvalue_in_entryRulelvalue2044);
            iv_rulelvalue=rulelvalue();

            state._fsp--;

             current =iv_rulelvalue.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulelvalue2055); 

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
    // $ANTLR end "entryRulelvalue"


    // $ANTLR start "rulelvalue"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:824:1: rulelvalue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_variable_0= rulevariable ;
    public final AntlrDatatypeRuleToken rulelvalue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_variable_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:827:28: (this_variable_0= rulevariable )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:829:5: this_variable_0= rulevariable
            {
             
                    newCompositeNode(grammarAccess.getLvalueAccess().getVariableParserRuleCall()); 
                
            pushFollow(FOLLOW_rulevariable_in_rulelvalue2101);
            this_variable_0=rulevariable();

            state._fsp--;


            		current.merge(this_variable_0);
                
             
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulelvalue"


    // $ANTLR start "entryRuleflow_control_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:847:1: entryRuleflow_control_statement returns [EObject current=null] : iv_ruleflow_control_statement= ruleflow_control_statement EOF ;
    public final EObject entryRuleflow_control_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleflow_control_statement = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:848:2: (iv_ruleflow_control_statement= ruleflow_control_statement EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:849:2: iv_ruleflow_control_statement= ruleflow_control_statement EOF
            {
             newCompositeNode(grammarAccess.getFlow_control_statementRule()); 
            pushFollow(FOLLOW_ruleflow_control_statement_in_entryRuleflow_control_statement2145);
            iv_ruleflow_control_statement=ruleflow_control_statement();

            state._fsp--;

             current =iv_ruleflow_control_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleflow_control_statement2155); 

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
    // $ANTLR end "entryRuleflow_control_statement"


    // $ANTLR start "ruleflow_control_statement"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:856:1: ruleflow_control_statement returns [EObject current=null] : ( ( () otherlv_1= 'if' ( (lv_expr_2_0= ruleexpression ) ) otherlv_3= 'then' ( (lv_substatements_4_0= rulestatement ) )* (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )* (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )? otherlv_11= 'end' otherlv_12= 'if' ) | ( () otherlv_14= 'for' rulelvalue otherlv_16= 'of' ( (lv_list_17_0= rulename ) ) otherlv_18= 'do' ( (lv_substatements_19_0= rulestatement ) )* otherlv_20= 'end' otherlv_21= 'for' ) | ( () otherlv_23= 'while' ( (lv_expr_24_0= ruleexpression ) ) otherlv_25= 'do' ( (lv_substatements_26_0= rulestatement ) )* otherlv_27= 'end' otherlv_28= 'while' ) ) ;
    public final EObject ruleflow_control_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        EObject lv_expr_2_0 = null;

        EObject lv_substatements_4_0 = null;

        EObject lv_elifexpr_6_0 = null;

        EObject lv_substatements_8_0 = null;

        EObject lv_substatements_10_0 = null;

        AntlrDatatypeRuleToken lv_list_17_0 = null;

        EObject lv_substatements_19_0 = null;

        EObject lv_expr_24_0 = null;

        EObject lv_substatements_26_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:859:28: ( ( ( () otherlv_1= 'if' ( (lv_expr_2_0= ruleexpression ) ) otherlv_3= 'then' ( (lv_substatements_4_0= rulestatement ) )* (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )* (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )? otherlv_11= 'end' otherlv_12= 'if' ) | ( () otherlv_14= 'for' rulelvalue otherlv_16= 'of' ( (lv_list_17_0= rulename ) ) otherlv_18= 'do' ( (lv_substatements_19_0= rulestatement ) )* otherlv_20= 'end' otherlv_21= 'for' ) | ( () otherlv_23= 'while' ( (lv_expr_24_0= ruleexpression ) ) otherlv_25= 'do' ( (lv_substatements_26_0= rulestatement ) )* otherlv_27= 'end' otherlv_28= 'while' ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:860:1: ( ( () otherlv_1= 'if' ( (lv_expr_2_0= ruleexpression ) ) otherlv_3= 'then' ( (lv_substatements_4_0= rulestatement ) )* (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )* (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )? otherlv_11= 'end' otherlv_12= 'if' ) | ( () otherlv_14= 'for' rulelvalue otherlv_16= 'of' ( (lv_list_17_0= rulename ) ) otherlv_18= 'do' ( (lv_substatements_19_0= rulestatement ) )* otherlv_20= 'end' otherlv_21= 'for' ) | ( () otherlv_23= 'while' ( (lv_expr_24_0= ruleexpression ) ) otherlv_25= 'do' ( (lv_substatements_26_0= rulestatement ) )* otherlv_27= 'end' otherlv_28= 'while' ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:860:1: ( ( () otherlv_1= 'if' ( (lv_expr_2_0= ruleexpression ) ) otherlv_3= 'then' ( (lv_substatements_4_0= rulestatement ) )* (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )* (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )? otherlv_11= 'end' otherlv_12= 'if' ) | ( () otherlv_14= 'for' rulelvalue otherlv_16= 'of' ( (lv_list_17_0= rulename ) ) otherlv_18= 'do' ( (lv_substatements_19_0= rulestatement ) )* otherlv_20= 'end' otherlv_21= 'for' ) | ( () otherlv_23= 'while' ( (lv_expr_24_0= ruleexpression ) ) otherlv_25= 'do' ( (lv_substatements_26_0= rulestatement ) )* otherlv_27= 'end' otherlv_28= 'while' ) )
            int alt20=3;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt20=1;
                }
                break;
            case 43:
                {
                alt20=2;
                }
                break;
            case 45:
                {
                alt20=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:860:2: ( () otherlv_1= 'if' ( (lv_expr_2_0= ruleexpression ) ) otherlv_3= 'then' ( (lv_substatements_4_0= rulestatement ) )* (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )* (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )? otherlv_11= 'end' otherlv_12= 'if' )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:860:2: ( () otherlv_1= 'if' ( (lv_expr_2_0= ruleexpression ) ) otherlv_3= 'then' ( (lv_substatements_4_0= rulestatement ) )* (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )* (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )? otherlv_11= 'end' otherlv_12= 'if' )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:860:3: () otherlv_1= 'if' ( (lv_expr_2_0= ruleexpression ) ) otherlv_3= 'then' ( (lv_substatements_4_0= rulestatement ) )* (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )* (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )? otherlv_11= 'end' otherlv_12= 'if'
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:860:3: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:861:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getFlow_control_statementAccess().getTypeStatementIfAction_0_0(),
                                current);
                        

                    }

                    otherlv_1=(Token)match(input,38,FOLLOW_38_in_ruleflow_control_statement2202); 

                        	newLeafNode(otherlv_1, grammarAccess.getFlow_control_statementAccess().getIfKeyword_0_1());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:870:1: ( (lv_expr_2_0= ruleexpression ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:871:1: (lv_expr_2_0= ruleexpression )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:871:1: (lv_expr_2_0= ruleexpression )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:872:3: lv_expr_2_0= ruleexpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getExprExpressionParserRuleCall_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleexpression_in_ruleflow_control_statement2223);
                    lv_expr_2_0=ruleexpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"expr",
                            		lv_expr_2_0, 
                            		"expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_3=(Token)match(input,39,FOLLOW_39_in_ruleflow_control_statement2235); 

                        	newLeafNode(otherlv_3, grammarAccess.getFlow_control_statementAccess().getThenKeyword_0_3());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:892:1: ( (lv_substatements_4_0= rulestatement ) )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==RULE_ID||LA13_0==13||LA13_0==16||LA13_0==28||(LA13_0>=31 && LA13_0<=32)||LA13_0==34||LA13_0==36||LA13_0==38||LA13_0==43||LA13_0==45) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:893:1: (lv_substatements_4_0= rulestatement )
                    	    {
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:893:1: (lv_substatements_4_0= rulestatement )
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:894:3: lv_substatements_4_0= rulestatement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getSubstatementsStatementParserRuleCall_0_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulestatement_in_ruleflow_control_statement2256);
                    	    lv_substatements_4_0=rulestatement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"substatements",
                    	            		lv_substatements_4_0, 
                    	            		"statement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);

                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:910:3: (otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )* )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==40) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:910:5: otherlv_5= 'elif' ( (lv_elifexpr_6_0= ruleexpression ) ) otherlv_7= 'then' ( (lv_substatements_8_0= rulestatement ) )*
                    	    {
                    	    otherlv_5=(Token)match(input,40,FOLLOW_40_in_ruleflow_control_statement2270); 

                    	        	newLeafNode(otherlv_5, grammarAccess.getFlow_control_statementAccess().getElifKeyword_0_5_0());
                    	        
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:914:1: ( (lv_elifexpr_6_0= ruleexpression ) )
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:915:1: (lv_elifexpr_6_0= ruleexpression )
                    	    {
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:915:1: (lv_elifexpr_6_0= ruleexpression )
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:916:3: lv_elifexpr_6_0= ruleexpression
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getElifexprExpressionParserRuleCall_0_5_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleexpression_in_ruleflow_control_statement2291);
                    	    lv_elifexpr_6_0=ruleexpression();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"elifexpr",
                    	            		lv_elifexpr_6_0, 
                    	            		"expression");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }

                    	    otherlv_7=(Token)match(input,39,FOLLOW_39_in_ruleflow_control_statement2303); 

                    	        	newLeafNode(otherlv_7, grammarAccess.getFlow_control_statementAccess().getThenKeyword_0_5_2());
                    	        
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:936:1: ( (lv_substatements_8_0= rulestatement ) )*
                    	    loop14:
                    	    do {
                    	        int alt14=2;
                    	        int LA14_0 = input.LA(1);

                    	        if ( (LA14_0==RULE_ID||LA14_0==13||LA14_0==16||LA14_0==28||(LA14_0>=31 && LA14_0<=32)||LA14_0==34||LA14_0==36||LA14_0==38||LA14_0==43||LA14_0==45) ) {
                    	            alt14=1;
                    	        }


                    	        switch (alt14) {
                    	    	case 1 :
                    	    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:937:1: (lv_substatements_8_0= rulestatement )
                    	    	    {
                    	    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:937:1: (lv_substatements_8_0= rulestatement )
                    	    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:938:3: lv_substatements_8_0= rulestatement
                    	    	    {
                    	    	     
                    	    	    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getSubstatementsStatementParserRuleCall_0_5_3_0()); 
                    	    	    	    
                    	    	    pushFollow(FOLLOW_rulestatement_in_ruleflow_control_statement2324);
                    	    	    lv_substatements_8_0=rulestatement();

                    	    	    state._fsp--;


                    	    	    	        if (current==null) {
                    	    	    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	    	    	        }
                    	    	           		add(
                    	    	           			current, 
                    	    	           			"substatements",
                    	    	            		lv_substatements_8_0, 
                    	    	            		"statement");
                    	    	    	        afterParserOrEnumRuleCall();
                    	    	    	    

                    	    	    }


                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop14;
                    	        }
                    	    } while (true);


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:954:5: (otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )* )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==41) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:954:7: otherlv_9= 'else' ( (lv_substatements_10_0= rulestatement ) )*
                            {
                            otherlv_9=(Token)match(input,41,FOLLOW_41_in_ruleflow_control_statement2340); 

                                	newLeafNode(otherlv_9, grammarAccess.getFlow_control_statementAccess().getElseKeyword_0_6_0());
                                
                            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:958:1: ( (lv_substatements_10_0= rulestatement ) )*
                            loop16:
                            do {
                                int alt16=2;
                                int LA16_0 = input.LA(1);

                                if ( (LA16_0==RULE_ID||LA16_0==13||LA16_0==16||LA16_0==28||(LA16_0>=31 && LA16_0<=32)||LA16_0==34||LA16_0==36||LA16_0==38||LA16_0==43||LA16_0==45) ) {
                                    alt16=1;
                                }


                                switch (alt16) {
                            	case 1 :
                            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:959:1: (lv_substatements_10_0= rulestatement )
                            	    {
                            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:959:1: (lv_substatements_10_0= rulestatement )
                            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:960:3: lv_substatements_10_0= rulestatement
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getSubstatementsStatementParserRuleCall_0_6_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_rulestatement_in_ruleflow_control_statement2361);
                            	    lv_substatements_10_0=rulestatement();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"substatements",
                            	            		lv_substatements_10_0, 
                            	            		"statement");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop16;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_11=(Token)match(input,42,FOLLOW_42_in_ruleflow_control_statement2376); 

                        	newLeafNode(otherlv_11, grammarAccess.getFlow_control_statementAccess().getEndKeyword_0_7());
                        
                    otherlv_12=(Token)match(input,38,FOLLOW_38_in_ruleflow_control_statement2388); 

                        	newLeafNode(otherlv_12, grammarAccess.getFlow_control_statementAccess().getIfKeyword_0_8());
                        

                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:985:6: ( () otherlv_14= 'for' rulelvalue otherlv_16= 'of' ( (lv_list_17_0= rulename ) ) otherlv_18= 'do' ( (lv_substatements_19_0= rulestatement ) )* otherlv_20= 'end' otherlv_21= 'for' )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:985:6: ( () otherlv_14= 'for' rulelvalue otherlv_16= 'of' ( (lv_list_17_0= rulename ) ) otherlv_18= 'do' ( (lv_substatements_19_0= rulestatement ) )* otherlv_20= 'end' otherlv_21= 'for' )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:985:7: () otherlv_14= 'for' rulelvalue otherlv_16= 'of' ( (lv_list_17_0= rulename ) ) otherlv_18= 'do' ( (lv_substatements_19_0= rulestatement ) )* otherlv_20= 'end' otherlv_21= 'for'
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:985:7: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:986:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getFlow_control_statementAccess().getTypeStatementForAction_1_0(),
                                current);
                        

                    }

                    otherlv_14=(Token)match(input,43,FOLLOW_43_in_ruleflow_control_statement2417); 

                        	newLeafNode(otherlv_14, grammarAccess.getFlow_control_statementAccess().getForKeyword_1_1());
                        
                     
                            newCompositeNode(grammarAccess.getFlow_control_statementAccess().getLvalueParserRuleCall_1_2()); 
                        
                    pushFollow(FOLLOW_rulelvalue_in_ruleflow_control_statement2433);
                    rulelvalue();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    otherlv_16=(Token)match(input,15,FOLLOW_15_in_ruleflow_control_statement2444); 

                        	newLeafNode(otherlv_16, grammarAccess.getFlow_control_statementAccess().getOfKeyword_1_3());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1007:1: ( (lv_list_17_0= rulename ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1008:1: (lv_list_17_0= rulename )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1008:1: (lv_list_17_0= rulename )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1009:3: lv_list_17_0= rulename
                    {
                     
                    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getListNameParserRuleCall_1_4_0()); 
                    	    
                    pushFollow(FOLLOW_rulename_in_ruleflow_control_statement2465);
                    lv_list_17_0=rulename();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"list",
                            		lv_list_17_0, 
                            		"name");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_18=(Token)match(input,44,FOLLOW_44_in_ruleflow_control_statement2477); 

                        	newLeafNode(otherlv_18, grammarAccess.getFlow_control_statementAccess().getDoKeyword_1_5());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1029:1: ( (lv_substatements_19_0= rulestatement ) )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==RULE_ID||LA18_0==13||LA18_0==16||LA18_0==28||(LA18_0>=31 && LA18_0<=32)||LA18_0==34||LA18_0==36||LA18_0==38||LA18_0==43||LA18_0==45) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1030:1: (lv_substatements_19_0= rulestatement )
                    	    {
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1030:1: (lv_substatements_19_0= rulestatement )
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1031:3: lv_substatements_19_0= rulestatement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getSubstatementsStatementParserRuleCall_1_6_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulestatement_in_ruleflow_control_statement2498);
                    	    lv_substatements_19_0=rulestatement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"substatements",
                    	            		lv_substatements_19_0, 
                    	            		"statement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);

                    otherlv_20=(Token)match(input,42,FOLLOW_42_in_ruleflow_control_statement2511); 

                        	newLeafNode(otherlv_20, grammarAccess.getFlow_control_statementAccess().getEndKeyword_1_7());
                        
                    otherlv_21=(Token)match(input,43,FOLLOW_43_in_ruleflow_control_statement2523); 

                        	newLeafNode(otherlv_21, grammarAccess.getFlow_control_statementAccess().getForKeyword_1_8());
                        

                    }


                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1056:6: ( () otherlv_23= 'while' ( (lv_expr_24_0= ruleexpression ) ) otherlv_25= 'do' ( (lv_substatements_26_0= rulestatement ) )* otherlv_27= 'end' otherlv_28= 'while' )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1056:6: ( () otherlv_23= 'while' ( (lv_expr_24_0= ruleexpression ) ) otherlv_25= 'do' ( (lv_substatements_26_0= rulestatement ) )* otherlv_27= 'end' otherlv_28= 'while' )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1056:7: () otherlv_23= 'while' ( (lv_expr_24_0= ruleexpression ) ) otherlv_25= 'do' ( (lv_substatements_26_0= rulestatement ) )* otherlv_27= 'end' otherlv_28= 'while'
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1056:7: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1057:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getFlow_control_statementAccess().getTypeStatementWhileAction_2_0(),
                                current);
                        

                    }

                    otherlv_23=(Token)match(input,45,FOLLOW_45_in_ruleflow_control_statement2552); 

                        	newLeafNode(otherlv_23, grammarAccess.getFlow_control_statementAccess().getWhileKeyword_2_1());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1066:1: ( (lv_expr_24_0= ruleexpression ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1067:1: (lv_expr_24_0= ruleexpression )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1067:1: (lv_expr_24_0= ruleexpression )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1068:3: lv_expr_24_0= ruleexpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getExprExpressionParserRuleCall_2_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleexpression_in_ruleflow_control_statement2573);
                    lv_expr_24_0=ruleexpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"expr",
                            		lv_expr_24_0, 
                            		"expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_25=(Token)match(input,44,FOLLOW_44_in_ruleflow_control_statement2585); 

                        	newLeafNode(otherlv_25, grammarAccess.getFlow_control_statementAccess().getDoKeyword_2_3());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1088:1: ( (lv_substatements_26_0= rulestatement ) )*
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0==RULE_ID||LA19_0==13||LA19_0==16||LA19_0==28||(LA19_0>=31 && LA19_0<=32)||LA19_0==34||LA19_0==36||LA19_0==38||LA19_0==43||LA19_0==45) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1089:1: (lv_substatements_26_0= rulestatement )
                    	    {
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1089:1: (lv_substatements_26_0= rulestatement )
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1090:3: lv_substatements_26_0= rulestatement
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFlow_control_statementAccess().getSubstatementsStatementParserRuleCall_2_4_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_rulestatement_in_ruleflow_control_statement2606);
                    	    lv_substatements_26_0=rulestatement();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFlow_control_statementRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"substatements",
                    	            		lv_substatements_26_0, 
                    	            		"statement");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);

                    otherlv_27=(Token)match(input,42,FOLLOW_42_in_ruleflow_control_statement2619); 

                        	newLeafNode(otherlv_27, grammarAccess.getFlow_control_statementAccess().getEndKeyword_2_5());
                        
                    otherlv_28=(Token)match(input,45,FOLLOW_45_in_ruleflow_control_statement2631); 

                        	newLeafNode(otherlv_28, grammarAccess.getFlow_control_statementAccess().getWhileKeyword_2_6());
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleflow_control_statement"


    // $ANTLR start "entryRuleexpression"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1122:1: entryRuleexpression returns [EObject current=null] : iv_ruleexpression= ruleexpression EOF ;
    public final EObject entryRuleexpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleexpression = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1123:2: (iv_ruleexpression= ruleexpression EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1124:2: iv_ruleexpression= ruleexpression EOF
            {
             newCompositeNode(grammarAccess.getExpressionRule()); 
            pushFollow(FOLLOW_ruleexpression_in_entryRuleexpression2668);
            iv_ruleexpression=ruleexpression();

            state._fsp--;

             current =iv_ruleexpression; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleexpression2678); 

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
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1131:1: ruleexpression returns [EObject current=null] : ( (otherlv_0= 'not' ( (lv_ne_1_0= ruleexpression ) ) ) | ( ( (lv_ls_2_0= ruleexpr2 ) ) ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )* ) ) ;
    public final EObject ruleexpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        EObject lv_ne_1_0 = null;

        EObject lv_ls_2_0 = null;

        EObject lv_rs_9_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1134:28: ( ( (otherlv_0= 'not' ( (lv_ne_1_0= ruleexpression ) ) ) | ( ( (lv_ls_2_0= ruleexpr2 ) ) ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )* ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1135:1: ( (otherlv_0= 'not' ( (lv_ne_1_0= ruleexpression ) ) ) | ( ( (lv_ls_2_0= ruleexpr2 ) ) ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )* ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1135:1: ( (otherlv_0= 'not' ( (lv_ne_1_0= ruleexpression ) ) ) | ( ( (lv_ls_2_0= ruleexpr2 ) ) ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )* ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==46) ) {
                alt23=1;
            }
            else if ( ((LA23_0>=RULE_INT && LA23_0<=RULE_ID)||LA23_0==34||(LA23_0>=53 && LA23_0<=54)||LA23_0==60||(LA23_0>=62 && LA23_0<=63)) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1135:2: (otherlv_0= 'not' ( (lv_ne_1_0= ruleexpression ) ) )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1135:2: (otherlv_0= 'not' ( (lv_ne_1_0= ruleexpression ) ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1135:4: otherlv_0= 'not' ( (lv_ne_1_0= ruleexpression ) )
                    {
                    otherlv_0=(Token)match(input,46,FOLLOW_46_in_ruleexpression2716); 

                        	newLeafNode(otherlv_0, grammarAccess.getExpressionAccess().getNotKeyword_0_0());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1139:1: ( (lv_ne_1_0= ruleexpression ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1140:1: (lv_ne_1_0= ruleexpression )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1140:1: (lv_ne_1_0= ruleexpression )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1141:3: lv_ne_1_0= ruleexpression
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpressionAccess().getNeExpressionParserRuleCall_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleexpression_in_ruleexpression2737);
                    lv_ne_1_0=ruleexpression();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                    	        }
                           		set(
                           			current, 
                           			"ne",
                            		lv_ne_1_0, 
                            		"expression");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1158:6: ( ( (lv_ls_2_0= ruleexpr2 ) ) ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )* )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1158:6: ( ( (lv_ls_2_0= ruleexpr2 ) ) ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )* )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1158:7: ( (lv_ls_2_0= ruleexpr2 ) ) ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )*
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1158:7: ( (lv_ls_2_0= ruleexpr2 ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1159:1: (lv_ls_2_0= ruleexpr2 )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1159:1: (lv_ls_2_0= ruleexpr2 )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1160:3: lv_ls_2_0= ruleexpr2
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpressionAccess().getLsExpr2ParserRuleCall_1_0_0()); 
                    	    
                    pushFollow(FOLLOW_ruleexpr2_in_ruleexpression2766);
                    lv_ls_2_0=ruleexpr2();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                    	        }
                           		set(
                           			current, 
                           			"ls",
                            		lv_ls_2_0, 
                            		"expr2");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1176:2: ( (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) ) )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( ((LA22_0>=47 && LA22_0<=52)) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1176:3: (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' ) ( (lv_rs_9_0= ruleexpr2 ) )
                    	    {
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1176:3: (otherlv_3= '==' | otherlv_4= '<>' | otherlv_5= '<' | otherlv_6= '>' | otherlv_7= '>=' | otherlv_8= '<=' )
                    	    int alt21=6;
                    	    switch ( input.LA(1) ) {
                    	    case 47:
                    	        {
                    	        alt21=1;
                    	        }
                    	        break;
                    	    case 48:
                    	        {
                    	        alt21=2;
                    	        }
                    	        break;
                    	    case 49:
                    	        {
                    	        alt21=3;
                    	        }
                    	        break;
                    	    case 50:
                    	        {
                    	        alt21=4;
                    	        }
                    	        break;
                    	    case 51:
                    	        {
                    	        alt21=5;
                    	        }
                    	        break;
                    	    case 52:
                    	        {
                    	        alt21=6;
                    	        }
                    	        break;
                    	    default:
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 21, 0, input);

                    	        throw nvae;
                    	    }

                    	    switch (alt21) {
                    	        case 1 :
                    	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1176:5: otherlv_3= '=='
                    	            {
                    	            otherlv_3=(Token)match(input,47,FOLLOW_47_in_ruleexpression2780); 

                    	                	newLeafNode(otherlv_3, grammarAccess.getExpressionAccess().getEqualsSignEqualsSignKeyword_1_1_0_0());
                    	                

                    	            }
                    	            break;
                    	        case 2 :
                    	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1181:7: otherlv_4= '<>'
                    	            {
                    	            otherlv_4=(Token)match(input,48,FOLLOW_48_in_ruleexpression2798); 

                    	                	newLeafNode(otherlv_4, grammarAccess.getExpressionAccess().getLessThanSignGreaterThanSignKeyword_1_1_0_1());
                    	                

                    	            }
                    	            break;
                    	        case 3 :
                    	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1186:7: otherlv_5= '<'
                    	            {
                    	            otherlv_5=(Token)match(input,49,FOLLOW_49_in_ruleexpression2816); 

                    	                	newLeafNode(otherlv_5, grammarAccess.getExpressionAccess().getLessThanSignKeyword_1_1_0_2());
                    	                

                    	            }
                    	            break;
                    	        case 4 :
                    	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1191:7: otherlv_6= '>'
                    	            {
                    	            otherlv_6=(Token)match(input,50,FOLLOW_50_in_ruleexpression2834); 

                    	                	newLeafNode(otherlv_6, grammarAccess.getExpressionAccess().getGreaterThanSignKeyword_1_1_0_3());
                    	                

                    	            }
                    	            break;
                    	        case 5 :
                    	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1196:7: otherlv_7= '>='
                    	            {
                    	            otherlv_7=(Token)match(input,51,FOLLOW_51_in_ruleexpression2852); 

                    	                	newLeafNode(otherlv_7, grammarAccess.getExpressionAccess().getGreaterThanSignEqualsSignKeyword_1_1_0_4());
                    	                

                    	            }
                    	            break;
                    	        case 6 :
                    	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1201:7: otherlv_8= '<='
                    	            {
                    	            otherlv_8=(Token)match(input,52,FOLLOW_52_in_ruleexpression2870); 

                    	                	newLeafNode(otherlv_8, grammarAccess.getExpressionAccess().getLessThanSignEqualsSignKeyword_1_1_0_5());
                    	                

                    	            }
                    	            break;

                    	    }

                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1205:2: ( (lv_rs_9_0= ruleexpr2 ) )
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1206:1: (lv_rs_9_0= ruleexpr2 )
                    	    {
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1206:1: (lv_rs_9_0= ruleexpr2 )
                    	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1207:3: lv_rs_9_0= ruleexpr2
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getExpressionAccess().getRsExpr2ParserRuleCall_1_1_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleexpr2_in_ruleexpression2892);
                    	    lv_rs_9_0=ruleexpr2();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getExpressionRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"rs",
                    	            		lv_rs_9_0, 
                    	            		"expr2");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
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


    // $ANTLR start "entryRuleexpr2"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1231:1: entryRuleexpr2 returns [EObject current=null] : iv_ruleexpr2= ruleexpr2 EOF ;
    public final EObject entryRuleexpr2() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleexpr2 = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1232:2: (iv_ruleexpr2= ruleexpr2 EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1233:2: iv_ruleexpr2= ruleexpr2 EOF
            {
             newCompositeNode(grammarAccess.getExpr2Rule()); 
            pushFollow(FOLLOW_ruleexpr2_in_entryRuleexpr22931);
            iv_ruleexpr2=ruleexpr2();

            state._fsp--;

             current =iv_ruleexpr2; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleexpr22941); 

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
    // $ANTLR end "entryRuleexpr2"


    // $ANTLR start "ruleexpr2"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1240:1: ruleexpr2 returns [EObject current=null] : ( ( (otherlv_0= 'empty' | otherlv_1= 'not_empty' ) ( (lv_n_2_0= rulename ) ) ) | ( (lv_s_3_0= rulesum ) ) ) ;
    public final EObject ruleexpr2() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_n_2_0 = null;

        EObject lv_s_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1243:28: ( ( ( (otherlv_0= 'empty' | otherlv_1= 'not_empty' ) ( (lv_n_2_0= rulename ) ) ) | ( (lv_s_3_0= rulesum ) ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1244:1: ( ( (otherlv_0= 'empty' | otherlv_1= 'not_empty' ) ( (lv_n_2_0= rulename ) ) ) | ( (lv_s_3_0= rulesum ) ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1244:1: ( ( (otherlv_0= 'empty' | otherlv_1= 'not_empty' ) ( (lv_n_2_0= rulename ) ) ) | ( (lv_s_3_0= rulesum ) ) )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=53 && LA25_0<=54)) ) {
                alt25=1;
            }
            else if ( ((LA25_0>=RULE_INT && LA25_0<=RULE_ID)||LA25_0==34||LA25_0==60||(LA25_0>=62 && LA25_0<=63)) ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1244:2: ( (otherlv_0= 'empty' | otherlv_1= 'not_empty' ) ( (lv_n_2_0= rulename ) ) )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1244:2: ( (otherlv_0= 'empty' | otherlv_1= 'not_empty' ) ( (lv_n_2_0= rulename ) ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1244:3: (otherlv_0= 'empty' | otherlv_1= 'not_empty' ) ( (lv_n_2_0= rulename ) )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1244:3: (otherlv_0= 'empty' | otherlv_1= 'not_empty' )
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==53) ) {
                        alt24=1;
                    }
                    else if ( (LA24_0==54) ) {
                        alt24=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 24, 0, input);

                        throw nvae;
                    }
                    switch (alt24) {
                        case 1 :
                            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1244:5: otherlv_0= 'empty'
                            {
                            otherlv_0=(Token)match(input,53,FOLLOW_53_in_ruleexpr22980); 

                                	newLeafNode(otherlv_0, grammarAccess.getExpr2Access().getEmptyKeyword_0_0_0());
                                

                            }
                            break;
                        case 2 :
                            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1249:7: otherlv_1= 'not_empty'
                            {
                            otherlv_1=(Token)match(input,54,FOLLOW_54_in_ruleexpr22998); 

                                	newLeafNode(otherlv_1, grammarAccess.getExpr2Access().getNot_emptyKeyword_0_0_1());
                                

                            }
                            break;

                    }

                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1253:2: ( (lv_n_2_0= rulename ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1254:1: (lv_n_2_0= rulename )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1254:1: (lv_n_2_0= rulename )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1255:3: lv_n_2_0= rulename
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpr2Access().getNNameParserRuleCall_0_1_0()); 
                    	    
                    pushFollow(FOLLOW_rulename_in_ruleexpr23020);
                    lv_n_2_0=rulename();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpr2Rule());
                    	        }
                           		set(
                           			current, 
                           			"n",
                            		lv_n_2_0, 
                            		"name");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1272:6: ( (lv_s_3_0= rulesum ) )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1272:6: ( (lv_s_3_0= rulesum ) )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1273:1: (lv_s_3_0= rulesum )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1273:1: (lv_s_3_0= rulesum )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1274:3: lv_s_3_0= rulesum
                    {
                     
                    	        newCompositeNode(grammarAccess.getExpr2Access().getSSumParserRuleCall_1_0()); 
                    	    
                    pushFollow(FOLLOW_rulesum_in_ruleexpr23048);
                    lv_s_3_0=rulesum();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getExpr2Rule());
                    	        }
                           		set(
                           			current, 
                           			"s",
                            		lv_s_3_0, 
                            		"sum");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleexpr2"


    // $ANTLR start "entryRulesum"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1298:1: entryRulesum returns [EObject current=null] : iv_rulesum= rulesum EOF ;
    public final EObject entryRulesum() throws RecognitionException {
        EObject current = null;

        EObject iv_rulesum = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1299:2: (iv_rulesum= rulesum EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1300:2: iv_rulesum= rulesum EOF
            {
             newCompositeNode(grammarAccess.getSumRule()); 
            pushFollow(FOLLOW_rulesum_in_entryRulesum3084);
            iv_rulesum=rulesum();

            state._fsp--;

             current =iv_rulesum; 
            match(input,EOF,FOLLOW_EOF_in_entryRulesum3094); 

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
    // $ANTLR end "entryRulesum"


    // $ANTLR start "rulesum"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1307:1: rulesum returns [EObject current=null] : ( ( (lv_lt_0_0= ruleproduct ) ) ( (otherlv_1= '+' | otherlv_2= '-' ) ( (lv_rt_3_0= ruleproduct ) ) )* ) ;
    public final EObject rulesum() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_lt_0_0 = null;

        EObject lv_rt_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1310:28: ( ( ( (lv_lt_0_0= ruleproduct ) ) ( (otherlv_1= '+' | otherlv_2= '-' ) ( (lv_rt_3_0= ruleproduct ) ) )* ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1311:1: ( ( (lv_lt_0_0= ruleproduct ) ) ( (otherlv_1= '+' | otherlv_2= '-' ) ( (lv_rt_3_0= ruleproduct ) ) )* )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1311:1: ( ( (lv_lt_0_0= ruleproduct ) ) ( (otherlv_1= '+' | otherlv_2= '-' ) ( (lv_rt_3_0= ruleproduct ) ) )* )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1311:2: ( (lv_lt_0_0= ruleproduct ) ) ( (otherlv_1= '+' | otherlv_2= '-' ) ( (lv_rt_3_0= ruleproduct ) ) )*
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1311:2: ( (lv_lt_0_0= ruleproduct ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1312:1: (lv_lt_0_0= ruleproduct )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1312:1: (lv_lt_0_0= ruleproduct )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1313:3: lv_lt_0_0= ruleproduct
            {
             
            	        newCompositeNode(grammarAccess.getSumAccess().getLtProductParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleproduct_in_rulesum3140);
            lv_lt_0_0=ruleproduct();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getSumRule());
            	        }
                   		set(
                   			current, 
                   			"lt",
                    		lv_lt_0_0, 
                    		"product");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1329:2: ( (otherlv_1= '+' | otherlv_2= '-' ) ( (lv_rt_3_0= ruleproduct ) ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>=55 && LA27_0<=56)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1329:3: (otherlv_1= '+' | otherlv_2= '-' ) ( (lv_rt_3_0= ruleproduct ) )
            	    {
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1329:3: (otherlv_1= '+' | otherlv_2= '-' )
            	    int alt26=2;
            	    int LA26_0 = input.LA(1);

            	    if ( (LA26_0==55) ) {
            	        alt26=1;
            	    }
            	    else if ( (LA26_0==56) ) {
            	        alt26=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 26, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt26) {
            	        case 1 :
            	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1329:5: otherlv_1= '+'
            	            {
            	            otherlv_1=(Token)match(input,55,FOLLOW_55_in_rulesum3154); 

            	                	newLeafNode(otherlv_1, grammarAccess.getSumAccess().getPlusSignKeyword_1_0_0());
            	                

            	            }
            	            break;
            	        case 2 :
            	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1334:7: otherlv_2= '-'
            	            {
            	            otherlv_2=(Token)match(input,56,FOLLOW_56_in_rulesum3172); 

            	                	newLeafNode(otherlv_2, grammarAccess.getSumAccess().getHyphenMinusKeyword_1_0_1());
            	                

            	            }
            	            break;

            	    }

            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1338:2: ( (lv_rt_3_0= ruleproduct ) )
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1339:1: (lv_rt_3_0= ruleproduct )
            	    {
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1339:1: (lv_rt_3_0= ruleproduct )
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1340:3: lv_rt_3_0= ruleproduct
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getSumAccess().getRtProductParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleproduct_in_rulesum3194);
            	    lv_rt_3_0=ruleproduct();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getSumRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"rt",
            	            		lv_rt_3_0, 
            	            		"product");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulesum"


    // $ANTLR start "entryRuleproduct"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1364:1: entryRuleproduct returns [EObject current=null] : iv_ruleproduct= ruleproduct EOF ;
    public final EObject entryRuleproduct() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleproduct = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1365:2: (iv_ruleproduct= ruleproduct EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1366:2: iv_ruleproduct= ruleproduct EOF
            {
             newCompositeNode(grammarAccess.getProductRule()); 
            pushFollow(FOLLOW_ruleproduct_in_entryRuleproduct3232);
            iv_ruleproduct=ruleproduct();

            state._fsp--;

             current =iv_ruleproduct; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleproduct3242); 

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
    // $ANTLR end "entryRuleproduct"


    // $ANTLR start "ruleproduct"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1373:1: ruleproduct returns [EObject current=null] : ( ( (lv_lf_0_0= rulevalue ) ) ( (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' ) ( (lv_rf_4_0= rulevalue ) ) )* ) ;
    public final EObject ruleproduct() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_lf_0_0 = null;

        EObject lv_rf_4_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1376:28: ( ( ( (lv_lf_0_0= rulevalue ) ) ( (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' ) ( (lv_rf_4_0= rulevalue ) ) )* ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1377:1: ( ( (lv_lf_0_0= rulevalue ) ) ( (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' ) ( (lv_rf_4_0= rulevalue ) ) )* )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1377:1: ( ( (lv_lf_0_0= rulevalue ) ) ( (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' ) ( (lv_rf_4_0= rulevalue ) ) )* )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1377:2: ( (lv_lf_0_0= rulevalue ) ) ( (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' ) ( (lv_rf_4_0= rulevalue ) ) )*
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1377:2: ( (lv_lf_0_0= rulevalue ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1378:1: (lv_lf_0_0= rulevalue )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1378:1: (lv_lf_0_0= rulevalue )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1379:3: lv_lf_0_0= rulevalue
            {
             
            	        newCompositeNode(grammarAccess.getProductAccess().getLfValueParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_rulevalue_in_ruleproduct3288);
            lv_lf_0_0=rulevalue();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getProductRule());
            	        }
                   		set(
                   			current, 
                   			"lf",
                    		lv_lf_0_0, 
                    		"value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1395:2: ( (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' ) ( (lv_rf_4_0= rulevalue ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=57 && LA29_0<=59)) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1395:3: (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' ) ( (lv_rf_4_0= rulevalue ) )
            	    {
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1395:3: (otherlv_1= '*' | otherlv_2= '/' | otherlv_3= '%' )
            	    int alt28=3;
            	    switch ( input.LA(1) ) {
            	    case 57:
            	        {
            	        alt28=1;
            	        }
            	        break;
            	    case 58:
            	        {
            	        alt28=2;
            	        }
            	        break;
            	    case 59:
            	        {
            	        alt28=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 28, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt28) {
            	        case 1 :
            	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1395:5: otherlv_1= '*'
            	            {
            	            otherlv_1=(Token)match(input,57,FOLLOW_57_in_ruleproduct3302); 

            	                	newLeafNode(otherlv_1, grammarAccess.getProductAccess().getAsteriskKeyword_1_0_0());
            	                

            	            }
            	            break;
            	        case 2 :
            	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1400:7: otherlv_2= '/'
            	            {
            	            otherlv_2=(Token)match(input,58,FOLLOW_58_in_ruleproduct3320); 

            	                	newLeafNode(otherlv_2, grammarAccess.getProductAccess().getSolidusKeyword_1_0_1());
            	                

            	            }
            	            break;
            	        case 3 :
            	            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1405:7: otherlv_3= '%'
            	            {
            	            otherlv_3=(Token)match(input,59,FOLLOW_59_in_ruleproduct3338); 

            	                	newLeafNode(otherlv_3, grammarAccess.getProductAccess().getPercentSignKeyword_1_0_2());
            	                

            	            }
            	            break;

            	    }

            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1409:2: ( (lv_rf_4_0= rulevalue ) )
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1410:1: (lv_rf_4_0= rulevalue )
            	    {
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1410:1: (lv_rf_4_0= rulevalue )
            	    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1411:3: lv_rf_4_0= rulevalue
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getProductAccess().getRfValueParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulevalue_in_ruleproduct3360);
            	    lv_rf_4_0=rulevalue();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getProductRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"rf",
            	            		lv_rf_4_0, 
            	            		"value");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleproduct"


    // $ANTLR start "entryRulevalue"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1435:1: entryRulevalue returns [EObject current=null] : iv_rulevalue= rulevalue EOF ;
    public final EObject entryRulevalue() throws RecognitionException {
        EObject current = null;

        EObject iv_rulevalue = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1436:2: (iv_rulevalue= rulevalue EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1437:2: iv_rulevalue= rulevalue EOF
            {
             newCompositeNode(grammarAccess.getValueRule()); 
            pushFollow(FOLLOW_rulevalue_in_entryRulevalue3398);
            iv_rulevalue=rulevalue();

            state._fsp--;

             current =iv_rulevalue; 
            match(input,EOF,FOLLOW_EOF_in_entryRulevalue3408); 

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
    // $ANTLR end "entryRulevalue"


    // $ANTLR start "rulevalue"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1444:1: rulevalue returns [EObject current=null] : ( (otherlv_0= '(' this_expression_1= ruleexpression otherlv_2= ')' ) | ( rulevariable () ) | (otherlv_5= 'true' () ) | (otherlv_7= 'false' () ) | (this_INT_9= RULE_INT () ) ) ;
    public final EObject rulevalue() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token this_INT_9=null;
        EObject this_expression_1 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1447:28: ( ( (otherlv_0= '(' this_expression_1= ruleexpression otherlv_2= ')' ) | ( rulevariable () ) | (otherlv_5= 'true' () ) | (otherlv_7= 'false' () ) | (this_INT_9= RULE_INT () ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1448:1: ( (otherlv_0= '(' this_expression_1= ruleexpression otherlv_2= ')' ) | ( rulevariable () ) | (otherlv_5= 'true' () ) | (otherlv_7= 'false' () ) | (this_INT_9= RULE_INT () ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1448:1: ( (otherlv_0= '(' this_expression_1= ruleexpression otherlv_2= ')' ) | ( rulevariable () ) | (otherlv_5= 'true' () ) | (otherlv_7= 'false' () ) | (this_INT_9= RULE_INT () ) )
            int alt30=5;
            switch ( input.LA(1) ) {
            case 60:
                {
                alt30=1;
                }
                break;
            case RULE_ID:
            case 34:
                {
                alt30=2;
                }
                break;
            case 62:
                {
                alt30=3;
                }
                break;
            case 63:
                {
                alt30=4;
                }
                break;
            case RULE_INT:
                {
                alt30=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1448:2: (otherlv_0= '(' this_expression_1= ruleexpression otherlv_2= ')' )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1448:2: (otherlv_0= '(' this_expression_1= ruleexpression otherlv_2= ')' )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1448:4: otherlv_0= '(' this_expression_1= ruleexpression otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,60,FOLLOW_60_in_rulevalue3446); 

                        	newLeafNode(otherlv_0, grammarAccess.getValueAccess().getLeftParenthesisKeyword_0_0());
                        
                     
                            newCompositeNode(grammarAccess.getValueAccess().getExpressionParserRuleCall_0_1()); 
                        
                    pushFollow(FOLLOW_ruleexpression_in_rulevalue3468);
                    this_expression_1=ruleexpression();

                    state._fsp--;

                     
                            current = this_expression_1; 
                            afterParserOrEnumRuleCall();
                        
                    otherlv_2=(Token)match(input,61,FOLLOW_61_in_rulevalue3479); 

                        	newLeafNode(otherlv_2, grammarAccess.getValueAccess().getRightParenthesisKeyword_0_2());
                        

                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1466:6: ( rulevariable () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1466:6: ( rulevariable () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1467:5: rulevariable ()
                    {
                     
                            newCompositeNode(grammarAccess.getValueAccess().getVariableParserRuleCall_1_0()); 
                        
                    pushFollow(FOLLOW_rulevariable_in_rulevalue3503);
                    rulevariable();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1474:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1475:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getValueAccess().getTypeValueVariableAction_1_1(),
                                current);
                        

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1481:6: (otherlv_5= 'true' () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1481:6: (otherlv_5= 'true' () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1481:8: otherlv_5= 'true' ()
                    {
                    otherlv_5=(Token)match(input,62,FOLLOW_62_in_rulevalue3531); 

                        	newLeafNode(otherlv_5, grammarAccess.getValueAccess().getTrueKeyword_2_0());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1485:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1486:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getValueAccess().getTypeConstantAction_2_1(),
                                current);
                        

                    }


                    }


                    }
                    break;
                case 4 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1492:6: (otherlv_7= 'false' () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1492:6: (otherlv_7= 'false' () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1492:8: otherlv_7= 'false' ()
                    {
                    otherlv_7=(Token)match(input,63,FOLLOW_63_in_rulevalue3560); 

                        	newLeafNode(otherlv_7, grammarAccess.getValueAccess().getFalseKeyword_3_0());
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1496:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1497:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getValueAccess().getTypeConstantAction_3_1(),
                                current);
                        

                    }


                    }


                    }
                    break;
                case 5 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1503:6: (this_INT_9= RULE_INT () )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1503:6: (this_INT_9= RULE_INT () )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1503:7: this_INT_9= RULE_INT ()
                    {
                    this_INT_9=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_rulevalue3588); 
                     
                        newLeafNode(this_INT_9, grammarAccess.getValueAccess().getINTTerminalRuleCall_4_0()); 
                        
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1507:1: ()
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1508:5: 
                    {

                            current = forceCreateModelElement(
                                grammarAccess.getValueAccess().getTypeConstantAction_4_1(),
                                current);
                        

                    }


                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulevalue"


    // $ANTLR start "entryRulevariable"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1521:1: entryRulevariable returns [String current=null] : iv_rulevariable= rulevariable EOF ;
    public final String entryRulevariable() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulevariable = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1522:2: (iv_rulevariable= rulevariable EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1523:2: iv_rulevariable= rulevariable EOF
            {
             newCompositeNode(grammarAccess.getVariableRule()); 
            pushFollow(FOLLOW_rulevariable_in_entryRulevariable3634);
            iv_rulevariable=rulevariable();

            state._fsp--;

             current =iv_rulevariable.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulevariable3645); 

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
    // $ANTLR end "entryRulevariable"


    // $ANTLR start "rulevariable"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1530:1: rulevariable returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_name_0= rulename | (this_object_reference_1= ruleobject_reference kw= '.' this_ID_3= RULE_ID ) ) ;
    public final AntlrDatatypeRuleToken rulevariable() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_ID_3=null;
        AntlrDatatypeRuleToken this_name_0 = null;

        AntlrDatatypeRuleToken this_object_reference_1 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1533:28: ( (this_name_0= rulename | (this_object_reference_1= ruleobject_reference kw= '.' this_ID_3= RULE_ID ) ) )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1534:1: (this_name_0= rulename | (this_object_reference_1= ruleobject_reference kw= '.' this_ID_3= RULE_ID ) )
            {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1534:1: (this_name_0= rulename | (this_object_reference_1= ruleobject_reference kw= '.' this_ID_3= RULE_ID ) )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_ID) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==EOF||LA31_1==12||LA31_1==15||LA31_1==37||LA31_1==39||LA31_1==44||(LA31_1>=47 && LA31_1<=52)||(LA31_1>=55 && LA31_1<=59)||LA31_1==61) ) {
                    alt31=1;
                }
                else if ( (LA31_1==35) ) {
                    alt31=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA31_0==34) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1535:5: this_name_0= rulename
                    {
                     
                            newCompositeNode(grammarAccess.getVariableAccess().getNameParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_rulename_in_rulevariable3692);
                    this_name_0=rulename();

                    state._fsp--;


                    		current.merge(this_name_0);
                        
                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1546:6: (this_object_reference_1= ruleobject_reference kw= '.' this_ID_3= RULE_ID )
                    {
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1546:6: (this_object_reference_1= ruleobject_reference kw= '.' this_ID_3= RULE_ID )
                    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1547:5: this_object_reference_1= ruleobject_reference kw= '.' this_ID_3= RULE_ID
                    {
                     
                            newCompositeNode(grammarAccess.getVariableAccess().getObject_referenceParserRuleCall_1_0()); 
                        
                    pushFollow(FOLLOW_ruleobject_reference_in_rulevariable3726);
                    this_object_reference_1=ruleobject_reference();

                    state._fsp--;


                    		current.merge(this_object_reference_1);
                        
                     
                            afterParserOrEnumRuleCall();
                        
                    kw=(Token)match(input,35,FOLLOW_35_in_rulevariable3744); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getVariableAccess().getFullStopKeyword_1_1()); 
                        
                    this_ID_3=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulevariable3759); 

                    		current.merge(this_ID_3);
                        
                     
                        newLeafNode(this_ID_3, grammarAccess.getVariableAccess().getIDTerminalRuleCall_1_2()); 
                        

                    }


                    }
                    break;

            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulevariable"


    // $ANTLR start "entryRuleclass_name"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1578:1: entryRuleclass_name returns [String current=null] : iv_ruleclass_name= ruleclass_name EOF ;
    public final String entryRuleclass_name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleclass_name = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1579:2: (iv_ruleclass_name= ruleclass_name EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1580:2: iv_ruleclass_name= ruleclass_name EOF
            {
             newCompositeNode(grammarAccess.getClass_nameRule()); 
            pushFollow(FOLLOW_ruleclass_name_in_entryRuleclass_name3806);
            iv_ruleclass_name=ruleclass_name();

            state._fsp--;

             current =iv_ruleclass_name.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleclass_name3817); 

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
    // $ANTLR end "entryRuleclass_name"


    // $ANTLR start "ruleclass_name"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1587:1: ruleclass_name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_name_0= rulename ;
    public final AntlrDatatypeRuleToken ruleclass_name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1590:28: (this_name_0= rulename )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1592:5: this_name_0= rulename
            {
             
                    newCompositeNode(grammarAccess.getClass_nameAccess().getNameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulename_in_ruleclass_name3863);
            this_name_0=rulename();

            state._fsp--;


            		current.merge(this_name_0);
                
             
                    afterParserOrEnumRuleCall();
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleclass_name"


    // $ANTLR start "entryRulename"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1610:1: entryRulename returns [String current=null] : iv_rulename= rulename EOF ;
    public final String entryRulename() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulename = null;


        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1611:2: (iv_rulename= rulename EOF )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1612:2: iv_rulename= rulename EOF
            {
             newCompositeNode(grammarAccess.getNameRule()); 
            pushFollow(FOLLOW_rulename_in_entryRulename3908);
            iv_rulename=rulename();

            state._fsp--;

             current =iv_rulename.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulename3919); 

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
    // $ANTLR end "entryRulename"


    // $ANTLR start "rulename"
    // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1619:1: rulename returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken rulename() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1622:28: (this_ID_0= RULE_ID )
            // ../org.xtuml.bp.ui.text/src-gen/org/argouml/xtuml/parser/antlr/internal/InternalOAL.g:1623:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulename3958); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getNameAccess().getIDTerminalRuleCall()); 
                

            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "rulename"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleCode_in_entryRuleCode75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCode85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestatement_in_ruleCode130 = new BitSet(new long[]{0x0000285590012082L});
    public static final BitSet FOLLOW_rulestatement_in_entryRulestatement166 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulestatement176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleassignment_in_rulestatement223 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ruleobject_statement_in_rulestatement250 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_ruleflow_control_statement_in_rulestatement277 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_rulestatement290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleobject_statement_in_entryRuleobject_statement326 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleobject_statement336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulecreate_statement_in_ruleobject_statement378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleselect_statement_in_ruleobject_statement415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulerelate_statement_in_ruleobject_statement437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleunrelate_statement_in_ruleobject_statement469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledelete_statement_in_ruleobject_statement501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulecreate_statement_in_entryRulecreate_statement547 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulecreate_statement558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rulecreate_statement596 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_rulecreate_statement609 = new BitSet(new long[]{0x0000000000008080L});
    public static final BitSet FOLLOW_rulename_in_rulecreate_statement632 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_rulecreate_statement652 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleclass_name_in_rulecreate_statement674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleselect_statement_in_entryRuleselect_statement719 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleselect_statement729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleselect_statement766 = new BitSet(new long[]{0x00000000000E0000L});
    public static final BitSet FOLLOW_17_in_ruleselect_statement779 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_18_in_ruleselect_statement797 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_19_in_ruleselect_statement815 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_rulename_in_ruleselect_statement837 = new BitSet(new long[]{0x0000000000900000L});
    public static final BitSet FOLLOW_20_in_ruleselect_statement851 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleselect_statement863 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleselect_statement875 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleclass_name_in_ruleselect_statement891 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_ruleselect_statement903 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_ruleselect_statement925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleselect_statement946 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleselect_statement958 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_ruleobject_reference_in_ruleselect_statement974 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleselect_statement986 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleclass_name_in_ruleselect_statement1002 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_ruleselect_statement1013 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulerelation_in_ruleselect_statement1029 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleselect_statement1040 = new BitSet(new long[]{0x0000000002400002L});
    public static final BitSet FOLLOW_22_in_ruleselect_statement1055 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_ruleselect_statement1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulerelate_statement_in_entryRulerelate_statement1117 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulerelate_statement1128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_rulerelate_statement1166 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_ruleobject_reference_in_rulerelate_statement1188 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_rulerelate_statement1206 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_ruleobject_reference_in_rulerelate_statement1228 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_rulerelate_statement1246 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulerelation_in_rulerelate_statement1268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleunrelate_statement_in_entryRuleunrelate_statement1314 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleunrelate_statement1325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleunrelate_statement1363 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_ruleobject_reference_in_ruleunrelate_statement1385 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleunrelate_statement1403 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_ruleobject_reference_in_ruleunrelate_statement1425 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_30_in_ruleunrelate_statement1443 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulerelation_in_ruleunrelate_statement1465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledelete_statement_in_entryRuledelete_statement1511 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuledelete_statement1522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruledelete_statement1560 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruledelete_statement1573 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_ruledelete_statement1586 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_ruleobject_reference_in_ruledelete_statement1608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleobject_reference_in_entryRuleobject_reference1654 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleobject_reference1665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_ruleobject_reference1703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulename_in_ruleobject_reference1731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulerelation_in_entryRulerelation1777 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulerelation1788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_RELATION_NAME_in_rulerelation1828 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_35_in_rulerelation1847 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_rulerelation1862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleassignment_in_entryRuleassignment1909 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleassignment1919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleassignment1957 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_rulelvalue_in_ruleassignment1975 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_ruleassignment1986 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_ruleassignment2007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelvalue_in_entryRulelvalue2044 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulelvalue2055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulevariable_in_rulelvalue2101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleflow_control_statement_in_entryRuleflow_control_statement2145 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleflow_control_statement2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_ruleflow_control_statement2202 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_ruleflow_control_statement2223 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_ruleflow_control_statement2235 = new BitSet(new long[]{0x00002F5590012080L});
    public static final BitSet FOLLOW_rulestatement_in_ruleflow_control_statement2256 = new BitSet(new long[]{0x00002F5590012080L});
    public static final BitSet FOLLOW_40_in_ruleflow_control_statement2270 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_ruleflow_control_statement2291 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_ruleflow_control_statement2303 = new BitSet(new long[]{0x00002F5590012080L});
    public static final BitSet FOLLOW_rulestatement_in_ruleflow_control_statement2324 = new BitSet(new long[]{0x00002F5590012080L});
    public static final BitSet FOLLOW_41_in_ruleflow_control_statement2340 = new BitSet(new long[]{0x00002C5590012080L});
    public static final BitSet FOLLOW_rulestatement_in_ruleflow_control_statement2361 = new BitSet(new long[]{0x00002C5590012080L});
    public static final BitSet FOLLOW_42_in_ruleflow_control_statement2376 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_38_in_ruleflow_control_statement2388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_ruleflow_control_statement2417 = new BitSet(new long[]{0x0000001400000080L});
    public static final BitSet FOLLOW_rulelvalue_in_ruleflow_control_statement2433 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleflow_control_statement2444 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_rulename_in_ruleflow_control_statement2465 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_ruleflow_control_statement2477 = new BitSet(new long[]{0x00002C5590012080L});
    public static final BitSet FOLLOW_rulestatement_in_ruleflow_control_statement2498 = new BitSet(new long[]{0x00002C5590012080L});
    public static final BitSet FOLLOW_42_in_ruleflow_control_statement2511 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_ruleflow_control_statement2523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleflow_control_statement2552 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_ruleflow_control_statement2573 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_ruleflow_control_statement2585 = new BitSet(new long[]{0x00002C5590012080L});
    public static final BitSet FOLLOW_rulestatement_in_ruleflow_control_statement2606 = new BitSet(new long[]{0x00002C5590012080L});
    public static final BitSet FOLLOW_42_in_ruleflow_control_statement2619 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_ruleflow_control_statement2631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleexpression_in_entryRuleexpression2668 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleexpression2678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ruleexpression2716 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_ruleexpression2737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleexpr2_in_ruleexpression2766 = new BitSet(new long[]{0x001F800000000002L});
    public static final BitSet FOLLOW_47_in_ruleexpression2780 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_48_in_ruleexpression2798 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_49_in_ruleexpression2816 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_50_in_ruleexpression2834 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_51_in_ruleexpression2852 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_52_in_ruleexpression2870 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpr2_in_ruleexpression2892 = new BitSet(new long[]{0x001F800000000002L});
    public static final BitSet FOLLOW_ruleexpr2_in_entryRuleexpr22931 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleexpr22941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_ruleexpr22980 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_54_in_ruleexpr22998 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_rulename_in_ruleexpr23020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesum_in_ruleexpr23048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesum_in_entryRulesum3084 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulesum3094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleproduct_in_rulesum3140 = new BitSet(new long[]{0x0180000000000002L});
    public static final BitSet FOLLOW_55_in_rulesum3154 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_56_in_rulesum3172 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleproduct_in_rulesum3194 = new BitSet(new long[]{0x0180000000000002L});
    public static final BitSet FOLLOW_ruleproduct_in_entryRuleproduct3232 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleproduct3242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulevalue_in_ruleproduct3288 = new BitSet(new long[]{0x0E00000000000002L});
    public static final BitSet FOLLOW_57_in_ruleproduct3302 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_58_in_ruleproduct3320 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_59_in_ruleproduct3338 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_rulevalue_in_ruleproduct3360 = new BitSet(new long[]{0x0E00000000000002L});
    public static final BitSet FOLLOW_rulevalue_in_entryRulevalue3398 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulevalue3408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_rulevalue3446 = new BitSet(new long[]{0xD0604014000000C0L});
    public static final BitSet FOLLOW_ruleexpression_in_rulevalue3468 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_rulevalue3479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulevariable_in_rulevalue3503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_rulevalue3531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_rulevalue3560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_rulevalue3588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulevariable_in_entryRulevariable3634 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulevariable3645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulename_in_rulevariable3692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleobject_reference_in_rulevariable3726 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_rulevariable3744 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RULE_ID_in_rulevariable3759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleclass_name_in_entryRuleclass_name3806 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleclass_name3817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulename_in_ruleclass_name3863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulename_in_entryRulename3908 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulename3919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulename3958 = new BitSet(new long[]{0x0000000000000002L});

}