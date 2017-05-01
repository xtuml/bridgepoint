---

This work is licensed under the Creative Commons CC0 License

---

# 8643 Combined Referentials
### xtUML Project Analysis Note


1. Abstract
-----------
In the present implementation, combining referentials can leave behind an orphaned base attribute.

2. Document References
----------------------  
<a id="2.1"></a>2.1 [BridgePoint DEI #8643](https://support.onefact.net/redmine/issues/8643)  

3. Background
-------------
Original bug report [[2.1]](#2.1) had the user attempting to combine two referential attributes that were part if the identifier and having one of the attributes left as an base attribute on the diagram.  

The behavior was found to be replicated by also combining a non-identifying referential attribute with an identifying referential attribute, but only if the non-identifying referential attribute was first selected and set as the initiator of the combine operation. If the identifying attribute is the initiator, then the combine works as desired.  

A subsequent split operation would leave the orphaned attribute in place while creating a new one from the split. i.e., split two combined attributes that produced an orphan, and you'll get three separate attributes.  

It was also observed that if the combined attributes were part of an identifier, then the "split from" choice would be left as a non-identifying attribute.  

4. Requirements
---------------

4.1 Combine two non-identifying referential attributes.  
4.1.1 Two attributes become one with both references separated by commas.
* Example: id {R1,R2}

4.2 Combine one non-identifying referential attribute with an identifying referential attribute.  
4.2.1 Two attributes become one with both references separated by commas and marked as an identifier.  
* Example: id {I,R1,R2}  

4.3 Combine two identifying referential attributes.  
4.3.1 Two attributes become one with both references separated by commas and marked as an identifier.  
* Example: id {I,R1,R2}  

4.4 Split a non-identifying combined attribute.  
4.4.1 Two non-identifying referential attributes are produced, and the combined attribute is removed.  
* Example: id {R1,R2} becomes id {R1} and id {R2}  

4.5 Split an identifying combined attribute.  
4.5.1 Two identifying referential attributes are produced, and the combined attribute is removed.  
* Example: id {I,R1,R2} becomes id {I,R1} and id {I,R2}  

5. Analysis
-----------
Existing implementation needs to be modified to accommodate the requirements. Existing implementation doesn't properly combine identifying attributes, and splits identifying attributes as one identifying and one non-identifying.  

If there was a desire to retain knowledge of how the attribute was configured prior to combination, then the OOAofOOA should be modified to add class representation for a combined attribute.  


6. Work Required
----------------

6.1 Modify the O_RATTR.combine_refs method to not convert an identifying referential attribute participating in the combine to a base attribute.  

6.2 Modify the O_RATTR.split_refs method to make both referential attributes, split from an identifying combined attribute, into identifying attributes per the same identifier as the combined attribute.   


7. Acceptance Test
------------------
7.1 Combine two non-identifying referential attributes.  
7.1.1 Two attributes become one with both references separated by commas.
* Example: id {R1,R2}

7.2 Combine one non-identifying referential attribute with an identifying referential attribute.  
7.2.1 Two attributes become one with both references separated by commas and marked as an identifier.  
* Example: id {I,R1,R2}  

7.3 Combine two identifying referential attributes.  
7.3.1 Two attributes become one with both references separated by commas and marked as an identifier.  
* Example: id {I,R1,R2}  

7.4 Split a non-identifying combined attribute.  
7.4.1 Two non-identifying referential attributes are produced, and the combined attribute is removed.  
* Example: id {R1,R2} becomes id {R1} and id {R2}  

7.5 Split an identifying combined attribute.  
7.5.1 Two identifying referential attributes are produced, and the combined attribute is removed.  
* Example: id {I,R1,R2} becomes id {I,R1} and id {I,R2}  

End
---

