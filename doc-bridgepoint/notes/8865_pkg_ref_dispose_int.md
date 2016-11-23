---

This work is licensed under the Creative Commons CC0 License

---

# Support package / package reference dispose and downgrade
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work performed to handle dispose and downgrade of 
package references.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8865](https://support.onefact.net/issues/8865) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #8873](https://support.onefact.net/issues/8873) Package Reference dispose and downgrade test.  
 
3. Background
-------------
Package reference support is being added in incremental baby steps.  This step 
makes sure dispose and downgrade are handled properly.   

4. Requirements
---------------
4.1 BridgePoint shall clean up Package References instances and associations 
  when a source or target package is removed from the model.    
4.2 BridgePoint shall handle downgrade of an assigned package.    

5. Work Required
----------------
5.1  Dispose   
5.1.1  Package dispose() must unAssign if the package is assigned as a reference  
5.1.2  Package dispose() must also work in reverse. If the package is the target
  of one or more package references, those references need to be forced to 
  unAssign.  

5.2  Downgrade  
5.2.1  Package downgradeCheck() must handle the same cases where the package is 
  the source or target of a downgrade.


6. Implementation Comments
--------------------------
6.1  Tear down error on R8008   
6.1.1  During testing we noticed an error in the log: 
```The following relationships were not torn down by the Component Visibility.dispose call: 8008```. 
Investigation found that this is due to ```PackageableElement.dispose()``` line 15. 
The code tears down the R8004 link before deleting the ```PE_CVS``` but it does
not unrelate across R8008 before deleting the ```PE_CVS```. We added code to 
tear down R8008 before deleting.      

7. Unit Test
------------
Captured as manual test [2.2](#2.2).  
<pre>
7.1 Downgrade test

    Create a new project "foo", import the test model
    Open the comp diagram
    R See that the package inside comp is assigned to tgt
    Expand the model in Model Explorer
    Move package tgt inside package privat to cause a loss of visibility
    R The downgrade notice dialog pops up and notes that tgt is associated with b and will cause a downgrade
    Select OK
    R The package inside comp is unassigned and the name changes to b

7.2 Delete target test

    Create a new project "bar", import the test model
    Open the comp diagram
    R See that the package inside comp is assigned to tgt
    Expand the model in Model Explorer
    Delete package tgt
    R The downgrade notice dialog pops up and notes that this will cause a downgrade on b
    Select OK
    R The package inside comp is unassigned and the name changes to b

7.3 Delete source test

    Create a new project "baz", import the test model.
    Open the comp diagram
    R See that the package inside comp is assigned to tgt
    Expand the model in Model Explorer
    Open the "Instance Population Monitor" view
    Expand baz/Ooaofooa Model Elements/baz.xtuml
    R See there is one PackageReference_c instance
    Delete assigned package "b" (assigned to tgt)
    In the Instance Population Monitor, click the double arrow "Update population display" button
    Expand baz/Ooaofooa Model Elements/baz.xtuml
    R See there are no PackageReference_c instances
</pre>

8. User Documentation
---------------------
None.  

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint  
Branch: 8865_pkg_ref_dispose  

<pre>

doc-bridgepoint/notes/8865_pkg_ref_dispose_int.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/
    Package/Package.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Packageable Element/
    Packageable Element/Packageable Element.xtuml


</pre>

End
---

