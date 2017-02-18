BridgePoint xtUML Release Notes
========================

Release 6.1.0  

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
  
* __IMPORTANT__: BridgePoint 6.0 is a completely open source version of the software. 
Please see [xtUML.org](http://xtuml.org) and [onefact.net](http://onefact.net) 
for ongoing development and support of BridgePoint.  
  
* BridgePoint ships as a zip file, similar to standard eclipse packages. Users 
who wish to create desktop shortcuts to start BridgePoint should set the 
shortcut to start ```<install dir>/bridgepoint```.  

* This version supports moving a model element via Cut & Paste.  The actual element
is moved and underlying UUIDs are not recalculated.  
  * Note that model elements that have connectors (associations, interfaces, etc) 
must be moved from the canvas because the graphical connectors do not appear in
Model Explorer.  The ```Cut``` menu item is disabled if the selection is incomplete.  
  * Move is supported for a specific list of model elements: Data type definition, 
Component definition, Interface definition, Class definition, Component reference, 
Imported class, Package (when package contains supported model elements)  

* BridgePoint 6.0 has enhanced support for modeling with the MASL action
language.  An Xtext-based editor provides syntax highlighting, validation
and context-sensitive edit assistance.  

* This version adds a integer attribute ```Dialect``` in the metamodel to action 
homes that used to populate the ```Action_Semantics``` field.   

* BridgePoint supports two different action languages: OAL and MASL.  The default
is set via workspace preferences at xtUML > Action Language > Default Action Language Dialect.
In this release the default is set to MASL.   
  
Interoperability with Previous Releases
------
BridgePoint 6.1.0 is compatible with workspaces and projects created with prior 
versions back to BridgePoint 5.0.  However, after opening the model in this version
a modeler will not be able to open the model in a prior version.   

Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). 
Please check here for known issues and to submit bug reports and requests for new features.


