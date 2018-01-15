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

package org.xtuml.bp.io.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredSelection;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.AsynchronousMessage_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BridgeParameter_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CoreDataType_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Dimensions_c;
import org.xtuml.bp.core.EclipseOoaofooa;
import org.xtuml.bp.core.ElementVisibility_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.End_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.EventArgument_c;
import org.xtuml.bp.core.EventSupplementalData_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.FunctionParameter_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.GlobalElementInSystem_c;
import org.xtuml.bp.core.ImportedProvisionInSatisfaction_c;
import org.xtuml.bp.core.ImportedProvision_c;
import org.xtuml.bp.core.ImportedReference_c;
import org.xtuml.bp.core.ImportedRequirement_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Message_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.OperationParameter_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.PortReference_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.SatisfactionInComponent_c;
import org.xtuml.bp.core.Satisfaction_c;
import org.xtuml.bp.core.SearchResultSet_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.StructureMember_c;
import org.xtuml.bp.core.SupplementalDataItems_c;
import org.xtuml.bp.core.SynchronousMessage_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TimeSpan_c;
import org.xtuml.bp.core.TimingMark_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.BaseModelDelta;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.InstanceList;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.actions.GenericPackageAssignComponentOnCL_ICAction;
import org.xtuml.bp.core.ui.actions.GenericPackageFormalizeOnC_PAction;
import org.xtuml.bp.core.ui.actions.GenericPackageFormalizeOnC_RAction;
import org.xtuml.bp.core.ui.actions.PullSynchronizationChanges;
import org.xtuml.bp.core.util.TransactionUtil;
import org.xtuml.bp.ui.canvas.AnchorOnSegment_c;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.ContainingShape_c;
import org.xtuml.bp.ui.canvas.Diagram_c;
import org.xtuml.bp.ui.canvas.Diagramelement_c;
import org.xtuml.bp.ui.canvas.ElementSpecification_c;
import org.xtuml.bp.ui.canvas.FloatingText_c;
import org.xtuml.bp.ui.canvas.Gr_c;
import org.xtuml.bp.ui.canvas.Graphconnector_c;
import org.xtuml.bp.ui.canvas.Graphedge_c;
import org.xtuml.bp.ui.canvas.Graphelement_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Graphnode_c;
import org.xtuml.bp.ui.canvas.LineSegment_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.NoncontainingShape_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.Waypoint_c;
import org.xtuml.bp.ui.graphics.commands.CreateConnectionCommand;

/**
 * Holds Java-only methods that were previously specified as part of
 * gen_import_java.inc.  An instance of this class is created by an instance of
 * one of the CoreImport subclasses to avail itself of the Java-only functionality
 * contained herein.
 */
public class ImportHelper
{
    /**
     * The CoreImport subclass instance this helper is assigned to assist.
     */
    private CoreImport importer;

    /**
     * These maps facilitate data upgrade for requirements and provisions from
     * 1.4.2 to 1.5.x and beyond
     */
    static public class C_P {
        public C_P( UUID id, UUID cid, UUID iid, UUID did, String n,
                String in, String d) {
            this.Provision_Id = id;
            this.Component_Id = cid;
            this.Interface_Id = iid;
            this.Diagram_Id = did;
            this.Name = n;
            this.InformalName = in;
            this.Descrip = d;
        }
        UUID Provision_Id;
        UUID Component_Id;
        UUID Interface_Id;
        UUID Diagram_Id;
        String Name;
        String InformalName;
        String Descrip;
    }
    public static Vector<C_P> old_provisions = new Vector<C_P>();

    static public class C_R {
        public C_R( UUID id, UUID cid, UUID iid, UUID did, String n, String d,
                String in) {
            this.Requirement_Id = id;
            this.Component_Id = cid;
            this.Interface_Id = iid;
            this.Diagram_Id = did;
            this.Name = n;
            this.Descrip = d;
            this.InformalName = in;
        }
        UUID Requirement_Id;
        UUID Component_Id;
        UUID Interface_Id;
        UUID Diagram_Id;
        String Name;
        String Descrip;
        String InformalName;
    }
    public static Vector<C_R> old_requirements = new Vector<C_R>();

    /**
     * This class and vector is used to upgrade C_C (Component)
     */
    static public class C_C {
        public C_C( UUID id, UUID did, String n, String d, Component_c pInst) {
            this.Id = id;
            this.Diagram_Id = did;
            this.Name = n;
            this.Descrip = d;
            this.Inst = pInst;
        }
        UUID Id;
        UUID Diagram_Id;
        String Name;
        String Descrip;
        Component_c Inst;
    }
    public Vector<C_C> old_components = new Vector<C_C>();

    /**
     * This class and vector is used to upgrade CD_IC (Used Component) to
     * CL_IC (Imported Component)
     */
    static public class CD_IC {
        public CD_IC( UUID id, UUID did, UUID cid, int m, String cn, String d, Ooaofooa modelRoot) {
            this.Id = id;
            this.Diagram_Id = did;
            this.Component_Id = cid;
            this.Mult = m;
            this.ClassifierName = cn;
            this.Descrip = d;
            this.ModelRoot = modelRoot;
        }
        UUID Id;
        UUID Diagram_Id;
        UUID Component_Id;
        int Mult;
        String ClassifierName;
        String Descrip;
        Ooaofooa ModelRoot;
    }
    public static Vector<CD_IC> used_components = new Vector<CD_IC>();

    /**
     * This class and vector stores old "Interface in Diagram" (CD_IID)
     * class info that is used to migrate old CD_ICs to CL_ICs
     */
    static public class CD_IID {
        public CD_IID( UUID id, UUID cid, UUID eid) {
            this.Id = id;
            this.Component_Id = cid;
            this.Element_Id = eid;
        }
        UUID Id;
        UUID Component_Id;
        UUID Element_Id;
    }
    public static Vector<CD_IID> interface_in_diagrams = new Vector<CD_IID>();

    /**
     * This class and vector stores old "Component in Diagram" (CD_CID) classes
     */
    static public class CD_CID {
        public CD_CID( UUID id, UUID eid) {
            this.Id = id;
            this.Element_Id = eid;
        }
        UUID Id;
        UUID Element_Id;
    }
    public static Vector<CD_CID> component_in_diagrams = new Vector<CD_CID>();

    /**
     * This class and vector stores old "Component Diagram Element" (CD_CDE)
     * classes
     */
    static public class CD_CDE {
        public CD_CDE( UUID eid, UUID id) {
            this.Element_Id = eid;
            this.Id = id;
        }
        UUID Element_Id;
        UUID Id;
    }
    public static Vector<CD_CDE> component_diagram_elements = new Vector<CD_CDE>();

    /**
     * Constructor.
     */
    public ImportHelper(CoreImport importer)
    {
        this.importer = importer;
    }

    /**
     * A convenience method.  See method wrapped.
     */
    protected Ooaofooa getModelRoot()
    {
        return importer.getModelRoot();
    }

    /**
     * A convenience method.  See method wrapped.
     */
    protected Ooaofgraphics getGraphicsModelRoot()
    {
        return importer.getGraphicsModelRoot();
    }

    // If a masl model was import then one of the following 2
    // elements will not be null
    private Package_c m_maslProjectImported = null;
    private Package_c m_maslDomainsImported = null;
    
    
    // m2x writes the following strings into description fields to flag that 
    // is a masl model being imported.
    private final String MASL_PROJECT = "masl_project";
    private final String MASL_DOMAIN = "masl_domain";
    private final String MASL_FORMALIZE = "masl_formalize";
    private final String MASL_TEMPORARY_INTERFACE = "masl_temporary";
    
    /**
     * Resolve the MASL project
     *   - Assign unassigned component references
     *   - Unhook temporary interface definitions and re-attach to existing interfaces in domains
     *   - create satisfactions
     */
    public void resolveMASLproject( SystemModel_c system, NonRootModelElement[] elements ) {    	
        if ( elements == null ) return;
        
        boolean transaction_aborted = false;
        Selection.getInstance().setSelection(new StructuredSelection(system));
		TransactionUtil.TransactionGroup transactionGroup = TransactionUtil
				.startTransactionsOnSelectedModelRoots("Import MASL");
		Ooaofooa.beginSaveOperation();
		try {
	        // first pass, assign component references and swap interfaces, formalize interfaces
	        for ( NonRootModelElement el : elements ) {
	            if ( el instanceof ComponentReference_c ) {
	            	resolveMASLComponentReference((ComponentReference_c)el);
	            } else if ( el instanceof Interface_c ) {
	            	resolveMASLTemporaryInterface((Interface_c)el);
	            } else if ( el instanceof Requirement_c ) {
	            	resolveMASLRequirement((Requirement_c)el);
	            } else if ( el instanceof Provision_c ) {
	            	resolveMASLProvision((Provision_c) el);
	            } else if (m_maslProjectImported==null && m_maslDomainsImported==null && (el instanceof Package_c)) {
	            	// If any package is marked as a masl project then
	            	// set the flag indicating a masl model was imported
	            	
	        		String descrip = ((Package_c)el).getDescrip();
	        		if (!descrip.isEmpty()) {
	        			Matcher m = Pattern.compile(MASL_PROJECT).matcher(descrip);
	        			if (m.find()) {
	        				m_maslProjectImported = (Package_c)el;
	        			} else {
	            			m = Pattern.compile(MASL_DOMAIN).matcher(descrip);
	            			if (m.find()) {
	            				m_maslDomainsImported = (Package_c)el;
	            			}
	        			}
	        		}
	            }
	        }
	        
	        if (m_maslProjectImported != null) {	        	
	            // create satisfactions
	            createMASLSatisfactions(elements);
	        }
		} catch (Exception e) {
			// revert the transaction
			CorePlugin.logError("Aborting the MASL import transaction", e);
			transaction_aborted = true;
			TransactionUtil.cancelTransactions(transactionGroup, e);
		} finally {
			Ooaofooa.endSaveOperation();
			if (!transaction_aborted) {
				TransactionUtil.endTransactions(transactionGroup);				
			}
		}
        if (m_maslProjectImported != null) {	        	
	    	// Create any missing port interface operations 
	    	// MASL projects don't always declare all domain 
	    	// terminator services in a MASL project.  Note that
        	// this call should _not_ be moved up inside the 
        	// transaction.  It needs to occur after all that 
        	// work is done.
	    	synchronizeWithMASLDomain(system);
        }		
    }

