---

This work is licensed under the Creative Commons CC0 License

---

# Fix graphics reconciliation problems
### xtUML Project Implementation Note

1. Abstract
-----------
This note describes the fixes for two graphics reconciliation bugs.

2. Document References
----------------------
<a id="8449"></a>8449 [BridgePoint DEI #8449](https://support.onefact.net/issues/8449) 
State diagram arrowheads point wrong direction in some cases.  
<a id="8483"></a>8483 [BridgePoint DEI #8483](https://support.onefact.net/issues/8483) 
Event labels not rendered for creation transitions.  

3. Background
-------------
During Raven production the ability to reconcile all graphics was introduced. 
Testing uncovered a couple of problems ([[8449]](#8449) [[8483]](#8483)). This 
document describes the fixes for these problems.


4. Requirements
---------------
4.1 Issue [[8449]](#8449) shall be resolved.  
4.2 Issue [[8483]](#8483) shall be resolved.  

5. Work Required
----------------
5.1 State diagram arrowheads point wrong direction in some cases.  
I made a change to assure that we treat source and destination sides of a connector
properly in cases where connectors are directional.

To do this, I created a new realized bridge, 
```boolean isDestination(Instance shapeInstance, Instance connectorInstance)```. 
This is implemented in Cl_c.java. Each of the 2 instances passed into this routine are
ooaofooa NonRootModelElement instances. This routine returns false by default. It returns 
true only in the case where the shape passed is a StateMachineState, the connector
is a Transition, and the state given is the destination state for the given Transition. It 
returns false for all other cases.

This bridge is used in GD_ARS:reconcileConnectorsNoExitingGraphics()
after we have all the information needed to create the connector, but just before we 
actually make the call to create the new connector. This bridge determines if "side1" of
the connector is the really the destination instead of the source for the connector. If 
it is the destination, then we swap side1 and side2 just
before we create the connector so that we get the source and destination of the connector
correct during creation. Note that currently the only directional connectors reconciled
are Transitions, so it is this specific case where the isDestination() 
bridge may return true, in all other cases it returns false and there no swap occurs.


5.2 Event labels not rendered for creation transitions.  
The problem we had was that the ooaofgraphics defines a separate OOAType 
datatype for "Creation Transition". This type differentiates Creation 
Transition from other Transition types when the Transition is rendered. 

I added a new GD_ES instance and the corresponding ooaofooa
operations to allow reconciliation with this new instance. The new
instance is for symbol "Creation Transition". 

Along with introducing the new ooaofooa operations
getCreationTransiontionCount()/getCreationTransiontionId(), to support
this new GD_ES I modified 
getTransitionDestinationCount()/getTransitionDestinationId() to filter
out Creation Transitions so they are only returned by the new ooaofooa
operations that were added.  

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
7.1 The tests are describes in the issues. It is simple a matter a performing
graphics reconciliation with a model containing the type of elements described 
in the issues.

8. User Documentation
---------------------
none

9. Code Changes
---------------
Fork/Repository: rmulvey/bridgepoint
Branch: 8483_creation_transition

<pre>

> doc-bridgepoint/notes/
    8449_8483_reconciliation_arrow_direction_and_creation_transition/
    8449_8483_reconciliation_arrow_direction_and_creation_transition.int.md

org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/
    State Machine State/State Machine State.xtuml

org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    External Entities/External Entities.xtuml
org.xtuml.bp.ui.canvas/models/org.xtuml.bp.ui.canvas/ooaofgraphics/
    Graphical Data/Auto Reconciliation Specification/
    Auto Reconciliation Specification.xtuml
org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/Cl_c.java

org.xtuml.bp.ui.graphics/plugin.xml


</pre>

End
---

