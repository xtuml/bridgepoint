package com.mentor.nucleus.bp.io.mdl.test;

//=====================================================================
//
//File:      $RCSfile: SpecificationPackageUpgradeTestsGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:12:54 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;


import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class SpecificationPackageUpgradeTestsGenerics extends BaseTest {
	    
    /**
     * Whether this test run is meant to produce the expected
     * results files, rather than actually perform the tests. 
     */
    private static boolean generateResults = false;

	protected void setUp() throws Exception {
		super.setUp();
	}
    
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	public void testSpecificationPackageUpgradeGenerics() throws CoreException, IOException {
		File expectedResultsFile1 = new File(
				m_workspace_path
						+ "expected_results/specification_package_upgrade_results1Generics.txt");
		File expectedResultsFile2 = new File(
				m_workspace_path
						+ "expected_results/specification_package_upgrade_results2Generics.txt");
		// get the component package which contains
		// all of the specification packages
//		ensureAvailableAndLoaded("com.mentor.nucleus.bp.io.mdl.test/models/SpecificationPackageUpgradeTestingGenerics.xtuml",
//				"Specification Package Upgrade Testing", false, true,
// 				"Package");
    	Ooaofooa.setPersistEnabled(true);
	    String	projectName =  getProject().getName();
	    if(getProject()!=null && getProject().exists()){
            try{
                getProject().delete(true, null); 
             }catch(Exception e){}
     }        

        setupProject(projectName);
      
       while(Display.getCurrent().readAndDispatch());
       m_sys = getSystemModel(projectName);
		TestingUtilities.importModelUsingWizardConvertToGenerics(m_sys, m_workspace_path + Ooaofooa.MODELS_DIRNAME +"/SpecificationPackageUpgradeTestingGenerics.xtuml", false);

		// persist the tree from the component package
		Package_c cp = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("System Component Testing");
			}
		
		});
		assertNotNull(cp);
		cp.getPersistableComponent().persistSelfAndChildren();
		String contents = concatenateModelFiles(cp.getPersistableComponent(), "");
		if(generateResults) {
			FileOutputStream fos = new FileOutputStream(expectedResultsFile1);
			fos.write(contents.getBytes());
			fos.flush();
			fos.close();
			System.out.println("Wrote expected results: " + expectedResultsFile1.getAbsolutePath());
		} else {
			FileInputStream fis1 = new FileInputStream(expectedResultsFile1);
			byte[] bytes1 = new byte[(int) expectedResultsFile1.length()];
			fis1.read(bytes1);
			fis1.close();
			String expectedResults1 = new String(bytes1);
			expectedResults1=expectedResults1.replaceAll("\r", "");
			
			FileInputStream fis2 = new FileInputStream(expectedResultsFile2);
			byte[] bytes2 = new byte[(int) expectedResultsFile2.length()];
			fis2.read(bytes2);
			fis2.close();
			String expectedResults2 = new String(bytes2);
			expectedResults2=expectedResults2.replaceAll("\r", "");
			
			if(expectedResults1.equals(contents))
              assertEquals("Expected results differ.", expectedResults1, contents);
            else if (expectedResults2.equals(contents))
              assertEquals("Expected results differ.", expectedResults2, contents);
            else 
             assertEquals(expectedResults2.length(), contents.length());
		}
	}

	private String concatenateModelFiles(PersistableModelComponent root, String contents) throws CoreException, IOException {
		IFile file = root.getFile();
		InputStream is = file.getContents();
		byte[] bytes = new byte[(int) file.getLocation().toFile().length()];
		is.read(bytes);
		is.close();
		contents = contents + "\n" + new String(bytes);
		Collection children = root.getChildren();
		Iterator iterator = children.iterator();
		while(iterator.hasNext()) {
			PersistableModelComponent child = (PersistableModelComponent) iterator.next();
			contents = concatenateModelFiles(child, contents);
		}
		return contents;
	}
	
}
