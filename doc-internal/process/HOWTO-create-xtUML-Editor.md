# HOWTO Create a New Build of xtUML Editor
### xtUML Project Technical Note

1. Abstract
-----------
This note describes the steps for creating a new build of xtUML Editor and 
publishing it on the interweb.

2. Document References
----------------------
[1] HOWTO update the BridgePoint version, https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-update-the-BP-version-number.txt  
[2] HOWTO upload the xtUML Editor, https://github.com/xtuml/internal/blob/master/doc-internal/process/HOWTO-upload-xtUML-editor-installer.md   

3. Steps
-------------
1.  Decide what the new version number for xtUML Editor will be  
2.  Create a tracking issue in CQ and git for the new xtUML Editor  
3.  Branch master in the xtuml/internal git repository to ```<issue#>_xtumleditor_<new version>```, check out this branch  
4.  Merge in the git branch origin/xtUMLeditor_changes  

__NOTE: For the first xtUML Editor creation after BP 4.1.6... The xtUMLeditor changes branch will need to be 
updated.  We will want to switch from leaving mc.c.binary to mc.none instead.__

5.  Bump the version using the ant script [1] to the new xtUML Editor version  
6.  Update the Release Notes and What's New  
7.  Commit all the changes to the branch  
8.  Build the branch on the build server  
9.  Test the installers on Windows and Linux.  Have Dean, Robert, and Cort test as well.
10. Rename the files from ```BridgePoint_<branch>*``` to ```xtUML_Editor_<new version>*```
11. Upload the installers to Amazon S3 [2]  
12. Update the xtUML.org website with the new Release Notes  
13. Notify Jayne she can do a mailing indicating a new release of xtUML Editor is available  
14. Post to the xtuml.org forum indicating the new release is available  
15. Bump the BP development version past the xtUML Editor version  
16. Done
