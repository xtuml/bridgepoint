package org.xtuml.bp.debug.ui.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AttributeValue_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataItemValue_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.InstanceHandle_c;
import org.xtuml.bp.core.InstanceSet_c;
import org.xtuml.bp.core.Instance_c;
import org.xtuml.bp.core.LinkParticipation_c;
import org.xtuml.bp.core.Link_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.LocalReference_c;
import org.xtuml.bp.core.LocalValue_c;
import org.xtuml.bp.core.Local_c;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.PendingEvent_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.RuntimeValue_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.TransientVar_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.ValueInArray_c;
import org.xtuml.bp.core.ValueInStructure_c;
import org.xtuml.bp.core.Variable_c;

public class BPVariable extends BPDebugElement implements IVariable {
	
	Object value = null;
	Object[] linkedValues  = null;
	Object instance = null;

	public BPVariable(IDebugTarget debugTarget, ILaunch launch, Object variable, String Name) {
		super((BPDebugTarget)debugTarget, launch);
		value = variable;
		name = Name;
		linkedValues = null;
		instance = null;
	}
	public BPVariable(IDebugTarget debugTarget, ILaunch launch, Object variable, String Name, Object[] LinkedValues, Object Instance) {
		super((BPDebugTarget)debugTarget, launch);
		value = variable;
		name = Name;
		linkedValues = LinkedValues;
		instance = Instance;
	}

	public IValue getValue() throws DebugException {
		if (value != null)
		  return new BPValue(getDebugTarget(), getLaunch(), value, name, this);
		return null;
	}
	
	public void setName(String Name){
		super.setName(Name);
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
      
      try{
    	  if (value instanceof StateMachineState_c){
    		  return "Current State";
    	  }
    	  if (value instanceof StateMachineEvent_c){
    		  return "Last Executed Transition";
    	  }
    	  if (value instanceof Association_c){ 
    		  if (name.equals("Origin Of")) {
    			  Link_c[] instanceLinks = getInstanceLinksForAnAssociation();
    			  LinkParticipation_c lp = LinkParticipation_c .getOneI_LIPOnR2902( instanceLinks);
    			  if (lp == null)
    				  return LinkParticipation_c.getOneI_LIPOnR2903( instanceLinks).getLabel();
    			  else 
    				  return lp.getLabel();

    		  } else if (name.equals("Destination Of")) {
    			  Link_c[] instanceLinks = getInstanceLinksForAnAssociation();
    			  LinkParticipation_c lp = LinkParticipation_c .getOneI_LIPOnR2901( instanceLinks);
    			  if (lp == null)
    				  return LinkParticipation_c .getOneI_LIPOnR2903( instanceLinks).getLabel();
    			  else 
    				  return lp.getLabel();
    		  } 
    		  else if (name.equals("Associator For")) {
    			  return "R" + ((Association_c)value).getNumb();

    		  }
    		  return "R" + ((Association_c)value).getNumb();
    	  }
    	  else if ( value instanceof PendingEvent_c[]){
    		  return "Pending Events";
    	  }
    	  else if ( value instanceof PendingEvent_c){
    		  return "Event";
    	  }
      }catch (Exception e) {
    	  return "Error: Variable for local value not found.";
      }
      
      return "Error: Variable for local value not found.";
    }
    
    /**
	 *  Get association links only selected association and instance.
	 *  
	 *  the linkedValues contains a list of all selected instance in particular
	 *  linking direction.
	 *  While querying for Association links will return an array for selected
	 *  instance links and other instances links
	 *  This method return the common links in linkedValues and Association
	 *  links array.
	 * 
	 * @return an Link_c array for selected instance in selected association.
	 */
    public Link_c[] getInstanceLinksForAnAssociation() {
		Link_c[] allOriginlinks = (Link_c[]) linkedValues;
		Link_c[] allAssocLinks = Link_c.getManyI_LNKsOnR2904((Association_c) value);
		ArrayList<Link_c> validLinks = new ArrayList<Link_c>();
		
		// Create a hashmap for fast search.
		HashMap<Link_c, String>  DuplicateInspection = new HashMap<Link_c, String>();
		for (Link_c link : allOriginlinks) {
			DuplicateInspection.put(link, "");
		}
		for (Link_c link : allAssocLinks) {
			String exist = DuplicateInspection.get(link);
			if (exist != null){
				/* A common Link_c instance between instance Link_c array
				 * and association Link_c array 
				*/  
				validLinks.add(link);
			}
		}
		Link_c[] instanceLinks = validLinks.toArray(new Link_c[validLinks.size()]);
		return instanceLinks;
	}
    
    public Class getType() {
    	return value.getClass();
    }
}