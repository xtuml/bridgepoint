---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Model Compiler Platform as a Service
### xtUML Project Implementation Note


1. Abstract
-----------
In this section, give a summary of the design that this note aims to
describe.

2. Document References
----------------------
[1] Issues 117, https://github.com/xtuml/internal/issues/117  
[2] CQ DEI dts0101010874 - Clear Quest original tracking issue    
[3] Node.js. http://nodejs.org/  
[4] MC-PaaS Prototype Notes.txt.  
[5] Issues 34, https://github.com/xtuml/mc/issues/34   
[6] 117_mc-paas_phase1_dnt.md  

3. Background
-------------
This note captures the implementation of the phase 1 prototype of MC-PaaS both
for the client and the server.

4. Requirements
---------------
See [6].

5. Work Required
----------------
5.1. On the client side, this work introduces a new plug-in ```com.mentor.nucleus.bp.mc.mcpaas```
  that works as specified in the design note [6].  
5.1.1  The new MC-PaaS plug-in will be promoted to the master branch of the repository
  but it will not yet be build by the build server or included in the nightly build.  
5.2. The author used the model compiler template plug-in to create the new MC-PaaS
  plug-in.  This required some updates to the template because it has not been used
  in quite a while.  Most notably, we removed all references and ties to the XMI Export
  plug-in from the MC plug-in because XMI Export is no longer valid.  The instructions
  document ```HOWTO-create-new-ModelCompiler-plugin.md``` is migrated from CVS to 
  the git process documents.  
5.3. On the server side, we implemented a node.js-based server as designed.  The code
  for the server as well as a daemon init script are put into the xtuml/mc repository
  under a new folder.  

6. Implementation Comments
--------------------------
None.

7. Unit Test
------------
See [6].

8. Code Changes
---------------
xtuml/internal Branch name: 117_mc-paas_proto

<pre>

com.mentor.nucleus.bp.mc/src/com/mentor/nucleus/
    bp/mc/AbstractNature.java

com.mentor.nucleus.bp.mc.mcpaas/.settings/
    org.eclipse.jdt.core.prefs
com.mentor.nucleus.bp.mc.mcpaas/bin/.gitignore
com.mentor.nucleus.bp.mc.mcpaas/build_settings/
    build_setting.properties
com.mentor.nucleus.bp.mc.mcpaas/defaults/
    launch_specification/Model Compiler.launch
com.mentor.nucleus.bp.mc.mcpaas/mc3020/.gitignore
com.mentor.nucleus.bp.mc.mcpaas/mc3020/
    default-manifest.xml
com.mentor.nucleus.bp.mc.mcpaas/mc3020/mcpaas.sh
com.mentor.nucleus.bp.mc.mcpaas/mc3020/README.txt
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/bridge.jap
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/bridge.mark
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/class.mark
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/datatype.jap
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/datatype.mark
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/dom_functions.arc
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/domain.jap
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/domain.mark
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/event.jap
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/event.mark
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/object.jap
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/populate.arc
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/registry.mark
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/sys_functions.arc
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/system.jap
com.mentor.nucleus.bp.mc.mcpaas/mc3020/schema/
    colors/system.mark
com.mentor.nucleus.bp.mc.mcpaas/META-INF/
    MANIFEST.MF
com.mentor.nucleus.bp.mc.mcpaas/src/com/mentor/
    nucleus/bp/mc/mcpaas/Activator.java
com.mentor.nucleus.bp.mc.mcpaas/src/com/mentor/
    nucleus/bp/mc/mcpaas/ExportBuilder.java
com.mentor.nucleus.bp.mc.mcpaas/src/com/mentor/
    nucleus/bp/mc/mcpaas/MCNature.java
com.mentor.nucleus.bp.mc.mcpaas/src/com/mentor/
    nucleus/bp/mc/mcpaas/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.mcpaas/.classpath
com.mentor.nucleus.bp.mc.mcpaas/.project
com.mentor.nucleus.bp.mc.mcpaas/build.properties
com.mentor.nucleus.bp.mc.mcpaas/generate.xml
com.mentor.nucleus.bp.mc.mcpaas/plugin.xml

com.mentor.nucleus.bp.mc.template/src/com/mentor/
    nucleus/bp/mc/template/ModelCompilerSection.java
com.mentor.nucleus.bp.mc.template/templates/
    model_compiler/generate.xml
com.mentor.nucleus.bp.mc.template/templates/
    model_compiler/java/MCNewProjectWizard.java
com.mentor.nucleus.bp.mc.template/generate.xml
com.mentor.nucleus.bp.mc.template/Readme.txt

doc-internal/notes/117_mc-paas/
    117_mc-paas_phase1_dnt.md
doc-internal/notes/117_mc-paas/
    117_mc-paas_phase1_int.md
doc-internal/notes/117_mc-paas/
    MC-PaaS Prototype Notes.txt
doc-internal/process/
    HOWTO-create-new-ModelCompiler-plugin.md



</pre>

xtuml/mc Branch name: 34_mcpaas_proto

<pre>

mc/mcpaas/server/.gitignore
mc/mcpaas/server/bpbuild.js
mc/mcpaas/server/jsLauncher.sh
mc/mcpaas/server/mcpaas

</pre>
End
---

