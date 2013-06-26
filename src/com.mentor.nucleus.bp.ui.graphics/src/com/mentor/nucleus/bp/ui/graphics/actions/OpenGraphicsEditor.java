package com.mentor.nucleus.bp.ui.graphics.actions;
//=====================================================================
//
//File:      $RCSfile: OpenGraphicsEditor.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:57 $
//
//(c) Copyright 2004-2013 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Project Technology, Inc. and is not for external distribution.
//=====================================================================
//
//This class is responsible for opening a graphics editor for the first
//time. If an editor is reopened when it is already open, Eclipse finds
//its collaborating copy of GraphicalEditorInput and compares it with the
//one it is trying to open. It recognizes them as the same and focusses
//the already open editor instead of opening a new one.
//
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditorInput;

public class OpenGraphicsEditor implements IActionDelegate {
  public void run(IAction action) {
    Object current = (Selection.getInstance().getStructuredSelection()).iterator().next();
    current = EditorUtil.getElementToEdit(current);
    try {
      GraphicalEditorInput input = GraphicalEditorInput.createInstance(current);
      if (input != null) {
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
          input,
          "com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor");
      }
    } catch (PartInitException e) {
      CanvasPlugin.logError("Failed to initialize Diagram Editor, reason:", e);
    }
  }
  public void selectionChanged(IAction action, ISelection selection) {
    //do nothing, selection is determined from core.
  }
}