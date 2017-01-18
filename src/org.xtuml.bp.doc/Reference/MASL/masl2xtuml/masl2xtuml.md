masl2xtuml
===================

This command line tool converts a MASL domain or MASL project into an equivalent xtuml project 
based on parameter settings. 


Syntax
------------
### Domain Conversion
```
masl2xtuml -d <domain source directory(s) ...> [-o <output directory>]
```
   
### Package Conversion
```
masl2xtuml -p <project source directory(s) ...> [-o <output directory>]
```
   
| Parameter             | Description                          |
|-----------------------|--------------------------------------|
| -d &lt;domain source directory&gt;| Specify any relative or absolute directory path to a MASL domain |
| -p &lt;project source directory&gt;| Specify any relative or absolute directory path to a MASL project |
| -o &lt;output directory&gt; | Optionally specify the target folder to write to |  
  
     
Notes
------------
* The tool is located in the subdirectory ```<BridgePoint Home>/tools/mc/bin```
* Output defaults to the current directory
* Progress and error messages are reported to stderr
* A directory is produced containing a single xtUML model file and MASL activity files
* Invoking the tool with missing parameters or syntax errors produces a message indicating proper usage
  
  
Examples
------------
```
./masl2xtuml -d GPS_watch_MASL_files/ -o xtUML_output
```


