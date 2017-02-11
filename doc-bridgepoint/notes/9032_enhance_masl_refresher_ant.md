---

This work is licensed under the Creative Commons CC0 License

---

# Enhance the MASL refresher
### xtUML Project Analysis Note


1. Abstract
-----------
The MASL refresher is run periodically by BridgePoint to update an internal cache
of structural MASL information for validating MASL activities.  This note proposes 
work to enhance the MASL refresher to not create unnecessary files.   
 
2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9032](https://support.onefact.net/issues/9032) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #8797](https://support.onefact.net/issues/8797) MASL refresher.       
<a id="2.3"></a>2.3 [BridgePoint DEI #8801](https://support.onefact.net/issues/8801) MASL Round Trip Test     

3. Background
-------------
The initial implementation of the MASL Refresher (see [[2.2]](#2.2) ) does not 
prevent/skip the output of MASL action language files. Instead, it lets the existing 
MASL flow run like it does already and then deletes the files we don't want (we 
are only interested in keeping structural MASL and not activities in the refresher).  

A better solution is to enhance ```x2m``` or ```masl``` (the exe) to filter the 
activity data out or take a flag that tells it not to generate the activity files 
at all.   

4. Requirements
---------------
4.1  The xtuml2masl chain shall have the ability to run and produce only structural
  MASL output files (```.prj```, ```.mod```, and ```.int```).   
4.1.1  This shall work without deleting the files after they are created.     

5. Analysis
-----------
5.1  Implement in ```x2m``` or ```masl```?  
5.1.1  Upon investigation, implementing the solution in ```masl``` can be constrained
  to inspecting and using the flag in a single place, during file render.  
5.1.2  We also considered implementing in ```x2m``` because this would provide
  better performance by "stripping out" the unwanted data earlier.  It
  will require special care to make sure we are still creating the right 
  function and operation signatures (so they can appear in the structural output) 
  without including the activity bodies.   

  Handling in ```x2m``` can prevent potentially hundreds or thousands of lines of code 
  from being unnecessarily piped through the system io streams. In the SAC example 
  domain, the ```SAC.smasl``` (produced by the parser) was roughly one third of 
  the size after removing the "codeblock" statements. ```wc``` output below:  
  
| Newlines | Word Count | Byte Count | File Name              |
|----------|------------|------------|------------------------|
| 825      | 855        | 21783      | SAC_no_codeblock.smasl |
| 860      | 4517       | 65186      | SAC.smasl              |
| 1685     | 5372       | 86969      | total                  |
  
  The solution here will leverage the "model" class that is used as a singleton to 
  store the project root directory during output. A boolean flag could be attached 
  to this class to indicate whether codeblock output should be suppressed or not. This 
  flag could be evaluated in the function ```body2code_block``` which is the only 
  place where "codeblock" SMASL statements are produced.   
5.1.3  We shall implement a combination solution includes modifications to both
  ```x2m``` and ```masl```.  The changes in the two scripts do not conflict. In fact, the 
  ```x2m``` solution alone is incomplete.  ```masl``` work is still necessary because 
  ```masl``` will produce stub implementation files in the absence of "codeblock" 
  statements.   

6. Work Required
----------------
6.1  Edit ```masl/sys_user_co.c::UserPostOoaInitializationCalloutf(...)```   
6.1.1  Add handling for command line param ```-s``` indicating to output 
  structural MASL only.  Set a boolean flag to use internally.   
6.1.2  Pass the flag to ```masl_gen_render``` on lines 144, 146, & 147   

6.2  Update the interface ```masl/lib/gen```   
6.2.1  Have the ```render``` operation accept "structural_only" flag, and pass 
  it on to ```file::render(...)```   

6.3  Edit class operation ```masl/maslpopulation/file::render()```   
6.3.1  Take "structural_only" flag as a parameter   
6.3.2  Use the flag to skip rendering activity files, terminator files, etc.   
  
6.4  Update ```xtuml2masl``` operation in ```xtumlmc_build```   
6.4.1  Pass the ```-s``` flag to ```masl``` when the user has called ```xtuml2masl```
  with ```-xl```, an existing flag telling the script to skip output action language.      
6.4.2  Remove existing code to delete the activity files.   
  
6.5  Edit ```maslout/gen/sys_user_co.c::UserPostOoaInitializationCalloutf()``` to handle 
  the "-s" argument to x2m and set a flag in the model class.  

6.6  Edit ```maslout/models/maslout/lib/xtuml2masl/maslout/maslout::body2code_block``` to
  inspect the flag in the model class and skip ```codeblock``` creation if it is set to indicate 
  no code wanted.   
  
7. Acceptance Test
------------------
7.1  Run the MASL Round Trip Test [[2.3]](#2.3) up to Import step 2.9 
* When the MASL is opened there are no red squigglies under elements in this domain.    
* Open navigator view and verify the ```.mod``` and ```.int``` files are inside the
models folder next to the SAC.xtuml that contains the C_C instance.  There are no
```.svc```, ```.fn```, etc. files in this location.   

End
---

