package org.xtuml.bp.io.mdl.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xtuml.bp.core.EclipseOoaofooa;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.test.ModelRecreationTests;

//@RunWith(OrderedRunner.class)
@RunWith(Parameterized.class)
public class MCModelConsistencyGenericsGlobals extends BaseTest
{
	@Rule public TestName name = new TestName();
	
	private static String testProjectName = "MCModelConsistencyGenericsGlobals";
	public static String StaticTestName = "testMCModelConsistency";
	public File testModel;
	public int modelNumber = 0;	
	public boolean testMCModelConsistencyPassed = false;
	private String test_id;
	
	
//	class data{
//		File testModel;
//		int 
//	}
	
    @Parameters
    public static Collection<Object[]> data() {
    	File[] testModels = ModelRecreationTests.getTestModelNames();
    	int modelCount = testModels.length;
    	Object[][] data =new Object[modelCount][2]; 
    	
    	for (int i = 0; i < testModels.length; i++) {
    		data[i][0] = testModels[i];
    		data[i][1] = new Integer(i);
		}
        return Arrays.asList(data);
    }
	
	public MCModelConsistencyGenericsGlobals(Object file, Object testNo) {
		super(null, null);
		testModel = (File)file;
		modelNumber = ((Integer)testNo).intValue();
		
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		Ooaofooa.setPersistEnabled(true);
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		if (name.getMethodName().contains(StaticTestName)) {
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
	
    @Test
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