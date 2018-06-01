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
<a id="2.4"></a>2.4 [Prototype design in §5.4](https://github.com/travislondon/bridgepoint/blob/4e2b241c6b7d921db8babc347a2942380894b2e5/doc-bridgepoint/notes/4896_5005_5106_9050_4859_5035_Prototypes/4896_5005_5106_9050_4859_5035_Prototypes.md)   

### 3. Background

See analysis [2.3].   

### 4. Requirements

(Copied from [2.3] for convenience)  

4.1  Users shall be able to re-order constants in a constant specification with
  "Move Up" and "Move Down" context menu actions.  
  
4.2  Constants in a constant specification shall be displayed on the canvas in
  the same order they are displayed in the model explorer.  
    
4.3  Constant ordering shall be persisted and maintained through tool exit and 
  restart.

### 5. Work Required

5.1  The final implementation mostly followed the original design and prototype.   
5.1.1  During the prototype phase a handcraft file was put into place for the actual 
sorting of Literal Symbolic Constant classes.  The prototype design note [2.4] proposed
that the final solution could implement new data in the tree PEI data that would provide
a sorting relation chain similar to the existing naming relation chain.  

I explored this option and decided that the complexity of the work was not justified
for the benefit.  It would require:  
* Modifying the PEI schema and data for this one case
* New archetype functions in `parse_chain.inc` similar to `generate_forward_rel_chain_nav` but modified to accommodate the necessary chain traversal with proper rel phrase.  
* Modify `create_metadata_elements_sorters.arc` to look for the new sorting rel chain PEI entries and invoke the necessary parse chain operations.  

5.1.2  The implementation instead adds special handling for this one case in one file: 
`create_metadata_elements_sorters.arc`.  With the implementation, the java for the
`LiteralSymbolicConstant_cSorter.java` and the manager will be generated rather than 
committed handcraft code.  

### 6. Implementation Comments

None.  

### 7. Unit Test

7.1  The Core2 Junit test suite has generated test cases that test for the existance 
of the proper CMEs, including Move Up and Down.  These tests are extended to handle 
Literal Symbolic Constants.  

7.2  Simple manual test  
* Create an xtUML Project
* Add a package and a constant specification
* Add constant "foo"
* Add constant "baz", see it added after foo in the model explorer and on the canvas
* Add constant "bar", see it added after baz in the model explorer and on the canvas
* Right-click on "baz" and click "Move Up".  See it moved above foo in the ME and on the canvas
* Right-click on "foo" and click "Move Down".  See it moved below bar in the ME and on the canvas
* Right-click on "baz" and see that "Move Up" is not available
* Right-click on "foo" and see that "Move Down" is not available

### 8. User Documentation

None.   

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__    
Branch: __9050_const_order__   

<pre>

 doc-bridgepoint/notes/9050_const_order/9050_const_order_ant.md
 doc-bridgepoint/notes/9050_const_order/9050_const_order_int.md
 doc-bridgepoint/process/Developer Getting Started Guide.md
 src/org.xtuml.bp.core/arc/create_metadata_elements_sorters.arc
 ...ls/org.xtuml.bp.core/ooaofooa/Constants/Literal Symbolic Constant/Literal Symbolic Constant.xtuml
 ....xtuml.bp.core/ooaofooa/Functions/Context Menu Entry Functions/Context Menu Entry Functions.xtuml
 src/org.xtuml.bp.core/sql/context_menu.pei.sql
 src/org.xtuml.bp.core/sql/ooaofooa_hierarchy.pei.sql
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/.gitignore
 src/org.xtuml.bp.ui.explorer/sql/UITree.pei.sql

</pre>

Fork/Repository: __keithbrown/bptest__    
Branch: __9050_const_order__   

<pre>
/src/org.xtuml.bp.core.test/arc/create_context_menu_tests.arc
</pre>

### End

