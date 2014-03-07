package com.mentor.nucleus.bp.debug.ui.model;

import java.util.UUID;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.mentor.nucleus.bp.core.ArrayValue_c;
import com.mentor.nucleus.bp.core.AttributeValue_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReferenceValue_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataItemValue_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.InstanceInReference_c;
import com.mentor.nucleus.bp.core.InstanceReferenceDataType_c;
import com.mentor.nucleus.bp.core.InstanceReferenceValue_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.LocalReference_c;
import com.mentor.nucleus.bp.core.LocalValue_c;
import com.mentor.nucleus.bp.core.Local_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.RuntimeValue_c;
import com.mentor.nucleus.bp.core.SimpleCoreValue_c;
import com.mentor.nucleus.bp.core.SimpleValue_c;
import com.mentor.nucleus.bp.core.StructuredValue_c;
import com.mentor.nucleus.bp.core.ValueInArray_c;
import com.mentor.nucleus.bp.core.ValueInStructure_c;

public class BPValue extends BPDebugElement implements IValue {
    Object value = null;

	public BPValue(IDebugTarget debugTarget, ILaunch launch, Object val) {
		super((BPDebugTarget)debugTarget, launch);
		value = val;
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
				String sep = "";
				if (insts.length == 0)
					return "empty";
				else {
				  for (int i=0; i<insts.length;i++) {
					result = result + sep + insts[i].getLabel();
					sep = ", ";
				  }
			    }
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
		return "Undefined";
	}

	public boolean isAllocated() throws DebugException {
		// TODO Auto-generated method stub
		return false;
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
				return getChildern(rvs);
			}
			else if  ( crv != null){
				rvs = new RuntimeValue_c[0];
				return getChildern(rvs);
			}
			else if ( irv != null){
				Instance_c [] insts = Instance_c.getManyI_INSsOnR3013(
						InstanceInReference_c.getManyL_IIRsOnR3311(irv));
				if (insts.length == 1) {
					AttributeValue_c[] vals = AttributeValue_c.
					                                 getManyI_AVLsOnR2909(insts[0]);
					return getChildern(vals);
				}
				else if (insts.length > 1) {
					return getChildern(insts);
				}
				else if  (insts.length == 0) {
					rvs = new RuntimeValue_c[0];
					return getChildern(rvs);
				}
			}
			else {
		
			}
			
		}
		else if ( strVal != null){
		    rvs = RuntimeValue_c.getManyRV_RVLsOnR3301(
	                   ValueInStructure_c.getManyRV_VISsOnR3301(strVal));
		    return getChildern(rvs);
		}
		else if (arrVal !=null ){
			ValueInArray_c [] vias = ValueInArray_c.getManyRV_VIAsOnR3302(arrVal);
			rvs = RuntimeValue_c.getManyRV_RVLsOnR3302(vias);
			return getChildern(rvs);
		}
		else {
			
		}
		
	
		return getChildern(rvs);
	}

	/**
	 * @param objects : represents the child values for the select variable in Variable View
	 * @return 
	 */
	private IVariable[] getChildern(Object[] objects) {
		IVariable [] result = new IVariable[objects.length];
		  for (int i=0; i< objects.length; i++) {
		    result[i] = new BPVariable(getDebugTarget(), getLaunch(), objects[i]);
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
			AttributeValue_c[] vals = AttributeValue_c
			.getManyI_AVLsOnR2909((Instance_c)value);
			return getChildern(vals);
		}
		else 
		{
			// report error
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
