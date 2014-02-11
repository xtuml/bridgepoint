//=====================================================================
//
//File:      $RCSfile: PkgCMDeleteTestGenerics.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/10 17:34:00 $
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class PkgCMDeleteTestGenerics extends DeleteTest {

    protected static       String projectName="MultiLevelModelSystem";
    protected static boolean firstTime = false;
    protected static IPath mdlClassPath = null;
    
    public PkgCMDeleteTestGenerics(String projName, String name) {
        super(null, name);
        showModelExplorer();
    }
    public PkgCMDeleteTestGenerics(String name) {
		super(projectName, name);
	}
	protected static boolean reCopy=true;

	protected void setupProjectAndTestModel() throws CoreException {
		// delete old remaining data and start over
		if (reCopy) {
			if (getProject() != null && getProject().exists()
					&& modelRoot != null) {
				try {
					getProject().delete(true, null);
				} catch (Exception e) {
				}
			}
			setupProject(projectName);
			CorePlugin.disableParseAllOnResourceChange();
			while (Display.getCurrent().readAndDispatch())
				;
			loadProject(projectName);
			modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
					getProjectHandle(projectName), "MultiLevelModel", true),
					true);
			graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
			reCopy = false;
		}
		project = getProjectHandle(projectName);
	}

	protected void setUp() throws Exception {
		super.setUp();
        setupProjectAndTestModel();
    }    
    
    protected void tearDown() throws Exception {
        dispatchEvents(0);
        super.tearDown();
    }
	/*  STARTS CODE: delete through Model Explorer */
	
	public void testDeleteMclass_ThruME_CanvasFocused() throws Exception{
		performDeleteComponentThruMEGenerics("ModelClass","A", EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
    }
	public void testDeleteDTPInPkg_ThruME_CanvasFocused() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "SubDataTypes",EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
    }
    public void testDeleteISM_ThruME_CanvasFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruMEGenerics("InstanceStateMachine","X", EditorTestUtilities.EDITOR_TYPE_CANVAS, 3);
    }
	public void testDeleteSSInSS_ThruME_CanvasFocused() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "SSInSS1",EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
    }
//	public void testDeleteSS_ThruME_CanvasFocused() throws Exception{
//        reCopy = true;
//        performDeleteComponentThruMEGenerics("Package", null,EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
//    }
    public void testDeleteEEP_ThruME_CanvasFocused() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_CANVAS, 6);
    }
	public void testDeleteDomain_ThruME_CanvasFocused() throws Exception{
        reCopy=true;
        performDeleteComponentThruMEGenerics("Package", null,EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
    }
//	public void testDeleteSystem_ThruME_CanvasFocused() throws Exception{
//        reCopy=true;
//        performDeleteComponentThruMEGenerics("SystemModel",null,EditorTestUtilities.EDITOR_TYPE_CANVAS, 3);
//    }
    public void testDeleteISM_ThruME_ActivityFocused() throws Exception{        
        performDeleteComponentThruMEGenerics("InstanceStateMachine", "X",EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 3);
    }
	public void testDeleteMclass_ThruME_ActivityFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruMEGenerics("ModelClass","A", EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 4);
    }
    public void testDeleteSSinSS_ThruME_ActivityFocused() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "SSInSS1", EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 4);
    }
    public void testDeleteEEP_ThruME_ActivityFocused() throws Exception{
        reCopy=true;
        performDeleteComponentThruMEGenerics("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 6);
    }
	public void testDeleteISM_ThruME_DescriptionFocused() throws Exception{
        performDeleteComponentThruMEGenerics("InstanceStateMachine","X", EditorTestUtilities.EDITOR_TYPE_DESC, 3);
    }
	public void testDeleteDTPInPkg_ThruME_DescriptionFocused() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "SubDataTypes", EditorTestUtilities.EDITOR_TYPE_DESC, 4);
    }
	public void testDeleteMclass_ThruME_DescriptionFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruMEGenerics("ModelClass","A", EditorTestUtilities.EDITOR_TYPE_DESC, 4);
    }
	public void testDeleteSSinSS_ThruME_DescriptionFocused() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "SSInSS1", EditorTestUtilities.EDITOR_TYPE_DESC, 4);
    }
