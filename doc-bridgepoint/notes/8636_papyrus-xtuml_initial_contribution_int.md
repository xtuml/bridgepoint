---

This work is licensed under the Creative Commons CC0 License

---

# Papyrus-xtUML Initial Contribution
### xtUML Project Implementation Note

1. Abstract
-----------
This note details the Initial Contribution (IC) submission of source code to
the Eclipse Foundation for the papyrus-xtUML project.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8636](https://support.onefact.net/issues/8636) Papyrus-xtUML -- Submit Initial Contribution  
<a id="2.2"></a>2.2 [Eclipse IPzilla CQ #11865](http://dev.eclipse.org/ipzilla/show_bug.cgi?id=11865) Contribution Questionnaire for Initial Contribution  
<a id="2.3"></a>2.3 [A Guide to the Legal Documentation for Eclipse-Based Content](https://www.eclipse.org/legal/guidetolegaldoc2.php)  
<a id="2.4"></a>2.4 [Guidelines for the Review of Third Party Dependencies](https://eclipse.org/org/documents/Eclipse_Policy_and_Procedure_for_3rd_Party_Dependencies_Final.pdf)  

3. Background
-------------
The Eclipse IP team must approve the initial contribution of source code
before it is checked into the Eclipse repository.  This process is tracked by
a Contribution Questionnaire [[2.2]](#2.2).  A first submission was made and 
comments were received.  This note details the work done to address the
comments.

The `bridgepoint` and `mc` repositories were submitted as zip files.  The
following test and documentation files were excluded from the submission:
<pre>
mc:
doc
mcmc

bridgepoint:
doc-bridgepoint
src/libTRANS
src/org.xtuml.bp.debug.ui.test/models/RealizedClassTest.zip
src/org.xtuml.bp.welcome/models/GPS Watch.zip
src/org.xtuml.help.bp.mc/doc.zip
src/org.xtuml.bp.ui.canvas.test/swt-win32-2135.dll
src/org.xtuml.bp.ui.canvas.test/swt-win32-2136.dll
src/
org.xtuml.bp.als.oal.test
org.xtuml.bp.core.test
org.xtuml.bp.debug.ui.test
org.xtuml.bp.io.mdl.test
org.xtuml.bp.model.compare.test
org.xtuml.bp.search.test
org.xtuml.bp.ui.canvas.test
org.xtuml.bp.ui.explorer.test
org.xtuml.bp.ui.properties.test
org.xtuml.bp.ui.text.test
org.xtuml.bp.welcome.test
org.xtuml.internal.test
</pre>

4. Requirements
---------------
The requirements are generated from the comments given by the Eclipse IP team
after the inital submission.  These comments along with our responses have been
incorporated into section 5.

5. Work Required
----------------
The following sections represent each comment from the Eclipse IP team after
submitting the initial contribution (comment 23 of [[2.2]](#2.2)).
Each comment is followed by our response and the work that was performed.  Any
of the following items still needing attention are addressed in section 6.

5.1  Copyright and License Headers need to be amended as we understand the
project will be dual licensed at Eclipse under the EPL 1.0/ASL 2.0.  We can
provide those headers to you as well as About File templates, etc. in the very
near future.

* OK.  That would be great if you send the required headers.  

  * The templates were sent and are attached to the issue [[2.1]](#2.1).  No
further action taken yet, see section 6.1.1.

5.2  The PDF Files that contain license information that is not the project
license should be removed.

* We can simply remove all the license PDFs in org.xtuml.bp.doc/legal/

  * Mentor Graphics required that the PDF versions of the third party licenses
be part of the distribution.  Eclipse does not.  Files removed from the
bridgepoint repo:
<pre>
src/org.xtuml.bp.doc/legal/apache_1.1.pdf
src/org.xtuml.bp.doc/legal/apache_2.0.pdf
src/org.xtuml.bp.doc/legal/boost_1.0.pdf
src/org.xtuml.bp.doc/legal/cddl_1.0.pdf
src/org.xtuml.bp.doc/legal/cpl_1.0.pdf
src/org.xtuml.bp.doc/legal/cygwin_api.pdf
src/org.xtuml.bp.doc/legal/eclipse_1.0.pdf
src/org.xtuml.bp.doc/legal/gnu_free_doc_1.2.pdf
src/org.xtuml.bp.doc/legal/gnu_gpl_2.0.pdf
src/org.xtuml.bp.doc/legal/gnu_gpl_3.0.pdf
src/org.xtuml.bp.doc/legal/gnu_lgpl_2.1.pdf
src/org.xtuml.bp.doc/legal/gnu_lgpl_3.0.pdf
src/org.xtuml.bp.doc/legal/gnu_library_gpl_2.0.pdf
src/org.xtuml.bp.doc/legal/oracle_binary_jre_javafx_6.pdf
src/org.xtuml.bp.doc/legal/perl_artistic_2.0.pdf
src/org.xtuml.bp.doc/legal/w3c_2002.pdf
</pre>

5.3  Update-copyright.pl contains copyright to Jari Aalto under the GPL.  This
content will need to be removed as that is not an approved license for Eclipse
distribution.

* This is simply a utility we use very rarely and it's not really part of
papyrus-xtuml itself.  We'll remove it from the submission.

  * The following two files were removed from bridgepoint.  They still exist
in the xtuml/internal repository if needed.
<pre>
utilities/update-copyright/test_input.txt
utilities/update-copyright/update-copyright.pl
</pre>

5.4  Many files indicate they contain proprietary and confidential information
and are not for external distribution.  A sampling of these files follow:
```
mcooa/models/mcooa/ooaofooa/Activity/Activity.xtuml  
mcooa/models/mcooa/ooaofooa/Association/Association.xtuml  
mcooa/models/mcooa/ooaofooa/Body/Body.xtuml  
mcooa/models/mcooa/ooaofooa/Communication/Communication.xtuml  
mcooa/models/mcooa/ooaofooa/Component/Component.xtuml  
mcooa/models/mcooa/ooaofooa/Constants/Constants.xtuml  
```
As you can imagine, these are not terms associated with open source.

* This is heritage from the formerly closed-source nature of this project. 
We will remove these notices.

  * Notices were removed.
  * Note the ooaofooa was not flagged because the "proprietary and confidental"
language was not present.

5.5  About.htmls indicate the use of the software is subject to license
restriction and read this license agreement.  This needs to be removed and
replaced with the standard Eclipse About Files.

* OK, more housekeeping.  We will replace as suggested.

  * The about.html files of all of the plugins were replaced with the provided
template.

5.6  Generally there appears to be many files that do not contain any copyright
and license headers.  Perhaps these are generated?  If so, we do expect
generated content to contain these headers.

* Yes, papyrus-xtuml has lots of generated code.  Please advise on exactly
what header information is required.  We would prefer to use the most minimal
block possible.

  * No answer received on the minimal block.  No further action has been taken,
see 6.1.2.

5.7  There are many many hits to GNU.  Are you using a GNU compiler???  Is this
because you have provided source that only resides on the build server only and
will not be checked into the Eclipse repo or included in downloads or ?

* We use gcc to compile one of our tools produced from the open-source code.
It's possible we have submitted some things that should not have been submitted.
Can you please provide a list of offending files?

  * A list was not provided.  Using grep the following observations were made:  
    * Most hits found to GNU were "Gnu Make Builder" in `.cproject` files.
    * Two files are output from GNU Bison and the header notes a special
exception for output files.  
`mc/model/escher/gen/y.tab.h`  
`mc/model/escher/gen/template_engine.tab.c`  
  * No further action taken.

5.8  Oss.htm seems to include the project’s third party dependencies.  This file
needs to be replaced by the necessary standard Eclipse Third Party About files.
In addition, there are quite a few packages referenced that cannot be
distributed from Eclipse for eg., MingGW which is GPL, Oracle Binary Licensed
code, etc.  I'm assuming these fall into a Workswith dependency.  I will
provide you with the Guide to the Eclipse Third Party Dependency Policy 
[[2.4]](#2.4) here as well.

* OK.  We will appreciate your guidance in this area. I will familiarize myself
with the Guide as a start.

  * See section 6.2

5.9  Trademark references currently refer to Onefact and will need to be
amended.

* More housekeeping.  I'll perform a search and remove on these trademarks.

  * Items removed.

5.10  LicenseAgreement file is not a standard file for Eclipse.  You'll need to
familiarize yourself with the necessary legal bits as outlined in the Eclipse
Guide to Legal Docs [[2.3]](#2.3).

* OK, will do.

  * Items removed.

5.11  There are quite a few files included that contain copyright to UK Crown
Copyright.  Can you help us understand this inclusion?  If its third party
content, it will need to be removed and a separate third party CQ will be
required instead.

* Papyrus-xtuml supports two different action languages (basically, programming
languages that drive the behavior of the application model).  One of these
action languages is called MASL and was developed by a UK government
organization.  The reference manual and associated documentation for MASL
created by this organization is allowed to be distributed openly but is
required by UK law to carry the UK Crown copyright.  
What does the third party CQ entail?  Are we not allowed to include this
documentation at all?

  * No answer was received.  No action taken.  See 6.3.

5.12  Translate file references a required license agreement for BridgePoint
MC-3020??

* More heritage notices that shall be removed.

  * Items removed.

5.13  The following two files indicate content copied and adapted from the
OOAofOOA:

/masl/models/masl/masl/activity/activity.xtuml  
maslin/models/maslin/masl2xtuml/ooapopulation/ooapopulation.xtuml  

Can you provide the origin of this content?

* Yes.  The OOAofOOA is the model of Paprus-xtuml (BrigePoint) that we generate
the tool itself from.  It is also used to create the model compiler tools.
The OOAofOOA model was submitted in the data drop we provided.  It exists in
the bridgepoint source project org.xtuml.bp.core as well the model
compiler (mc) project mcooa.  The other model compiler projects like masl,
maslin, etc, leverage the ooaofooa model.

  * Answer above supplied.

5.14  The masl_schema/schema/colors/sys_functions.arc indicates Enhancements
provided by TOYO Corporation.  Can you help us understand this statement?

* This is a very old note of collaboration on the closed-source code.  The
notice is no longer relevant and shall be removed.

  * Removed.

6. Implementation Comments
--------------------------
This section contains items from section 5 that were not resolved and need
further attention.

6.1  Header Files - The following suggest massive editing of files.  No action
has been taken yet.  
6.1.1  Copyright and License Headers need to be amended.  (from 5.1)  
6.1.2  Generally there appears to be many files that do not contain any
copyright and license headers.  (from 5.6)

6.2  Third Party Dependencies  (from 5.8)  

Note:  After review it was decided that the dependencies listed in the rest of
this section do not apply to the Papyrus-xtUML submission.  The Third Party
About file mentioned in 6.2.1 was removed.  The discussion in 6.2.2 no longer
applies.

6.2.1  "Oss.htm seems to include the project’s third party dependencies.  This
file needs to be replaced by the necessary standard Eclipse Third Party About
files."
* File format resolved.  `bridgepoint/src/org.xtuml.bp.doc/license/oss.htm` has
been replaced with a markdown version of the provided standard Eclipse Third
Party About file, `bp.doc/license/third_party_about.md`.

6.2.2  "In addition, there are quite a few packages referenced that cannot be
distributed from Eclipse for eg., MingGW which is GPL, Oracle Binary Licensed
code, etc.  I'm assuming these fall into a Workswith dependency.  I will
provide you with the Guide to the Eclipse Third Party Dependency Policy
[[2.4]](#2.4) here as well."

According to the guidelines, dependencies fall into two types:  "works-with"
and "prerequisite".  The packages in the lists below came from the `oss.htm`
file mentioned in 6.2.1.
* works-with - "as determined by the Project Management Committee (PMC) are
approved for use by the projects without further Eclipse Management
Organization (EMO) review."
  * MinGW/MSYS version 3.1.8
  * MinGW gcc version 5.1.3
  * DocBook version 4.5
  * DocBook XSL Stylesheets version 1.75.1
  * libxslt version 1.1.24
  * libxml2 version 2.7.3
  * libiconv version 1.9.2
  * zlib version 1.2.3

* prerequisite - "must be declared to and approved by the EMO."  
  * Java JRE (Linux) version 1.6.0_35  
  * Java JRE (Windows) version 1.6.0_35  
Note: JRE is used as an example of "exempt prerequisite" in section 4a of
the guideline [[2.4]](#2.4).

* undecided
  * ANTLR

6.3  UK Crown Copyright files  (from 5.11)  
Our response/questions were not answered.  No action taken.

7. Unit Test
------------
None.

8. User Documentation
---------------------
None.

9. Code Changes
---------------
Fork/Repository:  jason-rhodes/bridgepoint  
Branch:  8636_ip_review_cleanup 

<pre>
~/git/bridgepoint$ git diff --name-status master..8636_ip_review_cleanup
A       doc-bridgepoint/notes/8636_papyrus-xtuml_initial_contribution_int.md
A       doc-bridgepoint/review-minutes/8636_initial_contribution.int.md
M       src/org.xtuml.bp.als.oal/about.html
M       src/org.xtuml.bp.bld.pkg/about.html
M       src/org.xtuml.bp.cdt/about.html
M       src/org.xtuml.bp.cli/about.html
M       src/org.xtuml.bp.compare/about.html
M       src/org.xtuml.bp.core/about.html
M       src/org.xtuml.bp.debug.ui/about.html
M       src/org.xtuml.bp.doc/GettingStarted/HTML/GettingStarted.htm
M       src/org.xtuml.bp.doc/GettingStarted/HTML/Samples.htm
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate.htm
A       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate.html
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image001.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image001.png
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image002.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image003.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image004.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image005.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image005.png
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image006.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image007.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image007.png
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image008.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image009.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image010.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image011.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image023.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image025.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image026.jpg
D       src/org.xtuml.bp.doc/GettingStarted/HTML/Translate_files/image031.jpg
M       src/org.xtuml.bp.doc/about.html
D       src/org.xtuml.bp.doc/legal/apache_1.1.pdf
D       src/org.xtuml.bp.doc/legal/apache_2.0.pdf
D       src/org.xtuml.bp.doc/legal/boost_1.0.pdf
D       src/org.xtuml.bp.doc/legal/cddl_1.0.pdf
D       src/org.xtuml.bp.doc/legal/cpl_1.0.pdf
D       src/org.xtuml.bp.doc/legal/cygwin_api.pdf
D       src/org.xtuml.bp.doc/legal/eclipse_1.0.pdf
D       src/org.xtuml.bp.doc/legal/gnu_free_doc_1.2.pdf
D       src/org.xtuml.bp.doc/legal/gnu_gpl_2.0.pdf
D       src/org.xtuml.bp.doc/legal/gnu_gpl_3.0.pdf
D       src/org.xtuml.bp.doc/legal/gnu_lgpl_2.1.pdf
D       src/org.xtuml.bp.doc/legal/gnu_lgpl_3.0.pdf
D       src/org.xtuml.bp.doc/legal/gnu_library_gpl_2.0.pdf
D       src/org.xtuml.bp.doc/legal/oracle_binary_jre_javafx_6.pdf
D       src/org.xtuml.bp.doc/legal/perl_artistic_2.0.pdf
D       src/org.xtuml.bp.doc/legal/w3c_2002.pdf
D       src/org.xtuml.bp.doc/license/Trademarks.htm
D       src/org.xtuml.bp.doc/license/copyright.htm
A       src/org.xtuml.bp.doc/license/copyright.html
A       src/org.xtuml.bp.doc/license/copyright.md
D       src/org.xtuml.bp.doc/license/licenseagreement.htm
D       src/org.xtuml.bp.doc/license/oss.htm
M       src/org.xtuml.bp.doc/toc.xml
M       src/org.xtuml.bp.doc/topics_GettingStarted.xml
M       src/org.xtuml.bp.doc/topics_license.xml
M       src/org.xtuml.bp.docgen/about.html
M       src/org.xtuml.bp.io.core/about.html
M       src/org.xtuml.bp.io.image/about.html
M       src/org.xtuml.bp.io.mdl/about.html
M       src/org.xtuml.bp.mc.c.source/about.html
M       src/org.xtuml.bp.mc.cpp.source/about.html
M       src/org.xtuml.bp.mc.java.source/about.html
M       src/org.xtuml.bp.mc.none/about.html
M       src/org.xtuml.bp.mc.systemc.source/about.html
M       src/org.xtuml.bp.mc.template/about.html
M       src/org.xtuml.bp.mc/about.html
M       src/org.xtuml.bp.model.compare/about.html
M       src/org.xtuml.bp.pkg/about.html
M       src/org.xtuml.bp.search/about.html
M       src/org.xtuml.bp.ui.canvas/about.html
M       src/org.xtuml.bp.ui.explorer/about.html
M       src/org.xtuml.bp.ui.graphics/about.html
M       src/org.xtuml.bp.ui.properties/about.html
M       src/org.xtuml.bp.ui.search/about.html
M       src/org.xtuml.bp.ui.sem/about.html
M       src/org.xtuml.bp.ui.session/about.html
M       src/org.xtuml.bp.ui.text/about.html
M       src/org.xtuml.bp.utilities/about.html
M       src/org.xtuml.bp.verifier.pkg/about.html
M       src/org.xtuml.bp.welcome/about.html
M       src/org.xtuml.bp.welcome/introContent.xml
M       src/org.xtuml.bp.x2m/about.html
M       src/org.xtuml.help.bp.mc/about.html
D       utilities/update-copyright/test_input.txt
D       utilities/update-copyright/update-copyright.pl
</pre>

Fork/Repository:  jason-rhodes/mc  
Branch:  8636_ip_review_cleanup 
<pre>
~/git/mc$ git diff --name-status master..8636_ip_review_cleanup 
M       doc/ug/xml/a6.xml
M       doc/ug/xml/persist.xml
M       doc/ug/xml/ug.xml
M       model/escher/about.html
M       model/mcooa/models/mcooa/extensions/Translation Extensions/copyright/copyright.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Activity/Activity.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Association/Association.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Body/Body.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Communication/Communication.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Component/Component.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Constants/Constants.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Datatypes/Datatypes.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Domain/Domain.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Element Packaging/Element Packaging.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Event/Event.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Globals/Globals.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Instance Access/Instance Access.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Instance/Instance.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Interaction/Interaction.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Invocation/Invocation.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Message/Message.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Packageable Element/Packageable Element.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Persistence Associations/Persistence Associations.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Relate And Unrelate/Relate And Unrelate.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Selection/Selection.xtuml
M       model/mcooa/models/mcooa/ooaofooa/State Machine/State Machine.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Subsystem/Subsystem.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Use Case/Use Case.xtuml
M       model/mcooa/models/mcooa/ooaofooa/Value/Value.xtuml
M       model/mcooa/models/mcooa/ooaofooa/ooaofooa.xtuml
M       schema/colors/sys_functions.arc
</pre>

End
---

