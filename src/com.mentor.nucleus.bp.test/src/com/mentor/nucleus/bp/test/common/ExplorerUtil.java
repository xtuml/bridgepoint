//=====================================================================
//
//File:      $RCSfile: ExplorerUtil.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 23:21:32 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.test.common;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;


public class ExplorerUtil {
	private static ExplorerView explorer;
    
    private static TreeViewer getTreeV() {
        return BaseTest.getMETreeViewer();
    }
    /**
     * Perform rename on the currently selected model explorer tree item
     * @param newName
     */
    public static void showModelExplorer()
    {
    	CanvasUtilities.showModelExplorer();
    	explorer = CanvasUtilities.getView();
    }
    public static ExplorerView getView()
    {
    	return explorer;
    }
    public static void setView(ExplorerView ev)
    {
        explorer=ev;
        CanvasUtilities.setView(null);
    }
	public static void doRenameThruMExplorer(String newName) {
		RenameAction t2 = (RenameAction)CorePlugin.getRenameAction(BaseTest.getMETreeViewer());
		if(!t2.isEnabled())
			return;
		t2.run();
		t2.getTextEditor().setText(newName);
		Event e = new Event();
		e.type = SWT.Traverse;
		e.detail = SWT.TRAVERSE_RETURN;
		e.widget = t2.getTextEditor();
		t2.getTextEditor().notifyListeners(e.type, e);
	}
	public static void expandAll()
	{
		if(explorer == null)
    	{
			showModelExplorer();
			explorer.getTreeViewer().expandAll();
    	}
		else
		{
			explorer.getTreeViewer().expandAll();
		}
	}
	public static void setLinkWithEditor(boolean flag)
	{
		explorer.setLinkWithEditor(true);
	}
	public static void renameItem(String newName) {
		// this method is working and complete
		RenameAction t2 = (RenameAction)CorePlugin.getRenameAction(explorer.getTreeViewer());
		if(!t2.isEnabled())
			return ;
		t2.run();
		t2.getTextEditor().setText(newName);
		Event e = new Event();
		e.type = SWT.Traverse;
		e.detail = SWT.TRAVERSE_RETURN;
		e.widget = t2.getTextEditor();
		t2.getTextEditor().notifyListeners(e.type, e);
		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
		
	}
	static public void checkTreeItemDeletion(NonRootModelElement modelElement)
    {
	   //  the tree must be focused for the selection to be
        // be reported to the model explorer 
        TreeViewer viewer = explorer.getTreeViewer();
        final Tree tree = viewer.getTree();
        boolean focused = tree.setFocus();
        TestCase.assertTrue("Could not focus model explorer tree", focused);
        
        // select the node in the tree; note that we must specify
        // the model element, not its tree-item 
        viewer.setSelection(new StructuredSelection(
            new Object[] {modelElement}), false);
        
        // since this test is running on the event-dispatch thread, we
        // have to fire the event pump manually to get the 
        // selection event reported, before proceeding
        Display display = Display.getCurrent();
        while (display.readAndDispatch());
        TestCase.assertTrue("Tree Item still exist after deletion ", tree.getSelectionCount()==0);
    }
	static public void checkTreeItemExistance(NonRootModelElement modelElement,String name)
    {
    	// the tree must be focused for the selection to be
        // be reported to the model explorer 
        TreeViewer viewer = explorer.getTreeViewer();
        final Tree tree = viewer.getTree();
        boolean focused = tree.setFocus();
        TestCase.assertTrue("Could not focus model explorer tree", focused);
        
        // select the node in the tree; note that we must specify
        // the model element, not its tree-item 
        viewer.setSelection(new StructuredSelection(
            new Object[] {modelElement}), false);
        
        // since this test is running on the event-dispatch thread, we
        // have to fire the event pump manually to get the 
        // selection event reported, before proceeding
        Display display = Display.getCurrent();
        while (display.readAndDispatch());
        TestCase.assertTrue("Tree Item with text '"+name+"' dz not exist", 
        			tree.getSelection()[0].getText().equals(name));
    }
	/**
     * Select the NonRootModelElement from Model Explorer
	 * @return 
     */
    public static TreeItem selectItem(Object item)
    {
        // the tree must be focused for the selection to be
        // be reported to the model explorer 
        TreeViewer viewer = explorer.getTreeViewer();
        final Tree tree = viewer.getTree();
        boolean focused = tree.setFocus();
        TestCase.assertTrue("Could not focus model explorer tree", focused);
        
        // select the node in the tree; note that we must specify
        // the model element, not its tree-item 
        viewer.setSelection(new StructuredSelection(
            new Object[] {item}), false);
        
        // since this test is running on the event-dispatch thread, we
        // have to fire the event pump manually to get the 
        // selection event reported, before proceeding
        Display display = Display.getCurrent();
        while (display.readAndDispatch());
        TestCase.assertTrue("Tree Item not selected ", tree.getSelectionCount()>0);
        return tree.getSelection()[0];
    }
    /**
     * Returns the Tree Viewer of Model Explorer
     */
    public static TreeViewer getTreeViewer()
    {
       	return BaseTest.getMETreeViewer();
    }
    /**
     * Delete the selected Element and thier childs 
     * from Model Explorer
     */
    public static void deleteItem()
    {
    	explorer.doDelete();
    }
    /**
     * Open the selected Item from Model Explorer
     */
    public static IEditorPart openEditor()
    {
    	return explorer.doOpen();
    }
    public static TreeItem findRootTreeItem(IPath path) {
        path = path.removeFirstSegments(2);
        path = path.removeLastSegments(1);

        IPersistenceHierarchyMetaData metaData = PersistenceManager
                .getHierarchyMetaData();

        int index = 0;

        boolean focused = explorer.getTreeViewer().getTree().setFocus();

        while (Display.getCurrent().readAndDispatch())
            ;
        focused = explorer.getTreeViewer().getTree().setFocus();
        TestCase.assertTrue("Could not focus model explorer tree", focused);

        // Find projects tree items
        TreeItem[] items = explorer.getTreeViewer().getTree().getItems();
        TreeItem item = null;
        while (index < path.segmentCount()) {
            item = null;
            for (int i = 0; i < items.length; i++) {
                String itemText = metaData
                        .getRootElementName((NonRootModelElement) items[i]
                                .getData());
                if (path.segment(index).equals(itemText)) {
                    item = items[i];
                    if (i == 0)
                    	explorer.getTreeViewer().expandToLevel(2);

                    items = getChildren(item);
                    index++;
                    break;
                }
            }

            if (item == null) {
                return null;
            }
        }

        return item;
    }
    
