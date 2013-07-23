//========================================================================
//
//File:      $RCSfile: IModelDelta.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 22:54:11 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package com.mentor.nucleus.bp.core.common;


/**
 * This interface defines the ModelDelta sent as part of ModelChangeEvent. Prior to this
 * issue the constants defined here were part of ooaofooa.mdl but now are made part of
 * this.
 */
public interface IModelDelta {

	/**
	 * Returns the kind of the ModelDelta, which one of the constants defined in the 
     * Modeleventnotification_c class.
	 * @return the kind of this model delta      
	 */
	public int getKind();
	
	/**
	 * 
	 * @return the source Model element of the model change event. 
	 *         This value must not be null.           
	 */
	public ModelElement getModelElement();
	
	/**
	 * Allow setting the model element associated with this delta
	 */
	public void setModelElement(ModelElement element);
}
