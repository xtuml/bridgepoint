//=====================================================================
//
//File:      $RCSfile: PkgCMRestoreRenameTest.java,v $
//Version:   $Revision: 1.9 $
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.io.mdl.test.pkgcm.EditorTestUtilities;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMRenameTest;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class PkgCMRestoreRenameTest extends PkgCMRenameTest {
    
    public PkgCMRestoreRenameTest(String name) {
        super(null, name); // dont setup proj now it may have been renamed

    }

    //this will be called from super class's setup
    protected void setupProjectAndTestModel() throws CoreException{
        // setup Component Names at one place
        String temp = null;
        temp = getProperty("mdlClassUnderTest");
        if (temp != null)
            mdlClassUnderTest = temp;

        temp = getProperty("dtpUnderTest");
        if (temp != null)
            dtpUnderTest = temp;

        temp = getProperty("subsysInSubsysUnderTest");
        if (temp != null)
            subsysInSubsysUnderTest = temp;

        temp = getProperty("eepUnderTest");
        if (temp != null)
            eepUnderTest = temp;

        temp = getProperty("projectName");
        if (temp != null)
            projectName = temp;
        
        temp = getProperty("domainName");
        if (temp != null)
            domainName = temp;
        
        firstTime = getProperty("firstTime",firstTime);
        
        setupProject(projectName);

        // we want to execute ensureAvailableAndLoaded() once only, so this
        // check
        // cause it to execute for 1st test only
        if (toSetupWorkspace()) {
            if (firstTime) {
                ensureAvailableAndLoaded(domainName, false);
            } else {
                
                try { //expandAll can fail if any previous tests have been failed
                    showModelExplorer();
                    while (Display.getCurrent().readAndDispatch())
                        ;
                    
                    m_bp_tree.expandAll();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                while (Display.getCurrent().readAndDispatch())
                    ;
            }
        }
        firstTime=false;
    }
    
    protected void tearDown() throws Exception {
        setProperty("mdlClassUnderTest", mdlClassUnderTest);
        setProperty("dtpUnderTest", dtpUnderTest);
        setProperty("subsysInSubsysUnderTest", subsysInSubsysUnderTest);
        setProperty("eepUnderTest", eepUnderTest);
        setProperty("projectName", projectName);
        setProperty("domainName", domainName);
        setProperty("firstTime",firstTime);
        setProperty("oldName",oldCompName);
        //close all editors after restore test.
        if(toRunTests())
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
            .closeAllEditors(false);
        
        super.tearDown();
    }

    protected void initTest(String compType, String compName,
            int focusedEditor, boolean forceNotFocus, int expectedEditorCount)
            throws Exception {
        if (toSetupWorkspace()) {
            super.initTest(compType, compName, focusedEditor, forceNotFocus,
                    expectedEditorCount);
            saveTestVariables();
        } else {
            initTestVariables();
        }

    }

    private void saveTestVariables() {

        int edCount = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getEditorReferences().length;

        setProperty("newName", newName);
        setProperty("oldName",oldCompName);
        setProperty("componentPath", compPath.toString());
        
        
        setProperty("oldProxyRefs", oldProxyRefs);
        setProperty("childrenCount", childrenCount);
        //number of opened editors
        setProperty("editorCount", edCount);
        setProperty("expectedEditorCount", expectedEditorCount);
        setProperty("editorToTest", editorToTest);

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
        
        for (int i = 0; i < openEditors.length; i++) {
            IEditorPart editor = (IEditorPart) openEditors[i];
            setProperty("title" + i, editor.getTitle());
        }
    }

    private void initTestVariables() {
        newName = getProperty("newName");
        assertNotNull(newName);
                
        oldCompName = getProperty("oldName");
        assertNotNull(oldCompName);
        
        oldProxyRefs = getProperty("oldProxyRefs",-1);
        assertTrue("Not a valid oldProxyRefs value.", oldProxyRefs!=-1);
        
        childrenCount=getProperty("childrenCount",0);
        String title = getProperty("baseEditorTitle");

        if (title != null)
            baseEditor = (GraphicalEditor) EditorTestUtilities.findEditor(title);

        title = getProperty("focusEditorTitle");
        compPath = new Path(getProperty("componentPath"));
        
        String actTitle=null;
        if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor()!=null)
            actTitle= PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor().getTitle();

        assertEquals("Restore focused editor is not desired one.", title,
                actTitle);

        int i = 0;
        Vector vec = new Vector();

        while (true) {
            title = getProperty("title" + i);

            if (title == null)
                break;
            IEditorPart ed = EditorTestUtilities.findEditor(title);
            if (ed != null)
                vec.add(ed);

            i++;
        }
        openEditors = vec.toArray();

        // assertEquals("Not all Restored editors found",
        // Integer.parseInt(p.getProperty("editorCount")),openEditors.length);
    }


}
