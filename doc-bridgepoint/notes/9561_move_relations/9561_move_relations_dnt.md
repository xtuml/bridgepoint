--

This work is licensed under the Creative Commons CC0 License

---

# Move relations
### xtUML Project Design Note

### 1. Abstract

See [[2.2]](#2.2)

### 2. Document References

<a id="2.1"></a>2.1 [#9561 Move relations](https://support.onefact.net/issues/9561)  
<a id="2.2"></a>2.2 [#9561 analysis note](9561_move_relations_ant.md)  
<a id="2.3"></a>2.3 [test model](https://github.com/xtuml/models/tree/master/test/AssociationMoveTests)  
<a id="2.4"></a>2.4 [test suite](https://github.com/xtuml/bptest/tree/master/src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc)  
<a id="2.5"></a>2.5 [test matrix](https://github.com/xtuml/bptest/tree/master/src/org.xtuml.bp.ui.canvas.test/matrices/non_generated/association_move.txt)  
<a id="2.6"></a>2.6 [#9501 2017 issues SRS](https://docs.google.com/document/d/1ZyV-FZt77RThTWFBUm3PfM1jxXL9baSeKQWYzfrmFrE/edit)  

### 3. Background

None

### 4. Requirements

These requirements are drawn from the SRS [[2.6]](#2.6) section 3.1.3

The following definition shall be used for an association move operation:

> For a specified class or imported class (C1) participating in association (A),
> and second distinct specified class or imported class (C2) existing on a single
> diagram, C1 shall be removed from participation with A and C2 shall be placed
> into participation with A in the place of C1.

4.1 BridgePoint shall support association move operations (see above
definition)  
4.1.1 Move operations shall be supported for all association types  
4.2 User shall not be required to re-input the text phrases, multiplicities,
and conditionalities for a moved association  
4.3 Association number shall remain constant for a moved association  
4.4 A formalized association shall be automatically unformalized as part of a
move operation  
4.5 BridgePoint shall not attempt to reformalize a moved association  
4.6 A context menu operation and graphical drag operation shall be considered
in the design for moving an association  

Additional design requirements not called out in the SRS:  
4.7 Imported classes in association shall be supported  
4.8 The current graphical behavior of association editing shall be maintained  
4.8.1 Rectilinear routing shall work with move operations  
4.8.2 Reflexive associations shall provide clean, rectangular feedback lines  

### 5. Analysis

5.1 Graphical editor (drag and drop)

The graphical editor is based on a model of graphics defined in the
`bp.ui.canvas` plugin. The editor itself is on the Eclipse GEF (Graphical
Editing Framework) platform. For most model elements in xtUML graphical editing,
once a connection is made to a shape, it cannot be disconnected by drag and
drop. In GEF, this is called a `ReconnectRequest`. For the xtUML editor,
`ReconnectRequest`s are mostly ignored except in two cases: the reconnect is to
the same shape (i.e. moving where a connector actually anchors to a shape), or
from "whitespace" to a provision or requirement (i.e. satisfying an interface
reference).

The pattern of the second case above can be used to extend the editor to allow
`ReconnectRequest`s when moving associations on classes and imported classes.

5.2 Context menu

Analyzed as part of [[2.2]](#2.2) section 5.2.1

5.3 Conclusion

The graphical drag and drop option shall be pursued. In the analysis [[2.2]](#2.2), a
CME/wizard option was considered. The graphical option is much better for user
experience and through this analysis it is determined that is attainable in the
time frame of the project.

### 6. Design

6.1 Graphics edit policy

6.1.1 Edit policy background

The GEF editor uses a set of policy classes which define how/when/and where
graphical elements can be edited. Subclasses of the `Request` class are passed
to methods in these policy classes. In the particular case for moving
associations, a `ReconnectRequest` is issued when a user begins to drag a
connector away from its anchor and to another graphical object (shape,
connector, or even the background of the diagram itself). Two methods are
modified in the `ShapeGraphicalNodeEditPolicy` class:
`getSpecializedReconnectSourceCommand` and
`getSpecializedReconnectTargetCommand`. Both of these are modified to do the
same thing. Both are required because GEF distinguishes between the source and
target ends of a connector.

6.1.2 Method description

In this method, it is first verified that the reconnect target is not the same
as where it came from (i.e. user did not simply reposition a connector). Next,
the graphical "represents" OOA of OOA instance is retrieved from the graphics
model. Using Java reflection, the object is checked for an operation called
`moveAssociation`. If found, a new reconnect command is created.

6.1.3 Reconnect command description

The command takes 3 actions:

6.1.3.1 The `moveAssociation` operation is invoked. This operation returns a
boolean value which represents whether or not the move was successfully
completed. See section 6.3.3 for more information about failure cases. If it
returns `false`, the command returns `false` at this step. Note that when a
graphical command returns `false`, the transaction is canceled.

6.1.3.2 Next, the graphical model is updated to match the semantic model. This
is done by the `associateTerminalSpecs` method which creates relationships
between OOA of graphics specification classes and the `moveConnector` OOA of
graphics operation which updates the `GD_` and `DIM_` instances (see section
6.2)

6.1.3.2 Finally, positions and sizes of the anchors and shapes are updated. This
is done by the `transferLocation` method (same method invoked when a user
repositions an anchor on a shape) and the `resizeContainer` method.

6.2 OOA of graphics changes

A new operation `moveConnector` on the "Model Tool" (`CT_MTL`) class was introduced to handle
moving a connector. This is somewhat similar to the `finalizeConnector`
operation which is used by the graphics reconciler to automatically create
connector graphical instances. Cues were also taken from `createConnector` on
the "Model" (`GD_MD`) class.

The operation first selects the anchor (`DIM_CON`) on the proper end of the
connector. Next it unrelates the anchor from the old model element. Finally, it
relates the anchor to the new model element.

6.3 OOA of OOA changes

6.3.1 `moveAssociation`

The core of this feature is the `moveAssociation` operation on `R_REL` which
updates the semantic model for a move operation.

The inputs are the ID of the packageable element to which the connector is
currently associated (`current_id`) and the ID of the packageable element to
which the connector is requested to move to (`new_id`).

The generic algorithm is as follows:  
* The `O_OBJ` instance represented by `current_id` is selected  
  * If the packageable element is an imported class, the `O_OBJ` is selected
    through the `O_IOBJ` instance  
* The `O_OBJ` instance represented by `new_id` is selected  
  * If the packageable element is an imported class, the `O_OBJ` is selected
    through the `O_IOBJ` instance  
* If the association is formalized (checked using `isFormalized`), unformalize
  using `unformalize`  
* Check for restricted situations (see section 6.3.3). If any of these
  conditions are met, return `false`.  
* If no restricted conditions are met, select the `R_OIR` instance associated
  with self (`R_REL`) and the current `O_OBJ` across R201  
* Unrelate self from the current `O_OBJ` using the current `R_OIR` instance  
* Relate self to the new `O_OBJ` using the current `R_OIR` instance  
  _Note that all of the relationship information (e.g multiplicity, phrase,
  etc.) are maintained in the subclasses of `R_OIR`, so none of it is lost
  during the move since the same `R_OIR` instance is reused._  
* If either of the current or new packageable elements were an `O_IOBJ`, update
  the relationships to `R_OIR` across R202  

6.3.2 `moveAssociation` on other classes

The `moveAssociation` operation also was added on "Class As Link" (`R_ASSR`) and
"Class As Subtype" (`R_SUB`). These simply select the `R_REL` and call
`moveAssociation` on the `R_REL` instance. The reason these operations are
required is because in the case of the dotted line connector and subtype
connectors, the graphical "represents" is an instance of `R_ASSR` and `R_SUB`
repsectively and not an instance of `R_REL`.

6.3.3 Restricted situations

Some associations are not allowed in a class diagram. Move operations that
introduce these situations are prohibited (`moveAssociation` returns `false` and
the transaction is canceled).

6.3.3.1 The link class in a linked association cannot be the "one" or "other"
class  
6.3.3.2 A supertype may not be its own subtype (and vice versa)  
6.3.3.3 A subtype may not be associated to the supertype more than once  

6.4 Reflexive associations

When reflexive associations are created in the editor with a
`CreateConnectionRequest`, bendpoints are added to the feadback line to give it
a nice square shape. Otherwise the line would be drawn across the face of the
class. This needs to be extended for `ReconnectRequest`s so that moving an
association into a reflexive situation provides the same square feedback line.

In the `ConnectionPolicy` class, there is a method called `showTargetFeedback`
which modifies the feedback line before it is displayed in the case of a
reflexive association. It does this using the `createBendpointsForFeedback`
method. These two methods must be extended to handled `ReconnectRequest`
instances. Additionally, a new method `showSourceFeedback` must be implemented
(actually, must override the default behavior of the method in the superclass)
because reconnects can happen at both the source and target end of a connector.

### 7. Design Comments

7.1 Miscellaneous methods were moved in order to inherit for other classes.

### 8. User Documentation

8.1 Documentation shall be added to the modeling guide pertaining to this
feature

### 9. Unit Test

A test model was created to test this issue. It can be found at [[2.3]](#2.3). A
new test suite was created as well [[2.4]](#2.4). This test suite has 188 tests
generated by the test generator from a test matrix [[2.5]](#2.5). It covers all
of the possible association types, imported and local classes, situations
causing a reflexive association, oblique and rectilinear routing, formalized and
unformalized associations, and all of the restricted situations defined in
section 6.3.3.

The test checks each of the following success cases (when applicable):  
* The move was completed successfully  
* The move was prevented (restricted situations only)  
* All the association information (number, phrase, mult, cond, etc.) are
  preserved after move  
* The association is unformalized after move  
* Rectilinear routing is working after move (only when rectilinear routing is
  enabled)  
  _Note that the rectilear routing test is used to test requirement 4.8.2 even
  when oblique routing is enabled because the feedback lines should be square
  for reflexive associations_  

### End
