---

This work is licensed under the Creative Commons CC0 License

---

# Parent task for issues associated with Model Element Move and the 2016 issue list
### xtUML Project Analysis Note

1. Abstract
-----------
This issue is rasied to investigate the issues called out in the acceptance testing performed 
by SAAB for [Model Element Move](2.2) and the [2016 Issues list](2.3).  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9235](https://support.onefact.net/issues/9235) 
This issue serves as an organizational parent task for issues that will be investigated and resolved.  
<a id="2.2"></a>2.2 [SAAB Service Pro - Model Element Move DEI #8793](https://support.onefact.net/issues/8793)  
<a id="2.2.1"></a>2.2.1 [Model Element Move Statement of Work (One Fact Only)](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit#)  
<a id="2.2.2"></a>2.2.2 [One Fact - Model Element Move Test Plan](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8458_model_element_move_tests/8458_Model_Element_Move_Tests.dnt.md)  
<a id="2.2.3"></a>2.2.3 [One Fact - Model Element Move Test Results](https://docs.google.com/spreadsheets/d/1eJmEWtx3EDawwCslxL2MfvaqoJm8JawFnoCTLPuX9SM/edit#gid=1793892663) 
The One Fact test plan was created using a 1:1 mapping of tests from the [Use Cases In The 
Model Element Move Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit#heading=h.bs05sw301j79). The link provided here provides access to all test results from the 
most recent to the very first run of the tests. These results can be seen using the document revision history. Model Element Move was introduced as part of the [BridgePoint v5.8 release](https://support.onefact.net/versions/14) which was completed November 30, 2016.  
<a id="2.3"></a>2.3 [SAAB Service Pro - 2016 Issues List](https://support.onefact.net/projects/service-pro-saab/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=*&f%5B%5D=cf_16&op%5Bcf_16%5D=%3D&v%5Bcf_16%5D%5B%5D=SAAB+2016+List+%28%238505%29&f%5B%5D=fixed_version_id&op%5Bfixed_version_id%5D=%3D&v%5Bfixed_version_id%5D%5B%5D=14&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&c%5B%5D=cf_14&c%5B%5D=cf_16&group_by=&t%5B%5D=) 
This is a link to a query in the One Fact issue tracking system that shows the issues that were part of the 2016 Issue List project.  
<a id="2.3.1"></a>2.3.1 [2016 Issues Statement of Work (One Fact Only)](https://docs.google.com/document/d/1li2mQ-CVW0z5fm0qz6Bxw-zvalL1kPBc6sCgLQc9G8Y/edit)  
<a id="2.4"></a>2.4 [SAAB - Model Element Move Test Plan (One Fact Only)](https://docs.google.com/document/d/1WsYH0_gSdhurP3-jo3oG_w6itWMGnhZ99RcurpJVv44/edit)  
<a id="2.5"></a>2.5 [SAAB Dynamics - Test Results (One Fact Only)](https://docs.google.com/document/d/16YCPUdr2TZVdi92cRvLzog9Nw_UIsI99yheMr9u1HlA/edit)  
<a id="2.6"></a>2.6 [SAAB Aero - Test Results (One Fact Only)](https://docs.google.com/document/d/1AzbJy9dVS2VSmNdBQ4RCtrn3vy21NYwBOEzTq6vdM-I/edit)  
<a id="2.7"></a>2.7 [BridgePoint DEI #8853](https://support.onefact.net/issues/8853) The result of an undo operation is not always the exact same file on disk.     
<a id="2.8"></a>2.8 [BridgePoint DEI #9137](https://support.onefact.net/issues/9137) Address performance issues found profiling 5.2.2 vs 6.0.0.  
<a id="2.9"></a>2.9 [Rename failure caused by reference to a deleted element #9246](https://support.onefact.net/issues/9246)  
<a id="2.10"></a>2.10 [Graphics errors after element move DEI #9247](https://support.onefact.net/issues/9247)  
<a id="2.11"></a>2.11 [Unable to formalize assocaitions to/from shared classes #9248](https://support.onefact.net/issues/9248)  
<a id="2.12"></a>2.12 [Do not allow the data type to be changed in ports #9249](https://support.onefact.net/issues/9249)  



3. Background
-------------
The [Model Element Move](2.2) and [2016 Issue List](2.3) tasks were completed as part of the 
[BridgePoint v5.8 release](https://support.onefact.net/versions/14). The Model Element Move statement of work contains a clearly defined set of use cases, and [the One Fact test plan](2.2.2) contains tests that map directly to each use case. One Fact also created extended tests during the v5.8 test cycle and the [Model Element Move test document](https://docs.google.com/document/d/1WsYH0_gSdhurP3-jo3oG_w6itWMGnhZ99RcurpJVv44/edit) was extended to include these additional test cases.  

For acceptance testing, [SAAB created a test procedure for Model Element Move](2.4) and SAAB performed testing based on that procedure. SAAB also performed "extended tests" during this testing.  The SAAB test results include BridgePoint issues that are not related to the issues under test ([2.2](2.2), [2.3](2.3)). Two different SAAB groups reported results. 
[SAAB Dynamics reported results](2.5), and [SAAB Aero reported results](2.6).  

The goal of this analysis is to clearly call out the SAAB acceptance issues that are specific to the work performed by One Fact ([2.2](2.2), [2.3](2.3)) and to raise tracking issues for these failures that will be addressed for the warrenty associated with tasks performed by One Fact.  To assure the issues are clear to both One Fact and SAAB, the issue associated with this note shall serve as a parent task for issues that shall be resolved under warranty.  

4. Requirements
---------------
4.1 Clearly define the SAAB failures that shall be addressed under warranty.  
4.2 For each defined failure, perform a high-level analysis of the failure case.  

5. Analysis
-----------
This section shall only address failure cases from the reports. Note that for clarity SAAB test identifiers are used below.  
5.1 Analysis of [Issues reported by SAAB Dynamics](2.5)  
5.1.1 Acceptance testing of [Model Element Move](https://support.onefact.net/issues/8793) from Section 2  
5.1.1.1 (2.2.3-N-extended)  - Move after model deletion causes error  
Investigate further. This is a possible warranty issue.  Further investigation under section 6.1 below.  

5.1.1.2 (2.4.1-Z, 2.4.1-Z-extended) Element moved from "not top level" to "not top level" results in graphics errors  
Investige further. This is a possible warranty issue.  Further investigation under section 6.2 below. These test cases are 
grouped together because analysis shows they likely have the same cause.  

5.1.2 Acceptance testing for [Inter-project reference enhancements phase 1](https://support.onefact.net/issues/8557) from Section 4  
5.1.3 (Martin-1.5, Martin-1.6, Martin-1.7, Martin-1.8) Unable to formalize an association to a shared class  
This is a bug in shared classes.  These test cases are grouped together because analysis shows they likely have the same cause. Further investigation under section 6.3 below.

5.1.3 Acceptance testing for [Stop allowing the name of a parameter in a message (operation/signal) in a port to be changed].(8641) from Section 8  
5.1.3.1 (Martin-1.1) The headline issue is fixed, but type is still allowed to be changed.  
Investige further. This is a possible warranty issue.  Further investigation under section 6.4 below.  


5.2 Analysis of [Issues reported by SAAB Aero](2.6)  
5.2.1 VC-2.1.1  Undo does not restore the model to an unchanged state.  
This is a known issue, not new [2.7](2.7). It was not a requirement for the work at hand. A workaround is to replace with head revision.  

5.2.2 Analysis of notes taken by tester, JT, from section 5.4  
5.2.2.1 Undo issues are already called out by VC-2.1.1  
5.2.2.2 Attempt to create an element after a cut and then paste into the new element fails.  
This is by design. If any transation occurs between cut and paste the cut is aborted, and thus paste is not allowed.    
5.2.2.3 Performance issue observed during testing  
Investigation has been performed on this in BridgePoint v6.2 and an issue was addressed. Unable to tell if it is the same issue or not ([2.8](2.8)). No further action at this time.  

5.2.2 Analysis of notes taken by tester, PJ, from section 6  
5.2.2.1 It is observed that PJ reported the same observations as JT, just in a slightly different way.  
5.2.2.2 Not possible to move a component when it has ports and only the component, not the ports are selected  
This is by design.  
5.2.2.3 Renaming a top-level package does not rename the .xtuml file correctly  
Not part of the work at hand. No action.  

5.2.3 Analysis of notes in section 7 (other noteworthy discoveries)  
The items called out here are not part of the issues at hand.  

6. Issues to be addressed
----------------
6.1 [Rename failure caused by reference to a deleted element #9246](https://support.onefact.net/issues/9246)  
6.2 [Graphics errors after element move DEI #9247](https://support.onefact.net/issues/9247)  
6.3 [Unable to formalize assocaitions to/from shared classes #9248](https://support.onefact.net/issues/9248)  
6.4 [Do not allow the data type to be changed in ports #9249](https://support.onefact.net/issues/9249)  


7. SAAB Test Cases
------------------
The following test cases refer to SAAB test cases. In the specific issues raised in the section above these must be modified to refer to One Fact test cases that capture the reported problems such. This is simply here as an aide to the reader to get an idea of the steps to reproduce the problems.  

7.1 references to deleted projects seem to exist after deletion  
SAAB reference: Dynamics: Section 2 - 2.2.3-N-extended  
7.1.1 setup  
* Models in BP_test in git,  
* Model projects :  
  * a) TC_2_2  
  * b) TC_2_2_refA  
  * c) TC_2_2refB  
7.1.2 reproduction steps  
* a), b) and c) in workspace  
* create comp in c) using the If  
* commit to CM  
* delete c) from ws  
* cut If from b) into a) the package there If was first defined  
* Dialog that Visibility fails => Incorrect Error Report  
* Shutdown of BP  
* Delete of workspace  
* Create of workspace and import of a) b and c)  
  * 9a Restart of BP  
* Try to repeat 5 above, same result  
* Delete of c) from workspace  
 
7.2 Graphics errors during move, port not connected  
SAAB reference: Dynamics: Section 2 - 2.4.1-Z and 2.4.1-Z-extended  
7.2.1 setup
* Models in BP_test in git,  
* Model projects :  
  * a) TC_2_4  
  * b) TC_2_4_refA    
7.2.2 Move from not top level to not top level  
* a ) and b) in workspace, Moved a comp to b)  
* moved comp is not ok => Error multiple ports, graphic errors  
* restart  
* still error in moved comp => Error graphic, port not correct connected  
* Revert the complete model  
* Perform 1 again and result according to 2  
* Perform Undo  

7.2.3 Move from not top level to top level  
* < repeat steps above for this senario >  

7.3 Unable to formalize associations to a shared class  
SAAB reference: Dynamics: Section 4 - Martin-1.5, Martin-1.6, Martin-1.7, Martin-1.8  
6.3.1 setup  
* Models in BP_Shared_Classes in git,  
* Model projects :  
  *  Initial_Test  
  *  Initial_Test_refA  
* BP installation configured for component ‘org.xtuml.bp.core/bridgepoint_prefs_opaque_components=true’  

7.3.2  case 1  
* create association between a component defined class and an imported class  
  *  model is ok  
* try formalize the association using the imported class  
* not possible => Error, shall be allowed  
* try formalize the association using the defined class  
* possible, the imported class is updated, also in definition. => Error, class shall be readonly  
* note: This was repeated a number of time under different conditions. Sometimes it were possible to formalize using the imported class, mostly not. Almost all the times it were possible to formalize using the defined class, which affected the imported class  

7.3.3 case 2  
* create association between a component defined class and an imported class  
  * model is ok  
* create a new class and use as an association class  
* try formalize the association  
* Nothing happens  
* Error log indicates unhandled event loop exception  

7.3.4 case 3  
* 1 create super type of the imported class and a subtype of a component defined class  
  * model is ok  
* try formalize the association  
* Formalize dialog is empty  
* model is not ok, shall be possible => Error  
* create super type of a defined class and a subtype of an imported class  
  * model is ok  
* try formalize the association  
  * note: The defined class is possible to select => Error shall not be possible to select  
* select ok,  
* An error is displayed, Transaction: Inheritance Formalize failed . This is correct but shall be prohibited earlier in the phase  


7.3.5 case 4  
* import two classes using an imported package  
* create an association between the class  
* model is ok  
* try formalize the association using any class  
* possible => Error shall not be possible to modify the classes  
* model is incorrect  
 
 
7.4 When issue [#8641](https://support.onefact.net/issues/8641) was resolved it did not consider the data TYPE change.  Data type of a message parameter is allowed to be changed and should not be.    
SAAB reference: Dynamics: Section 8 - Martin-1.1  
7.4.1 setup  
* Models in BP_PR in git,  
* Model projects :  
  * PR_7541  
7.4.2 steps  
* define interface with signal and op with parameter  in a)  
* use if in a component  
* create component reference  
* Try change using component reference in model explorer view, Port-Name, Port-Message, Port-Message-Parameter, Port-Message_Operation-Return type  
* model is ok, not possible change  
* Try change using component definition in model explorer view, Port-Message, Port-Message-Parameter, Port-Message_Operation-Return type  
* model is ok, not possible change  
* Try change using component reference in model property view, Port-Name, Port-Message, Port-Message-Parameter, Port-Message_Operation-Return type  
* model is not ok,  ERROR => possible to change message parameter type  
* Try change using component definition in model property view, Port-Name, Port-Message, Port-Message-Parameter, Port-Message_Operation-Return type  
* model is not ok,  ERROR => possible to changemessage parameter type  

End
---
