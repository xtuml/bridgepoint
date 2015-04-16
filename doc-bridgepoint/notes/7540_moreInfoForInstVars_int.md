---

This work is licensed under the Creative Commons CC0 License

---

# Add more information for Instance variables in Variable view such as related instances
### xtUML Project Implementation Note
 

1. Abstract
-----------
Currently, only model class attributes are displayed in the Variables view, 
which is not good since the user needs to use other views, e.g. Session 
Explorer, to find more information about the instance.


2. Document References
----------------------
[1] Issues 135, https://github.com/xtuml/internal/issues/135    
[2] CQ DEI dts0100895768 - Add more information for Instance variables in   
	Variable view such as related instances.  
[3] Design note : < cvs >\Documentation\internal\technical\notes\dts0100895768\dts0100895768.dnt  
[4] CQ DEI dts0101070518 - Group related instances and Pending Events under 
	parent tree node in Session Explorer view
	
	
3. Background
-------------
None

4. Requirements
---------------
see[3]

5. Work Required
----------------
See [3]  

6. Implementation Comments
--------------------------
6.1 In Addition to the added children (Current State, Last Executed Transition, 
and related instances) to the instance, the Pending Events is added as well
to the instance children.

6.2 Unlike what is mentioned the design note, related instances will be grouped under
the association tree element in the variables view. Same things for Pending Events.

6.3 The order of the instance children is changed to match the same order 
in Session Explorer view which is : 
		- Last Executed Transition
		- Current State
		- Attributes 
		- Pending Events
		- Related Instances

6.4 A separated issue is raised [4] to decide if we need to change the instance
children representation for session explorer view, by grouping related instances
under association tree element.

6.5 The proper icons are displayed for the new added instance children (Current
State, Last Executed Events, Pending Events, and Associations)

6.6 The value of variables sets (instance set, related instance set, and Pending Event set) in
	Variables view is changed to display the set length at the beginning of value
	string. This change allow the user to determine the size of the set without
	need to count them manually.
	
Before: 
R1.'Teaches'	3:Subject, 4:Subject, 5:Subject	

After:
R1.'Teaches'	[3]  3:Subject, 4:Subject, 5:Subject	

7. Unit Test
------------
7.1 Debug verifier tests must pass.  
7.2 Although it is mention in [3] that a manual test will be added, but 
	with investigation, it is found that automated test can be done without
	much effort. therefore, a new junit class test is added.  
	The test execution a model in verifier, set breakpoint and execute the 
	model, then inspect/expand elements in Variables view and validate their 
	existence, value, and repetition.


8. Code Changes
---------------
Branch name: < rdmn7540_git135_UpdateBranch>

<pre>

org.xtuml.bp.debug.ui [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/xtuml/
    bp/debug/ui/BPDebugModelPresentation.java
org.xtuml.bp.debug.ui [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/xtuml/
    bp/debug/ui/model/BPDebugElement.java
org.xtuml.bp.debug.ui [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/xtuml/
    bp/debug/ui/model/BPStackFrame.java
org.xtuml.bp.debug.ui [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/xtuml/
    bp/debug/ui/model/BPValue.java
org.xtuml.bp.debug.ui [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/xtuml/
    bp/debug/ui/model/BPVariable.java

org.xtuml.bp.debug.ui.test [bridgepoint rdmn7540_git135_UpdateBranch]/src/
    VerifierTestSuite2.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/
    xtuml/bp/debug/test/InstanceVariableViewTests.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/
    xtuml/bp/debug/ui/test/DebugUITestUtilities.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7540_git135_UpdateBranch]/src/org/
    xtuml/bp/debug/ui/test/execute/VerifierExecuteActionTestsGlobals.java



</pre>

End
---

