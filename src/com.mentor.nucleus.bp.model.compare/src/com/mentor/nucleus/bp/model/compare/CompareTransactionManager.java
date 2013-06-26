package com.mentor.nucleus.bp.model.compare;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.compare.ResourceNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.RelationshipChangeModelDelta;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.ui.canvas.CanvasTransactionListener;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class CompareTransactionManager implements ITransactionListener {

	private TransactionManager manager = new TransactionManager();

	public CompareTransactionManager() {
		// initialize undo/redo actions, otherwise some cases can
		// cause a dead lock
		manager.getUndoAction();
		manager.getRedoAction();
		TransactionManager.getSingleton().addTransactionListener(this);
	}
	
	public Transaction startCompareTransaction() {
		Transaction transaction = null;
		try {
			if (manager.getActiveTransaction() != null) {
				manager.cancelTransaction(manager.getActiveTransaction());
			}
			transaction = manager.startTransaction(
					"Merge Change Collection Transaction", new ModelRoot[] {
							Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
		} catch (TransactionException e) {
			ComparePlugin.writeToLog("Unable to start merge transaction.", e,
					getClass());
		}
		return transaction;
	}
	
	public void endTransaction(Transaction transaction) {
		manager.endTransaction(transaction);
	}
	
	public void processTransaction(Transaction transaction) {
		manager.processTransaction(transaction, false);
	}
	
	public List<Transaction> getTransactions() {
		return manager.getUndoStack();
	}
	
	public void applyTransactions(Object input, Ooaofooa modelRoot, boolean startTransaction) throws TransactionException {
		Transaction mergeTransaction = null;
		if(startTransaction) {
			mergeTransaction = TransactionManager.getSingleton()
					.startTransaction("Model Merge", new ModelRoot[] {Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance()});
		}
		// locate the required elements in the destination (local file)
		IFile localFile = getLocalFileFromInput(input);
		adjustDeltasForDestination(localFile);
		ArrayList<Transaction> transactions = manager.getUndoStack();
		for(Transaction transaction : transactions) {
			// gather all new elements to process later
			List<NonRootModelElement> newElements = new ArrayList<NonRootModelElement>();
			ModelRoot[] roots = transaction.getParticipatingModelRoots();
			for(ModelRoot root : roots) {
				IModelDelta[] deltas = transaction.getDeltas(root);
				int i = 0;
				for(IModelDelta delta : deltas) {
					if(delta.getKind() == Modeleventnotification_c.DELTA_NEW) {
						NonRootModelElement newElement = (NonRootModelElement) delta.getModelElement();
						newElements.add(newElement);
					}
					i++;
				}
			}
			TransactionManager.getSingleton().processTransaction(transaction, true);
			// adjust the transaction so that it has the default instance of ooaofooa
			transaction.updateParticipatingModelRoot(Ooaofooa
					.getDefaultInstance(), modelRoot);
			transaction.updateParticipatingModelRoot(Ooaofgraphics
					.getDefaultInstance(), Ooaofgraphics
					.getInstance(modelRoot.getId()));
		}
		// disable reconciler
		if(startTransaction) {
			CanvasTransactionListener.disableReconciler();
			TransactionManager.getSingleton().endTransaction(mergeTransaction, false);
			CanvasTransactionListener.enableReconciler();
		}
	}

	private IFile getLocalFileFromInput(Object input) {
		if(input instanceof ResourceNode) {
			return (IFile) ((ResourceNode) input).getResource();
		}
		return null;
	}

	private void adjustDeltasForDestination(final IFile localFile) {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI
				.getWorkbench().getDisplay().getActiveShell());
		try {
			dialog.run(false, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					ArrayList<Transaction> transactions = manager.getUndoStack();
					List<IModelDelta> deltaList = new ArrayList<IModelDelta>();
					for(Transaction transaction : transactions) {
						ModelRoot[] roots = transaction.getParticipatingModelRoots();
						for(ModelRoot root : roots) {
							deltaList.addAll(Arrays.asList(transaction
									.getDeltas(root)));
						}
					}
					IFile file = localFile;
					if(file == null) {
						// here we are dealing with a team provider
						// that does not allow access to the local
						// file, so we find the file from a delta's
						// element's ID
						file = scanDeltasForFile(deltaList);
						if(file == null) {
							CorePlugin
									.logError(
											"The merge failed.  Unable to locate file to merge changes into.",
											null);
							return;
						}
					}
					monitor.beginTask("Preparing workspace for merge...",
							getWorkForMerge(
									deltaList.toArray(new IModelDelta[deltaList
											.size()]), file));
					try {
						CoreUtil.loadWorkspace(monitor);
					} catch (CoreException e1) {
						ComparePlugin
								.writeToLog(
										"Unable to load workspace in preparation of merge.",
										e1, getClass());
					}
					PersistableModelComponent component = PersistenceManager
							.findComponent(file.getFullPath());
					if (component == null) {
						CorePlugin
								.logError(
										"The merge failed.  Unable to locate file to merge changes into.",
										null);
						return;
					}
					if (!component.isLoaded()) {
						try {
							component.load(new NullProgressMonitor());
						} catch (CoreException e) {
							ComparePlugin.writeToLog(
									"Unable to load component for processing", e,
									getClass());
							return;
						}
					}
					for(Transaction transaction : transactions) {
						ModelRoot destinationRoot = component
								.getRootModelElement().getModelRoot();
						ModelRoot[] roots = transaction.getParticipatingModelRoots();
						for(ModelRoot root : roots) {
							IModelDelta[] deltas = transaction.getDeltas(root);
							adjustDeltasForModelRoot(deltas, monitor, root, destinationRoot);
						}
					}
				}
			});
		} catch (InvocationTargetException e) {
			ComparePlugin.writeToLog("Unable to process merge transaction.", e, getClass());
		} catch (InterruptedException e) {
			ComparePlugin.writeToLog("Unable to process merge transaction.", e, getClass());
		}
	}

	public static IFile scanDeltasForFile(List<IModelDelta> deltaList) {
		for(IModelDelta delta : deltaList) {
			Object instanceKey = ((NonRootModelElement) delta.getModelElement())
					.getInstanceKey();
			NonRootModelElement element = (NonRootModelElement) Ooaofooa
					.getDefaultInstance().getInstanceList(
							deltaList.get(0).getModelElement()
									.getClass()).getGlobal(
							instanceKey);
			if(element == null) {
				// likely a graphical element try the graphical root
				element = getElementGlobally(deltaList.get(0)
						.getModelElement().getClass(), instanceKey);
			}
			if(element != null) {
				return element.getFile();
			}
		}
		return null;
	}

	public static void adjustDeltasForModelRoot(IModelDelta[] deltas,
			IProgressMonitor monitor, ModelRoot participatingRoot,
			ModelRoot destinationRoot) {
		monitor.setTaskName("Processing Merge Deltas...");
		for (int i = 0; i < deltas.length; i++) {
			if (participatingRoot instanceof Ooaofgraphics) {
				destinationRoot = Ooaofgraphics.getInstance(destinationRoot
						.getId());
			}
			if (deltas[i] instanceof RelationshipChangeModelDelta) {
				if (deltas[i].getModelElement() != null) {
					NonRootModelElement sourceElement = findElementInDestination(
							deltas[i].getModelElement(), destinationRoot);
					if (sourceElement != null) {
						((RelationshipChangeModelDelta) deltas[i])
								.setSourceModelElement(sourceElement);
					}
				}
				ModelElement destinationElement = ((RelationshipChangeModelDelta) deltas[i])
						.getDestinationModelElement();
				if (destinationElement != null) {
					destinationElement = findElementInDestination(
							destinationElement, destinationRoot);
					if (destinationElement != null) {
						((RelationshipChangeModelDelta) deltas[i])
								.setDestinationModelElement(destinationElement);
					}
				}
			} else if (deltas[i] instanceof AttributeChangeModelDelta) {
				// special case for attributes that are objects
				AttributeChangeModelDelta acmd = (AttributeChangeModelDelta) deltas[i];
				if (acmd.getNewValue() instanceof NonRootModelElement) {
					NonRootModelElement newValue = (NonRootModelElement) acmd
							.getNewValue();
					ModelRoot newRoot = destinationRoot;
					if (newValue.getModelRoot() instanceof Ooaofooa) {
						newRoot = Ooaofooa.getInstance(newRoot.getId());
					} else if (newValue.getModelRoot() instanceof Ooaofgraphics) {
						newRoot = Ooaofgraphics.getInstance(newRoot.getId());
					}
					NonRootModelElement destinationElement = findElementInDestination(
							newValue, destinationRoot);
					acmd.setNewValue(destinationElement);
				}
				NonRootModelElement element = (NonRootModelElement) acmd
						.getModelElement();
				ModelRoot newRoot = destinationRoot;
				if (element.getModelRoot() instanceof Ooaofooa) {
					newRoot = Ooaofooa.getInstance(newRoot.getId());
				} else if (element.getModelRoot() instanceof Ooaofgraphics) {
					newRoot = Ooaofgraphics.getInstance(newRoot.getId());
				}
				NonRootModelElement destinationElement = findElementInDestination(
						element, destinationRoot);
				acmd.setModelElement(destinationElement);
			} else {
				NonRootModelElement element = (NonRootModelElement) deltas[i].getModelElement();
				if(element.isProxy()) {
					NonRootModelElement destinationElement = findElementInDestination(
							element, destinationRoot);
					if(destinationElement != null && !destinationElement.isProxy()) {
						continue;
					}
				}
				// need to set the model root for new elements
				if (deltas[i].getKind() == Modeleventnotification_c.DELTA_NEW) {
					NonRootModelElement newElement = (NonRootModelElement) deltas[i]
							.getModelElement();
					InstanceList instanceList = newElement.getModelRoot()
							.getInstanceList(newElement.getClass());
					instanceList.remove(newElement);
					newElement.setModelRoot(destinationRoot);
					destinationRoot.getInstanceList(newElement.getClass()).add(newElement);
					destinationRoot.getInstanceList(newElement.getClass()).put(
							newElement.getInstanceKey(), newElement);
				}
				NonRootModelElement destinationElement = findElementInDestination(
						deltas[i].getModelElement(), destinationRoot);
				if (destinationElement != null) {
					((BaseModelDelta) deltas[i])
							.setModelElement(destinationElement);
				}
			}
			monitor.worked(1);
		}
	}

	private static NonRootModelElement findElementInDestination(
			ModelElement modelElement, ModelRoot destinationRoot) {
		NonRootModelElement sourceElement = (NonRootModelElement) destinationRoot
				.getInstanceList(modelElement.getClass())
				.getGlobal(
						((NonRootModelElement) modelElement)
								.getInstanceKey());
		// New state transition classes do not consider the transition id as part of
		// the instance key, which causes any transition that is assigned the same
		// event to be found, see below for where we actually locate the instance
		if(sourceElement instanceof NewStateTransition_c) {
			sourceElement = null;
		}
		if (sourceElement == null) {
			InstanceList instanceList = destinationRoot
					.getInstanceList(modelElement.getClass());
			for (NonRootModelElement element : instanceList) {
				// new state transitions will likely have a different event
				// assigned which will cause the test below to fail even if
				// for the same transition, just look at the transition id
				if(element instanceof NewStateTransition_c) {
					NewStateTransition_c candidate = (NewStateTransition_c) element;
					NewStateTransition_c fromMerge = (NewStateTransition_c) modelElement;
					if(candidate.getTrans_id().equals(fromMerge.getTrans_id())) {
						return candidate;
					}
				} else {
					if (element.cachedIdentityEquals(modelElement)) {
						sourceElement = element;
						break;
					}
				}
			}
		}
		return sourceElement;
	}

	public static NonRootModelElement getElementGlobally(
			Class<?> classType, Object instanceKey) {
		Ooaofgraphics[] roots = Ooaofgraphics.getInstances();
		NonRootModelElement element = null;
		for (int rootCount = 0; rootCount < roots.length; rootCount++) {
			if (roots[rootCount].isCompareRoot()) {
				// do not search compare roots
				continue;
			}
			// search for element under the root using get(key)
			element = (NonRootModelElement) roots[rootCount].getInstanceList(
					classType).get(instanceKey);
			if (element != null) {
				break;
			}
		}
		return element;
	}

	protected int getWorkForMerge(IModelDelta[] deltas, IFile file) {
		int work = 0;
		try {
			work = CoreUtil.getChildrenCountOfProjects(new IProject[] {file.getProject()});
		} catch (CoreException e) {
			// skip work count
			work = 0;
		}
		work += deltas.length;
		return work;
	}

	/**
	 * Returns the plugin's redo action
	 */
	public Action getRedoAction() {
		return manager.getRedoAction();
	}

	/**
	 * Returns the plugin's undo action
	 */
	public Action getUndoAction() {
		return manager.getUndoAction();
	}

	@Override
	public void transactionCancelled(Transaction transaction) {
		// not concerned
	}

	@Override
	public void transactionEnded(Transaction transaction) {
		// registered against the main transaction manager
		// need to reset our undo/redo states
		manager.clearStacks();
		manager.setUndoRedoActionsState();
	}

	@Override
	public void transactionStarted(Transaction transaction) {
		// not concerned
	}

	public void addTransactionListener(ITransactionListener listener) {
		manager.addTransactionListener(listener);
	}
	
	public void removeTransactionListener(ITransactionListener listener) {
		manager.removeTransactionListener(listener);
	}

	public Object getActiveTransaction() {
		return manager.getActiveTransaction();
	}

	public void cancelTransaction(Transaction transaction) {
		manager.cancelTransaction(transaction);
	}
	
}
