---

This work is licensed under the Creative Commons CC0 License

---

# Run downgrade check on package reference unassignment
### xtUML Project Implementation Note


1. Abstract
-----------
During testing of package references we saw a case where an opaque component
had elements that should have been downgraded when a package reference went away
but they did not.  This changeset addressed this issue.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8876](https://support.onefact.net/issues/8876) Headline issue.    
<a id="2.2"></a>2.2 [BridgePoint DEI #8880](https://support.onefact.net/issues/8880) Package reference unassign downgrade test      

3. Background
-------------
See abstract.  

4. Requirements
---------------
4.1 When a component is opaque and a package reference is unassigned, elements
  inside the local component that relied on elements accessed in the referred-to
  package shall be downgraded.     

5. Work Required
----------------
5.1 In Package::unAssign(), after unhooking the package reference add  
```
  // This may have caused elements inside the parent component to need downgrade
  select one c_c related by self->PE_PE[R8001]->C_C[R8003];
  if (not_empty c_c)
    c_c.downgradeCheck();
  end if;
```  

6. Implementation Comments
--------------------------
None.  

7. Unit Test
------------
Captured as manual test [2.2](#2.2).  
<pre>
1. Test downgrade without component opaqueness

    In bp.pkg/plugin_customization.ini make sure opaqueness is "off" by setting the preference org.xtuml.bp.core/bridgepoint_prefs_opaque_components=false
    Start BridgePoint
    Create project "foo"
    Import the test model
    Navigate to comp and open the component diagram
    R See the package assigned to tgt
    Open the "impl" package diagram under comp
    R See the imported class assigned to "myclass" under tgt, and mydt is based on "specialdt" (which lives under tgt)
    Go back to the comp diagram and unassign the imported tgt package
    R No warning dialog pops up
    R Inside the impl package, myclass is still assigned and mydt is still based on specialdt
    Exit BridgePoint

2. Test downgrade with component opaqueness

    In bp.pkg/plugin_customization.ini make sure opaqueness is "on" by setting the preference org.xtuml.bp.core/bridgepoint_prefs_opaque_components=true
    Start BridgePoint
    Create project "bar"
    Import the test model
    Navigate to comp and open the component diagram
    R See the package assigned to tgt
    Open the "impl" package diagram under comp
    R See the imported class assigned to "myclass" under tgt, and mydt is based on "specialdt" (which lives under tgt)
    Go back to the comp diagram and unassign the imported tgt package
    R The downgrade dialog pops up and indicates myclass and mydt are affected by the unassignment
    Select OK to proceed
    R Inside the impl package, myclass is removed and mydt is now based on integer
    Exit BridgePoint
</pre>

8. User Documentation
---------------------
None.   

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8876_unassign_downgrade  

<pre>

doc-bridgepoint/notes/8876_unassign_downgrade_int.md 
src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/Package.xtuml  

</pre>

End
---

