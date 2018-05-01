---

This work is licensed under the Creative Commons CC0 License

---

# Analyze UDT range enforcement
### xtUML Project Analysis Note

### 1. Abstract

Quoted from the issue:  
>It would be very powerful to be able to define min and max values for
>data types, of core integer and real types.  Initially it could be
>enough only to add the data into the model and store them in the
>metamodel.  The ranges can then be used by model compilers.
>The ranges should be added to `S_CDT` and `S_UDT` of types
>integer and real types.
>
**>Later on the ranges can be used for run time checks during Verifier
>execution.**

### 2. Document References

<a id="2.1"></a>2.1 [5005](https://support.onefact.net/issues/5005) Ranges of data types  
<a id="2.2"></a>2.2 [analysis note](https://github.com/xtuml/bridgepoint/doc-bridgepoint/notes/5005_range/5005_range_ant.md) Ranges of data types  
<a id="2.3"></a>2.3 [10220](https://support.onefact.net/issues/10220) Support UDTs based on EDTs in MC-3020  
<a id="2.4"></a>2.4 [10221](https://support.onefact.net/issues/10221) Analyze UDT range enforcement.  
<a id="2.5"></a>2.5 [implementation note](https://github.com/xtuml/bridgepoint/doc-bridgepoint/notes/5005_range/5005_range_int.md) Ranges of data types  

### 3. Background

The reader should first be familiar with the analysis note [2.2] and
implementation note [2.5] for issue 5005 [2.1].

To scope issue 5005 [2.1], enforcement of range constraints

### 4. Requirements

See [2.2].

### 5. Analysis

5.1 Analyze and document enforcement in Verifier.  

5.4.1 R3307  
In Verifier, Runtime Value is a key class used to store and move information
in the runtime instances of an executing model.  `R3307` links a Runtime Value
to a Data Type (`S_DT`).  

5.2 Analyze and document enforcement in model compilers.  
5.2.1 R820  
In MC-3020, Value (`V_VAL`) is the key class dealing with the dynamics of
data in action bodies.  `R820` links Value to Data Type (`S_DT`) (and thus
to User Data Type and Range).  R820 is traversed in 3 places representing
the 3 places where range checking should be implemented.  
5.2.1.1 Assignment Statement  
A value moves from one value to another during assignment.  A value
moves from the right-hand side (rhs) to the left-hand side (lhs) across
the assignment operator ('=').  The left-hand value (lval) receives the
right-hand value (rval).  
In the case of immediate data, constants and enumerators, a range check
is always necessary before assignment.  There may exist situations where
a variable is being assigned that has been previously constrained.  It may
be possible to "optimize out" a range check in some of these situations.
At present, range checks are considered relatively low cost, so this
optimization is not currently recommended.  
5.2.1.2 Binary Operation  
When two values are combined with an operator, a new value is produced.  The
new value type is discovered across R820.
5.2.1.3 Actual Parameters  
5.2.2 Enumeration User Data Type Support  
Presently, MC-3020 does not support Enumerations as the base for User Data
Types.  This support would need to be added to support ranges on UDTs based
from Enumeration Data Types.  An issue [2.3] is raised to track this.  
5.2.3 Default Values  
When the type of a variable is constrained with a Range, this must affect
the default initial value.
5.2.4 User Callout  
When a range error is detected, control needs to be passed to the user.
In MC-3020, the way this is done is with a "user callout" function.  These
callout functions are invoked when an exception condition is detected.
This gives users a "hook" into these exceptional conditions.  There
already exist several user callout functions for exceptions such as
running out of instance, event or timer elements, empty handle usage, etc.
Two new callouts are to be introduced for integer and real value range
errors.


### 6. Work Required

6.1 Item 1  
6.1.1 Example sub-item
* Example List Element

### 7. Acceptance Test

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

### End
