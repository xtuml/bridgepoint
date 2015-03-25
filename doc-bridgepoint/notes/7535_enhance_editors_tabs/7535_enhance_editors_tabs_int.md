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
Branch name: rdmn7535_git229

<pre>

doc-bridgepoint [bridgepoint rdmn7535_git229]/notes/7535_enhance_editors_tabs/
    7535_enhance_editors_tabs_dnt.md
doc-bridgepoint [bridgepoint rdmn7535_git229]/notes/7535_enhance_editors_tabs/
    7535_enhance_editors_tabs_int.md

org.xtuml.bp.core [bridgepoint rdmn7535_git229]/icons/Description_decorator.gif
org.xtuml.bp.core [bridgepoint rdmn7535_git229]/icons/metadata/System.gif
org.xtuml.bp.core [bridgepoint rdmn7535_git229]/models/org.xtuml.bp.core/
    ooaofooa/State Machine/Transition/Transition.xtuml
org.xtuml.bp.core [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/util/
    EditorUtil.java
org.xtuml.bp.core [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/util/
    HierarchyUtil.java

org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/CommunicationLinkTestsGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/CommunicationMessageTestsGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/CommunicationTestsGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/ComponentFormalizationTests.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/ModelTransactionTestGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/ModificationValidationTestsGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/RefreshTestCoreGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/RenameInvolvingResourceTestGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/SequenceTestsGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/TigerNatureExistingProjectsTestGenerics.java
org.xtuml.bp.core.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/core/
    test/UseCaseTestsGenerics.java

org.xtuml.bp.debug.ui.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/
    debug/engine/VerifierTransitionActionTests.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/
    debug/ui/test/execute/VerifierInterfaceExecutionTests.java

org.xtuml.bp.io.mdl.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/io/mdl/
    test/IOMdlNestedTestGenerics.java
org.xtuml.bp.io.mdl.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/io/mdl/
    test/IOMdlUnicodeTestGenerics.java
org.xtuml.bp.io.mdl.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/io/mdl/
    test/pkgcm/EditorTestUtilities.java
org.xtuml.bp.io.mdl.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/io/mdl/
    test/pkgcm/ModifyRelationTest.java
org.xtuml.bp.io.mdl.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/io/mdl/
    test/pkgcm/restore/PkgCMRestoreModifyRelationTest.java

org.xtuml.bp.io.sql.test [bridgepoint rdmn7535_git229]/expected_results/odms.imp
org.xtuml.bp.io.sql.test [bridgepoint rdmn7535_git229]/expected_results/
    test_ca_smsmc2.imp

org.xtuml.bp.pkg [bridgepoint rdmn7535_git229]/plugin_customization.ini

org.xtuml.bp.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/test/common/
    CanvasTestUtils.java
org.xtuml.bp.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/test/common/
    GenericEditorUtil.java
org.xtuml.bp.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/test/common/
    TextEditorUtils.java

org.xtuml.bp.ui.canvas [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/Cl_c.java
org.xtuml.bp.ui.canvas [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/util/GraphicsUtil.java

org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/CanvasCopyTests.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/CanvasEditorReloadContentsTest.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/CanvasTestUtilities.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/ClassToStateDiagramNavigationTest.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/CloseCanvasEditor.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/ConnectorsAsAnchorsTest.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/FreeFloatingConnectorTest.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/I835OpenDiagramEditorWithSearchView.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/OpenCanvasEditor.java
org.xtuml.bp.ui.canvas.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    canvas/test/ShapeResizeTest.java

org.xtuml.bp.ui.graphics [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    graphics/editor/GraphicalEditor.java
org.xtuml.bp.ui.graphics [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    graphics/editor/ModelEditor.java

org.xtuml.bp.ui.properties.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/
    ui/properties/test/RefreshTestProp.java

org.xtuml.bp.ui.text [bridgepoint rdmn7535_git229]/arc/
    create_modeladapter_java.arc
org.xtuml.bp.ui.text [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/text/
    activity/ActivityEditor.java
org.xtuml.bp.ui.text [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/text/
    activity/ActivityEditorInput.java
org.xtuml.bp.ui.text [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/text/
    description/DescriptionEditor.java
org.xtuml.bp.ui.text [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/text/
    description/DescriptionEditorInput.java
org.xtuml.bp.ui.text [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/text/
    placeholder/PlaceHolderEntry.java

org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/UITextSuite.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/DuplicateRelationshipNumberParseAllTest.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/ParseAllOnModelReloadTest.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/PlaceHolderUpdateTest.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/TextEditorReloadContentsTest.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/activity/ActivityEditorInteraction.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/activity/CloseActivityEditor.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/activity/I643OALKeywordsHighlightingTest.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/activity/I697OpenActivityEditorFromMarker.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/activity/OpenActivityEditor.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/activity/ParseActivity.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/description/CloseDescriptionEditor.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/description/DescriptionEditorInteraction.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/description/OpenDescriptionEditor.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/i673Tests/rename/I673RenameObjectsAndTestActivityEditors.java
org.xtuml.bp.ui.text.test [bridgepoint rdmn7535_git229]/src/org/xtuml/bp/ui/
    text/test/i673Tests/rename/I673RenameObjectsAndTestDescriptionEditors.java





</pre>

End
---

