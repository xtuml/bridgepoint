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
---

    Date:     27 August 2014
    Start:    noon EDT
    End:      noon:30 EDT
    Present:  Keith, Bob, Campbell, Cort, Travis, Heba, Nehad, ...
    Robert, Roy, others ...

### Engineering status

Heba and Nehad are addressing the observations from the last review meeting of the SVX connector.  They are addressing them one by one.  There are struggles to get the JNI wrapper working every time.  Nehad is validating the added GUI.  He has come up to speed quickly and is working together with Heba.

Travis is finishing up the recent Compare and Merge issues.  He is building the unit tests, then we will have the code review.

Bob is addressing a SAAB SR to add more double-click support.  We need consistent double-click behavior throughout the xtUML Editor.  Bob has updated the pending release queries.  Please review them.  Missing items may have been pushed to the M release.

Campbell has greens on his workspace.  He will submit for promotion.  He is not happy with EGit.  :)

Keith has been working on the build server.  He has the MC-Java prebuilder running as part of it.  He is trying to use the same build steps on the build server that are used in development.  He is also incorporating CLI to kick the build off.  Keith mentioned employing a gh-pages branch on xtuml/training.  Dean is involved in populating the branch.

Cort has been working on the model compiler with tasks mostly focused on Yazaki.  A first demo release was provided last week to MGJ, no word yet on its reception.



---

    Date:     06 August 2014
    Start:    9:00 MST  
    End:      9:49
    Present:  Keith, Bob, Campbell, Cort, Travis, others ...
    Dean, others ...

### Engineering status

**Roy Clement-**  Roy is a CAE from Newbury, working for support.  Knowledgeable with design systems.  Worked for Mentor for 10 years.  Will be helping with BridgePoint support in Europe.  Next 4-5 weeks will ramp up knowledge of BridgePoint.  Just a bit delayed, supporting HDL designer as well.  Has a good background with eclipse based graphical tools.     

**Keith -** A notice has been sent out for the xtUML modeling course.  It is open to all registered users.  Hopefully it should generate some traffic.  We will continue to add to it, adding model based testing recently.  We still have sections to complete.  Bob started CLI videos and will finish it.  May move some modules to an advanced course.  Still need the quiz section for the OAL course.  We will put the analysis videos on the back burner.  Dean produced an introduction video that hopefully will incorporate community involvement.  We will move on from the CSM videos remaining.  Started an index which will have continuing development.  The classes have a grade book, allowing us to see traffic. 

**Bob -** Feedback from Montreal was generally good.  The first few modules were trivial, but this was expected.  The team would like a video explaining the differences between merge and rebase.  No additional recommendations for the training material.  Visited Wednesday - Friday, most of the team was present.  First learned about their workflow and process, the process is good just not well defined.  Suggested some additions to their process, mostly they were not using task-based branching.  Also suggested they provide documentation for their tasks.  Used the BridgePoint team's process as a guide.  They had no code review step, the process now includes a promoter.  Tested the process defined and addressed issues found.  Trained them using git with compare/merge using the process.  They ran into an xtUML merge bug, which scared them off of compare/merge.  Focused on compare/merge for the last two days.  Also helped with their current sprint.  Helped with testing towards the end, had to help as the person did not know BridgePoint well.  Received the test model and figured out the bug in the test model.    

Question (Dean): Was the SR to be raised a result of the trip?   
Answer (Bob): Yes, and this problem would have been easily found if we address the SR.   

**Travis -** An issue was found internally where merging can cause loss references to proxy elements.  Work was done to address this but video production was picked back up before completion.  The Ericsson Montreal team hit the same bug but in state machines using polymorphic events.  The work has been picked back up and redesigned to address both the class and state machine issues.  Further testing of all elements is under way so that we can deliver a more robust compare/merge.   
   
**Cort -**  We have some money remaining for The Other firm.  We want to spend that money so that we can transfer knowledge that allows us to manage the site.        
   
**Dean -**  John and Stephen are working on a new in-class training course.  This training course we be delivered to Ericsson and will complete the T1 hours.  The final version of the course material has been completed and sent to Budapest.  The class should be done next Wednesday.  The second part of the course will be delivered on December 8th.  The T1 hours remaining are 1 man week of hours for supporting Montreal.  We should run all of the hours out by the second week of September.  We may have two man days remaining.  Should see Japanese use of the training videos.  IT in Japan makes it harder to make use of the training videos.   
   
**Robert -** Sony has raised a System c model compiler issue.  It is a long running issue going on for about a year.

**Cort -** There is another issue for hover text.  Received an SR for hover text and we have already solved it.  In the e-mail Umemoto-san is asking to us to file the ER quickly.      
   
---
---

    Date:     23 July 2014
    Start:    12:00 EDT
    End:      12:29
    Present:  Keith, Bob, Campbell, Cort, Travis, others ...
    Dean, others ...

### Engineering status
Bob presented a quick sketch of the content for the next release.  We have a list set up in CQ.  What is interesting about the higher priority items now is that they are significant features rather than bugs.  Big items include OAL editor changes and logical model support in the compare and merge capability.

Bob is supplying access to the online training material to the Montreal team before he goes onsite.  There is material that directly addresses configuration management.  He will then go to Montreal in person to help with anything that will make them more efficient.

Cort and Dean explained that Stephen Mellor and John Wolfe will be "beta-testing" two courses that they have been working on.  One is oriented toward analysis of requirements; the other is basic xtUML modeling.  The training classes will be held in Europe in August and September.  This includes improvements to the GPS Watch case study.  Dean commented that we are getting positive feedback from John as he uses the tool in earnest.

Keith supplied a demo of our courseware.  He gave a brief tour of the front end that students will see.  He then showed us how the back end is managed within WP Courseware.  This was very informative.  We have a treasure trove of materials nicely organized.


