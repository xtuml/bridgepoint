---

This work is licensed under the Creative Commons CC0 License

---

# Import project from disk
### xtUML Project Analysis Note

### 1. Abstract

Import Project from disk is buggy. You need to restart the editor for the model
to appear in model Explorer

### 2. Document References

<a id="2.1"></a>2.1 [#9556 Import project from disk](https://support.onefact.net/issues/9556)  

### 3. Background
To reproduce the problem:  

Import > Existing Project into Workspace  
Do not select “copy into workspace”  
Result - In ME the project appears empty  
Close and open project  
It is back  

### 4. Requirements

4.1 Eclipse "Import Existing Projects into Workspace" shall work with xtUML
projects  
4.1.1 Model elements shall be loaded without a manual refresh  

### 5. Analysis

5.1 The issue is related to the UIUtil refresh capability. I have confirmed
that the resource listener properly handles the new resource events, however
something is going wrong with the refresh utility.  
5.2 I am using import through a git project as a comparison, since importing
existing git projects works.  
5.3 Set a breakpoint in the `refresh` method of this class:
`org.xtuml.bp.core/src/org/xtuml/bp/core/util/UIUtil.java` to start debugging  
5.3.1 Test using the path outlined in section 7  
5.3.2 Test using git project import  

### 6. Work Required

6.1 Discover the source of the bug through further debugging analysis  
6.2 Patch the bug  
6.3 Documentation and process  

Estimated time: 2 man days

### 7. Acceptance Test

7.1 Import GPS watch  
7.1.1 In a fresh workspace, select "File > Import..."  
7.1.2 Select "General > Existing Projects into Workspace"  
7.1.3 Under "Select root directory" click "Browse...". Navigate to `<models
repo>/applications/gps/GPS Watch`  
7.1.4 Assure that "Copy projects into workspace" is unchecked and click "Finish"  
7.1.5 After load completes, verify that the packages under the "GPS Watch"
project are accessible from the model explorer  

### End
