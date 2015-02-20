.//=====================================================================
.//
.// File:      $RCSfile: function_body.inc,v $
.// Version:   $Revision: 1.45.80.1 $
.// Modified:  $Date: 2013/07/19 12:25:14 $
.//
.// (c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//=====================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not
.// use this file except in compliance with the License.  You may obtain a copy
.// of the License at
.//
.//      http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
.// License for the specific language governing permissions and limitations under
.// the License.
.//=====================================================================
.//
.// The function declared below is responsible for creating the body
.// of a function declared as ui interface.
.//
.function function_body
  .param inst_ref function
  .//
  .// obtain the workflow corresponding to the passed function
  .//
  .select one wfl related by function->W_WOF[R2001]
  .select one cme related by wfl->CME[R2012]
  .if (not_empty wfl)
    .select any field related by wfl->W_STEP[R2005]->W_FLD[R2009]
    .if (not_empty field)
      .print "Generating wizard invocation"
      .invoke wizard_name = get_wizard_class_name(wfl)
    ${wizard_name.body} wizard = new ${wizard_name.body}();
    wizard.init(CorePlugin.getDefault().getWorkbench(), selection, null);
    // Instantiates the wizard container with the wizard and opens it
    WizardDialog dialog = new WizardDialog(m_part.getSite().getShell(), wizard);
    dialog.create();
    ImageDescriptor descr = CorePlugin.getImageDescriptor("green-bp.gif");
    dialog.getShell().setImage(descr.createImage());
    return dialog;
    .else
      .assign sel_decl = "IStructuredSelection"
      .assign iteration_req = false
      .select many context_items related by wfl->W_CTI[R2003]
      .for each context_item in context_items
        .if (context_item.Type == "many")
          .assign iteration_req = true
          .// a set of values must be placed in a new array for processing
          .select one class related by context_item->O_OBJ[R2002]
          .invoke cn = get_class_name(class)
    int $Cr{class.Name}count = 0;
    for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
      Object context = iterator.next();
      if (context instanceof ${cn.body}) {
        $Cr{class.Name}count++;
      }
    }
    if ($Cr{class.Name}count == 0) return;
    ${cn.body} [] v_${context_item.Name} = new ${cn.body} [$Cr{class.Name}count];
    $Cr{class.Name}count = 0;
        .end if
      .end for	
    // Assign the context selection variables with the action context 
    .assign class_name = ""
    .if (not iteration_req)
      .if(cme.UsesSelectionDialog)
        .assign firstContextItemName = ""
        .assign selectedElementType = ""
        .assign existingItemName = ""
        .// for selection dialog usage we must find
        .// every instance under a system
        .select any elemClass related by context_items->O_OBJ[R2002]
        .for each context_item in context_items
          .if(first context_items)
            .// for the first assign the context with selected element
      // Assign the context selection variable with the action context 
      Object context = selection.iterator().next();
            .select one class related by context_item->O_OBJ[R2002]
            .select one elemClass related by context_item->O_OBJ[R2002]
            .invoke cn = get_class_name(class)
            .assign class_name = "${class.Name}"
            .assign firstContextItemName = context_item.Name
      ${cn.body} v_${context_item.Name} = (${cn.body})context;
          .elif(not_last context_items)
            .select any statement from instances of ACT_SMT where ("${selected.Statement_Id}" == context_item.Statement_ID)
            .select any srw_stmt related by statement->ACT_SEL[R603]->ACT_SRW[R664]
            .select one srw_val related by srw_stmt->V_VAL[R611]
            .invoke hubi = has_user_bridge_invocation( srw_val )
            .if (hubi.result)
              .if(hubi.Method_Name == "selectExisting")
                .// this select processed should select the existing element which
                .// the element we are choosing for is associated with
                .assign existingItemName = "v_${context_item.Name}"    
      ${statement.buffer}
              .end if
            .end if
          .else
            .// the next should be the selection of the entire element
            .// set, the following is example of what the OAL should be
            .// for now code is only generated for this type of OAL
            .//
            .// select any selectedDT from instances of S_DT
            .//                where USER::selectOne(id:selected.DT_ID)
            .//                 and (element.canUseDataType(id:selected.DT_ID));
            .// 
            .// the latter where clause is optional, but if none exists then
            .// all elements of the expected type will show in the chooser
            .//
            .// first make sure all instances are loaded
            .select one class related by context_item->O_OBJ[R2002]
            .invoke cn = get_class_name(class)
            .assign class_name = "${class.Name}"
            .assign selectedElementType = cn.body
      // ensure that all ${class.Name}s are loaded
      PersistenceManager.ensureAllInstancesLoaded(v_${firstContextItemName}.getModelRoot(), ${cn.body}.class);
      // now find all the elements that should be shown 
      ${cn.body}[] elements = getElements(v_${firstContextItemName});      
	  .invoke traversal = get_pe_traversal(elemClass.Key_Lett)
	  .assign peTraversal = traversal.result
	  .assign secondaryPETraversal = traversal.result2
	  .if (peTraversal != "")
      PackageableElement_c pe = PackageableElement_c.getOnePE_PEOnR8001(${peTraversal});
	    .if(secondaryPETraversal != "")
	  if(pe == null) {
	  		pe = PackageableElement_c.getOnePE_PEOnR8001(${secondaryPETraversal});
	  }
	    .end if
      Package_c pkg = Package_c.getOneEP_PKGOnR8000(pe);
      Component_c comp = Component_c.getOneC_COnR8003(pe);
	    .end if
	  .end if
        .end for
		.if((cme.requiredSelectionCount == "*") and (existingItemName != ""))
	  // if the current selection is many, then we do not want to
	  // filter the currently set element for any of the selection
	  // if we want to be smart about it we could process each object
	  // in the selection and if they all matched the same type we
	  // could filter
	  if(Selection.getInstance().getSelectedNonRootModelElements().length > 1) {
	       ${existingItemName} = null;
	  }
	    .end if
         .// now output the code to open the selection dialog
      ElementSelectionDialog dialog = new ElementSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), elements,
	    .if(cme.TypeToChoose != "")
		  .print "Generating chooser name: ${cme.TypeToChoose}"
		        "${cme.TypeToChoose}", 
		.else
				"$l{cme.Label}", 
		.end if
        .if(existingItemName != "")
				 true, ${existingItemName},
		.else
				true, null,
		.end if
		.if(cme.EnableVisibilityFiltering)
				true, pkg);
		.else
				false, null);
		.end if
      dialog.setBlockOnOpen(true);
      dialog.setTitle("${cme.Label} Selection");
      int result = dialog.open();
      if(result == Window.OK) {
					${selectedElementType} v_${context_item.Name} = (${selectedElementType}) dialog.getResult()[0];
					if(v_${context_item.Name} != null) {
      .else
        .// this assumes that there's only one context item for the workflow
        .// we still need to iterate to get the inst_ref out of the inst_ref_set
        .for each context_item in context_items
      // Assign the context selection variable with the action context 
      Object context = selection.iterator().next();
          .select one class related by context_item->O_OBJ[R2002]
          .invoke cn = get_class_name(class)
          .assign class_name = "${class.Name}"
      ${cn.body} v_${context_item.Name} = (${cn.body})context;
      PersistableModelComponent.ensureCoreDataTypesAvailable(v_${context_item.Name}.getModelRoot());
          .if(class.Key_Lett == "S_SYS")
      PersistableModelComponent.ensureSystemCoreDataTypesAvailable(v_${context_item.Name});
          .else
            .invoke loadCode = gen_code_for_loading_core_types(class.Key_Lett, "v_${context_item.Name}");
        ${loadCode.result}
          .end if
        .end for
      .end if
	.else
    for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
      Object context = iterator.next();
        .for each context_item in context_items
          .select one class related by context_item->O_OBJ[R2002]
          .invoke cn = get_class_name(class)
          .assign class_name = "${class.Name}"
      if (context instanceof ${cn.body}) {
        .// a set member must be copied to the array
        v_${context_item.Name}[$Cr{class.Name}count] = (${cn.body})context;
        $Cr{class.Name}count++;
        .end for
      }
    }
    .end if

    .// Special case for Delete action
    .//
    .// we do not need to start a transaction
    .// for the delete action as it has one
    .// started in create_global_action.inc
    .// see the associated comment in the above
    .// file for clarification
    .invoke transaction_name = get_transaction_name(wfl, class_name)
    .assign usesTransactions = (cme.Specialism != "Delete")
    .if (usesTransactions)
	TransactionUtil.TransactionGroup transactionGroup = 
		TransactionUtil.startTransactionsOnSelectedModelRoots("${transaction_name.name}");
	.if (((((cme.Specialism == "Classes") or(cme.Specialism == "Components"))or ((cme.Specialism == "External")or(cme.Specialism == "Interaction")))or(((cme.Specialism == "New")or(cme.Specialism == "Activity"))or((cme.Specialism == "Types")or(cme.Specialism == "Usecase"))))and (cme.Resultant_Class_RelChain!=""))
     boolean abortTransaction = false;
    .end if 
    try {
	.end if
    .//
    .// Singleton and set context variables now in place. Now translate
    .// the action language and emit the results.
    .//
    .select any function from instances of S_SYNC where (selected.Sync_Id == wfl.Sync_Id)
    .select one action related by function->ACT_FNB[R695]->ACT_ACT[R698]
    .if (not_empty action)
      .select one outer_block related by action->ACT_BLK[R601] where (selected.Block_Id == action.Block_Id)
      .if (not_empty outer_block)
        // Ensure that actions take place between Verifier Activity executions
		Ooaofooa.beginSaveOperation();
		  .// this can process against many elements
		  .// perform the function to all in the selection
          .select any context_item related by wfl->W_CTI[R2003]
		  .select one class related by context_item->O_OBJ[R2002];
		  .invoke cn = get_class_name(class)
		.if(cme.requiredSelectionCount == "*")
for(Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
	v_${context_item.Name} = (${cn.body}) iterator.next();
		.end if
        .invoke result = ui_blck_xlate(outer_block, usesTransactions)
	${result.body}
	    .if(cme.requiredSelectionCount == "*")
		  .// close the for loop
}
		.end if
.if ((cme.Key_Lett =="C_C")and ((cme.Specialism == "New") or(cme.Specialism == "Components")))
 if (v_${context_item.Name}.isInGenericPackage()) 
     {
.end if 		
 .if (((((cme.Specialism == "Classes") or(cme.Specialism == "Components"))or ((cme.Specialism == "External")or(cme.Specialism == "Interaction")))or(((cme.Specialism == "New")or(cme.Specialism == "Activity"))or((cme.Specialism == "Types")or(cme.Specialism == "Usecase"))))and (cme.Resultant_Class_RelChain!=""))
	      .assign temp = "v_"+"${context_item.Name}" 
		  .invoke rel_nav = backward_rel_chain_nav(cn.body,temp, cme.Resultant_Class_RelChain, "many", false);
      		NonRootModelElement[] children = ${rel_nav.body};
            NonRootModelElement newElement = children[children.length - 1];
		  				IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
			boolean option = store.getBoolean(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION);
			if (!option) {
				String oldName = RenameActionUtil.getElementName(newElement);
		        boolean performRename = UIUtil.inputDialog(null, "Element Creation","Enter the name:", oldName, UIUtil.newRenameValidator(newElement));
				if (performRename) {
					String proposedName = UIUtil.inputDialogResult;
					RenameActionUtil.setElementName(newElement,proposedName);
					newElement.setComponent(null);
				} else {
					abortTransaction = true;
				}
			}	
	 .end if 
	.if ((cme.Key_Lett =="C_C")and ((cme.Specialism == "New") or(cme.Specialism == "Components")))
  
     }
     .end if 			
         // end critical section
        Ooaofooa.endSaveOperation();
          .else
        .print "ERROR: No outer block found for action ${action.Action_ID}"
      .end if
    .else
      .print "ERROR: No action found for workflow ${wfl.Name} - ${wfl.Sync_Id}"
    .end if    	    
	.// special case for delete action - see above
    .if (usesTransactions)
	// catch all exceptions and cancel the transactions
    } catch (Exception e) {
        Ooaofooa.endSaveOperation();
		TransactionUtil.cancelTransactions(transactionGroup, e);
    	CorePlugin.logError("Transaction: ${transaction_name.name} failed", e);//$$NON-NLS-1$$
	}
     .if (((((cme.Specialism == "Classes") or(cme.Specialism == "Components"))or ((cme.Specialism == "External")or(cme.Specialism == "Interaction")))or(((cme.Specialism == "New")or(cme.Specialism == "Activity"))or((cme.Specialism == "Types")or(cme.Specialism == "Usecase"))))and (cme.Resultant_Class_RelChain!=""))
    	if((! CorePlugin.getDefault().getPreferenceStore().getBoolean(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION)) &&abortTransaction)
		{
			TransactionUtil.cancelTransactions(transactionGroup);
		}
		else{
		     TransactionUtil.endTransactions(transactionGroup);
		}
	  .else
	      TransactionUtil.endTransactions(transactionGroup);
      .end if 		  
	.end if
	.if(cme.UsesSelectionDialog)
		}
	}
	.end if
      .end if
  .end if      