---
---

    Date:     9th July 2014

    Start:    1200 EDT
    End:      12:29
    Present:  Keith, Bob, Campbell, Cort, Travis, Heba, Nehad, others ...
    Dean, others ...

### Engineering status
**Keith -**
- Working on Class diagram data modeling video
- Will work with Teri Sipes & The Other Firm to get the WP Courseware stuff migrated to the live xtuml.org
- Everyone needs to create container pages and quizzes to go along with their finished video segments
- We are close to being able to make the course public on xtuml.org, but we need to reach critical mass

**Travis -**
- Looking at merge issue that causes corruption when merging classes.  About done but found an issue related to undo/redo of the work.
- Will go back to creating container pages and videos once the development is complete

**Bob -**
- Finally got done with process changes so xtUML Editor can stay in synch with master development. Released new xtUML Editor.
- Now working on CLI video.  Recording done, editing in process.  Next will do some basic git videos targeted at training the 
the Ericsson Montreal team.

**Campbell -**
- Have some videos that are nearing completion, all on the subject of VIEC and realized code integration
- Took a quick look at issue reported by SAAB

**Nehad -**
- Redo-ing the video that was rejected
- Also finishing UI enhancements issue.  There are lots of test failures because the UI changes, have to update test results.

**Heba -**
- Wrapping up two issues related to breakpoints.
- Updating the branch for port references.
- Re-recording the port video, making good progress.

**Cort -**
- Reviewing training videos
- Working a lot on FXAT
- Helped with Yazaki deal, they want some AUTOSAR functionality that we are investigating and planning the flow for
- Planning with Subba and Serge
- Ericsson deal came through at same level as last year.  We were hoping for more so we are looking for some additional 
business with them this year.

### Marketing
**Dean -**
- Helping gather information and provide support for 
- Yazaki is a very interesting opportunity.  The account team was very expressive that BridgePoint needed to be part of the deal.
- We have seen several new BridgePoint accounts
- Ericsson did come in flat, mostly because we ran out of time to see impact from the services deal for new teams.  Serge will be 
visiting Ericsson later this month to talk about a new services deal for later this year.
- Helping drive forward the Embriair evaluation

### Customer Support Status
**Robert -**  
- SAAB - Filed a bug for Verifier breakpoint issue, working through another apparent bug in the C MC
- SONY - Yagi-san is very unhappy that prebuilder can pull a license from any license that contains the appropriate atomic. He basically demands that we change it immediately.


---
---

    Date:     18th June 2014

    Start:    1200 EDT
    End:      12:25
    Present:  Keith, Bob, Campbell, Cort, others ...
    Dean, Robert, others ...

### Engineering status
**Keith - Release of 4.1.12  -**
The 4.1.12 Release is officially on SupportNet. The build number has been incremented in
preparation for the creation of a demo version. Demo version to be based on this release
and licensed to expire on 01/14/15.

Currently waiting for promotion is Heba's SVX client and a couple SR fixes not related 
to compare/merge. Promotion work to proceed as part of preparation for next release of 
xtUML.org Editor.  Expecting to see work completed before end of month. 

**Keith - Training course update -**
There is still quite a bit of work left to do to create the training video content and 
the pace is dragging. Quality of videos is quickly rising and few are needing to be reworked. 
Keith sees this as encouraging and likely to speed up the remaining content creation activities. 
Keith has also received interest from the SV team to learn more about our activities. It appears 
they have also received customer requests for similar self-paced materials. 

**Bob - compare/merge patch -**
Primarily involved in completing the 4.1.12 patch and push it out. He received news today 
from Saab that they will soon be testing 4.1.12 in an effort to qualify it for production 
usage. Also, there is a trip planned to Ericsson (Montreal) to provide training/consulting on 
compare/merge usage. The Ericsson team is encountering problems with their usage of configuration 
management and issue tracking inside eclipse. These problems are being reported to management 
as tool issues rather than methodology weaknesses and Bob's trip is to correct this misperception.

**Cort - FXAT - Custom model compiler  -**
FXAT has significantly extended the custom C model compiler and their models are too big for the conventional 
model compiler flow. Consequently, we are assisting them to create a model-based custom MC. Cort is
expecting to have the recent batch of changes completed by 06/27

### Marketing
**Dean - Yazaki Update -**
The negotiation is nearing completion and the account team has several options to address 
concerns raised by ESD. Final decision is expected before end of the month. 

### Customer Support Status
**Robert -**  
No items to discuss in this meeting.

---
---

    Date:     11th June 2014

    Start:    1200 EDT
    End:      12:30
    Present:  Keith, Travis, Campbell, others...
            Dean, Robert, others...

### Engineering status
**Keith - Training course update -**
The Other Firm got WordPress Courseware installed on xtUML.org. Keith started using 
this xtUML Developer WP Courseware development system.  The advantage of this over 
Coursera and otehr MOOCs (Massive open online course) is that our users do not 
have to leave xtUML.org to take the courses.  We have taken a step back from 
our current approach to evaluate this, and we haev decided to move forward with 
WP courseware.   This doesn't effect the team because we are still creating 
the course content the same way that we have been.  

We have ~60% of all needed videos done at this point.  Campbell have a bunch ~15 
that is a big chunk that is coming soon.

**Travis/Bob - compare/merge work for patch -**
We will give a release to Ericsson to get the thumbs up.

We have the crucial issues resolved now.  The issue are:
2627124315	dts0101057785	Copying of incoming changes for a class operations does not work properly
2627124343	dts0101057788	Copying of incoming changes for a interface signal does not work properly
2627145381	dts0101057790	After merging interface formalizations of components graphics of the formalized interface is not shown
Note that these problems came down to one root cause.  The issue was a problem with merging of elements that had been added or removed.  It manifested itself mostly easily in the case of sorted model elements.  However, we found that the problem was not unique to sorted model elements.   With this change, users will see empty “slots” (placeholders) on the right or left that represent the added/removed model element.  This visualization makes it very clear that an element is an incoming/outgoing addition or change, and it also makes the placement clear.  It was the target placement that was causing problems.   


