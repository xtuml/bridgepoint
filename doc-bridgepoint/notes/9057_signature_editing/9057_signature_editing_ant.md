---

This work is licensed under the Creative Commons CC0 License

---

# Editing MASL signatures can cause synchronization problems
### xtUML Project Analysis Note


1. Abstract
-----------
With a move to storage of MASL activities in `.masl` files instead of the
`Action_Semantics` in `.xtuml` files come growing pains. Currently, an
architecture is followed that has been labeled "structure is king". In this
architecture, the instances that represent the signature (the activity itself,
return type, parameters and their types, etc.) are stored as SQL in the `.xtuml`
file and only the action body itself is stored in text in `.masl` files and
inserted into the instances at load time.

The underlying problem is that all the action bodies for each persistable model
component are stored in one `.masl` file and are identified by MASL syntax
signatures. As stated above the _source_ that defines the signature is stored in
the `.xtuml` file and the signature in the `.masl` files is simply an
_architectural artifact_ to enable the MASL bodies to be identified by the
loader.

This mix of _source_ and _architecture_ is dangerous for the user. Users are
expected to edit only the source (action bodies) and leave the persistence
architecture (signatures) alone. When the rules are disobeyed, the loader can
fail in bad ways. For example, if a signature is changed, the importer may not
be able to identify the correct activity to apply the action body to, and then
when the activity is persisted again, the existing source action body would be
overwritten by empty string.


