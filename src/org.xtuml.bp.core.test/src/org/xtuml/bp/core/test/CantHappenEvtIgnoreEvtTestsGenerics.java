//========================================================================
//
//File:      $RCSfile: CantHappenEvtIgnoreEvtTestsGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:25 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package org.xtuml.bp.core.test;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CantHappen_c;
import org.xtuml.bp.core.EventIgnored_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.ui.GenericPackageCantHappenEventOnSM_STATEAction;
import org.xtuml.bp.core.ui.GenericPackageCantHappenEventOnSM_STATEWizardPage1;
import org.xtuml.bp.core.ui.GenericPackageCantHappenInStateOnSM_EVTAction;
import org.xtuml.bp.core.ui.GenericPackageCantHappenInStateOnSM_EVTWizardPage1;
import org.xtuml.bp.core.ui.GenericPackageIgnoreEventOnSM_STATEAction;
import org.xtuml.bp.core.ui.GenericPackageIgnoreEventOnSM_STATEWizardPage1;
import org.xtuml.bp.core.ui.GenericPackageIgnoreInStateOnSM_EVTAction;
import org.xtuml.bp.core.ui.GenericPackageIgnoreInStateOnSM_EVTWizardPage1;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.test.CanvasTest;

@RunWith(OrderedRunner.class)
public class CantHappenEvtIgnoreEvtTestsGenerics extends CanvasTest {

	IWorkbenchPage m_wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage();
	String test_id = null;

	static String workspace_path = "";

	//Ooaofooa modelRoot = BaseTest.getDefaultTestInstance();
	//Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
	static boolean initialized = false;

	private static Selection selection = Selection.getInstance();

	public CantHappenEvtIgnoreEvtTestsGenerics(){
		super(null, null);
	}

