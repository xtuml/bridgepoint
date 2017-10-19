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
<a id="2.4"></a>2.4 [Requirements Specification (SRS) for the Enhanced OAL Editor]   (https://docs.google.com/document/d/1gbqKooXBE5xBIv5bSS86pKOMKLS_W4t0GTjUfpvQvIY/edit)  
<a id="2.5"></a>2.5 [Analysis note for Enhanced OAL Editor (phase 1)](TODO)  

### 3. Background  

The OAL editor is being enhanced [2.3](2.3).  One of the things that must come with this enhancement is a way to navigate to declarations from the current cursor location in the OAL editor.  

### 4. Requirements

Requirements 4.1 and 4.2 are sourced directly from the SRS [2.4]. The other requirements here are needed to more clearly define the scope of this project. Where needed the reader will see that issues shall be raised to address tasks not covered in this phase 1 project [2.3](2.3) in the future.  

4.1 AE8-When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance   
4.1.1 In an expression, only the left-most element in the expression shall have the open declation behavior
4.1.1.1 An issue shall be raised, TODO, to consider the remaining elements in an expression  
4.1.2 When a transient variable is selected, the open behavior shall be to go to the first use of that variable in the OAL body.  

4.2 AE9-When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.  
4.2.1 Navigation to the declaration shall involve showing the user the spot of the declation in the model explorer tree.  
4.2.2 If the model explorer tree is not opened when the user selects "open declation", the user will be asked if they wish to open model explorer.
4.2.2.1 This dialog will give the user the option to never ask this question again.  

### 5. Analysis

5.1 Declarations  
Requirements AE-8 and AE-9 from the SRS [2.4](2.4) describe the ability for a user to find and open a declartion. This section will describe what this analysis considers a declaration for this project.   

5.1.1 Model Element Declaration   

In general, a Model Element declartion is an OAL artifact that refers to a structure Model Element that is not represetned in OAL. Specifically, these declarations include any references to the following:    

5.1.1.1 Class Key letters  
5.1.1.2 Functions    
5.1.1.3 External Entities Key letters  
5.1.1.4 Ports  
5.1.1.5 Event specifications  
5.1.1.6 Operations  
5.1.2.7 Bridges  
5.1.2.8 Interface operations  
5.1.3.9 Interface signals  
5.1.3.10 Parameters   (TODO: Travis, I am calling this out because it is critical. Please consider if you agree with my thinking here)  

5.1.1.1 Model Element Declaration handling  
Handling is clearly specified by the requirements under [4.2](4.2) above.  

5.1.2 Transient Declaration  
Transient declarations include local variables.  These are the variables in the OAL that have been defined within the same action body.  To the user, this means it is "local" to the action body being edited. In the ooaofooa meta-model these are identified by V_TRN. Opening the declaration for such a transient variable will take the user to the first definition of the variable in the same editor that is open during the selection.  

5.1.2.1 Transient Declaration handling  
A transient variable of any type will take the user to the location, in the currently opened action body, where it was first used.  
 

### 6. Design

6.1 Declaration determination  

The cursor location shall be used to determine what declaration is in consideration.  The selected text shall be used to locate a V_LOC.  The selected text shall match that of the V_LOC.  Once found there are three subtypes:

6.1.1 Instance Handle  (TODO: add class keyletters)
6.1.2 Instance Set  (TODO: add class keyletters)
6.1.3 Transient Var  (TODO: add class keyletters)

For instance handle and instance set, open declaration shall open the Model Class element associated across R818 and R819 respectively.  

For Transient Variables we shall navigate the metamodel to locate the type.  Open Declaration shall simply take the user to the initial usage within the same home.  

6.2 Adding Open Declaration  

6.2.1 Open Declaration  

The ui.text plugin shall have a new action added, OpenDeclarationAction.java.  The necessary plugin xml changes shall be made to add this as an editor based action.  Additionally, this action shall be tied to the CTRL + Left Mouse and F3 shortcuts.  This action class shall be given the editor instance that it is associated with.  The associated editor shall be used to determine the cursor location.  This location shall be used to determine the word.  

6.2.2 Location  

Location shall be determined by using the current cursor location (provided it is not whitespace) and backtrace until whitespace.  The location at that point shall match the V_LOC line number and start position attributes.  

6.2.2.1 Resolution  

Once the location is determined an instance of V_LOC shall be found against the activity home associated with the given editor using the location.  This V_LOC instance shall be used to determine what type of V_VAR is being handled.  

### 7. Design Comments

None.  

### 8. User Documentation

<TODO: A page must be added to the docs. Note that this is a simple markdown document, Keith can help)  

### 9. Unit Test

TODO: Consider testing  (agreed, this document is not done until this is complete)

### End
