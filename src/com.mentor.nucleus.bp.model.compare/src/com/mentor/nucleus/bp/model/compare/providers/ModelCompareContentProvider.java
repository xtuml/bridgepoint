package com.mentor.nucleus.bp.model.compare.providers;
//=====================================================================
//
//File:      $RCSfile: ModelCompareContentProvider.java,v $
//Version:   $Revision: 1.5.14.1 $
//Modified:  $Date: 2013/07/08 14:38:15 $
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.jface.viewers.Viewer;

import com.mentor.nucleus.bp.core.AttributeReferenceInClass_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.IModelClassInspector;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.ComparePlugin;
import com.mentor.nucleus.bp.model.compare.EmptyElement;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager.ModelLoadException;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.inspector.GraphicalModelInspector;

public class ModelCompareContentProvider extends AbstractTreeDifferenceProvider {
	
	ModelInspector inspector = new ModelInspector(MetadataSortingManager.createDefault());
	GraphicalModelInspector graphicalModelInspector = new GraphicalModelInspector();
	private Ooaofooa modelRoot;
	private Object cacheKey;
	private Ooaofooa[] modelRoots;
	private NonRootModelElement[] leftRoots = new NonRootModelElement[0];
	private NonRootModelElement[] rightRoots = new NonRootModelElement[0];
	private boolean includeMissingElements = true;

	public Object getParent(Object element) {
		if(element instanceof ComparableTreeObject) {
			element = ((ComparableTreeObject) element).getRealElement();
		}
		if(element instanceof NonRootModelElement) {
			Object result = null;
			if(((NonRootModelElement) element).getModelRoot() instanceof Ooaofgraphics) {
				result = graphicalModelInspector.getParent(element);
				if(result == null && element instanceof Model_c) {
					result = ((Model_c) element).getRepresents();
				}
			} else { 
				result = inspector.getParent(element);
			}
			return getComparableTreeObject(result);
		}
		if(element instanceof ObjectElement) {
			return getComparableTreeObject(((ObjectElement) element).getParent());
		}
		if(element instanceof EmptyElement) {
			return ((EmptyElement) element).getParent();
		}
		return null;
	}
	
	public final boolean hasChildren(Object element) {
		Object[] children = getChildren(element);
		return children.length != 0;
	}
	
	public void setCacheKey(Object cacheKey) {
		this.cacheKey = cacheKey;
	}
	
	public void setModelRoot(Ooaofooa modelRoot) {
		this.modelRoot = modelRoot;
	}
	
	public void setModelRoots(Ooaofooa[] modelRoots) {
		this.modelRoots = modelRoots;
	}
	
	public final Object[] getChildren(Object element) {
		if(element instanceof ComparableTreeObject) {
			element = ((ComparableTreeObject) element).getRealElement();
		}
		final Object finalElement = element; 
		if(element instanceof NonRootModelElement) {
			IModelClassInspector modelInspector = inspector;
			if(((NonRootModelElement) element).getModelRoot() instanceof Ooaofgraphics) {
				modelInspector = graphicalModelInspector;
			}
			// combine attributes and real children
 			ObjectElement[] attributes = getAttributefromInspector(element, modelInspector);
			ObjectElement[] referentials = modelInspector.getReferentials(element);
			// filter referentials
			List<ObjectElement> referentialList = new ArrayList<ObjectElement>();
			for(int i = 0; i < referentials.length; i++) {
				if (referentials[i].getParent() instanceof AttributeReferenceInClass_c) {
					continue;
				}
				referentialList.add(referentials[i]);
			}
			referentials = referentialList
					.toArray(new ObjectElement[referentialList.size()]);
			ObjectElement[] objectElementRelations = modelInspector.getChildRelations(element);
			List<Object> childRelations = new ArrayList<Object>();
			// convert child relations to NonRootModelElements
			for(int i = 0; i < objectElementRelations.length; i++) {
				if(objectElementRelations[i].getValue() instanceof NonRootModelElement) {
					if(!((NonRootModelElement) objectElementRelations[i].getValue()).isProxy()) {
						childRelations.add(objectElementRelations[i].getValue());	
					}
				} else {
					childRelations.add(objectElementRelations[i]);
				}
			}
			if(getIncludeNonTreeData()) {
				// include graphical data if asked for
				Model_c model = ComparePlugin.getDefault().getModelCacheManager().getLoadedGraphicalModelsForElements(element);
				if(model == null && modelRoots != null) {
					for(Ooaofooa modelRoot : modelRoots) {
						model = Model_c.ModelInstance(Ooaofgraphics.getInstance(modelRoot.getId()), new ClassQueryInterface_c() {
							
							@Override
							public boolean evaluate(Object candidate) {
								return ((Model_c) candidate).getRepresents() == finalElement; 
							}
						});
						if(model != null) {
							break;
						}
					}
				}
				if(model != null) {
					childRelations.add(model);
				}
			}
			Object[] childRelationArray = childRelations.toArray();
			Object[] all = new Object[attributes.length
			                                        + referentials.length + childRelationArray.length];
			System.arraycopy(attributes, 0, all, 0, attributes.length);
			System.arraycopy(referentials, 0, all, attributes.length, referentials.length);
			System.arraycopy(childRelationArray, 0, all, attributes.length
					+ referentials.length, childRelationArray.length);
			// convert all to comparable objects
			List<ComparableTreeObject> comparables = new ArrayList<ComparableTreeObject>();
			for(Object object : all) {
				comparables.add(getComparableTreeObject(object));
			}
			// insert empty elements
			if ((leftRoots.length != 0 || rightRoots.length != 0)
					&& includeMissingElements) {
				insertEmptyElements(element, comparables);
			}
			return comparables.toArray();
		}
		if(element instanceof IStreamContentAccessor) {
			try {
				NonRootModelElement[] rootElements = ComparePlugin.getDefault()
						.getModelCacheManager().getRootElements(element, null, false, modelRoot, cacheKey);
				if(rootElements != null && rootElements.length > 0) {
					List<ComparableTreeObject> comparables = new ArrayList<ComparableTreeObject>();
					for(NonRootModelElement elem : rootElements) {
						comparables.add(getComparableTreeObject(elem));
					}
					return comparables.toArray();
				} else {
					return new Object[0];
				}
			} catch (ModelLoadException e) {
				CorePlugin.logError("Unable to load content for comparison.", e);
			}
		}
		return new Object[0];
	}

