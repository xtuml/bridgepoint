---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the work performed to implement the Model Element Move (MEM)
feature.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8321](https://support.onefact.net/issues/8321) 
This is a link to this issue in the issue tracking system.  

<a id="2.2"></a>2.2 [MEM design note](8321_Model_Element_Move.dnt.md) 

<a id="2.3"></a>2.3 [MEM Manual Test Procedure](https://support.onefact.net/issues/8837) 

<a id="2.4"></a>2.4 [MEM Test Result Spreadsheet](https://docs.google.com/spreadsheets/d/1eJmEWtx3EDawwCslxL2MfvaqoJm8JawFnoCTLPuX9SM/edit#gid=1793892663) 
This is a spreadsheet used as an aide to track results during the long manual MEM test procedure. 

<a id="2.5"></a>2.5 [MEM Visibility Downgrade Research](8321_VisibilityDowngradeResearch.md)  

<a id="2.6"></a>2.6 [Undo is not available for a move (cut/paste) operation](https://support.onefact.net/issues/8755)  
This is a followup issue that must be completed before MEM is finalized.  

<a id="2.7"></a>2.7 [The initial Cut implemented (Model Element Move) currently allows a limited element selection
](https://support.onefact.net/issues/8798)  
This is a followup issue that must be completed before MEM is finalized.  

<a id="2.8"></a>2.8 [MEM SOW](https://drive.google.com/drive/u/0/folders/0B834tggB4vylMENIOWxhY29nNGs)  

<a id="2.9"></a>2.9 [Enable Summary data in the BridgePoint Instance Population Viewer](https://support.onefact.net/issues/8810)  

<a id="2.10"></a>2.10 [Downgrade of component reference leaves an orphaned connector](https://support.onefact.net/issues/8840)  

3. Background
-------------
See [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)

4. Requirements
---------------
See [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)

5. Work Required
----------------
See [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)


6. Implementation Comments
--------------------------
6.0 Graphical Element Move  
6.0.1 In the design note section 6.4.1.1 that describes the changes made to CanvasPasteAction.java::handleNonDiagramElementAsDestination() to move graphics a change was made.  
If the element moved if being moved from one PMC (file) to another then the code updates the PMC and
model root of the soruce element.
```
Before Change:
  Find the GraphicalElement (GD_GE) associated with selected_element
  Find the Model (GD_MD) associated with the GraphicalElement
  unrelate the GraphicalElement from the Model across R1  
  relate the GraphicalElement to the destination's Model across R1
  update the selected_element's associated container symbol on R307 if containment has changed
```
```
After Change:
  Find the GraphicalElement (GD_GE) associated with selected_element
  If the GraphicalElement was not found load the graphical model for the source element and search again
  Find the canvas (GD_MD) that the GD_GE is part of by navigating GD_GE->GD_MD[R1]
  Find the canvas (GD_MD) that has the same "represents" as the GD_GE (if there is one)
  if the GD_GE DOES have a canvas then update its GD_MD PMC to be the destination
  update the PMC of the GD_GE
  unrelate the GraphicalElement from the Model across R1  
  relate the GraphicalElement to the destination's Model across R1
  update the selected_element's associated container symbol on R307 if containment has changed  
```

6.0.2 The design note's section 6.4 read as follows:  
```
The exception is in the situation where component references are unassigned, in this case we must run graphics reconciliation to remove the provision and requirements when the element is unassigned. However, note that since unassign is being performed in this situation, no code change outside of the unassign is required.
```
That statement is not true. The move implementation does NOT use graphics reconciliation. If fact, graphics reconciliation is
explicitly disabled for the Move transaction. The Component Reference case does NOT need graphics reconciliation. The reason it does not is that the model eleent downgrade explicitly unformalizes the component reference in the case where it loses
visiibility and needs to be unformalized. This act of unformalzing causes the graphics for that component reference to be updated.  

6.0.2.1 There is a known bug in the model downgrade case of a component reference. The problem manifests itself by showing un
un-formalized component reference with a stick and no end after the downgrade [2.10](#2.10).

6.1 During implementation, section 6 of the design note that describes the MEM flow of control was updated. The update was
made to account for the fact that move of graphical instances is done before any other modifications. This was simply an error in the flow that was fixed.

6.2 In design note section 6.5 a TODO was left that referenced an external document. This external document captured some additional detail about model element downgrade research and its implementation. The document referred to has been captured here [2.4](#2.4), and that TODO was removed.  

6.3 MEM requires UNDO functionality to be present. In this initial implementation UNDO is not implemented. A follow-on issue has been raised for this task [2.6](#2.6).  

6.4 This initial version of MEM limits the elements that may be moved to packages and datatypes.  A follow-on issue has been raised [2.7](#2.7) to add the ability to move other models elements as specified in this issues Statement of Work [2.8](#2.8).

6.5 Instance Population Viewer
BridgePoint has an internal utility that allows instance population counts to be viewed by model root. During debugging of issues found during testing this utliity was used. In order to use the utility some changes were made.  
6.5.1 The ooaofgraphics instance population was added  
6.5.2 The "__default_root" population was added  
6.5.3 A bug that caused statistics to be incorrect for some models roots was fixed by turning off summary data in this utility. A sererate issue was raised about this problem [2.9](#2.9).  


7. Unit Test
------------
7.1 Run the manual test suite for this issue [2.3](#2.3).  
Note that this manual test references the fact that there is a spreadsheet, [2.4](#2.4),
that can be used to help track results of the tests in this suite.  

8. User Documentation
---------------------
No change was made to user documentation.  

9. Code Changes
---------------
Branch name: [models/rmulvey/8321_Model_Element_Move](https://github.com/rmulvey/bridgepoint/tree/8321_model_element_move)
<pre>

Two models were added: ModelElementMove2, and movetest.

Both of these models are used as part of the manual test procedure [2.3].

</pre>

Branch name: [bridgepoint/rmulvey/8321_Model_Element_Move](https://github.com/rmulvey/bridgepoint/tree/8321_Model_Element_Move)

<pre>

> doc-bridgepoint/notes/8321_Model_Element_Move/8321_Model_Element_Move.dnt.md
> doc-bridgepoint/notes/8321_Model_Element_Move/8321_Model Element_Move.int.md
> doc-bridgepoint/notes/8321_Model_Element_Move/BridgePointArchitecture.jpg
> doc-bridgepoint/notes/8321_Model_Element_Move/BridgePointArchitecture.png
> doc-bridgepoint/notes/8321_Model_Element_Move/BridgePointArchitecture.svg
> doc-bridgepoint/notes/8321_Model_Element_Move/BridgePointArchitecture.ucls
> doc-bridgepoint/review-minutes/8321_Model_Element_Move.dnt.rvm2.md
> doc-bridgepoint/review-minutes/8321_Model_Element_Move.int.rvm.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Association/
    Association.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component/
    Component.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/
    Component Library/Component Reference/Component Reference.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/
    Component Library/Imported Reference/Imported Reference.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Delegation/
    Delegation.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Interface/
    Interface.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/
    Interface Operation/Interface Operation.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/
    Interface Reference/Interface Reference.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/
    Property Parameter/Property Parameter.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Satisfaction/
    Satisfaction.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/
    Constant Specification/Constant Specification.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/
    Symbolic Constant/Symbolic Constant.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Bridge Parameter/
    Bridge Parameter.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Data Type/
    Data Type.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/
    Enumeration Data Type/Enumeration Data Type.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Exception/
    Exception.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/External Entity/
    External Entity.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/
    External Entity Data Item/External Entity Data Item.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/
    External Entity Event Data Item/External Entity Event Data Item.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Function/
    Function.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Function Parameter/
    Function Parameter.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/
    Structured Data Type/Structured Data Type.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Structure Member/
    Structure Member.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/User Data Type/
    User Data Type.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/
    Package.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Packageable Element/
    Packageable Element/Packageable Element.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/
    State Machine Event Data Item/State Machine Event Data Item.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/
    Attribute.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Imported Class/
    Imported Class.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Model Class/
    Model Class.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Operation/
    Operation.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/
    Operation Parameter/Operation Parameter.xtuml
org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentTransactionListener.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/ModelElementMovedModelDelta.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/ModelStreamProcessor.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/NonRootModelElement.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/TransactionManager.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/CopyCutAction.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/CutCopyPasteAction.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/PasteAction.java

org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/rtomove/RTOMoveTests.java

org.xtuml.bp.internal.tools/META-INF/MANIFEST.MF
org.xtuml.bp.internal.tools/src/org/xtuml/bp/internal/tools/views/
    BPInstancePopulationView.java
org.xtuml.bp.internal.tools/.classpath

org.xtuml.bp.model.compare/src/org/xtuml/bp/model/compare/
    CompareTransactionManager.java

org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/
    GraphicsReconcilerLauncher.java

org.xtuml.bp.ui.explorer/src/org/xtuml/bp/ui/explorer/ui/actions/
    ExplorerCutAction.java

org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/actions/
    CanvasCutAction.java
org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/actions/
    CanvasPasteAction.java


</pre>

End
---

