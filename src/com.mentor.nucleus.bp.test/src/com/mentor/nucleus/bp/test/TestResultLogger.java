//========================================================================
//
//File:      $RCSfile: TestResultLogger.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/03/13 22:18:53 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
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
		static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		

		public static void writeSummary(ITestRunSession currentSession) {
			File resultFolder = TestResultLogger.createTestResultFolder();
			File reportFile = new File(resultFolder.getPath() + "/"
					+ "Unit_test_summary.txt");
			FileWriter writer;
			String report = getHeader();
			report += getTestRunResultsFromHistory();
			report += getDataFromTestSession((TestRunSession)currentSession, sdf.format(System.currentTimeMillis()));
			
			try {
				writer = new FileWriter(reportFile);
				writer.write(report);
				writer.close();
			} catch (IOException e) {
				CorePlugin.logError(
						"Unable to save test run summary.", e);
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

		private static String getTestRunResultsFromHistory() {
			String report = "";
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
					importTestRunSession = (TestRunSession) org.eclipse.jdt.junit.JUnitCore
							.importTestRunSession(file);
				} catch (CoreException e) {
					CorePlugin.logError("Unable to import the test run summary: " + file.getName(), e);
				}
				if (importTestRunSession!=null && !importTestRunSession.getTestRunName().equals(
						"CaptureTestResults")) {
					report += getDataFromTestSession(importTestRunSession,
							sdf.format(file.lastModified()));
				}
			}
			return report;
		}
		
		private static String getDataFromTestSession(TestRunSession session,
				String testRunTimeStamp) {
			// test name
			String testInfo = createFormattedString(40,
					session.getTestRunName());

			Result testResult = session.getTestResult(true);
			if (testResult != ITestElement.Result.OK) {
				testInfo += createFormattedString(16, "FAIL");
			} else {
				testInfo += createFormattedString(16, "PASS");
			}

			// Test Count
			testInfo += createFormattedString(12,
					String.valueOf(session.getTotalCount()));

			// Test time
			ITestElement[] children = session.getChildren();
			double time = children[0].getElapsedTimeInSeconds();
			DecimalFormat decFormat = new DecimalFormat("#.00");
			String timeString = decFormat.format(time) + "s";
			testInfo += createFormattedString(8, timeString);

			// Date
			testInfo += testRunTimeStamp;

			testInfo += "\n";

			return testInfo;
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
	}
}
