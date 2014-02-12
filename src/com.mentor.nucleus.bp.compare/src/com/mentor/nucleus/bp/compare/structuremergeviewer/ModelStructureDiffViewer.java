//=====================================================================
//
//File:      $RCSfile: ModelStructureDiffViewer.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 22:49:52 $
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
package com.mentor.nucleus.bp.compare.structuremergeviewer;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.internal.ComparePreferencePage;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.compare.structuremergeviewer.StructureDiffViewer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.compare.ComparePlugin;
import com.mentor.nucleus.bp.compare.ICompareHelpContextIds;
import com.mentor.nucleus.bp.compare.ModelCacheManager;
import com.mentor.nucleus.bp.compare.contentmergeviewer.ModelMergeViewerContentProvider;
import com.mentor.nucleus.bp.compare.structuremergeviewer.ModelCompareStructureCreator.CompareDocumentRangeNode;
import com.mentor.nucleus.bp.core.inspector.IModelInspectorRegistry;

public class ModelStructureDiffViewer extends StructureDiffViewer {

    private List<Object> inputList = new Vector<Object>();
    private static HashMap<CompareConfiguration, ModelStructureDiffViewer> viewerMap = new HashMap<CompareConfiguration, ModelStructureDiffViewer>();
    ModelCacheManager modelManager = ComparePlugin.getDefault().getModelCacheManager();
    private ICompareInputChangeListener inputChangeListener;

    public ModelStructureDiffViewer(Composite parent, CompareConfiguration configuration) {
        super(parent, configuration);

        IModelInspectorRegistry registry = modelManager.getModelInspectorRegistry();
        setStructureCreator(new ModelCompareStructureCreator(registry, this));

        addFilter(new TreeElementFilter());
        setSorter(new ModelStructureDiffViewerSorter());
        
        PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getTree(), ICompareHelpContextIds.modelCompareId);
        viewerMap.put(configuration, this);
        inputChangeListener = new ICompareInputChangeListener() {
            
            @Override
            public void compareInputChanged(ICompareInput source) {
                modelManager.releaseModel(((ICompareInput) getInput()).getLeft());
                modelManager.releaseModel(((ICompareInput) getInput()).getRight());
                modelManager.releaseModel(((ICompareInput) getInput()).getAncestor());
            }
        };
    }

    private void releaseInput(ICompareInput compareInput) {
        if (compareInput != null) {
            compareInput.removeCompareInputChangeListener(inputChangeListener);
            Object input = compareInput.getAncestor();
            if (input != null) {
                modelManager.releaseModel(input, this);
            }
            input = compareInput.getLeft();
            if (input != null) {
                modelManager.releaseModel(input, this);
            }
            input = compareInput.getRight();
            if (input != null) {
                modelManager.releaseModel(input, this);
            }
        }
    }

    protected void handleDispose(DisposeEvent event) {
        for (int i = 0; i < inputList.size(); i++) {
            releaseInput((ICompareInput) inputList.get(i));
        }
        inputList.clear();
        super.handleDispose(event);
        viewerMap.remove(getCompareConfiguration());
    }

    @Override
    protected void handleDoubleSelect(SelectionEvent event) {
        if(!shouldProcessOpen()) {
            return;
        }
        super.handleDoubleSelect(event);
    }

    @Override
    protected void handleOpen(SelectionEvent event) {
        if(!shouldProcessOpen()) {
            return;
        }
        super.handleOpen(event);
    }

    @Override
    protected void handleSelect(SelectionEvent event) {
        IPreferenceStore ps= getCompareConfiguration().getPreferenceStore();
        if (ps != null && ps.getBoolean(ComparePreferencePage.OPEN_STRUCTURE_COMPARE)) {
            // single click compare changing allowed
            if(!shouldProcessOpen()) {
                return;
            }
        }
        super.handleSelect(event);
    }

    private boolean shouldProcessOpen() {
        // do not process event if the current compare viewer
        // is text, as it cannot handle the selection as input
        if(getInput() instanceof ICompareInput) {
            ICompareInput input = (ICompareInput) getInput();
            ITypedElement left = input.getLeft();
            ITypedElement right = input.getRight();
            ModelMergeViewerContentProvider active = ModelMergeViewerContentProvider.activeInputs.get(left);
            if(active == null) {
                active = ModelMergeViewerContentProvider.activeInputs.get(right);
            }
            if(active == null) {
                // do not process
                return false;
            }
        }
        return true;
    }

    protected void inputChanged(Object input, Object oldInput) {
        if (input != null) {
            if (input instanceof ICompareInput) {
                inputList.add(input);
                ((ICompareInput) input).addCompareInputChangeListener(inputChangeListener);
                if(oldInput != null) {
                    ((ICompareInput) oldInput).removeCompareInputChangeListener(inputChangeListener);
                }
            }
            super.inputChanged(input, oldInput);
        }
    }

    public boolean isExpandable(Object element) {
        if (element instanceof DiffNode) {
            Object children[] = ((DiffNode) element).getChildren();
            if (children != null && children.length == 1) {
                    String id =
                        (((CompareDocumentRangeNode) ((DiffNode) children[0])
                            .getId()))
                            .getUniqueId();
                    if (ModelCacheManager.BODY_ID.equals(id)) {
                        return false;
                    }
            }
        }
        return super.isExpandable(element);
    }

    static class TreeElementFilter extends ViewerFilter {

        public boolean select(Viewer viewer, Object parentElement, Object element) {
            if (parentElement != null && element instanceof DiffNode) {
                Object node = ((DiffNode) element).getId();
                if (node instanceof CompareDocumentRangeNode) {
                    String id = ((CompareDocumentRangeNode) node).getUniqueId();
                    if (ModelCacheManager.BODY_ID.equals(id)){
                        return false;
                    }
                }
            }
            return true;
        }
    }
    
    static class ModelStructureDiffViewerSorter extends ViewerSorter{
    
        public int category(Object element) {
            if (element instanceof DiffNode){
                DiffNode diffNode = (DiffNode)element;
                ITypedElement node = diffNode.getLeft();
                if(node == null){
                    node = diffNode.getRight();
                }
                if(node == null){
                    node = diffNode.getAncestor();
                }
                
                if(node != null && node instanceof CompareDocumentRangeNode){
                    return ((CompareDocumentRangeNode)node).getSortingIndex();
                }
            }
            return super.category(element);
        }
    }

    public static ModelStructureDiffViewer getViewer(CompareConfiguration config) {
        return viewerMap.get(config);
    }
}
