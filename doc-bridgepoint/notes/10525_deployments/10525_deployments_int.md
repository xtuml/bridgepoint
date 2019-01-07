---

This work is licensed under the Creative Commons CC0 License

---

# Implement deployments
### xtUML Project Implementation Note

### 1. Abstract

See [[2.2]](#2.2)

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10525](https://support.onefact.net/issues/10525) Implement deployments  
<a id="2.2"></a>2.2 [#10525 design note](10525_deployments_dnt.md)  
<a id="2.3"></a>2.3 [BridgePoint SR #10320](https://support.onefact.net/issues/10320) Project Primus Documentation  

### 3. Background

See [[2.2]](#2.2)

### 4. Requirements

See [[2.2]](#2.2)

### 5. Work Required

5.1 The "Deployment" subsystem was implemented in the metamodel as designed in
[[2.2]](#2.2).

5.2 The import of terminators was implemented in the metamodel as designed in
[[2.2]](#2.2). The `selectFiles` bridge was moved to the `User` EE which made
more sense.

5.3 The refresh terminators from source material was implemented in the metamodel as designed in
[[2.2]](#2.2).

5.4 MASL export was implemented in the metamodel as designed in [[2.2]](#2.2).

5.5 The schemas in both the bridgepoint and mc repositories were updated.

5.6 The welcome plugin was updated with the new metamodel project.

### 6. Implementation Comments

None.

### 7. Unit Test

7.1 The tests outlined in section 9 of [[2.2]](#2.2) were implemented as part of the
org.xtuml.bp.core.test test suite.

7.2 The file selection dialog could not be tested directly with a JUnit test
because it used a system dialog. Instead, a helper class was created to allow
the file selection action to be run with a file passed in as an argument.

### 8. User Documentation

8.1 Documentation for this work will be incorporated into the documentation
provided as part of issue #10320 [[2.3]](#2.3).

8.2 The welcome project has been changed to reflect the changes in the
metamodel.

8.3 Palette and context menu documentation shall be updated along with MASL
documentation.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint
Branch: 10525_deployments

<pre>

 doc-bridgepoint/notes/10525_deployments/10525_deployments_dnt.md                                                                              |    548 +
 doc-bridgepoint/notes/10525_deployments/10525_deployments_int.md                                                                              |    106 +
 doc-bridgepoint/notes/10525_deployments/deployments.png                                                                                       |    Bin 0 -> 633803 bytes
 doc-bridgepoint/review-minutes/10525_deployments_dnt_rvm.md                                                                                   |     71 +
 src/MC-Java/java.arc                                                                                                                          |      3 +
 src/MC-Java/ooa_schema.sql                                                                                                                    |     41 +
 src/org.xtuml.bp.core/arc/chooser_elements_provider.inc                                                                                       |     25 +-
 src/org.xtuml.bp.core/arc/create_core_plugin.inc                                                                                              |      5 +-
 src/org.xtuml.bp.core/arc/create_core_plugin_class.arc                                                                                        |     26 +
 src/org.xtuml.bp.core/arc/create_global_action.inc                                                                                            |      6 +-
 src/org.xtuml.bp.core/arc/create_selection_dialog_action.inc                                                                                  |      4 +
 src/org.xtuml.bp.core/arc/function_body.inc                                                                                                   |     10 +-
 src/org.xtuml.bp.core/arc/wfl_block.inc                                                                                                       |     31 -
 src/org.xtuml.bp.core/generate.properties                                                                                                     |      2 +-
 src/org.xtuml.bp.core/generate.xml                                                                                                            |      2 +-
 src/org.xtuml.bp.core/icons/metadata/Deployment.gif                                                                                           |    Bin 0 -> 172 bytes
 src/org.xtuml.bp.core/icons/metadata/ProvidedTerminator.gif                                                                                   |    Bin 0 -> 138 bytes
 src/org.xtuml.bp.core/icons/metadata/ProvidedTerminatorService.gif                                                                            |    Bin 0 -> 93 bytes
 src/org.xtuml.bp.core/icons/metadata/Terminator.gif                                                                                           |    Bin 0 -> 138 bytes
 src/org.xtuml.bp.core/icons/metadata/TerminatorService.gif                                                                                    |    Bin 0 -> 93 bytes
 src/org.xtuml.bp.core/icons/metadata/TerminatorServiceParameter.gif                                                                           |    Bin 0 -> 92 bytes
 src/org.xtuml.bp.core/icons/newdeployment.gif                                                                                                 |    Bin 0 -> 607 bytes
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component.xtuml                                                             |    144 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Signal Provisions and Requirements.xtuml |    192 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Datatypes/Datatypes.xtuml                                                             |      6 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Deployment/Deployment.xtuml                                                           |   1821 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Deployment/Deployment/Deployment.xtuml                                                |    805 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Deployment/Terminator Service Parameter/Terminator Service Parameter.xtuml            |    777 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Deployment/Terminator Service/Terminator Service.xtuml                                |   1195 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Deployment/Terminator/Terminator.xtuml                                                |    612 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Data Type/Data Type.xtuml                                                      |      4 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Dimensions/Dimensions.xtuml                                                    |    102 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Domain.xtuml                                                                   |     42 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/Package.xtuml                                               |     90 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/External Entities/External Entities.xtuml                                             |    193 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/Context Menu Entry Functions/Context Menu Entry Functions.xtuml             |    171 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/import_functions/import_functions.xtuml                                     |      9 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/x2m_functions/x2m_functions.xtuml                                           |    187 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Packageable Element/Packageable Element.xtuml                                         |    214 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Packageable Element/Packageable Element/Packageable Element.xtuml                     |      5 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/ooaofooa.xtuml                                                                        |     24 +
 src/org.xtuml.bp.core/sql/context_menu.pei.sql                                                                                                |      9 +
 src/org.xtuml.bp.core/sql/ooaofooa_hierarchy.pei.sql                                                                                          |     10 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/DependencyData.java                                                                        |     35 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/IDependencyProvider.java                                                                   |     17 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/inspector/.gitignore                                                                              |      4 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/inspector/PackageInspector.java                                                                   |   1991 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/sorter/.gitignore                                                                                 |     19 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/sorter/TerminatorService_cSorter.java                                                             |     30 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/sorter/Terminator_cSorter.java                                                                    |     30 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/.gitignore                                                                                     |      3 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/actions/.gitignore                                                                             |      3 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/dialogs/ElementSelectionDialog.java                                                            |    176 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/dialogs/ElementSelectionFlatView.java                                                          |     32 +-
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                                                              |      5 +
 src/org.xtuml.bp.io.mdl/sql/file_io.pei.sql                                                                                                   |     20 +-
 src/org.xtuml.bp.io.mdl/sql/stream.pei.sql                                                                                                    |      9 +-
 src/org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/Datatypes/Datatypes.xtuml                                              |      6 +
 src/org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/Cl_c.java                                                                               |     20 +
 src/org.xtuml.bp.ui.explorer/plugin.xml                                                                                                       |     16 +-
 src/org.xtuml.bp.ui.explorer/sql/UITree.pei.sql                                                                                               |      8 +
 src/org.xtuml.bp.ui.explorer/src/org/xtuml/bp/ui/explorer/decorators/StaleServiceDecorator.java                                               |     64 +
 src/org.xtuml.bp.ui.graphics/plugin.xml                                                                                                       |     21 +
 src/org.xtuml.bp.ui.properties/arc/BuildPropertySource.arc                                                                                    |      3 +-
 src/org.xtuml.bp.ui.text/arc/create_plugin_xml.arc                                                                                            |     13 +
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/masl/MASLEditorInputFactory.java                                                            |      1 +
 src/org.xtuml.bp.welcome/models/xtUML_Metamodel.xtuml                                                                                         | 570740 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-------------------------------------------------------------------------
 src/org.xtuml.bp.x2m/plugin.xml                                                                                                               |     16 +
 src/org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/actions/ExportProjectAction.java                                                                    |      4 +
 src/org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/generator/Generator.java                                                                            |     17 +
 src/org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/refresher/MASLEditorPartListener.java                                                               |     28 +-
 src/org.xtuml.bp.x2m/src/org/xtuml/bp/x2m/refresher/Refresher.java                                                                            |    152 +-
 72 files changed, 297668 insertions(+), 283307 deletions(-)

</pre>

Fork/Repository: leviathan747/mc
Branch: 10525_deployments

<pre>

 model/maslout/models/maslout/lib/xtuml2masl/maslout/maslout.xtuml                                            |  527 ++++++++++++++++++++++++++++++++++++++++++++++++-----
 model/mcooa/models/mcooa/ooaofooa/Deployment/Deployment.xtuml                                                | 1827 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 model/mcooa/models/mcooa/ooaofooa/Deployment/Deployment/Deployment.xtuml                                     |  124 +++++++++++++
 model/mcooa/models/mcooa/ooaofooa/Deployment/Terminator Service Parameter/Terminator Service Parameter.xtuml |  285 +++++++++++++++++++++++++++++
 model/mcooa/models/mcooa/ooaofooa/Deployment/Terminator Service/Terminator Service.xtuml                     |  323 ++++++++++++++++++++++++++++++++
 model/mcooa/models/mcooa/ooaofooa/Deployment/Terminator/Terminator.xtuml                                     |  199 ++++++++++++++++++++
 model/mcooa/models/mcooa/ooaofooa/Domain/Dimensions/Dimensions.xtuml                                         |  102 +++++++++++
 model/mcooa/models/mcooa/ooaofooa/Packageable Element/Packageable Element.xtuml                              |  192 ++++++++++++++++++++
 model/mcooa/models/mcooa/ooaofooa/ooaofooa.xtuml                                                             |   24 +++
 model/mcshared/models/mcshared/functions/functions.xtuml                                                     |   54 ++++++
 schema/sql/xtumlmc_schema.sql                                                                                |   41 +++++
 11 files changed, 3653 insertions(+), 45 deletions(-)

</pre>

Fork/Repository: leviathan747/models
Branch: 10525_deployments

<pre>

 test/DeploymentsDomains/.project                                                                                                    |  12 +++++
 test/DeploymentsDomains/gen/code_generation/DeploymentsDomains.sql                                                                  | 426 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/gen/code_generation/masl_output.txt                                                                         |  15 ++++++
 test/DeploymentsDomains/gen/code_generation/x2m_output.txt                                                                          |   6 +++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1.int                                                              |  13 +++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1.mod                                                              |  31 ++++++++++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update1.int                                                      |  13 +++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update2.int                                                      |  13 +++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update3.int                                                      |  13 +++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update4.int                                                      |  13 +++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update5.int                                                      |  13 +++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update6.int                                                      |  13 +++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update7.int                                                      |  14 ++++++
 test/DeploymentsDomains/masl/DeploymentsDomain1/DeploymentsDomain1_update8.int                                                      |  12 +++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1.xtuml                                       | 304 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1/DeploymentsDomain1.int                      |   4 ++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1/DeploymentsDomain1.mod                      |   4 ++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1/DeploymentsDomain1.xtuml                    | 416 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1/DeploymentsDomain1/A/A.xtuml                |  55 +++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1/DeploymentsDomain1/B/B.xtuml                |  55 +++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1/DeploymentsDomain1/DeploymentsDomain1.xtuml | 105 ++++++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/DeploymentsDomain1/functions/functions.xtuml                   |  98 +++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/Shared/DeploymentsDomain1/DeploymentsDomain1.xtuml             |  44 +++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/Shared/DeploymentsDomain1_term1/DeploymentsDomain1_term1.xtuml |  51 ++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain1/Shared/Shared.xtuml                                            | 143 ++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain2/DeploymentsDomain2.xtuml                                       |  94 ++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain2/DeploymentsDomain2/DeploymentsDomain2.int                      |   4 ++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain2/DeploymentsDomain2/DeploymentsDomain2.mod                      |   4 ++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain2/DeploymentsDomain2/DeploymentsDomain2.xtuml                    |  76 +++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomain2/Shared/Shared.xtuml                                            |  54 +++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/DeploymentsDomains.xtuml                                                          | 104 +++++++++++++++++++++++++++++++++++++++
 test/DeploymentsDomains/models/DeploymentsDomains/types/types.xtuml                                                                 |  87 +++++++++++++++++++++++++++++++++
 test/DeploymentsTests/.project                                                                                                      |  12 +++++
 test/DeploymentsTests/models/DeploymentsTests/DeploymentsTests.xtuml                                                                |  56 +++++++++++++++++++++
 test/DeploymentsTests/models/DeploymentsTests/DeploymentsTests/DeploymentsTests.xtuml                                               |  80 ++++++++++++++++++++++++++++++
 test/PropertiesRenameTests/models/PropertiesRenameTests/PropertiesRenameTests.xtuml                                                 |  30 ++++++++++--
 test/PropertiesRenameTests/models/PropertiesRenameTests/testPackage/testPackage.xtuml                                               |  49 +++++++++++++++++--
 37 files changed, 2528 insertions(+), 8 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest
Branch: 10525_deployments

<pre>

 src/org.xtuml.bp.core.test/META-INF/MANIFEST.MF                                                                   |    3 +-
 src/org.xtuml.bp.core.test/src/CoreTestFull.java                                                                  |    3 +-
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/TestVisibilityInElementChooser.java                         |    6 +
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/deployments/DeploymentExportTests.java                      |  113 ++++++++++++++++
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/deployments/DeploymentTestFull.java                         |   13 ++
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/deployments/ImportFromComponentTests.java                   |  211 +++++++++++++++++++++++++++++
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/deployments/ImportFromFileTests.java                        |   85 ++++++++++++
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/deployments/ImportTerminatorsFromFileOnD_DEPLAction.java    |   98 ++++++++++++++
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/deployments/TerminatorUpdateTests.java                      |  472 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.test/src/org/xtuml/bp/test/TestUtil.java                                                         | 1322 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-------------------------------------------------------------------------------------------
 src/org.xtuml.bp.ui.properties.test/models/org.xtuml.bp.ui.properties.test/testProp/Deployments/Deployments.xtuml |  205 ++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.properties.test/models/org.xtuml.bp.ui.properties.test/testProp/testProp.xtuml                |   24 ++++
 12 files changed, 1872 insertions(+), 683 deletions(-)

</pre>

### End
