package org.xtuml.bp.io.mdl.test;

//=====================================================================
//
//File:      $RCSfile: ParseOnImportTestsGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 05:13:52 $
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

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.ui.NewDomainWizard;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.WizardNewDomainCreationPage;
import org.xtuml.bp.core.ui.marker.DelayedMarkerJob;
import org.xtuml.bp.io.mdl.wizards.ModelImportPage;
import org.xtuml.bp.io.mdl.wizards.ModelImportWizard;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.text.activity.ParseAllActivitiesAction;

public class ParseOnImportTestsGenerics extends BaseTest {
	private static String testProjectName = "ParseOnImportTest";
	public File testModel;
	public static boolean generateResults = false;
	public int modelNumber = 0;

	public ParseOnImportTestsGenerics(String name) {
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
			if(!BaseTest.testGlobals)
			fail( ".log file is not empty after imnporting " + testModel.getName()); //$NON-NLS-1$
		}
		
        // Parse All
        ParseAllActivitiesAction paaa = new ParseAllActivitiesAction();
        paaa.run(null);

		if ( in_fh.exists() )
		{
			if(!BaseTest.testGlobals)
			fail( ".log file is not empty following a parse all, after import, of " + testModel.getName()); //$NON-NLS-1$
		}
		DelayedMarkerJob.enableProblemMarkerJob();
	}
	
	private void importModel() {

		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(m_sys);

		ModelImportWizard importWizard = new ModelImportWizard();
		importWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
				.getStructuredSelection());
		
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), importWizard);
		dialog.create();
		ModelImportPage importPage = (ModelImportPage) importWizard
				.getStartingPage();
		importPage.setSourceField(testModel.getAbsolutePath()+testModel.getName());
		importPage.setParseOnImport(true);
	    importWizard.performFinish();
	
		
	}
}
