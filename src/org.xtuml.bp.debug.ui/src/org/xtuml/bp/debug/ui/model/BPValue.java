 package org.xtuml.bp.debug.ui.model;
 
import java.util.UUID;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.xtuml.bp.core.ArrayValue_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AttributeValue_c;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.ComponentReferenceValue_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataItemValue_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.InstanceInReference_c;
import org.xtuml.bp.core.InstanceReferenceDataType_c;
import org.xtuml.bp.core.InstanceReferenceValue_c;
import org.xtuml.bp.core.Instance_c;
import org.xtuml.bp.core.LinkParticipation_c;
import org.xtuml.bp.core.Link_c;
import org.xtuml.bp.core.LocalReference_c;
import org.xtuml.bp.core.LocalValue_c;
import org.xtuml.bp.core.Local_c;
import org.xtuml.bp.core.NewStateTransition_c;
import org.xtuml.bp.core.PendingEvent_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.RuntimeValue_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.SimpleCoreValue_c;
import org.xtuml.bp.core.SimpleValue_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StructuredValue_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.ValueInArray_c;
import org.xtuml.bp.core.ValueInStructure_c;

public class BPValue extends BPDebugElement implements IValue {
    Object value = null;
    BPVariable  var = null;
	public BPValue(IDebugTarget debugTarget, ILaunch launch, Object val, String Name, BPVariable BPVar){
		super((BPDebugTarget)debugTarget, launch);
		value = val;
        name = Name;
        var = BPVar;
	}

	public String getReferenceTypeName() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getValueString(RuntimeValue_c p_rv) throws DebugException {
		String result = "";
		SimpleValue_c simVal = SimpleValue_c.getOneRV_SMVOnR3300(p_rv);
		StructuredValue_c strVal = StructuredValue_c.getOneRV_SVLOnR3300(p_rv);
		ArrayValue_c arrVal = ArrayValue_c.getOneRV_AVLOnR3300(p_rv);
		DataType_c dt = DataType_c.getOneS_DTOnR3307(p_rv);
		InstanceReferenceDataType_c irdt = InstanceReferenceDataType_c.getOneS_IRDTOnR17(dt);
		
		
		if ( simVal != null){
			SimpleCoreValue_c scv = SimpleCoreValue_c.getOneRV_SCVOnR3308(simVal);
			ComponentReferenceValue_c crv = ComponentReferenceValue_c.getOneRV_CRVOnR3308(simVal);
			InstanceReferenceValue_c irv = InstanceReferenceValue_c.getOneRV_IRVOnR3308(simVal);
			if ( scv != null){
				if ( irdt != null )
				{
					return "empty";
				}
				Object value = scv.getValue();
				if (value == null){
					value = "empty";
				}
				result = value.toString();
				if (dt != null && dt.getName().equals("string")) {
					  result = "\"" + result + "\"";
				   }
				return result;
			}
			else if  ( crv != null){
				 result = "Component not found";
				  ComponentInstance_c exe = ComponentInstance_c.getOneI_EXEOnR3309(crv);
				  if (exe != null) {
				    result = exe.getLabel() + "[" + exe.Getenginenumber() + "]";
				  }
				  return result;
			}
			else if ( irv != null){
				Instance_c [] insts = Instance_c.getManyI_INSsOnR3013(
						InstanceInReference_c.getManyL_IIRsOnR3311(irv));
				result = printInstanceSet(insts); 
				return result;
			}
			else {
				Throwable t = new Throwable();
				t.fillInStackTrace();
				CorePlugin
						.logError(
								"No subtype found for simple value.",
								t);
				return "Unknown simple runtime value";
			}
			
		}
		else if ( strVal != null){
			return p_rv.getLabel();
		}
		else if (arrVal !=null ){
			return p_rv.getLabel();
		}
		else {
			Throwable t = new Throwable();
			t.fillInStackTrace();
			CorePlugin
					.logError(
							"No subtype found for runtime value.",
							t);
			return "Unknown Runtime Value";
		}
	}

