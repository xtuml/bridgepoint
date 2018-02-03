---

This work is licensed under the Creative Commons CC0 License

---

# Ranges of data types
### xtUML Project Analysis Note

### 1. Abstract

"It would be very powerful to be able to define min and max values for
data types, of core integer and real types.  Initially it could be
enough only to add the data into the model and store them in the
metamodel.  The ranges can then be used by model compilers.
The ranges should be added to `S_CDT` and `S_UDT` of types
integer and real types.

Later on the ranges can be used for run time checks during Verifier
execution."

### 2. Document References

<a id="2.1"></a>2.1 [5005](https://support.onefact.net/issues/5005) Ranges of data types  

### 3. Background



### 4. Requirements

4.1 Range data shall be stored in the metamodel.  
4.2 A minimum setting shall be supported.  
4.3 A maximum setting shall be supported.  
4.4 Support for user defined types (UDTs) based on integer and real shall be supported.  

### 5. Analysis


Consider a single string field on `S_DT` (range).
Consider two fields on `S_DT` (min, max).
Consider adding a Constraint class.



### 6. Work Required

6.1 Item 1  

### 7. Acceptance Test

In this section, list the tests that need to be performed in order to
verify that all the requirements are satisfied. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

### End
