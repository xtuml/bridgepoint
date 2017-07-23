package org.xtuml.bp.core.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.ReferredToClassInAssoc_c;
import org.xtuml.bp.core.ReferringClassInAssoc_c;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;

public class AssociationCardinalityAction implements IActionDelegate {

	private NonRootModelElement rtoRgo;
	int rule = 0;

	// rules: 1, 0..1, 1..*, * (-1 is reserved for class as link, just reverse
	// the current mult)
	public AssociationCardinalityAction(NonRootModelElement rtoRgo, int rule) {
		this.rule = rule;
		this.rtoRgo = rtoRgo;
	}

	@Override
	public void run(IAction action) {
		try {
			Transaction transaction = TransactionManager.getSingleton().startTransaction("Set cardinality",
					new ModelElement[] { Ooaofooa.getDefaultInstance() });
			int cond = 0;
			int mult = 0;
			switch (rule) {
			case 0:
				// set cond and mult to 0
				// defaults just here for dexterity
				break;
			case 1:
				// set cond to 1 and mult to 0
				cond = 1;
				mult = 0;
				break;
			case 2:
				// set cond to 0 and mult to 1
				cond = 0;
				mult = 1;
				break;
			case 3:
				// set cond and mult to 1
				cond = 1;
				mult = 1;
				break;
			case -1:
				// switch mult for class as link
				ClassAsLink_c link = ClassAsLink_c.getOneR_ASSROnR205((ReferringClassInAssoc_c) rtoRgo);
				if (link != null) {
					link.setMult(link.getMult() == 0 ? 1 : 0);
				}
				TransactionManager.getSingleton().endTransaction(transaction);
				return;
			default:
				break;
			}
			ReferredToClassInAssoc_c rto = null;
			ReferringClassInAssoc_c rgo = null;
			if (rtoRgo instanceof ReferredToClassInAssoc_c) {
				rto = (ReferredToClassInAssoc_c) rtoRgo;
			}
			if (rtoRgo instanceof ReferringClassInAssoc_c) {
				rgo = (ReferringClassInAssoc_c) rtoRgo;
			}
			// get the subtype of the referred to or referring
			ClassAsSimpleParticipant_c part = ClassAsSimpleParticipant_c
					.getOneR_PARTOnR204((ReferredToClassInAssoc_c) rto);
			ClassAsSimpleFormalizer_c form = ClassAsSimpleFormalizer_c
					.getOneR_FORMOnR205((ReferringClassInAssoc_c) rgo);
			ClassAsAssociatedOneSide_c cone = ClassAsAssociatedOneSide_c
					.getOneR_AONEOnR204((ReferredToClassInAssoc_c) rto);
			ClassAsAssociatedOtherSide_c coth = ClassAsAssociatedOtherSide_c
					.getOneR_AOTHOnR204((ReferredToClassInAssoc_c) rto);
			if (part != null) {
				int currentCond = part.getCond();
				int currentMult = part.getMult();
				if (currentCond == cond && currentMult == mult) {
					// do not make a change 
					TransactionManager.getSingleton().cancelTransaction(transaction);
					return;
				}
				part.setCond(cond);
				part.setMult(mult);
			} else if (form != null) {
				int currentCond = form.getCond();
				int currentMult = form.getMult();
				if (currentCond == cond && currentMult == mult) {
					// do not make a change
					TransactionManager.getSingleton().cancelTransaction(transaction);
					return;
				}
				form.setCond(cond);
				form.setMult(mult);
			} else if (cone != null) {
				int currentCond = cone.getCond();
				int currentMult = cone.getMult();
				if (currentCond == cond && currentMult == mult) {
					// do not make a change
					TransactionManager.getSingleton().cancelTransaction(transaction);
					return;
				}
				cone.setCond(cond);
				cone.setMult(mult);
			} else if (coth != null) {
				int currentCond = coth.getCond();
				int currentMult = coth.getMult();
				if (currentCond == cond && currentMult == mult) {
					// do not make a change
					TransactionManager.getSingleton().cancelTransaction(transaction);
					return;
				}
				coth.setCond(cond);
				coth.setMult(mult);
			}
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {
			CorePlugin.logError("Unable to start set cardinality transaction.", e);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// nothing to do
	}

}
