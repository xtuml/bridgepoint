BridgePoint Profiling Session

System Configuration
-----------
OS: Debian 8   
CPU: 2.70Ghz 2 processors   
Machine ID: development-one-debian   
Base BridgePoint Version: 6.0.0   
Comparison BridgePoint Version: 5.2.2 
Profiler: JProfiler   
Profile Engineer: Travis London   

Profile Procedures
----------------------
1. BridgePoint initial load   

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
  
Profiling Results   
-------------
2. [Initial Load Profiling Results](https://drive.google.com/open?id=0Bw01o4iXr5FuMmdPQUpFdW5fMG8)      
2.1 [Initial Load Profiling Snapshot](https://drive.google.com/open?id=0Bw01o4iXr5FuajZ4MFdraUNFS0U).  

Regression Results   
---------

3. v5.2.2 - v6.0.0   

3.1 In JProfiler compare the v5.2.2 and v6.0.0 snapshots by choosing Session > Compare Snapshots in New Window menu   
3.2 Click the + toolbar button and add v5.2.2.jps and v6.0.0.jps snapshots   
3.3 Select the two snapshots in the Available Snapshots tree   
3.4 Right click and choose the Create CPU comparison item   
3.5 Choose the Hot spot comparison option and click finish   
3.6 Right click on the root in the session tree and choose Analyze > Collapse Recursions   
3.7 Expand the tree as done in the profiling section above   
3.8 Export the session data in html format as done in section 1   

[Initial Load Regression Results](https://drive.google.com/open?id=0Bw01o4iXr5FuT1BVc09LUXhmU2c)   


