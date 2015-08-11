---

This work is licensed under the Creative Commons CC0 License

---

# Meta-model cleanup from generic package migration (remove specialized package support)
### xtUML Project Design Note


1. Abstract
-----------
This issue is raised to remove specialize package support from BridgePoint.

2. Document References
----------------------
In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.
[1] [BridgePoint DEI #1](https://support.onefact.net/redmine/issues/1)  
[2] [BridgePoint DEI #2](https://support.onefact.net/redmine/issues/2)  

3. Background
-------------
BridgePoint was enhanced to support generic packages in 2011 (BridgePoint version 3.3.0). The issue for that work is described by the analysis note found in historical documentation here: [Redmine Historical issue #1227](https://github.com/xtuml/internal/tree/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20110614/technical/notes/dts0100631941)

That analysis note described 4 phases. The final phase, phase 4, describes a "cleanup phase" where all the specialized package artifacts are removed. Of course this step means that BridgePoint shall no longer contain any support for the old specialized packages. Specialized packages have not been used now for several years, and this issue is now raised to perform this clean up and remove specialized package artifacts, including all the OAL that is in place to support them.

This step is being taken now to cleanup the meta model and OAL as we begin to undertake some new significant changes to the metamodel. It is being done at this time because these artifacts and all the OAL in place to support them can be confusing to new developers that do not understand the history of the tool. It will also cut the amount of generated code significantly thus speeding the BridgePoint build cycle.

4. Requirements
---------------
4.1  All specialized packages shall be removed from the ooaofooa
4.2  All generic package model migration code shall be removed from BridgePoint
4.3  
4.4

5. Analysis
-----------
This section is only required if there is no preceding analysis note. If present
it sets out a brief analysis of the problem to be resolved by this design note.

5.1 Item 1  
5.2 Item 2  
5.3 Item 3  

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
