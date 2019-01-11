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
<a id="2.3"></a>2.3 [#10525 Design Note](https://github.com/leviathan747/bridgepoint/blob/10525_deployments/doc-bridgepoint/notes/10525_deployments/10525_deployments_dnt.md) This is the design note for deployments. 
<a id="2.4"></a>2.4 [BridgePoint SR #10320] Project Primus Documentation
### 3. Background

None  

### 4. Requirements

4.1 The GPS Watch example shall be converted to use deployments.  
4.2 Code shall be generated.  
4.2.1 Prove that the application still runs.  
4.3 The welcome plugin shall be updated.    


### 5. Work Required

5.1 Import the projects GPS_Watch, Location, Tracking, HeartRateMonitor, UI, and LOG from the models repository.  
5.2 Delete the GPS_Watch package within the GPS_Watch project.   
5.3 Create a new deployment called GPS_Watch within a new package called GPS_Watch.  
5.4 Import terminators from component.  
5.4.1 Note: a couple bugs were found at this point. See comments 8 and 9 on issue #10525 [2.2].  
5.5 Copy the messages in Example Application - GPS Watch (MASL) under Quick Start to the respective messages in the new deployment.  
5.6 Export to MASL and run configurations.  
5.7 Package the MASL version of the models.  


### 6. Implementation Comments

None  

### 7. Unit Test

7.1 Check for the deployments in Example Application - GPS Watch (MASL).  
7.1.1 Open Example Application - GPS Watch (MASL) from the Quick Start tab in the welcome plug in of BridgePoint.  
7.1.2 The messages under the GPS_Watch package in the GPS_Watch folder should be stored in deployments.  


### 8. User Documentation

8.1 Documentation for this work will be incorporated into the documentation provided as part of issue #10320 [2.4].  
8.2 The welcome project shall be changed to reflect the changes in the metamodel. 
8.3 Palette and context menu documentation shall be updated along with MASL documentation.  

### 9. Code Changes  

Fork/Repository: < enter your fork and repo name name >
Branch: < enter your branch name here >

<pre>

 Put the file list here 

</pre>

### End
