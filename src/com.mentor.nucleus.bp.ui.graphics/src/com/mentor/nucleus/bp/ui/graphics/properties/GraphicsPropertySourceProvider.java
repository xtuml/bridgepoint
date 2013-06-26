//========================================================================
//
//File:      $RCSfile: GraphicsPropertySourceProvider.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:31 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.properties;

import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.properties.ModelPropertySourceProvider;

public class GraphicsPropertySourceProvider extends ModelPropertySourceProvider {


	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySourceProvider#getPropertySource(java.lang.Object)
	 */
	public IPropertySource getPropertySource(Object object)
	{
		if(object instanceof EditPart) {
			// convert to the model the edit part's model
			// represents
			object = ((EditPart) object).getModel();
			if(object instanceof Model_c) {
				object = ((Model_c) object).getRepresents();
			} else if(object instanceof Shape_c) {
				GraphicalElement_c gElem = GraphicalElement_c.getOneGD_GEOnR2((Shape_c)object);
				if(gElem == null)
					return null;
				object = gElem.getRepresents();
			} else if(object instanceof Connector_c) {
				GraphicalElement_c gElem = GraphicalElement_c.getOneGD_GEOnR2((Connector_c)object);
				if(gElem == null)
					return null;
				object = gElem.getRepresents();
			} else if(object instanceof FloatingText_c) {
				FloatingText_c text = (FloatingText_c) object;
				Connector_c con = Connector_c.getOneGD_CONOnR8(text);
				if(con != null)
					object = GraphicalElement_c.getOneGD_GEOnR2(con).getRepresents();
				Shape_c shp = Shape_c.getOneGD_SHPOnR27(text);
				if(shp != null)
					object = GraphicalElement_c.getOneGD_GEOnR2(shp).getRepresents();
			}
		}
		return super.getPropertySource(object);
	}

}
