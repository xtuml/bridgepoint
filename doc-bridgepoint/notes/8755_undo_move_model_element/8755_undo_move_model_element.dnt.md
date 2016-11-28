---

This work is licensed under the Creative Commons CC0 License

---

# Undo model element move
### xtUML Project Design Note

1. Abstract
-----------
Undo support was not carried along with the initial model element move work
[2.2].  This note describes the changes required to re-enable undo support.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8755](https://support.onefact.net/issues/8755) Undo is not available for a move (cut/paste) operation  
<a id="2.2"></a>2.2 [BridgePoint DEI #8321](https://support.onefact.net/issues/8321) Model Element Move   
<a id="2.3"></a>2.3 [BridgePoint DEI #8837](https://support.onefact.net/issues/8837) Model Element Move (cut/paste) testing  
<a id="2.4"></a>2.4 [BridgePoint DEI #8853](https://support.onefact.net/issues/8853) Undo does not consider original list order   
<a id="2.5"></a>2.5 [BridgePoint DEI #8854](https://support.onefact.net/issues/8854) Support move to location with existing element
<a id="2.6"></a>2.5 [BridgePoint Analysis Note](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20090903/technical/Undo_Redo_Revert/undo_redo_revert-i473.ant) Provide Undo, Redo and Revert functionality    
<a id="2.7"></a>2.5 [BridgePoint Design Note](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20090903/technical/Architecture/i865.dnt) Batch Event Delivery Mechanism    

3. Background
-------------

Model element move was redesigned [2.2] such that a move is represented by a
copy/paste/delete action.  It use to be copy/delete/paste which did not
accurately describe a move.  It required two separate transactions which lead
to two undo calls for a proper restoration.  The new move support has made it
into the main baseline, with undo disabled.

4. Requirements
---------------

4.1 Supported elements shall be moved in a single operation that can be undone
with a single operation.   
4.2 Tests listed in [2.3], which use supported elements shall pass

5. Analysis
-----------

5.1 Enabling undo

Code shall be removed, added with [2.2], to enable the undo/redo stacks to again
store executed transactions.

5.2 Handling revert of move

Transaction revert shall have support added for the new move delta.  Undoing a
move transaction shall result in a proper reverse move delta being created.  The
same code that handles the move (no undo involved) shall support the undo move
just the same.

6. Design
---------   

6.1 Enabling undo

TransactionManager.addTransactionToStack() has the check for a move transaction
removed, allowing the stacks to store the current transaction.

6.2 Handling revert of move

In order to achieve a matching delta set we need to create a move delta as part
of the revert.  The move delta create is a reverse of the original move delta.
This delta needs to carry the original source of the element (it already has the
destination).  With this information Transaction.revert() is modified to create
the undo delta using the original source as the new destination and the
destination as the new source.  ModelRoot.fireModelElementMoved() is called on
this new delta to capture as part of the running transaction.

PasteAction.processPasteForMove() is modified to create the initial move delta
with the required source element data.

6.3 NPE during undo

Initial testing showed an NPE during undo.  This is in
PackageableElement::canReferToDataType().  The NPE occurs due to the fact that
the element is out of scope and therefore the dt we are looking for is not
found.  Code that searches for datatypes under components incorrectly expects
the first search under packages to return a datatype.  This is changed to use
the passed in datatype value as was used in the search under packages.

6.4 Issues found during design

6.4.1 Persistence ordering difference

Since undo was introduced, it has always simply handled the deltas in a reverse
manner.  For example an undo of a relationship change flips a relate to an
unrelate and visa-versa.  With move we now detach GD_GE instances from the
source diagram and associate them with the destination diagram.  When doing this
we lose the original location in the RGO list (in the RTO).  When undoing the
move this unrelate is changed to a relate and associated with the original
source diagram. When related the GD_GE being moved is added at the end of the
RGO list, causing that element to be persisted last out of the other GD_GEs.
This results in a correct logical model, but a difference in the xtuml file.

Issue [2.4] was raised to capture this.

6.4.2 Handling move to location with element that has matching name and type

It was noticed that the current support for move does not consider the case
where a similar element already exists.  Cut/Paste would rename the moved
element  But we probably should consider a dialog giving options to replace,
rename, abort.

Issue [2.5] was raised to capture this.

7. Design Comments
------------------

8. User Documentation
---------------------

9. Unit Test
------------

9.1 Run relevant tests described in [2.3]   
9.1.1 Result test passes   
9.1.2 Undo the move from the test   
9.1.3 Result the element is moved back   
9.1.4 Result the file structure matches the model structure    
9.1.5 Double click on the element moved after undo   
9.1.6 Result the appropriate editor is opened   

End
--- 
