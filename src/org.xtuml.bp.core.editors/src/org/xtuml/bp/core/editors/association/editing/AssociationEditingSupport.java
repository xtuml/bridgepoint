package org.xtuml.bp.core.editors.association.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.editors.association.AssociationEditorTab;
import org.xtuml.bp.core.editors.editing.ElementEditingSupport;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;

public class AssociationEditingSupport extends ElementEditingSupport {

	TableColumn column;
	TableViewer tableViewer;
	private CellEditor activeCellEditor;

	public AssociationEditingSupport(ColumnViewer viewer, TableColumn tableColumn, TableViewer tableViewer) {
		super(viewer, tableColumn);
		this.column = tableColumn;
		this.tableViewer = tableViewer;
	}

	@Override
	protected void initializeCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
		// cache the cellEditor, even though we created it
		// this is for unit testing if we were to just call
		// getCellEditor it would return a new instance not
		// the one the TableViewer is currently using
		activeCellEditor = cellEditor;
		super.initializeCellEditorValue(cellEditor, cell);
	}

	public CellEditor getActiveCellEditor() {
		return activeCellEditor;
	}
	@Override
	public void setValue(Object element, Object value) {
		element = getCellElement(element);
		// for Mult we want to set both the Mult and Cond
		if (element instanceof ObjectElement) {
			ObjectElement objEle = (ObjectElement) element;
			if (objEle.getName().equals("Mult")) { //$NON-NLS-1$
				if (!getRuleFromValue(objEle, value).equals(AssociationEditorTab.getRuleText(objEle))) {
					try {
						Transaction transaction = TransactionManager.getSingleton().startTransaction(
								"Update rule for association.", new ModelElement[] { Ooaofooa.getDefaultInstance() });
						updateValueForRule(objEle, value);
						TransactionManager.getSingleton().endTransaction(transaction);
					} catch (TransactionException e) {
						CorePlugin.logError("Unable to update Mult and Cond for association.", e);
					}
				}
				return;
			}
		}
		super.setValue(element, value);
	}

	private void updateValueForRule(ObjectElement objEle, Object value) {
		Object attributeOwner = objEle.getParent();
		String rule = getRuleFromValue(objEle, value);
		int mult = getMultFromRule(rule);
		int cond = getCondFromRule(rule);
		if (attributeOwner instanceof ClassAsAssociatedOneSide_c) {
			((ClassAsAssociatedOneSide_c) attributeOwner).setMult(mult);
			((ClassAsAssociatedOneSide_c) attributeOwner).setCond(cond);
		} else if (attributeOwner instanceof ClassAsAssociatedOtherSide_c) {
			((ClassAsAssociatedOtherSide_c) attributeOwner).setMult(mult);
			((ClassAsAssociatedOtherSide_c) attributeOwner).setCond(cond);
		} else if (attributeOwner instanceof ClassAsLink_c) {
			ClassAsLink_c cal = (ClassAsLink_c) attributeOwner;
			cal.setMult(mult);
		} else if (attributeOwner instanceof ClassAsSimpleFormalizer_c) {
			((ClassAsSimpleFormalizer_c) attributeOwner).setMult(mult);
			((ClassAsSimpleFormalizer_c) attributeOwner).setCond(cond);
		} else if (attributeOwner instanceof ClassAsSimpleParticipant_c) {
			((ClassAsSimpleParticipant_c) attributeOwner).setMult(mult);
			((ClassAsSimpleParticipant_c) attributeOwner).setCond(cond);
		}

	}

	// extract values from string of:
	// 1
	// 0..1
	// 1..*
	// *
	private int getCondFromRule(Object value) {
		if (value.equals("1")) {
			return 0;
		}
		if (value.equals("0..1")) {
			return 1;
		}
		if (value.equals("1..*")) {
			return 0;
		}
		if (value.equals("*")) {
			return 1;
		}
		return 0;
	}

	private int getMultFromRule(Object value) {
		if (value.toString().contains("*")) {
			return 1;
		}
		return 0;
	}

	@Override
	public Object getValue(Object element) {
		element = getCellElement(element);
		if (element instanceof ObjectElement) {
			if (((ObjectElement) element).getName().equals("Mult")) { //$NON-NLS-1$
				String rule = AssociationEditorTab.getRuleText((ObjectElement) element);
				Integer location = getRuleComboboxLocation(rule);
				if(((ObjectElement) element).getParent() instanceof ClassAsLink_c) {
					if(location == 3) {
						return 1;
					}
				}
				return location;
			}
		}
		return super.getValue(element);
	}

	private Integer getRuleComboboxLocation(String rule) {
		if (rule.equals("0..1")) {
			return 1;
		}
		if (rule.equals("1..*")) {
			return 2;
		}
		if (rule.equals("*")) {
			return 3;
		}
		return 0;
	}

	private String getRuleFromValue(ObjectElement objEle, Object value) {
		Integer intValue = (Integer) value;
		Object parent = objEle.getParent();
		if (parent instanceof ClassAsLink_c) {
			// only 0 and 1 supported
			if (intValue == 0) {
				return "1";
			} else {
				return "*";
			}
		}
		if (intValue == 1) {
			return "0..1";
		}
		if (intValue == 2) {
			return "1..*";
		}
		if (intValue == 3) {
			return "*";
		}
		return "1";
	}

	@Override
	public boolean canEdit(Object element) {
		element = getCellElement(element);
		return super.canEdit(element);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		element = getCellElement(element);
		if (element instanceof ObjectElement) {
			// if we are dealing with Mult we want a
			// combo box with rule listings
			ObjectElement objElement = (ObjectElement) element;
			if (objElement.getName().equals("Mult")) { //$NON-NLS-1$
				ComboBoxCellEditor editor = new ComboBoxCellEditor(tableViewer.getTable(),
						getItemsForComboEditor(objElement), SWT.READ_ONLY);
				editor.getControl().pack();
				editor.setActivationStyle(ComboBoxCellEditor.DROP_DOWN_ON_KEY_ACTIVATION
						| ComboBoxCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
						| ComboBoxCellEditor.DROP_DOWN_ON_PROGRAMMATIC_ACTIVATION
						| ComboBoxCellEditor.DROP_DOWN_ON_TRAVERSE_ACTIVATION);
				return editor;
			}
		}
		return super.getCellEditor(element);
	}

	private String[] getItemsForComboEditor(ObjectElement objElement) {
		Object parent = objElement.getParent();
		if (parent instanceof ClassAsLink_c) {
			// only 1 and * supported
			return new String[] { "1", "*" };
		}
		return new String[] { "1", "0..1", "1..*", "*" };
	}

	private Object getCellElement(Object element) {
		Integer index = (Integer) column.getData("index"); //$NON-NLS-1$
		// if the index is 0, we need the Numb ObjectElement
		if (index == 0) {
			ModelInspector inspector = new ModelInspector();
			Association_c association = (Association_c) element;
			ObjectElement[] attributes = inspector.getAttributes(association);
			for (ObjectElement attribute : attributes) {
				if (attribute.getName().equals("Numb")) { //$NON-NLS-1$
					return attribute;
				}
			}
		}
		// account for assoc numb at index 0
		index--;
		// grab the expected value at the given index
		IStructuredContentProvider provider = (IStructuredContentProvider) tableViewer.getContentProvider();
		Object[] elements = provider.getElements(element);
		if (elements.length > 0 && index < elements.length) {
			return elements[index];
		}
		return element;
	}

}
