//====================================================================
//
// File:      $RCSfile: CoreExport.java,v $
// Version:   $Revision: 1.28.14.1 $
// Modified:  $Date: 2013/07/26 10:13:23 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.io.core;
 
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.als.oal.ParserAllActivityModifier;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SpecificationPackage_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

public abstract class CoreExport implements IRunnableWithProgress {
    protected Ooaofooa m_modelRoot = null;
    private boolean m_exportOAL = false;
    private boolean m_exportGraphics = true;
	private boolean errorLoggedDuringParse = false;
	public boolean outputCachedIDs = false;
	public static boolean forceWriteAsProxy = false;
	public static boolean ignoreMissingPMCErrors = false;

    private Ooaofgraphics m_graphicsModelRoot = null;

    /**
     * Used to specify that export option should be true.
     */
    public static String YES = "yes"; //$NON-NLS-1$
    /**
     * Used to specify that export option  should be false.
     */
    public static String NO = "no"; //$NON-NLS-1$
    /**
     * Used to specify that the export option should be set based on the
     * value in the Model Export preference page.
     */
    public static String USER_PREFERENCE = "user_preference"; //$NON-NLS-1$
    
    public static boolean ignoreAlternateChildren = false;
	public static boolean exportSupertypes = true;

    /**
     * 
     * @param modelRoot
     * @param exportOAL This is one of the "public static" stings defined in 
     *                  this class (YES, NO, USER_PERFERENCE).
     */
    public CoreExport(Ooaofooa modelRoot, String exportOAL) {
        m_modelRoot = modelRoot;
       	m_exportGraphics = true;
        setExportOAL(exportOAL);
    }

    public static final String ExportLicenseError = "Failed to get a xtumlmcexport license. Proceeding with the export, but executable instances will not be included in the export";
    /**
     * Set the flag that indicates if we should export OAL or not.
     * 
     * @param exportOAL This is one of the "public static" stings defined in 
     *                  this class (YES, NO, USER_PERFERENCE).
     *                  
     * @return true is the option to setExportOAL is set to true and false if it is set to false
     */
    public boolean setExportOAL(String exportOAL) {
    	if (YES.equals(exportOAL)) {
        	m_exportOAL = true;
    	} else if (USER_PREFERENCE.equals(exportOAL)) {
			IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
	        String option = store.getString(BridgePointPreferencesStore.EXPORT_OAL);
	        if (option.equals(MessageDialogWithToggle.ALWAYS)) {
				m_exportOAL = true;
	        } else {
	        	m_exportOAL = false;
	        }
    	} else {
        	m_exportOAL = false;
    	}
    	return m_exportOAL;
    }
    
    /**
     * Set the flag that indicates if we should export graphics or not.
     * 
     * @param exportGraphics This is one of the "public static" stings defined in 
     *                  this class (YES, NO, USER_PERFERENCE).
     */
    public void setExportGraphics(String exportGraphics) {
    	if (NO.equals(exportGraphics)) {
        	m_exportGraphics = false;
    	} else if (USER_PREFERENCE.equals(exportGraphics)) {
			IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
	        String option = store.getString(BridgePointPreferencesStore.EXPORT_GRAPHICS);
	        if (option.equals(MessageDialogWithToggle.NEVER)) {
	        	m_exportGraphics = false;
	        } else {
				m_exportGraphics = true;
	        }
    	} else {
    		m_exportGraphics = true;
    	}
    }
    
    public boolean exportOAL() {
    	return m_exportOAL;
    }
    
    public boolean exportGraphics() {
    	return m_exportGraphics;
    }
    
    protected Ooaofooa getModelRoot() {
        return m_modelRoot;
    }

    protected Ooaofgraphics getGraphicsModelRoot() {
        m_graphicsModelRoot = Ooaofgraphics.getInstance(m_modelRoot.getId());
        return m_graphicsModelRoot;
    }

    /**
     * A convenience method. See method wrapped.
     */
    protected String get_file_header(String content, String version) {
        return get_file_header(content, version, ""); //$NON-NLS-1$
    }

    protected String get_file_header(String content, String version, String persistenceVersion) {
        return "-- BP " + version + " content: " + content //$NON-NLS-1$//$NON-NLS-2$
                + " syschar: 3" + //$NON-NLS-1$
                (!persistenceVersion.equals("") ? //$NON-NLS-1$
                        (" persistence-version: " + persistenceVersion) : "") //$NON-NLS-1$//$NON-NLS-2$
                + "\n\n"; //$NON-NLS-1$
    }
    
