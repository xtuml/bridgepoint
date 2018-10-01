---

This work is licensed under the Creative Commons CC0 License

---

# Analyze UDT range enforcement
### xtUML Project Analysis Note

### 1. Abstract

Quoted from the issue (5005 [2.1]):  
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
<a id="2.2"></a>2.2 [analysis note](../5005_range/5005_range_ant.md) Ranges of data types  
<a id="2.3"></a>2.3 [10220](https://support.onefact.net/issues/10220) Support UDTs based on EDTs in MC-3020  
<a id="2.4"></a>2.4 [10221](https://support.onefact.net/issues/10221) Analyze UDT range enforcement.  
<a id="2.5"></a>2.5 [implementation note](../5005_range/5005_range_int.md) Ranges of data types  
<a id="2.6"></a>2.6 [Date and Darwen](http://www.thethirdmanifesto.com/) The Third Manifesto  

### 3. Background

The reader should first be familiar with the analysis note [2.2] and
the implementation note [2.5] for issue 5005 [2.1].

To reduce the scope of issue 5005 [2.1], enforcement of range constraints
was deferred until after an analysis had been performed.  Constraining
types with ranges has been experimented with and implemented in isolated
cases in the xtUML Community.  This work has raised awareness of the value
of the capability as well as the depth and subtlety of its implementation.
The assignment of types must be examined, and varying strategies exist for
range enforcement.  It is challenging to check ranges often enough but not
too often.  It is easy to duplicate range checks.  Alternatives must be
examined.

This note provides an analysis of range enforcement in Verifier and model
compilers.  The note identifies and recommends one enforcement strategy that
is reasonable in the context of the architectures of Verifier and MC-3020.

Even though actual enforcement occurs in the model compilers and in Verifier,
the editor itself has significant responsibility in the support of types
constrained by ranges.  It is the editor, and the parser in particular, that
assigns types to values in statements and expressions in the action language.
In one sense, the Verifier and model compilers have the "easy" task of
enforcing the type rules established by the editor/parser.

It is noted that this work requires a small step in the direction of a more
sophisticated Model of Type and Type System deployment.  Date and Darwen [2.6]
should inform this work even if they do not prescribe the specifics.
Concepts such as hierarchy, assignability, convertibility, operability,
casting, promotion and demotion should be considered.

It is also noted that ranges are one of several flavors of constraint;
other forms of constraint exist.  A general purpose solution for subsetting
types is desirable.

### 4. Requirements

The following requirements come from [2.2].  Requirements 4.6 and 4.7
precipitate this analysis note.

4.1 Range data shall be stored in the metamodel.  
4.2 A minimum setting shall be supported.  
4.3 A maximum setting shall be supported.  
Note that minimum and maximum range settings are `inclusive`, meaning
that the minimum value of the range is the lowest legal value to be
taken by an element linked to the ranged type.  The maximum value of
the range is the maximum legal value.  
4.4 Support ranges on User Defined Types (`S_UDT`).  
4.5 The ability to establish ranges shall be supplied by the editor
user interface.  
4.6 Range constraints shall be analyzed in a note for Verifer.  
4.7 Range constraints shall be analyzed in a note for MC-3020.  

The following requirements serve as additional input to this analysis.
These come from assumptions made by the analyst and are made explicit
here for clarity.

4.8 Range constraint enforcement shall be analyzed for the BridgePoint
xtUML editor.  
Changes to the editor may be required beyond those that simply allow the
definition of a range.  In particular, the OAL parser must be analyzed.  
4.9 Configuration of Range Enforcement  
For each of Verifier and the model compilers, stategies and means of
configuring range checking shall be identified.  Configuration must
include enabling and disabling the run-time checks.  
4.10 Detection of Range Violation  
For each of Verifier and the model compilers, stategies and means of
detecting range violations shall be identified.  
4.11 Reporting of Range Violation  
For each of Verifier and the model compilers, stategies and means of
reporting range violations to the user shall be identified.  

### 5. Analysis

#### 5.1 Ground Common Between Model Compilers and Verifier  

5.1.1 Here are some examples of action language that must deal with values
linked to types constrained with ranges.  This is not an exhaustive list
but illustrates some of the simple scenarios facing an editor, interpreter
and translator once range constraints are introduced into the language.

Examples to consider:  
```
// assignment
a = param.rngi;  // rngi has a range, so a inherits it implicitly.
b = a;  // a and b types match, range check unnecessary.
c = a;  // range check not necessary
d = a;  // range check not necessary

// unary and binary operations
a = -a;  // Negation causes type promotion, check needed.
b = a + 1;  // ranged value binary op non-ranged value, check necessary
c = a + b;  // a and b promoted, result demoted, check necessary

// actual parameters
a = ::f( rngi:7 );  // f returns rngi.  Check needed on actual parameter 7.
a = ::f( rngi:a );  // no check needed on actual parameter
b = ::f( rngi: ::f ( rngi:a ) );  // No check needed!
d = a + ::f( rngi:a );  // Range needed on assignment.
```

5.1.2 Expression Parsing, Type Assignment and Conversions  
The parser assigns the Data Type for the result of a binary operation.
In the case of operations including both ranged and unranged operands,
the parser must assign a correct type to the result of the operation.
To support enforcement of ranges in either Verifier or the model compilers,
the parser must be enhanced to provide correct type assignment in the presence
of range-constrained types.  This is a prerequisite to correct behavior
"down stream" in Verifier and MCs.

Inspection of current prebuilder output seems to indicate that these type
assignments are not correct for expressions involving values based on
types constrained with ranges.

Rules must be established for the combination of ranged and unranged
type values.  The OAL parser must be extended to correctly assign types
when ranges are involved.  The following scenarios need clear rules:  
- a ranged value combined through binary operation with a literal value  
- combining two ranged values  
- mixing ranged integers with ranged reals  

The present model of Data Type in the xtUML metamodel may be simplistic
compared to richer type systems, but it is hierarchical.  User Data Types
are based on types at a higher level in the Data Type model hierarchy.
This can be used in a general sense to manage type 'promotion' and
'demotion'.  Type promotion involves converting a value from a more
constrained type to a less constrained type higher in the type hierarchy.
Type demotion is just the opposite and involves converting a value 
to a more constrained type lower in the type hierarchy.

In general, the type of a value is promoted when it is being combined with
a value higher up in the Data Type model hierarchy.  Type demotion occurs
when a value typed with a type higher in the model of Data Type is
assigned to a value typed with a more constrained type.  Demotion is
exactly where range checking needs to occur.

Note that constrained types may or may not support combination using
arithmetic operators.  Ranged types based from integer and real do not
support arithmetic like their parent types do.  Thus, when two ranged
types are combined in a binary operation, the types of the operands must
first be promoted (at least logically), and the result is the promoted
type (common parent in the type hierarchy).  This implies that two values
linked to the same ranged type produce a result of a promoted type
when combined with addition, subtraction, etc.

5.1.3 Asymmetric Ranges  
A range provides for a minimum and/or a maximum value.  These values are
stored in the metamodel as string data.  An empty minimum or empty maximum
is a legal possibility.  It is considered valid for a range to constrain
only the minimum or only the maximum value linked to a type.  Therefore,
enforcement shall deal with the possibility of checking values in only
one "direction".


#### 5.2 Range Enforcement in MC-3020  

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
(lval), then the need to check the range is obviated.  No type demotion
is occurring.  The rval already will be validly within range and thus so
will the (new) lval.

5.2.1.1 Assignment Statement  
A value moves from one value to another during assignment.  A value
moves from the right-hand side value (rval) to the left-hand side value
(lval) across the assignment operator ('=').  The lval receives the rval.  

In the case of immediate data, constants, and enumerators, a range check is
always necessary before assignment.  Literal values are typed with core
native types very high in the hierarchy of the model of Data Type.  There
may exist situations where a variable is being assigned that has been
previously constrained.  As described above, type comparisons are used
to determine when range checking is necessary.

5.2.1.2 Binary Operation  
When two values are combined with an operator, a new value is produced.  The
result value type is discovered across R820 as are the value types for the
left-hand operand and the right-hand operand.  The parser will have
determined the type for the result.  If the type is constrained with a
range, the model compiler shall enforce it.

5.2.1.3 Numeric Unary Operations  
A negation of a ranged type value results in a type promotion.  This can
put a range-constrained value out of range.  Therefore, range checking is
required on negation of values typed with ranged UDTs in the context of
an assigment or parameter passing.

5.2.1.4 Actual Parameters (`V_PAR`)  
An actual parameter (instance of `V_PAR`) is logically similar to an assignment.
Actual parameters can be immediate or other values not strictly typed to
match the typed parameters defined on the function (or operation, message,
event, etc.).  The type of the actual parameter value is known, and the
type expected as parameter to the invocation is also known.  When the type
of the actual parameter is not the same as the type expected on the
signature of the callable activity and when demotion is occuring, range
checking needs to be employed.

5.2.2 Enumeration User Data Type Support  
Presently, MC-3020 does not support Enumerations as the base for User Data
Types.  This support would need to be added to support ranges on UDTs based
from Enumeration Data Types.  An issue [2.3] is raised to track this.  

To support enumerators as range minimums and maximums, the values supplied
in the user model needs to be converted to its code generation equivalent.

5.2.3 Default Values  
When the type of a variable is constrained with a Range, this may affect
the default initial value.  When the attribute of a class is typed with a
type that includes a Range, the attribute must be initialized correctly
upon creation of the instance.  Clear rules about unitialized default
values must be established for attributes linked to ranged types.

5.2.4 `range_check` Function  
A function is required to be inserted when range checks are needed in
expressions.  Such a function may take three (3) parameters:  value,
Minimum, Maximum and returns the value.  A function with this signature
provides a means for the target value to be read exactly once.  Reading a
value more than once may affect the behavior of the application when side
effects from reading are present (as with some invocation values).

Different signature `range_check` functions may be required for asymmetric
ranges checking only Minimum or only Maximum.  Different `range_check`
functions may be needed for differently sized numeric types.  Casting may
be required for the return value from the `range_check` function.
A `range_check` function may be generated for each instance of Range in
the application model and based on the requirement for demotion in the
body of action language.  Multiple different range checking functions for
the same Range may be required if demotion occurs from different types.
This would result in different types for the input parameter(s) to the
`range_check` functions.

5.2.5 Exception Handling (User Callout)  
When a range error is detected, control needs to be passed to the user.
In MC-3020, the way this is done is with a "user callout" function.  These
callout functions are invoked when an exception condition is detected.
This gives users a "hook" into these exceptional conditions.  There
already exist several user callout functions for exceptions such as
running out of instance, event or timer elements, empty handle usage, etc.
A new callout is to be introduced for range errors.

5.2.6 Marking  
Marks will be needed to enable and disable run-time range checking in the
generated code.

5.2.7 Alternate Approaches  

5.2.7.1 MC Metamodel Translation Extensions Extensions  
Consideration may be given to adding Range minimum and maximum attributes
to `TE_DT` and/or `TE_PAR`.  There may be ways to carry data that tracks
checking the value for range.  Perhaps `TE_VAL` could have a range
checking field.  This way, the range could be kept separate from the value
and thus range checking only applied when moving or changing to a new
value.

5.2.7.2 Type System  
An updated type system would make the work for range checking easier.
A portion of what is needed involves understanding compatibility and
convertibility (for promotion and demotion).  A hierarchical type system
where ranged types are subtypes of their bases would simplify the task.

5.2.7.3 Array Index Range  
This work does not address index range checking which is a separate
but similar topic.

#### 5.3 Range Enforcement in Verifier  

Execution and Translation are corollary to each other.  Most of what is
true about one is true about the other.  The model compiler _translates_
the model; Verifier _interprets_ the model.  The two tasks are congruent;
both manifest the rules of Shlaer-Mellor.

The analysis for Verifier leverages the analysis of the model compiler.
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
The parser will supply a correct type to the result value.

5.3.1.3 Numeric Unary Operations  
During interpretation, the Verifier moves a runtime value from a single
operand to a result.  Promotion occurs.  Demotion can occur in the context
of assignment and parameter passing and precipitates the need for range
checking.

5.3.1.4 Actual Parameters  
Passing values in Verifier involves placing them into a modeled stack
frame before control is passed to the called function, operation, message,
event or bridge.  An actual parameter has a type, and the parameter in the
signature of the callable action body has a type.  As in translation,
these two types need to be compared to determine if demotion occurs and
thus range checking is required.

5.3.2 User Interface and Configuration  
When range constraints are present on types and applied to data elements
in a model, Verifier can enforce them.  Checking will be enabled by
default.  A means to disable range checking must be supplied.  Consideration
shall be given to accessing this setting in the same vicinity as instance
population check configuration.

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


6.1 Verifier  
It is recommended that range checking be performed as values move from
storage location to storage location.  In Verifier, this is `Runtime Value
(RV_RVL)` to `Runtime Value (RV_RVL)` and supported by type interrogation
across `R3307`.

6.2 MC-3020  
It is recommended that range checking be performed as values move from
storage location to storage location.  In the model compiler, this is from
`Value (V_VAL)` to `Value (V_VAL)` and supported by type interrogation
across `R820`.

6.3 Editor  
As a prerequisite to supporting range enforcement in execution, the parser
must be enhanced in the area of type assignment for values constrained by
ranges.  The type system must be considered, and type assignments made
in light of promotion and demotion and factoring in supported arithmetic
operators.

6.9 Configuration of Range Enforcement  
6.9.1 In the Verifier, it is recommended that the user interface provide
switches for enabling and disabling range checking.  
6.9.2 In MC-3020, marking should be supplied that enables and/or disables
range enforcement capability.

6.10 Detection of Range Violation  
6.10.1 For the Verifier, the interpreter will be extended to conditionally
check ranges when such checking is enabled.  
6.10.2 For the model compilers, it is recommended that code be generated in
line which calls range checking functions to detect range violation.  

6.11 Reporting of Range Violation  
6.11.1 For the Verifier, the interpreter will report range violations to
the Console much the same way it reports instance check violations today.  
6.11.2 For the model compilers, a user callout function should ve invoked
when a range check violation occurs.  Reporting can be done with print
statements or user code in the callout.  

### End
