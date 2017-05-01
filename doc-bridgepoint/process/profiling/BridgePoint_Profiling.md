BridgePoint Profiling    
-------    
This note describes the produces for profiling the BridgePoint tool.   

Current Profiler   
-------   
At this time JProfiler is the chosen profiler, however these procedures are generic enough to work with any profiler.    

Procedures
----------

1.1 JVM and Memory

For all but memory leaks see:
See: https://docs.oracle.com/cd/E26576_01/doc.312/e24936/tuning-java.htm#GSPTG00006

1.1.1 Look into tuning JVM, params like -Xms, 
1.1.2 Look into tuning GC, is it running too much?
1.1.3 Look for memory leaks, profile BridgePoint during the life and look through the collected memory data.  Perform deletions and watch the memory size assuring garbage collection occurs.  Also perform operations that close views/editors where temporary data should be garbage collected.
1.1.4 Look into UseParallelGC (garbage collects with multiprocessors)

1.2 Application

1.2.1 Startup

1.2.2.1 Launch profiler   
1.2.2.2 Launch BridgePoint making sure a workspace chooser dialog is shown   
1.2.2.3 Connect profiler to BridgePoint process   
1.2.2.4 Begin profiling   
1.2.2.5 Click OK to start BridgePoint with the given workspace   
1.2.2.6 Once BridgePoint is ready for user interaction pause profiling   
1.2.2.7 Go to Investigate profiling data step   

1.2.2 After startup

1.2.2.1 Launch profiler   
1.2.2.2 Launch BridgePoint   
1.2.2.3 Connect profiler to BridgePoint process   
1.2.2.4 Right before UI interaction begin profiling (example moving a shape)   
1.2.2.5 Once the UI interaction is complete pause profiling   
1.2.2.6 Go to Investigate profiling data step   
 
1.2.3 Regression testing

1.2.3.1 For any of the above profiling procedures after running them save a snapshot   
1.2.3.2 Do this for the current and previous build(s)   
1.2.3.3 For each build export a snapshot and store it on the amazon s3 server   
1.2.3.3 Compare the current and previous snapshots taking note of the differences, specifically those that show CPU time increase   
  
1.2.4 Investigate profiling data

1.2.4.1 Check the CPU usage by thread   
1.2.4.2 Navigate to the most CPU consuming calls   
1.2.4.3 Compare the data with the code at that point (in BridgePoint)   
1.2.4.4 Look mostly for calls that do not occur many times but take a good amount of time   
1.2.4.5 Also consider calls that occur many times and take time, seeing if they could possibly be reduced   
1.2.4.6 Report findings and pass for resolution determination   

1.3 Filtering

1.3.1 In most cases it is helpful to filter profiling data against only classes in the org.xtuml.* packages, if a problem is not found doing this then extend the filter to include more packages.   

Include in these steps:
    • org.eclipse.* (depending on issues filter specific packages if possible, for instance org.eclipse.ui.*)
    • java.util.*
    • java.*
