BridgePoint xtUML Release Notes
========================

Release 6.10.0

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
BridgePoint 6.10 is compatible with workspaces and projects created with prior 
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
[8306](https://support.onefact.net/issues/8306) |  Combining referential attributes causes orphaned attributes
[9283](https://support.onefact.net/issues/9283) |  Abstract and Concrete Polymorphic Events
[9699](https://support.onefact.net/issues/9699) |  Required Operation MASL Editing Not Possible
[9700](https://support.onefact.net/issues/9700) |  Inconsistencies with file contents and tool
[9709](https://support.onefact.net/issues/9709) |  Subtype appears in mod file before supertype when MASL is exported
[9710](https://support.onefact.net/issues/9710) |  When polymorphic events are changed in a supertype, reflect the change in the subtype(s)
[9836](https://support.onefact.net/issues/9836) |  Cannot merge referential with identifier when formalising super type
[9877](https://support.onefact.net/issues/9877) |  mcmc on mac requires flex
[9913](https://support.onefact.net/issues/9913) |  extra attribute after paste of subtype
[9932](https://support.onefact.net/issues/9932) |  Dumping MASL project only produces prj file
[9935](https://support.onefact.net/issues/9935) |  OAL content assist raises selection list at end of comment
[9937](https://support.onefact.net/issues/9937) |  Archive 6.8.0 build server
[9939](https://support.onefact.net/issues/9939) |  Pasting a class in a package does not update class number
[9940](https://support.onefact.net/issues/9940) |  Create department branch for intermediate promotions
[9942](https://support.onefact.net/issues/9942) |  git-refresh fails on build server for branches on xtuml fork
[9948](https://support.onefact.net/issues/9948) |  Bi-directional synchronization of MASL data can lead to problems
[9951](https://support.onefact.net/issues/9951) |  Scale back to a much smaller submission.
[9989](https://support.onefact.net/issues/9989) |  type export needs to be ordered
[9990](https://support.onefact.net/issues/9990) |  Utility to create interface messages from domain functions and class operations
[9991](https://support.onefact.net/issues/9991) |  Update AWS launch script for AMIs created Nov 2017
[9993](https://support.onefact.net/issues/9993) |  Remove AMIs no longer used
[9994](https://support.onefact.net/issues/9994) |  typo in TE_C_sort routine of C MC
[10009](https://support.onefact.net/issues/10009) |  Incorrect population of parameter list for class-based operation parameters, if user-defined dt from another package is used
[10013](https://support.onefact.net/issues/10013) |  support multiple format characters in the template engine
[10014](https://support.onefact.net/issues/10014) |  Publish to interface duplicates should append "_X" not "-X"
[10022](https://support.onefact.net/issues/10022) |  Content assist for relate statement
[10026](https://support.onefact.net/issues/10026) |  Exit jenkins BridgePoint build if mc executables are not created
[10027](https://support.onefact.net/issues/10027) |  Test test_G_ALL_nested_invoke in IOMdlTestGenerics fails
[10028](https://support.onefact.net/issues/10028) |  v6.10 Release Prep
[10030](https://support.onefact.net/issues/10030) |  Address failures in Junit Tests
[10031](https://support.onefact.net/issues/10031) |  Add more detail to help for "Publish to Interface..."

Known Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). Please check here for known issues and to 
submit bug reports and requests for new features. This issue tracker contains [all open bugs and feature requests](https://support.onefact.net/projects/bridgepoint/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=%3D&v%5Bstatus_id%5D%5B%5D=1&v%5Bstatus_id%5D%5B%5D=7&v%5Bstatus_id%5D%5B%5D=2&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&group_by=&t%5B%5D=). The 
following list contains specific items of note:  

| Issue |  Subject and Notes |
|-------|-----------------------------------------------------------------|
[9773](https://support.onefact.net/issues/9773) | Parse errors in DeterministicBehaviorTestModel.  An older test model now shows parse errors indicating unsynchronized references after load. There have been no similar field reports loading existing models. Users should watch out for similar behavior and file a report if one is found.   
[9924](https://support.onefact.net/issues/9924) | Port Reference Names Missing due to incomplete upgrade of CL_POR instances (workaround exists)   
  
