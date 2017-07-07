BridgePoint xtUML Release Notes
========================

Release 6.4.0  

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
BridgePoint 6.4 is compatible with workspaces and projects created with prior 
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
[7570](https://support.onefact.net/issues/7570) |  Create automated unit testing for build server
[7659](https://support.onefact.net/issues/7659) |  failure for manual test testing Constant data and Array Attribute in SE & Spotlight views #1562
[7812](https://support.onefact.net/issues/7812) |  Clean up BridgePoint JUnit tests
[8033](https://support.onefact.net/issues/8033) |  Create No Variable (ACT_CNV) not translated correctly
[8415](https://support.onefact.net/issues/8415) |  Use relate and unrelate of pyrsl.
[8547](https://support.onefact.net/issues/8547) |  Use create for WFL instead of SQL
[8593](https://support.onefact.net/issues/8593) |  bp.io.mdl.test failures
[8605](https://support.onefact.net/issues/8605) |  Complete build server unit test integration
[8608](https://support.onefact.net/issues/8608) |  The Verifier 1 unit test suite has failures
[8612](https://support.onefact.net/issues/8612) |  The Compare test suite has failures
[8930](https://support.onefact.net/issues/8930) |  Build "user" and "developer" versions of BridgePoint
[8960](https://support.onefact.net/issues/8960) |  Enhance mechanism for selective core builds
[9029](https://support.onefact.net/issues/9029) |  Fix Unit test build problems caused by moving test plugins (and data) to their own repository
[9035](https://support.onefact.net/issues/9035) |  Add markable_name column to application.mark.
[9037](https://support.onefact.net/issues/9037) |  support multiple pragma items in x2m
[9040](https://support.onefact.net/issues/9040) |  No default marking files
[9055](https://support.onefact.net/issues/9055) |  Docgen is failing
[9166](https://support.onefact.net/issues/9166) |  Failures in io.mdl.tests
[9209](https://support.onefact.net/issues/9209) |  Rework compare test infrastructure and address any real issues found
[9214](https://support.onefact.net/issues/9214) |  Update hudson BridgePoint tooling
[9217](https://support.onefact.net/issues/9217) |  Unable to rename a masl domain
[9288](https://support.onefact.net/issues/9288) |  Invalid Syntax Error Highlight
[9324](https://support.onefact.net/issues/9324) |  xtUML Metamodel example project creation hangs
[9332](https://support.onefact.net/issues/9332) |  MASL export error on state machine without event
[9334](https://support.onefact.net/issues/9334) |  Perform unit testing from the xtuml/bptest repository
[9343](https://support.onefact.net/issues/9343) |  Content assist doesn't show first time some operators are typed
[9344](https://support.onefact.net/issues/9344) |  Rename of Domain Service in masl project fails to update .mod
[9345](https://support.onefact.net/issues/9345) |  Rename of masl datatype fails
[9346](https://support.onefact.net/issues/9346) |  Domain Service Parameter in masl project rename failure
[9347](https://support.onefact.net/issues/9347) |  Terminator rename in masl project fails
[9348](https://support.onefact.net/issues/9348) |  Terminator service rename in masl project fails
[9349](https://support.onefact.net/issues/9349) |  Terminator service parameter rename in masl project fails
[9351](https://support.onefact.net/issues/9351) |  Object rename in masl project fails
[9352](https://support.onefact.net/issues/9352) |  Object service rename in masl project fails
[9360](https://support.onefact.net/issues/9360) |  MASL editor incorrectly calls out syntax error on deferred operation
[9380](https://support.onefact.net/issues/9380) |  Duplicate comment on referential attribute
[9384](https://support.onefact.net/issues/9384) |  Use of undefined classes and associations
[9387](https://support.onefact.net/issues/9387) |  MASL parser does not distinguish ternary traversal.
[9391](https://support.onefact.net/issues/9391) |  Run regression models through MASL integration test
[9392](https://support.onefact.net/issues/9392) |  [masl] in the grammar, add parentheses around deferrd expressions
[9393](https://support.onefact.net/issues/9393) |  [masl] allow both ends of a relationship to have the same name
[9394](https://support.onefact.net/issues/9394) |  [masl] AbstractMaslModelTests only looks at first caret
[9395](https://support.onefact.net/issues/9395) |  [masl] fix return type of timestamp - duration
[9396](https://support.onefact.net/issues/9396) |  [masl] Multiple ArrayIndexOutOfBoundsExceptions when importing example models into workspace
[9397](https://support.onefact.net/issues/9397) |  [masl] use context to disambiguate enumerator references
[9398](https://support.onefact.net/issues/9398) |  Extend CLI to be able to run multiple commands in a single instance of Eclipse
[9417](https://support.onefact.net/issues/9417) |  Domain terminator name clash
[9432](https://support.onefact.net/issues/9432) |  masl_check reports error for default values with enumerate type
[9433](https://support.onefact.net/issues/9433) |  masl_check fails with service/function overload
[9435](https://support.onefact.net/issues/9435) |  masl_check: find expression cannot resolve attribute reference
[9436](https://support.onefact.net/issues/9436) |  masl_check: assignment of integer literal to long_integer fails
[9437](https://support.onefact.net/issues/9437) |  update masl_round_trip on build server
[9438](https://support.onefact.net/issues/9438) |  Build mc executables on the build server
[9441](https://support.onefact.net/issues/9441) |  masl_check: Expression types for Super-Subtype navigations
[9443](https://support.onefact.net/issues/9443) |  masl_check: recursive structure causes stack overflow
[9449](https://support.onefact.net/issues/9449) |  masl_check: 'any characteristic
[9450](https://support.onefact.net/issues/9450) |  masl_check: Based literals
[9452](https://support.onefact.net/issues/9452) |  masl_check: schedule event generation expecting duration for 'at' expression
[9459](https://support.onefact.net/issues/9459) |  masl_check: Duplicate type definition from .mod and .int?
[9461](https://support.onefact.net/issues/9461) |  masl_check: Promotion to sequence type failing
[9463](https://support.onefact.net/issues/9463) |  masl_check: Enum lookup from another domain not resolving ambiguity correctly
[9464](https://support.onefact.net/issues/9464) |  MASL Editor - Elements found by Open declaration/find references open the wrong editor
[9470](https://support.onefact.net/issues/9470) |  masl_check: Relationship navigation, links and unlinks
[9472](https://support.onefact.net/issues/9472) |  masl_check: assignment to anonymous types
[9474](https://support.onefact.net/issues/9474) |  masl_check: Whitespace not ignored in domain scoping
[9476](https://support.onefact.net/issues/9476) |  masl_check: 'length characteristic not found on nested type definition
[9479](https://support.onefact.net/issues/9479) |  masl_check: instance return type error
[9480](https://support.onefact.net/issues/9480) |  masl_check: recursive structure causing stack overflow on assignment
[9508](https://support.onefact.net/issues/9508) |  Run masl_check on build server
[9524](https://support.onefact.net/issues/9524) |  Use consistent build and test approach
[9525](https://support.onefact.net/issues/9525) |  masl_check: empty statement
[9526](https://support.onefact.net/issues/9526) |  masl_check: dictionary errors
[9527](https://support.onefact.net/issues/9527) |  masl_check: promotion of ambiguous enum to sequence
[9528](https://support.onefact.net/issues/9528) |  masl_check extremely slow and memory hungry for large domains
[9531](https://support.onefact.net/issues/9531) |  MC3020 does not calculate derived attributes before copying over referentials
[9532](https://support.onefact.net/issues/9532) |  masl_check: duration'seconds parameter type
[9533](https://support.onefact.net/issues/9533) |  masl_check: Support non-integer dictionary keys
[9534](https://support.onefact.net/issues/9534) |  Update note templates
[9535](https://support.onefact.net/issues/9535) |  Add timestamps to BP build process
[9537](https://support.onefact.net/issues/9537) |  Flip MASL settings on for upcoming 6.2.6 release.
[9540](https://support.onefact.net/issues/9540) |  masl_check: scoping in find clause
[9565](https://support.onefact.net/issues/9565) |  Saab- 10 : Move Prefix name and Prefix type
[9580](https://support.onefact.net/issues/9580) |  Ambiguous Enum lookup in object/instance services, event generation and terminator service parameters.
[9582](https://support.onefact.net/issues/9582) |  masl_check: expressions containing function calls exponentially slower for each extra term
[9590](https://support.onefact.net/issues/9590) |  Builtin exception types
[9618](https://support.onefact.net/issues/9618) |  masl_check: dictionary with non-integer keys for user defined type
[9619](https://support.onefact.net/issues/9619) |  masl_check: Time arithmetic result types
[9620](https://support.onefact.net/issues/9620) |  masl_check: inline initialisation of instance of variable
[9621](https://support.onefact.net/issues/9621) |  masl_check: call to domain service in domain with same name as terminator service not resolving correctly
[9625](https://support.onefact.net/issues/9625) |  masl_check: promotion to sequence for concatentation and other collection operators
[9627](https://support.onefact.net/issues/9627) |  Clarify MASL Documentation
[9638](https://support.onefact.net/issues/9638) |  masl_check: Empty where clause on navigate causes error
[9639](https://support.onefact.net/issues/9639) |  masl_check: All characteristic signature parameters and return types of type integer should be anonymous long_integer
[9641](https://support.onefact.net/issues/9641) |  masl_check: Ambiguous Enum lookup in instance creation statements
[9644](https://support.onefact.net/issues/9644) |  type of enum'range should be range of enum
[9646](https://support.onefact.net/issues/9646) |  typename resolution on rhs of find expression
[9648](https://support.onefact.net/issues/9648) |  ordered_by result type
[9649](https://support.onefact.net/issues/9649) |  return type of get_unique characteristic should be anonymous set
[9650](https://support.onefact.net/issues/9650) |  Crash in masl_check for nested for loops with same loop variable name
[9655](https://support.onefact.net/issues/9655) |  Masl mark value data not populated in marking editor