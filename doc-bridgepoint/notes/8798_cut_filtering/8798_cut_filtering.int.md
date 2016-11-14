---

This work is licensed under the Creative Commons CC0 License

---

# Filtering cut selection for Model Element Move
### xtUML Project Implementation Note

1. Abstract
-----------
See [[2.4]](#2.4) Section 1

2. Document References
----------------------

<a id="2.1"></a>2.1 [#8321 Model Element Move](https://support.onefact.net/issues/8321)  
Parent issue for all Model Element Move related work  

<a id="2.2"></a>2.2 [#8798 The "cut" currently implemented (Model Element Move) allows a limited element selection](https://support.onefact.net/issues/8798)  
Parent issue for this work  

<a id="2.3"></a>2.3 [Design note for Model Element Move Issue](../8321_Model_Element_Move/8321_Model_Element_Move.dnt.md)   

<a id="2.4"></a>2.4 [#8798 design note](8798_cut_filtering.dnt.md) This is the design note for this work.  

<a id="2.5"></a>2.5 [Statement of Work for Model Element Move](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document.  This document is 
internal to the One Fact team.  

<a id="2.6"></a>2.6 [Issue 8458 - Create Test Model for Model Element Move](https://support.onefact.net/issues/8458)  

3. Background
-------------
Read the design note [[2.4]](#2.4).

4. Requirements
---------------
See [[2.4]](#2.4) section 4.

5. Work Required
----------------

5.1 Implement `isCuttable` operation on each cuttable element
* Shape types
  * Model Class
  * Imported Class
  * Component
  * Component Reference
  * Data Type
  * Package
  * Interface
  * Constant Specification
* Connector types
  * Association
  * Provision
  * Requirement
  * Imported Provision
  * Imported Requirement

5.2 Add `convertToInstance` to Instance Reference Data Type

Many classes on the OOA of OOA have a `convertToInstance` operation. It returns
the instance as a Java `Object`, and is used for checking selections. This work
required checking selections of Instance Reference Data Type instances, so this
operation was added.

5.3 Add `isSelected` bridge operation to the `Selection` EE in the OOA of OOA

This bridge was copied from the `Client` EE in the OOA of Graphics

5.4 Update `CutCopyPasteAction.java`

`CutCopyPasteAction.selectionIsCuttable` was updated to loop through the
selected model elements and call (by Java reflection) the `isCuttable` operation
on each instance. If any of these throws an error or returns false, the
selection is not cuttable and the whole routine returns false.

6. Implementation Comments
--------------------------

6.1 Constant Specifications

Constant Specifications are not identified as a cuttable element in the SOW for
model element move, however it was determined that this was an oversight in
the requirements and should be supported. Constant Specifications have been
supported in this work.

6.2 Algorithm

The algorithm implemented differs slightly from the pseudo-code that was
proposed in the design note. Below is a more accurate rendering:

```
for each element in the selection list
  call `isCuttable` on the instance (by Java reflection)
    (`isCuttable` is implemented independently for each supported model element.
    It checks if the instance itself is selected and if each connector or shape
    connected to it in the same diagram is selected)
  if an error is thrown or false is returned, the selection is invalid
end for
the selection is valid
```

6.3 `isCuttable` comment

The reader should note that the `isCuttable` operations do _not_ traverse the
model by calling `isCuttable` on their neighbors. `isCuttable` simply checks if
each neighboring connector or shape is selected, but does not verify that the
neighboring connector or shape is cuttable. These operations are designed to be
used by iterating over each element in a selection list and checking if each is
cuttable, so a traversal of the model would introduce a lot of redundancy if
used this way.

This method is not the most efficient. Consider two classes connected by a
relationship. When `isCuttable` is invoked on Class A, Class A will be checked
in the selection list and the relationship will be checked. When `isCuttable` is
invoked on Class B, Class B will be checked and the relationship will be checked
again.

Even though this checking routine could be improved, at this point in time, it
is desirable to take the simple approach.

7. Unit Test
------------
The unit tests for this change are included in the Model Element Move tests. See
the MEM design note [[2.3]](#2.3) for more details.

8. User Documentation
---------------------
See the MEM design note [[2.3]](#2.3) for more details.

9. Code Changes
---------------

Fork/Repository:    leviathan747/bridgepoint  
Branch:             8798_mem_selections  

<pre>

 doc-bridgepoint/notes/8798_cut_filtering/8798_cut_filtering.dnt.md                                                                  | 220 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Association/Association/Association.xtuml                                   |  39 ++++++++++++++++++++++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component Library/Component Reference/Component Reference.xtuml   |  39 +++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component Library/Imported Provision/Imported Provision.xtuml     |  42 +++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component Library/Imported Requirement/Imported Requirement.xtuml |  40 +++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Component/Component.xtuml                                         |  39 +++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Interface/Interface.xtuml                                         |  11 ++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Provision/Provision.xtuml                                         |  47 ++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Requirement/Requirement.xtuml                                     |  47 ++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Constants/Constant Specification/Constant Specification.xtuml               |  11 ++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Data Type/Data Type.xtuml                                            |  34 +++++++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Instance Reference Data Type/Instance Reference Data Type.xtuml      |  22 ++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/Package.xtuml                                     |  11 ++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/External Entities/External Entities.xtuml                                   |  22 ++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Imported Class/Imported Class.xtuml                               |  29 +++++++++++++++++++++
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Model Class/Model Class.xtuml                                     |  34 +++++++++++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/CutCopyPasteAction.java                                                              |  48 ++++++++++++++++++++---------------
 17 files changed, 714 insertions(+), 21 deletions(-)


</pre>

End
---

