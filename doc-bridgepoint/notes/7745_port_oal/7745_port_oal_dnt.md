---

This work is licensed under the Creative Commons CC0 License

---

# Do not allow the interface name for accessing a port in action code.
### xtUML Project Design Note


1. Abstract
-----------
This note describes a change to the OAL parser that restricts the way ports and
interfaces are accessed in user application models.

2. Document References
----------------------
[1] [BridgePoint DEI #7745](https://support.onefact.net/redmine/issues/7745)  

3. Background
-------------
A user writes: "Today it is possible to send/access a port message 
(operation/signal) via writing the port name or the name of the interface that 
formalize the port. This is very confusing and also hard to understand if the 
component has more than one port using a specific interface. Only allow the port 
name to be used in action code."   

The xtUML development team agrees that the requested feature is valuable, but
is concerned about breaking existing models.  Therefore, the port name restriction 
will be able to be disabled via a preference.

4. Requirements
---------------
4.1  The OAL editor shall be able to show an error if an interface name is used 
  in a "send" statement     
4.2  BridgePoint shall have an Action Language preference setting "Allow 
  interface names for sending inter-component messages."   
4.2.1  The new preference shall default to false   
4.2.2  When the preference is turned off, an OAL problem marker shall be displayed
  in the editor and Problems view for lines that use interface names in send statements.   
4.2.3  When the preference is turned on, an OAL problem marker shall not be displayed
  in the editor and Problems view for lines that use interface names in send statements.       

5. Analysis
-----------
5.1 The OAL grammar BNF (```bridgepoint/src/org.xtuml.bp.als.oal/bnf/oal.bnf```) key
  point in question is the ```message_invocation``` rule.   
5.2 The OAL implementation of the port or interface name check is the meta-model at 
  ```bp.core::ooaofooa::Functions::OAL Validation Functions::Message_name_validate```.    
5.3 Example models   
5.3.1  BridgePoint includes example applications that use interface-based message sending.  These 
  models need to be updated to use port-names instead.  
5.3.2  Models to check are: GPS Watch, avpace (in BridgePoint cheatsheets), and 
  the two-component Template Project.    
5.4 Preferences   
5.4.1 BrigePoint supports setting per-project or per-workspace preferences.  The new
  action language preference proposed in this note shall be implemented as a workspace
  preference.   
5.4.2 The OAL preference page UI code is in ```bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/ActionLanguagePreferences.java```   
5.4.3 Preferences are loaded and stored via a "model" and "store".  These are ```bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferences[Model|Store].java```   

6. Design
---------
6.1  Add a preference check in the interface checking block of code inside
  ```bp.core::ooaofooa::Functions::OAL Validation Functions::Message_name_validate```.
  Indicate a parse error when the preference is set to require port names and an
  interface name is used.  
6.2  Add a new preference to the UI preference page specified in 5.4.2.   
6.3  Add new backend preference setting to the preference model and store classes
  specified in 5.4.3.   
6.4  Add a new default for the preference in ```bridgepoint/src/org.xtuml.bp.pkg/plugin_customization.ini```.   
6.5  Update the GPS Watch model to use port names.  As an initial test of the new
  preference functionality, set the preference to require port names, then parse
  the GPS Watch.  Use an OAL activity search for "send" to inspect all inter-component
  messages and verify the preference change and parse caught all the required places
  to change.   
6.6  Add a new test JUnit class to ```org.xtuml.bp.als.oal.test``` to verify the 
  functionality.   
     
| &nbsp; |                                Send uses Port Name |  Send uses Interface Name |
| ------ | -------------------------------------------------- | ------------------------- |
| Pref set to require Port names     |          Success       |     __Error__             |
| Pref set not to require Port names |          Success       |     Success               |
     
7. Design Comments
------------------
None.

8. Unit Test
------------
8.1  Example models   
8.1.1  GPS Watch shall parse all activities without error when interface names in
  messages are not allowed.   
8.1.2  avpace from the BridgePoint cheatsheets shall parse all activities without error when interface names in
  messages are not allowed.   
8.1.3  Example 2-component Template Project shall parse all activities without error when interface names in
  messages are not allowed.      
8.2  New JUnit tests that validate the parsing behavior of messages when the preference
  is on and off shall pass.   
8.3  All existing JUnit test suites shall pass.      

End
---

