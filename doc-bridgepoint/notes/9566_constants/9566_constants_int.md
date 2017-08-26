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
<a id="2.3"></a>2.3 [#9738 problem created for constants with the same name in different specifications](https://support.onefact.net/issues/9738)  
<a id="2.4"></a>2.4 [dts0100777533 analysis
note](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20111020/technical/notes/dts0100777533/dts0100777533.ant) -- this is a One Fact internal engineering document  
<a id="2.5"></a>2.5 [#2350 dts0100777533 manual test](https://support.onefact.net/issues/2350)  
<a id="2.6"></a>2.6 [#9739 Constants in array dimensions not visible across model roots](https://support.onefact.net/issues/9739)  

### 3. Background

See [[2.2]](#2.2) section 3

### 4. Requirements

See [[2.2]](#2.2) section 4

### 5. Work Required

5.1 Parser changes  
5.1.1 Renamed rules in `oal.bnf` from "enumerator_access" to "scoped_access",
etc. See 6.1 in [[2.2]](#2.2)  
5.1.2 Renamed OAL validation functions and associated parameters and updated
implementations for:  
5.1.2.1 `Scoped_access_start`  
5.1.2.2 `Scoped_access_validate`  
5.1.2.3 `Scoped_access_end`  
5.1.2.4 `Scoped_data_type_validate`  
5.1.2.5 `Scoped_member_validate`  

5.2 Model changes

Because of an architectural detail in MC-Java, "select from instances ..." does
not select across model roots (root package under system) except when there is a
where clause comparing equality of a single primary identifying attribute. This
is commonly used in the OOA of OOA as a pattern instead of passing instance
references as a parameter. The way symbolic constants were modeled, a constant
had two identifying attributes making up its primary identifier -- its own
unique ID and the unique ID of the associated data type. After analysis, it was
determined that there is no reason why the ID of the data type should be part of
the ID -- the unique ID of the constant is sufficient to identify it. Because
this work introduced a need to select a constant by ID during parse, it was
necessary to remove the data type ID from the primary identifier so MC-Java
could generate code that searched across model roots.

5.2.1 Remove `DT_ID` from the primary identifier of Leaf Symbolic Constant  
5.2.2 Remove `DT_ID` from the primary identifier of Symbolic Constant  
5.2.3 Replace referential attributes that were removed in Symbolic Constant,
Leaf Symbolic Constant, Literal Symbolic Constant, and Symbolic Constant Value
with new base attributes of type `unique_id` of the same name appended with
`_Deprecated`  
5.2.4 Update the schema with the new attribute names  
5.2.5 Update the schema to remove `DT_ID` as a foreign key for R1502, R1503,
R1505, and R850  

The decision for this change was to leave the attributes in place and rename
them to "deprecated". This maintains persistence compatibility for models
created before this change.

5.3 Changes for array dimensions  

5.3.1 Current state

Some additional analysis was done to determine how to support scoped constants
in array dimensions. Currently we use regular expressions to validate the format
of dimensions and parse/populate them with regular expression matching. When
array dimensions were first introduced, only literal integers were allowed, so
this method takes sense. Since then, functionality has been specified (and in
some cases added) to allow constants and arithmetic expressions in array
dimensions. It would make sense to reuse the OAL parser to parse and evaluate
constant expressions in array dimensions -- this would be less complicated than
regular expressions for complex expressions, and it reuses the validation code
in both places.

5.3.2 Previous work and analysis

[[2.4]](#2.4) is an analysis note which specifies functionality for constants
and arithmetic expressions in array dimensions. Automated test cases were never
introduced for this work, but a manual test was introduced [[2.5]](#2.5). While
analyzing this bit it was discovered that the arithmetic expression parsing was
never implemented and another bug was uncovered where constants could not be
used in dimensions if the constant was defined in a different model root
[[2.6]](#2.6).

5.3.3 Decision

The analysis done in 5.3.1 determined that it is too expensive at the moment to
replace the regular expression validator/parser with the OAL parser. We will
continue to use regular expressions for array dimensions for the time being.
Since both concerns raised in 5.3.2 are out of the scope of the requirements for
this issue, neither will be handled. Scoped constants shall be supported in
array dimensions as far as regular constants have been to this point. Non-scoped
constants shall continue to be supported as far as the have to this point.

5.3.4 Updated regular expressions in the dimension validator to allow optional
`ID::` before the constant  
5.3.5 Changed the validator to search for constant specification first (if it is
provided), and then search through the CSP for the constant  
5.3.6 Update error messages where appropriate  

5.4 Miscellaneous changes  
5.4.1 Added a case for constant specifications in `PE_PE.getLocalName`  
5.4.2 Added model integrity check for constant specifications (see 6.3 of
[[2.2]](#2.2))  
5.4.3 Fixed integrity check for symbolic constants (see 6.3 of [[2.2]](#2.2))  

### 6. Implementation comments

6.1 [[2.3]](#2.3) is resolved as a result of 5.4.3  

### 7. Unit Test

See section 9 in [[2.2]](#2.2)

7.1 Testing scoped constants in array dimensions

This section expounds section 9.3 in [[2.2]](#2.2). These cases shall be created
for constants in array dimensions.

7.1.1 Scoped constant reference where the constant specification does not exist  
7.1.2 Scoped constant reference where the constant specification exists but the
constant does not exist  
7.1.3 Non-scoped constant reference with a non-existent constant  
7.1.4 Non-scoped constant reference with multiple identical constants in
different constant specifications  
7.1.5 Valid scoped constant access  
7.1.6 Valid non-scoped constant access  

7.2 Tests added  
7.2.1 `TestScopedConstants.java` in OAL tests (9 new tests)  
7.2.1 `ScopedConstantDimensionsTest.java` in properties tests (6 new tests)  

### 8. User Documentation

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9566_constants  

<pre>

 doc-bridgepoint/notes/9566_constants/9566_constants_ant.md                                                                  |  75 ++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9566_constants/9566_constants_dnt.md                                                                  | 338 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9566_constants/9566_constants_int.md                                                                  |  61 +++++++++++++++++++++++++++++++
 doc-bridgepoint/review-minutes/9566_constants_rvm.md                                                                        |  35 ++++++++++++++++++
 src/MC-Java/ooa_schema.sql                                                                                                  |  16 ++++----
 src/org.xtuml.bp.als.oal/bnf/oal.bnf                                                                                        |  14 +++----
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/Constant Specification/Constant Specification.xtuml       |  32 ++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/Constants.xtuml                                           |  30 ---------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/Leaf Symbolic Constant/Leaf Symbolic Constant.xtuml       |  55 ++++++----------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/Literal Symbolic Constant/Literal Symbolic Constant.xtuml |  57 ++++++-----------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/Symbolic Constant/Symbolic Constant.xtuml                 |  68 ++++++++++------------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/OAL Validation Functions/OAL Validation Functions.xtuml   | 253 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--------------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Packageable Element/Packageable Element/Packageable Element.xtuml   |   4 ++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Value/Symbolic Constant Value/Symbolic Constant Value.xtuml         |  50 ++++++-------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Value/Value.xtuml                                                   |  14 +------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/cells/editors/DimensionsValidator.java                                       |  90 +++++++++++++++++++++++++++------------------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/DimensionsUtil.java                                                        | 108 ++++++++++++++++++++++++++++--------------------------
 17 files changed, 927 insertions(+), 373 deletions(-)

</pre>

Fork/Repository: leviathan747/bptest  
Branch: 9566_constants  

<pre>

 src/org.xtuml.bp.als.oal.test/src/OALGlobalsTestSuite_Generics.java                                       |   2 ++
 src/org.xtuml.bp.als.oal.test/src/org/xtuml/bp/als/oal/test/TestScopedConstants.java                      | 241 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.properties.test/src/PropertiesGlobalsTestSuite.java                                   |   4 +++-
 src/org.xtuml.bp.ui.properties.test/src/org/xtuml/bp/ui/properties/test/ScopedConstantDimensionsTest.java | 112 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 4 files changed, 358 insertions(+), 1 deletion(-)

</pre>

Fork/Repository: leviathan747/models  
Branch: 9566_constants  

<pre>

 test/9566_constants/.project                                          |  12 +++++++
 test/9566_constants/models/9566_constants/9566_constants.xtuml        | 152 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/9566_constants/models/9566_constants/constants1/constants1.xtuml | 442 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/9566_constants/models/9566_constants/constants2/constants2.xtuml | 159 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/9566_constants/models/9566_constants/enums1/enums1.xtuml         | 190 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/9566_constants/models/9566_constants/enums2/enums2.xtuml         | 152 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 test/9566_constants/models/9566_constants/functions/.gitignore        |   1 +
 test/9566_constants/models/9566_constants/functions/functions.xtuml   |  71 ++++++++++++++++++++++++++++++++++++
 8 files changed, 1179 insertions(+)

</pre>

### End

