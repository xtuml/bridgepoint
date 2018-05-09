---

This work is licensed under the Creative Commons CC0 License

---

# Analyze Range Enforcement
### xtUML Project Review Minutes

Reviewed:  ../notes/10221_range/10221_range_ant.md bfc6fe41fea6f38feb1993cdcedb70fa63179221
Present:  Bob, Keith, Levi, Cort

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   Bob  1        "Quoted from issue" point out 5005.
_- 02  min   Bob  2        Double-check links.
_- 03  min   Bob  xxx      Copy in the requirements and clarify the section where the requirements come from.
                           Identify required changes to the editor.
                           Add other requirements that were "in my heart".
                           detect, report, configure (UI)
_- 04  min   Levi 5.1.1    Clean up comments on lines.  Specify that :ff returns rngi.  Fix bop.
_- 05  min   Levi 5.1.3    min and/or max
_- 06  min   Bob  5.2.1.1  Add Oxford comma.  remove 'not'
_- 07  min   xxx  5.2.1.2  operation --> operand
_- 08  min   xxx  5.2.1.3  Range checking may be necessary in the presence of assignment and actual parameters.
_- 09  min   xxx  5.2.1.4  Rules for V_PARs are logically the same as assignment.
_- 10  min   xxx  5.2.1.4  Clarify when range checking is needed (only when type conversion is a demotion).
_- 11  min   xxx  5.2.3/5.3.3    Specify or mention what should happen with enumerator defaults.  What to do?  Requires thought.
_- 12  min   xxx  5.2.4    Consider the input type of the cast (demotion).
_- 13  min   xxx  5.3.1.1  Missing line break.
_- 14  min   xxx  5.3.1.3  result operand --> result  ... and demotion only when assignment or parameters
_- 15  min   xxx  5.3      Clean up Verifier section and mention promotion and demotion.
_- 16  min   Cort 5.2      Make requirement for marking on/off range checking.
_- 17  min   xxx  6        Update this to have editor, Verifier and MC.  Generate sub-points that can go into an SRS.

</pre>
   
No major observations, a re-review is not required.


End
---
