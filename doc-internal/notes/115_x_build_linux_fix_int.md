---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Fix xtumlmc_build.exe on Linux to work with multiple license servers
### xtUML Project Implementation Note

1. Abstract
-----------
This note covers the work done to address user-reported problems encountered 
when running the tool with multiple license servers specified in the environment
on Linux.

2. Document References
----------------------
[1] Issues 115, https://github.com/xtuml/internal/issues/115  
[2] Issues 35, https://github.com/xtuml/mc/issues/35  
[3] CQ DEI dts0100939419 - Fix xtumlmc_build.exe (on Linux) to work with 
  multiple license servers  
[4] Issues 114, https://github.com/xtuml/internal/issues/114    

3. Background
-------------
A customer has reported that the BridgePoint model compiler fails to get a license
when a valid license exists.  The customer is using Linux.  The scenario is that
they have configured their MGLS_LICENSE_FILE environment variable with a list
of license servers.  The customer found that if the licenses on the first server
in the list are consumed, then the model compiler fails to checkout an available
license from one of the other servers specified.  

4. Requirements
---------------
4.1  The xtUML Verifier and model compilers shall successfully check out licenses
  from backup servers if checkout from the first server fails.
  
5. Work Required
----------------
5.1  Updated the Linux launcher to remove the "," for ":" substitution. This was
  done as part of a previous attempt to deal with multiple license servers 
  on Linux.  This substitution caused problems for the linux-proper side of 
  license acquisition in the eclipse UI for Verifier.
  
5.2  Update the xtumlmc_build perl script to substitute ";" for ":" in the 
  MGLS_LICENSE_FILE environment variable ahead of calling gen_erate.exe.  On 
  Windows, ";" is the normal separation character used, so this substitution 
  is essentially a no-op.  On Linux, we swap in the ";" separator ahead of 
  using wine to invoke gen_erate.exe which allows the multiple servers to be
  searched correctly.
  
6. Implementation Comments
--------------------------
6.1  In order to confirm (during testing) that Windows-based builds are not 
  adversely affected, we need to compile the perl script to EXE.  This issue is
  being handled in conjunction with [4], which changes the tool we use to build
  the EXE.  

7. Unit Test
------------
7.1  Setup:
  - Start a license server on svr-azt-eng-01 with one binary C model compiler
  composite license and one bpumlexecute_c license
  - Build xtumlmc_build to xtumlmc_build.exe (See 6.1)
  - Set up two linux machines and one windows machine where each sets MGLS_LICENSE_FILE
   to 1717@svr-azt-eng-01.wv.mentorg.com followed by 1717@wv-lic-01.wv.mentorg.com
  - Put the updated xtumlmc_build (scripts and EXE) into the 3 BridgePoint installations
  in the binary C MC plug-in
  - Update the Launcher.sh with the change for this work into the two Linux BridgePoints
  
7.2  Test
  - Start BridgePoint on Linux 1.  Create a project.  Build the project.
  - __R__ The build completes successfully
  - Using lmtools, check the licenses consumed on svr-azt-eng-01
  - __R__ The model compiler license is checked out by Linux 1
  - Start BridgePoint on Linux 2.  Create a project.  Build the project.
  - __R__ The build completes successfully
  - Start BridgePoint on Windows.  Create a project.  Build the project.
  - __R__ The build completes successfully
  - Launch a verifier session on Linux 1, Linux 2, and Windows
  - __R__ All verifier sessions start
  
8. Code Changes
---------------
xtuml/internal Branch name: 115_x_build_linux_fix
<pre>

Installer_MIP_MIMIC/Launcher.sh

</pre>

xtuml/mc branch name: 35_x_build_linux_fix
<pre>

mc/bin/xtumlmc_build

</pre>

End
---

