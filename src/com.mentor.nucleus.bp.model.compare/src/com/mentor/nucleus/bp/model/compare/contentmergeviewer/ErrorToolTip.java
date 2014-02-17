package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: ErrorToolTip.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:34 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ErrorToolTip {

	private Label label;
	private Shell tip;

	public ErrorToolTip(Shell shell) {
		tip = new Shell(shell, SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
		RowLayout layout = new RowLayout();
		tip.setLayout(layout);
		label = new Label(tip, SWT.NONE);
		label.setForeground(tip.getDisplay().getSystemColor(SWT.COLOR_RED));
		label.setBackground(tip.getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_BACKGROUND));
	}

	public void dispose() {
		label.dispose();
		tip.dispose();
	}

	public void setText(String text) {
		this.label.setText(text);
	}

	public void setVisible(boolean value) {
		tip.setVisible(value);
	}

	public void setLocation(int x, int y) {
		tip.setLocation(x, y);
	}

	public void autoSize() {
		tip.setSize(tip.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public int getHeight() {
		return tip.getSize().y;
	}

}
