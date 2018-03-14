Inter-project References Project Preferences
========================

## IPR Usage

### Allow inter-project model references  

Allow (or not) xtUML models to see the contents of public packages in other xtUML
models in this workspace.  

### Allow class and function access via IPR  

When IPRs are enabled, this option extends the functionality to allow an imported 
class inside a project to be assigned to a class that lives in another xtUML 
project. This allows for the creation of "library" classes.   

An imported class assigned via an inter-project reference is then treated 
like a locally-assigned imported class. Create, relate, select, member access 
shall work.  

This option also allows the modeler to invoke functions that exist inside a 
different xtUML project.   

## Emit referred to model elements in other projects

In the context of projects employing inter-project references, enabling this
option causes Model Compiler Pre-Build to follow and resolve references and 
output an aggregate file to pass to the model compiler tool chain.   

This option overrides the workspace-level setting of the same name in the 
general preferences at `xtUML > xtUML Translate`.  

## Preference Page

![Inter-project References](ProjIPR.png)    
