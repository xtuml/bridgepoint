---
This work is licensed under the Creative Commons CC0 License
---

## Verifier support for Interface message parameters of type `inst_ref` and `inst_ref-set`

### xtUML Project Design Note


### 1. Abstract


With support for Package References available, it is now possible to use shared class in different components of an application. 
If instance data is to be passed between components it is convenient to use paramters of type inst_ref and inst_ref_set as hitherto 
have been available for within-component communication.

This capability is supported by the BridgePoint Editor but as yet has no execution support within Verifier. 
This work aims to remedy that.

### 2. document References

https://support.onefact.net/issues/12222

https://support.onefact.net/issues/12191

### 3. Background

### 4. Requirements

When an instance is referenced as a parameter value in a send message - interface Operation or Signal - a replica of that instance 
should be created and added to the instance population in the context of the receiving component. All attribute values, including 
`current_state` if appropriate, but excluding any instance referencing attribute values should be copied to the newly-created instance.

Note: there is no requirement to treat receipt of a duplicate instance in any special way. It is the responsibility of the reciever 
to detect such a case and handle it appropriately.


### 5. Analysis

The code which processes parameter setup for invocation of either Interface Operation or Signal must be elaborated to detect the case 
where the parameter is a reference and arrange for creation of a replica instance and copying of attribute values.

In parameter setup, the receiving parameter must be established as a reference type which can be associated with one or more Local 
instance-in-reference (L_IIR) instances: cardinality differs between `inst_ref` and `inst_ref_set`. The replica instance(s) must be created
and related to the execution context. Attribute values must be copied. Note that `current_state` is represented by an association rather 
than an attribute.


### 6. Design

A combination of code analysis by examination, tracing of behavior with a debugger, and experimenting with code changes will be used to 
determine what modifications are required.

### 7. Design Comments

Interface Operation parameters are handled somewhat differently than Signal parameters; two code segments exist which are very similar 
but not identical in two different places. For now, the choice has been to replicate the elaboration of these code segments rather than 
attempt any re-factoring.

See 
SignalInvocation::setupParameterValues()

ComponentInstance::transferValueToTarget()

### 8. Documentation

None

### 9. Unit Test

A test which exercises a variety of data-passing scenarios has been implemented and run successfully.

See models/VamdMC/VerifierTest/Verify12222

### End
