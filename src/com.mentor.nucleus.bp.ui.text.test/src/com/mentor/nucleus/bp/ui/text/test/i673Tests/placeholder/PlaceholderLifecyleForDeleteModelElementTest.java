//=====================================================================
//
//File:      $RCSfile: PlaceholderLifecyleForDeleteModelElementTest.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 06:03:50 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.text.test.i673Tests.placeholder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;
import com.mentor.nucleus.bp.ui.text.test.activity.ActivityEditorInteraction;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorInteraction;

public class PlaceholderLifecyleForDeleteModelElementTest extends UITextTest {

	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public PlaceholderLifecyleForDeleteModelElementTest(String projectName,
			String name) throws CoreException {
		super(null, name);
	}

	public PlaceholderLifecyleForDeleteModelElementTest(String name) throws CoreException {
		super(null, name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (firstSetup) {
			loadProject(testModelName);

			// make sure the user isn't prompted to do a parse all
			CorePlugin.disableParseAllOnResourceChange();

			firstSetup = false;
		}
	}

	public void testLifecycleForDescriptionEditor(){
		UserDataType_c udt = UserDataType_c.UserDataTypeInstance(modelRoot);
		
		assertNotNull(udt);
		
		DescriptionEditorInteraction.openDescriptionEditor(udt);
		
		DescriptionEditor ed = (DescriptionEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(ed);
		
		IFile file = UITextTest.getExistingPlaceHolderFromManager(udt, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(file);
		
		//Adding marker to create a file
		IMarker marker = createNewMarker(file, 1, "This is a test marker", IMarker.BOOKMARK);
		assertNotNull(marker);
		
		file = UITextTest.getExistingPlaceHolderFromManager(udt, DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(file);
		
		//File is created.
		assertTrue(file.exists());
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(true);

		//Need to delete the udt.
        Selection.getInstance().clear();
		udt.Dispose();
		
		Display d = Display.getCurrent();
		while (d.readAndDispatch()){}
		
		//Underlying file should be delted
		assertFalse(file.exists());
	}
	
	public void testLifecycleForActivityEditor(){
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		
		assertNotNull(op);
		
		ActivityEditorInteraction.openActivityEditor(op);
		
		ActivityEditor ed = (ActivityEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(ed);
		
		IFile file = UITextTest.getExistingPlaceHolderFromManager(op, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(file);
		
		//Adding marker to create a file
		IMarker marker = createNewMarker(file, 1, "This is a test marker", IMarker.BOOKMARK);
		
		file = UITextTest.getExistingPlaceHolderFromManager(op, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this);
		assertNotNull(file);
		
		//File is created.
		assertTrue(file.exists());
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(true);

		//Need to delete the op.
        Selection.getInstance().clear();
		op.Dispose();
		
		Display d = Display.getCurrent();
		while (d.readAndDispatch()){}
		
		//Underlying file should be delted
		assertFalse(file.exists());		
	}
}
