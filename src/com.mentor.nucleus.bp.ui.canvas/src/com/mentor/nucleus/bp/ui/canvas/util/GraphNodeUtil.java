//========================================================================
//
//File:      $RCSfile: GraphNodeUtil.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:19:03 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.ui.canvas.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Point_c;

/**
 * Contains utility methods applicable to graph-node instances.
 */
public class GraphNodeUtil
{
    /**
     * Returns the position of the top-left corner of the given graph-node.
     */
    public static Point2D.Float getPosition(Graphnode_c node)
    {
        Graphelement_c element = Graphelement_c.getOneDIM_GEOnR301(node);
        return new Point2D.Float(element.getPositionx(), element.getPositiony());
    }

    /**
     * Returns the rectangular bounds of the given graph-node.
     */
    public static Rectangle2D.Float getBounds(Graphnode_c node)
    {
        Point2D.Float position = getPosition(node);
        return new Rectangle2D.Float(
            position.x, position.y, node.getWidth(), node.getHeight());
    }

    /**
     * Returns the center position of the given graph-node.
     */
    public static Point2D.Float getCenter(Graphnode_c node)
    {
        Rectangle2D.Float bounds = getBounds(node);
        return new Point2D.Float(
            bounds.x + bounds.width / 2,
            bounds.y + bounds.height / 2);
    }

    /**
     * Returns the graph-node that represents the given model-class
     * under the given graphics-root.
     */
    public static Graphnode_c getNode(
        Ooaofgraphics graphicsRoot, final ModelClass_c clazz)
    {
        GraphicalElement_c element = 
            GraphicalElementUtil.getRepresentingElement(graphicsRoot, clazz);
        return Graphnode_c.getOneDIM_NDOnR301(
            Graphelement_c.getOneDIM_GEOnR23(element));
    }
}
