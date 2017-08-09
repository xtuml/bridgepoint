---

This work is licensed under the Creative Commons CC0 License

---

# Rename of masl datatype fails
### xtUML Project Implementation Note

### 1. Abstract

Test #1 in [[2.2]](#2.2) fails.

### 2. Document References

<a id="2.1"></a>2.1 [#9345 Rename of masl datatype fails](https://support.onefact.net/issues/9345)  
<a id="2.2"></a>2.2 [#9138 MASL rename/refactor test](https://support.onefact.net/issues/9138)  
<a id="2.3"></a>2.3 [#8261 MASL refactor design note](8261_masl_refactor/8261_masl_refactor_dnt.md)  

### 3. Background

The main issue here is how the reloading and persisting of the model data
occurs during a refactor operation. Read section 6.3.1 of [[2.3]](#2.3) to
understand the order in which things happen for a refactor. 

It is very important that the file change listeners are _diabled_ during a
refactor operation so that model elements are not prematurely reloaded before
persistence of the structural change. The `PersistableModelComponent` (i.e.
`.xtuml` file) that contains the renamed element gets reloaded, but because all
of the file changes are dropped, if the MASL refactor affected action bodies in
other `PersistableModelComponent`s, they will not get reloaded.

Three options for a solution:
1. Buffer the file change events while the listeners are disabled. Replay and
   handle them after refactor is complete.  
2. Extend MASL refactor utility to maintain a list of affected
   `PersistableModelComponent`s. After refactor is complete, reload these PMCs.  
3. Reload the entire model after refactor is complete.  

Option 1 and 2 above are more involved and complicated. It was determined that
option 3 is the best choice because it is an extremely small change and because
refactor is already a processing intensive operation, the added time to reload
the model will not make a perceivable difference in performance. Additionally,
this reload only occurs after a successful MASL refactor, so OAL users will
never be affected by it.

### 4. Requirements

4.1 Test #1 in [[2.2]](#2.2) shall pass.  

### 5. Work Required

5.1 Update `ComponentTransactionListener.java` to reload the whole system after
a refactor and not just the PMCs affected by the model change.  
5.2 Update `loadComponentAndChildren` in `PersistableModelComponent.java` to
take additional parameters to specify whether or not the load is a reload.  

### 6. Implementation Comments

6.1 During this work, a typo was fixed in the welcome plugin  

### 7. Unit Test

Test #1 in [[2.2]](#2.2).

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9345_masl_datatype_rename  

<pre>

 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/ComponentTransactionListener.java | 39 +++++++++++++++++++++++----------------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/PersistableModelComponent.java    | 34 +++++++++++++++++++---------------
 src/org.xtuml.bp.welcome/introContent.xml                                            |  6 +++---
 3 files changed, 45 insertions(+), 34 deletions(-)

</pre>

### End

