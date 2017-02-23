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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ComponentResourceListener;
import org.xtuml.bp.core.common.InstanceList;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.UpgradeJob;
import org.xtuml.bp.core.ui.IModelImport;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.io.core.CoreImport;
import org.xtuml.bp.io.core.ImportHelper;
import org.xtuml.bp.ui.canvas.Diagram_c;
import org.xtuml.bp.ui.canvas.Gr_c;
import org.xtuml.bp.ui.canvas.Graphelement_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.GraphicsReconcilerLauncher;
import org.xtuml.bp.ui.canvas.Graphnode_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Shape_c;
/**
 * This wizard imports model data from a system level export file, or from on
 * older BridgePoint single file model
 */
public class ModelImportWizard extends Wizard implements IImportWizard {

	public ModelImportWizard() {
		super();
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
				IPath sourceFileDirectory = templatePath.removeLastSegments(1);
				ImportStreamStatus iss = new ImportStreamStatus(inStream);
				if (getContainer() == null || Ooaofooa.inUnitTest()) {
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

            // resolve component references and formalize interfaces in MASL projects
            ImportHelper helper = new ImportHelper((CoreImport)fImporter);
            helper.resolveMASLproject( fImporter.getLoadedInstances() );
            
			if (helper.maslModelWasImported()) {
				// Reconcile graphics.
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
					public void run() {
						List<NonRootModelElement> systems = new ArrayList<NonRootModelElement>();
						systems.add(fSystem);
						GraphicsReconcilerLauncher reconciler = new GraphicsReconcilerLauncher(systems);
						reconciler.runReconciler(false, true);
					}

				});				
			}
            
		}
		return true;
	}

	private class ImportStreamStatus implements IRunnableWithProgress {
		InputStream fInStream;

		String fMessage;

		public ImportStreamStatus(InputStream inStream) {
			fInStream = inStream;
		}

		public void run(final IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			try {
				fProcessor.setDestinationElement(fSystem);
				// otherwise read the file contents into a string and
				// pass into a byte array stream importer
				FileInputStream fis = new FileInputStream(fInputFile);
				byte[] fileBytes = new byte[Long.valueOf(fInputFile.length())
						.intValue()];
				fis.read(fileBytes);
				fis.close();
				String contents = new String(fileBytes);
				fProcessor.setContents(contents);
				ByteArrayInputStream is = new ByteArrayInputStream(fileBytes);
				Ooaofooa.getInstance(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME)
						.clearDatabase(monitor);
				fImporter = CorePlugin.getStreamImportFactory().create(
						is,
						Ooaofooa.getInstance(
								Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME, false),
						true, fSystem.getPersistableComponent().getFile().getFullPath());
				  fProcessor.runImporter(fImporter, monitor);

				fProcessor.processFirstStep(monitor);
				handleImportedGraphicalElements();
				fProcessor.processSecondStep(monitor);
				IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
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
				persistImportedElements(monitor);
				fMessage = fImporter.getErrorMessage();
				String fileFormatVersion = fImporter.getHeader()
						.getFileFormatVersion();
				if (PersistenceManager.requiresUpgradeBeforeUse(
						fileFormatVersion, "ModelClass")) {
					PersistenceManager.markSystemFileForUpgrade(fSystem.getPersistableComponent());
					UpgradeJob job = new UpgradeJob("Disable system",
							fSystem.getPersistableComponent());
					job.setRule(ResourcesPlugin.getWorkspace().getRoot());
					job.schedule();
				}
				
			} catch (IOException e) {
				org.xtuml.bp.io.core.CorePlugin.logError(
						"There was an exception loading the give source file.",
						e);
			}
		}

		String getMessage() {
			return fMessage;
		}
	}

	private GraphicalElement_c getGraphicalElementFor(NonRootModelElement element) {
		GraphicalElement_c[] elements = GraphicalElement_c
				.GraphicalElementInstances(Ooaofgraphics
						.getInstance(Ooaofgraphics.CLIPBOARD_MODEL_ROOT_NAME));
		for(int i = 0; i < elements.length; i++) {
			if(elements[i].getRepresents() == element) {
				return elements[i];
			}
		}
		return null;
	}

	public void persistImportedElements(IProgressMonitor monitor) {
		try {
			ComponentResourceListener.setIgnoreResourceChanges(true);
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

				public void run(IProgressMonitor monitor) throws CoreException {
					NonRootModelElement[] elementsToBeImported = fProcessor
							.getCachedExportedElements();
					monitor.beginTask("Persisting imported elements...",
							elementsToBeImported.length);
					for (int i = 0; i < elementsToBeImported.length; i++) {
						PersistableModelComponent component = elementsToBeImported[i]
								.getPersistableComponent();
						try {
							component.persistSelfAndChildren();
							monitor.worked(1);
						} catch (CoreException e) {

						}
					}
					fSystem.getPersistableComponent().persist();
				}

			}, monitor);
		} catch (CoreException e) {
			org.xtuml.bp.io.core.CorePlugin.logError(
					"Unable to persist imported elements.", e);
		}
	}

	public void handleImportedGraphicalElements() {
		Model_c systemModel = getSystemModel();
		GraphicalElement_c[] rootImportedElements = getRootImportedGraphicalElements();
		for (int i = 0; i < rootImportedElements.length; i++) {
			// first move the imported GD_GE to the destination
			// GD_MD
			Model_c prevModel = Model_c
					.getOneGD_MDOnR1(rootImportedElements[i]);
			if (prevModel != null) {
				prevModel.unrelateAcrossR1From(rootImportedElements[i]);
			}
			systemModel.relateAcrossR1To(rootImportedElements[i]);
			// now guarantee that it's placement will not overlap any
			// other GD_GE
			updateGraphicalPlacement(systemModel, rootImportedElements[i]);
		}
		updateGraphicalElementRoots(systemModel);
	}

	public void updateGraphicalPlacement(Model_c systemModel,
			GraphicalElement_c importedElement) {
		ModelRoot.disableChangeNotification();
		boolean isOver = doesElementOverlapExisting(systemModel, importedElement);
		while(isOver) {
			Graphelement_c elem = Graphelement_c.getOneDIM_GEOnR23(importedElement);
			elem.setPositionx(elem.getPositionx()
					+ Ooaofgraphics.Getgridsnapincrement(systemModel
							.getModelRoot(), false));
			isOver = doesElementOverlapExisting(systemModel, importedElement);
		}
		ModelRoot.enableChangeNotification();
	}

	private boolean doesElementOverlapExisting(Model_c systemModel, GraphicalElement_c importedElement) {
		boolean overlaps = false;
		Diagram_c diagram = Diagram_c.getOneDIM_DIAOnR18(systemModel);
		Graphelement_c elem = Graphelement_c.getOneDIM_GEOnR23(importedElement);
		Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(Shape_c.getOneGD_SHPOnR2(importedElement));
		GraphicalElement_c[] elements = GraphicalElement_c.getManyGD_GEsOnR1(systemModel);
		for(int i = 0; i < elements.length; i++) {
			// skip the imported element
			if(elements[i] == importedElement) continue;
			// first check to see if any of the points above
			// are over this element
			// first try the imported elements nw corner
			overlaps = elements[i].Isover(Gr_c.Scale((int) (elem
					.getPositionx() - diagram.getViewportx())),
					Gr_c.Scale((int) (elem.getPositiony() - diagram
							.getViewporty())));
			if(!overlaps) {
				// now try the sw corner
				overlaps = elements[i].Isover(Gr_c.Scale((int) (elem
						.getPositionx() - diagram.getViewportx())), Gr_c
						.Scale((int) (elem.getPositiony()
								- diagram.getViewporty() + node.getHeight())));
				if(!overlaps) {
					// and now the ne corner
					overlaps = elements[i].Isover(Gr_c.Scale((int) (elem
							.getPositionx()
							- diagram.getViewportx() + node.getWidth())), Gr_c
							.Scale((int) (elem.getPositiony() - diagram
									.getViewporty())));
					if(!overlaps) {
						// and finally the se corner
						overlaps = elements[i].Isover(Gr_c.Scale((int) (elem
								.getPositionx()
								- diagram.getViewportx() + node.getWidth())),
								Gr_c.Scale((int) (elem.getPositiony()
										- diagram.getViewporty() + node
										.getHeight())));
					}
				}
			}
			// now switch the coordinates
			if(!overlaps) {
				Graphelement_c thisElem = Graphelement_c.getOneDIM_GEOnR23(elements[i]);
				Graphnode_c thisNode = Graphnode_c.getOneDIM_NDOnR19(Shape_c.getOneGD_SHPOnR2(elements[i]));
				// first try the elements nw corner
				overlaps = importedElement.Isover(Gr_c.Scale((int) (thisElem
						.getPositionx() - diagram.getViewportx())),
						Gr_c.Scale((int) (thisElem.getPositiony() - diagram
								.getViewporty())));
				if(!overlaps) {
					// now try the sw corner
					overlaps = importedElement.Isover(Gr_c.Scale((int) (thisElem
							.getPositionx() - diagram.getViewportx())), Gr_c
							.Scale((int) (thisElem.getPositiony()
									- diagram.getViewporty() + thisNode.getHeight())));
					if(!overlaps) {
						// and now the ne corner
						overlaps = importedElement.Isover(Gr_c.Scale((int) (thisElem
								.getPositionx()
								- diagram.getViewportx() + thisNode.getWidth())), Gr_c
								.Scale((int) (thisElem.getPositiony() - diagram
										.getViewporty())));
						if(!overlaps) {
							// and finally the se corner
							overlaps = importedElement.Isover(Gr_c.Scale((int) (thisElem
									.getPositionx()
									- diagram.getViewportx() + thisNode.getWidth())),
									Gr_c.Scale((int) (thisElem.getPositiony()
											- diagram.getViewporty() + thisNode
											.getHeight())));
						}
					}
				}
			}
			if(overlaps)
				break;
		}
		return overlaps;
	}

	private Model_c getSystemModel() {
		Model_c model = Model_c.ModelInstance(Ooaofgraphics
				.getDefaultInstance(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Model_c) candidate).getOoa_id().equals(
						fSystem.Get_ooa_id());
			}

		});
		return model;
	}

	private GraphicalElement_c[] getRootImportedGraphicalElements() {
		ArrayList<GraphicalElement_c> list = new ArrayList<GraphicalElement_c>();
		GraphicalElement_c[] pastedElems = GraphicalElement_c
				.GraphicalElementInstances(Ooaofgraphics
						.getInstance(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME));
		for (int i = 0; i < pastedElems.length; i++) {
			if (pastedElems[i].getRepresents() != null)
				if (fProcessor
						.isTypePartOfExport((NonRootModelElement) pastedElems[i]
								.getRepresents())) {
					list.add(pastedElems[i]);
				}
		}
		return list.toArray(new GraphicalElement_c[list.size()]);
	}

	private void updateGraphicalElementRoots(Model_c model) {
		NonRootModelElement[] elements = fProcessor.getImporter().getLoadedGraphicalInstances();
		for (int i = 0; i < elements.length; i++) {
			InstanceList list = elements[i].getModelRoot().getInstanceList(
					elements[i].getClass());
			synchronized (list) {
				list.remove(elements[i]);
			}
			InstanceList parentList = model.getModelRoot().getInstanceList(
					elements[i].getClass());
			synchronized (list) {
				parentList.add(elements[i]);
			}
			parentList.put(elements[i].getInstanceKey(), elements[i]);
			elements[i].setModelRoot(model.getModelRoot());
		}
	}
}
