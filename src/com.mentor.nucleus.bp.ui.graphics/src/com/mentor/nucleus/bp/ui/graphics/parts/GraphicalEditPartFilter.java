//========================================================================
//
//File:      $RCSfile: GraphicalEditPartFilter.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:54 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.ui.graphics.parts;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.ui.IActionFilter;

import com.mentor.nucleus.bp.ui.graphics.router.Routing;

public class GraphicalEditPartFilter implements IActionFilter {

	private static Object singleton;
	
	@Override
	public boolean testAttribute(Object target, String name, String value) {
		GraphicalEditPart part = (GraphicalEditPart) target;
		if (part instanceof ConnectorEditPart) {
			if (name.equals("routing")) { //$NON-NLS-1$
				if (value.equals("setRectilinearRouting")) { //$NON-NLS-1$
					ConnectorEditPart cPart = (ConnectorEditPart) part;
					if (cPart.getConnectionRouterTypeFromStorage().equals(Routing.OBLIQUE)) {
						return true;
					}
					return false;
				}
				if (value.equals("setObliqueRouting")) { //$NON-NLS-1$
					ConnectorEditPart cPart = (ConnectorEditPart) part;
					if (cPart.getConnectionRouterTypeFromStorage().equals(Routing.RECTILINEAR)) {
						return true;
					}
					return false;
				}
			}
		}
		if (name.equals("layer")) { //$NON-NLS-1$
			DiagramEditPart diagramPart = (DiagramEditPart) part.getViewer()
					.getContents();
			if (value.equals("addToNewLayer")) { //$NON-NLS-1$
				if (part instanceof DiagramEditPart
						&& diagramPart.getViewer().getSelectedEditParts()
								.size() == 1) {
					return false;
				}
				if (diagramPart.getContainerShape() == part) {
					// do not allow hiding of the container symbol
					return false;
				}
				return true;
			}
		}
		return false;
	}

	public static Object getSingleton() {
		if (singleton == null) {
			singleton = new GraphicalEditPartFilter();
		}
		return singleton;
	}

}
