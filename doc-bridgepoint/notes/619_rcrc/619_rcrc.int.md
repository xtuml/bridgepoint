---

This work is licensed under the Creative Commons CC0 License

---

# Remove Redundant Assignment from Threading
### xtUML Project Implementation Note

1. Abstract
-----------
MC-3020 provides a threading API that is generic enough to be a wrapper
for POSIX, Windows and a couple of other threading packages.  The
implementation for POSIX discards the return code from the threading
library but does so in a way that flags warnings to cppcheck.

2. Document References
----------------------
[1] [BridgePoint DEI #619](https://support.onefact.net/redmine/issues/619)  
[2] [cppcheck](http://en.wikipedia.org/wiki/Cppcheck) wikipedia cppcheck page  
[3] [POSIX Threads](http://linux.die.net/man/3/pthread_cond_broadcast) POSIX thread man pages  
[4] [Test Model](https://github.com/xtuml/models/tree/master/VandMC_testing/mctest/string_return_test/) VandMC_testing/mctest/string_return_test  

3. Background
-------------
3.1  cppcheck is a static analysis syntax checker for C anc C++.  See [2].
Users are running this tool on generated code.  It identifies a redundant
assignment of `rc = rc`.  
3.2  POSIX threading routines return an integer.  A non-zero value usually
indicates an error.  See [3] and related pages.  
3.3  `rc` was declared and assigned to the return of the threading routines
to avoid a syntax warning of "unused return value".  Then, rc was unused, 
so we assigned it to itself.  Now cppcheck dislikes that.  

4. Requirements
---------------

4.1  cppcheck shall report no redundant assignents in the threading routines.  
4.2  cppcheck shall report no unused variables in the threading routines.  
4.3  gcc -Wall shall be clean when compiling sys_thread.c for POSIX.  

5. Work Required
----------------

5.1  Remove all use of rc in the threading routines.  
5.2  Check the return of the threading routines for non-zero in an `if`
condition.  This nicely makes a place for future error recovery.  

6. Implementation Comments
--------------------------
6.1  Reduced the scope of i in `Escher_strcpy` (based on report from cppcheck).  

7. Unit Test
------------
7.1  Build and run [4].  
7.1.1  Run cppcheck --enable=all sys_thread.c.  See no reported redundant assignments.  
7.1.2  gcc -c -Wall sys_thread.c.  See no warnings or errors.  
7.1.3  Compile project cleanly.  
7.1.4  Run project cleanly.  

8. Code Changes
---------------
Branch name:  xtuml/mc/619_rcrc, xtuml/bridgepoint/619_rcrc
Fork name:  cortlandstarrett (copy of mc, fork of bridgepoint)

<pre>

xtuml/bridgepoint
doc-bridgepoint/notes/619_rcrc/*

xtuml/mc
arc/t.sys_threadposix.c
arc/t.sys_sets.c

</pre>

End
---

