---

This work is licensed under the Creative Commons CC0 License

---

# MASL Project Terminator Signatures
### xtUML Project Implementation Note

1. Abstract
-----------
See [[2.2]](#2.2) section 1.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9039 Signatures for MASL project terminator services are generated wrong](https://support.onefact.net/issues/9039)  
<a id="2.2"></a>2.2 [#9039 original implementation note](9039_project_terminator_signatures_int.md)  
<a id="2.3"></a>2.3 [#9263 Add mechanism to distinguish MASL components and packages](https://support.onefact.net/issues/9263)  

3. Background
-------------
Before continuing, read the previous implementation note [[2.2]](#2.2). This
work was reverted because it did not consider the requirement to consistently
load and persist project terminator services without being dependent on the
existence of satisfactions.

4. Requirements
---------------
4.1 For `SPR_*` instances that represent realized activities of MASL project
terminators, MASL shall be persisted according to the name of the domain and
terminator  
4.2 For `SPR_*` instances that do not represent realized activities of MASL
project terminators, MASL shall be persisted in the usual way: `<component
name>::<port name>~><message name>`  
4.3 The above naming rules shall apply independent of the existence of a
satisfaction between the project component and the domain component reference  

5. Design
---------

The new design does not rely on satisfied interfaces, but leverages a naming
convention that already exists in the project project.

5.1 Port name

Project terminator ports must be named in a way that is fully qualified with the
domain name and the terminator name. This is because a project component may
have two domains with the same terminator and must avoid name collisions.
Currently the project terminator ports are named `<domain><terminator>`. We will
modify this slightly to be `<domain>__<terminator>`. In this way, we can parse
apart the two parts of the name for signatures.

5.2 Persistence

Both the load and persist sides can utilize this naming convention to generate
signatures on the persist and match with the right port on the load.

5.3 `m2x`

`m2x` must be modified to create ports with this naming scheme.

6. Work Required
----------------
6.1 Add bridge `stringSplit` to the `Utilities` EE to split the port name into
parts  
6.2 Update `message2routine` to use the port name and not the component name to
build the signature when in a MASL project component  
6.3 Add logic in the import function to search for a port with the new naming
scheme on load  

6.4 Update `m2x` to name new project terminators with the new naming scheme  

7. Implementation Comments
--------------------------
7.1 The logic in 6.2 relies on the current method of identifying a MASL project.
An issue is open to determine a better way to do this [[2.3]](#2.3).

8. Unit Test
------------
8.1 Convert and import SAC  
8.2 Convert and import SAC_PROC  
8.3 Navigate to and expand the SAC_PROC component. Open an activity in the
`SAC__Operator` port.  
8.4 Verify that the activities are named `SAC::Operator~>...` and not
`SAC_PROC::SAC__Operator~>...`  
8.5 Close and reopen the SAC_PROC project  
8.6 Open the activity again. Verify that the action language was loaded.  

9. User Documentation
---------------------
None

10. Code Changes
----------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 9039_term  

<pre>

 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/External Entities/External Entities.xtuml         | 56 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/import_functions/import_functions.xtuml | 17 ++++++++++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/x2m_functions/x2m_functions.xtuml       | 17 ++++++++++++++++-
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                          |  4 +++-
 4 files changed, 91 insertions(+), 3 deletions(-)

</pre>

Fork/Repository: leviathan747/mc  
Branch: 9039_term  

<pre>

 bin/m2x                                                          | Bin 692080 -> 692080 bytes
 model/maslin/models/maslin/m2x/ooapopulation/ooapopulation.xtuml |   2 +-
 2 files changed, 1 insertion(+), 1 deletion(-)

</pre>

End
---

