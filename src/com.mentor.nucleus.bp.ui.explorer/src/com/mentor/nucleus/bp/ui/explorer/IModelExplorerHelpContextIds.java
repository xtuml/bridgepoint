package com.mentor.nucleus.bp.ui.explorer;
//=====================================================================
//
// File:      $RCSfile: IModelExplorerHelpContextIds.java,v $
// Version:   $Revision: 1.13 $
// Modified:  $Date: 2013/01/10 23:15:44 $
//
// (c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// This document contains information proprietary and confidential to
// Project Technology, Inc. and is not for external distribution.
//=====================================================================
//
// This class is responsible for providing the help ids for the Model
// Explorer plugin.
//

/**
 * Help context ids for the BridgePoint Model Explorer view.
 * <p>
 * This interface contains constants only; it is not intended to be implemented
 * or extended.
 * </p>
 * 
 */
interface IModelExplorerHelpContextIds {
  public static final String PREFIX = "com.mentor.nucleus.bp.ui.explorer."; //$NON-NLS-1$

  // Actions
  public static final String FILTER_SELECTION_ACTION = PREFIX + "filter_selection_action_context"; //$NON-NLS-1$
  public static final String GOTO_RESOURCE_ACTION = PREFIX + "goto_resource_action_context"; //$NON-NLS-1$
  public static final String MODEL_EXPLORER_MOVE_ACTION = PREFIX + "model_explorer_move_action_context"; //$NON-NLS-1$
  public static final String MODEL_EXPLORER_RENAME_ACTION = PREFIX + "model_explorer_rename_action_context"; //$NON-NLS-1$
  public static final String SHOW_IN_EXPLORER_ACTION = PREFIX + "show_in_explorer_action_context"; //$NON-NLS-1$
  public static final String SORT_VIEW_ACTION = PREFIX + "sort_view_action_context"; //$NON-NLS-1$
  public static final String COPY_ACTION = PREFIX + "model_explorer_copy_action_context"; //$NON-NLS-1$
  public static final String PASTE_ACTION = PREFIX + "model_explorer_paste_action_context"; //$NON-NLS-1$
  public static final String COLLAPSE_ALL_ACTION = PREFIX + "collapse_all_action_context"; //$NON-NLS-1$

  // Dialogs
  public static final String GOTO_RESOURCE_DIALOG = PREFIX + "goto_resource_dialog_context"; //$NON-NLS-1$
  
  // Views
  public static final String modelExplorerId = PREFIX + "modelExplorerViewId"; //$NON-NLS-1$
}