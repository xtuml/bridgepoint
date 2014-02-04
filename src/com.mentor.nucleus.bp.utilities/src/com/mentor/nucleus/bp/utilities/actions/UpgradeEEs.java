package com.mentor.nucleus.bp.utilities.actions;

import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;

public class UpgradeEEs implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction("", new ModelElement[] {Ooaofooa.getDefaultInstance()});
			IStructuredSelection ss = (IStructuredSelection) selection;
			for(Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
				ExternalEntity_c ee = (ExternalEntity_c) iterator.next();
				ee.setIsrealized(true);
				Bridge_c[] brgs = Bridge_c.getManyS_BRGsOnR19(ee);
				for(Bridge_c brg : brgs) {
					brg.setSuc_pars(1);
				}
			}			
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
