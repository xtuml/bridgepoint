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
  
| DEI/SR |  Project |    Subject |
|--------|----------|------------|
    60  |  BridgePoint | Minor cosmetic flaw for Constant Specification
    510  |  Service Pro |  Problem with consecutive calls to Bridge when a parameter is sent by reference
    59  |  BridgePoint | Problem with consecutive calls to Bridge when a parameter is sent by reference
    511  |  Service Pro |  Minor cosmetic flaw for Constant Specification
    514  |  Service Pro |  Interproject reference not updated correctly which leads into a not consistent model
    7877  |  BridgePoint | Interproject reference not updated correctly which leads into a not consistent model
    591  |  BridgePoint | Have xtumlmc_build filter some Verifier runtime instances
    7525  |  Service Pro |  Stack Overflow Error in Verifier with breakpoints in code manipulating combined reflexive and non-reflexive associations.
    7927  |  BridgePoint | Remove models from bridgepoint repository
    8637  |  BridgePoint | Move pt_antlr from xtuml/bridgepoint to xtuml/pt_antlr
    8714  |  BridgePoint | Remove jars from bridgepoint and mc repos
    7940  |  BridgePoint | MC-Java: various syntax errors encountered by pyrsl
    8442  |  BridgePoint | Build BridgePoint with pyrsl
    8682  |  BridgePoint | Move the masldiff script so it is packaged more sensibly with the other MASL tools
    8387  |  BridgePoint | Export source: domain package or component?
    8393  |  BridgePoint | Export from workspace only?
    8681  |  BridgePoint | Pragma export cuts off single character pragma values
    8248  |  BridgePoint | (semi)automate model conversion
    8250  |  BridgePoint | compare unedited model export with import
    8251  |  BridgePoint | compare edited export with import
    8252  |  BridgePoint | export existing model to MASL
    8330  |  BridgePoint | GPS Watch acceptance test
    8353  |  BridgePoint | update prebuilders and syntax of GPS for MASL
    8475  |  BridgePoint | Update MASL test data
    8510  |  BridgePoint | Deal with overloaded activity remaining issues
    8258  |  BridgePoint | MASL syntax-highlighting editor
    8259  |  BridgePoint | MASL user defined identifier validation
    8260  |  BridgePoint | MASL xtUML context-sensitive edit/completion assistance
    8363  |  BridgePoint | MASL editor not opened from the canvas
    8441  |  BridgePoint | Integrate richer XText MASL editor
    8493  |  BridgePoint | Create satisfactions for project wiring
    8515  |  BridgePoint | string fields are not escaped by the instance dumper
    8719  |  BridgePoint | Class and Terminator keyletters and associated pragma
    8410  |  BridgePoint | Update model of MASL
    8449  |  BridgePoint | State diagram arrowheads point wrong direction in some cases
    8459  |  BridgePoint | Fix graphics creation for creation transitions
    8483  |  BridgePoint | event labels not rendered for creation transitions
    8400  |  BridgePoint | Remove fontchecker
    8401  |  BridgePoint | Clean up MC-Java build support tooling
    8438  |  BridgePoint | Remove compile target from generate.xml
    8471  |  BridgePoint | remove libTrans
    8425  |  BridgePoint | refine schema generation
    8508  |  BridgePoint | Move org.xtuml.bp.core build to standard pre-build style without split SQL
    8557  |  Service Pro |  Inter-project reference (IPR) enhancements
    8548  |  BridgePoint | Parent task for allowing imported classes via IPR
    8061  |  BridgePoint | Allow imported classes via IPR (phase 1)
    8633  |  BridgePoint | Package References (Imported Packages)
    8072  |  BridgePoint | Support package references in the MC.
    8679  |  BridgePoint | Analyze Package References and Update Metamodel
    8769  |  BridgePoint | Within a component restrict visibility to externally defined model elements
    8857  |  BridgePoint | Filter the package selection list
    8858  |  BridgePoint | Document package references
    8865  |  BridgePoint | Support package / package reference dispose and downgrade
    8586  |  BridgePoint | Data Visibility Not Visibly Changing With Selection
    8599  |  BridgePoint | GPS Watch README instructions fail in Linux.
    8579  |  BridgePoint | "Remove Signal" is never disabled
    8619  |  BridgePoint | Provisions and Requirements are not being created properly on component references
    8621  |  BridgePoint | Use EmptyCheck more.
    8627  |  BridgePoint | Bump the version to v5.5.0
    8638  |  BridgePoint | Mismatch between GPS Watch Welcome zip file and GPS Watch in the models repository
    8641  |  Service Pro |  It is possible to change the name of a parameter in a message (operation/signal) in a port. This affects all ports using the interface. (was #7541)
    8822  |  BridgePoint | It is possible to change the name of a parameter in a message (operation/signal) in a port. This affects all ports using the interface.
    8883  |  BridgePoint | Disallow operations on message parameters
    8643  |  Service Pro | combined referentials
    8708  |  BridgePoint | Rework combining and splitting referential attributes
    8658  |  BridgePoint | IPR classes not found
    8793  |  Service Pro |  Saab- Model Element Move
    8321  |  BridgePoint | Model Element Move
    8454  |  BridgePoint | Milestone 3 - Proxy paths are inconsistent
    8509  |  BridgePoint | Debug model load problems
    8755  |  BridgePoint | Undo is not available for a move (cut/paste) operation
    8798  |  BridgePoint | The "cut" currently implemented (Model Element Move) allows a limited element selection
    8840  |  BridgePoint | Downgrade of component reference leaves an orphaned connector
    8671  |  BridgePoint | Unformalize is enabled when it should not.
    8672  |  BridgePoint | Throwable exception coming from Where_spec_end parser function
    8688  |  BridgePoint | Audit Realized Bindings does not reload java classes
    8689  |  BridgePoint | Realized Strucutred data types can't handle primitive types
    8690  |  BridgePoint | Verifier doesn't use setters when marshalling members in structs out
    8703  |  BridgePoint | BPClassLoader doesn't close file stream after loading .class files
    8738  |  BridgePoint | Service signatures
    8739  |  BridgePoint | MASL is not displayed on state model canvas
    8743  |  Service Pro | Dialog pops up on new project
    8744  |  Service Pro | BridgePoint hangs on import
    8753  |  BridgePoint | Squash blanks from classes, role phrases, etc on MASL export
    8756  |  BridgePoint | Update MASL tooling to utilize updated persistence structure
    8762  |  BridgePoint | Enhance BP CLI to handle Java MC based projects
    8767  |  BridgePoint | Refresh available community build
    8771  |  BridgePoint | Problem characters prevent MASL export
    8773  |  BridgePoint | Datatypes not included in MASL exported data
    8774  |  BridgePoint | Problem with MASL export and referentials
    8820  |  BridgePoint | Change the BridgePoint oal marker extension to oal_err
    8795  |  BridgePoint | Satisfactions created in incorrect places
    8796  |  BridgePoint | turn on dynamic strings in masl
    8807  |  BridgePoint | Parser marks problems on MASL activities when exporting MASL
    8812  |  BridgePoint | CDT dialog during new project creation
    8813  |  BridgePoint | Assign Signal is visible for Transitions Instance State Machines
    
