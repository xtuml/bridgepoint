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
package org.xtuml.bp.utilities.load;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.IntegrityIssue_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IntegrityChecker;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.io.mdl.wizards.ModelExportPage;
import org.xtuml.bp.io.mdl.wizards.ModelExportWizard;
import org.xtuml.bp.io.mdl.wizards.ModelImportPage;
import org.xtuml.bp.io.mdl.wizards.ModelImportWizard;
import org.xtuml.bp.ui.canvas.GraphicsReconcilerLauncher;

public class ReconcileGraphicsAction implements IActionDelegate {

	private ArrayList<SystemModel_c> fElements = new ArrayList<SystemModel_c>();

	@Override
	public void run(IAction action) {
		String msg = "This is not  implemented.\n"
				+"The idea here is to make this work exactly the same as the graphics creation on import without "
				+"adding complexity to the code to do so. If modification the "
				+"import infrastructure is needed, then this CME is not worth it and should just be removed. "
				+"However, if we can export to a stream instead of a file and then import from that stream, AND also, if on "
				+"import we use the loaded instances instead of creaing new ones, then this would be a cool option to "
				+"have. Until then, this is not implemented.";
		UIUtil.openInformation(PlatformUI.getWorkbench().getDisplay()
				.getActiveShell(), "Load and Persist", msg);
		return;
		
//		Iterator<SystemModel_c> iter = fElements.iterator();
//		while (iter.hasNext()) {			
//			SystemModel_c modelElement = iter.next();
//			ModelImportWizard importWizard = new ModelImportWizard();
//			ModelImportPage importPage = (ModelImportPage) importWizard
//					.getStartingPage();
//			String path = modelElement.getPersistableComponent().getFullPath().toString();
//			importPage.setSourceField(path);
//			importWizard.importModel(modelElement);
//		}
	}
	

	private void exportSystem() {
		ModelExportWizard exportWizard = new ModelExportWizard();
		exportWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
				.getStructuredSelection());
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), exportWizard);
		dialog.create();
		ModelExportPage exportPage = (ModelExportPage) exportWizard
				.getStartingPage();
		// export the model to a file in the test project
//		exportPage.setDestinationField(testProject.getLocation().append(
//				"coretypetestGenerics.xtuml").toString());
		boolean result = exportWizard.performFinish();		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		fElements.clear();
		if(selection instanceof StructuredSelection) {
			IStructuredSelection ss = ((IStructuredSelection) selection);
			// cache selection of model elements
			Iterator<?> iter = ss.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof SystemModel_c) {
					fElements.add((SystemModel_c)obj);
				}
			}
		}
	}

}
