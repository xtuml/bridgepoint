MSD Weekly Meeting Minutes
--------------------------
* This document was originally maintained in [SVN]
    (http://wv-svn-01.wv.mentorg.com/svn/tenmile/trunk/documentation/review_minutes/SMA_weekly_minutes.txt).
*   The issue for tracking changes to this was initially 
    [Bugzilla issue 4055](http://tucson.wv.mentorg.com/bugzilla/show_bug.cgi?id=4055).
    It was then changed to  
    [Clearquest issue dts0100563933](http://dtsweb.wv.mentorg.com/cqweb/#/dtr01/dts01/RECORD/dts0100563933&noframes=true&format=HTML&recordType=Defect).
* In January 2013 the document began being maintained here on github.  Issue #30 is the github tracking issue now.


---

    Date:     date
    Start:    time EDT
    End:      time EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Item 1 -** text


### Marketing Status
**Item 1 -** text

 
### Customer Support Status
**Item 1 -** text

---

    Date:     Dec 18, 3013
    Start:    12:00 EDT
    End:      12:45 EDT
    Elapsed:  45 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
#### holiday vacation plans
* Cairo office is closed on 7 Jan.
* Travis London: Travis: Dec 23 - Jan 4
* Keith: Dec 23 - 27
* Bob: Dec 23 - 27
* Dean: Dec 20 - 31st 
* Cort: Dec 21 - Jan 4th
* Campbell: Now - Dec 31st

Note: everyone above will be monitoring email

#### MC-PaaS Update (Keith)

###### PaaS Intro Meeting (Keith, Bob, Dean, Subba, Willy)
- Keith gave a PPT presentation discussing the MC-PaaS prototype that relied on rsync
 to shuttle data from client to build server and layed out some phase2 requirements
- Willy shared a presentation that discussed some of the technologies he has built
 the SystemVision PaaS simulator on.
- In a follow up meeting Willy gave an live demo to Keith, Bob, and Dean


###### MC-PaaS Phase 2 Done:
- Client and server updated to transfer files via HTTP instead of rsync
- Client-side implementation now completely Java-based, got rid of cygwin dependencies from phase 1
- Server now supports multiple simultaneous builds

######  MC-PaaS Phase 2 TODO:
- Productize MC-PaaS plugin.  Clean up TODOs, read some settings from preferences
- Change Export Builder to not include OAL Instance data
- User authentication.  Collaborate with Willy as he is ahead in this area.
- Secure file transfer, either via the user auth middleware or another mechanism
- Per-user usage tracking on the server

#### Made in Japan 2013 (Cort)

###### Thurs:
- MGJ Office
  - Fujitsu and Yazaki meetings with account teams
  - AE training
  - FXAT prep (with Ichimasa and Watanabe)
  
###### Fri:
- Sony LSI (on-site)
  - "Help us expand into other projects and disciplines."
  - Training class at was not a "canned classes", it was built on the requirements of the moment.  The goal was to help them expand their usage.
  
###### Mon-Fri:
- FXAT in Yokohama, 5 days of training
  - 8 engineers + 1 manager + sporadic visits from others
    - Converted their models from old BP format to the new format
    - Worked on these models and taught how to update these models to work with the newer version of BridgePoint (Component-based vs the old Domain-based models)
  - dinner on Thurs w/ FXAT was positive for Mentor, FXAT wants to expand


### Marketing/Sales Status
#### Tier 1 Package for Ericsson (Dean)
* In Sept/Oct Ericsson made a commitment to move BridgePoint to a more prevalent production role
  * Since then discussions have been in progress to determine how to best handle this ramp-up
  * this tier-1 package is what is being offered
  * Over the 5 months discussion has been focused on how to help with this ramp-up
    1) Eductiion/training - Updated current course material tailored to their industry and even their specific needs
    2) Rapid response for any tool enhancement requests (prioritization for Ericsson SRs)
    3) Project management/engineering process help - They would like to work with John Wolfe on this

  * Timeframe is to focus enginnering in the first quarter.  
  * From a business point of view, this will likely not come in until later in the year, but this is still being negotiated.  Software versus service deals are what are causing this delay.


#### Ericsson Feedback/Response (Cort)
* Dec 12th Serge met with Ericsson and we have all seen the feedback from this.   It was in the form of a presentation that Ericsson gave to Serge.  Ericsson bascially says BP is the best tool availble, but it has many problems they want to see fixed and they want commitment that issues will be addressed.

* Serge says we had opportunity for $700K and now it is 1/2 of that.  The reason is that there is a large training component.

* If we do win the tier 1 and the expansion that brings 1 - 1.4 million that would make our numbers for 2014 all by itself.  Therefore we could consider an Ericsson focus.  The options around this are currently being worked on by Cort.



#### Upcoming businessiness (Jayne)
* Model package from Ericsson
* Sony Renewal

#### Upcoming marketing (Jayne)
* A January promotion for BP is being done through Tech Brief.  There was a good result from this company with SystemVision that brought it some new good leads, and we are hopeful for the same with BridgePoint.

* There was a new bubble of acivity on xtUML.org during Cort's trip to Japan that was seen

 
### Customer Support Status (Robert Rice)

- Cleaning up a flurry of Sony issues, which are now resolved with the exception of one ER that John Wolfe wanted to look into

- Working with Erik at Saab to get a "user archetype not run issue" solved

- Working on two critical Ericsson SRs describing problems with Verifier and large numbers of init functions: they mention slow performance at load, and non-sequential execution of OAL code 

---

    Date:     Dec 11,2013
    Start:    12:00 EDT
    End:      12:01 EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Dean

**This meeting moved to online forum:**

### Engineering Status
**Keith:**
 Assisted with Saab and Japanese support issues as well as working on the Phase 2 implementation of MC-PaaS (Model Compiler Platform as a Service)
 
**Bob:**
 Assisted Cort by creating a simple model compiler that works against sequence diagrams.   Currently, working on an model import parser bug involving model merge, and building the BP 4.1.4 demo which has a end-date of 20130331.

**Travis:**
Working on dts0100836686, provided a screenshot to SAAB.  SAAB requested further work on it.  Currently trying to determine a way to display the tree as they requested.
Investigated Sony build issue.  The problem is that generator does not support UTF-8.

**Nehad:**
I am working on tooltip enhancement request. The enhacements are to change focus to the tooltip, scrollbars and may be resizable tooltip windows as well. JDT tooltip is a very good example of what we want. Unfortunately, JDT tooltip is not reusable and coupled with text editor, and we need one can be used with GEF . I found more than one implementation appraoch, and investigating estimated amount of work needed for them

### Marketing Status
**Dean -** 
This week Cort is in Japan providing training to FujiXerox and sales training to MGJ AEs. Campbell has completed the xtUML training class at Ericsson and Serge will be visiting with management to establish a dialog on BridgePoint production usage. Efforts continue to complete the description of the Ericsson Tier 1 package and negotiate the list of deliverables.
 

---

    Date:     Nov 13, 2013
    Start:    time EDT
    End:      time EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Keith:**
- The delivered version of BridgePoint that we just finished was 4.1.0
- Then we immediately bumped the development version to 4.1.1
- Keith created xtuml editor branch and brought in the changes we had in our 
  CVS branch over to git. 
- Now we have a base branch for xtuml editor ready to go, Dean is 
  coordinating the release of that.
- Bob has created new demo version of BP 4.1.4 
- The current development version is BP 4.1.5

**Cort:**
- We have a chance for potential revenue from Ericsson if we put together tier one 
  package.
- Dean is working with the account team along with engineering to put together 
  the proposal.
- It will likely impact engineering in non-development ways.
- If we land business to get the division revenu, we will be focusing the
  engineering resources on the creation of training materials and examples.

- Heba Has the first integration that slipped off the the release because it 
  touches the meta model and would have affected the testing.
  But there's a branch that can be used to communicate this new capability.

- 2013 Japanese tour, We sold some BP licences to Fuji Xerox 10 years ago 
  and it was a success.
- We sold them some some licenses this year and as part of that they get trained
  and some help Upgrading their models and their model compiler.
- So there is one week worth of training and effectively consulting that is going 
  on Japan in December and couple of other visits to Sony, Mitsubishi and possibly
  Fujitsu.

- We need to finish the open source code split, we need to make it so, someone 
  beside a mentor graphics engineer can build BP because we have people  
  who possibly want to add features like Sony and Ericsson.

- Campbell has some first steps done with MC-java and will continue down that path.
  That will help having a server based model compiler to allow developers who edit 
  the model and want to translate to MC-Java.
- The first prototype of this will likely be an MC-3020 with BridgePoint model 
  compiler server that promotes a credentials dialogue when build is clicked.
  The model compiler runs on the server and the results are transferred back.

- Fuji Xerox are getting some of their really old models updates as part of their 
  training class. Cort is investigating what it takes to get those upgraded.

### Marketing Status
**Dean:**
- Starting at the end of this week first occurrence of a regular news letter that
  will be shared with xtuml.org members. 
- They also call for action language tutorial.
- Also next Monday will be the field update for Bridge Point 4.1.
 
### Customer Support Status
**Robert:**
- We still have issues with "dynamic arrays" and determining their exact length.
  It seems that a newly created dynamic array has the length of one which is wrong
  Ericsson has realized that and we might need to revisit this again.

- Our model compiler has troubles with the Japanese character set. 

---

    Date:     October 30, 2013
    Start:    7:00 GMT+2 (Cairo)
    End:      7:30 GMT+2 (Cairo)
    Elapsed:  30 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, Nehad, Heba (online)
            Dean, Robert, Jayne

### Engineering Status
**Bob:**  
 - Testing is started, Travis and Nehad working on  
 - There are 3 issues remains ( Heba, Cort, and Bob)  
 - Two issues got pushed off ( Saab and Sony issues )  
 - Saab issue: is a feature they only want ( changing parameter name anywhere in model) - dts0100917381  
 - Sony issue: too much work - dts0100974806  
 - Skip any test related to model compiler until we have release candidate

**Keith** 
 - We are almost ready to tag 4.1  
 - update doc gen  with model based model compiler  
 - master build is currently very close to a release candidate  

**Cort**
 - Working on model compiler, it is almost delivered
 - Note need to be reviewed for promotion, and manual testing and testing against customers models

