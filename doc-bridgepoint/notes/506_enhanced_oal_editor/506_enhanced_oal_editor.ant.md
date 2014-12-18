---

This work is licensed under the Creative Commons CC0 License

---

# Stage 1 - Enhance OAL Editor
### xtUML Project Analysis Note

1. Abstract
-----------
This note describes the analysis of the first step of moving the BridgePoint OAL editor forward.  In this step we will implement a new editor which focuses on syntax aware auto-completion.

2. Document References
----------------------
[1] Issues 506, Stage 1 - OAL editor with syntax aware auto-completion   
[2] BridgePoint UML Suite Help > Reference > OAL Reference > Keywords   
[3] Issues 42, standards-based OAL text editor

3. Background
-------------
Customers have asked for an enhanced OAL editor.  Including code completion as
well as the following:

- OAL escape character
- Reference and occurrence search
- Hyperlinking (ctrl + left mouse button for definition)

Customers would like to see an editor implemented in Xtext.

4. Requirements
---------------
4.1 Provide code completion with OAL

5. Analysis
-----------
5.1 Enhanced OAL editor

This analysis shall investigate approaches that allow progress to be made
towards an enhanced OAL editor.  The analysis will consider both an Xtext
solution as well as an in-house solution.

5.1.1 Xtext solution

Xtext is one possible solution and is favorable in that it is widely used and
comes with some default features.  However, Xtext works best with EMF based
metamodels.  It may be best to implement an Xtext editor if and when the
ooaofooa metamodel is designed in EMF.

5.1.1.1 Xtext syntax aware editor

In the Xtext approach we shall implement a basic syntax only aware editor.  This
support will come from the Xtext framework by default.  To build such an editor
we shall use our current OAL BNF as a basis.

5.1.1.2 Persistence

The Xtext framework generates an editor that works on a single file.  It expects
this file to contain the language text.  The BridgePoint tool stores all model
data in an xtUML file.  This includes the action language defined by the user.

In order to have a good working solution we can modify the Xtext generated
source to persist in the same way we do for the current editor.  This would mean
that a save against the Xtext editor would trigger our current infrastructure's
persistence behavior.  Alternatively we could persist OAL into a new file, with
the .xoal extension.

5.1.1.2.1 xtUML persistence

Persisting in the same way we do today has some benefits.  These include most
importantly that the infrastructure is already set up for this type of
persistence.  The downfall for this approach is that we need to maintain code
that will allow loading and saving data into the xtUML model.

5.1.1.2.2 Single file persistence

Using the default approach provided by Xtext is beneficial in that we do not
need to modify any of the default Xtext behavior.

However, this leads to other problems.  The problems are mostly dealing with
synchronization between the xtUML model and the new text file.  When saving OAL
from the Xtext editor we would have to assure that the Action_Semantics
attribute was updated.  Otherwise, Verifier and the model compilers would not
have the required data.  We could consider modifying Verifier and the model
compilers to make use of the ecore model data.  This could include bringing the
ecore to xtuml exporter back.  In this case the ecore data would get exported
before running Verifier or the model compilers.  

5.1.2 Current infrastructure

With the current infrastructure we shall define contexts, which are defined by
a document range.  The contexts shall include all keywords listed in [2].

5.1.2.1 Use the eclipse content assist support   
5.1.2.2 For each supported auto-completion in [2] add a context helper
          which is associated with a content processor.   
5.1.2.3 Override the SourceViewerConfiguration.getContentAssistant() method
          calling the necessary processors.   
5.1.2.4 Override current editor to add Content Assist Action in the text editor
          createActions method

5.2 Decision on approach

Using the current infrastructure we would have to perform additional work that
Xtext already handles.  The current infrastructure could be extended to handle
code completion, however the amount of work does not outweigh the additional
features provided by Xtext.

Feature examples:

- Syntax auto-completion   
- Auto insert of braces (could extend this to support auto insert of control
  statements)   
- Code formatting   
- Code folding   

In our current infrastructure we would have to
design libraries that parse the text of the document in order to filter the list
of auto-completions.  An example is the elif, else and end if cases.  In these
cases we would have to scan the current text and determine what should be
available in the auto-complete list.  It gets harder when you consider comments,
we would have to ignore keywords in comments.

We shall take the Xtext approach using the current persistence infrastructure.  

6. Work Required
----------------
6.1 Create Xtext grammar based on bnf   
6.1.1 Create an Xtext project   
6.1.1.1 Using the Xtext grammar created use the Modeling Workflow Engine to
        generate the necessary editor plug-ins   
6.1.1.1 Modify the Xtext code to persist into the xtUML model   

7. Acceptance Test
------------------
7.1 The editor shall support auto-completion at a syntax level, this includes
at a minimum the keywords specified in [2].  It shall also filter the list
depending on the previous keyword from the current cursor point.   

End
---

