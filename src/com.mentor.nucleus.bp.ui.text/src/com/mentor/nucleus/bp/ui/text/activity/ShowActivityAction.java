package com.mentor.nucleus.bp.ui.text.activity;
//====================================================================
//
// File:      $RCSfile: ShowActivityAction.java,v $
// Version:   $Revision: 1.13 $
// Modified:  $Date: 2013/01/10 23:20:48 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.ui.text.IModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.TextPlugin;

public class ShowActivityAction implements IActionDelegate
{
  IStructuredSelection currentSelection;
  /* (non-Javadoc)
   * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
   */
  public void run(IAction action)
  {
    Object current = currentSelection.iterator().next();
    if (current != null)
    {
      try
      {
        IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory)PlatformUI.getWorkbench().getElementFactory(ActivityEditorInput.FACTORY_ID);
      	IEditorInput input = factory.createInstance(current);
        PlatformUI
          .getWorkbench()
          .getActiveWorkbenchWindow()
          .getActivePage()
          .openEditor(input, "com.mentor.nucleus.bp.ui.text.activity.ActivityEditor");
      }
      catch (CoreException e)
      {
		TextPlugin.logError("Could not activate Action Editor", e); //$NON-NLS-1$
      }
    }
  }
  /* (non-Javadoc)
   * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
   */
  public void selectionChanged(IAction action, ISelection selection)
  {
    currentSelection = (IStructuredSelection) selection;
  }
}
