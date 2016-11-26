BridgePoint xtUML Release Notes
========================

Release 5.8  

License
-------
BridgePoint is licensed under the [Apache 2.0 License Agreement](http://www.apache.org/licenses/LICENSE-2.0).  


System Requirements
-------
  
| Category              | Requirement                          |
|-----------------------|--------------------------------------|
| Operating System      | Windows 7/8/10, Linux variants       |
| *Eclipse              | Version 4.5                          |
| *Java Virtual Machine | Version 1.8 or later                 |

*(supplied by installer)
    

General Notes
------
* A summary of the release highlights and new functionality is available on the 
[What's New](../WhatsNew/WhatsNew.html) page.  
  
* __IMPORTANT__: BridgePoint 5.8 is a completely open source version of the software. 
Please see [xtUML.org](http://xtuml.org) and [onefact.net](http://onefact.net) 
for ongoing development and support of BridgePoint.  
  
* BridgePoint ships as a zip file, similar to standard eclipse packages. Users 
who wish to create desktop shortcuts to start BridgePoint should set the 
shortcut to start ```<install dir>/eclipse/Launcher.[sh|bat]```.  

* This version supports moving a model element via Cut & Paste.  The actual element
is moved and underlying UUIDs are not recalculated.  
  * Note that model elements that have connectors (associations, interfaces, etc) 
must be moved from the canvas because the graphical connectors do not appear in
Model Explorer.  The ```Cut``` menu item is disabled if the selection is incomplete.  
  * Move is supported for a specific list of model elements: Data type definition, 
Component definition, Interface definition, Class definition, Component reference, 
Imported class, Package (when package contains supported model elements)  

* BridgePoint 5.8 has enhanced support for modeling with the MASL action
language.  An Xtext-based editor provides syntax highlighting, validation
and context-sensitive edit assistance.  

Interoperability with Previous Releases
------
BridgePoint 5.8 is compatible with workspaces and projects created with prior 
versions back to BridgePoint 5.0.


Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). 
Please check here for known issues and to submit bug reports and requests for new features.


Closed Issues
------
The full [v5.8 – All Issues](https://support.onefact.net/issues?query_id=47) list 
is available in the One Fact Support system. The highlights are included here:  
  
| issue |  Subject |
|-------|----------|
 [59](https://support.onefact.net/issues/59) |  Problem with consecutive calls to Bridge when a parameter is sent by reference
 [60](https://support.onefact.net/issues/60) |  Minor cosmetic flaw for Constant Specification
 [510](https://support.onefact.net/issues/510) |  Problem with consecutive calls to Bridge when a parameter is sent by reference
 [511](https://support.onefact.net/issues/511) |  Minor cosmetic flaw for Constant Specification
 [514](https://support.onefact.net/issues/514) |  Interproject reference not updated correctly which leads into a not consistent model
 [591](https://support.onefact.net/issues/591) |  Have xtumlmc_build filter some Verifier runtime instances
 [7525](https://support.onefact.net/issues/7525) |  Stack Overflow Error in Verifier with breakpoints in code manipulating combined reflexive and non-reflexive associations.
 [7877](https://support.onefact.net/issues/7877) |  Interproject reference not updated correctly which leads into a not consistent model
 [7927](https://support.onefact.net/issues/7927) |  Remove models from bridgepoint repository
 [7940](https://support.onefact.net/issues/7940) |  MC-Java: various syntax errors encountered by pyrsl
 [8061](https://support.onefact.net/issues/8061) |  Allow imported classes via IPR (phase 1)
 [8072](https://support.onefact.net/issues/8072) |  Support package references in the MC.
 [8248](https://support.onefact.net/issues/8248) |  (semi)automate model conversion
 [8250](https://support.onefact.net/issues/8250) |  compare unedited model export with import
 [8251](https://support.onefact.net/issues/8251) |  compare edited export with import
 [8252](https://support.onefact.net/issues/8252) |  export existing model to MASL
 [8258](https://support.onefact.net/issues/8258) |  MASL syntax-highlighting editor
 [8259](https://support.onefact.net/issues/8259) |  MASL user defined identifier validation
 [8260](https://support.onefact.net/issues/8260) |  MASL xtUML context-sensitive edit/completion assistance
 [8321](https://support.onefact.net/issues/8321) |  Model Element Move
 [8330](https://support.onefact.net/issues/8330) |  GPS Watch acceptance test
 [8353](https://support.onefact.net/issues/8353) |  update prebuilders and syntax of GPS for MASL
 [8363](https://support.onefact.net/issues/8363) |  MASL editor not opened from the canvas
 [8387](https://support.onefact.net/issues/8387) |  Export source: domain package or component?
 [8393](https://support.onefact.net/issues/8393) |  Export from workspace only?
 [8400](https://support.onefact.net/issues/8400) |  Remove fontchecker
 [8401](https://support.onefact.net/issues/8401) |  Clean up MC-Java build support tooling
 [8410](https://support.onefact.net/issues/8410) |  Update model of MASL
 [8425](https://support.onefact.net/issues/8425) |  refine schema generation
 [8438](https://support.onefact.net/issues/8438) |  Remove compile target from generate.xml
 [8441](https://support.onefact.net/issues/8441) |  Integrate richer XText MASL editor
 [8442](https://support.onefact.net/issues/8442) |  Build BridgePoint with pyrsl
 [8449](https://support.onefact.net/issues/8449) |  State diagram arrowheads point wrong direction in some cases
 [8454](https://support.onefact.net/issues/8454) |  Milestone 3 - Proxy paths are inconsistent
 [8459](https://support.onefact.net/issues/8459) |  Fix graphics creation for creation transitions
 [8471](https://support.onefact.net/issues/8471) |  remove libTrans
 [8475](https://support.onefact.net/issues/8475) |  Update MASL test data
 [8483](https://support.onefact.net/issues/8483) |  event labels not rendered for creation transitions
 [8493](https://support.onefact.net/issues/8493) |  Create satisfactions for project wiring
 [8508](https://support.onefact.net/issues/8508) |  Move org.xtuml.bp.core build to standard pre-build style without split SQL
 [8509](https://support.onefact.net/issues/8509) |  Debug model load problems
 [8510](https://support.onefact.net/issues/8510) |  Deal with overloaded activity remaining issues
 [8515](https://support.onefact.net/issues/8515) |  string fields are not escaped by the instance dumper
 [8548](https://support.onefact.net/issues/8548) |  Parent task for allowing imported classes via IPR
 [8557](https://support.onefact.net/issues/8557) |  Inter-project reference (IPR) enhancements
 [8579](https://support.onefact.net/issues/8579) |  "Remove Signal" is never disabled
 [8586](https://support.onefact.net/issues/8586) |  Data Visibility Not Visibly Changing With Selection
 [8599](https://support.onefact.net/issues/8599) |  GPS Watch README instructions fail in Linux.
 [8619](https://support.onefact.net/issues/8619) |  Provisions and Requirements are not being created properly on component references
 [8621](https://support.onefact.net/issues/8621) |  Use EmptyCheck more.
 [8627](https://support.onefact.net/issues/8627) |  Bump the version to v5.5.0
 [8633](https://support.onefact.net/issues/8633) |  Package References (Imported Packages)
 [8637](https://support.onefact.net/issues/8637) |  Move pt_antlr from xtuml/bridgepoint to xtuml/pt_antlr
 [8638](https://support.onefact.net/issues/8638) |  Mismatch between GPS Watch Welcome zip file and GPS Watch in the models repository
 [8641](https://support.onefact.net/issues/8641) |  It is possible to change the name of a parameter in a message (operation/signal) in a port. This affects all ports using the interface. (was #7541)
 [8643](https://support.onefact.net/issues/8643) |  combined referentials
 [8658](https://support.onefact.net/issues/8658) |  IPR classes not found
 [8671](https://support.onefact.net/issues/8671) |  Unformalize is enabled when it should not.
 [8672](https://support.onefact.net/issues/8672) |  Throwable exception coming from Where_spec_end parser function
 [8679](https://support.onefact.net/issues/8679) |  Analyze Package References and Update Metamodel
 [8681](https://support.onefact.net/issues/8681) |  Pragma export cuts off single character pragma values
 [8682](https://support.onefact.net/issues/8682) |  Move the masldiff script so it is packaged more sensibly with the other MASL tools
 [8688](https://support.onefact.net/issues/8688) |  Audit Realized Bindings does not reload java classes
 [8689](https://support.onefact.net/issues/8689) |  Realized Strucutred data types can't handle primitive types
 [8690](https://support.onefact.net/issues/8690) |  Verifier doesn't use setters when marshalling members in structs out
 [8703](https://support.onefact.net/issues/8703) |  BPClassLoader doesn't close file stream after loading .class files
 [8708](https://support.onefact.net/issues/8708) |  Rework combining and splitting referential attributes
 [8714](https://support.onefact.net/issues/8714) |  Remove jars from bridgepoint and mc repos
 [8719](https://support.onefact.net/issues/8719) |  Class and Terminator keyletters and associated pragma
 [8738](https://support.onefact.net/issues/8738) |  Service signatures
 [8739](https://support.onefact.net/issues/8739) |  MASL is not displayed on state model canvas
 [8743](https://support.onefact.net/issues/8743) |  Dialog pops up on new project
 [8744](https://support.onefact.net/issues/8744) |  BridgePoint hangs on import
 [8753](https://support.onefact.net/issues/8753) |  Squash blanks from classes, role phrases, etc on MASL export
 [8755](https://support.onefact.net/issues/8755) |  Undo is not available for a move (cut/paste) operation
 [8756](https://support.onefact.net/issues/8756) |  Update MASL tooling to utilize updated persistence structure
 [8762](https://support.onefact.net/issues/8762) |  Enhance BP CLI to handle Java MC based projects
 [8767](https://support.onefact.net/issues/8767) |  Refresh available community build
 [8769](https://support.onefact.net/issues/8769) |  Within a component restrict visibility to externally defined model elements
 [8771](https://support.onefact.net/issues/8771) |  Problem characters prevent MASL export
 [8773](https://support.onefact.net/issues/8773) |  Datatypes not included in MASL exported data
 [8774](https://support.onefact.net/issues/8774) |  Problem with MASL export and referentials
 [8793](https://support.onefact.net/issues/8793) |  Saab- Model Element Move
 [8795](https://support.onefact.net/issues/8795) |  Satisfactions created in incorrect places
 [8796](https://support.onefact.net/issues/8796) |  turn on dynamic strings in masl
 [8798](https://support.onefact.net/issues/8798) |  The "cut" currently implemented (Model Element Move) allows a limited element selection
 [8807](https://support.onefact.net/issues/8807) |  Parser marks problems on MASL activities when exporting MASL
 [8812](https://support.onefact.net/issues/8812) |  CDT dialog during new project creation
 [8813](https://support.onefact.net/issues/8813) |  Assign Signal is visible for Transitions Instance State Machines
 [8820](https://support.onefact.net/issues/8820) |  Change the BridgePoint oal marker extension to oal_err
 [8822](https://support.onefact.net/issues/8822) |  It is possible to change the name of a parameter in a message (operation/signal) in a port. This affects all ports using the interface.
 [8840](https://support.onefact.net/issues/8840) |  Downgrade of component reference leaves an orphaned connector
 [8857](https://support.onefact.net/issues/8857) |  Filter the package selection list
 [8858](https://support.onefact.net/issues/8858) |  Document package references
 [8865](https://support.onefact.net/issues/8865) |  Support package / package reference dispose and downgrade
 [8883](https://support.onefact.net/issues/8883) |  Disallow operations on message parameters

