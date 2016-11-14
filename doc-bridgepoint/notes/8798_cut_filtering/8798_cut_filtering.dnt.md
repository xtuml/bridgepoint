---

This work is licensed under the Creative Commons CC0 License

---

# Filtering cut selection for Model Element Move
### xtUML Project Design Note

1. Abstract
-----------
In the current state of the Model Element Move work the cut source selection
was limited to Packages and Datatypes. This issue is raised to enabled full
functionality. Note that the main reason selection is limited is that the
concept of what a logical element selection is not present. With this filter, a
user is allowed to select any subset of elements across many canvases. The
problem with logical selection lies mainly in connectors. It simply does not
make sense to make partial selections of connected elements in most cases. This
issue is raised to resolve this problem. This issue also covers the issue of
logical paste.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8321 Model Element Move](https://support.onefact.net/issues/8321)  
Parent issue for all Model Element Move related work  

<a id="2.2"></a>2.2 [#8798 The "cut" currently implemented (Model Element Move) allows a limited element selection](https://support.onefact.net/issues/8798)  
Parent issue for this work  

<a id="2.3"></a>2.3 [Design note for Model Element Move Issue](../8321_Model_Element_Move/8321_Model_Element_Move.dnt.md)   

<a id="2.4"></a>2.4 [Statement of Work for Model Element Move](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document.  This document is 
internal to the One Fact team.  

<a id="2.5"></a>2.5 [Issue 8458 - Create Test Model for Model Element Move](https://support.onefact.net/issues/8458)  

3. Background
-------------
The reader should take a moment to inspect the design note for Model Element
Move [[2.3]](#2.3), and internal readers should also review the SOW
[[2.4]](#2.4) before continuing with this note.

4. Requirements
---------------
4.1 "Invalid cut selection" shall be defined (see section 5.1)  
4.2 Cut shall be disabled on an invalid selection  
4.2.1 The _Cut_ CME shall be grayed out when an invalid cut group is selected  
4.2.2 Cut shall be prevented on an invalid selection when cut is attemped
via keyboard shortcut  

4.3 "Invalid paste selection" shall be defined (see
4.4 Paste shall be disabled on an invalid selection  
4.4.1 The _Paste_ CME shall be grayed out when an invalid paste group is
selected  
4.4.2 Paste shall be prevented on an invalid selection when paste is attemped
via keyboard shortcut  

5. Analysis
-----------

5.1 Invalid cut selection

In both the design note and the statement of work, general terminology is used
to describe a selection that is not "cuttable" -- that is, it would be logically
incorrect to move the selected model elements. This work requires that a clear
definition be formed for invalid cut selections.

5.1.1 Definition

A selection candidate for cut is said to be invalid if the selection contains
any 
* unsupported model element  
* shape which anchors a connector which is unselected,
* shape connected to an unselected shape via a connector, or
* connector which is anchored on an unselected shape.

5.1.2 Graphical pollution?

The reader should notice that the above definition is a graphical definition.
The question arises whether this is pollution of the semantics of cut with
graphical details of connectors and shapes. To this point, it should be
recognized that the idea of cut selection is in fact a graphical/packaging
concern. xtUML furthermore is graphical!

For example, _Imported Class_'s exist in order that one class can be used
cleanly across multiple diagrams. This mechanism prevents any relationship from
being separated from the element related (_Imported Class_ or _Model Class_)

For this reason, graphics is central to the idea of valid cut selection.
Separating model elements that are not meant to be separated graphically is
corruption of model data.

5.1.3 Absence of graphics

Although cut selection validity is a graphical issue, it is not impossible to
determine cut validity in the absence of graphics. The abstract idea of
connectors and shapes can be imposed on the specifics of the OOA of OOA even if
no graphical instances are present.

5.1.4 Specific rules

* A component reference cannot be cut unless each imported provision and
requirement is also selected  
* A component definition cannot be cut unless each provision or requirement is
also selected  
* A component reference or definition with a provision/requirement that is
satisfied, cannot be cut unless the other component reference or definition is
also selected  
* A class or imported class cannot be cut unless each relationship drawn to it
in the same package and all classes or imported classes connected via these
relationships are also selected
* A package, interface definition, or data type can always be cut

5.2 Filtering for canvas copy

BridgePoint already enforces selection rules for copy and paste similar to the
above rules for cut. It was desirable to analyze how this is done to inform this
design. The rules are similar but slightly different for copy:

A selection candidate for copy is said to be invalid if the selection contains
any 
* unsupported model element  
* connector which is anchored on an unselected shape.

A valid copy selection contains all valid cut selections, but it additionally
allows isolated shapes to be copied even if they anchor unselected connectors.
Because of this definition, no clever selection rules need to be implemented in
model explorer (since copy of any isolated element is allowed and connectors are
not shown in model explorer). The special selection rules are implemented in the
canvas copy action class. This filtering depends on the OOA of Graphics and the
pseudo-algorithm is as follows:

```
In the current Model (diagram):
for each graphical element:
  if the graphical element is selected:
    if the element is a shape:
      if it is not a supported model element, the selection is invalid
    else if the element is a connector:
      if either the shape on one side or the other is unselected, the selection is invalid
      (it is ok if it is connected to an unselected connector)
    end if
  end if
end for
selection is valid
```

This filtering algorithm could be extended to support cut by also checking
shapes to make sure all anchored connectors are also selected. The approach is
pleasantly abstract, hiding details of specific shapes and connectors, however,
it depends on graphical instances being present. This is acceptable for canvas
copy because the canvas must exist for canvas copy to happen anyway, however,
for cut, the filter must also apply to the model explorer and graphical
instances may not always be present.

5.3 Invalid paste selection

A selection candidate for paste after cut is said to be invalid if
* If the destination does not support paste  
* More than one destination is selected  
* The destination is the same as the source  

After analysis, it was determined that no additional work is needed to fulfill
the requirements for paste.

6. Design
---------

The design for cut selection shall be based on the pseudo-algorithm for canvas
copy laid out in section 5.2. It shall be modified to use OOA of OOA vocabulary
and specifics

6.1 Algorithm

```
for each element in the selection list
  if the element is a shape type (class, imported class, component, component reference, data type, package, interface definition)
    check if each connector is selected (if no, selection is invalid)
  else if element is a connector type (relationship, provision, requirement, imported provision, imported requirement)
    check if the shape element on either side is selected (if no, selection is invalid)
  end if
end for
the selection is valid
```

6.2 Checking operations

An operation `isCuttable` shall be added to each class listed in the above
algorithm. These operations shall determine if a class is cuttable by traversing
the proper connecting elements and checking selection.

6.3 Is selected checking

A new bridge `isSelected` shall be added to the `Selection` external entity and
shall follow the pattern of `isSelected` used in the OOA of Graphics `Client`
EE.

6.4 Integration with current infrastructure

This filtering shall be implemented in the file `CutCopyPasteAction.java`. The
infrastructure is already in place in this file, the filtering algorithm must
simply be implemented here. See the MEM design note [[2.3]](#2.3) for more details.

7. Design Comments
------------------
None

8. User Documentation
---------------------
See the MEM design note [[2.3]](#2.3) for more details.

9. Unit Test
------------
The unit tests for this change are included in the Model Element Move tests. See
the MEM design note [[2.3]](#2.3) for more details.

End
---

