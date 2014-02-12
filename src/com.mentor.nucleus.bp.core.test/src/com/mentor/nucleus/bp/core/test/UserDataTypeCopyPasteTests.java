package com.mentor.nucleus.bp.core.test;

//========================================================================
//
//File:      $RCSfile: UserDataTypeCopyPasteTests.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:05 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
import org.eclipse.core.resources.IProject;

import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class UserDataTypeCopyPasteTests extends BaseTest {

	private static Package_c testPackage;
	private static UserDataType_c firstUDT;
	private static UserDataType_c secondUDT;
	private static SystemModel_c destSys;

	@Override
	protected void initialSetup() throws Exception {
		IProject destinationProject = TestingUtilities
				.createProject("destination");
		destSys = getSystemModel(destinationProject.getName());
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Setup test model",
					new ModelElement[] { Ooaofooa.getDefaultInstance() });
			// setup test model
			m_sys.Newpackage();
			testPackage = Package_c.getOneEP_PKGOnR1401(m_sys);
			testPackage.Newdatatype();
			firstUDT = UserDataType_c.getOneS_UDTOnR17(DataType_c
					.getManyS_DTsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(testPackage)));
			UserDataType_c[] udts = UserDataType_c
					.getManyS_UDTsOnR17(DataType_c
							.getManyS_DTsOnR8001(PackageableElement_c
									.getManyPE_PEsOnR8000(testPackage)));
			secondUDT = udts[udts.length - 1];
			firstUDT.relateAcrossR18To(DataType_c.getOneS_DTOnR17(secondUDT));
			destSys.Newpackage();
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (Exception e) {
			if (transaction != null) {
				TransactionManager.getSingleton()
						.cancelTransaction(transaction);
			}
			fail(e.getMessage());
		}
	}

	public void testPasteOfUDTAssignedToUDT() {
		// must select the elements in the proper order
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(firstUDT);
		Selection.getInstance().addToSelection(secondUDT);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		// paste into the new project
		UITestingUtilities.pasteClipboardContentsInExplorer(Package_c
				.getOneEP_PKGOnR1401(destSys));
		UserDataType_c[] udts = UserDataType_c.getManyS_UDTsOnR17(DataType_c
				.getManyS_DTsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getOneEP_PKGOnR1401(destSys))));
		boolean foundUDTAsBaseType = false;
		for (int i = 0; i < udts.length; i++) {
			DataType_c baseType = DataType_c.getOneS_DTOnR18(udts[i]);
			UserDataType_c assigned = UserDataType_c.getOneS_UDTOnR17(baseType);
			if (assigned != null) {
				foundUDTAsBaseType = true;
			}
		}
		assertTrue("Paste did not properly leave UDT base type.",
				foundUDTAsBaseType);
	}
}
