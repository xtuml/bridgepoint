==========================================================================

File:      $RCSfile: Test_Readme.txt,v $
Version:   $Revision: 1.8 $
Modified:  $Date: 2013/01/10 23:19:42 $

(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.

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

To execute the test suite in your workspace
-------------------------------------------
- In the Java perspective, select Run/Run...
- Select JUnit Plug-in Test, context menu New
- Rename the new configuration to 'Explorer Test'
- On the Test tab, under 'Run a single test', specify 
  project 'com.mentor.nucleus.bp.ui.explorer.test' and test class 
  'com.mentor.nucleus.bp.ui.explorer.test.ExplorerTestSuite'
- Change workspace data to '${eclipse_home}/bp_tests/explorer'
- Change VM Arguments to 
  '-DWORKSPACE_PATH=${workspace_loc}/com.mentor.nucleus.bp.ui.explorer.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/explorer/.metadata/.log'
- Check 'Clear workspace data before launching'

The test results will be written to a file in the Eclipse top-level folder.

End
---

