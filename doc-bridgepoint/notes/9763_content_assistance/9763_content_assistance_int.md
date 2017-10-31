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

</pre>

Fork/Repository: leviathan747/bptest
Branch: 9870_autocomplete_tests

<pre>

</pre>

Fork/Repository: leviathan747/models
Branch: 9870_autocomplete_tests

<pre>

</pre>

### End

