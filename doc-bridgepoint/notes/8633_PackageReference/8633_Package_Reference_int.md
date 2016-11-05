---

This work is licensed under the Creative Commons CC0 License

---

# Package References (a.k.a. Imported Packages)
### xtUML Project Implementation Note


1. Abstract
-----------

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8633](https://support.onefact.net/issues/8633) Headline issue  
<a id="2.2"></a>2.2 [#8633 ANT](8633_PackageReference_ant.md) Analysis note    

3. Background
-------------
Add package references to the xtUML meta-model.

4. Requirements
---------------
See [2.2](#2.2).

5. Work Required
----------------
See [2.2](#2.2).

6. Implementation Comments
--------------------------
6.1 After adding the new class and relationship to the ```Element Packaging``` 
  package in the metamodel, there was a build error inside the archetype  ```org.xtuml.bp.io.mdl/arc/create_external_link_evaluator.arc```.  
6.1.1  It was determined that this is the first place where we have modeled a 
  reflexive+linked associative relationship in the metamodel that has an effect
  on the BridgePoint UI.  In the ```Engine``` package we have this type of 
  relationship with ```Runtime Channel``` and ```Component Instance```, but 
  this is verifier related and we do not process these classes in the external
  link evaluator archetype.  
6.1.2  The archetype was modified to handle this type of relationship properly
  when creating the evaluator functions.   
6.1.2.1  After the implementation was done, we noticed one possible issue in 
  the ```getAssociationMapOfExternalRGOs``` function.  In this function we are 
  creating a map where all the keys are the relationship numbers and the values 
  are lists of element across that relationship.  In the R1402 case, we are 
  writing to the same key "R1402" twice.  So the first set is overwritten.  
   
  There is code in ```org.xtuml.bp.core/arc/generate_RGO_resolution_methods.inc```
  that generates a ```Resolve...forR...()``` function. We found that this 
  archetype is not generating one of these functions to traverse R1402.  The 
  reason is that the archetype only generates the resolve for a very limited set
  of classes in the meta-model, as defined by the this gating function:   
```
.function is_supported_class
  .param String keylett
    .assign attr_result = false
    .if(keylett == "S_DT")
      .assign attr_result = true
    .elif(keylett == "C_I")
      .assign attr_result = true
    .elif(keylett == "C_C")
      .assign attr_result = true
    .elif(keylett == "C_EP")
      .assign attr_result = true
    .elif(keylett == "C_IR")
      .assign attr_result = true
    .elif(keylett == "C_PO")
      .assign attr_result = true 
    .end if
.end function
```
   
  It appears this all has to do with component/interface formalization probably.  
  Since package is not in this list, it seems we don't care about the resolve 
  and thus it does not matter that we are overwriting the map value since it 
  won't actually ever be used.     

7. Unit Test
------------
7.1  Get a clean build of the BridgePoint plug-ins with the new meta-model class
  in place.  
  
8. User Documentation
---------------------
None.

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8633_packref

<pre>

doc-bridgepoint/notes/8458_model_element_move_tests/
    8458_Model_Element_Move_Tests.dnt.md
doc-bridgepoint/notes/8633_PackageReference/8633_Package_Reference_int.md
doc-bridgepoint/notes/8633_PackageReference/8633_PackageReference_ant.md
doc-bridgepoint/notes/8633_PackageReference/pkgref.png

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/
    Element Packaging.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/
    Package Reference/Package Reference.xtuml

org.xtuml.bp.io.mdl/arc/create_external_link_evaluator.arc


</pre>

End
---

