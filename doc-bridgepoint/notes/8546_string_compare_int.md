---

This work is licensed under the Creative Commons CC0 License

---

# Incorrect uses of == for string comparison
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes work performed to change from equality/non-equality 
operators in favor of String operations.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8546](https://support.onefact.net/issues/8546) Headline issue  

3. Background
-------------
While chasing problems with another issue, we uncovered that BridgePoint has 
quite a few incorrect string comparisons in the java code where the code is 
using '==' instead of '.equals()' to test for string data equality. A quick 
regexp search for '==\s*"' turned up around 35 hits where the code compares to a
static string.  There are also other places where the code is using '==' to compare with 
variables instead of static strings.   

Using '==' instead of '.equals()' is almost never what you want in Java for 
string compare, so these places need to be fixed.  Refer to this [Stackoverflow article](http://stackoverflow.com/questions/513832/how-do-i-compare-strings-in-java).   

4. Requirements
---------------
4.1  Replace uses of operators '==' an '!=' for string comparison with String 
  operations.  
  
5. Work Required
----------------
5.1 Search the BridgePoint Java code base using regexp and do the required 
  conversion.  
* '==\s*"' to '&lt;variable&gt;.equals(&lt;string&gt;)'
* '"\s*==' to '&lt;variable&gt;.equals(&lt;string&gt;)'
* '==\s*[A-Za-z]+' to '&lt;variable&gt;.equals(&lt;variable&gt;)' (see note 1 below)
* '!=\s*[A-Za-z]+' (see note 2)
* '!=\s*"' to '!&lt;variable&gt;.isEmpty()'

Notes:  
1) This search returns over 30,000 hits, the majority of which are '== null'.  
  When I exclude most generated files ('&lt;file&gt;_c.java') from the search it goes
  down to 8800 hits.  I browsed through these fairly quickly and found and fixed
  a few string variable comparisons.   
2) This search returns over 19,000 hits, even with the generated 
  ('&lt;file&gt;_c.java') files excluded.  A spot check of a number of files showed 
  all hits were '!= null' checks.  I did not do a more detailed look at all
  hits.   
   
6. Implementation Comments
--------------------------
6.1  Cleaned up unusued variables in 'src/installer/build_installer_bp.sh'  
6.2  Re-order tests in 'src/org.xtuml.bp.als.oal.test/src/OALGlobalsTestSuite_Generics.java',
  one of the tests had apparently been moved to the top for testing.   I moved
  it back to its original location.   
6.3  Fix test setup in 'bp.als.oal.test/src/org/xtuml/bp/als/oal/test/ParseErrorForEmptySynchronousMessagesTests.java'
  and 'src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/SupportConstantsViaIPRTests.java' 
  for JUnit4.  
6.4  Update the location to find the GPS Watch test model in ' src/org.xtuml.bp.test/src/org/xtuml/bp/test/common/TestingUtilities.java'  
  
7. Unit Test
------------
7.1  Run the Parse All JUnits, see it pass.  
7.2  The other JUnits will be run as part of 5.4.0 release testing.  

8. User Documentation
---------------------
None required.

9. Code Changes
---------------
Branch name: keithbrown/xtuml/bridgepoint 8546_string_compare

<pre>
installer/build_installer_bp.sh

org.xtuml.bp.als.oal.test/src/OALGlobalsTestSuite_Generics.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/
    ArrayBaseTest_Generics.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/ArrayBaseTest.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/
    ComponentSyntaxTest_Generics.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/
    ParseErrorForEmptySynchronousMessagesTests.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/
    SupportConstantsViaIPRTests.java
org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/TestSelect_Generics.java

org.xtuml.bp.cli/src/org/xtuml/bp/cli/BPCLIPreferences.java
org.xtuml.bp.cli/src/org/xtuml/bp/cli/BuildWorkbenchAdvisor.java

org.xtuml.bp.compare/src/org/xtuml/bp/compare/text/ModelClassTextGenerator.java

org.xtuml.bp.core/src/lib/T.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/ModelRoot.java
org.xtuml.bp.core/src/org/xtuml/bp/core/common/TraceLogger.java
org.xtuml.bp.core/src/org/xtuml/bp/core/relocatables/
    RelocatableTagCreationUtil.java
org.xtuml.bp.core/src/org/xtuml/bp/core/ui/BinaryFormalizeOnR_RELWizard.java

org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/
    ModelTransactionTestGenerics.java
org.xtuml.bp.core.test/src/org/xtuml/bp/core/test/SequenceTestsGenerics.java

org.xtuml.bp.debug.ui/src/org/xtuml/bp/debug/ui/model/BPValue.java
org.xtuml.bp.debug.ui/src/org/xtuml/bp/debug/ui/model/BPVariable.java

org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/ui/test/
    DebugUITestUtilities.java
org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/ui/test/execute/
    VerifierInterfaceExecutionTests.java

org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/CoreImport.java

org.xtuml.bp.model.compare/src/org/xtuml/bp/model/compare/contentmergeviewer/
    SynchronizedTreeViewer.java

org.xtuml.bp.test/src/org/xtuml/bp/test/common/TestingUtilities.java

org.xtuml.bp.ui.canvas.test/src/org/xtuml/bp/ui/canvas/test/CanvasTest.java

org.xtuml.bp.ui.explorer.test/src/org/xtuml/bp/ui/explorer/test/
    ExplorerTest.java

org.xtuml.bp.ui.properties/src/org/xtuml/bp/ui/properties/
    ChooserPropertyDescriptor.java

org.xtuml.bp.welcome/src/org/xtuml/bp/welcome/gettingstarted/
    SampleProjectGettingStartedAction.java



</pre>

End
---

