---

This work is licensed under the Creative Commons CC0 License

---

# Add editor elements for Exceptions
### xtUML Project Implementation Note

1. Abstract
-----------
As part of the MASL work, we added a new first class model element _Exception_
(key letters _S_EXP_) to the OOA of OOA. Exception is a packageable element. It
can be created, renamed, and deleted within a package. Exceptions have no child
elements. This noted documents the work required to enable a user to edit
Exceptions in BridgePoint.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8544 Add UI elements for exceptions](https://support.onefact.net/issues/8544)  
<a id="2.2"></a>2.2 [#8542 design note](8442_build_bp_pyrsl/8442_build_bp_pyrsl_dnt.md)  

3. Background
-------------
MASL models allow definition of exceptions. Exceptions are defined by MASL at
the domain model level. Exceptions can be raised by MASL activities and handlers
can be defined for exceptions for each activity. To fully support MASL models,
BridgePoint must provide edit capabilities for exceptions.

4. Requirements
---------------
4.1 A user shall have the ability to create an exception  
4.1.1 A palette tool on the canvas for a package  
4.1.2 A CME on the canvas for a package  
4.1.3 A CME in the model explorer on a package  

4.2 A user shall have the ability to rename an exception  
4.2.1 A CME on the exception from the canvas  
4.2.2 A CME on the exception from the model explorer  

4.3 A user shall have the ability to delete an exception  
4.3.1 A CME on the exception from the canvas  
4.3.2 A CME on the exception from the model explorer  

4.4 The exception shall have an appropriate icon in the palette and model
explorer  
4.5 The auto-reconciler shall create/delete graphics for the exception when it
is created/deleted from the model explorer  

5. Work Required
----------------

5.1 CMEs

5.1.1 Added PEI data for CMEs for delete and rename on exceptions  
5.1.2 Added PEI data for CMEs for new exceptions on packages  
5.1.3 Added various changes to support menus and category for exceptions  

Commits for this category:
```
e40896278385b9a7e835feb256924f46d0dea608 job #8544 added archetype changes to support exceptions context menus
e0747543d9abd7d7398bc247da10b931960ccc83 job #8544 added PEI data for new context menu entries
8e91b5188d945a146deb0c6bbcc9070202fa3df3 job #8544 added missing menu bits for exceptions
```

5.2 Persistence

5.2.1 Added PEI data in both `file_io.pei.sql` and `stream.pei.sql` for
exception persistence. Exceptions have one child (packageable element)  

Commits for this category:
```
43b20c104d151674a8ca026e977698542027c341 job #8544 added PEI data for Exception persistence
```

5.3 Meta-model supporting operations

5.3.1 Added `Descrip` attribute to Exception.  
5.3.2 Added "Full Name" field to description of `Descrip` and `Name` (for
properties).  
5.3.3 Added operations `get_compartment_text`, `get_compartments`,
`get_entries`, `get_ooa_id`, `get_style`, `get_text_style`,  and `dispose` to
Exception. The underscored operations are used in graphics reonciliation.
`dispose` is a standard tear down function that follows the same pattern as
other packageable elements.  
5.3.4 Added operations `newException`, `getExceptionCount` and `getExceptionId`
to Package.  `newException` is used for creation, the others are used in
graphics reconciliation.  
5.3.5 Added functions `S_EXP_Delete`, `S_EXP_Rename`, and
`EP_PKG_ExceptionsException` to context menu entry functions. These are called
through a special API (see the CME model in org.xtuml.bp.core).  
5.3.6 Added a case in Packageable Element `dispose` to call Exception `dispose`.  
5.3.7 Added an enumeration for Exception in `OOAType` in the OOA of Graphics and
`ElementTypeConstants` in the OOA of OOA.  

Commits for this category:
```
37f379c18d9682328b163d0ed14787811fb8f045 job #8544 added model changes (enumerations, operations, attributes) to support exceptions
```

5.4 Graphics/auto-reconciler

5.4.1 Add a symbol entry for exception in `graphics/build.xml`.  
5.4.2 Add a valid symbol within package in `graphics/build.xml`. This allows the
palette to show exceptions when a package diagram is open.  
5.4.3 Add an auto-reconciliation specification for exception.  
5.4.4 Add a case for exception in `Cl_c.Getinstancefromooa_id`.  

Commits for this category:
```
92154734d35dd7e50f3ef735a74ed2adae741a41 job #8544 added code and PEI data for exception graphics and autoreconciliation
```

5.5 OOA hierarchy

5.5.1 Add PEI data for exception in `core/sql/ooaofooa_hierarchy.pei.sql`  
5.5.2 Add PEI data for exception in `explorer/sql/UITree.pei.sql`  
5.5.3 Note: The data I added in both places was almost identical. I am not sure
why both files are necessary  

Commits for this category:
```
e6114f9da3fde84351b3012ca804ebbb6e67c555 job #8544 added PEI data for exceptions in the tree
```

5.6 OOA schema

5.6.1 Hand added `S_EXP` to the OOA of OOA schema. This was necessary because
the schema generation was disabled in the build when `pyrsl` was integrated.
[[2.2]](#2.2) section 6.4  

Commits for this category:
```
95c61bd3382acb474c5333476fa90a8e620bd4cb job #8544 added Exception to the MC-Java ooaofooa schema
```

5.7 Icons

5.7.1 Added icon for exception  
5.7.2 Added icon for new exception  

Commits for this category:
```
eabecdc9bb06b616a2ed28ea97de7a57adedf41e job #8544 added Exception icons
```

5.8 Properties test

5.8.1 Added test exception data for properties test  

Commits for this category:
```
4d5c7cce7429176f4d4171306bb70798d5d6be3a job #8544 added properties test data for Exceptions
```

5.9 Misc

5.9.1 Added entries to gitignores. This was necessary because new CME action
files were generated and our gitignore files have an exhaustive list of files to
ignore  

Commits for this category:
```
e81973975dc40fc7f646f2ee2426e376748f2c1c job #8544 added .gitignore s for new exception CME classes
```

6. Implementation Comments
--------------------------

It should be noted that this implementation is a good prototype of the changeset
necessary to add a brand new first order model element, complete with graphics,
CMEs, and persistence. This work can be used as a guide to add new model
elements. 

7. Unit Test
------------

7.1 Verify that an exception can be created  
7.1.1 Create an exception on the palette on the canvas for a package   
7.1.2 Create an exception from the CME on the canvas for a package  
7.1.3 Create an exception from the CME in the model explorer on a package  

7.2 Verify that an exception can be renamed  
7.2.1 Rename an exception from the CME on the exception from the canvas  
7.2.2 Rename an exception from the CME on the exception from the model explorer  

7.3 Verify that an exception can be deleted  
7.3.1 Delete an exception from the CME on the exception from the canvas  
7.3.2 Delete an exception from the CME on the exception from the model explorer  

7.4 Verify in all cases that the auto-reconciler appropriately updates graphics
for create, delete, and rename actions  
7.5 Copy and paste an exception into a new package. Verify that the graphics are
created and the new exception can be seen in the model explorer  
7.6 Copy and paste a package containing an exception. Verify that the graphics
are created and the new exception can be seen in the model explorer  
7.7 Export a model containing exceptions. Re-import the model and verify that
the exceptions are in the right places and the graphics are present  
7.8 Create an exception, exit BridgePoint, restart BridgePoint and verify that
the exception was loaded properly  

8. Code Changes
---------------
Branch name: 8544_exceptions

<pre>

 src/MC-Java/ooa_schema.sql                                                                                       |   5 ++
 src/org.xtuml.bp.core/arc/create_core_plugin.inc                                                                 |   2 +
 src/org.xtuml.bp.core/arc/function_body.inc                                                                      |   6 +-
 src/org.xtuml.bp.core/icons/metadata/Exception.gif                                                               | Bin 0 -> 350 bytes
 src/org.xtuml.bp.core/icons/newexp.gif                                                                           | Bin 0 -> 583 bytes
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Datatypes/Datatypes.xtuml                                |   6 ++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Exception/Exception.xtuml                         | 198 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/Package.xtuml                  |  60 +++++++++++++++++
 .../models/org.xtuml.bp.core/ooaofooa/Functions/Context Menu Entry Functions/Context Menu Entry Functions.xtuml  |  63 ++++++++++++++++++
 .../models/org.xtuml.bp.core/ooaofooa/Packageable Element/Packageable Element/Packageable Element.xtuml          |   5 ++
 src/org.xtuml.bp.core/sql/context_menu.pei.sql                                                                   |   3 +
 src/org.xtuml.bp.core/sql/ooaofooa_hierarchy.pei.sql                                                             |   2 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/inspector/.gitignore                                                 |   1 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/.gitignore                                                        |   1 +
 src/org.xtuml.bp.io.mdl/sql/file_io.pei.sql                                                                      |   7 +-
 src/org.xtuml.bp.io.mdl/sql/stream.pei.sql                                                                       |   3 +-
 src/org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/Datatypes/Datatypes.xtuml                 |   6 ++
 src/org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/Cl_c.java                                                  |  20 ++++++
 src/org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/ModelContentOutlinePage.java                               |   7 +-
 src/org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc                                                        |   5 ++
 src/org.xtuml.bp.ui.explorer/arc/create_mon_explorer_view.inc                                                    |   5 ++
 src/org.xtuml.bp.ui.explorer/sql/UITree.pei.sql                                                                  |   2 +
 src/org.xtuml.bp.ui.graphics/plugin.xml                                                                          |  21 ++++++
 src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/outline/GraphicalOutlinePage.java                      |   5 ++
 src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/providers/CanvasEditorContextMenuProvider.java         |   5 ++
 src/org.xtuml.bp.ui.properties.test/models/org.xtuml.bp.ui.properties.test/testProp/Exceptions/Exceptions.xtuml  |  88 +++++++++++++++++++++++++
 src/org.xtuml.bp.ui.properties.test/models/org.xtuml.bp.ui.properties.test/testProp/testProp.xtuml               |  50 +++++++++++----
 src/org.xtuml.bp.ui.session/arc/create_SessionExplorer_view.inc                                                  |   5 ++
 28 files changed, 560 insertions(+), 21 deletions(-)

</pre>

9. Code Review
--------------
9.1 Update release notes issue in Redmine to call out that this feature needs to be included in the notes  
9.2 Raise an issue, why are there 2 UITree models  

End
---

