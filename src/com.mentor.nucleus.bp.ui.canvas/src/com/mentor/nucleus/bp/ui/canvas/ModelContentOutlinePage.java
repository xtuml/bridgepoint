package com.mentor.nucleus.bp.ui.canvas;
//=====================================================================
//
// File:      $RCSfile: ModelContentOutlinePage.java,v $
// Version:   $Revision: 1.44 $
// Modified:  $Date: 2013/01/10 23:19:00 $
//
// (c) Copyright 2003-2014 Mentor Graphics Corporation All rights reserved.
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
//
// This class provides the BridgePoint user with a tree of model
// elements whose root is the current selection.
//
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Scope_c;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.ui.explorer.ModelContentProvider;
import com.mentor.nucleus.bp.ui.explorer.ModelLabelProvider;
/**
 * Page for the content outliner
 */
public class ModelContentOutlinePage extends ContentOutlinePage {
  private static final String OPEN = "open";
  private static final String SORT = "Sort";
  private static final String HIDE_STATICS = "Hide Static Members";
  private static final String HIDE_OPERATIONS = "Hide Operations";
  protected Action cut, copy, paste, undo, redo;
  protected Action open, delete, rename;
  protected Action sort, hideStatics, hideOps;
  protected ViewerFilter filterStatics, filterOps;
  private Object instance;
  private boolean sorting = false;
  private ITransactionListener m_transactionListener;

