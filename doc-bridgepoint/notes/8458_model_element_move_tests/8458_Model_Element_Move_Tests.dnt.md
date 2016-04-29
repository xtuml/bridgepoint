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
Setup: *NOTE: Names are for test case illustration only!* xtUML Project with 2 Packages, 
A & B. Each Package has a Component, AD1 & BD1. Package A is the source for move 
operations, Package B is the destination for move operations. Visibility rules for 
BridgePoint dictate that elements in Package A are visible in Package B and vice versa, 
but elements in AD1 aren't visible in Package B and vice versa (BD1 to A). 

7.1 Use Case 4.1  
1. User Datatype in A, ADT1, of core type is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
2. User Datatype in A, ADT1, of type of peer user datatype in A, ADT2, is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
3. User Datatype in AD1, AD1DT1, of peer user datatype in AD1, AD1DT2, is moved to B.  
>* Result is AD1DT1 is changed to type integer, and option menu is shown for 
displaying all items in AD1 that were set to type AD1DT1.  
4. User Datatype in AD1, AD1DT2, of core type is moved to BD1.  
>* Option menu is shown for displaying all items in AD1 that were set to type AD1DT2.  

7.2 Use Case 4.2  
1. Interface in A, AI1, is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
2. Interface in A, AI1, is moved to BD1.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
3. Interface in AD1, AD1P1I1, is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
4. Interface in AD1, AD1P1I1, is moved to BD1.   
>* Option menu is shown for displaying all ports in AD1 that referenced AD1P1I1.  

7.3 Use Case 4.3  
1. Component in A, AD1, is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
2. Component in A, AD1, is moved to package in B, BP1.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
3. Component in AP1, AP1D1, is moved to B.  
>* Option menu is shown for displaying all items in AP1, that were referenced by AP1D1.  

7.4 Use Case 4.4  
1. Component in A, AD2, is moved to component in A, AD1.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
2. Component in A, AD2, is moved to component in BP1, BP1D1.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
3. Component in AP1, AP1D1, is moved to component in A, AD1.  
>* Option menu is shown for displaying all items in AP1, that were referenced by AP1D1.  

7.5 Use Case 4.5  
1. Component in A in component AD1, AD1D1, moved to A.  
>* Option menu is shown for displaying all items in AD1 that were referenced by AD1D1.  
2. Component in AP1 in component AP1D1, AP1D1D1, moved to A.  
>* Option menu is shown for displaying all items in AP1D1 that were referenced by AP1D1D1 
and all items in AP1 that were referenced by AP1D1D1.  

7.6 Use Case 4.6  
1. Component in A in component AD1, AD1D1, is moved to component in A, AD2.  
>* Option menu is shown for displaying all items in AD1 that were referenced by AD1D1.  
2. Component in AP1 in component AP1D1, AP1D1D1, is moved to component in A, AD2.  
>* Option menu is shown for displaying all items in AP1D1 that were referenced by AP1D1D1 
and all items in AP1 that were referenced by AP1D1D1.  

7.7 Use Case 4.7  
1. Component in A, AD3, containing component referencing AD2, is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
2. Component in AP1, AP1D2, containing component referencing AP1D1, is moved to B.  
>* Option menu is shown for displaying all items that were referenced by AP1D2 
and the missing reference to AP1D1.  

7.8 Use Case 4.8  
1. Class with no associations in A, AC1, is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
2. Class with no associations and one attribute typed as AP1DT1 in AP1, AP1C1, is moved
 to BP1.  
>* Attribute typed as AP1DT1 is now typed as integer. Options menu is shown for displaying 
the attribute type change.  
3. Imported, from A class AC1, class with no associations in AP1 is moved to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
4. Imported, from AP1 class AP1C1, class with no associations in AP1P1 is moved to BP1.  
>* Options menu is shown for displaying the move resulted in removal of the imported class.  

7.9 Use Case 4.9  
1. Class in A, AC2, and class in A, AC3, and association R1 from AC2 to AC are all moved 
to B.  
>* Result is successful move with no option menu shown for list of lost visibility 
items.  
2. Class in AP1, AP1C2 with attribute typed ADT1, and class in AP1, AP1C3 with attribute 
typed AP1DT1, related by R1 from AP1C2 to AP1C3 are all moved to BP1.  
>* Attribute typed AP1DT1 is changed to integer. Options menu is shown for displaying 
AP1C3 attribute type change.  

7.10 Use Case 4.10  
1. Class in A, AC4, and class in A, AC5, and association R2 from AC4 to AC5 are all moved 
to B without including class in A, AC6, which is related to AC5 across R3.  
>* Warning is displayed stating that AC6 and R3 must be included in the move. Move isn't 
permitted.  

7.11 Use Case 4.11  
1. 
 
End
---