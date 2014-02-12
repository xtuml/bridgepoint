//=====================================================================
//
//File:      $RCSfile: DeleteDatatypesTestGenerics.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/05/10 04:30:25 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTest;

/**
 * Performs tests that involve deleting datatypes defined in system-level
 * packages and verifying the places that use these datatypes are reverted back
 * to integer and void appropriately.
 */
public class DeleteDatatypesTestGenerics extends CanvasTest {
	private static Selection selection = Selection.getInstance();
	private static String projectName = "DatatypeTest";
    private String test_id;
    private static Ooaofooa testRoot;

    public DeleteDatatypesTestGenerics(String arg0) {
        super(null, arg0);
    }

    @Override
    protected String getResultName() {
        return "DeleteDatatypesTest" + "_" + test_id;
    }

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 *
	 * Since we are deleting elements out of the model with each test, we
	 * reload the model for each test and delete it after each test.
	 */
	protected void setUp() throws Exception {
		super.setUp();
        // Turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);
		
        // Create the project contents
		loadProject("DatatypeTest");

        m_sys = getSystemModel("DatatypeTest");

        testRoot = modelRoot;
        TransactionManager.getSingleton().disableDialog = true;
	}

	protected void tearDown() throws Exception {
		try {
			TestingUtilities.deleteProject(projectName);
			TransactionManager.getSingleton().disableDialog = false;
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		super.tearDown();
	}

	/**
	 * This test suite deletes a UDT that is assigned to a function parameter
	 * and return value and verifies the datatype is set back to integer and
	 * void respectively. The datatype is buried deep in a package tree and the
	 * following tests delete at various points in the tree. This function then
	 * checks that the param and return value dt's are reverted appropriately.
	 */
	private void checkDatatypesReverted() {
        Operation_c op = Operation_c.OperationInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Operation_c) candidate).getName()
								.equals("op1");
					}
				});
		assertNotNull(op);

		OperationParameter_c param = OperationParameter_c
				.getOneO_TPARMOnR117(op);
		assertNotNull(param);
		assertTrue("Did not find the expected parameter.", param.getName()
				.equals("p"));

		DataType_c op_dt = DataType_c.getOneS_DTOnR116(op);
		assertNotNull(op_dt);
		assertTrue("Return value datatype not reverted to \"void\".", op_dt
				.getName().equals("void"));

		DataType_c param_dt = DataType_c.getOneS_DTOnR118(param);
		assertNotNull(param_dt);
		assertTrue("Parameter datatype not reverted to \"integer\".", param_dt
				.getName().equals("integer"));
	}

	/**
	 * Here is the tree to the UDT:
	 *   System Model
	 *     "p" - Generic package
	 *       "q" - Generic package
	 *         "dtp1" - Datatype package
	 *           "dtp2" - Datatype package
	 *             "udt1" - UDT
	 *
	 * The following group of tests deletes at the various points in the
	 * heirarchy.
	 */
	public void testDeletionOfUDT1() {
        test_id = "1";

        // We have to get the dtp2 package to force some loading to happen.
        // If we skip this, we can't find the udt in the next step.
        Package_c dtp2 = Package_c.PackageInstance(
                testRoot, new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate) {
                        return ((Package_c) candidate).getName()
                                .equals("dtp2");
                    }
                });
        assertNotNull(dtp2);

		UserDataType_c udt = UserDataType_c.UserDataTypeInstance(testRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((UserDataType_c) candidate).getName().equals(
								"udt1");
					}
				});
		assertNotNull(udt);

		Cl_c.Clearselection();
		selection.addToSelection(udt);
		new DeleteAction(null).run();

		checkDatatypesReverted();
	}

	public void testDeletionOfUDT1ByDeletingDtp2() {
        test_id = "2";
		Package_c dtp2 = Package_c.PackageInstance(
				testRoot, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName()
								.equals("dtp2");
					}
				});
		assertNotNull(dtp2);

		Cl_c.Clearselection();
		selection.addToSelection(dtp2);
		new DeleteAction(null).run();

		checkDatatypesReverted();
	}

	public void testDeletionOfUDTByDeletingDtp1() {
        test_id = "3";
		Package_c dtp1 = Package_c.PackageInstance(
				testRoot, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName()
								.equals("dtp1");
					}
				});
		assertNotNull(dtp1);

		Cl_c.Clearselection();
		selection.addToSelection(dtp1);
		new DeleteAction(null).run();

		checkDatatypesReverted();
	}

	public void testDeletionOfUDTByDeletingPkgQ() {
        test_id = "4";

        Package_c q = Package_c.PackageInstance(testRoot,
                new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate) {
                        return ((Package_c) candidate).getName().equals("q");
                    }
                });
		assertNotNull(q);

		Cl_c.Clearselection();
		selection.addToSelection(q);
		new DeleteAction(null).run();

		checkDatatypesReverted();
	}

	public void testDeletionOfUDTByDeletingPkgP() {
        test_id = "5";

        Package_c p = Package_c.PackageInstance(testRoot,
                new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate) {
                        return ((Package_c) candidate).getName().equals("p");
                    }
                });
		assertNotNull(p);

		Cl_c.Clearselection();
		selection.addToSelection(p);
		new DeleteAction(null).run();

		checkDatatypesReverted();
	}
	
	public void testDeletionOfUDTAssignedToUDT() {
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create test elements",
					new ModelElement[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			m_sys.Newpackage();
			Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
			Package_c testPkg = pkgs[pkgs.length - 1];
			testPkg.Newdatatype();
			DataType_c[] dts = DataType_c
					.getManyS_DTsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(testPkg));
			DataType_c toDelete = dts[dts.length - 1];
			testPkg.Newdatatype();
			dts = DataType_c.getManyS_DTsOnR8001(PackageableElement_c
					.getManyPE_PEsOnR8000(testPkg));
			DataType_c assignedTo = dts[dts.length - 1];
			UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(assignedTo);
			udt.relateAcrossR18To(toDelete);
			toDelete.Dispose();
			DataType_c assignedType = DataType_c.getOneS_DTOnR18(udt);
			assertTrue(
					"User data type was not reverted to default on deletion of base type.",
					assignedType.getName().equals("integer"));
			TransactionManager.getSingleton().disableDialog = true;
			TransactionManager.getSingleton().endTransaction(transaction);
			TransactionManager.getSingleton().disableDialog = false;
		} catch (TransactionException e) {
			if (transaction != null) {
				TransactionManager.getSingleton().cancelTransaction(
						transaction, e);
			}
			TransactionManager.getSingleton().disableDialog = false;
			fail(e.getMessage());
		}
	}
}
