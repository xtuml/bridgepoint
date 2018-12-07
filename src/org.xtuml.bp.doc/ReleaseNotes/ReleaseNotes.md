BridgePoint xtUML Release Notes
========================

Release 6.16

License
-------
BridgePoint is licensed under the [Apache 2.0 License Agreement](http://www.apache.org/licenses/LICENSE-2.0).  


System Requirements
-------
  
| Category              | Requirement                          |
|-----------------------|--------------------------------------|
| Operating System      | Linux, MacOS, Windows                |
| *Eclipse              | Version 4.7                          |
| *Java Virtual Machine | Version 8 or later                   |

*(supplied by installer, except JVM not supplied on MacOS)
    

General Notes
------
* A summary of the release highlights and new functionality is available on the [What's New](../WhatsNew/WhatsNew.html) page.  
  
* __IMPORTANT__: BridgePoint is completely open source software. Please see [xtUML.org](http://xtuml.org) and [onefact.net](http://onefact.net) 
for ongoing development and support of BridgePoint.  
  
* BridgePoint ships as a zip file, similar to standard eclipse packages. Users who wish to 
create desktop shortcuts to start BridgePoint should set the shortcut to start `<install dir>/bridgepoint`.  

* BridgePoint supports two different action languages: OAL and MASL.  The default is set via workspace 
preferences at `xtUML > Action Language > Default Action Language Dialect`. The default is set to OAL.       
  
Interoperability with Previous Releases
------
BridgePoint 6.16 is compatible with workspaces and projects created with prior versions back to 
BridgePoint 5.0.  However, after opening the model in this version a modeler will not be able to 
open the model in a prior version.   

Changelog / Roadmap
------
For a complete list of changes for this and all completed BridgePoint Pro releases see the 
[BridgePoint roadmap](https://support.onefact.net/projects/bridgepoint/roadmap?utf8=%E2%9C%93&completed=1) in the issue tracking system.  

Closed Issues
------
 
| Issue |  Subject |
|-------|-----------------------------------------------------------------|   
[7674](https://support.onefact.net/issues/7674) |   Incorrect setting of referential attribute values for reflexive associations in generated code   
[7685](https://support.onefact.net/issues/7685) |   Graphically identify class-based operations   
[7965](https://support.onefact.net/issues/7965) |   Parent task for issues related replacing gen_erate with pyxtuml   
[8518](https://support.onefact.net/issues/8518) |   Supply documentation on idiom for types and typerefs to customer   
[8802](https://support.onefact.net/issues/8802) |   Test use of tics in string fields in MASL flow   
[8965](https://support.onefact.net/issues/8965) |   Eclipse is slow and hangs when running on Ubuntu 16.04   
[9481](https://support.onefact.net/issues/9481) |   Build BridgePoint with new generator   
[9799](https://support.onefact.net/issues/9799) |   Can't set initializer function   
[9824](https://support.onefact.net/issues/9824) |   mcmc.exe used in Windows   
[9911](https://support.onefact.net/issues/9911) |   Build BridgePoint with python generator 1.0   
[10010](https://support.onefact.net/issues/10010) |  Move to eclipse oxygen   
[10232](https://support.onefact.net/issues/10232) |  OAL Editor fixes and improvements   
[10252](https://support.onefact.net/issues/10252) |  Add simulated time to MC-3020.   
[10253](https://support.onefact.net/issues/10253) |  Add state save to MC-3020.   
[10254](https://support.onefact.net/issues/10254) |  Class-specific Tracing   
[10255](https://support.onefact.net/issues/10255) |  Tag interface messages as safe for interrupts.   
[10265](https://support.onefact.net/issues/10265) |  Bump version to 6.15   
[10276](https://support.onefact.net/issues/10276) |  Batch relate fails with string instance identifiers   
[10277](https://support.onefact.net/issues/10277) |  Deprecate Nucleus support in MC-3020.   
[10278](https://support.onefact.net/issues/10278) |  Stop using org.eclipse.jface.util.ListenerList   
[10279](https://support.onefact.net/issues/10279) |  Change the separator character for range values in the canvas from ‘,’ to ‘..’   
[10280](https://support.onefact.net/issues/10280) |  OAL completion contains invalid suggestions for constants   
[10296](https://support.onefact.net/issues/10296) |  integrity checker archetype generates incorrect OAL   
[10300](https://support.onefact.net/issues/10300) |  MC-3020 fails to load reflexive associatives   
[10301](https://support.onefact.net/issues/10301) |  Fix missing O_NBATTRs in mcooa.   
[10304](https://support.onefact.net/issues/10304) |  Referential integrity error for two similar instances of Satisfaction   
[10307](https://support.onefact.net/issues/10307) |  Import RSL/OAL functions more intelligently.   
[10327](https://support.onefact.net/issues/10327) |  Polymorphic event sent to self within subtype is silently dropped by generated code   
[10333](https://support.onefact.net/issues/10333) |  Make keyed timers the default.   
[10337](https://support.onefact.net/issues/10337) |  JUnit test build and run cleanup   
[10338](https://support.onefact.net/issues/10338) |  Move BridgePoint build to pyrsl 2.1.0   
[10341](https://support.onefact.net/issues/10341) |  JUnit test cleanup   
[10342](https://support.onefact.net/issues/10342) |  Add Makefile to integrity   
[10346](https://support.onefact.net/issues/10346) |  Update build server images with the oxygen build   
[10347](https://support.onefact.net/issues/10347) |  te_eq.allocate in multi-directional subtyping   
[10348](https://support.onefact.net/issues/10348) |  call to deleted function in persistence support   
[10349](https://support.onefact.net/issues/10349) |  Update sumo application   
[10351](https://support.onefact.net/issues/10351) |  Bring back MC-3020 Persistence   
[10352](https://support.onefact.net/issues/10352) |  Bus Error in interleave bridge when building for ARM with -Os   
[10355](https://support.onefact.net/issues/10355) |  Provide compile-time indication that simulated time is engaged   
[10357](https://support.onefact.net/issues/10357) |  GTK3 is working on Linux now   
[10359](https://support.onefact.net/issues/10359) |  Install Rover Simulator on Windows VM   
[10360](https://support.onefact.net/issues/10360) |  Enhance sumo robot application.   
[10362](https://support.onefact.net/issues/10362) |  Splash screen and icon wrong on Windows.   
[10366](https://support.onefact.net/issues/10366) |  C model compiler generates extraneous parentheses   
[10374](https://support.onefact.net/issues/10374) |  SS_bridge.c leaves output file open   
[10475](https://support.onefact.net/issues/10475) |  Move old training materials from svn to git.   
[10510](https://support.onefact.net/issues/10510) |  CDT builder out of order on new project   
[10519](https://support.onefact.net/issues/10519) |  Cannot assign initialization message for component references   
[10529](https://support.onefact.net/issues/10529) |  v6.16 Release   

    
Known Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). Please check here for known issues and to 
submit bug reports and requests for new features. This issue tracker contains [all open bugs and feature requests](https://support.onefact.net/projects/bridgepoint/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=%3D&v%5Bstatus_id%5D%5B%5D=1&v%5Bstatus_id%5D%5B%5D=7&v%5Bstatus_id%5D%5B%5D=2&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&group_by=&t%5B%5D=).  

  
