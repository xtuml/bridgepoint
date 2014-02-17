//========================================================================
//
//File:      $RCSfile: OoaofooaUtil.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2012/10/09 02:24:30 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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

package com.mentor.nucleus.bp.core.util;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

/**
 * Contains utility methods that are useful when deleting with Ooaofooa
 * model elements.
 */
public class OoaofooaUtil
{
    /**
     * Returns the subsystem of the given name from the given model-root.
     */
    public static Subsystem_c getSubsystem(Ooaofooa modelRoot, final String name)
    {
        return Subsystem_c.SubsystemInstance(modelRoot, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Subsystem_c)candidate).getName().equals(name);
                }
            });
    }
    
    /**
     * Returns the class of the given name from the given subsystem.
     */
    public static ModelClass_c getClass(Subsystem_c subsystem, final String name)
    {
        return ModelClass_c.getOneO_OBJOnR2(subsystem, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((ModelClass_c)candidate).getName().equals(name);
                }
            });
    }
     
    public static ModelClass_c getClass(Package_c pkg, final String name)
    {
        
        return ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg), 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((ModelClass_c)candidate).getName().equals(name);
                }
            });
    }
    /**
     * Returns the attribute of the given name, of the given class.
     */
    public static Attribute_c getAttribute(ModelClass_c clazz, final String name)
    {
        return Attribute_c.getOneO_ATTROnR102(clazz, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Attribute_c)candidate).getName().equals(name);
                }
            });
    }
    
    /**
     * Returns (what is presumed to be) the only operation of the given name, 
     * on the given class.
     */
    public static Operation_c getOperation(ModelClass_c clazz, final String name)
    {
        return Operation_c.getOneO_TFROnR115(clazz, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Operation_c)candidate).getName().equals(name);
                }
            });
    }
    
    /**
     * Returns the operation-parameter of the given name from the given 
     * operation.
     */
    public static OperationParameter_c getOperationParameter(
        Operation_c operation, final String parameterName)
    {
        return OperationParameter_c.getOneO_TPARMOnR117(operation, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((OperationParameter_c)candidate).getName().equals(
                        parameterName);
                }
            });
    }
    
    /**
     * Returns the first state machine found of the class of the 
     * given name.
     */
    public static StateMachine_c getStateMachine(Ooaofooa modelRoot,
        final String className) 
    {
        StateMachine_c machine = StateMachine_c.StateMachineInstance(
            modelRoot,
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((StateMachine_c)candidate).Get_name().equals(
                        className);
                }
            });
        return machine;
    }

    /**
     * Returns the state of the given name, of the given state machine.
     */
    public static StateMachineState_c getState(
        StateMachine_c machine, final String name) 
    {
        return StateMachineState_c.getOneSM_STATEOnR501(machine, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((StateMachineState_c)candidate).getName().equals(name);
                }
            });
    }

    /**
     * Returns the event of the given meaning, of the given state machine.
     */
    public static StateMachineEvent_c getEvent(
        StateMachine_c machine, final String meaning) 
    {
        return StateMachineEvent_c.getOneSM_EVTOnR502(machine, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((StateMachineEvent_c)candidate).getMning().
                        equals(meaning);
                }
            });
    }
    
    /**
     * Returns the event data-item of the given name, of the given state machine.
     */
    public static StateMachineEventDataItem_c getDataItem(
        StateMachine_c machine, final String name) 
    {
        return StateMachineEventDataItem_c.getOneSM_EVTDIOnR516(machine, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((StateMachineEventDataItem_c)candidate).
                        getName().equals(name);
                }
            });
    }
    
    /**
     * Returns the association of the given number found under the given
     * model-root.
     */
    public static Association_c getAssociation(
        Ooaofooa modelRoot, final int number)
    {
        return Association_c.AssociationInstance(modelRoot,
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Association_c)candidate).getNumb() == number;
                }
            });
    }

    /**
     * Returns the association of the given number that relates the given class
     * to some other model element.
     */
    public static Association_c getAssociation(
        ModelClass_c clazz, final int number)
    {
        return Association_c.getOneR_RELOnR201(clazz,
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Association_c)candidate).getNumb() == number;
                }
            });
    }
    
    /**
     * Returns the class-in-association of the given association whose
     * text phrase matches that given.
     */
    public static ClassInAssociation_c getClassInAssociation(
        Association_c assoc, final String phrase)
    {
        final String phraseWithoutTicks = (phrase.charAt(0) != '\'') ?
            phrase : phrase.substring(1, phrase.length() - 1);
        return ClassInAssociation_c.getOneR_OIROnR201(assoc,
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    String candidatePhrase =
                        ((ClassInAssociation_c)candidate).Get_text_phrase();
                    return candidatePhrase.equals(phraseWithoutTicks);
                }
            });
    }
    
    /**
     * Returns the model-class to which the given state-machine belongs.
     */
    public static ModelClass_c getClass(StateMachine_c machine) 
    {
        // if the given machine is an instance state machine
        ModelClass_c clazz = null;
        InstanceStateMachine_c instanceMachine =
            InstanceStateMachine_c.getOneSM_ISMOnR517(machine);
        if (instanceMachine != null) {
            // get the owning model-class from it
            clazz = ModelClass_c.getOneO_OBJOnR518(instanceMachine);
        }
        
        // otherwise, the given machine is a class state machine
        else {
            // get the owning model-class from it
            ClassStateMachine_c classMachine = 
                ClassStateMachine_c.getOneSM_ASMOnR517(machine);
            clazz = ModelClass_c.getOneO_OBJOnR519(classMachine);
        }
        
        return clazz;
    }
    
    /**
     * Returns the external-entity of the given name from the given model-root.
     */
    public static ExternalEntity_c getExternalEntity(Ooaofooa modelRoot, 
        final String name)
    {
        return ExternalEntity_c.ExternalEntityInstance(modelRoot, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((ExternalEntity_c)candidate).getName().equals(name);
                }
            });
    }
    
    /**
     * Returns (what is presumed to be) the only bridge of the given name, of 
     * the given external-entity.
     */
    public static Bridge_c getBridge(ExternalEntity_c externalEntity, 
        final String bridgeName)
    {
        return Bridge_c.getOneS_BRGOnR19(externalEntity, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Bridge_c)candidate).getName().equals(bridgeName);
                }
            });
    }
    
    /**
     * Returns the bridge-parameter of the given name from the given 
     * bridge.
     */
    public static BridgeParameter_c getBridgeParameter(
        Bridge_c bridge, final String parameterName)
    {
        return BridgeParameter_c.getOneS_BPARMOnR21(bridge, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((BridgeParameter_c)candidate).getName().equals(
                        parameterName);
                }
            });
    }

    /**
     * Returns (what is presumed to be) the only function of the given name,
     * of the given model-root.
     */
    public static Function_c getFunction(Ooaofooa modelRoot, final String name)
    {
        return Function_c.FunctionInstance(modelRoot, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Function_c)candidate).getName().equals(name);
                }
            });
    }
    
    /**
     * Returns the function-parameter of the given name from the given 
     * function.
     */
    public static FunctionParameter_c getFunctionParameter(
        Function_c function, final String parameterName)
    {
        return FunctionParameter_c.getOneS_SPARMOnR24(function, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((FunctionParameter_c)candidate).getName().equals(
                        parameterName);
                }
            });
    }
    
    /**
     * Returns the data-type of the given name from the given model-root.
     */
    public static DataType_c getDataType(Ooaofooa modelRoot, final String name)
    {
        return DataType_c.DataTypeInstance(modelRoot, 
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((DataType_c)candidate).getName().equals(name);
                }
            });
    }

    /**
     * Returns the enumerator of the given name, of the given data-type.
     */
    public static Enumerator_c getEnumerator(
        DataType_c dataType, final String name)
    {
        return Enumerator_c.getOneS_ENUMOnR27(
            EnumerationDataType_c.getOneS_EDTOnR17(dataType),
            new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate) {
                    return ((Enumerator_c)candidate).getName().equals(name);
                }
            });
    }

	/*
	 * Returns the system for a given element
	 */
	public static SystemModel_c getSystemForElement(NonRootModelElement element) {
		if (element instanceof PackageableElement_c) {
			Package_c pkg = Package_c.getOneEP_PKGOnR8000((PackageableElement_c)element);
			if (pkg != null) {
			  return SystemModel_c.getOneS_SYSOnR1405(pkg);
			}
			else { // could be a component container
				return getSystemForElement(PackageableElement_c.getOnePE_PEOnR8001(Component_c.getOneC_COnR8003((PackageableElement_c)element)));
			}
		}
		if (element instanceof Package_c) {
			  return SystemModel_c.getOneS_SYSOnR1405((Package_c)element);
		}
		if (element instanceof Component_c) {
			return SystemModel_c.getOneS_SYSOnR4606(ComponentPackage_c
					.getOneCP_CPOnR4608((Component_c) element));
		} else if (element instanceof ComponentPackage_c) {
			return SystemModel_c
					.getOneS_SYSOnR4606((ComponentPackage_c) element);
		} else if (element instanceof ComponentReference_c) {
			return SystemModel_c.getOneS_SYSOnR4606(ComponentPackage_c
					.getOneCP_CPOnR4608(Component_c
							.getOneC_COnR4201((ComponentReference_c) element)));
		} else if (element instanceof Domain_c) {
			return SystemModel_c.getOneS_SYSOnR28((Domain_c) element);
		} else if (element instanceof ComponentInstance_c) {
			Component_c component = Component_c
					.getOneC_COnR2955((ComponentInstance_c) element);
			if (component != null) {
				return getSystemForElement(component);
			} else {
				Domain_c domain = Domain_c
						.getOneS_DOMOnR2948((ComponentInstance_c) element);
				if (domain != null) {
					return getSystemForElement(domain);
				}
			}
		}
		return null;
	}
}
