---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Add more information for Instance variables in Variable view such as related instances
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/135_dts0100895768_NM/doc-internal/notes/135_dts0100895768/135_dts0100895768_int.md 9f3f2e34d70fd5b3118c36f9de1c33fc58a049ee  
Present:  Keith, Nehad, Travis

<pre>

-- Num Type  Who  Section  Comment

135 code review
1) BPDebugModelPresentation.java
  x- 01  min     remove lines 169 - 175
2) BPValue.java
  x- 02  min     Remove 312 - 316
  x- 03  min     Remove the TODO on 317, is this an override?
  x- 04  min     Reword the comment on 396 
  x- 05  min     add curly braces for all if statements, especially around line 430 and on
3) General JUnit comment
  x- 06  min    In the places where a "known failure" test was uncommented/re-enabled, change the comment from
// Commenting out known failure tests.  See dts0100656068
to
// This area has been problematic in the past.  See this file's history and dts0100656068.


</pre>
   
No major observations, a re-review is not required.
 
End
---
