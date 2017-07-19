---

This work is licensed under the Creative Commons CC0 License

---

# Move relations
### xtUML Project Design Note

### 1. Abstract

Be possible to move one end of a relation from one class to another. Formalized
relations probably need to be unformalized by such operation but I feel that is
less of a problem than the current delete old > create new > set cardinality >
formalize sequence.

### 2. Document References

<a id="2.1"></a>2.1 [#9561 Move relations](https://support.onefact.net/issues/9561)  
<a id="2.2"></a>2.2 [#9561 analysis note](9561_move_relations_ant.md)  

### 3. Background

### 4. Requirements

The following definition shall be used for an association move operation:

For a specified class or imported class (C1) participating in association (A),
and second distinct specified class or imported class (C2) existing on a single
diagram, C1 shall be removed from participation with A and C2 shall be placed
into participation with A in the place of C1.

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
4.7 Imported classes in association shall be suppored  
4.8 The current graphical behavior of association editing shall be maintained  
4.8.1 Rectilinear routing shall work with move operations  
4.8.2 Reflexive associations shall provide clean, rectanglular feedback lines  

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

The graphical drag and drop option shall be pursued. In the analysis, a
CME/wizard option was considered. The graphical option is much better for user
experience and through this analysis it is determined that is attainable in the
time frame of the project.

### 6. Design

6.1 Graphics edit policy

6.2 OOA of graphics changes

6.3 OOA of OOA changes

6.3.x Prohibited situations

Some associations are not allowed in a class diagram. Move operations that
introduce these situations will be prohibited by the editor.

6.3.x.1 The link class in a linked associaiton cannot be the "one" or "other"
class  
6.3.x.2 A supertype may not be its own subtype (and vice versa)  
6.3.x.3 A subtype may not be associated to the supertype more than once  

### 7. Design Comments

### 8. User Documentation

### 9. Unit Test

### End
