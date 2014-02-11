//=====================================================================
//
//File:      $RCSfile: PkgCMRenameTest.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:12:56 $
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

import com.mentor.nucleus.bp.core.CorePlugin;

public class PkgCMRenameTest extends RenameTest {

    protected static String domainName="MultiLevelModel";
    protected static  String projectName="com.mentor.nucleus.bp.io.mdl.renameTest";
    protected static  String mdlClassUnderTest="X";
    protected static  String dtpUnderTest="SubDataTypes";
    protected static  String subsysInSubsysUnderTest="SSInSS1";
    protected static  String eepUnderTest="EEP1";
    
    public PkgCMRenameTest(String name) {
        this(projectName, name);
    }
    public PkgCMRenameTest(String projName,String name) {
        super(projName, name);
        showModelExplorer();
    }
    protected static boolean firstTime=true;
    protected void setUp() throws Exception {
        super.setUp();
        CorePlugin.disableParseAllOnResourceChange();
        setupProjectAndTestModel();
    }
/**
 * This method may be overridden by subclasses as restore tests are doing. 
 * @throws CoreException 
 *
 */    
protected void setupProjectAndTestModel() throws CoreException {
    if(firstTime){
        ensureAvailableAndLoaded(domainName, false,false);      
        }
        firstTime=false;
        //projectName should be set in test which cause it to change
        setupProject(projectName);
    }
//************************************************************************
// Header comments of each method show what cells it cover given in
// /Documentation/internal/plan/R1_1_0/Pkg CM Test Matrix.txt
// Methods with no comments cover one cell.
//************************************************************************


/*  Rename components through Model Explorer : start */  
//  Domain
    public void testRenameDomain_ThruME_NoEditor() throws Exception{
        performRenameComponentThruME("Domain",null,EditorTestUtilities.EDITOR_TYPE_NONE, false,0);
    }
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameDomain_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruME("Domain",null, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 2);
    }
    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    public void testRenameDomain_ThruME_DescFocused() throws Exception{
        performRenameComponentThruME("Domain",null, EditorTestUtilities.EDITOR_TYPE_DESC, false, 2);
    }
    
    //Subsystem
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameSubSystem_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruME("Subsystem",null, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 2);
    }

    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    public void testRenameSubSystem_ThruME_DescFocused() throws Exception{
        performRenameComponentThruME("Subsystem",null, EditorTestUtilities.EDITOR_TYPE_DESC, false, 2);
    }
    public void testRenameSubSystem_ThruME_NoEditor() throws Exception{
        performRenameComponentThruME("Subsystem",null, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
    }
    //EEP in Pkg
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    public void testRenameEEPinPkg_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruME("ExternalEntityPackage",eepUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 1);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
    
    /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    public void testRenameEEPinPkg_ThruME_CanvasNotFocused() throws Exception{
        performRenameComponentThruME("ExternalEntityPackage",eepUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, true, 1);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
    public void testRenameEEPinPkg_ThruME_NoEditor() throws Exception{
        performRenameComponentThruME("ExternalEntityPackage",eepUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
//  Subsystem In Subsystem
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameSubSystemInSubsys_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruME("Subsystem",subsysInSubsysUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 2);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }

    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    public void testRenameSubSysteInSubsysm_ThruME_DescFocused() throws Exception{
        performRenameComponentThruME("Subsystem",subsysInSubsysUnderTest, EditorTestUtilities.EDITOR_TYPE_DESC, false, 2);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }
    public void testRenameSubSystemInSubsys_ThruME_NoEditor() throws Exception{
        performRenameComponentThruME("Subsystem",subsysInSubsysUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }

    //DTPinPkg

    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    public void testRenameDatatypePackageInPkg_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruME("DataTypePackage", dtpUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 1);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }

    /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    public void testRenameDatatypePackageInPkg_ThruME_CanvasNotFocused() throws Exception{
        performRenameComponentThruME("DataTypePackage", dtpUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, true, 1);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }
    public void testRenameDatatypePackageInPkg_ThruME_NoEditor() throws Exception{
        performRenameComponentThruME("DataTypePackage", dtpUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }
    //ModelClass
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameModelClass_ThruME_CanvasFocused() throws Exception{
        performRenameComponentThruME("ModelClass", mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, false, 5);
        if(toRunTests())//avoid to rename in setup pass
        mdlClassUnderTest+="_n";
    }

    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    public void testRenameModelClass_ThruME_DescFocused() throws Exception{
        performRenameComponentThruME("ModelClass", mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_DESC, false, 5);
        if(toRunTests())//avoid to rename in setup pass
        mdlClassUnderTest+="_n";
    }
    public void testRenameModelClass_ThruME_NoEditor() throws Exception{
        performRenameComponentThruME("ModelClass", mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
            mdlClassUnderTest+="_n";
    }

    /*  Rename components through Model Explorer : end */

    /*  Rename components through Canvas Editor : start */

    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameSubSystem_ThruCE() throws Exception{
        performRenameComponentThruCE("Subsystem",null, 2);
    }
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameSubSystemInSubsys_ThruCE() throws Exception{
        performRenameComponentThruCE("Subsystem",subsysInSubsysUnderTest, 2);
        if(toRunTests())//avoid to rename in setup pass
        subsysInSubsysUnderTest+="_n";
    }
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    public void testRenameDatatypePackageInPkg_ThruCE() throws Exception{
        performRenameComponentThruCE("DataTypePackage", dtpUnderTest, 1);
        if(toRunTests())//avoid to rename in setup pass
        dtpUnderTest+="_n";
    }
    //EEP in Pkg
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    public void testRenameEEPinPkg_ThruCE() throws Exception{
        performRenameComponentThruCE("ExternalEntityPackage",eepUnderTest, 1);
        if(toRunTests())//avoid to rename in setup pass
        eepUnderTest+="_n";
    }
    //ModelClass
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameModelClass_ThruCE() throws Exception{
        performRenameComponentThruCE("ModelClass", mdlClassUnderTest, 5);
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
    public void testRenameEEPinPkg_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRN("ExternalEntityPackage",eepUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 3);
}
     /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    public void testRenameEEPinPkg_ThruRN_CanvasNotFocused() throws Exception{
        performRenameComponentThruRN("ExternalEntityPackage",eepUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,true, 3);
    }
    public void testRenameEEPinPkg_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRN("ExternalEntityPackage",eepUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    
    //  ModelClass
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
//    public void testRenameModelClass_ThruRN_CanvasFocused() throws Exception{
//        performRenameComponentThruRN("ModelClass", mdlClassUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
//    }
//    /**
//     * Focused     : Description Editor
//     * Not Focused : Canvas Editor
//     */
//    public void testRenameModelClass_ThruRN_DescFocused() throws Exception{
//        performRenameComponentThruRN("ModelClass", mdlClassUnderTest,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
//    }
//    public void testRenameModelClass_ThruRN_NoEditor() throws Exception{
//        performRenameComponentThruRN("ModelClass", mdlClassUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
//    }
//    //  Subsystem in Subsystem
//    /**
//     * Focused     : Canvas Editor
//     * Not Focused : Description Editor
//     */
//    public void testRenameSubSystemInSubsys_ThruRN_CanvasFocused() throws Exception{
//        performRenameComponentThruRN("Subsystem",subsysInSubsysUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
//    }
//    /**
//     * Focused     : Description Editor
//     * Not Focused : Canvas Editor
//     */
//    public void testRenameSubSystemInSubsys_ThruRN_DescFocused() throws Exception{
//        performRenameComponentThruRN("Subsystem",subsysInSubsysUnderTest,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
//    }
//    public void testRenameSubSystemInSubsys_ThruRN_NoEditor() throws Exception{
//        performRenameComponentThruRN("Subsystem",subsysInSubsysUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
//    }
    //  Subsystem
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameSubSystem_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRN("Subsystem",null,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
    }
    /**
     * Focused     : Description Editor
     * Not Focused : Canvas Editor
     */
    public void testRenameSubSystem_ThruRN_DescFocused() throws Exception{
        performRenameComponentThruRN("Subsystem",null,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
    }
    public void testRenameSubSystem_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRN("Subsystem",null,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    //  DTPinPkg
    /**
     * Focused     : Canvas Editor
     * Not Focused : None
     */
    public void testRenameDatatypePackageInPkg_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRN("DataTypePackage", dtpUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
    }
    /**
     * Focused     : None
     * Not Focused : Canvas Editor
     */
    public void testRenameDatatypePackageInPkg_ThruRN_CanvasNotFocused() throws Exception{
        performRenameComponentThruRN("DataTypePackage", dtpUnderTest,EditorTestUtilities.EDITOR_TYPE_CANVAS,true, 2);
    }
    public void testRenameDatatypePackageInPkg_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRN("DataTypePackage", dtpUnderTest,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    
    //  Domain
    /**
     * Focused     : Canvas Editor
     * Not Focused : Description Editor
     */
    public void testRenameDomain_ThruRN_CanvasFocused() throws Exception{
        performRenameComponentThruRN("Domain", null,EditorTestUtilities.EDITOR_TYPE_CANVAS,false, 2);
    }
    public void testRenameDomain_ThruRN_DescFocused() throws Exception{
        performRenameComponentThruRN("Domain", null,EditorTestUtilities.EDITOR_TYPE_DESC,false, 2);
    }
    public void testRenameDomain_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRN("Domain", null,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
    public void testRenameSystem_ThruRN_NoEditor() throws Exception{
        performRenameComponentThruRN("SystemModel",projectName,EditorTestUtilities.EDITOR_TYPE_NONE,false, 0);
    }
//  System
    public void testRenameSystem_ThruME_NoEditor() throws Exception{
        performRenameComponentThruME("SystemModel",projectName, EditorTestUtilities.EDITOR_TYPE_NONE, false, 0);
        if(toRunTests())//avoid to rename in setup pass
        projectName+="_n"; //projectName changed for coming tests
        setupProject(projectName);
    }
    /*  Rename components through Resource Navigator : end */
}