    public static TreeItem[] getChildren(TreeItem item) {

    	explorer.getTreeViewer().expandToLevel(item.getData(), 1);
        return item.getItems();
    }
    /**
     * Selects the given item in the model explorer tree.
     */
    public static void selectItem(TreeItem item)
    {
        // the tree must be focused for the selection to be
        // be reported to the model explorer 
        TreeViewer viewer = explorer.getTreeViewer();
        final Tree tree = viewer.getTree();
        boolean focused = tree.setFocus();
        TestCase.assertTrue("Could not focus model explorer tree", focused);
        
        // select the node in the tree; note that we must specify
        // the model element, not its tree-item 
        viewer.setSelection(new StructuredSelection(
            new Object[] {item.getData()}), false);
        
        // since this test is running on the event-dispatch thread, we
        // have to fire the event pump manually to get the 
        // selection event reported, before proceeding
        Display display = Display.getCurrent();
        while (display.readAndDispatch());
    }
    
    public static TreeItem selectMEInModelExplorer(IPath itemPath) {
    	getTreeViewer().refresh();
        while (Display.getCurrent().readAndDispatch())
            ;
        TreeItem treeItem = findRootTreeItemFor(itemPath);
        Assert.assertNotNull(treeItem);
        selectItem(treeItem.getData());
        return treeItem;
    }
    /**
     * Returns the item amongst those given which has the given 
     * text as its text.
     */
    public static TreeItem findItem(NonRootModelElement element)
    {
    	explorer.getTreeViewer().expandToLevel(element, TreeViewer.ALL_LEVELS);
    	explorer.getTreeViewer().setSelection(new StructuredSelection(element));

        TreeItem[] selectedItems = explorer.getTreeViewer().getTree().getSelection();

        return (selectedItems.length > 0) ? selectedItems[0] : null;
    }
    
