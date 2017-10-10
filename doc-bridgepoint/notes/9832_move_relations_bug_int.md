---

This work is licensed under the Creative Commons CC0 License

---

# Move relation unformalizes and does not roll back
### xtUML Project Implementation Note

### 1. Abstract

It was observed during a demo of feature #9561 [[2.1]](#2.1) that in cases where
association moves are not allowed (e.g. moving a subtype association to another
subtype class in the same association) and the association is formalized, the
move is disallowed, but the association is still unformalized.

### 2. Document References

<a id="2.1"></a>2.1 [#9561 Move relations](https://support.onefact.net/issues/9561) -- Original feature issue  
<a id="2.2"></a>2.2 [#9832 Move relation unformalizes and does not roll back](https://support.onefact.net/issues/9832) -- Issue raised to track the observed bug  
<a id="2.3"></a>2.3 [#9561 implementation note](9561_move_relations/9561_move_relations_int.md)  
<a id="2.4"></a>2.5 [test matrix](https://github.com/xtuml/bptest/tree/master/src/org.xtuml.bp.ui.canvas.test/matrices/non_generated/association_move.txt)  

### 3. Background

None

### 4. Requirements

4.1 After move operations that are disallowed by the tool, associations that
were originally formalized shall remain formalized.  

### 5. Work Required

5.1 Testing

A test first approach was taken to tackle this bug. As much as the bug itself is
a problem, it is a problem that the existing test suite did not identify it.
Currently we have a wide range of cases covered by the degrees of freedom in the
test matrix [[2.4]](#2.4). Analysis revealed that there are actually 28 cases
tested in which an illegal move operation is attempted on formalized
associations. Currently there are 5 expected results that are checked for the
applicable cells in the matrix:  
```
1. moveComplete     "The move was not completed correctly."
2. moveDisallowed   "Disallowed move operation completed."
3. assocInfoSame    "The association details were not preserved."
4. assocUnformal    "The association is formalized after move."
5. rectilinearCheck "Rectilinear routing did not work."
```
For cells which represent move operations that are expected to complete
correctly, R1 is checked which asserts that the move was completed.
Additionally, R4 is checked which asserts that the association is unformalized
after the move. For cells which represent illegal move operations that are
_not_ expected to complete, R2 is checked which asserts that the move was in
fact prevented by the tool, however there is no check for illegal moves
concerning formalization.

5.1.1 Add an expected result for formalized associations

In each matrix cell which represents an illegal move operation on a formalized
association, a new expected result must be checked. This expected result has
been added to the matrix and applied to the 28 cells where it is applicable.  
```
6. assocFormal      "The association is unformalized after disallowed move."
```

5.1.2 Implement the check

The check for formalized associations had to be implemented in the test suite
superclass. Since R4 is the inverse, it was simple to copy the implementation of
the R4 check and negate it.

5.1.3 Regenerate the test suite

The test suite was regenerated to apply the changes to the test matrix. The perl
script in [[2.3]](#2.3) section 6.1 was used to update the suite.

5.1.4 Results

After updating the suite, 28 tests were observed to fail. This is good. Now that
the failures are confirmed to be caught by the test suite, the implementation
can fix them.

5.2 Implementation of the fix

The 'moveAssociation' operation on the 'Association' class performs several
constraint checks and short circuits (returns early) in situations where a
disallowed move is attempted. This operation also checks if the association is
formalized and unformalizes it if necessary. Analysis showed that the
unformalizeation was happening before the constraint checks, therefore even if
the constraints were not satisfied and the move was aborted, the association was
already unformalized.

The fix was as simple as moving the unformalization to execute after the
constraint checks.

### 6. Implementation Comments

None

### 7. Unit Test

7.1 No new tests were added, however the test suite was enhanced as described in
section 5.1

### 8. User Documentation

None

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9832_move_relations_bug  

<pre>

 doc-bridgepoint/notes/9832_move_relations_bug_int.md                                              | 128 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Association/Association.xtuml |  10 +++++-----
 2 files changed, 133 insertions(+), 5 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 9832_move_relations_bug  

<pre>

 src/org.xtuml.bp.ui.canvas.test/matrices/non_generated/association_move.txt                  | 21 +++++++++++----------
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc/AssociationMove.java   | 16 ++++++++++++++++
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/assoc/AssociationMove_0.java | 28 ++++++++++++++++++++++++++++
 3 files changed, 55 insertions(+), 10 deletions(-)

</pre>

### End

