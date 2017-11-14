---

This work is licensed under the Creative Commons CC0 License

---

# Introduce a mechanism that allows OAL Editor users to navigate to declarations    
### xtUML Project Design Note

### 1. Abstract

This note describes the changes required to support opening declarations for the selection or cursor location in an OAL editor.    

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #9761](https://support.onefact.net/issues/9761)  AE8-When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance.   
<a id="2.2"></a>2.2 [BridgePoint DEI #9762](https://support.onefact.net/issues/9762) AE9-When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.    
<a id="2.3"></a>2.3 [BridgePoint DEI #9571](https://support.onefact.net/issues/9571) Enhanced OAL Editor (phase 1).  
<a id="2.4"></a>2.4 [9571 SRS](https://docs.google.com/document/d/1gbqKooXBE5xBIv5bSS86pKOMKLS_W4t0GTjUfpvQvIY/edit) Requirements specification for the Enhanced OAL Editor project  
<a id="2.5"></a>2.5 [9571 Analysis ](../9571_oal_xtext_editor/9571_oal_xtext_editor_option2_ant.md) Analysis note for Enhanced OAL Editor (phase 1)  
<a id="2.6"></a>2.6 9761_9762_find_declarations_matrix.txt - The testing for this issue is being done with generated tests from a test matrix. This is the matrix for this work.  

### 3. Background  

The OAL editor is being enhanced [[2.3](#2.3)].  One of the things that shall come with this enhancement is a mechanism that allows the user to navigate to declarations from a selection in the OAL editor. This note describes how this mechanism will work.  

3.1 Terminology in this document  
3.1.1 Model Element References and Model Element Declarations  
In general, a Model Element Refererence is an OAL artifact that refers to a structural model element that is not represented in OAL. The item that is referred to is the declaration. For the purpose of this work, Model Element references include the following:    

3.1.1.1 Class Key letters  
3.1.1.2 Functions    
3.1.1.3 External Entities Key letters  
3.1.1.4 Ports  
3.1.1.5 Event specifications  
3.1.1.6 Operations  
3.1.2.7 Bridges  
3.1.2.8 Interface operations  
3.1.3.9 Interface signals  
3.1.3.10 Parameters  

### 4. Requirements

Requirements 4.1 and 4.2 below are sourced directly from the SRS [[2.4](#2.4)]. The other requirements below have been added to more clearly define the scope of this project.  

Note that requirement AE9 called out in the [SRS](#2.4) has been found to be confusing to the readers of this design. It does not seem to clearly describe what is suppose to happen. While the writer of this document does not want to change the requirement, it is being modified here in 4.2 below to keep the spirit of the requirement, but clarify it:  
orginal:  
When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.  

updated:  
When the user selects a model element reference the tool shall provide a means to navigate to that model element declaration.  

4.1 (AE8) When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance   
4.1.1 In an expression, only the left-most element in the expression shall have the find declaration behavior.  
4.1.2 When a transient variable is selected, the open behavior shall be to go to the first use of that variable in the OAL body.  

4.2 (AE9) When the user selects a model element reference the tool shall provide a means to navigate to that model element declaration.   

### 5. Analysis

Requirements AE-8 and AE-9 from the SRS [[2.4](#2.4)] describe the ability for a user to find and open a declaration. This section will describe what this process.   

5.1 Model Element Reference handling  
5.1.1 Navigation to the declaration shall involve showing the user the spot of the declaration in the model explorer tree and on the canvas.  
5.1.1.1 If the model explorer tree is not opened when the user selects "find declaration", it shall be opened.  
5.1.1.2 If the canvas is not opened when the user selects "find declaration", it shall be opened. 

5.2 Transient Declaration  
Transient declarations include local variables.  These are the variables in the OAL that have been defined within the same action body.  To the user, this means it is "local" to the action body being edited. In the ooaofooa meta-model these are identified by V_VAR. Opening the declaration for such a transient variable will take the user to the first definition of the variable in the same editor that is open during the selection.  

5.2.1 Transient Declaration handling  
A transient variable of any type will take the user to the location, in the currently opened action body, where it was first used.  
 

### 6. Design

6.1 Determination of item to consider using location  

Location shall be determined using current cursor position.  From the beginning of the current string, the location shall match the V_LOC line number and start position attributes. A declaration may be specified on the left hand side of an expression, the right-hand side, in a select statement, in and where clause, all such selections shall be supported.  

6.1.1 Model Element declaration  
The selection shall be used to determine what type of declaration has been matched.  The selected text shall be used to locate an ACT_SMT.  The selected line number and position shall match that of the ACT_SMT.  The following model element types shall be supported by this feature:  

6.1.1.1 Class Key letters (ACT_SEL, ACT_FIO, ACT_FIW)  
6.1.1.2 Functions (ACT_FNC)    
6.1.1.3 External Entities Key letters  
6.1.1.4 Ports  
6.1.1.5 Event specifications  
6.1.1.6 Operations (ACT_TFM)  
6.1.1.7 Bridges (ACT_BRG)  
6.1.1.8 Interface operations(ACT_IOP)  
6.1.1.9 Interface signals (ACT_SGN)  
6.1.1.10 Parameters  
6.1.1.11 Association numbers (ACT_SEL, ACT_FIO, ACT_FIW)


To search for the declaration of a non-transient variable, one of the above shall be satisfied.  The full statement subtype shall be commpletely parsed.  Once this parse is complete the tool shall navigate the Body SS, to locate the element under the cursor.  No declaration shall be opened if the location of the cursor does not match one of the specified elements above.  No declaration shall be opened if the body element has never been parsed.  

6.1.2 Transient Declaration  
Find Declaration shall take the user to the initial usage within the same home. To do this, we shall navigate the metamodel to locate the type.  

6.1.3 Most of the logic to support open declaration shall be written as new operations to each of the Statement subtypes.  One operation in the supertype Statement class shall have a switch, calling each subtype's new opertion.  The subtype operations shall traverse to the parsed instances created by the last parse which store positional values.  An example here is ACT_FIO.extentLineNumber and ACT_FIO.extentColumn.  All subtypes have attributes which capture this information.  Java classes shall be used where necessary to fit into the Eclipse UI.  

6.1.3.1 Logic flow  

1. User positions cursor in action body
2. User selects Open Declaration (or F3)
3. Eclipse UI entry point is called
4. Determinaition of selection is made
 if (is transient)
   lookup in actionbody
   position cursor at first use
 elif (is model element reference)  
   // here we use existing parse data, if none there is no action performed  
   select many statements related by actionBody->ACT_BLK[R601]->ACT_STMT[R602]  
   for each statement in statements  
     select one sfi_subtypw related by statement->ACT_SFI[R603]
     if(not_empty sfi_subtype)
       // using extentLineNumber and extentColumn
       if(extentLineNumber == openDeclarationLineNumber && extentColumn == openDeclarationColumn)
         select one modelClass related by sfi_subtype->O_OBJ[R677]
         return modelClass
     elif
      // repeat proposals in [6.1.1.1] - [6.1.1.1.11]  
     end if
   end if
 else
   // No action, invalid selection
 end if

6.2 Transient resolution  

Once the location is determined an instance of V_LOC shall be found against the activity home associated with the given editor using the location.  This V_LOC instance shall be used to find the first instance of the associated V_VAR.  

6.3 Open Declaration context menu entry  

The ui.text plugin shall have a new action added, OpenDeclarationAction.java.  The necessary plugin xml changes shall be made to add this as an editor based action.  

This action class shall be given the editor instance that it is associated with.  The associated editor shall be used to determine the cursor location.  This location shall be used to match against stored location values in the V_LOC class.  

6.3.1 This new CME shall be in the Eclipse menu in the section with the other BridgePoint CMEs
6.3.2 This action shall be tied to the F3 shortcut  

### 7. Design Comments

None.  

### 8. User Documentation

BridgePoint documentation shall be modifed to include this new feature.  

### 9. Unit Test

To assure proper test coverage of this feature, a test test matrix is being used to describe the required tests [[2.6](#2.6)]. 
This matrix is found in the org.xtuml.bp.ui.text.test plug-in under 
matrices/non_generated as 9761_9762_find_declarations_matrix.txt.  

### End
