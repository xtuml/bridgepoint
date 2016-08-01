---

This work is licensed under the Creative Commons CC0 License

---

# Overloaded Activites in BridgePoint
### xtUML Project Implementation Note

1. Abstract
-----------
This implementation aims to solve two remaining issues relating to overloaded
activities:

1. Overloaded activities cause filename collisions in the BridgePoint editor.
(see [[2.2]](#2.2))  
2. MASL project wiring is dependent on activity names, and thus is broken by
overloaded activities. (see [[2.3]](#2.3))  

2. Document References
----------------------
<a id="2.1"></a>2.1 [#8510 parent issue](https://support.onefact.net/issues/8510)  
<a id="2.2"></a>2.2 [#8511 issue for name collision](https://support.onefact.net/issues/8511)  
<a id="2.3"></a>2.3 [#8512 issue for project wiring](https://support.onefact.net/issues/8512)  
<a id="2.4"></a>2.4 [#8322 design note for overloaded activities](https://github.com/xtuml/mc/blob/testing/doc/notes/8322_overloaded_functions/8322_overloaded_functions_dnt.md)  

3. Background
-------------
Overloaded activities had been previously analyzed, designed, and implemented
([[2.4]](#2.4)), however, the design processed revealed remaining issues that
needed to be addressed. This implementation addresses those remaining issues.
The reader should read and understand the design note before continuing.

4. Requirements
---------------
4.1 Overloaded MASL activities shall open in a file with a unique name  
4.1.1 The name shall be determined only by persisted model data without
dependence on the state of the editor  
4.1.2 A naming scheme shall be chosen that is sufficiently intuitive to a user
browsing the filesystem manually  

4.2 Project terminator activities shall be properly wired in the case with
overloaded activities

5. Work Required
----------------

xtuml/bridgepoint

5.1 Add case to allow duplicate names of model elements that can have a signature  
5.2 Update and extend 'getSignature' operations in BridgePoint to allow for
getting a signature with tagged parameters or positional parameters  
5.3 Update the import helper to link activities in projects with their
corresponding activity files by signature instead of by name   
5.4 Update MASL text plugin to name files by signature instead of simply by name  

xtuml/mc

5.5 Add code to handle mapping project activity files to activities by signature
and not simply by name  
5.6 Update `x2m` to follow the new naming convention using signatures  


6. Implementation Comments
--------------------------

6.1 The naming scheme chosen for MASL activities is an extension to the current
naming scheme, but replacing the name of the activity with its full signature.

For port messages: `<port name>_<return type>_<activity name>_<param1 type>_ ... .masl`

For bridge operations: `<EE name>_<return type>_<activity name>_<param1 type>_ ... .masl`

For all others: `<return type>_<activity name>_<param1 type>_ ... .masl`

For example, an message on a port `Operator` with signature
`void start_process( integer process_num )` would have a filename
`Operator_void_start_process_integer.masl`. Suppose there was another message
that overloads `start_process` with signature `void start_process()`. Its
filename would be `Operator_void_start_process.masl`.

6.2 It should be noted that the above naming scheme is large and somewhat
clunky. As we look forward, we would like to see activities consolidated into
one file for each "scope" -- that is each package has one file containing all
the functions, each port has one file containing all the messages, each class
has one file containing all the operations, etc. Because of this vision and
because the above scheme fulfills requirements 4.1.1 and 4.1.2, it is quite
acceptable as an incremental advancement.

7. Unit Test
------------

7.1 MASL activity editing  
7.1.1 For each of the following model elements:  
  * Bridge operation  
  * Function  
  * Port message  
  * Class operation  

7.1.2 Create two activities with the same name, but different signature  
7.1.3 Open both activities with the MASL editor and verify that both activities
can be edited independently without interfering with one another  

7.2 Round-trip import/export  
7.2.1 Import an example model with overloaded activities  
7.2.2 Verify that the activities can be opened with the MASL editor  
7.2.3 Export the model to MASL  
7.2.3 Check the `export.log` file in the `masl/` directory and verify that all
the activities were copied successfully  
7.2.3.1 Diff the output MASL directory with the source MASL directory to verify
the activity files passed through the import/export process unchanged  
_Note: perform this test with both domain and project models making sure that
you have coverage of each type of activity listed in 7.1.1_

8. Code Changes
---------------
Branch name: leviathan747/bridgepoint/8510_overloaded_activities

<pre>
 src/org.xtuml.bp.core/arc/create_rename_action.inc                                                               |  13 ++++++-
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Interface Operation/Interface Operation.xtuml  | 110 ++++++++++++++++++++++++++++++++++++++++++++++----------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Component/Interface Signal/Interface Signal.xtuml        | 105 +++++++++++++++++++++++++++++++++++++++++++----------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Bridge/Bridge.xtuml                               | 111 +++++++++++++++++++++++++++++++++++++++++++++++----------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Domain/Function/Function.xtuml                           | 109 +++++++++++++++++++++++++++++++++++++++++++++----------
 src/org.xtuml/bp.core/models/org.xtuml.bp.core/ooaofooa/State Machine/State Machine Event/State Machine Event.xtuml                | 105 +++++++++++++++++++++++++++++++++++++++++++----------
 src/org.xtuml.bp.core/models/org.xtuml.bp.core/ooaofooa/Subsystem/Operation/Operation.xtuml                      | 111 +++++++++++++++++++++++++++++++++++++++++++++++----------
 src/org.xtuml.bp.io.core/src/org/xtuml/bp/io/core/ImportHelper.java                                              |  31 +++++-----------
 src/org.xtuml.bp.ui.text/src/org/xtuml/bp/ui/text/masl/MASLEditorInputFactory.java                               |  99 +++++++++++++++++++++++++++++++++++++++++++-------
 9 files changed, 647 insertions(+), 147 deletions(-)
</pre>

Branch name: leviathan747/mc/8510_overloaded_activities

<pre>
 model/maslin/gen/masl2xtuml.c                                                       |  98 +++
 model/maslin/gen/masl2xtuml_S_UDT_class.c                                           |  16 +
 model/maslin/gen/masl2xtuml_ooapopulation_class.c                                   | 208 ++++--
 model/maslin/models/maslin/lib/masl2xtuml/masl2xtuml.xtuml                          |  30 +-
 model/maslin/masl2xtuml/ooapopulation/ooapopulation.xtuml                           |  94 ++-
 model/maslout/models/maslout/lib/xtuml2masl/maslout_imported/maslout_imported.xtuml | 695 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-
 model/mcooa/models/mcooa/ooaofooa/Domain/Domain.xtuml                               |  48 +++++-
 7 files changed, 1108 insertions(+), 81 deletions(-)
</pre>

End
---

