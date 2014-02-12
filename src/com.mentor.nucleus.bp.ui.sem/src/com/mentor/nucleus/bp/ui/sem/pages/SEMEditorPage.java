package com.mentor.nucleus.bp.ui.sem.pages;
//=====================================================================
//
// File:      $RCSfile: SEMEditorPage.java,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2013/05/10 13:29:04 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
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
// This class is responsible for providing the help ids for the
// SessionExplorerPlugin plugin.
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.EventIgnored_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.LocalEvent_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.ui.sem.listeners.SemEditorListener;
import com.mentor.nucleus.bp.ui.sem.viewers.SEMEditingSupport;
import com.mentor.nucleus.bp.ui.sem.viewers.SEMTableViewer;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;

public class SEMEditorPage extends Composite {

	private StateMachine_c fStateMachine;
	private SEMTableViewer fTableViewer;
	private ArrayList<StateMachineEvent_c> events = new ArrayList<StateMachineEvent_c>();
	private ITransactionListener fTransactionListener;
	private SemEditorListener semListener;
	private static Image IMAGE_OAL_NO_GEAR = CorePlugin.getImageDescriptor("edit_oal_nogear.gif").createImage();
	private static Image IMAGE_EDIT_OAL = CorePlugin.getImageDescriptor("edit_oal.gif").createImage();
	protected static Cursor fOpenCursor;
	protected static Cursor fDefaultCursor;
	
	public SEMEditorPage(Composite parent, StateMachine_c stateMachine) {
		super(parent, SWT.SINGLE);
		if(fDefaultCursor == null) {
			fDefaultCursor = getCursor();
		}
		if(fOpenCursor == null) {
			fOpenCursor = new Cursor(getDisplay(), SWT.CURSOR_HAND);
		}
		fStateMachine = stateMachine;
		createControls(parent);
	}

