package org.xtuml.bp.io.mdl.test;
//=====================================================================
//
//File:      IOMdlPublishReferencesTest.java
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.InstanceReferenceDataType_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;

@RunWith(OrderedRunner.class)
public class IOMdlPublishReferencesTest extends BaseTest {

	public IOMdlPublishReferencesTest() {
		super("org.xtuml.bp.io.mdl.test", null);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testUpgradeModelWithPublishReference() throws Exception{
		// Load from git
		this.loadProject("InstanceReferenceTestMatrixModel");
			
		SystemModel_c sys = getSystemModel("InstanceReferenceTestMatrixModel");
		Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(sys);
		Package_c pkg = null;
		for(int i = 0; i < pkgs.length; i++) {
			if(pkgs[i].getName().equalsIgnoreCase("Subsystem")) {
				pkg = pkgs[i];
				break;
			}
		}
		ModelClass_c testModelClass = ModelClass_c.ModelClassInstance(pkg.getModelRoot(), new ModelClass_by_name_c("Accident"));
		Attribute_c[] attrs = Attribute_c.getManyO_ATTRsOnR102(testModelClass);
		for (Attribute_c attribute : attrs) {
			if ( attribute.getName().equalsIgnoreCase("vehicleReference") || attribute.getName().equalsIgnoreCase("selfReference")){
				assertTrue( "IRDT is null and it should not be.", null != InstanceReferenceDataType_c.getOneS_IRDTOnR17(DataType_c.getOneS_DTOnR114(attribute)));
			}
		}
	}
}
