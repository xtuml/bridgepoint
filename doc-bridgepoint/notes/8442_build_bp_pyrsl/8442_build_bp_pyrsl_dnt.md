---

This work is licensed under the Creative Commons CC0 License

---

# Build BridgePoint with pyrsl/pyxtuml
### xtUML Project Design Note


1. Abstract
-----------
This note describes the work performed to allow BridgePoint to be built
with the new generator (pyrsl/pyxtuml).

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8442](https://support.onefact.net/issues/8442) Headline issue  
<a id="2.2"></a>2.2 [pyrsl](https://github.com/xtuml/pyrsl) pyrsl github repository  
<a id="2.3"></a>2.3 [pyxtuml](https://github.com/xtuml/pyxtuml) pyxtuml github  repository
<a id="2.4"></a>2.4 [VC++-based generator](https://github.com/xtuml/generator) generator github  repository  

3. Background
-------------
BridgePoint is deprecating the VC++-based generator [2.4] in favor of a new 
python-based generator (pyrsl [2.2] & pyxtuml [2.3]).  BridgePoint Community 
Edition 5.3.4 installs `gen_erate.pyz` as the default generator on Linux. This 
generator has proven to successfully generate code for application models in
our testing over the past several months.   

However, one issue remains: BridgePoint itself cannot be built with the new
generator.  The new generator is more strict in the way it interprets RSL 
syntax.  Additionally, it does not allow for relationship traversal where any
of the referential attributes are empty.

4. Requirements
---------------
4.1  All BridgePoint plug-ins (source code and test) shall build with new 
  generator without error.  
4.2  BridgePoint Junit tests shall complete with the same level of pass/fail
  rate as for recent builds of the testing branch with old generator.   
  
5. Analysis
-----------
5.1  John Tornblom identified some problems in MC-Java archetypes using the new 
  generator [TODO] [TODO].  The new working branch `8442_build_bp_pyrsl` shall
  either start with these changes or take them under advisement.    


6. Design
---------
6.1  General syntax fixes and changes.  Most of these affect more than one 
  archetype.    

6.1.1  Declare variables using `select where ... (false)`.  This is used to widen
  the applicable scope of an RSL transient, which is now enforced strictly.  
  
6.1.2  Remove trailing `;` on lines.  RSL syntax does not use semi-colon to denote
  the end of a line.  Remove it in places where it exists.  
  
6.1.3  Make sure variable name is lower-case as a filename substitution.  In 
  `MC-Java/java.arc` the references to marking files via substitution variables
  are forced to be lowercase to accomodate strict casing on Unix system.    
  
6.1.4  A relationship traversal was specified using invalid syntax `-.`.  Change
  to valid syntax `->`.  
  
6.1.5  Parenthesize calls to cardinality operator in expressions.  

6.1.6  Fix invalid usage of ".body" when passing a string variable to an 
  operation.  We simply need to pass the variable itself without the `.body` 
  fragment reference.  
   
6.1.7  Remove invalid extra ")" on if statement  

6.1.8  Add extra containing parens on boolean expressions as if statement test  

6.1.9  Fix syntax on bad equality test if statement test  

6.1.10  Change include path to use "/" instead of "\"  

6.1.11  Add containing parens on where test of select statement  

6.1.12  Fix RSL comments that should use ".//" but were "//"  

6.1.13  Fix invalid relationship specification "E4503" > "R4503"  

6.1.14  Rename function parameter "cardinality" to "cardinality_" because the 
  former is a keyword  
   
6.1.15  In SQL file, add extra ' to escape use in a comment change
```
 '/' to ''/'' 
```  

6.2  In several archetype files under `bp.core`, we changed R2012 traversal to 
  not rely on referential attributes.  For example, changed from:  
  `.select one cme related by wfl->CME[R2012]`  
  to  
  `.select any cme from instances of CME where ((selected.Specialism == wfl.Specialism) and (selected.Label == wfl.Label) and (selected.Key_Lett == wfl.Key_Lett))`   

6.3  Fixed a number of archetypes that were doing a select against 
  `Key_Lett == "S_DOM"` then trying to do an operation on the empty returned 
  instance.  These caused pyrsl to exit with error and stop generating code.  

6.4  Modified MC-Java to not create a schema and instead copied the latest MC 
  schema to `MC-Java/ooa_schema.sql` and committed it.  We had to add attributes
  to the end of a number of classes as specified in `MC-Java/schema_gen.arc`. 
  These attributes are used during the BridgePoint build process.  

6.5  Modified `MC-Java/build.xml` to always include the file_io schema when
  generating code because pyrsl does not allow selects against classes that do
  not exist in the in-memory schema and MC-Java has some selects against File IO
  classes.  The old generator simply returned empty transients for these 
  selections, new generator exits with error.  

6.6  In `ui.properties.test/arc/create_view_test.arc` we changed R525 related-
  by selections to "select any where" selections with referential attribute 
  equality checks.  `SM_EVT.SMspd_ID` is 0 in our PEI data and cannot be used in
  the new generator for "select related by" traversals.  

6.7  In some of the plug-in builds the exported ooaofooa (from bp.core) model 
  data is used along with the exported ooaofgraphics (from bp.ui.canvas) model
  data.  When these files are created they go through the 
  `xtuml_cleanse_for_BridgePoint` process in the xtumlmc_build script.  Part of
  this process is to replace UUIDs with long values.  The exported files are
  then combined in memory by the generator (using multiple `-import` 
  arguments).  For example, the `ooaofooa-1.sql` contains:     
  
```
INSERT INTO O_OBJ
       VALUES (5,
        'Activity Node',
        ...
```   
  while `ooaofgraphics.sql` contains:  
```
INSERT INTO O_OBJ
        VALUES (5,
         'Model Tool',
         ...
```   

  The new generator is different than old generator in that the old
  generator reassigned long UUIDs on import.  The new generator does not
  reassign IDs which led to data collision of the in-memory instances.    

  Generator imports the two sql files, then runs an archetype.  The UUID 
  collision caused a set selection for elements related to Activity Node to come
  back with wrong data.  The set also contains data related to Model Tool.   

  We modified the xtumlmc_build script to take an "UUID seed" that over-rides 
  `$nextInternalUniqueID` value that defines the starting value for long-based
  IDs. Thus, we made the creation of `ooaofgraphics.sql` seed with a high 
  number (500000) to avoid ID collisions.  The changes are:    

```perl
  my $input_path = shift;
+ # If the first argument is -u, handle the UUID seed argument 
+  if ( $input_path eq '-u' ) { 
+    $nextInternalUniqueID = shift;
+    $input_path = shift;
+  }   
  $SF_Out = xtumlmc_open( ">" . shift );
  @ClassesToRemove = @_;
```  

- fixed (not_)[first | last] operators in pyrsl.  Updated pyrsl tests for these operators

- modified pyxtuml to not treat derived attributes as a special case when giving new class instance attributes default values


pypy call in xtumlmc_build.exe

? add newline on last statement RSL statement in files

7. Design Comments
------------------

8. User Documentation
---------------------

9. Unit Test
------------
9.1  The JUnit tests of the BridgePoint plug-ins shall complete with the same 
  success rate as a current build of the testing branch.  

End
---

