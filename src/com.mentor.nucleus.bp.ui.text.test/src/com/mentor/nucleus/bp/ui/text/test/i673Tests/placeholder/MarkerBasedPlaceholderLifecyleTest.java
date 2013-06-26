//=====================================================================
//
//File:      $RCSfile: MarkerBasedPlaceholderLifecyleTest.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 06:03:50 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.ui.text.test.i673Tests.placeholder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderEntry;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderManager;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;
import com.mentor.nucleus.bp.ui.text.test.activity.ActivityEditorInteraction;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorInteraction;

public class MarkerBasedPlaceholderLifecyleTest extends UITextTest {

	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public MarkerBasedPlaceholderLifecyleTest(String projectName, String name)
			throws CoreException {
		super(null, name);
	}
	
	public MarkerBasedPlaceholderLifecyleTest(String name)
		throws CoreException {
		super(null, name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		PlaceHolderManager.getDefaultInstance();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
		
		if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
	}
	public void testMarkerBasedPlaceholderLifecyleForDescriptionEditor(){
		markerLifecycleForEditor(DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION);
	}
	
	public void testMarkerBasedPlaceholderLifecylceForActivityEditor(){
		markerLifecycleForEditor(ActivityEditorInputFactory.PLACEHOLDER_EXTENSION);
	}
	private static IFile file = null;
	private void markerLifecycleForEditor(String type){
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		assertNotNull(op);
		
		if (type.equals(DescriptionEditorInputFactory.PLACEHOLDER_EXTENSION))
			DescriptionEditorInteraction.openDescriptionEditor(op);
		else
			ActivityEditorInteraction.openActivityEditor(op);
		
		IEditorPart ed = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		assertNotNull(ed);
		
		//Checking the instance exists in the map
		file = UITextTest.getExistingPlaceHolderFromManager(op, type, this); 
		assertNotNull(file);
		
		//The underlying file must not exist
		assertFalse(file.exists()); 
		
		//adding a new marker
		final IMarker marker = createNewMarker(file, 1, "This is a test bookmark", IMarker.BOOKMARK);
		assertNotNull(marker);
		
		//Closing the open editor
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(true);
		
		//Getting the instance from the map again
		//Checking the instance exists in the map
		file = UITextTest.getExistingPlaceHolderFromManager(op, type, this); 
		assertNotNull(file);
		
		Display d = Display.getCurrent();
		while(d.readAndDispatch()){}
		//The underlying should exist
		assertTrue(file.exists());
		
		try {
			marker.delete();				
		} catch (CoreException e) {
			fail("Can not remove the marker " + e.toString()); //$NON-NLS-1$
		}
		
		TestingUtilities.waitForThread(PlaceHolderEntry.DELETE_THREAD_NAME);
		
		d = Display.getCurrent();
		while(d.readAndDispatch()){}
		
		//Checking the instance exists in the map
		file = UITextTest.getExistingPlaceHolderFromManager(op, type, this); 
		assertNotNull(file);
		
		d = Display.getCurrent();
		while(d.readAndDispatch()){}
		
		//The underlying is removed
		synchronized(file){
			assertFalse(file.exists());								
		}
	}
	
	
}
