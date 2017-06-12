//========================================================================
//
//File:      $RCSfile: ModelEditor.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/13 19:02:46 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package org.xtuml.bp.core.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

public class ModelEditor extends FormEditor {
	
	private SourceEditor sourceEditor = new SourceEditor();
	
	class SourceEditor extends TextEditor implements IFormPage {

		@Override
		public void initialize(FormEditor editor) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public FormEditor getEditor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IManagedForm getManagedForm() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setActive(boolean active) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isActive() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canLeaveThePage() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Control getPartControl() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setIndex(int index) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isEditor() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean selectReveal(Object object) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		sourceEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		sourceEditor.doSaveAs();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return sourceEditor.isSaveAsAllowed();
	}

	@Override
	protected void addPages() {
		// TODO Auto-generated method stub
		
	}
	
}
