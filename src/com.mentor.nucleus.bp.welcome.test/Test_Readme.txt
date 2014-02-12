==========================================================================

File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.11 $
Modified:  $Date: 2013/01/10 23:05:15 $

(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.

==========================================================================
Licensed under the Apache License, Version 2.0 (the "License"); you may not 
use this file except in compliance with the License.  You may obtain a copy 
of the License at

      http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
License for the specific language governing permissions and limitations under
the License.
==========================================================================
To run test:

- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'WelcomeTest'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.welcome.test' and test class 'WelcomeTestSuite.java'
- Change workspace data to '${eclipse_home}/bp_tests/welcome'
- Check 'Clear workspace data before launching'
- Set VM args to '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.welcome.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/welcome/.metadata/.log'
- On the Plug-ins tab, make sure the com.mentor.nucleus.internal.test plugin 
	is enabled in order to test issue 1833 - the name change of the EDGE Builder.
	
	
