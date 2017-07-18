---

This work is licensed under the Creative Commons CC0 License

---

# Complete Integrity Checking
### xtUML Project Design Note

### 1. Abstract

Perform complete (referential) integrity checking.

### 2. Document References

<a id="2.1"></a>2.1 [9554](https://support.onefact.net/issues/9554) complete integrity checking  
<a id="2.2"></a>2.2 [633](https://support.onefact.net/issues/633) The "Check model integrity" utility does not capture orphaned referential attributes  
<a id="2.3"></a>2.3 [653](https://support.onefact.net/issues/653) Check model integrity tool doesn't find problem  
<a id="2.4"></a>2.4 [8514](https://support.onefact.net/issues/8514) Add a preference to disable the model integrity checker  
<a id="2.5"></a>2.5 [test_consistency.py](https://github.com/xtuml/pyxtuml/blob/master/tests/test_xtuml/test_consistency.py) pyxtuml consistency checker  
<a id="2.6"></a>2.6 [9554 Integrity Analysis Note](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/9554_integrity_ant.md) 9554 Integrity Checker Analysis Note  
<a id="2.7"></a>2.7 [Existing Integrity Checker Implementation Note](https://github.com/xtuml/internal/blob/master/doc-internal/notes/28_dts0100970501/28_dts0100970501_int.md) Integrity Checker Implementation  
<a id="2.8"></a>2.8 [9681](https://support.onefact.net/issues/9681) OAL parser exits prematurely on select related role phrases when not reflexive  

### 3. Background

See [[2.6]](#2.6).  

### 4. Requirements

See [[2.6]](#2.6).  

### 5. Analysis

5.1 Current Integrity Checker  
As stated in [[2.6]](#2.6), the current integrity checker is designed
to allow arbitrary rules to be applied and enforced on a model element
by model element basis.  The `NonRootModelElement` base class has an
empty implementation of `checkIntegrity`.  Any model element can override
this operation.  About a dozen elements do override it; most do not.
The elements that do override checkIntegrity perform useful _ad hoc_ checks.

The existing model called 'Model Integrity' may be leveraged in this
present work to report issues discovered by new integrity checking.

5.2 Current Referential Integrity  
As stated in [[2.6]](#2.6), the current integrity checker is not
explicitly an enforcer of referential integrity, even though there
is an operation on the `NonRootModelElement` base class called
`Checkreferentialintegrity`.  This routine was designed to identify
model elements that have been erroneously deleted or omitted from
persistent storage (during a merge).  The routine is detecting the
existence of a proxy with no associated model element.  This is not
sufficient to detect true referential integrity violations such as
missing relational links and incorrect referential attribute values.

The current `Checkreferentialintegrity` does not traverse sub/supertype
links.

5.3 Approaches to the Problem  
There are a few ways this problem can be addressed.  Each approach needs
to supply functionality that reads the model data and applies checking
to meet the given requirements.  This includes checking for instance
uniqueness (identifying attribute values) and traversing all unconditional
associations from each end of the association (part, form, aone, aoth,
assoc, sub, super).  Referential attribute values need to be compared
with identifiers in all cases.

Here are three approaches that have been considered:  
  - Generate OAL-based integrity checking and then import OAL into
    BridgePoint.  
  - Generate OAL-based integrity checking and then compile with MC-3020
    and compile a C executable.  
  - Generate Java-based integrity checking directly.

5.3.1 Generating OAL and Adding to BridgePoint  
This approach generates a model-level model integrity checker.  The
work required is to build an archetype that traverses `Association`
and `Subtype` packages and generates OAL that performs the checking
outlined above.

Once the checking OAL has been generated, this OAL needs to be
packaged into BridgePoint.  This can be done manually by pasting
the generated OAL into an activity in `bp.core`.  Or it can be
automated as part of the build process, so that the OAL checking
is re-generated when the meta-model changes.

5.3.2 Generating OAL and Compiling with MC-3020  
The OOA of OOA is duplicated in stripped down form in `mcooa`.
This model contains only the classes and none of the action
langauge of the meta-model.  This makes is much lighter weight
and easier to work with than the OOA of OOA in `bp.core`.

`docgen` is a model-based model compiler that interrogates
user model data and generates documentation.  It has in place
all of the facilities to build the ooaofooa, load prebuilder
output xtuml and run queries against the model data.  Thus,
it serves as the perfect prototyping environment for a model
data query like an integrity checker.

It could also serve as the final solution.  The results could
be packaged exactly like `docgen` is packaged.  The build and
packaging could be automated in the same way that `docgen` and
`x2m` are automated.

5.3.3 Generating Java  
A third option is to generate Java directly rather than generating OAL.
During prototyping, OAL has been generated.  The queries to collect
information to generate Java would be similar.  However, the templates
would be much more complex.

The OAL-generating templates are fairly simple (about 100 lines of RSL).
The Java-generating templates would be much more complex.  Because
the OAL contains virtually every form of `select` statement, a good
deal of the complexity of MC-Java would need to be duplicated into
the integrity checker template.

5.3.4 Chosen Approach  
The approach taken will be that described in 5.3.1.  First the OAL
will be pasted into BridgePoint, and a promotion pull request will
be prepared.  As a second (optional) step, the build process will be
automated to reduce maintenance costs moving forward.

The approach described in 5.3.2 will be used to prototype the
solution.  Since working with `mcooa` is easier than working with
`bp.core`, the development of the archetype will be done in the C
environment.  Build times are faster, and MC-3020 is easier to
debug than MC-Java.

### 6. Design

6.0 General Flow  
```
For each class in the model do the following:
  Search for duplicate identifiers in the instance extent.
  Match referential attribute values with identifier attribute values.
  Find unconditional formalizer from a participant in a simple association.
  Find unconditional participant from a formalizer in a simple association.
  For an associator in a linked association ensure both ends have an instance.
  From the one end of an associative, find the unconditional associator.
  From the other end of an associative, find the unconditional associator.
  For a subtype, ensure a supertype is present.
  For a supertype, ensure exactly one subtype is present.
```

6.1 Generalized Referential Integrity Checking  
A function is added that interrogates the entire instance population of a model
by selecting all instances of all Model Classes (`O_OBJ`) in the model data.
A blind `select ... from instances of` is performed on each Model Class.
The instance extent is exhaustively iterated.  Each of the required integrity
checks is performed on each instance of each class.

6.1.1 uniqueness check  
While iterating over each instance, a second selection to the same
Model Class which is formulated with a where clause that searches
for duplicate instance identifier attribute values.  Assurance is
made that exactly one instance with a particular identifier value
is present.  Note, this is an expensive query of greater than
order N squared, O(n^2), complexity.

6.1.2 link integrity  
6.1.2.1 unconditional participations  
For each instance, selections are made across associations that the
instance of the class participates in.  Only unconditional associations
are queried.  Queries are made systematically across each form of
_participation_.  Reflexive variations are included in eligible forms.
The forms of participation are:  
- simple participant  
- simple formalizer  
- associator  
- associative participant on the 'one' side  
- associative participant on the 'other' side  
- subtype  
- supertype  

6.1.2.2 non-null referentials  
For the simple association formalizer side, select across
conditional associations when a referential attribute is non-null.
Expect to find an instance.  Report discrepancies.

6.1.3 super/subtype integrity  
6.1.3.1 subtype  
For each instance participating as a subtype, the expected supertype(s)
is queried to assure exactly one is found.  
6.1.3.2 supertype  
For each instance participating as a supertype, select and find
exactly one subtype instance.

6.1.4 referential integrity  
6.1.4.1 An assertion is made that referential attribute sets match their
corresponding referred-to identifier attribute sets.  Mismatches are
reported.  
Note, this query may be skipped for architectures that implement referential
attributes as references to referred-to identifier attribute.  MC-Java is
such an architecture.  
6.1.4.2 An assertion is made that a referential attribute of a simple
formalizer not particpating in a link has an appropriate not-participating
value.  

4.1 (placeholder to match SRS)
4.2 The metamodel instance population of the application model shall be
interrogated for the presence of all required association links.
4.3 The values of referential attributes of related metamodel instances
shall be asserted to match the value of referred to identifier attributes.
4.4 Referential integrity assertion shall be applied to the entire
metamodel instance population of the application model being edited.
4.5 Assertion failures shall be reported clearly.
4.6 The xtUML metamodel shall be repaired to fix specific cardinality
errors.
4.7 The functionality of the checker shall be available from the user
interface.
4.8 The functionality of the checker shall be available from the command
line.

6.2 OOA of OOA Repair  
It looks to me like `V_LOC` may be deprecated.  Searches in bridgepoint/src
for `V_LOC` seem to show that it is only ever created (and deleted upon
cleanup) but never accessed.  Checking on this.  For this work, we will
simply repair the conditionality.  Raise a follow-on issue.

6.2.1 R110  
is `O_OIDA 1..*-----R110-----* R_RTO - - - 1 O_RTIDA`  
should be `O_OIDA *-----R110-----* R_RTO - - - 1 O_RTIDA`  
6.2.2 R835  
is `V_VAR 0..1-----R835-----1..* V_LOC`  
should be `V_VAR 1-----R835-----* V_LOC`  
6.2.3 R4708  
is `CL_POR 1-----R4708-----* CL_IIR`  
and seems to be correct, but the instance population may be adding or missing stuff in a test model.  I am guessing this is a model that is not upgraded.
6.2.4 R4709  
is `C_PO 0..1-----R4709-----* CL_POR`  
should be `C_PO 1-----R4709-----* CL_POR`  
6.2.5 R612  
is `ACT_BLK *-----R612-----1 ACT_ACT`  
should be `ACT_BLK *-----R612-----0..1 ACT_ACT`  

6.3 Design Observations  
See [[2.8]](#2.8).  OAL parser exits prematurely on select related
role phrases when not reflexive.  So, I avoided them.

```
Consider the menu option in workspace preferences.  Should we allow
this be messed with?  Should we never run integrity checker unless
someone asks for it?
```
```
We may need to make a special case for EP_PKG at the top level.
```

### 7. Acceptance Test

A positive case and a negative case for each requirement above is
created as an xtUML model persisted as SQL instances (normal xtUML
format data).

Expected results are defined for each case.

7.1 Two Instances with Duplicate Identifiers  
7.2 Missing Link  
7.2.1 Link Flavors  
7.2.1.1 Missing Simple Participant  
7.2.1.2 Missing Simple Formalizer  
7.2.1.3 Missing Simple Reflexive Participant  
7.2.1.4 Missing Simple Reflexive Formalizer  
7.2.1.5 Missing Associative Associator  
7.2.1.6 Missing Associative One Side  
7.2.1.7 Missing Associative Other Side  
7.2.1.8 Missing Subtype  
7.2.1.9 Extra Subtype  
7.2.1.10 Missing Supertype  
7.3 Null Referential  
7.3.1 Null Formalizer Referential  
7.3.2 Null Reflexive Formalizer Referential  
7.3.3 Null Associator Referential  
7.3.3 Null Subtype Referential  
7.4 Non-Null Referential  
7.4.1 Invalid Non-Null Conditional Formalizer Referential  
7.4.2 Invalid Non-Null Conditional Reflexive Formalizer Referential  

### End
