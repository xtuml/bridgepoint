package com.mentor.nucleus.bp.model.compare.providers;
//=====================================================================
//
//File:      $RCSfile: TreeDifferenceLabelProvider.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:40 $
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

import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.mentor.nucleus.bp.model.compare.EmptyElement;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;

public class TreeDifferenceLabelProvider implements ILabelProvider {

	private ModelCompareLabelProvider modelLabelProvider = new ModelCompareLabelProvider();
	private CompareConfiguration configuration;
	private TreeDifferencer differencer;
	private LocalResourceManager resourceManager;

	public TreeDifferenceLabelProvider(CompareConfiguration configuration) {
		this.configuration = configuration;
	}

	public void setDifferencer(TreeDifferencer differencer) {
		this.differencer = differencer;
	}

	@Override
	public Image getImage(Object element) {
		if(differencer == null) {
			return null;
		}
		List<TreeDifference> differences = differencer.getDifferences(element,
				true);
		if (differences.isEmpty()) {
			differences = differencer.getDifferences(element, false);
		}
		if (differences.isEmpty()) {
			return modelLabelProvider.getColumnImage(element, 0);
		}
		TreeDifference difference = differences.get(0);
		if (difference.getElement() == null) {
			// exclude overlay as this is for a missing element
			// and will be represented differently
			return modelLabelProvider.getColumnImage(element, 0);
		}
		// if the difference's element is an EmptyElement
		if(difference.getElement() instanceof EmptyElement) {
			// set the element as the represented element
			element = ((EmptyElement) difference.getElement())
					.getRepresentedMissingElement();
		}
		int kind = difference.getKind();
		if ((Boolean) configuration.getProperty("LEFT_IS_LOCAL")) {
			switch (kind & Differencer.DIRECTION_MASK) {
			case Differencer.LEFT:
				kind= (kind &~ Differencer.LEFT) | Differencer.RIGHT;
				break;
			case Differencer.RIGHT:
				kind= (kind &~ Differencer.RIGHT) | Differencer.LEFT;
				break;
			}
		}
		Image image = modelLabelProvider.getColumnImage(element, 0);
		if(kind != Differencer.CHANGE) {
			Image overlay = configuration.getImage(kind);
			OverlayCompositeImageDescriptor descriptor = new OverlayCompositeImageDescriptor(
				image.getImageData(), overlay.getImageData());
			return getResourceManager().createImage(descriptor);
		}
		return image;
	}

	private ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(JFaceResources.getResources());
		}
		return resourceManager;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof EmptyElement) {
			return modelLabelProvider.getColumnText(
					((EmptyElement) element).getRepresentedMissingElement(), 0);
		}
		return modelLabelProvider.getColumnText(element, 0);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// do nothing
	}

	@Override
	public void dispose() {
		modelLabelProvider.dispose();
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// do nothing
	}

}
