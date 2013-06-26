package com.mentor.nucleus.bp.ui.canvas;
//=====================================================================
//
//File:      $RCSfile: ToolAction.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:18:59 $
//
//(c) Copyright 2004-2013 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Project Technology, Inc. and is not for external distribution.
//=====================================================================
//
//This class provides a supertype for each of the tools appearing on
//the canvas toolbar.
//
import org.eclipse.jface.action.Action;
public class ToolAction extends Action {
  ModelTool_c target;
  public ToolAction(ModelTool_c target) {
    super("", AS_CHECK_BOX);
    this.target = target;
  }
}
