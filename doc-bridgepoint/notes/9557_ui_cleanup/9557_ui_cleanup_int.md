---

This work is licensed under the Creative Commons CC0 License

---

# User Interface Enhancements and Simplification 
### xtUML Project Implementation Note


### 1. Abstract

This note describes the work performed to address user requirements for
improvements to the BridgePoint User Interface.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9557](https://support.onefact.net/issues/9557) Headline issue.  
<a id="2.2"></a>2.2 [Design Note for 9557](9557_ui_cleanup_dnt.md)  
<a id="2.3"></a>2.3 [BridgePoint DEI #9446](https://support.onefact.net/issues/9446) Unable to merge a identifier from two super types in a subtype class    

### 3. Background

See [2.2].  

### 4. Requirements

See [2.2].  

### 5. Work Required

See [2.2].  The work was performed as designed with no major alterations.    

### 6. Implementation Comments

6.1  The original design proposal of using the `<visibility>` modifier on 
  `objectContribution` menu elements required some additional work.  Some menu
  elements already used the `<filter>` modifier.  The `<visibility>` stanzas 
  conflicted with and overrode the `<filter>` stanzas.  For example, the `Move Up` 
  menu item would show up when it should not (because parameter is already at top 
  of list).  The reason is that the `actionFilter()` was never being called to 
  check “can > moveup”. Based on research into the `objectContribution` API we
  found that `filter` is simply shorthand for an `objectState` visibility check.  Thus:  
   
```xml  
<filter name="can" value="unassign" />
```  
is equal to  
```xml  
<visibility>
  <objectState name="can" value="unassign" />
</visibility>
```  

  Therefore, we switched to the `objectState` format, which allowed combinations 
  where necessary to check the action filter and the system property, as in:  
  
```xml
<visibility>
  <and>
    <objectState name="can" value="unassign" />
    <not>
      <systemProperty name="bridgepoint.Unassign" value="disabled"/>
    </not>
  </and>
</visibility>
```
  
6.2  Context Menu Reorganizations  
6.2.1  Moved `BridgePoint Utilities`, `Manage Project Markings`, and canvas `Format`, `Layers` 
  and `Routing` with other BridgePoint tools in the CME.  Previously they were dumped into the 
  CME much further down and mixed in with other tools provided by eclipse.  This made it unclear
  to see that these are BridgePoint-specific features.  
6.2.2  Moved `Create Documentation` from being a top-level CME contribution and placed it under 
  the `BridgePoint Utilities` submenu on the CME.  This tool is not part of the core BridgePoint
  modeling functionality and, as an add-on, makes sense to be grouped with other similar add-ons.  

6.3  Bug fix in UITestingUtilities  
6.3.1  During development of the JUnits, I discovered an issue with 
  `UITestingUtilities.checkItemStatusInContextMenuByPath(menu, toolPath, false))`.  The problem was
  that when I passed a path such as `New::Interaction::Actor` and did not expect to find Actor because
  it had been disabled, the function was incorrectly returning the `Interaction` menu item.  I 
  adjusted the way the returned menuItem was set, to make sure it is null when we start checking 
  child items.  

6.4  Fixed a typo in `org.xtuml.bp.core/generate.xml`  

6.5  Update test generation.  
6.5.1  Due to the change from "Usecase" to "Usecase" in the cme, we had to make some modifications
  in `org.xtuml.bp.core.test/arc/create_action_test.arc` to remove spaces from the specialism name.  
  
6.6  Updated the DocGenTest to find the CME tool under BridgePoint Utilities. The test was not part
  of any JUnit suite so I added it to the Core System Level Tests.  Also fixed the the tests inside
  to use valid models and check results properly. 

6.7  Update some test cases in `CombineSplitReferentialsTestGenerics.java`  
6.7.1  Work was done in [2.3] that allows BridgePoint to combine multiple referential attributes 
  that do not share the same base attribute but do have identical types. The JUnit was not updated 
  at the time of promotion to reflect the new functionality.   
6.7.2  Updated the assert in `testReferentialAttributesWithDiffBaseTypes()` to expect combination of 
  two integer referentials to be allowed.    
6.7.3  Updated the checks in `testTwoReferentialAttributesSameBaseTypesOneDifferent()` for the new 
  referential combination logic.  

6.8  While working in the Core2 Suite I noticed that one of the test classes was commented out: 
  `PolymorphicEventAssignmentTestGenerics`. I uncommented the test class and ran the test suite locally
  and the tests passed.  Therefore, I'm leaving the change in place to now run the tests.   

### 7. Unit Test

See [2.2].

### 8. User Documentation

8.1  Added new help document "BridgePoint Context Menu Tools"  
8.2  Added new help document "Palette and Context Menu Customization"  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 9557_ui_cleanup   

<pre>
 doc-bridgepoint/notes/9304_masl_names_int.md       |   2 +-
 .../notes/9557_ui_cleanup/9557_ui_cleanup_dnt.md   | 276 ++++++++++++++++
 .../notes/9557_ui_cleanup/9557_ui_cleanup_int.md   | 122 +++++++
 doc-bridgepoint/notes/9557_ui_cleanup/bp_exit.jpeg | Bin 0 -> 13389 bytes
 doc-bridgepoint/notes/9557_ui_cleanup/bp_help.jpeg | Bin 0 -> 20946 bytes
 doc-bridgepoint/notes/9557_ui_cleanup/bp_menu.jpeg | Bin 0 -> 4049 bytes
 .../bp_model_explorer_rightclick_context_menu.jpeg | Bin 0 -> 12900 bytes
 .../notes/9557_ui_cleanup/bp_window_menu.jpeg      | Bin 0 -> 17748 bytes
 .../notes/9557_ui_cleanup/screenshot0.png          | Bin 0 -> 36249 bytes
 doc-bridgepoint/process/FAQ.md                     |   2 +-
 .../launch_configs/BP Application (OSX).launch     |   2 +-
 .../review-minutes/9557_ui_cleanup_dnt.rvm.md      |  34 ++
 .../bridgepoint.product                            |   3 +-
 src/org.xtuml.bp.core.editors/bin/.gitignore       |   1 -
 src/org.xtuml.bp.core/arc/create_core_plugin.inc   |  56 +++-
 src/org.xtuml.bp.core/arc/function_body.inc        |   6 +-
 src/org.xtuml.bp.core/generate.xml                 |   6 +-
 .../Context Menu Entry Functions.xtuml             |   2 +-
 src/org.xtuml.bp.core/sql/context_menu.pei.sql     |   2 +-
 .../src/org/xtuml/bp/core/ui/.gitignore            | 215 +------------
 .../ui/preferences/BridgePointPreferences.java     |   4 +-
 .../BridgePointContextMenuTools.html               | 349 +++++++++++++++++++++
 .../BridgePointContextMenuTools.md                 | 125 ++++++++
 .../BridgePointContextMenuTools/canvas_menu.png    | Bin 0 -> 272107 bytes
 .../BridgePointContextMenuTools/me_menu.png        | Bin 0 -> 386604 bytes
 .../BridgePointContextMenuTools/utilities_menu.png | Bin 0 -> 360408 bytes
 .../PaletteAndContextMenuCustomization.html        | 189 +++++++++++
 .../PaletteAndContextMenuCustomization.md          | 156 +++++++++
 src/org.xtuml.bp.doc/topics_Reference.xml          |   2 +
 src/org.xtuml.bp.docgen/plugin.xml                 |   9 +-
 src/org.xtuml.bp.integrity/plugin.xml              |   5 +
 .../META-INF/MANIFEST.MF                           |  12 -
 src/org.xtuml.bp.internal.tools/plugin.xml         |  31 +-
 .../CreateTestProjectAndImportTestModel.java       | 102 ------
 .../internal/tools/cleaner/CleanseForMCWorker.java | 268 ----------------
 src/org.xtuml.bp.mc/plugin.xml                     |   5 +
 .../bp/ui/canvas/ModelContentOutlinePage.java      |   2 +-
 .../arc/create_explorer_view.inc                   |   2 +-
 .../arc/create_mon_explorer_view.inc               |   2 +-
 src/org.xtuml.bp.ui.graphics/plugin.xml            |   2 +-
 .../bp/ui/graphics/editor/GraphicalEditor.java     |  20 ++
 .../ui/graphics/outline/GraphicalOutlinePage.java  |   2 +-
 .../GraphicsConnectionCreationToolEntry.java       |  24 +-
 .../palette/GraphicsCreationToolEntry.java         |  29 +-
 .../providers/CanvasEditorContextMenuProvider.java |   2 +-
 src/org.xtuml.bp.ui.marking/plugin.xml             |   7 +-
 .../arc/create_SessionExplorer_view.inc            |   2 +-
 src/org.xtuml.bp.utilities/META-INF/MANIFEST.MF    |   1 -
 src/org.xtuml.bp.utilities/plugin.xml              |  65 ++--
 .../data/FixMissingMatrixEntryAction.java          | 228 --------------
 .../bp/utilities/load/LoadByExpansionAction.java   |  40 ---
 src/org.xtuml.bp.x2m/plugin.xml                    |  21 +-
 .../masl/parser/antlr/lexer/jflex/MASLFlexer.java  |   6 +-
 utilities/build/build_configuration.sh             |   5 +-
 54 files changed, 1463 insertions(+), 983 deletions(-)
</pre>

Fork/Repository: keithbrown/bptest  
Branch: 9557_ui_cleanup   

<pre>
 .../arc/create_action_test.arc                     |   11 +-
 .../arc/create_context_menu_tests.arc              |    6 +-
 .../expected_results/DocGen/CFMon_doc.html         | 1194 -------
 .../expected_results/DocGen/CFMon_doc.xml          | 3454 --------------------
 .../expected_results/DocGen/DocGenTest_doc.html    |  285 --
 .../expected_results/DocGen/DocGenTest_doc.xml     |  827 -----
 .../DocGen/GPSWatch_System_RTO_Off_doc.html        |   17 -
 .../DocGen/GPSWatch_System_RTO_Off_doc.xml         |  181 -
 .../DocGen/GPSWatch_System_doc.html                | 1030 ------
 .../DocGen/GPSWatch_System_doc.xml                 | 2834 ----------------
 .../InterProjectRealizedClass_RTO_Off_doc.html     |   26 +
 .../InterProjectRealizedClass_RTO_Off_doc.xml      |  133 +
 .../DocGen/InterProjectRealizedClass_doc.html      |   76 +
 .../DocGen/InterProjectRealizedClass_doc.xml       |  324 ++
 .../expected_results/DocGen/MicrowaveOven_doc.html |  743 +++++
 .../expected_results/DocGen/MicrowaveOven_doc.xml  | 1682 ++++++++++
 src/org.xtuml.bp.core.test/generate.xml            |    1 +
 .../src/SystemLevelGlobalsTestSuite.java           |    8 +-
 .../xtuml/bp/core/test/UIConfigurationTests.java   |  152 +
 .../src/org/xtuml/bp/core/test/ui/DocGenTest.java  |  133 +-
 .../xtuml/bp/test/common/UITestingUtilities.java   |   11 +-
 21 files changed, 3194 insertions(+), 9934 deletions(-)
</pre>

### End

