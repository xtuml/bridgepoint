---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Proxy merge issues
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the code changes required.

2. Document References
----------------------
[1] Issues 244, https://github.com/xtuml/doc/issues/244  
[2] https://github.com/xtuml/internal/blob/master/doc-internal/notes/244_class_merge/244_class_merge.dnt.md

3. Background
-------------
See [2].

4. Requirements
---------------
See [2].

5. Work Required
----------------
See [2].

6. Implementation Comments
--------------------------
6.1 Undo and graphical change listener

During undo we send an event saying that an element was deleted.  This is to
capture the delta so that a redo can be performed.  Our graphics listener is
disposing of graphical elements in certain cases due to this event.  A
transaction contains all deltas including graphical deltas.  During an undo or
redo we do not need to listen for deletions as the delta list will contain the
necessary delta in order to remove the graphical data.

7. Unit Test
------------
See [2].

8. Code Changes
---------------
Branch name: 244_class_merge

<pre>

com.mentor.nucleus.bp.cli/src/com/mentor/nucleus/
    bp/cli/MergeWorkbenchAdvisor.java

com.mentor.nucleus.bp.core/arc/
    create_object_inspector.inc
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/
    bp/core/common/NonRootModelElement.java
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/
    bp/core/common/Transaction.java
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/
    bp/core/inspector/MessageArgumentInspector.java
com.mentor.nucleus.bp.core/src/com/mentor/nucleus/
    bp/core/inspector/PackageInspector.java
com.mentor.nucleus.bp.core/generate.xml

com.mentor.nucleus.bp.io.core/arc/
    export_functions.inc
com.mentor.nucleus.bp.io.core/src/com/mentor/
    nucleus/bp/io/core/CoreExport.java

com.mentor.nucleus.bp.io.mdl/arc/
    gen_stream_export.arc

com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/CompareTransactionManager.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/ModelMergeProcessor.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/TreeDifference.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/TreeDifferencer.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/contentmergeviewer/
    ModelContentMergeViewer.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/contentmergeviewer/
    SynchronizedTreeViewer.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/providers/ComparableProvider.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/providers/ModelCompareContentProvider.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/providers/NonRootModelElementComparable.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/providers/TreeDifferenceContentProvider.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/providers/custom/AssociationComparable.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/providers/custom/
    AssociationSubtypeComparable.java
com.mentor.nucleus.bp.model.compare/src/com/
    mentor/nucleus/bp/model/compare/structuremergeviewer/
    ModelStructureDiffViewer.java

com.mentor.nucleus.bp.model.compare.test/src/com/
    mentor/nucleus/bp/model/compare/test/ModelMergeTests.java
com.mentor.nucleus.bp.model.compare.test/src/com/
    mentor/nucleus/bp/model/compare/test/ModelMergeTests2.java

com.mentor.nucleus.bp.test/src/com/mentor/nucleus/
    bp/test/common/CompareTestUtilities.java

MC-Java/java.arc
MC-Java/referred_to.inc


</pre>

End
---

