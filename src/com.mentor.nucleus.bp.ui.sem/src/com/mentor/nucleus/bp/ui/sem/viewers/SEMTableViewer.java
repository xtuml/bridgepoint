package com.mentor.nucleus.bp.ui.sem.viewers;
//=====================================================================
//
// File:      $RCSfile: SEMTableViewer.java,v $
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

import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

public class SEMTableViewer extends TableViewer {

	public SEMTableViewer(Composite parent, int style) {
		super(parent, style);
		getTable().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		getTable().setHeaderVisible(true);
		getTable().setLinesVisible(true);
		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(this, new SEMFocusCellHighlighter(this));
		ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(this) {
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
		
		TableViewerEditor.create(this, focusCellManager, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
				| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
				| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);

	}

	public StateEventMatrixEntry_c getMatrixEntryFor(final StateMachineState_c state, final StateMachineEvent_c event) {
		StateEventMatrixEntry_c matrixEntry = StateEventMatrixEntry_c.getOneSM_SEMEOnR503(SemEvent_c.getOneSM_SEVTOnR525(event), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				StateEventMatrixEntry_c entry = (StateEventMatrixEntry_c) candidate;
				return entry.getSmevt_id().equals(event.getSmevt_id()) &&
							entry.getSmstt_id().equals(state.getSmstt_id());
			}
		});
		return matrixEntry;
	}

	
}
