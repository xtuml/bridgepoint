---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Code Cleanup
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes some simple code cleanup and changes to make BridgePoint
a bit more user-friendly.

2. Document References
----------------------
[1] Issues 2, https://github.com/xtuml/internal/issues/2  
[2] CQ DEI dts0101012005 - Transition to github phase 3 - code cleanup   
[3] CQ DEI dts0101011716 - Stop requesting xtumlvrfy license  
[4] CQ DEI dts0101012181 - Remove unused JUnits for non-globals and non-generics cases  

3. Background
-------------
With the transition to github complete, we have the opportunity to clean up and
remove some files and code that are no longer needed.  We can also take the 
opportunity to make a few simple changes to the development environment and 
address a concern recently raised by a customer.  

4. Requirements
---------------
4.1 Remove old build server stuff from bp.core/tools  
4.2 Rename the JUnit tests to remove the "Use Globals" and "Generics" modifiers  
4.3 Stop requesting xtumlvrfy license  

5. Work Required
----------------
5.1  Go through the files and directories under bp.core.  Remove model debugger
  and tool pieces that are no longer used.  
  
5.2  The BridgePoint development went through a transition in 2011 and 2012 when
  we upgraded the tool to move away from specialized packages and only use generic
  packages.  We also moved to a system of using global core data types rather
  than per-project core data types.  To facilitate testing during these transitions
  we had duplicate JUnit test cases where one was a "non-globals" version and one
  was a "use globals" version.  In some cases the same was true for specialized
  package and generic package-based tests.  In 2013 we completely finished the 
  migration to globals and GP models.   This made the old JUnit tests obsolete and
  we stopped running them.   We have used the new JUnit configs for nearly a year.
  Since the "UseGlobals" and "Generics" tests are now the norm, they are renamed
  to remove "UseGlobals" and "Generics" from the JUnit launch name and the test
  checklist is updated.  The underlying java classes are not renamed, so there
  is no fallout in the code or expected results.  Issue [4] is raised to clean
  out the old tests later.  
  
5.3  A customer running an evaluation with BridgePoint 4.0.6 tried to build the
  MicrowaveOven model.  They then inspected the license log and saw messages
  like:  

```
9:56:51 (mgcld) UNSUPPORTED: "xtumlvrfy" ...
```

  Since the bpumlexecute_c composite is the only way to get a verifier license 
  under the new licensing scheme, and it only contains xtumlsysvrfy, we shall 
  change BridgePoint to stop ever requesting xtumlvrfy.  It should only request 
  xtumlsysvrfy, even for single-component models.  This will make the problem 
  message stop showing up in the license log.  
    
6. Implementation Comments
--------------------------
6.1  We had originally considered removing the XMI Export plug-in from the tool.
  While removing the tool is, in itself, easy, there are fallout effects for any
  old xtUML Projects customers have that still have the XMI Export builder.  Removing 
  the builder from the code base would cause red errors in the builder list.  To
  handle this cleanly, we would need to remove the builder from existing projects.
  Most likely this would be via a popup dialog like we have for the EDGE to CDT 
  builder update.  Rather than develop this upgrade dialog and have the tool
  process projects on each load, we have decided to simply let the XMI Export
  plug-in live on in its current state.  The plug-in exists but it has no
  functionality.  The build() function simply returns null.  

6.2 With CVS we applied a nightly build tag to the code after building if there 
  were code changes. We wondered about doing this for git builds.  After consideration
  we realized this is not needed because the repository is "tagged" with a unique
  SHA ID for every commit.  By looking at the repository network graph we can
  see what the tag was for every nightly build.  There is no need to apply another
  tag.    

6.3 While inspecting the code for references to the XMI Export plug-in, the author
  found a couple of files that had large CVS log comment blocks included.  These
  CVS log blocks were removed.  
    
7. Unit Test
------------
7.1  Development environment build completes successfully  
 
7.2  JUnits run successfully   

7.3  Test the verifier licensing  
  - Setup up a license server with bpumlexecute_c
  - Setup the local environment to point at the license server
  - Start BridgePoint
  - Create MicrowaveOven sample project
  - Launch the MicrowaveOven xtUML eXecute launch config
  - Open the license log file
  - __R__ There is a request for bpumlexecute_c.  There is no request for xtumlvrfy
  
8. Code Changes
---------------
Branch name: 2_code_cleanup

