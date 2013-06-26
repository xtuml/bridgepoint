package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class CreationTransitionTest extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
	
	static String workspace_path = "";
    
	public CreationTransitionTest(String arg0) {
		super("com.mentor.nucleus.bp.ui.canvas.test", arg0);
	}

	protected String getResultName() {
		return "CreationTransitionTest" + "_" + test_id;
	}	

	protected void setUp() throws Exception {
		setModelName("CreationTransitionTestModel");
		super.setUp();
		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
		loadProject(getModelName());
	}

		
	protected void tearDown() throws Exception {
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
