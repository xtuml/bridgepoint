package com.mentor.nucleus.bp.core.ui.perspective;
//====================================================================
//
//File:      $RCSfile: BridgePointPerspective.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/03/13 23:15:37 $
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
//This class declares the the BridgePoint perspective. A perspective
//is a definition of the layout of windows for a particular activity.
//
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class BridgePointPerspective implements IPerspectiveFactory {
  public static final String ID_MGC_BP_EXPLORER = "com.mentor.nucleus.bp.ui.explorer.ExplorerView";//$NON-NLS-1$
  /* (non-Javadoc)
   * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
   */
  public void createInitialLayout(IPageLayout layout) {
//  Get the editor area.
  String editorArea = layout.getEditorArea();

  // Top left: Resource Navigator view and Bookmarks view placeholder
  IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.30f,  //$NON-NLS-1$
     editorArea);
  topLeft.addView(ID_MGC_BP_EXPLORER);
  topLeft.addPlaceholder(IPageLayout.ID_BOOKMARKS);

  // Bottom right: Problem List view and Property Sheet view
  IFolderLayout bottomCenter = layout.createFolder(
      "bottomCenter", IPageLayout.BOTTOM, 0.6f, editorArea);  //$NON-NLS-1$
  bottomCenter.addView(IPageLayout.ID_PROP_SHEET);

  // Bottom right: Problem List view and Property Sheet view
  IFolderLayout bottomRight = layout.createFolder(
      "bottomRight", IPageLayout.RIGHT, 0.6f, "bottomCenter");  //$NON-NLS-1$
  bottomRight.addView(IPageLayout.ID_PROBLEM_VIEW);
  bottomRight.addView(IPageLayout.ID_OUTLINE);

  // Right center: Palette View
  IFolderLayout top = layout.createFolder("rightCenter", IPageLayout.RIGHT, 0.7f, editorArea);
  top.addView("org.eclipse.gef.ui.palette_view");

  layout.addShowViewShortcut(ID_MGC_BP_EXPLORER);
  layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
  layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
  layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
  layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
  layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);

  layout.addNewWizardShortcut("com.mentor.nucleus.bp.NewWizardSystem");//$NON-NLS-1$
  layout.addNewWizardShortcut("com.mentor.nucleus.bp.NewWizardDomain");//$NON-NLS-1$
                          
  layout.addPerspectiveShortcut("com.mentor.nucleus.bp.debug.ui.DebugPerspective");//$NON-NLS-1$
  layout.addPerspectiveShortcut("org.eclipse.cdt.ui.CPerspective");
  layout.addPerspectiveShortcut("org.eclipse.egit.ui.GitRepositoryExploring");
  layout.addPerspectiveShortcut("org.eclipse.team.cvs.ui.cvsPerspective");//$NON-NLS-1$
  }
}
