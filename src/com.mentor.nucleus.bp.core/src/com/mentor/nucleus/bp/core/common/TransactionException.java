//=====================================================================
//
//File:      $RCSfile: TransactionException.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:11 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.core.common;

public class TransactionException extends Exception {
	public TransactionException(String message){
		super(message);
	}
	
	public TransactionException(Exception exception){
		super(exception);
	}
}
