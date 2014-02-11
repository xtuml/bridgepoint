//========================================================================
//
//File:      $RCSfile: MouseUtil.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:44:08 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//======================================================================== 

package com.mentor.nucleus.bp.ui.canvas.test.util;

import java.awt.geom.Point2D;

import org.eclipse.swt.graphics.Point;

import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.canvas.Model_c;

/**
 * Contains utility and convenience methods dealing with simulating mouse
 * events for testing purposes. 
 */
public class MouseUtil
{
    /**
     * Simulates a mouse press within the given model at the given 
     * model-location, which is first converted to the canvas's 
     * coordinate space.
     */
    public static void doPress(Model_c model, Point2D.Float location)
    {
        Point canvasLocation = convertToCanvasSpace(model, location);
        CanvasTestUtils.doMousePress(canvasLocation.x, canvasLocation.y);
    }

    /**
     * Simulates a mouse movement within the given model to the given 
     * model-location, which is first converted to the canvas's 
     * coordinate space.
     */
    public static void doMove(Model_c model, Point2D.Float location)
    {
        Point canvasLocation = convertToCanvasSpace(model, location);
        CanvasTestUtils.doMouseMove(canvasLocation.x, canvasLocation.y);
    }

    /**
     * Simulates a mouse release within the given model at the given 
     * model-location, which is first converted to the canvas's 
     * coordinate space.
     */
    public static void doRelease(Model_c model, Point2D.Float location)
    {
        Point canvasLocation = convertToCanvasSpace(model, location);
        CanvasTestUtils.doMouseRelease(canvasLocation.x, canvasLocation.y);
    }
    
    /**
     * Simulates a mouse double-click within the given model at the given 
     * model-location, which is first converted to the canvas's 
     * coordinate space.
     */
    public static void doDoubleClick(Model_c model, Point2D.Float location)
    {
        // first do a single-press and then release at the given location, 
        // since a real mouse double-click would first produce such events
        doPress(model, location);
        doRelease(model, location);
        
        Point canvasLocation = convertToCanvasSpace(model, location);
        CanvasTestUtils.doMouseDoubleClick(canvasLocation.x, canvasLocation.y);
    }
    
    /**
     * Simulates a mouse context press within the given model at the given 
     * model-location, which is first converted to the canvas's 
     * coordinate space.
     */
    public static void doContextPress(Model_c model, Point2D.Float location)
    {
        Point canvasLocation = convertToCanvasSpace(model, location);
        CanvasTestUtils.doMouseContextPress(canvasLocation.x, canvasLocation.y);
    }

    /**
     * Returns the conversion of the given location to the coordinate
     * space of the canvas associated with the given model.
     */
    public static Point convertToCanvasSpace(
        Model_c model, Point2D.Float location)
    {
        return CanvasTestUtils.convertToMouseCoor(
            new Point((int)location.x, (int)location.y), model);
    }
}
