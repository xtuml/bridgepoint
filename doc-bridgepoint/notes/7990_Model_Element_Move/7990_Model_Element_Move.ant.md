---

This work is licensed under the Creative Commons CC0 License

---

# Model element move
### xtUML Project Analysis Note

1. Abstract   
-----------   
This note picksanalyzes the different approaches to providing a model element move.   
It considers the current approach and offers other potential approaches.

2. Document References     
----------------------   
<a id="2.1"></a>2.1 [BridgePoint DEI #7990](https://support.onefact.net/redmine/issues/7990) This is a link to this issue in the issue tracking system.  
<a id="2.2"></a>2.2 [BridgePoint DEI #8031](https://support.onefact.net/redmine/issues/8031) Analyze Model Element Move - This issue was raised in the issue tracking system to answer high-level questions about this Model Element Move task in order to allow us to define requirements for the project. The [document produced by this work allowed](https://github.com/rmulvey/bridgepoint/blob/7990_Model_Element_Move/doc-bridgepoint/notes/8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md) was used during the SOW creation to help define the requirements for this project.  
<a id="2.3"></a>2.3 [Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  This is a link to this issue's Statement of Work document.
<a id="2.4"></a>2.4 [UnitTestGenerator.pl](https://github.com/xtuml/bridgepoint/blob/master/src/org.xtuml.bp.test/UnitTestGenerator.pl)  This is a link to the BridgePoint utility that is used to generate test suites from a defined test matrix.

3. Background   
-------------     

See the background in [[2.2]](#2.2) as well as the background in the SOW [[2.3]](#2.3).

4. Requirements   
---------------   
The requirements are defined in this issue's SOW [[2.3]](#2.3). For convience, the SOW requirements are being copied, in order here. This analysis shall create some additional requirements based on the selected implementation. These additional requirements shall be placed after the last requirement copied here from the SOW.  
4.1 Atomic Move Operation  
Selected model elements are moved in a single operation that can be undone with a single undo operation.  
4.2 Configuration-management Friendly  
Within the limitations of the Eclipse Team interface, move operations are performed in a way that maximizes the probability of the underlying configuration-management system detecting that a move operation (as opposed to a sequence of delete and create operations) occurred.  
4.3 Supported Model Element Types  
The model-element move capability is supported for the following model element types (see Limitations [[2.3]](#2.3)):  
* Data type definition
* Component definition 
* Interface definition
* Class definition 
* Component reference 
* Imported class 
* Package (when package contains supported model elements)

4.4 Connections Maintained Whenever Possible  
Whenever visibility permits, the connection between a moved model element and other model elements is maintained.  For example, a data type can be moved without affecting the attributes and parameters typed by it so long as the data type remains visible to those elements typed by it.  
4.5 Enabled Only When Valid Elements Selected  
The model-element move capability is enabled only when all model elements within the current selection are supported model element types for the move capability.  


5. Analysis   
-----------   
The initial investigation into this task called out 3 approaches they were:  
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
6.1 Introduce a new CME named Move...
6.1.1 Introduce the Move... CME in Model Explorer
6.1.2 Introduce the Move... CME in the canvas
6.1.3 Assure the Move... CME is only enabled when there is a valid selection  
6.1.4 The BridgePoint Move... CME shall be similiar to the Java > Refactor >Move... wizard, but where the Java Move CME contains a project tree to select the destiation, the BridgePoint > Move... doalog shall contain a Model explorer tree.
6.1.5 The BridgePoint Move... shall be implemented as a wizard.
6.1.5 Move... Wizard Page 1
6.1.5.1 Select Destination
6.1.5.1.1 This item shall contain a Model Explorer tree and the user shall be allowed to select a single location
6.1.5.1.2 Only valid destination shall be enabled, invalid destinations shall be disabled
6.1.6 Move... Wizard Page 2
6.1.6.1 Referring Model Elements
6.1.6.2 A tree view showing the Model Elements that will be affected by the move with a description of how they will be affected
6.2 The user may cancel the Move... at any time before selecting Finish, and no action shall be taken.
6.3 Only when and if the user selects the Move... wizard's Finsh button shall the atomic move take place.

6.4 Avoid using the current infrastructre's use of the clipboard if possible. 
6.4.1 The copy/paste infrastructre shall be used for it's ablity to perform selection and target validation. However, if this move can be done with the in-memory instances and still take advatage of this validation infrastructure it shall do so.

6. Enhance the current infrasture to not change element IDs during move.
7. 

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
7.1 Use the [[test generation utility]](#2.2) to generate tests for all source and target permutations. Note that documentation for this utility is found in the header of this perl script.  
7.1.1 Create a test matrix that defines all possible "degrees of freedom" for source and target selection.  
7.1.2 Add this matrix to bp.core.test plugin as a new test suite  
7.1.2 Modify the bp.core.test build to generate the test suite from the test matrix  
7.1.3 Implement the pieces of the suite that the test generation utilitie's results require adddtional work for  
7.1.3.1 Each generated tests shall assure that element IDs are not modified during move  
7.1.3.2 Each generated tests shall assure that move is performed as an atomic operation  
7.1.3.2.1 After the move a single "undo" restore the model to the state it was in prior to the move operation  
7.1.3.2.2 If the move operararion is canceled no changes shall be made.  
7.1.4 Run the suite and assure it passes  

End
---
