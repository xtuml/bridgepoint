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
//
package org.xtuml.bp.core.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InteractionParticipant_c;
import org.xtuml.bp.core.Message_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.ui.IModelImport;
import org.xtuml.bp.core.ui.IModelImport.IFileHeader;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import org.xtuml.bp.core.util.SupertypeSubtypeUtil;
import org.xtuml.bp.core.util.UIUtil;

public class PersistenceManager {

	private static PersistenceManager defaultInstance;
	private static IPersistenceHierarchyMetaData persistenceHierarchy;
	
	private boolean showErrorDialog = true;

	// list of component instances
	private static TreeMap<String, PersistableModelComponent> Instances = new TreeMap<String, PersistableModelComponent>();
	private static TreeMap<String, PersistableModelComponent> InconsistentInstances = new TreeMap<String, PersistableModelComponent>();
	private static Set<PersistableModelComponent> instanceSet = new HashSet<PersistableModelComponent>();
	public static List<IProject> projectsAllowedToLoad = new ArrayList<IProject>();
	public boolean doNotCreateUniqueName;
	private Executor loadExecutor;
	private SequentialExecutor sequentialExecutor = null;

	private PersistenceManager() {
		loadExecutor = Executors.newCachedThreadPool();
	}

	public static PersistenceManager getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new PersistenceManager();
			try {
				defaultInstance.initialize();
			} catch (CoreException e) {
				CorePlugin.logError("Problem loading persistence information ", e);
			}
		}
		return defaultInstance;
	}

	public static IPersistenceHierarchyMetaData getHierarchyMetaData() {
		if (persistenceHierarchy == null) {
			try {
				Bundle io_mdl = Platform.getBundle("org.xtuml.bp.io.mdl");//$NON-NLS-1$
				Class factoryClass = io_mdl.loadClass("org.xtuml.bp.io.mdl.PersistenceHierarchyMetaData"); //$NON-NLS-1$
				persistenceHierarchy = (IPersistenceHierarchyMetaData) factoryClass.newInstance();
			} catch (ClassNotFoundException cnf) {
				CorePlugin.logError("Problem loading persistence information ", cnf); //$NON-NLS-1$
			} catch (IllegalAccessException i) {
				CorePlugin.logError("Problem loading persistence information ", i); //$NON-NLS-1$
			} catch (InstantiationException i) {
				CorePlugin.logError("Problem loading persistence information ", i); //$NON-NLS-1$
			}
		}
		return persistenceHierarchy;
	}

	public String getUniqueNameForComponent(NonRootModelElement element, String candidateName) {
		IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
		if (hmd.isComponentRoot(element)) {
			NonRootModelElement parent_me = hmd.getParentComponentRootModelElement(element);
			PersistableModelComponent parent_mc = getComponent(parent_me);
			return getUniqueNameOfParent(parent_mc, candidateName, element);
		} else {

			NonRootModelElement parent_me = hmd.getComponentRootModelElement(element);

			String uniqueName = candidateName;
			int postfix = 0;

			List children = getHierarchyMetaData().getChildren(parent_me, false);

			Iterator iterator = children.iterator();
			while (iterator.hasNext()) {
				NonRootModelElement candidate = (NonRootModelElement) iterator.next();
				candidate = getChildForNameValidation(candidate);
				if (candidate == null) {
					continue;
				}
				if (candidate.getName().equals(uniqueName)) {
					if (candidate.getClass() == element.getClass() && candidate != getChildForNameValidation(element)) {
						postfix += 1;
						uniqueName = candidateName + "-" + postfix; //$NON-NLS-1$
						iterator = children.iterator();
					}
				}
			}
			return uniqueName;
		}
	}

	private NonRootModelElement getChildForNameValidation(NonRootModelElement candidate) {
		if (candidate instanceof PackageableElement_c) {
			// consider the subtype
			List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(candidate);
			if (!subtypes.isEmpty()) {
				candidate = subtypes.get(0);
				if (candidate instanceof Message_c || candidate instanceof InteractionParticipant_c) {
					// need to iterate again as this is still not the naming element
					candidate = getChildForNameValidation(candidate);
				}
			} else {
				// skip but log an error as this is a dangling instance
				CorePlugin.logError("Found a Packageable Element instance that had no subtypes, ID is "
						+ ((PackageableElement_c) candidate).getElement_id(), null);
				return null;
			}
		} else if (candidate instanceof Message_c) {
			List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(candidate);
			if (!subtypes.isEmpty()) {
				candidate = subtypes.get(0);
			} else {
				// skip but log an error as this is a dangling instance
				CorePlugin.logError(
						"Found a Message instance that had no subtypes, ID is " + ((Message_c) candidate).getMsg_id(),
						null);
				return null;
			}
		} else if (candidate instanceof InteractionParticipant_c) {
			List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(candidate);
			if (!subtypes.isEmpty()) {
				candidate = subtypes.get(0);
			} else {
				// skip but log an error as this is a dangling instance
				CorePlugin.logError("Found an Interaction Participant instance that had no subtypes, ID is "
						+ ((InteractionParticipant_c) candidate).getPart_id(), null);
				return null;
			}
		}
		return candidate;
	}

	public String getUniqueNameForComponent(NonRootModelElement element, NonRootModelElement parent_me,
			String candidateName) {
		if (doNotCreateUniqueName) {
			return candidateName;
		}
		IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
		if (hmd.isComponentRoot(element)) {
			PersistableModelComponent parent_mc = getComponent(parent_me);
			return getUniqueNameOfParent(parent_mc, candidateName, element);
		} else {

			parent_me = hmd.getComponentRootModelElement(element);

			String uniqueName = candidateName;
			int postfix = 0;

			List children = getHierarchyMetaData().getChildren(parent_me, false);

			Iterator iterator = children.iterator();
			while (iterator.hasNext()) {
				NonRootModelElement candidate = (NonRootModelElement) iterator.next();
				candidate = getChildForNameValidation(candidate);
				if (candidate == null) {
					continue;
				}
				if (candidate.getName().equals(uniqueName)) {
					if (candidate.getClass() == element.getClass() && candidate != getChildForNameValidation(element)) {
						postfix += 1;
						uniqueName = candidateName + "-" + postfix; //$NON-NLS-1$
						iterator = children.iterator();
					}
				}
			}
			return uniqueName;
		}
	}

	public String getUniqueNameOfParent(PersistableModelComponent parent_mc, String candidateName,
			NonRootModelElement child) {
		// TODO the check for parent_mc != null is only needed
		// as long as the unit tests use single-file models
		if (parent_mc != null && parent_mc.getChildrenCount() > 0) {
			String uniqueName = candidateName;
			int postfix = 0;
			NonRootModelElement nrme = getChildOfNonRootModelElement(parent_mc.getRootModelElement(), uniqueName,
					child);
			while (nrme != null) {
				postfix += 1;
				uniqueName = candidateName + "-" + postfix; //$NON-NLS-1$
				nrme = getChildOfNonRootModelElement(parent_mc.getRootModelElement(), uniqueName, child);
			}
			return uniqueName;
		} else {
			return candidateName;
		}
	}

	private NonRootModelElement getChildOfNonRootModelElement(NonRootModelElement parent, String candidateName,
			NonRootModelElement child) {
		List children = getHierarchyMetaData().getChildren(parent, false);
		Iterator iterator = children.iterator();
		while (iterator.hasNext()) {
			NonRootModelElement candidate = (NonRootModelElement) iterator.next();
			// skip child PE if the supertype of the parent
			if (candidate instanceof PackageableElement_c) {
				PackageableElement_c pe = (PackageableElement_c) candidate;
				List<NonRootModelElement> subtypes = SupertypeSubtypeUtil.getSubtypes(pe);
				if (!subtypes.isEmpty()) {
					if (subtypes.get(0) == parent) {
						continue;
					}
				}
			}
			candidate = getChildForNameValidation(candidate);
			if (candidate == null) {
				continue;
			}
			if (candidate.getName().equals(candidateName) && candidate != child) {
				return candidate;
			}
		}
		// if no child was found, check the component's children
		PersistableModelComponent component = parent.getPersistableComponent().getChild(candidateName);
		if (component != null && component.getRootModelElement() != child) {
			return component.getRootModelElement();
		}
		return null;
	}

	public boolean nameIsUnique(NonRootModelElement element, String candidateName) {
		IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
		if (hmd.isComponentRoot(element)) {
			NonRootModelElement parent_me = hmd.getParentComponentRootModelElement(element);
			PersistableModelComponent parent_mc = getComponent(parent_me);
			// TODO the check for parent_mc != null is only needed
			// as long as the unit tests use single-file models
			if (parent_mc != null && parent_mc.getChildrenCount() > 0) {
				Collection siblings = parent_mc.getChildren();
				for (Iterator sib_iter = siblings.iterator(); sib_iter.hasNext();) {
					PersistableModelComponent sib = (PersistableModelComponent) sib_iter.next();
					if (element != sib.getRootModelElement()) {
						if (CorePlugin.osIsCaseInsensitive()) {
							if (candidateName.toLowerCase().equals(sib.getName().toLowerCase())) {
								return false;
							}
						} else {
							if (candidateName.equals(sib.getName())) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	public static PersistableModelComponent createComponent(NonRootModelElement instance) {
		PersistableModelComponent component = getComponent(instance);
		if (component != null) {
			throw new IllegalArgumentException("Given component already registered");
		}

		NonRootModelElement parent_mc = persistenceHierarchy.getParentComponentRootModelElement(instance);
		if (parent_mc == null)
			return null;
		try {
			PersistableModelComponent parentPMC = parent_mc.getPersistableComponent();
			if (parentPMC != null) {
				component = new PersistableModelComponent(instance, parentPMC.getFile());
				component.refresh();
			}
		} catch (CoreException e) {
			throw new IllegalArgumentException("Given component is not a valid component");
		}
		return component;
	}

	static public PersistableModelComponent createRootComponent(final IProject project, SystemModel_c systemModel) {
		if (systemModel == null) {
			systemModel = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
				public boolean evaluate(Object candidate) {
					SystemModel_c selected = (SystemModel_c) candidate;
					return selected.getName().equals(project.getName());
				}
			}, false);

			if (systemModel == null) {
				systemModel = new SystemModel_c(Ooaofooa.getDefaultInstance());
				systemModel.setName(project.getName());
			}
		}

		PersistableModelComponent systemModelComponent = getRootComponent(project);
		if (systemModelComponent == null) {
			try {
				systemModelComponent = new PersistableModelComponent(systemModel, project);
			} catch (CoreException e) {
				throw new IllegalArgumentException("Given component is not a valid component");
			}
		}
		return systemModelComponent;
	}

	static public PersistableModelComponent createRootComponent(final IProject project) {
		return createRootComponent(project, null);
	}

	static public PersistableModelComponent getRootComponent(IProject project) {
		PersistableModelComponent[] roots = findRootComponentInstances();
		for (int i = 0; i < roots.length; ++i) {
			if (roots[i].getName().equals(project.getName())) {
				return roots[i];
			}
		}
		return null;
	}

	static public PersistableModelComponent findOrCreateComponent(IPath path) {
		// if the path is for a graphics file, replace the extension before searching
		if (path.getFileExtension().equals(Ooaofooa.GRAPHICS_EXT)) {
			path = path.removeFileExtension().addFileExtension(Ooaofooa.MODELS_EXT);
		}
		PersistableModelComponent pmc = findComponent(path);
		if (pmc == null) {
			// see if the component exists in the list of inconsistent
			// components
			pmc = findInconsistentComponent(path);
			// if not then go ahead an create it
			if (pmc == null)
				pmc = PersistableModelComponent.create(path);
			else
				// otherwise do not return the inconsistent
				// component
				pmc = null;
		}
		return pmc;
	}

	static public PersistableModelComponent getComponent(NonRootModelElement element) {
		if (element == null) {
			return null;
		}

		PersistableModelComponent component = element.getPersistableComponent();

		return component;
	}

	public static PersistableModelComponent findElementComponent(ModelElement element, boolean force) {

		NonRootModelElement target_me = (NonRootModelElement) element;
		PersistableModelComponent target = target_me.getPersistableComponent(force);
		if (target == null) {
			if (!persistenceHierarchy.isComponentRoot(target_me)) {
				target_me = persistenceHierarchy.getComponentRootModelElement(target_me);
			}
			if (target_me != null) {
				target = target_me.getPersistableComponent(force);
				if (target == null && force) {
					target = createComponent(target_me);
				}
			}
		}
		return target;
	}

	boolean initializing = true;

	/**
	 * Searches the workspace for all the projects with the xtUML nature, and
	 * creates the component info using the paths.
	 */
	private void initialize() throws CoreException {
		initializing = true;
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; ++i) {
			if (XtUMLNature.hasNature(projects[i]) && projects[i].isSynchronized(IResource.DEPTH_ZERO)) {
				// get the Models directory
				traverseProject(projects[i]);
			}
		}
		UpgradeHandler handler = getUpgradeHandler();
		// load the data for the roots
		PersistableModelComponent[] roots = findRootComponentInstances();
		for (int i = 0; i < roots.length; ++i) {
			if (!roots[i].isLoaded()) {
				UpgradeRequirement upgradeRequirement = checkForUpgradeRequirement(roots[i]);
				if (upgradeRequirement.requiresUpgrade) {
					if (upgradeRequirement.upgradeAtOnce) {
						handler.addUpgradeRequirement(upgradeRequirement);
						handler.performUpgrade();
					} else {
						// collect for calling later
						handler.addUpgradeRequirement(upgradeRequirement);
					}
				}
			}
		}
		if (handler.requirements != null && !handler.requirements.isEmpty()) {
			handler.performUpgrade();
		}
		// Fully load all models
		loadAllProjects();
		initializing = false;

		// The following forces the marking plugin to load without creating a hard
		// bundle dependency from
		// core to that plugin. We want it to load, register the listener, and
		// initialize for in-memory
		// marking data with connections to the actual model instances.
		final String packageName = "org.xtuml.bp.ui.marking"; //$NON-NLS-1$
		Bundle bundle = Platform.getBundle(packageName);
		try {
			Class<?> clazz = bundle.loadClass(packageName + "." + "MarkingDataManager");//$NON-NLS-1$ //$NON-NLS-2$
			Method initMethod = clazz.getMethod("initialize", new Class[] { IProject[].class });//$NON-NLS-1$
			initMethod.invoke(clazz, new Object[] { projects });
		} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public SequentialExecutor getSequentialExecutor() {
		return sequentialExecutor;
	}

	private synchronized void loadComponents(final Collection<PersistableModelComponent> pmcs,
			final IProgressMonitor monitor) throws CoreException {

		// filter the PMCs by checking their hash value against the most recent hash
		final Set<PersistableModelComponent> pmcsToLoad = new HashSet<>();
		pmcsToLoad.addAll(
				pmcs.stream().filter(pmc -> !pmc.isLoaded() || !Arrays.equals(pmc.calculateDigest(), pmc.getLastDigest()))
						.collect(Collectors.toSet()));

		// try to reload inconsistent components
		boolean reloadingInconsistentInstances = InconsistentInstances.values().size() > 0;
		if (pmcsToLoad.stream().anyMatch(PersistableModelComponent::isLoaded)) {
			pmcsToLoad.addAll(InconsistentInstances.values());
		}

		if (!pmcsToLoad.isEmpty()) {

			// System.out.println("Triggered load [reload=" + reload + "]... " +
			// pmcsToLoad.size());

			final Map<PersistableModelComponent, NonRootModelElement> oldElementMap = new HashMap<>();

			// if the PMC is already loaded, clear the database first
			pmcsToLoad.stream().filter(PersistableModelComponent::isLoaded).forEach(pmc -> {
				oldElementMap.put(pmc, pmc.getRootModelElement());
				pmc.clearDatabase();
			});

			// launch each load in a new thread
			sequentialExecutor = new SequentialExecutor(loadExecutor);
			final CompletionService<PersistableModelComponent> loadingPmcs = new ExecutorCompletionService<>(
					sequentialExecutor);
			for (PersistableModelComponent pmc : pmcsToLoad) {
				if (oldElementMap.containsKey(pmc)) {
					Ooaofooa.getDefaultInstance().fireModelElementAboutToBeReloaded(oldElementMap.get(pmc));
				}
				loadingPmcs.submit(() -> {
					try {
						final String oldName = Thread.currentThread().getName();
						Thread.currentThread().setName(pmc.getFile().toString());
						pmc.load(monitor, false, pmc.isLoaded());
						Thread.currentThread().setName(oldName);
					} catch (CoreException e) {
						CorePlugin.logError("Problem loading component: " + pmc.getFile(), e);
					}
				}, pmc);
			}

			// initiate the shutdown of the sequential executor
			sequentialExecutor.shutdown();
			
			boolean errorsOccurred = false;

			// wait for all PMCs to load
			int loadedPmcs = 0;
			while (loadedPmcs < pmcsToLoad.size()) {
				try {
					// wait for a PMC to load
					final Future<PersistableModelComponent> pmcFuture = loadingPmcs.poll(250, TimeUnit.MILLISECONDS);
					if (pmcFuture != null) {
						try {
							final PersistableModelComponent pmc = pmcFuture.get();
							if (pmc.isLoaded()) {
								// System.out.println("LOADED: " + pmc.getFile());
							} else {
								CorePlugin.logError("Failed to load: " + pmc.getFile(), new RuntimeException());
								errorsOccurred = true;
							}
						} catch (ExecutionException e) {
							CorePlugin.logError("Problem loading component", e);
							errorsOccurred = true;
						} finally {
							loadedPmcs++;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// finish the load for each PMC
			for (PersistableModelComponent pmc : pmcsToLoad) {
				try {
					if (pmc.isLoaded()) {
						pmc.finishLoad(monitor);
						if (oldElementMap.containsKey(pmc)) {
							Ooaofooa.getDefaultInstance().fireModelElementReloaded(oldElementMap.get(pmc),
									pmc.getRootModelElement());
						}
					}
				} catch (CoreException e) {
					CorePlugin.logError("Problem finishing component load", e);
					errorsOccurred = true;
				}
			}

			sequentialExecutor = null;

			if (!reloadingInconsistentInstances && errorsOccurred && showErrorDialog && !Ooaofooa.inUnitTest()) {
				if (PlatformUI.isWorkbenchRunning()) {
					PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
						final MessageDialogWithToggle dialog = new MessageDialogWithToggle(null,
								"Errors Occurred During xtUML Load", null,
								"Errors occurred while loading xtUML model files. Check for correct syntax in '.xtuml' files and then restart BridgePoint."
										+ " For full details, check the error log.",
								MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0,
								"Do not show this message again", false);
						dialog.open();
						showErrorDialog = !dialog.getToggleState();
					});
				}
			}

			// System.out.println("Done loading.");

		}
	}

	public void loadProjects(Collection<IProject> projects, IProgressMonitor monitor)
			throws CoreException {
		final Set<PersistableModelComponent> pmcsToLoad = new HashSet<>();
		for (final IProject project : projects) {
			// Get the list of PMCs to load
			// Don't reload inconsistent PMCs
			pmcsToLoad.addAll(getDeepChildrenOf(getRootComponent(project), false));

			// If IPRs are enabled, include all PMCs from projects in the workspace
			final Preferences projectPrefs = new ProjectScope(project)
					.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
			final boolean iprsEnabled = projectPrefs
					.getBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, false);
			if (iprsEnabled) {
				pmcsToLoad.addAll(Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects())
						.flatMap(p -> getDeepChildrenOf(getRootComponent(p), false).stream())
						.collect(Collectors.toSet()));
			}
		}
		loadComponents(pmcsToLoad, monitor);
	}

	public void loadAllProjects() {
		try {
			loadProjects(Set.of(ResourcesPlugin.getWorkspace().getRoot().getProjects()), new NullProgressMonitor());
		} catch (CoreException e) {
			CorePlugin.logError("Failed to load projects.", e);
		}
	}

	private UpgradeHandler getUpgradeHandler() {
		return new UpgradeHandler() {

			@Override
			public void performUpgrade() {
				String currentVersion = PersistenceChangeTracker.getCurrentVersion().getPersistenceVersion();
				String version716 = PersistenceChangeTracker.version_716.getPersistenceVersion();
				if (getPersistenceVersionAsInt(currentVersion) >= getPersistenceVersionAsInt(version716)) {
					// need to upgrade PE persistence locations for some
					// elements
					// we want to give the user a list of all projects that
					// require
					// the upgrade, this way they can choose which to upgrade
					final List<IProject> projects = new ArrayList<IProject>();
					for (UpgradeRequirement requirement : requirements) {
						projects.add(requirement.rootComponent.getFile().getProject());
					}
					CorePlugin.addProjectsForPEUpgrade(projects.toArray(new IProject[projects.size()]));
				}
			}
		};
	}

	abstract class UpgradeHandler {
		List<UpgradeRequirement> requirements = new ArrayList<UpgradeRequirement>();

		public void addUpgradeRequirement(UpgradeRequirement requirement) {
			requirements.add(requirement);
		}

		public abstract void performUpgrade();
	}

	class UpgradeRequirement {
		public boolean upgradeAtOnce;
		public boolean requiresUpgrade;
		public PersistableModelComponent rootComponent;

		public UpgradeRequirement(boolean requiresUpgrade, boolean upgradeAtOnce,
				PersistableModelComponent rootComponent) {
			this.requiresUpgrade = requiresUpgrade;
			this.upgradeAtOnce = upgradeAtOnce;
			this.rootComponent = rootComponent;
		}

	}

	private UpgradeRequirement checkForUpgradeRequirement(PersistableModelComponent pmc) {
		String persistenceVersion = getPersistenceVersion(pmc);
		if (getPersistenceVersionAsInt(persistenceVersion) < getPersistenceVersionAsInt(
				PersistenceChangeTracker.version_716.getPersistenceVersion())) {
			return new UpgradeRequirement(true, false, pmc);
		}
		return new UpgradeRequirement(false, false, pmc);
	}

	public void traverseProject(IProject project) throws CoreException {
		IFolder modelsFolder = project.getFolder(Ooaofooa.MODELS_DIRNAME);

		if (modelsFolder.exists()) {
			IResource[] members = modelsFolder.members();
			for (int j = 0; j < members.length; j++) {
				if ((members[j] instanceof IFolder) && members[j].getName().equals(project.getName())) {
					traverseResourceContainer((IFolder) members[j]);
				}
			}
		}
		ensureRootExists(project);
	}

	// Make all components for xtuml files found in project
	// made public to be accessable from unit tests
	public static void traverseResourceContainer(IFolder componentFolder) throws CoreException {

		// Get the .xtuml file and make it a component
		IResource[] members = componentFolder.members();

		for (int i = 0; i < members.length; i++) {
			if (members[i] instanceof IFile) {
				IFile file = (IFile) members[i];

				IPath path = file.getFullPath();

				String extension = file.getFileExtension();

				if ((extension != null) && extension.equals(Ooaofooa.MODELS_EXT)
						&& path.removeFileExtension().lastSegment().equals(componentFolder.getName())) {
					PersistableModelComponent pmc = PersistenceManager.findComponent(path);
					if (pmc == null) {
						PersistableModelComponent.create(path);
					}
				}
			} else if (members[i] instanceof IFolder) {
				traverseResourceContainer((IFolder) members[i]);
			}
		}
	}

	static public void ensureRootExists(final IProject project) {
		WorkspaceJob job = new WorkspaceJob("Create Root Component") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor) throws CoreException {
						PersistableModelComponent component = getRootComponent(project);
						IFile rootComponentFile = project.getFile(Ooaofooa.MODELS_DIRNAME + "/" + project.getName()
								+ "/" + project.getName() + "." + Ooaofooa.MODELS_EXT);
						if (component == null && !rootComponentFile.exists()) {
							component = createRootComponent(project);
							if (component != null) {
								// do not persist if the component could not be loaded
								if (PersistenceManager.isPersistenceVersionAcceptable(component)) {
									component.persist();
									final PersistableModelComponent finalComp = component;
									PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
										public void run() {
											UIUtil.refresh(finalComp.getRootModelElement());
										}
									});
								}
							}
						}
					}
				}, new NullProgressMonitor());
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.setPriority(Job.SHORT);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
	}

	static public PersistableModelComponent[] findRootComponentInstances() {
		Collection<PersistableModelComponent> matches = new ArrayList<PersistableModelComponent>();

		synchronized (Instances) {
			for (Iterator iterator = Instances.values().iterator(); iterator.hasNext();) {

				PersistableModelComponent comp = (PersistableModelComponent) iterator.next();
				if (comp.isRootComponent())
					matches.add(comp);
			}
		}
		return (PersistableModelComponent[]) matches.toArray(new PersistableModelComponent[matches.size()]);
	}

	static public void addComponent(PersistableModelComponent component) {
		synchronized (Instances) {
			Instances.put(component.getFullPath().toString(), component);
			instanceSet.add(component);
		}
	}

	static public void addInconsistentComponent(PersistableModelComponent component) {
		synchronized (InconsistentInstances) {
			PersistableModelComponent pmc = (PersistableModelComponent) InconsistentInstances
					.get(component.getFullPath().toString());
			if (pmc == null) {
				InconsistentInstances.put(component.getFullPath().toString(), component);
			}
		}
	}

	static public void removeComponent(PersistableModelComponent component) {
		synchronized (Instances) {
			Instances.remove(component.getFullPath().toString());
			instanceSet.remove(component);
		}
	}

	public static void removeInconsistentComponent(PersistableModelComponent inconsistentComponent) {
		synchronized (InconsistentInstances) {
			InconsistentInstances.remove(inconsistentComponent.getFullPath().toString());
		}
	}

	static public PersistableModelComponent findComponent(IPath path) {
		return findComponent(path.toString());
	}

	static public PersistableModelComponent findInconsistentComponent(IPath path) {
		return findInconsistentComponent(path.toString());
	}

	static public PersistableModelComponent findInconsistentComponent(String path) {
		synchronized (InconsistentInstances) {
			return (PersistableModelComponent) InconsistentInstances.get(path);
		}
	}
	
	static public boolean projectHasInconsistentInstances(IProject project) {
		synchronized (InconsistentInstances) {
			return InconsistentInstances.values().stream().anyMatch(pmc -> pmc.getFile().getProject().equals(project));
		}
	}

	static public PersistableModelComponent findComponent(String path) {
		if (path == null)
			return null;
		synchronized (Instances) {
			return (PersistableModelComponent) Instances.get(path);
		}
	}

	public static Collection getChildrenOf(PersistableModelComponent component) {
		IPath parentDirPath = component.getContainingDirectoryPath();
		Vector<PersistableModelComponent> result = new Vector<PersistableModelComponent>();
		int childSegments = component.getFullPath().segmentCount() + 1;
		synchronized (Instances) {

			for (Iterator iterator = Instances.tailMap(parentDirPath.toString()).values().iterator(); iterator
					.hasNext();) {
				PersistableModelComponent source = (PersistableModelComponent) iterator.next();
				if ((source.getFullPath().segmentCount() == childSegments)
						&& (parentDirPath.isPrefixOf(source.getFullPath()))) {
					result.add(source);
				}
			}
		}
		return result;
	}

	public static Collection<PersistableModelComponent> getDeepChildrenOf(PersistableModelComponent component) {
		return getDeepChildrenOf(component, false);
	}

	public static Collection<PersistableModelComponent> getDeepChildrenOf(PersistableModelComponent component,
			boolean includeInconsistentInstances) {
		Set<PersistableModelComponent> result = new HashSet<PersistableModelComponent>();
		if (component != null) {
			IPath parentDirPath = component.getContainingDirectoryPath();
			synchronized (Instances) {
				for (PersistableModelComponent source : Instances.values()) {
					if (parentDirPath.isPrefixOf(source.getFullPath())) {
						result.add(source);
					}
				}
			}
			if (includeInconsistentInstances) {
				synchronized (InconsistentInstances) {
					for (PersistableModelComponent source : InconsistentInstances.values()) {
						if (parentDirPath.isPrefixOf(source.getFullPath())) {
							result.add(source);
						}
					}
				}
			}
		}
		return result;
	}

	public static boolean isOrphaned(PersistableModelComponent component) {
		boolean orphaned;
		synchronized (instanceSet) {
			orphaned = (!instanceSet.contains(component));
		}
		if (!orphaned) {
			PersistableModelComponent parent = component.getParent();
			if (parent != null) {
				orphaned = parent.isOrphaned();
			}
		}
		return orphaned;
	}

	/**
	 * Retrieve all components of given type in given modelRoot, if modelRoot is
	 * null component will be searched in all modelRoots.
	 * 
	 * @param modelRoot
	 * @param elementClass
	 * @return
	 */
	static public List findAllComponents(ModelRoot modelRoot, Class elementClass) {

		IPersistenceHierarchyMetaData metaData = getHierarchyMetaData();
		String[] types = metaData.getComponentRootRootModelElementTypes(elementClass);
		if (types == null) {
			Exception e = new Exception();
			CorePlugin.logError("No root model element types for: " + elementClass.getName(), e);
		}

		Collection comps = null;

		Vector<PersistableModelComponent> results = new Vector<PersistableModelComponent>();
		synchronized (Instances) {

			IPath path = null;

			if (modelRoot == null || !ModelRoot.isFileBasedID(modelRoot.getId())) {// $NON-NLS-1$
				// used to find components in the entire workspace
				// provides support for RTO resolution
				comps = Instances.values();
				path = new Path("/");
			} else {
				// for the compare and clipboard roots return an empty list
				// of components (there are never components related to these
				// roots)
				if (modelRoot.getId().equals(ModelRoot.CLIPBOARD_MODEL_ROOT_NAME)
						|| modelRoot.getId().equals(ModelRoot.COMPARE_MODEL_ROOT_NAME)) {
					return results;
				}

				path = new Path(modelRoot.getId());
				String domName = path.removeFileExtension().segment(1);
				if (domName == null) {
					// if domain name could not be determined return no
					// components
					return results;
				}
				// we remove two segments to allow finding components
				// which are not direct children of the given model root
				// path, this case arises in system level modeling
				path = path.removeFileExtension().removeLastSegments(2);
			}
			comps = Instances.tailMap(path.toString()).values();
			int i = 0;
			for (Iterator iter = comps.iterator(); iter.hasNext();) {
				PersistableModelComponent pmc = (PersistableModelComponent) iter.next();
				if (path.isPrefixOf(pmc.getFullPath())) {
					for (i = 0; i < types.length; i++) {
						if (pmc.getComponentType().equals(types[i])) {
							results.add(pmc);
							break;
						}
					}
				} else {
					break;
				}
			}
			return results;
		}
	}

	static public List findAllChildComponents(PersistableModelComponent parent, ModelRoot modelRoot, Class childType,
			boolean deep) {
		if (parent == null) {
			// this is possible when creating new elements and in unit test
			// setup data
			return new Vector(0);
		}
		IPersistenceHierarchyMetaData metaData = getHierarchyMetaData();
		String[] types = metaData.getComponentRootRootModelElementTypes(childType);
		// comps contains all child in hierarchy, childSegments are immediate
		// children
		synchronized (Instances) {
			Collection comps = getChildrenOf(parent);
			if (deep)
				comps = getDeepChildrenOf(parent);

			Vector<PersistableModelComponent> children = new Vector<PersistableModelComponent>();

			for (Iterator iter = comps.iterator(); iter.hasNext();) {
				PersistableModelComponent pmc = (PersistableModelComponent) iter.next();

				if (childType == null) {
					children.add(pmc);
				} else {
					for (int i = 0; i < types.length; i++) {
						if (pmc.getComponentType().equals(types[i])) {
							children.add(pmc);
							break;
						}

					}
				}
			}

			return children;
		}
	}

	static public List findAllChildComponents(PersistableModelComponent parent, ModelRoot modelRoot, Class childType) {
		return findAllChildComponents(parent, modelRoot, childType, false);
	}

	public static int getPersistenceVersionAsInt(String persistenceVersion) {
		try {
			int result = Integer.valueOf(persistenceVersion.replace(".", ""));
			return result;
		} catch (NumberFormatException e) {
			// invalid header
			CorePlugin.logError("Unable to retrieve persistence version from file header.", e);
		}
		return -1;
	}

	public static boolean isPersistenceVersionAcceptable(PersistableModelComponent pmc) {
		String persistenceVersion = getPersistenceVersion(pmc);
		String toolPersistenceVersion = CorePlugin.getPersistenceVersion();
		if (persistenceVersion.equals("")) {
			return false;
		}
		int persistenceVersionAsInt = getPersistenceVersionAsInt(persistenceVersion);
		if (persistenceVersionAsInt == -1) {
			return false;
		}
		if (persistenceVersionAsInt < getPersistenceVersionAsInt(
				PersistenceChangeTracker.version_716.getPersistenceVersion())
				&& !projectsAllowedToLoad.contains(pmc.getFile().getProject())) {
			return false;
		}
		return getPersistenceVersionAsInt(persistenceVersion) <= getPersistenceVersionAsInt(toolPersistenceVersion);
	}

	public static String getPersistenceVersion(PersistableModelComponent pmc) {
		return getPersistenceVersion(pmc, true);
	}

	public static String getPersistenceVersion(PersistableModelComponent pmc, boolean useRoot) {
		PersistableModelComponent component = pmc;
		if (useRoot) {
			component = getRootComponent(pmc.getFile().getProject());
		}
		if (component != null && component.getFile() != null && component.getFile().exists()) {
			return getComponentHeader(component).getFileFormatVersion();
		} else {
			if (component != null) {
				// this is a project creation, return the latest version
				return CorePlugin.getPersistenceVersion();
			}
		}
		return "";
	}

	public static IFileHeader getComponentHeader(PersistableModelComponent pmc) {
		IModelImport importer;
		try {
			importer = CorePlugin.getModelImportFactory().create(pmc.getFile(), Ooaofooa.getDefaultInstance(), pmc,
					false, false, false, false);
			return importer.getHeader();
		} catch (IOException e) {
			CorePlugin.logError("Unable to retrieve file format version.", e);
		}
		return null;
	}

	public static void updatePersistenceVersion(PersistableModelComponent pmc) throws CoreException {
		// if the given component is a root component return
		if (pmc.isRootComponent()) {
			return;
		}
		// persist the root component if the persistence version needs updating
		String version = getPersistenceVersion(pmc);
		String toolVersion = CorePlugin.getPersistenceVersion();
		if (getPersistenceVersionAsInt(version) != getPersistenceVersionAsInt(toolVersion)) {
			PersistableModelComponent rootComponent = getRootComponent(pmc.getFile().getProject());
			if (rootComponent != null) {
				rootComponent.persist();
			}
		}
	}

	public boolean isInitializing() {
		return initializing;
	}

	public static boolean requiresUpgradeBeforeUse(PersistableModelComponent component) {
		String persistenceVersion = getPersistenceVersion(component, false);
		return requiresUpgradeBeforeUse(persistenceVersion, component.getComponentType());
	}

	public static boolean requiresUpgradeBeforeUse(String persistenceVersion, String componentType) {
		if (persistenceVersion == null || persistenceVersion.equals("")) {
			return true;
		}
		if (getPersistenceVersionAsInt(persistenceVersion) < getPersistenceVersionAsInt(
				PersistenceChangeTracker.version_716.getPersistenceVersion())) {
			// we only want to force unload if the root element is the system
			// model or this is an O_OBJ, C_C, EP_PKG, C_I component
			if (componentType.equals("SystemModel") || componentType.equals("ModelClass")
					|| componentType.equals("Component") || componentType.equals("Interface")
					|| componentType.equals("Package")) {
				return true;
			}
		}
		return false;
	}

	public static void markSystemFileForUpgrade(PersistableModelComponent component) {
		// we are rewriting the header for the system model file if it is
		// considered upgraded, this occurs when a single file change has
		// reverted part of an upgrade
		PersistableModelComponent root = getRootComponent(component.getFile().getProject());
		if (root == null) {
			// the root file may not exist yet, this can occur when files
			// are being added out of order
			return;
		}
		String rootVersion = getPersistenceVersion(root);
		try {
			writeHeaderLine(root, rootVersion, PersistenceChangeTracker.version_715.getPersistenceVersion());
		} catch (IOException e) {
			CorePlugin.logError("Unable to write new header line.", e);
		} catch (CoreException e) {
			CorePlugin.logError("Unable to write new header line.", e);
		}
	}

	public static void writeHeaderLine(final PersistableModelComponent root, final String previousVersion,
			final String expectedVersion) throws IOException, CoreException {
		// need to call this on the UI thread
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					final IFile file = root.getFile();
					BufferedReader reader = new BufferedReader(new InputStreamReader(file.getContents()));
					char[] buffer = new char[(int) file.getLocation().toFile().length()];
					reader.read(buffer);
					reader.close();
					String contents = new String(buffer);
					contents = contents.replaceFirst("persistence-version: " + previousVersion,
							"persistence-version: " + expectedVersion);
					final ByteArrayInputStream bais = new ByteArrayInputStream(contents.getBytes());
					IStatus validateEdit = ResourcesPlugin.getWorkspace().validateEdit(new IFile[] { file },
							PlatformUI.getWorkbench().getDisplay().getActiveShell());
					if (validateEdit.isOK() && !file.isReadOnly()) {
						file.setContents(bais, IFile.FORCE, new NullProgressMonitor());
					}
				} catch (CoreException e) {
					CorePlugin.logError("Unable to write header line.", e);
				} catch (IOException e) {
					CorePlugin.logError("Unable to write header line.", e);
				}
			}
		});
	}
	
	public void setShowErrorDialogEnabled(boolean enable) {
		showErrorDialog = enable;
	}

}
