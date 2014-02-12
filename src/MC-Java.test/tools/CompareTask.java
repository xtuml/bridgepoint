//====================================================================
//
//File:      $RCSfile: CompareTask.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2011/05/30 20:20:39 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//This document contains information proprietary and confidential to
//Project Technology, Inc. and is not for external distribution.
//=====================================================================

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

public class CompareTask extends Task {
	
	private String firstfile = null;
	private String secondfile = null;
	
    public void setFirstFile(String s) {
    	if (s.equals("")) {
    		throw new BuildException("Entry for first file to compare must not be empty");
    	}
        this.firstfile = s;
    }
    public void setSecondFile(String s) {
    	if (s.equals("")) {
    		throw new BuildException("Entry for second file to compare must not be empty");
    	}
        this.secondfile = s;
    }
    public String getDir() {
    	String dir = project.getBaseDir().toString();
    	return dir;
    }
    public void execute() throws BuildException {
		
			try {
				String dir = getDir();
				File file1 = new File(dir + "/" + firstfile);
				File file2 = new File(dir + "/" + secondfile);
					FileInputStream fis1 = new FileInputStream(file1);
					FileInputStream fis2 = new FileInputStream(file2);
					int count1 = (int) file1.length();
					int count2 = (int) file2.length();
					byte data1[] = new byte[count1];
					byte data2[] = new byte[count2];
					String result1 = "";
					String result2 = "";
					try {
						fis1.read(data1);
						fis2.read(data2);
						fis1.close();
						fis2.close();
						result1 = new String(data1);
						result2 = new String(data2);
						System.out.println("Comparing: " + firstfile + " and " + secondfile);
						if (!result1.equals(result2)) {
							System.err.println(firstfile + " and " + secondfile + " differ");
						} else {
							System.err.println(firstfile + " and " + secondfile + " are identical");
						}
					} catch (IOException e) {
						System.err.println("Internal Error " + e);
					}
				} catch (FileNotFoundException e) {
					System.err.println("Internal Error " + e);
				}
			}
		}
	