---

This work is licensed under the Creative Commons CC0 License

---

# Complete Integrity Checking
### xtUML Project Implementation Note

### 1. Abstract

Perform complete (referential) integrity checking.

### 2. Document References

<a id="2.1"></a>2.1 [9554](https://support.onefact.net/issues/9554) complete integrity checking  
<a id="2.2"></a>2.2 [9554 Integrity Analysis Note](9554_integrity_ant.md) 9554 Integrity Checker Analysis Note  
<a id="2.3"></a>2.3 [9554 Integrity Design Note](9554_integrity_dnt.md) 9554 Integrity Checker Design Note  
<a id="2.4"></a>2.4 [9719](https://support.onefact.net/issues/9719) Automate build of integrity.exe.  
<a id="2.5"></a>2.5 [9737](https://support.onefact.net/issues/9737) Update relations in OOA of OOA  
<a id="2.6"></a>2.6 [9742](https://support.onefact.net/issues/9742) Display string UUIDs in referential integrity report  

### 3. Background

See [[2.2]](#2.2).  

### 4. Requirements

See [[2.2]](#2.2).  

### 5. Work Required

See [[2.2]](#2.2).  

### 6. Implementation Comments

6.1 Loading File Data Directly  
It was noted during development that the Prebuilder cannot be used
for this work.  This is because Prebuilder presumes a well-formed
and well-linked model.  Prebuilder begins at the top-level of the
model (`S_SYS`) and navigates down across associations to emit the
model data.  Thus, if model data is not well-linked, then the Prebuilder
will omit data.  This work is intended to identify exactly these
types of missing links.  Thus, it is a requirement of the integrity
checker to load all instances independently of the linkage.  The
MC-3020 loader does exactly this.  It loads all instances first, and
then _batch relates_ next.  When a link is not found, the instance
remains unlinked.  The integrity checker can then detect and report
the missing link by selecting all instances and exhaustively interrogating
the linkage.

6.2 Inter-Project References (IPRs)  
We will need to consider that checking across project boundaries
means we need to have all of the data from all included projects.
A multi-select is required.  In the GUI, this will mean selecting
all inter-dependent projects to run the Check Referential Integrity.
From the command line, all inter-dependent projects must be passed
as parameters on the command line.

Alternatively, as discussed on August 9, 2017, we will automatically
select all projects in the workspace in all cases.  This is the
new plan of record.

6.3 Command Line  
Most of the interface to the integrity checker is supplied through
`xtumlmc_build` passing `xtuml_integrity` as the first argument.
This keeps the GUI and command line close and simplifies the plugin.

6.4 Global Native Types  
BridgePoint supplies global, native xtUML types to projects.  These types
are not stored as part of the project xtUML model data.  Instead, they
are loaded from within the tool.  This file is found in the org.xtuml.bp.pkg
project at `globals/Globals.xtuml`.

`xtumlmc_build xtuml_integrity` will automatically include this data along
with the selected project(s) model data.  An option is provided (`-g`) to
exclude this data so that Prebuilder output can be used as integrity input.

6.5 Out-and-Back  
A select out-and-back statement was added to detect when some associations
may have been over-written with batch relate.  This covers the case of
an extra subtype that the instance loader and batch relater would allow
to occur.

6.6 `defaults.xtuml`  
This file was included in bp.core and is no longer used.  Deleting it.

6.7 Error Counter  
The number of errors found are counted and included in the output report.
A counter local to each class type limits the number of errors found of
one type of instance to 10.  This will prevent a waterfall of errors in
the report.

6.8 Simplified Plugin  
The plugin depends on io.image and `System_c` (`S_SYS`).  These dependencies
are only in place because needing to have the _project_ variable.  This
can be obtained in simpler ways.

6.9 bp.core ooaofooa  
See [[2.5]](#2.5).  

6.10 Enhanced Loader  
Consider fixing the loader to deal with unrecognized data gracefully.  
Consider enhancing the escher loader to support OOA of Graphics instances.  

### 7. Unit Test

Testing is being performed in a way similar to `masl_round_trip`.
A script is designed that loops through a list of models passing the
data to the referential integrity checker.  Results are compared
against expected results.  Clean models and known corrupt models
are part of the input.  This list of models can grow over time.

The primary test script (`run_corrupt_integrity_tests.sh`) is a simple
linear script that runs the tests enumerated here.

7.1 Two Instances with Duplicate Identifiers  
Duplicated `O_ATTR` instance.  
7.2 Missing Link  
7.2.1 Link Flavors  
7.2.1.1 Missing Simple Participant  
Deleted `S_EE` instance.  
7.2.1.2 Missing Simple Formalizer  
Deleted `S_BRG` instance.  
7.2.1.3 Missing Simple Reflexive Participant  
Deleted "end" `S_ENUM` instance.  
7.2.1.4 Missing Simple Reflexive Formalizer  
Deleted "first" `S_ENUM` instance.  
7.2.1.5 Missing Associative Associator  
Note!  This situation cannot be detected.  The associator is the
formalizer in both directions.  
Deleted `SM_MOAH[R511]` instance.  
This test runs but expects to not detect this missing associator unless
it is linked through another association (which this is through R513).  
7.2.1.6 Missing Associative One Side  
Deleted `SM_MOORE[R511]` instance.  
Note that a single missing instance in a ternary relationship (associative)
will result in none of the three instances being linked.  
7.2.1.7 Missing Associative Other Side  
Deleted `SM_STATE[R511]` instance.  
Same note as for One.  
7.2.1.8 Missing Subtype  
Deleted `O_NBATTR[R107]` instance.  
7.2.1.9 Extra Subtype  
Added `S_CDT[R17]`.  
Note!  This is not detected, because the mc3020 batch relate will over-write
the relationship when the second instance is loaded.  
To detect this situation, the link implementation in mc3020 would check to
see if a relationship is already linked before linking it.  This could be
done in the Link operations or done in the `batch_relate` operations (which
would make the fix more specific to instance loading.  
The former (detecting already related Link operations) would be a nice
feature as long as it were enabled with a mark.  
Another thought is to tighten up the integrity checker to select out-and-back
and be sure to get the same instance.  
7.2.1.10 Missing Supertype  
Deleted `PE_PE[R8001]` instance.  
7.3 Null Referential  
7.3.1 Null Formalizer Referential  
Nulled out `SM_ISM.Obj_ID(R115)`.  
7.3.2 Null Reflexive Formalizer Referential  
Changed `S_BPARM.Previous_BParm_ID[R55]` to a bogus (non-zero) value.
I needed to change it rather null it out, because the OOA of OOA has
only biconditional reflexive associations.  
7.3.3 Null Associator Referential  
Nulled out `R_OIR.Rel_ID[R201]`.  
7.3.3 Null Subtype Referential  
Nulled out `S_EDT.DT_ID[R17]`.  
7.4 Non-Null Referential  
7.4.1 Invalid Non-Null Conditional Formalizer Referential  
Changed `PE_PE.Package_ID[R8000]`.  
7.4.2 Invalid Non-Null Conditional Reflexive Formalizer Referential  
Changed `O_ATTR.PAttr_ID[R103]`.  

### 8. User Documentation

Describe the end user documentation that was added for this change. 

### 9. Code Changes

Fork/Repository: cortlandstarrett/bridgepoint   Branch: integrity2  
Fork/Repository: cortlandstarrett/mc   Branch: integrity2  
Fork/Repository: cortlandstarrett/models   Branch: integrity2  
Fork/Repository: cortlandstarrett/buildmt   Branch: integrity2  

<pre>

Fork/Repository: cortlandstarrett/bridgepoint   Branch: integrity2
 doc-bridgepoint/notes/9554_integrity_ant.md                                 |    4 +-
 doc-bridgepoint/notes/9554_integrity_dnt.md                                 |  317 +++++++++
 doc-bridgepoint/notes/9554_integrity_int.md                                 |  282 ++++++++
 doc-bridgepoint/notes/9567_association_editing_enhancements/cme.md          |   26 +
 doc-bridgepoint/review-minutes/9554_integrity_check.dnt.rvm.md              |   40 ++
 releng/org.xtuml.bp.mctools/pom.xml                                         |    3 +
 releng/org.xtuml.bp.releng.parent/pom.xml                                   |    1 +
 src/org.xtuml.bp.core/build.properties                                      |    1 -
 src/org.xtuml.bp.core/models/default.xtuml                                  | 1339 ----------------
 .../Reference/UserInterface/xtuml_integrity/xtuml_integrity.html            |  113 +++
 .../Reference/UserInterface/xtuml_integrity/xtuml_integrity.md              |   77 ++
 src/org.xtuml.bp.doc/topics_Reference.xml                                   |    1 +
 src/org.xtuml.bp.integrity/.classpath                                       |    7 +
 src/org.xtuml.bp.integrity/.externalToolBuilders/Build.launch               |   14 +
 src/org.xtuml.bp.integrity/.externalToolBuilders/Clean.launch               |   10 +
 src/org.xtuml.bp.integrity/.gitignore                                       |    2 +
 src/org.xtuml.bp.integrity/.project                                         |   48 ++
 src/org.xtuml.bp.integrity/META-INF/MANIFEST.MF                             |   26 +
 src/org.xtuml.bp.integrity/about.html                                       |   36 +
 src/org.xtuml.bp.integrity/build.properties                                 |    6 +
 src/org.xtuml.bp.integrity/generate.xml                                     |   29 +
 src/org.xtuml.bp.integrity/plugin.xml                                       |   18 +
 src/org.xtuml.bp.integrity/pom.xml                                          |   48 ++
 src/org.xtuml.bp.integrity/src/org/xtuml/bp/integrity/IntegrityPlugin.java  |   59 ++
 .../src/org/xtuml/bp/integrity/actions/checkReferentialIntegrityAction.java |   39 +
 .../src/org/xtuml/bp/integrity/generator/Generator.java                     |  262 +++++++
 src/org.xtuml.bp.pkg-feature/feature.xml                                    |    6 +
 utilities/build/preferences/org.eclipse.core.resources.prefs                |    2 +-
 28 files changed, 1473 insertions(+), 1343 deletions(-)


Fork/Repository: cortlandstarrett/mc   Branch: integrity2
 bin/integrity                                                               |   Bin 0 -> 964752 bytes
 bin/mac/integrity                                                           |   Bin 0 -> 1025428 bytes
 bin/mac/mcmc                                                                |   Bin 1450168 -> 1392120 bytes
 bin/mc3020.py                                                               |     4 +-
 bin/win/integrity.exe                                                       |   Bin 0 -> 961539 bytes
 bin/win/xtumlmc_build.exe                                                   |   Bin
 bin/xtumlmc_build                                                           |    95 +-
 model/integrity/.cproject                                                   |    78 +
 model/integrity/.externalToolBuilders/Model Compiler.launch                 |    14 +
 .../org.eclipse.cdt.managedbuilder.core.ScannerConfigBuilder (3).launch     |     7 +
 .../org.eclipse.cdt.managedbuilder.core.genmakebuilder (3).launch}          |     2 +-
 model/integrity/.project                                                    |    51 +
 .../integrity/.settings/com.mentor.nucleus.bp.ui.project.preferences.prefs  |     4 +
 model/integrity/gen/HOWTO_build_integrity.txt                               |     1 +
 model/integrity/gen/LOG_bridge.c                                            |    83 +
 model/integrity/gen/T_bridge.c                                              |   364 ++
 model/integrity/gen/T_bridge.h                                              |    42 +
 model/integrity/gen/datatype.mark                                           |    16 +
 model/integrity/gen/domain.mark                                             |     3 +
 model/integrity/gen/sys_user_co.c                                           |   244 +
 model/integrity/gen/sys_user_co.h                                           |   292 +
 model/integrity/gen/system.mark                                             |     5 +
 model/integrity/models/integrity/integrity.xtuml                            |   104 +
 model/integrity/models/integrity/integrity/integrity.xtuml                  | 10816 +++++++++++++++++++++++++
 model/integrity/models/integrity/lib/integrity/functions/functions.xtuml    |    68 +
 model/integrity/models/integrity/lib/integrity/integrity.xtuml              |   148 +
 model/integrity/models/integrity/lib/integrity/integrity/integrity.xtuml    |    68 +
 model/integrity/models/integrity/lib/integrity/ooaofooa/ooaofooa.xtuml      |    71 +
 model/integrity/models/integrity/lib/lib.xtuml                              |    70 +
 model/integrity/models/integrity/sysintegrity/sysintegrity.xtuml            |    96 +
 model/integrity/src/.gitignore                                              |     3 +
 model/integrity/src/makefile.integrity                                      |    13 +
 model/mcooa/gen/integrity.arc                                               |   533 ++
 model/mcooa/models/mcooa/ooaofooa/Body/Body.xtuml                           |     2 +-
 model/mcooa/models/mcooa/ooaofooa/Subsystem/Subsystem.xtuml                 |     2 +-
 model/mcooa/models/mcooa/ooaofooa/Value/Value.xtuml                         |     4 +-
 schema/sql/xtumlmc_schema.sql                                               |     6 +-
 36 files changed, 13297 insertions(+), 12 deletions(-)

Fork/Repository: cortlandstarrett/models   Branch: integrity2
 integrity/.gitignore                                                        |    1 +
 integrity/README.md                                                         |   16 +
 integrity/corrupt_model_data/.gitignore                                     |    1 +
 integrity/corrupt_model_data/ExtraSubtype.expected                          |    0
 integrity/corrupt_model_data/ExtraSubtype.xtuml                             | 4935 ++++++++++++++++++++
 .../InvalidNonNullConditionalFormalizerReferential.expected                 |    3 +
 .../corrupt_model_data/InvalidNonNullConditionalFormalizerReferential.xtuml | 4932 ++++++++++++++++++++
 .../InvalidNonNullConditionalReflexiveFormalizerReferential.expected        |    3 +
 .../InvalidNonNullConditionalReflexiveFormalizerReferential.xtuml           | 4932 ++++++++++++++++++++
 integrity/corrupt_model_data/MWO_clean.xtuml                                | 4932 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingAssociativeAssociator.expected          |    3 +
 integrity/corrupt_model_data/MissingAssociativeAssociator.xtuml             | 4928 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingAssociativeOneSide.expected             |    4 +
 integrity/corrupt_model_data/MissingAssociativeOneSide.xtuml                | 4930 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingAssociativeOtherSide.expected           |    5 +
 integrity/corrupt_model_data/MissingAssociativeOtherSide.xtuml              | 4925 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingSimpleFormalizer.expected               |    3 +
 integrity/corrupt_model_data/MissingSimpleFormalizer.xtuml                  | 4921 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingSimpleParticipant.expected              |    4 +
 integrity/corrupt_model_data/MissingSimpleParticipant.xtuml                 | 4921 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingSimpleReflexiveFormalizer.expected      |    3 +
 integrity/corrupt_model_data/MissingSimpleReflexiveFormalizer.xtuml         | 4926 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingSimpleReflexiveParticipant.expected     |    3 +
 integrity/corrupt_model_data/MissingSimpleReflexiveParticipant.xtuml        | 4926 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingSubtype.expected                        |    3 +
 integrity/corrupt_model_data/MissingSubtype.xtuml                           | 4929 ++++++++++++++++++++
 integrity/corrupt_model_data/MissingSupertype.expected                      |    3 +
 integrity/corrupt_model_data/MissingSupertype.xtuml                         | 4926 ++++++++++++++++++++
 integrity/corrupt_model_data/NullAssociatorReferential.expected             |    4 +
 integrity/corrupt_model_data/NullAssociatorReferential.xtuml                | 4932 ++++++++++++++++++++
 integrity/corrupt_model_data/NullFormalizerReferential.expected             |    3 +
 integrity/corrupt_model_data/NullFormalizerReferential.xtuml                | 4932 ++++++++++++++++++++
 integrity/corrupt_model_data/NullReflexiveFormalizerReferential.expected    |    3 +
 integrity/corrupt_model_data/NullReflexiveFormalizerReferential.xtuml       | 4932 ++++++++++++++++++++
 integrity/corrupt_model_data/NullSubtypeReferential.expected                |    5 +
 integrity/corrupt_model_data/NullSubtypeReferential.xtuml                   | 4932 ++++++++++++++++++++
 integrity/corrupt_model_data/README.md                                      |   17 +
 integrity/corrupt_model_data/TwoInstancesWithDuplicateIdentifiers.expected  |    3 +
 integrity/corrupt_model_data/TwoInstancesWithDuplicateIdentifiers.xtuml     | 4944 ++++++++++++++++++++
 integrity/corrupt_model_data/run_corrupt_integrity_tests.sh                 |   41 +
 integrity/expected_results.txt                                              |   16 +
 integrity/integrity_tests                                                   |    8 +
 integrity/run_integrity_tests.sh                                            |   50 +
 43 files changed, 88940 insertions(+)

Fork/Repository: cortlandstarrett/buildmt   Branch: integrity2

</pre>

### End

