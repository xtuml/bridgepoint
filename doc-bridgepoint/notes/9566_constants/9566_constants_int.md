---

This work is licensed under the Creative Commons CC0 License

---

# Constant group item visibility
### xtUML Project Implementation Note

### 1. Abstract

User requests:
> Constant groups and their items are visible across projects globally. The
> "namespace" of a constant has to be included in the name to avoid naming
> conflicts. Suggestion is to at least hide the items behind the constant group
> name similar to how enums and enum items are managed.

### 2. Document References

<a id="2.1"></a>2.1 [#9566 Constant group item visibility](https://support.onefact.net/issues/9566)  
<a id="2.2"></a>2.2 [#9566 design note](9566_constants_dnt.md)  

### 3. Background

See [[2.2]](#2.2) section 3

### 4. Requirements

See [[2.2]](#2.2) section 4

### 5. Work Required

6.4 Type checking

TODO there is an issue with types where I can assign a constant value to a
variable that is typed with an enumeration data type. This should show a parse
error.

TODO add test for 6.4

### 7. Unit Test

TODO add test for 6.4

### 8. User Documentation


### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9566_constants  

<pre>

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 9566_constants  

<pre>

</pre>

Fork/Repository: leviathan747/models  
Branch: 9566_constants  

<pre>

</pre>

### End

