---

This work is licensed under the Creative Commons CC0 License

---

# Move relations
### xtUML Project Implementation Note

### 1. Abstract

User request:  
> Be possible to move one end of a relation from one class to another. Formalized
> relations probably need to be unformalized by such operation but I feel that is
> less of a problem than the current delete old > create new > set cardinality >
> formalize sequence.

### 2. Document References

<a id="2.1"></a>2.1 [#9561 Move relations](https://support.onefact.net/issues/9561)  
<a id="2.2"></a>2.2 [#9561 design note](9561_move_relations_dnt.md)  
<a id="2.3"></a>2.3 [test model](https://github.com/xtuml/models/tree/master/test/AssociationMoveTests)  
<a id="2.4"></a>2.4 [test suite](https://github.com/xtuml/bptest/tree/master/src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc)  
<a id="2.5"></a>2.5 [test matrix](https://github.com/xtuml/bptest/tree/master/src/org.xtuml.bp.ui.canvas.test/matrices/non_generated/association_move.txt)  
<a id="2.6"></a>2.6 [#9501 2017 issues SRS](https://docs.google.com/document/d/1ZyV-FZt77RThTWFBUm3PfM1jxXL9baSeKQWYzfrmFrE/edit)  

### 3. Background

None

### 4. Requirements

See [[2.2]](#2.2) section 4

### 5. Work Required

5.1 Implement the `moveAssociation` operation (see [[2.2]](#2.2) section
6.3.1).  
5.2 Implement the `moveConnector` operation (see [[2.2]](#2.2) section 6.2).  
5.3 Update `ShapeGraphicalNodeEditPolicy.java` to invoke the move command on
eligible `ReconnectRequest`s.  
5.4 Build test model.  
5.5 Create test matrix.  
5.6 Generate tests and implement testing procedure.  

### 6. Implementation Comments

6.1 Perl invocation used to generate the tests  
```
perl <git_dir>/bptest/src/org.xtuml.bp.test/UnitTestGenerator.pl <git_dir>/bptest/src/org.xtuml.bp.ui.canvas.test/matrices/non_generated/association_move.txt <git_dir>/bptest/src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc/AssociationMove.java -p org.xtuml.bp.ui.canvas.test.assoc -data
```

6.2 Some changes were made to the script that generates tests from test
matrices. Most notably, it was changed to use spaces instead of hard tabs. Also,
a feature was introduced to allow it to pass the column instance as additional
data to the row instance selection method. Care was taken to maintain
compatibility for other test suites.

### 7. Unit Test

See [[2.2]](#2.2) section 9 for an explanation of the testing for this issue.
Links to the suite, test model, and matrix can be found at references
[[2.3]](#2.3), [[2.4]](#2.4), and [[2.5]](#2.5).

### 8. User Documentation

8.1 This was added to the user documentation under "Model Elements" > "Class
Diagram" > "Association":  
> Associations can be moved from one class to another simply by dragging one
> end of the association and dropping it on a new class or imported class. This
> saves the modeler the time of deleting and recreating an association with all
> the relationship information. If a formalized association participates in a
> move operation, the association will first be unformalized. No attempt will
> be made to automatically reformalize after the move is complete.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9561_move_relations  

<pre>

 doc-bridgepoint/notes/9561_move_relations/9561_move_relations_ant.md                                            | 113 ++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9561_move_relations/9561_move_relations_dnt.md                                            | 283 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9561_move_relations/9561_move_relations_int.md                                            | 123 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9561_move_relations/assoc.png                                                             | Bin 0 -> 981620 bytes
 doc-bridgepoint/review-minutes/9561_move_relations_dnt_rvm.md                                                   |  36 +++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Association/Association.xtuml               | 165 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Class As Link/Class As Link.xtuml           |  40 +++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Class As Subtype/Class As Subtype.xtuml     |  40 +++++++++++++++++++
 src/org.xtuml.bp.doc/Reference/UserInterface/xtUMLModeling/ModelElements/HTML/ClassDiagram.htm                  |  68 +++++++++++++++++++++++++-------
 src/org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/Canvas Tools/Model Tool/Model Tool.xtuml |  69 ++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/policies/ConnectionPolicy.java                        |  51 +++++++++++++++++++++++-
 src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/policies/ConnectorGraphicalNodeEditPolicy.java        |  26 ------------
 src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/policies/ShapeGraphicalNodeEditPolicy.java            | 390 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++----------------------------------------------------------------------
 13 files changed, 1210 insertions(+), 194 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 9561_move_relations  

<pre>

 src/org.xtuml.bp.test/UnitTestGenerator.pl                                                    | 1292 +++++++++++++++++++++---------------------
 src/org.xtuml.bp.ui.canvas.test/matrices/non_generated/association_move.txt                   |   69 +++
 src/org.xtuml.bp.ui.canvas.test/src/GlobalsCanvasSuite2.java                                  |    6 +-
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc/AssociationMove.java    |  824 +++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc/AssociationMoveGPS.java |  113 ++++
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc/AssociationMove_0.java  | 6167 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 6 files changed, 7827 insertions(+), 644 deletions(-)

</pre>

Fork/Repository: leviathan747/models  
Branch: 9561_move_relations  

<pre>

test/AssociationMoveTests/*

</pre>

### End
