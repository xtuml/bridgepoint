package com.mentor.nucleus.bp.debug.ui.launch;

//====================================================================
//
//File:      $RCSfile: VerifiableElementInitializerDialog.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:17:46 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ImportedProvision_c;
import com.mentor.nucleus.bp.core.ImportedRequirement_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.ui.explorer.ModelLabelProvider;

public class VerifiableElementInitializerDialog extends DialogCellEditor {
	VerifiableElementComposite parent = null;

	/**
	 * @Override
	 */
	public VerifiableElementInitializerDialog(
			VerifiableElementComposite p_parent) {
		super(p_parent.getTreeViewer().getTree());
		parent = p_parent;
	}
	
	/**
	 * When the user makes a selection from the tree in the initializer selection
	 * dialog, this class is used to validate and return the selection information.
	 */
	class VerifiableElementSelectionStatusValidator implements ISelectionStatusValidator {
		/**
		 * @implements
		 */
		public IStatus validate(Object[] selection) {
			Status okStatus = new Status(IStatus.OK, BPDebugUIPlugin
					.getUniqueIdentifier(), IStatus.OK, "", null);
			Status infoStatus = new Status(IStatus.ERROR, BPDebugUIPlugin
					.getUniqueIdentifier(), IStatus.WARNING,
					"Please select None, or a valid message.", null);
			if (selection.length == 0) {
				return infoStatus;
			}
			if (selection[0] instanceof Port_c) {
				return infoStatus;
			}
			if (selection[0] instanceof Requirement_c) {
				return infoStatus;
			}
			if (selection[0] instanceof Provision_c) {
				return infoStatus;
			}
			return okStatus;
		}		
	}

	/**
	 * This is the class used to provide the content to the tree that is in the 
	 * initializer selection  dialog.
	 *
	 */
	class ElementInitilizerTreeContentProvider implements ITreeContentProvider {
		ITreeContentProvider provider;
		
		public ElementInitilizerTreeContentProvider(ITreeContentProvider p_provider) {
			provider = p_provider;
		}
		
		public void inputChanged(Viewer viewer, Object oldInput,
				Object newInput) {
		}

		public void dispose() {
		}

