package org.xtuml.bp.xtext.oal.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.xtuml.bp.xtext.oal.services.XoalGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalXoalParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_TOK_EQUAL", "RULE_TOK_DOUBLECOLON", "RULE_TOK_LPAREN", "RULE_TOK_RPAREN", "RULE_TOK_LSQBR", "RULE_TOK_RSQBR", "RULE_TOK_STRING", "RULE_TOK_NOTEQUAL", "RULE_TOK_LESSTHAN", "RULE_TOK_LE", "RULE_TOK_GT", "RULE_TOK_GE", "RULE_TOK_PLUS", "RULE_TOK_MINUS", "RULE_TOK_TIMES", "RULE_TOK_DIV", "RULE_INT", "RULE_TOK_COLON", "RULE_TOK_COMMA", "RULE_TOK_DOT", "RULE_TOK_QMARK", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "';'", "'assign'", "'break'", "'bridge'", "'send'", "'control'", "'stop'", "'continue'", "'create'", "'event'", "'instance'", "'of'", "'object'", "'_debug'", "'delete'", "'for'", "'each'", "'in'", "'end'", "'generate'", "'if'", "'elif'", "'else'", "'implicit_assignment_statement'", "'implicit_invocation_statement'", "'implicit_ib_transform_statement'", "'relate'", "'to'", "'across'", "'return'", "'select'", "'one'", "'any'", "'many'", "'transform'", "'function_statement'", "'unrelate'", "'from'", "'while'", "'invocation rule'", "'_trace'", "'_off'", "'_on'", "'_dump'", "'_sor'", "'_stat'", "'instance_chain'", "'related'", "'by'", "'instances'", "'selected'", "'self'", "'phrase'", "'or'", "'and'", "'true'", "'false'", "'=='"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int RULE_ID=4;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int RULE_TOK_COLON=22;
    public static final int T__63=63;
    public static final int RULE_TOK_LESSTHAN=13;
    public static final int RULE_ANY_OTHER=30;
    public static final int RULE_TOK_STRING=11;
    public static final int RULE_TOK_DOUBLECOLON=6;
    public static final int RULE_TOK_QMARK=25;
    public static final int RULE_TOK_GE=16;
    public static final int T__61=61;
    public static final int EOF=-1;
    public static final int T__60=60;
    public static final int RULE_TOK_GT=15;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int RULE_TOK_DIV=20;
    public static final int T__59=59;
    public static final int RULE_TOK_RSQBR=10;
    public static final int RULE_INT=21;
    public static final int RULE_TOK_MINUS=18;
    public static final int RULE_TOK_PLUS=17;
    public static final int T__50=50;
    public static final int RULE_TOK_LPAREN=7;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__80=80;
    public static final int T__46=46;
    public static final int T__81=81;
    public static final int T__47=47;
    public static final int T__82=82;
    public static final int T__44=44;
    public static final int T__83=83;
    public static final int T__45=45;
    public static final int RULE_TOK_COMMA=23;
    public static final int T__48=48;
    public static final int RULE_TOK_NOTEQUAL=12;
    public static final int T__49=49;
    public static final int RULE_TOK_EQUAL=5;
    public static final int T__85=85;
    public static final int RULE_SL_COMMENT=28;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__88=88;
    public static final int RULE_ML_COMMENT=27;
    public static final int T__31=31;
    public static final int RULE_STRING=26;
    public static final int T__32=32;
    public static final int T__71=71;
    public static final int T__33=33;
    public static final int T__72=72;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__70=70;
    public static final int RULE_TOK_LSQBR=9;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int RULE_TOK_TIMES=19;
    public static final int T__39=39;
    public static final int RULE_TOK_RPAREN=8;
    public static final int RULE_WS=29;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int RULE_TOK_DOT=24;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int RULE_TOK_LE=14;
    public static final int T__77=77;

    // delegates
    // delegators


        public InternalXoalParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalXoalParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalXoalParser.tokenNames; }
    public String getGrammarFileName() { return "../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g"; }



     	private XoalGrammarAccess grammarAccess;
     	
        public InternalXoalParser(TokenStream input, XoalGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Code";	
       	}
       	
       	@Override
       	protected XoalGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleCode"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:67:1: entryRuleCode returns [EObject current=null] : iv_ruleCode= ruleCode EOF ;
    public final EObject entryRuleCode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCode = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:68:2: (iv_ruleCode= ruleCode EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:69:2: iv_ruleCode= ruleCode EOF
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
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:76:1: ruleCode returns [EObject current=null] : this_block_0= ruleblock ;
    public final EObject ruleCode() throws RecognitionException {
        EObject current = null;

        EObject this_block_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:79:28: (this_block_0= ruleblock )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:81:5: this_block_0= ruleblock
            {
             
                    newCompositeNode(grammarAccess.getCodeAccess().getBlockParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleblock_in_ruleCode131);
            this_block_0=ruleblock();

            state._fsp--;

             
                    current = this_block_0; 
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
    // $ANTLR end "ruleCode"


    // $ANTLR start "entryRuleblock"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:97:1: entryRuleblock returns [EObject current=null] : iv_ruleblock= ruleblock EOF ;
    public final EObject entryRuleblock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleblock = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:98:2: (iv_ruleblock= ruleblock EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:99:2: iv_ruleblock= ruleblock EOF
            {
             newCompositeNode(grammarAccess.getBlockRule()); 
            pushFollow(FOLLOW_ruleblock_in_entryRuleblock165);
            iv_ruleblock=ruleblock();

            state._fsp--;

             current =iv_ruleblock; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleblock175); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleblock"


    // $ANTLR start "ruleblock"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:106:1: ruleblock returns [EObject current=null] : ( (lv_statements_0_0= rulestatement ) )* ;
    public final EObject ruleblock() throws RecognitionException {
        EObject current = null;

        EObject lv_statements_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:109:28: ( ( (lv_statements_0_0= rulestatement ) )* )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:110:1: ( (lv_statements_0_0= rulestatement ) )*
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:110:1: ( (lv_statements_0_0= rulestatement ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=32 && LA1_0<=36)||(LA1_0>=38 && LA1_0<=39)||(LA1_0>=44 && LA1_0<=46)||(LA1_0>=50 && LA1_0<=51)||(LA1_0>=54 && LA1_0<=57)||(LA1_0>=60 && LA1_0<=61)||LA1_0==65||LA1_0==67||LA1_0==69) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:111:1: (lv_statements_0_0= rulestatement )
            	    {
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:111:1: (lv_statements_0_0= rulestatement )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:112:3: lv_statements_0_0= rulestatement
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getBlockAccess().getStatementsStatementParserRuleCall_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulestatement_in_ruleblock220);
            	    lv_statements_0_0=rulestatement();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getBlockRule());
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
    // $ANTLR end "ruleblock"


    // $ANTLR start "entryRulestatement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:136:1: entryRulestatement returns [EObject current=null] : iv_rulestatement= rulestatement EOF ;
    public final EObject entryRulestatement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulestatement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:137:2: (iv_rulestatement= rulestatement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:138:2: iv_rulestatement= rulestatement EOF
            {
             newCompositeNode(grammarAccess.getStatementRule()); 
            pushFollow(FOLLOW_rulestatement_in_entryRulestatement256);
            iv_rulestatement=rulestatement();

            state._fsp--;

             current =iv_rulestatement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulestatement266); 

            }

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
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:145:1: rulestatement returns [EObject current=null] : ( ( ( ruleimplicit_ib_transform_statement rulefunction_statement ) | ruleimplicit_assignment_statement | ruleimplicit_invocation_statement | this_assignment_statement_4= ruleassignment_statement | this_control_statement_5= rulecontrol_statement | this_break_statement_6= rulebreak_statement | this_bridge_statement_7= rulebridge_statement | this_send_statement_8= rulesend_statement | this_continue_statement_9= rulecontinue_statement | this_create_object_statement_10= rulecreate_object_statement | this_create_event_statement_11= rulecreate_event_statement | this_delete_statement_12= ruledelete_statement | this_for_statement_13= rulefor_statement | this_generate_statement_14= rulegenerate_statement | this_if_statement_15= ruleif_statement | this_relate_statement_16= rulerelate_statement | this_return_statement_17= rulereturn_statement | this_select_statement_18= ruleselect_statement | ruletransform_statement | this_while_statement_20= rulewhile_statement | this_unrelate_statement_21= ruleunrelate_statement | ruledebug_statement ) otherlv_23= ';' ) ;
    public final EObject rulestatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_23=null;
        EObject this_assignment_statement_4 = null;

        EObject this_control_statement_5 = null;

        EObject this_break_statement_6 = null;

        EObject this_bridge_statement_7 = null;

        EObject this_send_statement_8 = null;

        EObject this_continue_statement_9 = null;

        EObject this_create_object_statement_10 = null;

        EObject this_create_event_statement_11 = null;

        EObject this_delete_statement_12 = null;

        EObject this_for_statement_13 = null;

        EObject this_generate_statement_14 = null;

        EObject this_if_statement_15 = null;

        EObject this_relate_statement_16 = null;

        EObject this_return_statement_17 = null;

        EObject this_select_statement_18 = null;

        EObject this_while_statement_20 = null;

        EObject this_unrelate_statement_21 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:148:28: ( ( ( ( ruleimplicit_ib_transform_statement rulefunction_statement ) | ruleimplicit_assignment_statement | ruleimplicit_invocation_statement | this_assignment_statement_4= ruleassignment_statement | this_control_statement_5= rulecontrol_statement | this_break_statement_6= rulebreak_statement | this_bridge_statement_7= rulebridge_statement | this_send_statement_8= rulesend_statement | this_continue_statement_9= rulecontinue_statement | this_create_object_statement_10= rulecreate_object_statement | this_create_event_statement_11= rulecreate_event_statement | this_delete_statement_12= ruledelete_statement | this_for_statement_13= rulefor_statement | this_generate_statement_14= rulegenerate_statement | this_if_statement_15= ruleif_statement | this_relate_statement_16= rulerelate_statement | this_return_statement_17= rulereturn_statement | this_select_statement_18= ruleselect_statement | ruletransform_statement | this_while_statement_20= rulewhile_statement | this_unrelate_statement_21= ruleunrelate_statement | ruledebug_statement ) otherlv_23= ';' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:149:1: ( ( ( ruleimplicit_ib_transform_statement rulefunction_statement ) | ruleimplicit_assignment_statement | ruleimplicit_invocation_statement | this_assignment_statement_4= ruleassignment_statement | this_control_statement_5= rulecontrol_statement | this_break_statement_6= rulebreak_statement | this_bridge_statement_7= rulebridge_statement | this_send_statement_8= rulesend_statement | this_continue_statement_9= rulecontinue_statement | this_create_object_statement_10= rulecreate_object_statement | this_create_event_statement_11= rulecreate_event_statement | this_delete_statement_12= ruledelete_statement | this_for_statement_13= rulefor_statement | this_generate_statement_14= rulegenerate_statement | this_if_statement_15= ruleif_statement | this_relate_statement_16= rulerelate_statement | this_return_statement_17= rulereturn_statement | this_select_statement_18= ruleselect_statement | ruletransform_statement | this_while_statement_20= rulewhile_statement | this_unrelate_statement_21= ruleunrelate_statement | ruledebug_statement ) otherlv_23= ';' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:149:1: ( ( ( ruleimplicit_ib_transform_statement rulefunction_statement ) | ruleimplicit_assignment_statement | ruleimplicit_invocation_statement | this_assignment_statement_4= ruleassignment_statement | this_control_statement_5= rulecontrol_statement | this_break_statement_6= rulebreak_statement | this_bridge_statement_7= rulebridge_statement | this_send_statement_8= rulesend_statement | this_continue_statement_9= rulecontinue_statement | this_create_object_statement_10= rulecreate_object_statement | this_create_event_statement_11= rulecreate_event_statement | this_delete_statement_12= ruledelete_statement | this_for_statement_13= rulefor_statement | this_generate_statement_14= rulegenerate_statement | this_if_statement_15= ruleif_statement | this_relate_statement_16= rulerelate_statement | this_return_statement_17= rulereturn_statement | this_select_statement_18= ruleselect_statement | ruletransform_statement | this_while_statement_20= rulewhile_statement | this_unrelate_statement_21= ruleunrelate_statement | ruledebug_statement ) otherlv_23= ';' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:149:2: ( ( ruleimplicit_ib_transform_statement rulefunction_statement ) | ruleimplicit_assignment_statement | ruleimplicit_invocation_statement | this_assignment_statement_4= ruleassignment_statement | this_control_statement_5= rulecontrol_statement | this_break_statement_6= rulebreak_statement | this_bridge_statement_7= rulebridge_statement | this_send_statement_8= rulesend_statement | this_continue_statement_9= rulecontinue_statement | this_create_object_statement_10= rulecreate_object_statement | this_create_event_statement_11= rulecreate_event_statement | this_delete_statement_12= ruledelete_statement | this_for_statement_13= rulefor_statement | this_generate_statement_14= rulegenerate_statement | this_if_statement_15= ruleif_statement | this_relate_statement_16= rulerelate_statement | this_return_statement_17= rulereturn_statement | this_select_statement_18= ruleselect_statement | ruletransform_statement | this_while_statement_20= rulewhile_statement | this_unrelate_statement_21= ruleunrelate_statement | ruledebug_statement ) otherlv_23= ';'
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:149:2: ( ( ruleimplicit_ib_transform_statement rulefunction_statement ) | ruleimplicit_assignment_statement | ruleimplicit_invocation_statement | this_assignment_statement_4= ruleassignment_statement | this_control_statement_5= rulecontrol_statement | this_break_statement_6= rulebreak_statement | this_bridge_statement_7= rulebridge_statement | this_send_statement_8= rulesend_statement | this_continue_statement_9= rulecontinue_statement | this_create_object_statement_10= rulecreate_object_statement | this_create_event_statement_11= rulecreate_event_statement | this_delete_statement_12= ruledelete_statement | this_for_statement_13= rulefor_statement | this_generate_statement_14= rulegenerate_statement | this_if_statement_15= ruleif_statement | this_relate_statement_16= rulerelate_statement | this_return_statement_17= rulereturn_statement | this_select_statement_18= ruleselect_statement | ruletransform_statement | this_while_statement_20= rulewhile_statement | this_unrelate_statement_21= ruleunrelate_statement | ruledebug_statement )
            int alt2=22;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:149:3: ( ruleimplicit_ib_transform_statement rulefunction_statement )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:149:3: ( ruleimplicit_ib_transform_statement rulefunction_statement )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:150:5: ruleimplicit_ib_transform_statement rulefunction_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getImplicit_ib_transform_statementParserRuleCall_0_0_0()); 
                        
                    pushFollow(FOLLOW_ruleimplicit_ib_transform_statement_in_rulestatement309);
                    ruleimplicit_ib_transform_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getFunction_statementParserRuleCall_0_0_1()); 
                        
                    pushFollow(FOLLOW_rulefunction_statement_in_rulestatement324);
                    rulefunction_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:167:5: ruleimplicit_assignment_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getImplicit_assignment_statementParserRuleCall_0_1()); 
                        
                    pushFollow(FOLLOW_ruleimplicit_assignment_statement_in_rulestatement346);
                    ruleimplicit_assignment_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:176:5: ruleimplicit_invocation_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getImplicit_invocation_statementParserRuleCall_0_2()); 
                        
                    pushFollow(FOLLOW_ruleimplicit_invocation_statement_in_rulestatement367);
                    ruleimplicit_invocation_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 4 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:185:5: this_assignment_statement_4= ruleassignment_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getAssignment_statementParserRuleCall_0_3()); 
                        
                    pushFollow(FOLLOW_ruleassignment_statement_in_rulestatement394);
                    this_assignment_statement_4=ruleassignment_statement();

                    state._fsp--;

                     
                            current = this_assignment_statement_4; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 5 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:195:5: this_control_statement_5= rulecontrol_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getControl_statementParserRuleCall_0_4()); 
                        
                    pushFollow(FOLLOW_rulecontrol_statement_in_rulestatement421);
                    this_control_statement_5=rulecontrol_statement();

                    state._fsp--;

                     
                            current = this_control_statement_5; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 6 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:205:5: this_break_statement_6= rulebreak_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getBreak_statementParserRuleCall_0_5()); 
                        
                    pushFollow(FOLLOW_rulebreak_statement_in_rulestatement448);
                    this_break_statement_6=rulebreak_statement();

                    state._fsp--;

                     
                            current = this_break_statement_6; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 7 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:215:5: this_bridge_statement_7= rulebridge_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getBridge_statementParserRuleCall_0_6()); 
                        
                    pushFollow(FOLLOW_rulebridge_statement_in_rulestatement475);
                    this_bridge_statement_7=rulebridge_statement();

                    state._fsp--;

                     
                            current = this_bridge_statement_7; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 8 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:225:5: this_send_statement_8= rulesend_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getSend_statementParserRuleCall_0_7()); 
                        
                    pushFollow(FOLLOW_rulesend_statement_in_rulestatement502);
                    this_send_statement_8=rulesend_statement();

                    state._fsp--;

                     
                            current = this_send_statement_8; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 9 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:235:5: this_continue_statement_9= rulecontinue_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getContinue_statementParserRuleCall_0_8()); 
                        
                    pushFollow(FOLLOW_rulecontinue_statement_in_rulestatement529);
                    this_continue_statement_9=rulecontinue_statement();

                    state._fsp--;

                     
                            current = this_continue_statement_9; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 10 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:245:5: this_create_object_statement_10= rulecreate_object_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getCreate_object_statementParserRuleCall_0_9()); 
                        
                    pushFollow(FOLLOW_rulecreate_object_statement_in_rulestatement556);
                    this_create_object_statement_10=rulecreate_object_statement();

                    state._fsp--;

                     
                            current = this_create_object_statement_10; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 11 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:255:5: this_create_event_statement_11= rulecreate_event_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getCreate_event_statementParserRuleCall_0_10()); 
                        
                    pushFollow(FOLLOW_rulecreate_event_statement_in_rulestatement583);
                    this_create_event_statement_11=rulecreate_event_statement();

                    state._fsp--;

                     
                            current = this_create_event_statement_11; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 12 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:265:5: this_delete_statement_12= ruledelete_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getDelete_statementParserRuleCall_0_11()); 
                        
                    pushFollow(FOLLOW_ruledelete_statement_in_rulestatement610);
                    this_delete_statement_12=ruledelete_statement();

                    state._fsp--;

                     
                            current = this_delete_statement_12; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 13 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:275:5: this_for_statement_13= rulefor_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getFor_statementParserRuleCall_0_12()); 
                        
                    pushFollow(FOLLOW_rulefor_statement_in_rulestatement637);
                    this_for_statement_13=rulefor_statement();

                    state._fsp--;

                     
                            current = this_for_statement_13; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 14 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:285:5: this_generate_statement_14= rulegenerate_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getGenerate_statementParserRuleCall_0_13()); 
                        
                    pushFollow(FOLLOW_rulegenerate_statement_in_rulestatement664);
                    this_generate_statement_14=rulegenerate_statement();

                    state._fsp--;

                     
                            current = this_generate_statement_14; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 15 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:295:5: this_if_statement_15= ruleif_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getIf_statementParserRuleCall_0_14()); 
                        
                    pushFollow(FOLLOW_ruleif_statement_in_rulestatement691);
                    this_if_statement_15=ruleif_statement();

                    state._fsp--;

                     
                            current = this_if_statement_15; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 16 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:305:5: this_relate_statement_16= rulerelate_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getRelate_statementParserRuleCall_0_15()); 
                        
                    pushFollow(FOLLOW_rulerelate_statement_in_rulestatement718);
                    this_relate_statement_16=rulerelate_statement();

                    state._fsp--;

                     
                            current = this_relate_statement_16; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 17 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:315:5: this_return_statement_17= rulereturn_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getReturn_statementParserRuleCall_0_16()); 
                        
                    pushFollow(FOLLOW_rulereturn_statement_in_rulestatement745);
                    this_return_statement_17=rulereturn_statement();

                    state._fsp--;

                     
                            current = this_return_statement_17; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 18 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:325:5: this_select_statement_18= ruleselect_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getSelect_statementParserRuleCall_0_17()); 
                        
                    pushFollow(FOLLOW_ruleselect_statement_in_rulestatement772);
                    this_select_statement_18=ruleselect_statement();

                    state._fsp--;

                     
                            current = this_select_statement_18; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 19 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:335:5: ruletransform_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getTransform_statementParserRuleCall_0_18()); 
                        
                    pushFollow(FOLLOW_ruletransform_statement_in_rulestatement793);
                    ruletransform_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 20 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:344:5: this_while_statement_20= rulewhile_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getWhile_statementParserRuleCall_0_19()); 
                        
                    pushFollow(FOLLOW_rulewhile_statement_in_rulestatement820);
                    this_while_statement_20=rulewhile_statement();

                    state._fsp--;

                     
                            current = this_while_statement_20; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 21 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:354:5: this_unrelate_statement_21= ruleunrelate_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getUnrelate_statementParserRuleCall_0_20()); 
                        
                    pushFollow(FOLLOW_ruleunrelate_statement_in_rulestatement847);
                    this_unrelate_statement_21=ruleunrelate_statement();

                    state._fsp--;

                     
                            current = this_unrelate_statement_21; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 22 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:364:5: ruledebug_statement
                    {
                     
                            newCompositeNode(grammarAccess.getStatementAccess().getDebug_statementParserRuleCall_0_21()); 
                        
                    pushFollow(FOLLOW_ruledebug_statement_in_rulestatement868);
                    ruledebug_statement();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            otherlv_23=(Token)match(input,31,FOLLOW_31_in_rulestatement880); 

                	newLeafNode(otherlv_23, grammarAccess.getStatementAccess().getSemicolonKeyword_1());
                

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


    // $ANTLR start "entryRuleassignment_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:383:1: entryRuleassignment_statement returns [EObject current=null] : iv_ruleassignment_statement= ruleassignment_statement EOF ;
    public final EObject entryRuleassignment_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleassignment_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:384:2: (iv_ruleassignment_statement= ruleassignment_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:385:2: iv_ruleassignment_statement= ruleassignment_statement EOF
            {
             newCompositeNode(grammarAccess.getAssignment_statementRule()); 
            pushFollow(FOLLOW_ruleassignment_statement_in_entryRuleassignment_statement916);
            iv_ruleassignment_statement=ruleassignment_statement();

            state._fsp--;

             current =iv_ruleassignment_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleassignment_statement926); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleassignment_statement"


    // $ANTLR start "ruleassignment_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:392:1: ruleassignment_statement returns [EObject current=null] : (otherlv_0= 'assign' ( (lv_a1_1_0= ruleassignment_expr ) ) ) ;
    public final EObject ruleassignment_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_a1_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:395:28: ( (otherlv_0= 'assign' ( (lv_a1_1_0= ruleassignment_expr ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:396:1: (otherlv_0= 'assign' ( (lv_a1_1_0= ruleassignment_expr ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:396:1: (otherlv_0= 'assign' ( (lv_a1_1_0= ruleassignment_expr ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:396:3: otherlv_0= 'assign' ( (lv_a1_1_0= ruleassignment_expr ) )
            {
            otherlv_0=(Token)match(input,32,FOLLOW_32_in_ruleassignment_statement963); 

                	newLeafNode(otherlv_0, grammarAccess.getAssignment_statementAccess().getAssignKeyword_0());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:400:1: ( (lv_a1_1_0= ruleassignment_expr ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:401:1: (lv_a1_1_0= ruleassignment_expr )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:401:1: (lv_a1_1_0= ruleassignment_expr )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:402:3: lv_a1_1_0= ruleassignment_expr
            {
             
            	        newCompositeNode(grammarAccess.getAssignment_statementAccess().getA1Assignment_exprParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleassignment_expr_in_ruleassignment_statement984);
            lv_a1_1_0=ruleassignment_expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAssignment_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_1_0, 
                    		"assignment_expr");
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
    // $ANTLR end "ruleassignment_statement"


    // $ANTLR start "entryRulebreak_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:426:1: entryRulebreak_statement returns [EObject current=null] : iv_rulebreak_statement= rulebreak_statement EOF ;
    public final EObject entryRulebreak_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulebreak_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:427:2: (iv_rulebreak_statement= rulebreak_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:428:2: iv_rulebreak_statement= rulebreak_statement EOF
            {
             newCompositeNode(grammarAccess.getBreak_statementRule()); 
            pushFollow(FOLLOW_rulebreak_statement_in_entryRulebreak_statement1020);
            iv_rulebreak_statement=rulebreak_statement();

            state._fsp--;

             current =iv_rulebreak_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulebreak_statement1030); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulebreak_statement"


    // $ANTLR start "rulebreak_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:435:1: rulebreak_statement returns [EObject current=null] : ( (lv_a1_0_0= 'break' ) ) ;
    public final EObject rulebreak_statement() throws RecognitionException {
        EObject current = null;

        Token lv_a1_0_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:438:28: ( ( (lv_a1_0_0= 'break' ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:439:1: ( (lv_a1_0_0= 'break' ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:439:1: ( (lv_a1_0_0= 'break' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:440:1: (lv_a1_0_0= 'break' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:440:1: (lv_a1_0_0= 'break' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:441:3: lv_a1_0_0= 'break'
            {
            lv_a1_0_0=(Token)match(input,33,FOLLOW_33_in_rulebreak_statement1072); 

                    newLeafNode(lv_a1_0_0, grammarAccess.getBreak_statementAccess().getA1BreakKeyword_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getBreak_statementRule());
            	        }
                   		setWithLastConsumed(current, "a1", lv_a1_0_0, "break");
            	    

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
    // $ANTLR end "rulebreak_statement"


    // $ANTLR start "entryRulebridge_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:462:1: entryRulebridge_statement returns [EObject current=null] : iv_rulebridge_statement= rulebridge_statement EOF ;
    public final EObject entryRulebridge_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulebridge_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:463:2: (iv_rulebridge_statement= rulebridge_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:464:2: iv_rulebridge_statement= rulebridge_statement EOF
            {
             newCompositeNode(grammarAccess.getBridge_statementRule()); 
            pushFollow(FOLLOW_rulebridge_statement_in_entryRulebridge_statement1120);
            iv_rulebridge_statement=rulebridge_statement();

            state._fsp--;

             current =iv_rulebridge_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulebridge_statement1130); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulebridge_statement"


    // $ANTLR start "rulebridge_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:471:1: rulebridge_statement returns [EObject current=null] : ( (lv_a1_0_0= 'bridge' ) ) ;
    public final EObject rulebridge_statement() throws RecognitionException {
        EObject current = null;

        Token lv_a1_0_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:474:28: ( ( (lv_a1_0_0= 'bridge' ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:475:1: ( (lv_a1_0_0= 'bridge' ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:475:1: ( (lv_a1_0_0= 'bridge' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:476:1: (lv_a1_0_0= 'bridge' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:476:1: (lv_a1_0_0= 'bridge' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:477:3: lv_a1_0_0= 'bridge'
            {
            lv_a1_0_0=(Token)match(input,34,FOLLOW_34_in_rulebridge_statement1172); 

                    newLeafNode(lv_a1_0_0, grammarAccess.getBridge_statementAccess().getA1BridgeKeyword_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getBridge_statementRule());
            	        }
                   		setWithLastConsumed(current, "a1", lv_a1_0_0, "bridge");
            	    

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
    // $ANTLR end "rulebridge_statement"


    // $ANTLR start "entryRulesend_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:498:1: entryRulesend_statement returns [EObject current=null] : iv_rulesend_statement= rulesend_statement EOF ;
    public final EObject entryRulesend_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulesend_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:499:2: (iv_rulesend_statement= rulesend_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:500:2: iv_rulesend_statement= rulesend_statement EOF
            {
             newCompositeNode(grammarAccess.getSend_statementRule()); 
            pushFollow(FOLLOW_rulesend_statement_in_entryRulesend_statement1220);
            iv_rulesend_statement=rulesend_statement();

            state._fsp--;

             current =iv_rulesend_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulesend_statement1230); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesend_statement"


    // $ANTLR start "rulesend_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:507:1: rulesend_statement returns [EObject current=null] : ( (lv_a1_0_0= 'send' ) ) ;
    public final EObject rulesend_statement() throws RecognitionException {
        EObject current = null;

        Token lv_a1_0_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:510:28: ( ( (lv_a1_0_0= 'send' ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:511:1: ( (lv_a1_0_0= 'send' ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:511:1: ( (lv_a1_0_0= 'send' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:512:1: (lv_a1_0_0= 'send' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:512:1: (lv_a1_0_0= 'send' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:513:3: lv_a1_0_0= 'send'
            {
            lv_a1_0_0=(Token)match(input,35,FOLLOW_35_in_rulesend_statement1272); 

                    newLeafNode(lv_a1_0_0, grammarAccess.getSend_statementAccess().getA1SendKeyword_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getSend_statementRule());
            	        }
                   		setWithLastConsumed(current, "a1", lv_a1_0_0, "send");
            	    

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
    // $ANTLR end "rulesend_statement"


    // $ANTLR start "entryRulecontrol_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:534:1: entryRulecontrol_statement returns [EObject current=null] : iv_rulecontrol_statement= rulecontrol_statement EOF ;
    public final EObject entryRulecontrol_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecontrol_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:535:2: (iv_rulecontrol_statement= rulecontrol_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:536:2: iv_rulecontrol_statement= rulecontrol_statement EOF
            {
             newCompositeNode(grammarAccess.getControl_statementRule()); 
            pushFollow(FOLLOW_rulecontrol_statement_in_entryRulecontrol_statement1320);
            iv_rulecontrol_statement=rulecontrol_statement();

            state._fsp--;

             current =iv_rulecontrol_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulecontrol_statement1330); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecontrol_statement"


    // $ANTLR start "rulecontrol_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:543:1: rulecontrol_statement returns [EObject current=null] : ( ( (lv_a1_0_0= 'control' ) ) otherlv_1= 'stop' ) ;
    public final EObject rulecontrol_statement() throws RecognitionException {
        EObject current = null;

        Token lv_a1_0_0=null;
        Token otherlv_1=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:546:28: ( ( ( (lv_a1_0_0= 'control' ) ) otherlv_1= 'stop' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:547:1: ( ( (lv_a1_0_0= 'control' ) ) otherlv_1= 'stop' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:547:1: ( ( (lv_a1_0_0= 'control' ) ) otherlv_1= 'stop' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:547:2: ( (lv_a1_0_0= 'control' ) ) otherlv_1= 'stop'
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:547:2: ( (lv_a1_0_0= 'control' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:548:1: (lv_a1_0_0= 'control' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:548:1: (lv_a1_0_0= 'control' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:549:3: lv_a1_0_0= 'control'
            {
            lv_a1_0_0=(Token)match(input,36,FOLLOW_36_in_rulecontrol_statement1373); 

                    newLeafNode(lv_a1_0_0, grammarAccess.getControl_statementAccess().getA1ControlKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getControl_statementRule());
            	        }
                   		setWithLastConsumed(current, "a1", lv_a1_0_0, "control");
            	    

            }


            }

            otherlv_1=(Token)match(input,37,FOLLOW_37_in_rulecontrol_statement1398); 

                	newLeafNode(otherlv_1, grammarAccess.getControl_statementAccess().getStopKeyword_1());
                

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
    // $ANTLR end "rulecontrol_statement"


    // $ANTLR start "entryRulecontinue_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:574:1: entryRulecontinue_statement returns [EObject current=null] : iv_rulecontinue_statement= rulecontinue_statement EOF ;
    public final EObject entryRulecontinue_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecontinue_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:575:2: (iv_rulecontinue_statement= rulecontinue_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:576:2: iv_rulecontinue_statement= rulecontinue_statement EOF
            {
             newCompositeNode(grammarAccess.getContinue_statementRule()); 
            pushFollow(FOLLOW_rulecontinue_statement_in_entryRulecontinue_statement1434);
            iv_rulecontinue_statement=rulecontinue_statement();

            state._fsp--;

             current =iv_rulecontinue_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulecontinue_statement1444); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecontinue_statement"


    // $ANTLR start "rulecontinue_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:583:1: rulecontinue_statement returns [EObject current=null] : ( (lv_a1_0_0= 'continue' ) ) ;
    public final EObject rulecontinue_statement() throws RecognitionException {
        EObject current = null;

        Token lv_a1_0_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:586:28: ( ( (lv_a1_0_0= 'continue' ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:587:1: ( (lv_a1_0_0= 'continue' ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:587:1: ( (lv_a1_0_0= 'continue' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:588:1: (lv_a1_0_0= 'continue' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:588:1: (lv_a1_0_0= 'continue' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:589:3: lv_a1_0_0= 'continue'
            {
            lv_a1_0_0=(Token)match(input,38,FOLLOW_38_in_rulecontinue_statement1486); 

                    newLeafNode(lv_a1_0_0, grammarAccess.getContinue_statementAccess().getA1ContinueKeyword_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getContinue_statementRule());
            	        }
                   		setWithLastConsumed(current, "a1", lv_a1_0_0, "continue");
            	    

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
    // $ANTLR end "rulecontinue_statement"


    // $ANTLR start "entryRulecreate_event_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:610:1: entryRulecreate_event_statement returns [EObject current=null] : iv_rulecreate_event_statement= rulecreate_event_statement EOF ;
    public final EObject entryRulecreate_event_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecreate_event_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:611:2: (iv_rulecreate_event_statement= rulecreate_event_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:612:2: iv_rulecreate_event_statement= rulecreate_event_statement EOF
            {
             newCompositeNode(grammarAccess.getCreate_event_statementRule()); 
            pushFollow(FOLLOW_rulecreate_event_statement_in_entryRulecreate_event_statement1534);
            iv_rulecreate_event_statement=rulecreate_event_statement();

            state._fsp--;

             current =iv_rulecreate_event_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulecreate_event_statement1544); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecreate_event_statement"


    // $ANTLR start "rulecreate_event_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:619:1: rulecreate_event_statement returns [EObject current=null] : (otherlv_0= 'create' otherlv_1= 'event' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleevent_spec ) ) ) ;
    public final EObject rulecreate_event_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_a1_3_0 = null;

        AntlrDatatypeRuleToken lv_a2_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:622:28: ( (otherlv_0= 'create' otherlv_1= 'event' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleevent_spec ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:623:1: (otherlv_0= 'create' otherlv_1= 'event' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleevent_spec ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:623:1: (otherlv_0= 'create' otherlv_1= 'event' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleevent_spec ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:623:3: otherlv_0= 'create' otherlv_1= 'event' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleevent_spec ) )
            {
            otherlv_0=(Token)match(input,39,FOLLOW_39_in_rulecreate_event_statement1581); 

                	newLeafNode(otherlv_0, grammarAccess.getCreate_event_statementAccess().getCreateKeyword_0());
                
            otherlv_1=(Token)match(input,40,FOLLOW_40_in_rulecreate_event_statement1593); 

                	newLeafNode(otherlv_1, grammarAccess.getCreate_event_statementAccess().getEventKeyword_1());
                
            otherlv_2=(Token)match(input,41,FOLLOW_41_in_rulecreate_event_statement1605); 

                	newLeafNode(otherlv_2, grammarAccess.getCreate_event_statementAccess().getInstanceKeyword_2());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:635:1: ( (lv_a1_3_0= rulelocal_variable ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:636:1: (lv_a1_3_0= rulelocal_variable )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:636:1: (lv_a1_3_0= rulelocal_variable )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:637:3: lv_a1_3_0= rulelocal_variable
            {
             
            	        newCompositeNode(grammarAccess.getCreate_event_statementAccess().getA1Local_variableParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_rulelocal_variable_in_rulecreate_event_statement1626);
            lv_a1_3_0=rulelocal_variable();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getCreate_event_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_3_0, 
                    		"local_variable");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,42,FOLLOW_42_in_rulecreate_event_statement1638); 

                	newLeafNode(otherlv_4, grammarAccess.getCreate_event_statementAccess().getOfKeyword_4());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:657:1: ( (lv_a2_5_0= ruleevent_spec ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:658:1: (lv_a2_5_0= ruleevent_spec )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:658:1: (lv_a2_5_0= ruleevent_spec )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:659:3: lv_a2_5_0= ruleevent_spec
            {
             
            	        newCompositeNode(grammarAccess.getCreate_event_statementAccess().getA2Event_specParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleevent_spec_in_rulecreate_event_statement1659);
            lv_a2_5_0=ruleevent_spec();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getCreate_event_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a2",
                    		lv_a2_5_0, 
                    		"event_spec");
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
    // $ANTLR end "rulecreate_event_statement"


    // $ANTLR start "entryRulecreate_object_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:683:1: entryRulecreate_object_statement returns [EObject current=null] : iv_rulecreate_object_statement= rulecreate_object_statement EOF ;
    public final EObject entryRulecreate_object_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulecreate_object_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:684:2: (iv_rulecreate_object_statement= rulecreate_object_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:685:2: iv_rulecreate_object_statement= rulecreate_object_statement EOF
            {
             newCompositeNode(grammarAccess.getCreate_object_statementRule()); 
            pushFollow(FOLLOW_rulecreate_object_statement_in_entryRulecreate_object_statement1695);
            iv_rulecreate_object_statement=rulecreate_object_statement();

            state._fsp--;

             current =iv_rulecreate_object_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulecreate_object_statement1705); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecreate_object_statement"


    // $ANTLR start "rulecreate_object_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:692:1: rulecreate_object_statement returns [EObject current=null] : (otherlv_0= 'create' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleobject_keyletters ) ) ) ;
    public final EObject rulecreate_object_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_a1_3_0 = null;

        AntlrDatatypeRuleToken lv_a2_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:695:28: ( (otherlv_0= 'create' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleobject_keyletters ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:696:1: (otherlv_0= 'create' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleobject_keyletters ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:696:1: (otherlv_0= 'create' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleobject_keyletters ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:696:3: otherlv_0= 'create' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= rulelocal_variable ) ) otherlv_4= 'of' ( (lv_a2_5_0= ruleobject_keyletters ) )
            {
            otherlv_0=(Token)match(input,39,FOLLOW_39_in_rulecreate_object_statement1742); 

                	newLeafNode(otherlv_0, grammarAccess.getCreate_object_statementAccess().getCreateKeyword_0());
                
            otherlv_1=(Token)match(input,43,FOLLOW_43_in_rulecreate_object_statement1754); 

                	newLeafNode(otherlv_1, grammarAccess.getCreate_object_statementAccess().getObjectKeyword_1());
                
            otherlv_2=(Token)match(input,41,FOLLOW_41_in_rulecreate_object_statement1766); 

                	newLeafNode(otherlv_2, grammarAccess.getCreate_object_statementAccess().getInstanceKeyword_2());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:708:1: ( (lv_a1_3_0= rulelocal_variable ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:709:1: (lv_a1_3_0= rulelocal_variable )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:709:1: (lv_a1_3_0= rulelocal_variable )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:710:3: lv_a1_3_0= rulelocal_variable
            {
             
            	        newCompositeNode(grammarAccess.getCreate_object_statementAccess().getA1Local_variableParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_rulelocal_variable_in_rulecreate_object_statement1787);
            lv_a1_3_0=rulelocal_variable();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getCreate_object_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_3_0, 
                    		"local_variable");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,42,FOLLOW_42_in_rulecreate_object_statement1799); 

                	newLeafNode(otherlv_4, grammarAccess.getCreate_object_statementAccess().getOfKeyword_4());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:730:1: ( (lv_a2_5_0= ruleobject_keyletters ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:731:1: (lv_a2_5_0= ruleobject_keyletters )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:731:1: (lv_a2_5_0= ruleobject_keyletters )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:732:3: lv_a2_5_0= ruleobject_keyletters
            {
             
            	        newCompositeNode(grammarAccess.getCreate_object_statementAccess().getA2Object_keylettersParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleobject_keyletters_in_rulecreate_object_statement1820);
            lv_a2_5_0=ruleobject_keyletters();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getCreate_object_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a2",
                    		lv_a2_5_0, 
                    		"object_keyletters");
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
    // $ANTLR end "rulecreate_object_statement"


    // $ANTLR start "entryRuledebug_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:756:1: entryRuledebug_statement returns [String current=null] : iv_ruledebug_statement= ruledebug_statement EOF ;
    public final String entryRuledebug_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruledebug_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:757:2: (iv_ruledebug_statement= ruledebug_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:758:2: iv_ruledebug_statement= ruledebug_statement EOF
            {
             newCompositeNode(grammarAccess.getDebug_statementRule()); 
            pushFollow(FOLLOW_ruledebug_statement_in_entryRuledebug_statement1857);
            iv_ruledebug_statement=ruledebug_statement();

            state._fsp--;

             current =iv_ruledebug_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuledebug_statement1868); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledebug_statement"


    // $ANTLR start "ruledebug_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:765:1: ruledebug_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '_debug' (this_debug_operand_1= ruledebug_operand )+ ) ;
    public final AntlrDatatypeRuleToken ruledebug_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_debug_operand_1 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:768:28: ( (kw= '_debug' (this_debug_operand_1= ruledebug_operand )+ ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:769:1: (kw= '_debug' (this_debug_operand_1= ruledebug_operand )+ )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:769:1: (kw= '_debug' (this_debug_operand_1= ruledebug_operand )+ )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:770:2: kw= '_debug' (this_debug_operand_1= ruledebug_operand )+
            {
            kw=(Token)match(input,44,FOLLOW_44_in_ruledebug_statement1906); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getDebug_statementAccess().get_debugKeyword_0()); 
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:775:1: (this_debug_operand_1= ruledebug_operand )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=71 && LA3_0<=76)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:776:5: this_debug_operand_1= ruledebug_operand
            	    {
            	     
            	            newCompositeNode(grammarAccess.getDebug_statementAccess().getDebug_operandParserRuleCall_1()); 
            	        
            	    pushFollow(FOLLOW_ruledebug_operand_in_ruledebug_statement1929);
            	    this_debug_operand_1=ruledebug_operand();

            	    state._fsp--;


            	    		current.merge(this_debug_operand_1);
            	        
            	     
            	            afterParserOrEnumRuleCall();
            	        

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
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
    // $ANTLR end "ruledebug_statement"


    // $ANTLR start "entryRuledelete_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:794:1: entryRuledelete_statement returns [EObject current=null] : iv_ruledelete_statement= ruledelete_statement EOF ;
    public final EObject entryRuledelete_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruledelete_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:795:2: (iv_ruledelete_statement= ruledelete_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:796:2: iv_ruledelete_statement= ruledelete_statement EOF
            {
             newCompositeNode(grammarAccess.getDelete_statementRule()); 
            pushFollow(FOLLOW_ruledelete_statement_in_entryRuledelete_statement1976);
            iv_ruledelete_statement=ruledelete_statement();

            state._fsp--;

             current =iv_ruledelete_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuledelete_statement1986); 

            }

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
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:803:1: ruledelete_statement returns [EObject current=null] : (otherlv_0= 'delete' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= ruleinst_ref_var ) ) ) ;
    public final EObject ruledelete_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject lv_a1_3_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:806:28: ( (otherlv_0= 'delete' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= ruleinst_ref_var ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:807:1: (otherlv_0= 'delete' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= ruleinst_ref_var ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:807:1: (otherlv_0= 'delete' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= ruleinst_ref_var ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:807:3: otherlv_0= 'delete' otherlv_1= 'object' otherlv_2= 'instance' ( (lv_a1_3_0= ruleinst_ref_var ) )
            {
            otherlv_0=(Token)match(input,45,FOLLOW_45_in_ruledelete_statement2023); 

                	newLeafNode(otherlv_0, grammarAccess.getDelete_statementAccess().getDeleteKeyword_0());
                
            otherlv_1=(Token)match(input,43,FOLLOW_43_in_ruledelete_statement2035); 

                	newLeafNode(otherlv_1, grammarAccess.getDelete_statementAccess().getObjectKeyword_1());
                
            otherlv_2=(Token)match(input,41,FOLLOW_41_in_ruledelete_statement2047); 

                	newLeafNode(otherlv_2, grammarAccess.getDelete_statementAccess().getInstanceKeyword_2());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:819:1: ( (lv_a1_3_0= ruleinst_ref_var ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:820:1: (lv_a1_3_0= ruleinst_ref_var )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:820:1: (lv_a1_3_0= ruleinst_ref_var )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:821:3: lv_a1_3_0= ruleinst_ref_var
            {
             
            	        newCompositeNode(grammarAccess.getDelete_statementAccess().getA1Inst_ref_varParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleinst_ref_var_in_ruledelete_statement2068);
            lv_a1_3_0=ruleinst_ref_var();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getDelete_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_3_0, 
                    		"inst_ref_var");
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
    // $ANTLR end "ruledelete_statement"


    // $ANTLR start "entryRulefor_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:845:1: entryRulefor_statement returns [EObject current=null] : iv_rulefor_statement= rulefor_statement EOF ;
    public final EObject entryRulefor_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulefor_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:846:2: (iv_rulefor_statement= rulefor_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:847:2: iv_rulefor_statement= rulefor_statement EOF
            {
             newCompositeNode(grammarAccess.getFor_statementRule()); 
            pushFollow(FOLLOW_rulefor_statement_in_entryRulefor_statement2104);
            iv_rulefor_statement=rulefor_statement();

            state._fsp--;

             current =iv_rulefor_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulefor_statement2114); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefor_statement"


    // $ANTLR start "rulefor_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:854:1: rulefor_statement returns [EObject current=null] : (otherlv_0= 'for' otherlv_1= 'each' ( (lv_a1_2_0= rulelocal_variable ) ) otherlv_3= 'in' ( (lv_a2_4_0= ruleinst_ref_set_var ) ) ( (lv_a3_5_0= ruleblock ) ) otherlv_6= 'end' otherlv_7= 'for' ) ;
    public final EObject rulefor_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_a1_2_0 = null;

        EObject lv_a2_4_0 = null;

        EObject lv_a3_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:857:28: ( (otherlv_0= 'for' otherlv_1= 'each' ( (lv_a1_2_0= rulelocal_variable ) ) otherlv_3= 'in' ( (lv_a2_4_0= ruleinst_ref_set_var ) ) ( (lv_a3_5_0= ruleblock ) ) otherlv_6= 'end' otherlv_7= 'for' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:858:1: (otherlv_0= 'for' otherlv_1= 'each' ( (lv_a1_2_0= rulelocal_variable ) ) otherlv_3= 'in' ( (lv_a2_4_0= ruleinst_ref_set_var ) ) ( (lv_a3_5_0= ruleblock ) ) otherlv_6= 'end' otherlv_7= 'for' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:858:1: (otherlv_0= 'for' otherlv_1= 'each' ( (lv_a1_2_0= rulelocal_variable ) ) otherlv_3= 'in' ( (lv_a2_4_0= ruleinst_ref_set_var ) ) ( (lv_a3_5_0= ruleblock ) ) otherlv_6= 'end' otherlv_7= 'for' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:858:3: otherlv_0= 'for' otherlv_1= 'each' ( (lv_a1_2_0= rulelocal_variable ) ) otherlv_3= 'in' ( (lv_a2_4_0= ruleinst_ref_set_var ) ) ( (lv_a3_5_0= ruleblock ) ) otherlv_6= 'end' otherlv_7= 'for'
            {
            otherlv_0=(Token)match(input,46,FOLLOW_46_in_rulefor_statement2151); 

                	newLeafNode(otherlv_0, grammarAccess.getFor_statementAccess().getForKeyword_0());
                
            otherlv_1=(Token)match(input,47,FOLLOW_47_in_rulefor_statement2163); 

                	newLeafNode(otherlv_1, grammarAccess.getFor_statementAccess().getEachKeyword_1());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:866:1: ( (lv_a1_2_0= rulelocal_variable ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:867:1: (lv_a1_2_0= rulelocal_variable )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:867:1: (lv_a1_2_0= rulelocal_variable )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:868:3: lv_a1_2_0= rulelocal_variable
            {
             
            	        newCompositeNode(grammarAccess.getFor_statementAccess().getA1Local_variableParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_rulelocal_variable_in_rulefor_statement2184);
            lv_a1_2_0=rulelocal_variable();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getFor_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_2_0, 
                    		"local_variable");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,48,FOLLOW_48_in_rulefor_statement2196); 

                	newLeafNode(otherlv_3, grammarAccess.getFor_statementAccess().getInKeyword_3());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:888:1: ( (lv_a2_4_0= ruleinst_ref_set_var ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:889:1: (lv_a2_4_0= ruleinst_ref_set_var )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:889:1: (lv_a2_4_0= ruleinst_ref_set_var )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:890:3: lv_a2_4_0= ruleinst_ref_set_var
            {
             
            	        newCompositeNode(grammarAccess.getFor_statementAccess().getA2Inst_ref_set_varParserRuleCall_4_0()); 
            	    
            pushFollow(FOLLOW_ruleinst_ref_set_var_in_rulefor_statement2217);
            lv_a2_4_0=ruleinst_ref_set_var();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getFor_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a2",
                    		lv_a2_4_0, 
                    		"inst_ref_set_var");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:906:2: ( (lv_a3_5_0= ruleblock ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:907:1: (lv_a3_5_0= ruleblock )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:907:1: (lv_a3_5_0= ruleblock )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:908:3: lv_a3_5_0= ruleblock
            {
             
            	        newCompositeNode(grammarAccess.getFor_statementAccess().getA3BlockParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_ruleblock_in_rulefor_statement2238);
            lv_a3_5_0=ruleblock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getFor_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a3",
                    		lv_a3_5_0, 
                    		"block");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_6=(Token)match(input,49,FOLLOW_49_in_rulefor_statement2250); 

                	newLeafNode(otherlv_6, grammarAccess.getFor_statementAccess().getEndKeyword_6());
                
            otherlv_7=(Token)match(input,46,FOLLOW_46_in_rulefor_statement2262); 

                	newLeafNode(otherlv_7, grammarAccess.getFor_statementAccess().getForKeyword_7());
                

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
    // $ANTLR end "rulefor_statement"


    // $ANTLR start "entryRulegenerate_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:940:1: entryRulegenerate_statement returns [EObject current=null] : iv_rulegenerate_statement= rulegenerate_statement EOF ;
    public final EObject entryRulegenerate_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulegenerate_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:941:2: (iv_rulegenerate_statement= rulegenerate_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:942:2: iv_rulegenerate_statement= rulegenerate_statement EOF
            {
             newCompositeNode(grammarAccess.getGenerate_statementRule()); 
            pushFollow(FOLLOW_rulegenerate_statement_in_entryRulegenerate_statement2298);
            iv_rulegenerate_statement=rulegenerate_statement();

            state._fsp--;

             current =iv_rulegenerate_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulegenerate_statement2308); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulegenerate_statement"


    // $ANTLR start "rulegenerate_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:949:1: rulegenerate_statement returns [EObject current=null] : (otherlv_0= 'generate' ( (lv_a1_1_0= ruleevent_spec ) ) ) ;
    public final EObject rulegenerate_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_a1_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:952:28: ( (otherlv_0= 'generate' ( (lv_a1_1_0= ruleevent_spec ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:953:1: (otherlv_0= 'generate' ( (lv_a1_1_0= ruleevent_spec ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:953:1: (otherlv_0= 'generate' ( (lv_a1_1_0= ruleevent_spec ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:953:3: otherlv_0= 'generate' ( (lv_a1_1_0= ruleevent_spec ) )
            {
            otherlv_0=(Token)match(input,50,FOLLOW_50_in_rulegenerate_statement2345); 

                	newLeafNode(otherlv_0, grammarAccess.getGenerate_statementAccess().getGenerateKeyword_0());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:957:1: ( (lv_a1_1_0= ruleevent_spec ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:958:1: (lv_a1_1_0= ruleevent_spec )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:958:1: (lv_a1_1_0= ruleevent_spec )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:959:3: lv_a1_1_0= ruleevent_spec
            {
             
            	        newCompositeNode(grammarAccess.getGenerate_statementAccess().getA1Event_specParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleevent_spec_in_rulegenerate_statement2366);
            lv_a1_1_0=ruleevent_spec();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getGenerate_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_1_0, 
                    		"event_spec");
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
    // $ANTLR end "rulegenerate_statement"


    // $ANTLR start "entryRuleif_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:983:1: entryRuleif_statement returns [EObject current=null] : iv_ruleif_statement= ruleif_statement EOF ;
    public final EObject entryRuleif_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleif_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:984:2: (iv_ruleif_statement= ruleif_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:985:2: iv_ruleif_statement= ruleif_statement EOF
            {
             newCompositeNode(grammarAccess.getIf_statementRule()); 
            pushFollow(FOLLOW_ruleif_statement_in_entryRuleif_statement2402);
            iv_ruleif_statement=ruleif_statement();

            state._fsp--;

             current =iv_ruleif_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleif_statement2412); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleif_statement"


    // $ANTLR start "ruleif_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:992:1: ruleif_statement returns [EObject current=null] : (otherlv_0= 'if' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) (otherlv_3= 'elif' ( (lv_a3_4_0= ruleexpr ) ) ( (lv_a4_5_0= ruleblock ) ) )? (otherlv_6= 'else' ( (lv_a5_7_0= ruleblock ) ) )? otherlv_8= 'end' otherlv_9= 'if' ) ;
    public final EObject ruleif_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        EObject lv_a1_1_0 = null;

        EObject lv_a2_2_0 = null;

        EObject lv_a3_4_0 = null;

        EObject lv_a4_5_0 = null;

        EObject lv_a5_7_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:995:28: ( (otherlv_0= 'if' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) (otherlv_3= 'elif' ( (lv_a3_4_0= ruleexpr ) ) ( (lv_a4_5_0= ruleblock ) ) )? (otherlv_6= 'else' ( (lv_a5_7_0= ruleblock ) ) )? otherlv_8= 'end' otherlv_9= 'if' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:996:1: (otherlv_0= 'if' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) (otherlv_3= 'elif' ( (lv_a3_4_0= ruleexpr ) ) ( (lv_a4_5_0= ruleblock ) ) )? (otherlv_6= 'else' ( (lv_a5_7_0= ruleblock ) ) )? otherlv_8= 'end' otherlv_9= 'if' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:996:1: (otherlv_0= 'if' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) (otherlv_3= 'elif' ( (lv_a3_4_0= ruleexpr ) ) ( (lv_a4_5_0= ruleblock ) ) )? (otherlv_6= 'else' ( (lv_a5_7_0= ruleblock ) ) )? otherlv_8= 'end' otherlv_9= 'if' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:996:3: otherlv_0= 'if' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) (otherlv_3= 'elif' ( (lv_a3_4_0= ruleexpr ) ) ( (lv_a4_5_0= ruleblock ) ) )? (otherlv_6= 'else' ( (lv_a5_7_0= ruleblock ) ) )? otherlv_8= 'end' otherlv_9= 'if'
            {
            otherlv_0=(Token)match(input,51,FOLLOW_51_in_ruleif_statement2449); 

                	newLeafNode(otherlv_0, grammarAccess.getIf_statementAccess().getIfKeyword_0());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1000:1: ( (lv_a1_1_0= ruleexpr ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1001:1: (lv_a1_1_0= ruleexpr )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1001:1: (lv_a1_1_0= ruleexpr )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1002:3: lv_a1_1_0= ruleexpr
            {
             
            	        newCompositeNode(grammarAccess.getIf_statementAccess().getA1ExprParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleexpr_in_ruleif_statement2470);
            lv_a1_1_0=ruleexpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getIf_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_1_0, 
                    		"expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1018:2: ( (lv_a2_2_0= ruleblock ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1019:1: (lv_a2_2_0= ruleblock )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1019:1: (lv_a2_2_0= ruleblock )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1020:3: lv_a2_2_0= ruleblock
            {
             
            	        newCompositeNode(grammarAccess.getIf_statementAccess().getA2BlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleblock_in_ruleif_statement2491);
            lv_a2_2_0=ruleblock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getIf_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a2",
                    		lv_a2_2_0, 
                    		"block");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1036:2: (otherlv_3= 'elif' ( (lv_a3_4_0= ruleexpr ) ) ( (lv_a4_5_0= ruleblock ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==52) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1036:4: otherlv_3= 'elif' ( (lv_a3_4_0= ruleexpr ) ) ( (lv_a4_5_0= ruleblock ) )
                    {
                    otherlv_3=(Token)match(input,52,FOLLOW_52_in_ruleif_statement2504); 

                        	newLeafNode(otherlv_3, grammarAccess.getIf_statementAccess().getElifKeyword_3_0());
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1040:1: ( (lv_a3_4_0= ruleexpr ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1041:1: (lv_a3_4_0= ruleexpr )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1041:1: (lv_a3_4_0= ruleexpr )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1042:3: lv_a3_4_0= ruleexpr
                    {
                     
                    	        newCompositeNode(grammarAccess.getIf_statementAccess().getA3ExprParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleexpr_in_ruleif_statement2525);
                    lv_a3_4_0=ruleexpr();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getIf_statementRule());
                    	        }
                           		add(
                           			current, 
                           			"a3",
                            		lv_a3_4_0, 
                            		"expr");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1058:2: ( (lv_a4_5_0= ruleblock ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1059:1: (lv_a4_5_0= ruleblock )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1059:1: (lv_a4_5_0= ruleblock )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1060:3: lv_a4_5_0= ruleblock
                    {
                     
                    	        newCompositeNode(grammarAccess.getIf_statementAccess().getA4BlockParserRuleCall_3_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleblock_in_ruleif_statement2546);
                    lv_a4_5_0=ruleblock();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getIf_statementRule());
                    	        }
                           		add(
                           			current, 
                           			"a4",
                            		lv_a4_5_0, 
                            		"block");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1076:4: (otherlv_6= 'else' ( (lv_a5_7_0= ruleblock ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==53) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1076:6: otherlv_6= 'else' ( (lv_a5_7_0= ruleblock ) )
                    {
                    otherlv_6=(Token)match(input,53,FOLLOW_53_in_ruleif_statement2561); 

                        	newLeafNode(otherlv_6, grammarAccess.getIf_statementAccess().getElseKeyword_4_0());
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1080:1: ( (lv_a5_7_0= ruleblock ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1081:1: (lv_a5_7_0= ruleblock )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1081:1: (lv_a5_7_0= ruleblock )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1082:3: lv_a5_7_0= ruleblock
                    {
                     
                    	        newCompositeNode(grammarAccess.getIf_statementAccess().getA5BlockParserRuleCall_4_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleblock_in_ruleif_statement2582);
                    lv_a5_7_0=ruleblock();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getIf_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"a5",
                            		lv_a5_7_0, 
                            		"block");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,49,FOLLOW_49_in_ruleif_statement2596); 

                	newLeafNode(otherlv_8, grammarAccess.getIf_statementAccess().getEndKeyword_5());
                
            otherlv_9=(Token)match(input,51,FOLLOW_51_in_ruleif_statement2608); 

                	newLeafNode(otherlv_9, grammarAccess.getIf_statementAccess().getIfKeyword_6());
                

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
    // $ANTLR end "ruleif_statement"


    // $ANTLR start "entryRuleimplicit_assignment_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1114:1: entryRuleimplicit_assignment_statement returns [String current=null] : iv_ruleimplicit_assignment_statement= ruleimplicit_assignment_statement EOF ;
    public final String entryRuleimplicit_assignment_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleimplicit_assignment_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1115:2: (iv_ruleimplicit_assignment_statement= ruleimplicit_assignment_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1116:2: iv_ruleimplicit_assignment_statement= ruleimplicit_assignment_statement EOF
            {
             newCompositeNode(grammarAccess.getImplicit_assignment_statementRule()); 
            pushFollow(FOLLOW_ruleimplicit_assignment_statement_in_entryRuleimplicit_assignment_statement2645);
            iv_ruleimplicit_assignment_statement=ruleimplicit_assignment_statement();

            state._fsp--;

             current =iv_ruleimplicit_assignment_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleimplicit_assignment_statement2656); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleimplicit_assignment_statement"


    // $ANTLR start "ruleimplicit_assignment_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1123:1: ruleimplicit_assignment_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'implicit_assignment_statement' ;
    public final AntlrDatatypeRuleToken ruleimplicit_assignment_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1126:28: (kw= 'implicit_assignment_statement' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1128:2: kw= 'implicit_assignment_statement'
            {
            kw=(Token)match(input,54,FOLLOW_54_in_ruleimplicit_assignment_statement2693); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getImplicit_assignment_statementAccess().getImplicit_assignment_statementKeyword()); 
                

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
    // $ANTLR end "ruleimplicit_assignment_statement"


    // $ANTLR start "entryRuleimplicit_invocation_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1141:1: entryRuleimplicit_invocation_statement returns [String current=null] : iv_ruleimplicit_invocation_statement= ruleimplicit_invocation_statement EOF ;
    public final String entryRuleimplicit_invocation_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleimplicit_invocation_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1142:2: (iv_ruleimplicit_invocation_statement= ruleimplicit_invocation_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1143:2: iv_ruleimplicit_invocation_statement= ruleimplicit_invocation_statement EOF
            {
             newCompositeNode(grammarAccess.getImplicit_invocation_statementRule()); 
            pushFollow(FOLLOW_ruleimplicit_invocation_statement_in_entryRuleimplicit_invocation_statement2733);
            iv_ruleimplicit_invocation_statement=ruleimplicit_invocation_statement();

            state._fsp--;

             current =iv_ruleimplicit_invocation_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleimplicit_invocation_statement2744); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleimplicit_invocation_statement"


    // $ANTLR start "ruleimplicit_invocation_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1150:1: ruleimplicit_invocation_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'implicit_invocation_statement' ;
    public final AntlrDatatypeRuleToken ruleimplicit_invocation_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1153:28: (kw= 'implicit_invocation_statement' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1155:2: kw= 'implicit_invocation_statement'
            {
            kw=(Token)match(input,55,FOLLOW_55_in_ruleimplicit_invocation_statement2781); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getImplicit_invocation_statementAccess().getImplicit_invocation_statementKeyword()); 
                

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
    // $ANTLR end "ruleimplicit_invocation_statement"


    // $ANTLR start "entryRuleimplicit_ib_transform_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1168:1: entryRuleimplicit_ib_transform_statement returns [String current=null] : iv_ruleimplicit_ib_transform_statement= ruleimplicit_ib_transform_statement EOF ;
    public final String entryRuleimplicit_ib_transform_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleimplicit_ib_transform_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1169:2: (iv_ruleimplicit_ib_transform_statement= ruleimplicit_ib_transform_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1170:2: iv_ruleimplicit_ib_transform_statement= ruleimplicit_ib_transform_statement EOF
            {
             newCompositeNode(grammarAccess.getImplicit_ib_transform_statementRule()); 
            pushFollow(FOLLOW_ruleimplicit_ib_transform_statement_in_entryRuleimplicit_ib_transform_statement2821);
            iv_ruleimplicit_ib_transform_statement=ruleimplicit_ib_transform_statement();

            state._fsp--;

             current =iv_ruleimplicit_ib_transform_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleimplicit_ib_transform_statement2832); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleimplicit_ib_transform_statement"


    // $ANTLR start "ruleimplicit_ib_transform_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1177:1: ruleimplicit_ib_transform_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'implicit_ib_transform_statement' ;
    public final AntlrDatatypeRuleToken ruleimplicit_ib_transform_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1180:28: (kw= 'implicit_ib_transform_statement' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1182:2: kw= 'implicit_ib_transform_statement'
            {
            kw=(Token)match(input,56,FOLLOW_56_in_ruleimplicit_ib_transform_statement2869); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getImplicit_ib_transform_statementAccess().getImplicit_ib_transform_statementKeyword()); 
                

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
    // $ANTLR end "ruleimplicit_ib_transform_statement"


    // $ANTLR start "entryRulerelate_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1195:1: entryRulerelate_statement returns [EObject current=null] : iv_rulerelate_statement= rulerelate_statement EOF ;
    public final EObject entryRulerelate_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulerelate_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1196:2: (iv_rulerelate_statement= rulerelate_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1197:2: iv_rulerelate_statement= rulerelate_statement EOF
            {
             newCompositeNode(grammarAccess.getRelate_statementRule()); 
            pushFollow(FOLLOW_rulerelate_statement_in_entryRulerelate_statement2908);
            iv_rulerelate_statement=rulerelate_statement();

            state._fsp--;

             current =iv_rulerelate_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulerelate_statement2918); 

            }

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
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1204:1: rulerelate_statement returns [EObject current=null] : (otherlv_0= 'relate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'to' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) ) ;
    public final EObject rulerelate_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_a1_1_0 = null;

        EObject lv_a2_3_0 = null;

        AntlrDatatypeRuleToken lv_a3_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1207:28: ( (otherlv_0= 'relate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'to' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1208:1: (otherlv_0= 'relate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'to' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1208:1: (otherlv_0= 'relate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'to' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1208:3: otherlv_0= 'relate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'to' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) )
            {
            otherlv_0=(Token)match(input,57,FOLLOW_57_in_rulerelate_statement2955); 

                	newLeafNode(otherlv_0, grammarAccess.getRelate_statementAccess().getRelateKeyword_0());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1212:1: ( (lv_a1_1_0= ruleinst_ref_var ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1213:1: (lv_a1_1_0= ruleinst_ref_var )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1213:1: (lv_a1_1_0= ruleinst_ref_var )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1214:3: lv_a1_1_0= ruleinst_ref_var
            {
             
            	        newCompositeNode(grammarAccess.getRelate_statementAccess().getA1Inst_ref_varParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleinst_ref_var_in_rulerelate_statement2976);
            lv_a1_1_0=ruleinst_ref_var();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRelate_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_1_0, 
                    		"inst_ref_var");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,58,FOLLOW_58_in_rulerelate_statement2988); 

                	newLeafNode(otherlv_2, grammarAccess.getRelate_statementAccess().getToKeyword_2());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1234:1: ( (lv_a2_3_0= ruleinst_ref_var ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1235:1: (lv_a2_3_0= ruleinst_ref_var )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1235:1: (lv_a2_3_0= ruleinst_ref_var )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1236:3: lv_a2_3_0= ruleinst_ref_var
            {
             
            	        newCompositeNode(grammarAccess.getRelate_statementAccess().getA2Inst_ref_varParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleinst_ref_var_in_rulerelate_statement3009);
            lv_a2_3_0=ruleinst_ref_var();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRelate_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a2",
                    		lv_a2_3_0, 
                    		"inst_ref_var");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,59,FOLLOW_59_in_rulerelate_statement3021); 

                	newLeafNode(otherlv_4, grammarAccess.getRelate_statementAccess().getAcrossKeyword_4());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1256:1: ( (lv_a3_5_0= rulerelationship ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1257:1: (lv_a3_5_0= rulerelationship )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1257:1: (lv_a3_5_0= rulerelationship )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1258:3: lv_a3_5_0= rulerelationship
            {
             
            	        newCompositeNode(grammarAccess.getRelate_statementAccess().getA3RelationshipParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_rulerelationship_in_rulerelate_statement3042);
            lv_a3_5_0=rulerelationship();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRelate_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a3",
                    		lv_a3_5_0, 
                    		"relationship");
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
    // $ANTLR end "rulerelate_statement"


    // $ANTLR start "entryRulereturn_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1282:1: entryRulereturn_statement returns [EObject current=null] : iv_rulereturn_statement= rulereturn_statement EOF ;
    public final EObject entryRulereturn_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulereturn_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1283:2: (iv_rulereturn_statement= rulereturn_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1284:2: iv_rulereturn_statement= rulereturn_statement EOF
            {
             newCompositeNode(grammarAccess.getReturn_statementRule()); 
            pushFollow(FOLLOW_rulereturn_statement_in_entryRulereturn_statement3078);
            iv_rulereturn_statement=rulereturn_statement();

            state._fsp--;

             current =iv_rulereturn_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulereturn_statement3088); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulereturn_statement"


    // $ANTLR start "rulereturn_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1291:1: rulereturn_statement returns [EObject current=null] : ( ( (lv_a1_0_0= 'return' ) ) ( (lv_a2_1_0= ruleexpr ) )? ) ;
    public final EObject rulereturn_statement() throws RecognitionException {
        EObject current = null;

        Token lv_a1_0_0=null;
        EObject lv_a2_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1294:28: ( ( ( (lv_a1_0_0= 'return' ) ) ( (lv_a2_1_0= ruleexpr ) )? ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1295:1: ( ( (lv_a1_0_0= 'return' ) ) ( (lv_a2_1_0= ruleexpr ) )? )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1295:1: ( ( (lv_a1_0_0= 'return' ) ) ( (lv_a2_1_0= ruleexpr ) )? )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1295:2: ( (lv_a1_0_0= 'return' ) ) ( (lv_a2_1_0= ruleexpr ) )?
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1295:2: ( (lv_a1_0_0= 'return' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1296:1: (lv_a1_0_0= 'return' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1296:1: (lv_a1_0_0= 'return' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1297:3: lv_a1_0_0= 'return'
            {
            lv_a1_0_0=(Token)match(input,60,FOLLOW_60_in_rulereturn_statement3131); 

                    newLeafNode(lv_a1_0_0, grammarAccess.getReturn_statementAccess().getA1ReturnKeyword_0_0());
                

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getReturn_statementRule());
            	        }
                   		setWithLastConsumed(current, "a1", lv_a1_0_0, "return");
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1310:2: ( (lv_a2_1_0= ruleexpr ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_TOK_LPAREN||LA6_0==RULE_TOK_STRING||LA6_0==RULE_INT||(LA6_0>=86 && LA6_0<=87)) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1311:1: (lv_a2_1_0= ruleexpr )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1311:1: (lv_a2_1_0= ruleexpr )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1312:3: lv_a2_1_0= ruleexpr
                    {
                     
                    	        newCompositeNode(grammarAccess.getReturn_statementAccess().getA2ExprParserRuleCall_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleexpr_in_rulereturn_statement3165);
                    lv_a2_1_0=ruleexpr();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getReturn_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"a2",
                            		lv_a2_1_0, 
                            		"expr");
                    	        afterParserOrEnumRuleCall();
                    	    

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
    // $ANTLR end "rulereturn_statement"


    // $ANTLR start "entryRuleselect_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1336:1: entryRuleselect_statement returns [EObject current=null] : iv_ruleselect_statement= ruleselect_statement EOF ;
    public final EObject entryRuleselect_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleselect_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1337:2: (iv_ruleselect_statement= ruleselect_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1338:2: iv_ruleselect_statement= ruleselect_statement EOF
            {
             newCompositeNode(grammarAccess.getSelect_statementRule()); 
            pushFollow(FOLLOW_ruleselect_statement_in_entryRuleselect_statement3202);
            iv_ruleselect_statement=ruleselect_statement();

            state._fsp--;

             current =iv_ruleselect_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleselect_statement3212); 

            }

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
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1345:1: ruleselect_statement returns [EObject current=null] : (otherlv_0= 'select' ( (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) ) | (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) ) | (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) ) ) ) ;
    public final EObject ruleselect_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_a1_3_0 = null;

        AntlrDatatypeRuleToken lv_a2_6_0 = null;

        AntlrDatatypeRuleToken lv_a3_9_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1348:28: ( (otherlv_0= 'select' ( (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) ) | (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) ) | (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1349:1: (otherlv_0= 'select' ( (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) ) | (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) ) | (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1349:1: (otherlv_0= 'select' ( (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) ) | (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) ) | (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1349:3: otherlv_0= 'select' ( (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) ) | (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) ) | (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) ) )
            {
            otherlv_0=(Token)match(input,61,FOLLOW_61_in_ruleselect_statement3249); 

                	newLeafNode(otherlv_0, grammarAccess.getSelect_statementAccess().getSelectKeyword_0());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1353:1: ( (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) ) | (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) ) | (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) ) )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 62:
                {
                alt7=1;
                }
                break;
            case 63:
                {
                alt7=2;
                }
                break;
            case 64:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1353:2: (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1353:2: (otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1353:4: otherlv_1= 'one' rulelocal_variable ( (lv_a1_3_0= ruleobject_spec ) )
                    {
                    otherlv_1=(Token)match(input,62,FOLLOW_62_in_ruleselect_statement3263); 

                        	newLeafNode(otherlv_1, grammarAccess.getSelect_statementAccess().getOneKeyword_1_0_0());
                        
                     
                            newCompositeNode(grammarAccess.getSelect_statementAccess().getLocal_variableParserRuleCall_1_0_1()); 
                        
                    pushFollow(FOLLOW_rulelocal_variable_in_ruleselect_statement3279);
                    rulelocal_variable();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1365:1: ( (lv_a1_3_0= ruleobject_spec ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1366:1: (lv_a1_3_0= ruleobject_spec )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1366:1: (lv_a1_3_0= ruleobject_spec )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1367:3: lv_a1_3_0= ruleobject_spec
                    {
                     
                    	        newCompositeNode(grammarAccess.getSelect_statementAccess().getA1Object_specParserRuleCall_1_0_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleobject_spec_in_ruleselect_statement3299);
                    lv_a1_3_0=ruleobject_spec();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getSelect_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"a1",
                            		lv_a1_3_0, 
                            		"object_spec");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1384:6: (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1384:6: (otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1384:8: otherlv_4= 'any' rulelocal_variable ( (lv_a2_6_0= ruleobject_spec ) )
                    {
                    otherlv_4=(Token)match(input,63,FOLLOW_63_in_ruleselect_statement3319); 

                        	newLeafNode(otherlv_4, grammarAccess.getSelect_statementAccess().getAnyKeyword_1_1_0());
                        
                     
                            newCompositeNode(grammarAccess.getSelect_statementAccess().getLocal_variableParserRuleCall_1_1_1()); 
                        
                    pushFollow(FOLLOW_rulelocal_variable_in_ruleselect_statement3335);
                    rulelocal_variable();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1396:1: ( (lv_a2_6_0= ruleobject_spec ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1397:1: (lv_a2_6_0= ruleobject_spec )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1397:1: (lv_a2_6_0= ruleobject_spec )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1398:3: lv_a2_6_0= ruleobject_spec
                    {
                     
                    	        newCompositeNode(grammarAccess.getSelect_statementAccess().getA2Object_specParserRuleCall_1_1_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleobject_spec_in_ruleselect_statement3355);
                    lv_a2_6_0=ruleobject_spec();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getSelect_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"a2",
                            		lv_a2_6_0, 
                            		"object_spec");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1415:6: (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1415:6: (otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1415:8: otherlv_7= 'many' rulelocal_variable ( (lv_a3_9_0= ruleobject_spec ) )
                    {
                    otherlv_7=(Token)match(input,64,FOLLOW_64_in_ruleselect_statement3375); 

                        	newLeafNode(otherlv_7, grammarAccess.getSelect_statementAccess().getManyKeyword_1_2_0());
                        
                     
                            newCompositeNode(grammarAccess.getSelect_statementAccess().getLocal_variableParserRuleCall_1_2_1()); 
                        
                    pushFollow(FOLLOW_rulelocal_variable_in_ruleselect_statement3391);
                    rulelocal_variable();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1427:1: ( (lv_a3_9_0= ruleobject_spec ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1428:1: (lv_a3_9_0= ruleobject_spec )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1428:1: (lv_a3_9_0= ruleobject_spec )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1429:3: lv_a3_9_0= ruleobject_spec
                    {
                     
                    	        newCompositeNode(grammarAccess.getSelect_statementAccess().getA3Object_specParserRuleCall_1_2_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleobject_spec_in_ruleselect_statement3411);
                    lv_a3_9_0=ruleobject_spec();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getSelect_statementRule());
                    	        }
                           		set(
                           			current, 
                           			"a3",
                            		lv_a3_9_0, 
                            		"object_spec");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


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


    // $ANTLR start "entryRuletransform_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1453:1: entryRuletransform_statement returns [String current=null] : iv_ruletransform_statement= ruletransform_statement EOF ;
    public final String entryRuletransform_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruletransform_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1454:2: (iv_ruletransform_statement= ruletransform_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1455:2: iv_ruletransform_statement= ruletransform_statement EOF
            {
             newCompositeNode(grammarAccess.getTransform_statementRule()); 
            pushFollow(FOLLOW_ruletransform_statement_in_entryRuletransform_statement3450);
            iv_ruletransform_statement=ruletransform_statement();

            state._fsp--;

             current =iv_ruletransform_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuletransform_statement3461); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuletransform_statement"


    // $ANTLR start "ruletransform_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1462:1: ruletransform_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'transform' ;
    public final AntlrDatatypeRuleToken ruletransform_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1465:28: (kw= 'transform' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1467:2: kw= 'transform'
            {
            kw=(Token)match(input,65,FOLLOW_65_in_ruletransform_statement3498); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getTransform_statementAccess().getTransformKeyword()); 
                

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
    // $ANTLR end "ruletransform_statement"


    // $ANTLR start "entryRulefunction_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1480:1: entryRulefunction_statement returns [String current=null] : iv_rulefunction_statement= rulefunction_statement EOF ;
    public final String entryRulefunction_statement() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulefunction_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1481:2: (iv_rulefunction_statement= rulefunction_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1482:2: iv_rulefunction_statement= rulefunction_statement EOF
            {
             newCompositeNode(grammarAccess.getFunction_statementRule()); 
            pushFollow(FOLLOW_rulefunction_statement_in_entryRulefunction_statement3538);
            iv_rulefunction_statement=rulefunction_statement();

            state._fsp--;

             current =iv_rulefunction_statement.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulefunction_statement3549); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefunction_statement"


    // $ANTLR start "rulefunction_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1489:1: rulefunction_statement returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'function_statement' ;
    public final AntlrDatatypeRuleToken rulefunction_statement() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1492:28: (kw= 'function_statement' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1494:2: kw= 'function_statement'
            {
            kw=(Token)match(input,66,FOLLOW_66_in_rulefunction_statement3586); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getFunction_statementAccess().getFunction_statementKeyword()); 
                

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
    // $ANTLR end "rulefunction_statement"


    // $ANTLR start "entryRuleunrelate_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1507:1: entryRuleunrelate_statement returns [EObject current=null] : iv_ruleunrelate_statement= ruleunrelate_statement EOF ;
    public final EObject entryRuleunrelate_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleunrelate_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1508:2: (iv_ruleunrelate_statement= ruleunrelate_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1509:2: iv_ruleunrelate_statement= ruleunrelate_statement EOF
            {
             newCompositeNode(grammarAccess.getUnrelate_statementRule()); 
            pushFollow(FOLLOW_ruleunrelate_statement_in_entryRuleunrelate_statement3625);
            iv_ruleunrelate_statement=ruleunrelate_statement();

            state._fsp--;

             current =iv_ruleunrelate_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleunrelate_statement3635); 

            }

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
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1516:1: ruleunrelate_statement returns [EObject current=null] : (otherlv_0= 'unrelate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'from' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) ) ;
    public final EObject ruleunrelate_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_a1_1_0 = null;

        EObject lv_a2_3_0 = null;

        AntlrDatatypeRuleToken lv_a3_5_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1519:28: ( (otherlv_0= 'unrelate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'from' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1520:1: (otherlv_0= 'unrelate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'from' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1520:1: (otherlv_0= 'unrelate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'from' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1520:3: otherlv_0= 'unrelate' ( (lv_a1_1_0= ruleinst_ref_var ) ) otherlv_2= 'from' ( (lv_a2_3_0= ruleinst_ref_var ) ) otherlv_4= 'across' ( (lv_a3_5_0= rulerelationship ) )
            {
            otherlv_0=(Token)match(input,67,FOLLOW_67_in_ruleunrelate_statement3672); 

                	newLeafNode(otherlv_0, grammarAccess.getUnrelate_statementAccess().getUnrelateKeyword_0());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1524:1: ( (lv_a1_1_0= ruleinst_ref_var ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1525:1: (lv_a1_1_0= ruleinst_ref_var )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1525:1: (lv_a1_1_0= ruleinst_ref_var )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1526:3: lv_a1_1_0= ruleinst_ref_var
            {
             
            	        newCompositeNode(grammarAccess.getUnrelate_statementAccess().getA1Inst_ref_varParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleinst_ref_var_in_ruleunrelate_statement3693);
            lv_a1_1_0=ruleinst_ref_var();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrelate_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_1_0, 
                    		"inst_ref_var");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,68,FOLLOW_68_in_ruleunrelate_statement3705); 

                	newLeafNode(otherlv_2, grammarAccess.getUnrelate_statementAccess().getFromKeyword_2());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1546:1: ( (lv_a2_3_0= ruleinst_ref_var ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1547:1: (lv_a2_3_0= ruleinst_ref_var )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1547:1: (lv_a2_3_0= ruleinst_ref_var )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1548:3: lv_a2_3_0= ruleinst_ref_var
            {
             
            	        newCompositeNode(grammarAccess.getUnrelate_statementAccess().getA2Inst_ref_varParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleinst_ref_var_in_ruleunrelate_statement3726);
            lv_a2_3_0=ruleinst_ref_var();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrelate_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a2",
                    		lv_a2_3_0, 
                    		"inst_ref_var");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,59,FOLLOW_59_in_ruleunrelate_statement3738); 

                	newLeafNode(otherlv_4, grammarAccess.getUnrelate_statementAccess().getAcrossKeyword_4());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1568:1: ( (lv_a3_5_0= rulerelationship ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1569:1: (lv_a3_5_0= rulerelationship )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1569:1: (lv_a3_5_0= rulerelationship )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1570:3: lv_a3_5_0= rulerelationship
            {
             
            	        newCompositeNode(grammarAccess.getUnrelate_statementAccess().getA3RelationshipParserRuleCall_5_0()); 
            	    
            pushFollow(FOLLOW_rulerelationship_in_ruleunrelate_statement3759);
            lv_a3_5_0=rulerelationship();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getUnrelate_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a3",
                    		lv_a3_5_0, 
                    		"relationship");
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
    // $ANTLR end "ruleunrelate_statement"


    // $ANTLR start "entryRulewhile_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1594:1: entryRulewhile_statement returns [EObject current=null] : iv_rulewhile_statement= rulewhile_statement EOF ;
    public final EObject entryRulewhile_statement() throws RecognitionException {
        EObject current = null;

        EObject iv_rulewhile_statement = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1595:2: (iv_rulewhile_statement= rulewhile_statement EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1596:2: iv_rulewhile_statement= rulewhile_statement EOF
            {
             newCompositeNode(grammarAccess.getWhile_statementRule()); 
            pushFollow(FOLLOW_rulewhile_statement_in_entryRulewhile_statement3795);
            iv_rulewhile_statement=rulewhile_statement();

            state._fsp--;

             current =iv_rulewhile_statement; 
            match(input,EOF,FOLLOW_EOF_in_entryRulewhile_statement3805); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulewhile_statement"


    // $ANTLR start "rulewhile_statement"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1603:1: rulewhile_statement returns [EObject current=null] : (otherlv_0= 'while' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) otherlv_3= 'end' otherlv_4= 'while' ) ;
    public final EObject rulewhile_statement() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_a1_1_0 = null;

        EObject lv_a2_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1606:28: ( (otherlv_0= 'while' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) otherlv_3= 'end' otherlv_4= 'while' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1607:1: (otherlv_0= 'while' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) otherlv_3= 'end' otherlv_4= 'while' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1607:1: (otherlv_0= 'while' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) otherlv_3= 'end' otherlv_4= 'while' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1607:3: otherlv_0= 'while' ( (lv_a1_1_0= ruleexpr ) ) ( (lv_a2_2_0= ruleblock ) ) otherlv_3= 'end' otherlv_4= 'while'
            {
            otherlv_0=(Token)match(input,69,FOLLOW_69_in_rulewhile_statement3842); 

                	newLeafNode(otherlv_0, grammarAccess.getWhile_statementAccess().getWhileKeyword_0());
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1611:1: ( (lv_a1_1_0= ruleexpr ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1612:1: (lv_a1_1_0= ruleexpr )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1612:1: (lv_a1_1_0= ruleexpr )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1613:3: lv_a1_1_0= ruleexpr
            {
             
            	        newCompositeNode(grammarAccess.getWhile_statementAccess().getA1ExprParserRuleCall_1_0()); 
            	    
            pushFollow(FOLLOW_ruleexpr_in_rulewhile_statement3863);
            lv_a1_1_0=ruleexpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWhile_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_1_0, 
                    		"expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1629:2: ( (lv_a2_2_0= ruleblock ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1630:1: (lv_a2_2_0= ruleblock )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1630:1: (lv_a2_2_0= ruleblock )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1631:3: lv_a2_2_0= ruleblock
            {
             
            	        newCompositeNode(grammarAccess.getWhile_statementAccess().getA2BlockParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleblock_in_rulewhile_statement3884);
            lv_a2_2_0=ruleblock();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getWhile_statementRule());
            	        }
                   		set(
                   			current, 
                   			"a2",
                    		lv_a2_2_0, 
                    		"block");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_3=(Token)match(input,49,FOLLOW_49_in_rulewhile_statement3896); 

                	newLeafNode(otherlv_3, grammarAccess.getWhile_statementAccess().getEndKeyword_3());
                
            otherlv_4=(Token)match(input,69,FOLLOW_69_in_rulewhile_statement3908); 

                	newLeafNode(otherlv_4, grammarAccess.getWhile_statementAccess().getWhileKeyword_4());
                

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
    // $ANTLR end "rulewhile_statement"


    // $ANTLR start "entryRuleassignment_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1663:1: entryRuleassignment_expr returns [EObject current=null] : iv_ruleassignment_expr= ruleassignment_expr EOF ;
    public final EObject entryRuleassignment_expr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleassignment_expr = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1664:2: (iv_ruleassignment_expr= ruleassignment_expr EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1665:2: iv_ruleassignment_expr= ruleassignment_expr EOF
            {
             newCompositeNode(grammarAccess.getAssignment_exprRule()); 
            pushFollow(FOLLOW_ruleassignment_expr_in_entryRuleassignment_expr3944);
            iv_ruleassignment_expr=ruleassignment_expr();

            state._fsp--;

             current =iv_ruleassignment_expr; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleassignment_expr3954); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleassignment_expr"


    // $ANTLR start "ruleassignment_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1672:1: ruleassignment_expr returns [EObject current=null] : (this_ID_0= RULE_ID this_TOK_EQUAL_1= RULE_TOK_EQUAL ( (lv_a1_2_0= ruleexpr ) ) ) ;
    public final EObject ruleassignment_expr() throws RecognitionException {
        EObject current = null;

        Token this_ID_0=null;
        Token this_TOK_EQUAL_1=null;
        EObject lv_a1_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1675:28: ( (this_ID_0= RULE_ID this_TOK_EQUAL_1= RULE_TOK_EQUAL ( (lv_a1_2_0= ruleexpr ) ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1676:1: (this_ID_0= RULE_ID this_TOK_EQUAL_1= RULE_TOK_EQUAL ( (lv_a1_2_0= ruleexpr ) ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1676:1: (this_ID_0= RULE_ID this_TOK_EQUAL_1= RULE_TOK_EQUAL ( (lv_a1_2_0= ruleexpr ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1676:2: this_ID_0= RULE_ID this_TOK_EQUAL_1= RULE_TOK_EQUAL ( (lv_a1_2_0= ruleexpr ) )
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleassignment_expr3990); 
             
                newLeafNode(this_ID_0, grammarAccess.getAssignment_exprAccess().getIDTerminalRuleCall_0()); 
                
            this_TOK_EQUAL_1=(Token)match(input,RULE_TOK_EQUAL,FOLLOW_RULE_TOK_EQUAL_in_ruleassignment_expr4000); 
             
                newLeafNode(this_TOK_EQUAL_1, grammarAccess.getAssignment_exprAccess().getTOK_EQUALTerminalRuleCall_1()); 
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1684:1: ( (lv_a1_2_0= ruleexpr ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1685:1: (lv_a1_2_0= ruleexpr )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1685:1: (lv_a1_2_0= ruleexpr )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1686:3: lv_a1_2_0= ruleexpr
            {
             
            	        newCompositeNode(grammarAccess.getAssignment_exprAccess().getA1ExprParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleexpr_in_ruleassignment_expr4020);
            lv_a1_2_0=ruleexpr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAssignment_exprRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_2_0, 
                    		"expr");
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
    // $ANTLR end "ruleassignment_expr"


    // $ANTLR start "entryRulebridge_invocation"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1710:1: entryRulebridge_invocation returns [EObject current=null] : iv_rulebridge_invocation= rulebridge_invocation EOF ;
    public final EObject entryRulebridge_invocation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulebridge_invocation = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1711:2: (iv_rulebridge_invocation= rulebridge_invocation EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1712:2: iv_rulebridge_invocation= rulebridge_invocation EOF
            {
             newCompositeNode(grammarAccess.getBridge_invocationRule()); 
            pushFollow(FOLLOW_rulebridge_invocation_in_entryRulebridge_invocation4056);
            iv_rulebridge_invocation=rulebridge_invocation();

            state._fsp--;

             current =iv_rulebridge_invocation; 
            match(input,EOF,FOLLOW_EOF_in_entryRulebridge_invocation4066); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulebridge_invocation"


    // $ANTLR start "rulebridge_invocation"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1719:1: rulebridge_invocation returns [EObject current=null] : ( ( (lv_a1_0_0= ruleee_keyletters ) ) this_TOK_DOUBLECOLON_1= RULE_TOK_DOUBLECOLON this_TOK_LPAREN_2= RULE_TOK_LPAREN this_TOK_RPAREN_3= RULE_TOK_RPAREN ) ;
    public final EObject rulebridge_invocation() throws RecognitionException {
        EObject current = null;

        Token this_TOK_DOUBLECOLON_1=null;
        Token this_TOK_LPAREN_2=null;
        Token this_TOK_RPAREN_3=null;
        AntlrDatatypeRuleToken lv_a1_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1722:28: ( ( ( (lv_a1_0_0= ruleee_keyletters ) ) this_TOK_DOUBLECOLON_1= RULE_TOK_DOUBLECOLON this_TOK_LPAREN_2= RULE_TOK_LPAREN this_TOK_RPAREN_3= RULE_TOK_RPAREN ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1723:1: ( ( (lv_a1_0_0= ruleee_keyletters ) ) this_TOK_DOUBLECOLON_1= RULE_TOK_DOUBLECOLON this_TOK_LPAREN_2= RULE_TOK_LPAREN this_TOK_RPAREN_3= RULE_TOK_RPAREN )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1723:1: ( ( (lv_a1_0_0= ruleee_keyletters ) ) this_TOK_DOUBLECOLON_1= RULE_TOK_DOUBLECOLON this_TOK_LPAREN_2= RULE_TOK_LPAREN this_TOK_RPAREN_3= RULE_TOK_RPAREN )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1723:2: ( (lv_a1_0_0= ruleee_keyletters ) ) this_TOK_DOUBLECOLON_1= RULE_TOK_DOUBLECOLON this_TOK_LPAREN_2= RULE_TOK_LPAREN this_TOK_RPAREN_3= RULE_TOK_RPAREN
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1723:2: ( (lv_a1_0_0= ruleee_keyletters ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1724:1: (lv_a1_0_0= ruleee_keyletters )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1724:1: (lv_a1_0_0= ruleee_keyletters )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1725:3: lv_a1_0_0= ruleee_keyletters
            {
             
            	        newCompositeNode(grammarAccess.getBridge_invocationAccess().getA1Ee_keylettersParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleee_keyletters_in_rulebridge_invocation4112);
            lv_a1_0_0=ruleee_keyletters();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getBridge_invocationRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"ee_keyletters");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            this_TOK_DOUBLECOLON_1=(Token)match(input,RULE_TOK_DOUBLECOLON,FOLLOW_RULE_TOK_DOUBLECOLON_in_rulebridge_invocation4123); 
             
                newLeafNode(this_TOK_DOUBLECOLON_1, grammarAccess.getBridge_invocationAccess().getTOK_DOUBLECOLONTerminalRuleCall_1()); 
                
            this_TOK_LPAREN_2=(Token)match(input,RULE_TOK_LPAREN,FOLLOW_RULE_TOK_LPAREN_in_rulebridge_invocation4133); 
             
                newLeafNode(this_TOK_LPAREN_2, grammarAccess.getBridge_invocationAccess().getTOK_LPARENTerminalRuleCall_2()); 
                
            this_TOK_RPAREN_3=(Token)match(input,RULE_TOK_RPAREN,FOLLOW_RULE_TOK_RPAREN_in_rulebridge_invocation4143); 
             
                newLeafNode(this_TOK_RPAREN_3, grammarAccess.getBridge_invocationAccess().getTOK_RPARENTerminalRuleCall_3()); 
                

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
    // $ANTLR end "rulebridge_invocation"


    // $ANTLR start "entryRuleinvocation"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1763:1: entryRuleinvocation returns [String current=null] : iv_ruleinvocation= ruleinvocation EOF ;
    public final String entryRuleinvocation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleinvocation = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1764:2: (iv_ruleinvocation= ruleinvocation EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1765:2: iv_ruleinvocation= ruleinvocation EOF
            {
             newCompositeNode(grammarAccess.getInvocationRule()); 
            pushFollow(FOLLOW_ruleinvocation_in_entryRuleinvocation4181);
            iv_ruleinvocation=ruleinvocation();

            state._fsp--;

             current =iv_ruleinvocation.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleinvocation4192); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleinvocation"


    // $ANTLR start "ruleinvocation"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1772:1: ruleinvocation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'invocation rule' ;
    public final AntlrDatatypeRuleToken ruleinvocation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1775:28: (kw= 'invocation rule' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1777:2: kw= 'invocation rule'
            {
            kw=(Token)match(input,70,FOLLOW_70_in_ruleinvocation4229); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getInvocationAccess().getInvocationRuleKeyword()); 
                

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
    // $ANTLR end "ruleinvocation"


    // $ANTLR start "entryRuledebug_operand"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1796:1: entryRuledebug_operand returns [String current=null] : iv_ruledebug_operand= ruledebug_operand EOF ;
    public final String entryRuledebug_operand() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruledebug_operand = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1797:2: (iv_ruledebug_operand= ruledebug_operand EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1798:2: iv_ruledebug_operand= ruledebug_operand EOF
            {
             newCompositeNode(grammarAccess.getDebug_operandRule()); 
            pushFollow(FOLLOW_ruledebug_operand_in_entryRuledebug_operand4275);
            iv_ruledebug_operand=ruledebug_operand();

            state._fsp--;

             current =iv_ruledebug_operand.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuledebug_operand4286); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledebug_operand"


    // $ANTLR start "ruledebug_operand"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1805:1: ruledebug_operand returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '_trace' (kw= '_off' | kw= '_on' ) ) | (kw= '_dump' (kw= '_off' | kw= '_on' ) ) | (kw= '_sor' (kw= '_off' | kw= '_on' ) ) | kw= '_on' | kw= '_off' | kw= '_stat' ) ;
    public final AntlrDatatypeRuleToken ruledebug_operand() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1808:28: ( ( (kw= '_trace' (kw= '_off' | kw= '_on' ) ) | (kw= '_dump' (kw= '_off' | kw= '_on' ) ) | (kw= '_sor' (kw= '_off' | kw= '_on' ) ) | kw= '_on' | kw= '_off' | kw= '_stat' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1809:1: ( (kw= '_trace' (kw= '_off' | kw= '_on' ) ) | (kw= '_dump' (kw= '_off' | kw= '_on' ) ) | (kw= '_sor' (kw= '_off' | kw= '_on' ) ) | kw= '_on' | kw= '_off' | kw= '_stat' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1809:1: ( (kw= '_trace' (kw= '_off' | kw= '_on' ) ) | (kw= '_dump' (kw= '_off' | kw= '_on' ) ) | (kw= '_sor' (kw= '_off' | kw= '_on' ) ) | kw= '_on' | kw= '_off' | kw= '_stat' )
            int alt11=6;
            switch ( input.LA(1) ) {
            case 71:
                {
                alt11=1;
                }
                break;
            case 74:
                {
                alt11=2;
                }
                break;
            case 75:
                {
                alt11=3;
                }
                break;
            case 73:
                {
                alt11=4;
                }
                break;
            case 72:
                {
                alt11=5;
                }
                break;
            case 76:
                {
                alt11=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1809:2: (kw= '_trace' (kw= '_off' | kw= '_on' ) )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1809:2: (kw= '_trace' (kw= '_off' | kw= '_on' ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1810:2: kw= '_trace' (kw= '_off' | kw= '_on' )
                    {
                    kw=(Token)match(input,71,FOLLOW_71_in_ruledebug_operand4325); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_traceKeyword_0_0()); 
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1815:1: (kw= '_off' | kw= '_on' )
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==72) ) {
                        alt8=1;
                    }
                    else if ( (LA8_0==73) ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 0, input);

                        throw nvae;
                    }
                    switch (alt8) {
                        case 1 :
                            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1816:2: kw= '_off'
                            {
                            kw=(Token)match(input,72,FOLLOW_72_in_ruledebug_operand4339); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_offKeyword_0_1_0()); 
                                

                            }
                            break;
                        case 2 :
                            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1823:2: kw= '_on'
                            {
                            kw=(Token)match(input,73,FOLLOW_73_in_ruledebug_operand4358); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_onKeyword_0_1_1()); 
                                

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1829:6: (kw= '_dump' (kw= '_off' | kw= '_on' ) )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1829:6: (kw= '_dump' (kw= '_off' | kw= '_on' ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1830:2: kw= '_dump' (kw= '_off' | kw= '_on' )
                    {
                    kw=(Token)match(input,74,FOLLOW_74_in_ruledebug_operand4380); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_dumpKeyword_1_0()); 
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1835:1: (kw= '_off' | kw= '_on' )
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==72) ) {
                        alt9=1;
                    }
                    else if ( (LA9_0==73) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 0, input);

                        throw nvae;
                    }
                    switch (alt9) {
                        case 1 :
                            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1836:2: kw= '_off'
                            {
                            kw=(Token)match(input,72,FOLLOW_72_in_ruledebug_operand4394); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_offKeyword_1_1_0()); 
                                

                            }
                            break;
                        case 2 :
                            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1843:2: kw= '_on'
                            {
                            kw=(Token)match(input,73,FOLLOW_73_in_ruledebug_operand4413); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_onKeyword_1_1_1()); 
                                

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1849:6: (kw= '_sor' (kw= '_off' | kw= '_on' ) )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1849:6: (kw= '_sor' (kw= '_off' | kw= '_on' ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1850:2: kw= '_sor' (kw= '_off' | kw= '_on' )
                    {
                    kw=(Token)match(input,75,FOLLOW_75_in_ruledebug_operand4435); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_sorKeyword_2_0()); 
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1855:1: (kw= '_off' | kw= '_on' )
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==72) ) {
                        alt10=1;
                    }
                    else if ( (LA10_0==73) ) {
                        alt10=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 0, input);

                        throw nvae;
                    }
                    switch (alt10) {
                        case 1 :
                            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1856:2: kw= '_off'
                            {
                            kw=(Token)match(input,72,FOLLOW_72_in_ruledebug_operand4449); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_offKeyword_2_1_0()); 
                                

                            }
                            break;
                        case 2 :
                            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1863:2: kw= '_on'
                            {
                            kw=(Token)match(input,73,FOLLOW_73_in_ruledebug_operand4468); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_onKeyword_2_1_1()); 
                                

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 4 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1870:2: kw= '_on'
                    {
                    kw=(Token)match(input,73,FOLLOW_73_in_ruledebug_operand4489); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_onKeyword_3()); 
                        

                    }
                    break;
                case 5 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1877:2: kw= '_off'
                    {
                    kw=(Token)match(input,72,FOLLOW_72_in_ruledebug_operand4508); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_offKeyword_4()); 
                        

                    }
                    break;
                case 6 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1884:2: kw= '_stat'
                    {
                    kw=(Token)match(input,76,FOLLOW_76_in_ruledebug_operand4527); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getDebug_operandAccess().get_statKeyword_5()); 
                        

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
    // $ANTLR end "ruledebug_operand"


    // $ANTLR start "entryRuleevent_spec"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1897:1: entryRuleevent_spec returns [String current=null] : iv_ruleevent_spec= ruleevent_spec EOF ;
    public final String entryRuleevent_spec() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleevent_spec = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1898:2: (iv_ruleevent_spec= ruleevent_spec EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1899:2: iv_ruleevent_spec= ruleevent_spec EOF
            {
             newCompositeNode(grammarAccess.getEvent_specRule()); 
            pushFollow(FOLLOW_ruleevent_spec_in_entryRuleevent_spec4568);
            iv_ruleevent_spec=ruleevent_spec();

            state._fsp--;

             current =iv_ruleevent_spec.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleevent_spec4579); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleevent_spec"


    // $ANTLR start "ruleevent_spec"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1906:1: ruleevent_spec returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_event_label_0= ruleevent_label kw= 'to' this_inst_ref_var_or_ee_keyletters_2= ruleinst_ref_var_or_ee_keyletters ) ;
    public final AntlrDatatypeRuleToken ruleevent_spec() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_event_label_0 = null;

        AntlrDatatypeRuleToken this_inst_ref_var_or_ee_keyletters_2 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1909:28: ( (this_event_label_0= ruleevent_label kw= 'to' this_inst_ref_var_or_ee_keyletters_2= ruleinst_ref_var_or_ee_keyletters ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1910:1: (this_event_label_0= ruleevent_label kw= 'to' this_inst_ref_var_or_ee_keyletters_2= ruleinst_ref_var_or_ee_keyletters )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1910:1: (this_event_label_0= ruleevent_label kw= 'to' this_inst_ref_var_or_ee_keyletters_2= ruleinst_ref_var_or_ee_keyletters )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1911:5: this_event_label_0= ruleevent_label kw= 'to' this_inst_ref_var_or_ee_keyletters_2= ruleinst_ref_var_or_ee_keyletters
            {
             
                    newCompositeNode(grammarAccess.getEvent_specAccess().getEvent_labelParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleevent_label_in_ruleevent_spec4626);
            this_event_label_0=ruleevent_label();

            state._fsp--;


            		current.merge(this_event_label_0);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,58,FOLLOW_58_in_ruleevent_spec4644); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getEvent_specAccess().getToKeyword_1()); 
                
             
                    newCompositeNode(grammarAccess.getEvent_specAccess().getInst_ref_var_or_ee_keylettersParserRuleCall_2()); 
                
            pushFollow(FOLLOW_ruleinst_ref_var_or_ee_keyletters_in_ruleevent_spec4666);
            this_inst_ref_var_or_ee_keyletters_2=ruleinst_ref_var_or_ee_keyletters();

            state._fsp--;


            		current.merge(this_inst_ref_var_or_ee_keyletters_2);
                
             
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
    // $ANTLR end "ruleevent_spec"


    // $ANTLR start "entryRuleinst_ref_var_or_ee_keyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1948:1: entryRuleinst_ref_var_or_ee_keyletters returns [String current=null] : iv_ruleinst_ref_var_or_ee_keyletters= ruleinst_ref_var_or_ee_keyletters EOF ;
    public final String entryRuleinst_ref_var_or_ee_keyletters() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleinst_ref_var_or_ee_keyletters = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1949:2: (iv_ruleinst_ref_var_or_ee_keyletters= ruleinst_ref_var_or_ee_keyletters EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1950:2: iv_ruleinst_ref_var_or_ee_keyletters= ruleinst_ref_var_or_ee_keyletters EOF
            {
             newCompositeNode(grammarAccess.getInst_ref_var_or_ee_keylettersRule()); 
            pushFollow(FOLLOW_ruleinst_ref_var_or_ee_keyletters_in_entryRuleinst_ref_var_or_ee_keyletters4714);
            iv_ruleinst_ref_var_or_ee_keyletters=ruleinst_ref_var_or_ee_keyletters();

            state._fsp--;

             current =iv_ruleinst_ref_var_or_ee_keyletters.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleinst_ref_var_or_ee_keyletters4725); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleinst_ref_var_or_ee_keyletters"


    // $ANTLR start "ruleinst_ref_var_or_ee_keyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1957:1: ruleinst_ref_var_or_ee_keyletters returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_local_variable_0= rulelocal_variable ;
    public final AntlrDatatypeRuleToken ruleinst_ref_var_or_ee_keyletters() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_local_variable_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1960:28: (this_local_variable_0= rulelocal_variable )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1962:5: this_local_variable_0= rulelocal_variable
            {
             
                    newCompositeNode(grammarAccess.getInst_ref_var_or_ee_keylettersAccess().getLocal_variableParserRuleCall()); 
                
            pushFollow(FOLLOW_rulelocal_variable_in_ruleinst_ref_var_or_ee_keyletters4771);
            this_local_variable_0=rulelocal_variable();

            state._fsp--;


            		current.merge(this_local_variable_0);
                
             
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
    // $ANTLR end "ruleinst_ref_var_or_ee_keyletters"


    // $ANTLR start "entryRuleinstance_chain"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1984:1: entryRuleinstance_chain returns [String current=null] : iv_ruleinstance_chain= ruleinstance_chain EOF ;
    public final String entryRuleinstance_chain() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleinstance_chain = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1985:2: (iv_ruleinstance_chain= ruleinstance_chain EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1986:2: iv_ruleinstance_chain= ruleinstance_chain EOF
            {
             newCompositeNode(grammarAccess.getInstance_chainRule()); 
            pushFollow(FOLLOW_ruleinstance_chain_in_entryRuleinstance_chain4820);
            iv_ruleinstance_chain=ruleinstance_chain();

            state._fsp--;

             current =iv_ruleinstance_chain.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleinstance_chain4831); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleinstance_chain"


    // $ANTLR start "ruleinstance_chain"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1993:1: ruleinstance_chain returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'instance_chain' ;
    public final AntlrDatatypeRuleToken ruleinstance_chain() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1996:28: (kw= 'instance_chain' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:1998:2: kw= 'instance_chain'
            {
            kw=(Token)match(input,77,FOLLOW_77_in_ruleinstance_chain4868); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getInstance_chainAccess().getInstance_chainKeyword()); 
                

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
    // $ANTLR end "ruleinstance_chain"


    // $ANTLR start "entryRuleobject_spec"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2011:1: entryRuleobject_spec returns [String current=null] : iv_ruleobject_spec= ruleobject_spec EOF ;
    public final String entryRuleobject_spec() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleobject_spec = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2012:2: (iv_ruleobject_spec= ruleobject_spec EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2013:2: iv_ruleobject_spec= ruleobject_spec EOF
            {
             newCompositeNode(grammarAccess.getObject_specRule()); 
            pushFollow(FOLLOW_ruleobject_spec_in_entryRuleobject_spec4908);
            iv_ruleobject_spec=ruleobject_spec();

            state._fsp--;

             current =iv_ruleobject_spec.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleobject_spec4919); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleobject_spec"


    // $ANTLR start "ruleobject_spec"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2020:1: ruleobject_spec returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= 'related' kw= 'by' this_local_variable_2= rulelocal_variable this_instance_chain_3= ruleinstance_chain ) | (kw= 'from' (kw= 'instances' kw= 'of' )? this_object_keyletters_7= ruleobject_keyletters ) ) ;
    public final AntlrDatatypeRuleToken ruleobject_spec() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_local_variable_2 = null;

        AntlrDatatypeRuleToken this_instance_chain_3 = null;

        AntlrDatatypeRuleToken this_object_keyletters_7 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2023:28: ( ( (kw= 'related' kw= 'by' this_local_variable_2= rulelocal_variable this_instance_chain_3= ruleinstance_chain ) | (kw= 'from' (kw= 'instances' kw= 'of' )? this_object_keyletters_7= ruleobject_keyletters ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2024:1: ( (kw= 'related' kw= 'by' this_local_variable_2= rulelocal_variable this_instance_chain_3= ruleinstance_chain ) | (kw= 'from' (kw= 'instances' kw= 'of' )? this_object_keyletters_7= ruleobject_keyletters ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2024:1: ( (kw= 'related' kw= 'by' this_local_variable_2= rulelocal_variable this_instance_chain_3= ruleinstance_chain ) | (kw= 'from' (kw= 'instances' kw= 'of' )? this_object_keyletters_7= ruleobject_keyletters ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==78) ) {
                alt13=1;
            }
            else if ( (LA13_0==68) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2024:2: (kw= 'related' kw= 'by' this_local_variable_2= rulelocal_variable this_instance_chain_3= ruleinstance_chain )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2024:2: (kw= 'related' kw= 'by' this_local_variable_2= rulelocal_variable this_instance_chain_3= ruleinstance_chain )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2025:2: kw= 'related' kw= 'by' this_local_variable_2= rulelocal_variable this_instance_chain_3= ruleinstance_chain
                    {
                    kw=(Token)match(input,78,FOLLOW_78_in_ruleobject_spec4958); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getObject_specAccess().getRelatedKeyword_0_0()); 
                        
                    kw=(Token)match(input,79,FOLLOW_79_in_ruleobject_spec4971); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getObject_specAccess().getByKeyword_0_1()); 
                        
                     
                            newCompositeNode(grammarAccess.getObject_specAccess().getLocal_variableParserRuleCall_0_2()); 
                        
                    pushFollow(FOLLOW_rulelocal_variable_in_ruleobject_spec4993);
                    this_local_variable_2=rulelocal_variable();

                    state._fsp--;


                    		current.merge(this_local_variable_2);
                        
                     
                            afterParserOrEnumRuleCall();
                        
                     
                            newCompositeNode(grammarAccess.getObject_specAccess().getInstance_chainParserRuleCall_0_3()); 
                        
                    pushFollow(FOLLOW_ruleinstance_chain_in_ruleobject_spec5020);
                    this_instance_chain_3=ruleinstance_chain();

                    state._fsp--;


                    		current.merge(this_instance_chain_3);
                        
                     
                            afterParserOrEnumRuleCall();
                        

                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2059:6: (kw= 'from' (kw= 'instances' kw= 'of' )? this_object_keyletters_7= ruleobject_keyletters )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2059:6: (kw= 'from' (kw= 'instances' kw= 'of' )? this_object_keyletters_7= ruleobject_keyletters )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2060:2: kw= 'from' (kw= 'instances' kw= 'of' )? this_object_keyletters_7= ruleobject_keyletters
                    {
                    kw=(Token)match(input,68,FOLLOW_68_in_ruleobject_spec5046); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getObject_specAccess().getFromKeyword_1_0()); 
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2065:1: (kw= 'instances' kw= 'of' )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==80) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2066:2: kw= 'instances' kw= 'of'
                            {
                            kw=(Token)match(input,80,FOLLOW_80_in_ruleobject_spec5060); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getObject_specAccess().getInstancesKeyword_1_1_0()); 
                                
                            kw=(Token)match(input,42,FOLLOW_42_in_ruleobject_spec5073); 

                                    current.merge(kw);
                                    newLeafNode(kw, grammarAccess.getObject_specAccess().getOfKeyword_1_1_1()); 
                                

                            }
                            break;

                    }

                     
                            newCompositeNode(grammarAccess.getObject_specAccess().getObject_keylettersParserRuleCall_1_2()); 
                        
                    pushFollow(FOLLOW_ruleobject_keyletters_in_ruleobject_spec5097);
                    this_object_keyletters_7=ruleobject_keyletters();

                    state._fsp--;


                    		current.merge(this_object_keyletters_7);
                        
                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleobject_spec"


    // $ANTLR start "entryRuledata_item"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2112:1: entryRuledata_item returns [String current=null] : iv_ruledata_item= ruledata_item EOF ;
    public final String entryRuledata_item() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruledata_item = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2113:2: (iv_ruledata_item= ruledata_item EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2114:2: iv_ruledata_item= ruledata_item EOF
            {
             newCompositeNode(grammarAccess.getData_itemRule()); 
            pushFollow(FOLLOW_ruledata_item_in_entryRuledata_item5160);
            iv_ruledata_item=ruledata_item();

            state._fsp--;

             current =iv_ruledata_item.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuledata_item5171); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledata_item"


    // $ANTLR start "ruledata_item"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2121:1: ruledata_item returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_data_item_name_0= ruledata_item_name ;
    public final AntlrDatatypeRuleToken ruledata_item() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_data_item_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2124:28: (this_data_item_name_0= ruledata_item_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2126:5: this_data_item_name_0= ruledata_item_name
            {
             
                    newCompositeNode(grammarAccess.getData_itemAccess().getData_item_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_ruledata_item_name_in_ruledata_item5217);
            this_data_item_name_0=ruledata_item_name();

            state._fsp--;


            		current.merge(this_data_item_name_0);
                
             
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
    // $ANTLR end "ruledata_item"


    // $ANTLR start "entryRuledata_item_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2144:1: entryRuledata_item_name returns [String current=null] : iv_ruledata_item_name= ruledata_item_name EOF ;
    public final String entryRuledata_item_name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruledata_item_name = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2145:2: (iv_ruledata_item_name= ruledata_item_name EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2146:2: iv_ruledata_item_name= ruledata_item_name EOF
            {
             newCompositeNode(grammarAccess.getData_item_nameRule()); 
            pushFollow(FOLLOW_ruledata_item_name_in_entryRuledata_item_name5262);
            iv_ruledata_item_name=ruledata_item_name();

            state._fsp--;

             current =iv_ruledata_item_name.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuledata_item_name5273); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuledata_item_name"


    // $ANTLR start "ruledata_item_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2153:1: ruledata_item_name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_general_name_0= rulegeneral_name ;
    public final AntlrDatatypeRuleToken ruledata_item_name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_general_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2156:28: (this_general_name_0= rulegeneral_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2158:5: this_general_name_0= rulegeneral_name
            {
             
                    newCompositeNode(grammarAccess.getData_item_nameAccess().getGeneral_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulegeneral_name_in_ruledata_item_name5319);
            this_general_name_0=rulegeneral_name();

            state._fsp--;


            		current.merge(this_general_name_0);
                
             
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
    // $ANTLR end "ruledata_item_name"


    // $ANTLR start "entryRulekeyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2180:1: entryRulekeyletters returns [String current=null] : iv_rulekeyletters= rulekeyletters EOF ;
    public final String entryRulekeyletters() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulekeyletters = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2181:2: (iv_rulekeyletters= rulekeyletters EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2182:2: iv_rulekeyletters= rulekeyletters EOF
            {
             newCompositeNode(grammarAccess.getKeylettersRule()); 
            pushFollow(FOLLOW_rulekeyletters_in_entryRulekeyletters5368);
            iv_rulekeyletters=rulekeyletters();

            state._fsp--;

             current =iv_rulekeyletters.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulekeyletters5379); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulekeyletters"


    // $ANTLR start "rulekeyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2189:1: rulekeyletters returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_general_name_0= rulegeneral_name ;
    public final AntlrDatatypeRuleToken rulekeyletters() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_general_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2192:28: (this_general_name_0= rulegeneral_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2194:5: this_general_name_0= rulegeneral_name
            {
             
                    newCompositeNode(grammarAccess.getKeylettersAccess().getGeneral_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulegeneral_name_in_rulekeyletters5425);
            this_general_name_0=rulegeneral_name();

            state._fsp--;


            		current.merge(this_general_name_0);
                
             
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
    // $ANTLR end "rulekeyletters"


    // $ANTLR start "entryRuleee_keyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2212:1: entryRuleee_keyletters returns [String current=null] : iv_ruleee_keyletters= ruleee_keyletters EOF ;
    public final String entryRuleee_keyletters() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleee_keyletters = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2213:2: (iv_ruleee_keyletters= ruleee_keyletters EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2214:2: iv_ruleee_keyletters= ruleee_keyletters EOF
            {
             newCompositeNode(grammarAccess.getEe_keylettersRule()); 
            pushFollow(FOLLOW_ruleee_keyletters_in_entryRuleee_keyletters5470);
            iv_ruleee_keyletters=ruleee_keyletters();

            state._fsp--;

             current =iv_ruleee_keyletters.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleee_keyletters5481); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleee_keyletters"


    // $ANTLR start "ruleee_keyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2221:1: ruleee_keyletters returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_keyletters_0= rulekeyletters ;
    public final AntlrDatatypeRuleToken ruleee_keyletters() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_keyletters_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2224:28: (this_keyletters_0= rulekeyletters )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2226:5: this_keyletters_0= rulekeyletters
            {
             
                    newCompositeNode(grammarAccess.getEe_keylettersAccess().getKeylettersParserRuleCall()); 
                
            pushFollow(FOLLOW_rulekeyletters_in_ruleee_keyletters5527);
            this_keyletters_0=rulekeyletters();

            state._fsp--;


            		current.merge(this_keyletters_0);
                
             
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
    // $ANTLR end "ruleee_keyletters"


    // $ANTLR start "entryRuleevent_label"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2244:1: entryRuleevent_label returns [String current=null] : iv_ruleevent_label= ruleevent_label EOF ;
    public final String entryRuleevent_label() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleevent_label = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2245:2: (iv_ruleevent_label= ruleevent_label EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2246:2: iv_ruleevent_label= ruleevent_label EOF
            {
             newCompositeNode(grammarAccess.getEvent_labelRule()); 
            pushFollow(FOLLOW_ruleevent_label_in_entryRuleevent_label5572);
            iv_ruleevent_label=ruleevent_label();

            state._fsp--;

             current =iv_ruleevent_label.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleevent_label5583); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleevent_label"


    // $ANTLR start "ruleevent_label"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2253:1: ruleevent_label returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_general_name_0= rulegeneral_name ;
    public final AntlrDatatypeRuleToken ruleevent_label() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_general_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2256:28: (this_general_name_0= rulegeneral_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2258:5: this_general_name_0= rulegeneral_name
            {
             
                    newCompositeNode(grammarAccess.getEvent_labelAccess().getGeneral_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulegeneral_name_in_ruleevent_label5629);
            this_general_name_0=rulegeneral_name();

            state._fsp--;


            		current.merge(this_general_name_0);
                
             
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
    // $ANTLR end "ruleevent_label"


    // $ANTLR start "entryRulegeneral_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2278:1: entryRulegeneral_name returns [String current=null] : iv_rulegeneral_name= rulegeneral_name EOF ;
    public final String entryRulegeneral_name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulegeneral_name = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2279:2: (iv_rulegeneral_name= rulegeneral_name EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2280:2: iv_rulegeneral_name= rulegeneral_name EOF
            {
             newCompositeNode(grammarAccess.getGeneral_nameRule()); 
            pushFollow(FOLLOW_rulegeneral_name_in_entryRulegeneral_name5676);
            iv_rulegeneral_name=rulegeneral_name();

            state._fsp--;

             current =iv_rulegeneral_name.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulegeneral_name5687); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulegeneral_name"


    // $ANTLR start "rulegeneral_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2287:1: rulegeneral_name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_limited_name_0= rulelimited_name ;
    public final AntlrDatatypeRuleToken rulegeneral_name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_limited_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2290:28: (this_limited_name_0= rulelimited_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2292:5: this_limited_name_0= rulelimited_name
            {
             
                    newCompositeNode(grammarAccess.getGeneral_nameAccess().getLimited_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulelimited_name_in_rulegeneral_name5733);
            this_limited_name_0=rulelimited_name();

            state._fsp--;


            		current.merge(this_limited_name_0);
                
             
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
    // $ANTLR end "rulegeneral_name"


    // $ANTLR start "entryRulesvc_general_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2310:1: entryRulesvc_general_name returns [String current=null] : iv_rulesvc_general_name= rulesvc_general_name EOF ;
    public final String entryRulesvc_general_name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulesvc_general_name = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2311:2: (iv_rulesvc_general_name= rulesvc_general_name EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2312:2: iv_rulesvc_general_name= rulesvc_general_name EOF
            {
             newCompositeNode(grammarAccess.getSvc_general_nameRule()); 
            pushFollow(FOLLOW_rulesvc_general_name_in_entryRulesvc_general_name5778);
            iv_rulesvc_general_name=rulesvc_general_name();

            state._fsp--;

             current =iv_rulesvc_general_name.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulesvc_general_name5789); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesvc_general_name"


    // $ANTLR start "rulesvc_general_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2319:1: rulesvc_general_name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_limited_name_0= rulelimited_name ;
    public final AntlrDatatypeRuleToken rulesvc_general_name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_limited_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2322:28: (this_limited_name_0= rulelimited_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2324:5: this_limited_name_0= rulelimited_name
            {
             
                    newCompositeNode(grammarAccess.getSvc_general_nameAccess().getLimited_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulelimited_name_in_rulesvc_general_name5835);
            this_limited_name_0=rulelimited_name();

            state._fsp--;


            		current.merge(this_limited_name_0);
                
             
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
    // $ANTLR end "rulesvc_general_name"


    // $ANTLR start "entryRulelimited_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2342:1: entryRulelimited_name returns [String current=null] : iv_rulelimited_name= rulelimited_name EOF ;
    public final String entryRulelimited_name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulelimited_name = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2343:2: (iv_rulelimited_name= rulelimited_name EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2344:2: iv_rulelimited_name= rulelimited_name EOF
            {
             newCompositeNode(grammarAccess.getLimited_nameRule()); 
            pushFollow(FOLLOW_rulelimited_name_in_entryRulelimited_name5880);
            iv_rulelimited_name=rulelimited_name();

            state._fsp--;

             current =iv_rulelimited_name.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulelimited_name5891); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelimited_name"


    // $ANTLR start "rulelimited_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2351:1: rulelimited_name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken rulelimited_name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2354:28: (this_ID_0= RULE_ID )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2355:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulelimited_name5930); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getLimited_nameAccess().getIDTerminalRuleCall()); 
                

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
    // $ANTLR end "rulelimited_name"


    // $ANTLR start "entryRuleinst_ref_set_var"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2370:1: entryRuleinst_ref_set_var returns [EObject current=null] : iv_ruleinst_ref_set_var= ruleinst_ref_set_var EOF ;
    public final EObject entryRuleinst_ref_set_var() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleinst_ref_set_var = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2371:2: (iv_ruleinst_ref_set_var= ruleinst_ref_set_var EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2372:2: iv_ruleinst_ref_set_var= ruleinst_ref_set_var EOF
            {
             newCompositeNode(grammarAccess.getInst_ref_set_varRule()); 
            pushFollow(FOLLOW_ruleinst_ref_set_var_in_entryRuleinst_ref_set_var5974);
            iv_ruleinst_ref_set_var=ruleinst_ref_set_var();

            state._fsp--;

             current =iv_ruleinst_ref_set_var; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleinst_ref_set_var5984); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleinst_ref_set_var"


    // $ANTLR start "ruleinst_ref_set_var"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2379:1: ruleinst_ref_set_var returns [EObject current=null] : ( (lv_a1_0_0= rulelocal_variable ) ) ;
    public final EObject ruleinst_ref_set_var() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_a1_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2382:28: ( ( (lv_a1_0_0= rulelocal_variable ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2383:1: ( (lv_a1_0_0= rulelocal_variable ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2383:1: ( (lv_a1_0_0= rulelocal_variable ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2384:1: (lv_a1_0_0= rulelocal_variable )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2384:1: (lv_a1_0_0= rulelocal_variable )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2385:3: lv_a1_0_0= rulelocal_variable
            {
             
            	        newCompositeNode(grammarAccess.getInst_ref_set_varAccess().getA1Local_variableParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_rulelocal_variable_in_ruleinst_ref_set_var6029);
            lv_a1_0_0=rulelocal_variable();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getInst_ref_set_varRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"local_variable");
            	        afterParserOrEnumRuleCall();
            	    

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
    // $ANTLR end "ruleinst_ref_set_var"


    // $ANTLR start "entryRuleinst_ref_var"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2409:1: entryRuleinst_ref_var returns [EObject current=null] : iv_ruleinst_ref_var= ruleinst_ref_var EOF ;
    public final EObject entryRuleinst_ref_var() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleinst_ref_var = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2410:2: (iv_ruleinst_ref_var= ruleinst_ref_var EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2411:2: iv_ruleinst_ref_var= ruleinst_ref_var EOF
            {
             newCompositeNode(grammarAccess.getInst_ref_varRule()); 
            pushFollow(FOLLOW_ruleinst_ref_var_in_entryRuleinst_ref_var6064);
            iv_ruleinst_ref_var=ruleinst_ref_var();

            state._fsp--;

             current =iv_ruleinst_ref_var; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleinst_ref_var6074); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleinst_ref_var"


    // $ANTLR start "ruleinst_ref_var"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2418:1: ruleinst_ref_var returns [EObject current=null] : ( (lv_a1_0_0= rulelocal_variable ) ) ;
    public final EObject ruleinst_ref_var() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_a1_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2421:28: ( ( (lv_a1_0_0= rulelocal_variable ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2422:1: ( (lv_a1_0_0= rulelocal_variable ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2422:1: ( (lv_a1_0_0= rulelocal_variable ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2423:1: (lv_a1_0_0= rulelocal_variable )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2423:1: (lv_a1_0_0= rulelocal_variable )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2424:3: lv_a1_0_0= rulelocal_variable
            {
             
            	        newCompositeNode(grammarAccess.getInst_ref_varAccess().getA1Local_variableParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_rulelocal_variable_in_ruleinst_ref_var6119);
            lv_a1_0_0=rulelocal_variable();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getInst_ref_varRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"local_variable");
            	        afterParserOrEnumRuleCall();
            	    

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
    // $ANTLR end "ruleinst_ref_var"


    // $ANTLR start "entryRulelocal_variable"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2448:1: entryRulelocal_variable returns [String current=null] : iv_rulelocal_variable= rulelocal_variable EOF ;
    public final String entryRulelocal_variable() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulelocal_variable = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2449:2: (iv_rulelocal_variable= rulelocal_variable EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2450:2: iv_rulelocal_variable= rulelocal_variable EOF
            {
             newCompositeNode(grammarAccess.getLocal_variableRule()); 
            pushFollow(FOLLOW_rulelocal_variable_in_entryRulelocal_variable6155);
            iv_rulelocal_variable=rulelocal_variable();

            state._fsp--;

             current =iv_rulelocal_variable.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulelocal_variable6166); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulelocal_variable"


    // $ANTLR start "rulelocal_variable"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2457:1: rulelocal_variable returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_root_element_label_0= ruleroot_element_label ;
    public final AntlrDatatypeRuleToken rulelocal_variable() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_root_element_label_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2460:28: (this_root_element_label_0= ruleroot_element_label )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2462:5: this_root_element_label_0= ruleroot_element_label
            {
             
                    newCompositeNode(grammarAccess.getLocal_variableAccess().getRoot_element_labelParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleroot_element_label_in_rulelocal_variable6212);
            this_root_element_label_0=ruleroot_element_label();

            state._fsp--;


            		current.merge(this_root_element_label_0);
                
             
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
    // $ANTLR end "rulelocal_variable"


    // $ANTLR start "entryRuleroot_element_label"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2480:1: entryRuleroot_element_label returns [String current=null] : iv_ruleroot_element_label= ruleroot_element_label EOF ;
    public final String entryRuleroot_element_label() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleroot_element_label = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2481:2: (iv_ruleroot_element_label= ruleroot_element_label EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2482:2: iv_ruleroot_element_label= ruleroot_element_label EOF
            {
             newCompositeNode(grammarAccess.getRoot_element_labelRule()); 
            pushFollow(FOLLOW_ruleroot_element_label_in_entryRuleroot_element_label6257);
            iv_ruleroot_element_label=ruleroot_element_label();

            state._fsp--;

             current =iv_ruleroot_element_label.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleroot_element_label6268); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleroot_element_label"


    // $ANTLR start "ruleroot_element_label"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2489:1: ruleroot_element_label returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'selected' | kw= 'self' | this_limited_name_2= rulelimited_name ) ;
    public final AntlrDatatypeRuleToken ruleroot_element_label() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_limited_name_2 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2492:28: ( (kw= 'selected' | kw= 'self' | this_limited_name_2= rulelimited_name ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2493:1: (kw= 'selected' | kw= 'self' | this_limited_name_2= rulelimited_name )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2493:1: (kw= 'selected' | kw= 'self' | this_limited_name_2= rulelimited_name )
            int alt14=3;
            switch ( input.LA(1) ) {
            case 81:
                {
                alt14=1;
                }
                break;
            case 82:
                {
                alt14=2;
                }
                break;
            case RULE_ID:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2494:2: kw= 'selected'
                    {
                    kw=(Token)match(input,81,FOLLOW_81_in_ruleroot_element_label6306); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getRoot_element_labelAccess().getSelectedKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2501:2: kw= 'self'
                    {
                    kw=(Token)match(input,82,FOLLOW_82_in_ruleroot_element_label6325); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getRoot_element_labelAccess().getSelfKeyword_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2508:5: this_limited_name_2= rulelimited_name
                    {
                     
                            newCompositeNode(grammarAccess.getRoot_element_labelAccess().getLimited_nameParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_rulelimited_name_in_ruleroot_element_label6353);
                    this_limited_name_2=rulelimited_name();

                    state._fsp--;


                    		current.merge(this_limited_name_2);
                        
                     
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
    // $ANTLR end "ruleroot_element_label"


    // $ANTLR start "entryRuleelement_label"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2526:1: entryRuleelement_label returns [String current=null] : iv_ruleelement_label= ruleelement_label EOF ;
    public final String entryRuleelement_label() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleelement_label = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2527:2: (iv_ruleelement_label= ruleelement_label EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2528:2: iv_ruleelement_label= ruleelement_label EOF
            {
             newCompositeNode(grammarAccess.getElement_labelRule()); 
            pushFollow(FOLLOW_ruleelement_label_in_entryRuleelement_label6399);
            iv_ruleelement_label=ruleelement_label();

            state._fsp--;

             current =iv_ruleelement_label.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleelement_label6410); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleelement_label"


    // $ANTLR start "ruleelement_label"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2535:1: ruleelement_label returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_general_name_0= rulegeneral_name ;
    public final AntlrDatatypeRuleToken ruleelement_label() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_general_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2538:28: (this_general_name_0= rulegeneral_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2540:5: this_general_name_0= rulegeneral_name
            {
             
                    newCompositeNode(grammarAccess.getElement_labelAccess().getGeneral_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulegeneral_name_in_ruleelement_label6456);
            this_general_name_0=rulegeneral_name();

            state._fsp--;


            		current.merge(this_general_name_0);
                
             
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
    // $ANTLR end "ruleelement_label"


    // $ANTLR start "entryRulefunction_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2558:1: entryRulefunction_name returns [String current=null] : iv_rulefunction_name= rulefunction_name EOF ;
    public final String entryRulefunction_name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulefunction_name = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2559:2: (iv_rulefunction_name= rulefunction_name EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2560:2: iv_rulefunction_name= rulefunction_name EOF
            {
             newCompositeNode(grammarAccess.getFunction_nameRule()); 
            pushFollow(FOLLOW_rulefunction_name_in_entryRulefunction_name6501);
            iv_rulefunction_name=rulefunction_name();

            state._fsp--;

             current =iv_rulefunction_name.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulefunction_name6512); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulefunction_name"


    // $ANTLR start "rulefunction_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2567:1: rulefunction_name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_general_name_0= rulegeneral_name ;
    public final AntlrDatatypeRuleToken rulefunction_name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_general_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2570:28: (this_general_name_0= rulegeneral_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2572:5: this_general_name_0= rulegeneral_name
            {
             
                    newCompositeNode(grammarAccess.getFunction_nameAccess().getGeneral_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulegeneral_name_in_rulefunction_name6558);
            this_general_name_0=rulegeneral_name();

            state._fsp--;


            		current.merge(this_general_name_0);
                
             
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
    // $ANTLR end "rulefunction_name"


    // $ANTLR start "entryRulesvc_function_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2590:1: entryRulesvc_function_name returns [String current=null] : iv_rulesvc_function_name= rulesvc_function_name EOF ;
    public final String entryRulesvc_function_name() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulesvc_function_name = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2591:2: (iv_rulesvc_function_name= rulesvc_function_name EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2592:2: iv_rulesvc_function_name= rulesvc_function_name EOF
            {
             newCompositeNode(grammarAccess.getSvc_function_nameRule()); 
            pushFollow(FOLLOW_rulesvc_function_name_in_entryRulesvc_function_name6603);
            iv_rulesvc_function_name=rulesvc_function_name();

            state._fsp--;

             current =iv_rulesvc_function_name.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulesvc_function_name6614); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesvc_function_name"


    // $ANTLR start "rulesvc_function_name"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2599:1: rulesvc_function_name returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_svc_general_name_0= rulesvc_general_name ;
    public final AntlrDatatypeRuleToken rulesvc_function_name() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_svc_general_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2602:28: (this_svc_general_name_0= rulesvc_general_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2604:5: this_svc_general_name_0= rulesvc_general_name
            {
             
                    newCompositeNode(grammarAccess.getSvc_function_nameAccess().getSvc_general_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_rulesvc_general_name_in_rulesvc_function_name6660);
            this_svc_general_name_0=rulesvc_general_name();

            state._fsp--;


            		current.merge(this_svc_general_name_0);
                
             
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
    // $ANTLR end "rulesvc_function_name"


    // $ANTLR start "entryRuleobject_keyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2624:1: entryRuleobject_keyletters returns [String current=null] : iv_ruleobject_keyletters= ruleobject_keyletters EOF ;
    public final String entryRuleobject_keyletters() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleobject_keyletters = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2625:2: (iv_ruleobject_keyletters= ruleobject_keyletters EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2626:2: iv_ruleobject_keyletters= ruleobject_keyletters EOF
            {
             newCompositeNode(grammarAccess.getObject_keylettersRule()); 
            pushFollow(FOLLOW_ruleobject_keyletters_in_entryRuleobject_keyletters6707);
            iv_ruleobject_keyletters=ruleobject_keyletters();

            state._fsp--;

             current =iv_ruleobject_keyletters.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleobject_keyletters6718); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleobject_keyletters"


    // $ANTLR start "ruleobject_keyletters"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2633:1: ruleobject_keyletters returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_keyletters_0= rulekeyletters ;
    public final AntlrDatatypeRuleToken ruleobject_keyletters() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_keyletters_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2636:28: (this_keyletters_0= rulekeyletters )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2638:5: this_keyletters_0= rulekeyletters
            {
             
                    newCompositeNode(grammarAccess.getObject_keylettersAccess().getKeylettersParserRuleCall()); 
                
            pushFollow(FOLLOW_rulekeyletters_in_ruleobject_keyletters6764);
            this_keyletters_0=rulekeyletters();

            state._fsp--;


            		current.merge(this_keyletters_0);
                
             
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
    // $ANTLR end "ruleobject_keyletters"


    // $ANTLR start "entryRulephrase"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2656:1: entryRulephrase returns [String current=null] : iv_rulephrase= rulephrase EOF ;
    public final String entryRulephrase() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulephrase = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2657:2: (iv_rulephrase= rulephrase EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2658:2: iv_rulephrase= rulephrase EOF
            {
             newCompositeNode(grammarAccess.getPhraseRule()); 
            pushFollow(FOLLOW_rulephrase_in_entryRulephrase6809);
            iv_rulephrase=rulephrase();

            state._fsp--;

             current =iv_rulephrase.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulephrase6820); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulephrase"


    // $ANTLR start "rulephrase"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2665:1: rulephrase returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'phrase' ;
    public final AntlrDatatypeRuleToken rulephrase() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2668:28: (kw= 'phrase' )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2670:2: kw= 'phrase'
            {
            kw=(Token)match(input,83,FOLLOW_83_in_rulephrase6857); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getPhraseAccess().getPhraseKeyword()); 
                

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
    // $ANTLR end "rulephrase"


    // $ANTLR start "entryRulerelationship"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2683:1: entryRulerelationship returns [String current=null] : iv_rulerelationship= rulerelationship EOF ;
    public final String entryRulerelationship() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulerelationship = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2684:2: (iv_rulerelationship= rulerelationship EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2685:2: iv_rulerelationship= rulerelationship EOF
            {
             newCompositeNode(grammarAccess.getRelationshipRule()); 
            pushFollow(FOLLOW_rulerelationship_in_entryRulerelationship6897);
            iv_rulerelationship=rulerelationship();

            state._fsp--;

             current =iv_rulerelationship.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulerelationship6908); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulerelationship"


    // $ANTLR start "rulerelationship"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2692:1: rulerelationship returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken rulerelationship() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2695:28: (this_ID_0= RULE_ID )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2696:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_rulerelationship6947); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getRelationshipAccess().getIDTerminalRuleCall()); 
                

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
    // $ANTLR end "rulerelationship"


    // $ANTLR start "entryRulesupp_data_item"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2711:1: entryRulesupp_data_item returns [String current=null] : iv_rulesupp_data_item= rulesupp_data_item EOF ;
    public final String entryRulesupp_data_item() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulesupp_data_item = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2712:2: (iv_rulesupp_data_item= rulesupp_data_item EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2713:2: iv_rulesupp_data_item= rulesupp_data_item EOF
            {
             newCompositeNode(grammarAccess.getSupp_data_itemRule()); 
            pushFollow(FOLLOW_rulesupp_data_item_in_entryRulesupp_data_item6992);
            iv_rulesupp_data_item=rulesupp_data_item();

            state._fsp--;

             current =iv_rulesupp_data_item.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulesupp_data_item7003); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesupp_data_item"


    // $ANTLR start "rulesupp_data_item"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2720:1: rulesupp_data_item returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_data_item_name_0= ruledata_item_name ;
    public final AntlrDatatypeRuleToken rulesupp_data_item() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_data_item_name_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2723:28: (this_data_item_name_0= ruledata_item_name )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2725:5: this_data_item_name_0= ruledata_item_name
            {
             
                    newCompositeNode(grammarAccess.getSupp_data_itemAccess().getData_item_nameParserRuleCall()); 
                
            pushFollow(FOLLOW_ruledata_item_name_in_rulesupp_data_item7049);
            this_data_item_name_0=ruledata_item_name();

            state._fsp--;


            		current.merge(this_data_item_name_0);
                
             
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
    // $ANTLR end "rulesupp_data_item"


    // $ANTLR start "entryRuleexpr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2747:1: entryRuleexpr returns [EObject current=null] : iv_ruleexpr= ruleexpr EOF ;
    public final EObject entryRuleexpr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleexpr = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2748:2: (iv_ruleexpr= ruleexpr EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2749:2: iv_ruleexpr= ruleexpr EOF
            {
             newCompositeNode(grammarAccess.getExprRule()); 
            pushFollow(FOLLOW_ruleexpr_in_entryRuleexpr7097);
            iv_ruleexpr=ruleexpr();

            state._fsp--;

             current =iv_ruleexpr; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleexpr7107); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleexpr"


    // $ANTLR start "ruleexpr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2756:1: ruleexpr returns [EObject current=null] : ( (lv_a1_0_0= rulesub_expr ) ) ;
    public final EObject ruleexpr() throws RecognitionException {
        EObject current = null;

        EObject lv_a1_0_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2759:28: ( ( (lv_a1_0_0= rulesub_expr ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2760:1: ( (lv_a1_0_0= rulesub_expr ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2760:1: ( (lv_a1_0_0= rulesub_expr ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2761:1: (lv_a1_0_0= rulesub_expr )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2761:1: (lv_a1_0_0= rulesub_expr )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2762:3: lv_a1_0_0= rulesub_expr
            {
             
            	        newCompositeNode(grammarAccess.getExprAccess().getA1Sub_exprParserRuleCall_0()); 
            	    
            pushFollow(FOLLOW_rulesub_expr_in_ruleexpr7152);
            lv_a1_0_0=rulesub_expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getExprRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"sub_expr");
            	        afterParserOrEnumRuleCall();
            	    

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
    // $ANTLR end "ruleexpr"


    // $ANTLR start "entryRulesub_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2786:1: entryRulesub_expr returns [EObject current=null] : iv_rulesub_expr= rulesub_expr EOF ;
    public final EObject entryRulesub_expr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulesub_expr = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2787:2: (iv_rulesub_expr= rulesub_expr EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2788:2: iv_rulesub_expr= rulesub_expr EOF
            {
             newCompositeNode(grammarAccess.getSub_exprRule()); 
            pushFollow(FOLLOW_rulesub_expr_in_entryRulesub_expr7187);
            iv_rulesub_expr=rulesub_expr();

            state._fsp--;

             current =iv_rulesub_expr; 
            match(input,EOF,FOLLOW_EOF_in_entryRulesub_expr7197); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesub_expr"


    // $ANTLR start "rulesub_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2795:1: rulesub_expr returns [EObject current=null] : ( ( (lv_a1_0_0= ruleconjunction ) ) (otherlv_1= 'or' ( (lv_a2_2_0= ruleconjunction ) ) )* ) ;
    public final EObject rulesub_expr() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_a1_0_0 = null;

        EObject lv_a2_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2798:28: ( ( ( (lv_a1_0_0= ruleconjunction ) ) (otherlv_1= 'or' ( (lv_a2_2_0= ruleconjunction ) ) )* ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2799:1: ( ( (lv_a1_0_0= ruleconjunction ) ) (otherlv_1= 'or' ( (lv_a2_2_0= ruleconjunction ) ) )* )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2799:1: ( ( (lv_a1_0_0= ruleconjunction ) ) (otherlv_1= 'or' ( (lv_a2_2_0= ruleconjunction ) ) )* )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2799:2: ( (lv_a1_0_0= ruleconjunction ) ) (otherlv_1= 'or' ( (lv_a2_2_0= ruleconjunction ) ) )*
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2799:2: ( (lv_a1_0_0= ruleconjunction ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2800:1: (lv_a1_0_0= ruleconjunction )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2800:1: (lv_a1_0_0= ruleconjunction )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2801:3: lv_a1_0_0= ruleconjunction
            {
             
            	        newCompositeNode(grammarAccess.getSub_exprAccess().getA1ConjunctionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleconjunction_in_rulesub_expr7243);
            lv_a1_0_0=ruleconjunction();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getSub_exprRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"conjunction");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2817:2: (otherlv_1= 'or' ( (lv_a2_2_0= ruleconjunction ) ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==84) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2817:4: otherlv_1= 'or' ( (lv_a2_2_0= ruleconjunction ) )
            	    {
            	    otherlv_1=(Token)match(input,84,FOLLOW_84_in_rulesub_expr7256); 

            	        	newLeafNode(otherlv_1, grammarAccess.getSub_exprAccess().getOrKeyword_1_0());
            	        
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2821:1: ( (lv_a2_2_0= ruleconjunction ) )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2822:1: (lv_a2_2_0= ruleconjunction )
            	    {
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2822:1: (lv_a2_2_0= ruleconjunction )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2823:3: lv_a2_2_0= ruleconjunction
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getSub_exprAccess().getA2ConjunctionParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleconjunction_in_rulesub_expr7277);
            	    lv_a2_2_0=ruleconjunction();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getSub_exprRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"a2",
            	            		lv_a2_2_0, 
            	            		"conjunction");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
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
    // $ANTLR end "rulesub_expr"


    // $ANTLR start "entryRuleconjunction"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2847:1: entryRuleconjunction returns [EObject current=null] : iv_ruleconjunction= ruleconjunction EOF ;
    public final EObject entryRuleconjunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleconjunction = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2848:2: (iv_ruleconjunction= ruleconjunction EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2849:2: iv_ruleconjunction= ruleconjunction EOF
            {
             newCompositeNode(grammarAccess.getConjunctionRule()); 
            pushFollow(FOLLOW_ruleconjunction_in_entryRuleconjunction7315);
            iv_ruleconjunction=ruleconjunction();

            state._fsp--;

             current =iv_ruleconjunction; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleconjunction7325); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleconjunction"


    // $ANTLR start "ruleconjunction"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2856:1: ruleconjunction returns [EObject current=null] : ( ( (lv_a1_0_0= rulerelational_expr ) ) (otherlv_1= 'and' ( (lv_a2_2_0= rulerelational_expr ) ) )* ) ;
    public final EObject ruleconjunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_a1_0_0 = null;

        EObject lv_a2_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2859:28: ( ( ( (lv_a1_0_0= rulerelational_expr ) ) (otherlv_1= 'and' ( (lv_a2_2_0= rulerelational_expr ) ) )* ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2860:1: ( ( (lv_a1_0_0= rulerelational_expr ) ) (otherlv_1= 'and' ( (lv_a2_2_0= rulerelational_expr ) ) )* )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2860:1: ( ( (lv_a1_0_0= rulerelational_expr ) ) (otherlv_1= 'and' ( (lv_a2_2_0= rulerelational_expr ) ) )* )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2860:2: ( (lv_a1_0_0= rulerelational_expr ) ) (otherlv_1= 'and' ( (lv_a2_2_0= rulerelational_expr ) ) )*
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2860:2: ( (lv_a1_0_0= rulerelational_expr ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2861:1: (lv_a1_0_0= rulerelational_expr )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2861:1: (lv_a1_0_0= rulerelational_expr )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2862:3: lv_a1_0_0= rulerelational_expr
            {
             
            	        newCompositeNode(grammarAccess.getConjunctionAccess().getA1Relational_exprParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_rulerelational_expr_in_ruleconjunction7371);
            lv_a1_0_0=rulerelational_expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getConjunctionRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"relational_expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2878:2: (otherlv_1= 'and' ( (lv_a2_2_0= rulerelational_expr ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==85) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2878:4: otherlv_1= 'and' ( (lv_a2_2_0= rulerelational_expr ) )
            	    {
            	    otherlv_1=(Token)match(input,85,FOLLOW_85_in_ruleconjunction7384); 

            	        	newLeafNode(otherlv_1, grammarAccess.getConjunctionAccess().getAndKeyword_1_0());
            	        
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2882:1: ( (lv_a2_2_0= rulerelational_expr ) )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2883:1: (lv_a2_2_0= rulerelational_expr )
            	    {
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2883:1: (lv_a2_2_0= rulerelational_expr )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2884:3: lv_a2_2_0= rulerelational_expr
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getConjunctionAccess().getA2Relational_exprParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulerelational_expr_in_ruleconjunction7405);
            	    lv_a2_2_0=rulerelational_expr();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getConjunctionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"a2",
            	            		lv_a2_2_0, 
            	            		"relational_expr");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
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
    // $ANTLR end "ruleconjunction"


    // $ANTLR start "entryRulerelational_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2908:1: entryRulerelational_expr returns [EObject current=null] : iv_rulerelational_expr= rulerelational_expr EOF ;
    public final EObject entryRulerelational_expr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulerelational_expr = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2909:2: (iv_rulerelational_expr= rulerelational_expr EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2910:2: iv_rulerelational_expr= rulerelational_expr EOF
            {
             newCompositeNode(grammarAccess.getRelational_exprRule()); 
            pushFollow(FOLLOW_rulerelational_expr_in_entryRulerelational_expr7443);
            iv_rulerelational_expr=rulerelational_expr();

            state._fsp--;

             current =iv_rulerelational_expr; 
            match(input,EOF,FOLLOW_EOF_in_entryRulerelational_expr7453); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulerelational_expr"


    // $ANTLR start "rulerelational_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2917:1: rulerelational_expr returns [EObject current=null] : ( ( (lv_a1_0_0= ruleaddition ) ) ( rulecomparison_operator ( (lv_a2_2_0= ruleaddition ) ) )? ) ;
    public final EObject rulerelational_expr() throws RecognitionException {
        EObject current = null;

        EObject lv_a1_0_0 = null;

        EObject lv_a2_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2920:28: ( ( ( (lv_a1_0_0= ruleaddition ) ) ( rulecomparison_operator ( (lv_a2_2_0= ruleaddition ) ) )? ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2921:1: ( ( (lv_a1_0_0= ruleaddition ) ) ( rulecomparison_operator ( (lv_a2_2_0= ruleaddition ) ) )? )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2921:1: ( ( (lv_a1_0_0= ruleaddition ) ) ( rulecomparison_operator ( (lv_a2_2_0= ruleaddition ) ) )? )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2921:2: ( (lv_a1_0_0= ruleaddition ) ) ( rulecomparison_operator ( (lv_a2_2_0= ruleaddition ) ) )?
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2921:2: ( (lv_a1_0_0= ruleaddition ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2922:1: (lv_a1_0_0= ruleaddition )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2922:1: (lv_a1_0_0= ruleaddition )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2923:3: lv_a1_0_0= ruleaddition
            {
             
            	        newCompositeNode(grammarAccess.getRelational_exprAccess().getA1AdditionParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleaddition_in_rulerelational_expr7499);
            lv_a1_0_0=ruleaddition();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getRelational_exprRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"addition");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2939:2: ( rulecomparison_operator ( (lv_a2_2_0= ruleaddition ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=RULE_TOK_NOTEQUAL && LA17_0<=RULE_TOK_GE)||LA17_0==88) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2940:5: rulecomparison_operator ( (lv_a2_2_0= ruleaddition ) )
                    {
                     
                            newCompositeNode(grammarAccess.getRelational_exprAccess().getComparison_operatorParserRuleCall_1_0()); 
                        
                    pushFollow(FOLLOW_rulecomparison_operator_in_rulerelational_expr7516);
                    rulecomparison_operator();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2947:1: ( (lv_a2_2_0= ruleaddition ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2948:1: (lv_a2_2_0= ruleaddition )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2948:1: (lv_a2_2_0= ruleaddition )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2949:3: lv_a2_2_0= ruleaddition
                    {
                     
                    	        newCompositeNode(grammarAccess.getRelational_exprAccess().getA2AdditionParserRuleCall_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleaddition_in_rulerelational_expr7536);
                    lv_a2_2_0=ruleaddition();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getRelational_exprRule());
                    	        }
                           		set(
                           			current, 
                           			"a2",
                            		lv_a2_2_0, 
                            		"addition");
                    	        afterParserOrEnumRuleCall();
                    	    

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
    // $ANTLR end "rulerelational_expr"


    // $ANTLR start "entryRuleaddition"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2973:1: entryRuleaddition returns [EObject current=null] : iv_ruleaddition= ruleaddition EOF ;
    public final EObject entryRuleaddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleaddition = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2974:2: (iv_ruleaddition= ruleaddition EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2975:2: iv_ruleaddition= ruleaddition EOF
            {
             newCompositeNode(grammarAccess.getAdditionRule()); 
            pushFollow(FOLLOW_ruleaddition_in_entryRuleaddition7574);
            iv_ruleaddition=ruleaddition();

            state._fsp--;

             current =iv_ruleaddition; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleaddition7584); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleaddition"


    // $ANTLR start "ruleaddition"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2982:1: ruleaddition returns [EObject current=null] : ( ( (lv_a1_0_0= rulemultiplication ) ) ( ruleplus_or_minus ( (lv_a2_2_0= rulemultiplication ) ) )* ) ;
    public final EObject ruleaddition() throws RecognitionException {
        EObject current = null;

        EObject lv_a1_0_0 = null;

        EObject lv_a2_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2985:28: ( ( ( (lv_a1_0_0= rulemultiplication ) ) ( ruleplus_or_minus ( (lv_a2_2_0= rulemultiplication ) ) )* ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2986:1: ( ( (lv_a1_0_0= rulemultiplication ) ) ( ruleplus_or_minus ( (lv_a2_2_0= rulemultiplication ) ) )* )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2986:1: ( ( (lv_a1_0_0= rulemultiplication ) ) ( ruleplus_or_minus ( (lv_a2_2_0= rulemultiplication ) ) )* )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2986:2: ( (lv_a1_0_0= rulemultiplication ) ) ( ruleplus_or_minus ( (lv_a2_2_0= rulemultiplication ) ) )*
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2986:2: ( (lv_a1_0_0= rulemultiplication ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2987:1: (lv_a1_0_0= rulemultiplication )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2987:1: (lv_a1_0_0= rulemultiplication )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:2988:3: lv_a1_0_0= rulemultiplication
            {
             
            	        newCompositeNode(grammarAccess.getAdditionAccess().getA1MultiplicationParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_rulemultiplication_in_ruleaddition7630);
            lv_a1_0_0=rulemultiplication();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getAdditionRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"multiplication");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3004:2: ( ruleplus_or_minus ( (lv_a2_2_0= rulemultiplication ) ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=RULE_TOK_PLUS && LA18_0<=RULE_TOK_MINUS)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3005:5: ruleplus_or_minus ( (lv_a2_2_0= rulemultiplication ) )
            	    {
            	     
            	            newCompositeNode(grammarAccess.getAdditionAccess().getPlus_or_minusParserRuleCall_1_0()); 
            	        
            	    pushFollow(FOLLOW_ruleplus_or_minus_in_ruleaddition7647);
            	    ruleplus_or_minus();

            	    state._fsp--;

            	     
            	            afterParserOrEnumRuleCall();
            	        
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3012:1: ( (lv_a2_2_0= rulemultiplication ) )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3013:1: (lv_a2_2_0= rulemultiplication )
            	    {
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3013:1: (lv_a2_2_0= rulemultiplication )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3014:3: lv_a2_2_0= rulemultiplication
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getAdditionAccess().getA2MultiplicationParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulemultiplication_in_ruleaddition7667);
            	    lv_a2_2_0=rulemultiplication();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getAdditionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"a2",
            	            		lv_a2_2_0, 
            	            		"multiplication");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
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
    // $ANTLR end "ruleaddition"


    // $ANTLR start "entryRulemultiplication"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3038:1: entryRulemultiplication returns [EObject current=null] : iv_rulemultiplication= rulemultiplication EOF ;
    public final EObject entryRulemultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_rulemultiplication = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3039:2: (iv_rulemultiplication= rulemultiplication EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3040:2: iv_rulemultiplication= rulemultiplication EOF
            {
             newCompositeNode(grammarAccess.getMultiplicationRule()); 
            pushFollow(FOLLOW_rulemultiplication_in_entryRulemultiplication7705);
            iv_rulemultiplication=rulemultiplication();

            state._fsp--;

             current =iv_rulemultiplication; 
            match(input,EOF,FOLLOW_EOF_in_entryRulemultiplication7715); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulemultiplication"


    // $ANTLR start "rulemultiplication"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3047:1: rulemultiplication returns [EObject current=null] : ( ( (lv_a1_0_0= rulesign_expr ) ) ( rulemult_op ( (lv_a2_2_0= rulesign_expr ) ) )* ) ;
    public final EObject rulemultiplication() throws RecognitionException {
        EObject current = null;

        EObject lv_a1_0_0 = null;

        EObject lv_a2_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3050:28: ( ( ( (lv_a1_0_0= rulesign_expr ) ) ( rulemult_op ( (lv_a2_2_0= rulesign_expr ) ) )* ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3051:1: ( ( (lv_a1_0_0= rulesign_expr ) ) ( rulemult_op ( (lv_a2_2_0= rulesign_expr ) ) )* )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3051:1: ( ( (lv_a1_0_0= rulesign_expr ) ) ( rulemult_op ( (lv_a2_2_0= rulesign_expr ) ) )* )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3051:2: ( (lv_a1_0_0= rulesign_expr ) ) ( rulemult_op ( (lv_a2_2_0= rulesign_expr ) ) )*
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3051:2: ( (lv_a1_0_0= rulesign_expr ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3052:1: (lv_a1_0_0= rulesign_expr )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3052:1: (lv_a1_0_0= rulesign_expr )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3053:3: lv_a1_0_0= rulesign_expr
            {
             
            	        newCompositeNode(grammarAccess.getMultiplicationAccess().getA1Sign_exprParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_rulesign_expr_in_rulemultiplication7761);
            lv_a1_0_0=rulesign_expr();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	        }
                   		set(
                   			current, 
                   			"a1",
                    		lv_a1_0_0, 
                    		"sign_expr");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3069:2: ( rulemult_op ( (lv_a2_2_0= rulesign_expr ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=RULE_TOK_TIMES && LA19_0<=RULE_TOK_DIV)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3070:5: rulemult_op ( (lv_a2_2_0= rulesign_expr ) )
            	    {
            	     
            	            newCompositeNode(grammarAccess.getMultiplicationAccess().getMult_opParserRuleCall_1_0()); 
            	        
            	    pushFollow(FOLLOW_rulemult_op_in_rulemultiplication7778);
            	    rulemult_op();

            	    state._fsp--;

            	     
            	            afterParserOrEnumRuleCall();
            	        
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3077:1: ( (lv_a2_2_0= rulesign_expr ) )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3078:1: (lv_a2_2_0= rulesign_expr )
            	    {
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3078:1: (lv_a2_2_0= rulesign_expr )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3079:3: lv_a2_2_0= rulesign_expr
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMultiplicationAccess().getA2Sign_exprParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_rulesign_expr_in_rulemultiplication7798);
            	    lv_a2_2_0=rulesign_expr();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"a2",
            	            		lv_a2_2_0, 
            	            		"sign_expr");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
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
    // $ANTLR end "rulemultiplication"


    // $ANTLR start "entryRulesign_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3103:1: entryRulesign_expr returns [EObject current=null] : iv_rulesign_expr= rulesign_expr EOF ;
    public final EObject entryRulesign_expr() throws RecognitionException {
        EObject current = null;

        EObject iv_rulesign_expr = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3104:2: (iv_rulesign_expr= rulesign_expr EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3105:2: iv_rulesign_expr= rulesign_expr EOF
            {
             newCompositeNode(grammarAccess.getSign_exprRule()); 
            pushFollow(FOLLOW_rulesign_expr_in_entryRulesign_expr7836);
            iv_rulesign_expr=rulesign_expr();

            state._fsp--;

             current =iv_rulesign_expr; 
            match(input,EOF,FOLLOW_EOF_in_entryRulesign_expr7846); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulesign_expr"


    // $ANTLR start "rulesign_expr"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3112:1: rulesign_expr returns [EObject current=null] : this_term_0= ruleterm ;
    public final EObject rulesign_expr() throws RecognitionException {
        EObject current = null;

        EObject this_term_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3115:28: (this_term_0= ruleterm )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3117:5: this_term_0= ruleterm
            {
             
                    newCompositeNode(grammarAccess.getSign_exprAccess().getTermParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleterm_in_rulesign_expr7892);
            this_term_0=ruleterm();

            state._fsp--;

             
                    current = this_term_0; 
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
    // $ANTLR end "rulesign_expr"


    // $ANTLR start "entryRuleterm"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3135:1: entryRuleterm returns [EObject current=null] : iv_ruleterm= ruleterm EOF ;
    public final EObject entryRuleterm() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleterm = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3136:2: (iv_ruleterm= ruleterm EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3137:2: iv_ruleterm= ruleterm EOF
            {
             newCompositeNode(grammarAccess.getTermRule()); 
            pushFollow(FOLLOW_ruleterm_in_entryRuleterm7928);
            iv_ruleterm=ruleterm();

            state._fsp--;

             current =iv_ruleterm; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleterm7938); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleterm"


    // $ANTLR start "ruleterm"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3144:1: ruleterm returns [EObject current=null] : ( ( (lv_a1_0_0= rulerval ) ) | (this_TOK_LPAREN_1= RULE_TOK_LPAREN ( (lv_a2_2_0= ruleexpr ) ) this_TOK_RPAREN_3= RULE_TOK_RPAREN ) ) ;
    public final EObject ruleterm() throws RecognitionException {
        EObject current = null;

        Token this_TOK_LPAREN_1=null;
        Token this_TOK_RPAREN_3=null;
        AntlrDatatypeRuleToken lv_a1_0_0 = null;

        EObject lv_a2_2_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3147:28: ( ( ( (lv_a1_0_0= rulerval ) ) | (this_TOK_LPAREN_1= RULE_TOK_LPAREN ( (lv_a2_2_0= ruleexpr ) ) this_TOK_RPAREN_3= RULE_TOK_RPAREN ) ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3148:1: ( ( (lv_a1_0_0= rulerval ) ) | (this_TOK_LPAREN_1= RULE_TOK_LPAREN ( (lv_a2_2_0= ruleexpr ) ) this_TOK_RPAREN_3= RULE_TOK_RPAREN ) )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3148:1: ( ( (lv_a1_0_0= rulerval ) ) | (this_TOK_LPAREN_1= RULE_TOK_LPAREN ( (lv_a2_2_0= ruleexpr ) ) this_TOK_RPAREN_3= RULE_TOK_RPAREN ) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RULE_TOK_STRING||LA20_0==RULE_INT||(LA20_0>=86 && LA20_0<=87)) ) {
                alt20=1;
            }
            else if ( (LA20_0==RULE_TOK_LPAREN) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3148:2: ( (lv_a1_0_0= rulerval ) )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3148:2: ( (lv_a1_0_0= rulerval ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3149:1: (lv_a1_0_0= rulerval )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3149:1: (lv_a1_0_0= rulerval )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3150:3: lv_a1_0_0= rulerval
                    {
                     
                    	        newCompositeNode(grammarAccess.getTermAccess().getA1RvalParserRuleCall_0_0()); 
                    	    
                    pushFollow(FOLLOW_rulerval_in_ruleterm7984);
                    lv_a1_0_0=rulerval();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getTermRule());
                    	        }
                           		set(
                           			current, 
                           			"a1",
                            		lv_a1_0_0, 
                            		"rval");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3167:6: (this_TOK_LPAREN_1= RULE_TOK_LPAREN ( (lv_a2_2_0= ruleexpr ) ) this_TOK_RPAREN_3= RULE_TOK_RPAREN )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3167:6: (this_TOK_LPAREN_1= RULE_TOK_LPAREN ( (lv_a2_2_0= ruleexpr ) ) this_TOK_RPAREN_3= RULE_TOK_RPAREN )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3167:7: this_TOK_LPAREN_1= RULE_TOK_LPAREN ( (lv_a2_2_0= ruleexpr ) ) this_TOK_RPAREN_3= RULE_TOK_RPAREN
                    {
                    this_TOK_LPAREN_1=(Token)match(input,RULE_TOK_LPAREN,FOLLOW_RULE_TOK_LPAREN_in_ruleterm8002); 
                     
                        newLeafNode(this_TOK_LPAREN_1, grammarAccess.getTermAccess().getTOK_LPARENTerminalRuleCall_1_0()); 
                        
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3171:1: ( (lv_a2_2_0= ruleexpr ) )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3172:1: (lv_a2_2_0= ruleexpr )
                    {
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3172:1: (lv_a2_2_0= ruleexpr )
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3173:3: lv_a2_2_0= ruleexpr
                    {
                     
                    	        newCompositeNode(grammarAccess.getTermAccess().getA2ExprParserRuleCall_1_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleexpr_in_ruleterm8022);
                    lv_a2_2_0=ruleexpr();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getTermRule());
                    	        }
                           		set(
                           			current, 
                           			"a2",
                            		lv_a2_2_0, 
                            		"expr");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    this_TOK_RPAREN_3=(Token)match(input,RULE_TOK_RPAREN,FOLLOW_RULE_TOK_RPAREN_in_ruleterm8033); 
                     
                        newLeafNode(this_TOK_RPAREN_3, grammarAccess.getTermAccess().getTOK_RPARENTerminalRuleCall_1_2()); 
                        

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
    // $ANTLR end "ruleterm"


    // $ANTLR start "entryRuleinstance_start_segment"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3207:1: entryRuleinstance_start_segment returns [EObject current=null] : iv_ruleinstance_start_segment= ruleinstance_start_segment EOF ;
    public final EObject entryRuleinstance_start_segment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleinstance_start_segment = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3208:2: (iv_ruleinstance_start_segment= ruleinstance_start_segment EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3209:2: iv_ruleinstance_start_segment= ruleinstance_start_segment EOF
            {
             newCompositeNode(grammarAccess.getInstance_start_segmentRule()); 
            pushFollow(FOLLOW_ruleinstance_start_segment_in_entryRuleinstance_start_segment8075);
            iv_ruleinstance_start_segment=ruleinstance_start_segment();

            state._fsp--;

             current =iv_ruleinstance_start_segment; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleinstance_start_segment8085); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleinstance_start_segment"


    // $ANTLR start "ruleinstance_start_segment"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3216:1: ruleinstance_start_segment returns [EObject current=null] : ( ruleroot_element_label (this_array_refs_1= rulearray_refs )? ) ;
    public final EObject ruleinstance_start_segment() throws RecognitionException {
        EObject current = null;

        EObject this_array_refs_1 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3219:28: ( ( ruleroot_element_label (this_array_refs_1= rulearray_refs )? ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3220:1: ( ruleroot_element_label (this_array_refs_1= rulearray_refs )? )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3220:1: ( ruleroot_element_label (this_array_refs_1= rulearray_refs )? )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3221:5: ruleroot_element_label (this_array_refs_1= rulearray_refs )?
            {
             
                    newCompositeNode(grammarAccess.getInstance_start_segmentAccess().getRoot_element_labelParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleroot_element_label_in_ruleinstance_start_segment8126);
            ruleroot_element_label();

            state._fsp--;

             
                    afterParserOrEnumRuleCall();
                
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3228:1: (this_array_refs_1= rulearray_refs )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_TOK_LSQBR) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3229:5: this_array_refs_1= rulearray_refs
                    {
                     
                            newCompositeNode(grammarAccess.getInstance_start_segmentAccess().getArray_refsParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_rulearray_refs_in_ruleinstance_start_segment8148);
                    this_array_refs_1=rulearray_refs();

                    state._fsp--;

                     
                            current = this_array_refs_1; 
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleinstance_start_segment"


    // $ANTLR start "entryRulearray_refs"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3253:1: entryRulearray_refs returns [EObject current=null] : iv_rulearray_refs= rulearray_refs EOF ;
    public final EObject entryRulearray_refs() throws RecognitionException {
        EObject current = null;

        EObject iv_rulearray_refs = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3254:2: (iv_rulearray_refs= rulearray_refs EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3255:2: iv_rulearray_refs= rulearray_refs EOF
            {
             newCompositeNode(grammarAccess.getArray_refsRule()); 
            pushFollow(FOLLOW_rulearray_refs_in_entryRulearray_refs8193);
            iv_rulearray_refs=rulearray_refs();

            state._fsp--;

             current =iv_rulearray_refs; 
            match(input,EOF,FOLLOW_EOF_in_entryRulearray_refs8203); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulearray_refs"


    // $ANTLR start "rulearray_refs"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3262:1: rulearray_refs returns [EObject current=null] : (this_TOK_LSQBR_0= RULE_TOK_LSQBR ( (lv_a1_1_0= ruleexpr ) ) this_TOK_RSQBR_2= RULE_TOK_RSQBR )+ ;
    public final EObject rulearray_refs() throws RecognitionException {
        EObject current = null;

        Token this_TOK_LSQBR_0=null;
        Token this_TOK_RSQBR_2=null;
        EObject lv_a1_1_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3265:28: ( (this_TOK_LSQBR_0= RULE_TOK_LSQBR ( (lv_a1_1_0= ruleexpr ) ) this_TOK_RSQBR_2= RULE_TOK_RSQBR )+ )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3266:1: (this_TOK_LSQBR_0= RULE_TOK_LSQBR ( (lv_a1_1_0= ruleexpr ) ) this_TOK_RSQBR_2= RULE_TOK_RSQBR )+
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3266:1: (this_TOK_LSQBR_0= RULE_TOK_LSQBR ( (lv_a1_1_0= ruleexpr ) ) this_TOK_RSQBR_2= RULE_TOK_RSQBR )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==RULE_TOK_LSQBR) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3266:2: this_TOK_LSQBR_0= RULE_TOK_LSQBR ( (lv_a1_1_0= ruleexpr ) ) this_TOK_RSQBR_2= RULE_TOK_RSQBR
            	    {
            	    this_TOK_LSQBR_0=(Token)match(input,RULE_TOK_LSQBR,FOLLOW_RULE_TOK_LSQBR_in_rulearray_refs8239); 
            	     
            	        newLeafNode(this_TOK_LSQBR_0, grammarAccess.getArray_refsAccess().getTOK_LSQBRTerminalRuleCall_0()); 
            	        
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3270:1: ( (lv_a1_1_0= ruleexpr ) )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3271:1: (lv_a1_1_0= ruleexpr )
            	    {
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3271:1: (lv_a1_1_0= ruleexpr )
            	    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3272:3: lv_a1_1_0= ruleexpr
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getArray_refsAccess().getA1ExprParserRuleCall_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleexpr_in_rulearray_refs8259);
            	    lv_a1_1_0=ruleexpr();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getArray_refsRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"a1",
            	            		lv_a1_1_0, 
            	            		"expr");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }

            	    this_TOK_RSQBR_2=(Token)match(input,RULE_TOK_RSQBR,FOLLOW_RULE_TOK_RSQBR_in_rulearray_refs8270); 
            	     
            	        newLeafNode(this_TOK_RSQBR_2, grammarAccess.getArray_refsAccess().getTOK_RSQBRTerminalRuleCall_2()); 
            	        

            	    }
            	    break;

            	default :
            	    if ( cnt22 >= 1 ) break loop22;
                        EarlyExitException eee =
                            new EarlyExitException(22, input);
                        throw eee;
                }
                cnt22++;
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
    // $ANTLR end "rulearray_refs"


    // $ANTLR start "entryRulerval"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3300:1: entryRulerval returns [String current=null] : iv_rulerval= rulerval EOF ;
    public final String entryRulerval() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulerval = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3301:2: (iv_rulerval= rulerval EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3302:2: iv_rulerval= rulerval EOF
            {
             newCompositeNode(grammarAccess.getRvalRule()); 
            pushFollow(FOLLOW_rulerval_in_entryRulerval8307);
            iv_rulerval=rulerval();

            state._fsp--;

             current =iv_rulerval.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulerval8318); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulerval"


    // $ANTLR start "rulerval"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3309:1: rulerval returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_constant_value_0= ruleconstant_value ;
    public final AntlrDatatypeRuleToken rulerval() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_constant_value_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3312:28: (this_constant_value_0= ruleconstant_value )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3314:5: this_constant_value_0= ruleconstant_value
            {
             
                    newCompositeNode(grammarAccess.getRvalAccess().getConstant_valueParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleconstant_value_in_rulerval8364);
            this_constant_value_0=ruleconstant_value();

            state._fsp--;


            		current.merge(this_constant_value_0);
                
             
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
    // $ANTLR end "rulerval"


    // $ANTLR start "entryRuleconstant_value"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3332:1: entryRuleconstant_value returns [String current=null] : iv_ruleconstant_value= ruleconstant_value EOF ;
    public final String entryRuleconstant_value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleconstant_value = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3333:2: (iv_ruleconstant_value= ruleconstant_value EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3334:2: iv_ruleconstant_value= ruleconstant_value EOF
            {
             newCompositeNode(grammarAccess.getConstant_valueRule()); 
            pushFollow(FOLLOW_ruleconstant_value_in_entryRuleconstant_value8409);
            iv_ruleconstant_value=ruleconstant_value();

            state._fsp--;

             current =iv_ruleconstant_value.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleconstant_value8420); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleconstant_value"


    // $ANTLR start "ruleconstant_value"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3341:1: ruleconstant_value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_TOK_NUMBER_0= ruleTOK_NUMBER | this_TOK_STRING_1= RULE_TOK_STRING | kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleconstant_value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_TOK_STRING_1=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_TOK_NUMBER_0 = null;


         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3344:28: ( (this_TOK_NUMBER_0= ruleTOK_NUMBER | this_TOK_STRING_1= RULE_TOK_STRING | kw= 'true' | kw= 'false' ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3345:1: (this_TOK_NUMBER_0= ruleTOK_NUMBER | this_TOK_STRING_1= RULE_TOK_STRING | kw= 'true' | kw= 'false' )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3345:1: (this_TOK_NUMBER_0= ruleTOK_NUMBER | this_TOK_STRING_1= RULE_TOK_STRING | kw= 'true' | kw= 'false' )
            int alt23=4;
            switch ( input.LA(1) ) {
            case RULE_INT:
                {
                alt23=1;
                }
                break;
            case RULE_TOK_STRING:
                {
                alt23=2;
                }
                break;
            case 86:
                {
                alt23=3;
                }
                break;
            case 87:
                {
                alt23=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3346:5: this_TOK_NUMBER_0= ruleTOK_NUMBER
                    {
                     
                            newCompositeNode(grammarAccess.getConstant_valueAccess().getTOK_NUMBERParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleTOK_NUMBER_in_ruleconstant_value8467);
                    this_TOK_NUMBER_0=ruleTOK_NUMBER();

                    state._fsp--;


                    		current.merge(this_TOK_NUMBER_0);
                        
                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3357:10: this_TOK_STRING_1= RULE_TOK_STRING
                    {
                    this_TOK_STRING_1=(Token)match(input,RULE_TOK_STRING,FOLLOW_RULE_TOK_STRING_in_ruleconstant_value8493); 

                    		current.merge(this_TOK_STRING_1);
                        
                     
                        newLeafNode(this_TOK_STRING_1, grammarAccess.getConstant_valueAccess().getTOK_STRINGTerminalRuleCall_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3366:2: kw= 'true'
                    {
                    kw=(Token)match(input,86,FOLLOW_86_in_ruleconstant_value8517); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getConstant_valueAccess().getTrueKeyword_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3373:2: kw= 'false'
                    {
                    kw=(Token)match(input,87,FOLLOW_87_in_ruleconstant_value8536); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getConstant_valueAccess().getFalseKeyword_3()); 
                        

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
    // $ANTLR end "ruleconstant_value"


    // $ANTLR start "entryRulecomparison_operator"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3386:1: entryRulecomparison_operator returns [String current=null] : iv_rulecomparison_operator= rulecomparison_operator EOF ;
    public final String entryRulecomparison_operator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulecomparison_operator = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3387:2: (iv_rulecomparison_operator= rulecomparison_operator EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3388:2: iv_rulecomparison_operator= rulecomparison_operator EOF
            {
             newCompositeNode(grammarAccess.getComparison_operatorRule()); 
            pushFollow(FOLLOW_rulecomparison_operator_in_entryRulecomparison_operator8577);
            iv_rulecomparison_operator=rulecomparison_operator();

            state._fsp--;

             current =iv_rulecomparison_operator.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulecomparison_operator8588); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulecomparison_operator"


    // $ANTLR start "rulecomparison_operator"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3395:1: rulecomparison_operator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '==' | this_TOK_NOTEQUAL_1= RULE_TOK_NOTEQUAL | this_TOK_LESSTHAN_2= RULE_TOK_LESSTHAN | this_TOK_LE_3= RULE_TOK_LE | this_TOK_GT_4= RULE_TOK_GT | this_TOK_GE_5= RULE_TOK_GE ) ;
    public final AntlrDatatypeRuleToken rulecomparison_operator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_TOK_NOTEQUAL_1=null;
        Token this_TOK_LESSTHAN_2=null;
        Token this_TOK_LE_3=null;
        Token this_TOK_GT_4=null;
        Token this_TOK_GE_5=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3398:28: ( (kw= '==' | this_TOK_NOTEQUAL_1= RULE_TOK_NOTEQUAL | this_TOK_LESSTHAN_2= RULE_TOK_LESSTHAN | this_TOK_LE_3= RULE_TOK_LE | this_TOK_GT_4= RULE_TOK_GT | this_TOK_GE_5= RULE_TOK_GE ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3399:1: (kw= '==' | this_TOK_NOTEQUAL_1= RULE_TOK_NOTEQUAL | this_TOK_LESSTHAN_2= RULE_TOK_LESSTHAN | this_TOK_LE_3= RULE_TOK_LE | this_TOK_GT_4= RULE_TOK_GT | this_TOK_GE_5= RULE_TOK_GE )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3399:1: (kw= '==' | this_TOK_NOTEQUAL_1= RULE_TOK_NOTEQUAL | this_TOK_LESSTHAN_2= RULE_TOK_LESSTHAN | this_TOK_LE_3= RULE_TOK_LE | this_TOK_GT_4= RULE_TOK_GT | this_TOK_GE_5= RULE_TOK_GE )
            int alt24=6;
            switch ( input.LA(1) ) {
            case 88:
                {
                alt24=1;
                }
                break;
            case RULE_TOK_NOTEQUAL:
                {
                alt24=2;
                }
                break;
            case RULE_TOK_LESSTHAN:
                {
                alt24=3;
                }
                break;
            case RULE_TOK_LE:
                {
                alt24=4;
                }
                break;
            case RULE_TOK_GT:
                {
                alt24=5;
                }
                break;
            case RULE_TOK_GE:
                {
                alt24=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3400:2: kw= '=='
                    {
                    kw=(Token)match(input,88,FOLLOW_88_in_rulecomparison_operator8626); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getComparison_operatorAccess().getEqualsSignEqualsSignKeyword_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3406:10: this_TOK_NOTEQUAL_1= RULE_TOK_NOTEQUAL
                    {
                    this_TOK_NOTEQUAL_1=(Token)match(input,RULE_TOK_NOTEQUAL,FOLLOW_RULE_TOK_NOTEQUAL_in_rulecomparison_operator8647); 

                    		current.merge(this_TOK_NOTEQUAL_1);
                        
                     
                        newLeafNode(this_TOK_NOTEQUAL_1, grammarAccess.getComparison_operatorAccess().getTOK_NOTEQUALTerminalRuleCall_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3414:10: this_TOK_LESSTHAN_2= RULE_TOK_LESSTHAN
                    {
                    this_TOK_LESSTHAN_2=(Token)match(input,RULE_TOK_LESSTHAN,FOLLOW_RULE_TOK_LESSTHAN_in_rulecomparison_operator8673); 

                    		current.merge(this_TOK_LESSTHAN_2);
                        
                     
                        newLeafNode(this_TOK_LESSTHAN_2, grammarAccess.getComparison_operatorAccess().getTOK_LESSTHANTerminalRuleCall_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3422:10: this_TOK_LE_3= RULE_TOK_LE
                    {
                    this_TOK_LE_3=(Token)match(input,RULE_TOK_LE,FOLLOW_RULE_TOK_LE_in_rulecomparison_operator8699); 

                    		current.merge(this_TOK_LE_3);
                        
                     
                        newLeafNode(this_TOK_LE_3, grammarAccess.getComparison_operatorAccess().getTOK_LETerminalRuleCall_3()); 
                        

                    }
                    break;
                case 5 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3430:10: this_TOK_GT_4= RULE_TOK_GT
                    {
                    this_TOK_GT_4=(Token)match(input,RULE_TOK_GT,FOLLOW_RULE_TOK_GT_in_rulecomparison_operator8725); 

                    		current.merge(this_TOK_GT_4);
                        
                     
                        newLeafNode(this_TOK_GT_4, grammarAccess.getComparison_operatorAccess().getTOK_GTTerminalRuleCall_4()); 
                        

                    }
                    break;
                case 6 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3438:10: this_TOK_GE_5= RULE_TOK_GE
                    {
                    this_TOK_GE_5=(Token)match(input,RULE_TOK_GE,FOLLOW_RULE_TOK_GE_in_rulecomparison_operator8751); 

                    		current.merge(this_TOK_GE_5);
                        
                     
                        newLeafNode(this_TOK_GE_5, grammarAccess.getComparison_operatorAccess().getTOK_GETerminalRuleCall_5()); 
                        

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
    // $ANTLR end "rulecomparison_operator"


    // $ANTLR start "entryRuleplus_or_minus"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3453:1: entryRuleplus_or_minus returns [String current=null] : iv_ruleplus_or_minus= ruleplus_or_minus EOF ;
    public final String entryRuleplus_or_minus() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleplus_or_minus = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3454:2: (iv_ruleplus_or_minus= ruleplus_or_minus EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3455:2: iv_ruleplus_or_minus= ruleplus_or_minus EOF
            {
             newCompositeNode(grammarAccess.getPlus_or_minusRule()); 
            pushFollow(FOLLOW_ruleplus_or_minus_in_entryRuleplus_or_minus8797);
            iv_ruleplus_or_minus=ruleplus_or_minus();

            state._fsp--;

             current =iv_ruleplus_or_minus.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleplus_or_minus8808); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleplus_or_minus"


    // $ANTLR start "ruleplus_or_minus"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3462:1: ruleplus_or_minus returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_TOK_PLUS_0= RULE_TOK_PLUS | this_TOK_MINUS_1= RULE_TOK_MINUS ) ;
    public final AntlrDatatypeRuleToken ruleplus_or_minus() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_TOK_PLUS_0=null;
        Token this_TOK_MINUS_1=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3465:28: ( (this_TOK_PLUS_0= RULE_TOK_PLUS | this_TOK_MINUS_1= RULE_TOK_MINUS ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3466:1: (this_TOK_PLUS_0= RULE_TOK_PLUS | this_TOK_MINUS_1= RULE_TOK_MINUS )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3466:1: (this_TOK_PLUS_0= RULE_TOK_PLUS | this_TOK_MINUS_1= RULE_TOK_MINUS )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_TOK_PLUS) ) {
                alt25=1;
            }
            else if ( (LA25_0==RULE_TOK_MINUS) ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3466:6: this_TOK_PLUS_0= RULE_TOK_PLUS
                    {
                    this_TOK_PLUS_0=(Token)match(input,RULE_TOK_PLUS,FOLLOW_RULE_TOK_PLUS_in_ruleplus_or_minus8848); 

                    		current.merge(this_TOK_PLUS_0);
                        
                     
                        newLeafNode(this_TOK_PLUS_0, grammarAccess.getPlus_or_minusAccess().getTOK_PLUSTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3474:10: this_TOK_MINUS_1= RULE_TOK_MINUS
                    {
                    this_TOK_MINUS_1=(Token)match(input,RULE_TOK_MINUS,FOLLOW_RULE_TOK_MINUS_in_ruleplus_or_minus8874); 

                    		current.merge(this_TOK_MINUS_1);
                        
                     
                        newLeafNode(this_TOK_MINUS_1, grammarAccess.getPlus_or_minusAccess().getTOK_MINUSTerminalRuleCall_1()); 
                        

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
    // $ANTLR end "ruleplus_or_minus"


    // $ANTLR start "entryRulemult_op"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3489:1: entryRulemult_op returns [String current=null] : iv_rulemult_op= rulemult_op EOF ;
    public final String entryRulemult_op() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rulemult_op = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3490:2: (iv_rulemult_op= rulemult_op EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3491:2: iv_rulemult_op= rulemult_op EOF
            {
             newCompositeNode(grammarAccess.getMult_opRule()); 
            pushFollow(FOLLOW_rulemult_op_in_entryRulemult_op8920);
            iv_rulemult_op=rulemult_op();

            state._fsp--;

             current =iv_rulemult_op.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRulemult_op8931); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulemult_op"


    // $ANTLR start "rulemult_op"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3498:1: rulemult_op returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_TOK_TIMES_0= RULE_TOK_TIMES | this_TOK_DIV_1= RULE_TOK_DIV ) ;
    public final AntlrDatatypeRuleToken rulemult_op() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_TOK_TIMES_0=null;
        Token this_TOK_DIV_1=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3501:28: ( (this_TOK_TIMES_0= RULE_TOK_TIMES | this_TOK_DIV_1= RULE_TOK_DIV ) )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3502:1: (this_TOK_TIMES_0= RULE_TOK_TIMES | this_TOK_DIV_1= RULE_TOK_DIV )
            {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3502:1: (this_TOK_TIMES_0= RULE_TOK_TIMES | this_TOK_DIV_1= RULE_TOK_DIV )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_TOK_TIMES) ) {
                alt26=1;
            }
            else if ( (LA26_0==RULE_TOK_DIV) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3502:6: this_TOK_TIMES_0= RULE_TOK_TIMES
                    {
                    this_TOK_TIMES_0=(Token)match(input,RULE_TOK_TIMES,FOLLOW_RULE_TOK_TIMES_in_rulemult_op8971); 

                    		current.merge(this_TOK_TIMES_0);
                        
                     
                        newLeafNode(this_TOK_TIMES_0, grammarAccess.getMult_opAccess().getTOK_TIMESTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3510:10: this_TOK_DIV_1= RULE_TOK_DIV
                    {
                    this_TOK_DIV_1=(Token)match(input,RULE_TOK_DIV,FOLLOW_RULE_TOK_DIV_in_rulemult_op8997); 

                    		current.merge(this_TOK_DIV_1);
                        
                     
                        newLeafNode(this_TOK_DIV_1, grammarAccess.getMult_opAccess().getTOK_DIVTerminalRuleCall_1()); 
                        

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
    // $ANTLR end "rulemult_op"


    // $ANTLR start "entryRuleTOK_NUMBER"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3527:1: entryRuleTOK_NUMBER returns [String current=null] : iv_ruleTOK_NUMBER= ruleTOK_NUMBER EOF ;
    public final String entryRuleTOK_NUMBER() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleTOK_NUMBER = null;


        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3528:2: (iv_ruleTOK_NUMBER= ruleTOK_NUMBER EOF )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3529:2: iv_ruleTOK_NUMBER= ruleTOK_NUMBER EOF
            {
             newCompositeNode(grammarAccess.getTOK_NUMBERRule()); 
            pushFollow(FOLLOW_ruleTOK_NUMBER_in_entryRuleTOK_NUMBER9045);
            iv_ruleTOK_NUMBER=ruleTOK_NUMBER();

            state._fsp--;

             current =iv_ruleTOK_NUMBER.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTOK_NUMBER9056); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTOK_NUMBER"


    // $ANTLR start "ruleTOK_NUMBER"
    // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3536:1: ruleTOK_NUMBER returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_INT_0= RULE_INT ;
    public final AntlrDatatypeRuleToken ruleTOK_NUMBER() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;

         enterRule(); 
            
        try {
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3539:28: (this_INT_0= RULE_INT )
            // ../org.xtuml.bp.xtext.oal/src-gen/org/xtuml/bp/xtext/oal/parser/antlr/internal/InternalXoal.g:3540:5: this_INT_0= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleTOK_NUMBER9095); 

            		current.merge(this_INT_0);
                
             
                newLeafNode(this_INT_0, grammarAccess.getTOK_NUMBERAccess().getINTTerminalRuleCall()); 
                

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
    // $ANTLR end "ruleTOK_NUMBER"

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    static final String DFA2_eotS =
        "\30\uffff";
    static final String DFA2_eofS =
        "\30\uffff";
    static final String DFA2_minS =
        "\1\40\11\uffff\1\50\15\uffff";
    static final String DFA2_maxS =
        "\1\105\11\uffff\1\53\15\uffff";
    static final String DFA2_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\14\1\15"+
        "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\12\1\13";
    static final String DFA2_specialS =
        "\30\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\4\1\6\1\7\1\10\1\5\1\uffff\1\11\1\12\4\uffff\1\25\1\13\1"+
            "\14\3\uffff\1\15\1\16\2\uffff\1\2\1\3\1\1\1\17\2\uffff\1\20"+
            "\1\21\3\uffff\1\22\1\uffff\1\24\1\uffff\1\23",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\27\2\uffff\1\26",
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

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "149:2: ( ( ruleimplicit_ib_transform_statement rulefunction_statement ) | ruleimplicit_assignment_statement | ruleimplicit_invocation_statement | this_assignment_statement_4= ruleassignment_statement | this_control_statement_5= rulecontrol_statement | this_break_statement_6= rulebreak_statement | this_bridge_statement_7= rulebridge_statement | this_send_statement_8= rulesend_statement | this_continue_statement_9= rulecontinue_statement | this_create_object_statement_10= rulecreate_object_statement | this_create_event_statement_11= rulecreate_event_statement | this_delete_statement_12= ruledelete_statement | this_for_statement_13= rulefor_statement | this_generate_statement_14= rulegenerate_statement | this_if_statement_15= ruleif_statement | this_relate_statement_16= rulerelate_statement | this_return_statement_17= rulereturn_statement | this_select_statement_18= ruleselect_statement | ruletransform_statement | this_while_statement_20= rulewhile_statement | this_unrelate_statement_21= ruleunrelate_statement | ruledebug_statement )";
        }
    }
 

    public static final BitSet FOLLOW_ruleCode_in_entryRuleCode75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleCode85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleblock_in_ruleCode131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleblock_in_entryRuleblock165 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleblock175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulestatement_in_ruleblock220 = new BitSet(new long[]{0x33CC70DF00000002L,0x000000000000002AL});
    public static final BitSet FOLLOW_rulestatement_in_entryRulestatement256 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulestatement266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleimplicit_ib_transform_statement_in_rulestatement309 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_rulefunction_statement_in_rulestatement324 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruleimplicit_assignment_statement_in_rulestatement346 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruleimplicit_invocation_statement_in_rulestatement367 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruleassignment_statement_in_rulestatement394 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulecontrol_statement_in_rulestatement421 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulebreak_statement_in_rulestatement448 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulebridge_statement_in_rulestatement475 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulesend_statement_in_rulestatement502 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulecontinue_statement_in_rulestatement529 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulecreate_object_statement_in_rulestatement556 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulecreate_event_statement_in_rulestatement583 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruledelete_statement_in_rulestatement610 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulefor_statement_in_rulestatement637 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulegenerate_statement_in_rulestatement664 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruleif_statement_in_rulestatement691 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulerelate_statement_in_rulestatement718 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulereturn_statement_in_rulestatement745 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruleselect_statement_in_rulestatement772 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruletransform_statement_in_rulestatement793 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_rulewhile_statement_in_rulestatement820 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruleunrelate_statement_in_rulestatement847 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_ruledebug_statement_in_rulestatement868 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_rulestatement880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleassignment_statement_in_entryRuleassignment_statement916 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleassignment_statement926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleassignment_statement963 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleassignment_expr_in_ruleassignment_statement984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulebreak_statement_in_entryRulebreak_statement1020 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulebreak_statement1030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_rulebreak_statement1072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulebridge_statement_in_entryRulebridge_statement1120 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulebridge_statement1130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_rulebridge_statement1172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesend_statement_in_entryRulesend_statement1220 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulesend_statement1230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_rulesend_statement1272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulecontrol_statement_in_entryRulecontrol_statement1320 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulecontrol_statement1330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_rulecontrol_statement1373 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37_in_rulecontrol_statement1398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulecontinue_statement_in_entryRulecontinue_statement1434 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulecontinue_statement1444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_rulecontinue_statement1486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulecreate_event_statement_in_entryRulecreate_event_statement1534 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulecreate_event_statement1544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rulecreate_event_statement1581 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_rulecreate_event_statement1593 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_rulecreate_event_statement1605 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_rulelocal_variable_in_rulecreate_event_statement1626 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_rulecreate_event_statement1638 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleevent_spec_in_rulecreate_event_statement1659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulecreate_object_statement_in_entryRulecreate_object_statement1695 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulecreate_object_statement1705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_rulecreate_object_statement1742 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_rulecreate_object_statement1754 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_rulecreate_object_statement1766 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_rulelocal_variable_in_rulecreate_object_statement1787 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_rulecreate_object_statement1799 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleobject_keyletters_in_rulecreate_object_statement1820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledebug_statement_in_entryRuledebug_statement1857 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuledebug_statement1868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruledebug_statement1906 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001F80L});
    public static final BitSet FOLLOW_ruledebug_operand_in_ruledebug_statement1929 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001F80L});
    public static final BitSet FOLLOW_ruledelete_statement_in_entryRuledelete_statement1976 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuledelete_statement1986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruledelete_statement2023 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_ruledelete_statement2035 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruledelete_statement2047 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleinst_ref_var_in_ruledelete_statement2068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefor_statement_in_entryRulefor_statement2104 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulefor_statement2114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_rulefor_statement2151 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_rulefor_statement2163 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_rulelocal_variable_in_rulefor_statement2184 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_rulefor_statement2196 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleinst_ref_set_var_in_rulefor_statement2217 = new BitSet(new long[]{0x33CC70DF00000000L,0x000000000000002AL});
    public static final BitSet FOLLOW_ruleblock_in_rulefor_statement2238 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_rulefor_statement2250 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_rulefor_statement2262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulegenerate_statement_in_entryRulegenerate_statement2298 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulegenerate_statement2308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_rulegenerate_statement2345 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleevent_spec_in_rulegenerate_statement2366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleif_statement_in_entryRuleif_statement2402 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleif_statement2412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_ruleif_statement2449 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleexpr_in_ruleif_statement2470 = new BitSet(new long[]{0x33CC70DF00000000L,0x000000000000002AL});
    public static final BitSet FOLLOW_ruleblock_in_ruleif_statement2491 = new BitSet(new long[]{0x0032000000000000L});
    public static final BitSet FOLLOW_52_in_ruleif_statement2504 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleexpr_in_ruleif_statement2525 = new BitSet(new long[]{0x33CC70DF00000000L,0x000000000000002AL});
    public static final BitSet FOLLOW_ruleblock_in_ruleif_statement2546 = new BitSet(new long[]{0x0022000000000000L});
    public static final BitSet FOLLOW_53_in_ruleif_statement2561 = new BitSet(new long[]{0x33CC70DF00000000L,0x000000000000002AL});
    public static final BitSet FOLLOW_ruleblock_in_ruleif_statement2582 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_ruleif_statement2596 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ruleif_statement2608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleimplicit_assignment_statement_in_entryRuleimplicit_assignment_statement2645 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleimplicit_assignment_statement2656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_ruleimplicit_assignment_statement2693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleimplicit_invocation_statement_in_entryRuleimplicit_invocation_statement2733 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleimplicit_invocation_statement2744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_ruleimplicit_invocation_statement2781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleimplicit_ib_transform_statement_in_entryRuleimplicit_ib_transform_statement2821 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleimplicit_ib_transform_statement2832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_ruleimplicit_ib_transform_statement2869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulerelate_statement_in_entryRulerelate_statement2908 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulerelate_statement2918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_rulerelate_statement2955 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleinst_ref_var_in_rulerelate_statement2976 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_rulerelate_statement2988 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleinst_ref_var_in_rulerelate_statement3009 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_rulerelate_statement3021 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulerelationship_in_rulerelate_statement3042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulereturn_statement_in_entryRulereturn_statement3078 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulereturn_statement3088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_rulereturn_statement3131 = new BitSet(new long[]{0x0000000000200882L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleexpr_in_rulereturn_statement3165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleselect_statement_in_entryRuleselect_statement3202 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleselect_statement3212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_ruleselect_statement3249 = new BitSet(new long[]{0xC000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_62_in_ruleselect_statement3263 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_rulelocal_variable_in_ruleselect_statement3279 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004010L});
    public static final BitSet FOLLOW_ruleobject_spec_in_ruleselect_statement3299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_ruleselect_statement3319 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_rulelocal_variable_in_ruleselect_statement3335 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004010L});
    public static final BitSet FOLLOW_ruleobject_spec_in_ruleselect_statement3355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_ruleselect_statement3375 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_rulelocal_variable_in_ruleselect_statement3391 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004010L});
    public static final BitSet FOLLOW_ruleobject_spec_in_ruleselect_statement3411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruletransform_statement_in_entryRuletransform_statement3450 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuletransform_statement3461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_ruletransform_statement3498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefunction_statement_in_entryRulefunction_statement3538 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulefunction_statement3549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_rulefunction_statement3586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleunrelate_statement_in_entryRuleunrelate_statement3625 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleunrelate_statement3635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_ruleunrelate_statement3672 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleinst_ref_var_in_ruleunrelate_statement3693 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_ruleunrelate_statement3705 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleinst_ref_var_in_ruleunrelate_statement3726 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_ruleunrelate_statement3738 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rulerelationship_in_ruleunrelate_statement3759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulewhile_statement_in_entryRulewhile_statement3795 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulewhile_statement3805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_rulewhile_statement3842 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleexpr_in_rulewhile_statement3863 = new BitSet(new long[]{0x33CC70DF00000000L,0x000000000000002AL});
    public static final BitSet FOLLOW_ruleblock_in_rulewhile_statement3884 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_rulewhile_statement3896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_rulewhile_statement3908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleassignment_expr_in_entryRuleassignment_expr3944 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleassignment_expr3954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleassignment_expr3990 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_TOK_EQUAL_in_ruleassignment_expr4000 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleexpr_in_ruleassignment_expr4020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulebridge_invocation_in_entryRulebridge_invocation4056 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulebridge_invocation4066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleee_keyletters_in_rulebridge_invocation4112 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RULE_TOK_DOUBLECOLON_in_rulebridge_invocation4123 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_RULE_TOK_LPAREN_in_rulebridge_invocation4133 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_TOK_RPAREN_in_rulebridge_invocation4143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleinvocation_in_entryRuleinvocation4181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleinvocation4192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_ruleinvocation4229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledebug_operand_in_entryRuledebug_operand4275 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuledebug_operand4286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_ruledebug_operand4325 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_72_in_ruledebug_operand4339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ruledebug_operand4358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_ruledebug_operand4380 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_72_in_ruledebug_operand4394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ruledebug_operand4413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_ruledebug_operand4435 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_72_in_ruledebug_operand4449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ruledebug_operand4468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ruledebug_operand4489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_ruledebug_operand4508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_ruledebug_operand4527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleevent_spec_in_entryRuleevent_spec4568 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleevent_spec4579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleevent_label_in_ruleevent_spec4626 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_ruleevent_spec4644 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleinst_ref_var_or_ee_keyletters_in_ruleevent_spec4666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleinst_ref_var_or_ee_keyletters_in_entryRuleinst_ref_var_or_ee_keyletters4714 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleinst_ref_var_or_ee_keyletters4725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelocal_variable_in_ruleinst_ref_var_or_ee_keyletters4771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleinstance_chain_in_entryRuleinstance_chain4820 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleinstance_chain4831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_ruleinstance_chain4868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleobject_spec_in_entryRuleobject_spec4908 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleobject_spec4919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_ruleobject_spec4958 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_ruleobject_spec4971 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_rulelocal_variable_in_ruleobject_spec4993 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleinstance_chain_in_ruleobject_spec5020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_ruleobject_spec5046 = new BitSet(new long[]{0x0000000000000010L,0x0000000000070000L});
    public static final BitSet FOLLOW_80_in_ruleobject_spec5060 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_ruleobject_spec5073 = new BitSet(new long[]{0x0000000000000010L,0x0000000000060000L});
    public static final BitSet FOLLOW_ruleobject_keyletters_in_ruleobject_spec5097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledata_item_in_entryRuledata_item5160 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuledata_item5171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledata_item_name_in_ruledata_item5217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledata_item_name_in_entryRuledata_item_name5262 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuledata_item_name5273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulegeneral_name_in_ruledata_item_name5319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulekeyletters_in_entryRulekeyletters5368 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulekeyletters5379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulegeneral_name_in_rulekeyletters5425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleee_keyletters_in_entryRuleee_keyletters5470 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleee_keyletters5481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulekeyletters_in_ruleee_keyletters5527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleevent_label_in_entryRuleevent_label5572 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleevent_label5583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulegeneral_name_in_ruleevent_label5629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulegeneral_name_in_entryRulegeneral_name5676 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulegeneral_name5687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelimited_name_in_rulegeneral_name5733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesvc_general_name_in_entryRulesvc_general_name5778 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulesvc_general_name5789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelimited_name_in_rulesvc_general_name5835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelimited_name_in_entryRulelimited_name5880 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulelimited_name5891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulelimited_name5930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleinst_ref_set_var_in_entryRuleinst_ref_set_var5974 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleinst_ref_set_var5984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelocal_variable_in_ruleinst_ref_set_var6029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleinst_ref_var_in_entryRuleinst_ref_var6064 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleinst_ref_var6074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelocal_variable_in_ruleinst_ref_var6119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelocal_variable_in_entryRulelocal_variable6155 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulelocal_variable6166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleroot_element_label_in_rulelocal_variable6212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleroot_element_label_in_entryRuleroot_element_label6257 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleroot_element_label6268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_ruleroot_element_label6306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_ruleroot_element_label6325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulelimited_name_in_ruleroot_element_label6353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleelement_label_in_entryRuleelement_label6399 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleelement_label6410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulegeneral_name_in_ruleelement_label6456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulefunction_name_in_entryRulefunction_name6501 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulefunction_name6512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulegeneral_name_in_rulefunction_name6558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesvc_function_name_in_entryRulesvc_function_name6603 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulesvc_function_name6614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesvc_general_name_in_rulesvc_function_name6660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleobject_keyletters_in_entryRuleobject_keyletters6707 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleobject_keyletters6718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulekeyletters_in_ruleobject_keyletters6764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulephrase_in_entryRulephrase6809 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulephrase6820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_rulephrase6857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulerelationship_in_entryRulerelationship6897 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulerelationship6908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rulerelationship6947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesupp_data_item_in_entryRulesupp_data_item6992 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulesupp_data_item7003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruledata_item_name_in_rulesupp_data_item7049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleexpr_in_entryRuleexpr7097 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleexpr7107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesub_expr_in_ruleexpr7152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesub_expr_in_entryRulesub_expr7187 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulesub_expr7197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleconjunction_in_rulesub_expr7243 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_rulesub_expr7256 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleconjunction_in_rulesub_expr7277 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_ruleconjunction_in_entryRuleconjunction7315 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleconjunction7325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulerelational_expr_in_ruleconjunction7371 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ruleconjunction7384 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_rulerelational_expr_in_ruleconjunction7405 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_rulerelational_expr_in_entryRulerelational_expr7443 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulerelational_expr7453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleaddition_in_rulerelational_expr7499 = new BitSet(new long[]{0x000000000001F002L,0x0000000001000000L});
    public static final BitSet FOLLOW_rulecomparison_operator_in_rulerelational_expr7516 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleaddition_in_rulerelational_expr7536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleaddition_in_entryRuleaddition7574 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleaddition7584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulemultiplication_in_ruleaddition7630 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_ruleplus_or_minus_in_ruleaddition7647 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_rulemultiplication_in_ruleaddition7667 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_rulemultiplication_in_entryRulemultiplication7705 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulemultiplication7715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulesign_expr_in_rulemultiplication7761 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_rulemult_op_in_rulemultiplication7778 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_rulesign_expr_in_rulemultiplication7798 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_rulesign_expr_in_entryRulesign_expr7836 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulesign_expr7846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleterm_in_rulesign_expr7892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleterm_in_entryRuleterm7928 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleterm7938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulerval_in_ruleterm7984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_LPAREN_in_ruleterm8002 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleexpr_in_ruleterm8022 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RULE_TOK_RPAREN_in_ruleterm8033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleinstance_start_segment_in_entryRuleinstance_start_segment8075 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleinstance_start_segment8085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleroot_element_label_in_ruleinstance_start_segment8126 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_rulearray_refs_in_ruleinstance_start_segment8148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulearray_refs_in_entryRulearray_refs8193 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulearray_refs8203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_LSQBR_in_rulearray_refs8239 = new BitSet(new long[]{0x0000000000200880L,0x0000000000C00000L});
    public static final BitSet FOLLOW_ruleexpr_in_rulearray_refs8259 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_RULE_TOK_RSQBR_in_rulearray_refs8270 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_rulerval_in_entryRulerval8307 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulerval8318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleconstant_value_in_rulerval8364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleconstant_value_in_entryRuleconstant_value8409 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleconstant_value8420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTOK_NUMBER_in_ruleconstant_value8467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_STRING_in_ruleconstant_value8493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_ruleconstant_value8517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_ruleconstant_value8536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulecomparison_operator_in_entryRulecomparison_operator8577 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulecomparison_operator8588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_rulecomparison_operator8626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_NOTEQUAL_in_rulecomparison_operator8647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_LESSTHAN_in_rulecomparison_operator8673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_LE_in_rulecomparison_operator8699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_GT_in_rulecomparison_operator8725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_GE_in_rulecomparison_operator8751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleplus_or_minus_in_entryRuleplus_or_minus8797 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleplus_or_minus8808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_PLUS_in_ruleplus_or_minus8848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_MINUS_in_ruleplus_or_minus8874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rulemult_op_in_entryRulemult_op8920 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRulemult_op8931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_TIMES_in_rulemult_op8971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_TOK_DIV_in_rulemult_op8997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTOK_NUMBER_in_entryRuleTOK_NUMBER9045 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTOK_NUMBER9056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleTOK_NUMBER9095 = new BitSet(new long[]{0x0000000000000002L});

}