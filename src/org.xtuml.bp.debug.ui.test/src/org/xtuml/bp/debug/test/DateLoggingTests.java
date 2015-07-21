package org.xtuml.bp.debug.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.debug.ui.launch.BPDebugUtils;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;

//=====================================================================
//
//File:      $RCSfile: DateLoggingTests.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2014/08/05 1:11:37 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//=====================================================================

public class DateLoggingTests extends BaseTest {

	private static IProject project;
	
	protected void setUp() throws Exception {
		super.setUp();
		loadProject("LogDateFormat");
	
	}

	public void testDateIsLoggedInTheCorrectFormat() {
		Package_c rootpkg = Package_c.getOneEP_PKGOnR1405(m_sys,
				new Package_by_name_c("root"));
		Component_c comp = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(rootpkg),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Container");
					}

				});

		Package_c pkg = Package_c.getOneEP_PKGOnR1405(m_sys,
				new Package_by_name_c("leaf"));
		Function_c fn = Function_c.getOneS_SYNCOnR8001(PackageableElement_c
				.getOnePE_PEOnR8000(pkg));
		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);
		Selection.getInstance().setSelection(new StructuredSelection(comp));

		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for a component.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		TestingUtilities.processDisplayEvents();
		

		openPerspectiveAndView(
				"org.xtuml.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(fn);
		BPDebugUtils.executeElement(fn);

		BPDebugUtils.openSessionExplorerView(true);
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		String actualResults = DebugUITestUtilities.getConsoleText();
		String dateAndMessage = actualResults.substring(37);
		String date = dateAndMessage.substring(0, dateAndMessage.length()-10);
		assertTrue(isValidFormat(date));
		 

		 
		

	} 
	
	 public static boolean isValidFormat(String value) {
	        Date date = null;
	        try {
	            date = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz").parse(value);
	        } catch (ParseException ex) {
	            ex.printStackTrace();
	        }
	        return date != null;
	    }
}