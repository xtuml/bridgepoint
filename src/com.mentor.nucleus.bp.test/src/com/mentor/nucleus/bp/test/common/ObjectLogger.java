//=====================================================================
//
//File:      $RCSfile: ObjectLogger.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:21:32 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.test.common;

import java.util.Vector;

import com.mentor.nucleus.bp.core.common.IModelDelta;

public class ObjectLogger {

    protected ObjectLogger() {
        super();        
    }
    
    Vector logList = new Vector(10);
    protected void addToLog(String logString, ILogable objectToLog){
        String logMsg = logString;
        if (objectToLog != null){
            logMsg += objectToLog.getLogString();
        }
        logList.add(logMsg);
    }
    
    protected void addToLog(String log){
        addToLog(log, null);
    }
      
    protected void clearLog(){
        logList.clear();
    }

    protected String[] getLogContents(){
        Object [] msgs = logList.toArray();
        String [] result = new String [msgs.length];
        for (int i =0; i < msgs.length; i++) {
          result[i] = (String)msgs[i];
        }
        return result;
    }
    
}