 	/**
 	 * Create a string of instance references array
 	 * in form of  
 	 *      [<array length>] #:<instance class>, #:<<instance class>, etc
 	 * 
 	 * @param insts  Instance references array
 	 * @return  the form string
 	 */
 	private String printInstanceSet(Instance_c[] insts) {
 		String result = "";
 		String sep = "";
 		if (insts.length == 0)
 			return "empty";
 		else if (insts.length == 1)
  			return insts[0].getLabel();
 		else { 
 		  result = insts.length > 1 ?  "["+insts.length+"]  " : "";
 		  for (int i=0; i<insts.length;i++) {
 			result = result + sep + insts[i].getLabel();
 			sep = ", ";
 		  }
  	}
 		return result;
 	}
 
 	
	public String getValueString() throws DebugException {
		if (value instanceof LocalValue_c) { 
		  LocalValue_c localValue = (LocalValue_c)value;
		  Local_c local = Local_c.getOneL_LCLOnR3001(localValue);
		  if (local != null) {
			UUID rtVal_ID = localValue.Getruntimevalue(local.getStack_frame_id());
			RuntimeValue_c rtVal = (RuntimeValue_c)local.getModelRoot().
			     getInstanceList(RuntimeValue_c.class).getGlobal(rtVal_ID.toString());
			return getValueString(rtVal);
		  }
		  else {
			RuntimeValue_c rtVal = RuntimeValue_c.getOneRV_RVLOnR3306(
					                    Local_c.getOneL_LCLOnR3001(localValue));
			SimpleValue_c smplVal = SimpleValue_c.getOneRV_SMVOnR3300(rtVal);
			if (smplVal != null) {
			  if (smplVal.Getvalue() != null) {
				String result = smplVal.Getvalue().toString();
                DataType_c dt = DataType_c.getOneS_DTOnR3307(rtVal);
				if (dt.getName().equals("string")) {
                  result = "\"" + result + "\"";
                }
			    return result;
			  }
			}
		  }
		}
		else if (value instanceof DataItemValue_c) {
		  DataItemValue_c div = (DataItemValue_c)value;
          RuntimeValue_c rtVal = RuntimeValue_c.getOneRV_RVLOnR3303(div);
          return getValueString(rtVal);
        }
		else if (value instanceof AttributeValue_c) {
			AttributeValue_c attrVal = (AttributeValue_c)value;
			RuntimeValue_c rtVal = RuntimeValue_c.getOneRV_RVLOnR3304(attrVal);
			if ( rtVal == null){
				// Referential attribute
				rtVal = (RuntimeValue_c) ((AttributeValue_c)value).getModelRoot().getInstanceList(
						RuntimeValue_c.class).getGlobal(null, attrVal.Getruntimevalue());
				
				String valueString = getValueString(rtVal);
				if ("Unknown Runtime Value".equalsIgnoreCase(valueString)){
					return "not participating";
				}
			}
			return  getValueString(rtVal);
			
		}
		else if (value instanceof LocalReference_c) {
			RuntimeValue_c rtVal = RuntimeValue_c.getOneRV_RVLOnR3306( Local_c.getOneL_LCLOnR3001(
					(LocalReference_c) value));
			if ( rtVal != null){
			return getValueString(rtVal);
			}else{
				return "empty";
			}
		}
		else if (value instanceof RuntimeValue_c) {
			return getValueString((RuntimeValue_c)value);
		}
		else if (value instanceof Instance_c){
			Instance_c inst = (Instance_c)value;
			return inst.getLabel();
				} 
		
	
		// Show More children for instance
		try{
			if (value instanceof StateMachineState_c){
				return ((StateMachineState_c)value).getName();
			}
			else if (value instanceof StateMachineEvent_c){
				return ((StateMachineEvent_c)value).getName();
			}
			else if (value instanceof PendingEvent_c) {
				String text = ((PendingEvent_c) value).getLabel();
				if (text == null) {
					return "";
				} else {
					return text;
				}
			}

			if (value instanceof Association_c) {
				Instance_c[] firstInstance = null;

				if (name.equals("Origin Of")) {
					Link_c[] instanceLinks = this.var.getInstanceLinksForAnAssociation();

					firstInstance = Instance_c .getManyI_INSsOnR2958(
							LinkParticipation_c.getManyI_LIPsOnR2903(
									instanceLinks));
					if (firstInstance.length == 0)
						firstInstance = Instance_c .getManyI_INSsOnR2958(
								LinkParticipation_c .getManyI_LIPsOnR2902(
										instanceLinks));

				} else if (name.equals("Destination Of")) {
					Link_c[] instanceLinks = this.var.getInstanceLinksForAnAssociation();

					firstInstance = Instance_c .getManyI_INSsOnR2958(
							LinkParticipation_c .getManyI_LIPsOnR2903(
									instanceLinks));
					if (firstInstance.length == 0)
						firstInstance = Instance_c .getManyI_INSsOnR2958(
								LinkParticipation_c .getManyI_LIPsOnR2901(
										instanceLinks));
				} 
				else if (name.equals("Associator For")) {
					Link_c[] instanceLinks = this.var.getInstanceLinksForAnAssociation();

					Instance_c[] first = Instance_c.getManyI_INSsOnR2958(
						LinkParticipation_c .getManyI_LIPsOnR2901(
									instanceLinks));
					Instance_c[] second = Instance_c .getManyI_INSsOnR2958(
							LinkParticipation_c .getManyI_LIPsOnR2902(
									instanceLinks));

					firstInstance = new Instance_c[first.length + second.length];
					System.arraycopy(first, 0, firstInstance, 0, first.length);
					System.arraycopy(second, 0,firstInstance , first.length, second.length);
				}
				return printInstanceSet(firstInstance);

			}else if ( value instanceof PendingEvent_c){
				return ((PendingEvent_c) value).getLabel();
			}else if ( value instanceof PendingEvent_c[]){
				PendingEvent_c[] events =  (PendingEvent_c[]) value;
				String result = "";
				String sep = "";
				if (events.length == 0)
					return "empty";
				else {
					result = events.length > 1 ?  "["+events.length+"]  " : "";
					for (int i=0; i<events.length;i++) {
						result = result + sep + events[i].getLabel();
						sep = ", ";
					}
				}
				return result;
			}
		}catch (Exception e) {
		return "Undefined";
       }
	return "Undefined";
	}

