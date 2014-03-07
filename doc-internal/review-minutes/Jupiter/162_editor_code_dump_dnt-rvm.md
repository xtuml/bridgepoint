---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Dump BridgePoint Code into xtuml/editor 
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/master/doc-internal/notes/162_dts0101036706/162_editor_code_dump_dnt.md
           5298e29d88ab94b56333264d47ea5f9a51e32d2e  
Present:  Keith,Travis,Bob

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   bob  5.4      We should strip out BridgePointLicenseManager.java.
                           If we leave it, people can modify the license code to 
						   just never check a license.  They can then put this
						   change in the licensed version of the tool.
						   
						   We should be able to simple delete the class because we are
						   probably stripping out all the references to it.  In the editor there 
						   really should be no references to this License Manager Class.
						   
x- 02  min   bob  6.2      Add a reference to this HOWTO document that will be used to create the
                           editor again.
						   
x- 03  min   TRL  8        run the unit tests, they should pass
x- 04  min   SKB  7       Put this in a private repository first, for our 
                          internal testing.  After testing we can make it public.

</pre>
   
No major observations, a re-review is not required.


End
---
