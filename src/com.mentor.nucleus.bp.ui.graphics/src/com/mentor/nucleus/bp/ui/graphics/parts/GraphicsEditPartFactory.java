//========================================================================
//
//File:      $RCSfile: GraphicsEditPartFactory.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:55 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

public class GraphicsEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof Model_c) {
			DiagramEditPart part = new DiagramEditPart();
			part.setModel(model);
			part.upgradeModelData();
			return part;
		}
		if(model instanceof Shape_c) {
			Shape_c shp = (Shape_c) model;
			ShapeEditPart part = new ShapeEditPart();
			part.setModel(shp);
			return part;
		}
		if(model instanceof Connector_c) {
			Connector_c connector = (Connector_c) model;
			if(connector != null) {
				ConnectorEditPart part = new ConnectorEditPart();
				part.setModel(connector);
				return part;
			}
		}
		if(model instanceof FloatingText_c) {
			TextEditPart part = new TextEditPart();
			part.setModel(model);
			return part;
		}
		return null;
	}

}