	public boolean isAllocated() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getValueStringForButtonTextPane() throws DebugException{
		return getValueString();
	}

	public IVariable[] getVariables(RuntimeValue_c p_rv) throws DebugException {

		SimpleValue_c simVal = SimpleValue_c.getOneRV_SMVOnR3300(p_rv);
		StructuredValue_c strVal = StructuredValue_c.getOneRV_SVLOnR3300(p_rv);
		ArrayValue_c arrVal = ArrayValue_c.getOneRV_AVLOnR3300(p_rv);
		
		RuntimeValue_c [] rvs = new RuntimeValue_c[0];
		if ( simVal != null){
			SimpleCoreValue_c scv = SimpleCoreValue_c.getOneRV_SCVOnR3308(simVal);
			ComponentReferenceValue_c crv = ComponentReferenceValue_c.getOneRV_CRVOnR3308(simVal);
			InstanceReferenceValue_c irv = InstanceReferenceValue_c.getOneRV_IRVOnR3308(simVal);
			if ( scv != null){
				rvs = new RuntimeValue_c[0];
				return getChildern(rvs, null, null, null);
			}
			else if  ( crv != null){
				rvs = new RuntimeValue_c[0];
				return getChildern(rvs, null, null, null);
			}
			else if ( irv != null){
				Instance_c [] insts = Instance_c.getManyI_INSsOnR3013(
						InstanceInReference_c.getManyL_IIRsOnR3311(irv));
				if (insts.length == 1) {
					AttributeValue_c[] vals = AttributeValue_c.
					                                 getManyI_AVLsOnR2909(insts[0]);
					return getInstanceChildern(insts[0]);
				}
				else if (insts.length > 1) {
					return getChildern(insts, null, null, null);
				}
				else if  (insts.length == 0) {
					rvs = new RuntimeValue_c[0];
					return getChildern(rvs, null, null, null);
				}
			}
			else {
		
			}
			
		}
		else if ( strVal != null){
		    rvs = RuntimeValue_c.getManyRV_RVLsOnR3301(
	                   ValueInStructure_c.getManyRV_VISsOnR3301(strVal));
		    return getChildern(rvs, null, null, null);
		}
		else if (arrVal !=null ){
			ValueInArray_c [] vias = ValueInArray_c.getManyRV_VIAsOnR3302(arrVal);
			rvs = RuntimeValue_c.getManyRV_RVLsOnR3302(vias);
			return getChildern(rvs, null, null, null);
		}
		else {
			
		}
		
	
		return getChildern(rvs, null, null, null);
	}
	/**
	 * Collect all instance children.
	 * Support two modes based on BP Verifier preference.
	 * 
	 * 
	 * @param instance  the instance to collect children for
	 * @return An array with instance children
	 */
	private IVariable[] getInstanceChildern(Instance_c inst) {
		AttributeValue_c[] vals = AttributeValue_c.getManyI_AVLsOnR2909(inst);
		StateMachineEvent_c event = null;
		
		try{
			// Last Executed Transition
			event = StateMachineEvent_c.getOneSM_EVTOnR525(
					SemEvent_c.getOneSM_SEVTOnR503(
							StateEventMatrixEntry_c.getOneSM_SEMEOnR504(
									NewStateTransition_c.getManySM_NSTXNsOnR507(
											Transition_c.getOneSM_TXNOnR2953(inst)))));

			StateMachineState_c currentState = StateMachineState_c.getOneSM_STATEOnR2915(inst);

			// collect associations based on instance relation direction
			Link_c[] originLinks = Link_c.getManyI_LNKsOnR2901(LinkParticipation_c .getManyI_LIPsOnR2958(inst));
			Association_c[] originAssocs = Association_c.getManyR_RELsOnR2904(originLinks);
			// Link_c instance used later in order to determine which links related to each association belongs to this instance
			IVariable[] originLinksChildern = getChildern(originAssocs, "Origin Of", originLinks, inst);


			Link_c[] destLinks = Link_c.getManyI_LNKsOnR2902(LinkParticipation_c .getManyI_LIPsOnR2958(inst));
			Association_c[] destAssocs = Association_c.getManyR_RELsOnR2904(destLinks);
			// Link_c instance used later in order to determine which links related to each association belongs to this instance
			IVariable[] destLinksChildern = getChildern(destAssocs, "Destination Of", destLinks, inst);

			Link_c[] assocLinks = Link_c.getManyI_LNKsOnR2903(LinkParticipation_c .getManyI_LIPsOnR2958(inst));
			Association_c[] linkedAsscos = Association_c.getManyR_RELsOnR2904(assocLinks);
			// Link_c instance used later in order to determine which links related to each association belongs to this instance
        	IVariable[] assocLinksChildern = getChildern(linkedAsscos, "Associator For", assocLinks, inst);

			IVariable[] attibutesChildern = getChildern(vals, null, null, null);

			// Collect Pending events instance
			PendingEvent_c[] pendingEvents = PendingEvent_c.getManyI_EVIsOnR2935(inst);

			int validState = 0;
			int validEvent = 0;
			int validPendingEvents = 0;
			if (pendingEvents.length != 0){
				validPendingEvents = 1;
			}
			if (currentState != null ){
				validState = 1;
			}
			if (event!= null){
				validEvent = 1;
			}

			// Create the whole children arrays
			IVariable[] childern = new IVariable[originLinksChildern.length
			                                     + destLinksChildern.length + assocLinksChildern.length + attibutesChildern.length + validEvent + validState + validPendingEvents];

			if (childern.length == 0){
				return childern;
			}

			childern[0] = new BPVariable(getDebugTarget(), getLaunch(), event, null);
			childern[validEvent] = new BPVariable(getDebugTarget(), getLaunch(), currentState, null);
			
			int childernIndex = validState + validEvent;
			// Add Attributes to children list
			if (attibutesChildern.length !=0){
				System.arraycopy(attibutesChildern, 0, childern, childernIndex, attibutesChildern.length);
			}
			// update the children list index
			childernIndex = childernIndex + attibutesChildern.length;
			// Add Pending events
			childern[childernIndex ] = new BPVariable(getDebugTarget(), getLaunch(), pendingEvents, null);
			
			childernIndex = childernIndex + validPendingEvents;
			if (originLinksChildern.length !=0){
				System.arraycopy(originLinksChildern, 0, childern, childernIndex, originLinksChildern.length);
			}
			childernIndex = childernIndex + originLinksChildern.length;
			if (destLinksChildern.length !=0){
				System.arraycopy(destLinksChildern, 0, childern, childernIndex, destLinksChildern.length);
			}
			childernIndex = childernIndex + destLinksChildern.length;
			if (assocLinksChildern.length !=0){
				System.arraycopy(assocLinksChildern, 0, childern, childernIndex, assocLinksChildern.length);
			}
			return childern;
		}catch (Exception e) {
				IVariable[] attibutesChildern = getChildern(vals, null, null, null);
				return attibutesChildern;
			}
 	}
	/**
	 * @param objects : represents the child values for the select variable in Variable View
	 * @return 
	 */
	private IVariable[] getChildern(Object[] objects, String Name, Object[] linkedValue, Object Instance)  {
		IVariable [] result = new IVariable[objects.length];
		  for (int i=0; i< objects.length; i++) {
		    result[i] = new BPVariable(getDebugTarget(), getLaunch(), objects[i], Name, linkedValue, Instance);
		  }
		  return result;
	}
	
