//=====================================================================
//
//File:      $RCSfile: CanvasCopyTests.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.core.resources.IFileState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCopyAction;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class CanvasCopyTests extends CanvasTest {

	private static boolean initialized;
	private String test_id;
	public static boolean generateResults = false;

	public CanvasCopyTests(String name) {
		super(null, name);	
	}

	public void setUp() throws Exception {
		super.setUp();
		if(!initialized) {
			Ooaofooa.setPersistEnabled(true);
			CorePlugin.disableParseAllOnResourceChange();
			loadProject("MicrowaveOven");
			initialized = true;
		}
	}
	
	public void testCopyAllClasses() {
		test_id = "1";
		
		final Package_c uut = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Microwave Oven");
			}
		});
		CanvasTestUtils.openDiagramEditor(uut);	
		CanvasUtilities.openCanvasEditor("Microwave Oven: Package Diagram");
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		assertNotNull(ce);
		ce.getCanvas().setBounds(0, 0, 1000, 1000);
		ce.zoomAll();
		ce.getSelectAllAction().run();
		CanvasCopyAction canvascopyaction = new CanvasCopyAction(ce);
		canvascopyaction.run();
		waitForTransaction();
		UITestingUtilities.clearGraphicalSelection();
		UITestingUtilities.addElementToGraphicalSelection(ce.getModel().getRepresents());
		UITestingUtilities.pasteClipboardContents(new Point(200, 200), ce);
		ce.zoomAll();
		waitForTransaction();
		validateOrGenerateResults(ce,generateResults);
	}
	
	public void testCopyAvailableAfterReload() {
		Package_c ss = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Microwave Oven");
			}
		});
		CanvasUtilities.openCanvasEditor(ss);
		ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(ss));
		assertNotNull(clazz);
		GraphicalEditor ce = (GraphicalEditor) UITestingUtilities.getActiveEditor();
		try {
			ss.getPersistableComponent().persist();
			IFileState[] history = ss.getPersistableComponent().getFile().getHistory(new NullProgressMonitor());
			ss.getPersistableComponent().getFile().setContents(history[0], 0, new NullProgressMonitor());
		} catch (CoreException e) {
			fail("Unable to touch test file.");
		}
		Selection.getInstance().clear(); Selection.getInstance().addToSelection(clazz);
		assertTrue("Copy was not available after a file reload.", UITestingUtilities.checkItemStatusInContextMenu(ce.getCanvas().getMenu(), "Copy", "", false));
	}

	protected String getResultName() {
		return "CopyTests" + "_"  + test_id;
	}
}
