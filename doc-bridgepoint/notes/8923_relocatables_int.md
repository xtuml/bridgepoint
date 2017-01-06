---

This work is licensed under the Creative Commons CC0 License

---

# Deprecate Relocatables Code
### xtUML Project Implementation Note


1. Abstract
-----------
Relocatables have not been supported since the Old Gray Ghost, yet there is
code to support the feature in the present build.  Delete it.

2. Document References
----------------------
[1] [8923](https://support.onefact.net/issues/8923) - Deprecate Relocatables Code  

3. Background
-------------
Relocatables supported a tagged identifier approach to imbedding links
into action language.  The links resolved to structural model elements.
The links were "edited in automatically" by the tool holding both an
edit buffer and a tagged buffer simultaneously.
This feature has never been supported in the Eclipse-based BridgePoint.
A rename/refactor approach will be supported for MASL in 6.0.
A similar scheme (hopefully) will be applied to OAL some day.

4. Requirements
---------------
4.1 Delete dead relocatables code.

5. Work Required
----------------
5.1 Delete the bp.core/relocatables module.  
5.2 Search for and replace all calls to this code.  
5.3 Disable and delete the model change listener that informed the editor
of structural xtUML changes.  
5.4 Delete the conversion function from OOAofOOA and stop calling it from
all of the `Action_Semantics` `O_DBATTR`s.  
5.6 Search for "elocatabl" in xtuml/bridgepoint and xtuml/bptest and
make adjustments as necessary.  

6. Implementation Comments
--------------------------
6.1 xtuml/bptest  
I grep'd bptest/src for "elocatable" and got no hits.  This indicates that
there are no dependencies in the test plugins.

6.2 `markComponentRoot`  
While grep'ing I found a concentration of specialized package marking
code that was wrong.  It was benign, but it is disconcerting to see
specialized pacakge stuff.

7. Unit Test
------------
7.1 Build BridgePoint.  
7.2 Run it to be sure it launches.  
7.3 Edit an OAL activity.  
7.4 Close the activity.  
7.5 Open it again.  
7.6 (R) Be sure it was persisted.  

8. Code Changes
---------------
<pre>

Fork: cortlandstarrett/bridgepoint

Branch name: 8923_relocatables

xtuml/bridgepoint
 doc-bridgepoint/notes/8923_relocatables_int.md                              |   67 ++
 src/org.xtuml.bp.core/META-INF/MANIFEST.MF                                  |    1 -
 src/org.xtuml.bp.core/color/ooaofooa_package_spec.clr                       |   70 --
 src/org.xtuml.bp.core/generate.xml                                          |    2 +-
 .../Provided Operation/Provided Operation.xtuml                             |    2 +-
 .../Provided Signal/Provided Signal.xtuml                                   |    2 +-
 .../Required Operation/Required Operation.xtuml                             |    2 +-
 .../Required Signal/Required Signal.xtuml                                   |    2 +-
 .../models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml            |    2 +-
 .../models/org.xtuml.bp.core/ooaofooa/Domain/Function/Function.xtuml        |    2 +-
 .../models/org.xtuml.bp.core/ooaofooa/Functions/Functions.xtuml             |   26 -
 .../models/org.xtuml.bp.core/ooaofooa/State Machine/Action/Action.xtuml     |    2 +-
 .../ooaofooa/Subsystem/Derived Base Attribute/Derived Base Attribute.xtuml  |    2 +-
 .../models/org.xtuml.bp.core/ooaofooa/Subsystem/Operation/Operation.xtuml   |    2 +-
 .../src/org/xtuml/bp/core/relocatables/RelocatableTagConversionUtil.java    |  517 -------------
 .../src/org/xtuml/bp/core/relocatables/RelocatableTagCreationUtil.java      | 1436 -----------------------------------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/relocatables/Relocatables.java  |  159 ----
 src/org.xtuml.bp.io.mdl/arc/gen_uuid_convert_util.arc                       |   22 +-
 .../src/org/xtuml/bp/ui/text/ModelElementPropertyStorage.java               |    5 -
 .../src/org/xtuml/bp/ui/text/activity/ActivityEditor.java                   |  139 +---
 src/org.xtuml.bp.welcome/models/xtUML_Metamodel.xtuml                       |   43 +-
 21 files changed, 90 insertions(+), 2415 deletions(-)

</pre>

End
---

