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
`min` and `max`.  The new attributes are typed as real to handle the
full minumum and maximum values.  The UI handles validation with type
determination (integer versus real).

The same model change is made to both `ooaofooa` and `mcooa`.

5.2 Update editor.  
5.2.1 UI Range configuration  
A new menu along with three sub menus are added. These are:  
  * Range  
  * Range > Minimum...  
  * Range > Maximum...  
  * Range > Clear Range  

The Range top level menu shows as long as the selection contians only UDTs
that have a core type of integer, real or Enumeration Data Type.  The `Clear
Range` menu item will show only as long as one of the selected User Data
Type has an already associated Range.  The Range menu will not show when
mixing integer or enumeration data type with real.  This is due to the fact
that a real value can be invalid for an integer value.

The `Minimum...` and `Maximum...` menu items open an input dialog.  This
dialog assures that the input value is valid.  In the case of integer the
string value is parsed.  If a `NumberFormatException` is thrown, a message
is given indicating an invalid entry.  In the case of a real, the value
is parsed and the given real (float) is checked to be a number (`NaN`) and
infinite exceptions.  The same message is given for the invalid real.

5.2.2 Add range data to compare and persistence.  
Three new entries are added to `ooaofooa_hierarchy.pei.sql` to cover
external attributes for the UDT class.  An external attribute is used
to display an attribute from another class under the one associated,
in this case the min and max values from the Range class associated
with the User Data Type.  This change allows the range values to show
in compare.  Note, these are not available in the properties view.

The `file_io.pei` and `stream.pei` data is modified to add a new child
to User Data Type, Range.

5.3 Analyze and document enforcement in Verifier.  
5.3.1 R3307  
In Verifier, Runtime Value is a key class used to store and move information
in the runtime instances of an executing model.  `R3307` links a Runtime Value
to a Data Type (`S_DT`).  

5.4 Analyze and document enforcement in model compilers.  
5.4.1 R820  
In MC-3020, Value (`V_VAL`) is the key class dealing with the dynamics of
data in action bodies.  `R820` links Value to Data Type (`S_DT`) (and thus
to User Data Type and Range).  Upon first analysis, it looks like R820 is
traversed in 3 places representing the 3 places where range checking should
be implemented.  
5.4.1.1 Assignment Statement  
A value moves from one value to another during an assignment.  A value moves
from the right-hand side (rhs) to left-hand side (lhs) across the assignment
operator ('=').  The left-hand value (lval) receives the right-hand value
(rval).
5.4.1.1.1 hmmm  
Research where this is necessary.  Depending upon type compatiblity,
this may be superfluous.  In some situations the rhs has already been checked
and is therefore O.K. to be applied to the lhs.  Immediate data assignement
is an example where the checking must occur.  
5.4.1.2 Binary Operation  
When two values are combined with an operator, a new value is produced.  The
new value type is discovered across R820.
5.4.1.3 Actual Parameters  

5.5 Implement test cases.  

### 6. Implementation Comments

### 7. Unit Test

Outline all the unit tests that need to pass and describe the method that you
will use to design and perform the tests. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

### 8. User Documentation

[add something to UI documentation to explain how to constrain a type.]

### 9. Code Changes

<pre>
Fork/Repository:  cortlandstarrett/bridgepoint
Branch:  5005_range

Fork/Repository:  cortlandstarrett/mc
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
Change mcooa to use R57.
Switch from real to string and perform string-to-real in the java.
</pre>
