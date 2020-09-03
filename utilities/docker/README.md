# Bridgepoint Docker Container

## Description
This is a simple docker setup to enable OneFacts Bridgepoint modeller tool to be ran within a Docker container.  

## Installation Requirements
The following are required to be installed to enable this container to be used.  

### MacOS X
* X Session Forwarding Support tool - XQuartz (this will require logg out and back in after set up)  
* Docker Software - Docker Desktop  

### Windows
* TODO  

### Linux
* TODO  

## Usage
### Generic Instructions
This docker setup contains two primary scripts mentioned below.  
* build-image.sh  
* run-bridgepoint-docker.sh  

You must have Docker running for this to work, as well as any OS specific applications mentioned above.  

### XQuartz Notes
When using this tool you must open 'Preferences' -> 'Security' and then tick the box 'Allow connections from network clients'.  
At this point you need to log out of your machine and back in to enable the settings to function properly.  

### build-image
Run this script to install the container.  

Help options can be found by running the script with help option.  
<pre><code>build-image.sh -h</code></pre>

An example command to run this script is below.  
<pre><code>build-image.sh -t bridgepoint:0.1 Centos7-dev7</code></pre>

### run-bridgepoint-docker  
It is recommended to run this script from within the directory where you wish to load projects from.  

Help for this script can be found by running the script with the help option.  
<pre><code>run-bridgepoint-docker.sh -h</code></pre>

An example command to run this script is below.  
<pre><code>run-bridgepoint-docker.sh -d /user/username/development -c bridgepoint</code></pre>
  
Running the script will automatically load bridgepoint upon completion and ask for a 'Workspace'.  
If you did not run this script from your proejct directory (bridgepoint projects)  
then you must have passed the option (-d) and the path to your project directory. This could be your user directory if you wish.  

Once you close Bridgepoint, this will also close the container. You can only have a single instance of bridgepoint running.  

### Optional Extras
As you need to run the run script from your project directory, it may be worth adding a alias to your environment file as below.  

#### Linux/Mac Os X
Option your bashrc file and add a line like the below to it.  
<pre><code>alias bridgepoint="/bin/bash /usr/username/dev-dir/bridgepoint-docker/run-bridgepoint-docker.sh</code></pre>  

#### Windows
TODO

# Project Status
* Testing  

# Future Development
1. Combine the installation of Bridgepoint into the docker container.  
2. Allow the version to be passed into the docker create to choose version of Bridgepoint to install.   
3. Drop into the development directory as default.  
4. Source the root users bashrc file to enable Bridgepoint to be manually loaded.  
5. Allow multiple instance of Bridgepoint to be run from a single docker container.  

# Copyright and License

UK Crown Copyright

[Apache](../../LICENSE)
