//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

package lib;

public class BPFloat {
	
	private float value;
	
	public BPFloat(float val)
	{
		value = val;
	}
	
	public float getValue()
	{
		return value;
	}
	
	public void setValue(float val)
	{
		value = val;
	}
}
