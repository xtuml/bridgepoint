---

This work is licensed under the Creative Commons CC0 License

---

# Storing action semantics as files
### xtUML Project Review Minutes

Reviewed:  https://github.com/leviathan747/bridgepoint/blob/8417_action_dialect_files_2/doc-bridgepoint/notes/8417_action_dialect_files/8417_action_dialect_files.dnt.md  af3b3ec   
Present:  Cort, Bob, Levi, Jason, Keith

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   KB   4.2      "have a corresponding a file" > "have a corresponding file"
x- 02  min   KB   4.2      clarify that package contains both Functions and EEs
_- 03  min   CS   5.1.2    Note the we still use *.oal for error markers, so this section could conflict
x- 04  min   LS   5.1.3    Clarify that the xtext editor is a reference to the MASL editor and not part of this work 
x- 05  min   BM   5.2      Make this section a link or copy it and add it to our FAQ as "What is a PMC?"
x- 06  min   CS   6.3      "created on load," > "created on load;"
x- 07  min   BM   6.3      Clarify... does it create all files on first entry, or only the one(s) that are needed?
x- 08  min   BM   6        Consider re-ordering the points in this section for readability
x- 09  min   BM   6.5.1    Another spot to add to the FAQ about the Persistence mechanism
x- 10  min   CS   6.5.2    ActionBody may be logically the same as ActionFileManager.  Consider this.
x- 11  min   BM   6.5.1    Add a brief description of the interaction with file_io.pei.sql and stream.pei.sql
x- 12  min   KB   6.6      Clarify is component means Persistable Model Component or xtUML Component
x- 13  min   BM   6        Consider and record the implications on model compare of these changes
x- 14  min   JR   6.9      Couple of grammar issues in this paragraph
x- 15  min   BM   7        Add "None"
_- 16  min   BM   9        We have OAL test models that exist to have action bodies in every action home.  We should reuse this model (and extend it if necessary)
_- 17  min   BM   9        If no automated test is done, a manual test must be created to capture testing that can be re-run later.
</pre>
   
No major observations, a re-review is not required.

End
---
