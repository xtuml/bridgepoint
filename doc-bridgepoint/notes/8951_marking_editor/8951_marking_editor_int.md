---

This work is licensed under the Creative Commons CC0 License

---

# Marking Editor
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work done for the first phase of the MASL marking editor.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8951](https://support.onefact.net/issues/8951) Headline issue.     

3. Background
-------------
See design note.   

4. Requirements
---------------
See design note.   

5. Work Required
----------------
5.1  The marking editor dialog is implemented as specified in the DNT.  

6. Implementation Comments
--------------------------
6.1  Additional features and documentation work is called out in separate issues in
the DNT.

6.2  Added new plug-in named ```org.xtuml.bp.ui.marking```   
6.2.1  This plug-in includes an xtuml model that describes the marking data, but
  this model is not code generated and used inside the plug-in code.  The necessary
  pieces to perform code generation are left in the ```generate.xml``` commented 
  out.       

6.3  Removed ```doc-bridgepoint/8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md ```
  as part of this branch.  The files is out of place and out of date, a newer 
  one exists in the proper folder under ```doc-bridgepoint/notes```.   
  
7. Unit Test
------------
7.1  The DNT calls out the unit testing.   

8. User Documentation
---------------------
8.1  The DNT calls out a documentation issue to go along with this work.   

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint  
Branch: 8951_marking_editor    

<pre>
> doc-bridgepoint/8031_Analyze_Model_Element_Move/
    8031_Analyze_Model_Element_Move.ant.md
> doc-bridgepoint/> notes/8951_marking_editor/8951_marking_editor_dnt.md
> doc-bridgepoint/> notes/8951_marking_editor/8951_marking_editor_int.md
> doc-bridgepoint/> notes/8951_marking_editor/marking_editor.png
> doc-bridgepoint/> notes/8951_marking_editor/ooaofmarking.png
> doc-bridgepoint/review-minutes/8951_marking_editor.dnt.rvm.md

org.xtuml.bp.pkg-feature/feature.xml

org.xtuml.bp.releng.parent/pom.xml

org.xtuml.bp.ui.marking/.externalToolBuilders/Marking Builder.launch
org.xtuml.bp.ui.marking/.settings/org.eclipse.jdt.core.prefs
org.xtuml.bp.ui.marking/color/ooaofmarking_import_spec.clr
org.xtuml.bp.ui.marking/color/ooaofmarking_package_spec.clr
org.xtuml.bp.ui.marking/color/ooaofmarking_startspec.clr
org.xtuml.bp.ui.marking/gen/.gitignore
org.xtuml.bp.ui.marking/META-INF/MANIFEST.MF
org.xtuml.bp.ui.marking/models/org.xtuml.bp.ui.marking/
    org.xtuml.bp.ui.marking.xtuml
org.xtuml.bp.ui.marking/models/org.xtuml.bp.ui.marking/ooaofmarking/
    ooaofmarking.xtuml
org.xtuml.bp.ui.marking/models/org.xtuml.bp.ui.marking/ooaofmarking/Feature/
    Feature.xtuml
org.xtuml.bp.ui.marking/models/org.xtuml.bp.ui.marking/ooaofmarking/Mark/
    Mark.xtuml
org.xtuml.bp.ui.marking/models/org.xtuml.bp.ui.marking/ooaofmarking/
    Markable Element Type/Markable Element Type.xtuml
org.xtuml.bp.ui.marking/sql/.gitignore
org.xtuml.bp.ui.marking/src/org/xtuml/bp/ui/marking/.gitignore
org.xtuml.bp.ui.marking/src/org/xtuml/bp/ui/marking/Activator.java
org.xtuml.bp.ui.marking/src/org/xtuml/bp/ui/marking/
    LaunchMarkingEditorAction.java
org.xtuml.bp.ui.marking/src/org/xtuml/bp/ui/marking/MarkingData.java
org.xtuml.bp.ui.marking/src/org/xtuml/bp/ui/marking/MarkingEditorDialog.java
org.xtuml.bp.ui.marking/.classpath
org.xtuml.bp.ui.marking/.gitignore
org.xtuml.bp.ui.marking/.project
org.xtuml.bp.ui.marking/about.html
org.xtuml.bp.ui.marking/build.properties
org.xtuml.bp.ui.marking/generate.xml
org.xtuml.bp.ui.marking/plugin.xml
org.xtuml.bp.ui.marking/pom.xml


</pre>

End
---

