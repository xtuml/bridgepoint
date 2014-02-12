//=====================================================================
//
//File:      $RCSfile: ConnectorToolSCTest.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 22:43:50 $
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
public class ConnectorToolSCTest extends StatechartTest
{
//	/**
//	 * The connector-tool exercised during these tests.
//	 */
//	private static ConnectorTool_c connectorTool;
//
	/**
	 * Constructor
	 */
	public ConnectorToolSCTest(String arg0)
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
//		if (connectorTool == null) {
//		    // do so
//		    connectorTool = CanvasTestUtils.getConnectorTool(
//		        graphicsModelRoot, "Association");
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
//			return connectorTool.get_current_state();
//		}
//		
//		public void cleanup() {}
//	}
//
//	public void test_127CT_CTL1InIdle() throws Exception
//	{
//		performTest("test_127",   
//				new Inputs() {
//					public void perform() {
//					    activateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL);
//	}
//
//	public void test_128CT_CTL2InWaitingForStartSymbol() throws Exception
//	{
//		performTest("test_128",  
//				new Inputs() {
//					public void perform() {
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE);
//	}
//
//	public void test_129CT_CTL3InWaitingForStartSymbol_BadLocation() throws Exception
//	{
//		performTest("test_129", 
//				new Inputs() {
//					public void perform() {
//					    activateConnectorTool();
//						doMousePressOnVacantArea();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL);
//	}
//
//	public void test_130CT_CTL3InWaitingForStartSymbol_GoodLocation() throws Exception
//	{
//		performTest("test_130", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_CREATING_FIRST_SEGMENT);
//	}
//
//	public void test_131CT_CTL6InCreatingFirstSegment() throws Exception
//	{
//		performTest("test_131", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMouseRelease(start.x, start.y);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL,
//				-1, -1);
//	}
//
//	public void test_132CT_CTL2InCreatingFirstSegment() throws Exception
//	{
//		// first get state machine from state 2 to state 4 
//		Point start = getGoodStartPosition();
//	    CanvasTestUtils.doMousePress(start.x, start.y);
//		
//		performTest("test_132", 
//				new Inputs() {
//					public void perform() {
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				-1, -1);
//	}
//
//	public void test_133CT_CTL7InCreatingFirstSegment() throws Exception
//	{
//		performTest("test_133", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 4 
//					    activateConnectorTool();
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//						
//					    CanvasTestUtils.doMouseMove(
//					        start.x + 150, start.y + 150);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_DRAGGING_FIRST_SEGMENT,
//				1, 1);
//	}
//	public void test_134CT_CTL7InDraggingFirstSegment() throws Exception
//	{
//		performTest("test_134", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMouseMove(
//					        start.x + 150, start.y + 100);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_DRAGGING_FIRST_SEGMENT,
//				0, 0);
//	}
//
//	public void test_135CT_CTL2InDraggingFirstSegment() throws Exception
//	{
//		performTest("test_135", 
//				new Inputs() {
//					public void perform() {
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				-1, -1);
//	}
//	
//	public void test_136CT_CTL6InDraggingFirstSegment_GoodLocation() throws Exception
//	{
//		performTest("test_136", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 5 
//						activateConnectorTool();
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 100);
//						
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 100);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_CREATING_SUBSEQUENT_SEGMENT,
//				1, 2);
//	}
//	
//	public void test_137CT_CTL2InCreatingSubsequentSegment() throws Exception
//	{
//		performTest("test_137", 
//				new Inputs() {
//					public void perform() {
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				-1, -2);
//	}
//	
//	public void test_138CT_CTL6InDraggingFirstSegment_BadLocation() throws Exception
//	{
//		performTest("test_138", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 5 
//						Point start = getGoodStartPosition();
//						activateConnectorTool();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 25);
//						
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 25);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL,
//				0, 0);
//	}
//	
//	public void test_139CT_CTL7InCreatingSubsequentSegment() throws Exception
//	{
//		performTest("test_139", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 2 to state 7 
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 100);
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 100);
//						
//					    CanvasTestUtils.doMouseMove(start.x + 50, start.y - 100);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_TRACKING_END_SEGMENT_WITHOUT_DRAWING,
//				1, 2);
//	}
//	
//	public void test_140CT_CTL7InTrackingEndSegmentWithoutDrawing() throws Exception
//	{
//		performTest("test_140", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMouseMove(start.x + 50, start.y);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_TRACKING_END_SEGMENT_WITHOUT_DRAWING,
//				0, 0);
//	}
//	
//	public void test_141CT_CTL2InTrackingEndSegmentWithoutDrawing() throws Exception
//	{
//		performTest("test_141", 
//				new Inputs() {
//					public void perform() {
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				-1, -2);
//	}
//	
//	public void test_142CT_CTL3InCreatingSubsequentSegment() throws Exception
//	{
//		performTest("test_142", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 7 
//						activateConnectorTool();
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 150);
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 150);
//						
//					    CanvasTestUtils.doMousePress(start.x, start.y - 150);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_PREPARING_TO_DRAG_SUBSEQUENT_SEGMENT,
//				1, 2);
//	}
//	
//	public void test_143CT_CTL6InPreparingToDragSubsequentSegment() throws Exception
//	{
//		performTest("test_143", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 150);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_MOUSE_UP_AT_BEGINNING_OF_SEGMENT,
//				0, 0);
//	}
//	
//	public void test_144CT_CTL3InMouseUpAtBeginningOfSegment() throws Exception
//	{
//		performTest("test_144", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y - 150);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_PREPARING_TO_DRAG_SUBSEQUENT_SEGMENT,
//				0, 0);
//	}
//	
//	public void test_145CT_CTL7InMouseUpAtBeginningOfSegment() throws Exception
//	{
//		// first get state machine from state 9 to state 10 
//		final Point start = getGoodStartPosition();
//	    CanvasTestUtils.doMouseRelease(start.x, start.y - 150);
//
//		performTest("test_145", 
//				new Inputs() {
//					public void perform() {
//					    CanvasTestUtils.doMouseMove(start.x + 50, start.y - 125);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_TRACKING_END_SEGMENT_WITHOUT_DRAWING,
//				0, 0);
//	}
//	
//	public void test_146CT_CTL3InTrackingEndSegmentWithoutDrawing() throws Exception
//	{
//		performTest("test_146", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x + 50, start.y - 125);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_MOUSE_DOWN_TRACKING_END_SEGMENT_WITHOUT_DRAWING,
//				0, 0);
//	}
//	
//	public void test_147CT_CTL7InMouseDownTrackingEndSegmentWithoutDrawing() throws Exception
//	{
//		performTest("test_147", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMouseMove(start.x + 75, start.y - 100);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_DRAGGING_SUBSEQUENT_SEGMENTS,
//				0, 0);
//	}
//	
//	public void test_148CT_CTL7InDraggingSubsequentSegments() throws Exception
//	{
//		performTest("test_148", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMouseMove(start.x + 100, start.y - 75);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_DRAGGING_SUBSEQUENT_SEGMENTS,
//				0, 0);
//	}
//	
//	public void test_149CT_CTL2InDraggingSubsequentSegments() throws Exception
//	{
//		performTest("test_149", 
//				new Inputs() {
//					public void perform() {
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				-1, -2);
//	}
//	
//	public void test_150CT_CTL2InPreparingToDragSubsequentSegment() throws Exception
//	{
//		performTest("test_150", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 9
//						activateConnectorTool();
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 100);
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 100);
//					    CanvasTestUtils.doMousePress(start.x, start.y - 100);
//						
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				0, 0);
//	}
//	
//	public void test_151CT_CTL2InMouseUpAtBeginningOfSegment() throws Exception
//	{
//		performTest("test_151", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 10 
//						activateConnectorTool();
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 100);
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 100);
//					    CanvasTestUtils.doMousePress(start.x, start.y - 100);
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 100);
//						
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				0, 0);
//	}
//	
//	public void test_152CT_CTL2InMouseDownTrackingEndSegmentWithoutDrawing() throws Exception
//	{
//		performTest("test_152", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 14 
//						activateConnectorTool();
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 100);
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 100);
//					    CanvasTestUtils.doMouseMove(start.x + 25, start.y - 75);
//					    CanvasTestUtils.doMousePress(start.x + 25, start.y - 75);
//						
//					    deactivateConnectorTool();
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_IDLE,
//				0, 0);
//	}
//	
//	public void test_153CT_CTL7InPreparingToDragSubsequentSegment() throws Exception
//	{
//		performTest("test_153", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 1 to state 9 
//						activateConnectorTool();
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 150);
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 150);
//					    CanvasTestUtils.doMousePress(start.x, start.y - 150);
//						
//					    CanvasTestUtils.doMouseMove(start.x + 50, start.y);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_DRAGGING_SUBSEQUENT_SEGMENTS,
//				1, 2);
//	}
//	
//	public void test_154CT_CTL6InDraggingSubsequentSegments() throws Exception
//	{
//		performTest("test_154", 
//				new Inputs() {
//					public void perform() {
//					    Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMouseRelease(start.x + 50, start.y);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL,
//				0, 0);
//	}
//	
//	public void test_155CT_CTL6InMouseDownTrackingEndSegmentWithoutDrawing() throws Exception
//	{
//		performTest("test_155", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 2 to state 14 
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(start.x + 50, start.y - 150);
//					    CanvasTestUtils.doMouseRelease(start.x + 50, start.y - 150);
//					    CanvasTestUtils.doMouseMove(start.x + 100, start.y - 150);
//					    CanvasTestUtils.doMousePress(start.x + 100, start.y - 150);
//						
//					    CanvasTestUtils.doMouseRelease(
//					        start.x + 100, start.y - 150);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_CREATING_SUBSEQUENT_SEGMENT,
//				1, 3);
//	}
//	
//	public void test_156CT_CTL6InDraggingFirstSegment_Complete() throws Exception
//	{
//		performTest("test_156", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 7 to state 5 
//						Point start = getGoodStartPosition();
//						Point end = getGoodEndPosition();
//						deactivateConnectorTool();  // to state 1
//						activateConnectorTool();  // to state 2
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(end.x, end.y);
//						
//					    CanvasTestUtils.doMouseRelease(end.x, end.y);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL,
//				0, -3 + 1);
//	}
//	
//	public void test_157CT_CTL6InDragginSubsequentSegments_BadLocation() throws Exception
//	{
//		performTest("test_157", 
//				new Inputs() {
//					public void perform() {
//						// first get state machine from state 2 to state 11 
//						Point start = getGoodStartPosition();
//					    CanvasTestUtils.doMousePress(start.x + 50, start.y);
//					    CanvasTestUtils.doMouseMove(start.x + 50, start.y - 150);
//					    CanvasTestUtils.doMouseRelease(start.x + 50, start.y - 150);
//					    CanvasTestUtils.doMousePress(start.x + 50, start.y - 150);
//					    CanvasTestUtils.doMouseMove(start.x, start.y - 150);
//						
//						// waypoint is not suitable because it's on top of an exising waypoint
//					    CanvasTestUtils.doMouseRelease(start.x, start.y - 150);
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL,
//				0, 0);
//	}
//	
//	public void test_158CT_CTL6InDraggingFirstSegment_UnsuitableFromClient() throws Exception
//	{
//		performTest("test_158", 
//				new Inputs() {
//		    		ConnectorTool_c oldTool;
//					public void perform() {
//						// first get state machine from state 2 to state 5 
//						// the test is to try to make 'Offline Disk' a subtype on R3 a second time
//						oldTool = connectorTool;
//					    connectorTool = CanvasTestUtils.getConnectorTool(graphicsModelRoot, "Subtype");
//						
//					    CanvasTestUtils ctu = new CanvasTestUtils();
//				        Association_c rel =
//					          Association_c.AssociationInstance(modelRoot, ctu.new Association_by_numb_c(3));
//				        Connector_c cut =
//				            Connector_c.ConnectorInstance(graphicsModelRoot, 
//				                ctu.new Connector_by_ooaid_c(rel.getRel_id(), 36));
//				        assertNotNull( cut );
//				        Point conEnd = new Point(cut.Getendx(), cut.Getendy());
//						Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot);
//						Model_c canvas = getUUT(uut.Get_ooa_id(), Modeltype_c.ClassDiagram);
//				        Point start =
//				              CanvasTestUtils.convertToMouseCoor(conEnd, canvas);
//
//				        Shape_c shp = CanvasTestUtils.getModelClassShape(modelRoot, "Offline Disk");
//						Point center = CanvasTestUtils.getShapeCenter(shp);
//						Point end =
//							CanvasTestUtils.convertToMouseCoor(center, canvas);
//
//						activateConnectorTool();
//					    CanvasTestUtils.doMousePress(start.x, start.y);
//					    CanvasTestUtils.doMouseMove(end.x, end.y);
//						
//					    CanvasTestUtils.doMouseRelease(end.x, end.y);
//					}
//					public void cleanup() {
//						if (oldTool != null) connectorTool = oldTool;
//					}
//				},
//				ConnectorTool_c.ST_CONNECTOR_TOOL_WAITING_FOR_START_SYMBOL,
//				0, 0);
//	}
//
//	private Point getGoodStartPosition() 
//	{
//		Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot);
//		Model_c canvas = getUUT(uut.Get_ooa_id(), Modeltype_c.ClassDiagram);
//		Shape_c shp =
//			CanvasTestUtils.getModelClassShape(modelRoot, "Qualified Process");
//		Point center = CanvasTestUtils.getShapeCenter(shp);
//		final Point startPosition =
//			CanvasTestUtils.convertToMouseCoor(center, canvas);
//		return startPosition;
//	}
//
//	private Point getGoodEndPosition() 
//	{
//		Subsystem_c uut = Subsystem_c.SubsystemInstance(modelRoot);
//		Model_c canvas = getUUT(uut.Get_ooa_id(), Modeltype_c.ClassDiagram);
//		Shape_c shp =
//			CanvasTestUtils.getModelClassShape(modelRoot, "Disk Request");
//		Point center = CanvasTestUtils.getShapeCenter(shp);
//		Point endPosition =
//			CanvasTestUtils.convertToMouseCoor(center, canvas);
//		return endPosition;
//	}
//
//    /**
//     * A convenience method.  See method wrapped.
//     */
//	private void activateConnectorTool()
//	{
//		CanvasTestUtils.controlConnectorToolState(
//			graphicsModelRoot, connectorTool, "activate");
//	}
//	
//    /**
//     * A convenience method.  See method wrapped.
//     */
//	private void deactivateConnectorTool()
//	{
//		CanvasTestUtils.controlConnectorToolState(
//			graphicsModelRoot, connectorTool, "deactivate");
//	}
//	
//	/**
//	 * See superclass method of same name that is wrapped here.  
//	 * Assertions are added that apply to most tests in this class.
//	 * These check the amount of graphical-elements and line-segments 
//	 * present in the model, pre- and post-test.  The given ...Change
//	 * variables specify the change in these amounts that should
//	 * be caused by the test.
//	 */
//    public void performTest(String testID, Inputs inputs, int expectedState,
//        int graphicalElemsAmountChange, int lineSegsAmountChange)
//        throws Exception
//    {
//		// record what graphical-elements and line-segments are in the model
//        // prior to the test
//        GraphicalElement_c [] priorGESet = GraphicalElement_c.GraphicalElementInstances(graphicsModelRoot);
//		LineSegment_c [] priorLSSet = LineSegment_c.LineSegmentInstances(graphicsModelRoot);
//
//        super.performTest(testID, inputs, expectedState);
//
//        // check that the number of graphical-elements and line-segments 
//        // in the model changed by the given amounts
//        GraphicalElement_c [] postGESet = GraphicalElement_c.GraphicalElementInstances(graphicsModelRoot);
//		assertEquals(priorGESet.length + graphicalElemsAmountChange, postGESet.length);
//		LineSegment_c [] postLSSet = LineSegment_c.LineSegmentInstances(graphicsModelRoot);
//		assertEquals(priorLSSet.length + lineSegsAmountChange, postLSSet.length);
//    }
}	
