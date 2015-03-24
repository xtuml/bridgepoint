---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# BridgePoint Editor Navigation enhancements and fixes
### xtUML Project Implementation Note


1. Abstract
-----------
A provided a screenshot for BrdigePoint UI shows 3 bugs and 1 needed 
enhancement, this note describe how to address them. 

2. Document References
----------------------
[1] Issues 229, https://github.com/xtuml/internal/issues/229  
[2] CQ DEI dts0101055471 - BridgePoint Editor Navigation enhancements and fixes  
[3] Design note 229_dts0101055471_dnt.md : 
	https://github.com/xtuml/internal/blob/229_dts0101055471_2/doc-internal/notes/229_dts0101055471/229_dts0101055471_dnt.md
[4] Redmine Issue 7535, https://support.onefact.net/redmine/issues/7535

3. Background
-------------
See [3]. 

4. Requirements
---------------
See [3].

5. Work Required
----------------
See [3].

6. Implementation Comments
--------------------------
6.1 The following changes has been made on the design: 
6.1.1 Adding new getPath() operations for needed model elements to be called in
	getTitleTooltip() method is canceled, instead Cl_c.getPath() method is used.
	
The method -Cl_c.getPath()- body is moved to HierarchyUtil in bp.core plugin to 
be accessible by all plugins.

6.1.2 When Eclipse session is closed, all opened editors data is saved into hard
disk using Memento. Unfortunately the editor icon is not one of them. When 
Eclipse session starts, it opens editors in lazy state, using the information
saved by Memento. As the icon is not stored, Eclipse gets the editor icon using 
plugin.xml entries, which return the editor icon instead of the element icon (as 
the getTitleImage() method did not invoked because of the lazy loading)

Therefore, focusGain() event is used for the three editors to force icon editor
title refresh to invoke the getTitleImage() for all editors instead of the active
editor only.

6.2 The junit tests detect a bug in PlaceHolderEntry. As the code creates 
a single  PlaceHolderFile object  for each extension, the Junit shows a use-case
where the editor element is changed, and PlaceHolderFile was already exist, which
leads to a NPE as the old editor element is used instead of the current one.  

 Therefore, a new method updateModelElementID(ModelElementID) is invoked each
 time getPlaceHolderFile(ModelElementID, String, Object, boolean) is invoked. 
 The new method will replace the ModelElementID for PlaceHolderEntry object
 if  needed.

7. Unit Test  
------------
7.1 By running Junit tests, it is found there are no need to add new tests for
	editors tabs. 

8. Code Changes
---------------
Branch name: 229_dts0101055471_2

<pre>

com.mentor.nucleus.bp.core [internal 229_dts0101055471_2]/icons/
    Description_decorator.gif
com.mentor.nucleus.bp.core [internal 229_dts0101055471_2]/icons/metadata/
    System.gif
com.mentor.nucleus.bp.core [internal 229_dts0101055471_2]/models/
    com.mentor.nucleus.bp.core/ooaofooa/State Machine/Transition/
    Transition.xtuml