**Travis**
 - Currently working on testing   
 - Ericsson report has  31 test cases, and issues have been raised with the failures, and Ericossn is asked to prioritized them  

**Nehad**
 - Working on manual testing

**Heba**
 - Reviewed design note note dts0100629397dnt.md  
 - going through the final review check list to get it to promotion  


### Marketing Status
**Dean**
- At the end of last year, we were no longer able to sell  modeling package.. new title is  Tier 1 service packages are the replacement  
- Tier one services are an entension for consulting, which engineering is able to be involved with. Extending revenue sources beyond  SW sales by charging customer for features and unique tool support  
- Tier one service time frame is 12 monthes  
- Tier 1 services do not include a commitment os support from CSD. Instead, all deliverables are supported through consulting or engineering efforts. Only if the feature becomes part of a standard BridgePinnt release will CSD be required to offer support.  
- Potential business opportunity: between $250,000 and high end >$500,000  

 
### Customer Support Status
**Robert**
 - There is a raised issue about usablity with Japaness character  

---

    Date:     October 23, 2013
    Start:    11:26 EDT
    End:      12:16 EDT
    Elapsed:  50 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, Heba, Nehad (online)
           

### Engineering Status
[10/23/13, 11:26:06 AM] Cortland Starrett: model compiler:
Archetypes done and testing complete under BP 4.0.  
Finishing packaging of the compiled MC today.  
Testing on the compiled MC tomorrow.  
Testing with a master build through the end of the week.  

[10/23/13, 11:29:00 AM] Campbell McCausland: dts0100949572: nested component wiring promoted.  
dts0100992921: dll unloading promoted.  
dts0100936030: VIEC UUID handling, dnt reviewed, code completed, test model in creation.  

[10/23/13, 11:35:20 AM] Robert Mulvey: release:  
-The release blocker list is down to 10 issues.  All but 1 are assigned and being worked on.  The one that is not was waiting for customer feedback (dts0100917381).   We have the feedback, but still need to determine what, if anything we can do for it now.  
-We can start some of the manual testing in parallel with  the remaining issues as soon as someone frees up to do this testing.  We will put the system in the ready to test state today.  
issues:  
dts0100942480 #74 -Link with Editor problems in progress  

[10/23/13, 11:37:36 AM] Keith Brown: dts0100951499 - comparison of component references in verifier doesn't work.  Work in progress.  DNT review today. Possibly ready for promotion late today.

[10/23/13, 12:09:02 PM] Travis London: I am working on the Sony "assembly connector" issue.  The code is done just need to finish process on it.  I am also including dts0100974806 in this work.  It hopefully will be done today.

[10/23/13, 12:14:43 PM] Robert Mulvey: FYI, I am planning to kick a build today to send to Ericsson for testing.  Our regular issue status meeting with them is tomorrow.

[10/23/13, 12:14:52 PM] Nehad A. Mohamad: I am writing a design note for dts0100942497

[10/23/13, 12:16:46 PM] Heba Khaled: implementing a work around for storing the svx channel configuration in the pereferences store


---

    Date:     October 16, 2013
    Start:    12:00pm EDT
    End:      12:25 EDT
    Elapsed:  25 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**MC Update - testing progress -** 
Next version will have an MC update. Updated MCs include C, C++, and SystemC for source, as well as the binary
MC model-based are being tested.

**Release Status of 4.1 Hercules -**
Release ship date is scheduled to be Nov 1. Leaves a couple weeks for development and last-minute bug fixes. Have
19 current issues that we are working to include, and likely none that we can push out. All but 4 issues have had some
analysis (Bob), but there is quite a bit of work left. Travis has been working directly with Marijan at Ericsson
on his issues.

### Marketing Status
**Ericsson Operations -** 
Ericsson projects are getting good visibility--Montreal is proceeding well.

**Japan Operations -**
Sony is seeing good ROI on their use of the Sysc model compiler. Looking to expand from 3 or 4 projects in the next 6-8
months--possibly looking to double their use or more. They have been looking into BridgePoint 4.0 licensing, including
attempting to defeat "linger". 

FX has again become a BP customer after updating their perp licenses. They are facing the challenge of updating their
dated models to the 4.0 release. This work is slated to be completed by early April next year. Training new users and
model migration are the needed tasks here.

Yazaki is a Tier 1 supplier for Toyota and is using BP in a pilot project in the MISRA 'C' MC space. This
project is scheduled to start in February '14 and proceed for 8-9 months. Potential revenue for this year. They have
been tasked with developing a model-driven design flow for Toyota.

**Trade Shows and Campaigns -**
IESF Detroit - Automotive show with a large MGC (BP and others) presence, as well as IBM Rhapsody. Very good traffic
and lots of interest in the model-driven design flow and model compilers. Presentation on model-driven design was well
received with good questions afteward. Total attendance was 613 people.

SAE Aero Montreal - Was not as active as IESF. Local host (Bombardier) pulled many attendees due to schedule conflict.
Polarsys is a new Eclipse-based safety-critical application for the embedded space.

Jayne - Other promotions and whitepapers are in the works. These are being done in exchange for qualified leads. A new
piece of collateral showing BP-SVX connection is being created. A presentation on BP and SVX product release will be
delivered to the sales force for BP 4.0.6 and SVX 5.10.2. Ten new members on xtuml.org, and keep looking at ways to 
integrate existing and new users there more.

### Customer Support Status
**Field Status -**
Robert - Many BridgePoint issues on the back burner with Agilent. These have all been reported previously on the
github issue tracker--Martin is just reporting these on the SupportNet system so they aren't lost. Sony continues to
fight licensing, though no defects have been reported so far with 4.0.6. CSD initiatives have mandated that (for now) 
support work is a secondary priority while new revenue opportunities are identified and initiated. This may eventually
help BP as it can free up Engineering and Marketing from training delivery (maybe).

---

    Date:     October 2nd, 2013
    Start:    12:00 EDT
    End:      12:35 EDT
    Elapsed:  35 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**4.0.6 Release -** 
This is now complete and available on SupportNet. Mainly fixes for licensing issues.

**4.1 Hercules Release -**
The target date for this is November 1st immediately following the quarterly release blackout. Working backwards
from that date means that we have 2 weeks of development time before code freeze and start of pre-release test
cycle. The issues listed against Hercules are all needed by customers and so cannot be slipped into the next
release. We must therefore all be working on Hercules related items to ensure we hit the date. In addition to
the issues listed agfainst Hercules we also need to move to the latest version of the Mentor standard installer.

**OAL Training Videos -**
These new resources are now linked from xtUML.org. They are found from the home page under Learn > Tutorials >
Action Language Tutorial (Series). They are designed to make training easier to deliver by allowing students to
learn at their own pace. This frees up the instructor to spend more time providintg targeted support to
individual students. Topics are short, between 1 and 3 minutes, allowing students to easily locate any subject
matter quickly. This organization also allows people to use them as a reference resource. The plan is to build
up a library of tutorials over time. It was reported that the appearance of these materials has driven lots of
new subscriber activity on the site. Keith suggested that a single OAL cheatsheet would be a useful addition to
our support collateral.

### Marketing Status
**Trade Shows -**
There is lots of trade show activity in September and October.

SAE AeroTech in Montreal ran from September 24th-26th.
The booth was extremely busy at times. There were representatives from all the major aerospace manufacturers
present. One interesting initiative was the formation of an organization to identify open source tool flows for
safety critical systems.

IESF in Detroit took place on September 19
This conference had an automotive focus. There were 1000 registrants that led to 665 attendees. There were two
presentations that touched on BridgePoints capabilities, one of them presented by our own Dean McArthur. Dean
reported attendance at his session was good, around 30 individuals. There were lots of questions afterwards,
leading to a 20 minute question and answer session in the corridor outside the presentation room. Much of this
discussion was about model compilation. This seems to be an area that is misunderstood so perhaps we should
prepare some marketing collateral to better explain the benefits and demonstrate how open it is. Jayne commented
that we managed to make IBM uncomfortable by BridgePoints proximity in the booth area.

Ericsson are holding their annual internal Solutions Conference next week. There will be a session focussing on
successes with BridgePoint.

**Opportunities -**
Having closed the Fuji-Xerox deal last week, attention is now focused on completing a deal with Sony.

### Customer Support Status
**Sony Licensing issue -**
Sony asked why a licensing issue SR they had raised had been closed while the issue still appeared to be present
in the latest release. Their original issue was related to a 1ms license check out that BridgePoint performed to
establish which model compilers were available to the customer. Together with the license 'linger' feature, this
was causing Sony to run out of model compiler licenses. The SR was closed because the issue has been fixed by
using a license 'peek' feature. Unfortunately it seems the customer is still experiencing the same symptoms.
Ichimasa-san and Dean will work with Sony to get the problem resolved. It may well turn out to be a license
server configuration issue.

---

    Date:     September 25, 2013
    Start:    12:00 EDT
    End:      12:40 EDT
    Elapsed:  40 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Robert, others...

### Engineering Status
**visit with Northrop Grumman in Azusa, CA (Cort) -**

This training came up rather suddenly.  They had been using Matlab, and a bit of Vista.  They were not satisfied 
with how long it look to create the functional models they need.   They are interested in BridgePoint as a higher-level
tool that may help provide the solution.   We provided initial training and are continueing to work with them to 
provide support to help them move forward with their project.

There are currently no BridgePoint licenses at Northrop Grumman.  We are hoping to introduce 
BridgePonit into the upcoming Northrop Gruman license renewal that happens before the end of the year.

**patch release - 4.0.6 -  (Keith) -**

The release is scheduled for delivery to Ireland team on Friday (9/27/2013).  The version has been bumped and 
Keith is working on the release notes.  We are putting in one more issue (Nehad's formalization
wozard work).

**training courses in Europe (Campbell) -**

1) Preparing for a 4-day trainign course in Budapest (Ericsson) in October.  

2) More training at Saab is scheduled for in November.

3) Ericsson wants 2 more training sessions one befoe the end of the year and another at the beginning of 2014.


**Ericsson ramping up (Bob/Cort) -**

The 4.0.6 release is geared toward Ericsson.  It contains issues that they identified as critical to expansion. The 
4.0.6 contains most of what Ericsson requested.  The 4.1 release which will follow in about 1 month contains the rest
of what they requested.


