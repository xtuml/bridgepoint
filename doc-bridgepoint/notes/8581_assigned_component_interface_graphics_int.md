---

This work is licensed under the Creative Commons CC0 License

---

# Imported interface reference graphics not rendered for component references  
### xtUML Project Implementation Note

1. Abstract
-----------
Graphics reconciliation is broken when a component reference is assigned to a
component outside the current project. This issue is raised to fix it.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8581 Interface not created during "Assign Component..." in IPR manual test.](https://support.onefact.net/issues/8581)  
<a id="2.2"></a>2.2 [#2409 Inter-Project references manual test](https://support.onefact.net/issues/2409)  
<a id="2.3"></a>2.3 [#8472 graphics reconciliation is creating graphics for connectors when it shouldnt](https://support.onefact.net/issues/8472)  

3. Background
-------------
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
provision/requriement connector as a template. This allows the imported
provision/requriement to have the same relative position and orientation on the
component reference as the actual provision/requriement has on the component.
The routine to find the template connector was returning null because we were
restricting graphics reconciliation from finding connectors across project
boundaries.

This restriction was introduced as part of issue #8472 [[2.3]](#2.3)

To resolve the issue, I first modified the _findConnector_ operation to take a
parameter _look_outsid_system_. This parameter allows the caller to specify
whether the operation should include elements found outside the current project.
Next, I introduced a transient variable _look_outside_system_ and initialized it
to true. If the code to find a template object returns null, this variable is
set to false. In this way, the reconciler is allowed to look outside the project
only if it is looking for a template connector.

4. Requirements
---------------
4.1 The auto reconciler shall create graphics for imported interface references
when assigning a component reference to a component from a different project  

5. Work Required
----------------

5.1 Modify `GD_ARS` _findConnector_ to take a boolean parameter
_look_outside_system_  
5.2 Modify `GD_ARS` _reconcileConnectorsWithExistingGraphics_ to pass
_look_outside_system_ as true to _findConnector_ if looking for a template
connector  

6. Implementation Comments
--------------------------
none

7. Unit Test
------------
7.1 Create a test case as described in section 3  
7.2 Assign the component reference in Project 2 to the component in Project 1  
7.3 Verify that the graphics for the imported interface references are drawn on
the component reference  

8. User Documentation
---------------------
none

9. Code Changes
---------------
Branch name: leviathan747/8581_assigned_component_interface_graphics

<pre>

 .../models/org.xtuml.bp.ui.canvas/ooaofgraphics/Graphical Data/Auto Reconciliation Specification/Auto Reconciliation Specification.xtuml              | 21 ++++++++++++++++-----
 1 file changed, 16 insertions(+), 5 deletions(-)

</pre>

End
---

