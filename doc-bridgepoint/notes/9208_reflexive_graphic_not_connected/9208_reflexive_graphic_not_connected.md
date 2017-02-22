---

This work is licensed under the Creative Commons CC0 License

---

# Reflexive graphic not connected
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes changes required to properly handle reflexive associations participating in associative links during reconciliation.   

2. Document References
----------------------  
<a id="2.1"></a>2.1 [BridgePoint DEI #9208](https://support.onefact.net/issues/9208) reflexive graphic not connected       

3. Background
-------------   
Support was added to reconcile missing graphics on a full scale.  The work included creating missing shapes and connectors fully.  The case of a reflexive in an associative link is not working.      

4. Requirements
---------------
4.1 Reconciliation of a reflexive association in an associative link shall work.      

5. Work Required
----------------
5.1 Consider associations in an associative link reflexive when appropriate      

The existing code only considered the simple association case (R_SIMP, R_PART).  A reflexive can also participate in the associative link case (R_RONE, R_ROTH, R_ASSR).  In Cl_c.Is_reflixive() the code handling the association case is simply changed to call the existing framework which determines whether or not an association is reflexive.  The reconciliation framework then handles this case appropriately.     

6. Implementation Comments
--------------------------

7. Unit Test
------------

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 9208_reflexive_graphic_not_connected

<pre>

doc-bridgepoint/notes/9208_reflexive_graphic_not_connected/9208_reflexive_graphic_not_connected.md

org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/Cl_c.java

</pre>

End
---

