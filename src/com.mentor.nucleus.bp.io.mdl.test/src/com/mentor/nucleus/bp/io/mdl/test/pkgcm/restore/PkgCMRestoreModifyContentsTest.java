//=====================================================================
//
//File:      $RCSfile: PkgCMRestoreModifyContentsTest.java,v $
//Version:   $Revision: 1.10 $
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

import java.util.Vector;

import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.EditorTestUtilities;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMModifyContentsTest;

public class PkgCMRestoreModifyContentsTest extends PkgCMModifyContentsTest {

    public PkgCMRestoreModifyContentsTest(String name) {
        super(projectName, name);
    }

    protected void setupProjectAndTestModel() throws CoreException {
        
        firstTime_modCont = getProperty("firstTime_modCont", firstTime_modCont);
        super.setupProjectAndTestModel();
    }

    protected void tearDown() throws Exception {
        setProperty("firstTime_modCont", firstTime_modCont);
        super.tearDown();
    }

    private void saveTestVariables() {
        setProperty("expectedEditorCount", expectedEditorCount);
        setProperty("editorToTest", editorToTest);
        setProperty("componentName", componentName);
        setProperty("oldFile", oldFile.getFullPath().toString());
        int edCount = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getEditorReferences().length;

        setProperty("editorCount", new Integer(edCount).toString());

        if (editorToTest == EditorTestUtilities.EDITOR_TYPE_NONE)
            removeProperty("focusEditorTitle");
        else
            setProperty("focusEditorTitle", PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage()
                    .getActiveEditor().getTitle());

        for (int i = 0; i < openEditors.length; i++) {
            IEditorPart editor = (IEditorPart) openEditors[i];
            setProperty("title" + i, editor.getTitle());
        }
        for (int i = 0; i < oldEditorContents.length; i++) {
            if (oldEditorContents[i] != null)
                setProperty("oldEditorContents" + i, oldEditorContents[i]);
        }

    }

    private void initTestVariables() {
        componentName = getProperty("componentName");
        expectedEditorCount = getProperty("expectedEditorCount", 0);
        editorToTest = getProperty("editorToTest", -1);
        String path = getProperty("oldFile");
        oldFile = PersistenceManager.findComponent(new Path(path)).getFile();
        String title = null;
        openEditors = new Object[expectedEditorCount];
        editorTitles = new String[expectedEditorCount];
        for (int i = 0; i < expectedEditorCount; i++) {
            title = getProperty("title" + i);

            if (title == null)
                continue;
            editorTitles[i] = title;
            openEditors[i] = EditorTestUtilities.findEditor(title,true);
            while(Display.getCurrent().readAndDispatch());
            m_wp.activate((IEditorPart)openEditors[i]);
        }

        title=getProperty("focusEditorTitle");
        if (title != null) {
            m_wp.activate(EditorTestUtilities.findEditor(title));
        }


        oldEditorContents = new String[expectedEditorCount];
        for (int i = 0; i < expectedEditorCount; i++) {
            oldEditorContents[i] = getProperty("oldEditorContents" + i);
        }
    }

    protected void initTest(String compType, String compName,
            int focusedEditor, int expectedEditorCount) throws Exception {
        if (toSetupWorkspace()) {
            super.initTest(compType, compName, focusedEditor,
                    expectedEditorCount);
            saveTestVariables();
        } else {
            initTestVariables();
        }
    }

}
