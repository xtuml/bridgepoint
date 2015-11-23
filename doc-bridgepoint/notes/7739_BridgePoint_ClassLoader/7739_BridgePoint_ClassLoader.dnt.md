---

This work is licensed under the Creative Commons CC0 License

---

# Custom Class Loader
### xtUML Project Design Note

1. Abstract
-----------
BridgePoint Verifier uses a custom Class Loader to load classes into the execution
session. The existing Class Loader accesses internal Eclipse components that are
no longer available in Eclipse 4.4


2. Document References
----------------------
[1] [BridgePoint DEI #7739](https://support.onefact.net/redmine/issues/7739) 
[2] [Existing Implementation](bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/BPClassLoader.java)


3. Background
-------------
The old class loader implementation is built upon an Eclipse internal class,
org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader that changes behaviour in Eclipse 4, [1]

DefaultClass implements the ClassLoader interface with certain extensions.

Certain aspects seems to have been important in the design of BPClassLoader.
Review of [2] have been used as a method to extract requirements.

Class Loading in an Eclipse context have been extended in comparision to the standard Java model
so that the class path used for loading depends on the plugin doing the loading and the dependencies that
plugin has declared. Different plugins running in the same Eclipse instance may get different results when loading the same class.

The internal class loading classes in Eclipse is redesigned between Eclipse 3 and Eclipse 4, and there is common interface that is possible to use on both target environments.

4. Requirements
---------------
The new BPClassLoader must have the possibility to specify a class path that lies outside the plugin the loading,
since integration of Java legacy components may reference a class outside the plugins in the running eclipse instance.

The new BPClassLoader should not depend on the Eclipse version used.

5. Analysis
-----------

5.1 Loading Order

The old BPClassLoader first tries to use an already loaded class found in a static definitions cache.
If no class is found in the definitions cache, all the local class path entries are searched for a suitable
file to load. If a class is found, that is loaded. Otherwise the more normal way of loading classes in Java is used,
handing over to the parent class loader.

5.2 Static Definitions Cache

Currently a shared definitions cache is used for loading, which even though a separate class loader is used for each component in Verifier,
different components can not load different variants of the same class.

5.3 Thread-safe

The class loader needs to be implemented in a thread-safe way, since the objects are accessed by different verifier threads.

6. Design
---------
Implement a basic classloader, not depending on Eclipse specific class loaders, but rather on the basic java class loader interface.
Use the default class loader of the platform as the parent class loader.

7. Design Comments
------------------

8. Unit Test
------------
All the existing unit test for verifier should still pass.

Test a number of different verifier configurations with external Java code.
The current unit tests seems to be lacking extensive testing of realized components, but contains a number of external entities test.
In addition there is need to perform testing with adapted class-paths, and realized component paths, since those tests seems to be missing in the test suite.

End
---