	protected String getResultName() {
		return "CantHappenEvtIgnoreEvtTests" + "_" + test_id;
	}
	//	private IProject createProject(String name) {
	//		IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot()
	//				.getProject(name);
	//
	//		// try to open a currently existing project
	//		try {
	//			projectHandle.open(new NullProgressMonitor());
	//			return projectHandle;
	//		} catch (CoreException e) {
	//			if (e.getStatus().getPlugin() != ResourcesPlugin.PI_RESOURCES
	//					|| e.getStatus().getCode() != IResourceStatus.RESOURCE_NOT_FOUND)
	//				CanvasPlugin.logError("open project '" + name + "' error", e);
	//		}
	//
	//		// project doesn't exist, create a new project
	//		try {
	//			final IProjectDescription myTestProject = ResourcesPlugin
	//					.getWorkspace().newProjectDescription(name);
	//			myTestProject.setLocation(null); // default location
	//			projectHandle.create(myTestProject, new NullProgressMonitor());
	//			projectHandle.open(new NullProgressMonitor());
	//			return projectHandle;
	//		} catch (CoreException e) {
	//			CanvasPlugin.logError("create project '" + name + "' error", e);
	//		}
	//		return null;
	//	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		if (!initialized) {
			loadProject("CantHappenEvtIgnoreEvtTests");
			initialized = true;
		}
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void openISC(String name) {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName(name));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR501(sms));
		CanvasTestUtils.openCanvasEditor(ism);
	}

	@Test
	public void testIgnoreEvtMenuItemTest() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"IgnoreEvtMIT State 1");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Cl_c.Clearselection();
		selection.addToSelection(ge.getRepresents());
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("IgnoreEvtMIT State 1"));
		assertTrue(sms.Actionfilter("can", "ignore generic pkg"));
		assertFalse(sms.Actionfilter("can", "ch generic pkg"));
	}
	@Test
	public void testCHEvtMenuItemTest() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"CHEvtMIT State 1");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Cl_c.Clearselection();
		selection.addToSelection(ge.getRepresents());
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("CHEvtMIT State 1"));
		assertTrue(sms.Actionfilter("can", "ch generic pkg"));
		assertFalse(sms.Actionfilter("can", "ignore generic pkg"));
	}
	@Test
	public void testCHEvtIgnoreEvtMenuItemTest() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"CHEvtIgnoreEvtMIT State 1");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Cl_c.Clearselection();
		selection.addToSelection(ge.getRepresents());
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName(
								"CHEvtIgnoreEvtMIT State 1"));
		assertTrue(sms.Actionfilter("can", "ignore generic pkg"));
		assertTrue(sms.Actionfilter("can", "ch generic pkg"));
	}
	@Test
	public void testMarkAsCHEvt() {
		openISC("CHT State 1");
		CanvasTestUtils ctu = new CanvasTestUtils();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"CHT State 1");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Cl_c.Clearselection();
		selection.addToSelection(ge.getRepresents());
		Action a = new Action() {
		};
		GenericPackageCantHappenEventOnSM_STATEAction chea = new GenericPackageCantHappenEventOnSM_STATEAction();
		chea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = chea
				.SM_STATE_GenericPackageCantHappenEvent(structuredSelection);
		GenericPackageCantHappenEventOnSM_STATEWizardPage1 chp = (GenericPackageCantHappenEventOnSM_STATEWizardPage1) wd
				.getCurrentPage();
		chp.onPageEntry();
		chp.getWizard().performFinish();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("CHT State 1"));
		SemEvent_c sem = SemEvent_c.getOneSM_SEVTOnR503(sms);
		CantHappen_c ch = CantHappen_c
				.getOneSM_CHOnR504(StateEventMatrixEntry_c
						.getOneSM_SEMEOnR503(sem));
		assertNotNull(ch);
	}
	@Test
	public void testMarkAsIgnoreEvt() {
		openISC("IgnoreEvtTest State 1");
		CanvasTestUtils ctu = new CanvasTestUtils();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"IgnoreEvtTest State 1");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Cl_c.Clearselection();
		selection.addToSelection(ge.getRepresents());
		Action a = new Action() {
		};
		GenericPackageIgnoreEventOnSM_STATEAction iea = new GenericPackageIgnoreEventOnSM_STATEAction();
		iea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = iea
				.SM_STATE_GenericPackageIgnoreEvent(structuredSelection);
		GenericPackageIgnoreEventOnSM_STATEWizardPage1 iep = (GenericPackageIgnoreEventOnSM_STATEWizardPage1) wd
				.getCurrentPage();
		iep.onPageEntry();
		iep.getWizard().performFinish();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("IgnoreEvtTest State 1"));
		SemEvent_c sme = SemEvent_c.getOneSM_SEVTOnR503(sms);
		EventIgnored_c ig = EventIgnored_c
				.getOneSM_EIGNOnR504(StateEventMatrixEntry_c
						.getOneSM_SEMEOnR503(sme));
		assertNotNull(ig);
	}
	@Test
	public void testMarkAsIgnoreEvtOnEvent() {
		openISC("IgnoreEvtEvent State 1");
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("IgnoreEvtEvent State 1"));
		StateMachineEvent_c sme = StateMachineEvent_c
				.getOneSM_EVTOnR502(StateMachine_c.getOneSM_SMOnR501(sms));
		Cl_c.Clearselection();
		selection.addToSelection(sme);
		Action a = new Action() {
		};
		GenericPackageIgnoreInStateOnSM_EVTAction isea = new GenericPackageIgnoreInStateOnSM_EVTAction();
		isea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = isea
				.SM_EVT_GenericPackageIgnoreInState(structuredSelection);
		GenericPackageIgnoreInStateOnSM_EVTWizardPage1 iep = (GenericPackageIgnoreInStateOnSM_EVTWizardPage1) wd
				.getCurrentPage();
		iep.onPageEntry();
		iep.getWizard().performFinish();
		SemEvent_c sevt = SemEvent_c.getOneSM_SEVTOnR503(sms);
		EventIgnored_c ei = EventIgnored_c
				.getOneSM_EIGNOnR504(StateEventMatrixEntry_c
						.getManySM_SEMEsOnR503(sevt));
		assertNotNull(ei);
	}
	@Test
	public void testMarkAsCHEvtOnEvent() {
		openISC("CHEvtEvent State 1");
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("CHEvtEvent State 1"));
		StateMachineEvent_c sme = StateMachineEvent_c
				.getOneSM_EVTOnR502(StateMachine_c.getOneSM_SMOnR501(sms));
		Cl_c.Clearselection();
		selection.addToSelection(sme);
		Action a = new Action() {
		};
		GenericPackageCantHappenInStateOnSM_EVTAction chia = new GenericPackageCantHappenInStateOnSM_EVTAction();
		chia.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = chia
				.SM_EVT_GenericPackageCantHappenInState(structuredSelection);
		GenericPackageCantHappenInStateOnSM_EVTWizardPage1 iep = (GenericPackageCantHappenInStateOnSM_EVTWizardPage1) wd
				.getCurrentPage();
		iep.onPageEntry();
		iep.getWizard().performFinish();
		CantHappen_c ch = CantHappen_c
				.getOneSM_CHOnR504(StateEventMatrixEntry_c
						.getManySM_SEMEsOnR503(SemEvent_c
								.getOneSM_SEVTOnR525(sme)));
		assertNotNull(ch);
	}
}