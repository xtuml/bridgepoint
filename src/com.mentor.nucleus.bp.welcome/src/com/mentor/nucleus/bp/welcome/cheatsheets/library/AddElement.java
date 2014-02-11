package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: AddElement.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.NewAttributeOnO_OBJAction;
import com.mentor.nucleus.bp.core.ui.NewBridgeOperationOnS_EEAction;
import com.mentor.nucleus.bp.core.ui.NewClassStateMachineOnO_OBJAction;
import com.mentor.nucleus.bp.core.ui.NewEnumeratorOnS_EDTAction;
import com.mentor.nucleus.bp.core.ui.NewEventOnSM_ISMAction;
import com.mentor.nucleus.bp.core.ui.NewFunctionOnEP_PKGAction;
import com.mentor.nucleus.bp.core.ui.NewInstanceStateMachineOnO_OBJAction;
import com.mentor.nucleus.bp.core.ui.NewMemberOnS_SDTAction;
import com.mentor.nucleus.bp.core.ui.NewOperationOnC_IAction;
import com.mentor.nucleus.bp.core.ui.NewOperationOnO_OBJAction;
import com.mentor.nucleus.bp.core.ui.NewParameterOnS_BRGAction;
import com.mentor.nucleus.bp.core.ui.NewSignalOnC_IAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class AddElement extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String projectName = params[0];
		String newElement = params[1];
		String parentOoaType = params[2];
		String parentName = params[3]; 
		String containerPkg = params[4];
		String inDirectParentName = params[5];
		String directParentName = params[6];
		 
		
		if (params != null  ){
			NonRootModelElement element = CheatSheetsUtilities.findElement(projectName, containerPkg, parentOoaType, parentName, inDirectParentName, directParentName);
			Action a = new Action() {
			};
			if ( element == null){
				CheatSheetsUtilities.canNotFindElementReport(parentOoaType,parentName);
			}
			StructuredSelection sel = new StructuredSelection(element);
			Selection.getInstance().setSelection(sel, true);
			//Model Classes
			if ( newElement.equalsIgnoreCase("Attribute")){
				
				NewAttributeOnO_OBJAction newAttribute = new NewAttributeOnO_OBJAction();
				newAttribute.run(a);
			}else if ( newElement.equalsIgnoreCase("Operation")){
				
				NewOperationOnO_OBJAction newOperation = new NewOperationOnO_OBJAction();
				newOperation.run(a);
			}else if ( newElement.equalsIgnoreCase("Class State Machine")){
				
				NewClassStateMachineOnO_OBJAction newClassStateMachine = new NewClassStateMachineOnO_OBJAction();
				newClassStateMachine.run(a);
			}else if ( newElement.equalsIgnoreCase("Instance State Machine")){
				
				NewInstanceStateMachineOnO_OBJAction newInstanceStateMachine = new NewInstanceStateMachineOnO_OBJAction();
				newInstanceStateMachine.run(a);
			}
			// State Machine :
			else if ( newElement.equalsIgnoreCase("Event")){
				
				NewEventOnSM_ISMAction newEvent =  new NewEventOnSM_ISMAction();
				newEvent.run(a);
			}
			// Package
			else if ( newElement.equalsIgnoreCase("Function")){
				NewFunctionOnEP_PKGAction newFunction = new NewFunctionOnEP_PKGAction();
				newFunction.run(a);
			}
			// External Entity
			else if (newElement.equalsIgnoreCase("Bridge")){
				NewBridgeOperationOnS_EEAction newBridge = new NewBridgeOperationOnS_EEAction();
				newBridge.run(a);
			}else if (newElement.equalsIgnoreCase("Bridge Parameter")){
				NewParameterOnS_BRGAction newParam = new NewParameterOnS_BRGAction();
				newParam.run(a);
			// Interfaces 
			}else if (newElement.equalsIgnoreCase("Interface Operation")){
				NewOperationOnC_IAction newIOperation = new NewOperationOnC_IAction();
				newIOperation.run(a);
			}else if (newElement.equalsIgnoreCase("Signal")){
				NewSignalOnC_IAction newSignal = new NewSignalOnC_IAction();
				newSignal.run(a);
			}
			// Structure DataType
			else if (newElement.equalsIgnoreCase("Member")){
				NewMemberOnS_SDTAction newMember = new NewMemberOnS_SDTAction();
				newMember.run(a);
			}
			// Enumeration DataType
			else if (newElement.equalsIgnoreCase("Enumerator")){
				NewEnumeratorOnS_EDTAction newEnum = new NewEnumeratorOnS_EDTAction();
				newEnum.run(a);
			}
			
		}

	}

}
