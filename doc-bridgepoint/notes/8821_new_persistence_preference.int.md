---

This work is licensed under the Creative Commons CC0 License

---

# Add preference to activate/deactivate persistence of actions as files
### xtUML Project Implementation Note

1. Abstract
-----------
Issue #8417 [[2.2]](#2.2) recently introduced a mechanism to store action bodies separately in files apart from the SQL model data. This issue is raised to introduce a preference by which a user can choose to activate this new feature or not. Since #8417 is such a huge paradigm shift, this will be useful in incremental migration and time-testing of the new persistence mechanism.

2. Document References
---------------------- 
<a id="2.1"></a>2.1 [#8821 Add preference to activate/deactivate persistence of actions as files](https://support.onefact.net/issues/8821)  
<a id="2.2"></a>2.2 [#8417 Storing activities as dialect files](https://support.onefact.net/issues/8417)  

3. Background
-------------
None

4. Requirements
---------------
4.1 A preference shall be added to toggle between storing activities with the SQL data and in files  
4.1.1 When enabled, activites shall be stored separately in their own files  
4.1.2 When disabled, activities shall be stored in the `Action_Semantics` field in the SQL data  
4.2 The default of the preference shall be disabled (that is actions shall be stored the "old way")  
4.3 The preference shall not present in the BridgePoint UI, but be configurable  

5. Work Required
----------------
5.1 `BridgePointPreferencesModel.java` and `BridgePointPreferencesStore.java`

Added field to the preferences model for the new activity persistence preference. Added
code and constants to read and set the value in preferences store. Set the default value
such that the new persistence is disabled by default.

5.2 `export_functions.inc`

Added a check of the new preference in two places. The first place is in the `write_*_actions`
routines that are used to write out actions to separate files. This gets skipped unless the persist
as separate files preference is enabled.

The second place is where `Action_Semantics_internal` fields are written as SQL. In the new
persistence scheme, the `Action_Semantics_internal` field is output as empty string (and the
action bodies are printed into separate files). If the new persistence feature is _disabled_,
the activites are still written into the `Action_Semantics_internal` field in the SQL.

5.3 `gen_import_java.inc`

A check of the preference is wrapped around the routine that sets the actions semantics from
separate files.

5.4 `plugin_customization.ini`

Add the default value to be "no_persist_activity_files"

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
7.1 Test old persistence  
7.1.1 Open a test model with action language (GPS Watch or Microwave Oven will work)  
7.1.2 Load and persist (right click, `BridgePoint Utilities` > `Load and Persist`)  
7.1.3 Verify on disk that no `.masl` or `.oal` action files were created  
7.1.4 In the Java perspective, close and reopen the project  
7.1.5 Verify that the action language was loaded properly

7.2 Test new persistence  
7.2.1 Close BridgePoint. Enable persistence in separate files by editing
`org.xtuml.bp.pkg/plugin_customization.ini` such that the line
`org.xtuml.bp.core/bridgepoint_prefs_activity_persistence=no_persist_activity_files`
becomes
`org.xtuml.bp.core/bridgepoint_prefs_activity_persistence=persist_activity_files`  
7.2.2 Restart BridgePoint.  
7.2.3 Load and persist (right click, `BridgePoint Utilities` > `Load and Persist`)  
7.2.4 Verify on disk that action files were created  
7.2.5 Verify that no action language exists in any `.xtuml` file  
7.2.6 In the Java perspective, close and reopen the project  
7.2.7 Verify that the action language was loaded properly

_Testing tip: When manually testing features that must affect persisted files
on disk in very particulary ways, it is often times good to initialize a git
repository with the test models. In this way, it is very easy to see when
`.masl` files are created and when the action language is removed from the
`.xtuml` files._

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint
Branch: 8821_new_persistence_pref

<pre>

 doc-bridgepoint/notes/8821_new_persistence_preference.int.md                        | 116 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java |   3 +++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java |   8 ++++++++
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                   |  10 ++++++++++
 src/org.xtuml.bp.io.core/arc/gen_export_java.inc                                    |   1 +
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                    |  85 +++++++++++++++++++++++++++++++++++++++++++++----------------------------------------
 src/org.xtuml.bp.pkg/plugin_customization.ini                                       |   1 +
 7 files changed, 184 insertions(+), 40 deletions(-)

</pre>

End
---

