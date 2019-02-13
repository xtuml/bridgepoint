---

This work is licensed under the Creative Commons CC0 License

---

# Handle exclusion of terminator services from prj file
### xtUML Project Analysis Note

### 1. Abstract

Terminator services in a deployment may be implemented in native code. In this
case, a declaration in the `.prj` file is necessary, but a `.tr` definition file
should not be produced. They may also be excluded all together so the domain
implementation is desired. In this case, no declaration in or definition should
be produced.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #11479](https://support.onefact.net/issues/11479) Main issue.  
<a id="2.2"></a>2.2 [Project SRS (internal document)](https://docs.google.com/document/d/14eUh9kFz_i3o7cYbi_L1BQ7EAxrAq38806vm3PkcnJ8/edit)  
<a id="2.3"></a>2.3 [Meeting notes (internal document)](https://docs.google.com/document/d/1msovMg3UwOr9uxk0XTkLUDjKfU2lJV7uKT5FHIEapkg/edit) Notes from a meeting where this problem was discussed.  

### 3. Background

See [[2.3]](#2.3)

This analysis represents an attempt to clarify requirements after an SRS has
already been accepted. In most cases, "requirements creep" is something the
BridgePoint team works to avoid, however in this case it is believed that the
requirement in question was not sufficiently clear and resulted in a
misunderstanding. The requirement will be clarified here with sub requirements.

### 4. Requirements

4.1 (Copied from R7.2 in [[2.2]](#2.2)) Terminator services shall have a
property to be marked as excluded from generation (currently existing MASL
utilities do not export project terminator services marked with dialect “none”,
this shall be reviewed).  
4.1.1 Terminator service definitions (`.tr` files) may be excluded from
generation to facilitate implementation in the target language.  
4.1.2 Terminator service declarations (in `.prj` file) _and_ terminator service
definitions may be excluded from generation in order to facilitate using the
domain terminator service definition in the generated code.  
4.1.3 A mechanism shall be implemented in the editor to allow the user to
achieve 4.1.1 and 4.1.2.  
4.1.4 The behavior of each service shall remain consistent after re-import of a
terminator.  

### 5. Analysis

5.1 Table

The following table was produced from analyzing the current behavior with
respect to generation.

| Activity Type    | Dialect  | Body (empty/not empty)  | Result                                          |
|------------------|----------|-------------------------|-------------------------------------------------|
| Project term svc | MASL     | not empty               | Included in .prj file; .tr file produced        |
| Project term svc | MASL     | empty                   | Included in .prj file; no .tr file produced     |
| Project term svc | None     | N/A                     | Not included in .prj file; no .tr file produced |
| Domain term svc  | MASL     | not empty               | Included in .mod file; .tr file produced        |
| Domain term svc  | MASL     | empty                   | Included in .mod file; no .tr file produced     |
| Domain term svc  | None     | N/A                     | Included in .mod file; .tr file produced        |
| Domain function  | MASL     | not empty               | Included in .mod file; .svc file produced       |
| Domain function  | MASL     | empty                   | Included in .mod file; no .svc file produced    |
| Domain function  | None     | N/A                     | Included in .mod file; no .svc file produced    |

There are some inconsistencies here between the three types of services here
that should be resolved, however this is out of the scope for the current
project.

The customer has informed us that the current mode of working is to use the
"None" dialect in domains when it is desired to implement a service in native
code. This will play in to the analysis as it will be desirable to stay
consistent with current models.

5.2 Design options

In order to fulfill the requirements outlined in section 4, four options have
been proposed.

5.2.1 Empty MASL actions

One way to distinguish between the two different flavors of exclusion would be
by using empty MASL bodies. Using "None" dialect can result in producing a
declaration but no definition for native implementations. Using "MASL" dialect
but an empty body can result in producing no declaration or definition.

Pros:
- Does not require the metamodel to be changed (can be encoded in current model)  
- Relatively easy to implement  

Cons:
- Not intuitive to a user  
- Requires that the Xtext editor is modified such that errors are not produced
  for empty bodies.  

5.2.2 Use existing dialects

Another way to handle this is by using the existing dialects. "None" would
result in no declaration or definition being produced. "C/C++" would result in
producing the declaration but no definition (for native implementations).

Pros:
- Does not require the metamodel to be changed (can be encoded in current model)  
- Relatively easy to implement  
- More explicit and intuitive than 5.2.1  

Cons:
- Requires existing models to be changed and working mode to be changed  
- Requires modification of existing implementations  

5.2.3 Marking editor

The marking editor can be used to handle this problem. When "None" dialect is
selected, a mark can be used to determine if it is to be generated into the
`.prj` file or not.

Pros:
- Does not require the metamodel to be changed (can be encoded in current model)  
- Relatively easy to implement  

Cons:
- Not readily visible to the user (must open marking editor)  

5.2.4 New model attribute

A new attribute could be added to the model of terminator services. This cold be
an enumeration that specifies where the implementation comes from in deployment.
Options would be "Domain" or "Deployment". If "Domain" is chosen, no declaration
or definition would be produced regardless of dialect. If "Deployment" is chosen
and the dialect is "None", no definition would be produced which is consistent
with current usage in domains. Adding an attribute would be easy to make
services marked as implemented by the domain visually different from other
services.

Pros:
- Very explicit and intuitive for users  

Cons:
- Requires update to the metamodel  
- More work to implement  

5.3 Defaults

A reasonable default can be applied to all of the design options

### 6. Work Required

6.1 Choose a design path  
6.2 Implement the design  
6.3 Test the design  

### 7. Acceptance Test

7.1 Acceptance test will be determined during the design phase.

### End
