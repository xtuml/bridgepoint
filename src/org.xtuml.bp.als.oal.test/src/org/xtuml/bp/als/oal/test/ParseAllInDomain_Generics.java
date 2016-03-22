//========================================================================
//
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
package org.xtuml.bp.als.oal.test;

import java.io.StringReader;
import java.util.UUID;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.als.oal.OalLexer;
import org.xtuml.bp.als.oal.OalParser;
import org.xtuml.bp.als.oal.Oal_validate;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.Block_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Oalconstants_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

@RunWith(OrderedRunner.class)
public class ParseAllInDomain_Generics extends BaseTest {
	
	@Rule public TestName name = new TestName();

	private String m_domain_name = "";
	public ParseAllInDomain_Generics() {
      super("org.xtuml.bp.als.oal.test", null);
      // they all start with "test_"
      IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
      store.setValue(
        BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING, true);
      store.setValue(
              BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE, true);
	}
    @Test
	public void test_ComponentSyntaxTest() { 
    	// This test was written before the Interface Name preference was introduced.
    	// The expected error messages depend on the preference being disabled.
    	IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ALLOW_INTERFACE_NAME_IN_IC_MESSAGE, true);
        parseAllActivities();
        store.setValue(BridgePointPreferencesStore.ALLOW_INTERFACE_NAME_IN_IC_MESSAGE, false);
    }
    @Test
	public void test_array_test() { parseAllActivities(); }
	@Test
	public void test_asc() { parseAllActivities(); }
    @Test
	public void test_BP50_evt() { parseAllActivities(); }
	@Test
	public void test_BP50_evt2() { parseAllActivities(); }
	@Test
	public void test_br1() { parseAllActivities(); }
	@Test
	public void test_br2() { parseAllActivities(); }
	@Test
	public void test_br1f() { parseAllActivities(); }
	@Test
	public void test_br2f() { parseAllActivities(); }
	@Test
	public void test_bridges() { parseAllActivities(); }
	@Test
	public void test_cl() { parseAllActivities(); }
	@Test
	public void test_dogs() { parseAllActivities(); }
	@Test
	public void test_enum1() { parseAllActivities(); }
	@Test
	public void test_enum2() { parseAllActivities(); }
	@Test
	public void test_enum3() { parseAllActivities(); }
	@Test
	public void test_enum4() { parseAllActivities(); }
	@Test
	public void test_event() { parseAllActivities(); }

	@Test
	public void test_G_ALL_G_EVT_LE_precreated() { parseAllActivities(); }
	@Test
	public void test_G_ALL_R_BRG_tim() { parseAllActivities(); }
	@Test
	public void test_G_ALL_multiple_exit_return() { parseAllActivities(); }
	@Test
	public void test_G_ALL_nested_invoke() { parseAllActivities(); }
	@Test
	public void test_G_ALL_performance_test1() { parseAllActivities(); }
	@Test
	public void test_G_ALL_performance_test2() { parseAllActivities(); }
	@Test
	public void test_G_ALL_performance_test3() { parseAllActivities(); }
	@Test
	public void test_G_ALL_performance_test4() { parseAllActivities(); }
	@Test
	public void test_G_ALL_performance_test5() { parseAllActivities(); }
	@Test
	public void test_G_ALL_performance_test6() { parseAllActivities(); }
	@Test
	public void test_G_ALL_performance_test7() { parseAllActivities(); }
	@Test
	public void test_G_ALL_select_where_enum() { parseAllActivities(); }
	@Test
	public void test_G_BRG_G_ALL_interop() { parseAllActivities(); }
	@Test
	public void test_G_COP_R_ALL_interop() { parseAllActivities(); }
	@Test
	public void test_G_EVT_PE_G_EVT_NLE_nle_ignored() { parseAllActivities(); }
	@Test
	public void test_G_IOP_MDA_self_event() { parseAllActivities(); }
	@Test
	public void test_G_IOP_R_ALL_interop() { parseAllActivities(); }
	@Test
	public void test_G_MDA_R_ALL_interop() { parseAllActivities(); }
	@Test
	public void test_G_STE_G_COP_compare_date() { parseAllActivities(); }
	@Test
	public void test_G_STE_G_EVT_PE_to_creation() { parseAllActivities(); }
	@Test
	public void test_G_STE_G_STE_pe_le_same_state() { parseAllActivities(); }
	@Test
	public void test_G_STE_assoc_rel() { parseAllActivities(); }
	@Test
	public void test_G_STE_del_inst_mult() { parseAllActivities(); }

	@Test
	public void test_im1() { parseAllActivities(); }
	@Test
	public void test_im2() { parseAllActivities(); }
	@Test
	public void test_im3() { parseAllActivities(); }
	@Test
	public void test_im4() { parseAllActivities(); }
	@Test
	public void test_ims() { parseAllActivities(); }
	@Test
	public void test_ims2() { parseAllActivities(); }
	@Test
	public void test_imx() { parseAllActivities(); }
	@Test
	public void test_init1() { parseAllActivities(); }
	@Test
	public void test_init2() { parseAllActivities(); }
	@Test
	public void test_interop_otherdom() { parseAllActivities(); }
	@Test
	public void test_memleak() { parseAllActivities(); }
	@Test
	public void test_mt1() { parseAllActivities(); }
	@Test
	public void test_no_inst() { parseAllActivities(); }
	@Test
	public void test_poly() { parseAllActivities(); }
	@Test
	public void test_reflexive() { parseAllActivities(); }
    @Test
	public void test_sdt_test() { parseAllActivities(); }
	@Test
	public void test_select() { parseAllActivities(); }
	@Test
	public void test_self() { parseAllActivities(); }
	@Test
	public void test_sm() { parseAllActivities(); }
	@Test
	public void test_sync() { parseAllActivities(); }
	@Test
	public void test_syntax() { parseAllActivities(); }
	@Test
	public void test_trans() { parseAllActivities(); }
	@Test
	public void test_wim2() { parseAllActivities(); }
	@Test
	public void test_wim3() { parseAllActivities(); }
	@Test
	public void test_wims() { parseAllActivities(); }
	@Test
	public void test_wimx() { parseAllActivities(); }

	// these are last becuase they take so long
    @Test
	public void test_ooaofooa() { parseAllActivities(); }
    @Test
	public void test_ooaofgraphics() { parseAllActivities(); }
	@Test
	public void test_ex1() { parseAllActivities(); }
	@Test
	public void test_ex2() { parseAllActivities(); }
	@Test
	public void test_ex3() { parseAllActivities(); }
	@Test
	public void test_udt_assignment() { parseAllActivities(); }

    private static String workspace_path = ""; //$NON-NLS-1$
    private static String m_logfile_path = "";

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
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
        String methodName = name.getMethodName();
        m_domain_name = methodName.substring(5, methodName.length());
        if ( m_domain_name.equals( "ooaofooa" ) ) {
            ensureAvailableAndLoaded("org.xtuml.bp.core", m_domain_name, false, false);
        } else if ( m_domain_name.equals( "ooaofgraphics" ) ) {
        	ensureAvailableAndLoaded("org.xtuml.bp.ui.canvas", m_domain_name, false, false);
        } else {
            TestingUtilities.importTestingProjectIntoWorkspace(m_domain_name);
            project = getProjectHandle(m_domain_name);
            m_sys = getSystemModel(m_domain_name);
            modelRoot = Ooaofooa
					.getInstance("/" + m_domain_name + "/models/" + m_domain_name + "/"
							+ m_domain_name + "/" + m_domain_name + ".xtuml");
        }
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
        try {
            super.tearDown();

            OalParserTest_Generics.tearDownActionData();
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
		/* TODO - SKB 10/2013 - was going to start parsing message bodies, but
		 * some of our test models don't pass when we do.  We have to delay
		 * fixing this due to R4.1.0 deadline.  Opened issue dts0101009201 to 
		 * resolve this.
		 **
		parseAllProvidedOperations();
		parseAllRequiredOperations();*/
		parseAllProvidedSignals();
        parseAllRequiredSignals();
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
    private void parseAllProvidedOperations()
    {
        ProvidedOperation_c[] op_set = ProvidedOperation_c.ProvidedOperationInstances(modelRoot);
        for ( int i = 0; i < op_set.length; ++i )
        {
            if( op_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
            {
                String x = parseAction(op_set[i], op_set[i].getAction_semantics(),
                    op_set[i].getId(), Oalconstants_c.PROV_OPERATION_TYPE);
                if ( ! x.equals("") )
                {
                    assertEquals("Provided Operation " + op_set[i].getName(), "", x);
                }
            }
        }
    }
    private void parseAllProvidedSignals()
    {
        ProvidedSignal_c[] sig_set = ProvidedSignal_c.ProvidedSignalInstances(modelRoot);
        for ( int i = 0; i < sig_set.length; ++i )
        {
            if( sig_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
            {
                String x = parseAction(sig_set[i], sig_set[i].getAction_semantics(),
                    sig_set[i].getId(), Oalconstants_c.PROV_SIGNAL_TYPE);
                if ( ! x.equals("") )
                {
                    assertEquals("Provided Signal " + sig_set[i].getName(), "", x);
                }
            }
        }
    }
    private void parseAllRequiredOperations()
    {
        RequiredOperation_c[] op_set = RequiredOperation_c.RequiredOperationInstances(modelRoot);
        for ( int i = 0; i < op_set.length; ++i )
        {
            if( op_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
            {
                String x = parseAction(op_set[i], op_set[i].getAction_semantics(),
                    op_set[i].getId(), Oalconstants_c.REQ_OPERATION_TYPE);
                if ( ! x.equals("") )
                {
                    assertEquals("Required Operation " + op_set[i].getName(), "", x);
                }
            }
        }
    }
    private void parseAllRequiredSignals()
    {
        RequiredSignal_c[] sig_set = RequiredSignal_c.RequiredSignalInstances(modelRoot);
        for ( int i = 0; i < sig_set.length; ++i )
        {
            if( sig_set[i].getSuc_pars() == Parsestatus_c.parseSuccessful )
            {
                String x = parseAction(sig_set[i], sig_set[i].getAction_semantics(),
                    sig_set[i].getId(), Oalconstants_c.REQ_SIGNAL_TYPE);
                if ( ! x.equals("") )
                {
                    assertEquals("Required Signal " + sig_set[i].getName(), "", x);
                }
            }
        }
    }

    public class Package_by_id_c implements ClassQueryInterface_c {
        public boolean evaluate(Object candidate) {
            Package_c selected = (Package_c) candidate;
            return (selected.getPackage_id().equals(m_id));
        }
        public Package_by_id_c(UUID id) {
            m_id = id;
        }
        private UUID m_id;
    }
    
	private String parseAction(NonRootModelElement nrme, String stmts, UUID funcId, int funcType)
	{
		boolean foundPackage = false;
		if(nrme.getFirstParentPackage() != null) {
			foundPackage = true;
			nrme = nrme.getFirstParentPackage();
		} else {
		    // In this case, nrme is likely under a Component hierarchy
		    Component_c comp = nrme.getFirstParentComponent();
		    if ( comp != null ) {
		        Package_c pkg = Package_c.PackageInstance(modelRoot,
	                new Package_by_id_c(comp.Getpackageid()));
		        if (pkg != null) {
                    foundPackage = true;
		            nrme = pkg.getFirstParentPackage();
		        }
		    }
		}
		OalLexer lexer = new OalLexer(new StringReader(stmts));
		OalParser parser = new OalParser(modelRoot, lexer);
		if(foundPackage) {
			parser.m_oal_context = new Oal_validate(nrme);
		} else {
			fail("Unable to determine model context to parse");
		}
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
