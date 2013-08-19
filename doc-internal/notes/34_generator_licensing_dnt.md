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
[1] Issues 34, https://github.com/xtuml/internal/issues/34  
[2] DEI dts0100995063  
[3] <CVS>/Documentation/internal/technical/notes/dts0100969061-969050.int

3. Background
-------------
BridgePoint 4.0 introduced a new mechanism [3] for our handling of the MGLS_ATTR_DISPLAY
key that is used to share composite model compiler licenses between eclipse and 
generator.  This mechanism works fine when the projects are physically located under the
workspace on disk.  It does not work when projects are located in physical locations that
are under the workspace directory.

4. Requirements
---------------
4.1  A project that physically lives in the workspace builds successfully with a single
  composite license.  
4.2  A project that physically lives outside the workspace builds successfully with a single
  composite license.  
  
5. Analysis
-----------
5.1  BridgePoint has a bug in generator that is cuasing the license failure the customer
  is experiencing.  What is happening is this:
- BridgePoint is writing the <workspace>/.metadata/.xtumldisplay file with the 
  <machine>+<workspace> info as we designed it [3].
- Generator is invoked on a project in the workspace.  It then looks for the .xtumldisplay 
  file using a relative path from the project to the workspace .metadata/ folder.  

5.2  This mechanism works fine, as long as the project is actually in the workspace.  But 
  eclipse lets you have projects in the workspace that actually live elsewhere on disk (e.g. 
  a project that lives in a git repository at c:/gitrepo/ but you're running a workspace at 
  c:/workspace/current).  

  Thus, generator fails to find the .xtumldisplay file and fails to check out the model compiler
  license because generator defaulted to PID as the display data since it couldn't read it 
  passed in from eclipse.


6. Design
---------

7. Design Comments
------------------

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

8.3  TODO - import existing project that has a Model Compiler.launch, test expected behavior.

End
---

