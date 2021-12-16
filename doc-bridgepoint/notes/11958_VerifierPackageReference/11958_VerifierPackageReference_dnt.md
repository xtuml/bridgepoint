---
This work is licensed under the Creative Commons CC0 License
---


# Support for Package Reference in Verifier
### xtUML Project Design Note


### 1. Abstract


Package References allow an empty package within a component to be 'assigned' as a reference to another package outside the component. 
When so assigned, the artifacts of the referred-to package appear to exist within the referring package. A referred-to package can be 
referred to by any number of assigned empty packages within multiple components.

This capability is supported by the BridgePoint Editor and various model compilers, but as yet has no execution support within Verifier. 
This work aims to remedy that.

### 2. document References

https://support.onefact.net/issues/11958

### 3. Background

### 4. Requirements

4.1 Assigned packages within any component shall be displayable in Session Explorer; they shall retain their pre-assignment names.

4.2 Classes defined within a referred-to package shall appear as "children" of an assigned referring package in Session Explorer.

4.2.1 Any instances of such classes, created within the parent component context, shall be displayable in Session Explorer.

4.2.2 Operations defined on such classes - both instance and class based - shall execute in invoking the component context.

4.2.2.1 Note: Verifier does not support direct execution of instance-based operations; these are not displayed in Session Explorer.

4.2.3 Mathematically derived attributes defined on such classes shall evaluate correctly when accessed.

4.2.4 State and transition activities defined in instance-based class models of such classes shall execute in invoking component context.

4.2.4.1 Note: Class-based state models are not addressed: see BridgePoint issue 12218.

4.3 Functions defined within a referred-to package shall appear as "children" of an assigned referring package in Session Explorer.

4.3.1 Any instances of such functions may be executed; they shall execute in the context of the invoking component.

4.4 Bridge operations in External Entities defined in referred-to packages shall execute in the invoking component context.

4.4.1 Note: As Bridge operations are not intended to be directly invocable, they shall not be displayed in Session Explorer.

Note: as Package Referencing applies to Packages - not Components - no support has been extended to Provided/Required Signals/Operations

### 5. Analysis
Package references are implemented by using a reflexive associative relationship between the definition of each referring package and the 
referred-to package to which it is assigned. This task involves determining when, and under which circumstances, this relationship should
be navigated.

### 6. Design
A combination of code analysis by examination, tracing of behavior with a debugger, and experimenting with code changes will be used to 
determine what modifications are required.

### 7. Design Comments


The relation R1402 is a reflexive associative which links referring package(s) to a referred-to package definition.

ElementPackaging::Package.InitializeClasses must follow R1402 to collect class definitions from a referred to package. The classes must, however, be initialized in the context of the correct execution engine, i.e. the invoking Component.

ElementPackaging::Package.ownsExecutableElementsOrChildOwnsExecutableElements and ElementPackaging::Package.isExecutingOrIsChildExecuting must follow R1402 so that the referring package is seen as containing artifacts of interest.

PackageableElement.collectVisibleElementsForName must follow R1402 as the referring package appears 'empty'.

ParserAllActivityModifier.java in org.xtuml.bp.als.oal must follow R1402 when collecting instances of Functions, Operations, State Activities, Transition Activities, Mathematically-derived Attributes and Bridge Operations. It must also, in initializeBodies, recurse into a referenced Package discovered across R1402. The R1402 relation is also followed in method resetPackagesBelow.

The various tree views in Verifier are populated by generated adapter classes which make use of hand-coded relationship chains, expressed as strings in .sql files, which specify navigation paths to elements of interest. For Session Explorer to have visibilty of referred-to artifacts some additional navigations are required. An added complexity is that some of these navigations must follow R1402 when determining whether there is content of interest, but must not be followed when evaluating packages for visibilty. A boolean attribute to allow suppression of visibility is added to the T_TPS node of org.xtuml.bp..ui.tree::UITree::UITree, populated by org.xtuml.bp.session::sql::UITree.pei.sql, and tested in create_generic_packages.

Thus, referenced packages are scanned when evaluating assigned packages for content, but are not made visible in their own right; their content appears as if declared in the assigned package.

An inconsistent - and, where package references are in effect, erroneous - retrieval of the execution engine in effect in TIM.java must be corrected. The consistent retrieval is timersMap.get(timersList.get(0))

A missing relationship phrase attribute in org.xtuml.bp.core/arc/parse_chain.inc must be added to support reflexive associations.

The R1402 relationship is reflexive. So when specifying relationship chains in .sql data, relationship phrases are required. Note that these must be enclosed in double single quote marks and must be formatted as the source phrases would appear after RSL $rc{..} processing.

### 8. Documentation

None

### 9. Unit Test

A test which exercises a variety of access via Package Referenes has been added to models/VandMC/VerifierTests.

### End
