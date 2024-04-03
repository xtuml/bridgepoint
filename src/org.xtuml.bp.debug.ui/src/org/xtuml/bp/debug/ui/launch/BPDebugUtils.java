package org.xtuml.bp.debug.ui.launch;

//====================================================================
//
// File:      $RCSfile: BPDebugUtils.java,v $
// Version:   $Revision: 1.23 $
// Modified:  $Date: 2013/01/10 23:17:47 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.ui.IDebugView;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.ui.preferences.BridgePointPersistencePreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.debug.ui.IBPDebugUIPluginConstants;
import org.xtuml.bp.debug.ui.actions.ExecuteAction;
import org.xtuml.bp.debug.ui.model.BPDebugTarget;
import org.xtuml.bp.debug.ui.model.BPThread;
import org.xtuml.bp.ui.session.views.SessionExplorerView;

public class BPDebugUtils {

	public static String getModelId(IFile file) {
		PersistableModelComponent component = PersistenceManager
				.findComponent(file.getFullPath());
		if (component != null) {
			NonRootModelElement rootElement = component.getRootModelElement();
			if (rootElement instanceof Component_c) {
				return ((Component_c) rootElement).getId().toString();
			} else if (rootElement instanceof Package_c) {
        return ((Package_c) rootElement).getPackage_id().toString();
      }
		}

		return null;
	}


	/**
	 * 
	 * @param idOrPath The UUID or model path of the model element to find.
	 * @return NonRootModelElement associated with the given ID
	 */
	public static NonRootModelElement getVerifiableElement(String idOrPath) {
		// look for a component first
		Optional<NonRootModelElement> result = Stream.of(Ooaofooa.getInstances())
				.flatMap(root -> root.getInstanceList(Component_c.class).stream())
				.filter(nrme -> elementMatchesIdOrPath(nrme, idOrPath)).findAny();

		// if found none, search for component references
		if (result.isEmpty()) {
			result = Stream.of(Ooaofooa.getInstances())
					.flatMap(root -> root.getInstanceList(ComponentReference_c.class).stream())
					.filter(nrme -> elementMatchesIdOrPath(nrme, idOrPath)).findAny();
		}

		return result.orElse(null);
	}
	