	private void createControls(Composite parent) {
		setLayout(new GridLayout(2, false));
		Label label = new Label(this, SWT.FLAT);
		label.setText("events");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		label = new Label(this, SWT.FLAT);
		label.setText("states");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true, 1, 1));
		createTableViewer(parent);
		addModelChangeListeners();
	}

	/**
	 * This class exists simply as a place holder for the no-state row.  When
	 * a no-state is present, its data is filled-in by the getColumnText()
	 * function (at the same point the other states data is determined).
	 * 
	 * @see getColumnText()
	 *
	 */
	class NoStateRow extends Object {
		
		NoStateRow() {
			
		}
	};
	
	private void createTableViewer(Composite parent) {
		fTableViewer = new SEMTableViewer(this, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER | SWT.FULL_SELECTION);
		addTableListeners();
		createInitialColumns(true);

		fTableViewer.setContentProvider(new IStructuredContentProvider() {
		
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// nothing to do
			}
		
			@Override
			public void dispose() {
				// nothing to do
			}
		
			@Override
			public Object[] getElements(Object inputElement) {
				if(inputElement instanceof StateMachine_c) {
					List<Object> rowData = new ArrayList<Object>();
					StateMachineState_c[] smStates = StateMachineState_c.getManySM_STATEsOnR501(fStateMachine);
					if (smStates.length > 0) {
						// See if there is an creation transition
						LocalEvent_c assignedLocalEvent = LocalEvent_c.getOneSM_LEVTOnR509(CreationTransition_c
								.getManySM_CRTXNsOnR507(Transition_c
										.getManySM_TXNsOnR506(smStates)));
						if ( assignedLocalEvent != null ) {
							Object noStateRow = new NoStateRow();
							rowData.add(noStateRow);
						}
						rowData.addAll( Arrays.asList(smStates) );
					}
					
					return rowData.toArray();
				}
				return null;
			}
		});
		fTableViewer.setLabelProvider(new ITableLabelProvider() {
		
			@Override
			public void removeListener(ILabelProviderListener listener) {
				// do nothing
			}
		
			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		
			@Override
			public void dispose() {
				// do nothing
			}
		
			@Override
			public void addListener(ILabelProviderListener listener) {
				// do nothing
			}
		
			@Override
			public String getColumnText(Object element, int columnIndex) {
				if(element instanceof StateMachineState_c) {
					if(columnIndex == 0) {
						// pack the column
						fTableViewer.getTable().getColumn(0).pack();
						return ((StateMachineState_c) element).getName();
					} else {
						final StateMachineEvent_c event = getEventAtColumn(columnIndex);
						final StateMachineState_c state = (StateMachineState_c) element;
						StateEventMatrixEntry_c matrixEntry = getMatrixEntry(state, event);
						if(matrixEntry != null) {
							NewStateTransition_c transition = NewStateTransition_c.getOneSM_NSTXNOnR504(matrixEntry);
							if(transition != null) {
								StateMachineState_c newState = StateMachineState_c
										.getOneSM_STATEOnR506(Transition_c
												.getOneSM_TXNOnR507(transition));
								return newState.getName();
							}
							CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(matrixEntry);
							if(ch != null) {
								return "Can't Happen";
							}
							EventIgnored_c ignored = EventIgnored_c.getOneSM_EIGNOnR504(matrixEntry);
							if(ignored != null) {
								return "Event Ignored";
							}
						}
					}
				} else if (element instanceof NoStateRow) {
					if(columnIndex == 0) {
						final String NoStateRowName = "";
						return NoStateRowName;
					} else {
						final StateMachineEvent_c event = getEventAtColumn(columnIndex);
						CreationTransition_c ct = CreationTransition_c
								.getOneSM_CRTXNOnR509(LocalEvent_c
										.getOneSM_LEVTOnR526(SemEvent_c
												.getOneSM_SEVTOnR525(event)));
						if (ct != null) {
							// If this is an assigned creation event then we
							// must show
							// the name of the state it is assigned to
							// Note: We do not allow editing of the creation
							// transition action.
							// so there is no editor for it.
							LocalEvent_c assignedEvent = LocalEvent_c
									.getOneSM_LEVTOnR509(ct);
							if (assignedEvent != null) {
								StateMachineState_c sms = StateMachineState_c
										.getOneSM_STATEOnR506(Transition_c
												.getOneSM_TXNOnR507(ct));
								return sms.getName();
							}
						}
						return "Can't Happen";
					}
				}
				return "";
			}
		
			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				if (element instanceof StateMachineState_c) {
					if(columnIndex == 0) {
						return CorePlugin.getImageFor(element);
					} else {
						final StateMachineEvent_c event = getEventAtColumn(columnIndex);
						final StateMachineState_c state = (StateMachineState_c) element;
						StateEventMatrixEntry_c matrixEntry = getMatrixEntry(state, event);
						if(matrixEntry != null) {
							NewStateTransition_c transition = NewStateTransition_c.getOneSM_NSTXNOnR504(matrixEntry);
							if(transition != null) {
								Transition_c superTrans = Transition_c.getOneSM_TXNOnR507(transition);
								Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c
										.getOneSM_AHOnR513(TransitionActionHome_c.getOneSM_TAHOnR530(superTrans)));
								if(action == null || action.getAction_semantics_internal().equals("")) {
									return IMAGE_OAL_NO_GEAR;
								}
								return IMAGE_EDIT_OAL;
							}
						}
					}
				} else if (element instanceof NoStateRow) {
					if (columnIndex == 0) {
						// The columns that represent state show the State.gif
						// icon, but this isn't a state. so it shows nothing
						return null;
					} else {
						// Note that since we do not allow editing of creation
						// transitions there is no icon.
						return null;
					}
				}
				return null;
			}
		});
		fTableViewer.setInput(fStateMachine);
	}

	private void addTableListeners() {
	 	fTableViewer.getTable().addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent e) {
				fTableViewer.getTable().setToolTipText("");
			}
		
			@Override
			public void mouseExit(MouseEvent e) {
				setCursor(fDefaultCursor);
			}
		
			@Override
			public void mouseEnter(MouseEvent e) {
			}
		});
	 	fTableViewer.getTable().addMouseMoveListener(new MouseMoveListener() {

			@Override
			public void mouseMove(MouseEvent e) {
				// if over an openable area, change cursor
				Point point = new Point(e.x, e.y);
				TableItem item = fTableViewer.getTable().getItem(point);
				if(item != null) {
			        for (int i = 0; i < fTableViewer.getTable().getColumnCount(); i++) {
			            Rectangle rect = item.getBounds(i);
			            if (rect.contains(point)) {
			            	StateMachineEvent_c event = getEventAtColumn(i);
			            	if(event == null) {
			            		setCursor(fOpenCursor);
			            		return;
			            	} else {
								StateEventMatrixEntry_c entry = getMatrixEntry((StateMachineState_c) item.getData(), event);
								if(entry != null) {
									// if this is a new state transition matrix entry
									// then open the transition activity editor
									NewStateTransition_c transition = NewStateTransition_c.getOneSM_NSTXNOnR504(entry);
									if(transition != null) {
										setCursor(fOpenCursor);
										return;
									}
								}
			            	}
			            }
			        }
				}
				setCursor(fDefaultCursor);
			}
		});
	 	fTableViewer.getTable().addMouseListener(new MouseListener() {
		
			@Override
			public void mouseUp(MouseEvent e) {
				// nothing to do
			}
		
			@Override
			public void mouseDown(MouseEvent e) {
				if(e.button == 1) {
					Object elementToOpen = null;
					Point point = new Point(e.x, e.y);
					TableItem item = fTableViewer.getTable().getItem(point);
					if(item != null) {
				        for (int i = 0; i < fTableViewer.getTable().getColumnCount(); i++) {
				            Rectangle rect = item.getBounds(i);
				            if (rect.contains(point)) {
				            	StateMachineEvent_c event = getEventAtColumn(i);
				            	if(event == null) {
				            		elementToOpen = item.getData();
				            	} else {
									StateEventMatrixEntry_c entry = getMatrixEntry((StateMachineState_c) item.getData(), event);
									if(entry != null) {
										// if this is a new state transition matrix entry
										// then open the transition activity editor
										NewStateTransition_c transition = NewStateTransition_c.getOneSM_NSTXNOnR504(entry);
										if(transition != null) {
											elementToOpen = Transition_c.getOneSM_TXNOnR507(transition);
										}
									}
				            	}
				            }
				        }
					}
					if(elementToOpen != null) {
				        openActivityDiagramFor(elementToOpen);
					}
				}
			}
		
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
	}

	protected void openActivityDiagramFor(final Object elementToOpen) {
        try
        {
          IWorkspaceRunnable r = new IWorkspaceRunnable()
          {
            public void run(IProgressMonitor monitor) throws CoreException
            {
                IStructuredSelection ss = new StructuredSelection(elementToOpen);
                ShowActivityAction sda = new ShowActivityAction();
                Action a = new Action(){};
                sda.selectionChanged(a, ss);
                sda.run( a );
            }
          };
          CorePlugin.getWorkspace().run(r, null);
        }
        catch (CoreException x)
        {
          CorePlugin.logError("Unable to open activity editor.", x);
        }
	}

	private void addModelChangeListeners() {
		fTransactionListener = new ITransactionListener() {
		
			@Override
			public void transactionStarted(Transaction transaction) {
				// do nothing
			}
		
			@Override
			public void transactionEnded(Transaction transaction) {
				if(transaction.getType().equals(Transaction.AUTORECONCILE_TYPE))
					return;
				final Transaction finalTransaction = transaction;
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				
					@Override
					public void run() {
						refresh(finalTransaction, false);
					}
				});
			}
		
			@Override
			public void transactionCancelled(Transaction transaction) {
				// do nothing
			}
		};
		semListener = new SemEditorListener(this);
		Ooaofooa.getDefaultInstance().addModelChangeListener(semListener);
		
		if ((fStateMachine != null)
				&& (fStateMachine.getModelRoot() instanceof Ooaofooa)
				&& (((Ooaofooa) fStateMachine.getModelRoot()).getRoot() != null)) {
			((Ooaofooa) fStateMachine.getModelRoot()).getRoot()
					.getTransactionManager().addTransactionListener(
							fTransactionListener);
		}
	}

	public void refresh(final Transaction transaction, boolean recreate) {
		if(fTableViewer.getTable().isDisposed())
			return;
		// refresh table, and columns
		if(transaction != null) {
			StateMachineEvent_c[] deletedEvents = getDeletedEvents(transaction);
			removeColumns(deletedEvents);
			StateMachineEvent_c[] addedEvents = getAddedEvents(transaction);
			addColumns(addedEvents);
		}
		if(recreate) {
			// we may need to update our cached state machine
			if(InstanceStateMachine_c.getOneSM_ISMOnR517(fStateMachine) == null) {
				fStateMachine = (StateMachine_c) fStateMachine.getModelRoot()
						.getInstanceList(StateMachine_c.class).get(
								fStateMachine.getInstanceKey());
			}
			events.clear();
			TableColumn[] columns = fTableViewer.getTable().getColumns();
			for(TableColumn column : columns) {
				column.dispose();
			}
			createInitialColumns(false);
		}
		refreshColumnNames();
		fTableViewer.refresh();
	}
	
	protected void refreshColumnNames() {
		for(int i = 0; i < fTableViewer.getTable().getColumnCount(); i++) {
			TableColumn column = fTableViewer.getTable().getColumn(i);
			if(column.getData() != null && column.getData() instanceof StateMachineEvent_c) {
				column.setText(((StateMachineEvent_c) column.getData()).Get_name());
			}
		}
	}

	protected void addColumns(StateMachineEvent_c[] addedEvents) {
		for(int i = 0; i < addedEvents.length; i++) {
			events.add(addedEvents[i]);
			createColumnForEvent(addedEvents[i]);
		}
	}

	protected void removeColumns(StateMachineEvent_c[] deletedEvents) {
		for(int i = 0; i < deletedEvents.length; i++) {
			for(int j = 1; j < fTableViewer.getTable().getColumnCount(); j++) {
				StateMachineEvent_c event = getEventAtColumn(j);
				if(event.equals(deletedEvents[i])) {
					fTableViewer.getTable().getColumn(j).dispose();
					events.remove(deletedEvents[i]);
				}
			}
		}
	}

	protected StateMachineEvent_c[] getAddedEvents(Transaction transaction) {
		ArrayList<StateMachineEvent_c> list = new ArrayList<StateMachineEvent_c>();
		IModelDelta[] deltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
		if(deltas != null) {
			for(int i = 0; i < deltas.length; i++) {
				if(deltas[i] instanceof BaseModelDelta) {
					if(deltas[i].getKind() == Modeleventnotification_c.DELTA_NEW) {
						if(deltas[i].getModelElement() instanceof StateMachineEvent_c) {
							StateMachineEvent_c event = (StateMachineEvent_c) deltas[i].getModelElement();
							StateMachine_c stateMachine = StateMachine_c.getOneSM_SMOnR502(event);
							if(stateMachine != null && stateMachine.equals(fStateMachine))
								list.add((StateMachineEvent_c) deltas[i].getModelElement());
						}
					}
				}
			}
		}
		return list.toArray(new StateMachineEvent_c[list.size()]);
	}

	protected StateMachineEvent_c[] getDeletedEvents(Transaction transaction) {
		ArrayList<StateMachineEvent_c> list = new ArrayList<StateMachineEvent_c>();
		IModelDelta[] deltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
		if(deltas != null) {
			for(int i = 0; i < deltas.length; i++) {
				if(deltas[i] instanceof BaseModelDelta) {
					if(deltas[i].getKind() == Modeleventnotification_c.DELTA_DELETE) {
						if(deltas[i].getModelElement() instanceof StateMachineEvent_c) {
							list.add((StateMachineEvent_c) deltas[i].getModelElement());
						}
					}
				}
			}
		}
		return list.toArray(new StateMachineEvent_c[list.size()]);
	}

	@Override
	public void dispose() {
		super.dispose();
		((Ooaofooa) fStateMachine.getModelRoot()).getRoot()
			.getTransactionManager().removeTransactionListener(fTransactionListener);
		Ooaofooa.getDefaultInstance().removeModelChangeListener(semListener);
	}

	protected StateEventMatrixEntry_c getMatrixEntry(final StateMachineState_c state,
			final StateMachineEvent_c event) {
		return fTableViewer.getMatrixEntryFor(state, event);
	}

	protected StateMachineEvent_c getEventAtColumn(int columnIndex) {
		if(columnIndex == 0) return null;
		return events.get(columnIndex - 1);
	}

	private void createInitialColumns(boolean pack) {
		setupInitialEvents();
		TableColumn blank = new TableColumn(fTableViewer.getTable(), SWT.CENTER);
		blank.setWidth(getWidthForStateColumn());
		// create a column for each event
		for(int i = 0; i < events .size(); i++) {
			createColumnForEvent(events.get(i));
		}
		if(pack) {
			fTableViewer.getTable().pack();
		}
	}

	private void createColumnForEvent(StateMachineEvent_c event) {
		TableViewerColumn column = new TableViewerColumn(fTableViewer, SWT.CENTER);
		column.setLabelProvider(new CellLabelProvider() {
		
			@Override
			public void update(ViewerCell cell) {
				ITableLabelProvider provider = (ITableLabelProvider) fTableViewer.getLabelProvider();
				cell.setText(provider.getColumnText(cell.getElement(), cell.getColumnIndex()));
				cell.setImage(provider.getColumnImage(cell.getElement(), cell.getColumnIndex()));
			}
		
		});
		column.setEditingSupport(new SEMEditingSupport(fTableViewer, column.getColumn()));
		column.getColumn().setData(event);
		column.getColumn().setText(event.Get_name());
		column.getColumn().pack();
	}

	private int getWidthForStateColumn() {
		return 80;
	}

	private void setupInitialEvents() {
		StateMachineEvent_c[] currentEvents = StateMachineEvent_c.getManySM_EVTsOnR502(fStateMachine);
		for(int i = 0; i < currentEvents.length; i++) {
			events.add(currentEvents[i]);
		}
	}

	public String getEditorName() {
		return "State Event Matrix Editor";
	}

	public StateMachine_c getStateMahine() {
		return fStateMachine;
	}
	
}
