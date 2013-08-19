---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Generator can't locate display key when build ran on a referenced project in a workspace
### xtUML Project Design Note


1. Abstract
-----------
A customer is using a workspace that contains "references" to projects that live
on disk in directories that are not under the current workspace directory.  When 
the build process is launched, the license key "MGLS_ATTR_DISPLAY" is not passed
successfully from eclipse to generator causing a generator license failure.

2. Document References
----------------------
[1] Issues 34, https://github.com/xtuml/internal/issues/34 - This issue  
[2] DEI dts0100995063 - This issue's CQ twin  
[3] < CVS >/Documentation/internal/technical/notes/dts0100969061-969050.int - Update branding
  for BridgePoint 4.0 and use new JLC licensing hooks.  
[4] DEI dts0100995354 - Have generator change the way it "tests" for licenses before checking them out  

3. Background
-------------
BridgePoint 4.0 introduced a new mechanism [3] for our handling of the MGLS_ATTR_DISPLAY
key that is used to share composite model compiler licenses between eclipse and 
generator.  This mechanism works fine when the projects are physically located under the
workspace on disk.  It does not work when projects are located in physical locations that
are not under the workspace directory.

4. Requirements
---------------
4.1  A project that physically lives in the workspace builds successfully with a single
  composite license.  
4.2  A project that physically lives outside the workspace builds successfully with a single
  composite license.  
  
5. Analysis
-----------
5.1  BridgePoint has a bug in generator that is causing the license failure the customer
  is experiencing.  What is happening is this:
- BridgePoint is writing the <workspace>/.metadata/.xtumldisplay file with the 
  < machine >+< workspace > info as we designed it [3].
- Generator is invoked on a project in the workspace.  It then looks for the .xtumldisplay 
  file using a relative path from the project to the workspace .metadata/ folder.  

5.2  This mechanism works fine, as long as the project is actually in the workspace.  But 
  eclipse lets you have projects in the workspace that actually live elsewhere on disk (e.g. 
  a project that lives in a git repository at c:/gitrepo/ but you're running a workspace at 
  c:/workspace/current).  

  Thus, generator fails to find the .xtumldisplay file and fails to check out the model compiler
  license because generator defaulted to PID as the display data since it couldn't read it 
  passed in from eclipse.  

5.3  The model compiler builder is controlled by a launch file named 
  < project >/.externalToolBuilders/Model Compiler.launch.  The "pre-builder" builder is, in contrast,
  implemented in source code as a class that implements eclipse's ```IncrementalProjectBuilder```
  class.  
  
5.4  Part of the reason we chose the solution we did for [3] was that we wanted to get away from
  updating the Model Compiler.launch file regularly, which dirtied the project.  
  
6. Design
---------
6.1  There are several options for solving the bug:
  - 6.1.1 Write the .xtumldisplay file into < project >/gen/code_generation
  - 6.1.2 Change the Model Compiler builder to work like pre-builder and docgen.  That is, be a 
  code-based implementation.  Here we get rid of Model Compiler.launch, and pass the MGLS_ATTR_DISPLAY 
  key in the environment settings to xtumlmc_build.exe invoked by the code.
  - 6.1.3 Update the currently executing environment and let xtumlmc_build and generator inherit
  the value to use via the environment.  

6.2  Option 6.1.1 has the benefit that it is a very simple change to make and the code base is
  affected very little, both on the plug-in and the generator side.  The build process is already 
  dirtying this folder, so it won't matter if we put one more file here.  Since the file will always
  be located in the project in this solution, we won't have any concern about where the project is
  on disk.  Given that this approach simply moves the file home, but not the key data, it is no 
  more or less "secure" than our current file-based approach.  
  
6.3 Option 6.1.2 provides a solution that is closer to the eclipse standard for the way builders
  are intended to be implemented.  It also gives the model compiler builder more visibility into
  the build process as it runs (build started, build finished, etc.).  This option gives us 
  flexibility down the road to pass additional environment variables to xtumlmc_build/generator
  without modifying the launch file.  This option has the drawbacks that it: a) is a significant 
  change to the code base, b)requires us to do a "project upgrade" of some sort to deal with getting
  rid of existing Model Compiler.launch files in order to run the new-style build. 
  
6.4 Option 6.1.3 is not possible.  Java has the ability to "get" an environment variable, but it 
  has no facility to "put" a variable into the current environment.
  
6.5  We will go with option 6.1.1.  It provides a clean and simple approach that will not require
  a "data upgrade" and it will not require nearly as much development and testing as 6.1.2.  

6.6  Required changes to implement chosen approach:  
6.6.1  Update ```BridgePointLicenseManager.writeXTUMLDisplayFile();``` to take an argument
  that is the path to the currently building project's gen/code_generation folder.    
6.6.2  Update the implementation of ```BridgePointLicenseManager.writeXTUMLDisplayFile(path);``` to 
  write the .xtumldisplay file to the path argument (creating the folder if it doesn't already exist).
  Note, the gen/code_generation folder is deleted with each build by the pre-builder and recreated to
  put the < system >.sql file pre-builder creates into the newly cleaned folder.  
6.6.3  In generator, modify ```u_bplic.c::get_display()```.  Change the relative path traversal to 
  look for the ```.xtumldisplay``` file in the project's gen/code_generation folder rather than the 
  workspace.  
  
6.7  We recently found out that the way we test for a license in generator actually does a very quick
  checkout/checkin sequence.  We considered updating the generator code while we have it open to 
  change this behavior to use a different call that does a test without checkout.  We decided to forego
  the work at this time and open issue [4] to track it.  
  
7. Design Comments
------------------
None.

8. Unit Test
------------
__Setup:__ BridgePoint has the new plug-ins and generator with the fix in place.  The license
server has a single license for bpumlmcc_c (available on svr-azt-eng-01).  The source archetypes
are in place.  BridgePoint is configured to point at the testing license server.  

8.1  Same workspace build
  - Start BridgePoint, create a new workspace
  - Create a new xtUML Project named "local" that uses the source model compiler and is 
  physically located inside the workspace.
  - Switch to C/C++ perspective
  - Build the project
  - __R__ Build runs, neither BridgePoint or generator give a license error

8.2  Outside workspace build
  - Start BridgePoint on the workspace from the prior test
  - Create a new xtUML Project named "remote" that uses the source model compiler and is 
  physically located outside the workspace.
  - Switch to C/C++ perspective
  - Build the project
  - __R__ Build runs, neither BridgePoint or generator give a license error

_NOTE: Repeat the tests for both GUI and CLI builds on Windows and Linux_

End
---

