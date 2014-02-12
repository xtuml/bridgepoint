package com.mentor.nucleus.bp.ui.sem.viewers;
//=====================================================================
//
// File:      $RCSfile: SEMFocusCellHighlighter.java,v $
// Version:   $Revision: 1.7 $
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

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class SEMFocusCellHighlighter extends FocusCellOwnerDrawHighlighter {

	public SEMFocusCellHighlighter(ColumnViewer viewer) {
		super(viewer);
		hookListener(viewer);
	}

	private void hookListener(final ColumnViewer viewer) {

		Listener listener = new Listener() {

			public void handleEvent(Event event) {
				if ((event.detail & SWT.SELECTED) > 0 || (event.detail & SWT.FocusOut) > 0) {
					ViewerCell focusCell = getFocusCell();
					if(focusCell != null) {
						ViewerRow row = focusCell.getViewerRow();
						ViewerCell cell = row.getCell(event.index);
						removeSelectionInformation(event, cell);
					}
				}
			}

		};
		viewer.getControl().addListener(SWT.EraseItem, listener);
	}
	
	private void removeSelectionInformation(Event event, ViewerCell cell) {
		GC gc = event.gc;
		gc.setBackground(cell.getViewerRow().getBackground(
				cell.getColumnIndex()));
		gc.setForeground(cell.getViewerRow().getForeground(
				cell.getColumnIndex()));
		gc.fillRectangle(cell.getBounds());
		// This is a workaround for an SWT-Bug on WinXP bug 169517
		gc.drawText(" ", cell.getBounds().x, cell.getBounds().y, false); //$NON-NLS-1$
		event.detail &= ~SWT.SELECTED;
	}
}