    /**
     * Returns the item amongst those given which has the given 
     * text as its text.
     */
    public static TreeItem findItem(String text )
    {
    	TreeViewer viewer = explorer.getTreeViewer();
        final Tree tree = viewer.getTree();
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
    
    public static TreeItem findItem(TreeItem item, String text) {
    	if(item == null) {
        	TreeViewer viewer = explorer.getTreeViewer();
            final Tree tree = viewer.getTree();
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
    
    /**
     * Returns the item amongst those given which has the given 
     * text as its text.
     */
    public static TreeItem findItem(String text,  TreeItem[] items )
    {
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
    
	
	/**
     * Select tree item representing data in model explorer tree and Perform
     * rename on the currently selected model explorer tree item
     * @param newName
     */
	public static void doRenameThruMExplorer(Object data,String newName) {
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(data);
		doRenameThruMExplorer(newName);
	}
	
    public static TreeItem findTreeItem(Object data) {
    	Display display=Display.getCurrent();
    	
        getTreeV().expandToLevel(data, TreeViewer.ALL_LEVELS);
        getTreeV().setSelection(new StructuredSelection(data));
        while(display.readAndDispatch());
        TreeItem[] selectedItems = getTreeV().getTree().getSelection();

        return (selectedItems.length > 0) ? selectedItems[0] : null;
    }
    
    static public TreeItem findChildComponentTreeItemInMEFor(
            PersistableModelComponent component, ISelectionCriteria criteria) {
        TreeItem item = findRootTreeItemFor(component.getFullPath());
        while (item != null) {
            item = findTreeItemInMEFor(component, true, criteria, item);
            Object data = item.getData();
            if(data instanceof NonRootModelElement){
                if(!component.getFile().equals(((NonRootModelElement)item.getData()).getFile())){
                return item;
            }
        }
        }

        return null;
    }

    static public TreeItem findTreeItemInMEFor(
            PersistableModelComponent component, ISelectionCriteria criteria) {
        // do not want to trigger load of all components during search therefore
        // do not use sequential search.
        TreeItem item = findRootTreeItemFor(component.getFullPath());
        if (item != null) {
            return findTreeItemInMEFor(component, false, criteria, item);
        }
        return null;
    }

    static private TreeItem findTreeItemInMEFor(
            PersistableModelComponent component,
            boolean includeChildrenComponent, ISelectionCriteria criteria,
            TreeItem item) {
        if(!(item.getData() instanceof NonRootModelElement))
            return null;
        NonRootModelElement me = (NonRootModelElement) item.getData();
        if ((component.getFile() != me.getFile())
                && !includeChildrenComponent)
            return null;

        if (criteria.select(me)) {
            return item;
        }

        if (includeChildrenComponent
                || (!includeChildrenComponent && component.getFile().equals(
                        me.getFile()))) {
            TreeItem[] children = getChildrenAfterExpansion(item);
            TreeItem childItem = null;
            for (int i = 0; i < children.length; i++) {
                childItem = findTreeItemInMEFor(component,
                        includeChildrenComponent, criteria, children[i]);
                if (childItem != null) {
                    return childItem;
                }
            }
        }
        return null;
    }

    static private TreeItem[] getChildrenAfterExpansion(TreeItem item) {

        getTreeV().expandToLevel(item.getData(), 1);
        return item.getItems();
    }

    

    static public TreeItem findRootTreeItemFor(IPath path) {
        path = path.removeFirstSegments(2);
        path = path.removeLastSegments(1);

        IPersistenceHierarchyMetaData metaData = PersistenceManager
                .getHierarchyMetaData();

        int index = 0;

        boolean focused = getTreeV().getTree().setFocus();

        while (Display.getCurrent().readAndDispatch())
            ;
        focused = getTreeV().getTree().setFocus();
        Assert.assertTrue("Could not focus model explorer tree", focused);

        // Find projects tree items
        TreeItem[] items = getTreeV().getTree().getItems();
        TreeItem item = null;
        while (index < path.segmentCount()) {
            item = null;
            for (int i = 0; i < items.length; i++) {
                
                String itemText = metaData
                        .getRootElementName((NonRootModelElement) items[i]
                                .getData());
                if (path.segment(index).equals(itemText)) {
                    item = items[i];
                    if (i == 0)
                        getTreeV().expandToLevel(2);

                    items = getChildrenAfterExpansion(item);
                    index++;
                    break;
                }

            }

            if (item == null) {
                return null;
            }
        }

        return item;
    }
    static public interface ISelectionCriteria {
        public boolean select(Object item);
    }

    static public class GenericModelElementCriteria implements
            ISelectionCriteria {
        public int editorType = -1;

        public String name = null;

        public void init() {
            editorType = -1;
            name = null;
        }

        public void init(String aName) {
            editorType = -1;
            name = aName;
        }

        public void init(int aEditorType) {
            editorType = aEditorType;
            name = null;
        }

        public boolean select(Object item) {
            if (item instanceof NonRootModelElement) {
                boolean toSelect = false;
                NonRootModelElement me = (NonRootModelElement) item;
                if (editorType != -1) {
                    toSelect = GenericEditorUtil.supportsEditor(me,
                            editorType);
                }

                if (!toSelect && name != null) {
                    IPersistenceHierarchyMetaData metaData = PersistenceManager
                            .getHierarchyMetaData();
                    toSelect = metaData.getRootElementName(me).equals(name);
                }

                return toSelect;
            }

            return false;
        }
    }
}
