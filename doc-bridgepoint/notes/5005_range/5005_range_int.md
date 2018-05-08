---

This work is licensed under the Creative Commons CC0 License

---

# Ranges of data types  
### xtUML Project Implementation Note

### 1. Abstract

Quoted from the issue:  
>It would be very powerful to be able to define min and max values for
>data types, of core integer and real types.  Initially it could be
>enough only to add the data into the model and store them in the
>metamodel.  The ranges can then be used by model compilers.
>The ranges should be added to `S_CDT` and `S_UDT` of types
>integer and real types.
>
>Later on the ranges can be used for run time checks during Verifier
>execution.

### 2. Document References

<a id="2.1"></a>2.1 [5005](https://support.onefact.net/issues/5005) Ranges of data types  
<a id="2.2"></a>2.2 [analysis note](5005_range_ant.md) Ranges of data types  
<a id="2.3"></a>2.3 [10220](https://support.onefact.net/issues/10220) Support UDTs based on EDTs in MC-3020  
<a id="2.4"></a>2.4 [10221](https://support.onefact.net/issues/10221) Analyze UDT range enforcement.  
<a id="2.5"></a>2.5 [10230](https://support.onefact.net/issues/10230) Test UDT Range Editor  
Note:  Only analysis and implementation notes for this issue.  

### 3. Background

See [2.2].

![Range Model](range2.png) Range and User Data Type


### 4. Requirements

See [2.2].

### 5. Work Required

5.1 Update metamodels (`ooaofooa` and `mcooa`).  
5.1.1 Add Range Class  
A new class `Range (S_RANGE)` is added to the Domain package under ooaofooa.
This class has a conditional one to one relationship according to the
analysis note [2.2] for the issue [2.1].

A range has minumum and/or maximum values.  These are added as attributes
`Min` and `Max`.  The new attributes are typed as string to handle the
full minumum and maximum values.  The UI handles validation with type
determination (integer versus real).

The same model change is made to both `ooaofooa` and `mcooa`.

5.1.2 Model Compiler Tools  
The `mcooa` change affects other model compiler tools.  This includes
escher (`mcmc`) and `integrity`.  The action language in the integrity
model is regenerated, pasted in, the referential integrity checker is
itself regenerated and compiled.  `mcmc` is rebuilt according to the
instructions in the `escher` gen folder.

5.1.3 `ooa_schema.sql` and `xtumlmc_schema.sql`  
The schemata for MC-Java and the model compilers need to have the new class
(Range) and association.  Instructions for regenerating the schema are found
in `escher/gen/HOWTO Create MC-3020 HEAD branch.txt`.  This is regenerated.  

5.1.4 `maslin` UDT  
`m2x` involves a hand-edited file in the `gen` folder of the `maslin`
project.  `masl2xtuml_S_UDT_class.c` is modified to update the identifiers
of data types during the import process.  This hand-edited file needs to be
refreshed because of the association across R57 to the new Range (`S_RANGE`)
class.

5.2 Update the editor.  
5.2.1 UI Range configuration  
A new menu with three entries is added.  These are:  
  * Range  
  * Range > Minimum...  
  * Range > Maximum...  
  * Range > Clear Range  

The Range top level menu shows as long as the selection contians only UDTs
that have a core type of integer, real or Enumeration Data Type.  The `Clear
Range` menu item will show only as long as one of the selected User Data
Types has an already associated Range.

The `Minimum...` and `Maximum...` menu items open an input dialog.  This
dialog assures that the input value is valid.  Validation of this field
is done with the `InputValueValidator` class.  Messages are given when
the input string is not valid.  The O.K. button is not enabled until a
valid input value is supplied.

5.2.2 Add range data to compare and persistence.
`T_TNS` and `T_TPS` are added so that Range shows up in the tree.
The `file_io.pei` and `stream.pei` data is modified to add Range as
a new child to User Data Type.

5.3 Update Metamodel Welcome Project  
Since the metamodel is changed, the example project is updated to follow
the changes in the OOA of OOA.

5.4 Analyze and document enforcement in Verifier.  
See [2.4].

5.5 Analyze and document enforcement in model compilers.  
See [2.4].

5.5 Implement test cases.  
See unit test below.  

### 6. Implementation Comments

6.1 Data Type User Documentation  
Documentation for Data Types existed in the native HTML format as
produced by a previous generation WYSIWYG tool (probably MSWord).
This entire section was converted to markdown before adding the new
information documenting UDT ranges.  `pandoc` was used to convert the
markdown source, and the `github-pandoc.css` stylesheet was
applied (found several directories above in the documentation
folder hierarchy).

### 7. Unit Test

7.1 Existing unit test shall pass.

7.2 Setting and Clearing Ranges  
Run the QA Manual test in [2.5].

### 8. User Documentation

8.1 Help Update  
The Help documentation is updated.  The section changed is
BridgePoint UML Suite Help --> Reference --> User Interface -->
xtUML Modeling Perspective --> Model Elements --> Data Types.

### 9. Code Changes

<pre>
Fork/Repository:  cortlandstarrett/bridgepoint
Branch:  5005_range
 doc-bridgepoint/notes/5005_range/5005_range_int.md                                 |    308 +
 doc-bridgepoint/process/Developer Getting Started Guide.md                         |      1 -
 releng/org.xtuml.bp.mctools/pom.xml                                                |      4 +-
 src/MC-Java/ooa_schema.sql                                                         |      6 +
 src/MC-Java/referential_attribute_util.inc                                         |      4 +-
 src/org.xtuml.bp.core/arc/create_core_plugin.inc                                   |      8 +
 src/org.xtuml.bp.core/arc/create_object_inspector.inc                              |      2 +-
 src/org.xtuml.bp.core/generate.xml                                                 |      5 +-
 src/org.xtuml.bp.core/icons/metadata/Range.gif                                     |    Bin 0 -> 336 bytes
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Domain.xtuml        |    250 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Range/Range.xtuml   |    136 +
 .../models/org.xtuml.bp.core/ooaofooa/Domain/User Data Type/User Data Type.xtuml   |      7 +-
 .../Functions/Context Menu Entry Functions/Context Menu Entry Functions.xtuml      |     27 +
 src/org.xtuml.bp.core/sql/ooaofooa_hierarchy.pei.sql                               |      4 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/InputValueValidator.java        |     13 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/inspector/.gitignore                   |      1 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/RangeValueInputValidator.java       |     52 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/actions/RangeAction.java            |    114 +
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/actions/RangeContributionItem.java  |    123 +
 .../Reference/UserInterface/xtUMLModeling/ModelElements/HTML/DataTypePackage.htm   |    216 +-
 .../Reference/UserInterface/xtUMLModeling/ModelElements/HTML/DataTypePackage.md    |     84 +-
 .../Reference/UserInterface/xtUMLModeling/ModelElements/HTML/range_cme1.png        |    Bin 0 -> 118577 bytes
 .../Reference/UserInterface/xtUMLModeling/ModelElements/HTML/range_cme3.png        |    Bin 0 -> 72061 bytes
 src/org.xtuml.bp.io.mdl/sql/file_io.pei.sql                                        |      3 +-
 src/org.xtuml.bp.io.mdl/sql/stream.pei.sql                                         |      3 +-
 src/org.xtuml.bp.ui.properties/arc/BuildPropertySource.arc                         |      2 +
 src/org.xtuml.bp.ui.properties/generate.xml                                        |      2 +-
 .../src/org/xtuml/bp/ui/properties/RangeValuePropertyDescriptor.java               |     70 +
 src/org.xtuml.bp.welcome/models/xtUML_Metamodel.xtuml                              | 560913 ++++++++++++++++++------------------
 29 files changed, 282050 insertions(+), 280308 deletions(-)

Fork/Repository:  cortlandstarrett/mc
Branch:  5005_range
 arc/q.val.translate.arc                                    |  16 +----
 bin/mac/mcmc                                               | Bin 1565704 -> 1570440 bytes
 bin/mcmc                                                   | Bin 2374008 -> 2409384 bytes
 bin/win/mcmc.exe                                           | Bin 1963200 -> 1707840 bytes
 mcmc/arlan/o2.oal                                          |  16 +----
 model/escher/gen/ooaofooa.c                                |  51 ++++-----------
 model/escher/gen/ooaofooa_c_orig                           | 120 +++++++++++++++++------------------
 model/escher/models/escher/mcmc/afunval/afunval.xtuml      |  16 +----
 model/integrity/models/integrity/integrity/integrity.xtuml |  34 ++++++++++
 model/maslin/gen/masl2xtuml_S_UDT_class.c                  |  18 ++++++
 model/mcooa/gen/ooa.txt                                    |  34 ++++++++++
 model/mcooa/models/mcooa/ooaofooa/Domain/Domain.xtuml      | 216 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--
 model/mcooa/models/mcooa/ooaofooa/Domain/Range/Range.xtuml | 124 ++++++++++++++++++++++++++++++++++++
 schema/sql/xtumlmc_schema.sql                              |   6 ++
 14 files changed, 502 insertions(+), 149 deletions(-)

</pre>

### End
