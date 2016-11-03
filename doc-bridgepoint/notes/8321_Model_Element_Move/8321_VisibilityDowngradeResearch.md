8321 - Visibility Downgrade Research
-------------------------------------

As part of the model element move (MEM) project [#8321], BridgePoint needs to handle 
cases where visibility between related elements is lost as a result of the move 
operation.  The MEM design calls out some requirements for how such loss of
visibility checking shall work.


This document is scratchpad for thinking about the implementation.  Once this settles out, 
it will make sense to move this to the DNT or INT for MEM.


At a high level, the suggested logic is:  

```
for each movedElement
  // Check visibility of elements referring to moved element 
  find RGOs
  for each rgo in RGOs
    if ( !isVisibleFromSrcToDestination )
      downgrade rgo
      add to list of downgraded elements
    end if
  end for
  // Check visibility of actions that refer to moved elements
  find actions referring to moved elements
  for each rga in RGAs
    if ( !isVisibleFromActionToElement ) 
      add to list of downgraded elements
    end if
  end for

  // Check visibility of elements this one refers to 
  find RTOs
  for each rto in RTOs
    if ( !isVisibleFromDestinationToSrc )
      downgrade rto
      add to list of downgraded elements
    end if
  end for
  // Check visibility of moved actions
  find references within moved actions
  for each rga in RGAs
    if ( !isVisibleFromActionToElement )
      add to list of downgraded elements
    end if
   
  end for
end for
```



Notes:  
------
* Element downgrade reporting is currently (prior to MEM) only being initiated in DataType.dispose().
* DataType.dispose() places call EE UTIL::reportElementDowngraded(), which in turn calls 
TransactionManager.reportElementDowngraded().
* Ability to see the destination of the paste is not equivalent to ability to see an element 
inside the destination (can't see inside private packages or components)
* There are functions already implemented that are used to check visibility that we may leverage with this work.  For example: PackageableElement.isElementVisibleToSelf(), PackageableElement.canReferToDatatype(), and ComponentReference.canReferToComponent()


Moveable Elements:
-------------------
Each movable element will have an OAL function called downgradeCheck() that implements the 
specific logic required.  This function is called via Java reflection near the end of 
PasteAction::run() as specified in the design note.  

```
Data type definition
Check related functions, params, attributes, etc
check RTOs if self is a UDT or SDT (iterate over members)
handle constants


Component definition
Check internals recursively
check component refs that refer to self
check associated interface references.  


Component reference
Done: check if the reference can still see the component to which it is assigned.  Unassign if not. Leverage ComponentReference.canReferToComponent().


Interface definition
check types (return and param) on each op & signal
check interface references that refer to self


Class definition
Done: Loop over attributes, check datatypes for downgrade
Done: Loop over operations, check parameters and return values for downgrade
Done: Loop over imported classes that refer to self, check for downgrade
TODO: need to handle IRDTs to this class. The instance data for the IRDTs are stored inside the parent/container of this class, not inside class file. The issue is that IRDTs are "invisible", there's no facility for the user to capture the elements in order to move them.


Imported class
TODO: implement.  Just check to see if we can see the assigned class.  Unassign if not.


Package (when package contains supported model elements)
Contained packageable elements recursively
```

Other - The SOW’s supported elements list does not explicitly call out these, but they are supported
--------------------------
* Interface references, but they and Imported References are "on" components and component references
* Functions
* Rval and param
* EEs
* Bridge rval and param
* EE Data Item
* EE Event Data Item

* Look for TODOs in downgradeCheck functions


Avoiding Repetitive Code
--------------------------
Much of the code required to implement visibility checks and downgrades will be repetitive, with each operation being quite similar to the analogous ones on other classes.  How can we avoid this near-duplicative code?  
Typing.  Instead of using specific associations such as R101 to connect a Model Class to an Imported Class, consider all such associations as typing.  For example, a port (or an interface reference) is typed by an interface just as an attribute is typed by a data type.  This might allow the visibility-check and downgrade code to be written once, using a single (reflexive) typing association between packageable elements.  
Factoring.  Factor out all the common code and put it in operations on Packageable Element, leaving only the truly class-specific code in the sub-classes of PE.
Generate.  Perhaps the code for visibility checking and downgrading can be generated provided input for the pieces that vary from one element to another such as class names, and association IDs.