.end function
.//////////////////////////////////////////////////////////////////////////////////////
.// This function takes an instance of W_WOF and O_OBJ, and constructs a name using
.// a combination of Label and Specialization strings available in the CME class
.// as WOF->CME[R2012].
.// The naming condition is:
.// .if (Specialism != "")
.//    .if (Label != "")
.//      name = Specialism + " instance(s) of " + Label
.//    .else
.//      name = Specialism + " instance(s) of " + O_OBJ.Name
.// .elif (Specialism == "")
.//    .if (Label != "")
.//       name = O_OBJ.Name + " " + Label 
.//    .else
.//       name = "Action performed on " + O_OBJ.Name
.//////////////////////////////////////////////////////////////////////////////////////
.function get_transaction_name
  .param inst_ref wfl
  .param string class_name
.//  
  .assign attr_name = ""
  .select one cme related by wfl->CME[R2012]
  .if (not_empty cme)
    .if (cme.Specialism != "")
      .if (cme.Label != "")
        .assign attr_name = "${cme.Specialism} " 
        .assign attr_name = attr_name + "${cme.Label}"
      .else
        .assign attr_name = "${cme.Specialism}" 
        .assign attr_name = attr_name + " instance(s) of " 
        .assign attr_name = attr_name + "${class_name}"
      .end if
    .else
      .if (cme.Label != "")
        .assign attr_name = "${class_name}" 
        .assign attr_name = attr_name + " " 
        .assign attr_name = attr_name + "${cme.Label}"        
      .else
        .assign attr_name = "Action performed on " 
        .assign attr_name = attr_name + "${class_name}"
      .end if
    .end if
  .end if
