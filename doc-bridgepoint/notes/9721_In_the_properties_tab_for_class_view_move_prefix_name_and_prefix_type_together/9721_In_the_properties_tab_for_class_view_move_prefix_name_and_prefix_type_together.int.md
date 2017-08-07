---

This work is licensed under the Creative Commons CC0 License

---

# In the properties tab for class view, move prefix name and prefix type together  
### xtUML Project Implementation Note

### 1. Abstract

This note describes the implementation and code changes for the issue.  

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #9721](https://support.onefact.net/issues/9721) In the properties tab for class view, move prefix name and prefix type together    
<a id="2.2"></a>2.2 [BridgePoint 9721 Analyis note](https://github.com/travislondon/bridgepoint/blob/9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together/doc-bridgepoint/notes/9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together/9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together.md) Analysis Note    
<a id="2.3"></a>2.3 [BridgePoint DEI #9565](https://support.onefact.net/issues/9565) https://support.onefact.net/issues/9565 Original move prefix issue.    

### 3. Background

See [[2.1]](#2.2).  

### 4. Requirements

See [[2.1]](#2.2).  

### 5. Work Required

5.1 Modify O_ATTR Descrip attribute so that Full Name: equals Attribute Prefix Mode.  
5.2 In BridgePointPropertySheetSorter, remove the custom ordering override added in [[2.1]](#2.3).  

### 6. Implementation Comments
None  

### 7. Unit Test

The tests described in [[2.1]](#2.2) have been implemented using j-unit.  Here are the added tests to PropertiesCustomOrderingTests.java:  
```java
testAttributeSelectedOrdering()
testAttributeParentPackageSelected()
testAttributeParentModelClassSelected()
testAttributeParentSystemSelected()
testImportedClassSelectedOrdering()
```

### 8. User Documentation

None   

### 9. Code Changes

Fork/Repository: travislondon/bptest  
Branch: 9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together    

<pre>

9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together/9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together.int.md

9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together/9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/
    Attribute.xtuml

org.xtuml.bp.ui.properties/src/org/xtuml/bp/ui/properties/
    BridgepointPropertySheetSorter.java

</pre>

Fork/Repository: travislondon/bptest  
Branch: 9721_In_the_properties_tab_for_class_view_move_prefix_name_and_prefix_type_together   

<pre>

org.xtuml.bp.test/src/org/xtuml/bp/test/common/UITestingUtilities.java

org.xtuml.bp.ui.properties.test/src/org/xtuml/bp/ui/properties/test/
PropertiesCustomOrderingTests.java

</pre>

### End

