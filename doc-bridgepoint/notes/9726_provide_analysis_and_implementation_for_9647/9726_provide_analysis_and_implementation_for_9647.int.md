---

This work is licensed under the Creative Commons CC0 License

---

# Address offline mode issues with maven  
### xtUML Project Implementation Note

### 1. Abstract

This note describes the files that were changed to implement the design.  

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #9726](https://support.onefact.net/issues/9726) Provide analysis and implementation for 9647    
<a id="2.2"></a>2.2 [BridgePoint Design Note 9726](https://github.com/travislondon/bridgepoint/blob/9726_1/doc-bridgepoint/notes/9726_provide_analysis_and_implementation_for_9647/9726_provide_analysis_and_implementation_for_9647.dnt.md) 9726 Design     

### 3. Background

See [[2.1]](#2.2).  

### 4. Requirements

See [[2.1]](#2.2).  

### 5. Work Required

See [[2.1]](#2.2).  

### 6. Implementation Comments

None.  

### 7. Unit Test

See [[2.1]](#2.2).  

### 8. User Documentation

See [[2.1]](#2.2).   

### 9. Code Changes

Fork/Repository: https://github.com/travislondon/bridgepoint  
Branch: 9726_1

<pre>

doc-bridgepoint/notes/9726_provide_analysis_and_implementation_for_9647/
9726_provide_analysis_and_implementation_for_9647.ant.md
doc-bridgepoint/notes/9726_provide_analysis_and_implementation_for_9647/
9726_provide_analysis_and_implementation_for_9647.dnt.md
doc-bridgepoint/notes/9726_provide_analysis_and_implementation_for_9647/
9726_provide_analysis_and_implementation_for_9647.int.md
doc-bridgepoint/review-minutes/9726_build_updates_rvm.md
doc-bridgepoint/process/Developer Getting Started Guide.md

org.xtuml.bp.als/.externalToolBuilders/Build.launch
org.xtuml.bp.als/.externalToolBuilders/Clean.launch

org.xtuml.bp.als.oal/.externalToolBuilders/Build.launch
org.xtuml.bp.als.oal/.externalToolBuilders/Clean.launch

org.xtuml.bp.cdt/.externalToolBuilders/Build.launch
org.xtuml.bp.cdt/.externalToolBuilders/Clean.launch

org.xtuml.bp.cli/.externalToolBuilders/Build.launch
org.xtuml.bp.cli/.externalToolBuilders/Clean.launch

org.xtuml.bp.compare/.externalToolBuilders/Build.launch
org.xtuml.bp.compare/.externalToolBuilders/Clean.launch

org.xtuml.bp.core/.externalToolBuilders/Build.launch
org.xtuml.bp.core/.externalToolBuilders/Clean.launch

org.xtuml.bp.debug.ui/.externalToolBuilders/Build.launch
org.xtuml.bp.debug.ui/.externalToolBuilders/Clean.launch

org.xtuml.bp.doc/.externalToolBuilders/Build.launch
org.xtuml.bp.doc/.externalToolBuilders/Clean.launch

org.xtuml.bp.docgen/.externalToolBuilders/Build.launch
org.xtuml.bp.docgen/.externalToolBuilders/Clean.launch

org.xtuml.bp.internal.tools/.externalToolBuilders/Build.launch
org.xtuml.bp.internal.tools/.externalToolBuilders/Clean.launch

org.xtuml.bp.io.core/.externalToolBuilders/Build.launch
org.xtuml.bp.io.core/.externalToolBuilders/Clean.launch

org.xtuml.bp.io.image/.externalToolBuilders/Build.launch
org.xtuml.bp.io.image/.externalToolBuilders/Clean.launch

org.xtuml.bp.io.mdl/.externalToolBuilders/Build.launch
org.xtuml.bp.io.mdl/.externalToolBuilders/Clean.launch

org.xtuml.bp.mc/.externalToolBuilders/Build.launch
org.xtuml.bp.mc/.externalToolBuilders/Clean.launch

org.xtuml.bp.mc.c.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.c.source/.externalToolBuilders/Clean.launch

org.xtuml.bp.mc.cpp.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.cpp.source/.externalToolBuilders/Clean.launch

org.xtuml.bp.mc.java.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.java.source/.externalToolBuilders/Clean.launch

org.xtuml.bp.mc.none/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.none/.externalToolBuilders/Clean.launch

org.xtuml.bp.mc.systemc.source/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.systemc.source/.externalToolBuilders/Clean.launch

org.xtuml.bp.mc.template/.externalToolBuilders/Build.launch
org.xtuml.bp.mc.template/.externalToolBuilders/Clean.launch

org.xtuml.bp.mctools/.externalToolBuilders/Build.launch
org.xtuml.bp.mctools/.externalToolBuilders/Clean.launch

org.xtuml.bp.mctools.parent/.externalToolBuilders/Build.launch
org.xtuml.bp.mctools.parent/.externalToolBuilders/Clean.launch

org.xtuml.bp.model.compare/.externalToolBuilders/Build.launch
org.xtuml.bp.model.compare/.externalToolBuilders/Clean.launch

org.xtuml.bp.pkg/.externalToolBuilders/Build.launch
org.xtuml.bp.pkg/.externalToolBuilders/Clean.launch

org.xtuml.bp.pkg-feature/.externalToolBuilders/Build.launch
org.xtuml.bp.pkg-feature/.externalToolBuilders/Clean.launch

org.xtuml.bp.releng.p2/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.p2/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.p2/.project

org.xtuml.bp.releng.parent/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent/.externalToolBuilders/Clean.launch

org.xtuml.bp.releng.parent.generation/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.generation/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.parent.generation/.project

org.xtuml.bp.releng.parent.product/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.product/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.parent.product/.project

org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Clean.launch
org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Test.launch
org.xtuml.bp.releng.parent.tests/.project

org.xtuml.bp.search/.externalToolBuilders/Build.launch
org.xtuml.bp.search/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.canvas/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.canvas/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.explorer/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.explorer/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.graphics/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.graphics/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.marking/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.marking/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.properties/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.properties/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.search/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.search/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.sem/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.sem/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.session/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.session/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.text/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.text/.externalToolBuilders/Clean.launch

org.xtuml.bp.utilities/.externalToolBuilders/Build.launch
org.xtuml.bp.utilities/.externalToolBuilders/Clean.launch

org.xtuml.bp.welcome/.externalToolBuilders/Build.launch
org.xtuml.bp.welcome/.externalToolBuilders/Clean.launch

org.xtuml.bp.x2m/.externalToolBuilders/Build.launch
org.xtuml.bp.x2m/.externalToolBuilders/Clean.launch

org.xtuml.bp.xtext.masl.parent/.externalToolBuilders/Build.launch
org.xtuml.bp.xtext.masl.parent/.externalToolBuilders/Clean.launch
org.xtuml.bp.xtext.masl.parent/.externalToolBuilders/Test.launch

org.xtuml.help.bp.mc/.externalToolBuilders/Build.launch
org.xtuml.help.bp.mc/.externalToolBuilders/Clean.launch

utilities/build/build_and_test_bp.sh
utilities/build/build_project.sh
utilities/build/prepare_build.sh
utilities/build/preferences/org.eclipse.core.resources.prefs


</pre>

Fork/Repository: https://github.com/travislondon/bptest  
Branch: 9726_1

<pre>  

org.xtuml.bp.als.oal.test/.externalToolBuilders/Build.launch
org.xtuml.bp.als.oal.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.als.oal.test/.externalToolBuilders/Test.launch

org.xtuml.bp.core.test/.externalToolBuilders/Build.launch
org.xtuml.bp.core.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.core.test/.externalToolBuilders/Test.launch

org.xtuml.bp.debug.ui.test/.externalToolBuilders/Build.launch
org.xtuml.bp.debug.ui.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.debug.ui.test/.externalToolBuilders/Test.launch

org.xtuml.bp.io.mdl.test/.externalToolBuilders/Build.launch
org.xtuml.bp.io.mdl.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.io.mdl.test/.externalToolBuilders/Test.launch

org.xtuml.bp.model.compare.test/.externalToolBuilders/Build.launch
org.xtuml.bp.model.compare.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.model.compare.test/.externalToolBuilders/Test.launch

org.xtuml.bp.search.test/.externalToolBuilders/Build.launch
org.xtuml.bp.search.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.search.test/.externalToolBuilders/Test.launch

org.xtuml.bp.test/.externalToolBuilders/Build.launch
org.xtuml.bp.test/.externalToolBuilders/Clean.launch

org.xtuml.bp.ui.canvas.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.canvas.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.canvas.test/.externalToolBuilders/Test.launch

org.xtuml.bp.ui.explorer.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.explorer.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.explorer.test/.externalToolBuilders/Test.launch

org.xtuml.bp.ui.properties.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.properties.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.properties.test/.externalToolBuilders/Test.launch

org.xtuml.bp.ui.text.test/.externalToolBuilders/Build.launch
org.xtuml.bp.ui.text.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.ui.text.test/.externalToolBuilders/Test.launch

org.xtuml.bp.welcome.test/.externalToolBuilders/Build.launch
org.xtuml.bp.welcome.test/.externalToolBuilders/Clean.launch
org.xtuml.bp.welcome.test/.externalToolBuilders/Test.launch

org.xtuml.internal.test/.externalToolBuilders/Build.launch
org.xtuml.internal.test/.externalToolBuilders/Clean.launch

</pre>  

### End

