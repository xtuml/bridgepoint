---

This work is licensed under the Creative Commons CC0 License

---

# Property Parameter In Interface Reference Restricting
### xtUML Project Analysis Note

1. Abstract
-----------
A user can change parameter properties on an interface reference, and the changes will be propagated back to the interface definition.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8822](https://support.onefact.net/issues/8822) 
This is a link to this issue in the issue tracking system.  
<a id="2.2"></a>2.2  [Statement of Work]

3. Background
-------------
Property Parameter instances are the same in both the Interface definition and the usage in interface references (provided signals and provided operations). Currently BridgePoint provides no restrictions on the editing operation capability in the interface references, which means that changes made there are reflected back into the interface definition.  

4. Requirements
---------------

4.1 Restrict rename  
* Ensure rename of a Property Parameter can only be done in the Interface definition.  

4.2 Restrict delete  
* Ensure deletion of a Property Parameter can only be done in the Interface definition.  

4.3 Restrict type set  
* Ensure type setting of a Property Parameter can only be done in the Interface definition.  

4.4 Restrict reorder  
* Ensure move up or down of a Property Parameter can only be done in the Interface definition.


5. Analysis
-----------
Unlike interface references, the OOAofOOA has no representation for a Property Parameter provided for use in a reference. This means that CME restrictions used for messages can't be copied for use in restricting the CME usage of Property Parameters. The restriction must therefore be determined through the positioning in the Model Explorer tree structure. If a selected parameter exists under an interface reference in the tree, then the CME for the parameter must be restricted.

6. Work Required
----------------
6.1 Determine how to traverse the Model Explorer tree structure.  
* Precedence exists in the UI test utilities. This work can be copied for use in the CME restriction.  

6.2 Insert the determination and restricting into CME framework in OOAofOOA for Property Parameter (C_PP).  
* Action Filter and Can* functions are already enabled for C_PP.  

7. Acceptance Test
------------------
7.1 Rename  
* Verify rename isn't available in interface reference for parameters.  
* Verify rename is still available in interface definition for parameters.

7.2 Delete  
* Verify delete isn't available in interface reference for parameters.  
* Verify delete is still available in interface definition for parameters.

7.3 Set Type  
* Verify set type isn't available in interface reference for parameters.  
* Verify set type is still available in interface definition for parameters.

7.4 Reorder  
* Verify move up or down isn't available in interface reference for parameters.  
* Verify move up or down is still available in interface definition for parameters.


End
---

