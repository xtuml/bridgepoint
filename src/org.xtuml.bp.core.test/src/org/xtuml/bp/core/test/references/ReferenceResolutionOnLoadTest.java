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
package org.xtuml.bp.core.test.references;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.xtuml.bp.core.CoreDataType_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.UITestingUtilities;

public class ReferenceResolutionOnLoadTest extends BaseTest {
	public void testResolutionGoodPathToRTO() {
		SystemModel_c systemModel = getSystemModel();
		TestUtil.executeInTransaction(systemModel, "Newpackage", new Object[0]);
		Package_c pkg1 = Package_c.getOneEP_PKGOnR1401(systemModel);
		TestUtil.executeInTransaction(pkg1, "setName", new Object[] {"pkg1"});
		TestUtil.executeInTransaction(pkg1, "Newdatatype", new Object[0]);
		UserDataType_c udt = UserDataType_c
				.getOneS_UDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg1)));
		TestUtil.executeInTransaction(systemModel, "Newpackage", new Object[0]);
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(systemModel);
		Package_c pkg2 = packages[1];
		TestUtil.executeInTransaction(pkg2, "setName", new Object[] {"pkg2"});
		TestUtil.executeInTransaction(pkg2, "Newdatatype", new Object[0]);		
		UserDataType_c udt2 = UserDataType_c
				.getOneS_UDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg2)));
		TestUtil.executeInTransaction(udt, "relateAcrossR18To", new Object[] {udt2});
		// trigger a reload
		TestUtil.closeProject(getProject());
		TestUtil.openProject(getProject());
		// select the original udt and check its RTO
		DataType_c coreType = DataType_c.getOneS_DTOnR18(udt);
		UserDataType_c reloadedCoreType = UserDataType_c.getOneS_UDTOnR17(coreType);
		assertTrue("Upon reload an incorrect core type was found.", reloadedCoreType.equals(udt2));
	}
	
	public void testResolutionBadPathToRTO() {
		SystemModel_c systemModel = getSystemModel();
		TestUtil.executeInTransaction(systemModel, "Newpackage", new Object[0]);
		Package_c pkg1 = Package_c.getOneEP_PKGOnR1401(systemModel);
		TestUtil.executeInTransaction(pkg1, "setName", new Object[] {"pkg1"});
		TestUtil.executeInTransaction(pkg1, "Newdatatype", new Object[0]);
		UserDataType_c udt = UserDataType_c
				.getOneS_UDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg1)));
		TestUtil.executeInTransaction(systemModel, "Newpackage", new Object[0]);
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(systemModel);
		Package_c pkg2 = packages[packages.length - 1];
		TestUtil.executeInTransaction(pkg2, "setName", new Object[] {"pkg2"});
		TestUtil.executeInTransaction(pkg2, "Newdatatype", new Object[0]);		
		UserDataType_c udt2 = UserDataType_c
				.getOneS_UDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg2)));
		TestUtil.executeInTransaction(udt, "relateAcrossR18To", new Object[] {udt2});
		packages = Package_c.getManyEP_PKGsOnR1401(systemModel);
		Package_c dest = packages[packages.length - 1];
		TestUtil.executeInTransaction(dest, "setName", new Object[] {"dest"});
		// move the package containing the RTO
		UITestingUtilities.cutElement(pkg2, null);
		UITestingUtilities.pasteClipboardContentsInExplorer(dest);
		// trigger a reload
		TestUtil.closeProject(getProject());
		TestUtil.openProject(getProject());
		// select the original udt and check its RTO
		DataType_c coreType = DataType_c.getOneS_DTOnR18(udt);
		UserDataType_c reloadedCoreType = UserDataType_c.getOneS_UDTOnR17(coreType);
		assertTrue("Upon reload a reference with a bad proxy path was not located.", reloadedCoreType.equals(udt2));
		// verify the proxy path for the reloaded element
		udt = UserDataType_c
				.getOneS_UDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg1)));
	}
	
	public void testResolutionNonExistent() throws CoreException {
		SystemModel_c systemModel = getSystemModel();
		TestUtil.executeInTransaction(systemModel, "Newpackage", new Object[0]);
		Package_c pkg1 = Package_c.getOneEP_PKGOnR1401(systemModel);
		TestUtil.executeInTransaction(pkg1, "setName", new Object[] {"pkg1"});
		TestUtil.executeInTransaction(pkg1, "Newdatatype", new Object[0]);
		UserDataType_c udt = UserDataType_c
				.getOneS_UDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg1)));
		TestUtil.executeInTransaction(systemModel, "Newpackage", new Object[0]);
		Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(systemModel);
		Package_c pkg2 = packages[1];
		TestUtil.executeInTransaction(pkg2, "setName", new Object[] {"pkg2"});
		TestUtil.executeInTransaction(pkg2, "Newdatatype", new Object[0]);		
		UserDataType_c udt2 = UserDataType_c
				.getOneS_UDTOnR17(DataType_c.getOneS_DTOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg2)));
		TestUtil.executeInTransaction(udt, "relateAcrossR18To", new Object[] {udt2});
		// delete the core type without bp listening
		TestUtil.closeProject(getProject());
		udt2.getFile().delete(true, new NullProgressMonitor());
		TestUtil.openProject(getProject());
		// select the original udt and check its RTO
		DataType_c coreType = DataType_c.getOneS_DTOnR18(udt);
		UserDataType_c reloadedCoreType = UserDataType_c.getOneS_UDTOnR17(coreType);
		assertTrue("Upon reload a non-existent reference was located.", !reloadedCoreType.equals(udt2));		
	}
}
