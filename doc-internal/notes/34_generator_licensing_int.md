---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Generator can't locate display key when build ran on a referenced project in a workspace
### xtUML Project Implementation Note



1. Abstract
-----------
This note details the implementation of fixes for a bug in BridgePoint where the tool
fails to pass the license key "MGLS_ATTR_DISPLAY" successfully from eclipse to generator
causing a generator license failure.

2. Document References
----------------------
[1] Issues 34, https://github.com/xtuml/internal/issues/34 - This issue  
[2] DEI dts0100995063 - This issue's CQ twin  
[3] Issue 34 Design note. https://github.com/xtuml/internal/blob/master/doc-internal/notes/34_generator_licensing_dnt.md  

3. Background
-------------
The current implementation of the license handling works for projects that are physically 
located inside the workspace.  It fails for projects that are physically located outside
the workspace on disk.  

4. Requirements
---------------
See [3].

5. Work Performed
-----------------
5.1 Updated BridgePointLicenseManager.writeXTUMLDisplayFile(); to take an argument that is the 
  path to the currently building project's gen/code_generation folder.  
5.2 Updated the implementation of BridgePointLicenseManager.writeXTUMLDisplayFile(path); to write 
  the .mgxtumldisplay file to the path argument (creating the folder if it doesn't already exist).   
5.3 In generator, modify u_bplic.c::get_display(). Change the relative path traversal to look for 
  the .mgxtumldisplay file in the project's gen/code_generation folder (actually, just the current
  directory as generator runs in gen/code_generation) rather than the workspace.  

6. Implementation Comments
--------------------------
6.1  The prior implementation wrote the file as <workspace>/.metadata/.xtumldisplay.  The author found
  that putting the file as gen/code_generation/.xtumldisplay actually caused a problem in xtumlmc_build.
  Around line 1296 of that script, we look for files in gen/code_generation matching the extensions .sql, 
  .bak, .xtuml and treat them as model input to the generator.  As noted in section 5, the file is now 
  named .mgxtumldisplay in order to avoid passing this pattern match with this file.  
6.2  In order to make the required change to generator, the author had to migrate the generator source
  from CVS under the "model_compilers" project into a new repository on github.com/xtuml/generator.
  The changeset in the branch represents updates to the build process and instructions to support building
  on the server svr-azt-eng-03 in the workspace c:\workspace\Generator_git with the source code for generator
  and the model compiler coming from git.  
6.3  This work had some conflicts with the promotion that preceeded it for Issue 35.  While resolving these
  conflicts that author found problems with the new "smart pre-build" feature.  That feature intends to 
  only re-export the model data to gen/code_generation/<<project>>.sql when a model file from the project is
  newer than the most recent exported data.  
6.3.1  The author reworked the flow of control and the Java API calls used in order to make sure the feature
  behaves as expected in all cases.  
6.3.2  The author added a new JUnit test in the bp.welcome test suite that performs a build, does a rebuild, 
  then modifies the model and rebuilds again.  The test verifies that the output file from pre-builder is left
  unchanged before the model is modified and is re-exported after the model is modified.

7. Unit Test
------------
See [3].  The author built the plug-ins that contained the changes and build generator with the fix in place.
The new files were put into Windows and Linux installations and executed in GUI and CLI modes.  

Since the Welcome test is updated with this work, its JUnit suite obviously needs to be re-run.

8. Code Changes
---------------
xtuml/internal branch name: 34_generator_licensing  
Pull request: https://github.com/xtuml/internal/pull/36/files  
Master Root ID to diff against: 98c0ad5
<pre>
M       src/com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/util/BridgePointLicenseManager.java
M       src/com.mentor.nucleus.bp.mc.mc3020/src/com/mentor/nucleus/bp/mc/mc3020/ExportBuilder.java
M       src/com.mentor.nucleus.bp.mc.mc3020/src/com/mentor/nucleus/bp/mc/mc3020/MCBuilderArgumentHandler.java
M       src/com.mentor.nucleus.bp.mc.mc3020/src/com/mentor/nucleus/bp/mc/mc3020/ModelCompiler.java
M       src/com.mentor.nucleus.bp.mc/src/com/mentor/nucleus/bp/mc/AbstractExportBuilder.java
M       src/com.mentor.nucleus.bp.mc/src/com/mentor/nucleus/bp/mc/MCBuilderArgumentHandler.java
M       src/com.mentor.nucleus.bp.welcome.test/src/com/mentor/nucleus/bp/welcome/test/WelcomePageTestGPS.java
</pre>

xtuml/generator branch name: 2_xtumldisplay_change  
Pull request: https://github.com/xtuml/generator/pull/3/files  
<pre>
A       src/gen_erate/.project
M       src/gen_erate/README.txt
M       src/gen_erate/bp_prod/win32/7.1B/build_scripts/get_host_platform.inc
M       src/gen_erate/bp_source/win32/7.1B/main/src/gen_erate/main_gen_erate.cc
M       src/gen_erate/bp_source/win32/7.1B/utility/src/u_bplic.c
M       src/gen_erate/create_bp_build
A       src/mc_dbms/.project
A       src/tools/.project
</pre>  

End
---
