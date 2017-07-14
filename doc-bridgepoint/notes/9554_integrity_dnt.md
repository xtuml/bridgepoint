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
<a id="2.7"></a>2.7 [Existing Integrity Checker Implementation Note](https://github.com/xtuml/internal/blob/master/doc-internal/notes/28_dts0100970501/28_dts0100970501_int.md) Integrity Checker Implementation  
<a id="2.8"></a>2.8 [9681](https://support.onefact.net/issues/9681) OAL parser exits prematurely on select related role phrases when not reflexive  

### 3. Background

See [[2.6]](#2.6).  

### 4. Requirements

See [[2.6]](#2.6).  

### 5. Analysis

5.1 Current Integrity Checker  
As stated in [[2.6]](#2.6), the current integrity checker is designed
to allow arbitrary rules to be applied and enforced on a model element
by model element basis.  The `NonRootModelElement` base class has an
empty implementation of `checkIntegrity`.  Any model element can override
this operation.  About a dozen elements do override it; most do not.
The elements that do override checkIntegrity perform useful _ad hoc_ checks.

The existing model called 'Model Integrity' may be leveraged in this
present work to report issues discovered by new integrity checking.

5.2 Referential Integrity  
As stated in [[2.6]](#2.6), the current integrity checker is not
explicitly an enforcer of referential integrity even though there
is an operation on the `NonRootModelElement` base class called
`Checkreferentialintegrity`.  This routine was designed to identify
model elements that have been erroneously deleted or omitted from
persistent storage (during a merge).  The routine is detecting the
existence of a proxy with no associated model element.  This is not
sufficient to detect true referential integrity violations such as
missing links and incorrect referential attribute values.

The current `Checkreferentialintegrity` does not traverse sub/supertype
links.

### 6. Design

```
notes and paste-in from requirements (just for now)
Consider prototyping in mcooa.
Consider the menu option in workspace preferences.

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
```


6.0 General Flow
```
For each class in the model do the following:
  Search for duplicate identifiers in the instance extent.
  Match referential attribute values with identifier attribute values.
  Find unconditional formalizer from a participant in a simple association.
  Find unconditional participant from a formalizer in a simple association.
  For an associator in a linked association ensure both ends have an instance.
  From the one end of an associative, find the unconditional associator.
  From the other end of an associative, find the unconditional associator.
  For a subtype, ensure a supertype is present.
  For a supertype, ensure exactly one subtype is present.
```

6.1 Check All Instances  
To accomplish this, select all instances (using `SELECT MANY ... FROM INSTANCES OF ...`)
of all classes in the OOA of OOA.  Exhaustively, iterate over the collections.

6.2

`RTOUtil.getRTOs` checks every class type.  if/else would short-circuit the check.  Checking the most common classes first would further optimize this routine.

getRTOs does not deal with subtypes

6.3 Referential Attribute Values  
MC-Java omits referential attributes and generates read accessors that
reference to base identifier attribute.  Therefore, this integrity check
is automatically inforced by the architecture in the generated code.

Hmm, maybe this is true in MC-Java, but the issue would be in the saved
instance data.
Perhaps if a referential is edited on disk to be the wrong value, one
of the other tests will catch it?  (cuz batchRelate will not work?)

6.4
6.5

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

See [[2.8]](#2.8).  OAL parser exits prematurely on select related
role phrases when not reflexive.  So, I avoided them.

### 7. Acceptance Test

A positive case and a negative case for each requirement above should
be created as an xtUML model persisted as SQL instances (normal xtUML
format data) under an Eclipse project.

Expected results will be defined for each case.

TODO:  finishing defining the tests  
TODO:  consider the scenarios for missing files  

7.2 Missing Link  
7.2.1 Link Flavors  
7.2.1.1 Missing Simple  
7.2.1.1 Missing Simple Reflexive  
7.2.1.1 Missing Associative  
7.2.1.1 Missing Sub/Super  
7.2.2 Orphaned Instance  
7.2.2.1 Orphaned RTO  
7.2.2.2 Orphaned RGO  
7.3 Null Referential (referential integrity)  

7.9 Duplicate Instances (uniqueness test)  

### End
