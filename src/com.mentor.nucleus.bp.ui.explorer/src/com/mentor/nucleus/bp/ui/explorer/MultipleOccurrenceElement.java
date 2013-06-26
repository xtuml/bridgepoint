//========================================================================
//
//File:      $RCSfile: MultipleOccurrenceElement.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:15:44 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.explorer;

import org.eclipse.core.runtime.IAdaptable;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class MultipleOccurrenceElement implements IAdaptable {

	private NonRootModelElement element;

	public MultipleOccurrenceElement(NonRootModelElement element) {
		this.element = element;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == NonRootModelElement.class) {
			return element;
		}
		return null;
	}

	public NonRootModelElement getElement() {
		return element;
	}
	
}
