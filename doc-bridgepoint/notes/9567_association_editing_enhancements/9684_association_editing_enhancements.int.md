---

This work is licensed under the Creative Commons CC0 License

---

# Enhanced association editing
### xtUML Project Implementation Note

### 1. Abstract

This note describes the files changed to implement this issue.  

### 2. Document References

<a id="2.1"></a>2.1 [Design Note](https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/9684_association_editing_enhancements/9567_association_editing_enhancements.dnt.md) 9684 Design      

### 3. Background

See [[2.1]](2.1).  

### 4. Requirements

See [[2.1]](2.1).  

### 5. Work Required

See [[2.1]](2.1).  

### 6. Implementation Comments

A new preference was added, Enable table based association editing.  This preference is disabled by default.  The context menu entries for Configure Association were adjusted to be dynamic and check this preference before showing.  Additionally, preference based tab support was added to prevent the Associations Editor tab.  

This issue was promoted but reverted due to testing failures.  They are now fixed.  The changes for this were in both the AssociationTableEditorTests.java and AssociationCardinalityMenuTests.java classes.  The changes in AssociaionTableEditorTests were to enable/disable the preference to allow table editing during setup and tear down.  The changes to AssociationCardinalityMenuTests were to use the already existing AssociationEditingCardinalityMenu test model.  Once these two tests did not share the same model they passed.  This was caused by the two tests using the same model-root and finding the wrong package instance.  

### 7. Unit Test

See [[2.1]](2.1).  All tests have been automated and make use of a new model under the models repository, AssociationEditing  

### 8. User Documentation

None.   

### 9. Code Changes

Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 9684_association_editing_enhancements

<pre>

doc-bridgepoint/notes/9567_association_editing_enhancements/
9684_association_editing_enhancements.dnt.md
doc-bridgepoint/notes/9567_association_editing_enhancements/
9684_association_editing_enhancements.int.md
doc-bridgepoint/notes/9567_association_editing_enhancements/
9567_association_editing_enhancements.md
doc-bridgepoint/review-minutes/9567_association_editing_enhancements_ant_rvm.md
doc-bridgepoint/review-minutes/9567_association_editing_enhancements_ant_rvm2.md

org.xtuml.bp.core/.externalToolBuilders/Build.launch
org.xtuml.bp.core/arc/BuildHierarchyProperties.arc
org.xtuml.bp.core/arc/create_properties_file.inc
org.xtuml.bp.core/arc/generate_cell_modifiers.arc

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Association/
Association.xtuml
org.xtuml.bp.core/src/org/xtuml/bp/core/CorePluginMessages.properties
org.xtuml.bp.core/src/org/xtuml/bp/core/common/ITransactionListener.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/Selection.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/cells/editors/IntegerCellEditor.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/BridgePointPreferences.java
org.xtuml.bp.core/generate.xml

org.xtuml.bp.core.editors/.externalToolBuilders/Build.launch
org.xtuml.bp.core.editors/.externalToolBuilders/Clean.launch
org.xtuml.bp.core.editors/.settings/org.eclipse.jdt.core.prefs
org.xtuml.bp.core.editors/bin/.gitignore
org.xtuml.bp.core.editors/META-INF/MANIFEST.MF
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/Activator.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/ErrorToolTip.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/ITabErrorSupport.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/actions/
TreeCopyAction.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/actions/
TreeCutAction.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/actions/
TreePasteAction.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/association/
AssociationEditorTab.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/association/
TextDialog.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/association/actions/
ConfigureAssociation.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/association/dialogs/
AssociationTableDialog.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/association/editing/
AssociationEditingSupport.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/association/factories/
AssociationEditorTabFactory.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/editing/
ElementEditingSupport.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/providers/
MetaModelContentProvider.java
org.xtuml.bp.core.editors/src/org/xtuml/bp/core/editors/providers/
MetaModelLabelProvider.java
org.xtuml.bp.core.editors/.classpath
org.xtuml.bp.core.editors/.gitignore
org.xtuml.bp.core.editors/.project
org.xtuml.bp.core.editors/build.properties
org.xtuml.bp.core.editors/plugin.xml
org.xtuml.bp.core.editors/pom.xml

org.xtuml.bp.pkg/plugin_customization.ini

org.xtuml.bp.pkg-feature/feature.xml

org.xtuml.bp.releng.parent/pom.xml

org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/editor/IEditorTabFactory.java
org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/editor/ModelEditor.java

org.xtuml.bp.ui.sem/src/org/xtuml/bp/ui/sem/factories/SEMPageFactory.java


</pre>

Fork/Repository: https://github.com/travislondon/bptest
Branch: 9684_association_editing_enhancements

<pre>

org.xtuml.bp.core.test/META-INF/MANIFEST.MF
org.xtuml.bp.core.test/src/CoreGlobalsTestSuiteGenerics.java
org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/
AssociationTableEditorTests.java

org.xtuml.bp.test/src/org/xtuml/bp/test/TestUtil.java 

</pre>

Fork/Repository: https://github.com/travislondon/models
Branch: 9684_association_editing_enhancements

<pre>

AssociationEditing/models/AssociationEditing/AssociationEditing.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/
AssociationEditing.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/A/A.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/B/B.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/C/C.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/D/D.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/E/E.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/F/F.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/G/G.xtuml
AssociationEditing/models/AssociationEditing/AssociationEditing/H/H.xtuml
AssociationEditing/models/AssociationEditing/ImportPackage/ImportPackage.xtuml
AssociationEditing/models/AssociationEditing/ImportPackage/ImportClass/
ImportClass.xtuml
AssociationEditing/models/AssociationEditing/ImportPackage/ImportClass2/
ImportClass2.xtuml
AssociationEditing/models/AssociationEditing/ImportPackage/OtherClass/
OtherClass.xtuml
AssociationEditing/.project

</pre>

### End

