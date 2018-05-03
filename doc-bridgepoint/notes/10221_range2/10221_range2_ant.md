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
>**Later on the ranges can be used for run time checks during Verifier execution.**

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

5.1 Examples to consider to stimulate thinking.  
```
a = param.rngi;  // rngi has a range, so a inherits it implicitly.
b = a;  // Value of a is not changing, check unnecessary.
c = a;  // a does not need to be re-checked.
d = a;  // a does not need to be re-re-checked.

a = -a;  // Negation can take a out of range, check needed.
b = a + 1;  // ranged value bop non-ranged value, check necessary.
c = a + b;  // ranged value bop ranged value, check necessary.

a = ::f( rngi:a );  // No check is needed on actual parameter.
a = ::f( rngi:7 );  // Check is needed on actual parameter.
b = ::f( rngi: ::f ( rngi:a ) );  // No check needed!
d = a + ::f( rngi:a );  // Range needed on binary operation.
```

5.2 Analyze and document enforcement in model compilers.  

5.2.2 R820  
In MC-3020, Value (`V_VAL`) is the key class dealing with the dynamics of
data in action bodies.  `R820` links Value to Data Type (`S_DT`) and thus
to User Data Type and Range.  R820 is traversed in 3 places representing
3 places where range checking should be implemented.  

5.2.2.1 Assignment Statement  
A value moves from one value to another during assignment.  A value
moves from the right-hand side (rhs) to the left-hand side (lhs) across
the assignment operator ('=').  The left-hand value (lval) receives the
right-hand value (rval).  

In the case of immediate data, constants and enumerators, a range check
is always necessary before assignment.  There may exist situations where
a variable is being assigned that has been previously constrained.  It may
be possible to optimize out range checks in some of these situations.

5.2.2.2 Binary Operation  
When two values are combined with an operator, a new value is produced.  The
result value type is discovered across R820 as are the value types for the
left-hand operand and the right-hand operation.  The parser will have
determined the type for the result.  If the type is constrained with a range,
the model compiler shall enforce it.

5.2.2.3 Actual Parameters  
Actual parameters can be immediate or other values not strictly typed
to match the types parameters defined on the function (or operation, etc).
The type of the actual parameter value is known, and the type expected
as parameter to this invocation is also known.  When the type of the
actual parameter is not the same as the type expected on the signature
of the invocable activity, range checking needs to be employed.

5.2.3 Unary Operations  
A negation can put a ranged value out of range.

5.2.4 Enumeration User Data Type Support  
Presently, MC-3020 does not support Enumerations as the base for User Data
Types.  This support would need to be added to support ranges on UDTs based
from Enumeration Data Types.  An issue [2.3] is raised to track this.  

5.2.5 Default Values  
When the type of a variable is constrained with a Range, this must affect
the default initial value.  When the attribute of a class is typed with a
type that includes a Range, the attribute should be initialized correctly
upon creation of the instance.

5.2.6 `range_check`  
A function is required to be inserted when range checks are needed.  The
function takes three (3) parameters:  value, Minimum, Maximum and returns
the value.  This way the value can be read exactly once.  Reading the value
more than once may affect the behavior of the application when side effects
from reading are present (as with invocations).

5.2.7 User Callout  
When a range error is detected, control needs to be passed to the user.
In MC-3020, the way this is done is with a "user callout" function.  These
callout functions are invoked when an exception condition is detected.
This gives users a "hook" into these exceptional conditions.  There
already exist several user callout functions for exceptions such as
running out of instance, event or timer elements, empty handle usage, etc.
Two new callouts are to be introduced for integer and real value range
errors.

5.2.8 Alternate Approaches  
Consider adding Range minimum and maximum attributes to `TE_DT` and/or
`TE_PAR`.  There may be ways to carry a counter that prevents re-checking
the value for range.
Perhaps `TE_VAL` has a range checking field.  This way, the
range could be kept separate from the value and thus the range
checking only applied when moving or changing to a new value.

I think that a `range_check` function (or macro) must exist
to reduce touches to the value.
For example, when the value is a function/operation/message/bridge return
value, the invocation can happen once only.


Can we detect when a value is "calculated"?
Can we detect when a value moves from a one type to another?

1) V_LIN, V_LRL, V_LEN moving into another value.
Assignment is only part of the story.
The nature of the LHS relative to the RHS is key.
We need to detect when RHS is not the same as LHS.  This is a promotion check.

For binary operation

An updated type system would make this work easier.

This work does not address index range checking which is a separate but similar topic.

5.3 Analyze and document enforcement in Verifier.  

5.3.1 R3307  
In Verifier, Runtime Value is a key class used to store and move information
in the runtime instances of an executing model.  `R3307` links a Runtime Value
to a Data Type (`S_DT`).  

5.3.2 Expression Parsing  
The parser assigns the Data Type for the result of a binary operation.
In the case of operations including both ranged and unranged operands,
the parser must choose a result that is not ranged.  Also, when two ranged
operands are combined, the result must be ranged.


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
