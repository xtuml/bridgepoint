---

This work is licensed under the Creative Commons CC0 License

---

# Fix import parser in the io plugin
### xtUML Project Implementation Note

1. Abstract
-----------
The import parser for `.masl` files is buggy. It specifically does not handle
comments well. This issue is raised to analyze and fix the parser so that it can
parse an arbitrary block of code from a signature.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8918 Fix import parser in the io plugin](https://support.onefact.net/issues/8918) Headline issue  
<a id="2.2"></a>2.2 [#8941 Updating masl and editting parametrs can result in masl being deleted](https://support.onefact.net/issues/8941)  
<a id="2.3"></a>2.3 [#8722 Create MASL cheat sheets that explain how to use BridgePoint for MASL](https://support.onefact.net/issues/8722)  

3. Background
-------------
We have an ANTLR parser that handles importing of signature identified action
bodies. The parser is designed to be able to import arbitrary bodies of text
representing the action body of a model element with a signature that is
extended from the MASL syntax. This same signature will be used to import bodies
of any number of action languages, so it must be able to parse truly arbitrary
text. This is not an easy task. The current parser works but has several
weaknesses. This work will simplify and strengthen the parser.

4. Requirements
---------------
4.1 The import parser shall parse arbitrary text within a defined signature  
4.1.1 The body itself shall not be parsed according to its grammar (OAL, MASL,
etc.)  
4.1.2 The body shall not be "escaped" to facilitate parse  
4.2 The grammar of the signature shall remain as close to MASL as feasible  
4.3 Sufficient error reporting shall be present to inform the user of parse
failures  

5. Work Required
----------------

5.1 Update import parser grammar

The grammar rules for the parser have been greatly simplified. The new rules are
as follows:
- Each signature ends with the keyword `is` followed by one newline  
- Each signature ends with a single line reading exactly: `end <activity type>;`
  where `<activity type>` is the appropriate activity keyword (`service`,
  `state`, etc.). It is important that this phrase is on a line by itself with
  absolutely no whitespace or comment before or after it.  

Here is an example service signature:
```
public service SAC::resend_infos () is
begin
  // code block body...
end service;
```

The body that the parser would produce would be:
```
begin
  // code block body...
```

Any phrase `end <activity type>;` on a single line by itself is strictly
disallowed in the body of an activity and will trigger a parse error if
encountered. It should be noted that the only place where this would be valid
syntax in OAL or MASL is in a multi-line comment.

5.1.1 `end` keyword in MASL

In MASL the keyword `end` is part of the code body itself and not the signature,
however in the above definition of a signature, `end` is part of the delimiter
of the end of the body. This will result in the `end` keyword not being part of
the parsed body (as the example above) when it should be. To handle this, the
importer has been modified to append the `end` keyword in the case of importing
MASL.

5.1.2 Exporter enforcement

The exporter enforces this format when persisting MASL activities. In the case
of a MASL activity, the exporter is careful not to duplicate the `end` keyword
when generating the signature.

5.1.3 Error reporting

Errors from the parser are reported after load using the error logging
infrastructure in `CorePlugin`. The file name and line information where the
error occurred are included in the error message.

5.1.4 Stumbling blocks

There is one notable "silent" error. Consider this case:
```
public service ::foo () is
begin
  // body of foo
end service;    // user adds comment or whitespace here

public service ::bar () is
begin
  // body of bar
end service;
```

If a user were to add a comment or white space before or after `end service;` on
an activity that is not the last activity in the file an error will occur. The
parser will not recognize the offending line as the end tag, and will continue
to parse the body of `foo`. The resulting action body for `foo` in this example
will be:
```
begin
  // body of foo
end service;    // user adds comment or whitespace here

public service ::bar () is
begin
  // body of bar
```
and the body of `bar` will be empty string. The parser would not be able to
detect this failure because it is designed to be agnostic of the contents of the
bodies. __The user should never edit the signature of an activity.__

5.2 Initializing MASL activities

Unlike OAL, MASL activities cannot have an empty list of statements. Code has
been added to the initialize operations in the OOA of OOA to initialize new MASL
activities with the text body:
```
begin
  null;
end
```

6. Implementation Comments
--------------------------

6.1 Future work

Importer failures can present serious problems if a persist is triggered after
an import failure. If no action language is imported, the next time the model is
persisted, the activity files will get overwritten with empty activities and the
source will be lost. This behavior is observed in [[2.2]](#2.2). This work will
be considered when handling that issue. I envision a mechanism that flags import
failures and prevents persist.

7. Unit Test
------------
7.1 MASL round trip test with SAC   
7.1.1 Before export, load the model and verify there are no reported importer
errors  

8. User Documentation
---------------------
Issue #8722 [[2.3]](#2.3) has been updated to track the task of creating
documentation for this issue.

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 8918_import_parser  

<pre>

 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Provided Operation/Provided Operation.xtuml |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Provided Signal/Provided Signal.xtuml       |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Required Operation/Required Operation.xtuml |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Signal Provisions and Requirements/Required Signal/Required Signal.xtuml       |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml                                                               |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Function/Function.xtuml                                                           |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine State/State Machine State.xtuml                              |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/Transition/Transition.xtuml                                                |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/Attribute.xtuml                                                      |   4 ++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Operation/Operation.xtuml                                                      |   4 ++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java                                                                |   6 ++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/IModelImport.java                                                                                 |   1 +
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                                                                                |  10 +++++++++-
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                                                                 |  18 ++++++++++++++----
 src/org.xtuml.bp.io.core/generate.xml                                                                                                            |  14 +-------------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/.gitignore                                                                                     |   6 ------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImport.java                                                                                |  16 ++++++++++------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g                                                                                      |  42 ++++++++++++++++++++++++++++++++++--------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/body.g                                                                                         | 109 ++++++++++++++++++++++++++++++++++++++++++-------------------------------------------------------------------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/comment.g                                                                                      |  76 ----------------------------------------------------------------------------
 20 files changed, 157 insertions(+), 181 deletions(-)

</pre>

End
---

