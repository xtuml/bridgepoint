---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Set up nightly build to use github
### xtUML Project Design Note



1. Abstract
-----------
The team is moving the BridgePoint source code from CVS to a private 
repository on github.com. This note describes the necessary work to make this 
move.

2. History
----------
None.

3. Document References
----------------------
[1] Issues 1, https://github.com/xtuml/internal/issues/1   
[2] New xtUML SVN repository, http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml   
[3] Issue 1 Analysis Note, https://github.com/xtuml/internal/blob/master/doc-internal/notes/1_github_nightly_build_ant.md   
[4] Issues 2 - Transition to github phase 3 - code cleanup, https://github.com/xtuml/internal/issues/2   
[5] HOWTO configure git build server, https://github.com/xtuml/internal/blob/master/doc-internal/process/releases/HOWTO-configure-git-build-server.md  
[6] Issues 2 - Transition to github phase 2 - deprecate CVS and cut over development, https://github.com/xtuml/internal/issues/3        
[7] Issues 5 - Build process enhancements, https://github.com/xtuml/internal/issues/5  

4. Background
-------------
See [3]

5. Requirements
---------------
See [3]

6. Analysis
-----------
See [3]

7. Design
---------
7.1  New build server VM  
7.1.1  IT created a new build server virtual machine with WinServer 2003 x64.  
  We originally tried to use a WinServer 2008 x64 image, but continually hit 
  problems with Windows User Access Control.  Finally we decided to drop back 
  to a 2003 image that is closer to our existing build server.  The new build
  server VM is named svr-orw-sle-10.wv.mentorg.com   
7.1.2  The steps performed to configure this server are recorded in [5] so they
  could be used to guide the configuring of another server if desired.  

7.2  Store build/installer creation artifacts in RCS   
7.2.1  The new SVN repository [2] now contains the   
  * Directories that are used as the install base for windows and linux in the 
  BridgePoint_e3.7 and BridgePoint_for_Linux_e3.7 projects.  These projects 
  contain the JRE, eclipse base, docbook tools, etc.  The files that are copied
  into these folders on a per-build basis are added to SVN ignore properties.
  * The tools to build the installer is in the MIMIC project.  Again, per-build
  files are ignored.  This includes the tools from corporate installer team as 
  well as the base versions of the mimic workspaces.
  * The "extra_files_for_build" project contains generator, mc3020 docs, etc.
  * A version of generator and its launching scripts used during the build
  * The MGLS files (DLL & pkginfo) used by the tools (generator) during
  the build process
  * The version of the ant tool used by the build process
  * The version of eclipse used by the build process
  * Since most of these directories are tool folders, they contain EXEs and DLLs
  that we want to include in SVN.  By default these files are ignored by SVN.
  The files must be manually added to SVN version control so they are stored in
  the repository.  

7.2.1.1   The files stored in SVN are checked out fresh with every build in order
  to simplify the build scripts and to ensure cleanliness between nightly and branch
  builds which may be interleaved at random by build server users.  This is a heavy
  hammer to employ, but the penalty is slight given that the SVN and build servers are
  both on the same mentor network in Wilsonville, so data transfer time is not much 
  of an issue.  The trade off is simply this data transfer penalty versus the 
  additional complexity of the build scripts that would be required to be more 
  intelligent about simply updating or not re-pulling the SVN data between nightly
  and branch builds. Issue [7] is raised to add more intelligence for a speed up.  
                           
7.2.2  The scripts used by the build server to perform the build are stored in 
  github under xtuml/internal/utilities/build.  The scripts are heavily based
  on the current CVS build scripts that live in bp.core/tools.  Those CVS build
  scripts will soon no longer be needed and a task [4] is open to clean them
  out.  

7.3  Source code  
7.3.1  The BridgePoint source code is stored in several github repositories:
  * xtuml/internal
  * xtuml/mc

7.3.2  The build process pulls the code from these repositories with each 
  build.  
7.3.3  Git ignore files apply to every sub-directory under where the ignore file
  lives.  This is different than the way CVS ignore files work.  As the 
  .cvsignore files were converted to .gitignore, some changes are required to
  account for these differences and ignore or not ignore the proper files.  
7.3.4  A .gitignore file is put at the top of the source tree to ignore *.class
  files.  
7.3.5  The point at which CVS HEAD is snapshot and moved to git is tagged in
  both revision control systems.  The CVS tag is "git_transition_point".  The git
  tag is "initial_code_from_CVS".
  
8. Design Comments
------------------
8.1  This issue covers the initial steps to make the build work from github
  using the new server.  Before we move to active development, some more steps
  must be performed.  These are covered in [6].  
8.2  There are additional tasks that should be performed to clean up the code
  base after we officially move all development to github.  These tasks are not
  blockers for our switch from CVS to git.  Thus, these are broken out into a 
  second issue ([4]).  

9. Work Required
----------------
See Design.

10. Unit Test
------------
10.1 Smoke test   
  - Run the installer produced by the new build scripts & server (Win & Linux)
  - __R__ It installs correctly
  - Create the MicrowaveOven sample project
  - Translate it
  - __R__ It translates, compiles, and produces and EXE
  - __R__ The EXE runs
  - eXecute it
  - __R__ It runs in xtUML eXecute 
  - Create the GPS Watch sample project
  - Translate it
  - __R__ It translates, compiles, and produces and EXE
  - __R__ The EXE runs
  - eXecute it
  - __R__ It runs in xtUML eXecute 
  
10.2 Difference test  
  - __R__ The installer size is very close to the current installer size
  - Install the existing HEAD build
  - Install the new git master build
  - "diff" the two directory trees
  - __R__ Binary files that are marked different make sense
  - __R__ Text files that are different are checked by hand and validated 

10.3 Branch builds work as well as git master builds  
  - __R__ The installer size of a branch build is very close to the master 
  installer size
  - Install the branch build
  - __R__ BridgePoint installs properly and the expected changes from the branch are
  visible. 

End
---

