BridgePoint Profiler

References

The following sheet has initial research information for each product tested.

BridgePoint Profiler Research

The profilers that are going to be evaluated are:

[jvisualvm](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/jvisualvm.html)   
[YourKit](https://yourkit.com)   
[JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html)   
[IBM Health Center](https://www.ibm.com/developerworks/java/jdk/tools/healthcenter/)   

Jvisualvm

jvisualvm is provided with any installed JDK.  It is free.  This profiler does not have any IDE integration and runs entirely as its own application.  It can attach to running processes as well as remote processes if configured appropriately.  The UI is simple but elegant enough.  It is also intuitive enough for any user that understands profiling.

YourKit

YourKit is a widely used profiling tool for java applications.  It also includes eclipse integration which is added easily through the initial application installation.  This profiler costs $499 per single-user license with a bulk discount at 5 licenses for $1399.

JProfiler

JProfiler is a widely used profiling tool for java applications.  It also includes eclipse integration which is added easily through the initial application installation.  This profiler costs $499 per single-user license with a bulk discount at 4-7 licenses for $427/license.

IBM Health Center

This tool is provided as an eclipse extension by IBM.  It is free to use.

Hardware And Data Used For Evaluation

Host:   
MacBook Pro (15-inch, Late 2016)   
2.7 GHz Intel Core i7   
16 GB 2133 MHz LPDDR3    
Radeon Pro 455 2048 MB   
Intel HD Graphics 530 1536 MB   

[Installation](https://s3.amazonaws.com/xtuml-releases/release-build/20170127_v6.0.0/BridgePoint_v6.0.0_macosx.cocoa.x86_64.zipi)

Data:   
Workspace containing development models (without ooaofooa but with MASL).   

2) From xtuml/mc/model, import maslin, mcooa and mcshared.   
3) From xtuml/bridgepoint, import org.xtuml.bp.ui.marking.   

