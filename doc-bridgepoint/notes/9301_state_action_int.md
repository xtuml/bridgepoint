---

This work is licensed under the Creative Commons CC0 License

---

# MASL state action file not exported for state with entry from supertype and local events with paramters
### xtUML Project Implementation Note

1. Abstract
-----------
As title

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9301 MASL state action file not exported for state with entry from supertype and local events with paramters](https://support.onefact.net/issues/9301) headline issue  
<a id="2.2"></a>2.2 [#9302 MASL state action file not exported for state with entry from supertype and local events with paramters](https://support.onefact.net/issues/9301) BridgePoint issue  
<a id="2.3"></a>2.3 [#9283 Abstract and Concrete Polymorphic Events](https://support.onefact.net/issues/9283)  

3. Background
-------------
At first glance this appeared to be a problem with the masl tools. It is not a
problem with the MASL toolchain, but a problem in BridgePoint proper. It was
introduced with the change that introduced concrete polys [[2.3]](#2.3). The
MASL activity importer works by parsing `.masl` files and matching the parsed
signature with the signatures contained in the right PMC using the OOA
operation `getSignature`. For states, `getSignature` selects any incoming event
and uses the event parameters to form the state signature. With concrete polys,
the event in the subtype never has any parameters and it happens to be the
event that is selected to form the signature. Therefore the signature does not
match and the activity is not loaded properly. The solution is to select up to
the supertype event for the parameters if a concrete poly in the subtype is
chosen.

4. Requirements
---------------
4.1 Export action files for states in a subtype with local events containing
parameters

5. Work Required
----------------
5.1 Add `getConcretePoly` operation to `SM_EVT` to recursively search up the
subtype hierarchy to select the polymorphic event at the top  
5.2 Introduce a call to `getConcretePoly` in `SM_STATE` `getSignature` to select
a concrete poly in the supertype if it exists  
5.3 `splitString` in the `Utilities` EE needed to be changed to allow splitting
on "." without treating it as a wildcard character  
5.4 Add corresponding call to `getConcretePoly` in `SM_ACT2state` on the export
side  

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
7.1 MASL round trip with `9302_state_action` test case
7.2 Round trip regression test

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 9301_state_action  

<pre>

 doc-bridgepoint/notes/9301_state_action_int.md                                                                      | 73 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/External Entities/External Entities.xtuml                   |  4 +++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/x2m_functions/x2m_functions.xtuml                 | 15 ++++++++++++---
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml | 39 +++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine State/State Machine State.xtuml |  8 ++++++++
 5 files changed, 123 insertions(+), 1 deletion(-)

</pre>

End
---

