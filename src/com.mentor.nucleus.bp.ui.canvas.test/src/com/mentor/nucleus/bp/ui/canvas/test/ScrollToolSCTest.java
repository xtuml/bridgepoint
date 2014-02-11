//=====================================================================
//
//File:      $RCSfile: ScrollToolSCTest.java,v $
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
 * in the scroll-tool statechart.
 * 
 * Note that the tests contained herein are meant to be run sequentially in the order of their
 * declaration, each one using as its initial state the final state of the previous test. 
 */
public class ScrollToolSCTest extends StatechartTest
{
	/**
	 * Constructor
	 */
	public ScrollToolSCTest(String arg0)
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
//			return ScrollTool_c.ScrollToolInstance(graphicsModelRoot)
//				.get_current_state();
//		}
//
//		public void cleanup() {}
//	}
//	
//	public void test_16CT_SCR7InIdle() throws Exception
//	{
//		performTest("test_16", 
//			new Inputs() {
//				public void perform() {
//					canvas.SpacePressed(false);
//				}
//				public void cleanup() {
//					canvas.SpaceReleased(false);
//				}
//			},
//			ScrollTool_c.ST_SCROLL_TOOL_WAITING_FOR_MOUSE_PRESS_TO_SLIDE_CANVAS);
//	}
//
//	public void test_20CT_SCR2InWaitingForMousePressToSlideCanvas() throws Exception
//	{
//		performTest("test_20", 
//				new Inputs() {
//					public void perform() {
//						canvas.SpacePressed(false);
//						doMousePressOnVacantArea();
//					}
//					public void cleanup() {
//					    doMouseReleaseOnVacantArea();
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_READY_TO_SLIDE_CANVAS);
//	}
//
//	public void test_22CT_SCR8InWaitingForMousePressToSlideCanvas()
//			throws Exception
//	{
//		performTest("test_22", 
//				new Inputs() {
//					public void perform() {
//						canvas.SpacePressed(false);
//						canvas.SpaceReleased(false);
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_IDLE);
//	}
//
//	public void test_23CT_SCR4InSlidingCanvas() throws Exception
//	{
//		performTest("test_23", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//						canvas.SpacePressed(false);
//						location = doMousePressOnVacantArea();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 20, location.y + 20);
//						CanvasTestUtils.doMouseMove(
//						    location.x + 40, location.y + 40);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//					    doMouseReleaseOnVacantArea();
//						canvas.SpaceReleased(false);
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_SLIDING_CANVAS);
//	}
//
//	public void test_24CT_SCR3InSlidingCanvas() throws Exception
//	{
//		performTest("test_24", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//						canvas.SpacePressed(false);
//						location = doMousePressOnVacantArea();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 20, location.y + 20);
//						CanvasTestUtils.doMouseRelease(
//						    location.x + 20, location.y + 20);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//						canvas.SpaceReleased(false);
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_RESTING_WHILE_SLIDING_CANVAS);
//	}
//
//	public void test_25CT_SCR8InSlidingCanvas() throws Exception
//	{
//		performTest("test_25", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//						canvas.SpacePressed(false);
//						location = doMousePressOnVacantArea();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 20, location.y + 20);
//						canvas.SpaceReleased(false);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//					    doMouseReleaseOnVacantArea();
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_IDLE);
//	}
//	
//	public void test_38CT_SCR4InReadyToSlideCanvas() throws Exception
//	{
//		performTest("test_38", 
//				new Inputs() {
//		    		Point location;
//					public void perform() {
//						canvas.SpacePressed(false);
//						location = doMousePressOnDiskRequestClass();
//						CanvasTestUtils.doMouseMove(
//						    location.x + 30, location.y + 30);
//					}
//					public void cleanup() {
//						CanvasTestUtils.doMouseMove(
//						    location.x, location.y);
//						canvas.SpaceReleased(false);
//						doMouseReleaseOnDiskRequestClass();
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_SLIDING_CANVAS);
//	}
//
//	public void test_39CT_SCR3InReadyToSlideCanvas() throws Exception
//	{
//		performTest("test_39", 
//				new Inputs() {
//					public void perform() {
//						canvas.SpacePressed(false);
//						doMousePressOnVacantArea();
//						doMouseReleaseOnVacantArea();
//					}
//					public void cleanup() {
//						canvas.SpaceReleased(false);
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_RESTING_WHILE_SLIDING_CANVAS);
//	}
//
//	public void test_40CT_SCR8InReadyToSlideCanvas() throws Exception
//	{
//		performTest("test_40", 
//				new Inputs() {
//					public void perform() {
//						canvas.SpacePressed(false);
//						doMousePressOnVacantArea();
//						canvas.SpaceReleased(false);
//					}
//					public void cleanup() {
//						doMouseReleaseOnVacantArea();
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_IDLE);
//	}
//
//	public void test_43CT_SCR2InWaitingForMousePressToSlideCanvasRetainSel()
//			throws Exception
//	{
//		performTest("test_43", 
//				new Inputs() {
//					public void perform() {
//						canvas.SpacePressed(false);
//						doMousePressRetainSelectionOnVacantArea();
//					}
//					public void cleanup() {
//						doMouseReleaseOnVacantArea();
//						canvas.SpaceReleased(false);
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_READY_TO_SLIDE_CANVAS);
//	}
//
//
//	public void test_123CT_SCR8InRestingWhileSlidingCanvas() throws Exception
//	{
//		performTest("test_123", 
//				new Inputs() {
//					public void perform() {
//						canvas.SpacePressed(false); 
//						doMousePressOnVacantArea();
//						doMouseReleaseOnVacantArea();
//						canvas.SpaceReleased(false);
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_IDLE);
//	}
//
//	public void test_124CT_SCR2InRestingWhileSlidingCanvas() throws Exception
//	{
//		performTest("test_124", 
//				new Inputs() {
//					public void perform() {
//						canvas.SpacePressed(false); 
//						doMousePressOnVacantArea();
//						doMouseReleaseOnVacantArea();
//						doMousePressOnVacantArea();
//					}
//					public void cleanup() {
//						doMouseReleaseOnVacantArea();
//						canvas.SpaceReleased(false);
//					}
//				},
//				ScrollTool_c.ST_SCROLL_TOOL_READY_TO_SLIDE_CANVAS);
//	}
}