    class UITextLogListener implements ILogListener {
        public void logging(IStatus status, String plugin) {
            errorLoggedDuringParse = true;
        }       
    }

    /**
	 * This simply calls the private version of the function with the same name.
	 * 
	 * @see parseAllForExport(final IProgressMonitor monitor, Shell dialogShell)
	 * @param monitor
	 * @throws OALParseException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public void parseAllForExport(final NonRootModelElement[] selectedElements, final IProgressMonitor monitor)
			throws InvocationTargetException,
			InterruptedException {
		parseAllForExport(selectedElements, monitor, null);
	}

	/**
	 * This simply calls the private version of the function with the same name.
	 * 
	 * @see parseAllForExport(final IProgressMonitor monitor, Shell dialogShell)
	 * @param dialogShell
	 * @throws OALParseException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public void parseAllForExport(final NonRootModelElement[] selectedElements,
			Shell dialogShell) throws InvocationTargetException,
			InterruptedException {
		parseAllForExport(selectedElements, null, dialogShell);
	}

	/**
	 * 
	 * Perform a parse all on all elements needed to assure that OAL instance
	 * data is created for all selected elements (and their containing elements).
	 * Note that is both the monitor and dialogShell parameters are null a
	 * default progress dialog (NullProgressMonitor()) will be used.
	 * 
	 * @param selectedElements The list of elements to be parsed.  
	 * @param monitor Progress monitor to update.
	 * @param dialogShell The progress monitor dialog will run under this shell.
	 * It can be null if one is not available.
	 * 
	 * @throws OALParseException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	private void parseAllForExport(final NonRootModelElement[] pSelectedElements, final IProgressMonitor monitor,
			Shell dialogShell) throws InvocationTargetException, InterruptedException {

		ILogListener ll = new UITextLogListener();
		Platform.addLogListener(ll);
		
		// Build the list of elements that may have OAL and need to be parsed
		List<NonRootModelElement> elementsToParse = getElementsToParse(pSelectedElements, true);
		    // Optimization to clear instance data prior to the parse.  
		    // The parse is much faster when there is no instance 
		    // data to have to search through.  We do this here instead of in the
		    // loop below because there may be a shared model root in the 
		    // selected elements that we parsed individually below.
		for (int i = 0; i < elementsToParse.size(); i++) {
		    ParserAllActivityModifier.disposeAllBodies(elementsToParse.get(i).getModelRoot());
	      }
		for (int i = 0; i < elementsToParse.size(); i++) {
			parseOneElement(elementsToParse.get(i), monitor, dialogShell);			
		}
		List<ModelRoot> rootsParsed = new ArrayList<ModelRoot>();
		for (int i = 0; i < elementsToParse.size(); i++) {
			// Initialize each of the bodies on R601.  Note that we are doing
			// this even if we have parse errors.  We simply warn about parse
			// errors, but proceed.
			if(!rootsParsed.contains(elementsToParse.get(i).getModelRoot())) {
				ParserAllActivityModifier.initializeAllBodies(elementsToParse.get(i));
				rootsParsed.add(elementsToParse.get(i).getModelRoot());
			}
		}


		Platform.removeLogListener(ll);
	}

	/**
	 * Parse the specified element.  
	 * 
	 * @arg The element to parse.  Currently this routine can only parse elements
	 *      of type Domain_c or Component_c
	 */
	private void parseOneElement(final NonRootModelElement selectedElement, final IProgressMonitor monitor,
			Shell dialogShell) throws InvocationTargetException, InterruptedException {
		
		final String className = selectedElement.getClass().getName();
		final String instanceName = selectedElement.getName();
		
		errorLoggedDuringParse = false;
		
		// This does mean that if the element passed-in is not one we know
		// how to parse this routine returns success
		if ((selectedElement instanceof Domain_c)
				|| (selectedElement instanceof Component_c) 
				|| (selectedElement instanceof Package_c)
				|| (selectedElement instanceof Subsystem_c)
				|| (selectedElement instanceof FunctionPackage_c)
				|| (selectedElement instanceof ExternalEntityPackage_c)) 
				{
			final NonRootModelElement nrme = selectedElement;
			IRunnableWithProgress rwp = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					if (monitor != null) {
						monitor.subTask("Parsing " + instanceName);
					}
					
					ParserAllActivityModifier aam = null;
					boolean workBenchIsRunning= PlatformUI.isWorkbenchRunning(); 
					if (nrme instanceof Domain_c) {
						if (workBenchIsRunning) {
							aam = new AllActivityModifier(
									(Domain_c) nrme, monitor);	
						} else {
							aam = new ParserAllActivityModifier(
									(Domain_c) nrme, monitor);
						}
					} else if (nrme instanceof Component_c) {
						if (workBenchIsRunning) {
							aam = new AllActivityModifier(
									(Component_c) nrme, monitor);
						} else {
							aam = new ParserAllActivityModifier(
									(Component_c) nrme, monitor);
						}
					} else if (nrme instanceof Package_c) {
						if (workBenchIsRunning) {
							aam = new AllActivityModifier(
									(Package_c) nrme, monitor);
						} else {
							aam = new ParserAllActivityModifier(
									(Package_c) nrme, monitor);
						}
					} else if (nrme instanceof Subsystem_c) {
						if (workBenchIsRunning) {
							aam = new AllActivityModifier(
									(Subsystem_c) nrme, monitor);
						} else {
							aam = new ParserAllActivityModifier(
									(Subsystem_c) nrme, monitor);
						}
					} else if (nrme instanceof FunctionPackage_c) {
						if (workBenchIsRunning) {
							aam = new AllActivityModifier(
									(FunctionPackage_c) nrme, monitor);
						} else {
							aam = new ParserAllActivityModifier(
									(FunctionPackage_c) nrme, monitor);
						}
					} else if (nrme instanceof ExternalEntityPackage_c) {
						if (workBenchIsRunning) {
							aam = new AllActivityModifier(
									(ExternalEntityPackage_c) nrme, monitor);
						} else {
							aam = new ParserAllActivityModifier(
									(ExternalEntityPackage_c) nrme, monitor);
						}
					}
						
					if (aam != null) {
						aam.processAllActivities(ParserAllActivityModifier.PARSE,false);
					}
					
					if (monitor != null) {
						monitor.done();
					}					
				}
			};
	