	public static List<NonRootModelElement> getVerifiableElements(final String[] idsOrPaths) {
		return Stream.of(idsOrPaths).map(BPDebugUtils::getVerifiableElement).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public static NonRootModelElement[] getPackageChildren(
			Package_c package_c) {
		List<NonRootModelElement> models = new ArrayList<NonRootModelElement>();
		Package_c[] childPackages = Package_c
				.getManyEP_PKGsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(package_c));
		for (int i = 0; i < childPackages.length; i++) {
			NonRootModelElement[] children = getPackageChildren(childPackages[i]);
			for (int j = 0; j < children.length; j++) {
				models.add(children[j]);
			}
		}
		Component_c[] components = Component_c.getManyC_CsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(package_c));
		for (int i = 0; i < components.length; i++) {
			NonRootModelElement[] children = getComponentChildren(components[i]);
			for (int j = 0; j < children.length; j++) {
				models.add(children[j]);
			}
			models.add(components[i]);
		}
		ComponentReference_c[] icomponents = ComponentReference_c
				.getManyCL_ICsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(package_c));
		for (int i = 0; i < icomponents.length; i++) {
			if (icomponents[i].Isassigned())
				models.add(icomponents[i]);
		}
		return models.toArray(new NonRootModelElement[models.size()]);
	}

	public static NonRootModelElement[] getComponentChildren(
			Component_c component) {
		List<NonRootModelElement> children = new ArrayList<NonRootModelElement>();
		Component_c[] genericComponentChildren = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(component));
		for (int i = 0; i < genericComponentChildren.length; i++) {
			children.add(genericComponentChildren[i]);
			NonRootModelElement[] nextChildren = getComponentChildren(genericComponentChildren[i]);
			for (int j = 0; j < nextChildren.length; j++) {
				children.add(nextChildren[j]);
			}
		}
		ComponentReference_c[] genericImportedCompChildren = ComponentReference_c
				.getManyCL_ICsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(component));
		for (int i = 0; i < genericImportedCompChildren.length; i++) {
			if (genericImportedCompChildren[i].Isassigned())
				children.add(genericImportedCompChildren[i]);
		}
		return children.toArray(new NonRootModelElement[children.size()]);
	}

	public static String[] getSystemNamesForElements(
			NonRootModelElement[] elements) {
		List<String> systemList = new ArrayList<String>();
		for (int i = 0; i < elements.length; i++) {
			SystemModel_c system = getElementsSystem(elements[i]);
			if (!systemList.contains(system.getName()))
				systemList.add(system.getName());
		}
		return systemList.toArray(new String[systemList.size()]);
	}

	public static SystemModel_c getElementsSystem(Object element) {
		if (element instanceof SystemModel_c) {
			return (SystemModel_c) element;
		}
		SystemModel_c system = null;
		if (element instanceof Component_c) {
				return getCompOrPkgsSystem(element);
		}
		if (element instanceof ComponentReference_c) {
				return getCompOrPkgsSystem(element);
		}
		if (element instanceof Package_c) {
			system = getCompOrPkgsSystem(element);
		}
		return system;
	}

	private static SystemModel_c getCompOrPkgsSystem(Object compOrPkg) {
		if (compOrPkg instanceof Package_c) {
			SystemModel_c system = SystemModel_c
          .getOneS_SYSOnR1401((Package_c) compOrPkg);
      if (system != null) {
        return system;
      }
      Package_c parentPkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
          .getOnePE_PEOnR8001((Package_c) compOrPkg));
      if (parentPkg != null) {
        return getCompOrPkgsSystem(parentPkg);
      }
      Component_c parentComp = Component_c
          .getOneC_COnR8003(PackageableElement_c
              .getOnePE_PEOnR8001((Package_c) compOrPkg));
      if (parentComp != null) {
        return getCompOrPkgsSystem(parentComp);
      }
		}
		if (compOrPkg instanceof Component_c) {
			Component_c comp = (Component_c) compOrPkg;
			Package_c parentPkg = Package_c
					.getOneEP_PKGOnR8000(PackageableElement_c
							.getOnePE_PEOnR8001(comp));
			if (parentPkg != null) {
				return getCompOrPkgsSystem(parentPkg);
			}
			Component_c parentComp = Component_c
					.getOneC_COnR8003(PackageableElement_c
							.getOnePE_PEOnR8001(comp));
			if (parentComp != null) {
				return getCompOrPkgsSystem(parentComp);
			}
		}
		if (compOrPkg instanceof ComponentReference_c) {
			ComponentReference_c cr = (ComponentReference_c) compOrPkg;
      Package_c parentPkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c
          .getOnePE_PEOnR8001(cr));
      if (parentPkg != null) {
        return getCompOrPkgsSystem(parentPkg);
      }
      Component_c parentComp = Component_c
          .getOneC_COnR8003(PackageableElement_c.getOnePE_PEOnR8001(cr));
      if (parentComp != null) {
        return getCompOrPkgsSystem(parentComp);
      }
		}
		// FIXME log error here
		return null;
	}
	
	public static SessionExplorerView openSessionExplorerView(
			final boolean showView) {

		IViewPart tempView = null;
		try {
			IWorkbenchPage dbgPage = PlatformUI.getWorkbench().showPerspective(
					IBPDebugUIPluginConstants.BPPERSPECTIVE_ID,
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());

			if (dbgPage != null) {
				if (showView) {
					tempView = dbgPage
							.showView(IBPDebugUIPluginConstants.ID_VIEW_INSTANCE);
				} else {
					tempView = dbgPage
							.findView(IBPDebugUIPluginConstants.ID_VIEW_INSTANCE);
				}
			}

		} catch (WorkbenchException e) {
		}

		if (tempView instanceof SessionExplorerView) {
			return (SessionExplorerView) tempView;
		}

		return null;
	}

	public static IDebugView openDebugView(
			final boolean showView) {

		IViewPart tempView = null;
		try {
			IWorkbenchPage dbgPage = PlatformUI.getWorkbench().showPerspective(
					IBPDebugUIPluginConstants.BPPERSPECTIVE_ID,
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());

			if (dbgPage != null) {
				if (showView) {
					tempView = dbgPage
							.showView(IBPDebugUIPluginConstants.ID_VIEW_DEBUG);
				} else {
					tempView = dbgPage
							.findView(IBPDebugUIPluginConstants.ID_VIEW_DEBUG);
				}
			}

		} catch (WorkbenchException e) {
		}

		if (tempView instanceof IDebugView) {
			return (IDebugView) tempView;
		}

		return null;
	}
    public static IDebugTarget getTargetForEngine(ComponentInstance_c engine) {
			IDebugTarget[] targets = BPDebugTarget.getTargets().toArray(new IDebugTarget[0]);
			for (int k = 0; k < targets.length; k++){
			  IDebugTarget target = targets[k];
			if (target instanceof BPDebugTarget) {
				BPDebugTarget bpTarget = (BPDebugTarget) target;
				try {
					IThread[] threads = bpTarget.getThreads();
					for (int j = 0; j < threads.length; j++) {
						if (threads[j] instanceof BPThread) {
							if (((BPThread) threads[j]).getEngine() == engine) {
								return bpTarget;
							}
						}
					}
				} catch (DebugException e) {
					CorePlugin.logError("Exception getting thread list in getTargetForEngine:", e);
				}
			}
		}
		return null;
    }
    
    public static void executeElement(NonRootModelElement element) {
    	openSessionExplorerView(true);
    	ExecuteAction action = new ExecuteAction();
    	action.setOALElement(element);
    	action.run(null);
    	UIUtil.dispatchAll();
    }
    
	public static boolean elementMatchesIdOrPath(NonRootModelElement nrme, String idOrPath) {
		return idOrPath.startsWith(nrme.Get_ooa_id().toString()) || idOrPath.startsWith(nrme.getPath());
	}
	
	public static String getElementIdentifier(NonRootModelElement nrme) {
		final Preferences projectPrefs = new ProjectScope(nrme.getPersistableComponent().getFile().getProject())
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		if (projectPrefs.get(BridgePointPersistencePreferences.BP_PERSISTENCE_MODE_ID, "null").equals("text")) {
			return nrme.getPath();
		} else {
			return nrme.Get_ooa_id().toString();
		}
	}

}
