package com.mentor.nucleus.bp.ui.graphics.actions;
//=====================================================================
//
//File:      $RCSfile: OpenGraphicsEditor.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:57 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
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
// the License.
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