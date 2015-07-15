---

This work is licensed under the Creative Commons CC0 License

---

# Unable to relaunch when using inter-project referred EE
### xtUML Project Implementation Note

1. Abstract
-----------
When a project that uses a shared realized external entity by inter-project
is terminated in verifier. Java LinkingError exception is thrown when re-execute 
this model in verifier and invoke the shared external entity.  

2. Document References
----------------------
[1] [BridgePoint DEI #7744] (https://support.onefact.net/redmine/issues/7744)  
[2] [Design Note](https://github.com/nmohamad/bridgepoint/blob/master/doc-bridgepoint/notes/7744_RealizedClass/7744_RealizedClass.dnt.md)  

3. Background
-------------
See [2]

4. Requirements
---------------
See [2]

5. Work Required
----------------
5.1 In BPDebugTarget.terminate() and BPThread.resetClassLoader(), check for the
	Active executing targets, if there is no executing target, clear VM_c.vmclMap
	and BPClassLoader.definitions.
	
5.2 If the terminated project contains a defined realized class, print a 
	warning message if the class definition will not be deleted due to 
	remaining running projects in verifier.

6. Implementation Comments
--------------------------
None.

7. Unit Test
------------
7.1 See [2]  
7.2 Verifier Test Suite 1 & 2 shall pass.

8. Code Changes
---------------
Branch name: <7744_realized_relaunch_failure>  bridgepoint repo

<pre>

doc-bridgepoint [bridgepoint 7744_realized_relaunch_failure]/notes/
    7744_RealizedClass/7744_RealizedClass.dnt.md
doc-bridgepoint [bridgepoint 7744_realized_relaunch_failure]/notes/
    7744_RealizedClass/7744_RealizedClass.int.md
doc-bridgepoint [bridgepoint 7744_realized_relaunch_failure]/review-minutes/
    7744_RealizedClass.rvm.md

org.xtuml.bp.core [bridgepoint 7744_realized_relaunch_failure]/src/org/xtuml/
    bp/core/Vm_c.java

org.xtuml.bp.debug.ui [bridgepoint 7744_realized_relaunch_failure]/src/org/
    xtuml/bp/debug/ui/model/BPDebugTarget.java
org.xtuml.bp.debug.ui [bridgepoint 7744_realized_relaunch_failure]/src/org/
    xtuml/bp/debug/ui/model/BPThread.java

org.xtuml.bp.debug.ui.test [bridgepoint 7744_realized_relaunch_failure]/src/
    VerifierTestSuite2.java
org.xtuml.bp.debug.ui.test [bridgepoint 7744_realized_relaunch_failure]/src/
    org/xtuml/bp/debug/test/RealizedClassRelaunchTest.java
org.xtuml.bp.debug.ui.test [bridgepoint 7744_realized_relaunch_failure]/src/
    org/xtuml/bp/debug/ui/test/DebugUITestUtilities.java

</pre>

Branch name: <7744_realized_relaunch_failure>  models repo

<pre>

InterProjectRealizedClass [models 7744_realized_relaunch_failure]/
    .externalToolBuilders/Model Compiler.launch
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/.launches/
    OnlyInterProjectLauncher.launch
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/.settings/
    com.mentor.nucleus.bp.ui.project.preferences.prefs
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/gen/
    bridge.mark
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/gen/class.mark
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/gen/
    datatype.mark
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/gen/
    domain.mark
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/gen/event.mark
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/gen/
    system.mark
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/gen/
    code_generation/InterProjectRealizedClass.sql
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/models/
    InterProjectRealizedClass/InterProjectRealizedClass.xtuml
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/models/
    InterProjectRealizedClass/Top Package/Top Package.xtuml
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/models/
    InterProjectRealizedClass/Top Package/TestComponent/TestComponent.xtuml
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/models/
    InterProjectRealizedClass/Top Package/TestComponent/Testing Package/
    Testing Package.xtuml
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    InterProjectRealizedClass_sys_main.c
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    InterProjectRealizedClass_sys_types.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    LOG_bridge.c
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    LOG_bridge.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    OnwerComponent_classes.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    OnwerComponent.c
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    OnwerComponent.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    RealizedClass_bridge.c
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    RealizedClass_bridge.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    sys_user_co.c
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    sys_user_co.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    sys_xtuml.c
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    sys_xtuml.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    TestComponent_classes.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    TestComponent.c
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/src/
    TestComponent.h
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/.cproject
InterProjectRealizedClass [models 7744_realized_relaunch_failure]/.project

RealizedClassOnwer [models 7744_realized_relaunch_failure]/
    .externalToolBuilders/Model Compiler.launch
RealizedClassOnwer [models 7744_realized_relaunch_failure]/.launches/
    OwnerAndInterProjectLauncher.launch
RealizedClassOnwer [models 7744_realized_relaunch_failure]/bin/lib/
    RealizedClass.class
RealizedClassOnwer [models 7744_realized_relaunch_failure]/gen/code_generation/
    RealizedClassOnwer.sql
RealizedClassOnwer [models 7744_realized_relaunch_failure]/models/
    RealizedClassOnwer/RealizedClassOnwer.xtuml
RealizedClassOnwer [models 7744_realized_relaunch_failure]/models/
    RealizedClassOnwer/Datatypes/Datatypes.xtuml
RealizedClassOnwer [models 7744_realized_relaunch_failure]/models/
    RealizedClassOnwer/Model/Model.xtuml
RealizedClassOnwer [models 7744_realized_relaunch_failure]/models/
    RealizedClassOnwer/System/System.xtuml
RealizedClassOnwer [models 7744_realized_relaunch_failure]/models/
    RealizedClassOnwer/System/OnwerComponent/OnwerComponent.xtuml
RealizedClassOnwer [models 7744_realized_relaunch_failure]/models/
    RealizedClassOnwer/System/OnwerComponent/Testing/Testing.xtuml
RealizedClassOnwer [models 7744_realized_relaunch_failure]/src/lib/
    RealizedClass.java
RealizedClassOnwer [models 7744_realized_relaunch_failure]/.classpath
RealizedClassOnwer [models 7744_realized_relaunch_failure]/.cproject
RealizedClassOnwer [models 7744_realized_relaunch_failure]/.project



</pre>
End
---

