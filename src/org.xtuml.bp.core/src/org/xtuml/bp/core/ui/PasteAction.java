//========================================================================
//
//File:      PasteAction.java
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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IPasteListener;
import org.xtuml.bp.core.common.IPersistenceHierarchyMetaData;
import org.xtuml.bp.core.common.ModelElementMovedModelDelta;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.util.OoaofgraphicsUtil;
import org.xtuml.bp.core.util.UIUtil;

public abstract class PasteAction extends CutCopyPasteAction  {

	public static final String TransactionNameForMove = "Model Element Move";
	public static final String TransactionNameForCopyPaste = "Paste";

	protected HashMap<NonRootModelElement, ModelStreamProcessor> processorMap = new HashMap<NonRootModelElement, ModelStreamProcessor>();

	public PasteAction() {
		super();
	}
	
	/**
	 * This class defines what occurs during paste. An inner class is created here 
	 * to clarify the difference in processing between the copy/paste case
	 * and the cut/paste move) case which share the UI but have different 
	 * implementation.
	 * 
	 */
	class PasteRunnable implements IRunnableWithProgress {
		
		/**
		 * This paste is only used when the user has selected cut/paste (move)
		 * 
		 * @param destination - The destination selection by the user
		 * @throws Exception
		 */
		private void processPasteForMove(NonRootModelElement destination) throws Exception {			
			// Iterate over each element that was selected. Note that
			// this is the actual selection. This is NOT using the "importer"
			// to suck in all dependent elements
			for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION) {

				// disconnect
				try {
					NonRootModelElement parentNRME = getContainerForMove(sourceElement);

					// If a "disconnect" operation exists on this source class type then 
					// we will use it.
					Class<?> clazz = sourceElement.getClass();
					String opName = "Disconnect";
					Method disconnectMethod = null;
					Method[] methods = clazz.getMethods();
					for (int i = 0; disconnectMethod==null && i < methods.length; i++) {
						if (methods[i].getName().equals(opName)) {
							disconnectMethod = methods[i];
							disconnectMethod.invoke(sourceElement);
						}
					}
					
					// Now, use the generated operation for disconnecting the PE from parent 
					// package or component, skip if we're directly under the System or if this is note a PE_PE (ex Requirements and Provisions)
					PackageableElement_c srcPE = sourceElement.getPE();
					boolean parentIsSys = parentNRME instanceof SystemModel_c;
					if ( !parentIsSys && srcPE != null ) {
						opName = "unrelateAcrossR8000From";
						if (getClassName(parentNRME).equals("component")) {
							opName = "unrelateAcrossR8003From";
						}
						clazz = srcPE.getClass();
						disconnectMethod = clazz.getMethod(opName,
								new Class[] { parentNRME.getClass(), boolean.class });
						disconnectMethod.invoke(srcPE, new Object[] { parentNRME, true });	
					}							
				} catch (Exception e) {
					CorePlugin.logError(
							"Unable to disconnect the PE_PE for " + getClassName(sourceElement) + "  (" + sourceElement.getName() //$NON-NLS-1$
									+ ") from its parent container.", e);
					throw e;
				}
			}
				
			// connect the selected elements to their destination
			for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION) {
				try {
					String opName = "Paste" + getClassName(sourceElement); //$NON-NLS-1$
					Class<?> clazz = destination.getClass();
					Method pasteMethod = clazz.getMethod(opName, new Class[] { UUID.class });
					pasteMethod.invoke(destination, new Object[] { sourceElement.Get_ooa_id() });
							
				} catch (NoSuchMethodException nsme) {
					// ignore for the case of elements that do not require calling
					// a paste operation
				} catch (Exception e) {
					CorePlugin.logError("Unable to connect " + getClassName(sourceElement) + "  (" //$NON-NLS-1$
							+ sourceElement.getName() + ") to " + destination.getClass().getSimpleName()
							+ " (" + destination.getName() + ") ", e);
					throw e;
				}

				// All the work prior to this changed the in memory model.
				// The transaction processing for this type of transaction 
				// will update the files on disk as needed.
				NonRootModelElement parentNRME = getContainerForMove(sourceElement);
				ModelElementMovedModelDelta change = new ModelElementMovedModelDelta(sourceElement, destination,
						parentNRME);
				Ooaofooa.getDefaultInstance().fireModelElementMoved(change);									
			} 
		
