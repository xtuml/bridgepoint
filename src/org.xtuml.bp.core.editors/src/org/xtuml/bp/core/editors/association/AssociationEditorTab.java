package org.xtuml.bp.core.editors.association;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SimpleAssociation_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.editors.ErrorToolTip;
import org.xtuml.bp.core.editors.ITabErrorSupport;
import org.xtuml.bp.core.editors.association.editing.AssociationEditingSupport;
import org.xtuml.bp.core.editors.providers.MetaModelLabelProvider;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.core.ui.BinaryFormalizeOnR_RELAction;
import org.xtuml.bp.core.ui.InheritanceFormalizeOnR_RELAction;
import org.xtuml.bp.core.ui.LinkedFormalizeOnR_ASSRAction;
import org.xtuml.bp.core.ui.Selection;

public class AssociationEditorTab extends Composite implements ITransactionListener {

	private TableViewer fTableViewer;
	public TableViewer getTableViewer() {
		return fTableViewer;
	}

	private ModelInspector inspector = new ModelInspector();
	private ErrorToolTip tip;
	public ErrorToolTip getTip() {
		return tip;
	}

	private Button formalizeButton;
	public Button getFormalizeButton() {
		return formalizeButton;
	}

	private Button fEditDescripButton;

	public Button getEditDescripButton() {
		return fEditDescripButton;
	}

