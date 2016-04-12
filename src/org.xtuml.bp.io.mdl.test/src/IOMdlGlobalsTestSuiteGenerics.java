//=====================================================================
//
//File:      $RCSfile: IOMdlGlobalsTestSuiteGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 05:15:20 $
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
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.test.GlobalTestSetupClass;
import org.xtuml.bp.io.mdl.test.IOMdlGlobalsTestGenerics;
import org.xtuml.bp.io.mdl.test.IOMdlNestedTestGenerics;
import org.xtuml.bp.io.mdl.test.IOMdlUnicodeTestGenerics;
import org.xtuml.bp.io.mdl.test.ImportPasteElementsWithIPRTest;
import org.xtuml.bp.io.mdl.test.ImportReferencedIPRModelTest;
import org.xtuml.bp.io.mdl.test.ProxyTestsGenerics;
import org.xtuml.bp.io.mdl.test.StaleProxyExportTestGenerics;

import junit.framework.Test;
import junit.framework.TestSuite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   GlobalTestSetupClass.class,
   IOMdlGlobalsTestGenerics.class,
   IOMdlNestedTestGenerics.class,
   IOMdlTestGenerics.class,
   IOMdlUnicodeTestGenerics.class,
   ImportReferencedIPRModelTest.class,
   ImportPasteElementsWithIPRTest.class,
   ProxyTestsGenerics.class,
   StaleProxyExportTestGenerics.class,  
})
public class IOMdlGlobalsTestSuiteGenerics extends TestSuite {


}