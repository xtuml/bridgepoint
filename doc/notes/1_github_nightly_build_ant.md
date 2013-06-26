---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Set up nightly build to use github
### xtUML Project Analysis Note



1. Abstract
-----------
The team is moving the BridgePoint source code from CVS to a private repository
on github.com.  This note describes the necessary work to make this move.

2. History
----------
None.

3. Document References
----------------------
[1] Issues 1, https://github.com/xtuml/internal/issues/1  
[2] New xtUML SVN repository, http://wv-svn-01.wv.mentorg.com/svn/sle/xtuml  

4. Background
-------------
Our team has used a CVS repository for many years to house the source code for
BridgePoint.  The open source xtUML editor code will eventually be housed in a 
public respoitory on github.  As we move towards that goal, we have decided that 
it makes sense to house our proprietary source code in a private repository on 
github as well.  CVS has served us well, but we expect to achieve some productivity
gains using the more robust and distributed git repositories.

5. Requirements
---------------
5.1  The source code shall be located in a private github repository.  

5.2  The build server shall continue to be able to build a complete BridgePoint 
 installer as it does today.  
 
5.3  The CVS repository shall live on in a read-only state for reference purposes.  

5.4  All things (source, build scripts, eclipse base, etc) needed by the build 
 shall be stored in RCS  
 
5.5  We shall still be able to build from our CVS read-only repo.  This is 
 needed for maintaince releases.  

6. Analysis
-----------
See section 7.

7. Work Required
----------------
7.1  Source code  
7.1.1  Export the source code projects from CVS  
7.1.1.1  Convert .cvsignore files to .gitignore  
7.1.1.2  Add repository-global .gitignore file to ignore java class files  
7.1.2  Import the source code projects into github  
7.1.3  Perform the rest of the work (7.2) to make the build work.  Then right
 before we flip the switch and move all active development, export the CVS projects
 again and compare them with the github versions.  Migrate any changes from CVS to
 github.  Denote these changes with a specific commit or tag so that we can see the 
 changes from day 0 of the work to the point where we go live.  

7.2  Update the build scripts to get the source code from github instead of CVS  

7.3  We have already started archiving the "extra_files_for_build" (generator, 
 mc3020 doc, etc) in our new SVN repository [2], rather than existing in a raw 
 folder on tucson.  These files must be updated as we transition to the new build
 process.  

7.4  Make CVS read-only access  
7.4.1  Use our CVS hook scripts to enforce this.  Modify the DTS number the user 
 passes in to make it invalid by adding "CVS-is-read-only" to the ID.  This will make 
 commits fail.  Modify the error message given on commit failure to point out that 
 CVS is now read-only.  
 
7.5  Put our Windows and Linux eclipse bases in SVN [2].  Update the build scripts
 to pull them from the repository.  

7.6  Set up a new VM to serve as a new build server.  

8. Acceptance Test
------------
8.1  Developers can access (checkout and commit) the source code on github and 
 build BridgePoint locally  
8.2  The build server can build windows and linux installers  
8.3  Perform a build on the existing build server using the CVS repository to verify
 we can still do maintenence builds.  

End
---

