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
<a id="2.4"></a>2.4 [#9263 Add mechanism to distinguish MASL components and packages](https://support.onefact.net/issues/9263)  
<a id="2.5"></a>2.5 [#9266 Assure that action bodies are not created when not needed for MASL](https://support.onefact.net/issues/9266)    

3. Background
-------------
Before continuing, the reader should fully read the old implementation note for
issue #9039 [[2.3]](#2.3). Especially focus on section 6.2. Note that the
implementation was reverted due to need for further analysis (see section 6.0).

4. Requirements
---------------
4.1 A mechanism shall be put in the editor to reliably prevent non-supported
elements from producing MASL signatures  
4.1.1 The mechanism shall be able to be toggled by the user  
4.1.2 The mechanism shall be designed to default to the desired behavior when
possible  
4.1.3 The defaults shall be clearly enumerated as a table  
4.2 `m2x` shall produce models that use the mechanism properly  

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
activity is set to the "None" dialect, editing the activity will be restricted,
but the activity will still be persisted. This allows a user to "get the code"
back if the dialect is switched back to another language.

5.2.1 UI behavior

* Activities of the "None" dialect shall not have an associated editor.  
* The MASL and OAL editor options in the "Open With" menu shall be
  grayed out.  
* The description editor shall become the default editor on double
  click.  
* The "Action Semantics" shall not be editable from the properties view.  
* The OAL parser shall not parse the `Action_Semantics` string at any
  time.  
* The action language shall not appear on states or transitions in a
  state diagram.  
* The "None" dialect shall be modifiable from the properties view with
  the other dialects.  

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
string "masl_domain" or "masl_project" in the package description of the
containing package.

5.3.3 Table of default dialect values on element creation

| | Derived attribute | Transition | Port message (required port on domain component) | Port message (required port on project component) | Port message (provided port on a project component) | Port message (provided port on a domain component) |
| ------------------- | ---- | ---- | ---- | ---- | ---- | ---- |
| **Default is MASL** | None | None | MASL | None | MASL | None |
| **Default is OAL**  | OAL  | OAL  | OAL  | OAL  | OAL  | OAL  |

6. Design
---------

6.1 "None" dialect

A new enumeration shall be added to the `ActionDialect` type in OOA of OOA

6.2 UI updates

6.2.1 Condition will be added in `get_connector_text` of `Transition` and
`get_compartment_text` of `State Machine State` to check if the dialect is
"None". The action body shall not be returned if it is "None"

6.2.2 A check will be added in the `selectionChanged` method of the
`IActionDelegate` for the MASL editor action and the OAL editor action to
verify that the action dialect of the selection is not "None". If it is "None",
the open editor option shall be grayed out.

6.2.3 A check will be added in the canvas editor and the model explorer to
default to the description editor for elements which have a dialect of "None"
set.

6.2.4 A check will be added to the properties view to prevent action semantics
fields for "None" dialect elements from being edited.

6.2.5 A check will be added to skip parsing an activity of "None" dialect.

6.3 Editor defaults

The table defined in section 5.3.3 shall be implemented in the `initialize`
operation of each activity class. Here the default dialect preference will be
checked. Additionally to determine if a message is in a MASL domain, the
description of the package containing the containing component shall be
searched for the string "masl_domain".  To determine if a message is in a MASL
project, the description of the package containing the containing component
shall be searched for the string "masl_project".

This is not the safest way to determine the MASL-ness of an element. An issue
has been raised to implement a better mechanism [[2.4]](#2.4).

6.4 Model loader

The loader will be updated to set the dialect to "None" for activities that are
part of an automatic formalization in MASL projects. This occurs for
requirements in projects that correspond to the public services of a domain and
should never contain MASL.

Previously the dialect was set to OAL to prevent the action language from being
persisted in MASL. This was introduced as part of issue #9039. See [[2.3]](#2.3)
section 5.3.

6.5 `m2x`

`m2x` shall be updated to set the dialect of all transitions and derived
attributes to "None". Additionally set all the port messages for the public
domain service provision to be "None" dialect after formalization.

7. Design Comments
------------------
None

8. User Documentation
---------------------
None

9. Unit Test
------------
9.1 Run manual test [#9266](https://support.onefact.net/issues/9266)  


End
---

