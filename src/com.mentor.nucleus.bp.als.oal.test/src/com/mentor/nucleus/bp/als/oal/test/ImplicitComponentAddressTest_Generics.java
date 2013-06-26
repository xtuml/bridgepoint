package com.mentor.nucleus.bp.als.oal.test;

import java.io.StringReader;
import java.util.UUID;

import org.eclipse.jface.preference.IPreferenceStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import com.mentor.nucleus.bp.als.oal.OalLexer;
import com.mentor.nucleus.bp.als.oal.OalParser;
import com.mentor.nucleus.bp.als.oal.Oal_validate;
import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class ImplicitComponentAddressTest_Generics extends BaseTest {

	public static boolean configured = false;
    private static String m_workspace_path = ""; //$NON-NLS-1$
    private static String m_comp_pkg_name = "ComponentSyntaxTest"; //$NON-NLS-1$
    public ImplicitComponentAddressTest_Generics() {
    	super(null, null);
    }
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
	  if (configured) {
		return;
	  }
 	  configured = true;
      super.setUp();

      if (m_workspace_path.equals(""))//$NON-NLS-1$
      {
        m_workspace_path = System.getProperty("WORKSPACE_PATH");//$NON-NLS-1$
      }
      assertNotNull( m_workspace_path );
      if (m_logfile_path == null || m_logfile_path.equals(""))
      {
        m_logfile_path = System.getProperty("LOGFILE_PATH");
      }
      assertNotNull( m_logfile_path );

      TestingUtilities.importTestingProjectIntoWorkspace(m_comp_pkg_name);
      
      modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				m_comp_pkg_name, m_comp_pkg_name, true));
      
      m_sys = getSystemModel(m_comp_pkg_name);
      
      IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
      store.setValue(
        BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING, false);
	}
	
	public void testProvidedOperation() {
      String x = parseAction("::FnWithCRParm(CRParm:sender);",
           Oalconstants_c.PROV_OPERATION_TYPE, "ProvidedTestInterface::ProvOp");
      assertEquals("Unexpected error:", "", x);
	}
	public void testProvidedSignal() {
	  String x = parseAction("::FnWithCRParm(CRParm:sender);",
	         Oalconstants_c.PROV_SIGNAL_TYPE, "ProvidedTestInterface::ProvSig");
	  assertEquals("Unexpected error:", "", x);
	}
	public void testClassStateMachineAllSignals() {
      String x = parseAction("::FnWithCRParm(CRParm:sender);",
          Oalconstants_c.STATE_TYPE, "Class State Machine::Signals Only");
      assertEquals("Unexpected error:", "", x);
    }
	public void testRequiredOperation() {
	  String x = parseAction("::FnWithCRParm(CRParm:sender);",
	                          Oalconstants_c.REQ_OPERATION_TYPE,
	                                          "PreferenceTestInterface::ReqOp");
	  assertEquals("Unexpected error:", ":1:23-28: Sender keyword can only be used in an incoming Interface Operation.\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
	}
	public void testRequiredSignal() {
	  String x = parseAction("::FnWithCRParm(CRParm:sender);",
		                          Oalconstants_c.REQ_SIGNAL_TYPE,
		                                     "PreferenceTestInterface::ReqSig");
	  assertEquals("Unexpected error:", ":1:23-28: Sender keyword can only be used in an incoming Signal.\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
	}
	public void testClassStateMachineMixed() {
      String x = parseAction("::FnWithCRParm(CRParm:sender);",
	            Oalconstants_c.STATE_TYPE, "Class State Machine::Hybrid");
	  assertEquals("Unexpected error:", ":1:23-28: Sender keyword can only be used where there are signals assigned to all incoming transitions\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
	}
	public void testInstanceStateMachine() {
      String x = parseAction("self.CRA = sender;",
	                      Oalconstants_c.STATE_TYPE,
	                           "Instance State Machine::Preference Test State");
	  assertEquals("Unexpected error:", ":1:12-17: Sender keyword is valid only where a message is serviced directly\nline 1:19: unexpected token: null\nline 1:19: expecting Semicolon, found 'null'\n", x);
	}
	public void testClassStateMachineTransitionWithNoEvent() {
	      String x = parseAction("::FnWithCRParm(CRParm:sender);",
		            Oalconstants_c.TRANSITION_TYPE, "Class State Machine::No Event");
		  assertEquals("Unexpected error:", ":1:23-28: Sender keyword can only be used when a signal is assigned to this transition\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
	}
	public void testClassStateMachineTransitionWithNonSignal() {
	      String x = parseAction("::FnWithCRParm(CRParm:sender);",
		            Oalconstants_c.TRANSITION_TYPE, "Class State Machine::Ev");
		  assertEquals("Unexpected error:", ":1:23-28: Sender keyword can only be used when a signal is assigned to this transition\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
	}
	public void testClassStateMachineTransitionWithSignal() {
	      String x = parseAction("::FnWithCRParm(CRParm:sender);",
		            Oalconstants_c.TRANSITION_TYPE, "Class State Machine::Sig");
		  assertEquals("Unexpected error:", "", x);
	}
	public void testBridge() {
      String x = parseAction("::FnWithCRParm(CRParm:sender);",
		                      Oalconstants_c.BRIDGE_TYPE,
		                                    "ComponentTest::BrNoParmsNoReturn");
	  assertEquals("Unexpected error:", ":1:23-28: Sender keyword is valid only where a message is serviced directly\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
	}
	public void testFunction() {
      String x = parseAction("::FnWithCRParm(CRParm:sender);",
		                     Oalconstants_c.FUNCTION_TYPE, "FnNoParmsNoReturn");
      assertEquals("Unexpected error:", ":1:23-28: Sender keyword is valid only where a message is serviced directly\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
    }
	public void testClassBasedOperation() {
      String x = parseAction("::FnWithCRParm(CRParm:sender);",
                   Oalconstants_c.OPERATION_TYPE, "opClassBasedPreferenceTest");
	  assertEquals("Unexpected error:", ":1:23-28: Sender keyword is valid only where a message is serviced directly\nline 1:30: expecting TOK_RPAREN, found ';'\nline 1:31: expecting Semicolon, found 'null'\n", x);
	}
	public void testOperation() {
	  String x = parseAction("self.CRA = sender;",
	                   Oalconstants_c.OPERATION_TYPE, "opPreferenceTest");
	  assertEquals("Unexpected error:", ":1:12-17: Sender keyword is valid only where a message is serviced directly\nline 1:19: unexpected token: null\nline 1:19: expecting Semicolon, found 'null'\n", x);
	}
	private String parseAction(String stmts, int opType, String actionName) {
        String [] names = actionName.split("::");
		OalLexer lexer = new OalLexer(new StringReader(stmts));
		OalParser parser = new OalParser(modelRoot, lexer);
		UUID actID = Gd_c.Null_unique_id();
		class ComponentNameTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				Component_c selected = (Component_c) candidate;
				return selected.getName().equals("Good Syntax");
			}
		}
		Component_c comp = Component_c.ComponentInstance(modelRoot,
                                                       new ComponentNameTest());
		assertTrue("Test component not found: Good Syntax", comp != null);
		parser.m_oal_context = new Oal_validate(comp);
		class ModelClassNameTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				ModelClass_c selected = (ModelClass_c) candidate;
				return selected.getName().equals("Test Component Syntax");
			}
		}
		ModelClass_c modelClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1405(m_sys)),
				new ModelClassNameTest());
		assertTrue("Test class not found: Test Component Syntax",
                                                            modelClass != null);
		if (opType == Oalconstants_c.OPERATION_TYPE) {
			actID = handleOpTest(modelClass, stmts, names);
		}
		else if (opType == Oalconstants_c.STATE_TYPE) {
			actID = handleStateTest(modelClass, stmts, names);
		}
		else if (opType == Oalconstants_c.TRANSITION_TYPE) {
			actID = handleTransitionTest(modelClass, stmts, names);
		}
		else if (opType == Oalconstants_c.PROV_OPERATION_TYPE ||
				         opType == Oalconstants_c.REQ_OPERATION_TYPE ||
				               opType == Oalconstants_c.PROV_SIGNAL_TYPE ||
				                     opType == Oalconstants_c.REQ_SIGNAL_TYPE) {
			actID = handleMessageTest(comp, stmts, names, opType);
		}
		else if (opType == Oalconstants_c.BRIDGE_TYPE) {
			actID = handleBridgeTest(comp, stmts, names);
		}
		else if (opType == Oalconstants_c.FUNCTION_TYPE) {
			actID = handleFunctionTest(comp, stmts, names);
		}
		assertNotSame("Invalid activity ID", actID, Gd_c.Null_unique_id());
		try {
			parser.action(actID, opType);

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
		}
		return parser.m_output;
	}
    UUID handleStateTest(ModelClass_c modelClass, String stmts, String [] names) {
        StateMachineState_c [] states =
       	 StateMachineState_c.getManySM_STATEsOnR501(
       	    		StateMachine_c.getOneSM_SMOnR517(
       	    		   ClassStateMachine_c.getOneSM_ASMOnR519(modelClass)));
         if (names[0].equals("Instance State Machine")) {
       	states = StateMachineState_c.getManySM_STATEsOnR501(
                                 StateMachine_c.getOneSM_SMOnR517(
                       InstanceStateMachine_c.getOneSM_ISMOnR518(modelClass)));
         }
         StateMachineState_c testState = null;
         for (int i = 0; i < states.length; i++) {
           if (states[i].getName().equals(names[1])) {
           	testState = states[i];
           	break;
           }
         }
         assertNotNull("Test state not found", testState);
         Action_c act = Action_c.getOneSM_ACTOnR514(
       		         ActionHome_c.getOneSM_AHOnR513(
       				     MooreActionHome_c.getOneSM_MOAHOnR511(testState)));
         act.setAction_semantics_internal(stmts);
         return act.getAct_id();
    }
    UUID handleTransitionTest(ModelClass_c modelClass, String stmts, String [] names) {
        Transition_c[] trans =
       	 Transition_c.getManySM_TXNsOnR505(
       	    		StateMachine_c.getOneSM_SMOnR517(
       	    		   ClassStateMachine_c.getOneSM_ASMOnR519(modelClass)));
         if (names[0].equals("Instance State Machine")) {
       	   trans = Transition_c.getManySM_TXNsOnR505(
                                 StateMachine_c.getOneSM_SMOnR517(
                       InstanceStateMachine_c.getOneSM_ISMOnR518(modelClass)));
         }
         Transition_c testTran = null;
         for (int i = 0; i < trans.length; i++) {
           if (trans[i].getName().contains(names[1])) {
           	testTran = trans[i];
           	break;
           }
         }
         assertNotNull("Test transition not found", testTran);
         Action_c act = Action_c.getOneSM_ACTOnR514(
       		         ActionHome_c.getOneSM_AHOnR513(
       				     TransitionActionHome_c.getOneSM_TAHOnR530(testTran)));
         act.setAction_semantics_internal(stmts);
         return act.getAct_id();
    }
    UUID handleOpTest(ModelClass_c modelClass, String stmts, String [] names) {
      Operation_c op = null;
	  Operation_c [] ops = Operation_c.getManyO_TFRsOnR115(modelClass);
	  for (int i=0; i < ops.length; i++) {
		  if (ops[i].getName().equals(names[0])) {
			  op = ops[i];
			  break;
		  }
	  }
	  assertNotNull("Test operation not found", op);
	  op.setAction_semantics_internal(stmts);
	  return op.getTfr_id();
    }
    UUID handleMessageTest(Component_c comp, String stmts, String [] names,
    		                                                       int opType) {
		  InterfaceReference_c selectedIfRef = null;
		  Interface_c selectedIf = null;
		  InterfaceReference_c [] ifRefs = InterfaceReference_c.
		                  getManyC_IRsOnR4016(Port_c.getManyC_POsOnR4010(comp));
		  for (int i=0; i < ifRefs.length; i++) {
			  Interface_c iface = Interface_c.getOneC_IOnR4012(ifRefs[i]);
			  if (iface.getName().equals(names[0])) {
				  selectedIfRef = ifRefs[i];
				  selectedIf = iface;
				  break;
			  }
		  }
		  assertNotNull("Interface not found", selectedIfRef);
		  ExecutableProperty_c selectedEp = null;
		  ExecutableProperty_c [] eps = ExecutableProperty_c.
		                                        getManyC_EPsOnR4003(selectedIf);
          for (int i=0; i < eps.length; i++) {
        	InterfaceOperation_c ifOp = InterfaceOperation_c.getOneC_IOOnR4004(eps[i]);
        	InterfaceSignal_c ifSig = InterfaceSignal_c.getOneC_ASOnR4004(eps[i]);
            if ((ifOp != null && ifOp.getName().equals(names[1])) ||
                            ifSig != null && ifSig.getName().equals(names[i])) {
              selectedEp = eps[i];
              break;
            }
          }
          assertNotNull("Operation not found", selectedEp);
          if (opType == Oalconstants_c.PROV_OPERATION_TYPE) {
		    ProvidedOperation_c po = ProvidedOperation_c.getOneSPR_POOnR4503(
				 ProvidedExecutableProperty_c.getOneSPR_PEPOnR4501(selectedEp));
		    po.setAction_semantics_internal(stmts);
		    return po.getId();
          }
          else if (opType == Oalconstants_c.REQ_OPERATION_TYPE) {
  		    RequiredOperation_c ro = RequiredOperation_c.getOneSPR_ROOnR4502(
  				 RequiredExecutableProperty_c.getOneSPR_REPOnR4500(selectedEp));
  			ro.setAction_semantics_internal(stmts);
  			return ro.getId();
          }
          else if (opType == Oalconstants_c.PROV_SIGNAL_TYPE) {
   		    ProvidedSignal_c ps = ProvidedSignal_c.getOneSPR_PSOnR4503(
                 ProvidedExecutableProperty_c.getOneSPR_PEPOnR4501(selectedEp));
            ps.setAction_semantics_internal(stmts);
            return ps.getId();
          }
          else { // opType == Oalconstants_c.REQ_SIGNAL_TYPE
    		RequiredSignal_c ro = RequiredSignal_c.getOneSPR_RSOnR4502(
    					 RequiredExecutableProperty_c.getOneSPR_REPOnR4500(selectedEp));
    		ro.setAction_semantics_internal(stmts);
    		return ro.getId();
          }
    }
    UUID handleBridgeTest(Component_c comp, String stmts, String [] names) {
    	ExternalEntity_c eeUT = null;
		ExternalEntity_c[] ees = ExternalEntity_c
				.getManyS_EEsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8003(comp))));
    	for (int i=0; i < ees.length; i++) {
    		if (ees[i].getName().equals(names[0])) {
    			eeUT = ees[i];
    			break;
    		}
    	}
    	assertNotNull("EE not found", eeUT);
    	Bridge_c brgUT = null;
    	Bridge_c [] brgs = Bridge_c.getManyS_BRGsOnR19(eeUT);
    	for(int i=0; i < brgs.length; i++) {
    	  if (brgs[i].getName().equals(names[1])) {
    		  brgUT = brgs[i];
    		  break;
    	  }
    	}
    	assertNotNull("Bridge not found", brgUT);
    	brgUT.setAction_semantics_internal(stmts);
    	return brgUT.getBrg_id();
    }
    UUID handleFunctionTest(Component_c comp, String stmts, String [] names) {
    	Function_c fnUT = null;
		Function_c[] fns = Function_c
				.getManyS_SYNCsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8003(comp))));
    	for (int i=0; i < fns.length; i++) {
    	  if (fns[i].getName().equals(names[0])) {
    		  fnUT = fns[i];
    		  break;
    	  }
    	}
    	assertNotNull("Function not found", fnUT);
    	fnUT.setAction_semantics_internal(stmts);
    	return fnUT.getSync_id();
    }
}
