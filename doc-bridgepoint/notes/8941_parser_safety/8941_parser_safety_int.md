---

This work is licensed under the Creative Commons CC0 License

---

# MASL Activity Parser Safety
### xtUML Project Implementation Note

1. Abstract
-----------
See [[2.2]](#2.2) section 1

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8941 Updating masl and editing parameters can result in masl being deleted](https://support.onefact.net/issues/8941) Example of the problem  
<a id="2.2"></a>2.2 [#8941 design note](8941_parser_safety_dnt.md)

3. Background
-------------
See [[2.2]](#2.2) section 3

4. Requirements
---------------
See [[2.2]](#2.2) section 4

Requirement 4.3 has been stricken from the list of requirements. Because MASL
export is a repeatable operation that does not compromise source data, it is not
worth the cost to insert the safety check.

5. Work Required
----------------

5.1 Update import parser with better error reporting and line numbers  
5.2 Add class `ActivityCount`. This class maintains a count of activities of
each dialect. It is comparable for equality (with a specific dialect). This is
used to assert that the number of activities imported is the same as the number
of activities in a specific PMC.  
5.3 Update PMC with flag for import failures, message for import failures, and
activity counts for the activities in the PMC and the activities imported
successfully.  
5.4 Created the `Import Error Reporting` EE for interfacing between the error
message on the PMC and the OOA of OOA.  
5.5 Updated the "setActions" functions in `import_functions` with the new error
reporting API.  
5.6 Added "count" functions to `import_functions` to count the number of
activities that exist in a package.  
5.7 Update importer to make the count match assertion and set the error messages
for parse errors.  
5.8 Update transaction manager to check the PMC for error flags before a
transaction that will cause a persist. Pop up a dialog if there are errors and
cancel the transaction.  

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
With the SAC model open in a workspace:  
7.1 Open `Session::delete_session` with the MASL editor  
7.2 Change the signature and save  
7.3 Verify that no popup error is reported  
7.4 Add a parameter to `Session`  
7.5 Verify that a popup appears displaying the error  
7.6 Press OK to dismiss  
7.7 Verify that the action language is not lost and the parameter was not added
to `Session`  
7.8 Change the signature back to its original state and save  
7.9 Add a parameter to `Session`  
7.10 Verify that the parameter was added and no error message appeared  

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint
Branch: 8941_parser_safety

<pre>

 doc-bridgepoint/notes/8941_parser_safety/8941_parser_safety_dnt.md                                        | 176 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/External Entities/External Entities.xtuml         | 188 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/import_functions/import_functions.xtuml | 506 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++---
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ActivityCount.java                                     |  60 ++++++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java                         |  81 ++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/TransactionManager.java                                |  40 ++++++++++++++-
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                          |  28 ++++++++---
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImport.java                                         |   8 +--
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g                                               |  19 ++++---
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/body.g                                                  |   6 +--
 10 files changed, 1080 insertions(+), 32 deletions(-)

</pre>

End
---

