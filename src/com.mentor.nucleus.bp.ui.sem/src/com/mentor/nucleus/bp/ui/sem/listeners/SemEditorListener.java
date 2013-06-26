// ========================================================================
//
//File: $RCSfile: SemEditorListener.java,v $
//Version: $Revision: 1.2 $
//Modified: $Date: 2013/05/10 13:29:03 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
			if(editorStateMachine.getSm_id().equals(ism.getSm_idCachedValue())) {
				refresh = true;
			}
		}
		if(event.getModelElement() instanceof ClassStateMachine_c) {
			ClassStateMachine_c csm = (ClassStateMachine_c) event.getModelElement();
			StateMachine_c editorStateMachine = editor.getStateMahine();
			if(editorStateMachine.getSm_id().equals(csm.getSm_idCachedValue())) {
				refresh = true;
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
			if (element.getPersistableComponent() == editor.getStateMahine()
					.getPersistableComponent()) {
				// just refresh
				editor.refresh(null, false);
			}
		}
	}

}
