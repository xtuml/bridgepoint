---

This work is licensed under the Creative Commons CC0 License

---

# Enhance BP CLI to handle Java MC based projects
### xtUML Project Implementation Note

1. Abstract
-----------
As sub-tast for the work required in [2], to Move org.xtuml.bp.core build to 
standard pre-build style without split SQL. It is required to update  bp.cli 
plugin to know how to handle mc-java based plugin builds different than 
non-mc-java based plug-ins.

2. Document References
----------------------
[1] [BridgePoint DEI #8762](https://support.onefact.net/redmine/issues/8762)   
[2] [BridgePoint DEI #8508](https://support.onefact.net/redmine/issues/8508)  

3. Background
-------------
  BuildWorkbenchAdvisor is part of the BP CLI implementation. When it is created, 
there was any projects that needed to handle the extra arguments like the java 
pre-builder does in bp.core.

  Since it didn't need to be smart, it didn't have to care what the project 
pre-builder was (bp.mc.c.binary, bp.mc.c.source, bp.mc.cpp.source) as they were 
all the same.  So it just specified to default to the bp.mc.c.source 
implementation rather than read the .project and figure out which one to use.

4. Requirements
---------------
  By introducing java pre-builder -which has extra configuration options that 
the bp.mc.c.source pre-builder does not- BP CLI needs to be smarter to see if it 
needs to run the java pre-builder (with associated arguments) or just rely on 
the mc.c.source pre-builder (ExportBUilder) as before.

5. Work Required
----------------
  The current implementation of BuildWorkbenchAdvisor ignores this and just 
uses the bp.mc.c.ExportBuilder. The problem is that to build bp.core, it requires 
the extra configuration information that is specified in the .project for the 
java export builder. So BuildWorkbenchAdvisor needed to be  smarter by looking 
at the project data for the project being built. if it specifies to use the 
mc.java pre-builder then it should read the configuration data, pass it in via 
setArgs, then run the mc.java.ExportBuilder. Otherwise (java pre-builder is not 
specified in the project configuration), just run the mc.c.source ExportBuilder 
as before.

6. Implementation Comments
--------------------------
   The output SQL files (ooaofooa-*.sql) using CLI pre-build  do not match the output 
files from the GUI build. What is wrong is that there is some data that is 
going into ooaofooa-1 that should be going into ooaofooa-4 and vice versa.
For instance, the counts of INSERT INTO ACT_ between the -1 files, it is 
exactly the same as the difference between the -4 files.  
 
The issue is the Activity package, in the Dev build, the Activity package is 
parse and the ACT_ and V_ instances are put into the ooaofooa-4 file. In the
CLI build they are put into the ooaofooa-1 file. Since Activity is at the top 
alphabetically, CLI pre-build puts it -1 file

7. Unit Test
------------
_- Prepare a workspace with BP plug-ins.  
_- Run CLI pre-build for bp.core.  
_- Run BP Application for the same WS used by CLI pre-build. 
_- Disable pre-build for bp.core.  
_- Build bp.core and the rest of plug-ins.  
_R Build complete with no errors.  


8. User Documentation
---------------------
None.

9. Code Changes
---------------
Fork/Repository: < nmohamad/bridgepoint >
Branch: < 8762_Enhance_CLI_to_handle_Java_MC >

<pre>
doc-bridgepoint/notes/8762_Enhance_CLI_to_handle_Java_MC/
    8762_Enhance_CLI_to_handle_Java_MC.int.md
doc-bridgepoint/process/templates/launch_configs/x BP CLI Build.launch

org.xtuml.bp.cli/META-INF/MANIFEST.MF
org.xtuml.bp.cli/src/org/xtuml/bp/cli/BuildWorkbenchAdvisor.java

org.xtuml.bp.mc.java.source/src/org/xtuml/bp/mc/java/source/ExportBuilder.java

</pre>

End
---