Ferenc met with John Wolfe and explained that in the next 18 months there will be
significant growth.   There has been a management decision that is driving this.  The decision made
is that all products must use one of 2 modeling solutions:

Bridgepoint

Conformic 

This is what is driving the additional training.  Ericsson is training a
key champion in each of the product development  groups.

Jan has setup a meeting that includes Wally Rhines that describes the Duci plans, so there
will be visibility to this directly to Wally.


### Marketing Status
**IESF Detroit (Cort on behalf of Dean) -**

Dean reports a good amount of interest.  Dean will report more when he returns.

**SAE Montreal info (Cort on behalf of Jayne) -**

Jayne and Dean are at SAE in Montreal.  There is more BridgePoint 
interest than anticipated.  Dean will report more when he returns.

**Fuji-Xerox deal (Cort) -**

Just made a deal for a little over 100K over the weekend.  It finally came through.  It will likely 
result in some additional training.



 
### Customer Support Status

**Robert Rice ** There is a request from Saab to implement a UDT as constant.  Nothing else has come in.


---


---

    Date:     September 11
    Start:    12:00 EDT
    End:      12:40 EDT
    Elapsed:  40 minutes
    Present:  Bob, Keith, Travis, Campbell, Heba, Nehad

### Engineering Status
**Bob -** 949572 (Verifier execution and delegation binding problem).  Also working on interfacing with customers.
  Customer meetings have gone pretty well.  Saab has now just pushed out 3.7 into production.  They reopened a few
  issues found during their testing.  We also had an Ericsson customer meeting.  They seem pretty happy with the progress
  of their issues.  We are using a google doc to maintain a list of their highest priority items.  We are pushing them
  to own the document.  We are making good progress on their issues.  

**Keith -** Working on two issues reported by Ericsson with Audit Realized Bindings.  Analysis is done.  Initial ideas 
  for fix have been tried and consulted with Campbell.  Design note is about ready for review.  Also have worked on some
  customer support items.  

**Campbell -** Running supported unit tests against MC-Java.  Found some incorrect behavior in the parser that affects
  MC-3020 as well.  Is testing a proposed fixed.  Need to test with MC-3020 as well.  Expects the Unit tests to pass.  

**Travis -** Model integrity tool is promoted as of yesterday.  Now working on state machine merge again.  Has received
  feedback from Ericsson.  Working through this feedback.  

**Nehad -** got feedback from the team about the dialogs.  We have decided not to generate the dialogs with every BP
  build.  Updating the design note and planning to re-review it.  

**Heba -** working on SVX client.  Migrating to work with the new SVX API.  Showed a small demo of the latest 
  functionality.  The rest of the team expressed interest in learning more about the fucntionality.  

### Marketing Status
**None**  

### Customer Support Status
**Robert -** Completed a beta of a method that Ericsson can use to simultaneously edit and CLI build of a project.  It 
  contains and example of an ANT-based build process.  dts0100974055 is the issue.  The stuff Robert created is in
  ``` \\wv\dfs\bridgepoint\field_data\dts0100974055 ```  

---

    Date:     August 28
    Start:    9:00 MST
    End:      9:20 MST
    Elapsed:  20 mins
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Bob -** 

Release is not moving quickly.  We lost Heba and Nehad due to Egypt issues.
Focusing on CQ pending issues for the release.  Updating process documents to
handle interfacing with CQ and GIT Issues.  Meeting with SAAB, the issue list
has not changed but they reopened a few.  The promotion that should have
addressed them did not.

**Nehad -**

Back to work after a one month break.
Picked up and finished work for dts0100835691, working on finishing automated
testing.
Will pick up automated test work for compare editor.


**Keith -** 

Campbell's changes had conflicts with outstanding work.  License key
passing had to be reworked, which was in the same area of work for code split. 
An enhancement was added to prevent generating data when not necessary
(in pre-builder).  There were issues found here that have been worked through.
Automated tests have been added.  Need to redo manual testing for windows and
linux.  Will need to redo the code review for the issue.  Entered an OSS request
for upgrading to eGit 1.2.

**Travis -** 

Working on model integrity tool.  Work is complete with the exception of
dangling references.  CLI merge is currently calling this tool after every
merge.  Will add UI version of the reporting tool.

**Campbell -** 

Last week we promoted the first step to add infrastructure for splitting the
code base.  Now merging changes for MC-Java into GIT.  All engineers need to
run an updated engineering environment (latest promotion).  Next bit of work is
to split the code and update MC-Java.  There are some outstanding issues that
affect existing model compilers, these should be updated before we replace the
dev environment.

### Marketing Status
**Dean -** 

September will host a number of presentations.  Helping the Japanese team with
licensing upgrading.
 
### Customer Support Status
**Robert -**

It has been quite lately, had one question on how to include default external
entities.  

Looking at issues that were reopened (2) from SAAB meeting (not fixed in 3.7
promotion).  

Martin has encountered problems with testing the new licensing.  Martin was
provided with work arounds that he was not completely satisfied with.  

Keith: Provided patches to Agilent for most issues.  Going to provide a fix for
sharing composite licenses.  We will provide an engineering release before
Martin's licenses expire.  

---

    Date:     August 14, 2013
    Start:    12:00 EDT
    End:      12:20 EDT
    Elapsed:  20 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, Heba, others...
            Dean, others ...

### Engineering Status
**Keith -** we are up and running with git.  We will flip the switch to make CVS read only this week.  The xtuml/doc
and xtuml/docmg repositories will be deleted soon.  Cort will create xtuml/generator soon and we will move the source
code into there.  Bob reports that a branch build with git worked fine for him.  

**Bob -** Sony hit an issue in our JLC implementation.  Our call was doing a checkout/checkin sequence to 
"test" for a license.  Corporate license team provided a new mechanism to test for licenses that does not do
a checkout/checkin.  We still have similar behavior in generator.  It does this to validate a good connection
to the license server.  We are not planning to change this right now.  Bob did a branch build with the new
behavior.  We will put this through promotion process.  

**Cort -** Sony is pouring over the license log.  We are trying to be responsive to their concerns.    

**Keith -** Agilent has 2 issues, a CLI Build problem and a 4.0 licensing problem.  Keith and Bob have worked with
Martin to resolve the problems.  With a proper setup we have shown 4.0 keys to work.  We just need to get their 
configuration set up to achieve this success. We have provided patched plugins for the CLI Build issue.  It works now
on my system, but not on Martin's.  Provided another patched plug-in with more instrumentation to help debug.  Still
trying to resolve.  

**Heba -** The situation in Cairo is concerning but she and Nehad are safe and working from home.  

### Marketing Status
**Dean -** 
- Jayne in Washington at mil-aero trade show.
- Working with Sony account team to solidify how they want to sell the products.  Sony is not a fan of linger, and wants
  to explore alternative approaches.
- There are a group of shows upcoming in September and October.  Plans and presentations are being created.

 
### Customer Support Status
None.


---

    Date:     August 7, 2013
    Start:    noon EDT
    End:      12:20 EDT
    Elapsed:  20 minutes
    Present:  Bob, Travis, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status

See marketing status.


### Marketing Status
**Sony Licensing -** Sony is anxiously looking at using BridgePoint 4.0 on 3 projects.
They want to share the model compiler licenses among roughly 15 designers.  However, they
are not satisfied with the linger option.  Also, during their testing, they found that
in a grid environment, our licensing checks out a different license for different machines.
Consequently, they are asking for an alternative licensing/packaging approach.

In addition, during the "check availability" of licenses, a license is actually checked out.
An SR/DR has been filed for this "check availability" option.  Robert is chasing down
licensing people.  Bob is assigned to the ER/DR.

**Agilent Linux CLI -** At Agilent, the engineers use a mixture of Windows and Linux and a
mixture of interactive and command line -based builds.  The CLI option (in Linux) was not
selecting the correct build configuration in some cases.

Keith has created a fix and delivered a patch to the customer.  Bob is promoting the fix
into our repository (using git).

There is also testing/configuring of 4.0 licensing that needs to be tested on site.

**ASC Team -** The ASC team is focusing on Audi and will be transitioning after that.  We will
get specifics when more information is available.

 
### Customer Support Status
**realized code interfacing -** Campbell is working on an issue with the xtUML/Java/C interfacing.
It looks like SAAB is seeing similar/related issues.

---


    Date:     July 31, 2013
    Start:    12:00 EDT EDT
    End:      12:30 EDT
    Elapsed:  30 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**github migration**   
The new git repositories are up and running.  We are now ready for engineers to switch their 
branches from cvs to git.  Engineers are now moving their branches to git.   No more 
promotion will be made to cvs head at this point, all promotions from this point on will be to git master.
Additionally, nightly builds will now be done from git.  

We will create a git Issues item for each CQ issue so we can provide check-in comments to git.   

Keith has created a tag for this starting point for git.  Campbell pointed out we should apply a similar tag to cvs.  

Bob's concern is the loose coupling of git Issues and CQ.  We are creating git Issues for each CQ item we are working on,
but this is duplication and the 2 are not tightly coupled.  

Keith pointed out that we do not have a mechanism to force the tagging of a commit as we do with cvs.  However 
with should be using #<issue> with each item, and we will work to enforce this in the future.   If you use job:<id> don't 
hit enter, make it 1 line.  The reason is it makes the web view history more valuable.


### Marketing Status
**Sony Issue about the model export license "linger" - Update from Sony** 
Robert has been working with Sony, and has tried actually not to mention it to Sony.  
He has directly MGJ on how linger works, and is now waiting for the actual feedback from 
a Sony face-to-face about this issue and some other issues.  Robert has been interfacing 
with Yagi-san (he is an end-user).   Yagi-san has been suspicious of the licensing for
quite a while because he "debugs" using license logs.  We have told them you can really not
debug from the licens log, but he there is an issue from his perspective that "something
looks fishy".   John Wolfe points out that we really want the Sony account team to handle
the dicussion of this with Sony, it should not be Robert.   John W, asks to be copied because
Global Accounts now owns the Sony account.  

Cort points out that we have a sticky problem here because with the free editor it is possible
for hundreds of users to use a single translation license.   We just need to assure we have fair
and equitable licensing.  Sony does not use Verifier.  

