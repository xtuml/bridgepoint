---

This work is licensed under the Creative Commons CC0 License

---

# Build BridgePoint with generator 1.0
### xtUML Project Implementation Note


### 1. Abstract

This note describes the work performed to be able to build BridgePoint with a 
modified 1.0 version of python-based generator.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9911](https://support.onefact.net/issues/9911) Build BridgePoint with generator 1.0    
<a id="2.2"></a>2.2 [BridgePoint DEI #9481](https://support.onefact.net/issues/9481) Build BridgePoint with new generator    
<a id="2.3"></a>2.3 [BridgePoint DEI #xxx3](https://support.onefact.net/issues/xxx3) TODO: Add description here.  

### 3. Background

BridgePoint currently ships with older versions of the python-based generator.  On 
Windows the generator is version 0.6.0.  On mac and linux the generator is version
0.5.0.

The xtUML development team desires to move to a newer version. The goal is to take
an incremental step that allows the tool to move forward and not get stale.  

### 4. Requirements

4.1  Build BridgePoint with a version of generator later than 0.6.0    

### 5. Work Required

5.1  The 0.x versions of generator print out warnings about schema mismatches when
building BridgePoint.  An overload of warnings.  we   

5.2 Item 2  

TODO - do we want the CME generation stuff removed when going to generator 1.0?
* Maybe we don't need this since we are moving to the newest generator.  Maybe those
changes should be pulled out and just recorded for what was done as a jumpstart for the
next task of moving to an even newer generator.

### 6. Implementation Comments

6.1 


### 7. Unit Test

7.1  Build and test on server  
* Put the new generator in place on the build server in the installation of BridgePoint used to perform the builds
* Run a "build and test" build of the branch  

7.2  Check new generator on sample projects  
For each of Mac, Linux, and Windows:   
* Open a terminal
* Navigate to the install `tools/mc/bin/` folder and run either `./gen_erate.pyz -version` or `gen_erate.exe -version`  
* See the version printed in 1.0.1
* Open BridgePoint
* Create Microwave Oven and GPS Watch example projects  

7.2.1  For each of Microwave Oven and GPS Watch perform the following test  
* Build the project
* __R__ See build success
* Run the test execution with the compiled EXE
* __R__ See success
* Save the generated source code folder off to a temp location
* Put the prior version of generator in place in the eclipse installation
* Clean the generated source away and rebuild the project
* __R__ See build success
* diff the generated source code from the new and old generator
* __R__ See expected differences (temp variables) 

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint   
Branch: 9481_build_bp_updates

<pre>
doc-bridgepoint/notes/9911_build_bp_updates_int.md
src/MC-Java/build.xml
src/org.xtuml.bp.als/generate.xml
src/org.xtuml.bp.compare/generate.xml
src/org.xtuml.bp.core/arc/create_object_inspector.inc
src/org.xtuml.bp.core/arc/page.inc
src/org.xtuml.bp.core/generate.xml
src/org.xtuml.bp.io.core/arc/export_functions.inc
src/org.xtuml.bp.io.core/arc/gen_import_java.inc
src/org.xtuml.bp.io.mdl/generate.xml
src/org.xtuml.bp.model.compare/generate.xml
src/org.xtuml.bp.ui.canvas/generate.xml
src/org.xtuml.bp.ui.explorer/generate.xml
src/org.xtuml.bp.ui.properties/generate.xml
src/org.xtuml.bp.ui.session/generate.xml
src/org.xtuml.bp.ui.text/arc/create_editorinput_factories_java.arc
src/org.xtuml.bp.ui.text/arc/create_modeladapter_java.arc
src/org.xtuml.bp.ui.text/arc/create_plugin_xml.arc
src/org.xtuml.bp.ui.text/generate.xml
src/org.xtuml.bp.ui.tree/templates/generate.xml
</pre>

Fork/Repository: keithbrown/bptest   
Branch: 9481_build_bp_updates

<pre>
src/org.xtuml.bp.core.test/arc/create_action_test.arc
src/org.xtuml.bp.core.test/generate.xml
src/org.xtuml.bp.model.compare.test/generate.xml
src/org.xtuml.bp.ui.canvas.test/generate.xml
src/org.xtuml.bp.ui.properties.test/generate.xml
</pre>

Fork/Repository: keithbrown/pyrsl 
Branch: kb_1.0_qim  

<pre>
rsl/gen_erate.py
rsl/version.py
setup.py
tests/test_cli.py
</pre>

### End

