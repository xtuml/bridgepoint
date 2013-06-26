//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
// (c) Copyright 2006-2013 by Mentor Graphics Corp.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package **PROJECT**;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;

import com.mentor.nucleus.bp.core.DomainProxy;
import com.mentor.nucleus.bp.core.Domain_c;

/**
 * The tree viewer that constitutes most of the **TREEVIEWER_PREFIX**'s
 * screen real estate. 
 */
public class **TREEVIEWER_PREFIX**TreeViewer extends TreeViewer 
{
    /**
     * Constructor.
     */
    public **TREEVIEWER_PREFIX**TreeViewer(Composite parent) 
    {
        super(parent);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(org.eclipse.jface.viewers.ISelection, boolean)
     * 
     * This is overridden to make it visible to the other members of this 
     * package.
     */
    public void setSelectionToWidget(ISelection selection, boolean reveal) 
    {
        super.setSelectionToWidget(selection, reveal);
    }
    
    public boolean replaceProxyWithDomain(Domain_c dom)
    {
		Item[] items = getChildren(getControl());
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				if (internalReplaceProxy(items[i], dom))
					return true;
			}
		}
		return false;
    }
    private boolean internalReplaceProxy(Item parent, Domain_c dom)
    {
		// compare with node
		Object data = parent.getData();
		if (data != null) {
			if ( data instanceof DomainProxy )
			{
				Domain_c proxyDom = ((DomainProxy)data).getActualElement(false);
				if (equals(proxyDom, dom))
				{
					associate(proxyDom, parent);
					return true;
				}
			}
		}
		// recurse over children
		Item[] items = getChildren(parent);
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (internalReplaceProxy(item, dom))
				return true;
		}
		return false;
    	
    }
}