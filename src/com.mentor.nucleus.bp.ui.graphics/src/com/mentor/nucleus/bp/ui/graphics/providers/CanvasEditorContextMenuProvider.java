//========================================================================
//
//File:      $RCSfile: CanvasEditorContextMenuProvider.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:06:36 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.providers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class CanvasEditorContextMenuProvider extends ContextMenuProvider {

	private GraphicalEditor fEditor;

	public CanvasEditorContextMenuProvider(EditPartViewer viewer,
			GraphicalEditor editor) {
		super(viewer);
		fEditor = editor;
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		final MenuManager createMenuManager = new MenuManager(
				"New", "com.mentor.nucleus.bp.ui.newroot"); //$NON-NLS-2$
		final MenuManager openMenuManager = new MenuManager(
				"Open With", "com.mentor.nucleus.bp.ui.openroot"); //$NON-NLS-2$
		final MenuManager classesMenu = new MenuManager("Classes", "com.mentor.nucleus.bp.ui.classroot"); //$NON-NLS-2$
		final MenuManager componentsMenu = new MenuManager("Components", "com.mentor.nucleus.bp.ui.componentroot"); //$NON-NLS-2$
		final MenuManager externalMenu = new MenuManager("External", "com.mentor.nucleus.bp.ui.externalroot"); //$NON-NLS-2$
		final MenuManager interactionMenu = new MenuManager("Interaction", "com.mentor.nucleus.bp.ui.interactionroot"); //$NON-NLS-2$
		final MenuManager activityMenu = new MenuManager("Activity", "com.mentor.nucleus.bp.ui.activityroot"); //$NON-NLS-2$
		final MenuManager typesMenu = new MenuManager("Types", "com.mentor.nucleus.bp.ui.typeroot"); //$NON-NLS-2$
		final MenuManager useCaseMenu = new MenuManager("Usecase", "com.mentor.nucleus.bp.ui.usecaseroot"); //$NON-NLS-2$

		
		menu.add(createMenuManager);
		createMenuManager.removeAll();
		createMenuManager.add(new GroupMarker(
				"com.mentor.nucleus.bp.ui.newmenu")); //$NON-NLS-1$
		
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
		
		menu.add(fEditor.getOpenAction());
		menu.add(openMenuManager);
		openMenuManager.removeAll();
		openMenuManager
				.add(new GroupMarker("com.mentor.nucleus.bp.ui.openmenu")); //$NON-NLS-1$
		menu.add(new Separator("com.mentor.nucleus.bp.ui.context-internal")); //$NON-NLS-1$
		menu
				.add(new Separator(
						"com.mentor.nucleus.bp.ui.context-internal-end")); //$NON-NLS-1$
		menu.add(new Separator());
		menu.add(fEditor.getUndoAction());
		menu.add(fEditor.getRedoAction());
		menu.add(new Separator());
		menu.add(fEditor.getCutAction());
		// for some reason we must specifically set enabled here
		fEditor.getCutAction().setEnabled(fEditor.getCutAction().isEnabled());
		menu.add(fEditor.getCopyAction());
		fEditor.getCopyAction().setEnabled(fEditor.getCopyAction().isEnabled());
		
		menu.add(fEditor.getPasteAction());
		fEditor.getPasteAction().setEnabled(fEditor.getPasteAction().isEnabled());
		menu.add(new Separator());
		menu.add(fEditor.getSelectAllAction());
		fEditor.getDeleteAction().setEnabled(
				enableDelete((IStructuredSelection) fEditor.getSite()
						.getSelectionProvider().getSelection()));
		menu.add(fEditor.getDeleteAction());
		fEditor.getRenameAction().setEnabled(RenameAction.canRenameAction());
		menu.add(fEditor.getRenameAction());
		menu.add(new Separator());
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator());
		menu.add(CorePlugin.getResourceImportAction());
		menu.add(CorePlugin.getResourceExportAction());
	}

	public static boolean enableDelete(IStructuredSelection graphicalSelection) {

        boolean enableDel = false;
        List<Object> elements = new ArrayList<Object>();
        IStructuredSelection selection = Selection.getInstance().getStructuredSelection();

        if ( !selection.isEmpty() ) {
        	if(graphicalSelection.getFirstElement() instanceof DiagramEditPart)
        		return false;
          enableDel = true;
          // Iterate through removing elements that are only graphical
          for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
              Object current = iter.next();
              if ( current instanceof GraphicalElement_c ) {
                  GraphicalElement_c ge = (GraphicalElement_c) current;
                  if(ge.getRepresents() == null) {
                      elements.add(current);
                      Selection.getInstance().removeFromSelection(ge);
                 }
              }
          }
        }

        selection = Selection.getInstance().getStructuredSelection();
        if ( !selection.isEmpty() ) {
            // Check the remaining items against the usual DeleteAction,
            enableDel = DeleteAction.canDeleteAction();
        }

        // Add the graphical only elements back to the selection
        for ( int i=0; i < elements.size(); ++i) {
            Selection.getInstance().addToSelection(elements.get(i));
        }

        return enableDel;
    
	}
}
