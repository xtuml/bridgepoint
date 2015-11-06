---

This work is licensed under the Creative Commons CC0 License

---

# Custom Class Loader
### xtUML Project Implementation Note


1. Abstract
-----------
BridgePoint Verifier uses a custom Class Loader to load classes into the execution
session. The existing Class Loader accesses internal Eclipse components that are
no longer available in Eclipse 4.4

2. Document References
----------------------
[1] [BridgePoint DEI #7739](https://support.onefact.net/redmine/issues/7739)   
[2] [Existing Implementation](bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/BPClassLoader.java)  
[3] [Design note](https://github.com/nmohamad/bridgepoint/blob/7739_classloader/doc-bridgepoint/notes/7739_BridgePoint_ClassLoader/7739_BridgePoint_ClassLoader.dnt.md)  
3. Background
-------------
See [1]

4. Requirements
---------------
[1]

5. Work Required
----------------
See [1]

6. Implementation Comments
--------------------------
See [3]

7. Unit Test
------------
7.1 A new test model is created RealizedComponentTest under Models repo. The 
test class contains 3 components, two of them are realized. The models cover 
all interface communication scenarios as follows.
- Interface Messages from UML to Java through both provided and required ports with parameter (by Val, by Ref) and without
- Interface Messages from Java to UML through both provided and required ports with parameter (by Val, by Ref) and without
- Interface Messages from Java to Java through both provided and required ports with parameter (by Val, by Ref) and without

A java junit class RealizedComponentTest.java is created, to load the test model
in verifier, execute 7 tests functions and compare the console log output.

8. Code Changes
---------------
Branch name: <7739_classloader>  bridgepoint repo

<pre>


doc-bridgepoint/> notes/7739_BridgePoint_ClassLoader/
    7739_BridgePoint_ClassLoader.dnt.md
doc-bridgepoint/> notes/7739_BridgePoint_ClassLoader/
    7739_BridgePoint_ClassLoader.int.md


org.xtuml.bp.core/src/org/xtuml/bp/core/Vm_c.java
org.xtuml.bp.core/src/org/xtuml/bp/core/util/BPClassLoader.java


org.xtuml.bp.debug.ui/src/org/xtuml/bp/debug/ui/model/BPDebugTarget.java

org.xtuml.bp.debug.ui.test/src/VerifierTestSuite.java
org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/test/
    RealizedComponentTest.java


</pre>

Branch name: <7739_classloader>  models repo

<pre>


RealizedComponentTest/.launches/RealizedComponentTest.launch
RealizedComponentTest/bin/interfaces/IHandShakeFromProvider.class
RealizedComponentTest/bin/interfaces/IHandShakeToProvider.class
RealizedComponentTest/bin/interfaces/INotificationFromProvider.class
RealizedComponentTest/bin/interfaces/INotificationToProvider.class
RealizedComponentTest/bin/interfaces/ISyncronizationFromProvider.class
RealizedComponentTest/bin/interfaces/ISyncronizationToProvider.class
RealizedComponentTest/bin/interfaces/IUserComputerInteractionFromProvider.class
RealizedComponentTest/bin/interfaces/IUserComputerInteractionToProvider.class
RealizedComponentTest/bin/interfaces/IUserPhoneInteractionFromProvider.class
RealizedComponentTest/bin/interfaces/IUserPhoneInteractionToProvider.class
RealizedComponentTest/bin/library/Computer.class
RealizedComponentTest/bin/library/Phone.class
RealizedComponentTest/javasrc/interfaces/IHandShakeFromProvider.java
RealizedComponentTest/javasrc/interfaces/IHandShakeToProvider.java
RealizedComponentTest/javasrc/interfaces/INotificationFromProvider.java
RealizedComponentTest/javasrc/interfaces/INotificationToProvider.java
RealizedComponentTest/javasrc/interfaces/ISyncronizationFromProvider.java
RealizedComponentTest/javasrc/interfaces/ISyncronizationToProvider.java
RealizedComponentTest/javasrc/interfaces/
    IUserComputerInteractionFromProvider.java
RealizedComponentTest/javasrc/interfaces/IUserComputerInteractionToProvider.java
RealizedComponentTest/javasrc/interfaces/IUserPhoneInteractionFromProvider.java
RealizedComponentTest/javasrc/interfaces/IUserPhoneInteractionToProvider.java
RealizedComponentTest/javasrc/library/Computer.java
RealizedComponentTest/javasrc/library/Phone.java
RealizedComponentTest/models/RealizedComponentTest/RealizedComponentTest.xtuml
RealizedComponentTest/models/RealizedComponentTest/interfaces/interfaces.xtuml
RealizedComponentTest/models/RealizedComponentTest/interfaces/HandShake/
    HandShake.xtuml
RealizedComponentTest/models/RealizedComponentTest/interfaces/Notification/
    Notification.xtuml
RealizedComponentTest/models/RealizedComponentTest/interfaces/Syncronization/
    Syncronization.xtuml
RealizedComponentTest/models/RealizedComponentTest/interfaces/
    UserComputerInteraction/UserComputerInteraction.xtuml
RealizedComponentTest/models/RealizedComponentTest/interfaces/
    UserPhoneInteraction/UserPhoneInteraction.xtuml
RealizedComponentTest/models/RealizedComponentTest/library/library.xtuml
RealizedComponentTest/models/RealizedComponentTest/library/Computer/
    Computer.xtuml
RealizedComponentTest/models/RealizedComponentTest/library/Phone/Phone.xtuml
RealizedComponentTest/models/RealizedComponentTest/library/User/User.xtuml
RealizedComponentTest/models/RealizedComponentTest/library/User/
    ComputerTesting/ComputerTesting.xtuml
RealizedComponentTest/models/RealizedComponentTest/library/User/
    External Entity/External Entity.xtuml
RealizedComponentTest/models/RealizedComponentTest/library/User/PhoneTesting/
    PhoneTesting__MakeCall.oal
RealizedComponentTest/models/RealizedComponentTest/library/User/PhoneTesting/
    PhoneTesting.xtuml
RealizedComponentTest/.classpath
RealizedComponentTest/.project


</pre>


End
---

