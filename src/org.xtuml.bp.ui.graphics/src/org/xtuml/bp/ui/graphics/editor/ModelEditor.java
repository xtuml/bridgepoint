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

package org.xtuml.bp.ui.graphics.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.InfoForm;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.util.GraphicsUtil;
import org.xtuml.bp.ui.explorer.ILinkWithEditorListener;
import org.xtuml.bp.ui.graphics.Activator;
import org.xtuml.bp.ui.graphics.parts.ShapeEditPart;

public class ModelEditor extends MultiPageEditorPart implements ILinkWithEditorListener, ISelectionChangedListener {

	private GraphicalEditor fGraphicalEditor;
	private List<IEditorTabFactory> focusBasedFactories = new ArrayList<IEditorTabFactory>();

	@Override
	protected void createPages() {
		fGraphicalEditor = createGraphicalEditor(getContainer());
		if(fGraphicalEditor != null)
			createPagesFromExtensionPoint(getContainer(), fGraphicalEditor.getModel());
	}

	@SuppressWarnings("deprecation")
	private GraphicalEditor createGraphicalEditor(Composite container) {
		GraphicalEditor editor = new GraphicalEditor(this);
		try {
			String result = checkEditorInput(editor);
			if(!result.equals("")) {
				InfoForm form = new InfoForm(container);
				form.setInfo(result);
				int index = addPage(form.getControl());
				setPageText(index, "Information");
				return null;
			}
			int addPage = addPage(editor, getEditorInput());
			setPageText(addPage, "Graphical Editor");
			Selection.getInstance().addSelectionChangedListener(this);
		} catch (PartInitException e) {
			Activator.logError("Unable to create graphical editor.", e);
		}
		return editor;
	}

	private String checkEditorInput(GraphicalEditor editor) {
		IEditorInput input = getEditorInput();
		String result = "";
		if(input instanceof FileEditorInput) {
			IPath path = ((FileEditorInput) input).getFile().getFullPath();
			if (path != null) {
				PersistableModelComponent pmc = PersistenceManager
						.findOrCreateComponent(path);
				if(pmc != null && pmc.getComponentType().equals("ModelClass")) { //$NON-NLS-1$
					if(!pmc.isLoaded())
						try {
							pmc.load(new NullProgressMonitor());
						} catch (CoreException e) {
							CorePlugin.logError("Unable to load PMC.", e);
						}
					setPartName(GraphicsUtil.getCanvasEditorTitle(pmc.getRootModelElement()));
					result = "Use Model Explorer to edit Model Class descriptions.";
				}
				if(pmc == null) {
					result = "Could not locate underlying model element.";
			}
		}
		}
		return result;
	}

