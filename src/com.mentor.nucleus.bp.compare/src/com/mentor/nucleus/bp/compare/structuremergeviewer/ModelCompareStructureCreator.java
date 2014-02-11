//=====================================================================
//
//File:      $RCSfile: ModelCompareStructureCreator.java,v $
//Version:   $Revision: 1.19 $
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

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DocumentRangeNode;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.compare.structuremergeviewer.IStructureCreator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.swt.graphics.Image;

import com.mentor.nucleus.bp.compare.ComparePlugin;
import com.mentor.nucleus.bp.compare.ModelCacheManager;
import com.mentor.nucleus.bp.compare.ModelCacheManager.ModelLoadException;
import com.mentor.nucleus.bp.compare.text.ModelClassTextGenerator;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.inspector.IModelInspectorRegistry;

/**
 * 
 * Encapsulates the structure creation  facility, to be provided to 
 * the StructureMergeViwer
 */
public class ModelCompareStructureCreator
	extends ModelClassTextGenerator
	implements IStructureCreator {

	ModelStructureDiffViewer diffViewer;
	ModelCacheManager modelManager = ComparePlugin.getDefault().getModelCacheManager();

	public ModelCompareStructureCreator(
		IModelInspectorRegistry aRegistry,
		ModelStructureDiffViewer aDiffViewer) {
		super(aRegistry);
		diffViewer = aDiffViewer;
	}

	public String getName() {
		return ComparePlugin.getResourceString("ComparePlugin.MainWindow.Title"); //$NON-NLS-1$
	}

	public IStructureComparator getStructure(Object input) {
		//Load the sql input using the io.sql
		//oldInput
		try {
			return (IStructureComparator) modelManager.getModel(input, diffViewer);
		} catch (ModelLoadException e) {
			CorePlugin.showImportErrorMessage(false, e.getMessage());
		}

		return null;
	}

	public IStructureComparator locate(Object path, Object input) {
		// NOT NEEDED for simple compare
		return null;
	}

	public String getContents(Object node, boolean ignoreWhitespace) {
		if (node instanceof CompareDocumentRangeNode) {
			return ((CompareDocumentRangeNode) node).getStringContents();
		}
		return null;
	}

	/* 
	 * IStructureCreator#save(IStructureComparator, Object)
	 */
	public void save(IStructureComparator node, Object input) {
		//NOT NEEDED for simple compare
	}

	public static class CompareDocumentRangeNode
		extends DocumentRangeNode
		implements ITypedElement {
		String type;
		String name;
		int index = 0;
		int sortingIndex = -1;
		
		boolean includeIndexInHash = false;
		public CompareDocumentRangeNode(
		    String id,
		    String aName,
			String aType,
			IDocument document,
			int start,
			int length) {			 
			super(0, id, document, (document.getLength() == 0 ? 0 : start), (document.getLength() == 0 ? 0 : length));
			type = aType;
			name = aName;
		}
		
		public String getId() {
			return getName();
		}
		
		public String getUniqueId(){
			return super.getId();
		}

		public String getName() {
			return name;
		}

		public Image getImage() {
			return CorePlugin.getImageFor(type, true);
		}

		public String getType() {
			return "xtuml"; //$NON-NLS-1$
		}
		
		public String getModelElementClassName(){
			return type;
		}

		public String getStringContents() {
			String s;
			try {
				Position fRange = getRange();
				s = super.getDocument().get(fRange.getOffset(), fRange.getLength());
			} catch (BadLocationException ex) {
				throw new Error(ex);
			}
			return s;
		}
		
		public int getIndex(){
			return index;
		}
		
		public void setIndex(int aIndex){
			index = aIndex;
		}
		
		public void setIncludeIndexInHash(boolean toInclude){
			includeIndexInHash = toInclude;
		}

		public int hashCode() {
			return includeIndexInHash ? (super.getId() + index + "").hashCode() : super.getId().hashCode(); //$NON-NLS-1$
		}
		
		public boolean hasSortingIndex(){
			return sortingIndex >= 0;
		}
		
		public void setSortingIndex(int index){
			sortingIndex = index;
		}
		
		public int getSortingIndex(){
			return sortingIndex;
		}
		
		public String toString(){
			return "{ID:" + super.getId() + " }{NAME:" + getName() + " }{type:" + type + "}"; //$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		}
		
	}
}
