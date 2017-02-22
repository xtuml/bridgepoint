---

This work is licensed under the Creative Commons CC0 License

---

# MASL rename refactoring with Xtext
### xtUML Project Design Note

1. Abstract
-----------
Update MASL actions when xtUML structural elements are renamed.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8261 MASL automatic reference maintenance](https://support.onefact.net/issues/8261) Headline issue  
<a id="2.2"></a>2.2 [#8261 analysis note](8261_masl_refactor_ant.md)  
This analysis note enumerates the elements that need to be handled, and
evaluated a pull request that was open for updates to the Xtext editor. The pull
request was rejected because there was more work to be done before it was
mergeable. This note describes all of the design including the Xtext editor
design.  
<a id="2.3"></a>2.3 [#9138 MASL refactor test](https://support.onefact.net/issues/9138)  
<a id="2.4"></a>2.4 [#8808 deprecate 'function' keyword in MASL](https://support.onefact.net/issues/8808)  
<a id="2.5"></a>2.5 [#9218 MASL event parameter data is not properly refactored by an edit operation](https://support.onefact.net/issues/9218)  

3. Background
-------------
None

4. Requirements
---------------

4.1 The Xtext editor shall rename all references to supported model elements
when the element is renamed in BridgePoint  
4.1.1 The Xtext editor shall only modify files in the `models/` directory as
part of the refactor  
4.1.2 The Xtext editor shall not touch the `masl/` directory as part of the
refactor  
4.2 The following model elements shall be supported by this work  
4.2.1 Data type  
4.2.2 Structure member  
4.2.3 Enumerator  
4.2.4 Domain  
4.2.5 Domain service  
4.2.6 Domain service parameter  
4.2.7 Terminator  
4.2.8 Terminator service  
4.2.9 Terminator service parameter  
4.2.10 Relationship phrase  
4.2.11 Object  
4.2.12 Object service  
4.2.13 Object service parameter  
4.2.14 Attribute  
4.2.15 State  
4.2.16 State parameter  
4.2.17 Event  
4.2.18 Event parameter  

5. Analysis
-----------

5.1 Types

When a type name changes, every "typeref" type that is based off this type must
be renamed manually by the user to reflect the change.

5.2 Structure members and enumerators

Since structure members and enumerators are defined within a MASL editor, the
built-in MASL refactoring mechanism can handle them without any extra work.

5.3 State parameter

State parameters are determined by the event parameters in xtUML. Renaming and
event parameter will rename the corresponding MASL parameter for any state which
as an entering transition assigned to that event.

6. Design
---------

6.1 Overview

The design approach taken logically separated the work into two parts -- Xtext
refactoring and BridgePoint integration through an eclipse extension point.

Extension points allow a plugin to define an interface (extension point) which
any number of plugins can implement (extension). These extension point and
extension plugins are registered through their `plugin.xml` file and the
extension point plugin can invoke the interface methods of any of the extension
plugins.

For rename refactoring, a new extension point `renameParticipants` was created
to bridge BridgePoint to the Xtext plugins. The interface is as follows:
```
public IStatus renameElement(NonRootModelElement element, String newName, String oldName);
```

6.2 Xtext refactoring

6.2.1 `MaslRenameParticipant`

The `MaslRenameParticipant` class is the most central class of the Xtext
refactoring. This class implements the extension point interface shown above.
When the `renameElement` method is invoked, it does the following:

6.2.1.1 Get the set of projects to which the refactored element belongs  

In the case of an interface message or message parameter, the elements to
refactor may span multiple referring projects.

6.2.1.2 Get the set of EClasses (MASL types) that define the refactored element

In some cases multiple MASL types can represent one xtUML element. Before #8808
[[2.4]](#2.4), functions and services were represented by the same xtUML element
but two separate MASL types. There are currently no instances of multiple
EClasses representing an element, however the infrastructure to do so remains in
place.

6.2.1.3 Get the set of qualified names that specify the instances of the
refactored element in the MASL text  

The qualified name uniquely specifies a MASL element. The model element instance
is used to derive the qualified name(s) which are then used to search the Xtext
index for elements to refactor. In some cases, multiple qualified names result
from one element. For example, event parameters map to both event parameters and
destination state parameters (see comment #3 in issue #9218 [[2.5]](#2.5)).

6.2.1.4 Build a refactor context

All of the above information is combined search the Xtext index and compile a
refactor context that contains all of the target URIs to refactor

6.2.1.5 Execute the refactor

The refactor operation is executed according to the refactor context. A status
code is returned.

6.2.2 `XtumlToMaslMapper`

The other important class is the `XtumlToMaslMapper` class. This class contains
all the logic to map the xtUML element to its corresponding EClass(es) and its
corresponding qualified name(s). This is where most of the "smarts" of the
integration between xtUML and MASL for refactoring is located.

6.3 BridgePoint integration

6.3.1 Order of operations

The order in which refactoring takes place is extremely important since both
Xtext and BridgePoint are modifying source files. The order is as follows:

1. xtUML element renamed in memory (memory model modified)  
2. Xtext refactoring is invoked (`.masl` files modified)  

  At this point, there is a unique discrepancy between the memory model and the
  persisted files. BridgePoint has updated the _structure_ and Xtext has updated
  the _actions_. The newest version of the structure is in memory and the newest
  version of the actions is on disk. These must be synchronized so that both the
  memory model and the persisted model have the newest version of both.

3. xtUML persist of structure only (on disk model now has both structure and
actions changes)  
4. xtUML reload (on disk model now has both structure and actions changes)  

6.3.1 `RenameParticipantUtil`

The `RenameParticipantUtil` class is a convenience class to allow BridgePoint to
call each registered extension of `renameParticipants` extension point when a
rename occurs. It has one public method `renameElement` which takes a
`Transaction` as an argument. This method loops through each model change delta
and if it is an attribute changed model delta of a qualified attribute ("Name",
"Mning", "Txt_phrs"), the `renameElement` method of each extension plugin is
called.

xtUML Attribute is a special case because the "Name" attribute is derived.
Instead, changes to "Root_nam" or "Prefix" trigger the call to the extension
plugins.

`renameElement` returns a boolean success code.

6.3.1 `ComponentTransactionListener`

The entry point for the refactor is from the component transaction listener
`transactionEnded` method. In the beginning of this method, the
`RenameParticipantUtil` is invoked. `true` is returned if any refactoring was
successful, `false` if otherwise. If `true` is returned, a flag is set to
indicate that the persists for this transaction should be done for structure
only. At the end of this method, the flag is unset and any persistable model
component affected is reloaded.

7. Design Comments
------------------

7.1 Overview of transaction listeners

This section aims to give some background information on the transaction
listener mechanism that was learned during this analysis and design.

7.1.1 Transactions

Model changes are grouped into transactions. A transaction defines a single
edit, however it may touch more than one part of the model. Transactions can be
of different types (rename, delete, add, move, etc.) and are handled differently
accordingly. Transactions are kept in a stack and are the basis of the undo/redo
functionality.

7.1.2 Model deltas

Each transaction has a set of model deltas. A model delta is an object that
contains information about what and how the model changed. There are many
subtypes of model delta that define specific changes. For example,
`AttributeChangeModelDelta` contains the data when an instance attribute is set.
The instance of the model delta is created in the attribute setter and added to
the current transaction. Some transactions can have many model deltas. For
example, the transaction to create an instance has an
`AttributeChangeModelDelta` for each attribute in the class.

7.1.3 Transaction manager

When a transaction is completed, started, cancelled, etc., the
`TransactionManager` is responsible for notifying any registered listeners. The
transaction manager fires an event to all registered classes and pass the
instance of the transaction.

7.1.4 Transaction listener classes

Registered transaction listeners can define behavior when transaction specific
events occur. In the case of refactoring, when a transaction is completed,
refactoring is attempted. The refactor utility checks all of the model deltas
for changes that indicate the need for refactor (i.e.
`AttributeChangeModelDelta`s where the attribute name is "Name", etc.)

8. User Documentation
---------------------
None

9. Unit Test
------------

See [[2.3]](#2.3)

End
---

