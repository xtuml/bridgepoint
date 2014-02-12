//=====================================================================
//
//File:      $RCSfile: TestVerifierRunTimeErrorMsgs.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/10 04:28:42 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.debug.ui.test.runtimemsg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.debug.ui.actions.ExecuteAction;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class TestVerifierRunTimeErrorMsgs extends BaseTest {
    public static boolean generateResults = false;
    public static boolean useDrawResults = true;
    public static String  launchComponentName="";
    public static String projectName = "";
    String test_id = "";

    protected String getResultName() {
        return getClass().getSimpleName() + "_" + test_id;
    }



    public TestVerifierRunTimeErrorMsgs(String subTypeClassName, String subTypeArg0) {
        super(null, subTypeArg0);
    }

    protected String getTestId(String src, String dest, String count) {
        return "test_" + count;
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * "A" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "A" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @return A model element used in the test as specified by the test matrix
     */
    List<NonRootModelElement> selectA(String element) {
    	List<NonRootModelElement>  functions = new ArrayList<NonRootModelElement>();
    	List<NonRootModelElement>  pos = new ArrayList<NonRootModelElement>();
    	List<NonRootModelElement>  pss = new ArrayList<NonRootModelElement>();
    	List<NonRootModelElement>  ros = new ArrayList<NonRootModelElement>();
    	List<NonRootModelElement>  rss = new ArrayList<NonRootModelElement>();
		Ooaofooa[] instancesUnderSystem = Ooaofooa.getInstancesUnderSystem(m_sys.getName());
		for(Ooaofooa root : instancesUnderSystem) {
			InstanceList list = root.getInstanceList(Function_c.class);
			if(!list.isEmpty()) {
				functions.addAll(list);
			}
			list = root.getInstanceList(ProvidedOperation_c.class);
			if(!list.isEmpty()) {
				pos.addAll(list);
			}
			list = root.getInstanceList(ProvidedSignal_c.class);
			if(!list.isEmpty()) {
				pss.addAll(list);
			}
			list = root.getInstanceList(RequiredOperation_c.class);
			if(!list.isEmpty()) {
				ros.addAll(list);
			}
			list = root.getInstanceList(RequiredSignal_c.class);
			if(!list.isEmpty()) {
				rss.addAll(list);
			}
		}
    	if (element.equalsIgnoreCase("A1")) {
    		return functions;
        }  else if (element.equalsIgnoreCase("A2")) {
        	return functions;
        } else if (element.equalsIgnoreCase("A3")) {
        	return functions;
        } else if (element.equalsIgnoreCase("A4")) {
        	return functions;
        } else if (element.equalsIgnoreCase("A5")) {
        	return functions;
        } else if (element.equalsIgnoreCase("A6")) {
        	return functions;
        } else if (element.equalsIgnoreCase("A7")) {
        	return pos;
        } else if (element.equalsIgnoreCase("A8")) {
        	return pss;
        } else if (element.equalsIgnoreCase("A9")) {
        	return ros;
        }else if (element.equalsIgnoreCase("A10")) {
        	return rss;
        }
        else        fail("An instance with degree of freedom type \"A\" was not found.  Instance Name: " + element + ".");
        return null;
    }

    /**
     * "CN" is one of the degrees of freedom as specified in this issues
     * test matrix.
     * This routine gets the "CN" instance from the given name.
     * 
     * @param element The degree of freedom instance to retrieve
     * @param src 
     * @param string 
     * @return A model element used in the test as specified by the test matrix
     */
	NonRootModelElement selectCN(String element, String stringOfA,
			List<NonRootModelElement> ResultOfA) {
		NonRootModelElement nrme = null;
		String elementName = stringOfA + element;
		if (stringOfA.equals("A1")
			||stringOfA.equals("A2")
			||stringOfA.equals("A3")
			||stringOfA.equals("A4")
			||stringOfA.equals("A6")
		   )
		{
			elementName="Execute"+elementName;
		   
		}
		
		
		
		for (Iterator<NonRootModelElement> iterator = ResultOfA.iterator(); iterator.hasNext();) {
			NonRootModelElement nonRootModelElement = (NonRootModelElement) iterator
					.next();
			if (nonRootModelElement.getName().equalsIgnoreCase(elementName)) {
				if ((nonRootModelElement instanceof ProvidedSignal_c || nonRootModelElement instanceof ProvidedOperation_c)
						&& (element.equals("C1N1CSM") || element.equals("C1N1"))) {
					if(nonRootModelElement instanceof ProvidedSignal_c) {
						Component_c comp = Component_c
								.getOneC_COnR4010(Port_c.getManyC_POsOnR4016(InterfaceReference_c.getManyC_IRsOnR4009(Provision_c.getManyC_PsOnR4501(ProvidedExecutableProperty_c
												.getManySPR_PEPsOnR4503((ProvidedSignal_c) nonRootModelElement)))));
						if(!comp.getName().equals("WrapperComponent")) {
							continue;
						}
					}
					if(nonRootModelElement instanceof ProvidedOperation_c) {
						Component_c comp = Component_c
								.getOneC_COnR4010(Port_c.getManyC_POsOnR4016(InterfaceReference_c.getManyC_IRsOnR4009(Provision_c.getManyC_PsOnR4501(ProvidedExecutableProperty_c
												.getManySPR_PEPsOnR4503((ProvidedOperation_c) nonRootModelElement)))));
						if(!comp.getName().equals("WrapperComponent")) {
							continue;
						}
						
					}
				}
				if ((nonRootModelElement instanceof RequiredSignal_c || nonRootModelElement instanceof RequiredOperation_c)
						&& (element.equals("C1N1"))) {
					if(nonRootModelElement instanceof RequiredSignal_c) {
						Component_c comp = Component_c
								.getOneC_COnR4010(Port_c.getManyC_POsOnR4016(InterfaceReference_c.getManyC_IRsOnR4009(Requirement_c.getManyC_RsOnR4500(RequiredExecutableProperty_c
												.getManySPR_REPsOnR4502((RequiredSignal_c) nonRootModelElement)))));
						if(!comp.getName().equals("WrapperComponent")) {
							continue;
						}
					}
					if(nonRootModelElement instanceof RequiredOperation_c) {
						Component_c comp = Component_c
								.getOneC_COnR4010(Port_c.getManyC_POsOnR4016(InterfaceReference_c.getManyC_IRsOnR4009(Requirement_c.getManyC_RsOnR4500(RequiredExecutableProperty_c
												.getManySPR_REPsOnR4502((RequiredOperation_c) nonRootModelElement)))));
						if(!comp.getName().equals("WrapperComponent")) {
							continue;
						}
						
					}
				}
				nrme = nonRootModelElement;
				break;
			}
		}
		
		assertTrue(
				"An instance with degree of freedom type \"CN\" was not found.  Instance Name: "
						+ element + ".", nrme != null);
		return nrme;
	}
	 private void runVerifier() {		 
		 Component_c component  = locateComponentInSystem();
	    	  assertNotNull(component );
	    	  
	    	  openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
	    	  
	    	  Selection.getInstance().setSelection(new StructuredSelection(component ));
	    	  
	    	  Menu menu = m_bp_tree.getControl().getMenu();
	    	  assertTrue(
	    	    "The Launch Verifier action was not present for a component.",
	    	    UITestingUtilities.checkItemStatusInContextMenu(menu,
	    	      "Launch Verifier", "", false));
	    	  MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
	    	  assertNotNull(launchVerifierItem);
	    	  ComponentInstance_c[] engines = ComponentInstance_c.ComponentInstanceInstances(component.getModelRoot());
	    	  assertTrue("Unexpected test state, there should be no Component Instances.", engines.length == 0);
	    	  TestUtil.debugToDialog(200);
	    	  launchVerifierItem.notifyListeners(SWT.Selection, null);
	    	  TestingUtilities.processDisplayEvents();
	    	  
	    	     menu = m_bp_tree.getControl().getMenu();
	    	  assertFalse(
	    	    "The Launch Verifier action was present for an unassigned imported component.",
	    	    UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));
			
		}
    private Component_c locateComponentInSystem() {
    	Ooaofooa[] rootsInSystem = Ooaofooa.getInstancesUnderSystem(m_sys.getName());
    	for(Ooaofooa root : rootsInSystem) {
    		InstanceList instanceList = root.getInstanceList(Component_c.class);
    		for(NonRootModelElement ele : instanceList) {
    			if(ele.getName().equalsIgnoreCase(launchComponentName)) {
    				return (Component_c) ele;
    			}
    		}
    	}
    	return null;
	}



	/**
     * This routine performs the action associated with a matrix cell.
     * The parameters represent model instances aquired based on the specifed
     * column instance and row instance.
     * 
     * @param columnInstance Model instance from the column
     * @param rowInstance Model instance from the row
     */
    void A_CN_Action(NonRootModelElement rowInstance) {
    	 runVerifier();
		
	       
 		ExecuteAction action = new ExecuteAction();
 		action.setOALElement(rowInstance);
 		action.run(null);
 		
		DebugUITestUtilities.waitForExecution();

//		ComponentInstance_c engine = ComponentInstance_c
//				.getOneI_EXEOnR2955(component);
//		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();
	
        //TODO: Implement

    }

    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_subsys_ModelClass_Op() {
        boolean Sys_compPkg_Comp_Comp_subsys_ModelClass_Op = false;
        //TODO: Implement
        return Sys_compPkg_Comp_Comp_subsys_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_subsys_ModelClass_Op() {
        boolean Sys_dom_subsys_subsys_ModelClass_Op = false;
        //TODO: Implement
        return Sys_dom_subsys_subsys_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_ReqSig() {
        boolean Sys_compPkg_Comp_Comp_ReqSig = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::Root Component Package::Nested Component Package::Root Component::Domain::A10C3N3"))
        {
        	Sys_compPkg_Comp_Comp_ReqSig = true;
        }
        return Sys_compPkg_Comp_Comp_ReqSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_PrvOp() {
        boolean Sys_compPkg_Comp_Comp_PrvOp = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::Root Component Package::Nested Component Package::Root Component::Domain::A7C3N3"))
        {
        	Sys_compPkg_Comp_Comp_PrvOp = true;
        }
        return Sys_compPkg_Comp_Comp_PrvOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_Pkg_Model() {
        boolean Sys_Pkg_comp_comp_Pkg_Model = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_Pkg_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_comPkg_Comp_PrvSig() {
        boolean Sys_compPkg_comPkg_Comp_PrvSig = false;
        //TODO: Implement
        return Sys_compPkg_comPkg_Comp_PrvSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_subsys_ModelClass_StateMachine_State() {
        boolean Sys_compPkg_Comp_subsys_ModelClass_StateMachine_State = false;
        //TODO: Implement
        return Sys_compPkg_Comp_subsys_ModelClass_StateMachine_State;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_comPkg_Comp_ReqOp() {
        boolean Sys_compPkg_comPkg_Comp_ReqOp = false;
        //TODO: Implement
        return Sys_compPkg_comPkg_Comp_ReqOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_compPkg_Comp_subsys_Model() {
        boolean Sys_compPkg_compPkg_Comp_subsys_Model = false;
        //TODO: Implement
        return Sys_compPkg_compPkg_Comp_subsys_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine() {
        boolean Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::Root Component Package::Nested Component Package::Root Component::Domain::Root Subsystem::Nested Subsystem::Class::Instance Based State Machine"))
        {
        	Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine = true;
        }
        return Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_PrvSig() {
        boolean Sys_compPkg_Comp_Comp_PrvSig = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::Root Component Package::Nested Component Package::Root Component::Domain::A8C3N3"))
        {
        	Sys_compPkg_Comp_Comp_PrvSig = true;
        }
        return Sys_compPkg_Comp_Comp_PrvSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_subsys_ModelClass_StateMachine() {
        boolean Sys_compPkg_Comp_subsys_ModelClass_StateMachine = false;
        //TODO: Implement
        return Sys_compPkg_Comp_subsys_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_FnPkg_FnPkg_Fn() {
        boolean Sys_compPkg_Comp_FnPkg_FnPkg_Fn = false;
        //TODO: Implement
        return Sys_compPkg_Comp_FnPkg_FnPkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_PrvOp() {
        boolean Sys_Pkg_comp_PrvOp = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::A7C1N1"))
        {
        	Sys_Pkg_comp_PrvOp = true;
        }
        return Sys_Pkg_comp_PrvOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_ReqSig() {
        boolean Sys_Pkg_comp_comp_ReqSig = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_ReqSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_comp_ReqOp() {
        boolean Sys_Pkg_Pkg_comp_ReqOp = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_comp_ReqOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_subsys_Model() {
    	 boolean Sys_compPkg_Comp_Comp_subsys_Model = false;
    	  String actual_results = DebugUITestUtilities.getConsoleText("null");
          if (actual_results.contains(" TestVerifierRunTimeErrorMsgs::Root Component Package::Nested Component Package::Root Component::Domain::Root Subsystem::Nested Subsystem::A3C3N3"))
          {
        	  Sys_compPkg_Comp_Comp_subsys_Model = true;
          }
          return Sys_compPkg_Comp_Comp_subsys_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_PrvOp() {
        boolean Sys_Pkg_comp_comp_PrvOp = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_PrvOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_PrvOp() {
        boolean Sys_compPkg_Comp_PrvOp = false;
        //TODO: Implement
        return Sys_compPkg_Comp_PrvOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_Model() {
        boolean Sys_Pkg_Pkg_Model = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_comPkg_Comp_ReqSig() {
        boolean Sys_compPkg_comPkg_Comp_ReqSig = false;
        //TODO: Implement
        return Sys_compPkg_comPkg_Comp_ReqSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_ModelClass_StateMachine() {
        boolean Sys_Pkg_ModelClass_StateMachine = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A3C1N1::Instance Based State Machine::KEY1: A2C1N1"))
        {
        	Sys_Pkg_ModelClass_StateMachine= true;
        }

        return Sys_Pkg_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_ModelClass_Op() {
        boolean Sys_dom_subsys_ModelClass_Op = false;
        //TODO: Implement
        return Sys_dom_subsys_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_compPkg_Comp_subsys_ModelClass_Op() {
        boolean Sys_compPkg_compPkg_Comp_subsys_ModelClass_Op = false;
        //TODO: Implement
        return Sys_compPkg_compPkg_Comp_subsys_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_ModelClass_StateMachine() {
        boolean Sys_Pkg_Pkg_ModelClass_StateMachine = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_ReqOp() {
        boolean Sys_compPkg_Comp_Comp_ReqOp = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::Root Component Package::Nested Component Package::Root Component::Domain::A9C3N3"));
        {
        	Sys_compPkg_Comp_Comp_ReqOp = true;
        }
        return Sys_compPkg_Comp_Comp_ReqOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_Pkg_EE_Brdg() {
        boolean Sys_Pkg_comp_comp_Pkg_EE_Brdg = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_Pkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_ReqSig() {
//        boolean Sys_compPkg_Comp_Comp_ReqSig = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_ReqSig;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_comPkg_Comp_ReqOp() {
//        boolean Sys_compPkg_comPkg_Comp_ReqOp = false;
//        //TODO: Implement
//        return Sys_compPkg_comPkg_Comp_ReqOp;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_comp_PrvSig() {
        boolean Sys_Pkg_Pkg_comp_PrvSig = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_comp_PrvSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_ModelClass_Op() {
        boolean Sys_Pkg_Pkg_ModelClass_Op = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_EEPkg_EEPkg_EE_Brdg() {
        boolean Sys_dom_EEPkg_EEPkg_EE_Brdg = false;
        //TODO: Implement
        return Sys_dom_EEPkg_EEPkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_Pkg_Fn() {
        boolean Sys_Pkg_comp_comp_Pkg_Fn = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_Pkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_PrvSig() {
        boolean Sys_Pkg_comp_PrvSig = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::A8C1N1"))
        {
        	Sys_Pkg_comp_PrvSig = true;
        }
        return Sys_Pkg_comp_PrvSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_PrvOp() {
//        boolean Sys_compPkg_Comp_PrvOp = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_PrvOp;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_FnPkg_FnPkg_Fn() {
        boolean Sys_dom_FnPkg_FnPkg_Fn = false;
        //TODO: Implement
        return Sys_dom_FnPkg_FnPkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_Pkg_ModelClass_StateMachine() {
        boolean Sys_Pkg_comp_comp_Pkg_ModelClass_StateMachine = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_Pkg_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine() {
//        boolean Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_ReqOp() {
//        boolean Sys_compPkg_Comp_Comp_ReqOp = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_ReqOp;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_EEPkg_EE_Brdg() {
        boolean Sys_compPkg_Comp_EEPkg_EE_Brdg = false;
        //TODO: Implement
        return Sys_compPkg_Comp_EEPkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_PrvOp() {
//        boolean Sys_compPkg_Comp_Comp_PrvOp = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_PrvOp;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_comp_PrvOp() {
        boolean Sys_Pkg_Pkg_comp_PrvOp = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_comp_PrvOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_ReqOp() {
        boolean Sys_Pkg_comp_ReqOp = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::A9C1N1"))
        {
        	Sys_Pkg_comp_ReqOp = true;
        }
        return Sys_Pkg_comp_ReqOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_PrvSig() {
        boolean Sys_Pkg_comp_comp_PrvSig = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_PrvSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_FnPkg_Fn() {
//        boolean Sys_compPkg_Comp_Comp_FnPkg_Fn = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_FnPkg_Fn;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_comPkg_Comp_ReqSig() {
//        boolean Sys_compPkg_comPkg_Comp_ReqSig = false;
//        //TODO: Implement
//        return Sys_compPkg_comPkg_Comp_ReqSig;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_ReqOp() {
//        boolean Sys_compPkg_Comp_ReqOp = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_ReqOp;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Model() {
        boolean Sys_Pkg_Model = false;
        
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A3C1N1"))
        {
        	Sys_Pkg_Model= true;
        }
        
        return Sys_Pkg_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_comPkg_Comp_PrvOp() {
//        boolean Sys_compPkg_comPkg_Comp_PrvOp = false;
//        //TODO: Implement
//        return Sys_compPkg_comPkg_Comp_PrvOp;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg() {
//        boolean Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_PrvSig() {
//        boolean Sys_compPkg_Comp_PrvSig = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_PrvSig;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_PrvSig() {
        boolean Sys_compPkg_Comp_PrvSig = false;
        //TODO: Implement
        return Sys_compPkg_Comp_PrvSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_comPkg_Comp_PrvSig() {
//        boolean Sys_compPkg_comPkg_Comp_PrvSig = false;
//        //TODO: Implement
//        return Sys_compPkg_comPkg_Comp_PrvSig;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_ModelClass_StateMachine_State() {
        boolean Sys_Pkg_Pkg_ModelClass_StateMachine_State = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_ModelClass_StateMachine_State;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_compPkg_Comp_subsys_ModelClass_StateMachine() {
        boolean Sys_compPkg_compPkg_Comp_subsys_ModelClass_StateMachine = false;
        //TODO: Implement
        return Sys_compPkg_compPkg_Comp_subsys_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_ModelClass_StateMachine_State() {
        boolean Sys_Pkg_ModelClass_StateMachine_State = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A3C1N1::Instance Based State Machine"))
        {
        	Sys_Pkg_ModelClass_StateMachine_State= true;
        }
     
        return Sys_Pkg_ModelClass_StateMachine_State;
    }

    boolean checkResult_Sys_Pkg_ModelClass_Class_StateMachine_State() {
        boolean Sys_Pkg_ModelClass_Class_StateMachine_State = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A3C1N1::Class Based State Machine::finish"))
        {
        	Sys_Pkg_ModelClass_Class_StateMachine_State= true;
        }
     
        return Sys_Pkg_ModelClass_Class_StateMachine_State;
    }
    
    boolean checkResult_Sys_Pkg_ModelClass_StateMachine_CHEvent() {
        boolean Sys_Pkg_ModelClass_StateMachine_CHEvent = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A3C1N1::A3C1N1"))
        {
        	Sys_Pkg_ModelClass_StateMachine_CHEvent= true;
        }
     
        return Sys_Pkg_ModelClass_StateMachine_CHEvent;
    }
    boolean checkResult_Sys_Pkg_ModelClass_StateMachine_CTEvent() {
    	boolean Sys_Pkg_ModelClass_StateMachine_CTEvent = false;
    	String actual_results = DebugUITestUtilities.getConsoleText("null");
    	if (actual_results.contains("User invoked function: ExecuteA1CT\r\n"))
    	{
    		Sys_Pkg_ModelClass_StateMachine_CTEvent= true;
    	}
    	
    	return Sys_Pkg_ModelClass_StateMachine_CTEvent;
    }
    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_ReqSig() {
//        boolean Sys_compPkg_Comp_ReqSig = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_ReqSig;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_Pkg_ModelClass_Op() {
        boolean Sys_Pkg_comp_comp_Pkg_ModelClass_Op = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_Pkg_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_ReqSig() {
        boolean Sys_Pkg_comp_ReqSig = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::A10C1N1"))
        {
        	Sys_Pkg_comp_ReqSig = true;
        }
        return Sys_Pkg_comp_ReqSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_subsys_Model() {
        boolean Sys_compPkg_Comp_subsys_Model = false;
        //TODO: Implement
        return Sys_compPkg_Comp_subsys_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_ModelClass_Op() {
        boolean Sys_Pkg_ModelClass_Op = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A4C1N1"))
        {
        	Sys_Pkg_ModelClass_Op= true;
        }
        return Sys_Pkg_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_comp_ReqSig() {
        boolean Sys_Pkg_Pkg_comp_ReqSig = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_comp_ReqSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_ReqOp() {
        boolean Sys_Pkg_comp_comp_ReqOp = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_ReqOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_FnPkg_Fn() {
        boolean Sys_dom_FnPkg_Fn = false;
        //TODO: Implement
        return Sys_dom_FnPkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_subsys_ModelClass_StateMachine() {
        boolean Sys_dom_subsys_subsys_ModelClass_StateMachine = false;
        //TODO: Implement
        return Sys_dom_subsys_subsys_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_PrvSig() {
//        boolean Sys_compPkg_Comp_Comp_PrvSig = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_PrvSig;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_comPkg_Comp_PrvOp() {
        boolean Sys_compPkg_comPkg_Comp_PrvOp = false;
        //TODO: Implement
        return Sys_compPkg_comPkg_Comp_PrvOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_EEPkg_EE_Brdg() {
        boolean Sys_dom_EEPkg_EE_Brdg = false;
        //TODO: Implement
        return Sys_dom_EEPkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_subsys_Model() {
//        boolean Sys_compPkg_Comp_Comp_subsys_Model = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_subsys_Model;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_EEPkg_EEPkg_EE_Brdg() {
        boolean Sys_compPkg_Comp_EEPkg_EEPkg_EE_Brdg = false;
        //TODO: Implement
        return Sys_compPkg_Comp_EEPkg_EEPkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_ReqOp() {
        boolean Sys_compPkg_Comp_ReqOp = false;
        //TODO: Implement
        return Sys_compPkg_Comp_ReqOp;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_Model() {
        boolean Sys_dom_subsys_Model = false;
        //TODO: Implement
        return Sys_dom_subsys_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_FnPkg_Fn() {
        boolean Sys_compPkg_Comp_Comp_FnPkg_Fn = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains("Domain::Root Function Package::Nested Function Package::A5C3N3"))
        {
        	Sys_compPkg_Comp_Comp_FnPkg_Fn = true;
        }
        return Sys_compPkg_Comp_Comp_FnPkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_EE_Brdg() {
        boolean Sys_Pkg_EE_Brdg = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A6C1N1"))
        {
        	Sys_Pkg_EE_Brdg= true;
        }
        return Sys_Pkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_subsys_Model() {
        boolean Sys_dom_subsys_subsys_Model = false;
        //TODO: Implement
        return Sys_dom_subsys_subsys_Model;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_ModelClass_StateMachine() {
        boolean Sys_dom_subsys_ModelClass_StateMachine = false;
        //TODO: Implement
        return Sys_dom_subsys_ModelClass_StateMachine;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
//    boolean checkResult_Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State() {
//        boolean Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State = false;
//        //TODO: Implement
//        return Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State;
//    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_ReqSig() {
        boolean Sys_compPkg_Comp_ReqSig = false;
        //TODO: Implement
        return Sys_compPkg_Comp_ReqSig;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_comp_comp_Pkg_ModelClass_StateMachine_State() {
        boolean Sys_Pkg_comp_comp_Pkg_ModelClass_StateMachine_State = false;
        //TODO: Implement
        return Sys_Pkg_comp_comp_Pkg_ModelClass_StateMachine_State;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Comp_Comp_subsys_ModelClass_Op() {
        boolean Sys_Pkg_Comp_Comp_subsys_ModelClass_Op = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::Root Component Package::Nested Component Package::Root Component::Domain::Root Subsystem::Nested Subsystem::A4C3N3"))
        {
        	Sys_Pkg_Comp_Comp_subsys_ModelClass_Op = true;
        }
        return Sys_Pkg_Comp_Comp_subsys_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State(){
        boolean Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains(projectName +"::Root Component Package::Nested Component Package::Root Component::Domain::Root Subsystem::Nested Subsystem::Class::Instance Based State Machine::A1C3N3"))
        {
        	Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State = true;
        }
        return Sys_compPkg_Comp_Comp_subsys_ModelClass_StateMachine_State;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg(){
        boolean Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
        if (actual_results.contains("Domain::External Entities::Nested External Entity Package::A6C3N3"))
        {
        	Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg = true;
        }
        return Sys_compPkg_Comp_Comp_EEPkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_EE_Brdg(){
        boolean Sys_Pkg_Pkg_EE_Brdg = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_EE_Brdg;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_compPkg_Comp_subsys_ModelClass_StateMachine_State(){
        boolean Sys_compPkg_compPkg_Comp_subsys_ModelClass_StateMachine_State = false;
        //TODO: Implement
        return Sys_compPkg_compPkg_Comp_subsys_ModelClass_StateMachine_State;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Pkg_Fn(){
        boolean Sys_Pkg_Pkg_Fn = false;
        //TODO: Implement
        return Sys_Pkg_Pkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_ModelClass_StateMachine_State(){
        boolean Sys_dom_subsys_ModelClass_StateMachine_State = false;
        //TODO: Implement
        return Sys_dom_subsys_ModelClass_StateMachine_State;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_subsys_ModelClass_Op(){
        boolean Sys_compPkg_Comp_subsys_ModelClass_Op = false;
        //TODO: Implement
        return Sys_compPkg_Comp_subsys_ModelClass_Op;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_Pkg_Fn() {
        boolean Sys_Pkg_Fn = false;
        String actual_results = DebugUITestUtilities.getConsoleText("null");
         if (actual_results.contains(projectName +"::C1N1::WrapperComponent::RootPackage::PackageInsidePackage::A5C1N1"))
         {
        	 Sys_Pkg_Fn= true;
         }
        return Sys_Pkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_compPkg_Comp_FnPkg_Fn(){
        boolean Sys_compPkg_Comp_FnPkg_Fn = false;
        //TODO: Implement
        return Sys_compPkg_Comp_FnPkg_Fn;
    }


    /**
    * This function verifies an expected result.
    *
    * @param source A model element instance aquired through a action taken
    *               on a column of the matrix.
    * @param destination A model element instance aquired through a action taken
    *                    taken on a row of the matrix.
    * @return true if the test succeeds, false if it fails
    */
    boolean checkResult_Sys_dom_subsys_subsys_ModelClass_StateMachine_State(){
        boolean Sys_dom_subsys_subsys_ModelClass_StateMachine_State = false;
        //TODO: Implement
        return Sys_dom_subsys_subsys_ModelClass_StateMachine_State;
    }


}
