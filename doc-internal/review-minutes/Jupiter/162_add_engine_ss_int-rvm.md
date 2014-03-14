---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Dump BridgePoint Code into xtuml/editor Repository - Instance SS Split Baby Step
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/162_add_engine_ss/doc-internal/notes/162_dts0101036706/162_add_engine_ss_int.md 
           ff397167b82f03a47eca13492a8ac950c9f9b3b8  
Present:  Cort, Keith,Bob,Travis,Campbell

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   CMC  5        We could leave SQE and EQE public.  However, Cort notes that these
                           probably shouldn't even be in the meta-model proper.  Therefore, this will
                           not be done.  Put this decision in the note.
x- 02  min   bob  general      We currently strip all I_ instances before model compilation.  We could potentially
                           leave the new public engine instances.  However, we will not do so.
			   We will continue to strip all of I_.
x- 03  min   bob  5        This work removed the 3 unassigned imported classes in the Instance SS
x- 04  min   cds  7       Run schema_gen on the updated model to help check ordering
                           Call this out in the note.
x- 05  min   trl  5      Note this marking in the 2 subsystems being modified:
                           TRANSLATE_FOR_EXTERNAL_USE:FALSE
			We are leaving this as-is, but it could potentially change to true.
			call this out in the note.
x- 06  min   bob  5      If ordering of Subsystems is presumed somewhere in the tool, then introduction of this
                           new SS could cause a problem.  However, discussion about this has revealed no such know places.

</pre>
   
No major observations, a re-review is not required.


End
---
