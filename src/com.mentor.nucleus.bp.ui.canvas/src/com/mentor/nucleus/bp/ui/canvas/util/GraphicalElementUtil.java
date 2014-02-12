//========================================================================
//
//File:      $RCSfile: GraphicalElementUtil.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:19:02 $
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
