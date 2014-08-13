package com.mentor.nucleus.bp.model.compare.providers.custom;
//=====================================================================
//
//File:      $RCSfile: AssociationComparable.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:46 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

import java.util.UUID;

import com.mentor.nucleus.bp.core.ClassAsAssociatedOneSide_c;
import com.mentor.nucleus.bp.core.ClassAsAssociatedOtherSide_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleFormalizer_c;
import com.mentor.nucleus.bp.core.ClassAsSimpleParticipant_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class AssociationSubtypeComparable extends NonRootModelElementComparable {

	public AssociationSubtypeComparable(NonRootModelElement realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		if (!super.treeItemEquals(other)) {
			if (other instanceof AssociationSubtypeComparable) {
				AssociationSubtypeComparable assocComp = (AssociationSubtypeComparable) other;
				NonRootModelElement otherAssociation = (NonRootModelElement) assocComp
						.getRealElement();
				NonRootModelElement thisAssociation = (NonRootModelElement) getRealElement();
				UUID thisMsgId = getAssociationId(thisAssociation);
				UUID otherMsgId = getAssociationId(otherAssociation);
				UUID thisOirId = getObjectInAssociationId(thisAssociation);
				UUID otherOirId = getObjectInAssociationId(otherAssociation);
				return thisMsgId.equals(otherMsgId) && thisOirId.equals(otherOirId);
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private UUID getObjectInAssociationId(NonRootModelElement association) {
		UUID id = Gd_c.Null_unique_id();
		if (association instanceof ClassAsSimpleParticipant_c) {
			return ((ClassAsSimpleParticipant_c) association).getOir_id();
		} else if (association instanceof ClassAsSimpleFormalizer_c) {
			return ((ClassAsSimpleFormalizer_c) association).getOir_id();
		} else if (association instanceof ClassAsAssociatedOneSide_c) {
			return ((ClassAsAssociatedOneSide_c) association).getOir_id();
		} else if (association instanceof ClassAsAssociatedOtherSide_c) {
			return ((ClassAsAssociatedOtherSide_c) association).getOir_id();
		}
		return id;
	}

	private UUID getAssociationId(NonRootModelElement association) {
		UUID id = Gd_c.Null_unique_id();
		if (association instanceof ClassAsSimpleParticipant_c) {
			return ((ClassAsSimpleParticipant_c) association).getRel_id();
		} else if (association instanceof ClassAsSimpleFormalizer_c) {
			return ((ClassAsSimpleFormalizer_c) association).getRel_id();
		} else if (association instanceof ClassAsAssociatedOneSide_c) {
			return ((ClassAsAssociatedOneSide_c) association).getRel_id();
		} else if (association instanceof ClassAsAssociatedOtherSide_c) {
			return ((ClassAsAssociatedOtherSide_c) association).getRel_id();
		}
		return id;
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
		return true;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable#hashCode()
	 */
	@Override
	public int hashCode() {
		UUID associationId = getAssociationId((NonRootModelElement) getRealElement());
		UUID objectId = getObjectInAssociationId((NonRootModelElement) getRealElement());
		return associationId.hashCode() + objectId.hashCode();
	}

}
