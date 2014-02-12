//=====================================================================
//
//File:      $RCSfile: ModelMergeViewerContentProvider.java,v $
//Version:   $Revision: 1.13 $
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

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.internal.MergeViewerContentProvider;
import org.eclipse.compare.structuremergeviewer.ICompareInput;

import com.mentor.nucleus.bp.compare.ComparePlugin;
import com.mentor.nucleus.bp.compare.ModelCacheManager;
import com.mentor.nucleus.bp.compare.ModelCacheManager.ModelLoadException;
import com.mentor.nucleus.bp.compare.structuremergeviewer.ModelCompareStructureCreator.CompareDocumentRangeNode;
import com.mentor.nucleus.bp.core.CorePlugin;

public class ModelMergeViewerContentProvider extends MergeViewerContentProvider {

	public static HashMap<Object, ModelMergeViewerContentProvider> activeInputs = new HashMap<Object, ModelMergeViewerContentProvider>();
	ModelCacheManager modelCacheManager = ComparePlugin.getDefault().getModelCacheManager();
	
	private List<Object> inputList = new Vector<Object>(); 
	
	public ModelMergeViewerContentProvider(
		CompareConfiguration cc) {
		super(cc);
		
	}
	
	public void releaseAll() {
		for (int i=0; i < inputList.size(); i++){
			modelCacheManager.releaseModel(inputList.get(i), this);
			activeInputs.remove(inputList.get(i));
		}
		inputList.clear();
	}
	
	private void addInput(ITypedElement aInput){
		inputList.add(aInput);
		activeInputs.put(aInput, this);
	}

	public Object getAncestorContent(Object input) {
		if (input != null)
			if (input instanceof ICompareInput) {
				ITypedElement ancestor = ((ICompareInput) input).getAncestor();
				if (ancestor != null){
					return getModelFromCache(ancestor);
				}
					
			}

		return null;
	}

	public Object getLeftContent(Object input) {
		if (input != null)
			if (input instanceof ICompareInput) {
				ITypedElement left = ((ICompareInput) input).getLeft();
				if (left != null){
					return getModelFromCache(left);
				}
					
			}
		return null;
	}

	public Object getRightContent(Object input) {
		if (input != null)
			if (input instanceof ICompareInput) {
				ITypedElement right = ((ICompareInput) input).getRight();
				if (right != null){
					return getModelFromCache(right);
				}									
			}

		return null;
	}

	protected Object getModelFromCache(Object input) {
		if(input instanceof CompareDocumentRangeNode){
			return input;
		}
		
		
		try {
			CompareDocumentRangeNode modelNode = (CompareDocumentRangeNode)modelCacheManager.getModel(input, this);
			addInput((ITypedElement)input);
			if (modelNode != null)
				return modelNode.getDocument();
		} catch (ModelLoadException e) {
			CorePlugin.showImportErrorMessage(false, e.getMessage());
		}
		return null;
	}
}
