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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IPasteListener;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.RGOResolver;
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
	private RGOResolver rgoResolver;
    
	protected HashMap<NonRootModelElement, ModelStreamProcessor> processorMap = new HashMap<NonRootModelElement, ModelStreamProcessor>();

	public PasteAction() {
		super();
	}

	public void run() {
		// clear the processor map
		processorMap.clear();
		RGOResolver.clearDowngradedElementsList();
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
					// The elements have been pasted, so now if this is a cut/paste, delete the source elements
					if (MOVE_IS_IN_PROGRESS && ELEMENT_MOVE_SOURCE_SELECTION != null ) {
						// Delete the source elements. We use the saved IStructuredSelection that was made by the user
						// during the cut operation because since the UUIDs were not changed during paste, if 
						// an element was pasted to the same model root there would be a problem searching by ID
						((DeleteAction) CorePlugin.getDeleteAction()).deleteSelection(ELEMENT_MOVE_SOURCE_SELECTION);
						ELEMENT_MOVE_SOURCE_SELECTION = null;
					} 
					
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
													.getFile().getFullPath(), !MOVE_IS_IN_PROGRESS);
							processor.runImporter(importer, monitor);
							processor.processFirstStep(monitor);
							processGraphics(destination);
							processor.finishImport(monitor);
						}
					}
				} catch (Exception e) {
					CorePlugin
							.logError("Unable to start Paste transaction.", e); //$NON-NLS-1$
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
					boolean result = displayProblemDialog(manager, transaction, monitor);
					if(!result) {
						// return as the user has cancelled the paste
						return;
					}
					transaction = null;
					for(NonRootModelElement destination : destinations) {
						ModelStreamProcessor processor = processorMap.get(destination);
						processor.runParseOnImportedElements(manager, monitor);
					}
					// gather all of the loaded instances, including graphical
					for(NonRootModelElement destination : destinations) {
						ModelStreamProcessor processor = processorMap.get(destination);
						NonRootModelElement[] loadedInstances = processor
								.getImporter().getLoadedInstances();
						NonRootModelElement[] loadedGraphicalInstances = processor
								.getImporter().getLoadedGraphicalInstances();
						List<NonRootModelElement> instances = new ArrayList<NonRootModelElement>();
						instances.addAll(Arrays.asList(loadedInstances));
						instances.addAll(Arrays.asList(loadedGraphicalInstances));
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
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to import contents from clipboard.", e); //$NON-NLS-1$
		} catch (InterruptedException e) {
			CorePlugin.logError("Unable to import contents from clipboard.", e); //$NON-NLS-1$
		} finally {
			// Regardless of if this was a move or not we can reset this flag now.
			if (MOVE_IS_IN_PROGRESS) {
				// Clear the clipboard to prevent another paste
				CorePlugin.getSystemClipboard().clearContents();
				MOVE_IS_IN_PROGRESS = false;
			}
		}
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
	 */
	public abstract void processGraphics(NonRootModelElement destination);

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
								NonRootModelElement[] sourceElements = Selection.getSelectedNonRootModelElements(ELEMENT_MOVE_SOURCE_SELECTION);
								for (int j = 0;  j < sourceElements.length; j++) {
									NonRootModelElement sourceElement = sourceElements[j];
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
	
	@Override
	protected int getActionType() {
		return PASTE_TYPE;
	}
	
	@Override
	public void postRun() {
	}

	protected abstract boolean supportsPaste(Object target, String child);
}