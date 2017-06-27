---

This work is licensed under the Creative Commons CC0 License

---

# Enhanced OAL Editor (phase 1, option 2)
### xtUML Project Analysis Note

### 1. Abstract

This note is an addendum to [[2.1]](#2.1). Before reading this note, the reader
should read the previous analysis in its entirety.

This note is written to call out a secondary option for phase one of the OAL
editor work. This is in response to a shifted priority of the requirements in
which the editor features which provide context assistance (AE3, AE8, AE9 in the
SRS [[2.2]](#2.2)) are of supreme importance and the requirements surrounding
multi-body editing and persistence are of little importance.

### 2. Document References

<a id="2.1"></a>2.1 [#9571 analysis note (phase 1)](9571_oal_xtext_editor_ant.md)  
<a id="2.2"></a>2.2 [#9415 OAL Xtext Editor SRS](https://docs.google.com/document/d/1gbqKooXBE5xBIv5bSS86pKOMKLS_W4t0GTjUfpvQvIY/edit)  

### 3. Background

Read [[2.1]](#2.1)

3.1 MASL Xtext editor

We have already implemented a text editor for MASL using Xtext that does
validation and context sensitive completion using information about the
structural model elements. The MASL editor gets its data for validation and
completion from a textual form of the model that is generated from the in memory
model. The flow goes as follows:

1. User creates or modifies a structural element (e.g. adds a class).  
2. User double-clicks to open a MASL activity.  
3. MASL "refresher" runs and produces a textual form of the model in MASL
   syntax. This `.mod` file is created in the `models/` directory in the
   project. This file contains in textual form the class that the user created.  
4. MASL Xtext editor parses the `.mod` file and all of the other activity files
   (including the file containing the activity that was double-clicked) into an
   EMF model in memory.  
5. User types `create object instance x of`.  
6. The Xtext editor queries the in memory EMF model for class definitions.  
7. The newly created class is displayed in a context sensitive completion pane.  

As part of the MASL work, an xtUML to MASL exporter (`x2m`) had already been
created and it made more sense to reuse this capability to leverage the natural
use case of Xtext where all of the sources are text files parseable by a single
grammar. It would have introduced much more code and would not have leveraged
the built-in capabilities of Xtext to implement a direct interface into the
meta-model.

### 4. Requirements

Sourced from the SRS [[2.2]](#2.2). See section 3 of the SRS for the full list
of requirements. 

As in [[2.1]](#2.1), the requirements that represent phase one have been copied
here. This set of requirements places priority on the specific requirements
mentioned in section 1.

4.1 Basic requirements

| ID | Description                                                                                                        |
|:---|:-------------------------------------------------------------------------------------------------------------------|
| B1 | All BridgePoint action homes shall support the functionality described by the other requirements of this document. |

4.2 Activity editor requirements

| ID  | Description                                                                                                                                                  |
|:----|:-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| AE1 | OAL keywords are highlighted when editing an OAL activity.                                                                                                   |
| AE2 | When editing an OAL activity each user-defined identifier within the activity is validated to ensure it is legal within the context in which it is used.  Invalid identifiers are marked, and a message explaining the error is provided. |
| AE3 | Context-sensitive completion assistance for user-defined identifiers is provided while editing OAL activities.                                               |
| AE5 | Problem markers shall be created in the problems view for errors OAL editors present in the activity editor.                                                 |
| AE6 | Opening a problem marker in the problems view shall open the activity editor and position the cursor at the error.                                           |
| AE8 | When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance. |
| AE9 | When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.                                      |

### 5. Analysis

5.1 Phase one requirements

In section 4, a subset of the requirements is called out as belonging to phase
one. In this version, the theme of the phase one requirements will be providing
an intelligent editor which can validate structural elements in text through an
interface to the meta-model. Textual persistence and signature editing will be
pushed to a later phase.

5.2 Verifier

Any path that is taken must consider the ability to single step using verifier.
Since OAL instances are still parsed by the OAL parser from the
`Action_Semantics` string, no extra work must be done to provide line and column
information to verifier. The `org.xtuml.debug.ui` plugin handles source location
and will not care what the underlying editor is. Since the parser will continue
to populate the model data debugging shall continue to work.

5.3 Options using Xtext

5.3.1 OOA of OOA interface with Xtext

By far the largest technical challenge presented by these requirements is how to
parse the textual OAL and link to instances of structural xtUML elements.

There is no textual persistence format for structural xtUML and therefore there
is no exporter like the MASL exporter described in 3.1 for xtUML. For an OAL
text editor to interface into the model, we will need to go one of two
directions:

5.3.1.1 Extend the MASL exporter to generate textual versions of the xtUML model for
validation  
  * Write an xtUML grammar to parse these new structural elements  
  * Follow the current MASL pattern  

We already have a good deal of code in place which can take compatible xtUML
models and generate MASL from them. This code could be extended to export any
xtUML model to a MASL-like textual format. Careful consideration and
consultation from interested parties would be an important step if this path
were taken.

5.3.1.1.1 Xtext grammar

We already have an Xtext compatible grammar which can parse OAL bodies, however
we will need to refine it and add production rules which can parse the newly
created textual xtUML syntax discussed above.

5.3.1.1.2 Validation, context assistance, etc.

More work would have to be done to put into place the proper validation of OAL
specific semantic rules including the handling of types. Context assistance code
would need to be introduced. We have done this before with the MASL editor and
have a good idea of the size of this undertaking.

Detailed information about the sizing of the original MASL editor to help with
sizing of an OAL editor can be seen in an internal issue
[here](https://support.onefact.net/issues/9543#note-9)

5.3.1.1.3 Memory consumption

It has been noted that for large models, a massive amount of memory is used to
load the models. If we use a textual representation of xtUML to validate OAL
activities using Xtext, we will likely double the memory consumption for loaded
models because each class, operation, state, etc. will have an xtUML instance
and a corresponding EMF instance in memory simultaneously. This is strong
argument against this option.

5.3.1.2 Extend or implement a direct interface into the OOA of OOA  
  * Use functions and class operations  
  * Integrate this interface into the validation and completion facilities of
    the OAL Xtext editor.  

A large body of OAL validation functions already exist in the OOA of OOA which
are automatically inserted into the ANTLR parser which is generated. A similar
mechanism could be put into place with an Xtext grammar.

For context assistance, it will be necessary for the Xtext editor to query the
model for visible elements of a certain type to be displayed in the help pane.
The OOA of graphics already has a fairly clever interface used for graphics
reconciliation which provides the graphics model with information about shapes
and connectors while maintaining a loose coupling between the models. An
interface similar to this one could be implemented such that the Xtext editor
can query arbitrary lists of applicable elements without knowledge of the shape
of the meta-model itself.

5.3.1.3 Visibility and IPRs

In each of the two options for interface, visibility must be considered. For
the extended exporter option (5.2.1), some sort of containment syntax will need
to be introduced to represent the packages for visibility purposes.

IPRs must also be considered. In the MASL work, IPRs are not supported for
elements in MASL activities. Instead interface (`.int`) files must be copied
into the `models/` directory. This type of solution is unacceptable for this
work. One option is to exend the exporter (5.2.1) to generate referred to
elements into some sort of interface file.

For the direct interface option (5.2.2), visibility and IPRs will be handled
naturally because OAL functions will be used to access the elements.

5.3.2 Snippet editor

Because Xtext likes to work with files, and our current persistence architecture
requires that buffers are populated and serialized to a live xtUML instance,
some customization must be done to provide an editable document to Xtext. We did
this in MASL with the MASL snippet editor which is able to load and persist MASL
text from model instances. An OAL snippet editor must be produced which does the
same for OAL.

---

Up to this point we have been working with the assumption that Xtext will be
used to complete this work. Xtext is good because it is well supported and will
help us to progress forward with future incremental improvements to the editor.
However, the costs associated with using Xtext before we are ready or in the
wrong context outweigh the costs extending existing framework. Extending
existing code may lead to "throw away" code, but it seems to be the most
expedient for satisfying the customer requirements. The rest of the analysis
will focus on options that do not include Xtext.

5.4 Options using existing framework

Using the existing OAL editor we can add auto-completion support be designing
content assistant processors. These processors are executed with a document
region where context help was requested. There shall be at least a keyword
processor, an invocation processor, an association processor and a local
variable processor. Using a token scanner we can determine from the given
region what processor is required. For instance if the token is white space or
not a keyword then we shall use the keyword processor and local variable
processor. If the token is the '.' character or "::" characters we shall use
the invocation processor.

5.4.1 Keyword processor

The keyword processor shall only show during whitespace content assist
requests. It also must locate the previous token to further filter what is
shown. For instance if content assist is requested for whitespace and the
previous token is a keyword such as select, only one, any or many shall be
given as proposals.

In addition to filtering based on the previous token, the proposals shall be
filtered based on current text. An example would be sel, which should only show
select.

5.4.1.2 Bridges and Classes

The keyword processor shall include in the keyword list all visible bridges and
visible classes that have at least one class based operation.

5.4.2 Invocation processor

The invocation processor shall only show after the appropriate characters as
stated above. The proposal list must be determined by the token preceding the
character. Once this information is present its region shall be determined and
the appropriate Statement class shall be located by matching the region values
to the statements line number and start position. With this information the
available invocations can be determined via the associated variable.

5.4.2.1 Event invocation

Event generation shall be supported using the "generate" token. When found
completion shall include all event instances within scope. For event generation
given an event label the only possible way to produce a good proposal list
would be to know the "to" token value. This is not generally helpful as one
would be required to complete the statement before going back to the
autocompletion support for the event label.

5.4.2.2 Create

Instance and event instance creation shall be handled by the invocation
processor. When the "of" token is found the preceding tokens shall be
considered to find either a visible object key letter, or in the case of an
event instance a visible event.

5.4.2.3 Delete

Instance deletion shall consider the "instance" token, and validate proposals
based on what local object variables are present.

5.4.2.4 Data Access

The invocation processor shall also produce attributes for clasess and data
items for events.

5.4.3 Association processor

5.4.3.1 Selections

The association processor shall execute when the -> "dagger" token is matched.
The preceding token shall be considered a value and if properly resolved shall
allow the processor to show only associations that the value participates in.
This shall include a list of associated classes using their key letters and the
association number. At this point valid contexts would be all elements in
association regardless of what the left value is/was. The processor shall
always use the full syntax in offerings, meaning text phrases are always
presented in the offerings and always used. The full association chain must be
cnsidered for cases where the dagger is not the first dagger after a value
instance. In this case the object key letters in the chain shall be used to
resolve the available associations.

5.4.3.2 Relate/Unrelate

The association processor needs to consider the two values in a relate
statement to properly offer association completions. The processor shall use
the across token to trigger this functionality.

The "using" token shall change the processor behavior to consider linked
classes only. If none are present then no context help shall be offered.

5.4.4 Local variable processor

As stated above this processor shall be used with whitespace requests. All
Variable instances shall be considered as long as they are within scope. This
will be all values within the current Block and any others until the Body. No
variables defined below the current scope shall be considered.

5.4.5 Comments

When a request is made within any comment token, no proposals shall be given.

5.4.6 Proposal display and completion

The design phase of this work shall determine the final display for context
help. Below is one possibility.

- Additional information such as owning element, next page xtUML element description

Here is one example for an operation invocation:

Pane 1 | Pane 2
operation(param1: type, param2: type) - Model::Classes::ClassOne::operation This operation performs this.

5.4.6.1 Completion

When a user chooses a proposal the tool needs to insert text. For keywords and
variables this is simply the keyword or variable name. For invocations it is a
bit more complicated. JDT is very elegant with their completion, allowing
current edit spots to allow the user to fully complete the assist. For this
work the complete signature of the invocation will be listed in the proposals.
When selected the entire signature will be inserted leaving modification to the
user for appropriate values.

5.4.7 Declaration and Reference searching

The search infrastructure already considers declaration and reference searching
in its design. Further work would have to be completed to make use here. The
classes under the Search package of ooaofooa starting with Declarations and
References would need to be fully implemented. The engine classes would require
a processQuery() operation. This operation needs to search the participants
associated with the Engine, for each create a match if the signature matches.
The Query classes would require a configureParticipants and createParticipant
operations. Using the same logic as with the content assist processors, we can
determine what element to search for. A new context menu entry would be defined
and present anytime a searchable reference was selected in the editor. When
executed the search infrastructure shall be called locating that element by
signature. All results will show in the search view as they do with JDT. Those
entries already allow traversal to the model element where expected.


5.5 Conclusion

After analysis it has been concluded that the best course to take would be to
extend the existing infrastructure to support the new functionality. Xtext is a
technology we would like to use in the future and would provide many benefits in
editing and persistence (especially when/if the xtUML meta-model becomes
compatible with EMF), however in the interest of the priorities of the customer,
it is the most reasonable for us to use existing code.

### 6. Work Required

6.1 Create a content assist processor for keywords  
6.1.1 Return all OAL keywords in a list  
6.1.2 Return all visible bridges and classes which have at least on class based
operation  
6.1.3 Use the keyword processor if dealing with whitespace or non-token partial
entries  
6.1.4 Look back at previous tokens to determine keywords to offer which make
sense  
6.2 Create a content assist processor for invocations  
6.2.1 Return all invocation types available for the value preceding the . or ::
characters  
6.2.2 Support invocation types found in the Body SS (Operation, Signal,
Interface Operation, Function, Bridge)  
6.2.3 Support event creation and generation  
6.2.3.1 Given the format for event generation using event labels, the
approariate data is not in place so no work is required.  
6.2.4 Support the "instance" and "of" tokens  
6.2.5 Support attributes and event data items  
6.3 Create a content assist processor for associations  
6.3.1 Support relate and unrelate  
6.3.2 Support the "using" token  
6.3.3 Support association chains  
6.4 Create a content assist processor for local variables  
6.4.1 During whitespace content assist provide a list of all local variables
within scope  
6.5 Assure that no proposals are allowed within comment tokens  
6.6 All work will be handled by each individual content assist processor  
6.7 Complete the described operations mentioned in the analysis note for the
following classes  
6.7.1 Declarations Engine, References Engine  
6.7.1.1 Declarations Engine  
6.7.1.1.1 processQuery, given a path, name or ID locate that element in the
workspace and create Name Match instances  
6.7.1.2 References Engine  
6.7.1.2.1 processQuery, given a path, name or ID locate all places for which
that element is a Value in OAL.  
6.7.1.2.2 parse all before searching  
6.7.2 Declarations Query, References Query  
6.7.2.1 For each add a configureParticipants operation and a createParticipant
operation  
6.7.2.2 For configureParticipants call Search_c.java as we do in other searchs,
modify Search_c.java to look for all elements that are visible  
6.7.2.3 For createParticipant create an instance of Named Searchable with the
given name, path or ID.  
6.7.3 Add selection based CME for Open Declaration  
6.7.3.1 On creation of the CM determine if the selection is against a value
token which represents a declared element, if so produce search data given the
selection information  

### 7. Acceptance Test

7.1 Use cases will be defined as part of the design process to validate this
work.

### End
