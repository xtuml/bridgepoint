// ========================================================================
//
//File: $RCSfile: ConvertToMultifileAction.java,v $
//Version: $Revision: 1.8 $
//Modified: $Date: 2012/01/23 21:27:59 $
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
//
package com.mentor.nucleus.bp.core.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;

public class ConvertToMultifileAction extends Action {

	public ConvertToMultifileAction() {
		super("Convert to multi-file model storage");
	}

	public void run() {
		IStructuredSelection selection = Selection.getInstance()
				.getStructuredSelection();
		// we only process one project
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		if (obj instanceof SystemModel_c) {
			IProject selectedProject = (IProject) ((IAdaptable) obj)
					.getAdapter(IProject.class);
			if (selectedProject != null) {
				showMultifileConversionWizard(selectedProject);
			}
		}
	}

	private void showMultifileConversionWizard(IProject project) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		shell.setCursor(shell.getDisplay().getSystemCursor(SWT.CURSOR_WAIT));

		Wizard wizard = new ConvertToMultifileWizard(project);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();

		shell.setCursor(shell.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
		dialog.open();
	}

	public boolean canShow() {

		IStructuredSelection selection = Selection.getInstance()
				.getStructuredSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		IProject project = null;

		if (obj instanceof SystemModel_c) {
			project = (IProject) ((IAdaptable) obj).getAdapter(IProject.class);
		}
		if (project != null) {
			IFolder mdl_folder = (IFolder) project
					.findMember(Ooaofooa.MODELS_DIRNAME);
			if (mdl_folder == null)
				return false; // no single file model

			IResource[] spf_models = null;
			try {
				spf_models = mdl_folder.members();
			} catch (CoreException e) {
				CorePlugin
						.logError("Could not find single file models list", e);
			}

			IFolder dom_parent = (IFolder) mdl_folder.findMember(project
					.getName());
			if (spf_models != null) {
				for (int j = 0; j < spf_models.length; ++j) {
					if (spf_models[j] instanceof IFile) {
						IFile content = (IFile) spf_models[j];
						if (content.getFileExtension().equals(
								Ooaofooa.MODELS_EXT)) {
							String fileName = content.getFullPath()
									.removeFileExtension().lastSegment();
							if (dom_parent == null)
								return true; //there r spf but none mpf
							if (dom_parent.findMember(fileName) == null)
								return true; //atleast one convertable model is there
						}
					}
				}
			}
		}
		return false;
	}
}