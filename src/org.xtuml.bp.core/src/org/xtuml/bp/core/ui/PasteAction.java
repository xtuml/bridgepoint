//========================================================================
//
//File:      $RCSfile: PasteAction.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2012/10/15 22:08:50 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
//

package org.xtuml.bp.core.ui;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.BaseModelDelta;
import org.xtuml.bp.core.common.IPasteListener;
import org.xtuml.bp.core.common.IPersistenceHierarchyMetaData;
import org.xtuml.bp.core.common.ModelElementMovedModelDelta;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.util.OoaofgraphicsUtil;
import org.xtuml.bp.core.util.UIUtil;

public abstract class PasteAction extends CutCopyPasteAction  {

	// This varible is only specified here because it calls a public
	// static function that clears the list of downgraded
	// elements prior to paste running. While it is really not necessary,
	// it is good for the static to have a clear place or origin, and this
	// is that place.
    @SuppressWarnings("unused")
    
	protected HashMap<NonRootModelElement, ModelStreamProcessor> processorMap = new HashMap<NonRootModelElement, ModelStreamProcessor>();

	/**
	 * This is how we add elements to the list to be hooked back up during paste.
	 * Note that we clear this list
	 */
	private static ArrayList<DowngradedElement> downgradedElements = new ArrayList<DowngradedElement>(); 
	static class DowngradedElement {
		DowngradedElement(NonRootModelElement rto, NonRootModelElement rgo, String associationDowngraded) {
			this.rto = rto;
			this.rgo = rgo;
			this.associationDowngraded = associationDowngraded;
		}
		NonRootModelElement rto;
		NonRootModelElement rgo;
		String associationDowngraded;
	}
    
	public PasteAction() {
		super();
	}