We are monitoring this and will know more after MGJ meets wioth Sony.


**Jayne - BP numbers for this quarter**   
We are a little over $530K.  We are slightly under plan, but feel strongly that Q3 will bring us 
back up over plan because we have more coming from Ericsson and Agilent (and a few others).


### Customer Support Status
**DLLs loaded from JNI are not unloaded** Robert needs some help on a problem about not unloading DLLs that have been 
loaded in by JNI.  He will be asking for help from Campbell on this.  

---

    Date:     July 17,2013
    Start:    12:00 EDT
    End:      12:32 EDT
    Elapsed:  32 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort,
            Dean, Robert

### Engineering Status
**Bob -** completed packaging up a version of Wine for easy installation on top of BridgePoint Linux. User 
 Experience Group has completed guidelines and will be sharing them with engineering teams. Actively working on 
 SRs from Ericsson and Saab with concentrated effort on dts0100949572 

**Keith -** nearing completion of build process migration. Process has uncovered several Windows 2008
 issues which are delaying completion. Currently, investigating Agilent SR related to CLI and licensing.

**Campbell -** Port of MC Java has completed sufficient testing as to be ready for promotion. Promotion expected
 to start next week.

**Cort -** Supporting customers and marketing activities consuming most of his time. These efforts are slowing down
 Model-based Model Compiler work which reached a major milestone and is showing a 3-5x speedup. This effort has
 identified some bugs in existing mc3020 design which will be removed when MBMC is released. 

**Travis -** implementation of element ordering support in Compare&Merge is complete. A build is expected to be sent
 to Ericsson for review next week. Project to automate testing of the CM functionality is on-going.
 
### Marketing Status
**Dean -** Agilent and MGJ have installed BP 4.0 and the impact of the new licensing strategy based on 
 the Linger option was noticed immediately. Feedback so far indicates mild to strong displeasure with this approach
 and MGJ has now nicknamed it "Tricky" licensing. Further exploration of alternative licensing strategies will need to
 be discussed.

 
### Customer Support Status
**Robert -** No items at this time

---

    Date:     July 10, 2013
    Start:    12:00 EDT
    End:      12:25 EDT
    Elapsed:  25 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Keith -** New build server and build setup on github in xtuml-internal repo. Committing
 build artifacts to SVN to aid in rebuilds. Scripts are working to pull data from SVN and
 github, beginning testing full builds on new build server. Eclipse base and MIMIC installer
 will remain on local SVN.

**Dean -** BP for SVX. Heba has started to move to create a Verifier connection to SV via SVX.


### Marketing Status
**Jayne -** Trade shows, trade shows, trade shows! John Vargas is presenting at an unmanned
 systems conference in D.C. Dean will be doing a booth demo in September. IESF regional events
 in India and Asia will have a Mentor presence. No SLE presence at IESF Europe. Meeting July 23
 to discuss BP 4.0.0 changes.

**Dean -** Fielding lots of customer presentations (Sandisk, etc.), and working closely with 
 Walter Gude (Field AE) to get SV and BP in accounts. Working to advance a BP<->SVX<->SV solution 
 flow at customer accounts and looking for opportunities. Ericsson ramping up production project 
 using BP--created a new team spread between Sweden, Hungary, and Montreal, Canada. Training class 
 in Montreal of 12 students and was well received. Still working through config management
 issues with compare/merge. Working to transition L3 from Visio to BP for diagram capture--hit
 slowdown because of missing UML concepts in BP.

 
### Customer Support Status
**Robert -** Answered several 3.7.0/4.0.0 questions from Sony. Working through an issue at 
 Ericsson where images in BP help are not displaying in the Eclipse internal web browser.
 Images display fine in external. Helped Sony work around a crash in the Eclipse internal
 web browser and got them set up using an external (system) browser. Submitted 979019 for 
 an Agilent issue where user can't edit interface connections between component refs.

---

    Date:     June 26, 2013
    Start:    12:00 EDT
    End:      time EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Campbell -** MC-Java now on new schema and fully translating all parts of BP.  Passes
 smoke test.  MC-Java still only supports SPs.  Will investigate whether we want to move 
 to generic packages now or not.  
 
**Keith -** Working on migrating source code to github.  

**Travis -** On FTO.  Bob reports Travis is still working on merge related issues.  

**Nehad -** Helped Travis.  Created automated test suite.  Picked up 835691 and looking
 at a reimplementation.  Working through issues with new development machine.
 
**Heba -** Working on Verifier interface with SVX.

**Bob -** Gemini issue list is Ericsson heavy.  This is probably right from a business 
 point of view.  Packaging wine for linux to help Sony.  Looking at issue that allows 
 placing a component within a parent component.  Also looking at 949572 from Ericsson, 
 a verifier startup problem.  Ongoing work with user experience group.  Setting up
 SLE-wide guidelines to improve our products.
 

### Marketing Status
**Jayne -** No new updates

**Dean -** Ericsson has kicked off new project.  Audi project has started.  Expect
 lots of new user questions on SupportNet and xtUML.org.  Please provide timely
 responses.

**Keith -** L3 Camden still using BP for documentation projects.  May do a short MC
 training class focusing on teaching them how to extract data to enhance the documentation.
 
### Customer Support Status
**Robert -** Thinks there are about 12 new Ericsson users in Canada.  Robert will try to
 create xtUML.org forum items for common questions and answers.


---

    Date:     June 12, 2013
    Start:    9:00 MST
    End:      9:28 MST
    Elapsed:  28 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Heba** Working on SVX BP integration.  Working to capture svx signals, Campbell and
Abdelhalim.

**Nehad** Working on Merge cell editor testing.  Had development environment issues,
worked them out.  Will be working on merge testing of attribute values
(through the copy right to left buttons).

**Campbell** Helping with the SVX interface to BP, dealing with requirements.  Working on the
MC-Java port, it currently translates all of core except for the specialized
archetypes (context menu model).

**Travis** Working on testing merge.  There are three categories to test: creation,
deletion, and referential changes.  Currently working on a generated approach
that will test all possible creations and deletions.  The process is slow going
due to specialized test setups.  These include things like setting up
creation/deletion for New Based Attribute, where you cannot delete but must use
the Set As Derived Based Attribute actions. 

**Bob** Working on the 4.0 release.  Adding fixes that were not schedule but will help
the customer.  Currently working on testing the changes.  Promoted the 4.0 work
to HEAD.  The release build is coming today.  We should update our development
workspaces with this build.  Working with Ericsson, trying to move progress
forward with the top issues list.  Working with the SLE user experience group,
created some guidelines.

**Cort** Working with Ericsson, Lockheed Martin, L3, Agilent.  The model compiler work
stalled due to customer interaction.  Progress is continuing now.

**Keith** Supporting L3 for a custom document generator.  Adding features for the document
generator.  These features can later be added to BP.  Working on 4.0 testing and
build release candidate.  Working with to handle support NET upgrade.

Release should be out by Friday.  After which we will begin preparing to move
code from CVS to GitHub.  This will allow us to remove specialized packages from
our development models.

### Marketing Status
**Dean** Mentor had multiple shows that BP did not attend, but BP came up in discussions.
This lead to some leads for BP.  Opportunities came from Ericsson and SAAB.
Later this month we will be at the Philadelphia show.  All of SLE will be
featured.  There will be training at Ericsson.  There is kick off training and
support at AUDI around June 24.  There is more activity in July for building
models for testing.  AUTOSAR flow should be completed around July.  At L3 there
is On-going activities, mostly custom document generation.  The account team
would like to setup a training class (executable models) for L3.  At L3 salt
lake city, system C flow is being included in the Vista flow.  Japan xerox is
progressing, looking for a pilot project.  Sony is making good progress.
Waiting on ROI report.

**Jayne** We have eight new users on xtuml.org within the last 3 weeks.  Sales requested
our presence at the Philadelphia booth.  The IBM show lead us to that request.
We will be present at the demo station at the Unmanned Vehicle show.  Europe is
requesting abstracts and to have a presence.  We will also be present at the
Detroit show in September demoing BP/ARXML.

 
### Customer Support Status
**Robert** Dealing with Ericsson, mostly related to CLI build/merge.  MGJ is requesting
assistance relating to UI changes in 3.7.  There are storage issues at Sony.

4.0 testing of the expected workflow for xtUML editor and BP is looking good.
Waiting to test CLI in this workflow.
---

---

    Date:     May 29, 2013 
    Start:    12:00 EDT
    End:      12:50 EDT
    Elapsed:  50 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, others...

### Engineering Status
**4.0 release status (Bob)** The changes are present in the release rbanch.  We updated
GLS in order to use the new JLC's linger option and we are now having some problems at
startup with the initialization of the new MGLS.   We are working on this trouble 

**Release "G"emini (after 4.0)  planning (Bob)** The List is hardening.  The "Open Issues For The Pendin Release" 
CQ query shows the current list.  There will be changes over the next week as we get updated priorities from Ericsson.
	
**MC-Java progress on porting to modern OAL packages. (CMcC)**	Working on updating MC-JAva to work 
with a "modern" generated schema instead of the copy that had been used. Need to be able to 
"partition" the build because of generator 4gb limit.
Working on a way to work around this by using a partitioning scheme with 
a prebuilder-like utility.
	
**Status/plan for Merge work (Travis/Nehad)** Created 16 merge issues, only 4 are non-test issues.  
Many of the test issues can be combined. The top issue we are working on is one that helps 
resolve an issue of false conflicts.  This is Ericsson's top issue.  These 16 are issues we raised, 
we are waiting on the Ericsson feedback.

**SVX integration (Heba)** Has a working prototype of 2 components communicating together.  Cort 
asked for the working prototype.  There was a review of the analysis, there were major observations 
so it will be re-reviewed.  Hopefully this Friday.
	
**model-based MC update (Cort)** Making progress.  Hoping to have somting that runs and can be shownin 2-3 weeks time.
RSL is now mostly action language formated.


