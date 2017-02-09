---

This work is licensed under the Creative Commons CC0 License

---

# Research best tool for profiling BridgePoint and define profiling procedure 
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the results from evaluation of commonly used java profilers against the BridgePoint tool.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9110](https://support.onefact.net/issues/9110) Research best tool for profiling BridgePoint and define profiling procedure  
<a id="2.2"></a>2.2 [BridgePoint Profiler Research](https://docs.google.com/spreadsheets/d/1PILhtDHFWBIWIWv07Ik1mYs_8G_QLoGVgwZoQWTWcaQ/edit#gid=0) Initial research into profiling tools   
<a id="2.3"></a>2.3 [BridgePoint Profiler](https://docs.google.com/document/d/1PdtBOeROEGpihp4jrgOM8JSUT57Ymsr9E8G7H2qATZ0/edit) Document describing tools further along with environment settings  
<a id="2.4"></a>2.4 [jvisualvm](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) jvisualvm is shipped with the Oracle JDK 

3. Background
-------------
3.1 Performance issues with BridgePoint

Notable performance issues have shown within the last year.  The tool needs to be profiled in order to determine the cause.  BridgePoint has never had a dedicated tool for profiling.  We need to determine which tool is best to suit the needs of BridgePoint development.

YourKit was suggested by EclipseSource as it has worked for them in the past.   

4. Requirements
---------------
4.1 Evaluate and conclude which tool best fits BridgePoint for profiling  

5. Work Required
----------------
None, below you will find evaluation data collected.

6. Implementation Comments
--------------------------

7. Unit Test
------------

8. User Documentation
---------------------
None

Profiler Evaluation
-------------------

Ran procedure on all profilers, using latest release.  Load was tested as the problem area for evaluation.

jvisualvm

Installation: Comes with any oracle jdk

Steps to run are as follows:

- Launch jvisualvm
- Launch BridgePoint
- Double click the Local > Local Application (pid #) in the Applications view of jvisualvm
- In the window opened select the Profiler tab
- Click the CPU button in the Profile: field
- Start loading BridgePoint (should have been stopped at the choose workspace dialog)
- Once the tool is ready for user interaction (fully loaded) click the Stop button in the Profile: field
- In the window opened examine the hot spot methods
- Note those that take the most time

The results given here are at a higher level than others.  The data is enough to determine in the code that is likely causing any performance issues.

jvisualvm supports snapshots, which are also comparable.  This is a good feature specially when looking for regression issues.

Conclusion: This seems to be a tool that is enough, nothing fancy but will point a developer in the right direction.

IBM Health Center

Installation: Update site found here: http://public.dhe.ibm.com/ibmdl/export/pub/software/websphere/runtimes/tools/healthcenter/

IBM Health Center does not allow attaching to a process.  It requires a build and debug session.  The UI is none intuitive.  Given this the conclusion is to not look further into this profiler unless the others do not provide enough.  Even in such a case this one seems like there would be too much of a learning curve.

JProfiler

Installation: Has an OS based installer, easy to use and integrate into Eclipse

Filtering is supported.  You can filter classes of type and compact filter classes of type (this is only going to show the first invocation).

Steps to run are as follows:

- Launch jProfiler
- Launch BridgePoint
- In jProfiler click the Attach button in the toolbar
- Choose the pid # for Bridgepoint and click Open
- In the window opened click the Edit button for Profiling settings
- From here you can chose many options, filtering is under the Filter Settings category (do nothing here)
- Click the cancel button then choose the OK button
- Click the CPU Views category
- Now click the Press <img> to record CPU data button
- Start loading BridgePoint (should have been stopped at the choose workspace dialog)
- Once the tool is ready for user interaction (fully loaded) click the Stop CPU button
- In the window opened examine the hot spot methods
- Note those that take the most time

The results here are desirable as you can nest down to the very last call.  Note that a lot of data is present in a nested loop, which occurs during BridgePoint load.  This brings the tool to a halt if the filtering data is not properly configured.  There are ways to expand to x amount of levels which is helpful in navigation.

This one also supports snapshots and comparisons.  Again good for regression issues.

Conclusion: The tool has a lot to configure, but is easy to use.  At a minimum filtering org.xtuml and all others as compact is enough.  The profile minimizes the amount of extra digging that is required in jvisualvm.  With the extra data provided it may be easier in some situations for locating performance issues.  Considering that with the right configuration you can achieve minimal results as jvisualvm does this tool is preferred.  Caveats are there will be a learning curve for configuration and use, but it can start with minimal settings and grow as needed.  The other caveat is the price.

YourKit

Installation: Has an OS based installer, easy to use and integrate into Eclipse.  Note for Mac is does not install into the Applications folder, but can be run from wherever it is unzipped.

Filtering is supported. You can compact filter classes of type (this is only going to show the first invocation).

Steps to run are as follows:

- Launch YourKit
- Launch BridgePoint and choose a non-bridgepoint perspective (required due to a crash when profiling from workspace location choosing)
- Restart BridgePoint
- In YourKit click the underlined bridge point item under Monitor Lock Applications
- In the toolbar choose Settings > Filters… to configure filters (if filtering is required)
- Hover the Start CPU Profiling button at the top and choose the Tracing option
- Click the Start CPU Profiling button again
- Start loading BridgePoint by opening the xtUML Perspective
- Once the tool is ready for user interaction (fully loaded) click the Stop CPU button
- In the window opened examine the CPU runtimes collected
- Note those that take the most time

This one also supports snapshots and comparisons.  Again good for regression issues.

Conclusion: This tool can be configured heavily but is not as intuitive as JProfiler.  The same data is provided as in JProfiler but the UI is not as elegant.

Suggestion:

Use JProfiler.  This profiler provides more data than jvisualvm, but either would work.  JProfiler is preferred just from a UI/intuitive standpoint over YourKit.  Easy to use quickly and all the same data is present.

After determining this suggestion this website was found.  The suggestion is in agreement:

https://blog.oio.de/2014/03/07/java-profilers-a-short-comparison-between-jprofiler-yourkit-and-javas-visualvm/

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



9. Code Changes
---------------
Branch: https://github.com/travislondon/bridgepoint/tree/9110_Research_bp_profiling_tool 

<pre>

doc-bridgepoint/notes/9110_Research_bp_profiling_tool/9110_Research_bp_profiling_tool.int.md

</pre>

End
---