	private void createPagesFromExtensionPoint(Composite container, Model_c model) {
	    IExtensionRegistry reg = Platform.getExtensionRegistry();
	    IExtensionPoint extPt = reg.getExtensionPoint("org.xtuml.bp.ui.graphics.editorTab"); //$NON-NLS-1$
	    IExtension[] extensions = extPt.getExtensions();
	    for(int i = 0; i < extensions.length; i++) {
	    	IConfigurationElement[] configurationElements = extensions[i].getConfigurationElements();
	    	for (int j = 0; j < configurationElements.length; j++) {
	    		if (configurationElements[j].getName().equals("EditorTab")) { //$NON-NLS-1$
	    			Object selection = configurationElements[j].getAttribute("Input"); //$NON-NLS-1$
	    			try {
	    				boolean focusBased = Boolean.valueOf(configurationElements[j].getAttribute("focusBased")); //$NON-NLS-1$
	    				if(focusBased) {
	    					storeFocusBasedTab(configurationElements[j], selection);
	    				}
						if(selection.equals(model.getRepresents().getClass().getName()) || selection.equals(NonRootModelElement.class.getName())) {
							if(!focusBased) {
								Object tabFactory = configurationElements[j].createExecutableExtension("EditorTabFactory"); //$NON-NLS-1$
								String editorTitle = configurationElements[j].getAttribute("EditorTitle"); //$NON-NLS-1$
								// if not focus based create the editor tab now
								if(tabFactory instanceof IEditorTabFactory) {
									IEditorTabFactory factory = (IEditorTabFactory) tabFactory;
									factory.setEditorTitle(editorTitle);
									createPageFromFactory(factory, container, model.getRepresents());
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
			if(tabFactory instanceof IEditorTabFactory) {
				((IEditorTabFactory) tabFactory).setFocusBased(true);
				((IEditorTabFactory) tabFactory).setEditorTitle(editorTitle);
				((IEditorTabFactory) tabFactory).setInput(input);
				focusBasedFactories.add((IEditorTabFactory) tabFactory);
				
			}
		} catch (CoreException e) {
			CorePlugin.logError("Unable to create editor tab factory.", e);
		}
		
	}

	private void createPageFromFactory(IEditorTabFactory tabFactory, Composite container, Object represents) {
		IEditorTabFactory factory = (IEditorTabFactory) tabFactory;
		factory.setInput(represents.getClass().getName());
		Composite composite = factory.createEditorTab(container, represents);
		int newPage = addPage((Composite) composite);
		setPageText(newPage, tabFactory.getEditorTitle());	
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if(fGraphicalEditor == null)
			return;
		fGraphicalEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		if(fGraphicalEditor == null)
			return;
		fGraphicalEditor.doSaveAs();
	}

	@Override
	public boolean isSaveAsAllowed() {
		if(fGraphicalEditor == null)
			return false;
		return fGraphicalEditor.isSaveAsAllowed();
	}

	public IEditorPart getActivePart() {
		return getActiveEditor();
	}

	@Override
	public String getTitleToolTip() {
		if(fGraphicalEditor == null)
			return "";
		return fGraphicalEditor.getTitleToolTip();
	}
	
	@Override
	public Image getTitleImage() {
		Object element = this.fGraphicalEditor.getModel().getRepresents();
		return CorePlugin.getImageFor(element);
	}
	
	
	@Override
	public String getTitle() {
		return getPartName();
	}
	
	@Override
	public String getPartName() {
		if(fGraphicalEditor == null)
			return super.getPartName();
		return fGraphicalEditor.getPartName();
	}
	
	public GraphicalEditor getGraphicalEditor() {
		return fGraphicalEditor;
	}

	@Override
	public void dispose() {
		super.dispose();
		Selection.getInstance().removeSelectionChangedListener(this);
	}

	public void refresh() {
		if(fGraphicalEditor != null && !fGraphicalEditor.getCanvas().isDisposed()) {
			fGraphicalEditor.refresh();
		}
	}
	
	@Override
	public void notifySelectionLink() {
		if(getGraphicalEditor() == null) { return; }
		// this notification is sent immediately after
		// a selection has been transferred from the explorer
		// tree
		// we only want to zoom selected if the selection is not
		// already visible
		IStructuredSelection selection = (IStructuredSelection) getGraphicalEditor()
				.getEditorSite().getSelectionProvider().getSelection();
		boolean zoomSelected = false;
		for(Object selected : selection.toList()) {
			if(selected instanceof AbstractGraphicalEditPart) {
				IFigure figure = ((AbstractGraphicalEditPart) selected)
						.getFigure();
				Viewport viewport = ((FigureCanvas) getGraphicalEditor()
						.getCanvas()).getViewport();
				Rectangle viewportBounds = viewport.getBounds().getCopy();
				Rectangle figureBounds = figure.getBounds().getCopy();
				figure.translateToAbsolute(figureBounds);
				if(!viewportBounds.intersects(figureBounds)) {
					zoomSelected = true;
					break;
				}
			}
		}
		if(zoomSelected) {
			getGraphicalEditor().zoomSelected();
		}
	}

	@Override
	public NonRootModelElement getFirstSelectedElement() {
		if(getGraphicalEditor() == null) { return null; }
		IStructuredSelection selection = (IStructuredSelection) getGraphicalEditor()
				.getEditorSite().getSelectionProvider().getSelection();
		for(Object selected : selection.toList()) {
			if (selected instanceof ShapeEditPart) {
				NonRootModelElement nrme = (NonRootModelElement) ((ShapeEditPart)selected).getGraphicalElement().getRepresents();
				return nrme;
			}
		}
		return null;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// see if any tabs need to be added, or removed
		for(IEditorTabFactory factory : focusBasedFactories) {
			// look for a tab with the same configured type
			// let any opened tabs worry about a selection change
			// to refresh their content
			Object configuredInput = factory.getInput();
			// for now only support single selections
			IStructuredSelection currentSelection = (IStructuredSelection) event.getSelection();
			if(currentSelection.size() == 1) {
				// create any non-existing editors
				if(configuredInput.equals(currentSelection.getFirstElement().getClass().getName())) {
					int pageCount = getPageCount();
					boolean foundExistingEditor = false;
					for(int i = 0; i < pageCount; i++) {
						if (getPageText(i).equals(factory.getEditorTitle())) {
							// found do not create a new tab
							foundExistingEditor = true;
							break;
						}
					}
					if(!foundExistingEditor) {
						// create a tab now
						createPageFromFactory(factory, getContainer(), currentSelection.getFirstElement());
					}
				} else {
					// current selection does not match, check all open
					// editors matching this factory and close
					for(int i = 0; i < getPageCount(); i++) {
						if (getPageText(i).equals(factory.getEditorTitle())) {
							// editor input and title match close here
							removePage(i);
						}
					}
				}
			} else { 
				// close all editors of this type
				for(int i = 0; i < getPageCount(); i++) {
					if(getPageText(i).equals(factory)) {
						removePage(i);
					}
				}
			}
		}
	}
}
