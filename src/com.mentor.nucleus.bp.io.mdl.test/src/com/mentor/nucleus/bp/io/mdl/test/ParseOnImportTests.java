package com.mentor.nucleus.bp.io.mdl.test;

//=====================================================================
//
//File:      $RCSfile: ParseOnImportTests.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:12:53 $
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
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.ui.NewDomainWizard;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.WizardNewDomainCreationPage;
import com.mentor.nucleus.bp.core.ui.marker.DelayedMarkerJob;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.text.activity.ParseAllActivitiesAction;

public class ParseOnImportTests extends BaseTest {
	private static String testProjectName = "ParseOnImportTest";
	public File testModel;
	public static boolean generateResults = false;
	public int modelNumber = 0;

	public ParseOnImportTests(String name) {
		super(testProjectName, name);	
	}

	public void setUp() throws Exception {
		super.setUp();

		CorePlugin.disableParseAllOnResourceChange();
		m_sys = getSystemModel(testProjectName);
		Ooaofooa.setPersistEnabled(true);
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testParseOnImport() {
		
		String initMessage = "Running " + getName()  + "  with " + testModel.getName(); 		
		System.out.println(initMessage);
		DelayedMarkerJob.disableProblemMarkerJob();
		// import the model being tested
		importModel();
		
		File in_fh = Platform.getLogFileLocation().toFile();
		if ( in_fh.exists() )
		{
			if (!BaseTest.testGlobals)
			fail( ".log file is not empty after imnporting " + testModel.getName()); //$NON-NLS-1$
		}
		
        // Parse All
        ParseAllActivitiesAction paaa = new ParseAllActivitiesAction();
        paaa.run(null);

		if ( in_fh.exists() )
		{
			if (!BaseTest.testGlobals)
			fail( ".log file is not empty following a parse all, after import, of " + testModel.getName()); //$NON-NLS-1$
		}
		DelayedMarkerJob.enableProblemMarkerJob();
	}
	
	private void importModel() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
		
			public void run(IProgressMonitor monitor) throws InvocationTargetException,
					InterruptedException {
				monitor.beginTask("Loading test model: " + testModel.getName().replaceAll(".xtuml", ""), 1);
				NewDomainWizard ndw = new NewDomainWizard();
				Selection sel = Selection.getInstance();
				sel.clear();
				sel.addToSelection(m_sys);
				ndw.init(null, sel.getStructuredSelection());
				ndw.addPages();
				WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), ndw);
				dialog.create();
				WizardNewDomainCreationPage wndcp = (WizardNewDomainCreationPage) ndw
						.getStartingPage();
				wndcp.setDomainNameFieldValue(testModel.getName().replaceAll("." + Ooaofooa.MODELS_EXT, ""));
				wndcp.setTemplateLocationFieldValue(testModel.getAbsolutePath());
				wndcp.setParseOnImport(true);
				ndw.setContainer(null);
				ndw.performFinish();
				
				monitor.worked(1);
			}
		
		};
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		try {
			dialog.run(false, false, runnable);
		} catch (InvocationTargetException e) {
			fail("Unable to initialize test models.");
		} catch (InterruptedException e) {
			fail("Initialization of test models was interrupted.");
		}
	}
}
