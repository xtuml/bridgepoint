//=====================================================================
//
//File:      $RCSfile: RenameTest.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:12:55 $
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

package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public abstract class RenameTest extends PkgCMBaseTest {
    // array of all opened editors whose title can be effected by rename
    // of component under test
    protected Object[] openEditors;

    protected IPath compPath;

    protected NonRootModelElement meBeingTested;

    protected PersistableModelComponent pmcBeingTested;

    protected GraphicalEditor baseEditor = null;
    
    protected String compType;

    protected String oldCompName;

    protected int focusedEditor;

    protected String newName;

    protected int childrenCount;

    protected int oldProxyRefs;

    protected int expectedEditorCount;

    protected int editorToTest;

    public RenameTest(String project, String name) {
        super(project, name);
    }

    protected void initTest(String compType1, String compName,
            int focusedEditor1, boolean forceNotFocus, int expectedEditorCount)
            throws Exception {
        PersistableModelComponent component = getComponent(compType1, compName);
        focusedEditor = focusedEditor1;
        editorToTest = focusedEditor;

        assertNotNull("Could not find component; " + compType1 + ":" + compName,
                component);
        compType = compType1;
        if (!component.isLoaded())
        	component.load(new NullProgressMonitor());
        meBeingTested = component.getRootModelElement();
        pmcBeingTested = component;
        newName = component.getName() + "_n";
        
        //save old name
        oldCompName=pmcBeingTested.getName();
        
        compPath = component.getFullPath();
        childrenCount = pmcBeingTested.getChildrenCount();
        oldProxyRefs = getProxyRefrences(component);
        boolean setFocus = false;
        
        Display d = Display.getCurrent();
        
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
        .closeAllEditors(false);
        
        
        if (focusedEditor==EditorTestUtilities.EDITOR_TYPE_NONE)
        {
            openEditors=new Object[0];
        }
        else if(focusedEditor==EditorTestUtilities.EDITOR_TYPE_CANVAS)
        {
//          open diagram editor that represent rootMe as shape
            if(throughRN){
                openEditors = openEditors(pmcBeingTested, expectedEditorCount);
            }
            else{
             openEditors=EditorTestUtilities.openEditorsForRename(meBeingTested);
            }
            baseEditor= CanvasEditorUtils.openEditorWithShapeOf(meBeingTested);
            
            if(baseEditor==null)
            {
                setFocus=true;
            } else {
                m_wp.activate(baseEditor);
            }
        } else {
            // test asserted due to following statement are N/A, so can be
            // eliminated
            assertTrue("Editor not supported for " + meBeingTested.getClass(),
                    EditorTestUtilities.isEditorSupported(meBeingTested,
                            focusedEditor));
            if(throughRN){
                openEditors = openEditors(pmcBeingTested, expectedEditorCount);
            }
            else{
                openEditors=EditorTestUtilities.openEditorsForRename(meBeingTested);
            }
            setFocus = true;
        }
        if (forceNotFocus) {
            // forceNotFocus will be used when these is only one editor and we
            // wana test it in not focused state
            setFocus = false;
            baseEditor = null; // graphics need not to be tested while editor
            // is not in focuse
            // Open any editor and set focus to that editor
            try {
                IDE.openEditor(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage(),
                        getProject().getFile(".project"));
            } catch (PartInitException e) {
                fail("Can't open editor for .project file :" + e.getMessage());
            }
        }
        if (setFocus)
            setFocus(openEditors, focusedEditor);

        assertEquals("All editors did not open", expectedEditorCount,
                openEditors.length);
        
        while (d.readAndDispatch())
            ;
    }

    /**
     * 
     * @param compType
     *            TODO
     * @param compName
     * @param focusedEditor
     * @param forceNotFocus
     *            some components has only one editor, this parameter will be
     *            used to put it in background by open some other editor
     * @param expectedEditorCount
     *            TODO
     * @throws Exception
     * @throws CoreException
     */
    protected void performRenameComponentThruME(String compType,
            String compName, int focusedEditor, boolean forceNotFocus,
            int expectedEditorCount) throws Exception {
        throughRN = false;
        initTest(compType, compName, focusedEditor, forceNotFocus,
                expectedEditorCount);
        
        if (!toRunTests())
            return; // this pass is for setup workspace next pass will execute actual test

        TreeItem item = selectMEInModelExplorer(compPath);
        //init variables from restored component
        meBeingTested = (NonRootModelElement) item.getData();
        pmcBeingTested = PersistenceManager.getComponent(
                meBeingTested);
        
        Display d = Display.getCurrent();
        while (d.readAndDispatch())
            ;

        doRenameThruMExplorer(newName);

        while (Display.getCurrent().readAndDispatch())
            ;

        performRenameChecks();
    }
    protected void performRenameComponentThruMEGenerics(String compType,
            String compName, int focusedEditor, boolean forceNotFocus,
            int expectedEditorCount) throws Exception {
        throughRN = false;
        initTest(compType, compName, focusedEditor, forceNotFocus,
                expectedEditorCount);
        
        if (!toRunTests())
            return; // this pass is for setup workspace next pass will execute actual test

        TreeItem item = selectMEInModelExplorer(compPath);
        //init variables from restored component
        meBeingTested = (NonRootModelElement) item.getData();
        pmcBeingTested = PersistenceManager.getComponent(
                meBeingTested);
        
        Display d = Display.getCurrent();
        while (d.readAndDispatch())
            ;

        doRenameThruMExplorer(newName);

        while (Display.getCurrent().readAndDispatch())
            ;

        performRenameChecksGenerics();
    }
    protected void performRenameComponentThruCE(String compType,
            String compName, int expectedEditorCount) throws Exception {
        throughRN = false;
        initTest(compType, compName, EditorTestUtilities.EDITOR_TYPE_CANVAS,
                false, expectedEditorCount);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return;
        while (Display.getCurrent().readAndDispatch())
            ;
        //init variables from restored component
        pmcBeingTested=PersistenceManager.findComponent(compPath);
        meBeingTested=pmcBeingTested.getRootModelElement();
        assertNotNull("Canvas Editor with shape couldn't opened.", baseEditor);
        CanvasEditorUtils.getShape(meBeingTested, true);
        while (Display.getCurrent().readAndDispatch())
            ;
        doRenameThruCanvasEditor(newName);
        while (Display.getCurrent().readAndDispatch())
            ;
        performRenameChecks();
    }
    protected void performRenameComponentThruCEGenerics(String compType,
            String compName, int expectedEditorCount) throws Exception {
        throughRN = false;
        initTest(compType, compName, EditorTestUtilities.EDITOR_TYPE_CANVAS,
                false, expectedEditorCount);
        // this pass is for setup workspace next pass will execute actual test
        if (!toRunTests())
            return;
        while (Display.getCurrent().readAndDispatch())
            ;
        //init variables from restored component
        pmcBeingTested=PersistenceManager.findComponent(compPath);
        meBeingTested=pmcBeingTested.getRootModelElement();
        assertNotNull("Canvas Editor with shape couldn't opened.", baseEditor);
        CanvasEditorUtils.getShape(meBeingTested, true);
        while (Display.getCurrent().readAndDispatch())
            ;
        doRenameThruCanvasEditor(newName);
        while (Display.getCurrent().readAndDispatch())
            ;
        performRenameChecksGenerics();
    }
    protected void performRenameComponentThruRN(String compType,
            String compName, int focusedEditor, boolean forceNotFocus,
            int expectedEditorCount) throws Exception {
        throughRN = true;
        initTest(compType, compName, focusedEditor, forceNotFocus,
                expectedEditorCount);

        if (!toRunTests())
            return; // this pass is for setup workspace next pass will execute actual test

        //init variables from restored component
        pmcBeingTested=PersistenceManager.findComponent(compPath);
        
        meBeingTested=pmcBeingTested.getRootModelElement();
        
        dispatchEvents(0);
        IFile file = pmcBeingTested.getFile();
        doRenameThruResNav(pmcBeingTested, newName);
        dispatchEvents(0);
        if(pmcBeingTested.isRootComponent()){
        project=ResourcesPlugin.getWorkspace().getRoot().getProject(newName);
        meBeingTested=pmcBeingTested.getRootModelElement();
        assertTrue(meBeingTested.getFile().exists());
        }else{
        file = project.getFile(compPath.removeFirstSegments(1).removeLastSegments(1).append(newName+ "." +Ooaofooa.MODELS_EXT));
        IFolder folder = project.getFolder(compPath.removeFirstSegments(1).removeLastSegments(2).append(newName));
        assertTrue("Renamed file does not exist: ", file.exists());
        assertFalse("Parent Folder of File also renamed: ", folder.exists());        
        }
        if(compType.equals("SystemModel")){
            checkTreeItemExistance(meBeingTested, newName);
        }else{
            checkTreeItemDeletion(meBeingTested);
            TreeItem item = ExplorerUtil.findItem(newName);
            assertNull(item);
        }
        

        if (baseEditor != null)
            validateOrGenerateResults(baseEditor, generateResult);

        EditorTestUtilities.checkAllEditorClosed(openEditors);
        
        //Rename Back senerio start
        
        try {
            if(pmcBeingTested.isRootComponent())
            {
                pmcBeingTested.getFile().getProject().move(new Path("/"+oldCompName), false, null);
            }else{
                IPath newPath = file.getFullPath().removeLastSegments(1).append(
                        oldCompName + "." + Ooaofooa.MODELS_EXT);
            file.move(newPath, false, new NullProgressMonitor());
            }
        } catch (CoreException e) {
            fail("Could not rename file");
        }
        dispatchEvents(0);
        pmcBeingTested=PersistenceManager.findComponent(compPath);
        TreeItem item = selectMEInModelExplorer(pmcBeingTested.getFullPath());
        assertNotNull(item);
    }
    protected void performRenameComponentThruRNGenerics(String compType,
            String compName, int focusedEditor, boolean forceNotFocus,
            int expectedEditorCount) throws Exception {
        throughRN = true;
        initTest(compType, compName, focusedEditor, forceNotFocus,
                expectedEditorCount);

        if (!toRunTests())
            return; // this pass is for setup workspace next pass will execute actual test

        //init variables from restored component
        pmcBeingTested=PersistenceManager.findComponent(compPath);
        
        meBeingTested=pmcBeingTested.getRootModelElement();
        
        dispatchEvents(0);
        IFile file = pmcBeingTested.getFile();
        doRenameThruResNav(pmcBeingTested, newName);
        dispatchEvents(0);
        if(pmcBeingTested.isRootComponent()){
        project=ResourcesPlugin.getWorkspace().getRoot().getProject(newName);
        meBeingTested=pmcBeingTested.getRootModelElement();
        assertTrue(meBeingTested.getFile().exists());
        }else{
        file = project.getFile(compPath.removeFirstSegments(1).removeLastSegments(1).append(newName+ "." +Ooaofooa.MODELS_EXT));
        IFolder folder = project.getFolder(compPath.removeFirstSegments(1).removeLastSegments(2).append(newName));
        assertTrue("Renamed file does not exist: ", file.exists());
        assertFalse("Parent Folder of File also renamed: ", folder.exists());        
        }
        if(compType.equals("SystemModel")){
            checkTreeItemExistance(meBeingTested, newName);
        }else{
            checkTreeItemDeletion(meBeingTested);
            TreeItem item = ExplorerUtil.findItem(newName);
            assertNull(item);
        }
        

        if (baseEditor != null)
            validateOrGenerateResultsGenerics(baseEditor, generateResult);

        EditorTestUtilities.checkAllEditorClosed(openEditors);
        
        //Rename Back senerio start
        
        try {
            if(pmcBeingTested.isRootComponent())
            {
                pmcBeingTested.getFile().getProject().move(new Path("/"+oldCompName), false, null);
            }else{
                IPath newPath = file.getFullPath().removeLastSegments(1).append(
                        oldCompName + "." + Ooaofooa.MODELS_EXT);
            file.move(newPath, false, new NullProgressMonitor());
            }
        } catch (CoreException e) {
            fail("Could not rename file");
        }
        dispatchEvents(0);
        pmcBeingTested=PersistenceManager.findComponent(compPath);
        TreeItem item = selectMEInModelExplorer(pmcBeingTested.getFullPath());
        assertNotNull(item);
    }

    private void performRenameChecks() {
        performRenameChecks(openEditors, baseEditor, pmcBeingTested, newName);
        assertEquals("Children component count differ after rename",
                childrenCount, pmcBeingTested.getChildrenCount());
        assertEquals("Not all proxy refrences updated!", oldProxyRefs,
                getProxyRefrences(pmcBeingTested));
    }
    private void performRenameChecksGenerics() {
        performRenameChecksGenerics(openEditors, baseEditor, pmcBeingTested, newName);
        assertEquals("Children component count differ after rename",
                childrenCount, pmcBeingTested.getChildrenCount());
        assertEquals("Not all proxy refrences updated!", oldProxyRefs,
                getProxyRefrences(pmcBeingTested));
    }
}