//====================================================================
//
//File:      $RCSfile: PTCodeFormatterTask.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:15:06 $
//
//(c) Copyright 2004-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

public class PTCodeFormatterTask extends Task {
	
	CodeFormatter cf = ToolFactory.createCodeFormatter(new HashMap());
	private Vector<FileSet> filesets = new Vector<FileSet>();
	
	public void addFileset(FileSet set) {
		filesets.addElement(set);
	}
	public void execute() throws BuildException {
		
		if (filesets.size() == 0) {
			throw new BuildException(
				"Specify at least one source - a file or " + "a fileset.");
		}
		
		for (int i = 0; i < filesets.size(); i++) {
			FileSet fs = filesets.elementAt(i);
			DirectoryScanner ds = fs.getDirectoryScanner(getProject());
			File fromDir = fs.getDir(getProject());
			String[] srcFiles = ds.getIncludedFiles();
			
			if (srcFiles.length == 0 ) {
				throw new BuildException(
				"Cannot find any files in the fileset.");
			}
			
			for (int j = 0; j < srcFiles.length; j++) {
				
				try {
					File file = new File(fromDir, srcFiles[j]);
					FileInputStream fis = new FileInputStream(file);
					int count = (int) file.length(); 
					byte data[] = new byte[count];
					String formatted_result = "";
					String result = "";
										
					try {
						fis.read(data);
						fis.close();
						result = new String(data);
						TextEdit te = cf.format(CodeFormatter.K_COMPILATION_UNIT, 
								result, 0, result.length(), 0, null);
						if ( te == null ) {
							// couldn't format input
							formatted_result = result;
						}
						else {
							Document d = new Document();
							d.set(result);
								try {
									te.apply(d, TextEdit.UPDATE_REGIONS);
								} catch (Exception e) {
									BuildException be = new BuildException();
									be.fillInStackTrace();
									throw be;
								}
								formatted_result = d.get();
						}
						System.out.println("Formatting: " + srcFiles[j]);
						if (!result.equals(formatted_result)) {
							
							FileOutputStream os =
								new FileOutputStream(
									new File(fromDir, srcFiles[j]));
							OutputStreamWriter writer =
								new OutputStreamWriter(os);
							writer.write(formatted_result);
							writer.close();
						} else {
							System.out.println(srcFiles[j] + " unchanged");
						}
					} catch (IOException e) {
						System.err.println("Internal Error " + e);
					}
				} catch (FileNotFoundException e) {
					System.err.println("Internal Error " + e);
				}
			}
		}
	}
}
