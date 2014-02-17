package com.mentor.nucleus.bp.model.compare.providers.custom;
//=====================================================================
//
//File:      $RCSfile: MessageComparable.java,v $
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

import com.mentor.nucleus.bp.core.BridgeMessage_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.EventMessage_c;
import com.mentor.nucleus.bp.core.FunctionMessage_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InformalAsynchronousMessage_c;
import com.mentor.nucleus.bp.core.InformalSynchronousMessage_c;
import com.mentor.nucleus.bp.core.InterfaceOperationMessage_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.OperationMessage_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.SignalMessage_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;

public class MessageComparable extends NonRootModelElementComparable {

	public MessageComparable(NonRootModelElement realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		if (!super.treeItemEquals(other)) {
			// if the other element is a polymorphic event
			// return true if our state machine event supertypes
			// are equal
			if (other instanceof MessageComparable) {
				MessageComparable messageComp = (MessageComparable) other;
				NonRootModelElement otherMessage = (NonRootModelElement) messageComp.getRealElement();
				NonRootModelElement thisMessage = (NonRootModelElement) getRealElement();
				UUID thisMsgId = getMessageId(thisMessage);
				UUID otherMsgId = getMessageId(otherMessage);
				return thisMsgId.equals(otherMsgId);
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private UUID getMessageId(NonRootModelElement message) {
		UUID id = null;
		if (message instanceof SignalMessage_c) {
			id = ((SignalMessage_c) message).getMsg_id();
		}
		if (message instanceof EventMessage_c) {
			id = ((EventMessage_c) message).getMsg_id();
		}
		if (message instanceof FunctionMessage_c) {
			id = ((FunctionMessage_c) message).getMsg_id();
		}
		if (message instanceof OperationMessage_c) {
			id = ((OperationMessage_c) message).getMsg_id();
		}
		if (message instanceof BridgeMessage_c) {
			id = ((BridgeMessage_c) message).getMsg_id();
		}
		if (message instanceof InterfaceOperationMessage_c) {
			id = ((InterfaceOperationMessage_c) message).getMsg_id();
		}
		if (message instanceof InformalSynchronousMessage_c) {
			id = ((InformalSynchronousMessage_c) message).getMsg_id();
		}
		if (message instanceof InformalAsynchronousMessage_c) {
			((InformalAsynchronousMessage_c) message).getMsg_id();
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
		if(treeItemEquals(other)) {
			MessageComparable otherComp = (MessageComparable) other;
			if(!otherComp.getRealElement().getClass().isInstance(getRealElement())) {
				return false;
			}
			// the formal element may be different
			if(!formalElementsEqual(otherComp.getRealElement())) {
				return false;
			}
		}
		return true;
	}

	private boolean formalElementsEqual(Object realElement) {
		NonRootModelElement thisFormalElement = getFormalElement(getRealElement());
		NonRootModelElement otherFormalElement =getFormalElement(realElement);
		if(thisFormalElement == null && otherFormalElement != null) {
			return false;
		}
		if(thisFormalElement != null && otherFormalElement == null) {
			return false;
		}
		if(thisFormalElement == null && otherFormalElement == null) { 
			return true;
		}
		return thisFormalElement.cachedIdentityEquals(otherFormalElement);
	}

	private NonRootModelElement getFormalElement(Object message) {
		if (message instanceof SignalMessage_c) {
			return InterfaceSignal_c.getOneC_ASOnR1021((SignalMessage_c) message);
		}
		if (message instanceof EventMessage_c) {
			return StateMachineEvent_c.getOneSM_EVTOnR1009((EventMessage_c) message);
		}
		if (message instanceof FunctionMessage_c) {
			return Function_c.getOneS_SYNCOnR1010((FunctionMessage_c) message);
		}
		if (message instanceof OperationMessage_c) {
			return Operation_c.getOneO_TFROnR1011((OperationMessage_c) message);
		}
		if (message instanceof BridgeMessage_c) {
			return Bridge_c.getOneS_BRGOnR1012((BridgeMessage_c) message);
		}
		if (message instanceof InterfaceOperationMessage_c) {
			return InterfaceOperation_c.getOneC_IOOnR1022((InterfaceOperationMessage_c) message);
		}
		return null;
	}
	
}
