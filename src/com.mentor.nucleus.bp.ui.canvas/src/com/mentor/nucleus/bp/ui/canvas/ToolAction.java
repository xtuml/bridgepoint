package com.mentor.nucleus.bp.ui.canvas;
//=====================================================================
//
//File:      $RCSfile: ToolAction.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:18:59 $
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
