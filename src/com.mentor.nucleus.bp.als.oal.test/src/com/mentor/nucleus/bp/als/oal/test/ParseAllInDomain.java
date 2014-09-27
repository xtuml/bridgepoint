//========================================================================
//
//File:      $RCSfile: ParseAllInDomain.java,v $
//Version:   $Revision: 1.29 $
//Modified:  $Date: 2013/01/10 23:00:37 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================
//
package com.mentor.nucleus.bp.als.oal.test;

import java.io.File;
import java.io.StringReader;
import java.util.UUID;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Parsestatus_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.DomainUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class ParseAllInDomain extends BaseTest {

	private String m_domain_name = "";
	public ParseAllInDomain(String arg0) {
      super("Models", arg0);
      // they all start with "test_"
      m_domain_name = arg0.substring(5, arg0.length());
      IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
      store.setValue(
        BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING, true);
	}
    public void test_ComponentSyntaxTest() {
    	parseAllActivities(); }
    public void test_array_test() { parseAllActivities(); }
	public void test_asc() { parseAllActivities(); }
    public void test_BP50_evt() { parseAllActivities(); }
	public void test_BP50_evt2() { parseAllActivities(); }
	public void test_br1() { parseAllActivities(); }
	public void test_br2() { parseAllActivities(); }
	public void test_br1f() { parseAllActivities(); }
	public void test_br2f() { parseAllActivities(); }
	public void test_bridges() { parseAllActivities(); }
	public void test_cl() { parseAllActivities(); }
	public void test_dogs() { parseAllActivities(); }
	public void test_enum1() { parseAllActivities(); }
	public void test_enum2() { parseAllActivities(); }
	public void test_enum3() { parseAllActivities(); }
	public void test_enum4() { parseAllActivities(); }
	public void test_event() { parseAllActivities(); }

	public void test_G_ALL_G_EVT_LE_precreated() { parseAllActivities(); }
	public void test_G_ALL_R_BRG_tim() { parseAllActivities(); }
	public void test_G_ALL_multiple_exit_return() { parseAllActivities(); }
	public void test_G_ALL_nested_invoke() { parseAllActivities(); }
	public void test_G_ALL_performance_test1() { parseAllActivities(); }
	public void test_G_ALL_performance_test2() { parseAllActivities(); }
	public void test_G_ALL_performance_test3() { parseAllActivities(); }
	public void test_G_ALL_performance_test4() { parseAllActivities(); }
	public void test_G_ALL_performance_test5() { parseAllActivities(); }
	public void test_G_ALL_performance_test6() { parseAllActivities(); }
	public void test_G_ALL_performance_test7() { parseAllActivities(); }
	public void test_G_ALL_select_where_enum() { parseAllActivities(); }
	public void test_G_BRG_G_ALL_interop() { parseAllActivities(); }
	public void test_G_COP_R_ALL_interop() { parseAllActivities(); }
	public void test_G_EVT_PE_G_EVT_NLE_nle_ignored() { parseAllActivities(); }
	public void test_G_IOP_MDA_self_event() { parseAllActivities(); }
	public void test_G_IOP_R_ALL_interop() { parseAllActivities(); }
	public void test_G_MDA_R_ALL_interop() { parseAllActivities(); }
	public void test_G_STE_G_COP_compare_date() { parseAllActivities(); }
	public void test_G_STE_G_EVT_PE_to_creation() { parseAllActivities(); }
	public void test_G_STE_G_STE_pe_le_same_state() { parseAllActivities(); }
	public void test_G_STE_assoc_rel() { parseAllActivities(); }
	public void test_G_STE_del_inst_mult() { parseAllActivities(); }

	public void test_im1() { parseAllActivities(); }
	public void test_im2() { parseAllActivities(); }
	public void test_im3() { parseAllActivities(); }
	public void test_im4() { parseAllActivities(); }
	public void test_ims() { parseAllActivities(); }
	public void test_ims2() { parseAllActivities(); }
	public void test_imx() { parseAllActivities(); }
	public void test_init1() { parseAllActivities(); }
	public void test_init2() { parseAllActivities(); }
	public void test_interop_otherdom() { parseAllActivities(); }
	public void test_memleak() { parseAllActivities(); }
	public void test_mt1() { parseAllActivities(); }
	public void test_no_inst() { parseAllActivities(); }
	public void test_poly() { parseAllActivities(); }
	public void test_reflexive() { parseAllActivities(); }
    public void test_sdt_test() { parseAllActivities(); }
	public void test_select() { parseAllActivities(); }
	public void test_self() { parseAllActivities(); }
	public void test_sm() { parseAllActivities(); }
	public void test_sync() { parseAllActivities(); }
	public void test_syntax() { parseAllActivities(); }
	public void test_trans() { parseAllActivities(); }
	public void test_wim2() { parseAllActivities(); }
	public void test_wim3() { parseAllActivities(); }
	public void test_wims() { parseAllActivities(); }
	public void test_wimx() { parseAllActivities(); }

	// these are last becuase they take so long
    public void test_ooaofooa() { parseAllActivities(); }
    public void test_ooaofgraphics() { parseAllActivities(); }
	public void test_ex1() { parseAllActivities(); }
	public void test_ex2() { parseAllActivities(); }
	public void test_ex3() { parseAllActivities(); }

    private static String workspace_path = ""; //$NON-NLS-1$
    private static String m_logfile_path = "";

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

        if (workspace_path == null || workspace_path.equals(""))//$NON-NLS-1$
        {
            workspace_path = System.getProperty("WORKSPACE_PATH");//$NON-NLS-1$
        }
        assertNotNull( workspace_path );
        if (m_logfile_path == null || m_logfile_path.equals(""))
        {
            m_logfile_path = System.getProperty("LOGFILE_PATH");
        }
        assertNotNull( m_logfile_path );

        if ( m_domain_name.equals( "ooaofooa" ) ) {
            ensureAvailableAndLoaded("com.mentor.nucleus.bp.core", m_domain_name, false, false);
        } else if ( m_domain_name.equals( "ooaofgraphics" ) ) {
            ensureAvailableAndLoaded("com.mentor.nucleus.bp.ui.canvas", m_domain_name, false, false);
        } else if ( m_domain_name.equals( "ComponentSyntaxTest" ) ) {
            ensureAvailableAndLoaded("Models", m_domain_name, false, false, "Component Package");
        } else {
            ensureAvailableAndLoaded("Models", m_domain_name, false, false);
        }
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
        try {
            super.tearDown();
            IPath in_path = new Path(m_logfile_path);
            File in_fh = in_path.toFile();
            if ( in_fh.exists() )
            {
                fail( ".log file is not empty");
            }

            OalParserTest.tearDownActionData();
        } catch (RecognitionException re) {
            // do nothing
        } catch (TokenStreamException te) {
            // do nothing
        }
	}

	private void parseAllActivities() {
		parseAllFunctions();
		parseAllBridges();
		parseAllOperations();
		parseAllMDAttrs();
		parseAllStateActivities();
	}
	private void parseAllFunctions()
	{
		Function_c[] func_set = Function_c.FunctionInstances(modelRoot);
		for ( int i = 0; i < func_set.length; ++i )
		{
			if( func_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
			{
				String x = parseAction(func_set[i], func_set[i].getAction_semantics(),
					func_set[i].getSync_id(), Oalconstants_c.FUNCTION_TYPE);
				assertEquals("Function "+func_set[i].getName(), "", x);
			}
		}
	}
	private void parseAllBridges()
	{
		Bridge_c[] bridge_set = Bridge_c.BridgeInstances(modelRoot);
		for ( int i = 0; i < bridge_set.length; ++i )
		{
			if( bridge_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
			{
				String x = parseAction(bridge_set[i], bridge_set[i].getAction_semantics(),
					bridge_set[i].getBrg_id(), Oalconstants_c.BRIDGE_TYPE);
				if ( ! x.equals("") )
				{
					ExternalEntity_c ee = ExternalEntity_c.getOneS_EEOnR19(bridge_set[i]);
					assertEquals("Bridge "+ee.getName()+"."+bridge_set[i].getName(), "", x);
				}
			}
		}
	}
	private void parseAllOperations()
	{
		Operation_c[] op_set = Operation_c.OperationInstances(modelRoot);
		for ( int i = 0; i < op_set.length; ++i )
		{
			if( op_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
			{
				String x = parseAction(op_set[i], op_set[i].getAction_semantics(),
					op_set[i].getTfr_id(), Oalconstants_c.OPERATION_TYPE);
				if ( ! x.equals("") )
				{
					ModelClass_c obj = ModelClass_c.getOneO_OBJOnR115(op_set[i]);
					assertEquals("Operation "+obj.getName()+"."+op_set[i].getName(), "", x);
				}
			}
		}
	}
	private void parseAllMDAttrs()
	{
		DerivedBaseAttribute_c[] mda_set = DerivedBaseAttribute_c.DerivedBaseAttributeInstances(modelRoot);
		for ( int i = 0; i < mda_set.length; ++i )
		{
			if( mda_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
			{
				String x = parseAction(mda_set[i], mda_set[i].getAction_semantics(),
					mda_set[i].getAttr_id(), Oalconstants_c.MDA_TYPE);
				if ( ! x.equals("") )
				{
					Attribute_c attr = Attribute_c.getOneO_ATTROnR106(
					   BaseAttribute_c.getOneO_BATTROnR107(mda_set[i]));
					ModelClass_c obj = ModelClass_c.getOneO_OBJOnR102(attr);
					assertEquals ("MDA "+obj.getName()+"."+attr.getName()+" ", "", x);
				}
			}
		}
	}
	private void parseAllStateActivities()
	{
		Action_c[] action_set = Action_c.ActionInstances(modelRoot);
		for ( int i = 0; i < action_set.length; ++i )
		{
			if( action_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
			{
				String x = parseAction(action_set[i], action_set[i].getAction_semantics(),
					action_set[i].getAct_id(), Oalconstants_c.STATE_TYPE);
				if ( ! x.equals("") )
				{
					StateMachineState_c source =
						StateMachineState_c.getOneSM_STATEOnR511(
							MooreActionHome_c.getOneSM_MOAHOnR513(
								ActionHome_c.getOneSM_AHOnR514(action_set[i])));
					ModelClass_c obj = ModelClass_c.getOneO_OBJOnR518(
					    InstanceStateMachine_c.getOneSM_ISMOnR517(
					    	StateMachine_c.getOneSM_SMOnR501(source)));
					if ( obj != null )
					{
						assertEquals ("ISM State "+obj.getName()+"."+source.getName()+" ", "", x);
					}
					else
					{
						obj = ModelClass_c.getOneO_OBJOnR519(
							ClassStateMachine_c.getOneSM_ASMOnR517(
								StateMachine_c.getOneSM_SMOnR501(source)));
						assertEquals ("CSM State "+obj.getName()+"."+source.getName()+" ", "", x);
					}
				}
			}
		}
	}

	private String parseAction(NonRootModelElement nrme, String stmts, UUID funcId, int funcType)
	{
		OalLexer lexer = new OalLexer(new StringReader(stmts));
		OalParser parser = new OalParser(modelRoot, lexer);
		parser.m_oal_context = new Oal_validate(DomainUtil.getDomainFromUknownME(nrme));
		try
		{
			parser.action(funcId, funcType);
		}
		catch (TokenStreamException e)
		{
		  Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
		  if ( e instanceof TokenStreamRecognitionException )
		  {
			  TokenStreamRecognitionException tsre = (TokenStreamRecognitionException)e;
			  parser.reportError(tsre.recog);
		  }
		  else
		  {
			  fail("Token stream exception in parser");
		  }
		}
		catch (RecognitionException e)
		{
			Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
			parser.reportError(e);
		}
		catch (InterruptedException ie){
			parser.reportError(new RecognitionException("Parser interrupted"));
		}
		return parser.m_output;
	}

}
