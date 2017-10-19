---

This work is licensed under the Creative Commons CC0 License

---

# Support open declarations    
### xtUML Project Design Note

### 1. Abstract

This note describes the changes required to support opening declarations for the selection or cursor location in an OAL editor.    

### 2. Document References
<a id="2.1"></a>2.1 [BridgePoint DEI #9761](https://support.onefact.net/issues/9761)  AE8-When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance. 
<a id="2.2"></a>2.2 [BridgePoint DEI #9762](https://support.onefact.net/issues/9762) AE9-When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.    

### 3. Background  

The OAL editor is being enhanced.  One of the things that must come with this enhancement is a way to navigate to declarations from the current cursor location in the OAL editor.  

### 4. Requirements

4.1 AE8-When a variable representing an OAL instance is selected in the editor, a CME shall be present that allows the user to find the declaration of the instance   
4.2 AE9-When a declaration is found using Find Declaration, the user shall be able to select it to navigate to the declaration.  

### 5. Analysis

5.1 Declarations     

5.1.1 Element declarations   

Element declarations include any references to the following:    

5.1.1.1 Class Key letters  
5.1.1.2 Functions    
5.1.1.3 External Entities Key letters  
5.1.1.4 Ports  
5.1.1.5 Event specifications  
5.1.1.6 Operations  
5.1.2.7 Bridges  
5.1.2.8 Interface operations  
5.1.3.9 Interface signals  

5.1.2 Local declarations  

Local declarations include local variables.  These are the variables in the OAL that have been defined within the same home.  Opening the declaration for such a variable will take you to the first definition of the variable in the same editor.

For instance, a defined local variable of any type will take the user to the location where it was first defined.

5.2 Declaration handling  

There are two approaches to handling a declaration.  The element declaration is handled by locating the specified element and opening it or showing it in Model Explorer.  The local declaration shall move the cursor to the declaration in the OAL editor.  

### 6. Design

6.1 Element determination  

The cursor location shall be used to determine what declaration is in consideration.  The start of the text shall be used to locate a V_LOC.  The start of the text shall match that of the V_LOC.  Once found there are three subtypes:

* Instance Handle  
* Instance Set  
* Transient Var  

For instance handle and instance set, open declaration shall open the Model Class element associated across R818 and R819 respectively.

For Transient Variables we shall navigate the metamodel to locate the type.  Open Declaration shall simply take the user to the initial definition within the same home.  

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

None.  

### 9. Unit Test

TODO: Consider testing  

### End
