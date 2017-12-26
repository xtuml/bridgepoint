---

This work is licensed under the Creative Commons CC0 License

---

# Inconsistencies with file contents and tool
### xtUML Project Implementation Note

### 1. Abstract

Inconsistencies with file contents and tool

### 2. Document References

<a id="2.1"></a>2.1 [#9696 Inconsistencies with file contents and tool](https://support.onefact.net/issues/9696)  

### 3. Background

A good description of the issue is provided in [[2.1]](#2.1). The reader should
refer to the issue description before continuing.

3.1 MASL service bodies

There are several xtUML action bodies that all map to "service" in MASL. They
are the following:

3.1.1 Provided operations  
3.1.2 Provided signals  
3.1.3 Required operations  
3.1.4 Required signals  
3.1.5 Operations  
3.1.6 Bridges  
3.1.7 Functions  

3.2 "Dialog of Death"

There has been a chronic issue with using BridgePoint with MASL where
inconsistencies between the `.masl` files and the `.xtuml` files can cause a
state that is difficult for the user to escape.

As an example, imagine a user edits a `.masl` file by hand and corrupts the file
with bad syntax. Then, the MASL importer runs and is unable to properly parse
the file, so the `Action_Semantics` fields are populated with empty strings.
Finally, the user makes a structural change that causes the file to be
re-persisted. The actions in the `.masl` file will be overwritten by empty
strings and potentially hundreds of lines of MASL actions could be lost. To
prevent this, a dialog was introduced to show before a persist in the case where
the tool suspects that there has been a data corruption in the `.masl` file. It
asks the user to fix the issue before continuing.

This is good in theory, but it can be very very bad. In many cases, the user
does not know where the error in the `.masl` file originated and so he cannot
find it to rectify it. Additionally, there are bugs in the tool that can cause
the `.masl` file to be corrupted, in which case the user definitely won't know
where the error is. The dialog does not present an option to "continue anyway",
so often times the user gets in a situation where they cannot continue to do
their work.

This issue is one of those situations where the tool can introduce a corruption
into the `.masl` file. In addition to fixing the presentation to match what the
user expects as correct MASL syntax, the solution to this issue will include a
mechanism to prevent the tool from ever corrupting a `.masl` file through
editing of an action body.

### 4. Requirements

4.1 Display "end state;" as the last line in MASL state action bodies  
4.2 Display "end service;" as the last line in MASL service bodies (See section
3.1)  
4.3 BridgePoint shall be able to properly load and persist MASL activities
(without hitting the "Dialog of Death") in cases where the user saves syntax
errors.  

### 5. Work Required

5.1 Overview

There are four main aspects of this work:

5.1.1 Mechanism to prevent DoD  
5.1.2 Import of MASL actions into xtUML model elements (as `Action_Semantics`)  
5.1.3 Export of MASL actions in xtUML model elements (as `.masl` files)  
5.1.4 Creation of new MASL activities  

5.2 Mechanism to prevent DoD  

The core of the issue with the "Dialog of Death" is that the import parser
relies on parsing the "end service;" or "end state;" phrase to terminate a body.
If there are more than one "end service;" or "end state;" or none at all, bad
things happen in parsing. There are checks in place to try to ensure that all
bodies are well formed when they are persisted, but there are holes in the
implementation. To be completely fool proof, the parser must rely on begin and
end delimiters that are not editable by the user.

For this purpose, two new tokens have been added to the grammar:
```
ACTIVITYBEGIN  : "//! ACTIVITY BEGIN. DO NOT EDIT THIS LINE." ('\r')? '\n' { newline(); };
ACTIVITYEND    : "//! ACTIVITY END. DO NOT EDIT THIS LINE." ('\r')? '\n' { newline(); };
```
These two tokens delimit the beginning and end end of an activity. They are
inserted automatically by the exporter, and excluded from the body by the
importer. Because these will exist in `.masl` files, they were designed to also
be parseable as comments by the Xtext MASL parser.

By using these special token delimiters, we can parse the body even if it
contains syntax errors (including the lack of an "end service;" or "end
state;").

5.2 Import of MASL actions

Currently, MASL activities are stored in a `.masl` file named after the action
home (PMC) that they reside in. For example, all MASL domain service in a
package would be stored in a single `.masl` file named after the package. All
class services would be stored in a single `.masl` file named after the class,
etc. There exists an ANTLR parser that parses these files, matches existing
xtUML elements based on signature, and inserts the actions into the
`Action_Semantics` field of the xtUML element.

The grammar for the action parser does not include "end" as part of the code
block itself, but the importer supplies it. Since the import grammar no longer
relies on "end state;" or "end service;" to parse a body (described above),
those keywords will be part of the body itself and will just be inserted into
the `Action_Semantics` string just like the rest of the body.

5.3 Export of MASL actions

Currently, the MASL exporter appends "end service;" or "end state;" to the end
of the action body before the activity is serialized to a `.masl` file on save.
If the body itself ends with the "end" keyword, only " service;" or " state;" is
appended.

To fulfill the requirements for this work, nothing will be appended to the
action body (except a line break for formatting), since "end state;" or "end
service;" will already be part of the body.

5.4 New activities

Currently, when new MASL activities are initialized, the `Action_Semantics`
field is populated with the following:
```
begin
  null;
end
```
This is done because the MASL grammar does not support bodies with zero
statements. To fulfill the requirements for this work, each activity
initialization shall be modified to provide either
```
begin
  null;
end state;
```
or
```
begin
  null;
end service;
```
where appropriate.

This change will be made in BridgePoint itself and the MASL to xtUML conversion
and export tools (`masl2xtuml` and `xtuml2masl`).

### 6. Implementation Comments

6.1 Model upgrade

Because this work changes the way `.masl` files are loaded and parsed from
disk, a mechanism will be needed which can upgrade existing models to the new
persistence format. A script using standard regular expressions tools will be
delivered with this issue (attached to the DEI). Note that this script will
only be necessary for existing MASL models created before this issue is
promoted.

### 7. Unit Test

7.1 No new unit test failures shall be observed  
7.2 No new MASL round trip failures shall be observed  
7.3 The use case presented in [[2.1]](#2.1) shall be tested without failure  
7.4 A model with `.masl` files persisted the "old way" shall be upgraded using
the script, imported, and modified without failure  

### 8. User Documentation

None

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint
Branch: 9696_masl_files

<pre>

 doc-bridgepoint/notes/9696_masl_files_int.md                                                                                                     | 204 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Provided Operation/Provided Operation.xtuml |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Provided Signal/Provided Signal.xtuml       |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Required Operation/Required Operation.xtuml |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Required Signal/Required Signal.xtuml       |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml                                                               |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Function/Function.xtuml                                                           |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/x2m_functions/x2m_functions.xtuml                                              |  10 ++------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine State/State Machine State.xtuml                              |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Operation/Operation.xtuml                                                      |   2 +-
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                                                                                |   2 ++
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                                                                 |   5 ----
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g                                                                                      |  18 +++++++------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/body.g                                                                                         |  47 ++++++++++++----------------------
 14 files changed, 242 insertions(+), 60 deletions(-)

</pre>

Fork/Repository: leviathan747/mc
Branch: 9696_masl_files

<pre>

 masl/parser/src/MaslWalker.g                          | 10 +++++-----
 model/masl/gen/masl/t.service_def_end.masl            |  1 -
 model/masl/gen/masl/t.state_def_end.masl              |  1 -
 model/masl/models/masl/masl/operation/operation.xtuml |  3 ---
 model/masl/models/masl/masl/routine/routine.xtuml     |  3 ---
 model/masl/models/masl/masl/state/state.xtuml         |  3 ---
 model/masl/src/masl/t.service_def_end.masl            |  3 ---
 model/masl/src/masl/t.state_def_end.masl              |  3 ---
 8 files changed, 5 insertions(+), 22 deletions(-)

</pre>

### End
