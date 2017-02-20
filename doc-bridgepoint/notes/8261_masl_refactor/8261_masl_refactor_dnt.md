---

This work is licensed under the Creative Commons CC0 License

---

# MASL rename refactoring with Xtext
### xtUML Project Design Note

1. Abstract
-----------
Update MASL actions when xtUML structural elements are renamed.

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8261 MASL automatic reference maintenance](https://support.onefact.net/issues/8261) Headline issue  
<a id="2.2"></a>2.2 [#8261 analysis note](8261_masl_refactor_ant.md)  
This analysis note enumerates the elements that need to be handled, and
evaluated a pull request that was open for updates to the Xtext editor. The pull
request was rejected because there was more work to be done before it was
mergeable. This note describes all of the design including the Xtext editor
design.  
<a id="2.3"></a>2.3 [#9138 MASL refactor test](https://support.onefact.net/issues/9138)  

3. Background
-------------
None

4. Requirements
---------------

4.1 The Xtext editor shall rename all references to supported model elements
when the element is renamed in BridgePoint  
4.1.1 The Xtext editor shall only modify files in the `models/` directory as
part of the refactor  
4.1.2 The Xtext editor shall not touch the `masl/` directory as part of the
refactor  
4.2 The following model elements shall be supported by this work  
4.2.1 Data type  
4.2.2 Structure member  
4.2.3 Enumerator  
4.2.4 Domain  
4.2.5 Domain service  
4.2.6 Domain service parameter  
4.2.7 Terminator  
4.2.8 Terminator service  
4.2.9 Terminator service parameter  
4.2.10 Relationship phrase  
4.2.11 Object  
4.2.12 Object service  
4.2.13 Object service parameter  
4.2.14 Attribute  
4.2.15 State  
4.2.16 State parameter  
4.2.17 Event  
4.2.18 Event parameter  

5. Analysis
-----------

5.1 Types

When a type name changes, every "typeref" type that is based off this type must
be renamed manually by the user to reflect the change.

5.2 Structure members and enumerators

Since structure members and enumerators are defined within a MASL editor, the
built-in MASL refactoring mechanism can handle them without any extra work.

5.3 State parameter

State parameters are determined by the event parameters in xtUML. Renaming and
event parameter will rename the corresponding MASL parameter for any state which
as an entering transition assigned to that event.

6. Design
---------

7. Design Comments
------------------

8. User Documentation
---------------------

9. Unit Test
------------

See [[2.3]](#2.3)

End
---

