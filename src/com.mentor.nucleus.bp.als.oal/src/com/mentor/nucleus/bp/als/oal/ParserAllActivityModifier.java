
//=====================================================================
//
//File:      $RCSfile: AllActivityModifier.java,v $
//Version:   $Revision: 1.43 $
//Modified:  $Date: 2013/01/10 23:20:48 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.als.oal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.BodyInComponent_c;
import com.mentor.nucleus.bp.core.BodyInElement_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.BridgeBody_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EePackageInPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntityInPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.FunctionBody_c;
import com.mentor.nucleus.bp.core.FunctionInPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackageInPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Parsestatus_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SubsystemInSubsystem_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.IAllActivityModifier;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

public class ParserAllActivityModifier implements IAllActivityModifier
{
    private Function_c[] m_func_set;
    private Bridge_c[] m_bridge_set;
    private Subsystem_c [] m_ss_set;
    private Operation_c[] m_op_set;
    private Attribute_c[] m_mda_set;
    private StateMachineState_c[] m_ism_state_set;
    private StateMachineState_c[] m_csm_state_set;
    private RequiredSignal_c[] m_required_signals;
    private RequiredOperation_c[] m_required_operations;
    private ProvidedSignal_c[] m_provided_signals;
    private ProvidedOperation_c[] m_provided_operations;
    private Transition_c[] m_ism_transitions;
    private Transition_c[] m_csm_transitions;
    private Object[] activitiesToParse;
    private ModelRoot m_root = null;
    
    private boolean parseAll = false;
    private boolean setProvided = false;
    
    private NonRootModelElement m_parent = null;
    private NonRootModelElement m_domain = null;
    private PackageableElement_c m_pkgElem = null;
    private Package_c m_pkg = null;
    
    protected IProgressMonitor m_pm = null;
    
    private ParseRunnable parseRunner;
    
    public ParserAllActivityModifier(Domain_c parent, IProgressMonitor monitor){
    	this((NonRootModelElement)parent, monitor);
    	parseAll = true;
    }
    
    public ParserAllActivityModifier(Component_c parent, IProgressMonitor monitor){
    	this((NonRootModelElement)parent, monitor);
    	parseAll = true;
    }
    
    public ParserAllActivityModifier(Package_c parent, IProgressMonitor monitor){
    	this((NonRootModelElement)parent, monitor);
    	parseAll = true;
    }
    
    public ParserAllActivityModifier(Subsystem_c parent, IProgressMonitor monitor){
    	this((NonRootModelElement)parent, monitor);
    	parseAll = true;
    }
    
    public ParserAllActivityModifier(FunctionPackage_c parent, IProgressMonitor monitor){
    	this((NonRootModelElement)parent, monitor);
    	parseAll = true;
    }
    
    public ParserAllActivityModifier(ExternalEntityPackage_c parent, IProgressMonitor monitor){
    	this((NonRootModelElement)parent, monitor);
    	parseAll = true;
    }
    
    public ParserAllActivityModifier(ModelRoot modelRoot, Object[] activities, IProgressMonitor monitor)
    {
        m_pm = monitor;
        m_root = modelRoot;
        activitiesToParse = activities;
    }
    
    public ParserAllActivityModifier(NonRootModelElement parent, IProgressMonitor monitor)
    {
    	m_parent = parent;
    	if(parent instanceof Domain_c) {
    		m_domain = parent;
    	} else if (parent instanceof Component_c) {
    		m_domain = Domain_c.getOneS_DOMOnR4204(DomainAsComponent_c.getOneCN_DCOnR4204((Component_c) parent));
    		if (m_domain == null) {
    			m_pkgElem = PackageableElement_c.getOnePE_PEOnR8001((Component_c)parent);
    		}
    	} else if (parent instanceof Package_c) {
    		m_pkgElem = PackageableElement_c.getOnePE_PEOnR8001((Package_c)parent);
    		m_pkg = (Package_c) parent;
    	}
        m_pm = monitor;   
    }
    
	public void processAllActivities(int op) {
		processAllActivities(op, true);
	}
	
	public void processAllActivities(int op, boolean disposeBeforeParse)
	{
		processAllActivities(op, disposeBeforeParse, false);
	}

	/**
	 * @see initializePotentialSharedBodies(final NonRootModelElement nrme)
	 * @param op The operation to perform.  Valid options are defined in 
	 *           interface @IAllActivityModifier
	 * @param disposeBeforeParse When set to true all OAL in the model is deleted
	 *        before the specified operation is started
	 * @param includeSharedFragments This flag is used to parse OAL whose parent
	 *                              is in a package but NOT under a component
	 *                              (this OAL is referred to as fragments here).
	 *                              When this flag is true, the code will  
	 *                              start at the component instance (specified
	 *                              in the constructor), get it's parent 
	 *                              Package instance, and parse the 'fragments'   
	 *                              in this package as well as all 'fragments' 
	 *                              in all parent packages in the model 
	 *                              hierarchy.  When this is done, the recursive
	 *                              parse that starts at the specified element
	 *                              is then started.   This was done as part of 
	 *                              issue dts0100900268.
	 **/
	public void processAllActivities(int op, boolean disposeBeforeParse, boolean includeSharedFragments) {

        if ( op == PARSE ) {
        	ModelRoot mr = null;
        	if(m_parent != null)
        		mr = m_parent.getModelRoot();
        	else
        		mr = m_root; 
            PersistableModelComponent.ensureDataTypesAvailable(mr);
            if (disposeBeforeParse) {
            	// Dispose existing data before starting the parse
            	disposeAllBodies(mr);
            }
        }
        getAllSubsystems();
        if(activitiesToParse != null) {
        	if ( m_pm != null ) m_pm.beginTask(pmMessages[op], countAllActivities());
        	doForAll(activitiesToParse, op);
        } else {
        	// Clear the current search scope
    		resetAllPackages();
    		
        	if (includeSharedFragments) {
	        	Package_c originalPackage = m_pkg;
	        	Package_c currentPkg = m_pkg;
	        	
	        	/**
	        	 *  If this is a component, Navigate 'up' the package chain to 
	        	 *  get all system-level OAL in packages above this one.
	        	 */
	    		if (m_pkg == null && m_pkgElem != null) {
	    			Component_c comp = Component_c.getOneC_COnR8001(m_pkgElem);
	    			if (comp != null) {
	    				currentPkg = comp.getParentPackage();
	    			}
	    		}
	    		
	    		/**
	    		 * If we are parsing a package then we want to include the
	    		 * system-level OAL in packages ABOVE this package. The 
	    		 * system-level OAL in this package itself is parsed
	    		 * in the call to getAllActivities(false); 
	    		 */
	    		if (m_pkg != null) {
	    			currentPkg = m_pkg.getParentPackage();			
	    		}
	    		
	    		// Before starting the parse of just system-level OAL 
	    		// remove the m_pkgElem instance so it is not included.
        		PackageableElement_c oringalPkgElem = m_pkgElem;
        		m_pkgElem = null;
        		
    			while (currentPkg != null) {
    				m_pkg = currentPkg;
        	        getAllActivities(true);        		
        	        currentPkg = currentPkg.getParentPackage();
    			}
    	        m_pkg = originalPackage;
        		m_pkgElem = oringalPkgElem;    			
        	} 
    	    
        	getAllActivities(false);        		

	        if ( m_pm != null ) {
	        	m_pm.beginTask(pmMessages[op], countAllActivities());
				if (!m_pm.isCanceled()) doForAll(m_func_set, op);
				if (!m_pm.isCanceled()) doForAll(m_bridge_set, op);
				if (!m_pm.isCanceled()) doForAll(m_op_set, op);
				if (!m_pm.isCanceled()) doForAll(m_mda_set, op);
				if (!m_pm.isCanceled()) doForAll(m_ism_state_set, op);
				if (!m_pm.isCanceled()) doForAll(m_csm_state_set, op);
				if (!m_pm.isCanceled()) doForAll(m_ism_transitions, op);
				if (!m_pm.isCanceled()) doForAll(m_csm_transitions, op);
				if (!m_pm.isCanceled()) doForAll(m_provided_operations, op);
				if (!m_pm.isCanceled()) doForAll(m_provided_signals, op);
				if (!m_pm.isCanceled()) doForAll(m_required_operations, op);
				if (!m_pm.isCanceled()) doForAll(m_required_signals, op);
	        }
	        else {
				doForAll(m_func_set, op);
				doForAll(m_bridge_set, op);
				doForAll(m_op_set, op);
				doForAll(m_mda_set, op);
				doForAll(m_ism_state_set, op);
				doForAll(m_csm_state_set, op);
				doForAll(m_ism_transitions, op);
				doForAll(m_csm_transitions, op);
				doForAll(m_provided_operations, op);
				doForAll(m_provided_signals, op);
				doForAll(m_required_operations, op);
				doForAll(m_required_signals, op);
	        }
        }
        if ( m_pm != null ) m_pm.done();
	}
	