	private void insertEmptyElements(Object element,
			List<ComparableTreeObject> comparables) {
		if (element instanceof NonRootModelElement) {
			NonRootModelElement nrme = (NonRootModelElement) element;
			// if we are the left side, then check the right side for
			// missing elements
			if (nrme.getModelRoot().getId().contains("LEFT")) {
				Object matchingChild = getMatchingChild(element, rightRoots);
				if (matchingChild != null) {
					// look at the children for the right side
					// for any that do not exist locally insert
					// a blank element
					Object[] children = getChildrenWithoutMissingElements(matchingChild);
					int location = 0;
					for (Object child : children) {
						Object[] leftChildren = getChildrenWithoutMissingElements(element);
						Object leftMatching = null;
						for (Object leftChild : leftChildren) {
							if (leftChild.equals(child)) {
								leftMatching = leftChild;
								break;
							}
						}
						if (leftMatching == null) {
							EmptyElement empty = new EmptyElement(child,
									element, location);
							comparables.add(location, empty);
						}
						location++;
					}
				}
			}
			// if we are the right side, then check the left side for
			// missing elements
			if (nrme.getModelRoot().getId().contains("RIGHT")) {
				Object matchingChild = getMatchingChild(element, leftRoots);
				if (matchingChild != null) {
					// look at the children for the right side
					// for any that do not exist locally insert
					// a blank element
					Object[] children = getChildrenWithoutMissingElements(matchingChild);
					int location = 0;
					for (Object child : children) {
						Object[] rightChildren = getChildrenWithoutMissingElements(element);
						Object rightMatching = null;
						for (Object rightChild : rightChildren) {
							if (rightChild.equals(child)) {
								rightMatching = rightChild;
								break;
							}
						}
						if (rightMatching == null) {
							EmptyElement empty = new EmptyElement(child,
									element, location);
							comparables.add(location, empty);
						}
						location++;
					}
				}
			}
			// if we are the ancestor, then check the left side for
			// missing elements
			if (nrme.getModelRoot().getId().contains("ANCESTOR")) {
				Object matchingChild = getMatchingChild(element, leftRoots);
				if (matchingChild != null) {
					// look at the children for the left side
					// for any that do not exist locally insert
					// a blank element
					Object[] children = getChildrenWithoutMissingElements(matchingChild);
					int location = 0;
					for (Object child : children) {
						Object[] ancestorChildren = getChildrenWithoutMissingElements(element);
						Object ancestorMatching = null;
						for (Object ancestorChild : ancestorChildren) {
							if (ancestorChild.equals(child)) {
								ancestorMatching = ancestorChild;
								break;
							}
						}
						if (ancestorMatching == null) {
							EmptyElement empty = new EmptyElement(child,
									element, location);
							comparables.add(location, empty);
						}
						location++;
					}
				}
				// repeat to add empty elements for missing right elements
				matchingChild = getMatchingChild(element, rightRoots);
				if (matchingChild != null) {
					// look at the children for the left side
					// for any that do not exist locally insert
					// a blank element
					Object[] children = getChildrenWithoutMissingElements(matchingChild);
					int location = 0;
					for (Object child : children) {
						Object[] ancestorChildren = getChildrenWithoutMissingElements(element);
						Object ancestorMatching = null;
						for (Object ancestorChild : ancestorChildren) {
							if (ancestorChild.equals(child)) {
								ancestorMatching = ancestorChild;
								break;
							}
						}
						if (ancestorMatching == null) {
							EmptyElement empty = new EmptyElement(child,
									element, location);
							comparables.add(location, empty);
						}
						location++;
					}
				}
			}
		}
	}


