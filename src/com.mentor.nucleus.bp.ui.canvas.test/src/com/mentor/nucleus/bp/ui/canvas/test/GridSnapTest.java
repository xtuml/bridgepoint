//=====================================================================
//
//File:      $RCSfile: GridSnapTest.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:43:50 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.ui.canvas.test;

import com.mentor.nucleus.bp.test.common.BaseTest;

/**
 * Contains various tests involving the snap-to-grid behavior of the canvas.
 */
public class GridSnapTest extends BaseTest
{
//    /**
//     * The editor upon which these tests operate.
//     */
//    private static CanvasEditor editor;
//    
//    /**
//     * This holds the grid snap-increment for shapes, for easy reference across the
//     * tests.
//     */
//    private static int snapIncr;
//    
//    private static boolean firstTime = true;
//    /**
//     * This holds the grid snap-increment for connectors, for easy reference across the
//     * tests.
//     */
//    private static int connSnapIncr;
//    
//    /**
//     * Constructor.
//     */
//    public GridSnapTest(String name)
//    {
//        super("GridSnapTest", name);
//    }
//
//    /* (non-Javadoc)
//     * @see junit.framework.TestCase#setUp()
//     */
//    public void setUp() throws Exception
//    {
//    	super.setUp();
//    	
//        if ( firstTime ) {
//            // close the already open class diagram to prevent confusion with the one
//            // we'll open for this test
//            CanvasTestUtils.closeClassDiagramEditor();    
//            firstTime = false;
//        }
//        
//        // if we haven't yet imported the model used for these tests
//    	ensureAvailableAndLoaded("Models", "odms1", false, false);
//            
//            // open a class diagram editor on the odms subsystem
//            editor = CanvasTestUtils.openClassDiagramEditor(
//                modelRoot);
//            
//            snapIncr = Ooaofgraphics.Getgridsnapincrement(graphicsModelRoot, false);
//            connSnapIncr = Ooaofgraphics.Getgridsnapincrement(graphicsModelRoot, true);
//        }
//    
//    /**
//     * Tests that the position of a dragged shape is grid-snapped after the 
//     * drag is finished.
//     */
//    public void testDragShape()
//    {
//        // find the shape of the class that we're going to drag
//        Shape_c shape = CanvasTestUtils.getModelClassShape(
//            modelRoot, "Disk Ownership");
//
//        // check that the shape's position would not be already 
//        // grid-snapped by the offset we're going to add to it
//        final Point corner = CanvasTestUtils.getShapeNWCorner(shape);
//        final int offset = 2 * snapIncr + snapIncr / 2;
//        assertTrue("Offset would grid-snap shape position, anyway.",
//            (corner.x + offset) % snapIncr != 0 &&
//            (corner.y + offset) % snapIncr != 0);
//
//        // find the connector that's connected to the north side of 
//        // the shape to be dragged; since it's on that side, we know that
//        // a drag should fully grid-snap its end
//        Connector_c conn = CanvasTestUtils.getAnyConnectorAttachedToShape(shape);
//        
//        // drag the shape to the SE a distance that shouldn't 
//        // cause it to be grid-snapped, anyway
//        Point center = CanvasTestUtils.convertToMouseCoor(
//            CanvasTestUtils.getShapeCenter(shape), editor.ptCanvas);
//        CanvasTestUtils.doMousePress(center.x, center.y);
//        CanvasTestUtils.doMouseMove(center.x + offset, center.y + offset);
//        CanvasTestUtils.doMouseRelease(center.x + offset, center.y + offset);
//        
//        // check that the shape's position has been grid-snapped
//        Point movedCorner = CanvasTestUtils.getShapeNWCorner(shape);
//        assertTrue("Position of dragged shape not grid-snapped.",
//            movedCorner.x % snapIncr == 0 &&
//            movedCorner.y % snapIncr == 0);
//        
//        // check that the end of the attached connector was also grid-snapped
//        assertTrue("Attached connector not grid-snapped.",
//            conn.Getstartx() % snapIncr == 0 && 
//            conn.Getstarty() % snapIncr == 0);
//    }
//    
//    /**
//     * Tests that the extent of a resized shape is grid-snapped after the 
//     * resize is finished.
//     */
//    public void testResizeShape()
//    {
//        // find the shape of the class that we're going to drag
//        Shape_c shape = CanvasTestUtils.getModelClassShape(
//            modelRoot, "Disk Request");
//
//        // check that the shape's extent would not be already 
//        // grid-snapped by the offset we're going to add to it
//        final Point corner = CanvasTestUtils.getShapeSECorner(shape);
//        final int offset = snapIncr + snapIncr / 2;
//        assertTrue("Offset would grid-snap shape extent, anyway.",
//            (corner.x + offset) % snapIncr != 0 &&
//            (corner.y + offset) % snapIncr != 0);
//
//        // find the connector that's connected to the south side of 
//        // the shape to be dragged; since it's on that side, we know that
//        // a resize should fully grid-snap its end
//        Connector_c conn = CanvasTestUtils.getAnyConnectorAttachedToShape(shape);
//        
//        // click within the shape, to select it
//        Point center = CanvasTestUtils.convertToMouseCoor(
//            CanvasTestUtils.getShapeCenter(shape), editor.ptCanvas);
//        CanvasTestUtils.doMousePress(center.x, center.y);
//        CanvasTestUtils.doMouseRelease(center.x, center.y);
//
//        // move the cursor over the shape's SE corner, to bring 
//        // up the resize cursor
//        Point mouseCorner = CanvasTestUtils.convertToMouseCoor(
//            corner, editor.ptCanvas);
//        CanvasTestUtils.createMouseEvent(
//            mouseCorner.x, mouseCorner.y, "MouseMove");
//        
//        // resize the shape to the SE a distance that shouldn't
//        // cause it to be grid-snapped, anyway
//        CanvasTestUtils.doMousePress(mouseCorner.x, mouseCorner.y);
//        CanvasTestUtils.doMouseMove(mouseCorner.x + offset, mouseCorner.y + offset);
//        CanvasTestUtils.doMouseRelease(
//            mouseCorner.x + offset, mouseCorner.y + offset);
//        
//        // move the mouse over whitespace, to idle the selection tool
//        // for the next test
//        CanvasTestUtils.doMouseMove(1, 1);
//
//        // check that the shape's extent has been grid-snapped
//        Point movedCorner = CanvasTestUtils.getShapeSECorner(shape);
//        assertTrue("Extent of resized shape not grid-snapped.",
//            movedCorner.x % snapIncr == 0 &&
//            movedCorner.y % snapIncr == 0);
//        
//        // check that the end of the attached connector was also grid-snapped
//        assertTrue("Attached connector not grid-snapped.",
//            conn.Getstartx() % connSnapIncr == 0 && 
//            conn.Getstarty() % connSnapIncr == 0);
//   }
//
//    /**
//     * Tests that the position and extent of a newly-created shape are
//     * grid-snapped.
//     */
//    public void testCreateShape()
//    {
//		// select the class shape-tool
//        ShapeTool_c shapeTool = CanvasTestUtils.getShapeTool(
//		    graphicsModelRoot, "Class");
//		CanvasTestUtils.controlShapeToolState(
//			graphicsModelRoot, shapeTool, "activate");
//		
//		// choose starting and ending drag-points for the shape's
//		// creation that we know aren't already grid-snapped
//		int startOrdinate = snapIncr + snapIncr / 2;
//		int extent = 5 * snapIncr + snapIncr / 4;
//		Point start = CanvasTestUtils.convertToMouseCoor(
//		    new Point(startOrdinate, startOrdinate), editor.ptCanvas);
//		Point finish = CanvasTestUtils.convertToMouseCoor(
//		    new Point(startOrdinate + extent, startOrdinate + extent), 
//		    editor.ptCanvas);
//
//		// simulate a mouse drag, to create the shape
//		CanvasTestUtils.doMousePress(start.x, start.y);
//        CanvasTestUtils.doMouseMove(finish.x, finish.y);
//        CanvasTestUtils.doMouseRelease(finish.x, finish.y);
//        
//        // find the shape just created
//        Shape_c shape = CanvasTestUtils.getModelClassShape(modelRoot,
//            "Unnamed Class");
//
//        // check that the shape's origin has been grid-snapped
//        Point corner = CanvasTestUtils.getShapeNWCorner(shape);
//        assertTrue("Origin of created shape not grid-snapped.",
//            corner.x % snapIncr == 0 &&
//            corner.y % snapIncr == 0);
//
//        // check that the shape's extent has been grid-snapped
//        corner = CanvasTestUtils.getShapeSECorner(shape);
//        assertTrue("Extent of created shape not grid-snapped.",
//            corner.x % snapIncr == 0 &&
//            corner.y % snapIncr == 0);
//        
//		// de-activate the class shape-tool
//    	CanvasTestUtils.controlShapeToolState(graphicsModelRoot, shapeTool, 
//    	    "deactivate");
//    }
//    
//    /**
//     * Tests that the movement of an interior waypoint of a connector
//     * is grid-snapped.
//     */
//    public void testMoveConnectorInteriorWaypoint()
//    {
//		// find the connector that represents R5
//	    Connector_c connector = CanvasTestUtils.getAssociationConnector(
//		    modelRoot, graphicsModelRoot, 5);
//		
//	    // find the connector's only interior waypoint
//	    LineSegment_c segment = CanvasTestUtils.getStartingSegment(
//	        graphicsModelRoot, connector);
//	    Waypoint_c waypoint = Waypoint_c.getOneDIM_WAYOnR22(segment);
//	    
//        // check that the destination of the waypoint drag isn't grid-snapped
//	    // in either axis, to ensure the test is valid
//	    final int offset = -(connSnapIncr + connSnapIncr / 2);
//        assertTrue("Waypoint already grid-snapped in at least one axis.",
//            (waypoint.getPositionx() + offset) % connSnapIncr != 0 &&
//            (waypoint.getPositiony() + offset) % connSnapIncr != 0);
//	    
//        // click the waypoint, to select the connector 
//		Point start = CanvasTestUtils.convertToMouseCoor(
//		    new Point((int)waypoint.getPositionx(), (int)waypoint.getPositiony()), 
//		    editor.ptCanvas);
//		CanvasTestUtils.doMousePress(start.x, start.y);
//		CanvasTestUtils.doMouseRelease(start.x, start.y);
//
//		// move the cursor in the vicinity of the waypoint, 
//		// to bring up the hotspot cursor
//		CanvasTestUtils.doMouseMove(start.x, start.y - 1);
//
//		// drag the waypoint
//		Point finish = CanvasTestUtils.convertToMouseCoor(
//		    new Point(
//		        (int)waypoint.getPositionx() + offset, 
//		        (int)waypoint.getPositiony() + offset), 
//		    editor.ptCanvas);
//		CanvasTestUtils.doMousePress(start.x, start.y);
//        CanvasTestUtils.doMouseMove(finish.x, finish.y);
//        CanvasTestUtils.doMouseRelease(finish.x, finish.y);
//
//        // move the mouse over whitespace, to idle the selection tool
//        // for the next test
//        CanvasTestUtils.doMouseMove(1, 1);
//
//        // check that the waypoint's final position has been grid-snapped
//        assertTrue("Moved waypoint not grid-snapped.",
//            waypoint.getPositionx() % connSnapIncr == 0 &&
//            waypoint.getPositiony() % connSnapIncr == 0);
//    }
//    
//    /**
//     * Tests that the movement of a connector's ending waypoint 
//     * inside the shape to which it is attached causes the waypoint 
//     * to be grid-snapped to the shape's border, if the shape 
//     * is itself already grid-snapped.
//     */
//    public void testMoveAttachedConnectorWaypointInsideShape()
//    {
//        // find the only connector attached to the shape of the 
//        // Disk Ownership class, which was grid-snapped during a 
//        // previous test in this class
//        Shape_c shape = CanvasTestUtils.getModelClassShape(
//            modelRoot, "Disk Ownership");
//        Connector_c conn = CanvasTestUtils.getAnyConnectorAttachedToShape(shape);
//        
//        // click the connector up its length a little ways, to select (just) it 
//		int oldStartx = conn.Getstartx();
//		Point start = CanvasTestUtils.convertToMouseCoor(
//		    new Point(oldStartx, conn.Getstarty()), 
//		    editor.ptCanvas);
//		CanvasTestUtils.doMousePress(start.x, start.y - 10);
//		CanvasTestUtils.doMouseRelease(start.x, start.y - 10);
//
//		// move the cursor over where the connector attaches
//		// to the shape, to bring up the hotspot cursor
//		CanvasTestUtils.doMouseMove(start.x, start.y);
//
//		// drag the waypoint diagonally into the shape
//		CanvasTestUtils.doMousePress(start.x, start.y);
//        CanvasTestUtils.doMouseMove(start.x + 50, start.y + 20);
//        CanvasTestUtils.doMouseRelease(start.x + 50, start.y + 20);
//
//        // check that the waypoint was actually moved, to help ensure the test 
//        // is valid
//        int newStartX = conn.Getstartx();
//        assertTrue("Waypoint not moved.", newStartX != oldStartx);
//        
//        // check that the waypoint's final position has been grid-snapped
//        // to the shape's border
//        int newStartY = conn.Getstarty();
//        assertTrue("Moved waypoint not grid-snapped to shape's border.",
//            newStartX % connSnapIncr == 0 &&
//            newStartY % connSnapIncr == 0 &&
//            newStartY == CanvasTestUtils.getShapeNWCorner(shape).y);
//    }
//    
//    /**
//     * Tests that the waypoints of a newly-created multi-segment connector have
//     * been grid-snapped.  The ends will only be fully grid-snapped if the
//     * attached shape edges have been grid-snapped. 
//     */
//    public void testCreateMultiSegmentConnector()
//    {
//        // find the mouse location of the center of the shape where
//        // the connector will start; we deliberately choose a shape 
//        // that we know has had its bottom edge grid-snapped by
//        // an earlier test in this class
//        Shape_c startShape = CanvasTestUtils.getModelClassShape(
//            modelRoot, "Disk Request");
//		Point start = CanvasTestUtils.convertToMouseCoor(
//		    CanvasTestUtils.getShapeCenter(startShape), 
//		    editor.ptCanvas);
//        
//        // find the mouse location of the center of the shape where
//        // the connector will end; we deliberately choose a shape 
//        // that we know has had its top edge grid-snapped by
//        // an earlier test in this class
//		Shape_c finishShape = CanvasTestUtils.getModelClassShape(
//            modelRoot, "Disk Ownership");
//		Point finish = CanvasTestUtils.convertToMouseCoor(
//		    CanvasTestUtils.getShapeCenter(finishShape), 
//		    editor.ptCanvas);
//		
//		// make it so that the connector's only interior waypoint
//		// will be about halfway between the two shapes (but, off
//		// to the side enough that there will still be two segments,
//		// rather than just one; also, the exact halfway y ordinate
//		// is over an existing horizontal segment, so we shift the
//		// waypoint slightly away from it)
//		Point interior = new Point(start.x - 100, (start.y + finish.y) / 2 - 10);
//
//		// activate the association tool
//		ConnectorTool_c tool = CanvasTestUtils.getConnectorTool(
//		    graphicsModelRoot, "Association");
//		CanvasTestUtils.controlConnectorToolState(graphicsModelRoot, tool, "activate");
//		
//		// create the first segment
//		CanvasTestUtils.doMousePress(start.x, start.y);
//        CanvasTestUtils.doMouseMove(interior.x, interior.y);
//        CanvasTestUtils.doMouseRelease(interior.x, interior.y);
//
//        // create the second segment
//        CanvasTestUtils.doMousePress(interior.x, interior.y);
//        CanvasTestUtils.doMouseMove(finish.x, finish.y);
//        CanvasTestUtils.doMouseRelease(finish.x, finish.y);
//        
//		// deactivate the association tool
//		CanvasTestUtils.controlConnectorToolState(graphicsModelRoot, tool, "deactivate");
//
//		// find the connector just created
//	    Connector_c conn = CanvasTestUtils.getAssociationConnector(
//		    modelRoot, graphicsModelRoot, 12);
//        
//        // check that the connector's start has been grid-snapped to the
//        // start-shape's lower edge
//	    int connStartY = conn.Getstarty();
//        assertTrue("Connector start not grid-snapped to starting shape's bottom edge.",
//            conn.Getstartx() % connSnapIncr == 0 &&
//            connStartY % connSnapIncr == 0 &&
//            connStartY == CanvasTestUtils.getShapeSECorner(startShape).y);
//
//        // check that the interior waypoint has been grid-snapped
//        LineSegment_c segment = CanvasTestUtils.getStartingSegment(
//            graphicsModelRoot, conn);
//        Waypoint_c waypoint = Waypoint_c.getOneDIM_WAYOnR22(segment);
//        assertTrue("Interior waypoint not grid-snapped.",
//            waypoint.getPositionx() % connSnapIncr == 0 &&
//            waypoint.getPositiony() % connSnapIncr == 0);
//        
//        // check that the connector's end has been grid-snapped to the
//        // finish-shape's upper edge
//	    int connEndY = conn.Getendy();
//        assertTrue("Connector end not grid-snapped to ending shape's top edge.",
//            conn.Getendx() % connSnapIncr == 0 &&
//            connEndY % connSnapIncr == 0 &&
//            connEndY == CanvasTestUtils.getShapeNWCorner(finishShape).y);
//    }
//    
//    /**
//     * Tests that an already grid-snapped shape is not moved when it is 
//     * dragged too little to reach the next grid snap-point.
//     */
//    public void testDragShapeTooLittleForMovement()
//    {
//        // find the shape of the class that we're going to drag;
//        // we use one that we know has already been grid-snapped
//        // by one of the previous tests in this class
//        Shape_c shape = CanvasTestUtils.getModelClassShape(
//            modelRoot, "Disk Ownership");
//
//        // drag the shape to the SE a very small amount
//        final Point corner = CanvasTestUtils.getShapeNWCorner(shape);
//        final int offset = Gr_c.Unscale(snapIncr / 2 - 1);
//        Point center = CanvasTestUtils.convertToMouseCoor(
//            CanvasTestUtils.getShapeCenter(shape), editor.ptCanvas);
//        CanvasTestUtils.doMousePress(center.x, center.y);
//        CanvasTestUtils.doMouseMove(center.x + offset, center.y + offset);
//        CanvasTestUtils.doMouseRelease(center.x + offset, center.y + offset);
//        
//        // check that the shape's position has not changed
//        Point newCorner = CanvasTestUtils.getShapeNWCorner(shape);
//        assertTrue("Position of dragged shape changed.",
//            newCorner.equals(corner));
//    }
}
