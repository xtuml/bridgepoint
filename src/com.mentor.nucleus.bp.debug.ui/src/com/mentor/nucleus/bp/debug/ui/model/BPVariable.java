package com.mentor.nucleus.bp.debug.ui.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import com.mentor.nucleus.bp.core.AttributeValue_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.DataItemValue_c;
import com.mentor.nucleus.bp.core.FunctionParameter_c;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.InstanceSet_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.LocalReference_c;
import com.mentor.nucleus.bp.core.LocalValue_c;
import com.mentor.nucleus.bp.core.Local_c;
import com.mentor.nucleus.bp.core.OperationParameter_c;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.RuntimeValue_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.TransientVar_c;
import com.mentor.nucleus.bp.core.ValueInArray_c;
import com.mentor.nucleus.bp.core.ValueInStructure_c;
import com.mentor.nucleus.bp.core.Variable_c;

public class BPVariable extends BPDebugElement implements IVariable {
	
	Object value = null;

	public BPVariable(IDebugTarget debugTarget, ILaunch launch, Object variable) {
		super((BPDebugTarget)debugTarget, launch);
		value = variable;
	}

	public IValue getValue() throws DebugException {
		if (value != null)
		  return new BPValue(getDebugTarget(), getLaunch(), value);
		return null;
	}

	public String getReferenceTypeName() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasValueChanged() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setValue(String expression) throws DebugException {
		// TODO Auto-generated method stub

	}

	public void setValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub

	}

	public boolean supportsValueModification() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean verifyValue(String expression) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean verifyValue(IValue value) throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}
    public String getName() {
      if (value instanceof LocalValue_c) {
    	Variable_c var = Variable_c.getOneV_VAROnR814(TransientVar_c.getOneV_TRNOnR3005((LocalValue_c)value));
    	if (var != null) {
    	  return var.getName();
    	}
    	FunctionParameter_c fnP = FunctionParameter_c.getOneS_SPARMOnR3007(Local_c.getOneL_LCLOnR3001((LocalValue_c)value));
    	if (fnP != null) {
      	  return fnP.getName();
      	}
    	OperationParameter_c opP = OperationParameter_c.getOneO_TPARMOnR3008(Local_c.getOneL_LCLOnR3001((LocalValue_c)value));
    	if (opP != null) {
      	  return opP.getName();
      	}
    	BridgeParameter_c brP = BridgeParameter_c.getOneS_BPARMOnR3009(Local_c.getOneL_LCLOnR3001((LocalValue_c)value));
    	if (brP != null) {
      	  return brP.getName();
      	}
    	PropertyParameter_c propP = PropertyParameter_c.getOneC_PPOnR3017((LocalValue_c) value);
    	if(propP != null)
    		return propP.getName();
      }
      else if (value instanceof DataItemValue_c) {
    	StateMachineEventDataItem_c evdi = StateMachineEventDataItem_c.getOneSM_EVTDIOnR2934((DataItemValue_c)value);
    	if (evdi != null) {
    	  return evdi.getName();
    	}
    	PropertyParameter_c param = PropertyParameter_c.getOneC_PPOnR2956((DataItemValue_c) value);
    	if(param != null) {
    		return param.getName();
    	}
      }
      else if (value instanceof AttributeValue_c) {
      	Attribute_c attr = Attribute_c.getOneO_ATTROnR2910((AttributeValue_c)value);
      	if (attr != null) {
      	  return attr.getName();
      	}
      }
      else if (value instanceof LocalReference_c) {
      	InstanceHandle_c instHnd = InstanceHandle_c.getOneV_INTOnR3004((LocalReference_c)value);
      	InstanceSet_c instSet = InstanceSet_c.getOneV_INSOnR3003((LocalReference_c)value);
      	Variable_c var = null;
      	if (instHnd != null) {
      		var = Variable_c.getOneV_VAROnR814(instHnd);
      	}
      	if (instSet != null) {
      		var = Variable_c.getOneV_VAROnR814(instSet);
      	}
      	if (var != null) {
      		return var.getName();
      	}
      }
      else if (value instanceof RuntimeValue_c) {
    	  ValueInStructure_c vis = ValueInStructure_c.getOneRV_VISOnR3301((RuntimeValue_c)value);
    	  if (vis != null) {
    	    return vis.getName();
    	  }
    	  ValueInArray_c via = ValueInArray_c.getOneRV_VIAOnR3302((RuntimeValue_c)value);
    	  if (via != null) {
    		  return "[" + Integer.toString(via.getIndex()) + "]";
    	  }
      }
      else if (value instanceof Instance_c){
    	  return "instance";
      }
      return "Error: Variable for local value not found.";
    }
    
    public Class getType() {
    	return value.getClass();
    }
}
