// ========================================================================
//
//File: $RCSfile: SemEditorListener.java,v $
//Version: $Revision: 1.2 $
//Modified: $Date: 2013/05/10 13:29:03 $
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
package com.mentor.nucleus.bp.ui.sem.listeners;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.sem.pages.SEMEditorPage;

public class SemEditorListener extends ModelChangeAdapter {

	private SEMEditorPage editor;

	public SemEditorListener(SEMEditorPage editor) {
		this.editor = editor;
	}
	
	@Override
	public void modelElementReloaded(ModelChangedEvent event) {
		// for reloads we must recreate the columns as we do not know
		// if something was removed or added
		boolean refresh = false;
		if(event.getModelElement() instanceof InstanceStateMachine_c) {
			InstanceStateMachine_c ism = (InstanceStateMachine_c) event.getModelElement();
			StateMachine_c editorStateMachine = editor.getStateMahine();
			// can be null during reloads
			if(editorStateMachine != null) {
				if(editorStateMachine.getSm_id().equals(ism.getSm_idCachedValue())) {
					refresh = true;
				}
			}
		}
		if(event.getModelElement() instanceof ClassStateMachine_c) {
			ClassStateMachine_c csm = (ClassStateMachine_c) event.getModelElement();
			StateMachine_c editorStateMachine = editor.getStateMahine();
			// can be null during reloads
			if(editorStateMachine != null) {
				if(editorStateMachine.getSm_id().equals(csm.getSm_idCachedValue())) {
					refresh = true;
				}
			}
		}
		if(refresh) {
			editor.refresh(null, true);
		}
	}

	@Override
	protected void performDefault(ModelChangedEvent event, IModelDelta delta) {
		if(event.getModelElement() instanceof NonRootModelElement) {
			NonRootModelElement element = (NonRootModelElement) event.getModelElement();
			if(element == null) {
				element = (NonRootModelElement) delta.getModelElement();
			}
			// can be null during reloads
			if(editor.getStateMahine() != null) {
				if (element.getPersistableComponent() == editor.getStateMahine()
						.getPersistableComponent()) {
					// just refresh
					editor.refresh(null, false);
				}
			}
		}
	}

}
