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

package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;

public class PkgCMModifyRelationTest extends ModifyRelationTest {

    protected static final String domainName = "MultiLevelModel";

    protected static String projectName = "com.mentor.nucleus.bp.io.mdl.modifyRelTest";

    protected static String mdlClassUnderTest = "X";

    protected static String dtpUnderTest = "SubDataTypes";

    protected static boolean reCopy_modRel = true;
    
    public PkgCMModifyRelationTest(String name) {
        super(projectName,name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setupProjectAndTestModel();
    }
    protected void setupProjectAndTestModel() throws CoreException {
        setupProject(projectName);
        ensureAvailableAndLoaded(domainName, false, true);
        CorePlugin.disableParseAllOnResourceChange();
    }
    /* Modify Tests through Canvas Editor : start */

    public void testModifySubsystemWithChildren() throws Exception {
        performModifyComponent("Subsystem", "SS1", "Class",false,true, 4, new String[] {"SSInSS1", "SSInSS12"});
    }
    
}
