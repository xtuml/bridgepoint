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
[1] Issues 506, Standards based OAL text Editor
[2] BridgePoint UML Suite Help > Reference > OAL Reference > Keywords   
[3] BridgePoint UML Suite Help > Reference > OAL Reference > Control Statements

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

5.1.1.1 

In the Xtext approach we shall implement a basic syntax only aware editor.  This
support will come from the Xtext framework by default.  To build such an editor
we shall use our current OAL BNF as a basis.

5.1.2 Current infrastructure

With the current infrastructure we shall define contexts, which are defined by
a document range.  The contexts shall include all keywords listed in [2].
Additionally, contexts should be added for control statements as described in
[3].
 
5.2 Decision on approach

We shall design both with syntax auto-completion to allow side by side
comparison.

6. Work Required
----------------
6.1 Xtext

6.1.1 Create Xtext grammar based on bnf   
6.1.2 Create new editor project   
6.1.2.1 Create a Xtext project   
6.1.2.2 Using the Xtext grammar created use the Modeling Workflow Engine to
        generate the necessary editor plug-ins   

6.2 Current infrastructure

6.2.1 Use the eclipse content assist support   
6.2.1.1 For each supported auto-completion in [2] and [3] add a context helper
        which is associated with a content processor.   
6.2.1.2 Override the SourceViewerConfiguration.getContentAssistant() method
        calling the necessary processors.   
6.2.1.3 Override current editor to add Content Assist Action in the text editor
        createActions method

7. Acceptance Test
------------------
7.1 Xtext   
7.1.1 The editor shall support auto-completion at a syntax level   
7.2 Current infrastructure   
7.2.1 The editor shall support auto-completion at a syntax level   

End
---

