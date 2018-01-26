---

This work is licensed under the Creative Commons CC0 License

---

# extra attribute after paste of subtype
### xtUML Project Implementation Note

### 1. Abstract

From [[2.1]](#2.1):

> After copying and pasting a subtype, the referentials-as-identifier attributes
> remain with type of Same_As<base attribute>.

### 2. Document References

<a id="2.1"></a>2.1 [#9913 extra attribute after paste of subtype](https://support.onefact.net/issues/9913)  
<a id="2.2"></a>2.2 [#9939 Pasting a class in a package does not update class number](https://support.onefact.net/issues/9939)  

### 3. Background

3.1 Copy/paste flow

The copy and paste flow in BridgePoint works as follows:
3.1.1 On copy, the selection is validated. If valid, the stream exporter is used to
copy the contents of the selection to the system clipboard as SQL insert
statements.  
3.1.2. On paste, the stream importer is used to load a collection of instances from
the SQL insert statements on the system clipboard.  
3.1.3. The instances are batch related and the unique IDs are recalculated (since
this is a copy operation, new unique instances must be created).  
3.1.4. OAL operations are invoked in the appropriate locations to perform higher
level operations such as linking the pasted elements to the new container
element, downgrade checks, renaming/renumbering etc.  
3.1.5. Other architectural operations are performed to finish the copy/paste.

3.2 Observed behavior

The observed behavior where a referential attribute remains after paste is a
failure in 3.1.4. During paste of a class into a package, an operation is run
that prunes away referential attributes that are no longer valid, however, this
operation is failing to dispose the referential attribute because a link is
still existing between the pasted class and the original referred to class.

3.3 ID reassignment

It was found through analysis that the cause of the link corruption described in
3.2 is due to an incomplete reassignment of unique IDs. The IDs themselves are
regenerated, however, not all referential attributes are propagated fully.

3.4 Class renumbering on paste

During this work, it was observed that although the class name gets updated to
be unique in a package on paste, the class number does not get updated. This can
be annoying especially when pasting a class multiple times as duplicate numbers
cause integrity violations that must later be resolved manually. Creating a new
class in a package properly increments the class number, so that logic should be
reused for pasting a class. [[2.2]](#2.2) has been raised to track this bug and
will be resolved as part of this work.

### 4. Requirements

4.1 Copy/pasting a class that contains a referential attribute shall result in a
class with the referential attribute removed.  
4.2 When pasting a class into a package, the class number shall be chosen to be
a unique number in the package.  
4.2.1 This behavior shall mimic the behavior of creating a new class in a
package.  

### 5. Work Required

5.1 Overview

Instead of trying to repair the old ID assigner, a new archetype was introduced
to generate utility classes to do ID reassignment. This used a simplified
approach, but because it doesn't touch the old ID assigner, it doesn't break
existing functionality that uses it (e.g. model compare). Perhaps at some point,
other modules that depend on the ID assigner will be updated to use the new
utility.

5.1 MC-Java changes  
5.1.1 Update MC-Java to generate setters for all attributes including
referentials.  
5.1.2 Update MC-Java to invoke "setUniqueId" in the setter of the ID attribute
which is the "ooa_id".  
5.1.3 `referential_attribute_util.inc` was introduced in MC-Java to generate
referential attribute utility classes.  

5.2 `bp.core` changes  
5.2.1 A new interface `org.xtuml.bp.core.common.IReferentialAttributeManager`
has been introduced to abstract the API for a referential attribute utility
class. This interface has three methods:
```
// Create a new UUID for every identifying attribute of type UUID
// that is not a referential attribute
public void generateUUIDs( NonRootModelElement element );

// Propagate the value of every identifying attribute that is referred
// to by another class
public void propagateIDs( NonRootModelElement element );

// Replace every referential attribute with the type-specific NULL value
public void clearReferentialAttributes( NonRootModelElement element );
```
5.2.2 Update `pasteModelClass` in Package to renumber the classes properly on
paste.  
5.2.3 Add archetype and supporting build targets, gitignore, etc. to build the
referential utility class for core.  

5.3 `ui.canvas` changes  
5.3.1 Add archetype and supporting build targets, gitignore, etc. to build the
referential utility class for canvas.  

5.4 `io.core` changes  
5.4.1 The archetype that generates the stream importer was changed to use the
new utility classes to recalculate unique IDs. The flow is as follows:  
5.4.1.1 For each loaded instance, cache the instance key in a list.  
5.4.1.2 For each loaded instance, clear all referential attribute values.  
5.4.1.3 For each loaded instance, generate a new UUID for each base attribute as
identifier of type `unique_id`.  
5.4.1.4 For each loaded instance, propagate its identifier value to referring
classes (note that order doesn't matter here because "getters" for referential
attributes as ID in MC-Java traverse to get a value from the referred to class).  
5.4.1.5 For each loaded instance, update the instance identifier in the instance
list (using the old IDs cached in step 5.4.1.1).  
5.4.1.6 Repeat steps 5.4.1.1-5 for loaded graphical instances.  

### 6. Implementation Comments

None.

### 7. Unit Test

7.1 Test the scenario in [[2.1]](#2.1) comment #8.  
7.2 Existing unit tests shall pass.  

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: leviathan747/bridgepoint
Branch: 9913_copy_paste

<pre>

 doc-bridgepoint/notes/9913_copy_paste_int.md                                                    | 152 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/MC-Java/java.arc                                                                            |  28 +++++++++++++++++++++++++++-
 src/MC-Java/referential_attribute_util.inc                                                      | 196 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/arc/generate_referential_attribute_util.arc                               |  35 +++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.core/generate.xml                                                              |  32 ++++++++++++++++++++++++++++++--
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Element Packaging/Package/Package.xtuml |  15 +++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/common/IReferentialAttributeManager.java            |  16 ++++++++++++++++
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/util/.gitignore                                     |   3 ++-
 src/org.xtuml.bp.io.core/arc/gen_import_java.inc                                                |   4 ++++
 src/org.xtuml.bp.io.core/arc/import_functions.inc                                               |  74 ++++++++++++++++++++++++++++++++++++++++++++++----------------------------
 src/org.xtuml.bp.ui.canvas/arc/generate_referential_attribute_util.arc                          |  35 +++++++++++++++++++++++++++++++++++
 src/org.xtuml.bp.ui.canvas/generate.xml                                                         |  31 ++++++++++++++++++++++++++++---
 src/org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/util/.gitignore                           |   1 +
 13 files changed, 587 insertions(+), 35 deletions(-)

</pre>

### End
