---

This work is licensed under the Creative Commons CC0 License

---

# Place Prefix Mode closer to Attribute Prefix Name in properties view  
### xtUML Project Design Note

### 1. Abstract

Currently the properties view shows the Prefix Mode for an attribute four entries below the Attribute Name Prefix entry.  From a usability standpoint this is not good as the user must navigate to the location the extra distance when configuring the Attribute name.  This is four key presses before configuring the Prefix Mode.    


### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9565](https://support.onefact.net/issues/9565) Saab- 10 : Move Prefix name and Prefix type  

### 3. Background

The BridgePoint tool has areas that require usability enhancements to allow engineers to be more efficient.   

### 4. Requirements

4.1 Order Attribute property entries more efficiently  
4.1.1 Order shall match  
- Array Dimensions  
- Attribute Name  
- Attribute Name Prefix  
- Attribute Prefix Mode   
- Attribute Root Name  

### 5. Analysis

5.1 Place Prefix Mode entry under Attribute Name Prefix  

Currently Eclipse alphanumerically orders property sheet entries.  This is the reason for the current location of Prefix Mode entry.  There are a few approaches that can be taken here.  

5.1.1 Configure UI display name for Pfx_Mode to be Attribute Prefix Mode, allowing the current alphanumeric ordering to achieve the necessary ordering   
5.1.2 Overriding Eclipse's ordering behavior  
5.1.2.1 Leave ordering according to the attribute ordering in the metamodel  
5.1.2.2 Adjust metamodel attribute ordering as needed to properly place the Prefix Mode  
5.1.3 Provide a custom sorter for any class that requires one, defaulting to the alphanumeric ordering if one is not present  

Here are examples of the properties view for each:  

Using 5.1.1  
- Array Dimensions  
- Attribute Name  
- Attribute Name Prefix  
- Attribute Prefix Mode  
- Attribute Root Name  

Using 5.1.2.1  
- Array Dimensions  
- Attribute Name  
- Attribute Name Prefix  
- Attribute Root Name  
- Attribute Prefix Mode  

Using 5.1.2.2  
Same as 5.1.1  

Using 5.1.3  
Full control over ordering so any order is possible  

5.1.1 is going to be the easiest with the least disruption.  

Anything in 5.1.2 is easy but may change the properties view ordering for any elements which have a property sheet.  For 5.1.2.2 this will require upgrade code, modified test results and likely more so it is not suggested.  

5.1.3 is more work but will allow for customization for any other element simply by adding its own sorter.  

5.1.3 is suggested even though it is a bit more work now.  The reason is that Prefix Mode is likely not going to be the only case we run into.  

### 6. Design

The choice was made to go with 5.1.3.  This leaves the tool open for other custom sorters.  

6.1 Introduce custom sorting for properties view  
6.1.1 Override sort method in BridgePointPropertySheetSorter, asks the new class in 6.1.1.1 for a sorter  
6.1.1.1 Introduce a new class, BridgePointPropertySheetSorters.  This class stores custom sorters in a static map and returns a sorter if present to the BridgePointPropertySheetSorter.  Later additions will add their class to the static map to use custom sorting.  Note at first an attempt was made to access the appropriate class from the data given to the class.  This was not possible so the current core selection is used.  The properties view relies on this selection to display any data it is therefore guaranteed the selection will be the owner for the properties entries to be sorted.  
6.2 Introduce sorter for Attribute class, AttributeSorter.  
6.2.1 A static map is used to predefine order of the display names for properties entries  
6.2.2 If an entry is not found in this static map then the next count value (of the iteration over property entries) is used.  This would be the case where a new attribute is added.  
 

### 7. Design Comments


### 8. User Documentation


### 9. Unit Test

9.1 For an Attribute open the properties view with the element selected      
9.1.1 Result ordering shall match that stated in the Requirement section  
9.2 Run properties tests  
9.2.1 Result is there are no failures  

### End
