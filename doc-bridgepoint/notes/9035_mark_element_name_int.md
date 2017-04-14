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
<a id="2.2"></a>2.2 [BridgePoint DEI #8985](https://support.onefact.net/issues/8985) MASL Marking Test       

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
  type shall now be included in the update to the internal data structure.  When 
  the user "OK"s the marking editor dialog, the data (now including the element type) 
  is persisted to ```application.mark```.  
5.2.4  The element type is now used to index into the internal data.  To find the exact
  mark we are looking for, we first get all rows with the desired path.  Inside this
  data set we then locate the mark using a combination of the feature name and 
  element type.  This allows us to uniquely find the exact mark we are looking for.   
    
5.3  x2m     


6. Implementation Comments
--------------------------
None.   

7. Unit Test
------------
7.1  Update the MASL Mark test [[2.2]](#2.2) to validate the new field usage in the Marking Editor   
7.1.1  Add a new class to the model under test named "PathDupe"    
7.1.2  Add a new attribute "dupe" to PathDupe     
7.1.3  Add a new operation "dupe" to PathDupe  
7.1.4  Open feature.mark  
7.1.5  Add new features "Attribute, dupetest" and "Operation, dupetest"  
7.1.6  Start the marking editor  
7.1.7  Locate the new attribute in PathDupe  
7.1.8  Set the value "att" for feature dupetest  
7.1.9  Locate the new operation in PathDupe  
7.1.10  Set the value "opp" for feature dupetest  
7.1.11  Locate the new attribute in PathDupe again  
7.1.12  Set the value "at" for feature dupetest  
7.1.13  Locate the new operation in PathDupe again  
7.1.14  Set the value "op" for feature dupetest  
7.1.15  Click OK on the Marking Editor  
7.1.16  Open application.mark  
7.1.16.1  Verify that there are correct marks for both the attribute and operation in PathDupe  
7.1.17  Open the Marking Editor again  
7.1.18  Locate the new attribute in PathDupe  
7.1.19  Verify the value is "at" for feature dupetest  
7.1.20  Locate the new operation in PathDupe  
7.1.21  Verify the value is "op" for feature dupetest  
7.1.22  Set the value to empty (remove "op") for feature dupetest  
7.1.23  Click OK on the Marking Editor  
7.1.24  Open application.mark  
7.1.24.1  Verify that there is only a correct marks for the attribute in PathDupe.  The operation mark is gone.  


8. User Documentation
---------------------
8.1  Update the Marking Editor document in Reference > User Interface for the 
  addition of the new column.     

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 9035_mark_element_name  
Fork/Repository: cortlandstarrett/mc   
Branch: 9038_pragma_lost  

<pre>
 .../notes/9035_mark_element_name_int.md            | 113 +++++++++++++++++++++
 .../UserInterface/MarkingEditor/MarkingEditor.html |  56 +++++-----
 .../UserInterface/MarkingEditor/MarkingEditor.md   |  56 +++++-----
 .../src/org/xtuml/bp/ui/marking/MarkingData.java   |  79 +++++++++++---
 .../xtuml/bp/ui/marking/MarkingEditorDialog.java   |  15 ++-
 .../uml.bp.ui.marking/ooaofmarking/Mark/Mark.xtuml |   8 +--
 
 xtuml/mc/bin/m2x
 xtuml/mc/bin/x2m

</pre>

End
---

