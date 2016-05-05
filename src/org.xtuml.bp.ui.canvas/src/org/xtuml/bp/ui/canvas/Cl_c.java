package org.xtuml.bp.ui.canvas;
import java.lang.reflect.Field;
//=====================================================================
//
//File:      $RCSfile: Cl_c.java,v $
//Version:   $Revision: 1.84 $
//Modified:  $Date: 2013/01/17 03:33:47 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
//
//This class implements the realized code for the 'Client (CL)'
//External Entity.
//
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.AcceptEventAction_c;
import org.xtuml.bp.core.AcceptTimeEventAction_c;
import org.xtuml.bp.core.ActivityDiagramAction_c;
import org.xtuml.bp.core.ActivityEdge_c;
import org.xtuml.bp.core.ActivityFinalNode_c;
import org.xtuml.bp.core.ActivityPartition_c;
import org.xtuml.bp.core.ActorParticipant_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AsynchronousMessage_c;
import org.xtuml.bp.core.BinaryAssociation_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.ClassAsSubtype_c;
import org.xtuml.bp.core.ClassAsSupertype_c;
import org.xtuml.bp.core.ClassInAssociation_c;
import org.xtuml.bp.core.ClassInEngine_c;
import org.xtuml.bp.core.ClassInState_c;
import org.xtuml.bp.core.ClassInstanceParticipant_c;
import org.xtuml.bp.core.ClassMonitor_c;
import org.xtuml.bp.core.ClassParticipant_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CommunicationLink_c;
import org.xtuml.bp.core.ComponentParticipant_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.CoreDataType_c;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.DecisionMergeNode_c;
import org.xtuml.bp.core.Delegation_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Extend_c;
import org.xtuml.bp.core.ExternalEntityParticipant_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.FlowFinalNode_c;
import org.xtuml.bp.core.ForkJoinNode_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.Generalization_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.ImportedProvision_c;
import org.xtuml.bp.core.ImportedRequirement_c;
import org.xtuml.bp.core.Include_c;
import org.xtuml.bp.core.InitialNode_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Instance_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Lifespan_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Monitor_c;
import org.xtuml.bp.core.NewStateTransition_c;
import org.xtuml.bp.core.NoEventTransition_c;
import org.xtuml.bp.core.ObjectNode_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.PackageParticipant_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.ReferredToClassInAssoc_c;
import org.xtuml.bp.core.ReferringClassInAssoc_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.ReturnMessage_c;
import org.xtuml.bp.core.SendSignal_c;
import org.xtuml.bp.core.SimpleAssociation_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.SynchronousMessage_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TimeSpan_c;
import org.xtuml.bp.core.TimingMark_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.UseCaseParticipant_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.User_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ILogger;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.CoreUtil;
import org.xtuml.bp.core.util.HierarchyUtil;

// Client
public class Cl_c {
    private static int rightClickX=0;
    private static int rightClickY=0;
    private static boolean onSymbolFlag=true;
    public static String Getconnectortext(
        final int At,
        final UUID End_ooa_id,
        final boolean End_ooa_type,
        final Object From,
        final UUID parent) {
        	Class[] argTypes = new Class[4];
        	Object[] args = new Object[4];
	        argTypes[0] = UUID.class;
	        argTypes[1] = boolean.class;
	        argTypes[2] = int.class;
	        argTypes[3] = UUID.class;
	        args[0] = End_ooa_id;
	        args[1] = new Boolean(End_ooa_type);
	        args[2] = new Integer(At);
	        args[3] = parent;
	        return s_invoke(
	                From,
	                "Get_connector_text",
	                argTypes, args);
    }

