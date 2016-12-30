---

This work is licensed under the Creative Commons CC0 License

---

# Build BridgePoint with MC bits in toosl/mc folder
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes changes to the BridgePoint ant build configuration
files to work with the new BridgePoint model compiler layout on disk.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8944](https://support.onefact.net/issues/8944)    
<a id="2.2"></a>2.2 [BridgePoint DEI #8495](https://support.onefact.net/issues/8495) Build BridgePoint with maven    

3. Background
-------------
Recent work [[2.2]](#2.2) added maven build configuration around the BridgePoint
build.  As part of that work, the model compiler files were moved from under
each of the unique model compiler plugins to a top-level ```tools/mc/```
folder.   

This work futher moves away from the packaged model compiler files by making the
BridgePoint build itself work from the ```tools/mc/``` folder rather than the
```org.xtuml.bp.mc.c.souce/mc3020/``` folder in the git repository.    

4. Requirements
---------------
4.1 BridgePoint shall build from a local BridgePoint development environment.     
4.2 BridgePoint shall build from the hudson build server.   

5. Work Required
----------------
5.1  Create ```MC-Java/common.xml``` to consolidate common variables that all
  of our ```generate.xml``` scripts use.     
```
  <property name="util_bin_dir" value="${eclipse.home}/tools/mc/bin"/>
  <property name="mcj_path" value="../MC-Java" />
  <property name="xtumlmc_build" value="${util_bin_dir}/xtumlmc_build"/>
        
  <condition property="isWindows" value="true" else="false">
    <os family="windows" />
  </condition>

  <target name="configureWindows" if="isWindows">
    <property name="xtumlmc_build" value="${util_bin_dir}/xtumlmc_build.exe"/>
  </target>
```  
5.1.1  Note that ```eclipse.home``` is set by the eclipse development environment
  when building BP inside eclipse.  Maven does not have access to this value on
  its own, so on the build server, we configured hudson to pass in the correct
  setting for this variable when invoking maven.  

5.2  Look at the ```build.xml``` and ```generate.xml``` files that control the
  BridgePoint build.   
5.2.1  Add an import: ```<import file="../MC-Java/common.xml"/>```  
5.2.2  Find all the places where ```xtumlmc_build.exe``` was called and change to 
  use the new property ```${xtumlmc_bild}```. 
5.2.3  Remove ```eclipse-home``` definitions in favor of ```eclipse.home```
5.2.4  Remove per-file ```util_bin_dir``` defintions, it is now inherited from the 
  common import.   

6. Implementation Comments
--------------------------
6.1  Get rid of xtumlmc_build.exe on Linux/Mac   
6.1.1  Our current model compiler tooling is set up to always run the 
  ```xtumlmc_build``` script on all Operating Systems as ```xtumlmc_build.exe```. 
  This is confusing on non-Windows OSes because we copy the perl script to 
  have the ```.exe``` extension.   
6.1.2  This work takes the opportunity to be smarter about calling this script
  properly.  That is, invoking the script itself on Linux/Mac and calling the 
  compiled script (.exe version) on Windows.  
6.1.3  This is supported in the BridgePoint build itself by a new file
  ```MC-Java/common.xml``` that adds OS-specific configuration of the xtumlmc_build
  invocation.  
6.1.4  It is supported in the BridgePoint runtime (user build invocations) by a
  modification to the ```build_setting.properties``` file in each mc plugin to 
  point at ```xtulmc_build``` rather than ```xtumlmc_build.exe``` and adding a
  small logic stanza when setting up the TOOL_LOCATION value in ```Model Compiler.launch```:
```
  String xbuild_path = AbstractProperties.getPropertyOrDefault(properties,
      AbstractProperties.XBUILD_LOCAL_LOCATION);
+  if( System.getProperty("os.name").startsWith("Windows") && 
+        !xbuild_path.endsWith(".exe") &&
+        !xbuild_path.endsWith(".EXE")) {
+      xbuild_path = xbuild_path.concat(".exe");
+  }
  BuilderManagement.replaceBuilderInfo(launchFile,
     AbstractNature.LAUNCH_ATTR_TOOL_LOCATION,
     homedir + xbuild_path);
```  
6.1.5  This makes the invocation much clearer, and it also makes it easier for
  BridgePoint developers on non-Windows machine to link ```tools/mc/``` to their
  git ```xtuml/mc``` repository and not have to overwrite ```xtumlmc_build.exe```.   

7. Unit Test
------------
7.1 Successfully build locally.     
7.2 Successfully build on the server.        

8. User Documentation
---------------------
8.1  Update Developer Getting Started Guide to not copy mc3020 folder into C source plugin.   

9. Code Changes
---------------
Fork/Repository: keithbrown
Branch: 8944_new_bp_build     

<pre>
 doc-bridgepoint/notes/8944_new_bp_build_int.md     | 122 +++++++++++++++++++++
 .../process/Developer Getting Started Guide.md     |   4 -
 releng/org.xtuml.bp.mctools/pom.xml                |   6 -
 src/MC-Java/build.xml                              |  22 +---
 src/MC-Java/common.xml                             |  22 ++++
 src/MC-Java/java.arc                               |  14 ++-
 src/org.xtuml.bp.als/generate.xml                  |  19 ++--
 src/org.xtuml.bp.cli/generate.xml                  |  11 +-
 src/org.xtuml.bp.compare/generate.xml              |  13 +--
 src/org.xtuml.bp.core.test/generate.xml            |  38 +++----
 src/org.xtuml.bp.core/generate.properties          |   6 +-
 src/org.xtuml.bp.core/generate.xml                 |  39 +++----
 src/org.xtuml.bp.debug.ui/generate.xml             |  11 +-
 src/org.xtuml.bp.docgen/generate.xml               |  11 +-
 src/org.xtuml.bp.io.core/generate.xml              |  15 +--
 src/org.xtuml.bp.io.image/generate.xml             |   9 +-
 src/org.xtuml.bp.io.mdl/generate.xml               |  31 +++---
 src/org.xtuml.bp.mc.c.source/build.properties      |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.c.source/generate.xml          |   9 +-
 src/org.xtuml.bp.mc.c.source/log_dir/.gitignore    |   1 -
 src/org.xtuml.bp.mc.c.source/log_dir/README.txt    |   1 -
 src/org.xtuml.bp.mc.c.source/mc3020/.gitignore     |  12 --
 src/org.xtuml.bp.mc.c.source/mc3020/README.txt     |   1 -
 src/org.xtuml.bp.mc.cpp.source/build.properties    |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.cpp.source/generate.xml        |   9 +-
 src/org.xtuml.bp.mc.cpp.source/log_dir/.gitignore  |   1 -
 src/org.xtuml.bp.mc.cpp.source/log_dir/README.txt  |   1 -
 src/org.xtuml.bp.mc.cpp.source/mc3020/.gitignore   |  12 --
 src/org.xtuml.bp.mc.cpp.source/mc3020/README.txt   |   1 -
 src/org.xtuml.bp.mc.java.source/build.properties   |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.java.source/generate.xml       |   9 +-
 src/org.xtuml.bp.mc.java.source/log_dir/README.txt |   1 -
 src/org.xtuml.bp.mc.java.source/mc3020/.gitignore  |  12 --
 src/org.xtuml.bp.mc.java.source/mc3020/README.txt  |   1 -
 src/org.xtuml.bp.mc.none/generate.xml              |   9 +-
 .../build.properties                               |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.systemc.source/generate.xml    |   9 +-
 .../log_dir/.gitignore                             |   1 -
 .../log_dir/README.txt                             |   1 -
 .../mc3020/.gitignore                              |  12 --
 .../mc3020/README.txt                              |   1 -
 src/org.xtuml.bp.mc.template/generate.xml          |   9 +-
 .../build_settings/build_setting.properties        |   2 +-
 .../templates/model_compiler/generate.xml          |   9 +-
 .../templates/model_compiler/log_dir/README.txt    |   1 -
 .../templates/model_compiler/mc3020/.gitignore     |  12 --
 .../templates/model_compiler/mc3020/README.txt     |   1 -
 src/org.xtuml.bp.mc/generate.xml                   |   9 +-
 .../src/org/xtuml/bp/mc/AbstractProperties.java    |   2 +-
 .../org/xtuml/bp/mc/MCBuilderArgumentHandler.java  |   5 +
 src/org.xtuml.bp.model.compare.test/generate.xml   |  11 +-
 src/org.xtuml.bp.model.compare/generate.xml        |  15 +--
 src/org.xtuml.bp.ui.canvas.test/generate.xml       |  19 ++--
 src/org.xtuml.bp.ui.canvas.test/test.xml           |  28 ++---
 src/org.xtuml.bp.ui.canvas/generate.xml            |  21 ++--
 src/org.xtuml.bp.ui.explorer/generate.xml          |  15 +--
 src/org.xtuml.bp.ui.graphics/generate.xml          |   7 +-
 src/org.xtuml.bp.ui.properties.test/generate.xml   |  24 ++--
 src/org.xtuml.bp.ui.properties/generate.xml        |  14 +--
 src/org.xtuml.bp.ui.sem/generate.xml               |  11 +-
 src/org.xtuml.bp.ui.session/generate.xml           |   8 +-
 src/org.xtuml.bp.ui.text.test/test.xml             |  18 ++-
 src/org.xtuml.bp.ui.text/generate.xml              |  18 ++-
 src/org.xtuml.bp.ui.tree/generate_schema.xml       |  10 +-
 src/org.xtuml.bp.ui.tree/templates/generate.xml    |   8 +-
 src/org.xtuml.bp.welcome.test/test.xml             |  28 ++---
 src/org.xtuml.bp.welcome/generate.xml              |  14 +--
 src/org.xtuml.bp.x2m/generate.xml                  |  11 +-
 72 files changed, 348 insertions(+), 495 deletions(-)

</pre>

End
---

