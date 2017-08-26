---

This work is licensed under the Creative Commons CC0 License

---

# Constant group item visibility
### xtUML Project Analysis Note

### 1. Abstract

Constant groups and their items are visible across projects globally. The
"namespace" of a constant has to be included in the name to avoid naming
conflicts. Suggestion is to at least hide the items behind the constant group
name similar to how enums and enum items are managed.

### 2. Document References

<a id="2.1"></a>2.1 [#9566 Constant group item visibility](https://support.onefact.net/issues/9566)  

### 3. Background
None

### 4. Requirements

4.1 xtUML constants shall be scoped by the name of the constant specification  
4.2 Scoping shall follow the same syntax as scoping enumerations in enumerated
types  
4.3 OAL shall continue to support unscoped constants (if they are globally
unique)  
4.4. BridgePoint model compilers shall not be affected by the new scoping rules  

### 5. Analysis

5.1 Constant values are created by the parser in the `member_access` rule.  
5.2 A rule exists `emumerator_access` which implements the desired scoping  
5.2.1 This rule could be renamed to `scoped_access`  
5.2.2 The validation routine must be updated to account for the fact that this
could be an access of an enumerator or a constant  
5.2.3 It would be just fine to leave the current code in `member_access` for
backwards compatibility  
5.3 Since constant values are parsed into instances, the model compilers should
not be affected  
5.3.1 MC-Java does not support constants  
5.3.2 MC3020 simply replaces the value of the constant (`CNST_LSC`) where the
instant of constant value (`V_SCV`) is referenced  
5.3.2.1 Because this is parsed and related, MC3020 does not rely on textual
scoping rules  
5.3.3 User model compilers may have to be updated if constants are globally
defined in the target language by unscoped name  
5.4 Naming of the constant specifications must be considered  
5.4.1 Enumerated types are not required by the tool to be a well formed
identifier, however if special characters or spaces are in the name, they cannot
be parsed by the OAL parser  
5.4.2 The design of this issue shall consider restricting the name of constant
specifications  

### 6. Work Required

6.1 Rename `enumerator_access` to `scoped_access` in the grammar  
6.2 Update the validation routine to validate and create constant values as well
as enumerator values  
6.3 Define and run unit tests  

### 7. Acceptance Test

Unit tests will be defined to test parse and code generation of all the
permutations of the following parameters:

7.1 One or many symbolic constants  
7.2 Constants in the same specification or different specifications  
7.3 Scoped and unscoped constant references  
7.4 Constant specifications with and without spaces in the name  

### End
