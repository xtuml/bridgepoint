---

This work is licensed under the Creative Commons CC0 License

---

# The default editor for new created operation is MASL editor
### xtUML Project Implementation Note

1. Abstract
-----------
The default editor for new created operation is MASL editor. To change it, you
need to select Activity once from drop menu (and write some OAL), so the next
time you double click on it, it will open Activity Editor.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8879 The default editor for new created operation is MASL editor](https://support.onefact.net/issues/8879)

3. Background
-------------
I struggled to reproduce this problem for quite a while. The expected behavior
is that if an activity is populated with both MASL and OAL text, or if both are
empty, the preference is consulted to open the correct editor. In this bug, even
when both MASL and OAL were empty, the MASL editor was being opened regardless
of the preference setting.

I tested both on Windows and Linux. I was unable to reproduce the problem
behavior on Linux but could consistently reproduce it on Windows. I looked in
the `ExplorerView` class which is where the editor decision is made. I found
that we have some outdated code there that checks existence of MASL files using
the old scheme of file naming. From inspection I postulated that Windows must
always be reporting that the file exists (and therefore MASL is populated) and
so the preference is bypassed. This code must be removed because it is obsolete
(and causing problems).

The desired behavior at this point is another question. At this point, we have
not promoted some of the MASL work that allows us to distinguish whether an
activity contains MASL. Because of this, the new behavior shall be to follow the
preference to open the correct editor every time.

We will revisit this behavior when we integrate the MASL work back into master.

4. Requirements
---------------
4.1 If the "Default Action Language Dialect" is set to "OAL", the OAL editor
shall open on double click of an activity  
4.2 If the "Default Action Language Dialect" is set to "MASL", the MASL editor
shall open on double click of an activity  

5. Work Required
----------------
5.1 Removed code that checked file existence and instead checked the preference
every time

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
I tested on Windows and Linux and verified that the errant behavior that I saw
before is no longer presented.

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint
Branch: 8879_default_editor_bug

<pre>

 src/org.xtuml.bp.ui.explorer/arc/create_explorer_view.inc | 24 ------------------------
 1 file changed, 24 deletions(-)

</pre>

End
---