		/**
		 * Populate the selection tree for the given element
		 */
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof String)
				return new Object[0];
			List<Object> elementList = new ArrayList<Object>();
			Object[] elements = provider.getElements(inputElement);
			for (int i = 0; i < elements.length; i++) {
				if (elements[i] instanceof Port_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof Provision_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof Requirement_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof RequiredOperation_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof RequiredSignal_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof ProvidedOperation_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof ProvidedSignal_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof ImportedProvision_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				} else if (elements[i] instanceof ImportedRequirement_c) {
					elementList
							.add((NonRootModelElement) elements[i]);
				}
			}
			if (inputElement instanceof Component_c
					|| inputElement instanceof ComponentReference_c) {
				elementList.add(0, VerifierLaunchConfiguration.ConfigurationAttribute.DefaultInitializer);
			}
			return elementList.toArray();
		}

		public boolean hasChildren(Object element) {
			return getElements(element).length != 0;
		}

		public Object getParent(Object element) {
			return provider.getParent(element);
		}

		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}		
	}
	
	/**
	 * Provide the labels for the tree nodes in the initializer selection tree
	 *
	 */
	class ElementInitilizerTreeLabelProvider implements ILabelProvider {
		ModelLabelProvider provider = new ModelLabelProvider();

		public ElementInitilizerTreeLabelProvider() {			
		}
		
		public void removeListener(ILabelProviderListener listener) {
		}

		public boolean isLabelProperty(Object element,
				String property) {
			return false;
		}

		public void dispose() {
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public String getText(Object element) {
			if (element instanceof String) {
				return (String) element;
			} else {
				return provider.getText(element);
			}
		}

		public Image getImage(Object element) {
			if (element instanceof String) {
				return null;
			} else {
				return provider.getImage(element);
			}
		}

	}
	
	/**
	 * @Override
	 */
	protected Object openDialogBox(Control cellEditorWindow) {
		final com.mentor.nucleus.bp.ui.explorer.ModelContentProvider provider = new com.mentor.nucleus.bp.ui.explorer.ModelContentProvider();
		final ElementTreeSelectionDialog initilizerSelectionDialog = new ElementTreeSelectionDialog(
				cellEditorWindow.getShell(),
				new ElementInitilizerTreeLabelProvider(),
				new ElementInitilizerTreeContentProvider(provider));
		initilizerSelectionDialog.setAllowMultiple(false);
		initilizerSelectionDialog.setDoubleClickSelects(true);
		initilizerSelectionDialog.setEmptyListMessage("No interfaces were found for the current selection.");
		initilizerSelectionDialog.setValidator(new VerifiableElementSelectionStatusValidator());
		
		Object currentSelection = parent.getTreeViewer().getCurrentSelection();
		initilizerSelectionDialog.setInput(currentSelection);
		initilizerSelectionDialog.setTitle("Select the initializer");
		int result = initilizerSelectionDialog.open();
		if (result == 0) {
			Object element = initilizerSelectionDialog.getFirstResult();
			if (element != null && element instanceof NonRootModelElement) {
				String id = getInitializerUUIDString((NonRootModelElement) element);
				setInitializerForElement(currentSelection, id);
			} else if (element != null && element instanceof String) {
				setInitializerForElement(currentSelection, (String) element);
			}
		}
		return initilizerSelectionDialog.getFirstResult();
	}

	protected NonRootModelElement getInitializerMessageElement(Object element) {
		if (element instanceof NonRootModelElement) {
			Vector<String> vector = parent.getElementVector(BPDebugUtils
					.getElementsSystem(element).getName());
			if (vector != null) {
				Iterator<String> iterator = vector.iterator();
				while (iterator.hasNext()) {
					String current = (String) iterator.next();
					if (current.startsWith(((NonRootModelElement) element)
							.Get_ooa_id().toString())) {
						String id = VerifierLaunchConfiguration.getInternalElement(current, VerifierLaunchConfiguration.ConfigurationAttribute.Initializer);
						if (!id.equals(VerifierLaunchConfiguration.ConfigurationAttribute.DefaultInitializer)) {
							Component_c component = null;
							if (element instanceof Component_c) {
								component = (Component_c) element;
							} else if (element instanceof ComponentReference_c) {
								component = Component_c
										.getOneC_COnR4201((ComponentReference_c) element);
							}
							return getInitializerMessageFromUUIDString(
									component, id);
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param element
	 * @return The current initializer string associated with the specified
	 *         element.
	 */
	protected String getInitializerForElement(Object element) {
		String resultInitializer = VerifierLaunchConfiguration.ConfigurationAttribute.DefaultInitializer;
		if (element instanceof NonRootModelElement) {
			Vector<String> modelVector = parent.getElementVector(BPDebugUtils
					.getElementsSystem(element).getName());
			if (modelVector != null) {
				Iterator<String> modelIterator = modelVector.iterator();
				while (modelIterator.hasNext()) {
					String currentLaunchConfigModel = (String) modelIterator.next();
					if (currentLaunchConfigModel.startsWith(((NonRootModelElement) element)
							.Get_ooa_id().toString())) {
						String initializerID = VerifierLaunchConfiguration
								.getInternalElement(
										currentLaunchConfigModel,
										VerifierLaunchConfiguration.ConfigurationAttribute.Initializer);
						NonRootModelElement initializerElement = getInitializerMessageFromUUIDString(
								(NonRootModelElement) element, initializerID);
						if (initializerElement != null) {
							resultInitializer = initializerElement.getName();
						} else {
							resultInitializer = initializerID;
						}
						break;
					}
				}
			}
		}
		return resultInitializer;
	}

	public static NonRootModelElement getInitializerMessageFromUUIDString(
			NonRootModelElement parent, final String id) {
		Component_c component = null;
		if (parent instanceof Component_c) {
			component = (Component_c) parent;
		} else if (parent instanceof ComponentReference_c) {
			component = Component_c
					.getOneC_COnR4201((ComponentReference_c) parent);
		} else {
			return null;
		}
		RequiredOperation_c requiredOp = RequiredOperation_c
				.getOneSPR_ROOnR4502(
						RequiredExecutableProperty_c
								.getManySPR_REPsOnR4500(Requirement_c
										.getManyC_RsOnR4009(InterfaceReference_c
												.getManyC_IRsOnR4016(Port_c
														.getManyC_POsOnR4010(component)))),
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((RequiredOperation_c) candidate)
										.getId().toString().equals(id);
							}

						});
		if (requiredOp != null) {
			return requiredOp;
		}
		RequiredSignal_c requiredSig = RequiredSignal_c
				.getOneSPR_RSOnR4502(
						RequiredExecutableProperty_c
								.getManySPR_REPsOnR4500(Requirement_c
										.getManyC_RsOnR4009(InterfaceReference_c
												.getManyC_IRsOnR4016(Port_c
														.getManyC_POsOnR4010(component)))),
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((RequiredSignal_c) candidate).getId()
										.toString().equals(id);
							}

						});
		if (requiredSig != null) {
			return requiredSig;
		}
		ProvidedOperation_c providedOp = ProvidedOperation_c
				.getOneSPR_POOnR4503(
						ProvidedExecutableProperty_c
								.getManySPR_PEPsOnR4501(Provision_c
										.getManyC_PsOnR4009(InterfaceReference_c
												.getManyC_IRsOnR4016(Port_c
														.getManyC_POsOnR4010(component)))),
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((ProvidedOperation_c) candidate)
										.getId().toString().equals(id);
							}

						});
		if (providedOp != null) {
			return providedOp;
		}
		ProvidedSignal_c providedSig = ProvidedSignal_c.getOneSPR_PSOnR4503(
				ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(component)))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((ProvidedSignal_c) candidate).getId()
								.toString().equals(id);
					}

				});
		if (providedSig != null) {
			return providedSig;
		}
		return null;
	}

	protected void setInitializerForElement(Object element, String initializer) {
		String mult = VerifierLaunchConfiguration.ConfigurationAttribute.DefaultMultiplicity;
		String enablement = VerifierLaunchConfiguration.DISABLED_STATE;
		String match = "";
		if (element instanceof NonRootModelElement) {
			Vector<String> vector = parent.getElementVector(BPDebugUtils
					.getElementsSystem(element).getName());

			// remove the old values
			Iterator<String> iterator = vector.iterator();
			while (iterator.hasNext()) {
				String current = (String) iterator.next();
				if (current.startsWith(((NonRootModelElement) element)
						.Get_ooa_id().toString())) {
					match = current;
					break;
				}
			}
			if (!match.equals("")) {
				vector.remove(match);
				mult = VerifierLaunchConfiguration.getInternalElement(match, VerifierLaunchConfiguration.ConfigurationAttribute.Multiplicity);
				enablement = VerifierLaunchConfiguration.getInternalElement(match, VerifierLaunchConfiguration.ConfigurationAttribute.State);
			}

			String newResult = VerifierLaunchConfiguration
					.getComponentSelectionString(((NonRootModelElement) element)
							.Get_ooa_id().toString(), mult, initializer, enablement);
			vector.add(newResult);

			parent.getTreeViewer().refresh(element);
			if (!match.equals(newResult)) {
				parent.updateControls();
			}
		}
	}

	protected String getInitializerUUIDString(NonRootModelElement element) {
		if (element instanceof RequiredOperation_c) {
			return ((RequiredOperation_c) element).getId().toString();
		} else if (element instanceof ProvidedOperation_c) {
			return ((ProvidedOperation_c) element).getId().toString();
		} else if (element instanceof RequiredSignal_c) {
			return ((RequiredSignal_c) element).getId().toString();
		} else if (element instanceof ProvidedSignal_c) {
			return ((ProvidedSignal_c) element).getId().toString();
		}
		return "";
	}

}
