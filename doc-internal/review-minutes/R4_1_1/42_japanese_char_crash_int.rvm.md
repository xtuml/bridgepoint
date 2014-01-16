---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Japanese characters in model can cause generator to crash
### xtUML Project Review Minutes

Reviewed:  42_japanese_char_crash_int.md
           a13084d128709c9d3a1ae0785f4102ee0ab33e53
Present:  Keith, Travis, Bob

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   bob  6.2      This needs to be in the "Indus" release, not "Hercules"

</pre>
   
No major observations, a re-review is not required.

Code Review
------------
_- xtumlmc_build::xtumlmc_cleanse_model
   Add a comment to the new sed command that describes what it is doing, especially the ord($1) part

End
---
