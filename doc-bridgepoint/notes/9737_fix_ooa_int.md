---

This work is licensed under the Creative Commons CC0 License

---

# Update relations in OOA of OOA  
### xtUML Project Implementation Note

### 1. Abstract

A few associations in the OOA of OOA were discovered to be incorrect
during work on the Referential Integrity Checker.  These are to be fixed.

### 2. Document References

<a id="2.1"></a>2.1 [9737](https://support.onefact.net/issues/9737) Update relations in OOA of OOA  
<a id="2.2"></a>2.2 [9554](https://support.onefact.net/issues/9554) Check Referential Integrity  

### 3. Background

During the work on [[2.2]](#2.2) 3 flaws in the OOA of OOA were noted.
These were committed to be fixed in the work of 9554 but set up as a
follow-on issue, this one.

### 4. Requirements

4.1 Repair the OOA of OOA  

4.1.1 R110
is `O_OIDA 1..*-----R110-----* R_RTO - - - 1 O_RTIDA`
should be `O_OIDA *-----R110-----* R_RTO - - - 1 O_RTIDA`
This is because we now support associations that are not formalized.
A Referred To Class in Association (`R_RTO`) is not required to
have an identifier.

4.1.2 R835
is `V_VAR 0..1-----R835-----1..* V_LOC`
should be `V_VAR 1-----R835-----* V_LOC`
It looks to me like `V_LOC` may be deprecated.  Searches in bridgepoint/src
for `V_LOC` seem to show that it is only ever created (and deleted upon
cleanup) but never accessed.  Checking on this.  For this work, we may
simply repair the conditionality.  An issue will be raised to track this
specific modification to meta-model.  See [[2.9]](#2.9).

4.1.3 R612
is `ACT_BLK *-----R612-----1 ACT_ACT`
should be `ACT_BLK *-----R612-----0..1 ACT_ACT`
This link is only present during parsing and needs to be biconditional.

### 5. Work Required

5.1 Edit the OOA of OOA to make the above changes.  
5.2 Make the corresponding changes to the MC-Java schema.  

### 6. Implementation Comments

One line in the schema was unrelated.  We now have the MC-Java schema
derived from the `xtumlmc_schema`.  The field `setadd` had been added
to `TE_SET` in `xtumlmc_schema`.  I went ahead and pulled that in to
the MC-Java schema for consistency even though though MC-Java does not
use the Translation Extension classes.

It was hoped that the change to the OOA of OOA would be a simple,
surgical change with no fall-out.  That has turned out to be true.
The change set is minute, and all unit tests passed on the first
try.

### 7. Unit Test

7.1 All existing unit tests shall pass.

### 8. User Documentation

The next time the metamodel example project is prepared, these
changes will get picked up.  However, it is not worth making a
special pass on that model just for these conditionalities.

### 9. Code Changes

<pre>
Fork/Repository: cortlandstarrett  
Branch: 9737_fix_ooa
doc-bridgepoint/notes/9737_fix_ooa_int.md                                         | 69 +++++++++++++
src/MC-Java/ooa_schema.sql                                                        |  7 +++--
src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Body/Body.xtuml           |  4 +--
src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Subsystem.xtuml |  2 +-
src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Value/Value.xtuml         |  4 +--
5 files changed, 78 insertions(+), 8 deletions(-)

</pre>

### End

