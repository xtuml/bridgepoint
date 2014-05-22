---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Address compare/merge issues from 4.1.10
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/master/doc-internal/notes/227/227.dnt.md
Present:  Keith,Bob,Travis

<pre>

-- Num Type  Who  Section  Comment
X- 01  min   skb  2        Number [1] "Issue 1".  This should be 227, not 1
X- 02  min   bob  4        Number the requirements
X- 03  min   bob  4        Reword the second requirement
x- 04  min   skb  7         Note the breadcrumbs being left behind for potential future O_OBJ changes and why they are left behind.
x- 05  min   trl  8        The second test is a manual test.  The fact it is manual should be called out.
x- 06  min   bob  5        The mc schema was changed, this should be called out in both dnt and int


Code review:  branch 227
x- 1. Class StateMachineComparable.java was removed, but it is not in the changeset.  The removal should be in the changeset

</pre>
   
No major observations, a re-review is not required.
(or... Major observations were recorded, a re-review is required.)


End
---
