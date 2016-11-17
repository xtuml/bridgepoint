---

This work is licensed under the Creative Commons CC0 License

---

# Filter Package References
### xtUML Project Implementation Note


1. Abstract
-----------
The initial implementation of package reference support was very loose.  The
package selection dialog did not filter the package list at all.  All visible 
packages in the workspace were available.  This was not ideal.  To be user-
friendly we need to filter the list down to only contain valid targets.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8857](https://support.onefact.net/issues/8857) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #8633](https://support.onefact.net/issues/8633) Analyze and add Package References        
<a id="2.3"></a>2.3 [BridgePoint DEI #8679](https://support.onefact.net/issues/xxx1) Support Package References in the UI       

3. Background
-------------
See the preceding issue documentation tied to [2.2](#2.2) and [2.3](#2.3).  

4. Requirements
---------------
4.1  The package selection list shall filter the list of target packages based on
  the following rules:   
4.1.1  Rule: do not allow assignment to self  
4.1.2  Rule: do not allow assignment to a package that is already assigned as a 
  package reference  
4.1.3  Rule: do not allow assignment to any parent package in hierarchy of the 
  reference being assigned.   

4.2  The CME for assigning a package reference shall only be visible when:  
4.2.1  Rule: The package must be empty  
4.2.2  Rule: Only a Package located directly as a child of a Component shall be 
  eligible to be a Package Reference.   

5. Work Required
----------------
5.1 Package::canAssignToPackage()  
5.1.1  Implement logic in this OAL function to enforce the rules in 4.1.  

5.2 Package::actionFilter()  
5.2.1  In the "assign" case section, implement logic to enforce the rules in
  4.2.   

6. Implementation Comments
--------------------------
6.1  ```bp.io.mdl/arc/export_function.inc```  
6.1.1  Remove the ```is_containment()``` function and the places where it is
  used.  It was hardcoded to always return false and therefore wasn't really
  useful.  
6.1.2  Reflexive relationships are explicitly not used during model export.
  However, the relationship ```R1402``` is needed to support exporting a proxy
  to the target package used in a package reference.  We modified this function
  to hardcode allowing R1402 to be used.   

7. Unit Test
------------
7.1  Setup
  * Download the model attached to this issue in redmine.
  * Start BridgePoint
  * Create a new project
  * Import the test model
    
7.2  Test
  * Open the "comp" diagram
  * Right-click on package "b"
  * __R__ "Assign As Package Reference..." is visible in the context menu, "Unassign" 
  is not
  * Select "Assign As Package Reference..."
  * __R__ The only packages in the list are "a" and "tgt"
  * Select "tgt"
  * __R__ The name changes from "b" to the path to the "tgt" package
  * __R__ The ```b.xtuml``` file on disk contains an EP_PKG_PROXY to "tgt" along
  with the EP_PKGREF
  * Restart BridgePoint
  * __R__ The "comp" diagram is opened and the package shows the path to "tgt". "Unassign" 
  is available on the CME for the package.
  * Open the source package and right-click on package "a"
  * __R__ The CME does not contain "Assign As Package Reference..."
  * Create a new package "c" under component
  * Click "Assign As A Package Reference..."
  * __R__ The only packages in the list are "a" and "tgt"  

8. User Documentation
---------------------
None.  

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8857_pkg_ref_filter   

<pre>

doc-bridgepoint/notes/8857_pkg_ref_filter_int.md 

src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/Package.xtuml
src/org.xtuml.bp.io.core/arc/export_functions.inc 
 
</pre>

End
---

