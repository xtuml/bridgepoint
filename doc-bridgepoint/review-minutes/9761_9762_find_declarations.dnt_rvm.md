---

This work is licensed under the Creative Commons CC0 License

---

# Introduce a mechanism that allows OAL Editor users to navigate to declarations
### xtUML Project Review Minutes

Reviewed:  https://github.com/travislondon/bridgepoint/blob/9761/doc-bridgepoint/notes/9761_9762_find_declarations/9761_9762_find_declarations.dnt.md 5646462  
Present: Bob, Travis, Keith, Levi    

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   KB   3.1.1    declaration misspelled
_- 02  min   KB   3.1.1    The terms "reference" and "declaration" should not be used interchangably.  Update this section.
_- 03  min   KB   3.1.1    The text in this paragraph should say "reference" instead of "declaration"
_- 04  min   KB   4        "shal" > "shall" (several places)
_- 05  min   LS   4        in the "updated" section change to say "navigate to that model element declaration"
_- 06  min   KB   4.1.x    change "open declaration" to "find declaration"
_- 07  min   KB   general  Mixture of "open declaration" and "find declaration", be consistent
_- 08  min   KB   6.3      Note that the CME will be put with the other BP CM entries
_- 09  min   LS   4.2      last word should be "declaration" here
_- 10  min   LS   5        declaration misspelled
_- 11  min   LS   5        Is there duplication of content here with section 3
_- 12  min   KB   5        We had a discussion about functionality.  We came to the conclusion that selection should be made in ME and the canvas should be opened to show where the element is (at least in some cases)
_- 13  min   LS   5.1.2    General discussion that we don't like this.  We think the ME should be opened automatically.
_- 14  min   LS   6.1.1    reword to not say "shall match"
_- 15  min   LS   6.1.1    Need to have an exhaustive list of all supported statement types here (consider that create and select are missing)
_- 16  min   KB   6.1.1    reword paragraph under 6.1.1.6
_- 17  min   LS   6.1.1    misspelled "complete"
_- 18  min   LS   6.1.2    Need some clarity here.  The text about "type" is confusing.
_- 19  min   LS   6.2      Note that this section is specifically about transient variables and not structural elements
_- 20  min   TL   6.2      Get rid of selection.
_- 21  min   LS   6.2.1    Reword.  Say the V_LOC is used to find the first instance of the V_VAR.
_- 22  min   LS   6        Add some comments to the design about where the logic for this feature will live.  Specifically, this will 
  use a lot of traversals.  It would be good to add this to the OAL in the model and not write this all in Java.
_- 23  min   TL   6.3.1    Note that on Mac this is Cmd+Left Mouse
_- 24  min   KB   6        We had a discussion and came to conclusion that we would rather have "Open Declaration" be shown but grayed out on invalid activations than not shown at all.


In the matrix (eca8863)
_- 25  min   LS   general  Get rid of "D".  D1 is the same as T1-10 and D2 is T11
_- 26  min   LS   general  Add a section for Location
_- 27  min   LS   general  Add explanitory comments to the matrix
</pre>
   
No major observations, a re-review is not required.

End
---
