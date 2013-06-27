---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Set up nightly build to use github
### xtUML Project Design Note



1. Abstract
-----------
The team is moving the BridgePoint source code from CVS to a private 
repository on github.com. This note describes the necessary work to make this move.

2. History
----------
None.

3. Document References
----------------------
[1] Issues 1, https://github.com/xtuml/internal/issues/1  
[2] New xtUML SVN repository, http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml  
[3] Issue 1 Analysis Note, https://github.com/xtuml/internal/blob/master/doc/notes/1_github_nightly_build_ant.md  

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
7.1  Work with IT to configure a new build server virtual machine.  

7.2  Store build/installer creation artifacts in RCS  
7.2.1  The new SVN repository [2] now contains the  
  * Directories that are used as the install base for windows and linux in the 
    BridgePoint_e3.7 and BridgePoint_for_Linux_e3.7 projects.  These projects contain
    the JRE, eclipse base, docbook tools, etc.  The files that are copied into these
    folders on a per-build basis are added to SVN ignore properties.
  * The tools to build the installer is in the MIMIC project.  Again, per-build files
    are ignored.  This includes the tools from corporate installer team as well as 
    the base versions of the mimic workspaces.
  * The "extra_files_for_build" project contains generator, mc3020 docs, etc.
  
8. Design Comments
------------------
TODO - remove old build server stuff from bp.core/tools
TODO - update build scripts to pull(/update) from SVN with every build
TODO - clean up TODOs in build scripts

9. Work Required
----------------

10. Unit Test
------------

End
---

