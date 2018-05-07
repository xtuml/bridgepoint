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
<a id="2.2"></a>2.2 [analysis note](https://github.com/xtuml/bridgepoint/doc-bridgepoint/notes/5005_range/5005_range_ant.md) Ranges of data types  
<a id="2.3"></a>2.3 [10220](https://support.onefact.net/issues/10220) Support UDTs based on EDTs in MC-3020  
<a id="2.4"></a>2.4 [10221](https://support.onefact.net/issues/10221) Analyze UDT range enforcement.  

### 3. Background

See [2.2].

### 4. Requirements

See [2.2].

### 5. Work Required

5.1 Update metamodels (`ooaofooa` and `mcooa`).  
5.1.1 Add Range Class  
A new class `Range (S_RANGE)` is added to the Domain package under ooaofooa.
This class has a conditional one to one relationship according to the
analysis note [2.2] for [2.1].

A range has minumum and maximum values.  These are added as attributes
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
project.  `masl2xtuml_S_UDT_class.c` is modified to updated the identifiers
of data types during the import process.  This hand-edited file needs to be
refreshed because of the association across R57 to the new Range (`S_RANGE`)
class.

5.2 Update the editor.  
5.2.1 UI Range configuration  
A new menu along with three sub menus are added. These are:  
  * Range  
  * Range > Minimum...  
  * Range > Maximum...  
  * Range > Clear Range  

The Range top level menu shows as long as the selection contians only UDTs
that have a core type of integer, real or Enumeration Data Type.  The `Clear
Range` menu item will show only as long as one of the selected User Data
Types has an already associated Range.

The `Minimum...` and `Maximum...` menu items open an input dialog.  This
dialog assures that the input value is valid.  In the case of integer the
string value is parsed.  If a `NumberFormatException` is thrown, a message
is given indicating an invalid entry.  In the case of a real, the value
is parsed and the given real (float) is checked to be a number (`NaN`) and
for infinite exceptions.  The same message is given for the invalid real.

5.2.2 Add range data to compare and persistence.  
Three new entries are added to `ooaofooa_hierarchy.pei.sql` to cover
external attributes for the UDT class.  An external attribute is used
to display an attribute from another class under the one associated,
in this case the Min and Max values from the Range class associated
with the User Data Type.  This change allows the range values to show
in compare.  Note, these are not available in the properties view.

The `file_io.pei` and `stream.pei` data is modified to add a new child
to User Data Type, Range.

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
7.2.1 Integer Ranges  
<pre>
_- Import the Example Application MicrowaveOven example model from
   Help --> Welcome --> QuickStart  
_- Navigate to the 'Datatypes' package.  
_- Add User Data Type 'i'.  
_R Be sure type of 'i' is integer.  
7.2.1.1 Range Menu  
_- Right-click on 'i' and navigate to Range  
_R See Range --> Minimum... and Range --> Maximum...  
_R Do NOT see Range --> Clear Range.  
7.2.1.2 Setting Incorrect Integer Range Minimum  
_- Right-click on 'i' and navigate to Range --> Minimum...  
_R See empty input text box.  
_- Set value in box to 0.1  
_R See message that input is not valid.  
_R See that OK button is not activated.  
7.2.1.3 Setting Correct Integer Range Minimum  
_- Set value in box to 0  
_R See that OK button is activated.  
_- Press OK.  
7.2.1.4 Reading Existing Integer Range Minimum  
_- Right-click on 'i' and navigate to Range --> Minimum...  
_R See text box with value input from before (0).  
_R See value input from before (0) on UDT on canvas.  
_R See value input from before (0) in Properties view.  
7.2.1.5 Range Menu with Clear Range  
_- Right-click on 'i' and navigate to Range  
_R See Range --> Minimum... and Range --> Maximum... and Range --> Clear Range.  
7.2.1.6 Setting Incorrect Integer Range Maximum  
_- Right-click on 'i' and navigate to Range --> Maximum...  
_R See empty input text box.  
_- Set value in box to 7.7  
_R See message that input is not valid.  
_R See that OK button is not activated.  
7.2.1.7 Setting Correct Integer Range Maximum  
_- Set value in box to 7  
_R See that OK button is activated.  
_- Press OK.  
7.2.1.8 Reading Existing Integer Range Maximum  
_- Right-click on 'i' and navigate to Range --> Maximum...  
_R See text box with value input from before (7).  
_R See value input from before (7) on UDT on canvas.  
_R See value input from before (7) in Properties view.  
7.2.1.9 Clearing Integer Range  
_- Right-click on 'i' and navigate to Range --> Clear Range  
_R See Range --> Minimum... and Range --> Maximum...  
_R Do NOT see Range --> Clear Range.  
_- Right-click on 'i' and navigate to Range --> Minimum...  
_R See empty input text box.  
_- Right-click on 'i' and navigate to Range --> Maximum...  
_R See empty input text box.  
</pre>

7.2.2 Real Ranges  
This test is exactly the same as the above [7.2.1] test except that
decimal numbers are allowed in the input text boxes.  
<pre>
_- Navigate to the 'Datatypes' package.  
_- Add User Data Type 'r'.  
_- Set Type of 'r' to 'real'.  
7.2.2.1 Range Menu  
_- Right-click on 'r' and navigate to Range  
_R See Range --> Minimum... and Range --> Maximum...  
_R Do NOT see Range --> Clear Range.  
7.2.2.2 Setting Incorrect Real Range Minimum  
_- Right-click on 'r' and navigate to Range --> Minimum...  
_R See empty input text box.  
_- Set value in box to 'abc'.  
_R See message that input is not valid.  
_R See that OK button is not activated.  
7.2.2.3 Setting Correct Real Range Minimum  
_- Set value in box to 2.7  
_R See that OK button is activated.  
_- Press OK.  
7.2.2.4 Reading Existing Real Range Minimum  
_- Right-click on 'r' and navigate to Range --> Minimum...  
_R See text box with value input from before (2.7).  
7.2.2.5 Range Menu with Clear Range  
_- Right-click on 'r' and navigate to Range  
_R See Range --> Minimum... and Range --> Maximum... and Range --> Clear Range.  
7.2.2.6 Setting Incorrect Real Range Maximum  
_- Right-click on 'r' and navigate to Range --> Maximum...  
_R See empty input text box.  
_- Set value in box to "dog"  
_R See message that input is not valid.  
_R See that OK button is not activated.  
7.2.2.7 Setting Correct Real Range Maximum  
_- Set value in box to 3.14  
_R See that OK button is activated.  
_- Press OK.  
7.2.2.8 Reading Existing Real Range Maximum  
_- Right-click on 'r' and navigate to Range --> Maximum...  
_R See text box with value input from before (3.14).  
7.2.2.9 Clearing Real Range  
_- Right-click on 'r' and navigate to Range --> Clear Range  
_R See Range --> Minimum... and Range --> Maximum...  
_R Do NOT see Range --> Clear Range.  
_- Right-click on 'r' and navigate to Range --> Minimum...  
_R See empty input text box.  
_- Right-click on 'r' and navigate to Range --> Maximum...  
_R See empty input text box.  
</pre>

7.2.3 Enumeration Ranges  
Enumerations can have integer range constraints.  Therefore,
this test is the same as the above [7.2.1] integer range test.  
<pre>
_- Add User Data Type 'e'.  
_- Set Type of 'e' to 'tube_wattage'.  
7.2.3.1 Range Menu  
_- Right-click on 'i' and navigate to Range  
_R See Range --> Minimum... and Range --> Maximum...  
_R Do NOT see Range --> Clear Range.  
7.2.3.2 Setting Incorrect Enumeration Range Minimum  
_- Right-click on 'e' and navigate to Range --> Minimum...  
_R See empty input text box.  
_- Set value in box to 0.1  
_R See message that input is not valid.  
_R See that OK button is not activated.  
7.2.3.3 Setting Correct Enumeration Range Minimum  
_- Set value in box to tube_wattage::low    
_R See that OK button is activated.  
_- Press OK.  
7.2.3.4 Reading Existing Enumeration Range Minimum  
_- Right-click on 'e' and navigate to Range --> Minimum...  
_R See text box with value input from before (tube_wattage::low).  
_R See value input from before (tube_wattage::low) on UDT on canvas.  
_R See value input from before (tube_wattage::low) in Properties view.  
7.2.3.5 Range Menu with Clear Range  
_- Right-click on 'e' and navigate to Range  
_R See Range --> Minimum... and Range --> Maximum... and Range --> Clear Range.  
7.2.3.6 Setting Incorrect Enumeration Range Maximum  
_- Right-click on 'e' and navigate to Range --> Maximum...  
_R See empty input text box.  
_- Set value in box to 7.7  
_R See message that input is not valid.  
_R See that OK button is not activated.  
7.2.3.7 Setting Correct Enumeration Range Maximum  
_- Set value in box to tube_wattage::med_low  
_R See that OK button is activated.  
_- Press OK.  
7.2.3.8 Reading Existing Enumeration Range Maximum  
_- Right-click on 'e' and navigate to Range --> Maximum...  
_R See text box with value input from before (tube_wattage::med_low).  
_R See value input from before (tube_wattage::med_low) on UDT on canvas.  
_R See value input from before (tube_wattage::med_low) in Properties view.  
7.2.3.9 Clearing Enumeration Range  
_- Right-click on 'e' and navigate to Range --> Clear Range  
_R See Range --> Minimum... and Range --> Maximum...  
_R Do NOT see Range --> Clear Range.  
_- Right-click on 'e' and navigate to Range --> Minimum...  
_R See empty input text box.  
_- Right-click on 'e' and navigate to Range --> Maximum...  
_R See empty input text box.  
</pre>


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
