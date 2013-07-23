package com.mentor.nucleus.bp.core.common ;

//========================================================================
//
//File:      $RCSfile: NullLogger.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

class NullLogger implements ILogger {
  public void println (int type, String filterValue, String toPrint) {
    //Do Nothing
  }
  public void println (Exception toPrint) {
    //Do Nothing
  }
}
