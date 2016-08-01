---

This work is licensed under the Creative Commons CC0 License

---

# Be Less Picky About Event Numbers
### xtUML Project Implementation Note

1. Abstract
-----------
The integrity checker runs regularly to enforce rules on model elements.
In one case it was too picky regarding state machine event numbers.  Fix this.

2. Document References
----------------------
<a id="2.1"></a>2.1 [8612](https://support.onefact.net/issues/8612) Be less picky about event numbers for polys  

3. Background
-------------
Shlaer-Mellor xtUML restricts the repeating event numbers in the same state
machine.  This is good.  However, polymorphic events introduce an interesting
twist.  An event from a supertype can be listed as an event on a subtype.
In such cases, we do not want to require unqueness of event number.  Only
local events need to be enforced with this rule.

4. Requirements
---------------
4.1 Do not report duplicate event numbers for non-local events.

5. Work Required
----------------
Compare the event number of local events with other local events.
To fix the issue, after a collision has been detected, be sure that
both collided events are local (not non-local).

6. Implementation Comments
--------------------------
6.1 Here is the code change, because it is so small.
<pre>
diff --git a/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml b/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml
index 41c1a6b..6c0c747 100755
--- a/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml       
+++ b/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml       
@@ -1130,12 +1130,19 @@ for each evt in events
                             id:self.SMevt_ID, element:self.convertToInstance());
   end if;
   if(evt != self and evt.Numb == self.Numb)
-    MI_IM::createIssue(sys_id:sys_id, description:
+    // Be sure that neither me nor the iterated event is polymorphic.
+    select one self_sm_nlevt related by self->SM_SEVT[R525]->SM_NLEVT[R526];
+    if ( empty self_sm_nlevt )
+      select one iterated_sm_nlevt related by evt->SM_SEVT[R525]->SM_NLEVT[R526];
+      if ( empty iterated_sm_nlevt )
+        MI_IM::createIssue(sys_id:sys_id, description:
                "Found another event under the same state machine with a " +
                                                           "matching number.",
                                       severity:Severity::Error, name:self.Mning,
                            path:self.getPath(),
                             id:self.SMevt_ID, element:self.convertToInstance());    
+      end if;
+    end if;
   end if;
</pre>

7. Unit Test
------------
7.1 Edit polycalc.  
7.2 Mess with event numbers to get a real collision.  
7.3 Mess with poly numbers to show that it is O.K. to have a poly
with the same number as a subtype event number.

8. User Documentation
---------------------

9. Code Changes
---------------
Branch name: 8612_evt_integrity

<pre>
.../models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml  | 9 ++++++++-
</pre>

End
---

