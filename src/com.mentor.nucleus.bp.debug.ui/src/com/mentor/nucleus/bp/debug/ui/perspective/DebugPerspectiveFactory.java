package com.mentor.nucleus.bp.debug.ui.perspective;
//====================================================================
//
// File:      $RCSfile: DebugPerspectiveFactory.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2013/01/10 23:18:20 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
import org.eclipse.debug.ui.IDebugUIConstants;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.mentor.nucleus.bp.debug.ui.IBPDebugUIPluginConstants;


public class DebugPerspectiveFactory implements IPerspectiveFactory {
    public void createInitialLayout(IPageLayout layout) {
        //top
        IFolderLayout debugFolder = layout.createFolder(IBPDebugUIPluginConstants.FOLDER_DEBUG,
                IPageLayout.TOP, (float) 0.25, layout.getEditorArea());

        debugFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_DEBUG);

        IFolderLayout toolsFolder = layout.createFolder(IBPDebugUIPluginConstants.FOLDER_TOOLS,
                IPageLayout.RIGHT, (float) 0.50, "debugFolder");

        toolsFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_VARIABLE);
        toolsFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_BREAKPOINT);
        toolsFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_EXPRESSION);

        //left
        IFolderLayout navigatorFolder = layout.createFolder(IBPDebugUIPluginConstants.FOLDER_PJTEXPLORER,
                IPageLayout.LEFT, (float) 0.25, layout.getEditorArea());

        navigatorFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_EXPLORER);
        navigatorFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_INSTANCE);
        navigatorFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_OUTLINE);
        navigatorFolder.addView(IBPDebugUIPluginConstants.ID_VIEW_MONITOR);

        //bottom
        IFolderLayout consoleFolder = layout.createFolder(IBPDebugUIPluginConstants.FOLDER_CONSOLE,
                IPageLayout.BOTTOM, (float) 0.70, layout.getEditorArea());
        consoleFolder.addView(IPageLayout.ID_PROP_SHEET);
        consoleFolder.addPlaceholder(IPageLayout.ID_TASK_LIST);

        setContentActionSet(layout);

        setContentsOfShowViewMenu(layout);
    }

    private void setContentActionSet(IPageLayout layout) {
        layout.addActionSet(IDebugUIConstants.LAUNCH_ACTION_SET);
        layout.addActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
    }

    /**
    * Sets the intial contents of the "Show View" menu.
    */
    protected void setContentsOfShowViewMenu(IPageLayout layout) {
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_DEBUG);
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_VARIABLE);
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_BREAKPOINT);
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_EXPRESSION);
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_INSTANCE);
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_EXPLORER);
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_MONITOR);
        layout.addShowViewShortcut(IBPDebugUIPluginConstants.ID_VIEW_OUTLINE);

        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
    }
}
