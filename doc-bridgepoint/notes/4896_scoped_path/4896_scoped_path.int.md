---

This work is licensed under the Creative Commons CC0 License

---

# Not possible to specify datatypes with the same name at different levels.
### xtUML Project Implementation Note

### 1. Abstract

If an enumeration data type with the same name exists at the system level and inside a component, BridgePoint 
currently gives a syntax error that reads there are mulitple enumerations found. This issue is raised to provide a mechanism
that allows types with the same name to exist without this error.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #4896](https://support.onefact.net/issues/4896) Service Pro Issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #4896](https://support.onefact.net/issues/10177) BridgePoint Issue  
<a id="2.3"></a>2.3 [Design Note for this issue](4896_scoped_path.dnt.md)   
<a id="2.4"></a>2.4 [Manual test #10262](https://support.onefact.net/issues/10262) Manual test for this issue.   

### 3. Background

See the design note [[2.3]](#2.3).  

### 4. Requirements

See the design note [[2.3]](#2.3).  

### 6. Implementation Comments

6.1 During test it was observed that integrity errors were being created for duplicate constants. This 
integrity check was simply searching all constants in the current system and looking for duplicate names.
This check was removed because this is not longer an error.

6.2 After encountering the problem described by [6.1], a check was made of the 
Enumerator integrity check. Not problems were found but an obvoius performance degredation 
caused by unused code was observed and fixed by removnig the unused code.
### 7. Unit Test

7.1 Test model models/scoped_path was introduced.

7.1 A new test model has been introduced into the models reositiory. The model is named scoped_path.  
7.2 The scoped_path test model is added to the als.oal.test suite to assure that there 
are no parse errors.  
7.3 A manual test issue is created [[2.4]](#2.4), which tests additional cases that are not easily exercised
in an automated test.  

### 8. User Documentation

none

### 9. Code Changes

Fork/Repository: rmulvey/models
Branch: 4896_dataytype_path_scoping_pathspec

<pre>

scoped_path/models/scoped_path/scoped_path.xtuml
scoped_path/models/scoped_path/scoped_path/scoped_path.xtuml
scoped_path/models/scoped_path/scoped_path/Component1/Component1.xtuml
scoped_path/models/scoped_path/scoped_path/Component1/PackageForClass1/
    PackageForClass1.xtuml
scoped_path/models/scoped_path/scoped_path/Component1/PackageForClass1/Class1/
    .gitignore
scoped_path/models/scoped_path/scoped_path/Component1/PackageForClass1/Class1/
    Class1.xtuml
scoped_path/models/scoped_path/scoped_path/Datatypes/Datatypes.xtuml
scoped_path/models/scoped_path/scoped_path/Enumeration Four/
    Enumeration Four.xtuml
scoped_path/models/scoped_path/scoped_path/Enumeration Four/
    Initialization Object/Initialization Object.xtuml
scoped_path/models/scoped_path/scoped_path/Enumeration Four/
    Initialization Object/InstanceStateMachine/.gitignore
scoped_path/models/scoped_path/scoped_path/Enumeration Four/
    Initialization Object/InstanceStateMachine/InstanceStateMachine.xtuml
scoped_path/models/scoped_path/scoped_path/Enumeration Four/Object A/
    Object A.xtuml
scoped_path/models/scoped_path/scoped_path/Enumeration Four/Object A/
    InstanceStateMachine/.gitignore
scoped_path/models/scoped_path/scoped_path/Enumeration Four/Object A/
    InstanceStateMachine/InstanceStateMachine.xtuml
scoped_path/models/scoped_path/scoped_path/External Entities/
    External Entities.xtuml
scoped_path/models/scoped_path/scoped_path/Functions/Functions.xtuml
scoped_path/.project


</pre>

Fork/Repository: rmulvey/bridgepoint
Branch: 4896_dataytype_path_scoping_pathspec

<pre>

 Put the file list here 

</pre>

### End

