xtuml2masl
===================

This command line tool converts an xtUML package into an equivalent MASL domain
or MASL project/deployment depending on parameter settings. The same tool
supports export of WASL.


Syntax
------------
### Domain Export
```
xtuml2masl [-v | -V] [-c] [-P] [-xf] [-xl] [-a <architecture>] -i <eclipse project location> -d <domain component> [ -o <output directory> ]
```

### Project/Deployment Export
```
xtuml2masl [-v | -V] [-c] [-P] [-xf] [-xl] [-a <architecture>] -i <eclipse project location> -p <project/deployment package> [ -o <output directory> ]
```

| Parameter                             | Description                                                                                                  |
|---------------------------------------|--------------------------------------------------------------------------------------------------------------|
| -v -V                                 | Optionally print help message                                                                                |
| -c                                    | Optionally used to enable the production of coverage statistics                                              |
| -P                                    | Optionally used to cause BridgePoint prebuilder to be used to export model data                              |
| -xf                                   | Optionally skip the formatting step                                                                          |
| -xl                                   | Optionally exclude action langauge from export                                                               |
| -a &lt;architecture&gt;               | Specify the output architecture. "MASL" and "WASL" are valid options. "MASL" is the default.                 |
| -i &lt;eclipse project location&gt;   | Specify an absolute or relative path to the root directory of the Eclipse project containing the input model |
| -d &lt;domain component&gt;           | Specify xtUML component that will be exported as a MASL domain                                               |
| -p &lt;project/deployment package&gt; | Specify xtUML package that will be exported as a MASL project/deployment                                     |
| -o &lt;output directory&gt;           | Optionally specify the target folder to write to                                                             |


Notes
------------
* The tool is located in the subdirectory ```<BridgePoint Home>/tools/mc/bin```
* Output defaults to the current directory
* Progress and error messages are reported to stderr
* Invoking the tool with missing parameters or syntax errors produces a message indicating proper usage


Examples
------------
```
./xtuml2masl -i workspace/Tracking -d Tracking -o TrackingMASL_OOA

./xtuml2masl -i ~/my_ws/GPSWatchMASL -p GPSWatch -o GPS_PROC
```
