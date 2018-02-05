---

This work is licensed under the Creative Commons CC0 License

---

# Not possible to specify datatypes with the same name at different levels.
### xtUML Project Design Note


### 1. Abstract

If an enumeration data type with the same name exists at the system level and inside a component, BridgePoint 
currently gives a syntax error that reads there are mulitple enumerations found. This issue is raised to provide a mechanism
that allows types with the same name to exist without this error.

#
### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #4896](https://support.onefact.net/issues/4896)   
<a id="2.2"></a>2.2 [SRS For Project Sortie-1](https://docs.google.com/document/d/124tp5O8PvCHCDZUDLX173c0B8u4N9d7CXEF-X8Voszw/edit#heading=h.yi778ev1y742) 
This is a One Fact internal link to the SRS that defines the requirements for this project.    
<a id="2.3"></a>2.3 [Parser enumerator binding policy incorrect](https://support.onefact.net/issues/1143) 
This is an old BridgePoint issue that looked into this issue at a very high-level a long time ago. No action was taken, but a
suggesting for implementation of a fix was made.  
<a id="2.4"></a>2.4 [Test Model For reproduction](https://github.com/rmulvey/models/tree/4896_scoped_enum) An existing test 
model, enum4, from the xtuml/models test repository was modified to reproducd this problem.  
<a id="2.5"></a>2.5 [Analysis for xtUML Revised Type System](
https://github.com/xtuml/bridgepoint/blob/1c1fc116e770f70a44aac7e73e0bcc2de00c66c6/doc-bridgepoint/notes/9416_type_system/9416_type_system_ant.md) This is an analysis that speaks to recent discussion about type system improvements.  
<a id="2.6"></a>2.1 [BridgePoint DEI #8061 Implementation note](https://github.com/xtuml/bridgepoint/blob/e47b13b9cb1004459f586b5bb1eb174df33b168f/doc-bridgepoint/notes/8061_ipr_classes.md) Allow Imported Classes via IPR (Phase 1)  



### 3. Background

A user observed that if a duplicate named Enumeration Data Type is defined both at the system level and inside a 
component, attempted usage of the data type results in a syntax error of the form:

```
Multiple enumerations found for ->color<-: enum4::enum4::Enumeration Four::ScopedEnum::ScopedEnum::color ,enum4::enum4::Datatypes::color	Object_A__one.oal_err	/enum4/models/enum4/enum4/Enumeration Four/ScopedEnum/ScopedEnum/Object A/InstanceStateMachine	line 1	Problem
```

![4896_error](4896_error.png)  

This issue is raised to investigate and provide a resolution that allows duplicate-named types to exist in the model.  

## 4. Requirements

The requirements are sourced from [[2.4]](#2.4).  
4.1 A BridgePoint model shall allow duplicate-named enumeration data types to exist in separate packages in a model.  
4.2 A BridgePoint model shall allow duplicate-named constant data types to exist in separate packages in a model.  
4.3 A BridgePoint model shall allow duplicate-named structured data types to exist in separate packages in a model.  
4.4 A BridgePoint model shall allow duplicate-named user data types to exist in separate packages in a model.  Note 
that this is already allowed in the tool today. However, it is simply added here for completeness.  
4.5 The mechanism introduced to allow duplicate types to exist shall allow for backwards compatibility of existing OAL.  

### 5. Analysis

Issue [[2.3] Comment 1](https://support.onefact.net/issues/1143#note-1) describes that this change requires a significant change to the parser validation implementation "for enumerators": "At the level it is currently being performed, there is too little scope to validate correctly. It needs to be performed at a much higher level, where the complete assignment statement syntax tree is accessible. One suggestion might be in data_types_compatible()".  

Recent discussion of type system improvements led to analysis that also speaks to possible updates in ooaofooa::Functions::OAL Validation Utility Functions::data_types_compatible() [[2.5]](#2.5).

Since that observation, some work in the area of package scoping was done. However, its implementation may not have been as anticipated [[2.6]](2.6). The parser was not updated to account for duplicates in this issue.  Issue [[2.6]](2.6) introduced the ability to [give visibility to a package that is outside the current component](https://www.youtube.com/watch?v=1qWhGdJMTSg), it did not change the parser.  


### 6. Design

TODO  

### 7. Design Comments

None  

### 8. User Documentation

The existing documenation shall be modified to update places that describe this behavior as an error and to describe the 
new behavior.  

### 9. Unit Test

9.1 Assure that duplicate-named enumeration data types may exist in separate packages in a model.  
9.2 Assure that duplicate-named constant data types may exist in separate packages in a model.  
9.3 Assure that duplicate-named structured data types may exist in separate packages in a model.  
9.4 Assure that duplicate-named user data types may exist in separate packages in a model.  Note 
that this is already allowed in the tool today. However, it is simply added here for completeness.  
9.5 Test backwards compatibility to assure that existing models do not have parse errors unless duplicates are present.  

### End
