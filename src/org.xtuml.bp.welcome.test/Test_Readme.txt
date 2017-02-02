==========================================================================

File:      Test_Readme.txt

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
  project 'org.xtuml.bp.welcome.test' and test class 'WelcomeTestSuite.java'
- Change workspace data to '${eclipse_home}/bp_tests/welcome'
- Check 'Clear workspace data before launching'
- Set VM args to '-DWORKSPACE_PATH=${workspace_loc}/org.xtuml.bp.welcome.test/ -DLOGFILE_PATH=${eclipse_home}/bp_tests/welcome/.metadata/.log'
- On the Plug-ins tab, make sure the org.xtuml.internal.test plug-in is enabled 
	
	
