---

This work is licensed under the Creative Commons CC0 License

---

# Imported interface reference graphics not rendered for component references  
### xtUML Project Implementation Note

1. Abstract
-----------
Graphics reconciliation is broken when a component reference is assigned to a
component outside the current project. Issue #8581 is raised to fix it
[[2.1]](#2.1). Errors also exist where required interfaces do not show up on
component diagrams, and no interface references are drawn on component diagrams
when there are no existing graphics. Issue #8603 is raised to fix these issues
[[2.4]](#2.4)  

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8581 Interface not created during "Assign Component..." in IPR manual test.](https://support.onefact.net/issues/8581)  
<a id="2.2"></a>2.2 [#2409 Inter-Project references manual test](https://support.onefact.net/issues/2409)  
<a id="2.3"></a>2.3 [#8472 graphics reconciliation is creating graphics for connectors when it shouldnt](https://support.onefact.net/issues/8472)  
<a id="2.4"></a>2.4 [#8603 Required Interfaces missing from component diagram.](https://support.onefact.net/issues/8603)  
<a id="2.5"></a>2.5 [#8615l Enable automated testing for graphics reconciliation](https://support.onefact.net/issues/8615)  

3. Background
-------------

3.1 Issue #8581

The issue states that an interface reference does not appear on a component
reference being assigned to a component as per manual test #2409 [[2.2]](#2.2).

An update to the issue notes that the issue does not exist when the component
and component reference are in the same project, but only presents itself when
assigning to a component across project boundaries.

To analyze the issue, I created a simple test case in a git repository. The case
consisted of two projects. Project 1 contained a single package with one
component and one interface. The component had one provided and one required
interface reference, both assigned to the single interface. Project 2 contained
a single package with one component reference (unassigned). Project 2 had IPRs
enabled.

The test case was to assign the single component reference in Project 2 to the
component defined in Project 1.

After debugging the test, I discovered the underlying issue. When reconciling
imported provisions and requirements, the auto-reconciler uses the actual
provision/requirement connector as a template. This allows the imported
provision/requirement to have the same relative position and orientation on the
component reference as the actual provision/requirement has on the component.
The routine to find the template connector was returning null because we were
restricting graphics reconciliation from finding connectors across project
boundaries.

This restriction was introduced as part of issue #8472 [[2.3]](#2.3)

To resolve the issue, I first modified the _findConnector_ operation to take a
parameter _look_outside_system_. This parameter allows the caller to specify
whether the operation should include elements found outside the current project.
Next, I introduced a transient variable _look_outside_system_ and initialized it
to true. If the code to find a template object returns null, this variable is
set to false. In this way, the reconciler is allowed to look outside the project
only if it is looking for a template connector.

3.2 Issue #8603

The issue states that requirements are not drawn on component diagrams although
they appear on the component in the package diagram.

An update states that in MASL models where all graphics are created on import,
no interfaces appear in the component diagram.

To analyze the issue, I created two test models in a git repository. Model 1
had a single component in a package. Model 2 had a single component in a
package, with one provided and one required interface reference. Graphics were
removed from Model 2.

The first test case was to create a requirement in the package diagram of Model
1 and see if the requirement showed up in the component diagram. The second test
case was to create a requirement in the component diagram of Model 1 and see if
the requirement showed up in the package diagram. The third test case was to
reconcile graphics on Model 2 and verify that all the graphics were created
properly in both diagrams.

To solve the first issue, I discovered that the logic in `C_C`
_getRequirementID_ was broken, and did not match that of _getProvisionID_. I
resolved this issue. I also discovered that `EP_PKG` _getProvisionCount_,
_getProvisionID_, _getRequirementCount_, and _getRequirementID_ also had a
logical error. I fixed these bugs and the first and second test cases passed.

To solve the second issue, I found that Package was missing an auto
reconciliation specification in the PEI data. Because Component is unique in
that it is both a shape and a container, it has two "symbols" (element
specifications) in the graphics PEI data. In the list of `GD_ARS`s on Package,
only the component shape was reconciled. Both the shape and the container of a
Component should be reconciled by Package. I added this to the PEI data and the
third test case passed.

4. Requirements
---------------
4.1 The auto reconciler shall create graphics for imported interface references
when assigning a component reference to a component from a different project  
4.2 The auto reconciler shall create requirements in a component diagram if the
requirement is drawn in a package  
4.3 The auto reconciler shall create requirements in a package diagram if the
requirement is drawn in a component diagram  
4.4 The auto reconciler shall create provisions and requirements on a component
diagram if there are no existing graphics  

5. Work Required
----------------

5.1 Modify `GD_ARS` _findConnector_ to take a boolean parameter
_look_outside_system_  
5.2 Modify `GD_ARS` _reconcileConnectorsWithExistingGraphics_ to pass
_look_outside_system_ as true to _findConnector_ if looking for a template
connector  

5.3 Modify `C_C` _getRequirementID_ to match the pattern of _getProvisionID_  
5.4 Fix logical errors in `EP_PKG` operations (see section 3.2 paragraph 5)  

5.5 Add `GD_ARS` for "Component Container" for Package  

6. Implementation Comments
--------------------------
none

7. Unit Test
------------
7.1.1 Create a test case as described in section 3.1  
7.1.2 Assign the component reference in Project 2 to the component in Project 1  
7.1.3 Verify that the graphics for the imported interface references are drawn on
the component reference (4.1)  

7.2.1 Create test cases as described in section 3.2  
7.2.2 Create a requirement in the package diagram of Model 1  
7.2.3 Verify that the requirement showed up in the component diagram (4.2)  
7.2.4 Create a requirement in the component diagram of Model 1  
7.2.5 Verify that the requirement showed up in the package diagram (4.3)  
7.2.6 Reconcile graphics on Model 2 (_BridgePoint Utilities > Reconcile
Graphics_)  
7.2.7 Verify that all the graphics were created properly in both diagrams (4.4)  

An issue was raised to automate these tests [[2.5]](#2.5)  

8. User Documentation
---------------------
none

9. Code Changes
---------------
Branch name: leviathan747/8581_assigned_component_interface_graphics

<pre>

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component/
    Component.xtuml
org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/
    Package.xtuml

org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Graphical Data/Graphical Data.xtuml
org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Graphical Data/Auto Reconciliation Specification/
    Auto Reconciliation Specification.xtuml

org.xtuml.bp.ui.graphics/plugin.xml

</pre>

End
---