			// parse the element
			if (dialogShell == null) {
				if (monitor == null) {
					rwp.run(new NullProgressMonitor());
				} else {
					rwp.run(monitor);
				}
			} else {
				new ProgressMonitorDialog(dialogShell)
						.run(true, false, rwp);
			}
			
		} else {
			// Log a warning that we received an element type that this
			// routine will not parse.  This really shouldn't happen.  If it
			// does it means something is being exported that may include OAL
			// that is not being persisted in the export.
			String message = "Warning: CoreExport.parseOneElement() received an element that will not be parsed.  ";
			message += "Class: " + className + "  Instance: " + instanceName;
			CorePlugin.logError(message, null);
		}

		if (!CoreUtil.IsRunningHeadless) {
			// Make sure that all events in the asynchronous event queue
			// are dispatched. This is here is help assure that parse errors
			// are detected by the ILogListener
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					// do nothing
				}
			});
		}
		
		if (errorLoggedDuringParse) {
			String message = "Warning: Parse errors encountered prior to model export.  ";
			message += "Class: " + className + "  Instance: " + instanceName;
			CorePlugin.logError(message, null);
		}
	}
		
	/**
	 * Given a system model return the list of components and domains that 
	 * need to be parsed in our to assure everything in the system is parsed
	 * and parsed only once.
	 * 
	 */
	public static List<NonRootModelElement> getParsableElementsInSystemModel(
			SystemModel_c sys) {
		return getElementsToParse(sys, false);	
	}
	
	/**
	 * Determine which of the selected elements may have OAL 
	 * 
	 * @return A list of domains and/or components.
	 */
	public List<NonRootModelElement> getElementsToParse(NonRootModelElement[] pSelectedElements) {
		return getElementsToParse(pSelectedElements, false);
	}
	private List<NonRootModelElement> getElementsToParse(NonRootModelElement[] pSelectedElements, boolean filterForRecursion)
	{
		ArrayList<NonRootModelElement> resultList = new ArrayList<NonRootModelElement>();
		 for (int i = 0; i < pSelectedElements.length; i++) {
			 resultList.addAll( getElementsToParse(pSelectedElements[i], filterForRecursion) );		
		}
		 return resultList;
	}
	private static List<NonRootModelElement> getElementsToParse(NonRootModelElement pSelectedElement, boolean filterForRecursion) 
    {

		List<NonRootModelElement> resultList = new ArrayList<NonRootModelElement>();
		// Build the resultList from SystemModel_c (if we have
		// been given a SystemModel_c
		if (pSelectedElement instanceof SystemModel_c) {
			SystemModel_c sys = (SystemModel_c) pSelectedElement;
			Component_c[] components = Component_c
					.getManyC_CsOnR4608(ComponentPackage_c
							.getManyCP_CPsOnR4606(sys));
			Domain_c[] formalDoms = Domain_c
					.getManyS_DOMsOnR4204(DomainAsComponent_c
							.getManyCN_DCsOnR4204(components));
			Domain_c allDoms[] = Domain_c.getManyS_DOMsOnR28(sys);
			Package_c allPkgs[] = Package_c.getManyEP_PKGsOnR1405(sys);
			Component_c[] componentsUnderPackages = Component_c
			.getManyC_CsOnR8001(PackageableElement_c
					.getManyPE_PEsOnR8000(allPkgs));

			if (filterForRecursion) {
				allPkgs = Package_c.getManyEP_PKGsOnR1401(sys);
				componentsUnderPackages = new Component_c[0];
			}
			// Remove the formalized doms. these will be parsed with the
			// component
			// that is formalized to them and we don't want to parse them
			// multiple
			// times.
			for (int i = 0; i < allDoms.length; i++) {
				boolean isFormal = false;
				for (int j = 0; j < formalDoms.length; j++) {
					if (allDoms[i].getDom_id() == formalDoms[j].getDom_id()) {
						isFormal = true;
						break;
					}
				}
				if (!isFormal) {
					resultList.add(allDoms[i]);
				}
			}

			List<Component_c> compList = Arrays.asList(components);
			resultList.addAll(compList);
			List<Component_c> compListUnderPackages = Arrays
					.asList(componentsUnderPackages);
			resultList.addAll(compListUnderPackages);

			List<Package_c> pkgList = Arrays.asList(allPkgs);
			resultList.addAll(pkgList);

		}
		else {
			
		// Determine what to parse
	
			if (pSelectedElement instanceof Domain_c) {
				resultList.add((Domain_c)pSelectedElement);
			} else if (pSelectedElement instanceof Component_c) {
				resultList.add((Component_c) pSelectedElement);
			} else if (pSelectedElement instanceof ComponentPackage_c) {
				Component_c[] comps = Component_c
				.getManyC_CsOnR4608((ComponentPackage_c) pSelectedElement);
				for (int j = 0; j < comps.length; j++) {
					resultList.add(comps[j]);
				}
				
			} else if (pSelectedElement instanceof Package_c) {
				Package_c pkg = (Package_c) pSelectedElement;
				boolean isInGenericPackage = pkg.verifyPackageAsGeneric(pkg);
				if (isInGenericPackage) {
					resultList.add(pkg);
					if (!filterForRecursion) {
					  resultList.addAll(getNestedElements(pkg));
					}
				} else {
					Component_c[] comps = Component_c
							.getManyC_CsOnR4608(ComponentPackage_c
									.getManyCP_CPsOnR1402(SpecificationPackage_c
											.getManyEP_SPKGsOnR1400(pkg)));
					for (int j = 0; j < comps.length; j++) {
						resultList.add(comps[j]);
					}
				}

			} else if (pSelectedElement instanceof Subsystem_c) {
				resultList.add(pSelectedElement);
			} else if (pSelectedElement instanceof FunctionPackage_c) {
				resultList.add(pSelectedElement);
			} else if (pSelectedElement instanceof ExternalEntityPackage_c) {
				resultList.add(pSelectedElement);
			}
		}

		return resultList;
	
    }
	private static List<NonRootModelElement> getNestedElements(NonRootModelElement rootElement) {
		ArrayList<NonRootModelElement> result = new ArrayList<NonRootModelElement>();
		
		Package_c[] pkgs = null; 
		Component_c[] comps = null;
		
		if (rootElement instanceof Package_c){
			pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((Package_c)rootElement));
		    comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000((Package_c)rootElement));
		}
		else if (rootElement instanceof Component_c){
			pkgs = Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003((Component_c)rootElement));
		    comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003((Component_c)rootElement));
		}
		
		for (int i = 0; i < pkgs.length; i++) {
			result.add(pkgs[i]);
			List<NonRootModelElement> value = getNestedElements(pkgs[i]);
			result.addAll(value);
		}
		for (int i = 0; i < comps.length; i++) {
			result.add(comps[i]);
			List<NonRootModelElement> value = getNestedElements(comps[i]);
			result.addAll(value);
		}
		return result;

	}
}
