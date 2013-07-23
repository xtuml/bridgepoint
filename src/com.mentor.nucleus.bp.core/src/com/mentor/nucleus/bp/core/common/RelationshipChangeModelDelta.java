//========================================================================
//
//File:      $RCSfile: RelationshipChangeModelDelta.java,v $
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


public class RelationshipChangeModelDelta extends BaseModelDelta {

	private ModelElement destinationModelElement = null;
	
	/*
	 * Stores the name of relationship by which relate/unrelate was done, so that it can  
	 * be used to perform undo.
	 */
	private String relationName;
	
	/*
	 * The suffix added to the relate/unrelate method in case of reflexive association  
	 */
	private String relationDirectionPhrase;
	
		
	/**
	 * @param eventType
	 * @param srcModelElement
	 * @param destModelElement
	 * @param relName        the association Numb through which relation/unrelation is done
	 * @param aRelationDirectionPhrase  the direction phrase in case of reflexive association
	 * 
	 * In this case the sourceModelElement is the ModelElement which is being related
	 * or unrelated from the destionationModelElement.
	 */
	public RelationshipChangeModelDelta(int eventType,
			ModelElement srcModelElement, ModelElement destModelElement, String relName, String aRelationDirectionPhrase) {
		super(eventType, srcModelElement);
		
		destinationModelElement = destModelElement;
		relationName = relName;
		relationDirectionPhrase = aRelationDirectionPhrase;
	}
	
	public ModelElement getSourceModelElement(){
		return getModelElement();
	}
	
	public void setSourceModelElement(ModelElement source) {
		super.setModelElement(source);
	}
	
	public void setDestinationModelElement(ModelElement destination) {
		destinationModelElement = destination;
	}
	
	public ModelElement getDestinationModelElement(){
		return destinationModelElement;
	}	
	
	public String getRelationName(){
		return relationName;
	}
	
	public String getRelationDirectionPhrase(){
	    return relationDirectionPhrase;
	}
}
