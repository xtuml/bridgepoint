---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Review Minutes

Reviewed:  https://github.com/rmulvey/bridgepoint/blob/8321_Model_Element_Move/doc-bridgepoint/notes/8321_Model_Element_Move/8321_Model_Element_Move.dnt.md  82f9dac   
Present:  Bob, Cort, Keith, Jason   

NOTE: This is a re-review of the design note.   

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   KB   4.5      "is enabled only" > "is enabled in the user interface only"
o- 02  min   CS   4.6      This requirement is wrong. Do we keep it here? Do we 
  drop it?   
  
  Note: The requirement is only "wrong" because investigation showed us that there it is 
  not really a problem. This investigation will be described, but I am keeping the requirement
  because it was the work done that allowed us to see there was not a problem that needed
  code change. If fact this was not an insignificant amout of work.
  
x- 03  maj   BM   5.1      First sentence of second paragraph is wrong.  The "existing 
  infrastructure" refers to current Cut/Copy/Paste.
x- 04  min   KB   5.2      Paragraph 6 needs to be rewritten or removed.
x- 05  min   BM   6        Paragraph 1 needs to be rewritten.
x- 06  min   CS   6.1.1    Bullet point 2 needs to be reworded.
x- 07  maj   CS   6.2      This entire section is no longer relevant.  We want to record
  the info, but somehow show it is not applicable anymore.  Can we show it in a different
  font or strikethrough?  Or move it to Design comments and note that it was a considered
  path that isn't used.
x- 08  min   KB   6.2.3    Remove first "The"
x- 09  min   KB   6.2.3.1  Add "work" to "require no additional"
x- 10  min   BM   6.2.3.1  "expception" > "exception"
x- 11  min   CS   6.2.3.1  "BridgePoibt" > "BridgePoint"
x- 12  min   KB   6.2.3.1  "An example of where was" > "An example of where this was"
x- 13  min   KB   6.3      "tree view" > "list box"
x- 14  min   KB   6.3.1    "dialof" > "dialog"
x- 15  min   CS   6.3.2    Note here that we realized we must perform demotion 
  checks on both the source and the target (RTOs & RGOs).
x- 16  min   KB   6.5      "recored" > "recorded"
x- 17  min   CS   8.1.1    "Whej" > "When"
x- 18  min   CS   8.1.2    "pase" > "paste"
x- 19  min   KB   9.4      Test must be extended to test both RTO and RGO downgrades

</pre>
   
Major observations were recorded, a re-review is required.


End
---
