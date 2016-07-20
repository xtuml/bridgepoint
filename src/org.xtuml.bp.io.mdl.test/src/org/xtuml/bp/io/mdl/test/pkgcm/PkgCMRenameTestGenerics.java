//=====================================================================
//
//File:      $RCSfile: PkgCMRenameTestGenerics.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/05/10 17:34:01 $
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

package org.xtuml.bp.io.mdl.test.pkgcm;

import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;

@RunWith(OrderedRunner.class)
public class PkgCMRenameTestGenerics extends RenameTest {

	@Rule public TestName name = new TestName();
	
	public String getName(){
		return name.getMethodName();
	}

    protected static  String projectName="MultiLevelModelSystem";
    protected static  String mdlClassUnderTest="X";
    protected static  String dtpUnderTest="SubDataTypes";
    protected static  String subsysInSubsysUnderTest="SSInSS1";
    protected static  String eepUnderTest="EEP1";
    
    public PkgCMRenameTestGenerics() {
        super(projectName, null);
        showModelExplorer();
    }
//    public PkgCMRenameTestGenerics() {
//        super(projName, null);
//        showModelExplorer();
//    }
    protected static boolean firstTime=true;
    @Before
	public void setUp() throws Exception {
        super.setUp();
        CorePlugin.disableParseAllOnResourceChange();
        setupProjectAndTestModel();
    }

	/**
	 * This method may be overridden by subclasses as restore tests are doing.
	 * 
	 * @throws CoreException
	 * 
	 */
	protected void setupProjectAndTestModel() throws CoreException {
		m_sys = getSystemModel(projectName);
		if (firstTime) {
			// ensureAvailableAndLoaded(domainName, false,false);
			loadProject(projectName);
			firstTime = false;
		}
		project = getProjectHandle(projectName);
		modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				getProjectHandle(projectName), "MultiLevelModel", true), true);
		graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
		Ooaofooa.setPersistEnabled(true);

	}

	//************************************************************************
// Header comments of each method show what cells it cover given in
// /Documentation/internal/plan/R1_1_0/Pkg CM Test Matrix.txt
// Methods with no comments cover one cell.
//************************************************************************


