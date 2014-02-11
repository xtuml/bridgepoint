package com.mentor.nucleus.bp.ui.sem.viewers;
//=====================================================================
//
// File:      $RCSfile: SEMEditingSupport.java,v $
// Version:   $Revision: 1.6 $
// Modified:  $Date: 2013/01/10 23:43:56 $
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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.widgets.TableColumn;

import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;

public class SEMEditingSupport extends EditingSupport {

	private TableColumn fColumn;
	private ComboBoxCellEditor fComboBoxEditor;
	private String[] fEditorValues = new String[] {"Can't Happen", "Event Ignored"}; 

	public SEMEditingSupport(ColumnViewer viewer, TableColumn column) {
		super(viewer);
		fColumn = column;
		SEMTableViewer tableViewer = (SEMTableViewer) viewer;
		fComboBoxEditor = new ComboBoxCellEditor(tableViewer.getTable(),
				fEditorValues);
	}

	@Override
	protected boolean canEdit(Object element) {
		ColumnViewer columnViewer = getViewer();
		if(columnViewer instanceof SEMTableViewer) {
			SEMTableViewer tableViewer = (SEMTableViewer) columnViewer;
			StateEventMatrixEntry_c entry = tableViewer.getMatrixEntryFor(
					(StateMachineState_c) element, (StateMachineEvent_c) fColumn.getData());
			if(entry != null) {
				NewStateTransition_c transition = NewStateTransition_c
						.getOneSM_NSTXNOnR504(entry);
				if(transition == null) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		ColumnViewer columnViewer = getViewer();
		if(columnViewer instanceof SEMTableViewer) {
			SEMTableViewer tableViewer = (SEMTableViewer) columnViewer;
			StateEventMatrixEntry_c entry = tableViewer.getMatrixEntryFor(
					(StateMachineState_c) element, (StateMachineEvent_c) fColumn.getData());
			if(entry != null) {
				NewStateTransition_c transition = NewStateTransition_c
						.getOneSM_NSTXNOnR504(entry);
				if(transition == null) {
					return fComboBoxEditor;
				}
			}
		}
		return null;
	}

	@Override
	protected Object getValue(Object element) {
		SEMTableViewer tableViewer = (SEMTableViewer) getViewer();
		StateEventMatrixEntry_c entry = tableViewer.getMatrixEntryFor(
				(StateMachineState_c) element, (StateMachineEvent_c) fColumn.getData());
		if(entry != null) {
			NewStateTransition_c transition = NewStateTransition_c
					.getOneSM_NSTXNOnR504(entry);
			if(transition == null) {
				CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(entry);
				if(ch != null) {
					return 0;
				} else {
					return 1;
				}
			}
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		SEMTableViewer tableViewer = (SEMTableViewer) getViewer();
		StateEventMatrixEntry_c entry = tableViewer.getMatrixEntryFor(
				(StateMachineState_c) element, (StateMachineEvent_c) fColumn.getData());
		if(entry != null) {
			NewStateTransition_c transition = NewStateTransition_c
					.getOneSM_NSTXNOnR504(entry);
			if(transition == null) {
				CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(entry);
				if(ch != null) {
					if(value.equals(Integer.valueOf(0))) {
						return;
					}
				} else {
					if(value.equals(Integer.valueOf(1))) {
						return;
					}
				}
				// a change was made, change the matrix entry
				StateMachineEvent_c event = (StateMachineEvent_c) fColumn.getData();
				StateMachineState_c state = (StateMachineState_c) element;
				Transaction transaction = null;
				TransactionManager manager = state.getTransactionManager();
				try {
					transaction = manager.startTransaction("Change matrix entry", Ooaofooa.getDefaultInstance());
					if(ch != null) {
						event.Ignoreonstate(state.getSmstt_id(), state
								.getSm_id());
					} else {
						event.Saycanthappenonstate(state.getSmstt_id(), state
								.getSm_id());
					}
				} catch (TransactionException e) {
					CorePlugin.logError("Unable to start transaction.", e);
				} finally {
					if(transaction != null) {
						manager.endTransaction(transaction);
					}
				}
			}
		}
	}

}
