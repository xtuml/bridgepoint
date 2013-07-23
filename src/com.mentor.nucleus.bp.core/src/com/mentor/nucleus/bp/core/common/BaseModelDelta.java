//========================================================================
//
//File:      $RCSfile: BaseModelDelta.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.core.common;



public class BaseModelDelta implements IModelDelta {

	private int kind;
	private ModelElement modelElement;
	
	boolean isIgnored = false;
	
	public BaseModelDelta(int deltaKind, ModelElement srcModelElement){
		kind = deltaKind;
		modelElement = srcModelElement;
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.core.common.IModelDelta#getKind()
	 */
	public int getKind() {
		return kind;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.core.common.IModelDelta#getModelElement()
	 */
	public ModelElement getModelElement() {
		return modelElement;
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.core.common.IModelDelta#setModelElement()
	 */
	public void setModelElement(ModelElement element) {
		modelElement = element;
	}

}
