---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Design Note

1. Abstract   
-----------   
This note describes the approach we shall take to implement the Model Element 
Move functionality in BridgePoint.  

2. Document References     
----------------------   
<a id="2.1"></a>2.1 [Model Element move (Issue 8321)](https://support.onefact.net/issues/8321)  
This is a link to this issue in the issue tracking system.  

<a id="2.2"></a>2.2 [Analysis note for Model Element Move Issue](https://support.onefact.net/issues/8031) 
The [analysis note produced by this work](../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md) 
was used during the SOW creation to help define the requirements for this project.  

<a id="2.3"></a>2.3 [Statement of Work for Model Element Move](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document.  This document is 
internal to the One Fact team.

<a id="2.4"></a>2.4 [Inconsistent proxy paths (Issue 8454)](https://support.onefact.net/issues/8454)  
Proxy paths are not written consistently.  

<a id="2.5"></a>2.5 [Issue 8458 - Create Test Model for Model Element Move](https://support.onefact.net/issues/8458)  

<a id="2.6"></a>2.6 [Issue 3532 design note - Support data type move capabilities through cut, copy, paste](../8031_Analyze_Model_Element_Move/i3532.dnt)  
This is the note introduced when datatype move support was introduced into BridgePoint.  

<a id="2.7"></a>2.7 [Original Model Element Move Design note - before redesign](https://github.com/xtuml/bridgepoint/blob/82f9dace19ee79c8011f687eb2d12315ea6e8dc3/doc-bridgepoint/notes/8321_Model_Element_Move/8321_Model_Element_Move.dnt.md)  
This Model Element Move project's original selected design encountered problems 
during implementation and a different design approach, outlined in this note, 
was taken. This link shows the original design. The background section below 
describes more about the problems encountered during implementation that 
caused this change of direction.  

3. Background   
-------------     

[This project's original design note](https://github.com/xtuml/bridgepoint/blob/82f9dace19ee79c8011f687eb2d12315ea6e8dc3/doc-bridgepoint/notes/8321_Model_Element_Move/8321_Model_Element_Move.dnt.md) 
was reviewed and approved early in the Model Element Move project. Implementation 
of that design uncovered multiple problems that prevented the original 
design from working as described. At the heart of the problems was the original
design's intention to use the existing copy/paste infrastructure in the 
implementation of the move behavior. Reusing the copy/paste infrastructure 
allowed much of the complexity of this project to be handled via the existing 
infrastructure. Even the [background section in the project's analysis note] 
(../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md)
looked quite a bit at the existing infrastructure for the solution.  

Prior to the work done for the original design's implementation, BridgePoint 
did have the ability to perform cut/paste. However, this "old" cut/paste 
behavior took full advantage of copy/paste. In fact, the cut/paste behavior 
was simply copy/paste followed by delete. The paste in this situation 
resulted in new UUIDs for all elements pasted. The original design took 
the approach that the change to make that prior cut/paste behavior a move 
was essentially simply a matter of preventing new UUIDs from 
being created for the pasted elements. 

Implementation showed that this approach would not satisfy the requirements. 
Specifically, the fact that reuse of the existing infrastructure would 
require delete to be called after the "move" was unacceptable. The 
delete() operation is modeled. Each meta-model element implements delete and 
the implementation is recursive. In the initial design it was believed that 
the implementation could simply modify the delete operations to prevent the 
recursive delete. However, this was not an appropriate solution. The move 
operation can not delete elements, but it does "unhook" the elements and 
reconnect them to the selected destination. Reusing the copy/paste infrastructure 
did not allow the element deletion and "unhooking" to be done independently.  

It is further noted that the original design called out that proxy removal 
would be done with this issue. Proxy removal is desirable and the note calls 
out that the removal of proxies would have helped this issue with regard
the requirement that a minimal change set is performed on move. However, 
proxy removal caused problems. The problems were in the area of model 
reload and model compare, both of which use the proxies. This resulted 
in the need to leave the proxies, which in turn caused requirement 
4.2 to not be met.  

It was a painful decision, but what it came down to was that "move 
is not copy/paste/delete" and it can not be treated as such. This meant that 
the original design choice, and even the original analysis had to be revisited.  

The design note that follows is the result of this revisiting of the 
original analysis and design for this project.  

A different solution has been selected that was not presented in the original 
analysis. This design note describes this approach. A 
[link to the original design](#2.7) that was abandoned is left in place for 
historical purposes, but the design note that follows is the approach taken.  

4. Requirements   
---------------   
The requirements are defined in this issue's [Statement of Work](#2.3). For convenience, 
the requirements defined in the SOW are being carried forward here.  

4.1 Selected model elements are moved in a single operation that can be undone with 
a single undo operation.  

4.2 Within the limitations of the Eclipse Team interface, move operations are 
performed in a way that maximizes the probability of the underlying 
configuration-management system detecting that a move operation (as opposed 
to a sequence of delete and create operations) occurred.  

4.3 Model element move shall be supported for the following model elements 
(see Limitations [[2.3]](#2.3)):  
4.3.1 Data type definition  
4.3.2 Component definition  
4.3.3 Interface definition  
4.3.4 Class definition  
4.3.5 Component reference  
4.3.6 Imported class  
4.3.7 Package (when a package contains supported model elements)  

4.4 Whenever visibility permits, the connection between a moved model element 
and other model elements shall be maintained.  

For example, a data type can be moved without affecting the attributes and 
parameters typed by it so long as the data type remains visible to those 
elements typed by it.  

4.5 The model-element move capability is enabled in the user interface only 
when all model elements within the current selection are supported model 
element types for the move capability.  

4.5.1 The option to move shall be present but disabled when a selection is 
not a valid move selection.  


5. Analysis   
-----------   
As described in the background section above, the [initial design](#2.7) and 
analysis for this issue led to an implementation that was not 
acceptable. The following calls out a different approach. 

5.1 Move elements by disconnecting selected elements from their 
containers and reconnecting them to the selected destination container.  

5.1.1 Almost every element in BridgePoint is a sub-type of PackackagableElement (PE_PE). All Model 
Elements being moved [4.3] inherit from PE_PE. 

5.1.2 All PackackagableElements are contained in either a Package or a Component.  
5.1.2.1 PackackagableElements inside Packages (EP_PKG) have a relationship with EP_PKG via R8000.  
5.1.2.1 PackackagableElements inside Components (C_C) have a relationship with C_C via R8003.  

6. Design   
----------------   
The following is Pseudo code for the "new" Model Element Move implementation.  
`PasteAction.java::run()` drives the paste change procedure, therefore the pseudo code
below is to a large extent implemented at PasteAction.java::run().  <b>The 
copy/paste behavior shall not be changed during this change.</b>  

<pre>
  User Makes a Selection through either Model Explorer or Canvas
  
  User Right-clicks the selection
  
  Cut is enabled only if the selection is valid
  
  User Selects Cut
  
  Selection is stored in a list<NonRootModelElement> (ELEMENTS_TO_MOVE). 

  User Makes a Selection through either Model Explorer or Canvas
  
  User Right-clicks the selection

  Paste is enabled only if the selection is valid
  
  User Selects Paste
  
  Start a Move Transaction
  
  Collect the RGOs that will be affected by this Move 
  
  for each selected_element in ELEMENTS_TO_MOVE 
      disconnect the graphical element associated selected_element from its canvas
      connect selected_element to the canvas associated with the destination
  end for 
   
  for each selected_element in ELEMENTS_TO_MOVE
    unhook the selected_element from container it is attached to (R8000/R8003)
  end for
    
  for each selected_element in ELEMENTS_TO_MOVE
    update selected_element''s  model root and set it to the destination model root
  end for
  
  for each selected_element in ELEMENTS_TO_MOVE
     Connect selected_element to the destination
     Add a "ModelElementMovedModelDelta" to the ongoing transaction
  end for    

  for each selected_element in ELEMENTS_TO_MOVE
    if (selected_element downgrade is needed)
      perform the downgrade
      add to the downgraded elements list
    end if
  end for

  show the user the elements that were downgraded
  if (user DOES want to continue)
    Complete the Move Transaction (this is where any affected files or folders get moved on disk and changes are persisted)
  else 
    Abort the Move transaction
  end if


</pre>

6.1 Modify the cut/paste operation to be analogous to "move" by making it a 
single transaction.  

6.1.1 The current infrastructure uses an abstract class, 
`core/ui/CopyCutAction.java extends org.eclipse.jface.action.Action`. 
To define the behavior of the move operation, this interface shall be modified 
to allow the cut/paste operation to be done in a single transaction as opposed to 2 
separate transactions. This new transaction shall be started and ended
within `PasteAction.java::run()`.  

In addition to modifications in the abstract class, the classes that 
extend this shall also be modified as required for this change. These classes are:  
* `core/ui/CopyAction.java` - This shall be modified as needed to adhere to interface 
changes, but functionality shall not be changed.
* `core/ui/CutAction.java` - The deletion of selected elements will no longer be 
performed during cut. Additionally, cut does not start a transaction any longer.  

6.1.2 Introduce a new abstract class, `CutCopyPasteAction extends Action`  
Modify the existing CutCopyAction and PasteAction classes to extend this 
new class instead of extending the Action class directly as was done before this
change.  

Move the common implementation from `CutCopyAction` "up" into this new
class. This change allows `PasteAction` to know when the user has selected 
cut vs copy and to perform the cut/paste (move) in a single transaction 
that occurs in PasteAction (previously cut was performed in a transaction of its own).  

6.1.3 Remove the code that was deleting elements during cut.
This change is in `CutAction.java::postRun()`. Rework this code as needed to 
account for the fact that elements are no longer being deleted.  

6.1.4 Add an attribute to CutCopyPasteAction, `ELEMENT_MOVE_SOURCE_SELECTION`, that 
holds the selection made by the user when cut is selected.  

6.2 Introduce a BridgePoint transaction type for Move  
In BridgePoint, during a transaction the memory model is modified as needed to perform a
specified task. When the memory model changes are completed the transaction is ended and
at that time file persistence occurs. The move operation requires file handling that is 
different than what is done by other BridgePoint transaction types. For example, there are
transaction types for element creation and deletion, but move is different at the file 
system level and requires different processing.  

6.2.1 In Datatypes/ModelEventNotification add a new enum DELTA_MODEL_ELEMENT_MOVE.  
Note that this enum is actually used in a bitset implementation so the value 
of this new element shall be 16384 ( 0b0010000000000000). 

6.2.2 Introduce the Java code required to use the new ModelDelta type.  

6.2.2.1  Introduce class ModelElementMovedModelDelta extends BaseModelDelta, and 
add a new interface operation, void modelElementMoved(ModelChangedEvent event, IModelDelta delta), 
to IModelChangeListener.   

6.2.2.2 Updated places as needed that have to implement this interface. Note 
that the only one that has a non-empty implementation is 
ComponentTransactionListener.java, and in it is where we shall add code that 
performs the folder move when the transaction ends.  

6.2.2.3 Introduce ComponentTransactionListener::movePMC() to handle the 
processing of the new ModelElementMovedModelDelta that may exist in a transaction.

This new operation is invoked only by ComponentTransactionListener.java::endTransaction(), 
and only when the new ModelElementMovedModelDelta is part of the transaction being processed.  

6.3 Handle disconnecting of elements for which a simple unrelate on R8000/R8003 
does not fully disconnect the element  

6.3.1 Introduce of a Disconnect operation for each element in ooaofooa that may be 
moved and that has to be disconnected by performing more work than a simple 
unrelate on R8000/R8003

6.3.1.1 This situation is actually unique to Package (EP_PKG). The reason is that it is also 
connected to S_SYS on R1401/1405.

6.4 Make changes necessary to move graphics  

To move the graphics associated with model elements being moved we shall take the same
"disconnect/reconnect" approach that is taken to move the selected ooaofooa elements. For
graphics the container that a selected element needs to be disconnected from is 
represented in the ooaofgraphics by GD_MD->GD_GE[R1]. 

The exception is in the situation where component references are unassigned, in this 
case we must run graphics reconciliation to remove the provision and requirements when the
element is unassigned. However, note that since unassign is being performed in this situation, 
no code change outside of the unassign is required.

6.4.1 Modify PasteAction.java::run() to call the abstract operation PasteAction.java::processGraphics() 
at the point during the move operation that we are ready to disconnect and reconnect the graphical elements
associated with each user-selected model element.  

The implementation of processGraphics() is found in `{Explorer | Canvas}PasteAction.java`.  However, the 
implementation in ExplorerPasteAction.java shall be modified to redirect all processing for this action to 
CanvasPasteAction.java so that implementation for this is in a single location. 

6.4.1.1 Modify CanvasPasteAction.java::handleNonDiagramElementAsDestination() to
handle this move case.  

Here is the pseudo code: 

```
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

6.5 Downgrade model elements that lose visibility to their referred-to types during move  

When a model element is moved from one location to another, BridgePoint
needs to perform work to clean up "referring" (RGO) and "referred to" (RTO) 
links to the element being moved.  This work must be done in both the originating and 
target containers.  Here is pseudo-code describing the work to be performed:

```
for each selectedElement
  // Downgrade src 
  find RGOs
  for each rgo in RGOs
    if ( !isVisibleFromSrcToDestination )
      downgrade rgo
      add to list of downgraded elements
    end if
  end for

  // Downgrade dest 
  find RTOs
  for each rto in RTOs
    if ( !isVisibleFromDestinationToSrc )
      downgrade rto
      add to list of downgraded elements
    end if
  end for

end for
```

6.6 UI Changes

6.6.1 Modify the tree list box that shows Model Elements affected by the 
cut/paste operation.  

6.6.1.1 The dialog shall allow the user to cancel   

When cancel is selected no action is performed on the underlying model.

6.6.1.2 Text shall be added to tell the user to enable IPRs and
check visibility.  

6.6.1.3 As per the SOW [[2.3](#2.3)], add save and print options to the dialog.  

6.6.1.3.1  Update `ScrolledTextDialog.java` to take a new parameter in the
constructor that indicates if the save and print buttons shall be used.  

6.6.1.3.2  Update the existing callers of ScrolledTextDialog such that the 
`TransactionManager.java` is the only one that uses save and print.  Other 
users retain existing behavior and do not use save and print.  

6.6.1.3.3  Add code to implement button "Save...".  This button opens a modal
dialog that allows the user to select a file to save into.  The contents of 
the list box (the affected elements) are written to the file if the user 
completes the dialog.  No action is taken if the user cancels the dialog.   

6.6.1.3.4  Add code to implement button "Print...".  This button opens a modal
printer selection dialog.  The contents of the list box (the affected 
elements) are sent to the printer if the user completes the dialog.  No action
is taken if the user cancels the dialog.  

6.6.2 Assure that the cut CME is only enabled when it should be.  

`bp/ui/explorer/actions/ui/Explorer{Cut | Copy | Paste}Action.java::isEnabled()` 
is where BridgePoint determines if the CME should be enabled or not. Additionally, 
there is an analogous implementation for canvas in 
`bp.ui.graphics.actions/Canvas{Cut | Copy | Paste}Action.java::isEnabled()`  

6.6.2.1. Disable paste when the selected target is the same as the source.  

An attempt to paste to the same location that the copy was made from is 
considered an invalid selection and shall not be allowed.  

In the PasteAction, the operations look to see if the destination allows paste 
for elements in the source being pasted to the target. It does this by calling 
an ooaofooa operation that is of the from {target model element instance}.Paste{Source Model Element Name}. 
The structure of this code is such that this behavior is essentially duplicated 
in `{Explorer | Canvas}PasteAction.java`.  

6.6.2.1.1 Refactor this and "move up" the operation named `clipboardContainsPastableModelElements()` 
into the parent class`core/ui/PasteAction.java` to facilitate adding the check 
to assure that on move, if the source and target PMCs match, paste is not enabled.  

6.6.2.1.2 The code added to assure paste is disabled if the source and 
target PMCs match was added to the refactored `core/ui/PasteAction.java::isEnabled()` 
function.  

6.6.2.1.3 The change is specific to move, copy/paste still allows paste into the 
same PMC as it did before this change.  

6.6.3 If a move is in progress do not allow more than 1 selected destination 
for paste. This is another change that is specific to move and is still allowed 
by copy/paste.  


7. Design Comments   
------------------   
7.1 Fix inconsistent proxy paths [[2.4](#2.4)]  
Investigation into fixing this problem showed that there was no problem with 
the way proxy paths were being written. After this investigation no additional 
action was required.  

8. User Documentation   
---------------------  
8.1 This issue changes the behavior of cut/paste making it analogous to move. 
BridgePoint documentation shall be updated accordingly.  
8.1.1 The cut operation no longer causes any change. When cut is selected, no 
change occurs unless paste is performed.  
8.1.2 Unlike copy/paste which creates new elements on paste, the modified cut/paste operation leaves identifiers intact.  
8.1.2.1 This means that while a copied buffer may be pasted multiple times, a cut buffer can be pasted only 1 time.  

9. Acceptance Test   
------------------  

Use cases for the tests below are found in the SOW [[2.3](#2.3)]. The unit test 
model(s) and use case implementations shall be further described by [[2.5](#2.5)].  
 
9.1 Test to assure that the move operation is atomic  

9.2 Test to assure that the move operation maximizes the probability of the  
underlying configuration-management system detecting that the move within  
the limitations of the eclipse team interface.  

9.3 Test element move for each supported model element as specified in section 4.3. 

9.4 Test to assure that when visibility permits, the connection between a 
moved model element and other model elements shall be maintained.  

9.4.1 Test to assure that when visibility does not permit the connection between 
model elements to be maintained proper defaults are used.

9.4.1.1 The above test shall be extended to test both RTO and RGO downgrades.

9.5 Test to assure the model-element move capability is only enabled for 
valid model element selections and is present but disabled otherwise.

9.6 Assure that the user documentation has been updated (see section 8).  

End
---
