//========================================================================
//
//File:      $RCSfile: ExplorerTreeViewer.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:15:43 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.ui.explorer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

/**
 * The tree viewer that constitutes most of the model explorer's screen real
 * estate.
 */
public class ExplorerTreeViewer extends TreeViewer {
    /**
     * Constructor.
     */
    public ExplorerTreeViewer(Composite parent) {
        super(parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(org.eclipse.jface.viewers.ISelection,
     *      boolean)
     * 
     * This is overridden to make it visible to the other members of this
     * package.
     */
    protected void setSelectionToWidget(ISelection selection, boolean reveal) {
        super.setSelectionToWidget(selection, reveal);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.StructuredViewer#update(org.eclipse.jface.viewers.ISelection, boolean)
     * 
     * This is overridden to add the check for the widget being disposed
     */
    public void update(Object pElement, String[] properties) {

        Object mElement = pElement;
        Assert.isNotNull(mElement);
        if (mElement instanceof IResource) {
            IPath path = null;
            PersistableModelComponent pmc = null;
            if (mElement instanceof IFile) {
                path = ((IFile) mElement).getFullPath();
                pmc = PersistenceManager.findComponent(path);
                if (pmc != null && pmc.getRootModelElement() != null) {
                    mElement = pmc.getRootModelElement().getFileFacade();
                }
            } else if (mElement instanceof IFolder) {
                path = ((IFolder) mElement).getFullPath();
                path = path.append(path.lastSegment() + "."
                        + Ooaofooa.MODELS_EXT);
                pmc = PersistenceManager.findComponent(path);
                if (pmc != null) {
                    mElement = pmc.getRootModelElement();
                }
            } else if (mElement instanceof IProject) {
                pmc = PersistenceManager.getRootComponent((IProject) mElement);
                if (pmc != null) {
                    mElement = pmc.getRootModelElement();
                }
            }
            if (mElement == null) {
                return;
            }
        }
        Widget item = findItem(mElement);
        if (item == null)
            return;
        else if (item.isDisposed()) {
            System.err.println("Tried to update a disposed widget for "
                    + mElement.toString());
            return;
        }

        internalUpdate(item, mElement, properties);
    }
	public TreeItem  findTreeItem(Object element) {
		return (TreeItem) findItem(element);
	}
}