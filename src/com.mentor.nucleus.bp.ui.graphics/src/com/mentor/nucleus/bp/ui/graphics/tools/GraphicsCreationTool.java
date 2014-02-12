//========================================================================
//
//File:      $RCSfile: GraphicsCreationTool.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:06:08 $
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
package com.mentor.nucleus.bp.ui.graphics.tools;

import java.util.UUID;

import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditDomain;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsCreateRequest;

public class GraphicsCreationTool extends CreationTool {

	private int type;

	public GraphicsCreationTool(CreationFactory factory, int type) {
		super(factory);
		this.type = type;
		this.setUnloadWhenFinished(true);
	}

	@Override
	protected Request createTargetRequest() {
		GraphicsCreateRequest request = new GraphicsCreateRequest(getToolId());
		request.setFactory(getFactory());
		return request;
	}

	private UUID getToolId() {
		return getTool().getTool_id();
	}

	public ModelTool_c getTool() {
		Model_c model = ((GraphicalEditDomain) getDomain()).getEditor().getModel();
		ModelTool_c[] tools = ModelTool_c.getManyCT_MTLsOnR100(model);
		for(int i = 0; i < tools.length; i++) {
			ModelTool_c tool = tools[i];
			if(tool.getOoa_type() == type) {
				return tool;
			}
		}
		return null;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if(getTool() != null)
			getTool().setActive(false);
	}

	@Override
	public void activate() {
		super.activate();
		getTool().setActive(true);
	}
	
	private boolean enableRenamePopup = false;
	
	public static void setRenamePopupPreference(boolean value) {
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
				value);
	}

	@Override
	protected boolean handleKeyUp(KeyEvent e) {
		if(e.keyCode == SWT.CTRL) {
			deactivate();
			getDomain().loadDefaultTool();
			if(enableRenamePopup) {
				setRenamePopupPreference(false);
				enableRenamePopup = false;
			}
		}
		return super.handleKeyUp(e);
	}

	@Override
	protected boolean handleButtonDown(int button) {
		if(button == 1 && getCurrentInput().isControlKeyDown()) {
			// disable rename dialog popup if enabled
			boolean popupDisabled = CorePlugin
					.getDefault()
					.getPreferenceStore()
					.getBoolean(
							BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION);
			if(!popupDisabled) {
				enableRenamePopup = true;
				setRenamePopupPreference(true);
			}
		}
		return super.handleButtonDown(button);
	}

	@Override
	protected void handleFinished() {
		if (unloadWhenFinished() && !getCurrentInput().isControlKeyDown()) {
			if(enableRenamePopup) {
				setRenamePopupPreference(false);
				enableRenamePopup = false;
			}
			getDomain().loadDefaultTool();
		} else {
			reactivate();
		}
	}

	@Override
	protected boolean handleViewerExited() {
		// restore the rename popup
		if(enableRenamePopup) {
			enableRenamePopup = false;
			setRenamePopupPreference(false);
		}
		return super.handleViewerExited();
	}
	
}
