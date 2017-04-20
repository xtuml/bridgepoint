---

This work is licensed under the Creative Commons CC0 License

---

# xtUML Metamodel example project creation hangs
### xtUML Project Implementation Note

### 1. Abstract

In the BP Welcome, when I try to create the xtUML Metamodel example project, it
does some processing, then completely hangs the tool.  A user reported the
same.

### 2. Document References

<a id="2.1"></a>2.1 [#9324 xtUML Metamodel example project creation hangs](https://support.onefact.net/issues/9324)  

### 3. Background

None

### 4. Requirements

4.1 A user shall import the xtUML meta model project without the tool hanging  

### 5. Work Required

See comment #3 in [[2.1]](#2.1) for description of the issue.

5.1 Move invocation of `Get_formatted_body` inside the check for the MASL
dialect  
5.2 Fix model data corruption in both `bp.core` and the xtUML meta model example
model  

### 6. Implementation Comments

6.1 Refreshed the xtUML meta model example model.

### 7. Unit Test

7.1 Import the xtUML meta model project from the quick start page  
7.2 Verify that it imports properly without hanging  

### 8. User Documentation

None

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9324_meta  

<pre>

 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Utility Functions/OAL Validation Utility Functions.xtuml |  4 ++--
 src/org.xtuml.bp.io.core/arc/export_functions.inc                                                                                         | 13 +++++++------
 src/org.xtuml.bp.welcome/models/xtUML_Metamodel.xtuml                                                                                     |  4 ++--
 3 files changed, 11 insertions(+), 10 deletions(-)

</pre>

End
---

