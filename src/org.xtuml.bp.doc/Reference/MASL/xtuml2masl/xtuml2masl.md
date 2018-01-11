xtuml2masl
===================

This command line tool converts an xtUML package into an equivalent MASL domain or MASL 
project depending on parameter settings.


Syntax
------------
### Domain Export
```
xtuml2masl [-v | -V] [-e] [-xf] [-xl] -i <eclipse project> -d <domain component> [ -o <output directory> ]
```
   
### Package Export
```
xtuml2masl [-v | -V] [-e] [-xf] [-xl] -i <eclipse project> -p <project package> [ -o <output directory> ]
```
   
| Parameter             | Description                          |
|-----------------------|--------------------------------------|
| -v -V                 | Optionally print help message                   |
| -e                    | Optionally used to indicate the tool is called from eclipse and pre-builder should not be run  |
| -xf                   | Optionally skip the formatting step |
| -xl                   | Optionally exclude action langauge from export |
| -i &lt;eclipse project&gt; | Specify an absolute or relative path to the root directory of the Eclipse project containing the input model |
| -d &lt;domain component&gt;| Specify xtUML component that will be exported as a MASL domain |
| -p &lt;project package&gt;| Specify xtUML package that will be exported as a MASL project |
| -o &lt;output directory&gt; | Optionally specify the target folder to write to |  
  
     
Notes
------------
* The tool is located in the subdirectory ```<BridgePoint Home>/tools/mc/bin```
* Output defaults to the current directory
* ```WORKSPACE``` variable in your shell environment must be set to the path to your eclipse workspace
* Progress and error messages are reported to stderr
* Invoking the tool with missing parameters or syntax errors produces a message indicating proper usage
  
  
Examples
------------
```
./xtuml2masl -i workspace/Tracking -d Tracking -o TrackingMASL_OOA

./xtuml2masl -i ~/my_ws/GPSWatchMASL -p GPSWatch -o GPS_PROC
```


