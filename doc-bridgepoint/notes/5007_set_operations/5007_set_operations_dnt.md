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

The following requirements are not part of the Sortie-1 deliverable requirements
however they shall be satisfied as part of this work.

4.9 OAL shall support instance set symmetric difference (disunion) expressions.  
4.10 Operator `^` shall be supported for symmetric difference expressions.  
4.11 MC-3020 shall support the set operations.  

### 5. Analysis

5.1 MC-3020

PR for set addition: https://github.com/xtuml/mc/pull/109

This was merged in, but it modified one of the source sets. Also, it was simple concatenation, so there
was opportunity for duplicates. This only supported sets and not instance handles.

TODO

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

TODO

### 7. Design Comments

TODO

### 8. User Documentation

TODO

### 9. Unit Test

TODO

### End
