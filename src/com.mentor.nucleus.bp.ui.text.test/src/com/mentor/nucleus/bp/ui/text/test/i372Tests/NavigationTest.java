//=====================================================================
//
//File:      $RCSfile: NavigationTest.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 06:06:15 $
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

package com.mentor.nucleus.bp.ui.text.test.i372Tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.internal.NavigationHistory;
import org.eclipse.ui.internal.NavigationHistoryAction;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementEditorInput;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput;
import com.mentor.nucleus.bp.ui.text.test.activity.ActivityEditorInteraction;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorInteraction;


public class NavigationTest extends BaseTest {
	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	static IFile testModel = null;	
	public NavigationTest(String name) throws CoreException {
		super(null, name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if ( firstSetup ) {
        	loadProject(testModelName);
        	m_sys = getSystemModel(testModelName);
			m_sys.getPersistableComponent().loadComponentAndChildren(
					new NullProgressMonitor());
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		page.closeAllEditors(false);
	}
	
	public void testForwardNavigationforDescriptionEditor()
	{
		InstanceList instList =  modelRoot.getInstanceList(Attribute_c.class);
		// there should be at least two attributes
		assertTrue(instList.size() >= 2);
		Attribute_c attr1 = (Attribute_c)instList.get(0);
		Attribute_c attr2 = (Attribute_c)instList.get(1);
		assertNotNull(attr1);
		assertNotNull(attr2);
		checkForwardNavigation(attr1, attr2, DescriptionEditorInput.EDITOR_ID);
	}
	
	public void testForwardNavigationforActivityEditor()
	{
		InstanceList instList =  modelRoot.getInstanceList(Operation_c.class);
		// there should be at least two attributes
		assertTrue(instList.size() >= 2);
		Operation_c op1 = (Operation_c)instList.get(0);
		Operation_c op2 = (Operation_c)instList.get(1);
		assertNotNull(op1);
		assertNotNull(op2);
		checkForwardNavigation(op1, op2, ActivityEditorInput.EDITOR_ID);
	}
	
	
	private void checkForwardNavigation(Object modelElement1, Object modelElement2,String EDITOR_ID)
	{
		Display display = Display.getCurrent();
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        		
		IFileEditorInput  editorInput1;
		IFileEditorInput  editorInput2;
		
			
		if(EDITOR_ID.equals(DescriptionEditorInput.EDITOR_ID))
		{
			DescriptionEditorInteraction.openDescriptionEditor(modelElement1);
		}
		else
		{
			ActivityEditorInteraction.openActivityEditor(modelElement1);
		}
		
		while(display.readAndDispatch());
		AbstractTextEditor editor1 = (AbstractTextEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		editorInput1 = (IFileEditorInput)editor1.getEditorInput();
		
		
		if(EDITOR_ID.equals(DescriptionEditorInput.EDITOR_ID))
		{
			DescriptionEditorInteraction.openDescriptionEditor(modelElement2);
		}
		else
		{
			ActivityEditorInteraction.openActivityEditor(modelElement2);
		}
		
		while(display.readAndDispatch());
		AbstractTextEditor editor2 = (AbstractTextEditor )PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		editorInput2 = (IFileEditorInput)editor2.getEditorInput();
		
		// closing editors in reverse order of their opening
				
		editor2.close(false);
		while(display.readAndDispatch());
			
		editor1.close(false);
		while(display.readAndDispatch());
		
		/*--- check that the place holder file have been deleted or not, if 
         they have not get deleted. chances are that, files might have markers 
        in them */
       
		validateExistanceOfPlaceholderFile(editorInput1,false);
		validateExistanceOfPlaceholderFile(editorInput2,false);
		
        NavigationHistoryAction nha = new NavigationHistoryAction(page.getWorkbenchWindow(), false);
		nha.run();
		while(display.readAndDispatch());
		// description editor should have been opened 
		validateEditor(modelElement1, EDITOR_ID);
		
		nha.run();
		while(display.readAndDispatch());
		validateEditor(modelElement2, EDITOR_ID);
	}
		
	public void testBackwardNavigationWithOutMarkers()
	{
	// check the Navigation with out markers, pass parameter as false
		checkBackwardNavigation(false);
	}
	
	public void testBackwardNavigationWithMarkers()
	{
	// test the Navigation with markers
		checkBackwardNavigation(true);
	}
	
	// the parameter specifies that either the markers are required or not.
	protected void checkBackwardNavigation(final boolean produceMarkers)
	{
		Display display = Display.getCurrent();
		while(display.readAndDispatch());
		FileEditorInput  dEditorInput;
		FileEditorInput  aEditorInput;
		
		final Operation_c uut = Operation_c.OperationInstance(modelRoot);
		assertNotNull(uut);
		
		
		WorkbenchPage page = (WorkbenchPage)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		// opening Description and Activity Editors for an Operation
		DescriptionEditorInteraction.openDescriptionEditor(uut);
		while(display.readAndDispatch());
		IEditorPart dEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (dEditorPart==null || !(dEditorPart instanceof DescriptionEditor))
		{
			fail("unable to open editor"); //$NON-NLS-1$
		}
		ActivityEditorInteraction.openActivityEditor(uut);
		while(display.readAndDispatch());
		IEditorPart aEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (aEditorPart ==null || !(aEditorPart instanceof ActivityEditor))
		{
			fail("unable to open Editor"); //$NON-NLS-1$
		}

		DescriptionEditor descriptionEditor = (DescriptionEditor )dEditorPart;
		ActivityEditor activityEditor= (ActivityEditor)aEditorPart;

		// Saving references of Editors' Inputs
		dEditorInput = (FileEditorInput)descriptionEditor.getEditorInput();
		aEditorInput = (FileEditorInput)activityEditor.getEditorInput();
		
		// changing and saving the text of the description Editor when it is opened for the first time.
		
		if(produceMarkers)
		{
			DescriptionEditorInteraction.createMarker(dEditorInput.getFile(),1,"test marker",IMarker.BOOKMARK);  //$NON-NLS-1$
			activityEditor.getDocumentProvider().getDocument(aEditorInput).set("ERROR// select many obj from instances of O_OBJ"); //$NON-NLS-1$
		}
		else
		{   // we will just change the text
			descriptionEditor.getDocumentProvider().getDocument(dEditorInput).set("changed"); //$NON-NLS-1$
			activityEditor.getDocumentProvider().getDocument(aEditorInput).set("// select many obj from instances of O_OBJ"); //$NON-NLS-1$
		}
		activityEditor.waitForParseThread();
		while(display.readAndDispatch());
		descriptionEditor.doSave(new NullProgressMonitor());
		
		// saving the text in a variable for use in comparison
		String firstDesc = descriptionEditor.getDocumentProvider().getDocument(dEditorInput).get();
		
		// now closing the description editor
		page.activate(descriptionEditor);
		while(display.readAndDispatch());
		
		page.closeEditor(descriptionEditor, false);		
		while(display.readAndDispatch());
		//changing and saving the text of the activity Editor when it is opened for the first time.
		activityEditor.doSave(new NullProgressMonitor());
		
		// saving the text in a variable for use in comparison
		String firstAction = activityEditor.getDocumentProvider().getDocument(aEditorInput).get();
		
		// now closing the activity editor
		page.closeEditor(activityEditor, false);
		while(display.readAndDispatch());
		
		// checking the existence of placeholder file related to DescriptionEditor's Input, 
		// pass 2nd argument as false if the existence of the file is not desired, otherwise pass true
		// we pass it the value of produceMarkers because if markers are required 
		// then file should exist and vice versa
		validateExistanceOfPlaceholderFile(dEditorInput,produceMarkers);
		// checking the existence of placeholder file related to ActivityEditor's Input,
		validateExistanceOfPlaceholderFile(aEditorInput,produceMarkers);
		
		while(display.readAndDispatch());
        NavigationHistoryAction nha = new NavigationHistoryAction(page.getWorkbenchWindow(), false);
		nha.run();
		while(display.readAndDispatch());
		// description editor should have been opened 
        this.validateEditor(firstAction, ActivityEditorInput.EDITOR_ID);
		nha.run();
		while(display.readAndDispatch());
		// activity editor should have been opened
        validateEditor(firstDesc, DescriptionEditorInput.EDITOR_ID);
	}
	
	
	/**  
	 *  Checks for the existence of the placeholder file represented by the passed FileEditorInput Object
	 * @param editorInput :
	 * <p>           The EditorInput which represent the placeholder file
	 * <p>
	 * @param desiredExistance : 
	 * <p>           It specifies that whether the existence of place holder file is desired or not.
	 *               if <code>true</code> is passed , method checks for the existence of the file and 
	 *               fail if file does not exists.
	 *               if <code>false</code> is passed , method checks for the existence of the file 
	 *               and fail if file exists.
	 */
	private void validateExistanceOfPlaceholderFile(IFileEditorInput editorInput, boolean desiredExistance)
	{
		IFile placeholderFile = editorInput.getFile();
		try {
			placeholderFile.refreshLocal(0, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		boolean exists =placeholderFile.exists(); 
		if(exists && !desiredExistance)
		{
			fail("Placeholder File ["+ placeholderFile +"] exists");  //$NON-NLS-1$//$NON-NLS-2$
		}
		else if (!exists && desiredExistance)
		{
			fail("Placeholder File ["+ placeholderFile +"] does not exists");  //$NON-NLS-1$//$NON-NLS-2$
		}		
	}

	// first parameter is the text within the editor
	private void validateEditor(String oldText, String editorID) 
	{
		IEditorPart editorPart= PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editorPart== null)
		{
			fail("Editor ["+editorID+"]was not opened."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		if(editorPart instanceof DescriptionEditor || editorPart instanceof ActivityEditor )
		{
			TextEditor editor = (TextEditor)editorPart;
			String editorText = editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
			assertEquals(oldText, editorText);
			
		}
		else
		{
			fail("Editor ["+editorID+"] was not opened."); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	private void validateEditor(Object modelElement, String editorID)
	{
		IEditorPart editorPart= PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editorPart== null)
		{
			fail("Editor ["+editorID+"]was not opened."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		if(editorPart instanceof DescriptionEditor || editorPart instanceof ActivityEditor )
		{
			AbstractModelElementEditorInput input =  (AbstractModelElementEditorInput)editorPart.getEditorInput();
			assertEquals(modelElement,input.getModelElement());
		}
		else
		{
			fail("Editor ["+editorID+"] was not opened.");  //$NON-NLS-1$//$NON-NLS-2$
		}
	}
}
