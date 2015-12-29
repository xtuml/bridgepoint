---

This work is licensed under the Creative Commons CC0 License

---

# Ready GPS Watch for Quick Start
### xtUML Project Design Note


### 1. Abstract
-----------
The GPS Watch model as managed in the models repository is to be launched from the Quick Start section of the BridgePoint Welcome screen.

### 2. Document References
----------------------
[1] [BridgePoint DEI #7986](https://support.onefact.net/redmine/issues/7986)  
[2] [BridgePoint DEI #7927](https://support.onefact.net/redmine/issues/7927)  
[3] [Analysis Note](https://github.com/xtuml/bridgepoint/doc-bridgepoint/notes/7986_GPS_Watch_Quick_Start/7986_GPS_Watch_Quick_Start_ant.md)  

### 3. Background
-------------
[3] outlines the requirements analysis; this document outlines the decisions made toward launching the GPS Watch model from the BridgePoint Welcome Quick Start.

Currently three GPS Watch models exist in the xtUML repositories.

The current design for the Quick Start project import is to have each of the project directories under the org.xtuml.bp.welcome/models directory. The design calls the eclipse class, ExternalProjectImportWizard, to handle the importation and automates the filling of the fields in the Wizard display screen. The design also checks to see if project exists on the path.

The file, src/org.xtuml.bp.welcome/introContent.xml, is used to show which projects are available and if the project import is a single model file or a project via the field, SingleFileModel.

### 4. Requirements
---------------
See [3].

### 5. Analysis
-----------
See [3].

### 6. Design
---------
1. Only the GPS Watch model in the models/application/gps repository will be retained. The other two models will be removed from the repositories.
2. The GPS Watch model, from the xtuml/models repository, will be placed as a zip file in the bridgepoint/src/org.xtuml.bp.welcome/models for distribution with BridgePoint.
3. The file, src/org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/ui/ProjectUtilities.java, in the xtuml/bridgepoint repository will be updated to allow for zip file selection when modifying the ExternalProjectImportWizard display screen.
4. Unit test references will be updated to reference the one remaining model. See Section 8.


### 7. Design Comments
------------------

### 8. Unit Test
------------
No new unit tests are required. The following list of existing unit tests must be run and passed.
1. org.xtuml.bp.welcome.test
2. org.xtuml.bp.als.oal.test
3. org.xtuml.bp.model.compare.test

End
---

