---

This work is licensed under the Creative Commons CC0 License

---

# Operators `+`, `-`, `and` and `or` of instance handle sets are missing in OAL.
### xtUML Project Design Note

### 1. Abstract

Set operators for instance sets are supported in RSL but not in OAL. This issue
is raised to analyze and implement support in OAL. Verifier and model compiler
support will also be considered.

### 2. Document References

<a id="2.1"></a>2.1 [#5007 Operators +, -, and and or of instance handle sets are missing in OAL.](https://support.onefact.net/issues/5007)  
<a id="2.2"></a>2.2 [#5007 analysis note](5007_set_operations_ant.md)  
<a id="2.3"></a>2.3 [#10045 Sortie-1 SRS](https://docs.google.com/document/d/124tp5O8PvCHCDZUDLX173c0B8u4N9d7CXEF-X8Voszw/edit) Internal requirements document for the Sortie project part 1  
<a id="2.4"></a>2.4 [Python 2 docs: operator precedence](https://docs.python.org/2/reference/expressions.html#operator-precedence)  
<a id="2.5"></a>2.5 [#8287 Add support for instance reference set addition](https://support.onefact.net/issues/8287)  
<a id="2.6"></a>2.6 [#8287 Github pull request](https://github.com/xtuml/mc/pull/109)  

### 3. Background

The reader should review the background section of [[2.2]](#2.2).

In addition to the three set operations covered in the background section of
[[2.2]](#2.2), the reader should be familiar with the following:

* The **symmetric difference** (disjunctive union/disunion) of sets A and B (`A
  ^ B`) is the set of all elements that are in set A or in set B but _not_ both.
  Symmetric difference is also commutative, meaning `A ^ B == B ^ A`.  
![symmetric_difference.png](symmetric_difference.png)

### 4. Requirements

The following requirements are sourced from the Sortie-1 SRS [[2.3]](#2.3).
They are worded slightly differently than the requirements in the analysis note
[[2.2]](#2.2) because they were reformatted when the Sortie-1 SRS was produced.

4.1 OAL shall support instance set union (addition) expressions.  
4.2 OAL shall support instance set intersection expressions.  
4.3 OAL shall support instance set subtraction expressions.  
4.4 Both instance set types and instance reference types shall be supported for
either operand.  
4.4.1 An operand of instance reference type shall logically be considered an
instance set containing exactly one instance.  
4.5 Operators `+` and `|` shall be supported for union expressions.  
4.6 Operator `&` shall be supported for intersection expressions.  
4.7 Operator `-` shall be supported for subtraction expressions.  
4.8 Verifier shall support the set operations.  

The following requirements are not part of the Sortie-1 deliverable
requirements however they will be satisfied as part of this work.

4.9 OAL shall support instance set symmetric difference (disunion) expressions.  
4.10 Operator `^` shall be supported for symmetric difference expressions.  
4.11 MC-3020 shall support the set operations.  

### 5. Analysis

5.1 Operator precedence

Part of this feature that was not explored in depth in the analysis note is
operator precedence. The operators that are introduced must fit into the
hierarchy of existing operators and have well established rules of precedence
among themselves. Several outside sources were analyzed to make a decision that
fits in with existing tools.

5.1.1 RSL (old generator)

The old RSL generator did not have explicit precedence for binary
operations but required parentheses to explicitly specify order of operations.
This is the way the `rsl2oal` converter utility that is used by MC-3020 works.

5.1.2 `pyrsl`

Version 1.0.0 of `pyrsl` does have inherent precedence of binary operators,
however it wasn't explicitly stated in the docs. Not much further investigation
into `pyrsl` was done to discover the exact ordering. The original designer
stated that little attention was paid to this part of the interpreter design.

5.1.3 Python

Operator precedence in Python was analyzed since set operations are supported
natively (see [[2.4]](#2.4)). The table in the Python docs shows that the `|`
and `&` operators (which are used for union and intersection) are lower
precedence than even addition. The table only cites bitwise operations, so the
actual precedence for the set operations is not clear, however it is assumed to
be the same as the bitwise operations since they use the same operators.

5.1.4 MASL

The most definitive data we have is from MASL. One of the original designers of
the MASL language provided this operator precedence table. The rows are listed
in order from highest precedence (most binding) to lowest precedence (least
binding).

| Expression/operation type        | Operators                                                        |
|----------------------------------|------------------------------------------------------------------|
| Primary                          | (...), literal, name, domain::name                               |
| Extended (postfix, create, find) | a.b, a~>b, a[…], a’b, a(...), find a where (...), create a (...) |
| Navigate                         | a->Rx                                                            |
| Link                             | link a Rx b                                                      |
| Unary                            | +a -a, not a, abs a                                              |
| Multiplicative                   | \*, /, mod, pow, rem, intersection, disunion                     |
| Additive                         | +, -, & (concatenate), union, not_in                             |
| Relational                       | <, >, <=, >=                                                     |
| Equality                         | =, /= (not equal to... Ada heritage!)                            |
| Logical And                      | and                                                              |
| Logical XOR                      | xor                                                              |
| Logical Or                       | or                                                               |
| Range                            | a .. b                                                           |

MASL supports all three of the originally analyzed set operations, however, in
MASL, set subtraction is done by the operator `not_in` (e.g. `A not_in B`
returns a set containing all the elements which are in `A` but not `B`). MASL
also supports "disunion" which is the symmetric difference (or disjunctive
union).

5.1.5 Conclusion

The crispness and precision of the MASL specification in this area is very
valuable. Additionally, the fact that union and difference are additive while
disunion and intersection are multiplicative is consistent with gut feel that
`&` should bind closer than `|`. Support for OAL set operations shall follow the
lead of MASL in precedence rules. Additionally, because MASL supports disunion,
OAL shall support it as part of this work since it will take little extra effort
to achieve.

5.2 MC-3020

Some work has already been done to add set addition to MC-3020 [[2.5]](#2.5).
There were problems with the original implementation. The input sets were
modified by the addition operation. Also, the routine assumed that the two sets
were disjoint (no elements existed in both sets). OAL parser limitations
prevented the feature from being very useful.

The work in this area was merged into master [[2.6]](#2.6). This changeset will
be used to set up the framework for the changes to fully implement the set
operations in MC-3020, however, little of the actual implementation will be
reused.

5.3 Instance reference types

Due to some heritage of the language and weaknesses of instance reference types,
users will be required to "Publish References" to instance reference types in
order to use set operations.

Study the following diagram. Important classes and associations are highlighted
in blue.

![val2.png](val2.png)  

In xtUML an instance of `Value` represents an expression. The subtypes of
`Value` represent the different types of values that can be expressed. Notice
that every instance of `Value` has an associated data type (R820). An instance
of `Variable` represents a local variable declared in a block (in OAL,
variables are declared implicitly at the first point of reference). The
variable can be one of three subtypes: an `Instance Handle` is a pointer to one
distinct instance. An `Instance Set` is a pointer to a set of zero to many
instances. A `Transient Var` is a variable that is given any other type besides
an instance or instance set type. Notice that for each of these variable types,
there is a corresponding subtype for `Value` (`Instance Reference`, `Instance
Set Reference`, and `Transient Value Reference`).

In xtUML, instance references and instance sets can only be used to type
attributes, structure members, parameters, and return types if the references
are "published". Publishing references creates two explicit data types in the
same package as the class -- one for instance reference (`inst_ref<ClassName>`)
and one for instance reference set (`inst_ref_set<ClassName>`). Once these data
types are created, they can be assigned using the type chooser.

Consider that even if types are not published for a class, a user may select
instances of that class and store them in a set variable. The user can then
subsequently assign the value of the set variable to another variable.
```
select many foos from instances of FOO;
foos1 = foos;
```

Remember that every value has an associated type. If instance reference types
for `FOO` are not published, what is the type of the right hand side value of
the assignment in line 2 above? The answer is the that the type is the special
instance set core type `inst_ref_set<Object>`. This type and `inst_ref<Object>`
are used to type instance set reference and instance reference values when
instance reference types are not explicitly published.  When the parser has to
check type compatibility for such types, first it checks that the types match
(i.e. both operands are typed `inst_ref_set<Object>`), then it must check that
the underlying classes are the same (you cannot assign a set of `FOO`s to a
variable typed as a set of `BAR`s). The parser navigates through the value
subtype to the backing `Variable` instance, then to the instance of `Model
Class`. This navigation is
`val->V_ISR[R801]->V_VAR[R809]->V_INS[R814]->O_OBJ[R819]` for sets and
`val->V_IRF[R801]->V_VAR[R808]->V_INT[R814]->O_OBJ[R818]` for instance
references.

In a model without any published instance reference types, there is no subtype
of `Value` which returns an instance type except `Instance Reference` and
`Instance Set Reference`. For these, type checking can be done as described
above. If however binary operations are extended to be able to return instance
set types from set operations, there will be no way to do type checking. The
type of the `Value` instance across R820 would be `inst_ref_set<Object>`, but
there is no path to navigate from an instance of `Binary Operation` to the
actual instance of `Model Class` associated with its return value. If instance
reference types are explicitly published however, it is trivial to navigate for
type checking since the type related across R820 stores a link to the underlying
`Model Class`

Type checking binary operations without publishing instance reference types is
not an impossible task. It would be possible to write a recursive routine which
would drill down through either the left or right operand of the binary
operation until either an instance reference or instance set is found. This
would be an inefficient design since there is no maximum depth to a binary
operation tree. For example:
```
x = ( ( set1 | set2 ) & ( set3 - set4 ) ) ^ set5
```
If the parser were trying to determine the type of the `^` operation, the
routine may arbitrarily choose the left operand to get the type. Then the
routine would have to determine the type of the `&` operation, etc. until
finally `set1` is reached and the underlying `Model Class` can be found through
the `Instance Set` variable.

Another option would be to modify the metamodel to include some way to store a
reference to the underlying `Model Class` at the level of the `Binary Operation`
instance. There are many different choices to accomplish this, but all would
require more analysis and update of model compilers.

In light of this limitation, the design choice is to simply require users to
explicitly publish any instance reference types for classes they plan to use
with set operations.

### 6. Design

6.1 Implementation flow

6.1.1 Modify parser to allow syntax of set operations and to correctly parse
them into `V_` instances.  
6.1.2 Build BridgePoint.  
6.1.3 Modify MC-Java to support set operations.  
6.1.4 Use new BridgePoint to implement set operations in Verifier (use the set
operations to implement set operations).  
6.1.4 Build BridgePoint again.  
6.1.5 Use set operations in Verifier.  

6.2 OAL parser modification

6.3 MC-Java

6.4 Verifier

6.5 MC-3020

### 7. Design Comments

7.1 Set equality

### 8. User Documentation

8.1 The help section "BridgePoint UML Suite Help > Reference > OAL reference >
Expressions" shall be rewritten to include the most current and correct
information about OAL expressions.  
8.1.1 Current documentation shall be revised and updated.  
8.1.2 A new section shall be created to discuss the use of the set operators.  
8.1.3 A table shall be included that explicitly defines the order of operations
in OAL.  

### 9. Unit Test

9.1 The existing unit tests shall pass.  

### End