2. Document References
----------------------
<a id="2.1"></a>2.1 [#9057 Adding parameter to a MASL service gives an error.](https://support.onefact.net/issues/9057) Headline issue  
<a id="2.2"></a>2.2 [#8412 Parameter Synchronization](https://support.onefact.net/issues/8412) More discussion of this issue  
<a id="2.3"></a>2.3 [#8941 Updating masl and editting parametrs can result in masl being deleted](https://support.onefact.net/issues/8941) Previous work in this area  
<a id="2.4"></a>2.4 [#9001 Validating cell editor for Data Type Definition](https://support.onefact.net/issues/9001) Partial Xtext editor for type definitions  

3. Background
-------------
Some work to mitigate the effects of this problem has been done [[2.3]](#2.3). A
set of assertions were applied to recognize when import failures have occurred
and prevent the user from persisting if there is a potential for action language
to be overwritten. This has proven to be a double-edged sword because the user
can get into a situation where the tool will prevent him from persisting, but he
does not know how to fix the signatures that have changed.

For example, imagine a function package with 100 functions. The user opens the
`.masl` file and deletes 99 of the functions. On the next structural persist, he
gets a warning that displays a set of import failures caused by editing the
signatures. The tool will not let him persist until the signatures are restored
to their original state. The user is not in revision control. The user must now
carefully type in all 99 functions that were deleted and refresh to clear the
import errors. This is unacceptable.

Furthermore, for the common user, this type of error is too confusing. It would
be better if he were somehow prevented from editing import architectural
artifacts in the first place.

4. Requirements
---------------
4.1 Architecture and source form MASL activities shall be separated in the
MASL activity editing environment  
4.1.1 The user shall never be allowed to edit architectural artifacts in
BridgePoint  

5. Analysis
-----------

Before reading the analysis section, the reader should fully digest and
understand the abstract and background sections.

After analysis there are only two clear options that fulfill the requirements.

5.1 Text is king

One way to solve this problem is to simply stop storing signature instances as
SQL and use the signatures in the `.masl` files as the source. This architecture
has been labeled "text is king". This would solve the underlying issue because
`.masl` files would truly be entirely source. Text is king is the future. It is
a superior architecture in many ways, not the least of which is the ability to
quickly copy, edit, delete and otherwise modify services through text and not
mouse clicks.

5.1.1 Restrictions

For this architecture to work, certain restrictions would need to be set in
place (at least initially). `.masl` files must be read only from the perspective
of BridgePoint. If users are allowed to edit in text and BridgePoint can
re-persist the files on a structural change, there is a possibility of some
import failure (maybe due to syntax error) that causes source to be overwritten
when it should not. The assertion that `.masl` files are read only would mean
that adding, deleting, typing, and otherwise changing signatures from
BridgePoint (model explorer or properties) would be disabled. These actions
could be modified to simply open the `.masl` file to the right place.

5.1.2 Challenges and risks

Text is king is a relatively high risk option because it would introduce a major
change to the persistence architecture. There are several additional
difficulties associated with this design choice

5.1.2.1 Graphical activities

The restriction presented in 5.1.1 that activities cannot be added or deleted
directly from the BridgePoint structural editor is shocking but palatable. It is
not too much to ask for users to simply type a signature to add an activity
rather than clicking buttons. However, what is less palatable is creating and
deleting graphical activities.

There are two activities with associated graphical elements: states and
transitions. The typical mode of creating or deleting a state or transition is
through the canvas editor, and it would be unacceptable to restrict doing so. If
text is king, some mechanism or exception will need to be in place to allow
states and transitions to be added and deleted through the canvas editor.

This introduces quite a bit of complexity since it is much easier to simply say
"`.masl` files are read only". Perhaps a long term solution would be to make the
relationship between state machine actions (SM_ACT) and states/transitions
conditional -- allow states and transitions with no corresponding actions,
however analysis would need to be done to determine the effect on model verifier
and the model compilers.

5.1.2.2 Deferred and native services

Another challenge to text is king is deferred and native activities. MASL allows
deferred (abstract) operations on classes which have a signature but no action
language. Additionally, activities can be declared that do not have any action
language and are going to be replaced by activities written in the target
language. If `.masl` files are read only from the BridgePoint structural editor,
a mechanism would need to be in place to either allow edit of these special
activities or provide some sort of indication in the activity editor that they
are deferred or native.

5.1.2.3 Type references

xtUML does not do name based scoping and relies on unique IDs to identify types.
Because of this, types with duplicate names are allowed in xtUML. If text is
king, types would need to be identified by name only. This issue may be able to
be ignored for the time being since MASL does not allow duplicate type names and
this work would apply only to MASL models in the short term, however it is a
long term consideration.

5.2 Single activity editing

The second option is to modify the editor such that one activity is displayed in
a buffer with no signature so the user has only the opportunity to edit
signature data from the BridgePoint structural editor (model explorer and
properties). This option has fewer associated risks because it is more aligned
with how the OAL editor has worked for years.

5.2.1 Xtext editor

An Xtext editor can be provided that can edit a single activity at a time in the
memory model of BridgePoint. The editor could validate against existing MASL
files in the workspace to provide all the same content assist and context aware
features we love without revealing the signature for edit to the user.

5.2.1.1 Validating the signature

A similar editor has been created for editing type definitions in MASL
[[2.4]](#2.4). This editor provides a dummy domain in which to define a type.
The text of the domain definition is hidden and only the type definition is
visible and editable. This allows the syntax to validate correctly and provides
the basis for content assist without revealing unnecessary and confusing
details.

The body of an activity is linked to the parameters and the activities of
_implementation_ (definition) not _delcaration_. This would present a problem if
an Xtext editor is presented with no parameters. Using an editor similar to the
one described above, the signature could exist but be hidden from the user. This
would allow references to the parameters in the action body to be linked
properly.

The signatures to provide for the editor could be generated from the xtUML model
in the same way they have been generated for persistence to `.masl` files.

5.2.1.2 Duplicate activity definitions

The Xtext editor would also have to handle the existing definition that is
located on disk in a `.masl` file. If the editor is editing a buffer in memory
but linking against all the MASL data in the project, there will be a duplicate
on disk of the definition being edited in memory. This would have to be expected
and handled by the Xtext editor.

5.2.2 Persistence

Editing the MASL in the xtUML memory model frees the tool to persist in whatever
way convenient. Because of this, the persistence of action bodies to `.masl`
files does not have to be rolled back but simply editing `.masl` files by hand
would no longer be encouraged behavior. The Xtext editor accessed from within
BridgePoint would edit one activity body at a time in memory and then replace
the `.masl` file with the contents of the xtUML memory model.

Furthermore, it would break the Xtext editor to stop storing MASL in `.masl`
files. The Xtext editor validates that each declared activity in a `.mod` file
has a corresponding definition, so to take a step back and store MASL in
`.xtuml` files would result in Xtext warnings that no implementation could be
found.

5.3 Conclusions

The second option (5.2) shall be taken. It presents far less of a risk with much
more manageable and predictable challenges, and it does not sacrifice the
progress that has been made in the persistence of action language separate from
structure.

6. Work Required
----------------

7. Acceptance Test
------------------

End
---