	public static void disposeAllBodies(ModelRoot mr) {            
			Body_c [] bodies = Body_c.BodyInstances(mr) ;
			for (int j = 0; j < bodies.length; j++) {
				bodies[j].Dispose();
			}
	}
	
    private void doForAll(Object [] activities, int op)
    {
        if (activities == null) return;
        
        for ( int i = 0; i < activities.length; ++i )
        {
            if ( op == PARSE )
                parseAction(activities[i]);
            else if ( op == CLEAR ) {
                // When running with no editors there is no placeholder.
            	// This comment is left here to call out that placeholder is 
            	// needed when there is an editor.
            	clearActionPlaceholder(activities[i]);
            }
            if (m_pm != null && m_pm.isCanceled()) {
            	break;
            }
        }
    }

    public void parseAction(Object modelElement)
    {
        int toParse = ParserAllActivityModifier.accessSuc_Pars( false, 0, modelElement );
        if ( toParse == Parsestatus_c.doNotParse )
        {
        	if (modelElement instanceof ProvidedOperation_c) {
        		((ProvidedOperation_c)modelElement).Initializeunparsed();
        	}
        	else if (modelElement instanceof RequiredOperation_c) {
        		((RequiredOperation_c)modelElement).Initializeunparsed();
        	}
        	else if (modelElement instanceof ProvidedSignal_c) {
        		((ProvidedSignal_c)modelElement).Initializeunparsed();
        	}
        	else if (modelElement instanceof RequiredSignal_c) {
        		((RequiredSignal_c)modelElement).Initializeunparsed();
        	}
            return;
        }

        /**
         * 
         * In the old code, in AllActivityModifier.java::parseAction()::367
    	 *  editorInput = (ActivityEditorInput)activityInputFactory.createInstance(modelElement);
         * that is where we get the "editor" for the given model element.  It 
         * is from this editor we then extract the OAL text.   In the version 
         * with no editor this is replaced with code that directly gets the 
         * OAL instead of getting it through the editor.  In the old code, this
         * call is handled by 
         * AbstractModelElementEditorInputFactory.java::createInstance().
         */
        
        // Get the string representation of the OAL for this 
        // model element.
       	String oalText = "";
       	if (modelElement instanceof Function_c) {
       		oalText = ((Function_c)modelElement).getAction_semantics();
       	} else if (modelElement instanceof Bridge_c) {
       		oalText = ((Bridge_c)modelElement).getAction_semantics();
       	} else if (modelElement instanceof Operation_c) {
       		oalText = ((Operation_c)modelElement).getAction_semantics();
       	} else if (modelElement instanceof Attribute_c) {
       		DerivedBaseAttribute_c dba = 
                    DerivedBaseAttribute_c.getOneO_DBATTROnR107(
                        BaseAttribute_c.getOneO_BATTROnR106((Attribute_c)modelElement));
       		if ( dba != null ) {
       			oalText = dba.getAction_semantics();
       		}
       	} else if (modelElement instanceof StateMachineState_c) {
       		Action_c act = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
     		          MooreActionHome_c.getOneSM_MOAHOnR511((StateMachineState_c)modelElement)));
       		if ( act != null ) {
       			oalText = act.getAction_semantics();
       		}
       	} else if (modelElement instanceof Transition_c) {
       		TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR530((Transition_c)modelElement);
            ActionHome_c ah = ActionHome_c.getOneSM_AHOnR513(tah);
            Action_c act = Action_c.getOneSM_ACTOnR514(ah);
   			oalText = act.getAction_semantics();
       	} else if (modelElement instanceof CreationTransition_c) {
       		Transition_c tr = Transition_c.getOneSM_TXNOnR507((CreationTransition_c)modelElement);
            TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR530(tr);
            ActionHome_c ah = ActionHome_c.getOneSM_AHOnR513(tah);
            Action_c act = Action_c.getOneSM_ACTOnR514(ah);
            oalText = act.getAction_semantics();
       	} else if (modelElement instanceof ProvidedOperation_c) {
       		oalText = ((ProvidedOperation_c)modelElement).getAction_semantics();
       	} else if (modelElement instanceof RequiredOperation_c) {
       		oalText = ((RequiredOperation_c)modelElement).getAction_semantics();
       	} else if (modelElement instanceof ProvidedSignal_c) {
       		oalText = ((ProvidedSignal_c)modelElement).getAction_semantics();
       	} else if (modelElement instanceof RequiredSignal_c) {
       		oalText = ((RequiredSignal_c)modelElement).getAction_semantics();
       	} else {
       		IllegalArgumentException ia = new IllegalArgumentException("AllActivityModifier::parseAction encountered an unexpeced type: " + modelElement.getClass().toString());
       		CorePlugin.logError("AllActivityModifier.parseAction:Unrecognized Action Home", ia);       		
       	}
		
        parseRunner = new ParseRunnable((NonRootModelElement)modelElement, oalText);
        parseRunner.run();
        
    }

    

	public static int accessSuc_Pars( boolean writeValue, int newValue, Object o_input )
    {
        if ( o_input instanceof Function_c )
        {
        	if ( writeValue )
                ((Function_c)o_input).setSuc_pars(newValue);
        	else
                return ((Function_c)o_input).getSuc_pars();
        }
        else if ( o_input instanceof Bridge_c )
        {
        	if ( writeValue )
        		((Bridge_c)o_input).setSuc_pars(newValue);
        	else
                return ((Bridge_c)o_input).getSuc_pars();
        }
        else if ( o_input instanceof Operation_c )
        {
        	if ( writeValue )
        		((Operation_c)o_input).setSuc_pars(newValue);
        	else
                return ((Operation_c)o_input).getSuc_pars();
        }
        else if ( o_input instanceof Attribute_c )
        {
            DerivedBaseAttribute_c dba = 
                DerivedBaseAttribute_c.getOneO_DBATTROnR107(
                    BaseAttribute_c.getOneO_BATTROnR106((Attribute_c)o_input));
 
        	if ( writeValue )
        		dba.setSuc_pars(newValue);
        	else
                return dba.getSuc_pars();
        }
        else if ( o_input instanceof StateMachineState_c )
        {
            
            MooreActionHome_c moah = MooreActionHome_c.getOneSM_MOAHOnR511((StateMachineState_c)o_input);
            ActionHome_c ah = ActionHome_c.getOneSM_AHOnR513(moah);
            Action_c act = Action_c.getOneSM_ACTOnR514(ah);
        	if ( writeValue )
        		act.setSuc_pars(newValue);
        	else
                return act.getSuc_pars();
        }
        else if ( o_input instanceof Transition_c )
        {
            
            TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR530((Transition_c)o_input);
            ActionHome_c ah = ActionHome_c.getOneSM_AHOnR513(tah);
            Action_c act = Action_c.getOneSM_ACTOnR514(ah);
        	if ( writeValue )
        		act.setSuc_pars(newValue);
        	else
                return act.getSuc_pars();
        }
        else if ( o_input instanceof CreationTransition_c )
        {
            Transition_c tr = Transition_c.getOneSM_TXNOnR507((CreationTransition_c)o_input);
            TransitionActionHome_c tah = TransitionActionHome_c.getOneSM_TAHOnR530(tr);
            ActionHome_c ah = ActionHome_c.getOneSM_AHOnR513(tah);
            Action_c act = Action_c.getOneSM_ACTOnR514(ah);
        	if ( writeValue )
        		act.setSuc_pars(newValue);
        	else
                return act.getSuc_pars();
        }
        else if(o_input instanceof ProvidedOperation_c) {
        	ProvidedOperation_c po = (ProvidedOperation_c) o_input;
        	if(writeValue) {
        		po.setSuc_pars(newValue);
        	} else
        		return po.getSuc_pars();
        }
        else if(o_input instanceof RequiredOperation_c) {
        	RequiredOperation_c ro = (RequiredOperation_c) o_input;
        	if(writeValue) {
        		ro.setSuc_pars(newValue);
        	} else
        		return ro.getSuc_pars();
        }    
        else if(o_input instanceof ProvidedSignal_c) {
        	ProvidedSignal_c ps = (ProvidedSignal_c) o_input;
        	if(writeValue) {
        		ps.setSuc_pars(newValue);
        	} else
        		return ps.getSuc_pars();
        }
        else if(o_input instanceof RequiredSignal_c) {
        	RequiredSignal_c rs = (RequiredSignal_c) o_input;
        	if(writeValue) {
        		rs.setSuc_pars(newValue);
        	} else
        		return rs.getSuc_pars();
        } 
        if (!writeValue) {
          IllegalArgumentException ia = new IllegalArgumentException("Unsupported Action Home:" + o_input.getClass().toString());
          CorePlugin.logError("AllActivityModifier.accessSucPar:Unrecognized Action Home", ia);
        }
        return Parsestatus_c.OOA_UNINITIALIZED_ENUM;
    }
    private void getAllActivities(boolean onlySharedFragments) {
        getAllFunctions(onlySharedFragments);
        getAllBridges(onlySharedFragments);
        if (!onlySharedFragments) {
	        getAllOperations();
	        getAllMDAttrs();
	        getAllStateActivities();
	        getAllTransitionActivities();
	       	getAllInterfaceOperations();
	       	getAllInterfaceSignals();
        }
    }
    private enum ActivityKind {BRIDGE, FUNCTION, MDA, OPERATION, PROVIDED_OP,
    	                        REQUIRED_OP, PROVIDED_SIGNAL, REQUIRED_SIGNAL,
                                        ISM_STATE, CSM_STATE, ISM_TXN, CSM_TXN};

	private void addActivitiesToList(ArrayList<NonRootModelElement> act_set,
			PackageableElement_c pkgElem, ActivityKind kind, Package_c rootPkg) {
		addActivitiesToList(act_set, pkgElem, kind, rootPkg, false);
	}

    private void addActivitiesToList(ArrayList<NonRootModelElement> act_set,
			                  PackageableElement_c pkgElem, ActivityKind kind,
                                                            Package_c rootPkg, boolean onlySharedFragments) {
		NonRootModelElement[] nrme_set = null;
		Package_c[] pkg_set = null;
		Component_c[] comp_set = null;
		Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
		Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
		if (rootPkg != null) {
			// The root package argument is used for system level packages that
			// do
			// not have a PackageableElement supertype. Setting this argument
			// therefore trumps the PackageableElement argument.
			pkg = rootPkg;
		}
		PackageableElement_c[] contents = null;
		if (pkg != null) {
			contents = PackageableElement_c.getManyPE_PEsOnR8000(pkg);
		} else if (comp != null) {
			contents = PackageableElement_c.getManyPE_PEsOnR8003(comp);
		} else {
			Throwable e = new Throwable();
			e.fillInStackTrace();
			String message = "Unsupported container in addActivitiesToList: ";
			CorePlugin.logError(message, e);
		}
		if (kind == ActivityKind.BRIDGE) {
			nrme_set = Bridge_c.getManyS_BRGsOnR19(ExternalEntity_c
					.getManyS_EEsOnR8001(contents));
		} else if (kind == ActivityKind.FUNCTION) {
			nrme_set = Function_c.getManyS_SYNCsOnR8001(contents);
		} else if (kind == ActivityKind.MDA) {
			nrme_set = Attribute_c
			    .getManyO_ATTRsOnR106(BaseAttribute_c
					.getManyO_BATTRsOnR107(DerivedBaseAttribute_c
							.getManyO_DBATTRsOnR107(BaseAttribute_c
									.getManyO_BATTRsOnR106(Attribute_c
											.getManyO_ATTRsOnR102(ModelClass_c
										   .getManyO_OBJsOnR8001(contents))))));
		} else if (kind == ActivityKind.OPERATION) {
			nrme_set = Operation_c.getManyO_TFRsOnR115(ModelClass_c
					                           .getManyO_OBJsOnR8001(contents));
		} else if (kind == ActivityKind.PROVIDED_OP) {
			nrme_set = ProvidedOperation_c
			    .getManySPR_POsOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
										     .getManyC_IRsOnR4016(Port_c
											     .getManyC_POsOnR4010(comp)))));
		} else if (kind == ActivityKind.REQUIRED_OP) {
			nrme_set = RequiredOperation_c
				.getManySPR_ROsOnR4502(RequiredExecutableProperty_c
					.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(InterfaceReference_c
								             .getManyC_IRsOnR4016(Port_c
									             .getManyC_POsOnR4010(comp)))));
		} else if (kind == ActivityKind.PROVIDED_SIGNAL) {
			nrme_set = ProvidedSignal_c
				.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
							.getManyC_PsOnR4009(InterfaceReference_c
									         .getManyC_IRsOnR4016(Port_c
											     .getManyC_POsOnR4010(comp)))));
		} else if (kind == ActivityKind.REQUIRED_SIGNAL) {
			nrme_set = RequiredSignal_c
				.getManySPR_RSsOnR4502(RequiredExecutableProperty_c
					.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(InterfaceReference_c
							                 .getManyC_IRsOnR4016(Port_c
								                 .getManyC_POsOnR4010(comp)))));
		} else if (kind == ActivityKind.ISM_STATE) {
			nrme_set = StateMachineState_c
					.getManySM_STATEsOnR501(StateMachine_c
							.getManySM_SMsOnR517(InstanceStateMachine_c
									.getManySM_ISMsOnR518(ModelClass_c
											 .getManyO_OBJsOnR8001(contents))));
		} else if (kind == ActivityKind.CSM_STATE) {
			nrme_set = StateMachineState_c
					.getManySM_STATEsOnR501(StateMachine_c
							.getManySM_SMsOnR517(ClassStateMachine_c
									.getManySM_ASMsOnR519(ModelClass_c
											 .getManyO_OBJsOnR8001(contents))));
		} else if (kind == ActivityKind.ISM_TXN) {
			nrme_set = Transition_c.getManySM_TXNsOnR505(StateMachine_c
					.getManySM_SMsOnR517(InstanceStateMachine_c
							    .getManySM_ISMsOnR518(ModelClass_c
									         .getManyO_OBJsOnR8001(contents))));
		} else if (kind == ActivityKind.CSM_TXN) {
			nrme_set = Transition_c.getManySM_TXNsOnR505(StateMachine_c
					.getManySM_SMsOnR517(ClassStateMachine_c
							  .getManySM_ASMsOnR519(ModelClass_c
									         .getManyO_OBJsOnR8001(contents))));
		} else {
			Throwable e = new Throwable();
			e.fillInStackTrace();
			CorePlugin.logError(
					"Unrecognized activity kind in addActivitiesToList", e);
		}
		for (int i = 0; i < nrme_set.length; ++i) {
			act_set.add(nrme_set[i]);
		}
		if (!onlySharedFragments) {
			pkg_set = Package_c.getManyEP_PKGsOnR8001(contents);
			comp_set = Component_c.getManyC_CsOnR8001(contents);
			for (int i = 0; i < pkg_set.length; ++i) {
				addActivitiesToList(act_set, PackageableElement_c
						.getOnePE_PEOnR8001(pkg_set[i]), kind, null);
			}
			for (int i = 0; i < comp_set.length; ++i) {
				addActivitiesToList(act_set, PackageableElement_c
						.getOnePE_PEOnR8001(comp_set[i]), kind, null);
			}
		}
	}

    private void getAllInterfaceSignals() {
    	m_required_signals = null;  // clear any existing entries
	    m_provided_signals = null;
        if(parseAll && m_domain != null && m_parent instanceof Component_c){
		  Component_c component = ((Component_c)m_parent);
		  m_required_signals = RequiredSignal_c
				.getManySPR_RSsOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component)))));
		  m_provided_signals = ProvidedSignal_c
				.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(Provision_c
								.getManyC_PsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component)))));
        }
        else if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            ArrayList<RequiredSignal_c> rs_set = new ArrayList<RequiredSignal_c>();
            addRequiredSignalsToList(rs_set, m_pkgElem);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem,
                                            ActivityKind.REQUIRED_SIGNAL, null);
          m_required_signals = act_set.toArray(
        		                          new RequiredSignal_c[act_set.size()]);
            assert(m_required_signals == rs_set.toArray(new RequiredSignal_c[rs_set.size()]));
            ArrayList<ProvidedSignal_c> pv_set = new ArrayList<ProvidedSignal_c>();
            addProvidedSignalsToList(pv_set, m_pkgElem);
          act_set.clear();
          addActivitiesToList(act_set, m_pkgElem,
                                            ActivityKind.PROVIDED_SIGNAL, null);
          m_provided_signals = act_set.toArray(
                                          new ProvidedSignal_c[act_set.size()]);
            assert(m_provided_signals == pv_set.toArray(new ProvidedSignal_c[pv_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
        	// isInGenericPackage == true
            ArrayList<RequiredSignal_c> rs_set = new ArrayList<RequiredSignal_c>();
            addRequiredSignalsToList(rs_set, m_pkg);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null,
                                           ActivityKind.REQUIRED_SIGNAL, m_pkg);
          m_required_signals = act_set.toArray(
        		                          new RequiredSignal_c[act_set.size()]);
            assert(m_required_signals == rs_set.toArray(new RequiredSignal_c[rs_set.size()]));
            ArrayList<ProvidedSignal_c> pv_set = new ArrayList<ProvidedSignal_c>();
            addProvidedSignalsToList(pv_set, m_pkg);
          act_set.clear();
          addActivitiesToList(act_set, null,
                                           ActivityKind.PROVIDED_SIGNAL, m_pkg);
          m_provided_signals = act_set.toArray(
                                          new ProvidedSignal_c[act_set.size()]);
            assert(m_provided_signals == pv_set.toArray( new ProvidedSignal_c[pv_set.size()]));
        }
	}

	private void getAllInterfaceOperations() {
		m_required_operations = null;
		m_provided_operations = null;
        if(parseAll && m_parent instanceof Component_c){
		  Component_c component = ((Component_c)m_parent);
		  m_required_operations = RequiredOperation_c
				.getManySPR_ROsOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component)))));
		  m_provided_operations = ProvidedOperation_c
				.getManySPR_POsOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(Provision_c
								.getManyC_PsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(component)))));
      }
      else if (parseAll && m_pkgElem != null) {
    	  // isInGenericPackage == true
          ArrayList<RequiredOperation_c> ro_set =
        	                               new ArrayList<RequiredOperation_c>();
          addRequiredOperationsToList(ro_set, m_pkgElem);
        ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
        addActivitiesToList(act_set, m_pkgElem, ActivityKind.REQUIRED_OP, null);
        m_required_operations = act_set.toArray(
        		                       new RequiredOperation_c[act_set.size()]);
    	  assert(m_required_operations == ro_set.toArray(new RequiredOperation_c[ro_set.size()]));
          ArrayList<ProvidedOperation_c> po_set =
        	                               new ArrayList<ProvidedOperation_c>();
          addProvidedOperationsToList(po_set, m_pkgElem);
        act_set.clear();
        addActivitiesToList(act_set, m_pkgElem, ActivityKind.PROVIDED_OP, null);
        m_provided_operations = act_set.toArray(
        		                       new ProvidedOperation_c[act_set.size()]);
    	  assert(m_provided_operations == po_set.toArray(new ProvidedOperation_c[po_set.size()]));
      }
      else if (parseAll && m_pkg != null) {
      	  // isInGenericPackage == true
          ArrayList<RequiredOperation_c> ro_set =
          	                               new ArrayList<RequiredOperation_c>();
          addRequiredOperationsToList(ro_set, m_pkg);
        ArrayList<NonRootModelElement> act_set =
                                             new ArrayList<NonRootModelElement>();
        addActivitiesToList(act_set, null, ActivityKind.REQUIRED_OP, m_pkg);
        m_required_operations = act_set.toArray(
          		                       new RequiredOperation_c[act_set.size()]);
      	  assert(m_required_operations == ro_set.toArray(new RequiredOperation_c[ro_set.size()]));
          ArrayList<ProvidedOperation_c> po_set =
          	                               new ArrayList<ProvidedOperation_c>();
          addProvidedOperationsToList(po_set, m_pkg);
        act_set.clear();
        addActivitiesToList(act_set, null, ActivityKind.PROVIDED_OP, m_pkg);
        m_provided_operations = act_set.toArray(
          		                       new ProvidedOperation_c[act_set.size()]);
      	  assert(m_provided_operations == po_set.toArray(new ProvidedOperation_c[po_set.size()]));
      }
	}
    private void addRequiredSignalsToList(ArrayList<RequiredSignal_c> rs_set, PackageableElement_c pkgElem) {
    	RequiredSignal_c [] r_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            r_set = RequiredSignal_c
			.getManySPR_RSsOnR4502(RequiredExecutableProperty_c
					.getManySPR_REPsOnR4500(Requirement_c
							.getManyC_RsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                r_set = RequiredSignal_c
				.getManySPR_RSsOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp)))));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < r_set.length; ++i ) {
            rs_set.add(r_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addRequiredSignalsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addRequiredSignalsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addRequiredSignalsToList(ArrayList<RequiredSignal_c> rs_set, Package_c pkg) {
    	RequiredSignal_c [] r_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
        	r_set = RequiredSignal_c
			.getManySPR_RSsOnR4502(RequiredExecutableProperty_c
					.getManySPR_REPsOnR4500(Requirement_c
							.getManyC_RsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < r_set.length; ++i ) {
            rs_set.add(r_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addRequiredSignalsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addRequiredSignalsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addProvidedSignalsToList(ArrayList<ProvidedSignal_c> pv_set, PackageableElement_c pkgElem) {
    	ProvidedSignal_c [] p_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            p_set = ProvidedSignal_c
			.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
							.getManyC_PsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                p_set = ProvidedSignal_c
				.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(Provision_c
								.getManyC_PsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp)))));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < p_set.length; ++i ) {
            pv_set.add(p_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addProvidedSignalsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addProvidedSignalsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addProvidedSignalsToList(ArrayList<ProvidedSignal_c> pv_set, Package_c pkg) {
    	ProvidedSignal_c [] p_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
        	p_set = ProvidedSignal_c
			.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
							.getManyC_PsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < p_set.length; ++i ) {
            pv_set.add(p_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addProvidedSignalsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addProvidedSignalsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addRequiredOperationsToList(ArrayList<RequiredOperation_c> rs_set, PackageableElement_c pkgElem) {
    	RequiredOperation_c [] r_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            r_set = RequiredOperation_c
			.getManySPR_ROsOnR4502(RequiredExecutableProperty_c
					.getManySPR_REPsOnR4500(Requirement_c
							.getManyC_RsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                r_set = RequiredOperation_c
				.getManySPR_ROsOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(Requirement_c
								.getManyC_RsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp)))));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < r_set.length; ++i ) {
            rs_set.add(r_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addRequiredOperationsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addRequiredOperationsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addRequiredOperationsToList(ArrayList<RequiredOperation_c> rs_set, Package_c pkg) {
    	RequiredOperation_c [] r_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
        	r_set = RequiredOperation_c
			.getManySPR_ROsOnR4502(RequiredExecutableProperty_c
					.getManySPR_REPsOnR4500(Requirement_c
							.getManyC_RsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < r_set.length; ++i ) {
            rs_set.add(r_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addRequiredOperationsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addRequiredOperationsToList(rs_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addProvidedOperationsToList(ArrayList<ProvidedOperation_c> pv_set, PackageableElement_c pkgElem) {
    	ProvidedOperation_c [] p_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            p_set = ProvidedOperation_c
			.getManySPR_POsOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
							.getManyC_PsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                p_set = ProvidedOperation_c
				.getManySPR_POsOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(Provision_c
								.getManyC_PsOnR4009(InterfaceReference_c
										.getManyC_IRsOnR4016(Port_c
												.getManyC_POsOnR4010(comp)))));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < p_set.length; ++i ) {
            pv_set.add(p_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addProvidedOperationsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addProvidedOperationsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addProvidedOperationsToList(ArrayList<ProvidedOperation_c> pv_set, Package_c pkg) {
    	ProvidedOperation_c [] p_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
        	p_set = ProvidedOperation_c
			.getManySPR_POsOnR4503(ProvidedExecutableProperty_c
					.getManySPR_PEPsOnR4501(Provision_c
							.getManyC_PsOnR4009(InterfaceReference_c
									.getManyC_IRsOnR4016(Port_c
											.getManyC_POsOnR4010(Component_c.getManyC_CsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < p_set.length; ++i ) {
            pv_set.add(p_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addProvidedOperationsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addProvidedOperationsToList(pv_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
	private int countAllActivities() {
    	if(activitiesToParse != null) {
    		return activitiesToParse.length;
    	}
        return ((m_func_set != null) ? m_func_set.length : 0)
         + ((m_bridge_set != null) ? m_bridge_set.length : 0)
         + ((m_op_set != null) ? m_op_set.length : 0)
         + ((m_mda_set != null) ? m_mda_set.length : 0)
         + ((m_ism_state_set != null) ? m_ism_state_set.length : 0)
         + ((m_csm_state_set != null) ? m_csm_state_set.length : 0)
         + ((m_provided_operations != null) ? m_provided_operations.length : 0)
         + ((m_required_operations != null) ? m_required_operations.length : 0)
         + ((m_provided_signals != null) ? m_provided_signals.length : 0)
         + ((m_required_signals != null) ? m_required_signals.length : 0);
    }

    private void resetAllPackages()
    {
        if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            resetPackagesBelow(m_pkgElem);
        }
        else if (parseAll && m_pkg != null) {
            // isInGenericPackage == true
            resetPackagesBelow(m_pkg);
        }
    }
    
    private void resetPackagesBelow(Package_c pkg) {
    	Package_c [] pkg_set = null;
        if (pkg != null) {
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	resetPackagesBelow(PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        	pkg_set[i].Clearscope();
        }
    }

    private void resetPackagesBelow(PackageableElement_c pkgElem) {
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp.Clearscope();
        	}
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	resetPackagesBelow(PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        	pkg_set[i].Clearscope();
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	resetPackagesBelow(PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        	comp_set[i].Clearscope();
        }
    }

    private void getAllFunctions(boolean onlySharedFragments)
    {
    	Function_c[] priorElements = m_func_set;
        if ( m_func_set != null )  m_func_set = null;  // clear any existing entries
        if(parseAll && m_domain != null){
            ArrayList function_set = new ArrayList();
            // navigating to all packages will cause them to be loaded
            FunctionPackage_c[] fpk_set = FunctionPackage_c.getManyS_FPKsOnR29((Domain_c) m_domain);
            addFunctionsToList(function_set, fpk_set);
            for ( int i = 0; i < fpk_set.length; ++i ) {
                loadChildFPKs(function_set, fpk_set[i]);
            }
        	m_func_set = (Function_c[])function_set.toArray(new Function_c[function_set.size()]);
        }
        else if (parseAll && m_pkgElem != null) {
            // isInGenericPackage == true
            ArrayList<Function_c> function_set = new ArrayList<Function_c>();
            addFunctionsToList(function_set, m_pkgElem);
          ArrayList<NonRootModelElement> act_set =
            	                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.FUNCTION, null);
          m_func_set = act_set.toArray(new Function_c[act_set.size()]);
            assert(m_func_set == function_set.toArray(new Function_c[function_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
            // isInGenericPackage == true
            ArrayList<Function_c> function_set = new ArrayList<Function_c>();
            addFunctionsToList(function_set, m_pkg, onlySharedFragments);
          ArrayList<NonRootModelElement> act_set =
        	                               new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.FUNCTION, m_pkg, onlySharedFragments);
          m_func_set = act_set.toArray(new Function_c[act_set.size()]);
            assert(m_func_set == function_set.toArray(new Function_c[function_set.size()]));
        }
        else if(m_parent instanceof FunctionPackage_c) {
            FunctionPackage_c[] top = {(FunctionPackage_c)m_parent};
            ArrayList<Function_c> function_set = new ArrayList<Function_c>();
            FunctionPackage_c[] fpk_set = FunctionPackage_c.getManyS_FPKsOnR30(FunctionPackageInPackage_c.getManyS_FPIPsOnR30(top));
            addFunctionsToList(function_set, top);
            for ( int i = 0; i < fpk_set.length; ++i ) {
                loadChildFPKs(function_set, fpk_set[i]);
            }
        	m_func_set = function_set.toArray(new Function_c[function_set.size()]);
        }else{
          m_func_set = (Function_c[])PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, Function_c.class);
        }
        
        if (priorElements != null) {
        	ArrayList<Function_c> result = new ArrayList<Function_c>(Arrays.asList(priorElements));
        	result.addAll(Arrays.asList(m_func_set));
        	m_func_set = result.toArray(new Function_c[result.size()]);
        }
    }

    private void addFunctionsToList(ArrayList function_set, FunctionPackage_c[] fpk_set) {
        Function_c [] f_set = Function_c.getManyS_SYNCsOnR31(FunctionInPackage_c.getManyS_FIPsOnR31(fpk_set));
        for ( int i = 0; i < f_set.length; ++i ) {
            function_set.add(f_set[i]);
        }
    }
    private void loadChildFPKs(ArrayList function_set, FunctionPackage_c fpk) {
        FunctionPackage_c[] child_fpk_set = FunctionPackage_c.getManyS_FPKsOnR32(FunctionPackageInPackage_c
                .getManyS_FPIPsOnR30(fpk));
        addFunctionsToList(function_set, child_fpk_set);
        for ( int i = 0; i < child_fpk_set.length; ++i ) {
            loadChildFPKs(function_set, child_fpk_set[i]);
        }
    }
    private void addFunctionsToList(ArrayList<Function_c> function_set, PackageableElement_c pkgElem) {
    	Function_c [] f_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            f_set = Function_c.getManyS_SYNCsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                f_set = Function_c.getManyS_SYNCsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8003(comp));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < f_set.length; ++i ) {
            function_set.add(f_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addFunctionsToList(function_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addFunctionsToList(function_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    
    
    private void addFunctionsToList(ArrayList<Function_c> function_set, Package_c pkg, boolean onlySharedFragments) {
    	Function_c [] f_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
            f_set = Function_c.getManyS_SYNCsOnR8001(
                                PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }        
        for ( int i = 0; i < f_set.length; ++i ) {
            function_set.add(f_set[i]);
        }
        if (!onlySharedFragments) {
	        for ( int i = 0; i < pkg_set.length; ++i ) {
	        	addFunctionsToList(function_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
	        }
	        for ( int i = 0; i < comp_set.length; ++i ) {
	        	addFunctionsToList(function_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
	        }
        }
    
    }
   private void getAllBridges(boolean onlySharedFragments)
    {
	   	Bridge_c[] priorElements = m_bridge_set;
        if ( m_bridge_set != null )  m_bridge_set = null;  // clear any existing entries
        if(parseAll && m_domain != null){
            ArrayList bridge_set = new ArrayList();
            // navigating to all packages will cause them to be loaded
            ExternalEntityPackage_c[] eepk_set = ExternalEntityPackage_c.getManyS_EEPKsOnR36((Domain_c) m_domain);
            addBridgesToList(bridge_set, eepk_set);
            for ( int i = 0; i < eepk_set.length; ++i ) {
                loadChildEEPKs(bridge_set, eepk_set[i]);
            }
            m_bridge_set = (Bridge_c[])bridge_set.toArray(new Bridge_c[bridge_set.size()]);
        }
        else if (parseAll && m_pkgElem != null) {
            // isInGenericPackage == true
            ArrayList<Bridge_c> bridge_set = new ArrayList<Bridge_c>();
            addBridgesToList(bridge_set, m_pkgElem);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.BRIDGE, null);
          m_bridge_set = act_set.toArray(new Bridge_c[act_set.size()]);
            assert(m_bridge_set == bridge_set.toArray(new Bridge_c[bridge_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
            // isInGenericPackage == true
            ArrayList<Bridge_c> bridge_set = new ArrayList<Bridge_c>();
            addBridgesToList(bridge_set, m_pkg, onlySharedFragments);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.BRIDGE, m_pkg, onlySharedFragments);
          
		  m_bridge_set = act_set.toArray(new Bridge_c[act_set.size()]);
            assert(m_bridge_set == bridge_set.toArray(new Bridge_c[bridge_set.size()]));
        }
        else if(m_parent instanceof ExternalEntityPackage_c) {
            ExternalEntityPackage_c[] top = {(ExternalEntityPackage_c)m_parent};
            ArrayList<Bridge_c> bridge_set = new ArrayList<Bridge_c>();
            ExternalEntityPackage_c[] eepk_set = ExternalEntityPackage_c.getManyS_EEPKsOnR33(ExternalEntityInPackage_c.getManyS_EEIPsOnR33(top));
            addBridgesToList(bridge_set, top);
            for ( int i = 0; i < eepk_set.length; ++i ) {
                loadChildEEPKs(bridge_set, eepk_set[i]);
            }
        	m_bridge_set = bridge_set.toArray(new Bridge_c[bridge_set.size()]);
        }else{
        	m_bridge_set = (Bridge_c[])PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, Bridge_c.class);
        }
        
        if (priorElements != null) {
        	ArrayList<Bridge_c> result = new ArrayList<Bridge_c>(Arrays.asList(priorElements));
        	result.addAll(Arrays.asList(m_bridge_set));
        	m_bridge_set = result.toArray(new Bridge_c[result.size()]);
        }
    }
   private void addBridgesToList(ArrayList bridge_set, ExternalEntityPackage_c[] eepk_set) {
       Bridge_c [] b_set = Bridge_c.getManyS_BRGsOnR19(
               ExternalEntity_c.getManyS_EEsOnR33(ExternalEntityInPackage_c.getManyS_EEIPsOnR33(eepk_set)));
       for ( int i = 0; i < b_set.length; ++i ) {
           bridge_set.add(b_set[i]);
       }
   }
    private void loadChildEEPKs(ArrayList bridge_set,ExternalEntityPackage_c eepk) {
        ExternalEntityPackage_c[] child_eepk_set = ExternalEntityPackage_c.getManyS_EEPKsOnR35(EePackageInPackage_c
                .getManyS_EEPIPsOnR34(eepk));
        addBridgesToList(bridge_set, child_eepk_set);
        for ( int i = 0; i < child_eepk_set.length; ++i ) {
            loadChildEEPKs(bridge_set, child_eepk_set[i]);
        }
    }
    private void addBridgesToList(ArrayList<Bridge_c> bridge_set, PackageableElement_c pkgElem) {
    	Bridge_c [] b_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            b_set = Bridge_c.getManyS_BRGsOnR19(
            		ExternalEntity_c.getManyS_EEsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                b_set = Bridge_c.getManyS_BRGsOnR19(
                		ExternalEntity_c.getManyS_EEsOnR8001(
                              PackageableElement_c.getManyPE_PEsOnR8003(comp)));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < b_set.length; ++i ) {
            bridge_set.add(b_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addBridgesToList(bridge_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addBridgesToList(bridge_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addBridgesToList(ArrayList<Bridge_c> bridge_set, Package_c pkg, boolean onlySharedFragments) {
    	Bridge_c [] b_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
            b_set = Bridge_c.getManyS_BRGsOnR19(
            		ExternalEntity_c.getManyS_EEsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < b_set.length; ++i ) {
            bridge_set.add(b_set[i]);
        }
        if (!onlySharedFragments) {
	        for ( int i = 0; i < pkg_set.length; ++i ) {
	        	addBridgesToList(bridge_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
	        }
	        for ( int i = 0; i < comp_set.length; ++i ) {
	        	addBridgesToList(bridge_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
	        }
        }
    }
    private void getAllSubsystems()
    {
        if ( m_ss_set != null )  m_ss_set = null;  // clear any existing entries
        if(parseAll && m_domain != null){
            ArrayList ss_result_set = new ArrayList();
            // navigating to all packages will cause them to be loaded
            Subsystem_c[] ss_set = Subsystem_c.getManyS_SSsOnR43((Domain_c) m_domain);
            for ( int i = 0; i < ss_set.length; ++i ) {
                ss_result_set.add(ss_set[i]);
                loadChildSSs(ss_result_set, ss_set[i]);
            }
            m_ss_set = (Subsystem_c[])ss_result_set.toArray(new Subsystem_c[ss_result_set.size()]);
        }else{
          if (m_parent instanceof Subsystem_c) {
              ArrayList<Subsystem_c> ss_result_set = new ArrayList<Subsystem_c>();
              ss_result_set.add((Subsystem_c)m_parent);
              Subsystem_c[] ss_set = Subsystem_c.getManyS_SSsOnR41(SubsystemInSubsystem_c.getManyS_SISsOnR41((Subsystem_c)m_parent));
              for ( int i = 0; i < ss_set.length; ++i ) {
                  ss_result_set.add(ss_set[i]);
                  loadChildSSs(ss_result_set, ss_set[i]);
              }
              m_ss_set = ss_result_set.toArray(new Subsystem_c[ss_result_set.size()]);
          }
        }
    }
    private void loadChildSSs(ArrayList ss_result_set, Subsystem_c ss) {
        Subsystem_c[] child_ss_set = Subsystem_c.getManyS_SSsOnR42(
                SubsystemInSubsystem_c.getManyS_SISsOnR41((Subsystem_c) ss));
        for ( int i = 0; i < child_ss_set.length; ++i ) {
            ss_result_set.add(child_ss_set[i]);
            loadChildSSs(ss_result_set, child_ss_set[i]);
        }
    }

    private void getAllOperations()
    {
        if ( m_op_set != null )  m_op_set = null;  // clear any existing entries
        if(parseAll && m_ss_set != null){
            m_op_set = Operation_c.getManyO_TFRsOnR115(
                    ModelClass_c.getManyO_OBJsOnR2(
                            m_ss_set));
        }
        else if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            ArrayList<Operation_c> op_set = new ArrayList<Operation_c>();
            addOperationsToList(op_set, m_pkgElem);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.OPERATION, null);
          m_op_set = act_set.toArray(new Operation_c[act_set.size()]);
            assert(m_op_set == op_set.toArray(new Operation_c[op_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
        	// isInGenericPackage == true
            ArrayList<Operation_c> op_set = new ArrayList<Operation_c>();
            addOperationsToList(op_set, m_pkg);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.OPERATION, m_pkg);
          m_op_set = act_set.toArray(new Operation_c[act_set.size()]);
            assert(m_op_set == op_set.toArray(new Operation_c[op_set.size()]));
        }else{
        	m_op_set = (Operation_c[])PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, Operation_c.class);
        }
    }
    private void addOperationsToList(ArrayList<Operation_c> op_set, PackageableElement_c pkgElem) {
    	Operation_c [] o_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            o_set = Operation_c.getManyO_TFRsOnR115(ModelClass_c.getManyO_OBJsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                o_set = Operation_c.getManyO_TFRsOnR115(
                		    ModelClass_c.getManyO_OBJsOnR8001(
                              PackageableElement_c.getManyPE_PEsOnR8003(comp)));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < o_set.length; ++i ) {
            op_set.add(o_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addOperationsToList(op_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addOperationsToList(op_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addOperationsToList(ArrayList<Operation_c> op_set, Package_c pkg) {
    	Operation_c [] o_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
            o_set = Operation_c.getManyO_TFRsOnR115(ModelClass_c.getManyO_OBJsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < o_set.length; ++i ) {
            op_set.add(o_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addOperationsToList(op_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addOperationsToList(op_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void getAllMDAttrs()
    {
        if ( m_mda_set != null )  m_mda_set = null;  // clear any existing entries
        if(parseAll && m_ss_set != null){
            m_mda_set = Attribute_c.getManyO_ATTRsOnR106(
                    BaseAttribute_c.getManyO_BATTRsOnR107(
                        DerivedBaseAttribute_c.getManyO_DBATTRsOnR107(
                            BaseAttribute_c.getManyO_BATTRsOnR106(
                                Attribute_c.getManyO_ATTRsOnR102(
                                    ModelClass_c.getManyO_OBJsOnR2(
                                            m_ss_set))))));
        }
        else if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            ArrayList<Attribute_c> attr_set = new ArrayList<Attribute_c>();
            addMDAsToList(attr_set, m_pkgElem);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.MDA, null);
          m_mda_set = act_set.toArray(new Attribute_c[act_set.size()]);
            assert(m_mda_set == attr_set.toArray(new Attribute_c[attr_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
        	// isInGenericPackage == true
            ArrayList<Attribute_c> attr_set = new ArrayList<Attribute_c>();
            addMDAsToList(attr_set, m_pkg);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.MDA, m_pkg);
          m_mda_set = act_set.toArray(new Attribute_c[act_set.size()]);
            assert(m_mda_set == attr_set.toArray(new Attribute_c[attr_set.size()]));
        }else{
        	DerivedBaseAttribute_c[] attrs = (DerivedBaseAttribute_c[])PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, DerivedBaseAttribute_c.class);
            m_mda_set = Attribute_c.getManyO_ATTRsOnR106(
                    BaseAttribute_c.getManyO_BATTRsOnR107(attrs));
        }
    }
    private void addMDAsToList(ArrayList<Attribute_c> attr_set, PackageableElement_c pkgElem) {
    	Attribute_c [] a_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
            a_set = Attribute_c.getManyO_ATTRsOnR106(
                    BaseAttribute_c.getManyO_BATTRsOnR107(
                            DerivedBaseAttribute_c.getManyO_DBATTRsOnR107(
                                BaseAttribute_c.getManyO_BATTRsOnR106(
                                    Attribute_c.getManyO_ATTRsOnR102(
                                        ModelClass_c.getManyO_OBJsOnR8001(
                           PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
                a_set = Attribute_c.getManyO_ATTRsOnR106(
                        BaseAttribute_c.getManyO_BATTRsOnR107(
                                DerivedBaseAttribute_c.getManyO_DBATTRsOnR107(
                                    BaseAttribute_c.getManyO_BATTRsOnR106(
                                        Attribute_c.getManyO_ATTRsOnR102(
                                            ModelClass_c.getManyO_OBJsOnR8001(
                          PackageableElement_c.getManyPE_PEsOnR8003(comp)))))));
                pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
                comp_set = Component_c.getManyC_CsOnR8001(
		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < a_set.length; ++i ) {
            attr_set.add(a_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addMDAsToList(attr_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addMDAsToList(attr_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void addMDAsToList(ArrayList<Attribute_c> attr_set, Package_c pkg) {
    	Attribute_c [] a_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
            a_set = Attribute_c.getManyO_ATTRsOnR106(
                    BaseAttribute_c.getManyO_BATTRsOnR107(
                            DerivedBaseAttribute_c.getManyO_DBATTRsOnR107(
                                BaseAttribute_c.getManyO_BATTRsOnR106(
                                    Attribute_c.getManyO_ATTRsOnR102(
                                        ModelClass_c.getManyO_OBJsOnR8001(
                           PackageableElement_c.getManyPE_PEsOnR8000(pkg)))))));
            pkg_set = Package_c.getManyEP_PKGsOnR8001(
            		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < a_set.length; ++i ) {
            attr_set.add(a_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addMDAsToList(attr_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]));
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addMDAsToList(attr_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]));
        }
    }
    private void getAllStateActivities()
    {
        if ( m_ism_state_set != null )  m_ism_state_set = null;  // clear any existing entries
        if(parseAll && m_ss_set != null){
            m_ism_state_set = StateMachineState_c.getManySM_STATEsOnR501(
                    StateMachine_c.getManySM_SMsOnR517(
                        InstanceStateMachine_c.getManySM_ISMsOnR518(
                            ModelClass_c.getManyO_OBJsOnR2(
                                    m_ss_set))));
        }
        else if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            ArrayList<StateMachineState_c> state_set = new ArrayList<StateMachineState_c>();
            addStatesToList(state_set, m_pkgElem, SMKind.instanceBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.ISM_STATE, null);
          m_ism_state_set = act_set.toArray(new StateMachineState_c[act_set.size()]);
            assert(m_ism_state_set == state_set.toArray(new StateMachineState_c[state_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
        	// isInGenericPackage == true
            ArrayList<StateMachineState_c> state_set = new ArrayList<StateMachineState_c>();
            addStatesToList(state_set, m_pkg, SMKind.instanceBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.ISM_STATE, m_pkg);
          m_ism_state_set = act_set.toArray(
        		                       new StateMachineState_c[act_set.size()]);
            assert(m_ism_state_set == state_set.toArray(new StateMachineState_c[state_set.size()]));
        }else{
            m_ism_state_set = (StateMachineState_c[]) PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, StateMachineState_c.class); 
        }

        if ( m_csm_state_set != null )  m_csm_state_set = null;  // clear any existing entries
        if(parseAll && m_ss_set != null){
            m_csm_state_set = StateMachineState_c.getManySM_STATEsOnR501(
                    StateMachine_c.getManySM_SMsOnR517(
                        ClassStateMachine_c.getManySM_ASMsOnR519(
                            ModelClass_c.getManyO_OBJsOnR2(
                                    m_ss_set))));
        }
        else if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            ArrayList<StateMachineState_c> state_set = new ArrayList<StateMachineState_c>();
            addStatesToList(state_set, m_pkgElem, SMKind.classBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.CSM_STATE, null);
          m_csm_state_set = act_set.toArray(
        		                       new StateMachineState_c[act_set.size()]);
        	assert(m_csm_state_set == state_set.toArray(new StateMachineState_c[state_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
        	// isInGenericPackage == true
            ArrayList<StateMachineState_c> state_set = new ArrayList<StateMachineState_c>();
            addStatesToList(state_set, m_pkg, SMKind.classBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.CSM_STATE, m_pkg);
          m_csm_state_set = act_set.toArray(
        		                       new StateMachineState_c[act_set.size()]);
        	assert(m_csm_state_set == state_set.toArray(new StateMachineState_c[state_set.size()]));
        }else{
            m_csm_state_set = (StateMachineState_c[]) PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, StateMachineState_c.class); 
        }
    }
    private enum SMKind {instanceBased, classBased};
    private void addStatesToList(ArrayList<StateMachineState_c> state_set, PackageableElement_c pkgElem, SMKind kind) {
    	StateMachineState_c [] st_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
          if (kind == SMKind.instanceBased) {
            st_set = StateMachineState_c.getManySM_STATEsOnR501(
                    StateMachine_c.getManySM_SMsOnR517(
                            InstanceStateMachine_c.getManySM_ISMsOnR518(
                                ModelClass_c.getManyO_OBJsOnR8001(
                             PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          else {
              st_set = StateMachineState_c.getManySM_STATEsOnR501(
                      StateMachine_c.getManySM_SMsOnR517(
                              ClassStateMachine_c.getManySM_ASMsOnR519(
                                  ModelClass_c.getManyO_OBJsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          pkg_set = Package_c.getManyEP_PKGsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
          comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
              if (kind == SMKind.instanceBased) {
                st_set = StateMachineState_c.getManySM_STATEsOnR501(
                        StateMachine_c.getManySM_SMsOnR517(
                                InstanceStateMachine_c.getManySM_ISMsOnR518(
                                    ModelClass_c.getManyO_OBJsOnR8001(
                            PackageableElement_c.getManyPE_PEsOnR8003(comp)))));
              }
              else {
                  st_set = StateMachineState_c.getManySM_STATEsOnR501(
                          StateMachine_c.getManySM_SMsOnR517(
                                  ClassStateMachine_c.getManySM_ASMsOnR519(
                                      ModelClass_c.getManyO_OBJsOnR8001(
                            PackageableElement_c.getManyPE_PEsOnR8003(comp)))));
              }
              pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
              comp_set = Component_c.getManyC_CsOnR8001(
	                           PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < st_set.length; ++i ) {
            state_set.add(st_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addStatesToList(state_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]), kind);
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addStatesToList(state_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]), kind);
        }
    }
    private void addStatesToList(ArrayList<StateMachineState_c> state_set, Package_c pkg, SMKind kind) {
    	StateMachineState_c [] st_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
          if (kind == SMKind.instanceBased) {
            st_set = StateMachineState_c.getManySM_STATEsOnR501(
                    StateMachine_c.getManySM_SMsOnR517(
                            InstanceStateMachine_c.getManySM_ISMsOnR518(
                                ModelClass_c.getManyO_OBJsOnR8001(
                             PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          else {
              st_set = StateMachineState_c.getManySM_STATEsOnR501(
                      StateMachine_c.getManySM_SMsOnR517(
                              ClassStateMachine_c.getManySM_ASMsOnR519(
                                  ModelClass_c.getManyO_OBJsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          pkg_set = Package_c.getManyEP_PKGsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
          comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < st_set.length; ++i ) {
            state_set.add(st_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addStatesToList(state_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]), kind);
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addStatesToList(state_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]), kind);
        }
    }
    private void getAllTransitionActivities()
    {
    	if (m_ism_transitions != null) m_ism_transitions = null;
    	if (parseAll && m_ss_set != null) {
            m_ism_transitions = Transition_c.getManySM_TXNsOnR505(
                    StateMachine_c.getManySM_SMsOnR517(
                        InstanceStateMachine_c.getManySM_ISMsOnR518(
                            ModelClass_c.getManyO_OBJsOnR2(
                                    m_ss_set))));
        }
        else if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            ArrayList<Transition_c> tran_set = new ArrayList<Transition_c>();
            addTransitionsToList(tran_set, m_pkgElem, SMKind.instanceBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.ISM_TXN, null);
          m_ism_transitions = act_set.toArray(new Transition_c[act_set.size()]);
        	assert(m_ism_transitions == tran_set.toArray(new Transition_c[tran_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
        	// isInGenericPackage == true
            ArrayList<Transition_c> tran_set = new ArrayList<Transition_c>();
            addTransitionsToList(tran_set, m_pkg, SMKind.instanceBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.ISM_TXN, m_pkg);
          m_ism_transitions = act_set.toArray(new Transition_c[act_set.size()]);
        	assert(m_ism_transitions == tran_set.toArray(new Transition_c[tran_set.size()]));
        }else{
            m_ism_transitions = (Transition_c[]) PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, Transition_c.class); 
    	}
    	if (m_csm_transitions != null) m_csm_transitions = null;
    	if (parseAll && m_ss_set != null) {
            m_csm_transitions = Transition_c.getManySM_TXNsOnR505(
                    StateMachine_c.getManySM_SMsOnR517(
                        ClassStateMachine_c.getManySM_ASMsOnR519(
                            ModelClass_c.getManyO_OBJsOnR2(
                                    m_ss_set))));
        }
        else if (parseAll && m_pkgElem != null) {
        	// isInGenericPackage == true
            ArrayList<Transition_c> tran_set = new ArrayList<Transition_c>();
            addTransitionsToList(tran_set, m_pkgElem, SMKind.classBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, m_pkgElem, ActivityKind.CSM_TXN, null);
          m_csm_transitions = act_set.toArray(new Transition_c[act_set.size()]);
        	assert(m_csm_transitions == tran_set.toArray(new Transition_c[tran_set.size()]));
        }
        else if (parseAll && m_pkg != null) {
        	// isInGenericPackage == true
            ArrayList<Transition_c> tran_set = new ArrayList<Transition_c>();
            addTransitionsToList(tran_set, m_pkg, SMKind.classBased);
          ArrayList<NonRootModelElement> act_set =
                                           new ArrayList<NonRootModelElement>();
          addActivitiesToList(act_set, null, ActivityKind.CSM_TXN, m_pkg);
          m_csm_transitions = act_set.toArray(new Transition_c[act_set.size()]);
        	assert(m_csm_transitions == tran_set.toArray(new Transition_c[tran_set.size()]));
        }else{
            m_csm_transitions = (Transition_c[]) PersistenceManager.getHierarchyMetaData().getActivityModelElements(m_parent, Transition_c.class); 
    	}
    }
    private void addTransitionsToList(ArrayList<Transition_c> tr_set, PackageableElement_c pkgElem, SMKind kind) {
    	Transition_c [] tran_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
       Package_c pkg = Package_c.getOneEP_PKGOnR8001(pkgElem);
        if (pkg != null) {
          if (kind == SMKind.instanceBased) {
            tran_set = Transition_c.getManySM_TXNsOnR505(
                    StateMachine_c.getManySM_SMsOnR517(
                            InstanceStateMachine_c.getManySM_ISMsOnR518(
                                ModelClass_c.getManyO_OBJsOnR8001(
                             PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          else {
              tran_set = Transition_c.getManySM_TXNsOnR505(
                      StateMachine_c.getManySM_SMsOnR517(
                              ClassStateMachine_c.getManySM_ASMsOnR519(
                                  ModelClass_c.getManyO_OBJsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          pkg_set = Package_c.getManyEP_PKGsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
          comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        else {
        	Component_c comp = Component_c.getOneC_COnR8001(pkgElem);
        	if (comp != null) {
              if (kind == SMKind.instanceBased) {
                tran_set = Transition_c.getManySM_TXNsOnR505(
                       StateMachine_c.getManySM_SMsOnR517(
                                InstanceStateMachine_c.getManySM_ISMsOnR518(
                                    ModelClass_c.getManyO_OBJsOnR8001(
                            PackageableElement_c.getManyPE_PEsOnR8003(comp)))));
              }
              else {
                tran_set = Transition_c.getManySM_TXNsOnR505(
                          StateMachine_c.getManySM_SMsOnR517(
                              ClassStateMachine_c.getManySM_ASMsOnR519(
                                       ModelClass_c.getManyO_OBJsOnR8001(
                            PackageableElement_c.getManyPE_PEsOnR8003(comp)))));
              }
              pkg_set = Package_c.getManyEP_PKGsOnR8001(
    		                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
              comp_set = Component_c.getManyC_CsOnR8001(
	                   PackageableElement_c.getManyPE_PEsOnR8003(comp));
        	}
        }
        for ( int i = 0; i < tran_set.length; ++i ) {
            tr_set.add(tran_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addTransitionsToList(tr_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]), kind);
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addTransitionsToList(tr_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]), kind);
        }
    }
    private void addTransitionsToList(ArrayList<Transition_c> tr_set, Package_c pkg, SMKind kind) {
    	Transition_c [] tran_set = null;
    	Package_c [] pkg_set = null;
    	Component_c [] comp_set = null;
        if (pkg != null) {
          if (kind == SMKind.instanceBased) {
            tran_set = Transition_c.getManySM_TXNsOnR505(
                    StateMachine_c.getManySM_SMsOnR517(
                            InstanceStateMachine_c.getManySM_ISMsOnR518(
                                ModelClass_c.getManyO_OBJsOnR8001(
                             PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          else {
              tran_set = Transition_c.getManySM_TXNsOnR505(
                      StateMachine_c.getManySM_SMsOnR517(
                              ClassStateMachine_c.getManySM_ASMsOnR519(
                                  ModelClass_c.getManyO_OBJsOnR8001(
                               PackageableElement_c.getManyPE_PEsOnR8000(pkg)))));
          }
          pkg_set = Package_c.getManyEP_PKGsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
          comp_set = Component_c.getManyC_CsOnR8001(
		            PackageableElement_c.getManyPE_PEsOnR8000(pkg));
        }
        for ( int i = 0; i < tran_set.length; ++i ) {
            tr_set.add(tran_set[i]);
        }
        for ( int i = 0; i < pkg_set.length; ++i ) {
        	addTransitionsToList(tr_set, PackageableElement_c.getOnePE_PEOnR8001(pkg_set[i]), kind);
        }
        for ( int i = 0; i < comp_set.length; ++i ) {
        	addTransitionsToList(tr_set, PackageableElement_c.getOnePE_PEOnR8001(comp_set[i]), kind);
        }
    }

	/**
	 * Initialize the given element body instances on R601.  
	 * 
	 */
   	public static  void initializeAllBodies(final NonRootModelElement nrme) {
		ModelRoot modelRoot = nrme.getModelRoot();
		ParserAllActivityModifier.initializeBodies(modelRoot, null);
	}

	/**
	 * Initialize the given element body instances on R601.  
	 * 
	 * @arg modelRoot The modelRoot we shall get all body instances from.
	 *                
	 * @arg nrme The element to parse. Note that this may be null, in which 
	 *           case all elements in the model root shall be parsed.
	 *      
	 */
    public static  void initializeBodies(final ModelRoot modelRoot, final NonRootModelElement nrme) {
        
        Body_c[] bodies = Body_c.BodyInstances(modelRoot);
        if (nrme != null) {
            if(nrme instanceof Component_c) {
              // initialize only bodies that are within
              // this component
              for (int j = 0; j < bodies.length; j++) {
                bodies[j].Associatewithowningcomponent();
                bodies[j].Associatewithcontainer();
              }
              bodies = Body_c.getManyACT_ACTsOnR694(BodyInComponent_c
                                    .getManyACT_BICsOnR694((Component_c) nrme));
              // Found across R694 is all the bodies for this specific
              // component. We need to recursively descend the component
              // containment hierarchy get them all.
              Component_c [] comps = Component_c.getManyC_CsOnR8001(
                      PackageableElement_c.getManyPE_PEsOnR8003((Component_c)nrme));
              for (int i=0; i < comps.length; i++) {
                  initializeBodies(modelRoot, comps[i]);
              }
              // Note that there is no need to recursively descend packages here
              // because R694 has all bodies, including those under packages.
            }
            else if (nrme instanceof Package_c) {
              PackageableElement_c pe = PackageableElement_c
                                            .getOnePE_PEOnR8001((Package_c)nrme);
              // initialize only bodies that are within
              // this package
              for (int j = 0; j < bodies.length; j++) {
                bodies[j].Associatewithcontainer();
              }
              bodies = Body_c.getManyACT_ACTsOnR640(BodyInElement_c
                                                    .getManyACT_BIEsOnR640(pe));
              // Recursively descend the component containment hierarchy
              Component_c [] comps = Component_c.getManyC_CsOnR8001(
                    PackageableElement_c.getManyPE_PEsOnR8000((Package_c)nrme));
              for (int i=0; i < comps.length; i++) {
                initializeBodies(modelRoot, comps[i]);
              }
              // recursively descend packages
              Package_c [] pkgs = Package_c.getManyEP_PKGsOnR8001(
                    PackageableElement_c.getManyPE_PEsOnR8000((Package_c)nrme));
              for (int i=0; i < pkgs.length; i++) {
                initializeBodies(modelRoot, pkgs[i]);
              }
            }
        }
        // When we get here, bodies either contains the bodies found under the
        // passed element, or all bodies for the model root if the passed
        // element was null. Either way, we need to initialize them.
        for (int m = 0; m < bodies.length; m++) {
            bodies[m].Initialize();
        }
    }
    
    /**
     * This routine is called to force body instances whose parent is in a package  
     * but NOT under a component to be initialized (connected across R601/R666).
     * This routine checks to see is the shared bodies are already connected
     * on R601/R666, and if they are if does not call body.initialize again
     * because body.initialize can only be called 1 time on each body instance
     * If called more then once, the instance end-up being disconnected because
     * body_c.initialize() unrelates the instance from R612/R650 as it relates
     * in on R601/R666.
	 *
     * @see processAllActivities(int op, boolean disposeBeforeParse, boolean includeSharedFragments)
     * @param nrme
     */
	public static void initializePotentialSharedBodies(
			final NonRootModelElement nrme) {
		if (nrme == null) {
			return;
		}
		Package_c pkg = nrme.getParentPackage();
		while (pkg != null) {
			Body_c[] bodies = Body_c.BodyInstances(pkg.getModelRoot());
            for (int j = 0; j < bodies.length; j++) {
                bodies[j].Associatewithcontainer();
            }			
			PackageableElement_c pe = PackageableElement_c
					.getOnePE_PEOnR8001(pkg);
			FunctionBody_c[] functionBodies = FunctionBody_c
					.getManyACT_FNBsOnR698(Body_c
							.getManyACT_ACTsOnR640(BodyInElement_c
									.getManyACT_BIEsOnR640(pe)));
			
			for (int i = 0; i < functionBodies.length; i++) {
				Body_c body = Body_c.getOneACT_ACTOnR698(functionBodies[i]);
				if (!body.Isinitialized()) {
					body.Initialize();
				}
			}

			BridgeBody_c[] bridgeBodies = BridgeBody_c
					.getManyACT_BRBsOnR698(Body_c
							.getManyACT_ACTsOnR640(BodyInElement_c
									.getManyACT_BIEsOnR640(pe)));
			for (int i = 0; i < bridgeBodies.length; i++) {
				Body_c body = Body_c.getOneACT_ACTOnR698(bridgeBodies[i]);
				if (!body.Isinitialized()) {
					body.Initialize();
				}
			}

			pkg = pkg.getParentPackage();
		}
	}

	@Override
	public void clearActionPlaceholder(Object o_input) 
	{
		// Noop in this implementation
		
	}
}
