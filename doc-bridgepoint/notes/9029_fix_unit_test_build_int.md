---

This work is licensed under the Creative Commons CC0 License

---

# Fix Unit Test Builds after Segregation 
### xtUML Project Implementation Note

1. Abstract
-----------
The unit tests for BridgePoint were moved from the bridgepoint repository into their own repository named, bptest, per [[2.2]](#2.2). This caused the unit test projects to no longer build due to relative paths to the Java model compiler contained in the bridgepoint repository.  

2. Document References
----------------------  
<a id="2.1"></a>2.1 [BridgePoint DEI #9029](https://support.onefact.net/issues/9029) Fix unit test build  
<a id="2.2"></a>2.2 [BridgePoint DEI #8942](https://support.onefact.net/issues/8942) Segregate Tests from Source  
<a id="2.3"></a>2.3 [Developer Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md)  
<a id="2.4"></a>2.4 [HOWTO Run BridgePoint Unit Tests](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md)  


3. Background
-------------
BridgePoint is built using the Java model compiler in the MC-Java directory of the bridgepoint repository. The build uses Apache Ant and Maven and consists of many projects. Some projects are concerned with the build of the BridgePoint tool, and some projects are concerned with testing the BridgePoint tool. These latter projects were moved into their own repository.  

A common repository guarantees every project in it a common root, so relative paths are guaranteed to work. Across repositories, there is no guarantee of a common root. Common practice is to co-locate repositories, but it isn't required. Relative paths are no longer assured to work.  

4. Requirements
---------------

4.1 Developers can build BridgePoint per [[2.3]](#2.3)  

4.2 Developers can build BridgePoint unit tests per [[2.4]](#2.4)  
4.2.1 Unit tests will be imported into the the workspace; no copy/link of projects into another repository.  


5. Work Required
----------------

<a id="5.1"></a>5.1 Create a method for specifying the repository paths without requiring the developer to place repositories in hard-coded locations.  
5.1.1 Investigation resulted in adding paths via ant properties for the bridgepoint MC-Java project and bptest repository. Use of the git_work_tree variable allowed finding of absolute paths of projects in the workspace.  
* Window > Preferences > Ant > Runtime > Properties

5.2 Find and replace relative paths with fully qualified pathnames found in [[5.1]](#5.1)   

5.3 Verify builds  
5.3.1 Build of BridgePoint without importing unit test projects.  
5.3.2 Build of unit test projects.

6. Implementation Comments
--------------------------

6.1 MC-Java project contains build.xml and common.xml, which are required to build all the projects.  
6.1.1 Relative paths in these files are evaluated relative to the repository for the project being built.  
6.1.2 Some parameters of build.xml targets used relative paths.  
6.1.3 common.xml used to define the mcj_path property used to find the MC-Java project. This was replaced with an ant property of the same name.  


6.2 Almost all projects have a generate.xml file that needed updated in both repositories.  
6.2.1 Most changes in the bridgepoint repository was just changing the relative path to MC-Java to an absolute path.  

6.3 Many other xml files in bptest required changes, as well as some arc files with relative paths.  

7. Unit Test
------------

7.1 Follow steps in [[2.3]](#2.3) and build BridgePoint.   
7.1.1 Ensure BridgePoint build runs.  

7.2 Follow steps in [[2.4]](#2.4) and build unit tests.  
7.2.1 Ensure unit tests can be run.
* At the time of this writing, the unit tests would fail due to the changes in [[2.2]](#2.2).  

8. User Documentation
---------------------
Modification of [[2.4]](#2.4) was made with respect to repository structure changes; no modification was required for the changes outlined in this document.  

9. Code Changes
---------------
Fork/Repository: [lwriemen/bptest](https://github.com/lwriemen/bptest.git)  
Branch: [9029_fix_unit_test_build](https://github.com/lwriemen/bptest/tree/9029_fix_unit_test_build)   

<pre>
src/org.xtuml.bp.als.oal.test/pom.xml
src/org.xtuml.bp.core.test/arc/create_action_test.arc
src/org.xtuml.bp.core.test/arc/generate_rto_move_matrix.arc
src/org.xtuml.bp.core.test/generate.xml
src/org.xtuml.bp.core.test/pom.xml
src/org.xtuml.bp.debug.ui.test/plugin.xml
src/org.xtuml.bp.debug.ui.test/pom.xml
src/org.xtuml.bp.io.mdl.test/pom.xml
src/org.xtuml.bp.model.compare.test/generate.xml
src/org.xtuml.bp.model.compare.test/pom.xml
src/org.xtuml.bp.search.test/pom.xml
src/org.xtuml.bp.test/plugin.xml
src/org.xtuml.bp.test/pom.xml
src/org.xtuml.bp.ui.canvas.test/generate.xml
src/org.xtuml.bp.ui.canvas.test/plugin.xml
src/org.xtuml.bp.ui.canvas.test/pom.xml
src/org.xtuml.bp.ui.canvas.test/test.xml
src/org.xtuml.bp.ui.explorer.test/pom.xml
src/org.xtuml.bp.ui.properties.test/generate.xml
src/org.xtuml.bp.ui.properties.test/pom.xml
src/org.xtuml.bp.ui.text.test/pom.xml
src/org.xtuml.bp.ui.text.test/test.xml
src/org.xtuml.bp.welcome.test/pom.xml
src/org.xtuml.bp.welcome.test/test.xml
src/org.xtuml.internal.test/pom.xml
</pre>

Fork/Repository: [lwriemen/bridgepoint](https://github.com/lwriemen/bridgepoint.git)  
Branch: [9029_fix_unit_test_build](https://github.com/lwriemen/bridgepoint/tree/9029_fix_unit_test_build)   

<pre>
releng/org.xtuml.bp.releng.parent.tests/pom.xml
src/MC-Java/build.xml
src/MC-Java/common.xml
src/org.xtuml.bp.als/generate.xml
src/org.xtuml.bp.cli/generate.xml
src/org.xtuml.bp.compare/generate.xml
src/org.xtuml.bp.core/generate.xml
src/org.xtuml.bp.debug.ui/generate.xml
src/org.xtuml.bp.docgen/generate.xml
src/org.xtuml.bp.io.core/generate.xml
src/org.xtuml.bp.io.image/generate.xml
src/org.xtuml.bp.io.mdl/generate.xml
src/org.xtuml.bp.mc.c.source/generate.xml
src/org.xtuml.bp.mc.cpp.source/generate.xml
src/org.xtuml.bp.mc.java.source/generate.xml
src/org.xtuml.bp.mc.none/generate.xml
src/org.xtuml.bp.mc.systemc.source/generate.xml
src/org.xtuml.bp.mc.template/generate.xml
src/org.xtuml.bp.mc.template/templates/model_compiler/generate.xml
src/org.xtuml.bp.mc/generate.xml
src/org.xtuml.bp.model.compare/generate.xml
src/org.xtuml.bp.ui.canvas/generate.xml
src/org.xtuml.bp.ui.explorer/generate.xml
src/org.xtuml.bp.ui.graphics/generate.xml
src/org.xtuml.bp.ui.marking/generate.xml
src/org.xtuml.bp.ui.properties/generate.xml
src/org.xtuml.bp.ui.sem/generate.xml
src/org.xtuml.bp.ui.session/generate.xml
src/org.xtuml.bp.ui.text/generate.xml
src/org.xtuml.bp.ui.tree/templates/generate.xml
src/org.xtuml.bp.x2m/generate.xml
utilities/build/preferences/org.eclipse.ant.core.prefs
</pre>  
End
---

