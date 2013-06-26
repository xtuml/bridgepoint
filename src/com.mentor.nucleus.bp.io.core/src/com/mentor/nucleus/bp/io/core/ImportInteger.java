//========================================================================
//
//File:      $RCSfile: ImportInteger.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:25:21 $
//
//(c) Copyright 2007-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.io.core;

public class ImportInteger {
	public static boolean isInt(Object arg) {
		if (arg instanceof String && !((String) arg).isEmpty()) {
			try {
				Integer.parseInt((String) arg);
				return true; // Parsed as an integer, return true
			} catch (NumberFormatException nfe) {
				// Failed to parse as an integer, drop though and return false
			}
		}
		return false;
	}
}
