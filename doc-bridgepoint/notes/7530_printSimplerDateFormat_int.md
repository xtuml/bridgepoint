---

This work is licensed under the Creative Commons CC0 License

---

# Print out a simpler date format when using TIM::LogDate
### xtUML Project Implementation Note

 
1. Abstract
-----------
The GregorianCalendar object's toString() method produces HUGE amounts of 
difficult-to-decipher information. It should be changed, in the Java, to use   
the SimpleDateFormat class method


2. Document References
----------------------
[1] Issues 246, https://github.com/xtuml/internal/issues/246  
[2] Defect:dts0101038829, "Print out a simpler date format when using TIM::LogDate"  

3. Background
-------------
 

4. Requirements
---------------
4.1  Print out a simpler date format when using LOG::LogDate


5. Work Required
----------------



6. Implementation Comments
--------------------------
6.1 The test implmentation is moved from the core test suite to debug ui test suite because of the dependencies on  
    Debug ui testing utilities
 
7. Unit Test
------------
7.1 Run Debug verifier test The added test named DateLoggingTests



8. Code Changes
---------------
Branch name: < rdmn7530_git246_UpdateBranch_a >

<pre>
org.xtuml.bp.core [bridgepoint rdmn7530_git246_UpdateBranch_a]/src/lib/
    LOG.java

org.xtuml.bp.debug.ui.test [bridgepoint rdmn7530_git246_UpdateBranch_a]/src/
    VerifierTestSuite.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7530_git246_UpdateBranch_a]/src/
    org/xtuml/bp/debug/test/DateLoggingTests.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7530_git246_UpdateBranch_a]/src/
    org/xtuml/bp/debug/test/VerifierUDTAsUDTInitializationTests.java


</pre>



Branch name: < rdmn7530_git246_UpdateBranch >

<pre>
 > LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/
    > .externalToolBuilders/Model Compiler.launch
> LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/models/
    LogDateFormat/LogDateFormat.xtuml
> LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/models/
    LogDateFormat/External Entities/External Entities.xtuml
> LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/models/
    LogDateFormat/root/root.xtuml
> LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/models/
    LogDateFormat/root/Container/Container.xtuml
> LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/models/
    LogDateFormat/root/Container/leaf/leaf.xtuml
> LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/.cproject
> LogDateFormat [models origin/rdmn7530_git246_UpdateBranch 87bb06b]/> .project



</pre>

End
---

