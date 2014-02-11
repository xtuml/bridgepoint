//========================================================================
//
//File:      $RCSfile: CodeChangesScraper.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:14:58 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License
//======================================================================== 

package com.mentor.nucleus.bp.internal.tools.process;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.team.ui.synchronize.ISynchronizePage;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.part.PageBookView;

/**
 * Captures the current contents of the Synchronize view in a form compatible 
 * with the Code Changes section of the implementation note (.int) file.
 * The output is written to the system clipboard, from where it may be pasted into 
 * an .int file.  To access this functionality, select the liked-named 
 * command from the Synchronize view's menu, after doing a compare between 
 * your branch and its root version to get the correct set of code changes for your 
 * issue displayed in the Synchronize view's tree.
 */
public class CodeChangesScraper implements IViewActionDelegate
{
    /**
     * The Synchronize view on which this scraper is operating.  
     * We have to store this across calls to this class's init() and run() 
     * IViewActionDelegate methods.
     */
    private IViewPart view;

    /* (non-Javadoc)
     * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
     */
    public void init(IViewPart view)
    {
        this.view = view;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action)
    {
        // locate the tree viewer within the synchronize-view
        ISynchronizePage page = (ISynchronizePage)((PageBookView)view).getCurrentPage();
        TreeViewer viewer = (TreeViewer)page.getViewer();
        
        // expand all nodes of the tree, as only the ones currently displayed 
        // are available to be scraped
        viewer.expandAll();

        StringBuffer changes = new StringBuffer("");
        
        // for each root node in the tree viewer
        Tree tree = viewer.getTree();
        TreeItem[] roots = tree.getItems(); 
        for (int i = 0; i < roots.length; i++) {
            // append the entries for the tree rooted at this node
            appendSubtreeText(roots[i], changes);
            changes.append("\n");
        }

        // output all entries to the system clipboard, to be pasted by the user
        // into an .int file
        StringSelection data = new StringSelection(changes.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(data, data);        
    }
    
    /**
     * Appends the code changes found for the tree of the given root to
     * the given buffer. 
     */
    private void appendSubtreeText(TreeItem root, StringBuffer changes)
    {
        // if the root has no children
        TreeItem[] children = root.getItems();
        if (children.length == 0) {
            // we have found an entity for which there was a code change,
            // so detm the entry's text 
            StringBuffer entry = new StringBuffer();
            appendItemText(root, entry);
            
            // if the entry is short enough to fit on one line
            final int maxLineLength = 80;
            if (entry.length() <= maxLineLength) {
                // append it as-is
                changes.append(entry);
            }
            
            // otherwise
            else {
                // break the entry into two lines at the last slash character
                // before the line length limit
                String firstRawLine = entry.substring(0, maxLineLength - 1);
                int lastSlashIndex = firstRawLine.lastIndexOf("/");
                String firstLine = entry.substring(0, lastSlashIndex + 1);
                String temp = entry.substring(lastSlashIndex + 1);
                String secondLine = entry.substring(lastSlashIndex + 1);
                if (temp.length() > maxLineLength - 4){
                    firstRawLine = temp.substring(0, maxLineLength - 5);
                    lastSlashIndex = firstRawLine.lastIndexOf("/");
                    String thirdLine = temp;
                    secondLine = firstRawLine.substring(0, lastSlashIndex + 1);
                    thirdLine = thirdLine.substring(lastSlashIndex + 1);
                    changes.append(firstLine);
                    changes.append("\n    ");
                    changes.append(secondLine);
                    changes.append("\n    ");
                    changes.append(thirdLine);
                }
                else{                
                    changes.append(firstLine);
                    changes.append("\n    ");
                    changes.append(secondLine);
                }
            }
            // the next entry will go on a new line
            changes.append("\n");
        }

        // otherwise
        else {    
            // for each child of the root
            for (int i = 0; i < children.length; i++) {
                // call this method recursively on this child, to get
                // the code changes for its subtree appended
                appendSubtreeText(children[i], changes);
            }
        }
    }

    /**
     * Appends the text of the given item to the given buffer, but 
     * only after appending the text for its ancestors in the tree.
     */
    private void appendItemText(TreeItem item, StringBuffer changes)
    {
        // if the item has a parent
        TreeItem parent = item.getParentItem();
        if (parent != null) {
            // call this method recursively on the parent, so that 
            // we get its text appended, first
            appendItemText(parent, changes);
        }
        
        // if the item's text contains a sequence of two spaces 
        // (likely meaning, it has been adorned with special labels by the 
        // synchronize-view), use only the preceding part 
        String text = item.getText();
        int spaceIndex = text.indexOf("  ");
        if (spaceIndex >= 0) text = text.substring(0, spaceIndex);

        // append the item's text
        if (parent != null) changes.append("/");
        changes.append(text);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection)
    {
        // nothing to do
    }
}
