---

This work is licensed under the Creative Commons CC0 License

---

# Malformed for loop causes tool hang
### xtUML Project Implementation Note

### 1. Abstract

User reports stack overflow when a for loop has an iterator variable and
iteration expression of the same name. For example:
```
for x in x loop
  // do stuff
end loop;
```

### 2. Document References

<a id="2.1"></a>2.1 [#10132 Malformed for loop causes tool hang](https://support.onefact.net/issues/10132)  

### 3. Background

Read the comments in the DEI [[2.1]](#2.1).

### 4. Requirements

4.1 No stack overflow error shall be encountered if a user creates a situation
as shown in section 1.  
4.2 For loop iterator variables shall not be visible outside the body of the
for loop.  

### 5. Work Required

5.1 Add code to filter instances of `LoopVariable` out of the top level scope.
They will be added back to the scope later inside the for loop.  

### 6. Implementation Comments

None.

### 7. Unit Test

This test assumes the tester is using a fresh workspace and has set the default
dialect to MASL.

7.1 Download `test10132_2.zip` from the issue [[2.1]](#2.1). Import the
contained project into the workspace.  
7.2 Navigate to test10132 > Tester > Tester > functions > test_func, open it.  
7.3 Verify that there are no parse errors initially.  
7.4 Uncomment line 6. Verify that a parse error is shown on "the_clazz".  
7.5 Comment out the first for loop (lines 11-15).  
7.6 Uncomment line 19. Verify that a parse error is shown on the second
"the_clazz". Verify that no stack overflow error occurred.  

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 10132_for_loop  

<pre>

 doc-bridgepoint/notes/10132_for_loop_int.md                                                                            | 74 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl/src/org/xtuml/bp/xtext/masl/scoping/MASLScopeProvider.xtend | 25 ++++++++++++++++++-------
 2 files changed, 92 insertions(+), 7 deletions(-)

</pre>

### End