	public static String Getconnectortext(final int At, final UUID End_ooa_id,
			final boolean End_ooa_type, final Object From, final UUID parent,
			final boolean connectedHidden) {
		Class[] argTypes = new Class[5];
		Object[] args = new Object[5];
		argTypes[0] = UUID.class;
		argTypes[1] = boolean.class;
		argTypes[2] = int.class;
		argTypes[3] = boolean.class;
		argTypes[4] = UUID.class;
		args[0] = End_ooa_id;
		args[1] = new Boolean(End_ooa_type);
		args[2] = new Integer(At);
		args[3] = connectedHidden;
		args[4] = parent;
		if(From == null) {
			return "";
		}
		Method hiddenSupportMethod = null;
		try {
			hiddenSupportMethod = From.getClass().getMethod("Get_connector_text", argTypes);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		if (hiddenSupportMethod == null) {
			return Getconnectortext(At, End_ooa_id, End_ooa_type, From, parent);
		}
		return s_invoke(From, "Get_connector_text", argTypes, args);
	}

    public static String Getcompartmenttext(
        final int At,
        final int Compartment_id,
        final int Entry_id,
        final Object From) {
        Class[] argTypes = new Class[3];
        argTypes[0] = int.class;
        argTypes[1] = int.class;
        argTypes[2] = int.class;
        Object[] args = new Object[3];
        args[0] = new Integer(At);
        args[1] = new Integer(Compartment_id);
        args[2] = new Integer(Entry_id);
        return s_invoke(
            From,
            "Get_compartment_text",
            argTypes, args);
    }
    public static void Setrightclickcoordinates (final int x , final int y)
    {
    	rightClickX=x;
    	rightClickY=y;
    }
    public static int Getrightclickx ()
    {
    	return rightClickX;
    }
    public static int Getrightclicky ()
    {
    	return rightClickY;
    }
    public static void Setonsymbol(final boolean onSymbol)
    {
    	onSymbolFlag=onSymbol;
    }
    public static boolean Isonsymbol()
    {
    	return onSymbolFlag;
    }
    public static String Getnamecompartmenttext(final Object instance) {
    	return s_invoke(instance, "Getnamecompartmenttext", null, null);
    }
    
    public static boolean Iscontainedinsystem(Object element, UUID system_id) {
    	if(element instanceof SystemModel_c) {
    		return ((SystemModel_c) element).getSys_id().equals(system_id);
    	}
    	if(element instanceof NonRootModelElement) {
    		if(((NonRootModelElement)element).getRoot() != null
    		    && ((NonRootModelElement)element).getRoot().
    		                                         Get_ooa_id().equals(system_id))
    			return true;
    	}
    	return false;
    }
    
    public static boolean Hasconnector(boolean matchingSpecs, UUID elementId,
    		                               Object instance, String methodName) {
        Class[] argTypes = new Class[2];
        argTypes[0] = boolean.class;
        argTypes[1] = UUID.class;
        Object[] args = new Object[2];
        args[0] = matchingSpecs;
        args[1] = elementId;
        return b_invoke(
            instance,
            methodName,
            argTypes, args);
    }
    
    public static String Getname(
        final Object From) {
        Class[] argTypes = new Class[0];
        Object[] args = new Object[0];
        String methodName = "getName";
        String result = "";
        // call hasMethod so an error is not
        // generated
        boolean foundMethod = hasMethod(From, methodName, argTypes);
        if(foundMethod) {
        	result = s_invoke(From,
        			methodName,
        			argTypes,
        			args);
        }
        else {
        	result = s_invoke(
            From,
            "getName",
        			argTypes,
        			args);
        }
          return result;
        }
    public static int Getcompartments(final Object From) {
        return i_invoke(From, "Get_compartments", new Class[0], new Object[0]);
    }
    public static UUID Getconnectorid(boolean elementTypesMatch,
    		                 final Object From, int index, final String Using) {
        Class[] argTypes = new Class[2];
        argTypes[0] = boolean.class;
        argTypes[1] = int.class;
        Object[] args = new Object[2];
        args[0] = new Boolean(elementTypesMatch);
        args[1] = new Integer(index);
    	
    	return uuid_invoke(From, Using, argTypes, args);
    }
    
	public static UUID Getthetargetconnectorid(final UUID Ooa_id, final int Ooa_type, final Object system) {
		UUID id = null;
		Object connector = Getinstancefromooa_id(Ooa_id, Ooa_type, system);
		Class[] argTypes = new Class[0];
		Object[] args = new Object[0];
		return uuid_invoke(connector, "Getconnectedid", argTypes, args);
	}

    public static UUID Getelementid(final Object From, int index, final String Using) {
        Class[] argTypes = new Class[1];
        argTypes[0] = int.class;
        Object[] args = new Object[1];
        args[0] = new Integer(index);
        
        return uuid_invoke(From, Using, argTypes, args);
    }
    
    public static Object Getelementinstance(final Object From, int index, final String Using) {
        Class[] argTypes = new Class[1];
        argTypes[0] = int.class;
        Object[] args = new Object[1];
        args[0] = new Integer(index);
    	
    	return invoke(From, Using, argTypes, args);
    }
    
    public static int Numconnectors(boolean elementTypesMatch,
    		                            final Object From, final String Using) {
        Class[] argTypes = new Class[1];
        argTypes[0] = boolean.class;
        Object[] args = new Object[1];
        args[0] = new Boolean(elementTypesMatch);

        return i_invoke(From, Using, argTypes, args);
    }
    public static int Numelements(final Object From, final String Using) {
        Class[] argTypes = null;
        Object[] args = null;
    	return i_invoke(From, Using, argTypes, args);
}
    public static boolean Isempty(final Object Element) {
        return Element == null;
    } // End isEmpty

    /**
     * Creates a path in the following form:
     * 
     * Element::Element::ParamElement
     * 
     * @param element - the represented client element
     */
	public static String Getpath(Object element) {
		return HierarchyUtil.Getpath(element);
	}

	public static Object Getooainstance(final UUID Ooa_id,
            final Object rootElement) {
		Class<Ooatype_c> ooaTypeClass = (Class<Ooatype_c>)Ooatype_c.class;
		Field[] fields = ooaTypeClass.getDeclaredFields();
		Object result = null;
		for (int i = 0; i < fields.length;i++) {
			String name = fields[i].getName();
			if (!name.equalsIgnoreCase("OOA_UNINITIALIZED_ENUM")) {
				Class cl = int.class;
				int value = -1;
				try {
					value = (Integer)fields[i].get(cl);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					CanvasPlugin.logError("Failed to find value for OOA_Type="+name, null);
				}
				result = Getinstancefromooa_id(Ooa_id, value
	                     , rootElement);
				if (result != null) {
					break;
				}
			}
		}
		return result;
	}
	public static Object Getinstancefromooa_id(
            SystemModel_c system,
            final UUID Ooa_id,
            final int Ooa_type) {
    		if (Ooa_type == Ooatype_c.Package) {
  				return Package_c.getOneEP_PKGOnR1401(system, new ClassQueryInterface_c() {
  					public boolean evaluate(Object candidate) {
  						Package_c pkg = (Package_c) candidate;
  						return pkg.Get_ooa_id().equals(Ooa_id);
  					}
  				});
  			} else {
  				return null;
  			}
    	}
    public static Object Getinstancefromooa_id(final UUID Ooa_id,
    		                     final int Ooa_type, final Object rootElement) {
    	if(rootElement instanceof SystemModel_c) {
    		return Getinstancefromooa_id((SystemModel_c) rootElement, Ooa_id, Ooa_type);
    	}
    	Ooaofooa root = null;
    	if(((NonRootModelElement)rootElement).getModelRoot() instanceof Ooaofgraphics)
    	  root = Ooaofooa.getInstance(((NonRootModelElement)rootElement).getModelRoot().getId());
    	else
    	  root = (Ooaofooa)((NonRootModelElement)rootElement).getModelRoot();
    	
    	return Getinstancefromooa_id(root, Ooa_id, Ooa_type);
    	
    }
    public static Object Getinstancefromooa_id(
        Ooaofooa modelRoot,
        final UUID Ooa_id,
        final int Ooa_type) {
    	if(Ooa_type == Ooatype_c.SystemModel) {
    		Object result = modelRoot.getInstanceList(SystemModel_c.class).get(Ooa_id);
            if (result == null) {
                class SystemModel_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((SystemModel_c) selected).getSys_id().equals(
                            Ooa_id);
                    }
                }
                result = SystemModel_c.SystemModelInstance(modelRoot,
                        new SystemModel_query_c());
            }
    		return result;
    	}
		else if (Ooa_type == Ooatype_c.Package) {
			Object result = modelRoot.getInstanceList(Package_c.class).get(Ooa_id);
            if (result == null) {
                class Package_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((Package_c) selected).getPackage_id().equals(
                            Ooa_id);
                    }
                }
                result = Package_c.PackageInstance(modelRoot,
                        new Package_query_c());
            }
    		return result;
	  	}    	
		else if(Ooa_type == Ooatype_c.ComponentParticipant) {
	    	Object result = modelRoot.getInstanceList(ComponentParticipant_c.class).get(Ooa_id);
	    	if(result == null) {
	    		result = ComponentParticipant_c.ComponentParticipantInstance(modelRoot, new ClassQueryInterface_c() {
				
					public boolean evaluate(Object candidate) {
						return ((ComponentParticipant_c) candidate).getPart_id().equals(Ooa_id);
					}
				
				});
	    	}
	    	return result;
	    }
		else if (Ooa_type == Ooatype_c.ComponentReference) {
			Object result = modelRoot.getInstanceList(ComponentReference_c.class).get(Ooa_id);
            if (result == null) {
                class UsedComponent_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((ComponentReference_c) selected).getId().equals(
                            Ooa_id);
                    }
                }
                result = ComponentReference_c.ComponentReferenceInstance(modelRoot,
                        new UsedComponent_query_c());
            }
    		return result;
	  	}
		else if (Ooa_type == Ooatype_c.ImportedRequiredInterface) {
			Object result = modelRoot.getInstanceList(ImportedRequirement_c.class).get(Ooa_id);
            if (result == null) {
                class UsedComponent_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((ImportedRequirement_c) selected).getId().equals(
                            Ooa_id);
                    }
                }
                result = ImportedRequirement_c.ImportedRequirementInstance(modelRoot,
                        new UsedComponent_query_c());
            }
    		return result;
	  	}
		else if (Ooa_type == Ooatype_c.ImportedProvidedInterface) {
			Object result = modelRoot.getInstanceList(ImportedProvision_c.class).get(Ooa_id);
            if (result == null) {
                class UsedComponent_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((ImportedProvision_c) selected).getId().equals(
                            Ooa_id);
                    }
                }
                result = ImportedProvision_c.ImportedProvisionInstance(modelRoot,
                        new UsedComponent_query_c());
            }
    		return result;
	  	}
    	else if (Ooa_type == Ooatype_c.RequiredInterface) {
    		Object result = modelRoot.getInstanceList(Requirement_c.class).get(Ooa_id);
            if (result == null) {
                class RequiredInterface_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((Requirement_c) selected).getRequirement_id().equals(
                            Ooa_id);
                    }
                }
                result = Requirement_c.RequirementInstance(modelRoot,
                        new RequiredInterface_query_c());
            }
    		return result;
	  	}
    	else if (Ooa_type == Ooatype_c.ProvidedInterface) {
    		Object result = modelRoot.getInstanceList(Provision_c.class).get(Ooa_id);
            if (result == null) {
                class ProvidedInterface_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((Provision_c) selected).getProvision_id().equals(
                            Ooa_id);
                    }
                }
                result = Provision_c.ProvisionInstance(modelRoot,
                        new ProvidedInterface_query_c());
            }
    		return result;
	  	}
    	else if (Ooa_type == Ooatype_c.Delegation) {
    		Object result = modelRoot.getInstanceList(Delegation_c.class).get(Ooa_id);
            if (result == null) {
                class ProvidedInterface_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((Delegation_c) selected).getId().equals(
                            Ooa_id);
                    }
                }
                result = Delegation_c.DelegationInstance(modelRoot,
                        new ProvidedInterface_query_c());
            }
    		return result;
	  	}
    	else if (Ooa_type == Ooatype_c.Interface) {
    		Object result = modelRoot.getInstanceList(Interface_c.class).get(Ooa_id);
            if (result == null) {
                class Interface_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((Interface_c) selected).getId().equals(
                            Ooa_id);
                    }
                }
                result = Interface_c.InterfaceInstance(modelRoot,
                        new Interface_query_c());
            }
    		return result;
	  	}
    	else if (Ooa_type == Ooatype_c.Component) {
    		Object result = modelRoot.getInstanceList(Component_c.class).get(Ooa_id);
            if (result == null) {
                class Component_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((Component_c) selected).getId().equals(
                            Ooa_id);
                    }
                }
                result = Component_c.ComponentInstance(modelRoot,
                        new Component_query_c());
            }
    		return result;
  		}
    	else if (Ooa_type == Ooatype_c.ComponentContainer) {
    		Object result = modelRoot.getInstanceList(Component_c.class).get(Ooa_id);
            if (result == null) {
                class Component_query_c implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((Component_c) selected).getId().equals(
                            Ooa_id);
                    }
                }
                result = Component_c.ComponentInstance(modelRoot,
                        new Component_query_c());
            }
    		return result;
  		}
    	else if (Ooa_type == Ooatype_c.EE) {
            Object result = modelRoot.getInstanceList(ExternalEntity_c.class).get(
                    Ooa_id);
            if (result == null) {
	            // select any represents from instances of S_EE where (selected.EE_ID == self.OOA_ID)
	            class ExternalEntity_test21_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((ExternalEntity_c) selected).getEe_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = ExternalEntity_c.ExternalEntityInstance(modelRoot,
	                    new ExternalEntity_test21_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Class) {
            Object result = modelRoot.getInstanceList(ModelClass_c.class).get(
                    Ooa_id);
            if (result == null) {
	            // select any represents from instances of O_OBJ where (selected.Obj_ID == self.OOA_ID)
	            class ModelClass_test21_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((ModelClass_c) selected).getObj_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = ModelClass_c.ModelClassInstance(modelRoot,
	                    new ModelClass_test21_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ImportedClass) {
            Object result = modelRoot.getInstanceList(ImportedClass_c.class).get(
                    Ooa_id);
            if (result == null) {
	            // select any represents from instances of O_IOBJ where (selected.IObj_ID == self.OOA_ID)
	            class ImportedClass_test21_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((ImportedClass_c) selected).getIobj_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = ImportedClass_c.ImportedClassInstance(modelRoot,
	                    new ImportedClass_test21_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Association) {
            Object result = modelRoot.getInstanceList(Association_c.class).get(
                    Ooa_id);
            if (result == null) {
	            // select any represents from instances of R_REL where (selected.Rel_ID == self.OOA_ID)
	            class Association_test21_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((Association_c) selected).getRel_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = Association_c.AssociationInstance(modelRoot,
	                    new Association_test21_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.StateMachine) {
            StateMachine_c sm = (StateMachine_c) modelRoot.getInstanceList(StateMachine_c.class).get(Ooa_id);
            Object result = InstanceStateMachine_c.getOneSM_ISMOnR517(sm);
            if (result == null) {
            	result = ClassStateMachine_c.getOneSM_ASMOnR517(sm);;
            }
            
            if (result == null) {
	            // select any represents from instances of SM_SM where (selected.SM_ID == self.OOA_ID)
	            class StateMachine_test22_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((StateMachine_c) selected).getSm_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            sm = StateMachine_c.StateMachineInstance(modelRoot,
	                                    new StateMachine_test22_c());
	            result = InstanceStateMachine_c.getOneSM_ISMOnR517(sm); 

	            if (result == null) {
	            	result = ClassStateMachine_c.getOneSM_ASMOnR517(sm);	            
	            }
            }

            return result;
        } else if (Ooa_type == Ooatype_c.State) {
            // select any represents from instances of SM_STATE where (selected.SMStt_ID == self.OOA_ID)
            class StateMachineState_test22_c implements ClassQueryInterface_c {
	            public boolean evaluate(Object selected) {
	                return ((StateMachineState_c) selected).getSmstt_id()
	                    .equals(Ooa_id);
	            }
	        }

            return StateMachineState_c.StateMachineStateInstance(modelRoot,
	                    new StateMachineState_test22_c());
        } else if (Ooa_type == Ooatype_c.Transition) {
            // select any represents from instances of SM_TXN where (selected.SMTxn_ID == self.OOA_ID)
            class Transition_test22_c implements ClassQueryInterface_c {
                public boolean evaluate(Object selected) {
                    return ((Transition_c) selected).getTrans_id().equals(
                        Ooa_id);
                }
            }
	
            return Transition_c.TransitionInstance(modelRoot,
	                    new Transition_test22_c());
        } else if (Ooa_type == Ooatype_c.CreationTransition) {
            // select any represents from instances of SM_TXN where (selected.SMTxn_ID == self.OOA_ID)
            class CreationTransition_test22_c implements ClassQueryInterface_c {
                public boolean evaluate(Object selected) {
                    return ((CreationTransition_c) selected).getTrans_id().equals(
                        Ooa_id);
                }
            }
	
            return CreationTransition_c.CreationTransitionInstance(modelRoot, new CreationTransition_test22_c());
        } else if (Ooa_type == Ooatype_c.EnumerationDataType) {
            Object result = modelRoot.getInstanceList(EnumerationDataType_c.class).get(
                    Ooa_id);
            if (result == null) {
	            // select any represents from instances of SM_TXN where (selected.SMTxn_ID == self.OOA_ID)
	            class EnumerationDataType_test31_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((EnumerationDataType_c) selected).getDt_id()
	                        .equals(Ooa_id);
	                }
	            }
	
	            result = EnumerationDataType_c.EnumerationDataTypeInstance(
	                modelRoot, new EnumerationDataType_test31_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ConstantSpecification) {
            Object result = modelRoot.getInstanceList(ConstantSpecification_c.class).get(
                    Ooa_id);
            if (result == null) {
                // select any represents from instances of SM_TXN where (selected.SMTxn_ID == self.OOA_ID)
                class ConstantSpecification_test31_c
                        implements ClassQueryInterface_c {
                    public boolean evaluate(Object selected) {
                        return ((ConstantSpecification_c) selected).getConstant_spec_id()
                            .equals(Ooa_id);
                    }
                }
    
                result = ConstantSpecification_c.ConstantSpecificationInstance(
                    modelRoot, new ConstantSpecification_test31_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.StructuredDataType) {
            Object result = modelRoot.getInstanceList(StructuredDataType_c.class).get(
                    Ooa_id);
            if (result == null) {
	            // select any represents from instances of S_SDT where (selected.DT_ID == self.OOA_ID)
	            class StructuredDataType_test31_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((StructuredDataType_c) selected).getDt_id()
	                        .equals(Ooa_id);
	                }
	            }
	
	            result = StructuredDataType_c.StructuredDataTypeInstance(
	                modelRoot, new StructuredDataType_test31_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Supertype) {
            Object result = modelRoot.getInstanceList(Association_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ClassAsSupertype_test26_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((Association_c) selected).getRel_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = Association_c.AssociationInstance(modelRoot,
	                    new ClassAsSupertype_test26_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Subtype) {
            class ClassAsSubtype_test27_c implements ClassQueryInterface_c {
                public boolean evaluate(Object selected) {
                    return ((ClassAsSubtype_c) selected).Get_ooa_id().equals(
                        Ooa_id);
                }
            }
	
            return ClassAsSubtype_c.ClassAsSubtypeInstance(modelRoot,
                    new ClassAsSubtype_test27_c());
        } else if (Ooa_type == Ooatype_c.AssociativeLink) {
        	Association_c assoc = (Association_c) modelRoot.getInstanceList(Association_c.class).get(Ooa_id);
            Object result = ClassAsLink_c.getOneR_ASSROnR211(LinkedAssociation_c.getOneR_ASSOCOnR206(assoc));
            if (result == null) {
	            class ClassAsLink_test28_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((ClassAsLink_c) selected).Get_ooa_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = ClassAsLink_c.ClassAsLinkInstance(modelRoot,
	                    new ClassAsLink_test28_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.CoreDataType) {
            Object result = modelRoot.getInstanceList(CoreDataType_c.class).get(
                    Ooa_id);
            if (result == null) {
	            // select any represents from instances of SM_TXN where (selected.SMTxn_ID == self.OOA_ID)
	            class CoreDataType_test29_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((CoreDataType_c) selected).getDt_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = CoreDataType_c.CoreDataTypeInstance(modelRoot,
	                    new CoreDataType_test29_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.UserDataType) {
            Object result = modelRoot.getInstanceList(UserDataType_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class UserDataType_test30_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((UserDataType_c) selected).getDt_id().equals(
	                        Ooa_id);
	                }
	            }
	
	            result = UserDataType_c.UserDataTypeInstance(modelRoot,
	                    new UserDataType_test30_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Lifeline) {
            Object result = modelRoot.getInstanceList(Lifespan_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class Lifeline_test_30_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object selected) {
	                    return ((Lifespan_c) selected).getPart_id().equals(Ooa_id);
	                }
	            }
	
	            result = Lifespan_c.LifespanInstance(modelRoot,
	                                               new Lifeline_test_30_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.TimingMark) {
            Object result = modelRoot.getInstanceList(TimingMark_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class TimingMark_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((TimingMark_c) candidate).getMark_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = TimingMark_c.TimingMarkInstance(modelRoot,
	                    new TimingMark_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.TimeSpan) {
            Object result = modelRoot.getInstanceList(TimeSpan_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class TimeSpan_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((TimeSpan_c) candidate).getSpan_id().equals(Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = TimeSpan_c.TimeSpanInstance(modelRoot,
	                                               new TimeSpan_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ClassInstanceParticipant) {
            Object result = modelRoot.getInstanceList(ClassInstanceParticipant_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ClassInstanceParticipant_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ClassInstanceParticipant_c) candidate).getPart_id()
	                            .equals(Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ClassInstanceParticipant_c.ClassInstanceParticipantInstance(
	                modelRoot, new ClassInstanceParticipant_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.SynchronousMessage) {
            Object result = modelRoot.getInstanceList(SynchronousMessage_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class SynchronousMessage_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((SynchronousMessage_c) candidate).getMsg_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = SynchronousMessage_c.SynchronousMessageInstance(modelRoot,
	                    new SynchronousMessage_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.AsynchronousMessage) {
            Object result = modelRoot.getInstanceList(AsynchronousMessage_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class AsynchronousMessage_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((AsynchronousMessage_c) candidate).getMsg_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = AsynchronousMessage_c.AsynchronousMessageInstance(
	                modelRoot, new AsynchronousMessage_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ReturnMessage) {
            Object result = modelRoot.getInstanceList(ReturnMessage_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ReturnMessage_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ReturnMessage_c) candidate).getMsg_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ReturnMessage_c.ReturnMessageInstance(modelRoot,
	                    new ReturnMessage_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.CommunicationSynchronousMessage) {
            Object result = modelRoot.getInstanceList(SynchronousMessage_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class CommunicationSynchronousMessage_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((SynchronousMessage_c) candidate).getMsg_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = SynchronousMessage_c.SynchronousMessageInstance(modelRoot,
	                    new CommunicationSynchronousMessage_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.CommunicationAsynchronousMessage) {
            Object result = modelRoot.getInstanceList(AsynchronousMessage_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class CommunicationAsynchronousMessage_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((AsynchronousMessage_c) candidate).getMsg_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = AsynchronousMessage_c.AsynchronousMessageInstance(
	                modelRoot, new CommunicationAsynchronousMessage_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.CommunicationReturnMessage) {
            Object result = modelRoot.getInstanceList(ReturnMessage_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class CommunicationReturnMessage_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ReturnMessage_c) candidate).getMsg_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ReturnMessage_c.ReturnMessageInstance(modelRoot,
	                    new CommunicationReturnMessage_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ClassParticipant) {
            Object result = modelRoot.getInstanceList(ClassParticipant_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ClassParticipant_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ClassParticipant_c) candidate).getPart_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ClassParticipant_c.ClassParticipantInstance(modelRoot,
	                    new ClassParticipant_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ExternalEntityParticipant) {
            Object result = modelRoot.getInstanceList(ExternalEntityParticipant_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ExternalEntityParticipant_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ExternalEntityParticipant_c) candidate).getPart_id()
	                            .equals(Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ExternalEntityParticipant_c
	                .ExternalEntityParticipantInstance(modelRoot,
	                    new ExternalEntityParticipant_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Actor) {
            Object result = modelRoot.getInstanceList(ActorParticipant_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class Actor_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ActorParticipant_c) candidate).getPart_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ActorParticipant_c.ActorParticipantInstance(modelRoot,
	                    new Actor_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.CommunicationLink) {
            Object result = modelRoot.getInstanceList(CommunicationLink_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class CommunicationLink_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((CommunicationLink_c) candidate).getLink_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = CommunicationLink_c.CommunicationLinkInstance(modelRoot,
	                    new CommunicationLink_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.UseCase) {
            Object result = modelRoot.getInstanceList(UseCaseParticipant_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class UseCase_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((UseCaseParticipant_c) candidate).getPart_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = UseCaseParticipant_c.UseCaseParticipantInstance(modelRoot,
	                    new UseCase_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.UseCaseBinaryAssociation) {
            Object result = modelRoot.getInstanceList(BinaryAssociation_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class UseCaseBinaryAssociation_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((BinaryAssociation_c) candidate).getAssoc_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = BinaryAssociation_c.BinaryAssociationInstance(modelRoot,
	                    new UseCaseBinaryAssociation_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Include) {
            Object result = modelRoot.getInstanceList(Include_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class Include_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((Include_c) candidate).getAssoc_id().equals(Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = Include_c.IncludeInstance(modelRoot, new Include_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Extend) {
            Object result = modelRoot.getInstanceList(Extend_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class Extend_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((Extend_c) candidate).getAssoc_id().equals(Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = Extend_c.ExtendInstance(modelRoot, new Extend_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Generalization) {
            Object result = modelRoot.getInstanceList(Generalization_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class Generalization_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((Generalization_c) candidate).getAssoc_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = Generalization_c.GeneralizationInstance(modelRoot,
	                    new Generalization_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.Partition) {
            Object result = modelRoot.getInstanceList(ActivityPartition_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class Partition_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ActivityPartition_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ActivityPartition_c.ActivityPartitionInstance(modelRoot,
	                    new Partition_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.InitialNode) {
            Object result = modelRoot.getInstanceList(InitialNode_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class InitialNode_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((InitialNode_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = InitialNode_c.InitialNodeInstance(modelRoot,
	                    new InitialNode_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ForkJoin) {
            Object result = modelRoot.getInstanceList(ForkJoinNode_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ForkJoin_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ForkJoinNode_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ForkJoinNode_c.ForkJoinNodeInstance(modelRoot,
	                    new ForkJoin_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ActivityEdge) {
            Object result = modelRoot.getInstanceList(ActivityEdge_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ActivityEdge_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ActivityEdge_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ActivityEdge_c.ActivityEdgeInstance(modelRoot,
	                    new ActivityEdge_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ActivityFinalNode) {
            Object result = modelRoot.getInstanceList(ActivityFinalNode_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ActivityFinalNode_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ActivityFinalNode_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ActivityFinalNode_c.ActivityFinalNodeInstance(modelRoot,
	                    new ActivityFinalNode_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.FlowFinalNode) {
            Object result = modelRoot.getInstanceList(FlowFinalNode_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class FlowFinalNode_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((FlowFinalNode_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = FlowFinalNode_c.FlowFinalNodeInstance(modelRoot,
	                    new FlowFinalNode_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.GenericAction) {
            Object result = modelRoot.getInstanceList(ActivityDiagramAction_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class GenericAction_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ActivityDiagramAction_c) candidate).Get_ooa_id()
	                            .equals(Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ActivityDiagramAction_c.ActivityDiagramActionInstance(
	                modelRoot, new GenericAction_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.DecisionMergeNode) {
            Object result = modelRoot.getInstanceList(DecisionMergeNode_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class DecisionMergeNode_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((DecisionMergeNode_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = DecisionMergeNode_c.DecisionMergeNodeInstance(modelRoot,
	                    new DecisionMergeNode_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.AcceptEventAction) {
            Object result = modelRoot.getInstanceList(AcceptEventAction_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class AcceptEventAction_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((AcceptEventAction_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = AcceptEventAction_c.AcceptEventActionInstance(modelRoot,
	                    new AcceptEventAction_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.AcceptTimeEventAction) {
            Object result = modelRoot.getInstanceList(AcceptTimeEventAction_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class AcceptTimeEventAction_Query_c
	                    implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((AcceptTimeEventAction_c) candidate).Get_ooa_id()
	                            .equals(Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = AcceptTimeEventAction_c.AcceptTimeEventActionInstance(
	                modelRoot, new AcceptTimeEventAction_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.SendSignalAction) {
            Object result = modelRoot.getInstanceList(SendSignal_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class SendSignalAction_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((SendSignal_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	            result = SendSignal_c.SendSignalInstance(modelRoot,
	            			new SendSignalAction_Query_c());
            }

            return result;
        } else if (Ooa_type == Ooatype_c.ObjectNode) {
            Object result = modelRoot.getInstanceList(ObjectNode_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class ObjectNode_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((ObjectNode_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = ObjectNode_c.ObjectNodeInstance(modelRoot,
	                    new ObjectNode_Query_c());
            }
            return result;
        } else if (Ooa_type == Ooatype_c.PackageParticipant) {
            Object result = modelRoot.getInstanceList(PackageParticipant_c.class).get(
                    Ooa_id);
            if (result == null) {
	            class PackageParticipant_Query_c implements ClassQueryInterface_c {
	                public boolean evaluate(Object candidate) {
	                    if (((PackageParticipant_c) candidate).Get_ooa_id().equals(
	                            Ooa_id)) {
	                        return true;
	                    }
	
	                    return false;
	                }
	            }
	
	            result = PackageParticipant_c.PackageParticipantInstance(modelRoot,
	                    new PackageParticipant_Query_c());
            }
            return result;
        } else {
            return null;
        }
    }    // End getInstanceFromOOA_ID


    public static int Getshapestyle(final Object From) {
        return i_invoke(From, "Get_style", new Class[0], new Object[0]);

    } // End getShapeStyle
     
    public static void select(final Object Element)
    {
    	Selection.getInstance().addToSelection(Element);
    }
    public static boolean Isselected(final Object Element) {
        return Selection.getInstance().contains(Element);
    } // End isSelected

    public static int Getconnectorstyle(final int At, final Object From) {
        Class[] argTypes = new Class[1];
        argTypes[0] = int.class;
        Object[] args = new Object[1];
        args[0] = new Integer(At);
        return i_invoke(From, "Get_style", argTypes, args);
    }
	public static UUID Getooa_idfrominstance(final Object Element) {
		Method getOoaId = findMethod(Element, "Get_ooa_id", new Class[0]);
		if (getOoaId != null) {
			Object result = doMethod(getOoaId, Element, new Object[0]);
			if (result != null) {
				return (UUID) result;
			}
		} else {
			System.err.println("get_ooa_id method not found for class "
					+ Element.getClass().getName());
		}
		return IdAssigner.MAX_UUID;
	} // End getOOA_IDFromInstance
	
	public static int Getentries(final int Compartment_id, final Object From) {
        Class[] argTypes = new Class[1];
        argTypes[0] = int.class;
        Object[] args = new Object[1];
        args[0] = new Integer(Compartment_id);
        return i_invoke(From, "Get_entries", argTypes, args);

    } // End getEntries

    public static int Gettextstyle(
        final int At,
        final int Compartment_id,
        final int Entry_id,
        final Object From) {
        Class[] argTypes = new Class[3];
        argTypes[0] = int.class;
        argTypes[1] = int.class;
        argTypes[2] = int.class;
        Object[] args = new Object[3];
        args[0] = new Integer(At);
        args[1] = new Integer(Compartment_id);
        args[2] = new Integer(Entry_id);
        return i_invoke(
            From,
            "Get_text_style",
            argTypes, args);
    }
    public static void Addtoselection(final Object Element) {
        if ( Element instanceof GraphicalElement_c ) {
            GraphicalElement_c ge = (GraphicalElement_c) Element;
            if(ge.getRepresents() == null) {
                Selection.getInstance().addToSelection(Element);
            } else {
                Selection.getInstance().addToSelection(ge.getRepresents());
            }
        } else {
            Selection.getInstance().addToSelection(Element);
        }
    } // End addToSelection

    public static void Removefromselection(final Object Element) {
        if ( Element instanceof GraphicalElement_c ) {
            GraphicalElement_c ge = (GraphicalElement_c) Element;
            if(ge.getRepresents() == null) {
                Selection.getInstance().removeFromSelection(Element);
            } else {
                Selection.getInstance().removeFromSelection(ge.getRepresents());
            }
        } else {
            Selection.getInstance().removeFromSelection(Element);
        }
    } // End removeFromSelection

    public static void Clearselection() {
        Selection.getInstance().clear();
    } // End clearSelection

    public static void Unselectall() {
    	Selection.getInstance().clear();
    } // End unSelectAll

    public static void Createnode(final Object On, final String Using) {
        invoke(On, Using, new Class[0], new Object[0]);
    } // End Createnode

    public static boolean Createconnector(
      final UUID From,
      final boolean Fromisimportedclass,
      final Object On,
      final UUID To,
      final boolean Toisimportedclass,
      final String Using) {
    	
        Class[] argTypes = new Class[4];
        argTypes[0] = UUID.class;
        argTypes[1] = boolean.class;
        argTypes[2] = UUID.class;
        argTypes[3] = boolean.class;
        
        Object[] args = new Object[4];
        args[0] = From;
        args[1] = new Boolean(Fromisimportedclass);
        args[2] = To;
        args[3] = new Boolean(Toisimportedclass);
    	
      return b_invoke(On, Using, argTypes, args);
    } // End Createconnector
    
	public static boolean Linkconnector(
	      final Object On,
	      final UUID To) {
	    	
	        Class[] argTypes = new Class[1];
	        argTypes[0] = UUID.class;
	        
	        Object[] args = new Object[1];
	        args[0] = To;
	    	
	      return b_invoke(On, "Linkconnector", argTypes, args);
    } // End Linkconnector
	
    public static UUID Getooaid(final Object From) {
        return uuid_invoke(From, "Get_ooa_id", new Class[0], new Object[0]);

    } // End getOOAId
    public static ModelRoot getModelRoot(Object from) {
  	
  	// we need the fully qualified class name here to distinguish
  	// from the same-named class in this package
  	
  	if ( from instanceof NonRootModelElement )
  	{
  		ModelRoot root = ((NonRootModelElement)from).getModelRoot();
	    return root;
  	}
  	else if ( from instanceof Ooaofooa )
  	{
	    return (ModelRoot)from;
  	}
    return null;
  }
  public static String getModelRootname(Object from) {
	ModelRoot root = getModelRoot(from);
    if (root != null) {
      return root.getId();
    }
    return null;
  }
  public static String Getmodelrootname(Object from) {
	  return Cl_c.getModelRootname(from);
  }
  //
  // invoke - invoke a method
  // result of invocation is used by caller
  //
  private static Object invoke(Object target, String methodName,
	    Class[] argTypes,
		Object[] args) {
    if (target != null) {
            Method method = findMethod (target, methodName, argTypes);
            if ( method != null )
            {
	        return doMethod( method, target, args);
            }
    }
    return null;
}
// End invoke
  //
  // b_invoke - invoke a method that returns boolean.
  //
  private static boolean b_invoke(
    Object target,
    String methodName,
    Class[] argTypes,
	Object[] args) {
    Object result = invoke( target, methodName, argTypes, args);
	        if ( result != null )
	        {
	        	return ((Boolean) result).booleanValue();
	        }
    return false;
  } // End b_invoke
  
//
// i_invoke - invoke a method that returns an int
//
private static int i_invoke(Object target, String methodName,
	    Class[] argTypes,
		Object[] args) {
	        Object result = invoke( target, methodName, argTypes, args);
	        if ( result != null )
	        {
	            return ((Integer) result).intValue();
	        }
 return 0;
} // End i_invoke
//
// uuid_invoke - invoke a method that returns a uuid
//
private static UUID uuid_invoke(
 Object target,
 String methodName,
    Class[] argTypes,
	Object[] args) {
    UUID result = (UUID)invoke( target, methodName, argTypes, args);
    return (result == null)?IdAssigner.NULL_UUID:result;
} // End uuid_invoke
//
// s_invoke - invoke a method that returns a string
//
private static String s_invoke(
		 Object target,
		 String methodName,
		    Class[] argTypes,
			Object[] args) {
		    Object result = invoke( target, methodName, argTypes, args);
		        if ( result != null )
		        {
		            return (String) result;
		        }
		        return "";
		      
}

	private static final String NOT_USED="";
	private static String[] errorReportingIgnoredFor = new String[] { "Getdescription", "getDescrip", NOT_USED, "Get_connector_tooltip"};

	public static Method findMethod(Object target, String methodName, Class[] argTypes) {
		Method result = null;
		try {

			if (!NOT_USED.equalsIgnoreCase(methodName)) {
				result = target.getClass().getMethod(methodName, argTypes);
			}
		} catch (NoSuchMethodException e) {
			// TODO BOB FIXME: I am ignoring all ClientNotFound operations right now because
			//                 OAL added in operations made inside GD_ARS.Reconcile are being made
			//                 against items that are not present. These are not fatal though, 
			//                 and they are causing too much "noise". This does need to be
			//                 put back into place and resolved by not making calls that are 
			//                 not needed, but right now attempts to do that cause this that
			//                 do need to be called to not get called and it is not worth chasing 
			//                 right now.
			
//			boolean shouldIgnore = false;
//			for (int i = 0; i < errorReportingIgnoredFor.length; i++) {
//				if (errorReportingIgnoredFor[i].equals(methodName)) {
//					shouldIgnore = true;
//					break;
//				}
//			}
//			
//			if (!shouldIgnore) {
//				String targetname = target.getClass().getName();
//				String argumentTypes = "";
//				for (int i = 0; (argTypes != null) && (i < argTypes.length); i++) {
//					if (i > 0) {
//						argumentTypes += ", ";
//					}
//					argumentTypes += argTypes[i].getSimpleName();
//				}
//				CanvasPlugin.logError("Client method not found. Target: " + targetname + "  OperationName: " + methodName + "  Argument(s): \"" + argumentTypes + "\"", e);
//			}
		}
		return result;
	}

	private static boolean hasMethod (Object target, String methodName, Class[] argTypes)
{
	boolean result = false;
	if (!NOT_USED.equalsIgnoreCase(methodName)) {
		result = CoreUtil.hasMethod(target, methodName, argTypes);
	}
	return result;
}
public static Object doMethod (Method m, Object target, Object[] args)
  {
    try {
        return m.invoke(target, args);
    } catch (IllegalAccessException e) {
        CanvasPlugin.logError(
                 "Client method, " + m.getName() + " cannot be accessed: ", e);
      } catch (InvocationTargetException e) {
        CanvasPlugin.logError(
                 "Client method, " + m.getName() + " is illegal target: ", e);
      }
    return null;
  }

public static Transaction Starttransaction(Object modelElement, String name){
	Transaction transaction = null;
	if (modelElement instanceof NonRootModelElement){
		// Now creating transaction
		TransactionManager transactionManager = 
			((NonRootModelElement)modelElement).getTransactionManager();
		
		try {
			transaction = transactionManager.startTransaction(name, new ModelRoot[] {Ooaofooa.getDefaultInstance(), Ooaofgraphics.getDefaultInstance()});
		} catch (TransactionException e) {
			if (transaction != null
						&& transactionManager != null
						&& transactionManager.getActiveTransaction() == transaction)
				transactionManager.cancelTransaction(transaction);
			CanvasPlugin.logError("Transaction '" + name + "' could not be started", e);
		}
	}	
	return transaction;
}

public static void Endtransaction (Object element, Transaction transaction){
	if (transaction != null && element instanceof NonRootModelElement){
		((NonRootModelElement)element).getTransactionManager().endTransaction(transaction);
	}
}

public static void Canceltransaction (Object element, Transaction transaction) {
	Canceltransaction(element, transaction, null);
}

public static void Canceltransaction (Object element, Transaction transaction, Exception exception){
	if (transaction != null && element instanceof NonRootModelElement){
		((NonRootModelElement)element).getTransactionManager().cancelTransaction(transaction, exception);
	}
}
public static boolean Iscomponentreadonly(Object object) {
	PersistableModelComponent component = PersistenceManager.getComponent((NonRootModelElement) object);
	if(component != null) {
		return component.isReadOnly(false);
	} else {
		return false;
	}
}
public static void Settoolbarstate(boolean readonly) {
	IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	IActionBars actionBars = activeEditor.getEditorSite().getActionBars();
	IContributionItem[] items = actionBars.getToolBarManager().getItems();
	boolean needsReset = false;
	int location = items.length - 1;
	if(items.length != 0) {
		if((items[location].isEnabled()) && (readonly)) {
			needsReset  = true;
		} else if ((!items[location].isEnabled()) && (!readonly)) {
			needsReset = true;
		}	
		if(needsReset) {
			activeEditor.getEditorSite().getActionBarContributor().setActiveEditor(activeEditor);
		}
	}
}

    public static int Getnumcompartmenticonslots(
        int compartmentNum, Object element)
    {
        // currently, only model-classes display icons
        if (!(element instanceof ModelClass_c)) return 0;
        
        // defer to the model-class
        return ((ModelClass_c)element).Getnumcompartmenticonslots(
            compartmentNum);
    }
    
    public static boolean Shouldcompartmenticonbedrawn(
        int compartmentNum, Object element, int slotNum)
    {
        // currently, only model-classes display icons
        if (!(element instanceof ModelClass_c)) return false;
        
        // defer to the model-class
        return ((ModelClass_c)element).Shouldcompartmenticonbedrawn(
            compartmentNum, slotNum);
    }
    
    public static String Getcompartmenticonname(
        int compartmentNum, Object element, int slotNum)
    {
        // since for now, the only icons that are displayed happen to represent
        // model elements, return the name of represented model element's class,
        // so that we use the same icon that is used elsewhere for the
        // model element's type
    	Object rep = Geticonrepresents(compartmentNum, element, slotNum);
    	if ( rep != null ) {
	        String fullName =  rep.getClass().getName();
	        String [] names = fullName.split("\\.");
	        return names[names.length-1];
    	}
    	return "";
    }

    /**
	 * Returns the model-element (if any) associated with the icon being
	 * displayed (or not) in the given icon-slot of the given compartment of the
	 * shape representing the given model-element.
	 * 
	 * Most of the code in the body of this method would normally be put into a
	 * Model Class operation of the same name, but no way was found to return a
	 * generic instance from an operation in Ooaofooa.
	 */
    public static Object Geticonrepresents(
        int compartmentNum, Object element, int slotNum)
    {
        // currently, only model-classes display icons
        if (!(element instanceof ModelClass_c)) return "";
        
        // if the first compartment is being considered (only the first
        // compartment is considered, since no icons are currently displayed
        // for later compartments)
        ModelClass_c clazz = (ModelClass_c)element;
        if (compartmentNum == 1) {
            // if the first icon slot is being considered
            if (slotNum == 0) {
                // return the instance state machine (if any) associated
                // with the model-class
                return InstanceStateMachine_c.getOneSM_ISMOnR518(clazz);
            }

            // else, if the second icon slot is being considered
            else if (slotNum == 1) {
                // return the class state machine (if any) associated
                // with the model-class
                return ClassStateMachine_c.getOneSM_ASMOnR519(clazz);
            }
        }

        return null;
    }
    
    public static boolean Getelidecompartmenttextatend(
        int compartmentNum, Object element, int entryNum)
    {
    	String methodName = "Getelidecompartmenttextatend"; //$NON-NLS-1$
    	
    	Class[] argTypes = {int.class, int.class};
    	Object[] args = {compartmentNum, entryNum};
    	
    	Method method = null;
		try {
			method = element.getClass().getMethod(methodName, argTypes);
		} catch (SecurityException e) {} 
		  catch (NoSuchMethodException e) {}
    	
    	if(method == null){
    		return true;
    	} else {
    		return b_invoke(element, methodName, argTypes, args);
    	}

    }
    public static boolean Ishighlighted(final Object Element) {
        // We need something more generic than this that will delegate to the client
        if (Element instanceof StateMachineState_c) {
      	StateMachineState_c  state = (StateMachineState_c)Element;
          Instance_c [] insts = Instance_c.getManyI_INSsOnR2915(state);
          Monitor_c mon = Monitor_c.getOneI_MONOnR2949(insts);
          if (mon != null) {
            return true;
          }
          ClassInEngine_c clazz = ClassInEngine_c.getOneCSME_CIEOnR2932(state);
          if (clazz != null) {
            ClassMonitor_c classMon = ClassMonitor_c.getOneCSME_CLMOnR2950(clazz);
          	return classMon != null;
          }
        }
        else if (Element instanceof Transition_c) {
        	Transition_c trans = (Transition_c)Element;
        	Instance_c [] insts = Instance_c.getManyI_INSsOnR2953(trans);
            Monitor_c mon = Monitor_c.getOneI_MONOnR2949(insts);
            if (mon != null) {
              return true;
            }
            ClassInState_c [] ciss = ClassInState_c.getManyCSME_CISsOnR2952(trans);
        	if (ciss.length > 0) {
        		return true;
        	}
        }
        return false;
  } // End ishighlighted
    
	public static boolean supportsCut(Object represents, Object child) {
		return supportsCopy(represents, child);
	}
	
	/**
	 * This operation controls enabling/disabling the copy CME.  By default
	 * copy IS allowed.  Copy is disabled if the model element that represents
	 * the currently open cavas contains an operation named canCopy<ME>() and that
	 * operation returns false.
	 * 
	 * For control of the paste CME see supportsPaste in this same class.
	 * 
	 * @see supportsPaste
	 * @param represents This object represent the currently open editor.
	 * @param child This object represents the selectin in the currently 
	 *              open editor.
	 * @return true if the copy CME should be enabled, and false if it should 
	 *              not be
	 */
	public static boolean supportsCopy(Object represents, Object child) {
		return CoreUtil.supportsCopy(represents, child);
	}
	
	/**
	 * This operation controls enabling/disabling the paste CME.  By default
	 * paste is NOT allowed.  Paste is enabled only if the model element that represents
	 * the currently open canvas contains an operation named paste<ME>().   Additionally,
	 * if the model element that represents the currently open canvas has an
	 * operation named canPaste<ME>() that operation shall be called and its
	 * determines is the paste CME is enabled or not.
	 * 
	 * For control of the copy CME see supportsCopy in this same class.
	 * 
	 * @see supportsPaste
	 * @param represents
	 * @param child
	 * @return
	 */
	public static boolean supportsPaste(Object represents, String child) {
		return CoreUtil.supportsPaste(represents, child, true);
	}
	
	public static void Logerror(String message) {
		User_c.Logerror(message,"PathNotProvided");
	}
	
	public static void Logexception(String message) {
		CanvasPlugin.logError(message, null);
	}
		
	public static void Logtracemsg(int Filteryype, String Filtervalue, String Message) {
		CanvasPlugin.logTraceMsg(Filteryype, Filtervalue, Message);
	}
		
	public static void Traceoperation(String msg) {
		Cl_c.Logtracemsg(ILogger.OPERATION, "", msg);		
	}
	
	public static boolean Connectorsupportslinking(Object connector) {
		return hasMethod(connector, "Linkconnector", new Class[] {UUID.class}); //$NON-NLS-1$
	}

	public static String Getconnectortooltipat(int end, Object represents) {
        Class<?>[] argTypes = new Class[1];
        argTypes[0] = int.class;
        Object[] args = new Object[1];
        args[0] = new Integer(end);
        return s_invoke(represents, "Get_connector_tooltip", argTypes, args);
	}

	public static boolean Tracegraphicscreationisenabled() {
		return CanvasPlugin.stringBufferLoggingIsEnabled();
	}
	
	public static void Writetracelog(String filename) {
		if (Tracegraphicscreationisenabled()) {
			CanvasPlugin.writeTraceLog(filename);
		}
	}	
	
	// This function gets the name of any NonRootModelElement
    public static String Getmodelelementname(final Object From) {
    	return s_invoke(From, "getName", null, null);
    }
    
    // This is really sort-of a hack around the ooag -> ooaofooa 
    // interface for graphics reconciliation which is
    // defined by GD_ARS. This is a special case for 
    // reflexives
    public static Boolean Isreflexive(final Object connectorInstance) {
    	boolean isReflexive = false;
    	if (connectorInstance instanceof Association_c) {
    		Association_c assoc = (Association_c)connectorInstance;
			ClassAsSimpleParticipant_c[] parts = ClassAsSimpleParticipant_c
					.getManyR_PARTsOnR207(SimpleAssociation_c.getOneR_SIMPOnR206(assoc));
			if (parts.length == 1) {
				// check for formalized reflexive
				ClassAsSimpleFormalizer_c form = ClassAsSimpleFormalizer_c
						.getOneR_FORMOnR208(SimpleAssociation_c.getOneR_SIMPOnR206(assoc));
				if (parts[0].getObj_id() ==form.getObj_id()) {
					isReflexive = true;
	  			}
			} else if (parts.length > 1) {
    			// check for unformalized reflexive
    			if (parts[0].getObj_id() == parts[1].getObj_id()) {
      			  isReflexive = true;
    			}
    		}
    	} else if (connectorInstance instanceof Transition_c) {
			Transition_c trans = (Transition_c) connectorInstance;
			StateMachineState_c smsDest = StateMachineState_c.getOneSM_STATEOnR506(trans);
			
			StateMachineState_c smsSourceNoEventTrans = StateMachineState_c
					.getOneSM_STATEOnR508(NoEventTransition_c.getOneSM_NETXNOnR507(trans));
			
			StateMachineState_c smsSourceNewStateTrans = StateMachineState_c.getOneSM_STATEOnR503(
					StateEventMatrixEntry_c.getOneSM_SEMEOnR504(NewStateTransition_c.getOneSM_NSTXNOnR507(trans)));
					
    		if ((smsDest != null) && (smsDest == smsSourceNoEventTrans || smsDest == smsSourceNewStateTrans)) {
    			isReflexive = true;
    		}
    	}
    	return isReflexive;
    }
    
    public static Boolean Isooalinkedassocinstance(final Object connectorInstance) {
    	Boolean result = false;
    	if (connectorInstance instanceof Association_c) {
    		LinkedAssociation_c linkedAssoc = LinkedAssociation_c.getOneR_ASSOCOnR206((Association_c)connectorInstance);    		
    		if (linkedAssoc != null) {
    			result = true;
    		}
    	}
    	return result;
    }
        
}// End Cl_c
