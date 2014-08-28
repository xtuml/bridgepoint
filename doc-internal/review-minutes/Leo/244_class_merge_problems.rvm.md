---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Proxy merge issues
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/master/doc-internal/notes/244_class_merge/244_class_merge.dnt.md
           480cc6649d2a0ee8780e85422705be1ca4b5f096
Present:  Bob,Keith,Travis

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   SKB  [2]      Wrong CQ number
x- 02  min   bob  4        formatting problem
x- 03  min   trl  7.2      This is only when it is in a compare situation (when it is a compare root)
x- 04  min   bob  7.4      "not elements " -> "not all elements "
x- 05  min   bob  7.5      Make sure there is documentation for this debug flag.  Perhaps we should add
                           a preference (or something like that) to turn this debug flag on (that could make it 
						   self-documenting).
x- 06  min   tlr  8.1      The first stepsshould say that it is a class file referring to another class (it
                            is a RGO)
X- 07  min   skb  8.3      Add an expected result after the "undo" step
x- 08  min   bob  8.4      This should call out that this has to start with 2 states and a transition between the 2 states.


Code Review on branch 244_class_merge_2 (Bob, Keith, Travis)
-----------
x- MergeWorkbenchAdvisor.java   Think about if perhaps this can/should be refactored and shared with the similar code in 
                                model.compare/ModelMErgeProcessor.java so there is not duplications.
                                It is not completely duplicate, but it may be good to do something to show that 
                                the 2 places are similar so when one is modified the other s not missed.
  TRL: Created a static method in ModelContentMergeViewer that is used by both locations.
  
x- ModelMergeProcessor.java::adjustPersistenceOrdering() - the new parameter that was added is not being used, 
                              remove it.                                
x- ModelMergeProcessor.java::handlePostCreation() - Around line 727 where there 
                              is a test for NoEventTransition, investigate to see if we need a similar test for
                              creation transition.
  TRL: Same test is required for Creation Transitions, where there is no SEME data required.
                              
x- ModelMergeProcessor.java::line 813 - Investigate this change and retest
  TRL: Change was removed, tests still pass.
  
x- ModelCompareContentProvider.java:getChildren()   - Stopped using getLoadedGraphicalModelsForElements, but not sure why.  Investigate.

  TRL: This was an attempt at providing a performance increase.  There is no notable performance increase and the tests still pass.           
x- ModelCompareContentProvider.java::getAdjustedLocationForSlot - This was modified to fix the
                             ordering issue that Bob saw while onsite in Montreal.  The
                             document is not currently describing this and needs to.
                             Code should also be commented better.
  TRL: The document describes the change, but the code was updated to comment the function of the method.

x- ModelStructureDiffViewer.java::inputChanged() - After the refresh, investigate 
                             to see if we should test again to see if the tree 
                             was disposed.

  TRL: It is not believed that the tree could be disposed after the refresh, however a check was added to be safe.  This check has no effect on the behavior or performance.
</pre>
   
No major observations, a re-review is not required.


End
---
