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
[1] [BridgePoint DEI #8031](https://support.onefact.net/redmine/issues/8031) - Analyze Model Element Move    
[2] [Design note for initial copy/cut/paste support](i2803.dnt)   
[3] [Design note for Supporting data type move capabilities through cut, copy, paste] (i3532.dnt)    
[4] https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit - Statement of work   


3. Background   
-------------     

3.1 Analysis   

The original work to provide cut copy and paste [3] was done such that all   
actions used as much as the same code as possible.  The idea at the time was   
that a cut was simply a copy with the addition of a delete.  Later a move   
operation was required.  At the time the easiest approach was to use the   
existing infrastructure while adding referring element proxies to the copied   
data.      

This approach does not provide an atomic operation for a move.  This leads to   
requiring a user to undo a paste as well as the cut to return the model from a   
move.   

Another issue with this approach is that when a cut is performed the pasted   
result has element IDs recreated.  This is due to sharing code between copy and   
cut and is not a true move.   

Proxies are complete replicas of the original element.  We use them to allow   
resolution during load of referring elements.  They are also used to access   
data during compare and merge.  Compare and merge currently has availability to   
a single file.  We use the proxies to compare data as that is all we get.  An   
example of this would be a change in typing for an element.  To compare we must   
use the proxy data.  Proxies are maintained at all times and represent the exact   
model element data at the last save.  In the case of referring proxy data we   
only use this during the cut and paste functionality.   

The tools file structure completely matches the model structure.   

BridgePoint was designed to search for an element by name during resolution.      

The following lists the current approach of copy cut and paste:   

- copy (text version of the model is added to the OS clipboard)   
- cut (text version of the model is added to the OS clipboard, data is   
  deleted in the model)
- paste (data is inspected and connected if possible)   

Paste recreates data currently.  If an element exists with the same name a new    
name is created.  This behavior occurs in both cut and copy.    

3.2 Questions answered   

Q. Do we always treat them as "hints" and not trust them completely?   
A. We do not always treat them as hints, we rely on them.  This involves keep    
   them up to date on modification of a referred to element.  We rely on them   
   for lazy loading as well as compare and merge.   

Q. Are proxies always a precise architectural representation of the referential attribute?   
A. Proxies match exactly with the referred to element with one exception, they   
   include a file path.   

Q. Does our file/container architecture match our model hierarchy?   
A. Yes.  We use the persistence infrastructure which stores the Persistable   
   Model Components in a tree.  This tree matches the same structure as the   
   file system.   

Q. When do we use Name to resolve a (scoped) search?   
A. Name is always used.      
   
Q. When do we use ID to resolve a search?    
A. We do not.      

Q. What is the general strategy for connecting pasted elements (in copy/cut and paste)?   
A. Upon paste we search the model for any in-scope elements that match by name   
   or use the default in the case of not finding an element.  This may result   
   in unformalized data, like a component reference.   
   
Q. I wonder if the simplest arrangement would be to change cut/paste to keep the IDs (and fix any bugs)?   
A. This is mentioned in section 5.3, and would be the easiest approach.   

4. Requirements   
---------------   
4.1 Element move shall be supported as an atomic operation.  
4.2 A single undo shall replace changes from an element move.    
4.3 Moving a referred to element shall result in reconnection of any referring   
    elements as long as visible.     
4.4 Moving any referring elements shall result in reconnection to the referred   
    to element if visible.  
4.5 The elements to be supported shall be:   
    - For referred to elements:    
      - Datatype   
      - Interface   
      - Component   
      - Executable Property   
      - Interface Reference   
      - Port   
    - For referring elements all shall be supported       
4.6 The move operation shall only be enabled when a valid target is selected.   
4.7 The move operation shall adhere to all eclipse team interface rules.   
4.8 The work shall be done in a configuration management friendly system manner,   
    but must not violate the eclipse team interface rules.   

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
