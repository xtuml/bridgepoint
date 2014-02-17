package com.mentor.nucleus.bp.model.compare.providers;
//=====================================================================
//
//File:      $RCSfile: ModelCompareLabelProvider.java,v $
//Version:   $Revision: 1.6 $
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

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.ComparePlugin;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.IModelClassInspector;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.core.ui.cells.CellModifierProvider;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.inspector.GraphicalModelInspector;

public class ModelCompareLabelProvider extends BaseLabelProvider implements ITableLabelProvider, IStyledLabelProvider {
	
	@Override
	public Image getColumnImage(Object element, int index) {
		// only images for the first column for now
		if(index == 1) {
			return null;
		}
		if(element instanceof ComparableTreeObject) {
			element = ((ComparableTreeObject) element).getRealElement();
		}
		if (element instanceof ObjectElement) {
			ObjectElement objEle = (ObjectElement) element;
			if (objEle.getValue() instanceof NonRootModelElement) {
				return getImage(objEle.getValue());
			} else {
				return CorePlugin.getImageFor(
						Attribute_c.class.getSimpleName(), false);
			}
		}
		if(element instanceof NonRootModelElement) {
			IModelClassInspector inspector = new ModelInspector();
			if(((NonRootModelElement) element).getModelRoot() instanceof Ooaofgraphics) {
				inspector = new GraphicalModelInspector();
			}
			return inspector.getImage(element);
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int index) {
		if(element instanceof ComparableTreeObject) {
			ComparableTreeObject compObj = (ComparableTreeObject) element;
			if(!compObj.getText().equals("") && index == 0) {
				return compObj.getText();
			}
			element = compObj.getRealElement();
		}
		if (element instanceof ObjectElement) {
			ObjectElement objEle = (ObjectElement) element;
			if ((objEle.getName().equals("Descrip") || objEle.getName().equals("Action_Semantics")) && (((String) objEle.getValue()).length() > 30)) { //$NON-NLS-1$ //$NON-NLS-2$
				if(index == 0) {
					return ComparePlugin.getResourceString(stripPackageName(objEle
						.getParent())
						+ "." //$NON-NLS-1$
						+ "attribute_"
						+ objEle.getName() + ".longname");
				} else {
					String value = objEle.getValue().toString();
					// if any new lines exists, just get the first
					String[] split = new String[0];
					if(value.contains("\r\n")) { //$NON-NLS-1$
						split = value.split("\r\n"); //$NON-NLS-1$
					} else if(value.contains("\n")) { //$NON-NLS-1$
						split = value.split("\n"); //$NON-NLS-1$
					}
					if(split.length == 0) {
						if(value.length() > 50) {
							return value.substring(0, 50) + "...";
						} else {
							return value;
						}
					} else {
						if(split[0].length() > 50) {
							return split[0].substring(0, 50) + "...";
						} else {
							return split[0];
						}
					}
				}
			}
			if (objEle.getType() == ObjectElement.REFERENTIAL_ATTRIBUTE_ELEMENT) {
				NonRootModelElement nrmeElement = (NonRootModelElement) objEle
						.getValue();
				if (nrmeElement != null) {
					if (index == 0) {
						return ComparePlugin
								.getResourceString(stripPackageName(objEle
										.getParent())
										+ "." //$NON-NLS-1$
										+ objEle.getName() + ".longname");
					} else {
						NonRootModelElement refNrme = ((NonRootModelElement) objEle
								.getValue());
						return getColumnText(refNrme, 0);
					}
				}
			}
			if(objEle.getType() == ObjectElement.ATTRIBUTE_ELEMENT) {
				if(objEle.getValue() instanceof NonRootModelElement) {
					String name = Cl_c.Getpath(((NonRootModelElement) objEle
							.getValue()));
					if (index == 0) {
						return ComparePlugin
								.getResourceString(stripPackageName(objEle
										.getParent())
										+ "." //$NON-NLS-1$
										+ "attribute_"
										+ objEle.getName()
										+ ".longname");
					} else {
						if (objEle.getName().equals("represents")) {
							if (objEle.getParent() instanceof GraphicalElement_c) {
								return ((GraphicalElement_c) objEle.getParent()).Getcachedrepresentspath();
							}
							if (objEle.getParent() instanceof Model_c) {
								return ((Model_c) objEle.getParent()).Getcachedrepresentspath();
							}
						}
						return name;
					}
				}
				if (index == 0) {
					return ComparePlugin
							.getResourceString(stripPackageName(objEle
									.getParent())
									+ "." //$NON-NLS-1$
									+ "attribute_"
									+ objEle.getName()
									+ ".longname");
				} else {
					String result = CellModifierProvider.getValue((NonRootModelElement) objEle.getParent(), objEle);
					if(result != null) {
						return result;
					}
					return objEle.getValue().toString();
				}
			}
			if (index == 0) {
				return ComparePlugin.getResourceString(stripPackageName(objEle
						.getParent())
						+ "." //$NON-NLS-1$
						+ objEle.getName() + ".longname");
			} else {
				if(objEle.getValue() == null) {
					return "unset";
				}
				return objEle.getValue().toString();
			}
		} else if (element instanceof NonRootModelElement) {
			String name = getElementName((NonRootModelElement) element);
			if(index == 0)
				return name;
			return null;
		}
		return null;
	}

	private String getElementName(NonRootModelElement element) {
		// find the naming ObjectElement child
		IModelClassInspector inspector = new ModelInspector();
		if(element.getModelRoot() instanceof Ooaofgraphics) {
			inspector = new GraphicalModelInspector();
		}
		ObjectElement namingElement = null;
		ObjectElement[] children = inspector.getAttributes(element);
		for(int i = 0; i < children.length; i++) {
			if(children[i] == null) {
				continue;
			}
			if(children[i].getName().equals("Name")) { //$NON-NLS-1$
				namingElement = children[i];
				break;
			}
		}
		if(namingElement == null) {
			return element.getName();
		}
		return (String) namingElement.getValue();
	}

	protected static String stripPackageName(Object object) {
		String className = object.getClass().getName();

		int indexOfPoint = className.lastIndexOf('.');
		if (indexOfPoint >= 0)
			className = className.substring(indexOfPoint + 1);
		return className;
	}
	
	@Override
	public StyledString getStyledText(Object element) {
		return new StyledString(getColumnText(element, 0));
	}

	@Override
	public Image getImage(Object element) {
		return getColumnImage(element, 0);
	}
	
}
