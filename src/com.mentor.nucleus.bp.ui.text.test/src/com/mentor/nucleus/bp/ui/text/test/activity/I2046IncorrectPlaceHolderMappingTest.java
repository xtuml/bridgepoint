//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.text.ModelAdapter;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderManager;

public class I2046IncorrectPlaceHolderMappingTest  extends BaseTest {

    static String projectName = "com.mentor.nucleus.bp.core";
    static String modelName = "ooaofooa";

    public I2046IncorrectPlaceHolderMappingTest(String name) throws CoreException {
        super(projectName, name); 
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        TestingUtilities.importDevelopmentProjectIntoWorkspace(projectName);
    }

    
	protected void tearDown() throws Exception {
		super.tearDown();
	}

    public void testCorrectPlaceHolderMappingForSameNameActivities(){
        // Fetch all operations with same name
        Operation_c[] operations = Operation_c.getManyO_TFRsOnR115(
            ModelClass_c.getManyO_OBJsOnR2(Subsystem_c.getManyS_SSsOnR1(
              Domain_c.getManyS_DOMsOnR28(m_sys))), new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
                if(((Operation_c)candidate).getName().equals("execute")){
                    return true;
                }
                return false;
            }
        });
        
        //Open editors for all of the operations and introduce ERROR at given 
        //index.
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        int errorOffset = 0;
        for (int i = 0; i < operations.length; i++) {
            ActivityEditorInteraction.openActivityEditor(operations[i]);
            ActivityEditor ae = (ActivityEditor) page.getActiveEditor();
            // change data in editor
            IDocument doc = ae.getDocumentProvider().getDocument(ae.getEditorInput());
            try {
                doc.replace(errorOffset, 0, "ERROR");
                // wait for the error to get parsed
                ae.waitForParseThread();
                ae.doSave(new NullProgressMonitor());
                errorOffset += 2;
            } catch (BadLocationException e) {
                fail("Bad Location Exception");
            }
        }
        
        PlaceHolderManager placeHolderManager = PlaceHolderManager.getDefaultInstance();

        //Verify if all place holder names are unique;
        List<IFile> placeHolderFiles = new Vector<IFile>(); 
        for (int i = 0; i < operations.length; i++) {
            ModelElementID id = ModelAdapter.getModelElementAdapter(operations[i]).createModelElementID(operations[i]);
            IFile file = placeHolderManager.getPlaceholderFile(id, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this);
            placeHolderManager.releasePlaceholderFile(id, ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this);
            assertFalse(placeHolderFiles.contains(file));
            
            assertTrue(file.exists());
            placeHolderFiles.add(file);
        }

        page.closeAllEditors(false);
        
        //After closing all editors attempt to open editor using the markers.
        errorOffset = 0;
        int index = 0;
        for (IFile file : placeHolderFiles) {
            // find the marker created above
            IMarker[] markers = TextEditorUtils.getMarkers(file);
            assertTrue("Problem marker(s) were not created", markers.length > 0);
            
            try {
                ActivityEditor ae = (ActivityEditor)IDE.openEditor(page, markers[0], true);

                //Verify that correct editor is opened.
                IDocument doc = ae.getDocumentProvider().getDocument(ae.getEditorInput());
                assertEquals(operations[index].getAction_semantics(), doc.get());
            } catch (PartInitException e) {
                fail("Could not open activity editor using marker");
            }
            index++;
        }
    }
    
}