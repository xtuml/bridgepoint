---

This work is licensed under the Creative Commons CC0 License

---

# Move to Source Model Compiler
### xtUML Project Review Minutes

Reviewed:  https://github.com/keithbrown/bridgepoint/blob/8397_move_to_source_mc/doc-bridgepoint/notes/8397_move_to_source_mc_int.md 687a2b8   
Present:  Bob, Cort, Keith   

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   Bob  5        You will need to modify the developers getting started guide where it copies things to setup the workspace.
    KB: Covered in section 8.1
x- 02  min   Bob  5        I thought I recalled a place in the new project wizard where we use the c binary mc as a default, I don't see it called out.
    KB: No, the new project wizard does not set a default MC selection
x- 03  min   Bob  5        I also thought I recalled a place in bp.mc where there is a constant that refers to to bp.mc.c.binary and uses it as a default. I do not see that.
    KB: No, this plug-in defines a string constant for the name, which we still use.
x- 04  min   Bob  5        I think docgen too takes advantage of the bp.mc.c.binary and I did not see it called out.
    KB: This is called out in 5.5
</pre>
   
No major observations, a re-review is not required.


End
---
