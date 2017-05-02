---

This work is licensed under the Creative Commons CC0 License

---

# Use create for WFL instead of SQL  
### xtUML Project Implementation Note

### 1. Abstract
Stop using the outdated idiom of generating instance data as SQL
and then importing them.  Create them.

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #8547](https://support.onefact.net/issues/8547) Use create for WFL instead of SQL  

### 3. Background
Currently, the workflow subsystem is generated as a population of
preexisting instances which are then (re)imported in a later step.
This can be accomplished using RSL `create object instance`.


### 4. Requirements
4.1 Item 1  
4.1.1 Example sub-item
* Example List Element
  * Example Sub list item

4.2 Item 2  
4.2.1 Example sub-item
* Example List Element

### 5. Work Required
5.1 Item 1  
5.1.1 Example sub-item
* Example List Element

5.2 Item 2  
5.2.1 Example sub-item
* Example List Element

### 6. Implementation Comments

### 7. Unit Test
7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

### 8. User Documentation

### 9. Code Changes

<pre>
Fork/Repository: cortlandstarrett/bridgepoint  
Branch: 8547_wfl  


</pre>

End
---

