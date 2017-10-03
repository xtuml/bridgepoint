BridgePoint xtUML Release Notes
========================

Release 6.6.0

License
-------
BridgePoint is licensed under the [Apache 2.0 License Agreement](http://www.apache.org/licenses/LICENSE-2.0).  


System Requirements
-------
  
| Category              | Requirement                          |
|-----------------------|--------------------------------------|
| Operating System      | Windows 7/8/10, Linux variants, MacOS|
| *Eclipse              | Version 4.5                          |
| *Java Virtual Machine | Version 8 or later                   |

*(supplied by installer)
    

General Notes
------
* A summary of the release highlights and new functionality is available on the 
[What's New](../WhatsNew/WhatsNew.html) page.  
  
* __IMPORTANT__: BridgePoint is completely open source software. 
Please see [xtUML.org](http://xtuml.org) and [onefact.net](http://onefact.net) 
for ongoing development and support of BridgePoint.  
  
* BridgePoint ships as a zip file, similar to standard eclipse packages. Users 
who wish to create desktop shortcuts to start BridgePoint should set the 
shortcut to start ```<install dir>/bridgepoint```.  

* BridgePoint supports two different action languages: OAL and MASL.  The default
is set via workspace preferences at xtUML > Action Language > Default Action Language Dialect.
In this release the default is set to OAL.       
  
Interoperability with Previous Releases
------
BridgePoint 6.6 is compatible with workspaces and projects created with prior 
versions back to BridgePoint 5.0.  However, after opening the model in this version
a modeler will not be able to open the model in a prior version.   

Changelog / Roadmap
------
For a complete list of changes for this and all completed BridgePoint Pro releases see the 
[BridgePoint roadmap](https://support.onefact.net/projects/bridgepoint/roadmap?utf8=%E2%9C%93&completed=1) in the issue tracking system.  

Closed Issues
------
 
| Issue |  Subject |
|-------|-----------------------------------------------------------------|
[633](https://support.onefact.net/issues/633) |   Editor: The "Check model integrity" utility does not capture orphaned referential attributes
[653](https://support.onefact.net/issues/653) |  Check model integrity tool doesn't find problem
[9408](https://support.onefact.net/issues/9408) |  Implement a CLI based MASL grammar parser test
[9419](https://support.onefact.net/issues/9419) |  Official MacOS support
[9445](https://support.onefact.net/issues/9445) |  Unable to merge a identifier from two super types in a subtype class
[9446](https://support.onefact.net/issues/9446) |  Unable to merge a identifier from two super types in a subtype class
[9554](https://support.onefact.net/issues/9554) |  Saab- 13 : complete integrity check
[9556](https://support.onefact.net/issues/9556) |  Saab- 11 : Import of an existing xtUML project fails to show the model until ME is closed and reopened
[9557](https://support.onefact.net/issues/9557) |  Saab- 9 : Mix of MASL and OAL terms in the same perspective
[9558](https://support.onefact.net/issues/9558) |  User Experience: Remove or filter menu items
[9561](https://support.onefact.net/issues/9561) |  Saab- 7 : Move relations
[9563](https://support.onefact.net/issues/9563) |  Saab- 8 : Unrelated and/or undocumented options in context menu
[9564](https://support.onefact.net/issues/9564) |  Saab- 4 : Feedback on formalized relations
[9566](https://support.onefact.net/issues/9566) |  Saab- 19 : Constant group item visibility
[9567](https://support.onefact.net/issues/9567) |  Saab- 6 : Cardinality on right click menu
[9568](https://support.onefact.net/issues/9568) |  Saab- 31 : Create folder gen/code_generation if it doesn't exist
[9632](https://support.onefact.net/issues/9632) |  Fix youtube video settings in learning course
[9647](https://support.onefact.net/issues/9647) |  Work in offline mode with maven builds
[9670](https://support.onefact.net/issues/9670) |  Bring version tweaks into master
[9684](https://support.onefact.net/issues/9684) |  Association editing enhancements
[9708](https://support.onefact.net/issues/9708) |  Interface Management Errors
[9717](https://support.onefact.net/issues/9717) |  Interface Management Errors
[9721](https://support.onefact.net/issues/9721) |  In the properties tab for class view, move prefix name and prefix type together
[9735](https://support.onefact.net/issues/9735) |  Using component external datatype as event data result in "Unassigned Parameter Placeholder"
[9737](https://support.onefact.net/issues/9737) |  Update relations in OOA of OOA
[9742](https://support.onefact.net/issues/9742) |  Display string UUIDs in referential integrity report
[9746](https://support.onefact.net/issues/9746) |  expand boxes around rel phrases and other boldened items
[9789](https://support.onefact.net/issues/9789) |  MC3020 fails with symbolic constant values

Known Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). Please check here for known issues and to 
submit bug reports and requests for new features. This issue tracker contains [all open bugs and feature requests](https://support.onefact.net/projects/bridgepoint/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=%3D&v%5Bstatus_id%5D%5B%5D=1&v%5Bstatus_id%5D%5B%5D=7&v%5Bstatus_id%5D%5B%5D=2&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&group_by=&t%5B%5D=). The 
following list contains specific items of note:  

| Issue |  Subject and Notes |
|-------|-----------------------------------------------------------------|
[9575](https://support.onefact.net/issues/9575) | Interface message parameter attributes not editable from properties view  
[9773](https://support.onefact.net/issues/9773) | Parse errors in DeterministicBehaviorTestModel.  An older test model now shows parse errors indicating unsynchronized references after load. There have been no similar field reports loading existing models. Users should watch out for similar behavior and file a report if one is found.   
  