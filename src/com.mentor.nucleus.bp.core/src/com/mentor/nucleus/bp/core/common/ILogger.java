package com.mentor.nucleus.bp.core.common ;
//========================================================================
//
//File:      $RCSfile: ILogger.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
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
