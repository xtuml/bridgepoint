BridgePoint Profiling Session

System Configuration
-----------
OS: Debian 8   
CPU: 2.70Ghz 4 processors   
Machine ID: development-one-debian   
Base BridgePoint Version: 6.0.0   
Comparison BridgePoint Version: 5.2.2   
Profiler: JProfiler   
Profile Engineer: Travis London   

Profile Procedures
----------------------
1.0 BridgePoint initial load   

Data present:   
org.xtuml.bp.ui.marker   
maslin   
mcooa   
mcshared   

Procedure:   
1.1 Launch profiler  
1.2 Launch BridgePoint and choose the test workspace   
1.3 Open the Git Repositories view and add the bridgepoint and mc repositories    
1.4 Import the org.xtuml.bp.ui.marking, maslin, mcooa and mccommon projects   
1.5 Reset the xtUML perspective   
1.6 Close BridgePoint   
1.7 Launch BridgePoint making sure a workspace chooser dialog is shown   
1.8 Connect profiler to BridgePoint process   
1.8.1 Choose Start Center in JProfiler   
1.8.2 Choose the Quick Attach tab and choose the BridgePoint pid   
1.8.3 Click Start   
1.8.4 Choose Instrumentation on the dialog shown   
1.8.5 Click the Edit button in the Filter settings field   
1.8.6 Click the + button and choose Add profiled package or class   
1.8.7 In the new Profiled entry modify the Class or Package value to org.xtuml.*   
1.8.8 Click the next two OK buttons   
1.9 Begin profiling   
1.9.1 Choose the CPU Views tree entry   
1.9.2 Press the Press to record CPU data button   
1.10 Click OK to start BridgePoint with the given workspace   
1.10 Once BridgePoint is ready for user interaction stop profiling by pressing the Stop CPU tool in the toolbar   
1.11 Create profiling report   
1.11.1 Right click on the root in the profiler data tree    
1.11.2 Choose Analyze > Collapse Recursions   
1.11.3 Expand the three highest cpu usage entries (listed from top to bottom)   
1.11.4 Expand until the highest percentage is broken into multiple calls or you cannot expand further    
1.11.5 Export the session data by clicking the Export tool in the toolbar   
1.11.6 Choose the html format and press OK, give the report a name and save   
1.11.7 Save the session as a snapshot by clicking the Save Snapshot tool in the toolbar   
  
2.0 BridgePoint first switch to C/C++ Perspective   

Data present:   
org.xtuml.bp.ui.marking   
maslin   
mcooa   
mcshared   

Procedure:   
2.1 Launch profiler   
2.2 Launch BridgePoint with the xtUML perspective showing   
2.3 Attach profiler to the BridgePoint process   
2.4 Start CPU Profiling   
2.5 Execute Window > Perspective > Open Perspective > C/C++   
2.6 Once the UI is responsive stop the profiler   
2.7 Create profiling report   
2.7.1 Right click on the root in the profiler data tree   
2.7.2 Choose Analyze > Collapse Recursions   
2.7.3 Expand the three highest cpu usage entries (listed from top to bottom)    
2.7.4 Expand until the highest percentage is broken into multiple calls or you cannot expand further    
2.7.5 Export the session data by clicking the Export tool in the toolbar   
2.7.6 Choose the html format and press OK, give the report a name and save.   
2.7.7 Save the session as a snapshot by clicking the Save Snapshot tool in the toolbar    

3.0 BridgePoint first cme invocation   

Data present:   
org.xtuml.bp.ui.marking   
maslin   
mcooa   
mcshared   

