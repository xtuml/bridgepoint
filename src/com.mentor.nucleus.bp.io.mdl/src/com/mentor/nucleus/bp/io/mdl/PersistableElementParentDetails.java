package com.mentor.nucleus.bp.io.mdl;
//=====================================================================
//
//File:      $RCSfile: PersistableElementParentDetails.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:34:42 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import com.mentor.nucleus.bp.core.common.IPersistableElementParentDetails;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class PersistableElementParentDetails implements IPersistableElementParentDetails {
	public String associationNumber = "";
	public String associationPhrase = "";
	public String childKeyLetters = "";
	public NonRootModelElement parent = null;
	public NonRootModelElement child = null;
	public boolean isMany;
	@Override
	public String getAssociationNumber() {
		return associationNumber;
	}
	@Override
	public String getAssociationPhrase() {
		return associationPhrase;
	}
	@Override
	public NonRootModelElement getChild() {
		return child;
	}
	@Override
	public NonRootModelElement getParent() {
		return parent;
	}
	@Override
	public String getChildKeyLetters() {
		return childKeyLetters;
	}
	@Override
	public boolean isMany() {
		return isMany;
	}
}
