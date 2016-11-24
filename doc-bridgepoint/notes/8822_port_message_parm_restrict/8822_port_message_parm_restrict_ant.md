---

This work is licensed under the Creative Commons CC0 License

---

# Interface Reference Restricting
### xtUML Project Analysis Note

1. Abstract
-----------
A user can change parameter properties on an interface reference, and the changes will be propagated back to the interface definition. A user is also able to delete signals and operations from an interface reference, but this doesn't get propagated back to the interface definition.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8822](https://support.onefact.net/issues/8822) 
This is a link to this issue in the issue tracking system.  
<a id="2.2"></a>2.2  [Statement of Work]

3. Background
-------------
Property Parameter instances are the same in both the Interface definition and the usage in interface references (provided signals and provided operations). Currently BridgePoint provides no restrictions on the editing operation capability for Property Parameters in the interface references, which means that changes made there are reflected back into the interface definition. For some reason, the deletion operation was left available on signals and operations in the interface references.  

4. Requirements
---------------

4.1 Property Parameter rename shall only be allowed in the Interface definition.  

4.2 Deletion of a Property Parameter shall only be allowed in the Interface definition.  

4.3 Deletion of a Signal shall only be allowed in the Interface definition.  

4.4 Deletion of an Operation shall only be allowed in the Interface definition.  

4.5 Type setting of a Property Parameter shall only be allowed in the Interface definition.  

4.6 Move up or down of a Property Parameter shall only be allowed in the Interface definition.  

5. Analysis
-----------
Unlike interface references, the OOAofOOA has no representation for a Property Parameter provided for use in a reference. This means that CME restrictions used for messages can't be copied for use in restricting the CME usage of Property Parameters. The restriction must therefore be determined through the positioning in the Model Explorer tree structure. If a selected parameter exists under an interface reference in the tree, then the CME for the parameter must be restricted.  
For signals and operations, the same test used to restrict rename can be used.  

6. Work Required
----------------
6.1 Determine how to traverse the Model Explorer tree structure.  
* Precedence exists in the UI test utilities. This work can be copied for use in the CME restriction.  

6.2 Insert the determination and restricting into CME framework in OOAofOOA for Property Parameter (C_PP).  
* Action Filter and Can* functions are already enabled for C_PP.  

6.3 Add delete restriction for zignals and operations per rename restriction.  

7. Acceptance Test
------------------
7.1 Rename  
* Verify rename isn't available in interface reference for parameters.  
* Verify rename is still available in interface definition for parameters.

7.2 Delete  
* Verify delete isn't available in interface reference for parameters.  
* Verify delete is still available in interface definition for parameters.  
* Verify delete isn't available in interface reference for provisions.  
* Verify delete isn't available in interface reference for requirements. 
* Verify delete isn't available in interface reference for signals.  
* Verify delete is still available in interface definition for signals.  
* Verify delete isn't available in interface reference for operations.  
* Verify delete is still available in interface definition for operations.  

7.3 Set Type  
* Verify set type isn't available in interface reference for parameters.  
* Verify set type is still available in interface definition for parameters.

7.4 Reorder  
* Verify move up or down isn't available in interface reference for parameters.  
* Verify move up or down is still available in interface definition for parameters.


End
---

