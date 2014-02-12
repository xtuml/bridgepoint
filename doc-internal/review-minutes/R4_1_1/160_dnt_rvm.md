---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Verifier doesn't correctly preserve invocation stack when used in pseudo recursive fashion
### xtUML Project Review Minutes

Reviewed:  160_dts0101022652dnt.md  84c9ed7eaa6c2a705f883eeb45333b42cccb868e  
Present:  NM, BM, KB

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   KB   5.1      Missing " at the end of the paragraph
_- 02  min   KB   5.3      There is no information here or in section 6 about what the result of the investigation is.  Add it.
_- 03  min   KB   8.1      Put the test in Suite 2 instead of Suite 1


Code review
Delete::execute
  line 99   Change comment to // empty loc_ref
  line 104  Change message to "...Cannot locate instance during deletion"
  
</pre>
   
No major observations, a re-review is not required.


End
---
