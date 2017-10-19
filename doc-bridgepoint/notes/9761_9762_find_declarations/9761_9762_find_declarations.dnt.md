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
<a id="2.6"></a>2.6 [Test matrix for this issue](find_declarations_matrix.txt) The testing for this issue is being done with generated tests from a test matrix. This is the matrix for this work.  

### 3. Background  

The OAL editor is being enhanced [[2.3](#2.3)].  One of the things that shall come with this enhancement is a mechanism that allows the user to navigate to declarations from a selection in the OAL editor. This note describes how this mechanism will work.  

3.1 Terminology in this document  
3.1.1 Model Element Reference and Declaration  
These terms are used in this document interchangeably to refer to the following:  
In general, a Model Element declartion is an OAL artifact that refers to a structure Model Element that is not represented in OAL. Specifically, these declarations include any references to the following:    

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
When the user selects a model element reference the tool shal provide a means to navigate to that model element reference.  

4.1 (AE8) When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance   
4.1.1 In an expression, only the left-most element in the expression shall have the open declaration behavior.  
4.1.2 When a transient variable is selected, the open behavior shall be to go to the first use of that variable in the OAL body.  

4.2 (AE9) When the user selects a model element reference the tool shal provide a means to navigate to that model element reference.   

### 5. Analysis

Requirements AE-8 and AE-9 from the SRS [[2.4](#2.4)] describe the ability for a user to find and open a declartion. This section will describe what this analysis considers a declaration for this project.   

5.1 Model Element Reference handling  
5.1.1 Navigation to the declaration shall involve showing the user the spot of the declation in the model explorer tree.  
5.1.2 If the model explorer tree is not opened when the user selects "open declation", the user will be asked if they wish to open model explorer.  
5.1.2.1 This dialog will give the user the option to never ask this question again.  

5.2 Transient Declaration  
Transient declarations include local variables.  These are the variables in the OAL that have been defined within the same action body.  To the user, this means it is "local" to the action body being edited. In the ooaofooa meta-model these are identified by V_TRN. Opening the declaration for such a transient variable will take the user to the first definition of the variable in the same editor that is open during the selection.  

5.2.1 Transient Declaration handling  
A transient variable of any type will take the user to the location, in the currently opened action body, where it was first used.  
 

### 6. Design

6.1 Declaration determination  

The selection shall be used to determine what type of declaration has been selected.  The selected text shall be used to locate a V_LOC.  The selected text shall match that of the V_LOC.  Once found there are three subtypes:

6.1.1 Instance Handle (V_INT)  
6.1.2 Instance Set (V_INS)  
6.1.3 Transient Var (V_TRN)  

For instance handle and instance set, open declaration shall open the Model Class element associated across R818 and R819 respectively.  

For Transient Variables we shall navigate the metamodel to locate the type.  Open Declaration shall simply take the user to the initial usage within the same home.  

6.2 Open Declaration Context Menu Entry  

6.2.1 Open Declaration  

The ui.text plugin shall have a new action added, OpenDeclarationAction.java.  The necessary plugin xml changes shall be made to add this as an editor based action.  

This action class shall be given the editor instance that it is associated with.  The associated editor shall be used to determine the cursor location.  This location shall be used to determine the word.  

6.2.1.1 This action shall be tied to the CTRL + Left Mouse  
6.2.1.2 This action shall be tied to the F3 shortcut  

6.2.2 Location  

Location shall be determined by using the current cursor location (provided it is not whitespace) and backtrace until whitespace.  The location at that point shall match the V_LOC line number and start position attributes.  

6.2.2.1 Resolution  

Once the location is determined an instance of V_LOC shall be found against the activity home associated with the given editor using the location.  This V_LOC instance shall be used to determine what type of V_VAR is being handled.  

### 7. Design Comments

None.  

### 8. User Documentation

BridgePoint documentation shall be modifed to include this new feature.  

### 9. Unit Test

To assure proper test coverage of this feature, a test test matrix is being used to describe the required tests. This matrix is found in [[2.6](#2.6)].  

### End