Procedure:   
3.1 Launch profiler   
3.2 Launch BridgePoint with the xtUML perspective showing   
3.3 Attach profiler to the BridgePoint process   
3.4 Start CPU Profiling   
3.5 Right click on any project in Model Explorer   
3.6 Once the UI is responsive stop the profiler   
3.7 Create profiling report   
3.7.1 Right click on the root in the profiler data tree   
3.7.2 Choose Analyze > Collapse Recursions   
3.7.3 Expand the three highest cpu usage entries (listed from top to bottom)   
3.7.4 Expand until the highest percentage is broken into multiple calls or you cannot expand further    
3.7.5 Export the session data by clicking the Export tool in the toolbar   
3.7.6 Choose the html format and press OK, give the report a name and save.   
3.7.7 Save the session as a snapshot by clicking the Save Snapshot tool in the toolbar    

4.0 BridgePoint first canvas open   

Data present:   
org.xtuml.bp.ui.marking   
maslin   
mcooa   
mcshared   

Procedure:   
4.1 Launch profiler   
4.2 Launch BridgePoint with the xtUML perspective showing   
4.3 Attach profiler to the BridgePoint process   
4.4 Start CPU Profiling   
4.5 Navigate to mcooa/ooaofooa in Model Explorer and double click   
4.6 Once the UI is responsive stop the profiler   
4.7 Create profiling report   
4.7.1 Right click on the root in the profiler data tree   
4.7.2 Choose Analyze > Collapse Recursions   
4.7.3 Expand the three highest cpu usage entries (listed from top to bottom)   
4.7.4 Expand until the highest percentage is broken into multiple calls or you cannot expand further   
4.7.5 Export the session data by clicking the Export tool in the toolbar   
4.7.6 Choose the html format and press OK, give the report a name and save   
4.7.7 Save the session as a snapshot by clicking the Save Snapshot tool in the toolbar   

5.0 BridgePoint first oal open   

Data present:   
org.xtuml.bp.ui.marking    
maslin   
mcooa   
mcshared   

Procedure:   
5.1 Launch profiler   
5.2 Launch BridgePoint with the xtUML perspective showing      
5.3 Attach profiler to the BridgePoint process   
5.4 Start CPU Profiling   
5.5 Navigate to maslin/m2x/assocFormalization/getID and double click   
5.6 Once the UI is responsive stop the profiler   
5.7 Create profiling report   
5.7.1 Right click on the root in the profiler data tree   
5.7.2 Choose Analyze > Collapse Recursions   
5.7.3 Expand the three highest cpu usage entries (listed from top to bottom)   
5.7.4 Expand until the highest percentage is broken into multiple calls or you cannot expand further   
5.7.5 Export the session data by clicking the Export tool in the toolbar   
5.7.6 Choose the html format and press OK, give the report a name and save   
5.7.7 Save the session as a snapshot by clicking the Save Snapshot tool in the toolbar   

6.0 BridgePoint first masl open function   

Data present:   
org.xtuml.bp.ui.marking    
maslin   
mcooa   
mcshared   
SAC   
SAC_PROC   

Procedure:   
6.1 Launch profiler   
6.2 Launch BridgePoint with the xtUML perspective showing      
6.3 Attach profiler to the BridgePoint process   
6.4 Start CPU Profiling   
6.5 Navigate to SAC/SAC/SAC/functions/add_group and double click   
6.6 Once the UI is responsive stop the profiler   
6.7 Create profiling report   
6.7.1 Right click on the root in the profiler data tree   
6.7.2 Choose Analyze > Collapse Recursions   
6.7.3 Expand the three highest cpu usage entries (listed from top to bottom)   
6.7.4 Expand until the highest percentage is broken into multiple calls or you cannot expand further   
6.7.5 Export the session data by clicking the Export tool in the toolbar   
6.7.6 Choose the html format and press OK, give the report a name and save   
6.7.7 Save the session as a snapshot by clicking the Save Snapshot tool in the toolbar   

7.0 BridgePoint first masl open state   

Data present:   
org.xtuml.bp.ui.marking    
maslin   
mcooa   
mcshared   
SAC   
SAC_PROC   

