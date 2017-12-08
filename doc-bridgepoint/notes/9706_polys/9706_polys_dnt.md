---

This work is licensed under the Creative Commons CC0 License

---

# When polymorphic events are changed in a supertype, reflect the change in the subtype(s)
### xtUML Project Design Note

### 1. Abstract

From [[2.1]](#2.1):

> When creating a new domain with a state machine in both the supertype and the
> subtype the events added to the supertype are not automatically available as
> polymorphic events in the subtype.
>
> I have to manually add the polymorphic events to the subtypes.
>
> Having to add them manually will likely lead to errors.

### 2. Document References

<a id="2.1"></a>2.1 [#9706 When polymorphic events are changed in a supertype, reflect the change in the subtype(s)](https://support.onefact.net/issues/9706)  
<a id="2.2"></a>2.2 [#9178 MASL Polymorphic events and transition tables](https://support.onefact.net/issues/9178)  
<a id="2.3"></a>2.3 [#9300 Document MASL idiom for polys](https://support.onefact.net/issues/9300)  

### 3. Background

As part of the analysis and implementation for [[2.2]](#2.2), a new idiom was
introduced to handle MASL style polymorphic events (this idiom was documented as
part of [[2.3]](#2.3)). In the xtUML semantic, polymorphic events are truly
abstract -- that is, only one subtype state machine is allowed to assign and
handle a given polymorphic event. In MASL however, a given event must be handled
by every state machine in the subtype supertype hierarchy.

For the purposes of this note and surrounding work, the term "abstract
polymorphic event" will refer to an xtUML style polymorphic event which can only
be handled by one subtype in the hierarchy and "concrete polymorphic event" will
refer to a MASL style polymorphic event that must be handled by every instance
in the hierarchy.

It is now time to start supporting concrete polymorphic event events in
BridgePoint. This note specifies the analysis and design to support these
events.

### 4. Requirements

4.1 BridgePoint shall provided capability for editing concrete or abstract
polymorphic events  
4.1.1 A mechanism shall be designed to clearly distinguish between models of the
two types  
4.1.2 No support shall be implemented for converting between the two idioms
4.2 The support for concrete polymorphic events shall be integrated with the
MASL tools to produce valid MASL models  
4.2.1 The MASL importer shall be modified to properly create concrete
polymorphic events as metamodel instances  
4.2.2 The MASL exporter shall handle concrete polymorphic events and produce
valid MASL  
4.2.3 An upgrade strategy shall be considered for models that follow the current
MASL idiom  
4.3 An issue shall be raised to analyze the impact on Verifier  
4.4 An issue shall be raised to analyze the impact on the model compilers  

### 5. Analysis

5.1 Overview

Study the following portion of the "State Machine subsystem:
![events.png](events.png)

Currently BridgePoint supports abstract polymorphic events by creating an event
instance in the supertype state machine which has an instance of `SM_PEVT` as
its subtype. In the subtype state machine, an instance of `SM_EVT`, `SM_SEVT`
and `SM_NLEVT` are created when the polymorphic event is assigned to a
transition. The `SM_NLEVT` instance is related to the `SM_PEVT` instance in the
supertype state machine; the `SM_SEVT` instance participates in the state event
matrix via `SM_SEME`. Notice that `SM_LEVT` relates to `SM_CRTXN` across R509.
This means that non-local events (i.e. references to polymorphic events) cannot
be used in creation transitions. Note also that the relationship between
`SM_NLEVT` and `SM_PEVT` (R527) does not prevent non-local events from being in
the same state machine as the polymorphic event even though this behavior is
enforced in OAL.

The current model has everything necessary to represent concrete polymorphic
events. The UI could be loosened to allow assigning polymorphic events in
multiple levels of the subtype/supertype hierarchy. At the supertype level, two
events would have to exist. One would be the polymorphic event itself, and the
other would be the non-local state event matrix event that points to the
polymorphic event. Some masking in the UI would have to be done to prevent both
events in the supertype from being displayed.

5.2 Vocab

For the rest of this note, the term "local polymorphic event" will be used to
refer to a polymorphic event that an instance of `SM_NLEVT` refers to which is
in the same state machine as the non-local event instance (i.e. a concrete
polymorphic event used in the supertype).

5.3 All events are polymorphic events

In the "concrete polymorphic" idiom, all events are polymorphic -- that is to
say, an event _must_ be handled in every instance state machine in a
subtype/supertype hierarchy. Because of this, a column in the state event matrix
must be created immediately if a supertype event is created. The column will
contain initial values for the cells ("Can't Happen" or "Ignore"), but these can
be changed or assigned to a transition. The current BridgePoint behavior is to
only create the column of the state event matrix when a polymorphic event is
actually assigned to a transition. This new behavior must be invoked to sync the
columns at the following actions:

5.3.1 Create event  
5.3.2 Paste event  
5.3.3 Create state machine in subtype  
5.3.4 Add subtype to subtype/supertype hierarchy  
5.3.5 Unassign event from creation transition (see section 5.5)  

5.4 UI Masking in the supertype

This strategy of using a non-local event referring to a local polymorphic event
in the supertype results in two "identical" events in the supertype. Some
special masking will need to be done to prevent both from showing in the tree.
Additionally, the naming convention for local events should be followed (as
opposed to scoped naming as for polymorphic events). Special cases shall be put
into place to make this look correct. Additionally, the state event matrix must
not have a column for each of the two events, but just one. Integrity errors
that warn two identical events in the same class will be suppressed when
concrete polymorphic events are used.

5.5 Creation events

As mentioned in 5.3, "all events are polymorphic". This means that creation
transitions will have no valid events to be assigned because every newly created
event will be converted to a polymorphic event immediately. The UI will be
relaxed to allow assigning polymorphic events to creation transitions, however,
the action will be cancelled if any of the non-local events in the subtypes are
assigned to a transition. Once selected for assignment, the polymorphic event
will be migrated to a local event.

Additionally, when an event is unassigned from a creation transition, the sync
will be invoked to re-migrated to a polymorphic event and propagated to the
subtype state machines. This will only occur if the event is not assigned to any
other creation transitions in the state machine.

### 6. Design

TODO:
- paste event
- unassign from creation event when the event is on another creation transition
- cause sync when a creation event is assigned. does it get propagated?

### 7. Design Comments

### 8. User Documentation

### 9. Unit Test

### End
