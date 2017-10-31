---

This work is licensed under the Creative Commons CC0 License

---

# Context sensitive help in the OAL editor
### xtUML Project Implementation Note

### 1. Abstract

Provide useful completion proposals at strategic points during the editing of
OAL activities.

### 2. Document References

<a id="2.1"></a>2.1 [#9763 Context-sensitive completion assistance for user-defined identifiers is provided while editing OAL activities.](https://support.onefact.net/issues/9763) -- Current Issue  
<a id="2.2"></a>2.2 [#9571 Enhanced OAL Editor (phase 1)](https://support.onefact.net/issues/9571) -- This issue is the parent of the headline issue  
<a id="2.3"></a>2.3 [#9571 design note](9763_content_assistance_dnt.md)  
<a id="2.4"></a>2.4 [#9718 Testing for Enhanced OAL Editor phase 1](https://support.onefact.net/issues/9718)  
<a id="2.5"></a>2.5 [#9749 use case document](../9749_usecases/9749_usecases_phase_1.md)  
<a id="2.6"></a>2.6 [test matrix](https://github.com/xtuml/bptest/blob/master/src/org.xtuml.bp.als.oal.test/matrices/non-generated/oal_autocomplete_matrix.txt)  

### 3. Background

See [[2.3]](#2.3) section 3

### 4. Requirements

See [[2.3]](#2.3) section 4

### 5. Work Required

5.1 Updated OAL BNF grammar to work with content assist. In some cases, the rule
lookahead predicates needed to be modified. A new rule was added for partial
block parses.  
5.2 Create `content_assist.arc`, `ooa_functions.arc`, and update
`grammar_enhancer.arc` to generate content assist invocations in the parser.  
5.3 Update the OAL parser java files to pass the content assist line and column
to the parser for content assist parses.  
5.4 Add `PartialParseRunnable.java` to handle running partial parses.  
5.5 Modify BNF xtUML model to include content assist function instances.  
5.6 Modify `create_core_plugin.inc` to add declaration for the content
assistance preference page.  
5.7 Add `ContentAssistPreferences.java` to create the content assist preference
page.  
5.8 Add content assist preferences to the preferences model and defaults.  
5.9 Build the `Proposal` subsystem.  
5.10 Implement the content assist functions in the "OAL Validation Functions"
and "OAL Validation Utility Functions" packages.  
5.11 Modify and add OOA of OOA class operations and bridges to support the
content assist functions.  
5.12 Add `ProposalTypes` EDT for specifying the type of proposal to display.  
5.13 Modify the core plugin class to get icons for those types.  
5.14 Add `DocumentUtil.java` to convert between absolute location (index) in a
document and line/column.  
5.15 Implement `OALCompletionProcessor.java`.  
5.16 Implement `OALCompletionProposal.java`.  
5.17 Implement `OALProposalSorter.java`.  
5.18 Modify the OAL editor and editor configuration classes to enable content
assist.  

### 6. Implementation Comments

6.1 Parsing performance issues

The design of this issue specified that the implementation note would explain
any changes in parsing technique that were chosen after the design and affect
parsing performance for content assist. Experiments were done at a technique of
partial parsing where the current block would be parsed starting at the most
recent parsed statement. This increased performance dramatically, however it had
many bugs and issues and was not able to be enabled for this implementation. The
partial parser remains in place, however it is behind a preference which is not
in the UI. In the future we would like to do more research with partial parsing.
Once it is fast enough, the "parse while editing" option could be removed.

6.2 Proposal ordering

The design note stated that the table that specifies the order of proposals will
be modified during the implementation. Here is the final ordering table:

| Proposal type (in sorted order) | Specific sort criteria |
|---------------------------------|------------------------|
Attribute                         | Alphanumerically       |
Operation                         | Alphanumerically       |
Signal To Provider                | Alphanumerically       |
Signal From Provider              | Alphanumerically       |
Operation To Provider             | Alphanumerically       |
Operation From Provider           | Alphanumerically       |
Function Parameter                | Model ordering         |
Operation Parameter               | Model ordering         |
Bridge Parameter                  | Model ordering         |
Event Data Item                   | Model ordering         |
Property Parameter                | Model ordering         |
Association                       | Numerically by rel num |
Variable                          | Alphanumerically       |
Event                             | Alphanumerically       |
External Entity                   | Alphanumerically       |
Port                              | Alphanumerically       |
Class                             | Alphanumerically       |
Function                          | Alphanumerically       |
Bridge                            | Alphanumerically       |
Enumeration Data Type             | Alphanumerically       |
Enumerator                        | Alphanumerically       |
Constant                          | Alphanumerically       |
Literal                           | Alphanumerically       |
Keyword                           | Alphanumerically       |

6.3 Proposal calculation cue

The design stated that the final form of Proposal Calculation Cue should be
documented in the implementation note. The model is as follows:
```
|-------------------------------------|
| Proposal Calculation Cue      P_PCC |
|------------------------------------ |
| Cue_ID: unique_id               {I} |
| Action_ID: unique_id        {R1602} |
| name: string                        |
| string_value: string                |
| int_value: integer                  |
|-------------------------------------|
```

6.4 Parse errors

The design brought up an issue where if "parse while editing" is off and content
assist is invoked where there are parse errors earlier in the body, content
assist will silently do nothing and the user will be confused. We have since
discovered that no users that we know about currently edit models with "parse
while editing" disabled. Therefore, they will not have this problem and no
solution will be implemented for this work.

### 7. Unit Test

7.1 Overview

Testing was done using a test matrix to generate tests. The matrix was designed
to generate an abbreviated test suite unless a flag is passed to generate all
the tests. The tests that are not included in the short suite are all negative
tests for each action home. The shortened suite tests only one action home for
each of these negative situations. There are approximately 7000 tests in the
shortened suite and approximately 39000 in the full suite.

The test matrix can be found at [[2.6]](#2.6)

7.2 Test design

7.2.1 Test model

A test model was created for testing which contains an identical OAL action body
in each of the ten possible action homes. Model elements are created in other
places to be used for content assist testing. The OAL bodies have comments in
strategic places which mark places for action language to be inserted.

7.2.2 General flow

Each test follows this general flow:

7.2.2.1 Get the correct action body based on the "AH" value  
7.2.2.2 Search for the insertion location in the body using the "S" value  
7.2.2.3 Get a partial statement prefix based on the "L" value  
7.2.2.4 Insert the partial statement in the correct location and invoke content
assist  
7.2.2.5 Collect the resulting proposals  
7.2.2.6 Get the list of expected proposals based on the "P" and "V" values  
7.2.2.7 Check to make sure that each possibility does or does not exist based on
the matrix  

7.2.3 Running tests on master

The test implementation uses Java reflection API to assure that the
implementation exists. It was a requirement for the testing that the tests
should be able to build and run (and all fail) with the master branch of the
`bridgepoint` repository. Because the test actually depends on some of those
classes being in place, it uses reflection to get those classes during initial
setup. If the classes do not exist, each test automatically fails. This design
allows the tests to run on master and with content assist in place.

7.2.4 Matrix formatter and unit test generator

As part of the test implementation, a matrix formatting tool was created. The
source is part of the org.xtuml.bp.test plugin and there is a launch
configuration for it found in that project as well.

The unit test generator Perl script was also modified to take an additional
argument `-c`. Matrices can include cells marked with "XC". When `-c` is
specified, tests are generated for these cells, but when it is not specified, no
test is generated for these cells. This can be useful for situations when some
tests should be disabled by default, but may be enabled later.

### 8. User Documentation

8.1 A new help page has been introduced in the BridgePoint help under `Reference
> User Interface > xtUML Modeling Perspective > Preferences` to document the
usage and options of the content assist feature.  

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint
Branch: 9763_content_assistance

<pre>

 doc-bridgepoint/notes/9749_usecases/9749_usecases_phase_1.md                                                                              |    5 +-
 doc-bridgepoint/notes/9763_content_assistance/9763_content_assistance_dnt.md                                                              |  546 +++++++++++
 doc-bridgepoint/notes/9763_content_assistance/9763_content_assistance_int.md                                                              |  221 +++++
 doc-bridgepoint/notes/9763_content_assistance/bnf.png                                                                                     |  Bin 0 -> 404807 bytes
 doc-bridgepoint/notes/9763_content_assistance/model.png                                                                                   |  Bin 0 -> 806096 bytes
 doc-bridgepoint/process/FAQ.md                                                                                                            |    6 +
 doc-bridgepoint/review-minutes/9763_content_assist_rvm.md                                                                                 |   67 ++
 src/org.xtuml.bp.als.oal/bnf/oal.bnf                                                                                                      |   17 +-
 src/org.xtuml.bp.als.oal/src/org/xtuml/bp/als/oal/ParseRunnable.java                                                                      |   14 +-
 src/org.xtuml.bp.als.oal/src/org/xtuml/bp/als/oal/PartialParseRunnable.java                                                               |  198 ++++
 src/org.xtuml.bp.als.oal/src/org/xtuml/bp/als/oal/TextParser.java                                                                         |  126 ++-
 src/org.xtuml.bp.als/arc/content_assist.arc                                                                                               |  138 +++
 src/org.xtuml.bp.als/arc/grammar_enhancer.arc                                                                                             |  343 +++++++
 src/org.xtuml.bp.als/arc/ooa_functions.arc                                                                                                |    6 +
 src/org.xtuml.bp.als/arc/validate_gen.arc                                                                                                 |   29 +-
 src/org.xtuml.bp.als/generate.xml                                                                                                         |   78 +-
 src/org.xtuml.bp.als/models/org.xtuml.bp.als/grammar/BNF/BNF.xtuml                                                                        |  196 +++-
 src/org.xtuml.bp.als/models/org.xtuml.bp.als/grammar/BNF/Content Assist Function/Content Assist Function.xtuml                            |  119 +++
 src/org.xtuml.bp.als/sql/.gitignore                                                                                                       |    2 +
 src/org.xtuml.bp.core/arc/create_core_plugin.inc                                                                                          |    5 +
 src/org.xtuml.bp.core/arc/create_core_plugin_class.arc                                                                                    |   15 +
 src/org.xtuml.bp.core/generate.properties                                                                                                 |    2 +-
 src/org.xtuml.bp.core/generate.xml                                                                                                        |    2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Association/Association.xtuml                                         |   77 ++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Class In Association/Class In Association.xtuml                       |   67 ++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Body/Block/Block.xtuml                                                            |   54 ++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Body/Body/Body.xtuml                                                              |   11 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Interface Operation/Interface Operation.xtuml                           |   37 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Interface Signal/Interface Signal.xtuml                                 |   29 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Datatypes/Datatypes.xtuml                                                         |  182 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml                                                        |   37 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Function/Function.xtuml                                                    |   37 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/External Entities/External Entities.xtuml                                         |  108 ++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Functions/OAL Validation Functions.xtuml                 | 8163 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Utility Functions/OAL Validation Utility Functions.xtuml | 2292 ++++++++++++++++++++++++++++++++++++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Proposal/Proposal Calculation Cue/Proposal Calculation Cue.xtuml                  |  175 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Proposal/Proposal List/Proposal List.xtuml                                        |  161 +++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Proposal/Proposal.xtuml                                                           |  773 +++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Proposal/Proposal/Proposal.xtuml                                                  |  236 +++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml                       |   34 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Operation/Operation.xtuml                                               |   39 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/ooaofooa.xtuml                                                                    |   28 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java                                                       |   21 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java                                                       |   29 +-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/ContentAssistPreferences.java                                                  |  248 +++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/DocumentUtil.java                                                                        |   45 +
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Preferences/HTML/CAprefs.png                                                   |  Bin 0 -> 210383 bytes
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Preferences/HTML/ContentAssist.html                                            |   60 ++
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Preferences/HTML/ContentAssist.md                                              |   71 ++
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/Preferences/HTML/Preferences.htm                                               |    2 +
 src/org.xtuml.bp.doc/topics_Reference.xml                                                                                                 |    1 +
 src/org.xtuml.bp.pkg/plugin_customization.ini                                                                                             |    7 +
 src/org.xtuml.bp.ui.text/META-INF/MANIFEST.MF                                                                                             |    1 +
 src/org.xtuml.bp.ui.text/src/ActionEditorPluginResources.properties                                                                       |    3 +
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/EditorConfiguration.java                                                                |    5 +-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/OALEditorPlugin.java                                                                    |    2 +-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/TextPlugin.java                                                                         |   11 -
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/activity/ActivityEditor.java                                                            |    2 +-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/contentassist/OALCompletionProcessor.java                                               |  372 +++++++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/contentassist/OALCompletionProposal.java                                                |  135 +++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/contentassist/OALProposalSorter.java                                                    |   86 ++
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/editor/oal/OALEditor.java                                                               |   17 +-
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/editor/oal/OALEditorConfiguration.java                                                  |   31 +-
 63 files changed, 15726 insertions(+), 98 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest
Branch: 9870_autocomplete_tests

<pre>

 src/org.xtuml.bp.als.oal.test/META-INF/MANIFEST.MF                                          |     4 +-
 src/org.xtuml.bp.als.oal.test/generate.xml                                                  |    40 +
 src/org.xtuml.bp.als.oal.test/matrices/non-generated/oal_autocomplete_matrix.txt            | 43065 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++---------------------------------------------------------------------------------------------------
 src/org.xtuml.bp.als.oal.test/pom.xml                                                       |    53 +
 src/org.xtuml.bp.als.oal.test/src/OALGlobalsTestSuite_Generics.java                         |     4 +-
 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/TestControl_Generics.java       |     7 +-
 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/completion/.gitignore           |     2 +
 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/completion/OalAutoComplete.java |   662 ++++
 src/org.xtuml.bp.test/.launches/MatrixFormatter.launch                                      |    13 +
 src/org.xtuml.bp.test/UnitTestGenerator.pl                                                  |    29 +-
 src/org.xtuml.bp.test/src/org/xtuml/bp/test/formatter/MatrixFormatter.java                  |   180 +
 11 files changed, 22538 insertions(+), 21521 deletions(-)

</pre>

Fork/Repository: leviathan747/models
Branch: 9870_autocomplete_tests

<pre>

 test/oal_autocomplete/.project                                                                                                                        |  12 ++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container.xtuml                                                                               | 328 ++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH01/AH01.xtuml                                                                     |  81 ++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH01/Class/Class.xtuml                                                              |  89 +++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH01/Class/InstanceStateMachine/InstanceStateMachine.xtuml                          | 689 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH02/AH02.xtuml                                                                     |  81 ++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH02/Class/Class.xtuml                                                              | 122 +++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH03/AH03.xtuml                                                                     | 130 ++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH04/AH04.xtuml                                                                     |  81 ++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH04/Class4/Class4.xtuml                                                            | 119 ++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH05/AH05.xtuml                                                                     | 164 +++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH06/AH06.xtuml                                                                     | 396 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH06/inner_component1/inner_component1.xtuml                                        | 432 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH07/AH07.xtuml                                                                     | 396 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH07/inner_component2/inner_component2.xtuml                                        | 431 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH08/AH08.xtuml                                                                     | 396 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH08/inner_component3/inner_component3.xtuml                                        | 432 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH09/AH09.xtuml                                                                     | 396 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH09/inner_component4/inner_component4.xtuml                                        | 431 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH10/AH10.xtuml                                                                     |  81 ++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH10/Class/Class.xtuml                                                              |  89 +++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH10/Class/InstanceStateMachine/InstanceStateMachine.xtuml                          | 343 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/Classes.xtuml                                                               | 860 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L11Class/ClassStateMachine/ClassStateMachine.xtuml                          | 274 +++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L11Class/InstanceStateMachine/InstanceStateMachine.xtuml                    | 432 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L11Class/L11Class.xtuml                                                     |  50 +++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L19/L19.xtuml                                                               |  55 ++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L19_link/L19_link.xtuml                                                     | 139 +++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L19_other/L19_other.xtuml                                                   |  55 ++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L2Class/L2Class.xtuml                                                       |  71 ++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L33/L33.xtuml                                                               |  65 +++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L6Class/L6Class.xtuml                                                       |  50 +++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L8Class/L8Class.xtuml                                                       |  55 ++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L8Class_two/L8Class_two.xtuml                                               |  83 ++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Container.xtuml                                                                     | 701 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Datatypes/Datatypes.xtuml                                                           | 221 +++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Functions/Functions.xtuml                                                           | 175 +++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Interface/Interface.xtuml                                                                     |  65 +++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Interface2/Interface2.xtuml                                                                   |  65 +++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible Elements.xtuml                                                             |  70 ++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/Classes.xtuml                                                      | 332 +++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L11Class/ClassStateMachine/ClassStateMachine.xtuml       | 274 +++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L11Class/InstanceStateMachine/InstanceStateMachine.xtuml | 274 +++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L11Class/invisible_L11Class.xtuml                        |  50 +++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L19/invisible_L19.xtuml                                  |  55 ++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L19_link/invisible_L19_link.xtuml                        |  32 ++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L19_other/invisible_L19_other.xtuml                      |  55 ++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L2Class/invisible_L2Class.xtuml                          |  71 ++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L33/invisible_L33.xtuml                                  |  65 +++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L6Class/invisible_L6Class.xtuml                          |  50 +++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L8Class/invisible_L8Class.xtuml                          |  55 ++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Classes/invisible_L8Class_two/invisible_L8Class_two.xtuml                  |  32 ++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Datatypes/Datatypes.xtuml                                                  | 221 +++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Functions/Functions.xtuml                                                  | 175 +++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Invisible Elements/Invisible/Invisible.xtuml                                                            | 148 +++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/oal_autocomplete.xtuml                                                                                  |  80 ++++++++++++++
 56 files changed, 11174 insertions(+)

</pre>

### End

