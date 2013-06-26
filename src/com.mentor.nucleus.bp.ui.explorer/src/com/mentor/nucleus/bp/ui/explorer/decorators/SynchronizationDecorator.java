//========================================================================
//
//File:      $RCSfile: SynchronizationDecorator.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:15:56 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
