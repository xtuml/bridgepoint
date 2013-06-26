package com.mentor.nucleus.bp.search.query;
//========================================================================
//
//File:      $RCSfile: ModelQueryProvider.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:14:21 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
import org.eclipse.search.ui.ISearchQuery;

public class ModelQueryProvider {

	public static ISearchQuery createQuery(ModelSearchInput input) {
		return ModelSearchQuery.createQuery(input);
	}

}
