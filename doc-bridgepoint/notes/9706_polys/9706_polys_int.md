---

This work is licensed under the Creative Commons CC0 License

---

# When polymorphic events are changed in a supertype, reflect the change in the subtype(s)
### xtUML Project Implementation Note

### 1. Abstract

See [[2.2]](#2.2) section 1

### 2. Document References

<a id="2.1"></a>2.1 [#9706 When polymorphic events are changed in a supertype, reflect the change in the subtype(s)](https://support.onefact.net/issues/9706)  
<a id="2.2"></a>2.2 [#9706 design note](9706_polys_dnt.md)  

### 3. Background

See [[2.2]](#2.2) section 3

### 4. Requirements

See [[2.2]](#2.2) section 4

### 5. Work Required

The "Design" and "Analysis" sections of the design note adequately cover the
changes that have been made and no more information is necessary here.

### 6. Implementation Comments

None.

### 7. Unit Test

See [[2.2]](#2.2) section 9

### 8. User Documentation

See [[2.2]](#2.2) section 8

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9706_polys  

<pre>

 doc-bridgepoint/notes/9706_polys/9706_polys_dnt.md                                                                                 | 238 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9706_polys/9706_polys_int.md                                                                                 |  61 ++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9706_polys/events.png                                                                                        | Bin 0 -> 1180290 bytes
 src/MC-Java/referred_to.inc                                                                                                        |  28 ++++++++++++++++---
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/Package.xtuml                                    |   5 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/Non Local Event/Non Local Event.xtuml                        |  33 ++++++++++++++++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/SEM Event/SEM Event.xtuml                                    |  49 +++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml                | 128 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine State/State Machine State.xtuml                |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine/State Machine.xtuml                            |  50 ++++++++++++++++++++++++++++++++--
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/Transition/Transition.xtuml                                  |  18 +++++++++++--
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Model Class/Model Class.xtuml                                    |  46 +++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java                                                |   6 +++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java                                                |   8 ++++++
 src/org.xtuml.bp.doc/Reference/MASL/MASLConversionGuide/MASLConversionGuide.html                                                   |  21 ---------------
 src/org.xtuml.bp.doc/Reference/MASL/MASLConversionGuide/MASLConversionGuide.md                                                     |  27 -------------------
 src/org.xtuml.bp.doc/Reference/MASL/MASLConversionGuide/images/image05.png                                                         | Bin 15815 -> 0 bytes
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                                                                  |   4 +--
 src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io/File IO/Alternate Export Child Link/Alternate Export Child Link.xtuml |  18 +++++++++++++
 src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io/File IO/File IO.xtuml                                                 |   4 +--
 src/org.xtuml.bp.io.mdl/sql/stream.pei.sql                                                                                         |   4 +--
 src/org.xtuml.bp.pkg/plugin_customization.ini                                                                                      |   2 ++
 src/org.xtuml.bp.ui.explorer/arc/create_adapters.inc                                                                               |   9 +++++--
 src/org.xtuml.bp.ui.explorer/generate.xml                                                                                          |   6 +++--
 src/org.xtuml.bp.ui.explorer/sql/UITree.pei.sql                                                                                    |   2 +-
 src/org.xtuml.bp.ui.sem/src/org/xtuml/bp/ui/sem/pages/SEMEditorPage.java                                                           |   3 ++-
 26 files changed, 685 insertions(+), 87 deletions(-)

</pre>

Fork/Repository: leviathan747/mc  
Branch: 9706_polys  

<pre>

 model/maslin/models/maslin/m2x/ooapopulation/ooapopulation.xtuml  | 380 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++------------------------
 model/maslout/models/maslout/lib/xtuml2masl/maslout/maslout.xtuml |  53 +++++++++-----------------------
 2 files changed, 355 insertions(+), 78 deletions(-)

</pre>

### End

