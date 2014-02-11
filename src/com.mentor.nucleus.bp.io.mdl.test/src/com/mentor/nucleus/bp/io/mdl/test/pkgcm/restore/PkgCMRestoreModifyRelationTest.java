//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.EditorTestUtilities;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMModifyRelationTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class PkgCMRestoreModifyRelationTest extends PkgCMModifyRelationTest {

    public PkgCMRestoreModifyRelationTest(String name) {
        super(name);        
    }

    protected void setupProjectAndTestModel() throws CoreException {
        
        reCopy_modRel = getProperty("reCopy_modRel", reCopy_modRel);
        if (!toRunTests()) 
            super.setupProjectAndTestModel();
    }

    protected void tearDown() throws Exception {
        setProperty("reCopy_modRel", reCopy_modRel);

        if (toRunTests()) // close editors after test run
            PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().closeAllEditors(false);
        super.tearDown();
    }

    private void saveTestVariables() {

        // setProperty("componentPath", oldFile.getFullPath().toString());
        setProperty("oldTestingFile", oldTestingFile.getFullPath().toString());
        int edCount = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getEditorReferences().length;

        setProperty("oldDeletedFile", oldDeletedFile.getFullPath().toString());
        setProperty("oldRenamedFile", oldRenamedFile.getFullPath().toString());

        setProperty("editorCount", new Integer(edCount).toString());
        setProperty("newName", newName);

        if (!testCanvas)
            removeProperty("baseEditor");
        else
            setProperty("baseEditor", PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage()
                    .getActiveEditor().getTitle());

        for (int i = 0; i < openEditors.length; i++) {
            IEditorPart editor = (IEditorPart) openEditors[i];
            setProperty("title" + i, editor.getTitle());
        }

    }

    private void initTestVariables() {
        
        String path1 = getProperty("oldTestingFile");
        pmcBeingTested = PersistenceManager.findComponent(new Path(path1));
        oldTestingFile = pmcBeingTested.getFile();
    
        String path2 = getProperty("oldDeletedFile");
        pmcBeingDeleted = PersistenceManager.findComponent(new Path(path2));
        oldDeletedFile = pmcBeingDeleted.getFile();
        meClassBeingDeleted = pmcBeingDeleted.getRootModelElement()
        .getClass();

        String path3 = getProperty("oldRenamedFile");
        pmcBeingRenamed = PersistenceManager.findComponent(new Path(path3));
        oldRenamedFile = pmcBeingRenamed.getFile();
        meClassBeingRenamed = pmcBeingRenamed.getRootModelElement()
        .getClass();
        
        String title = null;
        newName = getProperty("newName");
  

        title=getProperty("baseEditor");
        if (title != null) {
            baseEditor= (GraphicalEditor) EditorTestUtilities.findEditor(title);
        }
        int edCount = Integer.valueOf(getProperty("editorCount")).intValue();
        editorTitles = new  String[edCount];
        for (int i = 0; i < edCount-1; i++) {
            editorTitles[i] = getProperty("title" + String.valueOf(i));
        }

    }

    protected void initTest(String compType,String compName, String[] testNames) throws Exception {
        if (toSetupWorkspace()) {
            super.initTest(compType, compName, testNames);
            saveTestVariables();
        } else {
            initTestVariables();
        }
    }

}
