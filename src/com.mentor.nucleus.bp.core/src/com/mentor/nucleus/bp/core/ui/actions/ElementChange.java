//========================================================================
//
//File:      $RCSfile: ElementChange.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2012/03/23 18:47:39 $
//
//Copyright 2005-2012 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.core.ui.actions;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class ElementChange {
	private boolean isRemoval = false;
	private String changeLabel = "";
	private Object elementChanged = null;

	public ElementChange(Object elementChanged,
			boolean isDeletion) {
		this(elementChanged, isDeletion, "");
	}
	public ElementChange(Object elementChanged,
			boolean isRemoval, String changeLabel) {
		this.isRemoval = isRemoval;
		this.elementChanged = elementChanged;
		this.changeLabel = changeLabel;
	}
	
	public NonRootModelElement getElementChanged() {
		return (NonRootModelElement) elementChanged;
	}
	
	public boolean isRemoval() {
		return isRemoval;
	}
	
	public String getChangeLabel() {
		return changeLabel;
	}
}
