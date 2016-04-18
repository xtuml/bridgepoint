---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Implementation Note

1. Abstract
-----------
In this section, give a summary of the design that this note aims to
describe.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8321](https://support.onefact.net/redmine/issues/8321) 
This is a link to this issue in the issue tracking system.  

<a id="2.2"></a>2.2 [Analysis of Model Element Move #8031](https://support.onefact.net/redmine/issues/8031) 
The [analysis produced by this work allowed]
(../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md) was used 
during the SOW creation to help define the requirements for this project.  

<a id="2.3"></a>2.3 [Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document.  

<a id="2.4"></a>2.4 [UnitTestGenerator.pl](https://github.com/xtuml/bridgepoint/blob/master/src/org.xtuml.bp.test/UnitTestGenerator.pl)  
This is a link to the BridgePoint utility that is used to generate test suites 
from a defined test matrix.  

<a id="2.5"></a>2.5 [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)  


3. Background
-------------

See the background in [[2.2]](#2.2) as well as the background in the SOW [[2.3]](#2.3).

4. Requirements
---------------
see [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)

5. Work Required
----------------
Elaborate on each point of the Work Required section of the design note and
describe how you implemented each step.  
If there is no design note, this section, breaks out the consequential work 
(as a numbered list) needed to meet the requirements specified in the 
Requirements section. Here is an example reference to the Document References section [[2.1]](#2.1)

5.1 Item 1  
5.1.1 Example sub-item
* Example List Element

5.2 Item 2  
5.2.1 Example sub-item
* Example List Element

6. Implementation Comments
--------------------------
If the design cannot be implemented as written or if it needs some modification,
enumerate the changes to the design in this section.  If there was no preceding
design note, then this section documents any deviations from the implementation
as presented at the pre-implementation engineering review. Here is an example reference to the Document References section [[2.1]](#2.1)

6.1 Item 1  
```java
    // java code example
    public void clearDatabase(IProgressMonitor pm) 
    {
        // clear the corresponding graphics-root's database
        OoaofgraphicsUtil.clearGraphicsDatabase(rootId, pm);

        Ooaofooa.getDefaultInstance().fireModelElementUnloaded(this);
    }
```
6.1.1 Example sub-item
* Example List Element

6.2 Item 2  
6.2.1 Example sub-item
* Example List Element

7. Unit Test
------------
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
Describe the end user documentation that was added for this change. 

9. Code Changes
---------------
Branch name: < enter your branch name here >

<pre>

< Put the file list here >

</pre>

End
---

