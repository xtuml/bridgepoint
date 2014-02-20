---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Title goes here
### xtUML Project Design Note


Note: Each section has a description that states the purpose of that section.
Delete these section descriptions before checking in your note.  Delete this
note as well.

1. Abstract
-----------
In this section, give a summary of the design that this note aims to
describe.

2. Document References
----------------------
In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.
[1] Issues 1, https://github.com/xtuml/doc/issues/1  
[2] Issues 2, https://github.com/xtuml/doc/issues/2  

3. Background
-------------
In this section, outline the important points relating to this issue/bug that
the reader would need to know in order to understand the rest of this
document.

4. Requirements
---------------
This section is only required if there is no preceding analysis note. 
If present it describes the requirements that need to be satisfied.  If there 
is an SRS, this section may simply refer to it.  Each requirement should be as 
short and simple as possible and must be clearly defined.

5. Analysis
-----------
This section is only required if there is no preceding analysis note. If present
it sets out a brief analysis of the problem to be resolved by this design note.

* Item 1
* Item 2
* Item 3

6. Design
---------
In this section, describe in detail each step of the Work Required section of
the analysis, how the task will be accomplished, what technologies will
be used, algorithms, etc.

6.1 Some design point, with a code example
```java
    public void clearDatabase(IProgressMonitor pm) 
    {
        // clear the corresponding graphics-root's database
        OoaofgraphicsUtil.clearGraphicsDatabase(rootId, pm);

        Ooaofooa.getDefaultInstance().fireModelElementUnloaded(this);
    }
```

7. Design Comments
------------------
If research carried out during this phase shows that a requirement stated in the
analysis note is infeasible or needs some modification, enumerate those changes
here. If there was no preceding analysis note, then this section documents any
deviations from the design as presented at the design review.

8. Unit Test
------------
Outline all the unit tests that need to pass and describe the method that you
will use to design and perform the tests.

End
---