### Marketing Status
**Ericsson update (from yesterdays regular customer support call) (Bob/Dean)** There have been two calls with Ericsson.  
One was the regular support call the other more focused on moving from the pilot projects to production.  They 
have done some release testing on 3.7.  So far the testing has been positive.   Much of the suport call was 
also about moving from pilot project to production work (they are anxious to do this).  They request quicker 
turn-around of builds and with the builds a list ofthe issues fixed.  We will use the "Ready for Release - External" CQ
query to provide this information with the release we give them.  We also are working with them to update 
their top-issues list.
	-They are requesting traning in Montrial.  Dean is working on this.
	-There is oppotunity at the end of the Montrial project to ramp up to other production.


**Lockheed engagement (Cort)** We have an engagement.  It involves Gary Shubert at Lockheed who is a 
very experienced modeler.  There are more meetings coming next week, so this is moving forward.

**L3 updates (Dean/Keith)**
	1. Salt Lake

		* Opportunity for BP to be part of system engineering solution.
			-focus on data management (context)
			-Engineering disgrams (sysml)
		* About to go into a pilot project
			-They have not yet indicated the vendors.  We are waiting
			-Feedback is that we are not likly to be the first vendor, but we are 
			getting feedback so that we can prepare for our turn
	2. Camden

		* They are just about wrapped-up.  We built a small MC document generator.  
		  They are using it.  We are getting positive feedback.
		* May have post-project report next week.
 
### Customer Support Status
**Recent Agilent bugs (Cort/Bob)** Martin at Agilent has updated to 3.7. He also moved to a new 
machine and new server.  He is also creating a Jenkins server.  He has hit several issues.  He mode of 
operation is to raise lots of issues in git, and then if it is higher he escalates to CQ.

**Robert Rice** Lots of customers are downloading and integrating 3.7. Entering  SRs for merge issues from Travis and
working with Marijan from Ericsson, Bob, and Travis to obtain the latest top issues list from Ericsson.  

---

    Date:     May 22 2013
    Start:    noon EDT
    End:      time EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**4.0 Release progress -** A new Release Candidate branch has been created. We are re-applying licensing changes to HEAD. Dean has a list of changes. A few are easy, splash screen and welcome pages. Also some Eclipse category changes. Some help pages also need to be reworded.
Bob reported that ClearQuest is updated so that Fornax aligns with release 4.0. Everyone else should be working on Geminii issues. We will be performing compatibility checks between the xtUML Editor and BridgePoint. We must make it clear that the user must be careful to upgrade and not share models at different persistence levels. Also, 4.0 users cannot use 3.x licenses.  

**SAAB Aerospace -** SAAB has downloaded 3.7 into their test environment and will be testing over the next week. 3.7 will be moving into the production environment in the next two weeks. SAAB will likely bypass the 4.0 release unless there are significant fixes.

**SAAB Dynamics -** Erik Wedin is running into the 4GB memory limit. We are providing a workaround today.

**L3 Camden -** Tom Holbrook and John Vargas are handling this account. They are doing System Engineering with Sequence Diagrams and Components. We are having weekly engineering meetings to ensure timely support is available. They are looking at custom document generation with a document release in June. Over all they are happy with the tool and our support.

**L3 Salt Lake City -** This site is renewing its development tooling. We are in a competitive situation with NoMagic and Rhapsody. They have been a little slow to engage, but are close to moving to a pilot project of 3 month duration. There was an internal presentation to the Vice President. They are interested in integration with Vista. We are awaiting word on participation in the pilot.

**Other -** we are changing our packaging and licensing as well as doing a promotion including a road show. This will take place in late June, early July. There may be some training coming up at Fuji Xerox.

### Marketing Status
**Newsletter -** There will be a new customer newsletter going out this week which highlights release 3.7.

**xtUML.org -** We are working on the next promotion plan for this. Search engine optimization is being looked at, as well as the possibility of adding a chat feature to the site. There were some issues with the Amazon web service due to accounts being merged incorrectly.

**DAC -** Subba Somanchi is delivering a paper on model based design. 

**Japan -** Ichimasa san reported young engineers using BridgePoint in a robotics contest. We need to get a quote from Fuji Xerox as the sponsor of the winning team.
 
### Customer Support Status
**Activity -** There was not a great deal of support activity to report. Previously, we had several enhancement requests from Ericsson and SAAB.

**Technical Notes -** We need to create some technical notes on License Management, Integrating the xtUML Editor into an existing BridgePoint installation and a 3.7 to 4.0 migration guide. The last could have very similar content to the release notes. Supportnet needs to carry details of the licensing changes. Robert Rice will follow up on this.

**JNA -** The example JNA integration demonstrator was handed over to SAAB. There has been no feedback as yet. We need to capture this as a video presentation and also check the demo into a project on GitHub. 

---

    Date:     May 15, 2013
    Start:    noon EDT
    End:      time EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Nehad, Heba, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**3.7 Released Today -** Keith reported that 3.7 is released and available.  Note that SupportNet is in the middle of an upgrade.  So, customers cannot access the release directly, yet.  We will make the release available to customers through an ftp site.  Also, the C source archetypes get delivered this way.  
Keith reported that the "turn-on" date for the supportnet update is Friday.

**xtUML Editor Following Quickly -** Bob has begun preparing the delivery of the xtUML Editor compatible with 3.7.  Bob is merging in the 3.6.4.  It should be done by the end of the day.

**4.0 Schedule/Plan -** Cort reported that we will follow 3.7 quickly with 4.0.  4.0 will be functionally equivalent but will use the new licensing scheme that leverages the unlicensed OSS xtUML Editor.  The target release date for 4.0 is May 30.

**Compare/Merge Extensions and Testing -** We have delivered the requested Compare/Merge functionality in 3.7.  However, there are some extentions/enhancements that would make it significantly better.  We need additional testing as well.  We have automated testing for comparisons but not for all of the merge situations you can get into.  These tests need to be added.  
Compare/Merge is done and is available as an editor.  Some customers want access to the merge through the configuration management system.  We need to insert BridgePoint Compare into a specific configuration management step to avoid false positive differences.  
Travis will first work on configuring git to use the command line compare.  
Another step will be to integrate the TEAM Logical Model -based interface.

**BP/SVX Pause/Resume -** Heba had started analysis of BridgePoint with SVX before the release push.  She set it aside and will now pick it up again.  She started by reading the manuals and following the instructions.  So, then she made a producer/consumer model and did some prototyping.  She plans to finish the analysis note by the middle of next week.

### Marketing Status
**Campbell at SAAB -**  Campbell is training at SAAB this week in Linkoping.

**L3 Camden -** Keith delivered training a couple of weeks ago and has since been supporting their work.  There were about 5 engineers in the training.  One engineer is taking point on revisioning models inside of BridgePoint.  Mentor and L3 are having weekly conference calls to stay on track.  This team does a lot of analysis using Sequence Diagrams.  The next step is to get them executing their models and begin proving the value of xtUML using the Verifier.  The sales team is setting up follow-on training.

**L3 Salt Lake City -** Dean reported on a large business opportunity at L3-SLC.  This deal may involve our whole portfolio of tools.  The systems engineers are highly analysis oriented.  We are emphasizing virtual lab work.  Yesterday, they learned about Vista.  They will want to learn about the System-C model compiler.  They also want to build test benches at each step along the way and re-use those tests.  
We are strongly positioned to run/execute their systems from an early stage.

**Lockheed -** Dean also reported that a systems modeling opportunity is being worked at Lockheed.  More to come.

**The Other Firm -** Jayne reported that we have not heard from TOF since we put in our requests for live chat and forum update settings.

**Forecast -** Jayne summarized the forecast with Agilent coming in and Ericsson being the big one this quarter.

 
### Customer Support Status
**Brianne at L3 -** Robert has been working with Brianne to help her along and capture some requested enhancements.


---

    Date:     04-24-2013
    Start:    12:00 EDT
    End:      12:26 EDT
    Elapsed:  26 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Updates on Saab -  Dean** 
- That was on a short call, many of Saab engineers were on conference today presenting a paper.
- Saab's paper is highlight BP and model based development.
- Saab is interesting in status of BP 3.7, and what is expecting, and they are looking forwards to Editor splits, and wants being aware of this.

**L3 Training - Keith**  
- Campbell and Dean are cleaning up the training material, replacing SP-related material.
- Keith picked up from there and added some more modules: domain-specific data types, creating documentation, organizing the model.  Updating other modules for System modeling story.
- Presentation will be on Tuesday , Wednesday.

**polymorphic events with imported classes - Nehad**
- Development and testing are done.
- Preparing issue for promotion phase.



### Marketing Status
**Item 1 -** text
 
 
### Customer Support Status
**Robert** 
- Ericsson had problem with  realized components, which is synchronous threads issue when having two  realized components and Campbell is looking at it.
- Licensing issue with Yagi San because of the license server that can not rely on to determine how many licenses are checkout.
 

---

    Date:     04-17-2013
    Start:    12:00 EDT
    End:      12:40 EDT
    Elapsed:  40 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, Nehad
    Dean, Robert
                                
### Engineering Status
**Testing of the 3.7 Release candidate - Travis, Bob** 
- Continuing to update test suite to remove unnecessary tests. This effort has improved test coverage and continues to reduce testing time.
- Ongoing effort is expected to consume much of next week.
- Promotions including the completion of merge are waiting for test suite cleanup to conclude before proceeding
- Once promotions have concluded then a build will be sent to Ericsson for beta testing and feedback

**3.7 Release date changed - Keith**
- The release will not be available prior to the end of quarter blackout period
- New date is now set as May 7th.

**New Training Material - Keith, Campbell, Dean**
- Training classes at L3 (Camden) and Saab
- Current materials to be updated to remove references to Specialized packages
- L3 training to provide a new class offering that concentrates on using BridgePoint for Systems Analysis diagrams

**Verifier threading SR - Campbell**
- Issue involves a realized component launching additional threads which are unknown to Verifier.
- Investigation shows that this use case is not currently supported and the customer will be notified

**Polymorphic events - Nehad**
- Continuing to work on resolving dts0100948048

**Model-based Model Compiler - Cort**
- Preparing design note for Model-based MC
- Key step in RSL to OAL path successfully automated
                                
### Marketing Status

**Agilent Press Release** 
- Content of the PR is approved and should be on Mentor.com later this week

**Training Material**
- As mentioned above, revisions to the current course material are underway. 
- To help build interest in xtUML.org, new material will be packaged into an online class

**Events**
- SAE World in Detroit will show BP as part of the Mentor Software Tools offering
- DAC presentation as part of re-usable SW track
                                 
