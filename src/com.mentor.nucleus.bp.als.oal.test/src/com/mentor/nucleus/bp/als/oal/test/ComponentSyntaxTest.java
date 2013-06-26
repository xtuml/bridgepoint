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
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Oalconstants_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class ComponentSyntaxTest extends BaseTest {

    private static String m_workspace_path = ""; //$NON-NLS-1$
    private static String m_comp_pkg_name = "ComponentSyntaxTest"; //$NON-NLS-1$
    public ComponentSyntaxTest() {
    	super("Models", null);
    }
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
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

        ensureAvailableAndLoaded("Models", m_comp_pkg_name, false, false, "Component Package");
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(
          BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING, true);
	}
	
	public void testGoodSyntax() {
		// Most good syntax is checked in ParseAllInDomain,
		// This is here to give early heads up if something
		// is badly wrong witht he configuration.
		String x = parseAction("self.CRA = sender;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", "", x);
	}
	public void testexplicitSendSyntaxInterfaceNameNoInterface() {
		String x = parseAction("send NoInterface::testSig() to sender;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:19-25: Internal error, cannot find interface or port with name ->NoInterface::testSig<-\n", x);
	}
	public void testexplicitSendSyntaxMultipleInterfaces() {
		String x = parseAction("send ComponentTestMultipleRefsInterface::testSig() to sender;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:42-48: Ambiguous interface or port reference found ->ComponentTestMultipleRefsInterface::testSig<-\n", x);
	}
	public void testexplicitSendSyntaxPortNameNoPort() {
		String x = parseAction("send NoPort::testSig() to sender;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:14-20: Internal error, cannot find interface or port with name ->NoPort::testSig<-\n", x);
	}
	public void testexplicitSendSyntaxMultiplePorts() {
		String x = parseAction("send BadTestPort::testSig() to sender;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:19-25: Found port with duplicate name ->BadTestPort<-\n", x);
	}
	public void testimplicitSendSyntaxInterfaceNameNoInterface() {
		String x = parseAction("NoInterface::testSig();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:1-11: Cannot find specified class,  external entity or interface ->NoInterface<-.\nline 1:14: expecting Semicolon, found 'testSig'\n", x);
	}
	public void testimplicitSendSyntaxMultipleInterfaces() {
		String x = parseAction("ComponentTestMultipleRefsInterface::testSig();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:37-43: Ambiguous interface or port reference found ->ComponentTestMultipleRefsInterface::testSig<-\nline 1:45: expecting Semicolon, found ')'\n", x);
	}
	public void testimplicitSendSyntaxPortNameNoPort() {
		String x = parseAction("NoPort::testSig();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:1-6: Cannot find specified class,  external entity or interface ->NoPort<-.\nline 1:9: expecting Semicolon, found 'testSig'\n", x);
	}
	public void testimplicitSendSyntaxMultiplePorts() {
		String x = parseAction("BadTestPort::testSig();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:14-20: Found port with duplicate name ->BadTestPort<-\nline 1:22: expecting Semicolon, found ')'\n", x);
	}
	public void testexplicitGoodInterfaceNoSignal() {
		String x = parseAction("send ComponentTestInterface::NoSuchSig();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:30-38: Cannot find message with name ->ComponentTestInterface::NoSuchSig<-\n", x);
	}
	public void testexplicitGoodInterfaceNoOperation() {
		String x = parseAction("send ComponentTestInterface::NoSuchOp();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:30-37: Cannot find message with name ->ComponentTestInterface::NoSuchOp<-\n", x);
	}
	public void testexplicitGoodInterfaceMultipleSignals() {
		String x = parseAction("send ComponentTestInterface::SigMultipleDecl();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:30-44: Ambiguous message reference found ->ComponentTestInterface::SigMultipleDecl<-\n", x);
	}
	public void testexplicitGoodInterfaceMultipleOperations() {
		String x = parseAction("send ComponentTestInterface::OpMultipleDecl();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:30-43: Ambiguous message reference found ->ComponentTestInterface::OpMultipleDecl<-\n", x);
	}
	public void testimplicitGoodInterfaceNoSignal() {
		String x = parseAction("ComponentTestInterface::NoSuchSig();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:25-33: Cannot find bridge, operation or message ->ComponentTestInterface::NoSuchSig<-\nline 1:35: expecting Semicolon, found ')'\n", x);
	}
	public void testimplicitGoodInterfaceNoOperation() {
		String x = parseAction("ComponentTestInterface::NoSuchOp();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:25-32: Cannot find bridge, operation or message ->ComponentTestInterface::NoSuchOp<-\nline 1:34: expecting Semicolon, found ')'\n", x);
	}
	public void testimplicitGoodInterfaceMultipleSignals() {
		String x = parseAction("ComponentTestInterface::SigMultipleDecl();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:25-39: Ambiguous message reference found ->ComponentTestInterface::SigMultipleDecl<-\nline 1:41: expecting Semicolon, found ')'\n", x);
	}
	public void testimplicitGoodInterfaceMultipleOperations() {
		String x = parseAction("ComponentTestInterface::OpMultipleDecl();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:25-38: Ambiguous message reference found ->ComponentTestInterface::OpMultipleDecl<-\nline 1:40: expecting Semicolon, found ')'\n", x);
	}
	public void testexplicitBadComponentRefAttributeValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to self.testReal;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:58-65: ->to<- variable must be of type component_ref\nline 1:67: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefParameterValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to param.testParam;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:59-67: ->to<- variable must be of type component_ref\nline 1:69: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefEventDataValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to rcvd_evt.TimestampDI;", Oalconstants_c.STATE_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:62-72: ->to<- variable must be of type component_ref\nline 1:74: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefBridgeValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to CT::BrWithBoolReturn();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:74-74: ->to<- variable must be of type component_ref\nline 1:76: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefFunctionValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to ::FnWithUIDReturn();", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:71-71: ->to<- variable must be of type component_ref\nline 1:73: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefOperationValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to self;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:53-56: ->to<- variable must be of type component_ref\nline 1:58: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefStructuredDataValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to self.CRA_SDT.Name;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:66-69: ->to<- variable must be of type component_ref\nline 1:71: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefArrayValue() {
		String x = parseAction("send ComponentTestInterface::OpNoParmsNoReturn() to self.testDateArray[1];", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:73-73: ->to<- variable must be of type component_ref\nline 1:75: expecting Semicolon, found 'null'\n", x);
	}
	public void testexplicitBadComponentRefLocalVariableValue() {
		String x = parseAction("y = 9; send ComponentTestInterface::OpNoParmsNoReturn() to y;", Oalconstants_c.OPERATION_TYPE, null, null);
		assertEquals("Unexpected error:", ":1:60-60: ->to<- variable must be of type component_ref\nline 1:62: expecting Semicolon, found 'null'\n", x);
	}
	public void testBadRequiredInterfaceOperationParameterName(){
		String x = parseAction("number = param.DATA;", Oalconstants_c.REQ_OPERATION_TYPE, "testOp", "Port3");
		String errMsg = ":1:16-19: Parameter ->DATA<- is not associated with required operation ->testOp<-\nline 1:21: unexpected token: null\nline 1:21: expecting Semicolon, found 'null'\n";
		assertEquals(errMsg, x);
	}
	public void testBadProvidedInterfaceOperationParameterName(){
		String x = parseAction("number = param.DATA;", Oalconstants_c.PROV_OPERATION_TYPE, "testOp", "Port2");
		String errMsg = ":1:16-19: Parameter ->DATA<- is not associated with provided operation ->testOp<-\nline 1:21: unexpected token: null\nline 1:21: expecting Semicolon, found 'null'\n";
		assertEquals(errMsg, x);
	}
	public void testBadRequiredInterfaceSignalParameterName(){
		String x = parseAction("number = param.DATA;", Oalconstants_c.REQ_SIGNAL_TYPE, "testSig", "Port3");
		String errMsg = ":1:16-19: Parameter ->DATA<- is not associated with required signal ->testSig<-\nline 1:21: unexpected token: null\nline 1:21: expecting Semicolon, found 'null'\n";
		assertEquals(errMsg, x);
	}
	public void testBadProvidedInterfaceSignalParameterName(){
		String x = parseAction("number = param.DATA;", Oalconstants_c.PROV_SIGNAL_TYPE, "testSig", "Port2");
		String errMsg = ":1:16-19: Parameter ->DATA<- is not associated with provided signal ->testSig<-\nline 1:21: unexpected token: null\nline 1:21: expecting Semicolon, found 'null'\n";
		assertEquals(errMsg, x);
	}
	public void testCorrectRequiredInterfaceOperationParameterName(){
		String x = parseAction("number = param.data;", Oalconstants_c.REQ_OPERATION_TYPE, "testOp", "Port3");
		String errMsg = "";
		assertEquals(errMsg, x);
	}
	public void testCorrectProvidedInterfaceOperationParameterName(){
		String x = parseAction("number = param.data;", Oalconstants_c.PROV_OPERATION_TYPE, "testOp", "Port2");
		String errMsg = "";
		assertEquals(errMsg, x);
	}
	public void testCorrectRequiredInterfaceSignalParameterName(){
		String x = parseAction("number = param.data;", Oalconstants_c.REQ_SIGNAL_TYPE, "testSig", "Port3");
		String errMsg = "";
		assertEquals(errMsg, x);
	}
	public void testCorrectProvidedInterfaceSignalParameterName(){
		String x = parseAction("number = param.data;", Oalconstants_c.PROV_SIGNAL_TYPE, "testSig", "Port2");
		String errMsg = "";
		assertEquals(errMsg, x);
	}
	
	
	
	public void testOutboundMessageProvidedPortToProvider(){
		String x = parseAction("a  = 1;", Oalconstants_c.PROV_OPERATION_TYPE, "toProvider", "providedOutboundMessages");
		String errMsg = ":1:8-8: Return value required by interface operation\n";
		assertEquals(errMsg, x);
		
	}
	public void testOutboundMessageProvidedPortToProviderEmpty(){
		String x = parseAction("", Oalconstants_c.PROV_OPERATION_TYPE, "toProvider_empty", "providedOutboundMessages");
		String errMsg = ":1:1-1: Return value required by interface operation\n";
		assertEquals(errMsg, x);
	
	}
	public void testOutboundMessageProvidedPortFromProvider(){
		String x = parseAction("a = 1;", Oalconstants_c.PROV_OPERATION_TYPE, "fromProvider", "providedOutboundMessages");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	public void testOutboundMessageProvidedPortFromProviderEmpty(){
		String x = parseAction("", Oalconstants_c.PROV_OPERATION_TYPE, "fromProvider_empty", "providedOutboundMessages");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	public void testOutboundMessageRequiredPortToProvider(){
		String x = parseAction("b = 1;", Oalconstants_c.REQ_OPERATION_TYPE, "toProvider", "requiredOutboundMessages");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	public void testOutboundMessageRequiredPortToProviderEmpty(){
		String x = parseAction("", Oalconstants_c.REQ_OPERATION_TYPE, "toProvider_empty", "requiredOutboundMessages");
		String errMsg = "";
		assertEquals(errMsg, x);
		
	}
	public void testOutboundMessageRequiredPortFromProvider(){
		String x = parseAction("b = 1;", Oalconstants_c.REQ_OPERATION_TYPE, "fromProvider", "requiredOutboundMessages");
		String errMsg = ":1:7-7: Return value required by interface operation\n";
		assertEquals(errMsg, x);
	
	}
	public void testOutboundMessageRequiredPortFromProviderEmpty(){
		String x = parseAction("", Oalconstants_c.REQ_OPERATION_TYPE, "fromProvider_empty", "requiredOutboundMessages");
		String errMsg = ":1:1-1: Return value required by interface operation\n";
		assertEquals(errMsg, x);

	}
	
	
	private String parseAction(String stmts, int opType, String activityName, String portName) {
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
		Domain_c dom = Domain_c.getOneS_DOMOnR4204(
				                DomainAsComponent_c.getManyCN_DCsOnR4204(comp));
		parser.m_oal_context = new Oal_validate(dom);
		class ModelClassNameTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				ModelClass_c selected = (ModelClass_c) candidate;
				return selected.getName().equals("Test Component Syntax");
			}
		}
		ModelClass_c modelClass = ModelClass_c.getOneO_OBJOnR2(
				     Subsystem_c.getOneS_SSOnR1(dom), new ModelClassNameTest());
		assertTrue("Test class not found: Test Component Syntax",
				                                            modelClass != null);
		if (opType == Oalconstants_c.OPERATION_TYPE) {
		  Operation_c [] ops = Operation_c.getManyO_TFRsOnR115(modelClass);
		  assertTrue("Test operation not found", ops.length >= 4);
		  assertTrue("Test operation not in expected position",
			                     ops[3].getName().equals("opWithParmNoReturn"));
		  ops[3].setAction_semantics_internal(stmts);
		  actID = ops[3].getTfr_id();
		}
		else if (opType == Oalconstants_c.STATE_TYPE) {
          StateMachineState_c [] states =
        	 StateMachineState_c.getManySM_STATEsOnR501(
        	    		StateMachine_c.getOneSM_SMOnR517(
        	    		   ClassStateMachine_c.getOneSM_ASMOnR519(modelClass)));
          assertTrue("Test state not found", states.length >= 3);
          assertTrue("Test state not found in expected position",
        		                 states[2].getName().equals("Test Bad Syntax"));
          Action_c act = Action_c.getOneSM_ACTOnR514(
        		         ActionHome_c.getOneSM_AHOnR513(
        				     MooreActionHome_c.getOneSM_MOAHOnR511(states[2])));
          act.setAction_semantics_internal(stmts);
          actID = act.getAct_id();
		}else if (opType == Oalconstants_c.REQ_OPERATION_TYPE){
			actID = getInterfaceOperationInstances(comp, portName, true, activityName);
		} else if (opType == Oalconstants_c.PROV_OPERATION_TYPE) {
			actID = getInterfaceOperationInstances(comp, portName, false,activityName);
		} else if (opType == Oalconstants_c.REQ_SIGNAL_TYPE){
			actID = getInterfaceSginalInstances(comp, portName, true,activityName);
		}else if (opType == Oalconstants_c.PROV_SIGNAL_TYPE){
			actID = getInterfaceSginalInstances(comp, portName, false,activityName);
		}
		
		
			
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
	
	private UUID getInterfaceOperationInstances(Component_c comp,
			String portName, boolean Required, String operationName) {

		Port_c[] ports = Port_c.getManyC_POsOnR4010(comp);
		for (int i = 0; i < ports.length; ++i) {
			if (ports[i].getName().equalsIgnoreCase(portName)) {
				// BirdgePoint support only single interface reference for a
				// port
				// so select One interface reference only
				InterfaceReference_c intRef = InterfaceReference_c
						.getOneC_IROnR4016(ports[i]);

				Requirement_c req = Requirement_c.getOneC_ROnR4009(intRef);
				Provision_c prov = Provision_c.getOneC_POnR4009(intRef);
				if (req != null && Required) { // Required Interface
					RequiredExecutableProperty_c[] REPs = RequiredExecutableProperty_c
							.getManySPR_REPsOnR4500(req);
					for (int j = 0; j < REPs.length; ++j) {

						class ReqOperation_test1_c implements
								ClassQueryInterface_c {
							ReqOperation_test1_c(String p) {
								m_p = p;
							}

							private String m_p;
							public boolean evaluate(Object inst) {
								RequiredOperation_c selected = (RequiredOperation_c) inst;
								return selected.getName().equals(m_p);
							}
						}

						RequiredOperation_c testOpr = RequiredOperation_c
								.getOneSPR_ROOnR4502(REPs,
										new ReqOperation_test1_c(operationName));
						if (testOpr != null) {
							return testOpr.getId();
						}
					}
					fail("Missing Interface Operation " + operationName);//$NON-NLS-1$
					return null;
				} else if (prov != null && !Required) { // Provided Interface
					ProvidedExecutableProperty_c[] PEPs = ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(prov);
					for (int j = 0; j < PEPs.length; ++j) {

						class ProvOperation_test1_c implements
								ClassQueryInterface_c {
							ProvOperation_test1_c(String p) {
								m_p = p;
							}
							private String m_p;
							public boolean evaluate(Object inst) {
								ProvidedOperation_c selected = (ProvidedOperation_c) inst;

								return selected.getName().equals(m_p);
							}
						}

						ProvidedOperation_c testOpr = ProvidedOperation_c
						.getOneSPR_POOnR4503(PEPs, new ProvOperation_test1_c(
								operationName));

						if (testOpr != null) {
							return testOpr.getId();
						}
					}
					fail("Missing Interface Operation " + operationName);//$NON-NLS-1$
					return null;
				} else {
					fail("Port" + portName + "is defined as" + (Required ? "Provided" : "Required") + "interface.");//$NON-NLS-1$
					return null;
				}
			}
		}
		fail("Can not find port " + portName);//$NON-NLS-1$
		return null;
	}
	
	private UUID getInterfaceSginalInstances(Component_c comp,
			String portName, boolean Required, String signalName) {

		Port_c[] ports = Port_c.getManyC_POsOnR4010(comp);
		for (int i = 0; i < ports.length; ++i) {
			if (ports[i].getName().equalsIgnoreCase(portName)) {
				// BirdgePoint support only single interface reference for a
				// port
				// so select One interface reference only
				InterfaceReference_c intRef = InterfaceReference_c
						.getOneC_IROnR4016(ports[i]);

				Requirement_c req = Requirement_c.getOneC_ROnR4009(intRef);
				Provision_c prov = Provision_c.getOneC_POnR4009(intRef);
				if (req != null && Required) { // Required Interface
					RequiredExecutableProperty_c[] REPs = RequiredExecutableProperty_c
							.getManySPR_REPsOnR4500(req);
					for (int j = 0; j < REPs.length; ++j) {

						class ReqSginal_test1_c implements
								ClassQueryInterface_c {
							ReqSginal_test1_c(String p) {
								m_p = p;
							}

							private String m_p;
							public boolean evaluate(Object inst) {
								RequiredSignal_c selected = (RequiredSignal_c) inst;
								return selected.getName().equals(m_p);
							}
						}

						RequiredSignal_c testSig = RequiredSignal_c
								.getOneSPR_RSOnR4502(REPs,
										new ReqSginal_test1_c(signalName));
						if (testSig != null) {
							return testSig.getId();
						}
					}
					fail("Missing Interface Signal " + signalName);//$NON-NLS-1$
					return null;
				} else if (prov != null && !Required) { // Provided Interface
					ProvidedExecutableProperty_c[] PEPs = ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(prov);
					for (int j = 0; j < PEPs.length; ++j) {

						class ProvSignal_test1_c implements
								ClassQueryInterface_c {
							ProvSignal_test1_c(String p) {
								m_p = p;
							}
							private String m_p;
							public boolean evaluate(Object inst) {
								ProvidedSignal_c selected = (ProvidedSignal_c) inst;

								return selected.getName().equals(m_p);
							}
						}

						ProvidedSignal_c testSig = ProvidedSignal_c
						.getOneSPR_PSOnR4503(PEPs, new ProvSignal_test1_c(
								signalName));

						if (testSig != null) {
							return testSig.getId();
						}
					}
					fail("Missing Interface Signal " + signalName);//$NON-NLS-1$
					return null;
				} else {
					fail("Port" + portName + "is defined as" + (Required ? "Provided" : "Required") + "interface.");//$NON-NLS-1$
					return null;
				}
			}
		}
		fail("Can not find port " + portName);//$NON-NLS-1$
		return null;
	}
}
