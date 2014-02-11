//=====================================================================
//
//File:      $RCSfile: TransitionActionTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:25 $
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
package com.mentor.nucleus.bp.core.test;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class TransitionActionTestGenerics extends CanvasTest {
	
	private String test_id;
	public static boolean generateResults = false;
	private static boolean initialized = false;
	public TransitionActionTestGenerics(String arg0) {
		super(arg0);

		// turn off autobuild to stop MC-3020 builders from running		
		try {
			WorkspaceUtil.setAutobuilding(false);
		} catch (CoreException e) {
			fail(e.toString());
		}
	}

	protected void setUp() throws Exception {
      super.setUp();
//      IProject testProject = TestingUtilities.createProject("TransitionActionTest");
//      File sourceProject = new File(m_workspace_path + "../TransitionActionTest");
//      CorePlugin.disableParseAllOnResourceChange();
      
  	if (!initialized) {
		loadProject("TransitionActionTest");
		initialized = true;
		BaseTest.dispatchEvents(0);
	}

      
  //    TestingUtilities.copyProjectContents(sourceProject, testProject);
    //  TestingUtilities.allowJobCompletion();
	  try {
		  IProject testProject =project;
	    testProject.close(new NullProgressMonitor());
	    testProject.open(new NullProgressMonitor());
      } catch (CoreException e) {
	    fail(e.toString());
      }
      BaseTest.waitForJobs();
	}

	@Override
	protected String getResultName() {
		return "TransitionActionTest" + "_" + test_id;
	}

	public void testISMCreationTransition() {
		test_id = "1";
        modelRoot = getInitializedTestModelRoot("/TransitionActionTest/" +
        		"models/TransitionActionTest/Transition Action Test Package/" +
                                        "Transition Action Test Package.xtuml");
		
        InstanceStateMachine_c uut = InstanceStateMachine_c.
                                        InstanceStateMachineInstance(modelRoot);
        handleCreationTransitionTestPreconditions(uut);
        handleCreationTransitionTest(uut);
        handleCreationTransitionTestPostconditions();
        handleTearDown();
	}
	public void testISMTransition() {
		test_id = "2";
        modelRoot = getInitializedTestModelRoot("/TransitionActionTest/" +
        		"models/TransitionActionTest/Transition Action Test Package/" +
                                        "Transition Action Test Package.xtuml");
		
        InstanceStateMachine_c uut = InstanceStateMachine_c.
                                        InstanceStateMachineInstance(modelRoot);
        handleTransitionTestPreconditions(uut);
        handleTransitionTest(uut, "Test State A", "Test State B");
        handleTransitionTestPostconditions();
        handleTearDown();
	}
	public void testASMTransition() {
		test_id = "3";
        modelRoot = getInitializedTestModelRoot("/TransitionActionTest/" +
        		"models/TransitionActionTest/Transition Action Test Package/" +
                                        "Transition Action Test Package.xtuml");
		
        ClassStateMachine_c uut = ClassStateMachine_c.
                                           ClassStateMachineInstance(modelRoot);
        handleTransitionTestPreconditions(uut);
        handleTransitionTest(uut, "Test State D", "Test State E");
        handleTransitionTestPostconditions();
        handleTearDown();
	}
	private static void handleCreationTransitionTestPreconditions(Object uut) {
        assertNotNull(uut);
        CanvasUtilities.openCanvasEditor(uut);

  		TransitionActionHome_c tah = TransitionActionHome_c.
  		                            TransitionActionHomeInstance(modelRoot);
  		assertNull("Unexpected transition action home found", tah);
	}
	public static void handleCreationTransitionTest(Object uut) {
		try {
	  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
					.getGraphicalEditor();
	  		// give the diagram some time to layout figures
	  		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());	  		
	  		ce.zoomAll();
	  		ce.zoomOut();
	  		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	  		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
	  				                                            "Test State A");
	  		graphicsModelRoot = (Ooaofgraphics)OoaofgraphicsUtil.
	  		                         getGraphicsRoot(modelRoot.getId(),
                                          OoaofgraphicsUtil.getGraphicsClass());

	  		AbstractTool tool = UITestingUtilities.getTool("Creation Transition");
	  		UITestingUtilities.activateTool(tool);
	  		Point center = CanvasTestUtils.getShapeCenter(shp);
	  		Point mouse = CanvasTestUtils.convertToMouseCoor(center,
	  				                                               ce.getModel());  
	  		CanvasTestUtils.createMouseEvent((mouse.x - 100), (mouse.y - 100),
	  				                                               "MouseDown");
	  		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y, "MouseMove");
	  		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y, "MouseUp");
	  		UITestingUtilities.deactivateTool(tool);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
    private static void handleCreationTransitionTestPostconditions() {
  		TransitionActionHome_c [] tahs = TransitionActionHome_c.
  		                           TransitionActionHomeInstances(modelRoot);
        assertTrue("Too many transition action homes created.",
        		                                          tahs.length <= 1);
        assertTrue("Transition action home not found.", tahs.length > 0);
        ActionHome_c ah = ActionHome_c.getOneSM_AHOnR513(tahs[0]);
        assertNotNull("Action Home supertype not found.", ah);
        Action_c act = Action_c.getOneSM_ACTOnR514(ah);
        assertNotNull("Action not found", act);
        CreationTransition_c crTr = CreationTransition_c.
             getOneSM_CRTXNOnR507(Transition_c.getOneSM_TXNOnR530(tahs[0]));
        assertNotNull("Creation transition not found", crTr);
        NewStateTransition_c nsTr = NewStateTransition_c.
             getOneSM_NSTXNOnR507(Transition_c.getOneSM_TXNOnR530(tahs[0]));
        assertNull("Bad new state transition found", nsTr);
        NoEventTransition_c neTr = NoEventTransition_c.
             getOneSM_NETXNOnR507(Transition_c.getOneSM_TXNOnR530(tahs[0]));
        assertNull("Bad no event transition found", neTr);
    }
	private static void handleTransitionTestPreconditions(Object uut) {
        assertNotNull(uut);
        CanvasUtilities.openCanvasEditor(uut);

  		TransitionActionHome_c tah = TransitionActionHome_c.
  		                            TransitionActionHomeInstance(modelRoot);
  		assertNull("Unexpected transition action home found", tah);
	}
	public static void handleTransitionTest(Object uut, String startState,
                                                              String endState) {
		try {
	  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
					.getGraphicalEditor();
	  		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	  		ce.zoomAll();
	  		ce.zoomOut();
	  		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	  		Shape_c startShp = CanvasTestUtils.getModelStateShape(modelRoot,
	  				                                                startState);
	  		Shape_c endShp = CanvasTestUtils.getModelStateShape(modelRoot,
                                                                      endState);
	  		graphicsModelRoot = (Ooaofgraphics)OoaofgraphicsUtil.
	  		                         getGraphicsRoot(modelRoot.getId(),
                                          OoaofgraphicsUtil.getGraphicsClass());
	  		AbstractTool tool = UITestingUtilities.getTool("Transition");
	  		UITestingUtilities.activateTool(tool);
	  		Point center = CanvasTestUtils.getShapeCenter(startShp);
	  		Point startMouse = CanvasTestUtils.convertToMouseCoor(center,
	  				                                               ce.getModel());  
	  		center = CanvasTestUtils.getShapeCenter(endShp);
	  		Point endMouse = CanvasTestUtils.convertToMouseCoor(center,
	  				                                               ce.getModel());  
	  		CanvasTestUtils.createMouseEvent(startMouse.x, startMouse.y,
	  				                                               "MouseDown");
	  		CanvasTestUtils.createMouseEvent(endMouse.x, endMouse.y, "MouseMove");
	  		CanvasTestUtils.createMouseEvent(endMouse.x, endMouse.y, "MouseUp");
	  		UITestingUtilities.deactivateTool(tool);
		} catch (Exception e) {
			fail(e.toString());
		}
	}
    private static void handleTransitionTestPostconditions() {
  		TransitionActionHome_c [] tahs = TransitionActionHome_c.
  		                           TransitionActionHomeInstances(modelRoot);
        assertTrue("Too many transition action homes created.",
        		                                          tahs.length <= 1);
        assertTrue("Transition action home not found.", tahs.length > 0);
        ActionHome_c ah = ActionHome_c.getOneSM_AHOnR513(tahs[0]);
        assertNotNull("Action Home supertype not found.", ah);
        Action_c act = Action_c.getOneSM_ACTOnR514(ah);
        assertNotNull("Action not found", act);
        NoEventTransition_c neTr = NoEventTransition_c.
             getOneSM_NETXNOnR507(Transition_c.getOneSM_TXNOnR530(tahs[0]));
        assertNotNull("Creation transition not found", neTr);
        NewStateTransition_c nsTr = NewStateTransition_c.
             getOneSM_NSTXNOnR507(Transition_c.getOneSM_TXNOnR530(tahs[0]));
        assertNull("Bad new state transition found", nsTr);
        CreationTransition_c crTr = CreationTransition_c.
             getOneSM_CRTXNOnR507(Transition_c.getOneSM_TXNOnR530(tahs[0]));
        assertNull("Bad no event transition found", crTr);
    }
	public static void handleTearDown() {
        // tear down
        Transition_c [] trs = Transition_c.TransitionInstances(modelRoot);
        for (int i=0; i< trs.length; i++) {
          trs[i].Dispose();
        }
	}
    public static Ooaofooa getInitializedTestModelRoot(String p_path) {
		Ooaofooa result = Ooaofooa.getInstance(p_path, true);
		while(ResourcesPlugin.getWorkspace().isTreeLocked());
		while(!Platform.getJobManager().isIdle()) {
		  while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		return result;
    }
}
