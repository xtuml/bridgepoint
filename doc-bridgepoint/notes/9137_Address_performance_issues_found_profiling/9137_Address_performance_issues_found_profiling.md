---

This work is licensed under the Creative Commons CC0 License

---

# Improve unit tests for 6.0
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes changes required to achieve better unit testing results for 6.0 with a goal to get all passing.   

2. Document References
----------------------  
<a id="2.1"></a>2.1 [BridgePoint DEI #9147](https://support.onefact.net/issues/9147) Address performance issues found profiling v5.2.2 and v6.0.0  
<a id="2.2"></a>2.1 [BridgePoint DEI #9147](https://support.onefact.net/issues/9111) Profile BridgePoint using procedural techniques described by the engineering process     

3. Background
-------------   
Profiling was performed against v5.2 and v6.0 of the tool and the results need to be considered.   

4. Requirements
---------------
4.1 Any major performance issues shall be addressed.   

5. Work Required
----------------
5.1 Fix performance issue when drawing a canvas   

During profiling for [2.2] is was noted that the only performance difference was the fact that we now force load.   Another area was uncovered that greatly reduces performance when a graphical element has a compartment with many entries.  The reason is that the text extent for all entries is being calculated regardless of height extent.  This a heavy operation and should be minimized.  In Shape.draw() the logic to prevent painting data which extends the visible bounds is extended to also prevent calculating the text extent.   

6. Implementation Comments
--------------------------

7. Unit Test
------------

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 9137_Address_performance_issues_found_profiling

<pre>

doc-bridgepoint/notes/9137_Address_performance_issues_found_profiling/9137_Address_performance_issues_found_profiling.md

org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Graphical Data/Shape/Shape.xtuml

</pre>

End
---

