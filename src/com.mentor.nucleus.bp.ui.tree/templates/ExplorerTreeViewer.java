//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
// (c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
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