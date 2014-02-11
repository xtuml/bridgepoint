//========================================================================
//
//File:      $RCSfile: SLDTTestsGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 05:13:51 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//======================================================================== 
//
//  This class is responsible for testing the BridgePoint import
//  wizard
//

package com.mentor.nucleus.bp.io.mdl.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.GlobalElementInSystem_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;


public class SLDTTestsGenerics extends BaseTest {

	/**
	 * Tests that when importing a core data types package, the graphical
	 * elements for the removed core types are also removed
	 * 
	 * @throws CoreException
	 */
	public void testCoreTypeOfSLDTForNewProject()
			throws CoreException {
		IProject testProject = TestingUtilities
				.createProject("TestSLDTPackageGenerics");

		// get the system model associated with the project
		// created above
		SystemModel_c systemModel = getSystemModel(testProject.getName());
		assertNotNull("Error getting the system model for this test.",
				systemModel);
		
		GlobalElementInSystem_c[] gis = GlobalElementInSystem_c.getManyG_EISsOnR9100(systemModel);
		assertNotNull("Unable to locate system data type package.", gis);
		
		DataType_c[] dt = DataType_c.getManyS_DTsOnR8001(PackageableElement_c.getManyPE_PEsOnR9100(gis));
		assertNotNull("Unable to locate data type package.", dt);		
		
		UserDataType_c[] udt = UserDataType_c.getManyS_UDTsOnR17(dt);
		assertNotNull("Unable to locate user data type.", udt);
		
		
		CoreDataType_c cdt;
		int genType;
		
		for(int i = 0; i < udt.length; i++)
		 {
			genType = udt[i].getGen_type();
			cdt = CoreDataType_c.getOneS_CDTOnR17(DataType_c.getOneS_DTOnR18(udt[i]));
			if(cdt != null) {
				assertNotNull("Unable to locate core data type.", cdt);
				
				if(genType == 1 || genType == 2)
				{
					
				 assertTrue("Wrong core type.", cdt.Get_name().equals("inst<Mapping>"));
					 
				}
				else if (genType == 3)
				{
					
				 assertTrue("wrong core type.", cdt.Get_name().equals("inst_ref<Mapping>"));
					 						  	
				}
			}
				
		 }
	}
}
