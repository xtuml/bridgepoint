---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Support phase one of the xtext based OAL editor
### xtUML Project Design Note

1. Abstract
-----------
This note describes the changes required to support phase one of the xtext based
OAL editor.

2. Document References
----------------------
[1] Redmine Issue 506, https://support.onefact.net/redmine/issues/506  
[2] /doc-bridgepoint/notes/506_enhanced_oal_editor/506_enhanced_oal_editor.ant.md   
[3] Redmine Issue 507, https://support.onefact.net/redmine/issues/507

3. Background
-------------
[2] described a plan that would allow us to deliver a new xtext based OAL
editor.  The analysis also accounted for an in-house based editor that supported
at a minimum auto-completion.  The decision was made to work with xtext based on
the customer's choice as well as on what is given by default from xtext.

4. Requirements
---------------
See [2].

5. Analysis
-----------
See [2].

6. Design
---------
6.1 Create xtext plug-ins

Three new plug-ins shall be created:

org.xtuml.bp.xtext.oal   
org.xtuml.bp.xtext.oal.tests   
org.xtuml.bp.xtext.oal.ui   

These plug-ins are generated from the creation wizard for a new Xtext project.
Once created the projects shall be committed with no generation artifacts.

6.2 Define Xtext grammar

A new grammar is created based off of the previously used BNF data.  This
grammar shall support the entire OAL language.  The grammar file is committed
and should be one of the only source artifacts.

6.2.1 "Same Grammar"

It is important that BridgePoint maintain only one parser for each action
language supported.  Thus, there shall be one grammar used to recognize
and parse OAL.  BridgePoint already uses antlr for parsing.  Xtext is a
layer above antlr.  Xtext and antlr require "dialect differences", but the
foundational BNF (actually EBNF, Extended Backus-Naur Form) shall be the
shared.

6.2.2 Predicates

Xtext and antlr differ in their use of syntactic and semantic predicates.
Syntactic predicates are used to distinguish input streams that differ by
a token that requires look-ahead.  Semantic predicates are used to distinguish
syntactically identical forms that have different meaning.

6.2.2.1 Example Syntactic Difference

```
if ( cond1 )
  body1;
if ( cond2 )
  body2;
else
  body3;
end if;
end if;
```

6.2.2.2 Example Semantic Difference

Given class _DOG_ with class-based operation _bark_ and port _DOG_ with
message _scratch_.  The below action language requires semantic lookup
to decide whether "DOG" is a class or a port.  At syntax highlighting
time, it is not needed.  At parse time, when creating the OAL instance
population for execution or translation, it is required.

```
DOG::bark();
DOG::scratch();
```

6.2.2.3 Deriviation

The Xtext and antlr grammars will be derived from the same base EBNF.
Predicates will not be used in the Xtext derivation.

6.3 Configure build process

Considering that Xtext uses generation based off of a single grammar file we
need a build process that allows us to commit only the source data.

6.3.1 Create clean targets

At this point we cannot find a way to properly clean generated data using the
Xtext infrastructure.  We shall create an ant build script.  This build script
shall have two jobs:

- Run the workflow engine
- Clean all generated source

Considering that we should have only the grammar as source, the clean targets
shall simply clean everything except the projects as well as the grammar.

This shall allow us to use Build project on the org.xtuml.bp.xtext.oal project.
Which shall generate all of the necessary code.

Each project shall be responsible for cleaning.  This will leave us with three
ant scripts where each performs the necessary cleaning.

6.4 Persistence

xtUML uses attribute values to store the OAL data.  Our current editor looks at
a loaded model element to determine what text shall be loaded into the editor.
We need to use the same approach for the Xtext based editor.  We considered
staying with a separate file approach, however due to the current infrastructure
this would not be wise at this time.  Going with such an approach would cause
problems when synchronizing model data in order to work with verifier and the
model compilers.

6.4.1 Extend Xtext persistence

Xtext allows for injection methods.  These are entry points that allow a
customized class to be used for certain parts of the infrastructure.  The first
attempt at customizing our persistence lead us to injecting our own editor
implementation.  While this could work, some thought needs to be given in order
to choose the proper solution.  A post was added to the Xtext forum asking for
advise on the proper solution to solve this problem.  The response lead us to
another injection point which is for the document provider class.  Looking into
this it seems this may not be our best choice.  At this point the editor will be
left to edit .xoal files only.  Transferring data shall occur through copying
and pasting from the xoal editor to the original oal editor.

6.5 Next phase

The first phase of this work was to provide a prototype.  In the next phase [3],
we shall complete the persistence work described in 6.4 and on.  Furthermore,
we shall look into semantic parsing as well as semantic auto-completion.

7. Design Comments
------------------

8. Unit Test
------------
8.1 Xtext editor

8.1.1 Code completion

_- Type Ctrl + Space   
_R A list of data is given   
_- Choose an item from the list   
_R The item is automatically entered into the document   

8.1.1.2 Filtered code completion

_- Enter some OAL, for instance 'select'   
_- Type Ctrl + Space   
_R A list is given including only one, any or many   
_- Choose an item from the list   
_R The item is automatically entered into the document   

8.2 Parsing

_- Type the following into an xoal document:   
   'select a related by c;'   
_R The parser shall generate an error   


End
---

