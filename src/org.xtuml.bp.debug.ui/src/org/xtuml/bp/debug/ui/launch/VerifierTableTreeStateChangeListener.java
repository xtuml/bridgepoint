package org.xtuml.bp.debug.ui.launch;
//====================================================================
//
//File:      $RCSfile: VerifierTableTreeStateChangeListener.java,v $
//Version:   $Revision $
//Modified:  $Date: 2013/01/10 23:17:48 $
//
//(c) Copyright 2012-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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

import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;

import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;

/**
 * This CheckStateListener is specific to the ModelCheckedTreeViewer that is 
 * used in the Verifier debug launch configuration.
 * The ModelCheckedTreeViewer is implemented such that all checked elements are 
 * included in the tree selection.
 * This class allows us to maintain the list of elements to launch as well
 * as helps determine if an initializer has been selected yet or not.
 *
 */
public class VerifierTableTreeStateChangeListener implements
		ICheckStateListener {
	private VerifiableElementComposite parent;
	
	public VerifierTableTreeStateChangeListener(VerifiableElementComposite pParent) {
		parent = pParent;
	}
	
	public void checkStateChanged(CheckStateChangedEvent event) {
		handleCheckStateChanged(event);
		parent.updateControls();
	}

	/**
	 * When the check state changes we either add the element to
	 * the model (when it is checked), or remove the element from the map
	 * (when it is unchecked
	 * @param event
	 */
	private void handleCheckStateChanged(CheckStateChangedEvent event) {
		boolean wasAdded = event.getChecked();
		Object elementInvolved = event.getElement();
		// enable the checked model element
		Vector<String> vector = parent.getElementVector(BPDebugUtils
				.getElementsSystem(event.getElement()).getName());

		if (vector != null) {
			if (elementInvolved instanceof SystemModel_c) {
				// if the checked element was a system
				// model then we need to update all children
				Object[] children = getSystemChildren((SystemModel_c) elementInvolved);
				for (int i = 0; i < children.length; i++) {
					if (wasAdded) {
						parent.enableExecutionOfElement(vector,
								(NonRootModelElement) children[i]);
					} else {
						parent.disableExecutionOfElement(vector,
								(NonRootModelElement) children[i]);

					}
				}
			} else if (elementInvolved instanceof Package_c) {
				// if the checked element was a
				// package we need to update all children
				Object[] children = getPackageChildren((Package_c) elementInvolved);
				for (int i = 0; i < children.length; i++) {
					if (wasAdded) {
						parent.enableExecutionOfElement(vector,
								(NonRootModelElement) children[i]);
					} else {
						parent.disableExecutionOfElement(vector,
								(NonRootModelElement) children[i]);
					}
				}
			} else if (elementInvolved instanceof Component_c) {
				Object[] children = getComponentChildren((Component_c) elementInvolved);
				if (wasAdded) {
					// if the checked element was a component
					// we need to add all children, and self
					parent.enableExecutionOfElement(vector,
							(NonRootModelElement) elementInvolved);
				} else {
					parent.disableExecutionOfElement(vector,
							(NonRootModelElement) elementInvolved);
				}
				for (int i = 0; i < children.length; i++) {
					if (wasAdded) {
					parent.enableExecutionOfElement(vector,
							(NonRootModelElement) children[i]);
					} else {
						parent.disableExecutionOfElement(vector,
								(NonRootModelElement) children[i]);
					}
				}
			} else {
				if (wasAdded) {
					// otherwise just add the checked element
					parent.enableExecutionOfElement(vector,
							(NonRootModelElement) elementInvolved);
				} else {
					// otherwise just remove the unchecked element
					parent.disableExecutionOfElement(vector,
							(NonRootModelElement) elementInvolved);
				}
			}
		}
	}

	private NonRootModelElement[] getComponentChildren(Component_c component) {
		ArrayList<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		Component_c[] components = Component_c.getManyC_CsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8003(component));
		for (int i = 0; i < components.length; i++) {
			list.add(components[i]);
			NonRootModelElement[] children = getComponentChildren(components[i]);
			for (int j = 0; j < children.length; j++) {
				list.add(children[j]);
			}
		}
		ComponentReference_c[] compRefs = ComponentReference_c
				.getManyCL_ICsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(component));
		for (int i = 0; i < compRefs.length; i++) {
			list.add(compRefs[i]);
		}
		return list.toArray(new NonRootModelElement[list.size()]);
	}

	private NonRootModelElement[] getPackageChildren(Package_c pkg) {
		ArrayList<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		Component_c[] components = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkg));
		for (int i = 0; i < components.length; i++) {
			list.add(components[i]);
			NonRootModelElement[] children = getComponentChildren(components[i]);
			for (int j = 0; j < children.length; j++) {
				list.add(children[j]);
			}
		}
		ComponentReference_c[] compRefs = ComponentReference_c
				.getManyCL_ICsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkg));
		for (int i = 0; i < compRefs.length; i++) {
			list.add(compRefs[i]);
		}
		Package_c[] packages = Package_c
				.getManyEP_PKGsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(pkg));
		for (int i = 0; i < packages.length; i++) {
			list.add(packages[i]);
			NonRootModelElement[] children = getPackageChildren(packages[i]);
			for (int j = 0; j < children.length; j++) {
				list.add(children[j]);
			}
		}
		return list.toArray(new NonRootModelElement[list.size()]);
	}

	private Object[] getSystemChildren(SystemModel_c system) {
		Vector<NonRootModelElement> vector = new Vector<NonRootModelElement>();
		// get all components
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1405(system);
		for (int i = 0; i < packages.length; i++) {
			if (packages[i].Isexecutingorownsexecutableelements()) {
				vector.add(packages[i]);
			}
		}
		return vector.toArray();
	}

	private SystemModel_c getCheckedSystem(Object element) {
		if (element instanceof SystemModel_c) {
			return (SystemModel_c) element;
		}
		SystemModel_c system = null;
		return system;
	}

}
