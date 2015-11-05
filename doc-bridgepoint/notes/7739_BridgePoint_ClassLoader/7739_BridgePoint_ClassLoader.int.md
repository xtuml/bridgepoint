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

A java junit class RealizedComponentTest.java is created, to load the test model
in verifier, execute 7 tests functions and compare the console log output.

8. Code Changes
---------------
Branch name: <7739_classloader>

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

End
---