### Customer Support Status

**Java Native Access (JNA) Demo - Robert** 
- Demonstration of the structure and steps required to attach a realized component in C to a Verifier session through the VIEC interface
- An app note and demonstation to Saab is proposed for week of April 25th

**Key Service Requests to track**
- Saab has requested support for textual note elements on a diagram canvas. This item has been requested by all major BP customers and Robert is requesting priority be given to the implementation. Bob indicted this is a candidate for the F release
- Sony is reporting MC issues when a project is prepared in the xtUML Editor (xE). Engineering will investigate further in preparation for the 4.0 release. However, under 3.6 and 3.7, the expectation is that projects will start in UML Suite and then modified by xE. 
                                 
---
                                 
---

    Date:     04-10-2013
    Start:    12:00 EDT
    End:      12:32 EDT
    Elapsed:  32 mins
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
              Dean, Robert, Michelle, others...
                                
### Engineering Status
**Status of compare merge and where we are with 3.7 release - Bob** 
- The model has been updated now but there is more testing to be done 

- Updating the tesing models along the process
-  Removing duplicate test models 
-  Unzipping the models that were in zipped format 

- Going through the tests 
- Cleaning up the test models and JUnits 

- After this promotion there are promotions to be comming but they are 
  expected to be much cleaner 

- Keith is working on another execution issue that came from ericsson 

- We are moving past specialized packages in this release as opposed to 
  a later release: 
  -The running of tests will be much faster 
  -The tool now will now be more simple

- But it will cause a slip in the release date 


**Release date slip -Keith** 
- Updating the test process and the test models is taking more time 
- The not needed test suites versions are being cut out which helps
  accelerating the promotion time 

- The release date is being slipped to end of april or most likely 
  sometime in very early may , but it will need to be determined 

- It's ok because we don't have customers clamouring for it 

- Ericsson are on beta test program where they can have 
  the early release 
- Saab are now aware of the date slip they are ok because 
  their problem is not adressed on this release

**4.0 Release -Cort** 
- 4.0 release has one primary requirements are the licensing 
updates and the branding
- The work for licensing is done and is waiting to be merged 
- 4.0 release it follow shortly after 3.7


### Marketing Status

**Updates about activities going on L3 -Dean** 

-At L3 there are two activities:

-Re-engaging with BP and looking to improve the documentation 
 Process
 Keith will be on the site to give them training on how to 
 suport their documentation and analysis using BP

-For the last week there was also a meeting in Salt Lake City  
 to uncover the site was engaged in reviewing system design 
 and systemm engineering prcess 

-Bill is willing to go onsite they are talking about change 
 without knowing what they want to change 


-There's a request to start evaluating BP by one user 

 
-Xerox is working to find ways to use the latest BP offerings 

- Agilent (JohnW, Cort)
- There is some upcoming business with them.  Negotiation in progress


**xtuml.org status updates - Michelle**
- We had a brain storming and colected info and ideas 
  that are captured in a draft 
- Had a plan that is set up for the review 
- Jayne is having a look for the budget required 
  by some of the ideas 


**Call from John Fisher fromHoney well**

- call two days ago from jon fisher from honey well 
  he had some uncovering BP vs rapsody and BP with rapsody 
  that could be a good opportunity
  
 
### Customer Support Status

**Updates on L3 -Robert**
- At the new gersy side there are normal user issues 
 they are asking about datatypes mapping 
 or creating elemnts 

- Sony didnt submit any recent requests 
they are using CLI and running into issue where eclipse is open for the same workspace 
so there should be invistigation to make sure the settings for running CLI are right 


- Eric at SAAB Aero
Robert is working on java native acces example set up 
compile native C libraries 
Cort might help with that 


---

    Date:     03-27-2013
    Start:    12:00 EDT
    End:      12:45 EDT
    Elapsed:  45 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Status of BridgePoint 3.7.0 Release - Bob** 

Release 3.7.0 is delayed because of problems found during promotion testing.  
The BridgePoint 3.7.0 release date is being pushed back from March 29th to 
to April 15th.

**Status of ARXML project and AUDI eval - Dean** 

The current timeline for the eval (which was last set to April 18th) is slipping back because Audi has not yet
supplied the required use cases yet.  The training will not happen until at least a week after the use cases 
arrive. 
We do however know a couple of the use cases and what they require.  We Need ARXML import into BridgePoint for 
this.   It is very near completion (should only be a matter of hours left).  The ability to generate from 
BridgePoint xtUML into VSI is also needed.  This is not as mature as the import work (since it has 
been getting the recent attention), but it is sufficient.

### Team meeting in Wilsonville next week - Cort and Darrell

**Tuesday - ALL SMA**

This is a all day for "ALL SMA".   We will talk about SMA-wide strategy for the coming 
year (objectives).  We will have some cross-group show and tell, and talk about 
how things can fit together better.
    	
**Wednesday and Thursday - BridgePoint**

BridgePoint technical topics.  There is a very long list covereing both short and 
long term items. Cort is collecting and maaging this list which has received 
input from the whole team.

**Friday - Offsite Event**

The morning will be in the office and then an offsite event is planned.  We will go
to the Timberline ski lodge
        

### Marketing Status

**Integrated Electrical Solutions Forum (IESF) - Dallas** 

*Cort* 

Conference in Dallas focesed on Mil-Aero (a little transporation too). There has been a lot spent on 
planes even though the slow economy. A BridgePoint presentation was given by Michelle.  It covered 
safety critical stands complience and using xtUML to assist in these safety complient standards using 
a paper co-authored by Anders Ericsson from Saab.

Demo booth was not very active

*Michele*

Overall conferenece attendence was disappoinnting.   The BridgePoint materials created in conjuction with Anders 
		         are a good starting point for this type of messaging for BridgePooint
                 
*Jayne* 

24 people regeistered for Michelle's session, 9 actually attended.   
For the conference overall, 235 registeded and 123 actually showed, so the session 
numbers are consistent with the overall conference, which was low.

There were several leads asking for BridgePoint demos.
			   
There is another conference coming up in Dallas Aplil 16-19.  Jayne will send info to 
Cort and Dean so we can either have someone represent BridgePoint there or 
have material sent for a demo pod. 

### Customer Support Status
*Robert Rice*

A customer is asking if we have any JNI examples?

There are specific JNI example.  After some discssion, the suggestion is to have the custoemr first 
go thorugh the VIEC example from the documentation which will teach them how to interface to 
external code.   After that, the customer can use a JNA example from the web (wikipedia has a simple JNA 
example).  JNA is newer and simpler to use then JNI so it is suggested.
                  

---

    Date:     03-20-2013
    Start:    9:00 am MST
    End:      9:18 am MST
    Elapsed:  18 mins
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Keith and Travis**

Working through unit tests for latest compare/merge work.  First pass of tests
are complete, now will create a branch build that will be the new development
environment.  With the new dev environment will update all models then run all
tests again.

**Bob**

The pending issues list is up to date.  Working to get the issues resolved from
trip promoted.  Will happen as soon as the compare/merge promotion is complete.

**Campbell**

Had a good training trip.  Introduced a new exercise for the training course.
A Battleship game.  The models drive the game, including showing the game in
progress.

**Nehad**

Finished working on adding a prefix configuration option when formalizing
reflexive associations.  Working on issue dts0100749929, which Travis provided a
solution that Nehad is verifying.  Also working on dts0100948048.

**Heba**

Working on upgrading to the latest dev environment.  Working to complete issue
dts0100917321.

### Marketing Status
**Cort**

In Dallas at IESF running the booth.

### Customer Support Status
**Robert**

Going to have a meeting with Cort, John, and Martin.  Relating to issue with
test cases failing in verifier while they pass in the generated code.

---

    Date:     March 13, 2013
    Start:    phone conference cancelled
    End:      phone conference cancelled
    Elapsed:  0
    Present:  

### Engineering Status
**Campbell** is teaching at SAAB for a second week in a row.

**The release** is moving forward.  But there is a worry that it is moving
a bit slowly.  The update to Eclipse 3.7 has hit our test suite hard.
The list of issues for the release is mostly solidified, but we have active
customer SRs coming in also.

**Ericsson** has provided some test feedback on the Compare/Merge capability.


### Marketing Status
**Agilent** is continuing to progress on their gas chromatograph.

**SAAB** is training and modeling and expanding.

**L3 -** There are 2 new engagements with L3, one in Camden, NJ and one in
Salt Lake City, UT.  There is an on-site demo/discussion tommorrow in Camden
with John Vargas and Todd Holbrook onsite; Cort via webex.

The **All SMA Meeting** is scheduled in Wilsonville for April 2-5.  The
agenda is solidifying.

The **AUDI** engagement will progress in earnest during the week in WV.

 
### Customer Support Status
**Sony** is requesting new features.

**SAAB and Ericsson** are maintaining active SR lists and participating
in regular phone conferences with Mentor staff from Support and from
Engineering.

---

    Date:     March 6, 2013
    Start:    10:00 MST
    End:      10:18 MST
    Elapsed:  18 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Bob -** Promotion progress

Promotion includes update to Eclipse 3.7.  Plan to switch to 3.7 for development.
First promotion step is complete, promoted into a promotion branch.  Created a
3.6.5 build to be used for internal development.  Entire workspace requires model
upgrade for change in Packageable Element persistence policy.  Will be running all
tests after upgrading development workspace.  Travis provided a utility to locate
any potential corruption.

**Darrell -** Wilsonville meeting

SMA meeting in Wilsonville.  Trying to get everyone to together April 1st.  Travel
April 1st, have a meeting on Tuesday with the SMA group.  Discuss the success of
of last year, and plan for this year.  Discuss the organizational shifts.

### Marketing Status
**Dean -**

AUDI engagement is ramping up.  AUDI is producing use cases and will provide them
mid-month.  AUDI would like some introductory training.

Sony transitioned from the pilot project and have opened up BridgePoint to the
broader group.  Many users are working with the free version of the BridgePoint
editor.  The customer is making use of the CLI support.
 
 **Michelle -**
 
 Account team is requesting information on the packaging changes.  Sony would like
 to review them to better understand what the costs are going to be.  Expecting
 a ramp up this year and booking in Q2 or Q3 next year.
 
 Working on ISF slides, sorting through feedback from Anders.  Need to provide
 to SAAB for review.
 
 Plan after ISF is to work on promotion of the xtuml.org website.  Looking for
 content to increase interest and activity.
 
