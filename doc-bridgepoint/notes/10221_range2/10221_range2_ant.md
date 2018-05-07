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

#### 5.1 Ground Common Between Model Compilers and Verifier  

5.1.1 Examples to consider:  
```
// assignment
a = param.rngi;  // rngi has a range, so a inherits it implicitly.
b = a;  // Value of a is not changing, check unnecessary.
c = a;  // a does not need to be re-checked.
d = a;  // a does not need to be re-re-checked.

// unary and binary operations
a = -a;  // Negation can take a out of range, check needed.
b = a + 1;  // ranged value bop non-ranged value, check necessary
c = a + b;  // ranged value bop ranged value, check necessary

// actual parameters
a = ::f( rngi:7 );  // Check is needed on actual parameter.
a = ::f( rngi:a );  // no check needed on actual parameter
b = ::f( rngi: ::f ( rngi:a ) );  // No check needed!
d = a + ::f( rngi:a );  // Range needed on binary operation.
```

5.1.2 Expression Parsing  
The parser assigns the Data Type for the result of a binary operation.
In the case of operations including both ranged and unranged operands,
the parser must choose a result that is not ranged.

To support enforcement of ranges in either Verifier or the model compilers,
the parser must be doing correct type assignment.  This is a pre-requisite
to correct behavior "down stream" in Verifier and MCs.

Inspection of prebuilder output seems to indicate that these type
assignements are not correct for expressions involving values based on
types constrained with ranges.

5.1.3 Asymmetric Ranges  
A range provides for a minimum and a maximum value.  These values are
stored in the metamodel as string data.  An empty minimum or empty maximum
is a legal possiblity.  It is considered valid for a range to constrain
only the minimum or only the maximum value linked to a type.  Therefore,
enforcement shall deal with the possibility of checking values in only
one "direction".

5.1.4 Type Assignment and Conversions  
Rules must be established for the combination of ranged and unranged
type values.  The OAL parser must be extended to correctly assign types
when ranges are involved.  The following scenarios need clear rules:  
- ranged value combined through binary operation with literal value  
- combining two ranged values  
- mixing ranged integers with ranged reals  

#### 5.2 Analyze and document enforcement in model compilers.  

5.2.1 R820  
In MC-3020, Value (`V_VAL`) is the key class dealing with the dynamics of
data in action bodies.  `R820` links Value to Data Type (`S_DT`) and thus
to User Data Type and Range.  R820 is traversed in 3 places representing
3 places where range checking should be implemented.  A fourth location
similar to binary operations is unary operations.  These must also be
considered for range checking.

Note, that traversing `R820` does not automatically imply a requirement
for range checking.  Other factors affect the need.  These factors include
the types involved on "both sides" (destination and source) of the movement
of the value.

For example, in an assignment, when the right-hand side value (rval) is
a variable of the same type (including range) as the left-hand side value
(lval), then the need to check the range is obviated.  The rval already
will be validly within range and thus so will the (new) lval.

5.2.1.1 Assignment Statement  
A value moves from one value to another during assignment.  A value
moves from the right-hand side value (rval) to the left-hand side value
(lval) across the assignment operator ('=').  The lval receives the rval.  

In the case of immediate data, constants and enumerators, a range check
is always necessary before assignment.  There may exist situations where
a variable is being assigned that has been previously constrained.  It may
be possible to optimize out range checks in some of these situations.  As
described above, type comparisons can be used to detertmine when range
checking can be skipped.

5.2.1.2 Binary Operation  
When two values are combined with an operator, a new value is produced.  The
result value type is discovered across R820 as are the value types for the
left-hand operand and the right-hand operation.  The parser will have
determined the type for the result.  If the type is constrained with a range
(which it should be), the model compiler shall enforce it.

5.2.1.3 Numeric Unary Operations  
A negation can put a range-constrained value out of range.  Therefore,
range checking is required on negation.

5.2.1.4 Actual Parameters  
Actual parameters can be immediate or other values not strictly typed
to match the typed parameters defined on the function (or operation, etc).
The type of the actual parameter value is known, and the type expected
as parameter to the invocation is also known.  When the type of the
actual parameter is not the same as the type expected on the signature
of the invocable activity, range checking needs to be employed.

5.2.2 Enumeration User Data Type Support  
Presently, MC-3020 does not support Enumerations as the base for User Data
Types.  This support would need to be added to support ranges on UDTs based
from Enumeration Data Types.  An issue [2.3] is raised to track this.  

To support enumerators as range minimums and maximums, the values supplied
in the user model needs to be converted to its code generation equivalent.

5.2.3 Default Values  
When the type of a variable is constrained with a Range, this must affect
the default initial value.  When the attribute of a class is typed with a
type that includes a Range, the attribute must be initialized correctly
upon creation of the instance.

