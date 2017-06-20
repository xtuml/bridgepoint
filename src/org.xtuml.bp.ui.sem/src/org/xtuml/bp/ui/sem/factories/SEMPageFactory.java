package org.xtuml.bp.ui.sem.factories;
//=====================================================================
//
// File:      $RCSfile: SEMPageFactory.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2013/01/10 23:44:01 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
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
// This class is responsible for providing the help ids for the
// SessionExplorerPlugin plugin.
//

import org.eclipse.swt.widgets.Composite;

import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.editors.IEditorTabFactory;
import org.xtuml.bp.core.editors.ModelEditor;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.ui.sem.pages.SEMEditorPage;

public class SEMPageFactory implements IEditorTabFactory {

	private Object configuredInput;

	@Override
	public Composite createEditorTab(ModelEditor modelEditor, Composite parent, IModelEditorInput editorInput) {
		if(editorInput instanceof InstanceStateMachine_c) {
			SEMEditorPage page = new SEMEditorPage(parent, StateMachine_c.getOneSM_SMOnR517((InstanceStateMachine_c) editorInput));
			return page;
		}
		if(editorInput instanceof ClassStateMachine_c) {
			SEMEditorPage page = new SEMEditorPage(parent, StateMachine_c.getOneSM_SMOnR517((ClassStateMachine_c) editorInput));
			return page;			
		}
		return null;
	}

	@Override
	public boolean getFocusBased() {
		return false;
	}

	@Override
	public void setFocusBased(boolean isFocusBased) {
		// do nothing unless we change it later
	}

	@Override
	public String getEditorTitle() {
		return "State Event Matrix Editor";
	}

	@Override
	public void setEditorTitle(String title) {
		// do nothing for now
	}

	@Override
	public Object getInput() {
		return configuredInput;
	}

	@Override
	public void setInput(Object input) {
		configuredInput = input;
	}

}
