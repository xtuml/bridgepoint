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

import java.util.UUID;

public class BPUniqueId {
	
	private UUID value;
	
	public BPUniqueId(UUID val)
	{
		value = val;
	}
	
	public UUID getValue()
	{
		return value;
	}
	
	public void setValue(UUID val)
	{
		value = val;
	}
}
