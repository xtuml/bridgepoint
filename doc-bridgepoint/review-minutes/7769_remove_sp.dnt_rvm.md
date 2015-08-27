---

This work is licensed under the Creative Commons CC0 License

---

# Meta-model cleanup from generic package migration (remove specialized package support)
### xtUML Project Review Minutes

Reviewed:  https://github.com/rmulvey/bridgepoint/blob/7769_remove_sp/doc-bridgepoint/notes/7769_remove_sp/7769_remove_sp.dnt.md b12ff101d307f5e295f68199c3e5a53dec11e1ae    
Present:  Cort, Bob, Keith   

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   Cort abstract "specialize" > "specialized"
_- 02  min   Cort 2        remove "In this section..."
_- 03  min   Cort 3 para 1 consider changing "see dts0100631941.ant" to "see [4]"
_- 04  min   Cort 4.1.1    Move this out of the requirement section.  It is a note about how the 
  work will be done, not a requirement
_- 05  min   Cort 6.2      Note that this is not an exhaustive list.  Other classes will be removed as fallout
_- 06  min   Cort 6.2      Note when such unformalizations shift the attributes...see 6.17
_- 07  min   Cort 6.2      Note here that the resulting schema will be compatible with the old one
_- 08  min   Cort 6.2      Raise a follow on issue to remove the unused/placeholder attributes 
  of the schema later.  Add a requirement to this note as well.
_- 09  min   SKB  6.6      Formatting issue for 6.6.x.  Also, there are two 6.6 items
_- 10  min   SKB  6.7.1    "differnt" > "different"
_- 11  min   SKB  6.8 & 6.9  "prevent bp.core" > prevent the individual plugins these steps call out
_- 12  min   Cort 6.16     Add a requirement: "Capture performance metrics"
_- 13  min   Cort 6.17     State that all the placeholder attributes we added are of type UNIQUE_ID
_- 14  min   Cort 6.18     "takes" > "task"
_- 15  min   Bob  7.2      Add specific unit tests for this change
_- 16  min   Cort 7.3      Clarify that this is for "instance reference passing"
_- 17  min   Cort 7.10     "tells us we there" > "tells us there"

</pre>
   
No major observations, a re-review is not required.

End
---


