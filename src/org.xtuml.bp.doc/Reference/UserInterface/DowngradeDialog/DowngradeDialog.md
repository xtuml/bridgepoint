Downgrade Dialog
========================

Several actions, including delete, rename, move, and visibility change, cause 
the appearance of this dialog:  

![Image of Downgrade Dialog](dialog.png)
  
It indicates that the action in question, if completed, will cause the (likely 
undesirable) disassociation of model elements.  The following model element 
types may be involved:  
  
* Attribute, referring to a data type
* Parameter, referring to a data type
* Data type, referring to a data type
* Constant specification, referring to a data type
* Imported class, referring to a class 
* Component reference, referring to a component definition
* Component definition, referring to an interface definition (through formalization of a port)
* Package reference, referring to a package 
  
Examples include:  

* Deletion of a data type causing all parameters and attributes typed by the 
deleted data type to be reverted to the corresponding core type.
* Moving of classes such that they lose visibility to data types to which they 
refer for typing of attributes and parameters.
* Changing the visibility of a package such that referring model elements no 
longer have visibility to some of the model elements to which they refer.  For 
example an imported class no longer has visibility to the class to which it refers.
* Moving a package such that a component reference has lost visibility to the 
associated component definition or a component definition can no longer see an 
interface definition used to formalize one of its ports. 
  
The dialog presents an opportunity to copy or print the list of affected model 
elements before either continuing or canceling the operation.  Typically it is 
best to prevent the dissociation of the affected model elements before 
continuing with the operation in question, so it is recommended that you:  

1. Copy or print the list of affected elements.
2. Cancel the operation.
3. Adjust the model to ensure visibility between the affected elements will be 
maintained after the contemplated operation is completed.
4. Perform the operation in question.
  
  