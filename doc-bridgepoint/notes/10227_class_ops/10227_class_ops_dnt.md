---

This work is licensed under the Creative Commons CC0 License

---

# Class-based operations in a class are not clearly distinguishable from instance-based operations
### xtUML Project Implementation Note

### 1. Abstract

Class-based operations in a class are not clearly distinguishable from
instance-based operations. They appear in the same way. In BridgePoint 6.1 they
were underlined to distinguish them form instance-based operations. This is the
UML standard notation.

### 2. Document References

<a id="2.1"></a>2.1 [Service Pro SR #4859](https://support.onefact.net/issues/4859) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #10227](https://support.onefact.net/issues/10227)  
<a id="2.3"></a>2.3 [Analysis note](../4859_class_ops/4859_class_ops_ant.md)  
<a id="2.4"></a>2.4 [UML Spec](https://www.omg.org/spec/UML)

### 3. Background

None.

### 4. Requirements

4.1 See [Analysis note for class-based operations](#2.3)  

### 5. Analysis

5.1 See [Analysis note for class-based operations](#2.3)  

### 6. Design

6.1 In UML [2.4](#2.4) section 9.4.3.2, "If a StructuralFeature is marked with isStatic = true, then the bullet points above are relative to the Classifier itself
considered as an identifiable individual within some execution scope, rather than to individual instances."  
6.2 In UML [2.4](#2.4) section 9.4.4, "Static Features are underlined."  
6.3 In eclipse help, "JDT icons" and "C/C++ icons", use a filled green circle for public methods.  
6.4 "JDT icons" shows an 's' icon for static. Both CDT and JDT impose the 's' icon as a superscript on static methods.  
6.4 An 's' icon as a superscript will be used for xtUML class-based operations for consistency.   

### 7. Design Comments


### 8. User Documentation

Describe the end user documentation that was added for this change. 


### End
