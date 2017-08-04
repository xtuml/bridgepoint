---

This work is licensed under the Creative Commons CC0 License

---

# Move of prefix mode in properties is incomplete
### xtUML Project Analysis Note


### 1. Abstract

This note describes the analysis for allowing the properties view to be sorted for Attributes, specifically the prefix mode attribute of the O_ATTR class, even when a parent is selected.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9565](https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/9565_%20Move_Prefix_name_and_Prefix_type/9565_%20Move_Prefix_name_and_Prefix_type.md) Move_Prefix_name_and_Prefix_type design note.  
<a id="2.2"></a>2.2 [BridgePoint DEI #9721](https://support.onefact.net/issues/9721) Move of prefix mode in properties is incomplete    
<a id="2.3"></a>2.3 SRS-SAABSpring2017Issues-9501-v1.3   

### 3. Background

This issue was raised due to the fact that attributes cannot be ordered if a parent is selected.  The original requirements only specified selecting an attribute.

### 4. Requirements

4.1 Order shall match the following even if a parent of the Attribute class is selected.

Array Dimensions
Attribute Name
Attribute Name Prefix
Attribute Prefix Mode
Attribute Root Name   

### 5. Analysis

There is no determined solution for the issue as of yet, we just do not have the right information available.  Currently it looks at selection. The selection must be an Attribute.    

We get a list of property sheet entries which only have “Display Name” to check against.  This can allow us to check the count of property sheet entries or the display name to determine if the attribute sorter should do something.  The "Display Name" property can match other entries, which will cause the attribute sorter to break the order.  Consider "Description" as the display name.  A good number of elements have that attribute and "Display Name" property.   

There is another approach: 5.1.1 or 5.1.2.2 of [[2.1]](#2.1).  That way allows us to just control the O_ATTR attributes.    

### 6. Work Required

6.1 Pending...  

### 7. Acceptance Test

7.1 For an Attribute open the properties view with the parent class selected  
7.1.1 Result ordering shall match that stated in the Requirement section
7.2 Run properties tests
7.2.1 Result is there are no failures

### End
