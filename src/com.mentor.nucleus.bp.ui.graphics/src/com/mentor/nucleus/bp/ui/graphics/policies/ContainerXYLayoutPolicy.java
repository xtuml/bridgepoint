//========================================================================
//
//File:      $RCSfile: ContainerXYLayoutPolicy.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:05:51 $
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
//
package com.mentor.nucleus.bp.ui.graphics.policies;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.CreateRequest;

import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.commands.ShapeCreationCommand;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsCreateRequest;

public class ContainerXYLayoutPolicy extends GraphicsXYLayoutPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getNewObject().equals(Shape_c.class)) {
			return new ShapeCreationCommand(Model_c
					.getOneGD_MDOnR1(GraphicalElement_c
							.getOneGD_GEOnR2((Shape_c) getHost().getModel())),
					new Rectangle(request.getLocation(),
							(request.getSize() != null ? request.getSize()
									: new Dimension(0, 0))),
					(AbstractGraphicalEditPart) getHost(),
					((GraphicsCreateRequest) request).getToolId());
		}
		return null;
	}

}
