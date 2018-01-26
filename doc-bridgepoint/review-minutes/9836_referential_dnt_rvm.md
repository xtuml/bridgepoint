---

This work is licensed under the Creative Commons CC0 License

---

# Cannot merge referential with identifier when formalising super type
### xtUML Project Review Minutes

Reviewed: 9836_referential_dnt_rvm.md 7d19e13  
Present: Levi, Keith, Cort  

<pre>

    Num Type  Who  Section  Comment
x - 01  minor *    *        General discussion on combining attributes semantics  
                            - concatenate descriptions (with line break between)  
                            - take union of identifiers the attributes are
                              participating in  
                            - prefix name? base name? reference mode? don't know  
x - 02  minor CS   *        It is legal to combine attributes from different
                            identifiers  
x - 03  minor KB   2        Clarify DEI v. SR in the list  
x - 04  minor LS   3        Levi asks why not just delete the base attribute and use
                            the ref as ID? Keith notes that this is a simple model,
                            but if more attributes are reffering to that base
                            attribute, all of the formalization instances must be
                            carried over to the attribute to be combined with.  
x - 05  minor CS   3        "executable UML method" -> "Shlaer-Mellor method"  
x - 06  minor CS   4.1.4    Change order to say type first then identifier  
x - 07  minor LS   4.1      Please number the bulleted list  
x - 08  minor LS   5        Add a section to test existing behavior related to
                            combining attributes that are referred to as part of
                            other formalizations  
x - 09  minor CS   6.1      typo "govern" -> "governing"  
x - 10  minor CS   6.2      spelling error  
x - 11  minor CS   6.2      strike last sentence  

</pre>
   
No major observations, a re-review is not required.


End
---
