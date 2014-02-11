//=====================================================================
//
//File:      $RCSfile: ModelContentMergeViewer.java,v $
//Version:   $Revision: 1.14 $
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

import java.util.ResourceBundle;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.compare.ComparePlugin;
import com.mentor.nucleus.bp.compare.ICompareHelpContextIds;
import com.mentor.nucleus.bp.compare.structuremergeviewer.ModelCompareStructureCreator.CompareDocumentRangeNode;

public class ModelContentMergeViewer extends Viewer {
    
    // Adding the appropriate Font Registry entry to be used by
    // InternalTextMergeViewer
    static {
        Font font = JFaceResources.getFont(TextMergeViewer.class.getName());
        JFaceResources.getFontRegistry().put(
                InternalTextMergeViewer.class.getName(), font.getFontData());
    }

    private InternalTextMergeViewer internalViewer;
    private ModelMergeViewerContentProvider contentProvider;
    public Object originalInput;

    ModelContentMergeViewer(Composite parent, CompareConfiguration configuration) {

        configuration.setLeftEditable(false);
        configuration.setRightEditable(false);
        internalViewer = new InternalTextMergeViewer(parent, configuration);
        // re-enable the write state
        configuration.setLeftEditable(true);
        configuration.setRightEditable(true);

        contentProvider = new ModelMergeViewerContentProvider(configuration);
        internalViewer.setContentProvider(contentProvider);
    }

    public Control getControl() {
        return internalViewer.getControl();
    }

    public void setInput(Object aInput) {
        if(aInput instanceof DiffNode) {
            Object diffObject = ((DiffNode) aInput).getLeft();
            if(diffObject == null) {
                diffObject = ((DiffNode) aInput).getRight();
            }
            if(!(diffObject instanceof CompareDocumentRangeNode)) {
                originalInput = aInput;
            }
        } else {
            originalInput = aInput;
        }
        internalViewer.setInput(aInput);
    }

    public Object getInput() {
        return originalInput;
    }

    public ISelection getSelection() {
        return internalViewer.getSelection();
    }

    public void setSelection(ISelection s, boolean reveal) {
        internalViewer.setSelection(s, reveal);
    }

    public void refresh() {
        internalViewer.refresh();
    }

    class InternalTextMergeViewer extends TextMergeViewer {

        InternalTextMergeViewer(Composite aParent,
                CompareConfiguration configuration) {
            super(aParent, configuration);
        }
        
        @Override
        protected ResourceBundle getResourceBundle() {
            // need to use our own bundle here for label configuration
            return ComparePlugin.getDefault().getResourceBundle();
        }

        @Override
        protected void setEditable(ISourceViewer sourceViewer, boolean state) {
            sourceViewer.setEditable(false);
        }

        protected void handleDispose(DisposeEvent event){
           contentProvider.releaseAll();
           super.handleDispose(event);  
       }
       
        public void createControls(Composite parent) {
            super.createControls(parent);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(
                    super.getControl(),
                    ICompareHelpContextIds.modelCompareTextId);
        }
    }
}
