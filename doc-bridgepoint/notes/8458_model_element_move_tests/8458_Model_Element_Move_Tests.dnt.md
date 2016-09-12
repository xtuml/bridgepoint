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

6.2  


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
7.3.1. ComponentMovePass1 in Source package FailureCasesComponentPackage is 
moved to Destination package.  
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
 
End
---
