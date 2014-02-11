package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

//=====================================================================
//
//File:      $RCSfile: ModifyTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:12:57 $
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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.test.common.CanvasEditorUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

abstract class ModifyTest extends PkgCMBaseTest{
    //array of all opened editors whose title can be effected by  Modify
    //of component under test
    Object[] openEditors;
    IFile oldPath;
    NonRootModelElement meBeingTested;
    PersistableModelComponent pmcBeingTested;
    
    GraphicalEditor baseEditor=null;
    String newName;
    int childrenCount;
    int oldProxyRefs;
    
    public ModifyTest(String project, String name) {
        super(project, name);
    }

    private void initTest(PersistableModelComponent component, int focusedEditor){
        
        meBeingTested = component.getRootModelElement();
        pmcBeingTested=component;
        newName=component.getName()+"_n";
        oldPath=component.getFile();
        childrenCount=component.getChildrenCount();
        oldProxyRefs=getProxyRefrences();
        boolean setFocus=false;   
        
        if (focusedEditor==EditorTestUtilities.EDITOR_TYPE_NONE)
        {
            openEditors=new Object[0];
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
        }
        else if(focusedEditor==EditorTestUtilities.EDITOR_TYPE_CANVAS)
        {
//          open diagram editor that represent rootMe as shape
             openEditors=EditorTestUtilities.openEditorsForRename(meBeingTested);
            baseEditor= CanvasEditorUtils.openEditorWithShapeOf(meBeingTested);
            
            if(baseEditor==null)
            {
                setFocus=true;
            }
            else
            {                    
            m_wp.activate(baseEditor);
            }
        }
         else
        {
             //  test asserted due to following statement are N/A, so can be eliminated
           assertTrue("Editor not supported for "+meBeingTested.getClass(), EditorTestUtilities.isEditorSupported(meBeingTested, focusedEditor));
           openEditors=EditorTestUtilities.openEditorsForRename(meBeingTested);
           setFocus=true;
        }
        
        if(setFocus)
            setFocus(openEditors, focusedEditor);
        
        Display d = Display.getCurrent();
        while ( d.readAndDispatch() ) ;    
    }
    protected void performModifyComponentThuME(PersistableModelComponent component, int focusedEditor) throws CoreException {
        initTest(component,focusedEditor);
        selectMEInModelExplorer(meBeingTested);
        
        Display d = Display.getCurrent();
        while(d.readAndDispatch());
        //doModifyThruMExplorer(newName);
        
        while(d.readAndDispatch());
        performModifyChecks();
        while(d.readAndDispatch());
    }
    protected void performModifyComponentThuCE(PersistableModelComponent component, String componentType)
    {
        initTest(component,EditorTestUtilities.EDITOR_TYPE_CANVAS);
        createShape(baseEditor, componentType);
        
        doModifyThruCanvasEditor();
        Display display = Display.getCurrent();
        while (display.readAndDispatch());
        performModifyChecks();
    }
    private void doModifyThruCanvasEditor() {
        
        Shape_c shape = CanvasEditorUtils.getShape(meBeingTested, false);
        CanvasEditorUtils.edit(baseEditor, shape);
        
    }

    protected void performModifyComponentThuRN(PersistableModelComponent component, int focusedEditor)
    {
        initTest(component,focusedEditor);
                       
     //   doModifyThruResNav(component, newName);
        Display display = Display.getCurrent();
        while (display.readAndDispatch());
        performModifyChecks();
    }
    protected void performModifyChecks() {
        EditorTestUtilities.checkAllEditorTitles(openEditors, newName);
        checkFileModify();
        checkTreeItemExistance();
        assertEquals("Children component count differ after Modify",childrenCount, pmcBeingTested.getChildrenCount());
        if(baseEditor!=null)
            validateOrGenerateResults(baseEditor, generateResult);
        assertEquals("Not all proxy refrences updated!",oldProxyRefs, getProxyRefrences());
    }
    protected void checkFileModify() {
        
        assertTrue("File not Modified: " + pmcBeingTested.getFullPath(),
                pmcBeingTested.getFullPath().removeFileExtension().lastSegment().equals(newName));
        assertTrue("Modified file does not exist: " + pmcBeingTested.getFullPath(),
                pmcBeingTested.getFile().exists());
        assertTrue("Parent Folder of File not Modified: " + pmcBeingTested.getFile().getParent().getName(),
                pmcBeingTested.getFile().getParent().getName().equals(newName));
        assertTrue("Parent Folder of Modified file does not exist: " + pmcBeingTested.getFullPath(),
                pmcBeingTested.getFile().getParent().exists());
    }
    protected void checkTreeItemExistance() {
        ExplorerUtil.checkTreeItemExistance(meBeingTested, newName);
    }
    protected int getProxyRefrences()
    {
        if (pmcBeingTested.getComponentType().equals("SystemModel"))
            return 0;
        IFolder proPath=project.getFolder(Ooaofooa.MODELS_DIRNAME+"/"+project.getFullPath());
        IFolder domPath=project.getFolder(Ooaofooa.MODELS_DIRNAME+project.getFullPath()+"/"+pmcBeingTested.getDomainComponent().getName()); 
        return traverse4Reference(proPath,domPath,pmcBeingTested.getFullPath());
  }
    
    /**
     * This funcation counts pathToFind(proxy) occurances in projectFolder and only domainFolder other
     * domains are skiped
     * @param projectFolder
     * @param domainFolder
     * @param pathToFind
     * @return number of occurances found
     */ 
    private int traverse4Reference(IFolder projectFolder,IFolder domainFolder,IPath pathToFind)  {
        int refrences=0;
        IResource[] members;
        try {
            members = projectFolder.members();

        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof IFile) {
                IFile file = (IFile)members[i];         
                String extension = file.getFileExtension(); 
                
                if ((extension != null) && extension.equals(Ooaofooa.MODELS_EXT)) {
                    refrences+=countInFile(file,pathToFind);
                }
            }else if(members[i] instanceof IFolder && members[i].getFullPath().toOSString().startsWith(domainFolder.getFullPath().toOSString()))
            {
                refrences+=traverse4Reference((IFolder)members[i],domainFolder,pathToFind);
            }
        }
        } catch (CoreException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    return refrences;
    }

    private int countInFile(IFile file, IPath pathToFind) {
        int count=0;
        try {
            InputStreamReader in=new InputStreamReader( file.getContents());
            BufferedReader buffReader= new BufferedReader(in);
            
            StringBuffer sBuff= new StringBuffer(1024);
            String line=null;
            while((line=buffReader.readLine())!=null)
             sBuff.append(line);

            int i=0;
            
            while(true)
            {
                String str="/"+pathToFind.removeLastSegments(1).lastSegment()+ "/" + pathToFind.lastSegment();
            i=sBuff.indexOf(str,i);
            if(i==-1)
                break;
            count++;
            i+=str.length();
            }
            in.close();
        } catch (Exception e) {
             CorePlugin.logError(e.getMessage(), e);
        }        
        return count;
    }
    
}