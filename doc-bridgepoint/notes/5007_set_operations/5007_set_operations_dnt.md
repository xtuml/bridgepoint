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

### 4. Requirements

See the requirements section of [[2.2]](#2.2) for list of requirements. These
requirements are sourced from the Sortie-1 SRS [[2.3]](#2.3).

### 5. Analysis

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
