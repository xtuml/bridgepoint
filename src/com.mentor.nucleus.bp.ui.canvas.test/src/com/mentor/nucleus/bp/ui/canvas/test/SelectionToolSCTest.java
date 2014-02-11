//=====================================================================
//
//File:      $RCSfile: SelectionToolSCTest.java,v $
//Version:   $Revision: 1.12 $
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
 * in the selection-tool statechart.
 * 
 * Note that the tests contained herein are meant to be run sequentially in the order of their
 * declaration, each one using as its initial state the final state of the previous test. 
 */
public class SelectionToolSCTest extends StatechartTest
{
	/**
	 * Constructor
	 */
	public SelectionToolSCTest(String arg0)
	{
		super(arg0);
	}

//	/**
//	 * This fills in the methods of the parent class where we know the code applies across
//	 * all the tests of this class.
//	 */
//	private abstract class Inputs implements StatechartTest.Inputs
//	{
//		public int getToolCurrentState() {
//			return SelectionTool_c.SelectionToolInstance(graphicsModelRoot)
//				.get_current_state();
//		}
//
//		public void cleanup() {}
//	}
//
//	public void test_1CT_SEL9InCheckingMousePress() throws Exception
//	{
//		performTest("test_1", 
//				new Inputs() {
//					public void perform() {
//						doMousePressOnDiskRequestClass();
//					}
//					public void cleanup() {
//						doMouseReleaseOnDiskRequestClass();
//						canvas.Unselectall();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_SELECTING_SYMBOL);
//	}
//
//	public void test_2CT_SEL8InCheckingMousePress() throws Exception
//	{
//		performTest("test_2", 
//				new Inputs() {
//					public void perform() {
//						doMousePressOnVacantArea();
//					}
//					public void cleanup() {
//						doMouseReleaseOnVacantArea();
//						canvas.Unselectall();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_PREPARING_SELECTION_RECTANGLE);
//	}
//	
//	public void test_3CT_SEL12InCheckingMousePress() throws Exception
//	{
//		performTest("test_3", 
//				new Inputs() {
//					public void perform() {
//						doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//						doMousePressOnDiskRequestClass(); 
//					}
//					public void cleanup() {
//						doMouseReleaseOnDiskRequestClass();
//						canvas.Unselectall();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_PICKING_UP_SELECTED_SYMBOLS);
//	}
//
//	public void test_4CT_SEL4InPreparingSelectionRectangle() throws Exception
//	{
//		performTest("test_4", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//						location = doMousePressOnVacantArea();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 30, location.y + 30);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseRelease(
//						    location.x + 30, location.y + 30);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_DRAGGING_SELECTION_RECTANGLE);
//	}
//
//	public void test_5CT_SEL3InPreparingSelectionRectangle() throws Exception
//	{
//		performTest("test_5", 
//				new Inputs() {
//					public void perform() {
//						doMousePressOnVacantArea();
//						doMouseReleaseOnVacantArea();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_6CT_SEL4InSelectingSymbol() throws Exception
//	{
//		performTest("test_6", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//					    CanvasTestUtils.doMouseMove(
//					        location.x + 10, location.y + 10);
//					}
//					public void cleanup() {
//					    CanvasTestUtils.doMouseMove(
//					        location.x, location.y);
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_DRAGGING_SYMBOLS);
//	}
//
//	public void test_7CT_SEL3InSelectingSymbol() throws Exception
//	{
//		performTest("test_7", 
//				new Inputs() {
//					public void perform() {
//						doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_8CT_SEL4InDraggingSelectionRectangle() throws Exception
//	{
//		performTest("test_8", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//			    		location = doMousePressOnVacantArea();
//					    CanvasTestUtils.doMouseMove(
//					        location.x + 10, location.y + 10);
//					    CanvasTestUtils.doMouseMove(
//					        location.x + 20, location.y + 20);
//					}
//					public void cleanup() {
//					    CanvasTestUtils.doMouseRelease(
//					        location.x + 20, location.y + 20);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_DRAGGING_SELECTION_RECTANGLE);
//	}
//
//	public void test_9CT_SEL3InDraggingSelectionRectangle() throws Exception
//	{
//		performTest("test_9", 
//				new Inputs() {
//					public void perform() {
//					    Point location = doMousePressOnVacantArea();
//					    CanvasTestUtils.doMouseMove(
//					        location.x + 10, location.y + 10);
//					    CanvasTestUtils.doMouseRelease(
//					        location.x + 10, location.y + 10);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_10CT_SEL4InDraggingSymbols() throws Exception
//	{
//		performTest("test_10", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 10, location.y + 10);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 20, location.y + 20);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_DRAGGING_SYMBOLS);
//	}
//
//	public void test_11CT_SEL3InDraggingSymbols() throws Exception
//	{
//		performTest("test_11", 
//				new Inputs() {
//					public void perform() {
//					    Point location = doMousePressOnDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 10, location.y + 10);
//						CanvasTestUtils.doMouseRelease(
//						    location.x + 10, location.y + 10);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_12CT_SEL6InTogglingSymbolsCaughtBySelectionRectangle()
//			throws Exception
//	{
//		performTest("test_12", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x - 40, location.y - 40);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x - 50, location.y - 60);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_13CT_SEL4InIdle() throws Exception
//	{
//		performTest("test_13", 
//				new Inputs() {
//					public void perform() {
//					    Point location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x - 40, location.y - 70);
//					}
//					public void cleanup() {
//						canvas.Unselectall();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_14CT_SEL2InIdle() throws Exception
//	{
//		performTest("test_14", 
//				new Inputs() {
//					public void perform() {
//						canvas.Unselectall();
//						doMousePressOnDiskRequestClass();
//					}
//					public void cleanup() {
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_SELECTING_SYMBOL);
//	}
//
//	public void test_15CT_SEL5InIdle() throws Exception
//	{
//		performTest("test_15", 
//				new Inputs() {
//					public void perform() {
//						doMouseContextPressOnVacantArea();
//					}
//					public void cleanup() {
//					    doMouseReleaseOnVacantArea();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_OPENING_CONTEXT_MENU);
//	}
//
//	public void test_17CT_SEL4InPickingUpSelectedSymbols() throws Exception
//	{
//		performTest("test_17", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//						doMousePressOnDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 10, location.y + 10);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_DRAGGING_SYMBOLS);
//	}
//
//	public void test_18CT_SEL3InPickingUpSelectedSymbols() throws Exception
//	{
//		performTest("test_18", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//						doMousePressOnDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 10, location.y + 10);
//						CanvasTestUtils.doMouseRelease(
//						    location.x + 10, location.y + 10);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMousePress(
//						    location.x + 10, location.y + 10);
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_19CT_SEL3InOpeningContextMenu() throws Exception
//	{
//		performTest("test_19", 
//				new Inputs() {
//					public void perform() {
//						doMouseContextPressOnVacantArea();
//						doMouseReleaseOnVacantArea();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_26CT_SEL3InAwaitingMouseRelease() throws Exception
//	{
//		performTest("test_26", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//						canvas.SpacePressed(false);
//						location = doMousePressOnVacantArea();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 20, location.y + 20);
//						canvas.SpaceReleased(false);
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//					}
//					public void cleanup() {
//						canvas.SpacePressed(false);
//						doMousePressOnVacantArea();
//						CanvasTestUtils.doMouseMove(
//						    location.x - 20, location.y - 20);
//						canvas.SpaceReleased(false);
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_IDLE);
//	}
//
//	public void test_30CT_SEL19InCheckingForHotspots() throws Exception
//	{
//		performTest("test_30", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//					}
//					public void cleanup() {
//						canvas.Unselectall();
//						CanvasTestUtils.doMouseMove(
//						    location.x - 10, location.y);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_WAITING_FOR_MOUSE_PRESS_OVER_HOTSPOT);
//	}
//
//	public void test_31CT_SEL4InWaitingForMousePressOverHotspot()
//			throws Exception
//	{
//		performTest("test_31", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 1, location.y + 1);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x - 20, location.y - 20);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_WAITING_FOR_MOUSE_PRESS_OVER_HOTSPOT);
//	}
//
//	public void test_32CT_SEL2InWaitingForMousePressOverHotspot()
//			throws Exception
//	{
//		performTest("test_32", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.doMousePress(
//						    location.x, location.y);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x - 20, location.y - 20);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_WAITING_TO_MOVE_HOTSPOT);
//	}
//
//	public void test_33CT_SEL5InWaitingForMousePressOverHotspot()
//			throws Exception
//	{
//		performTest("test_33", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.doMouseContextPress(
//						    location.x, location.y);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_OPENING_CONTEXT_MENU);
//	}
//
//	public void test_34CT_SEL4InMovingHotspot() throws Exception
//	{
//		performTest("test_34", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.doMousePress(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x - 6, location.y - 6);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x - 40, location.y - 40);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_MOVING_HOTSPOT);
//	}
//
//	public void test_35CT_SEL3InMovingHotspot() throws Exception
//	{
//		performTest("test_35", 
//				new Inputs() {
//					public void perform() {
//					    doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    Point location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.doMousePress(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 15, location.y + 15);
//						CanvasTestUtils.doMouseRelease(
//						    location.x + 15, location.y + 15);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_WAITING_FOR_MOUSE_PRESS_OVER_HOTSPOT);
//	}
//
//	public void test_36CT_SEL4InWaitingToMoveHotspot() throws Exception
//	{
//		performTest("test_36", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.doMousePress(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 1, location.y + 1);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_MOVING_HOTSPOT);
//	}
//
//	public void test_37CT_SEL3InWaitingToMoveHotspot() throws Exception
//	{
//		performTest("test_37", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.doMousePress(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x - 40, location.y - 40);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_WAITING_FOR_MOUSE_PRESS_OVER_HOTSPOT);
//	}
//
//	public void test_42CT_SEL2InIdleRetainSel() throws Exception
//	{
//		performTest("test_42", 
//				new Inputs() {
//					public void perform() {
//						canvas.Unselectall();
//						doMousePressOnDiskRequestClass();
//					}
//					public void cleanup() {
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_SELECTING_SYMBOL);
//	}
//
//	public void test_44CT_SEL2InWaitingForMousePressOverHotspotRetainSel()
//			throws Exception
//	{
//		performTest("test_44", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//					    location = doMousePressOnDiskRequestClass();
//						doMouseReleaseOnDiskRequestClass();
//					    location = doMouseMoveOverNWCornerOfDiskRequestClass();
//						CanvasTestUtils.createMouseEvent(
//						    location.x, location.y, "MouseDownRetainSelection");
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseRelease(
//						    location.x, location.y);
//						CanvasTestUtils.doMouseMove(
//						    location.x - 40, location.y - 40);
//					}
//				},
//				SelectionTool_c.ST_SELECTION_TOOL_WAITING_TO_MOVE_HOTSPOT);
//	}
}