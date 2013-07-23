package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: ModelChangedEvent.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

import java.util.Enumeration;
import java.util.EventObject;

import org.eclipse.core.runtime.Platform;

import com.mentor.nucleus.bp.core.Modeleventnotification_c;

public class ModelChangedEvent extends EventObject {

	IModelDelta[] modelDeltas;
	ModelElement modelElement;
	ModelElement newModelElement;
	//Valid only for Modeleventnotification_c.MODEL_ELEMENT_PRE_RELOAD;
	String oldModelRoot = null; 
	
	int type;
	
	public ModelChangedEvent(ModelElement source,  IModelDelta delta) {
		super(source);
		modelDeltas = new IModelDelta[1];
		modelDeltas[0] = delta;
		this.type = Modeleventnotification_c.MODEL_ELEMENT_CHANGED; 
	}
	
	public ModelChangedEvent(ModelElement source,  IModelDelta[] delta) {
		super(source);
		modelDeltas = delta;
		this.type = Modeleventnotification_c.MODEL_ELEMENT_CHANGED; 
	}
	
	public ModelChangedEvent(ModelElement source,  int type, ModelElement mElement) {
		super(source);
		modelElement = mElement;
		this.type = type; 
	}
	
	public ModelChangedEvent(ModelElement source,  int type, ModelElement oldElement, ModelElement newElement) {
		super(source);
		modelElement = oldElement;
		newModelElement = newElement;
		this.type = type; 
	}
	

	/**
	 * Returns the affected model element
	 * @return The model element or null if not applicable to this type of event
	 */
	public ModelElement getModelElement(){
		return modelElement;
	}

	/**
	 * Returns the new affected model element
	 * @return The model element or null if not applicable to this type of event
	 */
	public ModelElement getNewModelElement(){
		return newModelElement;
	}

	/**
	 * Returns the model-root of the model element that is the source of this event.
	 */
	public ModelRoot getSourceModelRoot() {
		if (source instanceof NonRootModelElement) {
			return ((NonRootModelElement) source).getModelRoot();
		} else
			return (ModelRoot) source;
	}
	
	/**
	 * Returns the type of the ModelChangeEvent
	 * @return One of the type constant defined above
	 */
	public int getType(){
		return type;
	}
	
	public boolean containsDelta(){
		boolean containsDelta = (modelDeltas != null);
		if(containsDelta){
			containsDelta = false;
			for(int i=0; i<modelDeltas.length; i++){
				if(!((BaseModelDelta)modelDeltas[i]).isIgnored){
					containsDelta = true;
					break;
				}
			}
		}
		
		return containsDelta;
	}
	
	public IModelDelta getModelDelta(){
		if(modelDeltas != null){
			for (int i = 0; i < modelDeltas.length; i++) {
				if(!((BaseModelDelta)modelDeltas[i]).isIgnored){
					return modelDeltas[i];
				}
			}
		}
		
		return null;
	}

	public Enumeration getModelDeltas() {
		return new DeltaEnumerator();
	}
	
	private class DeltaEnumerator implements Enumeration{
		int lastIndex = 0;
		
		public boolean hasMoreElements() {
			return (getIndexofNextElement() >= 0);
		}

		public Object nextElement() {
			int indexOfNext = getIndexofNextElement();
			if(indexOfNext >= 0){
				lastIndex = indexOfNext + 1;
				return modelDeltas[indexOfNext];
			}
			
			return null;
		}
		
		private int getIndexofNextElement(){
			if(modelDeltas != null){
				for (int i = lastIndex; i < modelDeltas.length; i++) {
					if(!((BaseModelDelta)modelDeltas[i]).isIgnored){
						return i;
					}
				}
			}
			return -1;
		}
	}
	public String getOldRootId() {
		return oldModelRoot;
	}

	StackTraceElement[] stack = null;
	public void setStackTrace() {
	  if (Platform.inDebugMode()) {
		stack = Thread.currentThread().getStackTrace();
	  }
	}
}