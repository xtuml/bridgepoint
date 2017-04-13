---

This work is licensed under the Creative Commons CC0 License

---

# Support formalizing with imported classes with inter-project references
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the changes required to support association formalization when dealing with imported classes in an inter-project reference environment.  
This note also captures the changes to prevent modification of Property Parameter data within the properties view.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9249](https://support.onefact.net/issues/9249) Disallow parameter or return type to be changed in ports  
<a id="2.2"></a>2.2 [BridgePoint DEI #9248](https://support.onefact.net/issues/9248) Unable to formalize associations to/from shared classes  

3. Background
-------------
Changes were made in previous version of the tool to prevent modification of port children, Interface Operations and Signals.  This was done by creating a Port Reference element that would be used in the UI.  A later issue addressed the same changes for Property Parameters but only considered the name attribute.  

Support was added to support inter-project class referencing.  This did not call out a requirement to be able to formalize associations between inter-project reference classes.  This missing requirement lead to no testing for formalization.  

4. Requirements
---------------
4.1 Formalization shall be supported on all association types when the association includes an inter-project class reference  
4.2 No data under the Property Parameter property page entry shall be modifiable  

5. Work Required
----------------
5.1 Support formalization  
The OAL to support formalization has selection using a where clause.  These where clauses do not get generated to use the fast lookup feature of the architecture.  They search the current model-root given by the infrastructure which is the model-root for the local element.  This of course will not return any inter-project elements.  The locations which have this problem are Class Identifier.hasAttributes() and Model Class.newReferentialAttribute().  There are two approaches to handle these cases, one which traverse through S_SYS to try and find the elements and the other to use the fast lookup infrastructure.  The latter approach was used in both cases as it takes advantange of the speed and the OAL is simpler.  Note that we do not need to consider the IPR enablement here as the UI for formalization will already have done so.  

5.2 Prevent modification of Property Parameter data in properties page  
As stated in the background changes were made to prevent modifying the name attribute in the properties page.  The change was to create a new function Util_c.isProvidedParameter() and call it from Property Parameter.canRename().  This function looks at the selected element in the tree and traverses to its parent, returning true only if the parent is either an Interface Operation or Signal.  The BuildPropertySource.arc archetype is modified to set the property sheet's readonly variable to the value of the Util_c.isProvidedParameter() return.  Additionally, to prevent description changes the same archetype is modified to not generate the DescriptionPropertyDescriptor.  This causes generation for the description value as a standard property descriptor which is read-only.  This is the same approach we take for other cases with similar situations.  

6. Implementation Comments
--------------------------

7. Unit Test
------------
7.1 Tests are captured in [Issue 9250](https://support.onefact.net/issues/9250)  
7.2 Tests are captured in [Issue 9245](https://support.onefact.net/issues/9245)  

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/travislondon/bridgepoint
Branch: 9248_9249_Port_reference_modification-Formalize_shared_classes

<pre>

doc-bridgepoint/notes/
    9248_9249_Port_reference_modification-Formalize_shared_classes/
    9248_9249_Port_reference_modification-Formalize_shared_classes.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/
    Class Identifier/Class Identifier.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Model Class/
    Model Class.xtuml

org.xtuml.bp.ui.properties/arc/BuildPropertySource.arc



</pre>

End
---

