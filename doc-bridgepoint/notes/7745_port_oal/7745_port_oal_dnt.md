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

The xtUML development team has agrees that the requested feature is valuable, but
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
  ```bp.core::ooaofooa::Functions::OAL Validation Functions::Message_name_valid```. We will  
  add a preference check in here in the interface checking block.
5.3 The example GPS Watch application uses interface-based message sending.  This 
  model needs to be updated to use port-names instead.
5.4 Preferences   
5.4.1 BrigePoint supports setting per-project or per-workspace preferences.  The new
  action language preference proposed in this note shall be implemented as a workspace
  preference.   
5.4.2 The OAL preference page UI code is in ```bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/preferences/ActionLanguagePreferences.java```   
5.4.3 Preferences are loaded and stored via a "model" and "store".  These are ```bridgepoint/src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferences[Model|Store].java```   

6. Design
---------

7. Design Comments
------------------

8. Unit Test
------------

End
---

