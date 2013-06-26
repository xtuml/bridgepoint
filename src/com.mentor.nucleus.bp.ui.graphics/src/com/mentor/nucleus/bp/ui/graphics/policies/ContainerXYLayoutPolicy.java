//========================================================================
//
//File:      $RCSfile: ContainerXYLayoutPolicy.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:05:51 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
