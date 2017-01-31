---

This work is licensed under the Creative Commons CC0 License

---

# Add markable_name column to application.mark
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work performed to add a new field to the application.mark
file persisted data.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9035](https://support.onefact.net/issues/9035) Headline issue    

3. Background
-------------
During the development of the marking editor we made the decision to not persist
the model element type name in the ```application.mark``` file.  This was a bad
decision because that field helps us resolve all ambiguities on potential path
name collisions during the xtUML to MASL pragma output processing.  This issue 
adds the field to the data.  

4. Requirements
---------------
4.1  Persist the model element type name on each line in ```application.mark```  
4.1.1  The m2x process shall populate the field when is creates the file  
4.1.2  The marking editor shall populate the field when the user edits marking
  data and the data is saved.   
  
4.2  x2m shall read and use the model element type name field   

5. Work Required
----------------
5.1  m2x  
     
5.2  Marking Editor  
5.2.1  Marking editor must be extended to read the new column from the ```application.mark```
  file.  The new column is added after the feature, thus it is column three.  
5.2.2  The existing design of the marking editor requires a model element type
  selection as the first step and constrains the list of available instances based
  on the user selection.  From there, the user selects the application model 
  element and are presented with the corresponding features and their values.  
5.2.3  When the features of a given model instance are edited, the model element
  type shall now be included in the internal data structure.  When the user "OK"s 
  the marking editor dialog, the data (now including the type) is persisted 
  to ```application.mark```.  
  
5.3  x2m     


6. Implementation Comments
--------------------------
None.   

7. Unit Test
------------
7.1  TODO - Update the MASL Mark test to validate the new field usage  

8. User Documentation
---------------------
TODO - update the documentation for the marking editor  

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 9035_mark_element_name

<pre>

< Put the file list here >

</pre>

End
---

