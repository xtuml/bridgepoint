//========================================================================
//
//File:      $RCSfile: SLDTTests.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:12:54 $
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
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.SystemDatatypeInPackage_c;
import com.mentor.nucleus.bp.core.SystemDatatypePackage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;


public class SLDTTests extends BaseTest {

	/**
	 * Tests that when importing a core data types package, the graphical
	 * elements for the removed core types are also removed
	 * 
	 * @throws CoreException
	 */
	public void testCoreTypeOfSLDTForNewProject()
			throws CoreException {
		IProject testProject = TestingUtilities
				.createProject("TestSLDTPackage");

		// get the system model associated with the project
		// created above
		SystemModel_c systemModel = getSystemModel(testProject.getName());
		assertNotNull("Error getting the system model for this test.",
				systemModel);
		// open the diagram for the data types package found under
		// the system model
		DataTypePackage_c dtPackage = DataTypePackage_c
				.getOneS_DPKOnR4400(SystemDatatypePackage_c
						.getOneSLD_SDPOnR4400(systemModel));
		assertNotNull("Unable to locate core data type package.", dtPackage);
		
		
		
		SystemDatatypeInPackage_c[] sdip = SystemDatatypeInPackage_c.getManySLD_SDINPsOnR4402(systemModel);
		assertNotNull("Unable to locate system data type package.", sdip);
		
		DataType_c[] dt = DataType_c.getManyS_DTsOnR4401(sdip);
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
