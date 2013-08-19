---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Generator can't locate display key when build ran on a referenced project in a workspace
### xtUML Project Review Minutes


Reviewed:  34_generator_licensing_dnt.md  7c2944ad0dfea64e6dbc229d70481b014f80df95
Present:  Cort,Keith,Bob

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   bob  2      Give brief description/headline for [2][3]
x- 02  min   cort  6.3       typo: additiona
x- 03  min   bob/cort  Design Comment      discussed changing gen_Erate to use a license test at connection time that
                           checks for license exitence without checking out.  Decided not to do this now because of
                           concern that the checkout is really necessary.  This is based on the fact that our JLC
                           implemetnations does have to checkout 2 times on the first connection.
                           
                           Add an issue for this.

x- 04  min   keith test      run this in CLI and GUI

</pre>
   
No major observations, a re-review is not required.



End
---
