package org.xtuml.bp.ui.canvas.test;

import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

@RunWith(OrderedRunner.class)
public class CreationTransitionTest extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
	
	static String workspace_path = "";
    
	public CreationTransitionTest() {
		super("org.xtuml.bp.ui.canvas.test", null);
	}

	protected String getResultName() {
		return "CreationTransitionTest" + "_" + test_id;
	}	

	@Before
	public void setUp() throws Exception {
		setModelName("CreationTransitionTestModel");
		super.setUp();
		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
		loadProject(getModelName());
	}

		
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public class InstanceStateMachine_by_name_c implements ClassQueryInterface_c {
	public boolean evaluate(Object candidate) {
		InstanceStateMachine_c selected = (InstanceStateMachine_c) candidate;
		return (selected.Get_name().equals(m_name));
	}
	public InstanceStateMachine_by_name_c(String name) {
		m_name = name;		
	}
	private String m_name;
	}
	

	public void openTestSMDiagram(String title) {
		CanvasTestUtils ctu = new CanvasTestUtils();
		InstanceStateMachine_c uut = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
		CanvasTestUtils.openCanvasEditor(uut);
		BaseTest.dispatchEvents(0);
	}
	
	public void setGenerateResults() {
		try {
			generateResults = true;
			this.setUp();
  			this.testDrawCreationTransition();
		} catch (Exception e) {
			System.out.println(
				"Exception encountered by test result creator: " + e);
		}

	}
	
	@Test
	public void testDrawCreationTransition() {
  		test_id = "test_1";
  		openTestSMDiagram("TestState1");
  		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
  		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot, "TestState1");
  		AbstractTool tool = UITestingUtilities.getTool("Creation Transition");
  		UITestingUtilities.activateTool(tool);
  		Point center = CanvasTestUtils.getShapeCenter(shp);
  		Point mouse = CanvasTestUtils.convertToMouseCoor(center, ce.getModel());  
  		CanvasTestUtils.createMouseEvent((mouse.x - 200), (mouse.y - 200), "MouseDown");
  		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y, "MouseMove");
  		CanvasTestUtils.createMouseEvent(mouse.x, mouse.y, "MouseUp");
  		
        validateOrGenerateResultsGenerics(ce, generateResults);
  	   	UITestingUtilities.deactivateTool(tool);
  	
  	}
    	

    
 
    
}
