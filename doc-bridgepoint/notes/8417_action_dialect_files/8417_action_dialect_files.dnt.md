---

This work is licensed under the Creative Commons CC0 License

---

# Storing action semantics as files
### xtUML Project Design Note

1. Abstract
-----------
Currently, BridgePoint stores action language in an attribute on model
instances in the meta-model. This approach has a few key disadvantages:
* Mixing of concerns between the models and the actions
* Not easily extensible to support multiple dialects of action language
(think OAL, MASL, ALF)

Moving to a file based strategy for storing actions could solve both of
these issues, and open the door for BridgePoint to support more action
language dialects, and provide a more pleasing user experience.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8417 Storing activities as dialect files](https://support.onefact.net/issues/8417) -- This is the parent issue  
<a id="2.2"></a>2.2 [#8417 previous analysis note](https://github.com/leviathan747/bridgepoint/blob/8417_oal_test/doc-bridgepoint/notes/8417_action_files/8417_action_files.ant.md) -- This is an analysis note from previous work done on this issue. The requirements in this note will be informed by the requirements in the previous note, however, the design was mostly abandoned.  
<a id="2.3"></a>2.3 [#8417 previous design note](https://github.com/leviathan747/bridgepoint/blob/8417_oal_test/doc-bridgepoint/notes/8417_action_files/8417_action_files.dnt.md) -- This is a design note from previous work done on this issue. See comment above.  

3. Background
-------------
The reader should take a look at both [[2.2]](#2.2) and [[2.3]](#2.3) before
continuing. These notes represent work that was previously done in this area
that has been abandoned. Problems during implementation that made the design
infeasible, however, it is important to know the work that came before.

4. Requirements
---------------
4.1 Actions shall be stored separate from SQL model data in their own files  
4.2 Each component, package, class, and state machine shall have a corresponding
a file to contain all actions within the element  
4.2.2 Analysis shall be done to decide whether each action language dialect
should have its own file, or if they should be consolidated in one file  
4.2.3 Analysis shall be done to decide an appropriate file extension scheme  
4.3 BridgePoint shall continue to support single file model import and export  
4.4 Model editing behavior of BridgePoint (including signature editing and
editing of the action text) shall remain unchanged  
4.5 Architecture shall be designed to support multiple action language dialects
in the future  

5. Analysis
-----------

5.1 General Strategy and roadmap
![diagram](diagram.jpg)

I have included this diagram to illustrate where BridgePoint is today, where
BridgePoint could be in the future and an incremental step to take us that
direction offered by this design.

5.1.1 The diagram without the pink lines and the contents of the pink dotted box
represents BridgePoint today. `.xtuml` files are read by a SQL parser and the
file I/O plugin calls into the OOA of OOA to populate the in memory model. Other
BridgePoint plugins such as the parser, the pre-builder, graphics plugins (think
actions on states) query the OOA of OOA to access action semantics. When a
model change is detected, the I/O plugin is invoked to export the model by
dumping SQL insert statements representing the instances in a deterministic
order and format.

5.1.2 Now, draw your attention to the contents of the pink dotted box (and let
your mind think of the future). An Xtext based editor plugin can access `.oal`
files (or `.masl` or `.alf` files, etc.) for editing. The Xtext editor can
query the OOA of OOA instances to provide validation, smart completion,
refactoring, and other features. Other plugins that need to access actions can
ask the editor for the text of the actions. The Xtext parser can populate OOA
`V_` and `ACT_` instances and the old parser can be deprecated. In the future,
a concrete syntax of xtUML combined with Xtext integration could be used to
populate the full OOA of OOA and serialize back to text, simplifying and
deprecating the I/O plugin (except for graphical instances).

5.1.3 The above state can be reached through a series of incremental steps. This
design takes aim at the first step. Observe the two solid pink arrows. This
design outlines the plan to modify the I/O plugin so that action semantics can
be peeled away from the structural model and stored in separate files.
Minor modification to the export flow and PEI data can achieve this. The
importer can be modified to look in these separate files to populate the action
semantics instead of the SQL model data. The Xtext editor can also access these
files and focus on editing only. This modification to the I/O plugin allows
BridgePoint to function normally while the Xtext editor grows and develops full
integration.

5.2 PMCs

The persistence mechanism of BridgePoint hinges on two classes called
PersistableModelComponent (PMC) and PersistenceManager. Simply explained, PMC is
an abstraction of "File". Every model element has a PMC. The PMC defines where
on disk the model element is stored. A model element either has its own PMC (in
the case of a component, package, class, etc.), or it finds its PMC by recursing
upwards until it finds a "root model element" ancestor (_"root" is overused in
BridgePoint terminology -- in this case root is referenced with respect only to
persistence_). When a model is loaded, the PersistenceManager (singleton)
recursively searches the `models/` directory, and each `.xtuml` file is assigned
a PMC instance by the PersistenceManager. This collection of instances is then
passed to the importer which parses the SQL, creates OOA instances, and then
relates them. When a model element change is detected, the PMC of that model
element is identified, and the exporter performs a persist for only that
specific PMC (file).

This PMC architecture is utilized by many other parts of BridgePoint, including
copy/paste, delete, and rename. For this change, it must be extended to not only
represent one file, but a collection of files containing the structural data and
the actions. Thinking forward, the extension must have the notion of separate
but parallel actions in different dialects.

5.3 Naming and files

This feature raises questions on how to organize and name files. What file
extensions do we use? Are there multiple files for dialects or does one file
contain them? Is an action file guaranteed to exist, or does it appear once
actions are added. Decisions on these questions will be provided in the design.

6. Design
---------

6.1 ActionFileManager

6.2 Changes to PMC and PersistenceManager

6.3 Modifications to model export

6.3.1 New Action Body class

6.3.2 Archetype changes

6.4 Modifications to model import (load)

6.4.1 Parser grammar

6.4.2 Statement handler

6.5 Resource listener

6.6 Preferences and how they are used

8. User Documentation
---------------------
Describe the end user documentation that was added for this change. 

9. Unit Test
------------
Outline all the unit tests that need to pass and describe the method that you
will use to design and perform the tests. Here is an example reference to the Document References section [[2.1]](#2.1)

9.1 Item 1  
9.1.1 Example sub-item
* Example List Element

9.2 Item 2  
9.2.1 Example sub-item
* Example List Element

End
---

