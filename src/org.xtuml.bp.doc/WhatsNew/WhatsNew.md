What's New in BridgePoint 6.1.0
========================

Release Highlights
-------
* Marking Editor
* MASL
* Model Compilers
* Native platform builds (32 and 64 bit)

-------------------------------------------------------------------------------

Marking Editor
------
BridgePoint now supports a general purpose marking editor.  The editor relies on
feature markings specified by a model compiler architect.  The user then provides
application-specific marking values.      

![Marking Editor](marking_editor.png)
  
The BridgePoint documentation contains detailed information about the configuration
and use of the marking editor.  See Help > Help Contents > BridgePoint UML Suite Help > Reference > User Interface > Marking Editor.    


MASL
------
This release includes many improvements in the convert/import/edit/export flow of MASL 
models:    

  * Better handling of referential attributes during the model conversion process.   
  * Pragmas are now handled during conversion.  The imported model is marked using the new marking editor. The
  MASL export process now outputs the marks as pragmas.  
  * Command line export of MASL models is now supported.  
  * BridgePoint automatically creates graphics for converted models and automatically connects
  satisfactions in MASL Projects during import.
  * An example MASL Domain xtUML model is added to Help > Welcome > Quick Start.


Model Compilers
------
The model compiler files have moved from underneath individual plug-ins to the
top level folder ```<install dir>/tools/mc/```.   


Native Platform Builds
------
The BridgePoint tool build process has been improved to create 32 and 64-bit builds
specific to each platform.  This allows the tool to have better integration with 
the underlying OS.   