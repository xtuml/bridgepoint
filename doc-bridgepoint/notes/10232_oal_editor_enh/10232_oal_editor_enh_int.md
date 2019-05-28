---

This work is licensed under the Creative Commons CC0 License

---

# OAL Editor Enhancements
### xtUML Project Implementation Note


### 1. Abstract

This note describes work performed to fix tabs and spaces handling in the OAL
editor as well as add auto-indentation functionality.   

A simple, user-requested canvas update is also addressed.   

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI 10345 Design Note](10232_oal_editor_enh_dnt.md)   

### 3. Background

See [2.1].  

### 4. Requirements

See [2.1].  

### 5. Work Required

See [2.1].  

### 6. Implementation Comments

See [2.1].  

### 7. Unit Test

See [2.1].  

### 8. User Documentation

None

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__  
Branch: __10232_oal_editor_enh__    

<pre>

 doc-bridgepoint/notes/10232_oal_editor_enh/10232_oal_editor_enh_dnt.md | 108 +++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/10232_oal_editor_enh/10232_oal_editor_enh_int.md | 108 +++++++++++++++++++++++++++++++++
 .../models/org.xtuml.bp.core/ooaofooa/Domain/Range/Range.xtuml         |   2 +-
 .../src/org/xtuml/bp/ui/text/editor/oal/OALAutoEditStrategy.java       |  88 +++++++++++++++++++++++++++
 .../src/org/xtuml/bp/ui/text/editor/oal/OALEditor.java                 |  19 +++---
 .../src/org/xtuml/bp/ui/text/editor/oal/OALEditorConfiguration.java    |  39 +++++++++---
 .../ui/text/editor/oal/preference/OALEditorSyntaxPreferencePage.java   |  45 +++++++-------
 .../org/xtuml/bp/xtext/masl/parser/antlr/lexer/jflex/MASLFlexer.java   |   6 +-
 .../org/xtuml/bp/xtext/masl/serializer/MASLSyntacticSequencer.java     |  12 ++--
 utilities/build/build_configuration.sh                                 |  10 +--
</pre>

Fork/Repository: __keithbrown/bptest__  
Branch: __10232_oal_editor_enh__    

<pre>
 src/org.xtuml.bp.debug.ui.test/src/VerifierTestFull.java   | 6 +++++-
 src/org.xtuml.bp.debug.ui.test/src/VerifierTestSuite.java  | 9 ---------
 src/org.xtuml.bp.debug.ui.test/src/VerifierTestSuite2.java | 9 ---------
</pre>

### End

