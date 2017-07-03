---

This work is licensed under the Creative Commons CC0 License

---

# Complete Integrity Checking
### xtUML Project Design Note

### 1. Abstract

Perform complete (referential) integrity checking.

### 2. Document References

<a id="2.1"></a>2.1 [9554](https://support.onefact.net/issues/9554) complete integrity checking  
<a id="2.2"></a>2.2 [633](https://support.onefact.net/issues/633) The "Check model integrity" utility does not capture orphaned referential attributes  
<a id="2.3"></a>2.3 [653](https://support.onefact.net/issues/653) Check model integrity tool doesn't find problem  
<a id="2.4"></a>2.4 [8514](https://support.onefact.net/issues/8514) Add a preference to disable the model integrity checker  
<a id="2.5"></a>2.5 [test_consistency.py](https://github.com/xtuml/pyxtuml/blob/master/tests/test_xtuml/test_consistency.py) pyxtuml consistency checker  
<a id="2.6"></a>2.6 [9554 Integrity Analysis Note](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/9554_integrity_ant.md) 9554 Integrity Checker Analysis Note  

### 3. Background

[[2.6]](#2.6)  

### 4. Requirements

4.1 (placeholder to match SRS)  
4.2 The metamodel instance population of the application model shall be
interrogated for the presence of all required association links.  
4.3 The values of referential attributes of related metamodel instances
shall be asserted to match the value of referred to identifier attributes.  
4.4 Referential integrity assertion shall be applied to the entire
metamodel instance population of the application model being edited.  
4.5 Assertion failures shall be reported clearly.  
4.6 The xtUML metamodel shall be repaired to fix specific cardinality
errors.  
4.7 The functionality of the checker shall be available from the user
interface.  
4.8 The functionality of the checker shall be available from the command
line.  

### 5. Analysis

5.1 Current Integrity Checker  
The current integrity checker is an _ad hoc_ rule assertion utility.
Various model elements in the ooaofooa have an operation called
`checkIntegrity`.  These operations check for consistency in application
specific ways.

There is a model to support tracking issues detected by the Integrity Checker.
The model is called 'Model Integrity' and is formed to two classes named
'Integrity Manager' and 'Integrity Issue'.

5.2 Referential Integrity  
The Integrity Checker is not explicitly an enforcer of referential integrity.


### 6. Design

6.1 Generalized Referential Integrity Checking  
Add a utility that interrogates the entire instance population of a model
specifically ensuring that relationship links are present and that
referential attributes are in legal ranges.

6.1.1 uniqueness check  
Verify that within each class extent that all instances are unique from
one another.

6.1.2 link integrity  
6.1.2.1 For each instance, verify that unconditional associations carry
links to instances.  
6.1.2.2 For each instance, verify that non-null referentials attributes
are refering to attribute values in a existing instances.  

6.1.3 subtype integrity  
For each instance participating as a supertype, verify that exactly one
subtype is linked.

6.1.4 referential integrity  
6.1.4.1 Assert that referential attributes match their referred-to
identifier attributes.  
6.1.4.2 Assert that referential attributes not particpating in an
association have appropriate not-participating values.  

6.6 OOA of OOA Repair  
It looks to me like `V_LOC` may be deprecated.  Searches in bridgepoint/src
for `V_LOC` seem to show that it is only ever created (and deleted upon
cleanup) but never accessed.  Checking on this...

6.6.1 R110
is `O_OIDA 1..*-----R110-----* R_RTO - - - 1 O_RTIDA`  
should be `O_OIDA *-----R110-----* R_RTO - - - 1 O_RTIDA`  
6.6.2 R835
is `V_VAR 0..1-----R835-----1..* V_LOC`  
should be `V_VAR 1-----R835-----* V_LOC`  
6.6.3 R4708
is `CL_POR 1-----R4708-----* CL_IIR`  
and seems to be correct, but the instance population may be adding or missing stuff in a test model.  I am guessing this is a model that is not upgraded.
6.6.4 R4709
is `C_PO 0..1-----R4709-----* CL_POR`  
should be `C_PO 1-----R4709-----* CL_POR`  

I find `checkReferentialIntegrity` in the common integrity checker.
This routine uses the getRTOs to find all instances referring to it.
It seems to do most of the checking for instances that _should_ be there.
However, it only checks if it can find them.  It does not check for the correct number of instances or duplicates.


### 7. Acceptance Test

A positive case and a negative case for each requirement above should
be created as an xtUML model persisted as SQL instances (normal xtUML
format data) under an Eclipse project.

Expected results will be defined for each case.

TODO:  finishing defining the tests  

### End
