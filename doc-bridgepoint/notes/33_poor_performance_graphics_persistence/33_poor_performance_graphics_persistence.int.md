---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Poor persistence during graphical modifications
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes a change that prevents an issue where persistence
performance is hindered due to reading the system model file.

2. Document References
----------------------  
[1] Issues 33, https://github.com/bridgepoint/doc/issues/33    

3. Background
-------------
Customers have reported poor graphical performance and noted that most of it was
due to file reads.  The issue shows itself more when working across slower
network shared file systems.

4. Requirements
---------------
4.1 Graphical modifications shall show better performance.

5. Work Required
----------------
5.1. Increase persistence performance
  
Investigation showed that code which checked the current persistence version was
being called even if a model file had already been loaded.  This is done on
every model file in a system.  The step is also performed any time the tool
tries to access an element from a given model file.

In order to address this the call to
PersistenceManager.isPersistenceVersionAcceptable() is moved to be called after
the check to see if the file has already been loaded.

6. Implementation Comments
--------------------------

7. Unit Test
------------
_- Run all existing unit tests
_R All tests pass

8. Code Changes
---------------
Branch name: ?

<pre>

com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/common/
	PersistableModelComponent.java

</pre>

End
---