	private Object[] getChildrenWithoutMissingElements(Object element) {
		try {
			includeMissingElements = false;
			Object[] children = getChildren(element);
			return children;
		} finally {
			includeMissingElements = true;
		}
	}

	private Object getMatchingChild(Object element, Object[] rootElements) {
		for(Object root : rootElements) {
			ComparableTreeObject rootTreeObject = ComparableProvider.getComparableTreeObject(root);
			if(rootTreeObject.equals(ComparableProvider.getComparableTreeObject(element))) {
				return root;
			} else {
				// check the children
				Object matching = getMatchingChild(element,
						getChildrenWithoutMissingElements(root));
				if(matching != null) {
					return matching;
				}
			}
		}
		return null;
	}

	private ObjectElement[] getAttributefromInspector(Object object, IModelClassInspector modelInspector){
		ObjectElement[] refs = modelInspector.getAttributes(object);
		List<ObjectElement> refList = new ArrayList<ObjectElement>();
		for(ObjectElement element : refs) {
			if(element == null) {
				continue;
			}
			refList.add(element);
		}
		ObjectElement[] referentials = refList.toArray(new ObjectElement[refList.size()]);
		for (int i = 0 ; i < referentials.length; i++)
		{
			if( referentials[i].getValue() == null){
				if(referentials[i].getName().equals("represents")) {
					if (referentials[i].getParent() instanceof Model_c) {
						referentials[i].setValue(((Model_c) referentials[i]
								.getParent()).getRepresents_path());
						continue;
					}
					if (referentials[i].getParent() instanceof GraphicalElement_c) {
						referentials[i]
								.setValue(((GraphicalElement_c) referentials[i]
										.getParent()).getRepresents_path());
						continue;
					}
				}
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)"ORPH1", object, false);//$NON-NLS-1$ 
			}else if( referentials[i].getValue().toString().indexOf("ORPH1")+1 > 0 )	//$NON-NLS-1$
			{
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)referentials[i].getValue().toString().replaceAll("ORPH1", ""), object, false);//$NON-NLS-1$
			}else if( referentials[i].getValue().toString().indexOf("ORPH2")+1 > 0 )	//$NON-NLS-1$
			{
				referentials[i] = new ObjectElement(
						referentials[i].getName(),
						referentials[i].getType(),						
						(Object)referentials[i].getValue().toString().replaceAll("ORPH2", ""), object, false);//$NON-NLS-1$
			}			
		}
		return referentials;			
	}
	
	public Object[] getElements(Object element) {
		return getChildren(element);
	}				

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do
	}

	@Override
	public ComparableTreeObject getComparableTreeObject(Object realElement) {
		return ComparableProvider.getComparableTreeObject(realElement);
	}

	/**
	 * Returns a list of children of this given
	 * childs type, including the given child.
	 * This method currently excludes all attributes
	 * and only includes child relations.
	 */
	@Override
	public Object[] getChildrenOfType(Object parent, Class<?> type) {
		List<Object> children = new ArrayList<Object>();
		if(parent instanceof NonRootModelElement) {
			IModelClassInspector modelInspector = inspector;
			if(((NonRootModelElement) parent).getModelRoot() instanceof Ooaofgraphics) {
				modelInspector = graphicalModelInspector;
			}
			ObjectElement[] childRelations = modelInspector.getChildRelations(parent);
			for(ObjectElement element : childRelations) {
				if(element.getValue() instanceof NonRootModelElement) {
					if(element.getValue().getClass() == type) {
						children.add(element.getValue());
					}
				}
			}
		}
		return children.toArray();
	}

	public void setRootElements(NonRootModelElement[] leftRoots,
			NonRootModelElement[] rightRoots) {
		if(leftRoots != null) {
			this.leftRoots = leftRoots;
		} else {
			this.leftRoots = new NonRootModelElement[0];
		}
		if(rightRoots != null) {
			this.rightRoots = rightRoots;	
		} else {
			this.rightRoots = new NonRootModelElement[0];
		}
	}

}
