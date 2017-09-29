`xtuml_integrity`
===================

This command line (and GUI) tool performs referential integrity checks on xtUML model data.

Syntax
------------
### Check Referential Integrity  
```
xtumlmc_build xtuml_integrity -i <model folder or file1> [-i <another>] [-g] [-m <accumulated model data>] [ -o <report file> ]
```
   
| Parameter                         | Description                                                                        |
|-----------------------------------|------------------------------------------------------------------------------------|
| -i &lt;model folder or file&gt;   | Specify an absolute or relative path to a folder or file of model (`.xtuml`) data. |
| -i &lt;another&gt;                | Optionally specify another folder or file of model data.                           |
| -g                                | Optionally skip the inclusion of global native types in the integrity check.       |
| -m &lt;accumulated model data&gt; | Optionally specify a file into which to store the accumulated model data.          |
| -o &lt;report file&gt;            | Optionally name an output referential integrity report file (instead of STDOUT).   |


Notes
------------
* The tool is located in the subdirectory ```<BridgePoint Home>/tools/mc/bin/```  
* Output defaults to the standard out (STDOUT).  
* Progress and error messages are reported to standard error (STDERR) (GUI Console).  
* Invoking the tool with missing parameters or syntax errors produces a message indicating proper usage.  
* Skipping global native types is useful when running with Prebuilder output which already includes these types.  
* The functionality of this tool is available within the graphical user interface (GUI) under the BridgePoint Utilities context menu.  
* The GUI invocation runs on all the projects inside the workspace no matter which project is selected at the time the tool is invoked.  The integrity report is stored in the doc/ folder of the first project in the workspace and is automatically opened in an editor for convenience.  
* An easy way to navigate to the erroneous model elements is to highlight the UUID and click the Search->File menu (or toolbar).  Narrow the search by searching `.xtuml` files.  A search view will appear which allows navigation from one find to the next.  


Examples
------------
```
./xtumlmc_build xtuml_integrity -i ~/workspace/MicrowaveOven/models -m integrity.sql -o integrity.txt  
./xtumlmc_build xtuml_integrity -i /home/user/ws/IPRproj1/models -i /home/user/ws/IPRproj2 -m integrity.sql -o integrity.txt  

```

Background
------------
Check Referential Integrity runs a model data consistency query.  Model
data is loaded directly from the file system and interrogated against a
set of rules and assertions.  Since model data is well-formed and must
be compliant with a data base schema, many checks can be made to detect
problems and corruption in the data.

Problems can be introduced into model data files in a number of ways.
The most common way to corrupt model data is through incorrect
configuration management merges.  Consistency errors can also be caused
by hand-editing model files without a full knowledge of the data format.

The `xtuml_integrity` tool is provided to detect these problems and
identify the erroneous model elements.  The following assertions are
made against the model data.

* The identifiers for all instances of a particular class must be
unique within the entire collection.  
* Traversal across all unconditional associations must result in a
related instance.  
* The values of referential attributes for instances participating in
associations must match the values of the referred-to identifier attributes.  
* Referential attributes formalizing associations should be null for
instances not participating in that association.  
* For every instance of a supertype exactly one subtype instance
should be found across the sub/super association.  
* For every instance of a subtype exactly one supertype instance should
be found across the sub/super association.  

Note that these checks require exhaustive interrogation of the instance
population.  Large instance populations can take some time to check due
to the complexity being greater than order N-squared (O(N^2)) for each
instance population.

It is recommended that the command line tool be set up to run against
model repositories as part of a continuous integration development process.
