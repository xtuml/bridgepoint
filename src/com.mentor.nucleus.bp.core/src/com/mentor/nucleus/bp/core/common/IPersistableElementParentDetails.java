package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: IPersistableElementParentDetails.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:39:27 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

public interface IPersistableElementParentDetails {
	public NonRootModelElement getChild();
	public NonRootModelElement getParent();
	public String getAssociationNumber();
	public String getAssociationPhrase();
	public String getChildKeyLetters();
	public boolean isMany();
}
