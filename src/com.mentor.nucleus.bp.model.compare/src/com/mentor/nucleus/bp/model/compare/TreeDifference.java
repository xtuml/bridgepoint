package com.mentor.nucleus.bp.model.compare;

import org.eclipse.jface.viewers.TreePath;


public class TreeDifference {

	public static final String LOCATION_DIFFERENCE = "LOCATION_DIFF"; //$NON-NLS-1$
	public static final String VALUE_DIFFERENCE = "VALUE_DIFF"; //$NON-NLS-1$
	public static final String NAME_DIFFERENCE = "NAME_DIFF"; //$NON-NLS-1$

	private boolean includeChildren;
	private String type;
	private Object element;
	private TreeDifference matchingDifference;
	private Object parent;
	private int location;
	private int kind;
	private TreePath path;
	private boolean isContained;
	private TreeDifference container;
	
	public TreeDifference(Object element, String type, boolean includeChildren,
			int kind, TreePath path, boolean isContained, TreeDifference container) {
		this.element = element;
		this.type = type;
		this.includeChildren = includeChildren;
		this.kind = kind;
		this.path = path;
		this.isContained = isContained;
		this.container = container;
	}

	public Object getElement() {
		return element;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean getIncludeChildren() {
		return includeChildren;
	}

	public void setMatchingDifference(TreeDifference matchingDifference) {
		this.matchingDifference = matchingDifference;
	}
	
	public TreeDifference getMatchingDifference() {
		return matchingDifference;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}
	
	public Object getParent() {
		return parent;
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
	
	public int getLocation() {
		return location;
	}

	public int getKind() {
		return kind;
	}
	
	public TreePath getPath() {
		return path;
	}
	
	public boolean isContainedDifference() {
		return isContained;
	}

	public TreeDifference getContainerDifference() {
		return container;
	}
}
