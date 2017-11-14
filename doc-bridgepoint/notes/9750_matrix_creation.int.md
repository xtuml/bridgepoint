---

This work is licensed under the Creative Commons CC0 License

---

# OAL automatic completion  
### xtUML Project Implementation Note

### 1. Abstract

This note describes the creation of a test matrix for OAL automatic completion.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9750](https://support.onefact.net/issues/9750) Develop test matrix for oal autocompletion.  
<a id="2.2"></a>2.2 [BridgePoint DEI #9749](https://support.onefact.net/issues/9749) Determine use cases for OAL autocompletion.  
<a id="2.3"></a>2.3 [BridgePoint DEI #9749](https://support.onefact.net/issues/9751) Using test matrix implement tests for oal context sensitive help.  

### 3. Background

An enhanced OAL editor is requested by customers, of which one enhancement is OAL automatic completion.  Testing is of course required for such an addition.    

### 4. Requirements

4.1 Develop a testing matrix for OAL automatic completion  

### 5. Work Required

5.1 Degrees of freedom    
5.1.1 Locations  

Locations are the positional value in the location of a statement.  These are all set out in the use case document [2.2].  The use case document lists the location with all possible proposals of each.  This is defined in section 6 of the use case document [2.2].  This matrix degree of freedom has each implemented under Locations (L).  

5.1.2 Possibilities  

Possibilities are the possible proposals shown to the user when using automatic completion.  As stated in section 5.1.1, these are listed after each location in section 6 of the analysis note [2.2].  The possibilities are handled under the Possibilities degree of freedom (P).  

5.1.3 Activity homes  

There are ten different places where OAL may be written, all of which have different rules and therefore must be tested.  These activity homes are described in the analysis note [2.2].  A new degree of freedom is added, Activy_Homes(AH), to handle these homes.  

5.1.4 Scope  

Scoping is described in the analysis section [2.2].  For testing the proposal shall be tested to assure inaccessible values are not shown in the proposal list.  To handle these cases a new degree of freedom is add for Scope(S).  This degree of freedom has three options:  

* Before  
* After  
* Within  

5.1.5 Visibility  

Visibility in this case is related to external references within OAL.  One example would be a function invocation.  An element that is not visible to the action home shall not be listed in the proposals.  Degree of freedom Visibility(V) is created for this.  It only has two items, Visible and Non-Visible.  

5.2 Results  

The matrix only needs to worry about existing in the proposal list and not existing in the list.  The following results are created:  

* doesExist  "An expected element was not present in the auto-complete list"  
* non-existent  "An element was found in the auto-complete list when it should not have been present"  

5.3 Matrix  

The matrix is created with columns using the Scope(S) and Visibility(V) degrees of freedom.  The rows are created with the Locations(L), Possibilities(P) and Action_Homes(AH) degrees of freedom.  For each cell the result is either a R1, R2 or X.  The determination of the results was done by consideration of the analysi note from reference 2.2.  Those tests using an X are imposibilies.  Such an example is a local variable reference in OAL and the Visibility(V) being Non-Visible.  

### 6. Implementation Comments

None.  

### 7. Unit Test

Determined in reference [2.3].  

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: travislondon/bridgepoint
Branch: 9750_matrix_creation_2  

<pre>

doc-bridgepoint/notes/9750_matrix_creation.int.md

</pre>

Fork/Repository: travislondon/bptest
Branch: 9750_matrix_creation  

<pre>

org.xtuml.bp.als.oal.test/matrices/non-generated/oal_autocomplete_matrix.txt

</pre>

### End

