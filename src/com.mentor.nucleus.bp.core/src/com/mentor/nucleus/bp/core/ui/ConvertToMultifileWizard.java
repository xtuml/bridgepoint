package com.mentor.nucleus.bp.core.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ComponentResourceListener;
import com.mentor.nucleus.bp.core.common.IDConvertor;
import com.mentor.nucleus.bp.core.common.IdAssigner;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.UUIDMap;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class ConvertToMultifileWizard extends Wizard {
	public static final String copyright = "(c) Copyright 2005-2014 by Mentor Graphics Corporation.  All rights reserved.";

	private IProject project;

	private ConvertToMultifileWizardPage1 page1;

	private PersistenceManager manager;

	public ConvertToMultifileWizard(IProject projectToConvert) {
		if (projectToConvert == null) {
			throw new IllegalArgumentException(
					"Argument project cannot be null");
		}
		setNeedsProgressMonitor(true);
		project = projectToConvert;
	}

	public void addPages() {
		createListPage();
	}

	public IProject getSelectedProject() {
		return project;
	}

	private void createListPage() {
		page1 = new ConvertToMultifileWizardPage1("Convert To Multifile", this);
		page1.setTitle("Model Migration Wizard");
		page1
				.setDescription("Select the model(s) to convert to multifile format.");
		this.addPage(page1);
	}

	public List getModelFiles() {
		List<String> filesList = new ArrayList<String>(10);
		if (project != null) {
			IFolder mdl_folder = (IFolder) project
					.findMember(Ooaofooa.MODELS_DIRNAME);
			IResource[] mdl_content = null;
			if (mdl_folder != null) {
				if (!mdl_folder.isSynchronized(IResource.DEPTH_INFINITE)) {
					try { // Refresh the folder in case the user modified the contents in the file system
						mdl_folder.refreshLocal(IResource.DEPTH_INFINITE,
								new NullProgressMonitor());
					} catch (CoreException e) {
						CorePlugin.logError("Problem refreshing project : "
								+ mdl_folder.getName(), e);
					}
				}
				try {
					mdl_content = ((IFolder) mdl_folder).members();
				} catch (Exception e) {
					CorePlugin.logError("Problem accessing workspace ", e);
				}
				if (mdl_content != null) {
					IFolder dom_parent = (IFolder) mdl_folder
							.findMember(project.getName());
					for (int j = 0; j < mdl_content.length; ++j) {
						if (mdl_content[j] instanceof IFile) {
							IFile dom_file = (IFile) mdl_content[j];
							if (dom_file.getFileExtension().equals(
									Ooaofooa.MODELS_EXT)) {
								String fileName = dom_file.getFullPath()
										.removeFileExtension().lastSegment();
								if (dom_parent == null
										|| dom_parent.findMember(fileName) == null)
									filesList.add(dom_file.getName());
							}
						}
					} //END For
				}
			}
		}
		return filesList;
	}

	public boolean canFinish() {
		//String[] selectedNames = page1.getSelectedFilesList();
		//return (selectedNames != null && selectedNames.length > 0);
		return page1.isPageComplete();
	}

	public IWizardPage getNextPage(IWizardPage page) {
		return null;
	}

	public IWizardPage getPreviousPage(IWizardPage page) {
		return null;
	}

	private class myWRunnable implements IWorkspaceRunnable {
		public boolean returnResult;

		public void run(IProgressMonitor monitor) {
			// returns true to indicate the finish request was accepted, and false to indicate that the finish request was refused
			returnResult = true;

			Shell shell = getShell();
			shell
					.setCursor(shell.getDisplay().getSystemCursor(
							SWT.CURSOR_WAIT));
			manager = PersistenceManager.getDefaultInstance();

			SystemModel_c systemModel = SystemModel_c.SystemModelInstance(
					Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
						public boolean evaluate(Object candidate) {
							SystemModel_c selected = (SystemModel_c) candidate;
							return selected.getName().equals(project.getName());
						}
					});

			PersistableModelComponent sysModelComponent;

			if (systemModel != null) {
				sysModelComponent = PersistenceManager
						.getComponent(systemModel);
			} else {
				systemModel = new SystemModel_c(Ooaofooa.getDefaultInstance());
				systemModel.setName(project.getName());
				sysModelComponent = PersistenceManager.createRootComponent(
						project, systemModel);
			}

			boolean toPersist = !sysModelComponent.isPersisted();

			if (IdAssigner.isUUIDDummy(systemModel.getSys_id())) {
				toPersist = true;
				IdAssigner idAssigner = systemModel.getIdAssigner();
				Object oldKey = systemModel.getInstanceKey();
				systemModel.setSys_id(idAssigner.createUUID());
                systemModel.updateInstanceKey(oldKey, systemModel.getInstanceKey());
			}

			UUIDMap inputMap = new UUIDMap(null);

			if (toPersist) {
				try {
					sysModelComponent.persist();
				} catch (CoreException e) {
					CorePlugin.logError(
							"Problem while creating system model for project: "
									+ project.getName(), e);
					showErrorMessage(project.getName());
				}
			}

			String[] selectedFilesList = page1.getSelectedFilesList();
			boolean bDelete = page1.isDeleteSource();

			IFolder modelsFolder = getSelectedProject().getFolder(
					Ooaofooa.MODELS_DIRNAME);

			for (int j = 0; j < selectedFilesList.length; ++j) {
				IFile content = modelsFolder.getFile(selectedFilesList[j]);
				Ooaofooa mr = Ooaofooa.getInstance(content, true);
				Domain_c dom = Domain_c.DomainInstance(mr);
				IDConvertor.getInstance().convertToUUID(dom, inputMap);
				PersistableModelComponent component = PersistenceManager
						.getComponent(dom);
				try {
					if (component == null) {
						component = manager.registerModel(dom, project);
					}
					if (!component.isLoaded()) {
						dom.relateAcrossR28To(systemModel);
					}
					NewDomainWizard.persistSelfAndChildren(dom, new NullProgressMonitor());
					NewDomainWizard.displayDuplicateDialog();
					if (bDelete)
						content.delete(true, true, new NullProgressMonitor());
					returnResult = true;
				} catch (Throwable e) {
					CorePlugin.logError("Problem while migrating domain: "
							+ selectedFilesList[j], e);
					returnResult = false;
				} finally {
					NewDomainWizard.duplicateNames.clear();
				}
			}// END For

			UIUtil.refresh(systemModel); // refresh Model Explorer to show
			// converted domain
			shell.setCursor(shell.getDisplay()
					.getSystemCursor(SWT.CURSOR_ARROW));

			if (!returnResult) {
				showErrorMessage("Problem encountered while migrating, see error log for more information.");
			}
		}
	}

	public boolean performFinish() {
		// wrap with-in a WorkspaceRunnable so that created resources
		// have notifications sent in a batch
		myWRunnable wrunnable = new myWRunnable();
		ComponentResourceListener.setIgnoreResourceChanges(true);
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			ISchedulingRule rule = workspace.getRuleFactory().modifyRule(
					project);
			workspace.run(wrunnable, rule, IWorkspace.AVOID_UPDATE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			CorePlugin.logError(
					"Unable to create multi-file models for project.", e);
		}
		return wrunnable.returnResult;
	}

	// helper method of performFinish to show error during conversion while wizard dialog is still open
	private void showErrorMessage(String fileName) {
		page1.setErrorMessage("Problem while creating system model(s) for "
				+ fileName + "\n" + "See console for further information");

	}
}
