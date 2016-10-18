---

This work is licensed under the Creative Commons CC0 License

---

# Identifier Handling for MASL
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the changes implemented to address issues around MASL export
that are related to element names  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8753](https://support.onefact.net/issues/8753) Squash blanks from classes, role phrases, etc on MASL export    
<a id="2.2"></a>2.2 [BridgePoint DEI #8771](https://support.onefact.net/issues/8771) Problem characters prevent MASL export     
<a id="2.3"></a>2.3 [BridgePoint DEI #8774](https://support.onefact.net/issues/8774) Problem with MASL export and referentials     
<a id="2.4"></a>2.4 [8753 Analysis Note](8753_identifier_handling_ant.md)      

3. Background
-------------
See [[2.4]](#2.4).

4. Requirements
---------------
See [[2.4]](#2.4).

5. Work Required
----------------
5.1  Implemented new preference that controls whether or not the BridgePoint
  editor requires MASL-style names.  The default value is "off" (i.e. no change
  to current behavior).   
  
5.2  Modified RenameAction::isNameValid() to check the preference and enforce
  the naming convention if required.  There is no change to existing checks if
  the new preference is "off".   
  
5.3  Added JUnit test class ```CanvasInitialMASLNameTests```.   

6. Implementation Comments
--------------------------
6.1  The property edit field for text phrases had to be tweaked to use a
  validator.  We use the ```ModelElementNameValidator``` which already relied on
  the ```Rename.isNameValid``` check that was modified as part of this work.   
6.1.1  This touched the properties fields for attribute ```Txt_Phrs``` in:
```
AssociationFormalizerEndR_FORMPropertySource.java
AssociationParticipantEndR_PARTPropertySource.java
AssociativeOneEndR_AONEPropertySource.java
AssociativeOtherEndR_AOTHPropertySource.java
DerivedOneEndR_CONEPropertySource.java
DerivedOtherEndR_COTHPropertySource.java
```
  Note that these are all generated files.  The change only needed to be made
  in one place in the archetype ```BuildPropertySource.arc```   
  

7. Unit Test
------------
7.1  Run the UI Canvas Suite 1 Junits.  They include a new test set for this
  feature.
  
8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint
Branch: 8753_identifier_handling

<pre>

doc-bridgepoint/notes/8753_identifier_handling/8753_identifier_handling_ant.md
doc-bridgepoint/notes/8753_identifier_handling/8753_identifier_handling_int.md

org.xtuml.bp.core/arc/create_core_plugin_class.arc
org.xtuml.bp.core/arc/create_rename_action.inc
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/
    BridgePointPreferences.java

org.xtuml.bp.ui.canvas.test/src/GlobalsCanvasSuite1.java
org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/
    CanvasInitialMASLNameTests.java

org.xtuml.bp.ui.properties/arc/BuildPropertySource.arc
</pre>


Fork/Repository: keithbrown/mc
Branch: 8753_identifier_handling

<pre>

< Put the file list here >

</pre>

End
---

