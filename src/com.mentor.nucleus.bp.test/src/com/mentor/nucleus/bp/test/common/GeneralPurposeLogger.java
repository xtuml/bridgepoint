package com.mentor.nucleus.bp.test.common;

//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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