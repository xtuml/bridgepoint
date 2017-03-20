---

This work is licensed under the Creative Commons CC0 License

---

# Project terminator does not have to provide implementation for every domain terminator service
### xtUML Project Design Note


1. Abstract
-----------
A MASL project terminator does not have to provide an implementation for every domain terminator service. 
This issue is raised to implement support for this in BridgePoint.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9316](https://support.onefact.net/issues/9316)  
<a id="2.1.1"></a>2.1.1 [BridgePoint DEI #9311](https://support.onefact.net/issues/9311)  
This is the Service Pro issue associated with #9361.  
<a id="2.2"></a>2.2 [BridgePoint DEI #9041](https://support.onefact.net/issues/9041) 
Implement mechanism to prevent non-supported elements from persisting MASL.  
<a id="2.2.1"></a>2.2.1 [Design note for #9041](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/9041_activities/9041_activities_dnt.md)  
<a id="2.3"></a>2.3 [MASL round trip script](https://github.com/xtuml/mc/blob/master/bin/masl_round_trip)  
<a id="2.4"></a>2.4 [MASL round trip regression tests](https://github.com/xtuml/models/blob/master/masl/test/regression_test)  

3. Background
-------------
The [masl round trip test](https://github.com/xtuml/mc/blob/master/bin/masl_round_trip) is failing for a masl project 
that includes a domain terminator service that does not provide an implementation for every domain terminator 
service available. Following is what this model looks like:  
<a id="3.1"></a>3.1 Example masl domain  
```
domain DomainWithTerminator is 
  terminator T is
    public service s1();
    public service s2();
  end terminator;
end domain;
```  

<a id="3.2"></a>3.2 Example masl project  
```
project TerminatorServiceNotImplemented_PROC is
  domain DomainWithTerminator is 
    terminator T is
      public service s1();
    end terminator;
  end domain;
end project;
```  

3.3 BridgePoint MASL background related to this problem  
In BridgePoint, a masl project is represented by a BridgePoint project. Each masl project terminator is represented by a
BridgePoint interface. In BridgePoint, any component that uses an interface contains the full interface definition. Each 
interface instance is represented in a port instance inside the component instance that uses it.  A port in BridgePoint 
does not have to provide an implemenation of every operation and signal for an interface, but it does 
contain the signature for every operation and signal available. It is here that masl and xtuml differ. When a masl project 
does not include a signature for a service that is specifed in the domain terminator it means that that masl domain's implemenation will be used.  

Every action home in BridgePoint contains an attribute named dialect. The dialect attributes is an enumeration type that 
specifies the action bodies that BridgePoint allows. For example:  
3.3.1 OAL - this element may have an oal action body  
3.3.2 MASL - this element may have a masl action body  
3.3.3 C - this element may a C action body  
3.3.4 NONE - This element has no action body  

There are two possible dialect values for a terminator service in a masl domain:  
* MASL implementation (dialect MASL)  
* C++/external implementation (dialect C)  

There are three possible dialect values for a terminator service in a masl project:  
* Override with MASL implementation (dialect MASL)  
* Override with C++/external implementation (dialect C)  
* Don't override, implementation will fall back to domain implementation. (declaration not present in terminator)  

4. Requirements
---------------
The following requirements all refer to the handling of a situation where a masl project is imported into 
BridgePoint where the masl project includes a project terminator that does not provide an implemenation of all 
the domain terminator services. Please read the background section before proceeding.  
4.1 Bridgepoint shall create any missing service signatures in the port  
4.1.1 The missing service signatures shall have a dialect NONE in BridgePoint  
4.2 The x2m tool shall not write a domain terminator service signature for a masl project when an associated 
BridgePoint port message has dialect NONE  
4.3 The masl round trip test shall pass for the masl project specified by [3.2](#3.2).  

5. Analysis
-----------
Dialect type NONE was introduced when BridgePoint was modifed to implement a mechanism to prevent 
non-supported elements from persisting MASL, [2.2.1](#2.2.1). As described by that work, the goal was to 
not persist masl action bodies for cases where masl models do not support action bodies that BridgePoint 
does support.  

The issue at hand is that not only must an action body not be created, but the BridgePoint x2m tool must not 
generate a domain terminator service signature for the service in the project's terminator declaration in the 
project file.  

6. Design
---------
6.1 Create the missing service signature in the BridgePoint port  
After a model is imported the importer checks to see if it was a masl model that was imported. If it was, 
it process the imported instances. This check is done in ImportHelper.java::importModel(). Modify this code 
so that for masl import we check to see if it was a masl project that was imported. If it is a masl project, call 
"Synchronize With Library" (PullSynchronizationChanges.java::run()) to assure any missing interface operations are added.  
6.1.1 PullSynchronizationChanges.java::run() shall be modified to differentiate the CME call from this call  
This is needed because while the operation is very similar to what is required here, there are a couple of unique things this
call must assure:  
6.1.1.1 Assure no user interaction is required  
6.1.1.2 Assure operations added are marked with Dialect NONE  

6.2 Modify x2m to assure no domain terminator service signature is written to the masl project file when a project is being exported and it has a port with operation with dialect NONE  
6.2.1 Also assure that no .tr file is written in this case  

7. Design Comments
------------------
none  

8. User Documentation
---------------------
8.1 The masl user documentation shall be updated to assure the information in the table found in 
section 3.3 of [2.2.1](#2.2.1) is captured. When this is done, the information will be updated to 
account for the differences masl domains and masl projects.  

9. Unit Test
------------
9.1 The model called out in [3.2](#3.2) shall be added to the masl regession test script  
9.2 The [masl regression test script](#2.4) will be run and must pass  

End
---

