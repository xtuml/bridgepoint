---

This work is licensed under the Creative Commons CC0 License

---

# Cannot assign initialization message for component references
### xtUML Project Implementation Note

### 1. Abstract

The xtUML eXecute debug configuration allows a user to select a port message to
be invoked at the time of initialization of a component. A bug has been observed
where the dialog that allows this selection shows no options if the initializer
is being set for a component reference. It works as expected when setting the
initializer message on the component definition. This cripples the feature
because it is common practice to use component references in system deployment
packages. A user may even want to debug multiple deployments with different
debug configurations and must use component references to achieve this. This
issue is raised to analyze and fix the bug.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9799](https://support.onefact.net/issues/9799) Can't set initializer function  
<a id="2.2"></a>2.2 [BridgePoint DEI #10519](https://support.onefact.net/issues/10519) Duplicate issue  
<a id="2.3"></a>2.3 [BridgePoint DEI #10526](https://support.onefact.net/issues/10526)  

### 3. Background

None.

### 4. Requirements

4.1 A user shall be able to set an initializer message on a component reference
in an xtUML eXecute debug configuration.

### 5. Work Required

5.1 The dialog uses an inherited version of the tree content provider used in model
explorer. This provider leverages the existing provider, but filters out
elements that are not applicable to initialization messages. After inspection,
it can be seen that although instances of `ComponentReference`,
`ImportedProvision`, and `ImportedRequirement` are considered, `PortReference`
is missing. Adding this case to the `getElements` method solves the problem.

### 6. Implementation Comments

6.1 During testing of this feature, a model corruption was observed in the GPS
Watch example model. In the UI component reference in the "GPS_Watch" project,
the link between the UI port reference and the port in the component definition
is broken. The resulting behavior is that the port appears to have no name. The
links to the messages are maintained, so at the moment it does not affect the
execution behavior, but it should be cleaned up. An issue has been raised for 
this: [[2.3]](#2.3).

### 7. Unit Test

7.1 Test with GPS watch  
7.1.1 Open `UI::UI::UI::startTest`. Comment out the existing OAL and add
`UI::connect();`.  
7.1.2 Edit the GPS Watch debug configuration to use `startTest` as the
initializer function on the UI component reference in the "GPS_Watch" package.  
7.1.3 Launch the Java UI application and launch the GPS Watch debug
configuration.  
7.1.4 Confirm that the executing model automatically connects to Java UI and the
GPS Watch operates normally.  

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint  
Branch: 9799_initializer  

<pre>

  doc-bridgepoint/notes/9799_initializer_int.md                                                      |  82 ++++++++++++++++++
  src/org.xtuml.bp.debug.ui/src/org/xtuml/bp/debug/ui/launch/VerifiableElementInitializerDialog.java | 887 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--------------------------------------------------------------------------------------------------
 2 files changed, 519 insertions(+), 450 deletions(-)

</pre>

### End

