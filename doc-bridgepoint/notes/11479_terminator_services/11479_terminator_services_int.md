---

This work is licensed under the Creative Commons CC0 License

---

# Handle exclusion of terminator services from prj file
### xtUML Project Implementation Note

### 1. Abstract

See [[2.1]](#2.1) section 1

### 2. Document References

<a id="2.1"></a>2.1 [#11479 analysis note](11479_terminator_services_ant.md)  
<a id="2.2"></a>2.2 [BridgePoint DEI #11479](https://support.onefact.net/issues/11479) Main issue.  
<a id="2.3"></a>2.3 [Project SRS (internal document)](https://docs.google.com/document/d/14eUh9kFz_i3o7cYbi_L1BQ7EAxrAq38806vm3PkcnJ8/edit)  
<a id="2.4"></a>2.4 [Meeting notes (internal document)](https://docs.google.com/document/d/1msovMg3UwOr9uxk0XTkLUDjKfU2lJV7uKT5FHIEapkg/edit) Notes from a meeting where this problem was discussed.  
<a id="2.5"></a>2.5 [BridgePoint SR #10320](https://support.onefact.net/issues/10320) Project Primus Documentation  
<a id="2.6"></a>2.6 [BridgePoint DEI #11495](https://support.onefact.net/issues/11495) Manual test issue  

### 3. Background

See [[2.1]](#2.1) section 3

### 4. Requirements

See [[2.1]](#2.1) section 4

### 5. Work Required

5.1 Design chosen

From the analysis note [[2.1]](#2.1) option 5.2.4 was chosen.

5.2 Metamodel update

5.2.1 A new enumerated data type "ImplementationScope" was added to the OOA of OOA.
This enumerated type has two enumerators "Deployment" and "Domain".  
5.2.2 A new attribute "Implementation_Scope" was added to the "Terminator
Service" class typed with the new type "ImplementationScope".  
5.2.3 Code was added in the terminator import process to set this attribute on
creation of terminator services. The default value is "Domain" (Note that we
are not using the "DefaultValue" field int the model, but the code sets the
value on creation of the instance). This attribute is not modified when a
re-import/refresh of a terminator is performed.  

5.3 `x2m` update

5.3.1 During terminator service export, if the implementation scope of a
terminator service in a deployment is "Domain", it is skipped entirely (no
declaration in the `.prj` file and no terminator definition file).  
5.3.2 A check is wrapped around the action language export such that an action
language definition is only emitted if the dialect is "MASL". If the dialect is
"None", no activity definition file will be produced.  

### 6. Implementation Comments

6.1 The format of the file extension filter for the file selection dialog was
updated. Instead of having two different filters `*.int` and `*.mod`, a single
filter `*.int;*.mod` is used. This causes the file selection dialog to allow
users to select `.int` and `.mod` files simultaneously without the need to
change the selection filter in the drop down menu. This change is for the
"Import terminators from file..." menu option.

6.2 This change affects the persistence format of instances related to
deployments. Because of this, the existing GPS Watch example will not load
correctly. The test models committed to the `models` repository also will not
load correctly. The welcome plugin needs to be updated with the change as well.
These clean up tasks will be handled as part of the project documentation task
under issue #10320 [[2.5]](#2.5).

### 7. Unit Test

7.1 Acceptance test has been raised as a manual test. See [[2.6]](#2.6).

### 8. User Documentation

8.1 Documentation for this work will be incorporated into the documentation
provided as part of issue #10320 [[2.5]](#2.5).

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 11479_terminator_services  

<pre>

 doc-bridgepoint/notes/11479_terminator_services/11479_terminator_services_ant.md                                                  | 159 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/11479_terminator_services/11479_terminator_services_int.md                                                  |  93 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/MC-Java/ooa_schema.sql                                                                                                        |   3 ++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Datatypes/Datatypes.xtuml                                                 | 111 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-----------------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Deployment/Terminator Service/Terminator Service.xtuml                    |  28 ++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Deployment/Terminator/Terminator.xtuml                                    |   2 ++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/Context Menu Entry Functions/Context Menu Entry Functions.xtuml |   2 +-
 7 files changed, 367 insertions(+), 31 deletions(-)

</pre>

Fork/Repository: leviathan747/mc  
Branch: 11479_terminator_services  

<pre>

 model/maslout/models/maslout/lib/xtuml2masl/maslout/maslout.xtuml                        | 10 ++++++----
 model/mcooa/models/mcooa/ooaofooa/Datatypes/Datatypes.xtuml                              | 69 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++------
 model/mcooa/models/mcooa/ooaofooa/Deployment/Terminator Service/Terminator Service.xtuml | 28 ++++++++++++++++++++++++++++
 schema/sql/xtumlmc_schema.sql                                                            |  3 ++-
 4 files changed, 99 insertions(+), 11 deletions(-)

</pre>

### End
