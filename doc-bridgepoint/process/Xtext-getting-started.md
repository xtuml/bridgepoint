Xtext editor development getting started
========================================

This document provides the information required to get started developing the
Xtext MASL editor

Preparation
-----------

1. Fork and clone the "xtuml/bridgepoint" repository  
2. Checkout the **8441_masl_editor_2** branch, or create a new branch and merge
this branch into your local branch  

Setup Instructions
------------------

1. Follow the developer getting started guide found [here](Developer Getting Started Guide.md)  
    _Note: do not import any of the Xtext projects into the workspace during
    this step_  
    _Note: be sure that you checkout the **8441_masl_editor_2** branch before building_
2. Disable "Build Automatically" in Eclipse  
3. Navigate to the parent Xtext project directory
(`src/org.xtuml.bp.xtext.masl.parent/`) and perform a clean build with `mvn
clean install`  
4. Import the Xtext projects into the Eclipse workspace. Be sure to check
"Search for nested projects" and import all child projects of the MASL editor
parent project  
5. Resolve any path errors in the Xtext projects concerning required directories
"xtend-gen" and "src"  

At this point, you should be able to launch BridgePoint and use the Xtext
editor. Below are instructions of how to launch a test instance of BridgePoint.

Launching Developer BridgePoint
-------------------------------

1. Select _Run > Run Configurations..._  
2. Expand _Eclipse Application_ and select "x BP Application - Mars"  
3. Click Run  
