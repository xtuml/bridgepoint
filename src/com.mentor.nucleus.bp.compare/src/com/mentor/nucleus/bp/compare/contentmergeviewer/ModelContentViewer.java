//=====================================================================
//
//File:      $RCSfile: ModelContentViewer.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:49:46 $
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
package com.mentor.nucleus.bp.compare.contentmergeviewer;

import java.util.List;
import java.util.Vector;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.contentmergeviewer.IDocumentRange;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.mentor.nucleus.bp.compare.ComparePlugin;
import com.mentor.nucleus.bp.compare.ModelCacheManager;
import com.mentor.nucleus.bp.compare.ModelCacheManager.ModelLoadException;
import com.mentor.nucleus.bp.core.CorePlugin;

public class ModelContentViewer extends Viewer {

	private ModelCacheManager modelManager = ComparePlugin.getDefault().getModelCacheManager();

	private SourceViewer fSourceViewer;
	private List inputs = new Vector();
	Object input;

	ModelContentViewer(Composite parent) {
		fSourceViewer = new SourceViewer(parent, null, SWT.H_SCROLL + SWT.V_SCROLL);
		Control control = fSourceViewer.getTextWidget();
		Font font = JFaceResources.getFont(TextMergeViewer.class.getName());
		control.setFont(font);
		control.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				handleDispose();
			}
		});
		fSourceViewer.setEditable(false);
	}

	public Control getControl() {
		return fSourceViewer.getTextWidget();
	}

	public void setInput(Object aInput) {
		if (aInput != null) {
			inputs.add(aInput);

			input = aInput;
			try {
				IStreamContentAccessor modelText = modelManager.getModel(aInput, this);

				if (modelText instanceof IDocumentRange) {
					fSourceViewer.setDocument(((IDocumentRange) modelText).getDocument());
				}

			} catch (ModelLoadException e) {
				CorePlugin.showImportErrorMessage(false, e.getMessage());
			}
		}
	}

	public Object getInput() {
		return input;
	}

	public ISelection getSelection() {
		return fSourceViewer.getSelection();
	}

	public void setSelection(ISelection s, boolean reveal) {
		fSourceViewer.setSelection(s, reveal);
	}

	public void refresh() {
		fSourceViewer.refresh();
	}

	protected void handleDispose() {
		for (int i = 0; i < inputs.size(); i++) {
			modelManager.releaseModel(inputs.get(i), this);
		}
		inputs.clear();
	}

}
