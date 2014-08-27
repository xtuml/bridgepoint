---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Provide more information in tooltip when user hovers over element for a longer time
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/131_dts0100982691_NM/doc-internal/notes/131_dts0100982691/131_dts0100982691_dnt.md  d7ec4551308187e025c86d763b38d6a1facb7274   
Present:  NM, KB, TL

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   TL   4.2      Expand this requirement with more detail about what minimum and maximum mean   
x- 02  min   KB   6.5      There are two sections named 6.3.  The second one should be 6.5
x- 03  min   TL   6.3.1    Formatting issue    
x- 04  min   KB   6.3.6    Strike this       
x- 05  min   TL   6        Add a new line item that allows multiple tooltips to display at the same time
x- 06  min   TL   8        We need automated tests

Code Review:

x- 01 min  TL  BPToolTipHelper.java   make constructor first method in class
x- 02 min  RM  BPToolTipHelper.java   make sure we are disposing the class
                                      properly
x- 03 min  RM  GraphicalEditor        we are instantiating the tooltip helper
                                      but we are not destroying the instance
x- 04 min  RM  BPToolTipHelper        Add comment that this is the default for
                                      maximum size  
x- 05 min  RM  BPToolTipHelper        Add comments for the hard coded values
x- 06 min  RM  All                    Instantiate local variables to prevent
                                      NPEs (either in declaration or
                                            constructor, if its already
                                                instantiated then comment on it)

                                      
</pre>
   
No major observations, a re-review is not required.


End
---
