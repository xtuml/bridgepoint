---

This work is licensed under the Creative Commons CC0 License

---

# Search of xtUML structural elements
### xtUML Project Analysis Note


### 1. Abstract

This note describes the requirements and analysis for an enhancement to the
xtUML search to be able to locate structural model elements.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10049](https://support.onefact.net/issues/10049) Headline issue   
<a id="2.2"></a>2.2 [BridgePoint DEI #942](https://support.onefact.net/issues/942)  Search facility   
<a id="2.3"></a>2.3 [Engineering documentation for original implementation](https://github.com/xtuml/internal/tree/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20111020/technical/notes/dts0100580449)   
<a id="2.4"></a>2.4 [Open Declaration Design Note](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/9761_9762_find_declarations/9761_9762_find_declarations.dnt.md)   

### 3. Background

The xtUML search capability was originally done against issue dts0100580449. This
issue is captured as DEI #942 [2.2].  The original documentation is archived
in a git repository [2.3] and provides valuable information about the design and
implementation.    

The search feature is modeled inside the xtUML metamodel in the `Search` package. The 
metamodel even has some infrastructure already in place that is meant to support 
searching element names.   

Interestingly, this support was never implemented in the original work, only OAL 
and Descriptions are currently searchable.  The notes do not give any reason why 
this shrinkage of scope occurred.   

### 4. Requirements

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

5.1  Any element that has a `Name` attribute should be analyzed to potentially
  be included in the new name search.  These pool of elements is found in Appendix 
  8.1.   
5.1.1  Just having a `Name` attribute does not mean the metamodel element should
  be included in structural name searches (e.g. `Element Visibility` should be
  excluded).   
  
5.2  Some structural elements that should be found by the new "Name" search do
  not actually have a "Name" attribute in the metamodel.  A key example of this
  is for events where the displayed event name is a combination of several pieces
  of information, one part of which is the metamodel attribute `SM_EVT.Mning`.   
5.2.1  A search of all metamodel classes that have a `get_name()` operation is
  found in appendix 8.2.  
5.2.2  Note that the `get_name()` operation provides the displayed name for an  
  element that may have concatenated or constructed name such as in the case of
  the displayed name for state events or associations ("R" + `Association.Numb`).  
5.2.3  Simply having a `get_name()` operation does not mean that xtUML search 
  should include the element in a structural name search.  In order to provide
  useful and relevant results we should judiciously consider which metamodel
  elements to look at. For example, `Association > Class As Associated One Side`
  has the metamodel operation `get_name()`, but this doesn't really make sense to 
  include in search results.   
  
5.3  BridgePoint recently added logic to find and show selected elements from
  OAL.  This is attached to the "Open Declaration" context menu entry.  The
  feature is described in [2.4].  The behavior needed to meet requirement 4.3 is
  the same as provided by "Open Declaration".  
  

### 6. Work Required

6.1  Modify existing metamodel tooling under the `Search` package to include 
  support for "Declaration queries".   
6.1.1  Add `processQuery()` operation to `Declarations Engine`  
6.1.2  Add `configureParticipants()` and `createParticipant()` operations to 
  `Declaration Query`  
6.1.3  Extend `Name Match` class to record desired information of matched 
  elements.   

6.2  Modify the Search EE implementation in `bp.core/Search_c.java`   
6.2.1  Add set to hold `declarationElementsInScope`  
6.2.2  Add `gatherDeclarationParticipants()` operation  

6.3  Update `action_language_description_util.arc` to create functions that 
  support querying classes for declaration (name) queries.  Decide what elements
  to support based on the analysis in 5.2   

6.4  Connect in Open Declaration functionality to opening a name/declaration 
  search result.  
  
6.5  Update `ModelSearchPage.java` and related hand-craft classes to provide new 
  hooks for structural element name searching.  

### 7. Acceptance Test

7.1  Extend existing search tests with JUnit automated tests to verify all 
  new functionality.    

### 8. Appendix

8.1  Metamodel classes with `Name` attribute.  Note that the following results are
  modified by hand to remove duplicates for readability since `O_ATTR` has both 
  `Name` and `Root_Nam` field that are populated with the value "Name".  
```
03:17 PM-~/git/bridgepoint/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa$ find . -exec grep -Hns "\'Name\'" {} \;
./Activity/Decision Merge Node/Decision Merge Node.xtuml:246:   'Name',
./Activity/Object Node/Object Node.xtuml:261:   'Name',
./Activity/Activity Diagram Action/Activity Diagram Action.xtuml:260:   'Name',
./Activity/Accept Event Action/Accept Event Action.xtuml:233:   'Name',
./Activity/Accept Time Event Action/Accept Time Event Action.xtuml:233: 'Name',
./Activity/Send Signal/Send Signal.xtuml:260:   'Name',
./Activity/Activity Partition/Activity Partition.xtuml:140: 'Name',
./Constants/Symbolic Constant/Symbolic Constant.xtuml:435:  'Name',
./Value/Variable/Variable.xtuml:589:    'Name',
./Value/Actual Parameter/Actual Parameter.xtuml:299:    'Name',
./Runtime Value/Value In Structure/Value In Structure.xtuml:113:    'Name',
./Component/Provision/Provision.xtuml:907:  'Name',
./Component/Component Library/Component Reference/Component Reference.xtuml:1759:   'Name',
./Component/Component Library/Imported Reference/Imported Reference.xtuml:324:  'Name',
./Component/Component Library/Port Reference/Port Reference.xtuml:269:  'Name',
./Component/Component Library/Imported Requirement/Imported Requirement.xtuml:571:  'Name',
./Component/Component Library/Imported Provision/Imported Provision.xtuml:536:  'Name',
./Component/Interface/Interface.xtuml:1032: 'Name',
./Component/Interface Operation/Interface Operation.xtuml:833:  'Name',
./Component/Executable Property/Executable Property.xtuml:471:  'Name',
./Component/Component/Component.xtuml:3505: 'Name',
./Component/Delegation/Delegation.xtuml:503:    'Name',
./Component/Requirement/Requirement.xtuml:1010: 'Name',
./Component/Property Parameter/Property Parameter.xtuml:733:    'Name',
./Component/Interface Signal/Interface Signal.xtuml:490:    'Name',
./Component/Signal Provisions and Requirements/Provided Operation/Provided Operation.xtuml:348: 'Name',
./Component/Signal Provisions and Requirements/Required Signal/Required Signal.xtuml:417:   'Name',
./Component/Signal Provisions and Requirements/Required Operation/Required Operation.xtuml:351: 'Name',
./Component/Signal Provisions and Requirements/Provided Signal/Provided Signal.xtuml:417:   'Name',
./Component/Port/Port.xtuml:214:    'Name',
./Message/Return Message/Return Message.xtuml:290:  'Name',
./Packageable Element/Element Visibility/Element Visibility.xtuml:148:  'Name',
./Packageable Element/Element Visibility/Element Visibility.xtuml:172:  'Name',
./Packageable Element/Search Result Set/Search Result Set.xtuml:96: 'Name',
./Packageable Element/Search Result Set/Search Result Set.xtuml:136:    'Name');
./Packageable Element/Packageable Element.xtuml:5576:   'Name',
./Packageable Element/Component Visibility/Component Visibility.xtuml:148:  'Name',
./Packageable Element/Component Visibility/Component Visibility.xtuml:172:  'Name',
./Packageable Element/Component Result Set/Component Result Set.xtuml:45:   'Name',
./Packageable Element/Component Result Set/Component Result Set.xtuml:136:  'Name');
./Instance/Instance/Instance.xtuml:875: 'Name',
./State Machine/State Machine State/State Machine State.xtuml:1164: 'Name',
./State Machine/State Machine Event Data Item/State Machine Event Data Item.xtuml:712:  'Name',
./State Machine/Non Local Event/Non Local Event.xtuml:545:  'Name',
./Subsystem/Operation/Operation.xtuml:962:  'Name',
./Subsystem/Attribute/Attribute.xtuml:1404: 'Name',
./Subsystem/Operation Parameter/Operation Parameter.xtuml:730:  'Name',
./Subsystem/Model Class/Model Class.xtuml:2383: 'Name',
./Domain/External Entity Data Item/External Entity Data Item.xtuml:145: 'Name',
./Domain/System Model/System Model.xtuml:528:   'Name',
./Domain/Bridge/Bridge.xtuml:836:   'Name',
./Domain/Bridge Parameter/Bridge Parameter.xtuml:754:   'Name',
./Domain/Data Type/Data Type.xtuml:694: 'Name',
./Domain/Function/Function.xtuml:856:   'Name',
./Domain/Function Parameter/Function Parameter.xtuml:664:   'Name',
./Domain/External Entity Event Data Item/External Entity Event Data Item.xtuml:151: 'Name',
./Domain/External Entity/External Entity.xtuml:527: 'Name',
./Domain/Exception/Exception.xtuml:290: 'Name',
./Domain/Enumerator/Enumerator.xtuml:271:   'Name',
./Domain/Structure Member/Structure Member.xtuml:561:   'Name',
./Interaction/Class Instance Participant/Class Instance Participant.xtuml:1029: 'Name',
./Interaction/Timing Mark/Timing Mark.xtuml:146:    'Name',
./Interaction/Use Case Participant/Use Case Participant.xtuml:230:  'Name',
./Interaction/Class Participant Attribute/Class Participant Attribute.xtuml:105:    'Name',
./Interaction/Time Span/Time Span.xtuml:150:    'Name',
./Interaction/Actor Participant/Actor Participant.xtuml:242:    'Name',
./Element Packaging/Package/Package.xtuml:6784: 'Name',
```
  
8.2  Metamodel classes with `get_name()` operation  
```
01:41 PM-~/git/bridgepoint/src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa$ find . -exec grep -Hns "\'get_name\'" {} \;
./Association/Association/Association.xtuml:246:    'get_name',
./Association/Class As Derived One Side/Class As Derived One Side.xtuml:70: 'get_name',
./Association/Class As Simple Formalizer/Class As Simple Formalizer.xtuml:70:   'get_name',
./Association/Class As Associated One Side/Class As Associated One Side.xtuml:70:   'get_name',
./Association/Derived Association/Derived Association.xtuml:100:    'get_name',
./Association/Class As Simple Participant/Class As Simple Participant.xtuml:72: 'get_name',
./Association/Class As Derived Other Side/Class As Derived Other Side.xtuml:71: 'get_name',
./Association/Class As Link/Class As Link.xtuml:151:    'get_name',
./Association/Class As Supertype/Class As Supertype.xtuml:77:   'get_name',
./Association/Class As Associated Other Side/Class As Associated Other Side.xtuml:71:   'get_name',
./Association/Class As Subtype/Class As Subtype.xtuml:151:  'get_name',
./Activity/Decision Merge Node/Decision Merge Node.xtuml:30:    'get_name',
./Activity/Flow Final Node/Flow Final Node.xtuml:30:    'get_name',
./Activity/Fork Join Node/Fork Join Node.xtuml:133: 'get_name',
./Activity/Initial Node/Initial Node.xtuml:186: 'get_name',
./Activity/Activity Final Node/Activity Final Node.xtuml:30:    'get_name',
./Activity/Activity Edge/Activity Edge.xtuml:147:   'get_name',
./Constants/Constant Specification/Constant Specification.xtuml:192:    'get_name',
./Constants/Literal Symbolic Constant/Literal Symbolic Constant.xtuml:317:  'get_name',
./Component/Component/Component.xtuml:2968: 'get_name',
./Component/Delegation/Delegation.xtuml:358:    'get_name',
./Component/Satisfaction/Satisfaction.xtuml:127:    'get_name',
./Component/Interface Reference/Interface Reference.xtuml:498:  'get_name',
./Message/Signal Message/Signal Message.xtuml:37:   'get_name',
./Message/Event Message/Event Message.xtuml:44: 'get_name',
./Message/Asynchronous Message/Asynchronous Message.xtuml:127:  'get_name',
./Message/Bridge Message/Bridge Message.xtuml:44:   'get_name',
./Message/Function Message/Function Message.xtuml:44:   'get_name',
./Message/Interface Operation Message/Interface Operation Message.xtuml:33: 'get_name',
./Message/Synchronous Message/Synchronous Message.xtuml:145:    'get_name',
./Message/Operation Message/Operation Message.xtuml:44: 'get_name',
./Message/Informal Synchronous Message/Informal Synchronous Message.xtuml:29:   'get_name',
./Message/Informal Argument/Informal Argument.xtuml:14: 'get_name',
./Message/Informal Asynchronous Message/Informal Asynchronous Message.xtuml:29: 'get_name',
./Message/Message Argument/Message Argument.xtuml:88:   'get_name',
./State Machine/Local Event/Local Event.xtuml:33:   'get_name',
./State Machine/Transition/Transition.xtuml:926:    'get_name',
./State Machine/Instance State Machine/Instance State Machine.xtuml:13: 'get_name',
./State Machine/Signal Event/Signal Event.xtuml:79: 'get_name',
./State Machine/Polymorphic Event/Polymorphic Event.xtuml:190:  'get_name',
./State Machine/Event Supplemental Data/Event Supplemental Data.xtuml:49:   'get_name',
./State Machine/Non Local Event/Non Local Event.xtuml:13:   'get_name',
./State Machine/Supplemental Data Items/Supplemental Data Items.xtuml:39:   'get_name',
./State Machine/State Machine Event/State Machine Event.xtuml:56:   'get_name',
./State Machine/Event Ignored/Event Ignored.xtuml:13:   'get_name',
./State Machine/Action/Action.xtuml:40: 'get_name',
./State Machine/Creation Transition/Creation Transition.xtuml:180:  'get_name',
./State Machine/State Event Matrix Entry/State Event Matrix Entry.xtuml:13: 'get_name',
./State Machine/State Machine/State Machine.xtuml:13:   'get_name',
./State Machine/Class State Machine/Class State Machine.xtuml:13:   'get_name',
./State Machine/No Event Transition/No Event Transition.xtuml:28:   'get_name',
./State Machine/Cant Happen/Cant Happen.xtuml:13:   'get_name',
./State Machine/New State Transition/New State Transition.xtuml:40: 'get_name',
./Use Case/Extend/Extend.xtuml:137: 'get_name',
./Use Case/Generalization/Generalization.xtuml:134: 'get_name',
./Use Case/Include/Include.xtuml:137:   'get_name',
./Use Case/Binary Association/Binary Association.xtuml:132: 'get_name',
./Subsystem/Class Identifier Attribute/Class Identifier Attribute.xtuml:128:    'get_name',
./Subsystem/Derived Base Attribute/Derived Base Attribute.xtuml:36: 'get_name',
./Subsystem/Referred To Identifier Attribute/Referred To Identifier Attribute.xtuml:33: 'get_name',
./Subsystem/New Base Attribute/New Base Attribute.xtuml:29: 'get_name',
./Subsystem/Class Identifier/Class Identifier.xtuml:36: 'get_name',
./Subsystem/Referential Attribute/Referential Attribute.xtuml:79:   'get_name',
./Subsystem/Imported Class/Imported Class.xtuml:255:    'get_name',
./Subsystem/Attribute Reference in Class/Attribute Reference in Class.xtuml:13: 'get_name',
./Communication/Communication Link/Communication Link.xtuml:493:    'get_name',
./Domain/External Entity Event/External Entity Event.xtuml:13:  'get_name',
./Domain/Core Data Type/Core Data Type.xtuml:181:   'get_name',
./Domain/Enumeration Data Type/Enumeration Data Type.xtuml:69:  'get_name',
./Domain/User Data Type/User Data Type.xtuml:52:    'get_name',
./Domain/Instance Reference Data Type/Instance Reference Data Type.xtuml:50:    'get_name',
./Domain/External Entity Event Data/External Entity Event Data.xtuml:29:    'get_name',
./Domain/Structured Data Type/Structured Data Type.xtuml:230:   'get_name',
./Interaction/External Entity Participant/External Entity Participant.xtuml:360:    'get_name',
./Interaction/Class Participant/Class Participant.xtuml:432:    'get_name',
./Interaction/Instance Attribute Value/Instance Attribute Value.xtuml:67:   'get_name',
./Interaction/Lifespan/Lifespan.xtuml:143:  'get_name',
./Interaction/Component Participant/Component Participant.xtuml:209:    'get_name',
./Interaction/Package Participant/Package Participant.xtuml:268:    'get_name',
```   

### End
