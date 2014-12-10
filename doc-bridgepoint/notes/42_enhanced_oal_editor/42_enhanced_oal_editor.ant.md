---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Enhance OAL Editor
### xtUML Project Analysis Note

1. Abstract
-----------
This note describes the analysis of moving the BridgePoint OAL editor forward.

2. Document References
----------------------
[1] Issues 42, https://github.com/xtuml/bridgepoint/tree/master/doc-bridgepoint/notes/  
[2] doc-internal/notes/184_dts0101023704/supported_auto_completions.txt

3. Background
-------------
Customers have asked for an enhanced OAL editor.  Including code completion as
well as the following:

- OAL escape character
- Reference and occurrence search
- Hyperlinking (ctrl + left mouse button for definition)

4. Requirements
---------------
4.1 Provide code completion with OAL

5. Analysis
-----------
5.1 Provide an OAL editor with code completion support

5.1.1 xtext

xtext is a possible solution and is favorable in that it is widely used and
comes with some default features.  However, xtext works best with EMF based
metamodels.  It may be best to implement an xtext editor if and when the
ooaofooa metamodel is designed in EMF.

5.1.1.1 xtext approach

In the xtext approach we shall implement a basic syntax only aware editor.  This
support will come from the xtext framework by default.  To build such an editor
we shall use our current oal BNF as a basis.  We may be able to use the current
model which enters and extends the raw BNF then generates our current antlr.  We
would basically have an archetype which generated the desired xtext grammar.

The generated editor shall support code completion.  In addition, with xtext
these features will come for free but only at a syntax level:

- Syntax highlighting
- Auto-completion (at the syntax level)
- Error reporting (at the syntax level)
- Quick fix (at the syntax level)

5.1.2 Current infrastructure

5.1.2.1 Auto-completion support

5.1.2.1.1 Contexts where auto-complete support is available

Looking at the Body Subsystem in ooaofooa, the available contexts were
determined and captured in [3].  All of these contexts shall be supported.

5.1.2.1.2 Automation

Auto-completion support in eclipse is invoked in two ways, one of which is
automatically.  In the JDT editor this only occurs after the . control
statement.  The tool shall support automatically invoked auto-completions for
the following control statements:

::
.
->
[
'

The auto-completion when invoked automatically shall not interfere with the
user's typing.  If the user continues to type without using the up or down arrow
to navigate the proposal list, the list shall become filtered.  If the user
types enough to where there is one or less proposals the auto-complete list
shall be closed.

5.1.2 User-invoked

In the case of starting with whitespace (no control statement) the user shall be
required to hit a key combination to bring up the auto-complete list.  This list
shall contain the following elements:

keywords
Element (Interface Name, External Entity Key Letter, Class Key Letter, Event
names)
In-scope variables

Following these statements shall be considered whitespace: ( , ; " ! = - > < * ^

The above will account for selections using key letters but in a lazy way.  For
instance the following statement would give a list that included not only key
letters but also keywords and variables:

select one foo from instances of [keywords, Element, In-scope variables]

If during design we find a way to easily determine a select scope then we can
consider filtering the list on only possible class key letters.  The same
applies to event names when using create event or generate event. 

If no context can be determined a list shall never be shown.

5.2 Building the lists

We currently traverse all appropriate associations in the parser.  The parser is
not looking for a list in most cases but an exact name match on an element.  The
validation functions shall be used as an example to determine what traversals
are required for each auto-completion listed in [3].  For each one a function
shall be created which will return an array list of elements.  This function can
then later be called by the auto-complete infrastructure provided by eclipse.

For keywords a list of available keywords shall only be shown if at least the
first character is typed.
 
5.3 Integrating into eclipse

5.3.1 Eclipse content assist
 
Eclipse allows for content assist based off of a determined context.  The
context is determined in a similar manner to that of syntax coloring.  A range
of the document is considered a context.  The tool shall use this eclipse
technology defining the required contexts from [3].  The lists created in 5.2
shall have an associated completion proposal.  Each completion proposal shall be
associated with a context.  When the cursor is at a determinable context the
list of proposals shall be shown.

5.4 Determining the proper solution

5.4.1 xtext

xtext can be used easily for a syntax-only editor.  The following issues arise
when using xtext in our current infrastructure:

- Need to re-implement a non-file based storage approach, or migrate the
  existing one to the xtext generated editor
- If we wanted we could move to a separate file for OAL, again there would be
  more work here to support such a move
- Need to add hooks that call our existing parser, effectively parsing twice or
  find a way to traverse the EMF AST and populate our parser tree (V_VAL and
  such)
- Need a way to add our semantical parse errors to the editor
- Need a way to add the parse errors on a parse all
- Would have to make sure that breakpoints and stepping works appropriately with
  the new editor

The editor does come with more features and as long as we can call into ooaofooa
traversals from the xtext grammar we should be able to use them.

5.4.2 Current infrastructure

With the current infrastructure we already have the appropriate traversals
available, we just need to get them from the parser functions.  The eclipse
infrastructure provides all of the features for xtext generated editors.  We
would basically be designing our own version of an xtext generator, though with
a much smaller feature set at first.

5.4.3 Decision on approach

We shall design both to allow side by side comparison.  Given this, the current
infrastructure approach shall only be required to support auto-completion at the
syntax level.

6. Work Required
----------------
6.1 xtext

6.1.1 Create xtext grammar based on bnf
6.1.1.1 Create the grammar by hand, using the bnf as a reference
6.1.1.2 Create the grammar by generation
6.1.1.2.1 Using the current archetypes as a base create ones that can generate
          the xtext grammar
6.1.2 Create new editor project
6.1.2.1 Create a xtext project
6.1.2.2 Using the xtext grammar created use the Modeling Workflow Engine to
        generate the necessary editor plug-ins

6.2 Current infrastructure

6.2.1 Use the eclipse content assist support
6.2.1.1 For each supported auto-completion in [2] add a context helper which is
        associated with a content processor.
6.2.1.1.1 Create OAL functions that return a provided list and pass that to each
          content processor.
6.2.1.2 Override the SourceViewerConfiguration.getContentAssistant() method
        calling the necessary processors.
6.2.1.3 Override current editor to add Content Assist Action in the text editor
        createActions method

7. Acceptance Test
------------------
7.1 xtext
7.1.1 The editor shall support auto-completion at a syntax level
7.2 Current infrastructure
7.2.1 The editor shall support auto-completion at a syntax level

End
---

