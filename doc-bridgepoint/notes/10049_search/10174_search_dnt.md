---

This work is licensed under the Creative Commons CC0 License

---

# Search of xtUML structural elements
### xtUML Project Design Note


### 1. Abstract

This note describes the requirements and design for an enhancement to the
xtUML search to be able to locate structural model elements.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10174](https://support.onefact.net/issues/10174) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #10049](https://support.onefact.net/issues/10049) Initial service request  
<a id="2.3"></a>2.3 [Analysis note](10049_search_ant.md)   

### 3. Background

Users wish to use the search feature to locate structural elements in the model by 
name.  This note describes the design of this feature.  Additional background is 
available in the analysis [[2.3]](#2.3).

### 4. Requirements

__Carried forward verbatim from the analysis:__  
4.1  This feature shall be implemented as an extension to the existing xtUML Search
  capability.  
4.2  xtUML Search shall support locating a structural element by matching the
  user input against the structural element's name.     
4.2.1  A list of metamodel elements that the tool will support finding by name shall 
  be compiled and reviewed with xtUML users.   
4.3  Search results shall be displayed in the eclipse Search view.   
4.4  Double-clicking on a name-based result entry in the Search view shall 
  display the selected element in Model Explorer view. Elements with associated 
  graphical data shall also be displayed in a highlighted form on the canvas.     
4.5  The xtUML Search page shall be extended with a checkbox corresponding to
  name-based searching to match existing checkboxes for "Action Language" and 
  "Description" searching.  
  
### 5. Analysis

5.1 Searchable Elements  
5.1.1  The following metamodel instances have `Name` attributes and __will be__ included in
searches:    
```
Activity/Accept Event Action
Activity/Accept Time Event Action
Activity/Activity Diagram Action
Activity/Activity Partition
Activity/Decision Merge Node
Activity/Object Node
Activity/Send Signal
Component/Component
Component/Component Library/Component Reference
Component/Component Library/Imported Provision
Component/Component Library/Imported Reference
Component/Component Library/Imported Requirement
Component/Component Library/Port Reference
Component/Delegation
Component/Interface
Component/Interface Operation
Component/Interface Signal
Component/Requirement
Component/Port
Component/Property Parameter
Component/Provision
Component/Signal Provisions and Requirements/Provided Operation (not sure these 4 are needed...)
Component/Signal Provisions and Requirements/Provided Signal
Component/Signal Provisions and Requirements/Required Operation
Component/Signal Provisions and Requirements/Required Signal
Constants/Symbolic Constant
Domain/Bridge
Domain/Bridge Parameter
Domain/Enumerator
Domain/Exception
Domain/External Entity
Domain/External Entity Data Item
Domain/Function
Domain/Function Parameter
Domain/Structure Member
Domain/System Model
Element Packaging/Package
State Machine/State Machine Event Data Item
State Machine/State Machine State
Interaction/Actor Participant
Interaction/Class Instance Participant
Interaction/Class Participant Attribute
Interaction/Timing Mark
Interaction/Time Span
Interaction/Use Case Participant
Message/Return Message
Subsystem/Attribute
Subsystem/Model Class
Subsystem/Operation
Subsystem/Operation Parameter
```

5.1.2  The following metamodel instances have `get_name()` operations and __will be__ included in
searches:    
```
Association/Association
Communication/Communication Link
Component/Component
Component/Delegation
Component/Interface Reference
Component/Satisfaction
Constants/Constant Specification
Constants/Literal Symbolic Constant
Domain/Enumeration Data Type
Domain/Structured Data Type
Domain/User Data Type
Interaction/Class Participant
Interaction/Component Participant
Interaction/External Entity Participant
Interaction/Instance Attribute Value
Interaction/Package Participant
Message/Asynchronous Message
Message/Bridge Message
Message/Event Message
Message/Function Message
Message/Informal Argument
Message/Informal Asynchronous Message
Message/Informal Synchronous Message
Message/Interface Operation Message
Message/Message Argument
Message/Operation Message
Message/Signal Message
Message/Synchronous Message
State Machine/Creation Transition
State Machine/New State Transition
State Machine/State Machine Event
State Machine/Transition
Subsystem/Imported Class
Subsystem/Referential Attribute
```

5.2 Non-Searchable Elements  
5.2.1  The following metamodel instances have `Name` attributes, but __will not__ be included 
in searches:    
```
Component/Executable Property
Domain/Data Type
Domain/External Entity Event Data Item
Instance/Instance
Packageable Element/Element Visibility
Packageable Element/Component Result Set
Packageable Element/Component Visibility
Packageable Element/Packageable Element
Packageable Element/Search Result Set
Runtime Value/Value In Structure
State Machine/Non Local Event
Value/Actual Parameter
Value/Variable
```

5.2.2  The following metamodel instances have `get_name()` operations but __will not__ be 
included in searches:    
```
Activity/Activity Edge
Activity/Activity Final Node
Activity/Decision Merge Node
Activity/Flow Final Node
Activity/Fork Join Node
Activity/Initial Node
Association/Class As Associated One Side
Association/Class As Associated Other Side
Association/Class As Derived One Side
Association/Class As Derived Other Side
Association/Class As Link
Association/Class As Simple Formalizer
Association/Class As Simple Participant
Association/Class As Subtype
Association/Class As Supertype
Association/Derived Association
Domain/Core Data Type
Domain/External Entity Event
Domain/External Entity Event Data
Domain/Instance Reference Data Type
Interaction/Lifespan
State Machine/Action
State Machine/Cant Happen
State Machine/Class State Machine
State Machine/Event Ignored
State Machine/Event Supplemental Data
State Machine/Instance State Machine
State Machine/Local Event
State Machine/No Event Transition
State Machine/Non Local Event
State Machine/Polymorphic Event
State Machine/Signal Event
State Machine/State Event Matrix Entry
State Machine/State Machine
State Machine/Supplemental Data Items
Subsystem/Attribute Reference in Class
Subsystem/Class Identifier
Subsystem/Class Identifier Attribute
Subsystem/Derived Base Attribute
Subsystem/New Base Attribute
Subsystem/Referred To Identifier Attribute
Use Case/Binary Association
Use Case/Extend
Use Case/Generalization
Use Case/Include
```

### 6. Design

TODO

### 7. Design Comments

TODO


### 8. User Documentation

TODO


### 9. Unit Test

TODO


### End
