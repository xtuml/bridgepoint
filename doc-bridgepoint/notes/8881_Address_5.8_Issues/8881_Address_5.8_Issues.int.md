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
<a id="2.2"></a>2.2 [[BridgePoint Issue #8633](https://support.onefact.net/issues/8633) Introduce Package References.  
<a id="2.3"></a>2.3 [BridgePoint DEI #8321](https://support.onefact.net/issues/8321) Add support for Model Element Move.  

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
prevents unformalization of the other end attached to the component reference   
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

5.3 Fix problem causing several model execution tests to fail.  
As part of the work done to introduce package references, [issue 8633](https://support.onefact.net/issues/8633), MC-Java was 
modified to support associative classes on a one to many reflexive relationship. Prior to this change the ooaofooa meta model did not use such a relationship, and therefore MC-Java did not properly support it. The ooaofooa did however contain an associative many to many. This is R2968. The work done for 8633 fixed a problem with how relationship phrases were being used.
This fix cause verified execution to fail in many cases because it effectively switched the way role phrases were being interpreted in this case. To resolve this the role phrases on R2968 were swapped. Addtionally places that were using traversing this relationships in hand-craft java code were also modified to switch the usage of the relations phrase. There were only 4 such files that were not generated files. They were: bp/debug/java/access/VerifierInvocationHandler.java, bp/debug/ui/actions/ExecuteAction.java, bp/debug/ui/model/BPDebugTarget.java. An example of the failures this fixed comes from 
[General Graphics Testing 2](https://support.onefact.net/issues/1609) and its execution of the Looped test model. The pull request with this fix is [pull request 260](https://github.com/xtuml/bridgepoint/pull/260).

5.4 RTO Tests   
   
The RTO tests were failing mostly due to the new move behavior.  The test code   
has been reworked to test expected behavior of the new move functionality.  This    
includes taking out comments preventing result validation (added with [2.3]).   
It also includes some changes to deal with a delete when it actually occurs,   
previously we deleted the element after extracting the data to the clipboard.

The RTO tests also found an issue with the downgrade functionality of move.  The
downgrade when necessary will re-associate an element with a default RTO or   
simply dispose of the RGO if no valid element can be a reference.  The case of   
a User Data Type requires we check both the role of an RGO as well as a RTO.   
The code in User Data Type.downgradeCheck() is modified to traverse R18   
(RGO case) and check the visibility for the RTO.

Another change was made to overload the startMove method.  This method sets a   
flag indicating we are beginning a move.  It also clears the selected elements   
to be moved.  During testing we are creating transactions that execute inbetween   
the cut marker and the actual paste.  Starting a transaction sets the in-move   
flag to false (resetting to a clean state).  The new startMove method takes a   
boolean indicating that we simply want to set the marker back to true, leaving   
the expected selection alone.      

5.5 Explorer tests

There was an array index out of bounds occuring.  The menu items on first pass   
were not available, to prevent this a check for an empty array is added, before   
assuming the array was populated.  A dispatch event call was added which   
prevents the out of bounds exception, but the check for an empty array is left   
as a precaution.  

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

