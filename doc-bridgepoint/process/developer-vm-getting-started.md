# Developer Virtual Machine Getting Started Guid
This document provides the information required to create a fully functional BridgePoint development environment starting with
the developer VirtualBox image

Preparation 
-----------
0) Read and understand the [Developer Getting Started Guide](Developer%20Getting%20Started%20Guide.md).

1) Download and install [VirtualBox](https://www.virtualbox.org/wiki/Downloads).

2) Download the developer VirtualBox image [here](https://s3.amazonaws.com/1f-outgoing/BridgePointDeveloperStart.ova). Import the
image into VirtualBox (instructions for importing an image [here](https://www.virtualbox.org/manual/ch01.html#ovf)).

3) Follow the instructions in the [Developer Getting Started Guide](Developer%20Getting%20Started%20Guide.md) to create a Github
account and fork the xtUML repositories

Setup Instructions
------------------

* General information about the developer virtual machine:  
  * The username and password for the developer VM are: username, password.  
  * BridgePoint 5.5 is pre-installed at `~/xtuml/linux550/`.  
  * The bridgepoint, mc, and models repositories are located at `~/git/`.  
  * BridgePoint is pre-built in a workspace located at "~/workspace/".  
* Set up your global git identity. See the tutorial [here](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup).  
* Add remote repositories to the local git repositories  
  * Add **your fork** as a remote on each repository (referred to as _origin_)  
  * Add **the xtuml repository** as a remote on each repository (referred to as _upstream_)  
  * Consult [this tutorial](https://help.github.com/articles/adding-a-remote/) for help with adding remotes  
* Pull the `master` branch from the _upstream_ remotes of each repository. This assures that your development environment
is up to date with the latest development

### Congratulations!  Your environment is now built and ready for BridgePoint development.

  - If you have had any problems building, or have questions, check the [FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#bpdevelopers) or post to the [xtUML.org Forums] (https://xtuml.org/community/forum/xtuml-forum/) for help.
  - If you want to run BridgePoint unit tests, instructions are available in [HOWTO Run the BridgePoint Unit Tests](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md)
   

### Additional Notes
  - Information about running the BridgePoint build via the command line may be found [in the FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#clibuild).
