

---

This work is licensed under the Creative Commons CC0 License

---

# Analyze Package References and Update Metamodel
### xtUML Project Design Note


1. Abstract
-----------
This note describes the work done to support the addition of Package Reference
to the OOAofOOA metamodel and resolve issues around this class.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8679](https://support.onefact.net/issues/8679) Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #8633](https://support.onefact.net/issues/8633) Package Reference     
<a id="2.3"></a>2.3 [BridgePoint DEI #8858](https://support.onefact.net/issues/8858) Package Reference Documentation   
<a id="2.4"></a>2.4 [BridgePoint DEI #8857](https://support.onefact.net/issues/8857) Filter package selection list   

3. Background
-------------
The initial design of the Package Reference class and association is in [2.2](#2.2). 
Reflexive associations are notoriously mind-bending for model compiler developers, 
especially because the use of rel phrases can be implemented differently by the 
model compiler archetypes and the generator itself (as evidenced by the existence
of the ```swap_reflexives.sh``` script used in the MC-3020 creation steps.   

4. Requirements
---------------
4.1  Package Reference shall be added to OOAofOOA  
4.1.1  It shall be added as an associative class on R1402   
4.1.2  R1402 shall be a reflexive relationship on Package   

4.2  All BridgePoint plug-in code shall generate cleanly.   

4.3  Functionality   
4.3.1  Package References shall be persisted when created.   
4.3.2  They shall be loaded and linked properly when BridgePoint starts.   
4.3.3  Packages shall be assignable to other packages.   
4.3.4  Packages that are assigned as package references shall be unassignable.  

5. Analysis
-----------
See the analysis note for [2.2](#2.2).  

6. Design
---------
6.1  R1402 represents the first time we have used a reflexive-associative 
  relationship in the primary part of the metamodel.  This type of relationship
  is used in the verifier with the ```Runtime Channel``` class, but that one
  is not persisted, loaded, or used in the UI.   
6.1.1  In several places in MC-Java there is existing code that is meant to 
  handle these types of relationships when generating the Java, but the paths
  were never tested.   
  
6.2  In ```MC-Java/java.arc``` we modified ``` .function get_reflexive_phrase```
  to modify the traversal to get the desired relationship phrase.   
  
6.3  In ```MC-Java/statement.inc``` we modified more places to flip the rel 
  phrase being used when generating relate and unrelate statements for this 
  type of association.   
  
6.4  In ```org.xtuml.bp.core/arc/function_body.inc``` we added some special 
  case code that in functions with other special case code that feeds into 
  selection dialog creation.   
  
6.5  In the ```Package``` class we added new functions and modified existing 
ones to handle package references    
  * ```get_compartment_text```  - allows us to change the name of the assigned
  package to the path of the assignee.   
  * ```action_filter``` - used to hook into the code that determines if the
  assign and unassign entries should be shown in the context menu   
  * ```isAssigned``` - utility to determine if the package is asssigned as a 
  package reference.     
  * ```unAssign``` - unassigns the package as a reference.  Unhooks the R1402
 relationship and deletes the EP_PKGREF.   
  * ```assignAsPackageReference``` - Creates an EP_PKGREF and hooks up to the 
  target package on the other side.   
  * ```canAssignToPackage``` - utility function
     
6.6 In ```bp.core/ooaofooa/Functions/Context Menu Entry Functions/Context Menu Entry Functions.xtuml``` 
 we added new functions EP_PKG_AssignAsPackageReference and EP_PKG_Unassign to provide 
 the bridge between the context menu implementation domain and the ooaofooa.   
 
6.7 In ```bp.core/sql/context_menu.pei.sql``` we added new PEI instances that
 connect in the new context menu actions.   
 
6.8 In ```bp.io.core/arc/export_functions.inc``` we added a substitution 
 modifier "$cr" that was missing on a relationship phrase usage in a function
 name.   
 
6.9 ```bp.io.mdl/arc/create_persistence_meta_data.arc``` is another place where
 we had to add functionality to get the proper rel phrase from R_AONE and R_AOTH
 when generating for a reflexive-linked assocation.  Also added missing "$cr"
 variable modifiers.   
  
6.10 Updated ```file_io.pei.sql``` and ```stream.pei.sql``` to persist package
 reference instances along with the source package across R1402.   
 
7. Design Comments
------------------
There is additional work to be done to filter the package selection list that
will be handled in follow-on issue [2.4](#2.4).   

8. User Documentation
---------------------
Raised [2.3](#2.3).  

9. Unit Test
------------
9.1  Build BridgePoint successfully.   
9.2  Functional test  
  * Create a model  
  * Assign a package as a package ref
  * __R__ See the package name change to reflect the path to the target  
  * Restart BridgePoint  
  * __R__ See the package name still reflect the path to the target  
  * Unassign the package reference
  * __R__ The name changes back to the original package name  
  
End
---

