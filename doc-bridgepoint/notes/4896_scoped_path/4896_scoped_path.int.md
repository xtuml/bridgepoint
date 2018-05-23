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

This new test model is used to test cases where parse errors should NOT occur as well as when they should occur.  

### 8. User Documentation

TODO 

### 9. Code Changes

Fork/Repository: rmulvey/models
Branch: 4896_dataytype_path_scoping_pathspec

<pre>

 Put the file list here 

</pre>

Fork/Repository: rmulvey/bridgepoint
Branch: 4896_dataytype_path_scoping_pathspec

<pre>

 Put the file list here 

</pre>

### End