	public IVariable[] getVariables() throws DebugException {
		if (value instanceof DataItemValue_c) {
		  DataItemValue_c div = (DataItemValue_c)value;
	      RuntimeValue_c rtVal = RuntimeValue_c.getOneRV_RVLOnR3303(div);
	      return getVariables(rtVal);
        }
		else if (value instanceof LocalReference_c) {
			
			RuntimeValue_c rtVal = RuntimeValue_c.getOneRV_RVLOnR3306(Local_c
					.getOneL_LCLOnR3001((LocalReference_c) value));
			return getVariables(rtVal);
		}
		else if (value instanceof AttributeValue_c) {
			AttributeValue_c av = (AttributeValue_c)value;
			RuntimeValue_c rootRv = RuntimeValue_c.getOneRV_RVLOnR3304(av);
			return getVariables(rootRv);
		}
		else if (value instanceof LocalValue_c) {
			LocalValue_c lv = (LocalValue_c)value;
			Local_c local = Local_c.getOneL_LCLOnR3001(lv);
			if (local != null) {
			  PropertyParameter_c pp = PropertyParameter_c.getOneC_PPOnR3017(lv);
			  UUID rtVal_ID = lv.Getruntimevalue(local.getStack_frame_id());
			  RuntimeValue_c rootRv = (RuntimeValue_c)local.getModelRoot().
			     getInstanceList(RuntimeValue_c.class).getGlobal(rtVal_ID.toString());
			  return getVariables(rootRv);
			}
		}
		else if (value instanceof RuntimeValue_c) {
			return getVariables((RuntimeValue_c)value);
		}
		else if (value instanceof Instance_c){			 
			return getInstanceChildern((Instance_c)value);
		}
		else 
		{		
					// Show More children for instance
					try{
						if (value instanceof Association_c) {
							Instance_c[] firstInstance = null;
			
							if (name.equals("Origin Of")) {
								Link_c[] instanceLinks = this.var.getInstanceLinksForAnAssociation();
			
								firstInstance = Instance_c .getManyI_INSsOnR2958(
										LinkParticipation_c.getManyI_LIPsOnR2903(
												instanceLinks));
								if (firstInstance.length == 0)
									firstInstance = Instance_c .getManyI_INSsOnR2958(
											LinkParticipation_c .getManyI_LIPsOnR2902(
													instanceLinks));
			
							} else if (name.equals("Destination Of")) {
								Link_c[] instanceLinks = this.var.getInstanceLinksForAnAssociation();
			
								firstInstance = Instance_c .getManyI_INSsOnR2958(
										LinkParticipation_c .getManyI_LIPsOnR2903(
												instanceLinks));
								if (firstInstance.length == 0)
									firstInstance = Instance_c .getManyI_INSsOnR2958(
											LinkParticipation_c .getManyI_LIPsOnR2901(
													instanceLinks));
							} 
			
							else if (name.equals("Associator For")) {
								Link_c[] instanceLinks = this.var.getInstanceLinksForAnAssociation();
			
			
								Instance_c[] first = Instance_c.getManyI_INSsOnR2958(
										LinkParticipation_c .getManyI_LIPsOnR2901(
												instanceLinks));
			
								Instance_c[] second = Instance_c .getManyI_INSsOnR2958(
										LinkParticipation_c .getManyI_LIPsOnR2902(
												instanceLinks));
			
								firstInstance = new Instance_c[first.length + second.length];
								System.arraycopy(first, 0, firstInstance, 0, first.length);
								System.arraycopy(second, 0,firstInstance , first.length, second.length);
							}
							return getChildern(firstInstance, null, null, null);
						} else if ( value instanceof PendingEvent_c[]){
							return getChildern((Object[]) value, null , null, null);
						}
					}catch (Exception e) {
						return new IVariable[0];
					}
		}
		 		return new IVariable[0];
	}

	public boolean hasVariables() throws DebugException {
		return getVariables().length > 0;
	}

	public String getName() {
		return "Value ";
	}
}
