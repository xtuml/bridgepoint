---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Design Note

1. Abstract   
-----------   
This note describes the approach we shall take to test the Model Element 
Move functionality in BridgePoint.  

2. Document References     
----------------------   
<a id="2.1"></a>2.1 [BridgePoint DEI #8458](https://support.onefact.net/issues/8458) 
This is a link to this issue in the issue tracking system.  

<a id="2.2"></a>2.2 [Model Element Move Design Note #8321](https://github.com/xtuml/bridgepoint/blob/8321_Model_Element_Move/8321_Model_Element_Move.dnt.md)   

<a id="2.3"></a>2.3 [Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document, which contains the use cases.  

<a id="2.4"></a>2.4 [Model Element Move Analysis Note #8031](https://github.com/xtuml/bridgepoint/blob/8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md)
Analysis referenced in design note.  

<a id="2.5"></a>2.5 [Model Element Move User Test Cases #8726](https://support.onefact.net/issues/8726)   


3. Background   
-------------     

See the background in [the analysis note](#2.4).

4. Requirements   
---------------   
The requirements are defined in this issue's [Statement of Work](#2.3).

5. Analysis   
-----------   
See the [Analysis Note](#2.4). 


6. Design   
----------------   

6.1 See the [Design Note](#2.1).  



7. Test Cases  
------------------  
Every test case should be followed by an "Undo". The undo operation should 
leave all model files unchanged from before the move. The check for changes
should use a text-based difference tool. A good practice would be to test on a
clean checkout of the repository and let 'git diff' do the text compare.  
A finding of differences should be noted in the test results.  
Undo should be a single step. If multiple undo operations are required to 
recind the move, it should be noted as an error in the test case results.

7.1 Use Case 4.1  
7.1.1. VisibleDataType in Source package is moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.1.2. VisibleDataType in Source package is moved to 
DestinationComponentPackage.  
>* Options menu is shown for displaying all items typed to VisibleDataType, that
are now going to be set to the default type.  
>* Perform undo.  

7.2 Use Case 4.2  
7.2.1. VisibleInterface in Source package is moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.2.2. VisibleInterface in Source package is moved to 
DestinationComponentPackage.  
>* Option menu is shown for displaying all ports that referenced 
VisibleInterface.  
>* Perform undo.  

7.3 Use Case 4.3  
7.3.1. ComponentMovePass1 in Source package is moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.3.2. FailureMoveComponent1 in Source package is moved to Destination package.  
>* Option menu is shown for displaying FailureMoveClass.attribute1 set to 
default type and FailureMoveComponent1Port InvisibleInterface no longer visible.
A parse of FailureMoveClass.operation1 will result in failure due to EE no
longer visible.  
>* Perform undo.  

7.4 Use Case 4.4  
7.4.1. ComponentMovePass2 in Source package is moved to DestinationComponent in 
Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.4.2. FailureMoveComponent1 in Source package is moved to DestinationComponent 
in Destination package.  
>* Option menu is shown for displaying FailureMoveClass.attribute1 set to 
default type and FailureMoveComponent1Port InvisibleInterface no longer visible.
A parse of FailureMoveClass.operation1 will result in failure due to EE no
longer visible.  
>* Perform undo.  

7.5 Use Case 4.5  
7.5.1. ComponentInComponentMovePass in ComponentMovePass2 in Source package is 
moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.5.2. FailureMoveComponentInComponent in FailureMoveComponent1 in Source 
package is moved to Destination package.  
>* Option menu is shown for displaying FailureMoveComponent1Port 
InvisibleInterface no longer visible.  
>* Perform undo.  

7.6 Use Case 4.6  
7.6.1. ComponentInComponentMovePass in ComponentMovePass2 in Source package is 
moved to DestinationComponent in Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.6.2. FailureMoveComponentInComponent in FailureMoveComponent1 in Source 
package is moved to DestinationComponent in Destination package.  
>* Option menu is shown for displaying FailureMoveComponent1Port 
InvisibleInterface no longer visible.  
>* Perform undo.  

7.7 Use Case 4.7  
7.7.1. ComponentMovePass3 in Source package is moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.7.2. FailureMoveComponent2 in Source package is moved to Destination package.  
>* Option menu is shown for displaying that contained component reference has
lost visibility to the referred component.  
>* Perform undo.  

7.8 Use Case 4.8  
7.8.1. SingleClassPass in Source package is moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.8.2. FailureMoveClass in Source package is moved to Destination package.
>* Options menu is shown for displaying that FailureMoveClass.failureAttribute
has been set to the default data type.  
>* Perform undo.  

7.9 Use Case 4.9  
7.9.1. Class2RelatedTo3Pass, Class3RelatedTo2Pass, and R1 in Source package is 
moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.10 Use Case 4.10  
7.10.1. ClusterClass4Fail, ClusterClass5Fail, and R2 in Source package is moved 
to Destination package.  
>* Warning is displayed stating that ClusterClass6Fail and R3 must be included 
in the move. Move isn't permitted.  
>* Perform undo.  

7.11 Use Case 4.11  
7.11.1. ComponentMovePass4 in Source package is moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.11.2. FailureMoveComponent3 in Source package is moved to Destination package.  
>* Options menu is shown for displaying information that both components 
referenced and the interface formalized to the port connection are no longer 
visible.  
>* Perform undo.  

7.12 Use Case 4.12  
7.12.1. ImportedClassPass1, ImportedClassPass2, R4, R5, and the imported class
related across R5 in Source package is moved to Destination package.  
>* Result is successful move with no option menu shown for list of lost 
visibility items.  
>* Perform undo.  

7.12.2. ImportFailClass1, ImportFailClass2, R1, R2, and the imported class 
related across R3 in Source package is moved to Destination package.  
>* Options menu is shown for displaying information of the removal of the 
imported class.  
>* Perform undo.   
 
7.13 Pessimistic Locking Test - test checkout of RGOs with a Pessimistic locking RCS.
>* 1. Move a RTO that will cause one or more RGOs to be marked dirty
>* 2. Result is the user should see a dialog warning them that shows them the files that will be checked out. The user should have the chance to cancel at this point.

7.14 Moving a Package
>* 1.    Create a xtUML project
>* 2.    Create a package named P1 at top level
>* 3.    Create a package named P1-1 in P1
>* 4.    Create a package named P2 at top level
>* 5.    Cut P1-1
>* 6.    Paste into P2
>* 7.    Open CME on P1-1 (in P2), Cut is enabled.  Close the CME.
>* 8.    Create a package named P3 at top level
>* 9.    Cut P2
>* 10.   Paste into P3
>* 11.   P2 graphic at top level is gone, P2 graphic is added to P3
>* 12.   Restart BridgePoint
>* 13.   P2 is not available at top level, P2 is under P3   
  
7.15 Move class into component
>* 1.       Create a xtUML project
>* 2.       Create a package named P1 at top level
>* 3.       Create a class named CL1 in P1
>* 4.       Create a component named COMP1 in P1
>* 5.       Create a package named COMP1-P1 in COMP1
>* 6.       Cut CL1
>* 7.       Paste into COMP1-P1  

7.16  Move class in association into component
>* 1.    Create a xtUML project
>* 2.    Create a package named P1 at top level
>* 3.    Create a component named COMP1 in P1
>* 4.    Create a package named COMP1-P1 in COMP1
>* 5.    Create a package named COMP1-P2 in COMP1
>* 6.    Create a class named CL1 in P1
>* 7.    Create a class named CL2 in P1
>* 8.    Create an attribute named Id in CL2
>* 9.    Add Id as identifier
>* 10.   Create an association R1 between CL1 and CL2
>* 11.   Formalize association R1 using Id
>* 12.   Cut CL2, should that be ok?
>* 13.   Paste in COMP1-P1
>* 14.   Graphic in COMP1-P1 is incorrect and probably the consistency of the model, Error!
>* 15.    
>* 16.   Alternate the Cut of CL2 with a Cut of CL1 should that be ok?
>* 17.   Paste in COMP1-P1
>* 18.   Graphic in COMP1-P1 is incorrect and probably the consistency of the model, Error!      

7.17  Data Type at top level within project
>* 1.       Create a xtUML project Test1
>* 2.       Create a package named P1 at top level
>* 3.       Create a package named P2 at top level
>* 4.       Create a package named P3 at top level
>* 5.       Create a data type My_DT in P1
>* 6.       Create a component COMP1 in P2
>* 7.       Create a package COMP1-P1 in COMP1
>* 8.       Create a class CL1 in COMP1-P1
>* 9.       Create a attribute Attr in CL1
>* 10.   Set the attribute type to be My_DT
>* 11.   Cut My_DT from P1
>* 12.   Paste My_DT into P3
>* 13.   Check the attribute type of Attr in CL1 by opening the set type dialog of the attribute, path Test::P3 will be shown and data type Test::P3 will not be selectable in the dialog (Correct)
>* 14.   Restart BridgePoint
>* 15.   Check the attribute type of Attr in CL1 by opening the set type dialog of the attribute, path will be empty and data type Test::P3 will be selectable in the dialog (Fail). Now it looks like that the data type is incorrect.
>* 16.   Look into the p3.xtuml and cl1.xtuml files and find the instances of S_DT for My_DT and for O_ATTR of Attr, find out that the correct data type is assigned to the attribute
>* 17.   The model is correct but the editor does not show the correct information. Our tests will be very time consuming looking into the core xtuml file for finding the behavior.

7.18 Data type inside component
>* 1.       Same as 7.17 but define the data type inside COMP1
>* 2.       The same behavior as in 7.17

7.19 Data type at top level across project
>* 1.       Same as 7.17 but also create another project with a package at top level. Paste the data type into the package in the other project
>* 2.       The same behavior as in 7.17

End
---