.end function
.function gen_code_for_loading_core_types
  .param String kl
  .param String var
  .assign attr_result = ""
  .if(kl == "C_C")
    .assign attr_result = "		PersistableModelComponent.ensureComponentCoreDataTypesAvailable(${var});\n"
  .elif(kl == "CP_CP")
    .assign attr_result = "		PersistableModelComponent.ensureComponentPackageCoreDataTypesAvailable(${var});\n"
  .end if
.end function
.function get_pe_traversal
  .param String kl
  .assign attr_result = ""
  .assign attr_result2 = ""
  .if (kl == "C_IO")
    .assign attr_result = "Interface_c.getOneC_IOnR4003(ExecutableProperty_c.getOneC_EPOnR4004(v_element))"
  .elif (kl == "O_TFR")
    .assign attr_result = "ModelClass_c.getOneO_OBJOnR115(v_element)"
  .elif (kl == "S_BRG")
    .assign attr_result = "ExternalEntity_c.getOneS_EEOnR19(v_element)"
  .elif (kl == "S_SYNC")
    .assign attr_result = "v_element"
  .elif (kl == "C_PP")
    .assign attr_result = "Interface_c.getOneC_IOnR4003(ExecutableProperty_c.getOneC_EPOnR4006(v_element))"
  .elif (kl == "CNST_LSC")
    .assign attr_result = "ConstantSpecification_c.getOneCNST_CSPOnR1504(SymbolicConstant_c.getOneCNST_SYCOnR1502(LeafSymbolicConstant_c.getOneCNST_LFSCOnR1503(v_element)))"
  .elif (kl == "O_ATTR")
    .assign attr_result = "ModelClass_c.getOneO_OBJOnR102(v_element)"
  .elif (kl == "O_TPARM")
    .assign attr_result = "ModelClass_c.getOneO_OBJOnR115(Operation_c.getOneO_TFROnR117(v_element))"
  .elif (kl == "S_BPARM")
    .assign attr_result = "ExternalEntity_c.getOneS_EEOnR19(Bridge_c.getOneS_BRGOnR21(v_element))"
  .elif (kl == "S_SPARM")
    .assign attr_result = "Function_c.getOneS_SYNCOnR24(v_element)"
  .elif (kl == "S_MBR")
    .assign attr_result = "DataType_c.getOneS_DTOnR17(StructuredDataType_c.getOneS_SDTOnR44(v_element))"
  .elif (kl == "S_UDT")
    .assign attr_result = "DataType_c.getOneS_DTOnR17(v_element)"
  .elif (kl == "SM_EVTDI")
    .assign attr_result = "ModelClass_c.getOneO_OBJOnR519(ClassStateMachine_c.getOneSM_ASMOnR517(StateMachine_c.getOneSM_SMOnR516(v_element)))"
	.assign attr_result2 = "ModelClass_c.getOneO_OBJOnR518(InstanceStateMachine_c.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR516(v_element)))"
  .elif (kl == "O_IOBJ")
    .assign attr_result = "v_element"
  .elif (kl == "CL_IC")
    .assign attr_result = "v_element"
  .elif (kl == "SQ_COP")
	.assign attr_result = "InteractionParticipant_c.getOneSQ_POnR930(v_element)"
  .elif (kl == "SQ_CP")
	.assign attr_result = "InteractionParticipant_c.getOneSQ_POnR930(v_element)"
  .elif (kl == "SQ_CIP")
	.assign attr_result = "InteractionParticipant_c.getOneSQ_POnR930(v_element)"
  .elif (kl == "SQ_PP")
	.assign attr_result = "InteractionParticipant_c.getOneSQ_POnR930(v_element)"
  .elif (kl == "SQ_EEP")
	.assign attr_result = "InteractionParticipant_c.getOneSQ_POnR930(v_element)" 
  .elif (kl == "C_P")
    .assign attr_result = "Component_c.getOneC_COnR4010(Port_c.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4009(v_element)))"
  .elif (kl == "C_R")
    .assign attr_result = "Component_c.getOneC_COnR4010(Port_c.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4009(v_element)))"
    .assign attr_result = "Component_c.getOneC_COnR4010(Port_c.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4009(v_element)))"
  .elif (kl == "C_DG")
	.assign attr_result = "Component_c.getOneC_COnR4010(Port_c.getOneC_POOnR4016(InterfaceReference_c.getOneC_IROnR4013(InterfaceReferenceInDelegation_c.getOneC_RIDOnR4013(v_element))))"
  .end if
