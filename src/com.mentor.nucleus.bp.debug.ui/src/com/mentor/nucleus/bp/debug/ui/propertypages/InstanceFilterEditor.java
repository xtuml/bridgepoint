/*******************************************************************************
 * Copyright (c) 2000-2014 Mentor Graphics Corporation. All rights reserved.
 * Copyright 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.mentor.nucleus.bp.debug.ui.propertypages;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.mentor.nucleus.bp.core.ClassInEngine_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.debug.ui.ModelElementLocation;
import com.mentor.nucleus.bp.debug.ui.model.IBPBreakpoint;

/**
 * 
 */
public class InstanceFilterEditor {
	private IBPBreakpoint fBreakpoint;
	private CheckboxTreeViewer fInstanceViewer;
	private Composite fParent;
	private InstanceFilterContentProvider fContentProvider;
	private CheckHandler fCheckHandler;
	private InstanceFilterLabelProvider fLabelProvider;
	
	public InstanceFilterEditor(Composite parent, IBPBreakpoint breakpoint) {
		fBreakpoint= breakpoint;
		fContentProvider= new InstanceFilterContentProvider();
		fCheckHandler= new CheckHandler();
		fLabelProvider= new InstanceFilterLabelProvider();
		Label label= new Label(parent, SWT.NONE);
		label.setFont(parent.getFont());
		label.setText("Restrict to Selected Instance(s)"); 
		
		fParent= parent;
		createViewer();
	}
	
	/**
	 * Create and initialize the thread filter tree viewer.
	 */
	protected void createViewer() {
		GridData data= new GridData(GridData.FILL_BOTH);
		data.heightHint= 100;

		fInstanceViewer= new CheckboxTreeViewer(fParent, SWT.BORDER);
		fInstanceViewer.addCheckStateListener(fCheckHandler);
		fInstanceViewer.getTree().setLayoutData(data);
		fInstanceViewer.setContentProvider(fContentProvider);
		fInstanceViewer.setInput(fBreakpoint);
		fInstanceViewer.setLabelProvider(fLabelProvider);
		setInitialCheckedState();
	}

	/**
	 * Sets the initial checked state of the tree viewer.
	 * The initial state should reflect the current state
	 * of the breakpoint. If the breakpoint has an instance
	 * filter, that isntance should be checked.
	 */
	protected void setInitialCheckedState() {
		Instance_c[] objects = fBreakpoint.getInstanceFilters();
		for (int i= 0; i < objects.length; i++) {
			fCheckHandler.checkObject(objects[i], true);
		}
	}
	
	protected void doStore() {
		Object [] checkedItems = fInstanceViewer.getCheckedElements();
		fBreakpoint.clearInstanceFilters();
		for ( int i = 0; i < checkedItems.length; ++i ) {
			if ( checkedItems[i] instanceof Instance_c ) {
				fBreakpoint.addInstanceFilter((Instance_c)checkedItems[i]);
			}
		}						
	}
	
	class CheckHandler implements ICheckStateListener {	
		
		public void checkStateChanged(CheckStateChangedEvent event) {
			if ( event.getElement() instanceof ModelClass_c ) {
				Object [] children = ((ITreeContentProvider) fInstanceViewer.getContentProvider()).getChildren(event.getElement());
				for ( int i = 0; i < children.length; ++i ) {
					fInstanceViewer.setChecked(children[i], event.getChecked());
				}
				fInstanceViewer.setGrayed(event.getElement(), false);
			}
			fInstanceViewer.setChecked(event.getElement(), event.getChecked());
			if ( event.getElement() instanceof Instance_c ) {
				updateParentStatus(event.getElement(), event.getChecked());
			}
		}

		public void updateParentStatus(Object element, boolean checked) {
			ITreeContentProvider cp = (ITreeContentProvider) fInstanceViewer.getContentProvider();
			Object parent = cp.getParent(element);
			Object [] children = cp.getChildren(parent);
			boolean allTheSame = true;
			for ( int i = 0; i < children.length; ++i ) {
				if ( fInstanceViewer.getChecked(children[i]) != checked ) {
					allTheSame = false;
					break;
				}
			}
			if ( ! allTheSame ) {
				fInstanceViewer.setGrayChecked(parent, true);
			}
			else {
				fInstanceViewer.setGrayed(parent, false);
				fInstanceViewer.setChecked(parent, checked);
			}
		}
		
		public void checkObject(Instance_c object, boolean checked) {
			fInstanceViewer.setChecked(object, checked);
			updateParentStatus(object, checked);
		}
		
	}
	
	class InstanceFilterLabelProvider implements ILabelProvider {

		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			if (element instanceof ModelClass_c) {
				return ModelElementLocation.getModelElementLocation((ModelClass_c)element);
			}
			return element.toString();
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}
		
	}
	
	class InstanceFilterContentProvider implements ITreeContentProvider {
		
		/**
		 * @see ITreeContentProvider#getChildren(Object)
		 */
		public Object[] getChildren(Object parent) {
			if (parent instanceof IBPBreakpoint) {
				return ((IBPBreakpoint)parent).getAllClasses();
			}
			if (parent instanceof ClassInEngine_c) {
				return Instance_c.getManyI_INSsOnR2962((ClassInEngine_c)parent);
			}
			return new Object[0];
		}

		/**
		 * @see ITreeContentProvider#getParent(Object)
		 */
		public Object getParent(Object element) {
			if (element instanceof Instance_c) {
				return ClassInEngine_c.getOneCSME_CIEOnR2962((Instance_c)element);
			}
			if (element instanceof ModelClass_c) {
				return fBreakpoint;
			}
			return null;
		}

		/**
		 * @see ITreeContentProvider#hasChildren(Object)
		 */
		public boolean hasChildren(Object element) {
			if (element instanceof IBPBreakpoint) {
				return getChildren(element).length > 0;
			} 
			if (element instanceof ClassInEngine_c) {
				return Instance_c.getOneI_INSOnR2962((ClassInEngine_c)element) != null;
			}
			return false;
		}

		/**
		 * @see IStructuredContentProvider#getElements(Object)
		 */
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		/**
		 * @see IContentProvider#dispose()
		 */
		public void dispose() {
		}

		/**
		 * @see IContentProvider#inputChanged(Viewer, Object, Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
}
