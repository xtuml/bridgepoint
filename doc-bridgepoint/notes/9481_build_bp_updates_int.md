---

This work is licensed under the Creative Commons CC0 License

---

# Build BridgePoint with new generator (pyrsl 2) 
### xtUML Project Implementation Note


### 1. Abstract

This note describes the changes made and work performed to update the BridgePoint
code base to be able to build with pyrsl 2.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9481](https://support.onefact.net/issues/9481)    
<a id="2.2"></a>2.2 [BridgePoint DEI #9911](https://support.onefact.net/issues/9911) Build BP with pyrsl 1  
<a id="2.3"></a>2.3 [BridgePoint DEI #10322](https://support.onefact.net/issues/10322) pt_antlr does not properly handle options when processing oal.bnf  
<a id="2.4"></a>2.4 [BridgePoint DEI #10323](https://support.onefact.net/issues/10323) ui.properties test code gen failure with pyrsl2  
<a id="2.5"></a>2.5 [BridgePoint DEI #10337](https://support.onefact.net/issues/10337) JUnit test build and run cleanup  

### 3. Background

BridgePoint has used pyrsl 0.5 for several years.  In that time, new features 
and updates have been added to generator that provide significant performance 
improvements.  Significantly, though, pyrsl 2 changed to correct role phrases 
in reflexive traversals.  This means that any model compiler traversals across 
reflexives must be swapped.   

This work fixes MC-Java and the plug-in archetypes to build the BridgePoint code
base properly with pyrsl 2.  

This work builds on earlier work [2.2] to build the source code with pyrsl 1. This
INT does not repeat the changes captured in the INT for [2.2], so the reader is
expected to already be familiar with that note.  A guiding principle of this 
phase of the work is that the generated code output by pyrsl 2 should be identical 
to the generated code output by pyrsl 1.  The development process involved many 
build and compare cycles.   

### 4. Requirements

4.1 BridgePoint and bptest plugins shall generate correctly with pyrsl2.  
4.2 BridgePoint code generated with pyrsl2 shall be identical to code generated
  with pyrsl 1.   

### 5. Work Required

5.1 Swap reflexive association role phrases.  
There are many places where reflexives are traversed in MC-Java and the plug-in
archetypes.  Most of these use the role phrases 'succeeds' and 'precedes'.  A 
global search and replace was performed on all the source archetypes to swap 
these phrases.  This led to many changes like this:   
```xml
-  .select one next_link related by first_link->ACT_LNK[R604.'succeeds']
+  .select one next_link related by first_link->ACT_LNK[R604.'precedes']
```  
5.1.1  However, not all reflexives use these role phrases.  The ones that did 
not were found during the process of translation due to translation errors or 
places where the generated code did not match with the old generated code. Other 
examples are 
```xml
-  .select many parent_eos related by attr_parent_eo->EO[R1.'is_parent_of']  where (selected.writePosition!="none")
+  .select many parent_eos related by attr_parent_eo->EO[R1.'is_first_child_of']  where (selected.writePosition!="none")
```
and
```xml
-  .select any pre_sibling related by attr_parent_eo->EO[R2.'follows']
+  .select any pre_sibling related by attr_parent_eo->EO[R2.'precedes']
``` 
 
5.2 Core build dependency fix    
The old version of generator did not validate all referential links.  This meant
that PEI data was not required to have correct values in all referential attributes. With
pyrsl2 this is no longer the case.  The model is required to be self-consistent, containing
valid data in referentials.  In order to support this requirement, the `ooa.pei.sql`
must be available to the main part of the core build.  The affected build targets
are modified to include this dependency and build it if necessary.   

5.3 Fix invalid use of keyword  
In `src/MC-Java/java.arc` a local variable was named `unique_id`.  This is an invalid
reuse of a keyword.  The local variable is renamed.   

5.4 Remove `${info.unique_num}`   
This is an architectural feature that generates a unique number each time it is
invoked.  Using this feature can be handy but does not yield consistent code from
generation to generation which makes obtaining clean diffs impossible.  The places
where this feature is used are updated to use some other counting/uniqueness 
mechanism that will produce consistent code from build to build.  

5.5 Create valid comparisons  
Pyrsl 2 is more strict in the way it handles data processing. Specifically, the
archetypes contained places where data of differing types was handled without 
incident.  In pyrsl 2 these led to translation-time errors or they simply did
not work as expected and led to code that didn't match the output of the old 
generator.  These places were fixed.  Here is an example of one such place from
`src/MC-Java/verify_selects.arc`.  In this case, `ACT_ACT.Action_Id` is of type
`unique_id`:  
```xml
-  .select any action from instances of ACT_ACT where (selected.Action_Id == "${function.Sync_ID}")
+  .select any action from instances of ACT_ACT where ("${selected.Action_Id}" == "${function.Sync_ID}")
```  

5.6 Use `relate`  
In pyrsl2, direct referential ID assignment is no longer allowed.  The model compiler
must be changed to instead use the `relate` statement.  Here is an example of this 
type of change:  
```xml
    .create object instance dom of S_DOM
    .select any dt from instances of S_DT
    .if (not_empty dt)
-      .assign dom.Dom_ID = dt.Dom_ID
+      .relate dom to dt across R14
    .end if
```  

5.7 pt_antlr issue  
The first phase of `org.xtuml.bp.als` plugin build uses pt_antlr to process `oal.bnf` 
and create a model of the grammar and then dump out insert statements into `oal_grammar.sql`
that is then used in a later step.  

The options block in `oal.bnf` is not handled properly which leads to invalid data 
generated into `oal_grammar.sql`. To get around this problem we have committed the 
generated file `oal_grammar.sql` since it changes very infrequently. Until this issue 
is fixed, any edits to `oal.bnf` will require hand editing the `oal_grammar.sql` file.   

The root of the problem we hit is with pt_antlr and not pyrsl2. For grammar options, 
pt_antlr is creating Rule Reference instances because the options do not start with 
capital letters (see TOKEN_REF and RULE_REF rules in pt_antlr's antlr.g). Because they 
are Rule References with no matching rule, the referential attribute RR.rule_name is 
blanked when pyrsl2 does a batch relate of this data. We get around this problem if the 
option keys and values are Terminals instead of Rule References.   

This issue is captured in [2.3].  The target to generate the file, the clean 
target to remove it, and the appropriate line in `.gitignore` are commented out for now.   

5.8 Modify IO MDL and UI Text generation  
The build of `org.xtuml.bp.io.mdl` and `org.xtuml.bp.ui.text` previously used a 
persisted gen database file in between build targets.  This is removed in favor 
of a single build step that does not require the persisted database. The new 
method is faster and greatly simplifies the build of this plug-in.  

5.9 Quiet insert mismatches  
Many calls to `xtumlmc_gen_erate` are modified to use the `-qim` flag.  This flag
causes pyrsl to not output warnings when we import PEI data that has less fields in
the INSERT statement than are present in the schema.  
 
5.10 ui.properties test code gen failure with pyrsl2 
A build issue was uncovered in the translation of `org.xtuml.bp.ui.properties.test` that
is captured in [2.4].  The issue is related to model data and the deprecated 
schema field `SMspd_ID`.   
 
### 6. Implementation Comments

None.

### 7. Unit Test

7.1 Build the bridgepoint and bptest plugins branch `9911_build_bp_updates` with 
pyrsl 1 and `9481_build_bp_updates` with pyrsl2 in different workspaces.  Compare 
the source code files from each workspace.  Verify there are effectively no differences.  

7.2 Create a build server that has pyrsl2 in the host BridgePoint development 
environment. Build the development branches with full JUnit testing.  
  * There are a few more failures in the testing of the branch on the server than
  the current nightly build.  These were tested locally and shown not to reproduce.
  The issue of the difference in results is captured in [2.5] to be resolved after
  the promotion of this issue.  
   
### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__   
Branch: __9481_build_bp_updates__

<pre>
 doc-bridgepoint/notes/9911_build_bp_updates_int.md |  157 +
 src/MC-Java/arch_utils.inc                         |    6 +-
 src/MC-Java/block.inc                              |    6 +-
 src/MC-Java/build.xml                              |   36 +-
 src/MC-Java/java.arc                               |   16 +-
 src/MC-Java/mfp_utils.inc                          |   12 +-
 src/MC-Java/model_consistency.inc                  |   73 +-
 src/MC-Java/model_consistency_functions.inc        |   20 +-
 src/MC-Java/schema_gen.arc                         |    4 +-
 src/MC-Java/statement.inc                          |    4 +-
 src/MC-Java/verify_selects.arc                     |    6 +-
 src/org.xtuml.bp.als/arc/content_assist.arc        |    2 +-
 src/org.xtuml.bp.als/arc/grammar_enhancer.arc      |   62 +-
 src/org.xtuml.bp.als/generate.xml                  |   16 +-
 src/org.xtuml.bp.als/sql/.gitignore                |    2 +-
 src/org.xtuml.bp.als/sql/oal_grammar.sql           | 3646 +++++++++++++++++++
 src/org.xtuml.bp.compare/generate.xml              |    2 +-
 src/org.xtuml.bp.core/arc/create_global_action.inc |    2 +-
 src/org.xtuml.bp.core/arc/create_object_action.inc |    4 +-
 .../arc/create_object_inspector.inc                |    8 +-
 src/org.xtuml.bp.core/arc/create_rename_action.inc |    2 +-
 .../arc/create_selection_dialog_action.inc         |    2 +-
 src/org.xtuml.bp.core/arc/function_body.inc        |    2 +-
 src/org.xtuml.bp.core/arc/page.inc                 |    2 +-
 src/org.xtuml.bp.core/arc/ui_processing.inc        |   10 +-
 src/org.xtuml.bp.core/arc/wfl_block.inc            |   89 +-
 src/org.xtuml.bp.core/arc/wfl_pop.arc              |   14 +-
 src/org.xtuml.bp.core/arc/wfl_processing.inc       |   12 +-
 src/org.xtuml.bp.core/arc/wizard.inc               |    4 +-
 src/org.xtuml.bp.core/generate.xml                 |   16 +-
 .../import_functions/import_functions.xtuml        |   14 +-
 .../source/Reference/Metamodel/documenter.arc      |    6 +-
 src/org.xtuml.bp.io.core/arc/class2table.arc       |    6 +-
 .../arc/crosslink_validation.inc                   |    2 +-
 src/org.xtuml.bp.io.core/arc/export_functions.inc  |   10 +-
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc   |   10 +-
 .../arc/generate_configurable_components.inc       |   20 +-
 .../arc/create_persistence_meta_data.arc           |   32 +-
 .../arc/gen_uuid_convert_util.arc                  |    8 +-
 src/org.xtuml.bp.io.mdl/generate.xml               |  189 +-
 src/org.xtuml.bp.model.compare/generate.xml        |    2 +-
 src/org.xtuml.bp.ui.canvas/generate.xml            |    8 +-
 .../arc/create_adapters.inc                        |   18 +-
 src/org.xtuml.bp.ui.explorer/generate.xml          |    4 +-
 .../arc/BuildPropertySource.arc                    |   15 +-
 src/org.xtuml.bp.ui.properties/generate.xml        |    2 +-
 src/org.xtuml.bp.ui.session/generate.xml           |    2 +-
 .../arc/create_editorinput_factories_java.arc      |    2 +-
 .../arc/create_modeladapter_java.arc               |    2 +-
 src/org.xtuml.bp.ui.text/arc/create_plugin_xml.arc |    4 +-
 src/org.xtuml.bp.ui.text/generate.xml              |  100 +-
 .../arc/create_generic_adapters.inc                |   15 +-
 src/org.xtuml.bp.ui.tree/templates/generate.xml    |    2 +-
</pre>

Fork/Repository: __keithbrown/bptest__   
Branch: __9481_build_bp_updates__
<pre>
 .../arc/create_action_test.arc                     |  3 ++-
 src/org.xtuml.bp.core.test/generate.xml            | 16 ++++++-------
 src/org.xtuml.bp.model.compare.test/generate.xml   |  2 +-
 .../arc/gen_test_matrix.arc                        | 12 +++-------
 src/org.xtuml.bp.ui.canvas.test/generate.xml       |  6 ++---
 .../arc/create_view_test.arc                       | 25 ++++++++++++++++-----
 src/org.xtuml.bp.ui.properties.test/generate.xml   |  6 ++---
</pre>

### End

