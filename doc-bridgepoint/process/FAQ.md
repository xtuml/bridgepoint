# BridgePoint FAQ

## Table of Contents
  * [xtUML Profile](#xtuml_profile)
    * [What is xtUML?](#whatisxtuml)
    * [What is the history of xtUML?](#xtumlhistory)
    * [How is xtUML different from UML or SysML?](#differences)
    * [What is the xtUML Process?](#xtumlprocess)
    * [What is Object Action Language?](#whatisoal)
    * [What is a Model Compiler?](#whatismc)
    * [Where can I get model execution and translation support?](#xt_support)
    * [Where can I find out more about xtUML methodology and tools?](#morextumlinfo)
    * [What happened to the xtUML Editor?](#xtumleditor)
  * [BridgePoint Installation](#installation)
    * [Errors During Unzip](#unziperrors)
  * [BridgePoint Developer Issues](#bpdevelopers)
    * [ANTLR Build Error](#antlrbuilderror)
    * [Linux Distribution-Specific Instructions](#linux)
    * [Windows Unit Test Configuration](#windowstesting)
  * [Verifier](#verifier)
    * [What does "Nothing to verify." mean?](#nothingtoverify) 

xtUML Profile <a id="xtuml_profile"></a>
------------
  * **What is xtUML?**  <a id="whatisxtuml"></a>  
  xtUML is a UML profile that defines the constraints and extensions needed to make UML executable and translatable. xtUML transforms the UML language from a static picture drawing facility into a full-fledged solution for model-driven development. With xtUML, users don’t draw model pictures of their application at a moment of time. Instead, they create models that are the application.
  
  * **What is the history of xtUML?**   <a id="xtumlhistory"></a>  
  The introduction of the Unified Modeling Language (UML) by the Object Management Group (OMG) in 1997 marked a turning point in the development of a mature process for systems and software engineers. The UML provides a standard notation for describing complex systems and offers a more effective communication and documentation mechanism. However, UML fails to prescribe a methodology that ensures high quality systems will emerge from these diagrams. This lack of a methodology inadvertently constrained the impact of UML diagrams on the systems design community.To correct this problem, the developers of xtUML combined the methodology of Model Driven Architecture (MDA) with the notation of UML. This combination provides a rigorous framework for raising the abstraction of systems design and automating the transformation from platform-independent models to platform-specific implementations. From this starting point, a user community and set of tools emerged, and with the formation of xtuML.org, the ability to edit, execute, and translate xtUML models into target-domain solutions has continued to grow.
  
  * **How is xtUML different from UML or SysML?**   <a id="differences"></a>  
  xtUML and SysML are both profiles of the UML. The SysML profile customizes UML for the broad, interdisciplinary, field of systems engineering. The xtUML profile customizes UML for software and hardware application development and system design.  There is roughly 70% overlap.
  
  * **What is the xtUML Process?**   <a id="xtumlprocess"></a>  
  The xtUML process defines a recommended methodology and style guide for doing model-driven development with a focus on early execution followed by translation of application models into design-level code.
  
  * **What is Object Action Language?**   <a id="whatisoal"></a>  
    * The Object Action Language (OAL) is used to define the semantics for the processing that occurs in an action. An action can be associated with states, functions, and interface messages. OAL is used for:
      * data access
      * event generation
      * test
      * transformation
      * bridge and function
      * inter-component messaging  
    * It supports these through:  
      * control logic
      * access to the data described by the class diagram
      * access to the data supplied by events initiating actions
      * the ability to generate events
      * access to timers and to the current time and date  
  
  Using OAL allows the application developer to have the flexibility of a platform-independent model that is not tied to a specific target language or premature partitioning of the hardware and software components. This frees the developers from worrying about language-specific constructs and allows the model compiler to create efficient, retargetable, standards-compliant implementation code.
  
  * **What is a Model Compiler?**   <a id="whatismc"></a>  
  A model compiler is a software tool that translates the data (components and classes), control (state machines and messages), and processing (OAL) defined in the application model into an implementation-specific runnable suitable for execution on the target. Model compilers perform rules-based code generation. Commercial model compilers are available that support C, C++, and SystemC.
  
  * **Where can I get model execution and translation support?**   <a id="xt_support"></a>  
  The Verifier and model compilers are Open Source Software along with the rest of BridgePoint.

  * **Where can I find out more about xtUML methodology and tools?**   <a id="morextumlinfo"></a>  
[xtUML Learn](https://xtuml.org/learn/) provides links to courses, books, articles, presentations, and more.  The [xtUML Index](https://www.xtuml.org/index/) defines a list of terms with links to associated videos.
  
  * **What happened to the xtUML Editor?**  <a id="xtumleditor"></a>  
In September 2012 the front-end UML editor of the commercial BridgePoint xtUML environment was moved into the open source domain providing the industry with a free entry point into full model-driven development flows that can be augmented with user-developed model compilers. In November 2014 the remaining elements of BridgePoint were made open source rendering the Verifier (eXecution) and model compilers (Translation) fully accessible at the source level.  

BridgePoint Installation <a id="installation"></a>
------------
* **Errors During Unzip**  <a id="unziperrors"></a>  
  When unzipping the BridgePoint distribution if you see a message that indicates a duplicate file is 
  being installed or there missing files in the distribution, the problem is likely with the 
  unzip utility you are using.  We suggest you use [7-Zip](http://www.7-zip.org/download.html).
  
BridgePoint Developer Issues <a id="bpdevelopers"></a>
----------------------------

* **ANTLR Build Error** <a id="antlrbuilderror"></a>  
  After following the [Developer's Getting Started Guide](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md) 
  if you see errors in plugins caused by missing dependant files, and those files refer to ANTLR, follow these 
  instructions to resolve the issue:
 
  - Open a terminal (or cygwin shell if windows)
  - Open the Navigator view in eclipse
  - Delete org.xtuml.bp.als/sql/oal_grammar.sql
  - "touch" \<installation folder\>/BridgePoint/eclipse/plugins/org.xtuml.bp.als.oal/bnf/oal.bnf
    - (just change a comment)
  - Right click on org.xtuml.bp.als/generate.xml and choose ```Run As... > Ant Build...```
  - Uncheck everything
  - Check grammar
  - Click Run  
  
  This problem is that ANTLR is not running when it should in some cases. This is a sporatic dependancy 
  issue that has not yet been completly resolved.  It is raised in the issue tracking system as 
  issue [7631](https://support.onefact.net/redmine/issues/7631).
  
* **Linux Distribution-Specific Instructions** <a id="linux"></a>
  * Package Requirements for Various Linux Distributions  
    The BridgePoint [Developer's Getting Started Instructions](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md) 
    has instructions specifically for Linux Ubuntu. Here are package requirements for some other Linux distributions.  
      * Fedora 19:  
        ```$ sudo yum install wine gcc-c++  dos2unix compat-libstdc++-33 gtk2.i686 ant git```
      
      * Debian Wheezy:  
        ```$ sudo apt-get install ia32-libs ia32-libs-gtk libgtk2.0-0 lib32ncurses5 ant git```
  
* **Windows Unit Test Configuration**  <a id="windowstesting"></a>  
  This is used when runnning unit tests under Windows.  These instructions are used to prepare the Windows environment to run graphical compare tests.  If you do not want or need to run graphical compare tests, you do not have to perform these steps.  However, some BridgePoint unit tests will fail in Windows if you do not perform these steps.

  - Configure Windows Vista or 7 for unit test running.   Skip this step for other OSes.
    - Exit BridgePoint
    - Bring up the Windows Color and Appearance settings
      - Right click on the desktop background and select Personalize
      - In Vista select the Windows Color and Appearance hyperlink
      - In Windows 7 select the Windows Color hyperlink
      - In the window that appears, change the following attributes:
        - Active Title Bar    Size: 25 Font: Trebuchet 10
        - Border Padding      Size: 0
        - Caption Buttons     Size: 25
        - Icon                Size: 32 Font: Tahoma 8
        - Inactive Title Bar  Size: 25 Font: Trebuchet 10
        - Menu                Size: 19 Font: Tahoma 8
        - Message Box                  Font: Tahoma 8
        - Palette Title       Size: 17 Font: Tahoma 8
        - Selected Items      Size: 19 Font: Tahoma 8
        - Tooltip                      Font: Tahoma 8
    - If you have a smaller screen or resolution potential you may need to configure the start menu to not always be on top, or set it to auto-hide.
    - Note: Do NOT use the Windows setting that scales text to make it easier to see. This setting, in Windows 7, is found here: ```Personalize > Display > "Make it easier to read what is on your machine"```.  This setting must be set to ```"smaller" 100%```.  If it is adjusted your graphical compare results will not match.
  
Verifer <a id="verifier"></a>
------------

* **What does "Nothing to verify." mean?**  <a id="nothingtoverify"></a>  
  It means that the execution engine did not find parsed instances to execute. Here are things to check:
  * Make sure the selected element is being parsed successfully. If there is an OAL error there will be nothing to launch.  Check the Problems view for errors.
  * Make sure there is some OAL in the model elements that are under the selected launch configuration.  Inspect your model to verify there are elements under the project/component you are launching that contain action language. 
  * Are the packages containing the classes inside a component? They need to be.
  * Some other error in the selected launch configuration is likely present.  Check the Problems view and Error Log view for indications of issues in the model you are launching.  
  
  If you are still having trouble, check out [this thread on the xtuml.org forums](https://xtuml.org/community/topic/what-does-nothing-to-verify-mean/) and ask for help there.

