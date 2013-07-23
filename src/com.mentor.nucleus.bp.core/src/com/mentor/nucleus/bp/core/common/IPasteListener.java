//========================================================================
//
//File:      $RCSfile: IPasteListener.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.core.common;

import java.util.List;

public interface IPasteListener {
	public void pasteCompleted(NonRootModelElement destination,
			List<NonRootModelElement> loadedInstances);
}
