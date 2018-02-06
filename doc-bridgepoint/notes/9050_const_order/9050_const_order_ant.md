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
<a id="2.3"></a>2.3 [Original Analysis Note for Adding Ordering Associations](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20080606/i2932.ant)   
<a id="2.4"></a>2.4 [Original Design Note for Adding Ordering Associations](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20080606/i2932.dnt)   

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

5.5  There are several metamodel classes that compose a constant.  The Model 
  Explorer tree is actually showing the `Literal Symbolic Constant` elements 
  under the `Constant Specification` even though the element `Symbolic Constant`
  is the direct child in the metamodel. It appears this is causing confusion to
  the display and sorting.   
  
5.6  Review [2.3] and [2.4] to verify the constant ordering follows the same 
  implementation as other ordered elements.  
  
### 6. Work Required

6.1  Enable Move Up/Down on Symbolic Constants   

6.2  Add a constant sorter   

6.3  Ensure support in model import for ordered constants

### 7. Acceptance Test

7.1 Use the Move Up/Down action to reorder constants in a constant specification
  in model explorer.  Add and delete constants from a constant specification.  
* __R__ See the elements move accordingly in Model explorer and the canvas.  
* __R__ Canvas display always reflects the order in model explorer.   
* Restart BP
* __R__ See the ME and Canvas ordering reflects the same ordering before restarting  

### End
