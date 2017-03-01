---

This work is licensed under the Creative Commons CC0 License

---

# Implement mechanism to prevent non-supported elements from persisting MASL
### xtUML Project Design Note

1. Abstract
-----------
Some types of activities are not supported by MASL. These activities should not
generate a signature to a `.masl` file.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9041 Implement mechanism to prevent non-supported elements from persisting MASL](https://support.onefact.net/issues/9041)  
<a id="2.2"></a>2.2 [#9039 Signatures for MASL project terminator services are generated wrong](https://support.onefact.net/issues/9039)  
<a id="2.3"></a>2.3 [#9039 old implementation note](../9039_project_terminator_signatures/9039_project_terminator_signatures_int.md)  

3. Background
-------------
Before continuing, the reader should fully read the old implementation note for
issue #9039 [[2.3]](#2.3). Especially focus on section 6.2. Note that the
implementation was reverted due to need for further analysis (see section 6.0).

4. Requirements
---------------
4.1 A mechanism shall be put in place to reliably prevent non-supported elements
from producing MASL signatures  
4.1.1 The mechanism shall be able to be toggled by the user  
4.1.2 The mechanism shall be designed to default to the desired behavior when
possible  

5. Analysis
-----------

5.1 Non-supported activities

Some activities in xtUML do not map to a MASL activity. If a signature is
provided for these activities, the resulting textual MASL model is incorrect.
They are:  
- Derived attributes  
- Transition actions  
- Port messages when...  
  - The port is a provided port on a domain component or  
  - The port is a required port on a project component  

5.2 "None" dialect

A new dialect "None" could be introduced alongside the other dialects. If the
activity is set to the "None" dialect, the activity will not be persistent and
no editor will be available for the activity. In this way, when a user is
editing a MASL model, non-supported elements can have the "None" dialect and no
MASL signature will be provided for them.

5.2.1 UI behavior

Activities of the "None" dialect shall not have an associated editor. The MASL
and OAL editor options in the "Open With" menu shall be grayed out. The
description editor shall become the default editor on double click.

The "None" dialect shall be modifiable from the properties view with the other
dialects.

5.3 Defaults

The "None" dialect shall be default in certain strategic situations.

5.3.1 Transitions and derived attributes

Transition activities and derived attributes shall default to the "None" dialect
when created if the default action language dialect preference is set to "MASL"

5.3.2 Port messages

Port messages shall default to the "None" dialect on interface formalization if
the default action language dialect preference is set to "MASL" _**and**_ if the
port is not a required port on a domain component or a provided port on a
project component. If the default action language dialect preference is set to
"MASL" and the port is not one of the two aforementioned cases, the dialect
shall be set to "MASL". If the default action language dialect is set to "OAL",
the dialect shall be set to "OAL".

Domain components and project components are identified by the existence of the
string "masl_domain" in the component description and "masl_project" in the
package description of the containing package.

5.3.3 Table of default dialect values on element creation

| | Derived attribute | Transition | Port message (required port on domain component) | Port message (required port on non-domain component) | Port message (provided port on a project component) | Port message (required port on a non-project component) |
| ------------------- | ---- | ---- | ---- | ---- | ---- | ---- |
| **Default is MASL** | None | None | MASL | None | MASL | None |
| **Default is OAL**  | OAL  | OAL  | OAL  | OAL  | OAL  | OAL  |

5.4 Other UI support

A context menu entry shall be provided on ports to reset all message dialects to
"None" or to the default action language dialect

6. Design
---------

7. Design Comments
------------------

8. User Documentation
---------------------

9. Unit Test
------------

End
---

