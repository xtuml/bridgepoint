---

This work is licensed under the Creative Commons CC0 License

---

# Test Ordering Update
### xtUML Project Review Minutes

Reviewed:  https://github.com/nmohamad/bridgepoint/blob/8005_Test_Orderring/doc-bridgepoint/notes/8005_Test_Orderring/8005_Test_Orderring.md  f838cda   
Present:  Bob, Keith, Nehad

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   Bob  general  change file name to 8005_Test_Orderring.ant.md
x- 02  min   Bob  2.2      inocation > invocation
x- 03  min   Bob  3        "will-written" > "well-written"
_- 04  min   Keith 5       Add some more clarity/description of how the test runner class hooks 
    into the existing tests and how we don't have to use it for new test classes we write.
x- 05  min   Bob  5.2      "Test_A2_goo()" > "test_A2_goo()"
x- 06  min   Bob  5.3      "to used" > "to use"
x- 07  min   Keith 6       modify all the places where "dotest" was added.  Roll back the "doTest" 
    change in favor of the new annotations and ordering
x- 08  min   Keith 6       Note that the JUnit Plugin Test launch configs will have to be modified to switch from JU3 to JU4
x- 09  min   Bob  6        Note the existence of the @Timeout tag and how we can use it to 
    check for times creeping up over the years as well as stopping tests that are hung, thus allowing us to use the Launch all BridgePoint tests again
</pre>
   
No major observations, a re-review is not required.






End
---