** Campbell -**
Working on Realized code video. Hit some binding issues, creating GPS Watch VIEC component, now resolved. 
Helped Martin Nilsson with a Verifier realized EE question. Awaiting outcome.

** Heba -**
Working on videos.  Looked at some Verifier issues involving the sender keyword, but it is not high priority.

** Nehad -**
Working on videos.

### Marketing
** (Dean) -**
Ongoing meetings with Engineering involvement in several places for new business.  

Agilent was at a point where we were to suspect support, but an agreement has now been 
reached, so support can now continue.  


### Customer Support Status
**Robert -**  
No items to discuss in this meeting.

---
---
    Date:     4th June 2014

    Start:    1200 EDT
    End:      
    Present:  Keith, Travis, Campbell, Cort, others...
            Dean, Robert, others...

### Agenda
* Training materials progress
* Progress on patch release (Travis)
* Engineering involvement in sales opportunity at Yazaki (Dean)
* Customer Support (Robert)

### Engineering status
**Training materials progress (Keith) -**
Moving forward, reviewed and uploaded several movies to YouTube. Keith, Cort, and Travis are reviewing. Added status tracking for each segment into training document to better track current state of development. Will continue development as time is available during patch release work. We're seeing the light at the end of the tunnel!

**Progress on patch release (Travis) -**
ISM/CSM state IDs caused corruption in a version of the patch; Ericsson encountered this. Further issues with compare/merge element ordering have taken a lot of time to resolve. Travis is re-architecting and refactoring code for compare logic to make the code simpler. Good progress is now being made, possibly a patch in 3-4 more days.

### Marketing
**Engineering involvement in sales opportunity at Yazaki (Dean) -**
Two large deals possible in the June/July timeframe. The larger of the two is at Ericsson, and is making good progress with Serge's involvement. Yazaki is a new potential customer, but there are technical and political concerns which have jeopardized BridgePoint's place in the deal. Status for BP inclusion in a services deal at Yazaki is currently positive, but by no means assured. An evaluation is just starting to ramp up Atech in Brazil (a wholly owned subsidiary of Embraer). They provide command and control products and services for the Brazilian military. They're interested in BridgePoint as related to the SAAB aircraft deal with the Brazilian government. SAAB is still investigating the effect that Model Compiler license linger will have on their MC usage as they migrate to 4.1.

### Customer Support Status
**Robert -**
No customer issues to report. Not surprising as we are entering summer vacation time for most of Europe. CSD is going to designate an EU support engineer to help handle BridgePoint issues, so service level agreements can be better met. This engineer will likely be based in the UK or Israel and will be brought up to speed over the next few months.


---
---
    Date:     21th May 2014

    Start:    1200 EDT
    End:      
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, others...

### Agenda
* Compare and Merge (Travis)
* Summary of Training last week (Keith)
* C++ MC dispatch and binding issue (Cort)
* Training Materials (Keith)
* Update on timesheets for Ericsson T1 (Dean)
* Saab licensing issues with linger (Dean/Bob).

### Engineering Status

**Compare and Merge (Travis)  -**
The Ericsson EATF group raised 4 issue against 4.1.10.  One of them caused corruption and is critical.  It involved updating 2 different state machines in 2 different branches.  Travis found a way to change the upgrade to happen in memory and avoid the problems.  A test has been sent to EATF to test.

Bob mentions that Saab really doesn't want us to force persistence so the resolution now proposed is very good news.

**Summary of Ericsson Training last week (Keith)  -**
Training went well overall.  Keith is capturing the things he presented live in video form so it can be used in the developer course online.  There were 14 attendees.  Keith thinks the difficulty level was abut right.   There was a "free programming day" where the attendees worked on their on projects.  2 teams worked on model import tasks *like xmi import).  Another user worked on drag/drop of attributes.  Others worked on draw code to display class based operations in a different way.  A couple university students were interested in formal verification, they worked on transforming the model into a form that can be input into their functional verification tool.   We are providing input to keep all these projects moving.   
All these projects made the customers very anxious to get MC-Java into open source.
KEith found they have not really made good progress on the model-based MC since Bob and Cort visited.  We captured info to try and help push them.   In a meeting with Gergo Seres, Keith found that they really want to get the tool to integrate with other tools and technologies.  Keith's full trip report is available.


**Training Materials for full xtUML Editor Training (Keith)  -**
We need to move this forward so we have at least a partial course.  The training materials need to take priority over new development.   Keith is going to take a more hands-on approach to moving this project forward to completion.  We will have both an editor training course as well as a developer training course.
This has priority over new development.  It does not have priority over field issues.

**C++ MC dispatch and binding issue (Cort)  -**
The C++ model compiler has been deliver to Ikaria, there were a couple of problems, but we have now got to the bottom of them.


### Marketing Status
**Update on timesheets for Ericsson T1 (Dean)  -**
Overall tier 1 we are at under 300 hours remaining.  We are over 70% consumed.  We have been charging mostly against task 1 (training), but that task is now complete, so we will no longer charge against Task 1.   We do not want to get into a situation where we do a cost overrun, so we now much be more careful about the tasks the items are submitted against.


**Saab licensing issues with linger (Dean/Bob/Jayne).  -**
Saab Aero is only migrating to 4.0 and beyond now.  They are concerned with
the linger features because they are just now seeing it.  They think it 
will lead to license denials in the way they use the tool.  They 
have 38 users of verifier, but those users also use the MC.

Jayne says they have 60 engineers trained but they need some issues resolved.  She said this came from Walter
Sebia, (system vision simulation expert).   Bob reports that in our BP meeting with Saab aero today e discussed the issue list,
there are no specific issues at this time according to them.  They are working on updating the issue list now we are not sure what these issues Walter spoke of are.  

