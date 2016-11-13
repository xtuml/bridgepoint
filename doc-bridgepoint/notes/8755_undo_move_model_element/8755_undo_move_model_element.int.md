---

This work is licensed under the Creative Commons CC0 License

---

# Undo model element move
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the changes required to implement [1].

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8755](https://support.onefact.net/issues/8755) Undo is not available for a move (cut/paste) operation  
<a id="2.2"></a>2.2 [BridgePoint Design Note](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8755_undo_move_model_element/8755_undo_move_model_element.dnt.md) Design note    

3. Background
-------------
See [2.2].

4. Requirements
---------------
See [2.2].   

5. Work Required
----------------
See [2.2].   

6. Implementation Comments
--------------------------

6.1 Resource changes causing disposal of elements during move   

When renaming a source element to live in the destination we are seeing   
responses to resource changes from git.  These responses trigger reloads of the   
model element while we are in the middle of moving the data.  To prevent this
code was added to disable the component resource listener, for any notifications
during the rename.

7. Unit Test
------------
See [2.2].   

8. User Documentation
--------------------- 

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint/   
Branch: 8755_undo_model_element_move_2   

<pre>

doc-bridgepoint/notes/8755_undo_move_model_element/
    8755_undo_move_model_element.dnt.md
doc-bridgepoint/notes/8755_undo_move_model_element/
    8755_undo_move_model_element.int.md
doc-bridgepoint/review-minutes/8755_undo_move_model_element/
    8755_undo_move_dnt.rvm.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Packageable Element/
    Packageable Element/Packageable Element.xtuml
org.xtuml.bp.core/src/org/xtuml/bp/core/common/
    ModelElementMovedModelDelta.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/Transaction.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/TransactionManager.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/PasteAction.java

</pre>

End
---

