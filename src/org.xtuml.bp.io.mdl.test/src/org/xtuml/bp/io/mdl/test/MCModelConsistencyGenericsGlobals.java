package org.xtuml.bp.io.mdl.test;

import java.io.File;
import java.io.FileFilter;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.xtuml.bp.core.EclipseOoaofooa;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.io.mdl.ImportModel;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.canvas.test.ModelRecreationTests;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

public class MCModelConsistencyGenericsGlobals extends BaseTest
{
	private static String testProjectName = "MCModelConsistencyGenericsGlobals";
	public static String StaticTestName = "testMCModelConsistency";
	public File testModel;
	public int modelNumber = 0;	
	public boolean testMCModelConsistencyPassed = false;
	private String test_id;
	
	public MCModelConsistencyGenericsGlobals(String name) {
		super(null, name);		
	}
	
	public void setUp() throws Exception {
		super.setUp();
		Ooaofooa.setPersistEnabled(true);
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
		if (getName().contains(StaticTestName)) {
			String msg = getName() + " #" + test_id + " with "
					+ testModel.getName();
			if (testMCModelConsistencyPassed) {
				msg = msg + " (passed)";
			} else {
				msg = "Error! " + msg + " (failed)";
			}
			System.out.println(msg);
		}
	}
	
    public void testMCModelConsistency() throws Exception
    {
    	testMCModelConsistencyPassed=false;
    	test_id = String.valueOf(modelNumber + 1);
		EclipseOoaofooa.setPersistEnabled(true);
		final String fileName = testModel.getName();
		this.loadProject(fileName);
		SystemModel_c system = SystemModel_c.SystemModelInstance(
				Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((SystemModel_c) candidate).getName().equals(
								fileName);
					}
				});
		BaseTest.dispatchEvents(0);

		modelRoot.checkDomainConsistency();
		
		// Graphics cannot be checked until Bugzilla 1156 has been
		// addressed.
		// OoaofgraphicsUtil.checkGraphicsConsistency(modelRoot.getId());
		
		testMCModelConsistencyPassed=true;
	}
	
}