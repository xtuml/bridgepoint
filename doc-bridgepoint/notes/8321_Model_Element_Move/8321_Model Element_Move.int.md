---

This work is licensed under the Creative Commons CC0 License

---

# Model Element Move
### xtUML Project Implementation Note

1. Abstract
-----------
In this section, give a summary of the design that this note aims to
describe.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8321](https://support.onefact.net/redmine/issues/8321) 
This is a link to this issue in the issue tracking system.  

<a id="2.2"></a>2.2 [Analysis of Model Element Move #8031](https://support.onefact.net/redmine/issues/8031) 
The [analysis produced by this work allowed]
(../8031_Analyze_Model_Element_Move/8031_Analyze_Model_Element_Move.ant.md) was used 
during the SOW creation to help define the requirements for this project.  

<a id="2.3"></a>2.3 [Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit)  
This is a link to this issue's Statement of Work document.  

<a id="2.4"></a>2.4 [UnitTestGenerator.pl](https://github.com/xtuml/bridgepoint/blob/master/src/org.xtuml.bp.test/UnitTestGenerator.pl)  
This is a link to the BridgePoint utility that is used to generate test suites 
from a defined test matrix.  

<a id="2.5"></a>2.5 [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)  


3. Background
-------------

See the background in [[2.2]](#2.2) as well as the background in the SOW [[2.3]](#2.3).

4. Requirements
---------------
see [Design note for Model Element Move](8321_Model_Element_Move.dnt.md)

5. Work Required
----------------
6.1 Modify cut/paste operation to be analogous to "move" by making it a 
single long-lived transaction. This shall adhere to the ACID properties 
of transactions.  

I introduced a new abstract class, `CutCopyPasteAction extends Action`, and
I modified the  existing `CutCopyAction` and `PasteAction` classes
to extend this new class instead of extending Action directly as they were doing befire this
change. This allowed me to move some common implementation from `CutCopyAction` "up" into this new
class. This allowed PasteAction to know when the user has selected cut vs
copy and to performed the cut/paste in a single transaction that occurs
in PasteAction (previously cut was performed in a transaction of its own). I 
moved the code that was deleting elements out of `CutAction.java::postRun()`
and put this into `PasteAction::run()` at the point AFTER the move has taken place. 
I added an attribute to CutCopyPasteAction named ELEMENT_MOVE_SOURCE_SELECTION that 
holds the IStructuredSelection made by the user during cut. This allows us to
deleted the selection without concern that the user may have pasted some
element under the same model root that it was cut from. It also removes
any other potential problems with lookup by ID that may have problems
because the element IDs are not changed.  In order to prevent the
element IDs from being changed I modified `bp/io/CoreImport` constructors
to pass a new parameter, `boolean createIDsDuringModelImport`. This flag
is used in the generated file `ImportModelStream.java::finishLoad()` to
prevent the code that changes the IDs from running. There was a quite a
bit of fallout from this change because the change was made to an
archetype that generates a lot of different types of import classes. For 
example, this generaates the import classes for both stream and file 
import. This change is specific to the stream import, but the archetype 
generates both.  

Each place that instantiated one of these import class objects had to be 
modified to account for the new parameter. Note that in almost all cases 
the value of this new parameter is set to true, because we DO update the 
IDs in most cases. In addition to this new case just added, the only other 
cases where we were not updating IDs are in model merge.  

6.2 Modify the paste action (`core/ui/PasteAction.java`).  
@see 6.1  

6.2.1 The source element ID(s) shall be used and no new IDs shall be created  
@see 6.1  

6.2.2 The element is created in the target model root.  
@see 6.1 and 6.2.3

6.2.3 This is where requirement 4.2 is handled.  
As described in the design note, the desire is that we could have a situation 
where diff, after move of a model root, would show no changes and thus have a 
good chance for RCS system to handle it as a move. After move, there are changes 
in the target. The specific changes follow.  6.2.3.1 is the biggest hurdle to 
this WRT trying to be RCS friendly. Of course the changeset of the move from 
a diff point of view now that IDs are not changed is very small, but it is a diff. 
However, these diffs do not deter RCS systems that implements move from treating 
this as a move.  
6.2.3.1 The model root has changed. This means, for example, if moving a package 
from under one package to another, the PE_PE.Package_ID changes accordingly  
6.2.3.2 Proxies get updated (when we are able to remove proxies, this change this will go away)  
6.2.3.3 graphics - GD_GE instances contain a path to the location of the ooaofooa model element they represent (GD_GE.represents_path). This path gets updated with the move.  

6.2.4 Where deletion of elements is required (paste is performed to a different model root) 
the deletion of the source elements will occur. The deletion shall occur after the paste to
faciliate 6.2.3.  
@see 6.1  

6.2.5 An attempt to paste to the same location that the copy was made from is considered an 
invalid selection and shall not be allowed.  

TODO: Add implementation detail  

Note that same location does not mean same model root. A move should be allowed to the 
same model root as long as it is not the exact same location inside that root.  

6.3 Reuse the current tree view that shows Model Elements affected by the 
cut/paste operation  
TODO: Add implementation detail  

6.3.1 The dialog shall allow the user to cancel   
TODO: Add implementation detail  

6.3.2 In the type demotion dialog, consider adding text to tell the user to consider turning on IPRs or checking package visibility.  
TODO: Add implementation detail  

6.3.3 As per the SOW [[2.3](#2.3)], the dialog needs to have save and print optons added.  
TODO: Add implementation detail  

6.4 Modify all resolution operations to first search by ID  instead of name  
TODO: Add implementation detail  

6.5 Fix inconsistent proxy paths [[2.4](#2.4)]  
TODO: Add implementation detail  

6. Implementation Comments
--------------------------
NONE


7. Unit Test
------------
TODO: Add implementation detail  

7.1 Use the [[test generation utility]](#2.2) to generate tests for all source and target permutations. Note that documentation for this utility is found in the header of this perl script.  The goal of these generated tests is to assure all requirements are satisfied for each generated test. This assure that requirements are satisfied for each test permutation.  

7.1.1 Create a test matrix that defines all possible "degrees of freedom" for source and target selection.   

7.1.2 Add this matrix to bp.core.test plugin as a new test suite.  

7.1.3 Modify bp.core.test/generate.xml to generate the test suite from the test matrix using the test generation utility.  

7.1.4 Implement the pieces of the suite that the test generation utilitie's results require additional work for.  

7.1.4.1 Each generated tests shall assure that element IDs are not modified during move.  

7.1.4.2 Each generated tests shall assure that move is performed as an atomic operation.  

7.1.4.2.1 After the move a single "undo" restore the model to the state it was in prior to the move operation  

7.1.4.2.2 If the move operation is canceled no changes shall be made.  

7.1.4.3 Each generated test shall assure that its result modifies the minimum number of files necessary.  

7.1.5 Run the suite and assure it passes  

8. User Documentation
---------------------
TODO: Describe the end user documentation that was added for this change. 

9. Code Changes
---------------
Branch name: < enter your branch name here >

<pre>

< Put the file list here >

</pre>

End
---

