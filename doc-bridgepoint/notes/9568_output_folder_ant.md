---

This work is licensed under the Creative Commons CC0 License

---

# Create output folders if they don't exist  
### xtUML Project Analysis Note


### 1. Abstract

This note describes changes to the build chain to make sure output folders
exist when the project build runs.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9568](https://support.onefact.net/issues/9568) Headline issue.    

### 3. Background

Users have experienced situations where the pre-builder runs but does not save
its output because the target folder of the output does not exist.  Additionally,
the build process does not populate the project's ```src/``` folder if it does 
not already exist.   

### 4. Requirements

4.1 The pre-builder shall create ```<project>/gen/``` when it runs  
4.2 The pre-builder shall create ```<project>/gen/code_generation/``` when it runs  
4.3 Code generation tooling shall create ```<project>/src/``` when it runs
4.4 Requirements 4.1 thru 4.3 shall be satisfied regardless of invocation 
  via the BridgePoint CLI or GUI build. 

### 5. Analysis

5.1 The pre-builder is implemented in the ```org.xtuml.bp.mc.AbstractExportBuilder```.  
5.2 ```xtumlmc_build``` handles copying files from the ```<project>/gen/_ch/``` to 
  the output folder (typically ```<project>/src/```).  Currently it simply skips
  copying if the target folder does not exist.  See line 1742.

### 6. Work Required

6.1 Modify ```AbstractExportBuilder::exportSystem()``` to create the folder specified
  by the ```destDir``` parameter if it does not exist.  
6.2 Modify ```xtumlmc_build``` near line 1742 to create the output folder if it
  does not already exist.   

### 7. Acceptance Test

7.1 Code generation folder test
* Create the MicrowaveOven example project
* Remove its ```gen/``` folder
* Build the project from the UI
* __R__ The project's ```gen/code_generation/``` folder is created and populated
* Remove the ```gen/``` folder
* Exit BridgePoint
* Run the CLI build on the project with -prebuildOnly
* __R__ The project's ```gen/code_generation/``` folder is created and populated
* Remove the ```gen/``` folder and the ```src/```
* Run the CLI build on the project with
* __R__ The project's ```gen/code_generation/``` and ```src/``` folders are created and populated

7.2 Output source folder test
* Create the MicrowaveOven example project  
* Remove its ```src/``` folder
* Build the project
* __R__ The project ```src/``` folder is created and populated

### End
