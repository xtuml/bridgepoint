package org.xtuml.bp.core.editors.providers;
//=====================================================================
//
//File:      $RCSfile: ModelCompareContentProvider.java,v $
//Version:   $Revision: 1.5.14.1 $
//Modified:  $Date: 2013/07/08 14:38:15 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.xtuml.bp.core.AttributeReferenceInClass_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.core.sorter.MetadataSortingManager;

public class MetaModelContentProvider implements ITreeContentProvider {
	
	ModelInspector inspector = new ModelInspector(MetadataSortingManager.createDefault());

	public Object getParent(Object element) {
		if(element instanceof NonRootModelElement) {
			return inspector.getParent(element);
		}
		return null;
	}
	
	public final boolean hasChildren(Object element) {
		Object[] children = getChildren(element);
		return children.length != 0;
	}
		
	public final Object[] getChildren(Object element) {
		if(element instanceof IModelEditorInput) {
			element = ((IModelEditorInput) element).getRepresents();
		}
		if(element instanceof IWorkspaceRoot) {
			// get and load all system models
			IWorkspaceRoot root = (IWorkspaceRoot) element;
			IProject[] projects = root.getProjects();
			boolean foundOneProject = false;
			for(int i = 0; i < projects.length; i++) {
				try {
					if(projects[i].hasNature(XtUMLNature.getNatureId())) {
						foundOneProject = true;
						break;
					}
				} catch (CoreException e) {
					CorePlugin.logError("Problem checking for xtuml nature.", e);
				}
			}
			if(foundOneProject) {
			      try {
			    	  PersistenceManager.getDefaultInstance();   // causes initialization
			      } catch (Exception e) {
			    	  CorePlugin.logError("Unable to initialize persistable components.", e);
			      }
			}
			return SystemModel_c.SystemModelInstances(Ooaofooa.getDefaultInstance());
		}
		// if the element is the selection instance the
		// elements in the selection are the children
		if(element instanceof NonRootModelElement) {
			IModelClassInspector modelInspector = inspector;
			// combine attributes and real children
 			ObjectElement[] attributes = getAttributefromInspector(element, modelInspector);
			ObjectElement[] referentials = modelInspector.getReferentials(element);
			// filter referentials
			List<ObjectElement> referentialList = new ArrayList<ObjectElement>();
			for(int i = 0; i < referentials.length; i++) {
				if (referentials[i].getParent() instanceof AttributeReferenceInClass_c) {
					continue;
				}
				referentialList.add(referentials[i]);
			}
			referentials = referentialList
					.toArray(new ObjectElement[referentialList.size()]);
			ObjectElement[] objectElementRelations = modelInspector.getChildRelations(element);
			List<Object> childRelations = new ArrayList<Object>();
			// convert child relations to NonRootModelElements
			for(int i = 0; i < objectElementRelations.length; i++) {
				if(objectElementRelations[i].getValue() instanceof NonRootModelElement) {
					if(!((NonRootModelElement) objectElementRelations[i].getValue()).isProxy()) {
						childRelations.add(objectElementRelations[i].getValue());	
					}
				} else {
					childRelations.add(objectElementRelations[i]);
				}
			}
			Object[] childRelationArray = childRelations.toArray();
			Object[] all = new Object[attributes.length
			                                        + referentials.length + childRelationArray.length];
			System.arraycopy(attributes, 0, all, 0, attributes.length);
			System.arraycopy(referentials, 0, all, attributes.length, referentials.length);
			System.arraycopy(childRelationArray, 0, all, attributes.length
					+ referentials.length, childRelationArray.length);
			return all;
		}
		return new Object[0];
	}

	private ObjectElement[] getAttributefromInspector(Object object, IModelClassInspector modelInspector){
		ObjectElement[] referentials = modelInspector.getAttributes(object);
		for (int i = 0 ; i < referentials.length; i++)
		{
			if(referentials[i] == null) {
				continue;
			}
			if( referentials[i].getValue() == null){
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)"ORPH1", object, false);//$NON-NLS-1$ 
			}else if( referentials[i].getValue().toString().indexOf("ORPH1")+1 > 0 )	//$NON-NLS-1$
			{
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)referentials[i].getValue().toString().replaceAll("ORPH1", ""), object, false);//$NON-NLS-1$
			}else if( referentials[i].getValue().toString().indexOf("ORPH2")+1 > 0 )	//$NON-NLS-1$
			{
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)referentials[i].getValue().toString().replaceAll("ORPH2", ""), object, false);//$NON-NLS-1$
			}			
		}
		return referentials;			
	}
	
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do
	}				

}
