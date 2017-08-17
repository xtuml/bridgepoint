---

This work is licensed under the Creative Commons CC0 License

---

# Title goes here
### xtUML Project Design Note


Note: Each section has a description that states the purpose of that section.
Delete these section descriptions before checking in your note.  Delete this
note as well.

### 1. Abstract

In this section, give a summary of the design that this note aims to
describe.

### 2. Document References

In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.  
<a id="2.1"></a>2.1 [BridgePoint DEI #xxx1](https://support.onefact.net/issues/xxx1) TODO: Add description here.  
<a id="2.2"></a>2.2 [BridgePoint DEI #xxx2](https://support.onefact.net/issues/xxx2) TODO: Add description here.  
<a id="2.3"></a>2.3 [BridgePoint DEI #xxx3](https://support.onefact.net/issues/xxx3) TODO: Add description here.  

### 3. Background

In this section, outline the important points relating to this issue/bug that
the reader would need to know in order to understand the rest of this
document. Here is an example reference to the Document References section [[2.1]](#2.1)

![My Image](myimage.jpg)  

### 4. Requirements

This section is only required if there is no preceding analysis note. 
If present it describes the requirements that need to be satisfied.  If there 
is an SRS, this section may simply refer to it.  Each requirement should be as 
short and simple as possible and must be clearly defined. Here is an example reference to the Document References section [[2.1]](#2.1)

4.1 Item 1  
4.1.1 Example sub-item
* Example List Element
  * Example Sub list item

4.2 Item 2  
4.2.1 Example sub-item
* Example List Element

### 5. Analysis

This section is only required if there is no preceding analysis note. If present
it sets out a brief analysis of the problem to be resolved by this design note. Here is an example reference to the Document References section [[2.1]](#2.1)

5.1 Item 1  
5.1.1 Example sub-item
* Example List Element

5.2 Item 2  
5.2.1 Example sub-item
* Example List Element

### 6. Design

In this section, describe in detail each step of the Work Required section of
the analysis, how the task will be accomplished, what technologies will
be used, algorithms, etc. Here is an example reference to the Document References section [[2.1]](#2.1)

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

### 7. Design Comments

If research carried out during this phase shows that a requirement stated in the
analysis note is infeasible or needs some modification, enumerate those changes
here. If there was no preceding analysis note, then this section documents any
deviations from the design as presented at the design review. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

### 8. User Documentation

Describe the end user documentation that was added for this change. 

### 9. Unit Test

9.1 Start with a clean bridgepoint and bptest repository  
9.2 Configure build_configuration.sh to match your test workspace  
9.3 Configure build_configuration.sh so INCLUDE_TESTS=true  
9.4 Remove the destination workspace if existing  
9.5 Remove ~/.m2  
9.6 Modify build_and_test_bp.sh removing the "test" argument to build_project.sh  
9.7 Run build_and_test_bp.sh  
9.R Network is used, downloading maven repositories  
9.R All projects are built successfully, including test projects  
9.R No tests were run  
9.8 Disable network connection  
9.9 Start BridgePoint pointing at the test workspace  
9.R Build automatically triggers building of the workspace  
9.R No maven build was run  
9.R The JDT builder is run and there are no errors in the workspace  
9.10 Configure ui.text.test to disable the Build buider and enable the Test builder  
9.11 ui.text.test is built using maven  
9.12 ui.text.test is run  
9.13 Restore the builders  
9.14 Make a java change to cause an error in ui.text.test and build  
9.R No maven build was run, java error is visible in problems view
9.15 Make a java change in bp.core and build  
9.R No maven build was run  
9.16 For the file types, .xtuml, .sql, .arc and .inc  
9.16.1 Touch the file type  
9.16.2 Build the project  
9.16.R The maven build is run  
9.16.R The maven build is successful    
9.16.R The ant targets are run  
9.17 Run clean on ui.canvas  
9.R The maven clean is run  
9.R The maven clean is successful
9.18 Run build on ui.canvas  
9.R The maven build is run  
9.R The maven build is successful  
9.R All code is regenerated  
9.19 Run build_project.sh org.xtuml.bp.core.test  
9.R No maven build is run  
9.20 Run build_project.sh org.xtuml.bp.core.test test  
9.R The maven build is run  
9.R The maven build is successful  
9.R The core.tests are run  
9.21 Run build_project.sh org.xtuml.bp.core.test test  
9.R The maven build is run  
9.R The maven build is successful
9.R The core.tests are started but waiting for a remote debug connection  
9.22 Kill build_project.sh  
9.23 Restore network connection  
9.24 Restore the "test" argument to build_project.sh in build_and_test_bp.sh  
9.25 Run build_and_test_bp.sh  
9.R All projects are built successfully  
9.R All tests run and pass 100%  

### End
