//=====================================================================
//
//File:      $RCSfile: ConvertModels.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:13:03 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.junit.Test;

import junit.framework.TestCase;
/**
 * This is actually a utility program, rather than a test class.
 * It converts BP SQL files and earlier-format Tiger model files 
 * to latest-format Tiger model files.  
 * To use this program:
 * 
 * 1) Create a new JUnit plug-in test debug configuration, pointing
 *      at this class
 * 2) Place the files to be converted in the root of the workspace
 *      you associate with the above debug configuration;
 *      also, create a folder called "converted" in that root
 * 3) Run the debug configuration
 * 
 * The converted model files will be written to the "converted" folder
 * in the workspace root.
 * 
 * The JUnit TestCase is employed here merely as a wrapper which 
 * provides integration with the other plug-ins referenced by this
 * class's parent plug-in.  
 */
public class ConvertModels extends TestCase
{
    @Test
	public void testConvertModels() throws Exception
    {
    	//removed after io.sql removal
    }
}