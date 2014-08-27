# Ericsson Trip report

Keith Brown
2014 - 5/10 to 5/16

__Primary Objective:__ Deliver new two-day training course for "xtUML Editor Developer Training".


__My Thoughts:__
- For the first delivery ever of the course, I thought it went OK.  I told the students at the 
beginning that there would be a mixture of video and live lecture.  The video lectures worked OK
since I had contacted the Ericsson training staff and requested some external speakers for enhanced
sound.  The videos were short (<4 mins), so we generally watched the video through, then would replay
with stopping to allow the users to follow along.  Sometimes we just watched through and did not 
replay the video.
- There were 14 attendees
  - ELTE: János Hack (zodiakus@caesar.elte.hu)
  - BME: András Vörös (vori@mit.bme.hu), Oszkár Semeráth (semerath@mit.bme.hu) 
  - ETH: Ádám Balogh (adam.balogh@ericsson.com), Adam Darvas (adam.darvas@ericsson.com), Antal Wu-Hen-Chang (antal.wu-hen-chang@ericsson.com), Ferenc Bozóki (ferenc.bozoki@ericsson.com), Gábor Bátori (Gabor.Batori@ericsson.com), Gergely Dévai (gergely.devai@ericsson.com), Zoltán Jász (zoltan.jasz@ericsson.com), Máté Karácsony (mate.karacsony@ericsson.com)
  - ETK: Marijan Zemljic (marijan.zemljic@ericsson.com), Nenad Ukic (nenad.ukic@ericsson.com), Robert Inkret (robert.inkret@ericsson.com)
- Some users came wth xtUML Editor already installed.  Thus, they were bored for the first 45mins of
the course while we got other users set up.
- Some users had problems installing the developer add-ons file.  One had ```<``` translated to ```&lt;```
in the downloaded file.  Some users hit proxy issues inside eclipse trying to install.  I helped 
debug the character problem.  The Ericsson developers deal with these proxy issues regularly due to
Ericsson's IT strategy and the students worked through the problems by copying settings from working
machines.
- It took the first 2 hrs of the class to get everyone to the point of compiled source code from user
forked repositories.
- Took the next hour to do the debug lesson and give the (live) tour of OOAofOOA.
  - This part will need to be recorded eventually for the online class
  - It is hard to give a tour of the OOAofOOA to a room full of users who haven't ever looked at it before.
I emphasized that key packages they need to concentrate on right now are Domain, Component, Element Packaging,
Packagable Element, and Subsystem.
  - I talked about Specialized vs Generic Packages and how OOAofOOA will be moving to GPs and have a lot of the
current model stripped out.  Hopefully in the near future.
- This got us to lunch on the first day.  After lunch we did a quick quiz on the 5 key packages, then I did the 
source code tour.  Again I focused on the high points.  Then we dove into a number of exercises
  - First we did the "Change default attribute type to string" exercise (took ~30 min)
  - Next we did the "Perform a selection in OAL and Java" exercise (took ~45 min)
  - Last we did the exercise to "Create a new Context Menu Entry" (took about 2 hrs).  There are two branches on
xtuml/editor that show the CME example as a skeleton and with the details filled in to fulfill the assigned task.
  - Of course, I walked around and offered input and helped users move along
  - Not surprisingly, some users sped along faster than others.  It would be good to have additional work to
keep them busy.  As it was I know they just toyed around exploring the code and model on their own.
  - This took us to the end of day 1
- On Day 2 we started with exporting the modified utilities plug-in.  Some users hit Out of Memory errors 
doing this step.  Restarting fixed the problem.  We then worked through branching, merging, pull requests and
JUnit testing (including running some tests)
- We then talked about MC-Java via a PaaS solution
- We also discussed xtuml.org, the BridgePoint team's development process, and what is coming down the pipe as
far as new editor code and installers
- Next we did an exercise to set up a "git remote repository" and pull in Travis' work for auto component creation
- Finally we did an exercise to create an all new builder plug-in and make it output some information about the 
model being processed to the console.  There is a branch "training_example_builder" on xtuml/editor that shows what
the finished student content should look like.
- The content on the second day was a bit light IMO and could use some more teaching material
- Had a discussion with Ronan Barrett
  - He had attended parts of the course via video conference
  - He feels like MC-Java must be OSS for us to claim that xtUML Editor is OSS
  - He really wants us to be on EMF
  - He is concerned about the cost of maintaining many forks or private extensions.  He wants to understand
