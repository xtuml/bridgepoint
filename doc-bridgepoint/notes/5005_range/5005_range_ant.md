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
4.4 User defined types (UDTs) based on integer and real shall be supported.  

optional additional requirements  
4.5 Enforce range constraints in Verifer.  
4.6 Enforce range constraints in MC-3020.  

### 5. Analysis

![Range Metamodeling](range1.jpg) Range Metamodeling

The following are considerations for supporting the above requirements.
Each alternative is briefly described.  The recommendation is stated last.

5.1 `string S_DT.Range`  
Add a Range attribute to Data Type and be done.  The range would carry
a free-form, unparsed, unvalidated string that is edited in Properties
and persisted and propagated in the model data.  No work would be done on
Verifier or model compilers at this time.  A suggestion for syntax would
be to use '..' notation, but it this would not be enforced by the editor.

It is a consideration to place this field on `S_UDT` which would imply
that built-in types, Structured Data Types and Enumerations cannot
be constrained with ranges.

Note, expression-based range constraints may be preferred to explicit
minimum and maximum values.  Expression-based range constraints open
the door to more complex calculations.  However, by definition, a range
is a min and max for a continous segment of values.

5.2 `string S_UDT.Min and S_UDT.Max`  
This is exactly equivalent to the above but separates explicit minimum
and maximum values into separate fields.  The work is the same for the
editor, but the future work in Verifier and model compilers would be
simplified.

Again, it would be a consideration to place this on `S_DT` rather than
on `S_UDT`.  `S_UDT` is expressed here, because it is considered the
preferred solution.

5.3 Range Class  
The Range class would be conditionally linked to Data Type (or User Data
Type).  It would have fields Min and Max.

5.4 Range Class Linked to Values  
In this option, the Range class would be linked twice to Value.  The first
link would realize 'has min'; the second would realize 'has max'.

5.5 Constraint Class  
The Constraint class would be linked to Data Type and then linked to a
subtype Range.  Range would have Min and Max fields to take the place
of future links to Value.

5.6 Recommendation  
The recommendation at this time is to add Min and Max string fields to
User Data Type.  This is simple, easy and flexible.  Since truly modeling
Range is impossible until there is a model of Constraint and we are able to
link directly to Value (Expression), any model is a compromise.  This
compromise is the most direct, easy to implement and easy to upgrade
in the future.


### 6. Work Required

6.1 Item 1  

### 7. Acceptance Test

In this section, list the tests that need to be performed in order to
verify that all the requirements are satisfied. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

### End