```
com.mentor.nucleus.bp.compare/generate.xml

com.mentor.nucleus.bp.core/doc/external/manuals/mm/
    .gitignore
com.mentor.nucleus.bp.core/md/pt_debugger.cmd
com.mentor.nucleus.bp.core/md/stylesheets/md/
    no_logging.xsl
com.mentor.nucleus.bp.core/md/stylesheets/md/
    script_utils.xsl
com.mentor.nucleus.bp.core/md/stylesheets/md/
    sheets.xml
com.mentor.nucleus.bp.core/md/stylesheets/md/state.xsl
com.mentor.nucleus.bp.core/md/stylesheets/md/
    statement.xsl
com.mentor.nucleus.bp.core/md/stylesheets/md/
    user_invoke.xsl
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/
    core/util/BridgePointLicenseManager.java
com.mentor.nucleus.bp.core/tools/
    configure_build_process.sh
com.mentor.nucleus.bp.core/tools/
    configure_external_dependencies.sh
com.mentor.nucleus.bp.core/tools/
    create_nightly_build.sh
com.mentor.nucleus.bp.core/tools/
    create_release_functions.sh
com.mentor.nucleus.bp.core/tools/
    create_tiger_release.sh
com.mentor.nucleus.bp.core/tools/
    obfuscate_template.xml
com.mentor.nucleus.bp.core/tools/rc.mgclm
com.mentor.nucleus.bp.core/tools/run_branch_build.bat
com.mentor.nucleus.bp.core/tools/run_nightly_build.bat
com.mentor.nucleus.bp.core/tools/run_release_build.bat
com.mentor.nucleus.bp.core/tools/start_mgclm
com.mentor.nucleus.bp.core/tools/stop_mgclm
com.mentor.nucleus.bp.core/tools/tag_bp_nb.sh
com.mentor.nucleus.bp.core/tools/tag_bp.sh
com.mentor.nucleus.bp.core/tools/tag_remove.sh
com.mentor.nucleus.bp.core/tools/build/convert.xml
com.mentor.nucleus.bp.core/tools/build/extendmap.arc
com.mentor.nucleus.bp.core/tools/build/id2str.pl
com.mentor.nucleus.bp.core/tools/build/makemap.arc
com.mentor.nucleus.bp.core/tools/build/mapids.pl
com.mentor.nucleus.bp.core/tools/build/relocate.pl
com.mentor.nucleus.bp.core/tools/build/strip_reloc.pl
com.mentor.nucleus.bp.core/tools/cvstools/
    change_model_extension.sh
com.mentor.nucleus.bp.core/tools/cvstools/
    rename_modules.chk
com.mentor.nucleus.bp.core/tools/cvstools/
    rename_modules.sh
com.mentor.nucleus.bp.core/tools/eclipse_settings/
    preferences.epf
com.mentor.nucleus.bp.core/tools/pt_ant/ExecTask.java
com.mentor.nucleus.bp.core/tools/pt_ant/Readme.txt
com.mentor.nucleus.bp.core/tools/pt_ant/
    set_exec_call_timeouts.sh
com.mentor.nucleus.bp.core/tools/SCM_Bugzilla_Glue/
    Subversion/append_to_bz
com.mentor.nucleus.bp.core/tools/SCM_Bugzilla_Glue/
    Subversion/logmsg.verify
com.mentor.nucleus.bp.core/tools/SCM_Bugzilla_Glue/
    Subversion/logmsg-parsing.pl
com.mentor.nucleus.bp.core/tools/SCM_Bugzilla_Glue/
    Subversion/post-commit
com.mentor.nucleus.bp.core/tools/SCM_Bugzilla_Glue/
    Subversion/pre-commit
com.mentor.nucleus.bp.core/tools/SCM_Bugzilla_Glue/
    Subversion/Readme.txt
com.mentor.nucleus.bp.core/.template

com.mentor.nucleus.bp.debug.ui/src/com/mentor/
    nucleus/bp/debug/ui/launch/BPLaunchDelegate.java

com.mentor.nucleus.bp.ui.properties/generate.xml

doc-internal/process/templates/checklists/
    Unit_test_checklist.chk
doc-internal/process/templates/launch_configs/
    BridgePoint Unit Tests.launch
doc-internal/process/templates/launch_configs/
    Core Test.launch
doc-internal/process/templates/launch_configs/
    Core Test 2.launch
doc-internal/process/templates/launch_configs/
    Core Test - Existing Projects.launch
doc-internal/process/templates/launch_configs/
    Core Test - Existing Projects Generics.launch
doc-internal/process/templates/launch_configs/
    Core Test - System Level Tests.launch
doc-internal/process/templates/launch_configs/
    Core Test - Workspace Setup.launch
doc-internal/process/templates/launch_configs/
    Core Test - Workspace Setup Generics.launch
doc-internal/process/templates/launch_configs/
    IO MDL PkgCM Tests.launch
doc-internal/process/templates/launch_configs/
    IO MDL Tests.launch
doc-internal/process/templates/launch_configs/
    IO MDL Tests 2.launch
doc-internal/process/templates/launch_configs/
    Parse All Test.launch
doc-internal/process/templates/launch_configs/
    UI Canvas CCP Test.launch
doc-internal/process/templates/launch_configs/
    UI Canvas Suite 1.launch
doc-internal/process/templates/launch_configs/
    UI Canvas Suite 2.launch
doc-internal/process/templates/launch_configs/
    UI Explorer Test.launch
doc-internal/process/templates/launch_configs/
    UI Properties Test.launch
doc-internal/process/templates/launch_configs/
    UI Text Test.launch
doc-internal/process/templates/launch_configs/
    UseGlobals Core Test 2 Generics.launch
doc-internal/process/templates/launch_configs/
    UseGlobals Core Test Generics.launch
doc-internal/process/templates/launch_configs/
    UseGlobals Core Test - System Level Tests.launch
doc-internal/process/templates/launch_configs/
    UseGlobals IO MDL PkgCM Tests Generics.launch
doc-internal/process/templates/launch_configs/
    UseGlobals IO MDL Tests 2 Generics.launch
doc-internal/process/templates/launch_configs/
    UseGlobals IO MDL Tests Generics.launch
doc-internal/process/templates/launch_configs/
    UseGlobals Parse All Test Generics.launch
doc-internal/process/templates/launch_configs/
    UseGlobals UI Canvas CCP Test.launch
doc-internal/process/templates/launch_configs/
    UseGlobals UI Canvas Suite 1.launch
doc-internal/process/templates/launch_configs/
    UseGlobals UI Canvas Suite 2.launch
doc-internal/process/templates/launch_configs/
    UseGlobals UI Explorer Test.launch
doc-internal/process/templates/launch_configs/
    UseGlobals UI Properties Test.launch
doc-internal/process/templates/launch_configs/
    UseGlobals UI Text Test.launch
doc-internal/process/templates/launch_configs/
    Working set.launch
```

End
---

