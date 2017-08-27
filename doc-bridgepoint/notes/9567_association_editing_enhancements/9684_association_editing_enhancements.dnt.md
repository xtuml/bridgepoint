---

This work is licensed under the Creative Commons CC0 License

---

# Enhanced association editing  
### xtUML Project Design Note

### 1. Abstract

This note describes the design required to support table based association editing.  

### 2. Document References
<a id="2.1"></a>2.1 [Analysis Note](https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/9684_association_editing_enhancements/9567_association_editing_enhancements.md) 9684 Analysis    

### 3. Background

See [[2.1]](#2.1).  

### 4. Requirements

See [[2.1]](#2.1).  

### 5. Analysis

See [[2.1]](#2.1).

6.1 Table based editing    
6.1.1 Introduce new plug-in org.xtuml.bp.core.editors to house new editor variations  
6.1.1.1 Introduce new table based control  
6.1.1.1.1 Add class AssociationEditorTab, extending SWT Control  
6.1.1.1.2 Add plug-in extension for EditorTab, named Associations Editor with input of Package_c  
6.1.1.1.3 Add table viewer as main focus for the new control  
6.1.1.1.3.1 Define content and label providers, MetamodelContentProvider and MetamodelLabelProvider  
6.1.1.1.3.1.1 Do not consider supertype/subtype associtions as content  
6.1.1.1.3.2 Add BuildHierarchyProperties.arc, create_properties_file.inc to generate  CorePluginMessages.propeties enabling proper labels for hierarchy object elements (for example, R_ONE)  
6.1.1.1.3.3 Add ErrorToolTip class, shown as a pop up in the table when an invalid entry is given  
6.1.1.1.4 Add check box for formalization  
6.1.1.1.5 Add Description button to open new text dialog for editing descriptions of an association  
6.1.1.1.5.1 Add TextDialog class to handle text dialog support  
6.1.1.1.6 Add cell editing support  
6.1.1.1.6.1 Add ElementEditingSupport class, enabling editing of the various cell types  
6.1.1.1.6.2 Add AssociationEditingSupport extending ElementEditingSupport to include handling edit of Rules  
6.1.2 Fix IntergerCellEditor such that if dealing with a table based cell and Integer is passed rather than string it is converted   
6.1.3 Modify generate_cell_modifiers.arc to generate validation for text phrases  
6.1.4 Introduce AssociationTableDialog used to edit associations just as with the tab, only in a dialog  
6.1.4.1 Introduce new action to open the AssociationTableDialog  
6.1.4.2 Define new CMEs for Package, Association, Class As Link and Model Class  
6.1.4.2.1 For Package edit all associations in the package  
6.1.4.2.2 For Association edit all selected associations  
6.1.4.2.3 For Class As Link edit the association, or all selected Class As Link instances  
6.1.4.2.4 For Model Class edit all associations attached to the selected Model Class instances  
6.1.5 Extend Association::actionFilter() to prevent new CME when a supertype/subtype association is selected  
6.1.6 Add necessary external tool build configurations and pom file  
6.1.6.1 Adjust parent pom to include new core.editors plug-in  
6.1.7 Add new plug-in to pkg-feature  


### 7. Design Comments

None.  

### 8. User Documentation

None.   

### 9. Unit Test

```java
org.xtuml.bp.core.test.AssociationTableEditorTests.testFirstElementSelectedAndEditorStarted()
org.xtuml.bp.core.test.AssociationTableEditorTests.testFormalizedButtonStateMatchesModel()
org.xtuml.bp.core.test.AssociationTableEditorTests.testBadValueForNumber()
org.xtuml.bp.core.test.AssociationTableEditorTests.testBadValueForTextPhrase()
org.xtuml.bp.core.test.AssociationTableEditorTests.testNoEditorForOneSide()
org.xtuml.bp.core.test.AssociationTableEditorTests.testNoEditorForOtherSide()
org.xtuml.bp.core.test.AssociationTableEditorTests.testNoEditorForLink()
org.xtuml.bp.core.test.AssociationTableEditorTests.testNoEditorForOneSideBinary()
org.xtuml.bp.core.test.AssociationTableEditorTests.testNoEditorForOtherSideBinary()
org.xtuml.bp.core.test.AssociationTableEditorTests.testTabTraversal()
org.xtuml.bp.core.test.AssociationTableEditorTests.testFormalizeBinary()
org.xtuml.bp.core.test.AssociationTableEditorTests.testUnformalizeBinary()
org.xtuml.bp.core.test.AssociationTableEditorTests.testFormalizeLinked()
org.xtuml.bp.core.test.AssociationTableEditorTests.testUnformalizeLinked()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEAssociation()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEMultipleAssociation()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEAssociationAndClass()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMESimpleAssociationAndSubtype()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMESimpleAssociationSupertype()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMESupertype()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMELink()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMESubtype()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEClass()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEImportedClass()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEClasses()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEClassImportClass()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEImportedClasses()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEClassesWithSameAssociation()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEClassAndAttachedAssociation()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEDiagram()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEReflexive()
org.xtuml.bp.core.test.AssociationTableEditorTests.testCMEReflexiveClass()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModifyAssociationNumberBinary()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModifyAssociationNumberLinked()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModifyOneSideTextPhraseBinary()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModifyOtherSideTextPhraseBinary()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModifyOtherSideTextPhraseBinaryFormalized()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModifyOneSideTextPhraseLinked()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModifyOtherSideTextPhraseLinked()
org.xtuml.bp.core.test.AssociationTableEditorTests.testUndoRedo()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModificationRuleSimpleOne()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModificationRuleSimpleOther()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModificationRuleLinkedOne()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModificationRuleLinkedOther()
org.xtuml.bp.core.test.AssociationTableEditorTests.testModificationRuleLinkedLink()
```



### End