Vricon is taking 3 licenses from Saab Dynamics. Vricon is a wholly owned subsidiary of Saab.

	


### Customer Support Status
**Robert -** 
Robert is interested in the result of the linger option.

Marijian has entered some enhancement requests, Robert is filing DRs as needed.  These are mostly the issues Travis discussed earlier in the meeting.  Bob mentioned that Ericsson is keeping their priority document up to date to let us know what they want us to be working on.

---
---
    Date:     7th May 2014

    Start:    6:00 GMT+@
    End:      6:42 GMT+2
    Elapsed:  42
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, others...

### Agenda
* KB heading to Budapest to provide developer training
* Training material progress
* Engineering status: Kari
* Marketing
* Support

### Training Materials

**Bob  -** 
Did some work on outlines for CLI and Configuration and build topics, but stopped work to concentrate on
bringing the xtUML Editor up to date with Jupiter changes. The source will be updated prior to KB leaving
for Budapest. The xtUML Editor source workflow is currently very ugly, since parts of the model need to
be manually removed.

**Keith  -** 
Heading to Budapest to provide developer training.
Capturing the content as videos to additionally support the open source training materials.
Started with a basic outline similar to the User training under issue #5. Working with Travis
to create and edit videos to support the training. Keith may need some assistance from other
team members to get his materials ready for next week.
Currently we are clarifying the goals for the exercises. The high level goal is to be able to download and
build the git source, make small modifications and debug them. These goals will be met with various hands on
exercises. Ericsson requested an extra day of programming exercise to build on training. It would be good to
select an item from Farenc Bozoki's list of suggestions, but they all seem rather large to achieve in one day.

**Cort  -**
Working on training materials for OAL. Going through the existing materials to understand what we have already.
Looking to produce quiz assessments for every three to four videos.

**Nehad  -**
Writing materials for the BP UI and editors.

**Heba  -**
Resumed work on training materials, 4 scenarios and 2 videos. Tried making Voice-overs but progress was slow.

**Campbell  -**
Focused on scripting and video content as the home office was not sufficiently quiet for recording voice-
overs. Hence just one video completed to test the full workflow. Voice overs will be viable going forward
as the office is now quiet.

**Travis  -**
Worked with Keith to deliver training videos.

### Engineering Status

**Bob  -**
Jupiter now wrapped up. We just squeezed it under an Ericsson deadline. In, fact something else was needed
for the headless CLI support and this was rolled in after the deadline.
We are now moving to work on the next release, Kari. The issues list is updated and ready to go although the
current focus is on T1 materials development.

**Nehad  -**
Multiple tooltip work almost completed.

### Marketing Status
**Dean -**
Concluded IESF tour of US. Good responses received, now following up on leads. Many good contacts forged including
at Boeing (WA) and Rockwell Collins (TX).
There has been a noticable focus shift in the EDA literature towards the Connected Cars arena. Mentor is well
positioned in this area with its AUTOSAR products and BridgePoint has traditionally had good ties with these.
BridgePoint has always had good contacts with Mentor Graphics Japan (MGJ) who often turn up unique usage models for
BridgePoint. One such opportunity is at Yazaki. The engineers there were educated on the standard BridgePoint
development flow, but subsequently focused on modeling an ECU application flow...

**Cort  -**
... Yazaki is looking for BridgePoint with AUTOSAR which will require us to re-instate the integration with VSI. This
is expected to present challenges given the maturity of some of the cpomponents involved. The challenge for the team
will be to keep a balance so that the work for Ericsson and the opportunity at Yasaki are both kept progressing.
Fuji-Xerox are moving forward again after the holiday season. It is expected that they will complete upgrades to
their own model compiler this week.

**Dean  -**
SAAB is planning to upgrade to the latest BridgePoint from 3.7. This has been prompted by the addition of a few long-
awaited features; inter project constants, enhanced association handling in the Explorer tree and by increased Verifier
performance. Per Johnsson is actively working on xtUML.org creating editor enhancements. He expects others to begin
personal projects before long.

### Customer Support Status
**Robert -** 
Not a great deal of activity to report. Fuji-Xerox had a build issue where a Unicode whitespace character was appended
to the name of a variable such that it had a different name. A solution involving some filtering in a Perl script was
provided, but this is recognized as incomplete. What is really needed is to provide a warning when such Unicode white-
space is found.


---

---

    Date:     date
    Start:    6:00 GMT+@
    End:      6:40 GMT+2
    Elapsed:  40
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Bob  -** 
The Upcoming release is going to be 4.1.10.  
It's going to be done by the end of the next week.  
Bob has to deliver something to Ericsson it had to go to them on Friday so they would start testing.  
It could be in the form of nightly build or branch build.  
The main feature of that deliverable is the true headless prebuilder.  
Cort asked to see a version that has every thing in it.  

There's something that was promoted was Heba's issue it is not the most critical 
but SAAB wanted it in the release.  
 
He had separated the parser front end from the eclipse editors.   
The parser front end was tightly coupled in UI text to UI eclipse editors and UI editors.     
The parser didn't need workbench nor GUI things.  
So he broke that away, by copying classes and refactoring.  
It went fine but he did hit problems with model compiler tho.  
The original requirement is to enable the user to set up configurations and run them from the command line.  
So the whole build had to work not just the pre-builder.  
The model compiler coupling with the eclipse UI is a problem spot.  
He tried some different solutions to break that up but there's no easy way to do so.  
Cort asked him to clean what he has regarding the pre-builder separation and raise and issue for the remaining work to handle that when needed.  
He's now is refactoring the code and removing duplications but he's facing some problems with the testing.  
He's wrapping it up.  

If Travis's work is ready it will be included.  

Ericsson is not pushing, Bob will deliver either on Friday or on Monday.  


**Keith  -** 
**OpenAir  -**

