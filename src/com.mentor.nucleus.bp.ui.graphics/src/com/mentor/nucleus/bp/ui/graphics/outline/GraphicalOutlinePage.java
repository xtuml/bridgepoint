//========================================================================
//
//File:      $RCSfile: GraphicalOutlinePage.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:06:21 $
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
package com.mentor.nucleus.bp.ui.graphics.outline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.NewWizardMenu;
import org.eclipse.ui.part.PageBook;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Scope_c;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.ModelOutlineSorter;
import com.mentor.nucleus.bp.ui.explorer.ModelContentProvider;
import com.mentor.nucleus.bp.ui.explorer.ModelLabelProvider;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class GraphicalOutlinePage extends ContentOutlinePage {

	private PageBook pageBook;
	private Canvas overview;
	static final int ID_OUTLINE = 0;
	static final int ID_OVERVIEW = 1;
	static final int ID_SPLIT = 3;
	private Thumbnail thumbnail;
	private DisposeListener disposeListener;
	private GraphicalEditor editor;
	private Control outline;

	private static final String OPEN = "open";
	private static final String SORT = "Sort";
	private static final String HIDE_STATICS = "Hide Static Members";
	private static final String HIDE_OPERATIONS = "Hide Operations";
	protected Action cut, copy, paste, undo, redo;
	protected Action open, delete, rename;
	protected Action sort, hideStatics, hideOps;
	protected ViewerFilter filterStatics, filterOps;
	private boolean sorting = false;
	private ITransactionListener m_transactionListener;
	private TreeViewer treeViewer;
	private IAction showOutlineAction, showOverviewAction;

	public GraphicalOutlinePage(EditPartViewer viewer, GraphicalEditor editor) {
		super(viewer);
		this.editor = editor;
	}

	protected void configureOutlineViewer() {
		IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
		showOutlineAction = new Action() {
			public void run() {
				showPage(ID_OUTLINE);
			}
		};
		showOutlineAction.setImageDescriptor(CorePlugin
				.getImageDescriptor("outline.gif")); //$NON-NLS-1$
		showOutlineAction.setToolTipText("Click here to show the outline tree");
		tbm.add(showOutlineAction);
		showOverviewAction = new Action() {
			public void run() {
				showPage(ID_OVERVIEW);
			}
		};
		showOverviewAction.setImageDescriptor(CorePlugin
				.getImageDescriptor("overview.gif")); //$NON-NLS-1$
		showOverviewAction.setToolTipText("Click here to show a thumbnail view of the diagram");
		tbm.add(showOverviewAction);
		showPage(ID_OUTLINE);
	}

	public void createControl(Composite parent) {
		pageBook = new PageBook(parent, SWT.NONE);
		overview = new Canvas(pageBook, SWT.NONE);
		outline = createOutline(pageBook);
		configureOutlineViewer();
		getSite().setSelectionProvider(editor.getSite().getSelectionProvider());
	}

	private Control createOutline(Composite parent) {
		if (editor.getModel() != null) {
			if (editor.getModel().getRepresents() != null
					&& editor.getModel().getRepresents() instanceof NonRootModelElement) {
				Object selected = editor.getModel().getRepresents();
				if (selected instanceof Domain_c) {
					Component_c comp = Component_c
							.getOneC_COnR4204(DomainAsComponent_c
									.getOneCN_DCOnR4204((Domain_c) selected));
					if (comp != null)
						selected = comp;
				}
				treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
				treeViewer.setContentProvider(new ModelContentProvider());
				treeViewer.setLabelProvider(new ModelLabelProvider());
				createActions();
				createMenus();
				createToolbar();
				createFilters();
				hookListeners();
				treeViewer.setInput(selected);
				getSite().getActionBars().setGlobalActionHandler(
						ActionFactory.DELETE.getId(), delete);
				getSite().getActionBars().setGlobalActionHandler(
						ActionFactory.RENAME.getId(), rename);
				getSite().getActionBars().setGlobalActionHandler(
						ActionFactory.CUT.getId(), cut);
				getSite().getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), copy);
				getSite().getActionBars().setGlobalActionHandler(
						ActionFactory.PASTE.getId(), paste);
				getSite().getActionBars().setGlobalActionHandler(
						ActionFactory.UNDO.getId(), undo);
				getSite().getActionBars().setGlobalActionHandler(
						ActionFactory.REDO.getId(), redo);
				return treeViewer.getControl();
			}
		}
		return null;
	}

	public Object getAdapter(Class<?> type) {
		if (type == ZoomManager.class)
			return editor.getGraphicalViewer().getProperty(
					ZoomManager.class.toString());
		return null;
	}

	public Control getControl() {
		return pageBook;
	}

	protected void initializeOverview() {
		LightweightSystem lws = new LightweightSystem(overview);
		RootEditPart rep = editor.getGraphicalViewer().getRootEditPart();
		if (rep instanceof ScalableFreeformRootEditPart) {
			ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
			thumbnail = new ScrollableThumbnail((Viewport) root.getFigure());
			((IFigure) thumbnail.getChildren().get(0)).addFigureListener(new FigureListener() {
			
				@Override
				public void figureMoved(IFigure source) {
					// have the editor store the newly set
					// viewport location
					editor.storeViewportLocation();
				}
			});
			thumbnail.setSource(root.getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			disposeListener = new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			editor.getCanvas().addDisposeListener(disposeListener);
		}
	}

	public void setContents(Object contents) {
		getViewer().setContents(contents);
		DiagramEditPart rootPart = (DiagramEditPart) getViewer().getContents();
		rootPart.setAsOutline();
	}

	protected void showPage(int id) {
		if (id == ID_OUTLINE && outline != null) {
			showOutlineAction.setChecked(true);
			showOverviewAction.setChecked(false);
			pageBook.showPage(outline);
			outline.setParent(pageBook);
			if (thumbnail != null)
				thumbnail.setVisible(false);
		} else if (id == ID_OVERVIEW || outline == null) {
			if (thumbnail == null)
				initializeOverview();
			showOutlineAction.setChecked(false);
			showOverviewAction.setChecked(true);
			pageBook.showPage(overview);
			overview.setParent(pageBook);
			thumbnail.setVisible(true);
		}
	}

	protected void hookListeners() {
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					return;
				}
				if (event.getSelection() instanceof IStructuredSelection) {
					editor.getSite().getSelectionProvider().setSelection(event.getSelection());
					// add the selection to core as well
					Selection.getInstance().setSelection(event.getSelection());
				}
			}
		});
		m_transactionListener = new ITransactionListener() {
			@Override
			public void transactionCancelled(Transaction transaction) {
				// do nothing
			}

			@Override
			public void transactionEnded(Transaction transaction) {
				UIUtil.refreshViewer(treeViewer, null);
			}

			@Override
			public void transactionStarted(Transaction transaction) {
				// do nothing
			}
		};
		TransactionManager.getSingleton().addTransactionListener(m_transactionListener);

		treeViewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				handleOpen();
			}
		});
	}

	public void selectionChanged(SelectionChangedEvent event) {
		Selection.getInstance().setSelection(event.getSelection());
	}

	protected void createActions() {
		// 'New' is provided as a menu only. See 'createMenus'
		open = new Action(OPEN) {
			public void run() {
				handleOpen();
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this model Element");
		// 'Open With' is provided as a menu only. See 'createMenus'
		cut = new Action(ActionFactory.CUT.getId()) {
			public void run() {
			}
		};
		cut.setText("Cut");
		cut.setToolTipText("Cut this model Element to the clipboard");
		cut.setImageDescriptor(CorePlugin.getImageDescriptor("cut_edit.gif")); // $$NON-NLS-1$$
		cut.setEnabled(false); // Retargetable Actions work removes this line
		copy = new Action(ActionFactory.COPY.getId()) {
			public void run() {
			}
		};
		copy.setText("Copy");
		copy.setToolTipText("Copy this model Element to the clipboard");
		copy.setImageDescriptor(CorePlugin.getImageDescriptor("copy_edit.gif")); // $$NON-NLS-1$$
		copy.setEnabled(false); // Retargetable Actions work removes this line
		paste = new Action(ActionFactory.PASTE.getId()) {
			public void run() {
			}
		};
		paste.setText("Paste");
		paste.setToolTipText("Paste from the clipboard");
		paste.setImageDescriptor(CorePlugin
				.getImageDescriptor("paste_edit.gif")); // $$NON-NLS-1$$
		paste.setEnabled(false); // Retargetable Actions work removes this line

		//
		// Delete and Rename are retargetable actions defined by core.
		//
		delete = CorePlugin.getDeleteAction();
		rename = CorePlugin.getRenameAction(treeViewer);
		sort = new Action(SORT) {
			public void run() {
				sorting = !sorting;
				if (sorting) {
					treeViewer.setSorter(new ModelOutlineSorter());
				} else {
					treeViewer.setSorter(null);
				}
			}
		};
		sort.setChecked(false);
		sort.setToolTipText(SORT);
		sort
				.setImageDescriptor(CorePlugin
						.getImageDescriptor("alpha_mode.gif")); //$NON-NLS-1$
		hideStatics = new Action(HIDE_STATICS) {
			public void run() {
				updateFilter(hideStatics);
			}
		};
		hideStatics.setChecked(false);
		hideStatics.setToolTipText(HIDE_STATICS);
		hideStatics.setImageDescriptor(CorePlugin
				.getImageDescriptor("static_co.gif")); //$NON-NLS-1$
		hideOps = new Action(HIDE_OPERATIONS) {
			public void run() {
				updateFilter(hideOps);
			}
		};
		hideOps.setChecked(false);
		hideOps.setToolTipText(HIDE_OPERATIONS);
		hideOps.setImageDescriptor(CorePlugin
				.getImageDescriptor("methpub_obj_co.gif")); //$NON-NLS-1$
	}

	protected void createFilters() {
		filterStatics = new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof Operation_c) {
					return (((Operation_c) element).getInstance_based() == Scope_c.Instance);
				}
				if (element instanceof Function_c) {
					return false;
				}
				return true;
			}
		};
		filterOps = new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				return !(element instanceof Operation_c);
			}
		};
	}

	protected void createMenus() {

		// Context Menu
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		final MenuManager createMenuManager = new MenuManager(
				"Ne&w", "com.mentor.nucleus.bp.ui.newroot"); //$NON-NLS-2$
		final MenuManager openMenuManager = new MenuManager(
				"Open Wit&h", "com.mentor.nucleus.bp.ui.openroot"); //$NON-NLS-2$
		
		final MenuManager classesMenu = new MenuManager("Classes", "com.mentor.nucleus.bp.ui.classroot"); //$NON-NLS-2$
		final MenuManager componentsMenu = new MenuManager("Components", "com.mentor.nucleus.bp.ui.componentroot"); //$NON-NLS-2$
		final MenuManager externalMenu = new MenuManager("External", "com.mentor.nucleus.bp.ui.externalroot"); //$NON-NLS-2$
		final MenuManager interactionMenu = new MenuManager("Interaction", "com.mentor.nucleus.bp.ui.interactionroot"); //$NON-NLS-2$
		final MenuManager activityMenu = new MenuManager("Activity", "com.mentor.nucleus.bp.ui.activityroot"); //$NON-NLS-2$
		final MenuManager typesMenu = new MenuManager("Types", "com.mentor.nucleus.bp.ui.typeroot"); //$NON-NLS-2$
		final MenuManager useCaseMenu = new MenuManager("Usecase", "com.mentor.nucleus.bp.ui.usecaseroot"); //$NON-NLS-2$
		
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				mgr.add(createMenuManager);
				createMenuManager.removeAll();
				createMenuManager.add(new GroupMarker(
						"com.mentor.nucleus.bp.ui.newmenu")); //$NON-NLS-1$
				createMenuManager.add(activityMenu);
				activityMenu.removeAll();
				activityMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newactivitymenu") );
				
				createMenuManager.add(classesMenu);
				classesMenu.removeAll();
				classesMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newclassmenu") );
				
				createMenuManager.add(componentsMenu);
				componentsMenu.removeAll();
				componentsMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newcomponentmenu") );
				
				createMenuManager.add(externalMenu);
				externalMenu.removeAll();
				externalMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newexternalmenu") );
				
				createMenuManager.add(interactionMenu);
				interactionMenu.removeAll();
				interactionMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newinteractionmenu") );
				
				createMenuManager.add(typesMenu);
				typesMenu.removeAll();
				typesMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newtypemenu") );
				
				createMenuManager.add(useCaseMenu);
				useCaseMenu.removeAll();
				useCaseMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newusecasemenu") );
				
				createMenuManager.add(new NewWizardMenu(getSite()
						.getWorkbenchWindow()));
				mgr.add(open);
				mgr.add(openMenuManager);
				openMenuManager.removeAll();
				openMenuManager.add(new GroupMarker(
						"com.mentor.nucleus.bp.ui.openmenu")); //$NON-NLS-1$
				mgr.add(new Separator(
						"com.mentor.nucleus.bp.ui.context-internal")); //$NON-NLS-1$
				mgr.add(new Separator(
						"com.mentor.nucleus.bp.ui.context-internal-end")); //$NON-NLS-1$
				mgr.add(cut);
				mgr.add(copy);
				mgr.add(paste);
				mgr.add(new Separator());
				mgr.add(delete);
				mgr.add(rename);
				mgr.add(new Separator());
				mgr.add(new Separator());
				// If this is omitted, the platform complains because
				// it can't allow third party plug-ins to extend the menu
				// This is important for those who wish to use change
				// management plugins because Team menu items are added here.
				mgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		Menu menu = menuManager.createContextMenu(treeViewer.getTree());
		treeViewer.getTree().setMenu(menu);
		getSite().setSelectionProvider(Selection.getInstance());
		getSite().registerContextMenu(
				"com.mentor.nucleus.bp.ui.graphics.outline.menu", menuManager,
				Selection.getInstance());
	}

	/* Multiple filters can be enabled at a time. */
	protected void updateFilter(Action action) {
		if (action == hideStatics) {
			if (action.isChecked()) {
				treeViewer.addFilter(filterStatics);
			} else {
				treeViewer.removeFilter(filterStatics);
			}
			return;
		}
		if (action == hideOps) {
			if (action.isChecked()) {
				treeViewer.addFilter(filterOps);
			} else {
				treeViewer.removeFilter(filterOps);
			}
			return;
		}
	}

	protected void createToolbar() {
		IToolBarManager toolBarManager = getSite().getActionBars()
				.getToolBarManager();
		toolBarManager.add(sort);
		toolBarManager.add(hideStatics);
		toolBarManager.add(hideOps);
	}

	/**
	 * Fire up an editor
	 */
	private void handleOpen() {
		IStructuredSelection sel = Selection.getInstance()
				.getStructuredSelection();
		if (sel.isEmpty()) {
			return;
		}
		Object current = (sel).iterator().next();
		String name = current.getClass().getName();

		// see if the current element should open
		// something other than itself
		current = EditorUtil.getElementToEdit(current);
		//
		// Get the registry
		//
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		//
		// Get all the plugins that have extended this point
		//
		IExtensionPoint extPt = reg
				.getExtensionPoint("com.mentor.nucleus.bp.core.editors");
		IExtension[] exts = extPt.getExtensions();
		// Repeat for each extension until we find a default editor
		for (int i = 0; i < exts.length; i++) {
			IConfigurationElement[] elems = exts[i].getConfigurationElements();
			for (int j = 0; j < elems.length; j++) {
				// Find the editor elements
				if (elems[j].getName().equals("editor")) {
					IConfigurationElement[] edElems = elems[j].getChildren();
					for (int k = 0; k < edElems.length; k++) {
						//
						// Is this editor the default for the current model
						// element ?
						//
						if (edElems[k].getName().equals("defaultFor")
								&& edElems[k].getAttribute("class")
										.equals(name)) {
							try {
								//
								// Get the class supplied for the input
								//
								Bundle bundle = Platform
										.getBundle(elems[j]
												.getDeclaringExtension()
												.getNamespaceIdentifier());
								Class<?> inputClass = bundle.loadClass(elems[j]
										.getAttribute("input")); //$NON-NLS-1$
								Class<?>[] type = new Class[1];
								type[0] = Object.class;
								//
								// Dynamically get the method createInstance,
								// the supplied class must implement this
								//
								Method createInstance = inputClass.getMethod(
										"createInstance", type); //$NON-NLS-1$
								Object[] args = new Object[1];
								args[0] = current;
								//
								// Invoke the method.
								// The method is static; no instance is needed,
								// so first argument is null
								//
								IEditorInput input = (IEditorInput) createInstance
										.invoke(null, args);
								//
								// pass the input to the Eclipse editor, along
								// with the class name supplied by
								// the extending plugin.
								//
								if (input != null) {
									PlatformUI
											.getWorkbench()
											.getActiveWorkbenchWindow()
											.getActivePage()
											.openEditor(
													input,
													elems[j]
															.getAttribute("class")); //$NON-NLS-1$
								}
								return;
							} catch (ClassNotFoundException e) {
								CanvasPlugin.logError(
										"Input Class not found", e); //$NON-NLS-1$
							} catch (NoSuchMethodException e) {
								CanvasPlugin
										.logError(
												"Class does not implement static method createInstance", e); //$NON-NLS-1$
							} catch (InvocationTargetException e) {
								CanvasPlugin
										.logError(
												"Exception occured on invocation of static method createInstance of the Target", e.getTargetException()); //$NON-NLS-1$
							} catch (IllegalAccessException e) {
								CanvasPlugin
										.logError(
												"Target does not support static method createInstance", e); //$NON-NLS-1$
							} catch (PartInitException e) {
								CanvasPlugin.logError(
										"Could not activate Editor", e); //$NON-NLS-1$
							}
						}
					}
				}
			}
		}
	}

	public void dispose() {
		if (thumbnail != null) {
			thumbnail.deactivate();
			thumbnail = null;
		}
		super.dispose();
		if(m_transactionListener != null) {
			TransactionManager.getSingleton()
					.removeTransactionListener(m_transactionListener);
		}
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

}
