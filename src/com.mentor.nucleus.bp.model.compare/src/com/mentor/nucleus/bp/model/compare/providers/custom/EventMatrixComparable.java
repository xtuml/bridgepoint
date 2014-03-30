package com.mentor.nucleus.bp.model.compare.providers.custom;

import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.EventIgnored_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.common.BPElementID;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class EventMatrixComparable extends NonRootModelElementComparable {

	public EventMatrixComparable(NonRootModelElement realElement) {
		super(realElement);
	}
	@Override
	public boolean treeItemEquals(Object other) {
		if (!super.treeItemEquals(other)) {
			if (other instanceof EventMatrixComparable) {
				EventMatrixComparable comp = (EventMatrixComparable) other;
				NonRootModelElement otherMatrixEntry = (NonRootModelElement) comp
						.getRealElement();
				NonRootModelElement thisMatrixEntry = (NonRootModelElement) getRealElement();
				BPElementID thisMatrixId = getMatrixId(thisMatrixEntry);
				BPElementID otherMatrixId = getMatrixId(otherMatrixEntry);
				return thisMatrixId.equals(otherMatrixId);
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private BPElementID getMatrixId(NonRootModelElement entry) {
		if (entry instanceof NewStateTransition_c) {
			NewStateTransition_c nst = (NewStateTransition_c) entry;
			return new BPElementID(new Object[] { nst.getSmstt_idCachedValue(),
					nst.getSmevt_idCachedValue(),
					nst.getSmspd_idCachedValue() });
		}
		if (entry instanceof CantHappen_c) {
			CantHappen_c ch = (CantHappen_c) entry;
			return new BPElementID(new Object[] { ch.getSmstt_idCachedValue(),
					ch.getSmevt_idCachedValue(),
					ch.getSmspd_idCachedValue() });
		}
		if (entry instanceof EventIgnored_c) {
			EventIgnored_c ign = (EventIgnored_c) entry;
			return new BPElementID(new Object[] { ign.getSmstt_idCachedValue(),
					ign.getSmevt_idCachedValue(),
					ign.getSmspd_idCachedValue() });
		}
		return null;
	}

	@Override
	public boolean treeItemNameMatches(Object other) {
		return true;
	}

	@Override
	public boolean treeItemTypeEquals(Object other) {
		return true;
	}

	@Override
	public boolean treeItemValueEquals(Object other) {
		if (treeItemEquals(other)) {
			EventMatrixComparable otherComp = (EventMatrixComparable) other;
			if (!otherComp.getRealElement().getClass().isInstance(
					getRealElement())) {
				return false;
			}
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#ignoreOrdering()
	 */
	@Override
	public boolean ignoreOrdering() {
		return true;
	}

}
