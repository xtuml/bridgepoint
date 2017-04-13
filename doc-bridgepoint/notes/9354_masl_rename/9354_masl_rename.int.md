---

This work is licensed under the Creative Commons CC0 License

---

# Attribute rename in masl project fails
### xtUML Project Implementation Note

1. Abstract
-----------
Attribute rename in masl project fails.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9354](https://support.onefact.net/issues/9354)    
<a id="2.2"></a>2.2 [BridgePoint DEI #9238](https://support.onefact.net/issues/9138) MASL rename/refactor test.  
<a id="2.3"></a>2.3 [All open issues associated with manual test #9238](https://support.onefact.net/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=o&f%5B%5D=relates&op%5Brelates%5D=%3D&v%5Brelates%5D%5B%5D=9138&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&group_by=&t%5B%5D=)  

. Background
-------------
During the manual test pass for BridgePoint release v6.2.2 it was observed that all 
of the MASL rename/refactor tests failed [[2.1]](#2.1). This issue represents one of those 
failure cases. This indivigual failure case was picked as the issue to investigate and 
resolve because it seemed to be the simplest use case. It is likely that the resolution 
for this will fix all the failures. Each failure is denoted by [the open issues 
that are related to the the refactor/rename test](#2.3).  

4. Requirements
---------------
4.1 Test case 13 in the MASL rename/refactor test procedure [[2.2]](#2.2) shall pass.  

5. Work Required
----------------
5.1 In ComponentTransactionListener.java::transactionEnded() under the 
section that the calls masl rename/refactor, the code explicitly 
ENABLED the BridgePoint resource change listeners to force 
reloading of files based on change notifications. This was wrong. 
This setting shall be reversed to assure that the resource change 
listeners are DISABLED.  

The reason this change is required is that masl refactor/rename does 
make changes to files. These changes caused the resource change 
listener to reloading the model file BEFORE "this" BridgePoint 
transaction persisted the in-memory change (persist happens at the 
end of the transactionEnded() procesing). This reload caused the 
in-memory change to be wiped-out before the call to persist(target). 
This was the root cause of masl files being updated and xtuml 
files NOT being updated. This was a race condition betweeen 
the reload and the persist(target) call.  

5.2 As described in 5.1, this problem was a race condition. It was 
made more difficult to find based on the fact that the code that 
persists BridgePoint model files and the code that communicates 
with masl was not clearly seperated. As part of the investigation, 
changes were made to resolve this to aide in this investigation as 
well as future maintenence. This section describes those changes.  

5.2.1 Removed the thread that invokes the masl rename/refactor from 
ComponentTransactionListener.java::transactionEnded(). The fact that there needs to be
a thread, and there does, is not a detail that belongs in this class
(this class is already complex enough). I moved this thread to 
RenameParticipantUtil.java class where such details of masl 
refactor/rename are more appropriate.  

5.2.1.2 In RenameParticipantUtil.java::renameElement() I added synchronous 
execution thread. I did this simple because this thread was previously in
ComponentTransactionListener. This masl rename/refactor does need to run
in a thread, but it is more appropriate that this implementation detail
be in this file, rather than ComponentTransactionListener

5.2.2 In ComponentTransactionListener.java::transactionEnded() there was
a check to see if it is necessary to call masl refactoring or
not. This check was duplicating some complex, hand-craft code. This was
refactored to removed this duplicate code.  Again the details of this 
were put in RenameParticipantUtil.java where one of the 2 copies existed
before this change.  

5.2.2.1  Moved a masl refactor/rename specific routine, getAttributeNameForName, 
out of bp/core/util/RenameActionUtil.java and into RenameParticipantUtil.java 
which is the masl rename/refactor specific class. This allowed me to refactor 
this complex utility function that was duplicating some code from
RenameParticipantUtil.java.

5.2.2.2  In RenameParticipantUtil.java, the renameElement() 
function was refactored, some code that had been duplicated in another 
place was removed. A private class, MASLChangeDelta, was introduced 
to assist in this refactoring to make it clear that the BridgePoint
change Delta and the MASL change delta are unique, but both are 
important structures with independant logic. A MASLChangeDelta using 
a BridgePoint change delta (IModelChange) and the logic for mapping 
from the BridgePoint delta to the MASLChangeDelta is found in the 
new private operation getMASLChangeDelta(IModelDelta). This is 
where the 2 copies of what was duplicated code was brought together 
and simplified.  

5.2.2.2.1 Following is just an observation from the addtion of MASLChangeDelta that 
could be useful to think about in the future.  
It is observed here that if communication between the xtext editor and 
BridgePoint extended the BridgePoint change delta infrastrucure and 
called masl rename/refactor from the BridgePoint RenameAction the entire
changeset could be encapsulated into a refactored BridgePoint AttribtueChangeDelta,
perhaps a new delta that extended AttribtueChangeDelta to add masl artifacts. 
If this was done, the endTransaction() processing would have everything needed
to perform any and all changes made in xtext files that may need updated in 
BridgePoint. While the model is king this is not needed, but in a senario where 
text is king it may be useful to think about this.  

5.3 Moved synchronizeMaslEditors() from ComponentTransactionListener.java into 
RenameParticipantUtil.java so it can be reused. I modified UIUtil.java::visit() 
to callsynchronizeMaslEditors() so that masl editors are refreshed when xtuml 
models are reloaded. An example of when xtuml models get reloaded is on team 
operations.  This fixes a problem where performing a replace-with operation 
after a masl edit is not reflect in an open editor.  

6. Implementation Comments
--------------------------
none  

7. Unit Test
------------
7.1 The test case is called out by Test case 13 of [[2.2]](#2.2)  
7.2 The entire test procedure for [[2.2]](#2.2) shall be run and [all 
tests now passing that were failuring before this work](#2.3) shall be marked 
closed and the pull request for this issue referenced as the resolution.  


8. User Documentation
---------------------
none  

9. Code Changes
---------------
Fork/Repository: rmulvey  
Branch: 9354_masl_attribute  

<pre>

src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentTransactionListener.java
src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/RenameActionUtil.java
src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/RenameParticipantUtil.java
src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/UIUtil.java

</pre>

End
---

