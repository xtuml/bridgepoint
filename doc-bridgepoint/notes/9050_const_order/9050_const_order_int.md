---

This work is licensed under the Creative Commons CC0 License

---

# Element order is inconsistent between ME and canvas for constants 
### xtUML Project Implementation Note


### 1. Abstract

This note describes the implementation for an enhancement to constant ordering support.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9050](https://support.onefact.net/issues/9050) Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #5865](https://support.onefact.net/issues/5865) Historical customer SR    
<a id="2.3"></a>2.3 [Analysis Note](9050_const_order_ant.md)   

### 3. Background

Users have noted [2.1][2.2] that there is an inconsistency in the display of 
constant elements in a constant specification.  Model explorer appears to be 
alpha sorted while the display of the constants specification on the canvas 
appears to be listed in the order the elements were created in (the persistence 
order).   

Additionally, users request to "add functionality to sort the order of symbolic
constants."

This requested behavior will bring constants in line with other elements that
have had ordering relationships for a long time [2.3].   

### 4. Requirements

4.1  Users shall be able to re-order constants in a constant specification with
  "Move Up" and "Move Down" context menu actions.  
  
4.2  Constants in a constant specification shall be displayed on the canvas in
  the same order they are displayed in the model explorer.  
    
4.3  Constant ordering shall be persisted and maintained through tool exit and 
  restart.

### 5. Work Required

5.1 Item 1  

### 6. Implementation Comments


### 7. Unit Test


### 8. User Documentation

None.   

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__    
Branch: __9050_const_order__   

<pre>


</pre>

### End

