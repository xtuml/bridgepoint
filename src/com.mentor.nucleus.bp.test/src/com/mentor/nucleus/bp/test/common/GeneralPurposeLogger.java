package com.mentor.nucleus.bp.test.common;

//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import com.mentor.nucleus.bp.core.common.ILogger;

public class GeneralPurposeLogger implements ILogger {

    public GeneralPurposeLogger() {
        super();
    }

    ArrayList log = new ArrayList();

    public void println(int type, String filterValue, String msg) {
        log.add(msg);
    }

    public void println(Exception e) {
        log.add(e.toString());
    }

    public String[] getLogs() {
        Object[] msgs = log.toArray();
        String[] result = new String[msgs.length];
        for (int i = 0; i < msgs.length; i++) {
            result[i] = (String) msgs[i];
        }
        return result;
    }

    public void clear() {
        log.clear();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        Object[] msgs = log.toArray();

        for (int i = 0; i < msgs.length; i++) {
            buffer.append(msgs[i]);
            buffer.append("\r\n");
        }
        return buffer.toString();
    }
    
    public void write(File file) throws IOException{
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        write(writer);
    }
    
    public void write(PrintWriter writer){
        for (Iterator iterator = log.iterator(); iterator.hasNext();) {
            writer.println(iterator.next());
        }
    }
}