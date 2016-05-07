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
<a id="2.5"></a>2.5 [SMspd_ID removal instructions](https://support.onefact.net/issues/7751#note-18)   
<a id="2.6"></a>2.6 [MC-Java: various syntax errors encountered by pyrsl](https://support.onefact.net/issues/8442) - branch john-tornblom:7940_mc_java_fixes_for_pyrsl   
<a id="2.7"></a>2.7 [Syntax fix for unbalanced if statement](https://support.onefact.net/issues/7969)   
<a id="2.8"></a>2.8 [last and not_last do not seem to work](https://github.com/xtuml/pyrsl/issues/2)   

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
  generator [2.6] & [2.7].  The new working branch `8442_build_bp_pyrsl` shall
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
  the new generator for "select related by" traversals.  Note that in the MC
  schema `SMspd_ID` has been deprecated.  Eventually we will do the same in 
  the BridgePoint metamodel following the instructions in [2.5].

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
  
6.8  The default python interpreter is very slow when running generator on
  bp.core.  With old generator the build time was about 20 mins for that plugin. 
  With the new generator the build time ballooned to 85 mins.  John Tornblom 
  suggested using a different python interpreter named "pypy".  I modified 
  `xtumlmc_build.exe` to invoke gen_erate.pyz using pypy if pypy is available
  on the host system.  

```
# HOWTO install pypy on ubuntu 14.04 LTS
$ sudo add-apt-repository ppa:pypy/ppa
$ sudo apt-get update
$ sudo apt-get install pypy pypy-dev
# Put the updated <git>/mc/bin/xtumlmc_build (from branch 8442_build_bp_pyrsl) 
# into ~/git/bridgepoint/src/org.xtuml.bp.mc.c.source/mc3020/bin/xtumlmc_build.exe 
```   
  
  Using pypy the bp.core build time went down to ~25 mins.  

6.9  On several archetype files, new generator threw and error about unexpected
  EOF.  The issue was resolved by adding a newline onto the last RSL 
  statement.

6.10  Modified `bp.ui.text/arc/create_modeladapter_java.arc`  
  Moved nested for loop out to be a sibling for loop.  The nested loop did not 
  need to be nested, and in fact, was not being processed by the pyrsl build
  because the set of elements in the outer loop was being processed in a 
  different order than old generator.  When I move the loop out, the generated 
  code is identical between new and old generator.   

6.11  Modified pyxtuml to not treat derived attributes as a special case when 
  giving new class instance attributes default values.  
  
  The problem I hit is due to the fact that `T_TPS.Prev_TPS_ID` is a referential
  attribute and thus is a derived attribute. Our PEI data in 
  `ooaofooa_heirarch.pei.sql` does not specify values for every field. When 
  pyxtuml loads the data it sets the value of `T_TPS.Prev_TPS_ID` to the python 
  value "None" rather than "0", even though the field is an INTEGER. It does 
  this because it is a derived attribute (see https://github.com/keithbrown/pyxtuml/blob/8442_build_bp_pyrsl/xtuml/model.py#L480). 
  Here `inst.__a__` is the set of attributes and `inst.__d__` is the set of 
  derived attributes in a given class (refer to line 280 in the same file).  

  pyxtuml behaviour is different than old generator behavior here, so I think 
  that pyxtuml should not treat derived attributes specially and should just use
  whatever type-based default is done for non-derived attributes.  

  We could update our PEI data to include values for all fields instead of 
  relying to generator to do the right thing. A script or small parser could 
  make sure it was fully filled out. However, sometimes it is just cleaner to 
  read to leave out empty fields. A post-processing to go from readable/editable
  to syntactically correct is probably a good way to go (given time).  
    
6.12  Fixed (not_)[first | last] operators in pyrsl.  Updated pyrsl tests for 
  these operators. [2.8]  The internal symbols used by the for loop were 
  conflicting with "select where" and nested for loops.    


7. Design Comments
------------------
7.1  Instructions for getting set up to develop and build new generator:   
```
# Fork xtuml/pyrsl and xtuml/pyxtuml github repositories to my user account
$ sudo apt-get install python-ply
$ git clone https://github.com/keithbrown/pyxtuml.git
$ cd pyxtuml
$ sudo python setup.py install
$ git clone https://github.xtuml/keithbrown/pyrsl.git
$ cd pyrsl
$ sudo python setup.py install
# Create branch on pyxtuml (and pyrsl) as appropriate
# Make changes, commit and push them
$ cd <git>/pyrsl
$ ./package_pyz.sh -gituser <github username> -pyxtuml <git branch name> -pyrsl <git branch name>
- Copy created gen_erate.pyz into mc3020/bin folder to use it
```  

7.2 Updated the build scripts to stop moving `gen_erate.pyz` out of the way for
  the linux-based server build.  We now have the ability to use it to build
  BridgePoint on linux, so this change leaves it in place where it can be used.  

8. User Documentation
---------------------

9. Unit Test
------------
9.1  Run built-in unit tests for pyrsl.
```
$ git clone https://github.com/xtuml/pyrsl.git
$ cd pyrsl
$ python setup.py test
```   

9.2  The JUnit tests of the BridgePoint plug-ins shall complete with the same 
  success rate as a current build of the testing branch.  

End
---

