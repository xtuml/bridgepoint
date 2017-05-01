---

This work is licensed under the Creative Commons CC0 License

---

# Profile BridgePoint
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the steps taken to profile the current 6.0 release as well as the previous 5.2.2 release.  In addition it covers creation of a formal format for capturing results.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9110](https://support.onefact.net/issues/9110) Research best tool for profiling BridgePoint and define profiling procedure  
<a id="2.2"></a>2.2 [BridgePoint DEI #9111](https://support.onefact.net/issues/9111) Profile BridgePoint using procedural techniques described by the engineering process  
<a id="2.3"></a>2.3 [9110 implementation note](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/9110_Research_bp_profiling_tool/9110_Research_bp_profiling_tool.int.md) Implementation note describing tool selection and procedures.
<a id="2.43"></a>2.4 [Profiling Procedures](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/profiling/Bridgepoint_Profiling.md) Document describing procedures for profiling BridgePoint    

3. Background
-------------
The tool has seen recent degradation in performance.  This has been especially noted in load time as well as perspective switching.  [2.3] covers selecting a profiling tool as well as describing procedures for profiling.  This issue will produce a format for profiling results as well as profile the tool.

4. Requirements
---------------

4.1 Create a format for capturing profile results  
4.1.1 Determine structure and location for profiling results
4.1.2 Determine result format and file types
4.2 Profile BridgePoint under cases where slow down has been noticed  
4.2.1 Agree with team on areas to profile   
4.2.2 Profile and capture results for each area agreed upon for version 6.0.0 and 5.2.2   
4.2.3 Capture a regression profiling session between 6.0.0 and 5.2.2 for each area agreed upon   

5. Work Required
----------------

5.1 Create a format for capturing profile results
5.1.1 Create document template for profiling report   
5.1.1.1 [2.4] is created describing the procedure used as well as where the results are located.        
5.1.1.2 JProfiler allows for export to html or xml, html is currently chosen as the output is very readable      
5.1.2 Capture snapshots from each profiling session
5.2 Structure and location
5.2.1 Structure   

* BridgePoint_ProfilerSession_v< versionNumber >/BridgePoint_< shortProfileDesc >_< issueNum >/BridgePoint_Profiling_Report_v< versionNum >.md      
* BridgePoint_ProfilerSession_v< versionNumber >/BridgePoint_< shortProfileDesc >_< issueNum >/BridgePoint_Profiling_< shortProfileDesc >_v< versionNum >.html   
* BridgePoint_ProfilerSession_v< versionNumber >/BridgePoint_< shortProfileDesc >_< issueNum >/BridgePoint_Profiling_< shortProfileDesc >_v< versionNum >.jps   
* BridgePoint_ProfilerSession_v< versionNumber >/BridgePoint_< shortProfileDesc >_< issueNum >/BridgePoint_Regression_< shortProfileDesc >_v< versionNumOne >_v< versionNumTwo >.html   

5.2.2 Location   

All files will be stored with the above structure under this location, except for the BridgePoint_Profiling_Report_v< versionNum >.md file:

[Profile Data Location](https://drive.google.com/drive/u/1/folders/0B834tggB4vylSzZEYkw3TGM3OVU)   

5.3 Profile BridgePoint   
5.3.1 The following areas are profiled:   

* Initial load   
* First switch to C/C++ perspective   
* First right click CME population   
* First open of a canvas   
* First open of an OAL editor   
* First MASL editor   

5.3.2 Capture results as described in [2.4]   
5.3.3 Capture a comparison of profiling data for each area profiled in 5.2.2 and 6.0.0   
  
6. Implementation Comments
--------------------------

7. Unit Test
------------

8. User Documentation
--------------------- 

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: master

<pre>

doc-bridgepoint/notes/9110_Research_bp_profiling_tool/9110_Research_bp_profiling_tool.int.md   
doc-bridgepoint/notes/9110_Research_bp_profiling_tool/BridgePointProfiler.docx
doc-bridgepoint/notes/9111_Profile_BridgePoint/9111_Profile_BridgePoint.md
doc-bridgepoint/notes/9111_Profile_BridgePoint/BridgePoint_Profile_Report_v5.2.2.md
doc-bridgepoint/notes/9111_Profile_BridgePoint/BridgePoint_Profile_Report_v6.0.0.md
doc-bridgepoint/process/profiling/BridgePoint_Profiling.md
doc-bridgepoint/process/templates/BridgePoint_Profile_Report.md

</pre>

End
---

