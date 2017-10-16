---

This work is licensed under the Creative Commons CC0 License

---

# OAL auto-completion use cases
### xtUML Project Review Minutes

Reviewed:  9749_usecases_phase1.md f674963    
Present:  Levi  

<pre>

--  Num Type  Who  Section     Comment
x - 01  min   LS   5.2.10      This whole section should be stricken since we are not doing any filtering. Perhaps include a reference
                               to section 5.2.20 or 6.2.20 for generic expressions.
x - 02  min   LS   5.2.14      Strike section 5.2.14. In the previous review we decided that ordering is a design concern and not a use
                               case concern and it has been removed from section 6 anyways.
x - 03  min   LS   5.2.16      Strike the last sentence. Again we are not doing any type filtering.
x - 04  min   LS   5.2.18      Change "not empty" to "not_empty" (two places in this section).
x - 05  min   LS   5.2.18      Add a note that "cardinality" only takes instance sets as an operand while "not_empty" and "empty" take
                               both instance sets and instance references.
_ - 06  min   LS   5.2.19.1    I'm not sure we understand each other here.. My intention with the implementation is to provide full
                               signature template for _invocation_ completions (using the preference of course). When the cursor is actually
                               inside the parentheses, the behavior is to provide a list of parameters that have not been used yet and
                               supply them one by one. Do we need to change the implementation to match this analysis? In any case we must
                               understand one another and come to agreement.
x - 07  min   LS   5.2.20      Expressions shall not share the same behavior as beginning of expressions. This is wrong. Statements and
                               expressions are not the same thing. For example you would not expect "x = select any ..." to parse because
                               "select" is a statement, not an expression. Similarly you would not expect "not_empty foo;" to parse unless
                               it was part of a bigger expression. This section needs to be filled out. Use 6.2.20 as a guide because the
                               use cases there are right.
x - 08  min   LS   5.2.20      Numbering issue. There are three 5.2.20s. Make sure when the numbering is resolved that the use cases and
                               analysis still matches.
x - 09  min   LS   6.1.20      Numbering issue. There are three 6.1.20s.
x - 10  min   LS   6.1.19, 6.1.20, 6.1.20      The "end" keywords are listed here in the use cases but not in the analysis. I think we
                                               missed this in the previous review. Do we want to do this? I don't care either way, but
                                               we need to make it consistent with the analysis.
x - 11  min   LS   6.2.22      The reference here doesn't match the new number.
x - 12  min   LS   6.2.4.1.1   Strike.
x - 13  min   LS   6.2.4.1.2   Strike.
x - 14  min   LS   6.2.10      Formatting error. Missing line break.
x - 15  min   LS   6.2.10      Remove "assign" keyword. This is not suggested practice anymore. I also think this appears in the matrix.
x - 16  min   LS   6.2.13.3    Missing the actual use case? 6.2.13.3.1?
x - 17  min   LS   6.2.13.8    Missing the actual use case? 6.2.13.8.1?
x - 18  min   LS   6.2.16      Remove "not".
x - 19  min   LS   6.2.16.1    Note here that "empty" and "not_empty" can also operate on instance references.
x - 20  min   LS   6.2.17      Formatting error. Missing line break.
x - 21  min   LS   6.2.20.3    Add that it must have at least one class-based operation.
x - 22  min   LS   6.2.20.4    Add that it must have at least one bridge.
x - 23  min   LS   6.2.20.6    Add that it must have at least one outbound message.
x - 24  min   LS   6.2.20.6    "port operations" -> "ports". We only propose the port name here. The messages themselves come later.
x - 25  min   LS   6.2.20.7    Strike this. We are not doing type checking.
x - 26  min   LS   6.2.20.7.*  Add item here for "not_empty".
x - 27  min   LS   6.2.20.8    Strike this. We are not doing type checking.
x - 28  min   LS   6.2.20.9    Reword to: "All visible enumeration types".
x - 29  min   LS   6.2.20.10   Strike this. It is covered in my note in section 6.2.16.1.
x - 30  min   LS   6.2.21.2.1  Copy to match 6.2.9.3.1.
x - 31  min   LS   6.2.21.2.1.1  Copy to match 6.2.9.3.2.
x - 32  min   LS   6.2.21.2.1.2  Copy to match 6.2.9.3.3.
x - 33  min   LS   6.2.21.2.*  The numbering in this section is weird. Please make the sections in the previous three comments siblings.

</pre>
   
No major observations, a re-review is not required.

End
---
