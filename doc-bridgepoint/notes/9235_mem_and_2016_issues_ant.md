---

This work is licensed under the Creative Commons CC0 License

---

# Parent task for issues associated with Model Element Move and the 2016 issue list
### xtUML Project Analysis Note

1. Abstract
-----------
This issue is rasied to investigate the issues called out in the testing performed 
by SAAB for Model Element Move and the 2016 Issues list.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9235](https://support.onefact.net/issues/9235) 
This issue serves as an organizational parent task for issues that will be investigated and resolved.  
<a id="2.2"></a>2.2 [Model Element Move Statement of Work](https://support.onefact.net/issues/8793)  
<a id="2.3"></a>2.3 [2016 Issues Statement of Work](https://docs.google.com/document/d/1li2mQ-CVW0z5fm0qz6Bxw-zvalL1kPBc6sCgLQc9G8Y/edit)  
<a id="2.3.1"></a>2.3.1 [2016 Issue List](https://support.onefact.net/projects/service-pro-saab/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=*&f%5B%5D=cf_16&op%5Bcf_16%5D=%3D&v%5Bcf_16%5D%5B%5D=SAAB+2016+List+%28%238505%29&f%5B%5D=fixed_version_id&op%5Bfixed_version_id%5D=%3D&v%5Bfixed_version_id%5D%5B%5D=14&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&c%5B%5D=cf_14&c%5B%5D=cf_16&group_by=&t%5B%5D=) 
This is a link to a query in the One Fact issue tracking system that shows the issues that were part of the 2016 Issue List project.  
<a id="2.4"></a>2.4 [One Fact Model Element Move Test Plan](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8458_model_element_move_tests/8458_Model_Element_Move_Tests.dnt.md)  
<a id="2.4"></a>2.4 [One Fact Model Element Move Test Results](https://docs.google.com/spreadsheets/d/1eJmEWtx3EDawwCslxL2MfvaqoJm8JawFnoCTLPuX9SM/edit#gid=1793892663) 
skdjnfkdsnfkndskfndsf  
<a id="2.5"></a>2.5 [SAAB Model Element Move Test Plan](https://docs.google.com/document/d/1WsYH0_gSdhurP3-jo3oG_w6itWMGnhZ99RcurpJVv44/edit) 
SAAB Model Element Move Test Plan.  
<a id="2.6"></a>2.6 [SAAB Dynamics Test Results](https://docs.google.com/document/d/16YCPUdr2TZVdi92cRvLzog9Nw_UIsI99yheMr9u1HlA/edit) 
SAAB Dynamics Test Results.  
<a id="2.7"></a>2.7 [SAAB Aero Test Results](https://docs.google.com/document/d/1AzbJy9dVS2VSmNdBQ4RCtrn3vy21NYwBOEzTq6vdM-I/edit) 
SAAB Aero Test Results.  
<a id="2.8"></a>2.8 [BridgePoint DEI #xxx3](https://support.onefact.net/issues/xxx3) TODO: Add description here.  
<a id="2.9"></a>2.9 [BridgePoint DEI #xxx3](https://support.onefact.net/issues/xxx3) TODO: Add description here.  
<a id="2.10"></a>2.10 [BridgePoint DEI #xxx3](https://support.onefact.net/issues/xxx3) TODO: Add description here.  

3. Background
-------------
(SAAB created a test procedure for Model Element Move.)[2.5]. SAAB performed testing based on that procedure. SAAB also 
performed "extended tests" during this testing. Two different SAAB groups reported results. 
SAAB Dynamics reported results, and SAAB Aero reported results.

It is observed that most of the problems reports really have nothing to do with the issues at hand. The goal of this 
analysis is to clearly call out the issue specific to the work performed by One Fact and to raise specific issues for 
the failures that will be addressed for the warrenty associated with tasks completed.

Model Element move was issue #8793. 
The SAAB 2016 issue list can be seen here: 
https://support.onefact.net/projects/service-pro-saab/issues?utf8=%E2%9C%93&set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=*&f%5B%5D=cf_16&op%5Bcf_16%5D=%3D&v%5Bcf_16%5D%5B%5D=SAAB+2016+List+%28%238505%29&f%5B%5D=fixed_version_id&op%5Bfixed_version_id%5D=%3D&v%5Bfixed_version_id%5D%5B%5D=14&f%5B%5D=&c%5B%5D=project&c%5B%5D=status&c%5B%5D=subject&c%5B%5D=fixed_version&c%5B%5D=due_date&c%5B%5D=cf_14&c%5B%5D=cf_16&group_by=&t%5B%5D=

Note that One Fact's Model Element Move testing was done based on the use cases 
called out in the Model Element Move Statement of work. The One Fact Test procedure that 
describes these tests, with each tests mapping to the Statement of work is 
here: https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/8458_model_element_move_tests/8458_Model_Element_Move_Tests.dnt.md

The One Fact Test Results associated with this procedure, for version 5.8 and each version
before and after is here (see revision history for all results): https://docs.google.com/spreadsheets/d/1eJmEWtx3EDawwCslxL2MfvaqoJm8JawFnoCTLPuX9SM/edit#gid=1793892663

4. Requirements
---------------
4.1 Clearly define the SAAB failures that shall be addressed under warrenty  
4.2 For each define failure, perform a high-level analysis of the failure case  

5. Analysis
-----------
This section is only required if there is no preceding analysis note. If present
it sets out a brief analysis of the problem to be resolved by this design note. Here is an example reference to the Document References section [[2.1]](#2.1)

5.1 Item 1  
5.1.1 Example sub-item
* Example List Element

5.2 Item 2  
5.2.1 Example sub-item
* Example List Element

6. Work Required
----------------
In this section, break out the consequential work (as a numbered list) needed
to meet the requirements specified in the Requirements section. Here is an example reference to the Document References section [[2.1]](#2.1)

6.1 Item 1  
6.1.1 Example sub-item
* Example List Element

6.2 Item 2  
6.2.1 Example sub-item
* Example List Element

7. Acceptance Test
------------------
In this section, list the tests that need to be performed in order to
verify that all the requirements are satisfied. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

End
---
