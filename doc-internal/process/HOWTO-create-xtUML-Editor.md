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
1. Decide what the new version number for xtUML Editor will be  
2. Create a tracking issue in git xtuml/internal __and__ xtuml/editor issues for the new xtUML Editor 
3. Assure that promotion process has been followed so that all changes
are present in the xtuml/internal repository's `xtuml_editor_builder` branch
4. Create a branch for each of the following two branches from the xtuml/internal repository:
  * xtuml_editor_builder
  * xtuml_editor  
  Use names that append the version number to the existing branch names (e.g. `xtuml_editor_builder_v4.1.16`).  These 
are the branches you should work in while creating this release.  
5. Bump the version in the `xtuml_editor_builder_<version>` branch [1] to the desired version
for the new xtUML Editor.
6. Update the Release Notes and What's New documents.  These are found under
com.mentor.nucleus.bp.doc.
7. Build the `xtuml_editor_builder_<version>` branch on the build server.  This shall be the 
binary release of the xtUML Editor.  
8. When the build is complete, test this release candidate on both Windows and 
Linux.  But, while waiting for the build, the remaining steps can be taken to 
complete the xtUML Editor source release.  
9. Merge xtuml/internal branch `xtuml_editor_builder_<version>` into  
xtuml/internal `xtuml_editor_<version>`.  This is merging the binary release branch
changes into what will be the source code release.  
10. Move the `internal/xtuml_editor_<version>` branch code into the editor repository.  To do this:    
  * branch the xtuml/editor repository to create a working branch named `<issue>_update_OSS_source`
  * delete all `xtuml/editor/src/*` projects that will be updated. All projects are deleted
except for the following:
      * doc-editor
      * org.xtuml.sql.upgrade
      * org.xtuml.sql.upgrade.bin  
11. Commit the deletion of files  
12. Copy the new `src/*` from your local machine's location for the `internal/xtuml_editor_<version>` branch into the editor branch created in step 10.  Commit the new source.  
13. Build and test the xtuml/editor branch.  Commit the generated source code. 
14. Once the binary and source release are both tested, promote the xtuml/editor branch into editor/origin/master  
15. Upload the installers to Amazon S3 [2]  
16. Update the xtUML.org website with the new Release Notes 
  * To do this, send the document to admin@xtUML.org and they will post it to the site.
17. Notify Jayne she can do a mailing indicating a new release of xtUML Editor is available  
18. Post to the xtuml.org forum indicating the new release is available  
19. Bump the BP development version past the xtUML Editor version  
20. Done  
