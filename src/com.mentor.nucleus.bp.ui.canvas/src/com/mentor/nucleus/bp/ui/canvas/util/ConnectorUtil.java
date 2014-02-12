//========================================================================
//
//File:      $RCSfile: ConnectorUtil.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:19:03 $
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
