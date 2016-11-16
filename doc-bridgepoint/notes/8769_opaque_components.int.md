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
<a id="2.1"></a>2.1 [#8769 Within a component restrict visibility to externally defined model elements](https://support.onefact.net/issues/8769)  
Parent issue of this work.

3. Background
-------------
None

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

7. Implementation Comments
--------------------------
None

8. Unit Test
------------

9. User Documentation
---------------------

10. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint
Branch: 8769_opaque_components

<pre>

 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component/Component.xtuml | 44 ++++++++++++++++++++++++++++++--------------
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesModel.java         |  3 +++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/BridgePointPreferencesStore.java         |  5 +++++
 src/org.xtuml.bp.pkg/plugin_customization.ini                                               |  1 +
 4 files changed, 39 insertions(+), 14 deletions(-)

</pre>

End
---

