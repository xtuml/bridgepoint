// =====================================================================
//
//File: $RCSfile: I643OALKeywordsHighlightingTest.java,v $
//Version: $Revision: 1.13 $
//Modified: $Date: 2013/05/10 06:02:36 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.ui.text.test.activity;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

public class I643OALKeywordsHighlightingTest extends BaseTest {

	public I643OALKeywordsHighlightingTest(String name) throws CoreException {
		super("HighlightingTest", name); 
	}

	public I643OALKeywordsHighlightingTest(String projectName, String name)
			throws CoreException {
		super(projectName, name);

	}

	private static boolean firstSetup = true;
	private static final String testModelName = "testDescrip1";
	
	protected void setUp() throws Exception {
		super.setUp();

		if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
	}

    public void testHighlighting() {
		
		InstanceList instList = modelRoot.getInstanceList(Function_c.class);
				
		// there should be atleast two attributes
		assertTrue(instList.size() >= 1);
		Function_c func1 = (Function_c) instList.get(0);
		Display display = Display.getCurrent();
		while (display.readAndDispatch())
			;
		ActivityEditorInteraction.openActivityEditor(func1);

		while (display.readAndDispatch())
			;
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		while (display.readAndDispatch())
			;
		ActivityEditor activityEditor = (ActivityEditor) page.getActiveEditor();
		String str = activityEditor.getDocumentProvider().getDocument(
				activityEditor.getEditorInput()).get();
		String actualActivity = "// function activity\nselect any xyz_to from instances of T_T;\n"; //$NON-NLS-1$
		assertEquals(str, actualActivity);

		assertEquals(activityEditor.getTitle(),
				"test_function: Function Activity");//$NON-NLS-1$

		activityEditor.setFocus();
		while (display.readAndDispatch())
			;

		TextViewer tv = activityEditor.getTextViewer();
		StyleRange ranges[] = tv.getTextWidget().getStyleRanges(32,6);
		activityEditor.close(false);
		while (display.readAndDispatch());

		// Retrieve style with range of keyword xyz_to to see if its is 
		// highlighted as a single token.
		if(ranges.length != 1){
			fail("Improper Highlighting, This is not highlighted as a single token");
		}

		assertEquals("StyleRange {32, 6, fontStyle=normal, foreground=Color {0, 0, 0}}",ranges[0].toString());
	}
}