package org.xtuml.bp.welcome.test;

import org.eclipse.core.runtime.CoreException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.util.WorkspaceUtil;

//=====================================================================
//
//File:      $RCSfile: WelcomeTestSuite.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 13:25:52 $
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


import junit.framework.Test;
import junit.framework.TestSuite;

/**
* Test all areas of the core
*/
@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	GlobalTestSetupClass.class,
	WelcomePageTestGPS.class,
	WelcomePageTest.class,
	WelcomePageTestMetamodel.class,	
})
public class WelcomeTestSuite extends TestSuite {


}