//=====================================================================
//
//File:      $RCSfile: ObjectLogger.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:21:32 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
