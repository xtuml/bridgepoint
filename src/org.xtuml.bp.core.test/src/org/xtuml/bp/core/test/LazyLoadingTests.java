package org.xtuml.bp.core.test;
//=====================================================================
//
//File:      $RCSfile: LazyLoadingTests.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:05 $
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

@RunWith(OrderedRunner.class)
public class LazyLoadingTests extends BaseTest {

	private static IProject this_project;
	private static SystemModel_c this_system;

	@Override
	public void initialSetup() {
		try {
			this_project = TestingUtilities.createProject("lazy_loading");
			this_system = getSystemModel(this_project.getName());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		// enable persistence
		Ooaofooa.setPersistEnabled(true);
	}
	
	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		// disable persistence
		Ooaofooa.setPersistEnabled(false);
	}
	
	@Test
	public void testInterfaceUnderPackage() {
		Interface_c iface = null;
		Package_c pkg = null;
		Transaction transaction = null;
		try {
			transaction = TransactionManager.getSingleton().startTransaction(
					"Create test elements",
					new ModelElement[] { Ooaofooa.getDefaultInstance() });
			this_system.Newpackage();
			Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(this_system);
			pkg = pkgs[pkgs.length - 1];
			pkg.Newinterface();
			Interface_c[] ifaces = Interface_c
					.getManyC_IsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(pkg));
			iface = ifaces[ifaces.length - 1];
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {
			if(transaction != null) {
				TransactionManager.getSingleton().endTransaction(transaction);
			}
			fail(e.getMessage());
		}
		BaseTest.dispatchEvents(0);
		assertNotNull(iface);
		assertNotNull(pkg);
		iface.getPersistableComponent().clearDatabase();
		Interface_c[] ifacesAfterLoad = Interface_c.getManyC_IsOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(pkg, false), false);
		assertTrue("Interface component was not unloaded for test.",
				ifacesAfterLoad.length == 0);
		ifacesAfterLoad = Interface_c.getManyC_IsOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(pkg));
		assertTrue("Interface component was not loaded after unload.",
				ifacesAfterLoad.length > 0);
	}
	
}
