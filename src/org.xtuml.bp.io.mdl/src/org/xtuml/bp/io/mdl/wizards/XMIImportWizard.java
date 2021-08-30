//========================================================================
//
// File: XMIImportWizard.java
//
// This work is licensed under the Creative Commons CC0 License
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
package org.xtuml.bp.io.mdl.wizards;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.SAXException;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.ui.IModelImport;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.actions.PublishSynchronizationChanges;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.io.xmi.translate.ILogger;
import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.ui.canvas.GraphicsReconcilerLauncher;

import com.sdmetrics.util.XMLParser.XMLParseException;

/**
 * This wizard imports model data from an external XMI source. Currently EA is
 * supported, other XMI milegae may vary.
 */
public class XMIImportWizard extends Wizard implements IImportWizard {

	class NullLogger implements ILogger {

		@Override
		public void log(String arg0) {
		}

	}

	public XMIImportWizard() {
		importHelper = new ModelImportWizardHelper();
	}

	private String xtumlContent = "";

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
		// Run xmi.translate here
		XMITranslate translate = new XMITranslate(new NullLogger());
		try {
			xtumlContent = translate.loadXMI(fInputFile.getAbsolutePath());
		} catch (ParserConfigurationException | SAXException | XMLParseException | IOException e) {
			CorePlugin.logError("Unable to load XMI file", e);
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
			org.xtuml.bp.io.core.CorePlugin.logError("Unable to run import operation.", e);
		} catch (InterruptedException e) {
			org.xtuml.bp.io.core.CorePlugin.logError("Unable to run import operation.", e);
		}
		// XMI Translation does not create all elements, it could but
		// since this functionality is already supported we call these
		// now
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(fSystem);
		GraphicsReconcilerLauncher reconciler = new GraphicsReconcilerLauncher(
				List.of(Selection.getInstance().getSelectedNonRootModelElements()));
		reconciler.runReconciler(false, true);
		// disable report
		boolean showReport = CorePlugin.getDefault().getPreferenceStore()
				.getBoolean(BridgePointPreferencesStore.SHOW_SYNC_REPORT);
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.SHOW_SYNC_REPORT, false);
		PublishSynchronizationChanges syncInterface = new PublishSynchronizationChanges();
		syncInterface.disableNoReferenceDialog();
		syncInterface.selectionChanged(null, Selection.getInstance().getSelection());
		syncInterface.run(null);
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.SHOW_SYNC_REPORT,
				showReport);
		UIUtil.refresh(null);
		Selection.getInstance().setSelection(fPreviousSelection);
		if (fImporter == null) {
			fImportPage.setErrorMessage("Internal system error: Unable to load model.");
		}
		if (fImporter.getErrorMessage().equals("")) {
			return true;
		} else {
			// log the error message here
			org.xtuml.bp.io.core.CorePlugin.logError(fImporter.getErrorMessage(), null);
			return false;
		}
	}

	IStructuredSelection fPreviousSelection = null;

	private XMIImportPage fImportPage;

	public File fInputFile;

	private SystemModel_c fSystem;

	private ModelImportWizardHelper importHelper;

	public IModelImport fImporter;

	private ModelStreamProcessor fProcessor = new ModelStreamProcessor();

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		fPreviousSelection = Selection.getInstance().getStructuredSelection();
		setWindowTitle("Import");
		setDefaultPageImageDescriptor(CorePlugin.getImageDescriptor("importsql_wiz.gif")); //$NON-NLS-1$
	}

	public void addPages() {
		fImportPage = new XMIImportPage("XMI Model", Selection.getInstance().getStructuredSelection());
		addPage(fImportPage);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#getDialogSettings()
	 */
	public IDialogSettings getDialogSettings() {
		return org.xtuml.bp.io.core.CorePlugin.getDefault().getDialogSettings();
	}

	public boolean importModel(SystemModel_c system) {
		fSystem = system;
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());

		try {
			// turn off model change listeners
			ModelRoot.disableChangeNotification();
			ImportStreamStatus iss = new ImportStreamStatus();
			if (getContainer() == null) {
				// for unit tests to prevent displaying progress dialogs
				iss.run(new NullProgressMonitor());
			} else {
				dialog.run(true, false, iss);
			}
		} catch (InterruptedException e) {
			org.xtuml.bp.io.core.CorePlugin.logError("Internal error: import was interrupted", e); //$NON-NLS-1$
			return false;
		} catch (InvocationTargetException e) {
			org.xtuml.bp.io.core.CorePlugin.logError("Internal error: plugin.doLoad not found", e); //$NON-NLS-1$
			return false;
		} finally {
			ModelRoot.enableChangeNotification();
		}

		return true;
	}

	private class ImportStreamStatus implements IRunnableWithProgress {
		String fMessage;

		public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				fImporter = importHelper.doImportPhase1(fProcessor, fSystem, xtumlContent.getBytes(), monitor);
				if (fImportPage.parseOnImport()) {
					// this must be run on the display thread
					PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

						public void run() {
							fProcessor.runParseOnImportedElements(fSystem.getTransactionManager(), monitor);
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
				org.xtuml.bp.io.core.CorePlugin.logError("There was an exception loading the give source file.", e);
			}
		}

	}

}
