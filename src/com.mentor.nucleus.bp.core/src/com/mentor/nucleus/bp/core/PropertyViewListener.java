//=====================================================================
//
//File:      $RCSfile: PropertyViewListener.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/01/17 03:38:40 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.Transaction;

public class PropertyViewListener implements ITransactionListener {

	public void transactionCancelled(Transaction transaction) {
		// do nothing
	}

	public void transactionStarted(Transaction transaction) {
		// do nothing
	}

	public void transactionEnded(Transaction transaction) {
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (int i = 0; i < windows.length; ++i) {
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int j = 0; j < pages.length; ++j) {
				IViewReference[] views = pages[j].getViewReferences();
				for (int k = 0; k < views.length; ++k) {
					if (views[k].getPart(false) instanceof PropertySheet) {
						final PropertySheet ps = (PropertySheet) views[k]
								.getPart(false);
						if(ps.getCurrentPage() instanceof PropertySheetPage) {
						final PropertySheetPage psp = (PropertySheetPage) ps
								.getCurrentPage();

						Display d2 = PlatformUI.getWorkbench().getDisplay();
						if (d2 == null) {
							d2 = Ooaofooa.m_display;
						}
						if (d2 != null) {
							// always run async to prevent infinite loops
							// when the update comes from the property view
							d2.asyncExec(new Runnable() {
								public void run() {
									if (!psp.getControl().isDisposed())
										psp.refresh();
								}
							});
						}
						break;
					}
				}
			}
		}
	}
}
}
