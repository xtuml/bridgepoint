---

This work is licensed under the Creative Commons CC0 License

---

# Enhanced association editing  
### xtUML Project Analysis Note

### 1. Abstract

This note analyzes some approaches to which we could enhance editing support in the tool for associations.  

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #9567](https://support.onefact.net/issues/9567) Cardinality on right click menu  

### 3. Background

3.1  

Modifying association related data currently requires selecting the association, leaving the diagram editor, and navigating the properties view.  This is inconvenient and has potential of losing the selection along the way having the need to return to the diagram editor.  Along with its inconvenience it is also not as easy to understand the properties view as it is raw metamodel data.  To the user an association should be what is seen on the diagram editor:  

Class On One Side, Multiplicity, Conditionality, Text Phrase  
Class On Other Side, Multiplicity, Conditionality, Text Phrase  

### 4. Requirements

4.1 User shall be able to modify an association without the properties view  
4.1.1 User shall not have to leave the diagram editor  
4.1.2 Association data being modified shall be no more difficult to understand then as shown on the diagram  

### 5. Analysis

5.1 Modification without the properties view  
5.1.1 Modification without leaving diagram editor  
5.1.1.1 In-place editing  

Given the requirement to not leave the diagram editor in-place editing is a strong candidate.  The main benefit of in-place editing is that you are modifying the data that you are looking at.  If you understand the association, you then understand what you are modifying.  For this issue in-place editing shall be enabled for Multiplicity/Conditionality, Text Phrases and Assocation Number.  A validation of text entered for Multiplicity/Conditionality shall ensure that only the following is allowed:  

0..1  
1  
1..*  
*  

For text entered on an association number validation must guarantee an integer.  And for Text Phrase there shall be no validation.  An error tip shall be shown indicating when entered values are not acceptable.  This may be a pop-up near the editing area, which does not interfere with the user modification.  It also may be Error text in the Eclipse notification area near the bottom of the tool.  

Of course editing via in-place editing shall be treated just as editing is elsewhere.  Meaning changes are persisted, undoable etc,.  

5.1.1.2 Editing tab  

Another possibility which does not leave the diagram editor is to add a new Association tab to the diagram editor.  This tab shall show association data in a simple manner that does not veer far from the diagram editor.  A good example of what could be shown is in [[3.1]](#3.1).  This tab shall dynamically show when an association is selected.  The selection shall contain only one association.  Editing shall support Multiplicity, Conditionality, Text Phrase and Association Number.  The Multiplicity/Conditionality shall be modifiable via a drop down menu.  Text Phrase shall be an unverified modifiable text field.  Association Number shall require validation of an integer value and as in [[5.1.1.1]](#5.1.1.1) notification of invalid data shall be given.  

5.1.2 Context menu item to modify Multiplicity  

The original issue [[2.1]](#2.1) requests that a new menu item shall be added for adjusting Multiplicity.  This adheres to the requirements but does not fully cover modifying the association.  Such a dialog shall display two pull down menus allowing configuration of Multiplicity, with options of One or Many.  Each pull down menu shall have a label associated with the side of the associaton being modified.  In most cases this shall be the Class name at that end of the association.  In the case of a reflexive association the label shall indicate the referring Class (if formalized) by appending the Referred To Identifier Attribute text.  For example the label in a reflexive association could be: WorkoutSession {startDate, startTime}.  As with the other two approaches the change shall be handle just as any other editing action in the tool.  

5.3 Suggestions on moving forward  

[[5.1.2]](#5.1.2) seems like only a partial solution and would require the addition of two new menu items to complete Association modification.  This would be Set Text Phrases, and Set Conditionality.  

[[5.1.1.1]](#5.1.1.1) and [[5.1.1.2]](#5.1.1.2) both seem like good choices and actually could compliment each other.  Either will fulfill the requirements.  The suggestion for this analysis is to proceed with 5.1.1.2 simply because it allows one to take focus off of surrounding elements while editing the association.

### 6. Work Required

Will complete once a decision is made.  

### 7. Acceptance Test

7.1 Associations shall be modifiable without the properties view  
7.2 Association modification shall trigger persistence  
7.3 Association modification shall support undo/redo  
7.4 Association modification shall trigger refreshes within the UI  

### End
