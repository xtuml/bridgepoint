---

This work is licensed under the Creative Commons CC0 License

---

# Refining of persistence as activity files
### xtUML Project Implementation Note

1. Abstract
-----------
While being pressed for time, the work for the new persistence mechanism was
promoted while not being completely finished. The remaining issues needed to
either be fixed or follow-on issues need to be raised. The code needs to be
cleaned up.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8887 Clean up new persistence mechanism for activities](https://support.onefact.net/issues/8887) -- Parent issue  
<a id="2.2"></a>2.2 [#8917 Fully support persistence as activity files for OAL](https://support.onefact.net/issues/8917)  
<a id="2.3"></a>2.3 [#8918 Fix import parser in the io plugin](https://support.onefact.net/issues/8918)  
<a id="2.4"></a>2.4 [#8919 NumberFormatException when loading pre-dialect attribute models with proxies](https://support.onefact.net/issues/8919)  
<a id="2.5"></a>2.5 [#8920 Update Dialect attribute usage in the MC projects](https://support.onefact.net/issues/8920)  

3. Background
-------------
Here are the issues that were present at the last promotion:

1. Different dialects were not supported. All activities were persisted into
  `.masl` files. This needed to be updated so that the persistence mechanism
  properly multiplexed/demultiplexed the activities into the right files based
  on the dialect at the activity granularity.  
2. Not every type of activity is supported. Only the activities used by MASL
  are fully supported. Because of this, persistence as files will be limited to
  the MASL dialect  
3. The import parser has problems that cause it to mishandle comments. The parser
  needs to be revisited to avoid action language corruption.
4. Default dialect behavior was half-baked. New activities were dialect-less,
  editor selecting code was broken. This needed to be changed so that at
  creation, activities were given the default dialect, and then the editor is
  chosen based on the dialect of the selected activity.
5. Dialect strings were floating around the source code. The available dialects
  needed to be gathered into one location (enumeration). This also allows
  dialect setting through the "Properties" view to be a drop-down menu and not a
  free form text field.
6. `ActionFile`, `ImportFactory`, and `ExportFactory` classes had outdated APIs
  that assume dialect is at the PMC (package, component, class) granularity.
  These APIs needed to be cleaned up.

4. Requirements
---------------
4.1 Activity dialect  
4.1.1 Activity dialect shall be settable via drop-down menu in the "Properties"
view  
4.1.2 Activity dialect shall be set to the default when a new activity is
created  
4.1.3 Activity dialects shall be maintained in one place  
4.1.3.1 An xtUML enumeration type shall be created for this purpose  
4.1.3.2 All references to hard coded string values for dialect shall be
removed/replaced  

4.2 Persistence of files  
4.2.1 Activities with different dialects shall be persisted in different files
marked by `.<dialect>` extension  
4.2.2 Activities shall be parsed from many files  
4.2.3 Parsed activities shall be assigned their dialect based on the file they
are parsed from  
4.2.4 Activities containing co-existing action language in multiple dialects
shall not be supported in persistence  
4.2.5 A restriction shall be added to limit this persistence to the MASL dialect
only  

4.3 Sufficient code cleanup shall be done in the persistence change  
4.4 Issues shall be raised for problems not addressed in this changeset  

5. Work Required
----------------
5.1 An enumerated type `ActionDialect` was added to the OOA of OOA. Enumerators
`oal` and `masl` were introduced.  
5.2 Each class with the `Dialect` attribute was updated for the type to be
`ActionDialect`. The schema was updated in MC-Java accordingly.  
5.2 Changed the type in all the preferences code to match the new type of the
action dialect.  
5.3 Added code to the initializers of classes with `Dialect` attributes so that
the dialect is set to the default preference on creation.  
5.4 Updated import and export classes to persist and import from different
dialect files.  
5.5 Cleaned up the `ActionFile` class with the new changes.  
5.6 Updated references to the changed APIs across the code base.  
5.7 Added a restriction to only let MASL files be imported and persisted as
files.  

6. Implementation Comments
--------------------------

6.1 It may seem as though requirement 4.2.5 contradicts the rest of 4.2. Why
would the implementation be able to read and write to many different dialect
files if we are simply going to restrict it to one anyway. The reason is because
the restriction is meant to be a temporary safeguard. OAL activities are not
fully supported, so in the interest of making this a promotable change, the
restriction maintains OAL in the well tested persistence environment. When more
time/occasion arises to extend this work to fully support `.oal` files, the
infrastructure will be in place.

6.2 Remaining issues  

6.2.1 As mentioned above, problem 2 in the "Background" section is not being handled
in this work. Issue #8917 [[2.2]](#2.2) is raised.

6.2.2 Problem 3 in the "Background" section is also not handled as part of this
work. Issue #8918 [[2.3]](#2.3) is raised.

6.2.3 During this work I uncovered another bug having to do with the import of
old models (pre dialect attribute) and the proxy objects of dialect-having
classes. I observed this first when loading the "GPS Watch" model. A
`NumberFormatException` is raised when loading a provided signal proxy because the proxy path is supposed to be the
dialect attribute and is parsed as an integer. I believe this bug was missed
before because dialect was a string also. Issue #8919 [[2.4]](#2.4) is raised.

6.2.4 Dialect attributes are still strings in the MC projects. m2x, masl, and
x2m need to update any references to dialect to match the enumeration in the
OOA of OOA. Issue #8920 [[2.5]](#2.5) has been raised.

7. Unit Test
------------

7.1 Create a test model.  
7.2 Set the "Default Action Language Dialect" to OAL.  
7.3 Create two functions in a new package. Verify that the dialect is OAL for
both in the Properties view.  
7.4 Double click on one of the functions. Verify that the OAL editor is opened.  
7.5 Edit both functions and save. Verify that the action language appears in
the `.xtuml` file in the workspace.  
7.6 Use the drop-down menu in the Properties view to change the dialect of one
function to MASL. Verify that `<package_name>.masl` has been created in the
workspace and that the action language is correct.  
7.7 Double click the function. Verify that the MASL editor is opened.  
7.8 Switch to the Java perspective, close and reopen the project. Open both
functions and verify that the action language is correct.  
7.9 Use the drop-down menu to change the other function to the MASL dialect.
Verify that both functions exist int the `<package_name>.masl` file and that all
action language is gone from the `.xtuml` file.  
7.10 Set the "Default Action Language Dialect" to MASL.  
7.11 Create a third function and verify that the dialect is MASL.

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 8887_persistence_cleanup  

<pre>

 src/MC-Java/ooa_schema.sql                                                                                                                       |  18 +++++++++---------
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/MergeWorkbenchAdvisor.java                                                                             |   4 +---
 src/org.xtuml.bp.core/arc/create_core_plugin_class.arc                                                                                           |   4 +---
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Provided Operation/Provided Operation.xtuml |  12 ++++++++++--
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Provided Signal/Provided Signal.xtuml       |  12 ++++++++++--
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Required Operation/Required Operation.xtuml |  12 ++++++++++--
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Required Signal/Required Signal.xtuml       |  12 ++++++++++--
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Datatypes/Datatypes.xtuml                                                                |  52 ++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml                                                               |  10 +++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Function/Function.xtuml                                                           |  10 +++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/External Entities/External Entities.xtuml                                                |  30 ++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/import_functions/import_functions.xtuml                                        |  19 +++++++++++++------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/Action/Action.xtuml                                                        |   9 ++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine State/State Machine State.xtuml                              |   1 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/Transition/Transition.xtuml                                                |   1 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/Attribute.xtuml                                                      |   1 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Derived Base Attribute/Derived Base Attribute.xtuml                            |   9 ++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Operation/Operation.xtuml                                                      |  10 +++++++++-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ActionFile.java                                                                               | 127 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++----------------------------------------------------------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java                                                              |   2 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java                                                              |  12 +++++-------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentResourceListener.java                                                                |   2 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java                                                                |  15 +++++++--------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistenceManager.java                                                                       |   2 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/AbstractModelExportFactory.java                                                                   |   7 +++----
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/AbstractModelImportFactory.java                                                                   |   2 --
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/ActionLanguagePreferences.java                                                        |  11 ++++++-----
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                                                                                |  54 +++++++++++++++++++++++++++++++++++++++++++++++++-----
 src/org.xtuml.bp.io.core/arc/gen_export_java.inc                                                                                                 | 132 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--------------------------------------------------------------
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                                                                 |  18 +++++++++---------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImport.java                                                                                |  18 ++++--------------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g                                                                                      |   4 ++--
 src/org.xtuml.bp.io.mdl.test/src/IOMdlTestGenerics.java                                                                                          |   6 +++---
 src/org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/ExportModelFactory.java                                                                          |  12 +++++-------
 src/org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/ImportModelFactory.java                                                                          |   4 ----
 src/org.xtuml.bp.model.compare.test/arc/generate_metamodel_compare_test.arc                                                                      |   2 ++
 src/org.xtuml.bp.model.compare/src/org/xtuml/bp/model/compare/contentmergeviewer/ModelContentMergeViewer.java                                    |   2 +-
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasTest.java                                                                  |   2 +-
 src/org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc                                                                                        |  26 ++++++++------------------
 src/org.xtuml.bp.ui.properties.test/src/org/xtuml/bp/ui/properties/test/EnumRangeTest.java                                                       |   6 +++++-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/masl/MASLEditorInput.java                                                                      |  24 ------------------------
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/masl/MASLEditorInputFactory.java                                                               |  34 +++++++++++++++++++++++++---------
 42 files changed, 469 insertions(+), 281 deletions(-)

</pre>

End
---
