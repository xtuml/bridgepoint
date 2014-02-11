//=====================================================================
//
//File:      $RCSfile: PkgCMRestoreCreateTest.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:12:59 $
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

package com.mentor.nucleus.bp.io.mdl.test.pkgcm.restore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.io.mdl.test.pkgcm.EditorTestUtilities;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMCreateTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class PkgCMRestoreCreateTest extends PkgCMCreateTest {

    public PkgCMRestoreCreateTest(String name) {
        
        super(projectName, getTestCaseName());
        showModelExplorer();
    }

    protected void setupProjectAndTestModel() throws CoreException {
        firstTime_create = getProperty("firstTime_create",firstTime_create);
        String path=getProperty("mdlClassPath");
        if(path!=null)
            mdlClassPath=new Path(path);
        super.setupProjectAndTestModel();
    }
    
    protected void tearDown() throws Exception {
        if(mdlClassPath!=null)
            setProperty("mdlClassPath", mdlClassPath.toString());
        
        setProperty("firstTime_create",firstTime_create);
        super.tearDown();
    }

    protected void initTest(String parentPmcType, String parentPmcName, int focusedEditor, String compType) throws Exception {
        if(toSetupWorkspace()){
        super.initTest(parentPmcType, parentPmcName, focusedEditor, compType);
        saveTestVariables();
        } else {
        initTestVariables(parentPmcType,parentPmcName);
    }
        
    }
    private void saveTestVariables() {

        int edCount = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getEditorReferences().length;

        //number of opened editors
        setProperty("editorCount", new Integer(edCount).toString());
        
        setProperty("editorToTest", editorToTest);
        setProperty("compTypeBeingCreated", compTypeBeingCreated);
        
        if (editorToTest == EditorTestUtilities.EDITOR_TYPE_NONE)
            removeProperty("focusEditorTitle");
        else
            setProperty("focusEditorTitle", PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage()
                    .getActiveEditor().getTitle());

        if (baseEditor != null)
            setProperty("baseEditorTitle", baseEditor.getTitle());
        else
            removeProperty("baseEditorTitle");
    }

    private void initTestVariables(String compType,String compName) throws Exception {
        
        compTypeBeingCreated=getProperty("compTypeBeingCreated");
        parentPMC=getComponent(compType,compName );
        String title = getProperty("baseEditorTitle");
        editorToTest=getProperty("editorToTest",-2);
        if (title != null)
            baseEditor = (GraphicalEditor) EditorTestUtilities.findEditor(title);

        title = getProperty("focusEditorTitle");

        
        String actTitle=null;
        if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor()!=null)
            actTitle= PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor().getTitle();
        if(editorToTest!=EditorTestUtilities.EDITOR_TYPE_NONE){
        assertEquals("Restore focused editor is not desired one.", title,
                actTitle);
        }
    }
}
