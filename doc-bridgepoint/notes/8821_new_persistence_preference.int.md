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

5. Work Required
----------------
5.1 `BridgePointPreferencesModel.java` and `BridgePointPreferencesStore.java`

Added field to the preferences model for the new activity persistence preference. Added
code and constants to read and set the value in preferences store. Set the default value
such that the new persistence is disabled by default.

5.2 `ActionLanguagePreferences.java`

Added UI elements used to set the preference to the "Action Language" preference page.

5.3 `export_functions.inc`

Added a check of the new preference in two places. The first place is in the `write_*_actions`
routines that are used to write out actions to separate files. This gets skipped unless the persist
as separate files preference is enabled.

The second place is where `Action_Semantics_internal` fields are written as SQL. In the new
persistence scheme, the `Action_Semantics_internal` field is output as empty string (and the
action bodies are printed into separate files). If the new persistence feature is _disabled_,
the activites are still written into the `Action_Semantics_internal` field in the SQL.

5.4 `gen_import_java.inc`

A check of the preference is wrapped around the routine that sets the actions semantics from
separate files.

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
7.1 Open a test model with action language  
7.2 Disable persisting actions to separate files in preferences `xtUML` > `Action Language`  
7.3 Load and perisst (right click, `BridgePoint Utilities` > `Load and Persist`)  
7.4 Verify on disk that no `.masl` or `.oal` action files were created  
7.5 In the Java perspective, close and reopen the project  
7.6 Verify that the action language was loaded properly

7.7 Enable persisting actions to separate files in preferences `xtUML` > `Action Language`  
7.8 Load and perisst (right click, `BridgePoint Utilities` > `Load and Persist`)  
7.9 Verify on disk that action files were created  
7.10 Verify that no action language exists in any `.xtuml` file  
7.11 In the Java perspective, close and reopen the project  
7.12 Verify that the action language was loaded properly

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint
Branch: 8821_new_persistence_pref

<pre>

 doc-bridgepoint/notes/8821_new_persistence_preference.int.md
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/ActionLanguagePreferences.java
 src/org.xtuml.bp.io.core/arc/export_functions.inc
 src/org.xtuml.bp.io.core/arc/gen_export_java.inc
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc

</pre>

End
---

