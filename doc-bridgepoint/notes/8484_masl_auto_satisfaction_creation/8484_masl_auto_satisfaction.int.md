---

This work is licensed under the Creative Commons CC0 License

---

# Title goes here
### xtUML Project Implementation Note

Note: Each section has a description that states the purpose of that section.
Delete these section descriptions before checking in your note.  Delete this
note as well. __If you are using markdown formatting, don't forget to put 2 
spaces at the end of lines where you want a hard break.__

1. Abstract
-----------
In this section, give a summary of the design that this note aims to
describe.

2. Document References
----------------------
In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.  
<a id="2.1"></a>2.1 [BridgePoint DEI #8484](https://support.onefact.net/issues/8484) 
Update import to create satisfactions.  
<a id="2.2"></a>2.2 [BridgePoint DEI #8364](https://support.onefact.net/issues/8364) 
Adapt BridgePoint model import to copy MASL activities to their proper locations.  
<a id="2.3"></a>2.3 [BridgePoint DEI #8977](https://support.onefact.net/issues/8977) 
Manual test for automatic satisfaction creation.  

3. Background
-------------
During the process of migrating a MASL model to xtUML a single-file xtuml model
is created. This model contains some annotation that helps the model importer
know how to process the model. A prior task, [[2.2]](#2.2), introduced the 
blueprint for how MASL artifacts are migrated into a xtUML model.
This issue is raised to extend the work from [[2.2]](#2.2) to perform the 
task of automatically creating satisfactions between formalized provisions and 
requirements that have been annotated to indicate that a satisfaction is 
warranted.

In the single-file xtuml model being imported, each package that contains 
masl interfaces that need to be satisfied shall contain the following text 
string in the package description: masl_project

Since the this task is specific to creating satisfactions for MASL project 
there are constraints from the MASL export that can be used to limit the 
required work.  

3.1 The package that contains provisions and requirements to be satisfied shall
contain a description that includes the string: masl_project.  

3.2 There is a 1:1 mapping between provisions and requirements that shall be 
satisfied. For each provision there is no more than 1 requirement that to 
provide a satisfaction to. This means as soon as a satisfaction exists, against
a given provision or requirement no more satisfactions are needed.  
 

4. Requirements
---------------
The following requirements are specific to model import of a single file model 
with packages that contain a description string: masl_project.  
4.1 For each unsatisfied provision search the package for a matching 
requirement and create a satisfaction if a match is found.  
4.2 Create no more than 1 satisfaction per requirement and provision.  
4.3 The satisfaction shall be created in the package that that contains the 
{imported}provision/{imported}requirement being satisfied.  
 
5. Work Required
----------------
5.1 Modify ImportHelper.java::resolveMASLproject() that was introduced with  
[[2.2]](#2.2) and follow the pattern provided to introduce functionality that
iterates over each marked package to create satisfactions as specified
by the requirements.  

5.2 Introduce OAL that creates the satisfactions  
5.2.1 Introduce ooaofooa::Functions::autoCreateSatisfactions  
'''
find all provisions in the given package
find all imported provisions in the given package
find all requirements in the given package
find all imported requirments in the given package
for each provision
  if not satisfied yet
    for each requirement
      find a match to the provision by name
      if found 
        create satisfaction
      end if
    end for
  end if
  if not satisfied yet
    for each imported requirement
      find a match to the provision by name
      if found 
        create satisfaction
      end if
    end for
  end if
end for

repeat the above look with imported provisions

'''  

6. Implementation Comments
--------------------------
6.1 During testing it was found that graphics reconciliation of satisfactions
is not working. The "ball and cup" are not being connected. This problem 
existed prior to this task, and is being handled by a separate issue 
[[2.3]](#2.3).



7. Unit Test
------------
7.1 Run the manual test defined by [[2.3]](#2.3).  

8. User Documentation
---------------------
none  

9. Code Changes
---------------
Fork/Repository: rmulvey  
Branch: 8484_create_satisfaction_on_import  

<pre>

< Put the file list here >

</pre>

End
---

