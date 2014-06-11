---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Address multiple issues found in the 4.1.12 release
### xtUML Project Design Note

1. Abstract
-----------
This note describes the changes required to address issues found in the 4.1.12
release.

2. Document References
----------------------
[1] Issues 236, https://github.com/xtuml/doc/issues/236  
[2] CQ Issue, dts0101057785 - Copying of incoming changes for a class operations
                              does not work properly
[3] CQ Issue, dts0101057788 - Copying of incoming changes for a interface signal
                              does not work properly
[4] CQ Issue, dts0101057790 - After merging interface formalizations of
                              components graphics of the formalized interface is
                              not shown
[5] CQ Issue, dts0101009925 - Merging addition of state machine from two
                              branches
3. Background
-------------
Customers have tested all of the merge issues addressed in the 4.1.12 release.
One of the issues resolved in the release was [5], this work caused corruption
in the customer repository.   This corruption issue triggered a patch release.
The patch release was to only include addressing the possibility of corruption.
However, once the original issues were verified they extended their testing.
This extended testing revealed the issues that are resolved as part of this
work.

4. Requirements
---------------
4.1 Support merging new elements regardless of slot location
4.2 Support empty elements that indicate where the missing element will be
    merged to
4.3 Automatically merge graphics if the local file contains the represented
    element

5. Analysis
-----------
5.1 Ordering issues

Issues [2] and [3] are related to ordering. To be specific it is related to user
controlled ordering.  The issue is that we order elements in children slots.
For instance an interface operation is user ordered and so is an interface
signal.  However, the ordering is dependent on the child slot location.
Interface operations, regardless of user defined order, are always before
interface signals.  This was causing issues when trying to adjust user defined
ordering among the slot elements.  During the initial analysis slot
recognization was determined to be the best approach at resolving these issues.
The logic in the differencing engine was getting over complicated.  Upon
reconsideration it was thought that adding an empty slot for empty elements
would address the issue.  This turned out to be false, but it allowed for
simplification of existing code.  In addition to the simplification of existing
code it also better indicates where elements will be merged.

5.2 Automatically merge graphical data when local file contains semantic element

Issue [4] shows a problem where git has pre-merged a file containing the
semantic element, yet the file containing the graphical element has a conflict.
The tool shall be modified to check for the semantic element in it's local file.
If the semantic element exists, the graphical data shall also be merged.

6. Design
---------
6.1 Ordering issues
6.1.1 Add support for empty elements
6.1.2 Rework existing code to work with empty elements
6.1.3 Add support for slot determination
 
6.2 Automatically merge graphical data when local file contains semantic element

7. Design Comments
------------------

8. Unit Test
------------

End
---