/*  Rename components through Model Explorer : start */  
//  Domain
    @Test
	public void testRenameDomain_ThruME_NoEditor() throws Exception{
        performRenameComponentThruMEGenerics("Package",null,EditorTestUtilities.EDITOR_TYPE_NONE, false,0);
    }
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameDomain_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",null, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 2);
    }
    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameDomain_ThruME_DescFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",null, EditorTestUtilities.EDITOR_TYPE_DESC, false, 2);
    }
    
    //Subsystem
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameSubSystem_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",null, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 2);
    }

    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameSubSystem_ThruME_DescFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",null, EditorTestUtilities.EDITOR_TYPE_DESC, false, 2);
    }
    @Test
	public void testRenameSubSystem_ThruME_NoEditor() throws Exception{
        performRenameComponentThruMEGenerics("Package",null, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
    }
    //EEP in Pkg
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    @Test
	public void testRenameEEPinPkg_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",eepUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false,2);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
    
    /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameEEPinPkg_ThruME_CanvasNotFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",eepUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, true, 2);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
    @Test
	public void testRenameEEPinPkg_ThruME_NoEditor() throws Exception{
        performRenameComponentThruMEGenerics("Package",eepUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
//  Subsystem In Subsystem
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameSubSystemInSubsys_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",subsysInSubsysUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 2);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }

    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameSubSysteInSubsysm_ThruME_DescFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package",subsysInSubsysUnderTest, EditorTestUtilities.EDITOR_TYPE_DESC, false, 2);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }
    @Test
	public void testRenameSubSystemInSubsys_ThruME_NoEditor() throws Exception{
        performRenameComponentThruMEGenerics("Package",subsysInSubsysUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }

    //DTPinPkg

    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    @Test
	public void testRenameDatatypePackageInPkg_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package", dtpUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 2);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }

    /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameDatatypePackageInPkg_ThruME_CanvasNotFocused() throws Exception{
        performRenameComponentThruMEGenerics("Package", dtpUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, true, 2);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }
    @Test
	public void testRenameDatatypePackageInPkg_ThruME_NoEditor() throws Exception{
        performRenameComponentThruMEGenerics("Package", dtpUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }
    //ModelClass
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameModelClass_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruMEGenerics("ModelClass", mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 5);
        if(toRunTests())//avoid to rename in setup pass
        mdlClassUnderTest+="_n";
    }

    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameModelClass_ThruME_DescFocused() throws Exception{
        performRenameComponentThruMEGenerics("ModelClass", mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_DESC, false, 5);
        if(toRunTests())//avoid to rename in setup pass
        mdlClassUnderTest+="_n";
    }
    @Test
	public void testRenameModelClass_ThruME_NoEditor() throws Exception{
        performRenameComponentThruMEGenerics("ModelClass", mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
            mdlClassUnderTest+="_n";
    }
    /*  Rename components through Model Explorer : end */

    /*  Rename components through Canvas Editor : start */

    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
//    public void testRenameSubSystem_ThruCE() throws Exception{
//        performRenameComponentThruCEGenerics("Package",null, 2);
//    }
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameSubSystemInSubsys_ThruCE() throws Exception{
        performRenameComponentThruCEGenerics("Package",subsysInSubsysUnderTest, 2);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    @Test
	public void testRenameDatatypePackageInPkg_ThruCE() throws Exception{
        performRenameComponentThruCEGenerics("Package", dtpUnderTest, 2);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }
    //EEP in Pkg
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    @Test
	public void testRenameEEPinPkg_ThruCE() throws Exception{
        performRenameComponentThruCEGenerics("Package",eepUnderTest, 2);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
    //ModelClass
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameModelClass_ThruCE() throws Exception{
        performRenameComponentThruCEGenerics("ModelClass", mdlClassUnderTest, 5);
        if(toRunTests())//avoid to rename in setup pass
            mdlClassUnderTest+="_n";
    }
    /*  Rename components through Canvas Editor : end */
    

    
    /*  Rename components through Resource Navigator : start */
    

    //EEP in Pkg
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    @Test
	public void testRenameEEPinPkg_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package",eepUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 3);
}
     /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameEEPinPkg_ThruRN_CanvasNotFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package",eepUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,true, 3);
    }
    @Test
	public void testRenameEEPinPkg_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRNGenerics("Package",eepUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    
    //  ModelClass
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
//    public void testRenameModelClass_ThruRN_CanvasFocused() throws Exception{
//        performRenameComponentThruRNGenerics("ModelClass", mdlClassUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
//    }
//    /**
//     * Focused     : Description Editor
//     * Not Focused : Canvas Editor
//     */
//    public void testRenameModelClass_ThruRN_DescFocused() throws Exception{
//        performRenameComponentThruRNGenerics("ModelClass", mdlClassUnderTest,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
//    }
//    public void testRenameModelClass_ThruRN_NoEditor() throws Exception{
//        performRenameComponentThruRNGenerics("ModelClass", mdlClassUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
//    }
//    //  Subsystem in Subsystem
//    /**
//     * Focused     : Canvas Editor
//     * Not Focused : Description Editor
//     */
//    public void testRenameSubSystemInSubsys_ThruRN_CanvasFocused() throws Exception{
//        performRenameComponentThruRNGenerics("Subsystem",subsysInSubsysUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
//    }
//    /**
//     * Focused     : Description Editor
//     * Not Focused : Canvas Editor
//     */
//    public void testRenameSubSystemInSubsys_ThruRN_DescFocused() throws Exception{
//        performRenameComponentThruRNGenerics("Subsystem",subsysInSubsysUnderTest,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
//    }
//    public void testRenameSubSystemInSubsys_ThruRN_NoEditor() throws Exception{
//        performRenameComponentThruRNGenerics("Subsystem",subsysInSubsysUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
//    }
    //  Subsystem
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameSubSystem_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package",null,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
    }
    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameSubSystem_ThruRN_DescFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package",null,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
    }
    @Test
	public void testRenameSubSystem_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRNGenerics("Package",null,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    //  DTPinPkg
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    @Test
	public void testRenameDatatypePackageInPkg_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package", dtpUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
    }
    /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    @Test
	public void testRenameDatatypePackageInPkg_ThruRN_CanvasNotFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package", dtpUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,true, 2);
    }
    @Test
	public void testRenameDatatypePackageInPkg_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRNGenerics("Package", dtpUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    
    //  Domain
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    @Test
	public void testRenameDomain_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package", null,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
    }
    @Test
	public void testRenameDomain_ThruRN_DescFocused() throws Exception{
        performRenameComponentThruRNGenerics("Package", null,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
    }
    @Test
	public void testRenameDomain_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRNGenerics("Package", null,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    @Test
	public void testRenameSystem_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRNGenerics("SystemModel",projectName,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
//  System
    @Test
	public void testRenameSystem_ThruME_NoEditor() throws Exception{
        performRenameComponentThruMEGenerics("SystemModel",projectName, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        projectName+="_n"; //projectName changed for coming tests
     //   super.setupProject(projectName);
     //((BaseTest)this).get// = getProjectHandle(projectName).getProject();
        super.setupProject(projectName);
       m_sys = getSystemModel(projectName);   
        modelRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(getProjectHandle(projectName), projectName, true), true);

    //    graphicsModelRoot = Ooaofgraphics.getInstance(modelRoot.getId());
        getProject().delete(true, null);
    }

    /*  Rename components through Resource Navigator : end */
}
