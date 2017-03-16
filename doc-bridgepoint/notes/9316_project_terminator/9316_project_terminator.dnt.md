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
<a id="2.2"></a>2.2 [BridgePoint DEI #9041](https://support.onefact.net/issues/9041) 
Implement mechanism to prevent non-supported elements from persisting MASL.  
<a id="2.3"></a>2.3 [MASL round trip script](https://github.com/xtuml/mc/blob/master/bin/masl_round_trip)  
<a id="2.4"></a>2.4 [MASL round trip regression tests](https://github.com/xtuml/models/blob/master/masl/test/regression_test)  

3. Background
-------------
In BridgePoint, a masl project is represented by a BridgePoint project. Each masl project terminator is represented by a
BridgePoint interface. In BridgePoint, any component that uses an interface contains the full interface definition. Each 
interface instance is represented in a port instance inside the component instance that uses.  A port in BridgePoint 
does not have to provide an implemenation of every opertion and signal for an interface, but it does 
contain the signature for every operation and signal available. It is here that masl and xtuml differ.  

5.1 The BridgePoint Dialect attribute  
5.1 Every action body in BridgePoint contains a attribute named dialect. The current possible values for this attribute are:  
5.1.1 OAL - this element may have an oal action body  
5.1.2 MASL - this element may have a masl action body  
5.1.3 C - this element may a defined action C but it is external  
5.1.4 NONE - This element has no action body  

The [masl round trip test](https://github.com/xtuml/mc/blob/master/bin/masl_round_trip) is failing for masl project 
that includes a domain terminator service that does not provide an implementation for every domain terminstor 
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

4. Requirements
---------------
The following requirements all refer to the handling of a situation where a masl project is imported into 
BridgePoint where the project includes a project terminator that does not provide an implemenation of all 
the domain terminator services. The reader should read the background to understand and see such a model.  
4.1 Bridgepoint shall create a the missing service signature in the port.  
4.1.1 The missing service signature shall have a dialect NONE   
4.2 TODO: There needs to be an addtional marking, or something, to account for the fact that x2m can NOT 
create a masl signature to the masl project file for this service. If it does so, the round trip test will fail.
4.3 The masl round trip test shall pass for the masl project specified by [3.2][#3.2].

5. Analysis
-----------

6. Design
---------
   

7. Design Comments
------------------
none  

8. User Documentation
---------------------
none  

9. Unit Test
------------
9.1 The model called out in [3.2](#3.2) shall be added to the masl regession test script  
9.2 The masl regression test script will be run and must pass  

End
---

