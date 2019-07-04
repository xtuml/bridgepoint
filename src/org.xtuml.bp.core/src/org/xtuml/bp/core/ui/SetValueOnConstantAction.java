package org.xtuml.bp.core.ui;

//
// This class is the main BridgePoint entry point for the Set Value action.
//
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.util.TransactionUtil;
import org.xtuml.bp.core.util.UIUtil;

public class SetValueOnConstantAction implements IObjectActionDelegate {

	private static IStructuredSelection currentSelection = null;

	/**
	 * Constructor for SetValueAction.
	 */
	public SetValueOnConstantAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// The part is mainly needed to locate the selection provider, and
		// we cache our selection in core so no action is needed here.
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		currentSelection = Selection.getInstance().getStructuredSelection();
		Object context = currentSelection.iterator().next();

		String oldValue = "";
		String elementName = "";
		if (context instanceof LiteralSymbolicConstant_c) {
			LiteralSymbolicConstant_c v_lsc = (LiteralSymbolicConstant_c) context;
			oldValue = v_lsc.getValue();

			SymbolicConstant_c v_symConst = SymbolicConstant_c
					.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c
							.getOneCNST_LFSCOnR1503(v_lsc));
			elementName = v_symConst.getName();
		}

		String title = "Enter the new value for this element";
		if (!elementName.isEmpty()) {
			title = "Enter the new value for " + elementName;
		}
		boolean result = UIUtil.inputDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), "Value Entry", title,
				oldValue, new ConstantValueInputValidator());
		if(result) {
			String newValue = UIUtil.inputDialogResult;
			if (context instanceof LiteralSymbolicConstant_c) {
				if (!newValue.equals(oldValue)) {
					CNST_LSC_SetValue(newValue);
				}
			}
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// do nothing
	}

	public static void CNST_LSC_SetValue(String p_New_value) {
		// Assign the context selection variables with the action context 
		Object context = currentSelection.iterator().next();
		LiteralSymbolicConstant_c v_lsc = (LiteralSymbolicConstant_c) context;
		TransactionUtil.TransactionGroup transactionGroup = TransactionUtil
				.startTransactionsOnSelectedModelRoots("Set Value");

		try {
			// Ensure that actions take place between Verifier Activity executions
			Ooaofooa.beginSaveOperation();
			if (v_lsc != null) {
				v_lsc.setValue(p_New_value);
			} else {
				Throwable t = new Throwable();
				t.fillInStackTrace();
				CorePlugin.logError(
						"Attribute write attempted on null instance.", t);
			}

			// end critical section
			Ooaofooa.endSaveOperation();
			// catch all exceptions and cancel the transactions
		} catch (Exception e) {
			TransactionUtil.cancelTransactions(transactionGroup, e);
			CorePlugin.logError("Transaction: Set Value failed", e);//$NON-NLS-1$
		}

		TransactionUtil.endTransactions(transactionGroup);
	}
} // end SetValueAction

