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
<a id="2.2.1"></a>2.2.1 [Model Element Move Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit#)  
<a id="2.2.2"></a>2.2.2 [One Fact - Model Element Move Test Plan](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8458_model_element_move_tests/8458_Model_Element_Move_Tests.dnt.md)  
<a id="2.2.3"></a>2.2.3 [One Fact - Model Element Move Test Results](https://docs.google.com/spreadsheets/d/1eJmEWtx3EDawwCslxL2MfvaqoJm8JawFnoCTLPuX9SM/edit#gid=1793892663) 
The One Fact test plan was created using a 1:1 mapping of tests from the [Use Cases In The 
Model Element Move Statement of Work](https://docs.google.com/document/d/1_T4H7StO-VM8zfIFjr-V7VwUQMXML1c7nFJJofU0vGs/edit#heading=h.bs05sw301j79). The link provided here provides access to all test results from the 
most recent to the very first run of the tests. These results can be seen using the document revision history. Model Element Move was introduced as part of the [BridgePoint v5.8 release](https://support.onefact.net/versions/14) which was completed November 30, 2016.  
<a id="2.3"></a>2.3 [SAAB Service Pro - 2016 Issues List](https://support.onefact.net/projects/service-pro-saab/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=*&f%5B%5D=cf_16&op%5Bcf_16%5D=%3D&v%5Bcf_16%5D%5B%5D=SAAB+2016+List+%28%238505%29&f%5B%5D=fixed_version_id&op%5Bfixed_version_id%5D=%3D&v%5Bfixed_version_id%5D%5B%5D=14&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&c%5B%5D=cf_14&c%5B%5D=cf_16&group_by=&t%5B%5D=) 
This is a link to a query in the One Fact issue tracking system that shows the issues that were part of the 2016 Issue List project.  
<a id="2.3.1"></a>2.3.1 [2016 Issues Statement of Work](https://docs.google.com/document/d/1li2mQ-CVW0z5fm0qz6Bxw-zvalL1kPBc6sCgLQc9G8Y/edit)  
<a id="2.4"></a>2.4 [SAAB - Model Element Move Test Plan](https://docs.google.com/document/d/1WsYH0_gSdhurP3-jo3oG_w6itWMGnhZ99RcurpJVv44/edit)  
<a id="2.5"></a>2.5 [SAAB Dynamics - Test Results](https://docs.google.com/document/d/16YCPUdr2TZVdi92cRvLzog9Nw_UIsI99yheMr9u1HlA/edit)  
<a id="2.6"></a>2.6 [SAAB Aero - Test Results](https://docs.google.com/document/d/1AzbJy9dVS2VSmNdBQ4RCtrn3vy21NYwBOEzTq6vdM-I/edit)  
<a id="2.7"></a>2.7 [BridgePoint DEI #8853](https://support.onefact.net/issues/8853) The result of an undo operation is not always the exact same file on disk.     
<a id="2.8"></a>2.8 [BridgePoint DEI #9137](https://support.onefact.net/issues/9137) Address performance issues found profiling 5.2.2 vs 6.0.0.  
<a id="2.9"></a>2.9 [BridgePoint DEI #xxx3](https://support.onefact.net/issues/xxx3) TODO: Add description here.  

3. Background
-------------
The [Model Element Move](2.2) and [2016 Issue List](2.3) tasks were completed as part of the 
[BridgePoint v5.8 release](https://support.onefact.net/versions/14). The Model Element Move statement of work contains a clearly defined set of use cases, and [One Fact's test plan](2.2.2) contains tests that map directly to each use case. One Fact also created extended tests during the v5.8 test cycle and the [Model Element Move test document](https://docs.google.com/document/d/1WsYH0_gSdhurP3-jo3oG_w6itWMGnhZ99RcurpJVv44/edit) was extended to include these additional test cases.  

For acceptance testing, (SAAB created a test procedure for Model Element Move)[2.4] and SAAB performed testing based on that procedure. SAAB also performed "extended tests" during this testing.  SAAB's test results also include BridgePoint issues that are not related to the issues under test ([2.2](2.2), [2.3](2.3)). Two different SAAB groups reported results. 
[SAAB Dynamics reported results](2.5), and [SAAB Aero reported results](2.6).  

The goal of this analysis is to clearly call out the SAAB acceptance issues that are specific to the work performed by One Fact ([2.2](2.2), [2.3](2.3)) and to raise specific issues for these failures that will be addressed for the warrenty associated with tasks performed by One Fact.  To assure the issues are clear to both One Fact and SAAB, the issue associated with this note shall serve as a parent task for issues that shall be resolved under warranty.  

4. Requirements
---------------
4.1 Clearly define the SAAB failures that shall be addressed under warranty  
4.2 For each defined failure, perform a high-level analysis of the failure case  

5. Analysis
-----------
This section shall only address failure cases from the reports. Note that for clarity the SAAB test identifier are used instead of the usual BridgePoint section numbering.
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
5.2.2.2 Attempt to create an element after a cut and then paste into the new element fails
This is by design. If any transation occurs between but and paste the cut is aborted and thus paste is not allowed.    
5.2.2.3 Performance issue observed during testing  
Investigation has been performed on this in BridgePoint v6.2 and an issue was addressed. Unable to tell if it is the same issue or not [2.8](2.8). No further action at this time.  

5.2.2 Analysis of notes taken by tester, PJ, from section 6  
5.2.2.1 It is observed that PJ reported the same observations as JT, just in a slightly different way.  
5.2.2.2 Not possible to move a component when it has ports and only the component, not the ports are selected  
This is by design.  
5.2.2.3 Renaming a top-level package does not rename the .xtuml file correctly  
Not part of the work at hand. No action.  

5.2.3 Analysis of notes in section 7 (other noteworthy discoveries)  
The items called out here are not part of the issues at hand.  

6. Work Required (Issues to be addressed)
----------------
6.1 Dynamics: Section 2 - 2.2.3-N-extended  
TODO: Reproduce and report. If this is a problem, add the test case to the MEM test plan  
6.2 Dynamics: Section 2 - 2.4.1-Z and 2.4.1-Z-extended
TODO: Reproduce and report. If this is a problem, add the test case to the MEM test plan  
6.3 Dynamics: Section 4 - Martin-1.5, Martin-1.6, Martin-1.7, Martin-1.8
TODO: Create a One Fact test issue for these senarios. Reproduce. 
6.4 Dynamics: Section 8 - Martin-1.1  
TODO: Reproduce. Add this test to the test case created for [#8557](https://support.onefact.net/issues/8557)  

7. Acceptance Test
------------------
7.1 Model Element Move Testing
7.1.1 Enhance the One Fact Model Element Move test plan to include the following SAAB test plan tests
7.1.1.1 item 1 
7.1.1.1 item 2 
7.1.1.1 item 3

7.2 TODO: Retest 2016 issue failure case 1
7.2.1 TODO: Point to the One Fact test issue  

7.3 TODO: Retest 2016 issue failure case 2
7.3.1 TODO: Point to the One Fact test issue  


End
---
