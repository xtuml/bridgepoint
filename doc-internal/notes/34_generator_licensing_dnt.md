---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Generator can't locate display key when build ran on a referenced project in a workspace
### xtUML Project Design Note


1. Abstract
-----------
A customer is using a workspace that contains "references" to projects that live
on disk in directories that are not under the current workspace directory.  When 
the build process is launched, the license key "MGLS_ATTR_DISPLAY" is not passed
successfully from eclipse to generator causing a generator license failure.

2. Document References
----------------------
[1] Issues 34, https://github.com/xtuml/internal/issues/34  
[2] DEI dts0100995063  
[3] <CVS>/Documentation/internal/technical/notes/dts0100969061-969050.int

3. Background
-------------
BridgePoint 4.0 introduced a new mechanism [3] for our handling of the MGLS_ATTR_DISPLAY
key that is used to share composite model compiler licenses between eclipse and 
generator.  This mechanism works fine when the projects are physically located under the
workspace on disk.  It does not work when projects are located in physical locations that
are under the workspace directory.

4. Requirements
---------------
4.1  A project that physically lives in the workspace builds successfully with a single
  composite license.  
4.2  A project that physically lives outside the workspace builds successfully with a single
  composite license.  
  
5. Analysis
-----------
5.1  BridgePoint has a bug in generator that is cuasing the license failure the customer
  is experiencing.  What is happening is this:
- BridgePoint is writing the <workspace>/.metadata/.xtumldisplay file with the 
  <machine>+<workspace> info as we designed it [3].
- Generator is invoked on a project in the workspace.  It then looks for the .xtumldisplay 
  file using a relative path from the project to the workspace .metadata/ folder.  

5.2  This mechanism works fine, as long as the project is actually in the workspace.  But 
  eclipse lets you have projects in the workspace that actually live elsewhere on disk (e.g. 
  a project that lives in a git repository at c:/gitrepo/ but you're running a workspace at 
  c:/workspace/current).  

  Thus, generator fails to find the .xtumldisplay file and fails to check out the model compiler
  license because generator defaulted to PID as the display data since it couldn't read it 
  passed in from eclipse.


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

