BridgePoint xtUML Release Notes
========================

Release 6.2.1  

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
  
* __IMPORTANT__: BridgePoint is completely open source software. 
Please see [xtUML.org](http://xtuml.org) and [onefact.net](http://onefact.net) 
for ongoing development and support of BridgePoint.  
  
* BridgePoint ships as a zip file, similar to standard eclipse packages. Users 
who wish to create desktop shortcuts to start BridgePoint should set the 
shortcut to start ```<install dir>/bridgepoint```.  

* BridgePoint 6.2 has enhanced support for modeling with the MASL action
language.  An Xtext-based editor provides syntax highlighting, validation
and context-sensitive edit assistance.  

* BridgePoint supports two different action languages: OAL and MASL.  The default
is set via workspace preferences at xtUML > Action Language > Default Action Language Dialect.
In this release the default is set to MASL.   
  
Interoperability with Previous Releases
------
BridgePoint 6.2.1 is compatible with workspaces and projects created with prior 
versions back to BridgePoint 5.0.  However, after opening the model in this version
a modeler will not be able to open the model in a prior version.   

Issues and Feature Requests
------
The BridgePoint support system is hosted at [http://support.onefact.net](http://support.onefact.net). 
Please check here for known issues and to submit bug reports and requests for new features.


Closed Issues
------
  
| Issue |  Subject |
|-------|-----------------------------------------------------------------|
[8208](https://support.onefact.net/issues/8208) | Parent task for Deliverable Documentation (phase 2)
[8209](https://support.onefact.net/issues/8209) | Parent task for Model Migration
[8213](https://support.onefact.net/issues/8213) | Parent task for Export (phase 2)
[8218](https://support.onefact.net/issues/8218) | Parent task for Testing and Validation (phase 2)
[8219](https://support.onefact.net/issues/8219) | Parent task for Editor
[8253](https://support.onefact.net/issues/8253) | Monitor and Manage Project (phase 2)
[8254](https://support.onefact.net/issues/8254) | Document OAL to/from MASL Activity Mapping
[8257](https://support.onefact.net/issues/8257) | OAL to MASL
[8261](https://support.onefact.net/issues/8261) | MASL automatic reference maintenance
[8375](https://support.onefact.net/issues/8375) | Parent task for Import and Convert
[8411](https://support.onefact.net/issues/8411) | Type and Typeref
[8680](https://support.onefact.net/issues/8680) | Documentation comments not exported when the description is not terminated with a newline
[8775](https://support.onefact.net/issues/8775) | Error message output running masl2xtuml
[8785](https://support.onefact.net/issues/8785) | Project Raven- M9 Automatic reference maintenance
[8788](https://support.onefact.net/issues/8788) | Project Raven- M12 OAL-to-MASL conversion
[8808](https://support.onefact.net/issues/8808) | deprecate 'function' keyword in MASL
[8842](https://support.onefact.net/issues/8842) | Missing begin/end for in newly created operations via Model Explorer
[8988](https://support.onefact.net/issues/8988) | Add validation functionality to type editing for MASL
[9001](https://support.onefact.net/issues/9001) | Validating cell editor for Data Type Definition
[9024](https://support.onefact.net/issues/9024) | Missing 'begin' for empty-body service definitions
[9030](https://support.onefact.net/issues/9030) | NPE in properties of a state transition
[9031](https://support.onefact.net/issues/9031) | Dialect issue on state transition
[9032](https://support.onefact.net/issues/9032) | Enhance the MASL refresher / scooper
[9038](https://support.onefact.net/issues/9038) | Clear up problems with SAC test model
[9044](https://support.onefact.net/issues/9044) | Core dump in x2m
[9066](https://support.onefact.net/issues/9066) | Automate MASL round trip testing
[9067](https://support.onefact.net/issues/9067) | MASL export segmentation faults
[9071](https://support.onefact.net/issues/9071) | BridgePoint v6.0.0 does not unpack correctly
[9081](https://support.onefact.net/issues/9081) | Exception when perfoming a masl export using command line tool
[9090](https://support.onefact.net/issues/9090) | Subtype supertype ID issues in MASL flow
[9106](https://support.onefact.net/issues/9106) | Crash in Export MASL Domain
[9107](https://support.onefact.net/issues/9107) | Referentials from multiple relationships not output properly
[9108](https://support.onefact.net/issues/9108) | Collate MASL regression test models
[9109](https://support.onefact.net/issues/9109) | reflexive gets random formalizer
[9112](https://support.onefact.net/issues/9112) | BridgePoint build is slow
[9115](https://support.onefact.net/issues/9115) | Introduce PEI association model
[9117](https://support.onefact.net/issues/9117) | ipv6 propagating but not fully combining
[9118](https://support.onefact.net/issues/9118) | x2m no longer outputs activities
[9121](https://support.onefact.net/issues/9121) | Open masl action bodies in a single-buffer editor
[9128](https://support.onefact.net/issues/9128) | masl exporter segfault on doc comments
[9129](https://support.onefact.net/issues/9129) | Corruption in output from masl process during round trip testing
[9131](https://support.onefact.net/issues/9131) | (Spurious?) Error messages from masl export tool
[9133](https://support.onefact.net/issues/9133) | Round trip cannot copy difference report
[9134](https://support.onefact.net/issues/9134) | terminator services generated as private
[9135](https://support.onefact.net/issues/9135) | transition table generated for supertype with no state model
[9136](https://support.onefact.net/issues/9136) | domain services tagged as scenario or external are generated twice
[9145](https://support.onefact.net/issues/9145) | Whitespace differences in MASL actions on round-trip tests
[9160](https://support.onefact.net/issues/9160) | Type name used as element name causes forward declaration of type
[9163](https://support.onefact.net/issues/9163) | masl_round_trip generates two copies of each service file
[9165](https://support.onefact.net/issues/9165) | Doc comments on MASL referential attributes not exported
[9170](https://support.onefact.net/issues/9170) | Very long MASL action file fails to import
[9171](https://support.onefact.net/issues/9171) | Update SAC example model
[9176](https://support.onefact.net/issues/9176) | MASL deleted when saving file
[9177](https://support.onefact.net/issues/9177) | Invalid syntax checking report
[9181](https://support.onefact.net/issues/9181) | role phrase attached to wrong attribute on associative reflexive relationship
[9196](https://support.onefact.net/issues/9196) | Build issue with xtext plugins
[9197](https://support.onefact.net/issues/9197) | Terminator service parameter rename failure
[9201](https://support.onefact.net/issues/9201) | Default attribute values with enum type are not present in exported MASL
   
   