.end function
.function get_from_pe_traversal
  .param String kl
  .assign attr_result = ""
  .if (kl == "S_DT")
    .assign attr_result = "          DataType_c elem = DataType_c.getOneS_DTOnR8001(pes[i]);"
  .elif (kl == "O_OBJ")
    .assign attr_result = "          ModelClass_c elem = ModelClass_c.getOneO_OBJOnR8001(pes[i]);"
  .elif (kl == "C_C")
    .assign attr_result = "          Component_c elem = Component_c.getOneC_COnR8001(pes[i]);"
  .elif (kl == "C_I")
    .assign attr_result = "          Interface_c elem = Interface_c.getOneC_IOnR8001(pes[i]);"
  .end if
.end function
.function get_precondition
  .param String kl
  .param boolean enableVisibilityFiltering
  .param String selectedKl
    .assign attr_preamble = ""
    .assign attr_postamble = ""
  .if(not enableVisibilityFiltering)
    .if (kl == "S_DT")
      .assign attr_preamble = "          if (v_element.Canusedatatype(elem.getDt_id())) {"
	  .assign attr_postamble = "         }"
    .elif (kl == "O_OBJ")
      .assign attr_preamble = "          if (v_element.Canassigntoclass(elem.getObj_id())) {"
	  .assign attr_postamble = "         }"
    .elif (kl == "C_C")
      .assign attr_preamble = "          if (v_element.Canassigntocomp(elem.getId(), false)) {"
	  .assign attr_postamble = "         }"
    .end if
  .else
    .if(selectedKl == "SQ_CIP")
	  .// do not allow if the model root contains
	  .// any domain instance
      .assign attr_preamble = "			if(instancesUnderSystem[i].getInstanceList(Domain_c.class).isEmpty()) {"
	  .assign attr_postamble = "		}"
	.elif(selectedKl == "SQ_CP")
	  .// do not allow if the model root contains
	  .// any domain instance
      .assign attr_preamble = "			if(instancesUnderSystem[i].getInstanceList(Domain_c.class).isEmpty()) {"
	  .assign attr_postamble = "		}"
	.elif(selectedKl == "SQ_COP")
	  .// do not allow if the model root contains
	  .// any ComponentPackage instance
      .assign attr_preamble = "			if(instancesUnderSystem[i].getInstanceList(ComponentPackage_c.class).isEmpty()) {"
	  .assign attr_postamble = "		}"
	.elif(selectedKl == "SQ_EEP")
	  .// do not allow if the model root contains
	  .// any domain instance
      .assign attr_preamble = "			if(instancesUnderSystem[i].getInstanceList(Domain_c.class).isEmpty()) {"
	  .assign attr_postamble = "		}"
	.end if
  .end if
.end function
.function get_ooa_type_name
  .param String kl
  .assign attr_result = ""
  .if (kl == "S_DT")
    .assign attr_result = "DATATYPE"
  .elif (kl == "O_OBJ")
    .assign attr_result = "CLASS"
  .elif (kl == "C_C")
    .assign attr_result = "COMPONENT"
  .elif (kl == "C_I")
    .assign attr_result = "INTERFACE"
  .end if
.end function
