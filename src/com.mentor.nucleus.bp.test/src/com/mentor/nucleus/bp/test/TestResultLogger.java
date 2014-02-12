//========================================================================
//
//File:      $RCSfile: TestResultLogger.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/03/13 22:18:53 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.junit.model.TestRunHandler;
import org.eclipse.jdt.internal.junit.model.TestRunSession;
import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 * Saves the test session into the standard XML format
 * 
 */
public class TestResultLogger extends TestRunListener {
	private final static String Folder_Name = "test_results";
	@Override
	public void sessionFinished(ITestRunSession session) {
		writeResults(session);
	}

	/**
	 * Writes the session into an XML representation of the run.
	 */
	protected void writeResults(ITestRunSession session) {
		createTestResultFolder();
		String fileName = Folder_Name + "/" + session.getTestRunName() + ".xml";
		File file = new File(fileName);
		try {
			JUnitCore.exportTestRunSession(session, file);
		} catch (CoreException e) {
			CorePlugin.logError(
					"Unable to save test run session to XML format.", e);
		}
		CaptureTestSummary.writeSummary(session);
	}

	public static File createTestResultFolder() {
		// resolve the name of the first test performed during the run
		File folder = new File(Folder_Name);
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder;
	}
	
	public static class CaptureTestSummary extends TestCase {
		private static Map <String, String > ResultMap = new HashMap<String, String>();  // < Test name, data >
		
		
		/**
		 * This is only here is allow this to be tested in a normal unit test
		 * instead of as a listener. 
		 */
		public void testUnitTestStub() {
			writeSummary(null);
		}

		public static void writeSummary(ITestRunSession currentSession) {
			File resultFolder = TestResultLogger.createTestResultFolder();
			File reportFile = new File(resultFolder.getPath() + "/"
					+ "Unit_test_summary.txt");
			FileWriter writer;
			try {
				String report = getHeader();
				getTestRunResultsFromHistory();
				if (currentSession !=  null) {
					getDataFromTestSession((TestRunSession)currentSession, System.currentTimeMillis());
				}
				
				try {
					writer = new FileWriter(reportFile);
					writer.write(report);
					for (Map.Entry<String,String> entry : ResultMap.entrySet()) {
						writer.write(entry.getValue());
					}
					writer.close();
				} catch (Exception e) {
					CorePlugin.logError(
							"Unable to save test run summary.", e);
				}			
			} catch (Exception e) {
				CorePlugin.logError(
						"Unable to load the run summary data.", e);
			}
		}
		
		public static String getHeader() {


			String report = "";
			report += "This document captures the state of the BridgePoint unit tests:\n";
			report += "===============================================================\n";

			// The key for getting operating system name
			String name = "os.name";
			// The key for getting operating system version
			String version = "os.version";
			// The key for getting operating system architecture
			String architecture = "os.arch";
			report += "Branch: <TODO: Fill This In>\n";
			String hostName = "";
			try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (Throwable e) {
				hostName = "<Not Available>";
			}
			report += "Hostname: \t" + hostName + "\n";
			report += "OS Name:  \t" + System.getProperty(name) + "\n";
			report += "\tVersion:\t" + System.getProperty(version) + "\n";
			report += "\tArchitecture:\t" + System.getProperty(architecture)
					+ "\n";
			report += "\n";

			report += "Test Suite                              Status			TestRuns	Time    Timestamp\n";
			report += "---------------------------------------------------------------------------------------------\n";

			return report;
		}

		private static void getTestRunResultsFromHistory() {
			// Get the current test run history from metadata
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			String workspaceDirectory = workspace.getRoot().getLocation()
					.toFile().getPath();
			File historyDirectory = new File(workspace.getRoot().getLocation()
					.toFile().getPath()
					+ "/.metadata/.plugins/org.eclipse.jdt.junit.core/history");

			// iterate over the unit test history and get the data from the
			// prior runs
			File[] listFiles = historyDirectory.listFiles();
			for (File file : listFiles) {
				TestRunSession importTestRunSession = null;
				try {
					importTestRunSession = importTestRunSession(file);
				} catch (Exception e) {
					CorePlugin.logError("Unable to import the test run summary: " + file.getName(), e);
				}
				if (importTestRunSession!=null && !importTestRunSession.getTestRunName().equals(
						"CaptureTestSummary")) {
					getDataFromTestSession(importTestRunSession,
							file.lastModified());
				}
			}
		}
		
		private static void getDataFromTestSession(TestRunSession session,
				long testRunTimeStamp) {
			// test name
			String testInfo = createFormattedString(40,
					session.getTestRunName());

			Result testResult = session.getTestResult(true);
			if (testResult != ITestElement.Result.OK) {
				testInfo += createFormattedString(16, "FAIL (" + String.valueOf(session.getErrorCount()) + "e/" + String.valueOf(session.getFailureCount()) + "f)");
			} else {
				testInfo += createFormattedString(16, "PASS");
			}

			// Test Count
			testInfo += createFormattedString(12,
					String.valueOf(session.getTotalCount()));

			// Test time
			ITestElement[] children = session.getChildren();
			double time = children[0].getElapsedTimeInSeconds();
			DecimalFormat decFormat = new DecimalFormat("0.00");
			String timeString = decFormat.format(time) + "s";
			testInfo += createFormattedString(8, timeString);

			// Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			testInfo += sdf.format(testRunTimeStamp);

			testInfo += "\n";
			
			ResultMap.put(session.getTestRunName(), testInfo);
		}

		/**
		 * Return a string that contains the data padded with the correct number
		 * of tabs to make the formatting look nice.
		 * 
		 * @param templateLength
		 * @param data
		 * @return formatted string
		 */
		private static String createFormattedString(int templateLength, String data) {
			final int TabLength = 4;
			int actualFieldLength = data.length();
			int numTabsNeeded = (templateLength - actualFieldLength)
					/ TabLength;
			String result = data;
			if (((templateLength - actualFieldLength) % TabLength > 0)) {
				numTabsNeeded++;
			}
			for (int i = 0; i < numTabsNeeded; i++) {
				result += "\t";
			}
			return result;
		}
		
		
		/**
		 * This code was taken from org.eclipse.jdt.junit.JUnitCore and
		 * modified so that the imported session is not added to the list of
		 * sessions maintained in the junit test history.
		 *  
		 * @param file
		 * @return
		 * @throws Exception
		 */
		public static TestRunSession importTestRunSession(File file) throws Exception {
			   SAXParserFactory parserFactory= SAXParserFactory.newInstance();
			//   parserFactory.setValidating(true); // TODO: add DTD and debug flag
			   SAXParser parser= parserFactory.newSAXParser();
			   TestRunHandler handler= new TestRunHandler();
			   parser.parse(file, handler);
			   TestRunSession session= handler.getTestRunSession();
			   return session;
		 }
	}
}
