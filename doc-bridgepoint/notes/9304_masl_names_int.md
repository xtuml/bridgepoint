---

This work is licensed under the Creative Commons CC0 License

---

# Turn on preference for masl style identifiers in MASL-specific build
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes changes and fixes to make MASL model creation less error-prone.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9304](https://support.onefact.net/issues/9304) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #9313](https://support.onefact.net/issues/9313) Validate UDT names with xtext when editing MASL    
<a id="2.3"></a>2.3 [BridgePoint DEI #9290](https://support.onefact.net/issues/9290) When creating a new domain class diagram error occur for each class.   

3. Background
-------------
When creating a MASL-idiom model users really should be using MASL-style naming
by using the editor preference "Enable restricted identifier naming for model elements".  

4. Requirements
---------------
4.1 The MASL identifier preference shall default to "on"    
4.2 The rules that enforce the preference shall be relaxed to allow a single "."
  in event names.  This supports MASL-idiom polymorphic events.  
4.3 The rules that enforce the preference shall not be enforced at all for 
  user data types.     

5. Work Required
----------------
5.1 Edit the ```bp.pkg/plugin_customization.ini``` file to set the preference 
  default.   
5.2 Update the RenameAction class (a generated class).  If the MASL identifier preference is on:    
5.2.1  Check if the model element is a ```StateMachineEvent_c```.  If it is then
  modify the name string being checked to change the first ```"."``` to ```""```.  This
  supports MASL-idiom polymorphic events.    
5.2.2 Check if the model element is a ```UserDataType_c```.  If it is then skip name
  validation completely.  This supports complex MASL types in the MASL-idiom 
  xtuml.  A follow-on issue [[2.3]](#2.3) is raised to use full xtext-based validation
  on a UDT rename.     

6. Implementation Comments
--------------------------
6.1  Fixed an issue in the clean step of ```bp.core/generate.xml```.  A recently introduced 
  java file had not been put into the list of file to skip removing during clean.   

7. Unit Test
------------
7.1 Start BP on a clean workspace.  Make sure the MASL-identifier preference is on.  
7.2 With the preference on, add a state event.  Verify the name is restricted to
  MASL-style but does allow a single ".".    
7.3 With the preference on, add a UDT.  Verify that no restriction is placed on
  the name entered.    

8. User Documentation
---------------------
None.  

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint  
Branch: 9304_masl_names

<pre>

doc-bridgepoint/notes/9304_masl_names_int.md

org.xtuml.bp.core/generate.xml
org.xtuml.bp.core/arc/create_rename_action.inc

org.xtuml.bp.pkg/plugin_customization.ini

</pre>

End
---

