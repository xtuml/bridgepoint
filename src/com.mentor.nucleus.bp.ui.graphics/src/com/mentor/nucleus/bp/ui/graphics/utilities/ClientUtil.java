//========================================================================
//
//File:      $RCSfile: ClientUtil.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:24 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;

import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class ClientUtil {

	public static Object getConfigurationInheritanceElementId(Object represents) {
		if(represents == null) {
			return null;
		}
		try {
			Method InheritanceMethod = represents.getClass().getMethod(
					"Getconfigurationinheritanceelementid", new Class[0]);
			return InheritanceMethod.invoke(represents, new Object[0]);
		} catch (SecurityException e) {
			// log security exception
			CanvasPlugin.logError(
					"Unable to locate configuration inhertence element.", e);
		} catch (NoSuchMethodException e) {
			// ignore this exception as not all elements
			// are expected to provide this method
		} catch (IllegalArgumentException e) {
			CanvasPlugin.logError(
					"Unable to invoke configuration inhertence element.", e);
		} catch (IllegalAccessException e) {
			CanvasPlugin.logError(
					"Unable to invoke configuration inhertence element.", e);
		} catch (InvocationTargetException e) {
			CanvasPlugin.logError(
					"Unable to invoke configuration inhertence element.", e);
		}
		return null;
	}

	/**
	 * Locates and returns the elements that are in the inheritance hierarchy.
	 * The return is an ordered list, with youngest first
	 * 
	 * @param modelRoot
	 * @param represents
	 * @param model
	 * @return
	 */
	public static List<GraphicalElement_c> getInheritanceGraphicalElements(
			Object represents, Model_c model, GraphicalEditPart editPart) {
		GraphicalViewer graphicalViewer = (GraphicalViewer) editPart.getViewer();
		DiagramEditPart diagramPart = (DiagramEditPart) graphicalViewer
				.getContents();
		List<GraphicalElement_c> inheritance = new ArrayList<GraphicalElement_c>();
		if(represents == null) {
			return inheritance;
		}
		GraphicalElement_c element = getInheritanceGraphicalElement(represents,
				model, editPart);
		if (element != null) {
			inheritance.add(element);
			if (diagramPart.getContainerShape() != null
					&& GraphicalElement_c.getOneGD_GEOnR2(
							(Shape_c) diagramPart.getContainerShape()
									.getModel()).getRepresents() == represents) {
				// only look at the first element;
				return inheritance;
			}
			GraphicalElement_c nextElement = getInheritanceGraphicalElement(
					element.getRepresents(), model, editPart);
			while (nextElement != null) {
				inheritance.add(nextElement);
				nextElement = getInheritanceGraphicalElement(nextElement
						.getRepresents(), model, editPart);
			}
		}
		return inheritance;
	}

	public static GraphicalElement_c getInheritanceGraphicalElement(
			Object represents, Model_c model, GraphicalEditPart editPart) {
		Object object = getConfigurationInheritanceElementId(represents);
		GraphicalViewer graphicalViewer = (GraphicalViewer) editPart.getViewer();
		
		DiagramEditPart diagramPart = (DiagramEditPart) graphicalViewer
				.getContents();
		boolean skipObjectEqualsRepresentsCheck = false;
		if (diagramPart.getContainerShape() != null
				&& GraphicalElement_c.getOneGD_GEOnR2(
						(Shape_c) diagramPart.getContainerShape()
								.getModel()).getRepresents() == represents) {
			// for container shapes, see if there is a parent
			// which can be used
			object = represents;
			skipObjectEqualsRepresentsCheck = true;
		}
		if (object == null
				|| (object == represents && !skipObjectEqualsRepresentsCheck)) {
			return null;
		}
		// must load the parent as well, as that is where the graphics are stored
		try {
			PersistableModelComponent persistableComponent = ((NonRootModelElement) object).getPersistableComponent();
			if(persistableComponent != null) {
				if(persistableComponent.getParent() != null) {
					persistableComponent.getParent()
							.load(new NullProgressMonitor());
				}
			}
		} catch (CoreException e) {
			CanvasPlugin.logError(
					"Unable to load graphics for inheritance object.", e);
		}
		UUID elementId = ((NonRootModelElement) object).Get_ooa_id();
		GraphicalElement_c element = Activator.getElementMap().getElement(elementId);
		if(element == null && !elementId.equals(Gd_c.Null_unique_id())) {
			// the element was not included in the map for some
			// reason fall back to the slow search, and add the
			// found element to the map
			Model_c[] models = Model_c.ModelInstances(Ooaofgraphics
					.getInstance(((NonRootModelElement) object).getModelRoot()
							.getId()));
			for(int i = 0; i < models.length; i++) {
				boolean found = false;
				GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(models[i]);
				for(int j = 0; j < elements.length; j++) {
					if(elements[j].getOoa_id().equals(elementId)) {
						element = elements[j];
						Activator.getElementMap().addElement(element, elementId);
						found = true;
						break;
					}
				}
				if(found) {
					break;
				}
			}
		}
		return element;
	}


}
