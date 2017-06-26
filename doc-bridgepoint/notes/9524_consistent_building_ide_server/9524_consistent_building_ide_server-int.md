---

This work is licensed under the Creative Commons CC0 License

---

# Support consistent building with the IDE and Server  
### xtUML Project Implementation Note

### 1. Abstract

This note describes the variations made during implementation as well as the code changes.  

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #9524](https://support.onefact.net/issues/xxx1) Consistent building with ide and build server  
<a id="2.2"></a>2.2 [Design Note: 9524_consistent_building_ide_server.md](https://github.com/travislondon/bridgepoint/blob/9524_consistent_building_ide_server/doc-bridgepoint/notes/9524_consistent_building_ide_server/9524_consistent_building_ide_server.md) Project design note  

### 3. Background

See [[2.2]](#2.2).  

### 4. Requirements

See [[2.2]](#2.2).  

### 5. Work Required

See [[2.2]](#2.2).  

### 6. Implementation Comments
6.1 Build server changes

A new environment variable is added and used with maven build scripts, bp_install_dir.  This allows for a common use throughout the scripts in both the UI and the build server.  The buildmt server configurations for jobs: Build_BridgePoint and bridgepoint, were updated to set this variable to match where BridgePoint is installed for Jenkins.  

During building xtext required an updated snapshot to build properly.  This can be done by using the -U maven option.  This is now set in the bridgepoint job configuration.  

6.2 Add build scripts to ease building and testing  

The following scripts have been added and the howtos updated accordingly.  They all simplification of configuration and build/test running.  

* utilities/build/build_configuration.sh - Contains configurable environment variables, sourced by prepare_build.sh and build_and_test_bp.sh  
* utilities/build/prepare_build.sh - Creates and configures a workspace for building, this includes project import and prebuilding  
* utilities/build/build_and_test_bp.sh - Starts the build at the parent releng project triggering a full build of BridgePoint with testing if INCLUDE_TESTS is true   
* utilities/build/build_project.sh - Used in the UI launch configurations allowing for tests to build and run from a common script   

6.3 Build order  

Testing showed that our build order was not being set perfectly, when running a Build All with the maven scripts this was causing test projects to fail builds.  The build order is modified to prevent this.  

6.4 Update mc-java prebuilder  

The command line build does not prevent prebuild when unnecessary.  The reason is that the logic in the mc builders to handle such a situation is only present when the build function is called.  This is only called when in the eclipse UI.  To handle this the readyBuildArea function is called from the mc-java prebuilder.  To handle the call to this function the project being built is captured and referenced.  If the project variable is null the existing getProject() call is used.  

### 7. Unit Test

See [[2.2]](#2.2).  

### 8. User Documentation

See [[2.2]](#2.2).   

### 9. Code Changes

Fork/Repository: https://githumb.com/travislondon/bridgepoint
Branch: 9524_consistent_building_ide_servwer

<pre>

org.xtuml.bp.als/.externalToolBuilders/Build.launch
org.xtuml.bp.als/.externalToolBuilders/Clean.launch
org.xtuml.bp.als/.externalToolBuilders/OAL Builder.launch
org.xtuml.bp.als/.project
org.xtuml.bp.als/pom.xml

org.xtuml.bp.als.oal/.externalToolBuilders/Build.launch
org.xtuml.bp.als.oal/.externalToolBuilders/Clean.launch
org.xtuml.bp.als.oal/META-INF/MANIFEST.MF
org.xtuml.bp.als.oal/.project
org.xtuml.bp.als.oal/pom.xml

org.xtuml.bp.cdt/.externalToolBuilders/Build.launch
org.xtuml.bp.cdt/.externalToolBuilders/Clean.launch
org.xtuml.bp.cdt/META-INF/MANIFEST.MF
org.xtuml.bp.cdt/.project
org.xtuml.bp.cdt/pom.xml

org.xtuml.bp.cli/.externalToolBuilders/Build.launch
org.xtuml.bp.cli/.externalToolBuilders/Clean.launch
org.xtuml.bp.cli/META-INF/MANIFEST.MF
org.xtuml.bp.cli/.project
org.xtuml.bp.cli/pom.xml

org.xtuml.bp.compare/.externalToolBuilders/Build.launch
org.xtuml.bp.compare/.externalToolBuilders/Clean.launch
org.xtuml.bp.compare/.externalToolBuilders/Compare Build.launch
org.xtuml.bp.compare/META-INF/MANIFEST.MF
org.xtuml.bp.compare/.project
org.xtuml.bp.compare/pom.xml

org.xtuml.bp.core/.externalToolBuilders/Build.launch
org.xtuml.bp.core/.externalToolBuilders/Clean.launch
org.xtuml.bp.core/.externalToolBuilders/Core Builder.launch
org.xtuml.bp.core/META-INF/MANIFEST.MF
org.xtuml.bp.core/.project
org.xtuml.bp.core/generate.xml
org.xtuml.bp.core/pom.xml

org.xtuml.bp.debug.ui/.externalToolBuilders/Build.launch
org.xtuml.bp.debug.ui/.externalToolBuilders/Clean.launch
org.xtuml.bp.debug.ui/META-INF/MANIFEST.MF
org.xtuml.bp.debug.ui/.project
org.xtuml.bp.debug.ui/pom.xml

org.xtuml.bp.doc/.externalToolBuilders/Build.launch
org.xtuml.bp.doc/.externalToolBuilders/Clean.launch
org.xtuml.bp.doc/META-INF/MANIFEST.MF
org.xtuml.bp.doc/.project
org.xtuml.bp.doc/pom.xml

org.xtuml.bp.docgen/.externalToolBuilders/Build.launch
org.xtuml.bp.docgen/.externalToolBuilders/Clean.launch
org.xtuml.bp.docgen/META-INF/MANIFEST.MF
org.xtuml.bp.docgen/.project
org.xtuml.bp.docgen/pom.xml

org.xtuml.bp.internal.tools/.externalToolBuilders/Build.launch
org.xtuml.bp.internal.tools/.externalToolBuilders/Clean.launch
org.xtuml.bp.internal.tools/META-INF/MANIFEST.MF
org.xtuml.bp.internal.tools/.project
org.xtuml.bp.internal.tools/pom.xml
org.xtuml.bp.internal.tools/update_BP_version.xml

org.xtuml.bp.io.core/.externalToolBuilders/Build.launch
org.xtuml.bp.io.core/.externalToolBuilders/Clean.launch
org.xtuml.bp.io.core/.externalToolBuilders/IO Core Builder.launch
org.xtuml.bp.io.core/META-INF/MANIFEST.MF
org.xtuml.bp.io.core/.project
org.xtuml.bp.io.core/pom.xml

org.xtuml.bp.io.image/.externalToolBuilders/Build.launch
org.xtuml.bp.io.image/.externalToolBuilders/Clean.launch
org.xtuml.bp.io.image/META-INF/MANIFEST.MF
org.xtuml.bp.io.image/.project
org.xtuml.bp.io.image/pom.xml

org.xtuml.bp.io.mdl/.externalToolBuilders/Build.launch
org.xtuml.bp.io.mdl/.externalToolBuilders/Clean.launch
org.xtuml.bp.io.mdl/.externalToolBuilders/IO Mdl Builder.launch
org.xtuml.bp.io.mdl/META-INF/MANIFEST.MF
org.xtuml.bp.io.mdl/.project
org.xtuml.bp.io.mdl/pom.xml

org.xtuml.bp.mc/.externalToolBuilders/Build.launch
org.xtuml.bp.mc/.externalToolBuilders/Clean.launch
org.xtuml.bp.mc/META-INF/MANIFEST.MF
org.xtuml.bp.mc/src/org/xtuml/bp/mc/AbstractExportBuilder.java
org.xtuml.bp.mc/.project
org.xtuml.bp.mc/pom.xml

org.xtuml.bp.mc.c.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.c.source/.externalToolBuilders/Clean.launch
org.xtuml.bp.mc.c.source/META-INF/MANIFEST.MF
org.xtuml.bp.mc.c.source/.project
org.xtuml.bp.mc.c.source/pom.xml

org.xtuml.bp.mc.cpp.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.cpp.source/.externalToolBuilders/Clean.launch
org.xtuml.bp.mc.cpp.source/META-INF/MANIFEST.MF
org.xtuml.bp.mc.cpp.source/.project
org.xtuml.bp.mc.cpp.source/pom.xml

org.xtuml.bp.mc.java.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.java.source/.externalToolBuilders/Clean.launch
org.xtuml.bp.mc.java.source/META-INF/MANIFEST.MF
org.xtuml.bp.mc.java.source/src/org/xtuml/bp/mc/java/source/ExportBuilder.java
org.xtuml.bp.mc.java.source/.project
org.xtuml.bp.mc.java.source/pom.xml

org.xtuml.bp.mc.none/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.none/.externalToolBuilders/Clean.launch
org.xtuml.bp.mc.none/META-INF/MANIFEST.MF
org.xtuml.bp.mc.none/.project
org.xtuml.bp.mc.none/pom.xml

org.xtuml.bp.mc.systemc.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.systemc.source/.externalToolBuilders/Clean.launch
org.xtuml.bp.mc.systemc.source/META-INF/MANIFEST.MF
org.xtuml.bp.mc.systemc.source/.project
org.xtuml.bp.mc.systemc.source/pom.xml

org.xtuml.bp.mc.template/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.template/.externalToolBuilders/Clean.launch
org.xtuml.bp.mc.template/META-INF/MANIFEST.MF
org.xtuml.bp.mc.template/.project
org.xtuml.bp.mc.template/pom.xml

org.xtuml.bp.mctools/.externalToolBuilders/Build.launch
org.xtuml.bp.mctools/.externalToolBuilders/Clean.launch
org.xtuml.bp.mctools/.project

org.xtuml.bp.mctools.parent/.externalToolBuilders/Build.launch
org.xtuml.bp.mctools.parent/.externalToolBuilders/Clean.launch
org.xtuml.bp.mctools.parent/.project

org.xtuml.bp.model.compare/.externalToolBuilders/Build.launch
org.xtuml.bp.model.compare/.externalToolBuilders/Clean.launch
org.xtuml.bp.model.compare/.externalToolBuilders/Compare Build.launch
org.xtuml.bp.model.compare/META-INF/MANIFEST.MF
org.xtuml.bp.model.compare/.project
org.xtuml.bp.model.compare/pom.xml

org.xtuml.bp.pkg/.externalToolBuilders/Build.launch
org.xtuml.bp.pkg/.externalToolBuilders/Clean.launch
org.xtuml.bp.pkg/META-INF/MANIFEST.MF
org.xtuml.bp.pkg/.project
org.xtuml.bp.pkg/about.mappings
org.xtuml.bp.pkg/pom.xml

org.xtuml.bp.pkg-feature/.externalToolBuilders/Build.launch
org.xtuml.bp.pkg-feature/.externalToolBuilders/Clean.launch
org.xtuml.bp.pkg-feature/.project
org.xtuml.bp.pkg-feature/feature.xml
org.xtuml.bp.pkg-feature/pom.xml

org.xtuml.bp.releng.p2/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.p2/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.p2/.project
org.xtuml.bp.releng.p2/pom.xml

org.xtuml.bp.releng.parent/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.parent/.project
org.xtuml.bp.releng.parent/pom.xml

org.xtuml.bp.releng.parent.generation/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.generation/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.parent.generation/.project
org.xtuml.bp.releng.parent.generation/pom.xml

org.xtuml.bp.releng.parent.product/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.product/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.parent.product/.project
org.xtuml.bp.releng.parent.product/bridgepoint.product
org.xtuml.bp.releng.parent.product/pom.xml

org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Test.launch
org.xtuml.bp.releng.parent.tests/.project
org.xtuml.bp.releng.parent.tests/pom.xml

org.xtuml.bp.search/.externalToolBuilders/Build.launch
org.xtuml.bp.search/.externalToolBuilders/Clean.launch
org.xtuml.bp.search/META-INF/MANIFEST.MF
org.xtuml.bp.search/.project
org.xtuml.bp.search/pom.xml

org.xtuml.bp.ui.canvas/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.canvas/.externalToolBuilders/Canvas Builder.launch
org.xtuml.bp.ui.canvas/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.canvas/META-INF/MANIFEST.MF
org.xtuml.bp.ui.canvas/.project
org.xtuml.bp.ui.canvas/pom.xml

org.xtuml.bp.ui.explorer/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.explorer/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.explorer/.externalToolBuilders/Explorer Builder.launch
org.xtuml.bp.ui.explorer/META-INF/MANIFEST.MF
org.xtuml.bp.ui.explorer/.project
org.xtuml.bp.ui.explorer/pom.xml

org.xtuml.bp.ui.graphics/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.graphics/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.graphics/META-INF/MANIFEST.MF
org.xtuml.bp.ui.graphics/.project
org.xtuml.bp.ui.graphics/pom.xml

org.xtuml.bp.ui.marking/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.marking/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.marking/.externalToolBuilders/Marking Builder.launch
org.xtuml.bp.ui.marking/META-INF/MANIFEST.MF
org.xtuml.bp.ui.marking/.project
org.xtuml.bp.ui.marking/pom.xml

org.xtuml.bp.ui.properties/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.properties/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.properties/.externalToolBuilders/Properties_Builder.launch
org.xtuml.bp.ui.properties/META-INF/MANIFEST.MF
org.xtuml.bp.ui.properties/.project
org.xtuml.bp.ui.properties/pom.xml

org.xtuml.bp.ui.search/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.search/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.search/META-INF/MANIFEST.MF
org.xtuml.bp.ui.search/.project
org.xtuml.bp.ui.search/pom.xml

org.xtuml.bp.ui.sem/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.sem/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.sem/META-INF/MANIFEST.MF
org.xtuml.bp.ui.sem/.project
org.xtuml.bp.ui.sem/pom.xml

org.xtuml.bp.ui.session/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.session/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.session/.externalToolBuilders/SessionExplorer_builder.launch
org.xtuml.bp.ui.session/META-INF/MANIFEST.MF
org.xtuml.bp.ui.session/.project
org.xtuml.bp.ui.session/pom.xml

org.xtuml.bp.ui.text/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.text/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.text/.externalToolBuilders/UITextBuilder.launch
org.xtuml.bp.ui.text/META-INF/MANIFEST.MF
org.xtuml.bp.ui.text/.project
org.xtuml.bp.ui.text/pom.xml

org.xtuml.bp.ui.tree/templates/builder.launch

org.xtuml.bp.utilities/.externalToolBuilders/Build.launch
org.xtuml.bp.utilities/.externalToolBuilders/Clean.launch
org.xtuml.bp.utilities/META-INF/MANIFEST.MF
org.xtuml.bp.utilities/.project
org.xtuml.bp.utilities/pom.xml

org.xtuml.bp.welcome/.externalToolBuilders/Build.launch
org.xtuml.bp.welcome/.externalToolBuilders/Clean.launch
org.xtuml.bp.welcome/META-INF/MANIFEST.MF
org.xtuml.bp.welcome/models/avpace/.externalToolBuilders/Model Compiler.launch
org.xtuml.bp.welcome/models/MicrowaveOven/.externalToolBuilders/
    Model Compiler.launch
org.xtuml.bp.welcome/models/TemplateProject/.externalToolBuilders/
    Model Compiler.launch
org.xtuml.bp.welcome/.project
org.xtuml.bp.welcome/pom.xml

org.xtuml.bp.x2m/.externalToolBuilders/Build.launch
org.xtuml.bp.x2m/.externalToolBuilders/Clean.launch
org.xtuml.bp.x2m/META-INF/MANIFEST.MF
org.xtuml.bp.x2m/.project
org.xtuml.bp.x2m/pom.xml

> org.xtuml.bp.xtext.masl.parent/.externalToolBuilders/Build.launch
> org.xtuml.bp.xtext.masl.parent/.externalToolBuilders/Clean.launch
> org.xtuml.bp.xtext.masl.parent/.externalToolBuilders/Test.launch
> org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/META-INF/MANIFEST.MF
> org.xtuml.bp.xtext.masl.parent/.project
> org.xtuml.bp.xtext.masl.parent/pom.xml

org.xtuml.help.bp.mc/.externalToolBuilders/Build.launch
org.xtuml.help.bp.mc/.externalToolBuilders/Clean.launch
org.xtuml.help.bp.mc/META-INF/MANIFEST.MF
org.xtuml.help.bp.mc/.project
org.xtuml.help.bp.mc/pom.xml

utilities/build/build_and_test_bp.sh
utilities/build/build_configuration.sh
utilities/build/build_project.sh
utilities/build/prepare_build.sh
utilities/build/preferences/org.eclipse.core.resources.prefs

</pre>

Fork/Repository: https://github.com/travislondon/bptest
Branch: 9524_consistent_building_ide_servwer

<pre>
org.xtuml.bp.als.oal.test/.externalToolBuilders/Build.launch
org.xtuml.bp.als.oal.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.als.oal.test/.externalToolBuilders/Test.launch
org.xtuml.bp.als.oal.test/META-INF/MANIFEST.MF
org.xtuml.bp.als.oal.test/.project
org.xtuml.bp.als.oal.test/pom.xml

org.xtuml.bp.core.test/.externalToolBuilders/Build.launch
org.xtuml.bp.core.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.core.test/.externalToolBuilders/Core Test Builder.launch
org.xtuml.bp.core.test/.externalToolBuilders/Test.launch
org.xtuml.bp.core.test/META-INF/MANIFEST.MF
org.xtuml.bp.core.test/.project
org.xtuml.bp.core.test/pom.xml

org.xtuml.bp.debug.ui.test/.externalToolBuilders/Build.launch
org.xtuml.bp.debug.ui.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.debug.ui.test/.externalToolBuilders/Test.launch
org.xtuml.bp.debug.ui.test/META-INF/MANIFEST.MF
org.xtuml.bp.debug.ui.test/.project
org.xtuml.bp.debug.ui.test/pom.xml

org.xtuml.bp.io.mdl.test/.externalToolBuilders/Build.launch
org.xtuml.bp.io.mdl.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.io.mdl.test/.externalToolBuilders/Test.launch
org.xtuml.bp.io.mdl.test/META-INF/MANIFEST.MF
org.xtuml.bp.io.mdl.test/.project
org.xtuml.bp.io.mdl.test/pom.xml

org.xtuml.bp.model.compare.test/.externalToolBuilders/Build.launch
org.xtuml.bp.model.compare.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.model.compare.test/.externalToolBuilders/
    Model Compare Test Builder.launch
org.xtuml.bp.model.compare.test/.externalToolBuilders/Test.launch
org.xtuml.bp.model.compare.test/.project
org.xtuml.bp.model.compare.test/pom.xml

org.xtuml.bp.search.test/.externalToolBuilders/Build.launch
org.xtuml.bp.search.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.search.test/.externalToolBuilders/Test.launch
org.xtuml.bp.search.test/META-INF/MANIFEST.MF
org.xtuml.bp.search.test/.project
org.xtuml.bp.search.test/pom.xml

org.xtuml.bp.test/.externalToolBuilders/Build.launch
org.xtuml.bp.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.test/META-INF/MANIFEST.MF
org.xtuml.bp.test/.project
org.xtuml.bp.test/pom.xml

org.xtuml.bp.ui.canvas.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.canvas.test/.externalToolBuilders/CanvasTestBuilder.launch
org.xtuml.bp.ui.canvas.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.canvas.test/.externalToolBuilders/Test.launch
org.xtuml.bp.ui.canvas.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.canvas.test/.project
org.xtuml.bp.ui.canvas.test/pom.xml

org.xtuml.bp.ui.explorer.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.explorer.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.explorer.test/.externalToolBuilders/Test.launch
org.xtuml.bp.ui.explorer.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.explorer.test/.project
org.xtuml.bp.ui.explorer.test/pom.xml

org.xtuml.bp.ui.properties.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.properties.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.properties.test/.externalToolBuilders/
    Properties Test Builder.launch
org.xtuml.bp.ui.properties.test/.externalToolBuilders/Test.launch
org.xtuml.bp.ui.properties.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.properties.test/.project
org.xtuml.bp.ui.properties.test/pom.xml

org.xtuml.bp.ui.text.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.text.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.text.test/.externalToolBuilders/Test.launch
org.xtuml.bp.ui.text.test/META-INF/MANIFEST.MF
org.xtuml.bp.ui.text.test/.project
org.xtuml.bp.ui.text.test/pom.xml

org.xtuml.bp.welcome.test/.externalToolBuilders/Build.launch
org.xtuml.bp.welcome.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.welcome.test/.externalToolBuilders/Test.launch
org.xtuml.bp.welcome.test/META-INF/MANIFEST.MF
org.xtuml.bp.welcome.test/.project
org.xtuml.bp.welcome.test/pom.xml

org.xtuml.internal.test/.externalToolBuilders/Build.launch
org.xtuml.internal.test/.externalToolBuilders/Clean.launch
org.xtuml.internal.test/META-INF/MANIFEST.MF
org.xtuml.internal.test/.project
org.xtuml.internal.test/pom.xml

</pre>  

Fork/Repository: https://github.com/travislondon/buildmt
Branch: 9524_consistent_building_ide_servwer

<pre>
buildmt/buildmt/jenkins-home/jobs/Build-BridgePoint/config.xml
buildmt/buildmt/jenkins-home/jobs/bridgepoint/config.xml
</pre>
### End

