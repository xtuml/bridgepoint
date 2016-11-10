//========================================================================
//
//File:      $RCSfile: CanvasCutAction.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:57 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package org.xtuml.bp.ui.graphics.actions;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;

import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.CutAction;
import org.xtuml.bp.core.ui.DeleteAction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

public class CanvasCutAction extends CutAction {

	private GraphicalEditor m_editor;

	public CanvasCutAction(GraphicalEditor editor) {
		m_editor = editor;
	}
	
	public TransactionManager getTransactionManager() {
		return ((NonRootModelElement)m_editor.getModel().getRepresents()).getTransactionManager();
	}
	
	public NonRootModelElement[] getElementsToBeCopied(boolean includeGraphics) {
		return CanvasCopyAction.getCopiableElements(includeGraphics, m_editor.getModel());
	}

	/**
	 * Determines whether or not the selection
	 * contains elements which may be cut
	 */
	public boolean isSelectionCuttable() {
		if(m_editor == null) return false;
		boolean cuttable = CanvasCopyAction.isSelectionCopiable(m_editor.getModel());
		if(!cuttable || !selectionIsCuttable()) {
			cuttable = false;
		}
		return cuttable;
	}

	@Override
	public boolean isEnabled() {
		return isSelectionCuttable();
	}

	@Override
	protected Object getSecondaryClipboardData() {
		return CanvasCopyAction.getImageDataForSelection(m_editor);
	}

	@Override
	protected Transfer getSecondaryTransfer() {
		return ImageTransfer.getInstance();
	}

	@Override
	protected boolean onlyIncludeSecondaryData() {
		return false;
	}
	
}