	// process an unformalized provided interface
	private void resolveMASLProvision(Provision_c c_p) {
		// check if requirement needs formalizing
		boolean formalize = false;
		String descrip = c_p.getDescrip();
		if (!descrip.isEmpty()) {
			Matcher m = Pattern.compile(MASL_FORMALIZE).matcher(descrip);
			if (m.find()) {
				formalize = true;
			}
		}

		if (formalize) {

			// get the name of the interface we're looking for from the
			// InformalName field
			String c_p_name = c_p.getInformalname();

			// ensure that all Interfaces are loaded
			PersistenceManager.ensureAllInstancesLoaded(c_p.getModelRoot(), Interface_c.class);
			// get reachable interfaces
			Interface_c[] interfaces = GenericPackageFormalizeOnC_PAction.getElements(c_p);

			// match the Interface
			for (Interface_c c_i : interfaces) {
				if (c_i.getName().equals(c_p_name)) {
					// formalize the requirement
					c_p.Formalize(c_i.getId(), true);
					// Set all the dialects to None to avoid persisting empty MASL bodies
					ProvidedOperation_c[] spr_pos = ProvidedOperation_c.getManySPR_POsOnR4503(ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(c_p));
					for ( ProvidedOperation_c spr_po : spr_pos ) {
					    spr_po.setDialect(Actiondialect_c.none);	
					}
					ProvidedSignal_c[] spr_pss = ProvidedSignal_c.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(c_p));
					for ( ProvidedSignal_c spr_ps : spr_pss ) {
					    spr_ps.setDialect(Actiondialect_c.none);	
					}
					break;
				}
			}
		}
	}    
    
	// process an unformalized required interface
	private void resolveMASLRequirement(Requirement_c c_r) {
		// check if requirement needs formalizing
		boolean formalize = false;
		String descrip = c_r.getDescrip();
		if (!descrip.isEmpty()) {
			Matcher m = Pattern.compile(MASL_FORMALIZE).matcher(descrip);
			if (m.find()) {
				formalize = true;
			}
		}

		if (formalize) {

			// get the name of the interface we're looking for from the
			// InformalName field
			String c_r_name = c_r.getInformalname();

			// ensure that all Interfaces are loaded
			PersistenceManager.ensureAllInstancesLoaded(c_r.getModelRoot(), Interface_c.class);
			// get reachable interfaces
			Interface_c[] interfaces = GenericPackageFormalizeOnC_RAction.getElements(c_r);

			// match the Interface
			for (Interface_c c_i : interfaces) {
				if (c_i.getName().equals(c_r_name)) {
					// formalize the requirement
					c_r.Formalize(c_i.getId(), true);
					// Set all the dialects to None to avoid persisting empty MASL bodies
					RequiredOperation_c[] spr_ros = RequiredOperation_c.getManySPR_ROsOnR4502(RequiredExecutableProperty_c.getManySPR_REPsOnR4500(c_r));
					for ( RequiredOperation_c spr_ro : spr_ros ) {
					    spr_ro.setDialect(Actiondialect_c.none);	
					}
					RequiredSignal_c[] spr_rss = RequiredSignal_c.getManySPR_RSsOnR4502(RequiredExecutableProperty_c.getManySPR_REPsOnR4500(c_r));
					for ( RequiredSignal_c spr_rs : spr_rss ) {
					    spr_rs.setDialect(Actiondialect_c.none);	
					}
					break;
				}
			}

		}
	}    
    
	/**
	 * Create masl "temporary interfaces"
	 * 
	 * There are some action bodies coming from masl that are associated with a masl 
	 * domain that we need to import into xtuml. We do this by creating an
	 * interface to put these action bodies in. 
	 * 
	 * @param c_i
	 */
	private void resolveMASLTemporaryInterface(Interface_c c_i) {
		// check if this is a temporary interface
		String description = c_i.getDescrip();
		boolean temporary_interface = true;
		if (!description.isEmpty()) {
			Matcher m = Pattern.compile(MASL_TEMPORARY_INTERFACE).matcher(description);
			if (!m.find()) {
				temporary_interface = false;
			}
		}

		if (temporary_interface) {

			// ensure that all Interfaces are loaded
			PersistenceManager.ensureAllInstancesLoaded(c_i.getModelRoot(), Interface_c.class);

			// get reachable Interfaces
			Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(c_i));
			List<Interface_c> elementList = new ArrayList<Interface_c>();
			PackageableElement_c[] pes = null;
			if (pkg != null) {
				pkg.Clearscope();
				pkg.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), false, "", pkg.getPackage_id(),
						Elementtypeconstants_c.INTERFACE);
				class PETest implements ClassQueryInterface_c {
					public boolean evaluate(Object candidate) {
						SearchResultSet_c selected = (SearchResultSet_c) candidate;
						return (selected.getName().equals("")
								&& selected.getType() == Elementtypeconstants_c.INTERFACE);
					}
				}
				SearchResultSet_c results = SearchResultSet_c.getOnePE_SRSOnR8005(pkg, new PETest());
				pes = PackageableElement_c.getManyPE_PEsOnR8002(ElementVisibility_c.getManyPE_VISsOnR8006(results));
				for (int i = 0; pes != null && i < pes.length; i++) {
					Interface_c elem = Interface_c.getOneC_IOnR8001(pes[i]);
					elementList.add(elem);

				}
			}
			Interface_c[] interfaces = elementList.toArray(new Interface_c[elementList.size()]);

			// check for matching interfaces
			for (int i = 0; i < interfaces.length; i++) {
				// if name matches, but not the same interface
				if (!interfaces[i].getId().equals(c_i.getId()) && interfaces[i].getName().equals(c_i.getName())) {
					// select my interface reference
					InterfaceReference_c c_ir = InterfaceReference_c.getOneC_IROnR4012(c_i);
					if (null != c_ir) {
						// replace the temporary interface
						Ooaofooa.Replaceformalinterface(c_ir.getModelRoot(), c_ir.getId(), interfaces[i].getId());

						// dispose self
						c_i.Dispose();

						// break from loop
						break;
					}
				}
			}
		}
	}
	
	// assign a masl component reference 
	private void resolveMASLComponentReference(ComponentReference_c cl_ic) {
		// check if containing package is a MASL project package
		boolean masl_project = false;
		Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(cl_ic));
		if (null != pkg) {
			String pkg_descrip = pkg.getDescrip();
			if (!pkg_descrip.isEmpty()) {
				Matcher m = Pattern.compile(MASL_PROJECT).matcher(pkg_descrip);
				if (m.find()) {
					masl_project = true;
				}
			}
		}

		if (masl_project) {

			// get the name of the component we're looking for from the Descrip
			// field
			String description = cl_ic.getDescrip();

			// parse name
			String cl_ic_name = "";
			if (!description.isEmpty()) {
				Matcher m = Pattern.compile("name:.*").matcher(description);
				if (m.find()) {
					cl_ic_name = m.group().substring(5);
					cl_ic.setDescrip(m.replaceAll(""));
				}
			}

			// ensure that all Components are loaded
			PersistenceManager.ensureAllInstancesLoaded(cl_ic.getModelRoot(), Component_c.class);
			// get reachable components
			Component_c[] components = GenericPackageAssignComponentOnCL_ICAction.getElements(cl_ic);

			// match the ComponentReference with the Component
			boolean wasAssigned = false;
			for (Component_c c_c : components) {
				if (c_c.getName().equals(cl_ic_name)) {
					// assign the component reference to the component
					cl_ic.Assigntocomp(c_c.getId());
					wasAssigned = true;
					break;
				}
			}
			
			// If a masl component reference was not found, warn the user 
			// via a a console message
			if (!wasAssigned && !cl_ic_name.isEmpty()) {
				String msg = "The following Component Reference was not assigned: " + cl_ic_name;
					msg += "\nThe domain was not found in the workspace.";
					msg += "\nAssure that Interproject References are enabled and that the domain has been imported.";

				CorePlugin.logError(msg, null);
			}
		}
	}

    public Package_c getImportedMASLDomainPackage() {
    	return m_maslDomainsImported;
    }
    
    public Package_c getImportedMASLProject() {
    	return m_maslProjectImported;
    }

    /**
     * @return true if either a masl domain package or a masl project
     * package was imported. 
     * 
     * @return
     */
    public boolean maslModelWasImported() {
    	return m_maslDomainsImported != null || m_maslProjectImported != null;
    }

    /**
     * synchronize with the domain library to assure all domain 
     * terminator services are represented as port operations in
     * BridgePoint 
     * @see issue https://support.onefact.net/issues/9316
     */          
	private void synchronizeWithMASLDomain(SystemModel_c system) {
		// Set dialect to none while updating missing interfaces

		int dialect = Pref_c.Getactiondialect(BridgePointPreferencesStore.DEFAULT_ACTION_LANGUAGE_DIALECT);
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();

		try {
			// Set the dialect to none so the synchronized interfaces have
			// Actiondialect_c.none as their dialect value
			store.setValue(BridgePointPreferencesStore.DEFAULT_ACTION_LANGUAGE_DIALECT, Actiondialect_c.none); // $NON-NLS-1$
			
			// Synchronize with library to assure partial
			// reference specifications are implemented
			PullSynchronizationChanges sync = new PullSynchronizationChanges(false, system);
			sync.run(null);
		} finally {
			// restore the dialect value
			store.setValue(BridgePointPreferencesStore.DEFAULT_ACTION_LANGUAGE_DIALECT, dialect); // $NON-NLS-1$
		}
	}
    
    private void createMASLSatisfactions(NonRootModelElement[] nrmes) {    	
		// Create satisfactions
		for (NonRootModelElement nrme : nrmes) {
			// process the unassigned component references
			if (nrme instanceof Package_c) {

				// check if containing package is a MASL project package
				boolean masl_project = false;
				Package_c pkg = (Package_c)nrme;
				if (null != pkg) {
					String pkg_descrip = pkg.getDescrip();
					if (!pkg_descrip.isEmpty()) {
						Matcher m = Pattern.compile("masl_project").matcher(pkg_descrip);
						if (m.find()) {
							masl_project = true;
						}
					}
				}

				if (masl_project) {
					Ooaofooa.Autocreatemaslsatisfactions(pkg.getModelRoot(), pkg.getPackage_id());					
				}
			}
		}
    }
    
    /**
     * Creates a model instance and initializes its values
     * to the given pre-BP-7.1-format parameters.
     */
    public void parsePreBP7_1Model(Vector parms)
    {
        // create the model's diagram instance
        UUID diagramId = IdAssigner.createUUIDFromString((String) parms.elementAt(0));
        Diagram_c diagram = new Diagram_c(getGraphicsModelRoot(), diagramId, "", 0, 0, 0, IdAssigner.NULL_UUID);   //$$NON-NLS-1$$

        // populate the diagram's fields
        int viewportX = Integer.parseInt((String) parms.elementAt(10));
        boolean bpSql = importer.getHeader().getProductVersion().equals(CoreImport.bpSqlVersion);
        if (bpSql && viewportX < 0)
            viewportX = 1600;
        diagram.setViewportx(viewportX);
        float zoom = Float.parseFloat((String) parms.elementAt(12));
        
        if(zoom <= 0.000000f)
            zoom = 1.000000f;
        
        int viewportY = Integer.parseInt((String) parms.elementAt(11));
        if (bpSql) {
            if (viewportY < 0)
                viewportY = 4200;
            viewportY = 6000 - viewportY - (int) (621 / zoom);
        }
        diagram.setViewporty(viewportY);
        diagram.setZoom(zoom);

        // create the model instance itself, mapping the old-format data items
        // to the correct constructor parameters
        Model_c model = new Model_c(
            getGraphicsModelRoot(),
            diagramId,
            Integer.parseInt((String) parms.elementAt(1)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
            Integer.parseInt((String) parms.elementAt(3)),
            ((String) parms.elementAt(4)).equals("0") ? false : true, //$$NON-NLS-1$$
            ((String) parms.elementAt(5)).equals("0") ? false : true, //$$NON-NLS-1$$
            Integer.parseInt((String) parms.elementAt(6)),
            Integer.parseInt((String) parms.elementAt(7)),
            ((String) parms.elementAt(8)).equals("0") ? false : true, //$$NON-NLS-1$$
            Integer.parseInt((String) parms.elementAt(9)),
            ((String) parms.elementAt(13)).equals("0") ? false : true, //$$NON-NLS-1$$
            0,
            0,
            0,
            0,
           null,
            "",
            "");
        model.relateAcrossR18To(diagram);
}

    /**
     * Creates a graphical element instance and initializes its values
     * to the given pre-BP-7.1-format parameters.
     */
    public void parsePreBP7_1GraphicalElement(Vector parms)
    {
        // create the graphical element instance, mapping the old-format data items
        // to the correct constructor parameters
        UUID graphicalElemId = IdAssigner.createUUIDFromString((String) parms.elementAt(0));
        GraphicalElement_c graphicalElem = new GraphicalElement_c(
            getGraphicsModelRoot(),
            graphicalElemId,
            IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(2)),
            Integer.parseInt((String) parms.elementAt(3)),
            null, "");

        // if this graphical-element has a parent graph-element created
        // for it already
        Graphelement_c graphElem = (Graphelement_c)
            graphOrGraphicalElemsMap.get(graphicalElemId);
        if (graphElem != null) {
            // relate the two
            graphicalElem.relateAcrossR23To(graphElem);
        }

        // otherwise
        else {
            // store the graphical-element in a map so that it may be
            // associated with its parent graph-element later on, when
            // the latter is created
            graphOrGraphicalElemsMap.put(graphicalElemId, graphicalElem);
        }
    }

    /**
     * Holds two different kinds of objects: graphical elements
     * awaiting their supertype graph-element instances to be created; and,
     * conversely, graph-elements awaiting their subtype graphical-element
     * instances to be created.  This allows a graph-element to be related
     * its subtype graphical-element no matter which one gets parsed (or,
     * created as a result of a shape or connector being parsed, in the case
     * of a graph-element) first.  Both kind of objects are mapped by the
     * graphical-element ID, which is known in both cases.
     */
    private Map<UUID, Object> graphOrGraphicalElemsMap = new HashMap<UUID, Object>();

    /**
     * Creates a shape instance and initializes its values
     * to the given pre-BP-7.1-format parameters.
     */
    public void parsePreBP7_1Shape(Vector parms)
    {
        // create the shape's ancestor chain of instances
        UUID elementId = IdAssigner.createUUIDFromString((String) parms.elementAt(0));
        Diagramelement_c diaElem = new Diagramelement_c(
            getGraphicsModelRoot(), elementId, true, IdAssigner.NULL_UUID);
        Graphelement_c graphElem = new Graphelement_c(
            getGraphicsModelRoot(), 0, 0, elementId, IdAssigner.NULL_UUID);
        graphElem.relateAcrossR302To(diaElem);
        Graphnode_c node = new Graphnode_c(getGraphicsModelRoot());
        node.relateAcrossR301To(graphElem);

        // create the shape instance itself
        Shape_c shape = new Shape_c(getGraphicsModelRoot(), elementId);
        shape.relateAcrossR19To(node);

        relateGraphElemToGraphicalElem(graphElem, elementId);

        // store the pre-BP-7.1 position and size data into the shape's graph-node
        // and graph-element
        int x1 = Integer.parseInt((String) parms.elementAt(1));
        int y1 = Integer.parseInt((String) parms.elementAt(2));
        int x2 = Integer.parseInt((String) parms.elementAt(3));
        int y2 = Integer.parseInt((String) parms.elementAt(4));
        graphElem.setPositionx(x1);
        graphElem.setPositiony(y1);
        node.setWidth(x2 - x1);
        node.setHeight(y2 - y1);
    }

    /**
     * Tries to relate the given graph-element to its subtype graphical-element
     * (of the given ID).  If the graphical-element already exists, the
     * two are immediately related.  Otherwise, the graph-element is held
     * in a map, to be retrieved and related when the graphical-element is parsed and
     * created.
     */
    private void relateGraphElemToGraphicalElem(
        Graphelement_c graphElem, UUID graphicalElemId)
    {
        // if the graph-element's associated graphical-element has already been parsed
        GraphicalElement_c graphicalElem = (GraphicalElement_c)
            graphOrGraphicalElemsMap.get(graphicalElemId);
        if (graphicalElem != null) {
            // relate the two directly
            graphicalElem.relateAcrossR23To(graphElem);
        }

        // otherwise
        else {
            // store the graph-element in a map, so it may
            // be retrieved and related later by the graphical-element
            // when it is parsed
            graphOrGraphicalElemsMap.put(graphicalElemId, graphElem);
        }
    }

    /**
     * Creates a connector instance and initializes its values
     * to the given pre-BP-7.1-format parameters.
     */
    public void parsePreBP7_1Connector(Vector parms)
    {
        // create the connector's ancestor chain of instances
        UUID elementId = IdAssigner.createUUIDFromString((String) parms.elementAt(0));
        Diagramelement_c diaElem = new Diagramelement_c(
            getGraphicsModelRoot(), elementId, true, IdAssigner.NULL_UUID);
        Graphelement_c graphElem = new Graphelement_c(
            getGraphicsModelRoot(), 0, 0, elementId, IdAssigner.NULL_UUID);
        graphElem.relateAcrossR302To(diaElem);
        Graphedge_c edge = new Graphedge_c(getGraphicsModelRoot());
        edge.relateAcrossR301To(graphElem);

        // create the connector instance itself
        Connector_c conn = new Connector_c(
            getGraphicsModelRoot(),
            elementId,
            IdAssigner.createUUIDFromString((String) parms.elementAt(3)));
        conn.relateAcrossR20To(edge);

        relateGraphElemToGraphicalElem(graphElem, elementId);

        // store the data about which elements the connector is connected to
        // in a separate structure, which will be used once all elements have
        // been parsed to relate the connector with the elements in DIM fashion
        PreBP7_1ConnectorToImport toImport = new PreBP7_1ConnectorToImport();
        toImport.connector = conn;
        toImport.startsAtGraphicalElementId = IdAssigner.createUUIDFromString((String) parms.elementAt(1));
        toImport.endsAtGraphicalElementId = IdAssigner.createUUIDFromString((String) parms.elementAt(2));
        preBP7_1ConnectorsToImport.add(toImport);
    }

    /**
     * Stores the id's of the graphical elements connected by a connector
     * created by parsePreBP7_1Connector().  Once all elements have been parsed,
     * this data is used to relate the connector and the graphical elements
     * in DIM fashion.
     */
    private class PreBP7_1ConnectorToImport
    {
        public Connector_c connector;
        public UUID startsAtGraphicalElementId;
        public UUID endsAtGraphicalElementId;
    }

    /**
     * Holds all the instances of PreBP7_1ConnectorToImport that are created
     * during the model parsing.
     */
    private List<PreBP7_1ConnectorToImport> preBP7_1ConnectorsToImport = new ArrayList<PreBP7_1ConnectorToImport>();

    /**
     * Gets called after all model elements have been parsed, to relate
     * each pre-BP-7.1-specified connector to the elements it connects, in DIM fashion.
     */
    public void importPreBP7_1Connectors()
    {
        // for each pre-BP-7.1 connector to import
        List<PreBP7_1ConnectorToImport> conns = preBP7_1ConnectorsToImport;
        for (int i = 0; i < conns.size(); i++) {
            final PreBP7_1ConnectorToImport toImport = conns.get(i);

            // relate this connector to the elements it connects, in DIM fashion
            createGraphConnectorForImportedPreBP7_1Connector(toImport, true);
            createGraphConnectorForImportedPreBP7_1Connector(toImport, false);
        }
    }

    /**
     * Relates the given end (specified by forStart) of the given
     * connector-to-be-imported to the element to which it is connected,
     * in DIM fashion.
     */
    private void createGraphConnectorForImportedPreBP7_1Connector(
        final PreBP7_1ConnectorToImport toImport, final boolean forStart)
    {
        // resolve the graph-element at which the connector starts (or ends)
        Connector_c conn = toImport.connector;
        GraphicalElement_c graphicalElement = null;
        if(forStart)
            graphicalElement = (GraphicalElement_c) getGraphicsModelRoot()
                .getInstanceList(GraphicalElement_c.class).get(
                    toImport.startsAtGraphicalElementId.toString());
        else
            graphicalElement = (GraphicalElement_c) getGraphicsModelRoot()
                .getInstanceList(GraphicalElement_c.class).get(
                    toImport.endsAtGraphicalElementId.toString());

        Graphelement_c graphElement =
            Graphelement_c.getOneDIM_GEOnR23(graphicalElement);

        // in certain cases (e.g. the end of a supertype connector), there will be no
        // graph-element at the connector's end, and therefore no graph-connector must
        // be created
        if (graphElement == null) return;

        // create a new graph-connector on the graph-element
        // at which the connector starts (or ends), and relate it
        // to the connector
        Graphconnector_c graphConn = new Graphconnector_c(
            getGraphicsModelRoot());
        graphConn.relateAcrossR311To(graphElement);
        Graphedge_c edge = Graphedge_c.getOneDIM_EDOnR20(conn);
        if (forStart) graphConn.relateAcrossR320To(edge);
        else graphConn.relateAcrossR321To(edge);

        // for each line segment of this connector; we are seeking the
        // one that starts (or ends) the connector
        LineSegment_c[] segments = LineSegment_c.getManyGD_LSsOnR6(conn);
        LineSegment_c segment = null;
        for (int j = 0; j < segments.length; j++) {
            // if this is the starting (or ending) segment of this connector
            if ((forStart && LineSegment_c.getOneGD_LSOnR7Follows(segments[j]) == null)
                || (!forStart && LineSegment_c.getOneGD_LSOnR7Precedes(segments[j]) == null)) {
                // we are have found the segment sought
                segment = segments[j];
                break;
            }
        }

        // position the new graph-connector at the starting (or ending)
        // endpoint of this connector's starting (or ending) segment
        Waypoint_c terminalPoint = forStart ?
            Waypoint_c.getOneDIM_WAYOnR21(segment) :
            Waypoint_c.getOneDIM_WAYOnR22(segment);
        graphConn.setPositionx(terminalPoint.getPositionx());
        graphConn.setPositiony(terminalPoint.getPositiony());

    }

    /**
     * Creates a line-segment instance and initializes its values
     * to the given pre-BP-7.1-format parameters.
     */
    public void parsePreBP7_1LineSegment(Vector parms)
    {
        // create the line-segment instance
        LineSegment_c segment = new LineSegment_c(
            getGraphicsModelRoot(),
            IdAssigner.createUUIDFromString((String) parms.elementAt(0)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(1)),
            IdAssigner.createUUIDFromString((String) parms.elementAt(6)),
            IdAssigner.NULL_UUID, IdAssigner.NULL_UUID);

        // store the segment's positional data in a separate object,
        // which will get processed later on to produce waypoints
        PreBP7_1LineSegmentToImport toImport = new PreBP7_1LineSegmentToImport();
        toImport.segment = segment;
        toImport.startX = Integer.parseInt((String) parms.elementAt(2));
        toImport.startY = Integer.parseInt((String) parms.elementAt(3));
        toImport.endX = Integer.parseInt((String) parms.elementAt(4));
        toImport.endY = Integer.parseInt((String) parms.elementAt(5));
        preBP7_1LineSegmentsToImportMap.put(segment, toImport);
        preBP7_1LineSegmentsToImportList.add(toImport);
    }

    /**
     * Holds the positional data of a pre-BP-7.1-specified line segment,
     * which will get processed once all elements have been
     * parsed to produce 1 or 2 waypoints of the segment's parent connector.
     */
    private class PreBP7_1LineSegmentToImport
    {
        public LineSegment_c segment;
        public int startX, startY;
        public int endX, endY;
    }

    /**
     * Holds all the instances of PreBP7_1LineSegmentToImport that are created
     * during the model parsing, mapped by the segment which they describe.
     */
    private Map<LineSegment_c, PreBP7_1LineSegmentToImport> preBP7_1LineSegmentsToImportMap = new HashMap<LineSegment_c, PreBP7_1LineSegmentToImport>();

    /**
     * Holds all the instances of PreBP7_1LineSegmentToImport that are created
     * during the model parsing.  We need this in addition to the map above to
     * ensure that segments (actually, their connectors) are processed in a
     * consistent order by importPreBP7_1LineSegments().
     */
    private List<PreBP7_1LineSegmentToImport> preBP7_1LineSegmentsToImportList = new ArrayList<PreBP7_1LineSegmentToImport>();

    /**
     * Gets called after all model elements have been parsed, to create
     * the waypoints that hold the positional data of the segments.
     */
    public void importPreBP7_1LineSegments()
    {
        // for each imported segment to process
        List toImports = preBP7_1LineSegmentsToImportList;
        for (int i = 0; i < toImports.size(); i++) {
            PreBP7_1LineSegmentToImport toImport = (PreBP7_1LineSegmentToImport)
                toImports.get(i);

            // while we haven't yet found the first segment in the connector
            // to which the above segment belongs
            LineSegment_c firstSegment = null;
            LineSegment_c segment = toImport.segment;
            while (firstSegment == null) {
                // if there is no segment that precedes this one
                LineSegment_c previousSegment =
                    LineSegment_c.getOneGD_LSOnR7Follows(segment);
                if (previousSegment == null) {
                    // we've found the starting segment
                    firstSegment = segment;
                }

                // otherwise, move on to the preceding segment
                else segment = previousSegment;
            }

            // while we haven't yet processed the last segment in the connector
            // to which the above segment belongs
            segment = firstSegment;
            Connector_c conn = Connector_c.getOneGD_CONOnR6(segment);
            Graphedge_c edge = Graphedge_c.getOneDIM_EDOnR20(conn);
            Waypoint_c previousWaypoint = null;
            while (segment != null) {
                // remove from the map of segments the info that was stored for
                // this segment during the parse; remove it also from the list
                // of corresponding segments, and update our counter within that list
                toImport = (PreBP7_1LineSegmentToImport)
                    preBP7_1LineSegmentsToImportMap.remove(segment);
                int index = toImports.indexOf(toImport);
                toImports.remove(index);
                if (i >= index) i--;

                // if we are currently processing the first segment in the
                // connector
                if (segment == firstSegment) {
                    // create a waypoint for the start of the segment;
                    // all other segments will only contribute an ending
                    // waypoint, since the ends of the segments coincide
                    previousWaypoint = createWaypointForPreBP7_1LineSegment(
                        true, toImport.startX, toImport.startY, segment, edge,
                        previousWaypoint);
                }

                // otherwise
                else {
                    // assign the last created waypoint as the starting
                    // waypoint of this segment
                    previousWaypoint.relateAcrossR21To(segment);
                }

                // create a waypoint for the end of the segment
                previousWaypoint = createWaypointForPreBP7_1LineSegment(
                    false, toImport.endX, toImport.endY, segment, edge,
                    previousWaypoint);

                // move to the next segment in the connector
                segment = LineSegment_c.getOneGD_LSOnR7Precedes(segment);
            }
        }
    }

    /**
     * Creates a new waypoint to hold the given positional data for the given
     * end (specified by 'start') of the given segment, which is part of
     * the given graph-edge containing the given previous-waypoint.
     */
    private Waypoint_c createWaypointForPreBP7_1LineSegment(
        boolean start, int x, int y,
        LineSegment_c segment, Graphedge_c edge, Waypoint_c previousWaypoint)
    {
        // create the waypoint instance with the given positional data
        Waypoint_c waypoint = new Waypoint_c(getGraphicsModelRoot());
        waypoint.setPositionx(x);
        waypoint.setPositiony(y);

        // relate the waypoint as being the given end of the given segment
        if (start) waypoint.relateAcrossR21To(segment);
        else waypoint.relateAcrossR22To(segment);

        // relate the waypoint as belonging to the given graph-edge
        waypoint.relateAcrossR319To(edge);

        // relate the waypoint as following the given previous-waypoint
        if (previousWaypoint != null) {
            waypoint.relateAcrossR324ToFollows(previousWaypoint);
        }

        return waypoint;
    }

    /**
     * Holds vectors of parameters corresponding to legacy-version
     * connector-text instances.  These are held, rather than imported
     * right away, because connector-texts now have diagram-element ID's
     * of their own, so if they were immediately instantiated upon their
     * retrieval from the model file, they would mess up the
     * diagram-element ID sequence that holds for the file.  So instead,
     * we wait until the end of the import process to create these
     * text instances and assign them ID's.
     */
    private List<Vector> connectorTextParmsVectors = new ArrayList<Vector>();

    private static List<CD_IC> removedUsedComponents = new ArrayList<CD_IC>();

    private static List<CD_IID> removedIIDList = new ArrayList<CD_IID>();
    
    private static List<CD_CID> removedCIDList = new ArrayList<CD_CID>();

    /**
     * Has this helper hold on to the given vector of parameters for
     * later processing when importPreBP7_1_1ConnectorTexts() is called.
     */
    public void storePreBP7_1_1ConnectorText(Vector parms)
    {
        connectorTextParmsVectors.add(parms);
    }

    /**
     * Calls parsePreBP7_1_1ConnectorText() on all the vectors of
     * connector-text parameters that have been stored by this helper
     * through calls to storePreBP7_1_1ConnectorText().
     */
    public void importPreBP7_1_1ConnectorTexts()
    {
        // for each vector of connector-text parameters stored by this helper
        List vectors = connectorTextParmsVectors;
        for (int i = 0; i < vectors.size(); i++) {
            // parse this vector to create the text instance
            parsePreBP7_1_1ConnectorText((Vector)vectors.get(i));
        }
    }

    /**
     * Creates start, middle, and end connector text instances and
     * initializes their values to the given pre-BP-7.1.1-format parameters.
     */
    private void parsePreBP7_1_1ConnectorText(Vector parms)
    {
        // for each connector "end" for which a text must be created
        int firstTextSpecificParamIndex = 1;
        int numParamsPerText = 6;
        final UUID connectorId = IdAssigner.createUUIDFromString((String) parms.elementAt(0));
        int[] ends = {End_c.Start, End_c.Middle, End_c.End};
        for (int i = 0; i < 3; i++) {
            // detm where the parameters for the text at this end start
            // within the given vector
            int startParamIndex = firstTextSpecificParamIndex + i * numParamsPerText;

            // create the text's ancestor diagram-element
            Diagramelement_c diaElem = new Diagramelement_c(getGraphicsModelRoot());
            UUID elementId = diaElem.getElementid();

            // create the text's ancestor graph-element
            Graphelement_c graphElem = new Graphelement_c(
                getGraphicsModelRoot(), 0, 0, elementId, IdAssigner.NULL_UUID);
            graphElem.relateAcrossR302To(diaElem);
            int nwX = Integer.parseInt((String) parms.elementAt(startParamIndex));
            int nwY = Integer.parseInt((String) parms.elementAt(startParamIndex + 1));
            graphElem.setPositionx(nwX);
            graphElem.setPositiony(nwY);

            // create the text's ancestor graph-node
            Graphnode_c node = new Graphnode_c(getGraphicsModelRoot());
            node.relateAcrossR301To(graphElem);
            int seX = Integer.parseInt((String) parms.elementAt(startParamIndex + 2));
            int seY = Integer.parseInt((String) parms.elementAt(startParamIndex + 3));
            node.setWidth(seX - nwX);
            node.setHeight(seY - nwY);

            // create the text instance
            FloatingText_c startText = new FloatingText_c(
                getGraphicsModelRoot(),
                elementId,
                connectorId,
                ends[i],
                Integer.parseInt((String) parms.elementAt(startParamIndex + 4)),
                Integer.parseInt((String) parms.elementAt(startParamIndex + 5)));
            startText.relateAcrossR19To(node);

            // relate the text to its connector
            Connector_c conn = (Connector_c) getGraphicsModelRoot().getInstanceList(Connector_c.class).get(connectorId.toString());
            startText.relateAcrossR8To(conn);
        }
    }
    /** Converts any connector that has not been already,
     *  to use the AnchorOnSegment class as the link
     *  between a Graphical Connector and a Line Segment
     */
    public void createAnchorsOnSegmentsForPre7_1_2Connectors() {
        Connector_c[] connectors = Connector_c.ConnectorInstances(getGraphicsModelRoot());
        for(int i = 0; i < connectors.length; i++) {
            // for each graph connector that is anchored
            // on an edge
            Graphconnector_c[] anchors = Graphconnector_c.getManyDIM_CONsOnR311(Graphelement_c.getManyDIM_GEsOnR301(Graphedge_c.getManyDIM_EDsOnR20(connectors[i])));
            for(int j = 0; j < anchors.length; j++) {
                Connector_c connectingConnector = Connector_c.getOneGD_CONOnR20(Graphedge_c.getOneDIM_EDOnR321(anchors[j]));
                // Get the last segment of the connector
                LineSegment_c[] segments = LineSegment_c.getManyGD_LSsOnR6(connectingConnector);
                LineSegment_c endSeg = null;
                for(int k = 0; k < segments.length; k++) {
                    endSeg = segments[k];
                    LineSegment_c nextSeg = LineSegment_c.getOneGD_LSOnR7Precedes(endSeg);
                    if(nextSeg == null)
                        break;
                }
                Waypoint_c way = Waypoint_c.getOneDIM_WAYOnR22(endSeg);
                float positionX = 0;
                float positionY = 0;
                if(way != null) {
                    positionX = way.getPositionx();
                    positionY = way.getPositiony();
                } else {
                    positionX = anchors[j].getPositionx();
                    positionY = anchors[j].getPositiony();
                }
                // check every segment on this connector to see which
                // one the graph connector is over, associating that
                // segment with the graph connector using the GD_AOS
                // link class
                boolean foundSegment = false;
                segments = LineSegment_c.getManyGD_LSsOnR6(connectors[i]);
                for(int k = 0; k < segments.length; k++) {
                    Waypoint_c segmentStart = Waypoint_c.getOneDIM_WAYOnR21(segments[k]);

                    Waypoint_c segmentEnd = Waypoint_c.getOneDIM_WAYOnR22(segments[k]);

                    if(Gr_c.Isover(
                            Gr_c.Gethotspotsize(),
                            (int) positionX,
                            (int) segmentStart.getPositionx(),
                            (int) segmentEnd.getPositionx(),
                            (int) positionY,
                            (int) segmentStart.getPositiony(),
                            (int) segmentEnd.getPositiony())) {
                        foundSegment = true;
                        AnchorOnSegment_c aos = new AnchorOnSegment_c(getGraphicsModelRoot());
                        aos.relateAcrossR26To(segments[k]);
                        aos.relateAcrossR26To(anchors[j]);
                    }
                }
                // if for some reason the point was not over any segment
                // associate the connecting connector to any segment on the
                // connected to connector
                if(!foundSegment) {
                    LineSegment_c segment = LineSegment_c.getOneGD_LSOnR5(connectors[i]);
                    AnchorOnSegment_c aos = new AnchorOnSegment_c(getGraphicsModelRoot());
                    aos.relateAcrossR26To(anchors[j]);
                    aos.relateAcrossR26To(segment);
                }
            }
        }
    }
    /**
     * Creates the Shape_c subtype if it doesn't exist
     * already, this method also fixes it's own inconsistency
     * by removing the additional subtype (the method originally
     * created a new subtype even if a ContainingShape_c subtype
     * already existed)
     */
    public void createShapeSubtypes(Ooaofgraphics root) {
        Shape_c[] shapes = Shape_c.ShapeInstances(root, null, false);
        for(int i = 0; i < shapes.length; i++) {
            NoncontainingShape_c ncs = NoncontainingShape_c.getOneGD_NCSOnR28(shapes[i]);
            ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(shapes[i]);
            if(ncs == null && cs == null) {
                ncs = new NoncontainingShape_c(root);
                ncs.relateAcrossR28To(shapes[i]);
            }
            if(ncs != null && cs != null) {
                // both subtypes exist, this
                // has to be a containing shape
                // remove the non containing shape
                // subtype
                shapes[i].unrelateAcrossR28From(ncs);
                ncs.delete();
            }
        }
    }
    static List<Interface_c> interfaceInstances = new ArrayList<Interface_c>();
    
    public static void addInterfaceToInstances(NonRootModelElement iface) {
        if(iface instanceof Interface_c)
            interfaceInstances.add((Interface_c)iface);
    }
    
    static List<C_SF> satisfactionInstances = new ArrayList<C_SF>();
    public List<C_SF> localSatisfactionInstances = new ArrayList<C_SF>();
    
    static class C_SF {
        UUID fRequirement_ID;
        UUID fProvision_ID;
        Satisfaction_c fInst;
        public C_SF(UUID provision_id, UUID requirement_id, Satisfaction_c inst) {
            fInst = inst;
            fRequirement_ID = requirement_id;
            fProvision_ID = provision_id;
        }
        @Override
        public boolean equals(Object arg0) {
            if(arg0 instanceof C_SF) {
                C_SF candidate = (C_SF) arg0;
                if(candidate.fInst == this.fInst) {
                    if(candidate.fProvision_ID.equals(this.fProvision_ID)) {
                        if(candidate.fRequirement_ID.equals(this.fRequirement_ID)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
    }

    public Vector<CL_IR> old_imported_requirements = new Vector<CL_IR>();
    
    public static class CL_IR {
        UUID fImportedReference_ID;
        UUID fRequirement_ID;
        UUID fProvision_ID;
        String fName;
        String fDescrip;
        public CL_IR(UUID importedRef_id, UUID requirement_id, UUID provision_id, String name, String descrip) {
            fImportedReference_ID = importedRef_id;
            fRequirement_ID = requirement_id;
            fProvision_ID = provision_id;
            fName = name;
            fDescrip = descrip;
        }
    }
    
    public Vector<CL_IPINS> old_imported_provision_satisfactions = new Vector<CL_IPINS>();
    
    public static class CL_IPINS {
        UUID fRequirement_ID;
        UUID fProvision_ID;
        UUID fImportedProvision_ID;
        public CL_IPINS(UUID requirement_id, UUID provision_id, UUID importedPro_id) {
            fImportedProvision_ID = importedPro_id;
            fRequirement_ID = requirement_id;
            fProvision_ID = provision_id;
        }
    }
    
    public Vector<PA_SIC> old_satisfactions_in_comp = new Vector<PA_SIC>();
    
    public static class PA_SIC {
        UUID fComponent_ID;
        UUID fRequirement_ID;
        UUID fProvision_ID;
        public PA_SIC(UUID component_id, UUID requirement_id, UUID provision_id) {
            fComponent_ID = component_id;
            fRequirement_ID = requirement_id;
            fProvision_ID = provision_id;
        }
    }
    
    public Vector<PA_SICP> old_satisfactions_in_comppackage = new Vector<PA_SICP>();
    
    public static class PA_SICP {
        UUID fComponentPackage_ID;
        UUID fRequirement_ID;
        UUID fProvision_ID;
        public PA_SICP(UUID component_package_id, UUID requirement_id, UUID provision_id) {
            fComponentPackage_ID = component_package_id;
            fRequirement_ID = requirement_id;
            fProvision_ID = provision_id;
        }
    }
    
    public void addSatisfactionToInstances(Satisfaction_c inst, UUID reqID, UUID proID) {
        C_SF csf = new C_SF(proID, reqID, inst);
        satisfactionInstances.add(csf);
        localSatisfactionInstances.add(csf);
    }
    
    public static List<C_R> removedCRList = new ArrayList<C_R>();
    public static List<C_P> removedCPList = new ArrayList<C_P>();
    
    /**
     * This function is used to migrate 1.4.2-based provisions and
     * requirements to 1.5.x provisions and requirements with ports
     * and interface references.
     * @param cid 
     */
    public void migrateInterfaceChanges(final Ooaofooa modelRoot, Component_c component, CD_CID cid)
    {
        // ensure that all interfaces have been
        // loaded
        if (modelRoot.getRoot() == null) {
            SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
                    .getDefaultInstance(), new ClassQueryInterface_c() {

                public boolean evaluate(Object candidate) {
                    return ((SystemModel_c) candidate).getName().equals(
                            EclipseOoaofooa
                                    .getProjectNameFromModelRootId(modelRoot
                                            .getId()));
                }

            });
            modelRoot.setRoot(system);
        }
        PersistenceManager.ensureAllChildInstancesLoaded(modelRoot.getRoot().getPersistableComponent(),
                modelRoot,
                Interface_c.class, true);
        
        Interface_c[] intfs = interfaceInstances.toArray(new Interface_c[interfaceInstances.size()]);
        
        CD_IID[] iids = interface_in_diagrams.toArray(new CD_IID[interface_in_diagrams.size()]);
        CD_IID iid = null;
        for(int i = 0; i < iids.length; i++) {
            if(iids[i].Component_Id.equals(cid.Id)) {
                iid = iids[i];
                C_P[] cps = old_provisions.toArray(new C_P[old_provisions.size()]);
                for(int j = 0; j < cps.length; j++) {
                    C_P old_prov = cps[j];
                    
                    if (!iid.Id.equals(old_prov.Diagram_Id)) continue;
                    
                    Provision_c cp = new Provision_c(modelRoot, old_prov.Provision_Id,
                            old_prov.Name, old_prov.InformalName, old_prov.Descrip, "");
        
                    GraphicalElement_c[] graphElements = GraphicalElement_c
                        .GraphicalElementInstances(Ooaofgraphics
                            .getInstance(modelRoot.getId()));
                    GraphicalElement_c graphElem = null;
                    for(int k = 0; k < graphElements.length; k++) {
                        if(graphElements[k].getOoa_id().equals(old_prov.Provision_Id)) {
                            graphElements[k].setRepresents(cp);
                            graphElem = graphElements[k];
                            break;
                        }
                    }
                    
                    InterfaceReference_c cir = new InterfaceReference_c(modelRoot);
                    cp.relateAcrossR4009To(cir);
        
                    if(graphElem != null) {
                        graphElem.setOoa_id(cir.getId());
                    }
                    
                    Port_c cpo = new Port_c(modelRoot);
                    cpo.relateAcrossR4016To(cir);
                    cpo.relateAcrossR4010To(component);
        
                    for (int k = 0; k < intfs.length; k++) {
                        if (intfs[k].getId().equals(old_prov.Interface_Id)) {
                            cir.relateAcrossR4012To(intfs[k]);
                            break;
                        }
                    }
                    
                    cp.batchRelate(modelRoot, false, true);
                    cpo.batchRelate(modelRoot, false, true);
                    cir.batchRelate(modelRoot, false, true);
                    
                    cpo.Initialize();
                    
                    C_SF[] sats = satisfactionInstances.toArray(new C_SF[satisfactionInstances.size()]);
                    for(int k = 0; k < sats.length; k++) {
                        if(sats[k].fProvision_ID.equals(old_prov.Provision_Id)) {
                            // if the provision is one from an imported component
                            // do not relate
                            CD_IC[] cd_ics = used_components.toArray(new CD_IC[used_components.size()]);
                            boolean satisfiesWithImportedComponent = false;
                            for(int l = 0; l < cd_ics.length; l++) {
                                if(satisfactionIsWithImportedComponent(sats[k].fRequirement_ID, cd_ics[l], true)) {
                                    satisfiesWithImportedComponent = true;
                                    break;
                                }
                            }
                            if(satisfiesWithImportedComponent) continue;
                            // create the persistence instances and relate
                            sats[k].fInst.batchRelate(modelRoot, false, false);
                            cp.relateAcrossR4002To(sats[k].fInst);
                        }
                    }
                    
                    old_provisions.removeElement(old_prov);
                    removedCPList.add(old_prov);
                }
        
                C_R[] crs = old_requirements.toArray(new C_R[old_requirements.size()]);
                for(int j = 0; j < crs.length; j++) {
                    C_R old_req = crs[j];
                    
                    if (!iid.Id.equals(old_req.Diagram_Id)) continue;
                    
                    Requirement_c cr = new Requirement_c(modelRoot, old_req.Requirement_Id,
                            old_req.Name, old_req.Descrip, old_req.InformalName, "");
        
                    GraphicalElement_c[] graphElements = GraphicalElement_c
                            .GraphicalElementInstances(Ooaofgraphics
                                    .getInstance(modelRoot.getId()));
                    GraphicalElement_c graphElem = null;
                    for(int k = 0; k < graphElements.length; k++) {
                        if(graphElements[k].getOoa_id().equals(old_req.Requirement_Id)) {
                            graphElements[k].setRepresents(cr);
                            graphElem = graphElements[k];
                            break;
                        }
                    }
                    
                    InterfaceReference_c cir = new InterfaceReference_c(modelRoot);
                    cr.relateAcrossR4009To(cir);
        
                    if(graphElem != null) {
                        graphElem.setOoa_id(cir.getId());
                    }
                    
                    Port_c cpo = new Port_c(modelRoot);
                    cpo.relateAcrossR4016To(cir);
                    cpo.relateAcrossR4010To(component);
                    
                    for (int k = 0; k < intfs.length; k++) {
                        if (intfs[k].getId().equals(old_req.Interface_Id)) {
                            cir.relateAcrossR4012To(intfs[k]);
                            break;
                        }
                    }
                    
                    cr.batchRelate(modelRoot, false, true);
                    cpo.batchRelate(modelRoot, false, true);
                    cir.batchRelate(modelRoot, false, true);
                    
                    cpo.Initialize();

                    C_SF[] sats = satisfactionInstances.toArray(new C_SF[satisfactionInstances.size()]);
                    for(int k = 0; k < sats.length; k++) {
                        if(sats[k].fRequirement_ID.equals(old_req.Requirement_Id)) {
                            // if the provision is one from an imported component
                            // do not relate
                            CD_IC[] cd_ics = used_components.toArray(new CD_IC[used_components.size()]);
                            boolean satisfiesWithImportedComponent = false;
                            for(int l = 0; l < cd_ics.length; l++) {
                                if(satisfactionIsWithImportedComponent(sats[k].fProvision_ID, cd_ics[l], false)) {
                                    satisfiesWithImportedComponent = true;
                                    break;
                                }
                            }
                            if(!satisfiesWithImportedComponent) {
                                CD_IC[] removedICs = removedUsedComponents.toArray(new CD_IC[removedUsedComponents.size()]);
                                for(int l = 0; l < removedICs.length; l++) {
                                    if(satisfactionIsWithImportedComponent(sats[k].fProvision_ID, removedICs[l], false)) {
                                        satisfiesWithImportedComponent = true;
                                        break;
                                    }
                                }
                            }
                            if(satisfiesWithImportedComponent) continue;
                            // create the persistence instances and relate
                            sats[k].fInst.batchRelate(modelRoot, false, false);
                            cr.relateAcrossR4002To(sats[k].fInst);
                        }
                    }
                    
                    old_requirements.removeElement(old_req);
                    removedCRList.add(old_req);
                }

                interface_in_diagrams.removeElement(iid);
                removedIIDList.add(iid);
            }

        }
    }
    private boolean satisfactionIsWithImportedComponent(UUID ifaceID, CD_IC cd_ic, boolean provision) {
        CD_CID[] cids = component_in_diagrams.toArray(new CD_CID[component_in_diagrams.size()]);
        CD_CID cid = null;
        for(int i = 0; i < cids.length; i++) {
            if(cids[i].Id.equals(cd_ic.Diagram_Id)) {
                cid = cids[i];
                break;
            }
        }
        if(cid == null) {
            cid = getCIDFromRemovedList(cd_ic.Diagram_Id);
        }
        if(cid != null) {
            if(provision) {
                C_R[] crs = old_requirements.toArray(new C_R[old_requirements.size()]);
                C_R cr = null;
                for(int l = 0; l < crs.length; l++) {
                    if(crs[l].Requirement_Id.equals(ifaceID)) {
                        cr = crs[l];
                        break;
                    }
                }
                if(cr == null) {
                    cr = getCRFromRemovedList(ifaceID);
                }
                if(cr != null) {
                    CD_IID[] iids = interface_in_diagrams.toArray(new CD_IID[interface_in_diagrams.size()]);
                    for(int j = 0; j < iids.length; j++) {
                        if(iids[j].Component_Id.equals(cid.Id)) {
                            if(iids[j].Id.equals(cr.Diagram_Id))
                                return true;
                        }
                    }
                    CD_IID iid = getIIDFromRemovedList(cid.Id, cr.Diagram_Id);
                    if(iid != null) {
                        if(iid.Id.equals(cr.Diagram_Id))
                            return true;
                    }
                }
            } else {
                C_P[] cps = old_provisions.toArray(new C_P[old_provisions.size()]);
                C_P cp = null;
                for(int l = 0; l < cps.length; l++) {
                    if(cps[l].Provision_Id.equals(ifaceID)) {
                        cp = cps[l];
                        break;
                    }
                }
                if(cp == null) {
                    cp = getCPFromRemovedList(ifaceID);
                }
                if(cp != null) {
                    CD_IID[] iids = interface_in_diagrams.toArray(new CD_IID[interface_in_diagrams.size()]);
                    for(int j = 0; j < iids.length; j++) {
                        if(iids[j].Component_Id.equals(cid.Id)) {
                            if(iids[j].Id.equals(cp.Diagram_Id))
                                return true;
                        }
                    }
                    CD_IID iid = getIIDFromRemovedList(cid.Id, cp.Diagram_Id);
                    if(iid != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private CD_CID getCIDFromRemovedList(UUID diagramId) {
        CD_CID[] cids = removedCIDList.toArray(new CD_CID[removedCIDList.size()]);
        for(int i = 0; i < cids.length; i++) {
            if(cids[i].Id.equals(diagramId)) {
                return cids[i];
            }
        }
        return null;
    }

    private CD_IID getIIDFromRemovedList(UUID componentId, UUID diagramID) {
        CD_IID[] iids = removedIIDList.toArray(new CD_IID[removedIIDList.size()]);
        for(int i = 0; i < iids.length; i++) {
            if(iids[i].Component_Id.equals(componentId) && iids[i].Id.equals(diagramID)) {
                return iids[i];
            }
        }
        return null;
    }

    private C_P getCPFromRemovedList(UUID ifaceID) {
        C_P[] cps = removedCPList.toArray(new C_P[removedCPList.size()]);
        for(int i = 0; i < cps.length; i++) {
            if(cps[i].Provision_Id.equals(ifaceID)) {
                return cps[i];
            }
        }
        return null;
    }

    private C_R getCRFromRemovedList(UUID ifaceID) {
        C_R[] crs = removedCRList.toArray(new C_R[removedCRList.size()]);
        for(int i = 0; i < crs.length; i++) {
            if(crs[i].Requirement_Id.equals(ifaceID)) {
                return crs[i];
            }
        }
        return null;
    }

    /**
     * Migrate the 1.5.1 dynamic arrays to 1.5.2 where both dynamic and fixed-
     * size arrays are supported.
     * 
     * @parameter obj A model element that has a Dimensions or 
     *                Return_Dimensions attribute
     */
    public void upgradeDSAs(Ooaofooa modelRoot) {
      InterfaceOperation_c [] ioInstances = InterfaceOperation_c.InterfaceOperationInstances(modelRoot, null, false);
      for (int j=0; j < ioInstances.length; j++) {
        InterfaceOperation_c temp = ioInstances[j];
        if (ImportInteger.isInt(temp.getReturn_dimensions())) {
          int numDims = Integer.parseInt(temp.getReturn_dimensions());
          String dimensionString = "";
          for (int i = 0; i < numDims; i++) {
            dimensionString += "[]";
          }
          temp.setReturn_dimensions(dimensionString);
          for (int i = 0; i < numDims; i++) {
            temp.Resizereturn_dimensions(i, 0, numDims);
          }
        }
      }
      PropertyParameter_c [] ppInstances = PropertyParameter_c.PropertyParameterInstances(modelRoot, null, false);
      for (int j=0; j < ppInstances.length; j++) {
              PropertyParameter_c temp = ppInstances[j];
        if (ImportInteger.isInt(temp.getDimensions())) {
          int numDims = Integer.parseInt(temp.getDimensions());
          String dimensionString = "";
          for (int i = 0; i < numDims; i++) {
            dimensionString += "[]";
          }
          temp.setDimensions(dimensionString);
          for (int i = 0; i < numDims; i++) {
            temp.Resizedimensions(i, 0, numDims);
          }
        }
      }
      Bridge_c [] brInstances = Bridge_c.BridgeInstances(modelRoot, null, false);
      for (int j=0; j < brInstances.length; j++) {
        Bridge_c temp = brInstances[j];
        if (ImportInteger.isInt(temp.getReturn_dimensions())) {
          int numDims = Integer.parseInt(temp.getReturn_dimensions());
          String dimensionString = "";
          for (int i = 0; i < numDims; i++) {
            dimensionString += "[]";
          }
          temp.setReturn_dimensions(dimensionString);
          for (int i = 0; i < numDims; i++) {
            temp.Resizereturn_dimensions(i, 0, numDims);
          }
        }
        }
    BridgeParameter_c [] bpInstances = BridgeParameter_c.BridgeParameterInstances(modelRoot, null, false);
    for (int j=0; j < bpInstances.length; j++) {
      BridgeParameter_c temp = bpInstances[j];
      if (ImportInteger.isInt(temp.getDimensions())) {
        int numDims = Integer.parseInt(temp.getDimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setDimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizedimensions(i, 0, numDims);
        }
      }
        }
    Function_c [] fnInstances = Function_c.FunctionInstances(modelRoot, null, false);
    for (int j=0; j < fnInstances.length; j++) {
      Function_c temp = fnInstances[j];
      if (ImportInteger.isInt(temp.getReturn_dimensions())) {
        int numDims = Integer.parseInt(temp.getReturn_dimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setReturn_dimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizereturn_dimensions(i, 0, numDims);
        }
      }
        }
    FunctionParameter_c [] fpInstances = FunctionParameter_c.FunctionParameterInstances(modelRoot, null, false);
    for (int j=0; j < fpInstances.length; j++) {
      FunctionParameter_c temp = fpInstances[j];
      if (ImportInteger.isInt(temp.getDimensions())) {
        int numDims = Integer.parseInt(temp.getDimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setDimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizedimensions(i, 0, numDims);
        }
      }
        }
    StructureMember_c [] smInstances = StructureMember_c.StructureMemberInstances(modelRoot, null, false);
    for (int j=0; j < smInstances.length; j++) {
      StructureMember_c temp = smInstances[j];
      if (ImportInteger.isInt(temp.getDimensions())) {
        int numDims = Integer.parseInt(temp.getDimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setDimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizedimensions(i, 0, numDims);
        }
      }
        }
    StateMachineEventDataItem_c [] diInstances = StateMachineEventDataItem_c.StateMachineEventDataItemInstances(modelRoot, null, false);
    for (int j=0; j < diInstances.length; j++) {
      StateMachineEventDataItem_c temp = diInstances[j];
      if (ImportInteger.isInt(temp.getDimensions())) {
        int numDims = Integer.parseInt(temp.getDimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setDimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizedimensions(i, 0, numDims);
        }
      }
        }
    Attribute_c [] atInstances = Attribute_c.AttributeInstances(modelRoot, null, false);
    for (int j=0; j < atInstances.length; j++) {
      Attribute_c temp = atInstances[j];
      if (ImportInteger.isInt(temp.getDimensions())) {
        int numDims = Integer.parseInt(temp.getDimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setDimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizedimensions(i, 0, numDims);
        }
      }
        }
    Operation_c [] opInstances = Operation_c.OperationInstances(modelRoot, null, false);
    for (int j=0; j < opInstances.length; j++) {
      Operation_c temp = opInstances[j];
      if (ImportInteger.isInt(temp.getReturn_dimensions())) {
        int numDims = Integer.parseInt(temp.getReturn_dimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setReturn_dimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizereturn_dimensions(i, 0, numDims);
        }
      }
        }
    OperationParameter_c [] oaInstances = OperationParameter_c.OperationParameterInstances(modelRoot, null, false);
    for (int j=0; j < oaInstances.length; j++) {
      OperationParameter_c temp = oaInstances[j];
      if (ImportInteger.isInt(temp.getDimensions())) {
        int numDims = Integer.parseInt(temp.getDimensions());
        String dimensionString = "";
        for (int i = 0; i < numDims; i++) {
          dimensionString += "[]";
        }
        temp.setDimensions(dimensionString);
        for (int i = 0; i < numDims; i++) {
          temp.Resizedimensions(i, 0, numDims);
        }
      }
        }
    }
    
    static List<C_SF> upgradedSatisfactions = new ArrayList<C_SF>();
    
    public void addIdentifierForPre715Satisfactions(ModelRoot modelRoot) {
        C_SF[] storedSats = localSatisfactionInstances.toArray(new C_SF[localSatisfactionInstances.size()]);
        if(storedSats.length == 0) {
            storedSats = satisfactionInstances.toArray(new C_SF[satisfactionInstances.size()]);
            addIdentifierForPre715Satisfactions(modelRoot, storedSats);
        } else {
            addIdentifierForPre715Satisfactions(modelRoot, storedSats);
        }
    }
    
    public void addIdentifierForPre715Satisfactions(ModelRoot modelRoot, C_SF[] storedSats) {
        for(int i = 0; i < storedSats.length; i++) {
            // if this satisfaction has already been upgraded
            // don't handle it
            if(upgradedSatisfactions.contains(storedSats[i])) continue;
            // all C_SF instances stored have null instances
            // this function is responsible for setting to
            // a non null value
            String satID = UUID.randomUUID().toString();
            Satisfaction_c instance = new Satisfaction_c(modelRoot, IdAssigner
                    .createUUIDFromString(satID),
                    storedSats[i].fRequirement_ID, storedSats[i].fProvision_ID,
                    "", "");
            storedSats[i].fInst = instance;
            upgradedSatisfactions.add(storedSats[i]);
        }
    }
    
    public class SICP {
        PA_SICP fSicp;
        C_SF fSat;

        public SICP(PA_SICP sicp, C_SF sat) {
            this.fSicp = sicp;
            this.fSat = sat;
        }
    }
    
    public class SIC {
        PA_SIC fSic;
        C_SF fSat;

        public SIC(PA_SIC sic, C_SF sat) {
            this.fSic = sic;
            this.fSat = sat;
        }
    }
    
    /**
     *   The goal of this method is to perform the following
     *   
     *   * Upgrade satisfactions to have their own unique id
     *   * Associate the new satisfactions with CL_IR instances
     *   * Associate the new satisfactions with PA_SIC instances
     *   * Associate the new satisfactions with PA_SICP instances
     * @param modelRoot
     */
    public void associateSatisfactionsThroughIdentifier(ModelRoot modelRoot) {
        // first add an identifier for every satisfaction
        addIdentifierForPre715Satisfactions(modelRoot);
        CL_IPINS[] ipinss = old_imported_provision_satisfactions
                .toArray(new CL_IPINS[old_imported_provision_satisfactions
                        .size()]);
        for (int i = 0; i < ipinss.length; i++) {
            SICP sicp = getOldSatisfactionInComponentPackage(modelRoot,
                    ipinss[i].fProvision_ID, ipinss[i].fRequirement_ID);
            if (sicp != null) {
                ImportedProvisionInSatisfaction_c importProSat = new ImportedProvisionInSatisfaction_c(
                        modelRoot, sicp.fSat.fInst.getId(), ipinss[i].fImportedProvision_ID);
                importProSat.batchRelate(modelRoot, false, false);
                setupMatchingImportedRequirements(modelRoot, ipinss[i].fRequirement_ID, sicp.fSat.fInst.getId());
            } else {
                SIC sic = getOldSatisfactionInComponent(modelRoot,
                        ipinss[i].fProvision_ID, ipinss[i].fRequirement_ID);
                if (sic != null) {
                    setupSatisfactionInComponent(modelRoot, sic);
                    ImportedProvisionInSatisfaction_c importProSat = new ImportedProvisionInSatisfaction_c(
                            modelRoot, ipinss[i].fImportedProvision_ID, sic.fSat.fInst
                                    .getId());
                    importProSat.batchRelate(modelRoot, false, false);
                    setupMatchingImportedRequirements(modelRoot, ipinss[i].fRequirement_ID, sicp.fSat.fInst.getId());
                }
            }
        }

        // for every imported requirement locate a satisfaction
        // that works
        CL_IR[] irs = old_imported_requirements.toArray(new CL_IR[old_imported_requirements.size()]);
        for(int i = 0; i < irs.length; i++) {
            SICP sicp = getOldSatisfactionInComponentPackage(modelRoot, irs[i].fProvision_ID, irs[i].fRequirement_ID);
            if(sicp != null) {
                ImportedRequirement_c importReqInst = new ImportedRequirement_c(
                            modelRoot, irs[i].fImportedReference_ID, sicp.fSat.fInst
                                    .getId(), irs[i].fName, irs[i].fDescrip);
                    importReqInst.batchRelate(modelRoot, false, false);
            } else {
                SIC sic = getOldSatisfactionInComponent(modelRoot, irs[i].fProvision_ID, irs[i].fRequirement_ID);
                if(sic != null) {
                    setupSatisfactionInComponent(modelRoot, sic);
                    ImportedRequirement_c importReqInst = new ImportedRequirement_c(
                            modelRoot, irs[i].fImportedReference_ID, sic.fSat.fInst.getId(), irs[i].fName, irs[i].fDescrip);
                    importReqInst.batchRelate(modelRoot, false, false);
                } else {
                    // there are no satisfactions, just create the
                    // imported requirement
                    ImportedRequirement_c importReqInst = new ImportedRequirement_c(
                            modelRoot, irs[i].fImportedReference_ID, IdAssigner.NULL_UUID, irs[i].fName, irs[i].fDescrip);
                    importReqInst.batchRelate(modelRoot, false, false);                 
                }
            }
        }
        // for the remaining satisfactions create and relate the
        // necessary satisfaction in component or component package
        PA_SICP[] sicps = old_satisfactions_in_comppackage
            .toArray(new PA_SICP[old_satisfactions_in_comppackage.size()]);
        for(int i = 0; i < sicps.length; i++) {
            SICP sicp = getOldSatisfactionInComponentPackage(modelRoot,
                    sicps[i].fProvision_ID,
                    sicps[i].fRequirement_ID);
            if(sicp != null) {
                localSatisfactionInstances.remove(sicp.fSat);
            } 
        }
        PA_SIC[] sics = old_satisfactions_in_comp
                .toArray(new PA_SIC[old_satisfactions_in_comp.size()]);
        for(int i = 0; i < sics.length; i++) {
                SIC sic = getOldSatisfactionInComponent(modelRoot, sics[i].fProvision_ID, sics[i].fRequirement_ID);
                if(sic != null) {
                    setupSatisfactionInComponent(modelRoot, sic);
                    localSatisfactionInstances.remove(sic.fSat);
                }
        }
    }
    
    private void setupSatisfactionInComponent(ModelRoot modelRoot, SIC sic) {
        SatisfactionInComponent_c sicInst = new SatisfactionInComponent_c(
                modelRoot, sic.fSic.fComponent_ID, sic.fSat.fInst
                        .getId());
        Satisfaction_c sat = sic.fSat.fInst;
        sat.relateAcrossR9000To(sicInst);
        sat.batchRelate(modelRoot, false, false);
        sicInst.batchRelate(modelRoot, false, false);
    }

    private void setupMatchingImportedRequirements(ModelRoot modelRoot, UUID requirement_id, UUID satisfaction_id) {
        // check for any satisfactions where the provision id
        // matches this satisfactions
        CL_IR[] irs = old_imported_requirements
                .toArray(new CL_IR[old_imported_requirements.size()]);
        for(int j = 0; j < irs.length; j++) {
            if(irs[j].fRequirement_ID.equals(requirement_id)) {
                ImportedRequirement_c importReqInst = new ImportedRequirement_c(
                        modelRoot, irs[j].fImportedReference_ID, satisfaction_id, irs[j].fName, irs[j].fDescrip);
                importReqInst.batchRelate(modelRoot, false, false);
                old_imported_requirements.remove(irs[j]);
            }
        }       
    }

    private C_SF createSatisfaction(ModelRoot modelRoot, UUID requirement_id, UUID provision_id) {
        // a satisfaction did not exist, this was a bug
        // in pre-1.5.1 versions.
        // create a new satisfaction
        String satID = UUID.randomUUID().toString();
        Satisfaction_c instance = new Satisfaction_c(modelRoot, IdAssigner
                .createUUIDFromString(satID),
                requirement_id, provision_id,
        "", "");
        C_SF newCSF = new C_SF(provision_id, requirement_id, instance);
        return newCSF;
    }

    private Component_c getComponentFrom(PA_SIC sic) {
        NonRootModelElement[] elements = importer.getLoadedInstances();
        for(int i = 0; i < elements.length; i++) {
            if(elements[i] instanceof Component_c) {
                if(((Component_c)elements[i]).getId().equals(sic.fComponent_ID)) {
                    return (Component_c) elements[i];
                }
            }
        }
        return null;
    }

    private SIC getOldSatisfactionInComponent(ModelRoot modelRoot, UUID provision_id, UUID requirement_id) {
        PA_SIC[] sics = old_satisfactions_in_comp
                .toArray(new PA_SIC[old_satisfactions_in_comp.size()]);
        for (int i = -0; i < sics.length; i++) {
            if (sics[i].fProvision_ID.equals(provision_id)
                    && sics[i].fRequirement_ID.equals(requirement_id)) {
                // found a satisfaction in component package
                // now find a satisfaction
                C_SF sat = null;
                C_SF[] c_sfs = localSatisfactionInstances
                        .toArray(new C_SF[localSatisfactionInstances.size()]);
                for (int j = 0; j < c_sfs.length; j++) {
                    if (c_sfs[j].fProvision_ID.equals(provision_id)
                            && (c_sfs[j].fRequirement_ID
                                    .equals(requirement_id))) {
                        sat = c_sfs[j];
                        localSatisfactionInstances.remove(c_sfs[j]);
                        old_satisfactions_in_comp.remove(sics[i]);
                        break;
                    }
                }
                // create the satisfaction if necessary
                // see method description for more information
                if (sat == null)
                    sat = createSatisfaction(modelRoot,
                            requirement_id,
                            provision_id);
                return new SIC(sics[i], sat);
            }
        }
        return null;
    }

    private SICP getOldSatisfactionInComponentPackage(ModelRoot modelRoot, UUID provision_id, UUID requirement_id) {
        PA_SICP[] sicps = old_satisfactions_in_comppackage
                .toArray(new PA_SICP[old_satisfactions_in_comppackage.size()]);
        for(int i = - 0; i < sicps.length; i++) {
            if (sicps[i].fProvision_ID.equals(provision_id)
                    && sicps[i].fRequirement_ID.equals(requirement_id)) {
                // found a satisfaction in component package
                // now find a satisfaction
                C_SF sat = null;
                C_SF[] c_sfs = localSatisfactionInstances
                        .toArray(new C_SF[localSatisfactionInstances.size()]);
                for(int j = 0; j < c_sfs.length; j++) {
                    // first see if the satisfaction instance is associated
                    // with an imported provision that 
                    if (c_sfs[j].fProvision_ID.equals(provision_id)
                            && (c_sfs[j].fRequirement_ID
                                    .equals(requirement_id))) {
                        sat = c_sfs[j];
                        localSatisfactionInstances.remove(c_sfs[j]);
                        old_satisfactions_in_comppackage.remove(sicps[i]);
                        break;
                    }
                }
                // create the satisfaction if necessary
                // see method description for more information
                if (sat == null)
                    sat = createSatisfaction(modelRoot,
                            requirement_id,
                            provision_id);
                return new SICP(sicps[i], sat);
            }
        }
        return null;
    }
    public List<NonRootModelElement> upgradeTransitions(List<NonRootModelElement> loadedInstances) {
    	List<NonRootModelElement> createdElements = new ArrayList<NonRootModelElement>();
    	for(NonRootModelElement element : loadedInstances) {
    		if(element instanceof Transition_c) {
				Transition_c trans = (Transition_c) element;
				ModelRoot modelRoot = trans.getModelRoot();
				TransitionActionHome_c tah = TransitionActionHome_c
						.getOneSM_TAHOnR530(trans);
				if (tah == null) {
					// There is no transition action data, silently upgrade it
					StateMachine_c sm = StateMachine_c.getOneSM_SMOnR505(trans);
					Action_c act = new Action_c(modelRoot);
					createdElements.add(act);
					act.relateAcrossR515To(sm, false);
					ActionHome_c ah = new ActionHome_c(modelRoot);
					createdElements.add(ah);
					ah.relateAcrossR514To(act, false);
					act.setSuc_pars(Parsestatus_c.parseInitial);
					tah = new TransitionActionHome_c(modelRoot);
					createdElements.add(tah);
					tah.relateAcrossR513To(ah, false);
					tah.relateAcrossR530To(trans, false);
				}
    		}
    	}
    	return createdElements;
    }

    public void upgradeEventData(Ooaofooa modelRoot,
                                                List<NonRootModelElement> modelElems) {
        for ( NonRootModelElement modelElem : modelElems ) {
          if ( modelElem instanceof StateMachine_c ) {
          StateMachine_c sm = (StateMachine_c)modelElem;
          StateMachineEventDataItem_c [] smEvtDIs =
                         StateMachineEventDataItem_c.getManySM_EVTDIsOnR516(sm);
          StateMachineEvent_c unassignedDataEvent = null;
          for (int j = 0; j < smEvtDIs.length; j++) {
            // for all data items
            StateMachineEventDataItem_c evDi = smEvtDIs[j];
            // get all the events that carry this data item
            StateMachineEvent_c [] events = StateMachineEvent_c.
                       getManySM_EVTsOnR520(EventSupplementalData_c.
                             getManySM_SUPDTsOnR522(SupplementalDataItems_c.
                                                   getManySM_SDIsOnR522(evDi)));
            if (events.length > 0 ) {
              for (int k = 0; k < events.length; k++) {
                // for all events carried
                StateMachineEvent_c event = events[k];
                if (k < events.length - 1) {
                  // create a new clone data item
                  StateMachineEventDataItem_c clone =
                                     new StateMachineEventDataItem_c(modelRoot);
                  // clone the data item
                  clone.setName(evDi.getName());
                  clone.setDescrip(evDi.getDescrip());
                  clone.setDimensions(evDi.getDimensions());
                  DataType_c dt = DataType_c.getOneS_DTOnR524(evDi);
                  clone.relateAcrossR524To(dt);
                  clone.relateAcrossR516To(sm);
                  Dimensions_c [] dims = Dimensions_c.getManyS_DIMsOnR531(
                                                                   evDi, false);
                  for (int m = 0; m < dims.length; m++) {
                    Dimensions_c dimClone = new Dimensions_c(modelRoot);
                    dimClone.setDimensioncount(dims[m].getDimensioncount());
                    dimClone.setElementcount(dims[m].getElementcount());
                    clone.relateAcrossR531To(dimClone);
                  }
                  EventArgument_c [] eas =
                                    EventArgument_c.getManyMSG_EAsOnR1017(evDi,
                                                                         false);
                  for (int m = 0; m < eas.length; m++) {
                    evDi.unrelateAcrossR1017From(eas[m]);
                    clone.relateAcrossR1017To(eas[m]);
                  }
                  // and finally relate it to the event
                  clone.relateAcrossR532To(event);
                }
                else { // k == events.length -1
                  // this is the last event to be iterated,
                  // reuse the existing event data item,
                  evDi.relateAcrossR532To(event);
                }
                // note: to ensure all data is available to all referring
                // events, related SM_SDI and SM_SUPDT instances are not
                // disposed in the loop. Disposal is performed after all
                // data upgrade is complete.
                // See below under 'Remove all the legacy instances'.
              } // end for each referring event loop
            }
            else { // no referring events across deprecated traversal
              if (StateMachineEvent_c.getOneSM_EVTOnR532(evDi) == null) {
                // No owning event found across new traversal, so
                // this data item was not previously upgraded and is
                // currently unassigned. Attach it to a placeholder event 
                if (unassignedDataEvent == null) {
                  // create and initialize new event
                  // note: Initialize() performs SEM entry initialization
                  unassignedDataEvent = new StateMachineEvent_c(modelRoot);
                  sm.relateAcrossR502To(unassignedDataEvent);
                  unassignedDataEvent.Initialize();
                  unassignedDataEvent.
                                   setMning("Unassigned Parameter Placeholder");
                }
                evDi.relateAcrossR532To(unassignedDataEvent);
              }
            }
          }
          // Remove all the legacy instances
          EventSupplementalData_c [] evSupData = EventSupplementalData_c.
                                                     getManySM_SUPDTsOnR523(sm);
          for (int j = 0; j < evSupData.length; j++) {
            // Deprecated SM_SDI disposal is taken care
            // of by the SM_SUPDT disposals.
            evSupData[j].Dispose();
          }
        } // end if instanceof
        } // end for each state machine
    }
    
    public void upgradeElementOrder( List<NonRootModelElement> modelElems ) {
        for ( NonRootModelElement elem : modelElems ) {
            if ( elem instanceof Interface_c ) {
                // Owns C_AS and C_IO
                Interface_c intr = (Interface_c)elem;
                intr.Initializeorder();
            }

            else if ( elem instanceof ExecutableProperty_c ) {
                // Owns C_PP
                ExecutableProperty_c exP = (ExecutableProperty_c)elem;
                exP.Initializeorder();
            }
        
            else if ( elem instanceof Operation_c ) {
                // Owns O_TPARM
                Operation_c op = (Operation_c)elem;
                op.Initializeorder();
            }
        
            else if ( elem instanceof ModelClass_c ) {
                // Owns Operation
                ModelClass_c mc = (ModelClass_c)elem;
                mc.Initializeorder();
            }
        
            else if ( elem instanceof StateMachineEvent_c ) {
                // Owns SM_EVTDI
                StateMachineEvent_c sme = (StateMachineEvent_c)elem;
                sme.Initializeorder();
            }
        
            else if ( elem instanceof EnumerationDataType_c ) {
                // Owns S_ENUM
                EnumerationDataType_c edt = (EnumerationDataType_c)elem;
                edt.Initializeorder();
            }
        
            else if ( elem instanceof Function_c ) {
                // Owns S_SPARM
                Function_c func = (Function_c)elem;
                func.Initializeorder();
            }
        
            else if ( elem instanceof Bridge_c ) {
                // Owns S_BPARM
                Bridge_c brg = (Bridge_c)elem;
                brg.Initializeorder();
            }
        }
    }
    
    public List<NonRootModelElement> upgradeDatatypes(List<NonRootModelElement> loadedInstances) {
		ArrayList<NonRootModelElement> createdPes = new ArrayList<NonRootModelElement>();
		for (NonRootModelElement element : loadedInstances) {
			if (element instanceof DataType_c) {
				DataType_c dt = (DataType_c) element;
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(dt);
				if (pe == null) {
					pe = new PackageableElement_c(dt.getModelRoot(), dt
							.getDt_id(), Visibility_c.Public, Gd_c
							.Null_unique_id(), Gd_c.Null_unique_id(),
							Elementtypeconstants_c.DATATYPE);
					pe.relateAcrossR8001To(dt);
					createdPes.add(pe);
				}
			}
		}
		return createdPes;
    }
    
    public void upgradeGraphicalAnchors(List<NonRootModelElement> loadedGraphicalInstances) {
        for(NonRootModelElement graphicalElement : loadedGraphicalInstances) {
            // use the Connector_c instances as a starting point
            if(graphicalElement instanceof Connector_c) {
                // if this connector is considered a graphical anchor
                // host, then check to make sure it has no target (must be
                // white space
                Connector_c connector = (Connector_c) graphicalElement;
                ElementSpecification_c spec = ElementSpecification_c
                        .getOneGD_ESOnR10(GraphicalElement_c
                                .getOneGD_GEOnR2(connector));
                if(spec != null && spec.getIsanchorhost()) {
                    Graphconnector_c anchor = Graphconnector_c
                            .getOneDIM_CONOnR321(Graphedge_c
                                    .getOneDIM_EDOnR20(connector));
                    if(anchor != null) {
                        Connector_c sourceConnector = Connector_c
                                .getOneGD_CONOnR2(GraphicalElement_c
                                        .getOneGD_GEOnR23(Graphelement_c
                                                .getOneDIM_GEOnR311(anchor)));
                        if(sourceConnector != null)
                            // we need to switch this with the non anchor host
                            CreateConnectionCommand.moveGraphicalConnectors(sourceConnector, connector);
                    }
                }
            }
        }
    }
    
    /**
     * Upgrade messages to indicate whether or not they are participating in
     * a communication under a package.
     * 
     * @param loadedGraphicalInstances
     */
    public void configureMessages(List<NonRootModelElement> loadedGraphicalInstances) {
    	for(NonRootModelElement element : loadedGraphicalInstances) {
    		if(element instanceof Connector_c) {
    			Graphedge_c edge = Graphedge_c.getOneDIM_EDOnR20((Connector_c) element);
				Graphconnector_c startCon = Graphconnector_c
						.getOneDIM_CONOnR320(edge);
				Graphconnector_c endCon = Graphconnector_c
						.getOneDIM_CONOnR321(edge);
				if(startCon == null && endCon == null) {
					// this connector could be a message participating
					// in a communication
					GraphicalElement_c gElem = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) element);
					if(gElem.getRepresents() == null) {
						gElem.setRepresents(Cl_c.Getinstancefromooa_id(Ooaofooa
								.getInstance(gElem.getModelRoot().getId()),
								gElem.getOoa_id(), gElem.getOoa_type()));
					}
					if(gElem.getRepresents() instanceof SynchronousMessage_c) {
						SynchronousMessage_c syncMessage = (SynchronousMessage_c) gElem.getRepresents();
						Message_c message = Message_c.getOneMSG_MOnR1018(syncMessage);
						message.setParticipatesincommunication(true);
					}
					if(gElem.getRepresents() instanceof AsynchronousMessage_c) {
						AsynchronousMessage_c asyncMessage = (AsynchronousMessage_c) gElem.getRepresents();
						Message_c message = Message_c.getOneMSG_MOnR1018(asyncMessage);
						message.setParticipatesincommunication(true);
					}
				}
    		}
    	}
    }
    
    public List<NonRootModelElement> upgradeSystemLevelPackages(List<NonRootModelElement> loadedInstances) {
		ArrayList<NonRootModelElement> createdPes = new ArrayList<NonRootModelElement>();
		for (Iterator<NonRootModelElement> iterator = loadedInstances
				.iterator(); iterator.hasNext();) {
			Object next = iterator.next();
			if (next instanceof Package_c) {
				Package_c pkg = (Package_c) next;
				// if during import, the system will not be accessible until pastePackage
				// is called, instead check for a non-empty direct sys id
				if (!pkg.getDirect_sys_idCachedValue().equals(Gd_c.Null_unique_id())) {
					// this is a package at the system root
					// create the necessary PE_PE if it does
					// not already exist
					PackageableElement_c pe = PackageableElement_c
							.getOnePE_PEOnR8001(pkg);
					if (pe == null) {
						pe = new PackageableElement_c(pkg.getModelRoot(), pkg.getPackage_id(),
								Visibility_c.Public, Gd_c.Null_unique_id(),
								Gd_c.Null_unique_id(),
								Elementtypeconstants_c.PACKAGE);
						pe.relateAcrossR8001To(pkg);
						createdPes.add(pe);
					}
				}
			}
		}
		return createdPes;
    }
    
    public void removeSupertypePackageableElement(List<NonRootModelElement> loadedInstances) {
    	// The elements below were incorrectly added as subtypes to PE
    	// this method will clear those instances
    	for(NonRootModelElement element : loadedInstances) {
			if (element instanceof InterfaceReference_c
					|| element instanceof TimingMark_c
					|| element instanceof TimeSpan_c) {
				PackageableElement_c pe = (PackageableElement_c) element
						.getModelRoot().getInstanceList(
								PackageableElement_c.class).get(
								element.getInstanceKey());
				if(pe != null) {
					pe.Dispose();
				}
    		}
    	}
    }
    
    /**
     * This method will upgrade Messages in sequences, and Use Case Associations
     * so that they are contained through the diagram element rather than the
     * target they are connected to.
     * 
     */
    public List<NonRootModelElement> addLinkClassesForContainment(List<NonRootModelElement> loadedInstances) {
    	// if the element already has a link class, or has a PE then do not
    	// upgrade
    	List<NonRootModelElement> newElements = new ArrayList<NonRootModelElement>();
		for (NonRootModelElement element : loadedInstances) {
			if (element instanceof Message_c) {
				Message_c message = (Message_c) element;
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(message);
				if (pe != null) {
					continue;
				}
				// see where the message is
			}
		}
		return newElements;
    }

	public void setUpGlobals(SystemModel_c system) {
	  GlobalElementInSystem_c gis =
                             GlobalElementInSystem_c.getOneG_EISOnR9100(system);
	  if (gis == null) {  // Only set up globals once
		ModelRoot root = system.getModelRoot();
		// This relies on the fact that only global Packageable Elements are
		// found in the default model root.
		InstanceList peList = root.getInstanceList(PackageableElement_c.class);
		java.util.ListIterator<NonRootModelElement> it = peList.listIterator();
		while (it.hasNext()) {
			gis = new GlobalElementInSystem_c(root);
			gis.relateAcrossR9100To((PackageableElement_c)it.next());
			gis.relateAcrossR9100To(system);
		}
	  }
	}
	
	public void fixSatisfactionAssociations(
			List<NonRootModelElement> loadedElements) {
		for (NonRootModelElement element : loadedElements) {
			if (element instanceof Satisfaction_c) {
				Satisfaction_c satisfaction = (Satisfaction_c) element;
				PackageableElement_c pe = PackageableElement_c
						.getOnePE_PEOnR8001(satisfaction);
				if (pe != null) {

					// if dealing with an imported reference then
					// we need to assure that the package containing
					// that reference is the container of the satisfaction
					Package_c originalPKG = Package_c.getOneEP_PKGOnR8000(pe);
					Component_c originalCOMP = Component_c.getOneC_COnR8003(pe);
					ImportedProvision_c pro = ImportedProvision_c
							.getOneCL_IPOnR4705(ImportedProvisionInSatisfaction_c
									.getManyCL_IPINSsOnR4705(satisfaction));
					ImportedRequirement_c req = ImportedRequirement_c
							.getOneCL_IROnR4706(satisfaction);
					Package_c targetPKG = null;
					Component_c targetCOMP = null;
					if (pro != null) {
						targetPKG = Package_c
								.getOneEP_PKGOnR8000(PackageableElement_c
										.getManyPE_PEsOnR8001(ComponentReference_c
												.getManyCL_ICsOnR4707(PortReference_c
														.getManyCL_PORsOnR4708(ImportedReference_c
																.getManyCL_IIRsOnR4703(pro)))));
						targetCOMP = Component_c
								.getOneC_COnR8003(PackageableElement_c
										.getManyPE_PEsOnR8001(ComponentReference_c
												.getManyCL_ICsOnR4707(PortReference_c
														.getManyCL_PORsOnR4708(ImportedReference_c
																.getManyCL_IIRsOnR4703(pro)))));
					} else if (req != null) {
						targetPKG = Package_c
								.getOneEP_PKGOnR8000(PackageableElement_c
										.getManyPE_PEsOnR8001(ComponentReference_c
												.getManyCL_ICsOnR4707(PortReference_c
														.getManyCL_PORsOnR4708(ImportedReference_c
																.getManyCL_IIRsOnR4703(req)))));

						targetCOMP = Component_c
								.getOneC_COnR8003(PackageableElement_c
										.getManyPE_PEsOnR8001(ComponentReference_c
												.getManyCL_ICsOnR4707(PortReference_c
														.getManyCL_PORsOnR4708(ImportedReference_c
																.getManyCL_IIRsOnR4703(req)))));
					}

					boolean disconnectedOld = false;
					if (targetPKG != null || targetCOMP != null) {
						if (targetPKG != originalPKG && originalPKG != null) {
							pe.unrelateAcrossR8000From(originalPKG);
							disconnectedOld = true;
						}
	
						if (targetCOMP != originalCOMP && originalCOMP != null) {
							pe.unrelateAcrossR8003From(originalCOMP);
							disconnectedOld = true;
						}
					}

					if (disconnectedOld) {
						satisfaction.setComponent(null);
						if (targetPKG != null) {
							pe.relateAcrossR8000To(targetPKG);
						} else if (targetCOMP != null) {
							pe.relateAcrossR8003To(targetCOMP);
						}
					}
				}
			}
		}
	}
	
	/** 
	 * Older models had S_CDT proxies due to the old R18 association, now that
	 * the proxy should be an S_DT lazy loading is broken.  This method enforces
	 * the loading so that S_UDTs are properly hooked up.
	 * 
	 * @param loadedElements
	 */
	public void loadDataTypesFromCoreTypeProxy(List<NonRootModelElement> loadedElements) {
		// for every S_CDT
		for(NonRootModelElement element : loadedElements) {
			if(element instanceof CoreDataType_c) {
				// that is a proxy
				if(element.isProxy()) {
					// load the proxies file
					element.loadProxy();
				}
			}
		}
		// in some cases the loading of the S_DTs will have already occurred but
		// after the UDT is left as orphaned, here we scan for orphaned UDTs and
		// call batchRelate after all S_DTs are guaranteed to be loaded
		for(NonRootModelElement element : loadedElements) {
			if(element instanceof UserDataType_c) {
				DataType_c dt = DataType_c.getOneS_DTOnR18((UserDataType_c) element);
				if(dt == null) {
					element.batchRelate(element.getModelRoot(), true, true);
				}
			}
		}
		
	}

	public List<ExternalEntity_c> eesToUpgradeForIsRealized = new ArrayList<ExternalEntity_c>();

	public void addEEToUpgradeForIsRealized(ExternalEntity_c ee) {
		eesToUpgradeForIsRealized.add(ee);
	}

	public void upgradeEEsForIsRealized() {
		for (ExternalEntity_c ee : eesToUpgradeForIsRealized) {
			boolean isRealized = true;
			Bridge_c[] bridges = Bridge_c.getManyS_BRGsOnR19(ee);
			for (Bridge_c bridge : bridges) {
				// if we find any bridge that has action semantics
				// and is not set to do not parse, consider this
				// EE as non-realized
				if ((!bridge.getAction_semantics_internal().equals("") && bridge
						.getSuc_pars() != Parsestatus_c.doNotParse)
						&& (ee.getRealized_class_path().equals(""))) {
					isRealized = false;
					break;
				}
			}
			ee.setIsrealized(isRealized);
		}
	}
	public void addPortReferenceInstances(
			List<NonRootModelElement> loadedInstances) {

		for (NonRootModelElement impRef : loadedInstances) {
			if ((impRef instanceof ImportedReference_c) && (!impRef.isProxy())) {
				ImportedReference_c importedRef = (ImportedReference_c) impRef;
				PortReference_c prtRef = PortReference_c
						.getOneCL_POROnR4708(importedRef);
				if (prtRef == null) {

					ComponentReference_c cmpRef = (ComponentReference_c) impRef
							.getModelRoot()
							.getInstanceList(ComponentReference_c.class)
							.getGlobal(null,
									importedRef.getCl_por_idCachedValue());
					if (cmpRef == null) {
						InstanceList cmpReflist = impRef.getModelRoot()
								.getInstanceList(ComponentReference_c.class);
						for (NonRootModelElement elm : cmpReflist) {
							ComponentReference_c crf = (ComponentReference_c) elm;
							if (crf.getId() == importedRef
									.getCl_por_idCachedValue()) {
								cmpRef = crf;
								break;
							}
						}

					}
		 
					 
						prtRef = new PortReference_c(
								Ooaofooa.getDefaultInstance());
						Ooaofooa.getDefaultInstance().fireModelElementCreated(new BaseModelDelta(Modeleventnotification_c.DELTA_NEW,prtRef));
						prtRef.relateAcrossR4707To(cmpRef);

						InterfaceReference_c ifcRef = InterfaceReference_c
								.getOneC_IROnR4701(importedRef);
						Port_c port = Port_c.getOneC_POOnR4016(ifcRef);
						prtRef.relateAcrossR4709To(port);

						prtRef.relateAcrossR4708To(importedRef);
 
				}

			}

		}

	}
}
