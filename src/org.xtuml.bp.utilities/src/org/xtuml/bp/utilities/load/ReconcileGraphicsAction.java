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
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.GraphicsReconcilerLauncher;

public class ReconcileGraphicsAction implements IActionDelegate {
	List<NonRootModelElement> modelElementsToReconcile = new ArrayList<NonRootModelElement>();
	
	@Override
	public void run(IAction action) {
		GraphicsReconcilerLauncher reconciler = new GraphicsReconcilerLauncher(modelElementsToReconcile);
		reconciler.runReconciler(false, true);
	}	

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		modelElementsToReconcile.clear();
		if (selection instanceof StructuredSelection) {
			IStructuredSelection ss = ((IStructuredSelection) selection);
			// cache selection of model elements
			Iterator<?> iter = ss.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				modelElementsToReconcile.add((NonRootModelElement)obj);
			}
		}
	}

}
