---

This work is licensed under the Creative Commons CC0 License

---

# Address failures in JUnit tests
### xtUML Project Implementation Note


### 1. Abstract

Several test suites are failing on the build server.  This issue addresses some of these failures.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10030](https://support.onefact.net/issues/10030) Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #10035](https://support.onefact.net/issues/10035) Fix DocGen test on server    
<a id="2.3"></a>2.3 [BridgePoint DEI #10034](https://support.onefact.net/issues/10034) Smoke test      
<a id="2.4"></a>2.4 [BridgePoint DEI #9939](https://support.onefact.net/issues/9939) Pasting a class in a package does not update class number        
<a id="2.5"></a>2.5 [BridgePoint DEI #1447](https://support.onefact.net/issues/1447) Fix Canvas JUnit tests        
<a id="2.6"></a>2.6 [BridgePoint DEI #8568](https://support.onefact.net/issues/8568) Revisit Canvas CCP tests   
<a id="2.7"></a>2.7 [BridgePoint DEI #9947](https://support.onefact.net/issues/9947) Type ordering   

### 3. Background

JUnits run on the build server often.  The xtUML dev team desires these tests to be as clean
as possible so newly introduced failures are easy to spot.  We need to investigate the current 
failures and clean things up.  

### 4. Requirements

4.1 Fix tests that can be fixed and raise issues for tests that will be fixed separately.    

### 5. Work Required

5.1  Issue [2.4] added a feature to renumber classes during paste.  The expected results in the core test class
`CopyPasteTests` had a few cases where they had not been updated to account for this new behavior.  The expected 
results are updated to use new class numbers rather than duplicate/existing class numbers for pasted classes.    

5.2  Disable `DocGenTest` in the `Core - System Level Tests` suite because it isn't running on the server.  It 
works ok locally but on the server we get an error about `techpub.css` not found.  A quick investigation did 
not turn up an immediate solution.  Raised [2.2] to track down the problem.  The manual "smoke test" [2.3] runs 
document creation as a check.   

5.3  The `CombineSplitReferentialsTest` was broken.  The `performTest()` function had accidentally left the flag
to generate expected results set to true.  This caused the test to always re-create results rather than using the 
expected results to compare with actual results.  The test is fixed to properly compare.  

5.4  The "Cut" tests in the UI Canvas CCP test suite were disabled with issue 8587 and never put back into
place.  This work re-enables theses tests and fixes the test scenarios that test the feature to work properly.  Expected
results are updated accordingly.  

5.5  UI Canvas test suite 1 included a commented out test that is no longer valid, the test class doesn't exist.  The
commented out line is removed.  

5.6  UI Canvas test suite 3 had two test classes that were commented out due to issue 9505.  That issue is now 
fixed.  These tests were re-enabled  and found to work properly.  So the are left enabled.  

5.7  Unnecessary TODO comments are removed in several places along with CVS-style header blocks in files touched 
by this work. Additionally, import statements were cleaned up in modified files.   

5.8  In `CanvasCopyPasteTests` we re-enabled calls to validate expected results in some places that were commented out.  We
also re-enabled and updated and undo/redo test here.  

5.9  During the course of this work we noticed several opportunities to refactor common functionality up into the
`CanvasTest` class.  

5.10  Issue [2.5] disabled two tests in the `ConnectsAsAnchorsTest` class.  This was seven years ago.  We removed 
these 2 commented out tests.  

5.11 One test was failing in the Xtext MASL tests. The failure was related to a regression test which loaded and
parsed all MASL models in  the `models/masl/test/` directory. As part of the fix for issue #9947 [2.7], three models
were introduced into the `models/masl/test/` directory which contain MASL parse errors. Two of these intentionally
contain errors. `9947_typeorder2` was fixed to resolve the errors. `malformed_9947_typeorder1` and `malformed_9947_typeorder3`
were moved to `models/masl/malformed/`. `all_tests` was updated with their new location so the MASL round trip
regression test still works. Taking this action resolved the test failure in the Xtext test suite. From now on, we
will take the position that any MASL model in `models/masl/test/` shall be a valid MASL model.  

5.11.1 22 new launch configurations were added to BridgePoint. There are 11 Xtext MASL test suites. A launch configuration
was added for each suite for both OSX and linux.  

### 6. Implementation Comments

6.1  During the course of this work we noticed that the Canvas CCP tests need some work in the area of the
`ModelRecreationTestSuite` and `CanvasStateMachineCopyPasteTests`.  A search of redmine turned up issue [2.6] that
was raised previously to address exactly this.  That issue remains open.     

### 7. Unit Test

7.1  For test suites that have been touched, run them locally to verify success  
7.1.1  Core - System Level Tests 
7.1.2  Core Test 2
7.1.3  UI Canvas CCP Test
7.1.4  UI Canvas Suite 1
7.1.5  UI Canvas Suite 3

7.2  Run build with test on the server for dev branch `keithbrown/10030_test_update` and verify success
in the modified suites      

### 8. User Documentation

None.  

### 9. Code Changes

__Fork/Repository: keithbrown/bridgepoint__     
__Branch: 10030_test_update__   

<pre>
 doc-bridgepoint/notes/10030_test_update_int.md
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL DeclarationTypeProviderTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL DeclarationTypeProviderTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL ExampleModelsIntegrationTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL ExampleModelsIntegrationTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL LexerTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL LexerTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL LibraryTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL LibraryTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL LinkingTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL LinkingTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL PrimitiveTypesTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL PrimitiveTypesTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL SyntacticPredicateTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL SyntacticPredicateTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL TypeConformanceTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL TypeConformanceTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL TypeProviderTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL TypeProviderTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL ValidatorTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL ValidatorTest.launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL ValueConverterTest (OSX).launch
 doc-bridgepoint/process/templates/launch_configs/Xtext MASL ValueConverterTest.launch
</pre>

__Fork/Repository: keithbrown/bptest__     
__Branch: 10030_test_update__   

<pre>
 src/org.xtuml.bp.core.test/src/SystemLevelGlobalsTestSuite.java
 src/org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/CombineSplitReferentialsTestGenerics.java
 /org.xtuml.bp.io.mdl.test/src/org/xtuml/bp/io/mdl/test/ImportPasteElementsWithIPRTest.java
 src/org.xtuml.bp.ui.canvas.test/expected_results/CopyPasteTests_3/CopyPasteTests_3-112
 src/org.xtuml.bp.ui.canvas.test/expected_results/CopyPasteTests_4/CopyPasteTests_4-112
 src/org.xtuml.bp.ui.canvas.test/expected_results/CopyPasteTests_5/CopyPasteTests_5-112
 src/org.xtuml.bp.ui.canvas.test/expected_results/CopyTests_1/CopyTests_1-112
 src/org.xtuml.bp.ui.canvas.test/expected_results/CutTests_1/CutTests_1-112
 src/org.xtuml.bp.ui.canvas.test/expected_results/CutTests_2/CutTests_2-112
 src/org.xtuml.bp.ui.canvas.test/expected_results/CutTests_3/CutTests_3-112
 src/org.xtuml.bp.ui.canvas.test/src/CanvasCCPGlobalsTestSuite.java
 src/org.xtuml.bp.ui.canvas.test/src/GlobalsCanvasSuite1.java
 src/org.xtuml.bp.ui.canvas.test/src/GlobalsCanvasSuite3.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasCCPGlobalsTest.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasCopyPasteTests.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasCopyTests.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasCutTests.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasTest.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/ConnectorsAsAnchorsTest.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/GlobalsCanvasTestSuite1.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/GlobalsCanvasTestSuite2.java
 src/org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/ModelRecreationTests.java
</pre>

__Fork/Repository: leviathan747/models__     
__Branch: 10030_test_update__   

<pre>
 masl/{test => malformed}/malformed_9947_typeorder1/typeorder.int
 masl/{test => malformed}/malformed_9947_typeorder1/typeorder.mod
 masl/{test => malformed}/malformed_9947_typeorder3/typeorder.int
 masl/{test => malformed}/malformed_9947_typeorder3/typeorder.mod
 masl/test/9947_typeorder2/MyDom.int
 masl/test/9947_typeorder2/typeorder.mod
 masl/test/all_tests
</pre>

### End

