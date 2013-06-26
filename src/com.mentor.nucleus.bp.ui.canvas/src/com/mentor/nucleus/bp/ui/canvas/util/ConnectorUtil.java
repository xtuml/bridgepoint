//========================================================================
//
//File:      $RCSfile: ConnectorUtil.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:19:03 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.ui.canvas.util;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

/**
 * Contains utility methods applicable to connector instances.
 */
public class ConnectorUtil
{
    /**
     * Returns the connector-text of the given association's given end,
     * under the given graphics-root.
     */
    public static FloatingText_c getText(
        Ooaofgraphics graphicsRoot, final Association_c assoc, final int end)
    {
        GraphicalElement_c element = 
            GraphicalElementUtil.getRepresentingElement(graphicsRoot, assoc);
        Connector_c connector = Connector_c.getOneGD_CONOnR2(element);
        return FloatingText_c.getOneGD_CTXTOnR8(connector,             
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((FloatingText_c)candidate).getEnd() == end;
                }
            });
    }
}