Procedure:   
7.1 Launch profiler   
7.2 Launch BridgePoint with the xtUML perspective showing      
7.3 Attach profiler to the BridgePoint process   
7.4 Start CPU Profiling   
7.5 Navigate to SAC/SAC/SAC/SAC/Session/ISM/Session_Established and double click   
7.6 Once the UI is responsive stop the profiler   
7.7 Create profiling report   
7.7.1 Right click on the root in the profiler data tree   
7.7.2 Choose Analyze > Collapse Recursions   
7.7.3 Expand the three highest cpu usage entries (listed from top to bottom)   
7.7.4 Expand until the highest percentage is broken into multiple calls or you cannot expand further   
7.7.5 Export the session data by clicking the Export tool in the toolbar   
7.7.6 Choose the html format and press OK, give the report a name and save   
7.7.7 Save the session as a snapshot by clicking the Save Snapshot tool in the toolbar   

Profiling Results   
-------------
6.0 [Initial Load Profiling Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Profiling_initial_load_v6.0.0.html)      
6.1 [Initial Load Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5FuajZ4MFdraUNFS0U).  

6.2 [First time C/C++ Profiling Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Profiling_first_time_cc++_v6.0.0.html)   
6.3 [First time C/C++ Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5FuNTJGaURWREt0MEU)   

6.4 [First cme invocation Profiling Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Profiling_first_cme_invocation_v6.0.0.html)   
6.5 [First cme invocation Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5FuTGtiNmVURWhqTnM)   

6.6 [First canvas open Profiling Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Profiling_first_canvas_open_v6.0.0.html)   
6.7 [First canvas open Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5FuUzlIVU50TXhiVUE)   

6.8 [First oal open Profiling Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Profiling_first_oal_open_v6.0.0.html)   
6.9 [First oal open Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5FualExZnl5czVLeTA)   

6.10 [First masl open function Profiling Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Profiling_first_masl_open_function_v6.0.0.html)   
6.11 [First masl open function Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5Fuc0wwLWVISFRybmM)   

6.12 [First masl open state Profiling Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Profiling_first_masl_open_state_v6.0.0.html)   
6.13 [First masl open state Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5FuczZxdGFXMmNkV28)   

Regression Results   
---------  
7.1 v5.2.2 - v6.0.0 Intial load   

7.1.1 In JProfiler compare the v5.2.2 and v6.0.0 snapshots by choosing Session > Compare Snapshots in New Window menu   
7.1.2 Click the + toolbar button and add v5.2.2.jps and v6.0.0.jps snapshots   
7.1.3 Select the two snapshots in the Available Snapshots tree   
7.1.4 Right click and choose the Create CPU comparison item   
7.1.5 Choose the Hot spot comparison option and click finish   
7.1.6 Right click on the root in the session tree and choose Analyze > Collapse Recursions   
7.1.7 Expand the tree as done in the profiling section above   
7.1.8 Export the session data in html format as done in section 1   

[Initial Load Regression Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Regression_initial_load_v5.2.2_v6.0.0.html)   

7.2 v5.2.2 - v6.0.0 First time C/C++ Perspective     

7.2.1 Repeat the steps above in 7.1   

[First time C/C++ Regression Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Regression_first_time_cc++_v5.2.2_v6.0.0.html)   

7.3 v5.2.2 - v6.0.0 First cme invocation     

7.3.1 Repeat the steps above in 7.1   

[First cme invocation Regression Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Regression_first_cme_invocation_v5.2.2_v6.0.0.html)   

7.4 v5.2.2 - v6.0.0 First canvas open     

7.4.1 Repeat the steps above in 7.1   

[First canvas open Regression Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Regression_first_canvas_open_v5.2.2_v6.0.0.html)   

7.5 v5.2.2 - v6.0.0 First oal open     

7.5.1 Repeat the steps above in 7.1   

[First oal open Regression Results](http://htmlpreview.github.com/?https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/qa/profiling_data/BridgePoint_Regression_first_oal_open_v5.2.2_v6.0.0.html)   



