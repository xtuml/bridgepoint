---

This work is licensed under the Creative Commons CC0 License

---

# Implement Undo for the move (cut/paste) operation
### xtUML Project Design Note


1. Abstract
-----------
This note shall describe the approach that shall be taken to introduce undo for the move operation.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI 8755](https://support.onefact.net/issues/8755) This issue.  
<a id="2.2"></a>2.2 [8321 Model Element Move Design Note](../8321_Model_Element_Move.dnt.md)  
<a id="2.3"></a>2.3 [8321 Model Element Move Implementation Note](../8321_Model_Element_Move.int.md)  
<a id="2.4"></a>2.4 [MEM Manual Test Procedure](https://support.onefact.net/issues/8837)  

3. Background
-------------
In the initial version of Model Element Move that was introduced [2.3](#2.3) the 
ability to undo the move transaction was not implemented. This issue shall implement 
this functionality. 

This document will refer to a move operation. A move operation in BridgePoint is 
performed by using the cut/paste menu operations.

4. Requirements
---------------
4.1 Selected model elements are moved in a single operation that can be undone with a single undo operation.  

5. Analysis
-----------
5.1 Explanation of BridgePoint's undo/redo implementation.  
BridgePoint contains an undo/redo transaction stack (LIFO ordering). BridgePoint 
transactions are added to the undo transaction stack when they complete. When the 
undo option is selected, the element at the top (the last transaction performed) is
removed and the information in that transaction is used to roll-back the transaction.  
This works because the transaction contains a model delta for each copy of every 
model element that the transaction modified. This model delta holds a copy of the model
element BEFORE any modifications were made to it. The transaction therefore simply replaces 
the current model element with the original. Once done, it then performs any file-system cleanup
necessary that may have been caused by the transaction that was undone. An example is for the 
case where a model element is renamed. In this case, if the model element renamed is a Persistable
Model Component (PMC) root then after replacing the model element the stale folder (and file)
must be removed from disk.

5.2 Why wasn't UNOD implemented for Model Element Move as part of the initial work    
Model Element Move is implement in a single transaction in BridgePoint. In 
BridgePoint there are 2 ways to create a transaction. 
5.2.1 Single Transaction  
This is what MEM uses.  
5.2.2 Transaction Group  
This is what MEM needs in order to be able to undo the transaction.    


6. Design
---------
6.1 Modify PasteAction.java::PasteRunnable::Run  
6.1.1 Make the current transaction a group transaction  
6.2 Modify TranactionManager.java    
6.2.1 In addTransactionToStack() remove the code that is currently clearing the 
undo/redo stack when a move transaction is encountered.  
6.3 Modify Transaction.java::revert  
Add code to look for the Model_Element_Move delta and assure any folder/file 
created is removed and any folder/file that was removed is created.

7. Design Comments
------------------
none  

8. User Documentation
---------------------
none  

9. Unit Test
------------
9.1 Run the manual test suite for this issue [2.4](https://support.onefact.net/issues/8837).  


End
---

