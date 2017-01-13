---

This work is licensed under the Creative Commons CC0 License

---

# MASL type editor
### xtUML Project Implementation Note

1. Abstract
-----------
XML tags make type definitions messy and inconvenient to manage, leading to
erroneous type definitions which can cause export failures.  This issue is
raised to implement a better way to edit complex types.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8741 Type definitions are messy](https://support.onefact.net/issues/8741) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint v6.0 Release Planning](https://docs.google.com/document/d/1t7JuVMDwOcojUW8QqXamBKUtcZpBJbxdOfDiWdZZNKo/edit) Internal release planning document  
<a id="2.3"></a>2.3 [#8988 Add validation functionality to type editing for MASL](https://support.onefact.net/issues/8988)  
<a id="2.4"></a>2.4 [#8518 Supply early documentation on idiom for types and typerefs to customer](https://support.onefact.net/issues/8518)  

3. Background
-------------
In a meeting to discuss the 6.0 release (see [[2.2]](#2.2)), the One Fact team
discussed requirements for a new way to edit MASL type definitions.

Currently types are defined within XML tags in the description field of the
type. This is unacceptable and must be changed.

During the meeting three levels of requirements were established (represented by
4.1-4.3 below). A design has been chosen that satisfies requirement 4.1, but
more work will need to be done to satisfy requirements 4.2 and 4.3.

4. Requirements
---------------
4.1 Eliminate reliance on XML tags  
4.1.1 No longer use the description field to hold type definitions  
4.2 Validate syntax of MASL type definitions  
4.3 Validate MASL references in MASL type definitions  

5. Design
---------

5.1 Definition field

A new string attribute `Definition` shall be added to User Data Type (`S_UDT`)
in the OOA of OOA. This will be the location we will store complex MASL type
definitions.

5.2 `m2x` and `x2m`

The MASL convert and export tools will be updated to store complex MASL type
definitions in this new definition field instead of the description field with
XML tags.

5.3 User experience

The pattern of the description field will be copied in BridgePoint to provide an
editing experience for this new field. The type definition editor shall be the
default editor (double click) for User Data Types.

6. Work Required
----------------

6.1 Add `Definition` to `S_UDT` in the OOA of OOA; update schema  
6.2 Add upgrade code to handle old models that have no definition fields  
6.3 Add necessary files and make changes to the `ui.text` plugin to create a new
Type Definition Editor  
6.4 Modify properties plug-in to display and open the definition for editing
from the properties pane  

6.5 Add `Definition` to `S_UDT` in the OOA of OOA (mc repository); update schema  
6.6 Make necessary changes to `m2x` and `x2m` to utilize the new definition
field; update binaries  

7. Implementation Comments
--------------------------

7.1 Future work

We would like to use an embedded Xtext editor to accomplish requirements 4.2 and
4.3. An issue has been raised for that work [[2.3]](#2.3)

8. Unit Test
------------

8.1 MASL round trip test.  
8.1.1 After import, double click on `group_type` in the `types` package (`SAC` project)  
8.1.2 Verify that it is opened in a separate editor and is editable separate
from the description  

9. User Documentation
---------------------

See [[2.4]](#2.4)

10. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 8741_type_editor  

<pre>

 src/MC-Java/ooa_schema.sql                                                                          |   3 ++-
 src/org.xtuml.bp.core/icons/edit_tdf.gif                                                            | Bin 0 -> 379 bytes
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/User Data Type/User Data Type.xtuml  |  18 +++++++++++++++
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                    |   3 +++
 src/org.xtuml.bp.ui.properties/arc/BuildPropertySource.arc                                          |   4 +++-
 src/org.xtuml.bp.ui.properties/generate.xml                                                         |   2 +-
 src/org.xtuml.bp.ui.properties/src/org/xtuml/bp/ui/properties/TypeDefinitionPropertyDescriptor.java | 100 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.text/META-INF/MANIFEST.MF                                                       |   3 ++-
 src/org.xtuml.bp.ui.text/arc/create_editorinput_factories_java.arc                                  |   4 +++-
 src/org.xtuml.bp.ui.text/arc/create_plugin_xml.arc                                                  |  50 +++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.text/arc/create_typedefinition_editorinput_factory_java.inc                     | 101 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.text/arc/create_util.inc                                                        |   4 ++++
 src/org.xtuml.bp.ui.text/generate.xml                                                               |  13 ++++++-----
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/AbstractModelElementTextEditor.java               |  13 ++++++-----
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/DocumentProvider.java                             |  12 ++++++++++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/EditorHover.java                                  |   3 +++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/IUITextHelpContextIds.java                        |   1 +
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/placeholder/PlaceHolderEntry.java                 |   5 ++++-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/placeholder/PlaceHolderManager.java               |   3 ++-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/typedefinition/.gitignore                         |   1 +
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/typedefinition/ShowTypeDefinitionAction.java      |  60 +++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/typedefinition/TypeDefinitionEditor.java          | 235 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/typedefinition/TypeDefinitionEditorInput.java     |  71 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 23 files changed, 692 insertions(+), 17 deletions(-)

</pre>

Fork/Repository: leviathan747/mc  
Branch: 8741_type_definitions  

<pre>

 bin/m2x                                                                      | Bin 646657 -> 646657 bytes
 bin/x2m                                                                      | Bin 680533 -> 680533 bytes
 model/maslin/gen/masl2xtuml.c                                                |  30 +++++++++++++++++++++---------
 model/maslin/gen/masl2xtuml_S_UDT_class.c                                    |  13 ++++++++-----
 model/maslin/gen/masl2xtuml_ooapopulation_class.c                            |  20 ++++++++++----------
 model/maslin/models/maslin/lib/masl2xtuml/masl2xtuml.xtuml                   |   5 +++--
 model/maslin/models/maslin/m2x/ooapopulation/ooapopulation.xtuml             |   9 ++++-----
 model/maslout/models/maslout/lib/xtuml2masl/maslout/maslout.xtuml            |  29 ++++++++---------------------
 model/mcooa/models/mcooa/ooaofooa/Domain/User Data Type/User Data Type.xtuml |  18 ++++++++++++++++++
 schema/sql/xtumlmc_schema.sql                                                |   3 ++-
 10 files changed, 74 insertions(+), 53 deletions(-)

</pre>

End
---

