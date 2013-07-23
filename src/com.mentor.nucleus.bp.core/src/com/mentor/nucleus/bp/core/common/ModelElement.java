package com.mentor.nucleus.bp.core.common;

//========================================================================
//
//File:      $RCSfile: ModelElement.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

public abstract class ModelElement {
	public ModelElement() 
	{
		
	}
	public static void checkClassConsistency(ModelRoot modelRoot){
		
	}
	
	public boolean checkConsistency(){
		return true;
	}
	
}