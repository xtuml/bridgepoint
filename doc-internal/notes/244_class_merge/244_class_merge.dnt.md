---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Proxy merge issues
### xtUML Project Design Note


1. Abstract
-----------
This note describes changes that address some proxy related issues.

2. Document References
----------------------
[1] Issues 244, https://github.com/xtuml/doc/issues/244  
[2] CQ Issue dts0101063683 - Class merging causes broken models   

3. Background
-------------
During work of another issue merge was used for OAL changes in class files.
This presented an issue where corruption occurred in the model.  Additionally,
the same type of issue was hit on site with a customer.  This issue showed that
the same problem could occur with polymorphic events.

4. Requirements
---------------
4.1 Proxy merging shall not produce corruption.   
4.2 Ordering shall not cause unexpected conflicts.   

5. Analysis
-----------
5.1 Proxy merging

During merge we want to always export proxies when they are not local.  The
initial implementation had a flag to enable this.  It was true for all cases but
the copy of a new element.  In this case we actually need the proxies as well.

5.2 Container differences

One issue that was found was related to merging Referred To Identifier Attribute
instances.  The merge that interacts with these instances is a formalization
state change of an association.  During merge NPEs were being hit.  It was
determined that we had two choices, including special case the code to handle 
this situation or create a container difference.  In the case of a container
difference we actually create a difference on the association and copy it in its
entirety on merge.  

6. Design
---------
6.1 Proxy merging   

The code is modified to remove the flag specified in 5.1 and instead check for a
compare model root, in which case proxies will get exported.

MC-Java is modified to always return the cached value of an attribute so long as
the model root is a compare root and the element referred to is a proxy.  If it
is not a proxy that means it was an unrelate and therefore returning the cached
value would result in the unrelate not occuring on merge.

6.2 Container differences

The container approach was chosen due to less special casing and the fact that
this situation will not occur often.  It leaves us with a merge that is not as
fined grained, but prevents the addition of special case code.

To support merging container differences code is added that will exclude
contained differences selected for merge.  If any child element of the container
difference is selected the container difference will be the only one to be
merged.

Container differences are colored with a green line indicating a separation
between container and contained.

6.2.1 Association comparables

The class AssociationComparable is rewritten to work against Associations,
whereas before it worked on the subtypes.  This class is required to allow us to
consider the value of an Association different if the formalization state is
different.  The previous AssociationComparable class is renamed to
AssociationSubtypeComparable.

6.3 New element merging

When empty elements were added code was replaced to only consider a new element
when an empty element was the opposite change.  With the addition of container
changes and cases where a new reference replaces an older one, we need to also
consider a case where an empty element does not exist.  The ModelMergeProcessor
is modified to account for this.  In these cases the element on the other side
will get disposed and replaced with the remote element.


6.4 Undo issues

Undoing a merge shows some issues.  In the case of a formalization change that
is merged and then undone we have positional issues.  The association is left at
the end of the list.  To address this we adjust the ordering before disposal of
the existing element.  This allows us to capture deltas that will properly
restore ordering on undo.

6.5 Ordering issues

There were some situations where a conflict was determined due to an ordering
difference that should not exist.  This was due to bad logic when determining
the expected location for the missing element.  The logic was not fully
considering missing elements on one side.  The logic is reworked in
ModelCompareContentProvider to account for missing elements on the remote side.

The current merge works based off of the persisted order.  There are methods
that support adjusting this order once merged so that the two files can be
identical.  The existing ordering logic did not produce enough deltas to
properly support undo/redo.  This logic is modified to unrelate and re-relate
all associated elements, allowing for undo/redo to replace the elements in the
correct order.

7. Design Comments
------------------
7.1 Performance issues

During testing slow performance was noted.  One area that was causing some slow
down was in the inspector classes.  They were generated to use java reflection
in order to access attribute values.  This was modified to simply generate a
direct call to the method in order to get the attribute value.

Another small performance gain was made by modifying code in
ModelCompareContentProvider.  Rather than loop the referentials and build an
array, we loop them now once and ignore any that are null.

7.2 Undo issues

Testing showed issues with undo where an RTO was removed before the RGOs and
caused proxies to be created.  When merging we do not want to modify proxy
related data.  If an element was a proxy it remains as such and if it was not it
should not be converted to a proxy.  In NonRootModelElement.delete() we ignore
converting to a proxy if the RTO has external references.  This is only done
when in a compare situation, normal proxy conversion behaves as it used to.

7.3 Ant dependency issues in core

An issue was brought up where a rebuild did not occur for a few of the generated
utility classes.  These ant targets did not have the appropriate dependencies
and are updated to account for this.  Additionally, they did not touch the file
after generation for the case where generator was run but the generated code
was not different.

7.4 Delete confirmation dialog

During merge, dispose is called for elements to be removed.  We do not want this
dialog during merge.  In fact the checking can be incorrect as not all elements
are local and may be missing.  When we have logical model support we can
re-enable this, but for now the dialog is disabled in CompareTransactionManager.

7.5 Debug code

In ModelContentMergeViewer debug code is added that allows one to change a flag
that enables graphical data in the tree.  This helps during debugging as the 
graphical data is used but not visible to the user.

7.6 Two way merging

During testing it was noticed that two-way merging was not properly copying
graphical changes automatically.  The code was automatically assuming that the
comparison was three way.  In order to properly determine the type of comparison
and copy graphics appropriately, the tree differencer is asked whether or not
the comparison is three way.  If not the graphical changes are merged if they
are additions or deletions. 

8. Unit Test
------------
8.1 Merging proxies (class references)

_- Modify a class file that is an RGO in an association and compare with local
   history   
_- Merge the differences and save   
_R The O_REF entries are not corrupted   

8.2 Merging proxies (polymorphic events)

_- Modify a state machine with polymorphic events   
_- Compare with local history   
_- Merge the changes and save   
_R The transitions are not assigned to orphaned events    

8.3 Container differences

_- Unformalize an association   
_- Compare the class diagram's file with local history   
_R The association is marked as different    
_R The association difference has contained differences   
_- Select one of the contained differences and merge the change   
_R The entire association is copied over   
_- Undo the change   
_R The association differences are restored
_R The association is not formalized
_- Redo the merge by using the copy from right to left button   
_R The merge is successful   
_- Save the changes   
_- Compare the referring classes file with the local history   
_- Merge the changes and save   
_R The association is properly formalized   

8.4 Ordering issues

NOTE: The starting state machine must have two states with a transition between
them   

_- In branch one add a new state  
_- In branch two delete a state   
_- In branch two add a state   
_- Merge branch one into branch two     
_R There are no conflicts in the xtUML compare editor   
 

8.5 Two way merging
 
_- Create a new activity element   
_- Compare the diagram file with local history   
_- Merge the removal of the new activity element   
_R The graphics are also removed   

End
---

