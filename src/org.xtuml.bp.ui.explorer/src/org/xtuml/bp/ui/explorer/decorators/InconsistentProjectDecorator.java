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
package org.xtuml.bp.ui.explorer.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.PersistenceManager;

public class InconsistentProjectDecorator implements ILightweightLabelDecorator {

	private static final ImageDescriptor INCONSISTENT_OVERLAY = CorePlugin.getImageDescriptor("error_co.gif");

	@Override
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof SystemModel_c) {
			final IProject project = ((SystemModel_c) element).getFile().getProject();
			if (PersistenceManager.projectHasInconsistentInstances(project)) {
				decoration.addOverlay(INCONSISTENT_OVERLAY, IDecoration.BOTTOM_LEFT);
			}
		}
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
	}

}
