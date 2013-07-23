//=====================================================================
//
//File:      $RCSfile: ITransactionListener.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.core.common;


public interface ITransactionListener{
	public void transactionStarted(Transaction transaction);
	public void transactionCancelled(Transaction transaction);
	public void transactionEnded(Transaction transaction);
}