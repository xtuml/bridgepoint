---

This work is licensed under the Creative Commons CC0 License

---

# Storing action semantics as files
### xtUML Project Implementation Note

1. Abstract
-----------
See [[2.2]](#2.2) section 1

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8417 Storing activities as dialect files](https://support.onefact.net/issues/8417) -- This is the parent issue  
<a id="2.2"></a>2.2 [#8417 design note](8417_action_dialect_files.dnt.md)  

3. Background
-------------
See [[2.2]](#2.2) section 3

4. Requirements
---------------
See [[2.2]](#2.2) section 4

5. Work Required
----------------

5.1 Action file core architecture  
5.1.1 Create new class `ActionFile` to maintain individual action dialect files  
5.1.2 Add an `ActionFile` instance as a member of the
`PersistableModelComponent` class  
5.1.3 Update `PersistableModelComponent` and `PersistenceManager` classes to
maintian the `ActionFile` instance  
5.1.4 Modify `ComponentResourceListener` to listen for addition, deletion,
move/rename of action files and reload the PMC accordingly.  
5.1.5 File list associated with this change category  
```
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ActionFile.java                                            | 148 +++++++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java                             |  26 +++-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistenceManager.java                                    |   2 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentResourceListener.java                             |  55 ++++++++-
```

5.2 Import changes  
5.2.1 Add grammar to parse labeled action bodies (see [[2.2]](#2.2) section
6.3)  
5.2.2 Update `ant` script to generate this grammar and update `.gitignore` to
ignore files generated from the grammar  
5.2.3 Add a parameter to the routines used to create importers for the action
file. Updated in all appropriate places.  
5.2.4 Update `CoreImport` with a routine to read action files and invoke the new
action file parser  
5.2.5 Update the template for the import classes with the routine to search for
an activity instance and insert action language  
5.2.6 File list associated with this change category  
```
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/AbstractModelImportFactory.java                                |   2 +
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                              |  68 ++++++++++-
 src/org.xtuml.bp.io.core/generate.xml                                                                         |  36 +++++-
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/.gitignore                                                  |   6 +
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImport.java                                             | 105 ++++++++++++++++-
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g                                                   |  80 +++++++++++++
 src/org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/ImportModelFactory.java                                       |   4 +
```

5.3 Export changes  
5.3.1 Add a parameter to the routines used to create exporters for the action
file. Updated in all appropriate places.  
5.3.2 Add "Action Body" to the File IO model  
5.3.3 Update `class2table.arc` to generate instances of "Action Body" from the
OOA of OOA  
5.3.4 Update the export templates with routines to write out action bodies into
separate files where instances of "Action Body" exist  
5.3.5 File list associated with this change category  
```
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/AbstractModelExportFactory.java                                |   9 +-
 src/org.xtuml.bp.io.core/arc/class2table.arc                                                                  |  23 +++-
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                                             |  53 ++++++++-
 src/org.xtuml.bp.io.core/arc/gen_export_java.inc                                                              |  90 ++++++++++++--
 src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io/File IO/Action Body/Action Body.xtuml            | 137 +++++++++++++++++++++
 src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io/File IO/File IO.xtuml                            | 196 +++++++++++++++++++++++++++++-
 src/org.xtuml.bp.io.mdl/arc/gen_stream_export.arc                                                             |   4 +-
 src/org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/ExportModelFactory.java                                       |  12 +-
```

5.4 Preference changes  
5.4.1 Update preference model and preference store with new name of preference  
5.4.2 Add the UI code to change the preference in `ActionLanguagePreferences`
class  
5.4.3 Remove preference page from the ui.text plugin  
5.4.4 Update reference to the preference in `ExplorerView`  
5.4.5 File list associated with this change category  
```
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java                           |   6 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java                           |  13 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/ActionLanguagePreferences.java                     |  45 ++++++-
 src/org.xtuml.bp.ui.text/arc/create_plugin_xml.arc                                                            |   6 -
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/editor/preference/DefaultActivityEditorPreferencesPage.java | 145 -----------------------
 src/org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc                                                     |   2 +-
```

5.5 Updated signature in places where importers or exporters are created  
5.5.1 File list associated with this change category  
```
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/MergeWorkbenchAdvisor.java                                          |   4 +-
 src/org.xtuml.bp.core/arc/create_core_plugin_class.arc                                                        |   6 +-
 src/org.xtuml.bp.io.mdl.test/src/IOMdlTestGenerics.java                                                       |   6 +-
 src/org.xtuml.bp.model.compare/src/org/xtuml/bp/model/compare/contentmergeviewer/ModelContentMergeViewer.java |   2 +-
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasTest.java                               |   2 +-
```

5.6 Documentation  
5.6.1 Added design note and visuals  
5.6.2 Added sections from the design note to the FAQ  
5.6.3 File list associated with this change category  
```
 doc-bridgepoint/notes/8417_action_dialect_files/8417_action_dialect_files.dnt.md                              | 386 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/8417_action_dialect_files/diagram.jpg                                                   | Bin 0 -> 1890498 bytes
 doc-bridgepoint/notes/8417_action_dialect_files/fileio.png                                                    | Bin 0 -> 50772 bytes
 doc-bridgepoint/process/FAQ.md                                                                                |  38 ++++++
 doc-bridgepoint/process/fileio.png                                                                            | Bin 0 -> 50772 bytes
 doc-bridgepoint/review-minutes/8417_action_dialect_files.dnt.rvm.md                                           |  38 ++++++
```

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
See [[2.2]](#2.2) section 9

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 8417_action_dialect_files_2  

<pre>

 doc-bridgepoint/notes/8417_action_dialect_files/8417_action_dialect_files.dnt.md                              | 386 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/8417_action_dialect_files/diagram.jpg                                                   | Bin 0 -> 1890498 bytes
 doc-bridgepoint/notes/8417_action_dialect_files/fileio.png                                                    | Bin 0 -> 50772 bytes
 doc-bridgepoint/process/FAQ.md                                                                                |  38 ++++++
 doc-bridgepoint/process/fileio.png                                                                            | Bin 0 -> 50772 bytes
 doc-bridgepoint/review-minutes/8417_action_dialect_files.dnt.rvm.md                                           |  38 ++++++
 src/org.xtuml.bp.cli/src/org/xtuml/bp/cli/MergeWorkbenchAdvisor.java                                          |   4 +-
 src/org.xtuml.bp.core/arc/create_core_plugin_class.arc                                                        |   6 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ActionFile.java                                            | 148 +++++++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java                           |   6 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java                           |  13 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentResourceListener.java                             |  55 ++++++++-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java                             |  26 +++-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistenceManager.java                                    |   2 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/AbstractModelExportFactory.java                                |   9 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/AbstractModelImportFactory.java                                |   2 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/ActionLanguagePreferences.java                     |  45 ++++++-
 src/org.xtuml.bp.io.core/arc/class2table.arc                                                                  |  23 +++-
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                                             |  53 ++++++++-
 src/org.xtuml.bp.io.core/arc/gen_export_java.inc                                                              |  90 ++++++++++++--
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                              |  68 ++++++++++-
 src/org.xtuml.bp.io.core/generate.xml                                                                         |  36 +++++-
 src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io/File IO/Action Body/Action Body.xtuml            | 137 +++++++++++++++++++++
 src/org.xtuml.bp.io.core/models/org.xtuml.bp.io.core/file_io/File IO/File IO.xtuml                            | 196 +++++++++++++++++++++++++++++-
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/.gitignore                                                  |   6 +
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImport.java                                             | 105 ++++++++++++++++-
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g                                                   |  80 +++++++++++++
 src/org.xtuml.bp.io.mdl.test/src/IOMdlTestGenerics.java                                                       |   6 +-
 src/org.xtuml.bp.io.mdl/arc/gen_stream_export.arc                                                             |   4 +-
 src/org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/ExportModelFactory.java                                       |  12 +-
 src/org.xtuml.bp.io.mdl/src/org/xtuml/bp/io/mdl/ImportModelFactory.java                                       |   4 +
 src/org.xtuml.bp.model.compare/src/org/xtuml/bp/model/compare/contentmergeviewer/ModelContentMergeViewer.java |   2 +-
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasTest.java                               |   2 +-
 src/org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc                                                     |   2 +-
 src/org.xtuml.bp.ui.text/arc/create_plugin_xml.arc                                                            |   6 -
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/editor/preference/DefaultActivityEditorPreferencesPage.java | 145 -----------------------
 36 files changed, 1541 insertions(+), 214 deletions(-)

</pre>

End
---