  /**
   * Create a new instance of the reciver using adapatable
   * as the model.
   */
  public ModelContentOutlinePage(
    Object instance) {
    this.instance = instance;
  }
  /**
   * Creates the control and registers the popup menu for this page
   * Menu id "com.mentor.nucleus.bp.ui.canvas.outline"
   */
  public void createControl(Composite parent) {
    super.createControl(parent);
    TreeViewer viewer = super.getTreeViewer();
    viewer.setContentProvider(new ModelContentProvider());
    viewer.setLabelProvider(new ModelLabelProvider());
    getSite().setSelectionProvider(Selection.getInstance());
    createActions();
    createMenus();
    createToolbar();
    createFilters();
    hookListeners();
    viewer.setInput(this.instance);
    getSite().getActionBars().setGlobalActionHandler(
                              ActionFactory.DELETE.getId(), delete);
    getSite().getActionBars().setGlobalActionHandler(
                              ActionFactory.RENAME.getId(), rename);
    getSite().getActionBars().setGlobalActionHandler(
                              ActionFactory.CUT.getId(), cut);
    getSite().getActionBars().setGlobalActionHandler(
                              ActionFactory.COPY.getId(), copy);
    getSite().getActionBars().setGlobalActionHandler(
                              ActionFactory.PASTE.getId(), paste);
    getSite().getActionBars().setGlobalActionHandler(
    						  ActionFactory.UNDO.getId(), undo);
    getSite().getActionBars().setGlobalActionHandler(
			  				  ActionFactory.REDO.getId(), redo);
  }
  protected void hookListeners() {
    getTreeViewer()
      .addSelectionChangedListener(new ISelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSelection().isEmpty()) {
          return;
        }
        if (event.getSelection() instanceof IStructuredSelection) {
          Selection.getInstance().setSelection(event.getSelection());
        }
      }
    });
	m_transactionListener = new ITransactionListener() {
		@Override
		public void transactionCancelled(Transaction transaction) {
			// TODO Auto-generated method stub

		}

		@Override
		public void transactionEnded(Transaction transaction) {
		  UIUtil.refreshViewer(getTreeViewer(), null);
		}

		@Override
		public void transactionStarted(Transaction transaction) {
			// TODO Auto-generated method stub

		}
	};
	TransactionManager.getSingleton().addTransactionListener(m_transactionListener);

    getTreeViewer().addOpenListener(new IOpenListener() {
      public void open(OpenEvent event) {
        handleOpen();
      }
    });
  }
  public void selectionChanged(SelectionChangedEvent event) {
    super.selectionChanged(event);
    Selection.getInstance().setSelection(event.getSelection());
  }
  protected void createActions() {
    // 'New' is provided as a menu only. See 'createMenus'
    open = new Action(OPEN){
      public void run(){
        handleOpen();
      }
    };
    open.setText("Open");
    open.setToolTipText("Open this model Element");
    // 'Open With' is provided as a menu only. See 'createMenus'
    cut = new Action(ActionFactory.CUT.getId()){
      public void run(){
      }
    };
    cut.setText("Cut");
    cut.setToolTipText("Cut this model Element to the clipboard");
    cut.setImageDescriptor(CorePlugin.getImageDescriptor("cut_edit.gif")); //$$NON-NLS-1$$
    cut.setEnabled(false); // Retargetable Actions work removes this line
    copy = new Action(ActionFactory.COPY.getId()){
      public void run(){
      }
    };
    copy.setText("Copy");
    copy.setToolTipText("Copy this model Element to the clipboard");
    copy.setImageDescriptor(CorePlugin.getImageDescriptor("copy_edit.gif")); //$$NON-NLS-1$$
    copy.setEnabled(false); // Retargetable Actions work removes this line
    paste = new Action(ActionFactory.PASTE.getId()){
      public void run(){
      }
    };
    paste.setText("Paste");
    paste.setToolTipText("Paste from the clipboard");
    paste.setImageDescriptor(CorePlugin.getImageDescriptor("paste_edit.gif")); //$$NON-NLS-1$$
    paste.setEnabled(false); // Retargetable Actions work removes this line

    //
    // Delete and Rename are retargetable actions defined by core.
    //
    delete = CorePlugin.getDeleteAction();
    rename = CorePlugin.getRenameAction(getTreeViewer());
    sort = new Action(SORT) {
      public void run() {
        sorting = !sorting;
        if (sorting) {
          getTreeViewer().setSorter(new ModelOutlineSorter());
        } else {
          getTreeViewer().setSorter(null);
        }
      }
    };
    sort.setChecked(false);
    sort.setToolTipText(SORT);
    sort.setImageDescriptor(CorePlugin.getImageDescriptor("alpha_mode.gif")); //$NON-NLS-1$
    hideStatics = new Action(HIDE_STATICS) {
      public void run() {
        updateFilter(hideStatics);
      }
    };
    hideStatics.setChecked(false);
    hideStatics.setToolTipText(HIDE_STATICS);
    hideStatics.setImageDescriptor(CorePlugin.getImageDescriptor("static_co.gif")); //$NON-NLS-1$
    hideOps = new Action(HIDE_OPERATIONS) {
      public void run() {
        updateFilter(hideOps);
      }
    };
    hideOps.setChecked(false);
    hideOps.setToolTipText(HIDE_OPERATIONS);
    hideOps.setImageDescriptor(CorePlugin.getImageDescriptor("methpub_obj_co.gif")); //$NON-NLS-1$
  }
  protected void createFilters() {
    filterStatics = new ViewerFilter() {
      public boolean select(
        Viewer viewer,
        Object parentElement,
        Object element) {
        if (element instanceof Operation_c) {
          return (((Operation_c) element).getInstance_based() == Scope_c.Instance);
        }
        if (element instanceof Function_c) {
          return false;
        }
        return true;
      }
    };
    filterOps = new ViewerFilter() {
      public boolean select(
        Viewer viewer,
        Object parentElement,
        Object element) {
        return !(element instanceof Operation_c);
      }
    };
  }
  protected void createMenus() {
    // Configure the context menu.
    MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
    menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS + "-end")); //$NON-NLS-1$
    MenuManager createMenuManager = new MenuManager("New", "com.mentor.nucleus.bp.ui.newroot"); //$NON-NLS-2$
    MenuManager openMenuManager = new MenuManager("Open With", "com.mentor.nucleus.bp.ui.openroot"); //$NON-NLS-2$    
	final MenuManager classesMenu = new MenuManager("Classes", "com.mentor.nucleus.bp.ui.classroot"); //$NON-NLS-2$
	final MenuManager componentsMenu = new MenuManager("Components", "com.mentor.nucleus.bp.ui.componentroot"); //$NON-NLS-2$
	final MenuManager externalMenu = new MenuManager("External", "com.mentor.nucleus.bp.ui.externalroot"); //$NON-NLS-2$
	final MenuManager interactionMenu = new MenuManager("Interaction", "com.mentor.nucleus.bp.ui.interactionroot"); //$NON-NLS-2$
	final MenuManager activityMenu = new MenuManager("Activity", "com.mentor.nucleus.bp.ui.activityroot"); //$NON-NLS-2$
	final MenuManager typesMenu = new MenuManager("Types", "com.mentor.nucleus.bp.ui.typeroot"); //$NON-NLS-2$
	final MenuManager useCaseMenu = new MenuManager("Usecase", "com.mentor.nucleus.bp.ui.usecaseroot"); //$NON-NLS-2$
    
    menuMgr.add(createMenuManager);
    createMenuManager.removeAll();
    createMenuManager.add(new GroupMarker("com.mentor.nucleus.bp.ui.newmenu")); //$NON-NLS-1$

	createMenuManager.add(activityMenu);
	activityMenu.removeAll();
	activityMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newactivitymenu") );
	
	createMenuManager.add(classesMenu);
	classesMenu.removeAll();
	classesMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newclassmenu") );
	
	createMenuManager.add(componentsMenu);
	componentsMenu.removeAll();
	componentsMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newcomponentmenu") );
	
	createMenuManager.add(externalMenu);
	externalMenu.removeAll();
	externalMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newexternalmenu") );
	
	createMenuManager.add(interactionMenu);
	interactionMenu.removeAll();
	interactionMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newinteractionmenu") );
	
	createMenuManager.add(typesMenu);
	typesMenu.removeAll();
	typesMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newtypemenu") );
	
	createMenuManager.add(useCaseMenu);
	useCaseMenu.removeAll();
	useCaseMenu.add(new GroupMarker("com.mentor.nucleus.bp.ui.newusecasemenu") );
    
    menuMgr.add(open);
    menuMgr.add(openMenuManager);
    openMenuManager.removeAll();
    openMenuManager.add(new GroupMarker("com.mentor.nucleus.bp.ui.openmenu")); //$NON-NLS-1$
    menuMgr.add(new Separator("com.mentor.nucleus.bp.ui.context-internal")); //$NON-NLS-1$
    menuMgr.add(new Separator("com.mentor.nucleus.bp.ui.context-internal-end")); //$NON-NLS-1$

    // add the undo/redo actions
    TransactionManager manager = ((NonRootModelElement)instance).getTransactionManager();
    undo = manager.getUndoAction();
    redo = manager.getRedoAction();
    menuMgr.add(new Separator());
    menuMgr.add(undo);
    menuMgr.add(redo);

    menuMgr.add(new Separator());
    menuMgr.add(cut);
    menuMgr.add(copy);
    menuMgr.add(paste);
    menuMgr.add(delete);
    menuMgr.add(rename);
    TreeViewer viewer = getTreeViewer();
    Menu menu = menuMgr.createContextMenu(viewer.getTree());
    viewer.getTree().setMenu(menu);
    // Be sure to register it so that other plug-ins can add actions.
    getSite().registerContextMenu("com.mentor.nucleus.bp.ui.canvas.outline", menuMgr, viewer); //$NON-NLS-1$
  }
  /* Multiple filters can be enabled at a time. */
  protected void updateFilter(Action action) {
    if (action == hideStatics) {
      if (action.isChecked()) {
        getTreeViewer().addFilter(filterStatics);
      } else {
        getTreeViewer().removeFilter(filterStatics);
      }
      return;
    }
    if (action == hideOps) {
      if (action.isChecked()) {
        getTreeViewer().addFilter(filterOps);
      } else {
        getTreeViewer().removeFilter(filterOps);
      }
      return;
    }
  }
  protected void createToolbar() {
    IToolBarManager toolBarManager =
      getSite().getActionBars().getToolBarManager();
    toolBarManager.add(sort);
    toolBarManager.add(hideStatics);
    toolBarManager.add(hideOps);
  }
  /**
   * Fire up an editor
   */
  private void handleOpen() {
  	IStructuredSelection sel = Selection.getInstance().getStructuredSelection();
    if (sel.isEmpty()) {
      return;
    }
    Object current = (sel).iterator().next();
    String name = current.getClass().getName();
    
    // see if the current element should open
    // something other than itself
    current = EditorUtil.getElementToEdit(current);
    //
    // Get the registry
    //
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    //
    // Get all the plugins that have extended this point
    //
    IExtensionPoint extPt = reg.getExtensionPoint("com.mentor.nucleus.bp.core.editors");
    IExtension[] exts = extPt.getExtensions();
    // Repeat for each extension until we find a default editor
    for (int i=0; i < exts.length; i++) {
      IConfigurationElement[] elems = exts[i].getConfigurationElements();
      for (int j=0; j < elems.length; j++) {
        // Find the editor elements
        if (elems[j].getName().equals("editor")){
          IConfigurationElement[] edElems = elems[j].getChildren();
          for (int k=0; k < edElems.length; k++) {
            //
            // Is this editor the default for the current model element ?
            //
            if (edElems[k].getName().equals("defaultFor") &&
                edElems[k].getAttribute("class").equals(name) ) {
              try {
                //
                // Get the class supplied for the input
                //
                Bundle bundle = Platform.getBundle(elems[j].getDeclaringExtension().getNamespace());
                Class inputClass = bundle.loadClass(elems[j].getAttribute("input")); //$NON-NLS-1$
                Class[] type = new Class[1];
                type[0] = Object.class;
                //
                // Dynamically get the method createInstance, the supplied class must implement this
                //
                Method createInstance = inputClass.getMethod("createInstance", type); //$NON-NLS-1$
                Object[] args = new Object[1];
                args[0] = current;
                //
                // Invoke the method.
                // The method is static; no instance is needed, so first argument is null
                //
                IEditorInput input = (IEditorInput)createInstance.invoke(null, args);
                //
                // pass the input to the Eclipse editor, along with the class name supplied by
                // the extending plugin.
                //
                if (input != null) {
                  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
                    input,
                    elems[j].getAttribute("class"));  //$NON-NLS-1$
                }
                return;
              }
              catch (ClassNotFoundException e){
                CanvasPlugin.logError("Input Class not found", e); //$NON-NLS-1$
              }
              catch (NoSuchMethodException e) {
              	CanvasPlugin.logError("Class does not implement static method createInstance", e); //$NON-NLS-1$
              }
              catch (InvocationTargetException e) {
              	CanvasPlugin.logError("Exception occured on invocation of static method createInstance of the Target", e.getTargetException()); //$NON-NLS-1$
              }
              catch (IllegalAccessException e) {
              	CanvasPlugin.logError("Target does not support static method createInstance", e); //$NON-NLS-1$
              }
              catch (PartInitException e) {
              	CanvasPlugin.logError("Could not activate Editor", e); //$NON-NLS-1$
              }
            }
          }
        }
      }
    }
  }

    public void dispose() {
        super.dispose();
        TransactionManager.getSingleton().removeTransactionListener(m_transactionListener);
    }

    public TreeViewer getTreeViewer() {
        return super.getTreeViewer();
    }

}