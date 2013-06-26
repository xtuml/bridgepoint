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

public class BPString {
	
	private String value;
	
	public BPString(String val)
	{
		value = val;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String val)
	{
		value = val;
	}
}
