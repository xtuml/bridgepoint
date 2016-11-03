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
package org.xtuml.bp.core.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.common.NonRootModelElement;

/**
 *
 */
public abstract class CutCopyPasteAction extends Action {
	protected int COPY_TYPE = 0;

	protected int CUT_TYPE = 1;

	protected int PASTE_TYPE = 2;
	
	protected String transactioName;
	
	private static boolean MOVE_IS_IN_PROGRESS = false;

	public static ArrayList<NonRootModelElement> ELEMENT_MOVE_SOURCE_SELECTION = new ArrayList<NonRootModelElement>();
	public CutCopyPasteAction() {
		if (getActionType() == CUT_TYPE) {
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
			setText("Cut");
			setToolTipText("Cut the selected model elements.");
			transactioName = "Cut";
		} else if (getActionType() == COPY_TYPE) {
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
			setText("Copy");
			setToolTipText("Copy the selected model elements.");
			transactioName = "Copy";
		} else if (getActionType() == PASTE_TYPE) {
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
			setText("Paste");
			setToolTipText("Paste elements from the clipboard");
		}
	
	}
	
	public static boolean moveIsInProgress() {
		return MOVE_IS_IN_PROGRESS;
	}
	
	public static void startMove() {
		MOVE_IS_IN_PROGRESS = true;
		ELEMENT_MOVE_SOURCE_SELECTION.clear();
	}
	
	public static void stopMove() {
		MOVE_IS_IN_PROGRESS = false;
		ELEMENT_MOVE_SOURCE_SELECTION.clear();
		// Clear the clipboard to prevent another paste
		CorePlugin.getSystemClipboard().clearContents();
	}
		
	public static boolean selectionIsCuttable() {
		NonRootModelElement[] selectedNRMEs = Selection.getInstance().getSelectedNonRootModelElements();;
		boolean cuttable = true;
		for(int i = 0; i < selectedNRMEs.length; i++) {
			PackageableElement_c pe_pe = selectedNRMEs[i].getPE();
			if (null == pe_pe) {
				cuttable = false;
				break;
			} 
		}
		// only ask the delete action if the selection contains only
		// NonRootModelElement represented graphics
		if (selectionContainsOnlyCoreElements()) {
			cuttable = DeleteAction.canDeleteAction();
		}
		
		// Only allow cut on Packages and datatypes for now
		for (int i = 0; cuttable && i < selectedNRMEs.length; i++) {
			cuttable = false;
			NonRootModelElement rto = selectedNRMEs[i].getRTOElementForResolution();
			if (selectedNRMEs[i] instanceof Package_c || rto instanceof DataType_c) {
				cuttable = true;
			} 
		}
		
		return cuttable;
	}
	
	public static boolean selectionContainsOnlyCoreElements() {
		IStructuredSelection selection = (IStructuredSelection) Selection.getInstance().getStructuredSelection();
		for(Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
			Object selected = iterator.next();
			if(!(selected instanceof NonRootModelElement)) {
				return false;
			}
		}
		return true;
	}
	
	protected abstract void postRun();
	
	protected abstract int getActionType();
	
}