Now we officially switched to open air for time reporting.  
Claudia sent a document describing the steps to be done.  
That document included a temp. password that worked for some people and didn't work for other's.  
If your password didn't work you should contact her.  
That document indicated attaching a spread sheet file but that's not required.  

Once you are inside Open air you will be presented with a login page.  
You fill fields in the first page as shown in the document.  
Then you hit save on that page, you'll be taken to a time sheet page.
In the time sheet page you are required to fill the fields with the hours spent with each task.  
Every one is asked to fill time sheets in hour increments.  
Every one last week spent one hour on the presentation given by Keith learning how to set up training material.  
Keith is suggesting every one should keep track of their hours on a daily basis.  
They can be then submitted each week.  
The hours of each month should be submitted before the 25th of that month.  

**Keith status  -**
Been working on promoting the IPR constant support issue.
He has been on the training material work.  
He set up a course on OpenLearing "introductiontoxtuml".  
He started with the installation video, he will be uploading the videos to you tube shortly.  
He will add then the data to the OpenLearning course pages.  
He added several new pages including a quiz.



**Travis  -**
He's cleaning up the branch he created while he was on site.
He's going through automated tests, he added some testes that was described in the note.
He's facing some test problems, in verifier test, that doesn't seem to have a direct relation to the work he did.
He's hoping to finish running the tests tomorrow before he has a code review. 

Keith had the same problem with the verifier test during the promotion of Heba's issue.
He thinks verifier test went back to the state of getting in the way.
Bob had another branch, he had two failures but he doesn't think they are verifier related. 
Travis has troubles with the realized external entities tests.  
Bob thinks it's because Keith is running on VM and that's related to their bad performance.
Campbell runs tests on VM too but he didn't see a difference in the results.
It depends on how the promotion goes whither Travis's branch will be included in Bob's delivery or not.
But Ericsson does have the work in their hands already because Travis has given them a branch.  


**Campbell  -**
He did not really have much status to report because of his family issues there.
This week, He started to script training video material for Verifier.
The previous week He was giving training classes at SAAB.
It was mentioned to him that Grippen projects are now going to be using BridgePoint, which is quite exciting, since the Grippen aircraft is SAAB's flagship product.


**Nehad  -**
Finishing the tooltip work, the work is delayed due to  found more cornor cases failure  during the tests
Failures are fixed and in branch, and finishing the design note and manual tests steps

**Heba  -**
Been working on the model compiler work for the issue of adding port reference support and did put it ready for promotion.
Been working on an SVX related issue, Dean reported that he can't use timing values in microseconds as oposed to the existing milliseconds support.
She looked into it but it appeared that this is a limitation from the SVX APIs side.
She confirmed with AbdelHalim. he stated that this is solved in the newer version of SVX but that version was not released for java yet.
So she will have to write the java wrapper herself after the work for the release is done as recommended by Dean.

**Cort  -**
He's currently on FTO.
He's been working on Ericsson with T1 learning activities .
He's been doing model compiler work for Fuji Xerox 
the customization for their model compilers 
He was working close with MGJ site.



### Marketing Status
**Dean -**  
He stated that with the expanding use of Bridge Point within Ericsson, there comes some pros and cons.
In terms of the direct connection we are going to see ourselves expanding beyond Budapest and Croatian team. 
From Bob's comment it sounds we start to include Canadian team in Montreal.  
Dean Encourages every body to interact with them if they got an opportunity.  
With the expansion many groups will act independently.
There will be more user interaction and more training activities.

Preparing for a series of Mentor sponsored events that will be targeting North American mil-aero.
 
### Customer Support Status
**Robert -** 
He stated that we had the first BridgePoint call from US in two years.
Someone submitted an SR trying to run docgen on Ubuntu, we don't support it officially but Keith is giving him help going through the debugging process.  
The sale was made two weeks ago.  
They are a user of the editor for couple of years now.  
They just purchased model compiler and verifier.   



---

---

    Date:     16/4/2014
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
---

    Date:     04/09/2014
    Start:    9:00 MST
    End:      10:00 MST
    Elapsed:  1 hour
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Keith -** The upcoming patch release will be 4.1.10.  Focuses on providing
key features for Ericsson, CLI prebuild and compare/merge.  We are shooting for
an April 25th date.  Just a patch release but we still need to go through most
of the release process.  Travis is working on testing for the compare/merge
work.  Bob is working on development of the CLI pre-builder.  Nehad will have
the tooltip changes ready for this patch release.  Heba will have the port
reference work ready for this promotion.

All work should be handed over by the evening of April 24th.

Cort: Would like a nightly build in the customer's hands one week before the
24th.

### Webex training material

We will provide self tool training.  This meets Ericsson requirements but also
helps all customers.

Bob: 

6.9 Add reflexive navigation
6.9.18  Why is action language homes in this section?  
		  This section describes where action language can be used.
Should add command line interface topic.
Cover both linux and windows during installation and include discussion about
licensing.

Travis:

Model testings - We already have long videos for this, we could index the
existing videos pointing to parts of the video

Realized code execution

$t function

Include video showing an open source build and tool modification.

Nehad:

- Show cheat sheets
- Show spot light
- Best practice for model building and verifier
- Should we enhance the current cheat sheets, one for all videos?

John:

- Video ads take more time than the actual content.  Can we remove the ads
before we integrate them into the training course?
  
- We will strip them from the action language series.  We do want some sort of
  transition between sections.
        
Robert:

CSD is changing the business model, trying to provide pro-active support.
Each SR now has a value assigned that determines the cost.  This will be used
to determine the amount of revenue brought in.

April 2012 - April 2013 - 80% (SR raise rate vs fix)
April 2013 - April 2014 - 60% (SR raise rate vs fix)

Management sees the product as being expensive to support if the number of SRs
raised is too high.

We raise SRs just to track issues addressed.  These need to be understood and
excluded from the expense calculations.

---
---

    Date:     04/02/2014
    Start:    6:00 GMT+2
    End:      6:37 GMT+2
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status  

 **Ericsson pending issues and 4.1.10 "patch release" -**   
