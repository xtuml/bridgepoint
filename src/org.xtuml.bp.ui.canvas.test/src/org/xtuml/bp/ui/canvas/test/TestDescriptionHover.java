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

package org.xtuml.bp.ui.canvas.test;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.GraphicalEditPart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StructuredDataType_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.UITestingUtilities;

@RunWith(OrderedRunner.class)
public class TestDescriptionHover extends BaseTest {

	/* (non-Javadoc)
	 * @see org.xtuml.bp.test.common.BaseTest#initialSetup()
	 */
	private static boolean isFirstTime = true;
	@Override
//	@Before
	public void initialSetup() throws Exception {
		if (!isFirstTime)
			return;
		isFirstTime = false;
		loadProject("TestHoverDescriptions");
	}

	@Test
	public void testHoverTextUserDataType() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		UserDataType_c udt = UserDataType_c.UserDataTypeInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(udt, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(udt);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}

	@Test
	public void testHoverTextEnumerationDataType() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		EnumerationDataType_c edt = EnumerationDataType_c.EnumerationDataTypeInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(edt, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(edt);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}

	@Test
	public void testHoverTextStructuredDataType() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		StructuredDataType_c sdt = StructuredDataType_c.StructuredDataTypeInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(sdt, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(sdt);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}
	
	@Test
	public void testHoverTextState() throws Exception {
		modelRoot = Ooaofooa
				.getInstance("/TestHoverDescriptions/models/TestHoverDescriptions/Package/Package.xtuml");
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot);
		UITestingUtilities.getGraphicalEditorFor(state, false);
		GraphicalEditPart part = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(state);
		String toolTip = getTextFromToolTip(part);
		assertFalse("Did not find a tooltip.", toolTip.equals(""));
	}

	@Test
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

	@Test
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
