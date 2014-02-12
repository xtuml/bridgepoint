//=====================================================================
//
//File:      $RCSfile: ShapeToolSCTest.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/01/10 22:43:49 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.ui.canvas.test;


/**
 * Performs automated testing of the more interesting transitions between states
 * in the shape-tool statechart.
 * 
 * Note that the tests contained herein are meant to be run sequentially in the order of their
 * declaration, each one using as its initial state the final state of the previous test. 
 */
public class ShapeToolSCTest extends StatechartTest
{
//	/**
//	 * The shape-tool exercised during these tests.
//	 */
//	private static ShapeTool_c shapeTool;
//
//	/**
//	 * The shape model-tool exercised during these tests.
//	 */
//	private static ModelTool_c shapeModelTool;
//
//	/**
//	 * The "Class" element-specification, which in these tests is related to the 
//	 * above shape-tool.
//	 */
//	private static ElementSpecification_c classSpecification;
//
//	/**
//	 * This value is reused across several of these tests,
//	 * particularly, the ones that create new shapes.
//	 */
//	private static Point newShapeLocation;
//	
	/**
	 * Constructor
	 */
	public ShapeToolSCTest(String arg0)
	{
		super(arg0);
	}
//
//	/**
//	 * See parent method overridden.
//	 */
//	public void setUp() throws Exception
//	{
//		super.setUp();
//		
//		// if we haven't yet found the shape tool used during these tests
//		if (shapeTool == null) {
//		    // do so
//			shapeTool = CanvasTestUtils.getShapeTool(graphicsModelRoot, "Class");
//			shapeModelTool = ModelTool_c.getOneCT_MTLOnR102(shapeTool);
//		}
//	}
//	
//	/**
//	 * This fills in the methods of the parent class where we know the code applies across
//	 * all the tests of this class.
//	 */
//	private abstract class Inputs implements StatechartTest.Inputs
//	{
//		public int getToolCurrentState() {
//			return shapeTool.get_current_state();
//		}
//		public void cleanup() {
//		}
//	}
//
//	public void test_108CT_STL5InIdle() throws Exception
//	{
//		performTest("test_108", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						CanvasTestUtils.controlShapeToolState(
//							graphicsModelRoot, shapeTool, "activate");
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_WAITING_FOR_INITIAL_POSITION);
//	}
//
//	public void test_109CT_STL6InWaitingForInitialPosition() throws Exception
//	{
//		performTest("test_109", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						CanvasTestUtils.controlShapeToolState(
//								graphicsModelRoot, shapeTool, "deactivate");
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_IDLE);
//	}
//
//	public void test_110CT_STL1InWaitingForInitialPosition() throws Exception
//	{
//		performTest("test_110", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						Point location = newShapeLocation = 
//					        doMouseMoveOverNWCornerOfDiskRequestClass();
//						location.x += 510;
//						location.y -= 200;
//						CanvasTestUtils.controlShapeToolState(
//								graphicsModelRoot, shapeTool, "activate");
//						CanvasTestUtils.doMousePress(location.x, location.y);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_INITIALIZING_SHAPE);
//	}
//
//	public void test_111CT_STL2InInitializingShape() throws Exception
//	{
//		performTest("test_111", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						CanvasTestUtils.doMouseRelease(newShapeLocation.x, newShapeLocation.y);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_WAITING_FOR_INITIAL_POSITION);
//	}
//
//	public void test_112CT_STL3InInitializingShape() throws Exception
//	{
//		performTest("test_112", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//					    Point location = newShapeLocation = 
//					        doMouseMoveOverNWCornerOfDiskRequestClass();
//					    location.x -= 500;
//						CanvasTestUtils.doMouseMove(location.x, location.y);
//						CanvasTestUtils.doMousePress(location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 50, location.y + 50);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_DRAGGING_SHAPE);
//	}
//
//	public void test_113CT_STL3InDraggingShape() throws Exception
//	{
//		performTest("test_113", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//					    Point location = newShapeLocation;
//						CanvasTestUtils.doMouseMove(
//						    location.x + 100, location.y + 100);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_DRAGGING_SHAPE);
//	}
//
//	public void test_113aCT_STL3InDraggingShapeVariableAspectRatio()
//			throws Exception
//	{
//		// remember the dimensions of the current selection rectangle, so we can test
//		// against them below 
//		int selectionWidth = canvas.getSelrectw();
//		int selectionHeight = canvas.getSelrecth();
//
//		// locate the "Class" element-specification and relate it to the shape model-tool 
//		classSpecification = ElementSpecification_c.ElementSpecificationInstance(
//				Ooaofgraphics.getDefaultInstance(), 
//				new ClassQueryInterface_c() {
//					public boolean evaluate(Object candidate) {
//						ElementSpecification_c selected = (ElementSpecification_c) candidate;
//						return selected.getName().equals("Class");
//					}
//				});
//		classSpecification.relateAcrossR103To(shapeModelTool);
//						
//		performTest("test_113a", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						classSpecification.setIsfixedaspectratio(false);
//					    Point location = newShapeLocation;
//						CanvasTestUtils.doMouseMove(
//						    location.x + 150, location.y + 125);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_DRAGGING_SHAPE);
//
//		// test that the current selection rectangle enlarged as expected
//		assertEquals(Ooaofgraphics.Roundtogridsnappoint(graphicsModelRoot, false, selectionWidth + 50), 
//			canvas.getSelrectw());
//		assertEquals(Ooaofgraphics.Roundtogridsnappoint(graphicsModelRoot, false, selectionHeight + 25), 
//		    canvas.getSelrecth());
//	}
//
//	public void test_113bCT_STL3InDraggingShapeFixedAspectRatio()
//			throws Exception
//	{
//		// remember the dimensions of the current selection rectangle, so we can test
//		// against them below 
//		int selectionWidth = canvas.getSelrectw();
//
//		performTest("test_113b", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						classSpecification.setIsfixedaspectratio(true);
//						classSpecification.setDefaultheight(50);
//						classSpecification.setDefaultwidth(50);
//					    Point location = newShapeLocation;
//						CanvasTestUtils.doMouseMove(
//						    location.x + 200, location.y + 150);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_DRAGGING_SHAPE);
//
//		// test that the current selection rectangle enlarged as expected
//		assertEquals(Ooaofgraphics.Roundtogridsnappoint(graphicsModelRoot, false, selectionWidth + 50), 
//		    canvas.getSelrectw());
//		
//		// test that the selection rectangle is still a square, since the 
//		// above test operated under a fixed aspect ratio
//		assertEquals(canvas.getSelrectw(), canvas.getSelrecth()); 
//	}
//
//	public void test_114CT_STL2InDraggingShape() throws Exception
//	{
//		performTest("test_114", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						classSpecification.setIsfixedaspectratio(false);
//					    Point location = newShapeLocation;
//						CanvasTestUtils.doMouseRelease(
//						    location.x + 200, location.y + 150);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_WAITING_FOR_INITIAL_POSITION);
//	}
//
//	public void test_114aCT_STL2InDraggingShapeBackwards() throws Exception
//	{
//		performTest("test_114a", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//					    Point location = newShapeLocation;
//					    location.x += 500;
//					    location.y += 500;
//						CanvasTestUtils.doMousePress(location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x - 100, location.y - 100);
//						CanvasTestUtils.doMouseRelease(
//						    location.x - 100, location.y - 100);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_WAITING_FOR_INITIAL_POSITION);
//	}
//
//	public void test_114bCT_STL2InDraggingShapeNoElement() throws Exception
//	{
//        classSpecification.unrelateAcrossR103From(shapeModelTool);
//		performTest("test_114b", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//					    Point location = newShapeLocation;
//						CanvasTestUtils.doMousePress(location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 100, location.y + 100);
//						CanvasTestUtils.doMouseRelease(
//						    location.x + 100, location.y + 100);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_WAITING_FOR_INITIAL_POSITION);
//	}
//	public void test_ShapeSizeSmallerThanAllowed() throws Exception
//	{
//		classSpecification.relateAcrossR103To(shapeModelTool);
//		classSpecification.setDefaultheight(0);
//		classSpecification.setDefaultwidth(0);
//		performTest("test_115", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						Point location = newShapeLocation = 
//					        doMouseMoveOverNWCornerOfDiskRequestClass();
//						location.x += 720;
//						location.y -= 200;
//						CanvasTestUtils.doMouseMove(location.x, location.y);
//						CanvasTestUtils.doMousePress(location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 2, location.y + 2);
//						CanvasTestUtils.doMouseRelease(location.x + 2, location.y + 2);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_WAITING_FOR_INITIAL_POSITION);
//	}
//	public void test_UndoShapeDoesNotLeaveSelectionRectangle() throws Exception
//	{
//		performTest("test_116", //$NON_NLS-1$
//				new Inputs() {
//					public void perform() {
//						Point location = newShapeLocation = 
//					        doMouseMoveOverNWCornerOfDiskRequestClass();
//						location.x += 930;
//						location.y -= 200;
//						CanvasTestUtils.doMouseMove(location.x, location.y);
//						CanvasTestUtils.doMousePress(location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 10, location.y + 10);
//						CanvasTestUtils.doMouseRelease(location.x + 10, location.y + 10);
//						Action undoAction = getSystemModel("com.mentor.nucleus.bp.ui.canvas.test").getTransactionManager().getUndoAction();
//						undoAction.run();
//						CanvasEditor editor = (CanvasEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//						assertTrue("SelRectW not restored to zero.", editor.ptCanvas.getSelrectw() == 0);
//						assertTrue("SelRectH not restored to zero.", editor.ptCanvas.getSelrecth() == 0);
//					}
//				},
//				ShapeTool_c.ST_SHAPE_TOOL_WAITING_FOR_INITIAL_POSITION);
//	}
}	
