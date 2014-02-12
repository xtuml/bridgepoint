//====================================================================
//
//File:      $RCSfile: ModelElementLocation.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/01/10 23:17:56 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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
//======================================================================== 
//
package com.mentor.nucleus.bp.debug.ui;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.DataTypeInPackage_c;
import com.mentor.nucleus.bp.core.DataTypePackageInPackage_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EePackageInPackage_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.Enumerator_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionPackageInPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SubsystemInDomain_c;
import com.mentor.nucleus.bp.core.SubsystemInSubsystem_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class ModelElementLocation {
	static public String getModelElementLocation(NonRootModelElement nrme) {
		String text = "Unknown model element";
		if (nrme instanceof StateMachineState_c) {
			text = ((StateMachineState_c) nrme).getName();
			StateMachine_c sm = StateMachine_c.getOneSM_SMOnR501((StateMachineState_c)nrme);
			text = getModelElementLocation(sm) + "::" + text;
		}
		else if(nrme instanceof CreationTransition_c){
			
			CreationTransition_c ctrans=((CreationTransition_c) nrme);
			text= ctrans.getName();
			Transition_c trans=Transition_c.getOneSM_TXNOnR507(ctrans);
			StateMachine_c sm = StateMachine_c.getOneSM_SMOnR505(trans);
			text = getModelElementLocation(sm) + "::" + text;
		}
		else if (nrme instanceof StateMachineEvent_c) {
			StateMachineEvent_c evt = (StateMachineEvent_c) nrme;
			text = evt.getDrv_lbl();
			if ( evt.getMning().length() != 0 ) {
				text = text + ":'" + evt.getMning() + "'";
			}
			StateMachine_c sm = StateMachine_c.getOneSM_SMOnR502((StateMachineEvent_c)nrme);
			text = getModelElementLocation(sm) + "::" + text;
		}
		else if (nrme instanceof Transition_c) {
			Transition_c trans = (Transition_c) nrme;
			text = trans.Get_name();
			StateMachine_c sm = StateMachine_c.getOneSM_SMOnR505(trans);
			text = getModelElementLocation(sm) + "::" + text;
		}
		else if (nrme instanceof PendingEvent_c) {
			StateMachineEvent_c evt = StateMachineEvent_c.getOneSM_EVTOnR2906((PendingEvent_c) nrme);
			text = evt.getDrv_lbl();
			if ( evt.getMning().length() != 0 ) {
				text = text + ":'" + evt.getMning() + "'";
			}
			StateMachine_c sm = StateMachine_c.getOneSM_SMOnR502(evt);
			text = getModelElementLocation(sm) + "::" + text + "::" 
			    + ((PendingEvent_c) nrme).getEvent_id();
		}
		else if (nrme instanceof StateMachine_c ) {
			InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR517((StateMachine_c)nrme);
			ModelClass_c mc ;
			if ( ism != null ) {
				mc = ModelClass_c.getOneO_OBJOnR518(ism);
			}
			else {
				ClassStateMachine_c csm = ClassStateMachine_c.getOneSM_ASMOnR517((StateMachine_c)nrme);
				mc = ModelClass_c.getOneO_OBJOnR519(csm);
			}
			text = getModelElementLocation(mc);
			
		}
		else if (nrme instanceof OperationParameter_c) {
			text = ((OperationParameter_c) nrme).getName();
			Operation_c op = Operation_c.getOneO_TFROnR117((OperationParameter_c) nrme);
			text = getModelElementLocation(op) + "::" + text;
		}
		else if (nrme instanceof Operation_c) {
			text = ((Operation_c) nrme).getName();
			ModelClass_c mc = ModelClass_c.getOneO_OBJOnR115((Operation_c)nrme);
			text = getModelElementLocation(mc) + "::" + text;
		}
		else if (nrme instanceof Attribute_c) {
			text = ((Attribute_c) nrme).getName();
			ModelClass_c mc = ModelClass_c.getOneO_OBJOnR102((Attribute_c)nrme);
			text = getModelElementLocation(mc) + "::" + text;
		}
		else if (nrme instanceof ModelClass_c) {
			text = ((ModelClass_c) nrme).getName();
			Package_c parent = Package_c.getOneEP_PKGOnR8000(
					PackageableElement_c.getOnePE_PEOnR8001((ModelClass_c)nrme));
			if (parent != null)
				text = getModelElementLocation(parent) + "::" + text;
			else {
			  Subsystem_c ss = Subsystem_c.getOneS_SSOnR2((ModelClass_c)nrme);
			  if (ss != null)
			    text = getModelElementLocation(ss) + "::" + text;
			}
		}
		else if (nrme instanceof Association_c) {
			text = "R" + ((Association_c) nrme).getNumb();
			Subsystem_c ss = Subsystem_c.getOneS_SSOnR4((Association_c)nrme);
			text = getModelElementLocation(ss) + "::" + text;
		}
		else if (nrme instanceof Subsystem_c) {
			text = ((Subsystem_c) nrme).getName();
			SubsystemInSubsystem_c sis = SubsystemInSubsystem_c.getOneS_SISOnR42((Subsystem_c)nrme);
			if ( sis != null ) {
				Subsystem_c parent_ss = Subsystem_c.getOneS_SSOnR41(sis);
				text = getModelElementLocation(parent_ss) + "::" + text;
			}
			else {
				Domain_c dom = Domain_c.getOneS_DOMOnR43(SubsystemInDomain_c.getOneS_SIDOnR43((Subsystem_c)nrme));
				text = getModelElementLocation(dom) + "::" + text;
			}
		}
		else if (nrme instanceof FunctionParameter_c) {
			text = ((FunctionParameter_c) nrme).getName();
			Function_c op = Function_c.getOneS_SYNCOnR24((FunctionParameter_c) nrme);
			text = getModelElementLocation(op) + "::" + text;
		}
		else if (nrme instanceof Function_c) {
			text = ((Function_c) nrme).getName();
			FunctionPackage_c fp = FunctionPackage_c.getOneS_FPKOnR31((Function_c)nrme);
			text = getModelElementLocation(fp) + "::" + text;
		}
		else if (nrme instanceof FunctionPackage_c) {
			text = ((FunctionPackage_c) nrme).getName();
			FunctionPackageInPackage_c fpip = FunctionPackageInPackage_c.getOneS_FPIPOnR32((FunctionPackage_c) nrme);
			if ( fpip != null ) {
				FunctionPackage_c parent_fp = FunctionPackage_c.getOneS_FPKOnR30(fpip);
				text = getModelElementLocation(parent_fp) + "::" + text;
			}
			else {
				Domain_c dom = Domain_c.getOneS_DOMOnR29((FunctionPackage_c) nrme);
				text = getModelElementLocation(dom) + "::" + text;
			}
		}
		else if (nrme instanceof BridgeParameter_c) {
			text = ((BridgeParameter_c) nrme).getName();
			Bridge_c op = Bridge_c.getOneS_BRGOnR21((BridgeParameter_c) nrme);
			text = getModelElementLocation(op) + "::" + text;
		}
		else if (nrme instanceof Bridge_c) {
			text = ((Bridge_c) nrme).getName();
			ExternalEntity_c ee = ExternalEntity_c.getOneS_EEOnR19((Bridge_c)nrme);
			text = getModelElementLocation(ee) + "::" + text;
		}
		else if (nrme instanceof ExternalEntity_c) {
			text = ((ExternalEntity_c) nrme).getName();
			ExternalEntityPackage_c fp = ExternalEntityPackage_c.getOneS_EEPKOnR33((ExternalEntity_c)nrme);
			text = getModelElementLocation(fp) + "::" + text;
		}
		else if (nrme instanceof ExternalEntityPackage_c) {
			text = ((ExternalEntityPackage_c) nrme).getName();
			EePackageInPackage_c eepip = EePackageInPackage_c.getOneS_EEPIPOnR35((ExternalEntityPackage_c) nrme);
			if ( eepip != null ) {
				ExternalEntityPackage_c parent_eep = ExternalEntityPackage_c.getOneS_EEPKOnR34(eepip);
				text = getModelElementLocation(parent_eep) + "::" + text;
			}
			else {
				Domain_c dom = Domain_c.getOneS_DOMOnR36((ExternalEntityPackage_c) nrme);
				text = getModelElementLocation(dom) + "::" + text;
			}
		}
		else if (nrme instanceof Enumerator_c) {
			text = ((Enumerator_c) nrme).getName();
			DataType_c dt = DataType_c.getOneS_DTOnR17(
					EnumerationDataType_c.getOneS_EDTOnR27((Enumerator_c) nrme));
			text = getModelElementLocation(dt) + "::" + text;
		}
		else if (nrme instanceof DataType_c) {
			text = ((DataType_c) nrme).getName();
			DataTypeInPackage_c dtip = DataTypeInPackage_c.getOneS_DIPOnR39((DataType_c) nrme);
			DataTypePackage_c dtp = DataTypePackage_c.getOneS_DPKOnR39(dtip);
			text = getModelElementLocation(dtp) + "::" + text;
		}
		else if (nrme instanceof DataTypePackage_c) {
			text = ((DataTypePackage_c) nrme).getName();
			DataTypePackageInPackage_c dtpip = DataTypePackageInPackage_c.getOneS_DPIPOnR38((DataTypePackage_c) nrme);
			if ( dtpip != null ) {
				DataTypePackage_c parent_dtp = DataTypePackage_c.getOneS_DPKOnR37(dtpip);
				text = getModelElementLocation(parent_dtp) + "::" + text;
			}
			else {
				Domain_c dom = Domain_c.getOneS_DOMOnR40((DataTypePackage_c) nrme);
				text = getModelElementLocation(dom) + "::" + text;
			}
		}
		else if (nrme instanceof Domain_c) {
			text = ((Domain_c) nrme).getName();
			SystemModel_c sysMod = SystemModel_c.getOneS_SYSOnR28((Domain_c)nrme);
			if(sysMod != null) {
				text = getModelElementLocation(sysMod) + "::" + text;
			} else {
				Component_c component = Component_c
						.getOneC_COnR4204(DomainAsComponent_c
								.getOneCN_DCOnR4204((Domain_c) nrme));
				text = getModelElementLocation(component) + "::" + text;
			}
		}
		else if (nrme instanceof SystemModel_c) {
			text = ((SystemModel_c) nrme).getName();
		} else if(nrme instanceof Component_c) {
			text = ((Component_c) nrme).Getpath(true, "");
		} else if(nrme instanceof RequiredOperation_c) {
			text = ((RequiredOperation_c) nrme).getName();
			Component_c component = Component_c
					.getOneC_COnR4010(Port_c
							.getOneC_POOnR4016(InterfaceReference_c
									.getOneC_IROnR4009(Requirement_c
											.getOneC_ROnR4500(RequiredExecutableProperty_c
													.getOneSPR_REPOnR4502((RequiredOperation_c) nrme)))));
			if(component != null)
				text = getModelElementLocation(component) + "::" + text;
		} else if(nrme instanceof RequiredSignal_c) {
			text = ((RequiredSignal_c) nrme).getName();
			Component_c component = Component_c
					.getOneC_COnR4010(Port_c
							.getOneC_POOnR4016(InterfaceReference_c
									.getOneC_IROnR4009(Requirement_c
											.getOneC_ROnR4500(RequiredExecutableProperty_c
													.getOneSPR_REPOnR4502((RequiredSignal_c) nrme)))));
			if(component != null)
				text = getModelElementLocation(component) + "::" + text;			
		} else if(nrme instanceof ProvidedOperation_c) {
			text = ((ProvidedOperation_c) nrme).getName();
			Component_c component = Component_c
				.getOneC_COnR4010(Port_c
					.getOneC_POOnR4016(InterfaceReference_c
							.getOneC_IROnR4009(Provision_c
									.getOneC_POnR4501(ProvidedExecutableProperty_c
											.getOneSPR_PEPOnR4503((ProvidedOperation_c) nrme)))));
			if(component != null)
				text = getModelElementLocation(component) + "::" + text;			
		} else if(nrme instanceof ProvidedSignal_c) {
			text = ((ProvidedSignal_c) nrme).getName();
			Component_c component = Component_c
				.getOneC_COnR4010(Port_c
					.getOneC_POOnR4016(InterfaceReference_c
							.getOneC_IROnR4009(Provision_c
									.getOneC_POnR4501(ProvidedExecutableProperty_c
											.getOneSPR_PEPOnR4503((ProvidedSignal_c) nrme)))));
			if(component != null)
				text = getModelElementLocation(component) + "::" + text;			
		}
		else if (nrme instanceof Package_c) {
			text = ((Package_c)nrme).getName();
			Package_c parent = Package_c.getOneEP_PKGOnR8000(
					PackageableElement_c.getOnePE_PEOnR8001((Package_c)nrme));
			if (parent != null)
				text = getModelElementLocation(parent) + "::" + text;			
		}
		return text;
	}

}
