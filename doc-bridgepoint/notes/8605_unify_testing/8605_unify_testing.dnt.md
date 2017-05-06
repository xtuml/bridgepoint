---

This work is licensed under the Creative Commons CC0 License

---

# Support unit testing across multiple platforms
### xtUML Project Design Note


1. Abstract
-----------
This note describes changes which allow for testing to occur across multiple platforms.  

These platforms include UI (Mac/Linux)/CLI(Mac/Linux) and build servers.  While CLI and the build server should be identical, it is currently not true due to the use of vncserver on the build server.6.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8605](https://support.onefact.net/issues/8605) Complete build server unit test integration   
<a id="2.2"></a>2.2 [BridgePoint DEI #9505](https://support.onefact.net/issues/9505) Disable remaining failing tests.    
<a id="2.3"></a>2.3 [BridgePoint DEI #9209](https://support.onefact.net/issues/9209) Rework compare test infrastructure and address any real issues found  
<a id="2.4"></a>2.4 [BridgePoint DEI #9166](https://support.onefact.net/issues/9166) Failures in io.mdl.tests  	
<a id="2.5"></a>2.5 [BridgePoint DEI #8612](https://support.onefact.net/issues/8612) The Compare test suite has failures  
<a id="2.6"></a>2.6 [BridgePoint DEI #8614](https://support.onefact.net/issues/8614) The Canvas 2 test suite has failures  
<a id="2.7"></a>2.7 [BridgePoint DEI #8613](https://support.onefact.net/issues/8613) The Canvas 1 test suite has failures  
<a id="2.8"></a>2.7 [BridgePoint DEI #8611](https://support.onefact.net/issues/8611) The Core Setup and Core Existing WS test suites has failures  
<a id="2.9"></a>2.9 [BridgePoint DEI #8610](https://support.onefact.net/issues/8610) The Core 2 unit test suite has failures  
<a id="2.10"></a>2.10 [BridgePoint DEI #8609](https://support.onefact.net/issues/8609) The Verifier 2 unit test suite has failures  
<a id="2.11"></a>2.11 [BridgePoint DEI #8608](https://support.onefact.net/issues/8608) The Verifier 1 unit test suite has failures  
<a id="2.12"></a>2.12 [BridgePoint DEI #8607](https://support.onefact.net/issues/8607) The IO MDL PKG unit test suite is failing  
<a id="2.13"></a>2.13 [BridgePoint DEI #8593](https://support.onefact.net/issues/8593) bp.io.mdl.test failures  
<a id="2.14"></a>2.14 [BridgePoint DEI #7812](https://support.onefact.net/issues/7812) Clean up BridgePoint JUnit tests  
<a id="2.15"></a>2.15 [BridgePoint DEI #7659](https://support.onefact.net/issues/7659) failure for manual test testing Constant data and Array Attribute in SE & Spotlight views #1562		

3. Background
-------------
Recently a build server has been implemented.  Currently this is a Jenkins server.  More importantly we have moved to building the xtuml code using maven.  This provides a standard way to build without the requirement of a UI as well as puts us in the direction of having continuous integration builds.

This work allows tests to run in all platforms.  Issue [2.2] describes some tests that were disabled to allow a fully successful test run./ on the build server.  In all cases each test block would run individually with no issue.  

4. Requirements
---------------
4.1 The BridgePoint unit tests shall report 100% passing (consistently)   
4.2. Unused expected results shall be removed  
4.3. There shall be only 1 set of test results (no environment specific results)  
4.4. Unit tests shall run with the same result on linux and mac     
4.5. The number of passing unit tests shall be greater than or equal the 6.2.4 result  
4.6. Redmine Issues shall be raised for any test/suites that are removed  
4.6.1 Any such issue shall use Redmine issue 8605 as a parent.   
4.7 Enhance the HOWTO Run BridgePoint Unit Tests document to describe how to add a test suite.   (https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md)  
4.8. Work with the One Fact team to improve the build server to automation to assure the build server can:  
4.8.1 Launch build with or without unit testing from a trigger (no need to log into AWS)   
4.8.2 Optionally terminate the instance when complete  


5. Analysis
-----------
5.1 Handling UI Interaction  

5.1.1 The BridgePoint Unit tests rely on manipulating the UI.  We currently do this by scheduling a timed event.  This is slow and relies on getting lucky with the timing.  Knowing that we will have a UI dialog shown after a test action we should be able to simply close the last opened UI element.  

5.1.2 Another issue that is more relevant when running on the server is that our event dispatching is not handling everything.  Some events, like data changes which occur on a resource listener, were not being handled.  The dispatchEvents method of BaseTest should not require a timeout and should focus on all possible events.  

5.2 Unify graphical test results  

The current testing infrastructure relies a lot on graphical draw data to match.  This becomes a problem when switching between systems.  Previously, we had disabled positional data when running on linux.  The positional data will always be different (differences in DPI and UI themes).  The logic should test without any cropping allowing full text to be compared.  

5.2.1 Remove graphical results for OS specifics  

When we prevented the positional data for linux we created  a new set of expected results.  Any expected results that are not common shall be removed.  
 
5.3 Disable failing tests  

Some tests are still failing due to points 5.1.1 and 5.1.2.  These tests are small and run with a 100% success if by themselves.  These have been disabled according to [2.2].  

5.4 UUID generation  

In some tests we rely on unique ids that can be regenerated on each test run.  We do this by giving a seed to the java UUID class.  This seed however depends on the order of class loading and can cause issues between platforms as well as new model data.  The IdAssigner class shall be reworked such that it increments the given seed based on the class name hashcode.  

6. Design
---------
6.1 Handling UI interaction  

Dialog dismissal now occurs on its own thread.  The original calling classes now notify this thread that the next UI element opened shall be handled.  There are some cases that do not simply dismiss the UI element, but make modifications.  This is handled using Java 8 lambdas.  Below is an example:  

```java
	processShell(existingShells, shell -> {
	if (shell != null) {
		Dialog dialog = (Dialog) shell.getData();
		Control[] children = dialog.getShell().getChildren();
		for (int i = 0; i < children.length; i++) {
			Table table = findTable(children);
			if (table != null) {
			...										;
										}
```   

The ShellProcessor is an interface with one method, processShell.  It requires the current UI shells to determine which new shell is created due to the test action.  

6.1.2 Handle all event dispatching  

The same approach as with shell handling was taken with event dispatching.  There is now a thread that gets notified which will process display events as well as file changes.  It handles file changes by registering its own resource listener.  This listener will notify the thread that it is done when a timeout with no resource events occur.  This timeout is currently set to 300 milliseconds.  

6.2 Unify graphical test results  

There are two parts to this work.  First the graphical code is modified to always include full text and disable any sort of cropping.  Positional data is left as it was, meaning we still do not compare it.  

6.2.1  Remove graphical results for os specifics  

All code that tested the OS has been removed.  The only logic left that could be considered OS specific is related to comparing text results and the newline separator.  However, this logic was changed to use the Java property for newline which should always return whats required.  

All tests now look under expected_results for comparison data.  

6.3  Disable failing tests  

There were many progressions with this work.  Most times the UI results were 100% but the server tests failed.  When these failures were addressed the UI would start failing and visa versa.  We got to a point where there were just a few failures on the server.  We decided to disable those failing tests.  Those have beem commented out pointing at issue [2.2].  We could remove them but they generally pass when run alone so I have left them in.  

We have the ability to run tests in three ways, UI CLI and Server.  From the results during this work we should consider always running them in one way.  

6.4 UUID generation  

Currently every class that has a unique identifier also has an IdAssigner class initiated.  We use this class to generate unique ids, and further use it regenerate the same unique id for tests.  This is done by issuing a seed to the assigner.  This see is usually just a random number and works as long as the class loading occurs in the same order as the previous run.  Adding a new class or running tests on a different platform does not guarantee this ordering.  In order to guarantee the same result the class name's hashcode is now used as the incremental value to the seed counter.  

6.5 Addressed pre-existing test issues  

The following issues have been addressed as part of this task.  Most importantly is that verifier and model compare now run successfully.  

[2.3]
[2.4]
[2.5]
[2.6]
[2.7]
[2.8]
[2.9]
[2.10]
[2.11]
[2.12]
[2.13]
[2.14]
[2.15]
[2.16]
[2.17]
[2.18]

7. Design Comments
------------------

8. User Documentation
---------------------

9. Unit Test
------------
_- Run all junit tests
_R All tests pass

End
---
