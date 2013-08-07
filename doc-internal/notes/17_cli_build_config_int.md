---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# CLI Build Doesn't Use Specified Build Configuration 
### xtUML Project Implementation Note


1. Abstract
-----------
A customer has reported a problem with the BridgePoint command-line build
functionality.  This note describes the fix.

2. History
----------
None.

3. Document References
----------------------
[1] Issues 17, https://github.com/xtuml/internal/issues/17  
[2] Issues 117, https://github.com/xtuml/agilegc/issues/117  

4. Background
-------------
A BridgePoint customer has been making use of the command line interface (CLI)
for building projects.  The customer reports that a valid build configuration
is specified in the "-buildConfig <config name>" argument to CLI.sh Build [2].  

BridgePoint does not give an error indicating that it can't find the build,
indicating that the initial validity checks succeed.  The pre-build and 
translation steps are working properly.  However, once the code build 
(compilation) starts, the specified build configuration is not used.  Rather, 
the default configuration specified in the user interface is used.  

5. Requirements
---------------
5.1  If a valid configuration is specified in the CLI -buildConfig argument,
  then it will be used.  

5.2  If an invalid configuration is specified in the CLI -buildConfig argument,
  then a warning will be given and the default configuration will be used.  

5.3  Once a valid configuration is used in the CLI build, the active 
  configuration for the project will be set back to whatever was active before
  the CLI build ran.

6. Work Required
----------------
6.1  CDT stores the project's build configurations in instances of the 
  org.eclipse.cdt.managedbuilder.core.IConfiguration class.  
 
6.2  CLI's BuildWorkbenchAdvisor class stores the current configuration to a 
  variable then iterates over all of the configurations, looking for the one
  with the name that matches what the user specified in the -buildConfig 
  <config name> argument.  
6.2.1  Once found, our code called the 
  ``` IManagedBuildInfo.setSelectedConfiguration(<config>) ``` API call. Given
  the nature of the bug, it seemed likely that this was not working as 
  expected. Upon inspection of the API docs, we found that this call is "used 
  while the property pages are displayed."  
  
  Further inspection of the API showed that there is another call, 
  ```setDefaultConfiguration(<config>)``` that sets the primary configuration
  for the project.  Since our code sets the config, runs the build, then returns
  the configuration that was active before CLI Build runs, it is acceptable to
  use this call to change the active configuration to the one we want.  

7. Implementation Comments
--------------------------

8. Unit Test
------------
8.1  Create a very simple project "foo" with a component that contains a class.
  Add a new build config to the project named "Hello" alongside the existing 
  "Debug" and "Release" configurations.  Create a batch file on disk that 
  simply does ```echo Hello, world```.  Modify the Hello config so that it runs
  this batch file instead of the gcc tools.   

8.2  Configure the CLI launch script to work for the containing workspace.  

8.3  Test 1  
  - $ CLI.bat Build -project foo -buildConfig Hello
  - __R__ Translation succeeds.  The compilation step outputs "Hello, world" to
  the console.  No EXE is created.

8.4  Test 2  
  - $ CLI.bat Build -project foo -buildConfig Hellobad
  - __R__ Translation succeeds.  The compilation warns that Hellobad is an 
  invalid configuration.  GCC build messages are output to the console.  An EXE is created.  

8.5  Test 3  
  - Remove the EXE created in the previous test
  - $ CLI.bat Build -project foo 
  - __R__ Translation succeeds.  GCC build messages are output to the console.  
  An EXE is created.  

9. Code Changes
---------------
Branch name: 17_skb_clibuildconfig   
Pull request: https://github.com/xtuml/internal/pull/23/files

com.mentor.nucleus.bp.cli/src/com/mentor/nucleus/bp/cli/BuildWorkbenchAdvisor.java  

End
---

