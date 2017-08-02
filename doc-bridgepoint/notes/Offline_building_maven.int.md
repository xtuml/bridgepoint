---

This work is licensed under the Creative Commons CC0 License

---

# Offline building
### xtUML Project Implementation Note

### 1. Abstract

This note describes the implementation for performing maven builds offline while developing in the UI.    

### 2. Document References

None.

### 3. Background

Currently builds require a network and validate current dependency versions for every build.  

### 4. Requirements

4.1 Build offline after initial build from getting started guide.   

### 5. Work Required

5.1 Modify build launchers adding the -o option (for offline mode)  
5.2 Modify utilities/build/build_project.sh adding the -o option (for offline mode with tests)  

### 6. Implementation Comments

None.   

### 7. Unit Test

7.1 Run developer getting started  
7.2 Disable networking  
7.3 Modify a java file under bp.core.test and start a build  
7.R Build completes successfully     

### 8. User Documentation

None. 

### 9. Code Changes

Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: Offline_building_maven  

<pre>

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

org.xtuml.bp.releng.parent/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent/.externalToolBuilders/Clean.launch

org.xtuml.bp.releng.parent.generation/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.generation/.externalToolBuilders/Clean.launch

org.xtuml.bp.releng.parent.product/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.product/.externalToolBuilders/Clean.launch

org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Build.launch
org.xtuml.bp.releng.parent.tests/.externalToolBuilders/Clean.launch

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

org.xtuml.help.bp.mc/.externalToolBuilders/Build.launch
org.xtuml.help.bp.mc/.externalToolBuilders/Clean.launch

utilities/build/build_and_test_bp.sh
utilities/build/build_project.sh


</pre>


### End

