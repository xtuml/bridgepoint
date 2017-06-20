//========================================================================
//
//File:      $RCSfile: ModelEditor.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/13 19:02:46 $
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

package org.xtuml.bp.core.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.core.editors.input.ModelEditorInput;
import org.xtuml.bp.core.ui.Selection;

public class ModelEditor extends MultiPageEditorPart implements ISelectionChangedListener {
	private List<IEditorTabFactory> focusBasedFactories = new ArrayList<IEditorTabFactory>();

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		Selection.getInstance().addSelectionChangedListener(this);
	}

	@Override
	protected void createPages() {
		createPagesFromExtensionPoint(getContainer(), (IModelEditorInput) getEditorInput());
	}

	private void createPagesFromExtensionPoint(Composite container, IModelEditorInput input) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extPt = reg.getExtensionPoint("org.xtuml.bp.core.editors.editorTab"); //$NON-NLS-1$
		IExtension[] extensions = extPt.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] configurationElements = extensions[i].getConfigurationElements();
			for (int j = 0; j < configurationElements.length; j++) {
				if (configurationElements[j].getName().equals("EditorTab")) { //$NON-NLS-1$
					Object selection = configurationElements[j].getAttribute("Input"); //$NON-NLS-1$
					try {
						boolean focusBased = Boolean.valueOf(configurationElements[j].getAttribute("focusBased")); //$NON-NLS-1$
						if (focusBased) {
							storeFocusBasedTab(configurationElements[j], selection);
						}
						if (selection.equals(input.getRepresents().getClass().getName())
								|| selection.equals(NonRootModelElement.class.getName())) {
							if (!focusBased) {
								Object tabFactory = configurationElements[j]
										.createExecutableExtension("EditorTabFactory"); //$NON-NLS-1$
								String editorTitle = configurationElements[j].getAttribute("EditorTitle"); //$NON-NLS-1$
								// if not focus based create the editor tab now
								if (tabFactory instanceof IEditorTabFactory) {
									IEditorTabFactory factory = (IEditorTabFactory) tabFactory;
									factory.setEditorTitle(editorTitle);
									createPageFromFactory(factory, container, input);
								}
							}
						}
					} catch (InvalidRegistryObjectException e) {
						CorePlugin.logError("Unable to load registered class by name.", e);
					} catch (CoreException e) {
						CorePlugin.logError("Unable to load editor tab factory from configuration.", e);
					}
				}
			}
		}
	}

	private void storeFocusBasedTab(IConfigurationElement configuration, Object input) {
		try {
			Object tabFactory = configuration.createExecutableExtension("EditorTabFactory"); //$NON-NLS-1$
			String editorTitle = configuration.getAttribute("EditorTitle"); //$NON-NLS-1$
			if (tabFactory instanceof IEditorTabFactory) {
				((IEditorTabFactory) tabFactory).setFocusBased(true);
				((IEditorTabFactory) tabFactory).setEditorTitle(editorTitle);
				((IEditorTabFactory) tabFactory).setInput(input);
				focusBasedFactories.add((IEditorTabFactory) tabFactory);

			}
		} catch (CoreException e) {
			CorePlugin.logError("Unable to create editor tab factory.", e);
		}

	}

	private void createPageFromFactory(IEditorTabFactory tabFactory, Composite container, IModelEditorInput input) {
		IEditorTabFactory factory = (IEditorTabFactory) tabFactory;
		factory.setInput(input.getRepresents());
		Composite composite = factory.createEditorTab(this, container, input);
		// if composite is null the factory will have
		// created a full editor and UI will be setup
		// later
		if(composite != null) {
			int index = getPageCount();
			addPage(index, (Composite) composite);
			setPageText(index, tabFactory.getEditorTitle());
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// ask each tab to save
		for(int index = 0; index < getPageCount(); index++) {
			IEditorPart editor = getEditor(index);
			if(editor != null) {
				editor.doSave(monitor);
			}
		}
	}

	@Override
	public void doSaveAs() {
		// no save as currently supported
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public IEditorPart getActivePart() {
		return getActiveEditor();
	}

	@Override
	public String getTitleToolTip() {
		// if an editor ask for its title tooltip
		IEditorPart activeEditor = getActiveEditor();
		if(activeEditor != null) {
			return activeEditor.getTitleToolTip();
		}
		return super.getTitleToolTip();
	}

	@Override
	public Image getTitleImage() {
		IEditorPart activeEditor = getActiveEditor();
		if(activeEditor != null) {
			return activeEditor.getTitleImage();
		}
		return super.getTitleImage();
	}

	@Override
	public String getTitle() {
		return getPartName();
	}

	@Override
	public String getPartName() {
		IEditorPart activeEditor = getActiveEditor();
		if(activeEditor != null) {
			return activeEditor.getTitle();
		}
		return super.getPartName();
	}

	@Override
	public void dispose() {
		super.dispose();
		Selection.getInstance().removeSelectionChangedListener(this);
	}

	public void refresh() {
		IEditorPart activeEditor = getActiveEditor();
		if(activeEditor != null) {
			((IRefreshableEditor) activeEditor).refresh();
		}
	}
	
	public void setPageText(int index, String text) {
		super.setPageText(index, text);
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// see if any tabs need to be added, or removed
		for (IEditorTabFactory factory : focusBasedFactories) {
			// look for a tab with the same configured type
			// let any opened tabs worry about a selection change
			// to refresh their content
			Object configuredInput = factory.getInput();
			// if the input is an element already we will be
			// updating the selection but need the class name
			if (!(configuredInput instanceof String)) {
				configuredInput = configuredInput.getClass().getName();
			}
			// for now only support single selections
			IStructuredSelection currentSelection = (IStructuredSelection) event.getSelection();
			if (currentSelection.size() == 1) {
				// if the selection is a graphical element convert to
				// core element
				Object selected = Selection.getInstance().adaptElement(currentSelection.getFirstElement());
				// create any non-existing editors
				try {
					if (configuredInput.equals(selected.getClass().getSimpleName()) || configuredInput.equals(selected.getClass().getName()) || Class.forName((String) configuredInput).isInstance(selected)) {
						int pageCount = getPageCount();
						boolean foundExistingEditor = false;
						for (int i = 0; i < pageCount; i++) {
							if (getPageText(i).equals(factory.getEditorTitle())) {
								// found do not create a new tab
								foundExistingEditor = true;
								// update input for current tab
								factory.setInput(selected);
								break;
							}
						}
						if (!foundExistingEditor) {
							// create a tab now
							IModelEditorInput input = new ModelEditorInput(selected);
							createPageFromFactory(factory, getContainer(), input);
						}
					} else {
						// current selection does not match, check all open
						// editors matching this factory and close
						for (int i = 0; i < getPageCount(); i++) {
							if (getPageText(i).equals(factory.getEditorTitle())) {
								// editor input and title match close here
								removePage(i);
							}
						}
					}
				} catch (ClassNotFoundException e) {
					CorePlugin.logError("Unable to locate configured factory input class.", e);
				}
			} else {
				// close all editors of this type
				for (int i = 0; i < getPageCount(); i++) {
					if (getPageText(i).equals(factory.getEditorTitle())) {
						removePage(i);
					}
				}
			}
		}
	}

}
