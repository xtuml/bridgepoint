
//=====================================================================
//
//File:      $RCSfile: I697OpenActivityEditorFromMarker.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/05/10 06:02:36 $
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
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

public class I697OpenActivityEditorFromMarker extends UITextTest {

	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public I697OpenActivityEditorFromMarker(String projectName, String name) throws CoreException {
		super(null, name);
	}
	
	public I697OpenActivityEditorFromMarker(String name) throws CoreException {
		super(null, name);
	}

	private static IMarker currentMarker = null;
		
    protected void setUp() throws Exception {
    	super.setUp();
    	if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
    }	
    
	private ActivityEditor openFunctionActivityEditor(){
			Function_c uut = Function_c.FunctionInstance(modelRoot);
			assertNotNull(uut);

			ActivityEditorInteraction.openActivityEditor(uut);
			return TextEditorUtils.getActivityEditor("test_function: Function Activity"); //$NON-NLS-1$
	}
	
	private ActivityEditor openTransitionActivityEditor(){
		Transition_c uut = Transition_c.TransitionInstance(modelRoot);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		return TextEditorUtils.getActivityEditor("T_T_A3: third class in CSM State to CSM State: Transition Activity"); //$NON-NLS-1$
    }

	private ActivityEditor openCreationTransitionActivityEditor(){
		CreationTransition_c uut = CreationTransition_c.CreationTransitionInstance(modelRoot);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		return TextEditorUtils.getActivityEditor("No Event Assigned to creation state: ISM Creation State: Creation Transition Activity"); //$NON-NLS-1$
    }

	private void openFuncActivityEditorAndAddMarker(final String markerMessage, final String markerType, final int line){
		
		//_- Open an activity Editor.
		ActivityEditor ae = openFunctionActivityEditor();
		assertNotNull(ae);
		ActivityEditorInput editorInput = (ActivityEditorInput) ae.getEditorInput();
		final IFile file = editorInput.getFile();
		
		//_- Creating Marker
		currentMarker = UITextTest.createNewMarker(file, line, markerMessage, markerType);	 //$NON-NLS-1$
		
		//Close Editor
		ae.getSite().getPage().closeEditor(ae, false);
	}
	
	private void deleteCurrentMarker(){
		
		String type = null;
		if ( currentMarker.exists() )
		{
			try {
				type = currentMarker.getType();
				currentMarker.delete();
				currentMarker = null;
			} catch (CoreException e) {
				fail("Could not delete " + type  + " marker : " + e.toString()); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}
	
	public void testOpenActivityEditorFromBookmark(){
		
		openFuncActivityEditorAndAddMarker("test bookmark", IMarker.BOOKMARK, 1); //$NON-NLS-1$
		assertTrue(currentMarker.exists());
		
		UITextTest.openActivityEditorUsingMarker(currentMarker);
		
		deleteCurrentMarker();
	}
	
	public void testOpenActivityEditorFromTask(){
		openFuncActivityEditorAndAddMarker("test task", IMarker.TASK, 1); //$NON-NLS-1$
		
		// wait for processing as this test sometimes fails
		Display d = Display.getCurrent();
		while ( d.readAndDispatch() ) ;
		assertTrue(currentMarker.exists());
		
		UITextTest.openActivityEditorUsingMarker(currentMarker);
		
		deleteCurrentMarker();
	}
	
	public void testOpenActivityEditorFromProblem(){
		ActivityEditor ae = openFunctionActivityEditor();
		handleOpenActivityEditorTest(ae);
	}	
	public void testOpenTransitionActivityEditorFromProblem(){
		ActivityEditor ae = openTransitionActivityEditor();
		handleOpenActivityEditorTest(ae);
	}
	public void testOpenCreationTransitionActivityEditorFromProblem(){
		ActivityEditor ae = openCreationTransitionActivityEditor();
		handleOpenActivityEditorTest(ae);
	}
	private void handleOpenActivityEditorTest(ActivityEditor ae) {	
		IDocument doc = ae.getDocumentProvider().getDocument(ae.getEditorInput());
		try {
			doc.replace(0, 0, "bad;\n"); //$NON-NLS-1$
		} catch (BadLocationException e) {
			fail("Bad Location Exception"); //$NON-NLS-1$
		}

		ae.doSave(new NullProgressMonitor());
		ActivityEditorInput editorInput = (ActivityEditorInput) ae.getEditorInput();
		ae.getSite().getPage().closeEditor(ae, false);
		
		IMarker markers[] = null;
		try
		{
			markers = editorInput.getFile().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
		}
		catch (CoreException e)
		{
			fail("Markers not found: "+ e.toString()); //$NON-NLS-1$
		}
		
		assertNotNull(markers);
		assertTrue("More then one markers found", markers.length == 1); //$NON-NLS-1$
		
		currentMarker = markers[0];
		
		UITextTest.openActivityEditorUsingMarker(currentMarker);
		
		deleteCurrentMarker();			
	}	
}