	public void run() {
		// clear the processor map
		processorMap.clear();
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				TransactionManager manager = getTransactionManager();
				Transaction transaction = null;
				List<NonRootModelElement> destinations = getDestinations();
				try {
					transaction = manager
						.startTransaction(
							"Paste", //$NON-NLS-1$
							new ModelRoot[] {
									Ooaofooa.getDefaultInstance(),
									(ModelRoot) OoaofgraphicsUtil
									.getGraphicsRoot(
											Ooaofooa.DEFAULT_WORKING_MODELSPACE,
											OoaofgraphicsUtil
											.getGraphicsClass()) });
					Ooaofooa.beginSaveOperation();

						
					if (MOVE_IS_IN_PROGRESS ) {
						// Move only allows a single destination
						NonRootModelElement destination = destinations.get(0);
							
						// Iterate over each element that was selected. Note that
						// this is the actual selection. This is NOT using the "importer"
						// to suck in all dependent elements
						for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION) {
							PackageableElement_c srcPE = sourceElement.getPE();

							// disconnect
							try {	
								PersistableModelComponent srcPMC = sourceElement.getPersistableComponent(true);
								IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
								// If this element is a model root then we will unrelate from the parent
								if (hmd.isComponentRoot(sourceElement)) {
									// This gives us the path to a PMC that will be removed after the move 
									IPath srcPMCPath = srcPMC.getFullPath();
									srcPMC = srcPMC.getParent();
								}
								NonRootModelElement srcModelRoot = srcPMC.getRootModelElement();
										
										
								String opName = "unrelateAcrossR8000From";
								if (getClassName(sourceElement) == "component") {
									opName = "unrelateAcrossR8003From";
								}
									Class<?> clazz = srcPE.getClass();
									Method unrelateMethod = clazz.getMethod(opName,
											new Class[] { srcModelRoot.getClass(), boolean.class });
									unrelateMethod.invoke(srcPE, new Object[] { srcModelRoot, true });	
							} catch (Exception e) {
								CorePlugin.logError(
										"Unable to disconnect the PE_PE for " + getClassName(sourceElement) + "  (" + sourceElement.getName() //$NON-NLS-1$
												+ ") from its parent container.", e);
								throw e;
							}
						}
							
						// Move the graphics to their graphical model root
						processGraphics(destination); 
									
						// Move imported elements to the destination model root
						for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION) {
							sourceElement.updateModelRoot(destination.getModelRoot()); 
						}									
	
						// connect the selected elements to their destination
						for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION) {
							try {
								String opName = "Paste" + getClassName(sourceElement); //$NON-NLS-1$
								Class<?> clazz = destination.getClass();
								Method pasteMethod = clazz.getMethod(opName, new Class[] { UUID.class });
								pasteMethod.invoke(destination, new Object[] { sourceElement.Get_ooa_id() });
										
							} catch (Exception e) {
								CorePlugin.logError("Unable to connect " + getClassName(sourceElement) + "  (" //$NON-NLS-1$
										+ sourceElement.getName() + ") to " + destination.getClass().getSimpleName()
										+ " (" + destination.getName() + ") ", e);
								throw e;
							}
	
							// All the work prior to this changed the in memory model.
							// The transaction processing for this type of transaction 
							// will update the files on disk as needed.
							ModelElementMovedModelDelta change = new ModelElementMovedModelDelta(sourceElement
									,destination);
							Ooaofooa.getDefaultInstance().fireModelElementMoved(change);									
						} 
					} else {
						for (NonRootModelElement destination : destinations) {

							ModelStreamProcessor processor = new ModelStreamProcessor();
							processorMap.put(destination, processor);
							processor.setDestinationElement(destination);
							Object contents = CorePlugin.getSystemClipboard()
									.getContents(TextTransfer.getInstance());
							if (contents instanceof String) {
								String clipboardContents = (String) contents;
								processor.setContents(clipboardContents);
								String modelContents = clipboardContents
										.substring(clipboardContents.indexOf("\n") + 1);
								ByteArrayInputStream in = new ByteArrayInputStream(
										modelContents.getBytes());
								IModelImport importer = CorePlugin
										.getStreamImportFactory()
										.create(
												in,
												Ooaofooa
														.getInstance(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME),
												true,
												destination
														.getPersistableComponent()
														.getFile().getFullPath());
								// Import elements from the clipboard creating new UUIDs for the elements
								processor.runImporter(importer, monitor); 
								// Move imported elements to the destination model root
								processor.processFirstStep(monitor); 
								// move the imported graphical elements to the new model roots
								processGraphics(destination); 
							}
							// call paste* operation to connect the model element, set the PMC, and call resolution operation to batch relate
							processor.processSecondStep(monitor); 
						}
					}
				} catch (Exception e) {
					CorePlugin
							.logError("Paste failed.", e); //$NON-NLS-1$
					if (transaction != null && manager != null
							&& manager.getActiveTransaction() == transaction) {
						manager.cancelTransaction(transaction, e);
						transaction = null;
					}
				} finally {
					if (transaction != null) {
						manager.endTransaction(transaction);
					}
					Ooaofooa.endSaveOperation();
				}
				if(transaction != null) {
					// only perform finishing work if the transaction
					// was successful
					boolean continueProcessing = displayProblemDialog(manager, transaction, monitor);
					if(!continueProcessing) {
						// return as the user has cancelled the paste
						return;
					}
					transaction = null;
					for(NonRootModelElement destination : destinations) {
						if (MOVE_IS_IN_PROGRESS) {
							CorePlugin.parseAll(destination, monitor);
						} else {
							ModelStreamProcessor processor = processorMap.get(destination);
							processor.runParseOnImportedElements(manager, monitor);
						}
					}
					// gather all of the loaded instances, including graphical
					for(NonRootModelElement destination : destinations) {
						List<NonRootModelElement> instances = new ArrayList<NonRootModelElement>();
						if (MOVE_IS_IN_PROGRESS) {
							instances.addAll(ELEMENT_MOVE_SOURCE_SELECTION);
						} else {							
							ModelStreamProcessor processor = processorMap.get(destination);
							NonRootModelElement[] loadedInstances = processor
									.getImporter().getLoadedInstances();
							NonRootModelElement[] loadedGraphicalInstances = processor
									.getImporter().getLoadedGraphicalInstances();
							instances.addAll(Arrays.asList(loadedInstances));
							instances.addAll(Arrays.asList(loadedGraphicalInstances));
						}
						for(IPasteListener listener : CorePlugin.getPasteListeners()) {
							listener.pasteCompleted(destination, instances);
						}
					}					
				}
			}

		};
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getShell());
		try {
			dialog.run(false, false, runnable);
		} catch (Throwable e) {
			CorePlugin.logError("Unable to import contents from clipboard.", e); //$NON-NLS-1$
		} finally {
			// Regardless of if this was a move or not we can reset this flag now.
			if (MOVE_IS_IN_PROGRESS) {
				// Clear the clipboard to prevent another paste
				CorePlugin.getSystemClipboard().clearContents();
				ELEMENT_MOVE_SOURCE_SELECTION = new ArrayList<NonRootModelElement>();
				MOVE_IS_IN_PROGRESS = false;
			}
		}
	}

	private static String getClassName(NonRootModelElement sourceElement) {
		String srcClassType = sourceElement.getClass().getSimpleName();
		String srcClassPart = srcClassType.substring(0, srcClassType.length() - 2).toLowerCase();
		return srcClassPart;
	}
	
	/**
	 * Displays a dialog if the problems map is not empty
	 * 
	 * @param transaction
	 * @param monitor
	 */
	private boolean displayProblemDialog(TransactionManager manager,
			Transaction transaction, IProgressMonitor monitor) {
		if (ModelStreamProcessor.fProblemElements.size() != 0) {
			Set<String> textSet = ModelStreamProcessor.fProblemElements
					.keySet();
			Iterator<?> messageSet = textSet.iterator();
			while (messageSet.hasNext()) {
				String messageText = (String) messageSet.next();
				ArrayList<?> list = ModelStreamProcessor.fProblemElements
						.get(messageText);
				String elementNames = "";
				for (int i = 0; i < list.size(); i++) {
					String elementName = (String) list.get(i);
					if (!elementName.equals("")) {
						elementNames = elementNames + elementName + "\n";
					}
				}
				if (!elementNames.equals("")) {
					messageText = messageText + "\n\n" + elementNames;
				}
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				Image image = PlatformUI.getWorkbench().getDisplay()
						.getSystemImage(SWT.ICON_WARNING);
				boolean proceed = UIUtil.openMessageDialog(shell,
						"Problems during paste", image, messageText,
						UIUtil.BPMessageTypes.WARNING,
						new String[] { "Proceed", "Undo" }, 0);
				Transaction revertTransaction = null;
				if (!proceed) {
					revertTransaction = manager.revertTransaction(transaction,
							false, false, monitor);
					manager.removeTransactionFromStack(revertTransaction,
							Transaction.REDO_TYPE);
					ModelStreamProcessor.fProblemElements.clear();
					return false;
				}
			}
			ModelStreamProcessor.fProblemElements.clear();
		}
		return true;
	}

	/**
	 * hook up the pasted graphics to the destination roots
	 * 
	 * @param destination
	 * @throws Exception 
	 */
	public abstract void processGraphics(NonRootModelElement destination) throws Exception;

	public abstract TransactionManager getTransactionManager();

	public abstract List<NonRootModelElement> getDestinations();

	public static void addElementToProblemsList(String message,
			String elementname) {
		ModelStreamProcessor.addElementToProblemsList(message, elementname);
	}

	private String[] getClipboardTypes(String contents, NonRootModelElement destination) {
		return ModelStreamProcessor.getExportedTypes(contents);
	}

	protected boolean isTypeBeingPasted(NonRootModelElement type, NonRootModelElement destination) {
		return processorMap.get(destination).isTypePartOfExport(type);
	}

	protected NonRootModelElement[] getLoadedGraphicalInstances(NonRootModelElement destination) {
		return processorMap.get(destination).getImporter()
				.getLoadedGraphicalInstances();
	}

	@Override
	public boolean isEnabled() {
		boolean isEnabled = false;
		Clipboard cb = CorePlugin.getSystemClipboard();
		if (cb != null && !cb.isDisposed()) {
			Object contents = cb.getContents(TextTransfer.getInstance());
			if (contents instanceof String) {
				String stringContents = (String) contents;
				List<NonRootModelElement> destinations = getDestinations();
				// If this is a move. only 1 destination is allowed
				if (MOVE_IS_IN_PROGRESS && destinations.size() > 1) {
					return false;
				}
				for (NonRootModelElement destination : destinations) {
					String types[] = getClipboardTypes((String) contents, destination);
					if(types.length > 0) {
						for (int i = 0; i < types.length; i++) {
							if (MOVE_IS_IN_PROGRESS) {								
								// If this is a move, the source and destination can not be the same.
								for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION ) {
									if (destination.getPersistableComponent() == sourceElement.getPersistableComponent()) {
										return false;
									}
								}
							}
							isEnabled = supportsPaste(destination, types[i]);
							if(!isEnabled) {
								break;
							}
						}
					}
				}
			}			
		}
		return isEnabled;
	}
		
	public static void addDownGradedElement(NonRootModelElement rto, NonRootModelElement rgo, String associationDowngraded) {
		DowngradedElement element = new DowngradedElement(rto, rgo, associationDowngraded);
		downgradedElements.add(element);
	}
	
	@Override
	protected int getActionType() {
		return PASTE_TYPE;
	}
	
	@Override
	public void postRun() {
	}

	protected abstract boolean supportsPaste(Object target, String child);
}