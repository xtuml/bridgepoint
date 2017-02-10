8.1 Analysis of all profiling sessions

Looking through the profiling data provided by the sessions outlined in this document, it is determined that the most relevant data is present in the inital load regression data [7.1].  The other session data is valuable but shows that there are no significant regressions in performance.  In certain situations the other profiling cases could show worse times, but they would be related to such cases triggering load.

8.1.1 Analysis of intial load

Looking at the regression results in [7.1] shows a significant increase in CPU time.  The first and most important data point is not the time, but instead the increase in number of invocations for many of the hot spots captured.  The initial load test of course is exercising the BridgePoint load code.  Seeing the increase in invocations across multiple methods is not suprising.  The important thing to notice here is that something was changed to trigger loading invocations more times than previously.  The most important thing to look at in this data is the invocations of the processStatement method, it has increased by 9,772.  This would mean that the tool is either reloading files now during initial load or global data has been added containing 9,772 insert statements.

