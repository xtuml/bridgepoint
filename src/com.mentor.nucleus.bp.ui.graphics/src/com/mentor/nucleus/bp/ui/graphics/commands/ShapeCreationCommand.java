//========================================================================
//
//File:      $RCSfile: ShapeCreationCommand.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/01/17 03:29:39 $
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
package com.mentor.nucleus.bp.ui.graphics.commands;

import java.util.UUID;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.RenameActionUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class ShapeCreationCommand extends Command implements IExecutionValidationCommand{

	private final Model_c parent;
	private Rectangle bounds;
	private UUID toolId;
	private Shape_c newShape;
	private AbstractGraphicalEditPart editPart;

	public ShapeCreationCommand(Model_c parent, Rectangle bounds,
			AbstractGraphicalEditPart editPart, UUID toolId) {
		this.parent = parent;
		this.bounds = bounds;
		this.toolId = toolId;
		this.editPart = editPart;
		setLabel("shape creation");
	}

	public boolean canExecute() {
		return parent != null && bounds != null;
	}

	public void execute() {
		
	}

	
	private void refreshDiagram() {
		editPart.refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		// use BP redo
	}


	@Override
	public boolean executeWithValidation() {
		// TODO Auto-generated method stub

		// grid snap the bounds if necessary
		ModelTool_c tool = (ModelTool_c) parent.getModelRoot().getInstanceList(
				ModelTool_c.class).get(toolId.toString());
		ElementSpecification_c spec = ElementSpecification_c
				.getOneGD_ESOnR103(tool);
		boolean snap = false;
		if (bounds.getSize().width < Gr_c.Gethotspotsize() * 2) {
			snap = true;
			bounds.width = spec.Getdefaultwidth();
		}
		if (bounds.getSize().height < Gr_c.Gethotspotsize() * 2) {
			snap = true;
			bounds.height = spec.Getdefaultheight();
		}
		SnapToHelper helper = (SnapToHelper) editPart.getAdapter(SnapToHelper.class);
		if(helper != null && snap) {
			PrecisionRectangle newRect = new PrecisionRectangle(); 
			helper.snapRectangle(null, PositionConstants.VERTICAL
					| PositionConstants.HORIZONTAL, new PrecisionRectangle(bounds), newRect);
			bounds.translate(newRect.getLocation());
			// handle height and width as well
			PrecisionPoint delta = new PrecisionPoint();
			helper.snapPoint(null, PositionConstants.HORIZONTAL
					| PositionConstants.VERTICAL, new PrecisionPoint(bounds
					.getRight().x, bounds.getBottom().y), delta);
			bounds.width = bounds.width + delta.x;
			bounds.height = bounds.height + delta.y;
		}
		editPart.getFigure().translateToRelative(bounds);
		UUID shapeId = parent.Createshape(true, toolId);
		newShape = (Shape_c) parent.getModelRoot().getInstanceList(
				Shape_c.class).get(shapeId.toString());
		if (newShape!=null){
		Graphelement_c elem = Graphelement_c
				.getOneDIM_GEOnR23(GraphicalElement_c.getOneGD_GEOnR2(newShape));
		Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(newShape);
		elem.setPositionx(bounds.x);
		elem.setPositiony(bounds.y);
		node.setWidth(bounds.width);
		node.setHeight(bounds.height);
		DiagramEditPart diagramPart = (DiagramEditPart) editPart.getViewer()
				.getContents();
		diagramPart.resizeContainer();
		refreshDiagram();

		// rename
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
		boolean option = store
				.getBoolean(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION);
		if (!option) {
			GraphicalElement_c createdElement = GraphicalElement_c
					.getOneGD_GEOnR2(newShape);
			String oldName =RenameActionUtil.getElementName((NonRootModelElement) createdElement.getRepresents()); //Cl_c.Getname(createdElement.getRepresents());
			Class<? extends Object> classType = createdElement.getRepresents().getClass();
			if ((classType == ImportedClass_c.class)
					|| (classType == ComponentReference_c.class)) {
				return true;
			} else {
				boolean performRename = UIUtil.inputDialog(null, "Element Creation",
						"Enter the name:", oldName, UIUtil.newRenameValidator((ModelElement) createdElement.getRepresents()));
				if (performRename) {
					String proposedName = UIUtil.inputDialogResult;			
					RenameActionUtil.setElementName((NonRootModelElement) createdElement.getRepresents(), proposedName);
					((NonRootModelElement) createdElement.getRepresents()).setComponent(null);
					return true;
				} else {
					return false;
				}
			}
		} else {
			return true;
		}
		}
		else
		{
			return false;
		}
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		// use BP undo
	}

}