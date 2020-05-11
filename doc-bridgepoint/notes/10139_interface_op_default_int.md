---

This work is licensed under the Creative Commons CC0 License

---

# Published interface operations have wrong dialect in MASL mode
### xtUML Project Implementation Note

### 1. Abstract

When a user runs "Publish to interface" on a domain function in MASL mode, the 
dialect of the operation published to the interface is set to MASL when it should 
be set to None in order to follow the MASL modeling idiom.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10139](https://support.onefact.net/issues/10139) Headline issue.   

### 3. Background

BridgePoint already has some logic in place that sets the dialect of signals and 
operations to "None", as required, when a new interface operation is created.  

However, this code is not used when new operations (or signals) are added to an 
existing interface, either directly by the user or using "Publish to interface".  

Note that in the MASL idiom, only operation messages are used.  However, existing
BridgePoint internal management code handles signals as well as operations for 
completeness and that continues with this work.  

### 4. Requirements

4.1  An operation added to a provided interface on a domain (i.e. a MASL domain 
service) shall have dialect None and empty action semantics.  
4.1.1  This same operation shall have dialect None and empty action semantics on 
the operation in the provided interface of the component reference in a MASL Project.   
4.1.2  This same operation shall have dialect None and empty action semantics on 
the operation in the required interface of the component representing a MASL Project.   

4.2  An operation added to a required interface on a domain (i.e. a MASL terminator 
service) shall have dialect MASL and default MASL action semantics.  
4.2.1  This same operation shall have dialect MASL and default MASL action semantics 
on the operation in the required interface of the component reference in a MASL Project.   
4.2.2  This same operation shall have dialect MASL and default MASL action semantics 
on the operation in the provided interface of the component representing a MASL Project.   

4.3  The behavior specified in sections 4.1 and 4.2 shall also apply for signals.   

4.4  The behavior specified in sections 4.1 and 4.2 shall apply for operations added
to interfaces directly by the user with 'New > Operation' or when using the 
'Publish to interface' wizard.  

4.5  Newly assigned component references in MASL projects to MASL domains shall have
all interface messages set correctly.  

4.6  Newly assigned interface references in MASL domains shall have all interface 
messages set correctly depending on if the interface is provided or required.  

### 5. Work Required

5.1  For each type of: Provided Operation, Provided Signal, Required Operation, 
and Required Signal move the code block that performs the MASL to None dialect
conversion from 'Interface Reference::createSignalsAndOperations()' into the matching
'initialize()' operation in each class.  
5.1.1  Make sure the 'initialize()' calls are performed after dependent relationships
are configured as they are used for figuring out if the message inside the interface
reference is in a project or a domain.      

### 6. Implementation Comments

None.  
 
### 7. Unit Test
This test uses the MASL 'calculator' test model available at https://github.com/xtuml/models/tree/master/masl/calculator  
and assumes the BridgePoint preferences are set up with MASL as the default action
language.  

7.1 Manually add message  
7.1.1  Add a new operation "manop" to interface calc in calc domain  
* __R__ In the calc project, calc component, calc port, calc provided interface, manop has dialect None, and empty action semantics  
* __R__ In the calculator project, calculator component, calc port, calc required interface,  manop has dialect None, and empty action semantics     
* __R__ In the calculator project, calc component reference, calc port, calc provided interface,  manop has dialect None, and empty action semantics     

7.1.2  Add a new signal "mansig" to interface calc in calc domain  
* __R__ In the calc project, calc component, calc port, calc provided interface, mansig has dialect None, and empty action semantics  
* __R__ In the calculator project, calculator component, calc port, calc required interface,  mansig has dialect None, and empty action semantics     
* __R__ In the calculator project, calc component reference, calc port, calc provided interface,  mansig has dialect None, and empty action semantics     

7.1.3  Add a new operation "mantermop" to interface disp in calc domain   
* __R__ In the calc project, calc component, disp port, disp required interface, mantermop has dialect MASL, and default action semantics  
* __R__ In the calculator project, calculator component, calc__disp port, disp provided interface,  mantermop has dialect MASL, and default action semantics     
* __R__ In the calculator project, calc component reference, disp port, disp required interface,  mantermop has dialect MASL, and default action semantics     

7.1.4  Add a new signal "mantermsig" to interface disp in calc domain   
* __R__ In the calc project, calc component, disp port, disp required interface, mantermsig has dialect MASL, and default action semantics  
* __R__ In the calculator project, calculator component, calc__disp port, disp provided interface,  mantermsig has dialect MASL, and default action semantics     
* __R__ In the calculator project, calc component reference, disp port, disp required interface,  mantermsig has dialect MASL, and default action semantics     

7.2  Publish message  
7.2.1  In the calc domain, add a new function to the functions package named pubfunc
* Select pubfunc and choose Publish to interface from the context menu
* Choose the calc interface as the target
* __R__ In the calc project, calc component, calc port, calc provided interface, pubfunc has dialect None, and empty action semantics  
* __R__ In the calculator project, calculator component, calc port, calc required interface,  pubfunc has dialect None, and empty action semantics     
* __R__ In the calculator project, calc component reference, calc port, calc provided interface,  pubfunc has dialect None, and empty action semantics     

7.2.2  In the calc domain, add a new function to the functions package named pubfunc2
* Select pubfunc2 and choose Publish to interface from the context menu
* Choose the disp interface as the target
* __R__ In the calc project, calc component, disp port, disp required interface, pubfunc2 has dialect MASL, and default action semantics  
* __R__ In the calculator project, calculator component, calc__disp port, disp provided interface,  pubfunc2 has dialect MASL, and default action semantics     
* __R__ In the calculator project, calc component reference, disp port, disp required interface,  pubfunc2 has dialect MASL, and default action semantics     

7.3  Assign component reference  
* Add a new component reference to the types package of the calculator project
* Assign the component reference to the calc component  
* __R__ All messages under the calc port, calc provided interface have dialect None and empty action semantics 
* __R__ All messages under the disp port, disp required interface have dialect MASL and default action semantics 

7.4  Assign interface references  
* Add a new component calctest as a sibling to the calc component in the calc project
* Add a provided interface and a required interface to calctest
* Formalize the provided interface to the calc interface
* Formalize the required interface to the disp interface
* __R__ All messages under the Port1 port, calc provided interface have dialect None and empty action semantics 
* __R__ All messages under the Port2 port, disp required interface have dialect MASL and default action semantics 

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__    
Branch: __10139_interface_op_default__ 

<pre>
 doc-bridgepoint/notes/10139_interface_op_default_int.md
 .../models/org.xtuml.bp.core/ooaofooa/Component/Interface Reference/Interface Reference.xtuml         | 48 ++++++++----------------------------------------
 .../ooaofooa/Component/Signal Provisions and Requirements/Provided Operation/Provided Operation.xtuml | 11 +++++++++--
 .../ooaofooa/Component/Signal Provisions and Requirements/Provided Signal/Provided Signal.xtuml       | 11 +++++++++--
 .../ooaofooa/Component/Signal Provisions and Requirements/Required Operation/Required Operation.xtuml | 11 +++++++++--
 .../ooaofooa/Component/Signal Provisions and Requirements/Required Signal/Required Signal.xtuml       | 11 +++++++++--
</pre>

### End

