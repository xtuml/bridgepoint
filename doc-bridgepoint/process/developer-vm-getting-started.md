# Developer Virtual Machine Getting Started Guide

This document provides the information required to create a fully functional
BridgePoint development environment starting with the developer VirtualBox image

### Preparation 

0) Read and understand the [Developer Getting Started
Guide](Developer%20Getting%20Started%20Guide.md).  
1) Download and install [VirtualBox](https://www.virtualbox.org/wiki/Downloads).  
2) Download the developer VirtualBox image
[here](https://s3.amazonaws.com/1f-outgoing/BridgePointDeveloper.ova). Import
the image into VirtualBox (instructions for importing an image
[here](https://www.virtualbox.org/manual/ch01.html#ovf)).  
  * Although it is not strictly required, you may find that virtual machine
    to be very slow unless you install the "Guest Additions".  Instructions for
    installation can be found
    [here](https://www.virtualbox.org/manual/ch04.html#idp47569018442784).  
3) Follow the instructions in the [Developer Getting Started
Guide](Developer%20Getting%20Started%20Guide.md) to create a Github account and
fork the xtUML repositories.  

### General Information

* The VM image is based off Ubuntu 16.04 Desktop (LTS).  
* The username is "developer"; the password is "password".  
* BridgePoint 6.11.0 is installed at `~/xtuml/l6110/`.  
* The xtuml repositories `bptest`, `bridgepoint`, `mc`, `models`, `packaging` and `pt_antlr` are
  cloned at `~/git/`.  
  * Each repository has one remote repository called "upstream" that references
    the xtuml repositories on GitHub.  
* A development workspace is preconfigured at `~/workspace`.  

### Setup Instructions

* Pull the `master` branch from the _upstream_ remote of each repository (it is
  not necessary to do this for `packaging` and `pt_antlr`). This
  assures that your development environment is up to date with the latest
  development.  
  Repeat for each repo:
```
cd ~/git/bridgepoint
git pull upstream master
```
* Navigate to `~/git/bridgepoint/utilities/build/`. Run
  `./build_and_test_bp.sh`. This will build BridgePoint. It should
  take a little more than an hour (depending on your host machine).  
* Launch BridgePoint and select `/home/developer/workspace`. Select Project > Build
  Automatically. Wait for build to complete. This should be very quick.  

### Git Setup

Although you can run and test BridgePoint, extra setup is required to make
commits to the repositories.  
* Set up your global git identity. See the tutorial
  [here](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup).  
* Add remote repositories to the local git repositories  
  * Add _**your fork**_ as a remote on each repository (name the remote
    _origin_).  
  * Consult [this tutorial](https://help.github.com/articles/adding-a-remote/)
    for help with adding remotes  

### Congratulations! Your environment is now built and ready for BridgePoint development.

  * If you have had any problems building, or have questions, check the
    [FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#bpdevelopers)
    or post to the [xtUML.org Forums]
    (https://xtuml.org/community/forum/xtuml-forum/) for help.  
  * If you want to run BridgePoint unit tests, instructions are available in
    [HOWTO Run the BridgePoint Unit
    Tests](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/HOWTO-run-bridgepoint-unit-tests.md).  

### Additional Notes

  * Information about running the BridgePoint build via the command line may be
    found [in the
    FAQ](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md#clibuild).  

