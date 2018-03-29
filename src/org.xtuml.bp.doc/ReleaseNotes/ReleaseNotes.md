BridgePoint xtUML Release Notes
========================

Release 6.12.0

License
-------
BridgePoint is licensed under the [Apache 2.0 License Agreement](http://www.apache.org/licenses/LICENSE-2.0).  


System Requirements
-------
  
| Category              | Requirement                          |
|-----------------------|--------------------------------------|
| Operating System      | Linux, MacOS, Windows                |
| *Eclipse              | Version 4.5                          |
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
BridgePoint 6.12 is compatible with workspaces and projects created with prior versions back to 
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
[8287](https://support.onefact.net/issues/8287) | Add support for instance reference set addition  
[9021](https://support.onefact.net/issues/9021) | Improve mechanism for inter-domain references - Ensure .int (interface) files are provided to MASL editor  
[9322](https://support.onefact.net/issues/9322) | Document types  
[10042](https://support.onefact.net/issues/10042) | Change default for "Allow operations in where clause" preference  
[10044](https://support.onefact.net/issues/10044) | Polymorphic creation events cause round trip to crash  
[10046](https://support.onefact.net/issues/10046) | Update the developer virtual machine  
[10052](https://support.onefact.net/issues/10052) | Create coverage tool  
[10053](https://support.onefact.net/issues/10053) | PEI Model to MASL  
[10107](https://support.onefact.net/issues/10107) | Issues in calculator test model  
[10108](https://support.onefact.net/issues/10108) | Update MASL Conversion Guide  
[10120](https://support.onefact.net/issues/10120) | Valid syntax causes problems with parser and export (MASL)  
[10123](https://support.onefact.net/issues/10123) | Update Preferences help documentation  
[10128](https://support.onefact.net/issues/10128) | Documentation issue for tables that want to display &#124; operator  
[10132](https://support.onefact.net/issues/10132) | Malformed for loop causes tool hang  
[10136](https://support.onefact.net/issues/10136) | Create calculator MASL reference model  
[10139](https://support.onefact.net/issues/10139) | Published interface operations have wrong dialect in MASL mode  
[10150](https://support.onefact.net/issues/10150) | MC-3020 fails to translate referential attributes of type timestamp and date  
[10155](https://support.onefact.net/issues/10155) | Multiple projects depending on same INT file not working  
[10156](https://support.onefact.net/issues/10156) | Support export of public EDTs for MASL  
[10163](https://support.onefact.net/issues/10163) | v6.12 Release Prep  

Known Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). Please check here for known issues and to 
submit bug reports and requests for new features. This issue tracker contains [all open bugs and feature requests](https://support.onefact.net/projects/bridgepoint/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=%3D&v%5Bstatus_id%5D%5B%5D=1&v%5Bstatus_id%5D%5B%5D=7&v%5Bstatus_id%5D%5B%5D=2&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&group_by=&t%5B%5D=).  

  