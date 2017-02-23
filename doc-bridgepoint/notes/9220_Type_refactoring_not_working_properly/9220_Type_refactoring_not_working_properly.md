---

This work is licensed under the Creative Commons CC0 License

---

# Type refactoring not working properly
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the changes to reload associated xtuml files when a masl file has been updated.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9220](https://support.onefact.net/issues/9220) Type refactoring not working properly  

3. Background
-------------
The addition of xtext in the tool has provided refactoring support in .masl files.  This has been setup such that when an underlying model element is changed refactoring is automatically triggered.  The xtext framework handles modifying the .masl resource.  Our current implementation tries to listen for this change and update the snippet editors (those working against action_semantics variables).  This is currently not working leaving the tool unsynchronized.   

4. Requirements
---------------
4.1 Refactoring shall trigger updating the underlying action_semantics attribute value to support keeping snippet editors synchronized.   

5. Work Required
----------------
5.1 On masl content update reload associated model elements   

We cannot use the component resource listener approach as the resource events are sent during the transaction that updates the model.  For many reasons we cannot listen to resource changes during a transaction.  Instead in MaslDocumentProvider.doSaveDocument() we now explicitly reload the associated persistable model component.  This will allow the in-memory action semantics to get updated.  In addition to this change the code is also changed to update any open masl editors such that the reloaded content is used for display.   
   
6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Synchronization test   

_- Rename a data type that is referenced in a function   
_- Open the associated .masl file with a text editor   
_R The .masl file is updated to include the new name   
_- Open the associated masl editor for the function   
_R The masl data uses the new data type name   
_- Rename the data type again   
_R The masl data uses the new data type name in the open Masl Editor   

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 9220_Type_refactoring_not_working_properly   

<pre>

doc-bridgepoint/src/notes/9220_Type_refactoring_not_working_properly/9220_Type_refactoring_not_working_properly.md

src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentTransactionListener.java

src/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/document/MaslDocumentProvider.java

</pre>

End
---