//	public void testDeleteSS_ThruME_DescriptionFocused() throws Exception{
//        reCopy = true;
//        performDeleteComponentThruMEGenerics("Package", null, EditorTestUtilities.EDITOR_TYPE_DESC, 4);
//    }
    public void testDeleteEEP_ThruME_DescriptionFocused() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_DESC, 6);
    }
	public void testDeleteDomain_ThruME_DescriptionFocused() throws Exception{
        reCopy = true;
		performDeleteComponentThruMEGenerics("Package",null,EditorTestUtilities.EDITOR_TYPE_DESC, 4);
    }
//	public void testDeleteSystem_ThruME_DescriptionFocused() throws Exception{
//        reCopy=true;
//        performDeleteComponentThruMEGenerics("SystemModel",null,EditorTestUtilities.EDITOR_TYPE_DESC, 3);
//    }
	public void testDeleteISM_ThruME_NoEditor() throws Exception{
        performDeleteComponentThruMEGenerics("InstanceStateMachine","A",EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }
	public void testDeleteMclass_ThruME_NoEditor() throws Exception{
        performDeleteComponentThruMEGenerics("ModelClass","A",EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }
	public void testDeleteDTPInPkg_ThruME_NoEditor() throws Exception{
        performDeleteComponentThruMEGenerics("Package", "SubDataTypes",EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }
	public void testDeleteSSInSS_ThruME_NoEditor() throws Exception{
	    performDeleteComponentThruMEGenerics("Package", "SSInSS1",EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }
	public void testDeleteSS_ThruME_NoEditor() throws Exception{
		reCopy = true;
		performDeleteComponentThruMEGenerics("Package", null,EditorTestUtilities.EDITOR_TYPE_NONE, 0);
	    
    }
    public void testDeleteEEP_ThruME_NoEditor() throws Exception{
    	
        performDeleteComponentThruMEGenerics("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }
    public void testDeleteDomain_ThruME_NoEditor() throws Exception{
		reCopy = true;
        performDeleteComponentThruMEGenerics("Package", null,EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }	
	/*  STARTS CODE: delete through Canvas Editor */
	
	public void testDeleteMclass_ThruCE_CanvasFocused() throws Exception{
        performDeleteComponentThruCEGenerics("ModelClass", "A",EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
    }
	public void testDeleteDTPInPkg_ThruCE_CanvasFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruCEGenerics("Package","SubDataTypes",EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
	}
	public void testDeleteSSinSS_ThruCE_CanvasFocused() throws Exception{
        performDeleteComponentThruCEGenerics("Package", "SSInSS1",EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
    }
    public void testDeleteEEP_ThruCE_CanvasFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruCEGenerics("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_CANVAS, 6);
    }
//	public void testDeleteSS_ThruCE_CanvasFocused() throws Exception{
//        reCopy = true;
//        performDeleteComponentThruCE("Package", null,EditorTestUtilities.EDITOR_TYPE_CANVAS, 4);
//    }

	/*  ENDS CODE: delete through  Canvas Editor*/

    /*  STARTS CODE: delete through Resource Navigator */
    public void testDeleteISM_ThruRN_CanvasFocused() throws Exception{
        performDeleteComponentThruRN("InstanceStateMachine", "A",EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
    }    
//    public void testDeleteMclass_ThruRN_CanvasFocused() throws Exception{
//        performDeleteComponentThruRN("ModelClass", "X",EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
//    }    
    public void testDeleteDTPInPkg_ThruRN_CanvasFocused() throws Exception{
        performDeleteComponentThruRN("Package", "SubDataTypes",EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
    }
//    public void testDeleteSSInSS_ThruRN_CanvasFocused() throws Exception{
//        performDeleteComponentThruRN("Package", "SSInSS1",EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
//    }
    public void testDeleteSS_ThruRN_CanvasFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruRN("Package", null,EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
    }
    public void testDeleteEEP_ThruRN_CanvasFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruRN("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_CANVAS,3);
    }
    
    public void testDeleteISM_ThruRN_ActivityFocused() throws Exception{
        performDeleteComponentThruRN("InstanceStateMachine", "X",EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 3);
    }
//    public void testDeleteMclass_ThruRN_ActivityFocused() throws Exception{
//        performDeleteComponentThruRN("ModelClass", "X",EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 2);
//    }
//    public void testDeleteEEP_ThruRN_ActivityFocused() throws Exception{
//        performDeleteComponentThruRN("ExternalEntityPackage", "External Entities",EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 3);
//    }
  
    public void testDeleteISM_ThruRN_DescriptionFocused() throws Exception{
        performDeleteComponentThruRN("InstanceStateMachine", "A",EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }
    public void testDeleteDTPInPkg_ThruRN_DescriptionFocused() throws Exception{
        performDeleteComponentThruRN("Package", "SubDataTypes",EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }
    public void testDeleteMclass_ThruRN_DescriptionFocused() throws Exception{
        performDeleteComponentThruRN("ModelClass", "B",EditorTestUtilities.EDITOR_TYPE_DESC, 1);
    }
//    public void testDeleteSSinSS_ThruRN_DescriptionFocused() throws Exception{
//        performDeleteComponentThruRN("Package", "SSInSS1",EditorTestUtilities.EDITOR_TYPE_DESC, 2);
//
//    }
//    public void testDeleteSS_ThruRN_DescriptionFocused() throws Exception{
//        reCopy = true;
//        performDeleteComponentThruRN("Package", null,EditorTestUtilities.EDITOR_TYPE_DESC, 2);
//    }
    public void testDeleteEEP_ThruRN_DescriptionFocused() throws Exception{
        performDeleteComponentThruRN("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_DESC, 3);
    }
    public void testDeleteDomain_ThruRN_DescriptionFocused() throws Exception{
        reCopy = true;
        performDeleteComponentThruRN("Package", null,EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }
    
    
    public void testDeleteISM_ThruRN_NoEditor() throws Exception{
        performDeleteComponentThruRN("InstanceStateMachine","X",EditorTestUtilities.EDITOR_TYPE_NONE, 2);
    }
    public void testDeleteMclass_ThruRN_NoEditor() throws Exception{
        performDeleteComponentThruRN("ModelClass","A",EditorTestUtilities.EDITOR_TYPE_NONE, 2);
    }
    public void testDeleteDTPInPkg_ThruRN_NoEditor() throws Exception{
        performDeleteComponentThruRN("Package", "SubDataTypes",EditorTestUtilities.EDITOR_TYPE_NONE, 2);
    }
    public void testDeleteSSInSS_ThruRN_NoEditor() throws Exception{
        performDeleteComponentThruRN("Package", "SSInSS1",EditorTestUtilities.EDITOR_TYPE_NONE, 2);
    }
    public void testDeleteSS_ThruRN_NoEditor() throws Exception{
    	reCopy =true;
        performDeleteComponentThruRN("Package", null,EditorTestUtilities.EDITOR_TYPE_NONE, 2);
    }
    public void testDeleteEEP_ThruRN_NoEditor() throws Exception{
        performDeleteComponentThruRN("Package", "External Entities",EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }
    public void testDeleteDomain_ThruRN_NoEditor() throws Exception{
        reCopy=true;
        performDeleteComponentThruRN("Package", null,EditorTestUtilities.EDITOR_TYPE_NONE, 2);
    }

	public void testDeleteSystem_ThruME_NoEditor() throws Exception{
        reCopy=true;
        performDeleteComponentThruMEGenerics("SystemModel", null,EditorTestUtilities.EDITOR_TYPE_NONE, 0);
    }
//    public void testDeleteSystem_ThruRN_NoEditor() throws Exception{
//        performDeleteComponentThruRN("SystemModel", null,EditorTestUtilities.EDITOR_TYPE_NONE, 0, false);
//    }
    /*  ENDS CODE: delete through Resource Navigator */
    
}