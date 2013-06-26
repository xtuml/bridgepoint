package com.mentor.nucleus.bp.io.mdl.test;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.test.TransitionActionTestGenerics;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class TransitionActionPersistenceTests extends CanvasTest {
	
	private String test_id;
	public static boolean generateResults = false;

	public TransitionActionPersistenceTests(String arg0) {
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
      IProject testProject = TestingUtilities.createProject("TransitionActionTest");
      File sourceProject = new File(m_workspace_path + "../TransitionActionTest");
      CorePlugin.disableParseAllOnResourceChange();
      TestingUtilities.copyProjectContents(sourceProject, testProject);
      TestingUtilities.allowJobCompletion();
      try {
	    testProject.close(new NullProgressMonitor());
	    testProject.open(new NullProgressMonitor());
      } catch (CoreException e) {
	    fail(e.toString());
      }
      BaseTest.waitForJobs();
	}
    public void testISMTransitionActionPersistence() {
		test_id = "1";
		String modelPath = "/TransitionActionTest/models/TransitionActionTest/";
        modelRoot = TransitionActionTestGenerics.getInitializedTestModelRoot(
        		                                             modelPath +
        		                        "Transition Action Test Package/" +
                                        "Transition Action Test Package.xtuml");
		
        InstanceStateMachine_c uut = InstanceStateMachine_c.
                                        InstanceStateMachineInstance(modelRoot);
        assertNotNull(uut);
        CanvasUtilities.openCanvasEditor(uut);
        TransitionActionTestGenerics.handleCreationTransitionTest(uut);
        TransitionActionTestGenerics.handleTransitionTest(uut, "Test State A",
                                                                "Test State B");
    	String ISMPath = modelPath + "Transition Action Test Package/" +
    	       "Transition Action Test Component/Test Transition Actions/" +
                                  "Transition Action Test/InstanceStateMachine";
        IPath actualPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
        actualPath = actualPath.append(ISMPath);
        actualPath = actualPath.append("InstanceStateMachine." +
        		                                           Ooaofooa.MODELS_EXT);
        if (!generateResults) {
            TestingUtilities.fileContentsCompare( 
                m_workspace_path + "expected_results/" +
                "testTransitionActionPersistence/InstanceStateMachine." +
                                                            Ooaofooa.MODELS_EXT,
                                                         actualPath.toString());
        }
        else {
        	// TODO copy actual file above to expected.
        }
        TransitionActionTestGenerics.handleTearDown();
    }
    public void testCSMTransitionActionPersistence() {
		test_id = "2";
		String modelPath = "/TransitionActionTest/models/TransitionActionTest/";
        modelRoot = TransitionActionTestGenerics.getInitializedTestModelRoot(
        		                                             modelPath +
        		                        "Transition Action Test Package/" +
                                        "Transition Action Test Package.xtuml");
		
        ClassStateMachine_c uut = ClassStateMachine_c.
                                           ClassStateMachineInstance(modelRoot);
        assertNotNull(uut);
        CanvasUtilities.openCanvasEditor(uut);
        TransitionActionTestGenerics.handleTransitionTest(uut, "Test State D",
                                                                "Test State E");
    	String ISMPath = modelPath + "Transition Action Test Package/" +
    	       "Transition Action Test Component/Test Transition Actions/" +
                                     "Transition Action Test/ClassStateMachine";
        IPath actualPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
        actualPath = actualPath.append(ISMPath);
        actualPath = actualPath.append("ClassStateMachine." +
        		                                           Ooaofooa.MODELS_EXT);
        if (!generateResults) {
            TestingUtilities.fileContentsCompare( 
                m_workspace_path + "expected_results/" +
                "testTransitionActionPersistence/ClassStateMachine." +
                                                            Ooaofooa.MODELS_EXT,
                                                         actualPath.toString());
        }
        else {
        	// TODO copy actual file above to expected.
        }
        TransitionActionTestGenerics.handleTearDown();
    }
	protected void tearDown() throws Exception {
		super.tearDown();
        IPath in_path = new Path(m_logfile_path);
        File in_fh = in_path.toFile();
        if ( in_fh.exists() )
        {
            fail( ".log file is not empty"); //$NON-NLS-1$
        }
	}
	
	@Override
	protected String getResultName() {
		return "TransitionActionPersistenceTest" + "_" + test_id;
	}

}
