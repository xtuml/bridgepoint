//=====================================================================
//
//File:      $RCSfile: PkgCMModifyContentsTest.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 05:16:04 $
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

import java.io.File;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.internal.Workbench;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class PkgCMModifyContentsTest extends ModifyContentsTest {

    protected static final String domainName = "MultiLevelModel";

    protected static String mdlClassUnderTest = "X";

    protected static String dtpUnderTest = "SubDataTypes";

    protected static String subsysInSubsysUnderTest = "SSInSS1";

    protected static String eepUnderTest = "EEP1";

    protected static String projectName = "com.mentor.nucleus.bp.io.mdl.ModifyContTest";

    public PkgCMModifyContentsTest(String name) {
        this(projectName, name);
    }

    public PkgCMModifyContentsTest(String projectName, String name) {
        super(projectName, name);
        CorePlugin.disableParseAllOnResourceChange();
        showModelExplorer();
    }

    protected static boolean firstTime_modCont = true;

    protected void setUp() throws Exception {
        super.setUp();
        IPath in_path = new Path(m_logfile_path);
        File in_fh = in_path.toFile();
        if ( in_fh.exists() )
        {
            IPath newPath=in_path.removeLastSegments(1).append(getName()+".log");
            in_fh.renameTo(newPath.toFile()); //$NON-NLS-1$
        }
        setupProjectAndTestModel();
    }
    protected void tearDown()throws Exception{
        super.tearDown();
        if (toRunTests()) // close editors after test run
        {
            Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().
            closeAllEditors(true);
        }
    }

    protected void setupProjectAndTestModel() throws CoreException {
        if (firstTime_modCont) {
            ensureAvailableAndLoaded(domainName, false);
            // IO_MDL tests set this false we need it true
            Ooaofooa.setPersistEnabled(true);
            BaseTest.dispatchEvents(0);

        }
        firstTime_modCont = false;

    }

    // ************************************************************************
    // Header comments of each method show what cells it cover given in
    // /Documentation/internal/plan/R1_1_0/Pkg CM Test Matrix.txt
    // Methods with no comments cover one cell.
    // ************************************************************************

    /* Modify components through Model Explorer : start */

    // Domain
    /**
     * Focused : Canvas Editor Not Focused : Description Editor
     */
    public void testModifyContentDomain_Desc() throws Exception {
        performModifyComponentContents("Domain", null,
                EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }

    /**
     * Focused : Canvas Editor Not Focused : Description Editor
     */
    public void testModifyContentDomain_Canvas() throws Exception {
        performModifyComponentContents("Domain", null,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
    }

    // EEP in Pkg
    /**
     * Focused : Activity Editor Not Focused : Canvas Editor, Description Editor
     */
    public void testModifyContentEEPkgInPkg_Activity() throws Exception {
        performModifyComponentContents("ExternalEntityPackage", eepUnderTest,
                EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 3);
    }

    /**
     * Focused : Description Editor Not Focused : Canvas Editor, Activity Editor
     */
    public void testModifyContentEEPkgInPkg_Desc() throws Exception {
        performModifyComponentContents("ExternalEntityPackage", eepUnderTest,
                EditorTestUtilities.EDITOR_TYPE_DESC, 3);
    }

    /**
     * Focused : Canvas Editor Not Focused : Description Editor, Activity Editor
     */
    public void testModifyContentEEPkgInPkg_Canvas() throws Exception {
        performModifyComponentContents("ExternalEntityPackage", eepUnderTest,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, 3);
    }

    // Subsystem
    /**
     * Focused : Canvas Editor Not Focused : Description Editor
     */
    public void testModifyContentSubSystem_Canvas() throws Exception {
        performModifyComponentContents("Subsystem", null,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
    }

    /**
     * Focused : Description Editor Not Focused : Canvas Editor
     */
    public void testModifyContentSubSystem_Desc() throws Exception {
        performModifyComponentContents("Subsystem", null,
                EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }

    // Subsystem In Subsystem
    /**
     * Focused : Canvas Editor Not Focused : Description Editor
     */
    public void testModifyContentSubSystemInSub_Canvas() throws Exception {
        performModifyComponentContents("Subsystem", subsysInSubsysUnderTest,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
    }

    /**
     * Focused : Description Editor Not Focused : Canvas Editor
     */
    public void testModifyContentSubSystemInSub_Desc() throws Exception {
        performModifyComponentContents("Subsystem", subsysInSubsysUnderTest,
                EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }

    // DTPinPkg
    /**
     * Focused : Canvas Editor Not Focused : Description Editor
     */
    public void testModifyContentDatatypePkgInPkg_Canvas() throws Exception {
        performModifyComponentContents("DataTypePackage", dtpUnderTest,
                EditorTestUtilities.EDITOR_TYPE_CANVAS, 2);
    }

    /**
     * Focused : Description Editor Not Focused : Canvas Editor
     */
    public void testModifyContentDatatypePkgInPkg_Desc() throws Exception {
        performModifyComponentContents("DataTypePackage", dtpUnderTest,
                EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }

    // ModelClass
    /**
     * Focused : Description Editor Not Focused : Activity Editor
     */
    public void testModifyContentModelClass_Desc() throws Exception {
        performModifyComponentContents("ModelClass", mdlClassUnderTest,
                EditorTestUtilities.EDITOR_TYPE_DESC, 2);
    }

    /**
     * Focused : Activity Editor Not Focused : Description Editor
     */
    public void testModifyContentModelClass_Activity() throws Exception {
        performModifyComponentContents("ModelClass", mdlClassUnderTest,
                EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 2);
    }

    // ISM
    /**
     * Focused : Canvas Editor Not Focused : Description Editor, Activity Editor
     */
    public void testModifyContentISM_Canvas() throws Exception {
        performModifyComponentContents("InstanceStateMachine",
                mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_CANVAS, 3);
    }

    /**
     * Focused : Description Editor Not Focused : Canvas Editor, Activity Editor
     */
    public void testModifyContentISM_Desc() throws Exception {
        performModifyComponentContents("InstanceStateMachine",
                mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_DESC, 3);
    }

    /**
     * Focused : Activity Editor Not Focused : Canvas Editor, Description Editor
     */
    public void testModifyContentISM_Activity() throws Exception {
        performModifyComponentContents("InstanceStateMachine",
                mdlClassUnderTest, EditorTestUtilities.EDITOR_TYPE_ACTIVITY, 3);
    }
}
