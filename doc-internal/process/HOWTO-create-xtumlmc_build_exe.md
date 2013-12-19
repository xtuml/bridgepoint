# HOWTO Create xtumlmc_build.exe and Incorporate It Into Nightly Builds
### xtUML Project Technical Note

1. Abstract
-----------
This note describes the steps for creating the xtumlmc_build.exe using 
Strawberry Perl and put it where it will be picked up by the nightly
build process.

2. Document References
----------------------
[1] http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml
    This is the svn repository where the "extra files" for the build are stored.
    These are the large binary files like the eclipse installation, and other
    binaries.  
    
3. Steps
-------------
1.  Log into svr-azt-eng-03 via Remote Desktop
2.  Open the Strawberry Perl console (Start > All Programs > Strawberry Perl > Perl (command line))
3.  Navigate to the folder that contains the xtumlmc_build script to compile.
4.  Run ```pp -o xtumlmc_build.exe xtumlmc_build```
5.  Start BridgePoint on the ```c:\workspaces\build_artifacts``` workspace.  This workspace is already connected to [1].
6.  Switch to the Resource perspective
7.  Expand the ```extra_files_for_build``` project
8.  Copy the newly build created xtumlmc_build.exe from disk into this folder inside BridgePoint, overwriting the existing EXE
9.  Commit the new EXE to SVN
10. Done
