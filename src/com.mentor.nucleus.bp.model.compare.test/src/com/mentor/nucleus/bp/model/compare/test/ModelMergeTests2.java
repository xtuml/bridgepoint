package com.mentor.nucleus.bp.model.compare.test;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CompareTestUtilities;
import com.mentor.nucleus.bp.test.common.GitUtil;
import com.mentor.nucleus.bp.test.common.ZipUtil;

public class ModelMergeTests2  extends BaseTest {
	private String test_repositories = Platform.getInstanceLocation().getURL()
			.getFile()
			+ "/" + "test_repositories";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mentor.nucleus.bp.test.common.BaseTest#initialSetup()
	 */
	@Override
	protected void initialSetup() throws Exception {
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointPreferencesStore.ENABLE_ERROR_FOR_EMPTY_SYNCHRONOUS_MESSAGE,
						false);
		String test_repository_location = System
				.getenv("XTUML_TEST_MODEL_REPOSITORY");
		if (test_repository_location == null
				|| test_repository_location.equals("")) {
			// use the default location
			test_repository_location = BaseTest.DEFAULT_XTUML_TEST_MODEL_REPOSITORY;
		}
		test_repository_location = new Path(test_repository_location)
				.removeLastSegments(1).toString();
		ZipUtil.unzipFileContents(
				test_repository_location + "/"
						+ "test_repositories"
						+ "/" + "236.zip",
				test_repositories);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		loadProject("HierarchyTestModel");
		project = getProjectHandle("HierarchyTestModel");
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.closeAllEditors(false);
	}

	public void testMergeofSortedElementsForIssue236() throws Exception {
		String projectName = "GPS Watch";
		// import git repository from models repo
		GitUtil.loadRepository(test_repositories
				+ "/" + projectName, "slave");
		// import test project
		GitUtil.loadProject(projectName, projectName);
		// merge the test branch
		GitUtil.mergeBranch("slave", projectName, "master");
		// start the merge tool
		GitUtil.startMergeTool(projectName);

		// Merge Pkg1 (6 incoming changes in master, 5 outgoing changes from slave)
		m_sys = getSystemModel(projectName);		
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Pkg1");
					}
				});
		
		
		CompareTestUtilities.copyAllNonConflictingChangesFromRightToLeft();
		// validate
		assertTrue("Found conflicting changes remaining.", CompareTestUtilities
				.getConflictingChanges().size() == 0);
		assertTrue("Found incoming changes remaining.", CompareTestUtilities
				.getIncomingChanges().size() == 0);

		CompareTestUtilities.flushMergeEditor();

		// TODO: Merge Library::Location::GPS  (4 incoming changes in master, 4 outgoing changes from slave)
		// TODO: Merge Library::Tracking::WorkoutTimer (1 incoming change in master, 1 outgoing change from slave)
		// TODO: Merge test1::iface1 (1 incoming change in master, 1 outgoing change from slave)
		// TODO: CompareWith... (just run compare with and open the editor for one of he changes, 
		

		// delete test project if no failures/errors
		// and reset the repository
		TestUtil.deleteProject(getProjectHandle(projectName));
		
		// There should be no error log entries (shutdown will verify this)
	}


}
