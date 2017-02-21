---

This work is licensed under the Creative Commons CC0 License

---

# Analyze profiling data from 5.2.2 and 6.0.0
### xtUML Project Analysis Note

1. Abstract
-----------
This note describes the analysis of the profiling data capture by [4].   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9132]() Analyze profiling data for 5.2.2 and 6.0.0   
<a id="2.2"></a>2.2 [BridgePoint Profiling Report 5.2.2](https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/9111_Profile_BridgePoint/BridgePoint_Profile_Report_v5.2.2.md) Report for profiling 5.2.2   
<a id="2.3"></a>2.3 [BridgePoint Profiling Report 6.0.0](https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/9111_Profile_BridgePoint/BridgePoint_Profile_Report_v6.0.0.md) Report for profiling 6.0.0    

3. Background
-------------
A notable slow down in performance has shown between releases 5.2.2 and 6.0.0.  Profiling was performed on these two releases along with a regression comparison of profiling data.   

4. Requirements
---------------
4.1 Analyze profiling data from releases 5.2.2 and 6.0.0   

5. Analysis
-----------
5.1 Analysis of all profiling sessions

Looking through the profiling data provided by the sessions outlined in this document, it is determined that the most relevant data is present in the inital load regression data [2.3:7.1].  The other session data is valuable but shows that there are no significant regressions in performance.  In certain situations the other profiling cases could show worse times, but they would be related to such cases triggering load.

5.1.1 Analysis of intial load

Looking at the regression results in [2.3:7.1] shows a significant increase in CPU time.  The first and most important data point is not the time, but instead the increase in number of invocations for many of the hot spots captured.  The initial load test of course is exercising the BridgePoint load code.  Seeing the increase in invocations across multiple methods is not suprising.  The important thing to notice here is that something was changed to trigger loading invocations more times than previously.  The most important thing to look at in this data is the invocations of the processStatement method, it has increased by 9,772.  This could mean that the tool is either reloading files now during initial load or global data has been added containing 9,772 insert statements.  On the other hand it could be a simple matter of code changing the way lazy loading is working.   The latter is most likely going to be the cause and if so we simply are taking the hit early on now.  If this is a matter of more data being loaded up front we will need deeper analysis.   

6. Work Required
----------------
6.1 Investigate reasons for additional invocations  
6.1.1 Determine why there are 9,772 more invocations in 6.0.0 when using the same data set   
6.2 After determining the reason for the additional invocations address the issue by returning to the same number as in 5.2.2   
7. Acceptance Test
------------------
7.1 Unless design shows that we must have the additional invocations (added required model data in globals), or we are simply loading more than we were before, the number of invocations should match between 5.2.2 and 6.0.0   

End
---
