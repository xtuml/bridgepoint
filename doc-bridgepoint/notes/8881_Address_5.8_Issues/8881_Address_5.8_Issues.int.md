---

This work is licensed under the Creative Commons CC0 License

---

# Title goes here
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes changes required to address issues found during release
testing.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #7650](https://support.onefact.net/issues/7650) subsuperchain run Verifier and MC  
<a id="2.2"></a>2.2 [BridgePoint DEI #xxx2](https://support.onefact.net/issues/xxx2) TODO: Add description here.  
<a id="2.3"></a>2.3 [BridgePoint DEI #xxx3](https://support.onefact.net/issues/xxx3) TODO: Add description here.  

3. Background
-------------
This note describes any changes required to allow all manual tests to pass for   
release 5.8.   

4. Requirements
---------------
4.1 All manual tests must pass for release 5.8   

5. Work Required
----------------
5.1 7650   

This test was failing due to the tools inability to collect all of the proper   
external entities not within components.  Previously it seems to have passed due   
to the EE being treated as realized.  With the changes for the realized code the   
test fails unless set as realized.  This fix here is to modify the test model   
such that the Architecture EE is marked as realized.

5.2 Model Element Move

The following problems exist when using the new move capability:

1. Moving a component to private location causes unformalization of satisfied   
interfaces for any component reference.   
2. Undoing a component move that causes loss of satisfactions results in a   
broken graphical connection.      

The first one is resolved by passing false to the Unformalize operation, which   
prevents unformalization of the other end attached to the compoent reference   
being disposed.

The second issue is addressed by assuring that all elements are considered
graphically during the delete notification.  Currently the tool is expecting xml   
data to determine if a certain class type can live on that diagram.  The    
imported interface reference is always maintained via the reconciler.  This   
cannot work for move as the reconciler is not part of the initial transaction.   
The normal dispose operations are now being responded to and deleting graphical
instances within the move transaction.

The last issue is related to loading.  The code that assures locating an RTO is   
not considering the case where the RGO has been loaded, thereby creating a   
proxy.  The code is not run in batchRelate unless the target RTO is null.  It is   
now changed to be processed if the target RTO is null or the target RTO is a   
proxy.


6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Run all manual tests   
7.1.1 All tests pass   

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/(bridgepoint, models)   
Branch: 8881_Address_5.8_Issues

<pre>

MC-Java/java.arc

com.mentor.nucleus.bp.core/models/com.mentor.nucleus.bp.core/Component/Interface   
	Reference/InterfaceReference.xtuml

</pre>

End
---

