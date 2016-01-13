---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Design Note

1. Abstract   
-----------   
This note describes the approach we shall take to implement the Model Element Move functionality in BridgePoint.  

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
See [[2.2]](#2.2)

6. Design   
----------------   
6.1 Enhance the current infrasture to not change element IDs during move.  
6.1.2 The copy/paste infrastructre shall be used for it's ablity to perform selection and target validation. However, if this move can be done with the in-memory instances and still take advatage of this validation infrastructure it shall do so.  
6.1.3 Avoid using the current infrastructure's use of the clipboard if possible.  
6.1.4 While doing this work, consider modifying the current cut/paste operation to no longer change IDs during paste.   
6.2 Modify all resolution operations to first search by ID  instead of name  
6.3 Introduce a new CME named Move...  
6.3.1 Introduce the Move... CME in Model Explorer  
6.3.2 Introduce the Move... CME in the canvas  
6.3.3 Assure the Move... CME is only enabled when there is a valid selection  
6.3.4 The BridgePoint Move... CME shall be similiar to the Java > Refactor >Move... wizard, but where the Java Move CME contains a project tree to select the destination, the BridgePoint > Move... dialog shall contain a Model explorer tree.  
6.3.5 The BridgePoint Move... shall be implemented as a wizard.  
6.3.5 Move... Wizard Page 1  
6.3.5.1 Select Destination  
6.3.5.1.1 This item shall contain a Model Explorer tree and the user shall be allowed to select a single location  
6.3.5.1.2 Only valid destinations shall be enabled, invalid destinations shall be disabled  
6.3.6 Move... Wizard Page 2  
6.3.6.1 Referring Model Elements affected by the move operation  
6.3.6.2 A tree view showing the Model Elements that will be affected by the move with a description of how they will be affected  
6.3.6.3 An option to save this list shall be presented  
6.3.6.4 An option to print this list shall be presented  
6.4 The user may cancel the Move... at any time before selecting Finish, and no action shall be taken.  
6.5 Only when and if the user selects the Move... wizard's Finsh button shall the atomic move take place.  

7. Design Comments   
------------------   
none  

7. Acceptance Test   
------------------   
7.1 Use the [[test generation utility]](#2.2) to generate tests for all source and target permutations. Note that documentation for this utility is found in the header of this perl script.  The goal of these generated tests is to assure all requirements are satisfied for each generated test. This assure that requirements are satisfied for each test permutation.  
7.1.1 Create a test matrix that defines all possible "degrees of freedom" for source and target selection.   
7.1.2 Add this matrix to bp.core.test plugin as a new test suite.  
7.1.3 Modify bp.core.test/generate.xml to generate the test suite from the test matrix using the test generation utility.  
7.1.4 Implement the pieces of the suite that the test generation utilitie's results require adddtional work for.  
7.1.4.1 Each generated tests shall assure that element IDs are not modified during move.  
7.1.4.2 Each generated tests shall assure that move is performed as an atomic operation.  
7.1.4.2.1 After the move a single "undo" restore the model to the state it was in prior to the move operation  
7.1.4.2.2 If the move operararion is canceled no changes shall be made.  
7.1.4.3 Each generated test shall assure that its result modifies the minimum number of files necessary.  
7.1.5 Run the suite and assure it passes  

End
---
