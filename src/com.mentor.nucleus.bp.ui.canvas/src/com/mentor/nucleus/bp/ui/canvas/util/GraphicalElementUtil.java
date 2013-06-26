//========================================================================
//
//File:      $RCSfile: GraphicalElementUtil.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:19:02 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.ui.canvas.util;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

/**
 * Contains utility methods applicable to graphical-element instances.
 */
public class GraphicalElementUtil
{
    /**
     * Returns the graphical element which represents the given model-element
     * under the given graphics-root. 
     */
    public static GraphicalElement_c getRepresentingElement(
        Ooaofgraphics graphicsRoot, final NonRootModelElement modelElement)
    {
        return GraphicalElement_c.GraphicalElementInstance(graphicsRoot, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    GraphicalElement_c element = 
                        (GraphicalElement_c)candidate;
                    return element.getRepresents() == modelElement;
                }
            });
    }
}
