---

This work is licensed under the Creative Commons CC0 License

---

# Model element move
### xtUML Project Analysis Note

1. Abstract   
-----------   
This note analyzes the different approaches to providing a model element move.   
It considers the current approach and offers other potential approaches.

2. Document References     
----------------------   
<a id="2.1"></a>2.1 [BridgePoint DEI #7990](https://support.onefact.net/redmine/issues/7990) - Model Element Move    
<a id="2.2"></a>2.2 [BridgePoint DEI #8031](https://support.onefact.net/redmine/issues/8031) - Analyze Model Element Move - This issue was raised to answer high-level question to allow us to define requirements for the project. The document produced by this work allowed the SOW to be completed and review with the customer. The SOW defines the
requirements for this project.  
<a id="2.3"></a>2.3 https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20070830/notes/i2803.dnt - Initial copy/cut/paste support   
<a id="2.4"></a>2.4 https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20080606/i3532.dnt - Support data type move capabilities through cut, copy, paste   
<a id="2.5"></a>2.5 https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit - Statement of work     

3. Background   
-------------     

See [[2.5]](#2.5)

4. Requirements   
---------------   
The requirements are defined in this issues SOW [[2.5]](#2.5). 


5. Analysis   
-----------   
5.1 Supporting move as an atomic action  

One approach to supporting move as an atomic action is to introduce a context   
menu item for move.  This action shall use the existing infrastructure in a   
similar manner to the existing cut and paste approach.  It shall capture the   
changes in a single transaction.   

This approach would address the undo issue.   

In this case we shall modify the export code to only update the IDs when   
performing a copy.  A flag that shall be set during cut shall indicate the cut
operation and prevent updating of the IDs.  Additionally we would need to    
adjust the existing resolution operations to search by ID first, then search by  
name as it does currently.   

5.2 Using eclipse file infrastructure   

Another approach which may address configuration management issues is to move   
folders or files directly.  This can be done with the eclipse API but would   
require quite a bit more work.   

This approach would require a move operation as 5.1 does.  The operation though   
would simply locate the file or folder of the element and move it.  This would   
then require additional file change listeners or require updating the existing   
ones that we have.  The changes would require us to hear the change then fix the   
element data in memory and persist.   

5.3 Existing infrastructure  

Another approach is to remain simply with cut and paste.  In this case the tool   
shall be updated to prevent updating IDs in the same manner as described above    
in 5.1.  It shall also update the resolution methods as described in 5.1.   

In this case the operation would be a move but would not be atomic.   

6. Work Required   
----------------   
6.1 Supporting move as an atomic action   
6.1.1 New move operation using existing infrastructure   
6.1.1.2 Introduce a new menu item   
6.1.1.3 Create a dialog that allows destination selection    
6.1.1.3.1 Allow the dialog to filter locations by type   
6.1.1.3.2 Have the menu item call an operation that performs a cut and paste in   
          a single transaction    
6.1.1.3.3 Modify export and paste code to only modify IDs on copy   
6.1.1.3.4 Modify all resolution operations to first search by ID   
6.2 Using eclipse file infrastructure   
6.2.1 Create new move operation which locates the element's file or folder and   
      call into the eclipse file API to move.    
6.2.2 Create or adjust the file listener to update model data and persist on   
      a change event.    
6.3 Existing infrastructure   
6.3.1 Perform step 6.1.1.3.3   
6.3.2 Perform step 6.1.1.3.4   

7. Acceptance Test   
------------------   
7.1 Supporting move as an atomic action   

Requirements supported:   

 - 4.1   
 - 4.2   
 - 4.3   
 - 4.4   
 - 4.5   
 - 4.6   
 - 4.7   
 - 4.8   
 
- Perform a move operation   
- A dialog is available for destination selection   
- Only elements that are valid are shown      
- Upon move all referring elements are connected with their referred to elements   
  within scope   
- Upon move all referred to elements are reconnected with their referring    
  elements within scope   
- No IDs have changed
- Undoing the move resets the model in the original state   

7.2 Using eclipse file infrastructure   

Requirements supported:   

 - 4.1   
 - 4.2   
 - 4.3   
 - 4.4   
 - 4.5   
 - 4.7   
 - 4.8   
 
- Repeat steps from 7.1   

7.3 Existing infrastructure   

Requirements supported:   
   
 - 4.3   
 - 4.4   
 - 4.5   
 - 4.8   

- Cut a model element    
- Paste the model element   
- Upon move all referring elements are connected with their referred to elements   
  within scope   
- Upon move all referred to elements are reconnected with their referring    
  elements within scope   
- No IDs have changed   
- Undoing the move resets the model in the original state   

End
---
