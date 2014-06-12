---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Address multiple issues found in the 4.1.12 release candidate
### xtUML Project Design Note

1. Abstract
-----------
This note describes the changes required to address issues found in the 4.1.12
release candidate.

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
Customers have tested all of the merge issues addressed in the 4.1.12 release
candidate. One of the issues resolved in the release was [5], this work caused 
corruption in the customer repository.   This corruption issue triggered a patch 
release. The patch release was to only include addressing the possibility of 
corruption. However, once the original issues were verified they extended their 
testing. This extended testing revealed the issues that are resolved as part of 
this work.

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
A "slot" here is the location that a merged element is placed into.
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

A new class is added, EmptyElement.  This class represents a missing element
within the compare tree.  The class stores the expected location for the missing
element.

The missing elements are created within the ModelCompareContentProvider class.
After locating all children for the parent element the opposite side is checked
for any elements that do not exist locally.  For each missing element an
instance of the EmptyElement class is created and inserted into the children
list.  For the ancestor version both the left and right sides are looked at for
missing elements.  In the case that the left and right have the missing element
only one element is created in the merge.

In order to check the opposite side the ModelCompareContentProvider needs to
have access to both the left and right root elements.  CLI and the regular UI
parts are updated to pass in the latest root elements.

The work done in 6.1.3 allows determination of the proper slot location for a
missing element.  Using this the EmptyElements are inserted in the proper
location by determining the location in the appropriate slot.

6.1.2 Rework existing code to work with empty elements

6.1.2.1 ModelMergeProcessor

In ModelMergeProcessor, the code is reworked to look for an EmptyElement as the
difference element.  This replaces the existing checks for a null element.  The
location in the destination for the merge is determined by the value set in the
empty element.  The code for moveElementUp and moveElementDown is consolidated
into a single method, insertElementAt.

The handleNewElement method is modified to insert the new element at the end of
the ordering list.  Then the new insertElementAt method is called to place it at
the proper destination location.

6.1.2.2 TreeDifferencer

The TreeDifferencer is greatly simplified by removing all code that checked for
missing elements on one side.  Existing null checks are extended to look for an
EmptyElement instance and handle them the same way they were when it was a null
element.

6.1.2.3 ModelContentMergeViewer and SynchronizedTreeViewer

The drawing logic for highlighting differences is reworked to remove the code
that looks for a null element.  This reduces the amount of logic as all draws
will work with a highlight box rather than needing to underline the element
above the expected location.

6.1.3 Add support for slot determination

In create_object_inspector a new function is generated in the inspector classes:

int getTreeDifferenceSlot(Object Element)

This returns the a slot number for all model elements that are parents in a tree, 
and contain more than 1 child.  This "slot number" is used when comparing and merging tree
elements that contain multiple children.  All model element inspectors
contain this function, but it only returns a value for cases where it is
a parent element in a tree and the parent has multiple children.  This
function returns 0 if this is not a "parent" in the tree OR if the
parent has less than 2 children.

To further support this change to assure all necessary places contain this
operation, this new operation was added to the IModelClassInspector interface that
all inspector classes implement.


6.2 Automatically merge graphical data when local file contains semantic element

The code in ModelContentMergeViewer.getIncomingGraphicalDifferences() is
modified to locate the local file and check its contents for the semantic
element that the graphical element represents.  If it exists the graphical
difference is considered for automatic merge.

7. Design Comments
------------------
7.1 TreeDifferencer issues

During testing it was found that proper differencing was not always being done.
This was most notably visible when using the Git Staging view.  The problem here
was that the tool was trying to cache differences that were already determined.
The key that was being used to find an existing differencer was not always the
same in the case of the Git Staging view.  The tree differencing engine does not
require much processing, so rather than cache the differencers a new one is
created each time the input is changed.

7.2 AttributeComparable

It was noticed that while merge processing was occurring the tree can be
updated.  This was triggering comparison of attributes while they were in a
state which had the association between the model class and attribute torn down.
This was resulting in null pointer exceptions.  To address this the
AttributeComparable.java class is modified to ask the supertype of the two
elements if each are equal when either attribute's class is empty.

7.3 Issues reading input stream

The compare tool will automatically save files if viewed and then closed.  This
was done to remove the git patch data from a model file when no merge was
actually performed.  When using the Git staging view eclipse cannot handle the
given input stream correctly.  The code in ModelContentMergeViewer.handleDispose
is modified to read the stream ourselves and put the data into a
ByteArrayInputStream.  This new stream is then passed to the setContents method
for the local file.

8. Unit Test
------------

8.1  Verify issues [2], [3], and [4] are resolved.

8.1.1 Create a test model

* Start with GPS Watch
* Create a new package named 101057788 (named after the SR associated with [2]) under the system
* Create an interface, Pkg1, in 101057788 
* Create a new package named test1 under the system
* Create an interface, iface1, in test1
* Commit this to branches master and "slave" 
* In the master branch, add operations (m_op1, m_op2, sameName), add signals
(m_sig1, m_sig2, sameName) to the new interface, Pkg1
* In the master branch, add the following attributes to Libraray::Location::GPS (m_a1, m_a2)
* In the master branch, add the following operations to Libraray::Location::GPS (m_o1, m_o2)
* In the master branch create an operation, m_op1, in test1::iface1
* Commit changes the master 
* Switch to the slave branch
* In the slave branch, add operations (s_op1, s_op2, sameName), add signals
(s_sig1, s_sig2, sameName) to the new interface, Pkg1
* In the slave branch, add the following attributes to Libraray::Location::GPS (s_a1, s_a2)
* In the slave branch, add the following operations to Libraray::Location::GPS (s_o1, s_o2)
* In the slave branch create an operation, s_op1, in test1::iface1
* Note: The model described above this point exists in the git models repositopry as 236.zip. 
It is used in the unit test that performs the test described below this point.

8.1.2 Test merge (using the model from 8.1.1)

*  Checkout the slave branch
*  Merge master into slave branch
*  Result - conflicts will be reported in Pkg1, GPS, WorkoutTimer, and iface1
*  Open the merge tool
*  Result - Pkg1 (6 incoming changes in master, 5 outgoing changes from slave)
*  Result - Library::Location::GPS  (4 incoming changes in master, 4 outgoing changes from slave)
*  Result - Library::Tracking::WorkoutTimer (1 incoming change in master, 1 outgoing change from slave)
*  Result - test1::iface1 (1 incoming change in master, 1 outgoing change from slave)
*  Merge incoming changes from Pkg1 (merge from right to left)
*  Result - all changes are successfully merge
*  Merge incoming changes from Pkg1 (merge from right to left)
*  Result - all changes are successfully merge
*  Merge incoming changes from WorkoutTimer (merge from right to left)
*  Result - all changes are successfully merge
*  Merge incoming changes from iface1 (merge from right to left)
*  Result - all changes are successfully merge

8.1.2 Test compare (using the model from 8.1.1)

* Checkout the slave branch
* Compare With... master
* Result - Pkg1 (6 incoming changes in master, 5 outgoing changes from slave)
* Result - Library::Location::GPS  (4 incoming changes in master, 4 outgoing changes from slave)
* Result - Library::Tracking::WorkoutTimer (1 incoming change in master, 1 outgoing change from slave)
* Result - test1::iface1 (1 incoming change in master, 1 outgoing change from slave)
* Result - no error are present in the error log

End
---

