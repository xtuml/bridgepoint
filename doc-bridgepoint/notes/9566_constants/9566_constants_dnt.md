---

This work is licensed under the Creative Commons CC0 License

---

# Constant group item visibility
### xtUML Project Design Note

### 1. Abstract

User requests:
> Constant groups and their items are visible across projects globally. The
> "namespace" of a constant has to be included in the name to avoid naming
> conflicts. Suggestion is to at least hide the items behind the constant group
> name similar to how enums and enum items are managed.

### 2. Document References

<a id="2.1"></a>2.1 [#9566 Constant group item visibility](https://support.onefact.net/issues/9566)  
<a id="2.2"></a>2.2 [#9566 analysis note](9566_constants_ant.md)  
<a id="2.3"></a>2.3 [#9501 SRS](https://docs.google.com/document/d/1ZyV-FZt77RThTWFBUm3PfM1jxXL9baSeKQWYzfrmFrE/edit) - One Fact internal document  
<a id="2.4"></a>2.4 [#9738 problem created for constants with the same name in different specifications](https://support.onefact.net/issues/9738)  
<a id="2.5"></a>2.5 [#9739 Constants in array dimensions not visible across model roots](https://support.onefact.net/issues/9739)  

### 3. Background

Constants in OAL are currently parsed as a single identifier that must globally
identify constants in the system (including inter-project references). This can
prove to be a problem for constants that happen to have the same name. Scoping
using the constant specification group name can solve this issue.

Enumerated types are similar to constants in OAL, however enumerations are
scoped by their type name using the syntax `<type name>::<enumerator>`. They are
also always associated with the enumeration data type they are defined in, while
constants are associated with the primitive xtUML type that is assigned to them
(i.e. a constant of type `integer` can be assigned to any variable of type
`integer`).

The analysis note [[2.2]](#2.2) for this work was written roughly and used
internally for sizing. This note provides much more information and should be
considered to supersede the analysis note, however the analysis note is still
valuable for background.

Throughout this note, "constant specification" may be referred to as "CSP" and
enumeration data type may be referred to as "EDT".

### 4. Requirements

Requirements sourced from [[2.3]](#2.3)

4.1 xtUML constants shall be scoped by the name of the constant specification  
4.2 Scoping shall follow the same syntax as scoping enumerations in enumerated
types  
4.3 OAL shall continue to support unscoped constants (if they are globally
unique)  
4.4. BridgePoint model compilers and verifier\* shall not be affected by the new
scoping rules  

_\* verifier was not called out explicitly in the SRS_

### 5. Analysis

See [[2.2]](#2.2) section 5

As mentioned in the analysis note ([[2.2]](#2.2)), enumeration accesses already
exist in the grammar using the syntax we would need to scope constants:
```
ID::ID
```
The design will take a general strategy of extending the enumeration validation
functions to support both enumerations and constants.

To maintain backwards compatibility, the regular path for parsing constants
(`member_access` in the grammar) shall be left alone. This will continue to work
for constants that have globally unique names.

5.1 Validation functions

The OAL parser uses OAL functions in the OOA of OOA to validate and to populate
action language instances. Each production rule can have a `_start`, `_end`, and
`_validate` function. An antlr grammar is generated from a BNF grammar of OAL
and the archetypes that generate this grammar automatically insert invocations
to these OAL functions (correlated by the names of the grammar production
rules).

5.1.1 Validating enumerations

The BNF production rule for accessing enumerations is as follows:
```
enumerator_access
   :
    enum_data_type
    TOK_DOUBLECOLON!
    enumerator
  ;
```

Enumeration accesses are validated/populated in three steps  
1. When `enum_data_type` is parsed, the text is used to search for visible
   enumeration data types with the same name. If exactly one EDT is found, the
   unique ID of the EDT is returned. Otherwise a parse error is created.  
2. When `enumerator` is parsed, the ID from step 1 is used to select the EDT.
   Then an enumerator is selected through the EDT with the same text as the
   parsed token. If an enumerator is found, its ID is returned. If not, a parse
   error is created.  
3. In the `_end` function of the `enumerator_access` rule, the enumerator is
   selected by the ID returned in step 2 and action language instances are
   populated.

These three basic steps will be maintained, but adding in a search for constants
as well.

5.1.2 Issues with lookahead

One issue that arises with parsing two different model element types with the
same syntax is what to do if both are found. Imagine that there exists an
enumeration data type called `directions` containing the enumerators `N`, `NE`,
`E`, `SE`, `S`, `SW`, `W`, and `NW`. There also exists a constant specification
called `directions` containing constants `LEFT` and `RIGHT`. Now imagine the
following line of OAL:
```
direction = directions::LEFT;
```
At first glance it seems straightforward that the value of the constant `LEFT`
should be stored in the variable `direction`, however to the parser it is not as
simple. While parsing the identifier `directions`, the parser must search for
matching EDTs and CSPs. In this example, one of both is found. Which one should
the parser return? If it returns the EDT, then when the identifier `LEFT` is
parsed, no enumeration will be found for the EDT and a parse error would be
created.

One way to solve this issue is by looking ahead at the next token(s) to
determine which path to take. That way the parser could see that although there
is no enumerator `LEFT` for the EDT `directions`, there is a constant `LEFT` for
the CSP `directions`.

Another way to solve this problem is to wait until no enumerator `LEFT` is found
and then "take a step back" to look for a constant specification named
`directions` and search there for a constant named `LEFT`. This second path is
taken in this design because it better preserves the grammar.

### 6. Design

6.1 Naming

Because the `enumerator_access` rule is going to encompass both constants and
enumerators, the naming shall be adjusted to the following:
```
scoped_access
   :
    scoped_data_type
    TOK_DOUBLECOLON!
    scoped_member
  ;
```
References to these shall be updated throughout the grammar.

6.2 OAL validation function changes

As mentioned in section 5.1.1, parsing enumerator values is done in three steps
(which are in different OAL validation functions). Each of these three steps
will be discussed below giving an abstract description of the code for each of
the modified functions. Details of these functions irrelevant to this work shall
be omitted.

6.2.1 `Scoped_data_type_validate`

This function is passed (among other things) the text of the first identifier in
the `scoped_access` production rule. Typically it returns the ID of the
enumeration data type found of the same name. Now it will return the ID of the
EDT or CSP of the same name.

1. Select the containing element where the enumerator access has been parsed  
2. Perform a search for data type elements of the same name  
3. Count the number of enumeration data types discovered  
4. Perform a search for constant specification elements of the same name  
5. Count the number of constant specifications discovered  
6. Calculate the total count by adding the number of CSPs and EDTS  
7. If exactly one EDT or one CSP is found, return its unique ID\*, otherwise
report a parse error  

6.2.1.1 The following table aids in the visualization of the specific error that is
reported based on the number of EDTs and CSPs found.

|                   | EDT count 0   | EDT count 1   | EDT count > 1 |
|-------------------|---------------|---------------|---------------|
| **CSP count 0**   | error 1       | return EDT ID | error 3       |
| **CSP count 1**   | return CSP ID | return EDT ID | return CSP ID |
| **CSP count > 1** | error 2       | return EDT ID | error 3       |

* error 1 - "Cannot find specified enumeration or constant specification
  ->%s<-."  
* error 2 - "Multiple constant specifications found for ->%s<-: <list of CSPs>"  
* error 3 - "Multiple enumerations found for ->%s<-: <list of EDTs>"

\* Note that in the case where exactly one EDT and one CSP are found, it is
impossible to know which one to return (see section 5.1.2). By convention the ID
of the EDT is returned. This case is handled in the next step.

6.2.2 `Scoped_member_validate`

This function is passed (among other things) the ID of the EDT (or CSP) parsed
and the text of the second identifier in the `scoped_access` production rule.
Typically it returns the ID of the enumerator found in the specific EDT with the
same name. Now it will return the ID of the enumerator or constant parsed.

1. Select the EDT by the ID passed in (note that this may be empty)  
2. Select the CSP by the ID passed in (note that this may be empty)  
3. If the CSP is empty, perform a search for it in the containing package. If
   exactly one is found, store it in a local variable  
   * It is necessary to search for a CSP in this function as well due to the
     case described in section 5.1.2 and noted in the previous section  
4. Select an enumerator through the EDT by the same name  
5. Select a constant through the CSP by the same name  
6. If exactly one of two (constant or enumerator) is not empty, return its
unique ID, otherwise report a parse error  

6.2.2.1 The following table aids in the visualization of the specific error that is
reported based on the emptiness or not emptiness of the instances. The table
uses a few local variable references to make things easier to understand.

* `enum` - the enumerator selected through the EDT instance  
* `const` - the constant selected through the CSP instance  
* `edt` - the enumeration data type  
* `csp` - the constant specification  

|                                     | `not_empty enum` | `not_empty edt and empty enum` | `empty edt`       |
|-------------------------------------|------------------|--------------------------------|-------------------|
| **`not_empty const`**               | error 1          | return `const` ID              | return `const` ID |
| **`not_empty csp and empty const`** | return `enum` ID | error 2                        | error 3           |
| **`empty csp`**                     | return `enum` ID | error 4                        | error 5           |

* error 1 - "Found enumerator and constant with the same name->%s<- for ->"+name+"<-"  
* error 2 - "Cannot find enumerator or constant ->%s<- for ->"+name+"<-"  
* error 3 - "Cannot find constant ->%s<- for constant specification ->"+name+"<-"  
* error 4 - "Cannot find enumerator ->%s<- for enumeration data type ->"+name+"<-"  
* error 5 - "Unable to locate enumeration data type or constant specification."  
  _Note: this last error is a BridgePoint error, not a parse error. If the
  previous function succeeds, at least one of `edt` or `csp` should be not empty_

6.2.3 `Scoped_access_end`

This function is passed (among other things) the ID of the enumerator or
constant parsed. At this step, the action language instances are created and
related. Now we must distinguish between constant and enumeration and populate
the appropriate instances.

1. Select the enumerator by the ID passed in  
2. If not empty, create action language instances for an enumerator value  
3. Select the constant by the ID passed in  
4. If not empty, create action language instances for a constant value  
5. Return the value ID  

6.3 Integrity checks

In the `checkIntegrity` operation on the "Symbolic Constant" class, a check is
made that no other constant in the system has a matching name. The message as it
stands is a little bit misleading: "Found another constant under the same
specification with a matching name." This check is made regardless of which
specification the constant is defined in. Because this work opens up constants
to be scoped by their constant specification, this check will be narrowed to
only search within a single constant specification. The message is still
appropriate and will be left the same.

The mismatch between functionality and error message described above has been
raised as a bug ([[2.4]](#2.4) and will be resolved as part of this work.

Additionally, a new `checkIntegrity` operation shall be added to the "Constant
Specification" class which will check to make sure that no two constant
specifications have the same name. An appropriate error message shall be chosen.

6.4 Array dimensions

Symbolic constants of type integer are allowed in array dimensions. The array
dimensions are parsed using regular expressions. If an identifier is parsed, a
search is made for the symbolic constant and its value is assessed. This must be
extended to support scoped constants.

There is a bug in this area that constants defined in separate model roots
(root packages under system) are not visible for the purposes of array
dimensions. An issue has been raised to track this (see [[2.5]](#2.5)), but it
will not be resolved as part of this work as it is outside the scope of the
requirements to fix existing bugs.

### 7. Design Comments

None

### 8. User Documentation

Documentation shall be added to the reference manual to describe this feature.  

### 9. Unit Test

Test cases shall be produced which test scoped constant expressions by parsing
an assignment expression in which a local variable is assigned the value of a
constant. Test cases for each action home to test visibility rules is not
necessary because the routine that handles element visibility
(`collectVisibleElementsForName`) is not modified in this work and is well
covered by the existing unit tests.

9.1 Parse errors

The following cases will be tested for appropriate parse errors

9.1.1 No constant specification (or enumerated data type) exists for the given
name (exercises 6.2.1.1 error 1)  
9.1.2 Multiple constant specifications exist for the given name (exercises
6.2.1.1 error 2 )  
9.1.3 A constant and enumerator exist with the same name and type/specification
name (exercises 6.2.2.1 error 1)  
9.1.4 No constant (or enumerator) exist with the given name, but an EDT and CSP
both exist (exercises 6.2.2.1 error 2 )  
9.1.5 No constant exists with the given name, but a CSP does exist (exercises
6.2.2.1 error 3)  
9.1.6 A constant is referenced (without scoping) where the same constant exists
in more than one CSP  

_Note that the other errors in the tables for 6.2.\*.1 tables are not tested
here because they represent normal parser behavior for enumerated data type
parsing and are out of the scope of this work_

9.2 Value checking

The following valid cases shall be checked for the correct constant value

9.2.1 Two constants with the same name but different CSP names  
9.2.2 CSP and EDT with the same name, but constant with unique name  
9.2.3 Multiple EDTs with the same name, but exactly one CSP with the given name  

9.3 A unit test to test array dimensions with scoped constants shall be created.

9.4 Run existing JUnit tests, see no failures

Test cases shall be implemented as JUnit tests and will be included in the OAL
Parser test suite.

### End
