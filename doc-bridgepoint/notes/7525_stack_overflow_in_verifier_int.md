---

This work is licensed under the Creative Commons CC0 License

---

# Stack Overflow Error in Verifier on a Reflexive with Multiple Identifiers.
### xtUML Project Implementation Note

1. Abstract
-----------
This document describes the fix for a reported bug.  Stack Overflow Errors can
occur in the Session Explorer and Variables Pane of Verifier when there is a
referential attribute with multiple participants.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #7525](https://support.onefact.net/issues/7525)
Stack Overflow Error in Verifier with breakpoints in code manipulating combined
reflexive and non-reflexive associations.

3. Background
-------------
Following the steps as outlined in the bug report [[2.1]](#2.1) did not
reproduce the problem.  However, when that model was exported and then imported
into a new project the error was consistentily reproduced.



4. Requirements
---------------
This section is only required if there is no preceding design note. 
If present it describes the requirements that need to be satisfied.  If there 
is an SRS, this section may refer to it.  Each requirement should be as short 
and simple as possible and must be clearly defined. Here is an example reference to the Document References section [[2.1]](#2.1)

4.1 Item 1  
4.1.1 Example sub-item
* Example List Element
  * Example Sub list item

4.2 Item 2  
4.2.1 Example sub-item
* Example List Element

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
This is a manual test.

Setup:  Import ```7525_model``` from the xtuml/models repository ```test/```
folder.

7.1 Session Explorer Test  
* Launch Verifier
* Go to the Session Explorer window.
* Right-click and Execute ```Setup``` under ```FunctionsPackage```.
* Expand ```Node``` under ```ClassesPackage```.
* __R__ Success (The "Unhandled event loop exception java.lang.StackOverflow
  Error" pop-up does not occur.)

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

8. User Documentation
---------------------
None. 

9. Code Changes
---------------
Fork/Repository: jason-rhodes/bridgepoint  
Branch: 7525_stack_overflow_in_verifier

<pre>

< Put the file list here >

</pre>

End
---

