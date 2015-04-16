//========================================================================
//
//File:      org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/ModelIntegrityTests.java
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package org.xtuml.bp.core.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.IntegrityIssue_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IntegrityChecker;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;

public class ModelIntegrityTests extends BaseTest {

	private boolean generateResults = false;
	private String test_id;
	private IntegrityManager_c manager;	

	@Override
	protected void initialSetup() throws Exception {
		loadProject("ModelIntegrityTests");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		SystemModel_c system = SystemModel_c.getOneS_SYSOnR1301(manager);
		if(system != null) {
			system.unrelateAcrossR1301From(manager);
		}
		IntegrityIssue_c[] relatedIssues = IntegrityIssue_c.getManyMI_IIsOnR1300(manager);
		for(IntegrityIssue_c issue : relatedIssues) {
			issue.Dispose();
		}
		manager.delete();
	}

	public void testAssociationIntegrityChecks() throws IOException {
		test_id = "Association_Integrity"; 
		Package_c pkg = getPackage("Association Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testInterfaceOperationIntegrityChecks() throws IOException {
		test_id = "Interface_Operation_Integrity";
		Package_c pkg = getPackage("Interface Operation Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testInterfaceSignalIntegrityChecks() throws IOException {
		test_id = "Interface_Signal_Integrity";
		Package_c pkg = getPackage("Interface Signal Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}
	
	public void testPortIntegrityChecks() throws IOException {
		test_id = "Port_Integrity";
		Package_c pkg = getPackage("Port Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testAttributeIntegrityChecks() throws IOException {
		test_id = "Attribute_Integrity";
		Package_c pkg = getPackage("Attribute Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testBridgeParameterIntegrityChecks() throws IOException {
		test_id = "Bridge_Parameter_Integrity";
		Package_c pkg = getPackage("Bridge Parameter Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testBridgeIntegrityChecks() throws IOException {
		test_id = "Bridge_Integrity";
		Package_c pkg = getPackage("Bridge Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}
	
	public void testClassIntegrityChecks() throws IOException {
		test_id = "Class_Integrity";
		Package_c pkg = getPackage("Class Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testEnumerationDataTypeIntegrityChecks() throws IOException {
		test_id = "Enumeration_Data_Type_Integrity";
		Package_c pkg = getPackage("Enumeration Data Type Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testEnumeratorIntegrityChecks() throws IOException {
		test_id = "Enumerator_Integrity";
		Package_c pkg = getPackage("Enumerator Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testFunctionParameterIntegrityChecks() throws IOException {
		test_id = "Function_Parameter_Integrity";
		Package_c pkg = getPackage("Function Parameter Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}
	
	public void testFunctionIntegrityChecks() throws IOException {
		test_id = "Function_Integrity";
		Package_c pkg = getPackage("Function Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testOperationParameterIntegrityChecks() throws IOException {
		test_id = "Operation_Parameter_Integrity";
		Package_c pkg = getPackage("Operation Parameter Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testOperationIntegrityChecks() throws IOException {
		test_id = "Operation_Integrity";
		Package_c pkg = getPackage("Operation Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testPropertyParameterIntegrityChecks() throws IOException {
		test_id = "Property_Parameter_Integrity";
		Package_c pkg = getPackage("Property Parameter Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testStateMachineEventDataItemIntegrityChecks() throws IOException {
		test_id = "State_Machine_Event_Data_Item_Integrity";
		Package_c pkg = getPackage("State Machine Event Data Item Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testStateMachineEventIntegrityChecks() throws IOException {
		test_id = "State_Machine_Event_Integrity";
		Package_c pkg = getPackage("State Machine Event Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testStateMachineStateIntegrityChecks() throws IOException {
		test_id = "State_Machine_State_Integrity";
		Package_c pkg = getPackage("State Machine State Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testStructureMemberIntegrityChecks() throws IOException {
		test_id = "Structure_Member_Integrity";
		Package_c pkg = getPackage("Structure Member Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testStructuredDataTypeIntegrityChecks() throws IOException {
		test_id = "Structured_Data_Type_Integrity";
		Package_c pkg = getPackage("Structured Data Type Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testSymbolicConstantIntegrityChecks() throws IOException {
		test_id = "Symbolic_Constant_Integrity";
		Package_c pkg = getPackage("Symbolic Constant Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testUserDataTypeIntegrityChecks() throws IOException {
		test_id = "User_Data_Type_Integrity";
		Package_c pkg = getPackage("User Data Type Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}

	public void testDanglingReferenceIntegrityChecks() throws IOException {
		test_id = "Dangling_Reference_Integrity";
		Package_c pkg = getPackage("Dangling Reference Tests");
		String report = runIntegrityReportForElement(pkg);
		if(generateResults) {
			writeExpectedResults(report);
		} else {
			assertEquals(getExpectedResults(), report);
		}
	}
	
	private String getExpectedResults() throws IOException {
		String path = m_workspace_path + TestingUtilities.getExpectedResultsPath() + test_id + "/"
				+ test_id + ".txt";
		File file = new File(path);
		char[] chars = new char[(int) file.length()];
		FileReader reader = new FileReader(new File(path));
		reader.read(chars);
		return new String(chars);
	}

	private void writeExpectedResults(String report) throws IOException {
		String path = m_workspace_path + TestingUtilities.getExpectedResultsPath() + test_id + "/";
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		File file = new File(path + test_id + ".txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		writer.write(report);
		writer.close();
	}

	private String runIntegrityReportForElement(final Package_c pkg) {
		// the integrity checker no longer loads when locating
		// children, therefore this test must handle the loading
		pkg.getPersistableComponent().loadComponentAndChildren(
				new NullProgressMonitor());
		final List<IntegrityIssue_c> issues = new ArrayList<IntegrityIssue_c>();
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					manager = new IntegrityManager_c(Ooaofooa.getDefaultInstance());
					issues.addAll(Arrays.asList(IntegrityChecker
							.runIntegrityCheck(pkg, manager)));
				}
			}, new NullProgressMonitor());
		} catch (CoreException e) {
			CorePlugin.logError("Unable to create integrity issue report.", e);
		}
		return IntegrityChecker.createReportForIssues(issues
				.toArray(new IntegrityIssue_c[issues.size()]));
	}

	private Package_c getPackage(final String string) {
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals(string);
			}
		});
		return pkg;
	}
	
}