---

This work is licensed under the Creative Commons CC0 License

---

# Ranges of Data Types  
### xtUML Project Review Minutes

Reviewed:  https://github.com/cortlandstarrett/bridgepoint/blob/5005_range/doc-bridgepoint/notes/5005_range/5005_range_int.md
Present:  Bob, Keith, Levi, Cort

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   BM   general  Add a note that there is no design note for this work.
_- 02  min   BM   2        make sure all links work
_- 03  min   CS   5.1.1    link the picture into this note (or update and replace)
_- 04  min   KB   5.1.1    "A range has minumum and maximum values." > "A range may have may have minimum and/or maximum values."
_- 05  min   LS   5.2.1    Are UDTs on supported types supported?  What about if these UDTs are nested? Is a user doc updated needed?  
_- 06  min   KB   5.2.1    Reword sentence about how float validation happens.  Say something instead about the class that is used, InputValueValidator?
_- 07  min   LS   5.2.2    Strike this whole paragraph?  At a minimum strike first sentence and reword rest.
_- 08  min   LS   5.2.2    Could say something about added T_TNS and T_TPS entries added as childrent of UDT so they show in the tree
_- 09  min   LS   test     Add tests that exercise int to real and real to int
_- 10  min   CS   8        Update user doc to note that if you change the type of a ranged UDT to a non-convertible value an existing range will be deleted.
_- 11  min   

</pre>
   
No major observations, a re-review is not required.


Code review
<pre>
_- 01  min   BM   Let's go ahead and remove the archetype changes that handle capitalization in generated functions since they are not needed
_- 02  min   KB   In RangeContributionItem:63, parenthesize to make sure precendence rules are right.  Also, does this allow multi-select of int and EDT and edit range of them at the same time?
_- 03  min   KB   Turn off execute bit on file permissions of DataTypePackage.md
_- 04  min   KB   In RangeContributionItem, the double if on 31 and 34 should really be an if/else.
</pre>

End
---
