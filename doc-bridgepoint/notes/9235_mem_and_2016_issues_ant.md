---

This work is licensed under the Creative Commons CC0 License

---

# Parent task for issues associated with Model Element Move and the 2016 issue list
### xtUML Project Analysis Note

1. Abstract
-----------
This issue is rasied to investigate the issues called out in the acceptance testing performed 
by SAAB for [Model Element Move](#2.2) and the [2016 Issues list](#2.3).  

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
<a id="2.9"></a>2.9 [Rename of an interface without all referred-to projects present referring projects to be downgraded. DEI #9246](https://support.onefact.net/issues/9246)  
<a id="2.9.1"></a>2.9.1 [dts0100841747 - Existing interface references are not updated when the interface is updated via a CM repository](https://github.com/xtuml/internal/tree/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20121102/technical/notes/dts0100841747)  
<a id="2.10"></a>2.10 [Graphics errors after element move DEI #9247](https://support.onefact.net/issues/9247)  
<a id="2.11"></a>2.11 [Unable to formalize assocaitions to/from shared classes. DEI #9248](https://support.onefact.net/issues/9248)  
<a id="2.12"></a>2.12 [Do not allow the data type to be changed in ports. DEI #9249](https://support.onefact.net/issues/9249)  
<a id="2.13"></a>2.13 [In a pessimistic locking system a dialog appears during move that should not. DEI: #9281](https://support.onefact.net/issues/9281)  
<a id="2.14"></a>2.14 [Model Element Move (cut/paste) testing. DEI: #8837](https://support.onefact.net/issues/8837) This issue tracks the running of the One Fact manual test procedure associated with Model Element move.  

3. Background
-------------
The [Model Element Move](#2.2) and [2016 Issue List](#2.3) tasks were completed as part of the 
[BridgePoint v5.8 release](https://support.onefact.net/versions/14). The Model Element Move statement of work contains a clearly defined set of use cases, and [the One Fact test plan](#2.2.2) contains tests that map directly to each use case. One Fact also created extended tests during the v5.8 test cycle and the [Model Element Move test document](https://docs.google.com/document/d/1WsYH0_gSdhurP3-jo3oG_w6itWMGnhZ99RcurpJVv44/edit) was extended to include these additional test cases.  

For acceptance testing, [SAAB created a test procedure for Model Element Move](#2.4) and SAAB performed testing based on that procedure. SAAB also performed "extended tests" during this testing.  The SAAB test results include BridgePoint issues that are not related to the issues under test ([2.2](#2.2), [2.3](#2.3)). Two different SAAB groups reported results. 
[SAAB Dynamics reported results](#2.5), and [SAAB Aero reported results](#2.6).  

The goal of this analysis is to clearly call out the SAAB acceptance issues that are specific to the work performed by One Fact ([2.2](#2.2), [2.3](#2.3)) and to raise tracking issues for these failures that will be addressed for the warrenty associated with tasks performed by One Fact.  To assure the issues are clear to both One Fact and SAAB, the issue associated with this note shall serve as a parent task for issues that shall be resolved under warranty.  

4. Requirements
---------------
4.1 Clearly define the SAAB failures that shall be addressed under warranty.  
4.2 For each defined failure, perform a high-level analysis of the failure case.  

5. Analysis
-----------
This section shall only address failure cases from the reports. Note that for clarity SAAB test identifiers are used below.  
5.1 Analysis of [Issues reported by SAAB Dynamics](#2.5)  
5.1.1 Acceptance testing of [Model Element Move](https://support.onefact.net/issues/8793) from Section 2  
5.1.1.1 (2.2.3-N-extended)  - Move after model deletion causes error  

This issue was not easy to understand/reproduce, and therefore an issue was raised [#9246](https://support.onefact.net/issues/9246). Further investigation revealed that the core of this issue involves making changes to model elements without all referring projects present in the workspace. This is not a new issue. It is not specific to the new move functionality.  The BridgePoint preferences "Synchronize with library" and  "Synchronize references" were introduced to help deal with such situations [2.9.1](#2.9.1). These options are still required in the presence of the new move functionality.  There is a very simple sequence that allows this problem to be observed. This is described in [#9246 comment 7](https://support.onefact.net/issues/9246#note-7). No futher action shall be taken.  

5.1.1.2 (2.4.1-Z, 2.4.1-Z-extended) Element moved from "not top level" to "not top level" results in graphics errors. This is a warranty issue. These test cases are grouped together because analysis shows they likely have the same cause. See section 6.1 below for additional information.  

5.1.2 Acceptance testing for [Inter-project reference enhancements phase 1](https://support.onefact.net/issues/8557) from Section 4  

5.1.3 (Martin-1.5, Martin-1.6, Martin-1.7, Martin-1.8) Unable to formalize an association to a shared class  
This is a bug in shared classes.  This is a warranty issue. These test cases are grouped together because analysis shows they likely have the same cause. See section 6.2 below for additional information.  

5.1.3 Acceptance testing for [Stop allowing the name of a parameter in a message (operation/signal) in a port to be changed].(8641) from Section 8  

5.1.3.1 (Martin-1.1) The headline issue is fixed, but type is still allowed to be changed.  This is a warranty issue.  See section 6.3 below for additional information.  

5.2 Analysis of [Issues reported by SAAB Aero](#2.6)  

5.2.1 VC-2.1.1  Undo does not restore the model to an unchanged state.  
This is a known issue, not new [2.7](#2.7). It was not a requirement for the work at hand. A workaround is to replace with head revision.  

5.2.2 Analysis of notes taken by tester, JT, from section 5.4  

5.2.2.1 Undo issues are already called out by VC-2.1.1  

5.2.2.2 Attempt to create an element after a cut and then paste into the new element fails.  

This is by design. If any transation occurs between cut and paste the cut is aborted, and thus paste is not allowed.    

5.2.2.3 Performance issue observed during testing  

Investigation has been performed on this in BridgePoint v6.2 and an issue was addressed. Unable to tell if it is the same issue or not ([2.8](#2.8)). No further action at this time.  

5.2.2 Analysis of notes taken by tester, PJ, from section 6  

5.2.2.1 It is observed that PJ reported the same observations as JT, just in a slightly different way.  

5.2.2.2 Not possible to move a component when it has ports and only the component, not the ports are selected  

This is by design.  

5.2.2.3 Renaming a top-level package does not rename the .xtuml file correctly  

Not part of the work at hand. No action.  

5.2.2.4  (section 6 - very last sentence) reads: The non-update of proxies even when other file contents are update gives strange CM history. If you need to update proxies, those should be correct.  

An intermediate version of Model Element Move contained an implementation that caused proxies to be updated during move. The final implementation did NOT update proxies during move. The work done to remove the code that searched for proxies BEFORE move for the sole purpose of reporting them to the user in a pessimistic locking scenario was promoted by the following [pull request on November 17, 2016](https://github.com/xtuml/bridgepoint/pull/242)  

This issue shall be investigated further to assure it is resolved. See section 6.4 below for additional information.  

5.2.3 Analysis of notes in section 7 (other noteworthy discoveries)  
The items called out here are not part of the issues at hand.  

6. Issues to Resolve
----------------
6.1 [Graphics errors after element move DEI #9247](https://support.onefact.net/issues/9247)  
This problem appears to be resolved already in BridgePoint v6.2 see [#9247 comment 1]( https://support.onefact.net/issues/9247#note-1). This shall be fixed.  

6.2 [Unable to formalize assocaitions to/from shared classes #9248](https://support.onefact.net/issues/9248)  
The problem was that the requirements called out by the work done for shared classes did not include formalization. 
The issue requirements can be seen in section 4 of the [#8061 implemenation note](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8061_ipr_classes.md). This shall be fixed.  

6.3 [Do not allow the data type to be changed in ports #9249](https://support.onefact.net/issues/9249)  
The changes made fixed this issue for the Model Explorer view, but the test did properly consider the ability of the user to modify these artifcats from the properties view. This shall be fixed.  

6.4 [In a pessimistic locking system a dialog appears during move that should not #9281](https://support.onefact.net/issues/9281)  
As described in the analysis section, this issue shoould be resolved by the [pull request on November 17, 2016](https://github.com/xtuml/bridgepoint/pull/242). It observed that prior to this pull request being serviced, the One Fact Model Element move test plan contained an explicit test to assure this dialog DID appear when a pessimistic lock CM was in use This test is identified by section [7.13 of the Model Element Move Test Plan](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8458_model_element_move_tests/8458_Model_Element_Move_Tests.dnt.md). When the pull request referenced above was serviced, test 7.13 was marked obsolete.

Issue [#9235](https://support.onefact.net/issues/9235) has been raised to add this test back to the test plan, but to modify it to assure the dialog does NOT appear in this situation.    

7. Test Cases
------------------

7.1 Assure the graphics reconciliation of port references is working properly
Run manual test [#8801](https://support.onefact.net/issues/8801). 

7.2 Assure that formalization is allowed to/from shared classes.
Run manual test [#9250](https://support.onefact.net/issues/9250) 

7.3 Assure that data type in a port is not allowed to be changed in the properties view.  
Run manual test [#9245](https://support.onefact.net/issues/9245)  

7.4 Assure that in a pessimistic locking RCS a dialog does NOT appear during move that warns the user that proxies shall be modified.  
Run [Model Element Move (cut/paste) testing. DEI: #8837](https://support.onefact.net/issues/8837). The specific task for this is defined in section 7.13 of the test.  

End
---
