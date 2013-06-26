package com.mentor.nucleus.bp.search.results;
//========================================================================
//
//File:      $RCSfile: ModelMatch.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:14:29 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
import org.eclipse.search.ui.text.Match;

import com.mentor.nucleus.bp.core.Match_c;

public class ModelMatch extends Match {
	
	public static final int DESCRIPTION = 0;
	public static final int ACTION_LANGUAGE = 1;
	
	private int type = DESCRIPTION;
	private Match_c modelMatch;

	public ModelMatch(Object element, int offset, int length, int type, Match_c modelMatch) {
		super(element, offset, length);
		this.type = type;
		this.modelMatch = modelMatch;
	}
	
	public int getType() {
		return type ;
	}

	public Match_c getMatch() {
		return modelMatch;
	}

}
