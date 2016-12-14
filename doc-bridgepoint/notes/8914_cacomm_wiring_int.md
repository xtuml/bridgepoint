---

This work is licensed under the Creative Commons CC0 License

---

# Deprecate Unused Subsystems
### xtUML Project Implementation Note


1. Abstract
-----------
Let us continue to prune the metamodel of unused model artifacts.

2. Document References
----------------------
[1] [8914](https://support.onefact.net/issues/8914) - Delete deprecated OOA pacakges  

3. Background
-------------
Communication and Access is a relational class diagram that models the
paths of events and data between classes.  In a very distant version of
BridgePoint, this information was used to derive a diagram graphically
illustrating the movement of data and control between classes.  It was
very nice.  However, it has not been supported in many years.

Automatic Wiring is a subsystem containing an association class that
links EEs to function across domains.  Logic existed in the past to
automatically invoke a domain function in a foreign domain with the same
name as a bridge in a local domain.  This functionality was superceded
by components, interfaces, ports and inter-component messaging.

4. Requirements
---------------
4.1 Delete Communication and Access  
4.2 Delete Automatic Wiring  
4.3 Delete associated unused model, code, compiler, configuration artifacts.

5. Work Required
----------------
5.1 Delete Communication and Access  
5.2 Delete Automatic Wiring  
5.3 Search for the class keyletters in other places and surgically elide.  
```
grep -H S_AW from within bp.core/models
Removed a few disposes.
grep -H CA_ from within bp.core/models
Removed lots of disposes.

Removed CA_ and S_AW from ooa_schema.
Removed clearooa2.sql.  This was used with the old gen database tools.
Removed microwave.sql with is out of date and not used.

Updated export_functions.inc to not add a special brace around CA_SMSMC.
Similar update for import_functions.inc.
```
  
6. Implementation Comments
--------------------------
6.1 `xtumlmc_schema`  
Note that `MSG_M` wants a `participateInCommunication BOOLEAN` attribute.
This needs to stay in the MC schema for a release or two.  
6.2 unit test expected results  
There are hits to Communication and Access instances in the expected
results data.  However, these instances are ignored during load and
are not part of persistence.
  
7. Unit Test
------------
7.1 Build the schema.  Make sure no referentials shifted.  
7.2 Build BridgePoint.  
7.3 Run GPS Watch.  

8. Code Changes
---------------
<pre>

Fork: cortlandstarrett/bridgepoint
Fork: cortlandstarrett/mc

Branch name: 8914_cacomm_wiring

xtuml/bridgepoint
 doc-bridgepoint/notes/8914_cacomm_wiring_int.md                             |   87 +
 src/MC-Java/README.TXT                                                      |    3 +-
 src/MC-Java/clearooa2.sql                                                   |    2 -
 src/MC-Java/microwave.sql                                                   | 8125 -----------------------------------
 src/MC-Java/ooa_schema.sql                                                  |    6 -
 src/org.xtuml.bp.core.test/arc/create_dispose_test.arc                      |   80 -
 .../ooaofooa/Communication And Access/Access Path/Access Path.xtuml         |  213 -
 .../ooaofooa/Communication And Access/Communication And Access.xtuml        | 6059 --------------------------
 .../Communication And Access/Communication Path/Communication Path.xtuml    |  108 -
 .../Communication And Access/EE to SM Comm Path/EE to SM Comm Path.xtuml    |  265 --
 .../Communication And Access/EE to SM Event Comm/EE to SM Event Comm.xtuml  |  169 -
 .../SM to EE Access Path/SM to EE Access Path.xtuml                         |  247 --
 .../Communication And Access/SM to EE Comm Path/SM to EE Comm Path.xtuml    |  274 --
 .../SM to EE Data Item Access/SM to EE Data Item Access.xtuml               |  250 --
 .../Communication And Access/SM to EE Event Comm/SM to EE Event Comm.xtuml  |  224 -
 .../SM to OBJ Access Path/SM to OBJ Access Path.xtuml                       |  226 -
 .../SM to OBJ Attribute Access/SM to OBJ Attribute Access.xtuml             |  250 --
 .../Communication And Access/SM to SM Comm Path/SM to SM Comm Path.xtuml    |  330 --
 .../Communication And Access/SM to SM Event Comm/SM to SM Event Comm.xtuml  |  169 -
 .../models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml            |    7 -
 .../Domain/External Entity Data Item/External Entity Data Item.xtuml        |    4 -
 .../ooaofooa/Domain/External Entity Event/External Entity Event.xtuml       |    5 -
 .../ooaofooa/Domain/External Entity in Model/External Entity in Model.xtuml |   12 -
 .../models/org.xtuml.bp.core/ooaofooa/Domain/Function/Function.xtuml        |    5 -
 .../ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml    |    9 -
 .../ooaofooa/State Machine/State Machine/State Machine.xtuml                |   20 -
 .../models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/Attribute.xtuml   |    6 -
 .../ooaofooa/Subsystem/Imported Class/Imported Class.xtuml                  |   18 -
 .../org.xtuml.bp.core/ooaofooa/Subsystem/Model Class/Model Class.xtuml      |    6 -
 .../ooaofooa/Wiring/Automatic Wiring/Automatic Wiring.xtuml                 |  162 -
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Wiring/Wiring.xtuml |  554 ---
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/ooaofooa.xtuml      |   48 -
 src/org.xtuml.bp.io.core/arc/export_functions.inc                           |    8 -
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                            |   16 +-
 src/org.xtuml.bp.io.core/arc/import_functions.inc                           |    6 -
 .../src/org/xtuml/bp/ui/canvas/test/ErrorPathsTest.java                     |    8 -
 36 files changed, 89 insertions(+), 17892 deletions(-)


xtuml/mc
 bin/xtumlmc_build                                                           |   1 -
 model/maslin/models/maslin/m2x/ooapopulation/ooapopulation.xtuml            |   5 -
 .../models/mcooa/ooaofooa/Wiring/Automatic Wiring/Automatic Wiring.xtuml    | 139 ---------
 model/mcooa/models/mcooa/ooaofooa/Wiring/Wiring.xtuml                       | 545 ------------------------------------
 model/mcooa/models/mcooa/ooaofooa/ooaofooa.xtuml                            |  24 --
 schema/sql/xtumlmc_schema.sql                                               |   6 -
 7 files changed, 167 insertions(+), 720 deletions(-)

</pre>

End
---

