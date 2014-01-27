package com.mentor.nucleus.bp.debug.test;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.model.BPProcess;
import com.mentor.nucleus.bp.debug.ui.model.BPStackFrame;
import com.mentor.nucleus.bp.debug.ui.model.BPValue;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

public class VerifierUDTAsUDTInitializationTests extends BaseTest {

    @Override
    protected void initialSetup() throws Exception {
        // disable auto build
        WorkspaceUtil.setAutobuilding(false);

        loadProject("VerifierUDTAsSDTTests");

        m_sys = getSystemModel("VerifierUDTAsSDTTests");
        m_sys.getPersistableComponent().loadComponentAndChildren(
                new NullProgressMonitor());
    }

    @Override
	protected void tearDown() throws Exception {
	}

	public void testUDTAsSDTIntialization() throws DebugException {
        Function_c function = Function_c
                .getOneS_SYNCOnR8001(PackageableElement_c
                        .getManyPE_PEsOnR8000(Package_c
                                .getManyEP_PKGsOnR1405(m_sys)));
        Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys);
        CanvasTestUtils.openActivityEditor(function);
        ActivityEditor editor = (ActivityEditor) PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        DebugUITestUtilities.setBreakpointAtLine(editor, 2);
        Selection.getInstance().setSelection(new StructuredSelection(m_sys));
        openPerspectiveAndView(
                "com.mentor.nucleus.bp.debug.ui.DebugPerspective",
                BridgePointPerspective.ID_MGC_BP_EXPLORER);
        Selection.getInstance().setSelection(new StructuredSelection(pkg));
        Menu menu = m_bp_tree.getControl().getMenu();
        assertTrue(
                "The Launch Verifier action was not present for a component.",
                UITestingUtilities.checkItemStatusInContextMenu(menu,
                        "Launch Verifier", "", false));
        MenuItem launchVerifierItem = DebugUITestUtilities
                .getLaunchVerifierItem(menu);
        assertNotNull(launchVerifierItem);
        TestUtil.debugToDialog(200);
        launchVerifierItem.notifyListeners(SWT.Selection, null);
        TestingUtilities.processDisplayEvents();

        menu = m_bp_tree.getControl().getMenu();
        assertFalse(
                "The Launch Verifier action was present for an unassigned imported component.",
                UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));
        
        // execute the funtion
		BPDebugUtils.executeElement(function);
		
        DebugUITestUtilities.waitForExecution();

        DebugUITestUtilities.waitForBPThreads(m_sys);
        DebugUITestUtilities.waitForExecution();
        

        ComponentInstance_c inst = ComponentInstance_c
                .ComponentInstanceInstance(function.getModelRoot());
        BPProcess process = (BPProcess) DebugUITestUtilities
                .getProcessForEngine(inst);
        BPStackFrame frame = (BPStackFrame) process.getDebugTarget()
                .getThreads()[0].getTopStackFrame();
        IVariable[] variables = frame.getVariables();
        boolean foundVar = false;
        for (int i = 0; i < variables.length; i++) {
            if (variables[i].getValue() != null && variables[i].getValue() instanceof BPValue) {
                BPValue value = (BPValue) variables[i].getValue();
                IVariable[] valVars = value.getVariables();
                for(int j = 0; j < valVars.length; j++) {
                    if(valVars[j].getValue().getValueString().equals("0")) {
                        foundVar = true;
                        break;
                    }
                }
                if(foundVar) {
                    break;
                }
            }
        }
        assertTrue("UDT as SDT was not properly intialized.", foundVar);
    }

}
