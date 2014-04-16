---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Self-Paced Training Infrastructure
### xtUML Project Analysis Note



1. Abstract
-----------
The xtUML team needs to establish a preferred infrastructure for the creation 
and maintenance of online, electronic self-paced xtUML training as a pre-requisite
to beginning the work [3].

We shall consider best-in-industry tools including cousera.org and WordPress.  

2. Document References
----------------------
[1] Issues 167, https://github.com/xtuml/internal/issues/167    
[2] CQ DEI dts0101037431  
[3] Issues 166, dts0101037420 - xtUML Self-Paced Tool Training  
[4] Training Collateral Enhancement - Analysis Note, https://github.com/xtuml/internal/blob/d78935e061defd0ce0a758d5537d2ebfa432b947/doc-internal/notes/141_dts0101022089_training/141.ant.md  

3. Background
-------------

4. Requirements
---------------
The requirements for the online training platform are set forth in [4].  

5. Analysis
-----------
5.1  Definitions  
  - LMS : Learning Management System
  - MOOC : Massive Open On-line Course

5.2  Overview of available course delivery sites
  - __Blackboard (http://www.blackboard.com)__
    - Pay them to host your course.
    - Has course delivery, content managment, community engagement, assessment
  
  - __edX.org (http://www.edx.org/)__
    - Course delivery site for partner universities and select non-profits & corporations (the xConsortium)
    - Site built on their own OSS course deliver framework
  
  - __mooc.org (http://mooc.org)__
    - Collaboration between google and edX for non xConsortium members to build and host courses
    - "mooc.org is an edX destination. We're working to help educational institutions, businesses and teachers easily build and host courses for the world to take."
    - Looks like it could be very interesting, but not yet live.  Opens "first half of 2014"
  
  - __P2PU - Peer to Peer Univeristy (https://p2pu.org/en/)__
    - [wikipedia] Peer to Peer University (P2PU) is a nonprofit online open learning community which allows users to organize and participate in courses and study groups to learn about specific topics.  P2PU offers some of the features of massive open online courses, but is focused on people sharing their knowledge on a topic or learning about a topic offered by another user with a DIY wiki-type mentality.[3] Unlike typical massive open online courses, anyone can create a course as well as take one.[4] Additionally, because of its less hierarchical nature, P2PU activities need not necessary be Courses; the admin of the learning environment can select from Study Group and Challenge as well as creating their own term.
    - P2PU is hosting and coordinating the MOOC joint-venture Mechanical MOOC which is a blend of open online resources. The first Mechanical MOOC class will be “A Gentle Introduction to Python,” which is part M.I.T. OpenCourseWare, instant-feedback exercises and quizzes from Codecademy, and study groups organized by OpenStudy, while P2PU handles central communication such as email and discussion.
    - Wiki-style (formatted text and images), no videos
    - No built-in quiz facility
  
  - __Coursera (http://www.coursera.org)__
    - For-profit company offering online classes from partner universities
  
  - __Moodle (https://moodle.com/)__
    - various pieces:
      - On open-source e-learning platform (moodle.org)
      - moodle.net contains courses users can download and use
      - moodle.com: company that does the primary development on moodle software
    - There are sites such as freemoodle.org that will host courses.  Of course, different hosting sites, have different restrictions.
  
  - __ALISON (http://alison.com/)__
    - [wikipedia] ALISON (Advance Learning Interactive Systems ONline) is an e-learning provider founded in Galway, Ireland in 2007 by serial entrepreneur, Mike Feerick.[3][4][5] Its stated objective is to enable people to gain basic education and workplace skills.
    - Adobe After Effects class seems similar to what we have in mind for xtUML training. (http://alison.com/courses/Adobe-After-Effects-How-to-create-Motion-Effects-and-Visual-Graphics)
    - How it works: (from http://alison.com/subsection/?section=about&page=18)
      - Free to learner
      - Fees: Nominal fees are charged to use the ALISON Manager, the service that allows educators to create and manage learner groups. A learner group enables you to manage up to 50 students in the group.
      - Sponsorship: Much of our 'free to learner' content is sponsored by partners, who provide it free-of-charge because they're happy to see it made widely available. Other content is provided by publishers who earn a share of the revenue we generate through displaying their materials.
      - Parchments: Learners can purchase your Certificate or Diploma after completing your course. Many learners opt to do this but there is no obligation and you can still complete your learning in full. ALISON is and will remain a free site for the individual learner.
    - Courses must be taken on an internet-connected web-browser
    - The content delivery mechanism, based on poking around a bit, is not real impressive.  Adds between page transitions.
  
  - __Udacity (http://udacity.com)__
    - For profit company 
    - Charges course providers for the tools used to create the course and the hosting
  
  - __openlearning (http://www.openlearning.com)__
    - For profit company
    - Students can take courses for free or fee
    - "Public and private online courses from educators and individuals worldwide."
    - "Easily distribute video, audio, PDF documents, presentations, files, and images through course modules. Include activities and assessments, automatically marked quizzes/tests and group work."
    - "Third Party Integration.  Extract and update course data, student information, and grades for your courses with our APIs.  Include videos from YouTube, BrightCove & Vimeo.  Embed content from SlideShare, Google Docs, Maps, Twitter, DailyMotion and more!"
    - Additional info about course creation: https://www.openlearning.com/courses/create/?option=institution

  - __Eliademy (http://eliademy.com)__
    - Sells access to businesses to keep their training private
    - Free for students to access open classes
    - [wikipedia] Eliademy is a free online classroom that allows educators and students to create, share and manage online courses with real-time discussions and task management. Eliademy is based on Moodle (support import of course in Moodle format), Twitter Bootstrap and other open source technologies.
    - [eliademy] "Instantly create and edit online courses, forums and quizzes, share documents and embed any multimedia content straight into your course. With our state of the art visual editor your course will look like an expensive study book with just a few clicks."

  - __OpenCourseWare Consortium (http://www.ocwconsortium.org/)__
    - University focused
    - Course provider has to provide the content delivery service
  
  - __mylearningworx (http://mylearningworx.co)__
    - UK-focused
    - crowd-sourced content
    - classes are not free
  
  - __WordPress plug-ins__ There are WordPress plug-ins that provide Learning Management Systems (LMS)
    - WP Courseware (http://wpcourseware.com/)
    - WooThemes Sensei (http://http://www.woothemes.com/products/sensei/)
    - Train-Up! (http://http://wptrainup.co.uk/)
    - ~~TalentLMS WordPress plugin~~ - _It starts from $29/month for 25 users and a cost of $99/month for 100 users._
    - ~~BuddyPress Courseware~~ - _No longer maintained_
    
  - __Mentor Graphics Course System: (http://todo)__
    - Built in to mentor.com
    - Run by Mentor Educational Services & Customer Support

5.3  Interesting/Useful Links
  - How MOOC Video Production Affects Student Engagement: https://www.edx.org/blog/how-mooc-video-production-affects
  - 10 Steps to Developing and Online Course: https://www.youtube.com/watch?v=JKbPNx2TSgM

6. Work Required
----------------
6.1  Much of the externally-hosted MOOC world is being driven by universities starting to put course content online.  The sites built on this philosophy are too restrictive for our needs.  Based on the requirements stated in [4], the following are most appealing:
  - __openlearning (http://www.openlearning.com)__
  - __WordPress plug-in, WP Courseware (http://wpcourseware.com/)__

7. Acceptance Test
------------------

End
---

