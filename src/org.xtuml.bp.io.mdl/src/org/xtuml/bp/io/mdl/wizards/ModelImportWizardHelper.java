package org.xtuml.bp.io.mdl.wizards;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
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
import org.xtuml.bp.io.core.CoreImport;
import org.xtuml.bp.core.CorePlugin;
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

public class ModelImportWizardHelper {

    public void doResolveMASL(IModelImport fImporter, SystemModel_c fSystem, boolean reconcileGraphics) {
        // resolve component references and formalize interfaces in MASL projects
        ImportHelper helper = new ImportHelper((CoreImport)fImporter);
        helper.resolveMASLproject( fSystem, fImporter.getLoadedInstances() );
        
        if ( reconcileGraphics ) {
            if (helper.maslModelWasImported()) {
                // Reconcile graphics.
                List<NonRootModelElement> systems = new ArrayList<NonRootModelElement>();
                systems.add(fSystem);
                GraphicsReconcilerLauncher reconciler = new GraphicsReconcilerLauncher(systems);
                reconciler.runReconciler(false, true);
            }
        }
    }
    
    public IModelImport doImportPhase1(ModelStreamProcessor fProcessor, SystemModel_c fSystem, File fInputFile,
            IProgressMonitor monitor)
                    throws FileNotFoundException, IOException {
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
        IModelImport fImporter = CorePlugin.getStreamImportFactory().create(
                is,
                Ooaofooa.getInstance(
                        Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME, false),
                true, fSystem.getPersistableComponent().getFile().getFullPath());
          fProcessor.runImporter(fImporter, monitor);

        fProcessor.processFirstStep(monitor);
        handleImportedGraphicalElements(fSystem, fProcessor);
        fProcessor.processSecondStep(monitor);
        return fImporter;
    }

    public void handleImportedGraphicalElements(SystemModel_c fSystem, ModelStreamProcessor fProcessor) {
		Model_c systemModel = getSystemModel(fSystem);
		GraphicalElement_c[] rootImportedElements = getRootImportedGraphicalElements(fProcessor);
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
		updateGraphicalElementRoots(systemModel, fProcessor);
	}

    private Model_c getSystemModel(SystemModel_c fSystem) {
		Model_c model = Model_c.ModelInstance(Ooaofgraphics
				.getDefaultInstance(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Model_c) candidate).getOoa_id().equals(
						fSystem.Get_ooa_id());
			}

		});
		return model;
	}

	private GraphicalElement_c[] getRootImportedGraphicalElements(ModelStreamProcessor fProcessor) {
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

	private void updateGraphicalElementRoots(Model_c model, ModelStreamProcessor fProcessor) {
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

	public void persistImportedElements(IProgressMonitor monitor, ModelStreamProcessor fProcessor, SystemModel_c fSystem) {
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

    public void doImportPhase2(ModelStreamProcessor fProcessor, SystemModel_c fSystem, IProgressMonitor monitor, String fMessage, IModelImport fImporter) {
        persistImportedElements(monitor, fProcessor, fSystem);
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
    }

}
