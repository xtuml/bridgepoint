//========================================================================
//
// File: TestDescriptionHover.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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

package com.mentor.nucleus.bp.ui.canvas.test;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.GraphicalEditPart;

import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class TestDescriptionHover extends BaseTest {

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.test.common.BaseTest#initialSetup()
	 */
	@Override
	protected void initialSetup() throws Exception {
		loadProject("TestHoverDescriptions");
	}

	public void testHoverTextUserDataType() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		UserDataType_c udt = UserDataType_c.UserDataTypeInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(udt, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(udt);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}

	public void testHoverTextEnumerationDataType() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		EnumerationDataType_c edt = EnumerationDataType_c.EnumerationDataTypeInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(edt, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(edt);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}

	public void testHoverTextStructuredDataType() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		StructuredDataType_c sdt = StructuredDataType_c.StructuredDataTypeInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(sdt, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(sdt);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}
	
	public void testHoverTextState() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(state, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(state);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}

	public void testHoverTextTransition() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		Transition_c transition = Transition_c.TransitionInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				Transition_c trans = (Transition_c) candidate;
				CreationTransition_c ct = CreationTransition_c.getOneSM_CRTXNOnR507(trans);
				return ct == null;
			}
		});
		UITestingUtilities.getGraphicalEditorFor(transition, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(transition);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}

	public void testHoverTextCreationTransition() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		Transition_c transition = Transition_c.TransitionInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				Transition_c trans = (Transition_c) candidate;
				CreationTransition_c ct = CreationTransition_c.getOneSM_CRTXNOnR507(trans);
				return ct != null;
			}
		});
		CreationTransition_c ct = CreationTransition_c.getOneSM_CRTXNOnR507(transition);
		UITestingUtilities.getGraphicalEditorFor(ct, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(ct);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));		
	}

	private String getTextFromToolTip(GraphicalEditPart part) {
		IFigure toolTipFigure = part.getFigure().getToolTip();
		if(!(toolTipFigure instanceof Label)) {
			List<?> children = toolTipFigure.getChildren();
			for(Object child : children) {
				if(child instanceof Label) {
					Label label = (Label) child;
					return label.getText();
				}
			}
		} else {
			return ((Label) toolTipFigure).getText();
		}
		return "";
	}
	
}
