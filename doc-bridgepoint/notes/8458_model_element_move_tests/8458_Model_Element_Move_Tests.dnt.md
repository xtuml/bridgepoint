---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move Testing
### xtUML Project Design Note

1. Abstract   
-----------   
This note describes the approach we shall take to test the Model Element 
Move functionality in BridgePoint.  

2. Document References     
----------------------   
<a id="2.1"></a>2.1 [BridgePoint DEI #8458](https://support.onefact.net/issues/8458) 
This is a link to this issue in the issue tracking system.  

<a id="2.2"></a>2.2 [Model Element Move Design Note #8321](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8321_Model_Element_Move/8321_Model_Element_Move.dnt.md)   

<a id="2.3"></a>2.3 [Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document, which contains the use cases.  

<a id="2.4"></a>2.4 [Model Element Move Analysis Note #8031](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md)
Analysis referenced in design note.  

<a id="2.5"></a>2.5 [Model Element Move User Test Cases #8726](https://support.onefact.net/issues/8726)    

<a id="
"></a>2.6 [Model Element Move Test Model For Downgrade Testing (movetest)](https://github.com/xtuml/models/tree/master/movetest)  

<a id="2.7"></a>2.7 [Model Element Move Test Model For Misc Test Cases (ModelElementMoveTests2)](https://github.com/xtuml/models/tree/master/ModelElementMoveTest2)   

<a id="2.8"></a>2.8 [Main Model Element Move Test Model (ModelElementMoveTests1)](https://github.com/xtuml/models/tree/master/test/ModelElementMoveTests1)  

3. Background   
-------------     

See the background in [the analysis note](#2.4).

4. Requirements   
---------------   
The requirements are defined in this issue's [Statement of Work](#2.3).

5. Analysis   
-----------   
See the [Analysis Note](#2.4).  

5.1 Out-of-scope (downgradable) elements:  
1. Reference to a user defined type  
2. Interface reference  
3. Component reference  
4. Class refernce due to relationship  
5. Class reference held by imported class  
6. External Entity reference  
7. Parse of action language containing downgraded references.  

5.2 Places where a downgrade (scope change) occurs:  
1. Downgrade in source  
2. Downgrade in destination  


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

Components with interfaces attached must be selected from the canvas and selection
must include both component and interface(s).

7.0 Selection of components with interfaces. (Uses test model [2.8](#2.8))  
7.0.1. Select ComponentMovePass1 in Source package in ME.
  * Cut is not enabled.  

7.0.2. Select ComponentMovePass1 and attached interface (either drag box or ctrl-click select)
  * Cut is enabled.  

7.1 Use Case 4.1  (Uses test model [2.8](#2.8))  
7.1.1. VisibleDataType in Source package is moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.1.2. VisibleDataType in Source package is moved to 
DestinationComponentPackage.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred: 
      * ComponentMovePass1.ComponentMovePassClass.attribute1 to default
      * FailureCasesComponent.FailureMoveClass.systemViewAttribute to default
  * Perform undo.  

7.2 Use Case 4.2  (Uses test model [2.8](#2.8))  
7.2.1. VisibleInterface in Source package is moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.2.2. VisibleInterface in Source package is moved to 
DestinationComponentPackage.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * ComponentMovePass1.ComponentMovePassPort.VisibleInterface to unassigned
      * VisibleComponent1.VisibleComponent1Port.VisibleInterface to unassigned
      * VisibleComponent2.VisibleComponent2Port.VisibleInterface to unassigned
  * Perform undo.  

7.3 Use Case 4.3  (Uses test model [2.8](#2.8))  
7.3.1. ComponentMovePass1 in Source package is moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.3.2. FailureMoveComponent1 in Source package is moved to Destination package.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * FailureCasesComponent.FailureMoveComponent1.FailureMoveComponent1Package.FailureMoveClass.attribute1 to default
      * FailureCasesComponent.FailureMoveComponent1.FailureMoveComponentInComponent.FailureMoveComponentInComponentPort.InvisibleInterface to unassigned
      * FailureCasesComponent.FailureMoveComponent1.FailureMoveComponent1Port.InvisibleInterface to unassigned
      * PARSE of FailureCasesComponent.FailureMoveComponent1.FailureMoveComponent1Package.FailureMoveClass.operation1 on line 2 due to out-of-scope bridge reference.
  * Perform undo.  

7.4 Use Case 4.4  (Uses test model [2.8](#2.8))  
7.4.1. ComponentMovePass2 in Source package is moved to DestinationComponentPackage in 
Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.4.2. FailureMoveComponent1 in Source package is moved to DestinationComponentPackage 
in Destination package.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * Same as 7.3.2
  * Perform undo.  

7.5 Use Case 4.5  (Uses test model [2.8](#2.8))  
7.5.1. ComponentInComponentMovePass in ComponentMovePass2 in Source package is 
moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.5.2. FailureMoveComponentInComponent in FailureMoveComponent1 in Source 
package is moved to Destination package.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * FailureCasesComponent.FailureMoveComponent1.FailureMoveComponentInComponent.FailureMoveComponentInComponentPort.InvisibleInterface to unassigned
  * Perform undo.  

7.6 Use Case 4.6  (Uses test model [2.8](#2.8))  
7.6.1. ComponentInComponentMovePass in ComponentMovePass2 in Source package is 
moved to DestinationComponentPackage in Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.6.2. FailureMoveComponentInComponent in FailureMoveComponent1 in Source 
package is moved to DestinationComponentPackage in Destination package.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * Same as 7.5.2
  * Perform undo.  

7.7 Use Case 4.7  (Uses test model [2.8](#2.8))  
7.7.1. ComponentMovePass3 in Source package is moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.7.2. FailureMoveComponent2 in Source package is moved to Destination package.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * FailureCasesComponent.FailureMoveComponent2.ModelElementMoveTests::Source::FailureCasesComponent::FailureCasesComponentPackage::InvisibleComponent1 to unassigned
  * Perform undo.  

7.8 Use Case 4.8  (Uses test model [2.8](#2.8))  
7.8.1. SingleClassPass in Source package is moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.8.2. FailureMoveClass in Source package is moved to Destination package.
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * FailureCasesComponent.FailureMoveClass.failureAttribute to default
  * Perform undo.  

7.9 Use Case 4.9  (Uses test model [2.8](#2.8))  
7.9.1. Class2RelatedTo3Pass, Class3RelatedTo2Pass, and R1 in Source package is 
moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.10 Use Case 4.10  (Uses test model [2.8](#2.8))  
7.10.1. ClusterClass4Fail, ClusterClass5Fail, and R2 in Source package is moved 
to Destination package.  
  * Cut is not enabled.  

7.11 Use Case 4.11  (Uses test model [2.8](#2.8))  
7.11.1. ComponentMovePass4 in Source package is moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.11.2. FailureMoveComponent3 in Source package is moved to Destination package.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * FailureCasesComponent.FailureMoveComponent3.ModelElementMoveTests::Source::FailureCasesComponent::FailureCasesComponentPackage::InvisibleComponent1 to unassigned
      * FailureCasesComponent.FailureMoveComponent3.ModelElementMoveTests::Source::FailureCasesComponent::FailureCasesComponentPackage::InvisibleComponent2 to unassigned
  * Perform undo.  

7.12 Use Case 4.12  (Uses test model [2.8](#2.8))  
7.12.1. ImportedClassPass1, ImportedClassPass2, R4, R5, and the imported class
related across R5 in Source package is moved to Destination package.  
  * Result is successful move with no option menu shown for list of lost 
visibility items.  
  * Perform undo.  

7.12.2. ImportFailClass1, ImportFailClass2, R1, R2, and the imported class 
related across R3 in Source package is moved to Destination package.  
  * Options menu is shown for displaying downgrades.  
    * Check the following items to ensure a downgrade occurred:  
      * Imported class reference to ModelElementMoveTests::Source::FailureCasesComponent::FailureCasesComponentPackage::InvisibleImports::InvisibleImportClass to unassigned
  * Perform undo.   
 
7.13 Pessimistic Locking Test - Assure RGOs are not checked-out/dirtied by the move operation. (Uses test model [2.6](#2.6))  
  * 1. In Model Explorer
    * select BOTH mybasetype and mysubtype from package name impl
    * cut
  * 2. Paste into dest2  
  * 3. Results 
    * 3.1 no dialog appears
    * 3.2 the only packages marked dirty are impl and dest2
  * 4. select package named Functions in Model Explorer, RC, BridgePoint Utilities > Load and Persist
  * 5. Result - Functions is marked dirty
  * 6. Compare package Functions with the lastest from head
  * 7. Result - You should see that the proxy was modified to point to the moved datatype


7.14 Moving a Package (use 2.7)
  * 1.    Cut pkg1_1
  * 2.    Paste into pkg2
  * 3.    Open CME on pkg1_1 (in pkg2), Cut is enabled.  Close the CME.
  * 4.    Cut pkg2
  * 5.   Paste into pkg3
  * 6.   Result - pkg2 graphic at top level is gone, pkg2 graphic is added to pkg3
  * 7.   Restart BridgePoint
  * 8.   pkg2 is not available at top level, pkg2 is under pkg3   
  

7.15 Move class into component (create a model from scratch)  
  * 1.    Create a xtUML project
  * 2.    Create a package named P1 at top level
  * 3.    Create a class named CL1 in P1
  * 4.    Create a component named COMP1 in P1
  * 5.    Create a package named COMP1-P1 in COMP1
  * 6.    Cut CL1
  * 7.    Paste into COMP1-P1  

7.16  Move class in association into component (create a model from scratch)
  * 1.    Create a xtUML project
  * 2.    Create a package named P1 at top level
  * 3.    Create a component named COMP1 in P1
  * 4.    Create a package named COMP1-P1 in COMP1
  * 5.    Create a package named COMP1-P2 in COMP1
  * 6.    Create a class named CL1 in P1
  * 7.    Create a class named CL2 in P1
  * 8.    Create an attribute named Id in CL2
  * 9.    Add Id as identifier
  * 10.   Create an association R1 between CL1 and CL2
  * 11.   Formalize association R1 using Id
  * 12.   Select CL2
  * 13.   Cut is disabled  
  * 14.   Select CL1  
  * 15.   Cut is disabled  

7.17  Data Type at top level within project (Uses test model [2.7](#2.7))
  * 1.   Cut My_DT from P1
  * 2.   Paste My_DT into P3
  * 3.   Check the attribute type of Attr in CL1 by opening the set type dialog of the attribute, the path 7_17_Data_Type_at_top_level::P3 will be shown and data type 7_17_Data_Type_at_top_level::P3 will not be availble as a choice in the dialog.
  * 4.   Restart BridgePoint
  * 5.   Check the attribute type of Attr in CL1 verify the same result as step 3 above.

7.18 Data type inside component (Uses test model [2.7](#2.7))
  * 1. Create a new UDT named My_DT2 inside COMP1-P1
  * 2. Set Attr in CL1 to be of type My_DT2
  * 3. Restart BridgePoint
  * 4. Follow the steps in 7.17 (but moving My_DT2), see the same behavior as in 7.17

7.19 Data type at top level across project (Uses test model [2.7](#2.7))
  * 1. Create a new xtUML project named TestProj2
  * 2. Create a new package PKG2 inside TestProj2
  * 3. Enable IPRs in TestProj2 and in ModelElementMoveTest2
  * 4. Follow the steps in 7.17 (but moving My_DT into TestProj2/PKG2 instead of P3), see the same behavior as in 7.17

7.20 Check graphics moved with element (Uses test model [2.7](#2.7))
  * 1. Start BridgePoint with no canvases open
  * 2. Cut and paste a top-level package into another top-level pacakge from Model Explorer with no Canvas open.
  * 3. Open the System-level package
  * 4. The graphic on the system-level diagram is has been removed
  * 5. The Graphic on the target is fine when the target package is opened  
   
7.21 Move UDT causing downgrade in source and destination (Uses test model [2.6](#2.6))
  * 1. Expand movetest/p1/impl
  * 2. Cut mysubtype
  * 3. Expand movetest/p2/impl2
  * 4. Paste mysubtype to impl2
  * 5. Downgrade dialog indicates downgrades on elements under p1 (the src) that have lost visibility to mysubtype
  * 6. Downgrade dialog indicates downgrade on mysubtype inside p2 (the dest) since it lost visibility to mybasetype  

7.22 Move SDT causing downgrade in source and destination (Uses test model [2.6](#2.6))
  * 1. Expand movetest/p1/impl
  * 2. Cut testStruct
  * 3. Expand movetest/p2/impl2
  * 4. Paste testStruct to impl2
  * 5. Downgrade dialog indicates downgrades on elements under p1 (the src) that have lost visibility to testStruct
  * 6. Downgrade dialog indicates downgrade on testStruct.member_st && testStruct.member_bt inside p2 (the dest) since they lost visibility to mysubtype and mybasetype respectively  

7.23 Move class causing downgrade in source and destination (Uses test model [2.6](#2.6))
  * 1. Expand movetest/p1/impl
  * 2. Cut clz
  * 3. Expand movetest/p2/impl2
  * 4. Paste clz to impl2
  * 5. Downgrade dialog indicates downgrades on p1/impl/refs/Unassigned Imported Class (the src) that has lost visibility to clz
  * 6. Downgrade dialog indicates downgrade on attributes and operations inside clz (the dest) since they lost visibility to types under p1/impl  

7.24 Move component causing downgrade in source and destination (Uses test model [2.6](#2.6))
  * 1. Expand movetest/p1/impl
  * 2. Cut comp
  * 3. Expand movetest/p2/impl2
  * 4. Paste comp to impl2
  * 5. Downgrade dialog indicates downgrades on p1/impl/refs/Unassigned Imported Component (the src) that has lost visibility to comp
  * 6. Downgrade dialog indicates downgrade on internalType UDT inside comp/internals (the dest) since it lost visibility to type under p1/impl

7.25 Move interface causing downgrade in source and destination (Uses test model [2.6](#2.6))
  * 1. Expand movetest/p1/impl
  * 2. Cut TestInterface
  * 3. Expand movetest/p2/impl2
  * 4. Paste TestInterface to impl2
  * 5. Downgrade dialog indicates downgrades on Port1 (the src) that has lost visibility to comp
  * 6. Downgrade dialog indicates downgrade on TestInterface operations and signals use of UDT (the dest) since they lost visibility to type mysubtype under p1/impl

7.26 Move component reference causing downgrade in destination (Uses test model [2.6](#2.6))
  * 1. Expand movetest/p1/impl/refs
  * 2. Cut Component reference
  * 3. Expand movetest/p2/impl2
  * 4. Paste Component reference to impl2
  * 5. Downgrade dialog indicates downgrade on TestInterface operations and signals use of UDT (the dest) since they lost visibility to type mysubtype under p1/impl
  * 6. Proceed with the move
  * 7. p2/impl2 canvas is empty.

7.27 Move imported class causing downgrade in destination (Uses test model [2.6](#2.6))
  * 1. Open the canvas for movetest/p1/impl/refs
  * 2. Cut Imported class clz
  * 3. Expand movetest/p2/impl2
  * 4. Paste to impl2
  * 5. Downgrade dialog indicates downgrade of the imported class (in the dest) since it lost visibility to clz under p1/impl
  * 6. Proceed with the move
  * 7. Restart BridgePoint
  * 8. Open the p1/impl/refs canvas, the imported class is gone.
  * 9. The p2/impl2 canvas is empty.

7.28 Move package causing downgrade in destination (Uses test model [2.6](#2.6))
  * 1. Open the canvas for movetest/p1/impl
  * 2. Cut package EEs
  * 3. Expand movetest/p2/impl2
  * 4. Paste EEs into impl2
  * 5. Downgrade dialog indicates downgrade of the TestEE bridge param and return values (in the dest) since they lost visibility to mysubtype under p1/impl
  * 6. Proceed with the move
  * 7. p2/impl2 canvas contains EEs pkg, the graphics are correct.
  * 8. TestEE::testBridgeParam::p has type integer, TestEE::testBridgeRval has type void
  * 9. Restart BridgePoint
  * 10. Open the p2/impl2/EEs, verify TestEE::testBridgeParam::p has type integer, TestEE::testBridgeRval has type void

7.29 Verify moved element can be moved back (Uses test model [2.7](#2.7))  
  * 1. cut P1
  * 2. paste into P3
  * 3. Result - P1 is properly moved
  * 4. cut P1
  * 5. Paste in to the system level
  * 6. Result - Paste is successful  

7.30 Visible move doesn't affect sequence diagram (Uses test model [2.8](#2.8))  
  * Cut Sequences in FailureCasesComponentPackage and paste into Destination package.  
  * Verify InvisibleExternalEntity and Bridge1 aren't downgraded in Sequences package in FailureCasesComponentPackage  
  * perform undo  

7.31 Non-visible move causes downgrades on sequence diagram  (Uses test model [2.8](#2.8))  
  * Cut Sequences in FailureCasesComponentPackage and paste into DestinationComponentPackage.  
  * There should NOT be a downgrade message. BridgePoint does not consider visibility for interaction elements.  You can see this by being able to assign to an EE contained within another component (like the destination container in the test).  Therefore the bridge should not be downgraded  
  * perform undo  


7.32 Test downgraded component reference with satisfaction (Uses test model [2.7](#2.7))  
  * 1. Make sure the package named private is private.
  * 2. open the satisfaction_test package diagram
  * 3. select comp1 and its provision
  * 4. select cut
  * 5. paste the selection into the package named private
  * 6. The downgrade dialog should report that the component reference will be downgraded, select ok
  * 7. Result 1 - The satisfaction_test component diagram no longer shows comp-1 (it has been moved to "private".
  * 8. Result 2 - The satisfaction is no longer present.
  * 9. select undo
  * 10. The mode is restored
  * 11. select redo
  * 12. The change is performed again (same result as step 6 and 7)
  * 13. restart BridgePoint
  * 14. Result 3 - The satisfaction_test and "private" package diagrams are the same as before the restart (same result as step 6 and 7)

End
---
