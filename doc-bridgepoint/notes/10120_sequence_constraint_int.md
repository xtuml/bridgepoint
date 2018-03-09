---

This work is licensed under the Creative Commons CC0 License

---

# Valid syntax causes problems with parser and export (MASL)
### xtUML Project Implementation Note

### 1. Abstract

The MASL type system allows for constraining sequence types with a maximum size
constraint. When the MASL activity load grammar was introduced, these
constraints were not considered. Attempt to use them causes a Dialog of Death to
appear. This work will update the grammar to allow such constraints.

### 2. Document References

<a id="2.1"></a>2.1 [#10120 Valid syntax causes problems with parser and export (MASL)](https://support.onefact.net/issues/10120)  
<a id="2.2"></a>2.2 [Test model for #10120](https://github.com/xtuml/models/tree/master/masl/test/10120_sequence_constraint)  

### 3. Background

Analysis showed that this problem does not affect MASL round trip and it also
does not affect the MASL Xtext editor. The problem is limited to the load parser
which is responsible for parsing activities from `.masl` files and inserting
them into xtUML structural instances. This happens only _after_ the project is
converted/imported into BridgePoint and then reloaded.

"Dialog of Death" is an error dialog that appears when the tool detects an
inconsistency between the loaded model and the persisted data. It is in place to
prevent users from potentially loosing data. When a user gets in a situation
where the dialog appears, it is usually very difficult to get out of the
situation.

### 4. Requirements

4.1 Size constraints shall be supported on sequence types.  
4.1.1 Integer literals shall be allowed as a size constraint.  
4.1.2 No other expression shall be supported as a size constraint.  

### 5. Work Required

5.1 Modify `src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g` to
allow integer literals as sequence constraints.  
5.1.1 Add a lexer rule for integer literals.  
5.1.2 Add an optional constraint to the sequence production rule.  

### 6. Implementation Comments

None.

### 7. Unit Test

4.2 The test model found at [[2.2]](#2.2) shall be editable without producing
the Dialog of Death.  

7.1 MASL round trip tests shall pass  

7.2 Manual test  

7.2.1 Convert the MASL model found at [[2.2]](#2.2).  
7.2.2 Launch BridgePoint in a fresh workspace. Assure that the default dialect
is set to MASL.  
7.2.3 Create a new xtUML project "10120_sequence_constraint".  
7.2.4 Import the model produced in 7.2.1 into the project created in 7.2.3.  
7.2.5 Close and relaunch BridgePoint.  
7.2.6 Open
`10120_sequence_constraint::SequenceConstraint::SequenceConstraint::functions::foo`
in the MASL editor.  
7.2.7 Assure that the activity has been properly loaded and there are no errors.  
7.2.8 Make an edit and save. Assure that no error dialogs appear.  

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 10120_sequence_constraint 

<pre>

 doc-bridgepoint/notes/10120_sequence_constraint_int.md      | 101 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/actions.g |   4 +++-
 2 files changed, 104 insertions(+), 1 deletion(-)

</pre>

Fork/Repository: leviathan747/models  
Branch: 10120_sequence_constraint 

<pre>

 masl/test/10120_sequence_constraint/SequenceConstraint.int | 10 ++++++++++
 masl/test/10120_sequence_constraint/SequenceConstraint.mod | 10 ++++++++++
 masl/test/10120_sequence_constraint/foo.svc                |  4 ++++
 masl/test/all_tests                                        |  1 +
 4 files changed, 25 insertions(+)

</pre>

### End

