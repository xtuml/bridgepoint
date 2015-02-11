---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Import Mentor CQ data into Redmine  
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/master/doc-internal/notes/41_import_cq_data.dnt.md 6b27136   
Present:  Bob, Keith, Cort, Jason

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   BM   title    This is a design note, not analysis
_- 02  min   BM   2        Add reference to the issue for the import of the SR data
_- 03  min   BM   6.1.1    Add a little more detail about the fact that we are cutting columns and what the columns are
_- 04  min   BM   6.1.1    Add more detail about what the awk script is doing
_- 05  min   KB   6.1.2    Capture screenshot for MySQLWorkbench configuration, attach to issue 41, add reference to it in section 2
_- 06  min   BM   2        Add reference to MySQLWorkbench homepage
_- 07  min   BM   6.1.2    Add notes about how the data had to be massaged/updated when prepping and importing it
_- 08  min   BM   6.2      Call out the fact that we're basically repeating the same steps done in 6.1 but the data is going into a different table
_- 09  min   BM   6.3      Add the info about the data massage being done here
_- 10  min   KB   7.1      Expand it a bit more with note that the data can be accessed with MySQLWorkbench query.  Show a screenshot how this is done.
_- 11  min   JR   8        Note that the data can be checked by looking at the number of rows.  Note any checks done, even if small that were done
_- 12  min   JR   general  The related issues section in the redmine issue is not properly showing the related items after import.  Needs to be fixed.
_- 13  min   BM   7.1      Add a note about how some fields are concatenated when put into the redmine note field
_- 14  min   BM   general  Note that the related-to item timestamps are made up and "time of import" was used
_- 15  min   JR   general  Note that the dts # was concatenated with the description on the original description field

</pre>
   
No major observations, a re-review is not required.


End
---
