---

This work is licensed under the Creative Commons CC0 License

---

# Missing formalism for reflexive non-associative relationship with collapsed referential
### xtUML Project Implementation Note

1. Abstract
-----------

2. Document References
----------------------
<a id="2.1"></a>2.1 [#9323 Missing formalism for reflexive non-associative relationship with collapsed referential](https://support.onefact.net/issues/9323)  
<a id="2.2"></a>2.2 [#9323 dnt](https://github.com/xtuml/mc/blob/master/doc/notes/9323_collapsed_ref/9323_recursive_ref_dnt.md)  

3. Background
-------------
See [[2.2]].

4. Requirements
---------------
See [[2.2]].

5. Work Required
----------------
See [[2.2]].

6. Implementation Comments
--------------------------

6.1 `BuildPropertySource.arc` change

The change in `BuildPropertySource.arc` was introduced to allow the properties
view to correctly display types for baseless referential attributes. Before the
change, the property source did check if a base attribute across R113 is null,
but in the case where no base attribute was found it would simply display the
hardcoded value "same_as<Base_Attribute>". This is bad in many ways, but most
relevantly, it obfuscates any data type that is related to a baseless
referential attribute.  The code has been changed to instead display the name of
the data type associated with the referential attribute in the case of no base
attribute being found across R113.

7. Unit Test
------------
See [[2.2]].

8. Code Changes
---------------
<pre>

Fork: leviathan747/bridgepoint  
Branch: 9323_collapsed_ref  

 src/MC-Java/ooa_schema.sql                                                                                                        |   2 +-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Functions/Context Menu Entry Functions/Context Menu Entry Functions.xtuml |  33 +++++++++++++++++++-------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/Attribute.xtuml                                       | 209 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Model Class/Model Class.xtuml                                   |   6 ++---
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Referential Attribute/Referential Attribute.xtuml               |  91 ++++++++++++++++++++++++++++-------------------------------------------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Subsystem.xtuml                                                 |   4 ++--
 src/org.xtuml.bp.core/sql/context_menu.pei.sql                                                                                    |   6 +++--
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/ui/.gitignore                                                                         |   7 +++---
 src/org.xtuml.bp.ui.properties/arc/BuildPropertySource.arc                                                                        |   3 ++-
 9 files changed, 285 insertions(+), 76 deletions(-)

</pre>

End
---

