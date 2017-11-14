BridgePoint xtUML Release Notes
========================

Release 6.8.0

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
BridgePoint 6.8 is compatible with workspaces and projects created with prior 
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
[8652](https://support.onefact.net/issues/8652) |Reconcile outstanding changes for promotion  
[8897](https://support.onefact.net/issues/8897) |Graphical representation of the a domain class diagram and state transition from an import MASL domain do not render correctly.  
[8934](https://support.onefact.net/issues/8934) |Graphical Issues With Editor  
[9069](https://support.onefact.net/issues/9069) |Graphical representation of the a domain class diagram and state transition from an import MASL domain do not render correctly.  
[9570](https://support.onefact.net/issues/9570) |Improved BridgePoint delivery mechanism  
[9571](https://support.onefact.net/issues/9571) |Enhanced OAL Editor (phase 1)  
[9575](https://support.onefact.net/issues/9575) |Interface message parameter attributes not editable from properties view  
[9609](https://support.onefact.net/issues/9609) |Error Importing Domain using CLI  
[9617](https://support.onefact.net/issues/9617) |Error Importing Domain using CLI  
[9674](https://support.onefact.net/issues/9674) |Document mcmc build for mac  
[9675](https://support.onefact.net/issues/9675) |Update plugin info on xtext plugins  
[9718](https://support.onefact.net/issues/9718) |Testing for Enhanced OAL Editor phase 1
[9719](https://support.onefact.net/issues/9719) |Automate build of integrity.exe.  
[9720](https://support.onefact.net/issues/9720) |Include Maven2Eclipse (m2e) plug-in in developer product  
[9745](https://support.onefact.net/issues/9745) |Stop building some Windows edition  
[9749](https://support.onefact.net/issues/9749) |Determine use cases for OAL autocompletion  
[9750](https://support.onefact.net/issues/9750) |Develop test matrix for oal autocompletion  
[9751](https://support.onefact.net/issues/9751) |Using test matrix implement tests for oal context sensitive help  
[9757](https://support.onefact.net/issues/9757) |Problem with parameter name  
[9758](https://support.onefact.net/issues/9758) |Problem with parameter name  
[9761](https://support.onefact.net/issues/9761) |AE8-When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance.  
[9762](https://support.onefact.net/issues/9762) |AE9-When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.  
[9763](https://support.onefact.net/issues/9763) |AE3-Context-sensitive completion assistance for user-defined identifiers is provided while editing OAL activities.  
[9764](https://support.onefact.net/issues/9764) |unable to build integrity checker  
[9801](https://support.onefact.net/issues/9801) |Declarations created for non-existing classes when class-based state machine  
[9811](https://support.onefact.net/issues/9811) |Install xsltproc on build server images  
[9814](https://support.onefact.net/issues/9814) |Move location of Cardinality CME  
[9830](https://support.onefact.net/issues/9830) |get rid of extraneous characters  
[9831](https://support.onefact.net/issues/9831) |Cardinality modification menu shall show current selection  
[9832](https://support.onefact.net/issues/9832) |Move relation unformalizes and does not roll back  
[9835](https://support.onefact.net/issues/9835) |Update Windows Integrity Checker  
[9846](https://support.onefact.net/issues/9846) |Update build server image  
[9856](https://support.onefact.net/issues/9856) |sort integrity test results  
[9860](https://support.onefact.net/issues/9860) |vitalsigns cleanup  
[9909](https://support.onefact.net/issues/9909) |Enable "Parse while editing OAL activities"  
[9910](https://support.onefact.net/issues/9910) |Content assist after "generate" does not add a space  
[9912](https://support.onefact.net/issues/9912) |Content assist hangs in oal_open_declarations model  
[9914](https://support.onefact.net/issues/9914) |string_return_test invalid builders

Known Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). Please check here for known issues and to 
submit bug reports and requests for new features. This issue tracker contains [all open bugs and feature requests](https://support.onefact.net/projects/bridgepoint/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=%3D&v%5Bstatus_id%5D%5B%5D=1&v%5Bstatus_id%5D%5B%5D=7&v%5Bstatus_id%5D%5B%5D=2&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&group_by=&t%5B%5D=). The 
following list contains specific items of note:  

| Issue |  Subject and Notes |
|-------|-----------------------------------------------------------------|
[9773](https://support.onefact.net/issues/9773) | Parse errors in DeterministicBehaviorTestModel.  An older test model now shows parse errors indicating unsynchronized references after load. There have been no similar field reports loading existing models. Users should watch out for similar behavior and file a report if one is found.   
[9913](https://support.onefact.net/issues/9913) | Extra attribute after paste of subtype (workaround exists)   
[9924](https://support.onefact.net/issues/9924) | Port Reference Names Missing due to incomplete upgrade of CL_POR instances (workaround exists)   
  
