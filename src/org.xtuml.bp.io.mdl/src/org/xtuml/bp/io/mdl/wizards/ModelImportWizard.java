//=====================================================================
//
//File:      $RCSfile: ModelImportWizard.java,v $
//Version:   $Revision: 1.30 $
//Modified:  $Date: 2013/05/10 05:12:22 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package org.xtuml.bp.io.mdl.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.ui.IModelImport;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.UIUtil;
/**
 * This wizard imports model data from a system level export file, or from on
 * older BridgePoint single file model
 */
public class ModelImportWizard extends Wizard implements IImportWizard {

	public ModelImportWizard() {
		super();
		importHelper = new ModelImportWizardHelper();
	}

	
	@Override
	public boolean performFinish() {
		if (fImportPage.getSourceFilePath() != null) {
			fInputFile = new File(fImportPage.getSourceFilePath());
			if (!fInputFile.exists()) {
				return false;
			}
		} else {
			return false;
		}
		try {
			getContainer().run(false, false, new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) {
					Object[] checkedSystems = fImportPage.getSelectedSystems();
					for (int i = 0; i < checkedSystems.length; i++) {
						importModel((SystemModel_c) checkedSystems[i]);
					}
				}

			});
		} catch (InvocationTargetException e) {
			org.xtuml.bp.io.core.CorePlugin.logError(
					"Unable to run import operation.", e);
		} catch (InterruptedException e) {
			org.xtuml.bp.io.core.CorePlugin.logError(
					"Unable to run import operation.", e);
		}
		UIUtil.refresh(null);
		Selection.getInstance().setSelection(fPreviousSelection);
		if (fImporter.getErrorMessage().equals(""))
			return true;
		else
			return false;
	}

	IStructuredSelection fPreviousSelection = null;

	private ModelImportPage fImportPage;

	public File fInputFile;

	public IModelImport fImporter;

	private ModelStreamProcessor fProcessor = new ModelStreamProcessor();

	private SystemModel_c fSystem;
	
	private ModelImportWizardHelper importHelper;

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		fPreviousSelection = Selection.getInstance().getStructuredSelection();
		setWindowTitle("Import");
		setDefaultPageImageDescriptor(CorePlugin
				.getImageDescriptor("importsql_wiz.gif")); //$NON-NLS-1$
	}

	public void addPages() {
		fImportPage = new ModelImportPage("BridgePoint Model", Selection
				.getInstance().getStructuredSelection());
		addPage(fImportPage);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#getDialogSettings()
	 */
	public IDialogSettings getDialogSettings() {
		return org.xtuml.bp.io.core.CorePlugin.getDefault()
				.getDialogSettings();
	}

	public boolean importModel(SystemModel_c system) {
		fSystem = system;
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		String templateFileName = fImportPage.getSourceFilePath();
		final IPath templatePath = new Path(templateFileName);
		// import the domain file into the model root with the id of the full
		// path of the domain file
		if (templatePath.getFileExtension().equals(Ooaofooa.MODELS_EXT)) {
			final InputStream inStream;
			try {
				inStream = new FileInputStream(templatePath.toFile());
			} catch (FileNotFoundException e) {
				CorePlugin.logError("Internal error: failed to open "
						+ templateFileName, e);
				return false;
			}
			try {
				// turn off model change listeners
				ModelRoot.disableChangeNotification();
				ImportStreamStatus iss = new ImportStreamStatus(inStream);
				if (getContainer() == null) {
					// for unit tests to prevent displaying progress dialogs
					iss.run(new NullProgressMonitor());
				} else {
					dialog.run(true, false, iss);
				}
			} catch (InterruptedException e) {
				org.xtuml.bp.io.core.CorePlugin.logError(
						"Internal error: import was interrupted", e); //$NON-NLS-1$
				return false;
			} catch (InvocationTargetException e) {
				org.xtuml.bp.io.core.CorePlugin.logError(
						"Internal error: plugin.doLoad not found", e); //$NON-NLS-1$
				return false;
			} 
			finally {
				ModelRoot.enableChangeNotification();	
			}

			importHelper.doResolveMASL( fImporter, fSystem, true );
            
		}
		return true;
	}

	private class ImportStreamStatus implements IRunnableWithProgress {
		String fMessage;

		public ImportStreamStatus(InputStream inStream) {
		}

		public void run(final IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			try {
			    fImporter = importHelper.doImportPhase1(fProcessor, fSystem, fInputFile, monitor);
				if (fImportPage.parseOnImport()) {
					// this must be run on the display thread
					PlatformUI.getWorkbench().getDisplay().syncExec(
							new Runnable() {

								public void run() {
									fProcessor.runParseOnImportedElements(
											fSystem.getTransactionManager(),
											monitor);
								}

							});
				}
                // Make sure that all events in the asynchronous event queue
                // are dispatched.
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                    public void run() {
                        // do nothing
                    }
                });

				// . . . . before trying to force persistence.
				// since this process is not contained within a
				// transaction we must force persistence of the
				// imported elements
			    importHelper.doImportPhase2(fProcessor, fSystem, monitor, fMessage, fImporter);

			} catch (IOException e) {
				org.xtuml.bp.io.core.CorePlugin.logError(
						"There was an exception loading the give source file.",
						e);
			}
		}

	}
	
}
