---

This work is licensed under the Creative Commons CC0 License

---

# Convert GPS Watch to Use Deployments
### xtUML Project Implementation Note



### 1. Abstract

The welcome plugin must be updated so that the GPS Watch example now uses deployments in order to make the modeling more easily understandable. 

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #11450](https://support.onefact.net/issues/11450) This is the main issue.  
<a id="2.2"></a>2.2 [BridgePoint DEI #10525](https://support.onefact.net/issues/10525) This is the issue that implemented deployments.  
<a id="2.3"></a>2.3 [BridgePoint DEI #10525](https://github.com/leviathan747/bridgepoint/blob/10525_deployments/doc-bridgepoint/notes/10525_deployments/10525_deployments_dnt.md) This is the design note for deployments. 

### 3. Background

None

### 4. Requirements

4.1 The GPS Watch example shall be converted to use deployments.  
4.2 Code shall be generated.  
4.2.1 Prove that the application still runs.  
4.3 The welcome plugin shall be updated.    


### 5. Work Required

5.1 Import the projects GPS_Watch, Location, Tracking, hearRateMonitor, UI, and LOG from the models repository.  
5.2 Delete the GPS_Watch package within the GPS_Watch project.   
5.3 Create a new deployment called GPS_Watch within a new package called GPS_Watch.  
5.4 Import terminators from component.  
5.5 In a second workspace, open the Example Application - GPS Watch (MASL) under Quick Start. 



### 6. Implementation Comments

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

### 7. Unit Test

Outline all the unit tests that need to pass and describe the method that you
will use to design and perform the tests. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

### 8. User Documentation

Describe the end user documentation that was added for this change. 

### 9. Code Changes

Fork/Repository: < enter your fork and repo name name >
Branch: < enter your branch name here >

<pre>

 Put the file list here 

</pre>

### End
