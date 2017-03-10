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
<a id="2.2"></a>2.2 [MASL rename refactoring with Xtext](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8261_masl_refactor/8261_masl_refactor_dnt.md) Document describing masl editor flow   
<a id="2.3"></a>2.3 [BridgePoint DEI #9174]( https://support.onefact.net/issues/9174) Refactoring is not reflected in an open masl editor  

3. Background
-------------
The addition of xtext in the tool has provided refactoring support in .masl files.  This has been setup such that when an underlying model element is changed refactoring is automatically triggered.  The xtext framework handles modifying the .masl resource.  Our current implementation tries to listen for this change and update the snippet editors (those working against action_semantics variables).  This is currently not working leaving the tool unsynchronized.   

4. Requirements
---------------
4.1 Refactoring shall trigger updating the underlying action_semantics attribute value to support keeping snippet editors synchronized.   

5. Work Required
----------------
5.1 On masl content update reloaded associated model elements   

Currently the tool is using the component resource listener to respond to resource change events from the rename refactoring.   This is not working as these events are occuring during a transaction.  The code that starts the refactoring is surrounded with a block that guarantees enablement of the resource listener during the refactoring.  To reload the editor input after the corresponding xtuml resource is reloaded a model listener is added to the ModelSnippetEditor class.  This listener simply points the open editor to the newly loaded model element.      
   
6. Implementation Comments
--------------------------
6.1 This change also resolves [2.3 - Refactoring is not reflected in an open masl editor](2.3)

6.2 Do not update contents if the underlying resource is not being modified.  

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