	public AssociationEditorTab(Composite parent, Object inputObject) {
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_BOTH));
		createTableViewer(this, inputObject);
		createAssociationDetailArea(this, inputObject);
		createMenus();
		pack();
		fTableViewer.getTable().setFocus();
		if (fTableViewer.getElementAt(0) != null) {
			fTableViewer.editElement(fTableViewer.getElementAt(0), 0);
		}
		fTableViewer.getTable().addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// ignored
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == 'z' && ((e.stateMask & SWT.COMMAND) != 0) && ((e.stateMask & SWT.SHIFT) == 0)) {
					// undo
					TransactionManager.getSingleton().getUndoAction().run();
				}
				if (e.character == 'z' && ((e.stateMask & SWT.COMMAND) != 0) && ((e.stateMask & SWT.SHIFT) != 0)) {
					// redo
					TransactionManager.getSingleton().getRedoAction().run();
				}
			}
		});
	}

	private void createMenus() {
		MenuManager menu = new MenuManager();
		menu.add(TransactionManager.getSingleton().getUndoAction());
		menu.add(TransactionManager.getSingleton().getRedoAction());
		menu.createContextMenu(this);
		setMenu(menu.getMenu());
	}

	private void createAssociationDetailArea(Composite composite, Object inputObject) {
		GridData data = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		Composite associationNumberArea = new Composite(composite, SWT.FLAT);
		GridLayout associationNumberLayout = new GridLayout(2, false);
		associationNumberArea.setLayout(associationNumberLayout);
		associationNumberArea.setLayoutData(data);
		formalizeButton = new Button(associationNumberArea, SWT.CHECK);
		formalizeButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		formalizeButton.setText("Formalized");
		formalizeButton.setSelection(false);
		formalizeButton.setEnabled(false);
		formalizeButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selected = formalizeButton.getSelection();
				if (selected) {
					formalize();
				} else {
					unformalize();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		fEditDescripButton = new Button(associationNumberArea, SWT.PUSH);
		fEditDescripButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		fEditDescripButton.setText("Edit Description...");
		fEditDescripButton.setEnabled(false);
		fEditDescripButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Association_c association = getSelectedAssociation();
				TextDialog dialog = new TextDialog(getShell(), association.getDescrip(),
						association.getName() + " Description");
				dialog.open();
				if (!association.getDescrip().equals(dialog.getTextContents())) {
					try {
						Transaction transaction = TransactionManager.getSingleton().startTransaction(
								"Update association description", new ModelElement[] { Ooaofooa.getDefaultInstance() });
						association.setDescrip(dialog.getTextContents());
						TransactionManager.getSingleton().endTransaction(transaction);
					} catch (TransactionException e1) {
						CorePlugin.logError("Unable to start description update transaction", e1);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	protected Association_c getSelectedAssociation() {
		ISelection selection = fTableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			return (Association_c) ss.getFirstElement();
		}
		return null;
	}

	protected void unformalize() {
		Association_c assoc = getSelectedAssociation();
		try {
			Transaction transaction = TransactionManager.getSingleton().startTransaction("Unformalize association",
					new ModelElement[] { Ooaofooa.getDefaultInstance() });
			assoc.Unformalize();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {
			CorePlugin.logError("Unable to unformalize association", e);
		}
	}

	protected void formalize() {
		Association_c assoc = getSelectedAssociation();
		// update the core selection temporarily
		ISelection originalSelection = Selection.getInstance().getSelection();
		try {
			Selection.getInstance().clear();
			Selection.getInstance().addToSelection(assoc);
			SimpleAssociation_c simp = SimpleAssociation_c.getOneR_SIMPOnR206(assoc);
			LinkedAssociation_c linked = LinkedAssociation_c.getOneR_ASSOCOnR206(assoc);
			SubtypeSupertypeAssociation_c subSup = SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc);
			IAction a = new Action() {
			};
			if (simp != null) {
				BinaryFormalizeOnR_RELAction action = new BinaryFormalizeOnR_RELAction();
				action.setActivePart(a,
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
				action.run(null);
			}
			if (linked != null) {
				Selection.getInstance().clear();
				Selection.getInstance().addToSelection(ClassAsLink_c.getOneR_ASSROnR211(linked));
				LinkedFormalizeOnR_ASSRAction action = new LinkedFormalizeOnR_ASSRAction();
				action.setActivePart(a,
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
				action.run(null);
			}
			if (subSup != null) {
				InheritanceFormalizeOnR_RELAction action = new InheritanceFormalizeOnR_RELAction();
				action.setActivePart(a,
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
				action.run(null);
			}
			// user canceled formalization
			if (!assoc.Isformalized()) {
				formalizeButton.setSelection(false);
			}
		} finally {
			Selection.getInstance().setSelection(originalSelection);
		}
	}

	class TabTableViewer extends TableViewer implements ITabErrorSupport {

		public TabTableViewer(Composite parent, int style) {
			super(parent, style);
		}

		@Override
		public void setErrorMessage(String errorMessage) {
			if (tip == null) {
				tip = new ErrorToolTip(getShell());
			}
			if ((errorMessage != null) && !errorMessage.isEmpty()) {
				tip.setVisible(false);
				TableItem treeItem = fTableViewer.getTable().getSelection()[0];
				TableColumn column = fTableViewer.getTable().getColumn(0);
				tip.setText(errorMessage);
				Point location = new Point(column.getWidth(), treeItem.getBounds().y);
				Point controlPoint = fTableViewer.getTable().toDisplay(location);
				tip.autoSize();
				tip.setLocation(controlPoint.x, controlPoint.y - tip.getHeight() - 5);
				tip.setVisible(true);
			} else {
				tip.setVisible(false);
			}
		}

	}

	private void createTableViewer(Composite parent, final Object input) {
		fTableViewer = new TabTableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		fTableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		fTableViewer.setUseHashlookup(true);
		fTableViewer.getTable().setHeaderVisible(true);
		fTableViewer.getTable().setLinesVisible(true);
		addTableListeners();
		createInitialColumns(input);
		fTableViewer.getTable().pack();
		fTableViewer.setContentProvider(new IStructuredContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// nothing to do
			}

			@Override
			public void dispose() {
				// nothing to do
			}

			class NonSubSupClassQuery implements ClassQueryInterface_c {

				@Override
				public boolean evaluate(Object candidate) {
					// for now ignore supertype/subtype the table doesn't much
					// fit for
					// them
					Association_c test = (Association_c) candidate;
					return SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(test) == null;
				}

			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof Package_c) {
					return Association_c.getManyR_RELsOnR8001(
							PackageableElement_c.getManyPE_PEsOnR8000((Package_c) inputElement),
							new NonSubSupClassQuery());
				}
				if (inputElement instanceof IStructuredSelection) {
					Object[] selection = ((IStructuredSelection) inputElement).toArray();
					Set<Association_c> associations = new HashSet<Association_c>();
					for (Object selected : selection) {
						if (selected instanceof ModelClass_c) {
							ModelClass_c clazz = (ModelClass_c) selected;
							associations.addAll(
									Arrays.asList(Association_c.getManyR_RELsOnR201(clazz, new NonSubSupClassQuery())));
						}
						if (selected instanceof ImportedClass_c) {
							ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR101((ImportedClass_c) selected);
							associations.addAll(
									Arrays.asList(Association_c.getManyR_RELsOnR201(clazz, new ClassQueryInterface_c() {

										@Override
										public boolean evaluate(Object candidate) {
											// first filter subsup assocs
											NonSubSupClassQuery query = new NonSubSupClassQuery();
											boolean result = query.evaluate(candidate);
											if (result) {
												// now check that it is a local
												// association
												Package_c importedClassPackage = Package_c
														.getOneEP_PKGOnR8000(PackageableElement_c
																.getManyPE_PEsOnR8001((ImportedClass_c) selected));
												Package_c associationPackage = Package_c
														.getOneEP_PKGOnR8000(PackageableElement_c
																.getManyPE_PEsOnR8001((Association_c) candidate));
												if (importedClassPackage == associationPackage) {
													return true;
												}
											}
											return false;
										}
									})));
						}
						if (selected instanceof ClassAsLink_c) {
							return Association_c.getManyR_RELsOnR206(
									LinkedAssociation_c.getManyR_ASSOCsOnR211((ClassAsLink_c) selected));
						}
						if (selected instanceof Package_c) {
							Package_c pkg = (Package_c) selected;
							associations.addAll(Arrays.asList(Association_c.getManyR_RELsOnR8001(
									PackageableElement_c.getManyPE_PEsOnR8000(pkg), new NonSubSupClassQuery())));
						}
						if (selected instanceof Association_c) {
							associations.add((Association_c) selected);
						}
					}
					Object[] array = associations.toArray();
					Arrays.sort(array, new Comparator<Object>() {
						@Override
						public int compare(Object o1, Object o2) {
							Association_c one = (Association_c) o1;
							Association_c two = (Association_c) o2;
							if (one.getNumb() < two.getNumb()) {
								return -1;
							} else {
								return 1;
							}
						}
					});
					return array;
				}
				if (inputElement instanceof ModelClass_c) {
					return Association_c.getManyR_RELsOnR201((ModelClass_c) inputElement, new NonSubSupClassQuery());
				}
				if (inputElement instanceof Association_c) {
					ArrayList<Object> data = new ArrayList<Object>();
					ObjectElement[] childRelations = inspector.getChildRelations(inputElement);
					for (ObjectElement child : childRelations) {
						// if child relation is a link class, output mult first
						// then class as link
						if (child.getValue() instanceof ClassAsLink_c) {
							ObjectElement[] attributes = inspector.getAttributes(child.getValue());
							data.add(attributes[0]);
							data.add(child);
							continue;
						}
						data.add(child);
						ObjectElement[] attributes = inspector.getAttributes(child.getValue());
						for (ObjectElement attribute : attributes) {
							// skip cond
							if (attribute.getName().equals("Cond")) { // $NON-NLS-1$
								continue;
							}
							data.add(attribute);
						}
					}
					return data.toArray();
				}
				return new Object[0];
			}
		});
		fTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (ss.size() == 1) {
						// update formalized and descrip controls
						Association_c selectedAssociation = getSelectedAssociation();
						fEditDescripButton.setEnabled(true);
						formalizeButton.setEnabled(true);
						formalizeButton.setSelection(selectedAssociation.Isformalized());
					} else {
						// disable the controls
						fEditDescripButton.setEnabled(false);
						formalizeButton.setSelection(false);
						formalizeButton.setEnabled(false);
					}
				}
			}
		});
		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(fTableViewer,
				new FocusCellOwnerDrawHighlighter(fTableViewer));
		ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(fTableViewer) {
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
		TableViewerEditor.create(fTableViewer, focusCellManager, actSupport,
				ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		ColumnViewerEditorActivationListener activationListener = new ColumnViewerEditorActivationListener() {

			@Override
			public void beforeEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
				// do nothing
			}

			@Override
			public void beforeEditorActivated(ColumnViewerEditorActivationEvent event) {
				// do nothing
			}

			@Override
			public void afterEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
				ViewerCell cell = (ViewerCell) event.getSource();
				fTableViewer.refresh(cell.getElement());
				fTableViewer.getTable().update();
			}

			@Override
			public void afterEditorActivated(ColumnViewerEditorActivationEvent event) {
				// do nothing
			}
		};
		fTableViewer.getColumnViewerEditor().addEditorActivationListener(activationListener);
		fTableViewer.setInput(input);
	}

	@Override
	public void dispose() {
		TransactionManager.getSingleton().removeTransactionListener(this);
	}

	CellLabelProvider cellLabelProvider = new CellLabelProvider() {

		@Override
		public void update(ViewerCell cell) {
			MetaModelLabelProvider mmProvider = new MetaModelLabelProvider();
			int index = cell.getColumnIndex();
			if (index == 0) {
				ObjectElement[] attributes = inspector.getAttributes(cell.getElement());
				for (ObjectElement attribute : attributes) {
					// we only care about the Numb attribute
					if (attribute.getName().equals("Numb")) { //$NON-NLS-1$
						cell.setText(((Integer) attribute.getValue()).toString());
						return;
					}
				}
			}
			Object cellObject = cell.getElement();
			Object[] children = ((IStructuredContentProvider) fTableViewer.getContentProvider())
					.getElements(cellObject);
			if (index - 1 < children.length) {
				Object child = children[index - 1];
				// handle mult to combine with cond
				if (child instanceof ObjectElement) {
					ObjectElement objEle = (ObjectElement) child;
					if (objEle.getName().equals("Mult")) { //$NON-NLS-1$
						cell.setText(getRuleText(objEle));
						return;
					}
				}
				cell.setText(mmProvider.getColumnText(child, index - 1));
			}
		}
	};
	
	AssociationEditingSupport[] columnEditingSupport = new AssociationEditingSupport[9];
	private void createInitialColumns(Object input) {
		columnEditingSupport[0] = createColumn("Number", 50, true, 0);
		columnEditingSupport[1] = createColumn("One Side", 135, false, 1);
		columnEditingSupport[2] = createColumn("Rule", 35, true, 2);
		columnEditingSupport[3] = createColumn("Phrase", 135, true, 3);
		columnEditingSupport[4] = createColumn("Other Side", 135, false, 4);
		columnEditingSupport[5] = createColumn("Rule", 35, true, 5);
		columnEditingSupport[6] = createColumn("Phrase", 135, true, 6);
		columnEditingSupport[7] = createColumn("Rule", 35, true, 7);
		columnEditingSupport[8] = createColumn("Link Side", 135, false, 8);
	}
	
	public AssociationEditingSupport[] getEditingSupport() {
		return columnEditingSupport;
	}
	
	/**
	 * We have the following rules:
	 * 
	 * 1 (unconditional one) mult = 0 cond = 0
	 * 0..1 (conditional one) mult = 0 cond = 1
	 * 1..* (unconditional many) mult = 1, cond = 0
	 * * (conditional many) mult = 1, cond = 1
	 * 
	 */
	public static String getRuleText(ObjectElement objEle) {
		Integer mult = (Integer) objEle.getValue();
		Object attributeOwner = objEle.getParent();
		Integer cond = 0;
		if (attributeOwner instanceof ClassAsAssociatedOneSide_c) {
			cond = ((ClassAsAssociatedOneSide_c) attributeOwner).getCond();
		} else if (attributeOwner instanceof ClassAsAssociatedOtherSide_c) {
			cond = ((ClassAsAssociatedOtherSide_c) attributeOwner).getCond();
		} else if (attributeOwner instanceof ClassAsLink_c) {
			if (mult == 0) {
				return "1";
			} else {
				return "*";
			}
		} else if (attributeOwner instanceof ClassAsSimpleFormalizer_c) {
			cond = ((ClassAsSimpleFormalizer_c) attributeOwner).getCond();
		} else if (attributeOwner instanceof ClassAsSimpleParticipant_c) {
			cond = ((ClassAsSimpleParticipant_c) attributeOwner).getCond();
		}
		return getRuleForMultCond(mult, cond);
	}

	static String[][] rules = new String[][] { new String[] { "1", "0..1" }, new String[] { "1..*", "*" } };

	public static String getRuleForMultCond(Integer mult, Integer cond) {
		return rules[mult][cond];
	}

	private AssociationEditingSupport createColumn(String name, int width, boolean supportsEditing, int index) {
		AssociationEditingSupport editingSupport = null;
		TableViewerColumn column = new TableViewerColumn(fTableViewer, SWT.LEAD);
		column.getColumn().setText(name);
		column.getColumn().setWidth(width);
		column.setLabelProvider(cellLabelProvider);
		if (supportsEditing) {
			editingSupport = new AssociationEditingSupport(column.getViewer(), column.getColumn(), fTableViewer);
			column.setEditingSupport(editingSupport);
		}
		column.getColumn().setData("index", new Integer(index));
		return editingSupport;
	}

	private void addTableListeners() {
		TransactionManager.getSingleton().addTransactionListener(this);
	}

	@Override
	public void transactionEnded(Transaction transaction) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			if (!fTableViewer.getTable().isDisposed() && !fTableViewer.isCellEditorActive()) {
				fTableViewer.refresh();
				fTableViewer.getTable().update();
			}
		});
	}

	public void setInput(Object input) {
		fTableViewer.setInput(input);
	}

}
