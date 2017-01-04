---

This work is licensed under the Creative Commons CC0 License

---

# Deprecate Relocatables Code
### xtUML Project Implementation Note


1. Abstract
-----------
Relocatables have not been supported since the Old Gray Ghost, yet there is
code to support the feature in the present build.  Delete it.

2. Document References
----------------------
[1] [8923](https://support.onefact.net/issues/8923) - Deprecate Relocatables Code  

3. Background
-------------
Relocatables supported a tagged identifier approach to imbedding links
into action language.  The links resolved to structural model elements.
The links were "edited in automatically" by the tool at persistence time.
This feature has not been supported in the Eclipse-based BridgePoint.
A rename/refactor approach will be supported for MASL in 6.0.  A similar
scheme (hopefully) will be applied to OAL some day.

4. Requirements
---------------
4.1 Delete dead relocatables code.

5. Work Required
----------------
5.1 
  
6. Implementation Comments
--------------------------
  
7. Unit Test
------------
7.1 Build BridgePoint.  
7.2 Run it to be sure it launches.  
7.3 Edit an OAL activity.  
7.4 Close the activity.  
7.5 Open it again.  
7.6 (R) Be sure it was persisted.  

8. Code Changes
---------------
<pre>

Fork: cortlandstarrett/bridgepoint

Branch name: 8923_relocatables

xtuml/bridgepoint
 doc-bridgepoint/notes/8923_relocatables_int.md   | 86 +++++++++++++++++++++++++++++++++++++++++++++++++++

</pre>

End
---