com.mentor.nucleus.bp.core [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/util/EditorUtil.java
com.mentor.nucleus.bp.core [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/util/HierarchyUtil.java

com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/CommunicationLinkTestsGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/CommunicationMessageTestsGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/CommunicationTestsGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/ComponentFormalizationTests.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/ModelTransactionTestGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/ModificationValidationTestsGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/RefreshTestCoreGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/RenameInvolvingResourceTestGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/SequenceTestsGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/TigerNatureExistingProjectsTestGenerics.java
com.mentor.nucleus.bp.core.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/core/test/UseCaseTestsGenerics.java

com.mentor.nucleus.bp.debug.ui.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/debug/engine/VerifierTransitionActionTests.java
com.mentor.nucleus.bp.debug.ui.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/debug/ui/test/execute/VerifierInterfaceExecutionTests.java

com.mentor.nucleus.bp.debug.ui.test.execute.

com.mentor.nucleus.bp.io.mdl.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/io/mdl/test/IOMdlNestedTestGenerics.java
com.mentor.nucleus.bp.io.mdl.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/io/mdl/test/IOMdlUnicodeTestGenerics.java
com.mentor.nucleus.bp.io.mdl.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/io/mdl/test/pkgcm/EditorTestUtilities.java
com.mentor.nucleus.bp.io.mdl.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/io/mdl/test/pkgcm/ModifyRelationTest.java
com.mentor.nucleus.bp.io.mdl.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/io/mdl/test/pkgcm/restore/
    PkgCMRestoreModifyRelationTest.java

com.mentor.nucleus.bp.io.sql.test [internal 229_dts0101055471_2]/
    expected_results/odms.imp
com.mentor.nucleus.bp.io.sql.test [internal 229_dts0101055471_2]/
    expected_results/test_ca_smsmc2.imp

com.mentor.nucleus.bp.pkg [internal 229_dts0101055471_2]/
    plugin_customization.ini

com.mentor.nucleus.bp.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/test/common/CanvasTestUtils.java
com.mentor.nucleus.bp.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/test/common/GenericEditorUtil.java
com.mentor.nucleus.bp.test [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/test/common/TextEditorUtils.java

com.mentor.nucleus.bp.ui.canvas [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/ui/canvas/Cl_c.java
com.mentor.nucleus.bp.ui.canvas [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/ui/canvas/util/GraphicsUtil.java

com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/CanvasCopyTests.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/CanvasEditorReloadContentsTest.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/CanvasTestUtilities.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/ClassToStateDiagramNavigationTest.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/CloseCanvasEditor.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/ConnectorsAsAnchorsTest.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/FreeFloatingConnectorTest.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/I835OpenDiagramEditorWithSearchView.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/OpenCanvasEditor.java
com.mentor.nucleus.bp.ui.canvas.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/canvas/test/ShapeResizeTest.java

com.mentor.nucleus.bp.ui.graphics [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/graphics/editor/GraphicalEditor.java
com.mentor.nucleus.bp.ui.graphics [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/graphics/editor/ModelEditor.java

com.mentor.nucleus.bp.ui.properties.test [internal 229_dts0101055471_2]/src/
    com/mentor/nucleus/bp/ui/properties/test/RefreshTestProp.java

com.mentor.nucleus.bp.ui.text [internal 229_dts0101055471_2]/arc/
    create_modeladapter_java.arc
com.mentor.nucleus.bp.ui.text [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/ui/text/activity/ActivityEditor.java
com.mentor.nucleus.bp.ui.text [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/ui/text/activity/ActivityEditorInput.java
com.mentor.nucleus.bp.ui.text [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/ui/text/description/DescriptionEditor.java
com.mentor.nucleus.bp.ui.text [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/ui/text/description/DescriptionEditorInput.java
com.mentor.nucleus.bp.ui.text [internal 229_dts0101055471_2]/src/com/mentor/
    nucleus/bp/ui/text/placeholder/PlaceHolderEntry.java

com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/
    UITextSuite.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/DuplicateRelationshipNumberParseAllTest.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/ParseAllOnModelReloadTest.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/PlaceHolderUpdateTest.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/TextEditorReloadContentsTest.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/activity/ActivityEditorInteraction.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/activity/CloseActivityEditor.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/activity/I643OALKeywordsHighlightingTest.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/activity/
    I697OpenActivityEditorFromMarker.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/activity/OpenActivityEditor.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/activity/ParseActivity.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/description/CloseDescriptionEditor.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/description/DescriptionEditorInteraction.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/description/OpenDescriptionEditor.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/i673Tests/rename/
    I673RenameObjectsAndTestActivityEditors.java
com.mentor.nucleus.bp.ui.text.test [internal 229_dts0101055471_2]/src/com/
    mentor/nucleus/bp/ui/text/test/i673Tests/rename/
    I673RenameObjectsAndTestDescriptionEditors.java

doc-internal [internal 229_dts0101055471_2]/notes/229_dts0101055471/
    229_dts0101055471_dnt.md
doc-internal [internal 229_dts0101055471_2]/notes/229_dts0101055471/
    229_dts0101055471_int.md
doc-internal [internal 229_dts0101055471_2]/review-minutes/Leo/
    229_UI_updates_rvm.md



</pre>

End
---

