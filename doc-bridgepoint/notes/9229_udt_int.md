---

This work is licensed under the Creative Commons CC0 License

---

# Error when creating a user defined type
### xtUML Project Implementation Note

1. Abstract
-----------
The MASL refactor utility is failing on rename of data types that live outside
the component (in the "Shared" package). An error is being logged and refactor
fails.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9229 Error when creating a user defined type](https://support.onefact.net/issues/9229)  
<a id="2.2"></a>2.2 [#9229 Type refactoring not working properly](https://support.onefact.net/issues/9220)  
<a id="2.3"></a>2.3 [#9237 Analyze how to identify MASL public domain data types](https://support.onefact.net/issues/9237)  

3. Background
-------------
The root of this problem is that according to the MASL idiom, "public" domain
types are not within the component (they must be visible to other projects that
use the interfaces), but in a sibling package to the component called "Shared".
This was causing the MASL refactor utility to stumble because it needs to
calculate the fully qualified name of the MASL element to refactor including the
containing domain (component). Because the type is not contained in the
component, a null pointer exception was thrown when the utility could not find
it.

The simplest solution is to use the MASL idiom to search for the domain
component as a sibling element to the "Shared" package in the case of a data
type. This solution is brittle because it requires the packaging idiom to be
strictly followed. At the moment it makes the most to take this simple solution,
however a follow-on issue has been raised to analyze a better solution
[[2.3]](#2.3)

The `XtumlToMaslMapper` class is where the logic of the mappings between xtUML
elements and MASL qualified names is located. This class currently has some
weaknesses in null checking and error handling that will be cleaned up by this
work.

4. Requirements
---------------
4.1 Refactor shall be supported on user data types outside a component  
4.1.1 The refactor utility shall complete without errors on such a case  
4.1.2 The references to the data type shall be appropriately renamed in all
textual MASL  

5. Work Required
----------------
5.1 Add special case to `getComponent` routine for elements not inside a
component. If the element is a data type, a selection is made to find component
definitions sibling to the containing package. If exactly one component is
found, it is assumed to be the domain. If zero or more than one component is
found, null is returned.  
5.2 Changed the flow in `XtumlToMaslMapper` to be null safe.  

5.3 Added code in `MASLSnippetEditor.modelElementReloaded` to consider the case
of the type definition editor  

6. Implementation Comments
--------------------------
6.1 During the course of analyzing the bug, another bug was discovered in the
`MASLSnippetEditor` class. See [[2.1]](#2.1) comments 3 and 5. This code was
introduced to keep open editor buffers up to date when the model was reloaded
[[2.2]](#2.2). The code only considered MASL activity editors and did not
consider the case when the editor was for a type definition. A case was added to
handle this.

6.2 As section 5.1 describes, if there are zero or more than one component
found sibling to the "Shared" package, refactor is silently skipped. A design
was considered that would produce errors in this situation, however the silent
design was taken because a non-MASL model may very validly contain this
situation and an error would be inappropriate.

7. Unit Test
------------

Copied from [[2.1]](#2.1) comment 1

_ - convert SAC_OOA and import into BP  
_ - add new UDT "atype" to Shared  
_ - Edit the data type definition to be  
  ```
  enum (a, b)
  ```
_ - Save, leave type editor open  
_ - Go to model explorer and rename "atype" to "btype"  
R - No errors appear and references to "atype" in the `.int` and `.mod` files
have been updated  

8. User Documentation
---------------------
None

9. Code Changes
---------------
Fork/Repository: leviathan747/bridgepoint  
Branch: 9227_udt  

<pre>

 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/typedefinition/TypeDefinitionEditorInput.java                                  |   6 ++++++
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/document/MaslSnippetEditor.java     |  12 +++++++++++-
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/rename/MaslRenameParticipant.java   |  11 +++++++++--
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/rename/MaslRenameParticipant.xtend_ |   4 ++--
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/rename/XtumlToMaslMapper.java       | 141 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++---------------------------------
 src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/rename/XtumlToMaslMapper.xtend_     |  58 ++++++++++++++++++++++++++++++++++++++++------------------
 6 files changed, 176 insertions(+), 56 deletions(-)

</pre>

End
---

