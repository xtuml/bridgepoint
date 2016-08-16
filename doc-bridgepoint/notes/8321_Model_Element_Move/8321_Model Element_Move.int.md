---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the work performed to implement the Model Element Move
feature.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8321](https://support.onefact.net/redmine/issues/8321) 
This is a link to this issue in the issue tracking system.  

<a id="2.2"></a>2.2 [Model Element Move design note](8321_Model_Element_Move/8321_Model_Element_Move.dnt.md) 

<a id="2.3"></a>2.3 [UnitTestGenerator.pl](https://github.com/xtuml/bridgepoint/blob/master/src/org.xtuml.bp.test/UnitTestGenerator.pl)  
This is a link to the BridgePoint utility that is used to generate test suites 
from a defined test matrix.  

<a id="2.4"></a>2.4 [Test Model Creation -  #8458](https://support.onefact.net/issues/8458)  
Test model(s) for this issue.  

3. Background
-------------
See [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)

4. Requirements
---------------
See [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)

5. Work Required
----------------


6. Implementation Comments
--------------------------


7. Unit Test
------------
The test model is found with the [Test Model Creation Issue](#2.4).  

7.1 Use the [[test generation utility]](#2.2) to generate tests for all source and target permutations. Note that documentation for this utility is found in the header of this perl script.  The goal of these generated tests is to assure all requirements are satisfied for each generated test. This assure that requirements are satisfied for each test permutation.  

7.1.1 Create a test matrix that defines all possible "degrees of freedom" for source and target selection.   

7.1.2 Add this matrix to bp.core.test plugin as a new test suite.  

7.1.3 Modify bp.core.test/generate.xml to generate the test suite from the test matrix using the test generation utility.  

7.1.4 Implement the pieces of the suite that the test generation utilitie's results require additional work for.  

7.1.4.1 Each generated tests shall assure that element IDs are not modified during move.  

7.1.4.2 Each generated tests shall assure that move is performed as an atomic operation.  

7.1.4.2.1 After the move a single "undo" restore the model to the state it was in prior to the move operation  

7.1.4.2.2 If the move operation is canceled no changes shall be made.  

7.1.4.3 Each generated test shall assure that its result modifies the minimum number of files necessary.  

7.1.5 Run the suite and assure it passes  

8. User Documentation
---------------------
TODO: Describe the end user documentation that was added for this change. 

9. Code Changes
---------------
Branch name: 8321_Model_Element_Move

<pre>

< Put the file list here >

</pre>

End
---

