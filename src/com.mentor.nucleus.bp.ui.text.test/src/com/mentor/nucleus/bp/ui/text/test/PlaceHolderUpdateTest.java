//=====================================================================
//
//File:      $RCSfile: PlaceHolderUpdateTest.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/05/10 06:02:35 $
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

package com.mentor.nucleus.bp.ui.text.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.explorer.ExplorerPlugin;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput;
import com.mentor.nucleus.bp.ui.text.test.activity.ActivityEditorInteraction;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorInteraction;


/**
 * Contains tests that check whether placeholder files for 
 * activities and descriptions are properly updated when 
 * necessary.
 */
public class PlaceHolderUpdateTest extends UITextTest
{
	private static boolean firstSetup = true;

    /**
     * The name of the test domain used during most of these tests.
     * This isn't final because it needs to change temporarily 
     * during the domain-rename tests. 
     */
    private static String testModelName = "odms";

    /**
     * Constructor.
     */
    public PlaceHolderUpdateTest(String name) throws CoreException 
    {
        super(null, name);  
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        
        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
    }
    
    /**
     * For issue 768.  Gets a problem marker created for an activity 
     * editor and tests that the marker still opens the editor
     * after the associated domain is renamed.  This confirms that the 
     * model-root ID stored within the activity's place-holder
     * file was updated during the rename.
     */
    public void testPackageRenameUpdatesPlaceHolders() throws Exception
    {
        // open an editor on an activity in the domain
        String stateName = "Waiting for a Disk Request";
        String editorTitle = stateName + ": State Machine State Activity";
        ActivityEditor editor = TextEditorUtils.openActivityEditor(modelRoot, stateName, editorTitle);
        
        TextEditorUtils.createProblemMarker(editor);

        // remember the file on which the editor is operating, 
        // for below
        ActivityEditorInput editorInput =
            (ActivityEditorInput)editor.getEditorInput();
        IFile file = editorInput.getFile();

        // close the editor
        editor.getSite().getPage().closeEditor(editor, false);
        
        // rename the domain 
        final String oldTestModelName = testModelName;
        testModelName += "1";
        Package_c pkg = Package_c.PackageInstance(modelRoot, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals(oldTestModelName);
			}
		});
        
        TransactionManager manager = pkg.getTransactionManager();
    	Transaction transaction = manager.startTransaction("Changing package name", Ooaofooa.getDefaultInstance());
		pkg.setName(testModelName);
		manager.endTransaction(transaction);
		
		dispatchEvents(100);
        
        // find the marker created above
        IMarker[] markers = TextEditorUtils.getMarkers(file);
        assertTrue("Problem marker was not created", markers.length > 0);
        
        // try to use the marker to open the editor it's marking
        IWorkbenchPage page = 
            ActivityEditorInteraction.getProblemView().getSite().getPage();
        try {
            IDE.openEditor(page, markers[0], true);
        } catch (PartInitException e) {
            fail("Could not open activity editor using marker");
        }
        
        // check that the activity editor was opened
        editor = TextEditorUtils.getActivityEditor(editorTitle);
        assertNotNull("Marker did not open activity editor", editor);
        
        // close the editor so it doesn't interfere with later tests
        TextEditorUtils.closeEditor(editor);

        // rename the domain back to its original name, so the next test
        // can use it (and wait for the placeholders to get updated before
        // starting the next test)
        testModelName = oldTestModelName;
    	transaction = manager.startTransaction("Changing domain name", Ooaofooa.getDefaultInstance());
		pkg.setName(testModelName);
		manager.endTransaction(transaction);
		
		dispatchEvents(100);        
    }

    /**
     * For issue 768.  Adds a bookmark to a description  
     * editor and tests that the marker still opens the editor
     * after the associated system is renamed.  This confirms that the 
     * model-root ID stored within the description's place-holder
     * file was updated during the rename.
     */
    public void testSystemRenameUpdatesPlaceHolders() throws Exception
    {
        // open a description editor on the package
        DescriptionEditor editor = 
            TextEditorUtils.openPackageDescriptionEditor(modelRoot, "odms");
        
        // create a bookmark marker in the description editor
        DescriptionEditorInput editorInput =
            (DescriptionEditorInput)editor.getEditorInput();
        IFile file = editorInput.getFile();
        DescriptionEditorInteraction.createMarker(
            file, 1, "test bookmark", IMarker.BOOKMARK);     
        
        // close the editor
        editor.getSite().getPage().closeEditor(editor, false);
        
        // remember the description file's path, for below, as
        // the file will get moved during the system rename, below
        IPath path = file.getLocation();

        // rename the system
        String systemName = m_sys.getName();
        String oldName = systemName;
        systemName += "1"; 
        TransactionManager manager = TransactionManager.getSingleton();
        Transaction transaction = manager.startTransaction("Changing package name", Ooaofooa.getDefaultInstance());
        m_sys.setName(systemName);
        manager.endTransaction(transaction);
        
        dispatchEvents(100);
        
        // update our notion of the description file's path to reflect 
        // the system rename, above
        path = new Path(path.toString().replaceAll("odms/models/odms", "odms1/models/odms1"));
        file = ExplorerPlugin.getWorkspace().getRoot().getFileForLocation(path);
        
        // find the marker created above
        IMarker[] markers = TextEditorUtils.getMarkers(file);
        assertTrue("Task marker was not created", markers.length > 0);
        
        // try to use the marker to open the editor it's marking
        IWorkbenchPage page = 
            ActivityEditorInteraction.getProblemView().getSite().getPage();
        try {
            IDE.openEditor(page, markers[0], true);
        } catch (PartInitException e) {
            fail("Could not open description editor using marker");
        }
        
        // check that the description editor was opened
        editor = TextEditorUtils.getPackageDescriptionEditor(Package_c.getOneEP_PKGOnR1401(m_sys));
        assertNotNull("Marker did not open description editor", editor);
        
        // close the editor so it doesn't interfere with later tests
        TextEditorUtils.closeEditor(editor);

        // rename the system back to its original name, so the next test
        // can use it (and wait for the placeholders to get updated before
        // starting the next test)
        transaction = manager.startTransaction("Changing package name", Ooaofooa.getDefaultInstance());
        m_sys.setName(oldName);
        manager.endTransaction(transaction);
        
        dispatchEvents(100);    
    }
}
