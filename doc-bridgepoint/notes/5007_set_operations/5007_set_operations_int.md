---

This work is licensed under the Creative Commons CC0 License

---

# Operators `+`, `-`, `and` and `or` of instance handle sets are missing in OAL.
### xtUML Project Implementation Note

### 1. Abstract

Set operators for instance sets are supported in RSL but not in OAL. This issue
is raised to analyze and implement support in OAL. Verifier and model compiler
support will also be considered.

### 2. Document References

<a id="2.1"></a>2.1 [#5007 Operators +, -, and and or of instance handle sets are missing in OAL.](https://support.onefact.net/issues/5007)  
<a id="2.2"></a>2.2 [#5007 analysis note](5007_set_operations_ant.md)  
<a id="2.3"></a>2.3 [#5007 design note](5007_set_operations_dnt.md)  
<a id="2.4"></a>2.4 [#10119 Investigate existing tests which call setUp and tearDown twice](https://support.onefact.net/issues/10119)  

### 3. Background

See section 3 in [[2.2]](#2.2) and [[2.3]](#2.3).

### 4. Requirements

See section 4 in [[2.3]](#2.3).

### 5. Work Required

The design note contains a lot of detail on the implementation. See
[[2.3]](#2.3) section 6 for more information.

5.1 Implementation flow

Because the Verifier implementation of the set operations uses OAL, intermediate
builds had to be done and the new build had to be installed so that development
could continue. The flow was as follows:

5.1.1 Modify parser to allow syntax of set operations and to correctly parse
them into `V_` instances.  
5.1.2 Build BridgePoint.  
5.1.3 Modify MC-Java to support set operations.  
5.1.4 Use new BridgePoint to implement set operations in Verifier (use the set
operations to implement set operations).  
5.1.5 Build BridgePoint again.  
5.1.6 Use set operations in Verifier.  

### 6. Implementation Comments

6.1 Build environment

This work introduces a unique situation. We do not commonly use new BridgePoint
features in the development of the features themselves (as explained in section
5.1). Once this work is promoted, there will be no published version of
BridgePoint capable of building BridgePoint. Action must be taken to produce and
distribute a nightly build which developers can use to build the `master`
branch. The following actions will be taken:

6.1.1 The BridgePoint build server shall be updated and re-imaged.  
6.1.2 The developer VM shall be updated with a new version of BridgePoint.  
6.1.3 An announcement shall be made in the xtUML community chat that the latest
nightly build will be necessary to develop BridgePoint moving forward.  

6.2 Build server update

The following process will be taken to update the build server with the newest
version of BridgePoint:

6.2.1 Promote this work into `master`.  
6.2.2 Launch a build server instance. Install the latest 5007 branch build on
the build server instance. Run a nightly build (`master`). Terminate the
instance.  
6.2.3 Launch a new build server instance. Install the newest nightly build on
the new build server instance. Run another nightly build.  
6.2.4 Image the server instance. Propagate to all AWS regions. Update the server
launch scripts with new AMI IDs.  
6.2.5 Launch a final nightly build with test enabled to ensure that the build
server has been updated properly and no manual intervention is necessary.  

6.3 Build workaround

For developers who are unable to update or are unwilling to wait for the process
specified in 6.2, it will still be possible to build BridgePoint with old
builds. If the developer opens the `getInstanceSet` operation on "Binary
Operation" and comments out the lines which use the set operators, the build
should succeed. However, the behavior of the set operators in Verifier for any
resulting build will be incorrect. This is an unlikely use case, so this
workaround may be valuable for developers to prevent them from getting stuck
during the process of updating the build server.

### 7. Unit Test

7.1 Update of test suite generator

During implementation of the tests, it was discovered that the unit tests were
executing the `setUp` and `tearDown` methods twice for each test. When we moved
to JUnit4, we started using the `@Before` and `@After` annotations to specify
setup and tear down routines, however the unit test generator script
`UnitTestGenerator.pl` also continued to generate explicit invocations of these
methods at the beginning and end of each test. The script was modified to remove
these explicit invocations.

There are some test suites that are generated using this script, but afterward
are committed and maintained by hand. An issue has been raised [[2.4]](#2.4) to
investigate these tests to see if any improvement can be seen in removing the
explicit invocations.

7.2 Modification of existing tests

After implementation of the set operations and the unit tests for them, some
changes/updates were needed to ensure that all existing tests continue to pass.

7.2.1 In the parser, two strings used for parse errors were modified from
"addition expression" -> "additive expression" and "multiplication expression"
-> "multiplicative expression". This was done because now the set operations are
categorized as additive and multiplicative expressions and the new wording is
more accurate. In several areas, expected results needed to be updated with this
new wording.

7.2.2 There was a test in the OAL parser tests which assured that adding two
instance reference handles together with the `+` operator is not allowed. After
this work, this is allowed (and it will return a set containing the two
elements). This test has been removed because it is no longer valid.

7.2.3 Before this work, line and column information was not included in binary
operations because it was not needed. The implementation of the set operations
in MC-3020 used the line and column information to generate unique names for
return sets from binary operations. Because of this, the parser was modified to
store the correct information in the binary operation instances. The expected
results for a test needed to be updated to match the new line and column
information (before it was all `-1`s).

7.2.4 The export tests perform comparisons of expected output files in certain
tests. Three tests were showing diffs in the exported `.xtuml` files because of
the same issue noted in 7.2.3. The expected result files needed to be
regenerated for these tests. During this regeneration, it was manually confirmed
that the only new differences were in the line and column information of the
binary operations.

7.2.5 The unit test framework performs a check for every test in which it checks
the output log and throws an error if there is any unexpected output. During the
process of updating the unit tests and build process to run on maven, it was
found that in some cases, maven unexpectedly produces output. A flag was added
to allow test plugins to skip the output check. This had to be done for the set
operations unit test suite to enable it to pass when executed by the maven
build.

### 8. User Documentation

See [[2.3]](#2.3) section 8

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 5007_set_operations  

<pre>

 doc-bridgepoint/notes/5007_set_operations/5007_set_operations_ant.md                                                                      |   6 +-
 doc-bridgepoint/notes/5007_set_operations/5007_set_operations_dnt.md                                                                      | 487 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/5007_set_operations/5007_set_operations_int.md                                                                      | 241 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/5007_set_operations/difference.png                                                                                  | Bin 3355 -> 3340 bytes
 doc-bridgepoint/notes/5007_set_operations/intersection.png                                                                                | Bin 3266 -> 3218 bytes
 doc-bridgepoint/notes/5007_set_operations/sets.xcf                                                                                        | Bin 19527 -> 23534 bytes
 doc-bridgepoint/notes/5007_set_operations/symmetric_difference.png                                                                        | Bin 0 -> 3228 bytes
 doc-bridgepoint/notes/5007_set_operations/union.png                                                                                       | Bin 3333 -> 3320 bytes
 doc-bridgepoint/notes/5007_set_operations/val2.png                                                                                        | Bin 0 -> 1832455 bytes
 src/MC-Java/statement.inc                                                                                                                 |   8 +++
 src/MC-Java/value.inc                                                                                                                     |  21 +++++++
 src/org.xtuml.bp.als.oal/bnf/oal.bnf                                                                                                      |  14 ++++-
 src/org.xtuml.bp.als.oal/src/org/xtuml/bp/als/oal/oal_lex.g                                                                               |   3 +
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Functions/OAL Validation Functions.xtuml                 |  67 +++++++++++++++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Utility Functions/OAL Validation Utility Functions.xtuml | 217 +++++++++++++++++----------------------------------------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Instance/Instance.xtuml                                                           |  50 +++++++++++++---
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Value/Binary Operation/Binary Operation.xtuml                                     | 146 +++++++++++++++++++++++++---------------------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/NonRootModelElement.java                                                               | 114 ++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/Expressions.html                                                                           | 469 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/Expressions.md                                                                             | 330 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/difference.png                                                                             | Bin 0 -> 3340 bytes
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/intersection.png                                                                           | Bin 0 -> 3218 bytes
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/symmetric_difference.png                                                                   | Bin 0 -> 3228 bytes
 src/org.xtuml.bp.doc/Reference/OAL/Expressions/union.png                                                                                  | Bin 0 -> 3320 bytes
 src/org.xtuml.bp.doc/Reference/OAL/HTML/bpalref.book.htm                                                                                  |   2 +-
 src/org.xtuml.bp.doc/topics_Reference.xml                                                                                                 |   2 +-
 26 files changed, 1930 insertions(+), 247 deletions(-)

</pre>

Fork/Repository: leviathan747/mc  
Branch: 5007_set_operations  

<pre>

 arc/c/q.sys.singletons.arc                                               |   5 ++++-
 arc/c/t.sys_sets.h                                                       |  13 ++++++++++---
 arc/q.val.translate.arc                                                  |  41 +++++++++++++++++++++++++++++++++++++----
 arc/sysc/t.sys_sets.h                                                    |  10 +++++++++-
 arc/t.sys_sets.c                                                         | 193 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-------------------
 bin/mac/mcmc                                                             | Bin 1549160 -> 1569800 bytes
 bin/mcmc                                                                 | Bin 2404616 -> 2408744 bytes
 bin/win/mcmc.exe                                                         | Bin 1702402 -> 1707581 bytes
 mcmc/arlan/o.oal                                                         |  27 +++++++++++++++------------
 mcmc/arlan/o2.oal                                                        |  41 +++++++++++++++++++++++++++++++++++++----
 model/escher/gen/ooaofooa.c                                              | 138 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--------------------------------
 model/escher/gen/ooaofooa_c_orig                                         | 222 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++---------------------------------------------------------------------------------
 model/escher/models/escher/mcmc/afun/afun.xtuml                          |  27 +++++++++++++++------------
 model/escher/models/escher/mcmc/afunval/afunval.xtuml                    |  41 +++++++++++++++++++++++++++++++++++++----
 model/mcooa/models/mcooa/extensions/Translation Extensions/set/set.xtuml |  62 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++----
 schema/sql/xtumlmc_schema.sql                                            |   5 ++++-
 16 files changed, 646 insertions(+), 179 deletions(-)

</pre>

Fork/Repository: leviathan747/models  
Branch: 5007_set_operations  

<pre>

 test/test_set_operations_5007/.cproject                                                                                         | 129 +++++++++++++++++++++++++++
 test/test_set_operations_5007/.externalToolBuilders/Model Compiler.launch                                                       |  14 +++
 test/test_set_operations_5007/.gitignore                                                                                        |   3 +
 test/test_set_operations_5007/.launches/test_set_operations_5007.launch                                                         |  10 +++
 test/test_set_operations_5007/.launches/test_set_operations_5007_exe.launch                                                     |  31 +++++++
 test/test_set_operations_5007/.project                                                                                          |  43 +++++++++
 test/test_set_operations_5007/.settings/org.eclipse.cdt.codan.core.prefs                                                        |  71 +++++++++++++++
 test/test_set_operations_5007/gen/LOG_bridge.c                                                                                  |  84 ++++++++++++++++++
 test/test_set_operations_5007/gen/bridge.mark                                                                                   |  59 +++++++++++++
 test/test_set_operations_5007/gen/class.mark                                                                                    | 250 +++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/gen/datatype.mark                                                                                 | 166 +++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/gen/domain.mark                                                                                   | 219 ++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/gen/event.mark                                                                                    |  43 +++++++++
 test/test_set_operations_5007/gen/expected_results.txt                                                                          |  15 ++++
 test/test_set_operations_5007/gen/system.mark                                                                                   | 259 ++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/EEs/EEs.xtuml                                                     | 631 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp1/classes/PERSON/PERSON.xtuml                             | 138 +++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp1/classes/classes.xtuml                                   | 332 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp1/comp1.xtuml                                             | 124 ++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp1/functions/.gitignore                                    |   2 +
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp1/functions/functions.xtuml                               | 450 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/PrecedenceTests/G1/G1.xtuml                             | 782 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/PrecedenceTests/G2/G2.xtuml                             | 106 +++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/PrecedenceTests/PrecedenceTests.xtuml                   | 105 ++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SetCompareTests/H1/H1.xtuml                             | 126 +++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SetCompareTests/H2/H2.xtuml                             | 126 +++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SetCompareTests/SetCompareTests.xtuml                   | 105 ++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SimpleSetOperationsTests/O1/O1.xtuml                    | 134 ++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SimpleSetOperationsTests/O2/O2.xtuml                    | 134 ++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SimpleSetOperationsTests/O3/O3.xtuml                    | 134 ++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SimpleSetOperationsTests/O4/O4.xtuml                    | 134 ++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/SimpleSetOperationsTests/SimpleSetOperationsTests.xtuml | 153 ++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/classes/INTEGER/INTEGER.xtuml                           |  55 ++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/classes/classes.xtuml                                   | 121 ++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/comp2.xtuml                                             | 196 +++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/comp2/functions/functions.xtuml                               | 247 ++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/lib/lib.xtuml                                                     |  94 ++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/sys/sys.xtuml                                                     |  96 +++++++++++++++++++++
 test/test_set_operations_5007/models/test_set_operations_5007/test_set_operations_5007.xtuml                                    | 104 ++++++++++++++++++++++
 test/test_set_operations_5007/src/.gitignore                                                                                    |   1 +
 test/test_set_operations_5007/src/Makefile                                                                                      |  16 ++++
 41 files changed, 6042 insertions(+)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 5007_set_operations  

<pre>

 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/TestBPPrefStrictTyping_Generics.java     |    16 +-
 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/TestExpr_Generics.java                   |    27 +-
 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/TestLineNumbers_Generics.java            |     2 +-
 src/org.xtuml.bp.debug.ui.test/META-INF/MANIFEST.MF                                                  |     6 +-
 src/org.xtuml.bp.debug.ui.test/generate.xml                                                          |    54 +
 src/org.xtuml.bp.debug.ui.test/matrices/set_operations_compare_matrix.txt                            |    32 +
 src/org.xtuml.bp.debug.ui.test/matrices/set_operations_precedence_matrix.txt                         |    73 +
 src/org.xtuml.bp.debug.ui.test/matrices/set_operations_simple_matrix.txt                             |    34 +
 src/org.xtuml.bp.debug.ui.test/pom.xml                                                               |    46 +
 src/org.xtuml.bp.debug.ui.test/src/VerifierTestSuite2.java                                           |     7 +-
 src/org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/ui/test/sets/.gitignore                        |     6 +
 src/org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/ui/test/sets/SetOperationsCompareTests.java    |    80 +
 src/org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/ui/test/sets/SetOperationsPrecedenceTests.java |    80 +
 src/org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/ui/test/sets/SetOperationsSimpleTests.java     |    80 +
 src/org.xtuml.bp.debug.ui.test/src/org/xtuml/bp/debug/ui/test/sets/SetOperationsTests.java           |   251 ++
 src/org.xtuml.bp.io.mdl.test/expected_results/export_results/watch_exportGenerics.xtuml              | 41872 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-----------------------------------------------------------------------------------------------
 src/org.xtuml.bp.io.mdl.test/expected_results/testOAL1.xtuml                                         |   292 +-
 src/org.xtuml.bp.io.mdl.test/expected_results/testOAL1Generics.xtuml                                 |   292 +-
 src/org.xtuml.bp.test/UnitTestGenerator.pl                                                           |     2 -
 19 files changed, 21989 insertions(+), 21263 deletions(-)

</pre>

### End
