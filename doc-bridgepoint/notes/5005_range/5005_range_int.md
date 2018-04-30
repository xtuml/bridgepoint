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

5.1.3 `xtumlmc_schema.sql`  
The schema for the model compilers needs to have the new class (Range)
and association.  The instructions for regenerating the schema are found
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
5.4.1 R3307  
In Verifier, Runtime Value is a key class used to store and move information
in the runtime instances of an executing model.  `R3307` links a Runtime Value
to a Data Type (`S_DT`).  

5.5 Analyze and document enforcement in model compilers.  
5.5.1 R820  
In MC-3020, Value (`V_VAL`) is the key class dealing with the dynamics of
data in action bodies.  `R820` links Value to Data Type (`S_DT`) (and thus
to User Data Type and Range).  Upon first analysis, it looks like R820 is
traversed in 3 places representing the 3 places where range checking should
be implemented.  
5.5.1.1 Assignment Statement  
A value moves from one value to another during an assignment.  A value moves
from the right-hand side (rhs) to left-hand side (lhs) across the assignment
operator ('=').  The left-hand value (lval) receives the right-hand value
(rval).
5.5.1.1.1 hmmm  
Research where this is necessary.  Depending upon type compatiblity,
this may be superfluous.  In some situations the rhs has already been checked
and is therefore O.K. to be applied to the lhs.  Immediate data assignment
is an example where the checking must occur.  
5.5.1.2 Binary Operation  
When two values are combined with an operator, a new value is produced.  The
new value type is discovered across R820.
5.5.1.3 Actual Parameters  

5.5 Implement test cases.  

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
All subsequent steps are the same as [7.2.1]
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

Fork/Repository:  cortlandstarrett/mc
Branch:  5005_range

Fork/Repository:  cortlandstarrett/models
Branch:  5005_range
</pre>

### End


<pre>
TODO:
+ Change model to have keyletters S_RANGE to be consistent with existing model.
+ Change Range attributes from min and max to Min and Max.
+ Use role phrases from mcooa version (thus fixing misspelling).
[keep] Revert change to MC-Java/referential_attribute_util.inc.
[keep] Revert change to bp.core/arc/create_object_inspector.inc.
+ Change mcooa to use R57.
+ Switch from real to string and perform string-to-real in the java.
+ Update mc schema.
+ Regen integrity OAL.
+ Rebuild mcmc.
Update MM welcome project.
</pre>
