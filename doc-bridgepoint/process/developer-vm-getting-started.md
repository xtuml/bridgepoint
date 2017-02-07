# Developer Virtual Machine Getting Started Guide
This document provides the information required to create a fully functional BridgePoint development environment starting with
the developer VirtualBox image

Preparation 
-----------
0) Read and understand the [Developer Getting Started Guide](Developer%20Getting%20Started%20Guide.md).

1) Download and install [VirtualBox](https://www.virtualbox.org/wiki/Downloads).

2) Download the developer VirtualBox image [here](https://s3.amazonaws.com/1f-outgoing/BridgePointDeveloper.ova). Import the
image into VirtualBox (instructions for importing an image [here](https://www.virtualbox.org/manual/ch01.html#ovf)).

3) Follow the instructions in the [Developer Getting Started Guide](Developer%20Getting%20Started%20Guide.md) to create a Github
account and fork the xtUML repositories

General Information
-------------------
* The VM image is based off Ubuntu 16.04 Desktop (LTS).
* The username is "developer"; there is no password.  
_Note: Due to a Unity bug, if the machine is "locked", the operating system will still require the password (which does not exist) to unlock. To Unlock the machine, click the gear in the upper right hand corner and select "Switch Account...". This will allow you to log in to the developer account without a password by clicking "Log In"._
* BridgePoint 6.0.0 is installed at `~/xtuml/l586/`.
* The xtuml repositories bptest, bridgepoint, mc, models, and pt_antlr are cloned at `~/git/`.
  * Each repository has one remote repository called "upstream" that references the xtuml repositories on GitHub.
* A development workspace is preconfigured at `~/workspace`. BridgePoint is pre-built in this workspace at revision `8f884be`

Setup Instructions
------------------
* Pull the `master` branch from the _upstream_ remote of each repository. This assures that your development environment
is up to date with the latest development
```
git pull upstream master
```
* If master in the bridgepoint repository is already up to date, BridgePoint is ready to run and you are done. If master was not up to date, you will need to re-build BridgePoint.
* Launch BridgePoint and select `~/workspace`. Select Project > Build Automatically.

Git Setup
---------
Although you can run and test BridgePoint, extra setup is required to make commits to the repositories.
* Set up your global git identity. See the tutorial [here](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup).  
* Add remote repositories to the local git repositories  
  * Add _**your fork**_ as a remote on each repository (referred to as _origin_)  
  * Consult [this tutorial](https://help.github.com/articles/adding-a-remote/) for help with adding remotes  

### Congratulations!  Your environment is now built and ready for BridgePoint development.

  - If you have had any problems building, or have questions, check the [FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#bpdevelopers) or post to the [xtUML.org Forums] (https://xtuml.org/community/forum/xtuml-forum/) for help.
  - If you want to run BridgePoint unit tests, instructions are available in [HOWTO Run the BridgePoint Unit Tests](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md)
   

### Additional Notes
  - Information about running the BridgePoint build via the command line may be found [in the FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#clibuild).
