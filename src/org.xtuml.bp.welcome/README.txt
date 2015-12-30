HOWTO Update the Metamodel model
--------------------------------
- Update local xtuml/bridgepoint git repository with latest from master
- Start BridgePoint
- Import existing project org.xtuml.bp.core from the repository into the workspace
- Export org.xtuml.bp.core project to single file model xtUML_Metamodel.xtuml 
    with "Export OAL" preference turned off, and "Export Graphics" preference 
    turned on
- Copy xtUML_Metamodel.xtuml to org.xtuml.bp.welcome/models/xtUML_Metamodel.xtuml


HOWTO Add/Update the Welcome Projects
-------------------------------------
See here:  /notes/dts0100638163/dts0100638163.int in section 6.
and copied for convenience:

6.1 While updating sample projects and the unit tests that exercise these 
    sample projects the way samples are stored has been modified to make it
    easy to update existing sample projects and to add new ones.  We now store 
    complete projects under the bp.welcome/models folder.  
6.1.1 To edit one of these sample the developer shall:
    _- Open the CVS repository view
    _- Navigate to the bp.welcome/models/<folder of model to edit>
    _- Check out the folder
    _R Sample project is in the workspace under revision control
    _- Follow process to make modification and check them in
    
6.1.2 The following is the procedure for adding new sample projects:

	_- Create the new project
	_- If the sample is currently stored in revision control check it out to 
	   your workspace and disconnect it from source control.  If not in source 
	   control simply move to the next step.
	_- Open the Navigator view, select the project, Right Click (RC) > Move...
	_- Select the following as the destination:
	   <workspace>/org.xtuml.bp.welcome/models
	_- Select finish
	_- In the navigator view RC org.xtuml.bp.welcome and select refresh
	_R org.xtuml.bp.welcome is marked dirty and the new model is now in
	   <workspace>/org.xtuml.bp.welcome/models/<new model folder>
	_- Open org.xtuml.bp.welcome\introContent.xml
	_- Search for the section labeled:  <group id="page-links">
	_R For every current sample there will be 2 lines that look similar to this:
	        <link id="folder" label="Example Application - Microwave Oven" style-id="left" url="http://org.eclipse.ui.intro/runAction?pluginId=org.xtuml.bp.welcome&amp;class=org.xtuml.bp.welcome.gettingstarted.SampleProjectGettingStartedAction&amp;model=MicrowaveOven&amp;SingleFileModel=false">
	          <text>Click here to create a project that includes a sample application model of a Microwave Oven.</text>
	        </link>
	_- Copy and paste those 3 lines from one of the existing sample projects 
	   into the bottom of the <group id="page-links"> 
	_- Modify the model=<name>, SingleFileModel=<true or false>, and text 
	   fields for the new model
	_- Check in the changes
	_- Optional:
	   If this new model needs a runtime description then simply create a 
	   file named README and place it in this new projects root folder.  
	   For example:
	  <workspace>/org.xtuml.bp.welcome/models/<new model folder>/README
	   
	   NOTE: If a README file exists it will be opened when the project is 
	          created.
	   
	_- Test by running and select the new project from the Getting Started screen.

