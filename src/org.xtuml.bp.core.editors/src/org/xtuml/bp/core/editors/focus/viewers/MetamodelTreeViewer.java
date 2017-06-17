package org.xtuml.bp.core.editors.focus.viewers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.NewWizardMenu;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.editors.ErrorToolTip;
import org.xtuml.bp.core.editors.ITabErrorSupport;
import org.xtuml.bp.core.editors.actions.TreeCopyAction;
import org.xtuml.bp.core.editors.actions.TreeCutAction;
import org.xtuml.bp.core.editors.actions.TreePasteAction;
import org.xtuml.bp.core.editors.editing.ElementEditingSupport;
import org.xtuml.bp.core.editors.providers.MetaModelContentProvider;
import org.xtuml.bp.core.editors.providers.MetaModelLabelProvider;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.core.ui.Selection;

public class MetamodelTreeViewer extends TreeViewer implements ISelectionChangedListener, ITabErrorSupport {

	private ErrorToolTip tip;
	private TreeViewerColumn viewerColumn;
	// Actions
	protected Action cut, copy, paste;
	protected Action undo, redo;
	protected Action delete, rename;
	protected Action fileImport, fileExport;

	public MetamodelTreeViewer(Composite parent, Object objectInput) {
		super(parent, SWT.H_SCROLL
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.DOUBLE_BUFFERED
				| SWT.NO_BACKGROUND | SWT.BORDER);
		initialize(objectInput);
	}

	private void initialize(Object objectInput) {
		getTree().setHeaderVisible(true);
		getTree().setLinesVisible(true);
		createActions();
		createMenus();
		getTree().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// transfer selection to core selection
				ISelection selection = getSelection();
				Selection.getInstance().setSelection(adaptSelection(selection), false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		// configure content and label providers
		setContentProvider(new MetaModelContentProvider());
		final MetaModelLabelProvider labelProvider = new MetaModelLabelProvider();
		setLabelProvider(labelProvider);
		Selection.getInstance().addSelectionChangedListener(this);
		TransactionManager.getSingleton().addTransactionListener(new ITransactionListener() {
			@Override
			public void transactionEnded(Transaction transaction) {
				PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
					// do not refresh if widget has not yet
					// been created
					if(!getTree().isDisposed()) {
						refresh();
					}
				});
			}
		});
		TreeColumn rootColumn = new TreeColumn(getTree(), SWT.LEAD);
		rootColumn.setText("Elements");
		viewerColumn = new TreeViewerColumn(this, SWT.LEAD);
		viewerColumn.setLabelProvider(new CellLabelProvider() {
			
			@Override
			public void update(ViewerCell cell) {
				cell.setText(labelProvider.getColumnText(cell.getElement(), 1));
			}
		});
		viewerColumn.getColumn().setText("Values");
		viewerColumn.getColumn().setData("index", 1);
		viewerColumn.setEditingSupport(new ElementEditingSupport(this, (Item) viewerColumn.getColumn()));
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50));
		layout.addColumnData(new ColumnWeightData(50));
		getTree().setLayout(layout);
		setInput(objectInput);
	}

	protected ISelection adaptSelection(ISelection selection) {
		// convert selected ObjectElements to NonRootModelElements
		Set<NonRootModelElement> selected = new HashSet<NonRootModelElement>();
		List<?> currentSelection = ((IStructuredSelection) selection).toList();
		for(Object object : currentSelection) {
			if(object instanceof ObjectElement) {
				selected.add((NonRootModelElement) ((ObjectElement) object).getParent());
			} else {
				selected.add((NonRootModelElement) object);
			}
		}
		IStructuredSelection newSelection = new StructuredSelection(selected.toArray());
		return newSelection;
	}

	private void createActions() {
		// 'New' is provided as a sub-menu only. See 'createMenus'
		// 'Open With' is provided as a sub-menu only. See 'createMenus'
		undo = TransactionManager.getSingleton().getUndoAction();
		redo = TransactionManager.getSingleton().getRedoAction();
		cut = new TreeCutAction(this);
		copy = new TreeCopyAction(this);
		paste = new TreePasteAction();
		// Delete and Rename are retargetable actions defined by core.
		//
		delete = CorePlugin.getDeleteAction();
		rename = CorePlugin.getRenameAction(this);
		//
		fileImport = CorePlugin.getResourceImportAction();
		fileExport = CorePlugin.getResourceExportAction();
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
		Menu menu = menuManager.createContextMenu(getTree());
		getTree().setMenu(menu);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		setInput(Selection.getInstance());
	}

	public void setErrorMessage(String errorMessage) {
		if(tip == null) {
			tip = new ErrorToolTip(getTree().getShell());
		}
		if((errorMessage != null) && !errorMessage.isEmpty()) {
			tip.setVisible(false);
			TreeItem treeItem = getTree().getSelection()[0];
			TreeColumn column = getTree().getColumn(0);
			tip.setText(errorMessage);
			Point location = new Point(column.getWidth(), treeItem.getBounds().y);
			Point controlPoint = getTree().toDisplay(location);
			tip.autoSize();
			tip.setLocation(controlPoint.x, controlPoint.y - tip.getHeight()
					- 5);
			tip.setVisible(true);
		} else {
			tip.setVisible(false);
		}
	}

}
