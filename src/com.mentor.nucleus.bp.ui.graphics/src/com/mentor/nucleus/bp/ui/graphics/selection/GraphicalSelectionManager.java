//========================================================================
//
//File:      $RCSfile: GraphicalSelectionManager.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:06:14 $
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
package com.mentor.nucleus.bp.ui.graphics.selection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SelectionManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

public class GraphicalSelectionManager extends SelectionManager {

	@Override
	public void appendSelection(EditPart editpart) {
		super.appendSelection(editpart);
		synchronizeSelectionToCore();
	}

	@Override
	public void deselect(EditPart editpart) {
		super.deselect(editpart);
		synchronizeSelectionToCore();
	}

	@Override
	public void deselectAll() {
		super.deselectAll();
		synchronizeSelectionToCore();
	}

	@Override
	public void setSelection(ISelection newSelection) {
		List<EditPart> editParts = extractEditParts(newSelection);
		super.setSelection(new StructuredSelection(editParts));
		if(!selectionContainsNonEditParts(newSelection))
			synchronizeSelectionToCore();
	}

	private boolean selectionContainsNonEditParts(ISelection newSelection) {
		IStructuredSelection ss = (IStructuredSelection) newSelection;
		for(Object object : ss.toList()) {
			EditPart part = findEditPartFor(object);
			if(part == null && object instanceof NonRootModelElement)
				return true;
		}
		return false;
	}

	private List<EditPart> extractEditParts(ISelection newSelection) {
		ArrayList<EditPart> list = new ArrayList<EditPart>();
		if(newSelection instanceof IStructuredSelection) {
			for(Object object: ((IStructuredSelection) newSelection).toList())
				if (object instanceof EditPart
						&& ((GraphicalEditPart) object).getFigure().getParent()
								.isVisible())
					list.add((EditPart) object);
				else {
					EditPart part = findEditPartFor(object);
					if (part != null
							&& ((GraphicalEditPart) part).getFigure()
									.getParent().isVisible())
						list.add(part);
				}
			return list;
		} else
			return list;
	}

	private EditPart findEditPartFor(Object selected) {
		if(selected instanceof NonRootModelElement) {
			return (EditPart) getViewer().getEditPartRegistry().get(getGraphicalElementFor(selected));
		}
		if(selected instanceof EditPart)
			return (EditPart) selected;
		return null;
	}

	private Object getGraphicalElementFor(Object selected) {
		EditPart diagramPart = getViewer().getContents();
		Model_c model = (Model_c) diagramPart.getModel();
		GraphicalElement_c[] elems = GraphicalElement_c.getManyGD_GEsOnR1(model);
		for(int i = 0; i < elems.length; i++) {
			if(elems[i].getRepresents() == selected) {
				Shape_c shape = Shape_c.getOneGD_SHPOnR2(elems[i]);
				if(shape != null)
					return shape;
				Connector_c connector = Connector_c.getOneGD_CONOnR2(elems[i]);
				if(connector != null)
					return connector;
			}
		}
		return null;
	}

	public void synchronizeSelectionToCore() {
		Selection.getInstance().setSelection(getSelection());
	}

}