Closed Issues
------
The full [v6.0 – All Issues](https://support.onefact.net/issues?query_id=48) list 
is available in the One Fact Support system. The highlights are included here:  
  
| Issue |  Subject |
|-------|-----------------------------------------------------------------|
[661](https://support.onefact.net/issues/661)   |   Fix formatting of Welcome page on linux
[8053](https://support.onefact.net/issues/8053) |   The Welcome page needs some updates
[8255](https://support.onefact.net/issues/8255) |   MASL to/from Alf "distance"
[8372](https://support.onefact.net/issues/8372) |   BridgePoint importer orphans MASL activities
[8392](https://support.onefact.net/issues/8392) |   Java and C model compilers have mismatched enumeration types for ElementTypeConstants
[8416](https://support.onefact.net/issues/8416) |   Pragmas
[8417](https://support.onefact.net/issues/8417) |   Storing activities as dialect files
[8484](https://support.onefact.net/issues/8484) |   Update model import of MASL model file to automatically create satisfactions
[8495](https://support.onefact.net/issues/8495) |   Update to eclipse.org infrastructure style build
[8575](https://support.onefact.net/issues/8575) |   Sample project imports takes two tries.
[8616](https://support.onefact.net/issues/8616) |   integrity check too picky with event numbers
[8636](https://support.onefact.net/issues/8636) |   Papyrus-xtUML -- Submit Initial Contribution
[8691](https://support.onefact.net/issues/8691) |   Show MASL in state action boxes on the canvas
[8741](https://support.onefact.net/issues/8741) |   Type definitions are messy
[8763](https://support.onefact.net/issues/8763) |   type system for MASL editor
[8770](https://support.onefact.net/issues/8770) |   Clean up maslin duplication
[8780](https://support.onefact.net/issues/8780) |   Extend persistence to store activities with MASL signatures
[8797](https://support.onefact.net/issues/8797) |   Provide MASL data for editor
[8800](https://support.onefact.net/issues/8800) |   MASL formatter does not sort pragmas for MASL diff
[8805](https://support.onefact.net/issues/8805) |   Errors during "Export MASL Project"
[8817](https://support.onefact.net/issues/8817) |   Undo does not remove masl files
[8818](https://support.onefact.net/issues/8818) |   Integrate MASL editor with new persistence strategy
[8824](https://support.onefact.net/issues/8824) |   XText MASL editor should not rely on workspace's Build Automatically
[8826](https://support.onefact.net/issues/8826) |   Fix String::quote
[8836](https://support.onefact.net/issues/8836) |   Add Xtext nature to new xtUML Projects
[8844](https://support.onefact.net/issues/8844) |   Unhack package reference marking in MC.
[8887](https://support.onefact.net/issues/8887) |   Clean up new persistence mechanism for activities
[8892](https://support.onefact.net/issues/8892) |   Test By_Ref parameters of all types to Realized EEs.
[8898](https://support.onefact.net/issues/8898) |   MASL domain does not import correctly into BridgePoint
[8902](https://support.onefact.net/issues/8902) |   Downgrade dialog and help
[8911](https://support.onefact.net/issues/8911) |   Update splash screen to remove registration marks
[8912](https://support.onefact.net/issues/8912) |   m2x deletes empty C_EP
[8914](https://support.onefact.net/issues/8914) |   Delete deprecated OOA packages
[8918](https://support.onefact.net/issues/8918) |   Fix import parser in the io plugin
[8919](https://support.onefact.net/issues/8919) |   NumberFormatException when loading pre-dialect attribute models with proxies
[8920](https://support.onefact.net/issues/8920) |   Update Dialect attribute usage in the MC projects
[8923](https://support.onefact.net/issues/8923) |   Deprecate relocatables code
[8928](https://support.onefact.net/issues/8928) |   Consider import code for upgrading string dialect to int dialect
[8931](https://support.onefact.net/issues/8931) |   Do not parse when Dialect is not OAL
[8933](https://support.onefact.net/issues/8933) |   speed up stage 1
[8935](https://support.onefact.net/issues/8935) |   masl2xtuml core dumps when translating preferred identifiers that are merged referentials
[8936](https://support.onefact.net/issues/8936) |   Additional types package created with every import
[8937](https://support.onefact.net/issues/8937) |   Incorrect import/conversion of preferred referential identifier
[8938](https://support.onefact.net/issues/8938) |   Merged Referential Problems With Associative Classes
[8939](https://support.onefact.net/issues/8939) |   Failure To Import MASL Domain with no Relationship Definitions
[8941](https://support.onefact.net/issues/8941) |   Updating masl and editting parametrs can result in masl being deleted
[8942](https://support.onefact.net/issues/8942) |   Segregate tests out of xtuml/bridgepoint
[8944](https://support.onefact.net/issues/8944) |   Build BridgePoint with MC bits in toosl/mc folder
[8950](https://support.onefact.net/issues/8950) |   Update the VM
[8951](https://support.onefact.net/issues/8951) |   Marking editor
[8954](https://support.onefact.net/issues/8954) |   "Export MASL Domain" fails
[8957](https://support.onefact.net/issues/8957) |   Failure to convert/import default attribute value
[8959](https://support.onefact.net/issues/8959) |   Deal with ant binaries not being executable.
[8961](https://support.onefact.net/issues/8961) |   MASL parser mishandles FILE_NAME and LINE_NO terminals
[8962](https://support.onefact.net/issues/8962) |   Incorrect import of wrapper domains
[8963](https://support.onefact.net/issues/8963) |   Graphics reconciliation that starts with no graphics does not properly reconcile formalized required and provided interfaces
[8966](https://support.onefact.net/issues/8966) |   Clean up maven build issues
[8968](https://support.onefact.net/issues/8968) |   Upgrade models in mc repository with persistence changes
[8971](https://support.onefact.net/issues/8971) |   Error during graphics reconciliation on the SAC_PROC model
[8974](https://support.onefact.net/issues/8974) |   Error during MASL project export
[8978](https://support.onefact.net/issues/8978) |   Graphics reconciliation is not connecting the ball and cup in a satisfaction
[8979](https://support.onefact.net/issues/8979) |   Automatically run graphics reconciliation during MASL model import
[8980](https://support.onefact.net/issues/8980) |   m2x support for updated marking format
[8981](https://support.onefact.net/issues/8981) |   x2m support for updated marking
[8983](https://support.onefact.net/issues/8983) |   Document new marking editor
[8986](https://support.onefact.net/issues/8986) |   Annotate MASL models to communicate to Import
[8993](https://support.onefact.net/issues/8993) |   Introduce quick start for a new MASL domain or project
[8996](https://support.onefact.net/issues/8996) |   Project import drops shared typeref
[8998](https://support.onefact.net/issues/8998) |   MASL command line export
[8999](https://support.onefact.net/issues/8999) |   Error while attempting to open a MASL state action
[9002](https://support.onefact.net/issues/9002) |   CLI Import of single file xtUML model
[9007](https://support.onefact.net/issues/9007) |   Marking Editor Help Text
[9008](https://support.onefact.net/issues/9008) |   Force a load and persist of the ooaofgraphics model
[9010](https://support.onefact.net/issues/9010) |   Marking Editor Warning Messages
[9018](https://support.onefact.net/issues/9018) |   The BridgePoint Build ID reads "Internal Build"
[9020](https://support.onefact.net/issues/9020) |   In BridgePoint models folders update .gitignore for oal_err
[9028](https://support.onefact.net/issues/9028) |   Define what version of the xtext editor to use in the 6.0 release
[9043](https://support.onefact.net/issues/9043) |   Body of terminator service pulled over from project
[9046](https://support.onefact.net/issues/9046) |   Suppress using keyletters in x2m
[9052](https://support.onefact.net/issues/9052) |   Activity import parser loads qualified type names wrong
[9053](https://support.onefact.net/issues/9053) |   Marking Mappings   
   