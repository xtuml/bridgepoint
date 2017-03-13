//========================================================================
//
//File:      $RCSfile: ImportReferencedIPRModelTest.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 05:13:52 $
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
//  This class is responsible to import the model used in IPR feature test
//  done in ImportPasteElementsWithIPRTest
//
package org.xtuml.bp.io.mdl.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

@RunWith(OrderedRunner.class)
public class ImportReferencedIPRModelTest extends BaseTest {
	
	private static boolean initialized = false;
	private String projectName = "Library";
	

	public ImportReferencedIPRModelTest() throws Exception {
		super(null,null);
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		if (!initialized){
			// Load from git
			this.loadProject("Library");

			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(projectName);
			

			project.getName();
			m_sys = getSystemModel(project.getName());

			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			initialized = true;
		}
	}
	
	@Test
	public void test_nothing() throws CoreException{
			assertTrue(true);
	}
	
}
