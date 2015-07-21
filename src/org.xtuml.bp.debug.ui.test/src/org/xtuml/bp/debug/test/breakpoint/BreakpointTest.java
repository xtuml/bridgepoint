package org.xtuml.bp.debug.test.breakpoint;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.LocalEvent_c;
import org.xtuml.bp.core.NewStateTransition_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.text.activity.ActivityEditor;

public class BreakpointTest extends BaseTest {

	private static String projectName = "BreakpointTest";
	private boolean initialized = false;
	
	public BreakpointTest(String testName) throws Exception {
		super(projectName, testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			CorePlugin.disableParseAllOnResourceChange();

			// set perspective switch dialog on launch
			DebugUIPlugin.getDefault().getPluginPreferences().setValue(
					IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
					"always");

			DebugUITestUtilities.processDebugEvents();

			loadProject("dts0100655323");
			PersistableModelComponent sys_comp = m_sys
					.getPersistableComponent();
			sys_comp.loadComponentAndChildren(new NullProgressMonitor());

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			while (!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(
					IProject.DEPTH_INFINITE)) {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
						IProject.DEPTH_INFINITE, new NullProgressMonitor());
				while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
					;
			}

			Ooaofooa.setPersistEnabled(true);

			initialized = true;
		}
	}

	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
	
    public void testSetBreakpointOnNewStateTransitionWithLongEventDataList() {
		StateMachineEvent_c evt = StateMachineEvent_c.StateMachineEventInstance(modelRoot, new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c)candidate).getMning().equals("Test Event");
					}
				
				});
		assertNotNull(evt);
		
		Transition_c tr = Transition_c.getOneSM_TXNOnR507(NewStateTransition_c.
				getManySM_NSTXNsOnR504(
						StateEventMatrixEntry_c.getManySM_SEMEsOnR503(
								SemEvent_c.getManySM_SEVTsOnR525(evt))));
		assertNotNull(tr);
        setAndConfirmBreakpoint(tr);        
    }

    public void testSetBreakpointOnCreationTransitionWithLongEventDataList() {
		StateMachineEvent_c evt = StateMachineEvent_c.StateMachineEventInstance(modelRoot, new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c)candidate).getMning().equals("Test Event");
					}
				
				});
		assertNotNull(evt);
		
		CreationTransition_c tr = CreationTransition_c.
				getOneSM_CRTXNOnR509(LocalEvent_c.getManySM_LEVTsOnR526(
						SemEvent_c.getManySM_SEVTsOnR525(evt)));
		assertNotNull(tr);
        setAndConfirmBreakpoint(tr);        
    }

    private void setAndConfirmBreakpoint(NonRootModelElement tr) {
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(tr);
		ActivityEditor editor = DebugUITestUtilities
		                                .openActivityEditorForSelectedElement();
        DebugUITestUtilities.setBreakpointAtLine(editor, 2);
        IBreakpointManager bpm = DebugPlugin.getDefault().getBreakpointManager();
        IBreakpoint[] bps = bpm.getBreakpoints();
        assertTrue("Breakpoint not set", bps.length == 1);
	}
}
