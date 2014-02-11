//========================================================================
//
//File:      $RCSfile: SynchronizationDecorator.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:15:56 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.ui.explorer.decorators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Synchronizationtype_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class SynchronizationDecorator implements ILightweightLabelDecorator {

	private static final ImageDescriptor SYNC_OVERLAY = CorePlugin
			.getImageDescriptor("warning_co.gif");
	public static final String ID = "com.mentor.nucleus.bp.ui.explorer.synchronizationDecorator"; //$NON-NLS-1$

	@Override
	public void decorate(Object element, IDecoration decoration) {
		// rather than check the class for the isSynchronizedMethod through
		// reflection, only consider the known classes
		if (!isSynchronized(element)) {
			decoration.addOverlay(SYNC_OVERLAY, IDecoration.BOTTOM_LEFT);
		}
	}

	private boolean isSynchronized(Object element) {
		if (element instanceof NonRootModelElement) {
			NonRootModelElement nrme = (NonRootModelElement) element;
			if(nrme.Issynchronized()) {
				// check children as well
				return childrenAreSynchronized(nrme);
			} else {
				return false;
			}
		}
		return true;
	}

	private boolean childrenAreSynchronized(NonRootModelElement element) {
		boolean result = true;
		result = element.Issynchronized();
		if (result) {
			List<?> children = new ArrayList<Object>();
			element.Collectreferencesforsynchronization(children, Synchronizationtype_c.Pull);
			for (Object child : children) {
				if (((NonRootModelElement) child).getModelRoot() instanceof Ooaofooa) {
					result = childrenAreSynchronized((NonRootModelElement) child);
					if (!result) {
						return result;
					}
				}
			}
		}
		return result;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// not required
	}

	@Override
	public void dispose() {
		// not required
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// not required
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// not required
	}

}
