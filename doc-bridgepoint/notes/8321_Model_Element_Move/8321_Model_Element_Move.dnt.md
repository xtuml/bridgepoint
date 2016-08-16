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
(../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md) was used 
during the SOW creation to help define the requirements for this project.  

<a id="2.3"></a>2.3 [Statement of Work for Model Element Move](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document.  This document is internal to the 
One Fact team.

<a id="2.4"></a>2.4 [Inconsistent proxy paths (Issue 8454)](https://support.onefact.net/issues/8454)  
Proxy paths are not written consistently.  

<a id="2.5"></a>2.5 [MC-Java README.TXT](https://github.com/xtuml/bridgepoint/blob/master/src/MC-Java/README.TXT)  
This document describes the MC-Java implementation.  

<a id="2.6"></a>2.6 [Issue 8455 - Remove dead code associated with proxies](https://support.onefact.net/issues/8455)  
  
<a id="2.7"></a>2.7 Documentation associated with the introduction of proxies in BridgePoint  
Proxies were introduced into BridgePoint when multi-file persistence (PLCM) was introduced. The following 
engineering documents describe this:  

<a id="2.7.1"></a>2.7.1 [Issue 845 - PLCM Technical Note](i845.tnt)  
This note captures the initial brain-storming done regarding the PLCM project.  PLCM stands for package level configuration management. Over the years since this feature was introduced the term PLCM has been dropped and this is now more commonally referred to as multi-file persistence.  

<a id="2.7.2"></a>2.7.2 [Issue 845 - PLCM Final Analysis Note](i845-PLCM_1_0.ant)  
This is the final PLCM analysis note. The PLCM project was long-lived, and there was an initial analysis note but this one was created at the time the project was split into smaller deliverables.  

<a id="2.7.3"></a>2.7.3 [Issue 845 - PLCM Design Note for milestone 2](i845-2.dnt)  
This note captures the design note of PLCM as related to proxies.  

<a id="2.8"></a>2.8 [Issue 8458 - Test Cases for Model Element Move](https://support.onefact.net/issues/8458)  

<a id="2.9"></a>2.9 Documentation associated with use of proxies for model compare and merge  
<a id="2.9.1"></a>2.9.1 [Issue 244 Design note - Fix corruption caused by class merges](244_class_merge.dnt.md)  

<a id="2.10"></a>2.10 [Issue 3532 design note - Support data type move capabilities through cut, copy, paste](../8031_Analyze_Model_Element_Move/i3532.dnt)  

<a id="2.11"></a>2.11 [Eclipse Team Interface associated with element move](http://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresAdv_hooks.htm)  
This is documentation for Eclipse Team plugin providers. It shows that file move support could be 
implemented by a provider.  

<a id="2.12"></a>2.12 [Engineering notes associted with dts0100911019](dts0100911019)  
This issue shows an example where file system changes made by BridgePoint had a undesirable effect on a specific type of revision control system. The specified system was not really the relevant issue, though it was ClearCase. The issue was more general and was the fact the ClearCase system used pessimistic locking. The issue referenced because it is an example where the BridgePoint team handled this issue by modify the file system change so the RCS team interface would handle it in a more favorable way.  

3. Background   
-------------     

See the background in [the analysis note](../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md).

This design note was reviewed and approved early in the Model Element Move project. Implementation 
uncovered problems that prevented the original design from working as described. At the heart of the problem was the original design's intention to use the existing copy/paste infrastructure in the implementation of the move behavior. Reusing the copy/paste infrastructure allowed much of the 
complexity of this project to be handled via the existing infrastructure. Prior to the work done by this issue, BridgePoint did have the ability to perform cut/paste. However, this cut/paste behavior took 
full advantage of copy/paste. In fact, the cut/paste behavior was simply copy/paste followed by delete. 
The paste in the situation resulted in new UUIDs for all elements pasted. The original design took the 
approach that the change to make that prior cut/paste behavior a move was essentially simple a matter
of preventing new UUIDs  from being created for the pasted elements. Implementation came to the conclusion that this approach would not satisfy the requirements. Specifically, the fact that reuse of the existing infrastructure would require delete to be called after the "move" was unacceptable. The delete() operation is modeled. Each meta-model element implements delete and the implementation is recursive. In the initial design it was believed that the implementation would simply modify the delete operations to prevent the recursive delete. However, this was not an appropriate solution. What is came down to, although quite a painful decision, was that "move is not copy/paste/delete" and it can not be treated as such.  

After the implementation discovery above it was determined that move should not reuse the copy/paste 
infrastructure. This led to significant design changes. Upon re-reviewing the project design it was determined during the [re-review](../../review-minutes/8321_Model_Element_Move.dnt.rvm2.md) that this design note should be updated. However, it was decided that we should do our best to leave the original approved design in place to make it clear what was changed between the original design and the modified design. To facilitate this, in the note that follows you will see <s>strikethrough</s>  where items were removed from the original design and 
<b>bold</b> where things were added.


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

4.6 Proxy paths are currently written inconsistently [[2.2](#2.2)]. This bug 
shall be resolved.

5. Analysis   
-----------   
See the [Analysis Note](../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md). 

In this analysis section we will add some additional proxy analysis. We do 
this because it is observed that there are multiple ways to perform the 
implementation for this task, and we want to assure we look at overall 
product roadmap as we perform this task to leverage the work to move us 
toward longer term product goals that may be related. Specifically, it 
is desirable to remove proxies.  

5.1 Choice of design options from the analysis note.

<s>The option to use the existing infrastructure, [2.2](#2.2) section 5.3, shall 
be used. </s>This option takes advantage of both the existing UI infrastructure and 
the infrastructure that is used to perform target selection and validation. 

5.1.1 Atomic transaction requirement

The analysis note, [2.2](#2.2), wrote that the choice to modify the implementation of 
the cut/paste operation such that IDs would not be changed would 
result in a transaction that was not atomic. This was true. However, the implementation 
proposed by this design will result in an atomic transaction. It will result 
in a move that adheres to the ACID properties of transactions. That is, 
the move implemented via cut/paste shall be atomic, consistent, isolated, 
and durable. 

5.1.2 Making the cut/paste (move) an ACID-compliant transaction in BridgePoint.

In this work we shall modify the cut/paste operation to be a single, 
long-lived transaction. It is referred to as long-lived because it survives
across multiple user operations (cut and paste). With this change, cut/paste is
analogous to move.

5.1.2.1 The move transaction shall not terminate until either:  
5.1.2.1.1 The paste is performed  
5.1.2.1.2 The transaction is terminated before paste is performed  
The move transaction shall be canceled if any other transaction is initiated
before the paste operation is completed.  

5.2 Proxy analysis with an eye to proxy removal  
Requirement 4.6 calls out a bug in proxy implementation. This design note shall describe
2 ways to resolve this problem. One way is proxy removal. This is the desired approach. 
This section provides an analysis of this proxy removal approach. The design section 
of this note describes how we will determine which approach to take.  

Section 1 of the [PLCM design note for proxies](i845-2.dnt) describes the BridgePoint 
proxy implementation.  

The BridgePoint NonRootModelElement.java class contains an operation, boolean isProxy(), 
used to determine if an in-memory instance is a proxy or not. To determine if the 
element is a proxy, it looks at the string attribute 
NonRootModelElement.java::m_contentPath and if that attribute is not null 
then the element is a proxy.  

Experience working with BridgePoint over the years has led the BridgePoint team 
to believe that the introduction of proxies when multi-file persistence (PLCM) 
was added to the tool was a mistake. Proxies were introduced with the idea 
that they would help validate the distributed files introduced during the PLCM
project. Additionally, the concept of lazy loading of model files was made 
possible by proxies and it was seen as a great benefit because users no longer
needed to wait for a model to load the first time a model element was opened.  

What the BridgePoint team believes, and has shown with tests of model load time, 
is that the amount of time to load a model, even a large model is not 
significant when compared to the architectural clutter at the persistence layer
introduced by proxies. Over time maintenance around proxy issues has been
high. Furthermore, it is observed even in the [original note that introduced 
proxies (section 3.3)](i845-PLCM_1_0.ant), that lazy-loading had limitations. 
Over time what has been observed is that lazy loading has done more harm 
than good.  

<s>[[4.6](#4.6)] is a blocker to this issue because if 
proxies remain and are not persisted consistently the move operation will have 
failures caused by the inconsistent proxies paths. The following section 
examines proxy usage with an eye to the possibility of removing proxies 
as a solution to [[4.6](#4.6)].</s> Section 6.5 describes the steps to perform 
this removal.  

5.2.1 Proxy implemenation  
As described in the [PLCM design note for proxies](i845-2.dnt), proxies are 
implemented with the followed generated operations:  
*  `createProxy(..[all parameters].., String filePath)`  
Returns an instance.  
*  `resolveInstance(..[all parameters]..)`   
Searches, by UUID for a matching instance and returns the instance if found. If 
not found, creates a new instance.  
* `convertToProxy()`  
Convert a real instance to a proxy.
* `convertToRealInstance(..[all parameters]..)`  
Convert a proxy to a real instance.  

5.2.2.1 lazy loading  
Lazy loading policy provides that data for the parent (RTO) is not loaded unless
needed. However, in many cases immediate loading of parent data  
triggered. For example, a class diagram shows the type of its
attributes and so unless there are no attributes to display, the
corresponding data type data must be loaded. 

This issue shall take the position that maintenance and problems
around lazy loading support are not worthy of the feature benefits.  

5.2.2.2 Model Compare  
Model Compare and merge provides comparison of changes on a per-file basis. 
The comparison is not graphical, but it is structural based on the meta-model 
(it is more than a simple textual compare). This functionality has been modified 
quite a bit to add features and introduce bug fixes [2.9](#2.9). This feature 
does utilize proxies. Further analysis of the use of proxies by model compare 
and merge will be required in order to remove proxies from the tool. There 
were a lot of the features/fixes in this area, and will be easiest to 
analyze these further by removing the proxy functionality first.  Therefore, no
further analysis of this is done at this time.  

6. Design   
----------------   
<s>As described in the analysis section, the copy/paste infrastructure shall be 
used in order to take advantage of it's ability to perform selection 
and target validation as well as minimize changes to the BridgePoint user 
interface. This section describes how to modify this existing
infrastructure to change the behavior of cut/paste so that it is analogous with
move.  
</s>

6.1 Modify cut/paste operation to be analogous to "move" by making it a 
single long-lived transaction. This shall adhere to the ACID properties 
of transactions.  

6.1.1 The current infrastructure uses an abstract class, 
core/ui/CopyCutAction.java extends org.eclipse.jface.action.Action, 
to define the behavior of cut/copy operations this interface shall be modified 
to allow the cut/paste operation to be a single transaction as opposed to 2 
separate transactions. In addition to this abstract class, the classes that 
extend this shall also be modified as required for this change. These are:  
* `core/ui/CopyAction.java` - This shall be modified as needed to adhere to interface 
changes, but functionality shall not be changed.
* `core/ui/CutAction.java` - <s>The deletion of selected elements will no longer be 
performed during cut. Instead, this deletion will be moved to the paste 
operation so that no change is actually made until paste occurs.  </s>

6.2 Modify the paste action (`core/ui/PasteAction.java`).  

The copy/paste behavior shall not be changed during this change.  

<s>The paste operation behavior is implemented in the generated 
file io/mdl/ImportModelStream.java (generated from `io/mdl/arc/gen_stream_import.arc`). 
The PasteAction.java class invokes this through the same BridgePoint 
interface that is used for all model element loading (`IModelImport.java`). 
`ImportModelStream` is used for model export as well as paste, therefore to introduce 
the ability to cause element load to use the source IDs instead of creating new ones
a flag will be introduced to indicate that cut/paste is being performed (as opposed 
to copy/paste). When cut/paste is indicated, the generated model element constructor 
calls will be made in a way that assures:  
6.2.1 The source element ID(s) shall be used and no new IDs shall be created  
6.2.2 The element is created in the target model root.  
6.2.3 When model root(s) are included in the selection being moved, the paste 
action shall perform the move in a way that facilitates the ability of the team interface 
to handle the action as a move as opposed to a cut/paste (which would lose RCS history). 
This is where requirement 4.2 is handled.  
6.2.3.1 The implementation shall proceed by first modifying the in-memory instances without 
regard to the persistence layer. When this task is complete, because the persistence layer is 
seperate, in theory no additional work shall be required because. The reason is, since IDs are not 
changed, if a file whose contents are identical is deleted and created the team interface should be 
able to manage this as a move [2.11](#2.11).  BridgePoint deals with 
these changes at the file system level, and it is for the provider to handle the team 
operations appropriately. BridgePoint does not directly ever communicate with the 
Eclipse team interface, with 1 exception (compare and merge - 
`bp/model/compare/contentmergeviewer/ModelContentMergeViewer.java`).  What BridgePoint 
code can, and has done to help facilitate preferred RCS behavior is to 
assure that file operations are performed in the best order, and this issue 
shall seek to do this. An example of where this was done in the past may be seen 
here [2.12](#2.12).  

6.2.4 Where deletion of elements is required (paste is performed to a different model root) 
the deletion of the source elements will occur. The deletion shall occur after the paste to
faciliate 6.2.3.  
</s>

6.2.5 An attempt to paste to the same location that the copy was made from is considered an 
invalid selection and shall not be allowed.  

6.3 Reuse the current tree list box that shows Model Elements affected by the 
cut/paste operation  

6.3.1 The dialog shall allow the user to cancel   

6.3.2 In the type demotion dialog, consider adding text to tell the user to 
consider turning on IPRs or checking package visibility.  

<b>6.3.2.1 The implementation shall consider that fact that visibility checks that may result in
demotion will need to be performed on both the source and the target (RTOs and RGOs).</b>

6.3.3 As per the SOW [[2.3](#2.3)], the dialog needs to have save and print optons added.  

6.4 Modify all resolution operations to first search by ID  instead of name  

Section 6.2 of [[2.10](../8031_Analyze_Model_Element_Move/i3532.dnt)] describes 
how resolution operations were introduced to perform this search by ID. There 
should be no further action to take on this, but if testing shows otherwise, 
this is the pattern to follow to complete this task.

6.5 Fix inconsistent proxy paths [[2.4](#2.4)]  

It is desirable to resolve this problem by simply removing proxies from 
BridgePoint as described in the analysis section. However, doing so may 
be beyond the scope of this project. The primary concern is around unexpected 
consequences involving self hosting. However, it should not be difficult to 
perform a test of proxy removal, and the steps to do so shall be outlined here 
[6.5.1]. This task of resolving the inconsistent proxy paths is intentionally 
left as the last task in the project. If the attempt to remove proxies results
in unexpected problems that time constraints do not allow us to resolve then 
these problems shall be recorded and we shall resolve this requirement by 
fixing the bug in the existing proxy code [6.5.2].

6.5.1 Remove proxies from BridgePoint  

6.5.1.1 Remove generated code used to read and write proxies  
* remove convertToProxy()
* Stop generating the operations that read/write proxies in:
  * `io.core/arc/import_functions.inc`
  * `io.core/arc/export_functions.inc`
  * `io.mdl/arc/gen_stream_import_.arc`
  * `io.mdl/arc/gen_stream_export_.arc`

6.5.1.2 Load the whole model at startup
```
// nrme is the NonRootModelElement associated with the SystemModel_c instance
PersistableModelComponent pmc = nrme.getPersistableComponent();
			pmc.loadComponentAndChildren(new NullProgressMonitor());
```  
6.5.1.3 Remove generation of convertFromProxy() and delayed problem marker code  
```java
    public void convertFromProxy() {
        if (isProxy()) {
            m_contentPath = null;
            UmlProblem.proxyResolved(this);
        }
    }
```  
6.5.1.4 Remove generation of `convertToRealInstance()`  
6.5.1.5 Remove generation of `createProxy()`  
6.5.1.6 Test and fix fallout of proxy removal on compare and merge  


6.5.2 Write proxy paths consistently  

If the proxy removal encounters problems then this path to 
resolve [[2.4](#2.4)] shall be taken. The proxy path is 
obtained by 
bp.core.util.PersistenceUtil.java::resolveRelativePath(currentPath, relativePath). 
This routine gets called by the createProxy() operation which is generated for all 
NonRootModelElements. The RSL for this is found in MC-Java/java.arc::createProxy 
(around line 743 you can find the call to resolveRelativePath()). This code will be
debugged and the problem found and fixed.  


7. Design Comments   
------------------   
5.1 Modify cut/paste operation to be analogous to "move" by making it a 
single long-lived transaction.  

5.1.1 Introduce a new abstract class, `CutCopyPasteAction extends Action`, and
I modified the  existing `CutCopyAction` and `PasteAction` classes
to extend this new class instead of extending Action directly as was done before this
change. Move common implementation from `CutCopyAction` "up" into this new
class. This change allows PasteAction to know when the user has selected cut vs
copy and to perform the cut/paste (move) in a single transaction that occurs
in PasteAction (previously cut was performed in a transaction of its own). 

5.1.2 Moved the code that was deleting elements out of `CutAction.java::postRun()`
and put this into `PasteAction::run()` at the point AFTER the move has taken place. 
Rework this code as needed to account for the fact that elements are no longer being 
deleted.

5.1.3 Add an attribute to CutCopyPasteAction named ELEMENT_MOVE_SOURCE_SELECTION that 
holds the selection made by the user during cut.  

5.2 Introduce a move transaction  
The file move operation happens after the memory model has been modified. To perform this 
move at the file system level a new transactoin type shall be introduced.  

5.2.1 In Datatypes/ModelEventNotification add a new enum DELTA_MODEL_ELEMENT_MOVE. 
Note that this enum is actually used in a bitset implementation so the value of this new element shall be 16384 ( 0b0010000000000000). 

5.2.2 Introduce the  Java code required to use the new ModelDelta type.  

5.2.2.1  Introduce class ModelElementMovedModelDelta extends BaseModelDelta, and add a new interface operation, void modelElementMoved(ModelChangedEvent event, IModelDelta delta), to IModelChangeListener.   

5.2.2.2 Updated places as needed that have to implement this interface. Note that the only one that has a non-empty implementation is ComponentTransactionListener.java, and in it is where we shall add code that performs the 
folder move when the transaction ends.  

5.2.2.3 Introduce ComponentTransactionListener::movePMC(NonRootModel  

  
5.2.5 An attempt to paste to the same location that the cut was made from is considered an 
invalid selection and shall not be allowed.  

5.2.5.1. Disable paste when the selected target is the same as the source.  
5.2.5.1.1. `bp/ui/explorer/actions/ui/Explorer{Cut | Copy | Paste}Action.java::isEnabled()` 
implements this behavior on behalf of the org.eclipse.jface.action.Action abstract class. 
This is where BridgePoint determines if the CME should be enabled or not. Note that there 
is an analogous implementation for canvas in 
`bp.ui.graphics.actions/Canvas{Cut | Copy | Paste}Action.java::isEnabled()`  
5.2.5.1.2. The operation called out in the prior step is implemented to check the source and destination to
see if the CME should be enabled or not.  
5.2.5.1.3. In the PasteAction case, the operations look to see if the destination allows paste for 
elements in the source being pasted to the target. It does this by calling an ooaofooa 
operation that is of the form {target model element instance}.Paste{Source Model Element Name}
. The structure of this code was such that this behavior 
was essentially duplicated in `{Explorer | Canvas}PasteAction.java`. I refactored this and 
"moved up" an operation named `clipboardContainsPastableModelElements()` into the parent class
`core/ui/PasteAction.java` to facilitate adding the check to assure that on move the
if the source and target PMCs match paste is not enabled.  
5.2.5.1.4 The actual code added to assure paste is disabled if the source and target PMCs match was added to the refactored
`core/ui/PasteAction.java::isEnabled()` function.  
5.2.5.1.4.1 The change was specific to move, copy/paste still allows paste into the same PMC as it did before this change.  
5.2.5.2. In PasteAction.java::isEnabled() if a move is in progress do not allow more than 1 
selected destination. This is another chage that is specific to move and is still allowed by copy/paste.  


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

6.2 Pseudo code for the "new" Model Element Move implementation  
```
- PasteAction::run() - MOVE_IS_IN_PROGRESS flow

  Start Transaction
  for each element in selection
    run disconnect() if it exists
    unhook on from parent pkg/comp on R8000/R8003
  end for
  processGraphics - move graphical model roots
  for each element in selection
    update model root
  end for
  for each element in selection
    run modeled Paste() operation to move the element to the destination
  end for    
  End Transaction
  if ( transaction successful )
    continue = displayProblemsDialog()
    if (!continue)
      // paste cancelled, transaction reverted by displayProblemsDialog()
      return
    end if
    run ParseAll on destination
    run PasteCompleted listener on elements in selection
  end if
```

6.3 Introduction of Disconnect operation for elements that have a more complex disconnnect than R8000/R8003
```
[8/12/16, 1:03:37 PM] Keith Brown: during the MEM review, we made a review comment about how we seem to recall adding a model function we call via reflection, but we didn't remember what it was for or where it was used.
[8/12/16, 1:04:28 PM] Bob Mulvey: I am very surprised but I do not see where we documented it. However, it was Disconnect
[8/12/16, 1:04:45 PM] Bob Mulvey: And you can see where we use it right now in PasteAction.java::run
[8/12/16, 1:05:39 PM] Bob Mulvey: We currently use it in the case where there is more to have to disconnect then just R8000/R8003
[8/12/16, 1:06:26 PM] Bob Mulvey: So, we look to see if the Disconnect operation exists on the element being moved and if it does we call it
```

6.4 Design for graphics reconciliation
```
Note that in the graphics move we are simply discconecting and reconnnecting graphics on R1. 
The exception is in the situation where components references are unassigned, in this 
case we must run graphics reconcilaition to remove the provision and requirements when the
element is unassigned. However, note that since unassign is beingdone, this should happen
so no reconciliation should be needed
```

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