### Customer Support Status
**Robert -**

Received two new issues from Ericsson.  One is the inability to compare two
component references using realized java classes.  An issue has been submitted
with a good reproduction procedure.  Second issue is related to missing components
and blank component references.  Travis has investigated and found a missing
insert statement.  The cause is unknown, but it was suspected that the alpha or
beta could have been used.

---

    Date:     Feb 27, 2013
    Start:    12:00 EDT
    End:      time EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Keith -** Release date for BP3.7.0 set for 3/29/2013.  This is an interim release to get some critical 
  SRs out and move the eclipse base to 3.7  
**Travis -** Delivered beta to Ericsson.  Working on getting changes promoted.  Doing testing now. 
  Ericsson is amenable to testing out another beta.  Maybe sometime mid-March.  
**Bob -** Working on a large promotion branch.  Hit some testing issues, but those are resolved now.
  Working through final items in io.mdl and canvas tests.  It is getting close.  There are about 10
  items included in this promotion.  We'll bump the version after this promotion.  Retest, then 
  everyone will update to the new development environment.  
**Campbell -** Code split is coming along.  MC-Java schema updated for generator and parsed instance
  populations.  Next week will be off-site doing two weeks of teaching at Saab, so code split work is
  on hold while preparing for this.  


### Marketing Status
**Michelle -** 
  * SKO was last week.  Pretty good turnout and vibes.  BridgePoint breakout session was part of
  1hr SMA session.  Attendance was low due to end of day and competition with some other Mentor breakout 
  sessions.  30 attendees down from over 100 last year.  Feedback was good.  Ad hoc BP session was attended
  by about 30 people as well.  Gave the roadmap to the attendees.  Talked about the flow into AUTOSAR, which
  coincides with big Mentor corporate push into automotive.  Emphasized how BP and SystemVision fit into 
  the flow and provides value.  Audi account manager was very interested and wanted delivery dates.  Another
  Korean account manager was also very interested.  
  * Also working on FY14 plan, including target accounts list.  There will be an IESF presentation, the paper with
  Anders has been tentatively accepted, with one month to get it done.   

**Jayne -** IESF has 103 people registered so far.  10 of those have signed up to come to the BP presentation.
  Press release went out announcing the availability of the xtUML Editor.  The mentor.com website update is now
  live.  There are 3 new users on xtuml.org.  Seems to be a lot of discussion on linkedIn rather than xtuml.org.
  Darrell wonders about pushing the training series as an internal training tool.  
**Dean -** There was a key meeting on Monday between the Mentor account team and Audi.  The outcome is that there
  is a plan for a pilot project of 6-8 weeks, kicking off mid to late April.  Will be looking at the complete
  Mentor AUTOSAR flow.  For BP this will be importing ARXML, then exporting ARXML and using the model compiler.
  Will also explore integration with VSI.  This is a big, but aggressive opportunity.  Audi is currently trying to
  do this with Rhapsody.  So, we are presented with an opportunity to replace our competition.  This pilot will 
  involve replacing an existing C module and the big change for the customer is switching from PSM to PIM.  The use
  case they are most likely to ask for is the one we are presenting in our IESF presentation.  The pilot program will
  start out with two days of training.  
**Darrell -** There is an organization in Saudi Arabia that is looking to fund training and "centers of excellence".
  We are looking at doing this while lining up with corporate AUTOSAR push using BP and VSI.  Working with Mentor
  Office in Egypt (Hasam) to progress this opportunity.

### Customer Support Status
**Robert -** Not a ton of BP activity right now.  There is a component/VIEC interface param ordering issue that 
  he would like some input on.  Cort will follow up.

---

    Date:     February 20, 2013
    Start:    noon EST
    End:      12:30pm EST
    Elapsed:  30 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, others...

### Engineering Status
**The Move to GitHub -** Keith reported on the movement of development
artifacts from SVN and CVS into github.

#### I Who
- This will affect everyone on the team, but the biggest impact will be
to developers
  
#### II What, Where
- Moving source code to github
 * All code will go to private xtuml/verifier repository first
 * Moving editor plug-ins to xtuml/editor will happen with the true code split
   and involve plug-in renaming.
- Moving build support files to new SVN
 * wv-svn-01/svn/sle/xtuml - should have access using existing accounts
- Will move SVN test models to github
- Opportunistically move other SVN data to new SVN server.
- The existing CVS and SVN repositories will become read-only after the data
  is moved.  Developers with changes on branches will have to create branches
  on github and move their changes over.
- The new servers are supported in updated build scripts
- documentation, issues repositories
  
#### III When
- Model compiler development already moved to github in xtuml/mc repository
- After the promotion Bob has in progress is done, we will move BP development
  to github.
     
#### IV How
- The promotion also upgrades us to eclipse 3.7 under BP
- All developers will switch to the new development environment, which will 
  be 3.7 eclipse + 3.7 compatible BP (newly promoted) + some development addons
  

### Marketing Status
**SKO -** is in full swing.
**Accounts -** L-3 in Camden, New Jersey is ramping up (another) BridgePoint
pilot project.  Ichimasa-san provided more info from Sony who is growing,
albeit slowly.  They have 2 projects where BP generated code is in
production.
**DAC Preso -** An abstract has been submitted to present on an AUTOSAR
flow.

 
### Customer Support Status
**CSD Org -** Robert reported on some management changes have occurred
in his reporting structure.  The effect is yet to be seen.  Robert will
report to a new manager in March.

---

    Date:     Feb 13, 2013
    Start:    12:00 EST
    End:       EST
    Elapsed:   minutes
    Present:  Bob, Keith, Travis, Campbell, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Release Planning - Bob** 

Given feedback from customers we have decided to make a release prior to the 
OSS release.  This will include:
-Eclipse 3.7 
-Merge functionality
-bug fixes

We do not have the hard date yet, but are looking at mid-March.   
We are also working on the faster MC.  That work will not be included with this release, but we 
will likely follow-up with a MC-only release later with this work.

- Bob is promoting the Eclipse 3.7 work and also 5 bug fixes right now.  
- By next week's meeting Bob will have the list of bugs that we plan to include in the upcoming release.

**Keith** 
- Presented the final xtUML learning series presentation yesterday.  
- Working on moving our BridgePoint development source  to github 
- Working with Cort on the Model Compiler performance improvements

**Heba** 
-Working on ARXML to xtUML.  
-Working on picking up the BridgePoint issues that went to the background while she has 
been on the ARXMLto xtUML project.  These issues need to be completed for the upcoming release.

**Nehad** 
-Fixing bugs that customers have requested for the upcoming release.  The current focus is 
dts0100835691 (Formalizing Reflexive Association have no clear connection between association end and attribute)

**Travis** 
-Working on CLI Merge support.  It is now done.  Testing is in progress.  Travis will create a new video that will
be sent to Ericsson.  The video will be followed with the beta.  This work will be prmoted to head following 
the current promotion of the Eclipse 3.7 work (and bug fixes), but before we move the source to github.

**Campbell**
-Campbell is working on splitting the BridgePoint Editor "code split".  There is a version that removes 
all non-editor functionality.  However, there is a requirement that we maintain  a single code base.  Therefore,
the parser, execution, and build functionality must exist along-side the editor in a way that allows us
to maintain the entire product with a single source code base.  This is what Campbell's focus is.



### Marketing Status
**Dean**  
- At Sony there is a key meeting that will discuss how the pilot project went.  We are waiting to hear the 
conclusion of this and what they define as the next step.

- The learning series episodes have now concluded.

**Jayne**  
- There are intereactions from several new leads.  These are being followed-up and more followup will happen after SKO.
- Jayne said good job on the xtUML learning series.  It has been successfull by bringing in new leads and providing 
new content for xtuml.org.


**Michelle**  
- Working on BridegePoint material for SKO.  
- Following a new lead from Infinian.   
- Michelle too has been pleased with the result of the xtUML Learning series.  It has been a successful tool for us.

 
### Customer Support Status
**No customer support status today** 

---

    Date:     January 30, 2013
    Start:    noon EST
    End:      12:40 EST
    Elapsed:  40 minutes
    Present:  Keith, Campbell, Heba, Nehad, Cort, others...
            Dean, Robert, Bill, Jayne, Michelle, others...

### Engineering Status
**Road Trip -** Cort opened the meeting explaining that Bob has been on
the road in customer shops for 10 days now.  He spent last week at SAAB
in Linkoping; he spent 2 days in Budapest at Ericsson and will spend the
last 2 days of this week with Agilent in Middelburg.  This has been a
productive trip with many issues being resolved and/or clarified.

With Bob in the customer shop, the factory took to the opportunity to
share the BridgePoint roadmap through webex presentations.  In both
shops so far, the presentation was well received.  The vision statement
for BridgePoint rings true, and the nuts-and-bolts steps that have been
taken to support the vision are clear.

**Bob -** reported the good news that while working with SAAB, they
ran out of licenses, because they were using so many.  There were 38
unique users using BridgePoint.
Bob and Martin went through about 30 issues and did a lot of
reproducing and debugging.  Several issues were fixed.

At Ericsson, Bob and the team went through 38 issues resolving
roughly half of them.  Many of these were resolved through "discussion",
clarifying understanding and explaining how the tool and process work.

**Keith -** reported that we are continuing the push to get everything
up on xtuml.org.  We released our first Open Source plugin onto
github.com/xtuml/editor.  We will continue delivering incrementally
over the next several weeks.  We have completed the build of the
xtUML Editor.  This has licensing and branding changes.  We are working
with The Other First on getting this uploaded to xtuml.org today.
At the same time, our demo version of BP is expiring at the end of the
month.  We will be delivering that to the normal mentor.com location.

**Campbell -** has been supporting Bob.  In the background, Campbell
has been working on the code split.  We have our first cut at this.
Campbell reported that we now have the editor portion of BridgePoint
separated from the Verifier.  Tests are passing on this divided
source in a branch.  He also reported that there may need to be 
additional surgery to split out the parser in a way to protect model
compiler IP.  More on this in future updates.

