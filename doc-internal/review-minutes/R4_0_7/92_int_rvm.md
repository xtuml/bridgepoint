---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Prevent parse errors when finding more than one external entity
### xtUML Project Review Minutes

Reviewed:  92_int.md  <id>  
Present:  TL, BM, KB

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   BM  3   add a reference to the recent change
_- 02  min   BM  5   list the functions affected
_- 03  min   TL  6   raise an issue to include EEs in the integrity checker

Code review:
- In OAL Validation Utility Functions::is_valid_bridge(), on line 40, first do a 
"select any EE" to get the first EE found, then we can leave the parse error in place that exists already


</pre>
   
No major observations, a re-review is not required.


End
---
