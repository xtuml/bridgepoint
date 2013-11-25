---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Model Compiler Platform as a Service
### xtUML Project Design Note


1. Abstract
-----------
The note describes the requirements and design for running xtUML Translation 
(i.e. Model Compilation) as a web-based service.  The requirements and design 
presented here are for an initial prototype to serve as a phase 1 proof of 
concept.  

2. Document References
----------------------
[1] Issues 117, https://github.com/xtuml/internal/issues/117  
[2] CQ DEI dts0101010874 - Clear Quest original tracking issue    
[3] Node.js. http://nodejs.org/  
[4] MC-PaaS Prototype Notes.txt. 

3. Background
-------------
Our group has been exploring the business opportunities that a web-based model
compiler could present.  To demonstrate the feasibility of such an offering, we
will create a proof-of-concept application.  This will allow us to explore the
technical issues, create a demo-able tool, and lay a foundation for future 
enhancements.  

4. Requirements
---------------
Consider the following Use Case:    
1) client edits stuff  
2) client puts edited stuff into c-out/ (multiple files, possibly hierarchy)  
3) web agent moves files from c-out/ to s-in/  
4) web agent triggers arbitrary command/script on server  
5) command/script on server processes files in s-in/ and outputs stuff to s-out/  
6) web agent moves files from s-out/ to c-in/  

An optional step 3.5 could be added to explicitly (from the client) trigger
step 4 with a UI button or some such.

Addition notes and requirements:  
  - An rsync approach is fine.  The client will be the master.  So, when files 
  are deleted from the client (c-in, c-out), the web agent can delete them
  from the corresponding locations on the server.
  - Server should be Ubuntu (or other Debian) -compatible (so I can use apt-get).
  - Server should be locatable to AWS or a Linux VM on a network of my choice
  - Client side should be compatible with Linux first and then Windows

5. Analysis
-----------
5.1  Node.js [3] is a JavaScript-based web application framework that makes it 
  very easy to write applications that provide web services.  
  
5.2  We can use the xtumlmc_build command line interface on the server to do 
  the actual model translation.  We simply need to get the pre-builder output
  < project >.sql file up to the server.  Then we can build it using xtumlmc_build.  
  
5.3  RSync is a tool that provides a relatively simple mechanism to transfer
  folders and files from a client to a server.  The drawback is that each folder
  pair (client / server) that are hooked together require an some configuration.
  This will work fine for the prototype but may not scale well to a large
  deployment.    
  
6. Design
---------
6.1  Server  
6.1.1  Modify the default Launcher.sh to create a new launcher (jsLauncher.sh) that runs 
  xtumlmc_build instead of starting eclipse.  xtumlmc_build will run and perform
  translation on the files in the server's ```gen/``` folder, populating the server's
  ```src/``` folder with the generated code.  
6.1.2  Use Node.js to create an http server running on port 8000.  When the http
  server services a request, it will run jsLauncher.sh  
6.1.3  The server machine must be configured as an rsync server, providing mappings
  for rsync clients to populate the local ```gen/``` and pull from the local ```src/```.  
6.1.4  The server must also be configured to run the node.js-based http server
  via an init script so it runs even when no user is logged in.  
  
6.2  Client  
6.2.1  The client requires the ```rsync``` and ```curl``` tools.  Rsync is used
  to transfer files up to the server and to fetch files down from the server.
  Curl is a simple command-line http client that shall be used to send a message
  to the server to indicate that the files are in place and the build on the server 
  may proceed.    
6.2.2  Create a new Model Compiler plug-in using the BridgePoint model compiler
  template.  
6.2.2.1  This new MC plug-in shall run pre-builder just like all the other
  model compiler plug-ins.  However, instead of running xtumlmc_build locally
  via the ```Model Compiler.launch``` file, it will instead launch a shell script
  that:  
  - Runs rsync to push the gen/ folder files up to the server
  - Uses curl to send the http-based message to the server to trigger the build
  - Runs rsync every 10 seconds (up to 6 times) to pull the generated source
  code from the server  
  
7. Design Comments
------------------
7.1  The code is in branches on a couple different github repositories:
  - xtuml/internal branch: 117_mc-paas_proto
  - xtuml/mc branch: 34_mcpaas_proto  

7.2  We set up a globally-accessible MC-PaaS server on an Amazon AWS-based virtual
  machine that Cort created.  The details of the system configuration required
  to turn this machine into an MC-PaaS server are captured in [4].  
  
8. Unit Test
------------
8.1 When Dean McArthur is able to successfully use the MC-PaaS server hosted 
  on Amazon AWS then phase 1 shall be considered complete.    
  
End
---

