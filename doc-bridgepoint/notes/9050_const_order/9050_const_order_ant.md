---

This work is licensed under the Creative Commons CC0 License

---

# Element order is inconsistent between ME and canvas for constants 
### xtUML Project Analysis Note


### 1. Abstract

This note describes the analysis for an enhancement to constant ordering support.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9050](https://support.onefact.net/issues/9050) Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #5865](https://support.onefact.net/issues/5865) Historical customer SR    

### 3. Background

Users have noted [2.1][2.2] that there is an inconsistency in the display of 
constant elements in a constant specification.  Model explorer appears to be 
alpha sorted while the display of the constants specification on the canvas 
appears to be listed in the order the elements were created in (the persistence 
order).   

Additionally, users request to "add functionality to sort the order of symbolic
constants."

### 4. Requirements

4.1  Users shall be able to re-order constants in a constant specification with
  "Move Up" and "Move Down" context menu actions.  
  
4.2  Constants in a constant specification shall be displayed on the canvas in
  the same order they are displayed in the model explorer.  
    
### 5. Analysis

5.1  `Symbolic Constant` already has some useful bits in the metamodel.  
5.1.1  It has the reflexive ordering relationship R1505.   
5.1.2  It has operations `moveDown()` and `moveUp()` with implementations that 
  manipulate R1505.  
5.1.3  It has the `actionFilter()` operation with support for moving up and down.  
   
5.2  The "Move Down" and "Move Up" CMEs are not displayed in the popup context
  menu when activated on constants in a Constant Specification.  This must be
  fixed.  
  
5.3  For comparison, when class attributes are created in a class they are shown
  in Model Explorer and on the canvas in creation (initial persistence) order. They
  are not shown alpha-sorted in Model Explorer like constants are.   
5.3.1  Class attributes use the reflexive ordering relationship R103.   

5.4  `bp.core.sorter` contains sort classes for various sortable elements    
5.4.1  Sorter classes are generated from `create_metadata_elements_sorters.arc`  
5.4.2  `bp.ui.explorer.adapters.ClassesAdapter.java` (generated) uses the 
  `Attribute_cSorter` class in it's `getChildren()` operation.   
5.4.3  `bp.ui.explorer.adapters.ConstantSPecificationsAdapter.java` (generated) 
  does not use any sorter in it's `getChildren()` operation.  It just does
  a blind select of all Symbolic Constants related to the Constant Specification.

### 6. Work Required

6.1  Enable Move Up/Down on Symbolic Constants   

6.2  Add a Symbolic Constant sorter   

### 7. Acceptance Test

7.1 Use the Move Up/Down action to reorder constants in a constant specification
  in model explorer.  Add and delete constants from a constant specification.  
* See the elements move accordingly in Model explorer and the canvas.  
* Canvas display always reflects the order in model explorer.   

### End
