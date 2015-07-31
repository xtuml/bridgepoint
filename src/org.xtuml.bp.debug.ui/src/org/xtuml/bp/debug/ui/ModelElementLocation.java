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
package org.xtuml.bp.debug.ui;

import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.PendingEvent_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.NonRootModelElement;

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
			if (parent != null) {
				text = getModelElementLocation(parent) + "::" + text;
			} 
		}
		else if (nrme instanceof Association_c) {
			text = "R" + ((Association_c) nrme).getNumb();
			text = "::" + text;
		}
		else if (nrme instanceof FunctionParameter_c) {
			text = ((FunctionParameter_c) nrme).getName();
			Function_c op = Function_c.getOneS_SYNCOnR24((FunctionParameter_c) nrme);
			text = getModelElementLocation(op) + "::" + text;
		}
		else if (nrme instanceof Function_c) {
			text = ((Function_c) nrme).getName();
			text = "::" + text;
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
			text = "::" + text;
		}
		else if (nrme instanceof Enumerator_c) {
			text = ((Enumerator_c) nrme).getName();
			DataType_c dt = DataType_c.getOneS_DTOnR17(
					EnumerationDataType_c.getOneS_EDTOnR27((Enumerator_c) nrme));
			text = getModelElementLocation(dt) + "::" + text;
		}
		else if (nrme instanceof DataType_c) {
			text = ((DataType_c) nrme).getName();
			text = "::" + text;
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
