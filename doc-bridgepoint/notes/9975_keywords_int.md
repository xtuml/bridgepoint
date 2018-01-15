---

This work is licensed under the Creative Commons CC0 License

---

# Content assist for relate statement (Saab 2.3)
### xtUML Project Implementation Note

### 1. Abstract

Excerpt from the user report:

> Some keywords are supported(relate), but some are not(to, across). It is
> expected that all keywords (those who are marked purple), are supported.

### 2. Document References

<a id="2.1"></a>2.1 [#9975 Content assist for relate statement (Saab 2.3)](https://support.onefact.net/issues/9975)  
<a id="2.2"></a>2.2 [#10007 keyword content assist analysis note](10007_keyword_content_assist_ant.md)  
<a id="2.3"></a>2.3 [#9763 content assistance design note](9763_content_assistance/9763_content_assistance_dnt.md)  
<a id="2.4"></a>2.4 [#9749 phase 1 use cases](9749_usecases/9749_usecases_phase_1.md)  
<a id="2.5"></a>2.5 [keyword test model](https://support.onefact.net/attachments/download/1057/10007_keywords.zip)  
<a id="2.6"></a>2.6 [auto complete test matrix](https://github.com/xtuml/bptest/blob/master/src/org.xtuml.bp.als.oal.test/matrices/non-generated/oal_autocomplete_matrix.txt)  
<a id="2.7"></a>2.7 [#10011 Remove support for keywords as identifiers](https://support.onefact.net/issues/10011)  

### 3. Background

When content assist was first implemented, many keywords were included in
proposals, however all keyword proposals were not exhaustively analyzed and
implemented. This work is to reasonably implement the missing proposals for
keywords.

The reader should review the #10007 analysis note before continuing
([[2.2]](#2.2)).

### 4. Requirements

4.1 Keyword proposals shall be implemented as recommended in the #10007 analysis
note [[2.2]](#2.2)  

### 5. Work Required

5.1 Overview

There were 17 missing proposals that were recommended by the analysis to be
added. For most of these, it was a simple case of adding a new OAL validation
function in the OOA of OOA.

5.2 Added functions

The following functions were added:

5.2.1 `Message_invocation_tok_rparen_content_assist`  
5.2.2 `Create_event_statement_local_variable_content_assist`  
5.2.3 `Event_spec_event_label_content_assist`  
5.2.4 `Event_spec_tok_times_content_assist`  
5.2.5 `Event_spec_tok_rparen_content_assist`  
5.2.6 `Event_spec_event_meaning_content_assist`  
5.2.7 `For_statement_local_variable_content_assist`  
5.2.8 `Relate_statement_inst_ref_var2_content_assist`  
5.2.9 `Relate_statement_inst_ref_var4_content_assist`  
5.2.10 `Relate_statement_relationship_content_assist`  
5.2.11 `Relate_statement_phrase_content_assist`  
5.2.12 `Unrelate_statement_inst_ref_var2_content_assist`  
5.2.13 `Unrelate_statement_inst_ref_var4_content_assist`  
5.2.14 `Unrelate_statement_relationship_content_assist`  
5.2.15 `Unrelate_statement_phrase_content_assist`  
5.2.16 `bridge_content_assist`  
5.2.17 `transform_content_assist`  

Each one makes invocations to `single_keyword_content_assist` to add keywords
that apply.

5.3 Updated `Statement_begin_content_assist`

The function `Statement_begin_content_assist` was updated to invoke
`bridge_content_assist` and `transform_content_assist` after the `bridge` and
`transform` keywords at the beginning of a statement.

5.4 `param` and `self`

For the places where keywords `param` and `self` were appropriate, additional
filtering was introduced to only allow `self` in instance bodies and to only
allow `param` if the body is passed parameters. This is the same filtering logic
that is used for proposals at the beginning of a statement.

5.5 Updated test model and test matrix

The test model and matrix were updated to add testing for these new cases. More
details can be found in section 7.2.

### 6. Implementation Comments

6.1 `create object instance foo ...`

The keyword `of` in the create statement (5.4.1.4 in [[2.2]](#2.2)) was left
unimplemented. Because of the way the grammar was written to allow keywords as
identifiers, there was no way to implement this proposal without seriously
altering the grammar. An issue has been raised to deprecate keywords as
identifiers [[2.7]](#2.7). At that point, this proposal can be added.

### 7. Unit Test

7.1 Existing unit tests shall pass  

7.2 Test new cases

17 new test locations (L) and 6 new possibilities (P) have been added to the
unit test matrix [[2.6]](#2.6) for this work. Approximately 3000 new tests have
been introduced. These new tests shall pass.

### 8. User Documentation

None

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9975_keywords  

<pre>

 doc-bridgepoint/notes/10007_keyword_content_assist_ant.md                                                                                 |  472 ++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9975_keywords_int.md                                                                                                |  167 ++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Functions/OAL Validation Functions.xtuml                 | 1441 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Utility Functions/OAL Validation Utility Functions.xtuml |  201 ++++++++++++++++++++++
 4 files changed, 2281 insertions(+)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 9975_keywords  

<pre>

 src/org.xtuml.bp.als.oal.test/matrices/non-generated/oal_autocomplete_matrix.txt            | 13163 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-
 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/completion/OalAutoComplete.java |    46 +
 2 files changed, 13189 insertions(+), 20 deletions(-)

</pre>

Fork/Repository: leviathan747/models  
Branch: 9975_keywords  

<pre>

 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH01/Class/Class.xtuml                                              |   2 +-
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH01/Class/InstanceStateMachine/InstanceStateMachine.xtuml          |   6 +--
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH06/AH06.xtuml                                                     | 152 +++++++++++++++++++++++++++++++++++++++------------------------------
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH06/inner_component1/inner_component1.xtuml                        |   6 +--
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH07/AH07.xtuml                                                     | 152 +++++++++++++++++++++++++++++++++++++++------------------------------
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH07/inner_component2/inner_component2.xtuml                        |   6 +--
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH08/AH08.xtuml                                                     | 152 +++++++++++++++++++++++++++++++++++++++------------------------------
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH08/inner_component3/inner_component3.xtuml                        |   6 +--
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH09/AH09.xtuml                                                     | 152 +++++++++++++++++++++++++++++++++++++++------------------------------
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/AH09/inner_component4/inner_component4.xtuml                        |   6 +--
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/Classes.xtuml                                               | 346 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-----------
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L11Class/ClassStateMachine/ClassStateMachine.xtuml          |   4 +-
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L11Class/InstanceStateMachine/InstanceStateMachine.xtuml    | 297 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--------------------------------------------------------------
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L11Subclass/InstanceStateMachine/InstanceStateMachine.xtuml | 299 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L11Subclass/L11Subclass.xtuml                               |  50 +++++++++++++++++++++++
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L19_link/L19_link.xtuml                                     |   4 +-
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L2Class/L2Class.xtuml                                       |   2 +-
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Classes/L33/L33.xtuml                                               |   4 +-
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Container.xtuml                                                     |   8 ++--
 test/oal_autocomplete/models/oal_autocomplete/Container/Container/Functions/Functions.xtuml                                           |   4 +-
 20 files changed, 1206 insertions(+), 452 deletions(-)

</pre>

### End
