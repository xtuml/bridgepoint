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

### 7. Unit Test

7.1 All existing unit tests shall pass.

### 8. User Documentation

### 9. Code Changes

<pre>
Fork/Repository: cortlandstarrett  
Branch: 9737_fix_ooa

</pre>

### End

