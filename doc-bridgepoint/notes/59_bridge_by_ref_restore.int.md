---

This work is licensed under the Creative Commons CC0 License

---

# Bridge By-Reference Parameter Restoration
### xtUML Project Implementation Note


1. Abstract
-----------
Bridge execution in Model Verifier is done by the execute method in the Bridge 
Invocation and Bridge Value classes. This method is setup to handle execution
of both realized and modeled bridges, and would handle modification required
for by-reference parameters before executing the bridge. As the object used
for the by-reference parameter might be handled elsewhere in Model Verifier,
the modification needs to be undone after the bridge has been executed.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #59](https://support.onefact.net/issues/59)  
<a id="2.2"></a>2.2 [Test Model](https://github.com/xtuml/models/tree/master/test/BridgeByRefParameterRestore)
<a id="2.3"></a>2.3 [BridgePoint DEI #8885](https://support.onefact.net/issues/8885) realized conversion of core types  

3. Background
-------------
If OAL did not exist for the bridge, the current implementation of Bridge 
Invocation.execute() would perform the modification of the by-reference 
parameter before checking to see if a realized bridge existed. If the realized
bridge didn't exist, the modification of the by-reference parameter was left in
place, so another usage of the parameter within the same action processing would
result in the parameter being of the wrong type.
This was discovered by a user and the issue [[2.1]](#2.1) was generated.  

4. Requirements
---------------
4.1 Ensure any modification to the by-reference parameter is undone before the
execute method is exited.  
4.2 Do the same in Bridge Value.

5. Work Required
----------------
5.1 Move the modification of the by-reference parameter to after the check for
existence of a realized implementation of the bridge.  
* This was the root cause of the issue. [[2.1]](#2.1)  

5.2 Restore the by-reference parameters in all cases that they are modified.

6. Implementation Comments
--------------------------
There were scenarios through which a return value would never get set up
in Bridge Value.  Now, a return value is initialized early and then updated
by the OAL or by the realized bridge before returning.

A surgical and minimal fix was attempted first.  But more restructuring was
needed.  So, Bridge Invocation and Bridge Value were edited to be mostly
identical.  Now they can be vimdiff'd to see the parallel behavior.

Further testing found that there was a problem in the conversion of the
arguments and runtime values back to the originals.  For boolean, the
first conversion was from a string representation of `Boolean to `BPBoolean`;
this allows the realized Java to deal with it.  However, the conversion
back to the original converted to `Boolean`.  But this is not correct, so
a change was made to `GD::convertFromBPType` to convert the `BPBoolean` to
the string representation used by Verifier.  This allows the test case
supplied by the user to run correctly.

Found another problem.  The original design initial the return run-time
value in all cases.  This is incorrect; when there are statements in the
body, do not initialize the return run-time value.

Next, we need to consider how `Integer`, `Real` and `unique\_id` are handled.

7. Unit Test
------------
Execute the function in the test model. [[2.2]](#2.2)

7.1 Launch test model in Verifier. No errors should be reported.  

7.2 Run MicrowaveOven in Verifier.  See same behavior as always.

7.3 Run the model attached to issue #59.  See 12 lines of assertions
and no errors.

8. User Documentation
---------------------

9. Code Changes
---------------
<pre>
Fork/Repository: https://github.com/cortlandstarrett/bridgepoint.git
Branch: 59_byref

 doc-bridgepoint/notes/59_bridge_by_ref_restore.int.md                              |  76 ++++++++++++++++++
 .../ooaofooa/Invocation/Bridge Invocation/Bridge Invocation.xtuml                  |  87 ++++++++++----------
 .../models/org.xtuml.bp.core/ooaofooa/Value/Bridge Value/Bridge Value.xtuml        | 167 ++++++++++++++++++++-------------------
 3 files changed, 205 insertions(+), 125 deletions(-)

</pre>  

End
---

