---

This work is licensed under the Creative Commons CC0 License

---

# Update welcome plugin with OAL and MASL versions of the GPS Watch model
### xtUML Project Implementation Note

### 1. Abstract

Issue #10054 [[2.2]](#2.2) introduced a new version of the GPS Watch model
which had been refactored to be compatible with the MASL modeling idiom. The
model was created with two different versions of the action language, OAL and
MASL. It is executable and translatable on several different platforms and
architectures.  This work is to package the new versions of the model into the
BridgePoint welcome plugin quick start.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10202 Update welcome plugin with OAL and MASL versions of the GPS Watch model](https://support.onefact.net/issues/10202)  
<a id="2.2"></a>2.2 [Service Pro SR #10054 GPS Watch model to MASL](https://support.onefact.net/issues/10054)  
<a id="2.3"></a>2.3 [BridgePoint DEI #9556 Saab- 11 : Import of an existing xtUML project fails to show the model until ME is closed and reopened](https://support.onefact.net/issues/9556)  

### 3. Background

3.1 Overview

It has long been known that a good, rich reference model using MASL was needed
for educational purposes and to provide more testing exposure of the MASL
capabilities of BridgePoint. With this work, the new model will be readily
available to all BridgePoint users.

The OAL version of the model is committed in the models repository at
`applications/gps/GPS` in the `master` branch.  The MASL version of the model
is committed in the models repository at `applications/gps/GPS` in the
`gps_masl` branch. Both versions will be packaged to be importable from the
welcome plugin.

3.2 Dialect specific workspace preferences

There are a few workspace preferences that are required for editing models of a
specific dialect. For MASL, the required workspace preferences are as follows:

3.2.1 Default action language dialect: MASL  
3.2.2 Enable restricted identifier naming for model elements: true  
3.2.3 Allow concrete polymorphic events: true  

For OAL, the required workspace preferences are as follows:

3.2.4 Default action language dialect: OAL  
3.2.5 Allow concrete polymorphic events: false  

Upon import of an example model marked by a specific dialect, if any of the
required preferences is unset, the following dialog shall be shown.

![dialog](dialog.png)

Selecting "OK" will set the preferences for the user automatically. Selecting
"Cancel" will leave the preferences as they were.

_Note: the pictured dialog is specifically for when a MASL model is imported. If
an OAL model is imported, the contents will be slightly different based on the
OAL required preferences._

### 4. Requirements

4.1 The welcome plugin shall be updated with the newest version of the GPS Watch
model.  
4.2 Both the OAL and MASL versions of GPS Watch shall be accessible from the
welcome plugin.  
4.3 Upon import of an OAL or MASL model, the user shall be provided a mechanism
to set dialect specific workspace preferences.  
4.3.1 This mechanism shall be limited to importing quick start models from the
welcome plugin and shall not extend to other import paths.  

### 5. Work Required

5.1 Update `SampleProjectGettingStartedAction` with several enhancements:  
5.1.1 Allow zip archives with multiple projects.  
5.1.2 Specify files on which to set execute permissions after import.  
5.1.3 Add ability to specify the path to a `README` file other than the default.  
5.1.4 Add mechanism for setting dialect specific workspace preferences.  
5.2 Remove `SAC_OOA` and `SAC_PROC` models from the welcome plugin. These older
MASL example models are not well maintained and they do not execute. Since we
now have a superior example model, there is no reason to keep them in the
welcome plugin.  
5.3 Update `introContent.xml` with links to the new GPS models for OAL and MASL
and remove links to the SAC models.  

### 6. Implementation Comments

None.

### 7. Unit Test

7.1 Existing unit tests shall pass.

7.1.1 GPS Watch welcome plugin tests

Some work had to be done to fix the existing GPS Watch unit tests. For the most
part, things were only changed to account for the new model being split into
multiple projects.

7.1.2 `testProjectCreationNoImportIntoworkspace`

One test was removed from the welcome plugin. This test had to do with
importing an existing project in place without importing into the workspace and
was a regression test for issue #9556 ([[2.3]](#2.3)). This use case is
actually not accessible from the welcome plugin, so it was removed (it probably
did not belong in the welcome plugin tests to begin with). The test has been
moved to the io.mdl.test plugin in a test class called `ImportExistingTests`.

As part of the #9556 pull request where this test was introduced, extra
instrumentation was added to the welcome plugin which facilitated the test.
This has been removed since it is now dead code without the test. It is also
generally considered bad practice to add features to the application plugins
for the sole purpose of test.

7.1.3 Other test updates

There were four OAL parse tests which used the GPS Watch model as a test model.
These had to be updated to refer to the new model. They were also cleaned up
and formatted (removed tabs, Windows styled EOLs, etc).

No additional unit tests were added for the MASL model.

7.2 Manual test

7.2.1 Launch BP in a fresh workspace. From the welcome page select Quick Start >
Example Application - GPS Watch (MASL).  
7.2.2 Verify that the dialog appears asking the user to set the workspace
preferences for MASL. Select OK.  
7.2.3 Verify that the preferences have been set to MASL defaults.  
7.2.3.1 Navigate to Preferences > xtUML. See that restricted identifiers is
enabled.  
7.2.3.2 Navigate to Preferences > xtUML > Action Language. See that the default
dialect is MASL.  
7.2.4 Optional step: Follow the README instructions to run/rebuild the MASL
version of the application.  
7.2.5 Navigate back to the welcome page (Help > Welcome).  
7.2.6 Select Quick Start > Example Application - GPS Watch (OAL). Verify that a
dialog is shown warning the user that the projects already exist in the
workspace. Select "Yes" to delete the old projects.  
7.2.7 Verify that the dialog appears asking the user to set the workspace
preferences for OAL. Select OK.  
7.2.8 Verify that the preferences have been set to OAL defaults.  
7.2.8.1 Navigate to Preferences > xtUML > Action Language. See that the default
dialect is OAL.  
7.2.9 Optional step: Follow the README instructions to run/rebuild the OAL
version of the application.  

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 10202_new_gps  

<pre>

 doc-bridgepoint/notes/10202_update_gps_welcome/10202_update_gps_welcome_int.md                          |  182 ++++++++++++++++++++++++
 doc-bridgepoint/notes/10202_update_gps_welcome/dialog.png                                               |  Bin 0 -> 106590 bytes
 src/org.xtuml.bp.welcome/introContent.xml                                                               |   20 ++-
 src/org.xtuml.bp.welcome/models/GPS_Watch_MASL.zip                                                      |  Bin 0 -> 27077691 bytes
 src/org.xtuml.bp.welcome/models/{GPS Watch.zip => GPS_Watch_OAL.zip}                                    |  Bin 2765650 -> 2368010 bytes
 src/org.xtuml.bp.welcome/models/SAC_OOA.xtuml                                                           | 1467 -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 src/org.xtuml.bp.welcome/models/SAC_PROC.xtuml                                                          |  238 -------------------------------
 src/org.xtuml.bp.welcome/src/org/xtuml/bp/welcome/gettingstarted/SampleProjectGettingStartedAction.java |  424 ++++++++++++++++++++++++++++++++++++++----------------
 8 files changed, 491 insertions(+), 1840 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 10202_new_gps  

<pre>

  src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/TestAllowInterfaceNameInICMsg_Generics.java | 255 +++++++++++++++++++++++++++++++++++---------------------------------------
 src/org.xtuml.bp.io.mdl.test/src/IOMdlGlobalsTestSuiteGenerics.java                                     |   2 +
 src/org.xtuml.bp.io.mdl.test/src/org/xtuml/bp/io/mdl/test/ImportExistingTests.java                      | 133 +++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.test/src/org/xtuml/bp/test/common/TestingUtilities.java                                |   8 ++-
 src/org.xtuml.bp.welcome.test/src/org/xtuml/bp/welcome/test/WelcomePageTestGPS.java                     | 648 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++------------------------------------------------------------------------------------------------------------------
 5 files changed, 513 insertions(+), 533 deletions(-)

</pre>

### End