			// Check if anything (RTOs or RGOs) must be downgraded. Only run the
			// check function if it exists. We don't want to throw an error if it
			// does not exist.
			for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION) {
				try {
					String opName = "Downgradecheck"; //$NON-NLS-1$
					Class<?> clazz = sourceElement.getClass();
					Method downgradeCheckMethod = null;
					Method[] methods = clazz.getMethods();
					for (int i = 0; downgradeCheckMethod==null && i < methods.length; i++) {
						if (methods[i].getName().equals(opName)) {
							downgradeCheckMethod = methods[i];
							downgradeCheckMethod.invoke(sourceElement);
						}
					}
				} catch (Exception e) {
					CorePlugin.logError("Unable to run downgradeCheck() for " + getClassName(sourceElement), e); //$NON-NLS-1$
					throw e;
				}
			} 
			
			// Move the graphics to their graphical model root
			processGraphics(destination); 
		}
		
		/**
		 * This paste is only used when the user has selected copy/paste
		 * 
		 * @param destination - The destination selection by the user
		 * @throws Exception
		 */
		private void processPasteForCopy(NonRootModelElement destination, final IProgressMonitor monitor) throws Exception {
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
	
		@Override
		public void run(final IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			TransactionManager manager = getTransactionManager();
			List<NonRootModelElement> destinations = getDestinations();
			Transaction transaction = null;
			try {
				String pasteTransactionName = TransactionNameForCopyPaste;
				if (moveIsInProgress()) {
					pasteTransactionName = TransactionNameForMove;
				}
				
				transaction = manager
						.startTransaction(pasteTransactionName, //$NON-NLS-1$
								new ModelRoot[] { Ooaofooa.getDefaultInstance(),
										(ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
												Ooaofooa.DEFAULT_WORKING_MODELSPACE,
												OoaofgraphicsUtil.getGraphicsClass()) });
			} catch (TransactionException e1) {
				CorePlugin.logError("Failed to start the Paste transaction.", e1); //$NON-NLS-1$
				return;
			}
			
			boolean transactionWasCanceled = false;
			try {				
				Ooaofooa.beginSaveOperation();
				
				if (moveIsInProgress() ) {						
					// Move only allows 1 destination to be selected
					processPasteForMove(destinations.get(0));
				} else {
					for (NonRootModelElement destination : destinations) {
						processPasteForCopy(destination, monitor);
					}
				}
			} catch (Exception e) {
				CorePlugin.logError("Paste failed.", e); //$NON-NLS-1$
				if (manager.getActiveTransaction() == transaction) {
					// this will revert the transaction, thus aborting it
					manager.cancelTransaction(transaction, e);
					transaction = null;
					transactionWasCanceled = true;
				}
			} finally {
				if (!transactionWasCanceled) {
					manager.endTransaction(transaction);
				}
				
				// This call is simply used as, essentially, a critical section for verifier execution.
				// Regardless of what is going on with transaction processing (abort vs end) it 
				// must always be called to release the critical section.
				Ooaofooa.endSaveOperation();
				
				// If an exception was caught during the transaction then the transaction has been aborted.
				// the is no reason to go another farther.
				if (transactionWasCanceled) {
					return;
				}
			}

			if (!moveIsInProgress()) {
				boolean continueProcessing = displayProblemDialog(monitor);
				if (!continueProcessing) {
					// The user canceled the paste operation, revert the transaction and exit
					Transaction revertTransaction = manager.revertTransaction(transaction, false, false, monitor);
					manager.removeTransactionFromStack(revertTransaction, Transaction.REDO_TYPE);
					ModelStreamProcessor.fProblemElements.clear();
					return;
				}

				
				// Since copy/paste only affects the destination we only need to parse the new elements
				// that were created.
				for (NonRootModelElement destination : destinations) {
					ModelStreamProcessor processor = processorMap.get(destination);
					processor.runParseOnImportedElements(manager, monitor);
				}
				
				// gather all of the loaded instances, including graphical
				//
				// The <PasteListener>.pasteCompeted operation (called below) is used to move any elements
				// still on the clipboard to the destination model root (see GraphicalPasteListener)
				for (NonRootModelElement destination : destinations) {
					List<NonRootModelElement> instances = new ArrayList<NonRootModelElement>();
					ModelStreamProcessor processor = processorMap.get(destination);
					NonRootModelElement[] loadedInstances = processor.getImporter().getLoadedInstances();
					NonRootModelElement[] loadedGraphicalInstances = processor.getImporter()
							.getLoadedGraphicalInstances();
					instances.addAll(Arrays.asList(loadedInstances));
					instances.addAll(Arrays.asList(loadedGraphicalInstances));
					for (IPasteListener listener : CorePlugin.getPasteListeners()) {
						listener.pasteCompleted(destination, instances);
					}
				}
			}
		}
		
	}
	
	/**
	 * This run routine handles paste for both the copy and cut (move cases)
	 */
	public void run() {
		// clear the processor map
		processorMap.clear();
		IRunnableWithProgress runnable = new PasteRunnable();
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getShell());
		try {
			dialog.run(false, false, runnable);
		} catch (Throwable e) {
			CorePlugin.logError("Unable to import contents from clipboard.", e); //$NON-NLS-1$
		} finally {
			if (moveIsInProgress()) {
				stopMove();
			}
		}
	}

	public static NonRootModelElement getContainerForMove(NonRootModelElement sourceElement) {
		PersistableModelComponent srcPMC = sourceElement.getPersistableComponent(true);
		IPersistenceHierarchyMetaData hmd = PersistenceManager.getHierarchyMetaData();
		// If this element is a model root then we will unrelate from the parent
		if (hmd.isComponentRoot(sourceElement)) {
			// This gives us the path to a PMC that will be removed after the move 
			srcPMC = srcPMC.getParent();
		}
		NonRootModelElement srcModelRoot = srcPMC.getRootModelElement();
		return srcModelRoot;
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
	private boolean displayProblemDialog(IProgressMonitor monitor) {
		boolean proceed = true;
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
				proceed = UIUtil.openMessageDialog(shell,
						"Problems during paste", image, messageText,
						UIUtil.BPMessageTypes.WARNING,
						new String[] { "Proceed", "Undo" }, 0);
			}
			ModelStreamProcessor.fProblemElements.clear();
		}
		return proceed;
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
				List<NonRootModelElement> destinations = getDestinations();
				// If this is a move. only 1 destination is allowed
				if (moveIsInProgress() && destinations.size() > 1) {
					return false;
				}
				for (NonRootModelElement destination : destinations) {
					String types[] = getClipboardTypes((String) contents, destination);
					if(types.length > 0) {
						for (int i = 0; i < types.length; i++) {
							if (moveIsInProgress()) {								
								for (NonRootModelElement sourceElement : ELEMENT_MOVE_SOURCE_SELECTION ) {
									
									// If this is a move, the source and destination can not be the same.
									NonRootModelElement sourceContainer = getContainerForMove(sourceElement);
									if (destination == sourceContainer) {
										return false;
									}

									// Don't allow move of a package to a package under the source selection
									if (sourceElement instanceof Package_c) {
										NonRootModelElement tempParent = destination.getParentPackage();
										while (tempParent != null) {
											if (tempParent == sourceElement) {
												return false;
											}
											tempParent = tempParent.getParentPackage();
										}
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
		
	@Override
	protected int getActionType() {
		return PASTE_TYPE;
	}
	
	@Override
	public void postRun() {
	}

	protected abstract boolean supportsPaste(Object target, String child);
}