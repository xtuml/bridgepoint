package org.xtuml.bp.core.editors.tree;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.editors.IRefreshableEditor;
import org.xtuml.bp.core.editors.ModelEditor;
import org.xtuml.bp.core.editors.actions.TreeCopyAction;
import org.xtuml.bp.core.editors.actions.TreeCutAction;
import org.xtuml.bp.core.editors.actions.TreePasteAction;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.core.editors.tree.pages.MetamodelPage;
import org.xtuml.bp.core.ui.Selection;

public class TreeEditor implements IEditorPart, IRefreshableEditor {

	private MetamodelPage treePage;
	public ModelEditor modelEditor;
	private Image titleImage;
	private IEditorInput input;
	private IWorkbenchPartSite site;
	// Actions
	protected Action cut, copy, paste;
	protected Action undo, redo;
	protected Action delete, rename;
	protected Action fileImport, fileExport;
	
	public TreeEditor(ModelEditor modelEditor) {
		this.modelEditor = modelEditor;
	}

	@Override
	public void addPropertyListener(IPropertyListener listener) {
		// not necessary for tree editor
	}

	@Override
	public void createPartControl(Composite parent) {
		treePage = new MetamodelPage(parent, getEditorInput());
		treePage.getTreeViewer().setInput(((IModelEditorInput) getEditorInput()).getRepresents()); 
		treePage.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// update core selection
				Selection.getInstance().setSelection(event.getSelection());
			}
		});
		createActions();
		createMenus();
	}

	private void createMenus() {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		final MenuManager createMenuManager = new MenuManager("Ne&w", "org.xtuml.bp.ui.newroot"); //$NON-NLS-2$
		final MenuManager classesMenu = new MenuManager("Classes", "org.xtuml.bp.ui.classroot"); //$NON-NLS-2$
		final MenuManager componentsMenu = new MenuManager("Components", "org.xtuml.bp.ui.componentroot"); //$NON-NLS-2$
		final MenuManager exceptionMenu = new MenuManager("Exceptions", "org.xtuml.bp.ui.exceptionroot"); //$NON-NLS-2$
		final MenuManager externalMenu = new MenuManager("External", "org.xtuml.bp.ui.externalroot"); //$NON-NLS-2$
		final MenuManager interactionMenu = new MenuManager("Interaction", "org.xtuml.bp.ui.interactionroot"); //$NON-NLS-2$
		final MenuManager activityMenu = new MenuManager("Activity", "org.xtuml.bp.ui.activityroot");//$NON-NLS-2$
		final MenuManager typesMenu = new MenuManager("Types", "org.xtuml.bp.ui.typeroot"); //$NON-NLS-2$
		final MenuManager useCaseMenu = new MenuManager("Usecase", "org.xtuml.bp.ui.usecaseroot"); //$NON-NLS-2$

		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				mgr.add(createMenuManager);
				createMenuManager.removeAll();
				createMenuManager.add(new GroupMarker("org.xtuml.bp.ui.newmenu")); //$NON-NLS-1$

				createMenuManager.add(activityMenu);
				activityMenu.removeAll();
				activityMenu.add(new GroupMarker("org.xtuml.bp.ui.newactivitymenu"));

				createMenuManager.add(classesMenu);
				classesMenu.removeAll();
				classesMenu.add(new GroupMarker("org.xtuml.bp.ui.newclassmenu"));

				createMenuManager.add(componentsMenu);
				componentsMenu.removeAll();
				componentsMenu.add(new GroupMarker("org.xtuml.bp.ui.newcomponentmenu"));

				createMenuManager.add(exceptionMenu);
				exceptionMenu.removeAll();
				exceptionMenu.add(new GroupMarker("org.xtuml.bp.ui.newexceptionmenu"));

				createMenuManager.add(externalMenu);
				externalMenu.removeAll();
				externalMenu.add(new GroupMarker("org.xtuml.bp.ui.newexternalmenu"));

				createMenuManager.add(interactionMenu);
				interactionMenu.removeAll();
				interactionMenu.add(new GroupMarker("org.xtuml.bp.ui.newinteractionmenu"));

				createMenuManager.add(typesMenu);
				typesMenu.removeAll();
				typesMenu.add(new GroupMarker("org.xtuml.bp.ui.newtypemenu"));

				createMenuManager.add(useCaseMenu);
				useCaseMenu.removeAll();
				useCaseMenu.add(new GroupMarker("org.xtuml.bp.ui.newusecasemenu"));

				mgr.add(new Separator("org.xtuml.bp.ui.context-internal")); //$NON-NLS-1$
				mgr.add(new Separator("org.xtuml.bp.ui.context-internal-end")); //$NON-NLS-1$
				mgr.add(new Separator());
				mgr.add(undo);
				mgr.add(redo);
				mgr.add(new Separator());
				mgr.add(cut);
				cut.setEnabled(cut.isEnabled());
				mgr.add(copy);
				copy.setEnabled(copy.isEnabled());
				mgr.add(paste);
				paste.setEnabled(paste.isEnabled());
				mgr.add(new Separator());
				mgr.add(delete);
				mgr.add(rename);
				mgr.add(new Separator());
				mgr.add(fileImport);
				mgr.add(fileExport);
				// If this is omitted, the platform complains because
				// it can't allow third party plug-ins to extend the menu
				// This is important for those who wish to use change
				// management plugins because Team menu items are added here.
				mgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		Menu menu = menuManager.createContextMenu(treePage.getTreeViewer().getTree());
		treePage.getTreeViewer().getTree().setMenu(menu);
		getSite().registerContextMenu(menuManager, Selection.getInstance());
	}

	private void createActions() {
		// 'New' is provided as a sub-menu only. See 'createMenus'
		// 'Open With' is provided as a sub-menu only. See 'createMenus'
		undo = TransactionManager.getSingleton().getUndoAction();
		redo = TransactionManager.getSingleton().getRedoAction();
		cut = new TreeCutAction(treePage.getTreeViewer());
		copy = new TreeCopyAction(treePage.getTreeViewer());
		paste = new TreePasteAction();
		// Delete and Rename are retargetable actions defined by core.
		//
		delete = CorePlugin.getDeleteAction();
		rename = CorePlugin.getRenameAction(treePage.getTreeViewer());
		//
		fileImport = CorePlugin.getResourceImportAction();
		fileExport = CorePlugin.getResourceExportAction();
	}
	
	@Override
	public void dispose() {
		// nothing to do here
	}

	@Override
	public IWorkbenchPartSite getSite() {
		return site;
	}

	@Override
	public String getTitle() {
		return getEditorInput().getName();
	}

	@Override
	public Image getTitleImage() {
		if(titleImage == null || titleImage.isDisposed()) {
			titleImage = getEditorInput().getImageDescriptor().createImage();
		}
		return titleImage;
	}

	@Override
	public String getTitleToolTip() {
		return getEditorInput().getToolTipText();
	}

	@Override
	public void removePropertyListener(IPropertyListener listener) {
		// nothing to do here
	}

	@Override
	public void setFocus() {
		treePage.setFocus();
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// auto save enabled for tree
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		return false;
	}

	@Override
	public IEditorInput getEditorInput() {
		return input;
	}

	@Override
	public IEditorSite getEditorSite() {
		return (IEditorSite) getSite();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		site.setSelectionProvider(Selection.getInstance());
		this.input = input;
	}

	@Override
	public void refresh() {
		treePage.getTreeViewer().refresh();
	}

}