how we intend for them to be able to extend bp.core / Metamodel with their own private changes that has its
own lifecycle.  (SKB: We need to figure this out because it applies to how we will manage our own verifier and
MC extensions to the MM)
  - He would like to see more documentation about BP extension points such as menus
  - He is very interested in the next-level developer course that talks about modifying the metamodel, using
MC-Java, and extending MC-Java
- __Neno:__ is working on using Jenkins for creating a build environment.  He wants to improve the build speed
by parallelizing project builds.  Right now, all projects are in one workspace.  We talked about having multiple 
workspaces and using CLI Import or Git project references to build in two workspaces at once.  He plans to move
to git from SVN, hopefully yet this year.
- __Robert, Marijan:__ are reviewing model changes done by the Montreal team.  Have not been doing much in the way
of application modeling recently.  Would like to do more.  Both are interested in understanding the work that needs
to be done for the Ericsson model-based model compiler.
- __Ericsson MC Team:__ have a vision for what they think they need to do to implement the populate half of the mbmc,
however, many pieces are missing.  I wonder if they would be better off copying/reusing what we have already done for
MC-3020.  They have not made any progress on their mbmc since Cort was there in early March.
- __Antal:__ uses Conformiq to model tests in sequence diagrams.  Generates test functions that he incorporates into the
application model.  The test functions are very OAL intensive and thus Verifier takes a long time to start up.  I wonder
if we could parse each function the first time it is run in Verifier rather than parsing all functions at load time? In 
this way the user pays the parse time in a bunch of small pieces rather than one big chunk at Verifier start.
- __Gergely Seres:__
  - Wonders... as we plug-in xtext as a new editor, how hard would it be to plug in some different canvas
editor?
  - Says Ericsson has lots of modeling fragmentation (Rose, Papyrus, BP).  He wants to find commond ground/technologies that
would allow them to join these efforts together.  He says the goal is not to make it so Ericsson has a "free" toolchain, but
to allow modelers to work together and grow modeling as a development process within the company.  He sees EMF & Ecore as a 
path to do this.
  - He wondered about our strategy for controlling additions to the metamodel from contributors.  He also wondered how we 
envision users making private extensions to the OOAofOOA (like our Verifier or MC extensions) when the OOAofOOA is truly
independent and has its own lifecycle separate from the lifecycles of these private extensions.

__Student Comments:__
- Remove the BridgePoint Odometer view from showing by default (Marijan)
- Change keyletters on OOAofOOA classes to <name>_c form to match generated class.  Current scheme
of keyletters is not descriptive enough. (Marijan)
- The MC-PaaS demo video goes through steps way too fast.


__Ericsson Interest Areas:__
- Attendees were very much interested in learning how to import data from a file type of their own choosing.  They
expessed intent to write their own XMI import and or import from other files.  At least 6 people were interested in this 
are and got started on projects to do this on the "programming challenge day".  This led quickly into questions about 
how to programmatically create xtUML model elements and graphics.   Travis supported these efforts (particularly the graphics
questions) as this is non-trivial.  We may need to extend our API in this area.
- Adam Balogh got pretty far on an implementation of drag and drop for class attributes.  He is tired of endless "move up/down" 
clicks and really wants D-n-D support.
- Robert Inkret and Marijan made good progress editing generated code to make class-based operations show up differently on the
canvas and in ME, which is something they really want
- The two university students are very interested in Functional verification of xtUML models.  They need access to the model
data to do a model to model transformation and also the OAL instance data to put it into a different form in their FV tool.  I 
put them into contact with Dean.
- Adam Darvas showed me a BridgePoint integration with Quick Sequence Diagram Editor and even gave me the Java source code
he wrote for this.  It was pretty neat.  I captured a video.  It would be even better if he could attach it into verifier
rather than via EE calls in his application model.
- Several people are interested in how to edit and extend the parser as well as access OAL instance data

__My Additional Notes:__
- In the video where we download and import the source code, in the Import Projects dialog, add a 
voice note to "uncheck search for nested projects"
- Link with Editor does not work when you click on an imported class on a diagram
- In "HOWTO build xtUML Editor" doc, the instructions for setting the window preferences need to be updated for Win7
- I gave them the code for the function creation tool bp.core.function.gen and talked about how it could be used
and extended
- Need to get the videos uploaded to YouTube and fill in the holes with videos where I did live presentations.  Then get it into an online course system.
