---

This work is licensed under the Creative Commons CC0 License

---

# Return Strings Safely
### xtUML Project Implementation Note


1. Abstract
-----------
MC-3020 returns strings in a non-portable and marginally unsafe manner.
A new design for returning strings from action bodies is offered.

2. Document References
----------------------
[1] [BridgePoint DEI #589](https://support.onefact.net/redmine/issues/589)  test model for string reentrancy  
[2] [Test Model](https://github.com/xtuml/models/tree/master/VandMC_testing/mctest/string_return_test/) VandMC_testing/mctest/string_return_test  
[3] [Analysis Note](https://github.com/xtuml/bridgepoint/tree/master/doc-bridgepoint/notes/589_stringtest/589_returnstring.ant.md) doc-bridgepoint/notes/589_stringtest/  
[4] [Design Note](https://github.com/xtuml/bridgepoint/tree/master/doc-bridgepoint/notes/589_stringtest/589_returnstring.dnt.md) doc-bridgepoint/notes/589_stringtest/  

3. Background
-------------
Several solution options were explored in [3].  Here we implement the second choice after the first choice failed.

4. Requirements
---------------
See [3].

5. Work Required
----------------
See [4].

6. Implementation Comments
--------------------------

7. Unit Test
------------
8.1 Run [2].  It is expected to compile and run continuously.

8.2 Build and run GPS Watch on Windows.

8.3 Build and run the Microwave oven on both Windows and Unix.

8. Code Changes
---------------
Branch name:  xtuml/mc/589_returnstring2  
              xtuml/bridgepoint/589_returnstring  
              xtuml/models/589_stringtest  

Fork name:  cortlandstarrett (bridgepoint and models)  public repo (copy) of mc

<pre>

xtuml/mc
arc/c/t.sys_types.h     |  4 ++--
arc/q.class.arc         |  2 +-
arc/q.oal.action.arc    | 45 ++-------------------------------------------
arc/q.parameters.arc    |  2 +-
arc/q.smt.generate.arc  | 39 +++++++++++++++++----------------------
arc/q.sys.populate.arc  | 92 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-------
arc/q.utils.arc         | 48 ++++++------------------------------------------
arc/q.val.translate.arc | 67 +++++++++++++++++++++++++++++++++++++++++++++++++++++--------------
arc/t.smt.return.c      | 26 ++++++++++----------------
arc/t.sys_sets.c        |  4 ++--

xtuml/bridgepoint
doc-bridgepoint/notes/589_stringtest/*
doc-bridgepoint/process/templates/*

xtuml/models
VandMC_testing/mctest/string_return_test/*

</pre>

End
---

