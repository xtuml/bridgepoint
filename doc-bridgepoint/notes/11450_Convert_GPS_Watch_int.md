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
<a id="2.4"></a>2.4 [BridgePoint SR #10320](https://support.onefact.net/issues/10320) Project Primus Documentation
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
5.4.1 Note: a couple bugs were found at this point. See comments 8 and 9 on issue #10525 [[2.2]](#2.2).  
5.5 Copy the messages in Example Application - GPS Watch (MASL) under Quick Start to the respective messages in the new deployment.  
5.6 Export to MASL and run configurations.  
5.7 Package the MASL version of the models.  
5.8 Copy the file into the BridgePoint repository and commit.  

### 6. Implementation Comments

None  

### 7. Unit Test

7.1 Check for the deployments in Example Application - GPS Watch (MASL).  
7.1.1 Open Example Application - GPS Watch (MASL) from the Quick Start tab in the welcome plug in of BridgePoint.  
7.1.2 The messages under the GPS_Watch package in the GPS_Watch folder should be stored in deployments.  


### 8. User Documentation

8.1 Documentation for this work will be incorporated into the documentation provided as part of issue #10320 [[2.4]](#2.4).  

### 9. Code Changes  

Fork/Repository: ellyhume/bridgepoint  
Branch: 11450_convert_GPS

<pre>

 doc-bridgepoint/notes/11450_Convert_GPS_Watch_int.md                  | 101 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.welcome/models/GPS_Watch_MASL.zip                    | Bin 27112121 -> 27070372 bytes
 2 files changed, 103 insertions(+), 2 deletions(-)

</pre>

Fork/Repository: ellyhume/models  
Branch: gps_masl

<pre>

 applications/gps/GPS/GPS_Watch/.dependencies                                        |    7 +-
 applications/gps/GPS/GPS_Watch/.settings/language.settings.xml                      |   14 +
 applications/gps/GPS/GPS_Watch/masl/GPS_Watch/GPS_Watch.prj                         |   12 +-
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/GPS_Watch.xtuml                     |   72 +++--
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/GPS_Watch/GPS_Watch.masl            |  173 ++++++++++
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/GPS_Watch/GPS_Watch.prj             |    8 +-
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/GPS_Watch/GPS_Watch.xtuml           | 3655 ++++++++++++++++++++++++++++++++++++++++++++++++++-------------------------------------------------------------------------------------------------------------------------------------------------------------
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/GPS_Watch/GPS_Watch/GPS_Watch.masl  |  173 ----------
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/GPS_Watch/GPS_Watch/GPS_Watch.xtuml | 1822 -------------------------------------------------------------------------------------------------------
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/GPS_Watch/Shared/Shared.xtuml       |  389 ++++++++++++++++++++++
 applications/gps/GPS/GPS_Watch/models/GPS_Watch/types/types.xtuml                   |   87 +++++
 applications/gps/GPS/HeartRateMonitor/.settings/language.settings.xml               |   14 +
 applications/gps/GPS/Location/.settings/language.settings.xml                       |   14 +
 applications/gps/GPS/Tracking/.settings/language.settings.xml                       |   14 +
 applications/gps/GPS/UI/.cproject                                                   |    3 +-
 applications/gps/GPS/UI/.settings/language.settings.xml                             |   14 +
 masl/EEs/LOG/.settings/language.settings.xml                                        |   14 +
 17 files changed, 1673 insertions(+), 4812 deletions(-)

</pre>

### End
