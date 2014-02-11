package com.mentor.nucleus.bp.ui.canvas.test;

//=====================================================================
//
//File:      $RCSfile: ODMSTest.java,v $
//Version:   $Revision: 1.23 $
//Modified:  $Date: 2013/05/10 05:41:50 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
//
//This class is responsible for performing the automatic test of the
//Graphics Domain by showing the ODMS domain canvases. The test data
//is created by the CanvasTestResultCreator class defined elsewhere
//in this package. The test creator class also creates a JPEG for
//each canvas so that the sample results may be viewed by a human.
//This class also tests that the Graphics Domain is capable of showing
//canvases designed for display as opposed to for hard copy. It does
//this by setting the isHardCopy argument to Model_c.Draw to false.
//The consequence of this is that the background is painted in the
//selected color for the canvas type and selected symbols are shown
//with a bold outline and with selection handles.
//

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Rectangle;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class ODMSTest extends CanvasTest {

	private static Selection selection = Selection.getInstance();

	public ODMSTest(String arg0) {
		super("com.mentor.nucleus.bp.ui.canvas.test", arg0);
	}

	private static boolean generateResults = false;
	private static String test_id = "ODMS";
	
	@Override
	public void initialSetup() throws Exception {
		setModelName("odms");

		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
		store.setValue(BridgePointPreferencesStore.DISABLE_GRADIENTS, true);
		
		loadProject("odms");

	}

	protected String getResultName() {
		return test_id;
	}

	public void testODMSClassDiagramDisplayWithSelection() throws Exception {
		test_id = "ODMSDisplaySelected";
		ModelClass_c[] objs = ModelClass_c.ModelClassInstances(modelRoot);
		GraphicalEditor editor = UITestingUtilities.getGraphicalEditorFor(objs[3], false);
		UITestingUtilities.addElementToGraphicalSelection(objs[3]);
		UITestingUtilities.addElementToGraphicalSelection(objs[5]);
		UITestingUtilities.addElementToGraphicalSelection(objs[7]);
		validateOrGenerateResultsGenerics(editor, generateResults);
		UITestingUtilities.clearGraphicalSelection();
	}

	public void testODMSClassDiagramDisplayZoomedToSelection() throws Exception {
		// This test bypasses the default 'zoom all' behavior so
		// that 'zoom to selected' behavior is tested.
		// Hence the code below is a copy of what is in the supertype
		test_id = "ODMSDisplayZoomedToSelection";
		ModelClass_c[] objs = ModelClass_c.ModelClassInstances(modelRoot);
		String[] selected_kl = { "D_OD", "D_DDA", "D_QP" };
		Object[] elems = new Object[3];
		for (int i = 0; i < selected_kl.length; ++i) {
			for (int j = 0; j < objs.length; ++j) {
				if (objs[j].getKey_lett().equals(selected_kl[i])) {
					elems[i] = objs[j];
					break;
				}
			}
		}
		GraphicalEditor editor = UITestingUtilities.getGraphicalEditorFor((NonRootModelElement) elems[0], false);
		UITestingUtilities.addElementToGraphicalSelection(elems[0]);
		UITestingUtilities.addElementToGraphicalSelection(elems[1]);
		UITestingUtilities.addElementToGraphicalSelection(elems[2]);
		validateOrGenerateResultsGenerics(editor, generateResults);
		UITestingUtilities.clearGraphicalSelection();
	}

	public void setGenerateResults() {
		try {
			// this creates all the hard copy diagrams
			createExpectedResults(true, false, true);

			generateResults = true;
			ISelection old_sel = Selection.getInstance()
					.getStructuredSelection();
			testODMSClassDiagramDisplayWithSelection();
			testODMSClassDiagramDisplayZoomedToSelection();
			selection.setSelection(old_sel);
		} catch (Exception e) {
			CanvasPlugin.logError(
					"Exception encountered by test result creator", e);
		}
	}
}