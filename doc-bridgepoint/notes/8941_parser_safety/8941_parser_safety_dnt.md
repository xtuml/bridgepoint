---

This work is licensed under the Creative Commons CC0 License

---

# MASL Activity Parser Safety
### xtUML Project Design Note

1. Abstract
-----------
We now persist MASL in activity files separately from the SQL model data with
MASL style signatures. This presents a new problem of parsing these bodies into
the `Action_Semantics` field consistently. The parser must be resilient to
errors in the activities and allow multiple dialects to be parsed. Because of
these characteristics, the parser is vulnerable to undetected parse failures
that can cause MASL to be lost. This issue is raised to define and enforce a set
of assertions about imported MASL to prevent unwanted overwriting of MASL files
after a parse failure.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8941 Updating masl and editing parameters can result in
masl being deleted](https://support.onefact.net/issues/8941) Example of the
problem  

3. Background
-------------
Issue #8941 [[2.1]](#2.1) is being used to track this work because it is a prime
example of how a parse failure can cause MASL to be deleted. The scenario is
that a failure in the parser causes empty strings in the `Action_Semantics`
fields. The error is not sufficiently reported (or ignored) and a subsequent
persist is triggered.  Now, the empty strings are persisted as the body of the
activity and the original source is overwritten.

4. Requirements
---------------
4.1 Detect MASL import parse failures  
4.2 Notify or prevent a user before potentially overwriting MASL source  
4.3 Notify or prevent a user before potentially exporting bad MASL data  
4.4 User experience  
4.4.1 The user shall have sufficient warning before performing an unsafe action  
4.4.2 The user shall not be interrupted unnecessarily  

5. Analysis
-----------

5.1 Import parser assertions

Because the import parser is designed to be resilient to errors and parse
arbitrary action language dialects, there are ways to cause "parse failures"
without actually tripping up the parser. For example:
```
public service ::foo() is
begin
  // this is a service called foo
end service;    // bad comment

public service ::bar() is
begin
  // this is a service called bar
end service;
```
Since the parser accepts arbitrary lines of text until it reaches the exact
pattern `^end service;$`, the seemingly inculpable comment `// bad comment`
causes the parser to import the entire block into the body of `foo` while
importing an empty string into `bar`. This is clearly a "parse failure", however
it is not caught as an error by the parser.

Additional assertions are needed to ensure that these parse failures do not
result in a persist of bad data:

5.1.1 No parse errors

Any error raised by the lexer or parser shall be flagged as an import failure.

5.1.2 No empty bodies

MASL syntax does not allow for action bodies to be empty. They must have at
least one statement. Therefore, any action body parsed as an empty string shall
be flagged as an import failure.

5.1.3 Number/type of actions match

The existing model data can be used to match that the exact number and types of
activities (functions, operations, states, etc.) are matched exactly. Any
inconsistency in number or type of parsed activities with the model data is
flagged as an import failure.

5.2 Granularity

Since activities are stored in one file per persistable model component
(package, component, class, state machine), these assertions will be applied on
a per PMC basis.

6. Design
---------

6.1 Assertion checking

The assertions laid out in the analysis shall be checked at import time in the
import class (`ImportModelComponent`).

6.2 Flagging failures

A flag shall be added to `PersistableModelComponent` to denote a previous import
failure. When an assertion fails, this flag will be set. When a full import of
the PMC occurs with no failures, the flag will be unset. The default shall be
unset. A message field shall be introduced to contain information regarding the
failure(s).

6.3 Notifying the user

When a persist of a `PersistableModelComponent` is triggered, the aforementioned
flag shall be checked. If the PMC is flagged with import failures, a popup shall
appear warning the user that he is potentially performing an unsafe action. The
message will be displayed with information on where the error occurred and
possible ways to resolve the issue. The user shall be presented with the option
to cancel the persist, or continue.

The user should then find and fix the problem causing the import failure and
refresh the workspace. This will trigger a reimport and unset the flag.

6.3.1 MASL export

The same mechanism will be used to notify a user if an export operation is
triggered with an import failure flag set.

6.4 User experience considerations

It is a specific design decision to **_not_** inform the user of parse failures
immediately after import. The reason for this decision is considering the case
when a user is in the middle of an intermediate state. Error popups would be a
distraction and the user may be fully aware of the inconsistency.

7. Design Comments
------------------

7.1 Use cases

7.1.1 User adds an activity through the text editor
* A number/type mismatch would be caught.

7.1.2 User deletes an activity through the text editor
* An empty body would be caught
* A number/type mismatch would be caught

7.1.3 User deletes the entire body of an activity
* An empty body would be caught

7.1.4 User changes signature with text editor
* An empty body would be caught
* A number/type mismatch would be caught

7.1.5 User changes signature with invalid syntax
* A parse error is caught

7.1.6 User inadvertantly inserts comment or whitespace on end tag line
* An empty body would be caught
* A number/type mismatch would be caught

7.1.7 User has an unsaved file in the text editor, changes a signature through
Model Explorer, saves the file
* An empty body would be caught
* A number/type mismatch would be caught

8. User Documentation
---------------------
None

9. Unit Test
------------

End
---

