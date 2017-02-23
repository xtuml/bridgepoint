//=====================================================================
//
//File:      $RCSfile: PkgCMGlobalsTestSuiteGenerics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:13:04 $
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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xtuml.bp.io.mdl.test.GlobalTestSetupClass;
import org.xtuml.bp.io.mdl.test.PkgCMGlobalsTestGenerics;
import org.xtuml.bp.io.mdl.test.pkgcm.PkgCMCreateTestGenerics;
import org.xtuml.bp.io.mdl.test.pkgcm.PkgCMDeleteTestGenerics;
import org.xtuml.bp.io.mdl.test.pkgcm.PkgCMModifyContentsTestGenerics;
import org.xtuml.bp.io.mdl.test.pkgcm.PkgCMModifyRelationTestGenerics;
import org.xtuml.bp.io.mdl.test.pkgcm.PkgCMRenameTestGenerics;

import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GlobalTestSetupClass.class,
	PkgCMGlobalsTestGenerics.class,
	PkgCMModifyContentsTestGenerics.class,
	PkgCMModifyRelationTestGenerics.class,
	PkgCMCreateTestGenerics.class,
	PkgCMRenameTestGenerics.class,
	PkgCMDeleteTestGenerics.class,
	
})
public class PkgCMGlobalsTestSuiteGenerics extends TestSuite {

}