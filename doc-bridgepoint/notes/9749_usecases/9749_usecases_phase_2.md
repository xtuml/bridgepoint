---

This work is licensed under the Creative Commons CC0 License

---

# OAL auto-completion use cases
### xtUML Project Use Case Note

### 1. Abstract

This note describes the required use cases for OAL auto-completion.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9749](https://support.onefact.net/issues/9749) Provide use cases for OAL auto-completion.    
<a id="2.2"></a>2.2 [BridgePoint DEI #42](https://support.onefact.net/issues/42) standards-based OAL text editor.    
<a id="2.3"></a>2.3 [BridgePoint DEI #9571](https://support.onefact.net/issues/9571) Enhanced OAL Editor (phase 1).    
<a id="2.4"></a>2.4 OAL Reference, Help > BridgePoint UML Suite Help > Reference > OAL Reference.    
<a id="2.5"></a>2.5 [Design Note](https://github.com/leviathan747/bridgepoint/blob/9763_content_assistance/doc-bridgepoint/notes/9763_content_assistance/9763_content_assistance_dnt.md) 9763 Design note.    

### 3. Background

Use cases are required to initiate further testing development for [[2.1]](#2.3)

### 4. Requirements

4.1 Provide a document with use cases for auto-completion.  

### 5. Analysis

5.1 Generation from ooaofooa

After analysis it is believed that the ooaofooa holds all considerations in the metamodel.  This is through the Body Subsystem.  The Action bodies, statements and variables lists were generated from the ooaofooa.   

5.1.1 Action Bodies  

Action bodies define where OAL can live.  The following are the action bodies that are supported by the ooaofooa:  

* State Action Body
* Derived Attribute Body
* Function Body
* Operation Body
* Bridge Body
* Provided Operation Body
* Provided Signal Body
* Required Operation Body
* Required Signal Body
* Transition Action Body

5.1.2 Statements  

Statements control data and logic in OAL, the following are the supported statements:  

* For Stmt
* While Stmt
* If Stmt
* ElseIf Stmt
* Else Stmt
* Bridge Invocation
* Function Invocation
* Return Stmt
* Operation Invocation
* Assign to Member
* Delete
* Create No Variable
* Create
* Select Related Where one
* Select Related Where any
* Select Related Where many
* Select Related By one
* Select Related By any
* Select Related By many
* Select From Instances one
* Select From Instances any
* Select From Instances many
* Select From Instances Where one
* Select From Instances Where any
* Select From Instances Where many
* Unrelate Using
* Unrelate
* Relate Using
* Relate
* Control
* Break
* Continue
* Create Event to Instance
* Create Event to Class
* Create Event to Creator
* Generate
* Generate to Class
* Generate to Creator
* Generate Preexisting Event
* Interface Operation Invocation
* Signal Invocation

5.1.3 Variables  

Variables are either instance handles, or sets of instance handles, or transient variables which are typed.  

* User Data Type
* Instance Handle
* Instance Set
* Transient Var: Multiplicity
* Transient Var: Justification
* Transient Var: Style
* Transient Var: End
* Transient Var: ParseStatus
* Transient Var: Scope
* Transient Var: ModelEventNotification
* Transient Var: Visibility
* Transient Var: instance
* Transient Var: Token
* Transient Var: OalConstants
* Transient Var: RunStateType
* Transient Var: Breakpoint_Type
* Transient Var: StateChangeType
* Transient Var: EventProcessType
* Transient Var: IFDirectionType
* Transient Var: StateEnum
* Transient Var: ReentrantLock
* Transient Var: ElementTypeConstants
* Transient Var: long
* Transient Var: SearchScope
* Transient Var: SynchronizationType
* Transient Var: Severity
* Transient Var: ActionDialect
* Transient Var: void
* Transient Var: boolean
* Transient Var: integer
* Transient Var: real
* Transient Var: string
* Transient Var: unique_id
* Transient Var: state State_Model
* Transient Var: same_as Base_Attribute
* Transient Var: inst_ref Object
* Transient Var: inst_ref_set Object
* Transient Var: inst Event
* Transient Var: component_ref
* Transient Var: date
* Transient Var: inst_ref Timer
* Transient Var: timestamp

5.2 Further rules for filtering  

5.2.1  Statement Beginning:

At the beginning of a statement, statement types and variable types shall be allowed.  `self` handling is later defined.  Possibilities:

`::, ., <Class_KeyLetters>, <EE_KeyLetters>, <Port Name>, Beginning Keywords`

5.2.2 Operation invocations: 

Class Based: Class based invocations require the class key letters to be successfully parsed.  If the :: characters follow, the list shall contain all class based operations for the class.

Instance based operations work against either self or an Instance reference variable.  Once parsed and the '.' character is typed all instance based operations shall be listed.  

5.2.3 bridges:

After a successful parse of External Entity Key Letters all bridges after the EE key letters and :: characters shall show all bridges under such an EE.

5.2.4 function invocations:

After the :: characters have been typed all functions shall exist in the auto completion list.  Note that functions shall only show if beginning a statement or used as an expression to a part of an existing statement.  For instance functions shall not show if part of the start of an External Entity statement.

5.2.5 component ports:

After the send keyword has been parsed, unless omitted, all valid ports on the first parent component shall be added to the list.

5.2.6 interface invocations:

After the port has been parsed and the :: characters are typed the completion list shall contain all available operations and signals for the port.  Direction must be considered here, only those operations/signals with valid direction shall show in the list.

5.2.7 association traversal:

If the -> characters are typed after a successful parse of an instance handle, instance set handle, or a previous link associated classes shall show in the list.  This list shall contain entries like O_ATTR[R102], O_ID[R104].  The list shall be filtered based on the expected cardinality. If it is a select one only those classes at the end with cardinality one shall show.  

5.2.8 relationship phrases:

If the . character is typed and follows an association (Key_Lett[R<Numb>]) within square brackets, the associated relationship phrases shall be shown in the list.

5.2.9 event generation:

All events shall be listed if the generate statement precedes the current cursor location.  The list shall contain any locally created event variables, all events labeled by <Key_Lett>:<Mning.'event_meaning'> (parameters), and all classes which have attributes of the `inst<Event>` type.  After the 'to' string all instances which have such an event may be listed.  `class` may only show if the event is defined in a class state machine.  `creator` may only show if the event the statement is assigned to a creation event. 

5.2.10 Assignment:

For re-assignments the right value shall be filtered based on the initial type from the original assignment.  This includes assignments to attributes.  Attributes listed shall be filtered to exclude types of unique_id if they participate as an identifier.  The ooa specification reserves such attributes to be handled by the architecture.

5.2.11 Scoping  

Blocks are created by each statement below and follow all rules preceding this section. 

5.2.11.1 If 

The 'if' statement shall always be available as a beginning statement.  The 'elif' statement shall only be available if in an 'if' block.  The 'else' statement shall be available as long is the containing block does not already have one.

5.2.11.2 For

The 'for' statement shall always be available as a beginning statement.  

5.2.11.3 While

The 'while' statement shall always be available as a beginning statement.

5.2.11.4 Break and Continue

The 'break' and 'continue' keywords may only be present if within a 'for' or 'while' statement.

5.2.11.5 Return

The 'return' keyword shall only show in the following action bodies:

* Function Body
* Operation Body
* Bridge Body
* Provided Operation Body
* Required Operation Body

If an action is void and 'return' is parsed, nothing shall show in the list after the 'return'.


5.2.11.6 Variable Scoping

Variable access shall be filtered from lists based on scoping.

5.2.12 Using

After the 'using' keyword only those previously created instances that are the link class of the association may show.

5.2.13 Relates and unrelates

Filtering for the source and target shall filter on all previous existing instances.  After the 'across' statement only associations between the source and target shall show.  Note that we can not filter the target as we do not yet know the association.

5.2.14 Alpha-numeric

All results shall be added to a pre-filtered list based on the above rules.  The displayed list shall be alpha-numerically filtered further during typing based on currently typed characters.  Example:

sel shows select and any class that starts with sel  
f shows for and any class that starts with f

5.2.15 Self

Self is allowed in the following action bodies:

* Derived Attribute Body
* Operation Body, unless the operation is class based.
* Instance state machine actions

Self cannot be assigned to.  

5.2.16 The where  clause 

The where clause may only be present if within a select statement, and only as the last portion.  The 'selected' keyword may only exist in the list if within a where clause.  The list shall be filtered when using other statements, like attributes, such that they would produce a boolean result.

5.2.17 param

The 'param' keyword after the '.' character shall show all parameters passed to the action body.  

5.2.18 Empty, not empty, cardinality

The list shall only contain instances or instance sets after the following keywords:

* empty
* not empty 
* cardinality 

5.2.19 Parameters  

5.2.19.1 Full signature versus parameter completion  

A preference is expected to control whether or not a proposal list with full signature is offered, rather than allowing completion of each individual parameter.  For full signature, the list shall contain the full signature and parameters shall be modified after selection.  For individual parameters, the list shall remain the same only a new list for the parameters shall be shown.  This list shall only contain parameters available on the operation.

5.2.20 Object creation  

After a complete create statement including the `of` keyword include all visible classes key letters.  

5.2.21 Deletions  

After a delete statement up until the local variable, all local instance variables shall be shown.  Self shall also be included in this list as long as the self rules from section 5.2.15 are followed.  

5.2.22 Expressions  

Expressions shall share the same behavior as specified above for beginning statements, section 5.2.1.  In an expression the list shall be further filtered to include only those that share they same return type as the left hand side.  

5.2.23 event creation:

All visible events shall be listed if the create event statement, including the `of` portion, precedes the current cursor location.  After the `to` keyword is given the list shall contain all local instance references, all visible Classes key letters with the appended `class` keyword, all visible Classes key letters which have a creation transition and the selected event is assigned to the transition with the appended `creator` keyword.  

### 6. Use Cases

6.1 At the beginning of a new statement:  
6.1.1 `control stop`  
6.1.2 `create event instance`  
6.1.3 `create object instance`  
6.1.4 `delete object instance`  
6.1.5 `for each`  
6.1.6 `generate`  
6.1.7 `if`  
6.1.8 `param`  
6.1.9 `relate`  
6.1.10 `return`  
6.1.11 `select any`  
6.1.12 `select many`  
6.1.13 `select one`  
6.1.14 `send`  
6.1.15 `unrelate`  
6.1.16 `while`  
6.1.17 `break`  
6.1.18 `continue`  
6.1.19 `end while`  
6.1.20 `end for`  
6.1.21 `elif`  
6.1.22 `else`  
6.1.23 `end if`  
6.1.24 `self`  
6.1.25 All local variables in scope  
6.1.26 All EE key letters of visible EEs with at least one bridge of return type `void`  
6.1.27 All class key letters of visible classes with at least one class based operation of return type `void`  
6.1.28 All port names of containing component  

6.2.1 Statement beginnings, see section 6.1  

6.2.2 After `<inst_ref_var>.`:  
6.2.2.1 All attributes (if this is a right hand side expression, only propose attributes of the same type)  
6.2.2.2 All instance based operations of the class  
6.2.2.2.1 If a right hand side expression, only propose operations that return the declared type  
6.2.2.2.2 If this is a statement only propose operations that return `void`  

6.2.3 Interface operation invocations  
6.2.3.1 See generic section 6.2.24  

6.2.4 After `::`:  
6.2.4.1 All visible domain functions  
6.2.4.1.1 If invoked as a statement, only present functions with `void` return type  
6.2.4.1.2 If invoked as the right hand side of an expression, only present functions with a return type matching the left hand side  

6.2.5 After `send`:  
6.2.5.1 Port names of containing component  
6.2.5.2 After `send <port_name>::`:  
6.2.5.2.1 All interface messages (include the full signature with parameter prototypes)  
6.2.5.2.1.1 If invoked as a statement, only present interface signals and operations with the `void` type  
6.2.5.2.1.2 If invoked as the right hand side of an expression, only present interface operations with a type matching the left hand side  
6.2.5.3 After `<port_name>::<message_name>(<parameter_list>) to`:  
6.2.5.3.1 All local variables of type `component_ref`  

6.2.6 Interface operation invocations  
6.2.6.1 See generic section 6.2.24  

6.2.7 Select  
6.2.7.1 After `select [one|any|many] <var1>`:    
6.2.7.1.1 `related by`  
6.2.7.1.1 `from instances of`  
6.2.7.2 After `select [one|any|many] <var1> related by <var2>->` or `... -><key letters>[<rel>]->`:  
6.2.7.2 All valid relationship specifications  
6.2.7.2.1 If part of a `select one` statement, only present relationships with multiplicity "one"  
6.2.7.3 After `select [one|any|many] <var> from instances of`:  
6.2.7.3.1 All visible key letters  
6.2.7.3.1.1 If var is already typed, only propose the key letters of the type of `var`  

6.2.8 Include phrases only for reflexive associations   

6.2.9 After `generate`:  
6.2.9.1 All visible event specifications  
6.2.9.2 All local variables of type `inst<Event>`  
6.2.9.3 All attributes from local instance reference variables which have a type of `inst<Event>`   
6.2.9.4 After `<event_spec> to`:  
6.2.9.4.1 `<key letters> assigner` for assigner events  
6.2.9.4.2 `<key letters> creator` for creation events  
6.2,9,4.3 All local variables of the type that the event is defined in, and subtypes if it is a polymorphic event  

6.2.10 After `assign <var> =`:
6.2.10.1 All listed in section 6.1.26
6.2.10.2 If the variable name chosen is already defined, only elements from section 6.1.26 of the same type  

6.2.11.2 After `for each <var> in`:  
6.2.11.2.1 All local variables of an instance reference set type  
6.2.11.5 After `return`:  
6.2.11.5.1 All local variables of the same type as the body return type, or nothing when void  
6.2.11.5.2 Only for these body types:  

* Function Body
* Operation Body
* Bridge Body
* Provided Operation Body
* Required Operation Body

6.2.12 Using  
6.2.12.1 See section 6.2.13  

6.2.13 After `relate`:  
6.2.13.1 All local variables of an instance reference type  
6.2.13.2 After `relate <var> to`:  
6.2.13.2.1 All local variables of an instance reference type which have a possible relationship to the first variable  
6.2.13.3 After `relate <var1> to <var2> across`:  
6.2.13.3.1 All valid relationships between `var1` and `var2` (phrases included for reflexives, but excluded otherwise. See section [[2.1]](#2.5))  
6.2.13.4 After `relate <var1> to <var2> across <rel>.`:  
6.2.13.4.1 The relationship phrase for the relationship (or both phrases in the case of a reflexive associations)  
6.2.13.5 After `relate <var1> to <var2> across <rel> `:  
6.2.13.5.1 All local variables of an instance reference type (which have a possible associative relationship with `var1` and `var2`)
6.2.13.6 After `unrelate`:  
6.2.13.6.1 All local variables of an instance reference type  
6.2.13.7 After `unrelate <var> from`:  
6.2.13.7.1 All local variables of an instance reference type which are currently related to `var`  
6.2.13.8 After `unrelate <var1> from <var2> across`:  
6.2.13.8.1 All valid relationships in which `var1` and `var2` are both in participation (phrases included for reflexives, but excluded otherwise. See section [[2.1]](#2.5))  
6.2.13.9 After `urelate <var1> to <var2> across <rel>.`:  
6.2.13.9.1 The relationship phrase for the relationship  
6.2.13.10 After `unrelate <var1> to <var2> across <rel> `:  
6.2.13.10.1 `using` plus All local variables of an instance reference type which are currently the associative object for `var1` and `var2` across `rel`  

6.2.14 Alpha-numeric filtering, once all other filters have been applied and typing has begun further filter alpha-numerically  

6.2.15 After `self.`:  
6.2.15.1 All available attributes for the containing class  
6.2.15.2 All available instance based operations for the containing class    

6.2.16 After a selection:  
6.2.16.1 `where`  

6.2.17 param keyword  
6.2.17.1 See section 6.2.19  

6.2.18 After `cardinality`, `empty`, `not empty`, `not_empty`  
6.2.18.1 All instance reference sets  

6.2.19 Parameters
6.2.19.1 After `param.`:  
6.2.19.1.1 All available parameters  
6.2.19.1.2 If invoked as the right hand side of an expression, only present parameters with a type matching the left hand side  
6.2.19.2 After a left parenthesis before a parameter list:  
6.2.19.2.1 All available parameters  

6.2.20 After `create object instance <var> of`:  
6.2.20.1 All visible class key letters

6.2.21 After `delete object instance`:  
6.2.21.1 All local variables of an instance reference type  

6.2.22 At the beginning of a generic expression:  
6.2.22.1 All local variables of the declared type or an instance reference or structured type (if they have a member of the declared type)  
6.2.22.2 All visible constants of the declared type (scoped if not globally unique)  
6.2.22.3 All visible class key letters with at least one class based operation which returns the declared type  
6.2.22.4 All visible EE key letters with at least one bridge which returns the declared type  
6.2.22.5 All visible functions which return the declared type  
6.2.22.6 All visible port operations which return the declared type  
6.2.22.7 If the declared type is `boolean`:  
6.2.22.7.1 `not`  
6.2.22.7.2 `not_empty`  
6.2.22.7.3 `empty`  
6.2.22.7.4 `true`  
6.2.22.7.5 `false`  
6.2.22.8 If the declared type is `integer`:  
6.2.22.8.1 `cardinality`  
6.2.22.9 If the declared type is an EDT:  
6.2.22.9.1 All the enumerators for the EDT  

6.2.23 After `create event instance <var> of`:  
6.2.23.1 All visible event specifications  
6.2.23.2 After `create event instance <var> of <event> to`  
6.2.23.2.1 The class from which the event is defined  
6.2.23.2.1.1 If the event is class based but no assigned to a creation transition, append the `class` keyword  
6.2.23.2.1.2 If the event is class based and assigned to a creation transition, append the `creator` keyword  

6.2.24 After `<identifier>::`:  
6.2.24.1 All interface messages for ports matching `identifier`, all class based operations with key letters matching `identifier`, and all bridges with key letters matching `identifier` (include the full signature with parameter prototypes)  
6.2.24.1.1 If invoked as a statement, only present activities with the `void` type  
6.2.24.1.2 If invoked as the right hand side of an expression, only present activities with a type matching the left hand side  
6.2.24.2 As an expression all enumerators for EDTs matching `identifier` if the left hand side is of the EDT type  
6.2.24.3 As an expression all constants for constant specs matching `idetifier` if the constant type matches the left hand side  

### End
