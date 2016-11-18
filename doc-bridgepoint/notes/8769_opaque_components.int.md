---

This work is licensed under the Creative Commons CC0 License

---

# Make components opaque
### xtUML Project Implementation Note

1. Abstract
-----------
Components hide functionality and implementation details from external elements
except by accessing ports. However, elements outside a component had been visible
from the inside. This property could cause unwanted consequences when package
references are introduced. This work is to allow components to restrict
visibility of external elements as well.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8769 Within a component restrict visibility to externally defined model elements](https://support.onefact.net/issues/8769)  Parent issue of this work.  
<a id="2.2"></a>2.2 [#8798 The "cut" currently implemented (Model Element Move) allows a limited element selection](https://support.onefact.net/issues/8798)  
<a id="2.3"></a>2.3 [Package reference clarification truth table](https://docs.google.com/spreadsheets/d/1Xx349VAIN16xampkkm7EtqRtf-QNZIootUxY_xrNiZQ/edit#gid=0)  
<a id="2.4"></a>2.4 [Internal One Fact design thoughts](https://docs.google.com/document/d/1ONBhHKfspMfvD5-3IEAQgrUQBjaW-at79gZy3HcmZ3o/edit)  
<a id="2.5"></a>2.5 [#8875 Opaque component tests](https://support.onefact.net/issues/8875)  

3. Background
-------------
See Abstract.  Also see [2.3](#2.3) and [2.4](#2.4).

4. Requirements
---------------
4.1 Access of elements external to a component shall be restricted  
4.1.1 The BridgePoint editor shall have the capability to restrict access of model
elements external to a component through element choosers  
4.1.2 The OAL parser shall have the capability to restrict visibility of model
elements external to a component  
4.2 The access restriction shall be set/reset with a preference  
4.2.1 The preference shall not introduce new UI elements, but shall be
controlled via config file  
4.2.2 The preference shall default to allow access of external elements from
within a component  

5. Analysis
-----------

5.1 `collectVisibleElementsForName`

There is one operation used by BridgePoint to gather up visible model elements.
`collectVisibleElementsForName` is an operation that exists on the Component,
System Model, Package, and Packageable Element classes. It is used to traverse
the model and gather all elements of a specific type, paying attention to
visibility rules. This routine is used by the OAL parser and the element chooser
dialogs to find a list of visible elements.

5.2 Model element choosers

The following is a list of choosers that are implemented by the BridgePoint
editor:

- Formalize interface: Gathers a list of interfaces  
- Assign class: Gathers a list of classes  
- Assign component: Gathers a list of components  
- Assign as package ref: Gathers a list of packages  
- Set type: Gathers a list of data types  

5.3 Exceptions to the visibility restriction

5.3.1 Global types

Global built-in types are linked at the system level. If elements inside a
component are restricted from seeing outside the component, the global types
will not be accessible. This situation will be special cased to collect the
globals even when the components are opaque.

5.3.2 Packages

Imported packages are the mechanism by which opaque components will be able to
see external elements. In order to set up package references, a package chooser
for a package inside the component must be able to see packages outside the
component. Because the package chooser is not used anywhere else, and has no
meaning to the parser, it is acceptable to allow packages to be visible from
within a component. It is best to keep things simple until a need arises to make
this check more specific.

6. Work Required
----------------
6.1 Add preference to `BridgePointPreferencesModel.java` and
`BridgePointPreferencesStore.java`. Set default value to be "false" in
`plugin_customization.ini`.

6.2 Modify `collectVisibleElementsForName` operation on the Component class.
Before traversing to the parent package, check the opaque components preference.
If the preference is set to "false" (transparent components), or the element
type we are searching for is a package, traverse up the hierarchy like normal.
If the preference is set to "true" (opaque components), and the element type is
not package, do not traverse upwards. If we do not traverse upwards, select the
system model to which this component belongs and select all the global elements.
This allows us to get the global types from inside the component.

6.3 Modify `collectVisibleElementsForName` operation on the Package class.  
As we are collecting elements, if the package being processed is assigned as a
package reference then we initiate collection (with descending traversal only)
of the referred-to package.  In this way we bring the elements inside the 
referred-to package into visible scope.  

6.4 Add `isCuttable` to External Entity
This was left off in the cut functionality fixes and is added here.  

7. Implementation Comments
--------------------------
7.1 The change mentioned in 6.4 is a quick fix for issue #8798 [[2.2]](#2.2).
External Entities were overlooked when making cut filter support.

8. Unit Test
------------
See [2.5](#2.5).  

9. User Documentation
---------------------
None.  

10. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint  
Branch: 8769_opaque_components  

<pre>
 doc-bridgepoint/notes/8769_opaque_components.int.md
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component/Component.xtuml
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/External Entity/External Entity.xtuml
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package.xtuml
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java
 src/org.xtuml.bp.pkg/plugin_customization.ini                                      
 
</pre>

End
---

