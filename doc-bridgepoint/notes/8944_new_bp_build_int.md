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
+      xbuild_path.concat(".exe");
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


</pre>

End
---

