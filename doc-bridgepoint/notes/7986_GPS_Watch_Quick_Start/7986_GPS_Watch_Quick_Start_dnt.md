---

This work is licensed under the Creative Commons CC0 License

---

# Ready GPS Watch for Quick Start
### xtUML Project Design Note


1. Abstract
-----------
The GPS Watch model as managed in the [models repository](https://github.com/xtuml/models) is to be launched 
from the Quick Start section of the BridgePoint Welcome screen.

2. Document References
----------------------
In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.
[1] [BridgePoint DEI #1](https://support.onefact.net/redmine/issues/1)  
[2] [BridgePoint DEI #2](https://support.onefact.net/redmine/issues/2)  

3. Background
-------------
In this section, outline the important points relating to this issue/bug that
the reader would need to know in order to understand the rest of this
document.

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

