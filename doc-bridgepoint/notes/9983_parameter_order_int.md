---

This work is licensed under the Creative Commons CC0 License

---

# Incorrect population of parameter list for class-based operation parameters, if user-defined dt from another package is used
### xtUML Project Implementation Note

### 1. Abstract

This issue was originally reported as a comment in issue #9735 [[2.2]](#2.2).
We had hoped that the fix for one would fix the other, and while that ended up
not being the case, the two did turn out to be very closely related.

### 2. Document References

<a id="2.1"></a>2.1 [#9983 Incorrect population of parameter list for class-based operation parameters, if user-defined dt from another package is used](https://support.onefact.net/issues/9983)  
<a id="2.2"></a>2.2 [#9735 Using component external datatype as event data result in "Unassigned Parameter Placeholder"](https://support.onefact.net/issues/9735)  
<a id="2.3"></a>2.3 [#9735 implementation note](9735_unassigned_parameter_placeholder/9735_unassigned_parameter_placeholder_int.md)  
<a id="2.4"></a>2.4 [#9735 explore multi-file updates that should not](https://support.onefact.net/issues/10008)  


### 3. Background

The reader should look at issue #9735 [[2.2]](#2.2) and read the associated
implementation note [[2.3]](#2.3) before continuing.

### 4. Requirements

4.1 Upgrade code shall not result in circularly ordered parameters.  
4.1.1 The procedure described in the Description of [[2.1]](#2.1) shall be used
to verify 4.1  

### 5. Work Required

5.1 Analyzing the problem

Since I already had a hunch that this issue would follow in the steps of
[[2.2]](#2.2), I first set breakpoints to verify that an upgrade routine was
modifying the ordering associations after they had been loaded from the SQL
data. I found that it was in fact being updated by a method in the
`ImportHelper` class called `upgradeElementOrder`. This method is designed to
check if the explicit ordering relationships exist in data. If they do not, the
items are ordered alphabetically. Something was causing this method to think
that the elements did not already have ordering associations populated.

5.2 The failure case

The failure occurred only when a parameter was typed with a user-defined type.
Imagine the following sequence of events:

5.2.1 The class is loaded with all the instances of operations, parameters, etc.  
5.2.2 `bactchRelate` is invoked to link up the loaded instances.  
5.2.3 During `batchRelate`, one of the parameters refers to a type in another
PMC, so that PMC initiates a load (see [[2.3]](#2.3) section 5.3 for more
explanation).  
5.2.4 When the PMC containing the type is finished, `upgradeElementOrder` is
invoked.  
5.2.5 `upgradeElementOrder` starts operating on incompletely linked data from
the first PMC.  
5.2.6 FAIL! The operation thinks elements are not ordered and tries to order
them alphabetically causing terrible inconsistency in data.  

5.3 The fix

Similarly to the fix to issue #9735 [[2.2]](#2.2), the solution is to narrow the
data that is being operated on in the upgrade code. Instead of operating on an
entire instance population, the routine should only touch instance that were
loaded in that particular PMC. This would prevent 5.2.5 (above) from happening.

### 6. Implementation Comments

6.1 Further work

A generalization of the fix here applied to all `ImportHelper`/upgrade code
would be as follows:

> Upgrade code should only operate on instances which were loaded by the current
> PMC load. It should never touch instances that were loaded before or after.

An issue has been raised [[2.4]](#2.4) to analyze other areas in which upgrade
code operates on too broad of a scope.

### 7. Unit Test

7.1 Manual acceptance test

This issue is isolated and is not likely to recur after this fix. For the test,
the steps to reproduce from the issue [[2.1]](#2.1) shall be followed. It shall
be verified that after the fix the failure mode shall not be reproducible.

7.2 Existing unit tests shall pass

### 8. User Documentation

None

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9983_parameter_order  

<pre>

  doc-bridgepoint/notes/9983_parameter_order_int.md                   | 112 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                    |   2 +-
 src/org.xtuml.bp.io.core/arc/import_functions.inc                   |   2 +-
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/ImportHelper.java |  94 ++++++++++++++++++++++++++++++++++++++++++++--------------------------------------------------
 4 files changed, 158 insertions(+), 52 deletions(-)

</pre>

### End

