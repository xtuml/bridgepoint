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
<a id="2.3"></a>2.3 [pyrsl pull request #10](https://github.com/xtuml/pyrsl/pull/10)    

### 3. Background

BridgePoint currently ships with older versions of the python-based generator.  On 
Windows the generator is version 0.6.0.  On mac and linux the generator is version
0.5.0.

The xtUML development team desires to move to a newer version. The goal is to take
an incremental step that allows the tool to move forward and not get stale.  

### 4. Requirements

4.1  Build BridgePoint with modified generator version 1.0    
4.2  Build sample projects with modified generator version 1.0  

### 5. Work Required

5.1  The 0.x versions of generator print out warnings about schema mismatches when
building BridgePoint.  A __lot__ of warnings.  Recently [2.3] a new flag `-qim` was added
to the pyrsl master branch to "quiet insert mismatches".  
5.1.1  Apply the same enhancement to the `maintenance/1.0` branch.  
5.1.2  Bump the version number to 1.0.1.   

5.2 Build process and code generation updates   
5.2.1  bridgepoint  
5.2.1.1  Search all `generate.xml` files in the repo.  Update calls to `xtumlmc_gen_erate` to 
pass `-qim` as the first argument.   
5.2.1.2  The new generator does not populate uninitialized variables during load with 0.  The values
are stored in memory as python's None value.  In several places during a "select ... where" test 
we previously tested a value against 0.  These checks are failed using the new generator with an 
error saying something similar to "NoneType cannot be found".  The checks are changed to use python's 
"not" operation to test to see if the value is populated or not. Like this:   
```
-  .select many child_specs related by tree_node->T_TPS[R1000] where (selected.Prev_TPS_ID == 0)
+  .select many child_specs related by tree_node->T_TPS[R1000] where (not selected.Prev_TPS_ID)
```
5.2.1.3  `org.xtuml.bp.io.mdl` and `org.xtuml.bp.ui.text` generation  
Prior work was performed in the io.mdl generation to stop using persisted gen db file and 
multiple build targets and instead just generate all the code in one target without a persisted 
gen db.  That work was never fully implemented.  This change completes that work and puts it 
into active use. 

5.2.2  bptest  
5.2.2.1  Search all `generate.xml` files in the repo.  Update calls to `xtumlmc_gen_erate` to 
pass `-qim` as the first argument.   
5.2.2.2  Modify a "select .. where" to explicitly compare desired attributes rather than simply 
relying on referentials.  This is a common change for newer generators that work around incomplete
PEI data that may not have set values for all referentials.  

5.3  Put the new generator into packaging
5.3.1  Create new releases of generator 1.0.1 and publish them on github   
5.3.2  Update the `gen_erate.pyz` and `gen_erate.exe` in the `xtuml/mc` repository
  at `mc/bin` and `mc/bin/win` respectively.   

### 6. Implementation Comments

6.1  Build experimentation has shown that this version of generator does not provide and
build speed enhancements over the prior versions.  Changes that really affect build
times are part of post-1.0 commits.  

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
.gitignore
rsl/gen_erate.py
rsl/version.py
setup.py
tests/test_cli.py
</pre>

### End

