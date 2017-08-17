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
<a id="2.6"></a>2.6 [9554 Integrity Analysis Note](9554_integrity_ant.md) 9554 Integrity Checker Analysis Note  
<a id="2.7"></a>2.7 [Existing Integrity Checker Implementation Note](https://github.com/xtuml/internal/blob/master/doc-internal/notes/28_dts0100970501/28_dts0100970501_int.md) Integrity Checker Implementation  
<a id="2.8"></a>2.8 [9681](https://support.onefact.net/issues/9681) OAL parser exits prematurely on select related role phrases when not reflexive  
<a id="2.9"></a>2.9 [9690](https://support.onefact.net/issues/9690) Deprecate `V_LOC`.  
<a id="2.10"></a>2.10 [Model Integrity package in ooaofooa of bp.core](https://github.com/xtuml/bridgepoint/tree/master/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Model%20Integrity) Model Integrity  

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
See [[2.10]](#2.10).  

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
assr, sub, super).  Referential attribute values need to be compared
with identifiers.

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
This option takes the same approach as the previous option with
regard to building an archetype that generates OAL.  However, the
OAL is then deployed in a different rendition of the OOA of OOA
in the C model compiler.

The OOA of OOA is duplicated in stripped down form in `mcooa`.
This model is a "bare meta-model" and contains only the classes
and none of the action langauge of the meta-model.  This makes
is much lighter weight and easier-to-work-with than the
OOA of OOA in `bp.core`.

`docgen` is a model-based model compiler that interrogates
user model data and generates documentation.  It has in place
all of the facilities to build the ooaofooa, load prebuilder
output and run queries against the model data.  Thus,
it serves as a convenient prototyping environment for model
data queries such as that required for an integrity checker.

This approach could also serve as the final solution.  The results
could be packaged exactly like `docgen` is packaged.  The build and
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
The approach taken will be that described in 5.3.2.  If there is time,
the approach described in 5.3.1 will be tested as a simple "paste-in".
But any benefits of the 5.3.1 approach are outweighed by the risk of
adding so much action language into BridgePoint proper.  There are
weaknesses in MC-Java that would need to be dealt with such as model
root locality.  The build process is also more complex.  Performance
is a concern and would need benchmarking.

The approach described in 5.3.2 has been used to prototype the
solution.  Since working with `mcooa` is easier than working with
`bp.core`, the development of the archetype is done in the C
environment.  Build times are faster, and MC-3020 is easier to
debug than MC-Java.

After prototyping and doing some testing, this 'docgen-style' approach
is proving to be the best Way Forward.

Note that MC-3020 supports package references, and the meta-model
has been refactored in this environment.  The new integrity checker
can live in its own package/project/model.  It will be loosely
coupled with the rest of the tooling.

When Package Reference support is added to MC-Java, BridgePoint can
be refactored, and the integrity tool can be integrated at the Java
level.  This will open the door to finer granularity checking of
individual packages or even indiviual model elements.

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
Functions are added that interrogate the entire instance population of a model
by selecting all instances of all Model Classes (`O_OBJ`) in the model data.
A blind `select ... from instances of` is performed on each Model Class.
The instance extent is exhaustively iterated.  Each of the required integrity
checks is performed on each instance of each class.

6.1.1 uniqueness check  
While iterating over each instance, a second selection to the same
Model Class is made.  The selection is formulated with a where clause that
searches for duplicate instance identifier attribute values.  Assurance is
made that exactly one instance with a particular identifier value
is present.  Note, this is an expensive query of greater than
order N squared, O(n^2), complexity.  Performance is a concern.

6.1.2 link integrity  
6.1.2.1 unconditional participations  
For each instance, selections are made across associations that the
instance of the class participates in.  Queries are made systematically
across each form of _participation_.  Reflexive variations are included
in eligible forms.
The forms of participation are:  
- simple participant  
- simple formalizer  
- associator (two directions)  
- associative participant on the 'one' side  
- associative participant on the 'other' side  
- subtype  
- supertype  

6.1.2.2 non-null referentials  
For the simple association formalizers and associators, select across
conditional associations when a referential attribute is non-null.
Expect to find an instance.  Report discrepancies.

6.1.3 super/subtype integrity  
6.1.3.1 subtype  
For each instance participating as a subtype, the expected supertype
is queried to assure exactly one is found.  
Note, a special case is made for top-level packages ("Model Roots").
(A design idiosyncracy of BridgePoint is that top-level instances
of `EP_PKG` have no supertype `PE_PE`.)  
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
formalizer or associator not particpating in a link has an appropriate
not-participating value.  Note, that for complexity purposes only one
of the referential attributes that is of type `unique_id` is interrogated.  

6.2 OOA of OOA Repair  

6.2.1 R110  
is `O_OIDA 1..*-----R110-----* R_RTO - - - 1 O_RTIDA`  
should be `O_OIDA *-----R110-----* R_RTO - - - 1 O_RTIDA`  
This is because we now support associations that are not formalized.
A Referred To Class in Association (`R_RTO`) is not required to
have an identifier.  
6.2.2 R835  
is `V_VAR 0..1-----R835-----1..* V_LOC`  
should be `V_VAR 1-----R835-----* V_LOC`  
It looks to me like `V_LOC` may be deprecated.  Searches in bridgepoint/src
for `V_LOC` seem to show that it is only ever created (and deleted upon
cleanup) but never accessed.  Checking on this.  For this work, we may
simply repair the conditionality.  An issue will be raised to track this
specific modification to meta-model.  See [[2.9]](#2.9).  
6.2.3 R612  
is `ACT_BLK *-----R612-----1 ACT_ACT`  
should be `ACT_BLK *-----R612-----0..1 ACT_ACT`  
This link is only present during parsing and needs to be biconditional.  

6.3 Design Observations  
See [[2.8]](#2.8).  OAL parser exits prematurely on select related
role phrases when not reflexive.  So, I avoided them.

6.4 Report File  
The implementation may consider storing the output to a file rather
than simply using the Console.  The file could be marked up to make
a more beautiful presentation.  However, the plan of record is to use
output to a simple text file and open it in an editor upon completion
of the integrity check.

6.5 User Interface  
The user interface will be similar to the existing user interface.
Under `BridgePoint Utilities` `Check Model Integrity` will run as
it does today running the spot checking.

A new menu item under `BridgePoint Utilities` will be named
`Check Referential Integrity`.  It will run the new, exhaustive
referential integrity checker (from this work).  The output will
be displayed as a text file in the editor view.

We can consider combining these options after some mileage and
testing with both in place.

All inter-dependent projects must be (multi-) selected to get a
cohesive referential integrity check.

The command line interface will be as such:
```
xtumlmc_build xtuml_integrity -i <models folder or file1> -i <models folder of file2> -i <globals file> -m <accumulated model file> -o <output file>
```
The models folders or files supplied with the `-i` switch point to xtuml
files or folders (such as .../workspace/MicrowaveOven/models).  The path
to the Globals.xtuml file in org.xtuml.bp.pkg is needed to get clean
checking on native types.  The `-m` switch is optional and keeps the
accumulated model data file to be used to track down integrity violations.
The `-o` switch identifies an output file for the integrity report.
Without `-o` the report is emitted to the standard output.

### 7. Acceptance Test

The following tests will be created by editing valid models and
introducing all of the various forms of missing link and incorrect
referential attribute values.

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
