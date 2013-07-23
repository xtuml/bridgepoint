package com.mentor.nucleus.bp.core.common ;
//========================================================================
//
//File:      $RCSfile: ILogger.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

public interface ILogger {
  final static int UNKNOWN = 0;
  final static int FUNCTION = 1;
  final static int OPERATION = 2;
  final static int STATE_MACHINE = 3;
  final static int SQL = 4;
  final static int BRIDGE = 5;
  final static int MECH = 6;
  final static int DELETE = 7;
  final static int CONSISTENCY = 8;
  public void println(int type, String filterValue, String toPrint);
  public void println (Exception toPrint);
}
