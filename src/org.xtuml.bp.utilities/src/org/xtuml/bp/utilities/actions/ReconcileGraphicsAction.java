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
package org.xtuml.bp.utilities.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import org.xtuml.bp.core.IntegrityIssue_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IntegrityChecker;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.GraphicsReconcilerLauncher;

public class ReconcileGraphicsAction implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		List<SystemModel_c> systems = new ArrayList<SystemModel_c>();
		List<NonRootModelElement> issues = new ArrayList<NonRootModelElement>();
		IStructuredSelection ss = (IStructuredSelection) selection;
		for (Iterator<NonRootModelElement> iterator = ss.iterator(); iterator.hasNext();) {
			NonRootModelElement modelElement = (NonRootModelElement) iterator.next();
			SystemModel_c system = null;
			if (modelElement instanceof SystemModel_c) {
				system  = (SystemModel_c) modelElement;
			} else {
				String systemName = Ooaofooa.getProjectNameFromModelRootId(modelElement.getModelRoot().getId());
				system  = (SystemModel_c) modelElement.getSystemModelFromName(systemName);
			}
			if (!systems.contains(system)) {
				systems.add(system);
			}
		}
		GraphicsReconcilerLauncher reconciler = new GraphicsReconcilerLauncher(systems);
		reconciler.startReconciler(false, false);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// cache the selection
		this.selection = selection;
	}

}
