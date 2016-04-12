package org.xtuml.bp.io.mdl.test;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.test.TransitionActionTestGenerics;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

@RunWith(OrderedRunner.class)
public class TransitionActionPersistenceTests extends CanvasTest {
	
	private String test_id;
	public static boolean generateResults = false;

	public TransitionActionPersistenceTests() {
		super(null);

		// turn off autobuild to stop MC-3020 builders from running
		try {
			WorkspaceUtil.setAutobuilding(false);
		} catch (CoreException e) {
			fail(e.toString());
		}
	}

	@Before
	public void setUp() throws Exception {
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
    @Test
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
                m_workspace_path + TestingUtilities.getExpectedResultsPath() +
                "testTransitionActionPersistence/InstanceStateMachine." +
                                                            Ooaofooa.MODELS_EXT,
                                                         actualPath.toString());
        }
        else {
        	// TODO copy actual file above to expected.
        }
        TransitionActionTestGenerics.handleTearDown();
    }
    @Test
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
                m_workspace_path + TestingUtilities.getExpectedResultsPath() +
                "testTransitionActionPersistence/ClassStateMachine." +
                                                            Ooaofooa.MODELS_EXT,
                                                         actualPath.toString());
        }
        else {
        	// TODO copy actual file above to expected.
        }
        TransitionActionTestGenerics.handleTearDown();
    }
	@After
	public void tearDown() throws Exception {
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
