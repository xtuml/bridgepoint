---

This work is licensed under the Creative Commons CC0 License

---

# Improve mechanism for inter-domain references - Ensure .int (interface) files are provided to MASL editor  
### xtUML Project Implementation Note

### 1. Abstract

This note describes the implementation of a new dependency-management feature for
MASL models.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9021](https://support.onefact.net/issues/9021) Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #9679](https://support.onefact.net/issues/9679) Headline SR    
<a id="2.3"></a>2.3 [Design note for DEI #9021](9021_dependency_dnt.md)      
<a id="2.4"></a>2.4 [BridgePoint DEI #10123](https://support.onefact.net/issues/10123) Update Preferences help documentation    
<a id="2.5"></a>2.5 [BridgePoint DEI #10128](https://support.onefact.net/issues/10128) Documentation issue for tables that want to display | operator  
<a id="2.6"></a>2.6 [BridgePoint DEI #10131](https://support.onefact.net/issues/10131) Re-read dependency files more often  

### 3. Background

See 2.3  

### 4. Requirements

See 2.3   

### 5. Work Required

5.1  Added new page "Dependencies" to the project preferences wizard.  This dialog
allows for per-project dependency information to be stored in `<project>/.dependencies`  
5.1.1  The page may be disabled by setting the `-Dbridgepoint.Dependencies=disabled` in 
`bridgepoint.ini`  

5.2  Add new class `DepenencyData` to `bp.core` that provides a static function-based
API for reading and writing the dependency file.  

5.3  Performed some general cleanup in the project dependency classes.  Removed unused
imports, fixed typos, etc.  

5.4  Removed unnecessary `.project` file in the Command Line Interface documentation folder.  

5.5  Added `MaslProjectDependencyProvider` class to handle the files and folders found in
the dependency information as elements that should be added to the MASL validation index
in support of the validation process.   

5.6  Update `MaslWorkspaceProjectState` to use the new dependency provider.  

5.7  Add a new README to the `masl.ui` plug-in that describes the special steps 
BridgePoint developers must take when making modifications to xtend files that reference
data from `bp.core`  


### 6. Implementation Comments

None.  

### 7. Unit Test

See 2.3  

During the testing process, the test 9.3 does not pass regularly.  Issue [2.5] is 
opened to address this in follow on work.  

### 8. User Documentation

8.1  Updated the MASL conversion guide with information about using the dependency
manager in the project preferences.  

8.2  Completely re-wrote the preferences help documentation.  This resolves [2.4].  

8.3  Updated the Expressions help doc to fix problem with pipe operator inside tables. 
This resolves [2.5].  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint   
Branch: 9021_dependency

<pre>

 doc-bridgepoint/notes/9021_dependency/9021_dependency_dnt.md     | 249 ++++++++++++++++++++++++++
 doc-bridgepoint/notes/9021_dependency/9021_dependency_int.md     |  86 +++++++++
 doc-bridgepoint/notes/9021_dependency/c_lib_setting.png          | Bin 0 -> 175154 bytes
 doc-bridgepoint/notes/9021_dependency/dependency_prefs.png       | Bin 0 -> 171660 bytes
 doc-bridgepoint/notes/9021_dependency/java_lib_config.png        | Bin 0 -> 120437 bytes
 .../notes/9021_dependency/jdt_libs_tree_classpath.png            | Bin 0 -> 116588 bytes
 .../9021_improved_inter_domain_ref_handling.dnt.rvm.md           |  60 +++++++
 src/org.xtuml.bp.core/arc/create_core_plugin_class.arc           |  11 +-
 src/org.xtuml.bp.core/generate.xml                               |   1 +
 .../org/xtuml/bp/core/common/BridgePointPreferencesStore.java    |   2 +-
 .../src/org/xtuml/bp/core/common/DependencyData.java             | 146 ++++++++++++++++
 .../xtuml/bp/core/ui/preferences/ActionLanguagePreferences.java  |  10 --
 .../BridgePointProjectDependenciesPreferenceNode.java            |  21 +++
 .../preferences/BridgePointProjectDependenciesPreferences.java   | 209 ++++++++++++++++++++++
 .../ui/preferences/BridgePointProjectReferencesPreferences.java  |  13 --
 .../Reference/MASL/MASLConversionGuide/MASLConversionGuide.html  |  10 +-
 .../Reference/MASL/MASLConversionGuide/MASLConversionGuide.md    |  13 +-
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/Expressions.html  |  16 +-
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/Expressions.md    |  12 +-
 .../Reference/UserInterface/CommandLineInterface/.project        |  11 --
 .../UserInterface/xtUMLModeling/Preferences/ActionLanguage.html  |  74 ++++++++
 .../UserInterface/xtUMLModeling/Preferences/ActionLanguage.md    |  80 +++++++++
 .../UserInterface/xtUMLModeling/Preferences/ActionLanguage.png   | Bin 0 -> 99905 bytes
 .../UserInterface/xtUMLModeling/Preferences/ActivityEditor.html  |  29 +++
 .../UserInterface/xtUMLModeling/Preferences/ActivityEditor.md    |  18 ++
 .../UserInterface/xtUMLModeling/Preferences/ActivityEditor.png   | Bin 0 -> 93634 bytes
 .../xtUMLModeling/Preferences/{HTML => }/CAprefs.png             | Bin
 .../UserInterface/xtUMLModeling/Preferences/ColorsAndFonts.html  |  22 +++
 .../UserInterface/xtUMLModeling/Preferences/ColorsAndFonts.md    |  11 ++
 .../xtUMLModeling/Preferences/{HTML => }/ContentAssist.html      |  24 ++-
 .../xtUMLModeling/Preferences/{HTML => }/ContentAssist.md        |   0
 .../UserInterface/xtUMLModeling/Preferences/DiagramEditors.html  |  65 +++++++
 .../UserInterface/xtUMLModeling/Preferences/DiagramEditors.md    |  47 +++++
 .../UserInterface/xtUMLModeling/Preferences/DiagramEditors.png   | Bin 0 -> 76776 bytes
 .../UserInterface/xtUMLModeling/Preferences/DiagramText.png      | Bin 0 -> 107427 bytes
 .../xtUMLModeling/Preferences/HTML/ActivityEditor.htm            | 258 ---------------------------
 .../Preferences/HTML/ActivityEditor_files/image001.jpg           | Bin 54456 -> 0 bytes
 .../Preferences/HTML/ActivityEditor_files/image002.png           | Bin 39818 -> 0 bytes
 .../Preferences/HTML/ActivityEditor_files/image003.jpg           | Bin 57726 -> 0 bytes
 .../xtUMLModeling/Preferences/HTML/DiagramEditor.htm             | 232 ------------------------
 .../Preferences/HTML/DiagramEditor_files/image001.jpg            | Bin 53681 -> 0 bytes
 .../Preferences/HTML/DiagramEditor_files/image001.png            | Bin 35066 -> 0 bytes
 .../Preferences/HTML/DiagramEditor_files/image002.jpg            | Bin 62041 -> 0 bytes
 .../Preferences/HTML/DiagramEditor_files/image003.jpg            | Bin 51198 -> 0 bytes
 .../UserInterface/xtUMLModeling/Preferences/HTML/DiagramText.htm | 231 ------------------------
 .../Preferences/HTML/DiagramText_files/image001.jpg              | Bin 49249 -> 0 bytes
 .../Preferences/HTML/DiagramText_files/image001.png              | Bin 38103 -> 0 bytes
 .../UserInterface/xtUMLModeling/Preferences/HTML/Preferences.htm | 208 ----------------------
 .../xtUMLModeling/Preferences/MessageDirection.html              |  23 +++
 .../UserInterface/xtUMLModeling/Preferences/MessageDirection.md  |  12 ++
 .../UserInterface/xtUMLModeling/Preferences/MessageDirection.png | Bin 0 -> 56003 bytes
 .../UserInterface/xtUMLModeling/Preferences/ModelExport.html     |  28 +++
 .../UserInterface/xtUMLModeling/Preferences/ModelExport.md       |  20 +++
 .../UserInterface/xtUMLModeling/Preferences/ModelExport.png      | Bin 0 -> 56023 bytes
 .../UserInterface/xtUMLModeling/Preferences/ObliqueRouting.png   | Bin 0 -> 13631 bytes
 .../UserInterface/xtUMLModeling/Preferences/Preferences.html     |  47 +++++
 .../UserInterface/xtUMLModeling/Preferences/Preferences.md       |  34 ++++
 .../xtUMLModeling/Preferences/ProjActionLanguage.html            |  33 ++++
 .../xtUMLModeling/Preferences/ProjActionLanguage.md              |  22 +++
 .../xtUMLModeling/Preferences/ProjActionLanguage.png             | Bin 0 -> 55725 bytes
 .../xtUMLModeling/Preferences/ProjDependencies.html              |  32 ++++
 .../UserInterface/xtUMLModeling/Preferences/ProjDependencies.md  |  25 +++
 .../UserInterface/xtUMLModeling/Preferences/ProjDependencies.png | Bin 0 -> 81965 bytes
 .../UserInterface/xtUMLModeling/Preferences/ProjIPR.html         |  38 ++++
 .../Reference/UserInterface/xtUMLModeling/Preferences/ProjIPR.md |  35 ++++
 .../UserInterface/xtUMLModeling/Preferences/ProjIPR.png          | Bin 0 -> 66756 bytes
 .../xtUMLModeling/Preferences/ProjectPreferencesCME.png          | Bin 0 -> 36606 bytes
 .../xtUMLModeling/Preferences/RectilinearRouting.png             | Bin 0 -> 13166 bytes
 .../Reference/UserInterface/xtUMLModeling/Preferences/xtUML.html |  51 ++++++
 .../Reference/UserInterface/xtUMLModeling/Preferences/xtUML.md   |  59 +++++++
 .../Reference/UserInterface/xtUMLModeling/Preferences/xtUML.png  | Bin 0 -> 87229 bytes
 .../UserInterface/xtUMLModeling/Preferences/xtUMLTranslate.html  |  23 +++
 .../UserInterface/xtUMLModeling/Preferences/xtUMLTranslate.md    |  12 ++
 .../UserInterface/xtUMLModeling/Preferences/xtUMLTranslate.png   | Bin 0 -> 54954 bytes
 .../UserInterface/xtUMLModeling/Preferences/xtUMLeXecute.html    |  41 +++++
 .../UserInterface/xtUMLModeling/Preferences/xtUMLeXecute.md      |  34 ++++
 .../UserInterface/xtUMLModeling/Preferences/xtUMLeXecute.png     | Bin 0 -> 73463 bytes
 src/org.xtuml.bp.doc/topics_Reference.xml                        |  23 ++-
 .../org.xtuml.bp.xtext.masl.ui/README_xtend.md                   |  25 +++
 .../xtext/masl/ui/dependency/MaslProjectDependencyProvider.java  | 272 +++++++++++++++++++++++++++++
 .../masl/ui/dependency/MaslProjectDependencyProvider.xtend_      | 112 ++++++++++++
 .../bp/xtext/masl/ui/index/MaslWorkspaceProjectsState.xtend      |  20 ++-
 .../org.xtuml.bp.xtext.masl/META-INF/MANIFEST.MF                 |   1 +
 .../xtuml/bp/xtext/masl/dependency/MaslDependencyProvider.xtend  |  70 ++++++++
 .../src/org/xtuml/bp/xtext/masl/lib/MASLContainerManager.xtend   |   8 +
 .../bp/xtext/masl/scoping/MaslResourceDescriptionManager.xtend   |  18 +-
 utilities/build/build_configuration.sh                           |   5 +-
 87 files changed, 2276 insertions(+), 1016 deletions(-)
 
</pre>

### End

