package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: TextualAttributeCompareEditorInput.java,v $
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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.ICompareInputLabelProvider;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mentor.nucleus.bp.model.compare.providers.ModelCompareContentProvider;
import com.mentor.nucleus.bp.model.compare.providers.ModelCompareLabelProvider;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public class TextualAttributeCompareEditorInput extends CompareEditorInput {

	private ObjectElement leftElement;
	private ObjectElement rightElement;
	private ObjectElement ancestor;
	private SynchronizedTreeViewer viewer;
	private CompareConfiguration configuration;

	public TextualAttributeCompareEditorInput(
			CompareConfiguration configuration, ObjectElement leftElement,
			ObjectElement rightElement, ObjectElement ancestor,
			SynchronizedTreeViewer viewer) {
		super(configuration);
		this.leftElement = leftElement;
		this.rightElement = rightElement;
		this.ancestor = ancestor;
		this.viewer = viewer;
		this.configuration = configuration;
	}

	@Override
	public String getOKButtonLabel() {
		return "&Close";
	}

	@Override
	public boolean isSaveNeeded() {
		return true;
	}

	@Override
	protected Object prepareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		TextualAttributeCompareInput textualAttributeCompareInput = new TextualAttributeCompareInput();
		getCompareConfiguration().setLabelProvider(
				textualAttributeCompareInput, new ICompareInputLabelProvider() {

					private ITableLabelProvider labelProvider = new ModelCompareLabelProvider();

					@Override
					public void removeListener(ILabelProviderListener listener) {
						// no listeners
					}

					@Override
					public boolean isLabelProperty(Object element,
							String property) {
						return false;
					}

					@Override
					public void dispose() {
						// nothing to dispose
					}

					@Override
					public void addListener(ILabelProviderListener listener) {
						// no listeners necessary
					}

					@Override
					public String getText(Object element) {
						if (leftElement == null) {
							String path = getPath(rightElement);
							if (path.equals("")) {
								return labelProvider.getColumnText(rightElement
										.getParent(), 0);
							}
							return path
									+ "::"
									+ labelProvider.getColumnText(rightElement
											.getParent(), 0);
						}
						String path = getPath(leftElement);
						if(path.equals("")) {
							return labelProvider.getColumnText(leftElement
									.getParent(), 0);
						}
						return path
								+ "::"
								+ labelProvider
										.getColumnText(leftElement.getParent(), 0);
					}

					@Override
					public Image getImage(Object element) {
						if (leftElement == null) {
							return labelProvider.getColumnImage(rightElement
									.getParent(), 0);
						}
						return labelProvider.getColumnImage(leftElement.getParent(), 0);
					}

					@Override
					public String getRightLabel(Object input) {
						if (input == null) {
							return getText(input);
						}
						return getText(input) + " ("
								+ getRevision(false, false) + ")";
					}

					@Override
					public Image getRightImage(Object input) {
						return getImage(input);
					}

					@Override
					public String getLeftLabel(Object input) {
						if (input == null) {
							return getText(input);
						}
						return getText(input) + " (" + getRevision(true, false)
								+ ")";
					}

					@Override
					public Image getLeftImage(Object input) {
						return getImage(input);
					}

					@Override
					public String getAncestorLabel(Object input) {
						if (input == null) {
							return getText(input);
						}
						return getText(input) + " (" + getRevision(false, true)
								+ ")";
					}

					@Override
					public Image getAncestorImage(Object input) {
						return getImage(input);
					}
				});
		return textualAttributeCompareInput;
	}

	protected String getRevision(boolean isLeft, boolean isAncestor) {
		Object input = viewer.getMergeViewer().getInput();
		CompareConfiguration configuration = viewer.getMergeViewer().getConfiguration();
		String leftLabel = configuration.getLabelProvider().getLeftLabel(input);
		String rightLabel = configuration.getLabelProvider().getRightLabel(input);
		String ancestorLabel = configuration.getLabelProvider().getAncestorLabel(input);
		if (isLeft) {
			if(leftElement == null) {
				return "Not Present";
			}
			return leftLabel;
		} else {
			if(isAncestor) {
				if(ancestor == null) {
					return "Not Present";
				}
				return ancestorLabel;
			} else {
				if(rightElement == null) {
					return "Not Present";
				}
				return rightLabel;
			}
		}
	}

	protected String getPath(ObjectElement element) {
		ModelCompareContentProvider contProvider = new ModelCompareContentProvider();
		// start with the element's parent's parent
		NonRootModelElementComparable parentComparable = (NonRootModelElementComparable) contProvider
				.getParent(element.getParent());
		NonRootModelElement parent = null;
		if(parentComparable != null) {
			parent = (NonRootModelElement) parentComparable.getRealElement();
		}
		String path = "";
		if(parent != null) {
			path = parent.getName();
		}
		while (parent != null) {
			parentComparable = (NonRootModelElementComparable) contProvider.getParent(parent);
			if(parentComparable != null) {
				parent = (NonRootModelElement) parentComparable.getRealElement();
			} else {
				parent = null;
			}
			if(parent != null) {
				path = parent.getName() + "::" + path;
			}
		}
		return path;
	}

	public class TextualAttributeCompareInput implements ICompareInput {

		@Override
		public void addCompareInputChangeListener(
				ICompareInputChangeListener listener) {
			// no listeners to be added
		}

		@Override
		public void copy(boolean leftToRight) {
			// we do not need to do anything here
		}

		@Override
		public ITypedElement getAncestor() {
			if(ancestor == null) {
				return null;
			}
			return new TextualAttributeCompareElementType(ancestor, viewer, false, null, configuration);
		}

		@Override
		public Image getImage() {
			if(leftElement == null) {
				return CorePlugin.getImageFor(rightElement.getParent());
			}
			return CorePlugin.getImageFor(leftElement.getParent());
		}

		@Override
		public int getKind() {
			if(ancestor == null) {
				return Differencer.CHANGE_TYPE_MASK;
			}
			return Differencer.DIRECTION_MASK;
		}

		@Override
		public ITypedElement getLeft() {
			return new TextualAttributeCompareElementType(leftElement, viewer, true, rightElement, configuration);
		}

		@Override
		public String getName() {
			if(leftElement == null) {
				return rightElement.getName();
			}
			return leftElement.getName();
		}

		@Override
		public ITypedElement getRight() {
			return new TextualAttributeCompareElementType(rightElement, viewer, false, leftElement, configuration);
		}

		@Override
		public void removeCompareInputChangeListener(
				ICompareInputChangeListener listener) {
			// no listeners to be added
		}
		
	}
}