**Nehad -** reported progress on a parsing issue.  He is also working
with github for the first time.  He is now working on a new issue
assigned to himself.  He is struggling to reproduce the issue.  This
deals with EE bridges.

**Heba -** continues to refactor the ARXML-to-xtUML import.  She is
also working with the rest of the AUTOSAR team to fit this work
into the overall flow.

**Travis -** reported progress on the following issues:
- dts0100749929 - Produce warning when importing an identical project into workspace
Issue was still present with Serena Dimensions and Import existing project
into workspace.  Addressed for the latter but SAAB did not get a chance to test.
- No Issue - Found and fixed a dead lock that was always present but easier
to hit once the PE upgrade code was added.
- dts0100941736 - Resolved issues where the graphics were being removed after
a CM check out during creation.  Also added additional listeners to the SEM
editor so that the content is kept in line with file reloads.
- dts0100941910 - Helped SAAB and Bob get a bunch of missing interface
references back; the reconciler had a couple of bugs that were preventing
their recreation.  I believe they were also removed by the same bug in
the reconciler which determined they should be removed, this has already
been fixed.
- dts0100942085 - Addressed an issue where we were checking out files
that were not actually being modified.  The problem related to some
checks added to make sure we keep needed proxy data up to date.
- No issue - Added a Set To Provider/Set From Provider CME for Ericsson
- dts0100934967 - Added a CME "Add Built-in External Entities" that
creates LOG, TIM and ARCH to any selected package(s).
- dts0100934959 - Added realized path to component names if set, now
components will show as follows if realized: Component Name realized
by ThisClass.java
- dts0100942847 - Began working on CLI, now have persistence in the
normal PMC format.  Started testing with BP and ClearCase to try and
produce a demo.  Have it configured to use the CLI version of merge
but having some trouble with ClearCase (still learning).

**Cort -** is supporting the work in the field and in the remaining
cycles is speeding up the model compilers.  We will use model-based
model compiler technology.  Cort explained that the technology is
all there.  The issue is project management.  We need to move from
here (RSL) to there (xtUML) while maintaining all functionality and
both manifestations (RSL and xtUML).


### Marketing Status
**Dean -** reported his focus on Sony.  The account team has been working
closely with Sony on a pilot project.  The information fed back most recently
has been positive.  Dean reports that Sony is preparing for the next renewal.
A big portion of the strategy is to ramp up usage of BridgePoint so that
the renewal is as large as possible.  They want to establish some usage
of the Verifier.  Success with the pilot project is the first step.

For LSI we are focusing on a virtual platform opportunity.  Dean is
focused on educating the account team and preparing for a presentation.

The Inside xtUML series has had 3-in-a-row from Dean.  Michelle has
the next one which is done.  This may grow into a white paper for
other conferences.

**Michelle -** reported that Samsung started as a SystemVision opportunity
but may be a BridgePoint prospect.  They are looking at the high-level
functional process.

Northrup Grumman may be a big opportunity including test methodology.

**Jayne -** reported that SKO activities are in full speed.  Last year
BridgePoint was absent from SKO due to uncertainty in the strategy.
This year is just the opposite; we have a clear plan with tactics in motion.

 
### Customer Support Status
**Robert -** reported that "my numbers look good".  This is because
of the activity resulting from the engineering road trip.  Many issues
are flowing through the paperwork process at the moment.  Lots of SRs
are being resolved, however some new ones are being opened.


---
    Date:     January 23, 2013
    Start:    12:30pm EST
    End:      1:02pm EST
    Elapsed:  32 minutes
    Present:  Heba, Keith, Travis, Campbell, Nehad, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**BridgePoint Roadmap Preso to SAAB -** Cort reported on Bob's progress in Sweden
at SAAB.  Bob has had two productive days of issue reproduction and isolation.
In order to show solidarity and support from the factory for SAAB and for Bob,
we scheduled a webex presentation showing the BridgePoint roadmap and overview
of recent progress.  We had a good group of engineers from SAAB representing both
Dynamic and Aero on site while a large contingent of Mentor people joined.
The meeting seemed to go well.

**xtUML Editor Release Prep -** Bob mostly finished the xtUML Editor package before
leaving for Europe.  However, significant final touches were required.  Keith reported
that we have the first release of the xtUML Editor available for testing.  Smoke
testing has been done.  It is now ready for more ad hoc testing by everybody.  We
should all download it and try it out.  We have a few days for minor tweaks.  The
editor must be available to the world by Jan 31.

This package is roughly equivalent to BridgePoint 3.6.0.  Only the branding
and licensing have changed significantly.

This version of xtUML Editor is (still) built on Eclipse 3.5.

**ARXML Import -**  Heba gave a demo showing how BridgePoint imports ARXML.
It shows an AUTOSAR 4.0 format ARXML file being imported into a BridgePoint
xtUML model project.
The components, interfaces and data types are then created.  These include
graphics!  This will be a key feature for automotive (and possibly mil-areo
and and commercial aviation) customers.

The xtUML to ARXML is not present.  In the past, a proof-of-concept was
created for a specific model using AUTOSAR 3.x.  This work is mostly starting
from the beginning.


### Marketing Status
**Item 1 -** Dean reported good participation in the continuing xtUML Learning
Series.  SKO prep is heavily under way.  Lots of meetings are being scheduled
with account managers.  IESF web site is being launched this week.  xtUML
promotions are giving us hits but no so many registrations.

- LSI account activity is moving.
- AUDI will be presented at the end of the month.
- A Slovenian customer is interested in code generation.


### Customer Support Status
**SAAB MC Issue -** At least one of the models used by SAAB Dynamics is too
big to be compiled by MC-3020.  They are hitting the 4GB memory barrier of
generator.  We are working on the long-term solution but may need to find
something to help them in the short-term.

---

    Date:     January 16, 2013
    Start:    12:00 EST
    End:      time EST
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Compare/Merge -**
  Travis gave a webex demo via a pre-recorded video.  This is for the new model-aware compare/merge that integrates
  nicely with CM systems.  We are creating an alpha version that will be delivered to a few engineers at Ericsson.
  Beta version by end of February for additional customer feedback.
  
  Dean wondered about filtering on the structural differences view.  It is not supported at this time.  He also 
  wondered about "graphical merge".  We do support merging and updating the canvas data.  But we do not support a 
  display of the rendered canvases with the changes highlighted.
  
  The initial work is almost complete.  The promotion process is underway and will be completed this week.
  
### Marketing Status
**Dean -**
  There is a lot of activity on the Marketing side.  Work for sales kickoff, new part numbers and packaging for
  OSS release.
  
  Successful presentation this past Monday to LSI.  Ongoing interest from them.  There is also interest from Samsung,
  a Slovenian customer interested in the AUTOSAR flow.
  
  There is continued interest for the OSS Editor on xtuml.org
 
**Jayne -**
  A press release for the OSS editor is scheduled for the first day of SKO (2/18).  Number of attendees to our live
  series has declined since Christmas.  Was about 13, but now about 6.  Still seeing traffic for the archived versions.
  There has been an increase in the number of downloads of other BridgePoint-related collateral from Mentor.com.
  
  Would like to get some new white papers.  A lot of stuff on the mentor.com is old and is being removed in the webpage
  rework.
  
  
### Customer Support Status
**Robert -**
  In training this week.



---

    Date:     January 9, 2013
    Start:    noon EDT
    End:      12:35 EDT
    Elapsed:  35 minutes
    Present:  Bob, Keith, Travis, Cort, others...
            Dean, Robert, Michelle, others...

### Engineering Status
**Model-based compare/merge -**
  Working through automated tests.  Only one suite to go.  Still need to create 
  INT and address issue.  Then will promote and pass this to Ericsson for beta.
  After that need to add command-line support.
  
  There was a lot of test fallout due to some UI changes and that the Packageable
  Element are now always persisted with the element they represent.
  
  We will create a video that shows the difference between the old merge func-
  tionality and the new functionality.  This will be able to be shown to
  customers.
  
  Customer gave us 3 use cases that all rely on the command-line interface.  This
  is not part of the first delivery, but will be part of the second.
  
**Bob -** will be visiting Saab, Ericsson in Budapest, and Agilent on a European tour
  at the end of January.
  
### Marketing Status
**Dean -**
  Inside xtUML series has produced some new interest and opportunities.  Develop-
  ing a lead at LSI logic.  Also have seen interest due to presentation about 
  testbenches and test strategies.
  
  Cort indicates we are still on track for end of Jan 2013 delivery of free
  editor.
  
  Have a meeting with Sony account team this evening.  They have concerns that 
  the factory will be responding to.
  
  There is an Audi presentation tomorrow.
  
**Michelle -**
  Got approved T&Cs for the portal.  Still have to circle back due to concerns
  about OSS licensing.
  
  Need to do a press release for the upcoming release.
  
  Doing a lot of prep work for upcoming SKO.  Website overhaul, quoting guides,
  other collateral.
  
  Also working with Ericsson on a paper for IESF.
  
  The idea of a MG storefront with a downloadable package is on hold.  Infrastructure
  just not ready.  For naming, the name "BridgePoint" is sticking with the Mentor
  stuff (verifier, translator).  The xtUML naming is what is going with OSS.
  
  MCs we'll offer: C, C++, SystemC for high-level synthesis, SystemC with 
    TLM/RegDef. AUTOSAR, MisraC are in the future.
    
  Cort added that the binary MCs are necessary for the demo.  So we will carry it
    as a demo tool, but it isn't necessary as a part on the price list.  We won't
    be surprised if demand is raised for it and we end up adding it later.  It 
    would be nice if we could totally depend on the AE licensing mechnism for the
    demo version, but we see the need to continue our demo BP creation using our
    own licensing timebombs.

### Customer Support Status
**Robert -**
  Out of office next week for DVT training.
  
  Heard from Yagi-san that he successfully use the new $t functions to do what 
  he wanted with component descriptions.
  
  Got a couple of issues from Saab and Ericsson, working through.
  
  The Sony licensing problem was due to two parallel requests from Yagi-san.  MGJ
  took one, Robert took one.  MGJ found a workaround, Yagi-san told Robert to
  close his.  The solution by MGJ was weak.  Robert provided a new patch that
  addresses the underlying problem by adjusting the ENV var separators before
  the tool calls xtumlmc_build.pl.
  
