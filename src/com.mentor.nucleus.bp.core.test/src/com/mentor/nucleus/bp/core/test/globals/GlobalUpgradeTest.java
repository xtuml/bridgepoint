package com.mentor.nucleus.bp.core.test.globals;

import java.io.File;

import org.eclipse.core.resources.IProject;

import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class GlobalUpgradeTest extends BaseTest {

    String testModels[] = {"AssignClassTests", "AssignRemoveEvents",
    		               "AttributeMenuItemTests", "blankTest",
    		               "CantHappenEvtIgnoteEvtTests"};

    public void testUpgrade_RuntimeAuditTest_ToGlobalsOnSPModel() {
      String projectName = "Runtime Audit Test";
      performUpgradeTest(projectName);
    }

    public void testUpgrade_AutoReconcilerTestModel_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_BreakpointTest_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_ConnectorMoveTests_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_ConnectorPolicyTestModel_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_CopyPasteTestModel_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_CPSDT_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_CVSTestProject_ToGlobalsOnSPModel() {
        String projectName = "CVS Test Project";
        performUpgradeTest(projectName);
      }

    public void testUpgrade_DatatypeTest_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_destination_SystemModel_c_ToGlobalsOnSPModel() {
        String projectName = "destination_SystemModel_c";
        performUpgradeTest(projectName);
      }

    public void testUpgrade_Dissatisfaction_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_EventLabelTest_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testfla_test_ToGlobalsOnSPModel() {
        String projectName = "fla_test";
        performUpgradeTest(projectName);
      }

    public void testUpgrade_GraphicalAnchorTests_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_InstanceReferenceTestMatrixModel_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_InstRefsInVerifier_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_Looper_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_NamespaceTest_ToGlobalsOnSPModel() {
        String projectName = "Namespace Test";
        performUpgradeTest(projectName);
      }

    public void testUpgrade_RTOMoveTests_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_SatisfactionDragDrop_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_testMessageDirectionIndicationOnPort_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_testSignalAssignmentCCP_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_testSignalAssignmentCCPDestination_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_testVisibilityFilterInElementChooser_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_TransitionActionTest_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_VerifierInterfaceExecutionTests_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_VerifierLaunchConfigurationTests_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_VerifierMessageTest_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

    public void testUpgrade_VerifierTransitionActionTest_ToGlobalsOnSPModel() {
        String projectName = getName().split("_")[1];
        performUpgradeTest(projectName);
      }

	private void performUpgradeTest(String projectName) {
		try {
		    loadProjectInternal(projectName);
		  }
		  catch (Exception e) {
			  fail("Exception setting up Global upgrade test, " + projectName + "\n" + e.toString());
		  }
		  // Project upgrade is now done automatically
	}

    public void testUpgradeToGlobalsOnGPModel() {
    	
    }

    public void testUpgradeToGPsOnGlobalDTModel() {
    	
    }

    private void loadProjectInternal(String projectName) throws Exception {
        // Turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);

        // Create the project contents
		IProject testProject = TestingUtilities.createProject(projectName);
		File sourceProject = new File(m_workspace_path + "../" + projectName);
		TestingUtilities.copyProjectContents(sourceProject, testProject);
		TestingUtilities.allowJobCompletion();
    }
}
