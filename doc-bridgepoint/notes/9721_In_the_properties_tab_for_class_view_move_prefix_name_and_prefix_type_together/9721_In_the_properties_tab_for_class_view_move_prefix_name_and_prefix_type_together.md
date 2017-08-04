---

This work is licensed under the Creative Commons CC0 License

---

# In the properties tab for class view, move prefix name and prefix type together
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

- Array Dimensions  
- Attribute Name  
- Attribute Name Prefix  
- Prefix Mode   
- Attribute Root Name  

### 5. Analysis
For this issue we just do not have the right information available.  The work done for [[2.1]](2.1) looks at the current model selection to enable a specific sorter for the given property entries. The selection in this case must be an Attribute.    

We get a list of property sheet entries which only have “Display Name” to check against.  This can allow us to check the count of property sheet entries or the display name to determine if the attribute sorter should do something.  The "Display Name" property can match other entries, which will cause the attribute sorter to break the order.  Consider "Description" as the display name.  A good number of elements have that attribute and "Display Name" property.  The count is likely not a good check either as many other ooaofooa classes may equal that count.     

5.1 Options

5.1.1 Two approaches that we could use are detailed in the initial issues analysis.  They are sections 5.1.1 and 5.1.2.2 of [[2.1]](#2.1).  Those approaches allow us to just control the O_ATTR attributes.  In the 5.1.1 section we simply adjust the Pfx_Mode UI attribute name which allows the alpha-numeric sorter to put the entry in the correct place.  In the 5.1.2.2 section we would actually reorder the meta-model attribute for Pfx_Mode and not use the alpha-numeric ordering.      
5.1.2 We could determine a way to take control of property sheet entry creation.  If we can take over creation of the IPropertySheetEntry classes we can simply store the root element for the entries.  This will let us check for an Attribute_c root and use the sorter appropriately.       
5.1.3 Each individual "Display Name" value could be considered, and if they match we could just initiate the attribute entry sorter always.  This needs a good amount of consideration put to the effect on performance.  

5.2 Suggestion  

After analysis it is believed that option [[5.1.2]](#5.1.2) would be the best option.  The reason is that the initial design intent was to allow for many different sorters in a general way.  Option [[5.1.3]](#5.1.3) has some performance considerations and option [[5.1.1]](#5.1.1) will only address attributes.  

### 6. Work Required

6.1 Pending...  

### 7. Acceptance Test

7.1 For each of the following selections (paths to model elements):  

* System  
* System::Package  
* System::Package::ModelClass  
* System::Package::ModelClass::Attribute  
* System::Package::ImportedClass

7.1.1 Result ordering for the attribute shall match that stated in the Requirement section    
7.2 Run properties tests  
7.2.1 Result is there are no failures  


### End
