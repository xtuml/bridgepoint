---

This work is licensed under the Creative Commons CC0 License

---

# Support MASL Project projects in masl_round_trip
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work performed to add support handling MASL Projects in
the ```masl_round_trip``` test script.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9211](https://support.onefact.net/issues/9211) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #9013](https://support.onefact.net/issues/9013) Using CLI single-file import on MASL fails to autoreconcile graphics        

3. Background
-------------
The current implementation of ```masl_round_trip``` only supports testing of 
MASL domains.  We wish to support MASL Projects as well.   

4. Requirements
---------------
4.1 Given a MASL Project and domain(s), support convert/import/export/diff of
  the project data.    

5. Work Required
----------------
5.1 ```masl_round_trip```     
5.1.1  Create a new temporary workspace that contains 5 domains (d1, d2, etc) and
  a project (p1).  The project has IPRs turned on.  
5.1.2  The script is modified to import domains into the numbered projects and if
  a MASL project is specified it is imported into p1.   
5.1.3  If a project is specified, don't export and diff the domains, just the project.  
5.1.4  Capture the run of the import into a file in the ```/tmp``` folder  
5.1.5  Store the ```left.masldiff/``` and ```right.masldiff/``` into the output folder   

5.2 BridgePoint UI   
5.2.1  We noted that when running the project import, the component references were
  not being assigned to the components even though they were in the workspace.  It
  turned out that the problem was the tool ran the project import then exited immediately
  and did not give the listeners and post-import processing time to run.  This is the
  same reason we saw graphics not being reconciled on CLI Import.  The issue is that
  these pending jobs needed to be serviced before exiting.   
5.2.2  We added a call inside ```allowJobCompletion()``` to make sure that the job queue
  is being emptied and pending jobs are woken up and serviced.  We then call 
  ```allowJobCompletion()``` at the end of the import before exiting.   
  
6. Implementation Comments
--------------------------
None.  

7. Unit Test
------------
7.1 masl_round_trip -d SAC_OOA -p SAC_PROC -o /tmp/sactest  
  * Both projects are imported into the temporary workspace  
  * MASL export is done on the MASL Project only  
  * SAC_PROC is diff'd.  As of this writing there are expected diffs on public/private
  terminators and the missing INI and SOA domains  
  
7.2 Run the full ```regression_test``` and make sure no errors are introduced.    

7.3 Open the workspace ```/tmp/importwork```, click around in the projects and 
  open canvases, it should succeed.  
  
8. User Documentation
---------------------
None.  

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint  
Branch: 9211_project_round_trip  

<pre>
 doc-bridgepoint/notes/9211_project_round_trip.md   | 73 ++++++++++++++++++++++
 .../org/xtuml/bp/cli/ImportWorkbenchAdvisor.java   |  7 ++-
 .../xtuml/bp/utilities/ui/ProjectUtilities.java    |  1 +
</pre>

Fork/Repository: keithbrown/bridgepoint  
Branch: 9211_project_round_trip  

<pre>
 bin/importworkspace.zip | Bin 126194 -> 165115 bytes
 bin/masl_round_trip     | 133 +++++++++++++++++++++++++++++++++++++-----------
</pre>

Fork/Repository: keithbrown/models  
Branch: 9211_project_round_trip  

<pre>
 masl/test/regression_test | 1 +
</pre>

End
---

