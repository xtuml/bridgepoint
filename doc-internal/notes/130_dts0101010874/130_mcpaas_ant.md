---

Copyright 2014 Mentor Graphics Corp.  All Rights Reserved.

---

# Model Compiler Platform as a Service (MC-PaaS)
### xtUML Project Analysis Note



1. Abstract
-----------
The BridgePoint product line needs to provide access to a model compiler 
for customers who need usage-based access.  This note describes the requirements
for providing the model compiler as a network-based "platform as a service".  

2. Document References
----------------------
[1] Issues 130, https://github.com/xtuml/internal/issues/130  
[2] CQ DEI dts0101010874 - this issue  
[3] Issues 117, https://github.com/xtuml/internal/issues/117  

3. Background
-------------
Model compilers have always been bundled with BridgePoint as a high-cost 
licensed product.  Some customers want to use BridgePoint modeling and 
execution but do not want to pay for a model compiler.  Other customers want
to use the free xtUML Editor and pay for limited access to model compilation.
Still other customers want to model and execute in BridgePoint but move 
to usage-based payment and licensing for translation.  

By offering the model compilation (translation) to our customers as a 
network-based service platform, Mentor Graphics stands to satisfy customer
requirements and capture additional revenue from small-scale users and 
shops that are modeling with xtUML Editor.  

A proof-of-concept was created in [3] and promoted to xtuml/internal master.  It
did not affect any parts of the BridgePoint product.  An enhancement to the 
proof of concept was created in xtuml/internal branch 130_mcpaas_phase2.  This 
branch has not been promoted to master (as of 3/1/2014) since some of the changes
in the branch would affect the BridgePoint product plug-ins.  

4. Requirements
---------------
__4.1  User interface__  
- 4.1.1  A user shall be able to navigate to a MC-PaaS webpage, submit a project zipfile, run MC-PaaS, and receive a zipfile containing the translated model source code in return.
- 4.1.2  Integrated build
  - 4.1.2.1  A user shall be able to configure a project to use the MC-PaaS model compiler.
  - 4.1.2.2  User shall then be able to click "Build Project" inside BridgePoint or xtUML Editor.  The tool shall transfer the project data to an MC-PaaS server, run the translation, and populate the project's local ```src/``` folder with the source code resulting from the translation.  
- 4.1.3  The MC-PaaS server shall handle multiple concurrent build requests  
 
__4.2  User management__  
- 4.2.1  A web interface shall be provided for individuals to register to use MC-PaaS
- 4.2.2  A web interface shall be provided for an organization representative to register their organization to use MC-PaaS
  - 4.2.2.1  A web interface shall allow the organization to select registered users and add them to the organization  

__4.3  Security__  
- 4.3.1  All MC-PaaS communication across a network shall be under a secure connection
- 4.3.2  All project data sent across a network shall be symmetically encrypted/decrypted at each end  
- 4.3.3  MC-PaaS shall not store passwords
- 4.3.4  BridgePoint/xE shall not store passwords
- _require reuse xtuml.org logins?_
- _data storage? access to old completed builds?_
 
__4.4  Accounting__  
- 4.4.1  Each build request shall decrement the number of builds available to the user or organization by one
- _on demand, web-based payment acceptance?  Where does the money go?_
  - _instantaneous access to newly purchased builds_

__4.5  Deployment__  
- 4.5.1  MC-PaaS shall be available via any computer with internet access
- 4.5.2  MC-PaaS shall be installable and accessible on a server inside an organization's intranet
- _Amazon? xtuml.org?_
- _licensing of tools on server?_

5. Work Required
----------------
    In this section, break out the consequential work (as a numbered list) needed
    to meet the requirements specified in the Requirements section.

6. Acceptance Test
------------------
    In this section, list the tests that need to be performed in order to
    verify that all the requirements are satisfied.

End
---

