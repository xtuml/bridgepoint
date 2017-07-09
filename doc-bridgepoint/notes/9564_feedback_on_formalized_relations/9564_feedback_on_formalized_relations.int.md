---

This work is licensed under the Creative Commons CC0 License

---

# Feedback for formalized associations  
### xtUML Project Implementation Note

### 1. Abstract

This note describes the files changed for this work.

### 2. Document References

In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.  
<a id="2.1"></a>2.1 [BridgePoint DEI #9564](https://support.onefact.net/issues/9564) Feedback on formalized relations  
<a id="2.2"></a>2.2 [Design note](https://github.com/travislondon/bridgepoint/blob/9564_feedback_on_formalized_relations/doc-bridgepoint/notes/9564_feedback_on_formalized_relations/9564_feedback_on_formalized_relations.md) Project design note.  

### 3. Background

See [[2.2]](#2.2).  

### 4. Requirements

See [[2.2]](#2.2).  

### 5. Work Required

See [[2.2]](#2.2).  

### 6. Implementation Comments

None.  

### 7. Unit Test

See [[2.2]](#2.2).  

### 8. User Documentation

Describe the end user documentation that was added for this change. 

### 9. Code Changes

Fork/Repository: https://github.com/travislondon/bridgepoint  
Branch: 9564_feedback_on_formalized_relations  

<pre>

doc-bridgepoint/notes/9564_feedback_on_formalized_relations/
9564_feedback_on_formalized_relations.int.md
doc-bridgepoint/notes/9564_feedback_on_formalized_relations/
9564_feedback_on_formalized_relations.md
doc-bridgepoint/review-minutes/9564_feedback_on_formalized_relations_ant.rvm.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Association/
Association.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Class As Link/
Class As Link.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/
Class As Subtype/Class As Subtype.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Datatypes/Datatypes.xtuml
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java

org.xtuml.bp.pkg/plugin_customization.ini

org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/Datatypes/
Datatypes.xtuml

org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/figures/
DecoratedPolylineConnection.java
org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/parts/
ConnectorEditPart.java
org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/parts/TextEditPart.java
org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/preferences/
GraphicalEditorPreferences.java

</pre>  

Fork/Repository: https://github.com/travislondon/bptest  
Branch: 9564_feedback_on_formalized_relations  

<pre>

org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/SymbolTest.java

</pre>

Fork/Repository: https://github.com/travislondon/models  
Branch: 9564_feedback_on_formalized_relations  

<pre>

AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedbackTestModel.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/AssociationFeedback.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/LinkedAssociationFormalizedLink/
LinkedAssociationFormalizedLink.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/LinkedAssociationFormalizedOne/
LinkedAssociationFormalizedOne.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/LinkedAssociationFormalizedOther/
LinkedAssociationFormalizedOther.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/LinkedAssociationUnformalizedLink/
LinkedAssociationUnformalizedLink.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/LinkedAssociationUnformalizedOne/
LinkedAssociationUnformalizedOne.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/LinkedAssociationUnformalizedOther/
LinkedAssociationUnformalizedOther.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SimpleAssociationFormalizedOne/
SimpleAssociationFormalizedOne.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SimpleAssociationFormalizedTwo/
SimpleAssociationFormalizedTwo.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SimpleAssociationUnformalizedOne/
SimpleAssociationUnformalizedOne.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SimpleAssociationUnformalizedTwo/
SimpleAssociationUnformalizedTwo.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SubtypeOneFormalized/SubtypeOneFormalized.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SubtypeOneUnformalized/SubtypeOneUnformalized.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SubtypeTwoFormalized/SubtypeTwoFormalized.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SubtypeTwoUnformalized/SubtypeTwoUnformalized.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SupertypeFormalized/SupertypeFormalized.xtuml
AssociationFeedbackTestModel/models/AssociationFeedbackTestModel/
AssociationFeedback/SupertypeUnformalized/SupertypeUnformalized.xtuml
AssociationFeedbackTestModel/.project

</pre>


### End

