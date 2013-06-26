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

public class BPBoolean {
	
	private boolean value;
	
	public BPBoolean(boolean val)
	{		
		value = val;
	}
	
	public boolean getValue()
	{
		return value;
	}
	
	public void setValue(boolean val)
	{
		value = val;
	}
}
