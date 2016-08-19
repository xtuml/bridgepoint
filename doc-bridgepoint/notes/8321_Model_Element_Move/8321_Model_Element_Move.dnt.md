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
The [analysis note produced by this work]
(../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md) was 
used during the SOW creation to help define the requirements for this project.  

<a id="2.3"></a>2.3 [Statement of Work for Model Element Move](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document.  This document is 
internal to the One Fact team.

<a id="2.4"></a>2.4 [Inconsistent proxy paths (Issue 8454)](https://support.onefact.net/issues/8454)  
Proxy paths are not written consistently.  

<a id="2.5"></a>2.5 [MC-Java README.TXT](https://github.com/xtuml/bridgepoint/blob/master/src/MC-Java/README.TXT)  
This document describes the MC-Java implementation.  

<a id="2.6"></a>2.6 [Issue 8455 - Remove dead code associated with proxies](https://support.onefact.net/issues/8455)  
  
<a id="2.7"></a>2.7 Documentation associated with the introduction of proxies in 
BridgePoint  Proxies were introduced into BridgePoint when multi-file persistence 
(PLCM) was introduced. The following engineering documents describe this:  

<a id="2.7.1"></a>2.7.1 [Issue 845 - PLCM Technical Note](i845.tnt)  
This note captures the initial brain-storming done regarding the PLCM project. 
PLCM stands for package level configuration management. Over the years since 
this feature was introduced the term PLCM has been dropped and this is now more 
commonly referred to as multi-file persistence.  

<a id="2.7.2"></a>2.7.2 [Issue 845 - PLCM Final Analysis Note](i845-PLCM_1_0.ant)  
This is the final PLCM analysis note. The PLCM project was long-lived, and 
there was an initial analysis note but this one was created at the time the 
project was split into smaller deliverables.  

<a id="2.7.3"></a>2.7.3 [Issue 845 - PLCM Design Note for milestone 2](i845-2.dnt)  
This note captures the design note of PLCM as related to proxies.  

<a id="2.8"></a>2.8 [Issue 8458 - Test Cases for Model Element Move](https://support.onefact.net/issues/8458)  

<a id="2.9"></a>2.9 Documentation associated with use of proxies for model compare and merge  
<a id="2.9.1"></a>2.9.1 [Issue 244 Design note - Fix corruption caused by class merges](244_class_merge.dnt.md)  

<a id="2.10"></a>2.10 [Issue 3532 design note - Support data type move capabilities through cut, copy, paste](../8031_Analyze_Model_Element_Move/i3532.dnt)  

<a id="2.11"></a>2.11 [Eclipse Team Interface associated with element move](http://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresAdv_hooks.htm)  
This is documentation for Eclipse Team plugin providers. It shows that file move 
support could be implemented by a provider.  

<a id="2.12"></a>2.12 [Engineering notes associated with dts0100911019](dts0100911019)  
This issue shows an example where file system changes made by BridgePoint had a 
undesirable effect on a specific type of revision control system. The specified 
system was not really the relevant issue, though it was ClearCase. The issue 
was more general and was the fact the ClearCase system used pessimistic 
locking. The issue referenced because it is an example where the BridgePoint 
team handled this issue by modify the file system change so the RCS team 
interface would handle it in a more favorable way.  

<a id="2.13"></a>2.13 [Original Model Element Move Design note - before redesign](https://github.com/xtuml/bridgepoint/blob/82f9dace19ee79c8011f687eb2d12315ea6e8dc3/doc-bridgepoint/notes/8321_Model_Element_Move/8321_Model_Element_Move.dnt.md)  
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
was simply copy/paste followed by delete. The paste in the situation 
resulted in new UUIDs for all elements pasted. The original design took 
the approach that the change to make that prior cut/paste behavior a move 
was essentially simply a matter of preventing new UUIDs  from 
being created for the pasted elements. 

Implementation showed that this approach would not satisfy the requirements. 
Specifically, the fact that reuse of the existing infrastructure would 
require delete to be called after the "move" was unacceptable. The 
delete() operation is modeled. Each meta-model element implements delete and 
the implementation is recursive. In the initial design it was believed that 
the implementation could simply modify the delete operations to prevent the 
recursive delete. However, this was not an appropriate solution. The new move 
operation does not delete element's, but it does "unhook" the elements and 
reconnect to the selected destination. Reusing the copy/paste infrastructure 
did not allow the element deletion and "unhooking" to be done independently.  

It is further noted that the original design called out that proxy removal 
would be done with this issue. Proxy removal is desirable and the note calls 
out that the removal of proxies would have helped this issue WRT the requirement 
that a minimal change set is performed on move. However, proxy removal caused 
problems. The problems were in the area of model reload and model compare, 
both of which use the proxies. This resulted in the need to leave the proxies 
which, in turn, caused requirement 4.2 to not be met.  

What is came down to, although quite a painful decision, was that "move 
is not copy/paste/delete" and it can not be treated as such. This meant that 
the original design choice, and even the original analysis had to be revisited.  

The design note that follow is the result of this revisiting the 
original analysis and design for this project. A different solution was selected 
that was not presented in the original analysis. This design note describes this 
approach. A [link to the original design](#2.13) that was abandoned is 
left in place for historical purposes, but the design note that follows 
is the approach taken.



4. Requirements   
---------------   
The requirements are defined in this issue's [Statement of Work](#2.3). For convenience, 
the requirements defined in the SOW are being carried forward here. This design 
shall create some additional requirements based on the selected implementation. 
These additional requirements shall be placed after the last requirement copied  
from the SOW.  

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
As described in the background section above, the [initial design](#2.13) and 
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

6.1 Modify cut/paste operation to be analogous to "move" by making it a 
single long-lived transaction. It is referred to as long-lived because it 
survives across multiple user operations (cut and paste). With this change, 
cut/paste is analogous to move.

The move transaction shall not terminate until either:  
* The paste is performed  
*  The transaction is terminated before paste is performed  

6.1.1 The move transaction shall be canceled if any other transaction is initiated
before the paste operation is completed.  

6.1.2 The current infrastructure uses an abstract class, 
core/ui/CopyCutAction.java extends org.eclipse.jface.action.Action, 
to define the behavior of cut/copy operations this interface shall be modified 
to allow the cut/paste operation to be a single transaction as opposed to 2 
separate transactions. In addition to this abstract class, the classes that 
extend this shall also be modified as required for this change. These are:  
* `core/ui/CopyAction.java` - This shall be modified as needed to adhere to interface 
changes, but functionality shall not be changed.
* `core/ui/CutAction.java` - The deletion of selected elements will no longer be 
performed during cut. Additionally, cut does not start a transaction any longer.


6.1.3 Introduce a new abstract class, `CutCopyPasteAction extends Action`, and
I modified the  existing `CutCopyAction` and `PasteAction` classes
to extend this new class instead of extending Action directly as was done before this
change. Move common implementation from `CutCopyAction` "up" into this new
class. This change allows PasteAction to know when the user has selected cut vs
copy and to perform the cut/paste (move) in a single transaction that occurs
in PasteAction (previously cut was performed in a transaction of its own). 

6.1.4 Moved the code that was deleting elements out of `CutAction.java::postRun()`
and put this into `PasteAction::run()` at the point AFTER the move has taken place. 
Rework this code as needed to account for the fact that elements are no longer being 
deleted.

6.1.5 Add an attribute to CutCopyPasteAction named ELEMENT_MOVE_SOURCE_SELECTION that 
holds the selection made by the user during cut.  

6.2 Assure that the cut CME is only enabled when it should be.  
An attempt to paste to the same location that the copy was made from is considered an 
invalid selection and shall not be allowed. Modify {Explorer & Canvas}CutAction.java::isEnabled()
to assure that cut is not enabled if the source and destination are the same. 

6.2.1. Disable paste when the selected target is the same as the source.  

6.2.1.1. `bp/ui/explorer/actions/ui/Explorer{Cut | Copy | Paste}Action.java::isEnabled()` 
implements this behavior on behalf of the org.eclipse.jface.action.Action abstract class. 
This is where BridgePoint determines if the CME should be enabled or not. Note that there 
is an analogous implementation for canvas in 
`bp.ui.graphics.actions/Canvas{Cut | Copy | Paste}Action.java::isEnabled()`  

6.2.1.2. The operation called out in the prior step is implemented to check the 
source and destination to see if the CME should be enabled or not.  

6.2.1.3. In the PasteAction case, the operations look to see if the destination allows paste for 
elements in the source being pasted to the target. It does this by calling an ooaofooa 
operation that is of the form {target model element instance}.Paste{Source Model Element Name}
. The structure of this code was such that this behavior 
was essentially duplicated in `{Explorer | Canvas}PasteAction.java`. I refactored this and 
"moved up" an operation named `clipboardContainsPastableModelElements()` into the parent class
`core/ui/PasteAction.java` to facilitate adding the check to assure that on move the
if the source and target PMCs match paste is not enabled.  

6.2.1.4 The actual code added to assure paste is disabled if the source and 
target PMCs match was added to the refactored `core/ui/PasteAction.java::isEnabled()` 
function.  

6.2.1.4.1 The change was specific to move, copy/paste still allows paste into the same PMC as it did before this change.  

6.2.2. In PasteAction.java::isEnabled() if a move is in progress do not allow more than 1 
selected destination. This is another change that is specific to move and is still allowed by copy/paste.  

6.3 Introduce a move transaction  
The file move operation happens after the memory model has been modified. To 
perform this move at the file system level a new transaction type shall be 
introduced.  

6.3.1 In Datatypes/ModelEventNotification add a new enum DELTA_MODEL_ELEMENT_MOVE. 
Note that this enum is actually used in a bitset implementation so the value 
of this new element shall be 16384 ( 0b0010000000000000). 

6.3.2 Introduce the  Java code required to use the new ModelDelta type.  

6.3.2.1  Introduce class ModelElementMovedModelDelta extends BaseModelDelta, and 
add a new interface operation, void modelElementMoved(ModelChangedEvent event, IModelDelta delta), 
to IModelChangeListener.   

6.3.2.2 Updated places as needed that have to implement this interface. Note 
that the only one that has a non-empty implementation is 
ComponentTransactionListener.java, and in it is where we shall add code that 
performs the folder move when the transaction ends.  

6.3.2.3 Introduce ComponentTransactionListener::movePMC(NonRootModel  

6.4 Modify the paste action (`core/ui/PasteAction.java`).  

It is PasteAction::Run() that drives the changes made during paste. The 
tool goes through this same location for processing both copy/paste and cut/paste.

<b>The copy/paste behavior shall not be changed during this change.</b>  

6.4.1 Pseudo code for the "new" Model Element Move implementation.  
Since it is PasteAction::run() that drives the paste change the pseudo code
below is to a large extent implemented at this location.
```
  User Makes a Selection through either Model Explorer or Canvas
  
  User Right-clicks the selection
  
  Cut is enabled only if the selection is valid
  
  User Selects Cut
  
  Selection is stored in a list<NonRootModelElement> (ELEMENTS_TO_MOVE). 
  
  Start a Move Transaction
  
  for each selected_element in ELEMENTS_TO_MOVE
    run disconnect() via reflection if it exists (note: this is for rare cases where there is more to disconnect than R8000/R8003)
    unhook the selected_element from container pkg/comp on R8000/R8003
  end for
    
  for each selected_element in ELEMENTS_TO_MOVE 
      Find the GraphicalElement (GD_GE) associated with selected_element
      Find the Model (GD_MD) associated with the GraphicalElement
      unrelate the GraphicalElement from the Model across R1  
      relate the GraphicalElement to the destiantion's Model across R1
	  update the selected_element's associated container symbol on R307 if containment has changed
  end for 
   
  for each selected_element in ELEMENTS_TO_MOVE
    update selected_element's  model root and set it to the destination model root
  end for
  
  for each selected_element in ELEMENTS_TO_MOVE
     // Connect selected_element to the destination
    run <destination instance>.Paste<selected element class type name>() operation via reflection to move the element to the destination
    
    Add ModelElementMovedModelDelta to the transaction
  end for    

  for each selected_element in ELEMENTS_TO_MOVE
    if (selected_element downgrade is needed)
      add_to_downgrade_dialog
    end if
  end for

  End Move Transaction

  show the user the elements that will be downgraded
  if (user DOES want to continue)
     run ParseAll on destination
  else
     revert transaction
  end if

```

6.5 Reuse the current tree list box that shows Model Elements affected by the 
cut/paste operation  

6.5.1 The dialog shall allow the user to cancel   

6.5.2 Text shall be added to tell the user to enabled IPRs and
check visibility.  

6.5.3 As per the SOW [[2.3](#2.3)], the dialog shall have save and print options.  

6.6 Introduce of a Disconnect operation for each element in ooaofooa that may be 
moved and that has to be disconnected by performing more work than a simple 
unrelate on R8000/R8003

6.6.1 This situation is actually unique to Package (EP_PKG). The reason is that it is also 
connected to S_SYS on R1401/1405.




7. Design Comments   
------------------   



  


5.3 User Interface  

5.3.1 The dialog allows the user to cancel.  On cancel, no action is performed
  on the underlying model data.   

5.3.2 In the type demotion dialog, consider adding text to tell the user to consider turning on IPRs or checking package visibility.  
@see 5.4  

5.3.3 As per the SOW [[2.3](#2.3)], the dialog needs to have save and print options added.  
5.3.3.1  Updated `ScrolledTextDialog.java` to take a new parameter in the
  constructor that indicates if the save and print buttons shall be used.  
5.3.3.2  Updated the existing callers of ScrolledTextDialog such that the 
  `TransactionManager.java` is the only one that uses save and print.  Other 
  users retain existing behavior and do not use save and print.  
5.3.3.3  Added code to implement button "Save...".  This button opens a modal
  dialog that allows the user to select a file to save into.  The contents of 
  the list box (the affected elements) are written to the file if the user 
  completes the dialog.  No action is taken if the user cancels the dialog.   
5.3.3.4  Added code to implement button "Print...".  This button opens a modal
  printer selection dialog.  The contents of the list box (the affected 
  elements) are sent to the printer if the user completes the dialog.  No action
  is taken if the user cancels the dialog.  


5.4 Modify all resolution operations to first search by ID  instead of name  
5.4.1 Analysis of this problem  
During paste, after the elements are put in their new destination, the source elements must be
cleaned-up from the previous location. In the existing cut/paste implementation this meant 
performing a delete.  It is during the delete that elements (Datatypes) are "downgraded". 
In S_DT.Dispose() as a datatype is disposed the OAL disconnects the datatype, reconnects to 
the default type, and then calls a bridge that is used to record the "downgrade" so that 
it may be reported to the user. An example of what this looks like for one datatype, 
Bridges, is as follows:  
```
select many brgs related by self->S_BRG[R20];
for each brg in brgs
  unrelate self from brg across R20;
  relate brg to voidDt across R20;
  Util::collectModelElementsNames(elementType:"- Bridge : ",elementName:brg.Name);
end for;
```  

`PasteAction.java::run()` is responsible for paste in both the copy/paste and cut/paste scenarios. When a cut/paste occurs, after the elements are moved to their new home they must be removed from their source home. In this scenario, where the elements were simply moved (their IDs are the same) we do NOT want to call the ooaofooa Dispose operations on the elements. However, we DO need to perfom some cleanup. The cleanup needed is:  
* Remove the elements from the source root's instance list  
* remove the files  
  * This happens in the Transaction end operation. The ComponentTransactionListener.transactionEnded() operation looks to see if there were deletions in the transaction, and if so, it calls PersistableModelComponent.deleteSelfAndChildren()  

5.4.1.1. core/ui/DeleteAction(ISelection) is generated. It deletes the model types in the given selection in a specific order (starting with S_SYS).  The archetype generates code that finds the count of a given type (example EP_PKG) in the selection and then iterates over them one at a time calling <Element instance>.Dispose() on each one.  
5.4.1.2. <Element Instance>.Dispose() is of course generated from MC-Java and of course each model element in ooaofooa has a Dispose operation. MC-Java adds some code to the bottom of each of these operations that looks like this:  
```
    if (delete()) {
			Ooaofooa.getDefaultInstance()
					.fireModelElementDeleted(new BaseModelDelta(Modeleventnotification_c.DELTA_DELETE, this));
		}
```  
5.4.1.2.1 the delete() operation is generated for each model element.  
5.4.1.2.1.1 What this does first, is to call super.delete() (NonRootModelElement.java::delete()), which assures the element is not orphaned. Note that being orphanded means the element does not exist in the root's instance list. If not orphanded, we remove the elements from this root's instance list and return true. Additionally, it is worth noting there is a special case in this situation for merge. It looks like this:  
```
      ...
			// During merge we do not convert elements, they are moved
			// as is from one side to the other.  During a merge undo we
			// can hit a case where RTOs are removed before the RGO, causing
			// the check for external references to be true.
			if(hasExternalRefs && !getModelRoot().isCompareRoot()) {
				convertToProxy();
			} else {
				delete_unchecked();
			}
			return true;
			...
```  
5.4.1.2.1.1.1 Note that delete_unchecked() is where we remove the elements from the root's instance list as described 
in 2.1.  
5.4.1.2.1.2 delete() then has code that tests to assure relationships were torn down by the element's Dispose() properly, and reports non-fatal errors if the relationships were not properly disposed.  

5.4.2 Resolution for cut/paste's problem of "downgrading" model elements and improper element "deletion" on move.  
<b>TODO: Rework these sub-items</b>  
<s>5.4.2.1  modify MC-Java's handling of the Dispose() operation and wrap the body of the OAL action in a conditional expression that check to see if a move is in progress, and do NOT call the action body if a move is in progress.  
5.4.2.2 modify the generated delete() operations to check to see if a move is in progress and if a move is in progress do not report the error about relationships not being torn down.  
5.4.2.3 Investigate the "convertToProxy()" used in NonRootModelElement.delete()   
</s>

<b>5.4.3 In the type demotion dialog, we shall add text to tell the user to assure IPRs are enabled and to check package visibility.</b>  



5.5 Fix inconsistent proxy paths [[2.4](#2.4)]  
Investigation into this problem showed that there was no problem with the way proxy paths were being written. After this investigation no additional actoin was required.  

6.1 When a model element is moved from one location to another, BridgePoint
  needs to perform work to clean up "referring" and "referred to" links to 
  the element being moved.   This work must be done in both the originating and 
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




6.4 Design for graphics reconciliation
```
Note that in the graphics move we are simply discconecting and reconnnecting graphics on R1. 
The exception is in the situation where components references are unassigned, in this 
case we must run graphics reconcilaition to remove the provision and requirements when the
element is unassigned. However, note that since unassign is beingdone, this should happen
so no reconciliation should be needed
```

??????????????????????????????????

<b>6.3.2.1 The implementation shall consider that fact that visibility checks that may result in
demotion will need to be performed on both the source and the target (RTOs and RGOs).</b>


8. User Documentation   
---------------------  
8.1 This issue changes the behavior of cut/paste making it analogous to move. 
BridgePoint documenation shall be updated accordingly.  
8.1.1 The cut operation no longer causes any change. When cut is selected, no 
change occurs until and unless paste is performed.  
8.1.2 Unlike copy/paste which creates new elements on paste, the modified cut/paste operation leaves identifers intact.  
8.1.2.1 This means that while a copied buffer may be pasted muitple times, a cut buffer can be pasted only 1 time.  

9. Acceptance Test   
------------------  

Use cases for the tests below are found in the SOW [[2.3](#2.3)]. The unit test model(s) and use case implementations shall be further described by [[2.8](#2.8)].
 
9.1 Test to assure that the move operation is atomic  

9.2 Test to assure that the move operation maximizes the probability of the  
underlying configuration-management system detecting that the move within  
the limitations of the eclipse team interface.  

9.3 Test element move for each supported model element as specified in section 4.3. 

9.4 Test to assure that when visibility permits, the connection between a 
moved model element and other model elements shall be maintained.  

9.4.1 Test to assure that when visibility does not permit the connection between 
model elements to be maintained proper defaults are used.

<b>
9.4.2 The above test shall be extended to test both RTO and RGO downgrades.
</b>

9.5 Test to assure the model-element move capability is only enabled for 
valid model element selections and is present but disabled otherwise.

9.6 Assure that proxy paths are written consistently.  
Include use cases in this issue's test model(s) that cause the proxy path 
problems described by [[2.2](#2.2)]. Run these use cases to assure these 
problem are resolved.  

9.7 Assure that the user documenation has been updated (see section 8).  

End
---
