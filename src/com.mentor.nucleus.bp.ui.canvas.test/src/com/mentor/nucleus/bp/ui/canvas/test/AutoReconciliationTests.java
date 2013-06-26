//=====================================================================
//
//File:      $RCSfile: AutoReconciliationTests.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2007-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class AutoReconciliationTests extends CanvasTest {
	
	private String test_id;
	public static boolean generateResults = false;

	public AutoReconciliationTests(String arg0) {
		super(arg0);
	}

	@Override
	protected String getResultName() {
		return "AutoReconciliationTests" + "_" + test_id;
	}

	public void testGraphicalRepresentsSet() throws Exception {
		test_id = "1";
		
		// test that the graphical represents
		// is set prior to auto reconciliation
		// if it is not the diagram at the end
		// of this test will be missing some
		// graphics

		loadProject("AutoReconcilerTestModel");
		Ooaofooa testRoot = Ooaofooa.getInstance(
				"/AutoReconcilerTestModel/models/AutoReconcilerTestModel/Vehicle Design/Vehicle Design.xtuml", true);
		Package_c cp = Package_c.PackageInstance(testRoot);
		assertNotNull(cp);
		CanvasUtilities.openCanvasEditor(cp);
		
		TestingUtilities.allowJobCompletion();
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		TransactionManager tm = getSystemModel("AutoReconcilerTestModel").getTransactionManager();
		Transaction transaction = null;
		
		try {
			transaction = tm.startTransaction("Test Disposal", Ooaofooa.getDefaultInstance());
			cp.Dispose();
			tm.endTransaction(transaction);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		// we must wait for the reconciler transaction
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		
		testRoot = Ooaofooa.getInstance(
				"/AutoReconcilerTestModel/models/AutoReconcilerTestModel/Vehicle Components/Vehicle Components.xtuml", true);
		graphicsModelRoot = Ooaofgraphics.getInstance(testRoot.getId());
		
		cp = Package_c.PackageInstance(testRoot);
		assertNotNull(cp);
		CanvasUtilities.openCanvasEditor(cp);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		
		validateOrGenerateResults(ce, generateResults );
	}
	
}
