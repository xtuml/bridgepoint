Building BridgePoint From The Command Line
------------------------------------------


Abstract
------------
This document will guide developers who wish to build a BridgePoint version 
using the command line.

References
------------
[1]  https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md

Notes
-----
The current build scripts are only tested and used in linux.  While they may 
work in a cygwin environment it is suggested that a linux environment be used.   

The steps in this note perform most of the procedures outlined in reference [1]. 
It is a good idea to familiarize yourself with that document before proceeding.        

Build Environment Setup
-----------------------

### Create a build folder to work in.  The naming is not important.  Example: /home/$USER/build
```bash
username@hostname:~$ mkdir build
```
### Create a folder to contain the git repositories for the BridgePoint source.  Name it git/xtuml.
```bash
username@hostname:~$ cd build
username@hostname:~$ mkdir -p git/xtuml
```
### Change to the git/xtuml directory and clone the following projects
- bridgepoint   
- mc   
- packaging   
```bash
username@hostname:~$ cd git/xtuml
username@hostname:~$ git clone https://github.com/xtuml/bridgepoint.git
username@hostname:~$ git clone https://github.com/xtuml/mc.git
username@hostname:~$ git clone https://github.com/xtuml/packaging.git
username@hostname:~$ git clone https://github.com/xtuml/pt_antlr.git
```
### Change back to the build directory
```bash
username@hostname:~$ cd ../..
```
### Create a work directory and change to that new directory
```bash
username@hostname:~$ mkdir work
username@hostname:~$ cd work
```
### Copy the following two files into the work directory
- build/git/xtuml/bridgepoint/utilities/build/init_git_repositories.sh
- build/git/xtuml/bridgepoint/utilities/build/run_build.sh
```bash
username@hostname:~$ tr -d '\r' < ../git/xtuml/bridgepoint/utilities/build/init_git_repositories.sh > init_git_repositories.sh
username@hostname:~$ tr -d '\r' < ../git/xtuml/bridgepoint/utilities/build/run_build.sh > run_build.sh
```
### Configure your git account information.  This includes GITUSER and GITPASS, where GITPASS can be a real password or, preferably, a github API token for the specified user.
```bash
username@hostname:~$ export GITUSER="username@somedomain.com"
username@hostname:~$ export GITPASS="RealPassword", or "TOKEN"
```
### Run the build script, there are two optional arguments here.  One is to specify a branch to build, by default it is master.  The other is to specify a username for a target location for the release to be posted (some folder on a webserver)
```bash
username@hostname:~$ ./run_build.sh <BridgePoint_Installation> <Build_Directory_Parent> <optional:<git_branch_name>> <optional:<release_target_user_name>>
```
### Once the build is complete you will see plugins located under the build/releases/branch-timestamp folder.