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
BridgePoint 6.2 is compatible with workspaces and projects created with prior 
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
[8256](https://support.onefact.net/issues/8256) | MASL Project Conversion Guide (System level Modeling flow)
[8257](https://support.onefact.net/issues/8257) | OAL to MASL
[8507](https://support.onefact.net/issues/8507) | Handle deferred activities
[8628](https://support.onefact.net/issues/8628) | Update MASL Conversion Guide
[8693](https://support.onefact.net/issues/8693) | Empty MASL files are exported
[8706](https://support.onefact.net/issues/8706) | Empty MASL files exported
[8740](https://support.onefact.net/issues/8740) | unformatted MASL is better than no MASL
[8776](https://support.onefact.net/issues/8776) | Detect and report empty role phrases.
[8777](https://support.onefact.net/issues/8777) | Raven Production
[8987](https://support.onefact.net/issues/8987) | Renamining a class leaves inconsistent MASL files
[8995](https://support.onefact.net/issues/8995) | Update all documentation to reflect new location of MASL utilities
[9013](https://support.onefact.net/issues/9013) | Using CLI single-file import on MASL fails to autoreconcile graphics
[9025](https://support.onefact.net/issues/9025) | MASL editor error marked in .tr files
[9036](https://support.onefact.net/issues/9036) | Diffs appear in MASL due to untrimmed action bodies (masldiff)
[9039](https://support.onefact.net/issues/9039) | Signatures for MASL project terminator services are generated wrong
[9041](https://support.onefact.net/issues/9041) | Implement mechanism to prevent non-supported elements from persisting MASL
[9047](https://support.onefact.net/issues/9047) | Have the multi-buffer masl editor index to the selected action body when opened by the user
[9072](https://support.onefact.net/issues/9072) | Adding parameter to a MASL service gives an error.
[9089](https://support.onefact.net/issues/9089) | Remove instance<Object> generic type from default MASL types
[9092](https://support.onefact.net/issues/9092) | MASL style identifiers impedes creating typeref types
[9101](https://support.onefact.net/issues/9101) | New transitions result in incorrect MASL being persisted
[9110](https://support.onefact.net/issues/9110) | Research best tool for profiling BridgePoint and define profiling procedure
[9111](https://support.onefact.net/issues/9111) | Profile BridgePoint using procedural techniques described by the engineering process
[9132](https://support.onefact.net/issues/9132) | Analyze data found during profiling in 9111
[9137](https://support.onefact.net/issues/9137) | Address performance issues found profiling 5.2.2 vs 6.0.0
[9140](https://support.onefact.net/issues/9140) | Corruption in output from masl process during round trip testing
[9148](https://support.onefact.net/issues/9148) | New transitions result in incorrect MASL being persisted
[9149](https://support.onefact.net/issues/9149) | Attribute missing from identifier
[9151](https://support.onefact.net/issues/9151) | Type name used as element name causes forward declaration of type
[9152](https://support.onefact.net/issues/9152) | MASL style identifiers impedes creating typeref types
[9155](https://support.onefact.net/issues/9155) | Implement mechanism to prevent non-supported elements from persisting MASL
[9156](https://support.onefact.net/issues/9156) | Signatures for MASL project terminator services are generated wrong
[9157](https://support.onefact.net/issues/9157) | Handle deferred activities
[9159](https://support.onefact.net/issues/9159) | Attribute missing from identifier
[9168](https://support.onefact.net/issues/9168) | referential attributes/classes/terminators not output in consistent order for MASL round trip tests
[9169](https://support.onefact.net/issues/9169) | referential attributes not output in consistent order for MASL round trip tests
[9173](https://support.onefact.net/issues/9173) | Creating a class in a top level package causes an error
[9174](https://support.onefact.net/issues/9174) | Refactoring is not reflected in an open masl editor
[9178](https://support.onefact.net/issues/9178) | MASL Polymorphic events and transition tables
[9179](https://support.onefact.net/issues/9179) | Tool Hangs
[9180](https://support.onefact.net/issues/9180) | Invalid snytax error on relationship navigate
[9182](https://support.onefact.net/issues/9182) | Using "Go To" from the problem Tab opens underlying file
[9187](https://support.onefact.net/issues/9187) | MASL polymorphic events and transition tables
[9188](https://support.onefact.net/issues/9188) | Tool Hangs
[9189](https://support.onefact.net/issues/9189) | duplicate referential attribute specs
[9191](https://support.onefact.net/issues/9191) | Using "Go To" from the problem Tab opens underlying file (masl problem markers issue)
[9192](https://support.onefact.net/issues/9192) | duplicate referential attribute specs
[9193](https://support.onefact.net/issues/9193) | Unwanted warning in MASL editor
[9194](https://support.onefact.net/issues/9194) | Raven Scorecard
[9202](https://support.onefact.net/issues/9202) | Terminator service with no implementation file creates an implementation file on export.
[9204](https://support.onefact.net/issues/9204) | Terminator service with no implementation file creates an implementation file on export.
[9206](https://support.onefact.net/issues/9206) | Referential attribute going missing
[9207](https://support.onefact.net/issues/9207) | Referential attribute going missing
[9208](https://support.onefact.net/issues/9208) | reflexive graphic not connected
[9210](https://support.onefact.net/issues/9210) | Relationship phrases not refactored
[9211](https://support.onefact.net/issues/9211) | Support MASL Project projects in masl_round_trip
[9212](https://support.onefact.net/issues/9212) | Package OAL to MASL converter
[9218](https://support.onefact.net/issues/9218) | MASL event parameter data is not properly refactored by an edit operation
[9220](https://support.onefact.net/issues/9220) | Type refactoring not working properly
[9221](https://support.onefact.net/issues/9221) | Error when creating a user defined type
[9222](https://support.onefact.net/issues/9222) | When adding attributes to a empty class with an association the addition of attributes does not make them available in the model explorer.
[9223](https://support.onefact.net/issues/9223) | When adding a return type to a class operation an error dialog is displayed.
[9224](https://support.onefact.net/issues/9224) | When adding attributes to a empty class with an association the addition of attributes does not make them available in the model explorer.
[9225](https://support.onefact.net/issues/9225) | When adding a return type to a class operation an error dialog is displayed.
[9226](https://support.onefact.net/issues/9226) | MASL Domain and MASL Project Templates
[9227](https://support.onefact.net/issues/9227) | Unable to add an attribute to a subtype class.
[9228](https://support.onefact.net/issues/9228) | Unable to add an attribute to a subtype class.
[9229](https://support.onefact.net/issues/9229) | Error when creating a user defined type
[9230](https://support.onefact.net/issues/9230) | MASL round trip issue in SAC_OOA domain
[9231](https://support.onefact.net/issues/9231) | BridgePoint generates terminator signatures private
[9234](https://support.onefact.net/issues/9234) | Logged error editing MASL UDT
[9238](https://support.onefact.net/issues/9238) | MASL Template Updates
[9239](https://support.onefact.net/issues/9239) | Default attribute values with expression values are not present in exported MASL
[9240](https://support.onefact.net/issues/9240) | Referential added to wrong attribute on reflexive relationship
[9241](https://support.onefact.net/issues/9241) | attribute named current_state is dropped
[9242](https://support.onefact.net/issues/9242) | scope poly event with dot
[9243](https://support.onefact.net/issues/9243) | Formalising against secondary identifier which is superset of primary identifier misses referential spec from attribute
[9251](https://support.onefact.net/issues/9251) | Default attribute values with expression values are not present in exported MASL
[9252](https://support.onefact.net/issues/9252) | Referential added to wrong attribute on reflexive relationship
[9253](https://support.onefact.net/issues/9253) | Formalising against secondary identifier which is superset of primary identifier misses referential spec from attribute
[9255](https://support.onefact.net/issues/9255) | Pragmas on overloaded services appear on all versions of the service
[9257](https://support.onefact.net/issues/9257) | Pragmas on overloaded services appear on all versions of the service
[9264](https://support.onefact.net/issues/9264) | attribute named current_state is dropped
[9265](https://support.onefact.net/issues/9265) | Remove instance<Object> generic type from default MASL types
[9267](https://support.onefact.net/issues/9267) | Assigner state actions exported with wrong file name
[9268](https://support.onefact.net/issues/9268) | Assigner state actions exported with wrong file name
[9269](https://support.onefact.net/issues/9269) | Create (or update) a MASL modeling guide
[9270](https://support.onefact.net/issues/9270) | Missing parameters on states on objects with polymorphic events
[9271](https://support.onefact.net/issues/9271) | Event in subtype with same name as event in Supertype goes missing
[9272](https://support.onefact.net/issues/9272) | Missing parameters on states on objects with polymorphic events
[9273](https://support.onefact.net/issues/9273) | Event in subtype with same name as event in Supertype goes missing
[9275](https://support.onefact.net/issues/9275) | Missing formalism for reflexive non-associative relationship with collapsed referential.
[9276](https://support.onefact.net/issues/9276) | Missing Formalism for associative relationship with second relationship to the associative object
[9280](https://support.onefact.net/issues/9280) | Supertype state table missing rows
[9284](https://support.onefact.net/issues/9284) | Edits disappear
[9287](https://support.onefact.net/issues/9287) | Edits disappear
[9289](https://support.onefact.net/issues/9289) | cannot formalize 1:M reflexive
[9295](https://support.onefact.net/issues/9295) | Run masl_round_trip headlessly
[9296](https://support.onefact.net/issues/9296) | Run masl_round_trip headlessly   
[9297](https://support.onefact.net/issues/9297) | One-to-many associative formalises with wrong object in referential spec
[9298](https://support.onefact.net/issues/9298) | One-to-many associative formalises with wrong object in referential spec
[9299](https://support.onefact.net/issues/9299) | MASL Domain Export fails with core dump
[9300](https://support.onefact.net/issues/9300) | Document MASL idiom for polys
[9301](https://support.onefact.net/issues/9301) | MASL state action file not exported for state with entry from supertype and local events with paramters
[9304](https://support.onefact.net/issues/9304) | Turn on preference for masl style identifiers in MASL-specific build
[9307](https://support.onefact.net/issues/9307) | round-trip script only copes with five domains per .prj
[9308](https://support.onefact.net/issues/9308) | Pragmas on terminator services not exported
[9309](https://support.onefact.net/issues/9309) | round-trip script only copes with five domains per .prj
[9310](https://support.onefact.net/issues/9310) | Pragmas on terminator services not exported
[9312](https://support.onefact.net/issues/9312) | Description on project terminator services copied from the domain terminator service
[9315](https://support.onefact.net/issues/9315) | Description on project terminator services copied from the domain terminator service
[9317](https://support.onefact.net/issues/9317) | MASL Domain Export fails with core dump
[9320](https://support.onefact.net/issues/9320) | Terminator in domain in project with same name as another domain doesn't get .tr files exported
[9321](https://support.onefact.net/issues/9321) | Terminator in domain in project with same name as another domain doesn't get .tr files exported
[9323](https://support.onefact.net/issues/9323) | Missing formalism for reflexive non-associative relationship with collapsed referential.
[9325](https://support.onefact.net/issues/9325) | Don't always add xtext nature
[9328](https://support.onefact.net/issues/9328) | NPE in MASL port rename
[9354](https://support.onefact.net/issues/9354) | Attribute rename in masl project fails
[9359](https://support.onefact.net/issues/9359) | Capture all masl models for testing
[9361](https://support.onefact.net/issues/9361) | MASL Domain rename shows conflict error
[9362](https://support.onefact.net/issues/9362) | MASL project descriptions not imported into BridgePoint
[9363](https://support.onefact.net/issues/9363) | MASL project descriptions not imported into BridgePoint
[9365](https://support.onefact.net/issues/9365) | preferred unique referential attribute is missing 'unique' tag
[9366](https://support.onefact.net/issues/9366) | preferred unique referential attribute is missing 'unique' tag
[9368](https://support.onefact.net/issues/9368) | Move PragmaTest model to regression test suite
[9370](https://support.onefact.net/issues/9370) | Missing secondary identifier for combined referential attribute
[9371](https://support.onefact.net/issues/9371) | Missing secondary identifier for combined referential attribute
[9372](https://support.onefact.net/issues/9372) | Error UserEmptyHandleDetectedCallout O_ATTR masl2xtuml_O_ATTR_R114_unlink_is_defined_by
[9373](https://support.onefact.net/issues/9373) | Error UserEmptyHandleDetectedCallout O_ATTR masl2xtuml_O_ATTR_R114_unlink_is_defined_by  
    