- Bob is working on the main issue of making the CLI build truly headless because currently it requires eclipse workbench to be present.  
- Add support for constants via IPR issue is going into the release.  
- The expected date for this release would be April 18ths if possible 
  but Bob is still going to discuss that with Keith.  
 
**Summary of on-site time in Croatia** 
-Travis:
  It was good.  He was able to help them understand the current limitations and our plans.  
  They accept that they must wait for the logical model integration.  
  They were also very pleased with the changes that He gave them.  
  They feel more comfortable using it now.  
  They aligned my thoughts and what they need pretty well.  
  He was able to show them how to use the open source model compare code to debug an issue one of the Budapest guys was having (that impressed them). 
  It will be helpful in the future.  
  
  Compare/Merge to them is the highest priority, they need it to be very easy and fool proof.  
	Issues fixed (probably more):
	dts0101042909 - Fixed  
	dts0101042915 - Fixed  
	dts0101009925 - Fixed  
	dts0101040652 - Fixed  
	dts0101009924 - Fixed  
	dts0101039702 - Fixed  
	dts0101039330 - Fixed  
	Problems created for integrity issues automatically, after model modification and merge (prototype delivery)
    Fixed issue with the jgit autocrlf=true configuration option
    On merge files are written even when the user does not perform a merge (wants to keep the left data), this removes the git annotations from the file.
    Fixed an issue where state machine elements description hover was not working.
    Modified the tool so that key letters take on the class name with spaces removed.  They also keep synchronized until the user modifies the key letters manually.  
	Robert has identified at least 70 merge test variations, He will work with them to create test matrices which we will handle the test implementation for.
    Went over a list of over 20 bugs that Marijan has written down but has not raised SRs for yet.  Had him raise issues for the larger ones.
    Something interesting was discussed, a java model compiler.  They were not aware that we had one and have been working on their own.  They asked for it, told them it was currently closed source.  Something we need to keep in mind.
    Fixed an issue where we were losing model change notification during file replacing (bug was that diagrams were orphaned and had to be closed and reopened.




**Demo of new hover text feature -** 

Nehad showed the tooltip enhancement 
Tooltips shows when the mouse hovers over a canvas element 
When user clicks inside the tooltip window, a detailed tooltip window is painted 
with scrollbars,resize, move features and toolbar items
Making it editable or not is still being investigated 
The remaining work is adding icons to toolbar items, fix Ericsson issue for missing tooltip window, and minor fixes for tooltip shell.


**Fuji-Xerox model compiler customization -**    
They recently bought some BP licenses and it includes the source version of the C-ModelCompiler
They are using that and they are making customizations 
They have accumulations of models IP that made them large models.
And because of that they can't use the compiled model compiler quick build times 
and they are running into memory limitations. 
 
Cort is helping them  in the short term just as working for a longer term strategy enabling them update the mc themselves
but he is making the first one on their behalf.

He received their archetype files yesterday, he's investigating them and will have a meeting with the Mentor Graphics Japan this week.



### Marketing Status
**Dean  -**   
Marketing activities we are happily involved in Engineering shows .
In three venues in Dallas , Seattle and long beach California. 
This show is  military focus and we will be showing BridgePoint , System Vision and discussing other SLE tools.   
"Connected Engineering" is this year slogan.
 
They are doing some preparation for the xtuml and some promotions to raise awareness
of the source code and build instructions are now available.  
The broader community is getting convinced we are more open source than vendor specific trying to recruit the broader community to participat.  

Ericsson T1 activity, one thing that is significant is the development of the new course material. 
Much of that is being leveraged through CSD and the consultant they hired.   
Ericsson may do trial running of the material in the may time frame.

 
### Customer Support Status
**Robert -**  
Had a number of ericsson issues mostly related to compare/mege
Had filed number of enhancement requests  that SAAB mentioned 
The most important request for Ericsson is the need for headless is to be truly headless.
Arguing with Sony they have a problem with Copy/Paste that doesn't look like it's our problem, could have been related to the OS having the clipboard locked 




---
    Date:     03/19/2014
    Start:    6:00 GMT+2
    End:      6:15 GMT+2
    Elapsed:  15 minutes
    Present:  Bob, Keith, Travis, Campbell, Heba, Nehad, Robert 


### Engineering Status
**Bob  -** 
- Helping in field issues coming from Cort.
- Wokring on promoting Keith work, there are junit tests failures to fix
- Resolved issue coming from Ericson (dts0101039702), and working on failed automated tests. 

**Keith  -**
- Pushed all the generated Java files to Editor repo. so BridgePoint Editor is open source now
- There still changes to be done (seperate metamodel properly so customers can built it)
- There are JUnit test failures, but we will not try to clean them up at this time.
 
**Travis  -**
- Preparing for Ericsson trip, leaving Sunday.  Will spend the entire week there.
- Completed all of the top priority compare issues, except two that will take too long.  Will discuss these with the ericsson team.

**Campbell  -**
- Working on MC-Java to remove dependency on the generator parser
- Implementing the new MC-Java changes on the build server
- Supporting Corts discussions with Ericsson about integrating with EMF based tools

**Heba  -**
- Finishing work on Port Reference issue 

All Junit tests pass, except one test because the port reference is not presisted in the file system after model upgrade ( Travis will help )

**Nehad  -**
- Fixed verifier automated tests failed in server.
- Presented a demo for tooltip enhamcenet, working to resolve some issues regarding window position for large text, window appearence and others.
- Creating a new issue/branch to import the work done in #135 for session explorer view mirroring part only
 
### Customer Support Status
**Rovert  -** text
- Using Java Model Access API to provide an example to Ericsson 
- Working on issues regarding documents we provide for creating realized component

---

    Date:     03/12/2014
    Start:    9:00 MST
    End:      9:35 MST
    Elapsed:  35 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, Jayne, others...

### Engineering Status
**Bob -** Looking at two issues on the current release list.  A fix was provided
that is causing unexpected test failures.  The release has been disclosed to
both Saab and Ericsson.  It has been delivered to both.  Saab raised an issue
regarding support for constants and IPRs.  Ericsson feedback has been good for
the release, they are happy.  The verifier performance enhancement was well
received.

**Keith -** Moved the development branch to 4.1.7.  Master is open and ready, we
should consider merging existing branches to the latest.  Working on moving
code to open source.  Need to split verifier data out of the Instance package.
All private model data will be placed into a new package named Engine.  Once
split is done and a build can be completed we can move to dumping the code into
a public git repository.  Hope to have some code in the editor repository by
next Thursday to show customers.

**Campbell -** Getting back into the MC-Java changes that prevent the need for
the generator parser.  Trying to get a successful build.  Had to add a full
BridgePoint installation on the build server.  Working through some issues with
CLI building.  We need to import projects into a real eclipse workspace.
Modifying the build scripts to call the necessary CLI operations.  Researching
EMF to help Cort prepare for next weeks meetings.

**Nehad -** Working on enhanced tooltips.  The note has been reviewed.  Had to
work around the eclipse infrastructures.  We will attempt to see if we can have
the eclipse team make some adjustments to prevent the work arounds.  Will start
working on the design next.

**Heba -** Working on getting the SVX issue into a promotable state.  Found a
couple of bugs that must be addressed first.  Provided Dean a branch to test and
assure everything is done.  Working on some unit test failures.

Keith: Are we planning to move the SVX work into the mainline code base and
process?   
Cort: We believe so but we need to have some discussions about it first.  Please
make sure that we have a meeting or code review so that we can have these
discussions.   
Heba: If anyone is interested a link to show what has been done can be provided.   

**Travis -** Working on existing compare/merge issues in preparation for the
upcoming Budapest trip.

**Cort -** For expense reports provide required information but keep it simple.
Minimize the time spent on the expense reports.  Concur supports a select all
when assigning a customer to expense.  If working against the tier 1 package,
keep time in no smaller than an hour.

Completed the final issue for the last release.  Main focus is continuing to be
the field contact with Ericsson.  Will be visiting Saab Monday and Tuesday of
next week.  A recent Toyota recall has sub-contracted Takao Futagami to look
into a possible modeling approach.  They will be looking into BridgePoint and
Simulink via an interview with Saab.  
Will be visiting Ericsson on Thursday, discussing open-source based BridgePoint.

Bob: Who are you meeting at SAAB?   
Cort: Will meet with everyone that we know at Saab.   
Bob: Who is Ronan Barrett?   
Cort: Ronan is the modeling czar for corporate Ericsson.  Ericsson has been
modeling for around 20 years, with most of these models still existing in
heritage formats.  IBM quit support on the rational rose tool.  Ronan is looking
at what set of tools will make Ericsson successful with modeling, while trying
to avoid vendor lock-in.  Looking for ways to open BridgePoint more while still
protecting the business model.  Will be looking at EMF.   
Bob: We have been working from the engineer level up to gain sales.  In the past 
the top down approach was not very successful.   
Cort: Ronan is not top-level management, but a top-level technical tool
researcher.  He suggests what is contained in a modeling tool-box.  Ericsson
management does see the value in model generation.  Ronan is a way to open the
door to more teams then we are currently supporting.    

### Marketing Status
**Dean 1 -** text

 
### Customer Support Status
**Robert -** Sony was confused about the dreaded "assembly connector". Maybe we
could do with a video AppNote about its use? Or maybe Sony was just hoping it
was implemented exactly as they wanted.  Dean or Cort can deliver a video
showing the functionality.
FXAT is likely in need of training as to the proper way to use revision control.

---

    Date:     Feb 5, 2014
    Start:    12:00 EDT
    End:      12:31 EDT
    Elapsed:  31 minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, others...

### Engineering Status
**Campbell -** Found and fixed Verifier's usage of a very large instance extents.  The design note contains a table that shows the major runtime improvements.  See https://github.com/xtuml/internal/blob/master/doc-internal/notes/146_dts0101019516/146_dts0101019516_dnt.md .  Also talked to the customer about improvements to their model which they implemented and have seen improvements from. Consider this table and ordering of execution tips for release notes or SupportNet

**Bob -** We have a number of issues fixed and a few in progress that directly relate to Ericsson.  In the longer term, Ericsson has requested a number of editor enhancements.  We still have to do analysis for a number of these in order to determine the best path forward.  Ericsson's top 3 will definitely be included in the short-term release.  The issues list we are all working from is now mostly Ericsson-focused.

**Cort -** 
  - We plan to deliver a patch or out-of-band release to Ericsson to show our support for them.  But, we don't want to put everything into this one release.  We want to continually show support and progress through the summer.   
  - There is such a demand for Training coming from customers that CSD wants to proactively get involved in helping get the training course updated.  It looks like CSD will help us with packaging as well as content (contracted outside resources).


### Marketing Status
**Dean -** Goal for the year was 3.5M in product bookings for SV and BP.  The BP part was 800K to 1.5M. Coming out of Q3 our numbers were light in all accounts.  But, in the end, we achieved a combined number of 3.5M + 50K.  BP contributed ~850K.  In the end, our booking numbers were pretty much right on, but the services side were ~80K light.

 
### Customer Support Status
**Robert -**  
  - Working with Campbell and Tamas at Ericsson to improve Verifier performance
  - Investigating a SONY problem where using eXceed in Windows to access a Linux BP install produces a "no such file" error
  - Worked with Ericsson to try to reproduce a "'Value:Value.computeValue() Error: Result not computed" error, but unable to reproduce in a debuggable way

---

    Date:     Jan 15, 2014
    Start:    12:00 EDT
    End:      time EDT
    Elapsed:  time minutes
    Present:  Bob, Keith, Travis, Campbell, Cort, Heba, Nehad, others...
            Dean, Robert, others...

### Engineering Status

**Campbell -**
- Ericsson Budapest reported very slow performance with Verifier. Campbell uncovered that Ericsson model produced 15000 instance population, so it is a very large model. Further testing found that significant portion of time spent deleting instances. Changes to strategy for locating and delete instances have been made and produced a 50x speedup. Still looking at this issue and Campbell feels there is more to find.
- There is potential to split this SR into several DRs and to provide Ericsson with a test build containing this performance enhancement. Timeframe will depend on Unit testing and interest from Ericsson Budapest

**Cort -**
- Working on an Analysis note for the revised training material. Changes initiated in response to Ericsson requirements but these changes will benefit all xtUML training and users of xtUML.org
- Material to be split into self-paced technique training and in-class methodology training.
- Team will be asked to participate in the creation of several 5-minute How To videos that collectively describe the mechanics of using BridgePoint.
- John Wolfe to produce high level outline for instructor led portion of the training.

**Keith -**
- Investigated an issue from FujiXerox related to non-ascii characters causing model compilation to fail. Analysis note reviewed and completed work has been delivered to MGJ for final testing. Expect customer to receive it later this week.
- Change had the potential to break DocGen support when non-ascii characters are present in the model. Keith has identified a DocGen change that will resolve this potential issue.


### Marketing Status
**Tier 1 Services -** 
- Dean is working the the Ericsson and Sony account teams to construct a services proposal that will target each customer's top 5 SRs and accelerate engineering closure of the issues. Ericsson proposal is in review by Mentor Finance and Sony proposal is expected to be sent to account team next week.

- SKO preparations are in high gear and BridgePoint will have its own 90 minute breakout session. 

 
### Customer Support Status
**Ericsson Performance issue -** 
Worked with Campbell to fully characterize issue and now waiting for opportunity to test fix.

**Ericsson OAL Bridges binding issue -**
Working with Campbell to fully characterize the issue and DR is expected soon.

**Japanese character support -**
This is a high priority issue with FujiXerox actively contacting MGJ CSD. Keith's solution will be welcomed by Robert's counterpart in Japan


---

    Date:     Jan 8, 2014
    Start:    12:00 EDT
    End:      12:45 EDT
    Elapsed:  45 min
    Present:  Bob, Keith, Travis, Campbell, Cort, others...
            Dean, Robert, others...

### Engineering Status
**Cort -**
- Delivered compiled MC in 4.1, including performance improvements.  Feedback from Saab for a model dealing largely with string data: the speedup was very nice, but some expressions and param lists are exceeding some buffer limitations in the mcmc (size greater than 870 chars). They indicate the max is probably 2000-3000 chars. We are making progress on a dynamic memory allocation solution.  Worked on a bit over the holiday and will finish late this week or early next week.  This could be made into a marking feature.  Will discuss with marketing.

**Bob -**
- Ericsson engineering still updates their priorities list (last did it 1/7/14).  Robert Rice triages the list.  We have tried to start always using the BCC SR email CC to have e-mail conversations go into SR tracking.
- Working with Heba on Saab request analysis.
- Working on an Ericsson issue for dynamic array size being 1 when it should be 0
- Will set up a meeting to talk about CLI and code split.
  
**Keith -**
- Not much has happend on MC-PaaS since the last update.  Will be synching up with Willie and getting this moving again next week.
- Working on another encoding problem that came from Japan.  Similar to the last one with Japanese chars at the end of an activity field.  This time the problem area is description fields.
- Met with TheOtherFirm to discuss new enhancements to xtuml.org they are giving us to test.  These include podcasting, mail blaster integration, and a daily digest feature for the forum.
- Working on removing the long-deprecated mc.mc3020 plug-in.  The changes to remove it are done.  Hit some problems during unit testing.  Still in progress.

**Heba -**
- Working on Saab request that has to do with Ports not showing on component refs in session explorer.  Analysis note ready for review. The lack of ports is confusing when they component using the same interface more than once.
- SVX work is done.  Created a demo video.  
  
**Nehad -**
- Saab requested to have the ability to show the state machine diagram aside to state event matrix, I spent sometime on it tryin to find out if GEF can allow this, but I could not find easy way, and meanwhile I found a workaround for this using Eclipse functionality. Bob sent Saab mail to ask their acceptence regarding this workaround and we did not hear from them since that due to Holidays 
- There was a previous work done to enahnce the variable view, by displaying more info for model classes instance, one of these enhacement to list all related instances with the selected instance, this work is brought up because of customer request to display the associations text phrases in SE.  The work is almost done, a design note was previously reviewed. 
- Currently working on an issue reported by both Ericsson and Saab, dts0100533805, and have some difficulty to reproduce it.

**Campbell -**
- Picking up high priority Ericsson issues.  One is a "Works as designed".  Also looking at Verifier performance.  There is no immediate smoking gun, so investigation is ongoing.
- Will talk to Cort about priorities and how that affects open tasks like training, issues, and code split. 
  
**Travis -**
- Working on issue to show text phrase in session explorer.  Testing now.
- Over my vacation time I introduced declaration searching.  The support allows for entering text and searching for that text in named elements.  There are limitations in that the element will have to have either a Name attribute or a get_name operation.  Additionally there should be a "Show in Model Explorer" or "Show in Canvas" action.  There is also an issue with double clicking to open, but that should be fixed easily.  This is one item on the report from Ericsson.  I estimate that it would be 4 days to promote the issue (including documentation and tests).  

### Marketing Status
**Dean -** 
- Getting things ready for Sales Kickoff
- Pushing to get deals done for this quarter, Sony and Ericsson Tier 1 Services
 
### Customer Support Status
**Robert -**
- Ericsson and Saab have now returned from holiday and are opening issues. No suprises so far.

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
  
