//=====================================================================
//
//File:      $RCSfile: TextEditorReloadContentsTest.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 06:02:35 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.text.test;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IEditorPart;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementEditorInput;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;

/**
 * Performs tests related to the updating of an editor's 
 * contents when a model is reloaded from disk.
 */
public class TextEditorReloadContentsTest extends UITextTest
{
	private static boolean firstSetup = true;
	
    /**
     * The name of the test domain used during these tests.
     */
    private static final String testModelName = "odms";

    /**
     * Constructor.
     */
    public TextEditorReloadContentsTest(String name) throws CoreException
    {
        super(null, name);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
    }   
    
    /**
     * For issue 780.  Opens an activity editor and saves a change in it 
     * that causes a problem marker to be created, then recopies over the 
     * associated model file with the original, checking to see
     * that the editor's contents revert and that the marker
     * has been deleted. 
     */
    public void testActivityEditorUpdatesOnReload() throws Exception
    {
        // open an editor on an activity in the domain
        String stateName = "Waiting for a Disk to be Waiting";
        String editorTitle = stateName + ": State Machine State Activity";
        ActivityEditor editor = TextEditorUtils.openActivityEditor(modelRoot, stateName, editorTitle);
    	    	
        // remember what the original contents of the editor are,
        // for comparison below
        String originalContents = TextEditorUtils.getEditorContents(editor);
        
        TextEditorUtils.createProblemMarker(editor);
        
		// re-copy the test domain into our test project; it will
		// get reloaded into memory, which should wipe out the above change
        reCopyComponentFile(editor);

        // check that the editor's text has reverted to what it was
        // before the change above was made
        assertEquals("Editor contents did not revert after reload", 
            originalContents, TextEditorUtils.getEditorContents(editor));
        
        // the removal of markers occurs on a separate thread that 
        // must wait for the resource tree to unlock for modification,
        // thus we have to wait for the same occurrence, plus
        // some time for the thread to do the removal
        WorkspaceUtil.waitForWorkspaceTreeToUnlock(true);
        
        // check that the problem marker created above is gone
        IMarker[] markers = TextEditorUtils.getMarkers(editor);
        assertTrue("Problem marker was not removed", markers.length == 0);

        // check that we can create another problem marker in the reloaded
        // activity, to test whether parsing was started for the editor's
        // new input
        TextEditorUtils.createProblemMarker(editor);
        markers = TextEditorUtils.getMarkers(editor);
        assertTrue("Problem marker was not created after error inserted into reload activity", 
            markers.length > 0);

        // close the editor so it doesn't interfere with later tests
        TextEditorUtils.closeEditor(editor);
    }

    /**
     * For issue 780.  Opens a description editor and saves a change in it, 
     * then recopies over the associated model file with the original, 
     * checking to see that the editor's contents revert.
     */
    public void testDescriptionEditorUpdatesOnReload() throws Exception
    {
        // open a description editor on the domain
        DescriptionEditor editor = 
            TextEditorUtils.openPackageDescriptionEditor(modelRoot, "odms");
        
        // remember what the original contents of the editor are,
        // for comparison below
        String originalContents = TextEditorUtils.getEditorContents(editor);
        
        // add some text to the editor
        TextEditorUtils.addTextToEditor(editor, "test\n");

        // save the editor
        editor.doSave(new NullProgressMonitor());
        
        // re-copy the test domain into our test project; it will
        // get reloaded into memory, which should wipe out the above change
        reCopyComponentFile(editor);
        
        // check that the editor's text has reverted to what it was
        // before the change above was made
        assertEquals("Editor contents did not revert after reload", 
            TextEditorUtils.getEditorContents(editor), originalContents);
        
        // close the editor so it doesn't interfere with later tests
        TextEditorUtils.closeEditor(editor);
    }
    
	private void reCopyComponentFile(IEditorPart editor) throws Exception {
		NonRootModelElement me = ((AbstractModelElementEditorInput) editor
				.getEditorInput()).getModelElement();
		PersistableModelComponent component = PersistenceManager
				.getComponent(me);

		IFileState[] history = component.getFile().getHistory(
				new NullProgressMonitor());
		component.getFile().setContents(history[0], IFile.FORCE,
				new NullProgressMonitor());
	}
}
