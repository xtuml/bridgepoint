//========================================================================
//
// File: ZipUtil.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Assert;

public class ZipUtil {

	public static void unzipFileContents(String zipFilePath, String outputFolder)
			throws IOException {
		File folder = new File(outputFolder);
		if(!folder.exists()) {
			folder.mkdir();
		}
		byte[] buffer = new byte[1024];
		File zipFile = new File(zipFilePath);
		if (!zipFile.exists()) {
			Assert.fail("Could not locate zip file.");
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry entry = zis.getNextEntry();
		while(entry != null) {
			File file = new File(outputFolder + File.separator + entry.getName());
			if(entry.isDirectory()) {
				file.mkdir();
			} else { 
				FileOutputStream fos = new FileOutputStream(file);
				int length;
				while((length = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, length);
				}
				fos.close();
			}
			entry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
	}

}
