---

This work is licensed under the Creative Commons CC0 License

---

# Complete Integrity Checking
### xtUML Project Implementation Note

### 1. Abstract

Perform complete (referential) integrity checking.

### 2. Document References

<a id="2.1"></a>2.1 [9554](https://support.onefact.net/issues/9554) complete integrity checking  
<a id="2.2"></a>2.2 [9554 Integrity Analysis Note](9554_integrity_ant.md) 9554 Integrity Checker Analysis Note  
<a id="2.3"></a>2.3 [9554 Integrity Design Note](9554_integrity_dnt.md) 9554 Integrity Checker Design Note  

### 3. Background

See [[2.2]](#2.2).  

### 4. Requirements

See [[2.2]](#2.2).  

4.1 Item 1  
4.1.1 Example sub-item
* Example List Element
  * Example Sub list item

### 5. Work Required

5.1 Generalized Referential Integrity Checking  
Add a utility that interrogates the entire instance population of a model
specifically ensuring that relationship links are present and that
referential attributes are in legal ranges.

5.1.1 uniqueness check  
Verify that within each class extent that all instances are unique from
one another.

5.1.2 link integrity  
5.1.2.1 For each instance, verify that unconditional associations carry
links to instances.  
5.1.2.2 For each instance, verify that non-null referentials attributes
are refering to attribute values in existing instances.  

5.1.3 subtype integrity  
For each instance participating as a supertype, verify that exactly one
subtype is linked.

5.1.4 referential integrity  
5.1.4.1 Assert that referential attributes match their referred-to
identifier attributes.  
5.1.4.2 Assert that referential attributes not particpating in an
association have appropriate not-participating values.  


### 6. Implementation Comments

### 7. Unit Test

Outline all the unit tests that need to pass and describe the method that you
will use to design and perform the tests. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

### 8. User Documentation

Describe the end user documentation that was added for this change. 

### 9. Code Changes

Fork/Repository: cortlandstarrett/bridgepoint   Branch: integrity2  
Fork/Repository: cortlandstarrett/mc   Branch: integrity2  
Fork/Repository: cortlandstarrett/buildmt   Branch: integrity2  

<pre>
Fork/Repository: cortlandstarrett/bridgepoint   Branch: integrity2

Fork/Repository: cortlandstarrett/mc   Branch: integrity2

Fork/Repository: cortlandstarrett/buildmt   Branch: integrity2

</pre>

### End

