package **ACTIONS_PACKAGE**;
//=====================================================================
//
// File:      $RCSfile$
// Version:   $Revision$
// Modified:  $Date$
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
// This class is responsible for initializing and instantiating the
// dialog that allows the user to filter out components shown in the
// **VIEW_NAME** view.
//
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import **VIEW_PACKAGE**.*;
import **PROJECT**.*;
/**
 * The FilterSelectionAction opens the filters dialog.
 */
public class FilterSelectionAction extends Action {
  private static final String FILTER_TOOL_TIP = "Select the model elements to exclude from the view:";
  private static final String FILTER_SELECTION_MESSAGE = "Select the model elements to exclude from the view:";
  private static final String FILTER_TITLE_MESSAGE = "**PREFIX** Filters";

  private **VIEW_NAME** explorer = null;
  /**
   * Creates the action.
   * 
   * @param navigator the resource navigator
   * @param label the label for the action
   */
  public FilterSelectionAction(**VIEW_NAME** explorer, String label) {
    super(label);
    this.explorer = explorer;
    setToolTipText(FILTER_TOOL_TIP);
    PlatformUI.getWorkbench().getHelpSystem().setHelp(this, I**HELPCONTEXTIDS_PREFIX**HelpContextIds.FILTER_SELECTION_ACTION);
    setEnabled(true);
  }
  /*
   * Implementation of method defined on <code>IAction</code>.
   */
  public void run() {
    **PREFIX**FiltersContentProvider contentProvider = new **PREFIX**FiltersContentProvider();

    ListSelectionDialog dialog = new ListSelectionDialog(
                                                        explorer.getSite().getShell(),
                                                        explorer.getTreeViewer(),
                                                        contentProvider,
                                                        new LabelProvider(),
                                                        FILTER_SELECTION_MESSAGE);
    dialog.setTitle(FILTER_TITLE_MESSAGE);
    dialog.setInitialSelections(contentProvider.getInitialSelections());
    dialog.open();
    if (dialog.getReturnCode() == Dialog.OK) {
      Object[] results = dialog.getResult();
      String[] selectedPatterns = new String[results.length];
      System.arraycopy(results, 0, selectedPatterns, 0, results.length);
      contentProvider.setSelections(selectedPatterns);
      Viewer viewer = explorer.getTreeViewer();
      viewer.getControl().setRedraw(false);
      explorer.updateFilters(selectedPatterns);
      viewer.refresh();
      viewer.getControl().setRedraw(true);
    }
  }
}