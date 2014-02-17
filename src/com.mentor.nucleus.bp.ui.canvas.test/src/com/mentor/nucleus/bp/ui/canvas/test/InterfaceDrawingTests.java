package com.mentor.nucleus.bp.ui.canvas.test;
//=====================================================================
//
//File:      InterfaceDrawingTests.java
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class InterfaceDrawingTests extends CanvasTest {

	public InterfaceDrawingTests(String name) {
		super(null, name);
	}

	private Package_c testPackage;
	private String test_id;
	private static boolean generate;

	@Override
	protected void initialSetup() throws Exception {
		super.initialSetup();
		loadProject("testInterfaceDrawing");
		testPackage = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Package");
			}
		});
	}
	
	public void testIntefaceDrawing() {
		test_id = "1";
		CanvasTestUtilities.openDiagramEditor(testPackage);
		GraphicalEditor editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		validateOrGenerateResults(editor, generate);
	}

	@Override
	protected String getResultName() {
		return "InterfaceDrawingTests" + "_"  + test_id;
	}
	
}
