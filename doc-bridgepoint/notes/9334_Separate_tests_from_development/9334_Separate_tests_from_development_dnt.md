---

This work is licensed under the Creative Commons CC0 License

---

# Support both a development repository with a bptest repository 
### xtUML Project Design Note

1. Abstract
-----------
This note describes the changes required to support a development workspace with or without the bp test projects included.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9334](https://support.onefact.net/issues/9334) Perform unit testing from the xtuml/bptest repository  
<a id="2.2"></a>2.2 [BridgePoint DEI #9088](https://support.onefact.net/issues/9088) Create a branch from which unit tests may be built and run  
<a id="2.3"></a>2.3 [BridgePoint DEI #9029](https://support.onefact.net/issues/9029) Fix Unit test build problems caused by moving test plugins (and data) to their own repository  
<a id="2.4"></a>2.4 [HOWTO-run-bridgepoint-unit-tests](https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md)  

3. Background
-------------
A bit of work was done prior to this issue which almost got it to the point where tests would run.  However, that work did not attempt to get the tests to a passing state in the 6.2.2 release.  This note also covers that work.

4. Requirements
---------------
4.1 Developers shall be able to build BridgePoint without unit test plugins present.  
4.1.1 The Developer Getting Started Guide shall be updated, if needed.   
4.1.1.1 A focus on keeping instruction simple shall be made.   
4.1.1.2 A goal of no change to the Developer Getting Started Guide is the optimal solution.  

4.2 Unit tests will be imported into the the developer’s workspace from the bptest repository.  
4.2.1 No copy/link of projects into another repository.  

4.3 Developers can build BridgePoint with unit test plugins present and run the BridgePoint unit tests in their development environment.  
4.3.1 The HOWTO Run BridgePoint Unit Tests document shall be updated.  
4.3.1.1 A focus on keeping instruction simple shall be made.  
4.3.2 Developers shall be able to debug unit tests in their development environments.  

4.5 Unit tests plugins shall not be required to be present to build BridegePoint  

4.6 The bptest repository shall be updated to capture the work performed in the bptest branch during the BridgePoint 6.2.0 release cycle.  

5. Analysis
-----------
5.1 The build infrastructure is required to build both the development source and test source separately.  Building source only has always been present, but building tests will not work with the separation of MC-Java.  The work done in [2.3] addressed this issue by using the XTUML_DEVELOPMENT_REPOSITORY variable.  This variable is already set by the [2.4] instructions.  

5.2 Users shall import the test plug-ins from the bptest repository if desiring testing.  No copying and linking shall be required to include the test plug-ins.  

5.3 To run the tests a requirement is to use models from two different repository locations, without hard coded paths.  What is required is two variables to locate each of the supported repositories, bridgepoint and bptest.  These variables already exist and are part of the setup found in [2.4].  When looking for test models found in the development source XTUML_DEVELOPMENT_REPOSITORY shall be used.  

5.4 The test results captured for release 6.2 where created by using a branch of the bridgepoint repository.  This branch was for the issue [4.2].  That branch shall be used to include all changes into the separated repositories.  

6. Design
---------
6.1 Nothing is required to build the source without the unit tests present.  To build the test source the work from 9029 is merged into the bptest repository branch for this work.  

6.2 The howto for running tests [2.4] is updated by the work in [2.3] to include a step for importing the test plugins.  This is a simple import of all test plug-ins in the bptest repository.  

6.3 This is already supported, but stated here for clarity.  

6.4 Merge changes from testing branch used for [2.2]. 

In order to merge these changes into the new bptest branch the testing branch was checked out.  Then two folders where created one with the source projects from the testing branch and one from the new bptest repository.  These folders were compared with each other, merging changes into the one with projects from the bptest repository.  The projects with the merged changes were then copied over the actual git repository projects.  

6.5 Support absolute and relative paths  

The changes in 9029 allowed for a variable to be set, mcj_path.  This veriable is now defaulting to an absolute path.  MC-Java was modified to account for this in generate.schema by removing the .. as the target is run in the sql folder.  This is fine except that the variable only works in the UI, the build server POMs require a relavant path.  In order to support both approaches build.xml is changed to check for the existence of the required ooa_schema.sql file and call two new targets, one the original with .. and the other without.  The logic is that the file will only exist in one of the two locations.  The new targets are both called but only the one for which a variable was set to true depending on the existence of the file will run.  

6.6 Testing showed an issue with C_I instances and refreshing.  Research found that previous work had removed the coloring for C_I as a component root.  These removed changes were added back.  

6.7 Prevent NPE during tests where an event is received after an element has been deleted and the file is being persisted.  The delete event for a graphical element is received after persisting starts for its host file.  Persistence is calling Getpath on the elements in memory.  HierarchyUtil.Getpath is modified to check an element as being orphaned before calling the elements getName implementation.  

6.8 In ComponentTransactionListener when reloading due to action elements not being persisted send a reload event so that editors may be notified.  

6.9 Some refresh events are not seen with the new reload code for non-persisted action files during transaction end.  The reason is that the load is within a transaction listener, where the load events are not sent out.  Open editors require events sent during file load.  In GraphicalEditor the cached Model_c instance is not updated due to this.  GraphicalEditor.refresh() is modified to add a simple comparison against the cached Model_c and the one returned from the current instance list.  If they are different the cached value is updated.  

6.10 Refresh the title for graphical editors during reloads.  It was noticed that they were not being refreshed causing some tests to fail.  

6.11 Test result updates

The new test results from this work are from two things.  One of which is a change in this work to only output a certain number of characters for the graphical string results.  This was done to limit the number of differences just from running with different windowing systems/themes.  The other change is related to the change to how referentials are handled, the output on the diagram no longer shows the relationship number in these cases.  All other changes are related to the merged in changes from the test branch used for [2.2].  

6.12 Other differences come from issues 9029 [2.3] and 7570 [2.4].  

7. Design Comments
------------------
7.1 CLI prebuild  

During testing with building from the command line it was noted that the prebuild step for core.test was not producing the appropriate sql files.  In mc.java.source.ExportBuilder we override exportSystem() in order to make use of project settings that specify package roots to use for sql production.  At some point another exportSystem with a new parameter (doNotParse) was added.  This is the method being called from the CLI code.  In order to make use of the package root settings this method is also overridden in ExportBuilder, passing on the call to the existing exportSystem method.  

8. User Documentation
---------------------

9. Unit Test
------------
9.1 Unit test results shall match or be better than those from the 6.2 release.  

End
---

