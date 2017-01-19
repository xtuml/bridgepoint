---

This work is licensed under the Creative Commons CC0 License

---

# MASL rename refactoring with Xtext
### xtUML Project Analysis Note

1. Abstract
-----------
The Xtext editor has been extended to provide a hook to refactor MASL activities
on rename of qualified elements. This analysis note serves to enumerate the
qualified elements for refactor and to evaluate the current implementation for
promotion.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8261 MASL automatic reference maintenance](https://support.onefact.net/issues/8261) Headline issue  
<a id="2.2"></a>2.2 [Rename refactoring pull request](https://github.com/xtuml/bridgepoint/pull/300) Original pull request  

3. Background
-------------
Review [[2.2]](#2.2). This pull request was rejected to allow for evaluation and
bug fixes before promotion.

4. Requirements
---------------
4.1 Enumerate valid MASL rename actions  
4.1.1 Provide description of the current output  
4.1.2 Provide special instructions for MASL idiom when necessary  
4.2 Enumerate problems and questions about the pull request [[2.2]](#2.2)

5. Analysis
-----------

5.1 MASL rename/refactor elements

Elements marked with an `*` require additional comment

| **MASL type**                | **rename action** | **status**        | **TODO**                  |
|------------------------------|-------------------|-------------------|---------------------------|
| *type                        | CME or Properties | working for CME   | integrate with Properties |
| *structure member            | Definition field  | not implemented   | implement                 |
| *enumerator                  | Definition field  | not implemented   | implement                 |
| *domain service              | CME or Properties | working for CME   | integrate with Properties |
| domain service parameter     | CME or Properties | fails with errors | analyze failure           |
| *terminator                  | CME or Properties | working for CME   | integrate with Properties |
| *terminator service          | CME or Properties | fails with errors | analyze failure           |
| terminator service parameter | CME or Properties | fails with errors | analyze failure           |
| relationship phrase          | Properties only   | not implemented   | analyze failure           |
| object                       | CME or Properties | working for CME   | integrate with Properties |
| attribute                    | CME or Properties | working for CME   | integrate with Properties |
| object service               | CME or Properties | working for CME   | integrate with Properties |
| object service parameter     | CME or Properties | not tested        | test                      |
| state                        | CME or Properties | fails with errors | analyze failure           |
| *state parameter             | CME or Properties | not tested        | test                      |
| event                        | CME or Properties | fails with errors | analyze failure           |
| event parameter              | CME or Properties | not tested        | test                      |

5.1.1 Type

When a type name changes, every "typeref" type that is based off this type must
be renamed to reflect the change. This will need to be documented for the user.

5.1.2 Structure member

Structures and enumerated types are maintained as MASL text in the `Definition`
field of the UDT. Special parsing will be needed to trigger refactor when this
field is updated.

5.1.3 Enumerator

See 5.2

5.1.4 Domain service

Domain services follow visibility rules of pubic and private in the MASL idiom.
A public service is defined as a function which has a corresponding provided
message (matching signature) exposed through a port named after the domain. In
order to rename a public service, a user must first rename the interface message
and then rename the function. This will need to be documented for the user.

Currently the tool produces errors if the interface message is renamed.

5.1.5 Terminator

The port name is the important element to be renamed for terminators. However,
it is recommended to also rename the interface definition to avoid confusion.
This will need to be documented for the user.

5.1.6 Terminator service

To rename a terminator service, the user must rename the message in the
interface definition, not the port (interface reference). This will need to be
documented for the user.

5.1.7 State parameter

To rename a state parameter, the user must rename the event parameter for an
event that transitions to that state. This will need to be documented for the
user.

5.1.8 Other elements

Rename of other non-MASL elements (e.g. a package) result in errors from the
refactor utility. The utility should have the capability to fail silently on
rename of elements that are not supported.

5.2 Pull request questions [[2.2]](#2.2)

5.2.1 There is a change to `build.properties` in `debug.ui` and `session`
plugins. Why?

These changes eliminate compile errors in the respective files. I've added these
to earlier commits but they still don't seem to be merged to master.

5.2.2 The `xtext.masl` plugin is creating two new generated directories `text`
and `emf-gen` should these be added to the `.gitignore` file in
`xtext.masl.parent`?

Nope, please leave them checked in unless we automatically generate the artifacts
from the Xcore models.

5.2.3 Errors in refactor result in a popup error message. Is there a good reason
for this? It is usually preferred to avoid using distracting UI for errors with
the exception of critical errors.

Just didn't know what to do else. The popup appears if the pre-refactoring checks
of Xtext fail. This is when the user has already chosen to rename something. 
What should we do then instead? Silently fail and leave a corrupted workspace?
Siliently cancel the rename operation?

Maybe we should rather investigate and eliminate the issues that cause errors.


6. Work Required
----------------

6.1 Review issues and prepare pull request for promotion to master  
6.2 Prepare user documentation  

7. Acceptance Test
------------------
None

End
---

