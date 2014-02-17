//=====================================================================
//
//File:      $RCSfile: ModelCheckedTreeViewer.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/10/12 22:55:16 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.ui.tree;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.Selection;

public class ModelCheckedTreeViewer extends CheckboxTreeViewer {

	protected Selection fSelection = Selection.getInstance();
	private HashMap<Object, List<Object>> fChildrenSelected = new HashMap<Object, List<Object>>();
	private IStructuredSelection fOriginalSelection;
	boolean fExclusiveCheckBoxes = false;
	private ListenerList internalCheckStateChangeListeners = new ListenerList();
	private boolean linkedWithSelection = false;
	private boolean includeCheckItemsInSelection = true;
	
	public ModelCheckedTreeViewer(Composite parent, boolean exclusiveCheckBoxes) {
		super(parent);
		fExclusiveCheckBoxes = exclusiveCheckBoxes;
		includeCheckItemsInSelection = true;
	}

	public ModelCheckedTreeViewer(Composite parent, int style, boolean exclusiveCheckBoxes) {
		super(parent, style);
		fExclusiveCheckBoxes = exclusiveCheckBoxes;
		includeCheckItemsInSelection = true;
	}

	public ModelCheckedTreeViewer(Composite parent, int style, boolean exclusiveCheckBoxes, boolean p_includeCheckedItemsInSelection) {
		super(parent, style);
		fExclusiveCheckBoxes = exclusiveCheckBoxes;		
		includeCheckItemsInSelection = p_includeCheckedItemsInSelection;
		if(!includeCheckItemsInSelection) {
			// add a selection listener
			addSelectionChangedListener(new ISelectionChangedListener() {
				
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					fSelection.setSelection(getSelection());
				}
			});
		}
	}
	
	public void initialize() {
		setUseHashlookup(true);
		setComparer(new IElementComparer() {

			public int hashCode(Object element) {
				return System.identityHashCode(element);
			}

			public boolean equals(Object a, Object b) {
				return a == b;
			}

		});
		setInput(Ooaofooa.getDefaultInstance());
		
		addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				handleCheckStateChanged(event);
			}
		});

		addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChanged(event);
			}
		});

		// store original selection
		fOriginalSelection = fSelection.getStructuredSelection();
		fSelection.clear();
		
		// initialize check marks
		if(linkedWithSelection )
			initializeCheckedObjects();
	}
	
	public void setLinkedWithSelection(boolean value) {
		linkedWithSelection = value;
	}
	
	protected void handleCheckStateChanged(CheckStateChangedEvent event) {
		if (event.getChecked()) {
			if (fExclusiveCheckBoxes) {
				// we want exclusive check boxes
				// un select everything now
				Object[] checkedElements = getCheckedElements();
				for (int i = 0; i < checkedElements.length; i++) {
					// ignore the checked element from this event
					if(!(event.getElement() == checkedElements[i]))
						setChecked(checkedElements[i], false);
				}
			}
		} else {
			// if this element is grayed and being unchecked
			// remove the grey from the check box
			if(getGrayed(event.getElement())) {
				setGrayed(event.getElement(), false);
			}
		}
		setChecked(event.getElement(), event.getChecked());
	}

	private void changeChildSelectionFor(ITreeContentProvider provider, Object element, boolean checkedState) {
		Object[] children = provider.getChildren(element);
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				setSubtreeChecked(children[i], checkedState);
				changeChildSelectionFor(provider, children[i], checkedState);
				updateSelection(children[i], checkedState);
			}
		}
	}


	private void initializeCheckedObjects() {
		Object[] objects = fOriginalSelection.toArray();
		for(int i = 0; i < objects.length; i++) {
			if(!getChecked(objects[i])) {
				boolean checkResult = setChecked(objects[i], true);
				if(checkResult) {
					setGrayCheckedForParents(objects[i], true);
				}
			}
			// if exclusive checks set, then only
			// handle the first selection
			if(i == 0 && fExclusiveCheckBoxes) {
				break;
			}
		}
	}
	
	protected void handleSelectionChanged(SelectionChangedEvent event) {
		IStructuredSelection ss = (IStructuredSelection) event.getSelection();
		Object[] objects = ss.toArray();
		for(int i = 0; i < objects.length; i++) {
			// TODO: Add link support between selection and checks
		}
	}

	public void setGrayCheckedForParents(Object element, boolean state) {
		ITreeContentProvider provider = (ITreeContentProvider) getContentProvider();
		Object parent = provider.getParent(element);
		while (parent != null) {
			setGrayChecked(parent, state);
			parent = provider.getParent(parent);
		}
	}

	public void setLayoutData(GridData data) {
		getTree().setLayoutData(data);
	}
	
	public void dispose() {
		// restore the original selection
		fSelection.setSelection(fOriginalSelection);
	}

	@Override
	public boolean setGrayChecked(Object element, boolean state) {
		if(element instanceof Ooaofooa) return false;
		ITreeContentProvider provider = (ITreeContentProvider) getContentProvider();
		Object[] children = provider.getChildren(element);
		boolean oneOrMoreChildrenNotChecked = false;
		boolean noneSelected = true;
		if(children != null && children.length > 0) {
			for(int i = 0; i < children.length; i++) {
				if(!getChecked(children[i])) {
					oneOrMoreChildrenNotChecked = true;
				} else {
					noneSelected = false;
				}
			}

			if(noneSelected) {
				// non are selected un-select the element
				if(getChecked(element)) {
					if(getGrayed(element)) {
						super.setGrayed(element, false);
					}
					return super.setChecked(element, false);
				} else {
					// do nothing
					return false;
				}
			}
			if(oneOrMoreChildrenNotChecked) {
				return super.setGrayChecked(element, true);
			} else {
				super.setGrayChecked(element, false);
				return super.setChecked(element, true);
			}
		} return false;
	}

	public void updateSelection(Object element, boolean state) {
		if(element instanceof Ooaofooa) {
			return;
		} 
		// we prevent system models from being selected
		// until they are actually exportable, unless
		// exclusive check boxes are set (then we are dealing
		// with import)
		else if (element instanceof SystemModel_c && !fExclusiveCheckBoxes && state) {
			return;
		}
		if(state) {
			fSelection.addToSelection(element, false);
		} else {
			fSelection.removeFromSelection(element, false);
		}
	}

	@Override
	public boolean setChecked(Object element, boolean state) {
		boolean result = super.setChecked(element, state);
		if(result) {
			if (includeCheckItemsInSelection) {
				updateSelection(element, state);
			}
			changeChildSelectionFor((ITreeContentProvider) getContentProvider(), element, state);
			setGrayChecked(element, state);
			setGrayCheckedForParents(element, state);
			fireInternalCheckStateChanged(this, element, state);
		}
		return result;
	}

	private void fireInternalCheckStateChanged(ICheckable checkable, Object element, boolean state) {
		Object[] listeners = internalCheckStateChangeListeners.getListeners();
		for(int i = 0; i < listeners.length; i++) {
			ICheckStateListener listener = (ICheckStateListener) listeners[i];
			CheckStateChangedEvent event = new CheckStateChangedEvent(checkable, element, state);
			listener.checkStateChanged(event);
		}
	}
	
	public void addInternalCheckStateChangeListener(ICheckStateListener listener) {
		internalCheckStateChangeListeners.add(listener);
	}
	
	public void removeInternalCheckStateChangeListener(ICheckStateListener listener) {
		internalCheckStateChangeListeners.remove(listener);
	}
	
	@Override
	public ISelection getSelection() {
		if(!includeCheckItemsInSelection) {
			return super.getSelection();
		}
		return fSelection.getStructuredSelection();
	}

	public Object getCurrentSelection() {
		return getTree().getSelection()[0].getData();
	}

}