5.2.4 `range_check` Function  
A function is required to be inserted when range checks are needed in
expressions.  Such a function takes three (3) parameters:  value, Minimum,
Maximum and returns the value.  A function with this signature provides
a means for the target value to be read exactly once.  Reading a value
more than once may affect the behavior of the application when side effects
from reading are present (as with some invocation values).

Different signature `range_check` functions may be required for asymmetric
ranges checking only Minimum or Maximum.  Different `range_check` functions
may be needed for differently sized numeric types.  Casting may be required
for the return value from the `range_check` function.  Consider generating
a `range_check` function for each instance of Range in the application model.

5.2.5 Exception Handling (User Callout)  
When a range error is detected, control needs to be passed to the user.
In MC-3020, the way this is done is with a "user callout" function.  These
callout functions are invoked when an exception condition is detected.
This gives users a "hook" into these exceptional conditions.  There
already exist several user callout functions for exceptions such as
running out of instance, event or timer elements, empty handle usage, etc.
A new callout is to be introduced for range errors.

5.2.6 Alternate Approaches  

5.2.6.1 MC Metamodel Translation Extensions Extensions  
Consideration may be given to adding Range minimum and maximum attributes
to `TE_DT` and/or `TE_PAR`.  There may be ways to carry a counter that
prevents re-checking the value for range.  Perhaps `TE_VAL` could have
a range checking field.  This way, the range could be kept separate
from the value and thus range checking only applied when moving or
changing to a new value.

5.2.6.2 Type System  
An updated type system would make the work for range checking easier.
A portion of what is needed involves understanding compatibility and
convertability.  A hierarchical type system where ranged types are
subtypes of their bases would simplify the task.

5.2.6.3 Array Index Range  
This work does not address index range checking which is a separate
but similar topic.

#### 5.3 Analyze and document enforcement in Verifier.  

Execution and Translation are correlary to each other.  Most of what is
true about one is true about the other.  The model compiler _translates_
the model; Verifier _interprets_ the model.  The two tasks have many
similarities.  The rules of Shlaer-Mellor are employed by both.

The analysis for Verifier will leverage the analysis of the model compiler.
Where the model compiler needs to perform range checking when a translated
value is moved from one expression element to another, similarly the Verifier
needs to perform range checking in corresponding interpreted expression
elements.

5.3.1 R3307  
In Verifier, Runtime Value (`RV_RVL`) is a key class used to store and move
information in the runtime instances of an executing model.  `R3307` links
a Runtime Value to a Data Type (`S_DT`).  This Data Type leads to the Range
(`S_RANGE`) that may be linked to the constrained User Data Type.  

5.3.1.1 Assignment Statement
Like in the model compiler, data moves from one value to another during
assignment.  When moving unranged data from the right side of the assignment
operator (`=`) to the left side, range checking shall be done.  It is also
necessary to do range checking when both sides are ranged but ranged with
different ranges.

5.3.1.2 Binary Operation  
In a binary operation three values are involved: left operand, right
operand and result.  These three values potentially have different types.
The parser will supply a correct type to the result value.  In all cases,
the result of the operation must be checked to be within range.

5.3.1.3 Numeric Unary Operations  
During interpretation, the Verifier moves a runtime value from a single
operand to a result operand.  Range checking must occur.

5.3.1.4 Actual Parameters  
Passing values in Verifier involves placing them into a stack frame before
control is passed to the called function, operation, message or bridge.
An actual parameter has a type, and the parameter in the signature of the
invocable action body has a type.  As in translation, these two types need
to be compared to determine if range checking is required.

5.3.2 User Interface and Configuration  
When range constraints are present on types and applied to data elements
in a model, Verifier can enforce them.  Checking will be enabled by
default.  A means to disable range checking must be supplied.  Consider
accessing this setting in the same vicinity as instance population check
configuration.

5.3.3 Default Values  
Just as in translation, when the attribute of a class is typed with a
type that includes a Range, the attribute must be initialized correctly
upon creation of the instance.

5.3.4 `range_check` Function  
For convenience, reuse and consistency, the strategy of using a
`range_check` function will be used.  The function will centralize the
check of the range and reporting of exceptions.  It will also return the
checked value to be used in place in expressions.

5.3.5 Exception Handling  
When a range error is detected, the user needs to be informed.  A reporting
mechanism based upon the existing instance population checks will be used.


### 6. Work Required

6.1 Recommended Approach  
It is recommended that range checking be performed as values move from
storage location to storage location.  In the model compiler, this is
from `Value (V_VAL)` to `Value (V_VAL)` and supported by type interrogation
across `R820`.  In Verifier, this is `Runtime Value (RV_RVL)` to `Runtime
Value (RV_RVL)` and supported by type interrogation across `R3307`.

As a prerequisite, the parser must be enhanced in the area of type
assignment for values constrained by ranges.

### 7. Acceptance Test

TBD.

### End
