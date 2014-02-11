//========================================================================
//
//File:      $RCSfile: AdapterFactory.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/17 03:29:20 $
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
package com.mentor.nucleus.bp.ui.graphics.factories;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdapterFactory;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;

public class AdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof ShapeEditPart
				&& NonRootModelElement.class.isAssignableFrom(adapterType)) {
			ShapeEditPart part = (ShapeEditPart) adaptableObject;
			if(adapterType.equals(NonRootModelElement.class))
				// just return the element
				return part.getModelElement();
			if(part.getModelElement().getClass().equals(adapterType))
				return part.getModelElement();
		}
		if (adaptableObject instanceof ConnectorEditPart
				&& NonRootModelElement.class.isAssignableFrom(adapterType)) {
			ConnectorEditPart part = (ConnectorEditPart) adaptableObject;
			if(adapterType.equals(NonRootModelElement.class))
				// just return the element
				return part.getModelElement();
			if(part.getModelElement().getClass().equals(adapterType))
				return part.getModelElement();
		}
		if (adaptableObject instanceof TextEditPart
				&& NonRootModelElement.class.isAssignableFrom(adapterType)) {
			TextEditPart part = (TextEditPart) adaptableObject;
			if(adapterType.equals(NonRootModelElement.class))
				// just return the element
				return part.getModelElement();
			if(part.getModelElement().getClass().equals(adapterType))
				return part.getModelElement();
		}
		if (adaptableObject instanceof DiagramEditPart
				&& NonRootModelElement.class.isAssignableFrom(adapterType)) {
			DiagramEditPart part = (DiagramEditPart) adaptableObject;
			if(adapterType.equals(NonRootModelElement.class))
				// just return the element
				return ((Model_c) part.getModel()).getRepresents();
			if(((Model_c) part.getModel()).getRepresents().getClass().equals(adapterType))
				return ((Model_c) part.getModel()).getRepresents();
		}
		if(adaptableObject instanceof DiagramEditPart && IFile.class == adapterType) {
			DiagramEditPart part = (DiagramEditPart) adaptableObject;
			return ((Model_c) part.getModel()).getFile();
		}
		if(adaptableObject instanceof ShapeEditPart && IFile.class == adapterType) {
			ShapeEditPart part = (ShapeEditPart) adaptableObject;
			return ((Shape_c) part.getModel()).getFile();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class[] getAdapterList() {
		ElementSpecification_c[] specs = ElementSpecification_c
				.ElementSpecificationInstances(Ooaofgraphics
						.getDefaultInstance());
		ArrayList<Class> list = new ArrayList<Class>();
		for(int i = 0; i < specs.length; i++) {
			list.add(specs[i].getRepresents());
		}
		ModelSpecification_c[] modelSpecs = ModelSpecification_c
				.ModelSpecificationInstances(Ooaofgraphics.getDefaultInstance());
		for(int i = 0; i < modelSpecs.length; i++) {
			list.add(modelSpecs[i].getRepresents());
		}
		list.add(NonRootModelElement.class);
		list.add(IFile.class);
		list.add(IFolder.class);
		return (Class[]) list.toArray(new Class[list.size()]);
	}

}
