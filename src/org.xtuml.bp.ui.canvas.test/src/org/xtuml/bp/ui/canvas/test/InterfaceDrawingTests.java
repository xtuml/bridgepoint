package org.xtuml.bp.ui.canvas.test;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//=====================================================================
//
//File:      InterfaceDrawingTests.java
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

@RunWith(OrderedRunner.class)
public class InterfaceDrawingTests extends CanvasTest {

	public InterfaceDrawingTests() {
		super(null, null);
	}

	private Package_c testPackage;
	private String test_id;
	private static boolean generate;

	private static boolean isFirstTime = true;
	@Override
//	@Before
	public void initialSetup() throws Exception {
		if (!isFirstTime)
			return;
		isFirstTime = false;
		super.initialSetup();
		loadProject("testInterfaceDrawing");
		testPackage = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Package");
			}
		});
	}
	
	@Test
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
