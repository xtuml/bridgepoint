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

package com.mentor.nucleus.bp.ui.session;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;

/**
 * The tree viewer that constitutes most of the SessionExplorer's screen real
 * estate.
 */
public class SessionExplorerTreeViewer extends TreeViewer {
	/**
	 * Constructor.
	 */
	public SessionExplorerTreeViewer(Composite parent) {
		super(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(org.eclipse
	 * .jface.viewers.ISelection, boolean)
	 * 
	 * This is overridden to make it visible to the other members of this
	 * package.
	 */
	public void setSelectionToWidget(ISelection selection, boolean reveal) {
		super.setSelectionToWidget(selection, reveal);
	}

	protected void internalRefresh(Object element, boolean updateLabels) {
		if (element instanceof SystemModel_c)
			element = null;
		super.internalRefresh(element, updateLabels);
	}

	public ViewerSorter getSorter() {
		ViewerSorter result = super.getSorter();
		return result;
	}

	public ComponentInstance_c getExecutionEngineOfSelectedElement() {
		ISelection selection = getSelection();
		if (selection instanceof ITreeSelection) {
			return traverseToExecutionEngine();
		}
		return null;
	}

	private ComponentInstance_c traverseToExecutionEngine() {
		Object result = null;
		IContentProvider provider = getContentProvider();
		if (provider instanceof SessionExplorerContentProvider) {
			Item[] items = getSelection(getControl());
			if (items.length == 1) {
				TreePath tp = getTreePathFromItem(items[0]);
				int index = tp.getSegmentCount() - 1;
				while (index >= 0 && index < tp.getSegmentCount()) {
					if (tp.getSegment(index) instanceof ComponentInstance_c) {
						result = (ComponentInstance_c) tp.getSegment(index);
						break;
					}
					if (tp.getSegment(index) instanceof Domain_c) {
						result = ComponentInstance_c
								.getOneI_EXEOnR2948((Domain_c) tp
										.getSegment(index));
						break;
					}
					if (tp.getSegment(index) instanceof Package_c) {
						result = ComponentInstance_c
						       .getOneI_EXEOnR2970((Package_c) tp
								                            .getSegment(index));
						if (result != null) {
							break;
						}
					}
					index--;
				}
			}
		}
		return (ComponentInstance_c) result;
	}

	/**
	 * This method will find all tree items that have the given string as their
	 * display name.  The method has a high limit that when reached will abort
	 * searching, as the session tree can be infinitely deep.  This high limit
	 * is set to 250.
	 * 
	 * @param item
	 * @param text
	 * @return
	 */
	public ArrayList<TreeItem> findItemsContainingText(TreeItem item, String text) {
		return findItemsContainingText(item, text, 0);
	}
	
    public ArrayList<TreeItem> findItemsContainingText(TreeItem item, String text, int count)
    {
    	count++;
        ArrayList<TreeItem> rvalItems = new ArrayList<TreeItem>();
    	if(count == 250) {
    		return rvalItems;
    	}
        if ( item == null ) {
            final Tree tree = getTree();
            TreeItem[] items = tree.getItems();
            // for each of the items given 
            for (int i = 0; i < items.length; i++) {
                // if this item's text matches that given
                if (items[i].getText().contains(text)) {
                    // return this item
                    rvalItems.add(items[i]);
                } else {
                	// look for nested elements
                	rvalItems.addAll(findItemsContainingText(items[i], text, count));
                }
            }
        } else {
            TreeItem[] items = item.getItems();
            // for each of the items given 
            for (int i = 0; i < items.length; i++) {
                // if this item's text matches that given
                if (items[i].getText().contains(text)) {
                    // return this item
                    rvalItems.add(items[i]);
                } else {
                	// look for nested elements
                	rvalItems.addAll(findItemsContainingText(items[i], text, count));
                }
            }            
        }
        
        return rvalItems;
    }

    public TreeItem findItem(String text )
    {
        final Tree tree = getTree();
        TreeItem[] items = tree.getItems();
        // for each of the items given 
        for (int i = 0; i < items.length; i++) {
            // if this item's text matches that given
            if (items[i].getText().equals(text)) {
                // return this item
                return items[i];
            }
        }
        
        return null;
    }
    
    public TreeItem findItem(TreeItem item, String text) {
        if(item == null) {
            final Tree tree = getTree();
            TreeItem[] items = tree.getItems();
            // for each of the items given 
            for (int i = 0; i < items.length; i++) {
                // if this item's text matches that given
                if (items[i].getText().equals(text)) {
                    // return this item
                    return items[i];
                } else {
                    TreeItem findItem = findItem(items[i], text);
                    if(findItem != null) {
                        return findItem;
                    }
                }
            }
        } else {
            TreeItem[] items = item.getItems();
            // for each of the items given 
            for (int i = 0; i < items.length; i++) {
                // if this item's text matches that given
                if (items[i].getText().equals(text)) {
                    // return this item
                    return items[i];
                } else {
                    TreeItem findItem = findItem(items[i], text);
                    if(findItem != null) {
                        return findItem;
                    }
                }
            }
        }
        return null;
    }

}